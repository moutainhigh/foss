package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;


import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdCreateLoadTask;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdCreateLoadTaskResult;

/**
 * 建立快递派送装车任务接口
 * 
 * @author 
 * @date 2013年7月19日15:56:01
 * @version 1.0
 * @since
 */
public class KdCreateLoadTaskService implements
		IBusinessService<KdCreateLoadTaskResult, KdCreateLoadTask> {
	private static final Logger logger = Logger.getLogger(KdCreateLoadTaskService.class);
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	/**
	 * 
	 * <p>TODO(建立装车任务)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:12:26
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public KdCreateLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		KdCreateLoadTask model = JsonUtil.parseJsonToObject(KdCreateLoadTask.class,
				asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(建立装车任务)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:13:13
	 * @param asyncMsg 请求消息
	 * @param param 建立装车任务信息
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public KdCreateLoadTaskResult service(AsyncMsg asyncMsg, KdCreateLoadTask param)
			throws PdaBusiException {
		//数据合法性校验
		this.validate(param);
		//封装参数
		ExpressDeliverLoadTaskDto  model = new ExpressDeliverLoadTaskDto ();
		model.setCreateTime(param.getScanTime());
		model.setDeviceNo(asyncMsg.getPdaCode());
		model.setOperatorCode(asyncMsg.getUserCode());
		model.setCreateOrgCode(asyncMsg.getDeptCode());
		model.setPlatformNo(param.getPlatformCode());
		model.setTaskNo(param.getTaskCode());
		model.setVehicleNo(param.getTruckCode());
		model.setCourier(param.getDeliveryCode());
		model.setGoodsType(param.getCrgType());
		KdCreateLoadTaskResult kdCreateLoadTaskResult = new KdCreateLoadTaskResult();
		//调用FOSS接口
		try {
			long startTime = System.currentTimeMillis();
			//派送装车
			String taskCode = pdaExpressDeliverLoadService.createTask(model);
			kdCreateLoadTaskResult.setTaskCode(taskCode);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			logger.info("[asyncinfo]创建快递装车任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return kdCreateLoadTaskResult;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:21
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(KdCreateLoadTask kdCreateLoadTask)
			throws ArgumentInvalidException {
		// 检验建立装车任务非空
		Argument.notNull(kdCreateLoadTask, "kdCreateLoadTask");
		// 检验车牌号非空
		Argument.hasText(kdCreateLoadTask.getTruckCode(),
				"kdCreateLoadTask.truckCode");
		// 检验时间非空
		Argument.notNull(kdCreateLoadTask.getScanTime(),
				"kdCreateLoadTask.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_KD_LOAD_TASK_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}

}
