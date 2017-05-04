package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;
/**
 * @desc PDA端扫描生成包装金额信息 service interface
 * @author foss-189284-zx
 * @date 2014-5-8
 * */
public interface IPdaPackageMainPriceService extends IService{	
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息 
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	List<QueryPDAPackResultEntity> queryPdaPackagePriceByWaybillNoOrMaterial(QueryPDAPackConditionEntity pdaQueryPackConditionEntity,int limit,
			int start);
	/**
	 * @desc 根据运单号、包装供应商、包装部门、包装开始时间结束时间查询PDA端扫描生成包装金额信息  不分页
	 * @author foss-189284-zx
	 * @date 2014-5-8
	 * */
	public Long queryPdaPackagePriceList(QueryPDAPackConditionEntity pdaQueryPackConditionEntity);
	/**
	 *输出 PDA端扫描生成包装金额信息导出流
	* @author foss-189284-zx
	* @date 创建时间：2014-5-26 上午11:20:27 
	* @return InputStream
	 */
	InputStream exportExcelStream(QueryPDAPackConditionEntity pdaQueryPackConditionEntity);
	/**
	 * （反审核）审核PDA包装金额
	 * @author 105869-heyongdong
	 * @date 2014年6月25日 11:37:48
	 * */
	void auditPacked(List<String> id, String auditType, CurrentInfo user);
	
}
