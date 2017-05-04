package com.deppon.foss.module.generalquery.server.service.impl;

import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;
import com.deppon.foss.module.generalquery.shared.domain.KqueryCountEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;

/**
 * 新知识库查阅量
 * @ClassName SaveQueryCountService.java 
 * @Description 
 * @author 245955
 * @date 2015-5-8
 */
public class SaveQueryCountService implements IBusinessService<Void, KqueryCountEntity> {
	private IKnowledgeDao knowledgeDao;
	@Override
	public KqueryCountEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        KqueryCountEntity kqueryCount=JsonUtil.parseJsonToObject(KqueryCountEntity.class, asyncMsg.getContent());
        return kqueryCount;
	}
	
	@Override
	public Void service(AsyncMsg asyncMsg, KqueryCountEntity param) throws PdaBusiException {
		this.validate(param,asyncMsg);
		//根据工号和标题,调用方法,判断是否存在访问记录
		String isRead="Y";
		long count= knowledgeDao.queryCount(param.getTitle(), asyncMsg.getUserCode());
		if (count==0) {
			KqueryCountEntity entity=new KqueryCountEntity();
			entity.setUserCode(asyncMsg.getUserCode());
			entity.setTitle(param.getTitle());
			entity.setIsRead(isRead);
			//把这条记录插入到访问量表中
			knowledgeDao.saveQueryCount(entity);
			//按标题修改知识库表的访问量
			knowledgeDao.updateQueryCount(param.getTitle());
		}
		return  null;
	}
	 
	
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_KNOW_QUERYSOUNT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
 /**
  * 参数验证
  * @description 
  * @param kqueryCount
  * @author 245955
  * @date 2015-5-8
  */
 private void validate(KqueryCountEntity kqueryCount,AsyncMsg asyncMsg) {
		// 判断标题
		Argument.hasText(kqueryCount.getTitle(), "kqueryCount.title");
		//判断工号
		Argument.hasText(asyncMsg.getUserCode(), "asyncMsg.userCode");
	}
 public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}
}
