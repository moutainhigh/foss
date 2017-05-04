package com.deppon.foss.module.base.querying.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.querying.shared.dto.DeliveryInformationDto;
/**
 * 特殊增值服务信息接口
 * @author 268984
 *
 */
public interface ISpecialValueAddedService extends IService {
    /**
     * 
     * @param waybillNo 运单号
     * @return 提货信息
     */
	List<DeliveryInformationDto> queryDeliveryInfo(String waybillNo);
}
