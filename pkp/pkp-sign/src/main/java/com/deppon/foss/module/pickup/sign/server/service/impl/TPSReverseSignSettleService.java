package com.deppon.foss.module.pickup.sign.server.service.impl;

import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ITPSReverseSignSettleService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-02 16:00:38
 * VTS自动受理反签收反结清 
 *
 */
public class TPSReverseSignSettleService implements ITPSReverseSignSettleService{
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TPSReverseSignSettleService.class);
	
	/**
	 * 运单签收结果表
	 */
	private IWaybillSignResultService waybillSignResultService; 
	
	/**
	 * 结清货款表
	 */
	private IRepaymentService repaymentService;
	
	/**
	 * 实际承运表	
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 反签收 
	 */
	@Override
	@Transactional
	public void reverseSign(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			LOGGER.info("反签收传入的运单号为空！");
			throw new SignException("反签收传入的运单号为空！");
		}		
		
		/**
		 * .根据单号+active=Y 查询签收结果表信息，有的话置成无效N
		 * (1)先查询
		 * (2)根据ID修改
		 */
		WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
		resultEntity.setWaybillNo(waybillNo);
		resultEntity.setActive("Y");
		LOGGER.info("TPS反签收校查询签收结果表开始!单号为:" + waybillNo);
		
		resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
		if (resultEntity != null) {
			// 作废运单签收结果里当前运单号
			WaybillSignResultEntity updateSignResultEntity = new WaybillSignResultEntity();
			updateSignResultEntity.setModifyTime(new Date());//set修改时间
			updateSignResultEntity.setActive("N");//set有效状态为N
			
			updateSignResultEntity.setId(resultEntity.getId());//set主键ID，根据这个ID来修改签收结果表为N
			
			waybillSignResultService.updateWaybillSignResult(updateSignResultEntity);
			
		} else {
			LOGGER.error("运单号" + waybillNo + "在运单签收结果里不存在");//记录日志
			throw new SignException("FOSS签收结果表中不存在有效的单号为" + waybillNo + "的记录信息！");
		}
		LOGGER.info("TPS反签收签收结果表结束!单号为:" + waybillNo);
	}
	
	
	/**
	 * 反结清
	 */
	@Override
	@Transactional
	public void reverseSettle(String waybillNo) {
		
		if(StringUtils.isBlank(waybillNo)){
			throw new SignException("反签收传入的运单号为空！");
		}	
	
		List<RepaymentEntity> repaymentList = repaymentService.queryRepaymentListByWaybillNo(waybillNo);
		try {
			/**
			 * 将付款记录更新为N,结清状态置为N
			 */
			Date date = new Date();
			if(CollectionUtils.isNotEmpty(repaymentList)){
				for(RepaymentEntity repaymentEntity : repaymentList){
					
					RepaymentEntity repayment = new RepaymentEntity();
					repayment.setId(repaymentEntity.getId()); //根据id更新
					
					repayment.setActive(FossConstants.NO);
					repayment.setModifyTime(date);
					
					repaymentService.updateRepayment(repayment);
				}
			}
			// 更新ActualFreight表中的结清状态
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(waybillNo);
			//状态
			actualFreightEntity.setSettleStatus(FossConstants.NO);
			//结清时间
			actualFreightEntity.setSettleTime(null);
			//更新时间
			actualFreightEntity.setModifyTime(date);
			//更新结清状态
			actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
		} catch (Exception e) {
			LOGGER.error("TPS推送信息至FOSS反结清失败！"+e.getMessage(),e);//记录日志
			throw new SignException("TPS推送信息至FOSS反结清失败，单号为" + waybillNo);
		}
		
		
		LOGGER.info("TPS反结清付款记录表结束!单号为:" + waybillNo);
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
}
