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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IUnloadDiffReportDao.java
 *  
 *  FILE NAME          :IUnloadDiffReportDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportQMSDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;

/** 
 * @className: IUnloadDiffReportDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告dao接口
 * @date: 2012-12-10 上午9:55:50
 * 
 */
public interface IUnloadDiffReportDao {

	/**
	 * 传入查询条件，返回卸车差异报告结果列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午10:07:50
	 */
	List<UnloadDiffReportEntity> queryUnloadDiffReportList(QueryUnloadDiffReportConditionDto queryCondition,int limit,int start);
	
	/**
	 * 获取查询到的卸车差异报告总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午11:17:55
	 */
	Long queryUnloadDiffReportCount(QueryUnloadDiffReportConditionDto queryCondition);
	
	/**
	 * 根据卸车差异报告编号获取基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午10:28:00
	 */
	UnloadDiffReportEntity queryUnloadDiffReportBasicInfo(String diffReportNo);
	
	/**
	 * 根据卸车差异报告ID获取基本信息
	 * @author 045923-foss-shiwei
	 * @date 2013-2-22 下午5:33:15
	 */
	UnloadDiffReportEntity queryUnloadDiffReportBasicInfoById(String diffReportId);
	
	/**
	 * 根据卸车差异报告ID获取差异报告详情
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午11:04:17
	 */
	List<UnloadDiffReportDetailEntity> queryUnloadDiffReportDetail(String diffReportId);
	
	/**
	 * 获取所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:10:25
	 */
	List<UnloadDiffReportDetailEntity> queryUnresolvedUnloadSerialNoList(Date bizJobStartTime,int minutes);
	
	/**
	 * 插入一条差异报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 上午1:07:54
	 */
	int addUnloadDiffReport(UnloadDiffReportEntity baseEntity);
	
	/**
	 * 生成卸车差异报告时，新增差异报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:20:32
	 */
	int addUnloadDiffReportBasicInfo(Collection<UnloadDiffReportEntity> baseEntityList);
	
	/**
	 * 生成卸车差异报告时，批量插入差异报告流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:21:30
	 */
	int addUnloadDiffReportDetailList(List<UnloadDiffReportDetailEntity> detailList);
	
	/**
	 * 插入一条流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 上午1:04:32
	 */
	int addUnloadDiffReportDetail(UnloadDiffReportDetailEntity detail);
	
	/**
	 * 查询未处理完毕的差异报告ID
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 上午9:36:34
	 */
	List<String> queryUnResolvedDiffReport(int threadNo, int threadCount,Date bizDate,Date delverBizDate);
	/**
	 * 根据差异报告ID查询符合上报OA的差异明细
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午7:47:14
	 */
	List<UnloadDiffReportDetailEntity> queryUnResolvedDiffReportDetail(String reportId,BigDecimal timeLimit);
	
	/**
	 * 将卸车差异报告的处理状态更新为“已处理”
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:43:58
	 */
	int updateUnloadDiffReportHandleState(String diffReportId,String handleState);
	
	/**
	 * 根据ID更新卸车差异报告明细，可能更新的字段包括：
	 * 异常处理时间、备注、处理人编号、处理人名称、处理部门编号、处理部门名称
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 上午10:35:10
	 */
	int updateUnloadDiffReportDetail(UnloadDiffReportDetailEntity detailEntity);
	
	/**
	 * 获取差异报告明细中的少货明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:59:32
	 */
	List<UnloadDiffReportDetailEntity> queryUnresolvedLackGoodsException(UnloadDiffReportDetailEntity detailEntity);
	
	/**
	 * 新增一条少货差错单记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午2:19:11
	 */
	int addLackGoodsFoundInfo(LackGoodsFoundEntity entity);
	
	/**
	 * 查询某类型(卸车、清仓)少货差错单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午2:54:56
	 */
	List<String> queryNoFoundLackGoodsOAErrorNo(String reportType,Date reportOATime);
	
	/**
	 * 查询某差错单下所有未找到的流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:22:26
	 */
	List<LackGoodsFoundEntity> queryNoFoundLackGoodsDetailByOAErrorNo(String oaErrorNo);
	
	/**
	 * 少货找到后，更新相关字段，参数实体中需包含id
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:31:24
	 */
	int updateUnloadLackGoodsInfo(LackGoodsFoundEntity entity);

	/** 
	 * @Title: queryInStockNumForLack 
	 * @Description: 发现卸车少货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算少货
	 * @param map
	 * @return    
	 * @return int    返回类型 
	 * queryInStockNumForLack
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-23下午4:24:06
	 */ 
	Long queryInStockNumForLack(Map<String, Object> map);

	/** 
	 * @Title: queryUnloadDiffReportById 
	 * @Description: 根据id获取差异报告信息 
	 * @param id
	 * @return    
	 * @return UnloadDiffReportEntity    返回类型 
	 * queryUnloadDiffReportById
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-24上午10:49:44
	 */ 
	UnloadDiffReportEntity queryUnloadDiffReportById(String id);

	/**
	 * @author niuly
	 * @date 2014-6-17上午10:44:00
	 * @function 查询卸车少货且成功上报oa差错
	 * @param waybillNo
	 * @return
	 */
	Long queryLackCountByWaybillNo(String waybillNo);
	/**
	 * @author niuly
	 * @date 2014-6-9下午4:42:42
	 * @function 保存上报OA差错失败日志 
	 * @param logEntity
	 */
	int addReportOaErrorLog(ReportOaErrorLogEntity logEntity);

	/**
	 * <p>通过oa差错编号查询存在于少货找到上报记录表中的少货差错详情</p> 
	 * @author 148246-foss-sunjianbo
	 * @date 2014-10-21 下午7:55:30
	 * @param oaErrorNo
	 * @return
	 * @see
	 */
	List<LackGoodsFoundEntity> queryLackGoodsDetailByOAErrorNo(String oaErrorNo);
	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:26:34
	 * @function 查询接送货卸车需上报的差异数量
	 * @param reportId
	 * @return
	 */
	int queryUnResolvedLackDetailCount(String reportId);

	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:51:03
	 * @function 更新是否需要上报OA差错
	 * @param reportId
	 * @return
	 */
	int updateUnloadDiffReportReportOa(String reportId);
	
	/**
	 * @author nly
	 * @date 2014年12月12日 上午11:08:25
	 * @function 查询接货差异明细
	 * @param reportId
	 * @param timeLimit
	 * @return
	 */
	List<UnloadDiffReportDetailEntity> queryUnResolvedLackDetail(String reportId, BigDecimal timeLimit);
	/**
	 * @author nly
	 * @date 2014年12月13日 上午8:59:13
	 * @function 查询长短途卸车需上报的差异数量
	 * @param reportId
	 * @return
	 */
	int queryUnResolvedDiffReportDetailCount(String reportId);
	/**
	 * @author nly
	 * @date 2014年12月15日 下午5:10:22
	 * @function 根据接送货交接单号查询接送货人所在部门code
	 * @param billNo
	 * @return
	 */
	String queryPickUperOrgCode(String billNo);

	/**
	 * @author nly
	 * @date 2014年12月17日 上午9:36:30
	 * @function 查询少货货物所在接送货交接单号
	 * @param unloadOrgCode
	 * @param handOverBillNoList
	 * @param waybillNo
	 * @return
	 */
	List<String> queryPickUpHandOverNo(String waybillNo,List<String> handOverBillNoList);

	/** 查询夹带的条数
	 * @param taskId
	 * @param wayBillNo
	 * @return
	 * @author 257220
	 * @date 2015-7-21下午2:17:30
	 */
	int queryEntrainedNum(String taskId, String wayBillNo);
	
	/**
	 * 获取所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:10:25
	 */
	List<UnloadDiffReportDetailEntity> queryUnresolvedUnloadSerialNoListByTime(Date startTime,Date endTime);
	
	/**
	 * 插入上报OAreportid至中间表
	 * @author 283244
	 * */
	void insertUnloadtoOABackupsForQMS(String oaReportClearlessmsg,String loadOrgmsg,String unloadEntitymsg);
	/**
	 * 获取中间表数据
	 * @author 283244
	 * */
	List<ReportQMSDataEntity> queryOABackupsForQMSData();
	/**
	 *删除中间表备份数据
	 * @author 283244
	 * */
	void updateUnloadToOAbackups(String lostWarningId);
	
	
	/**
	 * @author 283244
	 * 卸车少货找到至QMS数据备份（按照运单号及流水号入库）
	 * */

	public void insertintoreportFoundDataBacks(String wno, String seriaNO);
}