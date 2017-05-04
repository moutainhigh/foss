package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;

/**
 * 同步快递点部和营业部的对应关系到CRM
 * 
 * @author WangPeng
 * @date   2013-8-1 下午5:34:58
 *
 */
public interface ISyncExpressPartAndSalesDeptService extends IService {

	/**
	 * 同步快递点部和营业部的对应关系到CRM
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-1 下午5:34:58
	 * @param   dto
	 *
	 */
	void syncExpressPartAndDeptToCrm(List<ExpressPartSalesDeptEntity> entity);
}
