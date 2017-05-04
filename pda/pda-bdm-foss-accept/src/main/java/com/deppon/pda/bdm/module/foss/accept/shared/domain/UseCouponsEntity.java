package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file UseCouponsEntity.java
 * @description 使用优惠券
 * @author ChenLiang
 * @created 2012-12-31 下午3:04:29
 * @version 1.0
 */
public class UseCouponsEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券编号
	 */
	private String couponCode;

	/**
	 * 运单号
	 */
	private String waybillCode;

	/**
	 * 订单号
	 */
	private String orderCode;

	/**
	 * 订单来源
	 */
	private String orderSource;

	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 运单金额
	 */
	private double wblMoney;

	/**
	 * 发货人手机号码
	 */
	private String consignorMobile;

	/**
	 * 发货人固话
	 */
	private String consignorTelephone;

	/**
	 * 运单金额明细
	 */
	private List<AmountInfoEntity> wblMoneyDetail;

	/**
	 * 客户编号
	 */
	private String customerCode;

	/**
	 * 出发部门
	 */
	private String departDeptCode;

	/**
	 * 到达部门
	 */
	private String assemblyDeptCode;
	
	/**
     * 发货部门所在外场  11月6日新增
     */
    protected String leaveOutDept;
    /**
     * 到达部门所在外场  11月6日新增
     */
    protected String arrivedOutDept;

	/**
	 * 使用标志
	 */
	private String useMark;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getWaybillCode() {
		return waybillCode;
	}

	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public double getWblMoney() {
		return wblMoney;
	}

	public void setWblMoney(double wblMoney) {
		this.wblMoney = wblMoney;
	}

	public String getConsignorMobile() {
		return consignorMobile;
	}

	public void setConsignorMobile(String consignorMobile) {
		this.consignorMobile = consignorMobile;
	}

	public String getConsignorTelephone() {
		return consignorTelephone;
	}

	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}

	public List<AmountInfoEntity> getWblMoneyDetail() {
		return wblMoneyDetail;
	}

	public void setWblMoneyDetail(List<AmountInfoEntity> wblMoneyDetail) {
		this.wblMoneyDetail = wblMoneyDetail;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getDepartDeptCode() {
		return departDeptCode;
	}

	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	public String getAssemblyDeptCode() {
		return assemblyDeptCode;
	}

	public void setAssemblyDeptCode(String assemblyDeptCode) {
		this.assemblyDeptCode = assemblyDeptCode;
	}

	public String getUseMark() {
		return useMark;
	}

	public void setUseMark(String useMark) {
		this.useMark = useMark;
	}

    public String getLeaveOutDept() {
        return leaveOutDept;
    }

    public void setLeaveOutDept(String leaveOutDept) {
        this.leaveOutDept = leaveOutDept;
    }

    public String getArrivedOutDept() {
        return arrivedOutDept;
    }

    public void setArrivedOutDept(String arrivedOutDept) {
        this.arrivedOutDept = arrivedOutDept;
    }

}
