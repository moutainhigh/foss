package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CustomerAddressDto;

public interface IEcsQueryService extends IService{
	 /**
		 *远程调用WK接口
		 * 根据手机号码查询收货人联系方式和地址信息
		 * @param mobilePhone
		 * @date 2017-03-17 下午 14:45
		 * @return
		 */
	List<CustomerAddressDto> queryConsignee(String mobilePhone);
}
