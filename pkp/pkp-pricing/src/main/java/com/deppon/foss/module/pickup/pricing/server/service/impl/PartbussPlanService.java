/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PartbussPlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ParameterException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理公司运价方案Service接口 实现类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 上午11:27:36
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 上午11:27:36
 * @since
 * @version
 */
public class PartbussPlanService implements IPartbussPlanService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(PartbussPlanService.class);

    /**
     * 快递代理公司运价方案Dao接口.
     */
    private IPartbussPlanDao partbussPlanDao;

    /**
     * 快递代理网点运价方案Service接口.
     */
    private IOutbranchPlanService outbranchPlanService;

    /**
     * 快递代理网点运价方案明细Service接口.
     */
    private IOubrPlanDetailService oubrPlanDetailService;

    /**
     * 快递代理公司接口.
     */
    private ILdpAgencyCompanyService ldpAgencyCompanyService;
    
    /**
     * 快递代理公司网点接口
     */
    private ILdpAgencyDeptService ldpAgencyDeptService;

    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 人员 Service接口.
     */
    private IEmployeeService employeeService;

    /**
     * 设置 人员 Service接口.
     * 
     * @param employeeService
     *            the employeeService to set
     */
    public void setEmployeeService(IEmployeeService employeeService) {
	this.employeeService = employeeService;
    }
    
    /**
     * @param ldpAgencyDeptService the ldpAgencyDeptService to set
     */
    public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
        this.ldpAgencyDeptService = ldpAgencyDeptService;
    }

    /**
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 设置 快递代理公司接口.
     * 
     * @param ldpAgencyCompanyService
     *            the ldpAgencyCompanyService to set
     */
    public void setLdpAgencyCompanyService(
	    ILdpAgencyCompanyService ldpAgencyCompanyService) {
	this.ldpAgencyCompanyService = ldpAgencyCompanyService;
    }

    /**
     * 设置 快递代理网点运价方案明细Service接口.
     * 
     * @param oubrPlanDetailService
     *            the oubrPlanDetailService to set
     */
    public void setOubrPlanDetailService(
	    IOubrPlanDetailService oubrPlanDetailService) {
	this.oubrPlanDetailService = oubrPlanDetailService;
    }

    /**
     * 设置 快递代理网点运价方案Service接口.
     * 
     * @param outbranchPlanService
     *            the outbranchPlanService to set
     */
    public void setOutbranchPlanService(
	    IOutbranchPlanService outbranchPlanService) {
	this.outbranchPlanService = outbranchPlanService;
    }

    /**
     * 设置 快递代理公司运价方案Dao接口.
     * 
     * @param partbussPlanDao
     *            the partbussPlanDao to set
     */
    public void setPartbussPlanDao(IPartbussPlanDao partbussPlanDao) {
	this.partbussPlanDao = partbussPlanDao;
    }

    /**
     * 新增快递代理公司运价方案.
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return entity
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:27:36
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public PartbussPlanEntity addInfo(PartbussPlanEntity entity) {
	if (null == entity) {
	    return null;
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	LOGGER.debug("versionNo:" + entity.getVersionNo());
	entity.setModifyDate(date);
	entity.setActive(FossConstants.INACTIVE);
	int result = partbussPlanDao.addInfo(entity);
	if (result > 0) {
	    return convertInfo(entity);
	} else {
	    return null;
	}
    }

    /**
     * 根据code作废快递代理公司运价方案.
     * 
     * @param codes
     *            ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:27:39
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#deleteInfo(java.lang.String[],
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	if (CollectionUtils.isEmpty(codes)) {
	    throw new ParameterException("传入参数ID不允许为空！");
	}
	// 根据父ＩＤ删除快递代理网点运价方案明细信息
	outbranchPlanService.deleteInfoByParentCode(codes);
	partbussPlanDao.deleteInfo(codes, modifyUser);

	return FossConstants.SUCCESS;
    }

    /**
     * 修改快递代理公司运价方案.
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public PartbussPlanEntity updateInfo(PartbussPlanEntity entity) {
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getId())) {
	    throw new PartbussPlanException("修改快递代理公司运价方案 ID不允许为空");
	} else {
	    entity.setModifyDate(new Date());
	    int result = partbussPlanDao.updateInfo(entity);

	    if (result > 0) {
		return convertInfo(entity);
	    } else {
		return null;
	    }
	}
    }

    /**
     * 根据传入对象查询符合条件所有快递代理公司运价方案信息.
     * 
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:27:39
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity,
     *      int, int)
     */
    @Override
    public List<PartbussPlanEntity> queryInfos(PartbussPlanEntity entity,
	    int limit, int start) {

	return convertInfoList(partbussPlanDao.queryInfos(entity, limit, start));
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:27:39
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public Long queryRecordCount(PartbussPlanEntity entity) {

	return partbussPlanDao.queryRecordCount(entity);
    }

    /**
     * 激活快递代理公司运价方案.
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:48:04
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#activate(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Transactional
    @Override
    public int activate(PartbussPlanEntity entity) {

	if (null == entity) {
	    throw new PartbussPlanException("激活快递代理公司运价方案 信息不允许为空！");
	}
	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());
	// 设置 生效日期.
	entity.setBeginTime(new Date());
	int result = partbussPlanDao.updateInfo(entity);
	if (result > 0) {
	    OutbranchPlanEntity entity2 = new OutbranchPlanEntity();
	    // 设置 快递代理运价方案ID
	    entity2.setExpressPartbussPlanId(entity.getId());
	    // 设置 更新组织
	    entity2.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    entity2.setModifyUser(entity.getModifyUser());

	    return outbranchPlanService.activate(entity2);
	} else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 立即激活快递代理公司运价方案
     * </p>
     * .
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#immediatelyActivate(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Transactional
    @Override
    public int immediatelyActivate(PartbussPlanEntity entity) {
	if (null == entity) {
	    throw new PartbussPlanException("激活快递代理公司运价方案 信息不允许为空！");
	}
	if (entity.getBeginTime().before(new Date(System.currentTimeMillis()))) {
	    throw new PartbussPlanException(
		    "立即激活操作的生效时间必须大于等于营业日期!,才能立即被激活,请重新修改草稿！");
	}
	PartbussPlanEntity entity3 = new PartbussPlanEntity();
	entity3.setExpressPartbussCode(entity.getExpressPartbussCode());
	entity3.setActive(FossConstants.ACTIVE);
	entity3.setBillDate(entity.getBeginTime());

	List<PartbussPlanEntity> list = queryInfos(entity3, Integer.MAX_VALUE,
		NumberConstants.ZERO);
	if (CollectionUtils.isNotEmpty(list)) {
	    throw new PartbussPlanException("该快递代理公司已经存在激活的运价方案！");
	}

	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());

	int result = partbussPlanDao.updateInfo(entity);
	if (result > 0) {
	    OutbranchPlanEntity entity2 = new OutbranchPlanEntity();
	    // 设置 快递代理运价方案ID
	    entity2.setExpressPartbussPlanId(entity.getId());
	    // 设置 更新组织
	    entity2.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    entity2.setModifyUser(entity.getModifyUser());

	    return outbranchPlanService.immediatelyActivate(entity2);
	} else {
	    return FossConstants.FAILURE;
	}

    }

    /**
     * <p>
     * 立即中止快递代理公司运价方案
     * </p>
     * .
     * 
     * @param entity
     *            快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#immediatelyStop(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public int immediatelyStop(PartbussPlanEntity entity) {
	if (null == entity) {
	    throw new PartbussPlanException("激活快递代理公司运价方案 信息不允许为空！");
	}
	if (entity.getEndTime().before(new Date(System.currentTimeMillis()))) {
	    throw new PartbussPlanException(
		    "立即中止操作的截止时间必须大于等于营业日期!,才能立即被激活,请重新修改草稿！");
	}
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());

	int result = partbussPlanDao.updateInfo(entity);
	if (result > 0) {
	    OutbranchPlanEntity entity2 = new OutbranchPlanEntity();
	    // 设置 快递代理运价方案ID
	    entity2.setExpressPartbussPlanId(entity.getId());
	    // 设置 更新组织
	    entity2.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    entity2.setModifyUser(entity.getModifyUser());

	    return outbranchPlanService.immediatelyStop(entity2);
	} else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 根据传入的方案ID 激活快递代理公司运价方案
     * </p>
     * .
     * 
     * @param idList
     *            id集合
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午4:04:45
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#activateList(java.util.List)
     */
    @Override
    public int activateList(List<String> idList) {
	if (CollectionUtils.isNotEmpty(idList)) {
	    for (String id : idList) {
		PartbussPlanEntity entity = partbussPlanDao.queryInfoById(id);
		if (null == entity) {
		    throw new PartbussPlanException("ID为：" + id
			    + " 的快递代理公司运价方案不存在!");
		} else {
		    if (entity.getBeginTime().before(
			    new Date(System.currentTimeMillis()))) {
			throw new PartbussPlanException(
				"当前营业日期必须小于生效日期,才能被激活,请重新修改草稿！");
		    }
		    PartbussPlanEntity entity2 = new PartbussPlanEntity();
		    entity2.setExpressPartbussCode(entity
			    .getExpressPartbussCode());
		    entity2.setActive(FossConstants.ACTIVE);
		    entity2.setBillDate(entity.getBeginTime());

		    List<PartbussPlanEntity> list = queryInfos(entity2,
			    Integer.MAX_VALUE, NumberConstants.ZERO);
		    if (CollectionUtils.isNotEmpty(list)) {
			throw new PartbussPlanException("该快递代理公司已经存在激活的运价方案！");
		    }
		    // 激活快递代理公司运价方案
		    activate(entity);
		}
	    }
	    return FossConstants.SUCCESS;
	}
	return FossConstants.FAILURE;
    }

    /**
     * <p>
     * 根据ID查询快递代理公司运价方案
     * </p>
     * .
     * 
     * @param id
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:33:30
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#queryInfoById(java.lang.String)
     */
    @Override
    public PartbussPlanEntity queryInfoById(String id) {
	if (StringUtils.isBlank(id)) {
	    return null;
	}
	return convertInfo(partbussPlanDao.queryInfoById(id));
    }

    /**
     * <p>
     * 根据ID复制快递代理运价方案
     * </p>
     * .
     * 
     * @param id
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午10:50:25
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#copyInfoById(java.lang.String)
     */
    @Transactional
    @Override
    public PartbussPlanEntity copyInfoById(String id) {
	if (StringUtils.isBlank(id)) {
	    return null;
	}
	// 根据ID查询快递代理公司运价方案，并复制一条运价方案
	PartbussPlanEntity entity = partbussPlanDao.queryInfoById(id);
	entity = addInfo(entity);
	OutbranchPlanEntity entity2 = new OutbranchPlanEntity();
	entity2.setExpressPartbussPlanId(id);
	// 根据传入对象查询符合条件所有快递代理网点运价方案信息
	List<OutbranchPlanEntity> entityList = outbranchPlanService.queryInfos(
		entity2, Integer.MAX_VALUE, NumberConstants.ZERO);
	if (CollectionUtils.isNotEmpty(entityList)) {
	    for (OutbranchPlanEntity entity3 : entityList) {
		String idString = entity3.getId();
		// 设置新的快递代理公司ID
		entity3.setExpressPartbussPlanId(entity.getId());
		// 复制网点运价方案
		OutbranchPlanEntity entity4 = outbranchPlanService
			.addInfo(entity3);
		OubrPlanDetailEntity entity5 = new OubrPlanDetailEntity();
		// 设置 快递代理网点运价方案ID.
		entity5.setExpressOutbranchPlanId(idString);
		List<OubrPlanDetailEntity> detailList = oubrPlanDetailService
			.queryInfos(entity5, Integer.MAX_VALUE,
				NumberConstants.ZERO);
		if (CollectionUtils.isNotEmpty(detailList)) {
		    for (OubrPlanDetailEntity detailEntity : detailList) {
			detailEntity.setExpressOutbranchPlanId(entity4.getId());
			// 复制网点运价方案明细
			oubrPlanDetailService.addInfo(detailEntity);
		    }
		}
	    }
	}

	return convertInfo(entity);
    }

    /**
     * <p>
     * 封装创建人名称、创建部门名称、
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午1:56:35
     * @param entity
     * @return
     * @see
     */
    private PartbussPlanEntity convertInfo(PartbussPlanEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    if (StringUtils.isNotBlank(entity.getLoadOrgCode())) {
		// 获取配载部门
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
			.queryOrgAdministrativeInfoByCode(entity
				.getLoadOrgCode());
		if (null != orgEntity) {
		    // 设置 配载部门名称.
		    entity.setLoadOrgName(orgEntity.getName());
		}
	    }
	    if(StringUtil.isNotEmpty(entity.getCreateUser())){
	    	EmployeeEntity employee = employeeService
	    		    .queryEmployeeByEmpCode(entity.getCreateUser());
	    	if (null != employee) {
	    		// 设置 创建人名称.
	    		entity.setCreateUserName(employee.getEmpName());
	    	    }
	    }
	    if(StringUtil.isNotEmpty(entity.getModifyUser())){
	    	EmployeeEntity modifyEmployee = employeeService
	    		    .queryEmployeeByEmpCode(entity.getModifyUser());
	    	if (null != modifyEmployee) {
	    		// 设置 修改人.
	    		entity.setModifyUserName(modifyEmployee.getEmpName());
	    	    }
	    }	    
	    // 根据快递代理公司编码查询代理公司信息
	    BusinessPartnerExpressEntity expressEntity = ldpAgencyCompanyService
		    .queryEntityByCode(entity.getExpressPartbussCode(),
			    FossConstants.ACTIVE);

	    
	    
	    if (null != expressEntity) {
		// 设置 快递代理公司名称.
		entity.setExpressPartbussName(expressEntity
			.getAgentCompanyName());
	    }

	    return entity;
	}
    }

    /**
     * <p>
     * 批量填充信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午2:34:28
     * @param list
     * @return
     * @see
     */
    private List<PartbussPlanEntity> convertInfoList(
	    List<PartbussPlanEntity> list) {
	if (CollectionUtils.isNotEmpty(list)) {
	    List<PartbussPlanEntity> entityList = new ArrayList<PartbussPlanEntity>();
	    for (PartbussPlanEntity entity : list) {
		entityList.add(convertInfo(entity));
	    }
	    return entityList;
	}
	return null;
    }
    
    /**
     * <p>批量保存快递代理运价方案(导入数据用)</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-10-19 下午4:38:45
     * @param list
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService#batchSave(java.util.List)
     */
    @Transactional
    @Override
    public int batchSave(List<PartbussPlanEntity> list) {
	if(CollectionUtils.isEmpty(list)){
	    return FossConstants.FAILURE;
	}
	UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	for(PartbussPlanEntity entity : list){
	    //生成快递代理运价编号
	    String priceNo = generateAfterCode();
	    entity.setPriceNo(priceNo);
	    BusinessPartnerExpressEntity partEntity =  ldpAgencyCompanyService.queryEntityByName(entity.getExpressPartbussName(),FossConstants.ACTIVE);
	    if(null != partEntity){
		//设置 快递代理公司
		entity.setExpressPartbussCode(partEntity.getAgentCompanyCode());
	    }else {
		throw new PartbussPlanException(entity.getExpressPartbussName()+" 在快递代理公司中不存在！");
	    }
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    //新增快递代理公司运价方案.
	    PartbussPlanEntity entity4 = addInfo(entity);
	    //获取 快递代理网点运价方案实体类集合
	    List<OutbranchPlanEntity> list2 = entity.getEntityList();
	    if(CollectionUtils.isNotEmpty(list2)){
		for(OutbranchPlanEntity entity2 : list2){
		    String outerBranchName = entity2.getOuterBranchName();
		    if(StringUtils.isNotBlank(outerBranchName)){
			OuterBranchExpressEntity entity3 = ldpAgencyDeptService.queryLdpAgencyDeptIsExistsByName(outerBranchName);
			if(null != entity3){
			    //设置 快递代理公司网点CODE.
			    entity2.setOuterBranchCode(entity3.getAgentDeptCode());
			    //设置 快递代理运价方案ID.
			    entity2.setExpressPartbussPlanId(entity4.getId());
			}else {
			    throw new PartbussPlanException(outerBranchName+" 在快递代理网点中不存在！");
			}
		    }else {
			throw new PartbussPlanException("快递代理网点名称不允许为空！");
		    }
		    entity2.setCreateUser(userCode);
		    entity2.setModifyUser(userCode);
		    entity2.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		    entity2.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
		    entity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		    //新增快递代理网点运价方案
		    OutbranchPlanEntity outbranchPlanEntity = outbranchPlanService.addInfo(entity2);
		    //获取 价格明细.
		    List<OubrPlanDetailEntity> priceDetail = entity2.getPriceDetail();
		    if(CollectionUtils.isNotEmpty(priceDetail)){
			for(OubrPlanDetailEntity detailEntity : priceDetail){
			    //设置快递代理网点运价方案ＩＤ
			    detailEntity.setExpressOutbranchPlanId(outbranchPlanEntity.getId());
			    detailEntity.setCreateUser(userCode);
			    detailEntity.setModifyUser(userCode);
			    detailEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
			    detailEntity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
			    detailEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			    //新增快递代理网点运价方案明细明细 
			    oubrPlanDetailService.addInfo(detailEntity);
			}
		    }
		}
	    }
	    
	}
	
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>生成快递代理运价编号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-10-21 上午10:14:05
     * @param code
     * @return
     * @see
     */
    private String generateAfterCode() {
	// 根据集中接送货大区生成的前五位编码模糊查询集中接送货大区 按区域编码降序排序
	String priceNo = partbussPlanDao.queryMaxPriceNo();
	String preCode = "YJLDP";
	// 默认生成编码从"0001"开始
	String afterCode = "00001";
	if (StringUtils.isNotBlank(priceNo)) {
	    // 获取编码的后五位编码
	    afterCode = priceNo.substring(priceNo.length() - NumberConstants.NUMBER_5,
		    priceNo.length());
	    // 把字符串转化成数字
	    int intCode = Integer.parseInt(afterCode);
	    // 数字加1
	    intCode = intCode + 1;
	    String strCode = String.valueOf(intCode);
	    if (strCode.length() == NumberConstants.NUMERAL_ONE) {
		afterCode = "0000" + strCode;
	    } else if (strCode.length() == NumberConstants.NUMERAL_TWO) {
		afterCode = "000" + strCode;
	    } else if (strCode.length() == NumberConstants.NUMERAL_THREE){
		afterCode = "00" + strCode;
	    }else if(strCode.length() == NumberConstants.NUMERAL_FOUR){
		afterCode = "0" + strCode;
	    }else {
		afterCode = strCode;
	    }
	    return preCode+afterCode;
	}else {
	    return preCode+afterCode;
	}
    }

}
