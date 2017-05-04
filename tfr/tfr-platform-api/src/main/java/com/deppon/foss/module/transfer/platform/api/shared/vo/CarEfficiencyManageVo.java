package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;


/**
* @description 车辆装卸车效率Vo
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月25日 下午1:54:55
*/
public class CarEfficiencyManageVo implements java.io.Serializable {
	



	/**
	* @fields carEfficiencyDto
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:14:05
	* @version V1.0
	*/
	private CarEfficiencyEntity carEfficiencyDto;
	
	
	/**
	* @fields carEfficiencyList
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:14:09
	* @version V1.0
	*/
	private List<CarEfficiencyEntity> carEfficiencyList;

	
	/**
	* @description 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:14:48
	*/
	public CarEfficiencyEntity getCarEfficiencyDto() {
		return carEfficiencyDto;
	}


	
	/**
	* @description 
	* @param carEfficiencyDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:14:53
	*/
	public void setCarEfficiencyDto(CarEfficiencyEntity carEfficiencyDto) {
		this.carEfficiencyDto = carEfficiencyDto;
	}


	
	/**
	* @description 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:14:59
	*/
	public List<CarEfficiencyEntity> getCarEfficiencyList() {
		return carEfficiencyList;
	}


	
	/**
	* @description 
	* @param carEfficiencyList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:15:04
	*/
	public void setCarEfficiencyList(List<CarEfficiencyEntity> carEfficiencyList) {
		this.carEfficiencyList = carEfficiencyList;
	}
	
}
