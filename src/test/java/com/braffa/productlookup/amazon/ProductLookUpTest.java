
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProductLookUpTest extends TestCase {

    private static String DocumentToString(Document doc) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ElementToStream(doc.getDocumentElement(), baos);

        String docs = new String(baos.toByteArray());
        return docs.replaceAll("><", ">\n<");
    }

    private static void ElementToStream(Element element, OutputStream out) {
        try{
            DOMSource source = new DOMSource(element);
            StreamResult result = new StreamResult(out);
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void testProductLookUp() {
        assertEquals("test ", "test", "test");
	Document myDoc = ProductLookUp.getProducts("5060088823156", "EAN",
				null, null, "All");

		 System.out.println(DocumentToString(myDoc));

		NodeList lOfProducts = myDoc.getElementsByTagName("products");
		assertTrue("Unexpected lOfProducts returned ",
		lOfProducts.getLength() == 1);
	NodeList lOfProduct = myDoc.getElementsByTagName("product");
		assertTrue("Unexpected lOfProduct returned ",
		lOfProduct.getLength() == 2);

		Node myNode = lOfProduct.item(1);
		NodeList myNodes = myNode.getChildNodes();
		assertTrue("Unexpected myNodes returned ", myNodes.getLength() == 8);

		assertEquals("Unexpected author returned ", "Pink Floyd",
				myNodes.item(0).getTextContent());

		assertEquals("Unexpected manufacturer returned ", "Music Sales",
				myNodes.item(1).getTextContent());

		assertEquals("Unexpected productgroup returned ", "Book",
				myNodes.item(2).getTextContent());

		assertEquals("Unexpected productid returned ", "5060088823156", myNodes
				.item(3).getTextContent());

		assertEquals("Unexpected productidtype returned ", "EAN",
				myNodes.item(4).getTextContent());

		assertEquals("Unexpected source returned ", "Amazon", myNodes.item(5)
				.getTextContent());

		assertEquals("Unexpected sourceid returned ", "B002DSYNUA", myNodes
				.item(6).getTextContent());

		assertEquals("Unexpected title returned ",
				"LICK LIBRARY JAM WITH PINK FLOYD GTR CD", myNodes.item(7)
						.getTextContent());

		myNode = lOfProduct.item(0);
		myNodes = myNode.getChildNodes();
		assertTrue("Unexpected myNodes returned ", myNodes.getLength() == 8);

		assertEquals("Unexpected author returned ", "Ken Nash", myNodes.item(0)
				.getTextContent());

		assertEquals("Unexpected manufacturer returned ", "", myNodes.item(1)
				.getTextContent());

		assertEquals("Unexpected productgroup returned ", "Book",
				myNodes.item(2).getTextContent());

		assertEquals("Unexpected productid returned ", "5060088823156", myNodes
				.item(3).getTextContent());

		assertEquals("Unexpected productidtype returned ", "EAN",
				myNodes.item(4).getTextContent());

		assertEquals("Unexpected source returned ", "Amazon", myNodes.item(5)
				.getTextContent());

		assertEquals("Unexpected sourceid returned ", "B0050GV3IW", myNodes
				.item(6).getTextContent());

		assertEquals("Unexpected title returned ",
				"Lick Library: Jam With Pink Floyd", myNodes.item(7)
						.getTextContent());

	}
    public void testProductImageLookUp() {
        assertEquals("test ", "test", "test");
 /*       Document myDoc = ProductLookUp.getImages("B0050GV3IW", null, "Images", null, null);

        NodeList ImageSet = myDoc.getElementsByTagName("ImageSet");
        assertTrue("test 2 Unexpected ImageSet returned ", ImageSet.getLength() == 1);

        NodeList SwatchImageNodes = myDoc.getElementsByTagName("SwatchImage");
        assertTrue("test 10 Unexpected SwatchImageNodes returned ", SwatchImageNodes.getLength() == 1);
        Node myNode = SwatchImageNodes.item(0);
        NodeList myNodes = myNode.getChildNodes();
        assertTrue("test 11 Unexpected myNodes returned ", myNodes.getLength() == 3);
        assertEquals("Unexpected URL returned ", "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL30_.jpg",
                myNodes.item(0).getTextContent());
        assertEquals("test 12 Unexpected HEIGHT returned ", "30", myNodes.item(1).getTextContent());
        assertEquals("test 13 Unexpected WIDTH returned ", "21", myNodes.item(2).getTextContent());

        NodeList SmallImages = myDoc.getElementsByTagName("SmallImage");
        assertTrue("test 20 Unexpected SmallImage returned ", SmallImages.getLength() == 1);
        myNode = SmallImages.item(0);
        myNodes = myNode.getChildNodes();
        assertEquals("test 21 Unexpected URL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL75_.jpg", myNodes.item(0).getTextContent());
        assertEquals("test 22 Unexpected HEIGHT returned ", "75", myNodes.item(1).getTextContent());
        assertEquals("test 23 Unexpected WIDTH returned ", "53", myNodes.item(2).getTextContent());

        NodeList MediumImages = myDoc.getElementsByTagName("MediumImage");
        assertTrue("test 30 Unexpected MediumImage returned ", MediumImages.getLength() == 1);
        myNode = MediumImages.item(0);
        myNodes = myNode.getChildNodes();
        assertEquals("test 31 Unexpected URL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL160_.jpg", myNodes.item(0).getTextContent());
        assertEquals("test 32 Unexpected HEIGHT returned ", "160", myNodes.item(1).getTextContent());
        assertEquals("test 33 Unexpected WIDTH returned ", "114", myNodes.item(2).getTextContent());

        NodeList LargeImages = myDoc.getElementsByTagName("LargeImage");
        assertTrue("test 40 Unexpected LargeImage returned ", LargeImages.getLength() == 1);
        myNode = LargeImages.item(0);
        myNodes = myNode.getChildNodes();
        assertEquals("test 41 Unexpected URL returned ", "http://ecx.images-amazon.com/images/I/41jyoobZAKL.jpg",
                myNodes.item(0).getTextContent());
        assertEquals("test 42 Unexpected HEIGHT returned ", "300", myNodes.item(1).getTextContent());
        assertEquals("test 43 Unexpected WIDTH returned ", "213", myNodes.item(2).getTextContent());

        NodeList ThumbnailImages = myDoc.getElementsByTagName("ThumbnailImage");
        assertTrue("test 50 Unexpected LargeImage returned ", ThumbnailImages.getLength() == 1);
        myNode = ThumbnailImages.item(0);
        myNodes = myNode.getChildNodes();
        assertEquals("test 51 Unexpected URL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL75_.jpg", myNodes.item(0).getTextContent());
        assertEquals("test 52 Unexpected HEIGHT returned ", "75", myNodes.item(1).getTextContent());
        assertEquals("test 53 Unexpected WIDTH returned ", "53", myNodes.item(2).getTextContent());

        NodeList TinyImages = myDoc.getElementsByTagName("TinyImage");
        assertTrue("test 60 Unexpected TinyImages returned ", TinyImages.getLength() == 1);
        myNode = TinyImages.item(0);
        myNodes = myNode.getChildNodes();
        assertEquals("test 61 Unexpected URL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL110_.jpg", myNodes.item(0).getTextContent());
        assertEquals("test 62 Unexpected HEIGHT returned ", "110", myNodes.item(1).getTextContent());
        assertEquals("test 63 Unexpected WIDTH returned ", "78", myNodes.item(2).getTextContent());

        // System.out.println(DocumentToString(myDoc));
*/    }

    public void testProductWithImagesLookUp() {
        assertEquals("test ", "test", "test");
/*        Document myDoc = ProductLookUp.getProductsWithImages("5060088823156", "EAN", null, null, "All");

        NodeList lOfProducts = myDoc.getElementsByTagName("products");
        assertTrue("test 1 Unexpected lOfProducts returned ", lOfProducts.getLength() == 1);

        NodeList lOfProduct = myDoc.getElementsByTagName("product");
        assertTrue("test 2 Unexpected lOfProduct returned ", lOfProduct.getLength() == 2);

        Node myNode = lOfProduct.item(0);
        NodeList myNodes = myNode.getChildNodes();

        assertTrue("test 3 Unexpected myNodes returned ", myNodes.getLength() == 9);

        assertEquals("test 4  Unexpected author returned ", "Pink Floyd", myNodes.item(0).getTextContent());

        assertEquals("test 5 Unexpected manufacturer returned ", "Music Sales", myNodes.item(1).getTextContent());

        assertEquals("test 6  Unexpected productgroup returned ", "Book", myNodes.item(2).getTextContent());

        assertEquals("test 7 Unexpected productid returned ", "5060088823156", myNodes.item(3).getTextContent());

        assertEquals("test 8 Unexpected productidtype returned ", "EAN", myNodes.item(4).getTextContent());

        assertEquals("test 9 Unexpected source returned ", "Amazon", myNodes.item(5).getTextContent());

        assertEquals("test 10  Unexpected sourceid returned ", "B002DSYNUA", myNodes.item(6).getTextContent());

        assertEquals("test 11 Unexpected title returned ", "LICK LIBRARY JAM WITH PINK FLOYD GTR CD", myNodes.item(7)
                .getTextContent());

        myNode = myNodes.item(8);
        assertEquals("test 12 Unexpected node returned ", "ImageSet", myNode.getNodeName());

        myNodes = myNode.getChildNodes();
        assertEquals("test 13 Unexpected nodes returned ", 6, myNodes.getLength());

        NodeList imageNodes = myNode.getChildNodes();
        assertEquals("test 15 Unexpected nodes returned ", 6, imageNodes.getLength());

        Node swatchnode = imageNodes.item(0);
        NodeList SwatchNodes = swatchnode.getChildNodes();
        assertEquals("test 16 Unexpected nodes returned ", 3, SwatchNodes.getLength());
        Node swatchURL = SwatchNodes.item(0);
        assertEquals("test 161 Unexpected swatchURL returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL30_.jpg", swatchURL.getTextContent());
        Node swatchHeight = SwatchNodes.item(1);
        assertEquals("test 162 Unexpected swatchHeight returned ", "30", swatchHeight.getTextContent());
        Node swatchWidth = SwatchNodes.item(2);
        assertEquals("test 163 Unexpected swatchWidth returned ", "21", swatchWidth.getTextContent());

        Node SmallImage = imageNodes.item(1);
        NodeList SmallImages = SmallImage.getChildNodes();
        assertEquals("test 17 Unexpected nodes returned ", 3, SmallImages.getLength());
        Node SmallImageURL = SmallImages.item(0);
        assertEquals("test 171 Unexpected SmallImageURL returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL75_.jpg", SmallImageURL.getTextContent());
        Node SmallImageHeight = SmallImages.item(1);
        assertEquals("test 172 Unexpected SmallImageHeight returned ", "75", SmallImageHeight.getTextContent());
        Node SmallImageWidth = SmallImages.item(2);
        assertEquals("test 173 Unexpected SmallImageWidth returned ", "53", SmallImageWidth.getTextContent());

        Node ThumbnailImage = imageNodes.item(2);
        NodeList ThumbnailImages = ThumbnailImage.getChildNodes();
        assertEquals("test 18 Unexpected ThumbnailImage nodes returned ", 3, ThumbnailImages.getLength());
        Node ThumbnailImageURL = ThumbnailImages.item(0);
        assertEquals("test 181 Unexpected ThumbnailImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL75_.jpg", ThumbnailImageURL.getTextContent());
        Node ThumbnailImageHeight = ThumbnailImages.item(1);
        assertEquals("test 182 Unexpected ThumbnailImageHeight nodes returned ", "75",
                ThumbnailImageHeight.getTextContent());
        Node ThumbnailImageWidth = ThumbnailImages.item(2);
        assertEquals("test 183 Unexpected ThumbnailImageWidth nodes returned ", "53",
                ThumbnailImageWidth.getTextContent());

        Node TinyImage = imageNodes.item(3);
        NodeList TinyImages = TinyImage.getChildNodes();
        assertEquals("test 19 Unexpected TinyImage nodes returned ", 3, TinyImages.getLength());
        Node TinyImageURL = TinyImages.item(0);
        assertEquals("test 191 Unexpected TinyImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL110_.jpg", TinyImageURL.getTextContent());
        Node TinyImageHeight = TinyImages.item(1);
        assertEquals("test 192 Unexpected TinyImageHeight nodes returned ", "110", TinyImageHeight.getTextContent());
        Node TinyImageWidth = TinyImages.item(2);
        assertEquals("test 193 Unexpected TinyImageWidth nodes returned ", "78", TinyImageWidth.getTextContent());

        Node MediumImage = imageNodes.item(4);
        NodeList MediumImages = MediumImage.getChildNodes();
        assertEquals("test 20 Unexpected MediumImage nodes returned ", 3, MediumImages.getLength());
        Node mediumImageURL = MediumImages.item(0);
        assertEquals("test 201 Unexpected mediumImageURL returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL160_.jpg", mediumImageURL.getTextContent());
        Node mediumImageHeight = MediumImages.item(1);
        assertEquals("test 202 Unexpected mediumImageHeight returned ", "160", mediumImageHeight.getTextContent());
        Node mediumImageWidth = MediumImages.item(2);
        assertEquals("test 203 Unexpected mediumImageWidth returned ", "114", mediumImageWidth.getTextContent());

        Node LargeImage = imageNodes.item(5);
        NodeList LargeImages = LargeImage.getChildNodes();
        assertEquals("test 21 Unexpected LargeImage nodes returned ", 3, LargeImages.getLength());
        Node largeImageURL = LargeImages.item(0);
        assertEquals("test 211 Unexpected largeImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L.jpg", largeImageURL.getTextContent());
        Node largeImageHeight = LargeImages.item(1);
        assertEquals("test 212 Unexpected largeImageHeight nodes returned ", "500", largeImageHeight.getTextContent());
        Node largeImageWidth = LargeImages.item(2);
        assertEquals("test 213 Unexpected largeImageWidth nodes returned ", "356", largeImageWidth.getTextContent());

        myNode = lOfProduct.item(1);
        myNodes = myNode.getChildNodes();
        assertTrue("Unexpected myNodes returned ", myNodes.getLength() == 9);

        assertEquals("Unexpected author returned ", "Ken Nash", myNodes.item(0).getTextContent());

        assertEquals("Unexpected manufacturer returned ", "", myNodes.item(1).getTextContent());

        assertEquals("Unexpected productgroup returned ", "Book", myNodes.item(2).getTextContent());

        assertEquals("Unexpected productid returned ", "5060088823156", myNodes.item(3).getTextContent());

        assertEquals("Unexpected productidtype returned ", "EAN", myNodes.item(4).getTextContent());

        assertEquals("Unexpected source returned ", "Amazon", myNodes.item(5).getTextContent());

        assertEquals("Unexpected sourceid returned ", "B0050GV3IW", myNodes.item(6).getTextContent());

        assertEquals("Unexpected title returned ", "Lick Library: Jam With Pink Floyd", myNodes.item(7)
                .getTextContent());

        myNode = myNodes.item(8);
        assertEquals("test 40 Unexpected node returned ", "ImageSet", myNode.getNodeName());

        imageNodes = myNode.getChildNodes();
        assertEquals("test 43 Unexpected nodes returned ", 6, imageNodes.getLength());

        swatchnode = imageNodes.item(0);
        SwatchNodes = swatchnode.getChildNodes();
        assertEquals("test 44 Unexpected nodes returned ", 3, SwatchNodes.getLength());
        swatchURL = SwatchNodes.item(0);
        assertEquals("test 441 Unexpected swatchURL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL30_.jpg", swatchURL.getTextContent());
        swatchHeight = SwatchNodes.item(1);
        assertEquals("test 442 Unexpected swatchHeight returned ", "30", swatchHeight.getTextContent());
        swatchWidth = SwatchNodes.item(2);
        assertEquals("test 443 Unexpected swatchWidth returned ", "21", swatchWidth.getTextContent());

        SmallImage = imageNodes.item(1);
        SmallImages = SmallImage.getChildNodes();
        assertEquals("test 45 Unexpected nodes returned ", 3, SmallImages.getLength());
        SmallImageURL = SmallImages.item(0);
        assertEquals("test 451 Unexpected SmallImageURL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL75_.jpg", SmallImageURL.getTextContent());
        SmallImageHeight = SmallImages.item(1);
        assertEquals("test 452 Unexpected SmallImageHeight returned ", "75", SmallImageHeight.getTextContent());
        SmallImageWidth = SmallImages.item(2);
        assertEquals("test 453 Unexpected SmallImageWidth returned ", "53", SmallImageWidth.getTextContent());

        ThumbnailImage = imageNodes.item(2);
        ThumbnailImages = ThumbnailImage.getChildNodes();
        assertEquals("test 46 Unexpected ThumbnailImage nodes returned ", 3, ThumbnailImages.getLength());
        ThumbnailImageURL = ThumbnailImages.item(0);
        assertEquals("test 461 Unexpected ThumbnailImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL75_.jpg", ThumbnailImageURL.getTextContent());
        ThumbnailImageHeight = ThumbnailImages.item(1);
        assertEquals("test 462 Unexpected ThumbnailImageHeight nodes returned ", "75",
                ThumbnailImageHeight.getTextContent());
        ThumbnailImageWidth = ThumbnailImages.item(2);
        assertEquals("test 463 Unexpected ThumbnailImageWidth nodes returned ", "53",
                ThumbnailImageWidth.getTextContent());

        TinyImage = imageNodes.item(3);
        TinyImages = TinyImage.getChildNodes();
        assertEquals("test 47 Unexpected TinyImage nodes returned ", 3, TinyImages.getLength());
        TinyImageURL = TinyImages.item(0);
        assertEquals("test 471 Unexpected TinyImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL110_.jpg", TinyImageURL.getTextContent());
        TinyImageHeight = TinyImages.item(1);
        assertEquals("test 472 Unexpected TinyImageHeight nodes returned ", "110", TinyImageHeight.getTextContent());
        TinyImageWidth = TinyImages.item(2);
        assertEquals("test 473 Unexpected TinyImageWidth nodes returned ", "78", TinyImageWidth.getTextContent());

        MediumImage = imageNodes.item(4);
        MediumImages = MediumImage.getChildNodes();
        assertEquals("test 48 Unexpected MediumImage nodes returned ", 3, MediumImages.getLength());
        mediumImageURL = MediumImages.item(0);
        assertEquals("test 481 Unexpected mediumImageURL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL160_.jpg", mediumImageURL.getTextContent());
        mediumImageHeight = MediumImages.item(1);
        assertEquals("test 482 Unexpected mediumImageHeight returned ", "160", mediumImageHeight.getTextContent());
        mediumImageWidth = MediumImages.item(2);
        assertEquals("test 486 Unexpected mediumImageWidth returned ", "114", mediumImageWidth.getTextContent());

        LargeImage = imageNodes.item(5);
        LargeImages = LargeImage.getChildNodes();
        assertEquals("test 49 Unexpected LargeImage nodes returned ", 3, LargeImages.getLength());
        largeImageURL = LargeImages.item(0);
        assertEquals("test 491 Unexpected largeImageURL nodes returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL.jpg", largeImageURL.getTextContent());
        largeImageHeight = LargeImages.item(1);
        assertEquals("test 492 Unexpected largeImageHeight nodes returned ", "300", largeImageHeight.getTextContent());
        largeImageWidth = LargeImages.item(2);
        assertEquals("test 493 Unexpected largeImageWidth nodes returned ", "213", largeImageWidth.getTextContent());

        // System.out.println(DocumentToString(myDoc));
*/    
        
        assertEquals("test ", "test", "test");
        }

    public void testProductWithSpecificImageLookUp() {

/*        Document myDoc = ProductLookUp.getProductsWithImage("5060088823156", "EAN", null, null, "All", "SmallImage");

        NodeList lOfProducts = myDoc.getElementsByTagName("products");
        assertTrue("test 1 Unexpected lOfProducts returned ", lOfProducts.getLength() == 1);

        NodeList lOfProduct = myDoc.getElementsByTagName("product");
        assertTrue("test 2 Unexpected lOfProduct returned ", lOfProduct.getLength() == 2);

        Node myNode = lOfProduct.item(0);
        NodeList myNodes = myNode.getChildNodes();

        assertTrue("test 3 Unexpected myNodes returned ", myNodes.getLength() == 9);

        assertEquals("test 4  Unexpected author returned ", "Pink Floyd", myNodes.item(0).getTextContent());

        assertEquals("test 5 Unexpected manufacturer returned ", "Music Sales", myNodes.item(1).getTextContent());

        assertEquals("test 6  Unexpected productgroup returned ", "Book", myNodes.item(2).getTextContent());

        assertEquals("test 7 Unexpected productid returned ", "5060088823156", myNodes.item(3).getTextContent());

        assertEquals("test 8 Unexpected productidtype returned ", "EAN", myNodes.item(4).getTextContent());

        assertEquals("test 9 Unexpected source returned ", "Amazon", myNodes.item(5).getTextContent());

        assertEquals("test 10  Unexpected sourceid returned ", "B002DSYNUA", myNodes.item(6).getTextContent());

        assertEquals("test 11 Unexpected title returned ", "LICK LIBRARY JAM WITH PINK FLOYD GTR CD", myNodes.item(7)
                .getTextContent());

        Node imageSetNode = myNodes.item(8);
        assertEquals("test 12 Unexpected node returned ", "ImageSet", imageSetNode.getNodeName());

        NodeList imageSetNodes = imageSetNode.getChildNodes();
        assertEquals("test 13 Unexpected nodes returned ", 1, imageSetNodes.getLength());

        Node smallImageNode = imageSetNodes.item(0);
        assertEquals("test 14 Unexpected smallImageNode returned ", "SmallImage", smallImageNode.getNodeName());

        NodeList smallIimageNodes = smallImageNode.getChildNodes();
        assertEquals("test 15 Unexpected smallIimageNodes returned ", 3, smallIimageNodes.getLength());

        Node SmallImageURL = smallIimageNodes.item(0);
        assertEquals("test 16 Unexpected SmallImageURL returned ",
                "http://ecx.images-amazon.com/images/I/511KMnyCt9L._SL75_.jpg", SmallImageURL.getTextContent());

        Node SmallImageHeight = smallIimageNodes.item(1);
        assertEquals("test 17 Unexpected SmallImageHeight returned ", "75", SmallImageHeight.getTextContent());

        Node SmallImageWidth = smallIimageNodes.item(2);
        assertEquals("test 18 Unexpected SmallImageWidth returned ", "53", SmallImageWidth.getTextContent());

        myNode = lOfProduct.item(1);
        myNodes = myNode.getChildNodes();
        assertTrue("test 19 Unexpected myNodes returned ", myNodes.getLength() == 9);

        assertEquals("test 20 Unexpected author returned ", "Ken Nash", myNodes.item(0).getTextContent());

        assertEquals("test 21 Unexpected manufacturer returned ", "", myNodes.item(1).getTextContent());

        assertEquals("test 22 Unexpected productgroup returned ", "Book", myNodes.item(2).getTextContent());

        assertEquals("test 23 Unexpected productid returned ", "5060088823156", myNodes.item(3).getTextContent());

        assertEquals("test 24 Unexpected productidtype returned ", "EAN", myNodes.item(4).getTextContent());

        assertEquals("test 25 Unexpected source returned ", "Amazon", myNodes.item(5).getTextContent());

        assertEquals("test 26 Unexpected sourceid returned ", "B0050GV3IW", myNodes.item(6).getTextContent());

        assertEquals("test 27 Unexpected title returned ", "Lick Library: Jam With Pink Floyd", myNodes.item(7)
                .getTextContent());

        imageSetNode = myNodes.item(8);
        assertEquals("test 28 Unexpected node returned ", "ImageSet", imageSetNode.getNodeName());

        imageSetNodes = imageSetNode.getChildNodes();
        assertEquals("test 29 Unexpected nodes returned ", 1, imageSetNodes.getLength());

        smallImageNode = imageSetNodes.item(0);
        assertEquals("test 30 Unexpected smallImageNode returned ", "SmallImage", smallImageNode.getNodeName());

        smallIimageNodes = smallImageNode.getChildNodes();
        assertEquals("test 31 Unexpected smallIimageNodes returned ", 3, smallIimageNodes.getLength());

        SmallImageURL = smallIimageNodes.item(0);
        assertEquals("test 32 Unexpected SmallImageURL returned ",
                "http://ecx.images-amazon.com/images/I/41jyoobZAKL._SL75_.jpg", SmallImageURL.getTextContent());

        SmallImageHeight = smallIimageNodes.item(1);
        assertEquals("test 33 Unexpected SmallImageHeight returned ", "75", SmallImageHeight.getTextContent());

        SmallImageWidth = smallIimageNodes.item(2);
        assertEquals("test 34 Unexpected SmallImageWidth returned ", "53", SmallImageWidth.getTextContent());

        System.out.println(DocumentToString(myDoc));*/
        assertEquals("test ", "test", "test");

    }
}
