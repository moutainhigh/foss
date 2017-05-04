package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;


/**
 * 
 * 客户信用额度还原服务 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-23 上午11:01:56
 */
public interface ICustomerCreditRestoreService  extends IService{
	
	/**
	 * 
	 * 查询上次JOB执行时间
	 * @Title: getLastExecuteTime 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-20 上午9:18:22
	 * @param @return    设定文件 
	 * @return Date    返回类型 
	 * @throws
	 */
	Date getLastExecuteTime(Date current);
	
	/**
	 * 
	 * 从综合管理同步客户信息到结算，并完成信用额度初始化
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:04:46
	 */
	void syncCustomer(Date lastDate,Date current);
	
	/**
	 * 
	 * 从综合管理同步组织信息到结算，并完成信用额度初始化
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:05:19
	 */
	void syncOrgBusiness();
	
	
	/**
	 * 还原客户信用额度
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:08:14
	 */
	void restoreCredit(Date lastDate,Date current);

}
