/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.server.action
 * FILE    NAME: ExceptionProcessAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.predeliver.server.action;


import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SerialNoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ExceptionProcessVo;

/**
 * 
 *
 * 处理异常Action
 * @author 043258-foss-zhaobin
 * @date 2012-10-31 上午11:22:35
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class ExceptionProcessAction extends AbstractAction
{
	/**
	 * serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 处理异常Vo
	 */
	private ExceptionProcessVo vo;
	/**
	 * 处理异常Service
	 */
	private IExceptionProcessService exceptionProcessService;
	
	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public ExceptionProcessVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(ExceptionProcessVo vo) {
		this.vo = vo;
	}

	/**
	 * Sets the exception process service.
	 *
	 * @param exceptionProcessService the new exception process service
	 */
	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	
	private String exceptionProcessId;
	private List<ExceptionProcessDetailEntity> exceptionProcessDetailList;
	
	public List<ExceptionProcessDetailEntity> getExceptionProcessDetailList() {
		return exceptionProcessDetailList;
	}

	public void setExceptionProcessDetailList(
			List<ExceptionProcessDetailEntity> exceptionProcessDetailList) {
		this.exceptionProcessDetailList = exceptionProcessDetailList;
	}

	public String getExceptionProcessId() {
		return exceptionProcessId;
	}

	public void setExceptionProcessId(String exceptionProcessId) {
		this.exceptionProcessId = exceptionProcessId;
	}
	
	/**
	 * 查询异常主数据.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 上午8:14:50
	 * @since
	 * @version
	 */
	@JSON 
	public String queryExceptionProcessInfo(){
		try {
			//获得总条数
			Long totalCount = exceptionProcessService.queryExceptionProcessInfoCount(vo.getExceptionProcessConditionDto());
			if(totalCount != null && totalCount.intValue() > 0)
			{
				//查询异常List
				List<ExceptionProcessDto> list = exceptionProcessService.queryExceptionProcessInfo(vo.getExceptionProcessConditionDto(), this.start,this.limit);
//				List<ExceptionProcessDto> list = exceptionProcessService.queryExceptionProcessListByCondition(vo.getExceptionProcessConditionDto(), this.start,this.limit);
				
				//设置vo
				vo.setExceptionProcessDtoList(list);
			}else
			{
				vo.setExceptionProcessDtoList(null);
			}
			//设置总条数
			this.setTotalCount(totalCount);
		} catch (ExceptionProcessException e) {
			//返回异常
			returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 查询异常处理记录.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 上午8:15:14
	 */
	@JSON 
	public String queryExceptionProcessDetailInfo(){
		try {
			//根据异常id查询异常处理记录
			ExceptionProcessDetailDto exceptionProcessDetailDto = exceptionProcessService.queryExceptionProcessDetailDto(vo.getExceptionProcessConditionDto().getTSrvExceptionId());
			//设置vo
			vo.setExceptionProcessDetailDto(exceptionProcessDetailDto);
		} catch (ExceptionProcessException e) {
			//返回异常
			returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/** 
	 * @Title: findExceptionProcessDetailList 
	 * @Description: 根据异常id查新异常操作信息
	 * @return String    返回类型 
	 * @throws 
	 */
	public String findExceptionProcessDetailList() {
		try {
			exceptionProcessDetailList = exceptionProcessService.selectExceptionProcessDetailList(exceptionProcessId);
		} catch (ExceptionProcessException e) {
			//返回异常
			returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 保存历史处理信息.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-2 下午2:57:05
	 * @since
	 * @version
	 */
	@JSON 
	public String addExceptionProcessDetailInfo(){
		try {
			//如果vo不为空
			if (vo != null) {
				//新增历史处理信息
				this.exceptionProcessService.addExceptionProcessDetail(vo.getExceptionProcessConditionDto().getTSrvExceptionId(),
						vo.getExceptionProcessConditionDto().getNotes(),vo.getExceptionProcessConditionDto().getWaybillNo(),
						vo.getExceptionProcessConditionDto().getDeliverDate());
			}
		  //捕获异常信息
		} catch (ExceptionProcessException e) {
			//返回错误信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/**
	 * 处理历史处理信息.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 下午2:57:05
	 * @since
	 * @version
	 */
	@JSON 
	public String operateExceptionProcessDetailInfo(){
		try {
			//如果vo不为空
			if (vo != null) {
				//新增处理异常信息
				this.exceptionProcessService.operateExceptionProcessDetail(vo.getExceptionProcessConditionDto().getTSrvExceptionId(),
						vo.getExceptionProcessConditionDto().getNotes(),vo.getExceptionProcessConditionDto().getExceptionType(),
						vo.getExceptionProcessConditionDto().getExceptionLink(),vo.getExceptionProcessConditionDto().getWaybillNo(),
						vo.getExceptionProcessConditionDto().getArrivesheetId(),vo.getExceptionProcessConditionDto().getDeliverDate());
			}
			//捕获异常信息
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/** 
	 * @Title: operateExceptionAndNotifyCustomer 
	 * @Description: 通知客户并处理完毕
	 * @return String    返回类型 
	 * @throws 
	 */
	public String operateExceptionAndNotifyCustomer() {
		try {
			if (vo == null) {
				throw new ExceptionProcessException("传入参数不能为空！");
			}
			exceptionProcessService.operateExceptionAndNotifyCustomer(vo.getExceptionProcessDetailDto());
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/**
	 * 新增异常主数据.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-3 下午3:45:21
 	 * @since
	 * @version
	 */
	@JSON 
	public String addExceptionProcessInfo(){
		try {
			//如果vo不为空
			if (vo != null) {
				//新增异常主数据
				this.exceptionProcessService.addExceptionProcessInfo(vo.getExceptionProcessConditionDto());
			}
			//捕获异常信息
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/**
	 * 新增通知.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-15 上午10:55:23
 	 * @since
	 * @version
	 */
	@JSON 
	public String addNotice(){
		try {
			//如果vo不为空
			if (vo != null) {
				//新增平台通知信息
				this.exceptionProcessService.addNotice(vo.getExceptionProcessDetailDto().getReceiveOrgCode(), vo.getExceptionProcessDetailDto());
			}
			//捕获异常信息
		} catch (BusinessException e) {
			//返回异常信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/**
	 * 
	 * 查询运单流水号 
	 * @author 043258-foss-zhaobin
	 * @date 2013-4-23 上午9:20:57
	 * @return
	 * @see
	 */
	@JSON 
	public String querySerialNos(){
		try {
			//如果vo不为空
			if (vo != null) {
				List<SerialNoDto> stockDtoList = exceptionProcessService.querySerialNos(vo.getExceptionProcessConditionDto().getWaybillNo());
				vo.setStockDtoList(stockDtoList);
			}
			//捕获异常信息
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		//成功
		return returnSuccess();	
	}
	
	/**
	 * 
	 * 批量处理异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午4:36:57
	 */
	@JSON
	public String batchProcess(){
		try {
			exceptionProcessService.batchProcess(vo.getExceptionProcessDtoList());
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 
	 * 批量处理完毕异常
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-21 下午4:36:57
	 */
	@JSON
	public String batchComplete(){
		try {
			exceptionProcessService.batchComplete(vo.getExceptionProcessDtoList());
		} catch (ExceptionProcessException e) {
			//返回异常信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	@JSON
	public String queryCity(){
		vo = new ExceptionProcessVo();
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		if(null != org){
			vo.setCity(org.getCityCode());
		}
		return returnSuccess();
	}
}