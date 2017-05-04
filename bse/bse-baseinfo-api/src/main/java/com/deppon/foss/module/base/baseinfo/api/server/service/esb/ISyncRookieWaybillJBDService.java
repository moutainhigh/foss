package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;

/**
 * 同步FOSS的集包地基础资料给dop，oms
 * @author 273311
 * @date 2016-1-21 上午11:50:25
 */
public interface ISyncRookieWaybillJBDService extends IService {
	/**
	 * 同步FOSS的集包地基础资料给dop，oms
	 * @author 273311
	 * @date 2016-1-21 上午11:50:25
	 */
     void syncToDopOms(List<RookieWaybillJBDEntity> entitys,String actionType);
}
