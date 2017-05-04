package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.VehicleDrivingVo;
public class CommonVehicleDrivingAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 长途车队信息action接口
	 * @author 323136
	 */
	
	
   
    private ICommonVehicleDrivingService commonVehicleDrivingService;

	private VehicleDrivingVo vehicleDrivingVo = new VehicleDrivingVo();


	/**
	 * 供选择器用
	 * @return
	 */
	@JSON
	public String queryCommonVehicleDriving(){
		
		vehicleDrivingVo.setVehicleDrivingEntityList(commonVehicleDrivingService
				.queryCommonVehicleDrivingByCondition(vehicleDrivingVo.getVehicleDrivingEntity(), start,
						limit));
		setTotalCount(commonVehicleDrivingService
				.queryCommonVehicleDrivingRecordByCondition(vehicleDrivingVo.getVehicleDrivingEntity()));
		return returnSuccess();
		
	}

	public void setCommonVehicleDrivingService(
			ICommonVehicleDrivingService commonVehicleDrivingService) {
		this.commonVehicleDrivingService = commonVehicleDrivingService;
	}





	public VehicleDrivingVo getVehicleDrivingVo() {
		return vehicleDrivingVo;
	}

	public void setVehicleDrivingVo(VehicleDrivingVo vehicleDrivingVo) {
		this.vehicleDrivingVo = vehicleDrivingVo;
	}



}
