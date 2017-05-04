package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.PositiveNumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.CalculateAction;
import com.deppon.foss.module.pickup.creating.client.action.DeleteOtherChargeAction;
import com.deppon.foss.module.pickup.creating.client.action.InstallAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryBankAccountAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryCustomCouponAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryOtherChargeAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.CollectionUtils;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * (增值业务面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:18:47,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:18:47
 * @since
 * @version
 */
@ContainerSeq(seq = 6)
public class IncrementPanel extends JPanel {

	private static final int FIVE = 5;

	private static final int TEN = 10;

	private static final int NIVE = 8;

	private static final int EIGHT = 8;
	private static final int FOUR = 4;

	private static final String FIELDNAME = "fieldName";
//	private static final String _HK_PROV = "810000";

//	private static final String _HK_CITY = "810100";
	
	private static final Log log = LogFactory.getLog(IncrementPanel.class);

	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(IncrementPanel.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6683340393741097043L;

	private static final int NUM_110 = 110;

	private static final double NUM_1000 = 1000.0;

	private String pictureWaybillType ;
	/**
	 * 运单service
	 */
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	private WaybillPanelVo waybillPanelVo;
	/**
	 * 收款人
	 */
	@Bind("accountName")
	private JTextFieldValidate txtPayee;

	/**
	 * 保价声明价
	 */
	@Bind("insuranceAmount")
	//@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价声明价") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtInsuranceValue;
	
	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	private JTextFieldValidate txtInsuranceRate;
	
	/**
	 * 报价费率范围
	 * */
	@Bind("insuranceRateRange")
	private JTextFieldValidate txtInsuranceRateRange;	//@BindingArgs({ @BindingArg(name = FIELDNAME, value = "报价费率范围") })
	
	public JTextFieldValidate getTxtInsuranceRateRange() {
		return txtInsuranceRateRange;
	}
	
	/**
	 * 代收货款
	 */
	@Bind("codAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收货款") })
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtCashOnDelivery;
	
	/**
	 * 查询代收货款退款银行账号
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryBankAccountAction.class)
	private JButton btnQueryBankAccount;

	/**
	 * 退款类型
	 */
	@Bind("refundType")
	@FocusSeq(seq = 3)
	JComboBox combRefundType;
	
	/**
	 * 返单类型
	 */
	@Bind("returnBillType")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "返单类型") })
	@FocusSeq(seq = 4)
	JComboBox combReturnBillType;
	
	/**
	 * 装卸费
	 */
	@Bind("serviceFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "装卸费") })
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtServiceCharge;
	
	/**
	 * 优惠编码
	 */
	@Bind("promotionsCode")
	@FocusSeq(seq = 6)
	private JTextFieldValidate txtPromotionNumber;

	/**
	 * 开单付款方式
	 */
	@Bind("paidMethod")
	@FocusSeq(seq = 7)
	private JComboBox combPaymentMode;
	

	/**
	 * 预付费保密
	 */
	@Bind("secretPrepaid")
	@FocusSeq(seq = 8)
	JCheckBox chbSecrecy;
	
	/**
	 * 计算
	 */
	@ButtonAction(icon = IconConstants.CALCULATE_PRICE, shortcutKey = "F11", type = CalculateAction.class)
	@FocusSeq(seq = 9)
	private JButton btnCalculate;

	/**
	 * 银行账号
	 */
	@Bind("accountCode")
	private JTextFieldValidate txtBankAccount;



	/**
	 * 其他费用
	 */
	@Bind("otherFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "其他费用") })
	private JTextFieldValidate txtOtherCharge;



	/**
	 * 滚动条面板
	 */
	JScrollPane scrollPane;

	/**
	 * 其他费用安装费
	 */
	@ButtonAction(icon = IconConstants.Install, shortcutKey = "", type = InstallAction.class)
	private JButton btnInstall;


	/**
	 * 其他費用
	 */
	JXTable tblOther;

	/**
	 * 其他费用新增
	 */
	@ButtonAction(icon = IconConstants.Other_Charge_New, shortcutKey = "", type = QueryOtherChargeAction.class)
	private JButton btnNew;

	/**
	 * 其他费用删除
	 */
	@ButtonAction(icon = IconConstants.DELETE, shortcutKey = "", type = DeleteOtherChargeAction.class)
	private JButton btnDelete;

	/**
	 * 主界面
	 */
	private WaybillEditUI ui;

	

	private JLabel label11;

	private JLabel label_1;

	private JLabel label_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	private JLabel labelActiveInfo;
	//市场营销活动
	@Bind("activeInfo")
	private JComboBox combActiveInfo;

	private JComboBox combServiceRate = new JComboBox();
	//装卸费
	private JLabel jlable = new JLabel(i18n.get("foss.gui.creating.incrementPanel.serviceRate"));
	
	/**
	 * @项目：智能开单项目
	 * @功能：保存操作该下拉框的时间
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午14:28
	 */
	double combReturnBillTypeTime=0;
	double combPaymentModeTime=0;
	WaybillPanelVo intellligenceBean;

	/**
	 * 优惠券查询按钮
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryCustomCouponAction.class)
	private JButton couponQuerybtn;
	/**
	 * Create the panel.
	 */
	public IncrementPanel(String pictureWaybillType) {
		this.pictureWaybillType = pictureWaybillType;
		// 初始化其他费用表格
		initTable();
		if(StringUtils.isNotBlank(pictureWaybillType) && 
				WaybillConstants.WAYBILL_PICTURE.equals(pictureWaybillType)){
			pictureInit();
		}else{
		// 初始化界面
		init();
		}
		// 初始化其他费用数据
		//initData();
	}
	
	public IncrementPanel(){
		init();
	}

	/**
	 * 
	 * <p>
	 * 界面初始化
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:27:54
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.incrementPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("19dlu:grow"),
				ColumnSpec.decode("max(18dlu;default)"),
				ColumnSpec.decode("20dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(35dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(13dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(21dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		// 保险声明价值
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.insuranceValue.label") + "：");
		add(label1, "2, 1, left, default");

		NumberDocument insuranceDocument = new NumberDocument(EIGHT);
		NumberDocumentUtils cashOnDelivery = new NumberDocumentUtils(NIVE);
		PositiveNumberDocument serviceCharge = new PositiveNumberDocument(FIVE);

		txtInsuranceValue = new JTextFieldValidate();
		add(txtInsuranceValue, "3, 1, fill, default");
		txtInsuranceValue.setColumns(TEN);
		txtInsuranceValue.setDocument(insuranceDocument);
		//保价费率
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label") + "：");
		add(lblNewLabel, "4, 1, right, default");
		lblNewLabel.setVisible(true);
		//保价费率
		txtInsuranceRate = new JTextFieldValidate();
		add(txtInsuranceRate, "5, 1, fill, default");
		txtInsuranceRate.setColumns(TEN);
		txtInsuranceRate.setEnabled(true);
		txtInsuranceRate.setVisible(true);
		
		lblNewLabel_1 = new JLabel("‰");
		add(lblNewLabel_1, "6, 1, right, default");
		lblNewLabel_1.setVisible(true);
		
		txtInsuranceRateRange = new JTextFieldValidate();
		add(txtInsuranceRateRange, "7, 1, 6, 1, fill, default");
		txtInsuranceRateRange.setColumns(TEN);
		txtInsuranceRateRange.setEnabled(false);

		// 代收货款
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label") + "：");
		add(label4, "14, 1, right, default");

		txtCashOnDelivery = new JTextFieldValidate();
		add(txtCashOnDelivery, "15, 1, 4, 1, fill, default");
		txtCashOnDelivery.setColumns(TEN);
		txtCashOnDelivery.setDocument(cashOnDelivery);

		// 退款类型
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.refundType.label") + "：");
		add(label7, "20, 1, left, default");

		combRefundType = new JComboBox();
		add(combRefundType, "21, 1, 2, 1, fill, default");

		scrollPane = new JScrollPane(tblOther);
		add(scrollPane, "24, 1, 9, 5, fill, fill");

		// 收款人
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.payee.label") + "：");
		add(label2, "2, 3, left, default");

		txtPayee = new JTextFieldValidate();
		add(txtPayee, "3, 3, 2, 1, fill, default");
		txtPayee.setColumns(TEN);
		txtPayee.setEditable(false);

		// 查询
		btnQueryBankAccount = new JButton();
		add(btnQueryBankAccount, "5, 3");
		btnQueryBankAccount.setMargin(new Insets(-2, 1, -2, 1));

		// 收款账号
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.bankAccount.label") + "：");
		add(label5, "14, 3, right, default");

		txtBankAccount = new JTextFieldValidate();
		add(txtBankAccount, "15, 3, 4, 1, fill, default");
		txtBankAccount.setColumns(TEN);
		txtBankAccount.setEditable(false);

		// 返单类别
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.returnBillType.label") + "：");
		add(label8, "20, 3, left, default");

		combReturnBillType = new JComboBox();
		add(combReturnBillType, "21, 3, 2, 1, fill, default");

		// 装卸费
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.serviceCharge.label") + "：");
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.SERVICE_RATE){
			add(label6, "2, 5, left, default");
		}

		txtServiceCharge = new JTextFieldValidate();
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.SERVICE_RATE){
		   add(txtServiceCharge, "3, 5, 3, 1, fill, default");
		}
		txtServiceCharge.setColumns(TEN);
		txtServiceCharge.setDocument(serviceCharge);

		label_1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label1"));
		add(label_1, "14, 5, right, default");
		JPanel couponPanel = new JPanel();
		couponPanel.setLayout(new GridBagLayout());
		add(couponPanel, "15, 5, 4, 1, fill, default");
		txtPromotionNumber = new JTextFieldValidate();
		txtPromotionNumber.setColumns(NumberConstants.NUMBER_18);
		couponPanel.add(txtPromotionNumber, new GridBagConstraints(0,0,1,1,  
                0.0,0.0,  
                GridBagConstraints.CENTER,  
                GridBagConstraints.NONE,  
                new Insets(0,0,0,0),  
                NUM_110,0));
		couponQuerybtn = new JButton();
		couponQuerybtn.setMargin(new Insets(-2, 1, -2, 1));
		couponPanel.add(couponQuerybtn, new GridBagConstraints(1,0,1,1,  
                0.0,0.0,  
                GridBagConstraints.CENTER,  
                GridBagConstraints.NONE,  
                new Insets(0,0,0,0),  
                0,0));
		label_2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label2"));
		add(label_2, "20, 5, right, default");
		
		combPaymentMode = new JComboBox();
		add(combPaymentMode, "21, 5, 2, 1, fill, default");
		
		// 预付费保密
		chbSecrecy = new JCheckBox(i18n.get("foss.gui.creating.incrementPanel.secrecy.label"));
		add(chbSecrecy, "2, 7");
		
		//市场营销活动名称
		labelActiveInfo = new JLabel(i18n.get("foss.gui.creating.incrementpanel.activeinfo.discount.name")+"：");
		labelActiveInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY){
		  add(labelActiveInfo, "8, 7, 7, 1");
		}
		
		combActiveInfo = new JComboBox();
		//固定下拉框宽度，不随内容增加而增大
		combActiveInfo.setMaximumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setMinimumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setPreferredSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY){
		   add(combActiveInfo, "15, 7, 4, 1, fill, default");
		}

		btnCalculate = new JButton(i18n.get("foss.gui.creating.incrementPanel.btnCalculate"));
		add(btnCalculate, "21, 7, 2, 1");
		if(this.pictureWaybillType != null && WaybillConstants.WAYBILL_PICTURE.equals(this.pictureWaybillType.trim())){
			btnCalculate.setEnabled(false);
		}
		
		// 其他费用合计
		label11 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.otherCharge.label") + "：");
		add(label11, "24, 7, right, default");

		txtOtherCharge = new JTextFieldValidate();
		add(txtOtherCharge, "26, 7");
		txtOtherCharge.setColumns(TEN);
		txtOtherCharge.setEnabled(false);
		// 安装费
		btnInstall = new JButton(
				i18n.get("foss.gui.creating.waybillEditUI.common.install"));
		add(btnInstall, "28, 7");
		btnInstall.setVisible(false);
		// 新增
		btnNew = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.new"));
		add(btnNew, "30, 7");

		// 删除
		btnDelete = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.delete"));
		add(btnDelete, "32, 7");

	}
	
	
	/**
	 * 
	 * <p>
	 * 图片开单界面 ，界面初始化
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:27:54
	 * @see
	 */
	private void pictureInit() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.incrementPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(18dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("33dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("19dlu"),
				ColumnSpec.decode("40dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(13dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(21dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		// 保险声明价值
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.insuranceValue.label") + "：");
		add(label1, "2, 1, left, default");

		NumberDocument insuranceDocument = new NumberDocument(EIGHT);
		NumberDocumentUtils cashOnDelivery = new NumberDocumentUtils(NIVE);
		PositiveNumberDocument serviceCharge = new PositiveNumberDocument(FIVE);

		txtInsuranceValue = new JTextFieldValidate();
		add(txtInsuranceValue, "3, 1, 3, 1, fill, default");
		txtInsuranceValue.setColumns(TEN);
		txtInsuranceValue.setDocument(insuranceDocument);
		//保价费率
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label") + "：");
		add(lblNewLabel, "6, 1, right, default");
		lblNewLabel.setVisible(true);
		//保价费率
		txtInsuranceRate = new JTextFieldValidate();
		add(txtInsuranceRate, "7, 1, 2, 1");
		txtInsuranceRate.setColumns(TEN);
		txtInsuranceRate.setEnabled(true);
		txtInsuranceRate.setVisible(true);
		
		lblNewLabel_1 = new JLabel("‰");
		add(lblNewLabel_1, "10, 1, right, default");
		lblNewLabel_1.setVisible(true);
		
		txtInsuranceRateRange = new JTextFieldValidate();
		add(txtInsuranceRateRange, "12, 1, 2, 1, fill, default");
		txtInsuranceRateRange.setColumns(TEN);
		txtInsuranceRateRange.setEnabled(false);

		// 代收货款
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label") + "：");
		add(label4, "17, 1, right, default");

		txtCashOnDelivery = new JTextFieldValidate();
		add(txtCashOnDelivery, "18, 1, 4, 1, fill, default");
		txtCashOnDelivery.setColumns(TEN);
		txtCashOnDelivery.setDocument(cashOnDelivery);

		// 退款类型
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.refundType.label") + "：");
		add(label7, "23, 1, left, default");

		combRefundType = new JComboBox();
		add(combRefundType, "24, 1, 2, 1, fill, default");

		scrollPane = new JScrollPane(tblOther);
		add(scrollPane, "27, 1, 9, 5, fill, fill");

		// 收款人
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.payee.label") + "：");
		add(label2, "2, 3, left, default");

		txtPayee = new JTextFieldValidate();
		add(txtPayee, "3, 3, 2, 1, fill, default");
		txtPayee.setColumns(TEN);
		txtPayee.setEditable(false);
		
				// 查询
				btnQueryBankAccount = new JButton();
				add(btnQueryBankAccount, "5, 3");
				btnQueryBankAccount.setMargin(new Insets(-2, 1, -2, 1));

		// 收款账号
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.bankAccount.label") + "：");
		add(label5, "17, 3, right, default");

		txtBankAccount = new JTextFieldValidate();
		add(txtBankAccount, "18, 3, 4, 1, fill, default");
		txtBankAccount.setColumns(TEN);
		txtBankAccount.setEditable(false);

		// 返单类别
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.returnBillType.label") + "：");
		add(label8, "23, 3, left, default");

		combReturnBillType = new JComboBox();
		add(combReturnBillType, "24, 3, 2, 1, fill, default");
		

		// 装卸费
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.serviceCharge.label") + "：");
		add(label6, "2, 5, left, default");

		txtServiceCharge = new JTextFieldValidate();
		add(txtServiceCharge, "3, 5, 4, 1, fill, default");
		txtServiceCharge.setColumns(TEN);
		txtServiceCharge.setDocument(serviceCharge);

		label_1 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label1"));
		add(label_1, "17, 5, right, default");

		txtPromotionNumber = new JTextFieldValidate();
		add(txtPromotionNumber, "18, 5, 4, 1, fill, default");
		txtPromotionNumber.setColumns(TEN);

		label_2 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.label2"));
		add(label_2, "23, 5, right, default");

		combPaymentMode = new JComboBox();
		add(combPaymentMode, "24, 5, 2, 1, fill, default");

		// 预付费保密
		chbSecrecy = new JCheckBox(i18n.get("foss.gui.creating.incrementPanel.secrecy.label"));
		add(chbSecrecy, "2, 7, 2, 1");
		
		//市场营销活动名称
		labelActiveInfo = new JLabel(i18n.get("foss.gui.creating.incrementpanel.activeinfo.discount.name")+"：");
		labelActiveInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(labelActiveInfo, "13, 7, 5, 1");
		
		combActiveInfo = new JComboBox();
		//固定下拉框宽度，不随内容增加而增大
		combActiveInfo.setMaximumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setMinimumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setPreferredSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		add(combActiveInfo, "18, 7, 4, 1, fill, default");

		btnCalculate = new JButton(i18n.get("foss.gui.creating.incrementPanel.btnCalculate"));
		add(btnCalculate, "24, 7, 2, 1");
		if(this.pictureWaybillType != null && WaybillConstants.WAYBILL_PICTURE.equals(this.pictureWaybillType.trim())){
			btnCalculate.setEnabled(false);
			
			add(jlable, "4, 7, right, default");
			
			add(combServiceRate, "6, 7, fill, default");
			combServiceRate.setEditable(true);
			ComboBoxEditor editor = combServiceRate.getEditor();
			JTextField textField = (JTextField)editor.getEditorComponent();
			FloatDocument weight = new FloatDocument(FOUR,1);
			textField.setDocument(weight);
			
			combServiceRate.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
				private BigDecimal serviceFeeRate = new BigDecimal(NumberConstants.NUMBER_15);
				@Override
				public void focusLost(FocusEvent e) {
					HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
					IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
					WaybillPanelVo bean = waybillBinder.getBean();
					//BigDecimal serviceRate = bean.getServiceRate();

					Object serviceRate = combServiceRate.getEditor().getItem();

					/**
					 * 判断装卸费是否为空
					 *     如果为空定义它的值为0
					 *     如果不为空则判断是值是否大于15 如果大于15把值设为0
					 */
					if(serviceRate == null || "".equals(serviceRate)){
						/**
						 *
						 * 如果为空定义它的值为0
						 *    
						 */
						bean.setServiceRate(BigDecimal.ZERO);
						combServiceRate.setSelectedIndex(0);
					}else{
						/**
						 * 
						 *   获取的数有可能是字符串   把字符串转换成BigDecimal类型
						 */
						BigDecimal big ;
						if(serviceRate instanceof String){
							big = new BigDecimal((String)serviceRate);
						}else if (serviceRate instanceof Integer){
							big = new BigDecimal((Integer)serviceRate);
						}else{
							big = (BigDecimal) serviceRate;
						}
						/**
						 * 
						 *     如果不为空则判断是值是否大于系统配置的值 如果大于则把值设为0
						 */
						
						if(big.compareTo(serviceFeeRate)>0){
							bean.setServiceRate(BigDecimal.ZERO);
							combServiceRate.setSelectedIndex(0);
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.servoceRate.message",serviceFeeRate.intValue()));
						}else{
							bean.setServiceRate(big);
						}
					}
				}
				@Override
				public void focusGained(FocusEvent e) {
					HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
					IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
					WaybillPanelVo bean = waybillBinder.getBean();
					serviceFeeRate = getServiceRate(bean);
				}
				private BigDecimal getServiceRate(WaybillPanelVo bean){
					ProductEntityVo productVo = bean.getProductCode();
					String receiveOrgCode = bean.getReceiveOrgCode();
					DefaultComboBoxModel serviceRateModel;
					BigDecimal serviceFeeRate = new BigDecimal(NumberConstants.NUMBER_15);
					if (StringUtils.isNotBlank(receiveOrgCode) && productVo != null) {
						// 调用接口读取装卸费费率
						ServiceFeeRateDto dto = null;
						if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
						{
							dto = waybillService.queryConfigurationParamsByOrgCode(receiveOrgCode,ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
						}else
						{
							dto = waybillService.queryConfigurationParamsByOrgCode(receiveOrgCode,ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
						}
				
						if (dto == null) {
							//throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFeeCalculate="));
							return serviceFeeRate;
						} else {
							// 判断是否存在装卸费费率
							if (dto.getServiceFeeRate() == null) {
								//throw new WaybillValidateException(dto.getMessage());
								return serviceFeeRate;
							} else {
								serviceFeeRate = dto.getServiceFeeRate();
							}
						}
						serviceRateModel = new DefaultComboBoxModel();
						
						if(!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
							serviceFeeRate = serviceFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_100));
						}
						
						for(int i = 0 ; i <= serviceFeeRate.intValue(); i++){
							serviceRateModel.addElement(i);
						}
					} else {
						serviceRateModel = new DefaultComboBoxModel();
						for(int i = 0 ; i < PictureWaybillEditUI.getFontsize().length; i++){
							serviceRateModel.addElement(PictureWaybillEditUI.getFontsize()[i]);
						}

					}
					combServiceRate.setModel(serviceRateModel);
					return serviceFeeRate;
				}
			});
		}
		// 其他费用合计
		label11 = new JLabel(i18n.get("foss.gui.creating.incrementPanel.otherCharge.label") + "：");
		add(label11, "27, 7, right, default");

		txtOtherCharge = new JTextFieldValidate();
		add(txtOtherCharge, "29, 7, fill, default");
		txtOtherCharge.setColumns(TEN);
		txtOtherCharge.setEnabled(false);
		// 安装费
		btnInstall = new JButton(
				i18n.get("foss.gui.creating.waybillEditUI.common.install"));
		add(btnInstall, "31, 7");
		btnInstall.setVisible(false);
		// 新增
		btnNew = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.new"));
		add(btnNew, "33, 7");

		// 删除
		btnDelete = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.delete"));
		add(btnDelete, "35, 7");

	}

	/**
	 * @return the ui
	 */
	public WaybillEditUI getUi() {
		return ui;
	}

	/**
	 * @param ui
	 *            the ui to set
	 */
	public void setUi(WaybillEditUI ui) {
		this.ui = ui;
	}

	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		// 初始化表格
		tblOther = new OtherFeeJXTable();
		// 设置表格数据模型
		tblOther.setModel(new WaybillOtherCharge());
		// 设置表格可以有滚动条
		tblOther.setAutoscrolls(true);
		// 禁止表格排序
		tblOther.setSortable(false);
		// 表格设置为不可编辑
		// 设置列可见状态
		tblOther.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tblOther.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) tblOther.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * 
	 * 初始化其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-6 下午06:40:00
	 */
	/*public void initData() {
		//查询增值费用
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(null));
		
		List<OtherChargeVo> voList = CommonUtils.getOtherChargeList(list);
			if (voList != null) {
				setChangeDetail(voList);
			}
		
	}*/
	
	/**
	 * 
	 * 初始化其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-6 下午06:40:00
	 */
	/*public void initDataPic() {
		//查询增值费用
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParamPic(null));
		List<OtherChargeVo> voList = CommonUtils.getOtherChargeListPic(list);
			if (voList != null) {
				setChangeDetailPic(voList);
			}
	}*/

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList) {
		WaybillOtherCharge changeInfoDetailTableModel = ((WaybillOtherCharge) tblOther.getModel());
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
		if (waybillPanelVo != null) {
			Common.getYokeCharge(waybillPanelVo, ui);
			// 加if判断是防止导入已开单的运单重新再加包装费，参见：BUG-10420
			// 获取计算前的预付金额
			BigDecimal prePayAmount = waybillPanelVo.getPrePayAmount();
			// 获取计算前的到付金额
			BigDecimal toPayAmount = waybillPanelVo.getToPayAmount();
			// 计算运费
			CalculateFeeTotalUtils.calculateFee(waybillPanelVo);
			// 重新设置预付金额
			waybillPanelVo.setPrePayAmount(prePayAmount);
			// 重新设置到付金额
			waybillPanelVo.setToPayAmount(toPayAmount);
		}
	}
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetailPic(List<OtherChargeVo> changeDetailList) {
		WaybillOtherCharge changeInfoDetailTableModel = ((WaybillOtherCharge) tblOther.getModel());
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
		if (waybillPanelVo != null) {
			Common.getYokeCharge(waybillPanelVo, ui);
			// 加if判断是防止导入已开单的运单重新再加包装费，参见：BUG-10420
			// 获取计算前的预付金额
			BigDecimal prePayAmount = waybillPanelVo.getPrePayAmount();
			// 获取计算前的到付金额
			BigDecimal toPayAmount = waybillPanelVo.getToPayAmount();
			// 计算运费
//			CalculateFeeTotalUtils.calculateFee(waybillPanelVo);
			// 重新设置预付金额
			waybillPanelVo.setPrePayAmount(prePayAmount);
			// 重新设置到付金额
			waybillPanelVo.setToPayAmount(toPayAmount);
		}
	}
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class WaybillOtherCharge extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据
		 */
		private List<OtherChargeVo> changeDetailList;

		/**
		 * 获得表格数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		/**
		 * 设置表格数据
		 * 
		 * @param changeDetailList
		 */
		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
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
		 * 设置单元格的值
		 */
		public void setValueAt(Object aValue, int row, int column) {
			List<OtherChargeVo> data = this.getData();

			/**
			 * 表格第一列名字是不能修改的
			 */
			if (column == 0) {
				return;
			}

			OtherChargeVo vo = data.get(row);

			/**
			 * vo is null
			 */
			if (vo == null) {
				return;
			}

			// 是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			/**
			 * 修改not allowed
			 */
			if (isUpdate == null) {
				isUpdate = Boolean.FALSE;
			}
			
			
			
			double money = 0;
			try {
				money = Double.parseDouble((String) aValue);
				/**
				 * 金额<=0
				 */
				/*if (money <= 0) {
					throw new Exception(i18n.get("foss.gui.creating.incrementPanel.exception.label"));
				}*/
			} catch (Exception e) {
				/**
				 * 输入金额非法
				 */
				money = -1;
				MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label"));
				return;
			}
			/**
			 * 保留2位小数
			 */
			BigDecimal b = BigDecimal.valueOf(money);
			/**
			 * 四舍五入
			 */
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal valueCal=b.setScale(2, BigDecimal.ROUND_HALF_UP);
			String moneyStr = Double.toString(f1);
			aValue = moneyStr;
			// 非法
			if (!isUpdate) {
				return;
			} else {
				/**
				 * set up money
				 */
				try {
				String oldMoney = vo.getMoney();

				double oldvalue = 0;
				try {
					oldvalue = Double.parseDouble(oldMoney);
				} catch (Exception e) {
					if(log.isErrorEnabled()){
						log.error(e);
					}
				}
				
				
				if(StringUtils.isNotEmpty(vo.getUpperLimit())&&StringUtils.isNotEmpty(vo.getLowerLimit()))
				{
					
					BigDecimal upperLimit=new BigDecimal(vo.getUpperLimit());
					BigDecimal lowerLimit=new BigDecimal(vo.getLowerLimit());
					//如果不是签收单原件返回，则不做上下限限制
					if(!PriceEntityConstants.PRICING_CODE_QS.equals(vo.getCode())){
						if(valueCal.compareTo(upperLimit)==1)
						{
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.max.value"));
							return;
						}
						else if(valueCal.compareTo(lowerLimit)==-1)
						{
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.min.value"));
							return;
						}	
					}				
				}
				if (waybillPanelVo != null) {
					waybillPanelVo.setOtherFee(waybillPanelVo.getOtherFee().subtract(BigDecimal.valueOf(oldvalue)).add(BigDecimal.valueOf(f1)));
					waybillPanelVo.setOtherFeeCanvas(waybillPanelVo.getOtherFee().toString());
				}
				vo.setMoney(moneyStr);
				
				} catch (Exception e) {
					/**
					 * 输入金额非法
					 */
					money = -1;
					MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label"));
					return;
				}
			}
			/**
			 * 正式提交修改的表格数据
			 */
			fireTableCellUpdated(row, column);
			if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
				String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
				String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
				if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(new BigDecimal(0)) > 0 
						&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(new BigDecimal(0)) > 0){
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
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		/**
		 * 获得数据
		 */
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				if(CollectionUtils.isNotEmpty(changeDetailList)){
					return changeDetailList.get(row).getChargeName();
				}else{
					return null;
				}				
			case 1:
				if(CollectionUtils.isNotEmpty(changeDetailList)){
					return changeDetailList.get(row).getMoney();
				}else{
					return null;
				}				
			default:
				return super.getValueAt(row, column);
			}

		}
	}

	/**
	 * getCombReturnBillType
	 * 
	 * @return
	 */
	public JComboBox getCombReturnBillType() {
		return combReturnBillType;
	}

	/**
	 * getCombRefundType
	 * 
	 * @return
	 */
	public JComboBox getCombRefundType() {
		return combRefundType;
	}

	/**
	 * getTxtInsuranceValue
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtInsuranceValue() {
		return txtInsuranceValue;
	}


	/**
	 * getTxtInsuranceRate
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtInsuranceRate() {
		return txtInsuranceRate;
	}

	/**
	 * getTblOther
	 * 
	 * @return
	 */
	public JXTable getTblOther() {
		return tblOther;
	}

	/**
	 * getTxtCashOnDelivery
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtCashOnDelivery() {
		return txtCashOnDelivery;
	}

	/**
	 * 
	 * 优惠编码
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午08:44:08
	 * @return
	 */
	public JTextFieldValidate getTxtPromotionNumber() {
		return txtPromotionNumber;
	}
	
	/**
	 * getChbSecrecy
	 * 
	 * @return
	 */
	public JCheckBox getChbSecrecy() {
		return chbSecrecy;
	}



	/**
	 * getTxtPayee
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPayee() {
		return txtPayee;
	}



	/**
	 * combPaymentMode getCombPaymentMode
	 * @return
	 */
	public JComboBox getCombPaymentMode() {
		return combPaymentMode;
	}
	
	/**
	 * getTxtBankAccount
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtBankAccount() {
		return txtBankAccount;
	}

	/**
	 * getTxtServiceCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtServiceCharge() {
		return txtServiceCharge;
	}

	/**
	 * getTxtOtherCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtOtherCharge() {
		return txtOtherCharge;
	}

	/**
	 * getBtnNew
	 * 
	 * @return
	 */
	public JButton getBtnNew() {
		return btnNew;
	}

	/**
	 * getBtnDelete
	 * 
	 * @return
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public JComboBox getCombActiveInfo() {
		return combActiveInfo;
	}

	public void setCombActiveInfo(JComboBox combActiveInfo) {
		this.combActiveInfo = combActiveInfo;
	}

	/**
	 * 其他费用table
	 * 
	 * @author shixiaowei
	 * 
	 */
	public class OtherFeeJXTable extends JXTable {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 该单元格是否可以编辑
		 */
		public boolean isCellEditable(int row, int column) {

			WaybillOtherCharge model = (WaybillOtherCharge) this.getModel();
			List<OtherChargeVo> data = model.getData();

			/**
			 * 表格第一列名字是不能修改的
			 */
			if (column == 0) {
				return false;
			}

			OtherChargeVo vo = data.get(row);

			// 数据不存在 也不允许修改
			if (vo == null) {
				return false;
			}

			// 是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			/**
			 * 默认情况下 不允许修改金额
			 */
			if (isUpdate == null) {
				isUpdate = Boolean.FALSE;
			}

			return isUpdate;
		}

	}

	/**
	 * @return the waybillPanelVo
	 */
	public WaybillPanelVo getWaybillPanelVo() {
		return waybillPanelVo;
	}

	/**
	 * @param waybillPanelVo
	 *            the waybillPanelVo to set
	 */
	public void setWaybillPanelVo(WaybillPanelVo waybillPanelVo) {
		this.waybillPanelVo = waybillPanelVo;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JLabel getLblNewLabel_1() {
		return lblNewLabel_1;
	}

	public void setLblNewLabel_1(JLabel lblNewLabel_1) {
		this.lblNewLabel_1 = lblNewLabel_1;
	}

	public JButton getBtnCalculate() {
		return btnCalculate;
	}

	public void setBtnCalculate(JButton btnCalculate) {
		this.btnCalculate = btnCalculate;
	}

	public JComboBox getCombServiceRate() {
		return combServiceRate;
	}

	public void setCombServiceRate(JComboBox combServiceRate) {
		this.combServiceRate = combServiceRate;
	}

	public JLabel getJlable() {
		return jlable;
	}

	public void setJlable(JLabel jlable) {
		this.jlable = jlable;
	}

	public JButton getBtnInstall() {
		return btnInstall;
	}

	public void setBtnInstall(JButton btnInstall) {
		this.btnInstall = btnInstall;
	}

	public WaybillPanelVo getIntellligenceBean() {
		return intellligenceBean;
	}

	public void setIntellligenceBean(WaybillPanelVo vo) {
		combReturnBillTypeTime=0;
		combPaymentModeTime=0;
		this.intellligenceBean = vo;
		/**
		 * @项目：智能开单项目
		 * @功能：保存操作该下拉框的时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午14:28
		 */
		if(intellligenceBean!=null&&intellligenceBean.getIbtg()!=null){
			combReturnBillType.addPopupMenuListener(new PopupMenuListener() {
				Date startTime=null;
				Date endTime=null;
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						startTime=new Date();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						if(intellligenceBean!=null&&startTime!=null){
							combReturnBillTypeTime=(endTime.getTime()-startTime.getTime())/NUM_1000+intellligenceBean.getIbtg().getCombReturnBillTypeTime();
							intellligenceBean.getIbtg().setCombReturnBillTypeTime(combReturnBillTypeTime);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					
				}	

			});
		}
		/**
		 * @项目：智能开单项目
		 * @功能：保存操作该下拉框的时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午14:28
		 */
		if(intellligenceBean!=null&&intellligenceBean.getIbtg()!=null){
			combPaymentMode.addPopupMenuListener(new PopupMenuListener() {
				Date startTime=null;
				Date endTime=null;
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						startTime=new Date();
					} catch (Exception e2) {
						if(log.isErrorEnabled()){
							log.error(e2);
						}
					}
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						if(intellligenceBean!=null&&startTime!=null){
							combPaymentModeTime=(endTime.getTime()-startTime.getTime())/NumberConstants.NUMBER_1000+intellligenceBean.getIbtg().getCombPaymentModeTime();
							intellligenceBean.getIbtg().setCombPaymentModeTime(combPaymentModeTime);
						}
					} catch (Exception e2) {
							if(log.isErrorEnabled()){
								log.error(e2);
							}
						}
				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
}