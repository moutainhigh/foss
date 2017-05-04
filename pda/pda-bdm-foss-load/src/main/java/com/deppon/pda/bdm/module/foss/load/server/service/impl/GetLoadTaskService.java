package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.load.shared.domain.GetLoadTask;

/**
 * 
 * TODO(获取装车指令)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:26:34,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:26:34
 * @since
 * @version
 */
public class GetLoadTaskService implements IBusinessService<List<GetLoadTask>, Void>{
	private IPDADeliverLoadService pdaDeliverLoadService;
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:15:31
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<GetLoadTask> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		Calendar calendar = Calendar.getInstance();	
		Date endTime = calendar.getTime();
		calendar.add(Calendar.DATE, -3); 		
		Date startTime = calendar.getTime();
		QueryAssignedLoadTaskEntity condition = new QueryAssignedLoadTaskEntity();
		condition.setLoaderCode(asyncMsg.getUserCode());
		condition.setLoaderOrgCode(asyncMsg.getDeptCode());
		condition.setQueryTimeBegin(startTime);
		condition.setQueryTimeEnd(endTime);
		condition.setQueryTransportTimeBegin(startTime);
		condition.setQueryTransportTimeEnd(endTime);
		List<GetLoadTask> tasks = new ArrayList<GetLoadTask>();
		List<PDAAssignLoadTaskEntity> res = null;
		try {
			//调用FOSS接口
			res = pdaDeliverLoadService
					.queryAssignedLoadTask(condition);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		for (PDAAssignLoadTaskEntity entity : res) {
			GetLoadTask model = new GetLoadTask();
			//派送单号
			model.setDeryListCode(entity.getDeliverBillNo());
			//月台号
			model.setPlatformCode(entity.getPlatformNo());
			//任务状态
			model.setStatus(entity.getState());
			//任务号
			model.setTaskCode(entity.getTaskNo());
			//车牌号
			model.setTruckCode(entity.getVehicleNo());
			model.setArrDeptCode(entity.getDestOrgCodes());
			model.setArrDeptName(entity.getDestOrgNames());
			model.setLoadType(entity.getLoadTaskType());
			tasks.add(model);
		}
		return tasks;
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_TASK_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDeliverLoadService(
			IPDADeliverLoadService pdaDeliverLoadService) {
		this.pdaDeliverLoadService = pdaDeliverLoadService;
	}
}
