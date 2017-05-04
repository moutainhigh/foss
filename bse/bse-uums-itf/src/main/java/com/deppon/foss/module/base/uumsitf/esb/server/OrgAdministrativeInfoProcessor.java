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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/OrgAdministrativeInfoProcessor.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.jaxb.SendAdminOrgRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendAdminOrgResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgAdministrativeInfoException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.orgsync.api.server.service.IOrgAdministrativeInfoRemindService;
import com.deppon.foss.module.base.uumsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.uums.inteface.domain.usermanagement.AdminOrgInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgResponse;

/**
 * 用来从UUMS同步"行政组织"到FOSS数据库：无SUC
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-9 上午11:00:57
 */
/**
 * 
 * 修订记录 日期 修订内容 修订人员 版本号 2012-05-28 新增 周海根 V0.1 2012-07-12 1，删除前置条件。
 * 2，修改后置条件为“可修改_查询行政组织业务属性”。 3，修改了业务规则。 4，处理日志可以从接口调用日志中获取，没必要单独再记录。因此，去掉了处理日志。
 * 李俊 V0.2
 * 
 * 1. SUC-647-同步行政组织接口 1.1 相关业务用例 无
 * 
 * 1.2 用例描述 该系统用例主要为UUMS提供更新组织架构服务。
 * 
 * 1.3 用例条件 条件类型 描述 引用系统用例 前置条件 后置条件 1、 可修改_查询行政组织业务属性。 SUC-85修改_查询行政组织业务属性
 * 
 * 1.4 操作用户角色 操作用户 描述
 * 
 * 
 * 1.5 界面要求 1.5.1 表现方式 无相关界面
 * 
 * 1.5.2 界面原型-主界面
 * 
 * 1.5.3 界面描述-主界面
 * 
 * 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 传入参数校验,验证密码,见SR1 【组织架构数据(输入)】 2
 * 将输入数据按照组织状态和组织代码逐条写入数据库，见SR2 【组织架构数据(输入)】 3 写接口调用日志 【接口调用日志】 4 返回调用结果
 * 【返回参数(输出)】
 * 
 * 序号 扩展事件 相关数据 备注 1a 若验证密码失败，则停止同步。 【返回参数(输出)】
 * 
 * 1.7 业务规则 序号 描述 SR-1 验证密码失败后，不进行组织架构更新，直接返回失败信息. SR-2
 * 如果组织状态是启用且组织的标杆编码不在FOSS中，
 * 则将组织数据插入数据库；如果组织状态是启用且组织的标杆编码在FOSS中或组织状态不是启用，则根据组织标杆编码查询更新当前数据库的记录 SR-3
 * 如果组织状态不是“启动”且组织标杆编码不在FOSS中，则返回错误信息； SR-4 如果上级组织标杆编码不在FOSS中，则返回错误信息； SR-5
 * 如果子公司标杆编码不在FOSS中，则返回错误信息； SR-6 如果成本中心编码不在FOSS中，则返回错误信息； SR-7 作废日期不能早于启用日期；
 * 
 * 1.8 数据元素 1.8.1 组织架构数据（输入） 字段名称 说明 输入限制 长度 是否必填 备注 用户名 调用接口的用户名 文本 50 密码
 * 调用接口的验证密码 文本 50 组织编码 OA的行政编码，W+6位编码（可根据组织编码找到上级组织） 文本 10 组织名称 行政组织名称 文本 50
 * 组织标杆编码 DP+6位数字编码（唯一） 文本 10 组织负责人 组织负责人工号 文本 10 上级组织标杆编码 DP+6位数字编码 文本 10
 * 所属子公司编码 所属子公司标杆编码 文本 10 所属成本中心编码 所属成本中心标杆编码 文本 10 组织状态 启用、预作废、作废、修改 文本 10
 * 作废日期 组织作废时间 日期 20 启用日期 组织启用时间 日期 20 1.8.2 接口调用日志 字段名称 说明 输入限制 长度 是否必填 备注 用户名
 * 调用接口的用户名 文本 10 UUMS 调用服务名称 服务名称 文本 20 更新组织架构 调用类型 接口调用的类型（调用，被调用） 文本 20 调用时间
 * 调用时间 日期时间 20 调用结果 成功、失败标志 布尔值 10 调用详细内容 输入的具体内容 文本 1000 1.8.3 接口调用返回结果(输出)
 * 字段名称 说明 输入限制 长度 是否必填 备注 状态码 成功、失败标志 文本 10 是 描述 如果调用失败，描述失败的具体原因 文本 500 否
 * 
 * 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 使用时间段 高峰使用时间段
 * 
 * 1.10 接口描述 接口名称 对方系统（外部系统或内部其他模块） 接口描述
 */
public class OrgAdministrativeInfoProcessor implements IProcess {
	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrgAdministrativeInfoProcessor.class);
	/**
	 * 同步"行政组织信息"接口结果操作Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 撤销组织提醒"接口结果操作Service
	 */
	private IOrgAdministrativeInfoRemindService orgAdministrativeInfoRemindService;

	// 业务锁
	private IBusinessLockService businessLockService;
	
	
	// 成功失败次数声明
	int	successCount = 0, failCount = 0;
	@Override
	public Object process(Object req) throws ESBBusinessException {
		// 打印日志
		LOGGER.info(" ***************************** Began to record data ***************************** ");
		// 声明sendAdminOrgRequest对象
		SendAdminOrgRequest sendAdminOrgRequest = (SendAdminOrgRequest) req;
		// 声明sendAdminOrgResponse对象
		SendAdminOrgResponse sendAdminOrgResponse = new SendAdminOrgResponse();
		// 打印日志信息
		LOGGER.info("UUMS invoke FOSS network interface,send t_bas_org info,request msg UUMS调用FOSS同步行政组织接口，请求报文:\n"
				+ new SendAdminOrgRequestTrans()
						.fromMessage(sendAdminOrgRequest));
		// request对象非空验证
		if (null != sendAdminOrgRequest) {
			List<SendAdminOrgProcessReult> detailList = sendAdminOrgResponse
					.getProcessResult();
			// 遍历
			for (AdminOrgInfo adminOrgInfo : sendAdminOrgRequest
					.getAdminOrgInfo()) {
				try {
					if(adminOrgInfo==null){
						throw new OrgAdministrativeInfoException("同步数据为空");	
					}
					// 业务锁
					MutexElement mutex = new MutexElement(
							adminOrgInfo.getOrgCode(), "UUMS_ORG_CODE",
							MutexElementType.UUMS_ORG_CODE);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,
							NumberConstants.NUMBER_10);
					if (result) {
						saveOrUpdateAdminOrgInfo(adminOrgInfo, mutex);
					} else {

						// 错误次数加1
						failCount++;
						detailList.add(this.recordSynchronizedRequestDataError(
								adminOrgInfo, false, "UUMS0102:出现了高并发操作！"));
						// 打印错误信息
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						// 继续执行
						continue;
					}
					// 成功次数加1
					successCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							adminOrgInfo, true, null));
				} catch (Exception e) {
					// 错误次数加1
					failCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							adminOrgInfo, false, e.getMessage()));
					// 打印错误信息
					LOGGER.error(e.getMessage(), e);
					// 继续执行
					continue;
				}
			}
			// 设置执行的成功次数
			sendAdminOrgResponse.setSuccessCount(successCount);
			// 设置执行的失败次数
			sendAdminOrgResponse.setFailedCount(failCount);
			// 打印日志
			LOGGER.info("UUMS invoke FOSS network interface,send t_bas_org info,return msg UUMS调用FOSS同步行政组织接口，返回报文:\n"
					+ new SendAdminOrgResponseTrans()
							.fromMessage(sendAdminOrgResponse));
		}
		// 打印日志
		LOGGER.info(" *****************************同步 行政组织 End to record data ***************************** ");
		// 返回request对象
		return sendAdminOrgResponse;
	}

	private void saveOrUpdateAdminOrgInfo(AdminOrgInfo adminOrgInfo,
			MutexElement mutex) {
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		// 数据合法性判断
		if (adminOrgInfo == null
				|| StringUtils.isBlank(adminOrgInfo.getOrgCode())
				|| StringUtils.isBlank(adminOrgInfo.getOrgBenchmarkCode())) {
			throw new OrgAdministrativeInfoException(
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR_CODE,
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR);
		}
		// 操作类别
		String operateType = adminOrgInfo.getChangeType();
		// 根据数据组织标杆编码查询行政组织信息
		OrgAdministrativeInfoEntity entityResult = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCodeNoCache(adminOrgInfo
						.getOrgBenchmarkCode());

		// 声明一个行政组织对象
		OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();

		/**
		 * operateType 处理规则取自文件UUMS_SEND_ADMINORG.xsd 1-新增, 2-修改, 3-异动, 4-待撤销,
		 * 5-已撤销
		 * 
		 * 经过与UUMS沟通确认，1为新增；2，3，4为修改；5为作废 才【以后只看5】， 其他根据组织是否存在判断
		 */
		// 处理新增，修改
		if (StringUtils.equals(NumberConstants.NUMERAL_S_ONE, operateType)
				|| StringUtils.equals(NumberConstants.NUMERAL_S_TWO,
						operateType)
				|| StringUtils.equals(NumberConstants.NUMERAL_S_THREE,
						operateType)
				|| StringUtils.equals(NumberConstants.NUMERAL_S_FOUR,
						operateType)) {
			/**
			 * 130566-ZJF 接口优化
			 */
			// 如果对象已存在： 说明做修改
			if (entityResult != null) {
				/*
				 * throw new OrgAdministrativeInfoException(
				 * DataRuleMessageConstant
				 * .DATA_RULE_REASON_OBJECT_EXIST_ERROR_CODE,
				 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_EXIST_ERROR);
				 */
				// BUG-27648：乔立峰 ：
				// 1、如果涉及部门名称修改执行该操作
				if (StringUtils.isEmpty(adminOrgInfo.getOrgName())
						|| !adminOrgInfo.getOrgName().trim()
								.equals(entityResult.getName().trim())) {

					saveOrupdate(adminOrgInfo);
				}

				// 实体对象类型转换
				entity = this.changeEsbToFossByUpdate(adminOrgInfo,
						entityResult);
				entity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);

				// 根据同步的动作执行对应的"修改"
				entity = orgAdministrativeInfoService
						.updateOrgAdministrativeInfo(entity, true);
				// 更新时，要判断是否要做“撤销组织提醒”。组织状态, 0-正常, 1-待撤销, 2-已撤销
				if (StringUtils.equals(entity.getStatus(),
						NumberConstants.NUMERAL_S_ONE)) {
					this.finishWork(entity);
				}
			} else {
				// 如果组织不存在， 说明做新增操作
				// 对象类型转换
				entity = this.changeEsbToFoss(adminOrgInfo, entity);
				entity.setCreateUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				entity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				/**
				 * 注：添加的快递点部，快递区域营业部，快递大区 在同步过来的时候先设为'N'
				 */
				// 快递大区
				entity.setExpressBigRegion(FossConstants.NO);
				// 快递虚拟营业部
				entity.setExpressSalesDepartment(FossConstants.NO);
				// 快递点部
				entity.setExpressPart(FossConstants.NO);
				// 根据同步的动作执行对应的"新增"
				orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
			}

		} else if (StringUtils.equals(NumberConstants.NUMERAL_S_FIVE,
				operateType)) {
			// 处理作废
			// 如果刪除的对象不存在：
			if (entityResult == null) {
				// 抛出异常
				throw new OrgAdministrativeInfoException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR);
			}
			// 实体对象类型转换
			entity = this.changeEsbToFoss(adminOrgInfo, entityResult);
			entity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
			// 根据同步的动作执行对应的"作废"
			orgAdministrativeInfoService.deleteOrgAdministrativeInfo(entity);
		}
		// 操作类型判断
		// 判断是否为新增 更新
		// 删除 异动
		/**
		 * else if (StringUtils.equals( NumberConstants.NUMERAL_S_TWO,
		 * operateType) || StringUtils.equals( NumberConstants.NUMERAL_S_THREE,
		 * operateType) || StringUtils.equals( NumberConstants.NUMERAL_S_FOUR,
		 * operateType)) { // 处理更新 // 如果修改的对象不存在： if (entityResult == null) {
		 * throw new OrgAdministrativeInfoException(
		 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
		 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR); }
		 * 
		 * //BUG-27648：乔立峰 ： //1、如果涉及部门名称修改执行该操作
		 * if(StringUtils.isEmpty(adminOrgInfo.getOrgName())
		 * ||!adminOrgInfo.getOrgName
		 * ().trim().equals(entityResult.getName().trim())){
		 * 
		 * //2、根据标杆编码code查询其所有子组织信息 List<OrgAdministrativeInfoEntity>
		 * entityResultChildList = orgAdministrativeInfoService
		 * .queryOrgAdministrativeInfoByUnifiedCodeNoCacheList
		 * (adminOrgInfo.getOrgBenchmarkCode());
		 * 
		 * //3、修改子组织信息 if(CollectionUtils.isNotEmpty(entityResultChildList)){
		 * //循环处理 for(OrgAdministrativeInfoEntity
		 * entityChild:entityResultChildList){ //修改网点名称\修改人
		 * entityChild.setParentOrgName(adminOrgInfo.getOrgName().trim());
		 * entityChild
		 * .setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
		 * orgAdministrativeInfoService.updateOrgAdministrativeInfo(entityChild,
		 * true); } } }
		 * 
		 * // 实体对象类型转换 entity = this.changeEsbToFossByUpdate(adminOrgInfo,
		 * entityResult);
		 * entity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
		 * 
		 * 
		 * // 根据同步的动作执行对应的"修改" entity = orgAdministrativeInfoService
		 * .updateOrgAdministrativeInfo(entity, true); //
		 * 更新时，要判断是否要做“撤销组织提醒”。组织状态, 0-正常, 1-待撤销, 2-已撤销 if
		 * (StringUtils.equals(entity.getStatus(),
		 * NumberConstants.NUMERAL_S_ONE)) { this.finishWork(entity); } }
		 */
		else {
			// 抛出错误信息
			throw new OrgAdministrativeInfoException(
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR_CODE,
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		}
		LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("完成解锁：" + mutex.getBusinessNo());
	}

	private void saveOrupdate(AdminOrgInfo adminOrgInfo) {
		//2、根据标杆编码code查询其所有子组织信息
		List<OrgAdministrativeInfoEntity> entityResultChildList = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCodeNoCacheList(adminOrgInfo.getOrgBenchmarkCode());
		
		//3、修改子组织信息
		if(CollectionUtils.isNotEmpty(entityResultChildList)){
			//循环处理
			for(OrgAdministrativeInfoEntity entityChild:entityResultChildList){
				//修改网点名称\修改人
				entityChild.setParentOrgName(adminOrgInfo.getOrgName().trim());
				entityChild.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				orgAdministrativeInfoService.updateOrgAdministrativeInfo(entityChild, true);
			}
		}
	}

	/**
	 * 
	 * 错误处理方法
	 * 
	 * 
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// 返回为空
		return null;
	}

	/**
	 * 将ESB请求的 行政组织 对象转换成FOSS的对象
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-9 下午4:13:50
	 */
	private OrgAdministrativeInfoEntity changeEsbToFoss(AdminOrgInfo esbInfo,
			OrgAdministrativeInfoEntity fossEntity)
			throws OrgAdministrativeInfoException {
		// esbInfo对象非空判断
		if (esbInfo == null) {
			// 返回为空
			return null;
		}
		// 验证参数的合法性
		if (StringUtils
				.equals(esbInfo.getOrgCode(), esbInfo.getParentOrgCode())) {
			// 抛出异常信息
			throw new OrgAdministrativeInfoException(
					"组织编码OrgCode与组织的上组织编码ParentOrgCode不能相同");
		}
		// 三目运算符判断参数是否为空，为空则声明新对象
		OrgAdministrativeInfoEntity entity = fossEntity == null ? new OrgAdministrativeInfoEntity()
				: fossEntity;
		if (StringUtils.isNotBlank(esbInfo.getOrgCode())) {
			// 组织编码
			entity.setCode(esbInfo.getOrgCode().trim());
		} else {
			// 组织编码
			entity.setCode(esbInfo.getOrgCode());
		}

		// 组织名称
		entity.setName(esbInfo.getOrgName());
		if (StringUtils.isNotBlank(esbInfo.getOrgBenchmarkCode())) {
			// 组织标杆编码
			entity.setUnifiedCode(esbInfo.getOrgBenchmarkCode().trim());
		} else {
			// 组织标杆编码
			entity.setUnifiedCode(esbInfo.getOrgBenchmarkCode());
		}

		// 组织负责人（工号）
		entity.setPrincipalNo(esbInfo.getOrgManager());
		// 组织联系电话
		entity.setDepTelephone(esbInfo.getOrgPhone());
		// 组织传真号码
		entity.setDepFax(esbInfo.getOrgFax());
		if (StringUtils.isNotBlank(esbInfo.getParentOrgBenchmarkCode())) {
			// 上级组织标杆编码
			entity.setParentOrgUnifiedCode(esbInfo.getParentOrgBenchmarkCode()
					.trim());
		} else {
			// 上级组织标杆编码
			entity.setParentOrgUnifiedCode(esbInfo.getParentOrgBenchmarkCode());
		}
		// 根据上级组织标杆编码查询出上级组织名称  BUG-50827  foss-132599-shenweihua
		String parentOrgUnifiedCode = orgAdministrativeInfoService.queryParentOrgNameByParentOrgUnifiedCode(entity.getParentOrgUnifiedCode());
		if(StringUtils.isNotBlank(parentOrgUnifiedCode)){
			entity.setParentOrgName(parentOrgUnifiedCode);
		}
		// 暂不写入数据库 所属子公司名称
		entity.setSubsidiaryName(esbInfo.getSubCompName());
		if (StringUtils.isNotBlank(esbInfo.getSubCompCode())) {
			// 所属子公司编码
			entity.setSubsidiaryCode(esbInfo.getSubCompCode().trim());
		} else {
			// 所属子公司编码
			entity.setSubsidiaryCode(esbInfo.getSubCompCode());
		}
		if (StringUtils.isNotBlank(esbInfo.getCostCenterCode())) {
			// 所属成本中心编码
			entity.setCostCenterCode(esbInfo.getCostCenterCode().trim());
		} else {
			// 所属成本中心编码
			entity.setCostCenterCode(esbInfo.getCostCenterCode());
		}

		// 所属成本中心名字
		entity.setCostCenterName(esbInfo.getCostCenterName());
		// 组织地址
		entity.setAddress(esbInfo.getOrgAddress());
		// 组织状态, 0-正常, 1-待撤销, 2-已撤销
		entity.setStatus(esbInfo.getOrgStatus());
		// 启用日期
		entity.setBeginTime(esbInfo.getValidDate());
		// 作废日期
		entity.setEndTime(esbInfo.getInvalidDate());
		// 是否事业部
		entity.setDivision(esbInfo.isIsCareerDept() ? FossConstants.YES
				: FossConstants.NO);
		// 是否大区
		entity.setBigRegion(esbInfo.isIsBigArea() ? FossConstants.YES
				: FossConstants.NO);
		// 是否小区
		entity.setSmallRegion(esbInfo.isIsSmallArea() ? FossConstants.YES
				: FossConstants.NO);
		// 是否大区
		entity.setBigRegion(boolToYesNo(esbInfo.isIsBigArea()));
		// 是否小区
		entity.setSmallRegion(boolToYesNo(esbInfo.isIsSmallArea()));
		// 部门简称
		entity.setOrgSimpleName(esbInfo.getDeptShortName());
		// 部门备注描述信息
		entity.setDeptDesc(esbInfo.getOrgDesc());
		if (StringUtils.isNotBlank(esbInfo.getParentOrgCode())) {
			// 上级组织编码
			entity.setParentOrgCode(esbInfo.getParentOrgCode().trim());
		} else {
			// 上级组织编码
			entity.setParentOrgCode(esbInfo.getParentOrgCode());
		}

		// UUMS主键ID
		entity.setUumsId(esbInfo.getOrgId());
		// UUMS上级主键ID
		entity.setUumsParentId(esbInfo.getParentOrgId());
		// UUMS主键ID序列
		entity.setUumsIds(esbInfo.getOrgSeq());
		// 是否为叶子节点
		entity.setIsLeaf(boolToYesNo(esbInfo.isIsLeaf()));
		// 显示顺序
		entity.setDisplayOrder(esbInfo.getDisplayOrder());
		// 部门层级
		entity.setDeptLevel(esbInfo.getOrgLevel());
		// 地区编码默认拼音
		entity.setAreaCode(esbInfo.getAreaCode());
		// 组织邮箱
		entity.setDeptEmail(esbInfo.getOrgEmail());
		// 邮编号码
		entity.setZipCode(esbInfo.getOrgZipCode());
		// 组织性质
		entity.setDeptAttribute(esbInfo.getOrgAttribute());
		// 已封存系统
		entity.setCanceledSystems(esbInfo.getCanceledSystems());
		if (StringUtils.isNotBlank(esbInfo.getEhrDeptCode())) {
			// EHR部门编码
			entity.setEhrDeptCode(esbInfo.getEhrDeptCode().trim());
		} else {
			// EHR部门编码
			entity.setEhrDeptCode(esbInfo.getEhrDeptCode());
		}

		// 返回entity对象
		return entity;
	}

	 /**
     * 将ESB请求的 行政组织 对象转换成FOSS的对象(这个只在更新的时候使用的方法)
     * 
     * @author foss-zhangxiaohui
     * @date 2013-06-03 下午9:26:50
     */
    private OrgAdministrativeInfoEntity changeEsbToFossByUpdate(AdminOrgInfo esbInfo,OrgAdministrativeInfoEntity fossEntity)
	    throws OrgAdministrativeInfoException {
    	//esbInfo对象非空判断
		if (esbInfo == null) {
			//返回为空
		    return null;
		}
		// 验证参数的合法性
		if (StringUtils.equals(esbInfo.getOrgCode(), esbInfo.getParentOrgCode())) {
			//抛出异常信息
		    throw new OrgAdministrativeInfoException("组织编码OrgCode与组织的上组织编码ParentOrgCode不能相同");
		}
		//三目运算符判断参数是否为空，为空则声明新对象
		OrgAdministrativeInfoEntity entity = fossEntity == null ? new OrgAdministrativeInfoEntity() : fossEntity;
		//更新时组织编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgCode())){
		    // 组织编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setCode(esbInfo.getOrgCode().trim());
		}
		//更新时组织名称非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgName())){
		    // 组织名称不为空则取传过来的对象的值set到Foss数据库
		    entity.setName(esbInfo.getOrgName().trim());
		}
		//更新时组织标杆编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgBenchmarkCode())){
		    // 组织标杆编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setUnifiedCode(esbInfo.getOrgBenchmarkCode().trim());
		}
		// 根据上级组织标杆编码查询出上级组织名称  BUG-50827  foss-132599-shenweihua
		String parentOrgUnifiedCode = orgAdministrativeInfoService.queryParentOrgNameByParentOrgUnifiedCode(entity.getParentOrgUnifiedCode());
		if(StringUtils.isNotBlank(parentOrgUnifiedCode)){
			entity.setParentOrgName(parentOrgUnifiedCode);
		}
		//更新时组织负责人（工号）非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgManager())){
		    // 组织负责人（工号）不为空则取传过来的对象的值set到Foss数据库
		    entity.setPrincipalNo(esbInfo.getOrgManager().trim());
		}
//		//更新时组织联系电话非空判断
//		if(StringUtils.isNotBlank(esbInfo.getOrgPhone())){
//		    // 组织联系电话不为空则取传过来的对象的值set到Foss数据库
//		    entity.setDepTelephone(esbInfo.getOrgPhone().trim());
//		}
//		//更新时组织传真号码非空判断
//		if(StringUtils.isNotBlank(esbInfo.getOrgFax())){
//		    // 组织传真号码不为空则取传过来的对象的值set到Foss数据库
//		    entity.setDepFax(esbInfo.getOrgFax().trim());
//		}
		//更新时上级组织标杆编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getParentOrgBenchmarkCode())){
		    // 上级组织标杆编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setParentOrgUnifiedCode(esbInfo.getParentOrgBenchmarkCode().trim());
		}
		//更新时所属子公司名称非空判断
		if(StringUtils.isNotBlank(esbInfo.getSubCompName())){
		    // 所属子公司名称不为空则取传过来的对象的值set到Foss数据库
		    entity.setSubsidiaryName(esbInfo.getSubCompName().trim());
		}
		//更新时所属子公司编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getSubCompCode())){
		    // 所属子公司编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setSubsidiaryCode(esbInfo.getSubCompCode().trim());
		}
		//更新时所属成本中心编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getCostCenterCode())){
		    // 所属成本中心编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setCostCenterCode(esbInfo.getCostCenterCode().trim());
		}
		//更新时所属成本中心名字非空判断
		if(StringUtils.isNotBlank(esbInfo.getCostCenterName())){
		    // 所属成本中心不为空则取传过来的对象的值set到Foss数据库
		    entity.setCostCenterName(esbInfo.getCostCenterName().trim());
		}
		//更新时组织地址非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgAddress())){
		    // 组织地址不为空则取传过来的对象的值set到Foss数据库
		    entity.setAddress(esbInfo.getOrgAddress().trim());
		}
		//更新时组织状态非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgStatus())){
		    // 组织状态不为空则取传过来的对象的值set到Foss数据库(0-正常, 1-待撤销, 2-已撤销)
		    entity.setStatus(esbInfo.getOrgStatus().trim());
		}
		//更新时启用日期非空判断
		if(null== esbInfo.getValidDate()){
		    // 启用日期不为空则取传过来的对象的值set到Foss数据库(0-正常, 1-待撤销, 2-已撤销)
		    entity.setBeginTime(esbInfo.getValidDate());
		}
		//更新时作废日期非空判断
		if(null== esbInfo.getInvalidDate()){
		    // 作废日期不为空则取传过来的对象的值set到Foss数据库(0-正常, 1-待撤销, 2-已撤销)
		    entity.setEndTime(esbInfo.getInvalidDate());
		}
		/**
		 * 由于UUMS暂时没有维护事业部大区小区的界面，所以更新时不更新FOSS已经维护的事业部大区小区属性
		 */
		
//		// 是否事业部
//		entity.setDivision(esbInfo.isIsCareerDept() ? FossConstants.YES
//			: FossConstants.NO);
//		// 是否大区
//		entity.setBigRegion(esbInfo.isIsBigArea() ? FossConstants.YES
//			: FossConstants.NO);
//		// 是否小区
//		entity.setSmallRegion(esbInfo.isIsSmallArea() ? FossConstants.YES
//			: FossConstants.NO);
//		// 是否大区
//		entity.setBigRegion(boolToYesNo(esbInfo.isIsBigArea()));
//		// 是否小区
//		entity.setSmallRegion(boolToYesNo(esbInfo.isIsSmallArea()));
//		//更新时部门简称非空判断
//		if(StringUtils.isNotBlank(esbInfo.getDeptShortName())){
//		    // 部门简称不为空则取传过来的对象的值set到Foss数据库
//		    entity.setOrgSimpleName(esbInfo.getDeptShortName().trim());
//		}
		//更新时部门备注描述信息非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgDesc())){
		    // 部门备注描述信息不为空则取传过来的对象的值set到Foss数据库
		    entity.setDeptDesc(esbInfo.getOrgDesc().trim());
		}
		//更新时上级组织编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getParentOrgCode())){
		    // 上级组织编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setParentOrgCode(esbInfo.getParentOrgCode().trim());
		}
		// UUMS主键ID
		entity.setUumsId(esbInfo.getOrgId());
		// UUMS上级主键ID
		entity.setUumsParentId(esbInfo.getParentOrgId());
		// UUMS主键ID序列
		entity.setUumsIds(esbInfo.getOrgSeq());
		// 是否为叶子节点
		entity.setIsLeaf(boolToYesNo(esbInfo.isIsLeaf()));
		// 显示顺序
		entity.setDisplayOrder(esbInfo.getDisplayOrder());
		//更新时部门层级非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgLevel())){
			// 部门层级不为空则取传过来的对象的值set到Foss数据库
			entity.setDeptLevel(esbInfo.getOrgLevel());
		}
		//更新时地区编码默认拼音非空判断
		if(StringUtils.isNotBlank(esbInfo.getAreaCode())){
		    // 地区编码默认拼音不为空则取传过来的对象的值set到Foss数据库
		    entity.setAreaCode(esbInfo.getAreaCode().trim());
		}
		//更新时组织邮箱非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgEmail())){
		    // 组织邮箱不为空则取传过来的对象的值set到Foss数据库
		    entity.setDeptEmail(esbInfo.getOrgEmail().trim());
		}
		//更新时邮编号码非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgZipCode())){
		    // 邮编号码不为空则取传过来的对象的值set到Foss数据库
		    entity.setZipCode(esbInfo.getOrgZipCode().trim());
		}
		//更新时组织性质非空判断
		if(StringUtils.isNotBlank(esbInfo.getOrgAttribute())){
		    // 组织性质不为空则取传过来的对象的值set到Foss数据库
		    entity.setDeptAttribute(esbInfo.getOrgAttribute().trim());
		}
		//更新时已封存系统非空判断
		if(StringUtils.isNotBlank(esbInfo.getCanceledSystems())){
		    // 已封存系统不为空则取传过来的对象的值set到Foss数据库
		    entity.setCanceledSystems(esbInfo.getCanceledSystems().trim());
		}
		//更新时EHR部门编码非空判断
		if(StringUtils.isNotBlank(esbInfo.getEhrDeptCode())){
		    // EHR部门编码不为空则取传过来的对象的值set到Foss数据库
		    entity.setEhrDeptCode(esbInfo.getEhrDeptCode().trim());
		}
		
		//返回entity对象
		return entity;
    }
    
	/**
	 * 撤销组织提醒
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-27 下午4:29:29
	 */
	private boolean finishWork(OrgAdministrativeInfoEntity entity) {
		// 对象非空验证
		if (entity == null) {
			// 抛出异常信息
			throw new OrgAdministrativeInfoException("组织不存在");
		}
		// 打印日志信息
		LOGGER.info("开始调 撤销组织提醒");
		// 执行撤销动作
		return orgAdministrativeInfoRemindService
				.cancelOrgAdministrativeInfoRemind(entity);
	}

	/**
	 * 对同步的UUMS对象的做成功和失败的记录
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-27 下午4:03:41
	 * @param tractor
	 *            请求的"职等信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SendAdminOrgProcessReult recordSynchronizedRequestDataError(
			AdminOrgInfo adminOrgInfo, boolean result, String reason) {
		// 声明SendAdminOrgProcessReult对象
		SendAdminOrgProcessReult sendAdminancialOrgProcessReult = new SendAdminOrgProcessReult();
		// 设置OrgChangeId
		sendAdminancialOrgProcessReult.setOrgChangeId(adminOrgInfo
				.getOrgChangeId());
		// 设置Result
		sendAdminancialOrgProcessReult.setResult(result);
		// 设置组织名称
		sendAdminancialOrgProcessReult.setOrgName(adminOrgInfo.getOrgName());
		// 设置标杆编码
		sendAdminancialOrgProcessReult.setOrgBenchmarkCode(adminOrgInfo
				.getOrgBenchmarkCode());
		// 设置修改类型
		sendAdminancialOrgProcessReult.setChangeType(adminOrgInfo
				.getChangeType());
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			// 设置失败原因
			sendAdminancialOrgProcessReult.setReason(reason);
		}
		// 返回结果
		return sendAdminancialOrgProcessReult;
	}

	/**
	 * 下面是工具方法
	 */
	public static String boolToYesNo(boolean yes) {
		// 三目运算符判断
		return yes ? FossConstants.YES : FossConstants.NO;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoRemindService
	 */
	public void setOrgAdministrativeInfoRemindService(
			IOrgAdministrativeInfoRemindService orgAdministrativeInfoRemindService) {
		this.orgAdministrativeInfoRemindService = orgAdministrativeInfoRemindService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}