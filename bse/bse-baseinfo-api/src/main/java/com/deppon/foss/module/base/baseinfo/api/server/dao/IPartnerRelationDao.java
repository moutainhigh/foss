package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;

/**
 * 
 * 网点映射关系
 * @author 308861 
 * @date 2016-8-24 下午2:16:59
 * @since
 * @version
 */
public interface IPartnerRelationDao {
	
	/**
	 * 
	 * 插入 
	 * @author 308861 
	 * @date 2016-9-1 下午9:03:52
	 * @param entity
	 * @return
	 * @see
	 */
	int addPartnerRelation(PartnerRelationEntity entity);
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2016-9-2 下午2:25:00
	 * @param entity
	 * @return
	 * @see
	 */
	int deletePartnerRelation(PartnerRelationEntity entity);
		
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
	 * @date 2016-8-24 下午3:46:54
	 * @param entity
	 * @return
	 * @see
	 */
	long queryPartnerRelationByEntityCount(PartnerRelationEntity entity);
	/**
	 * 
	 * 根据code查询网点所属子公司 
	 * @author 308861 
	 * @date 2016-8-25 下午3:24:55
	 * @param code
	 * @return
	 * @see
	 */
	PartnerRelationEntity getSubCompanyNameByCode(String code);
	
	/**
	 * 
	 * 根据id查询网点映射关系 
	 * @author 308861 
	 * @date 2016-8-29 下午2:18:23
	 * @param id
	 * @return
	 * @see
	 */
	PartnerRelationEntity getEntityById(String id);
	
	/**
	 * 
	 * 根据一级二级网点code查找是否存在此网点映射关系 
	 * @author 308861 
	 * @date 2016-8-31 下午8:08:23
	 * @param entity
	 * @return
	 * @see
	 */
	PartnerRelationEntity queryByCode(PartnerRelationEntity entity);
	/**
	 * 
	 * 根据code查询标杆编码 
	 * @author 308861 
	 * @date 2016-9-3 上午8:37:31
	 * @param code
	 * @return
	 * @see
	 */
	String unifideByCode(String code);
	/**
	 * 
	 * 根据二级code查询一级网点 
	 * @author 308861 
	 * @date 2016-9-2 下午5:19:27
	 * @param twoCode
	 * @return
	 * @see
	 */
	String oneCodeByTwo(String twoCode);
	
	/**
	 * 
	 * 根据一级code查询二级网点
	 * @author 308861 
	 * @date 2016-9-2 下午5:19:35
	 * @param oneCode
	 * @return
	 * @see
	 */
	List<String> twoCodeByOne(String oneCode);
}
