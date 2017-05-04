package com.deppon.foss.module.transfer.common.api.shared.domain;


import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 描述：StowageBillEntity-Class类
 * 作者：系统一键代码生成
 * 时间：2014-12-23 10:32:53
 */
@XmlRootElement(name="StowageBillEntity")
public class StowageBillEntity extends BaseEntity {

    // 序列化ID
    private static final long serialVersionUID = 1L;
    
    // 主键
    private String id;
    
    // FOSS约车编码
    private String fossCode;

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
    
    // 当前交易次数
    private Integer currTransTimes;
    
    // 备注
    private String remark;
    
    // 逻辑状态
    private Long logicState;
    
    // 创建时间
    private Date createDate;
    
    // 创建人
    private String createUser;
    
    // 修改时间
    private Date modifyDate;
    
    // 修改人
    private String modifyUser;
    
    // 用车部门  272681 2015/10/23
    private String carDepartment;
    
    // 到达部门  272681 2015/10/23
    private String arrivalDepartment;
    
    // 用车部门code  272681 2015/10/23
    private String carDepartmentCode;
    
    // 到达部门code  272681 2015/10/23
    private String arrivalDepartmentCode;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFossCode() {
		return fossCode;
	}

	public void setFossCode(String fossCode) {
		this.fossCode = fossCode;
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

	public Integer getCurrTransTimes() {
		return currTransTimes;
	}

	public void setCurrTransTimes(Integer currTransTimes) {
		this.currTransTimes = currTransTimes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLogicState() {
		return logicState;
	}

	public void setLogicState(Long logicState) {
		this.logicState = logicState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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