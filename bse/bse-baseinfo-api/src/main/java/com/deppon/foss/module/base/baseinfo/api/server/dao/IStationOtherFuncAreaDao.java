package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.StationOtherFuncAreaEntity;

public interface IStationOtherFuncAreaDao {
	
	
	public List<StationOtherFuncAreaEntity> queryAll(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);
	
	public StationOtherFuncAreaEntity  queryByEntityParam(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);
	
	public Integer updateStationOtherFuncArea(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);
	
	public Integer addStationOtherFuncArea(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);
	
	public Integer deleteStationOtherFuncArea(String[] codes);
	
	public Integer deleteStationOtherFuncAreaById(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);

	public Long countAll(StationOtherFuncAreaEntity stationOtherFuncAreaEntity);
	
	public List<StationOtherFuncAreaEntity> queryAllByParam(StationOtherFuncAreaEntity stationOtherFuncAreaEntity,int offset,int limit);

}
