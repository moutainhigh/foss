package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.GetUnldTask;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldBillModel;

public class GetUnldTaskService implements IBusinessService<List<GetUnldTask>, Void>{
	private IPDAUnloadTaskService pdaUnloadTaskService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:43
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<GetUnldTask> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		try {
			List<PDAAssignUnloadTaskEntity> res = pdaUnloadTaskService
					.pdaQueryAssignedUnloadTask(asyncMsg.getUserCode(),
							asyncMsg.getDeptCode());
			List<GetUnldTask> tasks = new ArrayList<GetUnldTask>();
			if (res != null && !res.isEmpty()) {
				for (PDAAssignUnloadTaskEntity unldTask : res) {
					GetUnldTask task = new GetUnldTask();
					task.setTruckCode(unldTask.getVehicleNo());
					task.setPlatformNo(unldTask.getBills().get(0).getPlatformNo());
					task.setTaskCode(unldTask.getBills().get(0).getTaskNo());
					task.setStatus(unldTask.getBills().get(0).getState());
					List<UnldBillModel> bills = new ArrayList<UnldBillModel>();
					for (PDAAssignUnloadBillEntity bill : unldTask.getBills()) {
						UnldBillModel model = new UnldBillModel();
						model.setBillNo(bill.getBillNo());
						model.setUnloadOrderType(bill.getUnloadOrderType());
						bills.add(model);
					}
					task.setBillNos(bills);
					tasks.add(task);
				}
			}
			return tasks;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:49
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	
}
