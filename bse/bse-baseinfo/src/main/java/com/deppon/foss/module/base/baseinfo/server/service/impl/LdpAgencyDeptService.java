package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LdpAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LdpAgencyDeptException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyDeptService implements ILdpAgencyDeptService {
    
	//注入快递代理公司网点Dao
	private ILdpAgencyDeptDao ldpAgencyDeptDao;

	private IDataDictionaryValueService dataDictionaryValueService;
	
	public void setLdpAgencyDeptDao(ILdpAgencyDeptDao ldpAgencyDeptDao) {
		this.ldpAgencyDeptDao = ldpAgencyDeptDao;
	}
	 
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * 新增快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
    @Transactional
	public int addLdpAgencyDept(OuterBranchExpressEntity entity) {
		if(null == entity){
			throw new LdpAgencyDeptException("新增快递代理公司网点对象为空！");
		}else{
			//313353 sonar
			this.sonarSplitOne(entity);
			
			if(StringUtils.isEmpty(entity.getAddress())){
				throw new LdpAgencyDeptException("新增快递代理公司网点地址为空！");
			}
			
			if(StringUtils.isEmpty(entity.getStationNumber())){
				throw new LdpAgencyDeptException("新增快递代理公司网点的提货网点编码为空！");
			}
			if(StringUtils.isEmpty(entity.getAgentCompanyAbbreviationCode())){
				throw new LdpAgencyDeptException("新增代理公司简称为空！");
			}
		}
		
		List<OuterBranchExpressEntity> ldpEntity= ldpAgencyDeptDao.queryLdpAgencyStationNumberIsExist(entity.getStationNumber());
		if(!CollectionUtils.isEmpty(ldpEntity)){
			throw new LdpAgencyDeptException("提货网点编码重复（由四位数字组成）！");
		}
		
		//用来标记快递代理网点编码是否重复
		boolean isExists = false;
		//查询快递代理网点编码是否重复
		isExists = this.queryLdpAgencyDeptIsExistsByCode(entity.getAgentDeptCode().trim());
		if(isExists){
			throw new LdpAgencyDeptException("新增快递代理公司网点编码重复！");
		}
		
		List<OuterBranchExpressEntity> rtnEntityList = ldpAgencyDeptDao.queryLdpAgencyDeptIsExistsByName(entity.getAgentDeptName());
		if(CollectionUtils.isNotEmpty(rtnEntityList)){
			for(OuterBranchExpressEntity rtnEntity:rtnEntityList){
				if(!rtnEntity.getAgentDeptCode().trim().equals(entity.getAgentDeptCode().trim())){
					throw new LdpAgencyDeptException("新增快递代理公司网点名称重复！");
				}
			}
		}
		
		if (null == entity.getCreateDate()) {
			entity.setCreateDate(new Date());
		}
		if (null == entity.getCreateUser()) {
			entity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
		}
		if (null == entity.getModifyDate()) {
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		}
		if (null == entity.getModifyUser()) {
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
		}
		
		//设置网点类型
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		entity.setActive(FossConstants.ACTIVE);
		entity.setVirtualCode(UUIDUtils.getUUID());
		entity.setId(UUIDUtils.getUUID());
		//记录新增行数
		int count = ldpAgencyDeptDao.addLdpAgencyDept(entity);
	    
		return count > 0 ? 1 : -1;
	}
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(OuterBranchExpressEntity entity) {
		if(StringUtils.isEmpty(entity.getAgentDeptCode())){
			throw new LdpAgencyDeptException("新增快递代理公司网点编码为空！");
		}
		if(StringUtils.isEmpty(entity.getAgentDeptName())){
			throw new LdpAgencyDeptException("新增快递代理公司网点名称为空！");
		}
		if(StringUtils.isEmpty(entity.getSimplename())){
			throw new LdpAgencyDeptException("新增快递代理公司网点简称为空！");
		}
		if(StringUtils.isEmpty(entity.getProvCode())){
			throw new LdpAgencyDeptException("新增快递代理公司网点省份为空！");
		}
		if(StringUtils.isEmpty(entity.getCityCode())){
			throw new LdpAgencyDeptException("新增快递代理公司网点城市为空！");
		}
		if(StringUtils.isEmpty(entity.getCountyCode())){
			throw new LdpAgencyDeptException("新增快递代理公司网点区/县为空！");
		}
		if(StringUtils.isEmpty(entity.getManagement())){
			throw new LdpAgencyDeptException("新增快递代理公司网点管理部门编码为空！");
		}
		if(StringUtils.isEmpty(entity.getAgentCompany())){
			throw new LdpAgencyDeptException("新增快递代理公司编码为空！");
		}
		if(StringUtils.isEmpty(entity.getContactPhone())){
			throw new LdpAgencyDeptException("新增快递代理公司网点联系电话为空！");
		}

	}
	
	/**
	 * 根据code作废快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param codeStr
	 *            快递代理代理网点虚拟编码拼接字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
    @Transactional
	public int deleteLdpAgencyDeptByCode(String codeStr, String modifyUser) {
		// 获取前台传递的虚拟code数组
				String[] virtualCodes = null;
				if (StringUtils.isEmpty(codeStr.trim())) {
					throw new LdpAgencyCompanyException("需要删除的记录所对应的虚拟code为空！");
				} else {
					virtualCodes = codeStr.split(",");
				}
				if (StringUtils.isEmpty(modifyUser)) {
					modifyUser = FossUserContext.getCurrentUser().getEmpCode();
				}
				
				// 删除选中的记录
				int count = ldpAgencyDeptDao.deleteLdpAgencyDeptByCode(virtualCodes, modifyUser);
				return count > 0 ? 1 : -1;
	}

	/**
	 * 修改快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
    @Transactional
	public int updateLdpAgencyDept(OuterBranchExpressEntity entity) {
		if(null == entity){
			throw new LdpAgencyDeptException("快递代理公司网点对象为空！");
		}else{
			//313353 sonar
			this.sonarSplitTwo(entity);
			
			if(StringUtils.isEmpty(entity.getAddress())){
				throw new LdpAgencyDeptException("新增快递代理公司网点地址为空！");
			}
			
			if(StringUtils.isEmpty(entity.getStationNumber())){
				throw new LdpAgencyDeptException("快递代理公司网点的提货网点编码为空！");
			}
			if(StringUtils.isEmpty(entity.getAgentCompanyAbbreviationCode())){
				throw new LdpAgencyDeptException("代理公司简称为空！");
			}
		}
			
		List<OuterBranchExpressEntity> ldpEntity= ldpAgencyDeptDao.queryLdpAgencyStationNumberIsExist(entity.getStationNumber());
		if(!CollectionUtils.isEmpty(ldpEntity)){
			for (OuterBranchExpressEntity outerBranchEntity : ldpEntity) {
				if(StringUtils.equals(outerBranchEntity.getAgentDeptCode(), entity.getAgentDeptCode())){
					continue;
				}else{
					
					throw new LdpAgencyDeptException("提货网点编码重复（由四位数字组成）！");
				}
			}
		}
		
		List<OuterBranchExpressEntity> rtnEntityList = ldpAgencyDeptDao.queryLdpAgencyDeptIsExistsByName(entity.getAgentDeptName());
		if(CollectionUtils.isNotEmpty(rtnEntityList)){
			for(OuterBranchExpressEntity rtnEntity:rtnEntityList){
				if(!rtnEntity.getAgentDeptCode().trim().equals(entity.getAgentDeptCode().trim())){
					throw new LdpAgencyDeptException("新增快递代理公司网点名称重复！");
				}
			}
		}
		
		if (null == entity.getCreateDate()) {
			entity.setCreateDate(new Date());
		}
		if (null == entity.getCreateUser()) {
			entity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
		}
		if (null == entity.getModifyDate()) {
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		}
		if (null == entity.getModifyUser()) {
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
		}
		//设置网点类型
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		entity.setActive(FossConstants.ACTIVE);
		//记录修改的行数
		
		OuterBranchExpressEntity outerBranchExpressEntity = ldpAgencyDeptDao.updateLdpAgencyDept(entity);
		
		return outerBranchExpressEntity != null ? 1 : -1;
	}
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(OuterBranchExpressEntity entity) {
		if(StringUtils.isEmpty(entity.getAgentDeptCode())){
			throw new LdpAgencyDeptException("快递代理公司网点编码为空！");
		}
		if(StringUtils.isEmpty(entity.getAgentDeptName())){
			throw new LdpAgencyDeptException("快递代理公司网点名称为空！");
		}
		if(StringUtils.isEmpty(entity.getSimplename())){
			throw new LdpAgencyDeptException("快递代理公司网点简称为空！");
		}
		if(StringUtils.isEmpty(entity.getProvCode())){
			throw new LdpAgencyDeptException("快递代理公司网点省份为空！");
		}
		if(StringUtils.isEmpty(entity.getCityCode())){
			throw new LdpAgencyDeptException("快递代理公司网点城市为空！");
		}
		if(StringUtils.isEmpty(entity.getCountyCode())){
			throw new LdpAgencyDeptException("快递代理公司网点区/县为空！");
		}
		if(StringUtils.isEmpty(entity.getManagement())){
			throw new LdpAgencyDeptException("快递代理公司网点管理部门编码为空！");
		}
		if(StringUtils.isEmpty(entity.getAgentCompany())){
			throw new LdpAgencyDeptException("快递代理公司编码为空！");
		}
		if(StringUtils.isEmpty(entity.getContactPhone())){
			throw new LdpAgencyDeptException("快递代理公司网点联系电话为空！");
		}
		
	}

	/**
	 * 根据传入对象查询符合条件所有快递代理代理网点信息
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param entity
	 *            快递代理代理网点点实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	public List<OuterBranchExpressEntity> queryLdpAgencyDepts(
			OuterBranchExpressEntity entity, int limit, int start) {
		if(null == entity){
			throw new LdpAgencyDeptException("传入的快递代理公司网点对象为空！");
		}
		
		//设置网点类型
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		if(StringUtils.isEmpty(entity.getActive())){
			
			entity.setActive(FossConstants.ACTIVE);
		}
		
		return ldpAgencyDeptDao.queryLdpAgencyDepts(entity, limit, start);
	}

	/**
	 * <p>
	 * 根据代理公司虚拟编码查询所管辖的理网点
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param comVirtualCode
	 *            代理公司虚拟编码
	 * @return
	 * @see
	 */
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCode(
			String comVirtualCode,String isActive) {
		//校验虚拟编码是否为空
		if(StringUtils.isEmpty(comVirtualCode)){
			throw new LdpAgencyDeptException("传入的快递代理网点虚拟编码为空！");
		}
		
		return ldpAgencyDeptDao.queryLdpAgencyDeptsByComVirtualCode(comVirtualCode, isActive);
	}

    /**
     * <p>根据快递代理公司虚拟编码查询所管辖的理网点</p> 
     * @author WangPeng
     * @date 2013-07-22 11:38 AM
     * @param comVirtualCode 快递代理公司虚拟编码数组
     * @return
     * @see
     */
     public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCodes(String[] comVirtualCode){
    	//校验虚拟编码是否为空
 		if(CollectionUtils.isEmpty(Arrays.asList(comVirtualCode))){
 			
 			throw new LdpAgencyDeptException("传入的快递代理网点虚拟编码为空！");
 		}
    	 return ldpAgencyDeptDao.queryLdpAgencyDeptsByComVirtualCodes(comVirtualCode);
     }
    
	
	/**
	 * 统计总记录数
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:38 PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return
	 * @see
	 */
	public Long queryRecordCount(OuterBranchExpressEntity entity) {
		if(null == entity){
			throw new LdpAgencyDeptException("传入的快递代理网点对象为空！");
		}
		return ldpAgencyDeptDao.queryRecordCount(entity);
	}
	
	/**
     * 根据网点编码查询是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    public boolean queryLdpAgencyDeptIsExistsByCode(String deptCode){
		return ldpAgencyDeptDao.queryLdpAgencyDeptIsExistsByCode(deptCode);
    	
    }
    /**
     * 根据网点编码查询是快递代理网点信息(包括代理公司简称的Code与Name)
     * @author  wusuhua
     * @Date    2015-5-29 上午10:47:03
     * @param   deptCode
     * @return  OuterBranchExpressEntity
     * */
    public OuterBranchExpressEntity queryLdpAgencyDeptByDeptCode(String deptCode) {
    	//校验网点编码是否为空
 		if(StringUtils.isEmpty(deptCode)){
 			throw new LdpAgencyDeptException("传入的快递代理网点编码为空！");
 		}
 		List<OuterBranchExpressEntity> list = ldpAgencyDeptDao.queryLdpAgencyDeptByCode(deptCode,"Y");
 		if(CollectionUtils.isEmpty(list)){
 			return null;
 		}
 		OuterBranchExpressEntity entity=list.get(0);
 		String teamsCode="ABBREVIATION_OF_AGENCY_COMPANY";
 		DataDictionaryValueEntity dataDictionaryValueEntity =dataDictionaryValueService.
 				queryDataDictionaryValueByTermsCodeValueCodeNoCache(teamsCode,entity.getAgentCompanyAbbreviationCode());
 		if(null!=dataDictionaryValueEntity){
 			entity.setAgentCompanyAbbreviation(dataDictionaryValueEntity.getValueName());
 		}
		return entity;
	}
    /**
     * 根据网点编码查询是快递代理网点信息
     * (以前的接口..不包括代理公司简称的Code与Name,这个方法在组织信息中被调用，为防止出错，上面新写了个方法 功能一样)
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    public OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode,String isActive) {
    	//校验网点编码是否为空
 		if(StringUtils.isEmpty(deptCode)){
 			throw new LdpAgencyDeptException("传入的快递代理网点编码为空！");
 		}
 		List<OuterBranchExpressEntity> list = ldpAgencyDeptDao.queryLdpAgencyDeptByCode(deptCode, isActive);
 		if(CollectionUtils.isEmpty(list)){
 			return null;
 		}
		return list.get(0);
	}
    
    /**
     * <p>根据快递代理公司编码查询所管辖的理网点</p> 
     * @author WangPeng
     * @date 2013-07-22 11:38 AM
     * @param agencyCompanyCode 快递代理公司编码
     * @return list
     * 
     */
     public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode,String isActive) {
    	//校验传入的快递代理公司编码是否为空
  		if(StringUtils.isEmpty(agencyCompanyCode)){
  			throw new LdpAgencyDeptException("传入的快递代理网点编码为空！");
  		}
		return ldpAgencyDeptDao.queryLdpAgencyDeptsByagencyCompanyCode(agencyCompanyCode,isActive);
	}
     
     /**
      * <p>根据快递代理公司编码查询所管辖的理网点</p> 
      * @author WangPeng
      * @date 2013-07-22 11:38 AM
      * @param agencyCompanyCode 快递代理公司编码
      * @return list
      * 
      */
      public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode,String isActive,int limit,int start) {
     	//校验传入的快递代理公司编码是否为空
   		if(StringUtils.isEmpty(agencyCompanyCode)){
   			throw new LdpAgencyDeptException("传入的快递代理网点编码为空！");
   		}
   		if(limit != 0){
   			
   			return ldpAgencyDeptDao.queryLdpAgencyDeptsByagencyCompanyCode(agencyCompanyCode,isActive, limit, start);
   		}
   		return ldpAgencyDeptDao.queryLdpAgencyDeptsByagencyCompanyCode(agencyCompanyCode,isActive);
 	}
      
    /**
     * <p>
     * 根据快递代理网点名称查询快递代理网点信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-10-21 上午11:27:16
     * @param deptName
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService#queryLdpAgencyDeptIsExistsByName(java.lang.String)
     */
    @Override
    public OuterBranchExpressEntity queryLdpAgencyDeptIsExistsByName(
	    String deptName) {
	if(StringUtils.isNotBlank(deptName)){
	    List<OuterBranchExpressEntity> list = ldpAgencyDeptDao.queryLdpAgencyDeptIsExistsByName(deptName.trim());
	    if(CollectionUtils.isNotEmpty(list)){
		return list.get(0);
	    }else {
		return null;
	    }
	}
	
	return null;
    }
   
}
