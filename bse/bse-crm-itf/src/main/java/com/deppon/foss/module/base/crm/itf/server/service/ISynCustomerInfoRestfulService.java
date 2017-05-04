package com.deppon.foss.module.base.crm.itf.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** 
* @author  FOSS综合-261997-css 
* @date 创建时间：2015-7-01 下午3:55:36 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface ISynCustomerInfoRestfulService {
	@POST
	@Path("/queryCrmInfo")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String queryCrmInfo(String queryCrmInfo);	
}
