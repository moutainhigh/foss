package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;

/**
 * 快递系统新增、修改、作废长途交接单时，相关财务单据处理接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-14 20:08
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface IEcsTruckStowageService {
	
	/**
	 * 生成快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@POST
	@Path("/addTruckStowage")
	String addTruckStowage(String jsonReq);
	
	/**
	 * 生成快递长途交接单（(为了加事务控制，所以重写一个方法)）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	void addTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 修改、作废应付单校验接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-03 11:26
	 */
	@POST
	@Path("/checkPayable")
	String checkPayableIsWriteOff(String jsonReq);
	
	/**
	 * 修改快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@POST
	@Path("/modifyTruckStowage")
	String modifyTruckStowage(String jsonReq);
	
	/**
	 * 修改快递长途交接单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	void modifyTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 作废快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@POST
	@Path("/disableTruckStowage")
	String disableTruckStowage(String jsonReq);
	
	/**
	 * 作废快递长途交接单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	void disableTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);

}
