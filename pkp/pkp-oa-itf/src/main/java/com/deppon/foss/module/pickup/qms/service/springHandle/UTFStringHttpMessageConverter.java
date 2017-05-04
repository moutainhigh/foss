package com.deppon.foss.module.pickup.qms.service.springHandle;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * 解决@ResponseBody 标签返回值编码默认ISO-8859-1，导致的乱码问题
 * @author 231434-FOSS-bieyexiong
 * @date 2015-7-15 上午8:20:14
 */
public class UTFStringHttpMessageConverter extends StringHttpMessageConverter{
	
	private static final MediaType UTF8 = new  MediaType("text","plain",Charset.forName("UTF-8"));

	@Override
	protected MediaType getDefaultContentType(String dumy) {
        return UTF8;
    }
}
