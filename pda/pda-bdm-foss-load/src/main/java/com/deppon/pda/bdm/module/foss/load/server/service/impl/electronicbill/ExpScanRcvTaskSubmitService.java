package com.deppon.pda.bdm.module.foss.load.server.service.impl.electronicbill;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SubmitLoadTask;

/**
 * @ClassName SmtLoadTaskEWaybillService.java 
 * @Description 电子运单  提交装车任务
 * @author 201638
 * @date 2015-1-29
 */
public class ExpScanRcvTaskSubmitService implements IBusinessService<Void, SubmitLoadTask>{
	private IPDAExpressPickService pdaExpressPickService;
	private ILoadDao loadDao;

	/**
	 * @description 解析body
	 * @param asyncMsg 异步数据
	 * @return SubmitLoadTask
	 * @throws PdaBusiException
	 * @author 201638
	 * @date 2015-1-29 
	 */
	@Override
	public SubmitLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitLoadTask model = JsonUtil.parseJsonToObject(SubmitLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:43
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitLoadTask param)
			throws PdaBusiException {
		this.validate(param);
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			//pdaExpressPickService.submitLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
			pdaExpressPickService.finishPickupTask(param.getTaskCode(), asyncMsg.getDeptCode(), param.getTruckCode(),asyncMsg.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:50
	 * @param submitLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(SubmitLoadTask submitLoadTask) throws ArgumentInvalidException{
		Argument.notNull(submitLoadTask, "submitLoadTask");
		//提交员工编号非空
		Argument.hasText(submitLoadTask.getUserCode(), "submitLoadTask.userCode");
		//任务号非空
		Argument.hasText(submitLoadTask.getTaskCode(), "submitLoadTask.taskCode");
		//车牌号非空
	    Argument.hasText(submitLoadTask.getTruckCode(), "submitLoadTask.truckCode");
		
	}
	
	/**
	 * @description 电子运单二期 接货装车 装车提交 
	 * @return ACCT_43
	 * @author 201638
	 * @date 2015-1-29 
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_SCAN_RCV_TASK_SUBMIT.VERSION;
	}

	/**
	 * @description 同步接口 
	 * @return false
	 * @author 201638
	 * @date 2015-1-29 
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressPickService(IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	
	
	
}
