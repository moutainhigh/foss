package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBranchSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递分部营业部映射关系Dao实现
 * 
 * @author foss-WeiXing
 * @date 2014-9-22 上午10:13:35
 */
public class ExpressBranchSalesDeptDao extends SqlSessionDaoSupport implements
		IExpressBranchSalesDeptDao {
	//命名空间
	private static final String NAMESPACE =ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressBranchSalesDept.";
	
	/**
	 *<P>添加快递分部对应实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午4:06:56
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity addExpressBranchSales(
			ExpressBranchSalesDeptEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		Date now =new Date();
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersionNo(now.getTime());
		entity.setModifyUser(entity.getCreateUser());
		int result =this.getSqlSession().insert(NAMESPACE+"addExpressBranchSales", entity);
		return result ==NumberConstants.ZERO?null:entity;
	}
	/**
	 *<P>根据id删除<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午4:30:32
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteExpressBranchSalesById(
			ExpressBranchSalesDeptEntity entity) {
		Map<String,Object> map =new HashMap<String,Object>();
		Date now =new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().delete(NAMESPACE+"deleteExpressBranchSalesById", map);
	}
	/** 
	 *<P>根据条件分页查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午4:48:37
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesList(
			ExpressBranchSalesDeptEntity entity,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressBranchSalesList",entity,rowBounds);
	}
	/**
	 *<P>根据营业部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:11:29
	 * @param salesDeptCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesListbySalesCode(
			String salesDeptCode) {
		if(StringUtils.isBlank(salesDeptCode)){
			return null;
		}
		ExpressBranchSalesDeptEntity entity =new ExpressBranchSalesDeptEntity();
		entity.setSalesDeptCode(salesDeptCode);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressBranchSalesListbySalesCode", entity);
	}
	/**
	 *<P>根据快递分部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:18:41
	 * @param expressBranchCode
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public ExpressBranchSalesDeptEntity queryExpressBranchSalesbyExpressBranchCode(
			String expressBranchCode) {
		if(StringUtils.isBlank(expressBranchCode)){
			return null;
		}
		ExpressBranchSalesDeptEntity entity =new ExpressBranchSalesDeptEntity();
		entity.setExpressBranchCode(expressBranchCode);
		entity.setActive(FossConstants.ACTIVE);
		List<ExpressBranchSalesDeptEntity> resultList =getSqlSession().selectList(NAMESPACE+"queryExpressBranchSalesbyExpressBranchCode",entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	
	/**
	 *<P>根据快递分部编码,营业部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:18:41
	 * @param expressBranchCode,salesDeptCode
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public ExpressBranchSalesDeptEntity queryExpressBranchSalesbyExpressBranchSalesDeptCode(
			String expressBranchCode,String salesDeptCode ) {
		if(StringUtils.isBlank(expressBranchCode)){
			return null;
		}
		ExpressBranchSalesDeptEntity entity =new ExpressBranchSalesDeptEntity();
		entity.setExpressBranchCode(expressBranchCode);
		entity.setSalesDeptCode(salesDeptCode);
		entity.setActive(FossConstants.ACTIVE);
		List<ExpressBranchSalesDeptEntity> resultList =getSqlSession().selectList(NAMESPACE+"queryExpressBranchSalesbyExpressBranchSalesDeptCode",entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	
	/**
	 *<P>根据快递分部编码或营业部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:18:41
	 * @param ExpressBranchSalesDeptEntity entity
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public ExpressBranchSalesDeptEntity queryByExpressBranchSalesDeptCode(ExpressBranchSalesDeptEntity entity ) {
		if(entity==null){
			return null;
		}
		entity.setActive(FossConstants.ACTIVE);
		List<ExpressBranchSalesDeptEntity> resultList =getSqlSession().selectList(NAMESPACE+"queryByExpressBranchSalesDeptCode",entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	/**
	 *<P>查询总数<P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:22:06
	 * @param entity
	 * @return
	 */
	@Override
	public long queryExpressBranchSalesCount(ExpressBranchSalesDeptEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryExpressBranchSalesCount",entity);
	}
	/**
	 *<P>根据快递分部编码、营业部编码动态作废实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:24:33
	 * @param deleteEntity
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity deleteExpressBranchSales(
			ExpressBranchSalesDeptEntity deleteEntity) {
		Map<String,Object> map =new HashMap<String,Object>();
		Date now =new Date();
		deleteEntity.setActive(FossConstants.INACTIVE);
		deleteEntity.setModifyDate(now);
		deleteEntity.setVersionNo(now.getTime());
		map.put("entity", deleteEntity);
		map.put("conditionActive", FossConstants.ACTIVE);
		int result =this.getSqlSession().delete(NAMESPACE+"deleteExpressBranchSales", map);
		return result==NumberConstants.ZERO?null:deleteEntity;
	}

}
