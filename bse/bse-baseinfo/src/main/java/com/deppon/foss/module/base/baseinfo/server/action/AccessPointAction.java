package com.deppon.foss.module.base.baseinfo.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AccessPointVo;

/**
 * 接驳点Action
 * @author 198771
 *
 */
public class AccessPointAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4067371265942924894L;
	
	private Logger LOGGER = LoggerFactory.getLogger(AccessPointAction.class);

	private AccessPointVo accessPointVo;
	
	private IAccessPointService accessPointService;

	public void setAccessPointService(IAccessPointService accessPointService) {
		this.accessPointService = accessPointService;
	}

	public AccessPointVo getAccessPointVo() {
		return accessPointVo;
	}

	public void setAccessPointVo(AccessPointVo accessPointVo) {
		this.accessPointVo = accessPointVo;
	}
	
	public String addAccessPoint(){
		accessPointService.addAccessPoint(accessPointVo.getAccessPointEntity());
		return returnSuccess();
	}

	/**
	 * 列表查询接驳点
	 * @return
	 */
	@JSON
	public String queryAccessPoints(){
		try{
			accessPointVo.setAccessPointEntityList(accessPointService.queryAccessPoints(
					accessPointVo.getAccessPointEntityCondition(),start,limit));
			this.setTotalCount(accessPointService.getCount(accessPointVo.getAccessPointEntityCondition()));
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据接驳点名称查询
	 * @return
	 */
	@JSON
	public String queryAccessPointsByName(){
		accessPointVo.setAccessPointEntityList(accessPointService.queryAccessPointsByName(
				accessPointVo.getAccessPointEntityCondition()));
		return returnSuccess();
	}
	/**
	 * 禁用/启用
	 * @return
	 */
	@JSON
	public String updateAccessPointStatu(){
		accessPointService.updateAccessPointStatu(accessPointVo.getAccessPointCodes());
		return returnSuccess();
	}
	
	/**
	 * 查询省市区
	 * @return
	 */
	@JSON
	public String queryAdministrativeRegions() {
		accessPointVo.setRegionList(accessPointService.
				queryRegions(accessPointVo.getAdministrativeRegionsEntity()));
		return returnSuccess();
	}
	
	/**
	 * 作废
	 * @return
	 */
	public String deleteAccessPoint(){
		accessPointService.deleteAccessPoint(accessPointVo.getAccessPointEntity());
		return returnSuccess();
	}
	/**
	 * 根据省市区编码进行查询
	 * @return
	 */
	@JSON
	public String queryRegionsByCodes(){
		accessPointVo.setRegionList(accessPointService.queryRegionsByCodes(accessPointVo.getRegionCodes()));
		return returnSuccess();
	}
	
	/**
	 * 修改前查询省市区
	 * @return
	 */
	@JSON
	public String queryDegreeRegionsByCodes(){
		accessPointVo = accessPointService.queryDegreeRegionsByCodes(accessPointVo);
		return returnSuccess();
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateAccessPoint(){
		accessPointService.updateAccessPoint(accessPointVo.getAccessPointEntity());
		return returnSuccess();
	}
	
	/**
	 * 根据接驳点编码查询接驳点与营业部关系
	 */
	@JSON
	public String queryAcceptPointSalesByAcceptPointCode() {
		accessPointVo.setAcceptPointSalesDeptEntitys(
				accessPointService.queryAcceptPointSalesByAcceptPointCode(accessPointVo.getAccessPointCodes()));
		return returnSuccess();
	}
}
