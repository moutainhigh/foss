package com.deppon.foss.module.pickup.sign.server.process;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.oa.module.errors.webservice.bean.objectwithinshort.ObjectWithinShortResponse;
import com.deppon.oa.module.errors.webservice.bean.objectwithinshort.ObjectWithinShortResponseDetail;
/**
 * JMS远端服务请求回调处理
 * 异常内物短少差错上报返回处理 <br/>
 * @date 2015-1-07 下午5:07:42
 */

public class ShortGoodsReportProcess implements ICallBackProcess {
	/**
	 * 日志
	 */
	protected final static Logger LOGGER = LoggerFactory.getLogger(ShortGoodsReportProcess.class.getName());
	
	
	/***
	 * 上报成功或失败添加日志接口
	 */
	private IShareJobDao shareJobDao;
	
	/***
	 * 内物短少差错 service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;
	
	
	/**
	 * esb回调foss oa上报差错接口
	 */
	@Override
	public void callback(Object response) throws ESBException {
		if(response!=null){
			Thread current = Thread.currentThread(); 
			LOGGER.info("############处理接口返回结果 start############当前处理线程:"+current.getName());
			ObjectWithinShortResponse _response=(ObjectWithinShortResponse) response;
			ObjectWithinShortResponseDetail responseDto = _response.getObjectWithinShortResponseDetail().get(0);
			processReturnInfo(responseDto);
			LOGGER.info("############处理接口返回结果 end############当前处理线程:"+current.getName());
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		if(errorResponse!=null){
			Thread current = Thread.currentThread(); 
			LOGGER.info("############处理接口返回结果 start############当前处理线程:"+current.getName());
			ObjectWithinShortResponse _response=(ObjectWithinShortResponse) errorResponse;
			ObjectWithinShortResponseDetail responseDto = _response.getObjectWithinShortResponseDetail().get(0);
			processReturnInfo(responseDto);
			LOGGER.info("############处理接口返回结果 end############当前处理线程:"+current.getName());
		}
	}
	
	/**
	 * 处理OA返回数据  </br>
	 * @date 2014-12-15 下午3:58:10
	 * @param com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto
	 * @see com.deppon.foss.module.pickup.sign.server.process.ShortGoodsReportApplicationOa.processReturnInfo(com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto)
	 */
	private void processReturnInfo(ObjectWithinShortResponseDetail responseDto) {
		String isSuccess=StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(responseDto.getReportResult())&&responseDto.getReportResult().equals(ReportConstants.REPORT_RETURN_STATE_ERROR)) {
			// 上报异常
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(responseDto,isSuccess);
			//更新上报
			if (flag == 1) {
				LOGGER.info("#####保存上报异常日志成功！######");
			} else {
				LOGGER.info("#####保存上报异常日志失败！######");
			}
		} else if (StringUtils.isNotEmpty(responseDto.getReportResult())&&responseDto.getReportResult().equals(ReportConstants.REPORT_RETURN_STATE_SUCCESS)) {
			// 上报成功
			// 上报异常
			isSuccess=ReportConstants.REPORT_OA_SUCCESS;;
			int flag = saveReportOALog(responseDto,isSuccess);
			//此数据无需再上报
			recordErrorWaybillDao.updateEntity(responseDto.getUniqueid());
			if (flag == 1) {
				LOGGER.info("#####保存上报OA成功日志成功！######");
			} else {
				LOGGER.info("#####保存上报OA成功日志失败！######");
			}
		}else if (StringUtils.isNotEmpty(responseDto.getReportResult())&&responseDto.getReportResult().equals(ReportConstants.REPORT_RETURN_STATE_REPEAT)) {
			//数据重复上报
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(responseDto,isSuccess);
			//此数据无需再上报
			recordErrorWaybillDao.updateEntity(responseDto.getUniqueid());
			if (flag == 1) {
				LOGGER.info("#####保存上报OA失败日志成功！######");
			} else {
				LOGGER.info("#####保存上报OA失败日志失败！######");
			}
		}
	}
	
	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param responseDto
	 * @author foss-yuting
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.process.ShortGoodsReportApplicationOa#saveReportOALog(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto)
	 */
	private int saveReportOALog(ObjectWithinShortResponseDetail responseDto,String isSuccess) {
		String msg = responseDto.getDealStatus();
		if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
			msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
		}
		int flag = this.addReportOaLog(responseDto, msg, isSuccess);
		return flag;
	}

	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param lostCargoReportDto
	 * @author foss-yuting
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.process.ShortGoodsReportApplicationOa#addReportOaLog(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto)
	 */
	private int addReportOaLog(ObjectWithinShortResponseDetail responseDto, String msg,String isSuccess){
		String waybillNo=StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(responseDto.getUniqueid())){
			//查看当前的运单 返回运单号 
			RecordErrorWaybillResultDto result = recordErrorWaybillDao.getRecordErrorWaybill(responseDto.getUniqueid());
			waybillNo = result.getWaybillNo();
		}
		if(StringUtils.isNotEmpty(isSuccess)&&isSuccess.equals(ReportConstants.REPORT_OA_SUCCESS)){
			JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
			jobSuccessLogEntity.setJobName(SignConstants.EXCEPTION_SHORT_NOTIFY_PROCESS_JOB);// job名称
			jobSuccessLogEntity.setCreateTime(new Date());
			jobSuccessLogEntity.setSuccessMsg("差错编号:" + responseDto.getHandlingid() + ";运单号:" + waybillNo + ";" + msg); // 调用OA接口成功返回差错编号
			jobSuccessLogEntity.setSuccessId(responseDto.getUniqueid());
			return shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
		}else{
			JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
			jobExceptionLogEntity.setJobName(SignConstants.EXCEPTION_SHORT_NOTIFY_PROCESS_JOB);// job名称
			jobExceptionLogEntity.setErrorMsg(msg + ";运单号:" + waybillNo);
			jobExceptionLogEntity.setErrorId(responseDto.getUniqueid());
			jobExceptionLogEntity.setCreateTime(new Date());
			return shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
		}
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setRecordErrorWaybillDao(
			IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}
	
}
