package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IldpCompanyValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpCompanyValueAddDao extends SqlSessionDaoSupport implements
		IldpCompanyValueAddDao {
	
	 private static final String NAMESPACE = "com.deppon.foss.module.pickup.pricing.ldpCompanyValueAddDao.";
	
	/**
	 *  根据条件查询快递代理理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @param   start
	 * @param   limit
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity,int start,int limit) {
		    RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryEntityByParams", entity, rowBounds);
		}
	
	/**
	 * 新增一快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:48:48
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int addNewPartbussValueAddEntity(PartbussValueAddEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "addNewPartbussValueAddEntity", entity);
	}
	
	/**
	 * 根据id去快递代理递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int deletePartbussValueAddEntity(String[] ids) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ids", Arrays.asList(ids));
		return this.getSqlSession().delete(NAMESPACE + "deletePartbussValueAddEntity", map);
	}
	
	/**
	 * 根据id快递代理快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int activeValueAddedServices(String[] ids) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ids", Arrays.asList(ids));
		map.put("modifyUser", FossUserContext.getCurrentUser().getEmpCode());
		map.put("modifyOrgCode", FossUserContext.getCurrentDeptCode());
		return this.getSqlSession().update(NAMESPACE + "activeValueAddedServices", map);
	}
	
	/**
	 快递代理新快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:53:40
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int updatePartbussValueAddEntity(PartbussValueAddEntity entity) {
		if(StringUtils.equals(entity.getActive(), FossConstants.ACTIVE)){
//			this.getSqlSession().update(NAMESPACE + "modifyEndTime", entity);
			entity.setLastCreateTime(new Date());
			entity.setLastCreatorCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			entity.setLastCreatorName(FossUserContext.getCurrentUser().getEmpName());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setId(UUIDUtils.getUUID());
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
			entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
			entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			return this.addNewPartbussValueAddEntity(entity);
		}
		return this.getSqlSession().update(NAMESPACE + "modifyValueAddedServices", entity);
	}
	
	/**
	 *快递代理激活快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:56:58
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int activeImmediatelyValueAddedServices(PartbussValueAddEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "activeImmediatelyValueAddedServices", entity);
	}
	
	/**
	 快递代理即终止快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:57:04
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int inActiveImmediatelyValueAddedServices(PartbussValueAddEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "inActiveImmediatelyValueAddedServices", entity);
	}
	
	/**
	 * 快递代理条件查询快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryEntityIsExistByParams", entity);
	}
	
	/**
	 * 统计查询的行数
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 下午1:51:08
	 * @param   entity
	 * @return  Long
	 *
	 */
	public Long countQueryRecord(PartbussValueAddEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countQueryRecord", entity);
	}
	
	/**
	 * 根据id去查询同一个方案是否有重复
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-20 上午11:36:25
	 * @return  List<PartbussValueAddEntity>
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PartbussValueAddEntity> queryEntityByIds(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("active", FossConstants.ACTIVE);
		map.put("id", id);
		return this.getSqlSession().selectList(NAMESPACE + "queryEntityByIds", map);
	}
}
