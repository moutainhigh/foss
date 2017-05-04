package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;


/**
* @description 退单率操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:25:08
*/
public interface IReturnRateService extends IService {
	
	/**
	* @description 查询退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:10
	*/
	public List<ReturnRateEntity> queryReturnRateList(ReturnRateEntity returnRateEntity,int start,int limit);
	
	/**
	* @description 查询退单率总记录
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:54
	*/
	public long queryReturnRateListCount(ReturnRateEntity returnRateEntity);
	
	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:48:41
	*/
	List<ReturnRateEntity> queryReturnRateLogList(ReturnRateEntity returnRateEntity,int start,int limit);
	
	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:48:41
	*/
	long queryReturnRateLogListCount(ReturnRateEntity returnRateEntity);
	
	
	/**
	* @description 退单率定时执行Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:34:21
	*/
	public void doReturnRateJobList(Date jobDate,int threadNo,int threadCount) throws Exception;
	
	
	/**
	 *@desc 查询外场提交派送装车任务票数
	 *@param orgCodeList
	 *@return List<ReturnRateEntity>
	 *@author 105795
	 *@date 2015年3月16日下午4:33:33 
	 */
	List<ReturnRateEntity> queryDeliverLoadWaybillQty(List<String> orgCodeList);
}
