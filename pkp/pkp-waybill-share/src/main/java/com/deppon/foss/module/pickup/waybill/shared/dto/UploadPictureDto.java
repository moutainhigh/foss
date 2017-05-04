package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 上传图片DTO
 * @author WangQianJin
 *
 */
public class UploadPictureDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -2968771070264509400L;

	/**
	 * 凭证类型
	 */
	private String proofType;

	/**
	 * 是否默认
	 */
	private boolean isDefault;

	/**
	 * 凭证名称
	 */
	private String proofName;

	/**
	 * 凭证内容
	 */
	private String proofBytes;
	
	/**
	 * 上传路径
	 */
	private String uploadPath;
	
	/**
	 * @return the proofType
	 */
	public String getProofType() {
		return proofType;
	}

	
	/**
	 * @param proofType the proofType to set
	 */
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	
	/**
	 * @return the isDefault
	 */
	public boolean getIsDefault() {
		return isDefault;
	}

	
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	
	/**
	 * @return the proofName
	 */
	public String getProofName() {
		return proofName;
	}

	
	/**
	 * @param proofName the proofName to set
	 */
	public void setProofName(String proofName) {
		this.proofName = proofName;
	}

	
	/**
	 * @return the proofBytes
	 */
	public String getProofBytes() {
		return proofBytes;
	}

	
	/**
	 * @param proofBytes the proofBytes to set
	 */
	public void setProofBytes(String proofBytes) {
		this.proofBytes = proofBytes;
	}


	public String getUploadPath() {
		return uploadPath;
	}


	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
}
