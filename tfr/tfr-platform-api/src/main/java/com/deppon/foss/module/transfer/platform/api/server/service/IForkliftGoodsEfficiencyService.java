package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;

/**
 * 电叉分货效率 Service
 * @author 200978
 * 2015-2-5
 */
public interface IForkliftGoodsEfficiencyService extends IService {

	/**
	 * 计算电叉分货效率   JOB主方法
	 * @Author 200978
	 * 2015-2-5
	 */
	void calculateForkliftGoodsEfficiency();
	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryParentTfrCtrCode(String code);
	
	/**
	 * 查询给定部门code所对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryOperationDeptCode(String code);
	
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
	 * 电叉分货效率   叉车司机所属部门 总条数
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
