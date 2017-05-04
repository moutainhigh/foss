/**  
 * Project Name:tfr-load-api  
 * File Name:IAutoAddCodeByHandService.java  
 * Package Name:com.deppon.foss.module.transfer.load.api.server.service  
 * Date:2015年6月16日上午1:50:48  
 *  
 */
package com.deppon.foss.module.transfer.load.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;

/**  
 * ClassName: IAutoAddCodeByHandService <br/>  
 * Function: 待人工补码记录service. <br/>  
 * date: 2015年6月16日 上午1:50:48 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface IAutoAddCodeByHandService extends IService {
	/**
	 * insertAddCodeByHand:需要转人工补码时，新增一条待人工补码记录. <br/>  
	 *  Date:2015年6月16日上午1:45:23  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param entity
	 * @return  
	 * @since JDK 1.6
	 */
	int insertAddCodeByHand(AutoAddCodeByHandEntity entity);
	
	/**
	 * deleteAddCodeByHand:补码完成后，删除待人工补码记录. <br/>  
	 *  Date:2015年6月16日上午1:46:10  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param waybillNo
	 * @return  
	 * @since JDK 1.6
	 */
	int deleteAddCodeByHand(String waybillNo);
	
	
	
	/**
	* @description 根据运单号查询人工补码
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月27日 下午4:26:28
	*/
	public AutoAddCodeByHandEntity queryAddCodeByHand(String waybillNo,String reason);
}
