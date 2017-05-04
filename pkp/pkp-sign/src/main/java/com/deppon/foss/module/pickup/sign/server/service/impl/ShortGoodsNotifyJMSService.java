package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IQmsErrorService;
import com.deppon.foss.module.pickup.sign.api.server.service.IShortGoodsNotifyJMSService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQmsErrorReportService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.QmsErrorReportResponseDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.ShortGoodsNotifyJMSService
 * @author: foss-yuting
 * @description: 新增内物短少差错-FOSS自动上报	每10分钟执行一次   </br>
 * @date:2014年12月3日 下午2:27:21
 */
public class ShortGoodsNotifyJMSService implements IShortGoodsNotifyJMSService {

	public static final String LOSTCARGO_MANAGE_DEPT_CODE="W01000301050203";

	/**
	 * 日志
	 */
	protected final static Logger logger = 
			LoggerFactory.getLogger(ShortGoodsNotifyJMSService.class.getName());

	/***
	 * 内物短少差错 service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;

	/***
	 * 上报OA接口
	 */ 
	private IWaybillDao waybillDao;

	/**
	 * 派送排单Service接口
	 */
	private IDeliverbillService deliverbillService;

	/**
	 * 人员 Service接口
	 */
	private IEmployeeService employeeService;

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
	 * 快递运单服务
	 */
	private IWaybillExpressService waybillExpressService;

	/**
	 * 新增内物短少差错-FOSS自动上报	每10分钟执行一次   </br>
	 * @date 2014-12-3 下午3:58:10
	 * @param oaReportClearless
	 * @author foss-yuting
	 * @return  
	 * update by 231434 2015-7-7
	 */
	@Override
	@Transactional
	public void processNotifyShortGoods(){
		List<RecordErrorWaybillResultDto> recordDtoList = 
				recordErrorWaybillDao.findReportOAExceptionList();
		if(CollectionUtils.isNotEmpty(recordDtoList)){
			QmsErrorReportResponseDto resDto = null;
			for(RecordErrorWaybillResultDto recordDto : recordDtoList){
				String waybillNo = recordDto.getWaybillNo();
				//根据运单号 关联运单表中的数据
				WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
				if(waybillEntity!=null){
					//查询派送信息
					DeliverbillDto deliverbillDto = new DeliverbillDto();
					deliverbillDto.setWaybillNo(recordDto.getWaybillNo());
					List<DeliverbillDto> deliverbilldtoList =
							deliverbillService.queryPartDeliverbillbyWaybill(deliverbillDto);
					if(CollectionUtils.isNotEmpty(deliverbilldtoList) && deliverbilldtoList.get(0) != null){
						//车牌号
						recordDto.setVehicleNo(deliverbilldtoList.get(0).getVehicleNo());
						//查询员工信息
						EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode
								(deliverbilldtoList.get(0).getDriverCode());
						//获取司机所在部门编码
						String driverOrgCode = emp == null ? null : emp.getOrgCode();
						//司机所在部门编码
						recordDto.setDriverOrgCode(driverOrgCode);
					}

					//记录日志
					logger.info("*************运单号：" + waybillNo + "上报QMS内物短少差错***********start");
					try{
						//如果为快递
						if(waybillExpressService.onlineDetermineIsExpressByProductCode
								(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
							//得到快递内物短少差错上报的json字符串
							Map<String,Object> errorInfoMap = qmsErrorService.getShortErrorQmsInfo
									(waybillEntity,recordDto);
							//快递差错上报
							resDto = qmsErrorReportService.reportQmsShortError(errorInfoMap);
						}else{
							//获取零担内物短少差错上报的json字符串
							Map<String,Object> errorInfoMap = qmsErrorService.getLDShortErrorQmsInfo
									(waybillEntity,recordDto);
							//零担差错上报
							resDto = qmsErrorReportService.reportQmsShortError(errorInfoMap);
						}
						//记录日志
						logger.info("*************运单号：" + waybillNo + "上报QMS内物短少差错***********end");

						//处理返回结果
						if(resDto != null){
							this.processReturnInfo(recordDto,resDto);
						}
					}catch(BusinessException e){
						//处理异常
						logger.error("运单号" + waybillNo + "内物短少差错上报QMS失败：" + e.getMessage());
						//记录异常日志
						resDto = new QmsErrorReportResponseDto();
						resDto.setMessage(e.getErrorCode());
						this.saveReportOALog(recordDto,resDto,ReportConstants.REPORT_OA_ERROR);
					}catch(Exception e){
						//处理异常
						logger.error("运单号" + waybillNo + "内物短少差错上报QMS失败：" + e.getMessage());
						//记录异常日志
						resDto = new QmsErrorReportResponseDto();
						resDto.setMessage(e.getMessage());
						this.saveReportOALog(recordDto,resDto,ReportConstants.REPORT_OA_ERROR);
					}
				}else{
					//记录日志
					logger.info("运单号：" + waybillNo + "上报QMS内物短少差错失败,该运单不存在。");
					//记录异常日志
					resDto = new QmsErrorReportResponseDto();
					resDto.setMessage("上报QMS内物短少差错失败,该运单不存在。");
					this.saveReportOALog(recordDto,resDto,ReportConstants.REPORT_OA_ERROR);
				}
				//销毁对象
				resDto = null;
			}
		}
	}

	/**
	 * 处理OA返回数据  </br>
	 * @date 2014-12-15 下午3:58:10
	 * @param com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto
	 * update by 231434 2015-7-7
	 */
	private void processReturnInfo(RecordErrorWaybillResultDto recordDto,
			QmsErrorReportResponseDto resDto){
		String isSuccess=StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(resDto.getHandleCode())
				&& ReportConstants.QMS_RETURN_SUCCESS.equals(resDto.getHandleCode())) {
			// 上报成功
			isSuccess=ReportConstants.REPORT_OA_SUCCESS;;
			int flag = saveReportOALog(recordDto,resDto,isSuccess);
			//此数据无需再上报
			recordErrorWaybillDao.updateEntity(recordDto.getId());
			if (flag == 1) {
				logger.info("#####保存上报QMS成功日志成功！######");
			} else {
				logger.info("#####保存上报QMS成功日志失败！######");
			}
		}else if (StringUtils.isNotEmpty(resDto.getHandleCode())
				&& ReportConstants.QMS_RETURN_REPEAT.equals(resDto.getHandleCode())) {
			//数据重复上报
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(recordDto,resDto,isSuccess);
			//此数据无需再上报
			recordErrorWaybillDao.updateEntity(recordDto.getId());
			if (flag == 1) {
				logger.info("#####保存上报QMS失败日志成功！######");
			} else {
				logger.info("#####保存上报QMS失败日志失败！######");
			}
		}else if (StringUtils.isNotEmpty(resDto.getHandleCode())) {
			// 上报异常
			isSuccess=ReportConstants.REPORT_OA_ERROR;
			int flag = saveReportOALog(recordDto,resDto,isSuccess);
			//更新上报
			if (flag == 1) {
				logger.info("#####保存上报异常日志成功！######");
			} else {
				logger.info("#####保存上报异常日志失败！######");
			}
		}
	}

	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param responseDto
	 * @author foss-yuting
	 * @return  
	 * update by 231434 2015-7-7
	 */
	private int saveReportOALog(RecordErrorWaybillResultDto recordErrorWaybillResultDto,
			QmsErrorReportResponseDto resDto,String isSuccess){
		String msg = resDto.getMessage();
		if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
			msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
		}
		int flag = this.addReportOaLog(recordErrorWaybillResultDto,resDto,msg,isSuccess);
		return flag;
	}

	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param lostCargoReportDto
	 * @author foss-yuting
	 * @return  
	 * update by 231434 2015-7-7
	 */
	private int addReportOaLog(RecordErrorWaybillResultDto recordErrorWaybillResultDto,
			QmsErrorReportResponseDto resDto,String msg,String isSuccess){
		if(StringUtils.isNotEmpty(isSuccess) && isSuccess.equals(ReportConstants.REPORT_OA_SUCCESS)){
			JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
			jobSuccessLogEntity.setJobName(SignConstants.EXCEPTION_SHORT_NOTIFY_PROCESS_JOB);// job名称
			jobSuccessLogEntity.setCreateTime(new Date());
			//调用QMS接口成功返回差错编号
			jobSuccessLogEntity.setSuccessMsg("差错编号:" + resDto.getErrorID() 
					+ ";运单号:" + recordErrorWaybillResultDto.getWaybillNo() + ";" + msg);
			jobSuccessLogEntity.setSuccessId(recordErrorWaybillResultDto.getId());
			return shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
		}else{
			JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
			jobExceptionLogEntity.setJobName(SignConstants.EXCEPTION_SHORT_NOTIFY_PROCESS_JOB);// job名称
			jobExceptionLogEntity.setCreateTime(new Date());
			//调用QMS接口失败返回失败消息
			jobExceptionLogEntity.setErrorMsg("运单号:" 
					+ recordErrorWaybillResultDto.getWaybillNo() + ";" +msg);
			jobExceptionLogEntity.setErrorId(recordErrorWaybillResultDto.getId());
			return shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
		}
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setRecordErrorWaybillDao(IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setQmsErrorService(IQmsErrorService qmsErrorService) {
		this.qmsErrorService = qmsErrorService;
	}
	public void setQmsErrorReportService(
			IQmsErrorReportService qmsErrorReportService) {
		this.qmsErrorReportService = qmsErrorReportService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}	
}
