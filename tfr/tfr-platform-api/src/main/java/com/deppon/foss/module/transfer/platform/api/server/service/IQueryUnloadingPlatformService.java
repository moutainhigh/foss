package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;

public interface IQueryUnloadingPlatformService extends IService {

	/**
	 * 
	 * 查询卸车进度
	 * @param queryUnloadingProgressConditionDto 查询卸车进度条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:16:30
	 */
	List<QueryUnloadingPlatformResultDto> 
	queryUnloadingProgressInfo(QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto,int limit,int start);
	/**
	 * 
	 * 查询卸车进度 count
	 * @param queryUnloadingProgressConditionDto 查询卸车进度条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:13:54
	 */
	Long queryUnloadingProgressInfoCount(QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto);
	
	/**
	 * 
	 * 查询当前部门装卸车标准
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午9:16:13
	 */
	LoadAndUnloadStandardDto queryLoadAndUnloadStd();
	
	
	
	/**
	* @description 未卸车明细 卡货
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午4:45:13
	*/
	List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailListKH(String taskId,int start,int limit);
	
	
	/**
	* @description 未卸车明细 的总重量和总体积
	* @param taskId
	* @param type
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:49:32
	*/
	NoUnloadGoodsDetail queryNoUnloadGoodsDetailListSum(String taskId,String type);
	
	
	/**
	* @description 未卸车明细 卡货 总记录
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:35:55
	*/
	long queryNoUnloadGoodsDetailListKHCount(String taskId);
	
	
	/**
	* @description  未卸车明细 卡货导出
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午8:41:31
	*/
	InputStream exportNoUnloadGoodsDetailKH(String taskId);
	
	
	/**
	* @description 未卸车明细 城际
	* @param taskId
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午4:50:01
	*/
	List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailListCJ(String taskId,int start,int limit);
	
	/**
	* @description 未卸车明细 城际 总记录
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:35:53
	*/
	long queryNoUnloadGoodsDetailListCJCount(String taskId);
	
	
	
	/**
	* @description 未卸车明细 城际 导出
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午8:42:10
	*/
	InputStream exportNoUnloadGoodsDetailCJ(String taskId);
	
}
