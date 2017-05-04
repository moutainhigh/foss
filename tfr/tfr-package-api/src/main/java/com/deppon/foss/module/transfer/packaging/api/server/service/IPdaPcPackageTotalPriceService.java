package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;

/** 
 * @author ZhangXu
 * @version 创建时间：2014-5-29 下午5:13:42 
 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
 * 查询并汇总PDA与PC端包装金额 
 */
public interface IPdaPcPackageTotalPriceService extends IService{
	/**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额 分页
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
public List<QueryPdaPcPackResultEntity> queryPdaPcTotalList(QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity);
 
 /**
	 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间 查询并汇总包装金额 总数
	* @author foss-189284-zx
	* @date 创建时间：2014-5-29 下午5:00:38 
	* @return List<QueryPdaPcPackResultEntity>
	 */
public Long queryPdaPcTotalPriceCount(QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity);

/**
 * 汇总PDA与PC端包装金额 信息  导出
 */
InputStream exportExcelStream(QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity);

}
