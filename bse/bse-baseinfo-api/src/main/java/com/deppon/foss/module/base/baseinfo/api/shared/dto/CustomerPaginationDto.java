/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分页查询客户用 dto
 * @author shixiaowei
 *
 */
public class CustomerPaginationDto implements Serializable {
	/**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4191999282548463574L;
	
	/**
	 *  客户list
	 */
	private List<CustomerQueryConditionDto> dtoList;
	   
	/**
	 * 记录总数
	 */
	private int totalCount;
	
    /**
	 * The page num.
	 */
    private int pageNum;

    /**
	 * The page size.
	 */
    private int pageSize;


	/**
	 * serialVersionUID.
	 */

	/*********** 运单传给综合的查询信息的封装 **********************/

	/**
	 * 客户编码.
	 */
	private String custCode;

	/**
	 * 客户名称.
	 */
	private String custName;
	
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 联系人编码
	 */
	private String linkmanCode;

	/**
	 * 联系人姓名.
	 */
	private String contactName;

	/**
	 * 联系人办公电话.
	 */
	private String contactPhone;

	/**
	 * 联系人移动电话.
	 */
	private String mobilePhone;

	/**
	 * 身份证号.
	 */
	private String idCard;

	/**
	 * 客户所属部门编码.
	 */
	private String deptCode;

	/**
	 * 发/收货人地址（业务往来常用地址）.
	 */
	private String address;

	/**
	 * 是否查询全公司客户信息
	 */
	private boolean exactQuery;

	/*********** 返回给运单的客户信息的封装 **********************/
	/**
	 * 客户ID
	 */
	private String custId;

	/**
	 * 联系人ID
	 */
	private String linkmanId;

	/**
	 * 客户信息用额度
	 */
	private BigDecimal creditAmount;

	/**
	 * 付款方式
	 */
	private String chargeType;

	/**
	 * 合同编号
	 */
	private String bargainCode;

	/**
	 * 合同起始日期
	 */
	private Date beginTime;

	/**
	 * 合同到期日期
	 */
	private Date endTime;
	
	/**
	 * 客户类型（CRM正式客户、CRM散客）
	 */
	private String customerType;
	
	/**
	 * 区域编码
	 */
	private String countyCode;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 省份编码
	 */
	private String provinceCode;
	
	/**
	 * 接送货地址ID
	 */
	private String addressId;
	
	/** 快递开单新增属性 **/
	/**
	 * 快递价格版本时间
	 */
	private Date exPriceVersionDate;
	/**
	 * 快递优惠类型
	 */
	 private String exPreferentialType;
	 
	 /**
	  * 快递结款方式
	  */
	 private String exPayWay;
	 
	 
	/**
	  * 发票标记时间
	  */
	 private Date invoiceDate;
	 
	
	/*********** set和get方法 **********************/
	public Date getExPriceVersionDate() {
		return exPriceVersionDate;
	}

	public void setExPriceVersionDate(Date exPriceVersionDate) {
		this.exPriceVersionDate = exPriceVersionDate;
	}

	public String getExPreferentialType() {
		return exPreferentialType;
	}

	public void setExPreferentialType(String exPreferentialType) {
		this.exPreferentialType = exPreferentialType;
	}

	public String getExPayWay() {
		return exPayWay;
	}

	public void setExPayWay(String exPayWay) {
		this.exPayWay = exPayWay;
	}

	
	/**
	 * 获取 客户编码.
	 * 
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * 设置 客户编码.
	 * 
	 * @param custCode
	 *            the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * 获取 客户名称.
	 * 
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * 设置 客户名称.
	 * 
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * 获取 联系人姓名.
	 * 
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * 设置 联系人姓名.
	 * 
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 获取 联系人办公电话.
	 * 
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置 联系人办公电话.
	 * 
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * 获取 联系人移动电话.
	 * 
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * 设置 联系人移动电话.
	 * 
	 * @param mobilePhone
	 *            the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * 获取 身份证号.
	 * 
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * 设置 身份证号.
	 * 
	 * @param idCard
	 *            the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * 获取 客户所属部门编码.
	 * 
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置 客户所属部门编码.
	 * 
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取 发/收货人地址.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 获取 联系人编码.
	 * 
	 * @return the 联系人编码
	 */
	public String getLinkmanCode() {
		return linkmanCode;
	}

	/**
	 * 设置 联系人编码.
	 * 
	 * @param linkmanCode
	 *            the new 联系人编码
	 */
	public void setLinkmanCode(String linkmanCode) {
		this.linkmanCode = linkmanCode;
	}

	/**
	 * 设置 发/收货人地址.
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	
	/**
	 * @return the exactQuery .
	 */
	public boolean isExactQuery() {
		return exactQuery;
	}

	
	/**
	 *@param exactQuery the exactQuery to set.
	 */
	public void setExactQuery(boolean exactQuery) {
		this.exactQuery = exactQuery;
	}

	/**
	 * 获取 ********* 返回给运单的客户信息的封装 *********************.
	 * 
	 * @return the ********* 返回给运单的客户信息的封装 *********************
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 设置 ********* 返回给运单的客户信息的封装 *********************.
	 * 
	 * @param custId
	 *            the new ********* 返回给运单的客户信息的封装 *********************
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 获取 联系人ID.
	 * 
	 * @return the 联系人ID
	 */
	public String getLinkmanId() {
		return linkmanId;
	}

	/**
	 * 设置 联系人ID.
	 * 
	 * @param linkmanId
	 *            the new 联系人ID
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/**
	 * 获取 客户信息用额度.
	 * 
	 * @return the 客户信息用额度
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * 设置 客户信息用额度.
	 * 
	 * @param creditAmount
	 *            the new 客户信息用额度
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * 获取 付款方式.
	 * 
	 * @return the 付款方式
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * 设置 付款方式.
	 * 
	 * @param chargeType
	 *            the new 付款方式
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * 获取 合同编号.
	 * 
	 * @return the 合同编号
	 */
	public String getBargainCode() {
		return bargainCode;
	}

	/**
	 * 设置 合同编号.
	 * 
	 * @param bargainCode
	 *            the new 合同编号
	 */
	public void setBargainCode(String bargainCode) {
		this.bargainCode = bargainCode;
	}

	

	
	
	/**
	 * 获取 合同起始日期.
	 *
	 * @return the 合同起始日期
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	
	/**
	 * 设置 合同起始日期.
	 *
	 * @param beginTime the new 合同起始日期
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	
	/**
	 * 设置 合同到期日期.
	 *
	 * @param endTime the new 合同到期日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	/**
	 * 获取 合同到期日期.
	 *
	 * @return the 合同到期日期
	 */
	public Date getEndTime() {
		return endTime;
	}

	
	public String getActive() {
		return active;
	}

	
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return the customerType .
	 */
	public String getCustomerType() {
		return customerType;
	}

	
	/**
	 *@param customerType the customerType to set.
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	/**
	 * @return the countyCode .
	 */
	public String getCountyCode() {
		return countyCode;
	}

	
	/**
	 *@param countyCode the countyCode to set.
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	
	/**
	 * @return the cityCode .
	 */
	public String getCityCode() {
		return cityCode;
	}

	
	/**
	 *@param cityCode the cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	
	/**
	 * @return the provinceCode .
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	
	/**
	 *@param provinceCode the provinceCode to set.
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	
	/**
	 * @return the addressId .
	 */
	public String getAddressId() {
		return addressId;
	}

	
	/**
	 *@param addressId the addressId to set.
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	
	/**
	 * @return the dtoList
	 */
	public List<CustomerQueryConditionDto> getDtoList() {
		return dtoList;
	}

	/**
	 * @param dtoList the dtoList to set
	 */
	public void setDtoList(List<CustomerQueryConditionDto> dtoList) {
		this.dtoList = dtoList;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
}
