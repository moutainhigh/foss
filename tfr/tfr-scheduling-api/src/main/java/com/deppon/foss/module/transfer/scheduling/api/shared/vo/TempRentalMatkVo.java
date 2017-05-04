package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MultiCarTakeGoodsDto;

public class TempRentalMatkVo {
	
	private String rentalUse;//租车用途
	private Date useCareDate;//用车日期
	private String inviteVehicleNo;//约车编号
	private String acceptPerson;//约车受理人
	private String acceptPersonCode;//约车受理人代码
	private String carReason;//用车原因
	private List<String> smallTicketNumList;//小票单号
	private String remark;//备注
	private String isRepeateMark;//是否多次标记
	private List<MultiCarTakeGoodsDto> multiCarTakeGoodsDtoList; //多车走货标记
	private List<RentalMarkEntity> rentalMarkEntityList; //租车标记选中数据
	private List<WKTfrBillEntity>  wkTfrBillEntityList;  //租车标记选中数据(快递运单和快递交接单)  313352  gyy
	private String departmentSignle;//部门标识
	private String consultPriceNo;//询价编号
	private String bearFeesDept;//费用承担部门
	private String bearFeesDeptCode;//费用承担部门编码
	
	public String getBearFeesDept() {
		return bearFeesDept;
	}
	public void setBearFeesDept(String bearFeesDept) {
		this.bearFeesDept = bearFeesDept;
	}
	public String getBearFeesDeptCode() {
		return bearFeesDeptCode;
	}
	public void setBearFeesDeptCode(String bearFeesDeptCode) {
		this.bearFeesDeptCode = bearFeesDeptCode;
	}

	//326027--begin 
	private String salesVehiclePlatformName;//请车平台名称
	
	private String singleSum; //单票金额 332219
	
	public String getSalesVehiclePlatformName() {
		return salesVehiclePlatformName;
	}
	public void setSalesVehiclePlatformName(String salesVehiclePlatformName) {
		this.salesVehiclePlatformName = salesVehiclePlatformName;
	}
	
	private String useVehiclePlatform;//使用请车平台

	public String getUseVehiclePlatform() {
		return useVehiclePlatform;
	}
	public void setUseVehiclePlatform(String useVehiclePlatform) {
		this.useVehiclePlatform = useVehiclePlatform;
	}
	/*public String getNosalesVehiclePlatformName() {
		return nosalesVehiclePlatformName;
	}
	public void setNosalesVehiclePlatformName(String nosalesVehiclePlatformName) {
		this.nosalesVehiclePlatformName = nosalesVehiclePlatformName;
	}
	private String nosalesVehiclePlatformName;*/
	//326027--end
	public String getConsultPriceNo() {
		return consultPriceNo;
	}
	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}
	public String getIsRepeateMark() {
		return isRepeateMark;
	}
	public void setIsRepeateMark(String isRepeateMark) {
		this.isRepeateMark = isRepeateMark;
	}
	public String getRentalUse() {
		return rentalUse;
	}
	public void setRentalUse(String rentalUse) {
		this.rentalUse = rentalUse;
	}

	public Date getUseCareDate() {
		return useCareDate;
	}
	public void setUseCareDate(Date useCareDate) {
		this.useCareDate = useCareDate;
	}
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}
	public String getAcceptPerson() {
		return acceptPerson;
	}
	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	public String getAcceptPersonCode() {
		return acceptPersonCode;
	}
	public void setAcceptPersonCode(String acceptPersonCode) {
		this.acceptPersonCode = acceptPersonCode;
	}
	public String getCarReason() {
		return carReason;
	}
	public void setCarReason(String carReason) {
		this.carReason = carReason;
	}
	public List<String> getSmallTicketNumList() {
		return smallTicketNumList;
	}
	public void setSmallTicketNumList(List<String> smallTicketNumList) {
		this.smallTicketNumList = smallTicketNumList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<MultiCarTakeGoodsDto> getMultiCarTakeGoodsDtoList() {
		return multiCarTakeGoodsDtoList;
	}
	public List<WKTfrBillEntity> getWkTfrBillEntityList() {
		return wkTfrBillEntityList;
	}
	public void setWkTfrBillEntityList(List<WKTfrBillEntity> wkTfrBillEntityList) {
		this.wkTfrBillEntityList = wkTfrBillEntityList;
	}
	public void setMultiCarTakeGoodsDtoList(
			List<MultiCarTakeGoodsDto> multiCarTakeGoodsDtoList) {
		this.multiCarTakeGoodsDtoList = multiCarTakeGoodsDtoList;
	}
	public List<RentalMarkEntity> getRentalMarkEntityList() {
		return rentalMarkEntityList;
	}
	public void setRentalMarkEntityList(List<RentalMarkEntity> rentalMarkEntityList) {
		this.rentalMarkEntityList = rentalMarkEntityList;
	}
	public String getDepartmentSignle() {
		return departmentSignle;
	}
	public void setDepartmentSignle(String departmentSignle) {
		this.departmentSignle = departmentSignle;
	}
	public String getSingleSum() {
		return singleSum;
	}
	public void setSingleSum(String singleSum) {
		this.singleSum = singleSum;
	}
}
