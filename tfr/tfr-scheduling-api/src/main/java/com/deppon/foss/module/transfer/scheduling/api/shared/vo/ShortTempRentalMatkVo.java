package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MultiCarTakeGoodsDto;

public class ShortTempRentalMatkVo {
	
	private Date useCareDate;//用车日期	
	private String inviteVehicleNo;//约车编号
	private String carReason;//用车原因
	private String remark;//备注
	private String isRepeateMark;//是否多次标记
	private List<ShortRentalMarkEntity> shortRentalMarkEntityList; //租车标记选中数据
	private List<MultiCarTakeGoodsDto> multiCarTakeGoodsDtoList;
	private String departmentSignle;//部门标识
	private String salesVehiclePlatformName;//请车平台名称
	//租车用途
	private String shortRentalUse;
    
	/**新增短途租车费用承担部门 author:106162 Date:2017-04-26*/
	//private String bearFeesDept;//费用承担部门
	//private String bearFeesDeptCode;//费用承担部门编码
	
	/**
	 * @return the shortRentalUse
	 */
	public String getShortRentalUse() {
		return shortRentalUse;
	}
	/**
	 * @param shortRentalUse the shortRentalUse to set
	 */
	public void setShortRentalUse(String shortRentalUse) {
		this.shortRentalUse = shortRentalUse;
	}
	/**
	 * @return the multiCarTakeGoodsDtoList
	 */
	public List<MultiCarTakeGoodsDto> getMultiCarTakeGoodsDtoList() {
		return multiCarTakeGoodsDtoList;
	}
	/**
	 * @param multiCarTakeGoodsDtoList the multiCarTakeGoodsDtoList to set
	 */
	public void setMultiCarTakeGoodsDtoList(List<MultiCarTakeGoodsDto> multiCarTakeGoodsDtoList) {
		this.multiCarTakeGoodsDtoList = multiCarTakeGoodsDtoList;
	}
	/**
	 * @return the useCareDate
	 */
	public Date getUseCareDate() {
		return useCareDate;
	}
	/**
	 * @param useCareDate the useCareDate to set
	 */
	public void setUseCareDate(Date useCareDate) {
		this.useCareDate = useCareDate;
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
	 * @return the carReason
	 */
	public String getCarReason() {
		return carReason;
	}
	/**
	 * @param carReason the carReason to set
	 */
	public void setCarReason(String carReason) {
		this.carReason = carReason;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the isRepeateMark
	 */
	public String getIsRepeateMark() {
		return isRepeateMark;
	}
	/**
	 * @param isRepeateMark the isRepeateMark to set
	 */
	public void setIsRepeateMark(String isRepeateMark) {
		this.isRepeateMark = isRepeateMark;
	}
	/**
	 * @return the rentalMarkEntityList
	 */
	public List<ShortRentalMarkEntity> getShortRentalMarkEntityList() {
		return shortRentalMarkEntityList;
	}
	/**
	 * @param rentalMarkEntityList the rentalMarkEntityList to set
	 */
	public void setShortRentalMarkEntityList(List<ShortRentalMarkEntity> shortRentalMarkEntity) {
		this.shortRentalMarkEntityList = shortRentalMarkEntity;
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
	/**
	 * @return the salesVehiclePlatformName
	 */
	public String getSalesVehiclePlatformName() {
		return salesVehiclePlatformName;
	}
	/**
	 * @param salesVehiclePlatformName the salesVehiclePlatformName to set
	 */
	public void setSalesVehiclePlatformName(String salesVehiclePlatformName) {
		this.salesVehiclePlatformName = salesVehiclePlatformName;
	}
	/**
	 * @return the bearFeesDept
	 */
	/*public String getBearFeesDept() {
		return bearFeesDept;
	}*/
	/**
	 * @param setBearFeesDept the setBearFeesDept to set
	 */
	/*public void setBearFeesDept(String bearFeesDept) {
		this.bearFeesDept = bearFeesDept;
	}*/
	/**
	 * @return the bearFeesDeptCode
	 */
	/*public String getBearFeesDeptCode() {
		return bearFeesDeptCode;
	}*/
	/**
	 * @param setBearFeesDeptCode the setBearFeesDeptCode to set
	 */
	/*public void setBearFeesDeptCode(String bearFeesDeptCode) {
		this.bearFeesDeptCode = bearFeesDeptCode;
	}*/
   
	
}
