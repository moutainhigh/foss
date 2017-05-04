package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAOrderTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.OrderConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.util.UUIDUtils;

/**
 * pda点单任务处理service.
 *
 *
 *
 * @author FOSS-272681
 * @date 2016-01-28 上午10:12:57
 */
public class PDAOrderTaskService implements IPDAOrderTaskService{
	
	private IPDAOrderTaskDao pdaOrderTaskDao;
	
	public void setPdaOrderTaskDao(IPDAOrderTaskDao pdaOrderTaskDao) {
		this.pdaOrderTaskDao = pdaOrderTaskDao;
	}
	
	private IOrderTaskDao orderTaskDao;
	public void setOrderTaskDao(IOrderTaskDao orderTaskDao) {
		this.orderTaskDao = orderTaskDao;
	}
	
	static final Logger logger = LoggerFactory.getLogger(OrderTaskService.class);
	/**
	 * pda刷新点单任务,传入当前部门，获得当前部门的点单任务编号，状态，点单人
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	public List<PDAOrderTaskEntity> refreshOrderTaskByPDA(String orgCode,String operator){
		if(orgCode != null && operator != null){
		    return pdaOrderTaskDao.refreshOrderTaskByPDA(orgCode,operator);
		}else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
		}
	}
	
	/**
	 * pda任务更新,根据点单任务编号 获得点单任务明细以及总重量，总体积，总票数，总件数
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	public PDAOrderTaskDetailEntity queryOrderTaskDetailByOrderTaskNo(String orderTaskNo){
		if(orderTaskNo != null){
			List<PDAOrderTaskBillEntity> bills = pdaOrderTaskDao.queryOrderTaskDetailByOrderTaskNo(orderTaskNo);
		      OrderTaskEntity orderTask = orderTaskDao.queryOrderTaskBaseInfoByNo(orderTaskNo);
		      PDAOrderTaskDetailEntity entity  = new PDAOrderTaskDetailEntity();
		      entity.setTotGoodsQty(orderTask.getTotGoodsQty());
		      entity.setTotVolume(orderTask.getTotVolume());
		      entity.setTotWaybillQty(orderTask.getTotWaybillQty());
		      entity.setTotWeight(orderTask.getTotWeight());
		      entity.setBills(bills);
		      return entity;
		}else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORDERTASKNO);
		}
	}
	
	/**
	 * pda获取流水明细,根据点单任务明细id
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	public List<PDAOrderSerialNoDetailEntity> queryPDAOrderTaskSerialNoListByBillNo(String id) {
		//返回查询结果
		return pdaOrderTaskDao.queryPDAOrderTaskSerialNoListByBillNo(id);
	}
	
	
	/**
	 * pda扫描点单任务,更新数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	public List<String> scanOrderTask(String orderTaskNo,String waybillNo,String serialNo,String operator,String id){
		logger.error("扫描点单任务上传开始:"+orderTaskNo);
		//查看当前的点单任务
		OrderTaskEntity orderTask = orderTaskDao.queryOrderTaskBaseInfoByNo(orderTaskNo);
		//查看当前点单任务明细
		OtHandOverBillDetailEntity orderTaskDetail = orderTaskDao.queryOrderTaskDetailByNo(orderTaskNo,waybillNo);
		//根据运单号查询运输性质，开单件数
		List<QuerySerialInfoBywaybillNoDto> serialInfoList = orderTaskDao.queryValidateWaybillNoAndSerialNo(waybillNo);
		String  transportType = "" ;
		BigDecimal  createBillQty = BigDecimal.ZERO;//开单件数
		if(serialInfoList.size() > 0){
			 transportType = serialInfoList.get(0).getTransportType();
			 String a = serialInfoList.get(0).getCreateBillQty();
			 if(a != null){
			 createBillQty = new BigDecimal(a);
			 }
		}
		if(orderTask != null && OrderConstants.ASSIGN_ORDER_TASK_STATE_IN.equals(orderTask.getOrderTaskState())){
			//根据运单和流水号查询，是否存在点单任务流水明细中
			OrderSerialNoDetailEntity serialNoEntity = orderTaskDao.queryOrderTaskSerialNoBaseInfoByNo(waybillNo,serialNo,orderTaskNo);
			List<OrderSerialNoDetailEntity> moreList = new ArrayList<OrderSerialNoDetailEntity>();
			if(serialNoEntity == null){//多货
				OtHandOverBillDetailEntity moreBillNo = new OtHandOverBillDetailEntity();
					if(orderTaskDetail == null){//点单任务明细中没该运单号,插入运单明细
					moreBillNo.setId(UUIDUtils.getUUID());
					moreBillNo.setOrderTaskNo(orderTaskNo);
					moreBillNo.setWaybillNo(waybillNo);
					moreBillNo.setTransportType(transportType);
					moreBillNo.setCreateBillQty(createBillQty);
					moreBillNo.setOrderGoodsQty(new BigDecimal(1));//多货点单件数为1
					moreBillNo.setModifyDate(new Date());
					moreBillNo.setCreateDate(new Date());
					moreBillNo.setCreateUserCode(operator);
					//插入多货点单任务明细
					orderTaskDao.insertMoreBillNoInfo(moreBillNo);
					//更新点单任务表，点单任务总票数加1
					long totWaybillQty = orderTask.getTotWaybillQty()+1;
					OrderTaskEntity orderEntity = new OrderTaskEntity();
					orderEntity.setTotWaybillQty(totWaybillQty);
					orderEntity.setOrderTaskNo(orderTaskNo);
					//更新点单任务中的总票数
					orderTaskDao.updateOrderTaskBasicInfo(orderEntity);
				}else{//如果存在该运单，就更新点单任务明细中的点单件数
					String orderTaskDetailId = orderTaskDetail.getId();
					moreBillNo.setId(orderTaskDetailId);
					String orderGoodsQty =(orderTaskDetail.getOrderGoodsQty().longValue()+1)+"";
					orderTaskDao.updateOrderTaskDetailById(orderTaskDetailId,orderGoodsQty);	
				}
				
				//不存在点单任务流水明细中则是多货，将多货流水插入点单任务流水明细表中
				OrderSerialNoDetailEntity entity = new OrderSerialNoDetailEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setWaybillNo(waybillNo);
				entity.setSerialNo(serialNo);
				entity.setOrderTaskDetailId(moreBillNo.getId());//点单任务明细id 
				entity.setOrderReportType(OrderConstants.ORDER_REPORT_TYPE_MORE);
				moreList.add(entity);
				//插入多货流水信息
				orderTaskDao.insertMoreSerialNoList(moreList);
				
			}else if(OrderConstants.ORDER_REPORT_TYPE_LOSE.equals(serialNoEntity.getOrderReportType())){
				//正常，更新点单任务流水表状态为正常
				List<String> idList = new ArrayList<String>();
				idList.add(serialNoEntity.getId());
				orderTaskDao.updateOrderSerialReportTypeInfo(idList,OrderConstants.ORDER_REPORT_TYPE_NORMAL,new Date(),null);
				//更新点单任务明细表的点单件数
				String serialNoId = serialNoEntity.getOrderTaskDetailId();
				//根据点单任务明细id 查询点单任务明细
				OtHandOverBillDetailEntity billDetail = orderTaskDao.queryOrderTaskDetailById(serialNoId);
				String orderGoodsQty = (billDetail.getOrderGoodsQty().longValue()+1)+"";
				orderTaskDao.updateOrderTaskDetailById(serialNoId,orderGoodsQty);
			}
		}else{
			logger.error(orderTaskNo,TransferPDAExceptionCode.EXCEPTION_ORDERTASK_NOT_EXISTS);
			logger.error("扫描上传点单任务结束"+orderTaskNo);
		}
		List<String> list = new ArrayList<String>();
		list.add(id);
		list.add(orderTask == null ? "" : orderTask.getOrderTaskState());
		return list;
	}
	
	/**
	 * pda提交点单任务,更新点单任务表
	 * @author 272681-foss-chenlei
	 * @date 2016-01-26
	 */
	public String finishPdaOrderTask(String orderTaskNo,String operator){
		logger.error("提交点单任务开始:"+orderTaskNo);
		//查看当前的点单任务
		OrderTaskEntity orderTask = orderTaskDao.queryOrderTaskBaseInfoByNo(orderTaskNo);
		if(orderTask != null){
			if(OrderConstants.ASSIGN_ORDER_TASK_STATE_IN.equals(orderTask.getOrderTaskState())){
			OrderTaskEntity entity = new OrderTaskEntity();
			entity.setOrderTaskNo(orderTaskNo);
			entity.setOrderTaskState(OrderConstants.ASSIGN_ORDER_TASK_STATE_END);
			entity.setOrderEndTime(new Date());
			entity.setModifyTime(new Date());
			entity.setModifyUser(operator);
			orderTaskDao.updateOrderTaskBasicInfo(entity);
			logger.error("提交点单任务结束"+orderTaskNo);
			    return entity.getOrderTaskState();
			}else{
				//点单任务已提交
				return OrderConstants.ORDER_TASK_ARLEADLY_ALREADY;
			}
		}else{
			logger.error("提交点单任务结束"+orderTaskNo);
			//点单任务不存在
			return OrderConstants.ORDER_TASK_NO_EXIST;
		}
	}

}
