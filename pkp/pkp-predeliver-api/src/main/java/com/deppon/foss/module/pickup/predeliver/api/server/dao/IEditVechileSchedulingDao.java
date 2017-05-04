package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;

/** 
 * @ClassName: IEditVechileSchedulingDao 
 * @Description: 车辆排班发生更改表Dao 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-18 上午10:43:23 
 *  
 */
public interface IEditVechileSchedulingDao {
	
	/**
	 * @Title: insertOne
	 * @Description: 添加车辆排班发生更改数据
	 * @param editVechileScheduling 车辆排班发生更改表对对象
	 * @return 受影响的行数
	 */
	int insertOne(EditVechileSchedulingEntity editVechileScheduling);
	
	/** 
	 * 批量添加车辆排班发生更改数据
	 * @param editVechileSchedulings 车辆排班发生更改表对对象集合
	 * @return 受影响的行数
	 * @author fangwenjun 237982
	 * @date 2015年11月13日 下午4:29:20 
	 */
	int insertList(List<EditVechileSchedulingEntity> editVechileSchedulings);

	/**
	 * @Title: selectList
	 * @Description: 查询当天与第二天车辆排班发生更改表没被使用过的数据
	 * @return 车辆排班发生更改表对象集合
	 */
	List<EditVechileSchedulingEntity> selectList();
	
	/**
	 * @Title: updateIsUsed
	 * @Description: 修改使用状态
	 * @param paramMap 传入参数
	 * @return 受影响的行数
	 */
	int updateIsUsed(Map<String,Object> paramMap);
	
	/**
	 * @Title: deleteByEditDate
	 * @Description: 删除修改日期为当天之前的信息
	 * @return 受影响的行数
	 */
	int deleteByEditDate();
}
