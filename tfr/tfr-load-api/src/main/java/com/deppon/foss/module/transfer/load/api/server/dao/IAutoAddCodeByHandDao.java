/**  
 * Project Name:tfr-load-api  
 * File Name:IAutoAddCodeByHandDao.java  
 * Package Name:com.deppon.foss.module.transfer.load.api.server.dao  
 * Date:2015年6月16日上午1:44:01  
 *  
 */
package com.deppon.foss.module.transfer.load.api.server.dao;

import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;

/**  
 * ClassName: IAutoAddCodeByHandDao <br/>  
 * Function: 待人工补码记录dao. <br/>  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface IAutoAddCodeByHandDao {
	
	/**
	 * insertAddCodeByHand:新增一条待人工补码记录. <br/>  
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
	
	
	/**
	* @description 查询此运单在[自动补码-待手工补码表]是否存在
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2016年1月29日 下午3:13:19
	*/
	public String searchAddCodeByHandByWaybillNo(String waybillNo);
	
	
	/**
	* @description 如果运单已在[自动补码-待手工补码表]存在,就更新此条记录
	* @param entity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2016年1月29日 下午3:20:27
	*/
	public int updateAddCodeByHandByWaybillNo(AutoAddCodeByHandEntity entity);
}
