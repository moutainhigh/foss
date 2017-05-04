package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.util.define.FossConstants;

public class ForecastQuantityJOBDao extends iBatis3DaoImpl implements IForecastQuantityJOBDao{

	public static String NAMESPACE="Foss.platform.forecastQuantityJOB.";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectBasOrgByCode(String threadNo, String threadCount,
			boolean isTransferCenter) {
		Map<String, Object> map=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_3);
		//线程号
		map.put("threadNo", threadNo);
		//线程数
		map.put("threadCount", threadCount);
		//是否外场如果为true则是外场，如果false则是营业部
		if(isTransferCenter){
			map.put("transferCenter", FossConstants.YES);
		}else{
			map.put("salesDepartment", FossConstants.YES);
		}
		
		return super.getSqlSession().selectList(NAMESPACE+"selectBasOrgByCode",map);
	}
	
	/**
	 * 根据当前外场code找始发部门(始发线路)
	 * @author 163580
	 * @date 2014-5-17 上午10:50:56
	 * @param orgCode 
	 * @return 返回始发线路到达orgCode的所有部门
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDeptCodeSource(String orgCode) {
		return super.getSqlSession().selectList(NAMESPACE+"queryDeptCodeSource", orgCode);
	}

	/**
	 * 根据当前外场code找到达部门(到达线路)
	 * @author 163580
	 * @date 2014-5-17 上午11:01:42
	 * @param orgCode
	 * @return 返回到达线路orgCode出发的所有的到达部门
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDeptCodeTarget(String orgCode) {
		return super.getSqlSession().selectList(NAMESPACE+"queryDeptCodeTarget", orgCode);
	}

	/**
	 * 根据当前外场code找到到达部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode出发的所有到达部门
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDeptCodeArrivalTransfer(String orgCode) {
		return super.getSqlSession().selectList(NAMESPACE+"queryDeptCodeArrivalTransfer", orgCode);
	}

	/**
	 * 根据当前外场code找到出发部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode到达的所有出发部门
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDeptCodeDepartTransfer(String orgCode) {
		return super.getSqlSession().selectList(NAMESPACE+"queryDeptCodeDepartTransfer", orgCode);
	}
	
	/**
	 * 所有驻地派送部
	 * @author 163580
	 * @date 2014-5-16 上午9:44:10
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryAlldeliveryStation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryAlldeliveryStation() {
		return super.getSqlSession().selectList(NAMESPACE + "queryAlldeliveryStation");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> selectPathDetailByOrgCodeAndRouteNo(
			String orgCode, Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException {
		Map<String, Object> map=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_3);
		
		map.put("origOrgCode", orgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		
		return super.getSqlSession().selectList(NAMESPACE+"selectPathDetailByOrgCodeAndRouteNo",map);
	}

	/** 
	 * @Title: selectPathDetailByDepartDeliveryvolume 
	 * @Description: 根据查询条件返回所有有的走货路径明细
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月14日 下午2:46:32
	 *
	 * @param map : objectiveOrgCode－>目的部门 ： 当前部门
	 * 				origOrgCode－>出发部门 ： 循环部门列表
	 * 				countStartTime－>调整出发时间 ： 周期开始时间
	 * 				countEndTime－>调整到达时间 ： 周期结束时间
	 * 				arriveOrLeave－> 抵达/离开 ： 路径明细状态
	 * 				beforeVehicleNo－> 车牌号 ： 这个字段比较特殊由于多个状态公用一个方法所以这边就没有办字段的判定符号添加到XML文件里面所以这边需要在赋值的时候需要加上判定符号
	 * 												X.E. beforeVehicleNo－> 车牌号 : !='N/A'<br>
	 * 				ifChangeTime－> 是否修改时间
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#selectPathDetailByDepartDeliveryvolume(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> selectPathDetailByDepartDeliveryvolume(
			Map<String, Object> map) throws TfrBusinessException {
		return super.getSqlSession().selectList(NAMESPACE+"selectPathDetailByDepartDeliveryvolume",map);
	}

	/** 
	 * @Title: queryStockWhitInStockByOrgCode 
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月18日 下午5:09:00
	 *
	 * @param map
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryStockWhitInStockByOrgCode(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryStockWhitInStockByOrgCode(
			Map<String, Object> map) throws TfrBusinessException {
		
		return super.getSqlSession().selectList(NAMESPACE+"queryStockWhitInStockByOrgCode",map);
	}

	/** 
	 * @Title: queryPathDetailByArrive 
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月22日 上午9:07:40
	 *
	 * @param map
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryPathDetailByArrive(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryPathDetailByArrive(
			Map<String, Object> map) throws TfrBusinessException {
		return super.getSqlSession().selectList(NAMESPACE+"queryPathDetailByArrive",map);
	}

	/**
	 * 查询在库货
	 * @author 163580
	 * @date 2014-5-9 下午4:33:22
	 * @param inventoryMap
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryStockWhitInStock(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryStockWhitInStock(
			Map<String, Object> inventoryMap) {
		return super.getSqlSession().selectList(NAMESPACE+"queryStockWhitInStock", inventoryMap);
	}

	/**
	 * @deprecated
	 * 开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:55:11
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param allTransfreCenterCodes
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryBillingUnHandover(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandover(
			String orgCode, String relevantOrgCode,
			List<String> allTransfreCenterCodes, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("allTransfreCenterCodes", allTransfreCenterCodes);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandover", map);
	}

	/**
	 * 派送货量-开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:55:11
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryBillingUnHandoverTransit(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverTransit(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE + "queryBillingUnHandoverTransit", map);
	}

	/**
	 * 到达中转-开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:55:11
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryBillingUnHandoverArrival(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverArrival(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverArrival", map);
	}

	/**
	 * @deprecated
	 * 在途
	 * @author 163580
	 * @date 2014-5-15 下午5:12:51
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param allTransfreCenterCodes
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryOntheway(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOntheway(String orgCode,
			String relevantOrgCode, List<String> allTransfreCenterCodes,
			Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("allTransfreCenterCodes", allTransfreCenterCodes);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOntheway", map);
	}

	/**
	 * 派送货量-在途
	 * @author 163580
	 * @date 2014-5-15 下午5:12:51
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryOnthewayTransit(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayTransit(String orgCode,
			String relevantOrgCode,Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE + "queryOnthewayTransit", map);
	}

	/**
	 * 到达中转-在途
	 * @author 163580
	 * @date 2014-5-15 下午5:12:51
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryOnthewayArrival(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayArrival(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE + "queryOnthewayArrival", map);
	}

	/**
	 * 本地出发-开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:55:11
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryBillingUnHandoverLocal(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverLocal(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverLocal", map);
	}

	/**
	 * 本地出发-在途
	 * @author 163580
	 * @date 2014-5-15 下午5:12:51
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryOntheway(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayLocal(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOnthewayLocal", map);
	}
	
	/**
	 * 二次中转-开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:55:11
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryBillingUnHandoverSecond(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverSecond(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverSecond", map);
	}
	
	/**
	 * 二次中转-在途
	 * @author 163580
	 * @date 2014-5-15 下午5:12:51
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao#queryOnthewaySecond(java.lang.String, java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewaySecond(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOnthewaySecond", map);
	}


	/**
	 * 集中接货-开单未交接
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param salesDepartmentCodeList (orgCode所辐射的营业部)
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverPick(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverPick", map);
	}

	/**
	 * 集中接货-在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode 到达部门
	 * @param relevantOrgCode 出发部门
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayPick(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOnthewayPick", map);
	}

	/**
	 * 长途到达-开单未交接
	 * @author 163580
	 * @date 2014-5-19 下午2:00:26
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverLongReach(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverLongReach", map);
	}

	/**
	 * 长途到达-在途
	 * @author 163580
	 * @date 2014-5-19 下午2:02:32
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayPickLongReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOnthewayPickLongReach", map);
	}
	
	/**
	 * 短途到达-开单未交接
	 * @author 163580
	 * @date 2014-5-19 下午2:00:26
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryBillingUnHandoverShortReach(
			String orgCode, String relevantOrgCode, Date forecastStartTime,
			Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryBillingUnHandoverShortReach", map);
	}

	/**
	 * 短途到达-在途
	 * @author 163580
	 * @date 2014-5-19 下午2:02:32
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryOnthewayPickShortReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_5);
		map.put("orgCode", orgCode);
		map.put("relevantOrgCode", relevantOrgCode);
		map.put("countStartTime", forecastStartTime);
		map.put("countEndTime", forecastEndTime);
		return super.getSqlSession().selectList(NAMESPACE+"queryOnthewayPickShortReach", map);
	}
}
