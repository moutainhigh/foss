package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;

public interface ISendOutBranchInfoService {
	/**
	 * 
	 *<p>接口同步方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-29上午11:11:28
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	void syncOutBranchInfoToWDGH(OuterBranchEntity dtos,String operateType);
}
