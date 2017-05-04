package com.deppon.foss.module.base.baseinfo.server.service.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class OutPhaseInterceptor extends AbstractPhaseInterceptor<Message>{
	
	private static final String ESB_RESULT_CODE = "ESB-ResultCode";
	
	public OutPhaseInterceptor() {
		super(ESB_RESULT_CODE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		Object obj = message.get("HTTP.RESPONSE");
		if (obj != null) {
			HttpServletResponse rs = (HttpServletResponse) obj;
			rs.setHeader(ESB_RESULT_CODE, "1");
		}
	}

}
