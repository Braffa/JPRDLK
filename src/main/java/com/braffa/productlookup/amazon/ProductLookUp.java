package com.braffa.productlookup.amazon;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProductLookUp extends ProductLookUpBase {

	private static void addChild(Document document, Element parent,
			String childName, String childValue) {
		Element child = document.createElement(childName);
		child.appendChild(child.getOwnerDocument().createTextNode(childValue));
		parent.appendChild(child);
	}

	private static Document buildProductsXML(Document doc, boolean imagesRequired, String imageType) {
		Document document = newEmptyDocument();
		Element products = document.createElement("products");

		NodeList itemIdList = doc.getElementsByTagName("ItemId");
		Node itemIdNode = itemIdList.item(0);
		String productId = itemIdNode.getTextContent();

		NodeList itemidTypeList = doc.getElementsByTagName("IdType");
		Node itemidTypeNode = itemidTypeList.item(0);
		String productIdType = itemidTypeNode.getTextContent();

		NodeList lOfItemNodes = doc.getElementsByTagName("Item");
		if (lOfItemNodes.getLength() > 0) {

			for (int i = 0; i < lOfItemNodes.getLength(); i++) {
				Node myNode = lOfItemNodes.item(i);
				NodeList myNodes = myNode.getChildNodes();
				String asin = "";
				for (int j = 0, len = myNodes.getLength(); j < len; j++) {
					Node node = myNodes.item(j);
					if (node.getNodeName().equals("ASIN")) {
						asin = node.getTextContent();
					}
					if (node.getNodeName().equals("ItemAttributes")) {
						NodeList myNodes2 = node.getChildNodes();
						String author = "";
						String manufacturer = "";
						String productGroup = "";
						String title = "";
						for (int k = 0; k < myNodes2.getLength(); k++) {
							Node node2 = myNodes2.item(k);
							if (node2.getNodeName().equals("Author")) {
								author = node2.getTextContent();
							}
							if (node2.getNodeName().equals("Manufacturer")) {
								manufacturer = node2.getTextContent();
							}
							if (node2.getNodeName().equals("ProductGroup")) {
								productGroup = node2.getTextContent();
							}
							if (node2.getNodeName().equals("Title")) {
								title = node2.getTextContent();
							}
						}
						Element product = document.createElement("product");
						products.appendChild(product);
						addChild(document, product, "author", author.trim());
						addChild(document, product, "manufacturer",
								manufacturer.trim());
						addChild(document, product, "productgroup",
								productGroup.trim());
						addChild(document, product, "productid",
								productId.trim());
						addChild(document, product, "productidtype",
								productIdType.trim());
						addChild(document, product, "source", "Amazon");
						addChild(document, product, "sourceid", asin.trim());
						addChild(document, product, "title", title.trim());
						
						if (imagesRequired) {
							if (asin.trim().length() > 0) {
								Document imageDocument = fetchFromAWS(asin.trim(), null, "Images",
										null, null);
								document = buildImagesXML(imageDocument, document, product, imageType);
							}
						}
						
					}
				}
			}
		}
		document.appendChild(products);
		return document;
	}
	
	private static Document buildImagesXML(Document AWSDoc, Document imageDocument, Element product, String imageType) {
		NodeList nodeList = AWSDoc.getElementsByTagName("ImageSets");
		Element imageSet = imageDocument.createElement("ImageSet");
		
		if (nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node myNode = nodeList.item(i);
				NodeList myNodes = myNode.getChildNodes();
				for (int j = 0, len = myNodes.getLength(); j < len; j++) {
					Node node = myNodes.item(j);
					NodeList myNodes2 = node.getChildNodes();
					for (int k = 0, len2 = myNodes2.getLength(); k < len2; k++) {
						Node node2 = myNodes2.item(k);
						NodeList myNodes3 = node2.getChildNodes();
						Element element1 = imageDocument.createElement(node2
								.getNodeName());
						if (imageType == null) {
							imageSet.appendChild(element1);
							for (int l = 0, len3 = myNodes3.getLength(); l < len3; l++) {
								Node node3 = myNodes3.item(l);
								Element element2 = imageDocument.createElement(node3
										.getNodeName());
								element2.setTextContent(node3.getTextContent());
								element1.appendChild(element2);
							}
						} else {
							if (node2.getNodeName().equals(imageType)) {
								imageSet.appendChild(element1);
								for (int l = 0, len3 = myNodes3.getLength(); l < len3; l++) {
									Node node3 = myNodes3.item(l);
									Element element2 = imageDocument.createElement(node3
											.getNodeName());
									element2.setTextContent(node3.getTextContent());
									element1.appendChild(element2);
								}
								break;
							}
						}
					}
				}
			}
		}
		if (product == null) {
			imageDocument.appendChild(imageSet);
		} else {
			product.appendChild(imageSet);
		}
		return imageDocument;
	}

	private static Document fetchFromAWS(String productId,
			String productIdType, String responseGroup, String condition,
			String searchIndex) {
		ProductSignedRequestsHelper helper = getSignedRequestsHelper();

		String requestUrlByMap = signRequestWithMap(helper, productId,
				productIdType, responseGroup, condition, searchIndex);

		System.out.println(requestUrlByMap);
		return fetchDocument(requestUrlByMap);
	}

	public static Document getProducts(String productId, String productIdType,
			String responseGroup, String condition, String searchIndex) {

		Document doc = fetchFromAWS(productId, productIdType, responseGroup,
				condition, searchIndex);

		return buildProductsXML(doc, false, null);

	}

	public static Document getImages(String productId, String productIdType,
			String responseGroup, String condition, String searchIndex) {

		Document AWSDoc = fetchFromAWS(productId, productIdType, responseGroup,
				condition, searchIndex);
		
		Document imageDocument = newEmptyDocument();

		return buildImagesXML(AWSDoc, imageDocument, null, null);
	}
	
	public static Document getProductsWithImages(String productId, String productIdType,
			String responseGroup, String condition, String searchIndex) {

		Document doc = fetchFromAWS(productId, productIdType, responseGroup,
				condition, searchIndex);

		return buildProductsXML(doc, true, null);

	}
	
	public static Document getProductsWithImage(String productId, String productIdType,
			String responseGroup, String condition, String searchIndex, String imageType) {

		Document doc = fetchFromAWS(productId, productIdType, responseGroup,
				condition, searchIndex);

		return buildProductsXML(doc, true, imageType);

	}

	private static Document newEmptyDocument() {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		document = builder.newDocument();

		return document;
	}

}
