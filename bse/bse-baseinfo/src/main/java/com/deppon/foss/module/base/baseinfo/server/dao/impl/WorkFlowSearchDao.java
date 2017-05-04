package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkFlowSearchDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;

/**
 * @author 078816-WangPeng
 * @date   2014-02-11
 * @description 工作流操作Dao
 *
 */
public class WorkFlowSearchDao extends SqlSessionDaoSupport implements IWorkFlowSearchDao {

	//定义NAMESPACE常量
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".workFlowSearch";
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 根据条件查询工作流的相关信息
	 *
	 */
	@Override
	public DeptTempArrearsWorkFlowDto queryWorkFlowInfos(
			DeptTempArrearsWorkFlowDto entity) {
		return (DeptTempArrearsWorkFlowDto) this.getSqlSession().selectOne(NAMESPACE+".queryWorkFlowInfos", entity);
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存起草提交的工作流信息
	 *
	 */
	@Override
	@Transactional
	public int saveWorkFlowDraftInfos(DeptTempArrearsWorkFlowDto entity) {
		this.getSqlSession().insert(NAMESPACE+".saveWorkFlowBusinessRelated", entity);
		return this.getSqlSession().insert(NAMESPACE+".saveWorkFlowDraftInfos", entity);
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存工作流审批信息
	 *
	 */
	@Override
	public int saveWorkFlowExamineInfos(DeptTempArrearsWorkFlowDto entity) {
		return this.getSqlSession().update(NAMESPACE+".saveWorkFlowExamineInfos", entity);
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 根据部门编码获取该营业部的最大临欠额度
	 *
	 */
	@SuppressWarnings("unused")
	private BigDecimal getOldDeptTempArrears(String code){
		return (BigDecimal) this.getSqlSession().selectOne(NAMESPACE+".getOldDeptTempArrears", code);
	}

	@Override
	public String recordCurrentDayWorkNums() {
		return (String) this.getSqlSession().selectOne(NAMESPACE+".recordCurrentDayWorkNums");
	}
}
