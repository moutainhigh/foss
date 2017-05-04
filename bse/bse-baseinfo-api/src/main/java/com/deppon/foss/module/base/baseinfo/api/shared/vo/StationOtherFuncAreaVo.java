package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.StationOtherFuncAreaEntity;

public class StationOtherFuncAreaVo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<StationOtherFuncAreaEntity> stationOtherFuncAreaList=new ArrayList<StationOtherFuncAreaEntity>();
	
	private StationOtherFuncAreaEntity stationOtherFuncAreaEntity;
	
	private String codeStr;

	public List<StationOtherFuncAreaEntity> getStationOtherFuncAreaList() {
		return stationOtherFuncAreaList;
	}

	public void setStationOtherFuncAreaList(
			List<StationOtherFuncAreaEntity> stationOtherFuncAreaList) {
		this.stationOtherFuncAreaList = stationOtherFuncAreaList;
	}

	public StationOtherFuncAreaEntity getStationOtherFuncAreaEntity() {
		return stationOtherFuncAreaEntity;
	}

	public void setStationOtherFuncAreaEntity(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		this.stationOtherFuncAreaEntity = stationOtherFuncAreaEntity;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

}
