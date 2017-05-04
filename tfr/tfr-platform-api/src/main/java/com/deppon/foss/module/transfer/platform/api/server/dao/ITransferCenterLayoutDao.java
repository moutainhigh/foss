/**
 * Project Name:tfr-platform-api
 * File Name:ITransferCenterLayoutDao.java
 * Package Name:com.deppon.foss.module.transfer.platform.api.server.dao
 * Date:2015年1月6日上午10:20:48
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;

/**
 * ClassName:ITransferCenterLayoutDao <br/>
 * Reason:	 场内布局dao接口. <br/>
 * Date:     2015年1月6日 上午10:20:48 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface ITransferCenterLayoutDao {
	
	/**
	 * 
	 * queryGoodsArea:查询库区. <br/>
	 * Date:2015年1月6日上午10:21:49
	 * @author 045923
	 * @param orgCode
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryGoodsArea(String orgCode);
	
	/**
	 * 
	 * queryPlatform:查询月台. <br/>
	 * Date:2015年1月6日上午10:22:24
	 * @author 045923
	 * @param orgCode
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryPlatform(String orgCode);
	
	/**
	 * 
	 * queryTransferArea:查询待叉区. <br/>
	 * Date:2015年1月6日上午10:22:51
	 * @author 045923
	 * @param orgCode
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryTransferArea(String orgCode);

}

