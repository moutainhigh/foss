package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发车到达信息 同步给tps  dto仅用于查询
 * @author 200978  xiaobingcheng
 * 2015-1-15
 */
public class DepartArriveToTpsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1443096119127063741L;

		// 约车编码
		private String fossAboutCode;

		// 车牌号
	    private String vehicleNo;
	    
	    // 出发时间
	    private Date setoutDate;
	    
	    // 到达时间
	    private Date achieveDate;
	    
	    // 配载日期
	    private Date stowageDate;
	    
	    // 车次号
	    private String vehicletips;
	    
	    // 总重量
	    private BigDecimal totalWeight;
	    
	    // 总体积
	    private BigDecimal totalVolume;
	    
	    // 总运费/成交价
	    private BigDecimal totalPrice;
	    
	    // 额定净空
	    private BigDecimal nominaLcearance;
	    
	    // 额定载重
	    private BigDecimal nominalLoad;
	    
	    // 备注
	    private String remark;

	    // 出发部门  272681 2015/10/21
	    private String carDepartment;
	    
	    // 到达部门  272681 2015/10/21
	    private String arrivalDepartment;
	    
	    // 用车部门code  272681 2015/10/23
	    private String carDepartmentCode;
	    
	    // 到达部门code  272681 2015/10/23
	    private String arrivalDepartmentCode;
	    
		public String getFossAboutCode() {
			return fossAboutCode;
		}

		public void setFossAboutCode(String fossAboutCode) {
			this.fossAboutCode = fossAboutCode;
		}

		public String getVehicleNo() {
			return vehicleNo;
		}

		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}

		public Date getSetoutDate() {
			return setoutDate;
		}

		public void setSetoutDate(Date setoutDate) {
			this.setoutDate = setoutDate;
		}

		public Date getAchieveDate() {
			return achieveDate;
		}

		public void setAchieveDate(Date achieveDate) {
			this.achieveDate = achieveDate;
		}

		public Date getStowageDate() {
			return stowageDate;
		}

		public void setStowageDate(Date stowageDate) {
			this.stowageDate = stowageDate;
		}

		public String getVehicletips() {
			return vehicletips;
		}

		public void setVehicletips(String vehicletips) {
			this.vehicletips = vehicletips;
		}

		public BigDecimal getTotalWeight() {
			return totalWeight;
		}

		public void setTotalWeight(BigDecimal totalWeight) {
			this.totalWeight = totalWeight;
		}

		public BigDecimal getTotalVolume() {
			return totalVolume;
		}

		public void setTotalVolume(BigDecimal totalVolume) {
			this.totalVolume = totalVolume;
		}

		public BigDecimal getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}

		public BigDecimal getNominaLcearance() {
			return nominaLcearance;
		}

		public void setNominaLcearance(BigDecimal nominaLcearance) {
			this.nominaLcearance = nominaLcearance;
		}

		public BigDecimal getNominalLoad() {
			return nominalLoad;
		}

		public void setNominalLoad(BigDecimal nominalLoad) {
			this.nominalLoad = nominalLoad;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getCarDepartment() {
			return carDepartment;
		}

		public void setCarDepartment(String carDepartment) {
			this.carDepartment = carDepartment;
		}

		public String getArrivalDepartment() {
			return arrivalDepartment;
		}

		public void setArrivalDepartment(String arrivalDepartment) {
			this.arrivalDepartment = arrivalDepartment;
		}

		public String getCarDepartmentCode() {
			return carDepartmentCode;
		}

		public void setCarDepartmentCode(String carDepartmentCode) {
			this.carDepartmentCode = carDepartmentCode;
		}

		public String getArrivalDepartmentCode() {
			return arrivalDepartmentCode;
		}

		public void setArrivalDepartmentCode(String arrivalDepartmentCode) {
			this.arrivalDepartmentCode = arrivalDepartmentCode;
		}

		
	    
	    
	
}
