package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliverySmallZoneVo;

public class CommonExpressDeliveryAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3055419549615516183L;
	
	private IExpressDeliveryBigZoneService expressDeliveryBigZoneService;
	
	private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
	
	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}
	
	public void setExpressDeliveryBigZoneService(
			IExpressDeliveryBigZoneService expressDeliveryBigZoneService) {
		this.expressDeliveryBigZoneService = expressDeliveryBigZoneService;
	}
	 private ExpressDeliverySmallZoneVo objectVo = new ExpressDeliverySmallZoneVo();
	 
	public ExpressDeliverySmallZoneVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(ExpressDeliverySmallZoneVo objectVo) {
		this.objectVo = objectVo;
	}

	@JSON
	public String queryExpressDeliver(){
		ExpressDeliveryBigZoneEntity  entityCondition=new ExpressDeliveryBigZoneEntity();
		String regionName=objectVo.getQueryParam();
		if(!StringUtil.isEmpty(regionName)){
			 entityCondition.setRegionName(regionName);
		}
		objectVo.setBigZoneEntityList(expressDeliveryBigZoneService.queryExpressDeliveryBigZones(entityCondition, limit, start));
		totalCount = expressDeliveryBigZoneService.queryRecordCount(entityCondition);
		return returnSuccess();
	}
	
	
	@JSON
	public String querySmallRegion(){
		ExpressDeliverySmallZoneEntity  entityCondition=new ExpressDeliverySmallZoneEntity();
		String regionName=objectVo.getQueryParam();
		if(!StringUtil.isEmpty(regionName)){
			 entityCondition.setRegionName(regionName);
		}
		objectVo.setSmallZoneEntityList(expressDeliverySmallZoneService.queryExpressDeliverySmallZones(entityCondition, limit, start));
		totalCount = expressDeliverySmallZoneService.queryRecordCount(entityCondition);
		 return returnSuccess();
	}
	
	
	
	

}
