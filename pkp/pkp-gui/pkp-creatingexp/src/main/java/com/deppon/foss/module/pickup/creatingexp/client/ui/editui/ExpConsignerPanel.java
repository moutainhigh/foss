/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTextField;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.LetterDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryConsignerAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryFeeBurdenDeptAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq=2)
public class ExpConsignerPanel  extends JPanel {

	private static final int TEN = 10;
	
	private static final int LEN100 = 100;


	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpConsignerPanel.class);

	/**
	 * 手机号码
	 */
	@Bind("deliveryCustomerMobilephone")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtConsignerMobile;

	/**
	 * 电话号码
	 */
	@Bind("deliveryCustomerPhone")
	@FocusSeq(seq=2)
	private JTextFieldValidate txtConsignerPhone;
	
	
	/**
	 * 发货客户名称
	 */
	@Bind("deliveryCustomerName")
	@FocusSeq(seq=3)
	private JTextFieldValidate txtConsigerName;
	
	/**
	 * 客户编码
	 */
	@Bind("deliveryCustomerCode")
	private JTextFieldValidate txtDeliveryCustomerCode;

	/**
	 * 发货人区域
	 */
	@Bind("deliveryCustomerArea")
	@FocusSeq(seq=5)
	JAddressField txtConsignerArea;
	
	/**
	 *  发货联系人
	 */
	JLabel lblLinkMan;
	
	/**
	 * 查询发货客户
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "F9", type = ExpQueryConsignerAction.class)
	private JButton btnQuery;

	private JLabel label2;
	private JPanel panel;
	
	/**
	 * 发货联系人
	 */
	@Bind("deliveryCustomerContact")
	@FocusSeq(seq=4)
	private JTextFieldValidate txtConsignerLinkMan;
	
	/**
	 * 原定义的发货联系人未使用，这里做内部带货费用承担部门（未改变命名）
	 */	
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpQueryFeeBurdenDeptAction.class)
	private JButton btnConsignerDept;
	
	/**
	 * 发货人地址
	 */
	@Bind("deliveryCustomerAddress")
	@FocusSeq(seq=6)
	private JTextField txtConsignerAddress;
	

	/**
	 * 发货人工号
	 */
	@Bind("deliveryEmployeeNo")
	@FocusSeq(seq=7)
	private JTextField txtDeliveryEmployeeNo;
	
	/**
	 * 发货人地址
	 */
	@Bind("deliveryCustomerAddressNote")
	private JXTextField txtConsignerAddressNote;
	
	/**
	 * 俩个Label主要是标识
	 * lblcenteralsett:是否统一结算
	 * lblNewLabel_2:合同部门
	 */
	private JLabel lblcenteralsett;
	private JLabel lblNewLabel_2;
	/**
	 * 快递发货客户：
	 * txtStartCentralizedSettlement：是否统一结算
	 * txtStartContractOrgCode：合同部门
	 */
	@Bind("startCentralizedSettlement")
	private JTextField txtStartCentralizedSettlement;
	@Bind("startContractOrgName")
	private JTextField txtStartContractOrgCode;
	
	/**
	 * Create the panel.
	 */
	public ExpConsignerPanel() {

		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:37:48
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.consignerPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(14dlu;default):grow"),
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
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//发货客户手机
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.consignerMobile.label")+"：");
		add(label1, "2, 1, left, default");

		txtConsignerMobile = new JTextFieldValidate();
		add(txtConsignerMobile, "4, 1, fill, default");
		txtConsignerMobile.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		MobileDocument numberDocument = new MobileDocument(NumberConstants.NUMBER_11);
		txtConsignerMobile.setDocument(numberDocument);

		//发货客户电话
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consignerPanel.consignerPhone.label")+"：");
		add(lblNewLabel, "6, 1, left, default");

		txtConsignerPhone = new JTextFieldValidate();
		add(txtConsignerPhone, "8, 1, fill, default");
		txtConsignerPhone.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		NumberDocument telePhoneDocument = new NumberDocument(NumberConstants.NUMBER_20);
		txtConsignerPhone.setDocument(telePhoneDocument);
		
		//发货客户姓名
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerName.label")+"：");
		add(lblNewLabel1, "10, 1, right, default");
		
		txtConsigerName = new JTextFieldValidate();
		add(txtConsigerName, "12, 1, 5, 1, fill, default");
		txtConsigerName.setColumns(TEN);
		
		//查询
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		add(btnQuery, "17, 1");
		
		//发货客户编码
		label2 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")+"：");
		add(label2, "19, 1, right, default");
		
		txtDeliveryCustomerCode = new JTextFieldValidate();
		add(txtDeliveryCustomerCode, "21, 1, fill, default");
		txtDeliveryCustomerCode.setColumns(TEN);
		txtDeliveryCustomerCode.setEditable(false);

		//发货客户联系人
		lblLinkMan = new JLabel("*"+i18n.get("foss.gui.creating.consignerPanel.consignerLinkMan.label")+"：");
		add(lblLinkMan, "2, 3, left, default");
		
		panel = new JPanel();
		add(panel, "4, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsignerLinkMan = new JTextFieldValidate();
		panel.add(txtConsignerLinkMan);
		txtConsignerLinkMan.setColumns(TEN);
		
		//查询
		btnConsignerDept = new JButton();
		btnConsignerDept.setVisible(false);
		btnConsignerDept.setMargin(new Insets(-2, 1, -2, 1));
		btnConsignerDept.setBorderPainted(false);
		panel.add(btnConsignerDept);

		//发货客户地址
		JLabel lblNewLabel2 = new JLabel("*"+i18n.get("foss.gui.creating.consignerPanel.consignerAddress.label")+"：");
		add(lblNewLabel2, "6, 3, left, default");

		txtConsignerArea = new JAddressField();
		txtConsignerArea.setText("");
		add(txtConsignerArea, "8, 3, 2, 1, fill, default");
		LetterDocument letterDocument=new LetterDocument();
		txtConsignerArea.setDocument(letterDocument);	
		
		txtConsignerAddress = new JTextField();
		add(txtConsignerAddress, "10, 3, 6, 1, fill, default");
		txtConsignerAddress.setColumns(TEN);
		LengthDocument consignerDocument = new LengthDocument(LEN100);
		txtConsignerAddress.setDocument(consignerDocument);
		
		txtConsignerAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(100);
		add(txtConsignerAddressNote, "16, 3, 3, 1, fill, default");
		txtConsignerAddressNote.setColumns(10);
		txtConsignerAddressNote.setDocument(consigneeDocumentNote);
		
		//发货人工号
		JLabel lblNewLabelDeliveryEmpCode = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryEmployeeNo.label")+"：");
		add(lblNewLabelDeliveryEmpCode, "19, 3, left, default");
		
		
		//发货人工号
		txtDeliveryEmployeeNo= new JTextField();
		add(txtDeliveryEmployeeNo, "21, 3, fill, default");
		txtDeliveryEmployeeNo.setColumns(TEN);
		LengthDocument txtDeliveryDoc = new LengthDocument(LEN100);
		txtDeliveryEmployeeNo.setDocument(txtDeliveryDoc);
		
		lblcenteralsett = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblcenteralsett, "2, 5, right, default");
		
		txtStartCentralizedSettlement = new JTextField();
		txtStartCentralizedSettlement.setEnabled(false);
		txtStartCentralizedSettlement.setEditable(false);
		add(txtStartCentralizedSettlement, "4, 5, fill, default");
		txtStartCentralizedSettlement.setColumns(TEN);
		
		lblNewLabel_2 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecontractcode"));
		add(lblNewLabel_2, "6, 5, right, default");
		
		txtStartContractOrgCode = new JTextField();
		txtStartContractOrgCode.setEnabled(false);
		txtStartContractOrgCode.setEditable(false);
		add(txtStartContractOrgCode, "8, 5, fill, default");
		txtStartContractOrgCode.setColumns(TEN);
	}
	

	public JLabel getLblLinkMan() {
		return lblLinkMan;
	}

	public JTextFieldValidate getTxtConsignerLinkMan() {
		return txtConsignerLinkMan;
	}
	
	
	public JAddressField getTxtConsignerArea() {
		return txtConsignerArea;
	}
	
	public JButton getBtnConsignerDept() {
		return btnConsignerDept;
	}
	
	public JTextField getTxtDeliveryCustomerCode() {
		return txtDeliveryCustomerCode;
	}
	
	/**
     * 获取发货人手机
	 */
	public JTextFieldValidate getTxtConsignerMobile(){
		return txtConsignerMobile;
	}
	
	/**
     * 获取发货人电话
	 */
	public JTextFieldValidate getTxtConsignerPhone(){
		return txtConsignerPhone;
	}
	
	/**
	 * 
	 * 查询发货客户
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:29:58
	 * @return
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}

	public JXTextField getTxtConsignerAddressNote() {
		return txtConsignerAddressNote;
	}

	public void setTxtConsignerAddressNote(JXTextField txtConsignerAddressNote) {
		this.txtConsignerAddressNote = txtConsignerAddressNote;
	}
	public JTextFieldValidate getTxtConsigerName() {
		return txtConsigerName;
	}

	public void setTxtConsigerName(JTextFieldValidate txtConsigerName) {
		this.txtConsigerName = txtConsigerName;
	}

	public JTextField getTxtDeliveryEmployeeNo() {
		return txtDeliveryEmployeeNo;
	}

	public void setTxtDeliveryEmployeeNo(JTextField txtDeliveryEmployeeNo) {
		this.txtDeliveryEmployeeNo = txtDeliveryEmployeeNo;
	}
	
	
}
