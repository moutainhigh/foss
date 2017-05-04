package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.FedBackDeryStatusEntity;

/**
 * 
  * @ClassName FnshDeryService 
  * @Description 反馈任务已接收状态
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class FedbckDeryStusService implements IBusinessService<Void, FedBackDeryStatusEntity> {
	
	private static final Log LOG = LogFactory.getLog(FedbckDeryStusService.class);
	
	private IPdaDeliverTaskService pdaDeliverTaskService;
	
	private IDeliveryDao deliveryDao;
	
	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	public void setPdaDeliverTaskService(IPdaDeliverTaskService pdaDeliverTaskService) {
		this.pdaDeliverTaskService = pdaDeliverTaskService;
	}

	/**
	 * 解析包体
	 */
	@Override
	public FedBackDeryStatusEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FedBackDeryStatusEntity fedBackDeryStatus = JsonUtil.parseJsonToObject(FedBackDeryStatusEntity.class, asyncMsg.getContent());
		// 部门
		fedBackDeryStatus.setDeptCode(asyncMsg.getDeptCode()); 
		// PDA编号
		fedBackDeryStatus.setPdaCode(asyncMsg.getPdaCode());   
		// user编号
		fedBackDeryStatus.setScanUser(asyncMsg.getUserCode()); 
		// 操作类型
		fedBackDeryStatus.setScanType(asyncMsg.getOperType()); 
		
		fedBackDeryStatus.setUploadTime(asyncMsg.getUploadTime());
		return fedBackDeryStatus;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, FedBackDeryStatusEntity fedBackDeryStatus) throws PdaBusiException {
		LOG.info(fedBackDeryStatus);
		try {
			//验证数据有效性
			this.validate(asyncMsg,fedBackDeryStatus);
			long startTime = System.currentTimeMillis();
			//完成下拉派送单接口 更新状态为 “已下拉”
			pdaDeliverTaskService.updateDeliverbillStatus(fedBackDeryStatus.getTaskCode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]反馈任务已接受接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存扫描信息
		saveScanMsg(fedBackDeryStatus);
		return null;
	}
	
	/**
	 * 
	* @Title: saveFedbckDeryStusScanMsg
	* @Description: 【反馈任务已接收 】扫描表
	* @param scanMsg
	* @return void  返回类型 
	* @throws
	 */
	@Transactional
	private void saveScanMsg(FedBackDeryStatusEntity scanMsg){
		deliveryDao.saveFedbckDeryStusScanMsg(scanMsg);
	}
	
	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param fedBackDeryStatus
	 */
	private void validate(AsyncMsg asyncMsg, FedBackDeryStatusEntity fedBackDeryStatus){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		Argument.notNull(fedBackDeryStatus, "FedBackDeryStatusEntity");
		//扫描数据UUID
		Argument.hasText(fedBackDeryStatus.getId(), "FedBackDeryStatusEntity.id");
		//扫描标识
		Argument.hasText(fedBackDeryStatus.getScanFlag(), "FedBackDeryStatusEntity.scanFlag");
		//扫描时间
		Argument.notNull(fedBackDeryStatus.getScanTime(), "FedBackDeryStatusEntity.scanTime");
		//送货任务号
		Argument.hasText(fedBackDeryStatus.getTaskCode(), "FedBackDeryStatusEntity.taskCode");
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_FINISH_DERY.VERSION;  // TODO 没定义完成送货类型
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
