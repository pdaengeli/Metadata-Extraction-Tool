# Metadata Extraction Tool

The National Library of New Zealand's Metadata Extraction Tool automatically extracts preservation-related metadata from digital files, then output that metadata in XML formats. It can be used through a graphical user interface or command-line interface.

## Introduction
The Metadata Extraction Tool was developed by the National Library of New Zealand to programmatically extract preservation metadata from a range of file formats like PDF documents, image files, sound files Microsoft office documents, and many others.

The tool was initially developed in 2003 and released as open source softtware in 2007. The current version is hosted at https://github.com/DIA-NZ/Metadata-Extraction-Tool, but the legacy version and documentation is also available at the [SourceForge project page](http://meta-extractor.sourceforge.net).

## Purpose of the Metadata Extraction Tool
The Tool builds on the Library's work on digital preservation, and its logical preservation metadata schema. It is designed to:

* automatically extracts preservation-related metadata from digital files
* output that metadata in a standard format (XML) for use in preservation activities.

The Tool was designed for preservation processes and activities, but can be used to for other tasks, such as the extraction of metadata for resource discovery.

## Supported File Formats
The Metadata Extract Tool includes a number of 'adapters' that extract metadata from specific file types. Extractors are currently provided for:

* Images: BMP, GIF, JPEG and TIFF.
* Office documents: MS Word (version 2, 6), Word Perfect, Open Office (version 1), MS Works, MS Excel, MS PowerPoint, and PDF.
* Audio and Video: WAV, MP3 (normal and with ID3Tags), BFW, FLAC.
* Markup languages: HTML and XML.
* Internet files: ARC

If a file type is unknown the tool applies a generic adapter, which extracts data that the host system 'knows' about any given file (such as size, filename, and date created).

## Capabilities

The tool has both a Microsoft Windows interface and a UNIX command line interface. This enables work to be automated through batch processing or processed on an individual basis as required.

The application opens all files as read-only, ensuring the integrity of original files. The tool only reads header information, so the extraction process is quick.

## Open Source Development

The Tool is written in Java and XML and is distributed under the Apache Public License (version 2).

Developers may be interested in extending some of the key components of the Metadata Extraction Tool such as extending existing adapters or developing new ones to process other file types, or creating new XSLT files to generate different XML output formats.

Please refer to [Developers Guide](http://meta-extractor.sourceforge.net/meta-extractor-developers-guide-v3.pdf) for more information on these components.

### Bug trackers

* Legacy: https://sourceforge.net/p/meta-extractor/bugs/
* Current: https://github.com/DIA-NZ/Metadata-Extraction-Tool/issues

## Contact

If you would like further information on the Metadata Extract Tool please send an email to: metadata-extract@natlib.govt.nz
