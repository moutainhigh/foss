package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.GetLoadTask;

/**
 * 获取派件指令任务
 * @author 092038
 *
 */
public class GetDeliveryTaskService implements IBusinessService<List<GetLoadTask>, Void>{
	private IPDAExpressSendPieceService pdaExpressSendPieceService;

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

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
			res = pdaExpressSendPieceService.queryUnfinishLoadTask(condition);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		for (PDAAssignLoadTaskEntity entity : res) {
			GetLoadTask model = new GetLoadTask();
			//状态
			model.setStatus(entity.getState());
			//快递员工号
			model.setTallyerCode(entity.getTayller());
			//扫描件数
			model.setPieceScan(entity.getScanQtyTotal());
			//任务号
			model.setTaskCode(entity.getTaskNo());
			//车牌号
			//model.setTruckCode(entity.getVehicleNo());	
			tasks.add(model);
		}
		return tasks;
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}
}
