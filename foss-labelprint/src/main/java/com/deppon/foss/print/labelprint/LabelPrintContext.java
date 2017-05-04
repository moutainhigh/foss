package com.deppon.foss.print.labelprint;

import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;

import com.deppon.foss.print.labelprint.impl.LabelPrintForm;
import com.deppon.foss.print.labelprint.impl.LabelPrintInfo;

public class LabelPrintContext {

	String mDeviceName = null;
	Map paramMap = new HashMap();
	
	PrintService mPrintService = null;
	
	public String getmDeviceName() {
		return mDeviceName;
	}
	public void setmDeviceName(String mDeviceName) {
		this.mDeviceName = mDeviceName;
	}
	public PrintService getmPrintService() {
		return mPrintService;
	}
	public void setmPrintService(PrintService mPrintService) {
		this.mPrintService = mPrintService;
	}
	
	public void put(String key,Object value){
		paramMap.put(key, value);
	}
	
	public Object get(String key){
		return paramMap.get(key);
	}
	private String channelNumber;
	public String getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	private String printForm;
	private String printFormParam;

	public String getPrintForm() {
		return printForm;
	}
	public void setPrintForm(String printForm) {
		this.printForm = printForm;
	}
	public String getPrintFormParam() {
		return printFormParam;
	}
	public void setPrintFormParam(String printFormParam) {
		this.printFormParam = printFormParam;
	}
	
	private LabelPrintInfo labelPrintInfo;
	public LabelPrintInfo getLabelPrintInfo() {
		return labelPrintInfo;
	}
	public void setLabelPrintInfo(LabelPrintInfo labelPrintInfo) {
		this.labelPrintInfo = labelPrintInfo;
	}

	int printPiece = 1;
	public int getPrintPiece() {
		return printPiece;
	}
	public void setPrintPiece(int printPiece) {
		this.printPiece = printPiece;
	}
	
	int prtLeft = 0;
	int prtTop = 0;

	public int getPrtLeft() {
		return prtLeft;
	}
	public void setPrtLeft(int prtLeft) {
		this.prtLeft = prtLeft;
	}
	public int getPrtTop() {
		return prtTop;
	}
	public void setPrtTop(int prtTop) {
		this.prtTop = prtTop;
	}
	
	private String testEnvInd = null;

	public String getTestEnvInd() {
		return testEnvInd;
	}
	public void setTestEnvInd(String testEnvInd) {
		this.testEnvInd = testEnvInd;
	}
	
}
