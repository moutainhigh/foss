/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpWaybillTempSaveData;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSubmitConfirmDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWaybillTempSaveAction  extends AbstractButtonActionListener<ExpWaybillEditUI> {
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpWaybillTempSaveAction.class);	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpWaybillTempSaveAction.class);	
	// WAY BILL SERVICE
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	//运单UI
	private ExpWaybillEditUI waybillEditUI;

	/**
	 * 
	 * <p>
	 * 运单暂存
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//获得绑定的VO对象
		ExpWaybillPanelVo bean = ExpCommon.getVoFromUI(waybillEditUI);

		Boolean bool = false;
		bool = validate(bean);

		try {
			ExpCommon.requestFocus(waybillEditUI);
			if (bool) {
				if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {

					// 运单暂存
					tempSaveWaybill(bean);
					MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.tempSaveWaybillSuccess"));
					// 设置可以再次暂存
					bean.setPCPending(true);
					// 设置控件的编辑状态 
					//componentSetEnable();
					//设置暂存成功后可以打标签
					waybillEditUI.buttonPanel.getBtnPrintLabel().setEnabled(true);
				} else {

					validateBusiness(bean);

					// 运单离线暂存
					waybillEditUI.setWaybillState(WaybillConstants.OFF_LINE_TEMP_SAVE);
					ExpSubmitConfirmDialog panel = new ExpSubmitConfirmDialog(bean, waybillEditUI);
					// 居中显示弹出窗口
					WindowUtil.centerAndShow(panel);
				}
			}
		} catch (BusinessException er) {
			LOGGER.error("运单暂存异常，原因" + er.getMessage(), er);
			MsgBox.showInfo(er.getMessage());
		}
	}
	/**
	 * 判断关键字段：收货部门、运单号、目的站、提货网点、预配线路、运输性质、提货方式、包装、件数是否有值，如果缺乏字段，需要进行填充后才能暂存
	 * @param bean
	 * @return 布尔
	 */
	private Boolean validate(WaybillPanelVo bean) {
		if (StringUtils.isEmpty(bean.getReceiveOrgCode())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullReceiveOrgCode"));
			return false;
		}else if(StringUtils.isEmpty(bean.getWaybillNo())){
			
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullWaybillNo"));
			return false;
		}else if(StringUtils.isEmpty(bean.getTargetOrgCode())){
			
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullTargetOrgCode"));
			return false;
		}else if(bean.getCustomerPickupOrgCode()==null)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullCustomerPickupOrgCode"));
			return false;
		}else if(StringUtils.isEmpty(ExpCommon.getPackage(bean)))
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullPackage"));
			return false;
		}else if(bean.getGoodsQtyTotal() == null || bean.getGoodsQtyTotal().intValue() == 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullGoodsQtyTotal"));
			return false;
		}
		//配载线路不能为空
		else if(StringUtil.isEmpty(bean.getLoadLineCode()) || StringUtil.isEmpty(bean.getLoadLineName())){
			if(!bean.getIsWholeVehicle())
			{
				MsgBox.showInfo(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.one"));
				return false;
			}
		}else if(bean.getGoodsWeightTotal() == null || bean.getGoodsWeightTotal().doubleValue() == 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.cargoInfoPanel.weight.label.isNotNull"));
			return false;
		}
		
		if(waybillService.isExsits(bean.getWaybillNo())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.waybillNoExist"));
			return false;
		}
		return true;
		
	}

	/**
	 * 
	 * 基本信息校验
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午03:49:00
	 */
	private void validateBusiness(WaybillPanelVo bean)
	{
		// 预付费保密校验
		validateSecretPrepaid(bean);
		//验证偏线的保险声明价值
		validateProduct(bean);
		//验证空运业务-机场自提
		validateAir(bean);
		//手机及电话号码必须录入一个 
		validatePhone(bean);
		//在上门接货的时候 司机工号必填
		validateDriverNo(bean);
		//付款方式选择临欠 不能超过客户信用额度
		validateCustomerPrepayCredit(bean);
	}
	
	
	
	/**
	 * 付款方式选择临欠 不能超过客户信用额度
	 * @param bean
	 */
	private void validateCustomerPrepayCredit(WaybillPanelVo bean) {
		if(bean.getPaidMethod()!=null 
			&& (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())
					|| WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())
			)){
			BigDecimal prepayAmountBigDecimal=bean.getPrePayAmount();
			String deliveryCustomerCode = bean.getDeliveryCustomerCode();
			if(prepayAmountBigDecimal==null || StringUtils.isEmpty(deliveryCustomerCode)){
				return;
			}
			List<CustomerQueryConditionDto> conditions = new ArrayList<CustomerQueryConditionDto>();
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setCustCode(deliveryCustomerCode);
			dto.setExactQuery(true);
			conditions.add(dto);
			List<CustomerQueryConditionDto> resultList = waybillService.queryCustomerByConditionList(conditions);
			if(resultList==null || resultList.isEmpty()){
				return;
			}
			CustomerQueryConditionDto resultDto = resultList.get(0);
			BigDecimal customerPrepayCredit = resultDto.getCreditAmount();
			if(customerPrepayCredit==null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullCustomerPrepayCredit"));
			}
			
			if(customerPrepayCredit.doubleValue()< prepayAmountBigDecimal.doubleValue()){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.lackCustomerPrepayCredit.one")
					+customerPrepayCredit.doubleValue()+i18n.get("foss.gui.creating.waybillTempSaveAction.exception.lackCustomerPrepayCredit.two"));
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
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullDriverCode"));
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
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullMobilephoneOrTel"));
		}
		
		if(StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone()) 
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullMobilephoneOrTel"));
		}
	}

	/**
	 * 
	 * 验证空运业务
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 上午09:19:51
	 */
	private void validateAir(WaybillPanelVo bean)
	{
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.validateAirException.one"));
				}
			}
			
			if(bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0)
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.validateAirException.two"));
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
	private void validateProduct(WaybillPanelVo bean) {
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())) {
			BigDecimal insuranceAmount = bean.getInsuranceAmount();
			BigDecimal maxInsuranceAmount = bean.getMaxInsuranceAmount();
			if(insuranceAmount == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullInsuranceAmount"));
			}
			if(maxInsuranceAmount == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.nullMaxInsuranceAmount"));
			}
			if (insuranceAmount.compareTo(maxInsuranceAmount) > 0) {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.overMaxInsuranceAmount") + maxInsuranceAmount);
			}
		}
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
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.exception.zeroPrePayAmount"));
			}
		}
	}
	
	
	
	/**
	 * 
	 * 将开单界面控件全部设置成不可编辑，将新增按钮设置为可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-1 上午10:49:57
	 */
	private void componentSetEnable()
	{
		UIUtils.disableUI(waybillEditUI);
		waybillEditUI.buttonPanel.getBtnNew().setEnabled(true);//新增

	}
	
	/**
	 * 
	 * 调用远程-运单暂存方法
	 * @author 025000-FOSS-helong
	 * @date 2012-11-28 上午11:04:35
	 */
	private void tempSaveWaybill(WaybillPanelVo bean2){
		ExpWaybillTempSaveData tempSave = new ExpWaybillTempSaveData(waybillEditUI);
//		/**
//		 * 暂存之前选计算下总运费
//		 * 否则在未点击“计算总运费”的情况下暂存时，总运费为0
//		 * 导入暂存的运单时，总费用又不为0的情况，参见BUG-7324 
//		 */
//		CalculateFeeTotalUtils.calculateFee(bean);
		ExpWaybillPanelVo bean = (ExpWaybillPanelVo ) bean2;
		if(StringUtils.isEmpty(bean.getIsAddCode())){
			bean.setIsAddCode(FossConstants.NO);
		}
		//增加是否快递字段 add by yangkang
		WaybillPendingDto waybill=tempSave.getWaybillDto(bean);
		waybill.setIsExpress(FossConstants.YES);
		
		waybillService.tempSaveWaybill(waybill);
	}
	

	
	@Override
	public void setIInjectUI(ExpWaybillEditUI waybillEditUI) {

		this.waybillEditUI = waybillEditUI;

	}

}
