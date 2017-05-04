package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * POS对接FOSS系统公共查询参数
 * 
 * @ClassName: CommonQueryParamDto
 * @author FOSS结算开发组
 * @date 2016-1-23 下午12:37:25
 */
public class CommonQueryParamDto {
	
	/**
	 * 系统归属，FOSS和CUBC
	 */
	private String origin;
	/**
	 * 员工工号
	 */
	private String empCode;
	
	/**
	 * 员工姓名
	 */
	private String empName;
	/**
	 * 登录部门
	 */
	private String orgCode;
	
	/**
	 * 登陆部门名称
	 */
	private String orgName;
	
	/**
	 * 是否为司机
	 */
	private String isDriver;
	
	/**
	 * 交易流水号
	 */
	private String tradeSerialNo;
	
	/**
	 * 流水号金额
	 */
	private BigDecimal serialAmount;
	
	/**
	 * 所属模块：待刷卡单据、结清货款、对账单、预存款、快递
	 */
	private String belongModule;
	
	/**
	 * 单号:此处单号为对账单模块所需参数，客户编码或者对账单号
	 */
	private List<String> numbers;
	
	/**
	 * 运单号
	 */
	private List<String> waybillNo;
	
	/**
	 * 单据类型
	 */
	private String billType;
	
	/**
	 * 操作时间
	 */
	private Date operateTime;
	
	/**
	 * 司机服务部门(司机选择所属的服务部门)
	 * 1.PDA后台传给前台（结清货款这个收入部门是传不过来的）
	 * 2.不管是开的现付还是到付，收入部门都是始发部门
	 */
	private String driverServerDept;
	
	/**
	 * 仅供PDA使用展示字段 
	 * 
	 */
	/**
	 * 收货人
	 */
	private String receiveCustomerName;
	
	/**
	 * 业务日期
	 */
	private Date createTime;
	
	/**
	 * 总运费
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 未付款总额
	 */
	private BigDecimal unPayAmount;
	
	/**
	 * 未核销
	 */
	private BigDecimal unverifyAmount;
	//支付宝条码支付
	private String  zfbPayType;
	//结清方式（PDA/PC）
	private String repaymentType;
	//收款账号
	private  String  recivibleNusString;
	/*******getter/setter*********/
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public BigDecimal getSerialAmount() {
		return serialAmount;
	}

	public void setSerialAmount(BigDecimal serialAmount) {
		this.serialAmount = serialAmount;
	}

	public String getBelongModule() {
		return belongModule;
	}

	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	public List<String> getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(List<String> waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDriverServerDept() {
		return driverServerDept;
	}

	public void setDriverServerDept(String driverServerDept) {
		this.driverServerDept = driverServerDept;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	public BigDecimal getUnPayAmount() {
		return unPayAmount;
	}

	public void setUnPayAmount(BigDecimal unPayAmount) {
		this.unPayAmount = unPayAmount;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getZfbPayType() {
		return zfbPayType;
	}

	public void setZfbPayType(String zfbPayType) {
		this.zfbPayType = zfbPayType;
	}

	public String getRecivibleNusString() {
		return recivibleNusString;
	}

	public void setRecivibleNusString(String recivibleNusString) {
		this.recivibleNusString = recivibleNusString;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
}

