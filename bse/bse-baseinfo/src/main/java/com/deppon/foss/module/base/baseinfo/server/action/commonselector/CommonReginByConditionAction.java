package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonReginByConditionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonAdministrativeRegionsVo;

public class CommonReginByConditionAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The city vo. */
	private CommonAdministrativeRegionsVo cityVo = new CommonAdministrativeRegionsVo();
	private ICommonReginByConditionService commonReginByConditionService ;
	
	/**
	 * .
	 * <p>
	 * 查询行政区域根据条件<br/>
	 * 方法名：queryAirlinesAgentListBySelectiveCondition
	 * </p>
	 * 
	 * @author 130346-foss-lifanghong
	 * @时间 2013-06-24
	 * @since JDK1.6
	 */
	@JSON
	public String query() {	
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(cityVo.getDegree());
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		entity.setParentDistrictCode(cityVo.getParentId());
		List<AdministrativeRegionsEntity> administrativeRegionsEntitys = commonReginByConditionService.commonReginByCondition(entity, start, limit);
		cityVo.setReginList(administrativeRegionsEntitys);
		long total = commonReginByConditionService.queryReginCountByCondition(entity);
		setTotalCount(total);
		return returnSuccess();		
}

	public CommonAdministrativeRegionsVo getCityVo() {
		return cityVo;
	}

	public void setCityVo(CommonAdministrativeRegionsVo cityVo) {
		this.cityVo = cityVo;
	}

 
	public void setCommonReginByConditionService(
			ICommonReginByConditionService commonReginByConditionService) {
		this.commonReginByConditionService = commonReginByConditionService;
	}
	

}
