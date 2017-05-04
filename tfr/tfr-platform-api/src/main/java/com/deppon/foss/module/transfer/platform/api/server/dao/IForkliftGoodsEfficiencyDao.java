package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;


/**
 * 电叉分货效率 DAO
 * @author 200978
 * 2015-2-5
 */
public interface IForkliftGoodsEfficiencyDao {

	/**
	 * 查询所有有待叉区的非分部外场
	 * @Author 200978
	 * 2015-2-5
	 * @return
	 */
	List<ForkliftEffEntity> queryTransferCenterContainForklift();
	
	/**
	 * 执行存储过程PRO_FORKLIFT_GOODS_EFFICIENCY   开始统计
	 * @Author 200978
	 * 2015-2-5
	 * @param staDate
	 * @param staMonth
	 * @param tfrCode
	 * @param tfrName
	 */
	void calculateForkliftGoodsEfficiencyPerTfr(Date staDate,int staMonth,String tfrCode,String tfrName);
	
	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	ForkliftEffEntity queryOperationDeptCodeByCurrentCode(String code);
	
	/***
	 * 查询电叉分货效率  转运场
	 * @Author 200978
	 * 2015-2-7
	 * @param forkliftEffEntity
	 * @return
	 */
	List<ForkliftEffEntity> queryForkliftGoodsEfficiencyOfTfr(ForkliftEffEntity forkliftEffEntity);
	
	/***
	 * 电叉分货效率   叉车司机所属部门
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	List<ForkliftDriverOrgEffEntity> queryForkliftGoodsEfficiencyOfOrg(ForkliftDriverEffEntity forkliftDriverEffEntity,int start,int limit);
	
	/***
	 * 电叉分货效率   叉车司机所属部门 count
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	Long queryForkliftGoodsEfficiencyOfOrgCount(ForkliftDriverEffEntity forkliftDriverEffEntity);
	
	/**
	 * 电叉分货效率  叉车司机
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	List<ForkliftDriverEffEntity> queryForkliftGoodsEfficiencyOfDriver(ForkliftDriverEffEntity forkliftDriverEffEntity);
	
	/**
	 * 查询待叉区停留时长占比
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	List<ForkliftGoodsStayDurationDto> queryForkliftStayDuration(ForkliftEffEntity forkliftEffEntity);
	
}
