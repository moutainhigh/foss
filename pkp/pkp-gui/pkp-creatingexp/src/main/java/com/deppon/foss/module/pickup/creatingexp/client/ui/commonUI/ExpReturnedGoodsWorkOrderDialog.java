package com.deppon.foss.module.pickup.creatingexp.client.ui.commonUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ExpReturnedGoodsWorkOrderDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_127 = 127;

	private static final int NUM_157 = 157;

	private static final int NUM_185 = 185;

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpReturnedGoodsWorkOrderDialog.class);

	/**
	 * 工单编号
	 */
	private JLabel workOrder;
	/**
	 * 工单编号值
	 */
	private JTextField workOrderValue;
	/**
	 * 返货原因
	 */
	private JLabel returnGoodsReason;
	/**
	 * 返货原因值
	 */
	private JTextArea returnGoodsReasonValue;
	/**
	 * 返货类型
	 */
	private JLabel returnGoodsType;
	/**
	 * 返货类型值
	 */
	private JTextField returnGoodsTypeVaule;
	/**
	 * 代收货款
	 */
	private JLabel goodsPayment;
	/**
	 * 代收货款值
	 */
	private JTextField goodsPaymentValue;
	/**
	 * 保价费
	 */
	private JLabel insurance;
	/**
	 * 保价费值
	 */
	private JTextField insuranceValue;
	/**
	 * 收货人姓名
	 */
	private JLabel receiveCustomerName;
	/**
	 * 收货人姓名值
	 */
	private JTextField receiveCustomerNameValue;
	/**
	 * 收货人手机
	 */
	private JLabel receiveCustomerMobilephone;
	/**
	 * 收货人手机值
	 */
	private JTextField receiveCustomerMobilephoneValue;
	/**
	 * 收货人电话
	 */
	private JLabel receiveCustomerPhone;
	/**
	 * 收货人电话值
	 */
	private JTextField receiveCustomerPhoneValue;
	/**
	 * 收货人地址
	 */
	private JLabel receiveCustomerAddress;
	/**
	 * 收货人地址值
	 */
	private JTextArea receiveCustomerAddressValue;
	/**
	 * 调查内容
	 */
	private JLabel investigationContent;
	/**
	 * 调查内容值
	 */
	private JTextArea investigationContentValue;
	/**
	 * 调查内容值
	 */
	private JButton closeButton;
	
	public ExpReturnedGoodsWorkOrderDialog(CrmReturnedGoodsDto crmDto){
		init();
		initData(crmDto);
		// 设置模态
		setModal(true);
	}
	/**
	 * 初始化数据
	 * @param crmDto
	 */
	private void initData(CrmReturnedGoodsDto crmDto){
		workOrderValue.setText(crmDto.getDealnumber() == null ? "" : crmDto.getDealnumber());
		receiveCustomerNameValue.setText(crmDto.getReturnManReceive() == null ? "" : crmDto.getReturnManReceive());
		returnGoodsTypeVaule.setText(returnGoodsTransformation(crmDto.getReturnType()));
		receiveCustomerMobilephoneValue.setText(crmDto.getReturnPhoneReceive() == null ? "" : crmDto.getReturnPhoneReceive());
		receiveCustomerPhoneValue.setText(crmDto.getReturnTelReceive() == null ? "" : crmDto.getReturnTelReceive());
		insuranceValue.setText(crmDto.getReturnMoneyInsured() == null ? "" : crmDto.getReturnMoneyInsured().toString());
		goodsPaymentValue.setText(crmDto.getReturnMoneyReceive() == null ? "" : crmDto.getReturnMoneyReceive().toString());
		returnGoodsReasonValue.setText(returnGoodsTransformation(crmDto.getReturnReason()));
		receiveCustomerAddressValue.setText(assemblyAddress(crmDto));
		investigationContentValue.setText(crmDto.getReturnSurvey() == null ? "" : crmDto.getReturnSurvey());
	}
	/**
	 * 拼装数据
	 *
	 */
/*	private String assemblyData(String data){
		StringBuffer sb = new StringBuffer();
		int leg = data.length()/33;
		int start = 0;
		int end = 33;
		if(leg > 0){
			sb.append("<html>");
			for(int i = 0 ; i < leg ; i++){
				if(end > data.length()){
					end = data.length() - 1;
				}
				if(end > data.length()){
					sb.append(data.substring(start, end));
				}else{
					sb.append(data.substring(start, end) + "<br>");
				}
				start = end;
				end = end + 32;
			}
			sb.append("</html>");
		}
		return sb.toString();
	}*/
	/**
	 * 拼装返货地址
	 *
	 */
	private String assemblyAddress(CrmReturnedGoodsDto crmDto){
		StringBuffer sb = new StringBuffer();
		sb.append("");
		String returnProvince = crmDto.getReturnProvince();
		String returnCity = crmDto.getReturnCity();
		String returnArea = crmDto.getReturnArea();
		String returnDetailaddress = crmDto.getReturnDetailaddress();
		if(returnProvince != null && !"".equals(returnProvince)){
			sb.append(returnProvince);
			sb.append("-");
		}
		if(returnCity != null && !"".equals(returnCity.trim())){
			sb.append(returnCity);
			sb.append("-");
		}
		if(returnArea != null && !"".equals(returnArea.trim())){
			sb.append(returnArea);
			sb.append("-");
		}
		if(returnDetailaddress != null && !"".equals(returnDetailaddress)){
			sb.append(returnDetailaddress);
		}
		
		return sb.toString();
	}
	
	/**
	 * 返货类型cod转换成汉字
	 *
	 */
	private String returnGoodsTransformation(String code){
		String value = "";
		if(code != null && !"".equals(code.trim())){
			if(WaybillConstants.RETURNTYPE_SAME_CITY.equals(code)){
				//同城转寄
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.same");
			}
			if(WaybillConstants.RETURNTYPE_OTHER_CITY.equals(code)){
				//非同城转寄
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.other");
			}
			if(WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(code)){
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.refuse");
			}
			if(WaybillConstants.RETURNTYPE_CONTINUE_SENDING.equals(code)){
				//继续派送
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sending");
			}
			if(WaybillConstants.RETURNTYPE_DETAINED_GOODS.equals(code)){
				//滞留件
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.detaomedDoods");
			}
			if(WaybillConstants.RETURNTYPE_SEVEN_DAYS_RETURN.equals(code)){
				//7天返货
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sevenDaysReturn");
			}
			if(WaybillConstants.RETURNTYPE_SEND_OUT.equals(code)){
				//外发
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sendOut");
			}
			if(WaybillConstants.RETURN_BACK.equals(code)){
				//外发
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.back");
			}
			if(WaybillConstants.RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN.equals(code)){
				//外发3天返货
				value = i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sendOutThreeDaysReturn");
			}
			if(WaybillConstants.COMPANY_REASON.equals(code)){
				//我司原因
				value = "我司原因";
			}
			if(WaybillConstants.CUSTOMER_REASON.equals(code)){
				//我司原因
				value = "客户原因";
			}
		}
		return value;
	}
	
	private void init(){
		setTitle("转寄退回件申请");
		setSize(NumberConstants.NUMBER_800, NumberConstants.NUMBER_550);
		setResizable(false);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("80dlu"),
				ColumnSpec.decode("5dlu"),
				ColumnSpec.decode("70dlu"),
				ColumnSpec.decode("6dlu"),
				ColumnSpec.decode("80dlu"),
				ColumnSpec.decode("5dlu"),
				ColumnSpec.decode("70dlu"),},
			new RowSpec[] {
				RowSpec.decode("10dlu"),
				RowSpec.decode("20dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("20dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("20dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("20dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("40dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("40dlu"),
				RowSpec.decode("10dlu"),
				RowSpec.decode("60dlu"),
				RowSpec.decode("20dlu"),}));
		Font font = new Font("宋体", 1, NumberConstants.NUMBER_16);
		Font fontValue = new Font("宋体", 0, NumberConstants.NUMBER_16);
		workOrder = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.four")+":");
		workOrder.setFont(font);
		panel.add(workOrder, "2, 2, right, fill");
		
		workOrderValue = new JTextField();
		workOrderValue.setColumns(NumberConstants.NUMBER_70);
		workOrderValue.setEditable(false);
		workOrderValue.setFont(fontValue);
		panel.add(workOrderValue, "4, 2, left, fill");
		
		receiveCustomerName = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerName")+":");
		receiveCustomerName.setFont(font);
		panel.add(receiveCustomerName, "2, 4, right, fill");
		
		receiveCustomerNameValue = new JTextField();
		receiveCustomerNameValue.setColumns(NumberConstants.NUMBER_70);
		receiveCustomerNameValue.setEditable(false);
		receiveCustomerNameValue.setFont(fontValue);
		panel.add(receiveCustomerNameValue, "4, 4, left, fill");
		
//		returnGoodsType = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.seven")+":");
		returnGoodsType = new JLabel("转寄退回件类型:");

		returnGoodsType.setFont(font);
		panel.add(returnGoodsType, "6, 4, right, fill");
		
		returnGoodsTypeVaule = new JTextField();
		returnGoodsTypeVaule.setColumns(NumberConstants.NUMBER_70);
		returnGoodsTypeVaule.setEditable(false);
		returnGoodsTypeVaule.setFont(fontValue);
		panel.add(returnGoodsTypeVaule, "8, 4, left, fill");
		
		receiveCustomerMobilephone = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerMobilephone"));
		receiveCustomerMobilephone.setFont(font);
		panel.add(receiveCustomerMobilephone, "2, 6, right, fill");
		
		receiveCustomerMobilephoneValue = new JTextField();
		receiveCustomerMobilephoneValue.setColumns(NumberConstants.NUMBER_70);
		receiveCustomerMobilephoneValue.setEditable(false);
		receiveCustomerMobilephoneValue.setFont(fontValue);
		panel.add(receiveCustomerMobilephoneValue, "4, 6, left, fill");
		
		receiveCustomerPhone = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerPhone"));
		receiveCustomerPhone.setFont(font);
		panel.add(receiveCustomerPhone, "6, 6, right, fill");
		
		receiveCustomerPhoneValue = new JTextField();
		receiveCustomerPhoneValue.setColumns(NumberConstants.NUMBER_70);
		receiveCustomerPhoneValue.setEditable(false);
		receiveCustomerPhoneValue.setFont(fontValue);
		panel.add(receiveCustomerPhoneValue, "8, 6, left, fill");
		
		insurance = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.insurance")+":");
		insurance.setFont(font);
		panel.add(insurance, "2, 8, right, fill");
		
		insuranceValue = new JTextField();
		insuranceValue.setColumns(NumberConstants.NUMBER_70);
		insuranceValue.setEditable(false);
		insuranceValue.setFont(fontValue);
		panel.add(insuranceValue, "4, 8, left, fill");
		
		goodsPayment = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.five")+":");
		goodsPayment.setFont(font);
		panel.add(goodsPayment, "6, 8, right, fill");
		
		goodsPaymentValue = new JTextField();
		goodsPaymentValue.setColumns(NumberConstants.NUMBER_70);
		goodsPaymentValue.setEditable(false);
		goodsPaymentValue.setFont(fontValue);
		panel.add(goodsPaymentValue, "8, 8, left, fill");
		
//		returnGoodsReason = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnGoodsReason")+":");
		returnGoodsReason = new JLabel("转寄退回件原因:");
		returnGoodsReason.setFont(font);
		panel.add(returnGoodsReason, "2, 10, right, top");
		
		returnGoodsReasonValue = new JTextArea(NumberConstants.NUMBER_3, NumberConstants.NUMBER_70);
		returnGoodsReasonValue.setEditable(false);
		returnGoodsReasonValue.setTabSize(NumberConstants.NUMBER_4); 
		returnGoodsReasonValue.setLineWrap(true);// 激活自动换行功能  
		returnGoodsReasonValue.setWrapStyleWord(true);// 激活断行不断字功能  
		returnGoodsReasonValue.setSize(NumberConstants.NUMBER_3, NumberConstants.NUMBER_50);
		returnGoodsReasonValue.setFont(fontValue);
		returnGoodsReasonValue.setBorder(new LineBorder(new java.awt.Color(NUM_127,NUM_157,NUM_185), 1, false));
		panel.add(returnGoodsReasonValue, "4, 10, 5, 1, left, top");
		
		receiveCustomerAddress = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerAddress")+":");
		receiveCustomerAddress.setFont(font);
		panel.add(receiveCustomerAddress, "2, 12, right, top");
		
		receiveCustomerAddressValue = new JTextArea(NumberConstants.NUMBER_3,NumberConstants.NUMBER_70);
		receiveCustomerAddressValue.setEditable(false);
		receiveCustomerAddressValue.setTabSize(NumberConstants.NUMBER_4); 
		receiveCustomerAddressValue.setLineWrap(true);// 激活自动换行功能  
		receiveCustomerAddressValue.setWrapStyleWord(true);// 激活断行不断字功能  
		receiveCustomerAddressValue.setSize(NumberConstants.NUMBER_3, NumberConstants.NUMBER_50);
		receiveCustomerAddressValue.setFont(fontValue);
		receiveCustomerAddressValue.setBorder(new LineBorder(new java.awt.Color(NUM_127,NUM_157,NUM_185), 1, false));
		panel.add(receiveCustomerAddressValue, "4, 12, 5, 1, left, top");
		
		investigationContent = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.investigationContent")+":");
		investigationContent.setFont(font);
		panel.add(investigationContent, "2, 14, right, top");
		
		investigationContentValue = new JTextArea(NumberConstants.NUMBER_4, NumberConstants.NUMBER_70);
		investigationContentValue.setEditable(false);
		investigationContentValue.setTabSize(NumberConstants.NUMBER_4); 
		investigationContentValue.setLineWrap(true);// 激活自动换行功能  
		investigationContentValue.setWrapStyleWord(true);// 激活断行不断字功能  
		investigationContentValue.setSize(NumberConstants.NUMBER_4, NumberConstants.NUMBER_50);
		investigationContentValue.setFont(fontValue);
		investigationContentValue.setBorder(new LineBorder(new java.awt.Color(NUM_127,NUM_157,NUM_185), 1, false));
		panel.add(investigationContentValue, "4, 14, 5, 1, left, top");
	
		closeButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.close"));
		closeButton.setFont(font);
		panel.add(closeButton, "6, 15, 2, 1, fill, fill");
		// 添加“取消”按钮监听事件
		closeButton.addActionListener(new CancelHandler());
	}
	/**
	 * 定义一般内部类：处理“取消”按钮事件
	 */
	private class CancelHandler implements ActionListener {

		/**
		 * 关闭当前打开的dialog
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	public JLabel getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(JLabel workOrder) {
		this.workOrder = workOrder;
	}
	public JTextField getWorkOrderValue() {
		return workOrderValue;
	}
	public void setWorkOrderValue(JTextField workOrderValue) {
		this.workOrderValue = workOrderValue;
	}
	public JLabel getReturnGoodsReason() {
		return returnGoodsReason;
	}
	public void setReturnGoodsReason(JLabel returnGoodsReason) {
		this.returnGoodsReason = returnGoodsReason;
	}
	public JTextArea getReturnGoodsReasonValue() {
		return returnGoodsReasonValue;
	}
	public void setReturnGoodsReasonValue(JTextArea returnGoodsReasonValue) {
		this.returnGoodsReasonValue = returnGoodsReasonValue;
	}
	public JLabel getReturnGoodsType() {
		return returnGoodsType;
	}
	public void setReturnGoodsType(JLabel returnGoodsType) {
		this.returnGoodsType = returnGoodsType;
	}
	public JTextField getReturnGoodsTypeVaule() {
		return returnGoodsTypeVaule;
	}
	public void setReturnGoodsTypeVaule(JTextField returnGoodsTypeVaule) {
		this.returnGoodsTypeVaule = returnGoodsTypeVaule;
	}
	public JLabel getGoodsPayment() {
		return goodsPayment;
	}
	public void setGoodsPayment(JLabel goodsPayment) {
		this.goodsPayment = goodsPayment;
	}
	public JTextField getGoodsPaymentValue() {
		return goodsPaymentValue;
	}
	public void setGoodsPaymentValue(JTextField goodsPaymentValue) {
		this.goodsPaymentValue = goodsPaymentValue;
	}
	public JLabel getInsurance() {
		return insurance;
	}
	public void setInsurance(JLabel insurance) {
		this.insurance = insurance;
	}
	public JTextField getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(JTextField insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	public JLabel getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(JLabel receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public JTextField getReceiveCustomerNameValue() {
		return receiveCustomerNameValue;
	}
	public void setReceiveCustomerNameValue(JTextField receiveCustomerNameValue) {
		this.receiveCustomerNameValue = receiveCustomerNameValue;
	}
	public JLabel getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	public void setReceiveCustomerMobilephone(JLabel receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	public JTextField getReceiveCustomerMobilephoneValue() {
		return receiveCustomerMobilephoneValue;
	}
	public void setReceiveCustomerMobilephoneValue(
			JTextField receiveCustomerMobilephoneValue) {
		this.receiveCustomerMobilephoneValue = receiveCustomerMobilephoneValue;
	}
	public JLabel getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(JLabel receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public JTextField getReceiveCustomerPhoneValue() {
		return receiveCustomerPhoneValue;
	}
	public void setReceiveCustomerPhoneValue(JTextField receiveCustomerPhoneValue) {
		this.receiveCustomerPhoneValue = receiveCustomerPhoneValue;
	}
	public JLabel getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(JLabel receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public JTextArea getReceiveCustomerAddressValue() {
		return receiveCustomerAddressValue;
	}
	public void setReceiveCustomerAddressValue(JTextArea receiveCustomerAddressValue) {
		this.receiveCustomerAddressValue = receiveCustomerAddressValue;
	}
	public JLabel getInvestigationContent() {
		return investigationContent;
	}
	public void setInvestigationContent(JLabel investigationContent) {
		this.investigationContent = investigationContent;
	}
	public JTextArea getInvestigationContentValue() {
		return investigationContentValue;
	}
	public void setInvestigationContentValue(JTextArea investigationContentValue) {
		this.investigationContentValue = investigationContentValue;
	}
	public JButton getCloseButton() {
		return closeButton;
	}
	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}
}
