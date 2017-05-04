package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.esb.crm.client.ModifyOrderLockInfoResponse;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOrderLockDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderLocksService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;

public class OrderLocksService implements IOrderLocksService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderLocksService.class);
	 
	IOrderLockDao orderLockDao;
     
	ICrmOrderService pkpcrmOrderService;
	
	public void setPkpcrmOrderService(ICrmOrderService pkpcrmOrderService) {
		this.pkpcrmOrderService = pkpcrmOrderService;
	}
	public void setOrderLockDao(IOrderLockDao orderLockDao) {
		this.orderLockDao = orderLockDao;
	}
	@Override
	public List<OrderLockResult> crmsyncorderLock(List<OrderLockEntity> records) {
		List<OrderLockResult> list =new ArrayList<OrderLockResult>();
		OrderLockResult orderLockResult=null ;
		for( OrderLockEntity record :records){
			orderLockResult= new OrderLockResult();
			if(record!=null){
				String code = record.getDeptCode();
				if(StringUtil.isEmpty(code)){
					orderLockResult.setDeptCode(code);
					orderLockResult.setFailCause("参数为空");
					orderLockResult.setResultState("0");
					orderLockDao.insertOrderLockLog(record);
				}else{
					try{
						String id =orderLockDao.queryOrderLockCodeNoCode(record);
						if(StringUtil.isEmpty(id)){
							orderLockDao.insert(record);
							orderLockResult.setDeptCode(record.getDeptCode());
							orderLockResult.setFailCause("成功");
							orderLockResult.setResultState("1");
						}else{
							orderLockDao.updateOrderLock(record);
							orderLockResult.setDeptCode(record.getDeptCode());
							orderLockResult.setFailCause("成功");
							orderLockResult.setResultState("1");
						}
					}catch(Exception e){
						LOGGER.info(e.getMessage());
					}
					
				}
			}else{
				orderLockResult.setDeptCode(null);
				orderLockResult.setFailCause("参数为空");
				orderLockResult.setResultState("0");
				orderLockDao.insertOrderLockLog(record);
			}
			list.add(orderLockResult);
		}
		
		return list;
	}
	@Override
	public OrderLockEntity queryOrderLockByDeptCode(String deptCode) {
		// TODO Auto-generated method stub
		return orderLockDao.queryOrderLockByDeptCode(deptCode);
	}
	
	/**
 	 * 
 	 * 查询锁屏信息
 	 * 
 	 * @author panguoyang
 	 * @date 2013-08-02
 	 */
	@Override
	public OrderLockEntity queryOrderLockByDeptCodeForSyn(String deptCode) {
		ModifyOrderLockInfoResponse modifyOrderLockInfoResponse=	pkpcrmOrderService.modifyOrderLockInfo(deptCode);
		if(modifyOrderLockInfoResponse==null){
			return null;
		}
		String depcode =modifyOrderLockInfoResponse.getDeptCode();
		if(StringUtil.isEmpty(depcode)){
			return null;
		}
		OrderLockEntity orderLockEntity=new OrderLockEntity();
		orderLockEntity.setDeptCode(modifyOrderLockInfoResponse.getDeptCode());
		orderLockEntity.setOrderCountOverdue((long)modifyOrderLockInfoResponse.getLockCount());
		orderLockEntity.setOrderCountUnoverdue((long)modifyOrderLockInfoResponse.getPromptCount());
	
		try{
		orderLockDao.updateOrderLock(orderLockEntity);
		}catch(Exception e){
			//不处理
		}
		return  orderLockEntity;
	}
	
	@Override
	public String queryUnifiedCode(String code) {
		 
		return orderLockDao.queryUnifiedCode(code);
	}
}
