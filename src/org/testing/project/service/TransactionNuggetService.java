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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testing.project.entity.Nugget;
import org.testing.project.entity.NuggetField;
import org.testing.project.utils.UtilTar;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TransactionNuggetService {

	public static void updateTransactionNugget(Nugget nugget) {
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String foldername = "/PERF" + timestamp.getTime();
			String path = (nugget.getOutputFolderPath() + foldername);
			new File(path).mkdirs();
			UtilTar.copyDirectory(new File(nugget.getInputFolderPath()), new File(path));
			String filename = path + "/transaction.xml";
			if (!filename.isEmpty()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				docFactory.setNamespaceAware(true);
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				docBuilder.setEntityResolver(new EntityResolver() {
					@Override
					public InputSource resolveEntity(String publicId, String systemId)
							throws SAXException, IOException {
						return new InputSource(new StringReader(""));
					}
				});
				Document doc = docBuilder.parse(nugget.getInputFolderPath() + "/transaction.xml");
				doc.getDocumentElement().normalize();
				Element root = doc.getDocumentElement();
				Node nodeTrade = doc.getElementsByTagName("TRADE").item(0);
				int nCount = nugget.getNuggetCount() > 1 ? nugget.getNuggetCount() : 1;
				((Element) root).setAttribute("RECORDS", String.valueOf(nCount));
				for (int j = 0; j < nCount; j++) {
					Element elem = (Element) nodeTrade.cloneNode(true);
					Node nodeINVNUM = elem.getElementsByTagName(NuggetField.INVNUM.nugget()).item(0);
					if (!isNullOrEmpty(nugget.getInvnum())) {
						Integer result = Integer.valueOf(nugget.getInvnum()).intValue();
						result = result + j + 1;
						nodeINVNUM.setTextContent(String.valueOf(result));
					} else {
						Integer result = Integer.valueOf(nodeINVNUM.getTextContent()).intValue();
						result = result + j + 1;
						nodeINVNUM.setTextContent(String.valueOf(result));
					}
					root.appendChild(elem);
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				DOMImplementation domImpl = doc.getImplementation();
				DocumentType doctype = domImpl.createDocumentType("doctype", "", "transactions.dtd");
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filename));
				transformer.transform(source, result);
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
			System.out.println("\nXML TAR GZ Created Successfully..");
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
