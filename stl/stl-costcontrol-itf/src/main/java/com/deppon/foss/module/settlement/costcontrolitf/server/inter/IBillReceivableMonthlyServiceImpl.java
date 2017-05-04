/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.settlement.costcontrolitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:273272,date:2016-2-17 下午4:53:05,content:TODO </p>
 * @author 273272 唐俊
 * @date 2016-2-17 下午4:53:05
 * @since
 * @version
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface IBillReceivableMonthlyServiceImpl {
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 下午4:58:21
	 * @param dto
	 * @return
	 * @see
	 */
	@POST
	@Path(value = "/billReceivableAmount")
	String queryBillReceivableAmount(String request);
}
