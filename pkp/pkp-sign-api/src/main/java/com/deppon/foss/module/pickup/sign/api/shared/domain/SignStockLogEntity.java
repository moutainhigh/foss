package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 *签收反签收同步改异步库存日志
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 上午11:32:12
 * @since
 * @version
 */
public class SignStockLogEntity extends BaseEntity {
	/**
     * 序列号
	 */
	private static final long serialVersionUID = -1584729975645233938L;
	/**
	 *运单号
	 */
	private String waybillNo;
	/**
	 * 流水号
	 */
    private String serialNo;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作部门编码
     */
    private String operatorCode;
    /**
     * 库存部门编码 
     */
    private String stockOrgCode;
    /**
     * 库存部门名称
     */
    private String stockOrgName;
    /**
     * 出入库类型
     */
    private String inoutType;
    /**
     * 状态 0-待执行 1-执行中 2-异常
     */
    private String status;
     /**
      * 异常原因
      */
    private String signStatus;
    /**
     * 是否整车
     */
    private String isWholeVehicle;
    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * Gets the 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * Sets the 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * Gets the 流水号.
     *
     * @return the 流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Sets the 流水号.
     *
     * @param serialNo the new 流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * Gets the 操作人.
     *
     * @return the 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the 操作人.
     *
     * @param operator the new 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Gets the 操作部门编码.
     *
     * @return the 操作部门编码
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * Sets the 操作部门编码.
     *
     * @param operatorCode the new 操作部门编码
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    /**
     * Gets the 库存部门编码.
     *
     * @return the 库存部门编码
     */
    public String getStockOrgCode() {
        return stockOrgCode;
    }

    /**
     * Sets the 库存部门编码.
     *
     * @param stockOrgCode the new 库存部门编码
     */
    public void setStockOrgCode(String stockOrgCode) {
        this.stockOrgCode = stockOrgCode;
    }

    /**
     * Gets the 库存部门名称.
     *
     * @return the 库存部门名称
     */
    public String getStockOrgName() {
        return stockOrgName;
    }

    /**
     * Sets the 库存部门名称.
     *
     * @param stockOrgName the new 库存部门名称
     */
    public void setStockOrgName(String stockOrgName) {
        this.stockOrgName = stockOrgName;
    }

    /**
     * Gets the 出入库类型.
     *
     * @return the 出入库类型
     */
    public String getInoutType() {
        return inoutType;
    }

    /**
     * Sets the 出入库类型.
     *
     * @param inoutType the new 出入库类型
     */
    public void setInoutType(String inoutType) {
        this.inoutType = inoutType;
    }

    /**
     * Gets the 状态 0-待执行 1-执行中 2-异常.
     *
     * @return the 状态 0-待执行 1-执行中 2-异常
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the 状态 0-待执行 1-执行中 2-异常.
     *
     * @param status the new 状态 0-待执行 1-执行中 2-异常
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the 异常原因.
     *
     * @return the 异常原因
     */
    public String getSignStatus() {
        return signStatus;
    }

    /**
     * Sets the 异常原因.
     *
     * @param signStatus the new 异常原因
     */
    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    /**
     * Gets the 是否整车.
     *
     * @return the 是否整车
     */
    public String getIsWholeVehicle() {
        return isWholeVehicle;
    }

    /**
     * Sets the 是否整车.
     *
     * @param isWholeVehicle the new 是否整车
     */
    public void setIsWholeVehicle(String isWholeVehicle) {
        this.isWholeVehicle = isWholeVehicle;
    }

    /**
     * Gets the 创建日期.
     *
     * @return the 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets the 创建日期.
     *
     * @param createTime the new 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}