package com.deppon.pda.bdm.module.ocb.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.ocb.server.dao.IFlowCountDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.FlowCountBean;

/**  
 * 作者：xiaolongwu
 * 描述：流量保存service
 * 包名：com.deppon.pda.bdm.module.ocb.server.service.impl
 * 时间：2014-12-30 下午4:10:36
 */
public class FlowCountService implements IBusinessService<Void, FlowCountBean> {
	
	private Logger log = Logger.getLogger(FlowCountService.class);
	private IFlowCountDao flowCountDao;

	@Override
	public FlowCountBean parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FlowCountBean bean = JsonUtil.parseJsonToObject(FlowCountBean.class,asyncMsg.getContent());
		return bean;
	}

	@Override
	public Void service(AsyncMsg asyncMsg, FlowCountBean param) throws PdaBusiException {
		try {
			flowCountDao.saveFlowCount(param);
		} catch (Exception e) {
			log.info(e);
			throw new PdaBusiException(new Throwable("保存流量失败"));
		}
		return null;
	}

	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_FLOW_SAV.VERSION;
	}

	/**
	 * @author：xiaolongwu
	 * @description：同步
	 * @parameters：
	 * @return：false
	 * @time：2014-12-30 下午4:07:18
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * @parameters：flowCountDao 注入dao
	 * @return：
	 */
	public void setFlowCountDao(IFlowCountDao flowCountDao) {
		this.flowCountDao = flowCountDao;
	}
	
}
