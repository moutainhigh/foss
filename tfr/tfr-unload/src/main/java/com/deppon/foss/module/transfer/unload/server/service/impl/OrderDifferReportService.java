package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderDifferReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderDifferReportService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.exception.OrderDifferReportException;
import com.deppon.foss.module.transfer.unload.api.shared.vo.OrderDifferReportVo;

import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
public class OrderDifferReportService implements IOrderDifferReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDifferReportService.class);
	/**
	 * 点单差异报告 DAo
	 */
	private IOrderDifferReportDao orderDifferReportDao;
	private ITfrCommonService tfrCommonService;

	/**
	 * @param orderDifferReportDao
	 *            the orderDifferReportDao to set
	 */
	public void setOrderDifferReportDao(
			IOrderDifferReportDao orderDifferReportDao) {
		this.orderDifferReportDao = orderDifferReportDao;
	}
	/**
	 * @param tfrCommonService the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}


	/**
	 * 
	 * <p>
	 * 差异报告查询界面 根据条件查询
	 * </p>
	 * 
	 * @author 189284
	 * @date 2015-12-31 下午4:35:55
	 * @param orderDifferReportEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureyOrderDifferReport(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity)
	 */
	@Override
	public List<OrderDifferReportDto> qureyOrderDifferReport(
			OrderDifferReportEntity orderDifferReportEntity) {
		return orderDifferReportDao.qureyOrderDifferReport(orderDifferReportEntity);
	}

	/**
	 * 
	 * <p>
	 * 差异报告查询界面 根据条件查询 分页
	 * </p>
	 * 
	 * @author 189284
	 * @date 2015-12-31 下午5:24:01
	 * @param orderDifferReportEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#qureyOrderDifferReportByPaging(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity,
	 *      int, int)
	 */
	@Override
	public List<OrderDifferReportDto> qureyOrderDifferReport(
			OrderDifferReportEntity orderDifferReportEntity, int limit,
			int start) {
		return orderDifferReportDao.qureyOrderDifferReport(orderDifferReportEntity, limit, start);
	}

	/**
	 * 
	 * <p>
	 * 差异报告查询界面 根据条件查询 总数
	 * </p>
	 * 
	 * @author 189284
	 * @date 2015-12-31 下午5:24:18
	 * @param orderDifferReportEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#qureyOrderDifferReportCount(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity)
	 */
	@Override
	public Long qureyOrderDifferReportCount(
			OrderDifferReportEntity orderDifferReportEntity) {
		return orderDifferReportDao
				.qureyOrderDifferReportCount(orderDifferReportEntity);
	}

	/**
	 * 
	 * <p>
	 * 根据报告编号 查询 点单差异明细（运单）
	 * </p>
	 * 
	 * @author 189284
	 * @date 2016-1-5 下午4:30:09
	 * @param reportNo
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#queryOrderReportDetailByNo(java.lang.String)
	 */
	@Override
	public List<OrderReportDetailDto> queryOrderReportDetailByNo(String reportNo) {
		if (StringUtils.isEmpty(reportNo)) {
			throw new OrderDifferReportException("差异报告编号不能为空！");
		}
		return orderDifferReportDao.queryOrderReportDetailByNo(reportNo);
	}

	/**
	 * 
	 * <p>
	 * 根据交接单号和运单号查询 差异流水明细
	 * </p>
	 * 
	 * @author 189284
	 * @date 2016-1-5 下午4:55:18
	 * @param waybillNo
	 * @param handoverNo
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#querySerialNoListByHandOverBillNoAndWaybillNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<OrderReportSerialNoDto> querySerialNoListByHandOverBillNoAndWaybillNo(
			String waybillNo, String handoverNo,String id) {
		if (StringUtils.isEmpty(id)) {
			throw new OrderDifferReportException("id不能为空");
		}
		return orderDifferReportDao.querySerialNoListByHandOverBillNoAndWaybillNo(waybillNo,handoverNo, id);
	}
	/**
	 * 
	 * <p>根据 运单id查询 流水明细list</p> 
	 * @author 189284 
	 * @date 2016-1-14 下午2:17:51
	 * @param detailId
	 * @return
	 * @see
	 */
	@Override
	public List<OrderReportSerialNoDto> querySerialNoListByDetailId(String detailId) {
		if (StringUtils.isEmpty(detailId)) {
			throw new OrderDifferReportException("id不能为空");
		}
		return orderDifferReportDao.querySerialNoListByDetailId(detailId);
	}
	/**
	 * 
	 * <p>
	 * 根据运单处理点单差异报告
	 * </p>
	 * 
	 * @author 189284
	 * @date 2016-1-10 下午5:30:59
	 * @param orderDifferReportVo
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#handleOrderDifferReportByWaybillNo(com.deppon.foss.module.transfer.load.api.shared.vo.OrderDifferReportVo)
	 */
	@Override
	public void handleOrderDifferReportByWaybillNo(OrderDifferReportVo orderDifferReportVo) {
		OrderReportSerialNoDto orderReportSerialNoDto=orderDifferReportVo.getOrderReportSerialNoDto();
		if(StringUtils.isEmpty(orderReportSerialNoDto.getDetailId())){
			throw new OrderDifferReportException("修改明细id为空！");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getReason())){
			throw new OrderDifferReportException("修改明细原因为空！");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getNote())){
			throw new OrderDifferReportException("修改明细备注为空！");
		}
		if(StringUtils.isEmpty(orderDifferReportVo.getReportNo())){
			throw new OrderDifferReportException("修改明细报告编号为空！");
		}
		//根据运单信息处理 点单差异报告（更新流水--处理流水）
		orderDifferReportDao.handleOrderDifferReportByWaybillNo(orderReportSerialNoDto);
		//根据运单信息处理 点单差异报告
		orderDifferReportDao.updateOrderReportDetailIsHandleById(orderReportSerialNoDto.getDetailId());
		//根据 id和报告编号查询 未处理的运单明细数（除当前id外）
		Long wayLong=orderDifferReportDao.qureyIsHandleWayBillById(orderReportSerialNoDto.getDetailId(), orderDifferReportVo.getReportNo());
		//如果为0  表示当前 报告编号下的所有 运单 和流水 都已经处理完毕   更新差异报告为 处理完毕状态
		if(wayLong==0L){
			OrderDifferReportDto orderDifferReportDto =new OrderDifferReportDto();
			orderDifferReportDto.setReportNo(orderDifferReportVo.getReportNo());
			updateOrderDifferReportStateById( orderDifferReportDto);
		}
	}
	/**
	 * 
	 * <p>
	 * 根据流水处理点单差异报告
	 * </p>
	 * 
	 * @author 189284
	 * @date 2016-1-10 下午5:31:04
	 * @param orderDifferReportVo
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#handleOrderDifferReportBySerialNo(com.deppon.foss.module.transfer.load.api.shared.vo.OrderDifferReportVo)
	 */
	@Override
	public void handleOrderDifferReportBySerialNo(OrderDifferReportVo orderDifferReportVo) {
		OrderReportSerialNoDto orderReportSerialNoDto =orderDifferReportVo.getOrderReportSerialNoDto();
		if(StringUtils.isEmpty(orderReportSerialNoDto.getId())){
			throw new OrderDifferReportException("修改明细id为空！");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getReason())){
			throw new OrderDifferReportException("修改明细原因为空！");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getNote())){
			throw new OrderDifferReportException("修改明细备注为空！");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getDetailId())){
			throw new OrderDifferReportException("修改明细对应运单id为空！");
		}
		//根据流水id  处理当前流水
		orderDifferReportDao.handleOrderDifferReportBySerialNo(orderReportSerialNoDto);
		//根据 id 查询 未处理的流水明细数（除当前流水id 外）
		Long serialLong=orderDifferReportDao.qureyIsHandleSerialNoById(orderReportSerialNoDto.getId(), orderReportSerialNoDto.getDetailId());
		//为0  表示当前运单下的流水都处理完毕  需要更新 当前运单的状态为已经处理
		if(serialLong==0){
			orderDifferReportDao.updateOrderReportDetailIsHandleById(orderReportSerialNoDto.getDetailId());
			Long wayLong=orderDifferReportDao.qureyIsHandleWayBillById(orderReportSerialNoDto.getDetailId(), orderDifferReportVo.getReportNo());
			////如果为0  表示当前 报告编号下的所有 运单 和流水 都已经处理完毕   更新差异报告为 处理完毕状态
			if(wayLong==0L){
				OrderDifferReportDto orderDifferReportDto =new OrderDifferReportDto();
				orderDifferReportDto.setReportNo(orderDifferReportVo.getReportNo());
				updateOrderDifferReportStateById(orderDifferReportDto);
			}
	    }
	}
	/**
	 * 
	 * <p>根据报告编号 修改报告状态 和处理完成时间 为完成</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午6:02:51
	 * @param orderDifferReportDto 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#updateOrderDifferReportStateById(com.deppon.foss.module.transfer.load.api.shared.dto.OrderDifferReportDto)
	 */
	@Override
	public void updateOrderDifferReportStateById(OrderDifferReportDto orderDifferReportDto) {
		orderDifferReportDao.updateOrderDifferReportStateById(orderDifferReportDto);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addOrederReport(Date bizJobStartTime, Date bizJobEndTime, int threadNo, int threadCount) {
//		查询待处理的点单任务
    	List<OrderDifferReportDto> orderDifferReportList= orderDifferReportDao.queryOrderReportByBatch( threadNo, threadCount);
//  	循环点单任务，并获取对应的点单快照列表和比对结果列表
		for(OrderDifferReportDto orderDifferReport: orderDifferReportList){
			try{
//				生成点单差异报告和对应的差异报告明细
				addOrderReport(orderDifferReport);
			}catch(Exception e){
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ORDER_REPORT.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ORDER_REPORT.getBizCode());
				jobProcessLogEntity.setExecBizId(orderDifferReport.getId());
				jobProcessLogEntity.setExecTableName("TFR.T_OPT_ORDER_TASK");
				jobProcessLogEntity.setRemark("保存差异报告及明细时出错");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
	}
	/**
	 * 
	 * <p>生成点单差异报告和对应的差异报告明细 (包括 运单明细和  流水明细)</p> 
	 * @author 189284 
	 * @date 2016-1-22 下午3:58:37
	 * @param orderDifferReport
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private  void addOrderReport( OrderDifferReportDto orderDifferReport){
		LOGGER.info("生成点单差异报告--点单任务编号"+orderDifferReport.getOrderTaskNo());
		List<OrderReportSerialNoDto> lostSerialNoList=orderDifferReportDao.qureyOrderTaskSerialnoByNoAndType(orderDifferReport.getOrderTaskNo(),"LOSE");
		List<OrderReportSerialNoDto> moreSerialNoList=orderDifferReportDao.qureyOrderTaskSerialnoByNoAndType(orderDifferReport.getOrderTaskNo(),"MORE");
			if(CollectionUtils.isNotEmpty(lostSerialNoList)){
				orderDifferReport.setLostGoodsQty(new BigDecimal(lostSerialNoList.size()));
				for (OrderReportSerialNoDto orderReportSerialNoDto : lostSerialNoList) {
					orderReportSerialNoDto.setIsHandle(FossConstants.NO);
					orderReportSerialNoDto.setId(UUIDUtils.getUUID());
					checkorderReportSerial(orderReportSerialNoDto);
					orderDifferReportDao.insertOrederReportSerialno(orderReportSerialNoDto);
				}
			}
			LOGGER.info("生成点单差异报告--流水明细完毕" );
			if(CollectionUtils.isNotEmpty(moreSerialNoList)){
				orderDifferReport.setMoreGoodsQty(new BigDecimal(moreSerialNoList.size()));
				for (OrderReportSerialNoDto orderReportSerialNoDto : moreSerialNoList) {
					orderReportSerialNoDto.setIsHandle(FossConstants.NO);
					orderReportSerialNoDto.setCreateTime(orderReportSerialNoDto.getCreateTime());
					orderReportSerialNoDto.setId(UUIDUtils.getUUID());
					checkorderReportSerial(orderReportSerialNoDto);
					orderDifferReportDao.insertOrederReportSerialno(orderReportSerialNoDto);
				}
			}
			if(CollectionUtils.isNotEmpty(lostSerialNoList)||CollectionUtils.isNotEmpty(moreSerialNoList)){
				String reportNo=tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ODRPT_NO);
				orderDifferReport.setReportNo(reportNo);
				orderDifferReportDao.insertOrderReportDetailByNo(orderDifferReport);
				LOGGER.info("生成点单差异报告--明细完毕");
				orderDifferReportDao.insertOrderDifferReport(orderDifferReport);
				LOGGER.info("生成点单差异报告--完毕"+orderDifferReport);
			}
			
	}
	/**
	 * 
	 * 检查保存的 流水信息是否正确 
	 * @author 189284 
	 * @date 2016-1-22 下午6:06:46
	 * @param orderReportSerialNoDto
	 * @see
	 */
	private void checkorderReportSerial(OrderReportSerialNoDto orderReportSerialNoDto){
		if(StringUtils.isEmpty(orderReportSerialNoDto.getDetailId())){
			throw new OrderDifferReportException("对应的运单id为空");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getWaybillNo())){
			throw new OrderDifferReportException("对应的运单号为空");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getSerialNo())){
			throw new OrderDifferReportException("对应的流水为空");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getIsHandle())){
			throw new OrderDifferReportException("对应的是否处理为空");
		}
		if(StringUtils.isEmpty(orderReportSerialNoDto.getOrderReportType())){
			//点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
			throw new OrderDifferReportException("对应的点单差异类型为空");
		}
	}
	/**
	 * 
	 * <p>根据报告编号 查询差报告</p> 
	 * @author 189284 
	 * @date 2016-1-26 下午6:10:14
	 * @param reportNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderDifferReportService#queryOrderReportByReportNo(java.lang.String)
	 */
	@Override
	public OrderDifferReportDto queryOrderReportByReportNo(String reportNo){
		return orderDifferReportDao.queryOrderReportByReportNo(reportNo);
	}
}
