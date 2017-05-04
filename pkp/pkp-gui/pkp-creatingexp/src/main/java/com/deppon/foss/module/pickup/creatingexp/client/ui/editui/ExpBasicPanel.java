/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.deppon.foss.base.util.define.NumberConstants;
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
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.WaybillNoDocument;
import com.deppon.foss.module.pickup.creating.client.ui.editui.EnterKeyForReceiveOrg;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryCourierAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQuerySalesDepartmentAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq=1)
public class ExpBasicPanel  extends JPanel {
	 private static final String FIELDNAME = "fieldName";
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpBasicPanel.class); 

	private static final int SIX = 6;

//	private static final int NIVE = 9;

	private static final int TEN = 10;
	
	private static final long serialVersionUID = 165299221767565361L;

	/**
	 * 运单号
	 */
	@Bind("waybillNo")
	@FocusSeq(seq=3)
	private JTextFieldValidate txtWaybillNO;

	/**
	 * 上门接货
	 */
	@Bind("pickupToDoor")
	@FocusSeq(seq=4)
	private JCheckBox cboReceiveModel;
	
	/**
	 * 整车
	 */
	@Bind("isWholeVehicle")
	@FocusSeq(seq=5)
	private JCheckBox chbWholeVehicle;

	/**
	 * 经过到达部门
	 */
	//@Bind("isPassDept")
	@FocusSeq(seq=6)
	private JCheckBox chbPassDept;
	
	/**
	 * 快递员工号
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpQueryCourierAction.class)
	@FocusSeq(seq=5)
	private JButton btnQueryCourier;
	@Bind("expressEmpCode")
	@FocusSeq(seq=4)
	private JTextFieldValidate txtCourierNumber;

	/**
	 * 整车约车编号
	 */
	private JLabel lblVehicleNumber;
	//@Bind("vehicleNumber")
	private JTextFieldValidate txtVehicleNumber;	
	
	/**
	 * 导入整车约车编号
	 */
	//@ButtonAction(icon = "preview.png", shortcutKey = "", type = ImportVehicleAction.class)
	private JButton btnImportVehicle;
	
	/**
	 * 集中开单或者营业部开单
	 */
//	private String waybillType;
	private JLabel lblNewLabel;
	private JLabel lblTypeLable;
	private JLabel lblOrigianlWaybillNo;
	
	@Bind("returnType")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "开单类型") })
	@FocusSeq(seq=8)
	private JComboBox comboBoxReturnType;
	
	@Bind("originalWaybillNo")@FocusSeq(seq=9)
	private JTextFieldValidate txtOriginalWaybillNo;
	/**
	 * 收货部门
	 */
	@Bind("receiveOrgName")
	@FocusSeq(seq=8)
	private JTextFieldValidate txtSalesDepartment;
	
	public JTextFieldValidate getTxtSalesDepartment() {
		return txtSalesDepartment;
	}

	/** 
	 * 内部发货
	 */
	private JLabel internalDelivery;
	/**
	 * 内部发货类型
	 */
	@Bind("internalDeliveryType")
	public JComboBox internalDeliveryType;
	/**
	 * 工号
	 */
	@Bind("employeeNo")
	private JTextFieldValidate txtStaffNumber;

	@ButtonAction(icon = "preview.png", shortcutKey = "", type = ExpQuerySalesDepartmentAction.class)
	@FocusSeq(seq=9)
	private JButton btnQueryDept;
	//伙伴开单
	@Bind("partnerBilling")
	private JCheckBox partnerCheckBox;
	//伙伴名称
	@Bind("partnerName")
	@FocusSeq(seq=6)
	private JTextFieldValidate partnerName;
	private JLabel partnerLabel;
	private JLabel phomeLabel;
	//伙伴手机号
	@Bind("partnerPhone")
	@FocusSeq(seq=7)
	private JTextFieldValidate partnerPhone;
	
	
	/**
	 * 上门发货
	 */
	@Bind("homeDelivery")
	@FocusSeq(seq=5)
	private JCheckBox homeDelivery;
//	private JLabel label;
	
//	@Bind("specialOffer")
//	@FocusSeq(seq=10)	
//	private JComboBox cboSpecialOffer;

	public JCheckBox getHomeDelivery() {
		return homeDelivery;
	}

	public void setHomeDelivery(JCheckBox homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	/**
	 * Create the panel.
	 */
	public ExpBasicPanel(String waybillType) {
//		this.waybillType = waybillType;
		init();
		//收货部门Enter键监控
		EnterKeyForReceiveOrg enterDepartment=new EnterKeyForReceiveOrg(btnQueryDept);		
		txtSalesDepartment.addKeyListener(enterDepartment);
		//整车编号Enter键监控
//		EnterKeyForReceiveOrg enterVehicleNumber=new EnterKeyForReceiveOrg(btnImportVehicle);		
//		txtVehicleNumber.addKeyListener(enterVehicleNumber);
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.basicPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(17dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(24dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
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
				FormFactory.DEFAULT_ROWSPEC,}));

		/**
		 * 判断是集中开单还是营业部开单，根据不同属性，生成不同的界面
		 */
		JLabel label_2 = new JLabel("*"+i18n.get("foss.gui.creating.waybillEditUI.common.waybillNo")+"：");
		add(label_2, "2, 2, left, default");

		
		
		
		txtWaybillNO = new JTextFieldValidate();
		add(txtWaybillNO, "3, 2, 8, 1, fill, default");
		txtWaybillNO.setColumns(TEN);
//		NumberDocument numberDocument = new NumberDocument(TEN);
		WaybillNoDocument numberDocument = new WaybillNoDocument(TEN);
		txtWaybillNO.setDocument(numberDocument);
		
		partnerCheckBox = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.partnerCheckBox.label"));
		//删除复选框“伙伴开单”选项及伙伴名称、手机号(直接删除)
		//add(partnerCheckBox, "14, 2");
		partnerLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.partnerLabel.label")+":");
		add(partnerLabel, "2, 14, left, default");
		partnerLabel.setVisible(false);
		
		partnerName = new JTextFieldValidate();
		add(partnerName, "3, 14, 5, 1, fill, default");
		partnerName.setColumns(TEN);
		partnerName.setEditable(false);
		partnerName.setEnabled(false);
		partnerName.setVisible(false);
		
		phomeLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.phomeLabel.label")+":");
		add(phomeLabel, "10, 14, left, default");
		phomeLabel.setVisible(false);
		
		partnerPhone = new JTextFieldValidate();
		add(partnerPhone, "12, 14, 3, 1, fill, default");
		partnerPhone.setColumns(TEN);
		partnerPhone.setEditable(false);
		partnerPhone.setEnabled(false);
		partnerPhone.setVisible(false);
		// 限制手机号码只允许输入11位的数字
		MobileDocument number = new MobileDocument(NumberConstants.NUMBER_11);
		partnerPhone.setDocument(number);
		
//		label = new JLabel("优惠活动：");
//		add(label, "2, 4, left, default");
//		
//		cboSpecialOffer = new JComboBox();
//		add(cboSpecialOffer, "4, 4, 7, 1, fill, default");
		
//		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartment.label"));
//		add(lblNewLabel, "2, 4, left, default");
//		lblNewLabel.setVisible(false);
		
//		txtSalesDepartment = new JTextFieldValidate();
//		add(txtSalesDepartment, "4, 4, 7, 1, fill, default");
//		txtSalesDepartment.setColumns(TEN);
//		txtSalesDepartment.setVisible(false);

		cboReceiveModel = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
		add(cboReceiveModel, "2, 4");
		cboReceiveModel.setEnabled(false);
		
		chbWholeVehicle = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.wholeVehicle.label"));
		add(chbWholeVehicle, "4, 4");
		chbWholeVehicle.setEnabled(false);
		
		chbPassDept = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.passDept.label"));
		add(chbPassDept, "5, 4, 9, 1");
		chbPassDept.setEnabled(false);
		
		homeDelivery = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.homeDelivery.label"));
		add(homeDelivery, "14, 4");
		homeDelivery.setEnabled(false);

		JLabel label_3 = new JLabel(i18n.get("foss.gui.creating.basicPanel.courierNumber.label")+"：");
		//如果是合伙人营业部，快递员运单号不显示
		add(label_3, "2, 6, left, default");
		txtCourierNumber = new JTextFieldValidate();
		//如果是合伙人营业部，快递员运单号不显示
		add(txtCourierNumber, "3, 6, 12, 1, fill, default");
		txtCourierNumber.setColumns(TEN);
		
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.COURIER_CODE_EXP){
			txtCourierNumber.setEditable(true);
		}else{
			txtCourierNumber.setEditable(false);
		}
		
		btnQueryCourier = new JButton();
		//如果是合伙人营业部，快递员运单号不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.COURIER_CODE_EXP){
			add(btnQueryCourier, "16, 6, right, default");
		}
		btnQueryCourier.setMargin(new Insets(-2, 1, -2, 1));

		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartmentIn.label"));
		add(lblNewLabel, "2, 8, left, default");
		lblNewLabel.setVisible(true);
		
		txtSalesDepartment = new JTextFieldValidate();
		add(txtSalesDepartment, "3, 8, 8, 1, fill, default");
		txtSalesDepartment.setColumns(TEN);
		txtSalesDepartment.setVisible(true);
		txtSalesDepartment.setEditable(false);
		
		btnQueryDept = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		//若是合伙人营业部，收入部门系统自动带出，不可修改
		if(!BZPartnersJudge.IS_PARTENER){
			add(btnQueryDept, "12, 8, 3, 1");
		}
		btnQueryDept.setVisible(true);
		
		
		lblTypeLable = new JLabel(i18n.get("foss.gui.creating.waybillEditUI.common.openWaybillType"));
		add(lblTypeLable, "2, 10, left, default");
		
		comboBoxReturnType = new JComboBox();
		add(comboBoxReturnType, "3, 10, 5, 1, fill, default");
		
		lblOrigianlWaybillNo = new JLabel(i18n.get("foss.gui.creating.waybillEditUI.common.originalWaybillNo"));
		add(lblOrigianlWaybillNo, "10, 10");
		// 内部发货
		internalDelivery = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.internalDelivery.label"));

		internalDeliveryType = new JComboBox();
		// 工号
		JLabel staffNo = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.number.label"));

		txtStaffNumber = new JTextFieldValidate();
		
		LengthDocument document  = new LengthDocument(SIX);
		
		// 内部发货
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY_EXP){		
		  add(internalDelivery, "2, 12, left, default");
		  add(internalDeliveryType, "3, 12, 5, 1, fill, default");
		  add(staffNo, "10, 12, left, default");
		  add(txtStaffNumber, "12, 12, 3, 1, fill, default");
		  txtStaffNumber.setDocument(document);
		}
		
		txtOriginalWaybillNo = new JTextFieldValidate();
		add(txtOriginalWaybillNo, "12, 10, 3, 1, fill, default");
		txtOriginalWaybillNo.setColumns(TEN);
		NumberDocument numberDocument2 = new NumberDocument(TEN);
		txtOriginalWaybillNo.setDocument(numberDocument2);
		txtOriginalWaybillNo.setEnabled(false);
		//btnImportVehicle.setVisible(false);
	}

	
//	/**
//	 * 
//	 * 集中开单
//	 * @author 025000-FOSS-helong
//	 * @date 2012-12-11 上午11:32:49
//	 */
//	private void focusWaybill()
//	{
//		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartment.label"));
//		add(lblNewLabel, "2, 2, left, default");
//		
//		txtSalesDepartment = new JTextFieldValidate();
//		add(txtSalesDepartment, "4, 2, 10, 1, fill, default");
//		txtSalesDepartment.setColumns(TEN);
//		txtSalesDepartment.setEditable(true);
//		
//		btnQueryDept = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
//		add(btnQueryDept, "14, 2, 7, 1");
//		
//		JLabel label2 = new JLabel("*"+i18n.get("foss.gui.creating.waybillEditUI.common.waybillNo")+"：");
//		add(label2, "2, 4, left, default");
//
//		txtWaybillNO = new JTextFieldValidate();
//		add(txtWaybillNO, "4, 4, 10, 1, fill, default");
//		txtWaybillNO.setColumns(TEN);
//		NumberDocument numberDocument = new NumberDocument(NIVE);
//		txtWaybillNO.setDocument(numberDocument);
//
//		cboReceiveModel = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
//		add(cboReceiveModel, "2, 6");
//		cboReceiveModel.setVisible(true);
//		
//		chbWholeVehicle = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.wholeVehicle.label"));
//		add(chbWholeVehicle, "4, 6");
//		chbWholeVehicle.setVisible(false);
//		
//		chbPassDept = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.passDept.label"));
//		add(chbPassDept, "6, 6, 11, 1");
//		chbPassDept.setVisible(false);
//
//		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.basicPanel.driverNumber.label")+"：");
//		add(label3, "2, 8, left, default");
//
//		txtDriverNumber = new JTextFieldValidate();
//		add(txtDriverNumber, "4, 8, 10, 1, fill, default");
//		txtDriverNumber.setColumns(TEN);
//		NumberDocument driverNumber = new NumberDocument(SIX);
//		txtDriverNumber.setDocument(driverNumber);
//
//		
//		lblVehicleNumber = new JLabel(i18n.get("foss.gui.creating.basicPanel.vehicleNumber.label")+"：");
//		add(lblVehicleNumber, "2, 10, left, default");
//		lblVehicleNumber.setVisible(false);
//		
//		txtVehicleNumber = new JTextFieldValidate();
//		add(txtVehicleNumber, "4, 10, 10, 1, fill, default");
//		txtVehicleNumber.setColumns(TEN);
//		txtVehicleNumber.setVisible(false);
//		
//		btnImportVehicle = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
//		add(btnImportVehicle, "14, 10, 7, 1");
//		btnImportVehicle.setVisible(false);
//	}
//	
//	/**
//	 * 
//	 * 营业部开单
//	 * @author 025000-FOSS-helong
//	 * @date 2012-12-11 上午11:32:57
//	 */
//	private void salesDepartment()
//	{
//		JLabel label_2 = new JLabel("*"+i18n.get("foss.gui.creating.waybillEditUI.common.waybillNo")+"：");
//		add(label_2, "2, 2, left, default");
//
//		
//		
//		
//		txtWaybillNO = new JTextFieldValidate();
//		add(txtWaybillNO, "4, 2, 7, 1, fill, default");
//		txtWaybillNO.setColumns(TEN);
//		NumberDocument numberDocument = new NumberDocument(NIVE);
//		txtWaybillNO.setDocument(numberDocument);
//		
//		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartment.label"));
//		add(lblNewLabel, "2, 4, left, default");
//		lblNewLabel.setVisible(false);
//		
//		txtSalesDepartment = new JTextFieldValidate();
//		add(txtSalesDepartment, "4, 4, 7, 1, fill, default");
//		txtSalesDepartment.setColumns(TEN);
//		txtSalesDepartment.setVisible(false);
//
//		cboReceiveModel = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
//		add(cboReceiveModel, "2, 6");
//		
//		chbWholeVehicle = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.wholeVehicle.label"));
//		add(chbWholeVehicle, "4, 6");
//		
//		chbPassDept = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.passDept.label"));
//		add(chbPassDept, "6, 6, 10, 1");
//		chbPassDept.setEnabled(false);
//
//		JLabel label_3 = new JLabel(i18n.get("foss.gui.creating.basicPanel.driverNumber.label")+"：");
//		add(label_3, "2, 8, left, default");
//
//		txtDriverNumber = new JTextFieldValidate();
//		add(txtDriverNumber, "4, 8, 13, 1, fill, default");
//		txtDriverNumber.setColumns(TEN);
//		txtDriverNumber.setEditable(false);
//		NumberDocument driverNumber = new NumberDocument(SIX);
//		txtDriverNumber.setDocument(driverNumber);
//
//		
//		lblVehicleNumber = new JLabel(i18n.get("foss.gui.creating.basicPanel.vehicleNumber.label")+"：");
//		add(lblVehicleNumber, "2, 10, left, default");
//		lblVehicleNumber.setVisible(false);
//		
//		txtVehicleNumber = new JTextFieldValidate();
//		add(txtVehicleNumber, "4, 10, 7, 1, fill, default");
//		txtVehicleNumber.setColumns(TEN);
//		txtVehicleNumber.setVisible(false);
//		
//		btnImportVehicle = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
//		add(btnImportVehicle, "12, 10, 5, 1");
//		btnImportVehicle.setVisible(false);
//	}




//public JComboBox getCboSpecialOffer() {
//		return cboSpecialOffer;
//	}



	/**
	 * getCboReceiveModel
	 * @return
	 */
	public JCheckBox getCboReceiveModel() {
		return cboReceiveModel;
	}

	/**
	 * getTxtDriverNumber
	 * @return
	 */
	public JTextFieldValidate getTxtCourierNumber() {
		return txtCourierNumber;
	}
	
	/**
	 * getLblVehicleNumber
	 * @return
	 */
	public JLabel getLblVehicleNumber() {
		return lblVehicleNumber;
	}
	
	/**
	 * getTxtVehicleNumber
	 * @return
	 */
	public JTextFieldValidate getTxtVehicleNumber() {
		return txtVehicleNumber;
	}
	
	/**
	 * getBtnImportVehicle
	 * @return
	 */
	public JButton getBtnImportVehicle() {
		return btnImportVehicle;
	}
	
	public JCheckBox getChbWholeVehicle() {
		return chbWholeVehicle;
	}
	
	public JCheckBox getChbPassDept() {
		return chbPassDept;
	}

	/**
	 * @return the txtWaybillNO
	 */
	public JTextFieldValidate getTxtWaybillNO() {
		return txtWaybillNO;
	}

	/**
	 * @param txtWaybillNO the txtWaybillNO to set
	 */
	public void setTxtWaybillNO(JTextFieldValidate txtWaybillNO) {
		this.txtWaybillNO = txtWaybillNO;
	}

	public JComboBox getComboBoxReturnType() {
		return comboBoxReturnType;
	}

	public JTextFieldValidate getTxtOriginalWaybillNo() {
		return txtOriginalWaybillNo;
	}
	public JLabel getInternalDelivery() {
		return internalDelivery;
	}

	public void setInternalDelivery(JLabel internalDelivery) {
		this.internalDelivery = internalDelivery;
	}

	public JComboBox getInternalDeliveryType() {
		return internalDeliveryType;
	}

	public void setInternalDeliveryType(JComboBox internalDeliveryType) {
		this.internalDeliveryType = internalDeliveryType;
	}

	public JTextFieldValidate getTxtStaffNumber() {
		return txtStaffNumber;
	}

	public void setTxtStaffNumber(JTextFieldValidate txtStaffNumber) {
		this.txtStaffNumber = txtStaffNumber;
	}
//	public void setCboSpecialOffer(JComboBox cboSpecialOffer) {
//		this.cboSpecialOffer = cboSpecialOffer;
//	}

	public JCheckBox getPartnerCheckBox() {
		return partnerCheckBox;
	}

	public void setPartnerCheckBox(JCheckBox partnerCheckBox) {
		this.partnerCheckBox = partnerCheckBox;
	}

	public JTextField getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(JTextFieldValidate partnerName) {
		this.partnerName = partnerName;
	}

	public JTextField getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(JTextFieldValidate partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	
	
	public JLabel getPartnerLabel() {
		return partnerLabel;
	}

	public void setPartnerLabel(JLabel partnerLabel) {
		this.partnerLabel = partnerLabel;
	}

	public JLabel getPhomeLabel() {
		return phomeLabel;
	}

	public void setPhomeLabel(JLabel phomeLabel) {
		this.phomeLabel = phomeLabel;
	}

	public JButton getBtnQueryCourier() {
		return btnQueryCourier;
	}

	public void setBtnQueryCourier(JButton btnQueryCourier) {
		this.btnQueryCourier = btnQueryCourier;
	}

	
	
}
