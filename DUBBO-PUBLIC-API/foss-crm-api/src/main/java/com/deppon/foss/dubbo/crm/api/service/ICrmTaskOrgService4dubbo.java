package com.deppon.foss.dubbo.crm.api.service;

import java.util.List;

import com.deppon.foss.dubbo.crm.api.define.Org2CrmEntity;
import com.deppon.foss.dubbo.crm.api.define.TaskDeptRequestEntity;

/**
 * 匹配任务部门接口
 * @author 335284
 * @since 2016.11.23
 */
public interface ICrmTaskOrgService4dubbo {
	List<Org2CrmEntity> matchTaskOrg(TaskDeptRequestEntity dept);
}