package com.deppon.foss.module.generalquery.server.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTimeEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;

/**
 * 查询最近两天的热点知识库
 * @author 245955       
 * @version 1.0     
 * @created 2015-9-10
 */
public class KnowLedgeTimeService implements IBusinessService<List<KnowledgeTimeEntity> , Void> {
	
	private static final Log LOG = LogFactory.getLog(KnowLedgeTimeService.class);
	
	private IKnowledgeDao knowledgeDao;
	
	public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}


	
	
	/**
	 * 服务方法
	 */
	@Override
	public List<KnowledgeTimeEntity>  service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
		List<KnowledgeTimeEntity>  kTime=null;
		try {
			kTime=knowledgeDao.selectKnowledgeTime();
		} catch (BusinessException e) {
			LOG.error("查询最近两天热点知识库服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch (Exception e) {
			LOG.error("查询最近两天热点知识库服务异常："+e);
			throw new FossInterfaceException(null,"查询出现位置异常");
		} 
		return kTime;
	}

	

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_KNOWLEDGE_TIME.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 解析包体
	 * @param asyncMsg
	 * @return
	 */
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
