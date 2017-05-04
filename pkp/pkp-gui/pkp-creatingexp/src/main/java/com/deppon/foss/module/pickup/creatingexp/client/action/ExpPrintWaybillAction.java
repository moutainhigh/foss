/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.OtherFeeBean;
import com.deppon.foss.module.pickup.creating.client.vo.WaybillPrintBean;
import com.deppon.foss.module.pickup.creatingexp.client.ui.print.ExpChoosePrintTypeDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;
import com.deppon.foss.prt.PrintUtil;
import com.deppon.foss.util.MoneyUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpPrintWaybillAction  implements IButtonActionListener<ExpChoosePrintTypeDialog> {
	// ui object
	private ExpChoosePrintTypeDialog ui;

	@Inject
	private IWaybillService wayBillService;
	
	// 日志
	private static final Log LOG = LogFactory.getLog(ExpPrintWaybillAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpPrintWaybillAction.class);
	// 运单entitiy
	private WaybillEntity waybillEntity;
	
	private static final String NUMBER_ONE="1";
	
	private static final String NUMBER_TWO="2";
	
	private static final String NUMBER_THREE="3";
	
	private static final String NUMBER_FOUR="4";

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NINE = 9;
	
	

	/**
	 * <p>
	 * (触发打印事件)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 上午9:38:49
	 * @param arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {

		try {
			JasperContext jctx = new JasperContext();
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			// In J2EE, getClassLoader might not work as expected (According to
			// sornar)
			jctx.setClassLoader(this.getClass().getClassLoader());
			wayBillService = WaybillServiceFactory.getWaybillService();
			WaybillPrintBean resourceBean = (WaybillPrintBean) ui.getWaybillEditUI().getWaybillPrintBean();
			String waybillNo = resourceBean.getWaybillNo();
			// 在线获得运单信息
			if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
				waybillEntity = wayBillService.queryWaybillByNumber(waybillNo);
			} else {
				// 离线获得运单信息
				WaybillPendingEntity pendingEntity = wayBillService.queryPendingByNo(waybillNo);
				waybillEntity = new WaybillEntity();
				waybillEntity.setId(pendingEntity.getId());
				waybillEntity.setReceiveOrgCode(pendingEntity.getReceiveOrgCode());
				waybillEntity.setBillTime(pendingEntity.getBillTime());
				waybillEntity.setCreateOrgCode(pendingEntity.getCreateOrgCode());
			}
			
			int currentRow = ui.getTable().getSelectedRow();
			String currentTempleteValue = (String)ui.getTable().getValueAt(currentRow,2);

			// 数据填充
			dataInsert(jctx, resourceBean, waybillEntity, user);
			// 获得打印管理类
			JasperPrintManager jasperPrintManager = new JasperPrintManager(jctx);
			// 运单打印
			// 判断是运单预览还是运单打印   true为打印 false为打印预览
			if (ui.getIsPrintOrPrePrint()) { // ui getIsPrintOrPrePrint true
				// 大陆版全打
				if (currentTempleteValue.equals(NUMBER_ONE)) {
					jctx.setPrtkey("printWaybill");
					jasperPrintManager.processPrintResultDirectWithoutImage(jctx);
					// 大陆运单打印 半打
				} else if (currentTempleteValue.equals(NUMBER_TWO)) {
					jctx.setPrtkey("printHalfWaybill");
					jasperPrintManager.processPrintResultDirectWithoutImage(jctx);
				}else if (currentTempleteValue.equals(NUMBER_THREE)) {
					//香港版全打
					jctx.setPrtkey("printwaybillhongkong");
					jasperPrintManager.processPrintResultDirectWithoutImage(jctx);
				}else if (currentTempleteValue.equals(NUMBER_FOUR)) {
					//香港版半打
					jctx.setPrtkey("printHalfWaybillhongkong");
					jasperPrintManager.processPrintResultDirectWithoutImage(jctx);
				}else {
					MsgBox.showError(i18n.get("foss.gui.creating.PrintWaybillAction.msgBox.selectPrintTemp"));
				}
			//运单预览
			}else {
				//预览大陆版 全打
				if (currentTempleteValue.equals(NUMBER_ONE)) {
					jctx.setPrtkey("waybill");
					jasperPrintManager.processPrintResultInPreviewer(jctx);
					//预览 大陆版 半打
				}else if(currentTempleteValue.equals(NUMBER_TWO)) {
					jctx.setPrtkey("halfwaybill");
					jasperPrintManager.processPrintResultInPreviewer(jctx);
					//预览 香港版全打
				}else if (currentTempleteValue.equals(NUMBER_THREE)) {
					jctx.setPrtkey("waybillhongkong");
					jasperPrintManager.processPrintResultInPreviewer(jctx);
					//预览香港版半打
				}else if (currentTempleteValue.equals(NUMBER_FOUR)) {
					jctx.setPrtkey("halfwaybillhongkong");
					jasperPrintManager.processPrintResultInPreviewer(jctx);
					//否则提示：请选择打印模版
				}else {
					MsgBox.showError(i18n.get("foss.gui.creating.PrintWaybillAction.msgBox.selectPreviewTemp"));
				}
				
			}
		
			ui.setVisible(false);
			ui = null;
			// 插条打印记录
			if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
				if (waybillEntity != null) {
					PrintInfoEntity record = new PrintInfoEntity();
					record.setPrintType(WaybillConstants.PRINT_INFO_WAYBILL);
					record.setWaybillNo(waybillNo);

					record.setWaybillId(waybillEntity.getId());
					// record.setPrintTimes();
					record.setPrintUserCode(user.getEmployee().getEmpCode());
					record.setPrintUser(user.getEmployee().getEmpName());
					record.setPrintOrgCode(user.getEmployee().getDepartment().getCode());
					record.setPrintOrg(user.getEmployee().getDepartment().getName());
					record.setPrintTime(new Date());
					record.setId(UUIDUtils.getUUID());
					WaybillServiceFactory.getWaybillService().insertSelective(record);
					// WaybillServiceFactory.getWaybillService().updateWaybillPrintTimesOnLine(resourceBean.getWaybillNo());
				}
				// else {
				// 离线 开单 离线状态的打印
				// WaybillServiceFactory
				// .getWaybillService().updateWaybillPrintTimes(resourceBean.getWaybillNo());
				// }
			}
		} catch (final Exception exp) {
			LOG.error("开单打印异常 Exception", exp);
					LOG.info("开单打印异常", exp);
			MsgBox.showError(exp.getMessage());
		}
	}

	/**
	 * <p>
	 * (判断对象是否为空)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-9 下午2:24:16
	 * @param value
	 * @return
	 * @see
	 */
	private String isBeanValueNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * <p>
	 * (数据从WaybillEditUI获得)
	 * 
	 * @author foss-jiangfei
	 * @param resourceBean
	 * @param jctx
	 * @date 2012-10-26 下午2:44:04
	 * @see
	 */
	private void dataInsert(JasperContext jctx, WaybillPrintBean resourceBean, WaybillEntity waybillEntity, UserEntity user) {

		// 数据填充
		StringBuffer otherFeeValue = new StringBuffer("");
		PrintUtil printUtil = new PrintUtil();
		//获取运单其他费用
		if (resourceBean.getOtherFeeList() != null) {
			for (OtherFeeBean temp : resourceBean.getOtherFeeList()) {
			    if(StringUtils.isNotBlank(temp.getOtherFeeName())){
					if(temp.getOtherFeeName().length()== NINE){
					    otherFeeValue.append(temp.getOtherFeeName()).append(" ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== EIGHT){
					    otherFeeValue.append(temp.getOtherFeeName()).append("  ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== SEVEN){
					    otherFeeValue.append(temp.getOtherFeeName()).append("   ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== SIX){
					    otherFeeValue.append(temp.getOtherFeeName()).append("    ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== FIVE){
					    otherFeeValue.append(temp.getOtherFeeName()).append("        ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== FOUR){
					    otherFeeValue.append(temp.getOtherFeeName()).append("            ").append(temp.getOtherFeeValue()).append("\n");
					}else if(temp.getOtherFeeName().length()== THREE){
					    otherFeeValue.append(temp.getOtherFeeName()).append("               ").append(temp.getOtherFeeValue()).append("\n");
					}else{
					    otherFeeValue.append(temp.getOtherFeeName()).append("                           ").append(temp.getOtherFeeValue()).append("\n");
					}
			    }
			}
		}
		//追加代收手续费
	    if(resourceBean.getCodFee().doubleValue()>0){
	    	otherFeeValue.append("代收手续费").append("        ").append(resourceBean.getCodFee()).append("\n");
	    }
		// 给参数 其他费用   赋值
		jctx.put("otherfee", isBeanValueNull(otherFeeValue));
		// 参数 运单赋值
		jctx.put("waybillNo", isBeanValueNull(resourceBean.getWaybillNo()));
		//设置退款类型名称
		jctx.put("refundTypeName", isBeanValueNull(resourceBean.getRefundTypeName()));
		
		String deliverCustomConnactWay = "";
		if (StringUtils.isNotEmpty(resourceBean.getDeliveryCustomerMobilephone()) && StringUtils.isNotEmpty(resourceBean.getDeliveryCustomerPhone())) {
			deliverCustomConnactWay = resourceBean.getDeliveryCustomerMobilephone() + PrintUtil.OBLIQUE_LINE + resourceBean.getDeliveryCustomerPhone();
		} else if (StringUtils.isNotEmpty(resourceBean.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(resourceBean.getDeliveryCustomerPhone())) {
			deliverCustomConnactWay = resourceBean.getDeliveryCustomerMobilephone();
		} else if (StringUtils.isEmpty(resourceBean.getDeliveryCustomerMobilephone()) && StringUtils.isNotEmpty(resourceBean.getDeliveryCustomerPhone())) {
			deliverCustomConnactWay = resourceBean.getDeliveryCustomerPhone();
		}

		
		jctx.put("deliveryCustomerName", isBeanValueNull(resourceBean.getDeliveryCustomerName()));//发货客户名字
		jctx.put("receiveCustomerName", isBeanValueNull(resourceBean.getReceiveCustomerName()));//收货客户名字
		
		jctx.put("consignor", isBeanValueNull(resourceBean.getDeliveryCustomerContact()));//发货人的名字
		jctx.put("deliveryCustomerContact", isBeanValueNull(resourceBean.getDeliveryCustomerContact()));//发货联系人（港版）
		jctx.put("receiveCustomerContact", isBeanValueNull(resourceBean.getReceiveCustomerContact()));//收货联系人（港版）

		jctx.put("field1", isBeanValueNull(deliverCustomConnactWay));

		jctx.put("consignorNo", isBeanValueNull(resourceBean.getDeliveryCustomerCode()));// "$F发货人编码"
		StringBuffer receiveCustomConnactWay = new StringBuffer();
		receiveCustomConnactWay.append(resourceBean.getReceiveCustomerMobilephone());
		if(null != resourceBean.getReceiveCustomerPhone()){
		    receiveCustomConnactWay.append(PrintUtil.OBLIQUE_LINE);
		    receiveCustomConnactWay.append(resourceBean.getReceiveCustomerPhone());
		}
		jctx.put("field2", isBeanValueNull(receiveCustomConnactWay));// "$F收货人联系方式"
		jctx.put("consignorAddr", isBeanValueNull(ui.getWaybillEditUI().getConsignerPanel().getTxtConsignerArea().getText() + resourceBean.getDeliveryCustomerAddress()));// "$F发货人地址"
		jctx.put("addr", isBeanValueNull(ui.getWaybillEditUI().getConsigneePanel().getTxtConsigneeArea().getText() + resourceBean.getReceiveCustomerAddress()));// "$F收货人地址"
//		jctx.put("consignee", isBeanValueNull(resourceBean.getReceiveCustomerName()));// "$F收货人"
		jctx.put("consignee", isBeanValueNull(resourceBean.getReceiveCustomerContact()));// "$F收货人"

		if (("").equals(isBeanValueNull(resourceBean.getProductCode()))) {
			jctx.put("product", "");
		} else {
			jctx.put("product", isBeanValueNull(resourceBean.getProductCode().getName()));// "$F运输方式"
		}

		if (isBeanValueNull(resourceBean.getReceiveMethod()).equals("")) {
			jctx.put("deliveryType", "");
		} else {
			jctx.put("deliveryType", isBeanValueNull(resourceBean.getReceiveMethod().getValueName()));// "$F交货方式");
		}

		if (isBeanValueNull(resourceBean.getPaidMethod()).equals("")) {
			jctx.put("payType", "");
		} else {

			jctx.put("payType", isBeanValueNull(resourceBean.getPaidMethod().getValueName()));// "$F付款方式");
		}

		if (isBeanValueNull(resourceBean.getReturnBillType()).equals("")) {
			jctx.put("signBill", "");
		} else {
			jctx.put("signBill", isBeanValueNull(resourceBean.getReturnBillType().getValueName()));// "$F签收单返单类别");
		}
//		deliveryCustomerName
		resourceBean.getDeliveryCustomerName();
		jctx.put("receiveCustomerCode", isBeanValueNull(resourceBean.getReceiveCustomerCode()));// "$F收货人编码" (港版);
		
		jctx.put("payAccount", isBeanValueNull(resourceBean.getAccountCode()));// "$F开户行账号");
		jctx.put("openAccountBank", isBeanValueNull(resourceBean.getAccountBank()));// "$F开户银行");
		jctx.put("accountName", isBeanValueNull(resourceBean.getAccountName()));// "$F户名");
		jctx.put("proxyAmount", printUtil.fmtMicrometer(resourceBean.getCodAmount().toString()));// "$F代收货款");
		jctx.put("insurance", printUtil.fmtMicrometer(resourceBean.getInsuranceAmount().toString()));// insure
																											// 保价申明值

		// 半打全打分界线
		// 储运注意事项
		jctx.put("stroeTransport", isBeanValueNull(resourceBean.getStorageMatter()));// "$F储运注意事项"

		jctx.put("onlineOrderNo", isBeanValueNull(resourceBean.getOnlineOrderNo()));// "$F网上订单号"
		jctx.put("commodityName", isBeanValueNull(resourceBean.getGoodsName()));// "$F货物品名"
		jctx.put("packaging", isBeanValueNull(resourceBean.getGoodsPackage()));// "$F包装"
		if (waybillEntity != null && waybillEntity.getReceiveOrgCode() != null) {
			String startStop=printUtil.gainCityNameByCode(waybillEntity.getReceiveOrgCode());
			if(startStop==null){
				startStop="";
			}
			jctx.put("startStop", startStop); // "$F始发站"
		}
		// jctx.put("startStop",
		// isBeanValueNull(resourceBean.getDeliverOrgCode()));// "$F始发站"
		jctx.put("endStop", isBeanValueNull(resourceBean.getTargetOrgCode()));// "$F目的站"
		jctx.put("count", isBeanValueNull(resourceBean.getGoodsQtyTotal()));// "$F件数");
		jctx.put("volume", isBeanValueNull(resourceBean.getGoodsVolumeTotal()));// "$F体积");
		jctx.put("weight", isBeanValueNull(resourceBean.getGoodsWeightTotal()));// "$F重量");
		jctx.put("isDoor", (resourceBean.getPickupToDoor() != null) ? (resourceBean.getPickupToDoor() ? i18n.get("foss.gui.creating.waybillEditUI.common.yes") : i18n.get("foss.gui.creating.waybillEditUI.common.no")) : "");// "$F上门接货");

		jctx.put("chargeWeight", isBeanValueNull(resourceBean.getBillWeight()));// "$F计费重量");

		jctx.put("rate", isBeanValueNull(resourceBean.getUnitPrice()));// "$F费率");
		jctx.put("freight", isBeanValueNull(resourceBean.getTransportFee()));// "$F运费");
		jctx.put("insuranceCharge", isBeanValueNull(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label")+resourceBean.getInsuranceFee()));// "$F保价费");t

		// 特殊处理的几个 使用三元表达式
		String noPayAmountString = i18n.get("foss.gui.creating.abstractWaybillService.noPayAmount.label") + resourceBean.getPrePayAmount() + "\n" + MoneyUtils.amountToChinese(resourceBean.getPrePayAmount());
		String toPayAmountString = i18n.get("foss.gui.creating.abstractWaybillService.toPayAmount.label") + resourceBean.getToPayAmount() + "\n" + MoneyUtils.amountToChinese(resourceBean.getToPayAmount());
		jctx.put("noPayAmount",noPayAmountString );// "$F现付费用");
		jctx.put("toPayAmount", toPayAmountString);// "$F到付费用");
		
		//查找系统配置的汇率		
		ConfigurationParamsEntity confEntity = wayBillService.queryConfigurationParamsByEntity
		(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.RMB_AGAINST_HK,FossConstants.ROOT_ORG_CODE);
		BigDecimal exchangeRate = null;
		if (confEntity != null && StringUtils.isNotEmpty(confEntity.getConfValue())) {
			exchangeRate = new BigDecimal(confEntity.getConfValue());
		}
		BigDecimal noPayAmountHK = numToChinese(resourceBean.getPrePayAmount(), exchangeRate);
		BigDecimal toPayAmountHK = numToChinese(resourceBean.getToPayAmount(), exchangeRate);
		
		jctx.put("exchangeRate", isBeanValueNull(exchangeRate));// "$F港币汇率"
		jctx.put("noPayAmountHK",noPayAmountString+"("+WaybillConstants.HK_UNIT+noPayAmountHK+")");// "$F预付费用 * 港币汇率"
		jctx.put("toPayAmountHK", toPayAmountString+"("+WaybillConstants.HK_UNIT+toPayAmountHK+")");// "$F到付费用 * 港币汇率"
		//保价费港版和大陆版应该保持一致
		jctx.put("insuranceHK", isBeanValueNull(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label")+resourceBean.getInsuranceFee()));// "$F保价申明价值 * 港币汇率"
		jctx.put("insuranceChargeHK", isBeanValueNull(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label")+resourceBean.getInsuranceFee()));// "$F保价费 * 港币汇率"
		jctx.put("makeBillInfo", isBeanValueNull(user.getEmployee().getEmpName()) + PrintUtil.OBLIQUE_LINE + ((waybillEntity.getBillTime() != null) ? PrintUtil.getDate(waybillEntity.getBillTime()) : ""));// "$F制单人/制单时间");
		OrgAdministrativeInfoEntity consigorNetInfo = printUtil.getOrg(resourceBean.getCreateOrgCode());
		OrgAdministrativeInfoEntity deliveryInfo = printUtil.getOrg(waybillEntity.getCustomerPickupOrgCode());
		
		
		
		/** 针对发货网点/地址/电话 信息组装**/
		//获得发货网点名称
		String tempConsigorNetName =  isBeanValueNull(consigorNetInfo == null?"":consigorNetInfo.getName());
		//如果发货网点不为""则追加"/"符号
		String consigorNetName = "".equals(tempConsigorNetName) ? "" : tempConsigorNetName+PrintUtil.OBLIQUE_LINE;
		//获得发货网点地址,如果发货网点不存在则显示"",否则获取发货网点地址信息，如果发货地址信息为null则也显示"",否则显示该地址信息
		String tempConsigorNetAddress = consigorNetInfo == null?"":consigorNetInfo.getAddress()==null?"":consigorNetInfo.getAddress();
		//如果发货网点地址信息显示"" 则不追加"/",否则追加"/"
		String consigorNetAddress = "".equals(tempConsigorNetAddress) ? "":tempConsigorNetAddress+PrintUtil.OBLIQUE_LINE; 
		//如果发货网点电话为空，默认以公司400电话为准否则显示发货网点电话
		String consigorTelPhone = StringUtils.isEmpty(printUtil.getOrgTelphone(waybillEntity.getCreateOrgCode())) ? PrintUtil.COMPANY_PHONE : printUtil.getOrgTelphone(waybillEntity.getCreateOrgCode());
		jctx.put("consigorNetInfo",consigorNetName + consigorNetAddress + consigorTelPhone);// "$F发货网点/发货网点地址/电话");
		
		/** 针对收货网点/地址/电话 信息组装**/
		//获得收货网点名称
		String tempDeliveryCustomerPickupOrgName = isBeanValueNull(resourceBean.getCustomerPickupOrgName());
		//如果收货网点不为""则追加"/"符号
		String deliveryCustomerPickupOrgName = "".equals(tempDeliveryCustomerPickupOrgName) ? "":tempDeliveryCustomerPickupOrgName+PrintUtil.OBLIQUE_LINE;
		//获得收货网点地址,如果收货网点不存在则显示"",否则获取收货网点地址信息，如果收货地址信息为null则也显示"",否则显示该地址信息
		String deliveryAddress = deliveryInfo == null?"":deliveryInfo.getAddress()==null?"":deliveryInfo.getAddress()+PrintUtil.OBLIQUE_LINE;
		//如果收货网点电话为空，默认以公司400电话为准否则显示收货网点电话
		String deliveryTelPhone =  StringUtils.isEmpty(printUtil.getOrgTelphone(waybillEntity.getReceiveOrgCode())) ? PrintUtil.COMPANY_PHONE : printUtil.getOrgTelphone(waybillEntity.getCustomerPickupOrgCode());
		jctx.put("deliveryInfo", deliveryCustomerPickupOrgName + deliveryAddress + deliveryTelPhone);// "$F提货网点/提货网点地址/电话");
	}

	
	private BigDecimal numToChinese(BigDecimal money,BigDecimal number){
		if (money != null && number != null) {
			BigDecimal moneyBigDecimal = money.multiply(number);
			moneyBigDecimal = moneyBigDecimal.setScale(0,BigDecimal.ROUND_HALF_UP);
			return moneyBigDecimal;
		}else {
			return BigDecimal.ZERO;
		}
	}
	
	
	/**
	 * UI 注入
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-19 下午4:30:20
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(ExpChoosePrintTypeDialog ui) {
		this.ui = ui;
	}
	
}
