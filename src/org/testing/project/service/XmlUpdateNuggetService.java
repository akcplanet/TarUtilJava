package org.testing.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testing.project.entity.Nugget;
import org.testing.project.entity.NuggetField;
import org.testing.project.utils.UtilTar;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUpdateNuggetService {

	public static void updateXMLNugget(Nugget nugget) {
		try {
			File folder = new File(nugget.getInputFolderPath());
			File[] listOfFiles = folder.listFiles();
			int nCount = nugget.getNuggetCount() > 1 ? nugget.getNuggetCount() : 1;
			for (int j = 0; j < nCount; j++) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String foldername = "/PERF" + timestamp.getTime();
				String path = (nugget.getOutputFolderPath() + foldername);

				for (File file : listOfFiles) {
					String filename = file.getName();
					if (filename.endsWith(".xml") || filename.endsWith(".XML")) {
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						docBuilder.setEntityResolver(new EntityResolver() {
							@Override
							public InputSource resolveEntity(String publicId, String systemId)
									throws SAXException, IOException {
								return new InputSource(new StringReader(""));
							}
						});
						Document doc = docBuilder.parse(file.getAbsolutePath());

						NodeList nodeCUSIP = doc.getElementsByTagName(NuggetField.CUSIP.nugget());
						if (isNullOrEmpty(nugget.getCusip()) && nodeCUSIP.getLength() > 0) {
							for (int i = 0; i < nodeCUSIP.getLength(); i++) {
								nodeCUSIP.item(i).setTextContent(nugget.getCusip());
							}
						}

						NodeList nodeINVNUM = doc.getElementsByTagName(NuggetField.INVNUM.nugget());
						if (isNullOrEmpty(nugget.getInvnum()) && nodeINVNUM.getLength() > 0) {
							Integer result = Integer.valueOf(nugget.getInvnum()).intValue();
							result = result + j;
							for (int i = 0; i < nodeINVNUM.getLength(); i++) {
								nodeINVNUM.item(i).setTextContent(String.valueOf(result));
							}
						}

						NodeList nodeportfolioname = doc
								.getElementsByTagName(NuggetField.PORTFOLIOS_PORTFOLIO_NAME.nugget());
						if (isNullOrEmpty(nugget.getPortfoliosPortfolioName()) && nodeportfolioname.getLength() > 0) {
							for (int i = 0; i < nodeportfolioname.getLength(); i++) {
								nodeportfolioname.item(i).setTextContent(nugget.getPortfoliosPortfolioName());
							}
						}

						NodeList nodetouchcount = doc.getElementsByTagName(NuggetField.TOUCH_COUNT.nugget());
						if (isNullOrEmpty(nugget.getTouchCount()) && nodetouchcount.getLength() > 0) {
							for (int i = 0; i < nodeportfolioname.getLength(); i++) {
								nodeportfolioname.item(i).setTextContent(nugget.getTouchCount());
							}
						}
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						new File(path).mkdirs();
						StreamResult result = new StreamResult(new File(path + "/" + filename));
						transformer.transform(source, result);
					}
				}
				FileOutputStream fostar = new FileOutputStream(nugget.getOutputFolderPath() + foldername + ".tar");
				ZipOutputStream zipOuttar = new ZipOutputStream(fostar);
				File fileToZip = new File(path);
				UtilTar.tarGZFile(fileToZip, fileToZip.getName(), zipOuttar);
				zipOuttar.close();
				fostar.close();
				new File(nugget.getOutputFolderPath() + "/executableFile").mkdirs();
				FileOutputStream fosgz = new FileOutputStream(
						nugget.getOutputFolderPath() + "/executableFile" + foldername + ".tar.gz");
				ZipOutputStream zipOutgz = new ZipOutputStream(fosgz);
				File fileToZip2 = new File(nugget.getOutputFolderPath() + foldername + ".tar");
				UtilTar.tarGZFile(fileToZip2, fileToZip2.getName(), zipOutgz);
				zipOutgz.close();
				fosgz.close();
				new File(nugget.getOutputFolderPath() + foldername + ".tar").delete();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

}
