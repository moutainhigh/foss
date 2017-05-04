package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
/**
 * 同步营业部自提派送扩展资料给周边系统，订单，快递
 * @author 273311
 *
 */
public interface ISyncSalesDescExpandService extends IService{
   /**
    * <p>同步营业部自提派送扩展资料给周边系统，订单，快递</p>
    * @date 2016-3-23 下午3:48:24
    * @author 273311
    */
	void syncSalesDescExpand (List<SalesDescExpandEntity> salesDescExpandLis,String operateType);
	
}
