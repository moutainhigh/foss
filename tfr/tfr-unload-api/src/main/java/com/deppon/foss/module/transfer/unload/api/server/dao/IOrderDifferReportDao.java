package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;
/**
 * @title: IOrderDifferReportDao 
 * @description: 点单任务差异报告Dao 数据接口.
 * @author: DPAP-CodeGenerator
 * @Date: 2015-12-28 19:17:03
 */
public interface IOrderDifferReportDao {
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: DPAP-CodeGenerator 
	* @date: 2015-12-28 19:17:03
	* @param entity void
	*/
	void insertOrderDifferReport(OrderDifferReportDto entity);	
	/**
	* 
	* @MethodName: updateByPrimaryKey 
	* @description: 根据主键更新
	* @author: DPAP-CodeGenerator
	* @date: 2015-12-28 19:17:03
	* @param entity void
	*/
	void updateOrderDifferReportById(OrderDifferReportEntity entity);
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询 总数</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午5:12:57
	 * @param orderDifferReportEntity
	 * @return
	 * @see
	 */
	Long qureyOrderDifferReportCount(OrderDifferReportEntity orderDifferReportEntity);
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午4:35:55
	 * @param orderDifferReportEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureyOrderDifferReport(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity)
	 */
  List<OrderDifferReportDto> qureyOrderDifferReport(OrderDifferReportEntity orderDifferReportEntity);
  /**
   * 
   * <p>差异报告查询界面 根据条件查询  分页</p> 
   * @author 189284 
   * @date 2015-12-31 下午5:26:03
   * @param orderDifferReportEntity
   * @param limit
   * @param start
   * @return
   * @see
   */
	List<OrderDifferReportDto> qureyOrderDifferReport(OrderDifferReportEntity orderDifferReportEntity, int limit,int start);
	/**
	 * 
	 * <p>根据报告编号  查询 点单差异明细（运单）</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:34:47
	 * @param reportNo
	 * @return
	 * @see
	 */
    List<OrderReportDetailDto> queryOrderReportDetailByNo(String reportNo);
    /**
     * 
     * <p>根据交接单号和运单号查询 差异流水明细</p> 
     * @author 189284 
     * @date 2016-1-5 下午4:57:20
     * @param waybillNo
     * @param handoverNo
     * @return
     * @see
     */
	List<OrderReportSerialNoDto> querySerialNoListByHandOverBillNoAndWaybillNo(String waybillNo, String handoverNo ,String id);
	/**
	 * 
	 * <p> 根据运单处理点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-11 下午3:58:56
	 * @param OrderReportSerialNoDto
	 * @see
	 */
	void handleOrderDifferReportByWaybillNo(OrderReportSerialNoDto orderReportSerialNoDto);
	/**
	 * 
	 * <p>根据流水处理点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-11 下午6:52:46
	 * @param orderReportSerialNoDto
	 * @see
	 */
	void handleOrderDifferReportBySerialNo(OrderReportSerialNoDto orderReportSerialNoDto);
	/**
	 * 
	 * <p>根据 id和报告编号查询 未处理的运单明细数</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午3:39:37
	 * @param id
	 * @return
	 * @see
	 */
	Long qureyIsHandleWayBillById(String id,String reportNo);
	/**
	 * 
	 * <p>根据 id 查询 未处理的流水明细数</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午3:56:50
	 * @param id
	 * @return
	 * @see
	 */
	Long qureyIsHandleSerialNoById(String id,String detailId);
	/**
	 * 
	 * <p>根据报告编号 修改报告状态  和处理完成时间</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午5:58:43
	 * @param orderDifferReportDto
	 * @see
	 */
	void updateOrderDifferReportStateById(OrderDifferReportDto orderDifferReportDto);
	/**
	 * 
	 * <p>根据 运单id查询 流水明细list</p> 
	 * @author 189284 
	 * @date 2016-1-14 下午2:18:41
	 * @param detailId
	 * @return
	 * @see
	 */
	List<OrderReportSerialNoDto> querySerialNoListByDetailId(String detailId);
	/**
	 * 
	 * <p>根据 运单id 跟新 运单是否处理状态</p> 
	 * @author 189284 
	 * @date 2016-1-14 下午4:51:07
	 * @param id
	 * @see
	 */
	void updateOrderReportDetailIsHandleById(String id);
	/**
	 * 
	 * <p>根据时间 查询应该生成差异报告的 点单任务</p> 
	 * @author 189284 
	 * @date 2016-1-19 下午2:22:00
	 * @param bizJobStartTime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @return
	 * @see
	 */
	List<OrderDifferReportDto> queryOrderReportByBatch( int threadNo, int threadCount);
	/**
	 * 
	 * <p>根据点单任务编号差异类型  查询 对应需要生产差异报告的 流水明细 </p> 
	 * @author 189284 
	 * @date 2016-1-20 下午6:30:57
	 * @param orderTaskNo 点单任务编号
	 * @param orderReportType 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 * @return
	 * @see
	 */
	List<OrderReportSerialNoDto> qureyOrderTaskSerialnoByNoAndType(String orderTaskNo, String orderReportType);
	/**
	 * 
	 * <p><!-- 根据单点任务编号 生成对应的点单差异报告 运单明细 --></p> 
	 * @author 189284 
	 * @date 2016-1-22 下午3:24:04
	 * @see
	 */
	void insertOrderReportDetailByNo(OrderDifferReportDto entity);
	/**
	 * 
	 * <p>新增 差异报告 流水</p> 
	 * @author 189284 
	 * @date 2016-1-22 下午5:47:06
	 * @param orderReportSerialNoDto
	 * @see
	 */
	void insertOrederReportSerialno(OrderReportSerialNoDto orderReportSerialNoDto);
	/**
	 * 
	 * <p>根据报告编号 查询差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-26 下午6:05:47
	 * @param reportNo
	 * @return
	 * @see
	 */
	OrderDifferReportDto queryOrderReportByReportNo(String reportNo);
}