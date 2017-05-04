package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;

/**
 * 同步FOSS的试点城市和快递代理城市信息给官网、CRM、呼叫中心系统
 * @author 088933-foss-zhangjiheng
 * @date 2013-7-24 上午11:50:25
 */
public interface ISyncExpressCityService extends IService {
	/**
	 * 同步FOSS的试点城市和快递代理城市信息给官网、CRM、呼叫中心系统
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-7-24 上午11:50:25
	 */
     void syncExpressCityToOws(List<ExpressCityResultDto> list);
}
