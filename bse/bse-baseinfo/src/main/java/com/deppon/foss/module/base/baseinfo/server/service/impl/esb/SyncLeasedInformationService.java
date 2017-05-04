package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.ecs.inteface.domain.LeasedDriverInfo;
import com.deppon.ecs.inteface.domain.LeasedTruckInfo;
import com.deppon.ecs.inteface.domain.SyncLeasedInformationRequest;
import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.domain.foss2ecs.SyncLeasedInformationRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;

/**
 * 
 * @author 313353
 * @date 2016/04/09
 */
public class SyncLeasedInformationService implements
		ISyncLeasedInformationService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncLeasedInformationService.class);
	
	@Autowired
	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
		this.syncESBSendService = syncESBSendService;
	}

	/**
	 * 服务编码
	 */
	private static final String SYNC_LEASED_INFORMATION = "ESB_FOSS2ESB_RECEIVE_LEASEDTRUCK";

	private static final String version = "1.0";

	/**
	 * 同步外请车到ECS
	 * 
	 * @author 313353
	 * @date 2016/04/09
	 */
	@Override
	public void syncLeasedTruckToECS(List<LeasedTruckInfo> entitys) {
		
		// 要传递的值
		SyncLeasedInformationRequest request = new SyncLeasedInformationRequest();

		StringBuilder codes = new StringBuilder();

		List<LeasedTruckInfo> esbInfos = request.getLeasedTruckInfos();
		for (LeasedTruckInfo fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}

			if (codes.toString().isEmpty()) {
				codes.append(fossEntity.getEngineNo());
			} else {
				codes.append(SymbolConstants.EN_COMMA);
				codes.append(fossEntity.getEngineNo());
			}
			esbInfos.add(fossEntity);
		}

		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_LEASED_INFORMATION);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步外请车信息到其它平台");
		header.setBusinessDesc2(codes.toString());

		try {
			LOGGER.info("start send T_BAS_LEASED_TRUCK info to other platform.FOSS开始发送同步 外请车信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
			// 发送消息ESB
			syncESBSendService.createThreadToSendESB(syncESBSendService, header, request);
			
			LOGGER.info("end send T_BAS_LEASED_TRUCK info to other platform.FOSS结束发送同步 外请车信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
		} catch (ESBConvertException e) {
			LOGGER.error("同步外请车信息失败。", e);
			throw new BusinessException("同步外请车信息失败。");
		} catch (ESBException e) {
			LOGGER.error("同步外请车信息失败。", e);
			throw new BusinessException("同步外请车信息失败。");
		}

	}

	/**
	 * 同步外请司机到ECS
	 */
	@Override
	public void syncLeasedDriverToECS(List<LeasedDriverInfo> entitys) {
		
		// 要传递的值
		SyncLeasedInformationRequest request = new SyncLeasedInformationRequest();

		StringBuilder codes = new StringBuilder();

		List<LeasedDriverInfo> esbInfos = request.getLeasedDriverInfos();
		for (LeasedDriverInfo fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}
			if (codes.toString().isEmpty()) {
				codes.append(fossEntity.getIdCard());
			} else {
				codes.append(SymbolConstants.EN_COMMA);
				codes.append(fossEntity.getIdCard());
			}
			esbInfos.add(fossEntity);
		}
		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_LEASED_INFORMATION);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步外请司机信息到其它平台");
		header.setBusinessDesc2(codes.toString());

		try {
			LOGGER.info("start send T_BAS_LEASED_DRIVER info to other platform.FOSS开始发送同步 外请司机信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
			// 发送消息ESB
			syncESBSendService.createThreadToSendESB(syncESBSendService, header, request);
			
			LOGGER.info("end send T_BAS_LEASED_DRIVER info to other platform.FOSS结束发送同步 外请司机信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
		} catch (ESBConvertException e) {
			LOGGER.error("同步外请司机信息失败。", e);
			throw new BusinessException("同步外请司机信息失败。");
		} catch (ESBException e) {
			LOGGER.error("同步外请司机信息失败。", e);
			throw new BusinessException("同步外请司机信息失败。");
		}
	}

	/**
	 * 同步白名单到ECS
	 */
	@Override
	public void syncWhiteListAuditToECS(List<WhiteListAuditInfo> entitys) {
		
		// 要传递的值
		SyncLeasedInformationRequest request = new SyncLeasedInformationRequest();

		StringBuilder codes = new StringBuilder();

		List<WhiteListAuditInfo> esbInfos = request.getWhiteListAuditInfos();
		for (WhiteListAuditInfo fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}
			if (codes.toString().isEmpty()) {
				codes.append(fossEntity.getDriverLicense());
			} else {
				codes.append(SymbolConstants.EN_COMMA);
				codes.append(fossEntity.getDriverLicense());
			}
			esbInfos.add(fossEntity);
		}
		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_LEASED_INFORMATION);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步白名单信息到其它平台");
		header.setBusinessDesc2(codes.toString());

		try {
			LOGGER.info("start send T_BAS_LEASED_DRIVER info to other platform.FOSS开始发送同步 白名单信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
			// 发送消息ESB
			syncESBSendService.createThreadToSendESB(syncESBSendService, header, request);
			
			LOGGER.info("end send T_BAS_LEASED_DRIVER info to other platform.FOSS结束发送同步 白名单信息 到其它平台 的报文："
					+ new SyncLeasedInformationRequestTrans().fromMessage(request));
		} catch (ESBConvertException e) {
			LOGGER.error("同步白名单信息失败。", e);
			throw new BusinessException("同步白名单信息失败。");
		} catch (ESBException e) {
			LOGGER.error("同步白名单信息失败。", e);
			throw new BusinessException("同步白名单信息失败。");
		}

	}

}
