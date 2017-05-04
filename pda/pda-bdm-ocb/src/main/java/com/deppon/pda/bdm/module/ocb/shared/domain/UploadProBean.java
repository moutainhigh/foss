package com.deppon.pda.bdm.module.ocb.shared.domain;

/*
* @ClassName: UploadProEntity 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 092039/dpyuanjb@deppon.com 
* @date 2014年11月13日 下午9:11:52 
*
 */
public class UploadProBean {
// 是否选中
private boolean select;
// 运单号
private String wblcode;
// 订单号
private String ordercode;
// 进度
private int progress;
// 上传完成
private int status;

public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public boolean isSelect() {
	return select;
}
public void setSelect(boolean select) {
	this.select = select;
}
public String getWblcode() {
	return wblcode;
}
public void setWblcode(String wblcode) {
	this.wblcode = wblcode;
}
public String getOrdercode() {
	return ordercode;
}
public void setOrdercode(String ordercode) {
	this.ordercode = ordercode;
}
public int getProgress() {
	return progress;
}
public void setProgress(int progress) {
	this.progress = progress;
}
}
