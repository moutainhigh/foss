package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.RcvOrderScanEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file RvcOrderService.java 
 * @description 快递订单已接收服务类
 * @author 245955
 * @created 2016-04-19    
 * @version 1.0
 */
public class KdRvcOrderService implements IBusinessService<String, RcvOrderScanEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	private IPDAToOMSService pdaToOMSService;
	
	//private IAcctDao acctDao;

	public void setPdaToOMSService(IPDAToOMSService pdaToOMSService) {
		this.pdaToOMSService = pdaToOMSService;
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
	 * @created 2016-04-19
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
	public String service(AsyncMsg asyncMsg, RcvOrderScanEntity rcvOrderScan) throws PdaBusiException {
		this.validate(asyncMsg, rcvOrderScan);
		
		rcvOrderScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
		log.debug("----开始保存快递订单已接收扫描数据----");
		//acctDao.saveRcvOrder(rcvOrderScan);
		log.debug("----保存快递订单已接收扫描数据成功----");
		
		PdaOrderOmsDto rvcOrder = new PdaOrderOmsDto();
		// 司机编码
		//rvcOrder.setDriverCode(asyncMsg.getUserCode());
		// 订单号
		rvcOrder.setOrderNo(rcvOrderScan.getOrderCode());
		// 订单状态（接收）
		rvcOrder.setOrderStatus(rcvOrderScan.getOrderStatus());
		// 订单类型 -- 接货
		rvcOrder.setOrderType(rcvOrderScan.getOrderType());
		// 更新时间
		//rvcOrder.setUpdateDateTime(rcvOrderScan.getScanTime());
		log.debug("---调用OMS订单已接收接口开始---");
		long startTime = System.currentTimeMillis();
		pdaToOMSService.pdaRvcOrderOMS(rvcOrder);
		long endTime = System.currentTimeMillis();
		QueueMonitorInfo.addTotalFossTime(endTime - startTime);
		log.info("[asyncinfo]订单接受接口消耗时间:" + (endTime - startTime)+ "ms");
		log.debug("---调用OMS订单已接收接口结束---");
		return asyncMsg.getId();
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_ORDER_RCV.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
