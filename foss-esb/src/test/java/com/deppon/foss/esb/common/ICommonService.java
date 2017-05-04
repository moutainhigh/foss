package com.deppon.foss.esb.common;

import javax.jws.WebService;
import javax.xml.ws.Holder;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.server.SampleDto;

@WebService
public interface ICommonService {
	public String process(SampleDto sample, Holder<ESBHeader> esbHeader);
}
