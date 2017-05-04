package com.deppon.foss.module.settlement.ecsitf.server.rs;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

/**
 * 解决@ResponseBody 标签返回值编码默认ISO-8859-1，导致的乱码问题
 * 
 * @author 231434-FOSS-bieyexiong
 * @date 2015-7-15 上午8:20:14
 */
public class UTFStringHttpMessageConverter extends StringHttpMessageConverter {

	private static final MediaType UTF8 = new MediaType("text", "plain",
			Charset.forName("UTF-8"));

	private boolean writeAcceptCharset = true;

	@Override
	protected MediaType getDefaultContentType(String dumy) {
		return UTF8;
	}

	protected List getAcceptedCharsets() {
		return Arrays.asList(UTF8.getCharSet());
	}

	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException {
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = UTF8.getCharSet();
		FileCopyUtils.copy(s,
				new OutputStreamWriter(outputMessage.getBody(), charset));
	}

	public boolean isWriteAcceptCharset() {
		return writeAcceptCharset;
	}

	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}
}
