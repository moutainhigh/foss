package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName: AccountStatementEntity 
 * @Description: TODO(对账单查询数据接收参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 下午4:01:37 
 *
 */
public class AccountStatementEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//单号
	private String number;
	//操作时间
	private Date operateTime;
	//所属模块accountStatement -- 对账单
    //POSList -- 待刷卡单据
   //settleCredit -- 结清货款
   //preDeposit -- 预存款
	private String belongModule;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getBelongModule() {
		return belongModule;
	}
	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}


}
