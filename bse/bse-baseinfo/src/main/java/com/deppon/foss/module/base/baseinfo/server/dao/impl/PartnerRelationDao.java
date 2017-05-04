package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerRelationDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
/**
 * 
 * 网点映射关系Dao实现
 * @author 308861 
 * @date 2016-2-28 下午2:36:20
 * @since
 * @version
 */
public class PartnerRelationDao extends SqlSessionDaoSupport implements IPartnerRelationDao{
    /**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.partnerRelation.";
	
    /**
     * 
     * 新增
     * @author 308861 
     * @date 2016-9-1 下午9:02:40
     * @param entity
     * @return 
     */
    @Override
	public int addPartnerRelation(PartnerRelationEntity entity) {
		return getSqlSession().insert(NAMESPACE + "addPartnerRelation", entity);
	}
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2016-9-2 下午2:25:18
	 * @param entity
	 * @return 
	 */
	@Override
	public int deletePartnerRelation(
			PartnerRelationEntity entity) {
		return getSqlSession().update(NAMESPACE + "deletePartnerRelation", entity);
	}
	
	/**
	 * 
	 * 根据id查询是否存在有效的网点映射关系 
	 * @author 308861 
	 * @date 2016-8-29 下午2:26:38
	 * @param id
	 * @return 
	 */
	@Override
	public PartnerRelationEntity getEntityById(String id) {
		return (PartnerRelationEntity)getSqlSession().selectOne(NAMESPACE+"getEntityById",id);
	}
	
	/**
	 * 
	 * 查询 （条件 ：一级二级网点名称   时间段） 
	 * @author 308861 
	 * @date 2016-8-24 下午2:59:45
	 * @param entity
	 * @return 
	 */
	@Override
	public List<PartnerRelationEntity> queryPartnerRelationByEntity(
			PartnerRelationEntity entity,int start,int limit) {
		RowBounds bounds = new RowBounds(start,limit);
		entity.setActive("Y");
		@SuppressWarnings("unchecked")
		List<PartnerRelationEntity> list=getSqlSession().selectList(NAMESPACE+"queryPartnerRelationByEntity",entity,bounds);
		if(!CollectionUtils.isEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * 统计查询结果 
	 * @author 308861 
	 * @date 2016-8-24 下午3:47:19
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryPartnerRelationByEntityCount(PartnerRelationEntity entity) {
		entity.setActive("Y");
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryPartnerRelationByEntityCount",entity);
	}
	
	/**
	 * 
	 * 根据编码查询网点所属子公司 
	 * @author 308861 
	 * @date 2016-8-25 下午3:25:32
	 * @param code
	 * @return 
	 */
	@Override
	public PartnerRelationEntity getSubCompanyNameByCode(String code) {
		if(code == null){
			return null;
		}
		return (PartnerRelationEntity)this.getSqlSession().selectOne(NAMESPACE+"getSubCompanyNameByCode", code);
	}
	
	/**
	 * 
	 * 根据一级二级网点code查询是否存在此网点映射关系 
	 * @author 308861 
	 * @date 2016-8-31 下午8:09:09
	 * @param entity
	 * @return 
	 */
	@Override
	public PartnerRelationEntity queryByCode(PartnerRelationEntity entity) {
		return (PartnerRelationEntity)this.getSqlSession().selectOne(NAMESPACE+"queryByCode",entity);
	}

	/**
	 * 
	 * 根据code查询标杆编码 
	 * @author 308861 
	 * @date 2016-9-3 上午8:38:55
	 * @param code
	 * @return 
	 */
	@Override
	public String unifideByCode(String code) {
		if(code == null){
			return null;
		}
		return (String)this.getSqlSession().selectOne(NAMESPACE+"unifideByCode",code);
	}
	
	/**
	 * 
	 * 根据二级code查询一级网点编码
	 * @author 308861 
	 * @date 2016-9-2 下午5:26:49
	 * @param twoCode
	 * @return 
	 */
	@Override
	public String oneCodeByTwo(String twoCode) {
		if(twoCode == null){
			return null;
		}
		return (String)this.getSqlSession().selectOne(NAMESPACE+"oneCodeByTwo",twoCode);
	}

	/**
	 * 
	 * 跟一级code查询二级网点编码 
	 * @author 308861 
	 * @date 2016-9-2 下午5:29:55
	 * @param oneCode
	 * @return 
	 */
	@Override
	public List<String> twoCodeByOne(String oneCode) {
		if(oneCode == null){
			return null;
		}
		@SuppressWarnings("unchecked")
		List<String> list=this.getSqlSession().selectList(NAMESPACE+"twoCodeByOne",oneCode);
		if(!CollectionUtils.isEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
}
