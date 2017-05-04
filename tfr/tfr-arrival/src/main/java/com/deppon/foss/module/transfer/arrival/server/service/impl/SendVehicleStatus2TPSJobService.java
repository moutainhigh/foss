package com.deppon.foss.module.transfer.arrival.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.arrival.api.server.service.ISendVehicleStatus2TPSJobService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.util.define.FossConstants;

public class SendVehicleStatus2TPSJobService implements
		ISendVehicleStatus2TPSJobService {
	
	/**
	 * 单次处理100条
	 */
	private static final int count = 100;
	
	/**
	 *查询代办job
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	/**
	 * 出发service
	 */
	private IDepartureService departureService;
	
	/**
	 * 记日志
	 */
	private ITfrCommonService tfrCommonService;

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}

	/**
	 * 
	* @description job入口方法
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.arrival.api.server.service.ISendVehicleStatus2TPSJobService#sendVehicleStatus2TPS()
	* @author 105869-foss-heyongdong
	* @update 2015年4月20日 下午7:30:31
	* @version V1.0
	 */
	@Override
	public int sendVehicleStatus2TPS() {
		TfrJobTodoQueryDto qDto = new TfrJobTodoQueryDto();
		qDto.setBusinessSceneList(new String[] {
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE,
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL });
		qDto.setBusinessGoalList(new String[] { BusinessGoalContants.BUSINESS_GOAL_TPS });
		qDto.setCount(count);

		while (true) {
			List<TfrJobTodoEntity> list = tfrJobTodoService.queryJobTodo(qDto);
			if (list == null || list.size() <= 0) {
				return 1;
			}
			for (TfrJobTodoEntity entity : list) {
				try {
					sendOneVehicleStatus2TPS(entity);
				} catch (Exception e) {
					// 记录出错日志
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizCode());
					jobProcessLogEntity.setRemark("任务执行失败！");
					jobProcessLogEntity.setExceptionInfo(e.getMessage());
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}
			}
		}
	}

	/**
	 * 
	* @description 单个处理，事务控制
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.arrival.api.server.service.ISendVehicleStatus2TPSJobService#sendOneVehicleStatus2TPS(com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年4月20日 下午7:21:19
	* @version V1.0
	 */
	@Override
	@Transactional
	public int sendOneVehicleStatus2TPS(TfrJobTodoEntity entity) {
		String truckTaskDetailID = entity.getBusinessID();
		Date bizTime = entity.getBusinessTime();
		String bizScene = entity.getBusinessScene();
		boolean result = true;
		if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
			result = departureService.synchDepartArriveInfoToTps(truckTaskDetailID,bizTime,"start");
		}else if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
			result = departureService.synchDepartArriveInfoToTps(truckTaskDetailID,bizTime,"arrive");
		}
		if(result){
			tfrJobTodoService.updateJobTodoByID(entity.getId());
		}
		return FossConstants.SUCCESS;
	}

}
