package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;
/**
 * 公共组件--价格区域查询action.
 *
 * @author lifanghong
 * @date 2013-08-21 上午10:47:17
 */
public class CommonPriceAllRegionAction extends AbstractAction implements
IQueryAction{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -7691655945994183614L;
	// vo
	/** The price region entity vo. */
	private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();
	// service
	/** The common price region service. */
	private ICommonAllPriceRegionService commonAllPriceRegionService;

	/**
	 * 价格区域查询方法.
	 *
	 * @return the string
	 * @author lifanghong
	 * @date 2013-08-21上午10:47:31
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		priceRegionEntityVo.setPriceRegionEntities(commonAllPriceRegionService
				.searchRegionByCondition(
						priceRegionEntityVo.getPriceRegionEntity(), start,
						limit));
		setTotalCount(commonAllPriceRegionService
				.countRegionByCondition(priceRegionEntityVo
						.getPriceRegionEntity()));
		return returnSuccess();
	}

	/**
	 * get、set方法.
	 *
	 * @return the price region entity vo
	 * @author lifanghong
	 * @date 2013-08-21 上午10:51:57
	 * @retrun PriceRegionEntityVo
	 */
	
	public PriceRegionEntityVo getPriceRegionEntityVo() {
		return priceRegionEntityVo;
	}

	/**
	 * Sets the price region entity vo.
	 *
	 * @param priceRegionEntityVo the new price region entity vo
	 */
	public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
		this.priceRegionEntityVo = priceRegionEntityVo;
	}

	public void setCommonAllPriceRegionService(
			ICommonAllPriceRegionService commonAllPriceRegionService) {
		this.commonAllPriceRegionService = commonAllPriceRegionService;
	}

	

}
