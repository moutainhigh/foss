package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface IEcsOutWarehouseExceptionService {
	
	/**
	 * 异常出库红冲操作
	 * @author foss-231434-bieyexiong
	 * @date 2016-04-26 9:19
	 */
	@POST
	@Path("/outWarehouseException")
	String outWarehouseException(String jsonReq);
	
	/**
	 * 异常出库红冲操作((为了加事务控制，所以重写一个方法))
	 * @author foss-231434-bieyexiong
	 * @date 2016-04-26 9:19
	 */
	void outWarehouseException(String waybillNo,String expType,CurrentInfo cInfo);
	
}
