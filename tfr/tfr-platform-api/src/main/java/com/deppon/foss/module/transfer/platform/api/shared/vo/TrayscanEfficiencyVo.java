package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;

/**
 * 托盘绑定效率VO
 * 
 * @author 200978 2015-2-3
 */
public class TrayscanEfficiencyVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hqCode;

	private String hqName;

	private String tfrCtrCode;

	private String tfrCtrName;

	private Date staDate;

	/**
	 * 
	 */
	List<TrayscanEfficiencyEntity> effs;

	List<TrayscanEfficiencyDetailEntity> effDetails;

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public String getHqCode() {
		return hqCode;
	}

	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	public String getHqName() {
		return hqName;
	}

	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public List<TrayscanEfficiencyEntity> getEffs() {
		return effs;
	}

	public void setEffs(List<TrayscanEfficiencyEntity> effs) {
		this.effs = effs;
	}

	public List<TrayscanEfficiencyDetailEntity> getEffDetails() {
		return effDetails;
	}

	public void setEffDetails(List<TrayscanEfficiencyDetailEntity> effDetails) {
		this.effDetails = effDetails;
	}

}
