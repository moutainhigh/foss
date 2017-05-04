package com.deppon.foss.module.base.querying.server.service.impl;

import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cc.module.foss.server.service.CommException;
import com.deppon.cc.module.foss.server.service.IFossToCCService;
import com.deppon.cc.module.foss.shared.domain.CcsSyncRecordorInfoRequest;
import com.deppon.cc.module.foss.shared.domain.CcsSyncRecordorInfoResponse;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.querying.server.service.ISyncWaybillInfoToCCService;
import com.deppon.foss.module.base.querying.shared.dto.WaybillInfoToCcDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 同步给CC系统的运单信息微博Service客户端
 * @author 130566
 *
 */
public class SyncWaybillInfoToCCService implements ISyncWaybillInfoToCCService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncWaybillInfoToCCService.class);
	
	private IFossToCCService fossToCCService;
	
	
	public IFossToCCService getFossToCCService() {
		return fossToCCService;
	}


	public void setFossToCCService(IFossToCCService fossToCCService) {
		this.fossToCCService = fossToCCService;
	}


	/**
	 * 
	 *<p>同步给CC运单信息接口</p>	
	 * @date 2014-8-5 上午11:10:46
	 * @author 130566-ZengJunfan
	 * @param dto
	 */
	@Override
	public boolean syncWaybillInfoToCC(WaybillInfoToCcDto dto) {
		LOGGER.info("SyncWaybillInfoToCC: info start.............");
		//设置esb 消息头
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_SEND_RECORDORINFO");
		header.setMessageFormat("SOAP");
		header.setSourceSystem("FOSS");
		header.setExchangePattern(1);
		header.setVersion("1.0");
		header.setRequestId(UUID.randomUUID().toString());
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		//非空校验
		if(null !=dto && StringUtils.isNotBlank(dto.getCallRecordNo())){
			//运单编号 作为唯一标识
			header.setBusinessId(dto.getCallRecordNo());
			CcsSyncRecordorInfoRequest request =new CcsSyncRecordorInfoRequest();
			//设置传递的请求信息
			request.setWaybillNo(dto.getWaybillNo());
			request.setCallRecordNo(dto.getCallRecordNo());
			request.setWaybillResult(dto.getWaybillResult());
			try {
				CcsSyncRecordorInfoResponse response=  fossToCCService.ccsSyncRecordorInfo(holder, request);
				LOGGER.info("SyncWaybillInfoToCC: info end.............");
				if(null !=response&&response.getIfSuccess().equals(FossConstants.YES)){
					return true;
				}else{
				//	LOGGER.debug("response Error Info :"+response.getErrMsg());
					return false;
				}
			} catch (CommException e) {
				LOGGER.debug("接口同步传输出现异常");
				throw new BusinessException("接口同步传输出现异常");
			}
		}
		return false;
	}

}
