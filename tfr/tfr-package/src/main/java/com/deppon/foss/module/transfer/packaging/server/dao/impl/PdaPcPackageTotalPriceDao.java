package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPdaPcPackageTotalPriceDao;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;

/** 
 * @author ZhangXu
 * @version 创建时间：2014-5-29 下午5:04:00 
 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
 * 查询并汇总PDA与PC端包装金额
 */
public class PdaPcPackageTotalPriceDao  extends iBatis3DaoImpl implements IPdaPcPackageTotalPriceDao {
	public final static String PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE = "foss.packaging.packMojarPrice.";

	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryPdaPcPackResultEntity> queryPdaPcTotalPrice(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity, int limit,
			int start) {
	      RowBounds rowBounds = new RowBounds(start, limit);		      			
		return getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPcTotalPrice", queryPdaPcPackConditionEntity,rowBounds);
	}
	
	
	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
 public List<QueryPdaPcPackResultEntity> queryPdaPcTotalList(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity){
		return getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPcTotalPrice", queryPdaPcPackConditionEntity);
 }
 
 /**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额 总数
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
 public Long  queryPdaPcTotalPriceCount(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity){
		return (Long) getSqlSession().selectOne(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPcTotalPriceCount", queryPdaPcPackConditionEntity);

 }


}
