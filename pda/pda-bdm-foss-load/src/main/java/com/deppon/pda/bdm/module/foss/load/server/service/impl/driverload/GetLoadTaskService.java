package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.GetLoadTask;

/**
 * 司机-获取装车指令
 * @ClassName GetLoadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-5-17
 */
public class GetLoadTaskService implements IBusinessService<List<GetLoadTask>, Void>{
	private IPDAExpressConnectionService pdaExpressConnectionService;
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}
	
	/**
	 *解析包头
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-5-17
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
		  res = pdaExpressConnectionService.queryUnfinishDriverLoadTask(condition);
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
			//快递员
			model.setCourierCode(entity.getTayller());
			//扫描件数
			model.setPicesScan(Integer.valueOf(entity.getScanQtyTotal()));
			tasks.add(model);
		}
		return tasks;
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}

}
