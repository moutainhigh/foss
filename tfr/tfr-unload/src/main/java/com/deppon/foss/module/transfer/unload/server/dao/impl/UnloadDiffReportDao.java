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
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportQMSDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: UnloadDiffReportDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告dao类
 * @date: 2012-12-10 上午10:42:19
 * 
 */
public class UnloadDiffReportDao extends iBatis3DaoImpl implements IUnloadDiffReportDao {
	
	/**
	 * mapper文件命名空间
	 */
	public static final String NAMESPACE = "foss.unload.unloaddiffreport.";

	/**
	 * 查询卸车差异报告
	 * @author 045923-foss-shiwei
	 * @param queryCondition 查询条件dto对象
	 * @date 2012-12-10 上午10:56:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportList(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportEntity> queryUnloadDiffReportList(QueryUnloadDiffReportConditionDto queryCondition, int limit,int start) {
		//分页
//		RowBounds rowBound = new RowBounds(start,limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadDiffReportList",queryCondition);
	}

	/**
	 * 获取查询的卸车差异报告总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午11:18:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportCount(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto)
	 */
	@Override
	public Long queryUnloadDiffReportCount(QueryUnloadDiffReportConditionDto queryCondition) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryUnloadDiffReportCount",queryCondition);
	}

	/**
	 * 根据卸车差异报告编号获取报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午10:42:47
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportBasicInfo(java.lang.String)
	 */
	@Override
	public UnloadDiffReportEntity queryUnloadDiffReportBasicInfo(String diffReportNo) {
		//返回查询结果
		
		List<UnloadDiffReportEntity> resultList=new ArrayList<UnloadDiffReportEntity>();
		resultList=this.getSqlSession().selectList(NAMESPACE+"queryUnloadDiffReportBasicInfo",diffReportNo);
		if(CollectionUtils.isNotEmpty(resultList)){

			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据卸车差异报告ID获取报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2013-2-22 下午5:34:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportBasicInfoById(java.lang.String)
	 */
	@Override
	public UnloadDiffReportEntity queryUnloadDiffReportBasicInfoById(String diffReportId) {
		//返回查询结果
		return (UnloadDiffReportEntity) this.getSqlSession().selectList(NAMESPACE+"queryUnloadDiffReportBasicInfoByID",diffReportId).get(0);
	}

	/**
	 * 根据卸车差异报告ID获取差异报告详情
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午11:05:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnloadDiffReportDetail(String diffReportId) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadDiffReportDetail",diffReportId);
	}

	/**
	 * 获取所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:10:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnresolvedUnloadSerialNoList(Date bizJobStartTime,int minutes){
		//返回查询结果
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bizJobStartTime", bizJobStartTime);
		map.put("minutes", minutes);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnresolvedUnloadSerialNoList",map);
	}
	
	/**
	 * 插入一条卸车差异报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 上午1:08:49
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#addUnloadDiffReport(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity)
	 */
	@Override
	public int addUnloadDiffReport(UnloadDiffReportEntity baseEntity) {
		//新增差异报告基本信息
		this.getSqlSession().insert(NAMESPACE+"addUnloadDiffReport",baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 生成卸车差异报告时，批量新增差异报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:20:32
	 */
	@Override
	public int addUnloadDiffReportBasicInfo(Collection<UnloadDiffReportEntity> baseEntityList) {
		//新增差异报告基本信息
		this.getSqlSession().insert(NAMESPACE+"addUnloadDiffReportBasicInfo",baseEntityList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 生成卸车差异报告时，批量插入差异报告流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:21:30
	 */
	@Override
	public int addUnloadDiffReportDetailList(List<UnloadDiffReportDetailEntity> detailList) {
		//插入卸车差异报告流水号明细
		this.getSqlSession().insert(NAMESPACE+"addUnloadDiffReportDetailList",detailList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 插入一条差异报告流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 上午1:06:57
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#addUnloadDiffReportDetail(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity)
	 */
	@Override
	public int addUnloadDiffReportDetail(UnloadDiffReportDetailEntity detail) {
		//插入卸车差异报告流水号明细
		this.getSqlSession().insert(NAMESPACE+"addUnloadDiffReportDetail",detail);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
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
		/*
		 * 只处理长途、短途的卸车差异报告
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
		paramsMap.put("threadCount", threadCount-1);
		paramsMap.put("threadNo", threadNo);
//		paramsMap.put("bizDate", bizDate);
		paramsMap.put("unloadDeliverType", UnloadConstants.UNLOAD_TASK_TYPE_DELIVER);
//		paramsMap.put("deliverBizDate", deliverBizDate);
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
		//2016/06/01更改，此处不再需要少货记录
		List<String> lackGoodsTypeList = new ArrayList<String>();
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
		//少货类型
		paramsMap.put("lackGoodsTypeList", lackGoodsTypeList);
		//多货类型
		paramsMap.put("moreGoodsType", UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS);
		//差异报告ID
		paramsMap.put("reportId", reportId);
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryUnResolvedDiffReportDetailCount",paramsMap);
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
		//更改于2016/06/01 此处不再需要少货
		List<String> lackGoodsTypeList = new ArrayList<String>();
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		lackGoodsTypeList.add(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
		//少货类型
		paramsMap.put("lackGoodsTypeList", lackGoodsTypeList);
		//多货类型
		paramsMap.put("moreGoodsType", UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS);
		//差异报告ID
		paramsMap.put("reportId", reportId);
		//少货未处理时限
		paramsMap.put("timeLimit", timeLimit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryUnResolvedDiffReportDetail", paramsMap);
	}

	/**
	 * 根据ID更新卸车差异报告明细，可能更新的字段包括：
	 * 异常处理时间、备注、处理人编号、处理人名称、处理部门编号、处理部门名称
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 上午10:40:29
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#updateUnloadDiffReportDetail(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity)
	 */
	@Override
	public int updateUnloadDiffReportDetail(UnloadDiffReportDetailEntity detailEntity) {
		//更新差异报告明细
		this.getSqlSession().update(NAMESPACE + "updateUnloadDiffReportDetail",detailEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 获取差异报告明细中的少货明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午4:00:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnresolvedLackGoodsException(UnloadDiffReportDetailEntity detailEntity){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryUnresolvedLackGoodsException",detailEntity);
	}

	/**
	 * 将卸车差异报告的处理状态更新为“已处理”
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:44:46
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#updateUnloadDiffReportHandleState(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateUnloadDiffReportHandleState(String diffReportId,
			String handleState) {
		//构造参数
		Map<String,String> map = new HashMap<String,String>();
		//差异报告ID
		map.put("diffReportId", diffReportId);
		//处理状态：已处理
		map.put("handleState", handleState);
		//update
		this.getSqlSession().update(NAMESPACE + "updateUnloadDiffReportHandleState",map);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增一条少货差错单记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午2:20:00
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#addLackGoodsFoundInfo(com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity)
	 */
	@Override
	public int addLackGoodsFoundInfo(LackGoodsFoundEntity entity) {
		this.getSqlSession().insert(NAMESPACE + "addLackGoodsFoundInfo",entity);
		return FossConstants.SUCCESS;
	}

	/**
	 * 查询某类型(卸车、清仓)少货差错单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午2:56:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryNoFoundLackGoodsOAErrorNo(java.lang.String, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryNoFoundLackGoodsOAErrorNo(String reportType,
			Date reportOATime) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("reportType", reportType);
		params.put("reportOATime", reportOATime);
		return this.getSqlSession().selectList(NAMESPACE + "queryNoFoundLackGoodsOAErrorNo",params);
	}
	
	/**
	 * 查询某差错单下所有未找到的流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:22:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LackGoodsFoundEntity> queryNoFoundLackGoodsDetailByOAErrorNo(String oaErrorNo){
		return this.getSqlSession().selectList(NAMESPACE + "queryNoFoundLackGoodsDetailByOAErrorNo",oaErrorNo);
	}
	
	/**
	 * 少货找到后，更新相关字段，参数实体中需包含id
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:31:24
	 */
	@Override
	public int updateUnloadLackGoodsInfo(LackGoodsFoundEntity entity){
		this.getSqlSession().update(NAMESPACE + "updateUnloadLackGoodsInfo",entity);
		return FossConstants.SUCCESS;
	}

	/** 
	 * @Title: queryInStockNumForLack 
	 * @Description: 发现卸车少货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算少货
	 * @param map
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryInStockNumForLack(java.util.Map)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-23下午4:24:19
	 */ 
	@Override
	public Long queryInStockNumForLack(Map<String, Object> map) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryInStockNumForLack", map);
	}

	/** 
	 * @Title: queryUnloadDiffReportById 
	 * @Description: 根据id获取差异报告信息 
	 * @param id
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryUnloadDiffReportById(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-24上午10:49:55
	 */ 
	@Override
	public UnloadDiffReportEntity queryUnloadDiffReportById(String id) {
		//返回查询结果
		return (UnloadDiffReportEntity) this.getSqlSession().selectOne(NAMESPACE+"queryUnloadDiffReportById", id);
	}

	/**
	 * @author niuly
	 * @date 2014-6-17上午10:44:00
	 * @function 查询卸车少货且成功上报oa差错
	 * @param waybillNo
	 * @return
	 */
	@Override
	public Long queryLackCountByWaybillNo(String waybillNo) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryLackCountByWaybillNo", waybillNo);
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
	 * <p>通过oa差错编号查询存在于少货找到上报记录表中的少货差错详情</p> 
	 * @author 148246-foss-sunjianbo
	 * @date 2014-10-21 下午7:56:18
	 * @param oaErrorNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryLackGoodsDetailByOAErrorNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LackGoodsFoundEntity> queryLackGoodsDetailByOAErrorNo(String oaErrorNo){
		return this.getSqlSession().selectList(NAMESPACE + "queryLackGoodsDetailByOAErrorNo",oaErrorNo);
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
		//差异类型
		paramsMap.put("lackGoodsType", UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
		//少货未处理时限
		paramsMap.put("timeLimit", timeLimit);		
		return this.getSqlSession().selectList(NAMESPACE + "queryUnResolvedLackDetail",paramsMap);
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
	 * @author nly
	 * @date 2014年12月15日 下午5:10:22
	 * @function 根据接送货交接单号查询接送货人所在部门code
	 * @param billNo
	 * @return
	 */
	@Override
	public String queryPickUperOrgCode(String billNo) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryPickUperOrgCode", billNo);
	}
	
	/**
	 * @author nly
	 * @date 2014年12月17日 上午9:36:30
	 * @function 查询少货货物所在接送货交接单号
	 * @param unloadOrgCode
	 * @param handOverBillNoList
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryPickUpHandOverNo(String waybillNo,List<String> handOverBillNoList){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("handOverBillNoList", handOverBillNoList);
		return this.getSqlSession().selectList(NAMESPACE + "queryPickUpHandOverNo", map);
	}
	/**
	 * 查询夹带的条数
	 * @param taskId
	 * @param wayBillNo
	 * @return
	 * @author 257220
	 * @date 2015-7-21下午2:06:27
	 */
	public int queryEntrainedNum(String taskId,String wayBillNo){
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", taskId);
		map.put("wayBillNo", wayBillNo);
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryGoodsState", map);
	}
	
	/**
	 * 获取间隔时间所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
	 * @author 257220
	 * @date 2015-10-30 下午5:10:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnresolvedUnloadSerialNoListByTime(Date startTime,Date endTime){
		//返回查询结果
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnresolvedUnloadSerialNoListOne",map);
	}
	/**
	 * 备份上报OAreportid，为上报QMS做数据源
	 * @author 283244
	 * */
	@Override
	public void insertUnloadtoOABackupsForQMS(String oaReportClearlessmsg,String loadOrgmsg,String unloadEntitymsg) {
		Map<String,String> mapdata=new HashMap<String,String>();
		mapdata.put("oaReportClearlessmsg", oaReportClearlessmsg);
		mapdata.put("loadOrgmsg", loadOrgmsg);
		mapdata.put("unloadEntitymsg", unloadEntitymsg);
		this.getSqlSession().insert(NAMESPACE+"insertUnloadtoOABackupsForQMS",mapdata);
	}
	/**
	 * 获取上报OA数据，用于上报QMS
	 * @author 283244
	 * */
	@Override
	public List<ReportQMSDataEntity> queryOABackupsForQMSData() {
		return this.getSqlSession().selectList(NAMESPACE + "queryOABackupsForQMSData");
	}

	@Override
	public void updateUnloadToOAbackups(String lostWarningId) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("lostWarningId", lostWarningId);
		this.getSqlSession().update(NAMESPACE+"updateUnloadToOAbackups",dataMap);
		
		
	}
	/**
	 * @author 283244
	 * 卸车少货找到至QMS数据备份（按照运单号及流水号入库）
	 * */

	@Override
	public void insertintoreportFoundDataBacks(String wno, String seriaNO) {
		Map<String,String> dataMap=new HashMap<String,String>();
		dataMap.put("wno", wno);
		dataMap.put("seriaNO", seriaNO);
		this.getSqlSession().insert(NAMESPACE+"insertintoreportFoundDataBacks",dataMap);
		
	}
}