package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.PathDetailEntity;


/**
* @description 智能分拣dao
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月25日 下午2:34:17
*/
public interface IExpressIntelligentSortCabDao {

	
	/**
	* @description 查询路由
	* @param depetCode
	* @param startTime
	* @param endTime
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	 * @param weightLimit 
	 * @param weightLimit 
	* @update 2015年4月25日 下午2:34:07
	*/
	List<PathDetailEntity> queryWaybillPathDetail(String depetCode, Date startTime, Date endTime, int weightLimit);

	
	/**
	* @description 查询路由总条数
	* @param depetCode
	* @param startTime
	* @param endTime
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	 * @param weightLimit 
	 * @param weightLimit 
	* @update 2015年4月25日 下午2:34:12
	*/
	int queryWaybillPathDetailCount(String depetCode, Date startTime, Date endTime, int weightLimit);


	
	/**
	* @description 查询运输性质
	* @param productCode
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月21日 下午2:12:16
	*/
	String queryWaybillTransportName(String productCode);

}
