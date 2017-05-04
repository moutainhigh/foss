package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.foss2cubc.ActualFreightInfo;
import com.deppon.esb.inteface.domain.foss2cubc.WaybillInfo;
import com.deppon.esb.inteface.domain.foss2cubc.WaybillInfoToCUBCRequest;
import com.deppon.esb.inteface.domain.foss2cubc.WaybillRfcInfo;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncWaybillLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToCUBCService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 运单(零担)信息、更改单信息、结算单据信息异步发送给cubc(客户结算中心)系统
 * WaybillInfoToCubcService
 * @author 198771-zhangwei
 * 2016-10-12 上午10:56:38
 */
public class WaybillInfoToCUBCService implements IWaybillInfoToCUBCService {
	
	private Logger logger = LoggerFactory.getLogger(WaybillInfoToCUBCService.class);
	
	//开单运单服务编码
	private static final String WAYBILL_ESBSERVICECODE = "ESB_FOSS2ESB_SYN_BILL_INFO";
	//更改单服务编码
	private static final String WAYBILLRFC_ESBSERVICECODE = "ESB_FOSS2ESB_FOSS_CUBC_MODIFY_SYN";
	
	private final String CUBCLOGENTITY_IS_NULL = "推送消息为空，请联系相关开发排查！！！";
	
	private final String CUBCLOGENTITY_ESBCODE_ERROR = "ESB服务编码错误，请联系相关开发排查！！！";
	//版本
	private static final String VERSION = "1.0";
	
	/**
	 * 日志service
	 */
	private ISyncWaybillLogService syncWaybillLogService;

	/**
	 * @param syncWaybillLogService the syncWaybillLogService to set
	 */
	public void setSyncWaybillLogService(
			ISyncWaybillLogService syncWaybillLogService) {
		this.syncWaybillLogService = syncWaybillLogService;
	}


	/**
	 * 运单(零担)信息异步推送
	 * @author 198771-zhangwei
	 * 2016-10-12 上午11:18:29
	 */
	@Override
	public void asyncSendWaybillInfoToCUBC(WaybillDto waybillDto) {
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		if(waybillEntity==null||StringUtil.isEmpty(waybillEntity.getWaybillNo())){
			logger.error("Foss推送运单消息至客户结算中心系统参数[运单号为空]异常");
			return;
		}
		
		WaybillInfoToCUBCRequest request = new WaybillInfoToCUBCRequest();
		WaybillLogEntity waybillLogEntity = new WaybillLogEntity();
		waybillLogEntity.setId(UUIDUtils.getUUID());
		waybillLogEntity.setCode(WAYBILL_ESBSERVICECODE);
		waybillLogEntity.setWaybillNo(waybillEntity.getWaybillNo());
		waybillLogEntity.setDesc1("同步运单信息至CUBC异步接口");
		waybillLogEntity.setDesc2(waybillEntity.getId());
		
		//请求唯一标识，回调返回用于修改日志表状态
		request.setId(waybillLogEntity.getId());
		try {
			//请求参数
			try {
				//运单信息
				WaybillInfo waybillInfo = new WaybillInfo();
				PropertyUtils.copyProperties(waybillInfo, waybillEntity);
				request.setWaybillInfo(waybillInfo);

				//实际承运表信息
				ActualFreightInfo actualFreightInfo = new ActualFreightInfo();
				PropertyUtils.copyProperties(actualFreightInfo, waybillDto.getActualFreightEntity());
				request.setActualFreightInfo(actualFreightInfo);
			} catch (Exception e) {
				waybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				waybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送运单消息至客户结算中心系统拷贝request异常，错误详情：", e);
			}
			
			try {
				//异步发送
				//准备消息头信息
				AccessHeader header = createAccessHeader(WAYBILL_ESBSERVICECODE,waybillEntity.getWaybillNo());
				String requestContent = JSON.toJSONString(request);
				logger.info("cubc request运单请求参数"+"："+requestContent);
				//请求参数
				waybillLogEntity.setRequestContent(requestContent);
				
				ESBJMSAccessor.asynReqeust(header, request);
				//日志记录成功
				waybillLogEntity.setStatu(WaybillConstants.SYNCED);
			} catch (Exception e) {
				waybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				waybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送运单消息至CUBC系统失败，错误详情：", e);
			}
		}finally{
			try {
				//添加推送日志
				syncWaybillLogService.insert(waybillLogEntity);
			} catch (Exception e) {
				logger.info("插入cubc日志失败"+"："+e.getMessage());
			}
		}
	}

	
	/**
	 * 创建消息头
	 * @param PdaDeliverSignDto 运单信息
	 * @return
	 */
	private AccessHeader createAccessHeader(String esbCode,String waybillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode(esbCode);
		//版本随意  1.0
		header.setVersion(VERSION);
		//business id 随意
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		header.setBusinessId(sdf.format(date));
		//运单号放在消息头中作为关键字方便排查
		header.setBusinessDesc1(waybillNo);
		return header;
	}


	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-15 上午11:26:15
	 */
	@Override
	public void asyncSendWaybillRfcInfoToCUBC(WaybillRfcEntity waybillRfcEntity, WaybillDto newVersionDto) {
		if(waybillRfcEntity == null || StringUtil.isEmpty(waybillRfcEntity.getId())){
			logger.error("Foss推送更改单消息至客户结算中心系统参数[更改单ID号]异常");
			return;
		}
		
		WaybillInfoToCUBCRequest request = new WaybillInfoToCUBCRequest();
		WaybillLogEntity waybillLogEntity = new WaybillLogEntity();
		waybillLogEntity.setId(UUIDUtils.getUUID());
		waybillLogEntity.setCode(WAYBILLRFC_ESBSERVICECODE);
		waybillLogEntity.setWaybillNo(waybillRfcEntity.getWaybillNo());
		waybillLogEntity.setDesc1("同步更改单信息至CUBC异步接口");
		waybillLogEntity.setDesc2(waybillRfcEntity.getId());
		
		//请求唯一标识，回调返回用于修改日志表状态
		request.setId(waybillLogEntity.getId());
		
		try{
			try {
				//更改单信息
				WaybillRfcInfo waybillRfcInfo = new WaybillRfcInfo();
				PropertyUtils.copyProperties(waybillRfcInfo, waybillRfcEntity);
				request.setWaybillRfcInfo(waybillRfcInfo);
				
				//运单信息
				WaybillInfo waybillInfo = new WaybillInfo();
				PropertyUtils.copyProperties(waybillInfo, newVersionDto.getWaybillEntity());
				request.setWaybillInfo(waybillInfo);
								
				//实际承运表信息
				ActualFreightInfo actualFreightInfo = new ActualFreightInfo();
				PropertyUtils.copyProperties(actualFreightInfo, newVersionDto.getActualFreightEntity());
				request.setActualFreightInfo(actualFreightInfo);
			} catch (Exception e) {
				waybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				waybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送更改单消息至客户结算中心系统拷贝request异常，错误详情：", e);
			}
			
			try {
				//异步发送
				//准备消息头信息
				AccessHeader header = createAccessHeader(WAYBILLRFC_ESBSERVICECODE,waybillRfcEntity.getWaybillNo());
				String requestContent = JSON.toJSONString(request);
				logger.info("cubc request更改单请求参数"+"："+requestContent);
				//请求参数
				waybillLogEntity.setRequestContent(requestContent);
				
				ESBJMSAccessor.asynReqeust(header, request);
				//日志记录成功
				waybillLogEntity.setStatu(WaybillConstants.SYNCED);
			} catch (Exception e) {
				waybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				waybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送更改单消息至CUBC系统失败，错误详情：", e);
			}											
		}finally{
			//添加推送日志
			syncWaybillLogService.insert(waybillLogEntity);
		}
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-14 上午9:45:55
	 */
	@Override
	public void asyncSendWaybillToCUBC(WaybillLogEntity waybillLogEntity) {
		if(waybillLogEntity==null){
			throw new WaybillSubmitException(CUBCLOGENTITY_IS_NULL);
		}
		
		if(!WAYBILL_ESBSERVICECODE.equals(waybillLogEntity.getCode())&&!WAYBILLRFC_ESBSERVICECODE.equals(waybillLogEntity.getCode())){
			throw new WaybillSubmitException(CUBCLOGENTITY_ESBCODE_ERROR);
		}
		
		WaybillLogEntity newWaybillLogEntity = new WaybillLogEntity();
		newWaybillLogEntity.setId(UUIDUtils.getUUID());
		newWaybillLogEntity.setCode(waybillLogEntity.getCode());
		newWaybillLogEntity.setWaybillNo(waybillLogEntity.getWaybillNo());
		newWaybillLogEntity.setDesc1("同步运单或更改单信息至CUBC异步接口");
		newWaybillLogEntity.setDesc2(waybillLogEntity.getId());
		
		try{
			WaybillInfoToCUBCRequest request = new WaybillInfoToCUBCRequest();
			try {
				request = JSON.parseObject(waybillLogEntity.getRequestContent(), WaybillInfoToCUBCRequest.class);
				//请求唯一标识，回调返回用于修改日志表状态
				request.setId(newWaybillLogEntity.getId());
			} catch (Exception e) {
				newWaybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				newWaybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送运单或更改单消息至CUBC系统失败，错误详情：", e);
			}
			try {
				//异步发送
				//准备消息头信息
				AccessHeader header = createAccessHeader(waybillLogEntity.getCode(),waybillLogEntity.getWaybillNo());
				logger.info("cubc request运单或更改单请求参数"+"："+waybillLogEntity.getRequestContent());
				//请求参数
				newWaybillLogEntity.setRequestContent(waybillLogEntity.getRequestContent());
				
				ESBJMSAccessor.asynReqeust(header, request);
				//日志记录成功
				newWaybillLogEntity.setStatu(WaybillConstants.SYNCED);
			} catch (Exception e) {
				newWaybillLogEntity.setErrorMsg(ReflectionToStringBuilder.toString(e));
				newWaybillLogEntity.setStatu(WaybillConstants.SYNC_PENDING);
				logger.error("Foss推送运单或更改单消息至CUBC系统失败，错误详情：", e);
			}	
		}finally{
			//作废旧数据
			syncWaybillLogService.deleteById(waybillLogEntity);
			//添加推送日志
			syncWaybillLogService.insert(newWaybillLogEntity);
		}
	}
}
