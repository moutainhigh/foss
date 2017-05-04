/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class FuelDepartureEntity extends BaseEntity {

	private static final long serialVersionUID = -6765046925134677120L;
	//车辆id
	private String vehicleId;
	//配载车次号
    private String assemblyNo;  

    //发车日期
    private Date departureDate;
    
    // 出发公里数
    private BigDecimal startKm;  

    // 到达公里数
    private BigDecimal arriveKm; 
    
    // 出发站
    private String deptRegionCode;  

    // 出发站名称
    private String deptRegionName;  

    // 目的站
    private String arrvRegionCode;  

    // 目的站名称
    private String arrvRegionName; 

   // 线路
    private String lineCode;  

   //线路名称
    private String lineName;  
    
    // 发车模式
    private String departureMode;  
    
    //实际载重
    private BigDecimal actualLoad;  

    // 体积
    private BigDecimal volume;
    // 司机1
    private String driver1Code;  

    // 司机2
    private String driver2Code;  

    // 司机1姓名
    private String driver1Name;  

    // 司机2姓名
    private String driver2Name;  
    
    // 行驶公里数
    private BigDecimal runKm;
     
    //油耗标准
    private BigDecimal fuelStandard;
    
    //单次发车百公里油耗
    private BigDecimal hdKmFuel;
    
    //单次发车加油升数合计
    private BigDecimal fuelQtyTotal;
    
    //单次发车平均加油单价
    private BigDecimal averagePrice;
    
    //单次发车公里路桥费
    //private BigDecimal kmRoadToll;
    //燃油费总计
    private BigDecimal fuelFeeTotal;
    
    //路桥费总计
    private BigDecimal roadTollTotal;
    
    // 备注
    private String remark;

    //加油信息List
    List <FuelDetailEntity> fuelDetailList = new ArrayList<FuelDetailEntity>();
    
    //路桥费信息list
    List <FuelRoadTollEntity> fuelRoadTollList = new ArrayList<FuelRoadTollEntity>();
    
    
	/**
	 * @return the fuelDetailList
	 */
	public List<FuelDetailEntity> getFuelDetailList() {
		return fuelDetailList;
	}

	/**
	 * @param fuelDetailList the fuelDetailList to set
	 */
	public void setFuelDetailList(List<FuelDetailEntity> fuelDetailList) {
		this.fuelDetailList = fuelDetailList;
	}

	/**
	 * @return the fuelRoadTollList
	 */
	public List<FuelRoadTollEntity> getFuelRoadTollList() {
		return fuelRoadTollList;
	}

	/**
	 * @param fuelRoadTollList the fuelRoadTollList to set
	 */
	public void setFuelRoadTollList(List<FuelRoadTollEntity> fuelRoadTollList) {
		this.fuelRoadTollList = fuelRoadTollList;
	}

	/**
	 * @return the vehicleId
	 */
	public String getVehicleId() {
		return vehicleId;
	}

	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * @return the assemblyNo
	 */
	public String getAssemblyNo() {
		return assemblyNo;
	}

	/**
	 * @param assemblyNo the assemblyNo to set
	 */
	public void setAssemblyNo(String assemblyNo) {
		this.assemblyNo = assemblyNo;
	}

	/**
	 * @return the departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the startKm
	 */
	public BigDecimal getStartKm() {
		return startKm;
	}

	/**
	 * @param startKm the startKm to set
	 */
	public void setStartKm(BigDecimal startKm) {
		this.startKm = startKm;
	}

	/**
	 * @return the arriveKm
	 */
	public BigDecimal getArriveKm() {
		return arriveKm;
	}

	/**
	 * @param arriveKm the arriveKm to set
	 */
	public void setArriveKm(BigDecimal arriveKm) {
		this.arriveKm = arriveKm;
	}

	/**
	 * @return the deptRegionCode
	 */
	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	/**
	 * @param deptRegionCode the deptRegionCode to set
	 */
	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	/**
	 * @return the deptRegionName
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}

	/**
	 * @param deptRegionName the deptRegionName to set
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	/**
	 * @return the arrvRegionCode
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * @param arrvRegionCode the arrvRegionCode to set
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	/**
	 * @return the arrvRegionName
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * @param arrvRegionName the arrvRegionName to set
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * @return the lineCode
	 */
	public String getLineCode() {
		return lineCode;
	}

	/**
	 * @param lineCode the lineCode to set
	 */
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * @return the departureMode
	 */
	public String getDepartureMode() {
		return departureMode;
	}

	/**
	 * @param departureMode the departureMode to set
	 */
	public void setDepartureMode(String departureMode) {
		this.departureMode = departureMode;
	}

	/**
	 * @return the actualLoad
	 */
	public BigDecimal getActualLoad() {
		return actualLoad;
	}

	/**
	 * @param actualLoad the actualLoad to set
	 */
	public void setActualLoad(BigDecimal actualLoad) {
		this.actualLoad = actualLoad;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the driver1Code
	 */
	public String getDriver1Code() {
		return driver1Code;
	}

	/**
	 * @param driver1Code the driver1Code to set
	 */
	public void setDriver1Code(String driver1Code) {
		this.driver1Code = driver1Code;
	}

	/**
	 * @return the driver2Code
	 */
	public String getDriver2Code() {
		return driver2Code;
	}

	/**
	 * @param driver2Code the driver2Code to set
	 */
	public void setDriver2Code(String driver2Code) {
		this.driver2Code = driver2Code;
	}

	/**
	 * @return the driver1Name
	 */
	public String getDriver1Name() {
		return driver1Name;
	}

	/**
	 * @param driver1Name the driver1Name to set
	 */
	public void setDriver1Name(String driver1Name) {
		this.driver1Name = driver1Name;
	}

	/**
	 * @return the driver2Name
	 */
	public String getDriver2Name() {
		return driver2Name;
	}

	/**
	 * @param driver2Name the driver2Name to set
	 */
	public void setDriver2Name(String driver2Name) {
		this.driver2Name = driver2Name;
	}

	/**
	 * @return the runKm
	 */
	public BigDecimal getRunKm() {
		return runKm;
	}

	/**
	 * @param runKm the runKm to set
	 */
	public void setRunKm(BigDecimal runKm) {
		this.runKm = runKm;
	}

	/**
	 * @return the fuelStandard
	 */
	public BigDecimal getFuelStandard() {
		return fuelStandard;
	}

	/**
	 * @param fuelStandard the fuelStandard to set
	 */
	public void setFuelStandard(BigDecimal fuelStandard) {
		this.fuelStandard = fuelStandard;
	}

	/**
	 * @return the fuelFeeTotal
	 */
	public BigDecimal getFuelFeeTotal() {
		return fuelFeeTotal;
	}

	/**
	 * @param fuelFeeTotal the fuelFeeTotal to set
	 */
	public void setFuelFeeTotal(BigDecimal fuelFeeTotal) {
		this.fuelFeeTotal = fuelFeeTotal;
	}

	/**
	 * @return the roadTollTotal
	 */
	public BigDecimal getRoadTollTotal() {
		return roadTollTotal;
	}

	/**
	 * @param roadTollTotal the roadTollTotal to set
	 */
	public void setRoadTollTotal(BigDecimal roadTollTotal) {
		this.roadTollTotal = roadTollTotal;
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
	 * @return the hdKmFuel
	 */
	public BigDecimal getHdKmFuel() {
		return hdKmFuel;
	}

	/**
	 * @param hdKmFuel the hdKmFuel to set
	 */
	public void setHdKmFuel(BigDecimal hdKmFuel) {
		this.hdKmFuel = hdKmFuel;
	}

	/**
	 * @return the fuelQtyTotal
	 */
	public BigDecimal getFuelQtyTotal() {
		return fuelQtyTotal;
	}

	/**
	 * @param fuelQtyTotal the fuelQtyTotal to set
	 */
	public void setFuelQtyTotal(BigDecimal fuelQtyTotal) {
		this.fuelQtyTotal = fuelQtyTotal;
	}

	/**
	 * @return the averagePrice
	 */
	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	/**
	 * @param averagePrice the averagePrice to set
	 */
	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

}
