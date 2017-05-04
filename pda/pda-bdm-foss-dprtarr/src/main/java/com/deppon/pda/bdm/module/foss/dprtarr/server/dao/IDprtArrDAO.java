package com.deppon.pda.bdm.module.foss.dprtarr.server.dao;

import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.ArrivalScanEntity;

/**
 * 出发到达DAO接口
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-13 上午08:39:50
 */
public interface IDprtArrDAO {

	
	
	/**
	 * 
	 * 描述 保存到达扫描信息  
	 * @param arrivalScan
	 */
	public void saveArrivalScan(ArrivalScanEntity arrivalScan);
	
}
