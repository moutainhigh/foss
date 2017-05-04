/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.dto.CustomerOperaLoggerDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.CustomerImpOperLogHandler;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSubmitConfirmCompareImportDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSubmitConfirmDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.validation.descriptor.ExpWaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWaybillSubmitAction implements
		IButtonActionListener<ExpWaybillEditUI> {

	// 保价声明上限
	//ExpCalculateAction expAction;

	// 主界面
	ExpWaybillEditUI ui;
	ISaleDepartmentService   isaleDepartmentService;
	//重量上限

	// 重量上限

	private static final double WEIGHT_UPPER_LIMIT = 50;

	// 体积上限
	private static final double VOLUME_UPPER_LIMIT = 0.3;
	
	// Logger
	private static final Log logger = LogFactory.getLog(ExpWaybillSubmitConfirmAction.class);

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ExpWaybillSubmitAction.class);

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 打开“确认提交”处理界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-30 下午6:41:49
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		/**
		 * 试点到试点能开即日退和三日退， 试点到非试点、试点到快递代理、非试点到试点、 非试点到非试点只能开单三日退， 非试点到快递代理无代收业务。
		 */

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		if(CommonContents.logToggle){
			logger.info("运单号："+bean.getWaybillNo()+"  提交开始...");
			String titleName = ui.getEditor().getTitle();
			CustomerOperaLoggerDto logger = CustomerImpOperLogHandler.setLogger(bean.getWaybillNo());
			logger.setMenuName(titleName);
			logger.setSubmitBtnName(e.getActionCommand());
			logger.submitStart();
		}
		List<ValidationError> errorList = waybillBinder.validate();
		Boolean bool = false;
		if (errorList != null) {
			bool = ExpCommon.validateDescriptor(errorList);
		}

		// 如果descrptor校验通过则执行下面的代码
		if (bool) {
			try {
				// Common.requestFocus(ui);

				validate(bean);

				// 检查发货城市和提货网点是不是一个城市，如果不是就做一个提示
				boolean confirm = confirmCustomerPickupOrgCodeCity(bean);

				if (!confirm) {
					return;
				}

				// 运单在线提交
				ui.setWaybillState(WaybillConstants.SUBMIT_STATE);

				/**
				 * 由于点击提交时WaybillPanelVo通过绑定重新从UI界面获得值，但是否集中接送货这个类型没与绑定，
				 * 故在此根据运单类型重新设置
				 */
				if (WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType())) {
					// 设置为集中接送货
					bean.setPickupCentralized(true);
				} else {
					// 设置为非集中接送货
					bean.setPickupCentralized(false);
				}
				
				//合伙人校验预存款资金池的资金额度 2016年1月25日 18:55:49 葛亮亮
				if(BZPartnersJudge.IS_PARTENER)
				{
					expPartnerMonthlyPrestoreCheck(bean);
				}
				
				if(CommonContents.logToggle){					
					logger.info("运单号："+bean.getWaybillNo()+"  提交结束...");
					CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).submitEnd();
				}
				
				JDialog	dialog = null;
				// 正常提交
				if (ui.getImportWaybillPanelVo() == null) {
					// 正常提交
					dialog = new ExpSubmitConfirmDialog(bean, ui);
					// 居中显示弹出窗口
					WindowUtil.centerAndShow(dialog);
				} else {

					// 导入的vo, 离线导入的时候产生 用于再提交的时候比较老新数据的差异
					// 正常提交
					dialog = new ExpSubmitConfirmCompareImportDialog(bean, ui);
					// 居中显示弹出窗口
					WindowUtil.centerAndShow(dialog);
				}
				
			} catch (WaybillValidateException w) {
				if (!"".equals(w.getMessage())) {
					MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
				}
			}
		}
		
	}
	
	//合伙人校验月结和预存资金池 2016年1月29日 10:44:22 葛亮亮
	public void expPartnerMonthlyPrestoreCheck(WaybillPanelVo bean){
		//登陆人信息
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		//登陆人部门编号
		String partnerDeptCode = user.getEmployee().getDepartment().getCode();
		//组织标杆编码
		String unifieldCode = user.getEmployee().getDepartment().getUnifiedCode();
		//开单总费用
		BigDecimal limit = bean.getTotalFee() != null ? bean.getTotalFee() : BigDecimal.ZERO;
		//代收货款
		BigDecimal codAmount = bean.getCodAmount() != null ? bean.getCodAmount() : BigDecimal.ZERO ;
				
		//预付金额
		BigDecimal prePayAmount = bean.getPrePayAmount() != null ? bean.getPrePayAmount() : BigDecimal.ZERO;
		// 订单付款方式
		String payCode = bean.getPaidMethod().getValueCode();
		
		//月结,从 杜军辉 接口验证
		if(WaybillConstants.MONTH_PAYMENT.equals(payCode))
		{
			if(prePayAmount.compareTo(BigDecimal.ZERO) > 0){
				//传值用的实体类
				PartenerDeductDto deductDto = new PartenerDeductDto();						
				deductDto.setPartnerDeptCode(partnerDeptCode);
				//月结传预付金额
				deductDto.setLimit(prePayAmount.toString());
				//接口返回的实体
				SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeductResponse = waybillService.partenerMonthlyLineDeduct(deductDto);
				Boolean isSuccess = partenerMonthlyLineDeductResponse.isSuccess();
				String ErrorMsg = partenerMonthlyLineDeductResponse.getErrorMsg();
				if(!isSuccess){ //验证失败
					throw new WaybillValidateException(ErrorMsg);
				}
			}
		}else{//剩余的从黄老板的接口走
			//快递反单时开单金额为0不需要调资金池接口  2016年3月23日 14:01:06 葛亮亮
			if(limit.compareTo(BigDecimal.ZERO) > 0)
			{
				//传值用的实体类
				PartenerPrestoreDeductDto restoreDeductDto = new PartenerPrestoreDeductDto();
				//这个接口需要传递标杆编码
				restoreDeductDto.setPartnerOrgCode(unifieldCode);
				restoreDeductDto.setAmount(limit.subtract(codAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
				
				//接口返回实体
				SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse = new SynPartenerPrestoreDeductResponse();
				partenerPrestoreDeductResponse = waybillService.partenerPrestoreDeduct(restoreDeductDto);
				Boolean isSuccess = partenerPrestoreDeductResponse.isSuccess();
				String errorInfo = partenerPrestoreDeductResponse.getErrorInfo();
				if(!isSuccess){ //验证失败
					throw new WaybillValidateException(errorInfo);
				}
			}
		}
	}
	
	/**
	 * 
	 * 优惠劵
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 下午04:33:42
	 * @param bean
	 */
	private void executeCoupon(WaybillPanelVo bean, BigDecimal promotionsFee) {
		CouponInfoDto couponInfoDto = null;
		// 优惠卷是否为空
		if (bean.getCouponInfoDto() != null) {
			couponInfoDto = bean.getCouponInfoDto();
		}

		if (couponInfoDto != null) {
			CouponInfoResultDto dto = waybillService
					.validateCoupon(couponInfoDto);
			if (dto != null) {
				// 判断：该优惠券是否已被使用
				if (!dto.isCanUse()) {
					// "您的优惠券已使用，不可重复使用！(waybill:9876543210;value:50)"
					String canNotUseReason = StringUtil.defaultIfNull(dto
							.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(
							canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!bean.getWaybillNo().equals(
							StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						MsgBox.showInfo(canNotUseReason);
						bean.setPromotionsCode(null);
					} else {

						String lastAmount = StringUtils.substringAfterLast(
								canNotUseReason, "value:");
						lastAmount = StringUtils.substringBeforeLast(
								lastAmount, ")");
						lastAmount = StringUtils.substringBeforeLast(
								lastAmount, "}");

						/**
						 * 设置优惠券费用
						 */
						try {
							bean.setCouponFree(new BigDecimal(lastAmount));
						} catch (Exception e) {
							bean.setCouponFree(BigDecimal.ZERO);
						}

					}
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						// 优惠金额
						promotionsFee = dto.getCouponAmount();

						/**
						 * 设置优惠券费用
						 */
						bean.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);
					} else {
						promotionsFee = BigDecimal.ZERO;
						bean.setPromotionsCode("");
					}
				}
			}
		}
	}
	
	/**
	 * 校验快递单号是否符合规则
	 * @param waybillNo
	 * @return
	 * @author 272311 - sangwenhao
	 */
	private boolean checkESCWaybillNo(String waybillNo) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("wayBillno", waybillNo);
			return waybillService.validateWaybillNoIsCorrect(map);
		}catch(BusinessException e){
			logger.info("校验快递单号是否符合规则出现异常："+e) ;
			return true ;
		} catch (Exception e) {
			logger.info("校验快递单号是否符合规则出现异常："+e) ;
			return false ;
		}
	}
	
	/**
	 * 
	 * 基本信息校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午03:49:00
	 */
	private void validate(ExpWaybillPanelVo bean) {
		// 更改单不需要这段代码
		validateWaybillNo(bean);
		//灰度分流规则校验--272311-sangwenhao
		validateECSWaybillNo(bean);
		ExpCommon.validateExpPayMethod(bean);

		// 验证整车业务
		validateVehicleNumber(bean);
		// 只允许合同客户才可以选择免费送货
		ExpCommon.validateDeliverFree(bean, ui);
		// 验证客户信息 -- 联系人不能为空
		validateCustomer(bean);
		// 验证配载线路
		validateFreightRout(bean);
		// 预付费保密校验
		validateSecretPrepaid(bean);
		// 验证偏线的保险声明价值
		validateProduct(bean);
		// 验证空运业务-机场自提
		validateAir(bean);
		// 验证付款方式
		validatePaidMethod(bean);
		// 验证费用
		validateFee(bean);
		// 验证退款类型
		validateRefundType(bean);
		// 再次验证代收货款业务
		validateCod(bean);

		// 验证返单
		// validateReturnBill(bean);

		// 在pda的情况下 必须输入手写先付金额
		// 如果没有填这个金额 最后现金差异报表查不出来 所以这个值在pda的情况下验证为必填
		validatePdaHandwriteFee(bean);

		// 手机及电话号码必须录入一个
		validatePhone(bean);
		// 在上门接货的时候 司机工号必填
		validateDriverNo(bean);
		// 付款方式选择临欠 不能超过客户信用额度
		// validateCustomerPrepayCredit(bean);
		// 判断客户信用度以结算判断为准，CRM的信用度暂不用判断

		// 验证包装信息
		validatePack(bean);
		// 验证收货地址
		validateReceiveAddress(bean);

		// 检查试点城市和试点营业部的逻辑
		if (bean.getReturnType() != null
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
						.equals(bean.getReturnType().getValueCode())) {
			// do noting
		} else {
			validateExpressCity(bean);
		}

		// 检查保险声明价值
		validateInsuranceFee(bean);

		// 检查公布价运费
		validateTransportFee(bean);

		// 检查德邦客户和发货人工号及内部带货费用承担部门
		validateDepponExpressEmpCode(bean);
		//重量体积限制

		// 重量体积限制
		validateWeightVolume(bean);
		// 伙伴开单校验
		if (null != bean.getPartnerBilling() && bean.getPartnerBilling()) {
			valdatePartnerName(bean);
			valdatePartnerPhone(bean);
		}
		// 校验CRM推广活动是否在有效时间范围内
		Common.validateCrmActiveInfo(bean);

		/**
		 * 对输入的保险价值声明进行验证 提交时不判断，在计算的时候进行校验
		 * 
		 * @author：218371-foss-zhaoyanjun
		 * @date:2014-10-14下午19:27
		 */
		// Common.vfia(bean);
		/**
		 * 对输入的保险价值声明进行验证
		 * 
		 * @author：218371-foss-zhaoyanjun
		 * @date:2014-10-15下午15:48
		 */
		Common.cpv(bean);
		//liding comment for NCI
		/**
		 * 验证交易流水号是否满足开单要求
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-23
		 */
		//ExpCommon.validateTransactionSerialNumber(bean, ui);

		/**
		 * 快递 送货费优化
		 * 
		 * @author 218459-foss-dongsiwei
		 */
		ExpCommon.deliveryGoodsFeeCalculate(bean);
	}

	private void validateECSWaybillNo(ExpWaybillPanelVo bean) {
		boolean switch4EcsFlag = waybillService.queryPkpSwitch4Ecs();
		if(switch4EcsFlag){
			//待补录的不走分流
			if(!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean.getWaybillstatus())){
				//一票多件，签收单返单，转寄退回 单号向OMS系统推送
				if(!(bean.getGoodsQtyTotal() > 1
						|| (bean.getReturnType()!=null && StringUtils.isNotBlank(bean.getReturnType().getValueCode())))){
					//其他情况正常开单走分流分配规则--272311-sangwenhao
					boolean isCorrect = checkESCWaybillNo(bean.getWaybillNo());
					if(!isCorrect){
						ui.getBasicPanel().getTxtWaybillNO().requestFocus(true) ;
						throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.waybillDescriptor.escWaybillNo.isCorrect"));
					}
				}//if end
			}//if end
		}//if end
	}

	/**
	 * 检查发货城市和提货网点是不是一个城市，如果不是就做一个提示
	 */
	private boolean confirmCustomerPickupOrgCodeCity(ExpWaybillPanelVo bean) {
		if (bean.getReceiveCustomerAreaDto() != null
				&& StringUtils.isNotEmpty(bean.getReceiveCustomerAreaDto()
						.getCityId())) {
			String cityId = bean.getReceiveCustomerAreaDto().getCityId();
			if (bean.getCustomerPickupOrgCode() != null
					&& StringUtils.isNotEmpty(bean.getCustomerPickupOrgCode()
							.getCode())) {
				String cityId2 = waybillService.queryCityIdByOrgCode(bean
						.getCustomerPickupOrgCode().getCode());
				if (cityId2 != null && !cityId2.equals(cityId)) {
					return MsgBox
							.showInfo2(
									i18n.get("foss.gui.creating.calculateAction.confirm.differentCity"),
									"");
				}
			}
		}
		return true;
	}

	/**
	 * 重量体积限制
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-7 下午7:26:17
	 */
	private void validateWeightVolume(WaybillPanelVo bean) {
		/**
		 * DMANA-805　毛重小于等于50kg*件数，体积小于等于0.3*件数
		 */
		BigDecimal qtyWeightUpperLimit = BigDecimal.valueOf(WEIGHT_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(VOLUME_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		//开单为返货且原单号为子母件时，跳过此校验
		boolean isFlag=false;
		if(bean instanceof ExpWaybillPanelVo){
			ExpWaybillPanelVo bean2=(ExpWaybillPanelVo)bean;
			if(StringUtils.isNotEmpty(bean2.getOldWaybillNo()) &&
					null !=bean2.getReturnType()&&(
					WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean2.getReturnType().getValueCode())||
					WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(bean2.getReturnType().getValueCode()))){
				TwoInOneWaybillDto waybillDto=waybillService.queryWaybillRelateByWaybillOrOrderNo(bean2.getOldWaybillNo());
				if(null !=waybillDto&&FossConstants.YES.equals(waybillDto.getIsTwoInOne())){
					isFlag=true;
				}
			}
		}
		if(!isFlag){
			if(WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())){
				if ((bean.getGoodsWeightTotal() != null && WaybillConstants.GUOGUO_WIGHT
						.compareTo(bean.getGoodsWeightTotal()) < 0) 
						|| (bean.getGoodsVolumeTotal() != null && WaybillConstants.GUOGUO_VOLUE
						.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creatingexp.expCalculateAction.guoguoQtyWeightUpperLimit"));
				}
				
			}else if ((bean.getGoodsWeightTotal() != null && qtyWeightUpperLimit
					.compareTo(bean.getGoodsWeightTotal()) < 0) // zxy 20140103
					// 增加bean.getGoodsWeightTotal()
					// !=null判断
					|| (bean.getGoodsVolumeTotal() != null && qtyVolumeUpperLimit
					.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creatingexp.expCalculateAction.qtyWeightUpperLimit"));
			}
		}

		// 件数不能大于1
		if (bean.getGoodsQtyTotal().intValue() != 1) {
			/**
			 * gyk 开返货单时 不需要验证部门是否能够开一票多件
			 */
			ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo) bean;
			if (bean2.getReturnType() != null) {
				if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO_NAME.equals(bean2.getReturnType().getValueName()) ||
						WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE_NAME.equals(bean2.getReturnType().getValueName())) {

				}
					else if(valdateExpMultiPieceDept(bean)){
					
				}else{
					SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
					//liyongfei 查询创建部门是否有一件多票的属性
					if(FossConstants.YES.equals(entitry.getCanExpressOneMany())){
						
					}else{						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						throw new WaybillValidateException(
								i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomer"));
					}
					// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件
					if (bean.getCustomerPickupOrgName() != null) {
						if ( bean.getCustomerPickupOrgName().contains(
										"出发")) {
							ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
							throw new WaybillValidateException(
									i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByAdress"));
						}
					}
				}
				}
				else if(valdateExpMultiPieceDept(bean)){
				
				}else{
				SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
				//liyongfei 查询创建部门是否有一件多票的属性
				if(FossConstants.YES.equals(entitry.getCanExpressOneMany())){
					
				}else{					ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomer"));
				}
				// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件
				if (bean.getCustomerPickupOrgName() != null) {
					if ( bean.getCustomerPickupOrgName().contains("出发")) {
						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						throw new WaybillValidateException(
								i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByAdress"));
					}
				}
			}
		}
	}

	/**
	 * 检查德邦客户和发货人工号及内部带货费用承担部门
	 */
	 private void validateDepponExpressEmpCode(ExpWaybillPanelVo bean) {

		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			if (StringUtils.isEmpty(bean.getDeliveryEmployeeNo())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateDeliveryEmployeeNo"));

			 }
		 }

		 validateDepponExpressFeeBurdenOrg(bean);
	}

	 /**
	  * 检查德邦客户内部带货费用承担部门
	  */
	 private void validateDepponExpressFeeBurdenOrg(ExpWaybillPanelVo bean) {

		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			if (StringUtils.isBlank(bean.getInnerPickupFeeBurdenDept())) {
				// 内部带货费用承担部门不能为空
				throw new WaybillValidateException(
						i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.expInnerPickupFeeBurdenDept.nullError"));
			 }
		 }
	}

	/**
	 * 检查公布价运费
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-16 上午9:50:06
	 */
	private void validateTransportFee(ExpWaybillPanelVo bean) {
    if(BZPartnersJudge.IS_PARTENER && bean.getTotalFee().compareTo(BigDecimal.ZERO)==1){
			
			ConfigurationParamsEntity configuration= BaseDataServiceFactory.getBaseDataService()
					.queryConfigurationParamsByEntity(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.CREATINGEXP_PARTNER_ORDER_DISCOUNT,
							FossConstants.ROOT_ORG_CODE);
			ConfigurationParamsEntity maxconfiguration= BaseDataServiceFactory.getBaseDataService()
					.queryConfigurationParamsByEntity(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.MAX_TRANSPORTFEE_EXP,
							FossConstants.ROOT_ORG_CODE);
			if(null!=configuration && StringUtils.isNotBlank(configuration.getConfValue()) &&
			   null!=maxconfiguration && StringUtils.isNotBlank(maxconfiguration.getConfValue())){
				double temp = Double.parseDouble(configuration.getConfValue());
				double maxtemp = Double.parseDouble(maxconfiguration.getConfValue());
				int intValue2 = bean.getTransportFee().intValue();
				if (intValue2 != 0) {
					int transportFee = bean.getTempTransportFee().intValue();
					int maxintValue = (int) (transportFee * maxtemp + 0.5);
					int intValue = (int) (transportFee * temp + 0.5);
					if (intValue2 >= intValue && intValue2 <= maxintValue) {
						Common.cpv(bean);
						ExpCommon.getYokeCharge(bean, ui);
						CalculateFeeTotalUtils.calculateFee(bean);
						ui.billingPayPanel.getBtnSubmit().setEnabled(true);
						ui.buttonPanel.getBtnSubmit().setEnabled(true);
					} else {
						ui.billingPayPanel.getBtnSubmit().setEnabled(false);
						ui.buttonPanel.getBtnSubmit().setEnabled(false);
						throw new WaybillValidateException(i18n
								.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,maxintValue));
						
					}
				}else{
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					ui.buttonPanel.getBtnSubmit().setEnabled(false);
					throw new WaybillValidateException(i18n
							.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
				}
			}else{
				int intValue2 = bean.getTransportFee().intValue();
				if (intValue2 != 0) {
					int transportFee = bean.getTempTransportFee().intValue();
					int intValue = (int) (transportFee * 0.6 + 0.5);
					if (intValue2 >= intValue && intValue2 <= transportFee) {
						Common.cpv(bean);
						ExpCommon.getYokeCharge(bean, ui);
						CalculateFeeTotalUtils.calculateFee(bean);
						ui.billingPayPanel.getBtnSubmit().setEnabled(true);
						ui.buttonPanel.getBtnSubmit().setEnabled(true);
					} else {
						ui.billingPayPanel.getBtnSubmit().setEnabled(false);
						ui.buttonPanel.getBtnSubmit().setEnabled(false);
						throw new WaybillValidateException(i18n
							.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,transportFee));
					}
				}else{
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					ui.buttonPanel.getBtnSubmit().setEnabled(false);
					throw new WaybillValidateException(i18n
							.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
				}
			}
		}
		// 公布价运费
		BigDecimal transFee = CommonUtils.defaultIfNull(bean.getTransportFee());
		// 优惠金额
		BigDecimal promotionsFee = BigDecimal.ZERO;
		// 开单类型
		DataDictionaryValueVo vo = bean.getReturnType();
		DataDictionaryValueVo vo2 = bean.getReceiveMethod();

		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {

		} else {
			// 内部带货金额清零
			ExpCommon.resetZero(bean, ui);
		}

		// 运费为0可以提交只有三种情况 1是内部带货 2是返单开单 3优惠劵大于等于公布价运费
		if ((vo2 != null && WaybillConstants.INNER_PICKUP.equals(bean
				.getReceiveMethod().getValueCode()))
				|| (vo != null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
						.equals(vo.getValueCode()))
				|| promotionsFee.doubleValue() >= transFee.doubleValue()) {
			return;
		} else {
			if (BigDecimal.ZERO.compareTo(transFee) == 0) {
				// 公布价运费为空，请检查价格是否存在！
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
			}
		}
	}

	/**
	 * 检查保险声明价值
	 * 
	 * @param bean
	 */
	private void validateInsuranceFee(ExpWaybillPanelVo bean) {
		ExpCalculateAction.validateInsuranceFee(bean);
		//expAction.validateInsuranceFee(bean);
			}

			/**
     * 检查试点城市和试点营业部的逻辑
	 * 
	 * @param bean
	 */
	private void validateExpressCity(ExpWaybillPanelVo bean) {

		/**
		 * 试点到试点能开即日退和三日退， 试点到非试点、试点到快递代理、非试点到试点、 非试点到非试点只能开单三日退， 非试点到快递代理无代收业务。
		 */

		SalesDepartmentCityDto createDto = bean
				.getCreateSalesDepartmentCityDto();
		SalesDepartmentCityDto endDto = bean.getTargetSalesDepartmentCityDto();

		if (createDto == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto"));
		} else if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& !FossConstants.YES
						.equals(createDto.getTestSalesDepartment())) {
			//如果返单开单，都按试点营业部对待（不校验是否营业部）
			if(!(bean.getReturnType()!=null &&
					"RETURN_WAYBILL".equals(bean.getReturnType().getValueCode()))){
			// 试点城市 非试点营业部不能开单
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto"));
		}
		}

		if (endDto == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateEndDto"));
		}

		// 到达和开始都是试点城市
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto
						.getCityType())) {
			// DataDictionaryValueVo vo = bean.getRefundType();
			// if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode()) &&
			// !WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }

			bean.setCanReturnCargo(FossConstants.YES);
		}

		// 开始试点城市 到达非试点
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			if (bean.getReceiveMethod() != null) {
				// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoPickupSelf"));
				}
			}

			// 返单类别
			// DataDictionaryValueVo vo = bean.getReturnBillType();
			// if(vo!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())){
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo2 = bean.getRefundType();
			// if(vo2!=null && StringUtils.isNotEmpty(vo2.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo2.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }

		}

		// 开始试点城市 到达-快递代理
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto
						.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			// if(bean.getReceiveMethod()!=null){
			// // 提货方式
			// String receiveMethod = bean.getReceiveMethod().getValueCode();
			// //是否自提
			// if (CommonUtils.verdictPickUpSelf(receiveMethod)) {
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSPCityDtoPickupSelf"));
			// }
			// }

			// 返单类别
			// DataDictionaryValueVo vo = bean.getReturnBillType();
			// if(vo!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())){
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			DataDictionaryValueVo vo2 = bean.getRefundType();
			if (vo2 != null
					&& StringUtils.isNotEmpty(vo2.getValueCode())
					&& WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo2
							.getValueCode())) {
				// 试点城市和快递代理城市之间不能开1日退代收货款款
				// throw new
				// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSLCityDtoRefund"));

				if (bean.getReceiveMethod() != null) {
					// 提货方式
					String receiveMethod = bean.getReceiveMethod()
							.getValueCode();
					// 是否自提
					if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateSLCityDtoRefund"));
					}
				}

			}

		}

		// 开始-非试点 到达-试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto
						.getCityType())) {
			bean.setCanReturnCargo(FossConstants.YES);
			String orderChannel = bean.getOrderChannel();
			if (!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO
					.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA
							.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG
							.equals(orderChannel)) {
//				throw new WaybillValidateException(
//						i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoChannel"));
			}

			// DataDictionaryValueVo vo = bean.getRefundType();
			// if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode()) &&
			// !WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoRefund"));
			// }

			// 返单类别
			// DataDictionaryValueVo vo2 = bean.getReturnBillType();
			// if(vo2!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())){
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo3 = bean.getRefundType();
			// if(vo3!=null && StringUtils.isNotEmpty(vo3.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo3.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }
		}

		// 开始-非试点 到达-非试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			String orderChannel = bean.getOrderChannel();
			if (!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO
					.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA
							.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG
							.equals(orderChannel)) {
//				throw new WaybillValidateException(
//						i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoChannel"));
			}

			if (bean.getReceiveMethod() != null) {
				// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateNNCityDtoPickupSelf"));
				}
			}

			// 返单类别
			// DataDictionaryValueVo vo2 = bean.getReturnBillType();
			// if(vo2!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())){
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateNNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo3 = bean.getRefundType();
			// if(vo3!=null && StringUtils.isNotEmpty(vo3.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo3.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }
		}

		// 开始-非试点 到达-非试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto
						.getCityType())) {
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateNPCityDtoBusiness"));

			if (bean.getReceiveMethod() != null) {
				// 提货方式
					String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
					if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					// 非试点城市和快递代理城市之间不能发快递运单
					// 试点城市和快递代理城市之间提货方式只能开派送
					// throw new WaybillValidateException(
					// i18n.get("foss.gui.creating.calculateAction.exception.validateSPCityDtoPickupSelf"));

						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateNPCityDtoBusiness"));

					}
				}

		}

		// 到达城市是非试点 不能开返单
		// if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())){
		// DataDictionaryValueVo vo2 = bean.getReturnBillType();
		// if(vo2!=null
		// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())){
		// //试点城市和非试点城市之间不能开返单
		// //TODO
		// throw new WaybillValidateException(
		// i18n.get("foss.gui.creating.calculateAction.exception.validateDtoReturnBillType"));
		// }
		//
		// }

		IBaseDataService baseDataService = GuiceContextFactroy.getInjector()
				.getInstance(BaseDataService.class);
		// 调用接口查询体积波动范围
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		ConfigurationParamsEntity param = baseDataService.queryByConfCode(
				ExpWaybillConstants.EXPRESS_PICKUP_TURN, user.getEmployee()
						.getDepartment().getCode());

		if (param != null && FossConstants.YES.equals(param.getConfValue())) {
			if (bean.getReceiveMethod() != null
					&& !WaybillConstants.INNER_PICKUP.equals((bean
							.getReceiveMethod().getValueCode()))) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateInnerPickup"));
			}

		}

		String receiveMethod = bean.getReceiveMethod().getValueCode();
		String code = bean.getCustomerPickupOrgCode().getCode();
		if (bean.getCustomerPickupOrgCode() != null) {

			// 是否自提
			if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {

				if (StringUtils.isNotEmpty(code)) {
					OrgAdministrativeInfoEntity o = waybillService
							.queryByCode(code);
					if (o != null
							&& !FossConstants.YES.equals(o
									.getExpressSalesDepartment())) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateExpressSalesDepartment"));
					}

				}
			} else {
				if (StringUtils.isNotEmpty(code)) {
					OrgAdministrativeInfoEntity o = waybillService
							.queryByCode(code);
					if (o != null
							&& FossConstants.YES.equals(o
									.getExpressSalesDepartment())) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateExpressSalesDepartmentVirtual"));
					}

				}

			}
		}
		// 快递保费
		ConfigurationParamsEntity param6 = baseDataService.queryByConfCode(
				ExpWaybillConstants.EXPRESS_CODE_FEE_UP, user.getEmployee()
						.getDepartment().getCode());

		if (param6 != null) {
			bean.setCodAmountMax(new BigDecimal(param6.getConfValue()));
		}

		if (bean.getCodAmountMax() != null
				&& bean.getCodAmount() != null
				&& bean.getCodAmountMax().doubleValue() < bean.getCodAmount()
						.doubleValue()) {

			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateCodMax")
							+ bean.getCodAmountMax());

		}
	}

	/**
     * 
     * 验证返单会被绕过去
	 * 
     * @author 025000-FOSS-helong
     * @date 2013-4-24
     */
	private void validateReturnBill(WaybillPanelVo bean) {
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType()
				.getValueCode())) {
			JXTable otherTable = ui.incrementPanel.getTblOther();
			com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge model = (com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge) otherTable
					.getModel();
			List<OtherChargeVo> data = model.getData();
			Boolean bool = true;
			if (data != null && !data.isEmpty()) {
				for (OtherChargeVo vo : data) {
					if (PriceEntityConstants.PRICING_CODE_QS.equals(vo
							.getCode())) {
						bool = false;
						break;
					}
				}
			}
			if (bool) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateReturnBill"));
			}
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
		
		/**
         * 选择目的站为外发虚拟快递营业部且有代收货款时，校验代收货款是否超限制
         * 如果代收货款大于代收上限就给出提示
         * @author:280747-foss-zhuxue
		 * @date 2015-10-301下午04:35:17
         */
		if (bean.getCustomerPickupOrgCode() != null) {
			if (waybillService.querySaleDepartmentByCodeNoCache(bean
					.getCustomerPickupOrgCode().getCode()) != null) {
				// 通过提货网点返回含有代收货款上限的实体
				SaleDepartmentEntity IsaleDepartmentEntity = waybillService
						.querySaleDepartmentByCodeNoCache(bean
								.getCustomerPickupOrgCode().getCode());
				if (IsaleDepartmentEntity.getAgentCollectedUpperLimit() != null
						&& codAmount.compareTo(new BigDecimal(
								IsaleDepartmentEntity
										.getAgentCollectedUpperLimit())) > 0) {
					// 获取提货网店名称
					String loadName = bean.getCustomerPickupOrgName();
					if (bean.getCustomerPickupOrgName() != null
							|| "".equals(bean.getCustomerPickupOrgName())
							&& loadName.endsWith("远郊快递营业部")) {
						// 超过该营业部代收上限，请重新选择目的站.
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit"));
					}
				}
			}
		}

		if (codAmount == null || codAmount.compareTo(zero) == 0) {

			DataDictionaryValueVo vo = bean.getRefundType();
			if (vo != null && vo.getValueCode() != null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateCod.one")
								+ vo.getValueName()
								+ i18n.get("foss.gui.creating.calculateAction.exception.validateCod.two"));
			}
		} else {
			if (bean.getRefundType() == null
					|| bean.getRefundType().getValueCode() == null) {
				ui.incrementPanel.getTxtCashOnDelivery().requestFocus();
				// 将退款类型设置为空
				ExpCommon.setRefundType(bean, ui);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateCod.four"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean
						.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName())
							|| StringUtils.isEmpty(bean.getAccountCode())
							|| StringUtils.isEmpty(bean.getAccountBank())) {
						if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext
								.get(WaybillConstants.LOGIN_TYPE).toString())) {
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.calculateAction.exception.validateCod.five"));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 验证客户信息 -- 联系人不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-18 上午09:55:02
	 */
	private void validateCustomer(WaybillPanelVo bean) {
		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			if (StringUtils.isEmpty(bean.getReceiveCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.innerPickUp.receiveCustomer"));
			}
		} else {
			if (StringUtils.isEmpty(bean.getDeliveryCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.deliveryCustomer"));
			}

			if (StringUtils.isEmpty(bean.getReceiveCustomerContact())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateCustomer.receiveCustomer"));
			}
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
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
				.getReturnBillType().getValueCode())) {
			JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
			AddressFieldDto consignerAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText()) || consignerAddress == null
					|| StringUtils.isBlank(consignerAddress.getProvinceId())
					|| StringUtils.isBlank(bean.getDeliveryCustomerAddress())) {
				ui.consignerPanel.getTxtConsignerArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
			}
		}

		if (bean.getReceiveMethod() == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.transferInfoPanel.receiveMethod.isNull"));
		}
		// 提货方式
		String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.DELIVER_NOUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code)
				|| WaybillConstants.DELIVER_STORAGE.equals(code)
				|| WaybillConstants.DELIVER_UP.equals(code)
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			JAddressFieldForExp jd = ui.consigneePanel.getTxtConsigneeArea();
			AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText()) || consigneeAddress == null
					|| StringUtils.isBlank(consigneeAddress.getProvinceId())
					|| StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}
		
		// 如果不是导入订单开单,则校验省市区是否规范，因为订单省市区业务规则与FOSS不一致，因此不做校验（MANA-1598）
		if (StringUtils.isEmpty(bean.getOrderNo())) {
			// 判断发货人省市区文本框是否为空
			String regionNerText = ui.consignerPanel.getTxtConsignerArea()
					.getText();
			if (StringUtils.isNotEmpty(regionNerText)) {
				// 判断是否为选择的省市区
				if (regionNerText.indexOf("-") == -1) {
					// 请选择正确的发货人省市区
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.consignerPanel.regionAddress.alert"));
				}
			}
			// 判断收货人省市区文本框是否为空
			String regionNeeText = ui.consigneePanel.getTxtConsigneeArea()
					.getText();
			if (StringUtils.isNotEmpty(regionNeeText)) {
				// 判断是否为选择的省市区
				if (regionNeeText.indexOf("-") == -1) {
					// 请选择正确的收货人省市区
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.consigneePanel.regionAddress.alert"));
				}
			}
		}

	}

	/**
	 * 
	 * 验证付款方式
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-16 下午3:32:27
	 * @see
	 */
	private void validatePaidMethod(WaybillPanelVo bean) {
		//校验是否是裹裹的运单，是裹裹的读取FOSS结算裹裹支付信息暂存表，存在费用信息且大于0，只允许网上支付
		if(StringUtils.equals("12", bean.getServerType())){
			boolean greenHandWrapWriteoffService = waybillService.greenHandWrapWriteoffService(bean.getWaybillNo());
			if(greenHandWrapWriteoffService){
				//存在结算数据，只能是网上支付
				if (!WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException("裹裹订单已付款，付款方式请选择“网上支付”");
				}
			}
		}
		
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
				.getValueCode())) {
			String servicetype = waybillService.queryOrderByOrderNo(bean.getOrderNo());
			if (StringUtils.isEmpty(bean.getDeliveryCustomerCode())) {
				//没有客户编码时，裹裹的单子也可以开网上支付（订单的serviceType为12为裹裹的订单）
				if(null==servicetype||!"12".equals(servicetype)){
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillSubmitAction.exception.paidMethod.onlinePayNoCustCode"));
				}
			}
			if (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean
					.getOrderChannel())
					&& !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean
							.getOrderPayment())) {
				if(null==servicetype||!"12".equals(servicetype)){
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.paidMethod.onlinePayForDeppon"));
				}
			}
		}
	}

	/**
	 * 
	 * 判断包装件数不能大于开单件数
	 * 
	 * @author WangQianJin
	 * @date 2013-04-16
	 */
	private void validatePack(WaybillPanelVo bean) {
		Integer qtyTotal = bean.getGoodsQtyTotal();// 总件数

		// 木架信息判空操作
		if (null == bean.getPaper() || null == bean.getWood()
				|| null == bean.getFibre() || null == bean.getSalver()
				|| null == bean.getMembrane()) {
			// 抛出异常信息
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
		}

		/**
		 * 判断其他信息是否为空或者为空格或者为0
		 */
		if (bean.getOtherPackage() == null
				|| "".equals(bean.getOtherPackage())
				|| (bean.getOtherPackage() != null && "0".equals(bean
						.getOtherPackage()))) {
			// 木架信息判空操作
			if (0 == bean.getPaper().intValue()
					&& 0 == bean.getWood().intValue()
					&& 0 == bean.getFibre().intValue()
					&& 0 == bean.getSalver().intValue()
					&& 0 == bean.getMembrane().intValue()) {
				// 抛出异常信息
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
			}
		}

		Integer paper = Integer.valueOf(bean.getPaper());// 纸
		Integer wood = Integer.valueOf(bean.getWood());// 木
		Integer fibre = Integer.valueOf(bean.getFibre());// 纤
		Integer salver = Integer.valueOf(bean.getSalver());// 托
		Integer membrane = Integer.valueOf(bean.getMembrane());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));
		}

		// 判断包装体积是否大于货物总体积
		if (bean.getWood() != null && bean.getWood() > 0) {
			BigDecimal standGoodsVol = bean.getStandGoodsVolume() == null ? BigDecimal.ZERO
					: bean.getStandGoodsVolume();
			BigDecimal boxGoodsVol = bean.getBoxGoodsVolume() == null ? BigDecimal.ZERO
					: bean.getBoxGoodsVolume();
			if (standGoodsVol.add(boxGoodsVol).compareTo(
					bean.getGoodsVolumeTotal()) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validatePack.volume"));
			}
		}
	}

	private void validateWaybillNo(WaybillPanelVo bean) {
		if (StringUtils.isNotEmpty(bean.getWaybillNoImported())) {
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
					.getWaybillstatus())) {
				if (!bean.getWaybillNoImported().equals(bean.getWaybillNo())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validatewaybill.pdanochangewaybillNo"));
				}
			}
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
		if (WaybillConstants.DELIVER_NOUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code)
				|| WaybillConstants.DELIVER_STORAGE.equals(code)
				|| WaybillConstants.DELIVER_UP.equals(code)
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			AddressFieldDto consigneeAddress = ui.consigneePanel
					.getTxtConsigneeArea().getAddressFieldDto();
			if (consigneeAddress == null
					|| StringUtils.isEmpty(consigneeAddress.getProvinceId())
					|| StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}
	}

	/**
	 * 
	 * 有退款类型必须有客户编码
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-12 下午3:26:37
	 * @param bean
	 * @see
	 */
	private void validateRefundType(WaybillPanelVo bean) {
		DataDictionaryValueVo vo = bean.getRefundType();
		// 有退款必须要有客户编码
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null
					|| "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.common.exception.refundType"));
    		}
			// 除审核退外都必须有银行账户信息
			if (!WaybillConstants.REFUND_TYPE_VALUE.equals(vo.getValueCode())
					&& (bean.getAccountCode() == null || "".equals(bean
							.getAccountCode()))) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.common.exception.refundTypeNoAccount"));
    		}
		}
	}

	/**
	 * 
	 * 验证整车
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-2 下午06:06:50
	 */
	private void validateVehicleNumber(WaybillPanelVo bean) {
		if (bean.getIsWholeVehicle()) {
			// 约车报价不能为空或为0
			if (bean.getWholeVehicleAppfee() == null
					|| bean.getWholeVehicleAppfee().intValue() == 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.ui.incrementPanel.wholeVehicleAppfeeIsNullOrZero"));
			}
			// 开单报价不能为空或为0
			if (bean.getTransportFee() == null
					|| bean.getTransportFee().intValue() == 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.ui.incrementPanel.transportFeeIsNullOrZero"));
			}
			// 没有经过营业部则不允许到付金额大于0
			if (!bean.getIsPassDept()) {
				if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.vehicle"));
				}
			}

			BigDecimal rateLow = bean.getWholeVehicleActualfeeFlowRangeLow();
			if (rateLow == null) {
				rateLow = BigDecimal.valueOf(0.1);
			}
			BigDecimal lowrate = BigDecimal.ONE.add(rateLow);
			BigDecimal lowLevel = BigDecimal.ZERO;
			if (bean.getWholeVehicleAppfee() != null) {
				lowLevel = bean.getWholeVehicleAppfee().multiply(lowrate);
			}
			if (bean.getTransportFee().compareTo(lowLevel) < 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.listener.Waybill.transportFee.four")
								+ lowLevel.doubleValue());
		}
	}
	}

	/**
	 * 
	 * 验证走货路径
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-25 下午3:36:16
	 * @param bean
	 * @see
	 */
	private void validateFreightRout(WaybillPanelVo bean) {
		if (!bean.getIsWholeVehicle()) {
			if (StringUtil.isEmpty(bean.getLoadLineName())
					|| StringUtil.isEmpty(bean.getLoadLineCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.one"));
    		}
		}
		if (StringUtil.isEmpty(bean.getLoadOrgCode())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.two"));
		}

		if (StringUtil.isEmpty(bean.getLastLoadOrgCode())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.three"));
		}

		// 区分AB货时必须选择货物类型
//		if (bean.getGoodsTypeIsAB() != null && bean.getGoodsTypeIsAB()) {
//			bean.setGoodsTypeIsAB(false);
			// if(StringUtils.isEmpty(bean.getGoodsType())) {
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFreightRoute.four"));
			// }
//		}
	}

	/**
	 * 在pda的情况下  必须输入手写先付金额
	 */
	private void validatePdaHandwriteFee(WaybillPanelVo bean) {
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus()) && (bean.getHandWriteMoney() == null)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullHandWriteMoney"));

		}
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())
				&& (bean.getHandWriteMoney().compareTo(BigDecimal.ZERO) == 0 && bean
						.getPrePayAmount().compareTo(BigDecimal.ZERO) != 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullHandWriteMoney"));

		}

	}

	/**
	 * 在上门接货的时候 司机工号必填
	 * 
	 * @param bean
	 */
	private void validateDriverNo(WaybillPanelVo bean) {
		bean.setPickupToDoor(false);
		if (bean.getPickupToDoor() != null && bean.getPickupToDoor()
				&& StringUtils.isEmpty(bean.getDriverCode())) {

			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullDriverCode"));
		}
	}

	/**
	 * 
	 * 手机及电话号码必须录入一个
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午03:49:00
	 */
	private void validatePhone(WaybillPanelVo bean) {
		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}

		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMobilephoneOrTel"));
		}

		if (StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())) {
			String mobilePhone = bean.getDeliveryCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor()
						.validateDeliveryCustomerMobilephone(mobilePhone, bean);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					MsgBox.showInfo(result);
				}
			}

		}

		if (StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())) {
			String mobilePhone = bean.getReceiveCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor()
						.validateDeliveryCustomerMobilephone(mobilePhone, bean);
				// mobilePhone = mobilePhone.substring(0, 1);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					MsgBox.showInfo(result);
				}
			}

		}

		// 原件签收单返回
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
				.getReturnBillType().getValueCode())) {
			AddressFieldDto consignerAddress = ui.consignerPanel
					.getTxtConsignerArea().getAddressFieldDto();
			if (consignerAddress == null
					|| StringUtils.isEmpty(consignerAddress.getProvinceId())
					|| StringUtils.isEmpty(bean.getDeliveryCustomerAddress())) {
				ui.consignerPanel.getTxtConsignerArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
			}
		}
	}

	/**
	 * 
	 * 验证空运业务
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 上午09:19:51
	 */
	private void validateAir(WaybillPanelVo bean) {
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.one"));
				}
			}

			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateArrviePayment.two"));
			}
		}
	}

	/**
	 * 
	 * 验证各种费
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-9 下午12:09:27
	 * @param bean
	 * @see
	 */
	private void validateFee(ExpWaybillPanelVo bean) {
		// 总运费不能为负
		String returnType = null;
		String receiveMethod = null;
		if (bean.getReturnType() != null) {
			returnType = bean.getReturnType().getValueCode();
		}

		if (bean.getReceiveMethod() != null) {
			receiveMethod = bean.getReceiveMethod().getValueCode();
		}

		if (bean.getTotalFee().compareTo(BigDecimal.ZERO) < 0) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFee.one"));
		}

		// if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType)
		// && ! WaybillConstants.INNER_PICKUP.equals(receiveMethod)
		// && StringUtils.isEmpty(bean.getPromotionsCode())){
		// if(bean.getTotalFee().compareTo(BigDecimal.ZERO) <= 0){
		// throw new
		// WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validateFee.one"));
		// }
		// }
		/**
		 * 如果不是整车，才对送货费验证
		 * 
		 * if (!bean.getIsWholeVehicle()) { //如果是送货并且为非免费送货，则送货费不允许为0
		 * if(WaybillConstants
		 * .DELIVER_INGA_AIR.equals(bean.getReceiveMethod().getValueCode()) ||
		 * WaybillConstants
		 * .DELIVER_NOUP.equals(bean.getReceiveMethod().getValueCode()) ||
		 * WaybillConstants
		 * .DELIVER_NOUP_AIR.equals(bean.getReceiveMethod().getValueCode()) ||
		 * WaybillConstants
		 * .DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode()) ||
		 * WaybillConstants
		 * .DELIVER_UP.equals(bean.getReceiveMethod().getValueCode()) ||
		 * WaybillConstants
		 * .DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode())){
		 * if(bean.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO) == 0){ throw
		 * new WaybillValidateException(i18n.get(
		 * "foss.gui.creating.waybillSubmitAction.exception.validateFee.two"));
		 * } } }
		 */
		/*
		 * if(bean.getVipInsuranceAmount()==null&&bean.getMaxInsuranceAmount()!=null
		 * && bean.getInsuranceAmount()!=null){
		 * if(bean.getMaxInsuranceAmount().doubleValue
		 * ()<bean.getInsuranceAmount().doubleValue()){ throw new
		 * WaybillValidateException(i18n.get(
		 * "foss.gui.creating.waybillSubmitAction.exception.validateFee.insurr")
		 * +bean.getMaxInsuranceAmount().toPlainString()+i18n.get(
		 * "foss.gui.creating.waybillSubmitAction.exception.validateFee.insurr2"
		 * ));
		 * 
		 * } }
		 */
			}

	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProduct(WaybillPanelVo bean) {
		// 判断产品是否偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean
				.getProductCode().getCode())) {
			BigDecimal insuranceAmount = bean.getInsuranceAmount();
			BigDecimal maxInsuranceAmount = bean.getMaxInsuranceAmount();
			if (insuranceAmount == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullInsuranceAmount"));
			}
			if (maxInsuranceAmount == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullMaxInsuranceAmount"));
			}
			if (insuranceAmount.compareTo(maxInsuranceAmount) > 0) {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.overMaxInsuranceAmount")
								+ maxInsuranceAmount);
			}
		}

		// 判断产品是否空运
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean
				.getProductCode().getCode())) {
			// 合票方式
			DataDictionaryValueVo freightMethod = bean.getFreightMethod();
			if (freightMethod == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFreightMethod"));
			}

			// 航班类别
			DataDictionaryValueVo flightNumberType = bean.getFlightNumberType();
			if (flightNumberType == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullFlightNumberType"));
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
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitAction.exception.nullPrePayAmount"));
			}
		}
	}

	// TODO
	/**
	 * 校验伙伴名称
	 */
	private void valdatePartnerName(ExpWaybillPanelVo bean) {
		if (StringUtils.isEmpty(bean.getPartnerName())) {
			return;
		}
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{1,70}");
		Matcher matcher = pattern.matcher(bean.getPartnerName());
		boolean b = matcher.matches();
		if (!b) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.validatePartnerName"));
		}
	}

	/**
	 * 校验伙伴电话
	 */
	private void valdatePartnerPhone(ExpWaybillPanelVo bean) {
		if (StringUtils.isEmpty(bean.getPartnerPhone())) {
			return;
		}
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(bean.getPartnerPhone());
		boolean b = matcher.matches();
		if (!b) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.validatePartnerPhome"));
		}
	}

	/**
	 * DMANA-638,对上海奉贤区奉城镇营业部放开快递运单开多件货功能
	 */
	private boolean valdateExpMultiPieceDept(WaybillPanelVo bean) {
		ConfigurationParamsEntity config = waybillService
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.EXP_MULTI_PIECE_DEPTCODE,
						FossConstants.ROOT_ORG_CODE);
		if (config != null) {
			if (bean.getCreateOrgCode().equals(config.getConfValue())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 将运单界面值传过来
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-30 下午6:42:22
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
