package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;

/** 
 * @ClassName: HandoverBillMatchCommunityService 
 * @Description: 已交单VoService  
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-4 下午5:39:00 
 *  
 */
public class HandoverBillVoService implements
		IHandoverBillVoService {

	/**
	 * 交单匹配小区Dao
	 */
	private IHandoverBillVoDao handoverBillVoDao;
	
	private IEditVechileSchedulingService editVechileSchedulingService;

	/**
	 * 设置editVechileSchedulingService
	 * @param editVechileSchedulingService 要设置的editVechileSchedulingService
	 */
	@Resource
	public void setEditVechileSchedulingService(
			IEditVechileSchedulingService editVechileSchedulingService) {
		this.editVechileSchedulingService = editVechileSchedulingService;
	}

	/**
	 * 设置handoverBillVoDao
	 * @param handoverBillVoDao 要设置的handoverBillVoDao
	 */
	@Resource
	public void setHandoverBillVoDao(IHandoverBillVoDao handoverBillVoDao) {
		this.handoverBillVoDao = handoverBillVoDao;
	}

	/**
	 * @Title: updateHandoverBill
	 * @Description: 更新交单表
	 * @param updateParamMap 传入参数
	 * @return 受影响的行数
	 */
	@Override
	public int updateHandoverBill(Map<String, Object> updateParamMap) {
		// 判断是否为空 或 传入参数的长度大于1
		if (updateParamMap == null || updateParamMap.size() <= 1) {
			return 0;
		}
		// 判断参数id不为空
		if (updateParamMap.get("id") == null || "".equals(updateParamMap.get("id").toString().trim())) {
			return 0;
		}
		// 返回结果
		return handoverBillVoDao.updateResidence(updateParamMap);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService#selectHBMatchCommunityList()
	 */
	@Override
	public List<HandoverBillVo> selectHBMatchCommunityList() {
		return handoverBillVoDao.selectList();
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService#updateHandoverBillIsUsed(java.util.Map)
	 */
	@Override
	public int updateHandoverBillIsUsed(String id, String isUsed) {
		// 判断Id是否为空
		if (id == null || "".equals(id.trim())) {
			return 0;
		}
		// 判断使用状态是否为空
		if (isUsed == null || "".equals(isUsed.trim())) {
			return 0;
		}
		// 返回结果
		return handoverBillVoDao.updateIsUsed(id, isUsed);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService#updateHandoverBillVehicle(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public int updateHandoverBillVehicle(String smallZoneCode,
			String vechileNo, Date preDeliverDate, String isVehicleScheduling) {
		// 判断传入小区编码是否为空
		if (smallZoneCode == null || "".equals(smallZoneCode.trim())) {
			return -1;
		}
		// 判断车牌号是否为空
		if (vechileNo == null || "".equals(vechileNo.trim())) {
			return -1;
		}
		// 判断修改日期是否为空
		if (preDeliverDate == null) {
			return -1;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("smallZoneCode", smallZoneCode);
		paramMap.put("vechileNo",  vechileNo);
		paramMap.put("preDeliverDate", preDeliverDate);
		paramMap.put("isVehicleScheduling", isVehicleScheduling);
		
		List<String> waybillNos = handoverBillVoDao.selectBySmallZone(paramMap);
		
		if(waybillNos != null && waybillNos.size() > 0) {
			paramMap.put("waybillNos", waybillNos);
		}
		
		return handoverBillVoDao.updateVehicle(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService#preprocess()
	 */
	@Override
	public void preprocess() {
		// 获取车辆排班变更信息集合
		List<EditVechileSchedulingEntity> selectEditInfoList = editVechileSchedulingService.selectEditInfoList();
		if (selectEditInfoList != null) {
			for (int i = 0, size = selectEditInfoList.size(); i < size; i++) {
				// 获取集合中的每个信息
				EditVechileSchedulingEntity editVechile = selectEditInfoList.get(i);
				// 修改数据库中的使用状态
				editVechileSchedulingService.updateEditInfoIsUsed(editVechile.getId(), "Y");
				// 修改交单表里的车辆
				updateHandoverBillVehicle(editVechile.getSmallZoneCode(),editVechile.getVechileNo(),editVechile.getEditDate(), editVechile.getSchedulingStatus());
			}
		}
		editVechileSchedulingService.deleteEditInfo();
	}
	
	

}
