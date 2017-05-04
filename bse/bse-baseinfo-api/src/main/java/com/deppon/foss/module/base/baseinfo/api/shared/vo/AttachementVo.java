package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;

/**
 * @author 078816-WangPeng
 * @date   2014-02-19
 * @description 上传文件附件Vo
 *
 */
public class AttachementVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8696284169833348175L;
	
	private AttachementEntity  attachementEntity;
	
	private List<AttachementEntity> attachementEntityList;
	/**
	 * 已经选中的营业部、卫星点
	 * 
	 */
	private String selectedSalesDept;
	/**
	 * 部门临时欠款额度
	 */
	private BigDecimal maxTempArrears;

	public AttachementEntity getAttachementEntity() {
		return attachementEntity;
	}

	public void setAttachementEntity(AttachementEntity attachementEntity) {
		this.attachementEntity = attachementEntity;
	}

	public List<AttachementEntity> getAttachementEntityList() {
		return attachementEntityList;
	}

	public void setAttachementEntityList(
			List<AttachementEntity> attachementEntityList) {
		this.attachementEntityList = attachementEntityList;
	}

	

	public BigDecimal getMaxTempArrears() {
		return maxTempArrears;
	}

	public void setMaxTempArrears(BigDecimal maxTempArrears) {
		this.maxTempArrears = maxTempArrears;
	}

	public String getSelectedSalesDept() {
		return selectedSalesDept;
	}

	public void setSelectedSalesDept(String selectedSalesDept) {
		this.selectedSalesDept = selectedSalesDept;
	}
	
	
}
