package com.deppon.foss.module.pickup.qms.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * FOSS对接QMS，重大货物异常处理
 * @author 231434-FOSS-bieyexiong
 * @date 2015-7-14 上午8:20:14
 */
@RequestMapping("v1/foss/grandGoodsAbnormalService")
public interface IGrandGoodsAbnormalHandleService {
	
	/**
	 * 重大货物异常处理
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-7-14 上午8:26:35
 	 */
	@RequestMapping(value="/grandAbnormalHandle",method=RequestMethod.POST)
	public String grandGoodAbnormalHandle(HttpServletResponse servletResponse,String grandGoodAbnormalJson);
	
}
