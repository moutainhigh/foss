package com.deppon.foss.module.pickup.waybill.shared.domain;


public class LDErrSubNoWaybillEntity extends ErrorSubEntity {

	private static final long serialVersionUID = 6072660913788667368L;

	//表id
	private Long subNoWaybillId;
  
	//责任人直接上级工号
    private String respSupervisorCode;

    //责任人直接上级名称
    private String respSupervisorName;

    //责任人部门负责人工号
    private String respDeptResperCode;

    //责任人部门负责人名称
    private String respDeptResperName;

    //责任人所在区域经理工号
    private String respSeniorManagerCode;

    //责任人所在区域经理名称
    private String respSeniorManagerName;

    //责任经理工号
    private String respManagerCode;

    //责任经理名称
    private String respManagerName;

    //责任经理直接上级工号
    private String respManagerSupCode;

    //责任经理直接上级名称
    private String respManagerSupName;

    //责任大区标杆编码
    private String respRegionCode;

    //责任大区名称
    private String respRegionName;

    //车牌号
    private String carCode;

    //单据编号
    private String billCode;

    //盘点时间
    private String checkTime;

    //坐支金额
    private String amountSpent;

    //自离人员工号
    private String fromStaffCode;

    //自离人员姓名
    private String fromStaffName;

    //所属部门标杆编码
    private String belongDeptCode;

    //所属部门名称
    private String belongDeptName;

    //旷工类型
    private String skipWorkType;

    //
    private String skipWorkDate;

    //旷工开始时间
    private String skipWorkStarttime;

    //旷工截止时间
    private String skipWorkEndtime;

    //损失金额
    private String loseMoney;

    //认领时间
    private String claimTime;

    //到帐时间
    private String arriveTime;

    //认领编号
    private String claimCode;

    //汇款类型
    private String paymentType;

    //责任财务部标杆编码
    private String respFinalDeptCode;

    //责任财务部名称
    private String respFinalDeptName;

    //付款人标杆编码
    private String paymentCode;

    //付款人名称
    private String paymentName;

    //付款部门标杆编码
    private String paymentDeptCode;

    //付款部门名称
    private String paymentDeptName;

    //事故时间
    private String accidentTime;

    //事故类型
    private String accidentType;

    //交通事故差错类型
    private String traficAccErrType;

    //备案差错编号
    private String recordErrCode;

    //车辆类型
    private String carType;

    //是否有指挥
    private String hasLeader;

    //指挥人标杆编码
    private String leaderCode;

    //指挥人名称
    private String leaderName;

    //营业部标杆编码
    private String businessDeptCode;

    //营业部名称
    private String businessDeptName;

    //营业部负责人责任确定方式
    private String makeSureType;

    //是否联系保险公司定损
    private String isContactIsuComp;

    //是否已取得定损单
    private String isGetDamagerBill;

    //责任类型
    private String respType;

    //我方赔偿对方金额
    private String amountToOtherParty;

    //对方赔偿我方金额
    private String amountToOurParty;

    //我方定损金额
    private String ourDamagerMoney;

    //对方定损金额
    private String twoPartyDamagerMoney;

    //可向保险公司收回事故赔款金额
    private String accidentPayMoney;

    //事故费用
    private String accidentFee;

    //主驾驶员工号
    private String mainDriverCode;

    //主驾驶员姓名
    private String mainDriverName;

    //主驾驶员所在部门标杆编码
    private String mainDriverDeptCode;

    //主驾驶员所在部门名称
    private String mainDriverDeptName;

    //驾驶员职位
    private String driverMajor;

    //副驾驶员1工号
    private String deputyDriverCode1;

    //副驾驶员1姓名
    private String deputyDriverName1;

    //副驾驶员1所在部门标杆编码
    private String deputyDriverDeptCode1;

    //副驾驶员1所在部门名称
    private String deputyDriverDeptName1;

    //副驾驶员2工号
    private String deputyDriverCode2;

    //副驾驶员2姓名
    private String deputyDriverName2;

    //副驾驶员2所在部门标杆编码
    private String deputyDriverDeptCode2;

    //副驾驶员2所在部门名称
    private String deputyDriverDeptName2;

    //事故跟踪处理部门标杆编码
    private String accidentDealDeptCode;

    //事故跟踪处理部门名称
    private String accidentDealDeptName;

    //客户名称
    private String clientName;

    //客户编码
    private String clientCode;

    //坏账金额
    private String badAmountMoney;

    //固定资产编码-净值
    private String fixedAssetNetValue;

    //盘点单编号
    private String checkBillCode;

    //短款金额
    private String shortAmount;

    //是否单据丢失
    private String isLoseBill;

    //外请车司机姓名
    private String outsideCarDriverName;


	/**
	 * subNoWaybillId <p>getter method</p>
	 * @author 273279
	 * @return  the subNoWaybillId
	 */
	public Long getSubNoWaybillId() {
		return subNoWaybillId;
	}


	/**
	 * subNoWaybillId <p>setter method</p>
	 * @author 273279
	 * @param subNoWaybillId the subNoWaybillId to set
	 */
	public void setSubNoWaybillId(Long subNoWaybillId) {
		this.subNoWaybillId = subNoWaybillId;
	}


	/**
	 * respSupervisorCode <p>getter method</p>
	 * @author 273279
	 * @return  the respSupervisorCode
	 */
	public String getRespSupervisorCode() {
		return respSupervisorCode;
	}


	/**
	 * respSupervisorCode <p>setter method</p>
	 * @author 273279
	 * @param respSupervisorCode the respSupervisorCode to set
	 */
	public void setRespSupervisorCode(String respSupervisorCode) {
		this.respSupervisorCode = respSupervisorCode;
	}


	/**
	 * respSupervisorName <p>getter method</p>
	 * @author 273279
	 * @return  the respSupervisorName
	 */
	public String getRespSupervisorName() {
		return respSupervisorName;
	}


	/**
	 * respSupervisorName <p>setter method</p>
	 * @author 273279
	 * @param respSupervisorName the respSupervisorName to set
	 */
	public void setRespSupervisorName(String respSupervisorName) {
		this.respSupervisorName = respSupervisorName;
	}


	/**
	 * respDeptResperCode <p>getter method</p>
	 * @author 273279
	 * @return  the respDeptResperCode
	 */
	public String getRespDeptResperCode() {
		return respDeptResperCode;
	}


	/**
	 * respDeptResperCode <p>setter method</p>
	 * @author 273279
	 * @param respDeptResperCode the respDeptResperCode to set
	 */
	public void setRespDeptResperCode(String respDeptResperCode) {
		this.respDeptResperCode = respDeptResperCode;
	}


	/**
	 * respDeptResperName <p>getter method</p>
	 * @author 273279
	 * @return  the respDeptResperName
	 */
	public String getRespDeptResperName() {
		return respDeptResperName;
	}


	/**
	 * respDeptResperName <p>setter method</p>
	 * @author 273279
	 * @param respDeptResperName the respDeptResperName to set
	 */
	public void setRespDeptResperName(String respDeptResperName) {
		this.respDeptResperName = respDeptResperName;
	}


	/**
	 * respSeniorManagerCode <p>getter method</p>
	 * @author 273279
	 * @return  the respSeniorManagerCode
	 */
	public String getRespSeniorManagerCode() {
		return respSeniorManagerCode;
	}


	/**
	 * respSeniorManagerCode <p>setter method</p>
	 * @author 273279
	 * @param respSeniorManagerCode the respSeniorManagerCode to set
	 */
	public void setRespSeniorManagerCode(String respSeniorManagerCode) {
		this.respSeniorManagerCode = respSeniorManagerCode;
	}


	/**
	 * respSeniorManagerName <p>getter method</p>
	 * @author 273279
	 * @return  the respSeniorManagerName
	 */
	public String getRespSeniorManagerName() {
		return respSeniorManagerName;
	}


	/**
	 * respSeniorManagerName <p>setter method</p>
	 * @author 273279
	 * @param respSeniorManagerName the respSeniorManagerName to set
	 */
	public void setRespSeniorManagerName(String respSeniorManagerName) {
		this.respSeniorManagerName = respSeniorManagerName;
	}


	/**
	 * respManagerCode <p>getter method</p>
	 * @author 273279
	 * @return  the respManagerCode
	 */
	public String getRespManagerCode() {
		return respManagerCode;
	}


	/**
	 * respManagerCode <p>setter method</p>
	 * @author 273279
	 * @param respManagerCode the respManagerCode to set
	 */
	public void setRespManagerCode(String respManagerCode) {
		this.respManagerCode = respManagerCode;
	}


	/**
	 * respManagerName <p>getter method</p>
	 * @author 273279
	 * @return  the respManagerName
	 */
	public String getRespManagerName() {
		return respManagerName;
	}


	/**
	 * respManagerName <p>setter method</p>
	 * @author 273279
	 * @param respManagerName the respManagerName to set
	 */
	public void setRespManagerName(String respManagerName) {
		this.respManagerName = respManagerName;
	}


	/**
	 * respManagerSupCode <p>getter method</p>
	 * @author 273279
	 * @return  the respManagerSupCode
	 */
	public String getRespManagerSupCode() {
		return respManagerSupCode;
	}


	/**
	 * respManagerSupCode <p>setter method</p>
	 * @author 273279
	 * @param respManagerSupCode the respManagerSupCode to set
	 */
	public void setRespManagerSupCode(String respManagerSupCode) {
		this.respManagerSupCode = respManagerSupCode;
	}


	/**
	 * respManagerSupName <p>getter method</p>
	 * @author 273279
	 * @return  the respManagerSupName
	 */
	public String getRespManagerSupName() {
		return respManagerSupName;
	}


	/**
	 * respManagerSupName <p>setter method</p>
	 * @author 273279
	 * @param respManagerSupName the respManagerSupName to set
	 */
	public void setRespManagerSupName(String respManagerSupName) {
		this.respManagerSupName = respManagerSupName;
	}


	/**
	 * respRegionCode <p>getter method</p>
	 * @author 273279
	 * @return  the respRegionCode
	 */
	public String getRespRegionCode() {
		return respRegionCode;
	}


	/**
	 * respRegionCode <p>setter method</p>
	 * @author 273279
	 * @param respRegionCode the respRegionCode to set
	 */
	public void setRespRegionCode(String respRegionCode) {
		this.respRegionCode = respRegionCode;
	}


	/**
	 * respRegionName <p>getter method</p>
	 * @author 273279
	 * @return  the respRegionName
	 */
	public String getRespRegionName() {
		return respRegionName;
	}


	/**
	 * respRegionName <p>setter method</p>
	 * @author 273279
	 * @param respRegionName the respRegionName to set
	 */
	public void setRespRegionName(String respRegionName) {
		this.respRegionName = respRegionName;
	}


	/**
	 * carCode <p>getter method</p>
	 * @author 273279
	 * @return  the carCode
	 */
	public String getCarCode() {
		return carCode;
	}


	/**
	 * carCode <p>setter method</p>
	 * @author 273279
	 * @param carCode the carCode to set
	 */
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}


	/**
	 * billCode <p>getter method</p>
	 * @author 273279
	 * @return  the billCode
	 */
	public String getBillCode() {
		return billCode;
	}


	/**
	 * billCode <p>setter method</p>
	 * @author 273279
	 * @param billCode the billCode to set
	 */
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}


	/**
	 * checkTime <p>getter method</p>
	 * @author 273279
	 * @return  the checkTime
	 */
	public String getCheckTime() {
		return checkTime;
	}


	/**
	 * checkTime <p>setter method</p>
	 * @author 273279
	 * @param checkTime the checkTime to set
	 */
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}


	/**
	 * amountSpent <p>getter method</p>
	 * @author 273279
	 * @return  the amountSpent
	 */
	public String getAmountSpent() {
		return amountSpent;
	}


	/**
	 * amountSpent <p>setter method</p>
	 * @author 273279
	 * @param amountSpent the amountSpent to set
	 */
	public void setAmountSpent(String amountSpent) {
		this.amountSpent = amountSpent;
	}


	/**
	 * fromStaffCode <p>getter method</p>
	 * @author 273279
	 * @return  the fromStaffCode
	 */
	public String getFromStaffCode() {
		return fromStaffCode;
	}


	/**
	 * fromStaffCode <p>setter method</p>
	 * @author 273279
	 * @param fromStaffCode the fromStaffCode to set
	 */
	public void setFromStaffCode(String fromStaffCode) {
		this.fromStaffCode = fromStaffCode;
	}


	/**
	 * fromStaffName <p>getter method</p>
	 * @author 273279
	 * @return  the fromStaffName
	 */
	public String getFromStaffName() {
		return fromStaffName;
	}


	/**
	 * fromStaffName <p>setter method</p>
	 * @author 273279
	 * @param fromStaffName the fromStaffName to set
	 */
	public void setFromStaffName(String fromStaffName) {
		this.fromStaffName = fromStaffName;
	}


	/**
	 * belongDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the belongDeptCode
	 */
	public String getBelongDeptCode() {
		return belongDeptCode;
	}


	/**
	 * belongDeptCode <p>setter method</p>
	 * @author 273279
	 * @param belongDeptCode the belongDeptCode to set
	 */
	public void setBelongDeptCode(String belongDeptCode) {
		this.belongDeptCode = belongDeptCode;
	}


	/**
	 * belongDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the belongDeptName
	 */
	public String getBelongDeptName() {
		return belongDeptName;
	}


	/**
	 * belongDeptName <p>setter method</p>
	 * @author 273279
	 * @param belongDeptName the belongDeptName to set
	 */
	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}


	/**
	 * skipWorkType <p>getter method</p>
	 * @author 273279
	 * @return  the skipWorkType
	 */
	public String getSkipWorkType() {
		return skipWorkType;
	}


	/**
	 * skipWorkType <p>setter method</p>
	 * @author 273279
	 * @param skipWorkType the skipWorkType to set
	 */
	public void setSkipWorkType(String skipWorkType) {
		this.skipWorkType = skipWorkType;
	}

	/**
	 * skipWorkDate <p>getter method</p>
	 * @author 273279
	 * @return  the skipWorkDate
	 */
	public String getSkipWorkDate() {
		return skipWorkDate;
	}

	/**
	 * skipWorkDate <p>setter method</p>
	 * @author 273279
	 * @param skipWorkDate the skipWorkDate to set
	 */
	public void setSkipWorkDate(String skipWorkDate) {
		this.skipWorkDate = skipWorkDate;
	}


	/**
	 * skipWorkStarttime <p>getter method</p>
	 * @author 273279
	 * @return  the skipWorkStarttime
	 */
	public String getSkipWorkStarttime() {
		return skipWorkStarttime;
	}


	/**
	 * skipWorkStarttime <p>setter method</p>
	 * @author 273279
	 * @param skipWorkStarttime the skipWorkStarttime to set
	 */
	public void setSkipWorkStarttime(String skipWorkStarttime) {
		this.skipWorkStarttime = skipWorkStarttime;
	}


	/**
	 * skipWorkEndtime <p>getter method</p>
	 * @author 273279
	 * @return  the skipWorkEndtime
	 */
	public String getSkipWorkEndtime() {
		return skipWorkEndtime;
	}


	/**
	 * skipWorkEndtime <p>setter method</p>
	 * @author 273279
	 * @param skipWorkEndtime the skipWorkEndtime to set
	 */
	public void setSkipWorkEndtime(String skipWorkEndtime) {
		this.skipWorkEndtime = skipWorkEndtime;
	}


	/**
	 * loseMoney <p>getter method</p>
	 * @author 273279
	 * @return  the loseMoney
	 */
	public String getLoseMoney() {
		return loseMoney;
	}


	/**
	 * loseMoney <p>setter method</p>
	 * @author 273279
	 * @param loseMoney the loseMoney to set
	 */
	public void setLoseMoney(String loseMoney) {
		this.loseMoney = loseMoney;
	}


	/**
	 * claimTime <p>getter method</p>
	 * @author 273279
	 * @return  the claimTime
	 */
	public String getClaimTime() {
		return claimTime;
	}


	/**
	 * claimTime <p>setter method</p>
	 * @author 273279
	 * @param claimTime the claimTime to set
	 */
	public void setClaimTime(String claimTime) {
		this.claimTime = claimTime;
	}


	/**
	 * arriveTime <p>getter method</p>
	 * @author 273279
	 * @return  the arriveTime
	 */
	public String getArriveTime() {
		return arriveTime;
	}


	/**
	 * arriveTime <p>setter method</p>
	 * @author 273279
	 * @param arriveTime the arriveTime to set
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}


	/**
	 * claimCode <p>getter method</p>
	 * @author 273279
	 * @return  the claimCode
	 */
	public String getClaimCode() {
		return claimCode;
	}


	/**
	 * claimCode <p>setter method</p>
	 * @author 273279
	 * @param claimCode the claimCode to set
	 */
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}


	/**
	 * paymentType <p>getter method</p>
	 * @author 273279
	 * @return  the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}


	/**
	 * paymentType <p>setter method</p>
	 * @author 273279
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	/**
	 * respFinalDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the respFinalDeptCode
	 */
	public String getRespFinalDeptCode() {
		return respFinalDeptCode;
	}


	/**
	 * respFinalDeptCode <p>setter method</p>
	 * @author 273279
	 * @param respFinalDeptCode the respFinalDeptCode to set
	 */
	public void setRespFinalDeptCode(String respFinalDeptCode) {
		this.respFinalDeptCode = respFinalDeptCode;
	}


	/**
	 * respFinalDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the respFinalDeptName
	 */
	public String getRespFinalDeptName() {
		return respFinalDeptName;
	}


	/**
	 * respFinalDeptName <p>setter method</p>
	 * @author 273279
	 * @param respFinalDeptName the respFinalDeptName to set
	 */
	public void setRespFinalDeptName(String respFinalDeptName) {
		this.respFinalDeptName = respFinalDeptName;
	}


	/**
	 * paymentCode <p>getter method</p>
	 * @author 273279
	 * @return  the paymentCode
	 */
	public String getPaymentCode() {
		return paymentCode;
	}


	/**
	 * paymentCode <p>setter method</p>
	 * @author 273279
	 * @param paymentCode the paymentCode to set
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}


	/**
	 * paymentName <p>getter method</p>
	 * @author 273279
	 * @return  the paymentName
	 */
	public String getPaymentName() {
		return paymentName;
	}


	/**
	 * paymentName <p>setter method</p>
	 * @author 273279
	 * @param paymentName the paymentName to set
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}


	/**
	 * paymentDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the paymentDeptCode
	 */
	public String getPaymentDeptCode() {
		return paymentDeptCode;
	}


	/**
	 * paymentDeptCode <p>setter method</p>
	 * @author 273279
	 * @param paymentDeptCode the paymentDeptCode to set
	 */
	public void setPaymentDeptCode(String paymentDeptCode) {
		this.paymentDeptCode = paymentDeptCode;
	}


	/**
	 * paymentDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the paymentDeptName
	 */
	public String getPaymentDeptName() {
		return paymentDeptName;
	}


	/**
	 * paymentDeptName <p>setter method</p>
	 * @author 273279
	 * @param paymentDeptName the paymentDeptName to set
	 */
	public void setPaymentDeptName(String paymentDeptName) {
		this.paymentDeptName = paymentDeptName;
	}


	/**
	 * accidentTime <p>getter method</p>
	 * @author 273279
	 * @return  the accidentTime
	 */
	public String getAccidentTime() {
		return accidentTime;
	}


	/**
	 * accidentTime <p>setter method</p>
	 * @author 273279
	 * @param accidentTime the accidentTime to set
	 */
	public void setAccidentTime(String accidentTime) {
		this.accidentTime = accidentTime;
	}


	/**
	 * accidentType <p>getter method</p>
	 * @author 273279
	 * @return  the accidentType
	 */
	public String getAccidentType() {
		return accidentType;
	}


	/**
	 * accidentType <p>setter method</p>
	 * @author 273279
	 * @param accidentType the accidentType to set
	 */
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}


	/**
	 * traficAccErrType <p>getter method</p>
	 * @author 273279
	 * @return  the traficAccErrType
	 */
	public String getTraficAccErrType() {
		return traficAccErrType;
	}


	/**
	 * traficAccErrType <p>setter method</p>
	 * @author 273279
	 * @param traficAccErrType the traficAccErrType to set
	 */
	public void setTraficAccErrType(String traficAccErrType) {
		this.traficAccErrType = traficAccErrType;
	}


	/**
	 * recordErrCode <p>getter method</p>
	 * @author 273279
	 * @return  the recordErrCode
	 */
	public String getRecordErrCode() {
		return recordErrCode;
	}


	/**
	 * recordErrCode <p>setter method</p>
	 * @author 273279
	 * @param recordErrCode the recordErrCode to set
	 */
	public void setRecordErrCode(String recordErrCode) {
		this.recordErrCode = recordErrCode;
	}


	/**
	 * carType <p>getter method</p>
	 * @author 273279
	 * @return  the carType
	 */
	public String getCarType() {
		return carType;
	}


	/**
	 * carType <p>setter method</p>
	 * @author 273279
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}


	/**
	 * hasLeader <p>getter method</p>
	 * @author 273279
	 * @return  the hasLeader
	 */
	public String getHasLeader() {
		return hasLeader;
	}


	/**
	 * hasLeader <p>setter method</p>
	 * @author 273279
	 * @param hasLeader the hasLeader to set
	 */
	public void setHasLeader(String hasLeader) {
		this.hasLeader = hasLeader;
	}


	/**
	 * leaderCode <p>getter method</p>
	 * @author 273279
	 * @return  the leaderCode
	 */
	public String getLeaderCode() {
		return leaderCode;
	}


	/**
	 * leaderCode <p>setter method</p>
	 * @author 273279
	 * @param leaderCode the leaderCode to set
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}


	/**
	 * leaderName <p>getter method</p>
	 * @author 273279
	 * @return  the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}


	/**
	 * leaderName <p>setter method</p>
	 * @author 273279
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}


	/**
	 * businessDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the businessDeptCode
	 */
	public String getBusinessDeptCode() {
		return businessDeptCode;
	}


	/**
	 * businessDeptCode <p>setter method</p>
	 * @author 273279
	 * @param businessDeptCode the businessDeptCode to set
	 */
	public void setBusinessDeptCode(String businessDeptCode) {
		this.businessDeptCode = businessDeptCode;
	}


	/**
	 * businessDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the businessDeptName
	 */
	public String getBusinessDeptName() {
		return businessDeptName;
	}


	/**
	 * businessDeptName <p>setter method</p>
	 * @author 273279
	 * @param businessDeptName the businessDeptName to set
	 */
	public void setBusinessDeptName(String businessDeptName) {
		this.businessDeptName = businessDeptName;
	}


	/**
	 * makeSureType <p>getter method</p>
	 * @author 273279
	 * @return  the makeSureType
	 */
	public String getMakeSureType() {
		return makeSureType;
	}


	/**
	 * makeSureType <p>setter method</p>
	 * @author 273279
	 * @param makeSureType the makeSureType to set
	 */
	public void setMakeSureType(String makeSureType) {
		this.makeSureType = makeSureType;
	}


	/**
	 * isContactIsuComp <p>getter method</p>
	 * @author 273279
	 * @return  the isContactIsuComp
	 */
	public String getIsContactIsuComp() {
		return isContactIsuComp;
	}


	/**
	 * isContactIsuComp <p>setter method</p>
	 * @author 273279
	 * @param isContactIsuComp the isContactIsuComp to set
	 */
	public void setIsContactIsuComp(String isContactIsuComp) {
		this.isContactIsuComp = isContactIsuComp;
	}


	/**
	 * isGetDamagerBill <p>getter method</p>
	 * @author 273279
	 * @return  the isGetDamagerBill
	 */
	public String getIsGetDamagerBill() {
		return isGetDamagerBill;
	}


	/**
	 * isGetDamagerBill <p>setter method</p>
	 * @author 273279
	 * @param isGetDamagerBill the isGetDamagerBill to set
	 */
	public void setIsGetDamagerBill(String isGetDamagerBill) {
		this.isGetDamagerBill = isGetDamagerBill;
	}


	/**
	 * respType <p>getter method</p>
	 * @author 273279
	 * @return  the respType
	 */
	public String getRespType() {
		return respType;
	}


	/**
	 * respType <p>setter method</p>
	 * @author 273279
	 * @param respType the respType to set
	 */
	public void setRespType(String respType) {
		this.respType = respType;
	}


	/**
	 * amountToOtherParty <p>getter method</p>
	 * @author 273279
	 * @return  the amountToOtherParty
	 */
	public String getAmountToOtherParty() {
		return amountToOtherParty;
	}


	/**
	 * amountToOtherParty <p>setter method</p>
	 * @author 273279
	 * @param amountToOtherParty the amountToOtherParty to set
	 */
	public void setAmountToOtherParty(String amountToOtherParty) {
		this.amountToOtherParty = amountToOtherParty;
	}


	/**
	 * amountToOurParty <p>getter method</p>
	 * @author 273279
	 * @return  the amountToOurParty
	 */
	public String getAmountToOurParty() {
		return amountToOurParty;
	}


	/**
	 * amountToOurParty <p>setter method</p>
	 * @author 273279
	 * @param amountToOurParty the amountToOurParty to set
	 */
	public void setAmountToOurParty(String amountToOurParty) {
		this.amountToOurParty = amountToOurParty;
	}


	/**
	 * ourDamagerMoney <p>getter method</p>
	 * @author 273279
	 * @return  the ourDamagerMoney
	 */
	public String getOurDamagerMoney() {
		return ourDamagerMoney;
	}


	/**
	 * ourDamagerMoney <p>setter method</p>
	 * @author 273279
	 * @param ourDamagerMoney the ourDamagerMoney to set
	 */
	public void setOurDamagerMoney(String ourDamagerMoney) {
		this.ourDamagerMoney = ourDamagerMoney;
	}


	/**
	 * twoPartyDamagerMoney <p>getter method</p>
	 * @author 273279
	 * @return  the twoPartyDamagerMoney
	 */
	public String getTwoPartyDamagerMoney() {
		return twoPartyDamagerMoney;
	}


	/**
	 * twoPartyDamagerMoney <p>setter method</p>
	 * @author 273279
	 * @param twoPartyDamagerMoney the twoPartyDamagerMoney to set
	 */
	public void setTwoPartyDamagerMoney(String twoPartyDamagerMoney) {
		this.twoPartyDamagerMoney = twoPartyDamagerMoney;
	}


	/**
	 * accidentPayMoney <p>getter method</p>
	 * @author 273279
	 * @return  the accidentPayMoney
	 */
	public String getAccidentPayMoney() {
		return accidentPayMoney;
	}


	/**
	 * accidentPayMoney <p>setter method</p>
	 * @author 273279
	 * @param accidentPayMoney the accidentPayMoney to set
	 */
	public void setAccidentPayMoney(String accidentPayMoney) {
		this.accidentPayMoney = accidentPayMoney;
	}


	/**
	 * accidentFee <p>getter method</p>
	 * @author 273279
	 * @return  the accidentFee
	 */
	public String getAccidentFee() {
		return accidentFee;
	}


	/**
	 * accidentFee <p>setter method</p>
	 * @author 273279
	 * @param accidentFee the accidentFee to set
	 */
	public void setAccidentFee(String accidentFee) {
		this.accidentFee = accidentFee;
	}


	/**
	 * mainDriverCode <p>getter method</p>
	 * @author 273279
	 * @return  the mainDriverCode
	 */
	public String getMainDriverCode() {
		return mainDriverCode;
	}


	/**
	 * mainDriverCode <p>setter method</p>
	 * @author 273279
	 * @param mainDriverCode the mainDriverCode to set
	 */
	public void setMainDriverCode(String mainDriverCode) {
		this.mainDriverCode = mainDriverCode;
	}


	/**
	 * mainDriverName <p>getter method</p>
	 * @author 273279
	 * @return  the mainDriverName
	 */
	public String getMainDriverName() {
		return mainDriverName;
	}


	/**
	 * mainDriverName <p>setter method</p>
	 * @author 273279
	 * @param mainDriverName the mainDriverName to set
	 */
	public void setMainDriverName(String mainDriverName) {
		this.mainDriverName = mainDriverName;
	}


	/**
	 * mainDriverDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the mainDriverDeptCode
	 */
	public String getMainDriverDeptCode() {
		return mainDriverDeptCode;
	}


	/**
	 * mainDriverDeptCode <p>setter method</p>
	 * @author 273279
	 * @param mainDriverDeptCode the mainDriverDeptCode to set
	 */
	public void setMainDriverDeptCode(String mainDriverDeptCode) {
		this.mainDriverDeptCode = mainDriverDeptCode;
	}


	/**
	 * mainDriverDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the mainDriverDeptName
	 */
	public String getMainDriverDeptName() {
		return mainDriverDeptName;
	}


	/**
	 * mainDriverDeptName <p>setter method</p>
	 * @author 273279
	 * @param mainDriverDeptName the mainDriverDeptName to set
	 */
	public void setMainDriverDeptName(String mainDriverDeptName) {
		this.mainDriverDeptName = mainDriverDeptName;
	}


	/**
	 * driverMajor <p>getter method</p>
	 * @author 273279
	 * @return  the driverMajor
	 */
	public String getDriverMajor() {
		return driverMajor;
	}


	/**
	 * driverMajor <p>setter method</p>
	 * @author 273279
	 * @param driverMajor the driverMajor to set
	 */
	public void setDriverMajor(String driverMajor) {
		this.driverMajor = driverMajor;
	}


	/**
	 * deputyDriverCode1 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverCode1
	 */
	public String getDeputyDriverCode1() {
		return deputyDriverCode1;
	}


	/**
	 * deputyDriverCode1 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverCode1 the deputyDriverCode1 to set
	 */
	public void setDeputyDriverCode1(String deputyDriverCode1) {
		this.deputyDriverCode1 = deputyDriverCode1;
	}


	/**
	 * deputyDriverName1 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverName1
	 */
	public String getDeputyDriverName1() {
		return deputyDriverName1;
	}


	/**
	 * deputyDriverName1 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverName1 the deputyDriverName1 to set
	 */
	public void setDeputyDriverName1(String deputyDriverName1) {
		this.deputyDriverName1 = deputyDriverName1;
	}


	/**
	 * deputyDriverDeptCode1 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverDeptCode1
	 */
	public String getDeputyDriverDeptCode1() {
		return deputyDriverDeptCode1;
	}


	/**
	 * deputyDriverDeptCode1 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverDeptCode1 the deputyDriverDeptCode1 to set
	 */
	public void setDeputyDriverDeptCode1(String deputyDriverDeptCode1) {
		this.deputyDriverDeptCode1 = deputyDriverDeptCode1;
	}


	/**
	 * deputyDriverDeptName1 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverDeptName1
	 */
	public String getDeputyDriverDeptName1() {
		return deputyDriverDeptName1;
	}


	/**
	 * deputyDriverDeptName1 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverDeptName1 the deputyDriverDeptName1 to set
	 */
	public void setDeputyDriverDeptName1(String deputyDriverDeptName1) {
		this.deputyDriverDeptName1 = deputyDriverDeptName1;
	}


	/**
	 * deputyDriverCode2 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverCode2
	 */
	public String getDeputyDriverCode2() {
		return deputyDriverCode2;
	}


	/**
	 * deputyDriverCode2 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverCode2 the deputyDriverCode2 to set
	 */
	public void setDeputyDriverCode2(String deputyDriverCode2) {
		this.deputyDriverCode2 = deputyDriverCode2;
	}


	/**
	 * deputyDriverName2 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverName2
	 */
	public String getDeputyDriverName2() {
		return deputyDriverName2;
	}


	/**
	 * deputyDriverName2 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverName2 the deputyDriverName2 to set
	 */
	public void setDeputyDriverName2(String deputyDriverName2) {
		this.deputyDriverName2 = deputyDriverName2;
	}


	/**
	 * deputyDriverDeptCode2 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverDeptCode2
	 */
	public String getDeputyDriverDeptCode2() {
		return deputyDriverDeptCode2;
	}


	/**
	 * deputyDriverDeptCode2 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverDeptCode2 the deputyDriverDeptCode2 to set
	 */
	public void setDeputyDriverDeptCode2(String deputyDriverDeptCode2) {
		this.deputyDriverDeptCode2 = deputyDriverDeptCode2;
	}


	/**
	 * deputyDriverDeptName2 <p>getter method</p>
	 * @author 273279
	 * @return  the deputyDriverDeptName2
	 */
	public String getDeputyDriverDeptName2() {
		return deputyDriverDeptName2;
	}


	/**
	 * deputyDriverDeptName2 <p>setter method</p>
	 * @author 273279
	 * @param deputyDriverDeptName2 the deputyDriverDeptName2 to set
	 */
	public void setDeputyDriverDeptName2(String deputyDriverDeptName2) {
		this.deputyDriverDeptName2 = deputyDriverDeptName2;
	}


	/**
	 * accidentDealDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the accidentDealDeptCode
	 */
	public String getAccidentDealDeptCode() {
		return accidentDealDeptCode;
	}


	/**
	 * accidentDealDeptCode <p>setter method</p>
	 * @author 273279
	 * @param accidentDealDeptCode the accidentDealDeptCode to set
	 */
	public void setAccidentDealDeptCode(String accidentDealDeptCode) {
		this.accidentDealDeptCode = accidentDealDeptCode;
	}


	/**
	 * accidentDealDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the accidentDealDeptName
	 */
	public String getAccidentDealDeptName() {
		return accidentDealDeptName;
	}


	/**
	 * accidentDealDeptName <p>setter method</p>
	 * @author 273279
	 * @param accidentDealDeptName the accidentDealDeptName to set
	 */
	public void setAccidentDealDeptName(String accidentDealDeptName) {
		this.accidentDealDeptName = accidentDealDeptName;
	}


	/**
	 * clientName <p>getter method</p>
	 * @author 273279
	 * @return  the clientName
	 */
	public String getClientName() {
		return clientName;
	}


	/**
	 * clientName <p>setter method</p>
	 * @author 273279
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	/**
	 * clientCode <p>getter method</p>
	 * @author 273279
	 * @return  the clientCode
	 */
	public String getClientCode() {
		return clientCode;
	}


	/**
	 * clientCode <p>setter method</p>
	 * @author 273279
	 * @param clientCode the clientCode to set
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}


	/**
	 * badAmountMoney <p>getter method</p>
	 * @author 273279
	 * @return  the badAmountMoney
	 */
	public String getBadAmountMoney() {
		return badAmountMoney;
	}


	/**
	 * badAmountMoney <p>setter method</p>
	 * @author 273279
	 * @param badAmountMoney the badAmountMoney to set
	 */
	public void setBadAmountMoney(String badAmountMoney) {
		this.badAmountMoney = badAmountMoney;
	}


	/**
	 * fixedAssetNetValue <p>getter method</p>
	 * @author 273279
	 * @return  the fixedAssetNetValue
	 */
	public String getFixedAssetNetValue() {
		return fixedAssetNetValue;
	}


	/**
	 * fixedAssetNetValue <p>setter method</p>
	 * @author 273279
	 * @param fixedAssetNetValue the fixedAssetNetValue to set
	 */
	public void setFixedAssetNetValue(String fixedAssetNetValue) {
		this.fixedAssetNetValue = fixedAssetNetValue;
	}


	/**
	 * checkBillCode <p>getter method</p>
	 * @author 273279
	 * @return  the checkBillCode
	 */
	public String getCheckBillCode() {
		return checkBillCode;
	}


	/**
	 * checkBillCode <p>setter method</p>
	 * @author 273279
	 * @param checkBillCode the checkBillCode to set
	 */
	public void setCheckBillCode(String checkBillCode) {
		this.checkBillCode = checkBillCode;
	}


	/**
	 * shortAmount <p>getter method</p>
	 * @author 273279
	 * @return  the shortAmount
	 */
	public String getShortAmount() {
		return shortAmount;
	}


	/**
	 * shortAmount <p>setter method</p>
	 * @author 273279
	 * @param shortAmount the shortAmount to set
	 */
	public void setShortAmount(String shortAmount) {
		this.shortAmount = shortAmount;
	}


	/**
	 * isLoseBill <p>getter method</p>
	 * @author 273279
	 * @return  the isLoseBill
	 */
	public String getIsLoseBill() {
		return isLoseBill;
	}


	/**
	 * isLoseBill <p>setter method</p>
	 * @author 273279
	 * @param isLoseBill the isLoseBill to set
	 */
	public void setIsLoseBill(String isLoseBill) {
		this.isLoseBill = isLoseBill;
	}


	/**
	 * outsideCarDriverName <p>getter method</p>
	 * @author 273279
	 * @return  the outsideCarDriverName
	 */
	public String getOutsideCarDriverName() {
		return outsideCarDriverName;
	}


	/**
	 * outsideCarDriverName <p>setter method</p>
	 * @author 273279
	 * @param outsideCarDriverName the outsideCarDriverName to set
	 */
	public void setOutsideCarDriverName(String outsideCarDriverName) {
		this.outsideCarDriverName = outsideCarDriverName;
	}

    
}