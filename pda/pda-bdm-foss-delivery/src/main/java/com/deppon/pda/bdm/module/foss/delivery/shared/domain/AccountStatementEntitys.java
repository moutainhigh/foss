package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
/**
 * 
 * @ClassName: AccountStatementEntitys 
 * @Description: TODO(对账单刷卡数据) 
 * @author &268974  wangzhili
 * @date 2016-2-23 下午6:05:54 
 *
 */
public class AccountStatementEntitys implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//交易流水号
	private String tradeSerialNo;
	//刷卡金额
	private BigDecimal serialAmount;
	//操作时间
	private Date operateTime;
	//刷卡部门名称
	private String cardDeptName;
	//刷卡部门编码
	private String cardDeptCode;
	//创建人名称
	private String createUserName;
	//创建人编码
	private String createUserCode;
	//所属模块
	private String belongModule;
	//异常原因
	private String errorCause;
	//明细集合
	private List<PosCardDetailEntity> posCardDetailEntitys;
	//客户编码
	private String customerCode;
	//客户名称
	private String customerName;
	//请求内容
	private String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
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
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getCardDeptName() {
		return cardDeptName;
	}
	public void setCardDeptName(String cardDeptName) {
		this.cardDeptName = cardDeptName;
	}
	public String getCardDeptCode() {
		return cardDeptCode;
	}
	public void setCardDeptCode(String cardDeptCode) {
		this.cardDeptCode = cardDeptCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getBelongModule() {
		return belongModule;
	}
	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}
	public List<PosCardDetailEntity> getPosCardDetailEntitys() {
		return posCardDetailEntitys;
	}
	public void setPosCardDetailEntitys(
			List<PosCardDetailEntity> posCardDetailEntitys) {
		this.posCardDetailEntitys = posCardDetailEntitys;
	}

	
}
