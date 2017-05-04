/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-uums-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/UserInfoResultNotificationProcessor.java
 * 
 * FILE NAME        	: UserInfoResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.jaxb.SendUserInfoRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendUserInfoResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService;
import com.deppon.foss.module.base.uumsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllResponse;
import com.deppon.uums.inteface.domain.usermanagement.UserInfoDeptAll;
import com.deppon.uums.inteface.domain.usermanagement.UserRoleDeptAllInfo;
import com.deppon.uums.inteface.domain.usermanagement.UserRoleDetailInfo;

/**
 * 用来从UUMS同步公司“用户信息”到FOSS数据库：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-23 上午11:12:40
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-23 上午11:12:40
 * @since
 * @version
 */
public class UserInfoResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserInfoResultNotificationProcessor.class);

	// 同步"用户信息"接口结果操作Service
	private IUserInformationService userInformationService;
	// 业务锁
	private IBusinessLockService businessLockService;
	//用户部门数据权限
	private IUserDeptDataService userDeptDataService;
	
	//统计成功失败记录
	int  successCount = 0, failCount = 0;

	/**
	 * 组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info(" ***************************** Begin to record data ***************************** ");

		SendUserInfoDeptAllRequest sendUserInfoRequest = (SendUserInfoDeptAllRequest) req;
		SendUserInfoDeptAllResponse sendUserInfoResponse = new SendUserInfoDeptAllResponse();
		
		LOGGER.info(new SendUserInfoRequestTrans()
		.fromMessage(sendUserInfoRequest));

		if (sendUserInfoRequest == null
				|| sendUserInfoRequest.getUserInfo() == null) {
			LOGGER.info(" 传入的sendUserInfoRequest对象为null或userInfo为null，无法处理");
			LOGGER.info(" ***************************** End to record data ***************************** ");
			return sendUserInfoResponse;
		}

	
		List<SendUserInfoDeptAllProcessReult> detailList = sendUserInfoResponse
				.getProcessResult();
		for (UserInfoDeptAll userInfo : sendUserInfoRequest.getUserInfo()) {
			// FOSS"用户信息"信息初始化
//			UserEntity userEntity = null;
			try {
				// 业务锁
				MutexElement mutex = new MutexElement(userInfo.getEmpCode(),
						"UUMS_USER_CODE", MutexElementType.UUMS_USER_CODE);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex,
						NumberConstants.ZERO);
				if (result) {
					updateUser(userInfo, mutex);
				} else {

					failCount++;
					detailList.add(recordSynchronizedRequestDataError(userInfo,
							false, "UUMS0102:出现了高并发操作！"));
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				}
				successCount++;
				detailList.add(recordSynchronizedRequestDataError(userInfo,
						true, null));
			} catch (Exception e) {
				failCount++;
				detailList.add(recordSynchronizedRequestDataError(userInfo,
						false, e.getMessage()));
				LOGGER.error("", e);
			}
		}
		sendUserInfoResponse.setSuccessCount(successCount);
		sendUserInfoResponse.setFailedCount(failCount);

		LOGGER.info(new SendUserInfoResponseTrans()
				.fromMessage(sendUserInfoResponse));

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return sendUserInfoResponse;
	}

	@SuppressWarnings("unused")
	private void updateUser(UserInfoDeptAll userInfo, MutexElement mutex) {
		UserEntity userEntity;
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		userEntity = vaidationUUMSUserInfoDataObjectIntoFOSS(userInfo);
		// ZJF-130566 接口优化
		/*
		 * // 如果是新增或返聘 if
		 * (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(userInfo
		 * .getChangeType()) || NumberConstants.NUMERAL_S_FOUR
		 * .equalsIgnoreCase(userInfo.getChangeType())) { if (userEntity !=
		 * null) { throw new UserException( "",
		 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR); }
		 * userEntity = new UserEntity(); } else if (userEntity == null) { throw
		 * new UserException( "",
		 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR); }
		 */
		// 定义一个方法变量啊
		boolean validUserEntity = false;
		// 若为空，说明是在foss 数据库中存在
		if (null != userEntity) {
			validUserEntity = true;
		} else if (userEntity == null) {
			validUserEntity = false;
			userEntity = new UserEntity();
		}
		// 数据转换与封装
		if (StringUtils.isBlank(userInfo.getEmpCode())) {
			throw new UserException("", "用户信息的员工编码不存在！");
		}
		userEntity.setEmpCode(userInfo.getEmpCode().trim());
		userEntity.setUserName(userInfo.getUserName());
		userEntity.setEmpName(userInfo.getEmpName());
		// 密码加密
		// 只有做新增或返聘动作时才同步用户密码
		if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(userInfo
				.getChangeType())
				|| NumberConstants.NUMERAL_S_FOUR.equalsIgnoreCase(userInfo
						.getChangeType())) {
			userEntity.setPassword(CryptoUtil.digestByMD5(userInfo
					.getDesPassword()));
		}
		userEntity.setTitle(userInfo.getPosition());
		userEntity.setBeginDate(userInfo.getValidDate());
		userEntity.setEndDate(userInfo.getInvalidDate());

		List<UserOrgRoleEntity> userOrgRoleList = new ArrayList<UserOrgRoleEntity>();
		// 如果用户的组织角色信息不为空，则循环用户的组织角色信息
		if (CollectionUtils.isNotEmpty(userInfo.getRoleInfo())) {
			// 用户组织角色信息列表
			for (UserRoleDeptAllInfo userRoleInfo : userInfo.getRoleInfo()) {
//				UserOrgRoleEntity userOrgRoleEntity = null;
				String orgCode = userRoleInfo.getOrgCode();
				String empCode = userEntity.getEmpCode();
				String orgUnifiedCode = userRoleInfo.getOrgBenchmarkCode();
				//313353 sonar
				this.sonarSplitOne(orgUnifiedCode, userRoleInfo);
				
				for (UserRoleDetailInfo userRoleDetailInfo : userRoleInfo
						.getRoleCode()) {
					setUserOrgRoleList(userInfo, userOrgRoleList, orgCode,
							empCode, orgUnifiedCode, userRoleDetailInfo);

				}
			}
		}

		if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(userInfo
				.getChangeType())
				|| NumberConstants.NUMERAL_S_FOUR.equalsIgnoreCase(userInfo
						.getChangeType())
				|| NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(userInfo
						.getChangeType())) {
			// 根据同步的动作执行对应"用户组信息"的"新增"或返聘 或者 "修改"
			// 校验信息为TRUE时， 说明库中已经存在
			if (validUserEntity) {
				LOGGER.info("更新用户信息:" + userEntity.getEmpCode());
				userInformationService.updateUser(userEntity, userOrgRoleList,
						ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
			} else {
				LOGGER.info("新增用户信息:" + userEntity.getEmpCode());
				userInformationService.addUser(userEntity, userOrgRoleList,
						ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT, false);
			}
			// 刷新用户部门数据权限-优化dujunhui
			userDeptDataService.refreshUserDeptData(userEntity.getEmpCode());
		} else if (NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(userInfo
				.getChangeType())) {
			// 根据同步的动作执行对应"用户组信息"的"删除"
			LOGGER.info("做废用户信息:" + userEntity.getEmpCode());
			userInformationService.deleteUser(userEntity, userOrgRoleList,
					ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
			// 刷新用户部门数据权限-优化dujunhui
			userDeptDataService.refreshUserDeptData(userEntity.getEmpCode());
		} else if (NumberConstants.NUMERAL_S_FIVE.equalsIgnoreCase(userInfo
				.getChangeType())) {
			// 2016/2/26 增加用户自离的逻辑
			// 根据同步的动作执行对应"用户组信息"的"自离"，限制用户登录，但不删除用户权限
			LOGGER.info("自离用户信息:" + userEntity.getEmpCode());
			userInformationService.leaveUser(userEntity,
					ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
			// 刷新用户部门数据权限-优化dujunhui
			// userDeptDataService.refreshUserDeptData(userEntity.getEmpCode());
		}
		/*
		 * else if (NumberConstants.NUMERAL_S_TWO
		 * .equalsIgnoreCase(userInfo.getChangeType())) { //
		 * 根据同步的动作执行对应"用户组信息"的"修改" LOGGER.info("更新用户信息:" +
		 * userEntity.getEmpCode());
		 * userInformationService.updateUser(userEntity, userOrgRoleList,
		 * ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT); }
		 */else {
			throw new UserException(
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		}
		LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("完成解锁：" + mutex.getBusinessNo());
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(String orgUnifiedCode, UserRoleDeptAllInfo userRoleInfo) {
		if (StringUtils.isBlank(orgUnifiedCode)) {
			throw new UserException("", "用户部门角色信息中的部门标杆编码不存在！");
		}
		// 角色列表
		if (CollectionUtils.isEmpty(userRoleInfo.getRoleCode())) {
			throw new UserException("", "用户部门角色信息列表中的角色信息为空！");
		}
	}

	private void setUserOrgRoleList(UserInfoDeptAll userInfo,
			List<UserOrgRoleEntity> userOrgRoleList, String orgCode,
			String empCode, String orgUnifiedCode,
			UserRoleDetailInfo userRoleDetailInfo) {
		UserOrgRoleEntity userOrgRoleEntity;
		userOrgRoleEntity = vaidationUUMSUserOrgRoleInfoDataObjectIntoFOSS(
				empCode, userRoleDetailInfo.getRoleCode().trim(),
				orgUnifiedCode);
		// 如果为新增或者返聘，则系统中不能存在有效的记录
		if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(userInfo
				.getChangeType())
				|| NumberConstants.NUMERAL_S_FOUR
						.equalsIgnoreCase(userInfo.getChangeType())) {
			if (userOrgRoleEntity != null) {
				return;
			}
		}
		if (null == userOrgRoleEntity) {
			userOrgRoleEntity = new UserOrgRoleEntity();
		}
		// 数据转换与封装
		if (StringUtils.isNotBlank(empCode)) {
			userOrgRoleEntity.setEmpCode(empCode.trim());
		}
		if (StringUtils.isNotBlank(orgCode)) {
			userOrgRoleEntity.setOrgCode(orgCode.trim());
		}
		if (StringUtils.isNotBlank(userRoleDetailInfo.getRoleCode()
				.trim())) {
			userOrgRoleEntity.setRoleCode(userRoleDetailInfo
					.getRoleCode().trim());
		}
		if (StringUtils.isNotBlank(orgUnifiedCode)) {
			userOrgRoleEntity.setOrgUnifiedCode(orgUnifiedCode
					.trim());
		}

		if (CollectionUtils.isNotEmpty(userOrgRoleList)) {
			boolean flagP = false;
			for (UserOrgRoleEntity entityAdd : userOrgRoleList) {

				if (entityAdd
						.getEmpCode()
						.trim()
						.equals(userOrgRoleEntity.getEmpCode()
								.trim())
						&& entityAdd
								.getOrgCode()
								.trim()
								.equals(userOrgRoleEntity
										.getOrgCode().trim())
						&& entityAdd
								.getRoleCode()
								.trim()
								.equals(userOrgRoleEntity
										.getRoleCode().trim())) {
					flagP = true;
				}
			}
			if (!flagP) {
				userOrgRoleList.add(userOrgRoleEntity);
			}
		} else {
			userOrgRoleList.add(userOrgRoleEntity);
		}

		// 如果是否包含状态为1，则同步增加所有下级组织的用户组织角色信息
		if (NumberConstants.NUMERAL_S_ONE.equals(userRoleDetailInfo
				.getIsDeptAll())) {
			// 如果该组织存下级组织，且同步时包含下级组织标志，给下级组织增加用户组织角色信息
			List<OrgAdministrativeInfoEntity> entityResultChildList = orgAdministrativeInfoService
					.queryAllOrgAdministrativeInfoByParentOrgUnicodeCode(orgUnifiedCode
							.trim());
			// 如果下级组织存在
			if (CollectionUtils.isNotEmpty(entityResultChildList)) {

				// 循环处理,生成用户组总角色信息
				for (OrgAdministrativeInfoEntity entity : entityResultChildList) {
					setUserOrgRoleList(userOrgRoleList, empCode,
							userRoleDetailInfo, entity);
				}

			}
		}
	}

	private void setUserOrgRoleList(List<UserOrgRoleEntity> userOrgRoleList,
			String empCode, UserRoleDetailInfo userRoleDetailInfo,
			OrgAdministrativeInfoEntity entity) {
		UserOrgRoleEntity userOrgRoleEntityTemp = new UserOrgRoleEntity();
		userOrgRoleEntityTemp
				.setEmpCode(empCode.trim());
		userOrgRoleEntityTemp.setOrgCode(entity
				.getCode().trim());
		userOrgRoleEntityTemp
				.setRoleCode(userRoleDetailInfo
						.getRoleCode().trim());
		userOrgRoleEntityTemp.setOrgUnifiedCode(entity
				.getUnifiedCode().trim());

		// 如果包含相同数据，不再加入到新增的用户组织角色列表
		boolean flagC = false;
		for (UserOrgRoleEntity entityAdd : userOrgRoleList) {

			if (entityAdd
					.getEmpCode()
					.trim()
					.equals(userOrgRoleEntityTemp
							.getEmpCode().trim())
					&& entityAdd
							.getOrgCode()
							.trim()
							.equals(userOrgRoleEntityTemp
									.getOrgCode()
									.trim())
					&& entityAdd
							.getRoleCode()
							.trim()
							.equals(userOrgRoleEntityTemp
									.getRoleCode()
									.trim())) {
				flagC = true;
			}
		}
		if (!flagC) {
			userOrgRoleList.add(userOrgRoleEntityTemp);
		}
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>
	 * 验证当前的UUMS"用户信息"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午6:50:00
	 * @param tractor
	 *            UUMS"用户信息"信息参数实体
	 * @return FOSS"用户信息"信息实体
	 * @see
	 */
	private UserEntity vaidationUUMSUserInfoDataObjectIntoFOSS(UserInfoDeptAll userInfo) {
		if (null == userInfo || StringUtils.isBlank(userInfo.getEmpCode())) {
			return null;
		}
		UserEntity user = new UserEntity();
		user.setEmpCode(userInfo.getEmpCode());
		return userInformationService.queryUserListBySelective(user);
	}

	/**
	 * <p>
	 * 验证当前的UUMS"用户信息"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2013-1-7 上午10:30:08
	 * @param userCode
	 *            用户工号
	 * @param roleCode
	 *            角色编码
	 * @param 组织标杆编码
	 * @return FOSS"用户信息"信息实体
	 * @see
	 */
	private UserOrgRoleEntity vaidationUUMSUserOrgRoleInfoDataObjectIntoFOSS(
			String userCode, String roleCode, String orgUnifiedCode) {
		if (StringUtils.isBlank(userCode)
				|| StringUtils.isBlank(orgUnifiedCode)
				|| StringUtils.isBlank(roleCode)) {
			return null;
		}
		UserOrgRoleEntity userOrgRole = new UserOrgRoleEntity();
		userOrgRole.setEmpCode(userCode);
		userOrgRole.setOrgUnifiedCode(orgUnifiedCode);
		userOrgRole.setRoleCode(roleCode);
		return userInformationService.queryUserOrgRoleBySelective(userOrgRole);
	}

	/**
	 * <p>
	 * 对同步的UUMS对象的做成功和失败的记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午3:42:13
	 * @param tractor
	 *            请求的"用户信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SendUserInfoDeptAllProcessReult recordSynchronizedRequestDataError(
			UserInfoDeptAll userInfo, boolean result, String reason) {
		SendUserInfoDeptAllProcessReult sendUserInfoProcessReult = new SendUserInfoDeptAllProcessReult();
		sendUserInfoProcessReult.setAccountChangeId(userInfo
				.getAccountChangeId());
		sendUserInfoProcessReult.setResult(result);
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			sendUserInfoProcessReult.setReason(reason);
		}
		sendUserInfoProcessReult.setEmpCode(userInfo.getEmpCode());
		sendUserInfoProcessReult.setChangeType(userInfo.getChangeType());
		return sendUserInfoProcessReult;
	}

	/**
	 * @param userInformationService
	 *            the userInformationService to set
	 */
	public void setUserInformationService(
			IUserInformationService userInformationService) {
		this.userInformationService = userInformationService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	
	/**
	 * @set
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoService = orgAdministrativeInfoService
		 */
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * @set
	 * @param userDeptDataService
	 */
	public void setUserDeptDataService(IUserDeptDataService userDeptDataService) {
		this.userDeptDataService = userDeptDataService;
	}

}
