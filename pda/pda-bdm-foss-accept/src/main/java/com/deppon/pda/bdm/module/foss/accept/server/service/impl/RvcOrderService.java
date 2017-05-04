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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.RcvOrderScanEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file RvcOrderService.java 
 * @description 订单已接收服务类
 * @author ChenLiang
 * @created 2012-12-31 下午2:10:17    
 * @version 1.0
 */
public class RvcOrderService implements IBusinessService<Void, RcvOrderScanEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	private IAcctDao acctDao;

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
	public RcvOrderScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		RcvOrderScanEntity rcvOrderScan = JsonUtil.parseJsonToObject(RcvOrderScanEntity.class, asyncMsg.getContent());
		//pda编号
		rcvOrderScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		rcvOrderScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		rcvOrderScan.setScanType(asyncMsg.getOperType());
		//用户编号
		rcvOrderScan.setScanUser(asyncMsg.getUserCode());
		
		rcvOrderScan.setUploadTime(asyncMsg.getUploadTime());
		return rcvOrderScan;
	}

	/**
	 * 
	 * @description 校验数据合法性
	 * @param rcvOrderScan
	 * @throws PdaBusiException 
	 * @created 2012-12-31 下午2:11:10
	 */
	private void validate(AsyncMsg asyncMsg, RcvOrderScanEntity rcvOrderScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		// 判断已接收实体
		Argument.notNull(rcvOrderScan, "RcvOrderScanEntity");
		Argument.hasText(rcvOrderScan.getId(), "RcvOrderScanEntity.id");
		// 判断扫描类型
		Argument.hasText(rcvOrderScan.getScanType(), "RcvOrderScanEntity.scanType");
		// 判断接收时间
		Argument.notNull(rcvOrderScan.getScanTime(), "RcvOrderScanEntity.scanTime");
		// 判断订单编号
		Argument.hasText(rcvOrderScan.getOrderCode(), "RcvOrderScanEntity.orderCode");
		// 扫描标识
		Argument.hasText(rcvOrderScan.getScanFlag(), "RcvOrderScanEntity.scanFlag");
		// 订单状态
		Argument.hasText(rcvOrderScan.getOrderStatus(), "RcvOrderScanEntity.orderStatus");
		// 订单类型
		Argument.hasText(rcvOrderScan.getOrderType(), "RcvOrderScanEntity.orderType");
		// 车牌号
		Argument.hasText(rcvOrderScan.getTruckCode(), "RcvOrderScanEntity.truckCode");
	}
	
	/**
	 * 
	 * @description 订单已接收服务
	 * @param asyncMsg
	 * @param rcvOrderScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, RcvOrderScanEntity rcvOrderScan) throws PdaBusiException {
		this.validate(asyncMsg, rcvOrderScan);
		
		rcvOrderScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
		log.debug("----开始保存订单已接收扫描数据----");
		acctDao.saveRcvOrder(rcvOrderScan);
		log.debug("----保存订单已接收扫描数据成功----");
		
		PdaOrderDto req = new PdaOrderDto();
		// 司机编码
		req.setDriverCode(asyncMsg.getUserCode());
		// 订单号
		req.setOrderNo(rcvOrderScan.getOrderCode());
		// 订单状态（接收）
		req.setOrderStatus(rcvOrderScan.getOrderStatus());
		// 订单类型 -- 接货
		req.setOrderType(rcvOrderScan.getOrderType());
		// 更新时间
		req.setUpdateDateTime(rcvOrderScan.getScanTime());
		
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		
		//16-03-18 version author 298403 加了订单锁，如果拿到锁，则可以进行原来的操作，防止数据库死锁
		OrderMutexElement mutexElement = null;
		if(StringUtil.isNumeric(orderLockTtl)){
			mutexElement = new OrderMutexElement(req.getOrderNo(), "PDA_接收订单接口", OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
		} else {
			mutexElement = new OrderMutexElement(req.getOrderNo(), "PDA_接收订单接口", OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
		}
		
		boolean isLocked = false;
		try {
			//互斥锁定
			isLocked = orderBusinessLockService.lock(mutexElement,DispatchOrderStatusConstants.ZERO);
			//获取到锁了，则进行自己的操作。
			if (isLocked) {
				// 获得锁
				log.debug("---调用FOSS接收订单接口开始---");
				long startTime = System.currentTimeMillis();
				pdaOrderTaskHandleService.udpateOrder(req);
				long endTime = System.currentTimeMillis();
				QueueMonitorInfo.addTotalFossTime(endTime - startTime);
				log.info("[asyncinfo]订单接受接口消耗时间:" + (endTime - startTime)+ "ms");
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} finally {
			//如果拿到锁,解锁 
			if(isLocked){
				orderBusinessLockService.unlock(mutexElement);
			}
		}
		log.debug("---调用FOSS接收订单接口结束---");
		return null;
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_ORDER_RCV.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
