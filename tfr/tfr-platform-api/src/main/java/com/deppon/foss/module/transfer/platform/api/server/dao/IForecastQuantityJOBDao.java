package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;

/**
 * 货量预测的job专用dao 所有与货量预测有关系，却又涉及到别的模块表的 单独接口可以加在次DAO里面
 * 
 * @author yu - 134019
 * @date 2014-03-06 15:34:14
 */
public interface IForecastQuantityJOBDao {
	/**
	 * 
	 * @param threadNo
	 *            线程号
	 * @param threadCount
	 *            线程数
	 * @param isTransferCenter
	 *            规定 ： 如果此字段为TRUE则是外场，如果此字段为FALSE则表示查出来的是营业部的列表
	 * @return 获取所有的外场编码
	 * 
	 * @author yuyongxiang-134019
	 * @date 2014-03-06 14:52:29
	 */
	List<String> selectBasOrgByCode(String threadNo, String threadCount,
			boolean isTransferCenter) throws TfrBusinessException;

	/**
	 * 
	 * @param orgCode
	 *            外场code
	 * @param forecastStartTime
	 *            周期开始时间
	 * @param forecastEndTime
	 *            周期结束时间
	 * @return 返回当前周期内所有集中开单的货物明细
	 * @throws TfrBusinessException
	 * 
	 * @author yuyongxiang - 134019
	 * @date 2014-03-07 18:38:50
	 * 
	 */
	List<PathDetailEntity> selectPathDetailByOrgCodeAndRouteNo(String orgCode,
			Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException;
	/**
	 * 
	 * @Title: selectPathDetailByDepartDeliveryvolume 
	 * @Description: 根据查询条件返回所有有的走货路径明细
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月14日 下午2:44:18 
	 * @param map : objectiveOrgCode－>目的部门 ： 当前部门<br>
	 * 				origOrgCode－>出发部门 ： 循环部门列表<br>
	 * 				countStartTime－>调整出发时间 ： 周期开始时间<br>
	 * 				countEndTime－>调整到达时间 ： 周期结束时间<br>
	 * 				arriveOrLeave－> 抵达/离开 ： 路径明细状态<br>
	 * 				beforeVehicleNo－> 车牌号 ： 这个字段比较特殊由于多个状态公用一个方法所以这边就没有办字段的判定符号添加到XML文件里面所以这边需要在赋值的时候需要加上判定符号
	 * 												X.E. beforeVehicleNo－> 车牌号 : !='N/A'<br>
	 * 				ifChangeTime－> 是否修改时间
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	List<PathDetailEntity> selectPathDetailByDepartDeliveryvolume(Map<String,Object> map)
					throws TfrBusinessException;
	/**
	 * 
	 * @Title: queryStockWhitInStockByOrgCode 
	 * @Description: 查询当前外场的在库的派送货区的货量
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月18日 下午5:07:06 
	 * @param map
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	List<PathDetailEntity> queryStockWhitInStockByOrgCode(Map<String,Object> map)
			throws TfrBusinessException;

	/**
	 * 
	 * @Title: queryPathDetailByArrive 
	 * @Description: TODO
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月22日 上午9:06:32 
	 * @param map
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	List<PathDetailEntity> queryPathDetailByArrive(Map<String,Object> map)
			throws TfrBusinessException;

	/**
	 * 查询出发在库
	 * @author 163580
	 * @date 2014-5-9 下午4:27:53
	 * @param inventoryMap
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryStockWhitInStock(
			Map<String, Object> inventoryMap);

	/**
	 * 开单未交接的货物
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param allTransfreCenterCodes
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandover(String orgCode,
			String relevantOrgCode, List<String> allTransfreCenterCodes,
			Date forecastStartTime, Date forecastEndTime);

	/**
	 * 派送货量-开单未交接的货物
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param allTransfreCenterCodes
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandoverTransit(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

	/**
	 * 到达中转-开单未交接的货物
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandoverArrival(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

	/**
	 * 在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param allTransfreCenterCodes
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOntheway(String orgCode,
			String relevantOrgCode, List<String> allTransfreCenterCodes,
			Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 派送货量-在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOnthewayTransit(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

	/**
	 * 到达中转-在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOnthewayArrival(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

	/**
	 * 计算本地出发货物-开单未交接的货物
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandoverLocal(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 计算本地出发货物-在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOnthewayLocal(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 计算二次中转货物-开单未交接的货物
	 * @author 163580
	 * @date 2014-5-15 下午4:17:36
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandoverSecond(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 二次中转-在途的货物
	 * @author 163580
	 * @date 2014-5-15 下午5:12:17
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOnthewaySecond(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
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
	List<PathDetailEntity> queryBillingUnHandoverLongReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
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
	List<PathDetailEntity> queryOnthewayPickLongReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 短途到达-开单未交接
	 * @author 163580
	 * @date 2014-5-19 下午2:22:58
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryBillingUnHandoverShortReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
	/**
	 * 短途到达-在途
	 * @author 163580
	 * @date 2014-5-19 下午2:22:58
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @see
	 */
	List<PathDetailEntity> queryOnthewayPickShortReach(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

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
	List<PathDetailEntity> queryBillingUnHandoverPick(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);
	
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
	List<PathDetailEntity> queryOnthewayPick(String orgCode,
			String relevantOrgCode, Date forecastStartTime, Date forecastEndTime);

	/**
	 * 所有驻地派送部
	 * @author 163580
	 * @date 2014-5-16 上午9:43:28
	 * @return
	 * @see
	 */
	List<String> queryAlldeliveryStation();

	/**
	 * 根据当前外场code找始发部门(始发线路)
	 * @author 163580
	 * @date 2014-5-17 上午10:50:56
	 * @param orgCode 
	 * @return 返回始发线路到达orgCode的所有部门
	 * @see
	 */
	List<String> queryDeptCodeSource(String orgCode);

	/**
	 * 根据当前外场code找到达部门(到达线路)
	 * @author 163580
	 * @date 2014-5-17 上午11:01:42
	 * @param orgCode
	 * @return 返回到达线路orgCode出发的所有的到达部门
	 * @see
	 */
	List<String> queryDeptCodeTarget(String orgCode);

	/**
	 * 根据当前外场code找到到达部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode出发的所有到达部门
	 * @see
	 */
	List<String> queryDeptCodeArrivalTransfer(String orgCode);

	/**
	 * 根据当前外场code找到出发部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode到达的所有出发部门
	 * @see
	 */
	List<String> queryDeptCodeDepartTransfer(String orgCode);
}
