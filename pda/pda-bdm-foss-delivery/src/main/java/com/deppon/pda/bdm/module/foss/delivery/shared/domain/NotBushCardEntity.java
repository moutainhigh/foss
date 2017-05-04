package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: NotBushCardEntity 
 * @Description: TODO(待刷卡实体) 
 * @author &268974  wangzhili
 * @date 2016-1-28 上午8:50:50 
 *
 */
public class NotBushCardEntity implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	//单号
	private String number;
	//操作时间
	private Date operateTime;
	//所属模块
	private String belongModule;
	
	

	public String getBelongModule() {
		return belongModule;
	}
	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}
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
	

}
