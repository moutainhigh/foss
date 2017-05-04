package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionBigTicketService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;

/**
 * 公共组件--价格区域查询action.
 *
 * @author shenweihua
 * @date 2014-07-4 上午10:47:17
 */
public class CommonPriceAllRegionBigTicketAction extends AbstractAction implements
IQueryAction{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/** The price region entity vo. */
	private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();
	// service
	/** The common price region service. */
	private ICommonAllPriceRegionBigTicketService commonAllPriceRegionBigTicketService;
	
	/**
	 * 价格区域查询方法.
	 *
	 * @return the string
	 * @author shenweihua
	 * @date 2013-08-21上午10:47:31
	 */
	@Override
	public String query() {
		priceRegionEntityVo.setPriceRegionEntities(commonAllPriceRegionBigTicketService
				.searchRegionByCondition(
						priceRegionEntityVo.getPriceRegionEntity(), start,
						limit));
		setTotalCount(commonAllPriceRegionBigTicketService
				.countRegionByCondition(priceRegionEntityVo
						.getPriceRegionEntity()));
		return returnSuccess();
	}
	
	/**
	 * 获取实体类
	 * @return
	 */
	public PriceRegionEntityVo getPriceRegionEntityVo() {
		return priceRegionEntityVo;
	}
	/**
	 * 设置实体类
	 * @param priceRegionEntityVo
	 */
	public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
		this.priceRegionEntityVo = priceRegionEntityVo;
	}
	/**
	 * 设置大票目的区域service
	 * @param commonAllPriceRegionBigTicketService
	 */
	public void setCommonAllPriceRegionBigTicketService(
			ICommonAllPriceRegionBigTicketService commonAllPriceRegionBigTicketService) {
		this.commonAllPriceRegionBigTicketService = commonAllPriceRegionBigTicketService;
	}
	
	
}
