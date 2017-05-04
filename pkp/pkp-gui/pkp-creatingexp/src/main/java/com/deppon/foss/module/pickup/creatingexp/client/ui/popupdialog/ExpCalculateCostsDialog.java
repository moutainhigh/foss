package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocumentAbs;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillValidationListner;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.editui.CanvasContentPanel;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpShowCalculateDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpCalculateBindingListener;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@ContainerSeq(seq = 1)
public class ExpCalculateCostsDialog extends JFrame {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -641058732241249707L;

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpCalculateCostsDialog.class);

	private Map<String, IBinder<ExpWaybillPanelVo>> bindersMap = new HashMap<String, IBinder<ExpWaybillPanelVo>>();

	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 营业部服务类
	SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory.getSalesDepartmentService();

	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);

	/**
	 * fieldName
	 */
	private static final String FIELDNAME = "fieldName";

	private static final String ZEROSTR = "0";

	private static final int NUM_682 = 682;

	private static final int NUM_441 = 441;

	private static final int NUM_144 = 144;

	private static final int NUM_255 = 255;

	private static final int TEN = 10;
	
	/**
	 * 签收单返单费用
	 */
	private BigDecimal returnBillFee = BigDecimal.ZERO;
	
	/**
	 * @description：保存绑定对象
	 * @date 2013-03-28
	 * @author wqj
	 */
	public void registToRespository() {
		bindersMap.put("waybillBinder", waybillBinder);
	}

	// 收货部门
	@Bind("receiveOrgName")
	@FocusSeq(seq = 1)
	private JTextFieldValidate textReceiveOrgName;

	// 查询收货部门放大镜
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = ExpQueryReceiveOrgAction.class)
	@FocusSeq(seq = 2)
	private JButton btnQueryReceive;

	// 目的站
	@Bind("targetOrgCode")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "目的站") })
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtDestination;

	@Bind("goodsQtyTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总件数") })
	@FocusSeq(seq = 11)
	private JTextFieldValidate txtTotalPieces;

	@Bind("goodsWeightTotal")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总重量") })
	@FocusSeq(seq = 12)
	private JTextFieldValidate txtWeight;

	@Bind("goodsVolumeTotal")
	// @NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总体积") })
	@FocusSeq(seq = 13)
	private JTextFieldValidate txtVolume;

	@Bind("customerPickupOrgName")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "提货网点") })
	@FocusSeq(seq = 10)
	private JTextFieldValidate txtBranch;

	@Bind("totalFee")
	@FocusSeq(seq = 27)
	private JTextFieldValidate txtTotalFreight;

	// 运单开单类型（集中开单、营业部开单）
	private String waybillType;

	// 运输性质
	private DefaultComboBoxModel productTypeModel;

	// 运输性质
	@Bind("productCode")
	@FocusSeq(seq = 1)
	private JComboBox combProductType;

	// 提货方式
	private DefaultComboBoxModel pikcModeModel;

	// 提货方式
	@Bind("receiveMethod")
	@FocusSeq(seq = 2)
	private JComboBox combPickMode;

	// 航班类型
	private DefaultComboBoxModel flightNumberType;

	// 合票方式
	private DefaultComboBoxModel freightMethod;

	// 查询提货网点放大镜
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpShowCalculateDialogAction.class)
	@FocusSeq(seq = 4)
	private JButton btnQueryBranch;

	// 计算总运费
	@FocusSeq(seq = 28)
	private JButton btnProFreight;

	// 关闭按钮
	@FocusSeq(seq = 29)
	private JButton btnClose;

	// 绑定接口
	private IBinder<ExpWaybillPanelVo> waybillBinder;

	// 事件监听
	private ExpCalculateBindingListener listener;

	// 计费类型
	// private DefaultComboBoxModel combBi2llingType;
	/**
	 * 增值业务面板
	 */
	public IncrementPanel incrementPanel;

	/**
	 * 画布信息
	 */
	public CanvasContentPanel canvasContentPanel;

	/**
	 * 付款方式
	 */
	@Bind("paidMethod")
	@FocusSeq(seq = 17)
	private JComboBox combPaidMethod;

	private DefaultComboBoxModel combPaymentModeModel;

	/**
	 * 保价声明
	 */
	@Bind("insuranceAmount")
	@FocusSeq(seq = 9)
	private JTextFieldValidate txtInsuranceAmount;

	// 空运货物类型
	private DefaultComboBoxModel combGoodsTypeModel;

	/**
	 * 代收货款
	 */
	@Bind("codAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收货款") })
	@FocusSeq(seq = 18)
	private JTextFieldValidate txtCashOnDelivery;

	/**
	 * 公布价运费
	 */
	@Bind("transportFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "公布价运费") })
	@FocusSeq(seq = 20)
	private JTextFieldValidate txtTransportFee;

	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	@FocusSeq(seq = 21)
	private JTextFieldValidate txtInsuranceRate;

	/**
	 * 保价费
	 */
	@Bind("insuranceFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费") })
	@FocusSeq(seq = 22)
	private JTextFieldValidate txtInsuranceFee;

	/**
	 * 代收费率
	 */
	@Bind("codRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收费率") })
	@FocusSeq(seq = 25)
	private JTextFieldValidate txtCodRate;

	/**
	 * 代收手续费
	 */
	@Bind("codFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收手续费") })
	@FocusSeq(seq = 26)
	private JTextFieldValidate txtCodFee;

	// 首重
	private JTextField txtHeadWeight;

	// 续重1
	private JTextField txtAddWeightOne;

	// 续重2
	private JTextField txtAddWeightTwo;

	// 代签回单
	@Bind("returnBillType")
	@NotNull()
	@FocusSeq(seq = 27)
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代签回单") })
	private JComboBox combReturnBill;

	//返回单类型
	private DefaultComboBoxModel combReturnBillTypeModel;
	
	//签回单
	private JTextField txtSignedReturnBill;

	public JTextField getTxtSignedReturnBill() {
		return txtSignedReturnBill;
	}

	
	public void setTxtSignedReturnBill(JTextField txtSignedReturnBill) {
		this.txtSignedReturnBill = txtSignedReturnBill;
	}

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

	public DefaultComboBoxModel getCombGoodsTypeModel() {
		return combGoodsTypeModel;
	}

	public void setCombGoodsTypeModel(DefaultComboBoxModel combGoodsTypeModel) {
		this.combGoodsTypeModel = combGoodsTypeModel;
	}

	public HashMap<String, IBinder<ExpWaybillPanelVo>> getBindersMap() {
		return (HashMap<String, IBinder<ExpWaybillPanelVo>>) bindersMap;
	}

	public void setBindersMap(Map<String, IBinder<ExpWaybillPanelVo>> bindersMap) {
		this.bindersMap = bindersMap;
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

	// public DefaultComboBoxModel getCombBillingType() {
	// return combBillingType;
	// }
	// public void setCombBillingType(DefaultComboBoxModel combBillingType) {
	// this.combBillingType = combBillingType;
	// }
	public JTextFieldValidate getTxtInsuranceAmount() {
		return txtInsuranceAmount;
	}

	public void setTxtInsuranceAmount(JTextFieldValidate txtInsuranceAmount) {
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

	public JTextFieldValidate getTxtDestination() {
		return txtDestination;
	}

	public void setTxtDestination(JTextFieldValidate txtDestination) {
		this.txtDestination = txtDestination;
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

	public IBinder<ExpWaybillPanelVo> getWaybillBinder() {
		return waybillBinder;
	}

	public void setWaybillBinder(IBinder<ExpWaybillPanelVo> waybillBinder) {
		this.waybillBinder = waybillBinder;
	}

	public ExpCalculateBindingListener getListener() {
		return listener;
	}

	public void setListener(ExpCalculateBindingListener listener) {
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

	public DefaultComboBoxModel getFreightMethod() {
		return freightMethod;
	}

	public void setFreightMethod(DefaultComboBoxModel freightMethod) {
		this.freightMethod = freightMethod;
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ExpCalculateCostsDialog dialog = new ExpCalculateCostsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ExpCalculateCostsDialog() {
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_682, NUM_441);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { RowSpec.decode("default:grow"), RowSpec.decode("top:default:grow"),
						RowSpec.decode("top:default:grow"), }));
		{
			JPanel panel = new JPanel();
			panel.setToolTipText("4343");
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.waybill"),
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(NumberConstants.NUMBER_30, NUM_144, NUM_255)));
			getContentPane().add(panel, "1, 1");
			FormLayout fl_panel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("left:default"), ColumnSpec.decode("max(50dlu;default)"), ColumnSpec.decode("default:grow"),
					ColumnSpec.decode("10dlu"), ColumnSpec.decode("max(35dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					ColumnSpec.decode("10dlu"), ColumnSpec.decode("max(35dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(60dlu;default):grow"),
					FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("20dlu:grow"), RowSpec.decode("20dlu"), RowSpec.decode("20dlu"), RowSpec.decode("20dlu:grow"), });
			panel.setLayout(fl_panel);
			{
				// 收货部门
				JLabel labelReceiveOrgName = new JLabel(i18n.get("foss.gui.creating.basicPanel.salesDepartment.label") + "：");
				panel.add(labelReceiveOrgName, "2, 1");
			}
			{
				{
					textReceiveOrgName = new JTextFieldValidate();
					panel.add(textReceiveOrgName, "3, 1");
					textReceiveOrgName.setColumns(TEN);
				}
			}
			btnQueryReceive = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
			// btnQueryReceive.setMargin(new Insets(-2, 1, -2, 1));
			btnQueryReceive.setBorderPainted(false);
			btnQueryReceive.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

				}
			});
			panel.add(btnQueryReceive, "5, 1");
			{
				// 运输性质
				JLabel label = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label") + "：");
				panel.add(label, "2, 3");
			}
			{
				combProductType = new JComboBox();
				panel.add(combProductType, "3, 3");
			}
			{
				// 提货方式
				JLabel lblNewLabel_1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickMode.label") + "：");
				panel.add(lblNewLabel_1, "5, 3");
			}
			{
				combPickMode = new JComboBox();
				panel.add(combPickMode, "7, 3");
			}
			{
				// 目的站
				JLabel lblNewLabel_2 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.destination.label") + "：");
				panel.add(lblNewLabel_2, "9, 3");
			}
			{
				txtDestination = new JTextFieldValidate();
				panel.add(txtDestination, "11, 3");
				txtDestination.setColumns(TEN);
				txtDestination.setEditable(true);
			}
			{
				// 付款方式
				JLabel label_1 = new JLabel(i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.paymentMode.label") + "：");
				panel.add(label_1, "2, 4");
			}
			{
				combPaidMethod = new JComboBox();
				panel.add(combPaidMethod, "3, 4, fill, default");
			}
			{
				// 保价声明
				JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceAmount.label") + "：");
				panel.add(label, "5, 4, left, default");
			}
			{
				txtInsuranceAmount = new JTextFieldValidate();
				txtInsuranceAmount.setText("0");
				panel.add(txtInsuranceAmount, "7, 4, fill, default");
				txtInsuranceAmount.setColumns(TEN);
				txtInsuranceAmount.setDocument(new NumberDocumentAbs(TEN));
			}
			{
				// 提货网点
				JLabel lblNewLabel_3 = new JLabel(i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.pickBranch.label") + "：");
				panel.add(lblNewLabel_3, "9, 4, left, default");
			}
			{
				txtBranch = new JTextFieldValidate();
				panel.add(txtBranch, "11, 4, fill, default");
				txtBranch.setColumns(TEN);
				txtBranch.setEditable(false);
			}
			{
				btnQueryBranch = new JButton();
				btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
				btnQueryBranch.setBorderPainted(false);
				btnQueryBranch.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

					}
				});
				panel.add(btnQueryBranch, "13, 3");
			}
			{
				// 总件数
				JLabel lblNewLabel_4 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalPieces.label"));
				panel.add(lblNewLabel_4, "2, 5");
			}
			{
				txtTotalPieces = new JTextFieldValidate();
				txtTotalPieces.setText("0");
				panel.add(txtTotalPieces, "3, 5");
				txtTotalPieces.setColumns(TEN);
				txtTotalPieces.setDocument(new NumberDocumentAbs(TEN));
			}
			{
				// 总重量
				JLabel label = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalWeight.label"));
				panel.add(label, "5, 5");
			}
			{
				txtWeight = new JTextFieldValidate();
				txtWeight.setText("0");
				panel.add(txtWeight, "7, 5");
				txtWeight.setColumns(TEN);
				txtWeight.setDocument(new FloatDocument(NumberConstants.NUMBER_8,2));
			}
			{
				// 总体积
				JLabel lblNewLabel_5 = new JLabel(i18n.get("foss.gui.creating.abandonedGoodsHandleUI.totalVolume.label"));
				panel.add(lblNewLabel_5, "9, 5");
			}
			{
				txtVolume = new JTextFieldValidate();
				txtVolume.setText("0");
				panel.add(txtVolume, "11, 5");
				txtVolume.setColumns(TEN);
				txtVolume.setDocument(new FloatDocument(NumberConstants.NUMBER_8,2));
			}
			{
				// 代收货款
				JLabel label_2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label") + "：");
				panel.add(label_2, "2, 6");
			}
			{
				txtCashOnDelivery = new JTextFieldValidate();
				txtCashOnDelivery.setText("0");
				panel.add(txtCashOnDelivery, "3, 6, fill, default");
				txtCashOnDelivery.setColumns(TEN);
				txtCashOnDelivery.setDocument(new NumberDocumentAbs(TEN));
			}
			{
				JLabel lblNewLabel_7 = new JLabel("代签回单：");
				panel.add(lblNewLabel_7, "5, 6, right, default");
			}
			{
				combReturnBill = new JComboBox();
				panel.add(combReturnBill, "7, 6, fill, default");
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), i18n.get("foss.gui.creating.numberPanel.calculateCostsDialog.freight"),
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(NumberConstants.NUMBER_30, NUM_144, NUM_255)));
			getContentPane().add(panel, "1, 2, fill, fill");
			panel.setLayout(new FormLayout(new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					ColumnSpec.decode("max(45dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					ColumnSpec.decode("10dlu"),
					ColumnSpec.decode("max(27dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					ColumnSpec.decode("10dlu"),
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("5dlu"),},
				new RowSpec[] {
					RowSpec.decode("20dlu"),
					RowSpec.decode("18dlu"),
					RowSpec.decode("18dlu"),
					FormFactory.DEFAULT_ROWSPEC,}));
			{
				JLabel label = new JLabel("首重：");
				panel.add(label, "2, 1");
			}
			{
				txtHeadWeight = new JTextField();
				txtHeadWeight.setEditable(false);
				panel.add(txtHeadWeight, "4, 1");
				txtHeadWeight.setText("0");
				txtHeadWeight.setColumns(TEN);
			}
			{
				JLabel lblNewLabel_6 = new JLabel("续重1：");
				panel.add(lblNewLabel_6, "6, 1");
			}
			{
				txtAddWeightOne = new JTextField();
				txtAddWeightOne.setEditable(false);
				panel.add(txtAddWeightOne, "8, 1");
				txtAddWeightOne.setText("0");
				txtAddWeightOne.setColumns(TEN);
			}
			{
				JLabel label = new JLabel("续重2：");
				panel.add(label, "10, 1");
			}
			{
				txtAddWeightTwo = new JTextField();
				txtAddWeightTwo.setEditable(false);
				panel.add(txtAddWeightTwo, "12, 1");
				txtAddWeightTwo.setText("0");
				txtAddWeightTwo.setColumns(TEN);
			}
			{
				// 保价费率
				JLabel label_5 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label") + "：");
				panel.add(label_5, "2, 2");
			}
			{
				JPanel panel_2 = new JPanel();
				panel.add(panel_2, "4, 2, fill, center");
				panel_2.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("center:max(12dlu;default)"), },
						new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));
				{
					txtInsuranceRate = new JTextFieldValidate();
					txtInsuranceRate.setText("0.0");
					txtInsuranceRate.setIsPassed(false);
					txtInsuranceRate.setIsEnable(false);
					panel_2.add(txtInsuranceRate, "1, 1");
					txtInsuranceRate.setColumns(NumberConstants.NUMBER_20);
					txtInsuranceRate.setEditable(false);
				}
				{
					JLabel lblNewLabel = new JLabel("‰");
					panel_2.add(lblNewLabel, "2, 1");
				}
			}
			{
				// 保价费
				JLabel label_6 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label") + "：");
				panel.add(label_6, "6, 2");
			}
			{
				txtInsuranceFee = new JTextFieldValidate();
				txtInsuranceFee.setText("0");
				txtInsuranceFee.setIsPassed(false);
				txtInsuranceFee.setIsEnable(false);
				panel.add(txtInsuranceFee, "8, 2, fill, default");
				txtInsuranceFee.setColumns(TEN);
				txtInsuranceFee.setEditable(false);
			}
			{
				// 公布价运费
				JLabel label_4 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.transportFee.label") + "：");
				panel.add(label_4, "10, 2");
			}
			{
				txtTransportFee = new JTextFieldValidate();
				txtTransportFee.setText("0");
				txtTransportFee.setIsPassed(false);
				txtTransportFee.setIsEnable(false);
				panel.add(txtTransportFee, "12, 2, fill, default");
				txtTransportFee.setColumns(TEN);
				txtTransportFee.setEditable(false);
			}
			{
				JLabel label_9 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codRate.label") + "：");
				panel.add(label_9, "2, 3");
			}
			{
				JPanel panel_3 = new JPanel();
				panel.add(panel_3, "4, 3, fill, center");
				panel_3.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), ColumnSpec.decode("center:max(12dlu;default)"), },
						new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));
				{
					txtCodRate = new JTextFieldValidate();
					txtCodRate.setText("0");
					txtCodRate.setIsPassed(false);
					txtCodRate.setIsEnable(false);
					txtCodRate.setEditable(false);
					panel_3.add(txtCodRate, "1, 1");
					txtCodRate.setColumns(NumberConstants.NUMBER_20);
				}
				{
					JLabel lblNewLabel = new JLabel("‰");
					panel_3.add(lblNewLabel, "2, 1");
				}
			}
			{
				JLabel label_10 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codFee.label") + "：");
				panel.add(label_10, "6, 3");
			}
			{
				txtCodFee = new JTextFieldValidate();
				txtCodFee.setText("0");
				txtCodFee.setIsEnable(false);
				txtCodFee.setIsPassed(false);
				panel.add(txtCodFee, "8, 3, fill, default");
				txtCodFee.setColumns(TEN);
				txtCodFee.setEditable(false);
			}
			{
				// 总费用
				JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.totalFee.label") + "：");
				panel.add(label, "10, 3");
			}
			{
				txtTotalFreight = new JTextFieldValidate();
				txtTotalFreight.setText("0");
				txtTotalFreight.setIsEnable(false);
				txtTotalFreight.setIsPassed(false);
				panel.add(txtTotalFreight, "12, 3");
				txtTotalFreight.setColumns(TEN);
				txtTotalFreight.setEditable(false);
			}
			{
				JLabel label = new JLabel("签回单：");
				panel.add(label, "2, 4");
			}
			{
				txtSignedReturnBill = new JTextField();
				txtSignedReturnBill.setEditable(false);
				txtSignedReturnBill.setText("0");
				panel.add(txtSignedReturnBill, "4, 4, fill, default");
				txtSignedReturnBill.setColumns(TEN);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, "1, 3, center, center");
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				btnProFreight = new JButton(i18n.get("foss.gui.creating.billingPayPanel.button.calculate.label"));
				btnProFreight.setActionCommand("OK");
				ImageIcon icon = ImageUtil.getImageIcon(this.getClass(), IconConstants.PREVIEWPNG);
				btnProFreight.setIcon(icon);
				// 当点击计算总运费调用方法
				btnProFreight.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						try {

							List<ValidationError> errorList = waybillBinder.validate();
							Boolean bool = false;
							if (errorList != null) {
								bool = ExpCommon.validateDescriptor(errorList);
							}

							// 这里我们多一步获取所有的其他费用
							if (bool) {
								ExpWaybillPanelVo bean = waybillBinder.getBean();
								// 清理所有费用相关的信息
								cleanFee(bean);
								// 验证方法
								validateUnite(bean);
								setReturnBillCharge(bean);
								// 最低一票
								BigDecimal minTransportFee = BigDecimal.ZERO;
								// 获取公布价运费
								ProductPriceDto dto = calculateTransportFee(bean);
								if (dto != null) {
									minTransportFee = dto.getMinFee();// 最低一票
									bean.setMinTransportFee(minTransportFee);
									// 计算增值服务费
									calculateValueAdd(bean);
									/**
									 * 计算总运费公共方法
									 */
									CalculateFeeTotalUtils.calculateFee(bean, true);
									// 设置公布价
									setExpressPublishPrice(bean);
								}
							}
						} catch (Exception e2) {
							if (!"".equals(e2.getMessage())) {
								MsgBox.showInfo(e2.getMessage());
							}
							e2.printStackTrace();
						}
					}
				});
				buttonPane.add(btnProFreight);
				getRootPane().setDefaultButton(btnProFreight);

			}
			{
				btnClose = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.close"));
				btnClose.setActionCommand("Cancel");
				ImageIcon icon = ImageUtil.getImageIcon(this.getClass(), IconConstants.DELETE);
				btnClose.setIcon(icon);
				// 当点击关闭按钮时调用
				btnClose.addActionListener(new MyActionListener(this));
				buttonPane.add(btnClose);
			}
		}
		// 设置模态
		//setModal(true);
		// 初始化下拉框
		initComboBox();
		// 绑定Action
		bind();
		// 获取bean
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		// 设置默认值
		setCombDefualtValue(bean);
		// 保存绑定对象
		registToRespository();
		// 初始化Vo
		initVo(bean);
		// 数据初始化
		focusWaybillInit(bean);
		// 初始化保险金额
		initInsuranceAmount(bean);
		// 最大最小值限制
		initLimitedValue(bean);
	}

	/**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	private void setReturnBillCharge(WaybillPanelVo bean) {

		List<OtherChargeVo> data = new ArrayList<OtherChargeVo>();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())
				&& !WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}

			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean.getReturnBillType().getValueCode())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = bean.getReturnBillType().getValueCode();
			}

			List<ValueAddDto> list = waybillService.queryValueAddPriceList(ExpCommon.getQueryOtherChargeParam(bean, type));
			OtherChargeVo otherVo = getReturnBillCharge(bean, list, data);
			// 添加返单费用到其他费用表格
			String chargeName = ExpCommon.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);

		} else {
			// 删除返单
			deleteReturnBill(data, bean);
		}
	}

	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		if (list != null) {
			if (!list.isEmpty()) {
				dto = list.get(0);
				// 费用编码
				vo.setCode(dto.getPriceEntityCode());
				// 名称
				vo.setChargeName(dto.getPriceEntityName());
				// 归集类别
				vo.setType(dto.getBelongToPriceEntityName());
				// 金额
				vo.setMoney(dto.getFee().toString());
				// 上限
				vo.setUpperLimit(dto.getMaxFee().toString());
				// 下限
				vo.setLowerLimit(dto.getMinFee().toString());
				// 是否可修改
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));
				
				String returnType = bean.getReturnBillType().getValueCode();
				//获得传真件返回时的金额
				if(WaybillConstants.RETURNBILLTYPE_FAX.equals(returnType) || WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(returnType)){
					returnBillFee = dto.getFee();
				}

				/**
				 * 月结
				 */
				Boolean chargeMode = bean.getChargeMode();
				if (chargeMode == null) {
					// 没有填写的情况下 作为非月结处理
					chargeMode = Boolean.FALSE;
				}
				/**
				 * 返单费用 非月结客户不允许进行编辑
				 */
				if (chargeMode) {
					vo.setIsUpdate(true);
				} else {
					vo.setIsUpdate(false);
				}

				// 是否可删除
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
				vo.setId(dto.getId());
			} else {
				// 删除返单
				deleteReturnBill(data, bean);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
			}
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
		}
		return vo;
	}

	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			ExpCommon.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
		}
	}

	/**
	 * 初始化登陆部门相关增值费的最大最小值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-27 下午4:13:45
	 */
	private void initLimitedValue(ExpWaybillPanelVo bean) {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();

		// 快递体积上限
		ConfigurationParamsEntity param2 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_VOLUME_RANGE_UP, user.getEmployee().getDepartment().getCode());
		// 快递体积下限
		ConfigurationParamsEntity param3 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_VOLUME_RANGE_LOW, user.getEmployee().getDepartment().getCode());
		// 快递保价申明价值上限
		ConfigurationParamsEntity param4 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_INSURRANCE_UP, user.getEmployee().getDepartment().getCode());
		// 快递保费
		ConfigurationParamsEntity param5 = baseDataService.queryByConfCode(ExpWaybillConstants.EXPRESS_INSURRANCE_FEE_UP, user.getEmployee().getDepartment().getCode());

		// 快递体积上限
		if (param2 != null) {
			bean.setVolumeUp(new BigDecimal(param2.getConfValue()));
		}
		// 快递体积下限
		if (param3 != null) {
			bean.setVolumeLow(new BigDecimal(param3.getConfValue()));
		}
		// 快递保价申明价值上限
		if (param4 != null) {
			bean.setMaxInsuranceAmount(new BigDecimal(param4.getConfValue()));
		}
		// 快递保价费上限
		if (param5 != null) {
			bean.setInsuranceFeeUp(new BigDecimal(param5.getConfValue()));
		}
	}

	/**
	 * 设置快递的首重、续费1、续费2费率
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-9 下午7:02:28
	 */
	private void setExpressPublishPrice(WaybillPanelVo bean) {
		IWaybillPriceExpressService willPriceExpressService = GuiceContextFactroy.getInjector().getInstance(IWaybillPriceExpressService.class);
		// 出发部门code
		String startDeptNo = bean.getCreateOrgCode();
		// 出发城市code
		String startCityCode = "";
		OrgAdministrativeInfoEntity orgInfo = baseDataService.queryOrgAdministrativeInfoEntityByCode(StringUtil.defaultIfNull(startDeptNo));
		if (null != orgInfo) {
			startCityCode = StringUtil.defaultIfNull(orgInfo.getCityCode());
		}

		//设置 签回单费用
		txtSignedReturnBill.setText(String.valueOf(returnBillFee));
		// 根据部门编码获得所属城市编码
		Injector injector = GuiceContextFactroy.getInjector();
		IOrgInfoService orgInfoService = injector.getInstance(OrgInfoService.class);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgInfoService.queryByCode(bean.getCustomerPickupOrgCode().getCode());
		// 到达城市code
		String destinationCode = orgAdministrativeInfoEntity.getCityCode();
		// 查询价格信息
		List<PublishPriceExpressEntity> priceList = willPriceExpressService.queryPublishPriceDetailOnline(startCityCode, destinationCode);
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(priceList)) {
			PublishPriceExpressEntity entity = priceList.get(0);
			// 设置首重
			txtHeadWeight.setText(String.valueOf(entity.getFirstWeight()));
			// 设置续重1
			txtAddWeightOne.setText(String.valueOf(entity.getFeeRate1()));
			// 设置续重2
			txtAddWeightTwo.setText(String.valueOf(entity.getFeeRate2()));
		}
	}

	/***
	 * 
	 * 清理所有与费用相关的信息
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void cleanFee(ExpWaybillPanelVo bean) {
		// 清理代收货款
		cleanCod(bean);
		// 清理保价
		cleanInsurance(bean);
		// 清理产品信息
		cleanProductPrice(bean);
		// 清理送货费
		cleanDeliverCharge(bean);
		// 清理其它费用
		cleanOtherCharge(bean);
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
		//bean.setMaxInsuranceAmount(BigDecimal.ZERO);
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
	private void initVo(ExpWaybillPanelVo bean) {

		// 单选框与复选框数据初始化
		bean.setPickupToDoor(false);// 是否上门接货
		bean.setPickupCentralized(false);// 是否上门接货
		bean.setPreciousGoods(false);// 是否贵重物品
		bean.setSpecialShapedGoods(false);// 是否异形物品
		bean.setIsPdaBill(false);// 是否为PDA运单
		bean.setCarDirectDelivery(false);// 大车直送
		bean.setSecretPrepaid(false);// 预付费保密
		bean.setIsWholeVehicle(false);// 是否整车
		bean.setIsPassDept(false);// 是否经过营业部
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if (dept.getCode() != null) {
			SaleDepartmentEntity org = waybillService.querySaleDeptByCode(dept.getCode());
			if (null != org) {
				// 出发城市与营业部对应关系DTO
				SalesDepartmentCityDto queryDto = new SalesDepartmentCityDto();
				// 设置查询条件
				queryDto.setSalesDepartmentCode(org.getCode());
				SalesDepartmentCityDto dto = salesDepartmentService.querySalesDepartmentCityInfo(queryDto);
				// 营业部是否可以快递接货，如果是的话 就是试点营业部
				if (dto != null) {
					dto.setTestSalesDepartment(org.getCanExpressPickupToDoor());
					bean.setCreateSalesDepartmentCityDto(dto);
				}

				// 始发网点 create time
				if (org.getOpeningDate() != null) {
					bean.setReceiveOrgCreateTime(org.getOpeningDate());
				}
			}
		}
		bean.setReceiveOrgCreateTime(dept.getBeginTime());// 收货部门开业时间
		bean.setCreateOrgCode(dept.getCode());// 开单组织
		bean.setModifyOrgCode(dept.getCode());// 更新组织
		bean.setCreateUserCode(user.getEmployee().getEmpCode());// 创建人
		bean.setModifyUserCode(user.getEmployee().getEmpCode());// 修改人
		// bean.setLongOrShort(PricingConstants.LONG_OR_SHORT_L);//长短途
		bean.setBillTime(new Date());// 开单时间

		// 增值服务面板
		bean.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
		// bean.setCodAmount(BigDecimal.ZERO);// 代收货款
		bean.setPackageFee(BigDecimal.ZERO);// 包装费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
		bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);// 计算后的送货费

		bean.setServiceFee(BigDecimal.ZERO);// 装卸费
		bean.setPickupFee(BigDecimal.ZERO);// 接货费
		bean.setOtherFee(BigDecimal.ZERO);// 其他费用

		// 计费付款面板
		bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		bean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		bean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		bean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		bean.setToPayAmount(BigDecimal.ZERO);// 到付金额
		bean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		bean.setTotalFee(BigDecimal.ZERO);
		bean.setWholeVehicleAppfee(BigDecimal.ZERO);// 整车约车报价

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

		bean.setStandCharge(BigDecimal.ZERO);// 打木架费用
		bean.setBoxCharge(BigDecimal.ZERO);// 打木箱费用

		/**
		 * 设置界面值
		 */
		// 总重量
		bean.setGoodsWeightTotal(BigDecimal.ZERO);
		// 总件数
		bean.setGoodsQtyTotal(Integer.valueOf(0));
		// 总体积
		bean.setGoodsVolumeTotal(BigDecimal.ZERO);
		// 代收货款
		bean.setCodAmount(BigDecimal.ZERO);

	}

	/**
	 * 统一验证
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 下午6:42:10
	 */
	private void validateUnite(ExpWaybillPanelVo bean) {
		/**
		 * 试点到试点能开即日退和三日退， 试点到非试点、试点到快递代理、非试点到试点、 非试点到非试点只能开单三日退， 非试点到快递代理无代收业务。
		 */

		SalesDepartmentCityDto createDto = bean.getCreateSalesDepartmentCityDto();
		SalesDepartmentCityDto endDto = bean.getTargetSalesDepartmentCityDto();
		
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
				// 获取代收货款
				BigDecimal codAmount = bean.getCodAmount();
				// BigDecimal codeAmount=new BigDecimal();
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
		
		//出发部门为空
		if(createDto==null){
			//对不起，您所在的部门不能开快递运单
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto"));
		}else if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && !FossConstants.YES.equals(createDto.getTestSalesDepartment())){
			//试点城市的 非试点营业部不能开单
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto"));
		}
		
		//提货网点为空
		if (endDto == null) {
			// 请重新选择提货网点
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateEndDto"));
		}

		// 开始试点城市 到达非试点
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			// 提货方式
			String receiveMethod = bean.getReceiveMethod().getValueCode();
			// 是否自提
			if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
				// 试点城市和非试点城市之间提货方式只能开自提
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoPickupSelf"));
			}

			// 返单类别
			DataDictionaryValueVo vo = bean.getReturnBillType();
			if (vo != null && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				// 试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			}
		}

		// 开始试点城市 到达-快递代理
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto.getCityType())) {
//			// 提货方式
//			String receiveMethod = bean.getReceiveMethod().getValueCode();
//			// 是否自提
//			if (CommonUtils.verdictPickUpSelf(receiveMethod)) {
//				// 试点城市和快递代理城市之间提货方式只能开派送
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSPCityDtoPickupSelf"));
//			}
			// 返单类别
			DataDictionaryValueVo vo = bean.getReturnBillType();
			if (vo != null && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				// 试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			}
		}

		// 开始-非试点 到达-试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())) {

			// 返单类别
			DataDictionaryValueVo vo2 = bean.getReturnBillType();
			if (vo2 != null && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())) {
				// 试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			}
		}

		// 开始-非试点 到达-非试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			// 提货方式
			String receiveMethod = bean.getReceiveMethod().getValueCode();
			// 是否自提
			if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
				// 试点城市和非试点城市之间提货方式只能开自提
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoPickupSelf"));
			}

			// 返单类别
			DataDictionaryValueVo vo2 = bean.getReturnBillType();
			if (vo2 != null && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())) {
				// 试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			}
		}

		// 开始-非试点 到达-快递代理
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto.getCityType())) {
			if (bean.getReceiveMethod() != null) {
				// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					// 非试点城市和快递代理城市之间不能发快递运单
					// 试点城市和快递代理城市之间提货方式只能开派送
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateNPCityDtoBusiness"));
				}
			}
		}
	}

	/**
	 * 
	 * 初始化保险金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午05:40:55
	 */
	private void initInsuranceAmount(ExpWaybillPanelVo bean) {
		ValueAddDto dto = new ValueAddDto();
		List<ValueAddDto> list = null;
		try {
			list = waybillService.queryValueAddPriceList(getQueryParam());
		} catch (Exception e) {
			//如果没有找到数据 ,也不能抛错
			dto.setMaxFee(BigDecimal.ZERO);
			dto.setMinFee(BigDecimal.ZERO);
			dto.setActualFeeRate(BigDecimal.ZERO);
			dto.setCaculateFee(BigDecimal.ZERO);
			dto.setDefaultBF(BigDecimal.ZERO);
			list = new ArrayList<ValueAddDto>();
			list.add(dto);
		}
		if (CollectionUtils.isNotEmpty(list)) {
			dto = list.get(0);
			//bean.setMaxInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getMaxFee()));
			bean.setMixInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getMinFee()));

			BigDecimal feeRate = ExpCommon.nullBigDecimalToZero(dto.getActualFeeRate());
			// 将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			feeRate = feeRate.multiply(permillage);
			// bean.setInsuranceRate(feeRate);
			// bean.setInsuranceFee(dto.getCaculateFee());
			bean.setInsuranceId(dto.getId());
			bean.setInsuranceCode(dto.getPriceEntityCode());
			// bean.setInsuranceAmount(ExpCommon.nullBigDecimalToZero(dto.getDefaultBF()));
			// bean.setInsuranceAmountCanvas(ExpCommon.nullBigDecimalToZero(dto.getDefaultBF()).toString());

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
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setCombDefualtValue(ExpWaybillPanelVo bean) {
		// 设置产品默认值
		setProductCode(bean);
		// 设置提货方式默认值
		setReceiveMethod(bean);
		// 设置返单类型默认值
		setReturnBillingType(bean);
		// 设置付款方式默认值
		setPaymentType(bean);
	}

	/**
	 * 设置付款方式默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-5 上午10:27:58
	 */
	private void setPaymentType(ExpWaybillPanelVo bean) {
		for (int i = 0; i < combPaymentModeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combPaymentModeModel.getElementAt(i);
			if (WaybillConstants.CASH_PAYMENT.equals(vo.getValueCode())) {
				bean.setPaidMethod(vo);
			}
		}
	}

	/**
	 * 
	 * 设置返单类型默认值
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 */
	private void setReturnBillingType(ExpWaybillPanelVo bean) {
		for (int i = 0; i < combReturnBillTypeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combReturnBillTypeModel.getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				bean.setReturnBillType(vo);
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
	private void setReceiveMethod(ExpWaybillPanelVo bean) {
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode())) {
				bean.setReceiveMethod(vo);
			}
		}
	}

	/**
	 * 初始化付款方式
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 * @see
	 */
	private void initCombPaymentMode() {
		java.util.List<DataDictionaryValueEntity> list = waybillService.queryPaymentMode();
		combPaymentModeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if (!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode()) && !WaybillConstants.CHECK.equals(dataDictionary.getValueCode())
					&& !WaybillConstants.TEMPORARY_DEBT.equals(dataDictionary.getValueCode())) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				combPaymentModeModel.addElement(vo);
			}
		}
		this.combPaidMethod.setModel(combPaymentModeModel);
	}

	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void calculateValueAdd(ExpWaybillPanelVo bean) {
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
	private void calculateServiceFee(ExpWaybillPanelVo bean) {

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
	private void validataServiceFee(ExpWaybillPanelVo bean) {
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
	private BigDecimal getServiceFeeRate(ExpWaybillPanelVo bean) {
		BigDecimal serviceFeeRate = bean.getServiceFeeRate();

		if (serviceFeeRate == null) {
			ProductEntityVo productVo = bean.getProductCode();
			// 调用接口读取装卸费费率
			ServiceFeeRateDto dto = null;
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(), ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			} else {
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(), ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
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
	private BigDecimal getWeightOrVolume(ExpWaybillPanelVo bean) {
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
	private boolean setServiceChargeState(ExpWaybillPanelVo bean) {
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
		// this.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
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
	private boolean channelServiceFee(ExpWaybillPanelVo bean) {

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
	private boolean lowestServiceFee(ExpWaybillPanelVo bean) {
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
	private void getDeliveryFee(ExpWaybillPanelVo bean) {

		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		java.util.List<ValueAddDto> list = null;

		// 提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费
			setDeliverFee(list, bean, true, false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费
			setDeliverFee(list, bean, true, false);
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 设置上楼费
			setDeliverFee(list, bean, false, false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 设置送货费
			setDeliverFee(list, bean, true, false);
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 设置进仓费
			setDeliverFee(list, bean, false, true);
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
	private void veryFarDeliveryFee(ExpWaybillPanelVo bean) {
		if (bean.getKilometer() != null) {
			java.util.List<ValueAddDto> list = waybillService.queryValueAddPriceList(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			// 设置超远派送费
			setDeliverFee(list, bean, false, false);
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
	private QueryBillCacilateValueAddDto getVeryFarQueryParam(ExpWaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
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
	 * 
	 * @param flag
	 *            = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge
	 *            = true 表示送货费中的送货进仓费
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void setDeliverFee(java.util.List<ValueAddDto> list, ExpWaybillPanelVo bean, Boolean flag, Boolean isDeliverStorge) {
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
			// 提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				// 费用名称
				deliver.setName(dto.getSubTypeName());
			} else {
				// 费用名称
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
			// Common.setFavorableDiscount(dto.getDiscountPrograms(), this,
			// bean);
		}
	}

	/**
	 * 
	 * 清理送货费
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void cleanDeliverCharge(ExpWaybillPanelVo bean) {
		// 画布-送货费
		bean.setDeliveryGoodsFeeCanvas("0");
		// 送货费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		// 送货费集合
		bean.setDeliverList(null);
	}
	
	
	/**
	 * 清理其它费用清理其它费用
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-7 上午11:27:55
	 */
	private void cleanOtherCharge(ExpWaybillPanelVo bean){
		// 画布-其它费用
		bean.setOtherFee(BigDecimal.ZERO);
		// 其它费用
		bean.setOtherFeeCanvas("0");
		// 清空签回单
		returnBillFee = BigDecimal.ZERO;
	}

	/**
	 * 
	 * 获取代收货款费率和费用
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void getCod(ExpWaybillPanelVo bean) {
		// 获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);

		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();
		// 代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(bean);
		if (codCollection != null) {
			priceEntities.add(codCollection);// 代收货款手续费
		}
		productPriceDtoCollection.setPriceEntities(priceEntities);

		// 统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService.queryGuiBillPrice(productPriceDtoCollection);

		// 如果返回的价格为空，抛出业务异常
		if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		}

		// 获取代收货款手续费
		GuiResultBillCalculateDto CodCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_HK, null);

		// 设置代收货款手续费
		setCodCollection(bean, CodCollection);

	}

	/**
	 * 
	 * 设置代收货款 如果有代收货款但却找不到价格，提示查询不到价格
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private void setCodCollection(ExpWaybillPanelVo bean, GuiResultBillCalculateDto dto) {
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
	private void setCod(ExpWaybillPanelVo bean, GuiResultBillCalculateDto dto) {
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
			// 将代收货款费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = ExpCommon.nullBigDecimalToZero(dto.getActualFeeRate());
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
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(ExpWaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor()) {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.NO);
		}

	}

	/**
	 * 
	 * 获取代收货款费率
	 * 
	 * @author WangQianJin
	 * @date 2013-05-02
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(ExpWaybillPanelVo bean) {

		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0) {

			return getQueryParamByCon(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), null);
		} else {
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
	private GuiQueryBillCalculateSubDto getQueryParamByCon(ExpWaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
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
	private QueryBillCacilateValueAddDto getQueryParam(ExpWaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
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
	private void getInsurance(ExpWaybillPanelVo bean) {
		ValueAddDto dto = new ValueAddDto();// 创建对象
		// 查询保价费
		java.util.List<ValueAddDto> list = waybillService.queryValueAddPriceList(getInsuranceParam(bean));

		if (list != null && !list.isEmpty()) {
			dto = list.get(0);
			setInsurance(bean, dto);// 设置保价费
		} else {
			// 清空保价费
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
	private void setInsurance(ExpWaybillPanelVo bean, ValueAddDto dto) {
		if (dto != null) {
//			// 最高保价声明
//			bean.setMaxInsuranceAmount(dto.getMaxFee());
			// 最低保价费
			bean.setMixInsuranceAmount(dto.getMinFee());
			// 保险费ID
			bean.setInsuranceId(dto.getId());
			// 保险费CODE
			bean.setInsuranceCode(dto.getPriceEntityCode());
		}

		// 保险费
		BigDecimal insuranceFee = CommonUtils.defaultIfNull(bean.getInsuranceFee());
		BigDecimal insuranceAmount = CommonUtils.defaultIfNull(bean.getInsuranceAmount());

		// 判断保险声明价值是否为0
		if (insuranceFee.compareTo(BigDecimal.ZERO) != 0 && insuranceAmount.compareTo(BigDecimal.ZERO) == 0) {
			MsgBox.showError("保价声明为0，则保价费必须为0！");
			getTxtInsuranceAmount().requestFocus();
			return;
		}

		// 保险费率
		BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal feeRate = CommonUtils.defaultIfNull(bean.getInsuranceRate());
		if (null != dto) {
			feeRate = CommonUtils.defaultIfNull(dto.getActualFeeRate());
			feeRate = feeRate.multiply(permillage);
		}

		/**
		 * 当保价费大于0时，进行判断： 1）若保险费小于最低一票，则保险费率按查询出来的费率计算，保险费＝保险费的最低一票；
		 * 2）若保险费大于或等于最低一票，则
		 */
		// 保价费大于0
		if (insuranceFee.compareTo(BigDecimal.ZERO) > 0) {
			// 保险费小于最低一票
			if (insuranceFee.compareTo(CommonUtils.defaultIfNull(bean.getMixInsuranceAmount())) < 0) {
				// 保价费
				insuranceFee = bean.getMixInsuranceAmount();
			}
		}else {
			if (insuranceAmount.doubleValue() > 0 && dto != null && dto.getCaculateFee() != null) {
				insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}
		}
//
//		if (insuranceAmount.compareTo(BigDecimal.ZERO) != 0) {
//			feeRate = insuranceFee.multiply(permillage).divide(insuranceAmount, 2, BigDecimal.ROUND_HALF_UP);
//		} else {
//			feeRate = BigDecimal.ZERO;
//		}

		// 保险费率
		bean.setInsuranceRate(feeRate);
		// 保险手续费
		bean.setInsuranceFee(insuranceFee);
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
		if (bean.getGoodsWeightTotal().doubleValue() == 0) {
			return null;
		} 
		// 第一次查询
		List<ProductPriceDto> productPrice = waybillService.queryProductPriceList(ExpCommon.getQueryParam(bean));

		ProductPriceDto dto = null;
		if (productPrice == null || productPrice.isEmpty()) {
			return null;
		} else {
			// 第二次查询，只试用于签收单原件返回
			List<ProductPriceDto> productReturnPrice = null;

			// 是否为签收单原件返回
			if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
				productReturnPrice = waybillService.queryProductPriceList(getQueryReturnParam(bean));
			}
			for (ProductPriceDto fee : productPrice) {
				if (fee.getCaculateFee() == null) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
				}
				BigDecimal transportFee = null;
				// 是否为签收单原件返回
				if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
					if (CollectionUtils.isNotEmpty(productReturnPrice)) {
						BigDecimal caculateReturn = productReturnPrice.get(0).getCaculateFee();
						returnBillFee = caculateReturn;
						transportFee = CommonUtils.defaultIfNull(fee.getCaculateFee()).add(caculateReturn).setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
					}
				} else {
					transportFee = fee.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
				}
				// 设置运费价格ID
				bean.setTransportFeeId(fee.getId());
				// 设置运费价格CODE
				bean.setTransportFeeCode(fee.getPriceEntityCode());
				// 设置运费
				bean.setTransportFee(transportFee);
				// 设置费率
				bean.setUnitPrice(fee.getActualFeeRate());
				// 设置计费类型（重量计费或者体积计费）
				// setBillingWay(fee.getCaculateType(), bean);

				// 设置计费重量
				setBillWeight(bean, fee);

				// 画布公布价运费
				bean.setTransportFeeCanvas(fee.getCaculateFee().toString());
				dto = fee;

			}
		}
		return dto;
	}

	/**
	 * 设置查询有签收单原件返回时的查询参数
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午6:03:41
	 */
	private QueryBillCacilateDto getQueryReturnParam(WaybillPanelVo bean) {
		// 新的查询条件
		QueryBillCacilateDto dto = new QueryBillCacilateDto();
		try {
			PropertyUtils.copyProperties(dto, ExpCommon.getQueryParam(bean));
			// 设置重量：固定值0.5
			dto.setWeight(BigDecimal.valueOf(0.5));
			// 设置体积
			dto.setVolume(BigDecimal.ZERO);
			dto.setCustomerCode("");
			dto.setIsSelfPickUp(FossConstants.NO);
		} catch (Exception e) {
			e.printStackTrace();
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
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {// 精准空运

			if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {// 按重量计费
				bean.setBillWeight(bean.getGoodsWeightTotal()); // 设置计费重量
			} else {
				// 计费重量
				if (fee.getVolumeWeight() == null) {
					bean.setBillWeight(BigDecimal.ZERO);// 设置计费重量 = 0
				} else {
					bean.setBillWeight(fee.getVolumeWeight()); // 设置计费重量
				}
			}
		} else {
			bean.setBillWeight(BigDecimal.ZERO);// 设置计费重量 = 0
		}

	}

	// /**
	// *
	// * 设置计费类型
	// *
	// * @author wqj
	// * @date 2013-03-28
	// */
	// private void setBillingWay(String billingWay, WaybillPanelVo bean) {
	// //DefaultComboBoxModel combBillingType = (DefaultComboBoxModel)
	// this.getCombBillingType();
	//
	// int size = combBillingType.getSize();
	// for (int i = 0; i < size; i++) {
	// DataDictionaryValueVo vo = (DataDictionaryValueVo)
	// combBillingType.getElementAt(i);
	// if
	// (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(billingWay))
	// {
	// if (WaybillConstants.BILLINGWAY_WEIGHT.equals(vo.getValueCode())) {
	// bean.setBillingType(vo);
	// }
	// }
	//
	// if
	// (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(billingWay))
	// {
	// if (WaybillConstants.BILLINGWAY_VOLUME.equals(vo.getValueCode())) {
	// bean.setBillingType(vo);
	// }
	// }
	// }
	// }

	/**
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-wqj
	 * @date 2013-03-27
	 */
	private void initComboBox() {
		// 产品类型
		initCombProductType();
		// 提货方式
		initCombPickMode();
		// 初始化付款方式
		initCombPaymentMode();
		// 返单类型
		initCombReturnBillType();
	}

	/**
	 * 
	 * 初始化空运货物类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 下午04:29:41
	 */
	private void initCombReturnBillType() {
		// 查询返单类型
		List<DataDictionaryValueEntity> list = waybillService.queryReturnBillType();
		combReturnBillTypeModel = new DefaultComboBoxModel();
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(list)) {
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode()) || WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(vo.getValueCode())
						|| WaybillConstants.RETURNBILLTYPE_FAX.equals(vo.getValueCode())) {
					combReturnBillTypeModel.addElement(vo);
				}

			}
		}
		combReturnBill.setModel(combReturnBillTypeModel);
	}

	/**
	 * 产品类型
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initCombProductType() {
		// 营业部开单
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		productTypeModel = new DefaultComboBoxModel();
		// 根据当前用户所在部门查询部门所属产品
		setProductTypeModel(dept.getCode());
		this.getCombProductType().setModel(productTypeModel);
	}

	/**
	 * 
	 * 设置产品到数据模型
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	@SuppressWarnings("unchecked")
	public void setProductTypeModel(String deptCode) {
		java.util.List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(deptCode, WaybillConstants.TYPE_OF_EXPRESS, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		ProductEntityVo vo = null;
		for (ProductEntity product : list) {
			vo = new ProductEntityVo();
			// 将数据库查询出的产品对象进行转换，转成VO使用的对象
			ValueCopy.entityValueCopy(product, vo);
			vo.setDestNetType(product.getDestNetType());
			productTypeModel.addElement(vo);
		}
	}

	/**
	 * 提货方式
	 * 
	 * @author 087584-foss-wqj
	 * @date 2013-3-27 下午3:12:42
	 */
	private void initCombPickMode() {
		java.util.List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		pikcModeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			////DMANA-5443 FOSS开单冻结自提功能
			if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode()) 
					//|| WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(vo.getValueCode())) {
				pikcModeModel.addElement(vo);
			}
		}
		this.getCombPickMode().setModel(pikcModeModel);
	}

	/**
	 * 
	 * @description:对按钮、文本框等控件进行绑定
	 * @date 2013-03-28
	 * @author wqj
	 */
	private void bind() {
		// 获取光标绑定
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		waybillBinder = BindingFactory.getIntsance().moderateBind(ExpWaybillPanelVo.class, this, new WaybillDescriptor(), true);

		waybillBinder.addValidationListener(new WaybillValidationListner());
		listener = new ExpCalculateBindingListener(this);
		waybillBinder.addBindingListener(listener);
	}

	/**
	 * 
	 * 集中开单控件以及数据初始化
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	private void focusWaybillInit(WaybillPanelVo bean) {
		if (WaybillConstants.WAYBILL_FOCUS.equals(waybillType)) {
			// 集中开单
			bean.setPickupCentralized(true);
			String receiveOrgCode = (String) SessionContext.get(WaybillConstants.FOCUS_DEPT_CODE);
			String receiveOrgName = (String) SessionContext.get(WaybillConstants.FOCUS_DEPT_NAME);
			if (StringUtils.isNotEmpty(receiveOrgCode) && StringUtils.isNotEmpty(receiveOrgName)) {
				bean.setReceiveOrgCode(receiveOrgCode);// 收货部门编码
				bean.setReceiveOrgName(receiveOrgName);// 收货部门名称
				// 根据部门查询产品
				setProductTypeModel(receiveOrgCode);
				// 设置产品默认值
				setProductCode(bean);
			}
		} else {
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			// 非集中开单
			bean.setPickupCentralized(false);
			bean.setReceiveOrgCode(dept.getCode());// 收货部门
			bean.setReceiveOrgName(dept.getName());// 收货部门
		}
		// 运输性质获取光标
		combProductType.requestFocus();
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
			// 默认设置为精准卡航
			if (CommonUtils.directDetermineIsExpressByProductCode(vo.getCode())) {
				bean.setProductCode(vo);
			}
		}
	}

	class MyActionListener implements ActionListener {

		ExpCalculateCostsDialog dialog;

		public MyActionListener(ExpCalculateCostsDialog dialog) {
			this.dialog = dialog;
		}

		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
		}
	}

	public JTextFieldValidate getTextReceiveOrgName() {
		return textReceiveOrgName;
	}

	public void setTextReceiveOrgName(JTextFieldValidate textReceiveOrgName) {
		this.textReceiveOrgName = textReceiveOrgName;
	}

	public JTextField getTxtHeadWeight() {
		return txtHeadWeight;
	}

	public void setTxtHeadWeight(JTextField txtHeadWeight) {
		this.txtHeadWeight = txtHeadWeight;
	}

	public JTextField getTxtAddWeightOne() {
		return txtAddWeightOne;
	}

	public void setTxtAddWeightOne(JTextField txtAddWeightOne) {
		this.txtAddWeightOne = txtAddWeightOne;
	}

	public JTextField getTxtAddWeightTwo() {
		return txtAddWeightTwo;
	}

	public void setTxtAddWeightTwo(JTextField txtAddWeightTwo) {
		this.txtAddWeightTwo = txtAddWeightTwo;
	}
}