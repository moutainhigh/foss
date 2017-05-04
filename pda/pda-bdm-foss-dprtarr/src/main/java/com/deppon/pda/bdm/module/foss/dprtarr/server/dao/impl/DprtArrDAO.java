package com.deppon.pda.bdm.module.foss.dprtarr.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.dprtarr.server.dao.IDprtArrDAO;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.ArrivalScanEntity;



/**
 * 到达出发DAO接口实现类    
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-13 上午08:44:28
 */
public class DprtArrDAO extends iBatis3DaoImpl implements IDprtArrDAO {

	/**
	 * 
	 * 描述 保存到达扫描信息  
	 * @param arrivalScan
	 */
	@Override
	public void saveArrivalScan(ArrivalScanEntity arrivalScan) {
		getSqlSession().insert(getClass().getName() + ".saveArrivalScan", arrivalScan);
	}
}
