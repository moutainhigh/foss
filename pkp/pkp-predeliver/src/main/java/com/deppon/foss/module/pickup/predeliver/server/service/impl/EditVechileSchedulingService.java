package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;
import com.deppon.foss.util.UUIDUtils;

/** 
 * @ClassName: EditVechileSchedulingService 
 * @Description: 车辆排班发生更改表Service 实现 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-18 下午3:28:42 
 *  
 */
public class EditVechileSchedulingService implements
		IEditVechileSchedulingService {
	
	/**
	 * 车辆排班发生更改表Dao
	 */
	private IEditVechileSchedulingDao editVechileSchedulingDao;
 
	/**
	 * 设置editVechileSchedulingDao
	 * @param editVechileSchedulingDao 要设置的editVechileSchedulingDao
	 */
	@Resource
	public void setEditVechileSchedulingDao(
			IEditVechileSchedulingDao editVechileSchedulingDao) {
		this.editVechileSchedulingDao = editVechileSchedulingDao;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService#insertOneEditInfo(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public int insertEditInfo(String smallZoneCodes, String vechileNo,
			Date editDate, String schedulingStatus) {
		// 判断传入小区编码是否为空
		if (smallZoneCodes == null || "".equals(smallZoneCodes.trim())) {
			return -1;
		}
		// 判断车牌号是否为空
		if (vechileNo == null || "".equals(vechileNo.trim())) {
			return -1;
		}
		// 判断修改日期是否为空
		if (editDate == null) {
			return -1;
		}
		// 判断排班状态是否为空
		if (schedulingStatus == null || "".equals(schedulingStatus.trim())) {
			return -1;
		}
		// 定义车辆排班发生更改表实体对象
		EditVechileSchedulingEntity editVechileScheduling = null;
		// 获得小区编码数组
		String[] smallZoneCodeArray = smallZoneCodes.split(",");
		
		List<EditVechileSchedulingEntity> editVechileSchedulings = new ArrayList<EditVechileSchedulingEntity>();
		
		for (int i = 0; i < smallZoneCodeArray.length; i++) {
			// 创建车辆排班发生更改表实体对象
			editVechileScheduling = new EditVechileSchedulingEntity();
			// 设置是否使用过
			editVechileScheduling.setIsUsed("N");
			// 设置车牌号
			editVechileScheduling.setVechileNo(vechileNo);
			// 设置修改日期
			editVechileScheduling.setEditDate(editDate);
			// 设置排班状态
			editVechileScheduling.setSchedulingStatus(schedulingStatus);
			// 设置主键
			editVechileScheduling.setId(UUIDUtils.getUUID());
			// 设置小区编码
			editVechileScheduling.setSmallZoneCode(smallZoneCodeArray[i]);
			
			editVechileSchedulings.add(editVechileScheduling);
		}
		int result = editVechileSchedulingDao.insertList(editVechileSchedulings);
		editVechileSchedulings = null;
		return result;
	}
	

	@Override
	public int insertListEditInfo(List<EditVechileSchedulingEntity> editVechileSchedulings) {
		
		if (editVechileSchedulings == null || editVechileSchedulings.size() == 0) {
			return -1;
		}
		
		return editVechileSchedulingDao.insertList(editVechileSchedulings);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService#selectList()
	 */
	@Override
	public List<EditVechileSchedulingEntity> selectEditInfoList() {
		return editVechileSchedulingDao.selectList();
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService#updateEditInfoIsUsed(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateEditInfoIsUsed(String id, String isUsed) {
		// 判断Id是否为空
		if (id == null || "".equals(id.trim())) {
			return 0;
		}
		// 判断isUsed是否为空
		if (isUsed == null || "".equals(isUsed.trim())) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("isUsed", isUsed);
		return editVechileSchedulingDao.updateIsUsed(paramMap);
	}

	@Override
	public int deleteEditInfo() {
		return editVechileSchedulingDao.deleteByEditDate();
	}
}
