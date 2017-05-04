package com.deppon.foss.module.base.baseinfo.server.action;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryAddressVo;

/**
 * 快递派送地址库Action
 * @author 198771
 *
 */
public class ExpressDeliveryAddressAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1646464132187494513L;
	
	/**
	 * 快递派送地址库Service
	 */
	private IExpressDeliveryAddressService expressDeliveryAddressService;
	
	/**
	 * 地址库VO
	 */
	private ExpressDeliveryAddressVo expressDeliveryAddressVo;
	
	public ExpressDeliveryAddressVo getExpressDeliveryAddressVo() {
		return expressDeliveryAddressVo;
	}

	public void setExpressDeliveryAddressVo(
			ExpressDeliveryAddressVo expressDeliveryAddressVo) {
		this.expressDeliveryAddressVo = expressDeliveryAddressVo;
	}

	public void setExpressDeliveryAddressService(
			IExpressDeliveryAddressService expressDeliveryAddressService) {
		this.expressDeliveryAddressService = expressDeliveryAddressService;
	}

	/**
	 * 列表查询
	 * @return
	 */
	public String queryExpressDeliveryAddressEntitys(){
		expressDeliveryAddressVo.setExpressDeliveryAddressEntityList(expressDeliveryAddressService.
				queryExpressDeliveryAddressEntitys(expressDeliveryAddressVo.getCondition(), start, limit));
		this.setTotalCount(expressDeliveryAddressService.getCountByCondition(expressDeliveryAddressVo.getCondition()));
		return returnSuccess();
	}

	/**
	 * 新增地址库
	 * @return
	 */
	public String addExpressDeliveryAddressEntity(){
		try {
			expressDeliveryAddressService.addExpressDeliveryAddressEntity(
					expressDeliveryAddressVo.getExpressDeliveryAddressEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 根据条件查询
	 * @return
	 */
	public String queryExpressDeliveryAddressEntitysByCondition(){
		expressDeliveryAddressVo.setExpressDeliveryAddressEntityList(
				expressDeliveryAddressService.queryExpressDeliveryAddressEntitysByCondition(
						expressDeliveryAddressVo.getCondition()));
		return returnSuccess();
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteExpressDeliveryAddressEntityByCodes(){
		try {
			expressDeliveryAddressService.deleteExpressDeliveryAddressEntityByCodes(expressDeliveryAddressVo.getIds()
				,expressDeliveryAddressVo.getExpressDeliveryAddressEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 批量修改
	 * @return
	 */
	public String updateBatchExpressDeliveryAddressEntity(){
		try {
			expressDeliveryAddressService.updateBatchExpressDeliveryAddressEntity(
				expressDeliveryAddressVo.getIds(), expressDeliveryAddressVo.getExpressDeliveryAddressEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateExpressDeliveryAddressEntity(){
		try {
			expressDeliveryAddressService.updateExpressDeliveryAddressEntity(
					expressDeliveryAddressVo.getIds(),expressDeliveryAddressVo.getExpressDeliveryAddressEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
}
