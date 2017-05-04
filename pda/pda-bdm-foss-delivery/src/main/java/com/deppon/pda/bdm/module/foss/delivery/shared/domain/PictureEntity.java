package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

/**
 * 
 * @ClassName PictureEntity
 * @Description 图片
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class PictureEntity {
	//图片内容
	private byte[] picture;
	//备注
	private String remark;
	
	//流水号ID
	private String id;
	//图片地址
	private String serilnumberId;
	//备注
	private String pictureUrl;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerilnumberId() {
		return serilnumberId;
	}

	public void setSerilnumberId(String serilnumberId) {
		this.serilnumberId = serilnumberId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
