package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 系统配置服务层实现类
 * @author WangQianJin
 * @date 2013-4-26 上午10:46:48
 */
public class SysConfigService implements ISysConfigService {
	
	// 系统配置
	private ISysConfigDao pkpsysConfigDao;
	//部门 复杂查询 service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setPkpsysConfigDao(ISysConfigDao pkpsysConfigDao) {
		this.pkpsysConfigDao = pkpsysConfigDao;
	}

	/**
     * 
     * <p>通过主键查询系统配置</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param id
     * @return
     * @see
     */
	@Override
	public ConfigurationParamsEntity queryByPrimaryKey(String id) {
		return pkpsysConfigDao.queryByPrimaryKey(id);
	}

	/**
     * 
     * <p>通过配置项标示查询系统配置,根据confcode和orgcode一起查询</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param confCode
     * @param orgCode
     * @return
     * @see
     */
	@Override
	public ConfigurationParamsEntity queryByConfCode(String confCode,
			String orgCode) {
		//本部门及所有的上级部门Code
		StringBuilder orgCodeListStr = new StringBuilder("");
		//如果部门为空，则获取当前用户的部门
		if(orgCode==null || "".equals(orgCode)){
			//获取当前用户
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			orgCode=user.getEmployee().getDepartment().getCode();
		}
		// 获取本部门及所有的上级部门列表
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
		if(orgList!=null && orgList.size()>0){
			for(int i=0;i<orgList.size();i++){
				OrgAdministrativeInfoEntity org=orgList.get(i);
				if(org!=null && org.getCode()!=null && !"".equals(org.getCode())){
					orgCodeListStr.append("'").append(org.getCode()).append("'").append(",");
				}				
			}
			if(orgCodeListStr.length()>0){
				orgCodeListStr = new StringBuilder(orgCodeListStr.toString().substring(0,orgCodeListStr.toString().length()-1));
			}
		}else{
			//如果查询不到上级部门，则获取当前部门和顶级部门
			orgCodeListStr.append("'").append(orgCode).append("',").append("'").append(FossConstants.ROOT_ORG_CODE).append("'");
		}		
		return pkpsysConfigDao.queryByConfCode(confCode, orgCodeListStr.toString());
	}

	/**
     * 
     * <p>通过配置项标示查询系统配置,只根据confcode来查询</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param confCode
     * @return 
     */
	@Override
	public ConfigurationParamsEntity queryByConfCode(String confCode) {
		return pkpsysConfigDao.queryByConfCode(confCode);
	}

	/**
     * 
     * <p>通过配置项集合查询系统配置</p> 
     * @author WangQianJin
     * @date 2012-10-24 下午1:45:48
     * @param confCodes
     * @param orgCode
     * @return
     * @see
     */
	@Override
	public List<ConfigurationParamsEntity> queryByConfCodeArray(
			String[] confCodes, String orgCode) {
		//本部门及所有的上级部门Code
		StringBuilder orgCodeListStr=new StringBuilder("");
		//如果部门为空，则获取当前用户的部门
		if(orgCode==null || "".equals(orgCode)){
			//获取当前用户
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			orgCode=user.getEmployee().getDepartment().getCode();
		}
		// 获取本部门及所有的上级部门列表
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
		if(orgList!=null && orgList.size()>0){
			for(int i=0;i<orgList.size();i++){
				OrgAdministrativeInfoEntity org=orgList.get(i);
				if(org!=null && org.getCode()!=null && !"".equals(org.getCode())){
					orgCodeListStr.append("'").append(org.getCode()).append("'").append(",");
				}				
			}
			if(orgCodeListStr.length()>0){
				orgCodeListStr=new StringBuilder(orgCodeListStr.toString().substring(0,orgCodeListStr.toString().length()-1));
			}
		}else{
			//如果查询不到上级部门，则获取当前部门和顶级部门
			orgCodeListStr.append("'").append(orgCode).append("',").append("'").append(FossConstants.ROOT_ORG_CODE).append("'");
		}		
		return pkpsysConfigDao.queryByConfCodeArray(confCodes, orgCodeListStr.toString());
	}

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public boolean addConfig(ConfigurationParamsEntity config) {
		return pkpsysConfigDao.addConfig(config);
	}

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void updateConfig(ConfigurationParamsEntity config) {
		pkpsysConfigDao.updateConfig(config);
	}

	/**
	 * 删除
	 * @param config
	 */
	@Override
	public void delete(ConfigurationParamsEntity config) {
		pkpsysConfigDao.delete(config);
	}
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByEntity(String model,String config,String orgCode){
		ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setConfType(model);
		entity.setCode(config);
		entity.setOrgCode(orgCode);
		List<ConfigurationParamsEntity> list=pkpsysConfigDao.queryConfigurationParamsByEntity(entity);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
