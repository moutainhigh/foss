package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.crm.module.client.sync.domain.dto.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
/**
 * 
* @ClassName: LFDrivingFileEntity
* @Description: 长途车辆行驶档案LongFDrivingFile
* @author ZX_189284
* @date 2016-5-20 上午8:32:32
*
 */
public class LFDrivingFileEntity  extends BaseEntity{

	 /**
	  * 
	  */
	  private static final long serialVersionUID = 1L;
	  private String id;
	 /**
	  * //期间';  
	  */
	  private String  drivingDate ;  
	 /**
	  * //所属车队编码code 
	  */
	  private String  orgIdCode  ;  
	  /**
		* //所属车队名称 
		*/
	  private String  orgIdName ; 
	  /**
		* //行车编码  
		*/
	  private String  drivingNo;
	  /**
		* //车牌号
		*/
	  private String  vehicleNo; 
	  /**
		* //车型
		*/
	  private String  vehicleType ; 
	  /**
	   * 车型名称
	   */
	  private String  vehicleTypeName;
	  /**
		* //线路Code';
		*/
	  private String  lineCode ;
	  /**
	   * //线路NAME';
	   */
	  private String  lineName ;
	  /**
		* //出发公里数'; 
		*/
	  private BigDecimal  departDistance;
	  /**
	   * //到达公里数'; 
	   */
	  private BigDecimal  arriveDistance;
	  /**
	   * //行驶公里数'; 
	   */
	  private BigDecimal  drivingDistance;
	  /**
	   * //总油升数';
	   */
	  private BigDecimal  consumeFuelTotal;
	  /**
	   * //总油费';  
	   */
	  private BigDecimal  consumeFuelFeeTotal;
	  /**
	   * //平均油价';
	   */
	  private BigDecimal  consumeFuelFee;
	  /**
	   * //路桥费';  
	   */
	  private BigDecimal  toolFeeTotal;
	  /**
	   * //百公里油耗'; 
	   */
	  private BigDecimal  consumeFuel;
	  /**
	   * //公里路桥费'; 
	   */
	  private BigDecimal  toolFee;
	  /**
	   * //司机1code  
	   */
	  private String  driverCodeOne ;
	  /**
	   * //司机1name 
	   */
	  private String  driverNameOne ;
	  /**
	   * //司机2code
	   */
	  private String  driverCodeTwo ; 
	  /**
	   * //司机name 
	   */
	  private String  driverNameTwo ; 
	  /**
	   * //最后操作人name  
	   */
	  private String  modifyUserName;
	  /**
	   * //最后操作人code';
	   */
	  private String  modifyUserCode;
	  /**
	   * //最后操作时间
	   */
	  private Date  modifyTime; 
	  /**
	   * //"线路途径外场Code
	   */
	  private String  lineTransferCode;  
	  /**
	   * //"线路途径外场NAME 
	   */
	  private String  lineTransferName;
	  /**
	   * 线路Code list
	   */
	  private List<String> lineTransferCodeList;
	  /**
	   * 线路code list 对应实体
	   */
	  private List<OrgAdministrativeInfoEntity> lineTransferEntitys;
	  /**
	   * //制单人姓名'; 
	   */
	  private String  createUserName;
	  /**
	   * //制单人编码'; 
	   */
	  private String  createUserCode;
	  /**
	   * //是否有效（Y是N否）'; 
	   */
	  private String  active; 
	  /**
	   * //是否空驶线路或中途换车头（Y是N否）'; 
	   */
	  private String  isDeivingEmpty;
	  /**
	   * //创建时间';
	   */
	  private Date  createTime;
	  /**
	   * 开始时间
	   */
	  private Date beginDate;
	  /**
	   * 结束时间
	   */
	  private Date endDate;
	  /**
	   * 异常信息备注
	   */
	  private String note;
	  /**
	   * 长途车行驶档案 明细list（配载单List）
	   */
	  private List<LFDrivingFileDetailEntity> lfDrivingFileDetailList;
	  
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * //期间'; 
	 * @return drivingDate
	 */
	public String getDrivingDate() {
		return drivingDate;
	}
	/**
	 * //期间'; 
	 * @param drivingDate 要设置的 drivingDate
	 */
	public void setDrivingDate(String drivingDate) {
		this.drivingDate = drivingDate;
	}
	/**
	 * //所属车队编码code 
	 * @return orgIdCode//创建时间';
	 */
	public String getOrgIdCode() {
		return orgIdCode;
	}
	/**
	 * //所属车队编码code 
	 * @param orgIdCode 要设置的 orgIdCode
	 */
	public void setOrgIdCode(String orgIdCode) {
		this.orgIdCode = orgIdCode;
	}
	
	/**
	 * 所属车队编码NAME
	 * @return orgIdName
	 */
	public String getOrgIdName() {
		return orgIdName;
	}
	/**
	 * 所属车队编码NAME
	 * @param orgIdName 要设置的 orgIdName
	 */
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	/**
	 * //行车编码 
	 * @return drivingNo
	 */
	public String getDrivingNo() {
		return drivingNo;
	}
	/**
	 * //行车编码 
	 * @param drivingNo 要设置的 drivingNo
	 */
	public void setDrivingNo(String drivingNo) {
		this.drivingNo = drivingNo;
	}
	/**
	 * //车牌号
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * //车牌号
	 * @param vehicleNo 要设置的 vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * //车型
	 * @return vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}
	/**
	 * //车型
	 * @param vehicleType 要设置的 vehicleType
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	/**
	 * 车型名称
	 * @return vehicleTypeName
	 */
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	/**
	 * 车型名称
	 * @param vehicleTypeName 要设置的 vehicleTypeName
	 */
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	/**
	 * //线路Code';
	 * @return lineCode
	 */
	public String getLineCode() {
		return lineCode;
	}
	/**
	 * //线路Code';
	 * @param lineCode 要设置的 lineCode
	 */
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	/**
	 * //线路name
	 * @return lineName
	 */
	public String getLineName() {
		return lineName;
	}
	/**
	 * //线路name
	 * @param lineName 要设置的 lineName
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	/**
	 * //出发公里数'; 
	 * @return departDistance
	 */
	public BigDecimal getDepartDistance() {
		return departDistance;
	}
	/**
	 * //出发公里数'
	 * @param departDistance 要设置的 departDistance
	 */
	public void setDepartDistance(BigDecimal departDistance) {
		this.departDistance = departDistance;
	}
	/**
	 * //到达公里数'; 
	 * @return arriveDistance
	 */
	public BigDecimal getArriveDistance() {
		return arriveDistance;
	}
	/**
	 * //到达公里数'; 
	 * @param arriveDistance 要设置的 arriveDistance
	 */
	public void setArriveDistance(BigDecimal arriveDistance) {
		this.arriveDistance = arriveDistance;
	}
	/**
	 * //行驶公里数'; 
	 * @return drivingDistance
	 */
	public BigDecimal getDrivingDistance() {
		return drivingDistance;
	}
	/**
	 * //行驶公里数'; 
	 * @param drivingDistance 要设置的 drivingDistance
	 */
	public void setDrivingDistance(BigDecimal drivingDistance) {
		this.drivingDistance = drivingDistance;
	}
	/**
	 * //总油升数';
	 * @return consumeFuelTotal
	 */
	public BigDecimal getConsumeFuelTotal() {
		return consumeFuelTotal;
	}
	/**
	 * //总油升数';
	 * @param consumeFuelTotal 要设置的 consumeFuelTotal
	 */
	public void setConsumeFuelTotal(BigDecimal consumeFuelTotal) {
		this.consumeFuelTotal = consumeFuelTotal;
	}
	/**
	 * ////总油费';
	 * @return consumeFuelFeeTotal
	 */
	public BigDecimal getConsumeFuelFeeTotal() {
		return consumeFuelFeeTotal;
	}
	/**
	 * //总油费'; 
	 * @param consumeFuelFeeTotal 要设置的 consumeFuelFeeTotal
	 */
	public void setConsumeFuelFeeTotal(BigDecimal consumeFuelFeeTotal) {
		this.consumeFuelFeeTotal = consumeFuelFeeTotal;
	}
	/**
	 * 平均油价';
	 * @return consumeFuelFee
	 */
	public BigDecimal getConsumeFuelFee() {
		return consumeFuelFee;
	}
	/**
	 * 平均油价';
	 * @param consumeFuelFee 要设置的 consumeFuelFee
	 */
	public void setConsumeFuelFee(BigDecimal consumeFuelFee) {
		this.consumeFuelFee = consumeFuelFee;
	}
	/**
	 * //路桥费'; 
	 * @return toolFeeTotal
	 */
	public BigDecimal getToolFeeTotal() {
		return toolFeeTotal;
	}
	/**
	 * //路桥费'; 
	 * @param toolFeeTotal 要设置的 toolFeeTotal
	 */
	public void setToolFeeTotal(BigDecimal toolFeeTotal) {
		this.toolFeeTotal = toolFeeTotal;
	}
	/**
	 * //百公里油耗'; 
	 * @return consumeFuel
	 */
	public BigDecimal getConsumeFuel() {
		return consumeFuel;
	}
	/**
	 * //百公里油耗'; 
	 * @param consumeFuel 要设置的 consumeFuel
	 */
	public void setConsumeFuel(BigDecimal consumeFuel) {
		this.consumeFuel = consumeFuel;
	}
	/**
	 * //公里路桥费'; 
	 * @return toolFee
	 */
	public BigDecimal getToolFee() {
		return toolFee;
	}
	/**
	 * //公里路桥费'; 
	 * @param toolFee 要设置的 toolFee
	 */
	public void setToolFee(BigDecimal toolFee) {
		this.toolFee = toolFee;
	}
	/**
	 * //司机1code 
	 * @return driverCodeOne
	 */
	public String getDriverCodeOne() {
		return driverCodeOne;
	}
	/**
	 * //司机1code 
	 * @param driverCodeOne 要设置的 driverCodeOne
	 */
	public void setDriverCodeOne(String driverCodeOne) {
		this.driverCodeOne = driverCodeOne;
	}
	/**
	 * //司机1name
	 * @return driverNameOne
	 */
	public String getDriverNameOne() {
		return driverNameOne;
	}
	/**
	 * //司机1name
	 * @param driverNameOne 要设置的 driverNameOne
	 */
	public void setDriverNameOne(String driverNameOne) {
		this.driverNameOne = driverNameOne;
	}
	/**
	 * //司机2code
	 * @return driverCodeTwo
	 */
	public String getDriverCodeTwo() {
		return driverCodeTwo;
	}
	/**
	 * //司机2code
	 * @param driverCodeTwo 要设置的 driverCodeTwo
	 */
	public void setDriverCodeTwo(String driverCodeTwo) {
		this.driverCodeTwo = driverCodeTwo;
	}
	/**
	 * //司机2name
	 * @return driverNameTwo
	 */
	public String getDriverNameTwo() {
		return driverNameTwo;
	}
	/**
	 * //司机2name
	 * @param driverNameTwo 要设置的 driverNameTwo
	 */
	public void setDriverNameTwo(String driverNameTwo) {
		this.driverNameTwo = driverNameTwo;
	}
	/**
	 * //最后操作人name 
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * //最后操作人name 
	 * @param modifyUserName 要设置的 modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/**
	 * //最后操作人code
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * //最后操作人code
	 * @param modifyUserCode 要设置的 modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * //最后操作时间
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * //最后操作时间
	 * @param modifyTime 要设置的 modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * //"线路途径外场Code
	 * @return lineTransferCode
	 */
	public String getLineTransferCode() {
		return lineTransferCode;
	}
	/**
	 * //"线路途径外场Code
	 * @param lineTransferCode 要设置的 lineTransferCode
	 */
	public void setLineTransferCode(String lineTransferCode) {
		this.lineTransferCode = lineTransferCode;
	}
	/**
	 * //"线路途径外场name
	 * @return lineTransferName
	 */
	public String getLineTransferName() {
		return lineTransferName;
	}
	/**
	 * //"线路途径外场name
	 * @param lineTransferName 要设置的 lineTransferName
	 */
	public void setLineTransferName(String lineTransferName) {
		this.lineTransferName = lineTransferName;
	}
	/**
	 * @return lineTransferCodeList
	 */
	public List<String> getLineTransferCodeList() {
		return lineTransferCodeList;
	}
	/**
	 * @param lineTransferCodeList 要设置的 lineTransferCodeList
	 */
	public void setLineTransferCodeList(List<String> lineTransferCodeList) {
		this.lineTransferCodeList = lineTransferCodeList;
	}
	/**
	 * @return lineTransferEntitys
	 */
	public List<OrgAdministrativeInfoEntity> getLineTransferEntitys() {
		return lineTransferEntitys;
	}
	/**
	 * @param lineTransferEntitys 要设置的 lineTransferEntitys
	 */
	public void setLineTransferEntitys(
			List<OrgAdministrativeInfoEntity> lineTransferEntitys) {
		this.lineTransferEntitys = lineTransferEntitys;
	}
	/**
	 * //制单人姓名'; 
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * //制单人姓名'; 
	 * @param createUserName 要设置的 createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * //制单人姓名code
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * //制单人姓名code
	 * @param createUserCode 要设置的 createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active 要设置的 active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * //是否空驶线路或中途换车头（Y是N否）'; 
	 * @return isDeivingEmpty
	 */
	public String getIsDeivingEmpty() {
		return isDeivingEmpty;
	}
	/**
	 * //是否空驶线路或中途换车头（Y是N否）'; 
	 * @param isDeivingEmpty 要设置的 isDeivingEmpty
	 */
	public void setIsDeivingEmpty(String isDeivingEmpty) {
		this.isDeivingEmpty = isDeivingEmpty;
	}
	/**
	 * //创建时间'
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * //创建时间'
	 * @param createTime 要设置的 createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 异常信息备注
	 * @return
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 异常信息备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 长途车行驶档案 明细list（配载单List）
	 * @return lfDrivingFileDetailList
	 */
	public List<LFDrivingFileDetailEntity> getLfDrivingFileDetailList() {
		return lfDrivingFileDetailList;
	}
	/**
	 * 长途车行驶档案 明细list（配载单List）
	 * @param lfDrivingFileDetailList 要设置的 lfDrivingFileDetailList
	 */
	public void setLfDrivingFileDetailList(
			List<LFDrivingFileDetailEntity> lfDrivingFileDetailList) {
		this.lfDrivingFileDetailList = lfDrivingFileDetailList;
	}
	
}
