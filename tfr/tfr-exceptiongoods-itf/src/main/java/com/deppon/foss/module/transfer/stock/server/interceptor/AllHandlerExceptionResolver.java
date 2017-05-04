package com.deppon.foss.module.transfer.stock.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class AllHandlerExceptionResolver implements HandlerExceptionResolver  {

	private static final String ESB_RESULT_CODE = "ESB-ResultCode";

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (ex != null) {
			response.setHeader(ESB_RESULT_CODE, "0");
		}
		return new ModelAndView();
	}


}
