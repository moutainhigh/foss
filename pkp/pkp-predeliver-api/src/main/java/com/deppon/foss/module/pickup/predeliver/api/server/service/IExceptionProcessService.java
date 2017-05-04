/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.server.service
 * FILE    NAME: IExceptionProcessService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionOperateDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SerialNoDto;


/**
 * 处理异常主数据Service层
 * @author 043258-foss-zhaobin
 * @date 2012-10-31 上午10:52:30
 */
public interface IExceptionProcessService extends IService
{
	/**
	 * 
	 * 按照查询条件查询异常信息
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
	InputStream queryExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto);

	/**
	 * 
	 * 获得总条数
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-31 上午10:51:38
	 */
	Long queryExceptionProcessInfoCount(ExceptionProcessConditionDto exceptionProcessConditionDto);

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
	ExceptionProcessDetailDto queryExceptionProcessDetailDto(String exceptionProcessid);
	
	/**
	 * 
	 * 更新异常信息状态
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-1 下午6:30:13
	 */
	boolean updateExceptionProcessInfo(ExceptionEntity exceptionEntity);
	
	/**
	 * 
	 * 新增历史处理详情
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-2 下午2:45:20
	 */
	void addExceptionProcessDetail(String tSrvExceptionId , String notes , String waybillNo , Date deliverDate);
	
	/**
	 * 
	 * 处理历史处理信息 
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 上午8:19:20
	 */
	void operateExceptionProcessDetail(String tSrvExceptionId , String notes , String exceptionType, String exceptionLink , String waybillNo, String arrivesheetId ,Date deliverDate);
	
	/**
	 * 
	 * 新增异常信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 下午3:52:46
	 */
	void addExceptionProcessInfo(ExceptionProcessConditionDto exceptionProcessConditionDto);

	/**
	 * 
	 * 新增历史处理详情
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-5 下午2:45:20
	 */
	void addExceptionProcessDetail(ExceptionProcessDetailEntity exceptionProcessDetailEntity);

	/**
	 * 
	 * 按照查询条件查询异常信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-31 上午10:51:13
	 */
	List<ExceptionProcessDto> queryExceptionProcess(ExceptionProcessConditionDto exceptionProcessConditionDto);

	/**
	 * 
	 * 删除客户自提异常信息(2王飞)
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-7 上午9:58:43
	 */
	void deleteExceptionProcessInfo2Pickup(ExceptionEntity exceptionEntity);
	
	/**
	 * 
	 * 处理异常提供给中转调用
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-7 下午3:39:05
	 */
	void operateExceptionProcessInfo(ExceptionOperateDto exceptionOperateDto);
	
	/**
	 * 
	 * 新增通知信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-15 上午10:56:56
	 */
	void addNotice(String receiveOrgCode,  ExceptionProcessDetailDto  noticeMap);
	
	/**
	 * 
	 * 查询运单流水号
	 * @author 043258-foss-zhaobin
	 * @date 2013-4-23 上午9:23:14
	 * @param waybillNo
	 * @return
	 * @see
	 */
	List<SerialNoDto> querySerialNos(String waybillNo);
	
	/**
	 * 
	 * 添加异常信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午2:25:21
	 */
	void addException(ExceptionEntity exceptionEntity);
	
	/**
	 * 
	 * 批量处理异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午5:34:10
	 */
	void batchProcess(List<ExceptionProcessDto> exceptionProcessDtos);
	
	/**
	 * 
	 * 批量处理完毕异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午5:34:10
	 */
	void batchComplete(List<ExceptionProcessDto> exceptionProcessDtos);
	
	/**
	 * 
	 * 根据运单号查询处理异常信息.
	 * 
	 * @param exceptionProcessConditionDto
	 *            查询条件
	 * @author foss-sunyanfei
	 * @date 2015-8-4 上午9:32:27
	 */
	List<ExceptionProcessDto> queryExceptionProcessListByCondition(ExceptionProcessConditionDto exceptionProcessConditionDto, int start, int limit);

	/**
	 * 修改处理异常状态
	 * @author foss-gpz
	 * @date 2015-10-21 下午5:20:58
	 * @param waybillNo 运单号
	 */
	boolean updateExceptionProcessStatus(String waybillNo);
	
	/** 
	 * @Title: selectExceptionProcessDetailList 
	 * @Description: 根据异常Id查询异常操作详细信息
	 * @param exceptionProcessId 异常Id
	 * @return List<ExceptionProcessDetailEntity>    返回类型 
	 * @throws 
	 */
	List<ExceptionProcessDetailEntity> selectExceptionProcessDetailList(String exceptionProcessId);
	
	/** 
	 * @Title: operateExceptionAndNotifyCustomer 
	 * @Description: 通知并处理完毕
	 * @param @param exceptionProcessDetailDto  异常处理历史信息Dto
	 * @return void    返回类型 
	 * @throws 
	 */
	void operateExceptionAndNotifyCustomer(ExceptionProcessDetailDto exceptionProcessDetailDto);
}