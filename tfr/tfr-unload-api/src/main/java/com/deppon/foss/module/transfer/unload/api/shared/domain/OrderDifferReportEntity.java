package com.deppon.foss.module.transfer.unload.api.shared.domain;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
public class OrderDifferReportEntity extends BaseEntity {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	* id 
	*/
	private String id;
	
	/** 
	* 报告编号 
	*/
	private String reportNo;
	
	/** 
	* 点单人 
	*/
	private String orderMan;
	
	/** 
	* 点单人code 
	*/
	private String orderManCode;
	
	/** 
	 * 报告状态
	* ING处理中，END处理完成，viod作废 
	*/
	private String reportState;
	
	/** 
	* 创建时间
	*/
	private Date createTime;
	
	/** 
	* 创建人
	*/
	private String createUserCode;
	
	/** 
	* 修改时间
	*/
	private Date modifyTime;
	
	/** 
	* 修改人
	*/
	private String modifyUserCode;
	
	/** 
	* 点单任务编号 
	*/
	private String reportTaskNo;
	/**
	 * 点单差异报告生成开始时间
	 */
	private Date orderDifferReportStartTime;
	/**
	 * 点单差异报告生成结束时间
	 */
	private Date orderDifferReportEndTime;
	/**
	 * 异常状态 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 */
	private String orderDifferReportType;
	/**'
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 差异报告创建部门
	 */
	private String orderOrgCode;
	/**
	* Get() id.
	*
	* @return id.
	*/
	public String getId() {
		return id;
	}
	/**
	* Set() id.
	*
	* @param id.
	*/
	public void setId(String id) {
		this.id = id;
	}
	/**
	* Get() 报告编号.
	*
	* @return 报告编号.
	*/
	public String getReportNo() {
		return reportNo;
	}
	/**
	* Set() 报告编号.
	*
	* @param 报告编号.
	*/
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	* Get() 点单人.
	*
	* @return 点单人.
	*/
	public String getOrderMan() {
		return orderMan;
	}
	/**
	* Set() 点单人.
	*
	* @param 点单人.
	*/
	public void setOrderMan(String orderMan) {
		this.orderMan = orderMan;
	}
	/**
	* Get() 点单人code.
	*
	* @return 点单人code.
	*/
	public String getOrderManCode() {
		return orderManCode;
	}
	/**
	* Set() 点单人code.
	*
	* @param 点单人code.
	*/
	public void setOrderManCode(String orderManCode) {
		this.orderManCode = orderManCode;
	}
	/**
	* Get() ING处理中，END处理完成，viod作废.
	*
	* @return ING处理中，END处理完成，viod作废.
	*/
	public String getReportState() {
		return reportState;
	}
	/**
	* Set() ING处理中，END处理完成，viod作废.
	*
	* @param ING处理中，END处理完成，viod作废.
	*/
	public void setReportState(String reportState) {
		this.reportState = reportState;
	}
	/**
	* Get() 点单任务编号.
	*
	* @return 点单任务编号.
	*/
	public String getReportTaskNo() {
		return reportTaskNo;
	}
	/**
	* Set() 点单任务编号.
	*
	* @param 点单任务编号.
	*/
	public void setReportTaskNo(String reportTaskNo) {
		this.reportTaskNo = reportTaskNo;
	}
	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return  the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * @return  the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return  the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * 点单差异报告生成开始时间
	 * @return  the orderDifferReportStartTime
	 */
	public Date getOrderDifferReportStartTime() {
		return orderDifferReportStartTime;
	}
	/**
	 * 点单差异报告生成开始时间
	 * @param orderDifferReportStartTime the orderDifferReportStartTime to set
	 */
	public void setOrderDifferReportStartTime(Date orderDifferReportStartTime) {
		this.orderDifferReportStartTime = orderDifferReportStartTime;
	}
	/**
	 * 点单差异报告生成结束时间
	 * @return  the orderDifferReportEndTime
	 */
	public Date getOrderDifferReportEndTime() {
		return orderDifferReportEndTime;
	}
	/**
	 * 点单差异报告生成结束时间
	 * @param orderDifferReportEndTime the orderDifferReportEndTime to set
	 */
	public void setOrderDifferReportEndTime(Date orderDifferReportEndTime) {
		this.orderDifferReportEndTime = orderDifferReportEndTime;
	}
	/**
	 * 异常状态 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 * @return  the orderDifferReportType
	 */
	public String getOrderDifferReportType() {
		return orderDifferReportType;
	}
	/**
	 * 异常状态 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 * @param orderDifferReportType the orderDifferReportType to set
	 */
	public void setOrderDifferReportType(String orderDifferReportType) {
		this.orderDifferReportType = orderDifferReportType;
	}
	/**
	 * 交单号
	 * @return  the handoverNo
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	/**
	 * 交接单号
	 * @param handoverNo the handoverNo to set
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	/**
	 * 差异报告创建部门（点单部门）
	 * @return  the orderOrgCode
	 */
	public String getOrderOrgCode() {
		return orderOrgCode;
	}
	/**
	 * 差异报告创建部门(点单部门)
	 * @param orderOrgCode the orderOrgCode to set
	 */
	public void setOrderOrgCode(String orderOrgCode) {
		this.orderOrgCode = orderOrgCode;
	}
}
