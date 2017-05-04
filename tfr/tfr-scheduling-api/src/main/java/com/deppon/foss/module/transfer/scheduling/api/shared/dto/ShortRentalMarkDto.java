package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @title: ShortRentalMarkDto
 * @description： 短途租车标记Dto
 * @author： ruilibao
 * @date： 2014-5-28 下午3:02:34
 */
public class ShortRentalMarkDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2675622414307981170L;

	// 用车部门
	private String useCarDepartment;
	//查询用车开始时间
	private Date  usecarDateBeginTime ;
	//查询用车结算时间
	private Date  usecarDateEndTime;
	//约车编号
	private String inviteVehicleNo;
	//车牌号
	private String vehicleNo;
	//车辆状态
	private String vehicleState;
	//当前部门标识
	private String departmentSignle;
	//部门列表
	private List<String> orgCodeList;
	//当前登录部门Code
	private String orgCode;
	//车辆任务ID,用于校验车辆是否重复标记
	private List<String> truckTaskIdList;
	//出发部门列表， 用于计算出最大公里数
	private List<String> origOrgCodeList;
	//到达部门列表，用于计算出最大公里数
	private List<String> destOrgCodeList;
	
	/**
	 * @return the origOrgCodeList
	 */
	public List<String> getOrigOrgCodeList() {
		return origOrgCodeList;
	}
	/**
	 * @param origOrgCodeList the origOrgCodeList to set
	 */
	public void setOrigOrgCodeList(List<String> origOrgCodeList) {
		this.origOrgCodeList = origOrgCodeList;
	}
	/**
	 * @return the destOrgCodeList
	 */
	public List<String> getDestOrgCodeList() {
		return destOrgCodeList;
	}
	/**
	 * @param destOrgCodeList the destOrgCodeList to set
	 */
	public void setDestOrgCodeList(List<String> destOrgCodeList) {
		this.destOrgCodeList = destOrgCodeList;
	}
	/**
	 * @return the truckTaskId
	 */
	public List<String> getTruckTaskIdList() {
		return truckTaskIdList;
	}
	/**
	 * @param truckTaskId the truckTaskId to set
	 */
	public void setTruckTaskIdList(List<String> truckTaskIdList) {
		this.truckTaskIdList = truckTaskIdList;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgCodeList
	 */
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	/**
	 * @param orgCodeList the orgCodeList to set
	 */
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	/**
	 * @return the useCarDepartment
	 */
	public String getUseCarDepartment() {
		return useCarDepartment;
	}
	/**
	 * @param useCarDepartment the useCarDepartment to set
	 */
	public void setUseCarDepartment(String useCarDepartment) {
		this.useCarDepartment = useCarDepartment;
	}
	/**
	 * @return the usecarDateBeginTime
	 */
	public Date getUsecarDateBeginTime() {
		return usecarDateBeginTime;
	}
	/**
	 * @param usecarDateBeginTime the usecarDateBeginTime to set
	 */
	public void setUsecarDateBeginTime(Date usecarDateBeginTime) {
		this.usecarDateBeginTime = usecarDateBeginTime;
	}
	/**
	 * @return the usecarDateEndTime
	 */
	public Date getUsecarDateEndTime() {
		return usecarDateEndTime;
	}
	/**
	 * @param usecarDateEndTime the usecarDateEndTime to set
	 */
	public void setUsecarDateEndTime(Date usecarDateEndTime) {
		this.usecarDateEndTime = usecarDateEndTime;
	}
	/**
	 * @return the inviteVehicleNo
	 */
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}
	/**
	 * @param inviteVehicleNo the inviteVehicleNo to set
	 */
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the vehicleState
	 */
	public String getVehicleState() {
		return vehicleState;
	}
	/**
	 * @param vehicleState the vehicleState to set
	 */
	public void setVehicleState(String vehicleState) {
		this.vehicleState = vehicleState;
	}
	/**
	 * @return the departmentSignle
	 */
	public String getDepartmentSignle() {
		return departmentSignle;
	}
	/**
	 * @param departmentSignle the departmentSignle to set
	 */
	public void setDepartmentSignle(String departmentSignle) {
		this.departmentSignle = departmentSignle;
	}
	
//	private String orgCode;//部门编码
//	
//	private String createDept;//创建部门编码
//	
//	private List<String> handoverBillNoList;//交接单号
//	
//	private List<String> deliverbillNoList;//派送单号
//	
//	private List<String> stowageBillNoList;//配载单号
//	
//	private String departmentSignle;//部门标识
//
//	private String queryType;//查询类型
//	
//	private  String incomeCategories;//收入类别
//	
//	private String rentalUse;//租车用途
//	/**车队的顶级车队所服务营业部的编码集合**/
//	private List<String> orgCodeList;
//	/**下面4个字段用于更新工作流信息*/
//	/**预提状态**/
//	private String accruedState;
//	/**预提工作流号**/
//	private String accruedWorkNo;
//	/**预提工作流处理结果**/
//	private String accruedWorkResult;
	
	
}
