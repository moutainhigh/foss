package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
/**
 * 用来存储交互“外请车挂车、外请车拖头、外请车厢式车”的DTO：SUC-608、SUC-103、SUC-44
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2013-3-27 下午5:46:15</p>
 * @author 100847-foss-GaoPeng
 * @date 2013-3-27 下午5:46:15
 * @since
 * @version
 */
public class LeasedTruckDto extends LeasedTruckEntity implements Serializable {

    private LeasedTruckDto(){}
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3308721828288986024L;
    
   
    /**
     * 白名单状态.
     */
    private String whiteStatus;
    
    /**
     * 审核人.
     */
    private String approveUser;
    
    /**
     * 审核人姓名.
     */
    private String approveUserName;
    
	/**
     * 审核时间.
     */
    private Date approveTime;
    
    /**
     * 获取白名单状态
     */
    public String getApproveUser() {
		return approveUser;
	}

    /**
     * 设置白名单状态
     */
    public String getWhiteStatus() {
		return whiteStatus;
	}


	/**
	 * 获取审核人
	 * @return
	 */
	public void setWhiteStatus(String whiteStatus) {
		this.whiteStatus = whiteStatus;
	}


	/**
	 * 设置审核人
	 * @param approveUser
	 */
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}


	/**
	 * 获取审核时间
	 * @return
	 */
	public Date getApproveTime() {
		return approveTime;
	}


	/**
	 * 设置审核时间
	 * @param approveTime
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}   
	
	/**
	 * 获取审核人姓名
	 * @return
	 */
	public String getApproveUserName() {
		return approveUserName;
	}
	
	/**
	 * 设置审核人姓名
	 * @param approveUserName
	 */
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
   
    public static LeasedTruckDto getInstance(LeasedTruckEntity leasedTruck){
	LeasedTruckDto leasedTruckDto = new LeasedTruckDto();
	if(null != leasedTruck){
	    BeanUtils.copyProperties(leasedTruck, leasedTruckDto, new String[]{
		    "deadLoad", "selfWeight", "beginTime", "endTime", "vehicleLength", "vehicleWidth",
		    "vehicleHeight", "selfVolume", "beginTimeOperatingLic", "endTimeOperatingLic",
		    "beginTimeDrivingLic", "endTimeDrivingLic", "beginTimeInsureCard", "endTimeInsureCard"
	    });
	    leasedTruckDto.setDeadLoad(leasedTruck.getDeadLoad());
	    leasedTruckDto.setSelfWeight(leasedTruck.getSelfWeight());
	    leasedTruckDto.setBeginTime(leasedTruck.getBeginTime());
	    leasedTruckDto.setEndTime(leasedTruck.getEndTime());
	    leasedTruckDto.setVehicleLength(leasedTruck.getVehicleLength());
	    leasedTruckDto.setVehicleWidth(leasedTruck.getVehicleWidth());
	    leasedTruckDto.setVehicleHeight(leasedTruck.getVehicleHeight());
	    leasedTruckDto.setSelfVolume(leasedTruck.getSelfVolume());
	    leasedTruckDto.setBeginTimeOperatingLic(leasedTruck.getBeginTimeOperatingLic());
	    leasedTruckDto.setEndTimeOperatingLic(leasedTruck.getEndTimeOperatingLic());
	    leasedTruckDto.setBeginTimeDrivingLic(leasedTruck.getBeginTimeDrivingLic());
	    leasedTruckDto.setEndTimeDrivingLic(leasedTruck.getEndTimeDrivingLic());
	    leasedTruckDto.setBeginTimeInsureCard(leasedTruck.getBeginTimeInsureCard());
	    leasedTruckDto.setEndTimeInsureCard(leasedTruck.getEndTimeInsureCard());
	}
	return leasedTruckDto;
    }
}
