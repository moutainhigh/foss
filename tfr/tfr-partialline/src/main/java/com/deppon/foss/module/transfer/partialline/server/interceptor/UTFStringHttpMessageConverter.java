package com.deppon.foss.module.transfer.partialline.server.interceptor;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * 解决@ResponseBody 标签返回值编码默认ISO-8859-1，导致的乱码问题
 * @author 269701-FOSS-lln
 * @date 2015-12-23 上午8:20:14
 */
public class UTFStringHttpMessageConverter extends StringHttpMessageConverter{
	
	private static final MediaType UTF8 = new  MediaType("text","plain",Charset.forName("UTF-8"));

	@Override
	protected MediaType getDefaultContentType(String dumy) {
        return UTF8;
    }
}
