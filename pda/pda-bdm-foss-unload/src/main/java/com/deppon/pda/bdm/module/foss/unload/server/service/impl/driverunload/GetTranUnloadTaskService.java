package com.deppon.pda.bdm.module.foss.unload.server.service.impl.driverunload;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SCPDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.GetUnloadTaskEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.GetUnloadTranTaskResultEntity;

/**
 * 获取接驳卸车指令任务
 * 
 * @ClassName GetTranUnloadTaskService.java
 * @Description
 * @author 245955
 * @date 2015-4-14
 */
public class GetTranUnloadTaskService implements IBusinessService<List<GetUnloadTranTaskResultEntity>, GetUnloadTaskEntity> {
	private ISCPDAUnloadTaskService scpdaUnloadTaskService;
	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @description
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public GetUnloadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetUnloadTaskEntity entity=JsonUtil.parseJsonToObject(GetUnloadTaskEntity.class,asyncMsg.getContent());
		return entity;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @description
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public List<GetUnloadTranTaskResultEntity> service(AsyncMsg asyncMsg, GetUnloadTaskEntity param) throws PdaBusiException {
		try {
			//FOSS获取卸车指令
			List<SCPDAAssignUnloadTaskEntity> res =scpdaUnloadTaskService.pdaQueryAssignedSCUnloadTask(param.getVehicleNo());
			List<GetUnloadTranTaskResultEntity> tasks = new ArrayList<GetUnloadTranTaskResultEntity>();
			if (res != null && !res.isEmpty()) {
				for (SCPDAAssignUnloadTaskEntity unldTask : res) {
					GetUnloadTranTaskResultEntity task = new GetUnloadTranTaskResultEntity();
					  task.setStatus(unldTask.getState());//状态
					  task.setTruckCode(unldTask.getVehicleNo());//车牌号
					  task.setConnectionPoint(unldTask.getConnectionPoint());//到达接驳点
					  task.setBillNo(unldTask.getBillNo());//单据编号
					  task.setTaskCode(unldTask.getTaskNo());//任务号
					  task.setWaybillQtyTotal(Integer.parseInt(unldTask.getWaybillQtyTotal()));//总票数
					  task.setGoodsQtyTotal(Integer.parseInt(unldTask.getGoodsQtyTotal()));//总件数
					//List<TranUnloadBillModel> bills = new ArrayList<TranUnloadBillModel>();
//					if(unldTask.getBillNo()!=null){
//					 for (SCPDAAssignUnloadTaskEntity billEntity:unldTask.getBills()){
//						TranUnloadBillModel model = new TranUnloadBillModel();
//						model.setBillNo(billEntity.getBillNo());
//						bills.add(model);
//					  }	
//					 task.setBillNos(bills);//单据编号
//					}				
				 tasks.add(task);
				}		
			}
			return tasks;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	}

	/**
	 * 操作类型
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLOAD_TRAN_GET.VERSION;
	}

	/**
	 * 是否异步
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setScpdaUnloadTaskService(ISCPDAUnloadTaskService scpdaUnloadTaskService) {
		this.scpdaUnloadTaskService = scpdaUnloadTaskService;
	}
	
}
