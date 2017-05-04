package com.deppon.foss.esb.common;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.server.SampleDto;
import com.deppon.foss.esb.util.ESBHeaderUtil;

public class CommonServiceImpl implements ICommonService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommonServiceImpl.class);
	@Override
	public String process(SampleDto sample, Holder<ESBHeader> esbHeader) {
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		
		LOGGER.info(sample.getCode());
		
		// do your business
		
		return sample.getId();
	}

}
