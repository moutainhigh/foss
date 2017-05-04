package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultValueDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 运单签收数据向外部提供接口
 * @author 269871
 * @date 2015-7-15 下午15:28:00
 */
public interface ISignDataOutService extends IService {
	/**
	 * 返回运单集合的签收数、异常签收数、签收率实体
	 * @param list运单集合
	 * @author 269871 foss-zhuliangzhi
	 *  @date 2015-7-15 下午15:30:08
	 * @return
	 */
	public ResultValueDto getReturnData(List<String> list);
//	public List signDetails(List<WaybillEntity> list);
}
