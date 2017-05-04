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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递大区与行政区域映射关系DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午2:38:39 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午2:38:39
 * @since
 * @version
 */
public class ExpressBigRegionDistrDao extends SqlSessionDaoSupport implements
	IExpressBigRegionDistrDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressBigRegionDistr.";
   
    /** 
     * 新增快递大区与行政区域映射关系
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:38:39
     * @param entity 快递大区与行政区域映射关系明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#addInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public int addInfo(ExpressBigRegionDistrEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废快递大区与行政区域映射关系 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#deleteInfo(java.util.List, java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	Date date = new Date();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", date);
	// 设置版本号
	map.put("versionNo", System.currentTimeMillis());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改快递大区与行政区域映射关系
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param entity 快递大区与行政区域映射关系实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#updateInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public int updateInfo(ExpressBigRegionDistrEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * 根据传入对象查询符合条件所有快递大区与行政区域映射关系信息 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#queryInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressBigRegionDistrEntity> queryInfos(
	    ExpressBigRegionDistrEntity entity, int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param entity 快递大区与行政区域映射关系实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public Long queryRecordCount(ExpressBigRegionDistrEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }

	/** 
	 * 根据有效状态、行政区域编码、快递大区编码查询快递大区行政区域映射信息
	 * @author foss-dujunhui
	 * @date 2014-6-23 下午4:50:45
	 * @param orgCode
	 * @param districtCode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao#queryInfoByDistrictCodeAndActive(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressBigRegionDistrEntity queryInfoByDistrictCodeAndActive(
			String orgCode,String districtCode, String active) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("districtCode", districtCode);
		map.put("active", active);
		return (ExpressBigRegionDistrEntity)this.getSqlSession().selectOne(NAMESPACE+"queryInfoByDistrictCodeAndActive", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressBigRegionDistrEntity> queryDeletEntityByIds(
			List<String> codes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		return this.getSqlSession().selectList(NAMESPACE+"queryDeletEntityByIds",map);
	}

}
