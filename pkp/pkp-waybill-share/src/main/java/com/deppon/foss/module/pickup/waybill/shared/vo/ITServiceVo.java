package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto;



/**
 * IT上报VO
 * @author WangQianJin
 * 2013-11-08
 */
public class ITServiceVo implements Serializable {
	
	/**
	 * 上报人工号
	 */
	private String empCode;
	/**
	 * 所属系统
	 */
	private String systemCode;
	/**
	 * 提示信息
	 */
	private String showMessage;
	/**
	 * 问题类型
	 */
	private String questionType;
	/**
	 * 上传列表
	 */
	private List<UploadPictureDto> uploadVoucherList;
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}	
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getShowMessage() {
		return showMessage;
	}
	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public List<UploadPictureDto> getUploadVoucherList() {
		return uploadVoucherList;
	}
	public void setUploadVoucherList(List<UploadPictureDto> uploadVoucherList) {
		this.uploadVoucherList = uploadVoucherList;
	}
	

}
