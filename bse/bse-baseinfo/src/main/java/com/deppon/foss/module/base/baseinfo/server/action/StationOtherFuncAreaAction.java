package com.deppon.foss.module.base.baseinfo.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStationOtherFuncAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.StationOtherFuncAreaException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.StationOtherFuncAreaVo;

public class StationOtherFuncAreaAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IStationOtherFuncAreaService stationOtherFuncAreaService;
	
	public void setStationOtherFuncAreaService(
			IStationOtherFuncAreaService stationOtherFuncAreaService) {
		this.stationOtherFuncAreaService = stationOtherFuncAreaService;
	}
	
	
	private StationOtherFuncAreaVo objectVo=new StationOtherFuncAreaVo();
	
	
	/**
	 * 
	 * @return
	 */
	@JSON
	public String queryAll() {

		try {
			objectVo.setStationOtherFuncAreaList(stationOtherFuncAreaService.
					queryAllByParam(objectVo.getStationOtherFuncAreaEntity(),this.start,this.limit));
			totalCount = stationOtherFuncAreaService.countAll(objectVo.getStationOtherFuncAreaEntity());
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage()); 
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@JSON
	public String updateStationOtherFuncArea() {
		try {
			stationOtherFuncAreaService.updateStationOtherFuncArea(objectVo.getStationOtherFuncAreaEntity());
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage()); 
		}
	}
	/**
	 * 
	 * @return
	 */
	@JSON
	public String addStationOtherFuncArea() {

		try {
			stationOtherFuncAreaService.addStationOtherFuncArea(objectVo.getStationOtherFuncAreaEntity());
			return returnSuccess();
		} catch (StationOtherFuncAreaException e) {
			return returnError(e.getMessage()); 
		}
	}
	/**
	 * 
	 * @return
	 */
	@JSON
	public String deleteStationOtherFuncArea() {

		try {
			stationOtherFuncAreaService.deleteStationOtherFuncArea(objectVo.getCodeStr());
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage()); 
		}
	}
	
	public StationOtherFuncAreaVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(StationOtherFuncAreaVo objectVo) {
		this.objectVo = objectVo;
	}

	
	
	
}
