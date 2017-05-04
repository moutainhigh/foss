package com.deppon.foss.module.transfer.common.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.OrgDto;

/**
 * FOSS-TFR与CRM系统交互
 * @author 105795-foss-wqh
 * @date 2014-10-29 上午11:00:05
 */
public interface IFOSSToCRMService extends IService{

	//
	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:匹配责任部门 
	 * @parm:waybillNo
	 * @return:List<OrgDto>
	 * @author:wqh
	 * @date:2014年10月29日上午10:10:26
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/matchResponbilityOrg")
	public List<OrgDto> matchResponbilityOrg(@QueryParam("waybillNo") String waybillNo);
	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:通过运单号，卸车部门找所在的 卸车任务编号
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
	public List<String> queryUnloadTaskNoByWaybillNo(String waybillNo,String unloadOrgCode);

	/**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:通过卸车任务统计卸车任务下的总重量 
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
    public BigDecimal queryTotalWeightByUnloadTaskNo(String unloadTaskId); 
	
    /**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:查询运单最后一件货的扫描时间 
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
    public Date queryUnloadLastScanTimeByWaybillNo(String waybillNo,String unloadTaskId,String unloadOrgCode);
    
   
    /**
	 * @com.deppon.foss.module.transfer.common.api.server.service
	 * @desc:判断车辆归属性
	 * @parm:waybillNo
	 * @return:void
	 * @author:wqh
	 * @date:2014年11月10日上午10:10:26
	 */
   	public List<String> queryBelongVehicleByVehicleNo(String vehicleNo);
    
}
