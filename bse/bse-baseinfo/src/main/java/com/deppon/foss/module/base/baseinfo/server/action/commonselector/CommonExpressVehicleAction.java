package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressVehicleVO;

/**
 * 快递车牌查询公共选择器
 * 
 * @author WangPeng
 * @date   2013-07-29 2:14 PM 
 * 
 */
public class CommonExpressVehicleAction extends AbstractAction implements
		IQueryAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 389606566210654064L;

	/**
	 * 快递代理车牌查询接口
	 */
	ICommonExpressVehicleService  commonExpressVehicleService;
	
	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}

	ExpressVehicleVO expressVehicleVo = new ExpressVehicleVO();
	
	public ExpressVehicleVO getExpressVehicleVo() {
		return expressVehicleVo;
	}

	public void setExpressVehicleVo(ExpressVehicleVO expressVehicleVo) {
		this.expressVehicleVo = expressVehicleVo;
	}

	/**
	 * 查询车牌号
	 * 
	 * @author WangPeng
	 * @Date   2013-7-29 下午4:25:13
	 * @return
	 *
	 */
	@Override
	public String query() {
		expressVehicleVo.setExpressVehicleList(commonExpressVehicleService.
				queryExpressVehicleNoListBySelectiveCondition(expressVehicleVo.getExpressVehicle(),
				limit, start));
		setTotalCount(commonExpressVehicleService.
				queryExpressVehicleNoRecordCountBySelectiveCondition(expressVehicleVo.getExpressVehicle()));
		return returnSuccess();
	}

}
