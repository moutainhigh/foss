package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendingWayBillInfoResponse;

/**
 * 
 * 派送信息查询Service
 * @author zyr
 * @date 2015-05-22 下午2:34:18
 * @since
 * @version
 */
public interface IQueryPredeliverService extends IService {
	/** 
	 * 按照查询条件查询派送信息
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	List<SendHandoverInfoDto> queryPredeliver(SendInfoSearchDto sendInfoSearchDto,int start, int limit);
	
	/**
	 * 
	 * 获得总条数
	 * @author zyr
	 * @date 2015-05-22 上午10:51:38
	 */
	Long getPredeliverInfoCount(SendInfoSearchDto sendInfoSearchDto);
	/** 
	 * 按照查询条件查询派送信息  运单
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	List<SendHandoverInfoDto> queryPredeliverWaybill(SendInfoSearchDto sendInfoSearchDto,int start, int limit);
	
	/**
	 * 
	 * 获得总条数 运单
	 * @author zyr
	 * @date 2015-05-22 上午10:51:38
	 */
	Long getPredeliverInfoCountWaybill(SendInfoSearchDto sendInfoSearchDto);
	
	/**
	 * 查询总派送信息(交接单).
	 * @author zyr
	 * @date 2015-05-28 下午3:31:16
	 */
	SendInfoSearchDto queryPredeliverTotal(SendInfoSearchDto sendInfoSearchDto,List<SendHandoverInfoDto> sendHandoverInfoDtoList);
	
	/**
	 * 查询总派送信息(运单).
	 * @author zyr
	 * @date 2015-05-28 下午3:31:16
	 */
	SendInfoSearchDto queryPredeliverWaybillTotal(SendInfoSearchDto sendInfoSearchDto,List<SendHandoverInfoDto> sendHandoverInfoDtoList);
	
	/** 
	 * 按照查询条件导出派送信息(交接单)
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	InputStream queryPredeliverInfo(SendInfoSearchDto sendInfoSearchDto);
	
	/** 
	 * 按照查询条件导出派送信息(运单)
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	InputStream queryPredeliverWaybillInfo(SendInfoSearchDto sendInfoSearchDto);
	
	/** 
	 * 按照查询条件查询派送信息(快递运单).
	 * @author zm
	 * @date 2016年12月10日11:31:29
	 */
	List<SendingWayBillInfoResponse> queryPredeliverExpressWaybill(SendInfoSearchDto sendInfoSearchDto) throws BusinessException;

	/**
	 * 查询总派送信息(快递运单).
	 * @author zm
	 * @date 2016年12月10日11:31:29
	 */
	SendInfoSearchDto queryPredeliverWaybillExpressTotal(SendInfoSearchDto sendInfoSearchDto,List<SendingWayBillInfoResponse> wayBillInfoResponses);
	
	/** 
	 * 按照查询条件导出派送信息(快递运单)
	 * @author zm
	 * @date 2016年12月10日14:52:33
	 */
	InputStream queryExpressWaybillInfo(SendInfoSearchDto sendInfoSearchDto);
}
