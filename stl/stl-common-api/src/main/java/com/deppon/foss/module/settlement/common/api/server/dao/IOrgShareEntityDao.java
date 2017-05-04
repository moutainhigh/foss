package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;

/**
 * 
 * 理赔成本划分实体DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 上午11:48:01
 */
public interface IOrgShareEntityDao {

	/**
	 * 新增成本划分数据
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:03:15
	 * @param entity 理赔成本划分实体
	 * @return
	 */
	int add(OrgShareEntity entity);

	/**
	 * 查询成本划分 ，返回部门信息为   标杆编码
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-31 下午4:31:12
	 * @param sourceNO
	 * @param active
	 * @return
	 */
	List<OrgShareEntity> selectBySourceBillNo(String sourceNo, String active);

}
