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

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOubrPlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.OubrPlanDetailException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理网点运价方案明细明细Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 上午11:33:01
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 上午11:33:01
 * @since
 * @version
 */
public class OubrPlanDetailService implements IOubrPlanDetailService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OubrPlanDetailService.class);

    /**
     * 快递代理网点运价方案明细明细Dao接口.
     */
    private IOubrPlanDetailDao oubrPlanDetailDao;

    /**
     * 设置 快递代理网点运价方案明细明细Dao接口.
     * 
     * @param oubrPlanDetailDao
     *            the oubrPlanDetailDao to set
     */
    public void setOubrPlanDetailDao(IOubrPlanDetailDao oubrPlanDetailDao) {
	this.oubrPlanDetailDao = oubrPlanDetailDao;
    }

    /**
     * 新增快递代理网点运价方案明细明细.
     * 
     * @param entity
     *            快递代理网点运价方案明细明细实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int addInfo(OubrPlanDetailEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if(entity.getLeftrange().compareTo(entity.getRightrange()) >= NumberConstants.ZERO){
	    throw new OubrPlanDetailException("重量下限必须小于重量上限！");
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	LOGGER.debug("versionNo:" + entity.getVersionNo());
	entity.setModifyDate(date);
	entity.setActive(FossConstants.INACTIVE);
	OubrPlanDetailEntity entity2 = queryMaxInfoByParentId(entity.getExpressOutbranchPlanId());
	if(null != entity2){
	    if(entity.getLeftrange().compareTo(entity2.getRightrange()) != NumberConstants.ZERO){
		throw new OubrPlanDetailException("数据不合法，重量下限必须为："+ entity2.getRightrange());
	    }
	}

	return oubrPlanDetailDao.addInfo(entity);
    }

    /**
     * 根据code作废快递代理网点运价方案明细.
     * 
     * @param codes
     *            ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#deleteInfo(java.lang.String[],
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	if (CollectionUtils.isEmpty(codes)) {
	    throw new OubrPlanDetailException("传入参数ID不允许为空！");
	}else {
	    for(String id : codes){
		//根据ID查询快递代理网点运价方案明细
		OubrPlanDetailEntity entity = queryinfoById(id);
		OubrPlanDetailEntity entity2 = new OubrPlanDetailEntity();
		entity2.setExpressOutbranchPlanId(entity.getExpressOutbranchPlanId());
		entity2.setLeftrange(entity.getRightrange());
		List<OubrPlanDetailEntity> list = queryInfos(entity2, Integer.MAX_VALUE, NumberConstants.ZERO);
		
		OubrPlanDetailEntity entity3 = new OubrPlanDetailEntity();
		entity3.setExpressOutbranchPlanId(entity.getExpressOutbranchPlanId());
		entity3.setRightrange(entity.getLeftrange());
		List<OubrPlanDetailEntity> list2 = queryInfos(entity3, Integer.MAX_VALUE, NumberConstants.ZERO);
		
		if(CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(list2)){
		    throw new OubrPlanDetailException("重量下限为： "+ entity.getLeftrange() + " 重量上限为： "+entity.getRightrange() + " 的方案明细删除后会导致重量区间段不连续，不允许删除!");
		}
	    }
	}

	return oubrPlanDetailDao.deleteInfo(codes, modifyUser);
    }
    
    /**
     * <p>根据父ＩＤ删除快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @param parentcodeList 快递代理网点运价ＩＤ
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#deleteInfoByParentCode(java.util.List)
     */
    @Override
    public int deleteInfoByParentCode(List<String> parentcodeList) {
	if (CollectionUtils.isEmpty(parentcodeList)) {
	    return FossConstants.FAILURE;
	}
	
	return oubrPlanDetailDao.deleteInfoByParentCode(parentcodeList);
    }

    /**
     * 修改快递代理网点运价方案明细.
     * 
     * @param entity
     *            快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int updateInfo(OubrPlanDetailEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if(entity.getLeftrange().compareTo(entity.getRightrange()) >= NumberConstants.ZERO){
	    throw new OubrPlanDetailException("重量下限必须小于重量上限！");
	}
	if (StringUtils.isBlank(entity.getId())) {
	    throw new OubrPlanDetailException("快递代理网点运价方案明细ID不允许为空");
	} else {
	    //根据ID查询快递代理网点运价方案明细
	    OubrPlanDetailEntity entity2 = queryinfoById(entity.getId());
	    if(entity.getLeftrange().compareTo(entity2.getLeftrange()) != NumberConstants.ZERO){
		OubrPlanDetailEntity entity3 = new OubrPlanDetailEntity();
		entity3.setRightrange(entity2.getLeftrange());
		entity3.setExpressOutbranchPlanId(entity2.getExpressOutbranchPlanId());
		List<OubrPlanDetailEntity> list = queryInfos(entity3,
			Integer.MAX_VALUE, NumberConstants.ZERO);
		if (CollectionUtils.isNotEmpty(list)) {
		    throw new OubrPlanDetailException("存在重量下限为："
			    + entity2.getLeftrange() + " 的网点方案明细，不允许修改！");
		}
	    }
	    
	    if(entity.getRightrange().compareTo(entity2.getRightrange()) != NumberConstants.ZERO){
		OubrPlanDetailEntity entity4 = new OubrPlanDetailEntity();
		entity4.setLeftrange(entity2.getRightrange());
		entity4.setExpressOutbranchPlanId(entity2.getExpressOutbranchPlanId());
		List<OubrPlanDetailEntity> list1 = queryInfos(entity4,
			Integer.MAX_VALUE, NumberConstants.ZERO);
		if (CollectionUtils.isNotEmpty(list1)) {
		    throw new OubrPlanDetailException("存在重量上限为："
			    + entity2.getRightrange() + " 的网点方案明细，不允许修改！");
		}
	    }
	    
	    entity.setModifyDate(new Date());
	    return oubrPlanDetailDao.updateInfo(entity);
	}
    }

    /**
     * 根据传入对象查询符合条件所有快递代理网点运价方案明细信息.
     * 
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity,
     *      int, int)
     */
    @Override
    public List<OubrPlanDetailEntity> queryInfos(OubrPlanDetailEntity entity,
	    int limit, int start) {

	return oubrPlanDetailDao.queryInfos(entity, limit, start);
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     *            快递代理网点运价方案明细实体
     * @return
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public Long queryRecordCount(OubrPlanDetailEntity entity) {

	return oubrPlanDetailDao.queryRecordCount(entity);
    }

    /**
     * 激活快递代理网点运价方案明细
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:48:04
     * @param entity
     *            快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#activate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int activate(OubrPlanDetailEntity entity) {
	// 设置修改时间
	entity.setModifyDate(new Date());
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);
	return oubrPlanDetailDao.activate(entity);
    }

    /**
     * <p>
     * 立即激活快递代理网点运价方案明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @param entity
     *            快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#immediatelyActivate(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int immediatelyActivate(OubrPlanDetailEntity entity) {
	// 设置修改时间
	entity.setModifyDate(new Date());
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	// 设置 数据状态.
	entity.setActive(FossConstants.ACTIVE);

	return oubrPlanDetailDao.activate(entity);
    }

    /**
     * <p>
     * 立即中止快递代理网点运价方案明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @param entity
     *            快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#immediatelyStop(com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity)
     */
    @Override
    public int immediatelyStop(OubrPlanDetailEntity entity) {
	// 设置修改时间
	entity.setModifyDate(new Date());
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	
	return oubrPlanDetailDao.activate(entity);
    }
    
    /**
     * <p>根据ID查询快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:45:21
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService#queryinfoById(java.lang.String)
     */
    @Override
    public OubrPlanDetailEntity queryinfoById(String id) {
	if(StringUtils.isBlank(id)){
	    return null;
	}
	return oubrPlanDetailDao.queryinfoById(id);
    }
    
    /**
     * <p>根据父ID查询右区间最大的值快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-1 下午5:57:02
     * @param parentId
     * @return
     * @see
     */
    public OubrPlanDetailEntity queryMaxInfoByParentId(String parentId){
	
	if(StringUtils.isBlank(parentId)){
	    return null;
	}
	List<OubrPlanDetailEntity> list = oubrPlanDetailDao.queryMaxInfoByParentId(parentId);
	
	if(CollectionUtils.isNotEmpty(list)){
	    return list.get(0);
	}
	
	return null;
    }
    

}
