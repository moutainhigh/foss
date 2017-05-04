package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 运单、标签打印异常类
 * @author 097972-foss-dengtingting
 * @date 2013-4-12 下午4:12:09
 */
public class WaybillLabelPrintException extends BusinessException{

	/**
	 * 序列号 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PRINT_EXISTWYBILL_EXCEPTION = "pkp.waybill.labelPrintinfoService.existWaybill.exception";

	public static final String WAYBILL_NUMBER_NOT_EXIST = "foss.waybillRfc.waybillNumberNotExist";
	
	public static final String IS_NOT_EWAYBILL = "foss.waybillRfc.waybillNumberNotExist.isNotEWaybillEntity";
	//传入的运单号不存在有效记录
    public static final String WAYBILLBEAN_NULL="foss.pkp.waybill.waybillManagerService.exception.nullWaybillBean";
  //传入的运单号不存在有效记录
    public static final String IS_NOT_EWAYBILLBEAN="foss.gui.creating.printSignUI.printSerial.isNotEWaybill";
	public WaybillLabelPrintException(){
		super();
	}
	
	public WaybillLabelPrintException(String code){
		super(code);
		this.errCode = code;
	}
	
	public WaybillLabelPrintException(String code, String msg){
		super(code,msg);
	}
}
