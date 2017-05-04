package com.deppon.foss.module.transfer.common.api.shared.exception;

import java.io.Serializable;

import net.sf.json.JSONObject;


/**
 * 清仓提交异常
 * @author 332153
 * @date 2016年10月13日14:54:29
 */
public class StTaskSubmitException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 1937263904748419090L;
	
	protected String errCode;//错误代码
	
	protected String errMessage;//错误信息
	
	protected int packageNum;//未提交报数量
	
	protected int waybillNum;//未运单数量
	
	protected int serialNum;//未扫描流水号

	public StTaskSubmitException(int packageNum,int waybillNum){
		super();
		this.errCode="UNFINISHED";
		this.packageNum=packageNum;
		this.waybillNum=waybillNum;
		this.serialNum=waybillNum;

	}
	public StTaskSubmitException(int packageNum,int waybillNum,int serialNum){
		super();
		this.errCode="UNFINISHED";
		this.packageNum=packageNum;
		this.waybillNum=waybillNum;
		this.serialNum=serialNum;
	}
	public StTaskSubmitException(String errCode,String errMessage,int packageNum,int waybillNum){
		super();
		this.errCode=errCode;
		this.errMessage=errMessage;
		this.packageNum=packageNum;
		this.waybillNum=waybillNum;
		this.serialNum=waybillNum;

	}
	public StTaskSubmitException(String errCode,String errMessage,int packageNum,int waybillNum,int serialNum){
		super();
		this.errCode=errCode;
		this.errMessage=errMessage;
		this.packageNum=packageNum;
		this.waybillNum=waybillNum;
		this.serialNum=serialNum;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getDefaultMessage() {
		return "PDA分支未完成任务";
	}
	public int getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(int packageNum) {
		this.packageNum = packageNum;
	}
	public int getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(int waybillNum) {
		this.waybillNum = waybillNum;
	}
	public int getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}
	public String getNuminfo(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("waybillNum", waybillNum);
		jsonObject.put("serialNum", serialNum);
		jsonObject.put("packageNum", packageNum);
		return jsonObject.toString();
	}
 	
	
}
