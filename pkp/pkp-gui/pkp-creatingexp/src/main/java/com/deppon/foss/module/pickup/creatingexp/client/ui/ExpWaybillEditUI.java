/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.ui.ExpQueryPublishPriceUI;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.ComboCheckBoxEntry;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog;
import com.deppon.foss.module.pickup.creating.client.vo.OtherFeeBean;
import com.deppon.foss.module.pickup.creating.client.vo.WaybillPrintBean;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpWaybillBindingListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpBasicPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpBillingPayPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpButtonPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpCanvasContentPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpCanvasPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpCargoInfoPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConsigneePanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConsignerPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpNumberPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpTransferInfoPanel;
import com.deppon.foss.module.pickup.creatingexp.client.ui.order.ExpWebOrderDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpCalculateCostsDialog;
import com.deppon.foss.module.pickup.creatingexp.client.validation.descriptor.ExpWaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWaybillEditUI extends JPanel  implements IApplicationAware{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	private static final String ZEROSTR = "0";
	/**
	 * 300
	 */
	private static final int THREEHUNDRED = 300;

	/**
	 * 50
	 */
	private static final int FIFIX = 50;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpWaybillEditUI.class);
	private IApplication application;//窗口应用程序
	private static final String DEFAULTGROW = "default:grow";
	//运单开单类型（集中开单、营业部开单）
	private String waybillType = WaybillConstants.WAYBILL_SALE_DEPARTMENT;
	private JPanel canvasButtonPanel;
	/**
	 * 运单信息面板
	 */
	public ExpBasicPanel basicPanel; 
	/**
	 * 发货人信息面板
	 */
	public ExpConsignerPanel consignerPanel;
	/**
	 * 收货人信息面板
	 */
	public ExpConsigneePanel consigneePanel;
	/**
	 * 按钮面板
	 */
	public ExpButtonPanel buttonPanel;
	/**
	 * 运单编号、会员编号信息
	 */
	public ExpNumberPanel numberPanel;
	/**
	 * 运输信息面板
	 */
	public ExpTransferInfoPanel transferInfoPanel;
	/**
	 * 货物信息面板
	 */
	public ExpCargoInfoPanel cargoInfoPanel;
	/**
	 * 增值业务面板
	 */
	public ExpIncrementPanel incrementPanel;
	/**
	 * 计费付款面板
	 */
	public ExpBillingPayPanel billingPayPanel;
	/**
	 * 画布
	 */
	public ExpCanvasPanel canvasPanel = null;
	/**
	 * 画布信息
	 */
	public ExpCanvasContentPanel canvasContentPanel;
	private JButton btnOpenCanvasPanel;
	private DefaultComboBoxModel flightNumberType;//航班类型
	private DefaultComboBoxModel productTypeModel;// 运输性质
	private DefaultComboBoxModel pikcModeModel;// 提货方式
	private DefaultComboBoxModel freightMethod;//合票方式
	private DefaultComboBoxModel combRefundTypeModel;//退款类型
	private DefaultComboBoxModel combReturnBillTypeModel;//返单类型
	private DefaultComboBoxModel combPaymentModeModel;//开单付款方式
	private DefaultComboBoxModel combGoodsType;// 空运货物类型
	private DefaultComboBoxModel combBillingType;// 计费类型
	private DefaultComboBoxModel combWaibillReturnModeModel;//开单返单类型
	private DefaultComboBoxModel combSpecialOffer;//开单活动类型
	private DefaultComboBoxModel deliveryTypeModel;//内部发货
	public DefaultComboBoxModel getDeliveryTypeModel() {
		return deliveryTypeModel;
	}

	public void setDeliveryTypeModel(DefaultComboBoxModel deliveryTypeModel) {
		this.deliveryTypeModel = deliveryTypeModel;
	}

	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	private ExpWaybillBindingListener listener;//事件监听
	private Map<String, IBinder<ExpWaybillPanelVo>> bindersMap = new HashMap<String, IBinder<ExpWaybillPanelVo>>();
	private IEditor editor;
	private ExpWaybillPanelVo importWaybillPanelVo;
	
	private DefaultComboBoxModel combActiveInfoModel;//市场营销活动类型
	
	public DefaultComboBoxModel getCombActiveInfoModel() {
		return combActiveInfoModel;
	}

	public void setCombActiveInfoModel(DefaultComboBoxModel combActiveInfoModel) {
		this.combActiveInfoModel = combActiveInfoModel;
	}
	
	//网上订单查询框
	private ExpWebOrderDialog expWebOrderDialog;
	
	//网点查询
	private PickupGoodsBranchDialog pickupGoodsBranchDialog;
	
	//公布价查询
	private ExpQueryPublishPriceUI expQueryPublishPriceUI;
	
	//简单报价
	private ExpCalculateCostsDialog expCalculateCostsDialog;
	
	/**
	 * 运单状态
	 */
	private String waybillState;
	// 绑定接口
	private IBinder<ExpWaybillPanelVo> waybillBinder;
	public void openUI(){
		if(SwingUtilities.isEventDispatchThread()){
			initCommonWaybillEditUI();
		}else{
			//由于是异步打开窗口  所以需要放在swing专用图形线程中，否则界面会出错
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					initCommonWaybillEditUI();
				}
			});
		}	
	}
	
	private void initCommonWaybillEditUI() {
		init();
		initComboBox();
		bind();
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		//增加是否快递字段  便于产品区分
		bean.setIsExpress(FossConstants.YES);
		setCombDefualtValue(bean);
		registToRespository();
		initVo(bean);
		//集中开单初始化
		focusWaybillInit(bean);
		//初始化保险金额
		initInsuranceAmount(bean);
		//根据是否在线登录初始化
		offLineInit();
		setReceiveMethod2(bean);
		//启动开始开单时间监听事件
		Common.startKeyPressListener(bean,this);
	}
	
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(162dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(476dlu;default):grow"),
				ColumnSpec.decode("max(5dlu;default)"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.DEFAULT,
						Sizes.constant("4dlu", true),
						Sizes.constant("20dlu", true)), 0), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("min(55dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(56dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(57dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(43dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("min(1pt;default)"), }));
		this.setBackground(CommonUtils.getExpressColor());
		buttonPanel = new ExpButtonPanel();
		add(buttonPanel, "2, 2, 3, 1, fill, fill");

		numberPanel = new ExpNumberPanel();
		add(numberPanel, "2, 4, 5, 1, fill, fill");

		canvasButtonPanel = new JPanel();
		add(canvasButtonPanel, "6, 6, 1, 11, fill, fill");
		canvasButtonPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));

		basicPanel = new ExpBasicPanel(waybillType);
		add(basicPanel, "2, 6, 1, 3, fill, fill");

		consignerPanel = new ExpConsignerPanel();
		add(consignerPanel, "4, 6, fill, fill");

		consigneePanel = new ExpConsigneePanel(this);
		add(consigneePanel, "4, 8, fill, fill");
		
//		if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(waybillType))
//		{
			transferInfoPanel = new ExpTransferInfoPanel();
			add(transferInfoPanel, "2, 10, 3, 1, fill, fill");

			cargoInfoPanel = new ExpCargoInfoPanel();
			add(cargoInfoPanel, "2, 12, 3, 1, fill, fill");

			incrementPanel = new ExpIncrementPanel();
			add(incrementPanel, "2, 14, 3, 1, fill, fill");
			
			incrementPanel.setUi(this);
			
        //此段代码需暂时留着
//		}else if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType)){
//			incrementPanel = new IncrementPanel();
//			add(incrementPanel, "2, 10, 3, 1, fill, fill");
//
//			incrementPanel.setUi(this);
//			
//			
//			transferInfoPanel = new TransferInfoPanel();
//			add(transferInfoPanel, "2, 12, 3, 1, fill, fill");
//
//			cargoInfoPanel = new CargoInfoPanel();
//			add(cargoInfoPanel, "2, 14, 3, 1, fill, fill");
//		}
		billingPayPanel = new ExpBillingPayPanel();
		add(billingPayPanel, "2, 16, 3, 1, fill, fill");

		canvasContentPanel = new ExpCanvasContentPanel();
		add(canvasContentPanel, "6, 18, fill, fill");

		btnOpenCanvasPanel = new JButton(i18n.get("foss.gui.creating.canvasContentPanel.htmltitle"),
				ImageUtil.getImageIcon(
						this.getClass(), "buttonClose.png"));
		btnOpenCanvasPanel.setPreferredSize(new Dimension(FIFIX, THREEHUNDRED));
		btnOpenCanvasPanel.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnOpenCanvasPanel.setHorizontalTextPosition(SwingConstants.CENTER);
		canvasButtonPanel.add(btnOpenCanvasPanel, "1, 2");
		
		//如果是集中开单，则将光标设置到收货部门
		if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType)){
			basicPanel.getTxtSalesDepartment().requestFocusInWindow();
		}else{
			basicPanel.getTxtWaybillNO().requestFocusInWindow();
		}
		//网上订单查询框
		expWebOrderDialog = new ExpWebOrderDialog(this);
		//网点查询
		pickupGoodsBranchDialog = new PickupGoodsBranchDialog();
		//公布价查询
		expQueryPublishPriceUI = new ExpQueryPublishPriceUI();
		//简单报价
		expCalculateCostsDialog = new ExpCalculateCostsDialog();
	}
	/**
	 * 
	 * 设置下拉框默认值
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午02:30:29
	 */
	private void setCombDefualtValue(WaybillPanelVo bean) {
		//设置产品默认值
		setProductCode(bean);
		//设置提货方式默认值
		setReceiveMethod(bean);
		//设置计费方式默认值
		setBillingType(bean);
		//设置返单类型默认值
		ExpCommon.setReturnBillType(bean,combReturnBillTypeModel);
		//设置空运货物类型默认值
		setCombAirGoodsType(bean);
	}
	
	/**
	 * 设置运输性质默认值
	 */
	public void setProductCode(WaybillPanelVo bean) {
		for (int i = 0; i < productTypeModel.getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) productTypeModel.getElementAt(i);
			//默认设置为360快递
			if (ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(vo.getCode())) {
				bean.setProductCode(vo);
			}
		}
	}
	
	/**
	 * 设置提货方式默认值
	 */
	private void setReceiveMethod(WaybillPanelVo bean) {
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			
			
			DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode())) {
				bean.setReceiveMethod(vo);
			}
			//TODO
//			IBaseDataService baseDataService=GuiceContextFactroy.getInjector()
//					.getInstance(BaseDataService.class);
//			//调用接口查询体积波动范围 
//			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
//			ConfigurationParamsEntity param = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_PICKUP_TURN, 
//					user.getEmployee().getDepartment().getCode());
//			if(param!=null && FossConstants.YES.equals(param.getConfValue())){
//				if (WaybillConstants.INNER_PICKUP.equals(vo.getValueCode())) {
//					bean.setReceiveMethod(vo);
//				}
//			}else{
//				if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode())) {
//					bean.setReceiveMethod(vo);
//				}
//			}
		}
	}
	
	private void setReceiveMethod2(WaybillPanelVo bean) {
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			
			
			DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			
			//TODO
			IBaseDataService baseDataService=GuiceContextFactroy.getInjector()
					.getInstance(BaseDataService.class);
			//调用接口查询体积波动范围 
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			ConfigurationParamsEntity param = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_PICKUP_TURN, 
					user.getEmployee().getDepartment().getCode());
			if(param!=null && FossConstants.YES.equals(param.getConfValue())){
				if (WaybillConstants.INNER_PICKUP.equals(vo.getValueCode())) {
					bean.setReceiveMethod(vo);
					
					// 内部带货
					ExpCommon.innerPickup((ExpWaybillPanelVo)bean, this);
					bean.setKilometer(null);
					// 公里数不可编辑
					this.transferInfoPanel.getTxtKilometer().setEditable(false);
					
					
				}
			}else{
				if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode())) {
					bean.setReceiveMethod(vo);
				}
			}
		}
	}
	
	/**
	 * 设置计费类型默认值
	 */
	private void setBillingType(WaybillPanelVo bean) {
		for (int i = 0; i < combBillingType.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combBillingType.getElementAt(i);
			if (WaybillConstants.BILLINGWAY_WEIGHT.equals(
					vo.getValueCode())) {
				bean.setBillingType(vo);
			}
		}
	}
	
	/**
	 * 
	 * 设置默认空运货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-4-8 上午10:56:34
	 * @param bean
	 */
	private void setCombAirGoodsType(WaybillPanelVo bean) {
		for (int i = 0; i < combGoodsType.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combGoodsType.getElementAt(i);
			if (WaybillConstants.AIR_GOODSTYPE_NAME.equals(vo.getValueName()) 
					|| WaybillConstants.AIR_GOODSTYPE_CODE.equals(vo.getValueCode())) {
				bean.setAirGoodsType(vo);
			}
		}
	}
	
	/**
	 * 
	 * @description:初始化VO
	 * @date 2012-7-16
	 * @author 025000-FOSS-helong
	 */
	private void initVo(ExpWaybillPanelVo bean) {
		
		//单选框与复选框数据初始化		
		/**
		 * 判断是否是集中开单如果是，则默认勾选上门接货
		 */
		if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType)){
			bean.setPickupToDoor(true);//是否上门接货
		}else{
			bean.setPickupToDoor(false);//是否上门接货			
		}		
		// 内部发货为空的话，工号输入框不可编辑且清空
		if (basicPanel.getInternalDeliveryType().getSelectedItem() == null) {
			basicPanel.getTxtStaffNumber().setEnabled(false);
		} else {
			basicPanel.getTxtStaffNumber().setEnabled(true);
		}
		bean.setPickupCentralized(false);//是否集中接货
		bean.setPreciousGoods(false);//是否贵重物品
		bean.setSpecialShapedGoods(false);//是否异形物品
		bean.setIsPdaBill(false);//是否为PDA运单
		bean.setCarDirectDelivery(false);//大车直送
		bean.setSecretPrepaid(false);//预付费保密
		bean.setIsWholeVehicle(false);//是否整车
		//bean.setGoodsType("A");//货物类型
		bean.setIsPassDept(false);//是否经过营业部
		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		SaleDepartmentEntity salesDept = null;
		if(dept.getCode()!=null){
			SaleDepartmentEntity org = waybillService.querySaleDeptByCode(dept.getCode());
			
			if(org!=null){
				//始发网点 create time
				if(org.getOpeningDate()!=null){
					bean.setReceiveOrgCreateTime(org.getOpeningDate());
				}
			}
			salesDept = org;
		}
		
		bean.setReceiveOrgCreateTime(dept.getBeginTime());//收货部门开业时间
		
		bean.setCreateOrgCode(dept.getCode());//开单组织
		bean.setModifyOrgCode(dept.getCode());//更新组织
		bean.setCreateUserCode(user.getEmployee().getEmpCode());//创建人
		bean.setModifyUserCode(user.getEmployee().getEmpCode());//修改人
		//bean.setLongOrShort(PricingConstants.LONG_OR_SHORT_L);//长短途
		bean.setBillTime(new Date());//开单时间

		// 货物信息面板
//		bean.setGoodsQtyTotal(Integer.valueOf(0));// 件数
//		bean.setGoodsVolumeTotal(BigDecimal.ZERO);// 体积
//		bean.setGoodsWeightTotal(BigDecimal.ZERO);// 重量
		initCombOutSideRemark();// 对外备注
		bean.setPaper(Integer.valueOf(0));// 纸
		bean.setWood(Integer.valueOf(0));// 木
		bean.setFibre(Integer.valueOf(0));// 纤
		bean.setSalver(Integer.valueOf(0));// 托
		bean.setMembrane(Integer.valueOf(0));// 膜
		bean.setTransportationRemark("");//储运事项

		// 增值服务面板
		bean.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
		//bean.setCodAmount(BigDecimal.ZERO);// 代收货款
		bean.setPackageFee(BigDecimal.ZERO);// 包装费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
		bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);//计算后的送货费
		//bean.setGoodsWeightTotal(BigDecimal.ZERO);
		
		bean.setServiceFee(BigDecimal.ZERO);// 装卸费
		bean.setPickupFee(BigDecimal.ZERO);// 接货费
		bean.setOtherFee(BigDecimal.ZERO);//其他费用


		// 计费付款面板
		bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		bean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		bean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		bean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		bean.setToPayAmount(BigDecimal.ZERO);// 到付金额
		bean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		bean.setTotalFee(BigDecimal.ZERO);
		bean.setWholeVehicleAppfee(BigDecimal.ZERO);//整车约车报价

		// 画布

		bean.setBillWeight(BigDecimal.ZERO);// 计费重量
		bean.setUnitPrice(BigDecimal.ZERO);// 费率
		bean.setTransportFeeCanvas(ZEROSTR);// 公布价运费
		bean.setInsuranceAmountCanvas(ZEROSTR);// 保价声明
		bean.setInsuranceRate(BigDecimal.ZERO);// 保价费率
		bean.setInsuranceFee(BigDecimal.ZERO);// 保价费

		bean.setCodAmountCanvas(ZEROSTR);// 代收货款
		bean.setCodRate(BigDecimal.ZERO);// 代收费率
		bean.setCodFee(BigDecimal.ZERO);// 代收手续费

		bean.setPickUpFeeCanvas(ZEROSTR);// 接货费
		bean.setDeliveryGoodsFeeCanvas(ZEROSTR);// 送货费
		bean.setPackageFeeCanvas(ZEROSTR);// 包装费
		bean.setServiceFeeCanvas(ZEROSTR);// 装卸费
		//根据部门是否开装卸费设置编辑状态
		if(!departPropertyServiceFee(bean)){
			this.incrementPanel.getTxtServiceCharge().setEnabled(false);
		}
		bean.setOtherFeeCanvas(ZEROSTR);// 其他费用
		bean.setPromotionsFeeCanvas(ZEROSTR);// 优惠合计
		bean.setTotalFeeCanvas(ZEROSTR);// 总费用
		
		bean.setStandCharge(BigDecimal.ZERO);//打木架费用
		bean.setBoxCharge(BigDecimal.ZERO);//打木箱费用
		bean.setGoodsVolumeTotal(BigDecimal.ZERO);
		bean.setGoodsQtyTotal(Integer.valueOf(1));
		bean.setPaper(Integer.valueOf(1));
		
		//初始化UI时，产品为商务专递时限制合票方式以及航班类型 tdswei
		if(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(bean.getProductCode().getCode())){
			DataDictionaryValueVo flightNumberType=new DataDictionaryValueVo();
			DataDictionaryValueVo freightMethod=new DataDictionaryValueVo();
			flightNumberType.setValueCode("MIDDLE_FLIGHT");
			freightMethod.setValueCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
			bean.setFreightMethod(freightMethod);
			bean.setFlightNumberType(flightNumberType);
		}
		
		CanvasPanelAction canvasPanelAction = new CanvasPanelAction(canvasContentPanel, btnOpenCanvasPanel);

		btnOpenCanvasPanel.addActionListener(canvasPanelAction);
		btnOpenCanvasPanel.registerKeyboardAction(canvasPanelAction,
				KeyStroke.getKeyStroke("F12"),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	
		if(bean.getReceiveMethod()!=null){
			/**
			 * 如果非送货时，公里数不可录入，且要清空
			 */
			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
				bean.setKilometer(null);
				// 公里数不可编辑
				transferInfoPanel.getTxtKilometer().setEditable(false);
			}else{
				// 公里数可编辑
				transferInfoPanel.getTxtKilometer().setEditable(true);
			}
		}		

		SalesDepartmentService salesDepartmentService 
			= DownLoadDataServiceFactory.getSalesDepartmentService();
		SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
		dto.setSalesDepartmentCode(dept.getCode());
		SalesDepartmentCityDto result = 
				salesDepartmentService.querySalesDepartmentCityInfo(dto);
		if(salesDept!=null){
			//营业部是否可以快递接货，如果是的话 就是试点营业部
			result.setTestSalesDepartment(StringUtil.defaultIfNull(salesDept.getCanExpressPickupToDoor()));
		}
		//querySalesDepartmentCityInfo
		bean.setCreateSalesDepartmentCityDto(result);
		
		IBaseDataService baseDataService=GuiceContextFactroy.getInjector()
				.getInstance(BaseDataService.class);
		//调用接口查询体积波动范围 
		ConfigurationParamsEntity param = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_VOLUME_RANGE_UP, 
				user.getEmployee().getDepartment().getCode());
		//调用接口查询体积波动范围 
		ConfigurationParamsEntity param2 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_VOLUME_RANGE_LOW, 
				user.getEmployee().getDepartment().getCode());
		//调用接口查询体积比
		ConfigurationParamsEntity param3 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_VOLUME_RANGE_RATE, 
						user.getEmployee().getDepartment().getCode());
		//快递保价申明价值上限
		ConfigurationParamsEntity param4 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_INSURRANCE_UP, 
								user.getEmployee().getDepartment().getCode());
		//快递保费
		ConfigurationParamsEntity param5 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_INSURRANCE_FEE_UP, 
										user.getEmployee().getDepartment().getCode());	
		
		//快递保费
		ConfigurationParamsEntity param6 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_CODE_FEE_UP, 
										user.getEmployee().getDepartment().getCode());	
		
		
		
		if(param!=null){//体积波动范围上限
			bean.setVolumeUp(new BigDecimal(param.getConfValue()));
		}
		if(param2!=null){//体积波动范围下限
			bean.setVolumeLow(new BigDecimal(param2.getConfValue()));
		}
		if(param3!=null){
			bean.setVolumeRate(new BigDecimal(param3.getConfValue()));
		}
		
		if(param4!=null){
			bean.setInsuranceAmountUp(new BigDecimal(param4.getConfValue()));
			bean.setMaxInsuranceAmount(new BigDecimal(param4.getConfValue()));
		}
//		
		if(param5!=null){
			bean.setInsuranceFeeUp(new BigDecimal(param5.getConfValue()));
		}
		
		if(param6!=null){
			bean.setCodAmountMax(new BigDecimal(param6.getConfValue()));
		}
	}
	
	/**
	 * 
	 * 初始化保险金额
	 */
	private void initInsuranceAmount(WaybillPanelVo bean){
		ValueAddDto dto = new ValueAddDto();
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryParam());

		if(list != null && !list.isEmpty()){
			dto = list.get(0);
			//bean.setMaxInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getMaxFee()));
			bean.setMixInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getMinFee()));
			
			BigDecimal feeRate = ExpCommon.nullBigDecimalToZero(dto.getActualFeeRate());
			//将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			feeRate = feeRate.multiply(permillage);
			
			/**
			bean.setInsuranceRate(feeRate);
			bean.setInsuranceFee(dto.getCaculateFee());
			bean.setInsuranceId(dto.getId());
			bean.setInsuranceCode(dto.getPriceEntityCode());
			**///小件手动填写
			bean.setInsuranceAmount(ExpCommon.nullBigDecimalToZero(null));
			bean.setInsuranceAmountCanvas(ExpCommon.nullBigDecimalToZero(null).toString());
//			bean.setInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getDefaultBF()));
//			bean.setInsuranceAmountCanvas(ExpCommon.nullBigDecimalToZero(dto.getDefaultBF()).toString());
		}
	}
	
	/**
	 * 获取查询参数
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(dept.getCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(null);// 到达部门CODE
		queryDto.setProductCode(null);// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(null);// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * (初始化对外备注)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombOutSideRemark() {
		final List<DataDictionaryValueEntity>  list = waybillService.queryOuterRemark();
		final DefaultComboBoxModel combOutSideRemarkModel = new DefaultComboBoxModel();
		final JComboCheckBox checkbox = (JComboCheckBox)this.cargoInfoPanel.getCombOutSideRemark();
		final ExpCargoInfoPanel cargoInfoPanel = this.cargoInfoPanel;
		
		//针对异步打开装口 需要用swing特殊线程
		checkbox.setModel(combOutSideRemarkModel);
		for (DataDictionaryValueEntity dataDictionary : list) {
			ComboCheckBoxEntry entry = new ComboCheckBoxEntry(dataDictionary.getValueCode(), dataDictionary.getValueName());
			checkbox.addItem(entry);
		}
		checkbox.addFocusListener(new CheckBoxListMouseHandler(cargoInfoPanel));
		checkbox.getRender().addFocusListener(new CheckBoxListMouseHandler(cargoInfoPanel));
	}
	
	/**
	 * 
	 * 集中开单控件以及数据初始化
	 * @author 025000-FOSS-helong
	 * @date 2013-1-26 上午10:58:10
	 */
	private void focusWaybillInit(WaybillPanelVo bean){
		if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType)){
			//集中开单
			bean.setPickupCentralized(true);
			//手写到付金额
			this.billingPayPanel.getTxtHandWriteMoney().setEnabled(true);
			String receiveOrgCode = (String)SessionContext.get(WaybillConstants.FOCUS_DEPT_CODE);
			String receiveOrgName = (String)SessionContext.get(WaybillConstants.FOCUS_DEPT_NAME);
			if(StringUtils.isNotEmpty(receiveOrgCode) && StringUtils.isNotEmpty(receiveOrgName)){
				bean.setReceiveOrgCode(receiveOrgCode);//收货部门编码
				
				//货部门省份编码
				if(StringUtil.isNotEmpty(receiveOrgCode)){
					bean.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(receiveOrgCode));
				}
				bean.setReceiveOrgName(receiveOrgName);//收货部门名称
				//根据部门查询产品
				setProductTypeModel(receiveOrgCode);
				//设置产品默认值
				setProductCode(bean);
			}
		}else{
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			//非集中开单
			bean.setPickupCentralized(false);
			//手写到付金额
			this.billingPayPanel.getTxtHandWriteMoney().setEnabled(false);
			bean.setReceiveOrgCode(dept.getCode());//收货部门
			bean.setReceiveOrgName(dept.getName());//收货部门
			//货部门省份编码
			if(StringUtil.isNotEmpty(dept.getCode())){
				bean.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(dept.getCode()));
			}
		}
	}
	
	
	/**
	 * 
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	private void initComboBox() {
		// 产品类型
		initCombProductType();
		// 提货方式
		initCombPickMode();
		//航班类型
		initFlightNumberType();
		//合票方式
		initFreightMethod();
		// 退款类型
		initCombRefundType();
		// 返单类型
		initCombReturnBillType();
		// 开单付款方式
		initCombPaymentMode();
		// 计费类型
		initCombBillingType();
		// 空运货物类型
		initCombGoodsType();
		// 运单开单类型
		initWaybillReturnType();
		// 活动类型
		iniCombSpecialOffer();
		//初始化市场营销活动
		combActiveInfoType();
		//内部发货
		initComDeliveryMode();
	}
	
	/**
	 * KD_SPECIALOFFER_TYPE
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void iniCombSpecialOffer() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
//		Date date = waybillBinder.getBean().getBillTime();
//		if(null == date){
//			date = new Date();
//		}
		//根据登录部门和当前时间查询并封装
		List<CityMarketPlanEntity> cityMarketPlanEntityList = waybillService.searchCityMarketPlanEntityList(
				dept.getCode(), new Date());
		
		combSpecialOffer = new DefaultComboBoxModel();//开单付款方式 =
		DataDictionaryValueVo emptyvo = new DataDictionaryValueVo();
		emptyvo.setId("");
		emptyvo.setValueCode("");
		emptyvo.setValueName("");
		combSpecialOffer.addElement(emptyvo);
		
		if(CollectionUtils.isNotEmpty(cityMarketPlanEntityList)){
			for(CityMarketPlanEntity entity:cityMarketPlanEntityList){
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				vo.setId(entity.getId());
				vo.setValueCode(entity.getCode());
				vo.setValueName(entity.getName());
				combSpecialOffer.addElement(vo);
			}
		}

//		this.basicPanel.getCboSpecialOffer()
//				.setModel(combSpecialOffer);
//		boolean flag = waybillService.isExistSpecialOffer(dept.getCode(),new Date());
//		//当前部门是否存在活动
//		this.getBasicPanel().getCboSpecialOffer().setEnabled(flag);
	}
	
	/**
	 * 初始化市场营销活动
	 * @创建时间 2014-4-19 下午4:52:38   
	 * @创建人： WangQianJin
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void combActiveInfoType() {
		/*初始化营销活动*/
		combActiveInfoModel = new DefaultComboBoxModel();
		DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		combActiveInfoModel.addElement(nullVo);
		//是否启用市场营销活动
		boolean isStart=waybillService.isStartCrmActive();
		if(isStart){
			//获取当前登陆部门code
			UserEntity user = (UserEntity)SessionContext.getCurrentUser();
			String currentOrgCode = user.getEmployee().getDepartment().getCode();
			//获取市场营销活动列表
			CrmActiveParamVo crmActiveParamVo = new CrmActiveParamVo();
			crmActiveParamVo.setCurrentDate(new Date());
			crmActiveParamVo.setActivityCategory(DictionaryValueConstants.BUSINESS_EXPRESS);
			crmActiveParamVo.setActive(FossConstants.ACTIVE);
			crmActiveParamVo.setCurrentOrgCode(currentOrgCode);
			CrmActiveInfoDto activeDto=waybillService.getActiveInfoList(crmActiveParamVo);
			List<MarkActivitiesQueryConditionDto> list = activeDto.getActiveList();		
			if(CollectionUtils.isNotEmpty(list)){
				for (MarkActivitiesQueryConditionDto dto: list) {
					DataDictionaryValueVo vo = new DataDictionaryValueVo();			
					vo.setId(dto.getId());
					vo.setValueCode(dto.getCode());
					vo.setValueName(dto.getName());
					combActiveInfoModel.addElement(vo);
				}
			}
			this.incrementPanel.getCombActiveInfo().setEnabled(true);
		}else{
			this.incrementPanel.getCombActiveInfo().setEnabled(false);
		}				
		this.incrementPanel.getCombActiveInfo().setModel(combActiveInfoModel);
	}
	
	public DefaultComboBoxModel getCombWaibillReturnModeModel() {
		return combWaibillReturnModeModel;
	}

	/**
	 * PKP_WAYBILLEXPRESS_TYPE
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initWaybillReturnType() {
		Injector injector = GuiceContextFactroy.getInjector();
		DataDictionaryValueService dictionaryValueSerivce = injector.getInstance(DataDictionaryValueService.class);

		List<DataDictionaryValueEntity> list = dictionaryValueSerivce.queryByTermsCode(DictionaryConstants.PKP_WAYBILLEXPRESS_TYPE);
		combWaibillReturnModeModel = new DefaultComboBoxModel();//开单付款方式 =
		DataDictionaryValueVo emptyvo = new DataDictionaryValueVo();
		emptyvo.setId("");
		emptyvo.setValueCode("");
		emptyvo.setValueName("");
		combWaibillReturnModeModel.addElement(emptyvo);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			combWaibillReturnModeModel.addElement(vo);
		}
		this.basicPanel.getComboBoxReturnType().setModel(combWaibillReturnModeModel);
	}

	/**
	 * 
	 * @description:对按钮、文本框等控件进行绑定
	 * @date 2012-7-16
	 * @author 025000-FOSS-helong
	 */
	private void bind() {
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		waybillBinder = BindingFactory.getIntsance().moderateBind(
				ExpWaybillPanelVo.class, this, new ExpWaybillDescriptor(), true);
		waybillBinder.addValidationListener(new WaybillValidationListner());
		listener = new ExpWaybillBindingListener(this);
		waybillBinder.addBindingListener(listener);
		incrementPanel.setWaybillPanelVo(waybillBinder.getBean());
	}
	/**
	 * 
	 * <p>
	 * (初始化产品类型)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombProductType(){
		//营业部开单
		if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(waybillType)){
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			productTypeModel = new DefaultComboBoxModel();
			//根据当前用户所在部门查询部门所属产品
			setProductTypeModel(dept.getCode());
			this.transferInfoPanel.getCombProductType().setModel(productTypeModel);
		}else if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType)){
			productTypeModel = new DefaultComboBoxModel();
			this.transferInfoPanel.getCombProductType().setModel(productTypeModel);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initCombPickMode() {
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		pikcModeModel = new DefaultComboBoxModel();
		IBaseDataService baseDataService=GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
		//调用接口查询体积波动范围 
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		ConfigurationParamsEntity param = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_PICKUP_TURN, 
				user.getEmployee().getDepartment().getCode());
		
		for (DataDictionaryValueEntity dataDictionary : list) {
			//INNER_PICKUP
			if(param != null && FossConstants.YES.equals(param.getConfValue())){
				if(WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					pikcModeModel.addElement(vo);
				}
			}else{
				if( //WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())||
//						WaybillConstants.SELF_PICKUP.equals(dataDictionary.getValueCode())||
						//DMANA-5443 FOSS开单冻结自提功能
						WaybillConstants.DELIVER_UP.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					pikcModeModel.addElement(vo);
				}
				
			}
		}
		this.transferInfoPanel.getCombPickMode().setModel(pikcModeModel);
	}
	
	/**
	 * 
	 * 设置产品到数据模型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-16 下午02:18:34
	 */
	@SuppressWarnings("unchecked")
	public void setProductTypeModel(String deptCode){
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(deptCode, WaybillConstants.TYPE_OF_EXPRESS, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		ProductEntityVo vo = null;
		//将数据库查询出的产品对象进行转换，转成VO使用的对象
		for (ProductEntity product : list) {
			vo = new ProductEntityVo();
			ValueCopy.entityValueCopy(product, vo);
			vo.setDestNetType(product.getDestNetType());
			productTypeModel.addElement(vo);
		}
	}
	/**
	 * (初始化航班类型)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initFlightNumberType() {
		List<DataDictionaryValueEntity> list = waybillService.queryPredictFlight();
		flightNumberType = new DefaultComboBoxModel();
		flightNumberType.addElement(new DataDictionaryValueVo());
		if(CollectionUtils.isNotEmpty(list)){
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				flightNumberType.addElement(vo);
			}
		}
		this.transferInfoPanel.getCombFlightNumberType().setModel(flightNumberType);
	}
	
	
	/**
	 * (初始化合票方式)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initFreightMethod() {
		List<DataDictionaryValueEntity> list = waybillService
				.queryFreightMethod();
		freightMethod = new DefaultComboBoxModel();
		freightMethod.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			freightMethod.addElement(vo);
		}
		this.transferInfoPanel.getCombFreightMethod().setModel(freightMethod);
	}
	
	/**
	 * (初始化退款类型)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initCombRefundType() {
		List<DataDictionaryValueEntity> list = waybillService.queryRefundType();
		
//		List<DataDictionaryValueEntity> list = new ArrayList<DataDictionaryValueEntity>();
//		//RA
//		for(DataDictionaryValueEntity de: list2){
//			if(WaybillConstants.REFUND_TYPE_VALUE.equals(de.getValueCode())){
//				continue;
//			}else{
//				list.add(de);
//			}
//		}
		
		combRefundTypeModel = new DefaultComboBoxModel();
		combRefundTypeModel.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			combRefundTypeModel.addElement(vo);
		}
		this.incrementPanel.getCombRefundType().setModel(combRefundTypeModel);
	}
	
	/**
	 * (初始化返单类型)
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombReturnBillType() {
		List<DataDictionaryValueEntity> list2 = waybillService.queryReturnBillType();
		combReturnBillTypeModel = new DefaultComboBoxModel();
		if(CollectionUtils.isNotEmpty(list2)){
			List<DataDictionaryValueEntity> list = new ArrayList<DataDictionaryValueEntity>();
			for(int i =0 ;i<list2.size();i++){
				DataDictionaryValueEntity entity = list2.get(i);
				if(WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(entity.getValueCode())
						||WaybillConstants.RETURNBILLTYPE_SCANNED.equals(entity.getValueCode())){
					continue;
				}else{
					list.add(entity);
				}
			}
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				combReturnBillTypeModel.addElement(vo);
			}
		}
		this.incrementPanel.getCombReturnBillType().setModel(combReturnBillTypeModel);
	}
	
	/**
	 * (初始化付款方式)
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombPaymentMode() {
		List<DataDictionaryValueEntity> list = waybillService.queryPaymentMode();
		
		combPaymentModeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if(WaybillConstants.TEMPORARY_DEBT.equals(dataDictionary.getValueCode())){
				continue;//选择“包裹”的时候，付款方式不允许为临时欠款；
			}
			//判断，如果是合伙人营业部，过滤掉网上支付选项
			if(!BZPartnersJudge.IS_PARTENER){
				if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
						&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode())){
						DataDictionaryValueVo vo = new DataDictionaryValueVo();
						ValueCopy.valueCopy(dataDictionary, vo);
						combPaymentModeModel.addElement(vo);
					}
			}else{
				if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
						&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode())
						&& !WaybillConstants.ONLINE_PAYMENT.equals(dataDictionary.getValueCode())){
						DataDictionaryValueVo vo = new DataDictionaryValueVo();
						ValueCopy.valueCopy(dataDictionary, vo);
						combPaymentModeModel.addElement(vo);
					}
			}
			
		}
		this.incrementPanel.getCombPaymentMode().setModel(combPaymentModeModel);
	}
	
	/**
	 * 初始化计费类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombBillingType() {
		List<DataDictionaryValueEntity> list = waybillService.queryBillingWay();
		combBillingType = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			combBillingType.addElement(vo);
		}
		this.canvasContentPanel.getCombBillingType().setModel(combBillingType);
	}
	
	/**
	 * 初始化空运货物类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initCombGoodsType() {
		List<GoodsTypeEntity> list = waybillService.findGoodsTypeByCondiction();
		combGoodsType = new DefaultComboBoxModel();
		for (GoodsTypeEntity goodsType : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(goodsType, vo);
			combGoodsType.addElement(vo);
		}
		this.cargoInfoPanel.getCombGoodsType().setModel(combGoodsType);
	}
	/**
	 * 快递是否可开装卸费取决于该部门能否开快递单（即部门属性基础资料中CanExpressPickupToDoor字段）
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {
		// 收货部门
		String orgCode = bean.getReceiveOrgCode();
		// org code is not null;
		if(StringUtils.isNotEmpty(orgCode)){
			// 根据编码查询
			SaleDepartmentEntity entity = waybillService.querySaleDeptByCode(orgCode);
			if(entity!=null){
				//不允许开装卸费
				if(!FossConstants.YES.equals(entity.getCanExpressPickupToDoor())){
					return false;
				}
			}
		}
		return true;
	}

	private void offLineInit(){
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
			
		}else{
			
		}
	}
	
	/**
	 * @description：保存绑定对象
	 */
	public void registToRespository() {
		bindersMap.put("waybillBinder", waybillBinder);
	}
	
	/**
	 * 获得绑定对象
	 */
	public HashMap<String, IBinder<ExpWaybillPanelVo>> getBindersMap() {
		return( HashMap<String, IBinder<ExpWaybillPanelVo>>) bindersMap;
	}
	
	public IApplication getApplication() {
		return application;
	}

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}
	
	 /**
     * CheckBoxListMouseHandler
     * @author shixw
     *
     */
    protected class CheckBoxListMouseHandler implements FocusListener {
    	
    	/**
    	 * panel
    	 */
    	private ExpCargoInfoPanel cargoInfoPanel;
    	
    	/**
    	 * constructor
    	 * @param cargoInfoPanel
    	 */
    	public CheckBoxListMouseHandler(ExpCargoInfoPanel cargoInfoPanel){
    		this. cargoInfoPanel = cargoInfoPanel;
    	}
    	
    	

    	/**
    	 * lost focust
    	 */
		public void focusLost(FocusEvent e) {
			
			
			
			StringBuffer sb = new StringBuffer("");
			
			String woodTxt= "";
			String sandTxt= "";
			String innerWaybillText = "";
			
			String originalTxt = cargoInfoPanel.getTxtTransportationRemark().getText();
			if(originalTxt==null){
				originalTxt = "";
			}
			
			String[] remark = originalTxt.split(";");
			for(int i=0;i<remark.length;i++){
				String oldData = remark[i];
				if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"))) != -1){
					woodTxt = oldData;
				}
				
				if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"))) != -1){
					sandTxt = oldData;
				}
				
				//弃货导入内部开单
				if (oldData.contains(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.innerWaybillText")))){
					innerWaybillText = oldData;
				}
				
			}
			/**
			 * 对外备注
			 */
        	JComboCheckBox checkbox = (JComboCheckBox)this.cargoInfoPanel.getCombOutSideRemark();
        	WaybillPanelVo vo = waybillBinder.getBean();
        	DataDictionaryValueVo dv = vo.getOuterNotes();
        	
        	if(StringUtils.isNotEmpty(woodTxt)){
        		sb.append(woodTxt).append(";");
        	}
        	
        	if(StringUtils.isNotEmpty(sandTxt)){
        		sb.append(sandTxt).append(";");
        	}
        	
        	//加上弃货导入内部开单备注
    		if (StringUtils.isNotEmpty(innerWaybillText)) {
    			sb.append(innerWaybillText).append(";");
    		}
        	
        	if(dv == null){
        		dv = new DataDictionaryValueVo();
        	}
        	for (String string : checkbox.getCheckedValues()) {
        		
				sb.append(string).append(";");
			}
        	
        	dv.setValueCode(sb.toString());
        	vo.setOuterNotes(dv);
        	
        	/**
        	 * 贵重物品
        	 */       	
        	Boolean bool = vo.getPreciousGoods();
    		if (bool!=null && bool) {
    			sb.append(WaybillConstants.VALUE_GOODS_NAME);	
    		} 
        	/**
        	 * 对内备注
        	 */
        	String innerSiderRemark = this.cargoInfoPanel.getTxtInsideRemark().getText();
        	if(innerSiderRemark!=null && !"".equals(innerSiderRemark) ){
        		sb.append(innerSiderRemark).append(";");
        	}
        	
        	/**
        	 * 大车直送
        	 */
        	if(cargoInfoPanel.getChbCarThrough().isSelected()){
        		sb.append(i18n.get("foss.gui.waybillEditUI.carThrough.name")).append("; ");
        	}
        	
        	
        	//cargoInfoPanel.getTxtTransportationRemark().setText(sb.toString());
        	//要用waybillBinder.getBean().setTransportationRemark这种方式设置储运事项，不然贵重物品和对内备注监听事件获取不到对外备注
        	waybillBinder.getBean().setTransportationRemark(sb.toString());
		}

		/**
		 * gain focus
		 */
		public void focusGained(FocusEvent e) {
			
		}
    }
    
    /**
	 * <p>
	 * (组装打印bean)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-26 上午11:48:12
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @see
	 */
	public WaybillPrintBean getWaybillPrintBean()
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		WaybillPrintBean printBean = new WaybillPrintBean();

		WaybillPanelVo vo = waybillBinder.getBean();
		if(vo.getDeliveryCustomerName() == null || "".equals(vo.getDeliveryCustomerName()) ){
			vo.setDeliveryCustomerName(this.getConsignerPanel().getTxtConsignerLinkMan().getText());
		}
		if(vo.getReceiveCustomerName() == null || "".equals(vo.getReceiveCustomerName())){
			vo.setReceiveCustomerName(this.getConsigneePanel().getTxtConsigneeLinkMan().getText());
		}

		PropertyUtils.copyProperties(printBean, vo);
		printBean.setOtherFeeList(getOtherChargeVo());// 其他费用
		//设置储运事项
		printBean.setStorageMatter(vo.getTransportationRemark());
		//设置网络订单号
		printBean.setOnlineOrderNo(vo.getOrderNo());
		//设置代收货款类型名称
		if(null!=vo.getRefundType()){
			String refundTypeName = vo.getRefundType().getValueName();
			if(!StringUtil.isEmpty(refundTypeName)){
				printBean.setRefundTypeName(refundTypeName);
			}
		}
		return printBean;
	}
	
	/**
	 * 
	 * 获取其他费用、以及装卸费、接货费等
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 上午10:27:25
	 */
	private List<OtherFeeBean> getOtherChargeVo() {
		JXTable table = this.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		WaybillPanelVo panelVo = waybillBinder.getBean();
		List<OtherFeeBean> beanList = new ArrayList<OtherFeeBean>();
		if(CollectionUtils.isNotEmpty(data))
		{
			for (OtherChargeVo vo : data) {
				OtherFeeBean otherFee = new OtherFeeBean();
				otherFee.setOtherFeeName(vo.getChargeName());
				if(StringUtil.equals(vo.getCode(), PriceEntityConstants.PRICING_CODE_DZYHQ)){
					continue;
				}
				otherFee.setOtherFeeValue(new BigDecimal(vo.getMoney()).longValue());
				otherFee.setOtherFeeCode(vo.getCode());
				beanList.add(otherFee);				
			}
		}
		if (panelVo.getPickupFee().longValue() != 0) {
			OtherFeeBean otherFee = new OtherFeeBean();
			otherFee.setOtherFeeName(i18n.get("foss.gui.creating.waybillEditUI.pickupFee.label"));
			otherFee.setOtherFeeValue(panelVo.getPickupFee().longValue());
			beanList.add(otherFee);
		}
//		if (panelVo.getDeliveryGoodsFee().longValue() != 0) {
//			OtherFeeBean otherFee = new OtherFeeBean();
//			otherFee.setOtherFeeName(i18n.get("foss.gui.creating.waybillEditUI.DeliveryGoodsFee.label"));
//			otherFee.setOtherFeeValue(panelVo.getDeliveryGoodsFee().longValue());
//			beanList.add(otherFee);
//		}
		if (panelVo.getPackageFee().longValue() != 0) {
			OtherFeeBean otherFee = new OtherFeeBean();
			otherFee.setOtherFeeName(i18n.get("foss.gui.creating.waybillEditUI.PackageFee.label"));
			otherFee.setOtherFeeValue(panelVo.getPackageFee().longValue());
			beanList.add(otherFee);
		}		 
		/**
		 * 获取送货费列表
		 */
		List<DeliverChargeEntity> deliverChargeEntityList = panelVo.getDeliverList();
		if(deliverChargeEntityList!=null && deliverChargeEntityList.size()>0){
			for (DeliverChargeEntity deliver : deliverChargeEntityList) {
				String code=deliver.getCode();
				boolean flag=false;
				/**
				 * 判断其他费用里面是否存在送货费
				 */
				if(beanList!=null && beanList.size()>0){
					for (OtherFeeBean otherFee : beanList) {
						if(code!=null && code.equals(otherFee.getOtherFeeCode())){
							flag=true;
							break;
						}
					}
				}
				//如果不存在，则添加
				if(!flag){
					OtherFeeBean fee = new OtherFeeBean();
					fee.setOtherFeeName(deliver.getName());
					if(deliver.getAmount()!=null){
						fee.setOtherFeeValue(new BigDecimal(deliver.getAmount()+"").longValue());
					}							
					fee.setOtherFeeCode(deliver.getCode());
					beanList.add(fee);
				}				
			}			
		}
		/**
		 * BUG-13165：劳务费的输入及显示
		 * 装卸费不打印到纸质运单上
		 */
//		if (panelVo.getServiceFee().longValue() != 0) {
//			OtherFeeBean otherFee = new OtherFeeBean();
//			otherFee.setOtherFeeName(i18n.get("foss.gui.creating.waybillEditUI.ServiceFee.label"));
//			otherFee.setOtherFeeValue(panelVo.getServiceFee().longValue());
//			beanList.add(otherFee);
//		}
		return beanList;

	}
	/**
	 * 
	 * 设置内部发货默认值
	 * 
	 * @author 025000-FOSS-guohao
	 * @date 2015-4-14 下午02:43:29
	 */
	public void setInternalDelivery(WaybillPanelVo bean) {
		for (int i = 0; i < deliveryTypeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) deliveryTypeModel
					.getElementAt(i);
			bean.setInternalDeliveryType(vo);
		}
	}
	/**
	 * 
	 * <p>
	 * (初始化内部发货)
	 * </p>
	 * 
	 * @author 025000-FOSS-guohao
	 * @date 2015-4-13 上午10:14:17
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initComDeliveryMode() {
		deliveryTypeModel = new DefaultComboBoxModel();
		List<DataDictionaryValueEntity> list = waybillService.queryInternalDeliveryType();
		DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		deliveryTypeModel.addElement(nullVo);
		deliveryTypeModel.setSelectedItem(nullVo);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			deliveryTypeModel.addElement(vo);
		}
		this.basicPanel.getInternalDeliveryType().setModel(deliveryTypeModel);			
	}
	
    
    /**
     * 
     * （画布展开与收缩）
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-26 下午05:40:44
     */
    class CanvasPanelAction implements ActionListener {

    	JPanel contentPanel;

    	JButton btnClose;

    	ExpCanvasPanel canvasPanel;

    	
    	
    	public CanvasPanelAction(JPanel contentPanel, JButton btnClose) {
    		this.contentPanel = contentPanel;
    		this.btnClose = btnClose;
    		canvasPanel = new ExpCanvasPanel(contentPanel,
    					btnClose);
    	}

    	@Override
    	public void actionPerformed(ActionEvent e) {
    		canvasPanel.showCanvas();
    	}

    }
	/**
	 * @return the i18n
	 */
	public II18n getI18n() {
		return i18n;
	}

	/**
	 * @param i18n the i18n to set
	 */
	public void setI18n(II18n i18n) {
		this.i18n = i18n;
	}

	/**
	 * @return the waybillType
	 */
	public String getWaybillType() {
		return waybillType;
	}

	/**
	 * @param waybillType the waybillType to set
	 */
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	/**
	 * @return the canvasButtonPanel
	 */
	public JPanel getCanvasButtonPanel() {
		return canvasButtonPanel;
	}

	/**
	 * @param canvasButtonPanel the canvasButtonPanel to set
	 */
	public void setCanvasButtonPanel(JPanel canvasButtonPanel) {
		this.canvasButtonPanel = canvasButtonPanel;
	}

	/**
	 * @return the basicPanel
	 */
	public ExpBasicPanel getBasicPanel() {
		return basicPanel;
	}

	/**
	 * @param basicPanel the basicPanel to set
	 */
	public void setBasicPanel(ExpBasicPanel basicPanel) {
		this.basicPanel = basicPanel;
	}

	/**
	 * @return the consignerPanel
	 */
	public ExpConsignerPanel getConsignerPanel() {
		return consignerPanel;
	}

	/**
	 * @param consignerPanel the consignerPanel to set
	 */
	public void setConsignerPanel(ExpConsignerPanel consignerPanel) {
		this.consignerPanel = consignerPanel;
	}

	/**
	 * @return the consigneePanel
	 */
	public ExpConsigneePanel getConsigneePanel() {
		return consigneePanel;
	}

	/**
	 * @param consigneePanel the consigneePanel to set
	 */
	public void setConsigneePanel(ExpConsigneePanel consigneePanel) {
		this.consigneePanel = consigneePanel;
	}

	/**
	 * @return the buttonPanel
	 */
	public ExpButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @param buttonPanel the buttonPanel to set
	 */
	public void setButtonPanel(ExpButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	/**
	 * @return the numberPanel
	 */
	public ExpNumberPanel getNumberPanel() {
		return numberPanel;
	}

	/**
	 * @param numberPanel the numberPanel to set
	 */
	public void setNumberPanel(ExpNumberPanel numberPanel) {
		this.numberPanel = numberPanel;
	}

	/**
	 * @return the transferInfoPanel
	 */
	public ExpTransferInfoPanel getTransferInfoPanel() {
		return transferInfoPanel;
	}

	/**
	 * @param transferInfoPanel the transferInfoPanel to set
	 */
	public void setTransferInfoPanel(ExpTransferInfoPanel transferInfoPanel) {
		this.transferInfoPanel = transferInfoPanel;
	}

	/**
	 * @return the cargoInfoPanel
	 */
	public ExpCargoInfoPanel getCargoInfoPanel() {
		return cargoInfoPanel;
	}

	/**
	 * @param cargoInfoPanel the cargoInfoPanel to set
	 */
	public void setCargoInfoPanel(ExpCargoInfoPanel cargoInfoPanel) {
		this.cargoInfoPanel = cargoInfoPanel;
	}

	/**
	 * @return the incrementPanel
	 */
	public ExpIncrementPanel getIncrementPanel() {
		return incrementPanel;
	}

	/**
	 * @param incrementPanel the incrementPanel to set
	 */
	public void setIncrementPanel(ExpIncrementPanel incrementPanel) {
		this.incrementPanel = incrementPanel;
	}

	/**
	 * @return the billingPayPanel
	 */
	public ExpBillingPayPanel getBillingPayPanel() {
		return billingPayPanel;
	}

	/**
	 * @param billingPayPanel the billingPayPanel to set
	 */
	public void setBillingPayPanel(ExpBillingPayPanel billingPayPanel) {
		this.billingPayPanel = billingPayPanel;
	}

	/**
	 * @return the canvasPanel
	 */
	public ExpCanvasPanel getCanvasPanel() {
		return canvasPanel;
	}

	/**
	 * @param canvasPanel the canvasPanel to set
	 */
	public void setCanvasPanel(ExpCanvasPanel canvasPanel) {
		this.canvasPanel = canvasPanel;
	}

	/**
	 * @return the canvasContentPanel
	 */
	public ExpCanvasContentPanel getCanvasContentPanel() {
		return canvasContentPanel;
	}

	/**
	 * @param canvasContentPanel the canvasContentPanel to set
	 */
	public void setCanvasContentPanel(ExpCanvasContentPanel canvasContentPanel) {
		this.canvasContentPanel = canvasContentPanel;
	}

	/**
	 * @return the btnOpenCanvasPanel
	 */
	public JButton getBtnOpenCanvasPanel() {
		return btnOpenCanvasPanel;
	}

	/**
	 * @param btnOpenCanvasPanel the btnOpenCanvasPanel to set
	 */
	public void setBtnOpenCanvasPanel(JButton btnOpenCanvasPanel) {
		this.btnOpenCanvasPanel = btnOpenCanvasPanel;
	}

	/**
	 * @return the flightNumberType
	 */
	public DefaultComboBoxModel getFlightNumberType() {
		return flightNumberType;
	}

	/**
	 * @param flightNumberType the flightNumberType to set
	 */
	public void setFlightNumberType(DefaultComboBoxModel flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	/**
	 * @return the productTypeModel
	 */
	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}

	/**
	 * @param productTypeModel the productTypeModel to set
	 */
	public void setProductTypeModel(DefaultComboBoxModel productTypeModel) {
		this.productTypeModel = productTypeModel;
	}

	/**
	 * @return the pikcModeModel
	 */
	public DefaultComboBoxModel getPikcModeModel() {
		return pikcModeModel;
	}

	/**
	 * @param pikcModeModel the pikcModeModel to set
	 */
	public void setPikcModeModel(DefaultComboBoxModel pikcModeModel) {
		this.pikcModeModel = pikcModeModel;
	}

	/**
	 * @return the freightMethod
	 */
	public DefaultComboBoxModel getFreightMethod() {
		return freightMethod;
	}

	/**
	 * @param freightMethod the freightMethod to set
	 */
	public void setFreightMethod(DefaultComboBoxModel freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * @return the combRefundTypeModel
	 */
	public DefaultComboBoxModel getCombRefundTypeModel() {
		return combRefundTypeModel;
	}

	/**
	 * @param combRefundTypeModel the combRefundTypeModel to set
	 */
	public void setCombRefundTypeModel(DefaultComboBoxModel combRefundTypeModel) {
		this.combRefundTypeModel = combRefundTypeModel;
	}

	/**
	 * @return the combReturnBillTypeModel
	 */
	public DefaultComboBoxModel getCombReturnBillTypeModel() {
		return combReturnBillTypeModel;
	}

	/**
	 * @param combReturnBillTypeModel the combReturnBillTypeModel to set
	 */
	public void setCombReturnBillTypeModel(
			DefaultComboBoxModel combReturnBillTypeModel) {
		this.combReturnBillTypeModel = combReturnBillTypeModel;
	}

	/**
	 * @return the combPaymentModeModel
	 */
	public DefaultComboBoxModel getCombPaymentModeModel() {
		return combPaymentModeModel;
	}

	/**
	 * @param combPaymentModeModel the combPaymentModeModel to set
	 */
	public void setCombPaymentModeModel(DefaultComboBoxModel combPaymentModeModel) {
		this.combPaymentModeModel = combPaymentModeModel;
	}

	/**
	 * @return the combGoodsType
	 */
	public DefaultComboBoxModel getCombGoodsType() {
		return combGoodsType;
	}

	/**
	 * @param combGoodsType the combGoodsType to set
	 */
	public void setCombGoodsType(DefaultComboBoxModel combGoodsType) {
		this.combGoodsType = combGoodsType;
	}

	/**
	 * @return the combBillingType
	 */
	public DefaultComboBoxModel getCombBillingType() {
		return combBillingType;
	}

	/**
	 * @param combBillingType the combBillingType to set
	 */
	public void setCombBillingType(DefaultComboBoxModel combBillingType) {
		this.combBillingType = combBillingType;
	}

	/**
	 * @return the waybillService
	 */
	public IWaybillService getWaybillService() {
		return waybillService;
	}

	/**
	 * @param waybillService the waybillService to set
	 */
	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}

	/**
	 * @return the listener
	 */
	public ExpWaybillBindingListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ExpWaybillBindingListener listener) {
		this.listener = listener;
	}

	/**
	 * @return the waybillBinder
	 */
	public IBinder<ExpWaybillPanelVo> getWaybillBinder() {
		return waybillBinder;
	}

	/**
	 * @param waybillBinder the waybillBinder to set
	 */
	public void setWaybillBinder(IBinder<ExpWaybillPanelVo> waybillBinder) {
		this.waybillBinder = waybillBinder;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the zerostr
	 */
	public static String getZerostr() {
		return ZEROSTR;
	}

	/**
	 * @return the threehundred
	 */
	public static int getThreehundred() {
		return THREEHUNDRED;
	}

	/**
	 * @return the fifix
	 */
	public static int getFifix() {
		return FIFIX;
	}

	/**
	 * @return the defaultgrow
	 */
	public static String getDefaultgrow() {
		return DEFAULTGROW;
	}

	/**
	 * @param bindersMap the bindersMap to set
	 */
	public void setBindersMap(Map<String, IBinder<ExpWaybillPanelVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}
	/**
	 * @return 收货人地址 .
	 */
	public JTextFieldValidate getTxtConsigneeAddress() {
		return this.consigneePanel.getTxtConsigneeAddress();
	}
	/**
	 * @return 发货联系人 .
	 */
	public JTextFieldValidate getTxtConsignerLinkMan(){
		return this.consignerPanel.getTxtConsignerLinkMan();
	}
	
	/**
	 * @return 收货联系人 .
	 */
	public JTextFieldValidate getTxtConsigneeLinkMan(){
		return this.consigneePanel.getTxtConsigneeLinkMan();
	}
	
	/**
	 * @return 收货区域 .
	 */
	public JAddressFieldForExp getTxtConsigneeArea(){
		return this.consigneePanel.getTxtConsigneeArea();
	}
	
	/**
	 * @return 发货区域 .
	 */
	public JAddressField getTxtConsignerArea(){
		return this.consignerPanel.getTxtConsignerArea();
	}
	public IEditor getEditor() {
		return editor;
	}
	
	public void setEditor(IEditor editor) {
		this.editor = editor;
	}
	public String getWaybillState() {
		return waybillState;
	}

	public void setWaybillState(String waybillState) {
		this.waybillState = waybillState;
	}
	/**
	 * @return the importWaybillPanelVo
	 */
	public WaybillPanelVo getImportWaybillPanelVo() {
		return importWaybillPanelVo;
	}

	/**
	 * @param importWaybillPanelVo the importWaybillPanelVo to set
	 */
	public void setImportWaybillPanelVo(ExpWaybillPanelVo importWaybillPanelVo) {
		this.importWaybillPanelVo = importWaybillPanelVo;
	}
	
	public DefaultComboBoxModel getCombSpecialOffer() {
		return combSpecialOffer;
	}

	public void setCombSpecialOffer(DefaultComboBoxModel combSpecialOffer) {
		this.combSpecialOffer = combSpecialOffer;
	}

	public ExpWebOrderDialog getExpWebOrderDialog() {
		return expWebOrderDialog;
	}

	public void setExpWebOrderDialog(ExpWebOrderDialog expWebOrderDialog) {
		this.expWebOrderDialog = expWebOrderDialog;
	}

	public PickupGoodsBranchDialog getPickupGoodsBranchDialog() {
		return pickupGoodsBranchDialog;
	}

	public void setPickupGoodsBranchDialog(
			PickupGoodsBranchDialog pickupGoodsBranchDialog) {
		this.pickupGoodsBranchDialog = pickupGoodsBranchDialog;
	}

	public ExpQueryPublishPriceUI getExpQueryPublishPriceUI() {
		return expQueryPublishPriceUI;
	}

	public void setExpQueryPublishPriceUI(
			ExpQueryPublishPriceUI expQueryPublishPriceUI) {
		this.expQueryPublishPriceUI = expQueryPublishPriceUI;
	}

	public ExpCalculateCostsDialog getExpCalculateCostsDialog() {
		return expCalculateCostsDialog;
	}

	public void setExpCalculateCostsDialog(
			ExpCalculateCostsDialog expCalculateCostsDialog) {
		this.expCalculateCostsDialog = expCalculateCostsDialog;
	}
}
