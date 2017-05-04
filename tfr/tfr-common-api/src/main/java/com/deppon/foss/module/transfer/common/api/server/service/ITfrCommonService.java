/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/ITfrCommonService.java
 *  
 *  FILE NAME          :ITfrCommonService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.CreateErrorLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrCommonVO;

/**
 * <pre>
 * 提供中转模块中的通用服务
 * 
 * </pre>
 * @author foss-wuyingjie
 * @date 2012-11-2 下午4:27:06
 */
public interface ITfrCommonService extends IService {
	/**
	 * <pre>
	 * 正式生成新的编号,用于创建任务前
	 * @param serialNumberRule 传入TfrSerialNumberRuleEnum，如：
	 * 生成订舱编号，则如下调用 generateSerialNumber(TfrSerialNumberRuleEnum.DC)
	 * @param params 传入参数 如：
	 *    清仓差异报告编号：需传入对应的清仓任务编号
	 *    派送装车任务编号、装车任务编号、卸车任务编号：需传入部门编号  generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "XXXXXXX")
	 *    派送装车差异报告编号：需传入派送装车任务编号
	 *    卸车差异报告编号：需传入卸车任务编号
	 *    配载车次号：需依次传入  始发外场简称(params[0]) 到达外场简称(params[1]) 出发日期yymmdd(params[2]) 是否为整车(params[3]) 是否为整车参数(params[4]) generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "GZ", "SH", "20120101")
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-3 上午12:41:37
	 */
	public String generateSerialNumber(TfrSerialNumberRuleEnum serialNumberRule, String... params);
	/**
	 * <pre>
	 * 正式生成新的编号,用于创建任务前，此方法不会锁定sn_rule行，不会影响使用sequence的编号，非sequence生成的编号不能使用
	 * @param serialNumberRule 传入TfrSerialNumberRuleEnum，如：
	 * 生成订舱编号，则如下调用 generateSerialNumber(TfrSerialNumberRuleEnum.DC)
	 * @param params 传入参数 如：
	 *    清仓差异报告编号：需传入对应的清仓任务编号
	 *    派送装车任务编号、装车任务编号、卸车任务编号：需传入部门编号  generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "XXXXXXX")
	 *    派送装车差异报告编号：需传入派送装车任务编号
	 *    卸车差异报告编号：需传入卸车任务编号
	 *    配载车次号：需依次传入  始发外场简称(params[0]) 到达外场简称(params[1]) 出发日期yymmdd(params[2]) 是否为整车(params[3]) 是否为整车参数(params[4]) generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "GZ", "SH", "20120101")
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-3 上午12:41:37
	 */
	public String generateSerialNumberNoLock(TfrSerialNumberRuleEnum serialNumberRule, String... params);
	
	/**
	 * <pre>
	 * 获得生成新的编号，只用于页面的呈现，此方法不会更新后台的序列
	 * 在正式提交某业务时，必须再次使用generateSerialNumber生成新的编号
	 * @param serialNumberRule 传入TfrSerialNumberRuleEnum，如：
	 * 生成订舱编号，则如下调用 generateSerialNumber(TfrSerialNumberRuleEnum.DC)
	 * @param params 传入参数 如：
	 *    清仓差异报告编号：需传入对应的清仓任务编号
	 *    派送装车任务编号、装车任务编号、卸车任务编号：需传入部门编号  generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "XXXXXXX")
	 *    派送装车差异报告编号：需传入派送装车任务编号
	 *    卸车差异报告编号：需传入卸车任务编号
	 *    配载车次号：需依次传入  始发外场简称(params[0]) 到达外场简称(params[1]) 出发日期yymmdd(params[2]) 是否为整车(params[3]) 是否为整车参数(params[4]) generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "GZ", "SH", "20120101")
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-3 上午12:54:01
	 */
	public String showSerialNumber(TfrSerialNumberRuleEnum serialNumberRule, String... params);
	
	/**
	 * <pre>
	 * 通过传入的数据字典编码，获取下拉框信息，默认项为全选
	 * 
     * 如：需载入库区类型，则需传入DictionaryConstants.BSE_GOODSAREA_USAGE
     *    页面显示将会显示
     *    
     *       --请选择--
     *          长途
     *          短途
     *          
     *    其中 默认value为"ALL"，需页面指定
     * </pre>
	 * @param termsCode 数据字典指定的编号
	 * @author foss-wuyingjie
	 * @date 2012-11-16 下午2:50:59
	 */
	public List<BaseDataDictDto> loadDictDataCombox(String termsCode);
	/**
	 * 通过传入的数据字典编码，获取下拉框信息，没有默认项
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午8:20:43
	 */
	List<BaseDataDictDto> loadDictDataComboxNoDefault(String termsCode);

	/**
	 * 通过业务编号获取某JOB最后一次成功执行的业务截止时间，同时锁定此记录
	 * @author foss-wuyingjie
	 * 
	 * @param scheduledFireTime job计划触发时间
	 * @param threadCount 	         线程号
	 * @param threadNo          线程数
	 * @return TfrJobProcessEntity 返回上次执行的job执行记录
	 * @date 2012-11-28 下午4:01:45
	 */
	public TfrJobProcessEntity queryLastExecutedJobInfo(TfrJobBusinessTypeEnum typeEnum, Date scheduledFireTime, int threadNo, int threadCount);

	/**
	 * 通过业务编号获取某JOB信息，不锁定此记录
	 * @author foss-wuyingjie
	 * 
	 * @param scheduledFireTime job计划触发时间
	 * @param threadCount 	         线程号
	 * @param threadNo          线程数
	 * @return TfrJobProcessEntity 返回上次执行的job执行记录
	 * @date 2012-11-28 下午4:01:45
	 */
	public TfrJobProcessEntity queryJobInfo(TfrJobBusinessTypeEnum typeEnum, Date scheduledFireTime, int threadNo, int threadCount);
	
	/**
	 * 更新job业务执行状态
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午7:00:04
	 */
	public void updateExecutedJob(TfrJobProcessEntity jobProcess);

	/**
	 * 记录job日志
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午8:05:12
	 */
	public void addJobProcessLog(TfrJobProcessLogEntity jobProcessLogEntity);

	/**
	 * 检查运单号是否存在
	 *
	 * @param commonCode 运单号
	 * @param serialNo 流水号 
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-14 下午1:30:05
	 */
	public TfrCommonVO validateWaybillNoExist(String waybillNo, String serialNo);
	
	/**
	 * 通过数据字典编码返回名称
	 *
	 * @param code
	 * @param value
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-28 下午2:10:00
	 */
	public String queryDictNameByCode(String code, String value);
	
	/**
	 * 
	 * 新事务生成编号
	 * @author alfred
	 * @date 2013-9-4 上午8:26:34
	 * @param serialNumberRule
	 * @param params
	 * @return
	 * @see
	 */
	String generateSerialNumberRequireNew(TfrSerialNumberRuleEnum serialNumberRule, String... params);
	
	/**
	 * 插入错误记录
	 * @author alfred
	 * @date 2016-10-19 16:26:34
	 * @param entity
	 * @return
	 * @see
	 */
	public void createErrorLog(CreateErrorLogEntity entity);
}