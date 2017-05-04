package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;

/**
 * DOP、OWS标签信息推送查询接口
 * @author foss-zhangfan 329834
 * @date 20160526
 * @since
 * @version
 */
public interface ILabelPushService extends IService{
	/**
	 * 获取标签信息
	 * @param waybillPendingEntity 待补录运单实体
	 * @param originOrgCode 始发部门CODE
	 * @return 标签信息（包含订单信息）
	 */
	public LabelInfoDto queryLabelInfos(WaybillEntity waybillEntity);
}
