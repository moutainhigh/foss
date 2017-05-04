package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;


/**
 * 点单Dao
 * @author chenlei
 * @date 2015-12-12 上午9:22:31
 */
public interface IOrderTaskDao {
	//根据交接单号获取交接单信息list
	List<OtHandOverBillDetailEntity> queryHandOverBillInfoByNo(String handoverNo);

	/**
	 * 新增卸车任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	int addOrderTaskBasicInfo(OrderTaskEntity baseEntity);

	/**
	 * 新增点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	int addOrderTaskBillDetailList(
			List<OtHandOverBillDetailEntity> billDetailList);

	/**
	 * 新增点单任务流水
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	int addOrderTaskSerialNoDetailList(
			List<OrderSerialNoDetailEntity> serialNoDetailList);
	
	/**
	 * 查询点单任务(分页)
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25
	 */
	List<OrderTaskDto> queryOrderTask(OrderTaskDto orderTaskDto, int limit,
			int start);

	/**
	 * 查询点单任务总数
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25 
	 */
	Long getTotCount(OrderTaskDto orderTaskDto);


	//根据交接单号获取点单任务信息
	List<OtHandOverBillDetailEntity> queryOrderTaskByHandoverNo(String handoverNo);

	/**
	 * 根据点单任务号获取任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26 
	 */
	OrderTaskEntity queryOrderTaskBaseInfoByNo(String orderTaskNo);

	/**
	 * 根据点单任务号获取任务明细基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26 
	 */
	List<OtHandOverBillDetailEntity> queryOrderTaskBillDetailListByNo(
			String orderTaskNo);

	/**
	 * 传入交接单编号和运单号，获取流水号信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	List<OrderSerialNoDetailEntity> queryOrderTaskSerialNoListByBillNo(
			String id);

	/**
	 * 添加多货时，输入的运单号获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-30
	 */
	List<QuerySerialInfoBywaybillNoDto> queryValidateWaybillNoAndSerialNo(
			String waybillNo);

	/**
	 * 根据点单任务号更新点单任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	int updateOrderTaskBasicInfo(OrderTaskEntity entity);
	
	/**
	 * 根据ID集合批量更新点单任务流水信息状态
	 * @author 272681-foss-chenlei
	 * @date 2016-1-4
	 */
	int updateOrderSerialReportTypeInfo(List<String> idList,String reportType,Date modifyTime,Date createTime);

	/**
	 * 根据ID更新点单任务明细点单件数
	 * @author 272681-foss-chenlei
	 * @date 2016-01-05
	 */
	int updateOrderTaskDetailById(String id,String orderGoodsQty);

	/**
	 * 处理点单任务界面添加多货流水
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	int insertMoreSerialNoList(List<OrderSerialNoDetailEntity> moreList);

	/**
	 * 处理点单任务界面添加多货单据明细
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	int insertMoreBillNoInfo(OtHandOverBillDetailEntity bean);

	/**
	 * 点击一键正常,获得流水信息和流水数量
	 * @author 272681-foss-chenlei
	 * @date 2016-01-10
	 */
	List<QuerySerialListByTskNumDto> queryOrderTaskSerialNo(
			String orderTaskNo);

	List<OrderTaskEntity> queryUnfinishOrderTask();

	/**
	 * 处理点单任务界面添加多货时查询是否已经添加过
	 * @author 272681-foss-chenlei
	 * @date 2016-01-26
	 */
	List<OrderSerialNoDetailEntity> queryMoreSerialNoInfoByWaybillNo(
			String waybillNo, String handoverNo, String orderTaskNo);

	List<OrderSerialNoDetailEntity> queryOrderTaskMoreSerialNoListByNo(
			String orderTaskNo, String waybillNo);

	OtHandOverBillDetailEntity queryOrderTaskDetailByNo(String orderTaskNo,
			String waybillNo);

	OrderSerialNoDetailEntity queryOrderTaskSerialNoBaseInfoByNo(
			String waybillNo, String serialNo,String orderTaskNo);

	OtHandOverBillDetailEntity queryOrderTaskDetailById(String id);
	
	/**
	 * 根据明细ID查询对应点单任务流水 数量 
	 * @creater 272681 2016-4-6 14:36:41
	 * @param orderDetailID
	 * @return
	 */
	List<QuerySerialListByTskNumDto> querySerialCountsByDetailID(List<String> idList);

	/**
	 * 根据多个交接单查询点单任务 
	 * @creater 272681 2016-4-28 14:36:41
	 * @param orderDetailID
	 * @return
	 */
	Long queryOrderTaskByHandoverNoStrs(String handOverNoStrs);

}
