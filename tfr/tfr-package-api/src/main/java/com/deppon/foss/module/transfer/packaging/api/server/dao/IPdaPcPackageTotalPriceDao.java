package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;

/** 
 * @author ZhangXu
 * @version 创建时间：2014-5-29 下午4:48:19 
 * 类说明 
 */
public interface IPdaPcPackageTotalPriceDao {
	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
 public List<QueryPdaPcPackResultEntity> queryPdaPcTotalPrice(QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity, int limit,
			int start);
 /**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
 public List<QueryPdaPcPackResultEntity> queryPdaPcTotalList(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity);
 
 /**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额 总数
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
 public Long queryPdaPcTotalPriceCount(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity);
}
