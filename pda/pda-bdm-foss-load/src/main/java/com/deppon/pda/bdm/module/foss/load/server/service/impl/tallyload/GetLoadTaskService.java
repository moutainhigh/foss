package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.GetLoadTaskEntity;

/**
 * 获取装车指令
 * @ClassName GetPotService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-22
 */
public class GetLoadTaskService implements IBusinessService<List<GetLoadTaskEntity>, Void> {
	private IPDAExpressConnectionService pdaExpressConnectionService;
	
	/**
	 * 解析包体
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	/**
	 * 服务方法
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public List<GetLoadTaskEntity> service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
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
		List<GetLoadTaskEntity> tasks = new ArrayList<GetLoadTaskEntity>();
		List<PDAAssignLoadTaskEntity> res = null;
		try {
			//调用FOSS接口
			res=pdaExpressConnectionService.queryUnfinishLoadTask(condition);
			//res = pdaDeliverLoadService.queryAssignedLoadTask(condition);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		for (PDAAssignLoadTaskEntity entity : res) {
			GetLoadTaskEntity model = new GetLoadTaskEntity();
			List<String> arrDeptCode = new ArrayList<String>();
			List<String> arrDeptName = new ArrayList<String>();
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
			//到达部门编码
			arrDeptCode.add(entity.getAccessPointCode());
			model.setArrDeptCode(arrDeptCode);
			//到达部门名称
			arrDeptName.add(entity.getAccessPointName());
			model.setArrDeptName(arrDeptName);
			model.setLoadType(entity.getLoadTaskType());
			tasks.add(model);
		}
		return tasks;
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public String getOperType() {
		 return LoadConstant.OPER_TYPE_LOAD_TRAN_GET.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}

}
