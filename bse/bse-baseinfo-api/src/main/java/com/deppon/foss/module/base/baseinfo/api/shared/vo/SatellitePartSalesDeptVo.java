package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;

/**
 * 卫星点部和营业部映射的vo
 * @author 130566
 *
 */
public class SatellitePartSalesDeptVo {
	//卫星点部和营业部映射实体
	private SatellitePartSalesDeptEntity satellitePartSalesDeptEntity;
	//卫星点部和营业部映射列表
	private List<SatellitePartSalesDeptEntity> satellitePartSalesList;
	//idList,批量作废时用的
	private List<String> idList;
	//新增集合
	private List<SatellitePartSalesDeptEntity> addSatellitePartSalesList;
	//作废集合
	private List<SatellitePartSalesDeptEntity> deleteSatellitePartSalesList;
	
	public SatellitePartSalesDeptEntity getSatellitePartSalesDeptEntity() {
		return satellitePartSalesDeptEntity;
	}
	public void setSatellitePartSalesDeptEntity(
			SatellitePartSalesDeptEntity satellitePartSalesDeptEntity) {
		this.satellitePartSalesDeptEntity = satellitePartSalesDeptEntity;
	}
	public List<SatellitePartSalesDeptEntity> getSatellitePartSalesList() {
		return satellitePartSalesList;
	}
	public void setSatellitePartSalesList(
			List<SatellitePartSalesDeptEntity> satellitePartSalesList) {
		this.satellitePartSalesList = satellitePartSalesList;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public List<SatellitePartSalesDeptEntity> getAddSatellitePartSalesList() {
		return addSatellitePartSalesList;
	}
	public void setAddSatellitePartSalesList(
			List<SatellitePartSalesDeptEntity> addSatellitePartSalesList) {
		this.addSatellitePartSalesList = addSatellitePartSalesList;
	}
	public List<SatellitePartSalesDeptEntity> getDeleteSatellitePartSalesList() {
		return deleteSatellitePartSalesList;
	}
	public void setDeleteSatellitePartSalesList(
			List<SatellitePartSalesDeptEntity> deleteSatellitePartSalesList) {
		this.deleteSatellitePartSalesList = deleteSatellitePartSalesList;
	}
	
	
	
}
