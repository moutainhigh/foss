package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;


/**
* @description TPS约车信息推送实体类
* @author 283250-foss-liuyi
* @update 2016年1月20日 上午9:16:51
*/
@XmlRootElement(name="VehicleRequestEntity")
public class TpsAboutVehicleRequestEntity  implements Serializable{
	/**
	* @fields serialVersionUID
	* @author 283250-foss-liuyi
	* @update 2016年1月20日 上午9:18:43
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 6647136724102913711L;
	
	/** 外请车状态 */
	private String status;
	
	/** 外请车编号 */
	private String inviteNo;
	
	/** 申请时间 */
	private Date applyTime;
	
	/** 使用类别 */
	private String usePurpose;
	
	/** 用车类别 */
	private String useType;
	
	/** 车型 CODE*/
	private String orderVehicleModel;
	
	/** 预计用车时间 */
	private Date predictUseTime;
	
	/** 用车地址 */
	private String useAddress;
	
	/** 发货人 */
	private String clientName;
	
	/** 发货人电话 */
	private String clientContactPhone;
	
	/** 货物包装 */
	private String goodsPackege;
	
	/** 货物件数 */
	private Integer goodsQty;
	
	/** 重量 */
	private BigDecimal weight;
	
	/** 体积 */
	private BigDecimal volume;
	
	/** 其他要求  */
	private String requirment;
	
	/** 派车车队 编码 */
	private String dispatchTransDept;
	
	/** 申请网点名称[用车部门] */
	private String applyOrgName;
	
	/** 申请网点编码[用车部门] */
	private String applyOrgCode;
	
	/** 申请人员名称 */
	private String applyEmpName;
		
	/** 申请人员编码 */
	private String applyEmpCode;
	
	/** 固定电话 */
	private String telephoneNo;
	
	/** 手机 */
	private String mobilephoneNo;

	/** 是否完成开单  */
	private String isFinishBill;
	
	/** 是否经过到达部门  */
	private String isPassByArrivedDept;
	
	/** 到达部门code */
	private String arrivedDeptCode;
	
	/**  是否回程 */
	private String isReturn;
	
	/**  回程价格  */
	private BigDecimal returnCost;
	
	/** 是否合同车   */
	private String isContractVehicle;
	
	/**  合同线路  */
	private String contractLineCode;
	
	/** 使用状态  */
	private String useStatus;
	
	/** 货物名称  */
	private String goodsName;
	
	/** 装货时间  */
	private Date loadGoodsTime;
	
	/**顶级车队编号*/
    private String topFleetCode;
    
    /**抱到时间*/
    private Date arrivalTime;
    
    //269701--foss--lln 2015-08-11 begin
    /**到达地址--省*/
    private String provinceName;
    private String provinceCode;
    /**到达地址--市*/
    private String cityName;
    private String cityCode;
    /**到达地址--区/县*/
    private String areaName;
    private String areaCode;
    /**到达地址--详细地址*/
    private String detailAdress;
    /**到达地址*/
    private String arrivedAddress;
   //269701--foss--lln 2015-08-11 begin
    
    /**是否现场看货*/
    private String isScaneSeeGoods;
    
    /**货物是否可叠加*/
    private String isGoodsOver;
    /** 派车车队名称  */
	private String dispatchTransDeptName;
	/** 经过到达部门名称  */
	private String arrivedDeptName;
	/** 申请人所在部门名称  */
	private String applyEmpOrgName;
	/** 顶级车队  */
	private String topFleetName;
	
	/**原foss受理外请车 所需数据start*/
	/** 预计到达时间  */
	private Date perdictArriveTime;
	/** 请车价格 */
	private BigDecimal inviteCost;
	/** 车牌号*/
	private String vehicleNo;
	/** 币种*/
	private String currencyCode;
	/** 信息部名称*/
	private String ministryinformation;
	/**原foss受理外请车 所需数据end*/
	
	/**原foss审核外请车 所需数据start*/
	/** 审核结果备注*/
	private String notes;
	/** 是否营业部自请车*/
	private String isSaleDepartmentCompany;
	/** 拼车类型*/
	private String carpoolingType;
	/**原foss审核外请车 所需数据end*/
	
	
	/**原foss基础 增加字段start*/
	/**价编号*/
	private String inquiryNo;
	/**原foss基础 增加字段end*/
	
	
	/**接口新加判断逻辑需要的参数start*/
	/**是否需要更新，针对重复约车数据   Y更新 N不更新*/
	private String isUpdate;
	/**接口新加判断逻辑需要的参数end*/
	
	/** 信息部编码(tps系统编码)--310248*/
	private String ministryinformationcode;

	public String getMinistryinformationcode() {
		return ministryinformationcode;
	}

	public void setMinistryinformationcode(String ministryinformationcode) {
		this.ministryinformationcode = ministryinformationcode;
	}
	/**
	 * @return the dispatchTransDeptName
	 */
	public String getDispatchTransDeptName() {
		return dispatchTransDeptName;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Date getPerdictArriveTime() {
		return perdictArriveTime;
	}

	public void setPerdictArriveTime(Date perdictArriveTime) {
		this.perdictArriveTime = perdictArriveTime;
	}

	public BigDecimal getInviteCost() {
		return inviteCost;
	}

	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMinistryinformation() {
		return ministryinformation;
	}

	public void setMinistryinformation(String ministryinformation) {
		this.ministryinformation = ministryinformation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getIsSaleDepartmentCompany() {
		return isSaleDepartmentCompany;
	}

	public void setIsSaleDepartmentCompany(String isSaleDepartmentCompany) {
		this.isSaleDepartmentCompany = isSaleDepartmentCompany;
	}

	public String getCarpoolingType() {
		return carpoolingType;
	}

	public void setCarpoolingType(String carpoolingType) {
		this.carpoolingType = carpoolingType;
	}

	public String getInquiryNo() {
		return inquiryNo;
	}

	public void setInquiryNo(String inquiryNo) {
		this.inquiryNo = inquiryNo;
	}

	/**
	 * @param dispatchTransDeptName the dispatchTransDeptName to set
	 */
	public void setDispatchTransDeptName(String dispatchTransDeptName) {
		this.dispatchTransDeptName = dispatchTransDeptName;
	}

	/**
	 * @return the arrivedDeptName
	 */
	public String getArrivedDeptName() {
		return arrivedDeptName;
	}

	/**
	 * @param arrivedDeptName the arrivedDeptName to set
	 */
	public void setArrivedDeptName(String arrivedDeptName) {
		this.arrivedDeptName = arrivedDeptName;
	}

	public String getIsScaneSeeGoods() {
		return isScaneSeeGoods;
	}

	public void setIsScaneSeeGoods(String isScaneSeeGoods) {
		this.isScaneSeeGoods = isScaneSeeGoods;
	}

	public String getIsGoodsOver() {
		return isGoodsOver;
	}

	public void setIsGoodsOver(String isGoodsOver) {
		this.isGoodsOver = isGoodsOver;
	}

	/**
	 * 获得status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 获得inviteNo
	 * @return the inviteNo
	 */
	public String getInviteNo() {
		return inviteNo;
	}
	
	/**
	 * 获得applyTime
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	
	/**
	 * 获得usePurpose
	 * @return the usePurpose
	 */
	public String getUsePurpose() {
		return usePurpose;
	}
	
	/**
	 * 获得useType
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}
	
	/**
	 * 获得orderVehicleModel
	 * @return the orderVehicleModel
	 */
	public String getOrderVehicleModel() {
		return orderVehicleModel;
	}
	
	/**
	 * 获得predictUseTime
	 * @return the predictUseTime
	 */
	public Date getPredictUseTime() {
		return predictUseTime;
	}
	
	/**
	 * 获得useAddress
	 * @return the useAddress
	 */
	public String getUseAddress() {
		return useAddress;
	}
	
	/**
	 * 获得clientName
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	
	/**
	 * 获得clientContactPhone
	 * @return the clientContactPhone
	 */
	public String getClientContactPhone() {
		return clientContactPhone;
	}
	
	/**
	 * 获得goodsPackege
	 * @return the goodsPackege
	 */
	public String getGoodsPackege() {
		return goodsPackege;
	}
	
	/**
	 * 获得goodsQty
	 * @return the goodsQty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 获得weight
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 获得volume
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 获得requirment
	 * @return the requirment
	 */
	public String getRequirment() {
		return requirment;
	}
	
	/**
	 * 获得dispatchTransDept
	 * @return the dispatchTransDept
	 */
	public String getDispatchTransDept() {
		return dispatchTransDept;
	}
	
	/**
	 * 获得applyOrgName
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	
	/**
	 * 获得applyOrgCode
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	
	/**
	 * 获得applyEmpName
	 * @return the applyEmpName
	 */
	public String getApplyEmpName() {
		return applyEmpName;
	}
	
	/**
	 * 获得applyEmpCode
	 * @return the applyEmpCode
	 */
	public String getApplyEmpCode() {
		return applyEmpCode;
	}
	
	/**
	 * 获得telephoneNo
	 * @return the telephoneNo
	 */
	public String getTelephoneNo() {
		return telephoneNo;
	}
	
	/**
	 * 获得mobilephoneNo
	 * @return the mobilephoneNo
	 */
	public String getMobilephoneNo() {
		return mobilephoneNo;
	}
	
		/**
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 设置inviteNo
	 * @param inviteNo the inviteNo to set
	 */
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
	
	/**
	 * 设置applyTime
	 * @param applyTime the applyTime to set
	 */
	@DateFormat
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	/**
	 * 设置usePurpose
	 * @param usePurpose the usePurpose to set
	 */
	public void setUsePurpose(String usePurpose) {
		this.usePurpose = usePurpose;
	}
	
	/**
	 * 设置useType
	 * @param useType the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	/**
	 * 设置orderVehicleModel
	 * @param orderVehicleModel the orderVehicleModel to set
	 */
	public void setOrderVehicleModel(String orderVehicleModel) {
		this.orderVehicleModel = orderVehicleModel;
	}
	
	/**
	 * 设置predictUseTime
	 * @param predictUseTime the predictUseTime to set
	 */
	@DateFormat
	public void setPredictUseTime(Date predictUseTime) {
		this.predictUseTime = predictUseTime;
	}
	
	/**
	 * 设置useAddress
	 * @param useAddress the useAddress to set
	 */
	public void setUseAddress(String useAddress) {
		this.useAddress = useAddress;
	}
	
	/**
	 * 设置clientName
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	/**
	 * 设置clientContactPhone
	 * @param clientContactPhone the clientContactPhone to set
	 */
	public void setClientContactPhone(String clientContactPhone) {
		this.clientContactPhone = clientContactPhone;
	}
	
	/**
	 * 设置goodsPackege
	 * @param goodsPackege the goodsPackege to set
	 */
	public void setGoodsPackege(String goodsPackege) {
		this.goodsPackege = goodsPackege;
	}
	
	/**
	 * 设置goodsQty
	 * @param goodsQty the goodsQty to set
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * 设置weight
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 设置volume
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 设置requirment
	 * @param requirment the requirment to set
	 */
	public void setRequirment(String requirment) {
		this.requirment = requirment;
	}
	
	/**
	 * 设置dispatchTransDept
	 * @param dispatchTransDept the dispatchTransDept to set
	 */
	public void setDispatchTransDept(String dispatchTransDept) {
		this.dispatchTransDept = dispatchTransDept;
	}
	
	/**
	 * 设置applyOrgName
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	
	/**
	 * 设置applyOrgCode
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	
	/**
	 * 设置applyEmpName
	 * @param applyEmpName the applyEmpName to set
	 */
	public void setApplyEmpName(String applyEmpName) {
		this.applyEmpName = applyEmpName;
	}
	
	/**
	 * 设置applyEmpCode
	 * @param applyEmpCode the applyEmpCode to set
	 */
	public void setApplyEmpCode(String applyEmpCode) {
		this.applyEmpCode = applyEmpCode;
	}
	
	/**
	 * 设置telephoneNo
	 * @param telephoneNo the telephoneNo to set
	 */
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	/**
	 * 设置mobilephoneNo
	 * @param mobilephoneNo the mobilephoneNo to set
	 */
	public void setMobilephoneNo(String mobilephoneNo) {
		this.mobilephoneNo = mobilephoneNo;
	}
	
	/**
	 * 获得isFinishBill
	 * @return the isFinishBill
	 */
	public String getIsFinishBill() {
		return isFinishBill;
	}

	/**
	 * 设置isFinishBill
	 * @param isFinishBill the isFinishBill to set
	 */
	public void setIsFinishBill(String isFinishBill) {
		this.isFinishBill = isFinishBill;
	}
	
	/**
	 * 获得isPassByArrivedDept
	 * @return the isPassByArrivedDept
	 */
	public String getIsPassByArrivedDept() {
		return isPassByArrivedDept;
	}

	/**
	 * 设置isPassByArrivedDept
	 * @param isPassByArrivedDept the isPassByArrivedDept to set
	 */
	public void setIsPassByArrivedDept(String isPassByArrivedDept) {
		this.isPassByArrivedDept = isPassByArrivedDept;
	}

	/**
	 * 获得arrivedDeptCode
	 * @return the arrivedDeptCode
	 */
	public String getArrivedDeptCode() {
		return arrivedDeptCode;
	}

	/**
	 * 设置arrivedDeptCode
	 * @param arrivedDeptCode the arrivedDeptCode to set
	 */
	public void setArrivedDeptCode(String arrivedDeptCode) {
		this.arrivedDeptCode = arrivedDeptCode;
	}
	
	/**
	 * 获得isReturn
	 * @return the isReturn
	 */
	public String getIsReturn() {
		return isReturn;
	}

	/**
	 * 设置isReturn
	 * @param isReturn the isReturn to set
	 */
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	/**
	 * 获得returnCost
	 * @return the returnCost
	 */
	public BigDecimal getReturnCost() {
		return returnCost;
	}

	/**
	 * 设置returnCost
	 * @param returnCost the returnCost to set
	 */
	public void setReturnCost(BigDecimal returnCost) {
		this.returnCost = returnCost;
	}

	/**
	 * 获得isContractVehicle
	 * @return the isContractVehicle
	 */
	public String getIsContractVehicle() {
		return isContractVehicle;
	}

	/**
	 * 设置isContractVehicle
	 * @param isContractVehicle the isContractVehicle to set
	 */
	public void setIsContractVehicle(String isContractVehicle) {
		this.isContractVehicle = isContractVehicle;
	}

	/**
	 * 获得contractLineCode
	 * @return the contractLineCode
	 */
	public String getContractLineCode() {
		return contractLineCode;
	}

	/**
	 * 设置contractLineCode
	 * @param contractLineCode the contractLineCode to set
	 */
	public void setContractLineCode(String contractLineCode) {
		this.contractLineCode = contractLineCode;
	}
	
	/**
	 * 获得useStatus
	 * @return the useStatus
	 */
	public String getUseStatus() {
		return useStatus;
	}

	/**
	 * 设置useStatus
	 * @param useStatus the useStatus to set
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	
	/**
	 * 获得goodsName
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置goodsName
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获得loadGoodsTime
	 * @return the loadGoodsTime
	 */
	public Date getLoadGoodsTime() {
		return loadGoodsTime;
	}

	/**
	 * 设置loadGoodsTime
	 * @param loadGoodsTime the loadGoodsTime to set
	 */
	@DateFormat
	public void setLoadGoodsTime(Date loadGoodsTime) {
		this.loadGoodsTime = loadGoodsTime;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[status,").append(status).append("]");
		stringBuilder.append("[inviteNo,").append(inviteNo).append("]");
		stringBuilder.append("[applyTime,").append(applyTime).append("]");
		stringBuilder.append("[usePurpose,").append(usePurpose).append("]");
		stringBuilder.append("[useType,").append(useType).append("]");
		stringBuilder.append("[orderVehicleModel,").append(orderVehicleModel).append("]");
		stringBuilder.append("[predictUseTime,").append(predictUseTime).append("]");
		stringBuilder.append("[useAddress,").append(useAddress).append("]");
		//269701--foss--lln 2015-08-11 begin
		//省
		stringBuilder.append("[provinceName,").append(provinceName).append("]");
		stringBuilder.append("[provinceCode,").append(provinceCode).append("]");
		//市
		stringBuilder.append("[cityName,").append(cityName).append("]");
		stringBuilder.append("[cityCode,").append(cityCode).append("]");
		//区/县
		stringBuilder.append("[areaName,").append(areaName).append("]");
		stringBuilder.append("[areaCode,").append(areaCode).append("]");
		//详细地址
		stringBuilder.append("[detailAdress,").append(detailAdress).append("]");
		//269701--foss--lln 2015-08-11 end
		stringBuilder.append("[arrivedAddress,").append(arrivedAddress).append("]");
		stringBuilder.append("[clientName,").append(clientName).append("]");
		stringBuilder.append("[clientContactPhone,").append(clientContactPhone).append("]");
		stringBuilder.append("[goodsPackege,").append(goodsPackege).append("]");
		stringBuilder.append("[goodsQty,").append(goodsQty).append("]");
		stringBuilder.append("[weight,").append(weight).append("]");
		stringBuilder.append("[volume,").append(volume).append("]");
		stringBuilder.append("[requirment,").append(requirment).append("]");
		stringBuilder.append("[dispatchTransDept,").append(dispatchTransDept).append("]");
		stringBuilder.append("[applyOrgName,").append(applyOrgName).append("]");
		stringBuilder.append("[applyOrgCode,").append(applyOrgCode).append("]");
		stringBuilder.append("[applyEmpName,").append(applyEmpName).append("]");
		stringBuilder.append("[applyEmpCode,").append(applyEmpCode).append("]");
		stringBuilder.append("[telephoneNo,").append(telephoneNo).append("]");
		stringBuilder.append("[mobilephoneNo,").append(mobilephoneNo).append("]");
		stringBuilder.append("[isFinishBill,").append(isFinishBill).append("]");
		stringBuilder.append("[useStatus,").append(useStatus).append("]");
		stringBuilder.append("[goodsName,").append(goodsName).append("]");
		stringBuilder.append("[loadGoodsTime,").append(loadGoodsTime).append("]");
		return stringBuilder.toString();
	}

	public String getTopFleetCode() {
		return topFleetCode;
	}

	public void setTopFleetCode(String topFleetCode) {
		this.topFleetCode = topFleetCode;
	}

	/**
	 * @return the arrivalTime
	 */
	public Date getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	//269701--foss--lln 2015-08-11 begin
	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the detailAdress
	 */
	public String getDetailAdress() {
		return detailAdress;
	}

	/**
	 * @param detailAdress the detailAdress to set
	 */
	public void setDetailAdress(String detailAdress) {
		this.detailAdress = detailAdress;
	}
	
	
	/**
	 * @return the provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	//269701--foss--lln 2015-08-11 end
	public String getArrivedAddress() {
		return arrivedAddress;
	}

	public void setArrivedAddress(String arrivedAddress) {
		this.arrivedAddress = arrivedAddress;
	}

	public String getApplyEmpOrgName() {
		return applyEmpOrgName;
	}

	public void setApplyEmpOrgName(String applyEmpOrgName) {
		this.applyEmpOrgName = applyEmpOrgName;
	}

	public String getTopFleetName() {
		return topFleetName;
	}

	public void setTopFleetName(String topFleetName) {
		this.topFleetName = topFleetName;
	}
	
	
	
}
