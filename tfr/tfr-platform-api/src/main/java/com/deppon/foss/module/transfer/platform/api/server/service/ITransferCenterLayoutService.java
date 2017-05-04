/**
 * Project Name:tfr-platform-api
 * File Name:ITransferCenterLayoutService.java
 * Package Name:com.deppon.foss.module.transfer.platform.api.server.service
 * Date:2014年12月30日上午9:08:14
 * Copyright (c) 2014, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;

/**
 * ClassName:ITransferCenterLayoutService <br/>
 * Reason:	 场内布局图service接口. <br/>
 * Date:     2014年12月30日 上午9:08:14 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface ITransferCenterLayoutService extends IService {
	
	/**
	 * 
	 * queryGoodsArea:查询库区. <br/>
	 * @author 045923
	 * @param orgCode
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryGoodsArea(String orgCode);
	
	/**
	 * queryPlatform:查询月台. <br/>
	 * Date:2014年12月30日上午9:59:52
	 * @author 045923
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryPlatform(String orgCode);
	
	/**
	 * 
	 * queryTransferArea:查询待叉区. <br/>
	 * Date:2014年12月30日上午10:00:46
	 * @author 045923
	 * @param orgCode
	 * @return
	 * @since JDK 1.6
	 */
	List<TransferCenterUnitDto> queryTransferArea(String orgCode);
	
	/**
	 * beTransferCenter:判断一个部门或其上级部门是否为外场. <br/>
	 * Date:2014年12月30日上午11:13:57
	 * @author 045923
	 * @param org
	 * @return
	 * @since JDK 1.6
	 */
	String beTransferCenter();

}

