package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递派送电子地图管理Dao实现
 * @author dujunhui-187862
 * @date 2014-12-19-上午9:15:56
 */
public class ExpressDeliveryMapManageDao extends SqlSessionDaoSupport implements
		IExpressDeliveryMapManageDao {

	private static final String NAMESPACE="foss.bse.bse-baseinfo.expressDeliveryMapManage.";
	
	/* 批量查看选中的快递派送电子地图管理信息
	 * @date 2014-12-19-上午9:15:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#queryExpressDeliveryMapManageBatchView(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageBatchView(
			String[] codes) {
		Map<Object,Object> map=new HashMap<Object,Object>();
		map.put("codeList", codes);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressDeliveryMapManageBatchView",map);
	}

	/* 根据salesDepartmentCode查询快递派送电子地图管理信息
	 * @date 2014-12-19-上午9:15:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#queryExpressDeliveryMapManageByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExpressDeliveryMapManageEntity queryExpressDeliveryMapManageByCode(
			String salesDepartmentCode) {
		
		ExpressDeliveryMapManageEntity entity=new ExpressDeliveryMapManageEntity();
		entity.setSalesDepartmentCode(salesDepartmentCode);
		
		List<ExpressDeliveryMapManageEntity> entityList=this.getSqlSession().
				selectList(NAMESPACE + "queryExpressDeliveryMapManageByCode",entity);
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}

	/* 根据条件查询快递派送电子地图管理信息
	 * @date 2014-12-19-上午9:15:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#queryExpressDeliveryMapManageByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageByCondition(
			ExpressDeliveryMapManageEntity entity, int start, int limit) {
		RowBounds rowBounds=new RowBounds(start,limit);
		/*List<ExpressDeliveryMapManageEntity> entityList=null;
		if(StringUtil.equals(entity.getVerifyState(), DictionaryValueConstants.ALL)){//查询全部List
			entityList=this.getSqlSession().selectList(NAMESPACE + "queryExpressDeliveryMapManageListAll",entity,rowBounds);
		}else{
			entityList=this.getSqlSession().selectList(NAMESPACE + "queryExpressDeliveryMapManageListByCondition",entity,rowBounds);
		}*/
		List<ExpressDeliveryMapManageEntity> entityList=this.getSqlSession().selectList(NAMESPACE+"querySalesDepartmentInfo", entity,rowBounds);
		return CollectionUtils.isEmpty(entityList) ? null:entityList;
	}

	/* 统计根据条件查询的快递派送电子地图管理信息条数
	 * @date 2014-12-19-上午9:15:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#queryExpressDeliveryMapManageCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@Override
	public Long queryExpressDeliveryMapManageCountByCondition(
			ExpressDeliveryMapManageEntity entity) {
		/*if(StringUtil.equals(entity.getVerifyState(), DictionaryValueConstants.ALL)){//查询全部count
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "countExpressDeliveryMapManageListAll", entity);
		}else{
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "countExpressDeliveryMapManageListByCondition", entity);
		}*/
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"countSalesDepartmentInfo", entity);
	}

	/* 根据实体查询快递派送电子地图管理信息用于导出（已作废）
	 * @date 2014-12-19-上午9:15:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#queryExpressDeliveryMapManageListForExport(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageListForExport(
			ExpressDeliveryMapManageEntity entity) {
		List<ExpressDeliveryMapManageEntity> entityList=this.getSqlSession().selectList(NAMESPACE + "queryExpressDeliveryMapManageListForExport", entity);
		return CollectionUtils.isEmpty(entityList) ? null:entityList;
	}

	/*
	 * 审核快递派送区域电子地图信息
	 * 187862-dujunhui
	 * 2014-12-30 下午3:20:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#verifyExpressDeliveryMapManage(java.lang.String[])
	 */
	@Override
	public void verifyExpressDeliveryMapManage(String[] codes) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codeList", codes);
		map.put("verifyTime", new Date());
		map.put("verifyState", DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_VERIFIED);//审核状态设置为已审核
		map.put("active", FossConstants.ACTIVE);
		map.put("verifyManCode", FossUserContext.getCurrentUser().
				getEmployee().getEmpCode());//设置审核人工号
//		map.put("departServiceArea", null);//部门服务面积
		this.getSqlSession().update(NAMESPACE+"verifyExpressDeliveryMapManage", map);
	}

	/*
	 * 生效快递派送区域电子地图信息
	 * 187862-dujunhui
	 * 2014-12-31 下午2:05:40
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#activateExpressDeliveryMapManage(java.lang.String[])
	 */
	@Override
	public void activateExpressDeliveryMapManage(String[] codes) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codeList", codes);
		map.put("verifyTime", new Date());
		map.put("verifyState", DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_ACTIVED);//审核状态设置为生效
		map.put("active", FossConstants.ACTIVE);
		map.put("verifyManCode", FossUserContext.getCurrentUser().
				getEmployee().getEmpCode());//设置审核人工号
//		map.put("departServiceArea", null);//部门服务面积
		this.getSqlSession().update(NAMESPACE+"activateExpressDeliveryMapManage",map);
	}

	/*
	 * 退回快递派送区域电子地图信息
	 * 187862-dujunhui
	 * 2014-12-31 下午3:07:29
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#turnBackExpressDeliveryMapManage(java.lang.String[])
	 */
	@Override
	public void turnBackExpressDeliveryMapManage(String[] codes) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codeList", codes);
		map.put("verifyTime", new Date());
		map.put("verifyState", DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_TURNBACK);//审核状态设置为已退回
		map.put("active", FossConstants.ACTIVE);
		map.put("verifyManCode", FossUserContext.getCurrentUser().getEmployee().getEmpCode());//设置审核人工号
		map.put("verifyManName", FossUserContext.getCurrentUser().getEmployee().getEmpName());//设置审核人工号
		map.put("modifyDate",  new Date());
		map.put("modifyUser",  new Date());
		this.getSqlSession().update(NAMESPACE+"matchReturn", map);
	}

	/*
	 * 更新保存GIS派送区域电子地图坐标
	 * 187862-dujunhui
	 * 2015-1-7 下午3:36:27
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#updateExpressDeliveryGISMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@Override
	public void updateExpressDeliveryGISMap(
			ExpressDeliveryMapManageEntity entity) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("salesDepartmentCode", entity.getSalesDepartmentCode());
		map.put("expressDeliveryCoordinate", entity.getExpressDeliveryCoordinate());
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentUser().
				getEmployee().getEmpCode());//设置审核人工号
//		map.put("departServiceArea", null);//部门服务面积
		//如果进行的审核状态为未编辑、未审核、已审核（无修改按钮）、已退回、生效，则地图修改后审核状态为未审核(审核人、审核时间、审核状态为空，派送坐标不为空)
//		map.put("verifyState", DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED);
		this.getSqlSession().update(NAMESPACE+"updateExpressDeliveryGISMap", map);
	}

	/*
	 * 根据编码作废营业部信息（已作废）
	 * 187862-dujunhui
	 * 2015-1-7 下午4:34:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao#voidSalesDepartmentInfo(java.lang.String[])
	 */
	@Override
	public void voidSalesDepartmentInfo(String[] codes) {
		if(codes!=null){
			Map<String,Object> mapToVoid=new HashMap<String,Object>();
			mapToVoid.put("codeList", codes);
			this.getSqlSession().update(NAMESPACE+"voidSalesDepartmentInfo", mapToVoid);
		}
	}

	/**
	 * 快递派送区域起草信息新增
	 */
	@Override
	public ExpressDeliveryMapManageEntity addSalesDepartmentInfo(ExpressDeliveryMapManageEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setCreateUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		int l= this.getSqlSession().insert(NAMESPACE+"addSalesDepartmentInfo", entity);
		return  (ExpressDeliveryMapManageEntity) (l>0?entity:0);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageEntityByCode(
			ExpressDeliveryMapManageEntity entity) {
//		entity.setSalesDepartmentCode(orgCode);
		entity.setActive(FossConstants.ACTIVE);
		List<ExpressDeliveryMapManageEntity> entitys=this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryMapManageEntityByCode", entity);
		return entitys;
	}

	/**
	 * 根据编码作废快递派送电子地图信息
	 */
	@Override
	public long deleteExpressDeliveryMapManageEntityById(ExpressDeliveryMapManageEntity entity) {
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(new Date());
		entity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		return this.getSqlSession().update(NAMESPACE+"updateSalesDepartmentInfo", entity);
	}

}