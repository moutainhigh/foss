package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.api.server.service.ITPSToFOSSService;
import com.deppon.foss.module.transfer.common.api.shared.domain.CarRentalMarkEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TotalNumberEntityEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TpsAboutVehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.VehicleRequestEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IConsultPriceService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class TPSToFOSSService implements ITPSToFOSSService{
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	 
	/**
	 * 
	 */
	private static Logger LOGGER = LogManager.getLogger(TPSToFOSSService.class);
	/**
	 * 配载单
	 * */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}
	private IAuditInviteApplyService auditInviteApplyService;
	
	public void setAuditInviteApplyService(
			IAuditInviteApplyService auditInviteApplyService) {
		this.auditInviteApplyService = auditInviteApplyService;
	}

	private IConsultPriceService consultPriceService;
	
	public void setConsultPriceService(IConsultPriceService consultPriceService) {
		this.consultPriceService = consultPriceService;
	}
	
	/**
	 * TPS同步交易信息到FOSS，即TPS审核约车后同步到FOSS，使FOSS约车为审核状态。 
	 * 如果FOSS约车编号为已审核则 不需要再次审核。
	 * @author 105869
	 * @date 2015年1月5日 14:49:22
	 * @param string (json)
	 */
	public ResponseParameterEntity synchTradVehicleInfo(String entity) {
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_FOSS_SENDVEHINFO_TPS");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		//定义返回对象
		ResponseParameterEntity reult=new ResponseParameterEntity();
		//解析JSON字符串为实体
		try {
			LOGGER.error("TPS同步交易信息：开始解析传入参数===================");	
			List<VehicleRequestEntity> vehicleRequestEntity = JSON.parseArray(JSON.parseObject(entity).get("requestEntity").toString(),
					VehicleRequestEntity.class);
			//审核约车信息
			auditInviteApplyService.passInviteInviteTPSApply(vehicleRequestEntity);
			
			LOGGER.error("TPS同步交易信息：审核约车信息结束===================");
			reult.setResultFlag(true);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		} catch (Exception e) {
			reult.setResultFlag(false);
			reult.setFailureReason(e.toString());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");// 如果设置为0 ESB回拦截 TPS收不到返回信息
			LOGGER.error("TPS同步交易信息：反序列化SmsResult时失败！"+e.toString());
			return reult;
		}
		LOGGER.error("TPS同步交易信息完成");
		return reult;
	}

	/**
	 * TPS同步将询价编号，询价信息，询价部门添加到FOSS的询价信息表中
	 * @param request
	 * @return
	 * @author 268084
	 * @param(string)JSON
	 */
	@Override
	public ResponseParameterEntity synchConsultPriceNo(String infoList) {
	    response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_SYNC_BID_INFO");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		//定义返回对象
		ResponseParameterEntity reult=new ResponseParameterEntity();
		//解析JSON字符串为实体
		try {
			LOGGER.error("TPS同步传输询价信息：开始解析传入参数===================");
			//CarRentalMarkEntity carRentalMarkEntity = JSON.parseObject(infoList,CarRentalMarkEntity.class);
			String requestEntityStr = JSON.parseObject(infoList).get("requestEntity").toString();
			CarRentalMarkEntity carRentalMarkEntity=(CarRentalMarkEntity) (JSON.parseObject(requestEntityStr,CarRentalMarkEntity.class));
			ConsultPriceEntity entity=new ConsultPriceEntity();
			entity.setConsultInfoId(UUIDUtils.getUUID());
			entity.setConsultPriceNo(carRentalMarkEntity.getQuotataionNumber());
			entity.setQuotedInfo(carRentalMarkEntity.getQuotePrice());
			entity.setNeedVehicleDept(carRentalMarkEntity.getPleaseCarDepartment());
			consultPriceService.addConsultPriceInfo(entity);
			LOGGER.error("TPS同步传输询价信息：传输询价信息结束===================");
			reult.setResultFlag(true);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		} catch (Exception e) {
			reult.setResultFlag(false);
			reult.setFailureReason(e.toString());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");// 如果设置为0 ESB回拦截 TPS收不到返回信息
			LOGGER.error("TPS同步传输询价信息：反序列化SmsResult时失败！"+e.toString());
			e.printStackTrace();
			return reult;
		}
		LOGGER.error("TPS同步传输询价信息完成");
		return reult;
	}
	/**
	 * 
	 * TPS定时取FOSS中外请车发车趟数
	 * 当年1月1号到当日长途外请车和合同车累计发车趟数 
	 * 当月1号到当日累计长途外请车和合同车累计发车趟数
	 * @author 105869
	 * @date 2015年1月5日 14:49:22
	 * 
	 * */	
	public String cumulativeDepartureTimes() {
		//响应消息头
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_FOSS_SENDVEHCOUNT_TPS");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		LOGGER.error("TPS定时取FOSS中外请车发车趟数：开始=============");
		//返回实体
		ResponseParameterEntity responseEntity = new ResponseParameterEntity();
		String result = "";
		try {
			//查询外请车发车趟数
			TotalNumberEntityEntity totalNumber=vehicleAssembleBillService.cumulativeDepartureTimes();
			responseEntity = new ResponseParameterEntity();
			responseEntity.setResponseEntity(totalNumber);
			responseEntity.setResultFlag(true);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			LOGGER.error("TPS定时取FOSS中外请车发车趟数：结束=============");
		} catch (Exception e) {
			responseEntity.setResultFlag(false);
			responseEntity.setFailureReason(e.toString());
			LOGGER.error("TPS定时取FOSS中外请车发车趟数：失败"+e.toString());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			result = JSON.toJSONString(responseEntity);
			return result;
		}
		
		result = JSON.toJSONString(responseEntity);
		
		return result;
	}

	
	/**
	* TPS同步已受理约车信息到FOSS
	* @see com.deppon.foss.module.transfer.common.api.server.service.ITPSToFOSSService#synchAboutTradVehicleInfo(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年1月21日 下午3:00:15
	*/
	@Override
	public ResponseParameterEntity synchAboutTradVehicleInfo(String entity) {
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_SYNC_ABOUT_TRADVEHICL");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		//定义返回对象
		ResponseParameterEntity reult=new ResponseParameterEntity();
		//解析JSON字符串为实体
		try {
			//考虑到线上日志输出级别,输出error以便后期排查问题
			LOGGER.error("TPS同步已受理约车信息：开始解析传入参数===================");	
			TpsAboutVehicleRequestEntity tpsAboutVehicleRequestEntity=JSON.parseObject(JSON.parseObject(entity).get("requestEntity").toString(),
					TpsAboutVehicleRequestEntity.class);
			auditInviteApplyService.tpsAboutVehicleToFoss(tpsAboutVehicleRequestEntity);
			LOGGER.error("TPS同步已受理约车信息到：同步约车信息结束==================="+tpsAboutVehicleRequestEntity.getInquiryNo());
			reult.setResultFlag(true);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		} catch (Exception e) {
			reult.setResultFlag(false);
			reult.setFailureReason(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");// 如果设置为0 ESB回拦截 TPS收不到返回信息
			LOGGER.error("TPS同步已受理约车信息到：反序列化SmsResult时失败！,entity:"+entity,e);
			return reult;
		}
		LOGGER.info("TPS同步已受理约车信息到完成");
		return reult;
	}

}
