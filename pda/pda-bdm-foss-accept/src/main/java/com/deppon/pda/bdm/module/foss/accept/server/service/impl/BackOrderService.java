package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaOrderTaskHandleService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BackOrderScanEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file BackOrderService.java 
 * @description 退回订单服务类
 * @author ChenLiang
 * @created 2012-12-29 下午1:42:05    
 * @version 1.0
 */
public class BackOrderService implements IBusinessService<Void, BackOrderScanEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	//数据操作类
	private IAcctDao acctDao;
	//foss接口
	private IPdaOrderTaskHandleService pdaOrderTaskHandleService;
	//订单锁接口
	private IOrderBusinessLockService orderBusinessLockService;
	//配置 
	private IConfigurationParamsService configurationParamsService;
	
	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	public void setPdaOrderTaskHandleService(IPdaOrderTaskHandleService pdaOrderTaskHandleService) {
		this.pdaOrderTaskHandleService = pdaOrderTaskHandleService;
	}

	public void setOrderBusinessLockService(IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public BackOrderScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		BackOrderScanEntity backOrderScan = JsonUtil.parseJsonToObject(BackOrderScanEntity.class, asyncMsg.getContent());
		//pda编号
		backOrderScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		backOrderScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		backOrderScan.setScanType(asyncMsg.getOperType());
		//用户编号
		backOrderScan.setScanUser(asyncMsg.getUserCode());
		backOrderScan.setUploadTime(asyncMsg.getUploadTime());
		return backOrderScan;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param backOrderScan
	 * @throws PdaBusiException 
	 * @created 2012-12-29 上午10:47:20
	 */
	private void validate(AsyncMsg asyncMsg, BackOrderScanEntity backOrderScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 判断订单返回实体
		Argument.notNull(backOrderScan, "BackOrderScanEntity");
		Argument.hasText(backOrderScan.getId(), "BackOrderScanEntity.id");
		// 扫描时间
		Argument.notNull(backOrderScan.getScanTime(), "BackOrderScanEntity.scanTiem");
		// 判断操作类型
		Argument.hasText(backOrderScan.getScanType(), "BackOrderScanEntity.scanType");
		// 判断订单编号
		Argument.hasText(backOrderScan.getOrderCode(), "BackOrderScanEntity.orderCode");
		// 扫描标识
		Argument.hasText(backOrderScan.getScanFlag(), "BackOrderScanEntity.scanFlag");
		// 订单状态
		Argument.hasText(backOrderScan.getOrderStatus(), "BackOrderScanEntity.orderStatus");
		// 订单类型
		Argument.hasText(backOrderScan.getOrderType(), "BackOrderScanEntity.orderType");
		// 车牌号
		Argument.hasText(backOrderScan.getTruckCode(), "BackOrderScanEntity.truckCode");
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param backOrderScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, BackOrderScanEntity backOrderScan) throws PdaBusiException {
		this.validate(asyncMsg, backOrderScan);
		
		backOrderScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
		
		String backReason = backOrderScan.getBackReason();
		
		//转发/退回  ORDER_RETURN 退回      ORDER_FORWARD 转发
		if(backOrderScan.getOptState()!=null && "ORDER_FORWARD".equals(backOrderScan.getOptState())){
			backOrderScan.setBackReason(backOrderScan.getOptState()+":"+backOrderScan.getForwardDriverCode()+","+backOrderScan.getForwardDriverName());
			
		}else{
			backOrderScan.setBackReason(backOrderScan.getOptState()+":"+backOrderScan.getBackReason());
		}
		
		log.debug("---保存订单已退回扫描数据开始---");
		acctDao.saveBackOrder(backOrderScan);
		log.debug("---保存订单已退回扫描数据成功---");
		
		PdaOrderDto pdaOrderDto = new PdaOrderDto();
		// 司机编码
		pdaOrderDto.setDriverCode(asyncMsg.getUserCode());
		// 订单号
		pdaOrderDto.setOrderNo(backOrderScan.getOrderCode());
		// 订单状态（退回）
		pdaOrderDto.setOrderStatus(backOrderScan.getOrderStatus());
		// 订单类型 -- 接货
		pdaOrderDto.setOrderType(backOrderScan.getOrderType());
		// 备注
		pdaOrderDto.setRemark(backOrderScan.getRemark());
		// 退回原因
		pdaOrderDto.setReturnReason(backReason);
		// 更新时间
		pdaOrderDto.setUpdateDateTime(backOrderScan.getScanTime());
		//转发工号
		pdaOrderDto.setForwardDriverCode(backOrderScan.getForwardDriverCode());
		//转发姓名
		pdaOrderDto.setForwardDriverName(backOrderScan.getForwardDriverName());
		//转发/退回
		pdaOrderDto.setOptState(backOrderScan.getOptState());
		//接货最早时间/
		pdaOrderDto.setEarliestPickupTime(backOrderScan.getEarliestPickupTime());
		// 接货最晚时间
		pdaOrderDto.setLatestPickupTime(backOrderScan.getLatestPickupTime());
		
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		
		//16-03-18 version author 298403 加了订单锁，如果拿到锁，则可以进行原来的操作，防止数据库死锁
		OrderMutexElement mutexElement = null;
		if(StringUtil.isNumeric(orderLockTtl)){
			mutexElement = new OrderMutexElement(pdaOrderDto.getOrderNo(), "PDA_退回订单接口取锁", OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
		} else {
			mutexElement = new OrderMutexElement(pdaOrderDto.getOrderNo(), "PDA_退回订单接口取锁", OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
		}
		
		boolean isLocked = false;
		try {
			isLocked = orderBusinessLockService.lock(mutexElement,DispatchOrderStatusConstants.ZERO);
			if (isLocked) {
				//拿到锁
				long startTime = System.currentTimeMillis();
				log.info("---调用FOSS退回订单接口开始---");
				//调用foss接口
				pdaOrderTaskHandleService.udpateOrder(pdaOrderDto);
				long endTime = System.currentTimeMillis();
				QueueMonitorInfo.addTotalFossTime(endTime-startTime);
				log.info("[asyncinfo]退回订单接口消耗时间:"+(endTime-startTime)+"ms");
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} finally {
			//如果拿到锁,解锁 
			if(isLocked){
				orderBusinessLockService.unlock(mutexElement);
			}
		}
		log.info("---调用FOSS退回订单接口结束---");
		return null;
	}

	
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_ORDER_BACK.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	} 
}
