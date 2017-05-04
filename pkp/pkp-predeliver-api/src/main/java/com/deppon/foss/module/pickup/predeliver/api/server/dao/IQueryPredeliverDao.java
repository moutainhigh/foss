package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;

/**
 * 
 * 派送信息查询DAO
 * @author zyr
 * @date 2015-05-26 上午10:51:13
 * @since
 * @version
 */
public interface IQueryPredeliverDao {
	/**
	 * 
	 * 按照查询条件查询派送信息
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	List<SendHandoverInfoDto> queryPredeliver(SendInfoSearchDto sendInfoSearchDto,int start, int limit);

	/**
	 * 
	 * 获得总条数
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	Long getPredeliverInfoCount(SendInfoSearchDto sendInfoSearchDto);
	
	/**
	 * 
	 * 按照查询条件查询派送信息非分页
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	List<SendHandoverInfoDto> queryPredeliverInfo(SendInfoSearchDto sendInfoSearchDto);
	/**
	 * 
	 * 按照查询条件查询派送信息  运单
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	List<SendHandoverInfoDto> queryPredeliverWaybill(SendInfoSearchDto sendInfoSearchDto,int start, int limit);
	
	/**
	 * 
	 * 按照查询条件查询派送信息  运单 非分页
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	List<SendHandoverInfoDto> queryPredeliverWaybillInfo(SendInfoSearchDto sendInfoSearchDto);

	/**
	 * 
	 * 获得总条数  运单
	 * @author zyr
	 * @date 2015-05-26 上午10:51:13
	 */
	Long getPredeliverInfoCountWaybill(SendInfoSearchDto sendInfoSearchDto);
}
