package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.dao.IFossConfigEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;

/**
 *  esb地址配置Service
 *  @author 269044
 *  @date 2015-11-04
 */
public class FossConfigEntityService implements IFossConfigEntityService {

	/**
	 * esb地址查询接口 
	 */
	private IFossConfigEntityDao fossConfigEntityDao;
	/**
	 * 查询esb地址信息
	 * @author 269044
	 * @date 2015-11-04 
	 * @param serverCode
	 * @return FossConfigEntity
	 */
	@Override
	public FossConfigEntity queryFossConfigEntityByServerCode(String serverCode) {
		return fossConfigEntityDao.queryFossConfigEntityByServerCode(serverCode);
	}
	
	/**
	 * setter
	 * @param fossConfigEntityDao
	 */
	public void setFossConfigEntityDao(IFossConfigEntityDao fossConfigEntityDao) {
		this.fossConfigEntityDao = fossConfigEntityDao;
	}

}
