package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *  Restful服务端接口
 * @author 232607 
 * @date 2015-12-21 上午9:42:45
 * @since
 * @version
 */
public interface IRestfulService  extends Serializable{
	
	@POST
	@Path(value="/receiveSupplierInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	String receiveSupplierInfo(String json);
	//自测：http://localhost:8080/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
	//开发环境：http://10.224.68.36:8080/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
	//日常2：http://10.230.13.69/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
}
