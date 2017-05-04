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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/UnloadDiffReportDao.java
 *  
 *  FILE NAME          :UnloadDiffReportDao.java
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
package com.deppon.foss.module.transfer.lostwarning.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IUnloadLackDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;

/** 
 * @className: UnloadDiffReportDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告dao类
 * @date: 2012-12-10 上午10:42:19
 * 
 */
public class UnloadLackDiffReportDao extends iBatis3DaoImpl implements IUnloadLackDiffReportDao {
	
	/**
	 * mapper文件命名空间
	 */
	public static final String NAMESPACE = "foss.lostwarning.unloadLackdiffreport.";
	
	
	/**
	 * 查询未处理完毕的差异报告
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 上午9:38:30
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnResolvedDiffReport()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> queryUnResolvedDiffReport(int threadNo, int threadCount,Date bizDate,Date deliverBizDate) {
		//构造查询参数
		Map paramsMap = new HashMap();
		List unloadTypeList = new ArrayList<String>();
		
		/** 只处理长途、短途的卸车差异报告
		 * 此时需要过滤掉其他卸车任务产生的差异报告
		 * 接货卸车差异上报OA差错
		 */
		//长途
		unloadTypeList.add(UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE);
		//短途
		unloadTypeList.add(UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE);
		//二程接驳卸车
		unloadTypeList.add(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS);
		//差异报告状态：处理中
		paramsMap.put("handleStatus", UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVING);
		//卸车类型：长途、短途
		paramsMap.put("unloadTypeList", unloadTypeList);
		paramsMap.put("unloadDeliverType", UnloadConstants.UNLOAD_TASK_TYPE_DELIVER);
		
		//少货类型
		List<String> lackGoodsTypeList = new ArrayList<String>();
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
		
		List<String> lackGoodsTypeListDeliver = new ArrayList<String>();
		lackGoodsTypeListDeliver.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		
		//少货类型
		paramsMap.put("lackGoodsTypeList", lackGoodsTypeList);
		//少货类型DELIVER
		paramsMap.put("lackGoodsTypeListDeliver", lackGoodsTypeListDeliver);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryUnResolvedDiffReport", paramsMap);
	}
	
	/**
	 * @author nly
	 * @date 2014年12月13日 上午8:59:13
	 * @function 查询长短途卸车需上报的差异明细数量
	 * @param reportId
	 * @return
	 */
	@Override
	public int queryUnResolvedDiffReportDetailCount(String reportId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<String> lackGoodsTypeList = new ArrayList<String>();
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
		//少货类型
		paramsMap.put("lackGoodsTypeList", lackGoodsTypeList);
		//差异报告ID
		paramsMap.put("reportId", reportId);
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryUnResolvedDiffReportDetailCount",paramsMap);
	}
	
	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:51:03
	 * @function 更新是否需要上报OA差错
	 * @param reportId
	 * @return
	 */
	@Override
	public int updateUnloadDiffReportReportOa(String reportId) {
		return this.getSqlSession().update(NAMESPACE + "updateUnloadDiffReportReportOa",reportId);
	}
	
	/**
	 * @author wwb
	 * @date 2016年6月27日16:06:44
	 * @function 更新是否需要上报OA差错
	 * @param reportId
	 * @return
	 */
	@Override
	public int addUnloadDiffReportReportIdTemp(String reportId){
		return this.getSqlSession().update(NAMESPACE + "addUnloadDiffReportReportIdToTemp",reportId);
	}

	/**
	 * 根据差异报告ID查询符合上报OA的差异明细
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午8:33:21
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnResolvedDiffReportDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnResolvedDiffReportDetail(
			String reportId,BigDecimal timeLimit) {
		//构造查询参数
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<String> lackGoodsTypeList = new ArrayList<String>();
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
		//少货类型
		paramsMap.put("lackGoodsTypeList", lackGoodsTypeList);
		//差异报告ID
		paramsMap.put("reportId", reportId);
		//少货未处理时限
		paramsMap.put("timeLimit", timeLimit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryUnResolvedDiffReportDetail", paramsMap);
	}

	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:30:25
	 * @function 查询未处理的接货差异数量
	 * @param reportId
	 * @return
	 */
	@Override
	public int queryUnResolvedLackDetailCount(String reportId) {
		//构造查询参数
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		//差异报告ID
		paramsMap.put("reportId", reportId);
		//差异类型
		paramsMap.put("lackGoodsType", UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryUnResolvedLackDetailCount",paramsMap);
	}

	/**
	 * @author nly
	 * @date 2014年12月12日 上午11:08:25
	 * @function 查询接货差异明细
	 * @param reportId
	 * @param timeLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnResolvedLackDetail(String reportId, BigDecimal timeLimit) {
		//构造查询参数
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		//差异报告ID
		paramsMap.put("reportId", reportId);
		//少货未处理时限
		paramsMap.put("timeLimit", timeLimit);
		//差异类型
		paramsMap.put("lackGoodsType", UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		return this.getSqlSession().selectList(NAMESPACE + "queryUnResolvedLackDetail",paramsMap);
	}
	
	/**
	 * @author niuly
	 * @date 2014-6-9下午4:42:42
	 * @function 保存上报OA差错失败日志 
	 * @param logEntity
	 */
	@Override
	public int addReportOaErrorLog(ReportOaErrorLogEntity logEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addReportOaErrorLog",logEntity);
	}
	
	/**
	 * 查询运单是否已经已作废、已中止
	 */
	@Override
	public boolean isInvalidWaybillNo(String waybillNo){
		//构造查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		int num =  (Integer) this.getSqlSession().selectOne(NAMESPACE + "isInvalidWaybillNo",paramsMap);
		if(num>0){
			return true;
		}
		return false;
	}


	/**
	 * 查询运单流水号是否已经已签收
	 */
	@Override
	public boolean isSignedWaybillNoSerialNo(String waybillNo, String serialNo){
		//构造查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		int num =  (Integer)  this.getSqlSession().selectOne(NAMESPACE + "isSignedWaybillNoSerialNo",paramsMap);
		if(num > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询运单流水号是否已经装车扫描
	 */
	@Override
	public boolean isLoadDataWaybillNo(UnloadDiffReportDetailEntity reportDetail){
		//构造查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", reportDetail.getWaybillNo());
		paramsMap.put("serialNo", reportDetail.getSerialNo());
		paramsMap.put("unloadGaprepId", reportDetail.getDiffReportId());
		int num =  (Integer)  this.getSqlSession().selectOne(NAMESPACE + "isLoadDataWaybillNo",paramsMap);
		if(num > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询流水号是否已经无效
	 */
	@Override
	public boolean isInvalidSerialNo(String waybillNo, String serialNo){
		//构造查询参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		int num =  (Integer)  this.getSqlSession().selectOne(NAMESPACE + "isInvalidSerialNo",paramsMap);
		if(num > 0){
			return false;
		}
		return true;
	}
	/**
	  * 查询该运单号是否卸车扫描、单票入库、清仓扫描
	 */
	@Override
	public boolean isSanAndSignleInStockLessGoods(String wayBillNo) {
		int num =  (Integer) this.getSqlSession().selectOne(NAMESPACE + "querySanAndSignleInStockLessGoods",wayBillNo);
		if(num > 0){
			return false;
		}
		return true;
	}

}