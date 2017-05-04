package com.deppon.foss.module.pickup.qms.service.springHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class AllHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		
		if(exception != null){
			response.setHeader("ESB-ResultCode","0");
		}
		return new ModelAndView();
	}

}
