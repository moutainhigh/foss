package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;

/**
 * 
 * GUI查询走货路径相关服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-28 下午2:00:16,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-28 下午2:00:16
 * @since
 * @version
 */
public interface IWaybillFreightRouteService extends IService {
	/**
	 * 
	 * <p>查询配载部门和最终配载部门信息(在线查询)</p> 
	 * @author foss-sunrui
	 * @date 2012-12-28 下午2:22:50
	 * @param pickupCentralized	是否集中接送货
	 * @param orginalOrganizationCode 出发营业部
	 * @param destinationOrganizationCode 到达营业部
	 * @param productCode 运输性质
	 * @return
	 * @see
	 */
	OrgInfoDto queryLodeDepartmentInfoOnline(boolean pickupCentralized, String orginalOrganizationCode,
			String destinationOrganizationCode, String productCode);
}
