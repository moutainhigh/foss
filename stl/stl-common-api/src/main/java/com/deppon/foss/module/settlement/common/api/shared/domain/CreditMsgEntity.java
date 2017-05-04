package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 财务收支平衡消息表
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-18 上午10:38:00
 * @since
 * @version
 */
public class CreditMsgEntity extends BaseEntity{
  

	private static final long serialVersionUID = 1L;

	/**
	 * 操作类型：核销/反核销/红冲
	 */
	private String type;

	/**
	 * 来源单据号：应收单号
	 */
    private String sourceBillNo;

    /**
     * 部门或者客户编码
     */
    private String code;

    /**
     * 部门或者客户名称
     */
    private String name;

    /**
     * 财务平衡表类型：客户平衡表/部门平衡表
     */
    private String creditType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态：未执行/已执行
     */
    private String status;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceBillNo() {
        return sourceBillNo;
    }

    public void setSourceBillNo(String sourceBillNo) {
        this.sourceBillNo = sourceBillNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
