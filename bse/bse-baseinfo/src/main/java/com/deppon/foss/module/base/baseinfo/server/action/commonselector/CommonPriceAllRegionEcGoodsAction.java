package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionEcGoodsService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;

/**
 * 公共组件--首续重目的地区域查询
 */
public class CommonPriceAllRegionEcGoodsAction extends AbstractAction implements IQueryAction{
	private static final long serialVersionUID = 1L;
	private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();
	private ICommonAllPriceRegionEcGoodsService commonAllPriceRegionEcGoodsService;
	public PriceRegionEntityVo getPriceRegionEntityVo() {
		return priceRegionEntityVo;
	}
	public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
		this.priceRegionEntityVo = priceRegionEntityVo;
	}
	public void setCommonAllPriceRegionEcGoodsService(
			ICommonAllPriceRegionEcGoodsService commonAllPriceRegionEcGoodsService) {
		this.commonAllPriceRegionEcGoodsService = commonAllPriceRegionEcGoodsService;
	}
	/**
	 * 价格区域查询方法.
	 */
	@Override
	public String query() {
		priceRegionEntityVo.setPriceRegionEntities(commonAllPriceRegionEcGoodsService.searchRegionByCondition(priceRegionEntityVo.getPriceRegionEntity(),start, limit));
		super.setTotalCount(commonAllPriceRegionEcGoodsService.countRegionByCondition(priceRegionEntityVo.getPriceRegionEntity()));
		return returnSuccess();
	}
}
