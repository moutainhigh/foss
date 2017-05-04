package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.foss.QuotaMarkReq;
import com.deppon.esb.inteface.domain.foss.Waybill;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IFimsPdaService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 接送货提供定额发票至发票系统Service
 * @author Foss-105888-Zhangxingwang
 * @date 2014-7-28 13:51:30
 */
public class FimsPdaService implements IFimsPdaService {

	protected final static Logger LOG = LoggerFactory.getLogger(FimsPdaService.class);
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	
	private IActualFreightService actualFreightService;
	/**
	 * 组织服务类
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 系统配置服务类
	 */
	private ISysConfigService pkpsysConfigService;
	
	/**
	 * 小票服务类
	 */
	private IOtherRevenueService otherRevenueService;
	@Override
	public void asynSendOtherRenueInfoToFims(PdaDeliverSignDto dto) {
		//首先看是否开启推送的开关
		if(isStartSendOtherRenueInfoToFims()){
			//请求参数
			QuotaMarkReq request = new QuotaMarkReq();
			//异步发送
			//如果这俩数据都没有，建议重新查询一次
			WaybillEntity entity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(entity != null){
				//开票金额
				request.setAmount(entity.getTotalFee().toString());
				//开票人
				request.setEmpcode(dto.getDriverCode());
				//开票部门
				request.setBillDept(this.queryOrgUnifiedCodeByOrgCode(dto.getSignDeptCode()));
				//开票日期
				if(dto.getSignTime() != null){
					try {
						request.setTicketTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSignTime()));
					} catch (Exception e) {
						request.setTicketTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}
				}
				ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
				/**
				 * 运单数据封装    数据的获取
				 * */
				Waybill w = new Waybill();
				//运单号
				w.setWayBillNo(entity.getWaybillNo());
				//业务类型
				w.setBusinessType(Arrays.asList(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,
				ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,
				ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE).indexOf(entity.getProductCode())>0?"01":"02");
				//预付金额
				w.setPrePayAmount(entity.getPrePayAmount());
				//到付金额
				w.setToPayAmount((entity.getCodAmount()!=null&&entity.getCodAmount().compareTo(BigDecimal.ZERO)>0)?
				entity.getToPayAmount().subtract(entity.getCodAmount()):entity.getToPayAmount());
				//发货方客户编码
				w.setDeliverCustomerCode(w.getDeliverCustomerCode());
				//收货方客户编码
				w.setRereceiveCustomerCode(entity.getReceiveCustomerCode());
				//发货部门编码
				w.setReceiveOrgCode(this.queryOrgUnifiedCodeByOrgCode(entity.getReceiveOrgCode()));
				//到达部门编码
				w.setDescOrgCode(this.queryOrgUnifiedCodeByOrgCode(entity.getCustomerPickupOrgCode()));
				//开单日期
				w.setBillTime(entity.getBillTime());
				//运输路线
				//w.setTransferLine("");
				if(actentity!=null){
					//发票标记
					w.setInvoiceMark(StringUtils.equals(actentity.getInvoice(), 
					SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)?"01":"02");
					//出发催款部门
					w.setReceiveDunningDeptCode(actentity.getStartReminderOrgCode());
					//出发合同部门
					w.setReceiveContractDeptCode(actentity.getStartContractOrgCode());
					//到达催款部门
					w.setDescDunningDeptCode(actentity.getArriveReminderOrgCode());
					//到达合同部门
					w.setDescContractDeptCode(actentity.getArriveContractOrgCode());
				}
				
				request.getWaybills().add(w);
				
				//小票数据封装
				List<OtherRevenueEntity> otherRevenueList = otherRevenueService.queryOtherRevenueByWayBillNO(dto.getWaybillNo());
				if(CollectionUtils.isNotEmpty(otherRevenueList)){
					Waybill wayTemp = null;
					for(OtherRevenueEntity e : otherRevenueList){
						wayTemp = new Waybill();
						//运单号
						wayTemp.setWayBillNo(e.getWaybillNo());
						//小票号
						wayTemp.setOtherRevenueNo(e.getOtherRevenueNo());
						//业务类型
						wayTemp.setBusinessType(Arrays.asList(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,
						ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,
						ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE).indexOf(e.getProductCode())>0?"01":"02");
						//发票标记
						wayTemp.setInvoiceMark(StringUtils.equals(e.getInvoiceMark(), 
								SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)?"01":"02");
						//预付金额
						wayTemp.setPrePayAmount(e.getAmount());
						//到付金额
						//wayTemp.setToPayAmount(e.getAmount());
						//发货方客户编码
						wayTemp.setDeliverCustomerCode(entity.getDeliveryCustomerCode());
						//收货方客户编码
						wayTemp.setRereceiveCustomerCode(StringUtils.equals(e.getGeneratingComCode(),
								e.getCreateOrgCode())?e.getCustomerCode():null);
						//客户名称
						wayTemp.setCustomerName(e.getCustomerName());
						//发货部门编码
						wayTemp.setReceiveOrgCode(this.queryOrgUnifiedCodeByOrgCode(e.getGeneratingOrgCode()));
						//到达部门编码
						wayTemp.setDescOrgCode(this.queryOrgUnifiedCodeByOrgCode(entity.getCustomerPickupOrgCode()));
						//开单日期
						wayTemp.setBillTime(e.getBusinessDate());
						//运输路线
						//wayTemp.setTransferLine("");
						if(actentity!=null){
						//出发催款部门
						w.setReceiveDunningDeptCode(actentity.getStartReminderOrgCode());
						//出发合同部门
						w.setReceiveContractDeptCode(actentity.getStartContractOrgCode());
						//到达催款部门
						w.setDescDunningDeptCode(actentity.getArriveReminderOrgCode());
						//到达合同部门
						w.setDescContractDeptCode(actentity.getArriveContractOrgCode());
					}
						request.getSmallTickets().add(wayTemp);
					}
				}
			}
			//准备消息头信息
			AccessHeader header = createAccessHeader(dto);
			try {
				LOG.info("================消息头："+ReflectionToStringBuilder.toString(header)+"==========");
				LOG.info("================request请求参数："+ReflectionToStringBuilder.toString(request)+"=======", ToStringStyle.SIMPLE_STYLE);
				ESBJMSAccessor.asynReqeust(header, request);
				LOG.info("================消息推送成功:");
			} catch (Exception e) {
				// 对异常进行处理
				LOG.error("Foss推送消息至发票系统失败，错误详情：", e);
			}
		}		
	}
	
	/**
	 * 创建消息头
	 * @param waybillNo
	 * @return
	 */
	private AccessHeader createAccessHeader(PdaDeliverSignDto dto) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		//ESB_FOSS2ESB_RECEIVE_ORDERSTATUS
		LOG.info("接送货推送的发票系统编码为："+DeliverbillConstants.ESB_FOSS2ESB_QUOTA_MARK_CODE+"版本号:"+DeliverbillConstants.ESB_FOSS2ESB_QUOTA_MARK_VERSION);
		header.setEsbServiceCode(DeliverbillConstants.ESB_FOSS2ESB_QUOTA_MARK_CODE);
		//版本随意  1.0
		header.setVersion(DeliverbillConstants.ESB_FOSS2ESB_QUOTA_MARK_VERSION);
		//business id 随意
		header.setBusinessId(dto.getWaybillNo());
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(dto.getWaybillNo());
		return header;
	}
	
	/**
	 * 根据部门编码查询获得标杆编码.查询不到返回部门编码
	 * @param orgCode
	 * @return
	 */
	private String queryOrgUnifiedCodeByOrgCode(String orgCode){
		// 通过综合接口获取部门
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(orgCode); // 部门
		return null == orgEntity ? orgCode:orgEntity.getUnifiedCode();
		
	}
	
	/**
	 * @功能 判断是否已经开启微信开关
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-1-13 21:03:04
	 * @return boolean
	 */
	private boolean isStartSendOtherRenueInfoToFims() {
		boolean start = false;
		// 获取配置参数
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.QUOTA_INVOICE_SWITCH_CODE,FossConstants.ROOT_ORG_CODE);
		if (config != null) {
			//判断是否开启开关
			if (FossConstants.YES.equals(config.getConfValue())) {
				start = true;
			}
		}
		return start;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	
	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}
}
