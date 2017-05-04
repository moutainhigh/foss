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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOutbranchPlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递代理理网点运价方案Dao接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午3:26:03 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午3:26:03
 * @since
 * @version
 */
public class OutbranchPlanDao extends SqlSessionDaoSupport implements IOutbranchPlanDao {

    private static final String NAMESPACE = "foss.pkp.pkp-pricing.outbranchPlanEntity.";

    /** 
     * 新快递代理网点运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public int addInfo(OutbranchPlanEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据cod快递代理快递代理网点运价方案 
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
     * <p>根据父快递代理除快递代理网点运价方案信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @param parentcode快递代理t 快递代理公司运价ＩＤ
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOutbranchPlanDao#deleteInfoByParentCode(java.util.List)
     */
    @Override
    public int deleteInfoByParentCode(List<String> parentcodeList) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", parentcodeList);
	return this.getSqlSession().update(NAMESPACE + "deleteByParentCode", map);
    }

    /** 
   快递代理 修改快递代理网点运价方案 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param 快递代理ity 快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public int updateInfo(OutbranchPlanEntity entity) {
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * 根据传入对象快递代理合条件所有快递代理网点运价方案信息 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OutbranchPlanEntity> queryInfos(OutbranchPlanEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @para快递代理ntity 快递代理网点运价方案实体
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public Long queryRecordCount(OutbranchPlanEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    /**
  快递代理* <p>激活快递代理网点运价方案 </p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:25:30
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOutbranchPlanDao#activate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public int activate(OutbranchPlanEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "activate", entity);
    }
    
    /**
     快递代理p>根据ID查询快递代理网点运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:07:43
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IOutbranchPlanDao#queryInfoById(java.lang.String)
     */
    @Override
    public OutbranchPlanEntity queryInfoById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", id);
	
	return (OutbranchPlanEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoById", map);
    }

    @Override
    @SuppressWarnings("unchecked")
    public OutbranchPlanEntity queryPriceByCode(String outerBranchCode,
	    Date billDate) {
	
	OutbranchPlanEntity entity=new OutbranchPlanEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setBillDate(billDate);
	entity.setOuterBranchCode(outerBranchCode);	
	List<OutbranchPlanEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryPriceByCode",entity);
	if(CollectionUtils.isNotEmpty(list))
	{
	    return list.get(0);
	}else
	{
	    return null;
	}
    }
    

}
