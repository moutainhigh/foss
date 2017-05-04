package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
  * @ClassName DeryExcpScanEntity 
  * @Description 派送异常 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class DeryExcpScanEntity extends ScanMsgEntity{
	private static final long serialVersionUID = 988487513109905602L;
	//异常原因
	private String excpReason;
	//车牌号
	private String truckCode;
	//到达联编号
	private String arrInfoCode;
	//备注
	private String remark;
	 //派送开始时间
    private Date sendstart;
    //派送结束时间
    private Date sendend;
    //签收开始时间
    private Date signstart;
    //签收结束时间
    private Date signTime;
    //签收类型
    private String signtypee;
    //author:245960 Date:2015-06-05
    /**
     * 再次派送时间
     */
    private Date nextDeliverTime;
    
	public Date getNextDeliverTime() {
		return nextDeliverTime;
	}
	public void setNextDeliverTime(Date nextDeliverTime) {
		this.nextDeliverTime = nextDeliverTime;
	}
	public String getExcpReason() {
		return excpReason;
	}
	public void setExcpReason(String excpReason) {
		this.excpReason = excpReason;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getArrInfoCode() {
		return arrInfoCode;
	}
	public void setArrInfoCode(String arrInfoCode) {
		this.arrInfoCode = arrInfoCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getSigntypee() {
		return signtypee;
	}
	public void setSigntypee(String signtypee) {
		this.signtypee = signtypee;
	}
	public Date getSendstart() {
		return sendstart;
	}
	public void setSendstart(Date sendstart) {
		this.sendstart = sendstart;
	}
	public Date getSendend() {
		return sendend;
	}
	public void setSendend(Date sendend) {
		this.sendend = sendend;
	}
	public Date getSignstart() {
		return signstart;
	}
	public void setSignstart(Date signstart) {
		this.signstart = signstart;
	}
	
}
