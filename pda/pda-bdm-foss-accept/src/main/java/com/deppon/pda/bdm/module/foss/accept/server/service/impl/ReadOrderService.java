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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReadOrderScanEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file ReadOrderService.java 
 * @description 订单已读服务类
 * @author ChenLiang
 * @created 2012-12-31 下午12:26:22    
 * @version 1.0
 */
public class ReadOrderService implements IBusinessService<Void, ReadOrderScanEntity> {
	
	private Logger log = Logger.getLogger(getClass());

	private IPdaOrderTaskHandleService pdaOrderTaskHandleService;
	
	private IAcctDao acctDao;
	//订单锁接口
	private IOrderBusinessLockService orderBusinessLockService;
	//配置 
	private IConfigurationParamsService configurationParamsService;
	
	public void setPdaOrderTaskHandleService(IPdaOrderTaskHandleService pdaOrderTaskHandleService) {
		this.pdaOrderTaskHandleService = pdaOrderTaskHandleService;
	}

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
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
	public ReadOrderScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ReadOrderScanEntity readOrderScan = new ReadOrderScanEntity();
		//解析包体
		readOrderScan = JsonUtil.parseJsonToObject(ReadOrderScanEntity.class, asyncMsg.getContent());
		//pda编号
		readOrderScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		readOrderScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		readOrderScan.setScanType(asyncMsg.getOperType());
		//用户编号
		readOrderScan.setScanUser(asyncMsg.getUserCode());
		
		readOrderScan.setUploadTime(asyncMsg.getUploadTime());
		return readOrderScan;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param readOrderScan
	 * @throws PdaBusiException 
	 * @created 2012-12-31 下午12:59:14
	 */
	private void validate(AsyncMsg asyncMsg, ReadOrderScanEntity readOrderScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 判断订单已读实体
		Argument.notNull(readOrderScan, "ReadOrderScanEntity");
		Argument.hasText(readOrderScan.getId(), "ReadOrderScanEntity.id");
		// 判断扫描类型
		Argument.hasText(readOrderScan.getScanType(), "ReadOrderScanEntity.scanType");
		// 判断已读时间
		Argument.notNull(readOrderScan.getScanTime(), "ReadOrderScanEntity.scanTime");
		// 判断订单编号
		Argument.hasText(readOrderScan.getOrderCode(), "ReadOrderScanEntity.orderCode");
		// 扫描标识
		Argument.hasText(readOrderScan.getScanFlag(), "ReadOrderScanEntity.scanFlag");
		// 订单状态
		Argument.hasText(readOrderScan.getOrderStatus(), "RcvOrderScanEntity.orderStatus");
		// 订单类型
		Argument.hasText(readOrderScan.getOrderType(), "ReadOrderScanEntity.orderType");
		// 车牌号
		Argument.hasText(readOrderScan.getTruckCode(), "ReadOrderScanEntity.truckCode");
	}

	/**
	 * 
	 * @description 订单已读服务
	 * @param asyncMsg
	 * @param readOrderScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, ReadOrderScanEntity readOrderScan) throws PdaBusiException {
		this.validate(asyncMsg, readOrderScan);
		
		readOrderScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
		log.debug("---保存订单已读扫描数据开始---");
		acctDao.saveReadOrder(readOrderScan);
		log.debug("---保存订单已读扫描数据开始---");
		
		PdaOrderDto req = new PdaOrderDto();
		// 司机编码
		req.setDriverCode(asyncMsg.getUserCode());
		// 订单号
		req.setOrderNo(readOrderScan.getOrderCode());
		// 订单状态（已读）
		req.setOrderStatus(readOrderScan.getOrderStatus());
		// 订单类型 -- 接货
		req.setOrderType(readOrderScan.getOrderType());
		// 更新时间
		req.setUpdateDateTime(readOrderScan.getScanTime());
		
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		
		//2016-03-18 version author 298403 加了订单锁，如果拿到锁，则可以进行原来的操作，防止数据库死锁
		OrderMutexElement mutexElement = null;
		if(StringUtil.isNumeric(orderLockTtl)){
			mutexElement = new OrderMutexElement(req.getOrderNo(), "PDA_已读订单接口取锁", OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
		} else {
			mutexElement = new OrderMutexElement(req.getOrderNo(), "PDA_已读订单接口取锁", OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
		}
		
		boolean isLocked = false;
		try {
			isLocked = orderBusinessLockService.lock(mutexElement,DispatchOrderStatusConstants.ZERO);
			//获取到锁了，则进行自己的操作。
			if (isLocked) {
				log.debug("---调用FOSS已读订单接口开始---");
				long startTime = System.currentTimeMillis();
				pdaOrderTaskHandleService.udpateOrder(req);
				long endTime = System.currentTimeMillis();
				QueueMonitorInfo.addTotalFossTime(endTime-startTime);
				log.info("[asyncinfo]反馈订单已读接口消耗时间:"+(endTime-startTime)+"ms");
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} finally {
			//如果拿到锁,解锁 
			if(isLocked){
				orderBusinessLockService.unlock(mutexElement);
			}
		}
		log.debug("---调用FOSS已读订单接口结束---");
		return null;
	}
	
	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_ORDER_READ.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
