package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;

public interface ISendNetGroupMixInfoToWDGHService {
	/**
	 * 
	 *<p>接口同步方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19上午11:19:28
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	void syncNetGroupMixInfo(List<NetGroupMixEntity> dtos,String operateType);
}
