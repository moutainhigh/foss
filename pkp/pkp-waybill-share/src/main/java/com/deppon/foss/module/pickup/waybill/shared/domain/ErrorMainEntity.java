package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 差错上报主信息
 * @author foss-273279-Liding
 * @date 2015-8-17 16:41:05
 */
public class ErrorMainEntity implements Serializable {

	private static final long serialVersionUID = -9021959415392159800L;

	//差错编号
	private String errorId;
	//上报人工号
	private String repEmpcode;
	//上报人姓名
	private String repEmpName;
	//上报人职位
	private String repEmpJob;
	//上报人部门标杆编码
	private String repDeptCode;
	//上报人部门名称
	private String repDeptName;
	//差错类别（数据字典代码：errorCategory）
	private String errorCategory;
	private String errorCategoryVal;
	//差错类型id
	private String errorTypeId;
	//差错类型名称
	private String errorTypeName;
	//文件标准id
	private long docStandarId;
	//文件标准名称
	private String docStandarName;
	//差错状态（数据字典代码：errStatus）
	private String errorStatus;
	//上报时间
	private String repTime;
	//运单号 
	private String wayBillNum;
	//上报人事业部标杆编码
	private String repDivisionCode;
	private String repDivisionName;
	//收货部门标杆编码
	private String receiveDeptCode;
	//收货部门名称 
	private String receiveDeptName;
	//最后的反馈有效时间
	private String lastAvaibleFBTime;
	//是否已删除（数据字典代码：yesorno）
	private String del;

	private String namespace;
	private String needFeedback;
	/**
	 * errorId <p>getter method</p>
	 * @author 273279
	 * @return  the errorId
	 */
	public String getErrorId() {
		return errorId;
	}
	/**
	 * errorId <p>setter method</p>
	 * @author 273279
	 * @param errorId the errorId to set
	 */
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	/**
	 * repEmpcode <p>getter method</p>
	 * @author 273279
	 * @return  the repEmpcode
	 */
	public String getRepEmpcode() {
		return repEmpcode;
	}
	/**
	 * repEmpcode <p>setter method</p>
	 * @author 273279
	 * @param repEmpcode the repEmpcode to set
	 */
	public void setRepEmpcode(String repEmpcode) {
		this.repEmpcode = repEmpcode;
	}
	/**
	 * repEmpName <p>getter method</p>
	 * @author 273279
	 * @return  the repEmpName
	 */
	public String getRepEmpName() {
		return repEmpName;
	}
	/**
	 * repEmpName <p>setter method</p>
	 * @author 273279
	 * @param repEmpName the repEmpName to set
	 */
	public void setRepEmpName(String repEmpName) {
		this.repEmpName = repEmpName;
	}
	/**
	 * repEmpJob <p>getter method</p>
	 * @author 273279
	 * @return  the repEmpJob
	 */
	public String getRepEmpJob() {
		return repEmpJob;
	}
	/**
	 * repEmpJob <p>setter method</p>
	 * @author 273279
	 * @param repEmpJob the repEmpJob to set
	 */
	public void setRepEmpJob(String repEmpJob) {
		this.repEmpJob = repEmpJob;
	}
	/**
	 * repDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the repDeptCode
	 */
	public String getRepDeptCode() {
		return repDeptCode;
	}
	/**
	 * repDeptCode <p>setter method</p>
	 * @author 273279
	 * @param repDeptCode the repDeptCode to set
	 */
	public void setRepDeptCode(String repDeptCode) {
		this.repDeptCode = repDeptCode;
	}
	/**
	 * repDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the repDeptName
	 */
	public String getRepDeptName() {
		return repDeptName;
	}
	/**
	 * repDeptName <p>setter method</p>
	 * @author 273279
	 * @param repDeptName the repDeptName to set
	 */
	public void setRepDeptName(String repDeptName) {
		this.repDeptName = repDeptName;
	}
	/**
	 * errorCategory <p>getter method</p>
	 * @author 273279
	 * @return  the errorCategory
	 */
	public String getErrorCategory() {
		return errorCategory;
	}
	/**
	 * errorCategory <p>setter method</p>
	 * @author 273279
	 * @param errorCategory the errorCategory to set
	 */
	public void setErrorCategory(String errorCategory) {
		this.errorCategory = errorCategory;
	}
	/**
	 * errorCategoryVal <p>getter method</p>
	 * @author 273279
	 * @return  the errorCategoryVal
	 */
	public String getErrorCategoryVal() {
		return errorCategoryVal;
	}
	/**
	 * errorCategoryVal <p>setter method</p>
	 * @author 273279
	 * @param errorCategoryVal the errorCategoryVal to set
	 */
	public void setErrorCategoryVal(String errorCategoryVal) {
		this.errorCategoryVal = errorCategoryVal;
	}
	/**
	 * errorTypeId <p>getter method</p>
	 * @author 273279
	 * @return  the errorTypeId
	 */
	public String getErrorTypeId() {
		return errorTypeId;
	}
	/**
	 * errorTypeId <p>setter method</p>
	 * @author 273279
	 * @param errorTypeId the errorTypeId to set
	 */
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	/**
	 * docStandarId <p>getter method</p>
	 * @author 273279
	 * @return  the docStandarId
	 */
	public long getDocStandarId() {
		return docStandarId;
	}
	/**
	 * docStandarId <p>setter method</p>
	 * @author 273279
	 * @param docStandarId the docStandarId to set
	 */
	public void setDocStandarId(long docStandarId) {
		this.docStandarId = docStandarId;
	}
	/**
	 * errorStatus <p>getter method</p>
	 * @author 273279
	 * @return  the errorStatus
	 */
	public String getErrorStatus() {
		return errorStatus;
	}
	/**
	 * errorStatus <p>setter method</p>
	 * @author 273279
	 * @param errorStatus the errorStatus to set
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	/**
	 * repTime <p>getter method</p>
	 * @author 273279
	 * @return  the repTime
	 */
	public String getRepTime() {
		return repTime;
	}
	/**
	 * repTime <p>setter method</p>
	 * @author 273279
	 * @param repTime the repTime to set
	 */
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	/**
	 * wayBillNum <p>getter method</p>
	 * @author 273279
	 * @return  the wayBillNum
	 */
	public String getWayBillNum() {
		return wayBillNum;
	}
	/**
	 * wayBillNum <p>setter method</p>
	 * @author 273279
	 * @param wayBillNum the wayBillNum to set
	 */
	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}
	/**
	 * repDivisionCode <p>getter method</p>
	 * @author 273279
	 * @return  the repDivisionCode
	 */
	public String getRepDivisionCode() {
		return repDivisionCode;
	}
	/**
	 * repDivisionCode <p>setter method</p>
	 * @author 273279
	 * @param repDivisionCode the repDivisionCode to set
	 */
	public void setRepDivisionCode(String repDivisionCode) {
		this.repDivisionCode = repDivisionCode;
	}
	/**
	 * repDivisionName <p>getter method</p>
	 * @author 273279
	 * @return  the repDivisionName
	 */
	public String getRepDivisionName() {
		return repDivisionName;
	}
	/**
	 * repDivisionName <p>setter method</p>
	 * @author 273279
	 * @param repDivisionName the repDivisionName to set
	 */
	public void setRepDivisionName(String repDivisionName) {
		this.repDivisionName = repDivisionName;
	}
	/**
	 * receiveDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the receiveDeptCode
	 */
	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}
	/**
	 * receiveDeptCode <p>setter method</p>
	 * @author 273279
	 * @param receiveDeptCode the receiveDeptCode to set
	 */
	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}
	/**
	 * receiveDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the receiveDeptName
	 */
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	/**
	 * receiveDeptName <p>setter method</p>
	 * @author 273279
	 * @param receiveDeptName the receiveDeptName to set
	 */
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	/**
	 * lastAvaibleFBTime <p>getter method</p>
	 * @author 273279
	 * @return  the lastAvaibleFBTime
	 */
	public String getLastAvaibleFBTime() {
		return lastAvaibleFBTime;
	}
	/**
	 * lastAvaibleFBTime <p>setter method</p>
	 * @author 273279
	 * @param lastAvaibleFBTime the lastAvaibleFBTime to set
	 */
	public void setLastAvaibleFBTime(String lastAvaibleFBTime) {
		this.lastAvaibleFBTime = lastAvaibleFBTime;
	}
	/**
	 * del <p>getter method</p>
	 * @author 273279
	 * @return  the del
	 */
	public String getDel() {
		return del;
	}
	/**
	 * del <p>setter method</p>
	 * @author 273279
	 * @param del the del to set
	 */
	public void setDel(String del) {
		this.del = del;
	}
	/**
	 * errorTypeName <p>getter method</p>
	 * @author 273279
	 * @return  the errorTypeName
	 */
	public String getErrorTypeName() {
		return errorTypeName;
	}
	/**
	 * errorTypeName <p>setter method</p>
	 * @author 273279
	 * @param errorTypeName the errorTypeName to set
	 */
	public void setErrorTypeName(String errorTypeName) {
		this.errorTypeName = errorTypeName;
	}
	/**
	 * docStandarName <p>getter method</p>
	 * @author 273279
	 * @return  the docStandarName
	 */
	public String getDocStandarName() {
		return docStandarName;
	}
	/**
	 * docStandarName <p>setter method</p>
	 * @author 273279
	 * @param docStandarName the docStandarName to set
	 */
	public void setDocStandarName(String docStandarName) {
		this.docStandarName = docStandarName;
	}
	/**
	 * namespace <p>getter method</p>
	 * @author 273279
	 * @return  the namespace
	 */
	public String getNamespace() {
		return namespace;
	}
	/**
	 * namespace <p>setter method</p>
	 * @author 273279
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	/**
	 * needFeedback <p>getter method</p>
	 * @author 273279
	 * @return  the needFeedback
	 */
	public String getNeedFeedback() {
		return needFeedback;
	}
	/**
	 * needFeedback <p>setter method</p>
	 * @author 273279
	 * @param needFeedback the needFeedback to set
	 */
	public void setNeedFeedback(String needFeedback) {
		this.needFeedback = needFeedback;
	}

}
