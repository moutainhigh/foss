package com.deppon.foss.module.base.querying.server.service;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *  Restful服务端接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-9-10 下午2:10:07,content:TODO </p>
 * @author 232607 
 * @date 2015-9-10 下午2:10:07
 * @since
 * @version
 */
public interface IRestfulService  extends Serializable{
	
	@POST
	@Path(value="/queryDeliveryConfirm")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	String queryDeliveryConfirm(String json);
	//http://localhost:8080/bse-querying-web/services/restfulService/queryDeliveryConfirm
	
	@POST
	@Path(value="/queryWayBilllTrackRecords")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	String queryWayBilllTrackRecords(String json);
	//http://localhost:8080/bse-querying-web/services/restfulService/queryWayBilllTrackRecords
	
}
