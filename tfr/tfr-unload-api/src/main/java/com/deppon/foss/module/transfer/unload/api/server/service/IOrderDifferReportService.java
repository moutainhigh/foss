package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.OrderDifferReportVo;

public interface IOrderDifferReportService {
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
	 * @date 2015-12-31 下午5:18:55
	 * @param orderDifferReportEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<OrderDifferReportDto> qureyOrderDifferReport(OrderDifferReportEntity orderDifferReportEntity,int  limit,int  start);
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询 总数</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午5:22:45
	 * @param orderDifferReportEntity
	 * @return
	 * @see
	 */
	Long qureyOrderDifferReportCount(OrderDifferReportEntity orderDifferReportEntity);
	/**
	 * 
	 * <p>根据报告编号  查询 点单差异明细（运单）</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:29:10
	 * @param reportNo
	 * @return
	 * @see
	 */
	List<OrderReportDetailDto> queryOrderReportDetailByNo(String reportNo);
	/**
	 * 
	 * <p>根据交接单号和运单号查询 差异流水明细</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:53:18
	 * @param waybillNo
	 * @param handoverNo
	 * @return
	 * @see
	 */
	List<OrderReportSerialNoDto> querySerialNoListByHandOverBillNoAndWaybillNo(String waybillNo, String handoverNo,String id);
	/**
	 * 
	 * <p>根据运单信息处理 点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-10 下午5:29:26
	 * @param orderDifferReportVo
	 * @see
	 */
	void handleOrderDifferReportByWaybillNo(OrderDifferReportVo orderDifferReportVo);
	/**
	 * 
	 * <p>根据流水 处理差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-10 下午5:29:51
	 * @param orderDifferReportVo
	 * @see
	 */
	void handleOrderDifferReportBySerialNo(OrderDifferReportVo orderDifferReportVo);
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
	 * @date 2016-1-14 下午2:20:17
	 * @param detailId
	 * @return
	 * @see
	 */
	List<OrderReportSerialNoDto> querySerialNoListByDetailId(String detailId);
	/**
	 * 
	 * <p>生成差异报告 定时任务</p> 
	 * @author 189284 
	 * @date 2016-1-19 上午10:31:31
	 * @param bizJobStartTime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @see
	 */
	void addOrederReport(Date bizJobStartTime, Date bizJobEndTime,int threadNo, int threadCount);
	/**
	 * 
	 * <p>根据报告编号 查询差报告 </p> 
	 * @author 189284 
	 * @date 2016-1-26 下午6:09:31
	 * @param reportNo
	 * @return
	 * @see
	 */
	OrderDifferReportDto queryOrderReportByReportNo(String reportNo);
}
