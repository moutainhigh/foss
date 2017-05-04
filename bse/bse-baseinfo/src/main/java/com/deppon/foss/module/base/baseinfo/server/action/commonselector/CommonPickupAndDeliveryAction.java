package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PAndDeliveryZoneRegionVo;
import com.deppon.foss.framework.shared.util.string.StringUtil;

public class CommonPickupAndDeliveryAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 909262019499569432L;
	
	/**
	 * 
	 */
	private PAndDeliveryZoneRegionVo objectVo = new PAndDeliveryZoneRegionVo();
	/**
	 * 
	 * @return
	 */
	public PAndDeliveryZoneRegionVo getObjectVo() {
		return objectVo;
	}
    /**
     * 
     * @param objectVo
     */
 	public void setObjectVo(PAndDeliveryZoneRegionVo objectVo) {
		this.objectVo = objectVo;
	}

	/**
	 * 
	 */
	 private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
	 /**
	  * 
	  */
	 private IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService;

	 
	 /**
	  * 
	  * @param pickupAndDeliverySmallZoneService
	  */
	 public void setPickupAndDeliverySmallZoneService(
			IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
		this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
	}
	
	/**
	 * 
	 * @param pickupAndDeliveryBigZoneService
	 */
	public void setPickupAndDeliveryBigZoneService(
			IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService) {
		this.pickupAndDeliveryBigZoneService = pickupAndDeliveryBigZoneService;
	}
	/**
	 * 接送货大区公共选择器
	 * @return
	 */
	@JSON
	public String queryBigZone(){
		BigZoneEntity entityCondition=new BigZoneEntity();
		String regionName=objectVo.getQueryParam();
		if(!StringUtil.isEmpty(regionName)){
			 entityCondition.setRegionName(regionName);
		}
		objectVo.setBigZoneEntityList(pickupAndDeliveryBigZoneService.queryPickupAndDeliveryBigZones(entityCondition, limit, start));
		totalCount = pickupAndDeliveryBigZoneService.queryRecordCount(entityCondition);
		return returnSuccess();
	}
	/**
	 * 接送货小区公共选择器
	 * @return
	 */
	@JSON
	public String querySmallZone(){
		SmallZoneEntity entityCondition=new SmallZoneEntity();
		String regionName=objectVo.getQueryParam();
		if(!StringUtil.isEmpty(regionName)){
			 entityCondition.setRegionName(regionName);
		}
		objectVo.setSmallZoneEntityList(pickupAndDeliverySmallZoneService.queryPickupAndDeliverySmallZones(entityCondition, limit, start));
		totalCount = pickupAndDeliverySmallZoneService.queryRecordCount(entityCondition);
		 return returnSuccess();
	}

}
