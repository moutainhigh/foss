package com.deppon.foss.module.pickup.creating.client.common;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.IdentityEffectivePlanVo;
import com.deppon.foss.module.pickup.common.client.vo.LabeledGoodEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValidateVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.CalculateAction;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

public class WaybillValidate {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateAction.class);
	
	//clob通过to_char后最多存储的字符不能超过1300个
	private static final int CLOB_LIMIT = 1337;

	// 日志对象
	protected final static Logger LOG = LoggerFactory.getLogger(AbstractButtonActionListener.class.getName());

	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 运单基础资料服务
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	//运单UI对象
	WaybillEditUI ui;
	
	public WaybillValidate(WaybillEditUI ui){
		this.ui = ui;
	}
		
	/**
	 * 超重对内备注
	 * @param bean
	 */
	private void setInnerNotes(WaybillPanelVo bean){
		//对内备注
		String innerNs=bean.getInnerNotes();
		//储运事项
		String cysx= bean.getTransportationRemark();
		String[] strs=null;
		String[] cysxStrs=null;
		if(StringUtil.isNotEmpty(innerNs)){
		   strs = innerNs.split(";");
		}
		if(StringUtil.isNotEmpty(cysx)){
			cysxStrs = cysx.split(";");
		}
		
		ProductEntityVo pc =bean.getProductCode();
		if(pc!=null && !"AF".equals(pc.getCode())){
			if(bean.getGoodsWeightTotal()==null
					||
				   bean.getGoodsQtyTotal()==null){
					return;
				}
				BigDecimal zzl=bean.getGoodsWeightTotal();
				int zjs=bean.getGoodsQtyTotal();
				if(zjs==0){
					return;
				}
				
				BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
				
				if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
					if(strs!=null){
						StringBuilder newInnerNotes = new StringBuilder();
						for(String str:strs){
							if(str.equals("超重货")){
								continue;
							 }
							newInnerNotes.append(str).append(";");
						}
						 newInnerNotes.append("超重货").append(";");
					   bean.setInnerNotes(newInnerNotes.toString());
						 
					}else{
						bean.setInnerNotes("超重货;");
					}
					
					if(cysxStrs!=null){
						StringBuilder remark = new StringBuilder();
						for(String str:cysxStrs){
							if(str.equals("超重货")){
								continue;
							 }
							remark.append(str).append(";");
						}
						 remark.append("超重货").append(";");
					   bean.setTransportationRemark(remark.toString());
						 
					}else{
						bean.setTransportationRemark("超重货;");
					}
						
				}else{
					if(strs!=null){
						StringBuilder innerNs1 = new StringBuilder();
						for(String str :strs){
							if(str.equals("超重货")){
								continue;
							}
						   innerNs1.append(str).append(";");
						}
					  bean.setInnerNotes(innerNs1.toString());
					}
					
					if(cysxStrs!=null){
						StringBuilder cysx2 = new StringBuilder();
						for(String str :cysxStrs){
							if(str.equals("超重货")){
								continue;
							}
							cysx2.append(str).append(";");
						}
					  bean.setTransportationRemark(cysx2.toString());
					}
				}
		}else{
			
			if(strs!=null){
				StringBuilder innerNs2 = new StringBuilder();
				for(String str :strs){
					if(str.equals("超重货")){
						continue;
					}
					innerNs2.append(str).append(";");
				}
			  bean.setInnerNotes(innerNs2.toString());
			}
			
			if(cysxStrs!=null){
				StringBuilder cysx2 = new StringBuilder();
				for(String str :cysxStrs){
					if(str.equals("超重货")){
						continue;
					}
					cysx2.append(str).append(";");
				}
			  bean.setTransportationRemark(cysx2.toString());
			}
		}
	}

	private void checkWaybillAndPendingOrderNo(String orderNumber) {
		if (!(StringUtils.isNotBlank(ui.pictureWaybillEditUI.getPictureOperateType()) 
				&& "VIEWORMODIFY".equals(ui.pictureWaybillEditUI.getPictureOperateType()))
				&& waybillService.checkWaybillAndPendingOrderNo(orderNumber)) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderRepeat"));
		}
	}
	
	
	/**
	 * TODO 该方法为历史遗留的方法， 虽然放在common中 单只在图片开单提交验证时候用到；  集中开单、营业部
	 * 开单请斟酌使用  
	 * @param bean
	 */
	public void validate(WaybillPanelVo bean) {
		
		//保价费
		if(new Long(bean.getInsuranceFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)).longValue()).toString().length() > NumberConstants.NUMBER_8){
			ui.incrementPanel.getTxtInsuranceValue().requestFocus();
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.overMaxInsuranceFee"));
		}
		
		
		//DMANA-4292   开单省市区校验
		Common.validateCity(bean);
		
		//验证收货地址
		validateReceiveAddress(bean);	
				
		// 客户校验
		validateCustomer(bean);
				
		//对内备注
		setInnerNotes(bean);
		
		//保价费率
		insuranceRateListener(bean);
				
		//发票标记
		CommonUtils.setInvoiceType(bean,new Date());
				
		//验证整车 统一结算 --发票标记 与 统一结算 无关联 --sangwenhao-272311
//		validateWholeCarSettlement(bean);
		//货物名称校验
		waybillGoogNameCheck(bean);
		
		if(!bean.getIsWholeVehicle()){
			//验证提货方式与合票方式
			validatePickupOrgCode(bean);
		}		
		// PDA运单信息
		validateWaybillNo(bean);
		
		// 整车约车校验
		validateVehicleNumber(bean);

		//打木托校验 zxy 20131118 ISSUE-4391
		validEnterYoke(bean);
		// 件数校验
		validateWeightVolume(bean);

		// 目的站校验
		validateDistination(bean);

		// 产品校验
		validateProduct(bean);

		// 包装校验
		validatePack(bean);
		//包装信息不能大于50字符
		validateGoodPackage(bean.getGoodsPackage());
		//手机及电话号码必须录入一个 
		validatePhone(bean);

		//zxy 20140101 MANA-409 start 新增:验证保价声明价值
		validateInsurance(bean);
		//zxy 20140101 MANA-409 end 新增:验证保价声明价值
		// 校验提货网点重量、体积上限
		validateVW(bean);
		
		//提货方式校验
		validateReceiveMethod(bean);

		// 付款方式校验
		validatePaymentMode(bean);
		
		// 代收货款校验
		validateCod(bean);

		// 验证空运合票方式和航班类型不能为空
		validateAir(bean);

		// 验证空运货物类型不能为空
		validateAirGoodsType(bean);
		
		/**
		 * 改校验放在validateProductPLF方法中去
		 * @author Foss-278328-hujinyang
		 * @time 20151225 15:59
		 */
		//验证空运业务-机场自提
//		validateAirKY(bean);

		// 只允许合同客户才可以选择免费送货
		Common.validateDeliverFree(bean, ui);
		//当发货客户【是否统一结算】为【是】的时候，付款方式只能为【月结】或者【临欠】
		Common.validatePayMethod(bean);
		
		//校验时效时否正确
		/**
		 * 目的站中已经校验  在不需要再次校验  
		 * @author Foss-278328-hujinyang
		 * @date 20151224 19:28
		 */
//		validateEffectivePlan(bean);
		// 验证配载线路
		validateFreightRout(bean);
		//验证自提件类型
		validateEconomyGoodsType(bean);
		
		//liding comment
		//NCI项目,付款方式银行卡时取消校验
		/**
		 * 验证交易流水号是否满足开单要求
		 * @author:Foss-278328-hujinyang
		 * @date:20160106 18:02
		 */
		//Common.validateTransactionSerialNumber(bean, ui);
		
		/**
		 * 改方法只有图片开调用，所以不会出现集中开单组和普通开单的检查
		 * @author Foss-278328-hujinyang
		 * @date 20151224 19:28
		 */
		//集中开单组和普通开单的检查
//		validateCenterDelivery(bean);		
		
		//校验营销活动是否开启
		Common.validateActiveStart(bean);
		
		//校验优惠券是否开启
		Common.validatePromotionsCode(bean);
		//验证偏线的保险声明价值
		validateProductPLF(bean);

		//验证退款类型
		validateRefundType(bean);
		
		/**
		 * 已经与业务核实过不用再校验司机工号
		 * @author Foss-278328-hujinyang
		 * @Date 20151226 15:02
		 */
		//在上门接货的时候 司机工号必填
//		validateDriverNo(bean);
		
		//验证预付金额是否为负数
		validatePrePayAmountFee(bean);
		identityPayment(bean);
		/**
		 * @author 200945-wutao
		 * @date 2014-08-26
		 * 检验是否进行开箱验货
		 */
		//validateUnpackInspection(bean);
		//校验CRM推广活动是否在有效时间范围内
		Common.validateCrmActiveInfo(bean);

		//验证香港地区必须勾选商业区或者住宅区
		//Common.validateBusinessZoneResidentialDistrict(ui, bean);
		
		/**
		 * 已经与业务核实过不用再校验司机工号
		 * @author Foss-278328-hujinyang
		 * @Date 20151224 18:58
		 */
//		validatePickupToDoor(bean);
		
		//验证订单号是否被占用
		if(!StringUtils.isBlank(bean.getOrderNo()))
		{
			checkWaybillAndPendingOrderNo(bean.getOrderNo());
		}	
		
	}
	

	/**
	 * 
	 * 手机及电话号码必须录入一个 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午03:49:00
	 */
	private void validatePhone(WaybillPanelVo bean) {
		if(StringUtils.isEmpty(bean.getReceiveCustomerMobilephone()) 
				&& StringUtils.isEmpty(bean.getReceiveCustomerPhone())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}
		
		if(StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone()) 
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}
	}

	/**
	 * 
	 * 验证空运业务
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 上午09:19:51
	 */
	private void validateAirKY(WaybillPanelVo bean)
	{
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.one"));
				}
			}
			
			if(bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0)
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.two"));
			}
		}
	}
	
	
	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProductPLF(WaybillPanelVo bean) {
		//判断产品是否偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())) {
			BigDecimal insuranceAmount = bean.getInsuranceAmount();
			BigDecimal maxInsuranceAmount = bean.getMaxInsuranceAmount();
			if(insuranceAmount == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullInsuranceAmount"));
			}
			if(maxInsuranceAmount == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMaxInsuranceAmount"));
			}
			if (insuranceAmount.compareTo(maxInsuranceAmount) > 0) {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.overMaxInsuranceAmount") + maxInsuranceAmount);
			}
		}
		
		
		//判断产品是否空运
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
			//合票方式
//			DataDictionaryValueVo freightMethod = bean.getFreightMethod();
//			if(freightMethod == null)
//			{
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFreightMethod"));
//			}
			
			if (bean.getFlightNumberType() == null || bean.getFlightNumberType().getValueCode() == null) {
		        throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FLIGHTNUMBERTYPE));
		    }

	        if (bean.getFreightMethod() == null || bean.getFreightMethod().getValueCode() == null) {
	            throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FREIGHTMETHOD));
	        }
			
			//航班类别
//			DataDictionaryValueVo flightNumberType = bean.getFlightNumberType();
//			if(flightNumberType == null)
//			{
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFlightNumberType"));
//			}
		}
		
		
	}
	/**
	 * @author 200945
	 * @param bean
	 */
	private void validateWholeCarSettlement(WaybillPanelVo bean) {
		// TODO Auto-generated method stub
		if(WaybillConstants.INVOICE_01.equals(bean.getInvoice()) 
				&&  WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean.getStartCentralizedSettlement())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillRFCUI.alterInvoice"));
		}
	}
	private void waybillGoogNameCheck(WaybillPanelVo bean) {
		 if(StringUtil.isEmpty(bean.getGoodsName())){
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) &&
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				bean.setGoodsName(ui.getPictureCargoInfoPanel().getTxtGoodsName().getText());
			}else{
				bean.setGoodsName(ui.getCargoInfoPanel().getTxtGoodsName().getText());
			}
		 }
	}

	/**
	 * <p>
	 * 判断付款方式是否符合业务规则
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2013-1-16 下午4:17:51
	 * @param bean
	 * @see
	 */
	private void identityPayment(WaybillPanelVo bean) {
		Object object = null;
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			object = ui.pictureTransferInfoPanel.getCombProductType().getSelectedItem();
		}else{
			object = ui.transferInfoPanel.getCombProductType().getSelectedItem();
		}
		
		if(object != null){
			// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				if (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel()) && !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean.getOrderPayment())) {
//					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.identityPayment.one"));
					if(!StringUtils.isBlank(bean.getOrderNo())){
						setPaidMethod(bean);
					}
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.identityPayment.one"));
				}
			}
			
			ProductEntityVo productEntityVo = null;
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				productEntityVo = (ProductEntityVo) ui.pictureTransferInfoPanel.getCombProductType().getSelectedItem();
			}else{
				productEntityVo = (ProductEntityVo) ui.transferInfoPanel.getCombProductType().getSelectedItem();
			}
			// 付款方式为临时欠款、到付 不允许勾选预付费保密
			// 产品为汽运偏线 不允许勾选预付费保密
			if (//WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())
					WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())
					|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productEntityVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntityVo.getCode())) {
				ui.incrementPanel.getChbSecrecy().setEnabled(false);
				ui.incrementPanel.getChbSecrecy().setSelected(false);
			} else {
				ui.incrementPanel.getChbSecrecy().setEnabled(true);
			}
			
			/**
			 * 与validatePaymentMode中该验证重复 
			 * @author Foss-278328-hujinyang
			 * @time 20151225 12:03
			 */
			/*//临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
			if(WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())){
				//判断客户编码是否为空
				if(StringUtil.isEmpty(bean.getDeliveryCustomerCode())){
					MsgBox.showError(i18n.get("foss.gui.creating.listener.Waybill.MsgBox.payment"));
				}
			}*/
			
			if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				 
				CusBargainVo vo=new CusBargainVo();
				vo.setChargeType(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(new Date());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				boolean  isOk = waybillService.isCanPaidMethod(vo);
				if(!isOk){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.NocanPaidMethod"));
				}
			}
		}
	}
	
	
	/**
	 * 
	 * 设置付费方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setPaidMethod(WaybillPanelVo bean) {
		int size = ui.getCombPaymentModeModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getCombPaymentModeModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setPaidMethod(vo);
			}
		}
	}

	
	/**
	 * 
	 * @author 200945-wutao
	 * @param bean
	 * 开箱验货业务逻辑处理
	 */
	public void validateUnpackInspection(WaybillPanelVo bean) {
		//此处判断是否是集中开单，如果是集中开单，那么就不执行此验证
		if(bean.getPickupCentralized()!=null && bean.getPickupCentralized()){
			return;
		}
		//此count用于判定当到达区域在基础数据中有数据，提醒后，始发区域在基础数据也有数据，那么，始发区域就不进行提醒，否则，进行提醒
		int count = 0;
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
		// 偏线汽运和精准空运
		if(bean.getProductCode().getCode().equals(WaybillConstants.AIR_FLIGHT) || bean.getProductCode().getCode().equals(WaybillConstants.HIGHWAYS_REFERRALS)){
			//偏线或空运的始发和到达区域查询，此处数据从偏线表中获取
			OuterBranchEntity outerBranchEntityArr = BaseDataServiceFactory.getBaseDataService().queryOuterBranchByBranchCode(bean.getCustomerPickupOrgCode().getCode(),null);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityStart = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getReceiveOrgCode());
			//先进行到达区域判断，如果到达区域在开箱验货的基础资料中没有找到对应的数据，那么进行始发区域的判断。即走else中的代码
			 if(outerBranchEntityArr != null){
				orgAdministrativeInfoEntity.setProvCode(outerBranchEntityArr.getProvCode());
				orgAdministrativeInfoEntity.setCityCode(outerBranchEntityArr.getCityCode());
				orgAdministrativeInfoEntity.setCountyCode(outerBranchEntityArr.getCountyCode());
				count = unPackInspectionArr(orgAdministrativeInfoEntity);
			}
			 //此处代码进行当前营业部，即收货部门或始发部门。该处的数据从组织表中读取
			 if(count == 0){
				 if(orgAdministrativeInfoEntityStart != null){
						unPackInspectionStart(orgAdministrativeInfoEntityStart);
					} 
			 }	 
		}else{
			//其他的运输类型
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityArr = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getCustomerPickupOrgCode().getCode());
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityStart = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getReceiveOrgCode());
			if(orgAdministrativeInfoEntityArr != null){
				count = unPackInspectionArr(orgAdministrativeInfoEntityArr);
			}
			if(count == 0){
				if(orgAdministrativeInfoEntityStart != null){
					unPackInspectionStart(orgAdministrativeInfoEntityStart);
				}
			}	
		}	
	}
	
	/**
	 * 
	 * @author 200945-wutao
	 * @param orgAdministrativeInfoEntity
	 * 提示开箱验货：获取发货部门和收货部门所在的省市区，与基础数据进行匹配如果匹配成功，则提示：开箱验货
	 * @date 2014-09-10
	 * 此方法是到达区域进行开箱验货提醒
	 */
	private int unPackInspectionArr(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		int count = 0;
		BillInspectionRemindEntity entityArrProv = BaseDataServiceFactory
				.getBaseDataService().queryBillInspectionRemindByRegionCode(
						orgAdministrativeInfoEntity,
						WaybillConstants.ARRIVE_REGION_CODE,
						WaybillConstants.DISTRICT_PROVINCE_CODE);
		if (null != entityArrProv) {
			JOptionPane.showMessageDialog(null, entityArrProv.getRegionName()
					+ "到达区域货物,请务必开箱验货！", "提示", JOptionPane.WARNING_MESSAGE);
			count++;
		} else {
			BillInspectionRemindEntity entityArrtCity = BaseDataServiceFactory
					.getBaseDataService()
					.queryBillInspectionRemindByRegionCode(
							orgAdministrativeInfoEntity,
							WaybillConstants.ARRIVE_REGION_CODE,
							WaybillConstants.DISTRICT_CITY_CODE);
			if (null != entityArrtCity) {
				JOptionPane.showMessageDialog(null,
						entityArrtCity.getRegionName() + "到达区域货物,请务必开箱验货！",
						"提示", JOptionPane.WARNING_MESSAGE);
				count++;
			} else {
				BillInspectionRemindEntity entityArrCounty = BaseDataServiceFactory
						.getBaseDataService()
						.queryBillInspectionRemindByRegionCode(
								orgAdministrativeInfoEntity,
								WaybillConstants.ARRIVE_REGION_CODE,
								WaybillConstants.DISTRICT_COUNTY_CODE);
				if (null != entityArrCounty) {
					JOptionPane
							.showMessageDialog(null,
									entityArrCounty.getRegionName()
											+ "到达区域货物,请务必开箱验货！", "提示",
									JOptionPane.WARNING_MESSAGE);
					count++;
				}
			}
		}
		return count;

	}
	/**
	 * 
	 * @author 200945-wutao
	 * @param orgAdministrativeInfoEntity
	 * 提示开箱验货：获取发货部门和收货部门所在的省市区，与基础数据进行匹配如果匹配成功，则提示：开箱验货
	 * @date 2014-09-10
	 * 此方法始发区域进行判断
	 */
	public void unPackInspectionStart(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		// 发货部门（收货部门，始发区域）
		BillInspectionRemindEntity entityStartProv = BaseDataServiceFactory
				.getBaseDataService().queryBillInspectionRemindByRegionCode(
						orgAdministrativeInfoEntity,
						WaybillConstants.START_REGION_CODE,
						WaybillConstants.DISTRICT_PROVINCE_CODE);
		if (null != entityStartProv) {
			JOptionPane.showMessageDialog(null, entityStartProv.getRegionName()
					+ "始发区域货物,请务必开箱验货！", "提示", JOptionPane.WARNING_MESSAGE);
		} else {
			BillInspectionRemindEntity entityStartCity = BaseDataServiceFactory
					.getBaseDataService()
					.queryBillInspectionRemindByRegionCode(
							orgAdministrativeInfoEntity,
							WaybillConstants.START_REGION_CODE,
							WaybillConstants.DISTRICT_CITY_CODE);
			if (null != entityStartCity) {
				JOptionPane.showMessageDialog(null,
						entityStartCity.getRegionName() + "始发区域货物,请务必开箱验货！",
						"提示",

						JOptionPane.WARNING_MESSAGE);
			} else {
				BillInspectionRemindEntity entityStartCounty = BaseDataServiceFactory
						.getBaseDataService()
						.queryBillInspectionRemindByRegionCode(
								orgAdministrativeInfoEntity,
								WaybillConstants.START_REGION_CODE,
								WaybillConstants.DISTRICT_COUNTY_CODE);
				if (null != entityStartCounty) {
					JOptionPane.showMessageDialog(null,
							entityStartCounty.getRegionName()
									+ "始发区域货物,请务必开箱验货！", "提示",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
		
	
	/**
	 * 验证打木托
	 * ISSUE-4391
     * @author	157229-zxy 
     * @date 2013-11-18
	 */
	private void validEnterYoke(WaybillPanelVo bean) {
		//当从未弹出过打木托对话框这个值则是null
		if(ui.getDialog() == null)
			return ;
		// 打木托件数
		BigDecimal salverGoodsPieces = new BigDecimal(
				StringUtils.defaultIfBlank(ui.getDialog()
						.getTxtSalverGoodsPieces().getText(), "0"));
		Object[] salverValues = ui.getDialog().listSalver.getSelectedValues();
		if (salverValues != null
				&& (salverGoodsPieces.compareTo(new BigDecimal(
						salverValues.length)) > 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.woodListener.eight"));
		}
		if (salverValues != null && salverValues.length > 0
				&& (salverGoodsPieces.compareTo(BigDecimal.ZERO) <= 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.woodListener.night"));
		}
		// 流水号类型置空
		Common.refreshLabeledPackageType(bean);
		if (salverValues != null) {
			for (int i = 0; i < salverValues.length; i++) {
				LabeledGoodEntityVo vo = (LabeledGoodEntityVo) salverValues[i];
				vo.getEntity().setPackageType(
						WaybillConstants.PACKAGE_TYPE_SALVER);
			}
		}
	}
	/**
	 * 集中开单组和普通开单的检查
	 * @param bean
	 */
	private void validateCenterDelivery(WaybillPanelVo bean) {
		if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui.getWaybillType())){
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			if(!dept.checkSaleDepartment()){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.departmentError"));	
			}
		}
		
		
	}

	/**
     * 调整保价费率范围
     * @param bean
     */
	private void insuranceRateListener(WaybillPanelVo bean) {
		//最低保价费率
		BigDecimal minFeeRate = bean.getMinFeeRate();
		//最高保价费率
		BigDecimal maxFeeRate = bean.getMaxFeeRate();
		//调整保价费率
		BigDecimal insuranceRate = bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000));
		//报价声明价值
		BigDecimal insuranceAmount =bean.getInsuranceAmount();
		if(minFeeRate!=null && maxFeeRate!=null){
			if(insuranceRate.compareTo(minFeeRate)<0
					 ||
			   insuranceRate.compareTo(maxFeeRate)>0){
				if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
					String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
					String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
					if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
							&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
						ui.incrementPanel.getBtnCalculate().setEnabled(true);
						ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
						ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
						ui.incrementPanel.getJlable().setVisible(false);
						ui.incrementPanel.getCombServiceRate().setVisible(false);
					}else{
						ui.incrementPanel.getBtnCalculate().setEnabled(false);
						ui.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
						ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
						ui.incrementPanel.getJlable().setVisible(true);
						ui.incrementPanel.getCombServiceRate().setVisible(true);
					}

				}else{
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
					ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
				}
				minFeeRate=minFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
				maxFeeRate=maxFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.Outrange",new Object[]{insuranceAmount,minFeeRate+"‰",maxFeeRate+"‰"}));
			}
		}
		
	}
	
	/**
	 * 校验是否自提件
	 * @author WangQianJin
	 * @date 2013-08-21
	 */
	private void validateEconomyGoodsType(WaybillPanelVo bean){
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods()){
			//如果是自提件，并且自提件类型为空，则给予提示
			if(bean.getEconomyGoodsType()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.economygoodstype.isnull"));	
			}else{
				if(bean.getEconomyGoodsType().getValueCode()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.economygoodstype.isnull"));
				}
			}			
		}
	}
	
	/**
	 * 再检验一次时交是否正确
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-18 下午5:50:13
	 */
	private void validateEffectivePlan(WaybillPanelVo bean){
		if (bean.getCustomerPickupOrgCode() != null) {	
			IdentityEffectivePlanVo vo = null;
			//先判定是否开启偏线时效方案的开关
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())){
				ConfigurationParamsEntity entity = baseDataService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.OUTER_EFFECTIVE_VERIFIED_CODE,FossConstants.ROOT_ORG_CODE);
				if (entity != null) {
					//判断是否开启开关
					if (FossConstants.YES.equals(entity.getConfValue())) {
						vo = Common.identityOuterEffectivePlan(bean);
						if(!vo.isExist()){
							LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
							throw new WaybillValidateException(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
						}
					}
				}
			}else if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())
					&& !ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode())) {
				vo = Common.identityEffectivePlan(bean);
				if(!vo.isExist()){
					LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
					throw new WaybillValidateException(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
				}
			}
		}
	}	
	/**
	 * 校验运单号
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-28 下午3:16:20
	 */
	private void validateWaybillNo(WaybillPanelVo bean){
		//导入前的运单号
		String oldWaybill = StringUtil.defaultIfNull(bean.getOldWaybillNo());
		//导入后的运单号
		String newWaybill = StringUtil.defaultIfNull(bean.getWaybillNo());
		//运单状态
		String pendingType = StringUtil.defaultIfNull(bean.getWaybillstatus());
		
		
		//PDA补录运单不能修改运单号
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType) && !oldWaybill.equals(newWaybill)) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.pda.import",new Object[]{oldWaybill,newWaybill}));
		}
	}
	
	/**
	 * 校验提货方式与合票方式等
	 * @author WangQianJin
	 * @date 2013-07-10
	 */
	private void validatePickupOrgCode(WaybillPanelVo bean){
		ValidateVo vo=new ValidateVo();
		if(bean.getCustomerPickupOrgCode()!=null){
			vo.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());	
		}
		if(bean.getProductCode()!=null){
			vo.setProductCode(bean.getProductCode().getCode());
		}
		if(bean.getReceiveMethod()!=null){
			vo.setReceiveMethod(bean.getReceiveMethod().getValueCode());
		}
		if(bean.getFreightMethod()!=null){
			vo.setFreightMethod(bean.getFreightMethod().getValueCode());
		}
		//验证提货网点选择是否正确
		CommonUtils.validatePickupOrgCode(vo);
	}

    /**
     * 
     * 验证返单会被绕过去
     * @author 025000-FOSS-helong
     * @date 2013-4-24
     */
	private void validateReturnBill(WaybillPanelVo bean){
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode()))
		{
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> data = model.getData();
			Boolean bool = true;
			if(data != null && !data.isEmpty())
			{
				for(OtherChargeVo vo : data)
				{
					if(PriceEntityConstants.PRICING_CODE_QS.equals(vo.getCode()))
					{
						bool = false;
						break;
					}
				}
			}
			if(bool)
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateReturnBill"));
			}
		}
	}

	/**
	 * 
	 * 验证空运货物类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-7 下午05:57:27
	 */
	private void validateAirGoodsType(WaybillPanelVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if (bean.getAirGoodsType() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_AIRGOODSTYPE));
			}
		}
	}

	/**
	 * 
	 * 验证空运合票方式和航班类型不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-20 下午05:47:47
	 */
	private void validateAir(WaybillPanelVo bean) {

		ProductEntityVo productVo = bean.getProductCode();
		// 只有空运才需要进行校验
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if (bean.getFlightNumberType() == null || bean.getFlightNumberType().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FLIGHTNUMBERTYPE));
			}

			if (bean.getFreightMethod() == null || bean.getFreightMethod().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FREIGHTMETHOD));
			}
		}

	}

	/**
	 * 
	 * 验证整车业务规则
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午05:08:10
	 * @param bean
	 */
	private void validateVehicleNumber(WaybillPanelVo bean) {
		if (bean.getIsWholeVehicle()) {
			if (StringUtils.isEmpty(bean.getVehicleNumber())) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.null_VehicleNumber));
			}
		}
	}

	/**
	 * 
	 * 验证重量、体积、件数不能为默认值0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午08:16:30
	 */
	private void validateWeightVolume(WaybillPanelVo bean) {
		if(StringUtils.isBlank(bean.getGoodsName())){
			ui.pictureCargoInfoPanel.getTxtWeight().requestFocus();
			throw new WaybillValidateException(i18n.get("foss.gui.creating.descriptor.Waybill.goodsName.null"));
		}
		if (null ==bean.getGoodsQtyTotal() || bean.getGoodsQtyTotal().intValue() == 0 || bean.getGoodsQtyTotal().intValue()<0 ) {
			ui.pictureCargoInfoPanel.getTxtTotalPiece().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSQTYTOTAL));
		}
	}

	/**
	 * 
	 * 代收货款业务规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 上午11:35:22
	 */
	private void validateCod(WaybillPanelVo bean) {

		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal codAmount = bean.getCodAmount();

		// 代收货款不能小于0
		if(codAmount != null && codAmount.compareTo(zero) < 0){
			throw new WaybillValidateException("代收货款不能小于0");
		}
		
		if (codAmount == null || codAmount.compareTo(zero) == 0) {

			DataDictionaryValueVo vo = bean.getRefundType();
			if (vo != null && vo.getValueCode() != null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.one") + vo.getValueName() + i18n.get("foss.gui.creating.calculateAction.exception.validateCod.two"));
			}
			/**
			 * 判断是否为整车类型并且提货方式是否为机场自提，
			 * 如果不是，才给与没有代收货款提示
			 */
			if (ui.incrementPanel.getCombRefundType().isEnabled()&&!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode()) && !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmValidate"), i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					ui.incrementPanel.getTxtCashOnDelivery().requestFocus();
					// 将退款类型设置为空
					Common.setRefundType(bean, ui);
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.three"));
				}
			}
		} else {
			if(!bean.getIsWholeVehicle())
			{
				if(!FossConstants.YES.equals(bean.getCanAgentCollected()))
				{
					throw new WaybillValidateException("对不起，您选择的网点不允许开代收货款！");
				}
			}

			Common.validateBankInfo(bean, ui, waybillService);

			//DP-FOSS zhaoyiqing 20161026 开单配合CUBC校验银行信息
			Common.validateBankInfoCUBC(bean);
			
			/**
			 * 为退款类型重新赋值，因为当发货客户是非公司会员客户时，第一次选择退款类型会清空，当再次选择此退款类型时，bean中的退款类型并没有并没有改变，还是空。
			 */
			if(ui.incrementPanel.getCombRefundType().getSelectedItem()!=null){
				DataDictionaryValueVo refundType=(DataDictionaryValueVo)ui.incrementPanel.getCombRefundType().getSelectedItem();
				bean.setRefundType(refundType);
			}
			if (bean.getRefundType() == null || bean.getRefundType().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.four"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName()) || StringUtils.isEmpty(bean.getAccountCode()) || StringUtils.isEmpty(bean.getAccountBank())) {
						if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
							throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.five"));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 判断包装件数不能大于开单件数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 上午10:04:36
	 */
	private void validatePack(WaybillPanelVo bean) {
		Integer qtyTotal = bean.getGoodsQtyTotal();// 总件数

		// 木架信息判空操作
		if (null == bean.getPaper() || null == bean.getWood() || null == bean.getFibre() || null == bean.getSalver() || null == bean.getMembrane()) {
			// 记录异常日志信息
			LOG.error(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
			// 抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
		}

		// 木架信息判空操作
		if (0 == bean.getPaper().intValue() && 0 == bean.getWood().intValue() 
				&& 0 == bean.getFibre().intValue() && 0 == bean.getSalver().intValue() 
				&& 0 == bean.getMembrane().intValue() && StringUtils.isEmpty(bean.getOtherPackage())) {
			// 记录异常日志信息
			LOG.error(i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
			// 抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
		}

		Integer paper = Integer.valueOf(bean.getPaper());// 纸
		Integer wood = Integer.valueOf(bean.getWood());// 木
		Integer fibre = Integer.valueOf(bean.getFibre());// 纤
		Integer salver = Integer.valueOf(bean.getSalver());// 托
		Integer membrane = Integer.valueOf(bean.getMembrane());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));

		}
	}
	
	/**
	 * 
	 * 验证合票方式如果是单独开单，则只能选机场自提
	 * 
	 * @author WangQianJin
	 * @date 2013-07-08 下午03:21:42
	 */
	private void validateReceiveMethod(WaybillPanelVo bean) {		
		/**
		 * 判断合票方式和运输性质和提货方式是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null && bean.getReceiveMethod()!=null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运和提货方式是否为机场自提
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())
					&& !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				//空运单独开单，提货方式只能是机场自提！
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.exception.validatereceivemethod"));
			} 
		}
         //自提，送货费不能编辑
		if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			    ui.billingPayPanel.getTxtDeliveryCharge().setEditable(false);
		}	
	}

	/**
	 * 
	 * 验证提货方式为机场自提时，不能开到付
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午03:21:42
	 */
	private void validatePaymentMode(WaybillPanelVo bean) {
		
		
		
		if (bean.getPaidMethod() == null) {
			ui.incrementPanel.getCombPaymentMode().requestFocus();
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullPaidMethod"));
		}
		/**
         * 	若为月结合同，则结算方式可以选择月结，注明校验适用营业部时按照主客户合同的适用部门进行校验（主客户月结合同的归属部门和绑定部门)
         * 	如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
         * 	如果主客户当前有效合同为统一结算，则开单选择结款方式只能为月结和到付；
		 */
		if( 	
				StringUtil.isNotBlank(bean.getIsCustCircle()) && 
				StringUtil.equals("Y", bean.getIsCustCircle()) && 
				bean.getCustomerCircleEntity() != null &&
						bean.getCusBargainNewEntity() != null  &&
								bean.getCustomerNewEntity() !=null
						){
			//获取 月结方式 
			String chargeType=bean.getCusBargainNewEntity().getChargeType();
			//如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
			if(bean.getCusBargainNewEntity()==null || (StringUtils.isBlank(chargeType) || StringUtils.equals("NOT_MONTH_END", chargeType))){
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					//给客户提示抛出结算方式不能选择月结
					throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillBillder.common.alterPaidMethod"));
				}
			}
		}
		//图片开单如果重量体积为0生成待补录,提交校验
		//网上支付校验（官网以及裹裹订单可以开网上支付），151211,20160904
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
				.getValueCode())) {
			if (StringUtils.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.paidMethod.onlinePayNoCustCode"));
			}
			// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付,此处过滤出裹裹订单的情况，使用WaybillConstants.SERVICE_TYPE进行过滤
			  if (!WaybillConstants.SERVICE_TYPE.equals(bean.getServerType()) && (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel()) || !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean.getOrderPayment()))) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.paidMethod.onlinePayForDeppon"));
			}
		}
		//只有月结客户才能开月结
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			// 判断客户是否月结
			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
			}
		}

		//提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.one"));
				}
			}

			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.two"));
			}
		}
		
		if(!bean.getIsWholeVehicle())
		{
			//提货网点是否可以货到付款
			if(!FossConstants.YES.equals(bean.getArriveCharge()))
			{
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.noArrivedPay"));
				}
			}
		}


		// 空运以及偏线无法选择网上支付
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				throw new WaybillValidateException(productVo.getName() + i18n.get("foss.gui.creating.calculateAction.exception.unableOnlinePayment"));
			}
		}

		/**
		 * 临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		 */
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
			//判断客户编码是否为空
			if (StringUtil.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillSubmitException(i18n.get("foss.gui.creating.calculateAction.exception.paymentLQ"));
			}
		}

	}

	/**
	 * 
	 * 校验手机号码与电话号码必须有一个不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-3 上午11:15:38
	 */
	private void validateCustomer(WaybillPanelVo bean) {
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode()))
		{
			if(StringUtils.isEmpty(bean.getDeliveryCustomerContact()))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.innerPickUp.deliveryCustomer"));
			}
			
			if(StringUtils.isEmpty(bean.getReceiveCustomerContact()))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.innerPickUp.receiveCustomer"));
			}
		}else
		{
			if(StringUtils.isEmpty(bean.getDeliveryCustomerContact()))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.deliveryCustomer"));
			}
			
			if(StringUtils.isEmpty(bean.getReceiveCustomerContact()))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.receiveCustomer"));
			}
		}
		
		if (StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())) {
			String mobilePhone = bean.getDeliveryCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor().validateDeliveryCustomerMobilephone(mobilePhone, bean);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					ui.consignerPanel.getTxtConsignerMobile().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillDescriptor.deliver.mobile.format.error"));
				}
			}

		}

		if (StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())) {
			String mobilePhone = bean.getReceiveCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor().validateDeliveryCustomerMobilephone(mobilePhone, bean);
				mobilePhone = mobilePhone.substring(0, 1);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					ui.consigneePanel.getTxtConsigneeMobile().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillDescriptor.receiver.mobile.format.error"));
				}
			}
		}

		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorPhoneOrTel"));
		}

		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone()) && StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneePhoneOrTel"));
		}
		
		//校验收货联系人的电话
		if (StringUtils.isNotBlank(bean.getReceiveCustomerPhone())) {
			String result = new WaybillDescriptor()
					.validateReceiveCustomerPhone(bean
							.getReceiveCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consigneePanel.getTxtConsigneePhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
			}
		}
		// 校验发货联系人的电话
		if (StringUtils.isNotBlank(bean.getDeliveryCustomerPhone())) {
			String result = new WaybillDescriptor()
					.validateDeliveryCustomerPhone(bean
							.getDeliveryCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consignerPanel.getTxtConsignerPhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
			}
		}

		// 原件签收单返回
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
			JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
			AddressFieldDto consignerAddress = jd.getAddressFieldDto();
//			AddressFieldDto consignerAddress = ui.consignerPanel.getTxtConsignerArea().getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText()) || consignerAddress==null || StringUtils.isEmpty(consignerAddress.getProvinceId()) 
					|| StringUtils.isEmpty(consignerAddress.getProvinceName()) 
					|| StringUtils.isEmpty(bean.getDeliveryCustomerAddress())) {
				ui.consignerPanel.getTxtConsignerArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
			}
		}

		if(bean.getReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.transferInfoPanel.receiveMethod.isNull"));
		}
		/**
		 * 已存在对提货方式的验证方式
		 * @author Foss-278328-hujinyang 
		 * @time 20151224 19:46
		 */
		// 提货方式
		/*String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_FREE.equals(code) || WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_UP.equals(code) || WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) || WaybillConstants.DELIVER_UP_AIR.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			AddressFieldDto consigneeAddress = ui.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
			if (consigneeAddress==null || StringUtils.isEmpty(consigneeAddress.getProvinceId()) 
					|| StringUtils.isEmpty(consigneeAddress.getProvinceName())|| StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}*/
		
		//如果不是导入订单开单,则校验省市区是否规范，因为订单省市区业务规则与FOSS不一致，因此不做校验（MANA-1598）
		if(StringUtils.isEmpty(bean.getOrderNo())){
			//判断发货人省市区文本框是否为空
			String regionNerText=ui.consignerPanel.getTxtConsignerArea().getText();		
			if(StringUtils.isNotEmpty(regionNerText)){				
				//判断是否为选择的省市区
				if(regionNerText.indexOf("-")==-1){
					//请选择正确的发货人省市区
					throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.regionAddress.alert"));
				}
			}
			//判断收货人省市区文本框是否为空			
			String regionNeeText=ui.consigneePanel.getTxtConsigneeArea().getText();			
			if(StringUtils.isNotEmpty(regionNeeText)){				
				//判断是否为选择的省市区
				if(regionNeeText.indexOf("-")==-1){
					//请选择正确的收货人省市区
					throw new WaybillValidateException(i18n.get("foss.gui.creating.consigneePanel.regionAddress.alert"));
				}
			}
		}
		
	}

	/**
	 * 
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午08:10:17
	 */
	private void validateVW(WaybillPanelVo bean) {
		/**
		 * BUG-41949:运单储运事项过长，导致综合查询报错，建议在开单时进行限制长度。。。见图。。258000250
		 */
		//对内备注
		int innerNotes = StringUtil.defaultIfNull(bean.getInnerNotes()).length();
		//储运事项 
		int transNotes = StringUtil.defaultIfNull(bean.getTransportationRemark()).length();
		
		if(innerNotes > CLOB_LIMIT){
			///对内备注录入错误：最大字符不能超过1300个！
			StringBuffer sb = new StringBuffer();
			sb.append(i18n.get("foss.gui.creating.cargoInfoPanel.insideRemark.label"));
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.inputDataError"));
			sb.append(CLOB_LIMIT);
			sb.append("\n");
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.currentStringNum"));
			sb.append(innerNotes);
			throw new WaybillValidateException(sb.toString());
		}
		
		if(transNotes > CLOB_LIMIT){
			//储运事项 录入错误：最大字符不能超过1300个！
			StringBuffer sb = new StringBuffer();
			sb.append(i18n.get("foss.gui.creating.cargoInfoPanel.transportationRemark.label"));
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.inputDataError"));
			sb.append(CLOB_LIMIT);
			sb.append("\n");
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.currentStringNum"));
			sb.append(transNotes);
			throw new WaybillValidateException(sb.toString());
		}
	}

	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProduct(WaybillPanelVo bean) {
		// 产品对象
		ProductEntityVo product = bean.getProductCode();
		// 产品对象是否为空或编码是否为空
		if (product == null || "".equals(StringUtil.defaultIfNull(product.getCode()))) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductType"));
		}
		/*if(WaybillConstants.AIR_FLIGHT.equals(product)){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.NotNullProductType"));
		}*/
	}

	/**
	 * 
	 * 校验目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:26:27
	 */
	private void validateDistination(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
		}
	}
	/**
	 * 验证保价声明价值-MANA-409
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-1 上午11:01:19
	  * @param record
	  * @return
	 */
	private void validateInsurance(WaybillPanelVo bean) {
		//1非月结客户无订单发货,2非月结客户通过内部渠道（400、营业部下单） 保价声明价值等于0则抛出异常
		if (null == bean.getReceiveMethod()) {
			// 抛出异常信息
			throw new WaybillSubmitException(i18n.get("foss.gui.creating.listener.Waybill.exception.ReceiveMethodNotNull"));
		}

		/**
		 * 与validatePaymentMode 中非月结客户 校验重复
		 *@author Foss-278328-hujinyang
		 *@ date 20151225 12:01
		 */
		/*// 如果内部带货则不用判断保价声明价值
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			//报价声明价值
			BigDecimal insuranceAmount =bean.getInsuranceAmount();
			//保价声明价值小于等于0
			if(insuranceAmount == null || insuranceAmount.compareTo(BigDecimal.ZERO) <= 0){
				//非月结客户
				if(bean.getChargeMode() == null || !bean.getChargeMode()){
					//订单号为空
					if(StringUtils.isBlank(bean.getOrderNo())){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero"));
					}else{
						//非月结客户通过内部渠道（400、营业部下单）下单
						if(WaybillConstants.CRM_ORDER_CHANNEL_CALLCENTER.equals(bean.getOrderChannel())
//								|| WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel())  官网、
								|| WaybillConstants.CRM_ORDER_CHANNEL_BUSINESS_DEPT.equals(bean.getOrderChannel())){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero"));
						}
					}
				}
			}
		}*/
	}
	
	/**
	 * @function 校验包装信息是否大于50个字符
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-8 18:46:21
	 * @param goodsPackage
	 */
	private void validateGoodPackage(String goodsPackage) {
		try {
			if(StringUtils.isEmpty(goodsPackage)){
				return;
			}
			String a = new String(goodsPackage.getBytes("GBK"),"ISO-8859-1");
			if(a.length()> NumberConstants.NUMBER_50){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.cargoInfoPanel.txtOther.length.notOver50"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 验证走货路径
	 * @author foss-sunrui
	 * @date 2013-3-25 下午3:36:16
	 * @param bean
	 * @see
	 */
	private void validateFreightRout(WaybillPanelVo bean){
		if(!bean.getIsWholeVehicle()){
    		if(StringUtil.isEmpty(bean.getLoadLineName())||StringUtil.isEmpty(bean.getLoadLineCode())){
    			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.one"));
    		}
		}
		if(StringUtil.isEmpty(bean.getLoadOrgCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.two"));
		}
		
		if(StringUtil.isEmpty(bean.getLastLoadOrgCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.three"));
		}
		
		//如果不是空运，区分AB货时必须选择货物类型（空运货物类型和汽运货物类型不是同一个字段）
		/*if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {			
			if(bean.getGoodsTypeIsAB()!=null&&bean.getGoodsTypeIsAB()){
				if(StringUtils.isEmpty(bean.getGoodsType())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.four"));
				}
			}
		}*/
		
	}
	/**
	 * 
	 * 预付费保密校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:45:05
	 */
	private void validateSecretPrepaid(WaybillPanelVo bean) {
		boolean bool = bean.getSecretPrepaid();
		if (bool) {
			if (bean.getPrePayAmount().longValue() == 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullPrePayAmount"));
			}
		}
	}
	/**
	 * 
	 * 有退款类型必须有客户编码
	 * @author foss-sunrui
	 * @date 2013-4-12 下午3:26:37
	 * @param bean
	 * @see
	 */
	private void validateRefundType(WaybillPanelVo bean){
		DataDictionaryValueVo vo = bean.getRefundType();
		// 有退款必须要有客户编码
		if (vo != null && vo.getValueCode() != null) {
			/**
			 * 与validateCod中的Common.validateBankInfo方法重复校验！
			 * @author Foss-278328-hujinyang
			 * @time 20151225 15:51
			 */
//    		if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
//    			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.refundType"));
//    		}
    		//除审核退外都必须有银行账户信息
    		if (!WaybillConstants.REFUND_TYPE_VALUE.equals(vo.getValueCode())&&(bean.getAccountCode() == null || "".equals(bean.getAccountCode()))) {
    			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.refundTypeNoAccount"));
    		}
		}
	}
	/**
	 * 在上门接货的时候 司机工号必填
	 * @param bean
	 */
	private void validateDriverNo(WaybillPanelVo bean) {
		if(bean.getPickupToDoor()!=null 
			&& bean.getPickupToDoor() 
			&& StringUtils.isEmpty(bean.getDriverCode())	){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullDriverCode"));
		}
	}
	/**
	 * 
	 * 判断收货地址是否为空
	 * 
	 * @author WangQianJin
	 * @date 2013-04-24
	 */
	private void validateReceiveAddress(WaybillPanelVo bean) {
		// 提货方式
		String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_FREE.equals(code) || WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_UP.equals(code) || WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) || WaybillConstants.DELIVER_UP_AIR.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			AddressFieldDto consigneeAddress = ui.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
			if (consigneeAddress==null || StringUtils.isEmpty(consigneeAddress.getProvinceId()) || StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}
	}	
	/**
	 * 验证预付金额是否为负数
	 * @author 105888-foss-zhangxingwang
	 * date 2013-9-16 20:11:32
	 * @param bean
	 */
	private void validatePrePayAmountFee(WaybillPanelVo bean) {
		if(bean.getPrePayAmount() != null && bean.getPrePayAmount().doubleValue() < 0 ){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillsubmitaction.exception.validateprepayamount"));
		}
	}

}
