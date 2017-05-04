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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressBigRegionDistrService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DistrictDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressBigRegionDistrException;
import com.deppon.foss.module.base.baseinfo.server.cache.AdministrativeRegionCacheDeal;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递大区与行政区域映射关系Service接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午2:12:10 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午2:12:10
 * @since
 * @version
 */
public class ExpressBigRegionDistrService implements
	IExpressBigRegionDistrService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressBigRegionDistrService.class);
    
    /**
     * 快递大区与行政区域映射关系DAO接口.
     */
    private IExpressBigRegionDistrDao expressBigRegionDistrDao;
    
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 同步cubc接口
     */
    private ISyncExpressBigRegionDistrService syncExpressBigRegionDistrService;
    
   public void setSyncExpressBigRegionDistrService(
			ISyncExpressBigRegionDistrService syncExpressBigRegionDistrService) {
		this.syncExpressBigRegionDistrService = syncExpressBigRegionDistrService;
	}
	// 此处不用注入
    /** The administrative region cache deal. */
    private AdministrativeRegionCacheDeal administrativeRegionCacheDeal = new AdministrativeRegionCacheDeal();
    
    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    
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
     * 设置 快递大区与行政区域映射关系DAO接口.
     *
     * @param expressBigRegionDistrDao the expressBigRegionDistrDao to set
     */
    public void setExpressBigRegionDistrDao(
    	IExpressBigRegionDistrDao expressBigRegionDistrDao) {
        this.expressBigRegionDistrDao = expressBigRegionDistrDao;
    }

    /**
     * 新增快递大区与行政区域映射关系.
     *
     * @param entity 快递大区与行政区域映射关系明细实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:12:10
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#addInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public int addInfo(ExpressBigRegionDistrEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	// 设置版本号
	entity.setVersionNo(System.currentTimeMillis());
	LOGGER.debug("versionNo:" + entity.getVersionNo());
	entity.setModifyDate(date);
	entity.setActive(FossConstants.ACTIVE);

	return expressBigRegionDistrDao.addInfo(entity);
    }
    
    /**
     * <p>批量保存快递大区与行政区域映射关系</p>.
     *
     * @param list 
     * @param userCode 
     * @return 
     * @author 094463-foss-xieyantao/187862-dujunhui
     * @date 2013-8-15 上午9:32:26
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#batchAddInfos(java.util.List)
     */
    @Transactional
    @Override
    public int batchAddInfos(List<ExpressBigRegionDistrEntity> list,String userCode) {
        if(CollectionUtils.isEmpty(list)){
            throw new ExpressBigRegionDistrException("传入的参数不允许为空！");
        }else {
        	//生成同步信息
       	 List<ExpressBigRegionDistrEntity> sysList = new ArrayList<ExpressBigRegionDistrEntity>();
	    for(ExpressBigRegionDistrEntity entity : list){
		entity.setCreateUser(userCode);
		entity.setModifyUser(userCode);
		if(StringUtils.isNotBlank(entity.getCountyCode())){
		    entity.setDistrictCode(entity.getCountyCode());
		}else {
		    entity.setDistrictCode(entity.getCityCode());
		}
		
		//验证该行政区域是否已经配置
		//根据有效状态和行政区域编码、部门编码查询是否已经配置过
		ExpressBigRegionDistrEntity entityExist = this.queryInfoByDistrictCodeAndActive(entity.getOrgCode(),entity.getDistrictCode(), FossConstants.ACTIVE);
		if(entityExist!=null){
			 String disName = "";
			//根据编码查询
			 AdministrativeRegionsEntity distrEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(entity.getDistrictCode());
			 if(distrEntity != null){
				 disName = distrEntity.getName();
			 }
			throw new BusinessException("行政区域: "+disName+" 与该部门已配置！");
		}
		
		//新增快递大区与行政区域映射关系
		addInfo(entity);
		//加入到带同步接口
		sysList.add(entity);
	    }
	    if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
	    	syncExpressBigRegionDistrService.SyncExpressBigRegionDistr(sysList, "1");
	    }
	}
        
        return FossConstants.SUCCESS;
    }
    
    /**
     * 根据code作废快递大区与行政区域映射关系.
     *
     * @param codes ID字符串集合
     * @param modifyUser 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:12:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#deleteInfo(java.util.List, java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	if (CollectionUtils.isEmpty(codes)) {
	    throw new ExpressBigRegionDistrException("传入参数ID不允许为空！");
	}
    int deletEntity= expressBigRegionDistrDao.deleteInfo(codes, modifyUser);
    List<ExpressBigRegionDistrEntity> syslist=expressBigRegionDistrDao.queryDeletEntityByIds(codes);
    if (deletEntity>0) {
	syncExpressBigRegionDistrService.SyncExpressBigRegionDistr(syslist, "3");	
	}
	return deletEntity;
    }

    /**
     * 修改快递大区与行政区域映射关系.
     *
     * @param entity 快递大区与行政区域映射关系实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:12:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#updateInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public int updateInfo(ExpressBigRegionDistrEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (StringUtils.isBlank(entity.getId())) {
	    throw new ExpressBigRegionDistrException("快递大区与行政区域映射关系ID不允许为空");
	} else {
	    entity.setModifyDate(new Date());
	    return expressBigRegionDistrDao.updateInfo(entity);
	}
    }
    
    /**
     * <p>批量修改快递大区与行政区域映射关系</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午7:04:32
     * @param addList 新增的数据集合
     * @param deleteList 删除的数据集合
     * @param userCode 操作人编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#batchUpdateInfos(java.util.List, java.util.List, java.lang.String)
     */
    @Transactional
    @Override
    public int batchUpdateInfos(List<ExpressBigRegionDistrEntity> addList,List<ExpressBigRegionDistrEntity> deleteList, String userCode) {
        
        if(CollectionUtils.isEmpty(addList) && CollectionUtils.isEmpty(deleteList)){
            throw new ExpressBigRegionDistrException("传入的参数不允许为空！");
        }else {
            if(CollectionUtils.isNotEmpty(addList)){
                //批量保存新增消息
                batchAddInfos(addList, userCode);
            }
            
            if(CollectionUtils.isNotEmpty(deleteList)){
                List<String> codeList = new ArrayList<String>();
                for(ExpressBigRegionDistrEntity entity : deleteList){
            	codeList.add(entity.getId());
                }
                //根据code作废快递大区与行政区域映射关系.
                deleteInfo(codeList, userCode);
            }
            return FossConstants.SUCCESS;
	}
    }
    
    /**
     * <p>根据ID查询快递大区与行政区域映射关系实体</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午4:00:09
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#queryInfoById(java.lang.String)
     */
    @Override
    public ExpressBigRegionDistrEntity queryInfoById(String id) {
        if(StringUtils.isBlank(id)){
            throw new ExpressBigRegionDistrException("传入的ID不允许为空！");
        }
        ExpressBigRegionDistrEntity entity = new ExpressBigRegionDistrEntity();
        entity.setId(id);
        List<ExpressBigRegionDistrEntity> list = queryInfos(entity, Integer.MAX_VALUE, NumberConstants.ZERO);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 根据传入对象查询符合条件所有快递大区与行政区域映射关系信息.
     *
     * @param entity 
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:12:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#queryInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity, int, int)
     */
    @Override
    public List<ExpressBigRegionDistrEntity> queryInfos(
	    ExpressBigRegionDistrEntity entity, int limit, int start) {
	
    	//如果县级市条件存在，地级市条件无效；
    	if(StringUtils.isNotEmpty(entity.getCountyCode())){
    		entity.setDistrictCode(entity.getCountyCode());
    		
    	//通过地级市条件查询	
    	}else{
    		//根据地级市code获取全部先县级市CODE
        	List<String> districtCodeList = this.getDistrictCodeByParentDistrictCode(entity.getCityCode());	
        	entity.setDistrictCodeList(districtCodeList);
    	}
    	
    	entity.setActive(FossConstants.ACTIVE);
    	return convertInfoList(expressBigRegionDistrDao.queryInfos(entity, limit, start));
    }

    /**
     * 统计总记录数.
     *
     * @param entity 快递大区与行政区域映射关系实体
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:12:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity)
     */
    @Override
    public Long queryRecordCount(ExpressBigRegionDistrEntity entity) {
	
    	
    	//如果县级市条件存在，地级市条件无效；
    	if(StringUtils.isNotEmpty(entity.getCountyCode())){
    		entity.setDistrictCode(entity.getCountyCode());
    		
    	//通过地级市条件查询	
    	}else{
    		//根据地级市code获取全部先县级市CODE
        	List<String> districtCodeList = this.getDistrictCodeByParentDistrictCode(entity.getCityCode());	
        	entity.setDistrictCodeList(districtCodeList);
    	}
    	
    	entity.setActive(FossConstants.ACTIVE);
    	return expressBigRegionDistrDao.queryRecordCount(entity);
    }

    /** 
     * <p>获取快递大区所映射行政区域LIST(最小一级，如果行政区域是市，返回市下面所有区\县的列表)</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午2:23:03
     * @param orgCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#queryDistrictDtoListForOrgCode(java.lang.String)
     */
    @Override
    public List<DistrictDto> queryDistrictDtoListForOrgCode(String orgCode) {
	ExpressBigRegionDistrEntity entity=new ExpressBigRegionDistrEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setOrgCode(orgCode);
	int limit=Integer.MAX_VALUE;
	int start=0;
	List<ExpressBigRegionDistrEntity> objectlist = expressBigRegionDistrDao.queryInfos(entity, limit,start);
	if(CollectionUtils.isEmpty(objectlist)){
	    return null;
	}
	
	List<AdministrativeRegionsEntity> allData = administrativeRegionCacheDeal.getAllData();
	
	List<DistrictDto> resultList=new ArrayList<DistrictDto>();
	for (ExpressBigRegionDistrEntity expressBigRegionDistrEntity : objectlist) {
	   String districtCode = expressBigRegionDistrEntity.getDistrictCode();
	   AdministrativeRegionsEntity district=searchRegionByCode(allData,districtCode);
	   if(district!=null)
	   {
	       if (DictionaryValueConstants.DISTRICT_CITY.equals(district.getDegree())){
		   DistrictDto vo=new DistrictDto();
		   resultList.add(vo);
		   vo.setCityCode(district.getCode());	
		   vo.setCityName(district.getName());	
		   String proCode=district.getParentDistrictCode();
		   vo.setProCode(proCode);
		   AdministrativeRegionsEntity parentDis=searchRegionByCode(allData,proCode);
		    if(parentDis!=null){
		    	vo.setProName(parentDis.getName());			
		    }
		    resultList.addAll( getChildAdministrativeRegionsEntity(allData,vo));		    
		   
		     
		    
	        }
	       else if (DictionaryValueConstants.DISTRICT_COUNTY.equals(district.getDegree())){
		   //如果是区县，一层层找上去
		   DistrictDto vo=new DistrictDto();
		   resultList.add(vo);
		   vo.setCountyCode(district.getCode());
		   vo.setCountyName(district.getName());
		   String parentCode=district.getParentDistrictCode();
		   vo.setCityCode(parentCode);		   
		   //找出实体
		   AdministrativeRegionsEntity parentDis=searchRegionByCode(allData,parentCode);
		   if(parentDis!=null)  {
		       vo.setCityName(parentDis.getName()); 		       
		       String grandpCode=parentDis.getParentDistrictCode();		       
		       vo.setProCode(grandpCode);
		       //找出实体
		       AdministrativeRegionsEntity  grandPDis=searchRegionByCode(allData,grandpCode);
		         if(grandPDis!=null) {
		        	 vo.setProName(grandPDis.getName());
		         }
		       
		   }
		 
	       }
	       
	      
	   }
	    
	    
	}
	
	return resultList;
    }
    
    /**
     * 
     *
     * @param allData 
     * @param code 
     * @return 
     */
    private AdministrativeRegionsEntity searchRegionByCode(List<AdministrativeRegionsEntity> allData ,String code) {
	if (StringUtil.isEmpty(code)) {
		return null;
	}
	 
	if (CollectionUtils.isNotEmpty(allData)) {
		for (int i = 0; i < allData.size(); i++) {
			if (code.equals(allData.get(i).getCode())) {
				return allData.get(i);
			}
		}
	}
	return null;
     } 
    
     /**
      * 
      *
      * @param allData 
      * @param parm 
      * @return 
      */
     private List<DistrictDto> getChildAdministrativeRegionsEntity(List<AdministrativeRegionsEntity> allData ,
	     DistrictDto parm) {
	
	List<DistrictDto> targetList = new ArrayList<DistrictDto>();
	// 遍历城市列表，获取传进来省份的城市列表
	for (AdministrativeRegionsEntity c : allData) {
		if (parm.getCityCode().equals(c.getParentDistrictCode())) {
		    DistrictDto vo =new DistrictDto();
		    vo.setCountyCode(c.getCode());
		    vo.setCountyName(c.getName());
		    vo.setProCode(parm.getProCode());
		    vo.setProName(parm.getProName());
		    vo.setCityCode(parm.getCityCode());
		    vo.setCityName(parm.getCityName());
		    targetList.add(vo);
		}
	}
	return targetList;
     }
     
     /**
      * <p>封装部门名称、城市名称</p>.
      *
      * @param entity 
      * @return 
      * @author 094463-foss-xieyantao
      * @date 2013-8-15 下午2:13:47
      * @see
      */
     private ExpressBigRegionDistrEntity convertInfo(ExpressBigRegionDistrEntity entity){
	 if(null == entity){
	     return null;
	 }
	 //根据编码查询
	 OrgAdministrativeInfoEntity orgentity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getOrgCode());
	 if(orgentity != null){
	     //设置 组织名称.
	     entity.setOrgName(orgentity.getName());
	 }
	 //根据编码查询
	 AdministrativeRegionsEntity distrEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(entity.getDistrictCode());
	 if(distrEntity != null){
	     //判断是否区县
	     if(StringUtils.equals(DictionaryValueConstants.DISTRICT_COUNTY, distrEntity.getDegree())){
		 entity.setCountyCode(entity.getDistrictCode());
		 entity.setCountyName(distrEntity.getName());
		 entity.setCityCode(distrEntity.getParentDistrictCode());
		 entity.setCityName(distrEntity.getParentDistrictName());
		 //根据城市编码查询省份编码
		 AdministrativeRegionsEntity entity1 = administrativeRegionsService.queryAdministrativeRegionsByCode(distrEntity.getParentDistrictCode());
		 if(entity1 != null){
		     entity.setProvCode(entity1.getParentDistrictCode());
		     entity.setProvName(entity1.getParentDistrictName());
		 }
		 entity.setDistrictName(entity.getCityName()+ "-" + entity.getCountyName());
	     }else {
		 entity.setCityCode(entity.getDistrictCode());
		 entity.setCityName(distrEntity.getName());
		 entity.setProvCode(distrEntity.getParentDistrictCode());
		 entity.setProvName(distrEntity.getParentDistrictName());
		 entity.setDistrictName(entity.getCityName());
	    }
	 }
	 return entity;
     }
     
     /**
      * <p>批量封装信息</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-8-15 下午3:25:04
      * @param list
      * @return
      * @see
      */
     private List<ExpressBigRegionDistrEntity> convertInfoList(List<ExpressBigRegionDistrEntity> list){
	 if(CollectionUtils.isEmpty(list)){
	     return null;
	 }else {
	     List<ExpressBigRegionDistrEntity> entityList = new ArrayList<ExpressBigRegionDistrEntity>();
	    for(ExpressBigRegionDistrEntity entity : list){
		entityList.add(convertInfo(entity));
	    }
	    return entityList;
	}
     }
     
     /**
     * 根据父行政区域CODE获取所有子行政区域CODE
     * @author foss-qiaolifeng
     * @date 2013-8-22 下午2:26:32
     * @param code
     * @return
     */
    private List<String> getDistrictCodeByParentDistrictCode(String code){
    	
    	//生成返回list
    	List<String> rtnCode = new ArrayList<String>();
    	 if(StringUtils.isEmpty(code)){
    		 return rtnCode;
    	 }
    	 rtnCode.add(code);
    	 
    	 //根据父CODE查询全部子行政区域
    	 List<AdministrativeRegionsEntity> administrativeRegionsEntityList = administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(code); 
    	 if(CollectionUtils.isNotEmpty(administrativeRegionsEntityList)){
    		 	for(AdministrativeRegionsEntity entity:administrativeRegionsEntityList){
    		 		rtnCode.add(entity.getCode());
    		 	}
    	 }
    	 
    	 return rtnCode;
    
    }


	/** 
	 * 根据有效状态、行政区域编码查询快递大区行政区域映射信息
	 * @author 187862-dujunhui
	 * @date 2014-6-23 下午4:58:21
	 * @param orgCode
	 * @param districtCode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService#queryInfoByDistrictCodeAndActive(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressBigRegionDistrEntity queryInfoByDistrictCodeAndActive(
			String orgCode,String districtCode, String active) {

		if(StringUtils.isEmpty(districtCode)){
			return null;
		}
		if(StringUtils.isEmpty(active)){
			return null;
		}
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
		//查询并返回数据
		return expressBigRegionDistrDao.queryInfoByDistrictCodeAndActive(orgCode,districtCode, active);
	}
     

}
