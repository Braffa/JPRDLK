package com.braffa.productlookup.amazon;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProductLookUpAdditionalTest extends TestCase {

	private static String DocumentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ElementToStream(doc.getDocumentElement(), baos);

		String docs = new String(baos.toByteArray());
		return docs.replaceAll("><", ">\n<");
	}

	private static void ElementToStream(Element element, OutputStream out) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void testProductLookUpByISBN_1() {
		// 13 DIGIT ISBN
		//Document myDoc = ProductLookUp.getProducts("9780980576856", "ISBN",
		//		null, null, "All");

		// System.out.println(DocumentToString(myDoc));

	}
	
	public void testProductLookUpByISBN_2() {
		// 10 DIGIT ISBN
		//for (int x = 0; x < 11; x++) {
		//	//Document myDoc = ProductLookUp.getProducts("978098057685" + x,
		//			"ISBN", null, null, "All");
		//	System.out.println("index " + x);
		//	System.out.println(DocumentToString(myDoc));
		//}
	}

	public void testProductLookUpByISBN_3() {
		// 10 DIGIT ISBN
		//for (int x = 0; x < 11; x++) {
		//	Document myDoc = ProductLookUp.getProducts("978078325222" + x,
		//			"ISBN", null, null, "All");
		//	System.out.println("index " + x);
		//	System.out.println(DocumentToString(myDoc));
		//}
	}
	public void testProductLookUpByISBN_4() {
		// 10 DIGIT ISBN
		//for (int x = 0; x < 11; x++) {
		//	Document myDoc = ProductLookUp.getProducts("979078325222" + x,
		//			"ISBN", null, null, "All");
		////	System.out.println("index " + x);
	//		System.out.println(DocumentToString(myDoc));
		//}
        assertEquals("test ", "test", "test");
	}
}
