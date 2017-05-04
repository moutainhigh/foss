package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryRegionsVo;

public class CommonExpressDeliveryRegionsAction extends AbstractAction implements IQueryAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExpressDeliveryRegionsVo expressDeliveryRegionsVo;
	private IExpressDeliveryRegionsService expressDeliveryRegionsService;

	/**
	 * 人员公共组件查询方法.
	 *
	 * @return the string
	 * @author 130246
	 * @date 2014-03-07 上午11:36:29
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		long totalCount=expressDeliveryRegionsService.queryCount(expressDeliveryRegionsVo.getExpressDeliveryRegionsEntity());
		if(totalCount > 0){
			List<ExpressDeliveryRegionsEntity> expressDeliveryRegionsList=expressDeliveryRegionsService.queryRegionsListAttachDegreeName(expressDeliveryRegionsVo.getExpressDeliveryRegionsEntity(), start, limit);
			expressDeliveryRegionsVo.setExpressDeliveryRegionsList(expressDeliveryRegionsList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * 根据上级行政区域编码查询下级行政区域，注意：此处查询的是行政区域表，非快递派送区域表
	 * @return
	 */
	@JSON
	public String queryRegionsByParentCode(){
		if(expressDeliveryRegionsVo==null){
			expressDeliveryRegionsVo = new ExpressDeliveryRegionsVo();
		}
		if(expressDeliveryRegionsVo.getParentDistrictCode()!=null){
			ExpressDeliveryRegionsEntity entity = new ExpressDeliveryRegionsEntity();
			entity.setParentDistrictCode(expressDeliveryRegionsVo.getParentDistrictCode());
			expressDeliveryRegionsVo.setExpressDeliveryRegionsEntity(entity);
		}
		expressDeliveryRegionsVo.setExpressDeliveryRegionsList(expressDeliveryRegionsService.queryRegions(
				expressDeliveryRegionsVo.getExpressDeliveryRegionsEntity()));
		return returnSuccess();
	}

	public ExpressDeliveryRegionsVo getExpressDeliveryRegionsVo() {
		return expressDeliveryRegionsVo;
	}

	public void setExpressDeliveryRegionsVo(
			ExpressDeliveryRegionsVo expressDeliveryRegionsVo) {
		this.expressDeliveryRegionsVo = expressDeliveryRegionsVo;
	}

	public void setExpressDeliveryRegionsService(
			IExpressDeliveryRegionsService expressDeliveryRegionsService) {
		this.expressDeliveryRegionsService = expressDeliveryRegionsService;
	}
	
}
