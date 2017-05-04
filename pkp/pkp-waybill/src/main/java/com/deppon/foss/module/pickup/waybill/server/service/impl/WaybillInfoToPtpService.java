package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerWaybillInfoRequest;
import com.deppon.esb.pojo.transformer.jaxb.PartnerWaybillInfoRequestTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushPartnerWaybillLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运单信息异步发送给ptp系统
 * @author 272311-sangwenhao
 * @date 2016-1-15
 */
public class WaybillInfoToPtpService implements IWaybillInfoToPtpService {
	
	private Logger logger = LoggerFactory.getLogger(WaybillInfoToPtpService.class);
	
	private static String asynCode = "ESB_FOSS2ESB_PARTNER_WAYBILL" ;
	private static String version = "0.1" ;
	
	private IPushPartnerWaybillLogService pushPartnerWaybillLogService ;

	public IPushPartnerWaybillLogService getPushPartnerWaybillLogService() {
		return pushPartnerWaybillLogService;
	}

	public void setPushPartnerWaybillLogService(
			IPushPartnerWaybillLogService pushPartnerWaybillLogService) {
		this.pushPartnerWaybillLogService = pushPartnerWaybillLogService;
	}
	
	/**
	 * 组织机构信息 提供与组织机构相关的服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,noRollbackFor=Exception.class)
	public void asynSendWaybillInfoToPtp(PtpWaybillDto ptpWaybillDto) {
		
		//处理提货网点名称（转运返货后直营在发生内部更改WAYBILL表中会存放转运返货前的提货网点）
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getCustomerPickupOrgCode());
		if(null != orgAdministrativeInfoEntity){
			ptpWaybillDto.setCustomerPickupOrgName(orgAdministrativeInfoEntity.getName());
		}
		
		PartnerWaybillInfoRequest request = new PartnerWaybillInfoRequest();
		String errorMsg = null;
		boolean flag = false ;
		try {
			//请求参数
			try {
				PropertyUtils.copyProperties(request, ptpWaybillDto);
			} catch (Exception e) {
				flag = true ;
				// 对异常进行处理
				logger.error("Foss推送消息至合伙人系统拷贝request异常，错误详情：", e);
				throw new WaybillSubmitException("Foss推送消息至合伙人系统拷贝request异常，错误详情："+System.err);
			}
			
			//异步发送
			//准备消息头信息
			AccessHeader header = createAccessHeader(request);
			try {
				logger.info("消息头："+ReflectionToStringBuilder.toString(header));
				logger.info("request请求参数："+ReflectionToStringBuilder.toString(request)+"=======", ToStringStyle.SIMPLE_STYLE);
				ESBJMSAccessor.asynReqeust(header, request);
				logger.info("================消息推送成功");
			} catch (Exception e) {
				flag = true ;
				// 对异常进行处理
				logger.error("Foss推送消息至合伙人系统失败，错误详情：", e);
				throw new WaybillSubmitException("Foss推送消息至合伙人系统失败，错误详情："+ReflectionToStringBuilder.toString(e));
			}
			
		} catch (Exception e) {
			flag = true ;
			errorMsg = ReflectionToStringBuilder.toString(e).substring(0, NumberConstants.NUMBER_100);
			throw new WaybillSubmitException(errorMsg);
		}finally{
			try {
				//添加推送日志
				addPartnerwaybillLog(ptpWaybillDto,request,errorMsg,flag) ;
			} catch (Exception e) {
				throw new WaybillSubmitException(e.getMessage());
			}
		}
		
	}
	
	/**
	 * 创建消息头
	 * @param PdaDeliverSignDto 运单信息
	 * @return
	 */
	private AccessHeader createAccessHeader(PartnerWaybillInfoRequest dto) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		//ESB_FOSS2ESB_RECEIVE_ORDERSTATUS
		logger.info("接送货推送的合伙人系统编码为："+asynCode+"版本号:"+version);
		header.setEsbServiceCode(asynCode);
		//版本随意  1.0
		header.setVersion(version);
		//business id 随意
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		header.setBusinessId(sdf.format(date));
		//运单号放在消息头中传给Ptp waybillNo
		header.setBusinessDesc1(dto.getWaybillNo());
		return header;
	}
	
	/**
	 * 添加推送日志
	 * @param ptpWaybillDto
	 * @param sendMsg
	 * @author 272311-sangwenhao
	 * @date 2016-2-18
	 */
	private void addPartnerwaybillLog(PtpWaybillDto ptpWaybillDto,PartnerWaybillInfoRequest request,String errorMsg,boolean flag){
		try {
			PartnerWaybillInfoRequestTrans trans = PartnerWaybillInfoRequestTrans.class.newInstance();
			String sendMsg = trans.fromMessage(request);
			logger.info("fromMessage:"+sendMsg);
			
			PushPartnerWaybillLogEntity entity = new PushPartnerWaybillLogEntity();
			entity.setOperateContent(sendMsg);
			entity.setOperateOrgCode(request.getCurrentDeptCode());
			entity.setOperateOrgName(request.getCurrentDeptName());
			entity.setOperateTime(new Date());
			entity.setOperator(request.getUserName());
			entity.setOperatorCode(request.getUserCode());
			entity.setWaybillNo(request.getWaybillNo());
			entity.setIsExpress(FossConstants.NO);
			entity.setWayBillCreateTime(ptpWaybillDto.getCreateTime());
			
			if(flag){
				entity.setIsError(FossConstants.YES);
				entity.setErrorMsg(errorMsg);
			}
			pushPartnerWaybillLogService.insert(entity);
			
		} catch (Exception e) {
			logger.error("Foss推送消息至合伙人记录日志信息异常，错误详情：", e);
			throw new WaybillSubmitException("Foss推送消息至合伙人记录日志信息异常，错误详情："+System.err);
		}
	}

}
