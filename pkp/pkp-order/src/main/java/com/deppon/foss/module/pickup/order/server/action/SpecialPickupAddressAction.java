package com.deppon.foss.module.pickup.order.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialPickupAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialAddressVo;

public class SpecialPickupAddressAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private ISpecialPickupAddressService specialPickupAddressService;
	
	private SpecialAddressVo specialAddressVo;
	
	/**
	 * 
	 * 根据条件查询特殊地址
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-19 上午8:42:39
	 */
	@JSON
	public String querySpecialAddressList() {
		try {
			//查询总条数
			this.totalCount = this.specialPickupAddressService.queryCountByCondition(specialAddressVo.getSpecialAddressConditionDto());
			//若总条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//查询特殊地址
				specialAddressVo.setSpecialAddressEntityList(specialPickupAddressService.querySpecialAddressByCommon(specialAddressVo.getSpecialAddressConditionDto(), start, limit));
			} else {
				//设置为null
				specialAddressVo.setSpecialAddressEntityList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 更新车辆信息
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-19 下午3:08:54
	 */
	@JSON
	public String updateVehicleByid() {
		try {
			int result = specialPickupAddressService.updateVehicleByid(specialAddressVo.getSpecialAddressEntity());
			if (result > 0) {
				//返回成功
				return super.returnSuccess("修改成功");
			} else {
				//返回异常
				return super.returnError("修改失败");
			}
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 保存地址信息
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-24 下午3:08:54
	 */
	@JSON
	public String savePickupAddressInfo() {
		try {
			SpecialAddressEntity specialAddressEntity = specialPickupAddressService.selectByAddress(specialAddressVo.getSpecialAddressEntity().getAddress());
			if(specialAddressEntity != null)
			{
				int res = specialPickupAddressService.updateVehicleByAddress(specialAddressVo.getSpecialAddressEntity());
				if (res > 0) {
					//返回成功
					return super.returnSuccess("保存成功");
				} else {
					//返回异常
					return super.returnError("保存失败");
				}
			}else{
				int result = specialPickupAddressService.addSpecialAddress(specialAddressVo.getSpecialAddressEntity());
				if (result > 0) {
					//返回成功
					return super.returnSuccess("保存成功");
				} else {
					//返回异常
					return super.returnError("保存失败");
				}
			}
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 作废地址信息
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-19 下午3:08:54
	 */
	@JSON
	public String invalidSpecialAddress() {
		try {
			int result = specialPickupAddressService.deleteSpecialAddress(specialAddressVo.getSpecialAddressConditionDto().getId());
			if (result > 0) {
				//返回成功
				return super.returnSuccess("作废地址信息成功");
			} else {
				//返回异常
				return super.returnError("作废地址信息失败");
			}
		} catch (DispatchException e) {
			return returnError(e);
		}
	}

	public void setSpecialPickupAddressService(
			ISpecialPickupAddressService specialPickupAddressService) {
		this.specialPickupAddressService = specialPickupAddressService;
	}

	public SpecialAddressVo getSpecialAddressVo() {
		return specialAddressVo;
	}

	public void setSpecialAddressVo(SpecialAddressVo specialAddressVo) {
		this.specialAddressVo = specialAddressVo;
	}

}
