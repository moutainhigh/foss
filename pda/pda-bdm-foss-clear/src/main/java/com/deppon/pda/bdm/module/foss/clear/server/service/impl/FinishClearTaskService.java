package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FnshClrTaskScanEntity;
/**
 * 
  * @ClassName FinishClearTaskService 
  * @Description TODO 完成清仓任务
  * @author xujun dpxj@deppon.com 
  * @date 2013-1-10 下午7:06:15
 */
public class FinishClearTaskService implements IBusinessService<Void, FnshClrTaskScanEntity> {
	
	private static final Log LOG = LogFactory.getLog(FinishClearTaskService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;
	
	/**
	* @Title: parseBody 
	* @Description: 反序列化json字符串
	* @param  asyncMsg   
	* @return FnshClrTaskScanEntity   
	* @throws
	 */
	@Transactional
	@Override
	public FnshClrTaskScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		FnshClrTaskScanEntity clrTaskScanEntity = 
				JsonUtil.parseJsonToObject(FnshClrTaskScanEntity.class, asyncMsg.getContent());
		// 部门
		clrTaskScanEntity.setDeptCode(asyncMsg.getDeptCode()); 
		// PDA
		clrTaskScanEntity.setPdaCode(asyncMsg.getPdaCode());   
		// 设置用户编码
		clrTaskScanEntity.setScanUser(asyncMsg.getUserCode()); 
		// 操作类型
		clrTaskScanEntity.setScanType(asyncMsg.getOperType()); 
		clrTaskScanEntity.setId(asyncMsg.getId());			  
		clrTaskScanEntity.setUploadTime(asyncMsg.getUploadTime()); 
		return clrTaskScanEntity;
	}
	
	/**
	* @Title: service 
	* @Description: 服务实现类
	* @param  asyncMsg   
	* @param  param 
	* @return Void   
	* @throws
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, FnshClrTaskScanEntity param)
			throws PdaBusiException {
		LOG.info("完成清仓任务开始");
		this.validate(asyncMsg,param);
		//调用FOSS服务类
		try {
			long startTime = System.currentTimeMillis();
			pdaStockcheckingService.finishPdaStTask(param.getTaskCode(), 
					param.getScanTime(), 
					asyncMsg.getPdaCode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]完成清仓任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.error("完成清仓任务出现错误："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.info("完成清仓任务成功");
		return null;
	}

	/**
	* @Title: validate 
	* @Description: 验证业务数据
	* @param asyncMsg
	* @param param   
	* @return void   
	* @throws
	 */
	private void validate(AsyncMsg asyncMsg, FnshClrTaskScanEntity param) {
		Argument.notNull(param, "FnshClrTaskScanEntity");
		Argument.hasText(param.getTaskCode(), "FnshClrTaskScanEntity.TaskCode");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_TASK_FINISH.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}
}
