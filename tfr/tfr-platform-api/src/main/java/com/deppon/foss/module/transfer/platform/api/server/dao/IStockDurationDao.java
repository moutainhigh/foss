package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationDispatch;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationTransfer;
import com.deppon.foss.module.transfer.platform.api.shared.dto.Dates4StockDurationDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.KeyValueDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;

public interface IStockDurationDao {

	/**
	 * @desc 查询提货方式
	 * @return
	 * @date 2015年4月10日 上午11:36:03
	 * @author Ouyang
	 */
	List<KeyValueDto> findReceiveMethod();

	/**
	 * @desc 查询分组外场，及是否含待叉区
	 * @param threadCount
	 * @param threadNo
	 * @return map TFR_CTR_CODE外场编码,TFR_CTR_NAME外场名称，HAS_FORK_AREA是否含有待叉区 Y/N
	 * @date 2015年3月24日 上午9:40:49
	 * @author Ouyang
	 */
	List<Map<String, String>> findTfrCtrs(Integer threadCount, Integer threadNo);

	/************** 中转部分 *******************/
	/**
	 * @desc 查询中转装车数据,快递
	 * @param tfrCtrCode
	 * @param staDate
	 * @param endDate
	 * @return
	 * @date 2015年3月24日 上午11:10:42
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrLoadExp(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth);

	/**
	 * @desc 查询中转装车数据，零担
	 * @param tfrCtrCode
	 * @param staDate
	 * @param endDate
	 * @return
	 * @date 2015年3月24日 上午11:10:42
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrLoadLtc(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth);

	/**
	 * @desc 查询下一部门
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return map NEXT_ORG_CODE下一部门编码，NEXT_ORG_NAME下一部门名称
	 * @date 2015年3月24日 上午11:16:02
	 * @author Ouyang
	 */
	Map<String, String> findNextOrg(Map<String, String> map);

	/**
	 * @desc 查询到达时间
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return
	 * @date 2015年3月24日 上午11:18:29
	 * @author Ouyang
	 */
	Date findArrivedTime(Map<String, String> map);

	/**
	 * @desc 查询卸车开始和卸车扫描时间
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return 卸车任务开始时间,卸车扫描时间
	 * @date 2015年3月24日 上午11:23:55
	 * @author Ouyang
	 */
	Dates4StockDurationDto findUnloadTaskBeginAndScanTime(Map<String, String> map);

	/**
	 * @desc 查询托盘绑定时间和叉车扫描时间
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return 托盘绑定时间,叉车扫描时间
	 * @date 2015年3月24日 上午11:27:12
	 * @author Ouyang
	 */
	Dates4StockDurationDto findTrayBindingAndForkliftScanTime(
			Map<String, String> map);

	/**
	 * @desc 查询出包装库区时间
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return
	 * @date 2015年3月24日 上午11:29:54
	 * @author Ouyang
	 */
	Date findOutPkgAreaTime(Map<String, String> map);

	/**
	 * @desc 查询入包装库区时间
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return
	 * @date 2015年3月24日 上午11:29:54
	 * @author Ouyang
	 */
	Date findInPkgAreaTime(Map<String, String> map);

	/************** 派送部分 *******************/
	/**
	 * @desc 查询快递派送装车数据
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @return
	 * @date 2015年3月25日 上午9:23:09
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptLoadExp(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth);

	/**
	 * @desc 查询零担派送装车数据
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @return
	 * @date 2015年3月25日 上午9:23:09
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptLoadLtc(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth);

	/**
	 * @desc 查询上一部门
	 * @param map
	 *            tfrCtrCode外场编码，waybillNo运单号
	 * @return map PRE_ORG_CODE下一部门编码，PRE_ORG_NAME下一部门名称
	 * @date 2015年3月24日 上午11:16:02
	 * @author Ouyang
	 */
	Map<String, String> findPreOrg(Map<String, String> map);

	/**
	 * @desc 查询运单签收时间
	 * @param waybillNo
	 * @return
	 * @date 2015年3月25日 下午4:23:07
	 * @author Ouyang
	 */
	Date findSignTime(String waybillNo);

	/************** 汇总时各种时间查询 ，快递部分 *******************/
	/**
	 * @desc 查询卸车等待时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findUnloadWaitTimeExp(Map<String, Object> map);

	/**
	 * @desc 查询卸车时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findUnloadTimeExp(Map<String, Object> map);

	/**
	 * @desc 查询待叉区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findForkAreaStayTimeExp(Map<String, Object> map);

	/**
	 * @desc 查询包装库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findPkgAreaStayTimeExp(Map<String, Object> map);

	/**
	 * @desc 查询中转库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findTfrAreaTimeExp(Map<String, Object> map);

	/**
	 * @desc 查询派送库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findDptAreaTimeExp(Map<String, Object> map);

	/************** 汇总时各种时间查询 ，快递部分 *******************/
	/**
	 * @desc 查询卸车等待时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findUnloadWaitTimeLtc(Map<String, Object> map);

	/**
	 * @desc 查询卸车时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findUnloadTimeLtc(Map<String, Object> map);

	/**
	 * @desc 查询待叉区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findForkAreaStayTimeLtc(Map<String, Object> map);

	/**
	 * @desc 查询包装库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findPkgAreaStayTimeLtc(Map<String, Object> map);

	/**
	 * @desc 查询中转库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findTfrAreaTimeLtc(Map<String, Object> map);

	/**
	 * @desc 查询派送库区停留时长
	 * @param map
	 *            tfrCtrCode外场编码 staDate统计时间
	 * @return
	 * @date 2015年3月25日 下午6:50:28
	 * @author Ouyang
	 */
	Map<String, BigDecimal> findDptAreaTimeLtc(Map<String, Object> map);

	/************** insert *******************/
	/**
	 * @desc 新增快递中转数据
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertTfrExp(StockDurationTransfer info);

	/**
	 * @desc 新增零担中转数据
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertTfrLtc(StockDurationTransfer info);

	/**
	 * @desc 新增快递派送数据
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertDptExp(StockDurationDispatch info);

	/**
	 * @desc 新增零担派送数据
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertDptLtc(StockDurationDispatch info);

	/**
	 * @desc 新增快递数据汇总
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertExpDay(StockDuration info);

	/**
	 * @desc 新增零担数据汇总
	 * @param info
	 * @date 2015年3月24日 下午4:06:37
	 * @author Ouyang
	 */
	void insertLtcDay(StockDuration info);

	/**
	 * @desc 快递数据月汇总
	 * @param tfrCtrCode
	 * @param staDate
	 * @param staMonth
	 * @date 2015年3月26日 上午11:40:02
	 * @author Ouyang
	 */
	void insertExpMonth(String tfrCtrCode, Date staDate, int staMonth);

	/**
	 * @desc 零担数据月汇总
	 * @param tfrCtrCode
	 * @param staDate
	 * @param staMonth
	 * @date 2015年3月26日 上午11:40:02
	 * @author Ouyang
	 */
	void insertLtcMonth(String tfrCtrCode, Date staDate, int staMonth);

	/**
	 * @desc 查询快递数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:06
	 * @author Ouyang
	 */
	List<StockDuration> findExpDay(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:09
	 * @author Ouyang
	 */
	List<StockDuration> findExpMonth(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:11
	 * @author Ouyang
	 */
	List<StockDuration> findLtcDay(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:14
	 * @author Ouyang
	 */
	List<StockDuration> findLtcMonth(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrExp(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询零担中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationTransfer> findTfrLtc(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询快递派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptExp(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询零担派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	List<StockDurationDispatch> findDptLtc(StockDurationQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询快递中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findTfrExpCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findTfrLtcCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询快递派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findDptExpCnt(StockDurationQcDto parameter);

	/**
	 * @desc 查询零担派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	Long findDptLtcCnt(StockDurationQcDto parameter);
}
