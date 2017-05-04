package com.deppon.foss.module.generalquery.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.generalquery.shared.domain.DeptMessageInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.ExDelieryAddExportEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTimeEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTitleEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeTypeInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KqueryCountEntity;

public interface IKnowledgeDao {

	
	public KnowledgeContentEntity knowledgeContent(KnowledgeContentInfoEntity knowledgeContent);
	
	public DeptMessageInfoEntity selectDept(String userCode);
	
	public DeptMessageInfoEntity selectIfDivision(String deptCode);
	
	/**
	 * 查询标题
	 * @description 
	 * @param KnowledgeTypeInfoEntity
	 * @return
	 * @author 245955
	 * @date 2015-5-5
	 */
	public List<KnowledgeTitleEntity> knowledgeTitle(KnowledgeTypeInfoEntity entity);
	
	/**
	 * 根据工号查询之前该员工有没有访问记录
	 * @description 
	 * @param title
	 * @param userCode
	 * @return
	 * @author 245955
	 * @date 2015-5-8
	 */
	public long queryCount(String title,String userCode);
	
	/**
	 * 向访问量表中插入数据
	 * @description 
	 * @param entity
	 * @author 245955
	 * @date 2015-5-9
	 */
	public  void saveQueryCount(KqueryCountEntity entity);
	/**
	 * 修改访问量数据
	 * @description 
	 * @param userCode
	 * @author 245955
	 * @date 2015-5-9
	 */
	public void updateQueryCount(String userCode);
	/**
	 * 查询数据库时间
	 */
	public Date querySysDate();
	/**
	 * 返回china包下载地址
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-7-27
	 */
	public ExDelieryAddExportEntity returnExportUrl();
	/**
	 * 查询最近两天的热点知识库
	 * @return KnowledgeTimeEntity
	 */
	public List<KnowledgeTimeEntity>  selectKnowledgeTime();
}
