/**
 * Project Name:tfr-platform
 * File Name:TransferCenterLayoutDao.java
 * Package Name:com.deppon.foss.module.transfer.platform.server.dao.impl
 * Date:2015年1月6日上午10:24:58
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITransferCenterLayoutDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;

/**
 * ClassName:TransferCenterLayoutDao <br/>
 * Reason:	 场内布局dao实现类. <br/>
 * Date:     2015年1月6日 上午10:24:58 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransferCenterLayoutDao extends iBatis3DaoImpl implements
		ITransferCenterLayoutDao {
	
	private static final String NAMESPACE = "foss.platform.transferCenterLayout.";

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferCenterUnitDto> queryGoodsArea(String orgCode) {
		return this.getSqlSession().selectList(NAMESPACE + "queryGoodsArea",orgCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferCenterUnitDto> queryPlatform(String orgCode) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPlatform",orgCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferCenterUnitDto> queryTransferArea(String orgCode) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTransferArea",orgCode);
	}

}

