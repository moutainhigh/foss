package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.FnshLoadTaskEntity;

/**
 * 完成装车任务
 * @ClassName FnshLoadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-23
 */

public class FnshLoadTaskService implements IBusinessService<Void, FnshLoadTaskEntity>  {
	private IPDAExpressConnectionService pdaExpressConnectionService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 解析包体
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p>
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public FnshLoadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FnshLoadTaskEntity entity=JsonUtil.parseJsonToObject(FnshLoadTaskEntity.class, asyncMsg.getContent());
		return entity;
	}
	
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p>
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FnshLoadTaskEntity param) throws PdaBusiException {
		this.validate(param);
		//保存完成任务信息
		//loadDao.saveFnshLoadTaskScan(param);
		try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
		   List<String> list=new ArrayList<String>();	   
		   list.addAll(param.getTaskCodes());//任务号
		   pdaExpressConnectionService.finishLoadTask(list,param.getVehicleNo());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]完成装车任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-23
	 */
	private void validate(FnshLoadTaskEntity entity) throws ArgumentInvalidException{
		//扫描时间非空
		Argument.notNull(entity.getVehicleNo(), "FnshLoadTaskEntity.vehicleNo");
		//任务号非空
		Argument.notNull(entity.getTaskCodes(), "FnshLoadTaskEntity.taskCodes");
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_FNSH.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}    
	
}
