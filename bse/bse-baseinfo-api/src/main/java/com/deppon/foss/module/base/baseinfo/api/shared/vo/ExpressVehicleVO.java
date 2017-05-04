package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;

/**
 * 快递车辆Vo
 * 
 * @author WangPeng
 * @date   2013-07-29 10:48 PM
 *
 */
public class ExpressVehicleVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9048954492201647090L;
	/**
	 * 快递车对象
	 */
	private ExpressVehicleDto expressVehicle;
	
	/**
	 * 快递车集合
	 */
	private List<ExpressVehicleDto>  expressVehicleList;

	public ExpressVehicleDto getExpressVehicle() {
		return expressVehicle;
	}

	public void setExpressVehicle(ExpressVehicleDto expressVehicle) {
		this.expressVehicle = expressVehicle;
	}

	public List<ExpressVehicleDto> getExpressVehicleList() {
		return expressVehicleList;
	}

	public void setExpressVehicleList(List<ExpressVehicleDto> expressVehicleList) {
		this.expressVehicleList = expressVehicleList;
	}
	
	
}
