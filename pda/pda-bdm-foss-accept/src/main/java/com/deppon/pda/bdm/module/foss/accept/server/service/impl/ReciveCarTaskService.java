package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReceiveCarTaskEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file HandleOrderService.java 
 * @description 接收约车任务（转货完成）
 * @author ChenLiang
 * @created 2013-1-8 下午2:20:38    
 * @version 1.0
 */
public class ReciveCarTaskService implements IBusinessService<Void, ReceiveCarTaskEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	
	private IAcctDao acctDao;
	
	private IPdaOrderTaskHandleService pdaOrderTaskHandleService;

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	public void setPdaOrderTaskHandleService(IPdaOrderTaskHandleService pdaOrderTaskHandleService) {
		this.pdaOrderTaskHandleService = pdaOrderTaskHandleService;
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
	public ReceiveCarTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		ReceiveCarTaskEntity reciveCarTask = JsonUtil.parseJsonToObject(ReceiveCarTaskEntity.class, asyncMsg.getContent());
		//pda编号
		reciveCarTask.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		reciveCarTask.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		reciveCarTask.setScanType(asyncMsg.getOperType());
		//用户编号
		reciveCarTask.setScanUser(asyncMsg.getUserCode());
		
		reciveCarTask.setUploadTime(asyncMsg.getUploadTime());
		return reciveCarTask;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param asyncMsg
	 * @param receiveCarTask 
	 * @created 2013-1-8 下午2:57:25
	 */
	private void validate(AsyncMsg asyncMsg, ReceiveCarTaskEntity receiveCarTask) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证数据合法性
		Argument.notNull(receiveCarTask, "ReceiveCarTaskEntity");
		Argument.hasText(receiveCarTask.getId(), "ReceiveCarTaskEntity.id");
		//扫描时间
		Argument.notNull(receiveCarTask.getScanTime(), "ReceiveCarTaskEntity.scanTime");
		//扫描标识
		Argument.hasText(receiveCarTask.getScanFlag(), "ReceiveCarTaskEntity.scanFlag");
		//订单号/约车编号
		Argument.hasText(receiveCarTask.getOrderCode(), "ReceiveCarTaskEntity.orderCode");
		//订单状态
		Argument.hasText(receiveCarTask.getOrderStatus(), "ReceiveCarTaskEntity.orderStatus");
		//订单类型
		Argument.hasText(receiveCarTask.getOrderType(), "ReceiveCarTaskEntity.orderType");
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param receiveCarTask
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, ReceiveCarTaskEntity receiveCarTask) throws PdaBusiException {
		this.validate(asyncMsg, receiveCarTask);
		
		receiveCarTask.setSyncStatus(Constant.SYNC_STATUS_INIT);
		log.debug("---保存接受约车任务扫描数据开始---");
		acctDao.saveReceiveCarTask(receiveCarTask);
		log.debug("---保存接受约车任务扫描数据结束---");
		
		try {
			PdaOrderDto dto = new PdaOrderDto();
			// 司机编码
			dto.setDriverCode(receiveCarTask.getScanUser());
			// 订单号
			dto.setOrderNo(receiveCarTask.getOrderCode());
			// 订单状态
			dto.setOrderStatus(receiveCarTask.getOrderStatus());
			// 订单类型 --转货
			dto.setOrderType(receiveCarTask.getOrderType());
			// 更新时间
			dto.setUpdateDateTime(receiveCarTask.getScanTime());
			long startTime = System.currentTimeMillis();
			log.debug("---调用FOSS转货约车接口开始---");
			pdaOrderTaskHandleService.udpateOrder(dto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]转货约车接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS转货约车接口结束---");
		
		return null;
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_TASK_ACCT.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
