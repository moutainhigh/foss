package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWaybillDetailNewDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailNewQueryDto;

/**
 * 可视化排单Dao实现
 * @author 239284
 *
 */
public class WaybillDetailNewDao  extends iBatis3DaoImpl  implements IWaybillDetailNewDao{

	//可视化排单 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IWaybillDetailDao.";

	/**
	 * 根据运单集合 或者除了货物状态查询运单明细信息 总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public WaybillDeliverNewCountDto queryWaybillDetailNewByWaybillsOtherCount(WaybillDetailNewQueryDto pre) {
		if(pre==null){
			return null;
		}
		return (WaybillDeliverNewCountDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewByWaybillNosCount", pre);
	}
	/**
	 * 查询待交单运单List
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewByWaybillsOtherList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailNewByWaybillNos", pre, rowBounds);
	}
	/**
	 * 查询送货日期默认30天内的数据 总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public WaybillDeliverNewCountDto queryWaybillDetailNewOneMonthCount(WaybillDetailNewQueryDto pre) {
		if(pre==null){
			return null;
		}
		return (WaybillDeliverNewCountDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewByTimeCount", pre);
	}
	/**
	 * 查询送货日期默认30天内的数据
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewOneMonthList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailNewByTime", pre, rowBounds);
	}
	/**
	 * 查询在库存中运单明细信息  总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public WaybillDeliverNewCountDto queryWaybillDetailNewByInStockCount(WaybillDetailNewQueryDto pre) {
		if(pre==null){
			return null;
		}
		return (WaybillDeliverNewCountDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewByInStockCount", pre);
	}
	/**
	 * 查询在库存中运单明细信息 
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewInStockList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailNewInStock", pre, rowBounds);
	}
	/**
	 * 查询在已到达运单明细信息 总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public WaybillDeliverNewCountDto queryWaybillDetailNewArrivedCount(WaybillDetailNewQueryDto pre) {
		if(pre==null){
			return null;
		}
		return (WaybillDeliverNewCountDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewArrivedCount", pre);
	}
	/**
	 * 查询在已到达运单明细信息
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewArrivedList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailNewArrived", pre, rowBounds);
	}
	/**
	 * 查询在预计到达运单明细信息 总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public WaybillDeliverNewCountDto queryWaybillDetailNewPreArriveCount(WaybillDetailNewQueryDto pre) {
		if(pre==null){
			return null;
		}
		return (WaybillDeliverNewCountDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewPreArriveCount", pre);
	}
	/**
	 * 查询在预计到达运单明细信息
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewPreArriveList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailNewPreArrive", pre, rowBounds);
	}
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-7  下午6:16:48
	 * @param deliverbillId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WaybillDetailBillArrageDto>  queryDeliverbillDetailList(Map<Object,Object> map){
		return this.getSqlSession().selectList(NAMESPACE + "selectByDeliverbillId", map);
	}
	
	/**
	 * 根据运单号查询收货联系人
	 */
	@Override
	public String queryWaybillDetailNewByWaybillNo(
			String waybillNo) {
		if(waybillNo==null){
			return null;
		}
		return   (String) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailNewByWaybillNo", waybillNo);
	}
	
	/**
	 * 根据运单号查询交单时间
	 */
	@Override
	public Date queryBilltimeByWaybillNo(String waybillNo){
		return    (Date) this.getSqlSession().selectOne(NAMESPACE + "queryBilltimeByWaybillNo", waybillNo);
	}
}
