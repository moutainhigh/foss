package com.deppon.foss.module.generalquery.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;
import com.deppon.foss.module.generalquery.shared.domain.DeptMessageInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.ExDelieryAddExportEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTimeEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTitleEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTypeInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KqueryCountEntity;

public class KnowledgeDaoImpl  extends SqlSessionDaoSupport implements IKnowledgeDao {

	@Override
	public KnowledgeContentEntity knowledgeContent(KnowledgeContentInfoEntity knowledgeContent) {
		return (KnowledgeContentEntity)getSqlSession().selectOne(getClass().getName()  + ".selectKnowledgeContent", knowledgeContent);
		   
	}

	@Override
	public DeptMessageInfoEntity selectDept(String userCode) {
		return (DeptMessageInfoEntity)getSqlSession().selectOne(getClass().getName()  + ".selectDeptMessage", userCode);
		   
	}

	@Override
	public DeptMessageInfoEntity selectIfDivision(String deptCode) {
		return (DeptMessageInfoEntity)getSqlSession().selectOne(getClass().getName()  + ".selectIfDivision", deptCode);
	}

	/**
	 * 查询标题
	 * @description 
	 * @param KnowledgeTypeInfoEntity
	 * @return
	 * @author 245955
	 * @date 2015-5-5
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KnowledgeTitleEntity> knowledgeTitle(
			KnowledgeTypeInfoEntity knowledgeTypeInfoEntity) {
		return (List<KnowledgeTitleEntity>)getSqlSession().selectList(getClass().getName()  + ".selectTitle", knowledgeTypeInfoEntity);
	}

	/**
	 * 根据工号查询之前该员工有没有访问记录
	 * @description 
	 * @param title
	 * @param userCode
	 * @return
	 * @author 245955
	 * @date 2015-5-8
	 */
	@Override
	public long queryCount(String title, String userCode) {
		Map<String,String> hsMap=new HashMap<String,String>();
		hsMap.put("title", title);
		hsMap.put("userCode", userCode);
		return (Long)getSqlSession().selectOne(getClass().getName() +".selectQueryCount",hsMap);
	}
    
	/**
	 * 保存员工访问记录信息
	 * @description 
	 * @param entity
	 * @author 245955
	 * @date 2015-5-9
	 */
	@Override
	public void saveQueryCount(KqueryCountEntity entity) {
      getSqlSession().insert(getClass().getName()+".insertQueryCount", entity);		
	}

	/**
	 * 修改访问量数据
	 * @description 
	 * @param userCode
	 * @author 245955
	 * @date 2015-5-9
	 */
	@Override
	public void updateQueryCount(String title) {
      getSqlSession().update(getClass().getName()+".updateQueryCount",title);	
	}
	/**
	 * 查询数据库时间
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-5-27
	 */
	@Override
	public Date querySysDate() {
		return (Date)getSqlSession().selectOne(getClass().getName()+".selectSysDate");
	}
	/**
	 * 返回china包下载地址
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-7-27
	 */
	@Override
	public ExDelieryAddExportEntity returnExportUrl() {
		return (ExDelieryAddExportEntity) getSqlSession().selectOne(getClass().getName()+".showExportUrl");
	}
	/**
	 * 查询最近两天的热点知识库
	 * @return KnowledgeTimeEntity
	 */
	@Override
	public  List<KnowledgeTimeEntity> selectKnowledgeTime() {
		return (List<KnowledgeTimeEntity> )getSqlSession().selectList(getClass().getName()+".selectKnowledgeTime");
	}
}
