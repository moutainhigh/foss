package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IDepartVehiclePlanInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.DepartVehiclePlanInfoQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartPlanInfoDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * 获取发车计划信息
 * @author 332209
 * @date 2012-12-4 下午5:25:01
 */
public class DepartVehiclePlanInfoService implements  IDepartVehiclePlanInfoService {
	
	// 日志
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartVehiclePlanInfoService.class);

	// 获取发车计划信息DAO
	private ITruckDepartPlanDetailDao truckDepartPlanDetailDao;
	
	public ITruckDepartPlanDetailDao getTruckDepartPlanDetailDao() {
		return truckDepartPlanDetailDao;
	}

	// 线路服务类,用于取线路实体信息
	private ILineService lineService;
	
	// 线路服务类,用于取线路实体信息
	private IExpressLineService expresslineService ;
	
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setTruckDepartPlanDetailDao(
			ITruckDepartPlanDetailDao truckDepartPlanDetailDao) {
		this.truckDepartPlanDetailDao = truckDepartPlanDetailDao;
	}



	/**
	 * 获取发车计划信息
	 */
	@Override
	public DepartPlanInfoDetailDto queryDepartVehiclePlanInfo(
			DepartVehiclePlanInfoQueryParmEntity entity) {

		LOGGER.debug("start DepartVehiclePlanInfoService");
		// 发车计划明细DTO
		TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
		
		//查询条件DTO封装
		// 车牌
		detailDto.setVehicleNo(entity.getVehicleNo());
		// 发车日期
		detailDto.setDepartDate(entity.getDepartDate());
		// 出发部门
		detailDto.setOrigOrgCode(entity.getOrigOrgCode());
		// 到达部门
		detailDto.setDestOrgCode(entity.getDestOrgCode());
		// 可用
		detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		//判断长途还是短途
		if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(entity.getPlanType())) {
			// 长途下发
			detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
			// 长途
			detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
		} else {
			// 短途新建
			detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_NEW);
			// 短途
			detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		}

		// 未出发
		detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		detailDto.setList(list);
		
		TruckDepartPlanDetailDto planDetail = null;
		
		//根据车牌号查询发车计划
		planDetail = truckDepartPlanDetailDao.queryTruckDepartPlanInfoDetail(detailDto);
		//对象不可能为"" 352203
		if (planDetail == null) {
			//根据挂牌号查询发车计划
			planDetail = truckDepartPlanDetailDao.queryDepartPlanDetailByTrailerVehicleNo(detailDto);
		}
		
		if (planDetail == null) {
			//根据货柜号查询
			planDetail = truckDepartPlanDetailDao.queryLatestTruckDepartPlanDetailByContainerNo(detailDto);
		}
		
		//根据交接时间查询不到，把交接时间设置为空，进行查询
		if (planDetail == null) {
			detailDto.setDepartDate(null);
			//根据车牌号查询
			planDetail = truckDepartPlanDetailDao.queryLatestTruckDepartPlanDetail(detailDto);
			//根据挂牌号查询
			if (planDetail == null) {
				planDetail = truckDepartPlanDetailDao.queryDepartPlanDetailByTrailerVehicleNo(detailDto);
			}
			//根据货柜号查询
			if (planDetail == null) {
				planDetail = truckDepartPlanDetailDao.queryLatestTruckDepartPlanDetailByContainerNo(detailDto);
			}
		}
		
		String virtualCode = null;
		
		if (planDetail == null) {
			return null;
		}
		//获取线路虚拟编码
		virtualCode = planDetail.getLineNo();
		//调用综合接口，获取线路实体信息
		LineEntity lineEntity = lineService.queryLineByVirtualCode(virtualCode);
		ExpressLineEntity explineEntity = expresslineService.queryLineByVirtualCode(virtualCode); 
			
		DepartPlanInfoDetailDto dtoInfo = new DepartPlanInfoDetailDto();

		//如果运输性质不为空，则根据不同的运输性质获取不同的运行时数
		if(StringUtils.isNotBlank(entity.getTransProperty())){
			if(StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE, entity.getTransProperty())){
				if (lineEntity != null) {
					if(lineEntity.getCommonAging() != null){
						//运行时数读取普货时效
						dtoInfo.setRunHours(new BigDecimal(lineEntity.getCommonAging()).divide(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000),ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
					}
				}
				if (explineEntity != null) {
					if(explineEntity.getCommonAging() != null){
						//运行时数读取普货时效
						dtoInfo.setRunHours(new BigDecimal(explineEntity.getCommonAging()).divide(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000),ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
					}
				}
			}else if(StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN, entity.getTransProperty())){
				if (lineEntity != null) {
					if(lineEntity.getFastAging() != null){
						//运行时数读取卡货时效
						dtoInfo.setRunHours(new BigDecimal(lineEntity.getFastAging()).divide(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000),ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
					}
				}
				if (explineEntity != null) {
					if(explineEntity.getFastAging() != null){
						//运行时数读取卡货时效
						dtoInfo.setRunHours(new BigDecimal(explineEntity.getFastAging()).divide(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000),ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
					}
				}
			}
		}
		//司机code
		dtoInfo.setDriverCode(planDetail.getDriverCode1());
		//司机姓名
		dtoInfo.setDriverName(planDetail.getDriverName1());
		//司机电话
		dtoInfo.setDriverPhone(planDetail.getDriverPhone1());
		//卡车班次
		dtoInfo.setShift(planDetail.getFrequencyNo());
		//货柜编号
		dtoInfo.setContainerNo(planDetail.getContainerNo());
		//车型
		dtoInfo.setVechileModel(planDetail.getTruckModel());
		//挂牌号
		dtoInfo.setBrandNo(planDetail.getTrailerVehicleNo());
		//线路虚拟编码
		dtoInfo.setLineVirtualNo(virtualCode);
		
		return dtoInfo;
	}
	
}