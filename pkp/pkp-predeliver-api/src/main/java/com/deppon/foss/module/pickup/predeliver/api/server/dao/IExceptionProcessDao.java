/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.server.dao
 * FILE    NAME: IExceptionProcessDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;


/**
 * 处理异常主数据DAO层
 * @author 043258-foss-zhaobin
 * @date 2012-10-31 上午10:46:23
 */
public interface IExceptionProcessDao 
{
		/**
		 * 
		 * 按照查询条件查询异常信息(分页)
		 * @author 043258-foss-zhaobin
		 * @date 2012-10-31 上午10:51:13
		 */
		List<ExceptionProcessDto> queryExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto,int start, int limit);
		
		/**
		 * 
		 * 按照查询条件查询异常信息(非分页)
		 * @author 043258-foss-zhaobin
		 * @date 2012-11-6 上午10:51:13
		 */
		List<ExceptionProcessDto> queryExceptionProcess(ExceptionProcessConditionDto exceptionProcessConditionDto);

		/**
		 * 
		 * 获得总条数
		 * @author 043258-foss-zhaobin
		 * @date 2012-10-31 上午10:51:38
		 */
		Long queryExceptionProcessInfoCount(ExceptionProcessConditionDto exceptionProcessConditionDto);
		
		/**
		 * 
		 * 获得总条数
		 * @author 043258-foss-zhaobin
		 * @date 2012-10-31 上午10:51:38
		 */
		Long queryExceptionProcessInfoCountLast(ExceptionProcessConditionDto exceptionProcessConditionDto);

		/**
		 * 
		 * 新增异常信息
		 * @author 043258-foss-zhaobin
		 * @date 2012-10-31 上午10:51:53
		 */
		void addExceptionProcessInfo(ExceptionEntity exceptionEntity);
		
		/**
		 * 
		 * 根据异常ID查看异常详情
		 * @author 043258-foss-zhaobin
		 * @date 2012-11-1 上午11:14:33
		 */
		ExceptionProcessDetailDto queryExceptionProcessDetailDto(ExceptionProcessConditionDto exceptionProcessConditionDto);
		
		/**
		 * 处理异常返回预计送货日期
		 *
		 * @author 
		 * @date 
		 */
		Date queryActualFreightInfo(String waybillNo);
		
		
		/**
		 * 
		 * 更新异常信息状态
		 * @author 043258-foss-zhaobin
		 * @date 2012-11-1 下午6:30:13
		 */
		boolean updateExceptionProcessInfo(ExceptionEntity exceptionEntity);
		
		/**
		 * 
		 * 更新预计送货日期
		 */
		
		boolean updateActualFreightInfo(String waybillNo, Date deliverDate);
		
		/**
		 * 
		 * 新增历史处理详情
		 * @author 043258-foss-zhaobin
		 * @date 2012-11-2 下午2:45:20
		 */
		void addExceptionProcessDetail(ExceptionProcessDetailEntity exceptionProcessDetailEntity);
		
		/**
		 * 
		 * 更新异常信息状态
		 * @author 043258-foss-zhaobin
		 * @date 2012-11-17 下午6:30:13
		 */
		boolean updateExceptionGoodsQty(String waybillNo);
		
		/**
		 * 
		 * 查询异常主表信息
		 * @author 043258-foss-zhaobin
		 * @date 2012-12-7 上午9:22:55
		 */
		List<ExceptionEntity> queryExceptionProcessInfo(ExceptionEntity exceptionEntity);
		
		/**
		 * 
		 * 删除异常主信息
		 * @author 043258-foss-zhaobin
		 * @date 2012-12-7 上午9:18:10
		 */
		void deleteExceptionProcessInfo(String exceptionId);
		
		/**
		 * 
		 * 删除异常分录信息
		 * @author 043258-foss-zhaobin
		 * @date 2012-12-7 上午9:18:14
		 */
		void deleteExceptionProcessDetail(String tSrvExceptionId);
		
		/**
		 * 
		 * 是否已完结
		 * @author 043258-foss-zhaobin
		 * @date 2013-1-14 下午2:36:05
		 */
		boolean isOperate(String tSrvExceptionId);
		
		/**
		 * 
		 * 查询处理中运单
		 * @author 043258-
		 *				foss-zhaobin
		 * @date 2013-3-22 
		 *				下午4:42:18
		 * @param waybillNo
		 * @param serialNo
		 * @return
		 * @see
		 */
		int queryHandlingInfo(String waybillNo,String serialNo);

		/**
		 * 根据运单号号和异常处理状态查询异常处理记录
		 *
		 * @author gpz-foss
		 * @date 2015-11-9 下午4:21:57
		 * @param exceptionCondition 
		 * @return List<ExceptionEntity>
		 */
		List<ExceptionEntity> queryExceptionProcessInfoByParams(
				ExceptionEntity exceptionCondition);

	
		/** 
		 * @Title: selectExceptionProcessDetailList 
		 * @Description: 根据异常ID查询异常操作详细信息
		 * @param  exceptionProcessId 异常ID
		 * @return List<ExceptionProcessDetailEntity>    返回类型 
		 * @throws 
		 */
		List<ExceptionProcessDetailEntity> selectExceptionProcessDetailList(String exceptionProcessId);

		/** 
		 * @Title: selectSingleExceptionProcessDetailOfEnd 
		 * @Description: 查询最后一次处理异常信息 
		 * @param @param exceptionProcessId 异常ID
		 * @return ExceptionProcessDetailEntity    返回类型 
		 * @throws 
		 */
		ExceptionProcessDetailEntity selectSingleExceptionProcessDetailOfEnd(String exceptionProcessId);
}