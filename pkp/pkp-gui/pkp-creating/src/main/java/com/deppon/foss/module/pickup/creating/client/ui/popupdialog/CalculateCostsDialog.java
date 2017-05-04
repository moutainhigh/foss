package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.ShowCalculateDialogAction;
import com.deppon.foss.module.pickup.creating.client.action.VehiclePriceDialogUIAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.CalculateBindingListener;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.editui.CanvasContentPanel;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@ContainerSeq(seq=1)
public class CalculateCostsDialog extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -641058732241249707L;
	
	private static final Log log = LogFactory.getLog(CalculateCostsDialog.class);

	/**
	 * 10
	 */
	private static final int TEN = 10;
	
	private static final int FOUR = 4;
	
	private static final int EIGHT = 8;
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateCostsDialog.class);
	private Map<String, IBinder<WaybillPanelVo>> bindersMap = new HashMap<String, IBinder<WaybillPanelVo>>();
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	/**
	 * fieldName
	 */
	private static final String FIELDNAME = "fieldName";
	private static final String ZEROSTR = "0";
	private static final int NUM_826 = 826;
	private static final int NUM_537 = 537;
	private static final int NUM_144 = 144;
	private static final int NUM_255 = 255;
	
	private static final double TWOPOINTFIVE = 2.5;
	
	/**
	 * @description：保存绑定对象
	 * @date 2013-03-28
	 * @author WangQianJin
	 */
	public void registToRespository() {
		bindersMap.put("waybillBinder", waybillBinder);
	}
	
	private CalculateVehicleDialog calculateVehicleDialog;
	
	//运单开单类型（集中开单、营业部开单）
	private String waybillType;
	
	//收货部门
	@Bind("receiveOrgName")
	@FocusSeq(seq=1)
	private JTextFieldValidate textReceiveOrgName;
	//查询收货部门放大镜
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = QueryReceiveOrgAction.class)
	@FocusSeq(seq=2)
	private JButton btnQueryReceive;
	// 运输性质
	private DefaultComboBoxModel productTypeModel;	
	// 运输性质
	@Bind("productCode")
	@FocusSeq(seq=3)
	private JComboBox combProductType;
	// 提货方式
	private DefaultComboBoxModel pikcModeModel;
	// 提货方式
	@Bind("receiveMethod")
	@FocusSeq(seq=4)
	private JComboBox combPickMode;
	// 目的站
	@Bind("targetOrgCode")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "目的站") })
	@FocusSeq(seq=5)
	private JTextFieldValidate txtDestination;
	//查询目的站放大镜
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ShowCalculateDialogAction.class)
	@FocusSeq(seq=6)
	private JButton btnQueryBranch;
	//合票方式
	@Bind("freightMethod")
	@FocusSeq(seq=7)
	private JComboBox combFreightMethod;
	//航班类型
	private DefaultComboBoxModel flightNumberType;
	//航班类型
	@Bind("flightNumberType")
	@FocusSeq(seq=8)
	private JComboBox combFlightNumberType;
	//合票方式
	private DefaultComboBoxModel freightMethod;	
	//航班时间
	@Bind("flightShift")
	@FocusSeq(seq=9)
	private JTextFieldValidate txtFlightTime;
	/**
	 * 是否上门接货
	 */
	@Bind("pickupToDoor")
	@FocusSeq(seq=10)
	private JCheckBox chckbxPickUp;
	/**
	 * 保价声明
	 */
	@Bind("insuranceAmount")
	@FocusSeq(seq=11)
	private JTextFieldValidate txtInsuranceAmount;
	//提货网点
	@Bind("customerPickupOrgName")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "提货网点") })
	@FocusSeq(seq=12)
	private JTextFieldValidate txtBranch;
	//总件数
	@Bind("goodsQtyTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总件数") })
	@FocusSeq(seq=13)
	private JTextFieldValidate txtTotalPieces;
	//总重量
	@Bind("goodsWeightTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总重量") })
	@FocusSeq(seq=14)
	private JTextFieldValidate txtWeight;
	//总体积
	@Bind("goodsVolumeTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总体积") })
	@FocusSeq(seq=15)
	private JTextFieldValidate txtVolume;
	/**
	 * 货物尺寸
	 */
	@Bind("goodsSize")
	@FocusSeq(seq = 16)
	private JTextFieldValidate txtSize;	
	//计费类型
	private DefaultComboBoxModel combBillingType;
	//计费类型
	@Bind("billingType")
	@FocusSeq(seq=17)
	private JComboBox billingType;
	/**
	 * 付款方式
	 */
	@Bind("paidMethod")
	@FocusSeq(seq=18)
	private JComboBox combPaidMethod;
	private DefaultComboBoxModel combPaymentModeModel;	
	/**
	 * 代收货款
	 */
	@Bind("codAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收货款") })
	@FocusSeq(seq=19)
	private JTextFieldValidate txtCashOnDelivery;
	/**
	 * 货物类型  A类
	 */
//	@Bind("goodsType#A")
//	@FocusSeq(seq=20)
//	private JRadioButton rbnA;
	/**
	 * 货物类型 B类
	 */
//	@Bind("goodsType#B")
//	@FocusSeq(seq=21)
//	private JRadioButton rbnB;	
	/**
	 * 空运货物类型
	 */
	@Bind("airGoodsType")
	private JComboBox combGoodsType;
	// 空运货物类型
	private DefaultComboBoxModel combGoodsTypeModel;
	/**
	 * 费率
	 */
	@Bind("unitPrice")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "费率") })
	@FocusSeq(seq=22)
	private JTextFieldValidate txtUnitPrice;
	/**
	 * 公布价运费
	 */
	@Bind("transportFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "公布价运费") })
	@FocusSeq(seq=23)
	private JTextFieldValidate txtTransportFee;
	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	@FocusSeq(seq=24)
	private JTextFieldValidate txtInsuranceRate;
	/**
	 * 保价费
	 */
	@Bind("insuranceFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费") })
	@FocusSeq(seq=25)
	private JTextFieldValidate txtInsuranceFee;
	/**
	 * 接货费
	 */
	@Bind("pickUpFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "接货费") })
	@FocusSeq(seq=26)
	private JTextFieldValidate txtPickUpFee;
	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "送货费") })
	@FocusSeq(seq=27)
	private JTextFieldValidate txtDeliveryGoodsFee;	
	/**
	 * 代收费率
	 */
	@Bind("codRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收费率") })
	@FocusSeq(seq=28)
	private JTextFieldValidate txtCodRate;
	/**
	 * 代收手续费
	 */
	@Bind("codFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收手续费") })
	@FocusSeq(seq=29)
	private JTextFieldValidate txtCodFee;
	//总运费
	@Bind("totalFee")
	@FocusSeq(seq=30)
	private JTextFieldValidate txtTotalFreight;
	/**
	 * 其他费用
	 */
	@Bind("otherFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "其他费用") })
	@FocusSeq(seq=31)
	private JTextFieldValidate txtOtherCharge;
	
	//计算总运费
	@FocusSeq(seq=32)
	private JButton btnProFreight;
	//关闭按钮	
	@FocusSeq(seq=33)
	private JButton btnClose;
	//整车报价按钮
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "", type = VehiclePriceDialogUIAction.class)
	JButton btnVehiclePrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.vehiclePrice.label"));
	
	// 绑定接口
	private IBinder<WaybillPanelVo> waybillBinder;
	//事件监听
	private CalculateBindingListener listener;
	
	
	/**
	 * 增值业务面板
	 */
	public IncrementPanel incrementPanel;
	/**
	 * 画布信息
	 */
	public CanvasContentPanel canvasContentPanel;

	/**
	 * 其他費用
	 */
	JXTable tblOther;
	JScrollPane scrollPane;
	
	
	public JTextFieldValidate getTxtCodRate() {
		return txtCodRate;
	}


	
	public void setTxtCodRate(JTextFieldValidate txtCodRate) {
		this.txtCodRate = txtCodRate;
	}


	
	public JTextFieldValidate getTxtCodFee() {
		return txtCodFee;
	}


	
	public void setTxtCodFee(JTextFieldValidate txtCodFee) {
		this.txtCodFee = txtCodFee;
	}


	public JTextFieldValidate getTxtCashOnDelivery() {
		return txtCashOnDelivery;
	}

	
	public void setTxtCashOnDelivery(JTextFieldValidate txtCashOnDelivery) {
		this.txtCashOnDelivery = txtCashOnDelivery;
	}

	
	public JTextFieldValidate getTxtUnitPrice() {
		return txtUnitPrice;
	}

	
	public void setTxtUnitPrice(JTextFieldValidate txtUnitPrice) {
		this.txtUnitPrice = txtUnitPrice;
	}

	
	public JTextFieldValidate getTxtTransportFee() {
		return txtTransportFee;
	}

	
	public void setTxtTransportFee(JTextFieldValidate txtTransportFee) {
		this.txtTransportFee = txtTransportFee;
	}

	
	public JTextFieldValidate getTxtInsuranceRate() {
		return txtInsuranceRate;
	}

	
	public void setTxtInsuranceRate(JTextFieldValidate txtInsuranceRate) {
		this.txtInsuranceRate = txtInsuranceRate;
	}

	
	public JTextFieldValidate getTxtInsuranceFee() {
		return txtInsuranceFee;
	}

	
	public void setTxtInsuranceFee(JTextFieldValidate txtInsuranceFee) {
		this.txtInsuranceFee = txtInsuranceFee;
	}

	
	public JTextFieldValidate getTxtPickUpFee() {
		return txtPickUpFee;
	}

	
	public void setTxtPickUpFee(JTextFieldValidate txtPickUpFee) {
		this.txtPickUpFee = txtPickUpFee;
	}

	
	public JTextFieldValidate getTxtDeliveryGoodsFee() {
		return txtDeliveryGoodsFee;
	}

	
	public void setTxtDeliveryGoodsFee(JTextFieldValidate txtDeliveryGoodsFee) {
		this.txtDeliveryGoodsFee = txtDeliveryGoodsFee;
	}	
	
	public JComboBox getCombGoodsType() {
		return combGoodsType;
	}
	
	public void setCombGoodsType(JComboBox combGoodsType) {
		this.combGoodsType = combGoodsType;
	}
	
	public DefaultComboBoxModel getCombGoodsTypeModel() {
		return combGoodsTypeModel;
	}
	
	public void setCombGoodsTypeModel(DefaultComboBoxModel combGoodsTypeModel) {
		this.combGoodsTypeModel = combGoodsTypeModel;
	}
	public JCheckBox getChckbxPickUp() {
		return chckbxPickUp;
	}	
	public void setChckbxPickUp(JCheckBox chckbxPickUp) {
		this.chckbxPickUp = chckbxPickUp;
	}
	public HashMap<String, IBinder<WaybillPanelVo>> getBindersMap() {
		return( HashMap<String, IBinder<WaybillPanelVo>>) bindersMap;
	}
	public void setBindersMap(Map<String, IBinder<WaybillPanelVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}	
	public JComboBox getBillingType() {
		return billingType;
	}
	public void setBillingType(JComboBox billingType) {
		this.billingType = billingType;
	}
	public JButton getBtnProFreight() {
		return btnProFreight;
	}
	public void setBtnProFreight(JButton btnProFreight) {
		this.btnProFreight = btnProFreight;
	}
	public JButton getBtnClose() {
		return btnClose;
	}
	public void setBtnClose(JButton btnClose) {
		this.btnClose = btnClose;
	}
	public DefaultComboBoxModel getCombBillingType() {
		return combBillingType;
	}
	public void setCombBillingType(DefaultComboBoxModel combBillingType) {
		this.combBillingType = combBillingType;
	}
	public JTextFieldValidate getTxtInsuranceAmount() {
		return txtInsuranceAmount;
	}
	public void setTxtInsuranceAmount(
			JTextFieldValidate txtInsuranceAmount) {
		this.txtInsuranceAmount = txtInsuranceAmount;
	}
	public JComboBox getCombPaidMethod() {
		return combPaidMethod;
	}
	public void setCombPaidMethod(JComboBox combPaidMethod) {
		this.combPaidMethod = combPaidMethod;
	}
	public DefaultComboBoxModel getCombPaymentModeModel() {
		return combPaymentModeModel;
	}
	public void setCombPaymentModeModel(DefaultComboBoxModel combPaymentModeModel) {
		this.combPaymentModeModel = combPaymentModeModel;
	}
	public CanvasContentPanel getCanvasContentPanel() {
		return canvasContentPanel;
	}	
	public void setCanvasContentPanel(CanvasContentPanel canvasContentPanel) {
		this.canvasContentPanel = canvasContentPanel;
	}
	/*public JRadioButton getRbnA() {
		return rbnA;
	}
	public void setRbnA(JRadioButton rbnA) {
		this.rbnA = rbnA;
	}
	public JRadioButton getRbnB() {
		return rbnB;
	}
	public void setRbnB(JRadioButton rbnB) {
		this.rbnB = rbnB;
	}*/
	public JTextFieldValidate getTxtDestination() {
		return txtDestination;
	}
	public void setTxtDestination(JTextFieldValidate txtDestination) {
		this.txtDestination = txtDestination;
	}
	public JTextFieldValidate getTxtFlightTime() {
		return txtFlightTime;
	}
	public void setTxtFlightTime(JTextFieldValidate txtFlightTime) {
		this.txtFlightTime = txtFlightTime;
	}
	public JTextFieldValidate getTxtTotalPieces() {
		return txtTotalPieces;
	}
	public void setTxtTotalPieces(JTextFieldValidate txtTotalPieces) {
		this.txtTotalPieces = txtTotalPieces;
	}
	public JTextFieldValidate getTxtWeight() {
		return txtWeight;
	}
	public void setTxtWeight(JTextFieldValidate txtWeight) {
		this.txtWeight = txtWeight;
	}
	public JTextFieldValidate getTxtVolume() {
		return txtVolume;
	}
	public void setTxtVolume(JTextFieldValidate txtVolume) {
		this.txtVolume = txtVolume;
	}
	public JTextFieldValidate getTxtBranch() {
		return txtBranch;
	}
	public void setTxtBranch(JTextFieldValidate txtBranch) {
		this.txtBranch = txtBranch;
	}
	public JTextFieldValidate getTxtTotalFreight() {
		return txtTotalFreight;
	}
	public void setTxtTotalFreight(JTextFieldValidate txtTotalFreight) {
		this.txtTotalFreight = txtTotalFreight;
	}
	public IWaybillService getWaybillService() {
		return waybillService;
	}
	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}
	public IBinder<WaybillPanelVo> getWaybillBinder() {
		return waybillBinder;
	}
	public void setWaybillBinder(IBinder<WaybillPanelVo> waybillBinder) {
		this.waybillBinder = waybillBinder;
	}
	public CalculateBindingListener getListener() {
		return listener;
	}
	public void setListener(CalculateBindingListener listener) {
		this.listener = listener;
	}
	public IncrementPanel getIncrementPanel() {
		return incrementPanel;
	}
	public void setIncrementPanel(IncrementPanel incrementPanel) {
		this.incrementPanel = incrementPanel;
	}
	public JButton getBtnQueryBranch() {
		return btnQueryBranch;
	}
	public void setBtnQueryBranch(JButton btnQueryBranch) {
		this.btnQueryBranch = btnQueryBranch;
	}
	public JComboBox getCombFreightMethod() {
		return combFreightMethod;
	}
	public void setCombFreightMethod(JComboBox combFreightMethod) {
		this.combFreightMethod = combFreightMethod;
	}
	public DefaultComboBoxModel getFreightMethod() {
		return freightMethod;
	}
	public void setFreightMethod(DefaultComboBoxModel freightMethod) {
		this.freightMethod = freightMethod;
	}
	public JComboBox getCombFlightNumberType() {
		return combFlightNumberType;
	}
	public void setCombFlightNumberType(JComboBox combFlightNumberType) {
		this.combFlightNumberType = combFlightNumberType;
	}
	public DefaultComboBoxModel getFlightNumberType() {
		return flightNumberType;
	}
	public void setFlightNumberType(DefaultComboBoxModel flightNumberType) {
		this.flightNumberType = flightNumberType;
	}
	public JComboBox getCombPickMode() {
		return combPickMode;
	}
	public void setCombPickMode(JComboBox combPickMode) {
		this.combPickMode = combPickMode;
	}
	public DefaultComboBoxModel getPikcModeModel() {
		return pikcModeModel;
	}
	public void setPikcModeModel(DefaultComboBoxModel pikcModeModel) {
		this.pikcModeModel = pikcModeModel;
	}
	public JComboBox getCombProductType() {
		return combProductType;
	}
	public void setCombProductType(JComboBox combProductType) {
		this.combProductType = combProductType;
	}
	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}
	public void setProductTypeModel(DefaultComboBoxModel productTypeModel) {
		this.productTypeModel = productTypeModel;
	}
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	/**
	 * Create the dialog.
	 */
	public CalculateCostsDialog() {
		//初始化表格
		initTable();
		
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_826, NUM_537);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("top:default:grow"),
				RowSpec.decode("top:default"),}));
		{
			JPanel panel = new JPanel();
			panel.setToolTipText("4343");
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.waybill"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(NumberConstants.NUMBER_30, NUM_144, NUM_255)));
			getContentPane().add(panel, "1, 1");
			FormLayout flPanel = new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("left:default"),
					ColumnSpec.decode("max(50dlu;default)"),
					ColumnSpec.decode("max(63dlu;default):grow"),
					ColumnSpec.decode("max(9dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(35dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(57dlu;default):grow"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(35dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,},
				new RowSpec[] {
					RowSpec.decode("20dlu"),
					RowSpec.decode("20dlu:grow"),
					RowSpec.decode("20dlu"),
					RowSpec.decode("20dlu"),
					RowSpec.decode("20dlu"),
					RowSpec.decode("20dlu:grow"),
					RowSpec.decode("20dlu"),});
			panel.setLayout(flPanel);
			{
				//收货部门
				JLabel labelReceiveOrgName = new JLabel(i18n.get("foss.gui.creating.basicPanel.salesDepartment.label")+"：");
				panel.add(labelReceiveOrgName, "2, 1");
			}
			{
				textReceiveOrgName = new JTextFieldValidate();
				panel.add(textReceiveOrgName, "3, 1");
				textReceiveOrgName.setColumns(TEN);
			}
			{
				btnQueryReceive = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));				
				//btnQueryReceive.setMargin(new Insets(-2, 1, -2, 1));
				btnQueryReceive.setBorderPainted(false);
				btnQueryReceive.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						
					}
				});
				panel.add(btnQueryReceive, "6, 1");
			}
			{	
				//整车报价按钮
				panel.add(btnVehiclePrice, "15, 1, 12, 1");
			}
			{
				//运输性质
				JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label")+"：");
				panel.add(label, "2, 2");
			}
			{
				combProductType = new JComboBox();
				panel.add(combProductType, "3, 2");
			}
			{
				//提货方式
				JLabel lblNewLabelN1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickMode.label")+"：");
				panel.add(lblNewLabelN1, "6, 2");
			}
			{
				combPickMode = new JComboBox();
				panel.add(combPickMode, "8, 2");
			}
			{
				//目的站
				JLabel lblNewLabelN2 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.destination.label")+"：");
				panel.add(lblNewLabelN2, "10, 2");
			}
			{
				txtDestination = new JTextFieldValidate();
				panel.add(txtDestination, "12, 2, 21, 1");
				txtDestination.setColumns(TEN);
			}
			{
				btnQueryBranch = new JButton();
				btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
				btnQueryBranch.setBorderPainted(false);
				btnQueryBranch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						
					}
				});
				panel.add(btnQueryBranch, "34, 2");
			}
			{
				//合票方式
				JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.freightMethod.label")+"：");
				panel.add(label, "2, 3");
			}
			{
				combFreightMethod = new JComboBox();
				panel.add(combFreightMethod, "3, 3");
				combFreightMethod.setEnabled(false);
			}
			{
				//航班类型
				JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightNumberType.label")+"：");
				panel.add(label, "6, 3");
			}
			{
				combFlightNumberType = new JComboBox();
				panel.add(combFlightNumberType, "8, 3");
				combFlightNumberType.setEnabled(false);
			}
			{
				//航班时间
				JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.flightShift.label")+"：");
				panel.add(label, "10, 3");
			}
			{
				txtFlightTime = new JTextFieldValidate();
				panel.add(txtFlightTime, "12, 3, 21, 1");
				txtFlightTime.setColumns(TEN);
				txtFlightTime.setColumns(TEN);
				txtFlightTime.setEditable(false);
			}
			{
				//上门接货
				chckbxPickUp = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
				panel.add(chckbxPickUp, "2, 4");
			}
			{
				//保价声明
				JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceAmount.label")+"：");
				panel.add(label, "6, 4, left, default");
			}
			{
				txtInsuranceAmount = new JTextFieldValidate();
				panel.add(txtInsuranceAmount, "8, 4, fill, default");
				txtInsuranceAmount.setColumns(TEN);
			}
			{
				//提货网点
				JLabel lblNewLabelN3 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickBranch.label")+"：");
				panel.add(lblNewLabelN3, "10, 4, left, default");
			}
			{
				txtBranch = new JTextFieldValidate();
				panel.add(txtBranch, "12, 4, 21, 1, fill, default");
				txtBranch.setColumns(TEN);
			}
			{
				//总件数
				JLabel lblNewLabelN4 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalPieces.label"));
				panel.add(lblNewLabelN4, "2, 5");
			}
			{
				txtTotalPieces = new JTextFieldValidate();
				//zxy 20130916 BUG-54936 start 新增：重量,件数等不能输入负数和字符
				txtTotalPieces.setDocument(new PackageNumberDocument(FOUR));
				//zxy 20130916 BUG-54936 end 新增：重量,件数等不能输入负数和字符
				panel.add(txtTotalPieces, "3, 5");
				txtTotalPieces.setColumns(TEN);
			}
			{
				//总重量
				JLabel label = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalWeight.label"));
				panel.add(label, "6, 5");
			}
			{
				txtWeight = new JTextFieldValidate();
				//zxy 20130916 BUG-54936 start 新增：重量,件数等不能输入负数和字符
				FloatDocument weight = new FloatDocument(EIGHT,2);
				txtWeight.setDocument(weight);
				//zxy 20130916 BUG-54936 end 新增：重量,件数等不能输入负数和字符
				panel.add(txtWeight, "8, 5");
				txtWeight.setColumns(TEN);
			}
			{
				JLabel labelN11 = new JLabel(i18n.get("foss.gui.creating.cargoInfoPanel.size.label")+"：");
				panel.add(labelN11, "10, 5");
			}
			{
				//货物尺寸
				txtSize = new JTextFieldValidate();
				panel.add(txtSize, "12, 5, 21, 1, fill, default");
				txtSize.setColumns(TEN);
			}
			{
				//总体积
				JLabel lblNewLabelN5 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalVolume.label"));
				panel.add(lblNewLabelN5, "2, 6");
			}
			{
				txtVolume = new JTextFieldValidate();
				//zxy 20130916 BUG-54936 start 新增：重量,件数等不能输入负数和字符
				FloatDocument volume = new FloatDocument(EIGHT,2);
				txtVolume.setDocument(volume);
				//zxy 20130916 BUG-54936 end 新增：重量,件数等不能输入负数和字符
				panel.add(txtVolume, "3, 6");
				txtVolume.setColumns(TEN);
			}
			{
				//计费类型
				JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.billingType.label")+"：");
				panel.add(label, "6, 6, left, default");
			}
			{
				billingType = new JComboBox();
				panel.add(billingType, "8, 6, fill, default");
				billingType.setEnabled(false);
			}
			{
				//付款方式
				JLabel labelN1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.paymentMode.label")+"：");
				panel.add(labelN1, "10, 6");
			}
			{
				combPaidMethod = new JComboBox();
				panel.add(combPaidMethod, "12, 6, 21, 1, fill, default");
			}
			{
				//代收货款
				JLabel labelN2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label")+"：");
				panel.add(labelN2, "2, 7");
			}
			{
				txtCashOnDelivery = new JTextFieldValidate();
				//zxy 20130916 BUG-54936 start 新增：重量,件数等不能输入负数和字符
				NumberDocument cashOnDelivery = new NumberDocument(EIGHT);
				txtCashOnDelivery.setDocument(cashOnDelivery);
				//zxy 20130916 BUG-54936 end 新增：重量,件数等不能输入负数和字符
				panel.add(txtCashOnDelivery, "3, 7, fill, default");
				txtCashOnDelivery.setColumns(TEN);
			}
			/**
			 * 删除页面中展示的AB类型
			 * @author Foss-278328-hujinyang
			 * @time 20151228 11:24
			 */
//			{
//				//货物类型
//				JLabel label = new JLabel(i18n.get("foss.gui.creating.linkTableMode.column.eighteen")+"：");
//				panel.add(label, "6, 7");
//			}
			{
				JPanel panelN1 = new JPanel();
				panelN1.setBorder(null);
				panel.add(panelN1, "8, 7, left, center");
				panelN1.setLayout(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("center:max(20dlu;default)"),
						ColumnSpec.decode("center:max(20dlu;default)"),
						FormFactory.DEFAULT_COLSPEC,},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,}));
//				{
//					rbnA = new JRadioButton("A");
//					panel_1.add(rbnA, "1, 1, center, center");
//				}
//				{
//					rbnB = new JRadioButton("B");
//					panel_1.add(rbnB, "2, 1");
//				}
				{		
					combGoodsType = new JComboBox();
					panelN1.add(combGoodsType, "3, 1");
					combGoodsType.setVisible(false);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.freight"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(NumberConstants.NUMBER_30, NUM_144, NUM_255)));
			getContentPane().add(panel, "1, 2, fill, fill");
			panel.setLayout(new FormLayout(new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					ColumnSpec.decode("max(49dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(83dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(27dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(175dlu;default):grow"),},
				new RowSpec[] {
					RowSpec.decode("20dlu:grow"),
					RowSpec.decode("18dlu"),
					RowSpec.decode("18dlu"),
					RowSpec.decode("18dlu"),
					RowSpec.decode("20dlu"),}));
			{
				//费率
				JLabel label3 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.unitPrice.label")+"：");
				panel.add(label3, "2, 1");
			}
			{
				txtUnitPrice = new JTextFieldValidate();
				panel.add(txtUnitPrice, "4, 1, fill, default");
				txtUnitPrice.setColumns(TEN);
				txtUnitPrice.setEditable(false);
			}
			{
				//公布价运费
				JLabel label4 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.transportFee.label")+"：");
				panel.add(label4, "6, 1");
			}
			{
				txtTransportFee = new JTextFieldValidate();
				panel.add(txtTransportFee, "8, 1, fill, default");
				txtTransportFee.setColumns(TEN);
				txtTransportFee.setEditable(false);
			}
			{
				scrollPane = new JScrollPane(tblOther);
				panel.add(scrollPane, "10, 1, 3, 4, fill, fill");
			}
			{
				//保价费率
				JLabel label5 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label")+"：");
				panel.add(label5, "2, 2");
			}
			{
				JPanel panel2 = new JPanel();
				panel.add(panel2, "4, 2, fill, center");
				panel2.setLayout(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("center:max(80dlu;default)"),
						ColumnSpec.decode("center:max(12dlu;default)"),},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,}));
				{
					txtInsuranceRate = new JTextFieldValidate();
					panel2.add(txtInsuranceRate, "1, 1");
					txtInsuranceRate.setColumns(NumberConstants.NUMBER_20);
					txtInsuranceRate.setEditable(false);
				}
				{
					JLabel lblNewLabel = new JLabel("‰");
					panel2.add(lblNewLabel, "2, 1");
				}
			}
			{
				//保价费
				JLabel label6 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label")+"：");
				panel.add(label6, "6, 2");
			}
			{
				txtInsuranceFee = new JTextFieldValidate();
				panel.add(txtInsuranceFee, "8, 2, fill, default");
				txtInsuranceFee.setColumns(TEN);
				txtInsuranceFee.setEditable(false);
			}
			{
				//接货费
				JLabel label7 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.pickUpFee.label")+"：");
				panel.add(label7, "2, 3");
			}
			{
				txtPickUpFee = new JTextFieldValidate();
				panel.add(txtPickUpFee, "4, 3, fill, default");
				txtPickUpFee.setColumns(TEN);
				txtPickUpFee.setEditable(false);
			}
			{
				//送货费
				JLabel label8 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.deliveryGoodsFee.label")+"：");
				panel.add(label8, "6, 3");
			}
			{
				txtDeliveryGoodsFee = new JTextFieldValidate();
				panel.add(txtDeliveryGoodsFee, "8, 3, fill, default");
				txtDeliveryGoodsFee.setColumns(TEN);
				txtDeliveryGoodsFee.setEditable(false);
			}
			{				
				JLabel label9 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codRate.label")+"：");
				panel.add(label9, "2, 4");
			}
			{
				JPanel panel3 = new JPanel();
				panel.add(panel3, "4, 4, fill, center");
				panel3.setLayout(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("center:max(80dlu;default)"),
						ColumnSpec.decode("center:max(12dlu;default)"),},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,}));
				{
					txtCodRate = new JTextFieldValidate();
					panel3.add(txtCodRate, "1, 1");
					txtCodRate.setColumns(NumberConstants.NUMBER_20);
					txtCodRate.setEditable(false);
				}
				{
					JLabel lblNewLabel = new JLabel("‰");
					panel3.add(lblNewLabel, "2, 1");
				}
			}
			{
				JLabel label10 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codFee.label")+"：");
				panel.add(label10, "6, 4");
			}
			{
				txtCodFee = new JTextFieldValidate();
				panel.add(txtCodFee, "8, 4, fill, default");
				txtCodFee.setColumns(TEN);
				txtCodFee.setEditable(false);
			}
			{
				//总费用
				JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.totalFee.label")+"：");
				panel.add(label, "2, 5");
			}
			{
				txtTotalFreight = new JTextFieldValidate();
				panel.add(txtTotalFreight, "4, 5");
				txtTotalFreight.setColumns(TEN);
				txtTotalFreight.setEditable(false);
			}
			{
				JLabel lblNewLabel6 = new JLabel("其他费用合计：");
				panel.add(lblNewLabel6, "10, 5, right, default");
			}
			{
				txtOtherCharge = new JTextFieldValidate();
				panel.add(txtOtherCharge, "12, 5, fill, default");
				txtOtherCharge.setColumns(TEN);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, "1, 3");
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				btnProFreight = new JButton(i18n.get("foss.gui.creating.billingPayPanel.button.calculate.label"));
				btnProFreight.setActionCommand("OK");				
				ImageIcon icon = ImageUtil.getImageIcon(this.getClass(),IconConstants.PREVIEWPNG);						
				btnProFreight.setIcon(icon);
				//当点击计算总运费调用方法
				btnProFreight.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//zxy 20131009 BUG-56504 start 修改：增加异常捕捉try-catch
						try{
							List<ValidationError> errorList = waybillBinder.validate();
							Boolean bool = false;
							if (errorList != null) {
								bool = Common.validateDescriptor(errorList);
							}
							if (bool) {									
								WaybillPanelVo bean = waybillBinder.getBean();
								// 清理所有费用相关的信息
								cleanFee(bean);
								//BUG-56504  修改：张兴旺 2013-10-2 18:46:38
								//验证是否能开代收
								validateCod(bean);	
								//wutao
								//验证：当运输性质为门到门的时候，提货方式不能为【自提】
								validateTransporTypeDTDSelectedOwn(bean);
								// 验证空运货物类型不能为空
								validateAirGoodsType(bean);
								//校验精准大票
								validateIsBigGoods(bean);
								// 最低一票
								BigDecimal minTransportFee = BigDecimal.ZERO;
								//获取公布价运费
								ProductPriceDto dto = calculateTransportFee(bean);
								if(dto!=null){
									minTransportFee = dto.getMinFee();// 最低一票
									bean.setMinTransportFee(minTransportFee);
									// 计算增值服务费
									calculateValueAdd(bean);						
									/**
									 * 计算总运费公共方法
									 */
									CalculateFeeTotalUtils.calculateFee(bean, true);
								}							
							}
						}catch(WaybillValidateException w){
							if (!"".equals(w.getMessage())) {
								MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
							}
						}
						//zxy 20131009 BUG-56504 end 修改：增加异常捕捉try-catch
					}
				});
				buttonPane.add(btnProFreight);
				getRootPane().setDefaultButton(btnProFreight);
			}
			{
				btnClose = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.close"));
				btnClose.setActionCommand("Cancel");
				ImageIcon icon = ImageUtil.getImageIcon(this.getClass(),IconConstants.DELETE);					
				btnClose.setIcon(icon);
				//当点击关闭按钮时调用
				btnClose.addActionListener(new MyActionListener(this));				
				buttonPane.add(btnClose);
			}
		}
		// 设置模态
		//setModal(true);
		//初始化下拉框
		initComboBox();
		//绑定Action
		bind();		
		//获取bean
		WaybillPanelVo bean = waybillBinder.getBean();
		//设置默认值
		setCombDefualtValue(bean);
		//保存绑定对象
		registToRespository();
		//初始化Vo
		initVo(bean);
		//数据初始化
		focusWaybillInit(bean);
		//初始化保险金额
		initInsuranceAmount(bean);
		calculateVehicleDialog = new CalculateVehicleDialog();
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
			if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode()) && !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(null, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmValidate"), i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					txtCashOnDelivery.requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.three"));
				}
			}
		} else {
			if(!bean.getIsWholeVehicle())
			{
				if(!FossConstants.YES.equals(bean.getCanAgentCollected()))
				{
					//zxy 20131009 BUG-56504 start 修改：如果不允许开代收款，则直接抛出异常
//					MsgBox.showInfo("对不起，您选择的网点不允许开代收货款！");
					txtCashOnDelivery.setText(BigDecimal.ZERO.toString());
					txtCashOnDelivery.requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notDoRefundType"));
//					return;
					//zxy 20131009 BUG-56504 end 修改：如果不允许开代收款，则直接抛出异常
				}
			}
		}
	}
	
	
	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		//初始化表格
		tblOther = new JXTable();
		//设置表格数据模型
		tblOther.setModel(new OtherChargeModel());
		//设置表格可以有滚动条
		tblOther.setAutoscrolls(true);
		//禁止表格排序
		tblOther.setSortable(false);
		//表格设置为不可编辑
		tblOther.setEditable(false);
		//设置列可见状态
		tblOther.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tblOther.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) tblOther.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	
	public JXTable getTblOther() {
		return tblOther;
	}



	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> data) {
		OtherChargeModel otherChargeMode = ((OtherChargeModel) tblOther
				.getModel());
		otherChargeMode.setData(data);
		// 刷新表格数据
		otherChargeMode.fireTableDataChanged();
	}
	
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class OtherChargeModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 折扣优惠数据VO
		 */
		private List<OtherChargeVo> data;


		public List<OtherChargeVo> getData() {
			return data;
		}

		public void setData(List<OtherChargeVo> data) {
			this.data = data;
		}
		

		/**
		 * 设置单元格的头
		 * 
		 */
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.canvasContentPanel.otherFee.label");
			case 1:
				return i18n.get("foss.gui.creating.incrementPanel.gridValue.label");
			default:
				return "";
			}
		}
		
		/**
		 * 获得列数
		 */
		public int getColumnCount() {
			return 2;
		}

		/**
		 * 获得行数
		 */
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		/**
		 * 获得数据
		 */
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getChargeName();
			case 1:
				return data.get(row).getMoney();
			default:
				return super.getValueAt(row, column);
			}

		}
	}
	
	
	
	
	/***
	 * 
	 * 清理所有与费用相关的信息
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void cleanFee(WaybillPanelVo bean) {
		// 清理代收货款
		cleanCod(bean);
		// 清理保价
		cleanInsurance(bean);		
		// 清理产品信息
		cleanProductPrice(bean);
		// 清理送货费
		cleanDeliverCharge(bean);		
	}
	
	/**
	 * 
	 * 清理代收贷款
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 * @param bean
	 */
	private void cleanCod(WaybillPanelVo bean) {
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款金额
		bean.setCodFee(BigDecimal.ZERO);
		// 代收货款编码
		bean.setCodCode("");
		// 代收货款ID
		bean.setCodId("");
	}
	
	/**
	 * 
	 * 清空保险费相关
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void cleanInsurance(WaybillPanelVo bean) {
		// 保险声明值最大值
		bean.setMaxInsuranceAmount(BigDecimal.ZERO);
		// 保险费率
		bean.setInsuranceRate(BigDecimal.ZERO);
		// 保险手续费
		bean.setInsuranceFee(BigDecimal.ZERO);
		// 保险费ID
		bean.setInsuranceId("");
		// 保险费CODE
		bean.setInsuranceCode("");
	}
	
	/**
	 * 
	 * 清理产品价格相关
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void cleanProductPrice(WaybillPanelVo bean) {
		// 设置运费价格ID
		bean.setTransportFeeId("");
		// 设置运费价格CODE
		bean.setTransportFeeCode("");
		// 设置运费
		if (!bean.getIsWholeVehicle()) {
			bean.setTransportFee(BigDecimal.ZERO);
			// 画布公布价运费
			bean.setTransportFeeCanvas(BigDecimal.ZERO.toString());
		}
		// 设置费率
		bean.setUnitPrice(BigDecimal.ZERO);

		// 计费重量
		bean.setBillWeight(BigDecimal.ZERO);
	}
	
	/**
	 * 
	 * @description:初始化VO
	 * @date 2013-04-01
	 * @author wqj
	 */
	private void initVo(WaybillPanelVo bean) {
		
		//单选框与复选框数据初始化
		bean.setPickupToDoor(false);//是否上门接货
		bean.setPickupCentralized(false);//是否上门接货
		bean.setPreciousGoods(false);//是否贵重物品
		bean.setSpecialShapedGoods(false);//是否异形物品
		bean.setIsPdaBill(false);//是否为PDA运单
		bean.setCarDirectDelivery(false);//大车直送
		bean.setSecretPrepaid(false);//预付费保密
		bean.setIsWholeVehicle(false);//是否整车
		bean.setIsPassDept(false);//是否经过营业部		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if(dept.getCode()!=null){				
			SaleDepartmentEntity org = waybillService.querySaleDeptByCode(dept.getCode());			
			if(org!=null){
				//始发网点 create time
				if(org.getOpeningDate()!=null){
					bean.setReceiveOrgCreateTime(org.getOpeningDate());
				}
				if(org.getName()!=null){
					bean.setReceiveOrgName(org.getName());
				}
			}
		}		
		bean.setReceiveOrgCreateTime(dept.getBeginTime());//收货部门开业时间		
		bean.setCreateOrgCode(dept.getCode());//开单组织
		bean.setModifyOrgCode(dept.getCode());//更新组织
		bean.setCreateUserCode(user.getEmployee().getEmpCode());//创建人
		bean.setModifyUserCode(user.getEmployee().getEmpCode());//修改人
		//bean.setLongOrShort(PricingConstants.LONG_OR_SHORT_L);//长短途
		bean.setBillTime(new Date());//开单时间		

		// 增值服务面板
		bean.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
		//bean.setCodAmount(BigDecimal.ZERO);// 代收货款
		bean.setPackageFee(BigDecimal.ZERO);// 包装费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
		bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);//计算后的送货费
		
		
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
		
		bean.setOtherFeeCanvas(ZEROSTR);// 其他费用
		bean.setPromotionsFeeCanvas(ZEROSTR);// 优惠合计
		bean.setTotalFeeCanvas(ZEROSTR);// 总费用
		
		bean.setStandCharge(BigDecimal.ZERO);//打木架费用
		bean.setBoxCharge(BigDecimal.ZERO);//打木箱费用
	}
	
	/**
	 * 
	 * 验证空运货物类型
	 * 
	 * @author wqj
	 * @date 2013-03-29
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
	 * @param bean
	 */
	private void validateTransporTypeDTDSelectedOwn(WaybillPanelVo bean){
		if(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.TransportTypeIsDTDNOTSelectedOwn"));
		}
	}
}
	/**
	 * 
	 * 初始化保险金额
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午05:40:55
	 */
	private void initInsuranceAmount(WaybillPanelVo bean)
	{
		ValueAddDto dto = new ValueAddDto();
		List<ValueAddDto> list = null;
		try {
			/**
			 * 优化处理 如果从主面板中获取的的保价费不0或者空  就不需要再与数据库交互
			 * @author Foss-278328-hujinyang
			 * @date 20151214
			 */
//			list = waybillService.queryValueAddPriceList(getQueryParam());
//		} catch (Exception e) {
			//如果没有找到数据 ,也不能抛错
			dto.setMaxFee(BigDecimal.ZERO);
			dto.setMinFee(BigDecimal.ZERO);
			dto.setActualFeeRate(BigDecimal.ZERO);
			dto.setCaculateFee(BigDecimal.ZERO);
			dto.setDefaultBF(BigDecimal.ZERO);
			list = new ArrayList<ValueAddDto>();
//			list.add(dto);
		}catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		}

		if(CollectionUtils.isNotEmpty(list)){
			dto = list.get(0);
			bean.setMaxInsuranceAmount(Common.nullBigDecimalToZero(dto.getMaxFee()));
			bean.setMixInsuranceAmount(Common.nullBigDecimalToZero(dto.getMinFee()));
			
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto.getActualFeeRate());
			//将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			feeRate = feeRate.multiply(permillage);
			bean.setInsuranceRate(feeRate);
			bean.setInsuranceFee(dto.getCaculateFee());
			bean.setInsuranceId(dto.getId());
			bean.setInsuranceCode(dto.getPriceEntityCode());
			bean.setInsuranceAmount(Common.nullBigDecimalToZero(dto.getDefaultBF()));
			bean.setInsuranceAmountCanvas(Common.nullBigDecimalToZero(dto.getDefaultBF()).toString());
		}
	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
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
	 * 设置下拉框默认值
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setCombDefualtValue(WaybillPanelVo bean) {
		setProductCode(bean);//设置产品默认值
		setReceiveMethod(bean);//设置提货方式默认值
		setBillingType(bean);//设置计费方式默认值
		setBombPaidMethod(bean);
	}
	
	/**
	 * 
	 * 设置计费类型默认值
	 * 
	 * @author wqj
	 * @date 2013-03-29
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
	 * 设置提货方式默认值
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 */
	private void setReceiveMethod(WaybillPanelVo bean) {
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			if (WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())) {
				bean.setReceiveMethod(vo);
			}
		}
	}
	
	/**
	 * 初始化付款方式
	 * @author wqj
	 * @date 2013-03-29
	 * @see
	 */
	private void initCombPaymentMode() {
		java.util.List<DataDictionaryValueEntity> list = waybillService
				.queryPaymentMode();
		combPaymentModeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
				&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode()))
			{
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				combPaymentModeModel.addElement(vo);
			}
		}
		this.combPaidMethod.setModel(combPaymentModeModel);				
	}
	
	/**
	 * 
	 * 初始化计费类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 上午11:29:46
	 */
	private void initCombBillingType() {
		java.util.List<DataDictionaryValueEntity> list = waybillService.queryBillingWay();
		combBillingType = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			combBillingType.addElement(vo);
		}
		this.billingType.setModel(combBillingType);
	}
	
	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void calculateValueAdd(WaybillPanelVo bean) {
		// 保价费
		getInsurance(bean);
		// 代收货款手续费
		getCod(bean);
		// 送货费
		getDeliveryFee(bean);
		// 计算装卸费
		calculateServiceFee(bean);
	}
	
	
	/**
	 * 
	 * 计算装卸费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void calculateServiceFee(WaybillPanelVo bean) {

		// 设置是否允许修改装卸费
		boolean serviceChargeEnabled = setServiceChargeState(bean);

		// 装卸费无效
		if (!serviceChargeEnabled) {
			return;
		}

		validataServiceFee(bean);

		// 获取装卸费
		BigDecimal serivceFee = bean.getServiceFee();
		if (serivceFee != null && serivceFee.longValue() != 0) {
			// 输入装卸费之后的公布价运费 = 纯运费+装卸费
			// 输入装卸费之后的费率 = （纯运费+装卸费）/重量或体积

			// 获取运费
			BigDecimal transportFee = bean.getTransportFee();
			// 获取费率
			BigDecimal unitPrice = bean.getUnitPrice();
			// 运费 = 运费+装卸费
			transportFee = transportFee.add(bean.getServiceFee());
			// 设置新的运费
			bean.setTransportFee(transportFee);
			// 费率 = 最新运费（运费+装卸费）/重量或体积
			unitPrice = transportFee.divide(getWeightOrVolume(bean), 2, BigDecimal.ROUND_HALF_DOWN);
			// 设置新的费率
			bean.setUnitPrice(unitPrice);
		}
	}
	
	/**
	 * 
	 * 验证是否可以开装卸费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void validataServiceFee(WaybillPanelVo bean) {
		BigDecimal serivceFee = bean.getServiceFee();

		if (serivceFee == null || serivceFee.longValue() == 0) {
			return;
		}
		BigDecimal transportFee = bean.getTransportFee();

		BigDecimal serviceFee2 = transportFee.multiply(getServiceFeeRate(bean));
		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFee"));
		}
	}
	
	/**
	 * 
	 * 获取装卸费费率
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private BigDecimal getServiceFeeRate(WaybillPanelVo bean) {
		BigDecimal serviceFeeRate = bean.getServiceFeeRate();

		if (serviceFeeRate == null) {
			ProductEntityVo productVo = bean.getProductCode();
			// 调用接口读取装卸费费率
			ServiceFeeRateDto dto = null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			}else
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
			}
			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFeeCalculate="));
			} else {
				// 判断是否存在装卸费费率
				if (dto.getServiceFeeRate() == null) {
					throw new WaybillValidateException(dto.getMessage());
				} else {
					serviceFeeRate = dto.getServiceFeeRate();
				}
			}
		}
		return serviceFeeRate;
	}
	
	/**
	 * 
	 * 获取重量或者体积
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private BigDecimal getWeightOrVolume(WaybillPanelVo bean) {
		// 判断是否按重量计费
		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {
			return bean.getGoodsWeightTotal();
		} else {
			// 按体积计费
			ProductEntityVo productVo = bean.getProductCode();
			// 判断是否空运
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				// 是空运则返回计费重量
				return bean.getBillWeight();
			} else {
				// 是汽运则返回体积
				return bean.getGoodsVolumeTotal();
			}
		}
	}
	
	/**
	 * 设置是否允许修改装卸费
	 * 
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 */
	private boolean setServiceChargeState(WaybillPanelVo bean) {
		// 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
		// （只限制配载类型为专线的，包括月结）；
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10.如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
		 */
		boolean serviceChargeEnabled = true;
		// 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
		serviceChargeEnabled = lowestServiceFee(bean);
		// 2012年12月1日 (以后)开业的部门不能开装卸费
		// 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		// 月发越送 == 月结
		if (serviceChargeEnabled) {
			serviceChargeEnabled = channelServiceFee(bean);
		}
//		this.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
		if (!serviceChargeEnabled) {
			bean.setServiceFee(BigDecimal.ZERO);
		}
		return serviceChargeEnabled;
	}
	
	/**
	 * 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 
	 * @param bean
	 * @return
	 */
	private boolean channelServiceFee(WaybillPanelVo bean) {

		String channel = bean.getOrderChannel();
		// 阿里巴巴
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(channel) || WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT.equals(channel)) {
			return false;
		}
		// 月发月结
		if (bean.getChargeMode() != null) {
			if (bean.getChargeMode()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
	 * 
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 * @return
	 */
	private boolean lowestServiceFee(WaybillPanelVo bean) {
		BigDecimal minTransportFee = bean.getMinTransportFee();
		BigDecimal transportFee = bean.getTransportFee();
		boolean serviceChargeEnabled = true;
		if (minTransportFee != null) {

			// 最低一票 装卸费=0
			if (transportFee.compareTo(minTransportFee) == 0) {
				bean.setServiceFee(BigDecimal.ZERO);
				serviceChargeEnabled = false;
			}
		}

		return serviceChargeEnabled;
	}
	
	/**
	 * 
	 * 计算送货费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void getDeliveryFee(WaybillPanelVo bean) {

		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		java.util.List<ValueAddDto> list = null;

		//提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费 
			setDeliverFee(list, bean, true , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费
			setDeliverFee(list, bean, true , false);
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 设置上楼费
			setDeliverFee(list, bean, false , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费
			setDeliverFee(list, bean, true , false);
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 设置进仓费
			setDeliverFee(list, bean, false , true);
			// 超远派送费
			veryFarDeliveryFee(bean);
		}
	}
	
	/**
	 * 
	 * 超远派送费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void veryFarDeliveryFee(WaybillPanelVo bean) {
		if (bean.getKilometer() != null) {
			java.util.List<ValueAddDto> list = waybillService.queryValueAddPriceList(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			// 设置超远派送费
			setDeliverFee(list, bean, false , false);
		}
	}
	
	/**
	 * 
	 * 获取超远派送费查询参数
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 * @param bean
	 * @param valueAddType
	 * @param cost
	 * @param subType
	 * @return
	 */
	private QueryBillCacilateValueAddDto getVeryFarQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(valueAddType);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		// 公里数
		queryDto.setKilom(bean.getKilometer());
		return queryDto;
	}
	
	/**
	 * 
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * @param flag = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge = true 表示送货费中的送货进仓费
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setDeliverFee(java.util.List<ValueAddDto> list, WaybillPanelVo bean, Boolean flag , Boolean isDeliverStorge) {
		if (list != null && !list.isEmpty()) {

			ValueAddDto dto = list.get(0);
			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);
			// 费用编码
			deliver.setCode(dto.getPriceEntityCode());
			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(bean.getWaybillNo());
			//提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				//费用名称
				deliver.setName(dto.getSubTypeName());
			}else
			{
				//费用名称
				deliver.setName(dto.getPriceEntityName());
			}
			// 送货费合计
			BigDecimal chargeTotal = BigDecimal.ZERO;
			// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
			chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			bean.setDeliveryGoodsFee(chargeTotal);
			// 画布-送货费
			bean.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			// 计算的送货费
			bean.setCalculateDeliveryGoodsFee(chargeTotal);
			// 获取派送费集合
			java.util.List<DeliverChargeEntity> delivetList = bean.getDeliverList();
			if (delivetList == null) {
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			// 将新的派送费添加到派送费集合
			delivetList.add(deliver);
			// 将派送费集合进行更新
			bean.setDeliverList(delivetList);
			// 此标识用来标记是否送货费,如果查询出来是送货费，则将送货费的最大上限设置
			if (flag) {
				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
			}
			// 费用折扣
//			Common.setFavorableDiscount(dto.getDiscountPrograms(), this, bean);
		}
	}
	
	/**
	 * 
	 * 清理送货费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void cleanDeliverCharge(WaybillPanelVo bean) {
		//画布-送货费
		bean.setDeliveryGoodsFeeCanvas("0");
		//送货费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		//送货费集合
		bean.setDeliverList(null);
	}
	
	/**
	 * 
	 * 获取代收货款费率和费用
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void getCod(WaybillPanelVo bean) {
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);

		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();
		//代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(bean);
		if (codCollection != null) {
			priceEntities.add(codCollection);//代收货款手续费
		}
		productPriceDtoCollection.setPriceEntities(priceEntities);
		
		//最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(bean.getLastLoadOrgCode());
		
		//统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService.queryGuiBillPrice(productPriceDtoCollection);

		//如果返回的价格为空，抛出业务异常
		if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		}
		
		//获取代收货款手续费
		GuiResultBillCalculateDto codCollection2 = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_HK, null);

		//设置代收货款手续费
		setCodCollection(bean, codCollection2);
		
	}
	
	/**
	 * 
	 * 设置代收货款 如果有代收货款但却找不到价格，提示查询不到价格
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void setCodCollection(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0) {
			setCod(bean, dto);
		}
	}
	
	/**
	 * 
	 * 设置代收货款
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void setCod(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		if (dto == null) {
			// 代收货款费率
			bean.setCodRate(BigDecimal.ZERO);
			// 代收货款金额
			bean.setCodFee(BigDecimal.ZERO);
			// 代收货款编码
			bean.setCodCode("");
			// 代收货款ID
			bean.setCodId("");
		} else {
			//将代收货款费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto.getActualFeeRate());
			feeRate = feeRate.multiply(permillage);
			// 代收货款费率
			bean.setCodRate(feeRate);

			BigDecimal codFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 代收货款金额
			bean.setCodFee(codFee);

			// 代收货款编码
			bean.setCodCode(dto.getPriceEntryCode());
			// 代收货款ID
			bean.setCodId(dto.getId());
		}
	}
	
	/**
	 * 
	 * 获取GuiResultBillCalculateDto
	 * 
	 * @param dtos
	 * @param valueAddType
	 * @param subType
	 * @return
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode()) && subType.equals(guiResultBillCalculateDto.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	/**
	 * 
	 * 获得产品价格详细信息
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(WaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor()) {
			return Common.getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return Common.getQueryParamCollection(bean, FossConstants.NO);
		}

	}
	
	/**
	 * 
	 * 获取代收货款费率
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(WaybillPanelVo bean) {
		
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0) {
			
			return getQueryParamByCon(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), null);
		}else{
			return null;
		}
		

	}
	
	/**
	 * 
	 * 根据条件获取查询参数
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private GuiQueryBillCalculateSubDto getQueryParamByCon(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}	
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private QueryBillCacilateValueAddDto getQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(valueAddType);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		return queryDto;
	}
	
	/**
	 * 
	 * 获取保价费以及最高保额
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void getInsurance(WaybillPanelVo bean) {
		ValueAddDto dto = new ValueAddDto();//创建对象
		// 查询保价费
		java.util.List<ValueAddDto> list = waybillService.queryValueAddPriceList(getInsuranceParam(bean));

		if (list != null && !list.isEmpty()) {
			dto = list.get(0);
			setInsurance(bean, dto);//设置保价费
			// 设置折扣优惠
//			Common.setFavorableDiscount(dto.getDiscountPrograms(), this, bean);
		} else {
			//清空保价费
			setInsurance(bean, null);
		}
	}
	
	/**
	 * 
	 * 设置保险费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setInsurance(WaybillPanelVo bean, ValueAddDto dto) {
		if (dto == null) {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(BigDecimal.ZERO);
			// 保险费率
			bean.setInsuranceRate(BigDecimal.ZERO);
			// 保险手续费
			bean.setInsuranceFee(BigDecimal.ZERO);
			// 保险费ID
			bean.setInsuranceId("");
			// 保险费CODE
			bean.setInsuranceCode("");
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullInsuranceFee"));
		} else {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(dto.getMaxFee());
			//将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto.getActualFeeRate());
			feeRate = feeRate.multiply(permillage);
			// 保险费率
			bean.setInsuranceRate(feeRate);
			BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 保险手续费
			bean.setInsuranceFee(insuranceFee);
			// 保险费ID
			bean.setInsuranceId(dto.getId());
			// 保险费CODE
			bean.setInsuranceCode(dto.getPriceEntityCode());
		}

	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private QueryBillCacilateValueAddDto getInsuranceParam(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		return queryDto;
	}
	
	/**
	 * 
	 * 计算公布价运费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private ProductPriceDto calculateTransportFee(WaybillPanelVo bean) {
		// 查询产品价格
		ProductPriceDto dto = getProductPriceDto(bean);
		if (dto == null) {
//			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
			return dto;
		} else {
			return dto;
		}

	}
	
	/**
	 * 
	 * 获得产品价格详细信息
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private ProductPriceDto getProductPriceDto(WaybillPanelVo bean) {
		java.util.List<ProductPriceDto> productPrice = null;
		// 如果是集中接送货或者上门接货怎么优先读取上门接货的价格
		if ((bean.getPickupToDoor()!=null && bean.getPickupToDoor()) || (bean.getPickupCentralized()!=null && bean.getPickupCentralized())) {
			productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.YES));
//			this.incrementPanel.getTxtPickUpCharge().setEnabled(false);
			//查询不到上门接货的价格，则读取普通的价格
			if (productPrice == null) {
				productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO));
//				this.incrementPanel.getTxtPickUpCharge().setEnabled(true);
			}
		} else {
			productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO));
		}

		ProductPriceDto dto = null;
		if (productPrice == null || productPrice.isEmpty()) {
//			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
			
			return null;
		} else {
			for (ProductPriceDto fee : productPrice) {
				if (fee.getCaculateFee() == null) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
				}
				BigDecimal transportFee = fee.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
				// 设置运费价格ID
				bean.setTransportFeeId(fee.getId());
				// 设置运费价格CODE
				bean.setTransportFeeCode(fee.getPriceEntityCode());
				// 设置运费
				bean.setTransportFee(transportFee);
				// 设置费率
				bean.setUnitPrice(fee.getActualFeeRate());
				// 设置计费类型（重量计费或者体积计费）
				setBillingWay(fee.getCaculateType(), bean);

				// 设置计费重量
				setBillWeight(bean, fee);

				// 画布公布价运费
				bean.setTransportFeeCanvas(fee.getCaculateFee().toString());
				dto = fee;

//				// 设置折扣优惠
//				Common.setFavorableDiscount(dto.getDiscountPrograms(), this, bean);
//
//				// 设置折扣值
//				setDiscount(dto.getDiscountPrograms(), bean);

			}
		}
		return dto;
	}

	
	/**
	 * 
	 * 设置计费重量
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setBillWeight(WaybillPanelVo bean, ProductPriceDto fee) {
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {//精准空运

			if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {//按重量计费
				bean.setBillWeight(bean.getGoodsWeightTotal()); //设置计费重量
			} else {
				// 计费重量
				if (fee.getVolumeWeight() == null) {
					bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
				} else {
					bean.setBillWeight(fee.getVolumeWeight()); //设置计费重量
				}
			}
		} else {
			bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
		}

	}
	

	
	/**
	 * 
	 * 设置计费类型
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setBillingWay(String billingWay, WaybillPanelVo bean) {
		//DefaultComboBoxModel combBillingType = (DefaultComboBoxModel) this.getCombBillingType();

		int size = combBillingType.getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combBillingType.getElementAt(i);
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_WEIGHT.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}

			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_VOLUME.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}
		}
	}
	
	/**
	 * 初始化下拉框
	 * @author 025000-FOSS-wqj
	 * @date 2013-03-27
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
		// 计费类型
		initCombBillingType();
		// 初始化付款方式
		initCombPaymentMode();
		//初始化空运类型
		initCombGoodsType();
	}
	
	/**
	 * 
	 * 初始化空运货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 下午04:29:41
	 */
	private void initCombGoodsType() {
		List<GoodsTypeEntity> list = waybillService.findGoodsTypeByCondiction();
		combGoodsTypeModel = new DefaultComboBoxModel();
		for (GoodsTypeEntity goodsType : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(goodsType, vo);
			combGoodsTypeModel.addElement(vo);
		}
		this.getCombGoodsType().setModel(combGoodsTypeModel);
	}

	/**
	 * 产品类型
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initCombProductType()
	{
		//营业部开单
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		productTypeModel = new DefaultComboBoxModel();
		//根据当前用户所在部门查询部门所属产品
		setProductTypeModel(dept.getCode());
		this.getCombProductType().setModel(productTypeModel);
	}
	
	/**
	 * 
	 * 设置产品到数据模型
	 * @author wqj
	 * @date 2013-03-28
	 */
	@SuppressWarnings("unchecked")
	public void setProductTypeModel(String deptCode){
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(deptCode, WaybillConstants.TYPE_OF_CARGO, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		ProductEntityVo vo = null;
		for (ProductEntity product : list) {
			if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())){
				vo = new ProductEntityVo();
				//将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
	}
	
	/**
	 * 提货方式
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initCombPickMode()
	{
		java.util.List<DataDictionaryValueEntity> list = waybillService
				.queryPickUpGoodsHighWays();
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		pikcModeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
		}
		this.getCombPickMode().setModel(pikcModeModel);
	}
	
	/**
	 * 航班类型
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initFlightNumberType()
	{
		java.util.List<DataDictionaryValueEntity> list = waybillService
				.queryPredictFlight();
		flightNumberType = new DefaultComboBoxModel();
		flightNumberType.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			flightNumberType.addElement(vo);
		}
		this.getCombFlightNumberType().setModel(flightNumberType);
	}
	
	/**
	 * 合票方式
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initFreightMethod()
	{
		java.util.List<DataDictionaryValueEntity> list = waybillService
				.queryFreightMethod();
		freightMethod = new DefaultComboBoxModel();
		freightMethod.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			freightMethod.addElement(vo);
		}
		this.getCombFreightMethod().setModel(freightMethod);
	}
	
	/**
	 * 
	 * @description:对按钮、文本框等控件进行绑定
	 * @date 2013-03-28
	 * @author wqj
	 */
	private void bind() {
		//获取光标绑定
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		waybillBinder = BindingFactory.getIntsance().moderateBind(
				WaybillPanelVo.class, this, new WaybillDescriptor(), true);

		waybillBinder.addValidationListener(new WaybillValidationListner());
		listener = new CalculateBindingListener(this);
		waybillBinder.addBindingListener(listener);		
	}
	
	/**
	 * 
	 * 集中开单控件以及数据初始化
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void focusWaybillInit(WaybillPanelVo bean)
	{
		if(WaybillConstants.WAYBILL_FOCUS.equals(waybillType))
		{
			//集中开单
			bean.setPickupCentralized(true);
			//集中开单隐藏整车报价按钮
			this.btnVehiclePrice.setEnabled(false);
			String receiveOrgCode = (String)SessionContext.get(WaybillConstants.FOCUS_DEPT_CODE);
			String receiveOrgName = (String)SessionContext.get(WaybillConstants.FOCUS_DEPT_NAME);
			if(StringUtils.isNotEmpty(receiveOrgCode) && StringUtils.isNotEmpty(receiveOrgName))
			{
				bean.setReceiveOrgCode(receiveOrgCode);//收货部门编码
				bean.setReceiveOrgName(receiveOrgName);//收货部门名称
				//根据部门查询产品
				setProductTypeModel(receiveOrgCode);
				//设置产品默认值
				setProductCode(bean);
			}
		}else
		{
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			//非集中开单
			bean.setPickupCentralized(false);
			bean.setReceiveOrgCode(dept.getCode());//收货部门
			bean.setReceiveOrgName(dept.getName());//收货部门
		}
		//运输性质获取光标
		combProductType.requestFocus();
	}
	
	/**
	 *是否符合精准大票校验
	 * 
	 */
	public void validateIsBigGoods(WaybillPanelVo bean) {
		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG
				.equals(bean.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG
						.equals(bean.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG
						.equals(bean.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG
						.equals(bean.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR
						.equals(bean.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD
						.equals(bean.getProductCode().getCode())){
			/**
			 * DMANA-7725  
			 * @author YANGKANG
			 * 过滤新产品门到门和场到场  不做超重货或者超重货服务费的校验
			 */
			if(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(bean.getProductCode().getCode())
					||ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
				
					BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
					BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
					
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) <= 0){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.GoodsWeight"));
					}
				
			}else{
				Boolean falg = CommonUtils.isHeavyWeight(bean);
				if (falg) {
					throw new WaybillValidateException("不符合开精准大票条件！");
				}else{										
						BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
						BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
						if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) <= 0){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.GoodsWeight"));											
					}
				}
			}
			
		}			
	}
	
	
	/**
	 * 
	 * 设置运输性质默认值
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	public void setProductCode(WaybillPanelVo bean) {
		for (int i = 0; i < productTypeModel.getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) productTypeModel.getElementAt(i);
			//默认设置为精准卡航
			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(vo.getCode())) {
				bean.setProductCode(vo);
			}
		}
	}
	
	public void setBombPaidMethod(WaybillPanelVo bean){
		for (int i = 0; i < combPaymentModeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combPaymentModeModel.getElementAt(i);
			//默认设置为现金
			if (WaybillConstants.CASH_PAYMENT.equals(
					vo.getValueCode())) {
				bean.setPaidMethod(vo);
			}
		}
	}
	
	class MyActionListener implements ActionListener {
		CalculateCostsDialog dialog;
		public MyActionListener(CalculateCostsDialog dialog){
			this.dialog=dialog;
		}
		
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
		}
	}
	
	public CalculateVehicleDialog getCalculateVehicleDialog() {
		return calculateVehicleDialog;
	}
	
	public void setCalculateVehicleDialog(CalculateVehicleDialog calculateVehicleDialog) {
		this.calculateVehicleDialog = calculateVehicleDialog;
	}
}
