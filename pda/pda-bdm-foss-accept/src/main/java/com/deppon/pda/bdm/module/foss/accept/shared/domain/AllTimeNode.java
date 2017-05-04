package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**  
 * 作者：肖龙雾
 * 描述：TODO 
 * 包名：com.deppon.pda.bdm.module.foss.delivery.shared.domain
 * 时间：2014-11-24 上午11:15:08
 */
public class AllTimeNode implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	/**id*/
	private String id;
	
	/**运单号*/
	private String waybillcode;
	
	/**工号*/
	private String empcode;
	
	/**订单接受时间*/
	private String orderreceive;
	
	/**查看明细时间*/
	private String seedetail;
	
	/**开单开始时间*/
	private String billstart;
	
	/**接货开单时间*/
	private String receivebill;
	
	/**开单结束时间*/
	private String billend;
	
	/**选择目的站开始时间*/
	private String selectdestinationstart;
	
	/**选择目的站结束时间*/
	private String selectdestinationend;
	
	/**增值服务开始时间*/
	private String valueaddstart;
	
	/**增值服务结束时间*/
	private String valueaddend;
	
	/**计算运费开始时间*/
	private String calcarriagestart;
	
	/**计算运费结束时间*/
	private String calcarriageend;	
	
	/**刷卡支付开始时间*/
	private String cardstart;
	
	/**刷卡支付结束时间*/
	private String cardend;
	
	/**支付类型*/
	private String paytype;
	
	/**当前时间*/
	private String curtime;
	
	/**快递员对应部门*/
	private String deptcode;

	/************************************************************/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillcode() {
		return waybillcode;
	}

	public void setWaybillcode(String waybillcode) {
		this.waybillcode = waybillcode;
	}

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public String getOrderreceive() {
		return orderreceive;
	}

	public void setOrderreceive(String orderreceive) {
		this.orderreceive = orderreceive;
	}

	public String getSeedetail() {
		return seedetail;
	}

	public void setSeedetail(String seedetail) {
		this.seedetail = seedetail;
	}

	public String getBillstart() {
		return billstart;
	}

	public void setBillstart(String billstart) {
		this.billstart = billstart;
	}

	public String getReceivebill() {
		return receivebill;
	}

	public void setReceivebill(String receivebill) {
		this.receivebill = receivebill;
	}

	public String getBillend() {
		return billend;
	}

	public void setBillend(String billend) {
		this.billend = billend;
	}

	public String getSelectdestinationstart() {
		return selectdestinationstart;
	}

	public void setSelectdestinationstart(String selectdestinationstart) {
		this.selectdestinationstart = selectdestinationstart;
	}

	public String getSelectdestinationend() {
		return selectdestinationend;
	}

	public void setSelectdestinationend(String selectdestinationend) {
		this.selectdestinationend = selectdestinationend;
	}

	public String getValueaddstart() {
		return valueaddstart;
	}

	public void setValueaddstart(String valueaddstart) {
		this.valueaddstart = valueaddstart;
	}

	public String getValueaddend() {
		return valueaddend;
	}

	public void setValueaddend(String valueaddend) {
		this.valueaddend = valueaddend;
	}

	public String getCalcarriagestart() {
		return calcarriagestart;
	}

	public void setCalcarriagestart(String calcarriagestart) {
		this.calcarriagestart = calcarriagestart;
	}

	public String getCalcarriageend() {
		return calcarriageend;
	}

	public void setCalcarriageend(String calcarriageend) {
		this.calcarriageend = calcarriageend;
	}

	public String getCardstart() {
		return cardstart;
	}

	public void setCardstart(String cardstart) {
		this.cardstart = cardstart;
	}

	public String getCardend() {
		return cardend;
	}

	public void setCardend(String cardend) {
		this.cardend = cardend;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getCurtime() {
		return curtime;
	}

	public void setCurtime(String curtime) {
		this.curtime = curtime;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	
	
}
