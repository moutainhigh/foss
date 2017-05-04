package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IQmsErrorService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordQmsUnnormalSignJobService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQmsErrorReportService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.RecordUnnormalSignResponseDto;

/**
 * @author: foss-231434-bieyexiong
 * @description: 新增异常线上划责差错 FOSS自动上报QMS	每XX分钟执行一次  </br>
 * @date:2016年02月19日 下午17:00:58
 */
public class RecordQmsUnnormalSignJobService implements IRecordQmsUnnormalSignJobService{

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = 
							LoggerFactory.getLogger(RecordQmsUnnormalSignJobService.class);
	
	/**
	 * 异常签收明细为空
	 */
	private static final String ERROR_INFO = "上报失败：异常签收明细为空(该单号可能正常签收或者未签收)！";
	
	/**
	 * 重复上报
	 */
	private static final String REPEATED_REPORT = "重复上报!";
	
	/***
	 * 记录异常运单 上报QMS的Service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;
	
	/**
	 * 异常线上划责差错 FOSS自动上报QMS service
	 */
	private IRecordQmsUnnormalSignJobService recordQmsUnnormalSignJobService;
	
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * QMS差错上报信息
	 */
	private IQmsErrorService qmsErrorService;
	
	/**
	 * Qms差错自动上报
	 */
	private IQmsErrorReportService qmsErrorReportService;
	
	/***
	 * 上报成功或失败添加日志接口
	 */
	private IShareJobDao shareJobDao;
	
	/**
	 * 新增异常线上划责差错 FOSS自动上报QMS	每XX分钟执行一次
	 * @author: foss-231434-bieyexiong
	 * @date:2016年02月19日 下午17:01:20
	 */
	@Override
	public void processRecordQmsUnnormalSign() {
		//查询要上报的数据
		List<RecordUnnormalSignWaybillDto> unnormalDtos = 
					recordErrorWaybillDao.findRecordUnnormalSignEntitys();
		//如果要上报数据不为空
		if(CollectionUtils.isNotEmpty(unnormalDtos)){
			for(RecordUnnormalSignWaybillDto unnormalDto : unnormalDtos){
				try{
					//上报差错
					recordQmsUnnormalSignJobService.sendUnnormalSignInfo(unnormalDto);
				}catch(BusinessException e){
					//处理异常
					LOGGER.error("运单号" + unnormalDto.getWaybillNo() + 
							"零担异常签收线上划责差错上报QMS失败：" + e.getMessage());
					//记录异常日志
					RecordUnnormalSignResponseDto resDto = new RecordUnnormalSignResponseDto();
					resDto.setResultMsg(e.getErrorCode());
					this.saveReportOALog(unnormalDto,resDto,ReportConstants.REPORT_OA_ERROR);
				}catch(Exception e){
					//处理异常
					LOGGER.error("运单号" + unnormalDto.getWaybillNo() + 
							"零担异常签收线上划责差错上报QMS失败：" + e.getMessage());
					//记录异常日志
					RecordUnnormalSignResponseDto resDto = new RecordUnnormalSignResponseDto();
					resDto.setResultMsg(e.getMessage());
					this.saveReportOALog(unnormalDto,resDto,ReportConstants.REPORT_OA_ERROR);
				}
			}
		}
	}
	
	/**
	 * 上报差错（防止一个单号出现异常而导致所有单号回滚，故将每个单号当成独立的事物，不互相影响）
	 * @author: foss-231434-bieyexiong
	 * @date:2016年02月19日 下午17:32:16
	 */
	@Override
	@Transactional
	public void sendUnnormalSignInfo(RecordUnnormalSignWaybillDto unnormalDto) {
		if(unnormalDto == null){
			LOGGER.error("零担异常签收线上划责自动上报失败，上报数据为空！");
			throw new SignException("上报失败：上报数据为空！");
		}
		RecordUnnormalSignResponseDto resDto = null;
		
		//运单明细
		WaybillEntity waybill = 
				waybillManagerService.queryWaybillBasicByNo(unnormalDto.getWaybillNo());
		String errorInfo = "";
		try{
			//查询上报信息
			errorInfo = qmsErrorService.getLDUnnormalSignQmsInfo(waybill, unnormalDto);
		}catch(BusinessException e){
			//查询信息时上报失败(最有可能是上报过内物短少差错，将其状态修改至无需上报)
			if(!ERROR_INFO.equals(e.getErrorCode())){
				
				//BUG  变更签收时，若没受理，则签收明细未变，查不到异常签收结果，此时依然需要再重新发送异常签收信息
				unnormalDto.setActive("N");
				unnormalDto.setNote(e.getErrorCode());
				unnormalDto.setModifyTime(new Date());
				recordErrorWaybillDao.updateUnnormalEntity(unnormalDto);
				
				//记录异常日志
				resDto = new RecordUnnormalSignResponseDto();
				resDto.setResultMsg(e.getErrorCode());
				this.saveReportOALog(unnormalDto,resDto,ReportConstants.REPORT_OA_ERROR);
			}
			//不需要抛异常回滚，也不需要再发请求了，所以return
			return;
		}
		LOGGER.info("*************运单号：" + unnormalDto.getWaybillNo() + "上报QMS零担异常签收线上划责差错***********start");
		//发送上报请求
		resDto = qmsErrorReportService.reportQmsUnnormalSignError(errorInfo);
		LOGGER.info("*************运单号：" + unnormalDto.getWaybillNo() + "上报QMS零担异常签收线上划责差错***********end");
		//处理返回结果
		if(resDto != null){
			this.processReturnInfo(unnormalDto,resDto);
		}
	}

	/**
	 * 处理QMS返回数据  
	 * @author 231434-bieyexiong
	 * @date 2016-2-25 上午9:18:19
	 * HandleCode=0,上报成功
	 * HandleCode=9,重复上报
	 * HandleCode!=0 && !=9,上报失败
	 */
	private void processReturnInfo(RecordUnnormalSignWaybillDto unnormalDto,
			RecordUnnormalSignResponseDto resDto){
		String isSuccess=StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(resDto.getResult())
				&& ReportConstants.REPORT_RETURN_STATE_SUCCESS.equals(resDto.getResult())) {
			// 上报成功
			isSuccess=ReportConstants.REPORT_OA_SUCCESS;;
			int flag = saveReportOALog(unnormalDto,resDto,isSuccess);
			//此数据无需再上报
			unnormalDto.setActive("N");
			unnormalDto.setNote(resDto.getResultMsg());
			unnormalDto.setModifyTime(new Date());
			recordErrorWaybillDao.updateUnnormalEntity(unnormalDto);
			if (flag == 1) {
				LOGGER.info("#####保存上报QMS成功日志成功！######");
			} else {
				LOGGER.info("#####保存上报QMS成功日志失败！######");
			}
		}else if (StringUtils.isNotEmpty(resDto.getResult())
				&& ReportConstants.REPORT_RETURN_STATE_REPEAT.equals(resDto.getResult())) {
			//数据重复上报
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(unnormalDto,resDto,isSuccess);
			String note = resDto.getResultMsg();
			//返回的消息有“重复上报！”时，将active更新，否则不更新
			if(StringUtils.isNotEmpty(note)
					&& REPEATED_REPORT.equals(note.substring(note.length()-NumberConstants.NUMBER_5))){
				//此数据无需再上报
				unnormalDto.setActive("N");
				unnormalDto.setNote(note);
				unnormalDto.setModifyTime(new Date());
				recordErrorWaybillDao.updateUnnormalEntity(unnormalDto);
			}
			if (flag == 1) {
				LOGGER.info("#####保存上报QMS失败日志成功！######");
			} else {
				LOGGER.info("#####保存上报QMS失败日志失败！######");
			}
		}else if (StringUtils.isNotEmpty(resDto.getResult())) {
			// 上报异常
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(unnormalDto,resDto,isSuccess);
			//更新上报
			if (flag == 1) {
				LOGGER.info("#####保存上报异常日志成功！######");
			} else {
				LOGGER.info("#####保存上报异常日志失败！######");
			}
		}
	}
	
	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param responseDto
	 * @author foss-yuting
	 * @return  
	 * update by 231434 2016-2-24
	 */
	private int saveReportOALog(RecordUnnormalSignWaybillDto unnormalDto,
			RecordUnnormalSignResponseDto resDto,String isSuccess){
		String msg = resDto.getResultMsg();
		if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
			msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
		}
		int flag = this.addReportOaLog(unnormalDto,resDto,msg,isSuccess);
		return flag;
	}

	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param lostCargoReportDto
	 * @author foss-yuting
	 * @return  
	 * update by 231434 2016-2-24
	 */
	private int addReportOaLog(RecordUnnormalSignWaybillDto unnormalDto,
			RecordUnnormalSignResponseDto resDto,String msg,String isSuccess){
		if(StringUtils.isNotEmpty(isSuccess) && isSuccess.equals(ReportConstants.REPORT_OA_SUCCESS)){
			JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
			// job名称
			jobSuccessLogEntity.setJobName(SignConstants.UNNORMAL_SIGN_NOTIFY_PROCESS_JOB);
			jobSuccessLogEntity.setCreateTime(new Date());
			//调用QMS接口成功返回差错编号
			jobSuccessLogEntity.setSuccessMsg("异常签收自动上报成功，运单号:" + unnormalDto.getWaybillNo() + ";" + msg);
			jobSuccessLogEntity.setSuccessId(unnormalDto.getId());
			return shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
		}else{
			JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
			// job名称
			jobExceptionLogEntity.setJobName(SignConstants.UNNORMAL_SIGN_NOTIFY_PROCESS_JOB);
			jobExceptionLogEntity.setCreateTime(new Date());
			//调用QMS接口失败返回失败消息
			jobExceptionLogEntity.setErrorMsg("异常签收自动上报失败，运单号:" + unnormalDto.getWaybillNo() + ";" +msg);
			jobExceptionLogEntity.setErrorId(unnormalDto.getId());
			return shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
		}
	}

	public void setRecordErrorWaybillDao(
			IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}

	public void setRecordQmsUnnormalSignJobService(
			IRecordQmsUnnormalSignJobService recordQmsUnnormalSignJobService) {
		this.recordQmsUnnormalSignJobService = recordQmsUnnormalSignJobService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setQmsErrorService(IQmsErrorService qmsErrorService) {
		this.qmsErrorService = qmsErrorService;
	}

	public void setQmsErrorReportService(
			IQmsErrorReportService qmsErrorReportService) {
		this.qmsErrorReportService = qmsErrorReportService;
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

}
