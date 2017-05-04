package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.ecs.inteface.domain.ResourceInfo;
import com.deppon.ecs.inteface.domain.RoleInfo;
import com.deppon.ecs.inteface.domain.RoleResourceInfo;
import com.deppon.ecs.inteface.domain.SyncResourceRequest;
import com.deppon.ecs.inteface.domain.UserEntityInfo;
import com.deppon.ecs.inteface.domain.UserOrgRoleInfo;
import com.deppon.esb.pojo.transformer.jaxb.SyncResourceRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
/*import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;*/

/**
 * 权限信息同步
 * @author 310854
 * @date   2016-4-29 - 下午4:08:20
 */
public class SyncResourceService implements ISyncResourceService {


	private static final Logger log = LoggerFactory
			.getLogger(SyncEfficiencyService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECEIVE_SALES_DEPARTMENT";

	private static final String VERSION = "1.0";

	/**
     * 系统配置参数 Service接口
     */
    private IConfigurationParamsService configurationParamsService;
    
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
	
	@Override
	public void syncResource(List<?> entitys) {
		// TODO Auto-generated method stub
		if(!configurationParamsService.queryBseSwitch4Ecs()){
			return;
		}
		StringBuilder logs = new StringBuilder();
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncResourceRequest resourceRequest = new SyncResourceRequest();
			List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
			List<RoleResourceInfo> roleResourceInfos = new ArrayList<RoleResourceInfo>();
			List<ResourceInfo> resourceInfos = new ArrayList<ResourceInfo>();
			List<UserOrgRoleInfo> userOrgRoleInfos = new ArrayList<UserOrgRoleInfo>();
			List<UserEntityInfo> userEntityInfos = new ArrayList<UserEntityInfo>();

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			for(int i = 0;i < entitys.size(); i++){
				if(entitys.get(i) instanceof RoleEntity){
					logs.append("角色");
					RoleInfo roleInfo = transFossToEsb((RoleEntity)entitys.get(i));
					roleInfos.add(roleInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if(entitys.get(i) instanceof RoleResourceEntity){
					logs.append("角色权限");
					RoleResourceInfo roleResourceInfo =transFossToEsb((RoleResourceEntity)entitys.get(i));
					roleResourceInfos.add(roleResourceInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if(entitys.get(i) instanceof ResourceEntity){
					logs.append("权限");
					ResourceInfo resourceInfo = transFossToEsb((ResourceEntity)entitys.get(i));
					resourceInfos.add(resourceInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if (entitys.get(i) instanceof UserOrgRoleEntity){
					logs.append("用户部门角色");
					UserOrgRoleInfo userOrgRoleInfo = transFossToEsb((UserOrgRoleEntity)entitys.get(i));
					userOrgRoleInfos.add(userOrgRoleInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if(entitys.get(i) instanceof UserEntity){
					logs.append("用户信息");
					UserEntityInfo userInfo = transFossToEsb((UserEntity)entitys.get(i));
					userEntityInfos.add(userInfo);
					codes.append(SymbolConstants.EN_COMMA);
				}
			}

			resourceRequest.getResource().addAll(resourceInfos);
			resourceRequest.getRole().addAll(roleInfos);
			resourceRequest.getRoleResource().addAll(roleResourceInfos);
			resourceRequest.getUserOrgRole().addAll(userOrgRoleInfos);
			resourceRequest.getUserEntity().addAll(userEntityInfos);
			
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader.setBusinessDesc1("send Resource info to other platform.同步"+logs.toString()+"信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send Resource info to other platform 开始 同步"+logs.toString()+"信息 到其它平台：\n"
					+ new SyncResourceRequestTrans()
							.fromMessage(resourceRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, resourceRequest);

			log.info("end to send Resource info to other platform 结束 同步"+logs.toString()+"信息 到其它平台：\n"
					+ new SyncResourceRequestTrans()
							.fromMessage(resourceRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error("同步"+logs.toString()+" 到快递平台，发送数据到ESB错误:      "+e.getMessage(), e);
			throw new BusinessException("同步"+logs.toString()+" 到快递平台，发送数据到ESB错误");
		}

	}

	/**
	 * 用户信息转换
	 * @author 310854
	 * @date 2016-7-28
	 */
	private UserEntityInfo transFossToEsb(UserEntity entity){
		UserEntityInfo info = new UserEntityInfo();
		if(null != entity.getId()){
			info.setID(entity.getId());
		}
		if(null != entity.getActive()){
			info.setActive(entity.getActive());
		}
		if(null != entity.getBeginDate()){
			info.setBeginDate(entity.getBeginDate());
		}
		if(null != entity.getCreateDate()){
			info.setCreateDate(entity.getCreateDate());
		}
		if(null != entity.getCreateUser()){
			info.setCreateUser(entity.getCreateUser());
		}
		if(null != entity.getEmpCode()){
			info.setEmpCode(entity.getEmpCode());
		}
		if(null != entity.getEmpName()){
			info.setEmpName(entity.getEmpName());
		}
		if(null != entity.getEndDate()){
			info.setEndDate(entity.getEndDate());
		}
		if(null != entity.getLastLogin()){
			info.setLastLogin(entity.getLastLogin());
		}
		if(null != entity.getModifyDate()){
			info.setModifyDate(entity.getModifyDate());
		}
		if(null != entity.getModifyUser()){
			info.setModifyUser(entity.getModifyUser());
		}
		if(null != entity.getPassword()){
			info.setPassword(entity.getPassword());
		}
		if(null != entity.getTitle()){
			info.setTitle(entity.getTitle());
		}
		if(null != entity.getUserName()){
			info.setUserName(entity.getUserName());
		}
		if(null != entity.getVersion()){
			info.setVersion(entity.getVersion().toString());
		}
		return info;
	}
	
	/**
	 * 角色
	 * @author 310854
	 * @date   2016-4-29 - 下午4:44:28
	 * @param entity
	 * @param operationType
	 * @return
	 */
	private RoleInfo transFossToEsb(RoleEntity entity) {
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setActive(entity.getActive());
		roleInfo.setCreateTime(entity.getCreateDate());
		roleInfo.setCreateUserCode(entity.getCreateUser());
		roleInfo.setId(entity.getId());
		roleInfo.setModifyTime(entity.getModifyDate());
		roleInfo.setModifyUserCode(entity.getModifyUser());
		roleInfo.setNotes(entity.getNotes());
		roleInfo.setName(entity.getName());
		roleInfo.setCode(entity.getCode());
		
		return roleInfo;
	}
	
	/**
	 * 角色权限
	 * @author 310854
	 * @date 2016-4-8
	 */
	private RoleResourceInfo transFossToEsb(RoleResourceEntity entity) {
		RoleResourceInfo roleResourceInfo = new RoleResourceInfo();
		roleResourceInfo.setActive(entity.getActive());
		roleResourceInfo.setCreateTime(entity.getCreateDate());
		roleResourceInfo.setCreateUserCode(entity.getCreateUser());
		roleResourceInfo.setId(entity.getId());
		roleResourceInfo.setModifyTime(entity.getModifyDate());
		roleResourceInfo.setModifyUserCode(entity.getModifyUser());
		roleResourceInfo.setResourceCode(entity.getResourceCode());
		roleResourceInfo.setRoleCode(entity.getRoleCode());
		if(null != entity.getVersionNo()){
			roleResourceInfo.setVersionNo(entity.getVersionNo().toString());
		}
		roleResourceInfo.setVirtualCode(entity.getVirtualCode());
		
		return roleResourceInfo;
	}
	
	/**
	 * 权限
	 * @author 310854
	 * @date 2016-4-8
	 */
	private ResourceInfo transFossToEsb(ResourceEntity entity) {
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setActive(entity.getActive());
		resourceInfo.setCreateTime(entity.getCreateDate());
		resourceInfo.setCreateUserCode(entity.getCreateUser());
		resourceInfo.setId(entity.getId());
		resourceInfo.setModifyTime(entity.getModifyDate());
		resourceInfo.setModifyUserCode(entity.getModifyUser());
		resourceInfo.setBelongSystemType(entity.getBelongSystemType());
		resourceInfo.setChecked(entity.getChecked());
		resourceInfo.setCls(entity.getCls());
		resourceInfo.setCode(entity.getCode());
		resourceInfo.setDisplayOrder(entity.getDisplayOrder());
		resourceInfo.setEntryUri(entity.getEntryUri());
		resourceInfo.setIconCls(entity.getIconCls());
		resourceInfo.setLeafFlag(entity.getLeafFlag());
		resourceInfo.setName(entity.getName());
		resourceInfo.setNotes(entity.getNotes());
		if(null != entity.getParentRes()){
			resourceInfo.setParentRes(entity.getParentRes().getCode());
		}
		resourceInfo.setResLevel(entity.getResLevel());
		resourceInfo.setResType(entity.getResType());
		if(null != entity.getVersionNo()){
			resourceInfo.setVersionNo(entity.getVersionNo().toString());
		}
		
		return resourceInfo; 
	}

	/**
	 * 用户部门角色
	 * @author 310854
	 * @date 2016-4-8
	 */
	private UserOrgRoleInfo transFossToEsb(UserOrgRoleEntity entity){
		UserOrgRoleInfo userOrgRoleInfo = new UserOrgRoleInfo();
		userOrgRoleInfo.setActive(entity.getActive());
		userOrgRoleInfo.setCreateTime(entity.getCreateDate());
		userOrgRoleInfo.setCreateUserCode(entity.getCreateUser());
		userOrgRoleInfo.setEmpCode(entity.getEmpCode());
		userOrgRoleInfo.setId(entity.getId());
		userOrgRoleInfo.setModifyTime(entity.getModifyDate());
		userOrgRoleInfo.setModifyUserCode(entity.getModifyUser());
		userOrgRoleInfo.setOrgCode(entity.getOrgCode());
		userOrgRoleInfo.setOrgUnifiedCode(entity.getOrgUnifiedCode());
		userOrgRoleInfo.setRoleCode(entity.getRoleCode());
		if(null != entity.getVersion()){
			userOrgRoleInfo.setVersion(entity.getVersion().toString());
		}
		userOrgRoleInfo.setVirtualCode(entity.getVirtualCode());
		
		return userOrgRoleInfo;
	}

}
