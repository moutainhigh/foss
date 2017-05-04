package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FindGoodsAdminSubmitEntity;

/**
 * 提交找货任务
 * @author 245955
 *
 */
public class ClearSearchSmtService implements IBusinessService<Void, FindGoodsAdminSubmitEntity>{
	private static final Log LOG = LogFactory.getLog(SmtClearTaskService.class);
	private IFindGoodsAdminService findGoodsAdminService;
	private IClearDao clearDao;
	/**
	 * 找货任务-获取包头
	 */
	@Override
	public FindGoodsAdminSubmitEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		FindGoodsAdminSubmitEntity entity=JsonUtil.parseJsonToObject(FindGoodsAdminSubmitEntity.class, asyncMsg.getContent());
		entity.setUser(asyncMsg.getUserCode());
		return entity;
	}

	/**
	 * 提交找货任务-Service类
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FindGoodsAdminSubmitEntity param)
			throws PdaBusiException {
		LOG.info("submit cleartask start...");
		//参数校验
		this.validate(asyncMsg,param);
		try {
			if(clearDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
		    //提交找货任务
			findGoodsAdminService.commitFindGoodsAdmin(param.getTaskCode(),param.getUser());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.info("submit success!");
		return null;
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_SEARCH_SUBMIT.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	/**
	 * 非空校验
	 * @param entity
	 * @throws ArgumentInvalidException
	 */
	private void validate(AsyncMsg asyncMsg,FindGoodsAdminSubmitEntity entity)
			throws ArgumentInvalidException {
		//检验非空
		Argument.notNull(entity, "FindGoodsAdminSubmitEntity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "FindGoodsAdminSubmitEntity.taskCode");
		//PDA编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}
	
	public void setFindGoodsAdminService(
			IFindGoodsAdminService findGoodsAdminService) {
		this.findGoodsAdminService = findGoodsAdminService;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}
    
}
