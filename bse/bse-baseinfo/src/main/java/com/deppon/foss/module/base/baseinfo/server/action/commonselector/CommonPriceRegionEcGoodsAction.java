package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionEcGoodsService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;

/**
 * 公共组件--首续重价格区域查询action.
 */
public class CommonPriceRegionEcGoodsAction extends AbstractAction implements IQueryAction{ 

	private static final long serialVersionUID = 1L;
	private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();
	private ICommonPriceRegionEcGoodsService commonPriceRegionEcGoodsService;
	public PriceRegionEntityVo getPriceRegionEntityVo() {
		return priceRegionEntityVo;
	}
	public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
		this.priceRegionEntityVo = priceRegionEntityVo;
	}
	public void setCommonPriceRegionEcGoodsService(ICommonPriceRegionEcGoodsService commonPriceRegionEcGoodsService) {
		this.commonPriceRegionEcGoodsService = commonPriceRegionEcGoodsService;
	}

	/**
	 * 首续重区域查询方法.
	 */
	@Override
	public String query() {
		priceRegionEntityVo.setPriceRegionEntities(commonPriceRegionEcGoodsService.searchRegionEcGoodsByCondition(priceRegionEntityVo.getPriceRegionEntity(), start,limit));
		super.setTotalCount(commonPriceRegionEcGoodsService.countRegionEcGoodsByCondition(priceRegionEntityVo.getPriceRegionEntity()));
		return returnSuccess();
	}
}
