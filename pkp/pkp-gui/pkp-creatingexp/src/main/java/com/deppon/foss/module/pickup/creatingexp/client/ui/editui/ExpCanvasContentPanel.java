/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpCanvasContentPanel extends JPanel {

	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 12
	 */
	private static final int TWELVE = 12;

	/**
	 * fieldName
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpCanvasContentPanel.class);

	/**
	 * 计费类型
	 */
	@Bind("billingType")
	private JComboBox combBillingType;//计费类型
	
	/**
	 * 计费重量
	 */
	@Bind("billWeight")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "计费重量") })
	private JTextFieldValidate txtBillWeight;

	/**
	 * 费率
	 */
	@Bind("unitPrice")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "费率") })
	private JTextFieldValidate txtUnitPrice;

	/**
	 * 公布价运费
	 */
	@Bind("transportFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "公布价运费") })
	private JTextFieldValidate txtTransportFee;

	/**
	 * 保价声明
	 */
	@Bind("insuranceAmountCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价声明") })
	private JTextFieldValidate txtInsuranceAmount;

	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	private JTextFieldValidate txtInsuranceRate;

	/**
	 * 保价费
	 */
	@Bind("insuranceFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费") })
	private JTextFieldValidate txtInsuranceFee;

	/**
	 * 代收金额
	 */
	@Bind("codAmountCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收金额") })
	private JTextFieldValidate txtCodAmount;

	/**
	 * 代收费率
	 */
	@Bind("codRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收费率") })
	private JTextFieldValidate txtCodRate;

	/**
	 * 代收手续费
	 */
	@Bind("codFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收手续费") })
	private JTextFieldValidate txtCodFee;

	/**
	 * 接货费
	 */
	@Bind("pickUpFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "接货费") })
	private JTextFieldValidate txtPickUpFee;

	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "送货费") })
	private JTextFieldValidate txtDeliveryGoodsFee;

	/**
	 * 包装费
	 */
	@Bind("packageFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "包装费") })
	private JTextFieldValidate txtPackageFee;

	/**
	 * 装卸费
	 */
	@Bind("serviceFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "装卸费") })
	private JTextFieldValidate txtServiceFee;

	/**
	 * 其他费用
	 */
	@Bind("otherFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "其他费用合计") })
	private JTextFieldValidate txtOtherFee;

	/**
	 * 优惠
	 */
	@Bind("promotionsFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "优惠合计") })
	private JTextFieldValidate txtPromotionsFee;

	/**
	 * 总费用
	 */
	@Bind("totalFeeCanvas")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总费用") })
	private JTextFieldValidate txtTotalFee;

	/**
	 * 其它价格PANEL
	 */
	private ExpOtherCostPanel otherCost = new ExpOtherCostPanel();
	
	private ExpCargoRoutePanel cargoRoutePanel = new ExpCargoRoutePanel();
	
	/**
	 * 折扣优惠面板
	 */
	private ExpConcessionsPanel concessionsPanel = new ExpConcessionsPanel();
	
	@Bind("standCharge")
	private JTextFieldValidate txtStandCharge;//木架费
	
	@Bind("boxCharge")
	private JTextFieldValidate txtBoxCharge;//木箱费
	

	
	/**
	 * 构造方法
	 */
	public ExpCanvasContentPanel() {
		init();
		add(detailInfoPanelInit(), "2, 2, 1, 3, fill, fill");
		add(concessionsPanel, "3, 2, fill, fill");
		add(otherCost, "3, 3, fill, fill");
		add(cargoRoutePanel, "3, 4, fill, fill");

	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setBorder(new JDTitledBorder());
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				RowSpec.decode("default:grow"),
				RowSpec.decode("default:grow"),}));
	}

	/**
	 * 初始化界面信息 DEFAULT VALUE
	 * @return
	 */
	private JPanel detailInfoPanelInit() {
		JPanel panel = new JPanel();
		// setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//详细计价信息
		JLabel label20 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.title"));
		label20.setFont(new Font("宋体", Font.BOLD, TWELVE));
		panel.add(label20, "2, 2, 3, 1");

		//计费类型
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.billingType.label")+"：");
		panel.add(lblNewLabel1, "2, 4");

		combBillingType = new JComboBox();
		panel.add(combBillingType, "3, 4, 4, 1, fill, default");
		combBillingType.setEnabled(false);

		//计费重量
		JLabel label = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.billWeight.label")+"：");
		panel.add(label, "2, 6");

		txtBillWeight = new JTextFieldValidate();
		panel.add(txtBillWeight, "3, 6, 4, 1, fill, default");
		txtBillWeight.setColumns(TEN);
		txtBillWeight.setEnabled(false);

		//费率
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.unitPrice.label")+"：");
		panel.add(label1, "2, 8");

		txtUnitPrice = new JTextFieldValidate();
		panel.add(txtUnitPrice, "3, 8, 4, 1, fill, default");
		txtUnitPrice.setColumns(TEN);
		txtUnitPrice.setEnabled(false);

		//公布价运费
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.transportFee.label")+"：");
		panel.add(label2, "2, 10");

		txtTransportFee = new JTextFieldValidate();
		panel.add(txtTransportFee, "3, 10, 4, 1, fill, default");
		txtTransportFee.setColumns(TEN);
		txtTransportFee.setEnabled(false);

		JLabel label3 = new JLabel("-------------------");
		panel.add(label3, "2, 11, 3, 3, center, default");

		//保价声明
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceAmount.label")+"：");
		panel.add(label4, "2, 14");

		txtInsuranceAmount = new JTextFieldValidate();
		panel.add(txtInsuranceAmount, "3, 14, 4, 1, fill, default");
		txtInsuranceAmount.setColumns(TEN);
		txtInsuranceAmount.setEnabled(false);

		//保价费率
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label")+"：");
		panel.add(label5, "2, 16");

		txtInsuranceRate = new JTextFieldValidate();
		panel.add(txtInsuranceRate, "3, 16, 3, 1, fill, default");
		txtInsuranceRate.setColumns(TEN);
		txtInsuranceRate.setEnabled(false);
		
		JLabel label_1 = new JLabel("‰");
		panel.add(label_1, "6, 16");

		//保价费
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceFee.label")+"：");
		panel.add(label6, "2, 18");

		txtInsuranceFee = new JTextFieldValidate();
		panel.add(txtInsuranceFee, "3, 18, 4, 1, fill, default");
		txtInsuranceFee.setColumns(TEN);
		txtInsuranceFee.setEnabled(false);

		JLabel label7 = new JLabel("-------------------");
		panel.add(label7, "2, 19, 3, 3, center, default");

		//代收金额
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codAmount.label")+"：");
		panel.add(lblNewLabel2, "2, 22");

		txtCodAmount = new JTextFieldValidate();
		panel.add(txtCodAmount, "3, 22, 4, 1, fill, default");
		txtCodAmount.setColumns(TEN);
		txtCodAmount.setEnabled(false);

		//代收费率
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codRate.label")+"：");
		panel.add(label8, "2, 24");

		txtCodRate = new JTextFieldValidate();
		panel.add(txtCodRate, "3, 24, 3, 1, fill, default");
		txtCodRate.setColumns(TEN);
		txtCodRate.setEnabled(false);
		
		JLabel label_2 = new JLabel("‰");
		panel.add(label_2, "6, 24");

		//代收手续费
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.codFee.label")+"：");
		panel.add(label9, "2, 26");

		txtCodFee = new JTextFieldValidate();
		panel.add(txtCodFee, "3, 26, 4, 1, fill, default");
		txtCodFee.setColumns(TEN);
		txtCodFee.setEnabled(false);

		JLabel label10 = new JLabel("-------------------");
		panel.add(label10, "2, 27, 3, 3, center, default");

		//接货费
		JLabel label21 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.pickUpFee.label")+"：");
		panel.add(label21, "2, 30");

		txtPickUpFee = new JTextFieldValidate();
		panel.add(txtPickUpFee, "3, 30, 4, 1, fill, default");
		txtPickUpFee.setColumns(TEN);
		txtPickUpFee.setEnabled(false);

		//送货费
		JLabel label11 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.deliveryGoodsFee.label")+"：");
		panel.add(label11, "2, 32");

		txtDeliveryGoodsFee = new JTextFieldValidate();
		panel.add(txtDeliveryGoodsFee, "3, 32, 4, 1, fill, default");
		txtDeliveryGoodsFee.setColumns(TEN);
		txtDeliveryGoodsFee.setEnabled(false);
		
		//木架费
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.standCharge.label")+"：");
		panel.add(lblNewLabel, "2, 34");
		
		txtStandCharge = new JTextFieldValidate();
		txtStandCharge.setEnabled(false);
		panel.add(txtStandCharge, "3, 34, 4, 1, fill, default");
		txtStandCharge.setColumns(TEN);
		
		//木箱费
		JLabel label22 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.boxCharge.label")+"：");
		panel.add(label22, "2, 36");
		
		txtBoxCharge = new JTextFieldValidate();
		txtBoxCharge.setEnabled(false);
		panel.add(txtBoxCharge, "3, 36, 4, 1, fill, default");
		txtBoxCharge.setColumns(TEN);

		//包装费
		JLabel label12 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.packageFee.label")+"：");
		panel.add(label12, "2, 38");

		txtPackageFee = new JTextFieldValidate();
		panel.add(txtPackageFee, "3, 38, 4, 1, fill, default");
		txtPackageFee.setColumns(TEN);
		txtPackageFee.setEnabled(false);

		//装卸费
		JLabel label13 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.serviceFee.label")+"：");
		panel.add(label13, "2, 40");

		txtServiceFee = new JTextFieldValidate();
		panel.add(txtServiceFee, "3, 40, 4, 1, fill, default");
		txtServiceFee.setColumns(TEN);
		txtServiceFee.setEnabled(false);

		JLabel label14 = new JLabel("-------------------");
		panel.add(label14, "2, 41, 3, 3, center, default");

		//其他费用
		JLabel label15 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.otherFee.label")+"：");
		panel.add(label15, "2, 44");

		txtOtherFee = new JTextFieldValidate();
		panel.add(txtOtherFee, "3, 44, 4, 1, fill, default");
		txtOtherFee.setColumns(TEN);
		txtOtherFee.setEnabled(false);

		JLabel label16 = new JLabel("-------------------");
		panel.add(label16, "2, 45, 3, 3, center, default");

		//优惠费用
		JLabel label17 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.promotionsFee.label")+"：");
		panel.add(label17, "2, 48");

		txtPromotionsFee = new JTextFieldValidate();
		panel.add(txtPromotionsFee, "3, 48, 4, 1, fill, default");
		txtPromotionsFee.setColumns(TEN);
		txtPromotionsFee.setEnabled(false);

		JLabel label18 = new JLabel("-------------------");
		panel.add(label18, "2, 49, 3, 3, center, default");

		//总运费
		JLabel label19 = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.totalFee.label")+"：");
		panel.add(label19, "2, 52");

		txtTotalFee = new JTextFieldValidate();
		panel.add(txtTotalFee, "3, 52, 4, 1, fill, default");
		txtTotalFee.setColumns(TEN);
		txtTotalFee.setEnabled(false);

		return panel;
	}

	/**
	 * getCombBillingType
	 * @return JComboBox
	 */
	public JComboBox getCombBillingType() {
		return combBillingType;
	}

	/**
	 * getOtherCost
	 * @return OtherCostPanel
	 */
	public ExpOtherCostPanel getOtherCost() {
		return otherCost;
	}
	
	public ExpConcessionsPanel getConcessionsPanel() {
		return concessionsPanel;
	}

	public ExpCargoRoutePanel getCargoRoutePanel() {
		return cargoRoutePanel;
	}

}
