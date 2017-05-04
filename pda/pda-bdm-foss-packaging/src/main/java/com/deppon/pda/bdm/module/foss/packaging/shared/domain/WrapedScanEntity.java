package com.deppon.pda.bdm.module.foss.packaging.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * TODO(包装打包扫描)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午11:19:50,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午11:19:50
 * @since
 * @version
 */
public class WrapedScanEntity extends ScanMsgEntity{
	/**
	 * 旧标签号
	 */
	private List<String> labelCodes;
	/**
	 * 包装类型
	 */
	private String wrapType;
	/**
	 * 包装后体积
	 */
	private double wrapVolume;
	/**
	 * 加托个数
	 */
	private int trayNumbers;
	/**
	 * 打包员工编号
	 */
	private List<String>  wrapedPeople;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 获取包供应商
	 * 
	 */
	private String packageSupplierCode;
	
	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}
	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	public List<String> getLabelCodes() {
		return labelCodes;
	}
	public void setLabelCodes(List<String> labelCodes) {
		this.labelCodes = labelCodes;
	}
	public String getWrapType() {
		return wrapType;
	}
	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}
	public double getWrapVolume() {
		return wrapVolume;
	}
	public void setWrapVolume(double wrapVolume) {
		this.wrapVolume = wrapVolume;
	}
	public int getTrayNumbers() {
		return trayNumbers;
	}
	public void setTrayNumbers(int trayNumbers) {
		this.trayNumbers = trayNumbers;
	}
	public List<String> getWrapedPeople() {
		return wrapedPeople;
	}
	public void setWrapedPeople(List<String> wrapedPeople) {
		this.wrapedPeople = wrapedPeople;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
