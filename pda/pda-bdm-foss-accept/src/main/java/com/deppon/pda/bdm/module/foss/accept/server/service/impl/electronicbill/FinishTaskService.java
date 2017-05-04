package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.FinishTaskEntity;

/**
 * TODO完成接货实现类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:200689,date:2015-1-28 下午5:23:49,content:TODO
 * </p>
 * 
 * @author 200689
 * @date 2015-1-28 下午5:23:49
 * @since
 * @version
 */
public class FinishTaskService implements IBusinessService<Void, FinishTaskEntity> {
	private IPdaWaybillService pdaWaybillService;
	
	private IAcctDao acctDao;

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}
	
	private Logger log = Logger.getLogger(getClass());

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:21
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public FinishTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FinishTaskEntity entity = JsonUtil.parseJsonToObject(FinishTaskEntity.class, asyncMsg.getContent());
//		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
//		entity.setScanType(asyncMsg.getOperType());
//		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FinishTaskEntity param) throws PdaBusiException {

		this.validate(param);
//		acctDao.saveFnshLoadTaskScan(param);
		try {
			if(acctDao.queryNoSyncScanMsgCountByTaskCode(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			log.info("---调用FOSS完成接货接口开始---");
			//传给FOSS任务号
			pdaWaybillService.overPickup(param.getTaskCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		log.info("---调用FOSS完成接货接口结束---");
		return null;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:27
	 * @param unldScanEnity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(FinishTaskEntity finishTaskEntity) throws ArgumentInvalidException {
		// 检查完成任务非空
		Argument.notNull(finishTaskEntity, "finishTaskEntity");
		// 检验任务号非空
		Argument.hasText(finishTaskEntity.getTaskCode(), "finishTaskEntity.taskCode");
	}

	/**
	 * @description 电子运单二期  接货收件 完成任务接口<br>
	 * @return ACCT_40
	 * @author 201638
	 * @date 2015-3-2 
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_FINISH_TASK.VERSION;
	}

	/**
	 * @description 同步接口
	 * @return false
	 * @author 201638
	 * @date 2015-3-2 
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}


}
