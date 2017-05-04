/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWaybillSubmitAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq = 8)
public class ExpBillingPayPanel  extends JPanel {

	/**
	 * 14
	 */
	private static final int FOURTEEN = 14;

	private static final int NIVE = 9;

	private static final int FIVE = 5;
	/**
	 * 10 for column count
	 */
	private static final int TEN = 10;
	
	private static final int SEVEN = 7;
	
	private static final int SIX = 6;

	/**
	 * fieldname
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpBillingPayPanel.class);

	/**
	 * 包装费
	 */
	@Bind("packageFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "包装费") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtPackCharge;

	/**
	 * 接货费
	 */
	@Bind("pickupFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "接货费") })
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtPickUpCharge;

	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "送货费") })
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtDeliveryCharge;

	/**
	 * 预付金额
	 */
	@Bind("prePayAmount")
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtAdvancesMoney;

	/**
	 * 到付金额
	 */
	@Bind("toPayAmount")
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtArrivePayment;

	/**
	 * 提交
	 */
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "", type = ExpWaybillSubmitAction.class)
	@FocusSeq(seq = 6)
	JButton btnSubmit;

	/**
	 * 增值服务费
	 */
	@Bind("valueAddFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "增值服务费") })
	private JTextFieldValidate txtIncrementCharge;

	/**
	 * 优惠合计
	 */
	@Bind("promotionsFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "优惠合计") })
	private JTextFieldValidate txtPromotionTotal;

	/**
	 * 总费用
	 */
	@Bind("totalFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总费用") })
	private JTextFieldValidate txtTotalCharge;

	/**
	 * 手写现付金额
	 */
	@Bind("handWriteMoney")
	private JTextFieldValidate txtHandWriteMoney;
	
	/**
	 *控制文本框只能输入数字
	 *@author:218371-foss-zhaoyanjun
	 *@date:2015-01-23上午10:25
	 */
	PackageNumberDocument number=new PackageNumberDocument(NumberConstants.NUMBER_200);
	
	// 支付子面板
	public ExpBillingPayBelongPanel billingPayBelongPanel;
	
	/**
	 * 增加交易流水号标签
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:53
	 */
	private JLabel label;
	
	/**
	 * 增加交易流水号文本框
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:53
	 */
	@Bind("transactionSerialNumber")
	private JTextFieldValidate transactionSerialNumber;

	/**
	 * Create the panel.
	 */
	public ExpBillingPayPanel() {

		init();

	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:40:13
	 * @see
	 */
	private void init() {
		setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u8D39\u7528\u660E\u7EC6", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70,
				213)), new EmptyBorder(0, 0, NumberConstants.NUMBER_4, 0)));
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(31dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(29dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		billingPayBelongPanel = new ExpBillingPayBelongPanel();
		add(billingPayBelongPanel, "2, 1, 7, 1, fill, center");
		NumberDocument advancesMoney = new NumberDocument(TEN);
		NumberDocument packCharge = new NumberDocument(SIX);
		NumberDocument pickUpCharge = new NumberDocument(FIVE);
		NumberDocument deliverCharge = new NumberDocument(FIVE);

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel.label"));
		add(lblNewLabel, "12, 1, right, default");

		txtPackCharge = new JTextFieldValidate();
		add(txtPackCharge, "14, 1, fill, default");
		txtPackCharge.setColumns(TEN);		
		txtPackCharge.setDocument(packCharge);
		txtPackCharge.setEnabled(true);
		txtPackCharge.setEditable(true);

		JLabel lblNewLabel_1 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel1.label"));
		add(lblNewLabel_1, "16, 1, right, default");

		txtPickUpCharge = new JTextFieldValidate();
		txtPickUpCharge.setEnabled(false);
		add(txtPickUpCharge, "18, 1, fill, default");
		txtPickUpCharge.setDocument(pickUpCharge);
		txtPickUpCharge.setColumns(TEN);

		JLabel lblNewLabel_2 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel2.label"));
		add(lblNewLabel_2, "20, 1, right, default");

		txtDeliveryCharge = new JTextFieldValidate();
		txtDeliveryCharge.setEnabled(true);
		add(txtDeliveryCharge, "22, 1, fill, default");
		txtDeliveryCharge.setColumns(10);
		txtDeliveryCharge.setDocument(deliverCharge);

		// 增值服务费
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.incrementCharge.label") + "：");
		add(label3, "2, 3, right, default");

		txtIncrementCharge = new JTextFieldValidate();
		add(txtIncrementCharge, "3, 3, 2, 1, fill, default");
		txtIncrementCharge.setColumns(TEN);
		txtIncrementCharge.setEnabled(false);

		// 优惠合计
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.promotionTotal.label") + "：");
		add(label5, "6, 3, left, default");

		txtPromotionTotal = new JTextFieldValidate();
		add(txtPromotionTotal, "7, 3, 2, 1, fill, default");
		txtPromotionTotal.setColumns(TEN);
		txtPromotionTotal.setEnabled(false);

		// 到付金额
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.advancesMoney.label") + "：");
		add(label4, "12, 3, right, default");
		FloatDocument advancesPayment = new FloatDocument(TEN, 2);
		txtAdvancesMoney = new JTextFieldValidate();
		add(txtAdvancesMoney, "14, 3, fill, default");
		txtAdvancesMoney.setDocument(advancesPayment);
		txtAdvancesMoney.setColumns(TEN);
		txtAdvancesMoney.setEnabled(false);

		// 到付金额
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.arrivePayment.label") + "：");
		add(label6, "16, 3, left, default");

		txtArrivePayment = new JTextFieldValidate();
		add(txtArrivePayment, "18, 3, fill, default");
		txtArrivePayment.setColumns(TEN);
//		NumberDocument arrivePayment = new NumberDocument(TEN);
//		liyongfei 设置到付货款可以显示小数
		FloatDocument arrivePayment = new FloatDocument(TEN, 2);
		txtArrivePayment.setDocument(arrivePayment);
		txtArrivePayment.setEnabled(false);

		// 手写现付金额
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.handWriteMoney.label") + "：");
		add(label9, "20, 3, right, default");

		txtHandWriteMoney = new JTextFieldValidate();
		add(txtHandWriteMoney, "22, 3, fill, default");
		txtHandWriteMoney.setColumns(TEN);
		NumberDocument document = new NumberDocument(TEN);
		txtHandWriteMoney.setDocument(document);
		txtHandWriteMoney.setEnabled(false);

		JPanel panel = new JPanel();
		panel.setBorder(new JDTitledBorder());
		add(panel, "2, 5, 21, 1");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(59dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		// 总运费
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.totalCharge.label") + "：");
		label8.setFont(new Font("宋体", Font.BOLD, FOURTEEN));
		panel.add(label8, "2, 2, right, default");

		txtTotalCharge = new JTextFieldValidate();
		panel.add(txtTotalCharge, "4, 2, 5, 1, fill, default");
		txtTotalCharge.setColumns(TEN);
		txtTotalCharge.setEnabled(false);
		
		/**
		 * 增加交易流水号标签
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-22下午18:53
		 */
		label = new JLabel("交易流水号：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		panel.add(label, "10, 2, right, default");
		
		/**
		 * 增加交易流水号文本框
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-22下午18:53
		 */
		transactionSerialNumber = new JTextFieldValidate();
		transactionSerialNumber.setEnabled(false);
		panel.add(transactionSerialNumber, "12, 2, 5, 1, fill, default");
		transactionSerialNumber.setColumns(TEN);
		transactionSerialNumber.setDocument(number);

		btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));
		panel.add(btnSubmit, "20, 2");
		btnSubmit.setEnabled(false);
	}

	/**
	 * 
	 * 提交
	 * @author 025000-FOSS-helong
	 * @date 2013-4-16 下午02:28:50
	 * @return
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	/**
	 * getTxtHandWriteMoney
	 * 
	 * @return JTextFieldValidate
	 */
	public JTextFieldValidate getTxtHandWriteMoney() {
		return txtHandWriteMoney;
	}

	/**
	 * 
	 * 获得预付金额控件对象
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:25:13
	 * @return
	 */
	public JTextFieldValidate getTxtAdvancesMoney() {
		return txtAdvancesMoney;
	}

	/**
	 * getTxtPickUpCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPickUpCharge() {
		return txtPickUpCharge;
	}

	/**
	 * getTxtDeliveryCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtDeliveryCharge() {
		return txtDeliveryCharge;
	}

	/**
	 * getTxtPackCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPackCharge() {
		return txtPackCharge;
	}

	/**
	 * 
	 * 获得到付金额控件对象
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:25:30
	 * @return
	 */
	public JTextFieldValidate getTxtArrivePayment() {
		return txtArrivePayment;
	}

	public JTextFieldValidate getTxtIncrementCharge() {
		return txtIncrementCharge;
	}

	public JTextFieldValidate getTxtPromotionTotal() {
		return txtPromotionTotal;
	}

	public JTextFieldValidate getTxtTotalCharge() {
		return txtTotalCharge;
	}
	
	public JTextFieldValidate getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(
			JTextFieldValidate transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
}
