package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPdaPackageMainPriceDao;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;

/**
 * @desc PDA端扫描生成包装金额信息 
 * @author foss-189284-zx
 * @date 2014-5-8
 * */
public class PdaPackageMainPriceDao  extends iBatis3DaoImpl implements IPdaPackageMainPriceDao {

	public final static String PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE = "foss.packaging.packMojarPrice.";
	
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	@SuppressWarnings("unchecked")
	public List<QueryPDAPackResultEntity> queryPdaPackagePriceByWaybillNoOrMaterial(QueryPDAPackConditionEntity pdaQueryPackConditionEntity,int limit,
			int start){
		      RowBounds rowBounds = new RowBounds(start, limit);		      			
			  List<QueryPDAPackResultEntity> queryPDAPackResultEntity= getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPackagePriceByWaybillNoOrMaterial", pdaQueryPackConditionEntity,rowBounds);
					return  queryPDAPackResultEntity;

	}
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 不分页
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	@SuppressWarnings("unchecked")
	public List<QueryPDAPackResultEntity> queryPdaPackagePriceResultList(QueryPDAPackConditionEntity pdaQueryPackConditionEntity){
			  List<QueryPDAPackResultEntity> queryPDAPackResultEntity= getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPackagePriceByWaybillNoOrMaterial", pdaQueryPackConditionEntity);
					return  queryPDAPackResultEntity;

	}
	
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 总数
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	@SuppressWarnings("unchecked")
	public Long queryPdaPackagePriceList(QueryPDAPackConditionEntity pdaQueryPackConditionEntity){
		return    (Long) getSqlSession().selectOne(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPackagePriceCount", pdaQueryPackConditionEntity);

	}
	/**
	 * @desc 根据id查询pda包装金额
	 * @author 105869-heyongdong
	 * @date 2014年6月25日 17:04:50
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<QueryPDAPackResultEntity> queryPdaPackagePriceByIds(List<String> id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", id);
		return this.getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPdaPackagePriceByIds", map);
	}
	
	/**
	 * @desc 根据id跟新审核状态
	 * @author 105869-heyongdong
	 * @date 2014年6月25日 17:04:50
	 */
	@Override
	public Long updateAuditPackgeByIds(List<QueryPDAPackResultEntity> entitys) {
		return (long) this.getSqlSession().insert(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"updateAuditPackgeByIds", entitys);
	}
}
