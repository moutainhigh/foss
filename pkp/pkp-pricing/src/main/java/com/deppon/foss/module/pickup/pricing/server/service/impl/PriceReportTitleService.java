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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PriceReportTitleException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 汽运价格报表表头信息Service接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-10 上午9:42:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-10 上午9:42:19
 * @since
 * @version
 */
public class PriceReportTitleService implements IPriceReportTitleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceReportTitleService.class);
    
    /**
     * 汽运价格报表表头信息DAO接口.
     */
    private IPriceReportTitleDao priceReportTitleDao;
    
    /**
     * 用户接口
     */
    private IUserService userService;
    
    /**
     * 设置 汽运价格报表表头信息DAO接口.
     *
     * @param priceReportTitleDao the priceReportTitleDao to set
     */
    public void setPriceReportTitleDao(IPriceReportTitleDao priceReportTitleDao) {
        this.priceReportTitleDao = priceReportTitleDao;
    }
    
    /**
     * @param userService the userService to set
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    /**
     * 新增汽运价格报表表头信息.
     *
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 上午8:49:48
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity)
     */
    @Override
    public int addInfo(PriceReportTitleEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	// 设置下载版本号
	entity.setVersionNo(date.getTime());
	LOGGER.debug("versionNo:" + entity.getVersionNo());
	entity.setModifyDate(date);
	// 第一次记录新增时，虚拟编码为记录的id
	entity.setActive(FossConstants.ACTIVE);
	return priceReportTitleDao.addInfo(entity);
    }

    /**
     * 根据code作废汽运价格报表表头信息.
     *
     * @param codes ID字符串集合
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#deleteInfo(java.util.List, java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	if(CollectionUtils.isEmpty(codes)){
	    throw new PriceReportTitleException("汽运价格报表表头信息ID不允许为空！");
	}
	return priceReportTitleDao.deleteInfo(codes, modifyUser);
    }

    /**
     * 修改汽运价格报表表头信息.
     *
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity)
     */
    @Transactional
    @Override
    public int updateInfo(PriceReportTitleEntity entity) {
	if(null == entity){
	    throw new PriceReportTitleException("传入的参数不允许为空！");
	}
	if(StringUtils.isBlank(entity.getId())){
	    throw new PriceReportTitleException("汽运价格报表表头信息ID不允许为空！");
	}
	List<String> list = new ArrayList<String>();
	list.add(entity.getId());
	int result = deleteInfo(list, entity.getModifyUser());
	if(result > 0){
	    return addInfo(entity);
	}else {
	    return FossConstants.FAILURE;
	}
    }

    /**
     * 根据传入对象查询符合条件所有汽运价格报表表头信息信息.
     *
     * @param entity 
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity, int, int)
     */
    @Override
    public List<PriceReportTitleEntity> queryInfos(
	    PriceReportTitleEntity entity, int limit, int start) {
	entity.setActive(FossConstants.ACTIVE);
//	entity.setIsShow(FossConstants.ACTIVE);
	
	return convertInfoList(priceReportTitleDao.queryInfos(entity, limit, start));
    }
    
    /**
     * <p>按序号从小到大查询所有可以显示的汽运价格表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-13 上午10:25:39
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#queryAllInfos()
     */
    @Override
    public List<PriceReportTitleEntity> queryAllInfos() {
	PriceReportTitleEntity entity = new PriceReportTitleEntity();
	entity.setIsShow(FossConstants.ACTIVE);
	
        return queryInfos(entity, Integer.MAX_VALUE, NumberConstants.ZERO);
    }
    
    //===============增加合伙人判定/20160920/lianhe/开始======================================
	
	/**
	 * <p>按序号从小到大查询所有可以显示的汽运价格表表头信息(新增合伙人判定标记)</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月20日 下午3:06:01
	 * @param isPartner
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#queryAllInfos(java.lang.String)
	 */
	@Override
	public List<PriceReportTitleEntity> queryAllInfos(String isPartner) {
		PriceReportTitleEntity entity = new PriceReportTitleEntity();
		entity.setIsShow(FossConstants.ACTIVE);
		entity.setIsPartner(isPartner);
        return queryInfos(entity, Integer.MAX_VALUE, NumberConstants.ZERO);
	}
	//===============增加合伙人判定/20160920/lianhe/截止======================================
    

    /**
     * 统计总记录数.
     *
     * @param entity 汽运价格报表表头信息实体
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity)
     */
    @Override
    public Long queryRecordCount(PriceReportTitleEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
//	entity.setIsShow(FossConstants.ACTIVE);
	return priceReportTitleDao.queryRecordCount(entity);
    }

    /**
     * <p>根据ID查询汽运价格报表表头信息</p>.
     *
     * @param id 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午5:07:43
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService#queryInfoById(java.lang.String)
     */
    @Override
    public PriceReportTitleEntity queryInfoById(String id) {
	if(StringUtils.isEmpty(id)){
	    throw new PriceReportTitleException("汽运价格报表表头信息ID不允许为空!");
	}
	return convertInfo(priceReportTitleDao.queryInfoById(id));
    }
    
    /**
     * <p>封装修改人名称</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-13 上午9:02:45
     * @param entity
     * @return
     * @see
     */
    private PriceReportTitleEntity convertInfo(PriceReportTitleEntity entity){
	if(null == entity){
	    return null;
	}
	if(StringUtils.isNotBlank(entity.getModifyUser())){
	    UserEntity userEntity = userService.queryUserByEmpCode(entity.getModifyUser());
	    if(null != userEntity){
		entity.setModifyUserName(userEntity.getEmpName());
	    }
	}
	if(StringUtils.isNotBlank(entity.getCreateUser())){
	    UserEntity userEntity = userService.queryUserByEmpCode(entity.getCreateUser());
	    if(null != userEntity){
		entity.setCreateUserName(userEntity.getEmpName());
	    }
	}
	
	return entity;
    }
    
    /**
     * <p>批量封装修改人名称</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-13 上午9:15:51
     * @param list
     * @return
     * @see
     */
    private List<PriceReportTitleEntity> convertInfoList(List<PriceReportTitleEntity> list) {
   	//定义集合
   	List<PriceReportTitleEntity> entities = new ArrayList<PriceReportTitleEntity>();
   	//集合验证
   	if (CollectionUtils.isNotEmpty(list)) {
   	    //迭代循环
   	    for (PriceReportTitleEntity entity : list) {
   		entity = convertInfo(entity);
   		//存放到集合
   		entities.add(entity);
   	    }
   	    //返回信息
   	    return entities;
   	} else {
   	    return null;
   	}
       }

}
