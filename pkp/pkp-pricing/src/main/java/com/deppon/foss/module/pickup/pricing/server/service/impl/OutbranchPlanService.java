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
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOutbranchPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.OutbranchPlanException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 快递代理网点运价方案Service接口实现类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 上午11:32:08
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 上午11:32:08
 * @since
 * @version
 */
public class OutbranchPlanService implements IOutbranchPlanService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OutbranchPlanService.class);

    /**
     * 快递代理网点运价方案Dao接口.
     */
    private IOutbranchPlanDao outbranchPlanDao;

    /**
     * 快递代理网点运价方案明细Service接口.
     */
    private IOubrPlanDetailService oubrPlanDetailService;
    
    /**
     * 快递代理网点运价方案明细Service接口.
     */
    private ILdpAgencyDeptService ldpAgencyDeptService;
    
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    
    
    /**
     * 设置 行政区域接口.
     *
     * @param administrativeRegionsService the administrativeRegionsService to set
     */
    public void setAdministrativeRegionsService(
    	IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }

    /**
     * 设置 快递代理网点运价方案明细Service接口.
     *
     * @param ldpAgencyDeptService the ldpAgencyDeptService to set
     */
    public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
        this.ldpAgencyDeptService = ldpAgencyDeptService;
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
     * 设置 快递代理网点运价方案Dao接口.
     * 
     * @param outbranchPlanDao
     *            the outbranchPlanDao to set
     */
    public void setOutbranchPlanDao(IOutbranchPlanDao outbranchPlanDao) {
	this.outbranchPlanDao = outbranchPlanDao;
    }

    /**
     * 新增快递代理网点运价方案.
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public OutbranchPlanEntity addInfo(OutbranchPlanEntity entity) {
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
	
	OutbranchPlanEntity entity2 = new OutbranchPlanEntity();
	entity2.setExpressPartbussPlanId(entity.getExpressPartbussPlanId());
	entity2.setOuterBranchCode(entity.getOuterBranchCode());
	List<OutbranchPlanEntity> list = outbranchPlanDao.queryInfos(entity2, Integer.MAX_VALUE, NumberConstants.ZERO);
	if(CollectionUtils.isNotEmpty(list)){
	    throw new OutbranchPlanException("同一个网点只能有一个价格！");
	}
	
	int result = outbranchPlanDao.addInfo(entity);
	if(result > 0){
	    return convertInfo(entity);
	}else {
	    return null;
	}
    }

    /**
     * 根据code作废快递代理网点运价方案.
     * 
     * @param codes
     *            ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:32:10
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#deleteInfo(java.lang.String[],
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	if (CollectionUtils.isEmpty(codes)) {
	    throw new OutbranchPlanException("传入参数ID不允许为空！");
	}
	//根据父ＩＤ删除快递代理网点运价方案明细信息
	oubrPlanDetailService.deleteInfoByParentCode(codes);
	outbranchPlanDao.deleteInfo(codes, modifyUser);
	
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据父ＩＤ删除快递代理网点运价方案明细信息</p>.
     *
     * @param parentcodeList 快递代理网点运价ＩＤ
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#deleteInfoByParentCode(java.util.List)
     */
    @Transactional
    @Override
    public int deleteInfoByParentCode(List<String> parentcodeList) {
	if (CollectionUtils.isEmpty(parentcodeList)) {
	    throw new OutbranchPlanException("传入快递代理公司运价方案ID不允许为空！");
	}
	List<String> codeList = new ArrayList<String>();
	for(String parentCode : parentcodeList){
	    OutbranchPlanEntity entity = new OutbranchPlanEntity();
	    //设置 快递代理运价方案ID.
	    entity.setExpressPartbussPlanId(parentCode);
	    List<OutbranchPlanEntity> list = outbranchPlanDao.queryInfos(entity, Integer.MAX_VALUE, NumberConstants.ZERO);
	    if(CollectionUtils.isNotEmpty(list)){
		for(OutbranchPlanEntity entity2 : list){
			codeList.add(entity2.getId());
		}
	    }
	}
	
	//根据父ＩＤ删除快递代理网点运价方案明细信息
	oubrPlanDetailService.deleteInfoByParentCode(codeList);
	//根据父ＩＤ删除快递代理网点运价方案信息
	outbranchPlanDao.deleteInfoByParentCode(parentcodeList);
	 
        return FossConstants.SUCCESS;
    }

    /**
     * 修改快递代理网点运价方案.
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:32:10
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public OutbranchPlanEntity updateInfo(OutbranchPlanEntity entity) {
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getId())) {
	    throw new OutbranchPlanException("修改快递代理网点运价方案 ID不允许为空");
	} else {
	    entity.setModifyDate(new Date());
	    int result = outbranchPlanDao.updateInfo(entity);
	    
	    if(result > 0){
		return convertInfo(entity);
	    }else {
		return null;
	    }
	}
    }

    /**
     * 根据传入对象查询符合条件所有快递代理网点运价方案信息.
     * 
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:32:10
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity,
     *      int, int)
     */
    @Override
    public List<OutbranchPlanEntity> queryInfos(OutbranchPlanEntity entity,
	    int limit, int start) {

	return convertInfoList(outbranchPlanDao.queryInfos(entity, limit, start));
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-7-22 上午11:32:10
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Override
    public Long queryRecordCount(OutbranchPlanEntity entity) {

	return outbranchPlanDao.queryRecordCount(entity);
    }

    /**
     * 激活快递代理网点运价方案.
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:48:04
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#activate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Transactional
    @Override
    public int activate(OutbranchPlanEntity entity) {
	if (null == entity) {
	    throw new OutbranchPlanException("快递代理网点运价方案信息不允许为空！");
	}
	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());
	
	int result = outbranchPlanDao.activate(entity);

	if (result > 0) {
	    OubrPlanDetailEntity oubrPlanDetail = new OubrPlanDetailEntity();
	    // 设置 快递代理网点运价方案ID
	    oubrPlanDetail.setExpressOutbranchPlanId(entity.getId());
	    // 设置 更新组织
	    oubrPlanDetail.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    oubrPlanDetail.setModifyUser(entity.getModifyUser());
	    return oubrPlanDetailService.activate(oubrPlanDetail);
	} else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 立即激活快递代理网点运价方案
     * </p>
     * .
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#immediatelyActivate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Transactional
    @Override
    public int immediatelyActivate(OutbranchPlanEntity entity) {

	if (null == entity) {
	    throw new OutbranchPlanException("快递代理网点运价方案信息不允许为空！");
	}
	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());

	int result = outbranchPlanDao.activate(entity);
	if (result > 0) {
	    OubrPlanDetailEntity oubrPlanDetail = new OubrPlanDetailEntity();
	    // 设置 快递代理网点运价方案ID
	    oubrPlanDetail.setExpressOutbranchPlanId(entity.getId());
	    // 设置 更新组织
	    oubrPlanDetail.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    oubrPlanDetail.setModifyUser(entity.getModifyUser());

	    return oubrPlanDetailService.immediatelyActivate(oubrPlanDetail);
	} else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 立即中止快递代理网点运价方案
     * </p>
     * .
     * 
     * @param entity
     *            快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#immediatelyStop(com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity)
     */
    @Transactional
    @Override
    public int immediatelyStop(OutbranchPlanEntity entity) {
	if (null == entity) {
	    throw new OutbranchPlanException("快递代理网点运价方案信息不允许为空！");
	}
	// 设置 数据版本.
	entity.setVersionNo(System.currentTimeMillis());
	// 设置修改时间
	entity.setModifyDate(new Date());

	int result = outbranchPlanDao.activate(entity);
	
	if (result > 0) {
	    OubrPlanDetailEntity oubrPlanDetail = new OubrPlanDetailEntity();
	    // 设置 快递代理网点运价方案ID
	    oubrPlanDetail.setExpressOutbranchPlanId(entity.getId());
	    // 设置 更新组织
	    oubrPlanDetail.setModifyOrgCode(entity.getModifyOrgCode());
	    // 设置更新人
	    oubrPlanDetail.setModifyUser(entity.getModifyUser());

	    return oubrPlanDetailService.immediatelyStop(oubrPlanDetail);
	} else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * 
     *
     * @param outerBranchCode 
     * @param billDate 
     * @return 
     */
    @Override
    public OutbranchPlanEntity queryPriceByCode(String outerBranchCode,
	    Date billDate) {
	 
	if(StringUtils.isBlank(outerBranchCode)){
	    return null;
	}
	if(billDate==null)
	{
	    billDate=new Date();
	}
	return convertInfo(outbranchPlanDao.queryPriceByCode(outerBranchCode,billDate));
	 
    }
    
    /**
     * <p>根据ID查询快递代理网点运价方案</p>.
     *
     * @param id 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:07:43
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService#queryInfoById(java.lang.String)
     */
    @Override
    public OutbranchPlanEntity queryInfoById(String id) {
	if(StringUtils.isBlank(id)){
	    return null;
	}
	
	return convertInfo(outbranchPlanDao.queryInfoById(id));
    }
    
    
    /**
     * <p>
     * 封装快递代理网点名称、省、市、区县名称、
     * </p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午1:56:35
     * @see
     */
    private OutbranchPlanEntity convertInfo(OutbranchPlanEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    OuterBranchExpressEntity expressEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(entity.getOuterBranchCode(),FossConstants.ACTIVE);
	    
	    if (null != expressEntity) {
		// 设置 快递代理公司网点名称（扩展）.
		entity.setOuterBranchName(expressEntity.getAgentDeptName());
		entity.setProvName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(expressEntity.getProvCode()));
		entity.setCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(expressEntity.getCityCode()));
		entity.setDistrictName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(expressEntity.getCountyCode()));
	    }
	    return entity;
	}
    }

    /**
     * <p>
     * 批量填充信息
     * </p>.
     *
     * @param list 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午2:34:28
     * @see
     */
    private List<OutbranchPlanEntity> convertInfoList(
	    List<OutbranchPlanEntity> list) {
	if (CollectionUtils.isNotEmpty(list)) {
	    List<OutbranchPlanEntity> entityList = new ArrayList<OutbranchPlanEntity>();
	    for (OutbranchPlanEntity entity : list) {
		entityList.add(convertInfo(entity));
	    }
	    return entityList;
	}
	return null;
    }

}


