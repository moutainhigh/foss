/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 落地配扩展对象，用于查询返回或者查询参数
 * 
 * @author liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillDto extends LdpExternalBillEntity {

	private static final long serialVersionUID = -5368891875450086975L;

	// 运单相关信息
	/**
	 * 付款方式PAID_METHOD
	 */
	private String payType;
	/**
	 * 提货方式RECEIVE_METHOD
	 */
	private String pickupType;
	/**
	 * 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	private BigDecimal weight;
	/**
	 * 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	private BigDecimal volume;
	/**
	 * 件数//货物总件数GOODS_QTY_TOTAL
	 */
	private Integer goodsNum;
	/**
	 * 货物名称//货物名称GOODS_NAME
	 */
	private String goodsName;
	/**
	 * 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	private BigDecimal declarationValue;
	/**
	 * 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	private String receiveCompany;
	/**
	 * 收货客户联系人
	 */
	private String receiveName;
	/**
	 * 收货联系方式
	 */
	private String receivePhone;
	/**
	 * 收货详细地址
	 */
	private String receiveAddr;
	
	/**
	 * 录入日期从
	 */
	private String registerTimeFrom;
	/**
	 * 录入日期到
	 */
	private String registerTimeTo;
	/**
	 * 交接类型
	 */
	private String handoverType;
	/**
	 * 网点电话
	 */
	private String agentOrgPhone;
	/**
	 * 网点地址
	 */
	private String agentOrgaddr;
	/**
	 * 运单状态
	 */
	private String active;
	/**
	 * 状态列表
	 */
	private List<String> list;
	/**
	 * 交接单状态
	 */
	private List<Integer> billStatuslist;
	/**
	 * 查询过滤部门编码
	 */
	private String filterOrgCode;
	/**
	 * 审核状态全部查询
	 */
	private String auditAll;

	private List<String> filterOrgCodes;
	
	/**
	 * 交接单创建时间
	 */
	private Date billTime;

	/**
     * 行政区域code
     */
    private String districtCode;
    
    private BigDecimal paymentCollectionFee;
    private BigDecimal insuranceFee;
    /**
     * 开单部门
     */
    private String createOrgCode;
    
    /**
     * 开单部门名称
     */
    private String createOrgName;

    /**
     * 收件详细地址
     */
    private String receiveCustomerAddress;
    
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getPaymentCollectionFee() {
		return paymentCollectionFee;
	}

	public void setPaymentCollectionFee(BigDecimal paymentCollectionFee) {
		this.paymentCollectionFee = paymentCollectionFee;
	}

	/**
	 * 付款方式
	 */
	public String getPayType() {
		return payType;
	}
	
	/**
	 * 付款方式
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	/**
	 * 提货方式
	 */
	public String getPickupType() {
		return pickupType;
	}
	
	/**
	 * 提货方式
	 */
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	/**
	 * 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 件数
	 */
	public Integer getGoodsNum() {
		return goodsNum;
	}

	/**
	 * 件数
	 */
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	/**
	 * 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 声明价值
	 */
	public BigDecimal getDeclarationValue() {
		return declarationValue;
	}

	/**
	 * 声明价值
	 */
	public void setDeclarationValue(BigDecimal declarationValue) {
		this.declarationValue = declarationValue;
	}

	/**
	 * 收货客户(公司)
	 */
	public String getReceiveCompany() {
		return receiveCompany;
	}

	/**
	 * 收货客户(公司)
	 */
	public void setReceiveCompany(String receiveCompany) {
		this.receiveCompany = receiveCompany;
	}

	/**
	 * 收货联系人姓名
	 */
	public String getReceiveName() {
		return receiveName;
	}

	/**
	 * 收货联系人姓名
	 */
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	/**
	 * 收货联系人电话
	 */
	public String getReceivePhone() {
		return receivePhone;
	}

	/**
	 * 收货联系人电话
	 */
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	/**
	 * 收货客户地址
	 */
	public String getReceiveAddr() {
		return receiveAddr;
	}

	/**
	 * 收货客户地址
	 */
	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}

	/**
	 * 获取 录入日期从.
	 * 
	 * @return the 录入日期从
	 */
	public String getRegisterTimeFrom() {
		return registerTimeFrom;
	}

	/**
	 * 设置 录入日期从.
	 * 
	 * @param registerTimeFrom
	 *            the new 录入日期从
	 */
	public void setRegisterTimeFrom(String registerTimeFrom) {
		this.registerTimeFrom = registerTimeFrom;
	}

	/**
	 * 获取 录入日期到.
	 * 
	 * @return the 录入日期到
	 */
	public String getRegisterTimeTo() {
		return registerTimeTo;
	}

	/**
	 * 设置 录入日期到.
	 * 
	 * @param registerTimeTo
	 *            the new 录入日期到
	 */
	public void setRegisterTimeTo(String registerTimeTo) {
		this.registerTimeTo = registerTimeTo;
	}

	/**
	 * 获取 交接类型.
	 * 
	 * @return the 交接类型
	 */
	public String getHandoverType() {
		return handoverType;
	}

	/**
	 * 设置 交接类型.
	 * 
	 * @param handoverType
	 *            the new 交接类型
	 */
	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	/**
	 * 获取 运单状态.
	 * 
	 * @return the 运单状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 运单状态.
	 * 
	 * @param active
	 *            the new 运单状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 状态列表.
	 * 
	 * @return the 状态列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 状态列表.
	 * 
	 * @param list
	 *            the new 状态列表
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 获取 交接单状态.
	 * 
	 * @return the 交接单状态
	 */
	public List<Integer> getBillStatuslist() {
		return billStatuslist;
	}

	/**
	 * 设置 交接单状态.
	 * 
	 * @param billStatuslist
	 *            the new 交接单状态
	 */
	public void setBillStatuslist(List<Integer> billStatuslist) {
		this.billStatuslist = billStatuslist;
	}

	public String getFilterOrgCode() {
		return filterOrgCode;
	}

	public void setFilterOrgCode(String filterOrgCode) {
		this.filterOrgCode = filterOrgCode;
	}

	public String getAuditAll() {
		return auditAll;
	}

	public void setAuditAll(String auditAll) {
		this.auditAll = auditAll;
	}

	public List<String> getFilterOrgCodes() {
		return filterOrgCodes;
	}

	public void setFilterOrgCodes(List<String> filterOrgCodes) {
		this.filterOrgCodes = filterOrgCodes;
	}

	public String getAgentOrgPhone() {
		return agentOrgPhone;
	}

	public void setAgentOrgPhone(String agentOrgPhone) {
		this.agentOrgPhone = agentOrgPhone;
	}

	public String getAgentOrgaddr() {
		return agentOrgaddr;
	}

	public void setAgentOrgaddr(String agentOrgaddr) {
		this.agentOrgaddr = agentOrgaddr;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

    public String getReceiveCustomerAddress() {
        return receiveCustomerAddress;
    }

    public void setReceiveCustomerAddress(String receiveCustomerAddress) {
        this.receiveCustomerAddress = receiveCustomerAddress;
    }
}