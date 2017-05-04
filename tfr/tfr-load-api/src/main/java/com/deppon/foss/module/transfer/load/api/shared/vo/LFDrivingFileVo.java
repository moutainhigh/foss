package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;


public class LFDrivingFileVo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 1L;
	/**
	 * 长途车行驶档案 基础资料
	 */
	private LFDrivingFileEntity lfDrivingFile;
	private LFDrivingFileDetailEntity lfDrivingFileDetailEntity;
	private List<LFDrivingFileEntity> lfDrivingFiles;
	private List<LFDrivingFileDetailEntity> lfDrivingFileDetails;
	/**
	 * 修改配载信息list
	 */
	private List<LFDrivingFileDetailEntity> updatelfDrivingFileDetails;
	/**
	 * 删除配载信息 ids
	 */
	private List<String> ids;
	/**
	 * 根据车牌号 查询出来的车辆信息
	 */
	private VehicleAssociationDto vehicleAssociationDto;
	/**
	 * 配载单号
	 */
	private String vehicleassembleNo;
	/**
	 * 所属车队Code
	 */
	private String orgIdCode;
	/**
	 * 行驶编码
	 */
	private String drivingNo;
	/***
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 *  修改信息时  是否更改过车牌号 或者配载单号
	 */
	private String isChange;
	
	/**
	 * 根据车牌号 查询出来的车辆信息
	 * @return vehicleAssociationDto
	 */
	public VehicleAssociationDto getVehicleAssociationDto() {
		return vehicleAssociationDto;
	}
	/**
	 * 根据车牌号 查询出来的车辆信息
	 * @param vehicleAssociationDto 要设置的 vehicleAssociationDto
	 */
	public void setVehicleAssociationDto(VehicleAssociationDto vehicleAssociationDto) {
		this.vehicleAssociationDto = vehicleAssociationDto;
	}
	/**
	 * 车牌号
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * 车牌号
	 * @param vehicleNo 要设置的 vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * 行驶编码
	 * @return drivingNo
	 */
	public String getDrivingNo() {
		return drivingNo;
	}
	/**
	 * 行驶编码
	 * @param drivingNo 要设置的 drivingNo
	 */
	public void setDrivingNo(String drivingNo) {
		this.drivingNo = drivingNo;
	}
	/**
	 * 所属车队Code
	 * @return orgIdCode
	 */
	public String getOrgIdCode() {
		return orgIdCode;
	}
	/**
	 * 所属车队Code
	 * @param orgIdCode 要设置的 orgIdCode
	 */
	public void setOrgIdCode(String orgIdCode) {
		this.orgIdCode = orgIdCode;
	}
	/** 
	 * 配载单号
	 * @return vehicleassembleNo
	 */
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}
	/**
	 *  配载单号
	 * @param vehicleassembleNo 要设置的 vehicleassembleNo
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}
	
	/**
	 * @return lfDrivingFile
	 */
	public LFDrivingFileEntity getLfDrivingFile() {
		return lfDrivingFile;
	}
	/**
	 * @param lfDrivingFile 要设置的 lfDrivingFile
	 */
	public void setLfDrivingFile(LFDrivingFileEntity lfDrivingFile) {
		this.lfDrivingFile = lfDrivingFile;
	}
	/**
	 * @return lfDrivingFileDetailEntity
	 */
	public LFDrivingFileDetailEntity getLfDrivingFileDetailEntity() {
		return lfDrivingFileDetailEntity;
	}
	/**
	 * @param lfDrivingFileDetailEntity 要设置的 lfDrivingFileDetailEntity
	 */
	public void setLfDrivingFileDetailEntity(
			LFDrivingFileDetailEntity lfDrivingFileDetailEntity) {
		this.lfDrivingFileDetailEntity = lfDrivingFileDetailEntity;
	}
	/**
	 * @return lfDrivingFiles
	 */
	public List<LFDrivingFileEntity> getLfDrivingFiles() {
		return lfDrivingFiles;
	}
	/**
	 * @param lfDrivingFiles 要设置的 lfDrivingFiles
	 */
	public void setLfDrivingFiles(List<LFDrivingFileEntity> lfDrivingFiles) {
		this.lfDrivingFiles = lfDrivingFiles;
	}
	/**
	 * @return lfDrivingFileDetails
	 */
	public List<LFDrivingFileDetailEntity> getLfDrivingFileDetails() {
		return lfDrivingFileDetails;
	}
	/**
	 * @param lfDrivingFileDetails 要设置的 lfDrivingFileDetails
	 */
	public void setLfDrivingFileDetails(
			List<LFDrivingFileDetailEntity> lfDrivingFileDetails) {
		this.lfDrivingFileDetails = lfDrivingFileDetails;
	}
	/**
	 * 修改 配载信息list
	 * @return updatelfDrivingFileDetails
	 */
	public List<LFDrivingFileDetailEntity> getUpdatelfDrivingFileDetails() {
		return updatelfDrivingFileDetails;
	}
	/**
	 * 修改配载信息list
	 * @param updatelfDrivingFileDetails 要设置的 updatelfDrivingFileDetails
	 */
	public void setUpdatelfDrivingFileDetails(
			List<LFDrivingFileDetailEntity> updatelfDrivingFileDetails) {
		this.updatelfDrivingFileDetails = updatelfDrivingFileDetails;
	}
	/**
	 * 删除 配载信息 ids
	 * @return ids
	 */
	public List<String> getIds() {
		return ids;
	}
	/**
	 * 删除 配载信息 ids
	 * @param ids 要设置的 ids
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	/**
	 *  修改信息时  是否更改过车牌号 或者配载单号
	 * @return isChange
	 */
	public String getIsChange() {
		return isChange;
	}
	/**
	 *  修改信息时  是否更改过车牌号 或者配载单号
	 * @param isChange 要设置的 isChange
	 */
	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	
	
}
