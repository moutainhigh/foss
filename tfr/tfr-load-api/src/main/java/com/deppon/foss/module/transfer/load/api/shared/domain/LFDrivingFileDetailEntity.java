package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.crm.module.client.sync.domain.dto.BaseEntity;

/**
 * 
* @ClassName: LFDrivingFileDetailEntity
* @Description: 长途车辆行驶档案明细
* @author ZX_189284
* @date 2016-5-20 上午10:25:08
*
 */
public class LFDrivingFileDetailEntity extends BaseEntity {
	private String id;
	/**
	 * //行车编码';
	 */
	private  String  drivingNo;
	/**
	 * //配载车次号';
	 */
	private  String  vehicleassembleNo;
	/**
	 * //配载部门CODE';
	 */
	private  String  origOrgCode;
	/**
	 * //配载部门NAME';
	 */
	private  String  origOrgName;
	/**
	 * //配载时间';
	 */
	private  Date  departTime;
	/**
	 * //到达部门CODE';
	 */
	private  String  destOrgCode;
	/**
	 * //到达部门NAME';
	 */
	private  String  destOrgName;
	/**
	 * /c
	 */
	private  Date  arriveTime;
	/**
	 * //总重量';
	 */
	private  BigDecimal  weightTotal;
	/**
	 * //总体积
	 */
	private  BigDecimal  volumeTotal;
	/**
    * 类型:空驶年审:DE_Year_Check        
	* 空驶维修保养:DE_Maintenance  
	* 空驶提车柜:DE_Car_Cabinet    
	* 空驶救援:DE_SOS              
	* 空驶补货:DE_ Restock          
	* 混合发车:mix_depart          
	* 分段发车:subsection_depart    
	* 其他:other'
	 */
	private  String type;
	
	
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
	 * //行车编码';
	 * @return drivingNo
	 */
	public String getDrivingNo() {
		return drivingNo;
	}
	/**
	 * //行车编码'
	 * @param drivingNo 要设置的 drivingNo
	 */
	public void setDrivingNo(String drivingNo) {
		this.drivingNo = drivingNo;
	}
	/**
	 * //配载车次号';
	 * @return vehicleassembleNo
	 */
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}
	/**
	 * //配载车次号';
	 * @param vehicleassembleNo 要设置的 vehicleassembleNo
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}
	/**
	 * //配载部门CODE';
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	/**
	 * //配载部门CODE';
	 * @param origOrgCode 要设置的 origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	/**
	 * 配载部门name
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	/**
	 * 配载部门name
	 * @param origOrgName 要设置的 origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	/**
	 * //配载时间';
	 * @return createTime
	 */
	public Date getDepartTime() {
		return departTime;
	}
	/**
	 * 配载时间';
	 * @param departTime 要设置的 departTime
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	
	/**
	 * //到达部门CODE';
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * //到达部门CODE';
	 * @param destOrgCode 要设置的 destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	/**
	 * 到达部门name
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	/**
	 * 到达部门name
	 * @param destOrgName 要设置的 destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * /到达时间';
	 * @return arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	/**
	 * /到达时间';
	 * @param arriveTime 要设置的 arriveTime
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	/**
	 * 总重量';
	 * @return weightTotal
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	/**
	 * 总重量';
	 * @param weightTotal 要设置的 weightTotal
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	/**
	 * 总体积
	 * @return volumeTotal
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	/**
	 * 总体积
	 * @param volumeTotal 要设置的 volumeTotal
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	/**
	 * 类型:空驶年审:DE_Year_Check        
	* 空驶维修保养:DE_Maintenance  
	* 空驶提车柜:DE_Car_Cabinet    
	* 空驶救援:DE_SOS              
	* 空驶补货:DE_ Restock          
	* 混合发车:mix_depart          
	* 分段发车:subsection_depart    
	* 其他:other'
	 * @return type
	 */
	public String getType() {
		return type;
	}
	/**
	 * 类型:空驶年审:DE_Year_Check        
	* 空驶维修保养:DE_Maintenance  
	* 空驶提车柜:DE_Car_Cabinet    
	* 空驶救援:DE_SOS              
	* 空驶补货:DE_ Restock          
	* 混合发车:mix_depart          
	* 分段发车:subsection_depart    
	* 其他:other'
	 * @param type 要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
