package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;
/**
 * @desc PDA端扫描生成包装金额信息 
 * @author foss-189284-zx
 * @date 2014-5-8
 * */

public interface IPdaPackageMainPriceDao {

	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	List<QueryPDAPackResultEntity>  queryPdaPackagePriceByWaybillNoOrMaterial(QueryPDAPackConditionEntity pdaQueryPackConditionEntity,int limit,
			int start);
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息  不分页
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	public List<QueryPDAPackResultEntity> queryPdaPackagePriceResultList(QueryPDAPackConditionEntity pdaQueryPackConditionEntity);
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 总数
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	public Long queryPdaPackagePriceList(QueryPDAPackConditionEntity pdaQueryPackConditionEntity);
	/**
	 * @desc 根据id查询pda包装金额信息
	 * @author 105869-heyongdong
	 * @data 2014年6月25日 14:20:09
	 * */
	List<QueryPDAPackResultEntity> queryPdaPackagePriceByIds(List<String> id);
	/**
	 * @desc 跟新反审核（审核）状态通过id
	 * @author 105869-heyongdong
	 * @date 2014年6月25日 16:57:01
	 * */
	Long updateAuditPackgeByIds(List<QueryPDAPackResultEntity> entitys);
}
