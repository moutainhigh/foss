package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.deppon.foss.framework.service.IService;


/**
* @description 悟空交接单
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年4月22日 下午7:07:21
*/
@Produces(MediaType.APPLICATION_JSON)
public interface IITFWKTfrBillService extends IService {
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/addBill")
	// http://10.224.227.27:8080/foss/services/wktfrbill/addBill
	public Object addBill(String reqMsg);
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/updateBill")
	// http://10.224.227.27:8080/foss/services/wktfrbill/updateBill
	public Object updateBill(String reqMsg);
	
}
