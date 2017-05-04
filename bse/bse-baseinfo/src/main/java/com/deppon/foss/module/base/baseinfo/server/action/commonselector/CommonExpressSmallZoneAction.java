/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonExpressSmallZoneVo;

/**
 *<p>Title: CommonExpressSmallZoneAction</p>
 * <p>Description:快递收派小区action </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public class CommonExpressSmallZoneAction extends AbstractAction implements IQueryAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前后台交互的实体
	 */
	private CommonExpressSmallZoneVo commonExpressZoneVo;
	/**
	 * service
	 */
	private ICommonExpressSmallZoneService commonExpressSmallZoneService;
	/**
	 * @return the commonExpressZoneVo
	 */
	public CommonExpressSmallZoneVo getCommonExpressZoneVo() {
		return commonExpressZoneVo;
	}

	/**
	 * @param commonExpressZoneVo the commonExpressZoneVo to set
	 */
	public void setCommonExpressZoneVo(CommonExpressSmallZoneVo commonExpressZoneVo) {
		this.commonExpressZoneVo = commonExpressZoneVo;
	}

	/**
	 * @param commonExpressSmallZoneService the commonExpressSmallZoneService to set
	 */
	public void setCommonExpressSmallZoneService(
			ICommonExpressSmallZoneService commonExpressSmallZoneService) {
		this.commonExpressSmallZoneService = commonExpressSmallZoneService;
	}

	/**
	 *<p>Title: query</p>
	 *<p>查询小区的方法</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午10:45:14
	 * @return
	*/
	@Override
	public String query() {
		CommonExpressSmallZoneEntity entity =commonExpressZoneVo.getCommonExpressSmallZoneEntity();
		commonExpressZoneVo.setCommonExpressSmallZoneList(commonExpressSmallZoneService.queryCommonExpressSmallZoneList(entity, start, limit));
		this.setTotalCount(commonExpressSmallZoneService.queryCommonExpressSmallZoneCount(entity));
		return returnSuccess();
	}

}
