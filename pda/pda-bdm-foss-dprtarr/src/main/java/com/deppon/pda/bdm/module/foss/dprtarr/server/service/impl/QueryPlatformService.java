package com.deppon.pda.bdm.module.foss.dprtarr.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDApreplatformEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DprtarrConstnat;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.PlatformResultEntity;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.QueryPlatformEntity;

/**   
 * @ClassName QueryPlatformService  
 * @Description 获取预分配月台功能
 * @author  092038 张贞献  q
 * @date 2015-5-29    
 */ 
public class QueryPlatformService implements
IBusinessService<List<PlatformResultEntity>, QueryPlatformEntity> {
	private static final Logger LOG = Logger.getLogger(QueryPlatformService.class);
	
	private IPDAUnloadTaskService pdaUnloadTaskService;
	
	
	
	@Override
	public QueryPlatformEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QueryPlatformEntity queryPlatformEntity = JsonUtil.parseJsonToObject(
				QueryPlatformEntity.class, asyncMsg.getContent());
		return queryPlatformEntity;
	}

	@Override
	public List<PlatformResultEntity> service(AsyncMsg asyncMsg, QueryPlatformEntity param)
			throws PdaBusiException {
	
		 List<PlatformResultEntity>  results =new ArrayList<PlatformResultEntity>();
		 List<PDApreplatformEntity>  res = null;
		 
		 //校验字段为空性
		 validate(param);
		 
		try {
			long startTime = System.currentTimeMillis();
			 /* @desc 提供给PDA 查询待卸车辆预分配月台情况
			 * @param currOrgCode 当前部门编码
			 * @param vehicleNo 车牌号  (非必填)
			 * @param platformNo 月台号(非必填)
			 * @param businessType 业务类型(必填)  长途:LONG_DISTANCE ,短途:SHORT_DISTANCE,接送货：DIVISION
			 * @param enterTfrCenterType 入场情况(必填) 已入场 IN,未入场 OUT
			 */
			res = pdaUnloadTaskService.queryPreplatformNo(
					asyncMsg.getDeptCode(), 
					param.getTruckCode(), 
					param.getPlatformNo(),
					param.getTransType(),
					param.getIsInOrOut());
						
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]获取预分配月台功能消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.info("获取预分配月台功能服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		if(res == null){
			return null;
		}
		PlatformResultEntity platformResult = null;
		for(PDApreplatformEntity entity : res){
		
			platformResult = new PlatformResultEntity();
			//出发部门code
			platformResult.setDepartOrgCode(entity.getDepartOrgCode());
			//出发部门name
			platformResult.setDepartOrgName(entity.getDepartOrgName());
			//月台号
			platformResult.setPlatformNo(entity.getPlatformNo());
			//车牌号
			platformResult.setVehicleNo(entity.getVehicleNo());
			//到达时间
			platformResult.setArriveTime(entity.getArriveTime());
			
			results.add(platformResult);
		}
					
		return results;
	}

	@Override
	public String getOperType() {
		
		return DprtarrConstnat.OPER_TYPE_QRY_PLATFORM_NO.VERSION;
	}

	@Override
	public boolean isAsync() {
		//是否异步
		return false;
	}

	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	
	private void validate(QueryPlatformEntity param) {
		//上传实体是否为空
		Argument.notNull(param, "QueryPlatformEntity");
		Argument.hasText(param.getIsInOrOut(), "QueryPlatformEntity.isInOrOut");
		Argument.hasText(param.getTransType(),"QueryPlatformEntity.transType");

	}
	

}
