package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionBigTicketService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;

/**
 * 公共组件--价格区域查询action.
 *
 * @author shenweihua
 * @date 2014-07-3 上午10:47:17
 */
public class CommonPriceRegionBigTicketAction extends AbstractAction implements IQueryAction{ 

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/** The price region entity vo. */
	private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();
	// service
	/** The common price region service. */
	private ICommonPriceRegionBigTicketService commonPriceRegionBigTicketService;
	
	/**
	 * 大票区域查询方法.
	 * @return the string
	 * @author shenweihua
	 * @date 2014-07-4 上午10:47:31
	 */
	@Override
	public String query() {
		priceRegionEntityVo.setPriceRegionEntities(commonPriceRegionBigTicketService
				.searchRegionBigTicketByCondition(
						priceRegionEntityVo.getPriceRegionEntity(), start,
						limit));
		setTotalCount(commonPriceRegionBigTicketService
				.countRegionBigTicketByCondition(priceRegionEntityVo
						.getPriceRegionEntity()));
		return returnSuccess();
	}
	
	/**
	 * 获取价格区域vo
	 * @return
	 */
	public PriceRegionEntityVo getPriceRegionEntityVo() {
		return priceRegionEntityVo;
	}
	/**
	 * 设置价格区域vo
	 * @param priceRegionEntityVo
	 */
	public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
		this.priceRegionEntityVo = priceRegionEntityVo;
	}
	/**
	 * 设置大票价格区域service
	 * @param commonPriceRegionBigTicketService
	 */
	public void setCommonPriceRegionBigTicketService(
			ICommonPriceRegionBigTicketService commonPriceRegionBigTicketService) {
		this.commonPriceRegionBigTicketService = commonPriceRegionBigTicketService;
	}

	
}
