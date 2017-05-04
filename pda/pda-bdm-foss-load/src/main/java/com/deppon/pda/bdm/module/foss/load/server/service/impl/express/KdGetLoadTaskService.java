package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdGetLoadTask;

/** 
  * @ClassName KdGetLoadTaskService 
  * @Description TODO 获取快递派送装车指令
  * @author cbb 
  * @date 2013-7-30 上午9:23:15 
*/ 
public class KdGetLoadTaskService implements IBusinessService<List<KdGetLoadTask>, Void>{
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
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
	public List<KdGetLoadTask> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		Calendar calendar = Calendar.getInstance();	
		Date endTime = calendar.getTime();
		calendar.add(Calendar.DATE, -3); 		
		Date startTime = calendar.getTime();
		List<KdGetLoadTask> tasks = new ArrayList<KdGetLoadTask>();
		List<PDAAssignLoadTaskEntity> res = null;
		try {
			//调用FOSS接口
			res = pdaExpressDeliverLoadService.queryExpressDeliverLoadTask(asyncMsg.getUserCode(), asyncMsg.getDeptCode(), startTime, endTime);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		for (PDAAssignLoadTaskEntity entity : res) {
			KdGetLoadTask model = new KdGetLoadTask();
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
			//目的站编号
			model.setArrDeptCode(entity.getDestOrgCodes());
			//目的站名称
			model.setArrDeptName(entity.getDestOrgNames());
			//装车类型
			model.setLoadType(entity.getLoadTaskType());
			tasks.add(model);
		}
		return tasks;
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_KD_LOAD_TASK_GET.VERSION;
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
