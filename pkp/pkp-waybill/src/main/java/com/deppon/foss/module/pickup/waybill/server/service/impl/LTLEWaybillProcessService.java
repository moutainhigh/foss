package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;

public class LTLEWaybillProcessService implements ILTLEWaybillProcessService {

	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillProcessService.class);
	
	/**
	 * 零担电子面单激活处理DAO
	 */
	private IWaybillProcessDao waybillProcessDao;
	
	/**
	 * OMS订单DAO
	 */
	private IOmsOrderDao omsOrderDao;
	
	@Override
	public List<WaybillProcessEntity> queryWaybillProcessListByJobId(String jobId) {
		return waybillProcessDao.queryWaybillProcessListByJobId(jobId);
	}

	@Override
	public int updateForJob(Map<String, Object> map) {
		return waybillProcessDao.updateForJob(map);
	}
	
	@Override
	public int updateWaybillProcessByJobId(Map<String, Object> map){
		return waybillProcessDao.updateWaybillProcessByJobId(map);
	}
	
	/**
	 * @description 添加激活线程属性默认值SecondKey="Y",Active="Y",JobId="N/A",ProcessCount= 1;
	 * 线程入口只有一个程序
	 */
	public int addWaybillProcessEntity(String waybillNo,String orderNo,String processType){
		int result = 0;
		WaybillProcessEntity newEntity = new WaybillProcessEntity();
		try{
			LOGGER.info("零担电子运单添加WaybillProcess,ProcessType:"+processType+",订单号:"+orderNo+",运单号:"+waybillNo);
			if(StringUtils.isEmpty(waybillNo)||StringUtils.isEmpty(orderNo)){
				return result;
			}
			Date time = new Date();
			newEntity.setId(UUIDUtils.getUUID());
			newEntity.setOrderNo(orderNo);
			newEntity.setWaybillNo(waybillNo);
			newEntity.setSecondKey(WaybillConstants.YES);
			newEntity.setActive(WaybillConstants.YES);
			newEntity.setJobId(WaybillConstants.UNKNOWN);
			newEntity.setCreateTime(time);
			newEntity.setModifyTime(time);
			newEntity.setProcessCount(1);
			newEntity.setProcessType(processType);
			result = waybillProcessDao.addWaybillProcessEntity(newEntity);
			return result;
		}catch(Exception e){
			LOGGER.error("零担电子运单,待补录,开单异常信息：" + e.getMessage());
		}
		OmsOrderEntity omsOrderEntity = omsOrderDao.queryOmsOrderByOrderNo(orderNo);
		/**
		 * 如果已经开单,则直接返回0
		 */
		if(omsOrderEntity!=null&&StringUtils.equals(omsOrderEntity.getWaybillStatus(),WaybillConstants.PDA_ACTIVE))
		{
			return 0;
		}
		
		/**
		 * 当插入WaybillProcess值为0的时候,
		 * 查询实体对象
		 */
		WaybillProcessEntity entity = null;
		if(result==0){
			entity = waybillProcessDao.queryNotFinishedByWaybillNo(waybillNo);
		}
		/**
		 * entity有可能在上一次插入值和查询的过程中已经修改过了,导致查询不到
		 * 所以进行判断是否为null
		 */
		boolean handle = false;
		if(entity!=null){
			/**
			 * 当不为空时候,比较记录创建的时间往前+1小时，
			 * 当如果大于当前时间,不处理。
			 * 当小于当前时间时候，也就是线程记录加载时间已经超过1小时，现强行将线程数据进行解锁
			 */
			handle = false;
			Date lastTime = DateUtils.addHours(entity.getCreateTime(), 1);
			Date now = new Date();
			if(lastTime.compareTo(now)<=0){
				handle = true;
			}
		}
		/**
		 * 是否解锁线程数据
		 */
		if(handle){
			/**
			 * 根据ID+SecondKey（Y）进行更新,这样通过数据库行锁解决并发问题。
			 * 并发第一个之后的更新记录数都将是0条.当是0条时候将不插入数据
			 */
			int updateNum = waybillProcessDao.unlockedById(entity.getId());
			try{
				if(updateNum>0){
					result = waybillProcessDao.addWaybillProcessEntity(newEntity);
				}
			}catch(Exception e){
				LOGGER.error("零担电子运单插入运单处理程序出现并发异常："+e.getMessage(),e);
			}
		}
		return result;
	}
	
	/**
	 * 当激活失败时候调用此方法去掉激活线程的唯一性
	 * @param waybillNo 运单号
	 * @param newSecondKey 和运单号为组合主键，可以将JOBID作为新的secondKey（添加激活线程时secondKey默认值为Y）
	 */
	public int updateWaybillProcessSecondKey(String waybillNo,String newSecondKey){
		return waybillProcessDao.updateWaybillProcessSecondKey(waybillNo, newSecondKey);
	}

	public void setWaybillProcessDao(IWaybillProcessDao waybillProcessDao) {
		this.waybillProcessDao = waybillProcessDao;
	}

	public void setOmsOrderDao(IOmsOrderDao omsOrderDao) {
		this.omsOrderDao = omsOrderDao;
	}
	
}
