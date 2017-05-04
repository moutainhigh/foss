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
import javax.swing.text.AbstractDocument.Content;

import oracle.sql.NUMBER;

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
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.LetterDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryConsigneeAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryConsigneeDeptAction;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */@ContainerSeq(seq=3)
public class ExpConsigneePanel  extends JPanel {

	private static final int TEN = 10;
	
	private static final int LEN100 = 100;

	private static final long serialVersionUID = 1L;

	private static final int ELEVEN = 11;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpConsigneePanel.class);
	
	/**
	 * 手机号码
	 */
	@Bind("receiveCustomerMobilephone")
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtConsigneeMobile;
	/**
	 * 查询收货客户
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "F10", type = ExpQueryConsigneeAction.class)
	private JButton btnQuery;
	/**
	 * 电话号码
	 */
	@Bind("receiveCustomerPhone")
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtConsigneePhone;

	/**
	 * 联系人
	 */
	@Bind("receiveCustomerContact")
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtConsigneeLinkMan;

	/**
	 * 地址
	 */
	@Bind("receiveCustomerAddress")
	@FocusSeq(seq = 6)
	private JTextFieldValidate txtConsigneeAddress;

	/**
	 * 行政区域
	 */
	@Bind("receiveCustomerArea")  //加上绑定是为了实现地址匹配的联动
	@FocusSeq(seq = 5)
	private JAddressFieldForExp txtConsigneeArea;

	/**
	 * 收货客户名称收货客户名称
	 */
	@Bind("receiveCustomerName")
	@FocusSeq(seq = 3)
	private JTextField txtReceiveCustomerName;
	
	/**
	 * 收货客户编码
	 */
	@Bind("receiveCustomerCode")
	private JTextField txtReceiveCustomerCode;
	private JLabel label2;
	

	private JLabel label5;

	private JLabel lblLinkMan;
	private JLabel lblNewLabel;
	private JPanel panel;
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpQueryConsigneeDeptAction.class)
	private JButton btnConsigneeDept;
	
	/**
	 * 地址
	 */
	@Bind("receiveCustomerAddressNote")
	private JXTextField txtConsigneeAddressNote;

	/**
	 * 快递-收货客户：
	 * @author 200945
	 *【 是否统一结算】【合同部门】
	 */
	private JLabel lblcenteralsett;
	@Bind("arriveCentralizedSettlement")
	private JTextField txtArriveCentralizedSettlement;
	private JLabel label_1;
	@Bind("arriveContractOrgName")
	private JTextField txtArriveContractOrgCode;

	/**
	 * Create the panel.
	 */
	public ExpConsigneePanel(ExpWaybillEditUI ui) {
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:36:01
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.consigneePanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(59dlu;default):grow"),
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

		//收货客户手机
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.consigneeMobile.label")+"：");
		add(label1, "2, 1, left, default");

		txtConsigneeMobile = new JTextFieldValidate();
		add(txtConsigneeMobile, "4, 1, fill, default");
		txtConsigneeMobile.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		MobileDocument numberDocument = new MobileDocument(ELEVEN);
		txtConsigneeMobile.setDocument(numberDocument);

		//收货客户电话
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.consigneePhone.label")+"：");
		add(label3, "6, 1, left, default");

		txtConsigneePhone = new JTextFieldValidate();
		add(txtConsigneePhone, "8, 1, fill, default");
		txtConsigneePhone.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		NumberDocument telePhoneDocument = new NumberDocument(NumberConstants.NUMBER_20);
		txtConsigneePhone.setDocument(telePhoneDocument);
		
		//收货客户名称
		label2 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.receiveCustomerName.label")+"：");
		add(label2, "10, 1, right, default");
		
		txtReceiveCustomerName = new JTextField();
		add(txtReceiveCustomerName, "12, 1, fill, default");
		txtReceiveCustomerName.setColumns(TEN);
		
		//查询
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		add(btnQuery, "13, 1");
		
		//收货客户编码
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consigneePanel.receiveCustomerCode.label")+"：");
		add(lblNewLabel, "15, 1, right, default");
		
		txtReceiveCustomerCode = new JTextField();
		add(txtReceiveCustomerCode, "17, 1, fill, default");
		txtReceiveCustomerCode.setColumns(TEN);
		txtReceiveCustomerCode.setEditable(false);

		//收货联系人
		lblLinkMan = new JLabel("*"+i18n.get("foss.gui.creating.consigneePanel.consigneeLinkMan.label")+"： ");
		add(lblLinkMan, "2, 3, left, default");
		
		panel = new JPanel();
		add(panel, "4, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsigneeLinkMan = new JTextFieldValidate();
		panel.add(txtConsigneeLinkMan);
		txtConsigneeLinkMan.setColumns(TEN);
		
		//查询
		btnConsigneeDept = new JButton();
		panel.add(btnConsigneeDept);
		btnConsigneeDept.setVisible(false);
		btnConsigneeDept.setMargin(new Insets(-2, 1, -2, 1));
		btnConsigneeDept.setBorderPainted(false);

		//收货人地址
		label5 = new JLabel("*"+i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.label")+"：");
		add(label5, "6, 3, left, default");

		txtConsigneeArea = new JAddressFieldForExp();
		add(txtConsigneeArea, "8, 3, 4, 1, fill, default");
		LetterDocument letterDocument=new LetterDocument();
		txtConsigneeArea.setDocument(letterDocument);		
		
		txtConsigneeAddress = new JTextFieldValidate();
		add(txtConsigneeAddress, "12, 3, 4, 1");
		txtConsigneeAddress.setColumns(TEN);
		LengthDocument consigneeDocument = new LengthDocument(LEN100);
		txtConsigneeAddress.setDocument(consigneeDocument);
		
		txtConsigneeAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(NumberConstants.NUMBER_100);
		add(txtConsigneeAddressNote, "16, 3, 2, 1, fill, default");
		txtConsigneeAddressNote.setColumns(TEN);
		txtConsigneeAddressNote.setDocument(consigneeDocumentNote);
		
		lblcenteralsett = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblcenteralsett, "2, 5, right, default");
		
		txtArriveCentralizedSettlement = new JTextField();
		txtArriveCentralizedSettlement.setEnabled(false);
		txtArriveCentralizedSettlement.setEditable(false);
		add(txtArriveCentralizedSettlement, "4, 5, fill, default");
		txtArriveCentralizedSettlement.setColumns(TEN);
		
		label_1 = new JLabel(i18n.get("foss.guicreating.consigneePanel.lblarrivecontractcode"));
		add(label_1, "6, 5, right, default");
		
		txtArriveContractOrgCode = new JTextField();
		txtArriveContractOrgCode.setEnabled(false);
		txtArriveContractOrgCode.setEditable(false);
		add(txtArriveContractOrgCode, "8, 5, fill, default");
		txtArriveContractOrgCode.setColumns(TEN);

	}

	public JTextFieldValidate getTxtConsigneeLinkMan() {
		return txtConsigneeLinkMan;
	}

	public JLabel getLblLinkMan() {
		return lblLinkMan;
	}
	
	public JAddressFieldForExp getTxtConsigneeArea() {
		return txtConsigneeArea;
	}
	
	public JButton getBtnConsigneeDept() {
		return btnConsigneeDept;
	}

	
	/**
	 * @return the txtConsigneeAddress .
	 */
	public JTextFieldValidate getTxtConsigneeAddress() {
		return txtConsigneeAddress;
	}
	
	public JTextField getTxtReceiveCustomerCode() {
		return txtReceiveCustomerCode;
	}

	
	/**
	 * @return the txtConsigneeMobile .
	 */
	public JTextFieldValidate getTxtConsigneeMobile() {
		return txtConsigneeMobile;
	}

	
	/**
	 * @return the txtConsigneePhone .
	 */
	public JTextFieldValidate getTxtConsigneePhone() {
		return txtConsigneePhone;
	}

	
	/**
	 * @return the txtReceiveCustomerName .
	 */
	public JTextField getTxtReceiveCustomerName() {
		return txtReceiveCustomerName;
	}
	
	/**
	 * 
	 * 查询收货客户
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:28:44
	 * @return
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JXTextField getTxtConsigneeAddressNote() {
		return txtConsigneeAddressNote;
	}

	public void setTxtConsigneeAddressNote(JXTextField txtConsigneeAddressNote) {
		this.txtConsigneeAddressNote = txtConsigneeAddressNote;
	}
}
