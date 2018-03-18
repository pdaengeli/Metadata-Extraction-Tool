<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:nz_govt_natlib_xsl_XSLTFunctions="nz.govt.natlib.xsl.XSLTFunctions">
	<xsl:strip-space elements="doc chapter section" />
	<xsl:output omit-xml-declaration="yes" indent="yes" encoding="iso-8859-1" version="1.0" />
	<xsl:template match="/">
		<File>
			<FileIdentifier>
				<xsl:value-of
					select="nz_govt_natlib_xsl_XSLTFunctions:determineFileIdentifier(string(EPUB/METADATA/PID),string(EPUB/METADATA/OID),string(EPUB/METADATA/FILENAME),string(EPUB/METADATA/FID))" />
			</FileIdentifier>
			<xsl:for-each select="EPUB/METADATA/PATH">
				<Path>
					<xsl:value-of select="." />
				</Path>
			</xsl:for-each>
			<Filename>
				<xsl:for-each select="EPUB/METADATA/FILENAME">
					<Name>
						<xsl:value-of select="." />
					</Name>
				</xsl:for-each>
				<xsl:for-each select="EPUB/METADATA/EXTENSION">
					<Extension>
						<xsl:value-of select="." />
					</Extension>
				</xsl:for-each>
			</Filename>
			<xsl:for-each select="EPUB/METADATA/FILELENGTH">
				<Size>
					<xsl:value-of select="." />
				</Size>
			</xsl:for-each>
			<FileDateTime>
				<xsl:for-each select="EPUB/METADATA/DATE">
					<Date format="yyyyMMdd">
						<xsl:value-of select="." />
					</Date>
				</xsl:for-each>
				<xsl:for-each select="EPUB/METADATA/TIME">
					<Time format="HHmmssSSS">
						<xsl:value-of select="." />
					</Time>
				</xsl:for-each>
			</FileDateTime>
			<xsl:for-each select="EPUB/METADATA/TYPE">
				<Mimetype>
					<xsl:value-of select="." />
				</Mimetype>
			</xsl:for-each>
			<FileFormat>
				<xsl:value-of select="string('Epub')" />
			</FileFormat>
			<Text>
				<CharacterSet>
					<xsl:value-of select="string('UTF-8')" />
				</CharacterSet>
				<MarkupLanguage>
					<xsl:value-of select="string('English')" />
				</MarkupLanguage>
			</Text>
			<EpubMetaData>
				<xsl:for-each select="EPUB/EPUB-META/FORMAT">
					<MetadataFormat>
						<xsl:value-of select="." />
					</MetadataFormat>
				</xsl:for-each>
				<Authors>
					<xsl:for-each select="EPUB/EPUB-META/AUTHOR">
					<Author>
						<xsl:value-of select="."/>
					</Author>
					</xsl:for-each>
				</Authors>
				<Contributors>
					<xsl:for-each select="EPUB/EPUB-META/CONTRIBUTOR">
					<Contributor>
						<xsl:value-of select="." />
					</Contributor>
				</xsl:for-each>
				</Contributors>
				<Publishers>
					<xsl:for-each select="EPUB/EPUB-META/PUBLISHER">
					<Publisher>
						<xsl:value-of select="." />
					</Publisher>
				</xsl:for-each>
				</Publishers>
				<Identifiers>
					<xsl:for-each select="EPUB/EPUB-META/IDENTIFIER">
						<Identifier>
							<Scheme>
								<xsl:value-of select="SCHEME"/>
							</Scheme>
							<Value>
								<xsl:value-of select="VALUE"/>
							</Value>
						</Identifier>
					</xsl:for-each>
				</Identifiers>							
			</EpubMetaData>
			<EpubCover>
				<xsl:for-each select="EPUB/COVER/ID">
					<Id>
						<xsl:value-of select="." />
					</Id>
				</xsl:for-each>
				<xsl:for-each select="EPUB/COVER/TITLE">
					<Title>
						<xsl:value-of select="." />
					</Title>
				</xsl:for-each>
				<xsl:for-each select="EPUB/COVER/ENCODING">
					<Encoding>
						<xsl:value-of select="." />
					</Encoding>
				</xsl:for-each>
				<xsl:for-each select="EPUB/COVER/HREF">
					<Href>
						<xsl:value-of select="." />
					</Href>
				</xsl:for-each>	
				<xsl:for-each select="EPUB/COVER/SIZE">
					<Size>
						<xsl:value-of select="." />
					</Size>
				</xsl:for-each>
				<xsl:for-each select="EPUB/COVER/MEDIATYPE">
					<MediaType>
						<xsl:value-of select="." />
					</MediaType>
				</xsl:for-each>										
			</EpubCover>
			<xsl:for-each select="EPUB/MIMEREPORT">
              <MimeReport>
                <xsl:value-of select="."/>
              </MimeReport>
            </xsl:for-each>
			<Ncx>
				<xsl:for-each select="EPUB/NCX/ID">
					<Id>
						<xsl:value-of select="." />
					</Id>
				</xsl:for-each>
				<xsl:for-each select="EPUB/NCX/TITLE">
					<Title>
						<xsl:value-of select="." />
					</Title>
				</xsl:for-each>
				<xsl:for-each select="EPUB/NCX/ENCODING">
					<Encoding>
						<xsl:value-of select="." />
					</Encoding>
				</xsl:for-each>
				<xsl:for-each select="EPUB/NCX/HREF">
					<Href>
						<xsl:value-of select="." />
					</Href>
				</xsl:for-each>	
				<xsl:for-each select="EPUB/NCX/SIZE">
					<Size>
						<xsl:value-of select="." />
					</Size>
				</xsl:for-each>
				<xsl:for-each select="EPUB/NCX/MEDIATYPE">
					<MediaType>
						<xsl:value-of select="." />
					</MediaType>
				</xsl:for-each>				
			</Ncx>
			<Opf>
				<xsl:for-each select="EPUB/OPF/ID">
					<Id>
						<xsl:value-of select="." />
					</Id>
				</xsl:for-each>
				<xsl:for-each select="EPUB/OPF/TITLE">
					<Title>
						<xsl:value-of select="." />
					</Title>
				</xsl:for-each>
				<xsl:for-each select="EPUB/OPF/ENCODING">
					<Encoding>
						<xsl:value-of select="." />
					</Encoding>
				</xsl:for-each>
				<xsl:for-each select="EPUB/OPF/HREF">
					<Href>
						<xsl:value-of select="." />
					</Href>
				</xsl:for-each>	
				<xsl:for-each select="EPUB/OPF/SIZE">
					<Size>
						<xsl:value-of select="." />
					</Size>
				</xsl:for-each>
				<xsl:for-each select="EPUB/OPF/MEDIATYPE">
					<MediaType>
						<xsl:value-of select="." />
					</MediaType>
				</xsl:for-each>				
			</Opf>
			<TableOfContents>
				<xsl:for-each select="EPUB/TABLEOFCONTENTS/SIZE">
					<Size>
						<xsl:value-of select="." />
					</Size>
				</xsl:for-each>
				<References>
					<xsl:for-each select="EPUB/TABLEOFCONTENTS/REFERENCE">
						<Reference>
							<Id>
								<xsl:value-of select="ID"/>
							</Id>
							<Title>
								<xsl:value-of select="REFERENCETITLE"/>
							</Title>
							<Encoding>
								<xsl:value-of select="ENCODING"/>
							</Encoding>
							<Href>
								<xsl:value-of select="HREF"/>
							</Href>
							<Size>
								<xsl:value-of select="SIZE"/>
							</Size>
							<MediaType>
								<xsl:value-of select="MEDIATYPE"/>
							</MediaType>
						</Reference>
					</xsl:for-each>
				</References>			
			</TableOfContents>			
		</File>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c)1998-2002 eXcelon Corp. 
<metaInformation> <scenarios><scenario default="yes" name="test1" userelativepaths="yes" externalpreview="no" url="..\..\harvested\nlnz_dd\A PSALM OF LIFE wordv97&#x2D;v2000.doc.xml" htmlbaseurl="" processortype="internal" 
	commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/></scenarios><MapperInfo	srcSchemaPath="epub.dtd" srcSchemaRoot="EPUB" 
	srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="nlnz_file.xsd" destSchemaRoot="File" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/> 
</metaInformation> -->