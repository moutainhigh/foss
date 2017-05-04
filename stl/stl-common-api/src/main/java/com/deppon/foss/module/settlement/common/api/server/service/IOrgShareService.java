package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;

/**
 * 
 * 理赔成本划分服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 下午2:16:52
 */
public interface IOrgShareService extends IService {

	/**
	 * 新增理赔成本划分
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:22:29
	 * @param entity
	 */
	void addOrgShare(OrgShareEntity entity);

	/**
	 * 查询成本划分 ，返回部门信息为   标杆编码
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-31 下午4:46:48
	 * @param sourceBillNo
	 * @param active
	 * @return
	 */
	List<OrgShareEntity> selectBySourceBillNo(String sourceBillNo, String active);
	

}
