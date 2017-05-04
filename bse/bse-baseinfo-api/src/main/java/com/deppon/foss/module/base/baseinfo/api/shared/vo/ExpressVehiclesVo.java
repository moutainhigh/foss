package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;

/**
 * 快递车辆Vo
 * 
 * 
 * @author xmm
 * @date   2013-08-13 10:48 PM
 *
 */
public class ExpressVehiclesVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4776648425660300920L;
	
    /**
     * "快递车辆"对象
     */
    private ExpressVehiclesEntity expressVehiclesEntity;
    
    /**
     * 车辆对应 区域code 新增修改时用到
     */
    private List<String> areaCodeList;
    /**
     * "快递车辆"对象列表集合
     */
    private List<ExpressVehiclesEntity> expressVehiclesEntityList;
    
    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;
    
    
    /**
     * 批量作废车辆记录id集合
     */
    private List<String> vehListIds;

	public ExpressVehiclesEntity getExpressVehiclesEntity() {
		return expressVehiclesEntity;
	}

	public void setExpressVehiclesEntity(ExpressVehiclesEntity expressVehiclesEntity) {
		this.expressVehiclesEntity = expressVehiclesEntity;
	}

	public List<ExpressVehiclesEntity> getExpressVehiclesEntityList() {
		return expressVehiclesEntityList;
	}

	public void setExpressVehiclesEntityList(
			List<ExpressVehiclesEntity> expressVehiclesEntityList) {
		this.expressVehiclesEntityList = expressVehiclesEntityList;
	}

	public List<String> getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(List<String> batchIds) {
		this.batchIds = batchIds;
	}

	public List<String> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(List<String> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

	/**
	 * @return the vehListIds
	 */
	public List<String> getVehListIds() {
		return vehListIds;
	}

	/**
	 * @param vehListIds the vehListIds to set
	 */
	public void setVehListIds(List<String> vehListIds) {
		this.vehListIds = vehListIds;
	}
}
