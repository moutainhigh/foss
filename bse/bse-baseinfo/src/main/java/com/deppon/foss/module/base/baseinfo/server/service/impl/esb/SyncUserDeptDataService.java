package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.AuthorityInfo;
import com.deppon.ecs.inteface.domain.DepartmentInfos;
import com.deppon.ecs.inteface.domain.SyncUserDeptDataRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncUserDeptDataRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.util.define.FossConstants;
import com.eos.foundation.common.utils.StringUtil;

/**
 * 同步数据权限Service
 * @author 310854
 * @date 2016-4-6
 */
public class SyncUserDeptDataService implements ISyncUserDeptDataService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncUserDeptDataService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECEIVE_AUTHORITY";

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
	public void syncUserDeptData(List<UserDeptDataEntity> entitys, EmployeeEntity userEntity, int operateType)  throws BusinessException{
		// TODO Auto-generated method stub
		if(!configurationParamsService.queryBseSwitch4Ecs()){
			return;
		}
		try {
			if (null == userEntity || StringUtil.isBlank(userEntity.getEmpCode())) {
				throw new BusinessException("传入用户为空！接口转换异常");
			}
			SyncUserDeptDataRequest userDeptDataRequest = new SyncUserDeptDataRequest();
			List<AuthorityInfo> infos = new ArrayList<AuthorityInfo>();
			List<DepartmentInfos> departmentInfos = new ArrayList<DepartmentInfos>();

			AuthorityInfo authorityInfo =  new AuthorityInfo();
			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			
			if(!CollectionUtils.isEmpty(entitys)){
				for (UserDeptDataEntity entity : entitys) {
					codes.append(SymbolConstants.EN_COMMA);
					if (entity == null) {
						continue;
					}
					DepartmentInfos info = this.transFossToEsb(entity);
				
					departmentInfos.add(info);
				//	codes.append(entity.getRegionCode());
				}
			}
			
			authorityInfo.setUserCode(userEntity.getEmpCode());
			authorityInfo.getDepartmentInfo().addAll(departmentInfos);
			authorityInfo.setOperationType(operateType);
			infos.add(authorityInfo);
			
			userDeptDataRequest.getAuthority().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send UserDeptData info to other platform.同步定数据权限信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send UserDeptData info to other platform 开始 同步数据权限信息 到其它平台：\n"
					+ new SyncUserDeptDataRequestTrans()
							.fromMessage(userDeptDataRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, userDeptDataRequest);

			log.info("end to send UserDeptData info to other platform 结束 同步数据权限信息 到其它平台：\n"
					+ new SyncUserDeptDataRequestTrans()
							.fromMessage(userDeptDataRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error("同步数据权限信息 到快递平台，发送数据到ESB错误 :    "+e.getMessage(), e);
			throw new BusinessException("同步数据权限信息 到快递平台，发送数据到ESB错误");
		}

	}

	/**
	 * 数据权限信息
	 * @author 310854
	 * @date   2016-4-29 - 下午4:33:55
	 * @param entity
	 * @param operateType
	 * @return
	 */
	private DepartmentInfos transFossToEsb(UserDeptDataEntity entity) {
		
		DepartmentInfos info = new DepartmentInfos();
		if(null != entity.getDept() && null != entity.getDept().getCode()){
			info.setOrgCode(entity.getDept().getCode());
		}
		info.setCreateTime(entity.getCreateDate());
		info.setCreateUserCode(GainEmployee.getOperUserCode());
		info.setId(entity.getId());
		info.setModifyTime(entity.getModifyDate());
		info.setModifyUserCode(GainEmployee.getOperUserCode());
		
		
		info.setVersionNo("1");
		info.setActive(FossConstants.ACTIVE);
		info.setIncludeSubOrg(entity.getSubOrgFlag());
//		info.setOperationType(operateType);
		return info; 
	}

}
