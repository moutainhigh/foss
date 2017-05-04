package com.deppon.foss.module.generalquery.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;

import com.deppon.foss.module.generalquery.shared.domain.DeptMessageInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTitleEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTitleInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTypeEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTypeInfoEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;

/**
 * 查询某种类型对应的标题服务类
 * @author way       
 */
public class KnowLedgeTitleService implements IBusinessService<KnowledgeTitleInfoEntity, KnowledgeTypeEntity> {
	
	private static final Log LOG = LogFactory.getLog(KnowLedgeTitleService.class);
	
	private IKnowledgeDao knowledgeDao;
	
	public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}


	/**
	 * 解析包体
	 */
	@Override
	public KnowledgeTypeEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		//解析内容
		KnowledgeTypeEntity knowledgeTypeEntity = JsonUtil.parseJsonToObject(KnowledgeTypeEntity.class, asyncMsg.getContent());
		
		return knowledgeTypeEntity;
	}
	
	@Override
	public KnowledgeTitleInfoEntity service(AsyncMsg asyncMsg,
			KnowledgeTypeEntity knowledgeTypeEntity) throws PdaBusiException {
		try {
			LOG.debug("start get  KnowLedgeType...");
			//验证数据有效性
			this.validate(knowledgeTypeEntity);
			
			KnowledgeTitleInfoEntity kTitleInfoEntity = new KnowledgeTitleInfoEntity();
			
			Long date=knowledgeDao.querySysDate().getTime();
			kTitleInfoEntity.setVersion(String.valueOf(date));
			
			List<KnowledgeTitleEntity> knowledgeTitleEntityList = new ArrayList<KnowledgeTitleEntity>();
			//查询条件
			KnowledgeTypeInfoEntity knowledgeTypeInfoEntity = toKnowledgeTypeInfoEntity(asyncMsg,knowledgeTypeEntity);
			
		
			//查询标题
			knowledgeTitleEntityList = knowledgeDao.knowledgeTitle(knowledgeTypeInfoEntity);
			
			if(knowledgeTitleEntityList!=null){
				
				kTitleInfoEntity.setKnowledgeTitleEntityList(knowledgeTitleEntityList);
				
			}
			return kTitleInfoEntity;
		} catch (BusinessException e) {
			LOG.error("知识库内容查询" +
					"服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	
	
	/**
	 * 封装实体
	 */
	public KnowledgeTypeInfoEntity toKnowledgeTypeInfoEntity(AsyncMsg asyncMsg,
		   KnowledgeTypeEntity knowledgeTypeEntity) {

		   KnowledgeTypeInfoEntity knowledgeTypeInfoEntity = new KnowledgeTypeInfoEntity();
		   
			//根据员工工号查询所在的部门 
			DeptMessageInfoEntity dmie = knowledgeDao.selectDept(asyncMsg.getUserCode());
			if("Y".equals(dmie.getDivision())){//如果本身就是事业部 直接返回该部门名称
				knowledgeTypeInfoEntity.setDeptName(dmie.getDeptName());
			}else{
				while(dmie.getDivision() == null || "N".equals(dmie.getDivision())){//如果不是事业部，则一直查询，知道是事业部为止，返回事业部的名称
					dmie = knowledgeDao.selectIfDivision(dmie.getParent_org_code());
				}
				knowledgeTypeInfoEntity.setDeptName(dmie.getDeptName());
			}
			//赋值
			knowledgeTypeInfoEntity.setTypeName(knowledgeTypeEntity.getTypeName());
			long versionLong=0;
			String versionString = knowledgeTypeEntity.getVersion();
			if(versionString !=null && !"".equals(versionString)){
				 versionLong = Long.parseLong(knowledgeTypeEntity.getVersion());
			}
			
			knowledgeTypeInfoEntity.setVersionLong(versionLong);
			if(versionLong!=0){
				knowledgeTypeInfoEntity.setVersionDate(new Date(versionLong));
			}
				
			return knowledgeTypeInfoEntity;
		
	}
	
	
	
	/**
	 * 验证数据有效性
	 * @param qryTraceInfoEntity
	 */
	private void validate(KnowledgeTypeEntity knowledgeTypeEntity) {
		
		Argument.notNull(knowledgeTypeEntity, "KnowledgeTypeEntity");
		// 判断知识主题
		Argument.hasText(knowledgeTypeEntity.getTypeName(), "KnowledgeTypeEntity.typeName");
		
	}
	

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_KNOWLEDGE_TITLE.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	

	
}
