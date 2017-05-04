package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 
 * 网点映射关系 service接口
 * @author 308861 
 * @date 2016-8-24 下午4:14:39
 * @since
 * @version
 */
public interface IPartnerRelationService extends IService{
	/**
	 * 
	 * 插入 
	 * @author 308861 
	 * @date 2016-8-24 下午2:18:30
	 * @param entity
	 * @return
	 * @see
	 */
	PartnerRelationEntity addPartnerRelation(PartnerRelationEntity entity);
	/**
	 * 
	 * 修改  
	 * @author 308861 
	 * @date 2016-8-24 下午2:28:53
	 * @param entity
	 * @return
	 * @see
	 */
	PartnerRelationEntity updatePartnerRelation(PartnerRelationEntity entity);
	/**
	 * 
	 * 作废  
	 * @author 308861 
	 * @date 2016-9-2 下午2:33:07
	 * @param ids
	 * @see
	 */
	void deletePartnerRelation(List<String> ids);
		
	/**
	 * 
	 * 精确查询  (查询条件 一级二级网点合伙人名称   时间段)  
	 * @author 308861 
	 * @date 2016-8-24 下午2:32:14
	 * @param entity
	 * @return
	 * @see
	 */
	List<PartnerRelationEntity> queryPartnerRelationByEntity(PartnerRelationEntity entity,int start,int limit);
	/**
	 * 
	 * 统计 
	 * @author 308861 
	 * @date 2016-8-25 上午9:31:20
	 * @param entity
	 * @return
	 * @see
	 */
	long queryPartnerRelationByEntityCount(PartnerRelationEntity entity);
	/**
	 * 
	 * 根据code查询网点所属子公司 
	 * @author 308861 
	 * @date 2016-8-25 下午4:54:37
	 * @param code
	 * @return
	 * @see
	 */
	PartnerRelationEntity getSubCompanyNameByCode(String code);
	/**
	 * 
	 * 根据二级code查询一级网点编码  
	 * @author 308861 
	 * @date 2016-9-2 下午5:50:01
	 * @param twoCode
	 * @return
	 * @see
	 */
	SaleDepartmentEntity oneEntityByTwoCode(String twoCode);
	/**
	 * 
	 * 根据一级code查询二级网点编码 
	 * @author 308861 
	 * @date 2016-9-2 下午5:50:26
	 * @param oneCode
	 * @return
	 * @see
	 */
	List<SaleDepartmentEntity> twoEntiyByOne(String oneCode);
}
