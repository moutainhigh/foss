package com.deppon.foss.module.base.baseinfo.api.shared.domain;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-11 10:24:55
 * 快递自动补码管理
 *
 */
public class ExpressAutoComplementManageEntity extends BaseEntity{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 省份名称
	 */
	private String provinceName;
	
	/**
	 * 状态
	 * 是否开启关闭 开启-Y;关闭-N
	 */
	private String status;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 工号
	 */
	private String jobNumber;
	
	/**
	 * 操作时间
	 */
	private Date operationTime;
	
	/**
	 * 城市编码
	 * @return
	 */
	private String cityCode;
	
	/**
	 * 创建人姓名
	 * @return
	 */
	private String createUserName;
	
	/**
	 * 修改人姓名
	 * @return
	 */
	private String modifyUserName;
	
	/**
	 * 省份编码
	 * @return
	 */
	private String provinceCode;
	
	
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	
}
