package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * TODO(合伙人查询公共选择器service)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Foss-352676-yuahb,date:2016-9-21 上午9:48:44,content:TODO
 * </p>
 * 
 * @author Foss-352676-YUANHB
 * @date 2016-9-21 上午9:48:44
 * @since
 * @version
 */
public interface ICommonPartnerDeptService {
	/**
	 * TODO(查询合伙人公共选择器)
	 * @author Foss-352676-YUANHB
	 * @date 2016-9-21 上午9:51:05
	 * @param saleDepartmentEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<SaleDepartmentEntity> queryPartnerDeptsByCondition(
			SaleDepartmentEntity saleDepartmentEntity, int limit, int start);

	/**
	 * TODO(查询合伙人公共选择器数量)
	 * @author Foss-352676-YUANHB
	 * @date 2016-9-21 上午9:51:09
	 * @param saleDepartmentEntity
	 * @return
	 * @see
	 */
	Long queryPartnerDeptDeptsCount(SaleDepartmentEntity saleDepartmentEntity);

}
