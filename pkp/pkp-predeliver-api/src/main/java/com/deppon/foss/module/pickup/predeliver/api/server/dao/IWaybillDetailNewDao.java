package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailNewQueryDto;



/**
 * 运单明细
 * @author 159231 meiying
 * 2015-6-2  下午7:51:59
 */
public interface IWaybillDetailNewDao {
	/**
	 * 根据运单集合 或者除了货物状态查询运单明细信息(总数)
	 * @author 159231 meiying
	 * 2015-6-3  下午3:19:52
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybillDetailNewByWaybillsOtherCount(WaybillDetailNewQueryDto pre);
	/**
	 * 根据运单集合 或者除了货物状态查询运单明细信息
	 * @author 159231 meiying
	 * 2015-6-3  下午3:21:06
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewByWaybillsOtherList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 查询送货日期默认30天内的数据 总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:24
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybillDetailNewOneMonthCount(WaybillDetailNewQueryDto pre) ;
	/**
	 * 查询送货日期默认30天内的数据
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:39
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewOneMonthList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 查询在库存中运单明细信息   总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:24
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybillDetailNewByInStockCount(WaybillDetailNewQueryDto pre) ;
	/**
	 * 查询在库存中运单明细信息  
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:39
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewInStockList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 查询在已到达运单明细信息    总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:24
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybillDetailNewArrivedCount(WaybillDetailNewQueryDto pre) ;
	/**
	 * 查询在已到达运单明细信息 
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:39
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewArrivedList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 查询在预计到达运单明细信息    总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:24
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybillDetailNewPreArriveCount(WaybillDetailNewQueryDto pre) ;
	/**
	 * 查询在预计到达运单明细信息
	 * @author 159231 meiying
	 * 2015-6-3  下午3:27:39
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewPreArriveList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-7  下午6:16:21
	 * @param deliverbillId
	 * @return
	 */
	List<WaybillDetailBillArrageDto>  queryDeliverbillDetailList(Map<Object,Object> map);
	
	/**
	 * 根据运单号查询收货联系人
	 * @param waybillNo
	 * @return
	 */
	String queryWaybillDetailNewByWaybillNo(String waybillNo);
	/**
	 * 根据运单号查询交单时间
	 * @param waybillNo
	 * @return
	 */
	Date queryBilltimeByWaybillNo(String waybillNo);
	
}
