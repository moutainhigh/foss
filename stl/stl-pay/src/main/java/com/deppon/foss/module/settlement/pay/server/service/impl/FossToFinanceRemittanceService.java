package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;

import javax.xml.ws.Holder;

import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.OnlinePayInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.fin.module.claimpay.service.CommonException;
import com.deppon.fin.module.claimpay.service.IFinClaimNumService;
import com.deppon.fin.module.claimpay.shared.domain.ObtainClaimNumRequest;
import com.deppon.fin.module.claimpay.shared.domain.ObtainClaimNumResponse;
import com.deppon.fin.module.claimpay.shared.domain.QueryTransFerRequest;
import com.deppon.fin.module.claimpay.shared.domain.QueryTransFerResponse;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumRequest;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumResponse;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RemitTransferQueryResultDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 汇款服务实现类
 * 
 * @author foss-wangxuemin
 * @date Dec 7, 2012 4:43:38 PM
 */
public class FossToFinanceRemittanceService implements
		IFossToFinanceRemittanceService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(FossToFinanceRemittanceService.class);

	/**
	 * 注入费控接口service
	 */
	private IFinClaimNumService claimNumService;

	/**
	 * 注入组织管理service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 占用汇款
	 * 
	 * @date Dec 7, 2012 4:33:35 PM
	 */

	/**
	 * 网上支付的款项处理
	 */
	IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;

	@Override
	public void obtainClaim(BigDecimal claimAmount, String claimBillNum,
			String claimDeptCode,String debtNumber) {
		// 外部系统开关打开时才调用接口占用汇款
		if (SettlementConstants.EXTEND_SYSTEM_FINANCE_SWITCH) {
			try {
				ObtainClaimNumRequest request = new ObtainClaimNumRequest();
				request.setClaimAmount(claimAmount);
				request.setClaimBillNum(claimBillNum);
				// 获取组织的的实体信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(claimDeptCode);
				// 部门标杆编码
				request.setClaimDeptCode(orgEntity.getUnifiedCode());
				//增加单号 --毛建强 2012-1-26
				request.setDebtNumber(debtNumber);

				// 实例化ESBHeader，并设置到Holder对象中
				ESBHeader header = new ESBHeader();
				header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_OBTAIN_NUMBER);
				//与业务相关的字段
				header.setBusinessId(claimBillNum);
				header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
				header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
				//消息格式
				header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
				header.setRequestId(UUIDUtils.getUUID());
			    //请求系统
				header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
				Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

				ObtainClaimNumResponse response = claimNumService
						.obtainClaimNum(holder, request);
				if (!response.isResult()) {
					throw new SettlementException("占用汇款接口异常!" + response.getReason());
				}

			} catch (CommonException e) {
				logger.error(e.getMessage(),e);
				throw new SettlementException("占用汇款接口异常!" + e.getMessage());
			}
		}
	}

	/**
	 * 释放汇款
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 7, 2012 4:33:32 PM
	 */
	@Override
	public void releaseClaim(BigDecimal claimAmount, String claimBillNum,
			String claimDeptCode,String debtNumber) {
		// 外部系统开关打开时才调用接口释放汇款
		if (SettlementConstants.EXTEND_SYSTEM_FINANCE_SWITCH) {
			try {
				ReleaseClaimNumRequest request = new ReleaseClaimNumRequest();
				request.setClaimAmount(claimAmount);
				request.setClaimBillNum(claimBillNum);
				// 获取组织的的实体信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(claimDeptCode);
				
				//modify by 269044-zhurongrong 解决bamp上的控指针异常
				if(orgEntity == null || StringUtils.isEmpty(orgEntity.getUnifiedCode())) {
					//抛出异常
					throw new SettlementException("预收单的预收部门编码" + claimDeptCode + "对应的组织信息不存在，请维护一下");
				}//end

				// 部门标杆编码
				request.setClaimDeptCode(orgEntity.getUnifiedCode());
				
				//增加单号  --毛建强 2012-1-26
				request.setDebtNumber(debtNumber);

				// 实例化ESBHeader，并设置到Holder对象中
				ESBHeader header = new ESBHeader();
				header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_RELEASE_NUMBER);
				//与业务相关的字段
				header.setBusinessId(claimBillNum);
				header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
				header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
				//消息格式
				header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
				header.setRequestId(UUIDUtils.getUUID());
			    //请求系统
				header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
				Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

				ReleaseClaimNumResponse response = claimNumService
						.releaseClaimNum(holder, request);
				if (!response.isResult()) {
					throw new SettlementException("释放汇款接口异常!" + response.getReason());
				}

			} catch (CommonException e) {
				logger.error(e.getMessage(),e);
				throw new SettlementException("释放汇款接口异常!" + e.getMessage());
			}
		}
	}

	/**
	 * 查询汇款信息
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 12, 2012 3:42:14 PM
	 */
	@Override
	public RemitTransferQueryResultDto queryTransfer(String remitTransNum, String payableType) {

		//返回值
		RemitTransferQueryResultDto resultDto = null;
		// 外部系统开关打开时才调用接口释放汇款
		if (SettlementConstants.EXTEND_SYSTEM_FINANCE_SWITCH) {

			if(StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,payableType)){
				//调用查询网上支付信息方法
				OnlinePayInfoDto dto = financeOnlinePayWSProxy.query(remitTransNum);

				if(dto != null) {
					resultDto = new RemitTransferQueryResultDto();
					resultDto.setRemitName(dto.getRemitterName());
					resultDto.setRemitDate(dto.getRemittanceTime());
					resultDto.setNoCancelAmount(dto.getUnuseredAmounts());
				}
			} else{
				try {
					QueryTransFerRequest request = new QueryTransFerRequest();
					request.setRemitTransNum(remitTransNum);//汇款编号
					request.setPayment(payableType);//汇款类型

					// 实例化ESBHeader，并设置到Holder对象中
					ESBHeader header = new ESBHeader();
					header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_QUERY_TRANSFER);
					//与业务相关的字段
					header.setBusinessId(remitTransNum);
					header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
					header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
					//消息格式
					header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
					header.setRequestId(UUIDUtils.getUUID());
					//请求系统
					header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
					Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

					QueryTransFerResponse response = claimNumService
							.queryTransFer(holder, request);

					if (StringUtils.isEmpty(response.getClaimDeptNo())
							&&StringUtils.isEmpty(response.getClaimDeptName())
							&&StringUtils.isEmpty(response.getRemitName())
							&&StringUtils.isEmpty(response.getClaimState())
							&&response.getRemitDate()==null
							&&response.getNoCancelAmount()==null) {
						return null;
					}
					resultDto = new RemitTransferQueryResultDto();
					resultDto.setRemitName(response.getRemitName());
					resultDto.setRemitDate(response.getRemitDate());
					resultDto.setClaimState(response.getClaimState());
					resultDto.setClaimDeptName(response.getClaimDeptName());
					resultDto.setClaimDeptNo(response.getClaimDeptNo());
					resultDto.setNoCancelAmount(response.getNoCancelAmount());

				} catch (CommonException e) {
					logger.error(e.getMessage(),e);
					throw new SettlementException("汇款查询接口异常!" + e.getMessage());
				}
			}
		}
		return resultDto;
	}

	/**
	 * @param claimNumService
	 */
	public void setClaimNumService(IFinClaimNumService claimNumService) {
		this.claimNumService = claimNumService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setFinanceOnlinePayWSProxy(
			IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
		this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
	}
}
