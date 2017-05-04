package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * OWS、DOP标签信息同步接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-22 下午5:23:50, </p>
 * @author dp-zhangfan 329834
 * @date 2016-05-27 下午5:23:50
 * @since
 * @version
 */
public interface IOwsDopLabelInfoJmsService {
	
	 /**
     * 
     * <p>同步标签信息</p> 
     * @author foss-sunrui
     * @date 2012-11-22 下午5:24:31
     * @param request
     * @param labelProcessEntityId 即标签推送线程实体的id
     * @return
     * @see
     */
	ResultDto sendLabelInfo(String waybillNo, String labelProcessEntityId);
    
}
