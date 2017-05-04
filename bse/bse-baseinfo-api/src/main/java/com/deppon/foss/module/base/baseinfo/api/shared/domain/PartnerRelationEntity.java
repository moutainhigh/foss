package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 网点映射关系实体
 * @author 308861 
 * @date 2016-8-19 下午2:57:43
 */
public class PartnerRelationEntity extends BaseEntity{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 978026852184750485L;
	
	/**
	 * 一级合伙人网点编码
	 */
	private String onePartnerCode;
	/**
	 * 一级合伙人网点标杆编码
	 */
	private String onePartnerUnifiedCode;
	/**
	 * 一级合伙人网点名称
	 */
	private String onePartnerName;
	/**
	 * 二级合伙人网点编码
	 */
	private String twoPartnerCode;
	/**
	 * 二级合伙人网点标杆编码
	 */
	private String twoPartnerUnifiedCode;
	/**
	 * 二级合伙人网点名称
	 */
	private String twoPartnerName;
	/**
	 * 对接营业部编码
	 */
	private String dockingNumber;
	/**
	 * 对接营业部名称
	 */
	private String dockingDepName;
	/**
	 * 所属子公司编码
	 */
	private String subCompanyCode;
	/**
	 * 所属子公司名称
	 */
	private String subCompanyName;
	/**
	 * 创建人工号
	 */
	private String createCode;
	/**
	 * 修改人工号
	 */
	private String modifyCode;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 查询条件  开始时间
	 */
	private Date startDate;
	/**
	 * 查询条件  结束时间
	 */
	private Date endDate;
	
	/**
	 * 一级网点所属子公司编码
	 */
	private String oneSubCompanyCode;
	/**
	 * 一级网点所属子公司名称
	 */
	private String oneSubCompanyName;
	/**
	 * 二级网点所属子公司编码
	 */
	private String twoSubCompanyCode;
	/**
	 * 二级网点所属子公司名称
	 */
	private String twoSubCompanyName;
	
	/**
	 * 操作类型
	 */
	private Integer OperateType;
	
	public String getOnePartnerCode() {
		return onePartnerCode;
	}
	public void setOnePartnerCode(String onePartnerCode) {
		this.onePartnerCode = onePartnerCode;
	}
	public String getOnePartnerUnifiedCode() {
		return onePartnerUnifiedCode;
	}
	public void setOnePartnerUnifiedCode(String onePartnerUnifiedCode) {
		this.onePartnerUnifiedCode = onePartnerUnifiedCode;
	}
	public String getOnePartnerName() {
		return onePartnerName;
	}
	public void setOnePartnerName(String onePartnerName) {
		this.onePartnerName = onePartnerName;
	}
	public String getTwoPartnerCode() {
		return twoPartnerCode;
	}
	public void setTwoPartnerCode(String twoPartnerCode) {
		this.twoPartnerCode = twoPartnerCode;
	}
	public String getTwoPartnerUnifiedCode() {
		return twoPartnerUnifiedCode;
	}
	public void setTwoPartnerUnifiedCode(String twoPartnerUnifiedCode) {
		this.twoPartnerUnifiedCode = twoPartnerUnifiedCode;
	}
	public String getTwoPartnerName() {
		return twoPartnerName;
	}
	public void setTwoPartnerName(String twoPartnerName) {
		this.twoPartnerName = twoPartnerName;
	}
	public String getDockingNumber() {
		return dockingNumber;
	}
	public void setDockingNumber(String dockingNumber) {
		this.dockingNumber = dockingNumber;
	}
	public String getDockingDepName() {
		return dockingDepName;
	}
	public void setDockingDepName(String dockingDepName) {
		this.dockingDepName = dockingDepName;
	}
	public String getSubCompanyCode() {
		return subCompanyCode;
	}
	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}
	public String getSubCompanyName() {
		return subCompanyName;
	}
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getCreateCode() {
		return createCode;
	}
	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}
	public String getModifyCode() {
		return modifyCode;
	}
	public void setModifyCode(String modifyCode) {
		this.modifyCode = modifyCode;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOneSubCompanyCode() {
		return oneSubCompanyCode;
	}
	public void setOneSubCompanyCode(String oneSubCompanyCode) {
		this.oneSubCompanyCode = oneSubCompanyCode;
	}
	public String getOneSubCompanyName() {
		return oneSubCompanyName;
	}
	public void setOneSubCompanyName(String oneSubCompanyName) {
		this.oneSubCompanyName = oneSubCompanyName;
	}
	public String getTwoSubCompanyCode() {
		return twoSubCompanyCode;
	}
	public void setTwoSubCompanyCode(String twoSubCompanyCode) {
		this.twoSubCompanyCode = twoSubCompanyCode;
	}
	public String getTwoSubCompanyName() {
		return twoSubCompanyName;
	}
	public void setTwoSubCompanyName(String twoSubCompanyName) {
		this.twoSubCompanyName = twoSubCompanyName;
	}
	public Integer getOperateType() {
		return OperateType;
	}
	public void setOperateType(Integer operateType) {
		OperateType = operateType;
	}
}