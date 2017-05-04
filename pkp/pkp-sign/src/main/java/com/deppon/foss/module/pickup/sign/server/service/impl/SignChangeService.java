package com.deppon.foss.module.pickup.sign.server.service.impl;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveSheetMannerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IReverseSignDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcChangeDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.*;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.*;
import com.deppon.foss.module.pickup.sign.api.shared.dto.*;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.server.util.SequenceUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.*;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaErrorReportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.QmsErrorException;
import com.deppon.foss.module.settlement.common.api.server.service.*;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.*;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPdaPosManageDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 签收变更业务服务<br />
 * </p>
 * 
 * @title SignChangeService.java
 * @package com.deppon.foss.module.pickup.sign.server.service.impl
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-6
 */
public class SignChangeService implements ISignChangeService {
	
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}

	private String queryCodAuditListUrl;
	public void setQueryCodAuditListUrl(String queryCodAuditListUrl) {
		this.queryCodAuditListUrl = queryCodAuditListUrl;
	}

	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.SignChangeService";
	
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
	/**
	 * add by 353654
	 */
	private IExternalBillService externalBillService;
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}	
	private ICUBCSignChangeService cUBCSignChangeService;
	public void setcUBCSignChangeService(
			ICUBCSignChangeService cUBCSignChangeService) {
		this.cUBCSignChangeService = cUBCSignChangeService;
	}
	// LOGGER
	private static final Logger LOGGER = LoggerFactory.getLogger(SignChangeService.class);
	// 变更签收
	private ISignRfcDao signRfcDao;
	// 变更明细
	private ISignRfcChangeDetailDao signRfcChangeDetailDao;
	// 付款
	private IRepaymentDao repaymentDao;
    //付款服务
	private IRepaymentService repaymentService;
	// 到达联
	private IArrivesheetDao arrivesheetDao;
	// 到达联服务
	private IArriveSheetManngerService arriveSheetManngerService;
	// 运单签收结果
	private IWaybillSignResultService waybillSignResultService;
	// 反签收明细
	private IReverseSignDetailDao reverseSignDetailDao;
	//DOP签收服务
	private IDopSignService dopSignService;
	// 运单
	private IWaybillManagerService waybillManagerService;
	// 到达联签收明细
	private ISignDetailService signDetailService;
	//部门服务
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	//附件
	private IAttachementService attachementService;
	//检查运单对应的代收货款是否已付款
	private ICodCommonService codCommonService;
	 //结算应收单服务
	private IBillReceivableService billReceivableService;
	/*
	 * 空运签收服务
	 */
	private IAirAgencySignService airAgencySignService;
	
	/**
	 * 合伙人零担签收service
	 */
	private IPtpSignPartnerService ptpSignPartnerService;
	
	/**
	 * 保价声明价值
	 */
	private static final BigDecimal INSURANCEAMOUNT_NUM = new BigDecimal("10000");
	
	private ICustomerService customerService;
	/**
	 *  结算签收Service
	 */
	private ILineSignService lineSignService;
	
	/**
	 * 小票红冲服务类
	 */
	private IOtherRevenueService otherRevenueService;
	/**
	 * 业务斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 收入确认、反确认服务（确认现金收款单、应收单的确认收入日期，更新应付单的签收日期，反确认反之操作）
	 */
	private ITakingService takingService;
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 快递代理公司网点接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	private IMessageService messageService;
	
	/**
	 * 运单签收结果接口
	 */
	private IWaybillSignResultDao  waybillSignResultDao;
	
	/**
	 * QMS差错信息
	 */
	private IQmsErrorService qmsErrorService;
	/**
	 * QMS差错上报
	 */
	private IQmsErrorReportService qmsErrorReportService;
	
	/***
	 * 记录异常运单 上报QMS的Service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;
	
	/***
	 * 记录异常运单 上报OA的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService;
	
	/**
	 * 子母件服务接口层
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
    /**
     * 代收货款审核
     */
    private  ICodAuditService codAuditService;
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 快递运单服务
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 运单状态服务
	 * 接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * <p>注入<br />
	 * @author chenjunying
	 * @version 0.1 2015-03-31
	 * @param actualFreightService
	 * void
	 */
	
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * <p>注入<br />
	 * @author chenjunying
	 * @version 0.1 2015-02-03
	 * @param waybillExpressService
	 * void
	 */
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 * 
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param attachementService
	 * void
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		//代收货款记录管理服务
		this.codCommonService = codCommonService;
	}

	/**
	 * 
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param attachementService
	 * void
	 */
	public void setAttachementService(IAttachementService attachementService) {
		//附件
		this.attachementService = attachementService;
	}
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param attachementService
	 * void
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		//运单服务
		this.billReceivableService = billReceivableService;
	}

	/**
	 * 
	 * <p>设置注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-18
	 * @param repaymentService
	 * void
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		//付款服务
		this.repaymentService = repaymentService;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param signRfcDao
	 * void
	 */
	public void setSignRfcDao(ISignRfcDao signRfcDao) {
		//签收变更Dao
		this.signRfcDao = signRfcDao;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param signRfcChangeDetailDao
	 * void
	 */
	public void setSignRfcChangeDetailDao(
			ISignRfcChangeDetailDao signRfcChangeDetailDao) {
		//变更签收明细DAO
		this.signRfcChangeDetailDao = signRfcChangeDetailDao;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param repaymentDao
	 * void
	 */
	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		//付款DAO
		this.repaymentDao = repaymentDao;
	}
	/**
	 * T+0报表
	 * @author 309603 yangqiang
	 * @date 2016-02-23
	 */
	private IPdaPosManageService pdaPosManageService;
	/**
	 * T+0报表Dao
	 * @author 309603 yangqiang
	 * @date 2016-02-23
	 */
	private IPdaPosManageDao pdaPosManageDao;
	/**
	 * T+0服务注入
	 * @param pdaPosManageService
	 */
	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
	

	public void setPdaPosManageDao(IPdaPosManageDao pdaPosManageDao) {
		this.pdaPosManageDao = pdaPosManageDao;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param arrivesheetDao
	 * void
	 */
	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		//到达联DAO
		this.arrivesheetDao = arrivesheetDao;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillSignResultService
	 * void
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		//运单签收结果服务
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param reverseSignDetailDao
	 * void
	 */
	public void setReverseSignDetailDao(
			IReverseSignDetailDao reverseSignDetailDao) {
		//反签收详细DAO
		this.reverseSignDetailDao = reverseSignDetailDao;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillManagerService
	 * void
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		//运单管理服务
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param signDetailService
	 * void
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		//签收详细服务
		this.signDetailService = signDetailService;
	}

	/**
	 * 
	 * <p>注入br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param arriveSheetManngerService
	 * void
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		//到达联管理服务
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * 
	 * <p>空运偏线服务<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-25
	 * @param airAgencySignService
	 * void
	 */
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		//空运签收服务
		this.airAgencySignService = airAgencySignService;
	}

	/**
	 * 
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param orgAdministrativeInfoService
	 * void
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		//部门管理服务
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 
	 * 理赔状态Service
	 * @author foss-xujie
	 * 2016-6-24
	 */
	private IClaimStatusService ClaimStatusService;
	
	
	public IClaimStatusService getClaimStatusService() {
		return ClaimStatusService;
	}

	public void setClaimStatusService(IClaimStatusService claimStatusService) {
		ClaimStatusService = claimStatusService;
	}

	@Override
	public RepaymentArrivesheetDto searchRepaymentArrivesheet(String waybillNo) {
		LOGGER.info("searchRepaymentArrivesheet begin......");
		//检查运单
		checkWayBillAndReturnWaybillEntity(waybillNo);
		if(StringUtils.isNotEmpty(waybillNo)){
			CodAuditDto dto = new CodAuditDto();
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add(waybillNo);
			dto.setWaybillNos(waybillNoList);
            List<CodAuditEntity> codAuditEntityList=null;
            //代收货款审核灰度   353654 ------------------------ start
            String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".searchRepaymentArrivesheet",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                 codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
                 if(codAuditEntityList != null && codAuditEntityList.size() > 0){
                	 CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
                	 if(codAuditEntity != null){
                		 //如果为资金部锁定
                		 if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
                			 throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
                		 }
                		 if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
                			 throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
                		 }
                		 if(StringUtils.equalsIgnoreCase("SSL", codAuditEntity.getLockStatus())){
                			 throw new SettlementException("此单据已被短期冻结，如需操作，请联系资金安全控制组进行解冻");
                		 }
                		 if(StringUtils.equalsIgnoreCase("SLL", codAuditEntity.getLockStatus())){
                			 throw new SettlementException("此单据已被长期冻结，如需操作，请联系资金安全控制组!");
                		 }
                	 }
                 }else{
                	 LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
                 }
            }
            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
					requestDto.setWaybillNo(waybillNoList);
					CUBCCodAuditResultDto resultDto = null;
					try {
						resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
					} catch (Exception e) {
						LOGGER.error("调用CUBC代收货款审核接口连接异常...");
						throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
					}
					if(resultDto != null){
						if(StringUtils.isNotBlank(resultDto.getMeg())){
							LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
							throw new SettlementException(resultDto.getMeg());	
						}
						List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
						if(CollectionUtils.isNotEmpty(auditList)){
							com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto = auditList.get(0);
							if(codAuditDto != null){
								//如果为资金部锁定
								if(StringUtils.equalsIgnoreCase("FL", codAuditDto.getLockStatus())){
									throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
								}
								if(StringUtils.equals("RL", codAuditDto.getLockStatus())){
									throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
								}
								if(StringUtils.equalsIgnoreCase("SSL", codAuditDto.getLockStatus())){
									throw new SettlementException("此单据已被短期冻结，如需操作，请联系资金安全控制组进行解冻");
								}
								if(StringUtils.equalsIgnoreCase("SLL", codAuditDto.getLockStatus())){
									throw new SettlementException("此单据已被长期冻结，如需操作，请联系资金安全控制组!");
								}
							}
						}else{
							LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
						}
					}
            }
            //代收货款审核灰度   353654 ------------------------ end
		}
		//返回数据
		RepaymentArrivesheetDto dto = new RepaymentArrivesheetDto();	
		//获取子母件信息
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(SignConstants.WAYBILL_NO,waybillNo);
		params.put(SignConstants.ACTIVE,FossConstants.YES);
		TwoInOneWaybillDto twoInOneDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
		//默认可以反结清
		dto.setIsAllReverse(FossConstants.YES);
		//如果是子母件，且子件集合不为空，则判断其他单号是否全部反签收
		if(twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
				&& CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())){
			// 传入参数
			WaybillSignResultEntity wayEntity = new WaybillSignResultEntity();
			// 根据运单号 查询运单签收结果
			WaybillSignResultEntity waybillSign = null;
			//将母件单号放入集合，用于查询其他单号是否全部反签收
			twoInOneDto.getWaybillNoList().add(twoInOneDto.getMainWaybillNo());
			//判断其他单号是否全部反签收
			for(String sonWaybillNo : twoInOneDto.getWaybillNoList()){
				//如果是本单号，则跳过进入下一循环
				if(waybillNo.equals(sonWaybillNo)){
					continue;
				}
				//传入参数(运单号,当前运单号生效)
				wayEntity.setWaybillNo(sonWaybillNo);
				wayEntity.setActive(FossConstants.ACTIVE);
				//根据运单号 查询运单有效签收结果
				waybillSign = waybillSignResultDao.queryWaybillSignResult(wayEntity);
				//有签收结果信息，即有单号未反签收，前台限制反结清
				if(waybillSign != null){
					//不可以反结清
					dto.setIsAllReverse(FossConstants.NO);
					break;
				}
			}
		}
		
		//获得付款LIST
		getReturnRepaymentList(waybillNo, dto);
		// 获得到达联LIST,通过运单号
		ArriveSheetDto ae = new ArriveSheetDto();
		// 运单号
		ae.setWaybillNo(waybillNo);
		// 已签收确认
		List<String> arriveSheetStatusList = new ArrayList<String>();
		arriveSheetStatusList.add(ArriveSheetConstants.STATUS_SIGN);
		// 已签收状态
		ae.setArriveSheetStatus(arriveSheetStatusList);
		//激活状态
		ae.setActive(FossConstants.ACTIVE);
		//非销毁状态
		ae.setDestroyed(FossConstants.NO);
		// 获得运单对应的有效到达联信息
		List<ArriveSheetEntity> arriveSheetList = arrivesheetDao
				.queryArriveSheetByLifeCycle(ae);

		// 获得不在审批中的到达联
		List<ArriveSheetEntity> arriveSheetNotAuditList = new ArrayList<ArriveSheetEntity>();
		// 是否有审批中的到达联
		if (CollectionUtils.isNotEmpty(arriveSheetList)) {
			for (ArriveSheetEntity arriveSheetEntity : arriveSheetList) {
				// 不在审批中的到达联
				if (FossConstants.NO.equals(arriveSheetEntity.getIsRfcing())) {
					//到达联没有审批的数据
					arriveSheetNotAuditList.add(arriveSheetEntity);
				} else {
					//有审批中的到达联
					dto.setIsAuditingArrivesheetFlg(FossConstants.YES);
					LOGGER.info("没有数据");
				}
			}
		}else{
			dto.getMsgList().add("请确认该到达联已签收出库");
		}
		//从新设置返回的到达联信息List,追加了流水号
		List<ArriveSheetEntity> newarriveSheetList = getSerialNoString(arriveSheetNotAuditList);
		//返回前台的数据
		dto.setArriveSheetEntityList(newarriveSheetList);
		//打印日志
		LOGGER.info("searchRepaymentArrivesheet end......");
		//返回结果
		return dto;
	}
	/**
	 * 获取付款List
	 * @param waybillNo
	 * @param dto
	 */
	private void getReturnRepaymentList(String waybillNo,
			RepaymentArrivesheetDto dto) {
		// 获得付款LIST,通过运单号
		RepaymentEntity re = new RepaymentEntity();
		// 运单号
		re.setWaybillNo(waybillNo);
		// 状态
		re.setActive(FossConstants.ACTIVE);
		// 已生成
		re.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
		//是否有审批中的付款
		boolean isRepaymentFlg = false;
		//获得付款的签收数据
		List<RepaymentEntity> repaymentList = repaymentDao
				.queryRepaymentList(re);
		//追加无需生成的数据
		re.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
		//没有付款的签收数据
		List<RepaymentEntity> notrequireRepaymentList = repaymentDao
				.queryRepaymentList(re);
		//判断是否有无需生成的数据
		if(CollectionUtils.isNotEmpty(notrequireRepaymentList)){
			//遍历没有付款的签收数据数据
			for (RepaymentEntity repaymentEntity : notrequireRepaymentList) {
				//把无需生成的结清货款信息放入返回LIST中
				repaymentList.add(repaymentEntity);
			}
		}
		if(CollectionUtils.isNotEmpty(repaymentList)){
			//遍历查看是否有审批中的付款
			for (RepaymentEntity repaymentEntity : repaymentList) {
				//是否有审批中的数据
				if(FossConstants.YES.equals(repaymentEntity.getIsRfcing())){
					//设置FLG为true
					isRepaymentFlg = true;
					//退出
					break;
				} else {
					// 设置客户名称
					if (StringUtils.isNotEmpty(repaymentEntity.getConsigneeCode())) {
						CustomerDto customerDto = customerService.queryCustomerDtoByCustCode(repaymentEntity.getConsigneeCode());
						repaymentEntity.setConsigneeName(customerDto == null ? "" : customerDto.getName());
						repaymentEntity.setBeforeConsigneeName(customerDto == null ? "" : customerDto.getName());
					}
				}
			}
		//为空的情况
		}else{
			//添加消息
			dto.getMsgList().add("请确认该货款以结清");
		}
		//有审批中的付款信息
		if(isRepaymentFlg){
			//审批中的付款信息
			dto.setIsAuditingRepaymentFlg(FossConstants.YES);
			//有审批中的付款信息,付款信息不可以修改,设置为不可见
			dto.setRepaymentEntityList(null);
		}else{
			//设置返回数据
			dto.setRepaymentEntityList(repaymentList);
		}
	}
	
	private List<ArriveSheetEntity> getSerialNoString(
			List<ArriveSheetEntity> arriveSheetNotAuditList) {
		// 创建返回页面GRID中的数据对象
		List<ArriveSheetEntity> newarriveSheetList = new ArrayList<ArriveSheetEntity>();
		// 到达联的货物流水号信息
		List<SignDetailEntity> signDetailList;
		//定义一个连接字符
		StringBuffer sbBuffer;
		//对没有审批的到达联进行遍历
		for (ArriveSheetEntity arriveSheetEntity : arriveSheetNotAuditList) {
			//获得到达联LIST
			signDetailList = signDetailService
					.queryByArrivesheetNo(arriveSheetEntity.getArrivesheetNo());
			//实例化连接字符串对象
			sbBuffer = new StringBuffer();
			//遍历流水号LIST遍历
			for (SignDetailEntity signDetailEntity : signDetailList) {
				//做字符串连接
				sbBuffer.append(signDetailEntity.getSerialNo()).append(",");
			}
			//是否有流水号
			if (sbBuffer.length() > 1) {
				//对连接的字符串进行截取有效字符串
				arriveSheetEntity.setTagNumber(sbBuffer.substring(0,
						sbBuffer.length() - 1));
			}
			//获得序列号LIST
			newarriveSheetList.add(arriveSheetEntity);
		}
		//返回序列号集合
		return newarriveSheetList;
	}

	@Override
	@Transactional
	public void saveChangeDedicated(SignResultDto dto, CurrentInfo currentInfo) {
		LOGGER.info("saveChangeDedicated begin......");
		//互斥锁定
		MutexElement mutexElement = new MutexElement(dto.getSignRfcEntity().getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error(SignException.WAYBILL_LOCKED);//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		
		// 付款变更
		SignRfcEntity repaymentSignRfcEntity = new SignRfcEntity();
		// 到达联变更
		SignRfcEntity arriveSheetSignRfcEntity = new SignRfcEntity();
		// 货签表变更
		SignRfcEntity sLabelTableSignRfcEntity = new SignRfcEntity();
		// 运单号
		String waybillNo = dto.getSignRfcEntity().getWaybillNo();
		
		// 获得变更明细数据项
		List<SignRfcChangeDetailEntity> changeDetailentityList = dto
				.getChangeDetailentity();
		// 付款FLG
		boolean repaymentFlg = false;
		// 到达联FLG
		boolean arriveSheetFlg = false;
		// 货签表FLG
		boolean sLabelTableFlg = false;
		//正常签收变异常签收
		boolean normalToUnnormal = false;
		//异常签收线上划责dto
		RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();
		
		//判断集合是否为空
		if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
			//遍历改变详细的字段
			for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentityList) {
				//看是否有付款类型
				if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_REPAYMENT
						.equals(signRfcChangeDetailEntity.getRfcType())) {
					// 有付款类型，设置为真
					repaymentFlg = true;
				}else if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET
						.equals(signRfcChangeDetailEntity.getRfcType())) {//到达联
					//没有设置为假
					arriveSheetFlg = true;
				}else if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_LABELTABLEFLG
						.equals(signRfcChangeDetailEntity.getRfcType())) {//货签表
					sLabelTableFlg = true;
				}
			}
		}
		// 创建签收结果对象
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置运单号
		entity.setWaybillNo(waybillNo);
		// 状态
		entity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//判断是否为快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),waybill.getBillTime())
				&&!(waybill == null)&&!(resultEntity == null)){
			//判断是否为理赔状态和是否为异常签收
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveChangeDedicated",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				if(getClaimInfo(waybillNo)&&!SignConstants.NORMAL_SIGN.
						equalsIgnoreCase(resultEntity.getSignSituation())){
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException("已申请理赔，无法更改签收状态");
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO
			}
		}
		
		//根据BUG-57155 进行修改
		// 运单号
		repaymentSignRfcEntity.setWaybillNo(waybillNo);
		repaymentSignRfcEntity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
		if (isRfc(repaymentSignRfcEntity)) {
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(SignException.IS_NOT_APPROVAL);
		}
		// 付款的操作
		////在申请运单反货款结清和货款变更时，调用结算提供的接口，判断运单代收货款是否已付款。若已付代收货款，则不允许提交申请
		if (repaymentFlg) {
			try {
				//输入的数据进行逻辑校验
				checkWaybillRepayment(waybillNo);
				// 付款变更
				checkInputMoney(waybillNo,changeDetailentityList,dto.gettSrvRepaymentId());
			} catch (BusinessException e) {
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(e.getErrorCode(),e);
			}
			// 运单号
			repaymentSignRfcEntity.setWaybillNo(waybillNo);
			//付款编号
			repaymentSignRfcEntity.settSrvRepaymentId(dto.gettSrvRepaymentId());
			//改变原因
			repaymentSignRfcEntity.setReason(dto.getRepaymentChangeReason());
			// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
			repaymentSignRfcEntity
					.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
			// 变更类型--付款
			repaymentSignRfcEntity
					.setRfcType(SignConstants.SIGN_RFC_TYPE_REPAYMENT);
			// 对签收变更进行赋值
			setSignRfcInfo(currentInfo, repaymentSignRfcEntity);
			// 保存付款变更
			signRfcDao.insertSelective(repaymentSignRfcEntity);
			//保存附件
			saveAttachment(dto.getRepaymentFiles(),repaymentSignRfcEntity.getId());
			// 保存详细
			//判断集合是否为空
			if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
				//遍历集合
				for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentityList) {
					//是否有付款数据
					if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_REPAYMENT
							.equals(signRfcChangeDetailEntity.getRfcType())) {
						//设置付款数据
						signRfcChangeDetailEntity
								.settSrvSignRfcId(repaymentSignRfcEntity
										.getId());
						// 保存变更明细项
						signRfcChangeDetailDao
								.insertSelective(signRfcChangeDetailEntity);
					}
				}
			}
			// 更新付款状态
			RepaymentEntity repayment = new RepaymentEntity();
			//设置付款ID
			repayment.setId(dto.gettSrvRepaymentId());
			//设置状态为审批中
			repayment.setIsRfcing(FossConstants.YES);
			//更新付款状态
			repaymentDao.updateRepayment(repayment);
		}
		// 到达联操作
		if (arriveSheetFlg) {
			// 运单号
			arriveSheetSignRfcEntity.setWaybillNo(waybillNo);
			//设置到达联ID
			arriveSheetSignRfcEntity.settSrvArrivesheetId(dto
					.gettSrvArrivesheetId());
			//设置到达联变更理由
			arriveSheetSignRfcEntity
					.setReason(dto.getArrivesheetChangeReason());
			// 变更类型--到达联
			arriveSheetSignRfcEntity
					.setRfcType(SignConstants.SIGN_RFC_TYPE_ARRIVESHEET);
			// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
			arriveSheetSignRfcEntity
					.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);

			// 对签收变更进行赋值
			setSignRfcInfo(currentInfo, arriveSheetSignRfcEntity);
			/*if (isRfc(arriveSheetSignRfcEntity)) {
				businessLockService.unlock(mutexElement);//解锁
				// 已提交申请
				throw new SignException(SignException.IS_NOT_APPROVAL);
			}*/
			// 保存到达联变更
			signRfcDao.insertSelective(arriveSheetSignRfcEntity);
			//保存附件
			saveAttachment(dto.getArrivesheetFiles(),arriveSheetSignRfcEntity.getId());
			Integer result =0;
			// 保存详细
			//判断到达联变更详细集合是否为空
			if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
				//遍历变更到达联详细集合
				for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentityList) {
					if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET
							.equals(signRfcChangeDetailEntity.getRfcType())) {
						signRfcChangeDetailEntity
								.settSrvSignRfcId(arriveSheetSignRfcEntity
										.getId());
						//根据ISSUE-3456变更签收结果由异常签收变成正常签收的需要添加财务的校验规则做修改
						if(SignConstants.SITUATION.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
							if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
								//校验，是否有投诉自动变更后，变更签收结果为正常签收
								checkValidateChange(dto);                
								try{
									//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------start
									String vestSystemCode = null;
						            try {
						            	ArrayList<String> arrayList = new ArrayList<String>();
						            	arrayList.add(waybillNo);
						            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
						            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveChangeDedicated",
						            			SettlementConstants.TYPE_FOSS);
						            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
						            	List<VestBatchResult> list = response.getVestBatchResult();
						            	vestSystemCode = list.get(0).getVestSystemCode();		
									} catch (Exception e) {
										LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
										throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
									}
									if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
										lineSignService.reverseToNormalSignal(waybillNo,currentInfo);
									}
									if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
										CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
										requestDto.setSourceNo(waybillNo);
										requestDto.setRequestDate(new Date());
										requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
										requestDto.setCustomerName(currentInfo.getCurrentDeptName());
										requestDto.setOperatorCode(currentInfo.getEmpCode());
										requestDto.setOperatorName(currentInfo.getEmpName());
										requestDto.setOperationTag(SignConstants.OPERATIONTAG_SQ);
										//设置外发单号---外发签收
										List<String> list = externalBillService.getExternalBillNumListByWaybillNo(waybillNo);
										if(!list.isEmpty()){
											requestDto.setExternalWaybillNos(list);
											LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
										}
										CUBCSignChangeResultDto resultDto1 = null;
										try {
											resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
										} catch (Exception e) {
											throw new SettlementException("服务器正忙,CUBC申请签收变更失败,请稍后重试");
										}
										if(resultDto1 != null){
											if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
												if(StringUtils.isNotBlank(resultDto1.getMeg())){
													LOGGER.error("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
													throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
												}else{
													throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败但未获取到异常信息");
												}
											}		
										}
									}
									//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------end
								}catch(SettlementException e ){
									businessLockService.unlock(mutexElement);//解锁
									throw new SignException(e.getErrorCode(),e);
								}
								
							}
							result=NumberConstants.NUMBER_1;
							//变更项为签收情况时，如果变更前是正常签收，变更后肯定不是正常签收，可能需要上报异常线上划责
							if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getBeforeRfcinfo())){
								normalToUnnormal = true;
								//设置签收情况
								unnormalDto.setSignSituation(signRfcChangeDetailEntity.getAfterRfcinfo());
							}
						}
						//如果变更项为签收备注时，设置签收备注
						if(SignConstants.SIGN_NOTE.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
							//设置签收备注
							unnormalDto.setSignNote(signRfcChangeDetailEntity.getAfterRfcinfo());
						}
						if(result!=NumberConstants.NUMBER_1 &&SignConstants.SIGN_NOTE.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
							result=NumberConstants.NUMBER_2;
						}
						// 保存变更明细项
						signRfcChangeDetailDao.insertSelective(signRfcChangeDetailEntity);
					}
				}
			}
			try{
				if(result!=NumberConstants.NUMERAL_ZORE){
					changeOAErrorReport(waybillNo,currentInfo,result);
				}
			}catch(QmsErrorException e){
				LOGGER.info("运单号：" + waybillNo + "上报QMS反签收差错失败");
				businessLockService.unlock(mutexElement);//解锁
				// 已提交申请
				throw new SignException(e.getErrorCode());
			}
			ArriveSheetEntity arriveSheet = new ArriveSheetEntity();
			arriveSheet.setId(dto.gettSrvArrivesheetId());
			arriveSheet.setIsRfcing(FossConstants.YES);
			//更新到达联状态
			arrivesheetDao.updateByPrimaryKeySelective(arriveSheet);
		}
		
		StringBuffer serialNo=new StringBuffer();
		//货签表变更
		if(sLabelTableFlg){
				if(!arriveSheetFlg){
						// 运单号
						sLabelTableSignRfcEntity.setWaybillNo(waybillNo);
						//设置货签表ID
						sLabelTableSignRfcEntity.settSrvLabelTableId(dto.gettSrvLabelTableId());
						//设置到达联ID
						sLabelTableSignRfcEntity.settSrvArrivesheetId(dto.gettSrvArrivesheetId());
						//设置到货签表变更理由
						sLabelTableSignRfcEntity.setReason(dto.getArrivesheetChangeReason());
						// 变更类型--到达联变更
						sLabelTableSignRfcEntity.setRfcType(SignConstants.SIGN_RFC_TYPE_ARRIVESHEET);
						// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
						sLabelTableSignRfcEntity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
						// 对签收变更进行赋值
						setSignRfcInfo(currentInfo, sLabelTableSignRfcEntity);
						// 保存到达联变更
						signRfcDao.insertSelective(sLabelTableSignRfcEntity);
						//保存附件
						saveAttachment(dto.getArrivesheetFiles(),arriveSheetSignRfcEntity.getId());
				}else{
					sLabelTableSignRfcEntity.setId(arriveSheetSignRfcEntity.getId());
				}
				
				//判断集合是否为空
				if (CollectionUtils.isNotEmpty(changeDetailentityList)) {
					//异常件数,默认为0
					Integer unnormalNumber = NumberConstants.ZERO;
					//遍历集合
					for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentityList) {
						//是否货签表有变更数据
						if (SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_LABELTABLEFLG.equals(signRfcChangeDetailEntity.getRfcType())) {
							//设置货签表数据
							signRfcChangeDetailEntity.settSrvSignRfcId(sLabelTableSignRfcEntity.getId());
							/**
							 * 由于货签表变更,变更前信息和变更后信息的数据格式为:流水号,签收情况 
							*/
							//String tmpAfter=signRfcChangeDetailEntity.getAfterRfcinfo().substring(signRfcChangeDetailEntity.getAfterRfcinfo().indexOf(".")+1);
							//String tmpBefore=signRfcChangeDetailEntity.getBeforeRfcinfo().substring(signRfcChangeDetailEntity.getBeforeRfcinfo().indexOf(".")+1);
							signRfcChangeDetailEntity.setAfterRfcinfo(signRfcChangeDetailEntity.getAfterRfcinfo());
							signRfcChangeDetailEntity.setBeforeRfcinfo(signRfcChangeDetailEntity.getBeforeRfcinfo());
							// 保存变更明细项
							signRfcChangeDetailDao.insertSelective(signRfcChangeDetailEntity);
							//上报内物短少差错
							String afterRfcinfo = signRfcChangeDetailEntity.getAfterRfcinfo();
							String beforeRfcinfo = signRfcChangeDetailEntity.getBeforeRfcinfo();
							if(StringUtils.isNotEmpty(afterRfcinfo)&&afterRfcinfo.contains(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT)){
								serialNo.append(afterRfcinfo.split("\\.")[0]).append(SignConstants.SPLIT_CHAR);
							}else if(StringUtils.isNotBlank(beforeRfcinfo)&&beforeRfcinfo.contains(SignConstants.NORMAL_SIGN)
									&& normalToUnnormal){
								//到达联变更前是正常签收，
								//并且当前流水号变更前是正常签收，变更后是异常签收时,异常件数+1
								++unnormalNumber;
							}
						}
					}
					String rstSerialNo=StringUtils.EMPTY;
					if(serialNo!=null && serialNo.toString().length()>0){
						//截取最后一个“,”
								rstSerialNo = serialNo.substring(0, serialNo.length()-1);
							if(StringUtils.isNotEmpty(rstSerialNo)){
							this.saveRecordShortErrorInfo(waybillNo, currentInfo, rstSerialNo);
						}
					}else if(unnormalNumber > NumberConstants.ZERO){
						//运单号
						unnormalDto.setWaybillNo(waybillNo);
						//异常件数
						unnormalDto.setUnnormalNumber(unnormalNumber);
						//变更前是正常签收，变更后是异常签收时，上报“异常签收线上划责”
						this.saveRecordUnnormalErrorInfo(unnormalDto);
					}
				}
		}
		
		
		try {
			this.createBatchInstationMsg(waybillNo,currentInfo);
		} catch (BusinessException e) {
			businessLockService.unlock(mutexElement);//解锁
			throw new SignException(e.getErrorCode(),e);
		}
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("saveChangeDedicated end......");
	}
	/**
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.SignChangeService.recordErrorWaybill
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报QMS
	 * @date:2014年12月5日 下午15:59:21
	 * update by foss-231434-bieyexiong
	 */
	private void saveRecordShortErrorInfo(String waybillNo, CurrentInfo currentInfo,String rstSerialNo){
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
		if(currentInfo != null){
			RecordErrorWaybillDto recordErrorWaybillDto = new RecordErrorWaybillDto();
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(waybillNo);
			//短少量
			recordErrorWaybillDto.setAlittleShort(null);
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(null);
			//创建时间
			recordErrorWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorWaybillDto.setModifyTime(new Date());
			//上报人工号
			recordErrorWaybillDto.setOperateCode(currentInfo.getEmpCode());
			//上报人名字
			recordErrorWaybillDto.setOperateName(currentInfo.getEmpName());
			//上报人所在部门编码
			recordErrorWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//流水号
			recordErrorWaybillDto.setSerialNo(rstSerialNo);
			recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
		}
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
	}
	
	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 * PdaDeliverSignDto dto,ArriveSheetEntity entity,CurrentInfo currentInfo
	 */
	private void saveRecordImportantErrorInfo(String waybillNo,CurrentInfo currentInfo,WaybillEntity waybill){
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********start");
		if( currentInfo != null && waybill != null){
			RecordErrorImportantWaybillDto recordErrorImportantWaybillDto = new RecordErrorImportantWaybillDto();
			//主键
			recordErrorImportantWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorImportantWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorImportantWaybillDto.setWaybillNo(waybillNo);
			//保价声明价值
			recordErrorImportantWaybillDto.setInsuranceAmount(waybill.getInsuranceAmount());
			//创建时间
			recordErrorImportantWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorImportantWaybillDto.setModifyTime(new Date());
			//开单时间
			recordErrorImportantWaybillDto.setBillTime(waybill.getBillTime());
			//上报人名字
			recordErrorImportantWaybillDto.setOperateName(currentInfo.getEmpName());
			//上报人工号
			recordErrorImportantWaybillDto.setOperateCode(currentInfo.getEmpCode());
			//上报人所在部门编码
			recordErrorImportantWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorImportantWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//foss记录重大货物异常自动上报信息 OA
			recordErrorWaybillService.recordErrorImportantWaybillReportOA(recordErrorImportantWaybillDto);
		}
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********end");
	}


	/**
	 * @author: foss-231434-bieyexiong
	 * @description: foss记录异常 上报QMS
	 * @date:2016年02月18日 下午15:37:21
	 */
	private void saveRecordUnnormalErrorInfo(RecordUnnormalSignWaybillDto unnormalDto){
		if(unnormalDto != null){
			//运单明细
			WaybillEntity waybill = waybillManagerService.
										queryWaybillBasicByNo(unnormalDto.getWaybillNo());
			if(waybill != null 
					&& !waybillExpressService.onlineDetermineIsExpressByProductCode
								(waybill.getProductCode(),waybill.getBillTime())){
				WaybillSignResultEntity wentity = new WaybillSignResultEntity();
				wentity.setWaybillNo(unnormalDto.getWaybillNo());
				wentity.setActive("Y");
				//查询签收的签收结果信息
				WaybillSignResultEntity wrentity = waybillSignResultDao.queryWaybillSignResult(wentity);
				//运单签收结果不为null，并且已全部签收，才需要上报签收异常线上划责差错
				if(wrentity != null && SignConstants.SIGN_STATUS_ALL.equals(wrentity.getSignStatus())){
					
					LOGGER.info("*************保存QMS异常签收线上划责信息***********start");
					//主键id
					unnormalDto.setId(UUIDUtils.getUUID());
					//签收时间
					unnormalDto.setSignTime(wrentity.getSignTime());
					if(StringUtils.isBlank(unnormalDto.getSignNote())){
						//签收备注
						unnormalDto.setSignNote(wrentity.getSignNote());
					}
					//是否已上报(默认为Y，未上报)
					unnormalDto.setActive("Y");
					//创建时间
					unnormalDto.setCreateTime(new Date());
					//备注
					unnormalDto.setNote("未上报");
					
					//保存异常划责信息
					recordErrorWaybillDao.insertUnnormalEntity(unnormalDto);
					
					LOGGER.info("*************保存QMS异常签收线上划责信息***********end");
				}
			}
		}
	}
	
	/**
	 * 变更签收结果进行OA差错上报
	 * @param waybillNo
	 * @param currentInfo
	 * @param result
	 * update by foss-bieyexiong
	 */
	private void changeOAErrorReport(String waybillNo,CurrentInfo currentInfo,Integer result){
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (waybill != null) {
			WaybillSignResultEntity wentity = new WaybillSignResultEntity();
			wentity.setWaybillNo(waybillNo);
			wentity.setActive("Y");
			//查询第一次全部签收的签收结果信息
			WaybillSignResultEntity wrentity = waybillSignResultDao
					.queryFirstSignAllByEntity(wentity);
			if (wrentity != null) {
				Date cdate = wrentity.getSignTime();
				Date ndate = new Date();
				/**
				 * 淘宝快递运单签收时间为第二天凌晨算起
				 * @author 302346-foss-Jiang Xun
				 * @date 2015-12-12 下午15:00:00
				 */
				if(WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equalsIgnoreCase(waybill.getOrderChannel())){
					Calendar cal = Calendar.getInstance();
					cal.setTime(cdate);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cdate = cal.getTime();
				}
				//反签收时间-签收时间
				double ldate = (ndate.getTime() - cdate.getTime()) / 1000 / 3600.0;
				//零担大客户（发货客户）、距离第一次全部签收小于等于480小时，不上报反签收差错
				if(!ProductCodeUtils.isExpress(waybill.getProductCode())
						&&(FossConstants.YES.equals(waybill.getDeliveryBigCustomer()))
						&& ldate <= SettlementReportNumber.FOUR_HUNDRED_AND_EIGHTY){
					ldate = NumberConstants.NUMERAL_ZORE;
				}
				//签收情况
				String signSituation = wrentity.getSignSituation();
				//签收状态
				String signStatus = wrentity.getSignStatus();
				if (SignConstants.SIGN_STATUS_ALL.equals(signStatus)) {
					//如果不为快递，上报OA变更签收差错,距离第一次签收超过72小时
					if (ldate > SettlementReportNumber.SEVENTY_TWO && !waybillExpressService.onlineDetermineIsExpressByProductCode
											(waybill.getProductCode(),waybill.getBillTime())) {
						if(result ==NumberConstants.NUMBER_1
								|| (result ==NumberConstants.NUMBER_2
										&&(!SignConstants.NORMAL_SIGN.equals(signSituation)))){
							LOGGER.info("*************上报QMS零担反签收差错***********start");
							//得到差错上报json字符串
							Map<String,Object> errorInfoMap = qmsErrorService.getLDReverseErrorQmsInfo(waybill, currentInfo);
							qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
							LOGGER.info("*************上报QMS零担反签收差错***********end");
						}
					} else if(ldate > SettlementReportNumber.ONE_HUNDRED_AND_TWENTY && result == NumberConstants.NUMBER_1
									&& waybillExpressService.onlineDetermineIsExpressByProductCode
											(waybill.getProductCode(),waybill.getBillTime())){
						validateCurrentInfo(currentInfo, waybill, ldate);
					}
				}
			}
		}
	}

	private void validateCurrentInfo(CurrentInfo currentInfo,
			WaybillEntity waybill, double ldate) {
		if(WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equalsIgnoreCase(waybill.getOrderChannel())){
			if(ldate > SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_EIGHT){//7天
				//运输性质为淘宝快递,且满足上报条件（超过168小时反签收），上报QMS快递变更签收结果差错,
				LOGGER.info("*************上报QMS淘宝快递反签收差错***********start");
				//得到差错上报json字符串
				Map<String,Object> errorInfoMap = qmsErrorService.getReverseErrorQmsInfo(waybill, currentInfo);
				qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
				LOGGER.info("*************上报QMS淘宝快递反签收差错***********end");
			}
		}else{
			// 运输性质为快递时,且变更项为签收情况，且距离第一次签收超过120小时，上报QMS反签收差错
			LOGGER.info("*************上报QMS快递反签收差错***********start");
			//得到差错上报json字符串
			Map<String,Object> errorInfoMap = qmsErrorService.getReverseErrorQmsInfo(waybill, currentInfo);
			//差错上报
			qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
			LOGGER.info("*************上报QMS快递反签收差错***********end");
		}
	}
	/**
	 * 反签收差错上报
	 * @author: foss-bieyexiong
	 * @date:2015年4月7日 下午15:41:36
	 * */
	private void reverseErrorReport(String waybillNo,CurrentInfo currentInfo){
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		double span = 0.0;
		if(waybill != null ){
			span = checkReportError(waybill);
			if(!waybillExpressService.onlineDetermineIsExpressByProductCode
					(waybill.getProductCode(),waybill.getBillTime())){
				//判断是否满足反签收上报条件（第一次全部签收出库后，超过72小时反签收）
				if(span > SettlementReportNumber.SEVENTY_TWO){
				//运输性质为零担,且满足上报条件（超过72小时反签收），上报QMS零担反签收差错,
				LOGGER.info("*************上报QMS零担反签收差错***********start");
				//得到差错上报json字符串
				Map<String,Object> errorInfoMap = qmsErrorService.getLDReverseErrorQmsInfo(waybill, currentInfo);
				qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
				LOGGER.info("*************上报QMS零担反签收差错***********end");
				}
			}else if(span > SettlementReportNumber.ONE_HUNDRED_AND_TWENTY){
				if(WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(waybill.getOrderChannel())){
					if(span > SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_EIGHT){//7天
						//运输性质为淘宝快递,且满足上报条件（超过168小时反签收），上报QMS快递反签收差错,
						LOGGER.info("*************上报QMS淘宝快递反签收差错***********start");
						//得到差错上报json字符串
						Map<String,Object> errorInfoMap = qmsErrorService.getReverseErrorQmsInfo(waybill, currentInfo);
						qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
						LOGGER.info("*************上报QMS淘宝快递反签收差错***********end");
					}
				}else{
					//运输性质为快递,且满足上报条件（超过120小时反签收），上报QMS快递反签收差错,
					LOGGER.info("*************上报QMS快递反签收差错***********start");
					//得到差错上报json字符串
					Map<String,Object> errorInfoMap = qmsErrorService.getReverseErrorQmsInfo(waybill, currentInfo);
					qmsErrorReportService.reportQmsReverseSignError(errorInfoMap);
					LOGGER.info("*************上报QMS快递反签收差错***********end");
				}
			}
		}
	}
	/**
	 * 描述：判断是否满足反签收上报条件（第一次全部签收出库后，超过72小时反签收）
	 * @author 231434-foss-bieyexiong
	 * @date 2015-3-31 下午17:06:40
	 * */
	public double checkReportError(WaybillEntity waybill){
		if(waybill != null){
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
			entity.setWaybillNo(waybill.getWaybillNo());
		entity.setActive("Y");
		//查询第一次全部签收的签收结果信息
		WaybillSignResultEntity wentity=waybillSignResultDao.queryFirstSignAllByEntity(entity);
		if(wentity!=null){
			Date cdate =wentity.getSignTime();
			Date ndate =new Date();
			/**
			 * 淘宝快递运单签收时间为第二天凌晨算起
			 * @author 302346-foss-Jiang Xun
			 * @date 2015-12-11 下午16:34:20
			 */
			if(WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equalsIgnoreCase(waybill.getOrderChannel())){
				Calendar cal = Calendar.getInstance();
				cal.setTime(cdate);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cdate = cal.getTime();
			}
			
			//反签收时间-签收时间
			double ldate =(ndate.getTime()-cdate.getTime())/1000/3600.0;
			//签收情况
			String signSituation =wentity.getSignSituation();
			//签收状态
			String signStatus =wentity.getSignStatus();
			if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
				//异常签收
				if(SignConstants.UNNORMAL_SIGN.equals(signSituation)){
					// 获得到达联LIST,通过运单号
					ArriveSheetDto ae = new ArriveSheetDto();
					// 运单号
						ae.setWaybillNo(waybill.getWaybillNo());
					// 已签收确认
					List<String> arriveSheetStatusList = new ArrayList<String>();
					arriveSheetStatusList.add(ArriveSheetConstants.STATUS_SIGN);
					// 已签收状态
					ae.setArriveSheetStatus(arriveSheetStatusList);
					//激活状态
					ae.setActive(FossConstants.ACTIVE);
					//非销毁状态
					ae.setDestroyed(FossConstants.NO);
					// 获得运单对应的有效到达联信息
					List<ArriveSheetEntity> arriveSheetList = arrivesheetDao.queryArriveSheetByLifeCycle(ae);
					for(ArriveSheetEntity ety:arriveSheetList){
						if(SignConstants.PARTIAL_SIGN.equals(ety.getSituation())){
							return NumberConstants.NUMERAL_ZORE;
						}
					}
				}
					//零担大客户（发货客户）、距离第一次全部签收小于等于480小时，不上报反签收差错
					if(!ProductCodeUtils.isExpress(waybill.getProductCode())
							&& (FossConstants.YES.equals(waybill.getDeliveryBigCustomer()))
									&& ldate <= SettlementReportNumber.FOUR_HUNDRED_AND_EIGHTY){
						return NumberConstants.NUMERAL_ZORE;
					}
				return ldate;
			}
		}
		}
		return NumberConstants.NUMERAL_ZORE;
	}
	
	/**
	 * 
	 * <p>申请运单反货款结清和货款变更时，
	 * 调用结算提供的接口，
	 * 判断运单代收货款是否已付款。
	 * 若已付代收货款，
	 * 则不允许提交申请<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-12
	 * void
	 */
	private void checkWaybillRepayment(String waybillNo) {
		try {
			//申请运单反货款结清和货款变更时，调用结算提供的接口，判断运单代收货款是否已付款。若已付代收货款，则不允许提交申请
			//校验代收货款是否已支付   灰度   353654 ------------------------ start
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".checkWaybillRepayment",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                //调用FOSS
			    if (!codCommonService.checkCodHasPaymentByWaybillNo(waybillNo,
					SettlementConstants.LINE_SIGN)) {
				//抛出异常
				throw new SignException(
						"运单代收货款已付款,不允许提交申请或者存在服务补救应付单和理赔应付单，不能操作！");
			    }
            }
            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					//add by 353654
					LOGGER.info("CUBC开始检查运单对应的代收货款是否已付款（冻结之后）");
					if (StringUtils.isBlank(waybillNo)) {
						throw new SettlementException("CUBC内部错误，运单号为空");
					}
					//校验签收类型
					String signType = SettlementConstants.LINE_SIGN;
					List<String> signTypeList = new ArrayList<String>(SettlementReportNumber.THREE);
					signTypeList.add(SettlementConstants.LINE_SIGN);
					signTypeList.add(SettlementConstants.AIR_SIGN);
					signTypeList.add(SettlementConstants.PARTIAL_LINE_SIGN);
					if (StringUtils.isBlank(signType) || !signTypeList.contains(signType)) {
						throw new SettlementException("内部错误，签收类型异常：" + signType);
					}
					//调用CUBC代收货款查询接口校验代收货款
					List<String> waybillNoList = new ArrayList<String>();
					waybillNoList.add(waybillNo);
					CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
					requestDto.setWaybillNo(waybillNoList);
					CUBCCodAuditResultDto resultDto = null;
					try {
						resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
					}catch (Exception e) {
						throw new SettlementException("服务器正忙,CUBC校验代收货款失败,请稍后重试");
					}
					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = null;
					//只有专线和空运有代收货款 不能存在资金部冻结、退款中、退款成功的代收货款信息
					if(resultDto != null){
						if(StringUtils.isNotBlank(resultDto.getMeg())){
							LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
							throw new SettlementException(resultDto.getMeg());	
						}
						auditList = resultDto.getCodAuditList();
						if(CollectionUtils.isNotEmpty(auditList)){
							com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto = auditList.get(0);
							if(codAuditDto != null){
								//空运反签收时
								if(StringUtils.equals(signType, SettlementConstants.AIR_SIGN)
										&&StringUtils.equals(codAuditDto.getAirStatus(), SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE)){
									throw new SettlementException("空运代收货款已审核，不能进行后续操作！");
								}
								// 冻结之后状态
								List<String> statusList = new ArrayList<String>(5);
								statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);
								statusList.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
								statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
								statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
								statusList.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);
								if(StringUtils.isNotBlank(codAuditDto.getStatus())){
									if(statusList.contains(codAuditDto.getStatus())){
										throw new SettlementException("存在退款中或退款成功的代收货款，不能进行后续操作");
									}
								}else{
									throw new SettlementException("代收货款状态异常：" + codAuditDto.getStatus());
								}
							}
						}
					}else{
						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款审核");
					}
					/*//调用CUBC物流交易单查询接口校验应付
					boolean flag = false;//校验标记
					FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
					requestDto1.setWaybillNos(waybillNoList);
					FossSearchTradeResponseDO responseDto1 = null;
					try {
						responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
					} catch (Exception e) {
						throw new SettlementException("服务器正忙,查询物流交易单失败,请稍后重试");
					}
					if(responseDto1 != null){
						if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
							LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
							throw new SettlementException(responseDto1.getMsg());
						}
						Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
						List<TradeDO> tradeslist = dataMap.get(waybillNo);
						if(!CollectionUtils.isEmpty(tradeslist)){
							//专线签收
							if(SettlementConstants.LINE_SIGN.equals(signType)){
								for (TradeDO tradeDO : tradeslist) {
									if(tradeDO.getOrderSubType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM)//理赔应付单
											||tradeDO.getOrderSubType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION)//服务补救应付单
											||tradeDO.getOrderSubType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD)//代收货款应付单
											||tradeDO.getOrderSubType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST)//整车尾款应付单
											||tradeDO.getOrderSubType().equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE)){//装卸费应付单
										flag = true;
									}
								}
							}
							if(flag){
								LOGGER.error("专线反签收,不允许存在理赔,代收货款,服务补救,装卸费,整车尾款应付流水存在");
								throw new SettlementException("专线反签收,不允许存在理赔,代收货款,服务补救,装卸费,整车尾款应付流水存在");
							}
							for (TradeDO tradeDO : tradeslist) {
								if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(tradeDO.getOrderSubType())){
									throw new SettlementException("运单存在理赔应付单，不能进行后续操作！");
								}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(tradeDO.getOrderSubType())){
									throw new SettlementException("运单存在服务补救应付单，不能进行后续操作！");
								}
								//已核销金额大于0
								if (tradeDO.getVerifyAmount() != null && tradeDO.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
									throw new SettlementException("应付单已核销，不能进行反签收操作！");
								}
								//应付单正在付款中或已付款
								if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(tradeDO.getPayStatus())) {
									throw new SettlementException("应付单付款中或已付款，不能进行反签收操作");
								}
							}
						}
					}else{
						LOGGER.info("CUBC,单号："+waybillNo+"未查询到物流交易单信息");
					}*/
				
            }
            //校验代收货款是否已支付   灰度   353654 ------------------------ end
		//捕获异常
		} catch (SettlementException ex) {
			//抛出异常
			throw new SignException(ex.getErrorCode());
		}
	}

	@Override
	public WaybillSignResultEntity searchWaybillSignResult(String waybillNo) {
		LOGGER.info("searchWaybillSignResult begin......");
		//是否为本部门
		WaybillEntity waybillEntity = checkWayBillAndReturnWaybillEntity(waybillNo);
		//判断运单是否为空
		if(waybillEntity == null 
				|| (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
						&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode()))
				&& !waybillExpressService.onlineDetermineIsExpressByProductCode
							(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
			throw new SignException("数据没找到，非空运/偏线/快递运单");
		}
		//判断为悟空的运单 ，提示悟空-变更签收结果界面做操作 362309
		if(FossConstants.YES.equals(waybillEntity.getIsecs())){
			throw new SignException("查询失败："+waybillNo + "单号为悟空的运单请在悟空界面进行操作！");
		}			
		if (waybillExpressService.onlineDetermineIsExpressByProductCode
					(waybillEntity.getProductCode(),waybillEntity.getBillTime())) {
			OuterBranchExpressEntity ldpOrg = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
					waybillEntity.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
			if (ldpOrg == null) {
				throw new SignException("运单对应的提货网点不是快递代理网点！");
			}
		}
		
		// 创建对象
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置运单号
		entity.setWaybillNo(waybillNo);
		// 状态
		entity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService
				.queryWaybillSignResultByWaybillNo(entity);
		//判断运单签收结果是否为空
		if(resultEntity != null){
			//add by chenjunying 2015-02-05 菜鸟对接 ----
			//判断运单为快递 ，限制返货新单的原单反签收
			if(waybillExpressService.onlineDetermineIsExpressByProductCode
							(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
				LOGGER.info("验证运单是否有返货有效新单开始......");
				WaybillExpressEntity expEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(
						waybillNo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if(expEntity != null){ //有返货新单
					//原单查出的返货新单 有未作废的
					LOGGER.info("运单存在有效返货新单，不允许原单反签收");
					throw new SignException("该运单存在有效返货新单，原单不允许反签收");
				}
				//为新单，或原单无有效返货新单 允许当前单号反签收
				LOGGER.info("验证运单是否有返货有效新单结束......");
			}
			//----
			//运单签收件数
			Integer signCount = getkAirliftPartialSignCount(waybillNo);
			//设置签收件数
			resultEntity.setSignCount(signCount);
		}else{
			//日志信息
			LOGGER.info("空运偏线数据没有找到--运单号=="+waybillNo);
		}
		//日志信息
		LOGGER.info("searchWaybillSignResult end......");
		//返回签收结果信息
		return resultEntity;
	}

	@Override
	@Transactional
	public void saveChangeAirliftPartialLine(SignResultDto dto,
			CurrentInfo currentInfo) {
		LOGGER.info("saveChangeAirliftPartialLine begin......");
		//判断数据是否合理
		checkAirliftPartialLineData(dto);
		//获得变更签收实体
		SignRfcEntity record = dto.getSignRfcEntity();
		// 对签收变更进行赋值
		setSignRfcInfo(currentInfo, record);
		// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
		record.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
		// 变更类型--空运偏线--运单签收结果
		record.setRfcType(SignConstants.SIGN_RFC_TYPE_WAYBILL);
		//互斥锁定
		MutexElement mutexElement = new MutexElement(dto.getSignRfcEntity().getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error(SignException.WAYBILL_LOCKED);//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		if (isRfc(record)) {
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(SignException.IS_NOT_APPROVAL);
		}
		// 运单号
		String waybillNo = dto.getSignRfcEntity().getWaybillNo();
		// 创建签收结果对象
		WaybillSignResultEntity waybillSignResultEntityentity = new WaybillSignResultEntity();
		//设置运单号
		waybillSignResultEntityentity.setWaybillNo(waybillNo);
		// 状态
		waybillSignResultEntityentity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntityentity);
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//判断是否为快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),waybill.getBillTime())
				&&!(waybill == null)&&!(resultEntity == null)){
			//判断是否为理赔状态和是否为异常签收
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveChangeAirliftPartialLine",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				if(getClaimInfo(waybillNo)&&!SignConstants.NORMAL_SIGN.
						equalsIgnoreCase(resultEntity.getSignSituation())){
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException("已申请理赔，无法更改签收状态");
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO
			}
		}
		
		// 保存
		signRfcDao.insertSelective(record);
		//保存附件
		saveAttachment(dto.getWaybillSignResultFiles(),record.getId());
		//获得变更签收字段集合
		List<SignRfcChangeDetailEntity> changeDetailentity = dto
				.getChangeDetailentity();
		Integer result =0;
		//正常签收变异常签收
		boolean normalToUnnormal = false;
		//异常签收线上划责dto
		RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();

		//判断集合是否为空
		if (CollectionUtils.isNotEmpty(changeDetailentity)) {
			//遍历变更字段集合
			for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentity) {
				//设置变更ID
				signRfcChangeDetailEntity.settSrvSignRfcId(record.getId());
				//设置变更类型
				signRfcChangeDetailEntity
						.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT);
				//根据ISSUE-3456变更签收结果由异常签收变成正常签收的需要添加财务的校验规则做修改
				if(SignConstants.SIGN_SITUATION.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
					if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
						//校验，是否有投诉自动变更后，变更签收结果为正常签收
						checkValidateChange(dto);     
						try{
							//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------start
							String vestSystemCode = null;
				            try {
				            	ArrayList<String> arrayList = new ArrayList<String>();
				            	arrayList.add(waybillNo);
				            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveChangeAirliftPartialLine",
				            			SettlementConstants.TYPE_FOSS);
				            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
				            	List<VestBatchResult> list = response.getVestBatchResult();
				            	vestSystemCode = list.get(0).getVestSystemCode();		
							} catch (Exception e) {
								LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
							}
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
								lineSignService.reverseToNormalSignal(dto.getSignRfcEntity().getWaybillNo(),currentInfo);
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
								CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
								requestDto.setSourceNo(dto.getSignRfcEntity().getWaybillNo());
								requestDto.setRequestDate(new Date());
								requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
								requestDto.setCustomerName(currentInfo.getCurrentDeptName());
								requestDto.setOperatorCode(currentInfo.getEmpCode());
								requestDto.setOperatorName(currentInfo.getEmpName());
								requestDto.setOperationTag(SignConstants.OPERATIONTAG_SQ);
								//设置外发单号---外发签收
								List<String> list = externalBillService.getExternalBillNumListByWaybillNo(waybillNo);
								if(!list.isEmpty()){
									requestDto.setExternalWaybillNos(list);
									LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
								}
								CUBCSignChangeResultDto resultDto1 = null;
								try {
									resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
								} catch (Exception e) {
									throw new SettlementException("服务器正忙,CUBC申请签收变更失败,请稍后重试");
								}
								if(resultDto1 != null){
									if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
										if(StringUtils.isNotBlank(resultDto1.getMeg())){
											LOGGER.error("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
											throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
										}else{
											throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败但未获取到异常信息");
										}
									}		
								}
							}
							//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------end
						}catch(SettlementException e ){
							businessLockService.unlock(mutexElement);//解锁
							throw new SignException(e.getErrorCode(),e);
						}
					}
					//上报 OA 内物短少差错
					if(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
						this.saveRecordShortErrorInfo(dto.getSignRfcEntity().getWaybillNo(),currentInfo,null);
					}else if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getBeforeRfcinfo())
							&& !ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
						//正常签收变异常签收
						normalToUnnormal = true;
						//设置运单号
						unnormalDto.setWaybillNo(dto.getSignRfcEntity().getWaybillNo());
						//设置签收情况
						unnormalDto.setSignSituation(signRfcChangeDetailEntity.getAfterRfcinfo());
						//设置异常签收件数(空运偏线流水号只有1件)
						unnormalDto.setUnnormalNumber(NumberConstants.ONE);
					}
					//306548保存QMS重大货物异常差错信息(签收情况为异常，保价金额>=10000)
					if(ReportConstants.UNNORMAL_SIGN.equals(signRfcChangeDetailEntity.getBeforeRfcinfo())&& !ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(signRfcChangeDetailEntity.getAfterRfcinfo())&&(waybill.getInsuranceAmount().compareTo(INSURANCEAMOUNT_NUM)!=-1)){
						this.saveRecordImportantErrorInfo(dto.getSignRfcEntity().getWaybillNo(),currentInfo,waybill);
					}
					result=1;
				}
				//如果变更项为签收备注，设置签收备注
				if(SignConstants.SIGN_NOTE.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
					//设置备注
					unnormalDto.setSignNote(signRfcChangeDetailEntity.getAfterRfcinfo());
				}
				if(result!=1 &&SignConstants.SIGN_NOTE.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
					result=2;
				}
				//保存变更信息字段
				signRfcChangeDetailDao.insertSelective(signRfcChangeDetailEntity);
			}
			//如果 是 正常签收变异常签收
			if(normalToUnnormal){
				//变更前是正常签收，变更后是异常签收时，上报“异常签收线上划责”
				this.saveRecordUnnormalErrorInfo(unnormalDto);
			}
		}
		try{
			if(result!=0){
				changeOAErrorReport(dto.getSignRfcEntity().getWaybillNo(),currentInfo,result);
			}
		}catch(QmsErrorException e){
			LOGGER.info("运单号：" + record.getWaybillNo() + "上报QMS反签收差错失败");
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(e.getErrorCode());
		}
		// 更新签收结果状态
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置变更ID
		entity.setId(record.gettSrvWaybillSignResultId());
		//设置变更审批状态
		entity.setIsRfcing(FossConstants.YES);
		//更新审批状态
		waybillSignResultService.updateWaybillSignResultById(entity);
		businessLockService.unlock(mutexElement);//解锁
		//日志信息
		LOGGER.info("saveChangeAirliftPartialLine end......");
	}

	private void checkAirliftPartialLineData(SignResultDto dto) {
		if (dto == null || dto.getSignRfcEntity() == null 
				|| StringUtils.isEmpty(dto.getSignRfcEntity().getWaybillNo())) {
			throw new SignException("请重输入运单号，点击查询后再提交！");
		}
		//查询条件实体
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置运单号
		entity.setWaybillNo(dto.getSignRfcEntity().getWaybillNo());
		//设置运单状态
		entity.setActive(FossConstants.YES);
		//获得运单的签收件数
		Integer count =  waybillSignResultService.queryWaybillQty(entity);
		count = count == null ? 0 : count; 
		//获得变更申请数据
		List<SignRfcChangeDetailEntity> list = dto.getChangeDetailentity();
		//定义签收件数
		Integer signCount = 0;
		//遍历签收比昂个字段集合
		for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : list) {
			//是否对签收件数做了修改
			if(SignConstants.SIGNGOODSQTY.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
				//得到变更后的签收件数
				signCount = Integer.parseInt(signRfcChangeDetailEntity.getAfterRfcinfo());
			}
		}
		//签收件数要小于运单对应的货物件数
		if(count < signCount){
			//抛出异常
			throw new SignException("签收件数不正确,请重输入！");
		}else{
			LOGGER.info("正确数据");
		}
	}

	private Integer getkAirliftPartialSignCount(String waybillNo) {
		//查询条件实体
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置运单号
		entity.setWaybillNo(waybillNo);
		//设置运单状态
		entity.setActive(FossConstants.YES);
		//获得运单的签收件数
		return waybillSignResultService.queryWaybillQty(entity);
	}

	@Override
	public RepaymentArrivesheetDto searchReverseSignDedicatedInfo(String waybillNo) {
		LOGGER.info("searchReverseSignInfo begin......");
		//获得运单信息
		WaybillEntity waybillEntity = waybillManagerService
				.queryWaybillBasicByNo(waybillNo);
		if (waybillEntity == null || StringUtils.isEmpty(waybillEntity.getId())) {
			throw new SignException("该运单不存在");
		}
		//判断运单为快递 ，限制返货新单的原单反签收
		if(waybillExpressService.onlineDetermineIsExpressByProductCode
						(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
			LOGGER.info("验证运单是否有返货有效新单开始......");
			WaybillExpressEntity expEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(
					waybillNo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if(expEntity != null){ //有返货新单
				//原单查出的返货新单 有未作废的
				LOGGER.info("运单存在有效返货新单，不允许原单反签收");
				throw new SignException("该运单存在有效返货新单，原单不允许反签收");
			}
			//为新单，或原单无有效返货新单 允许当前单号反签收
			LOGGER.info("验证运单是否有返货有效新单结束......");
		}
		
		//获得反签收信息
		RepaymentArrivesheetDto dto = searchRepaymentArrivesheet(waybillNo);
		// 查看是否有状态为审批中的到达联
		if (FossConstants.YES.equals(dto.getIsAuditingArrivesheetFlg())) {
			// 如有审批中的到达联，则不可以对付款进行反结清
			dto.setRepaymentEntityList(null);
		}
		//设置发货部门的code到name
		waybillEntity.setReceiveOrgCode(getNameByCode(waybillEntity.getReceiveOrgCode()));
		//设置最终配载部门的CODE到NAME
		waybillEntity.setLastLoadOrgCode(getNameByCode(waybillEntity.getLastLoadOrgCode()));
		// 运单
		dto.setWaybillEntity(waybillEntity);
		//日志信息
		LOGGER.info("searchReverseSignInfo end......");
		//返回DTO
		return dto;
	}

	@Override
	@Transactional
	public void saveReverseSignDedicatedInfo(SignResultDto dto, CurrentInfo currentInfo) {
		LOGGER.info("saveReverseSignInfo begin......");
		//互斥锁定
		MutexElement mutexElement = new MutexElement(dto.getSignRfcEntity().getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error(SignException.WAYBILL_LOCKED);//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		// 运单号
		String waybillNum = dto.getSignRfcEntity().getWaybillNo();
		// 创建签收结果对象
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置运单号
		entity.setWaybillNo(waybillNum);
		// 状态
		entity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNum);
		//判断是否为快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),waybill.getBillTime())
				&&!(waybill == null)&&!(resultEntity == null)){
			//判断是否为理赔状态和是否为异常签收
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNum);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveReverseSignDedicatedInfo",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNum);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				if(getClaimInfo(waybillNum)&&!SignConstants.NORMAL_SIGN.
						equalsIgnoreCase(resultEntity.getSignSituation())){
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException("已申请理赔，无法更改签收状态");
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO
			}
		}
		try {
			
			// 选中付款ID
			String repaymentIds = dto.getRepaymentArrivesheetDto()
					.getRepaymentIds();
			// 选中的到达联ID
			String arrivesheetIds = dto.getRepaymentArrivesheetDto()
					.getArriveSheetIds();
			// 签收变更
			SignRfcEntity record = dto.getSignRfcEntity();
			// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
			record.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
			// 反签收
			record.setRfcType(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED);
			// 签收变更赋值
			setSignRfcInfo(currentInfo, record);
			
			if (isRfc(record)) {
				// 已提交申请
				throw new SignException(SignException.IS_NOT_APPROVAL);
			}			
			// 保存签收变更
			signRfcDao.insertSelective(record);
			//反签收附件保存
			saveAttachment(dto.getReverseSignRfcFiles(),record.getId());
			// 反签收明细
			ReverseSignDetailEntity reverseSignDetailEntity;
			
			//查询子母件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(SignConstants.WAYBILL_NO,record.getWaybillNo());
			params.put(SignConstants.ACTIVE,FossConstants.YES);
			TwoInOneWaybillDto twoInOneDto = waybillRelateDetailEntityService.
					queryWaybillRelateByWaybillOrOrderNo(params);
			// 是否有付款反签收
			if (StringUtils.isNotEmpty(repaymentIds)) {
				try{
					//判断是否子母件（若是母件，再判断子件是否全部反结清）
					this.checkTwoInOneRepayment(twoInOneDto,record.getWaybillNo());
				}catch(SignException e){
					// 已提交申请
					throw new SignException(SignException.SON_NOT_REPAYMENT);
				}
				//判断运单代收货款是否已付款。若已付代收货款，则不允许提交申请
				checkWaybillRepayment(record.getWaybillNo());
				//获得需要反结清的数据集
				String[] repaymentArray = repaymentIds.split(",");
				RepaymentEntity repayment;
				for (String id : repaymentArray) {
					reverseSignDetailEntity = new ReverseSignDetailEntity();
					// 签收变更ID
					reverseSignDetailEntity.settSrvSignRfcId(record.getId());
					// 付款ID
					reverseSignDetailEntity.setMappingId(id);
					// 类型
					reverseSignDetailEntity
							.setType(SignConstants.REVERSE_SIGN_TYPE_REPAYMENT);
					// 保存反签收明细
					reverseSignDetailDao.insertSelective(reverseSignDetailEntity);
					// 更改付款状态
					// 创建对象
					repayment = new RepaymentEntity();
					// id
					repayment.setId(id);
					//签收中
					repayment.setIsRfcing(FossConstants.YES);
					//更新付款信息
					repaymentDao.updateRepayment(repayment);
				}
			}
			// 到达联反签收
			if (StringUtils.isNotEmpty(arrivesheetIds)) {
				//校验子母件确认收入
				if(checkTwoInOneReverseConfirmIncome(twoInOneDto,record.getWaybillNo(),false)){
					// 校验反签收服务
					validReverseConfirmIncome(record, currentInfo, SettlementConstants.LINE_SIGN);
				}
				//获得反签收到达联数组
				String[] arrivesheetArray = arrivesheetIds.split(",");
				//校验是否所有的货物都被返回
				checkArrivesheetQty(dto,arrivesheetArray);
				//定义到达联实体
				ArriveSheetEntity arriveSheet;
				//遍历到达联
				for (String id : arrivesheetArray) {
					//反签收详细实体
					reverseSignDetailEntity = new ReverseSignDetailEntity();
					// 签收变更ID
					reverseSignDetailEntity.settSrvSignRfcId(record.getId());
					// 到达联ID
					reverseSignDetailEntity.setMappingId(id);
					// 类型
					reverseSignDetailEntity
							.setType(SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET);
					// 保存反签收明细
					reverseSignDetailDao.insertSelective(reverseSignDetailEntity);
					// 更新到达联状态
					// 创建对象
					arriveSheet = new ArriveSheetEntity();
					// id
					arriveSheet.setId(id);
					//设置为审批中
					arriveSheet.setIsRfcing(FossConstants.YES);
					//更新到达联状态
					arrivesheetDao.updateByPrimaryKeySelective(arriveSheet);
				}
				this.createBatchInstationMsg(record.getWaybillNo(),currentInfo);
				
				try{
					//专线OA差错上报
					String waybillNo=record.getWaybillNo();
					this.reverseErrorReport(waybillNo,currentInfo);
				}catch(QmsErrorException e){
					LOGGER.info("运单号：" + record.getWaybillNo() + "上报QMS反签收差错失败");
					// 已提交申请
					throw new SignException(e.getErrorCode());
				}
			}
		} catch (BusinessException e) {
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(e.getErrorCode());
		}
		businessLockService.unlock(mutexElement);//解锁
		//日志信息
		LOGGER.info("saveReverseSignInfo end......");
	}

	/**
	 * 描述：获取OA差错信息
	 * 作者：pgy
	 * @param waybill
	 * @param currentInfo
	 * @returnOaErrorReportDto
	 */
	public OaErrorReportDto getAaErrorReportDto(WaybillEntity waybill, CurrentInfo currentInfo){
		OaErrorReportDto oaDto = new OaErrorReportDto();
		//运单号
		oaDto.setWaybillNo(waybill.getWaybillNo());
		//运输类型
		oaDto.setTransportType(waybill.getTransportType());
		//返单类型
		oaDto.setReturnBillType(waybill.getReturnBillType());
		//托运人（发货联系人）
		oaDto.setDeliveryCustomerContact(waybill.getDeliveryCustomerContact()) ;
		//运输性质
		oaDto.setProductCode(waybill.getProductCode());
		//收货人电话
		oaDto.setReceiveCustomerPhones(waybill.getReceiveCustomerPhone());
		//提货方式
		oaDto.setReceiveMethod(waybill.getReceiveMethod());
		//储运事项
		oaDto.setTransportationRemark(waybill.getTransportationRemark());
		//货物重量
		oaDto.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
		//货物体积
		oaDto.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
		//件数
		oaDto.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
		//货物名称
		oaDto.setGoodsName(waybill.getGoodsName());
		//发货时间
		oaDto.setCreateTime(waybill.getCreateTime());
		//目的站
		oaDto.setTargetOrgCode(waybill.getTargetOrgCode());
		//收货人(收货联系人)
		oaDto.setReceiveCustomerContact(waybill.getReceiveCustomerContact());
		//收货部门
		oaDto.setReceiveOrgName(waybill.getReceiveOrgName());
		//标杆编码
		OrgAdministrativeInfoEntity org= orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybill.getCreateOrgCode());
		if(org!=null){
			oaDto.setUnifiedCode(org.getUnifiedCode());
		}
		//付款方式		
		oaDto.setPaidMethod(waybill.getPaidMethod());
		//保险金额
		oaDto.setInsuranceAmount(waybill.getInsuranceAmount());
		//货物包装
		oaDto.setGoodsPackage(waybill.getGoodsPackage());
		//运费总额
		oaDto.setTotalFee(waybill.getTotalFee());
		//事件经过(空值)
		oaDto.setChangeItmes("");
		//附件(空值)
		oaDto.setAccessory("");
		//责任部门
		oaDto.setResponsibilityDeptName(currentInfo.getCurrentDeptName());
		//查询责任事业部
		List<String> orgTypeLst = new ArrayList<String>();
		orgTypeLst.add(BizTypeConstants.ORG_DIVISION);
		OrgAdministrativeInfoEntity orgEntity =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode(),orgTypeLst);
		if(orgEntity!=null){
			oaDto.setBusDepartmentName(orgEntity.getName());
		}else {
			//查询大区
			List<String> orgBigRegionTypeLst = new ArrayList<String>();
			orgBigRegionTypeLst.add(BizTypeConstants.ORG_BIG_REGION);
			OrgAdministrativeInfoEntity orgBigEntity =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode(),orgBigRegionTypeLst);
			if(orgBigEntity!= null){
				oaDto.setBusDepartmentName(orgBigEntity.getName());
			}
		}
		//操作员
		oaDto.setUserId(currentInfo.getEmpCode());
		//职位
		oaDto.setTitle(currentInfo.getUser().getTitle());
		return oaDto;
	}
	/**
	 * 对代收货款大于0的，做反签收\变更签收结果 的进行添加站内消息
	 *  @author 159231-foss-meiying
	 * @date 2014-1-17 上午10:15:40
	 */
	private void createBatchInstationMsg(String waybillNo,CurrentInfo currentInfo){
		//获得运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybillEntity!=null && waybillEntity.getCodAmount() !=null){
			if(waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO)==1 ){
				String context="单号"+waybillNo+"含代收货款，存在未受理的更改单，请联系负责人及时审批";
				InstationJobMsgEntity entiy = new InstationJobMsgEntity();
				//发送人和发送部门信息
				entiy.setSenderCode(currentInfo.getEmpCode());
				entiy.setSenderName(currentInfo.getEmpName());
				entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
				entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
				//设置为代收货款消息
				entiy.setMsgType(MessageConstants.MSG_TYPE__COLLECTION);						
				//接受方式为组织
				entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
				//设置接收部门信息
				entiy.setReceiveOrgCode(currentInfo.getCurrentDeptCode());
				entiy.setReceiveOrgName(currentInfo.getCurrentDeptName());
				//设置
				entiy.setContext(context);
				messageService.createBatchInstationMsg(entiy);
			}
		}
	}
	/**
	 * 
	 * 校验反签收服务
	 * @param record
	 * @param currentInfo
	 * @author ibm-wangfei
	 * @date May 13, 2013 7:04:39 PM
	 */
	private void validReverseConfirmIncome(SignRfcEntity record, CurrentInfo currentInfo, String signType) {
		LineSignDto lineSignDto = new LineSignDto();
		//获得运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(record.getWaybillNo());
		if (waybillEntity == null) {
			// 运单不存在
			throw new SignException("运单不存在");
		}
		// 运单号
		lineSignDto.setWaybillNo(record.getWaybillNo());
		// 运输性质
		lineSignDto.setProductType(waybillEntity.getProductCode());
		// 反签收部门编码
		lineSignDto.setSignOrgCode(currentInfo.getCurrentDeptCode());
		// 反签收部门名称
		lineSignDto.setSignOrgName(currentInfo.getCurrentDeptName());
		// 汽运偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())) {
			lineSignDto.setSignType(SettlementConstants.PARTIAL_LINE_SIGN);//签收类型为汽运偏线
		} else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())) {
			// 签收类型 -----空运签收
			lineSignDto.setSignType(SettlementConstants.AIR_SIGN);
		} else if(SettlementConstants.LAND_STOWAGE_SIGN.equals(signType)){
			// 签收类型 -----快递代理
			lineSignDto.setSignType(SettlementConstants.LAND_STOWAGE_SIGN);
		} else {
			// 签收类型 -----专线签收
			lineSignDto.setSignType(SettlementConstants.LINE_SIGN);
		}
		// 业务日期                           
		lineSignDto.setSignDate(new Date());
		// 校验反签收服务
		try {
			String vestSystemCode2 = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(record.getWaybillNo());
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validReverseConfirmIncome",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode2 = list.get(0).getVestSystemCode();
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+ record.getWaybillNo());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
				takingService.validReverseConfirmIncome(lineSignDto, currentInfo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
				//TODO  此处CUBC签收里自己做校验
			}
			//校验反签收服务---------灰度----------end---------------356354
		} catch (SettlementException e) {
			throw new SignException(e.getErrorCode());
		}
	}
	
	private void setSignRfcInfo(CurrentInfo currentInfo, SignRfcEntity record) {
		// 获得申请编号
		record.setRfcNo(getRecNoInfo(record.getWaybillNo()));
		// 起草部门
		record.setDraftOrgName(currentInfo.getCurrentDeptName());
		// 起草部门编码
		record.setDraftOrgCode(currentInfo.getCurrentDeptCode());
		// 起草人
		record.setDrafter(currentInfo.getEmpName());
		// 起草人编号
		record.setDrafterCode(currentInfo.getEmpCode());
		// 起草时间
		record.setDraftTime(new Date());
	}

	private void setSignAuditInfo(CurrentInfo currentInfo, SignRfcEntity record) {
		// 操作人
		record.setOperator(currentInfo.getEmpName());
		// 操作人编码
		record.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门
		record.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作人部门编码
		record.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		// 操作时间
		record.setOperateTime(new Date());
		// 设置原有状态为处理中
		record.setOldStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
	}

	public String getRecNoInfo(String waybillNo) {
		StringBuffer recNo = new StringBuffer(waybillNo);
		// 填充4为数
		StringBuffer sno = new StringBuffer(SignConstants.SIGN_RFC_SIGN_NO);
		// 获得序列号
		String no = SequenceUtil.getNextId(waybillNo);
		// 链接序列号
		String newno = sno.append(no).toString();
		//获得流水号数据
		return recNo.append(
				newno.substring(newno.length() - SignConstants.SIGN_RFC_SEQ_NO,
						newno.length())).toString();
	}

	@Override
	public List<SignRfcEntity> searchSignRfcList(SignRfcEntity entity,
			int start, int limit,CurrentInfo currentInfo) {
		//运单号查询具有排他性
		if(StringUtils.isNotEmpty(entity.getWaybillNo())){
			SignRfcEntity entitybyWaybillNo = new SignRfcEntity();
			//单号查询具有排他性
			entitybyWaybillNo.setWaybillNo(entity.getWaybillNo());
			//设置创建日信息
			entitybyWaybillNo.setCreateUser(currentInfo.getEmpCode());
			//设置查询条件
			entitybyWaybillNo.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
			//返单结果数据集大小
			return signRfcDao.querySignRfcList(entitybyWaybillNo, start, limit);
		}
		//设置创建日信息
		entity.setCreateUser(currentInfo.getEmpCode());
		//查询变更数据
		return signRfcDao.querySignRfcList(entity, start, limit);
	}
	
	@Override
	public List<SignRfcEntity> searchSignRfcListForView(SignRfcEntity entity,
			int start, int limit,CurrentInfo currentInfo) {
		//设置创建日信息
		entity.setCreateUser(currentInfo.getEmpCode());
		//查询变更数据
		return signRfcDao.querySignRfcList(entity, start, limit);
	}

	@Override
	public Long getSignRfcCount(SignRfcEntity entity,CurrentInfo currentInfo) {
		//运单号查询具有排他性
		if(StringUtils.isNotEmpty(entity.getWaybillNo())){
			SignRfcEntity entitybyWaybillNo = new SignRfcEntity();
			//单号查询具有排他性
			entitybyWaybillNo.setWaybillNo(entity.getWaybillNo());
			//返单结果数据集大小
			return signRfcDao.getTotalCount(entitybyWaybillNo);
		}
		//设置创建人信息
		entity.setCreateUser(currentInfo.getEmpCode());
		return signRfcDao.getTotalCount(entity);
	}
	@Override
	public Long getSignRfcCountView(SignRfcEntity entity,CurrentInfo currentInfo) {
		//设置创建人信息
		entity.setCreateUser(currentInfo.getEmpCode());
		return signRfcDao.getTotalCount(entity);
	}

	@Override
	public SignResultDto getSignDetail(SignResultDto dto) {
		LOGGER.info("getSignDetail begin......");
		// 获得变更申请对象
		SignRfcEntity einfo = signRfcDao.selectById(dto.getSignRfcEntity()
				.getId());
		//获得文件附件列表
		List<AttachementEntity> attachementEntityList = searchAttachmentById(dto.getSignRfcEntity().getId());

		// 变更明细查询条件
		SignRfcChangeDetailEntity srcdeCondition = new SignRfcChangeDetailEntity();
		// 变更明细
		List<SignRfcChangeDetailEntity> reList = null;
		// 变更明细DTO
		ChangeDto changeDto = new ChangeDto();
		// 反签收数据
		RepaymentArrivesheetDto repaymentArrivesheetDto = new RepaymentArrivesheetDto();
		// 运单签收结果
		if (SignConstants.SIGN_RFC_TYPE_WAYBILL.equals(einfo.getRfcType())) {
			// 运单签收结果数据
			WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService
					.queryWaybillSignResultById(einfo
							.gettSrvWaybillSignResultId());
			// id
			srcdeCondition.settSrvSignRfcId(einfo.getId());
			srcdeCondition
					.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT);
			// 变更明细
			reList = signRfcChangeDetailDao.selectList(srcdeCondition);
			dto.setChangeDetailentity(reList);
			// 设置变更明细
			// 运单签收结果信息
			changeDto.setWaybillSignResultEntity(waybillSignResultEntity);
			//空运偏线附件列表
			dto.setWaybillSignResultFiles(attachementEntityList);
			// 付款
		} else if (SignConstants.SIGN_RFC_TYPE_REPAYMENT.equals(einfo
				.getRfcType())) {
			// 付款实体
			RepaymentEntity repaymentEntity = repaymentDao
					.searchRepaymentById(einfo.gettSrvRepaymentId());
			if (repaymentEntity != null && StringUtils.isNotEmpty(repaymentEntity.getConsigneeCode())) {
				CustomerDto customerDto = customerService.queryCustomerDtoByCustCode(repaymentEntity.getConsigneeCode());
				repaymentEntity.setConsigneeName(customerDto == null ? "" : customerDto.getName());
			}
			// id
			srcdeCondition.settSrvSignRfcId(einfo.getId());
			srcdeCondition
					.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_REPAYMENT);
			// 变更明细
			reList = signRfcChangeDetailDao.selectList(srcdeCondition);
			dto.setChangeDetailentity(reList);
			setCustomerInfo(reList);
			// 设置变更明细
			// 付款信息
			changeDto.setRepaymentEntity(repaymentEntity);
			//付款附件列表
			dto.setRepaymentFiles(attachementEntityList);
			// 到达联
		} else if (SignConstants.SIGN_RFC_TYPE_ARRIVESHEET.equals(einfo
				.getRfcType())) {
			// 创建对象
			ArriveSheetEntity conditionEntity = new ArriveSheetEntity();
			//设置到达联ID
			conditionEntity.setId(einfo.gettSrvArrivesheetId());
			//获得到达联实体信息
			ArriveSheetEntity arriveSheetEntity = arrivesheetDao
					.queryArriveSheetByEntity(conditionEntity);
			// id
			srcdeCondition.settSrvSignRfcId(einfo.getId());
			//设置到达联类型
			srcdeCondition
					.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET);
			// 变更明细(包括到达联和货签表)
			reList = signRfcChangeDetailDao.selectList1(srcdeCondition);  
			//设置改变字段的集合
			dto.setChangeDetailentity(reList);
			// 设置变更明细
			// 到达联信息
			changeDto.setArriveSheetEntity(arriveSheetEntity);
			//到达联附件列表
			dto.setArrivesheetFiles(attachementEntityList);
			// 反签收
		} else if(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED.equals(einfo
				.getRfcType())){
			// 运单信息
			WaybillEntity waybillEntity = waybillManagerService
					.queryWaybillBasicByNo(einfo.getWaybillNo());
			// 付款LIST
			List<RepaymentEntity> repaymentEntityList = new ArrayList<RepaymentEntity>();
			// 到达联LIST
			List<ArriveSheetEntity> arriveSheetEntityList = new ArrayList<ArriveSheetEntity>();
			// 到达联查询条件实体
			ArriveSheetEntity conditionEntity = new ArriveSheetEntity();
			// 获得反签收明细
			List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao
					.searchReverseSignDetailList(einfo.getId());
			//集合不为空
			if (CollectionUtils.isNotEmpty(rsdeList)) {
				//遍历集合
				for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
					//反签收类型为付款
					if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT
							.equals(reverseSignDetailEntity.getType())) {
						// id
						repaymentEntityList.add(repaymentDao
								.searchRepaymentById(reverseSignDetailEntity.getMappingId()));
						//反签收类型为到达联
					} else if(SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET
							.equals(reverseSignDetailEntity.getType())){
						// id
						conditionEntity.setId(reverseSignDetailEntity.getMappingId());
						// add to save list
						arriveSheetEntityList.add(arrivesheetDao
								.queryArriveSheetByEntity(conditionEntity));
					}
				}
			}
			// 返回到达联
			repaymentArrivesheetDto
					.setArriveSheetEntityList(arriveSheetEntityList);
			// 返回付款
			repaymentArrivesheetDto.setRepaymentEntityList(repaymentEntityList);
			if(waybillEntity != null){
				//把CODE改为名称
				waybillEntity.setReceiveOrgCode(getNameByCode(waybillEntity.getReceiveOrgCode()));
				//到达部门CODE改为名称
				waybillEntity.setLastLoadOrgCode(getNameByCode(waybillEntity.getLastLoadOrgCode()));
			}
			// 返回运单信息
			repaymentArrivesheetDto.setWaybillEntity(waybillEntity);
			dto.setRepaymentArrivesheetDto(repaymentArrivesheetDto);
			//反签收附件列表
			dto.setReverseSignRfcFiles(attachementEntityList);
		//空运偏线反签收
		}else{
			//运单签收结果信息
			WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService
								.queryWaybillSignResultById(einfo
										.gettSrvWaybillSignResultId());
			//运单签收结果信息
			repaymentArrivesheetDto.setWaybillSignResultEntity(waybillSignResultEntity);
			//设置反签收信息
			dto.setRepaymentArrivesheetDto(repaymentArrivesheetDto);
			//反签收附件列表
			dto.setReverseSignRfcFiles(attachementEntityList);
		}
		//变更明细
		dto.setChangeDto(changeDto);
		//日志信息
		LOGGER.info("getSignDetail end......");
		//返回页面显示DTO
		return dto;
	}
	
	private void setCustomerInfo(List<SignRfcChangeDetailEntity> reList) {
		if (CollectionUtils.isEmpty(reList)) {
			return;
		}
		for (SignRfcChangeDetailEntity detail : reList) {
			// 如果是客户变更
			if (StringUtils.equals(SignConstants.CONSIGNEE_CODE, detail.getRfcItemsCode())) {
				if (StringUtils.isNotEmpty(detail.getBeforeRfcinfo())) {
					CustomerDto customerDto = customerService.queryCustomerDtoByCustCode(detail.getBeforeRfcinfo());
					detail.setBeforeRfcinfo(customerDto == null ? "" : customerDto.getName());
				}
				if (StringUtils.isNotEmpty(detail.getAfterRfcinfo())) {
					CustomerDto customerDto = customerService.queryCustomerDtoByCustCode(detail.getAfterRfcinfo());
					detail.setAfterRfcinfo(customerDto == null ? "" : customerDto.getName());
				}
			}
		}
	}

	@Override
	@Transactional
	public void agree(String id, String notes, CurrentInfo currentInfo) {
		LOGGER.info("agree begin......");
		// 获得申请数据
		SignRfcEntity entity = signRfcDao.selectById(id);
		// 运单号
		String waybillNum = entity.getWaybillNo();
		// 创建签收结果对象
		WaybillSignResultEntity waybillSignResultEntity = new WaybillSignResultEntity();
		//设置运单号
		waybillSignResultEntity.setWaybillNo(waybillNum);
		// 状态
		waybillSignResultEntity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntity);
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNum);
		//判断是否为快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),waybill.getBillTime())
				&&!(waybill == null)&&!(resultEntity == null)){
			
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNum);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveReverseSignDedicatedInfo",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNum);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				if(getClaimInfo(waybillNum)&&!SignConstants.NORMAL_SIGN.
						equalsIgnoreCase(resultEntity.getSignSituation())){
					throw new SignException("已申请理赔，无法更改签收状态");
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO
			}
		}
		
		List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao
				.searchReverseSignDetailList(id);
		// 到达联实体
		ArriveSheetEntity arriveSheetEntity;
		// 反签收中到达联信息
		List<ArriveSheetEntity> arriveSheetEntityList = new ArrayList<ArriveSheetEntity>();
		// 付款实体
		RepaymentEntity repaymentEntity;
		// 反签收中的付款信息
		List<RepaymentEntity> repaymentEntityList = new ArrayList<RepaymentEntity>();
		//
		for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
			if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT
					.equals(reverseSignDetailEntity.getType())) {
				repaymentEntity = repaymentDao
						.searchRepaymentById(reverseSignDetailEntity.getMappingId());
				repaymentEntity.setIsRfcing(FossConstants.NO);
				repaymentEntityList.add(repaymentEntity);
			} else if (SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET
					.equals(reverseSignDetailEntity.getType())){
				arriveSheetEntity = arrivesheetDao
						.queryArriveSheetById(reverseSignDetailEntity.getMappingId());
				arriveSheetEntity.setIsRfcing(FossConstants.NO);
				arriveSheetEntityList.add(arriveSheetEntity);
			}
		}
		
		// 更新变更签收数据
		entity.setNotes(notes);
		// 同意原因
		entity.setStatus(SignConstants.SIGN_RFC_SIGN_PASSED);
		//设置cubc关联ID
		entity.setCubcID(id);
		// 同意
		setSignAuditInfo(currentInfo, entity);
		// 更新明细数据
		updateData(entity, FossConstants.YES, currentInfo);
		//家装反签收 add zhuliangzhi 2015-12-22
		if(entity!=null&&StringUtil.isNotEmpty(entity.getWaybillNo())){
			LOGGER.info("家装运单反签收*开始----");
			//查询是否家装运单
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
			if(actual!=null){
				if(StringUtils.isNotEmpty(actual.getSpecialValueAddedServiceType())&&
						actual.getSpecialValueAddedServiceType().contains("_EQUIP")){//若家装运单进行反签收，给DOP发送信息
					LOGGER.info("家装运单："+entity.getWaybillNo()+" 反签收记录----");
					dopSignService.updateRfc(entity.getWaybillNo());//更新家装签收日志表里的反签收标识
					LOGGER.info("家装运单反签收发送给DOP信息----");
					dopSignService.sendDopRfcSignMessage(entity.getWaybillNo());//将反签收信息回调给dop
				}
			}
			LOGGER.info("家装运单反签收*完成----");
		}
		/**
		 * 反结清释放T+0明细，和T+0中的占用金额
		 * @author yangqiang
		 */
		//unSettlement(entity);
		/**
		 * @author 367638 caodajun
		 * FOSS同步反签收状态和运单号给ptp
		 */
		if(entity!=null&&StringUtil.isNotEmpty(entity.getWaybillNo())){
		LOGGER.info("同步反签收状态和与运单号给ptp*开始----");
		  PartnerUpdateTakeEffectTimeRequest signrequest=new PartnerUpdateTakeEffectTimeRequest();
		  signrequest.setSourceBillNo(entity.getWaybillNo());
		  signrequest.setSignDate(null);
	   	  signrequest.setSignType(2);
		  List<PartnerUpdateTakeEffectTimeRequest> request=new ArrayList<PartnerUpdateTakeEffectTimeRequest>();
		  request.add(signrequest);
		    // 向ptp分批推送签收时间和运单号
			if (!ptpSignPartnerService.validBillSaleFlowByAsynESB(request)) {
				throw new SignException("合伙人签收时间发送ESB失败!");
			}
		 LOGGER.info("同步反签收状态和与运单号给ptp*完成----");
	     }
		//日志信息
		LOGGER.info("agree end......");
	}

	@Override
	@Transactional
	public void refuse(String id, String notes, CurrentInfo currentInfo) {
		LOGGER.info("refuse begin......");
		// 签收变更申请元数据
		// 获得申请数据
		SignRfcEntity entity = signRfcDao.selectById(id);
		// 更新变更签收数据
		entity.setNotes(notes);
		// 拒绝原因
		entity.setStatus(SignConstants.SIGN_RFC_SIGN_REFUSED);
		// 拒绝
		setSignAuditInfo(currentInfo, entity);
		// 更新明细数据
		updateData(entity, FossConstants.NO, currentInfo);
		//日志信息
		LOGGER.info("refuse end......");
	}
	
	private void updateData(SignRfcEntity entity, String status,
			CurrentInfo currentInfo) {

		// entity不为空，并且专线里 含有付款变更 或 到达联变更 或 反签收
		if((entity != null) && (SignConstants.SIGN_RFC_TYPE_REPAYMENT.equals(entity.getRfcType())
				|| SignConstants.SIGN_RFC_TYPE_ARRIVESHEET.equals(entity.getRfcType())
				|| SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED.equals(entity.getRfcType()) )){
				
				String waybillNo = entity.getWaybillNo();
				if(StringUtils.isNotEmpty(waybillNo)){
					CodAuditDto dto = new CodAuditDto();
					List<String> waybillNoList = new ArrayList<String>();
					waybillNoList.add(waybillNo);
					dto.setWaybillNos(waybillNoList);
                    List<CodAuditEntity> codAuditEntityList = null;
                    String vestSystemCode2 = null;
                    try {
                    	ArrayList<String> arrayList = new ArrayList<String>();
                    	arrayList.add(waybillNo);
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode2 = list.get(0).getVestSystemCode();		
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"运单号："+ waybillNo);
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
                    if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
                        codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
                        if(codAuditEntityList != null && codAuditEntityList.size() > 0){
    						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
    						if(codAuditEntity != null){
    							//如果为资金部锁定
    							if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
    								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
    							}
    							if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
    								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
    							}
    							if(StringUtils.equalsIgnoreCase("SSL", codAuditEntity.getLockStatus())){
    								throw new SettlementException("此单据已被短期冻结，如需操作，请联系资金安全控制组进行解冻");
    							}
    							if(StringUtils.equalsIgnoreCase("SLL", codAuditEntity.getLockStatus())){
    								throw new SettlementException("此单据已被长期冻结，如需操作，请联系资金安全控制组!");
    							}
    						}
    					}else{
    						LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
    					}
                    }
                    if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
        					CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
        					requestDto.setWaybillNo(waybillNoList);
        					CUBCCodAuditResultDto resultDto = null;
							try {
								resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
							} catch (Exception e) {
								LOGGER.error("调用CUBC代收货款审核接口连接异常...");
	        					throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
							}
        					if(resultDto != null){
        						if(StringUtils.isNotBlank(resultDto.getMeg())){
        							LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
        							throw new SettlementException(resultDto.getMeg());	
        						}
        						List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
        						if(CollectionUtils.isNotEmpty(auditList)){
        							com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto = auditList.get(0);
        							if(codAuditDto != null){
        								//如果为资金部锁定
        								if(StringUtils.equalsIgnoreCase("FL", codAuditDto.getLockStatus())){
        									throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
        								}
        								if(StringUtils.equals("RL", codAuditDto.getLockStatus())){
        									throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
        								}
        								if(StringUtils.equalsIgnoreCase("SSL", codAuditDto.getLockStatus())){
        									throw new SettlementException("此单据已被短期冻结，如需操作，请联系资金安全控制组进行解冻");
        								}
        								if(StringUtils.equalsIgnoreCase("SLL", codAuditDto.getLockStatus())){
        									throw new SettlementException("此单据已被长期冻结，如需操作，请联系资金安全控制组!");
        								}
        							}
        						}else{
        							LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
        						}
        					}
                    }
		}
	}

		// 获得变更项数据条件实体
		SignRfcChangeDetailEntity srcde;
		// 获得变更项数据List
		List<SignRfcChangeDetailEntity> scdeList = null;
		
		MutexElement mutexElement = new MutexElement(entity.getId(), "变更ID", MutexElementType.ACCEPT_RFC);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error(SignException.WAYBILL_LOCKED);//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		//更新变更签收的状态
		int updateCount = signRfcDao.updateSelective(entity);
		if (updateCount == 0) {
			businessLockService.unlock(mutexElement);//解锁
			// 并发控制，没有更新到数据,当前运单已审核，请查询后重新处理
			throw new SignException(SignException.APPLICATION_SIGN_RFC_ACCEPTED); 
		}
		// 付款
		if (SignConstants.SIGN_RFC_TYPE_REPAYMENT.equals(entity.getRfcType())) {
			RepaymentEntity repayment;
			// 更新付款状态
			if (FossConstants.NO.equals(status)) {
				repayment = new RepaymentEntity();
				// ID
				repayment.setId(entity.gettSrvRepaymentId());
				// NO
				repayment.setIsRfcing(FossConstants.NO);
				// 更新
				repaymentDao.updateRepayment(repayment);
			} else {
				// 条件赋值
				srcde = new SignRfcChangeDetailEntity();
				// 对应变更项的变更申请ID
				srcde.settSrvSignRfcId(entity.getId());
				// 变更类型
				srcde.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_REPAYMENT);
				// 变更项LIST
				scdeList = signRfcChangeDetailDao.selectList(srcde);

				// 变更前的付款
				RepaymentEntity oldRepayment = repaymentDao
						.searchRepaymentById(entity.gettSrvRepaymentId());
				// 需要负责的付款对象
				repayment = new RepaymentEntity();

				// 把旧对象的值付给新对象
				BeanUtils.copyProperties(oldRepayment, repayment);

				// 获得需要修改对象
				repayment = (RepaymentEntity) updateApplyData(scdeList,
						repayment);
				try {
					//付款状态为不在审批中
					repayment.setIsRfcing(FossConstants.NO);
					// 调用付款接口
					repaymentService.changeRepayment(oldRepayment, repayment,
							currentInfo);
				} catch (RepaymentException ex) {
					businessLockService.unlock(mutexElement);//解锁
					//抛出异常
					throw new SignException(ex.getErrorCode(),ex);
				}
			}
			// 到达联
		} else if (SignConstants.SIGN_RFC_TYPE_ARRIVESHEET.equals(entity
				.getRfcType())) {
			// 更新到达联状态
			ArriveSheetEntity arriveSheet;
			// 状态NO
			if (FossConstants.NO.equals(status)) {
				// 创建对象
				arriveSheet = new ArriveSheetEntity();
				// ID
				arriveSheet.setId(entity.gettSrvArrivesheetId());
				// NO
				arriveSheet.setIsRfcing(FossConstants.NO);
				// 更新
				arrivesheetDao.updateByPrimaryKeySelective(arriveSheet);
			} else {
				// 条件赋值
				srcde = new SignRfcChangeDetailEntity();
				// 对应变更项的变更申请ID
				srcde.settSrvSignRfcId(entity.getId());
				// 变更类型
				srcde.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET);
				// 变更项LIST
				scdeList = signRfcChangeDetailDao.selectList(srcde);
				// 变更前的到达联
				ArriveSheetEntity oldarriveSheet = arrivesheetDao.queryArriveSheetById(entity.gettSrvArrivesheetId());
                //实例化到达联信息
				arriveSheet = new ArriveSheetEntity();
                //把查询信息复制到实例化的到达联对象中
				BeanUtils.copyProperties(oldarriveSheet, arriveSheet);
				// 调用到达联接口 //
				try {
					//设置到达联状态为不在审批中
					arriveSheet.setIsRfcing(FossConstants.NO);
					if(scdeList != null && scdeList.size()>0 && oldarriveSheet!=null){
						 //修改到达联的签收情况
						arriveSheet = (ArriveSheetEntity) updateApplyData(scdeList,arriveSheet); 
						//增加变更签收结果时，是否对保管费小票记录进行红冲的标志位
						boolean isRed =false;
						//根据ISSUE-3456变更签收结果由异常签收变成正常签收的需要添加财务的校验规则做修改
						for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : scdeList) {
							isRed = validaArriveSheetEntity(currentInfo,
									arriveSheet, oldarriveSheet, isRed,
									signRfcChangeDetailEntity);
						}	
						validaSignRfcEntity(entity, currentInfo, isRed);	
					}
						/**
						 * 以下代码处理货签表的
						 * */
						// 条件赋值
						srcde = new SignRfcChangeDetailEntity();
						// 对应变更项的变更申请ID
						srcde.settSrvSignRfcId(entity.getId());
						// 变更类型:货签表
						srcde.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_LABELTABLEFLG);
						// 变更项LIST
						scdeList = signRfcChangeDetailDao.selectList(srcde);
						if(scdeList!=null && scdeList.size()>0){
							for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : scdeList) {
									//更新签收明细表(T_SRV_SIGN_DETAIL),流水号对应的签收情况
									//1.由于签收变更明细表(t_srv_sign_rfc_changedetail)中的变更前信息和变更后信息的数据格式为:流水号.签收情况
								    //因此要拆解后获取签收情况,然后再存
									String afterInfoTmp= signRfcChangeDetailEntity.getAfterRfcinfo();
									String afterSerialNoInfo= afterInfoTmp.substring(0,afterInfoTmp.indexOf("."));
									String afterSituationInfo= afterInfoTmp.substring(afterInfoTmp.indexOf(".")+1);
									//2.更新签收明细表
									SignDetailEntity  signDetailEntity = new SignDetailEntity ();
									List<String> tmpList=new ArrayList<String>();
									tmpList.add(afterSerialNoInfo);
									signDetailEntity.setSerialNos(tmpList);
									signDetailEntity.setArrivesheetNo(arriveSheet.getArrivesheetNo());  //设置到达联编号
									signDetailEntity.setSituation(afterSituationInfo);
									signDetailService.updateByParams(signDetailEntity);
							}
						}
					// 调用付款接口
					arriveSheetManngerService.changeArriveSheet(oldarriveSheet,arriveSheet);
					//调用CUBC签收变更变更签收时间，修改人code，name等信息 ---------------灰度start
					String vestSystemCode2 = null;
	                try {
	                	ArrayList<String> arrayList = new ArrayList<String>();
	                	arrayList.add(oldarriveSheet.getWaybillNo());
	                	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	                			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
	                			SettlementConstants.TYPE_FOSS);
	                	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	                	List<VestBatchResult> list = response.getVestBatchResult();
	                	vestSystemCode2 = list.get(0).getVestSystemCode();
	    			} catch (Exception e) {
	    				LOGGER.info("灰度分流失败,"+"运单号："+ oldarriveSheet.getWaybillNo());
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	    			}
	                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
	                	//FOSS不做处理---原有逻辑
	                }
	                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
							CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
							requestDto.setSourceNo(oldarriveSheet.getWaybillNo());						//运单号
							requestDto.setRequestDate(arriveSheet.getSignTime());					//变更时间
							requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
							requestDto.setCustomerName(currentInfo.getCurrentDeptName());
							requestDto.setOperatorCode(currentInfo.getEmpCode());
							requestDto.setOperatorName(currentInfo.getEmpName());
							requestDto.setOperationTag(SignConstants.OPERATIONTAG_CZ);
							//签收情况不变
							requestDto.setOldSignSituation(oldarriveSheet.getSituation());			//变更前签收情况
							requestDto.setSignSituation(arriveSheet.getSituation());			//变更后签收情况
							//设置外发单号---外发签收
							List<String> list = externalBillService.getExternalBillNumListByWaybillNo(oldarriveSheet.getWaybillNo());
							if(!list.isEmpty()){
								requestDto.setExternalWaybillNos(list);
								LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
							}
							CUBCSignChangeResultDto resultDto1 = null;
							try {
								resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
							} catch (Exception e) {
								throw new SettlementException("服务器正忙,CUBC受理签收变更失败,请稍后重试");
							}
							if(resultDto1 != null){
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMeg())){
										LOGGER.error("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
										throw new SettlementException("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
									}else{
										throw new SettlementException("调用CUBC签收变更接口受理变更失败但未获取到异常信息");
									}
								}		
							}
	                }
				//捕获到异常
				} catch (BusinessException ex) {
					LOGGER.error("接口调用异常！", ex);
					businessLockService.unlock(mutexElement);//解锁
					//抛出异常
					throw new SignException(ex.getErrorCode(), ex);
				}
			}

			// 空运偏线
		} else if (SignConstants.SIGN_RFC_TYPE_WAYBILL.equals(entity
				.getRfcType())) {
			// 更新签收结果状态
			WaybillSignResultEntity waybillSignResultEntity;
			if (FossConstants.NO.equals(status)) {
				// 创建对象
				waybillSignResultEntity = new WaybillSignResultEntity();
				//设置运单签收结果的ID
				waybillSignResultEntity.setId(entity
						.gettSrvWaybillSignResultId());
				//该运单不在审批中
				waybillSignResultEntity.setIsRfcing(FossConstants.NO);
				// 更新数据
				waybillSignResultService.updateWaybillSignResultById(waybillSignResultEntity);
			} else {
				// 条件赋值
				srcde = new SignRfcChangeDetailEntity();
				// 对应变更项的变更申请ID
				srcde.settSrvSignRfcId(entity.getId());
				// 变更类型
				srcde.setRfcType(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT);
				// 变更项LIST
				scdeList = signRfcChangeDetailDao.selectList(srcde);
				// 变更前的签收结果
				WaybillSignResultEntity oldwaybillSignResultEntity = waybillSignResultService.queryWaybillSignResultById(entity.gettSrvWaybillSignResultId());
                //运单签收结果实体
				waybillSignResultEntity = new WaybillSignResultEntity();

				//复制信息
				BeanUtils.copyProperties(oldwaybillSignResultEntity,waybillSignResultEntity);

				// 获得需要修改对象
				waybillSignResultEntity = (WaybillSignResultEntity) updateApplyData(scdeList, waybillSignResultEntity);
				// 调用接口 //
				try {
					waybillSignResultEntity.setIsRfcing(FossConstants.NO);
					if(scdeList != null && scdeList.size()>0 && oldwaybillSignResultEntity !=null){
						//根据ISSUE-3456变更签收结果由异常签收变成正常签收的需要添加财务的校验规则做修改
						for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : scdeList) {
							if(SignConstants.SIGN_SITUATION.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
								if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
									//CUBC签收变更受理  理赔应付单校验   灰度改造    353654 ---------------------------start
									String vestSystemCode = null;
						            try {
						            	ArrayList<String> arrayList = new ArrayList<String>();
				                    	arrayList.add(oldwaybillSignResultEntity.getWaybillNo());
				                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
				                    			SettlementConstants.TYPE_FOSS);
				                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
						            	List<VestBatchResult> list = response.getVestBatchResult();
						            	vestSystemCode = list.get(0).getVestSystemCode();		
									} catch (Exception e) {
										LOGGER.info("灰度分流失败,"+"运单号："+oldwaybillSignResultEntity.getWaybillNo());
										throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
									}
									if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
										lineSignService.reverseToNormalSignal(oldwaybillSignResultEntity.getWaybillNo(),currentInfo);
									}
									if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
										CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
										requestDto.setSourceNo(oldwaybillSignResultEntity.getWaybillNo());
										requestDto.setRequestDate(new Date());
										requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
										requestDto.setCustomerName(currentInfo.getCurrentDeptName());
										requestDto.setOperatorCode(currentInfo.getEmpCode());
										requestDto.setOperatorName(currentInfo.getEmpName());
										requestDto.setOperationTag(SignConstants.OPERATIONTAG_SQ);
										//设置外发单号---外发签收
										List<String> list = externalBillService.getExternalBillNumListByWaybillNo(oldwaybillSignResultEntity.getWaybillNo());
										if(!list.isEmpty()){
											requestDto.setExternalWaybillNos(list);
											LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
										}
										CUBCSignChangeResultDto resultDto1 = null;
										try {
											resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
										} catch (Exception e) {
											throw new SettlementException("服务器正忙,CUBC申请签收变更失败,请稍后重试");
										}
										if(resultDto1 != null){
											if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
												if(StringUtils.isNotBlank(resultDto1.getMeg())){
													LOGGER.error("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
													throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
												}else{
													throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败但未获取到异常信息");
												}
											}		
										}
									}
									//CUBC签收变更受理  理赔应付单校验   灰度改造    353654 ---------------------------end
								}
							}
						}
					}
				
					// 调用签收变更接口
					waybillSignResultService.changeWaybillSignResult(oldwaybillSignResultEntity,waybillSignResultEntity);
					//调用CUBC签收变更变更签收时间，修改人code，name等信息 ---------------灰度start
					String vestSystemCode2 = null;
                    try {
                    	ArrayList<String> arrayList = new ArrayList<String>();
                    	arrayList.add(oldwaybillSignResultEntity.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode2 = list.get(0).getVestSystemCode();
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"运单号："+ oldwaybillSignResultEntity.getWaybillNo());
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
                    if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
                    	//FOSS不做处理---原有逻辑
                    }
                    if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
							CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
							requestDto.setSourceNo(oldwaybillSignResultEntity.getWaybillNo());					//运单号
							requestDto.setRequestDate(waybillSignResultEntity.getSignTime());					//变更时间
							requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
							requestDto.setCustomerName(currentInfo.getCurrentDeptName());
							requestDto.setOperatorCode(currentInfo.getEmpCode());
							requestDto.setOperatorName(currentInfo.getEmpName());
							requestDto.setOperationTag(SignConstants.OPERATIONTAG_CZ);
							requestDto.setOldSignSituation(oldwaybillSignResultEntity.getSignSituation());		//变更前签收情况
							requestDto.setSignSituation(waybillSignResultEntity.getSignSituation());			//变更后签收情况
							//设置外发单号---外发签收
							List<String> list = externalBillService.getExternalBillNumListByWaybillNo(oldwaybillSignResultEntity.getWaybillNo());
							if(!list.isEmpty()){
								requestDto.setExternalWaybillNos(list);
								LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
							}
							CUBCSignChangeResultDto resultDto1 = null;
							try {
								resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
							} catch (Exception e) {
								throw new SettlementException("服务器正忙,CUBC受理签收变更失败,请稍后重试");
							}
							if(resultDto1 != null){
								if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
									if(StringUtils.isNotBlank(resultDto1.getMeg())){
										LOGGER.error("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
										throw new SettlementException("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
									}else{
										throw new SettlementException("调用CUBC签收变更接口受理变更失败但未获取到异常信息");
									}
								}		
							}
                    }
                    //调用CUBC签收变更变更签收时间，修改人code，name等信息 ---------------灰度end
					//捕获异常
				} catch (BusinessException ex) {
					businessLockService.unlock(mutexElement);//解锁
					LOGGER.error("接口调用异常，请核对", ex);
					//抛出异常
					throw new SignException(ex.getErrorCode(), ex);
				}
			}

			// 反签收
		} else if(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED.equals(entity
				.getRfcType())){
			if(StringUtils.isNotBlank(entity.getCubcID())){
				currentInfo.setCubcID(entity.getCubcID());
			}
			List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao
					.searchReverseSignDetailList(entity.getId());
			if(CollectionUtils.isNotEmpty(rsdeList)){
				if (FossConstants.NO.equals(status)) {
					// 付款
					RepaymentEntity repayment;
					// 到达联
					ArriveSheetEntity arriveSheet;
					for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
						if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT
								.equals(reverseSignDetailEntity.getType())) {
							// 创建对象
							repayment = new RepaymentEntity();
							// ID
							repayment.setId(reverseSignDetailEntity.getMappingId());
							// NO
							repayment.setIsRfcing(FossConstants.NO);
							// 更新数据
							repaymentDao.updateRepayment(repayment);
						} else if (SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET
								.equals(reverseSignDetailEntity.getType())){
							// 创建对象
							arriveSheet = new ArriveSheetEntity();
							// ID
							arriveSheet.setId(reverseSignDetailEntity.getMappingId());
							// NO
							arriveSheet.setIsRfcing(FossConstants.NO);
							// 更新数据
							arrivesheetDao.updateByPrimaryKeySelective(arriveSheet);
						}
					}
					// 反签收同意
				} else {
					// 到达联实体
					ArriveSheetEntity arriveSheetEntity;
					// 反签收中到达联信息
					List<ArriveSheetEntity> arriveSheetEntityList = new ArrayList<ArriveSheetEntity>();
					// 付款实体
					RepaymentEntity repaymentEntity;
					// 反签收中的付款信息
					List<RepaymentEntity> repaymentEntityList = new ArrayList<RepaymentEntity>();
					//CUBC签收变更   灰度改造    353654 ---------------------------start
					String vestSystemCode1 = null;
		            try {
		            	ArrayList<String> arrayList = new ArrayList<String>();
                    	arrayList.add(entity.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		            	List<VestBatchResult> list = response.getVestBatchResult();
		            	vestSystemCode1 = list.get(0).getVestSystemCode();		
					} catch (Exception e) {
						LOGGER.info("灰度分流失败,"+"运单号："+entity.getWaybillNo());
		  				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
					}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
						//获取该运单对应的所有T+0明细    add  by  309603  yangqiang
						/**
						 * 根据单据号去查询明细数据，多个明细
						 * 
						 * 获取交易流水号和已使用流水号金额(明细中的数据)，
						 * 循环去释放交易流水号中的金额
						 */
						for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
							if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT.equals(reverseSignDetailEntity.getType())) {
								repaymentEntity = repaymentDao.searchRepaymentById(reverseSignDetailEntity.getMappingId());
								repaymentEntity.setIsRfcing(FossConstants.NO);
								repaymentEntityList.add(repaymentEntity);
								//如果是银行卡，统计要反结清的金额
								if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())){
									PosCardDetailEntity query = new PosCardDetailEntity();
									query.setInvoiceNo(entity.getWaybillNo());
									query.setInvoiceType("W2");
									List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(query);	
									BigDecimal	total = repaymentEntity.getActualFreight()==null?BigDecimal.ZERO:repaymentEntity.getActualFreight()
											.add(repaymentEntity.getCodAmount()==null?BigDecimal.ZERO:repaymentEntity.getCodAmount());
								if (lists!=null&&lists.size()>0)
									validaRepaymentEntity(currentInfo,
											repaymentEntity, lists, total);
							  }
							} else if (SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET.equals(reverseSignDetailEntity.getType())){
								arriveSheetEntity = arrivesheetDao.queryArriveSheetById(reverseSignDetailEntity.getMappingId());
								arriveSheetEntity.setIsRfcing(FossConstants.NO);
								arriveSheetEntityList.add(arriveSheetEntity);
							}
						}
						
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
						/**
						 * 根据单据号去查询明细数据，多个明细
						 * 
						 * 获取交易流水号和已使用流水号金额(明细中的数据)，
						 * 循环去释放交易流水号中的金额
						 */
						for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
							if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT.equals(reverseSignDetailEntity.getType())) {
								repaymentEntity = repaymentDao.searchRepaymentById(reverseSignDetailEntity.getMappingId());
								repaymentEntity.setIsRfcing(FossConstants.NO);
								repaymentEntityList.add(repaymentEntity);
//								//如果是银行卡，统计要反结清的金额
//								if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity.getPaymentType())){
//									PosCardDetailEntity query = new PosCardDetailEntity();
//									query.setInvoiceNo(entity.getWaybillNo());
//									query.setInvoiceType("W2");
//									List<PosCardDetailEntity> lists = pdaPosManageDao.queryDetailsByNo(query);	
//									BigDecimal	total = repaymentEntity.getActualFreight()==null?BigDecimal.ZERO:repaymentEntity.getActualFreight()
//											.add(repaymentEntity.getCodAmount()==null?BigDecimal.ZERO:repaymentEntity.getCodAmount());
//								if (lists!=null&&lists.size()>0)
//									validaRepaymentEntity(currentInfo,
//											repaymentEntity, lists, total);
//							  }
							} else if (SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET.equals(reverseSignDetailEntity.getType())){
								arriveSheetEntity = arrivesheetDao.queryArriveSheetById(reverseSignDetailEntity.getMappingId());
								arriveSheetEntity.setIsRfcing(FossConstants.NO);
								arriveSheetEntityList.add(arriveSheetEntity);
							}
						}
					}
					//CUBC签收变更   灰度改造    353654 ---------------------------end
					
					// 调用反到达联接口信息 
					if(CollectionUtils.isNotEmpty(arriveSheetEntityList)){
					try {
						// 调用付款接口
						arriveSheetManngerService.reverseArriveSheet(
								arriveSheetEntityList, currentInfo);
					} catch (ArriveSheetMannerException ex) {
						businessLockService.unlock(mutexElement);//解锁
						LOGGER.error("接口调用异常", ex);
						throw new SignException(ex.getErrorCode(), ex);
					}
					}
					// 调用反结清接口信息
					if(CollectionUtils.isNotEmpty(repaymentEntityList)){
						try {
							repaymentService.reverseRepayment(repaymentEntityList,currentInfo);
						} catch (RepaymentException ex) {
							businessLockService.unlock(mutexElement);//解锁
							LOGGER.error("接口调用异常", ex);
							throw new SignException(ex.getErrorCode(), ex);
							
						}
					}
					//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，反签收推送轨迹--add by 231438 快递100轨迹推送 
					//专线反签收 同意后推送轨迹
					sendWaybillTrackService.packagingMethodForSignRfc(entity, currentInfo);
				}
			}
		} else{
			List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao.searchReverseSignDetailList(entity.getId());
			// 更新签收结果状态
			WaybillSignResultEntity waybillSignResultEntity;
			//反签收详细实体
			ReverseSignDetailEntity reverseSignDetailEntity = rsdeList.get(0);
			//拒绝
			if (FossConstants.NO.equals(status)) {
				// 创建对象
				waybillSignResultEntity = new WaybillSignResultEntity();
				waybillSignResultEntity.setId(reverseSignDetailEntity.getMappingId());
				waybillSignResultEntity.setIsRfcing(FossConstants.NO);
				// 更新数据
				waybillSignResultService.updateWaybillSignResultById(waybillSignResultEntity);
				//调用CUBC签收变更变更签收时间，修改人code，name等信息 ---------------灰度start
				String vestSystemCode2 = null;
                try {
                	ArrayList<String> arrayList = new ArrayList<String>();
                	arrayList.add(waybillSignResultEntity.getWaybillNo());
                	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".updateData",
                			SettlementConstants.TYPE_FOSS);
                	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                	List<VestBatchResult> list = response.getVestBatchResult();
                	vestSystemCode2 = list.get(0).getVestSystemCode();
    			} catch (Exception e) {
    				LOGGER.info("灰度分流失败,"+"运单号："+ waybillSignResultEntity.getWaybillNo());
    				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
    			}
                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode2)){
                	//FOSS不做处理---原有逻辑
                }
                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode2)){
						CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
						requestDto.setSourceNo(waybillSignResultEntity.getWaybillNo());						//运单号
						requestDto.setRequestDate(waybillSignResultEntity.getSignTime());					//变更时间
						requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
						requestDto.setCustomerName(currentInfo.getCurrentDeptName());
						requestDto.setOperatorCode(currentInfo.getEmpCode());
						requestDto.setOperatorName(currentInfo.getEmpName());
						requestDto.setOperationTag(SignConstants.OPERATIONTAG_CZ);
						//签收情况不变
						requestDto.setOldSignSituation(waybillSignResultEntity.getSignSituation());			//变更前签收情况
						requestDto.setSignSituation(waybillSignResultEntity.getSignSituation());			//变更后签收情况
						//设置外发单号---外发签收
						List<String> list = externalBillService.getExternalBillNumListByWaybillNo(waybillSignResultEntity.getWaybillNo());
						if(!list.isEmpty()){
							requestDto.setExternalWaybillNos(list);
							LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
						}
						CUBCSignChangeResultDto resultDto1 = null;
						try {
							resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
						} catch (Exception e) {
							throw new SettlementException("服务器正忙,CUBC受理签收变更失败,请稍后重试");
						}
						if(resultDto1 != null){
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMeg())){
									LOGGER.error("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
									throw new SettlementException("调用CUBC签收变更接口受理变更失败,异常信息为：" + resultDto1.getMeg());
								}else{
									throw new SettlementException("调用CUBC签收变更接口受理变更失败但未获取到异常信息");
								}
							}		
						}
                }
                //调用CUBC签收变更变更签收时间，修改人code，name等信息 ---------------灰度end
			//同意
			}else{
				try {
					//反签收空运偏线同意后的操作
					airAgencySignService.reverseWaybillSignResult(reverseSignDetailEntity.getMappingId(), currentInfo);
					//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，反签收推送轨迹--add by 231438 快递100轨迹推送
					//反签收 空运偏线同意后推送轨迹
					sendWaybillTrackService.packagingMethodForSignRfc(entity, currentInfo);
				} catch (AirAgencySignException ex) {
					businessLockService.unlock(mutexElement);//解锁
					LOGGER.error("接口调用异常", ex);
					throw new SignException(ex.getErrorCode(), ex);
				}
			}
		}
		businessLockService.unlock(mutexElement);//解锁
	}
	
	private boolean validaArriveSheetEntity(CurrentInfo currentInfo,
			ArriveSheetEntity arriveSheet, ArriveSheetEntity oldarriveSheet,
			boolean isRed, SignRfcChangeDetailEntity signRfcChangeDetailEntity) {
		if(SignConstants.SITUATION.equals(signRfcChangeDetailEntity.getRfcItemsCode())){
			if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getAfterRfcinfo())){
				//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------start
				String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
	            	arrayList.add(oldarriveSheet.getWaybillNo());
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaArriveSheetEntity",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();		
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+oldarriveSheet.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					lineSignService.reverseToNormalSignal(oldarriveSheet.getWaybillNo(),currentInfo);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					CUBCSignChangeRequestDto requestDto = new CUBCSignChangeRequestDto();
					requestDto.setSourceNo(oldarriveSheet.getWaybillNo());
					requestDto.setRequestDate(new Date());
					requestDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
					requestDto.setCustomerName(currentInfo.getCurrentDeptName());
					requestDto.setOperatorCode(currentInfo.getEmpCode());
					requestDto.setOperatorName(currentInfo.getEmpName());
					requestDto.setOperationTag(SignConstants.OPERATIONTAG_SQ);
					//设置外发单号---外发签收
					List<String> list = externalBillService.getExternalBillNumListByWaybillNo(oldarriveSheet.getWaybillNo());
					if(!list.isEmpty()){
						requestDto.setExternalWaybillNos(list);
						LOGGER.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
					}
					CUBCSignChangeResultDto resultDto1 = null;
					try {
						resultDto1 = cUBCSignChangeService.changeRepayment(requestDto);
					} catch (Exception e) {
						throw new SettlementException("服务器正忙,CUBC申请签收变更失败,请稍后重试");
					}
					if(resultDto1 != null){
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
								throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败,异常信息为：" + resultDto1.getMeg());
							}else{
								throw new SettlementException("调用CUBC签收变更接口申请校验理赔失败但未获取到异常信息");
							}
						}		
					}	
				}
				//CUBC签收变更申请   理赔应付单校验   灰度改造    353654 ---------------------------end	 
				
				//如果变更签收结果为正常签收时,根据到达联编号查询签收明细表(T_SRV_SIGN_DETAIL),并更新其签收情况为"正常签收"
				SignDetailEntity  signDetailEntity = new SignDetailEntity ();
				signDetailEntity.setArrivesheetNo(arriveSheet.getArrivesheetNo());  //设置到达联编号
				signDetailEntity.setSituation(ArriveSheetConstants.SITUATION_NORMAL_SIGN);
				signDetailService.updateByParams(signDetailEntity);
			}
		 if(!isRed&&(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(signRfcChangeDetailEntity.getBeforeRfcinfo())
				 ||ArriveSheetConstants.SITUATION_PARTIAL_SIGN.equals(signRfcChangeDetailEntity.getBeforeRfcinfo()))
				 &&signRfcChangeDetailEntity.getAfterRfcinfo().indexOf(ArriveSheetConstants.SITUATION_UNNORMAL)!=-1				
				 ){
			 		isRed =true;
		 }
		}
		return isRed;
	}
	

	private void validaSignRfcEntity(SignRfcEntity entity,
			CurrentInfo currentInfo, boolean isRed) {
		if(isRed){
		 //查询当前运单是否有结清货款时产生的有效的保管费小票记录  ------------//TODO  CUBC是否提供查询小票的流水
			List<OtherRevenueEntity> OtherRevenueEntityList = null;
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(entity.getWaybillNo());
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaSignRfcEntity",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+entity.getWaybillNo());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				OtherRevenueEntityList =otherRevenueService.queryOtherRevenueByWayBillNOAndInvoiceCategory(entity.getWaybillNo(),null);
				if(OtherRevenueEntityList!=null&&OtherRevenueEntityList.size()>0){
					//调用结算接口   对小票记录进行红冲操作
					for(OtherRevenueEntity otherRevenueEntity:OtherRevenueEntityList){
						OtherRevenueDto otherRevenueDto = new OtherRevenueDto();
						otherRevenueDto.setActive(FossConstants.ACTIVE);
						otherRevenueDto.setOtherRevenueNo(otherRevenueEntity.getOtherRevenueNo());
						otherRevenueService.cancelOtherRevenue(otherRevenueDto,currentInfo);
					}
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO---查询并红冲小票
			}
		}
	}

	private void validaRepaymentEntity(CurrentInfo currentInfo,
			RepaymentEntity repaymentEntity, List<PosCardDetailEntity> lists,
			BigDecimal total) {
			for (PosCardDetailEntity posCardDetailEntity : lists) {
				if(total.compareTo(BigDecimal.ZERO)==1)
					if(posCardDetailEntity.getTradeSerialNo().equals(repaymentEntity.getClaimNo())){
						posCardDetailEntity.setModifyUser(currentInfo.getEmpName());
						posCardDetailEntity.setModifyUserCode(currentInfo.getEmpName());
						//如果反结清金额等于该明细的金额，更新T+0，同时删除明细
						if (total.compareTo(posCardDetailEntity.getOccupateAmount())==0) {
							// 根据交易流水号更新已使用流水号金额和未使用金额
							pdaPosManageDao.updatePosByNoForMoney(posCardDetailEntity);
							// 更新一个，释放一个 删除明细，将明细isDelete 置为Y
							pdaPosManageDao.deleteDetailByNo(posCardDetailEntity);
							break;
						}else{
							//如果反结清金额小于该明细金额，更新T+0金额，同时更新明细中金额
				
							posCardDetailEntity.setOccupateAmount(total);
							// 根据交易流水号更新已使用流水号金额和未使用金额
							pdaPosManageDao.updatePosByNoForMoney(posCardDetailEntity);
							//插入新的POS刷卡明细
							//准备数据
							posCardDetailEntity.setOccupateAmount(total.multiply(new BigDecimal("-1")));
							//根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据，灰度改造   353654 ---------------------------start 
							String vestSystemCode = null;
				            try {
				            	ArrayList<String> arrayList = new ArrayList<String>();
				            	arrayList.add(repaymentEntity.getWaybillNo());
				            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
				            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaRepaymentEntity",
				            			SettlementConstants.TYPE_FOSS);
				            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
				            	List<VestBatchResult> list = response.getVestBatchResult();
				            	vestSystemCode = list.get(0).getVestSystemCode();		
							} catch (Exception e) {
								LOGGER.info("灰度分流失败,"+"运单号："+repaymentEntity.getWaybillNo());
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
							}
							if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
								pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
							}
							if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
								//CUBC TODO
							}
							//根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据，灰度改造   353654 ---------------------------end
						}
					}
				}
			}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object updateApplyData(List<SignRfcChangeDetailEntity> scdeList,
			Object oc) {
		if (CollectionUtils.isEmpty(scdeList)) {
			return oc;
		}
		// 对象类型
		Class cls = oc.getClass();
		// 得到对象中的字段
		Field[] fields = cls.getDeclaredFields();
		// 字段名
		String fieldName;
		// 字段类型
		String fieldType;
		// 字段首字母大写
		String firstLetter;
		// 字段的Set方法
		String setMethodName;
		// 字段值
		String value;
		try {
			// 针对对象的每一个字段循环
			for (Field field : fields) {
				// 字段NAME
				fieldName = field.getName();
				// 字段TYPE
				fieldType = field.getType().getSimpleName();
				// 字段首字母
				firstLetter = fieldName.substring(0, 1).toUpperCase();
				// SET NAME
				setMethodName = "set" + firstLetter + fieldName.substring(1);
				// 循环
				for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : scdeList) {
					// 字段名相等
					if (fieldName.equals(signRfcChangeDetailEntity
							.getRfcItemsCode())) {
						// GET METHOD
						Method fieldSetMet = cls.getMethod(setMethodName,
								new Class[] { field.getType() });
						// VALUE
						value = signRfcChangeDetailEntity.getAfterRfcinfo();
						// TYPE IS String
						if ("String".equals(fieldType)) {
							fieldSetMet.invoke(oc, value);
							// type is date
						} else if ("Date".equals(fieldType)) {
							Date temp = parseDate(value);
							fieldSetMet.invoke(oc, temp);
							// type is integer or int
						} else if ("Integer".equals(fieldType)
								|| "int".equals(fieldType)) {
							Integer intval;
							if (StringUtils.isEmpty(value)) {
								intval = 0;
							} else {
								intval = Integer.parseInt(value);
							}
							fieldSetMet.invoke(oc, intval);
							// type is Long or long
						} else if ("Long".equalsIgnoreCase(fieldType)) {
							Long temp;
							if (StringUtils.isEmpty(value)) {
								temp = 0L;
							} else {
								temp = Long.parseLong(value);
							}
							fieldSetMet.invoke(oc, temp);
							// type is double or Double
						} else if ("Double".equalsIgnoreCase(fieldType)) {
							Double temp;
							if (StringUtils.isEmpty(value)) {
								temp = 0D;
							} else {
								temp = Double.parseDouble(value);
							}
							fieldSetMet.invoke(oc, temp);
							// type is bigdecimal
						} else if ("BigDecimal".equalsIgnoreCase(fieldType)) {
							BigDecimal temp;
							if (StringUtils.isEmpty(value)) {
								temp = BigDecimal.ZERO;
							} else {
								temp = new BigDecimal(value);
							}
							fieldSetMet.invoke(oc, temp);
						} else {
							// other type nothing to work
							LOGGER.info("not supper type" + fieldType);
						}
					}
				}
			}
			return oc;
			// 技术异常
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new SignException("技术异常", e);
		}
	}

	/**
	 * 
	 * <p>格式化时间<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param datestr
	 * @return
	 * Date
	 */
	private Date parseDate(String datestr) {
		// date string is null do nothing work
		if (StringUtils.isEmpty(datestr)) {
			return null;
		}
		try {
			String fmtstr = null;
			// format to yyyy-MM-dd HH:mm:ss
			if (datestr.indexOf(':') > 0) {
				fmtstr = "yyyy-MM-dd HH:mm:ss";
			} else {
				// format to yyyy-MM-dd
				fmtstr = "yyyy-MM-dd";
			}
			// create SimpleDateFormat
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
			// get string
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 给综合提高的接口数据<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-22 void
	 */
	@Override
	public List<ChangeSignRfcDto> queryArriveChangeByWaybillNo(String waybillNo) {
		// 条件实体
		SignRfcEntity entity = new SignRfcEntity();
		// 设置运单号
		entity.setWaybillNo(waybillNo);
		// 获得查询数据
		List<SignRfcEntity> entList = signRfcDao.querySignRfcList(entity);
		// 变更数据
		List<ChangeSignRfcDto> changeSignRfcDtoList= new ArrayList<ChangeSignRfcDto>();
		if (CollectionUtils.isEmpty(entList)) {
			return changeSignRfcDtoList;
		}
		
		// 变跟数据
		ChangeSignRfcDto changeSignRfcDto;
		//变更明细
		List<SignRfcChangeDetailEntity> signRfcChangeDetailList;
		// 查询条件
		SignRfcChangeDetailEntity signRfcChangeDetailEntity;
		for (SignRfcEntity signRfcEntity : entList) {
			// 返回对象
			changeSignRfcDto = new ChangeSignRfcDto();
			//运单签收结果信息
			signRfcChangeDetailList = new ArrayList<SignRfcChangeDetailEntity>();
			// 反签收
			if(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED.equals(signRfcEntity.getRfcType())){
				// 获得反签收明细
				List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao.searchReverseSignDetailList(signRfcEntity.getId());
				//集合不为空
				if (CollectionUtils.isNotEmpty(rsdeList)) {
					StringBuffer sb = new StringBuffer();
					sb.append("反签收");
					//遍历集合
					for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
						validaStringBuffer(sb, reverseSignDetailEntity);
					}
					SignRfcChangeDetailEntity changeEntity = new SignRfcChangeDetailEntity();
					changeEntity.setRfcItems(sb.toString());
					signRfcChangeDetailList.add(changeEntity);
				}
			//空运偏线反签收
			} else if(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_AIR_PARTIAL.equals(signRfcEntity.getRfcType())){
				WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultById(signRfcEntity.gettSrvWaybillSignResultId());
				
				WaybillEntity rstEntity =waybillManagerService.queryPartWaybillByNo(waybillNo);
				boolean isExpressExternal = false;
				validaSignRfcChange(signRfcChangeDetailList,
						waybillSignResultEntity, rstEntity, isExpressExternal);
			} else {
				signRfcChangeDetailEntity = new SignRfcChangeDetailEntity();
				signRfcChangeDetailEntity.settSrvSignRfcId(signRfcEntity.getId());
				signRfcChangeDetailList = signRfcChangeDetailDao.selectList(signRfcChangeDetailEntity);
				for (SignRfcChangeDetailEntity signRfcChangeDetailEntity2 : signRfcChangeDetailList) {
					if(SignConstants.PAYMENTTYPE.equals(signRfcChangeDetailEntity2.getRfcItemsCode())){
						signRfcChangeDetailEntity2.setAfterRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getAfterRfcinfo(), DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));
						signRfcChangeDetailEntity2.setBeforeRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getBeforeRfcinfo(), DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));
					}else if(SignConstants.IDENTIFYTYPE.equals(signRfcChangeDetailEntity2.getRfcItemsCode())){
						signRfcChangeDetailEntity2.setAfterRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getAfterRfcinfo(), DictionaryConstants.PKP_CREDENTIAL_TYPE));
						signRfcChangeDetailEntity2.setBeforeRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getBeforeRfcinfo(), DictionaryConstants.PKP_CREDENTIAL_TYPE));
					}else if(SignConstants.SITUATION.equals(signRfcChangeDetailEntity2.getRfcItemsCode())||SignConstants.SIGN_SITUATION.equals(signRfcChangeDetailEntity2.getRfcItemsCode())){
						if(SignConstants.SIGN_RFC_CHANGEDETAIL_TYPE_LABELTABLEFLG.equals(signRfcChangeDetailEntity2.getRfcType())){
							//更新签收明细表(T_SRV_SIGN_DETAIL),流水号对应的签收情况
							//1.由于签收变更明细表(t_srv_sign_rfc_changedetail)中的变更前信息和变更后信息的数据格式为:流水号.签收情况
						    //因此要拆解后获取签收情况,然后再存
							String afterInfoTmp= signRfcChangeDetailEntity2.getAfterRfcinfo();
							String beforeRfcinfo=signRfcChangeDetailEntity2.getBeforeRfcinfo();
							if(StringUtils.isNotBlank(afterInfoTmp)){
								String afterSerialNoInfo="";
								String afterSituationInfo=null;
								if(afterInfoTmp.indexOf(".")<0){
									 afterSerialNoInfo= afterInfoTmp;
								}else{
									 afterSerialNoInfo= afterInfoTmp.substring(0,afterInfoTmp.indexOf("."));
									 afterSituationInfo= afterInfoTmp.substring(afterInfoTmp.indexOf(".")+1);
								}
								signRfcChangeDetailEntity2.setAfterRfcinfo(afterSerialNoInfo+"."+DictUtil.rendererSubmitToDisplay(afterSituationInfo, DictionaryConstants.PKP_SIGN_SITUATION));
							}else{
								signRfcChangeDetailEntity2.setAfterRfcinfo("");
							}
							validaSignRfc(signRfcChangeDetailEntity2,
									beforeRfcinfo);
							
						}else{
							
							signRfcChangeDetailEntity2.setAfterRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getAfterRfcinfo(), DictionaryConstants.PKP_SIGN_SITUATION));
							signRfcChangeDetailEntity2.setBeforeRfcinfo(DictUtil.rendererSubmitToDisplay(signRfcChangeDetailEntity2.getBeforeRfcinfo(), DictionaryConstants.PKP_SIGN_SITUATION));
						}
					}
				}
			}
			changeSignRfcDto.setSignRfcChangeDetailList(signRfcChangeDetailList);
			changeSignRfcDto.setSignRfcEntity(signRfcEntity);
			changeSignRfcDtoList.add(changeSignRfcDto);
		}
		return changeSignRfcDtoList;
	}

	private void validaStringBuffer(StringBuffer sb,
			ReverseSignDetailEntity reverseSignDetailEntity) {
		if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT.equals(reverseSignDetailEntity.getType())) {
			//反签收类型为付款
			RepaymentEntity repaymentEntity = repaymentDao.searchRepaymentById(reverseSignDetailEntity.getMappingId());
			if (repaymentEntity != null) {
				sb.append(" 付款编号:");
				sb.append(repaymentEntity.getRepaymentNo());
			}
		} else if(SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET.equals(reverseSignDetailEntity.getType())){
			//反签收类型为到达联
			ArriveSheetEntity conditionEntity = new ArriveSheetEntity();
			conditionEntity.setId(reverseSignDetailEntity.getMappingId());
			ArriveSheetEntity arriveSheetEntity = arrivesheetDao.queryArriveSheetByEntity(conditionEntity);
			if (arriveSheetEntity != null) {
				sb.append(" 到达联编号:");
				sb.append(arriveSheetEntity.getArrivesheetNo());
			}
		}
	}

	private void validaSignRfcChange(
			List<SignRfcChangeDetailEntity> signRfcChangeDetailList,
			WaybillSignResultEntity waybillSignResultEntity,
			WaybillEntity rstEntity, boolean isExpressExternal) {
		if(rstEntity != null && waybillExpressService.onlineDetermineIsExpressByProductCode
										(rstEntity.getProductCode(),rstEntity.getBillTime())){
			OuterBranchExpressEntity outerBranchExpressEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(rstEntity.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
			if(null != outerBranchExpressEntity){
				isExpressExternal = true;
			}
		}
		if (waybillSignResultEntity != null) {
			SignRfcChangeDetailEntity changeEntity = new SignRfcChangeDetailEntity();
			if(isExpressExternal){
				changeEntity.setRfcItems("快递代理反签收");
			}else{
				changeEntity.setRfcItems("空运偏线反签收");
			}
			signRfcChangeDetailList.add(changeEntity);
		}
	}

	private void validaSignRfc(
			SignRfcChangeDetailEntity signRfcChangeDetailEntity2,
			String beforeRfcinfo) {
		if( StringUtils.isNotBlank(beforeRfcinfo) ){
			String beforeSerialNoInfo="";
			String beforeSituationInfo=null;
			if(beforeRfcinfo.indexOf(".")<0){
				beforeSerialNoInfo=beforeRfcinfo;
			}else{
				beforeSerialNoInfo= beforeRfcinfo.substring(0,beforeRfcinfo.indexOf("."));
				beforeSituationInfo= beforeRfcinfo.substring(beforeRfcinfo.indexOf(".")+1);
			}
			signRfcChangeDetailEntity2.setBeforeRfcinfo(beforeSerialNoInfo+"."+DictUtil.rendererSubmitToDisplay(beforeSituationInfo, DictionaryConstants.PKP_SIGN_SITUATION));
		}else{
			signRfcChangeDetailEntity2.setBeforeRfcinfo("");
		}
	}
	/**
	 * 
	 * <p>签收时间的更改只能修改为货物到达本部门库存后的时间，
	 * 不能修改为出库之前的时间<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-25
	 * @param arrivesheetId
	 * @param signTime
	 * void
	 */
	@SuppressWarnings("unused")
	private void checkInputSignTime(String arrivesheetId,Date signTime){
		//通过ID获得到达联信息
		ArriveSheetEntity arriveSheetEntity = arrivesheetDao.queryArriveSheetById(arrivesheetId);
		//获得第一次的到达联信息
		ArriveSheetEntity arriveSheetEntityFrist = arrivesheetDao.queryArriveSheetCreateTime(arriveSheetEntity.getArrivesheetNo());
		if(signTime != null){
			if(signTime.after(arriveSheetEntityFrist.getCreateTime()) &&
					signTime.before(arriveSheetEntityFrist.getSignTime())){
				LOGGER.info("签收时间正确");
			}else{
				LOGGER.error("签收时间不正确");
				throw new SignException("签收时间只能在到达联开始时间之后和签收之前");
			}
		}else{
			LOGGER.info("签收时间没有变更");
		}
	}
	/**
	 * 
	 * <p>保存附件<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-6
	 * @param fileList
	 * @param id
	 * void
	 */
	private void saveAttachment(List<AttachementEntity> fileList, String id) {
		//是否有附件
		if (CollectionUtils.isNotEmpty(fileList)) {
			for (AttachementEntity attachementEntity : fileList) {
				//关联上传组件和业务
				attachementEntity.setRelatedKey(id);
				//更新上传组件数据
				attachementService.updateAttachementInfo(attachementEntity);
			}
		} else {
			LOGGER.info("没有附件，不需要保存");
		}

	}
	/**
	 * 
	 * <p>获得该变更的凭证<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-7
	 * @param id 变更签收ID
	 * @return
	 * List<AttachementEntity>
	 */
	private List<AttachementEntity> searchAttachmentById(String id) {
		return attachementService.getAttachementInfos(id, SignConstants.ATTACHEMENTMODE);
	}
	/**
	 * 
	 * <p>判断输入的金额是否有效<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-7
	 * @param waybillNo
	 * @param changeDetailentityList
	 * @param tSrvSignRfcId;
	 * 变更ID
	 * @param rfcItems;
	 * 变更项NAME
	 * @param rfcItemsCode;
	 * 变更项CODE
	 * @param beforeRfcinfo;
	 * 变更前
	 * @param afterRfcinfo;
	 * 变更后
	 * @param rfcType;
	 * 变更类型(0:付款,1:到达联,2:运单签收结果)
	 * @param repaymentId
	 * void
	 */
	private void checkInputMoney(String waybillNo,
			List<SignRfcChangeDetailEntity> changeDetailentityList,
			String repaymentId) {
		// 付款实体
		RepaymentEntity repaymentEntity = repaymentDao
				.searchRepaymentById(repaymentId);
		// 老实付运费
		BigDecimal oldactualFreight = repaymentEntity.getActualFreight();
		// 老代收货款
		BigDecimal oldcodAmount = repaymentEntity.getCodAmount();
		// 新实付运费
		BigDecimal newactualFreight = repaymentEntity.getActualFreight();
		// 新代收货款
		BigDecimal newcodAmount = repaymentEntity.getCodAmount();
		// 从新赋值
		try {
			for (SignRfcChangeDetailEntity signRfcChangeDetailEntity : changeDetailentityList) {
				// 实付运费
				if (SignConstants.ACTUALFREIGHT
						.equals(signRfcChangeDetailEntity.getRfcItemsCode())) {
					newactualFreight = new BigDecimal(
							signRfcChangeDetailEntity.getAfterRfcinfo());
					// 代收货款
				} else if (SignConstants.CODAMOUNT
						.equals(signRfcChangeDetailEntity.getRfcItemsCode())) {
					newcodAmount = new BigDecimal(
							signRfcChangeDetailEntity.getAfterRfcinfo());
				} else {
					LOGGER.info("不是所要字段");
				}
			}
		} catch (Exception e) {
			throw new SignException("输入的金额不可以为空和非数据");
		}
		FinancialDto financialDto = new FinancialDto();

		// 调用接口，获得财务数据
		setFinancialDtoInfo(waybillNo, financialDto);

		// 应收代收款
		BigDecimal receiveableAmount = financialDto.getReceiveableAmount();
		// 应收到付款
		BigDecimal receiveablePayAmoout = financialDto
				.getReceiveablePayAmoout();
		if (receiveableAmount == null && receiveablePayAmoout == null) {
			throw new SignException("没有找到应收代收款和应收到付款");
		} else {
			if (receiveableAmount == null) {
				receiveableAmount = BigDecimal.ZERO;
			} else {
				LOGGER.info("数据正常");
			}
			if (receiveablePayAmoout == null) {
				receiveablePayAmoout = BigDecimal.ZERO;
			} else {
				LOGGER.info("数据正常");
			}
			// 新输入的实付运费，必须大于等于0小于应收到付款+老的实付运费(receiveablePayAmoout+oldactualFreight>newactualFreight>0)
			if (newactualFreight.compareTo(receiveablePayAmoout
					.add(oldactualFreight)) == 1
					|| newactualFreight.compareTo(BigDecimal.ZERO) == -1) {
				throw new SignException("实付运费必须大于或等于0,小于"
						+ receiveablePayAmoout.add(oldactualFreight));
			}
			// 新输入的代收款，必须大于等于0小于应收代收款+老的代收款(receiveableAmount+oldcodAmount>newcodAmount>0)
			if (newcodAmount.compareTo(receiveableAmount.add(oldcodAmount)) == 1
					|| newcodAmount.compareTo(BigDecimal.ZERO) == -1) {
				throw new SignException("代收款必须大于或等于0,小于"
						+ receiveableAmount.add(oldcodAmount));
			}
		}

	}

	/**
	 * 
	 * <p>调用结算接口，获得应收货款和应收代收货款<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-8
	 * @param waybillNo
	 * @param financialDto
	 * @param toPayAmount
	 * 到付金额(实付运费)
	 * @param codAmount
	 *  代收货款
	 * @param receivedAllAmount
	 *  收款总额
	 * @param paymentType
	 * 开单付款方式
	 * @param receiveableAmount
	 *  应收代收款
	 * @param receivedAmount
	 *  已收代收款
	 * @param receiveablePayAmoout
	 *  应收到付款
	 * @param receivedPayAmount
	 *  已收到付款
	 * @param isWholeVehicle
	 * 是否整车运单
	 * @param productCode
	 *  运输性质
	 * @param consigneeCode
	 * 客户编码
	 * @param consigneeName
	 * 客户名称
	 * @param claimNo
	 * 款项认领编号
	 * @param deliverbillNo
	 * 派送单编号
	 * @param orderNo
	 * 订单号
	 * void
	 */
	private void setFinancialDtoInfo(String waybillNo, FinancialDto financialDto) {
		BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
		//财务单据查询，灰度改造   353654 ---------------------------start
		List<BillReceivableEntity> billReceivableEntities = null;
		String vestSystemCode = null;
        try {
        	ArrayList<String> arrayList = new ArrayList<String>();
        	arrayList.add(waybillNo);
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".setFinancialDtoInfo",
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();		
		} catch (Exception e) {
			LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillNo);
			billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
			LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillNo);
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			try {
				billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillNo);			
			} catch (Exception e) {
				LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
				throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
			}
		}
		//财务单据查询，灰度改造   353654 ---------------------------end
		if(CollectionUtils.isEmpty(billReceivableEntities)){
			LOGGER.info("单号"+waybillNo+";"+vestSystemCode+"财务查询为空");
			// 应收代收款
			financialDto.setReceiveableAmount(BigDecimal.ZERO);
			// 已收代收款
			financialDto.setReceivedAmount(BigDecimal.ZERO);
			// 应收到付款
			financialDto.setReceiveablePayAmoout(BigDecimal.ZERO);
			//已收到付款
			financialDto.setReceivedPayAmount(BigDecimal.ZERO);
		}
		if(!CollectionUtils.isEmpty(billReceivableEntities)){
			for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
				// 到达应收单
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(billReceivableEntity.getBillType())) {
					// 应收到付款
					financialDto.setReceiveablePayAmoout(billReceivableEntity
							.getUnverifyAmount());
					// 已收到付款
					financialDto.setReceivedPayAmount(billReceivableEntity
							.getVerifyAmount());
				} // 代收货款应收单
				else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(billReceivableEntity.getBillType())) {
					// 应收代收款
					financialDto.setReceiveableAmount(billReceivableEntity
							.getUnverifyAmount());
					// 已收代收款
					financialDto.setReceivedAmount(billReceivableEntity
							.getVerifyAmount());
				}
			}
		}
	}
	/**
	 * * 界面标题：空运/偏线反签收
	 * 6.	查询运单：运单号。
	 * a)	运单号：客户申请空运/偏线反签收的对应运单单号
	 * b)	查询：根据录入的运单号，将查询出的运单信息显示中在运单信息栏中
	 * 7.	运单签收信息：提货人名称、证件类型、证件号、签收情况、签收件数、签收时间、签收备注。
	 * a)	提货人名称：即实际提货人的名字
	 * b)	证件类型：提货人提供的身份证件类型
	 * c)	证件号：身份证件号码
	 * d)	签收情况：货物签收情况
	 * e)	签收件数：货物签收件数
	 * f)	签收时间：客户签收货物的时间
	 * g)	签收备注：签收信息的其它附加信息
	 * 8.	运单基本信息：包括运单号、收货人、
	 * 收货人地址、收货人电话、付款方式、到付金额、预付金额、
	 * 代收货款、货物名称、保险价值、总运费、派送方式、重量、件数、体积、
	 * 包装、发货部门、发货人、到达部门、到达时间、运输性质、运输类型、库存状态、出发时间。
	 * a)	运单号：开单时录入的运单号
	 * b)	收货人：收货人名字 
	 * c)	收货人地址：收货人的地址
	 * d)	收货人电话：收货人的电话
	 * e)	付款方式：开单付款方式
	 * f)	到付金额：开单到付金额
	 * g)	预付金额：开单预付金额
	 * h)	代收货款：发货人要求承运方代收的金额
	 * i)	货物名称：承运货物名称
	 * j)	保险价值：发货人对所托运货物的投保金额
	 * k)	总运费：开单总运费
	 * l)	派送方式：给收货人送货方式
	 * m)	重量：货物重量
	 * n)	件数：货物件数
	 * o)	体积：货物体积
	 * p)	包装：货物包装
	 * q)	发货部门：发货人所在发货部门
	 * r)	发货人：发货人名字
	 * s)	到达部门：货物到达目的站的营业部
	 * t)	到达时间：货物到达时间
	 * u)	运输性质：产品类型
	 * v)	运输类型：走货方式
	 * w)	库存状态：货物库存状态
	 * x)	出发时间：货物走货时间
	 * 9.	上传凭证：包含变更原因、变更凭证
	 * a)	反签收备注：备注反签收的原因
	 * b)	反签收凭证：申请人提交的反签收凭证
	 * c)	是否确认反签收：勾选上之后表示确认反签收操作
	 * <p>获得反签收空运和偏线的信息<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @param waybillNo
	 * @return
	 * RepaymentArrivesheetDto
	 */
	@Override
	public RepaymentArrivesheetDto searchReverseSignAirPartial(String waybillNo) {
		//反签收
		RepaymentArrivesheetDto dto = new RepaymentArrivesheetDto();
		WaybillEntity waybillEntity = waybillManagerService
				.queryWaybillBasicByNo(waybillNo);
		
		if (waybillEntity == null || StringUtils.isEmpty(waybillEntity.getId())) {
			throw new SignException("该运单不存在");
		}
		//判断为悟空的运单 ，提示悟空-反签收界面操作362309
		if(FossConstants.YES.equals(waybillEntity.getIsecs())){
			throw new SignException("查询失败："+waybillNo + "单号为悟空的运单请在悟空界面进行操作！");
		}
		
		//判断运单为快递 ，限制返货新单的原单反签收
		if(waybillExpressService.onlineDetermineIsExpressByProductCode
						(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
			LOGGER.info("验证运单是否有返货有效新单开始......");
			WaybillExpressEntity expEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(
					waybillNo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if(expEntity != null){ //有返货新单
				//原单查出的返货新单 有未作废的
				LOGGER.info("运单存在有效返货新单，不允许原单反签收");
				throw new SignException("该运单存在有效返货新单，原单不允许反签收");
			}
			//为新单，或原单无有效返货新单 允许当前单号反签收
			LOGGER.info("验证运单是否有返货有效新单结束......");
		}
		
		//获得部门--发货部门
		waybillEntity.setReceiveOrgCode(getNameByCode(waybillEntity.getReceiveOrgCode()));
		//获得部门--到达部门
		waybillEntity.setLastLoadOrgCode(getNameByCode(waybillEntity.getLastLoadOrgCode()));
		dto.setWaybillEntity(waybillEntity);
		
		//运单签收结果
		WaybillSignResultEntity entity = searchWaybillSignResult(waybillNo);
		if(entity != null){
			dto.setWaybillSignResultEntity(entity);
		}
		return dto;
	}
	/**
	 * 空运/偏线反签收
	 * 界面标题：空运/偏线反签收
	 * 6.	查询运单：运单号。
	 * a)	运单号：客户申请空运/偏线反签收的对应运单单号
	 * b)	查询：根据录入的运单号，
	 * 将查询出的运单信息显示中在运单信息栏中
	 * 7.	运单签收信息：提货人名称、证件类型、
	 * 证件号、签收情况、签收件数、签收时间、签收备注。
	 * a)	提货人名称：即实际提货人的名字
	 * b)	证件类型：提货人提供的身份证件类型
	 * c)	证件号：身份证件号码
	 * d)	签收情况：货物签收情况
	 * e)	签收件数：货物签收件数
	 * f)	签收时间：客户签收货物的时间
	 * g)	签收备注：签收信息的其它附加信息
	 * 8.	运单基本信息：包括运单号、收货人、收货人地址、收货人电话、
	 * 付款方式、到付金额、预付金额、代收货款、货物名称、保险价值、
	 * 总运费、派送方式、重量、件数、体积、包装、发货部门、发货人、
	 * 到达部门、到达时间、运输性质、运输类型、库存状态、出发时间。
	 * a)	运单号：开单时录入的运单号
	 * b)	收货人：收货人名字 
	 * c)	收货人地址：收货人的地址
	 * d)	收货人电话：收货人的电话
	 * e)	付款方式：开单付款方式
	 * f)	到付金额：开单到付金额
	 * g)	预付金额：开单预付金额
	 * h)	代收货款：发货人要求承运方代收的金额
	 * i)	货物名称：承运货物名称
	 * j)	保险价值：发货人对所托运货物的投保金额
	 * k)	总运费：开单总运费
	 * l)	派送方式：给收货人送货方式
	 * m)	重量：货物重量
	 * n)	件数：货物件数
	 * o)	体积：货物体积
	 * p)	包装：货物包装
	 * q)	发货部门：发货人所在发货部门
	 * r)	发货人：发货人名字
	 * s)	到达部门：货物到达目的站的营业部
	 * t)	到达时间：货物到达时间
	 * u)	运输性质：产品类型
	 * v)	运输类型：走货方式
	 * w)	库存状态：货物库存状态
	 * x)	出发时间：货物走货时间
	 * 9.	上传凭证：包含变更原因、变更凭证
	 * a)	反签收备注：备注反签收的原因
	 * b)	反签收凭证：申请人提交的反签收凭证
	 * c)	是否确认反签收：勾选上之后表示确认反签收操作
	 * <p>反签收空运和偏线的信息保存<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @param dto
	 * @param arrivesheetNo;
	 * 到达联编号
	 * @param deliverymanName;
	 * 提货人名称
	 * @param identifyType; 
	 * 证件类型
	 * @param identifyCode;
	 * 证件号码
	 * @param situation;
	 * 签收情况
	 * @param arriveSheetGoodsQty;
	 * 到达联件数
	 * @param signGoodsQty; 
	 * 签收件数
	 * @param signNote;
	 * 签收备注
	 * @param signTime; 
	 * 签收时间
	 * @param isPrinted;  
	 * 是否打印
	 * @param printtimes; 
	 * 打印次数
	 * @param createUserName; 
	 * 创建人
	 * @param createUserCode; 
	 * 创建人编码
	 * @param createOrgName; 
	 * 创建部门
	 * @param createOrgCode; 
	 * 创建部门编码
	 * @param createTime; 
	 * 创建时间
	 * @param status;
	 * 到达联状态
	 * @param isPdaSign;
	 * 是否PDA签到
	 * @param active;
	 * 是否有效
	 * @param isSentRequired; 
	 * 是否必送货
	 * @param isNeedInvoice; 
	 * 是否需要发票
	 * @param preNoticeContent; 
	 * 提前通知内容
	 * @param deliverRequire; 
	 * 送货要求
	 * @param isRfcing;
	 * 是否审批中
	 * @param tagNumber; 
	 * 标签编号
	 * @param operateTime;
	 * 签收操作时间
	 * @param operator;
	 *  操作人
	 * @param operatorCode;
	 *  操作人编码
	 * @param operateOrgName;
	 *  操作部门名称
	 * @param operateOrgCode;
	 *  操作部门编码
	 * @param destroyed;
	 *  是否作废
	 * @param modifyTime;
	 * 有效日期
	 * @param oldArriveSheetGoodsQty;
	 * 并发控制使用
	 * @param oldStatus;
	 */
	@Override
	@Transactional
	public void saveReverseSignAirPartialInfo(SignResultDto dto, CurrentInfo currentInfo) {
		LOGGER.info("saveReverseSignAirPartialInfo begin......");
		// 签收变更
		SignRfcEntity record = dto.getSignRfcEntity();
		// 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
		record.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
		// 反签收
		record.setRfcType(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_AIR_PARTIAL);
		// 签收变更赋值
		setSignRfcInfo(currentInfo, record);
		//互斥锁定
		MutexElement mutexElement = new MutexElement(dto.getSignRfcEntity().getWaybillNo(), "运单编号", MutexElementType.APPLICATION_SIGN_RFC);
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error(SignException.WAYBILL_LOCKED);//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		
		if (isRfc(record)) {
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(SignException.IS_NOT_APPROVAL);
		}
		
		/**
		 * 需求DN201608050016
		 * FOSS-xujie 330547
		 * 2016-6-25
		 */
		// 运单号
		String waybillNum = dto.getSignRfcEntity().getWaybillNo();
		// 创建签收结果对象
		WaybillSignResultEntity waybillSignResultEntity = new WaybillSignResultEntity();
		//设置运单号
		waybillSignResultEntity.setWaybillNo(waybillNum);
		// 状态
		waybillSignResultEntity.setActive(FossConstants.ACTIVE);
		//获得运单签收结果信息
		WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntity);
		//运单明细
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNum);
		//判断是否为快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),waybill.getBillTime())
				&&!(waybill == null)&&!(resultEntity == null)){
			//判断是否为理赔状态和是否为异常签收
			String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNum);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".saveReverseSignAirPartialInfo",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNum);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				if(getClaimInfo(waybillNum)&&!SignConstants.NORMAL_SIGN.
						equalsIgnoreCase(resultEntity.getSignSituation())){
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException("已申请理赔，无法更改签收状态");
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//CUBC TODO
			}
		}
		
		//查询子母件
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(SignConstants.WAYBILL_NO,record.getWaybillNo());
		params.put(SignConstants.ACTIVE,FossConstants.YES);
		TwoInOneWaybillDto twoInOneDto = waybillRelateDetailEntityService.
				queryWaybillRelateByWaybillOrOrderNo(params);
		
		try {
			//校验子母件确认收入
			if(checkTwoInOneReverseConfirmIncome(twoInOneDto,record.getWaybillNo(),true)){
				// 校验反签收服务
				validReverseConfirmIncome(record, currentInfo, SettlementConstants.LAND_STOWAGE_SIGN);
			}
		} catch (SignException e) {
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(e.getErrorCode());
		}
		// 保存签收变更
		signRfcDao.insertSelective(record);
		//反签收附件保存
		saveAttachment(dto.getReverseSignRfcFiles(),record.getId());
		// 反签收明细
		ReverseSignDetailEntity reverseSignDetailEntity = new ReverseSignDetailEntity();
		//类型--运单签收结果
		reverseSignDetailEntity.setType(SignConstants.REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT);
		//ID
		reverseSignDetailEntity.settSrvSignRfcId(record.getId());
		//mappingID--运单签收结果ID
		reverseSignDetailEntity.setMappingId(record.gettSrvWaybillSignResultId());
		//保存空运偏线反签收数据
		reverseSignDetailDao.insertSelective(reverseSignDetailEntity);
		// 更新签收结果状态
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		entity.setId(record.gettSrvWaybillSignResultId());
		entity.setIsRfcing(FossConstants.YES);
		waybillSignResultService.updateWaybillSignResultById(entity);
		try{
		//空运偏线OA差错上报
			String waybillNo=record.getWaybillNo();
			this.reverseErrorReport(waybillNo,currentInfo);
		}catch(QmsErrorException e){
			LOGGER.info("运单号：" + record.getWaybillNo() + "上报QMS反签收差错失败");
			businessLockService.unlock(mutexElement);//解锁
			// 已提交申请
			throw new SignException(e.getErrorCode());
		}
		
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("saveReverseSignAirPartialInfo end......");
	}

	/***数据校验**/
	//运单是否为本部门--
	/**
	 * 
	 * <p>判断查询人是否和运单的最终配载部门是同一部门<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-29
	 * @param wayBillNo
	 * @return
	 * WaybillEntity
	 */
	private WaybillEntity checkWayBillAndReturnWaybillEntity(String wayBillNo){

		//运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(wayBillNo);
		if(waybillEntity == null){
			throw new SignException("没有查到该运单号码：" + wayBillNo);
		}		
		//add by chenjunying 231438 2015-02-05 ----
		//判断运单为快递 ，限制返货新单的原单反签收
		if(waybillExpressService.onlineDetermineIsExpressByProductCode
						(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
			LOGGER.info("验证运单是否有返货有效新单开始......");
			WaybillExpressEntity expEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(
					wayBillNo, WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if(expEntity != null){ //有返货新单
				//原单查出的返货新单 有未作废的
				LOGGER.info("运单存在有效返货新单，不允许原单反签收");
				throw new SignException("该运单存在有效返货新单，原单不允许反签收");
			}
			//为新单，或原单无有效返货新单 允许当前单号反签收
			LOGGER.info("验证运单是否有返货有效新单结束......");
		} //----
		
		//最终配载部门
		String orgCode=FossUserContext.getCurrentDeptCode();//获取当前用户部门编码
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())){
			//非营业部找到它上级所属外场的编码
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
			if(orgAdministrativeInfoEntity != null){
				orgCode=orgAdministrativeInfoEntity.getCode();
			}
		}
		if(FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && orgCode.equals(waybillEntity.getReceiveOrgCode())
				&& FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
		{
			//这里不作操作
		}
		else if(!orgCode.equals(waybillEntity.getLastLoadOrgCode())){
			throw new SignException("请确认该运单号其到达部门为当前操作部门");
		}
		return waybillEntity;
	}
	/**
	 * 
	 * <p>根据CODE获得Name<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-22
	 * @param code
	 * @return
	 * String
	 */
	private String getNameByCode(String code){
		//获得部门--发货部门
		OrgAdministrativeInfoEntity org = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		if(org != null){
			return org.getName();
		}
		return null;
	}

	@Override
	public boolean checkWayBillSignStatus(String waybillNo){
		// 条件实体
		SignRfcEntity entity = new SignRfcEntity();
		// 设置运单号
		entity.setWaybillNo(waybillNo);
		// 获得查询数据
		List<SignRfcEntity> entList = signRfcDao.querySignRfcList(entity);
		for (SignRfcEntity signRfcEntity : entList) {
			if(SignConstants.SIGN_RFC_SIGN_APPROVALIN.equals(signRfcEntity.getStatus())){
				return false;
			}
		}
		return true;
	}

	private void checkArrivesheetQty(SignResultDto dto,
			String[] arrivesheetArray) {
		// 获得到达联LIST,通过运单号
		ArriveSheetDto ae = new ArriveSheetDto();
		// 运单号
		ae.setWaybillNo(dto.getSignRfcEntity().getWaybillNo());
		// 已签收确认
		List<String> arriveSheetStatusList = new ArrayList<String>();
		arriveSheetStatusList.add(ArriveSheetConstants.STATUS_SIGN);
		// 已签收状态
		ae.setArriveSheetStatus(arriveSheetStatusList);
		// 激活状态
		ae.setActive(FossConstants.ACTIVE);
		// 非销毁状态
		ae.setDestroyed(FossConstants.NO);
		//不在审批中
		ae.setIsRfcing(FossConstants.NO);
		// 获得运单对应的有效到达联信息
		List<ArriveSheetEntity> arriveSheetList = arrivesheetDao
				.queryArriveSheetByLifeCycle(ae);
		//如果到达联的数量和选中的到达联数据比较
		if (arriveSheetList.size() == arrivesheetArray.length) {
			//校验是否有代收货款结清
			checkWaybillRepayment(dto.getSignRfcEntity().getWaybillNo());		
		}

	}
	/**
	 * 
	 * <p>提供给综合的接口<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-20
	 * @param waybillNo
	 * void
	 */
	@Override
	public List<WayBillNoLocusDTO> queryReverseArriveSheetByWaybillNo(String waybillNo) {
		// 实例化返回LIST
		List<WayBillNoLocusDTO> wayBillNoLocusDTOList = new ArrayList<WayBillNoLocusDTO>();
		// 定义返回DTO
		WayBillNoLocusDTO wayBillNoLocusDTO;
		//定义到达联实体
		ArriveSheetEntity arriveSheetEntity;
		//偏线空运
		WaybillSignResultEntity waybillSignResultEntity;
		// 查询条件
		SignRfcEntity condition = new SignRfcEntity();
		// 设置运单号
		condition.setWaybillNo(waybillNo);
		// 设置状态
		condition.setStatus(SignConstants.SIGN_RFC_SIGN_PASSED);
		// 设置类型为凡签收专线
		condition.setRfcType(SignConstants.SIGN_RFC_TYPE_REVERSESIGN_DEDICATED);
		// 获得变更到达联单历史
		List<SignRfcEntity> list = signRfcDao
				.querySignRfcListByWaybill(condition);
		// 遍历集合
		for (SignRfcEntity signRfcEntity : list) {
			// 查询该主数据对应的明细数据
			List<ReverseSignDetailEntity> reverseSignDetailEntityList = reverseSignDetailDao
					.searchReverseSignDetailList(signRfcEntity.getId());
			// 遍历反签收明细
			for (ReverseSignDetailEntity reverseSignDetailEntity : reverseSignDetailEntityList) {
				// 判断是反签收的货物
				if (SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET
						.equals(reverseSignDetailEntity.getType())) {
					wayBillNoLocusDTO = setWayBillNoLocusDTOValue(waybillNo,
							signRfcEntity);

					//通过映射ID，得到到达联信息
					arriveSheetEntity = arrivesheetDao.queryArriveSheetById(reverseSignDetailEntity.getMappingId());
					// 操作件数 /
					wayBillNoLocusDTO.setOperateNumber(arriveSheetEntity.getSignGoodsQty());

					// 签收人姓名 /
					wayBillNoLocusDTO.setSignManName(null);
					
					//放入到返回集合中
					wayBillNoLocusDTOList.add(wayBillNoLocusDTO);
				}else if(SignConstants.REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT
						.equals(reverseSignDetailEntity.getType())){
					//设置返回对象
					wayBillNoLocusDTO = setWayBillNoLocusDTOValue(waybillNo,
							signRfcEntity);

					//通过映射ID，得到到达联信息
					waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultById(reverseSignDetailEntity.getMappingId());
					// 操作件数 /
					wayBillNoLocusDTO.setOperateNumber(waybillSignResultEntity.getSignGoodsQty());

					// 签收人姓名 /
					wayBillNoLocusDTO.setSignManName(null);
					
					//放入到返回集合中
					wayBillNoLocusDTOList.add(wayBillNoLocusDTO);
				}
			}

		}
		return wayBillNoLocusDTOList;
	}

	private WayBillNoLocusDTO setWayBillNoLocusDTOValue(String waybillNo,
			SignRfcEntity signRfcEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO;
		// 实例化对象
		wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 运单号
		wayBillNoLocusDTO.setWaybillNo(waybillNo);
		// 操作部门编码
		wayBillNoLocusDTO.setOperateOrgCode(signRfcEntity
				.getDraftOrgCode());

		// 操作内容 /
		wayBillNoLocusDTO.setOperateContent(signRfcEntity
				.getReason());

		// 操作时间/
		wayBillNoLocusDTO.setOperateTime(signRfcEntity
				.getDraftTime());

		// 操作人姓名 /
		wayBillNoLocusDTO
				.setOperateName(signRfcEntity.getDrafter());
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 
	 * 验证运单签收变更、反签收申请情况
	 * 
	 * @param waybillNo
	 * @author ibm-wangfei
	 * @date Apr 2, 2013 5:03:27 PM
	 */
	@Override
	public void checkWayBillRfcStatus(String waybillNo) {
		// 查询条件
		SignRfcEntity condition = new SignRfcEntity();
		// 设置运单号
		condition.setWaybillNo(waybillNo);
		// 设置状态
		condition.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
		// 获得变更到达联单历史
		List<SignRfcEntity> list = signRfcDao.querySignRfcListByWaybill(condition);
		if (CollectionUtils.isNotEmpty(list)) {
			//抛出异常
			throw new SignException(SignException.SIGN_CHECK_REVIEWING);
		}
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	/**
	 * 
	 * 验证是否已经提交申请
	 * 
	 * @param newEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 22, 2013 4:31:12 PM
	 */
	public boolean isRfc(SignRfcEntity newEntity) {
		SignRfcEntity checkEntity = new SignRfcEntity();
		// 运单号
		checkEntity.setWaybillNo(newEntity.getWaybillNo());
		// 变更类型
		checkEntity.setRfcType(newEntity.getRfcType());
		// 审批状态
		checkEntity.setStatus(newEntity.getStatus());
		Long i = signRfcDao.getWaybillNotApprovalCount(checkEntity);
		return i != null && i.intValue() > 0;
	}
	/**
	 * DMANA-9716 2015-03-31
	 * 判断限制有投诉变更的运单 ，不让变更为正常签收
	 * @author chenjunying 
	 * @param SignResultDto
	 */
	public void checkValidateChange(SignResultDto dto){
		// 判断限制有投诉变更的运单 ，不让变更为正常签收
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getSignRfcEntity().getWaybillNo());
		if(null==actual){
			//无实际承运表信息，抛出异常中断执行
			throw new SignException("实际承运表中无该运单信息");
		}
		//限制 有投诉变更签收结果的运单，签收时再做正常签收
		if(SignConstants.YES.equals(actual.getComplainStatus())){ //有投诉自动变更，变更签收结果不让变更为正常签收
			throw new SignException("投诉自动变更异常签收的运单，不能变更为正常签收");
		}
	}
	/**
	 * 查询运单签收变更、反签收记录
	 * @param waybillNo,status
	 * @author Foss-231438 chenjunying
	 * @date 2015-05-07
	 */
	@Override
	public List<SignRfcEntity> queryWayBillRfcListBy(String waybillNo,String status) {
		// 查询条件
		SignRfcEntity condition = new SignRfcEntity();
		// 设置运单号
		condition.setWaybillNo(waybillNo);
		// 设置状态
		condition.setStatus(status);
		// 获得运单签收变更、反签收记录
		return signRfcDao.querySignRfcListByWaybill(condition);
	}
	
	/**
	 * 校验子母件 子件单号是否全部反结清
	 * @author Foss-231434 bieyexiong
	 * @date 2015-09-08
	 */
	private void checkTwoInOneRepayment(TwoInOneWaybillDto twoInOneDto,String waybillNo){
		//该运单是子母件并且是母件,并且子件集合不为空
		if(twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
					&& waybillNo.equals(twoInOneDto.getMainWaybillNo())
					&& CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())){
			for(String sonWaybillNo : twoInOneDto.getWaybillNoList()){
				//查询子单是否结清
				if(repaymentService.isPayment(sonWaybillNo)){
					//子单未全部反结清,母单不能反结清，抛业务异常
					throw new SignException(SignException.SON_NOT_REPAYMENT);
				}
			}
		}
	}
	
	/**
	 * 校验子母件确认收入
	 * @author Foss-231434 bieyexiong
	 * @date 2015-10-27
	 */
	private boolean checkTwoInOneReverseConfirmIncome(TwoInOneWaybillDto twoInOneDto, String waybillNo, boolean isLdp){
		//该运单是子母件并且是母件,并且子件集合不为空
		if(twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
				&& waybillNo.equals(twoInOneDto.getMainWaybillNo())
				&& CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())){
			//所有子件的签收结果集
			List<WaybillSignResultEntity> signResults = new ArrayList<WaybillSignResultEntity>();
			//子件所有运单
			List<String> waybillNos = twoInOneDto.getWaybillNoList();
			// 创建对象
			WaybillSignResultEntity entity = new WaybillSignResultEntity();
			// 状态
			entity.setActive(FossConstants.ACTIVE);
			for(String waybill : waybillNos){
				//设置运单号
				entity.setWaybillNo(waybill);
				//签收结果
				WaybillSignResultEntity resultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
				//当前子件单号全部签收时，添加进签收结果集
				if(resultEntity != null 
						&& SignConstants.SIGN_STATUS_ALL.equals(resultEntity.getSignStatus())){
					signResults.add(resultEntity);
				}
			}
			//快递代理当子件未全部反签收时，母件不让反签收
			if(isLdp && signResults.size() > 0){
				//快递代理，子单未全部反签收,母单不能反签收，抛业务异常
				throw new SignException(SignException.SON_NOT_REVERSE);
			}
			if(signResults.size() == waybillNos.size()){
				//是母件，且子件所有单号全部签收
				return true;
			}else{
				//是母件，但子母件有单号未签收
				return false;
			}
		}else{
			//非子母件或非母件
			return true;
		}
	}
	/**
	 * 查询运单签反签收记录 (反结清、反签收【空运/偏线/落地配-反签收结果、专线反到达联】)
	 * @param waybillNo,status
	 * @author Foss-231438 chenjunying
	 * @date 2015-12-03
	 */
	@Override
	public List<SignRfcEntity> queryWayBillRfcReverseList(String waybillNo,String status) {
		// 查询条件
		SignRfcEntity condition = new SignRfcEntity();
		// 设置运单号
		condition.setWaybillNo(waybillNo);
		// 设置状态
		condition.setStatus(status);
		// 获得运单签收变更、反签收记录
		return signRfcDao.queryReserveSignListByCondition(condition);
	}
	
	/**
	 * 获取运单理赔信息
	 * 
	 * @param waybillNo
	 * @author Foss-330547 徐杰 DN201608050016
	 * @return
	 * @throws IOException
	 * @date 2016-09-10
	 */
	public boolean getClaimInfo(String waybillNo){
		try {
			// 设置IP和ESB的URL
			String ESB_CODE = "/ESB_FOSS2ESB_SIGN_QUERY_CLAIMINFO";
			//测试用IP上线后注释
			//String IP = "http://10.230.13.54:8180/esb2/rs";

			// 根据服务端的ESB_CODE查到esb地址
			FossConfigEntity fossConfig = fossConfigEntityService
					.queryFossConfigEntityByServerCode(ESB_CODE);
			String esbTPSAddr = fossConfig.getEsbAddr();
			LOGGER.info(esbTPSAddr + ESB_CODE);
			// 设置请求发送的JSON
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("waybillNo", waybillNo);
			String json = jsonObject.toString();
			RequestEntity requestEntity = new StringRequestEntity(json,"application/json", "UTF-8");

			// 构造PostMethod的实例
			PostMethod postMethod = new PostMethod(esbTPSAddr + ESB_CODE);
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");

			HttpClient httpClient = new HttpClient();
			// 设置编码格式
			httpClient.getParams().setContentCharset("UTF-8");
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			LOGGER.info("客户端方法执行的结果值" + statusCode);
			String responseBody = "";
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			LOGGER.info(responseBody);

			// 将返回值转换成实体类
			JSONObject jo = JSONObject.fromObject(responseBody);
			ClaimInfoEntity claimInfoEntity = new ClaimInfoEntity();
			if (jo != null) {
				Object obj = JSONObject.toBean(jo, ClaimInfoEntity.class);

				claimInfoEntity = (ClaimInfoEntity) obj;
				LOGGER.info("查询CRM理赔记录结果 " + claimInfoEntity.getReason());

			}

			// 获取理赔状态 TRUE为理赔中 FALSE为不理赔
			boolean claimStatus = claimInfoEntity.isClaimIsExist();
			return claimStatus;
		} catch (Exception e) {
			throw new SettlementException("查询CRM理赔信息异常，返回的异常信息为: " + e.getMessage());
		}

	}
	/**
	 * @author 378375
	 * @param entity
	 * @return 查询审批中的签收变更单的运单数量
	 * @date 2017年3月7日 21:54:17
	 */
	public Integer getWaybillApprovalCountling(SignRfcEntity entity) {
		// 查询条件
		SignRfcEntity condition = new SignRfcEntity();
		// 设置运单号
		condition.setWaybillNo(entity.getWaybillNo());
		//设置审批状态为审批中
		condition.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
		// 获得运单签收变更、反签收记录
		return signRfcDao.getWaybillApprovalCountling(condition);
	}

	
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public IWaybillSignResultDao getWaybillSignResultDao() {
		return waybillSignResultDao;
	}

	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	public void setQmsErrorService(IQmsErrorService qmsErrorService) {
		this.qmsErrorService = qmsErrorService;
	}
	
	public void setQmsErrorReportService(
			IQmsErrorReportService qmsErrorReportService) {
		this.qmsErrorReportService = qmsErrorReportService;
	}

	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}

	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}

	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}

	public void setDopSignService(IDopSignService dopSignService) {
		this.dopSignService = dopSignService;
	}

	public void setRecordErrorWaybillDao(
			IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}

	public void setPtpSignPartnerService(
			IPtpSignPartnerService ptpSignPartnerService) {
		this.ptpSignPartnerService = ptpSignPartnerService;
	}
}