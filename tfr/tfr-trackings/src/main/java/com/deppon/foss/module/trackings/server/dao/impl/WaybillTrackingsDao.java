package com.deppon.foss.module.trackings.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.IWaybillTrackingsDao;
import com.deppon.foss.module.trackings.api.shared.domain.WaybillTrackingsLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.OrderWaybillDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.util.UUIDUtils;

public class WaybillTrackingsDao extends iBatis3DaoImpl implements IWaybillTrackingsDao {
	private static String prefix = "foss.trackings.waybillTrackingsMapper.";
	/**
	 * @author nly
	 * @date 2015年3月27日 下午2:38:13
	 * @function 保存订阅单号信息
	 * @param dto
	 * @return
	 */
	@Override
	public int addOrderWaybillNo(OrderWaybillDto dto) {
		dto.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(prefix + "addOrderWaybillNo", dto);
	}
	/**
	 * @author nly
	 * @date 2015年3月27日 下午2:39:26
	 * @function 根据单号查询订阅信息
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryWaybillInfoByNo(String waybillNo) {
		return this.getSqlSession().selectList(prefix + "queryWaybillInfoByNo", waybillNo);
	}
	/**
	 * @author nly
	 * @date 2015年3月29日 上午11:05:00
	 * @function 更新运单信息和路由信息
	 * @param waybillNo
	 * @param routeList
	 */
	@Override
	public void updateTrackByNoAndRoute(String waybillNo, List<String> routeList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("routeList", routeList);
		this.getSqlSession().update(prefix + "updateTrackByNoAndRoute", map);
	}
	/**
	 * @author nly
	 * @date 2015年3月30日 上午11:19:26
	 * @function 保存操作轨迹
	 * @param trackDto
	 */
	@Override
	public void saveWaybillTrack(WaybillTrackingsDto trackDto) {
		trackDto.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(prefix + "addWaybillTrack", trackDto);
	}
	/**
	 * @author nly
	 * @date 2015年3月31日 下午2:38:25
	 * @function 查询首次推送的订阅订单轨迹
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryPushWaybillInfo(OrderWaybillDto dto) {
		return this.getSqlSession().selectList(prefix + "queryPushWaybillInfo",dto);
	}
	/**
	 * @author nly
	 * @date 2015年4月2日 上午10:14:10
	 * @function 运单号是否存在
	 * @param code
	 * @return
	 */
	@Override
	public boolean checkWaybillNo(String code) {
		int count = (Integer) this.getSqlSession().selectOne(prefix + "queryWaybillNoCount",code);
		return count > 0;
	}
	/**
	 * @author nly
	 * @date 2015年4月2日 上午11:23:24
	 * @function 新增轨迹日志
	 * @param log
	 */
	@Override
	public void addTrackLog(WaybillTrackingsLogEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(prefix + "addTrackLog", entity);
	}
	/**
	 * @author nly
	 * @date 2015年4月2日 下午2:57:32
	 * @function 更新订阅单号表
	 * @param dto
	 */
	@Override
	public void updateOrderWaybillByNo(OrderWaybillDto dto) {
		this.getSqlSession().update(prefix + "updateOrderWaybillByNo", dto);
	}
	/**
	 * @author nly
	 * @date 2015年4月4日 下午1:07:53
	 * @function 删除单号轨迹
	 * @param code
	 */
	@Override
	public void deleteTrackByNo(String code) {
		this.getSqlSession().delete(prefix + "deleteTrackByNo", code);		
	}
	/**
	 * @author nly
	 * @date 2015年4月4日 下午1:50:48
	 * @function 获取routeId
	 * @param waybillNo
	 * @return
	 */
	@Override
	public String queryRouteId(String waybillNo) {
		return (String) this.getSqlSession().selectOne(prefix + "queryRouteId", waybillNo);
	}
	/**
	 * @author nly
	 * @date 2015年4月4日 下午2:48:48
	 * @function 更新轨迹信息
	 * @param trackDto
	 */
//	@Override
//	public void updateWaybillTrack(WaybillTrackingsDto trackDto) {
//		this.getSqlSession().update(prefix + "updateWaybillTrack", trackDto);
//	}
	/**
	 * @author nly
	 * @date 2015年4月4日 下午3:45:06
	 * @function 查询所有需推送的轨迹
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WaybillTrackingsDto> queryTrackInfo() {
//		return this.getSqlSession().selectList(prefix + "queryTrackInfo");
//	}
	/**
	 * @author nly
	 * @date 2015年4月9日 上午9:47:45
	 * @function 查询需推送stop的运单信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryStopPushWaybillInfo() {
		return this.getSqlSession().selectList(prefix + "queryStopPushWaybillInfo");
	}
	/**
	 * @author nly
	 * @date 2015年4月10日 上午9:11:55
	 * @function 查询运单对应的轨迹信息
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillTrackingsDto> queryTrackInfoByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(prefix + "queryTrackInfoByWaybillNo",waybillNo);
	}
	/**
	 * @author nly
	 * @date 2015年4月10日 上午10:38:33
	 * @function 更新为初始值
	 * @param code
	 */
	@Override
	public void updateOrderWaybillInfoByNo(String code,String orderType) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("code", code);
		map.put("operator", orderType);
		this.getSqlSession().update(prefix + "updateOrderWaybillInfoByNo",map);
	}
	
	@Override
	public void updateOrderWaybillByNoAndPush(OrderWaybillDto dto) {
		this.getSqlSession().update(prefix + "updateOrderWaybillByNoAndPush", dto);
	}
	/**
	 * @author nly
	 * @date 2015年4月11日 上午8:26:38
	 * @function 查询abort运单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryAbortPushWaybillInfo() {
		return this.getSqlSession().selectList(prefix + "queryAbortPushWaybillInfo");
	}
	/**
	 * @author nly
	 * @date 2015年4月11日 上午11:02:41
	 * @function 查询增量推送运单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryAppendPushWaybillInfo() {
		return this.getSqlSession().selectList(prefix + "queryAppendPushWaybillInfo");
	}
	/**
	 * @author nly
	 * @date 2015年4月11日 下午1:58:56
	 * @function 查询全量推送运单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWaybillDto> queryOverridePushWaybillInfo() {
		return this.getSqlSession().selectList(prefix + "queryOverridePushWaybillInfo");
	}
	/**
	 * @author nly
	 * @date 2015年4月11日 下午3:03:04
	 * @function 校验轨迹是否已存在
	 * @param trackDto
	 * @return
	 */
	@Override
	public boolean checkExistsTrack(WaybillTrackingsDto trackDto) {
		int count = (Integer) this.getSqlSession().selectOne(prefix + "selectTrack",trackDto);
		return count > 0;
	}
}
