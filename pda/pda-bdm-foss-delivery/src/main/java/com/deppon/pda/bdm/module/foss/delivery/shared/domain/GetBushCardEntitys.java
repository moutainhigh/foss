package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: GetBushCardEntitys 
 * @Description: TODO(待刷卡多条明细实体) 
 * @author &268974  wangzhili
 * @date 2016-3-2 上午9:55:06 
 *
 */
public class GetBushCardEntitys implements Serializable{
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
	private String createDeptCode;
	//员工名称
	private String createUserName;
	//员工编码
	private String createUserCode;
	//所属模块
	private String belongModule;
	//异常原因
	private String errorCause;
	//明细集合
	private List<BushCardDetailEntity> bushCardDetailEntity;
	//请求内容
	private String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getBelongModule() {
		return belongModule;
	}
	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}
	public String getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	public List<BushCardDetailEntity> getBushCardDetailEntity() {
		return bushCardDetailEntity;
	}
	public void setBushCardDetailEntity(
			List<BushCardDetailEntity> bushCardDetailEntity) {
		this.bushCardDetailEntity = bushCardDetailEntity;
	}
	public String getCreateDeptCode() {
		return createDeptCode;
	}
	public void setCreateDeptCode(String createDeptCode) {
		this.createDeptCode = createDeptCode;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	

	
}
