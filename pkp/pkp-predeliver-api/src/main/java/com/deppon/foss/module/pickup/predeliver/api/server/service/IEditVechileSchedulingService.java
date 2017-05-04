package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;

/** 
 * @ClassName: IEditVechileSchedulingService 
 * @Description: 车辆排班发生更改表Service 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-18 上午10:43:11 
 *  
 */
public interface IEditVechileSchedulingService extends IService {

	/**
	 * @Title: insertEditInfo
	 * @Description: 添加排班修改信息
	 * @param smallZoneCodes 小区编码(如果多条使用英文","隔开)
	 * @param vechileNo 车牌号
	 * @param editDate  更改日期
	 * @param schedulingStatus 车辆排班状态
	 * @return 返回值 大于0：添加成功  -1：参数传入有空值
	 */
	int insertEditInfo(String smallZoneCodes, String vechileNo, Date editDate, String schedulingStatus);
	
	/** 
	 * 批量添加排班修改信息
	 * @param editVechileSchedulings  车辆排班发生更改对象集合
	 * @return 返回值 大于0：添加成功  -1：参数传入有空值
	 * @author fangwenjun 237982
	 * @date 2015年12月9日 下午2:56:20 
	 */
	int insertListEditInfo(List<EditVechileSchedulingEntity> editVechileSchedulings);
	
	/**
	 * @Title: selectList
	 * @Description: 查询当天与第二天车辆排班发生更改表没被使用过的数据
	 * @return 车辆排班发生更改表对象集合
	 */
	List<EditVechileSchedulingEntity> selectEditInfoList();
	
	/**
	 * @Title: updateEditInfo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id 主键
	 * @param isUsed 是否使用过
	 * @return 受影响的行数
	 */
	int updateEditInfoIsUsed(String id, String isUsed);
	
	/**
	 * @Title: deleteEditInfo
	 * @Description: 删除当天之前的车辆更改信息
	 * @return 受影响的行数
	 */
	int deleteEditInfo();
}
