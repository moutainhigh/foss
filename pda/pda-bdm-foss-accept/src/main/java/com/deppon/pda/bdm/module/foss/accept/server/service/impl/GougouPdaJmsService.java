package com.deppon.pda.bdm.module.foss.accept.server.service.impl;


import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GougouPdaJmsEntity;

/**
 * 裹裹系统对接PDA
 * @author 245955
 * 
 */
public class GougouPdaJmsService implements IBusinessService<String, GougouPdaJmsEntity>  {
	private Logger log = Logger.getLogger(PdamPositionSaveService.class);
	private IAcctDao acctDao;
	/**
	 * 解析包体
	 */
	@Override
	public GougouPdaJmsEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		GougouPdaJmsEntity entity=JsonUtil.parseJsonToObject(GougouPdaJmsEntity.class,asyncMsg.getContent());
		return entity;
	}
	
	/**
	 * 服务方法
	 */
	@Override
	public String service(AsyncMsg asyncMsg, GougouPdaJmsEntity param)
			throws PdaBusiException {
		PdaEsbProducerServiceImpl pdaEsbProducerServiceImpl = new PdaEsbProducerServiceImpl();
		//校验参数是否合法
		this.validate(param);
		log.info("向DOP发送数据开始"+param.getLogisticNo()+param.getVerifyCode());
		//保存裹裹渠道单号和验证码至数据库
		acctDao.saveVerifyCode(param);
		pdaEsbProducerServiceImpl.syncWaybillCodeToDop(param);
		log.info("向DOP发送数据结束");
		return null;
	}

	/**
	 * 参数有效校验
	 */
	private void validate(GougouPdaJmsEntity entity) throws PdaBusiException {
		Argument.notNull(entity, "GougouPdaSmsEntity");
		//验证码
		Argument.hasText(entity.getVerifyCode(), "GougouPdaSmsEntity.verifyCode");
		//渠道单号
		Argument.hasText(entity.getLogisticNo(), "GougouPdaSmsEntity.logisticNo");
	}
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_GOU_PDA_JMS.VERSION;
	}
	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}
 
}
