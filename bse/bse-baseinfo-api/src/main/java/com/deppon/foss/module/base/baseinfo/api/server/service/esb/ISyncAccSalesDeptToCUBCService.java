package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.AcceptPointSalesDeptDto;

/**
 * 同步FOSS的接驳点营业部关系表给结算中心
 * @author 
 * @date 
 */
public interface ISyncAccSalesDeptToCUBCService {

	 /**
     * 同步FOSS的接驳点营业部关系表给结算中心
     * @author 307196
     * @date 
     */
	void syncAccSalesDept(AcceptPointSalesDeptDto entitys, String operateType);
}
