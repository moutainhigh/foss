package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changingexp.client.vo.ExpReturnedGoodsWaybillVo;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.define.FossConstants;

public class ImportReturnGoodsAction {
	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ImportReturnGoodsAction.class);

	private static final int TEN = 10;
	

	private static final double NUM_ZERO_POINT_ZERO_ONE = 0.01;

	private static final double NUM_ZERO_POINT_ONE = 0.1;

	/**
	 * 运单服务
	 * 提供运单相关的本地服务接口
	 */
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 离线暂存
	 * 提供运单离线暂存相关的本地服务接口
	 */
	private IWaybillOffLinePendingService waybillOffLinePendingService = GuiceContextFactroy.getInjector().getInstance(WaybillOffLinePendingService.class);

	/**
	 * 国际化
	 * 提供i18n国际化相关的服务接口
	 */
	private static II18n i18n = I18nManager.getI18n(ImportReturnGoodsAction.class);

	// 工具类获取branchVo对象
	BusinessUtils bu = new BusinessUtils();
	//开单界面
	ExpWaybillEditUI ui;
	
	
	
	/**
	 * 导入返货管理运单
	 * 
	 * @author 199293
	 * @date 2015-2-4 
	 */
	public void importWaybillEditUI(Object object) {
		try {
			if (null == object) {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullObject"));
			}
			//返货管理VO
			ExpReturnedGoodsWaybillVo value = (ExpReturnedGoodsWaybillVo)object;
			
			//获取UI界面
			ui = getWaybillEditUI();
			//获取UI界面控件
			ExpWaybillPanelVo vo = ui.getBindersMap().get("waybillBinder").getBean();
			//设置开单类型
			DefaultComboBoxModel model = ui.getCombWaibillReturnModeModel();
			for(int i = 0 ; i < model.getSize() ; i++){
				DataDictionaryValueVo ddvv = (DataDictionaryValueVo) model.getElementAt(i);
				if(ddvv != null && 
						!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(ddvv.getValueCode())){
					if(StringUtil.isNotEmpty(value.getReturnModeString())
							&& value.getReturnModeString().equals(ddvv.getValueName())){
						vo.setReturnType(ddvv);
						break;
					}
				}
			}
			vo.setOriginalWaybillNo(value.getWaybillNo());
			//对返货快递开单界面设值
			originalWaybillNoListener(vo);
			vo.setCodAmount(value.getGoodsPayment());
			//vo.setReceiveCustomerAddress(value.getAddress());
			ui.basicPanel.getComboBoxReturnType().setEnabled(false);
            //ui.consigneePanel.getTxtConsigneeAddress().setEnabled(false);
			ui.cargoInfoPanel.getTxtTotalPiece().setEnabled(false);
			ui.cargoInfoPanel.getTxtWeight().setEnabled(false);
			ui.cargoInfoPanel.getTxtSize().setEnabled(false);
			ui.cargoInfoPanel.getTxtVolume().setEnabled(false);
			ui.cargoInfoPanel.getTxtWood().setEditable(false);
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
			setLoadLine(vo);
		} catch (BusinessException ex) {
			LOGGER.error("导入异常", ex);			
			if(StringUtils.isNotEmpty(ex.getErrorCode())){
			    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
			}
		}
	}
	
	/**
	 * 返货快递开单界面
	 * 
	 * @author 199293
	 * @date 2015-2-4
	 */
	public ExpWaybillEditUI getWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		ExpWaybillEditUI ui = openUIActionAndReturn(application);
		ui.setApplication(application);
		return ui;
	}
	
	/**
	 * 
	 * 返货快递开单界面
	 * @author 199293
	 * @date 2015-2-4 
	 */
    public ExpWaybillEditUI openUIActionAndReturn(IApplication application) {
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillReturn.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
	
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		waybillEditUI.openUI();
		return waybillEditUI;
    }
    
    /**
	 * 修改了原始运单号以后会调用
	 * @param bean
	 */
	private void originalWaybillNoListener(ExpWaybillPanelVo bean) {
		//crm返货信息
		CrmReturnedGoodsDto d = null ;
		if(StringUtils.isEmpty(bean.getOriginalWaybillNo())){
			return;
		}
		
		if(bean.getReturnType()==null 
			|| StringUtils.isEmpty(bean.getReturnType().getValueCode())){
			bean.setOriginalWaybillNo(null);
			return;
		}
		
		if (!NumberValidate.checkBetweenLength(bean.getOriginalWaybillNo(), TEN, TEN)) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.lengthexp"));
			bean.setOriginalWaybillNo(null);
			 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
			return;
		} 
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			//这里要在线开单才可以
		
			IWaybillHessianRemoting waybillRemotingService  = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
			WaybillDto dto = waybillRemotingService.queryWaybillByNo(bean.getOriginalWaybillNo());
			
			//运单号查不到 无效运单号
			if(dto==null || dto.getWaybillEntity()==null){
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notexist"));
				bean.setOriginalWaybillNo(null);
				 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
				return;
			}
			
			//不是包裹 不能做返货返单
			if(!CommonUtils.directDetermineIsExpressByProductCode(dto.getWaybillEntity().getProductCode())){
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notexpress"));
				bean.setOriginalWaybillNo(null);
				return;
			}
			
//			//没有签收的情况下 不能做返货或者返单
//			if(!dto.isAllSigned()){
//				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notsign"));
//				bean.setOriginalWaybillNo(null);
//				return;
//			}
			//判断该运单号是否创建了返货申请且已受理 
			CrmReturnedGoodsDto crmReturnedGoodsDto = new CrmReturnedGoodsDto();
			crmReturnedGoodsDto.setOriWaybill(bean.getOriginalWaybillNo());
			List<CrmReturnedGoodsDto> queryResult = waybillService.queryReturnGoodsWorkOrder(crmReturnedGoodsDto);
			int resultSize = queryResult.size();
			
			if(resultSize == 1){
				d=queryResult.get(0);
			}else if(resultSize > 1){
				//取最新的数据
				long[] time = new long[resultSize];
				Date date;
				for(int i=0;i<resultSize;i++){
					date = queryResult.get(i).getCreateTime();
					if(date!=null && !date.toString().equals("")){
						time[i] = queryResult.get(i).getCreateTime().getTime();
					}
				}
				long max=0;
				for(int j=0;j<time.length;j++){
					if(time[j]>max){
						max=time[j];
					}
				}
				Date maxDate = new Date(max);
				//数据创建时间相同个数
				int value = 0;
				for(int i=0;i<resultSize;i++){
					date = queryResult.get(i).getCreateTime();
					if(date!=null && !date.toString().equals("")){
						if(maxDate.equals(date)){
							d=queryResult.get(i);
							value++;
						}
					}
				}
				//数据创建时间相同个数大于1时，抛出异常
				if(value>1){
					bean.setOriginalWaybillNo(null);
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.createDateIsSame") + "${user.home}/foss-framework.log");
				}
			}
			if(d!=null){
				if (!((WaybillConstants.RETURNTYPE_OTHER_CITY.equals(d
						.getReturnType()) || WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE
						.equals(d.getReturnType())||WaybillConstants.RETURN_BACK
						.equals(d.getReturnType()))
						&& WaybillConstants.ACCEPTSTATUS_HANDLED.equals(d
								.getReturnStatus()))) {
				  throw new WaybillValidateException("原单号返货申请未受理或者返货类型不是非同城转寄或退回件");
				}
				//收货客户手机
//				dto.getWaybillEntity().setReceiveCustomerMobilephone(d.getReturnPhoneReceive());
//				//收货客户电话
//				dto.getWaybillEntity().setReceiveCustomerPhone(d.getReturnTelReceive());
//				//收货客户名称
//				dto.getWaybillEntity().setReceiveCustomerName(d.getReturnManReceive());
//				//收货客户编码
//				dto.getWaybillEntity().setReceiveCustomerCode("");
//				//收货联系人
//				dto.getWaybillEntity().setReceiveCustomerContact("");
//				//行政区域  省、市、区
//				dto.getWaybillEntity().setReceiveCustomerProvCode(d.getReturnProvinceId());
//				dto.getWaybillEntity().setReceiveCustomerCityCode(d.getReturnCityId());
//				dto.getWaybillEntity().setReceiveCustomerDistCode(d.getReturnAreaId());
//				//具体地址
//				dto.getWaybillEntity().setReceiveCustomerAddress(d.getReturnDetailaddress());
			}
			//根据原单号查询运单号
			String wayBillNo = waybillService.getWaybillNo(bean.getOriginalWaybillNo());
			if(StringUtil.isNotEmpty(wayBillNo)){
				bean.setWaybillNo(wayBillNo);
				bean.setWaybillstatus(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				if(StringUtil.isEmpty(bean.getOldWaybillNo())){
					bean.setOldWaybillNo(wayBillNo);
				}
			}
			//这里要判断将原单回写到新的运单表中去 同时将很多地方置为灰色 不可编辑 
			importWaybillVoData(dto , d);
			
			//设置保价的值
			if(d != null){
				bean.setInsuranceAmount(d.getReturnMoneyInsured());
				//设置公司原因还是自己原因
				bean.setReturnBillReason(d.getReturnReason());
			}
			setWaybillUiByReturnType(true);
			
			importOrder(bean,dto);
			
		}else{
			//离线的时候因为无法导入原始单 也无法检查原始单是否被签收等信息 所以是不能开单的
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo"));
			bean.setOriginalWaybillNo(null);
			return;
		}
		
		
	}

	/**
	 * @param dto
	 */
	private void importWaybillVoData(WaybillDto value,CrmReturnedGoodsDto d) {
		//业务工具类
		BusinessUtils businessUtils = new BusinessUtils();
		// 获取绑定bean
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo vo = waybillBinder.getBean();
		// 拷贝数据
		DataDictionaryValueVo valueVo = vo.getReturnType();
		String returnType = null;
		if(valueVo!=null){
			returnType = valueVo.getValueCode();
		}
		if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType)){
			if(value.getWaybillExpressEntity()!=null 
			&& !FossConstants.YES.equals(value.getWaybillExpressEntity().getCanReturnCargo())){
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.cannotReturnCargo"));
				return;
			}
		}
		if(d != null){
			vo.setReceiveCustomerId("");// 发货客户ID
			vo.setReceiveCustomerContactId("");// 发货客户联系人ID
			vo.setReceiveCustomerCode("");// 发货客户编码
			vo.setReceiveBigCustomer("");//大客户标记
			vo.setReceiveCustomerName("");// 发货客户名称
			vo.setReceiveCustomerMobilephone(CommonUtils.defaultIfNull(d.getReturnPhoneReceive()));// 发货客户手机
			vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(d.getReturnTelReceive()));// 发货客户电话
			vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(d.getReturnManReceive()));// 发货客户联系人
			// 发货具体地址
			vo.setReceiveCustomerAddress(CommonUtils.defaultIfNull(d.getReturnDetailaddress()));
			/**
			 * 发货客户省市区
			 */
			AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(d.getReturnProvinceId(), 
					d.getReturnCityId(), d.getReturnAreaId());
			if(null != consignerAddress){
			    // 发货省份
			    vo.setReceiveCustomerProvCode(consignerAddress.getProvinceId());
			    // 发货市
			    vo.setReceiveCustomerCityCode(consignerAddress.getCityId());
			    // 发货区
			    vo.setReceiveCustomerDistCode(consignerAddress.getCountyId());
			    // 发货区域
			    vo.setReceiveCustomerAreaDto(consignerAddress);
			    vo.setReceiveCustomerArea(businessUtils.getAddressAreaText(consignerAddress));
			    /**
			     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			     */
			    ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(consignerAddress);
			}
		}else{
			vo.setReceiveCustomerId(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerId()));// 发货客户ID
			vo.setReceiveCustomerContactId(value.getWaybillEntity().getDeliverCustContactId());// 发货客户联系人ID
			vo.setReceiveCustomerCode(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerCode()));// 发货客户编码
			vo.setReceiveBigCustomer(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryBigCustomer()));//大客户标记
			vo.setReceiveCustomerName(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerName()));// 发货客户名称
			vo.setReceiveCustomerMobilephone(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerMobilephone()));// 发货客户手机
			vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerPhone()));// 发货客户电话
			vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerContact()));// 发货客户联系人
			// 发货具体地址
			vo.setReceiveCustomerAddress(value.getWaybillEntity().getDeliveryCustomerAddress());
			/**
			 * 发货客户省市区
			 */
			AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(value.getWaybillEntity().getDeliveryCustomerProvCode(), 
					value.getWaybillEntity().getDeliveryCustomerCityCode(), value.getWaybillEntity().getDeliveryCustomerDistCode());
			if(null != consignerAddress){
			    // 发货省份
			    vo.setReceiveCustomerProvCode(consignerAddress.getProvinceId());
			    // 发货市
			    vo.setReceiveCustomerCityCode(consignerAddress.getCityId());
			    // 发货区
			    vo.setReceiveCustomerDistCode(consignerAddress.getCountyId());
			    // 发货区域
			    vo.setReceiveCustomerAreaDto(consignerAddress);
			    vo.setReceiveCustomerArea(businessUtils.getAddressAreaText(consignerAddress));
			    /**
			     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			     */
			    ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(consignerAddress);
			}
		}
		
		
		vo.setDeliveryCustomerId(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerId()));// 发货客户ID
		vo.setDeliveryCustomerContactId(value.getWaybillEntity().getDeliverCustContactId());// 发货客户联系人ID
		vo.setDeliveryCustomerCode(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerCode()));// 发货客户编码
		vo.setDeliveryCustomerName(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerName()));// 发货客户名称
		vo.setDeliveryCustomerMobilephone(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerMobilephone()));// 发货客户手机
		vo.setDeliveryCustomerPhone(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerPhone()));// 发货客户电话
		vo.setDeliveryCustomerContact(CommonUtils.defaultIfNull(value.getWaybillEntity().getDeliveryCustomerContact()));// 发货客户联系人
		// 发货具体地址
		vo.setDeliveryCustomerAddress(value.getWaybillEntity().getDeliveryCustomerAddress());
		/**
		 * 发货客户省市区
		 */
		AddressFieldDto consigneeAddress = businessUtils.getProvCityCounty(value.getWaybillEntity().getDeliveryCustomerProvCode(), 
				value.getWaybillEntity().getDeliveryCustomerCityCode(), value.getWaybillEntity().getDeliveryCustomerDistCode());
		if(null != consigneeAddress){
		    // 发货省份
		    vo.setDeliveryCustomerProvCode(consigneeAddress.getProvinceId());
		    // 发货市
		    vo.setDeliveryCustomerCityCode(consigneeAddress.getCityId());
		    // 发货区
		    vo.setDeliveryCustomerDistCode(consigneeAddress.getCountyId());
		    // 发货区域
		    vo.setDeliveryCustomerAreaDto(consigneeAddress);
		    vo.setDeliveryCustomerArea(businessUtils.getAddressAreaText(consigneeAddress));
		    /**
		     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
		     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
		     */
		    ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(consigneeAddress);
		}
		
		ExpCommon.setProductCode(ui, vo, CommonUtils.defaultIfNull(value.getWaybillEntity().getProductCode()));
		// 根据运输性质的改变，改变提货方式
		ExpCommon.changePickUpMode(vo, ui);
		
		// 空运、偏线以及中转下线无法选择签收单返单
		ExpCommon.setReturnBill(vo,ui);
		// 偏线与空运不能选择预付费保密
		ExpCommon.setSecretPrepaid(vo,ui);
		
		// 提货方式
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setReceiveMethod(ExpCommon.getCombBoxValue(ui.getPikcModeModel(),  WaybillConstants.DELIVER_UP));
		}else{
			vo.setReceiveMethod(ExpCommon.getCombBoxValue(ui.getPikcModeModel(),  value.getWaybillEntity().getReceiveMethod()));
		}		
		//设置提货网点
		if( value.getWaybillEntity().getReceiveOrgCode()!=null){
			BranchVo pickup = businessUtils.getCustomerPickupOrg( value.getWaybillEntity().getReceiveOrgCode(),
					value.getWaybillEntity().getProductCode(), value.getWaybillEntity().getBillTime());			
			if(pickup!=null){
				//这个是为了判断直接开单返货单与补录返货单的区别
				if(vo.getTargetOrgCode()!=null){
					pickup.setCode(vo.getCustomerPickupOrgCode().getCode());
				}else{				
					vo.setCustomerPickupOrgCode(pickup);// 提货网点
					vo.setCustomerPickupOrgName(pickup.getName());//提货网点名称
					vo.setTargetOrgCode(pickup.getTargetOrgName());// 目的站					
				}
				//是否可代收货款
				vo.setCanAgentCollected(pickup.getCanAgentCollected());
				//是否可货到付款
				vo.setArriveCharge(pickup.getArriveCharge());
			}
			SalesDepartmentService salesDepartmentService 
				= DownLoadDataServiceFactory.getSalesDepartmentService();
			SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
			dto.setSalesDepartmentCode(pickup.getCode());
			SalesDepartmentCityDto result = 
					salesDepartmentService.querySalesDepartmentCityInfo(dto);
			SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory.getWaybillService()
					.querySaleDeptByCode(pickup.getCode());
			if(saleDepartmentEntity!=null && result!=null){
				//营业部是否可以快递接货，如果是的话 就是试点营业部
				result.setTestSalesDepartment(saleDepartmentEntity.getCanExpressPickupToDoor());
			}
			if(value.getWaybillEntity() != null && StringUtils.isNotEmpty(value.getWaybillEntity().getReceiveMethod())
					&& CommonUtils.directDetermineIsExpressByProductCode(value.getWaybillEntity().getProductCode())
					&& !CommonUtils.verdictPickUpSelf( value.getWaybillEntity().getReceiveMethod())){				
				if(result!=null && StringUtils.isNotEmpty(result.getCityName())){
					vo.setTargetOrgCode(result.getCityName());
				}else{
					//无试点城市
					vo.setTargetOrgCode("无试点城市");
				}
			}else{
				String simpleName = bu.getSimpleName(pickup.getCode(), vo.getBillTime());
				if (!"".equals(simpleName)) {
					vo.setTargetOrgCode(simpleName);
				} else {
					vo.setTargetOrgCode(pickup.getTargetOrgName());
				}
			}
			vo.setTargetSalesDepartmentCityDto(result);
		}
		
		vo.setReturnBillType(ExpCommon.getCombBoxValue(ui.getCombReturnBillTypeModel(),WaybillConstants.NOT_RETURN_BILL ));
		vo.setPickupToDoor(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getPickupToDoor()));// 是否上门接货
		vo.setDriverCode(value.getWaybillEntity().getDriverCode());// 司机
		vo.setPickupCentralized(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getPickupCentralized()));// 是否集中接货
		vo.setCarDirectDelivery(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getCarDirectDelivery()));// 是否大车直送
		
		if(  WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType)){
			vo.setGoodsName(i18n.get("foss.gui.creating.listener.Waybill.returnGo"));// 货物名称
		}else{
			vo.setGoodsName(value.getWaybillEntity().getGoodsName());// 货物名称
		}

		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setGoodsQtyTotal(value.getWaybillEntity().getGoodsQtyTotal());// 货物总件数
			vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(value.getWaybillEntity().getGoodsWeightTotal()));// 货物总重量
			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(value.getWaybillEntity().getGoodsVolumeTotal()));// 货物总体积
			vo.setGoodsSize(value.getWaybillEntity().getGoodsSize());// 货物尺寸
			vo.setGoodsType(value.getWaybillEntity().getGoodsTypeCode());// 货物类型
			
			vo.setPaper(value.getWaybillEntity().getPaperNum());// 纸
			if(vo.getPaper()==null){
				vo.setPaper(Integer.valueOf(0));
			}
			vo.setWood(value.getWaybillEntity().getWoodNum());// 木
			if(vo.getWood()==null){
				vo.setWood(Integer.valueOf(0));
			}
			
			vo.setFibre(value.getWaybillEntity().getFibreNum());// 纤
			if(vo.getFibre()==null){
				vo.setFibre(Integer.valueOf(0));
			}
			
			vo.setSalver(value.getWaybillEntity().getSalverNum());// 托
			if(vo.getSalver()==null){
				vo.setSalver(Integer.valueOf(0));
			}
			
			vo.setMembrane(value.getWaybillEntity().getMembraneNum());// 膜
			if(vo.getMembrane()==null){
				vo.setMembrane(Integer.valueOf(0));
			}
			vo.setOtherPackage(value.getWaybillEntity().getOtherPackage());// 其他
			vo.setGoodsPackage(value.getWaybillEntity().getGoodsPackage());// 货物包装
			ui.cargoInfoPanel.getTxtTotalPiece().setEditable(false);
			ui.cargoInfoPanel.getTxtVolume().setEditable(false);
			ui.cargoInfoPanel.getTxtWeight().setEditable(false);
			ui.cargoInfoPanel.getTxtSize().setEditable(false);
			ui.cargoInfoPanel.getTxtFibre().setEditable(false);
			ui.cargoInfoPanel.getTxtMembrane().setEditable(false);
			ui.cargoInfoPanel.getTxtOther().setEditable(false);
			ui.cargoInfoPanel.getTxtPaper().setEditable(false);
			ui.cargoInfoPanel.getTxtSalver().setEditable(false);
		}else{
			vo.setGoodsQtyTotal(Integer.valueOf(1));
			vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(BigDecimal.valueOf(NUM_ZERO_POINT_ONE)));// 货物总重量
			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(BigDecimal.valueOf(NUM_ZERO_POINT_ZERO_ONE)));// 货物总体积
			vo.setGoodsSize(null);// 货物尺寸
			vo.setGoodsType(null);// 货物类型
			vo.setPaper(Integer.valueOf(1));// 纸
			vo.setWood(Integer.valueOf(0));// 木
			vo.setFibre(Integer.valueOf(0));// 纤
			vo.setSalver(Integer.valueOf(0));// 托
			vo.setMembrane(Integer.valueOf(0));// 膜
			vo.setOtherPackage(null);// 其他
			vo.setGoodsPackage("1纸");// 货物包装
		}
		
		DataDictionaryValueVo outerNotes = new DataDictionaryValueVo();
		outerNotes.setValueCode(value.getWaybillEntity().getOuterNotes());
		vo.setOuterNotes(outerNotes);//对外备注
		vo.setInnerNotes(value.getWaybillEntity().getInnerNotes());// 对内备注
		vo.setTransportationRemark(value.getWaybillEntity().getTransportationRemark());// 储运事项
		
				//退款类型
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setRefundType(ExpCommon.getCombBoxValue(ui.getCombRefundTypeModel(),value.getWaybillEntity().getRefundType() ));
		}else{
			vo.setRefundType(null);
		}
		
		 //原单号应收到付款
		BigDecimal  receiveablePayAmoout =waybillService.queryFinanceSign(vo.getOriginalWaybillNo());
		
		if(receiveablePayAmoout!=null){
			//关联单号费用
			vo.setConnectionNumFee(receiveablePayAmoout);   
		}else{
			 //关联单号费用
			 vo.setConnectionNumFee(BigDecimal.ZERO); 
		}
		vo.setSecretPrepaid(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getSecretPrepaid()));// 预付费保密
		vo.setToPayAmount(BigDecimal.valueOf(0));// 到付金额
		vo.setPrePayAmount(BigDecimal.valueOf(0));// 预付金额
		
		// 优惠费用
		vo.setPromotionsFee(BigDecimal.valueOf(0));
		// 优惠费用画布
		vo.setPromotionsFeeCanvas(String.valueOf(BigDecimal.valueOf(0)));
		
		// 运费计费费率
		vo.setUnitPrice(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		
		// 公布价运费
		vo.setTransportFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 公布价运费画布
		vo.setTransportFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		vo.setValueAddFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));// 增值费用
		// 开单付款方式
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setPaidMethod(ExpCommon.getCombBoxValue(ui.getCombPaymentModeModel(),WaybillConstants.ARRIVE_PAYMENT));
		}else{
			vo.setPaidMethod(ExpCommon.getCombBoxValue(ui.getCombPaymentModeModel(),WaybillConstants.ARRIVE_PAYMENT));
		}
		// 根据设置的付款方式，设置接送货是否可编辑
		ExpCommon.selfPickup(vo, ui);
		// 总费用
		vo.setTotalFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 总费用画布
		vo.setTotalFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		vo.setForbiddenLine(value.getWaybillEntity().getForbiddenLine());// 禁行
		vo.setFreightMethod(ExpCommon.getCombBoxValue(ui.getFreightMethod(),value.getWaybillEntity().getFreightMethod()));// 合票方式
		//设置航班类型
		vo.setFlightNumberType(ExpCommon.getCombBoxValue(ui.getFlightNumberType(), value.getWaybillEntity().getFlightNumberType()));
		vo.setFlightShift(CommonUtils.defaultIfNull(value.getWaybillEntity().getFlightShift()));// 航班时间
		vo.setPromotionsCode("");// 优惠编码
		vo.setCreateTime(new Date());// 创建时间
		vo.setModifyTime(new Date());// 更新时间
		vo.setBillTime(new Date());// 开单时间

		vo.setCurrencyCode(value.getWaybillEntity().getCurrencyCode());// 币种
		vo.setIsWholeVehicle(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getIsWholeVehicle()));// 是否整车运单
		//是否经过营业部
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(value.getWaybillEntity().getIsPassOwnDepartment()));
		//整车约车编号
		vo.setVehicleNumber(StringUtil.defaultIfNull(value.getWaybillEntity().getOrderVehicleNum()));   
		vo.setWholeVehicleActualfee(CommonUtils.defaultIfNull(value.getWaybillEntity().getWholeVehicleActualfee()));// 整车开单报价
		vo.setWholeVehicleAppfee(CommonUtils.defaultIfNull(value.getWaybillEntity().getWholeVehicleAppfee()));// 整车约车报价
		vo.setAccountName(value.getWaybillEntity().getAccountName());// 返款帐户开户名称
		vo.setAccountCode(value.getWaybillEntity().getAccountCode());// 返款帐户开户账户
		vo.setAccountBank(value.getWaybillEntity().getAccountBank());// 返款帐户开户银行
		// 计费重量
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setBillWeight(CommonUtils.defaultIfNull(value.getWaybillEntity().getBillWeight()));
		}
		if(value.getWaybillExpressEntity()!=null 
				&&
		  WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)
				&& 
		  FossConstants.ACTIVE.equals(value.getWaybillExpressEntity().getChangeVolume())){
			vo.setChangeVolume(FossConstants.ACTIVE);
		}
		// 送货费
		vo.setDeliveryGoodsFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 送货费画布
		vo.setDeliveryGoodsFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		// 其他费用
		vo.setOtherFee( CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 其他费用画布
		vo.setOtherFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		// 包装手续费
		vo.setPackageFee( CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 包装手续费画布
		vo.setPackageFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		// 接货费
		vo.setPickupFee( CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 接货费画布
		vo.setPickUpFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		// 保价声明价值
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)){
			vo.setInsuranceAmount(CommonUtils.defaultIfNull(value.getWaybillEntity().getInsuranceAmount()));
			// 保价声明价值画布
			vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils.defaultIfNull(value.getWaybillEntity().getInsuranceAmount())));
			// 保价费率
			//将保险费率转换成千分率的格式
			BigDecimal permillageIns = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = CommonUtils.defaultIfNull(value.getWaybillEntity().getInsuranceRate()).multiply(permillageIns);
			vo.setInsuranceRate(feeRate);
			// 保价费
			vo.setInsuranceFee(CommonUtils.defaultIfNull( value.getWaybillEntity().getInsuranceFee()));
		}else{
			vo.setInsuranceAmount(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
			// 保价声明价值画布
			vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
			// 保价费率
			//将保险费率转换成千分率的格式
			BigDecimal permillageIns = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = BigDecimal.valueOf(0);
			vo.setInsuranceRate(feeRate);
			// 保价费
			vo.setInsuranceFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		}
		// 代收货款
		vo.setCodAmount(BigDecimal.valueOf(0));
		// 代收货款画布
		vo.setCodAmountCanvas(String.valueOf(BigDecimal.valueOf(0)));
		// 代收费率
		//将代收货款费率转换成千分率的格式
		BigDecimal permillageCod = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal codRate =BigDecimal.valueOf(0);// CommonUtils.defaultIfNull(waybillPending.getCodRate()).multiply(permillageCod);
		// 代收货款费率
		vo.setCodRate(codRate);
		// 代收货款手续费
		vo.setCodFee( CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		
		// 装卸费
		vo.setServiceFee( CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 装卸费画布
		vo.setServiceFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(BigDecimal.valueOf(0))));
		
		
		//公里数
		vo.setKilometer( value.getWaybillEntity().getKilometer());
		
		if(vo.getReceiveMethod()!=null){
			/**
			 * 如果非送货时，公里数不可录入，且要清空
			 */
			if (WaybillConstants.SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
					WaybillConstants.INNER_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_PICKUP_FREE.equals(vo.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIRPORT_PICKUP.equals(vo.getReceiveMethod().getValueCode())){
				vo.setKilometer(null);
				// 公里数不可编辑
				ui.transferInfoPanel.getTxtKilometer().setEditable(false);
			}else{
				// 公里数可编辑
				ui.transferInfoPanel.getTxtKilometer().setEditable(true);
			}
		}
		//是否电商尊享
		CusBargainEntity cusBarga =waybillService.queryCusBargainByCustCode(vo.getDeliveryCustomerCode());
		if(null!=cusBarga){
			vo.setIsElectricityToEnjoy(cusBarga.getIfElecEnjoy());
		}
		//setLoadLine(vo);
	}
	
	

	/**
	 * 设置预配线路和预计出发时间与预计到达时间 （摄取方法供GIS地图匹配网点使用）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 上午8:49:17
	 */
	public void setLoadLine(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() != null) {
			// 查询始发配载部门、最终配载部门以及线路
			queryLodeDepartmentInfo(bean);
			if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode()) && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				Date leaveTime = getLeaveTime(bean);
				if (leaveTime != null) {
					bean.setPreDepartureTime(leaveTime);// 预计出发时间
					bean.setPreCustomerPickupTime(getPickupDeliveryTime(bean));// 预计派送/自提时间
				} else {
					LOGGER.debug("未查询到对应的时效  ");
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.nullRelativeTime"));
				}
			}
		}
	}
	
	/**
	 * 
	 * 获得预计出发时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getLeaveTime(WaybillPanelVo bean) {
		
		Date leaveTime = waybillService.searchPreLeaveTime(bean.getCreateOrgCode(), bean.getLoadOrgCode(), bean.getProductCode().getCode(), new Date());
		return leaveTime;
	}
	
	/**
	 * 
	 * 获得预计派送/提货时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getPickupDeliveryTime(WaybillPanelVo bean) {
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(bean)) {
			effectiveDto = waybillService.searchPreSelfPickupTime(bean.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(bean.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getDeliveryDate();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		}
	}
	
	/**
	 * 
	 * 判断提货方式是否自提
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 上午09:46:55
	 */
	private Boolean isPickup(WaybillPanelVo bean) {
		/**
		 * 判断提货方式是否为空
		 */
		if(bean.getReceiveMethod()!=null){
			String code = bean.getReceiveMethod().getValueCode();
			if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))

			{
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}		
	}
	/**
	 * 
	 * 查询始发配载部门、最终配载部门以及线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午03:54:30
	 */
	private void queryLodeDepartmentInfo(WaybillPanelVo bean) {
		OrgInfoDto dto = null;
		try {
			// 运输性质非空判断
			if (null == bean.getProductCode()) {
				LOGGER.error("运输性质不能为空！");
				throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDepartmentDialog.exception.transType"));
			}

			if(bean.getPickupCentralized()!=null&&bean.getPickupCentralized()){
				dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(), bean.getCreateOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
			}else{
				dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(), bean.getCreateOrgCode()/** bean.getReceiveOrgCode()*/, bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
			}
			if (dto == null || dto.getFreightRoute() == null) {
				ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				LOGGER.info("查询走货路径成功。。。");
				bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
				LOGGER.info("配载线路名称:" + dto.getRouteLineName());
				if (freightRoute != null) {
					bean.setLoadLineCode(freightRoute.getVirtualCode()==null?"NOTEXISTS":freightRoute.getVirtualCode());// 配载线路编码
					LOGGER.info("配载线路编码:" + freightRoute.getVirtualCode());
					bean.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码
					LOGGER.info("代打木架部门编码:" + freightRoute.getPackingOrganizationCode());
					bean.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					LOGGER.info("代打木架部门名称:" + freightRoute.getPackingOrganizationName());
					bean.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
					LOGGER.info("是否可以打木架:" + freightRoute.getDoPacking());
				} else {
					bean.setLoadLineCode("NOTEXISTS");// 配载线路编码
					LOGGER.info("配载线路编码:获取到的走货路径实体为空");
					bean.setPackageOrgCode("");// 代打木架部门编码
					LOGGER.info("代打木架部门编码:");
					bean.setPackingOrganizationName("");// 代打木架部门名称
					LOGGER.info("代打木架部门名称:");
					bean.setDoPacking("");// 是否可以打木架
					LOGGER.info("是否可以打木架:");
				}
				bean.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
				LOGGER.info("配载部门编号:" + dto.getFirstLoadOrgCode());
				bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
				LOGGER.info("配载部门名称:" + dto.getFirstLoadOrgName());
				bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				LOGGER.info("最终配载部门编号:" + dto.getLastLoadOrgCode());
				bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
				LOGGER.info("最终配载部门名称:" + dto.getLastLoadOrgName());

				bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
				LOGGER.info("最终配置外场:" + dto.getLastOutLoadOrgCode());
//				bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());// 是否AB货
//				LOGGER.info("是否可区分AB货:" + dto.getGoodsTypeIsAB());
				// 设置AB货编辑状态
				
				// 如果路径可以打打木架则设置打木架按钮可点击
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);//小件不能打木架
				} else {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				}
				bean.setPickupCentralizedDeptCode(dto.getPickupCentralizedDeptCode());
				LOGGER.info("集中接货开单组所在外场的驻地营业部编码:" + dto.getPickupCentralizedDeptCode());
			}
		} catch (BaseInfoInvokeException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * e.getMessage())
			 */
			);
		} catch (BusinessException w) {
			bean.setLoadLineName("");// 配载线路名称
			bean.setLoadLineCode("");// 配载线路编码
			bean.setLoadOrgCode("");// 配载部门编号
			bean.setLoadOrgName("");// 配载部门名称
			bean.setLastLoadOrgCode("");// 最终配载部门编号
			bean.setLastLoadOrgName("");// 最终配载部门名称
			bean.setPackageOrgCode("");// 代打木架部门编码
			bean.setPackingOrganizationName("");// 代打木架部门名称
			bean.setDoPacking("");// 是否可以打木架
			bean.setLastOutLoadOrgCode("");// 最终配置外场

			ui.cargoInfoPanel.getBtnWood().setEnabled(false);// 代打木架按钮不可点击
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * e.getMessage())
			 */
			);

			// throw w;
		}
	}
	
	/**
	 * 页面控件可编辑性设置
	 */
	private void setWaybillUiByReturnType(boolean editable) {
		ui.incrementPanel.getTxtInsuranceValue().setEditable(editable);
		ui.incrementPanel.getTxtCashOnDelivery().setEditable(editable);
		ui.incrementPanel.getCombRefundType().setEnabled(editable);
		ui.incrementPanel.getCombReturnBillType().setEnabled(editable);
		ui.incrementPanel.getTxtPromotionNumber().setEditable(editable);
		ui.incrementPanel.getTxtInsurCharge().setEditable(editable);
		ui.incrementPanel.getTxtInsuranceValue().setEnabled(editable);
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(editable);
		ui.incrementPanel.getTxtPromotionNumber().setEnabled(editable);
		ui.incrementPanel.getTxtInsurCharge().setEnabled(editable);
		ui.incrementPanel.getBtnNew().setEnabled(editable);
		ui.incrementPanel.getBtnDelete().setEnabled(editable);
		ui.billingPayPanel.getTxtPackCharge().setEnabled(editable);
	}
	
	/**
	 * 快递子母件 导入返货工单的处理
	 * @author 218459-foss-dongsiwei
	 */
	private void importOrder(ExpWaybillPanelVo bean,WaybillDto dto){
		/**
		 * 1、根据传入的返货运单号来查询子母件单号
		 *       若查询不到数据，则表明此单号为普通运单号，普通运单返货开单不走此段逻辑
		 * 2、获取返货方式为按票返时，返货总件数应剔除掉除库存状态在 快递管理丢货组(编码为：W01000301050203 ) 的件数，总重量和总体积为其余单号相加
		 * 3、获取返货方式为按件返时，返货总件数为同一子母件里面的同在到达部门库存的件数，总重量和总体积为相同库存单号相加     
		 */
		if(StringUtils.isNotEmpty(bean.getOriginalWaybillNo())){
			List<WaybillRelateDetailEntity>  list=waybillService.queryWaybillRelateByWaybillNo(bean.getOriginalWaybillNo());
			List<String> waybillNos=new ArrayList<String>();
			if(CollectionUtils.isEmpty(list) || WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())){
				bean.setPicture(false);
				return;
			}else{
				bean.setPicture(true);
			}
			String orgCode=dto.getWaybillEntity().getCustomerPickupOrgCode();
			// 查询营业部的部门信息
			if(StringUtils.isNotEmpty(orgCode)){			
				SaleDepartmentEntity dept = waybillService.querySaleDepartmentByCode(orgCode);
				if(dept != null && FossConstants.ACTIVE.equals(dept.getStation())){
					// 驻地营业部对应外场
					orgCode=dept.getTransferCenter();
				}
			}
			//按票返货
			if(bean.getReturnType() !=null &&WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean.getReturnType().getValueCode())){
				for (int i = 0; i <list.size(); i++) {
					QmsYchExceptionReportEntity  entity=waybillService.isLoseGroup(list.get(i).getWaybillNo());
					if(entity !=null && WaybillConstants.YES.equals(entity.getIsInLoseGroup()) && entity.getIsSuccess()==1){
						list.remove(i);
					}
				}
				if(CollectionUtils.isNotEmpty(list)){
					for (int i = 0; i < list.size(); i++) {
						waybillNos.add(list.get(i).getWaybillNo());
					}
					calculateWeight(list,bean);
				}
			}
			//按件返
			if(bean.getReturnType() !=null &&WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(bean.getReturnType().getValueCode())){
				List<WaybillRelateDetailEntity>  relateEntity=new ArrayList<WaybillRelateDetailEntity>();
				for (int i = 0; i < list.size(); i++) {
					StockEntity  entity=waybillService.queryStockEntityByNos(orgCode,list.get(i).getWaybillNo() ,WaybillConstants.SERIAL_NUMBER,null);
					if(entity !=null){
						relateEntity.add(list.get(i));
						waybillNos.add(list.get(i).getWaybillNo());
					}
				}
				if(CollectionUtils.isNotEmpty(relateEntity)){					
					calculateWeight(relateEntity,bean);
				}
			}
			bean.setWaybillNos(waybillNos);
		}
	}
		
	/**
	 * 计算子母件 总的体积和总的重量
	 * @author 218459-foss-dongsiwei
	 */
	private void calculateWeight(List<WaybillRelateDetailEntity> list,ExpWaybillPanelVo bean){
		BigDecimal totalWeight=BigDecimal.ZERO;
		BigDecimal totalVolue=BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			totalWeight= totalWeight.add(list.get(i).getWeight());
			totalVolue=totalVolue.add(list.get(i).getVolume());
		}
		bean.setGoodsWeightTotal(totalWeight);
		bean.setGoodsVolumeTotal(totalVolue);
		bean.setGoodsQtyTotal(list.size());
		bean.setPaper(list.size());
		
	}
}
