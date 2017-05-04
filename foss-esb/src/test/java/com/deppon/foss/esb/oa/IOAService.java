package com.deppon.foss.esb.oa;

import javax.jws.WebService;

import com.deppon.foss.esb.server.SampleDto;

/**
 * 对于OA的webservice，接口中不会有【Holder<ESBHeader> esbHeader】的参数；
 * 需要通过cxf拦截器来处理esb所需要的header信息；
 * 
 * @author zhengwl
 *
 */
@WebService
public interface IOAService {
	public String process(SampleDto sample);
}
