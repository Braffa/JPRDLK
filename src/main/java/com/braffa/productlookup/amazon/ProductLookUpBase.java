package com.braffa.productlookup.amazon;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class ProductLookUpBase {
	protected static final String AWS_ACCESS_KEY_ID = "AKIAJCZUWB4R35LW7MBA";
	protected static final String AWS_SECRET_KEY = "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";
	protected static final String ENDPOINT = "ecs.amazonaws.co.uk";
	protected static final String AssociateTag = "braffa-21";

	protected static Document fetchDocument(String requestUrl) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(requestUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static ProductSignedRequestsHelper getSignedRequestsHelper() {
		ProductSignedRequestsHelper helper;
		try {
			helper = ProductSignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY, AssociateTag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return helper;
	}

	protected static String signRequestWithMap(ProductSignedRequestsHelper helper,
			String itemId, String idType, String responseGroup,
			String condition, String searchIndex) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", "2009-03-31");
		params.put("Operation", "ItemLookup");

		if (itemId != null && itemId.length() > 0) {
			params.put("ItemId", itemId);
		}
		if (idType != null && idType.length() > 0) {
			params.put("IdType", idType);
		}
		if (responseGroup != null && responseGroup.length() > 0) {
			params.put("ResponseGroup", responseGroup);
		}
		if (condition != null && condition.length() > 0) {
			params.put("Condition", condition);
		}
		if (searchIndex != null && searchIndex.length() > 0) {
			params.put("SearchIndex", searchIndex);
		}

		String requestUrl = helper.sign(params);
		return requestUrl;
	}
}
