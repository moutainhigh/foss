package com.deppon.foss.module.transfer.partialline.api.shared.define;


import org.apache.commons.httpclient.methods.PostMethod;

import com.deppon.foss.util.UUIDUtils;


/**
 * 
 * @author 130134
 *
 */
public class RestFullHeaderFactory {
	public final static String VERSION = "version";
	public final static String BUSINESSID = "businessId";
	public final static String BUSINESSDESC1 = "businessDesc1";
	public final static String BUSINESSDESC2 = "businessDesc2";
	public final static String BUSINESSDESC3 = "businessDesc3";
	public final static String REQUESTID = "requestId";
	public final static String RESPONSEID = "responseId";
	public final static String SOURCESYSTEM = "sourceSystem";
	public final static String TARGETSYSTEM = "targetSystem";
	public final static String ESBSERVICECODE = "esbServiceCode";
	public final static String BACKSERVICECODE = "backServiceCode";
	public final static String MESSAGEFORMAT = "messageFormat";
	public final static String EXCHANGEPATTERN = "exchangePattern";
	public final static String RESULTCODE = "resultCode";
	public final static String ACCEPT = "accept";
	public final static String CONTENTTYPE = "Content-Type";
	 
	/**
	 * 
	 * @param url
	 * @param serviceCode
	 * @return
	 */
	public PostMethod getPostMethod(String url,String serviceCode){
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader(VERSION,"0.1");
		postMethod.setRequestHeader(ESBSERVICECODE, serviceCode);
		postMethod.setRequestHeader(REQUESTID, UUIDUtils.getUUID());
		postMethod.setRequestHeader(BUSINESSID, UUIDUtils.getUUID());
		postMethod.setRequestHeader(MESSAGEFORMAT, "JSON");
		postMethod.setRequestHeader(EXCHANGEPATTERN, "1");
		postMethod.setRequestHeader(SOURCESYSTEM, "WDGH");
		postMethod.setRequestHeader(ACCEPT, "text/html,application/json");
		postMethod.addRequestHeader(CONTENTTYPE,"application/json");
		return postMethod;
		
	}
	
}
