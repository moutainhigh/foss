package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAcceptPointSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 接驳点与营业部映射实现DAO
 * @author 132599
 *
 * @date 2015-4-15 下午6:32:37
 */
public class AcceptPointSalesDeptDao extends SqlSessionDaoSupport implements IAcceptPointSalesDeptDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.acceptPointSalesDept.";
	
	/**
     * <p>新增接驳点与营业部映射主干信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addAcceptPointSales(AcceptPointSalesDeptEntity entity) {
		
		return  this.getSqlSession().insert(NAMESPACE + "insertAcceptPointSales", entity);
	}
	
	/**
     * <p>新增接驳点与营业部映射子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addSalesDept(AcceptPointSalesChildrenDeptEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insertAcceptPointSalesDept", entity);
	}
	
	/**
     * <p>修改接驳点与营业部映射主干信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int updateAcceptPointSales(AcceptPointSalesDeptEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE + "updateAcceptPointSales", entity);
	}
	
	/**
     * <p>作废接驳点与营业部映射信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteAcceptPointSalesDeptById(String id,Date modifyDate, String modifyUser, String modifyUserName) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		map.put("modifyDate", modifyDate);
		map.put("modifyUser", modifyUser);
		map.put("modifyUserName", modifyUserName);
		
		
		return this.getSqlSession().update(NAMESPACE + "deleteAcceptPointSalesById", map);
	}
	
	/**
     * <p>作废接驳点与营业部映射主干对应的子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteSalesDeptByAcceptSmallCode(String acceptPointCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptPointCode", acceptPointCode);
		map.put("inactive", FossConstants.INACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteSalesDeptByAcceptSmallCode", map);
	}
	
	/**
     * 根据接驳点、营业区编码查询接驳点与营业部映射子信息
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryChildrenDeptInfoByAcceptSmallCode(String acceptPointCode, String active, String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptPointCode", acceptPointCode);
		map.put("active", active);
		map.put("status", status);
		return this.getSqlSession().selectList(NAMESPACE + "queryChildrenDeptInfoByAcceptSmallCode",
				map);
	}
	
	
	
	/**
     * <p>作废接驳点与营业部映射主干对应的子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteChildrenSalesDeptById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inactive", FossConstants.INACTIVE);
		map.put("id", id);
		return this.getSqlSession().update(NAMESPACE + "deleteChildrenSalesDeptById", map);
	}
	
	/**
     * <p>修改接驳点与营业部映射主干状态信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	public int updateAcceptPointSalesStatusById(List<String> idList,String status,Date modifyDate, String modifyUser, String modifyUserName) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("status", status);
		map.put("modifyDate", modifyDate);
		map.put("modifyUser", modifyUser);
		map.put("modifyUserName", modifyUserName);
		
		return this.getSqlSession().update(NAMESPACE + "updateAcceptPointSalesStatusById", map);
	}
	
	
	/** 
	 * <p>TODO(根据idList批量查询接驳点与营业部映射主干状态信息)</p> 
	 * @author 269231
	 * @param idList
	 * @return 
	 */
    
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointSalesDeptByIdList(List<String> idList) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("idList", idList);
		return this.getSqlSession().selectList(NAMESPACE + "queryAcceptPointSalesDeptByIdList", map);
	}
	
	
	/**
     * <p>修改接驳点与营业部映射子状态信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	public int updateSalesDeptStatusByAcceptSmallCode(String status,
			String acceptPointCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptPointCode", acceptPointCode);
		map.put("status", status);
		return this.getSqlSession().update(NAMESPACE + "updateSalesDeptStatusByAcceptSmallCode", map);
	}
	
	/**
     * 根据传入对象查询符合条件所有接驳点与营业部映射信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesDeptEntity> queryAllAcceptPointSalesDept(
			AcceptPointSalesDeptEntity entity, int limit, int start) {
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryAllAcceptPointSalesDept",
			entity, rowBounds);
	}
	
	/**
     * 根据传入对象查询符合条件所有接驳点与营业部映射子信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryAllAcceptPointSalesChildrenDept(String acceptPointCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptPointCode", acceptPointCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllAcceptPointSalesChildrenDept",
				map);
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-15 下午3:10:32
     * @param entity
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(AcceptPointSalesDeptEntity entity) {

		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}

	/**
     * 根据id查询接驳点映射主干信息
     * @author 132599-FOSS-ShenweiHua
     * @date   2013-4-18 下午3:10:32
     * @return
     */
	@Override
	public AcceptPointSalesDeptEntity queryAcceptPointSalesById(String id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (AcceptPointSalesDeptEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAcceptPointSalesById",
				map);
	}
	
	/**
     * 根据接ID查询接驳点与营业部映射子信息
     */
	@SuppressWarnings("unchecked")
	@Override
	public AcceptPointSalesChildrenDeptEntity queryChildrenDeptById(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (AcceptPointSalesChildrenDeptEntity)this.getSqlSession().selectOne(NAMESPACE + "queryChildrenDeptById",
				map);
	}	
	
	/**
     * 根据实体查询接驳点映射主干信息
     * @author 132599-FOSS-ShenweiHua
     * @date   2013-4-18 下午3:10:32
     * @return
     */
	@Override
	public AcceptPointSalesDeptEntity queryAcceptPointSales(
			AcceptPointSalesDeptEntity entity) {
		 return (AcceptPointSalesDeptEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAcceptPointSales",
				 entity);
	}
	
	/**
     * 根据接驳点、营业区编码查询接驳点与营业部映射子信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryChildrenDeptByAcceptSmallCode(String acceptPointCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acceptPointCode", acceptPointCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryChildrenDeptByAcceptSmallCode",
				map);
	}
	
	/**
     * 根据上级编码查询下面的营业部
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryAllOrgAdministrativeInfoByParentOrgCode(String acceptPointCode,String parentOrgCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentOrgCode", parentOrgCode);
		map.put("acceptPointCode", acceptPointCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllOrgAdministrativeInfoByParentOrgCode",
				map);
	}
	
	 /**
     * 根据大区编码从接驳点基础资料里面查询接驳点信息和中转场信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointTransferInfoByBigRegionCode(
			String bigRegionCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bigRegionCode", bigRegionCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryAcceptPointTransferInfoByBigRegionCode",
				map);
	}
	
	/**
     * 根据大区编码从组织表总查询大区下面的营业区信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> querySmallRegionInfoByBigRegionCode(
			String bigRegionCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bigRegionCode", bigRegionCode);
		return this.getSqlSession().selectList(NAMESPACE + "querySmallRegionInfoByBigRegionCode",
				map);
	}
	
	
	 /**
     * 根据接驳点编码查询
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointSalesByAcceptPointCode(
			List<String> acceptPointCodes) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAcceptPointSalesByAcceptPointCode",
				acceptPointCodes);
	}
	
	/**
	 * 根据员工号查询快递员对应的接驳点
     * @author 132599-FOSS-ShenweiHua
     * @date   2015-4-29 上午10:50:21
     * @param empCode
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryExpressAcceptPointByEmployeeCode(String empCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empCode", empCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressAcceptPointByEmployeeCode",
				map);
	}

	/**
     * 根据接驳点编码查询接驳点与营业部关系
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryAcceptPointSaleDeptsByAcceptPointCode(
			List<String> acceptPointCodes) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAcceptPointSaleDeptsByAcceptPointCode",
				acceptPointCodes);
	}
	
}
