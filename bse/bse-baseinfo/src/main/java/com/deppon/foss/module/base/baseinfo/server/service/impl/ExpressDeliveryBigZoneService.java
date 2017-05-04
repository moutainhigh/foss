package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryZoneException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressDeliveryBigZoneService implements IExpressDeliveryBigZoneService {
	 /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDeliveryBigZoneService.class);
   
    /**
     * 快递收派大区接口.
     */
    private IExpressDeliveryBigZoneDao expressDeliveryBigZoneDao;
    /**
     * 快递收派小区service接口.
     */
    private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
     
     public void setExpressDeliveryBigZoneDao(
			IExpressDeliveryBigZoneDao expressDeliveryBigZoneDao) {
		this.expressDeliveryBigZoneDao = expressDeliveryBigZoneDao;
	}
    public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}
     
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
     
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }
    
    /**
	 * 新增快递收派大区
	 * @param entity
	 * @param addList
	 * @param deleteList
	 * @return
	 * @param @param entity
	 * @param @param addList
	 * @param @param deleteList
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:58
	 */
    @Transactional
    @Override
    public int addExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity,List<String> addList, List<String> deleteList)throws ExpressDeliveryZoneException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setActive(FossConstants.ACTIVE);
	// 记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 新增时虚拟编码与第一次新增记录的ID相同
	entity.setVirtualCode(entity.getId());
	if(StringUtil.isEmpty(entity.getManagement())){
		throw new ExpressDeliveryZoneException("管理部门部门不能为空！");
	}
	ExpressDeliveryBigZoneEntity managerMentEntity=expressDeliveryBigZoneDao.queryBigzoneByManageMent(entity.getManagement());
	if(null!=managerMentEntity){
		throw new ExpressDeliveryZoneException("该管理部门已经创建了快递收派大区！");
	}
	List<ExpressDeliveryBigZoneEntity> managerMentEntityName=expressDeliveryBigZoneDao.queryBigzoneByName(entity.getRegionName());
	if(managerMentEntityName.size()>0){
		
		throw new ExpressDeliveryZoneException("该快递收派大区名称已经存在！");
	}
	int result = expressDeliveryBigZoneDao.addExpressDeliveryBigZone(entity);
	if (result > 0) {
	    if (CollectionUtils.isNotEmpty(addList)) {// 添加小区虚拟编码集合不为空
		for (String virtualCode : addList) {
		    // 根据大区编码生成小区编码
//		    String smallRegionCode = expressDeliverySmallZoneService.generateRegionCode(entity.getRegionCode());
		    ExpressDeliverySmallZoneEntity smallZone = new ExpressDeliverySmallZoneEntity();
		    // 设置小区区域编码
//		    smallZone.setRegionCode(smallRegionCode);
		    // 设置所属大区虚拟编码
		    smallZone.setBigzonecode(entity.getVirtualCode());
		    // 设置修改日期
		    smallZone.setModifyDate(date);
		    smallZone.setModifyUser("system");
		    smallZone.setVirtualCode(virtualCode);
		    expressDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
		}
	    }
	    if (CollectionUtils.isNotEmpty(deleteList)) {// 删除小区虚拟编码集合不为空
		for (String virtualCode : deleteList) {
			ExpressDeliverySmallZoneEntity smallZone = new ExpressDeliverySmallZoneEntity();
		    // 设置小区区域编码
//		    smallZone.setRegionCode("");
		    // 设置所属大区虚拟编码
		    smallZone.setBigzonecode("");
		    // 设置修改日期
		    smallZone.setModifyDate(date);
		    smallZone.setModifyUser("system");
		    smallZone.setVirtualCode(virtualCode);
		    expressDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
		}
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
	 * 根据code作废快递收派大区信息------
	 * @param codeStr
	 * @param modifyUser
	 * @return
	 * @param @param codeStr
	 * @param @param modifyUser
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:36:19
	 */
    @Transactional
    @Override
    public int deleteExpressDeliveryBigZoneByCode(String codeStr,String modifyUser) throws ExpressDeliveryZoneException {
	if (StringUtil.isBlank(codeStr)) {
	    throw new ExpressDeliveryZoneException("快递派送大区虚拟编码不允许为空！");
	} else {
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    LOGGER.debug("codeStr: " + codeStr);
	    int result = expressDeliveryBigZoneDao.deleteExpressDeliveryBigZoneByCode(codes,modifyUser);
	    
	    if(result > 0){
		for(String bigvirtualCode : codes){
		    
			ExpressDeliverySmallZoneEntity smallZone = new ExpressDeliverySmallZoneEntity();
		    // 设置小区区域编码
//		    smallZone.setRegionCode("");
		    // 设置所属大区虚拟编码
//		    smallZone.setBigzonecode("");
		    // 设置修改日期
		    smallZone.setModifyDate(new Date());
		    smallZone.setModifyUser("system");
		    //设置小区所属大区虚拟编码
		    smallZone.setBigzonecode(bigvirtualCode);
		    //设置小区所属大区为空
		    smallZone.setBigzonecode1("");
		    
		    expressDeliverySmallZoneService.updateSmallZoneByBigCode(smallZone);
		}
		
		return FossConstants.SUCCESS;
		
	    }else {
		return FossConstants.FAILURE;
	    }
	    
	}
    }
    
    /**
	 * 修改快递收派大区信息
	 * @param entity
	 * @param addList
	 * @param deleteList
	 * @return
	 * @param @param entity
	 * @param @param addList
	 * @param @param deleteList
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:49
	 */
    @Transactional
    @Override
    public int updateExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity,List<String> addList, List<String> deleteList) throws ExpressDeliveryZoneException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new ExpressDeliveryZoneException("快递派送大区虚拟编码不允许为空！");
	}
	ExpressDeliveryBigZoneEntity bigZoneEntity=expressDeliveryBigZoneDao.queryBigzoneByVirtualCode(entity.getVirtualCode());
	if(!StringUtil.equals(bigZoneEntity.getRegionName(), entity.getRegionName())){
		List<ExpressDeliveryBigZoneEntity>  bigZoneEntities=expressDeliveryBigZoneDao.queryBigzoneByName(entity.getRegionName());
		if(bigZoneEntities.size()>0){
			 throw new ExpressDeliveryZoneException("快递派送大区名称不能重复！");
		}
	}
	
	
	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };
	// 作废历史记录
	int flag = expressDeliveryBigZoneDao.deleteExpressDeliveryBigZoneByCode(codes,entity.getModifyUser());
	if (flag > 0) {
	    Date date = new Date();
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(date);
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    int result = expressDeliveryBigZoneDao.addExpressDeliveryBigZone(entity);
	    if (result > 0) {
		if (CollectionUtils.isNotEmpty(addList)) {// 添加小区虚拟编码集合不为空
		    for (String virtualCode : addList) {
		    	ExpressDeliverySmallZoneEntity smallZone = new ExpressDeliverySmallZoneEntity();
			
			// 设置所属大区虚拟编码
			smallZone.setBigzonecode(entity.getVirtualCode());
			// 设置修改日期
			smallZone.setModifyDate(date);
			smallZone.setModifyUser("system");
			smallZone.setVirtualCode(virtualCode);
			expressDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
		    }
		}
		if (CollectionUtils.isNotEmpty(deleteList)) {// 删除小区虚拟编码集合不为空
		    for (String virtualCode : deleteList) {
		    	ExpressDeliverySmallZoneEntity smallZone = new ExpressDeliverySmallZoneEntity();
				// 设置小区区域编码
//				smallZone.setRegionCode("");
				// 设置所属大区虚拟编码
				smallZone.setBigzonecode("");
				// 设置修改日期
				smallZone.setModifyDate(date);
				smallZone.setModifyUser("system");
				smallZone.setVirtualCode(virtualCode);
				expressDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
		    }
		}
	    }
	    return FossConstants.SUCCESS;
	}
	return FossConstants.FAILURE;
    }
    /**
     * 根据用户所输入的条件查询快递收派大区的详细信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:38
	 */
    @Transactional
    @Override
    public List<ExpressDeliveryBigZoneEntity> queryExpressDeliveryBigZones(ExpressDeliveryBigZoneEntity entity, int limit, int start) throws ExpressDeliveryZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return convertInfoList(expressDeliveryBigZoneDao.queryExpressDeliveryBigZones(entity, limit, start));
    }
    
    /**
	 * 根据传入对象查询符合条件所有快递收派大区信息 同事 模糊查询 按照 大区 code和name
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:27
	 */
    @Override
    public List<ExpressDeliveryBigZoneEntity> queryBigZonesByNameOrCode(ExpressDeliveryBigZoneEntity entity)throws ExpressDeliveryZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return convertInfoList(expressDeliveryBigZoneDao.queryBigZonesByNameOrCode(entity));
    }

    /**
	 * 统计总记录数
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:15
	 */
    @Transactional
    @Override
    public Long queryRecordCount(ExpressDeliveryBigZoneEntity entity) throws ExpressDeliveryZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return expressDeliveryBigZoneDao.queryRecordCount(entity);
    }


    /**
	 * 根据区域编码查询快递收派大区详细信息
	 * @param regionCode
	 * @return
	 * @param @param regionCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:06
	 */
    @Override
    public ExpressDeliveryBigZoneEntity queryBigzoneByCode(String regionCode) throws ExpressDeliveryZoneException {
	if (StringUtil.isBlank(regionCode)) {
	    throw new ExpressDeliveryZoneException("区域编码不允许为空！");
	}
	return convertInfo(expressDeliveryBigZoneDao.queryBigzoneByCode(regionCode));
    }


    /**
	 * 根据大区虚拟编码查询快递收派大区详细信息
	 * @param virtualCode
	 * @return
	 * @param @param virtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:47:55
	 */
    @Override
    public ExpressDeliveryBigZoneEntity queryBigzoneByVirtualCode(String virtualCode) {
	if (StringUtil.isBlank(virtualCode)) {
	    throw new ExpressDeliveryZoneException("快递收派大区虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("virtualCode: " + virtualCode);
	    return convertInfo(expressDeliveryBigZoneDao.queryBigzoneByVirtualCode(virtualCode));
	}
    }
   
    /**
	 * 根据省份、城市、区县编码获取名称然后进行封装
	 * @param virtualCode
	 * @return
	 * @param @param virtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:47:55
	 */
    private ExpressDeliveryBigZoneEntity convertInfo(ExpressDeliveryBigZoneEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getManagement());
	     
	    if (null != org) {
		// 设置管理部门名称
		entity.setManagementName(org.getName());
	    } else {
		entity.setManagementName(null);
	    }
	    // 设置省份名称
	    entity.setProvName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getProvCode()));
	    // 设置城市名称
	    entity.setCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCityCode()));
	    // 设置区县名称
	    entity.setCountyName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCountyCode()));

	    return entity;
	}
    }
   /**
    * 对查询出的快递收派大区的信息进行封装
    * @param list
    * @return
    * @param @param list
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午1:39:23
    */
    private List<ExpressDeliveryBigZoneEntity> convertInfoList(List<ExpressDeliveryBigZoneEntity> list) {
	List<ExpressDeliveryBigZoneEntity> entities = new ArrayList<ExpressDeliveryBigZoneEntity>();
	if (CollectionUtils.isNotEmpty(list)) {
	    for (ExpressDeliveryBigZoneEntity entity : list) {
		entity = convertInfo(entity);
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }
    /**
     * 根据管理部门编码生成大区编码 规则：D+管理部门编码
     * @param orgCode
     * @return
     * @param @param orgCode
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:39:23
     */
    @Override
    public String generateCode(String orgCode) throws BusinessException {
//    	if(StringUtil.isEmpty(orgCode)){
//    		throw new ExpressDeliveryZoneException("该大区的编码不能为空！");
//    	}
	// 上海事业部-上海车队
	StringBuffer basicCode = new StringBuffer();
	
	basicCode.append("D");
	basicCode.append(orgCode);
	String regionCode=basicCode.toString();
	/*ExpressDeliveryBigZoneEntity entity=expressDeliveryBigZoneDao.queryBigzoneByCode(regionCode);
	if(null!=entity){
		throw new ExpressDeliveryZoneException("该部门已经创建了快递派送部大区！");
	}*/
	return regionCode;
    }
   
    
    /**
     * 根据用户输入的条件导出快递收派大区信息
     * @param entity
     * @return
     * @throws ExpressDeliveryZoneException
     * @param @param entity
     * @param @return
     * @param @throws ExpressDeliveryZoneException
     * @author 130134
     * @date 2014-4-16 下午1:47:35
     */
    @Override
    public ExportResource exportBigZoneList(ExpressDeliveryBigZoneEntity entity)throws ExpressDeliveryZoneException {
	//参数验证
	if(null == entity){
	    //返回空值
	    return null;
	}
	//设置有效
	entity.setActive(FossConstants.ACTIVE);
	//根据传入对象查询符合条件所有快递收派小区信息.
	List<ExpressDeliveryBigZoneEntity> list = queryExpressDeliveryBigZones(entity, Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
	//集合验证
	if (null == list) {
	    //定义一个集合
	    list = new ArrayList<ExpressDeliveryBigZoneEntity>();
	}
	//定义集合
	List<List<String>> resultList = new ArrayList<List<String>>();
	//迭代循环
	for(ExpressDeliveryBigZoneEntity smallZone : list){
	    //实体信息封装到集合中
	    List<String> result = exportInfoList(smallZone);
	    //存放到集合
	    resultList.add(result);
	}
	//导出对象创建
	ExportResource sheet = new ExportResource();
	//设置Excel表头
	sheet.setHeads(ComnConst.EXPRESS_BIGZONE_TITLE);
	//设置导出数据
	sheet.setRowList(resultList);
	return sheet;
    }
    
    /**
     * 对导出的excle进行表头的封装
     * @param entity
     * @return
     * @param @param entity
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:47:35
     */
    private List<String> exportInfoList(ExpressDeliveryBigZoneEntity entity){
    	//定义一个机会
    	List<String> result = new ArrayList<String>();
    	//添加小区编码.
    	result.add(entity.getRegionCode());
    	//小区名称.
    	result.add(entity.getRegionName());
    	//管理部门名称.
    	result.add(entity.getManagementName());

    	return result;
        }

}
