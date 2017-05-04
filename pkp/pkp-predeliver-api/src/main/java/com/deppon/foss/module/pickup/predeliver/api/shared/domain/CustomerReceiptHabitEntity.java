package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.annotation.AllowBlank;

/**
 * @ClassName: CustomerReceiptHabitEntity
 * @Description: 收货客户习惯类
 * @author 237982-foss-fangwenjun
 * @date 2015-3-27 下午2:22:25
 * 
 */
public class CustomerReceiptHabitEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -564546564541L;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 创建人姓名
	 */
	private String createrName;

	/**
	 * 创建人Code
	 */
	private String createUser;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户联系人
	 */
	private String customerContactName;

	/**
	 * 客户手机号码
	 */
	private String customerMobilePhone;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户固话
	 */
	private String customerPhone;

	/**
	 * 送货时间段
	 */
	private String deliveryTimeInterval;

	/**
	 * 送货时间点 结束
	 */
	private String deliveryTimeOver;

	/**
	 * 送货时间点 开始
	 */
	private String deliveryTimeStart;

	/**
	 * 收货习惯Id
	 */
	private String id;

	/**
	 * 发票类型为其它描述发票类型
	 */
	private String invoiceDetail;

	/**
	 * 发票类型
	 * 
	 */
	private String invoiceType;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 修改人Code
	 */
	private String modifyUser;

	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;

	/**
	 * 操作部门名称
	 */
	private String operateOrgName;

	/**
	 * 操作人姓名
	 */
	private String operatorName;

	/**
	 * 收货习惯备注
	 */
	private String receiptHabitRemark;

	/**
	 * 获取createDate
	 * 
	 * @return the createDate
	 */
	@AllowBlank
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 获取createrName
	 * 
	 * @return the createrName
	 */
	@AllowBlank
	public String getCreaterName() {
		return createrName;
	}

	/**
	 * 获取createUser
	 * 
	 * @return the createUser
	 */
	@AllowBlank
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 获取customerCode
	 * 
	 * @return customerCode
	 */
	@AllowBlank
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 获取customerContactName
	 * 
	 * @return customerContactName
	 */
	@AllowBlank
	public String getCustomerContactName() {
		return customerContactName;
	}

	/**
	 * 获取customerMobilePhone
	 * 
	 * @return customerMobilePhone
	 */
	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	/**
	 * 获取customerName
	 * 
	 * @return customerName
	 */
	@AllowBlank
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 获取customerPhone
	 * 
	 * @return customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * 获取deliveryTimeInterval
	 * 
	 * @return deliveryTimeInterval
	 */
	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	/**
	 * 获取deliveryTimeOver
	 * 
	 * @return deliveryTimeOver
	 */
	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	/**
	 * 获取deliveryTimeStart
	 * 
	 * @return deliveryTimeStart
	 */
	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	/**
	 * 获取id
	 * 
	 * @return the id
	 */
	@AllowBlank
	public String getId() {
		return id;
	}

	/**
	 * 获取invoiceDetail
	 * 
	 * @return the invoiceDetail
	 */
	public String getInvoiceDetail() {
		return invoiceDetail;
	}

	/**
	 * 获取invoiceType
	 * 
	 * @return invoiceType
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * 获取modifyDate
	 * 
	 * @return the modifyDate
	 */
	@AllowBlank
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 获取modifyUser
	 * 
	 * @return the modifyUser
	 */
	@AllowBlank
	public String getModifyUser() {
		return modifyUser;
	}

	/**
	 * 获取operateOrgCode
	 * 
	 * @return operateOrgCode
	 */
	@AllowBlank
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * 获取operateOrgName
	 * 
	 * @return operateOrgName
	 */
	@AllowBlank
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * 获取operatorName
	 * 
	 * @return operatorName
	 */
	@AllowBlank
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 获取receiptHabitRemark
	 * 
	 * @return receiptHabitRemark
	 */
	public String getReceiptHabitRemark() {
		return receiptHabitRemark;
	}

	/**
	 * 设置createDate
	 * 
	 * @param createDate
	 *            要设置的createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 设置createrName
	 * 
	 * @param createrName
	 *            要设置的createrName
	 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	/**
	 * 设置createUser
	 * 
	 * @param createUser
	 *            要设置的createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 设置customerCode
	 * 
	 * @param customerCode
	 *            要设置的customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 设置customerContactName
	 * 
	 * @param customerContactName
	 *            要设置的customerContactName
	 */
	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}

	/**
	 * 设置customerMobilePhone
	 * 
	 * @param customerMobilePhone
	 *            要设置的customerMobilePhone
	 */
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	/**
	 * 设置customerName
	 * 
	 * @param customerName
	 *            要设置的customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 设置customerPhone
	 * 
	 * @param customerPhone
	 *            要设置的customerPhone
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * 设置deliveryTimeInterval
	 * 
	 * @param deliveryTimeInterval
	 *            要设置的deliveryTimeInterval
	 */
	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	/**
	 * 设置deliveryTimeOver
	 * 
	 * @param deliveryTimeOver
	 *            要设置的deliveryTimeOver
	 */
	public void setDeliveryTimeOver(String deliveryTimeOver) {

		Pattern pattern = Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9])");
		Matcher matcher = pattern.matcher(deliveryTimeOver);
		if (matcher.matches()) {
			this.deliveryTimeOver = deliveryTimeOver;
		} else {
			this.deliveryTimeOver = "";
		}
	}

	/**
	 * 设置deliveryTimeStart
	 * 
	 * @param deliveryTimeStart
	 *            要设置的deliveryTimeStart
	 */
	public void setDeliveryTimeStart(String deliveryTimeStart) {
		Pattern pattern = Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9])");
		Matcher matcher = pattern.matcher(deliveryTimeStart);
		if (matcher.matches()) {
			this.deliveryTimeStart = deliveryTimeStart;
		} else {
			this.deliveryTimeStart = "";
		}
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 *            要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置invoiceDetail
	 * 
	 * @param invoiceDetail
	 *            要设置的invoiceDetail
	 */
	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	/**
	 * 设置invoiceType
	 * 
	 * @param invoiceType
	 *            要设置的invoiceType
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * 设置modifyDate
	 * 
	 * @param modifyDate
	 *            要设置的modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 设置modifyUser
	 * 
	 * @param modifyUser
	 *            要设置的modifyUser
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * 设置operateOrgCode
	 * 
	 * @param operateOrgCode
	 *            要设置的operateOrgCode
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * 设置operateOrgName
	 * 
	 * @param operateOrgName
	 *            要设置的operateOrgName
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * 设置operatorName
	 * 
	 * @param operatorName
	 *            要设置的operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 设置receiptHabitRemark
	 * 
	 * @param receiptHabitRemark
	 *            要设置的receiptHabitRemark
	 */
	public void setReceiptHabitRemark(String receiptHabitRemark) {
		this.receiptHabitRemark = receiptHabitRemark;
	}

}
