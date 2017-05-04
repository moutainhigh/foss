/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;


/**
 * 快递代理理网点运价方案明细DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午3:25:07 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午3:25:07
 * @since
 * @version
 */
public class OubrPlanDetailDao extends SqlSessionDaoSupport implements IOubrPlanDetailDao {


    private static final String NAMESPACE = "foss.pkp.pkp-pricing.oubrPlanDetailEntity.";

    /** 
     * 新快递代理网点运价方案明细 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int addInfo(OubrPlanDetailEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据cod快递代理快递代理网点运价方案明细 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#deleteInfo(java.lang.String[], java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * <p>根据父快递代理除快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @param parentcode快递代理t 快递代理网点运价ＩＤ
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#deleteInfoByParentCode(java.util.List)
     */
    @Override
    public int deleteInfoByParentCode(List<String> parentcodeList) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", parentcodeList);

	return this.getSqlSession().update(NAMESPACE + "deleteByParentCode", map);
    }

    /** 
   快递代理 修改快递代理网点运价方案明细 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param 快递代理ity 快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int updateInfo(OubrPlanDetailEntity entity) {
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * 根据传入对象快递代理合条件所有快递代理网点运价方案明细信息 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OubrPlanDetailEntity> queryInfos(OubrPlanDetailEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @para快递代理ntity 快递代理网点运价方案明细实体
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public Long queryRecordCount(OubrPlanDetailEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    /**快递代理   * 激活快递代理网点运价方案明细
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:42:19
     * @pa快递代理 entity 快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#activate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int activate(OubrPlanDetailEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "activate", entity);
    }
    
    /**
    快递代理<p>根据ID查询快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:45:21
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryinfoById(java.lang.String)
     */
    @Override
    public OubrPlanDetailEntity queryinfoById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", id);
	
	return (OubrPlanDetailEntity)this.getSqlSession().selectOne(NAMESPACE + "queryinfoById", map);
    }
    
    /**
     * <p>根快递代理D查询右区间最大的值快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-1 下午5:45:52
     * @param parentId
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryMaxInfoByParentId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OubrPlanDetailEntity> queryMaxInfoByParentId(String parentId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("parentId", parentId);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryMaxInfoByParentId", map);
    }

}
