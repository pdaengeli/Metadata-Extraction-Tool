package nz.govt.natlib.adapter.epub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FilenameUtils;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import nz.govt.natlib.adapter.AdapterUtils;
import nz.govt.natlib.adapter.DataAdapter;
import nz.govt.natlib.fx.ParserContext;
import nz.govt.natlib.meta.log.LogManager;
import nz.govt.natlib.meta.log.LogMessage;

public class EpubAdapter extends DataAdapter {

	public boolean acceptsFile(File file) {
		boolean epub = false;
		String fileExt = FilenameUtils.getExtension(file.getName());
		epub = (fileExt.equals("epub")); 
		if (!epub) {
			LogManager.getInstance().logMessage(LogMessage.WORTHLESS_CHATTER, "IO Exception determining EPUB file type");
		}
		return epub;
	}

	public String getVersion() {
		return "1.0";
	}

	public String getOutputType() {
		return "epub.dtd";
	}

	public String getInputType() {
		return "application/epub+zip";
	}

	public String getName() {
		return "Epub Text Adapter";
	}

	public String getDescription() {
		return "Adapts all EPUB files";
	}

	public void adapt(File file, ParserContext ctx) throws IOException {
		// Header and default information
		ctx.fireStartParseEvent("epub");
		writeFileInfo(file, ctx);
		FileInputStream fin = null;
		HashMap<String, Integer> mimeMap = new HashMap<String, Integer>();
		try {
			EpubReader epubReader = new EpubReader();
			fin = new FileInputStream(file);
			Book book = epubReader.readEpub(fin);

			Metadata metadata = book.getMetadata();
			ctx.fireStartParseEvent("epub-meta");
			processEpubMetadata(metadata, ctx);
			ctx.fireEndParseEvent("epub-meta");

			Resource coverImage = book.getCoverImage();
			if (coverImage != null) {
				getResourceMetadata(ctx, coverImage, "cover");
			}

			Resource ncx = book.getNcxResource();
			if (ncx != null) {
				getResourceMetadata(ctx, ncx, "ncx");
			}

			Resource opf = book.getOpfResource();
			if (opf != null) {
				getResourceMetadata(ctx, opf, "opf");
			}

			// Create mime report page wise
			Resources allResources = book.getResources();
			if (allResources != null) {
				createMimeReport(ctx, mimeMap, allResources);
			}

			TableOfContents toc = book.getTableOfContents();
			if (toc != null) {
				ctx.fireStartParseEvent("tableofcontents");
				processTocData(toc, ctx);
				ctx.fireEndParseEvent("tableofcontents");
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			ctx.fireEndParseEvent("epub");
			AdapterUtils.close(fin);
		}
	}

	private void createMimeReport(ParserContext ctx, HashMap<String, Integer> mimeMap, Resources resources) {
		Map<String, Resource> resourceMap = resources.getResourceMap();
		Resource value = null;
		for (String key : resourceMap.keySet()) {
			value = resourceMap.get(key);
			if (value != null) {
				String mimeType = value.getMediaType().getName();
				addMimeTypeToMimeMap(mimeType, mimeMap);
			}
		}
		// System.out.println(mimeMap);
		if (mimeMap.size() > 0) {
			Set<String> keys = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
			keys.addAll(mimeMap.keySet());
			Iterator<String> keyIterator = keys.iterator();
			StringBuffer mimeSummary = new StringBuffer();
			boolean first = true;
			while (keyIterator != null && keyIterator.hasNext()) {
				String mimetype = (String) keyIterator.next();
				if (first == false) {
					mimeSummary.append(", ");
				}
				first = false;
				mimeSummary.append(mimetype).append(":").append(mimeMap.get(mimetype));
			}
			fireSpecialNull(ctx, "mimereport", mimeSummary.toString());
		}
	}

	private void addMimeTypeToMimeMap(String mimeType, HashMap<String, Integer> mimeMap) {
		if (mimeType == null)
			return;
		mimeType = mimeType.trim();
		Integer counter = mimeMap.get(mimeType);
		int count = 0;
		if (counter != null) {
			count = counter.intValue();
		}
		count++;
		mimeMap.put(mimeType, new Integer(count));
	}

	private void processTocData(TableOfContents toc, ParserContext ctx) {
		try {
			fireSpecialNull(ctx, "size", String.valueOf(toc.size()));

			List<TOCReference> tocReferenceList = toc.getTocReferences();
			for (int i = 0; i < tocReferenceList.size(); i++) {
				TOCReference tocReference = tocReferenceList.get(i);

				Resource tocResource = tocReference.getResource();
				ctx.fireStartParseEvent("reference");
				fireSpecialNull(ctx, "id", tocResource.getId());
				fireSpecialNull(ctx, "referencetitle", tocReference.getTitle());
				fireSpecialNull(ctx, "encoding", tocResource.getInputEncoding());
				fireSpecialNull(ctx, "href", tocResource.getHref());
				fireSpecialNull(ctx, "size", String.valueOf(tocResource.getSize()));
				String mediaType = tocResource.getMediaType() != null ? tocResource.getMediaType().getName() : "";
				fireSpecialNull(ctx, "mediatype", mediaType);
				ctx.fireEndParseEvent("reference");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	private void getResourceMetadata(ParserContext ctx, Resource resource, String eventName) {
		ctx.fireStartParseEvent(eventName);
		fireSpecialNull(ctx, "id", resource.getId());
		fireSpecialNull(ctx, "title", resource.getTitle());
		fireSpecialNull(ctx, "encoding", resource.getInputEncoding());
		fireSpecialNull(ctx, "href", resource.getHref());
		fireSpecialNull(ctx, "size", String.valueOf(resource.getSize()));
		String mediaType = resource.getMediaType() != null ? resource.getMediaType().getName() : "";
		fireSpecialNull(ctx, "mediatype", mediaType);
		ctx.fireEndParseEvent(eventName);
	}

	private void processEpubMetadata(Metadata metadata, ParserContext ctx) {
		try {
			List<Author> authorsList = metadata.getAuthors();
			for (int i = 0; i < authorsList.size(); i++) {
				Author author = authorsList.get(i);
				fireSpecialNull(ctx, "author", author.getFirstname() + " " + author.getLastname());
			}

			List<Author> contributorsList = metadata.getContributors();
			for (int i = 0; i < contributorsList.size(); i++) {
				Author contributor = contributorsList.get(i);
				fireSpecialNull(ctx, "contributor", contributor.getFirstname() + " " + contributor.getLastname());
			}

			List<Date> datesList = metadata.getDates();
			for (int i = 0; i < datesList.size(); i++) {
				String date = datesList.get(i).getValue();
				fireSpecialNull(ctx, "date", date);
			}

			List<String> descriptionList = metadata.getDescriptions();
			getEachMetadata(ctx, "description", descriptionList);

			fireSpecialNull(ctx, "format", metadata.getFormat());

			List<Identifier> identifiersList = metadata.getIdentifiers();
			for (int i = 0; i < identifiersList.size(); i++) {
				Identifier identifier = identifiersList.get(i);
				String scheme = identifier.getScheme();
				String value = identifier.getValue();
				ctx.fireStartParseEvent("identifier");
				fireSpecialNull(ctx, "Scheme", scheme);
				fireSpecialNull(ctx, "Value", value);
				ctx.fireEndParseEvent("identifier");
			}

			fireSpecialNull(ctx, "language", metadata.getLanguage());

			List<String> publishersList = metadata.getPublishers();
			getEachMetadata(ctx, "publisher", publishersList);

			List<String> rightsList = metadata.getRights();
			getEachMetadata(ctx, "rights", rightsList);

			List<String> subjectsList = metadata.getSubjects();
			getEachMetadata(ctx, "subject", subjectsList);

			List<String> titlesList = metadata.getTitles();
			getEachMetadata(ctx, "title", titlesList);

			List<String> typesList = metadata.getSubjects();
			getEachMetadata(ctx, "type", typesList);

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void getEachMetadata(ParserContext ctx, String name, List<String> contentList) {
		for (int i = 0; i < contentList.size(); i++) {
			String value = contentList.get(i);
			fireSpecialNull(ctx, name, value);
		}
	}

	private static void fireSpecialNull(ParserContext ctx, String name, String value) {
		if (value == null) {
			ctx.fireParseEvent(name, "");
		} else {
			ctx.fireParseEvent(name, value);
		}
		// System.out.println(name + ": " + value);
	}

	public static void main(String args[]) {
		try {
			File testFile = new File("C:\\AppDev\\NDHA\\EPub_files\\V1-FL17239.epub");
			ParserContext ctx = new ParserContext();
			EpubAdapter epubAdapter = new EpubAdapter();

			System.out.println("Extracting EPUB metadata from EPUB Adapter....\n");

			epubAdapter.adapt(testFile, ctx);
			ctx.printAttributes();

			System.out.println("\n ####END####");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
