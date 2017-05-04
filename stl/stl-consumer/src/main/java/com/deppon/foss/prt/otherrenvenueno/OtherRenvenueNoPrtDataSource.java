/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/otherrenvenueno/OtherRenvenueNoPrtDataSource.java
 * 
 * FILE NAME        	: OtherRenvenueNoPrtDataSource.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.prt.otherrenvenueno;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.AmountConvertUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;

/**
 * 小票打印.
 *
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-29 上午8:33:40
 */
public class OtherRenvenueNoPrtDataSource implements JasperDataSource {
 
	// 注入日志
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(OtherRenvenueNoPrtDataSource.class);

	/**
	 * 设置前端的打印参数.
	 *
	 * @param jasperContext the jasper context
	 * @return the map
	 * @throws Exception the exception
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-5 上午9:52:37
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		logger.info("开始填充小票打印数据源");
		// 声明打印值map
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (jasperContext != null && jasperContext.getParamMap() != null) {

			String id = (String) jasperContext.get("id");
			if (StringUtils.isBlank(id)) {
				throw new SettlementException("查询参数不正确！");
			}
			//获取小票Service实例
			IOtherRevenueService otherRevenueService = (IOtherRevenueService) jasperContext.getApplicationContext().getBean("otherRevenueService");
			//根据ID获取小票信息
			OtherRevenueEntity otherRevenueEntity = otherRevenueService.queryOtherRevenueById(id);
			if(null==otherRevenueEntity){
				//查询参数不正确
				throw new SettlementException("查询参数不正确！");
			}
			//声名组织Service
			IOrgAdministrativeInfoService orgAdministrativeInfoService=null;
			//如果小票组织编码不为空
			//则实体化组织service
			if(StringUtils.isNotBlank(otherRevenueEntity.getCreateOrgCode())){
				orgAdministrativeInfoService=(IOrgAdministrativeInfoService) jasperContext.getApplicationContext().getBean("orgAdministrativeInfoService");
				//根据小票组织code获取组识信息
				OrgAdministrativeInfoEntity orgEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(otherRevenueEntity.getCreateOrgCode());
				if(null!=orgEntity){
					// 设置打印
					// 部门信息
					parameter.put("printDeptName", orgEntity.getName());
					parameter.put("printDeptCode", orgEntity.getCode());
					parameter.put("printDeptAddress", orgEntity.getAddress());
					parameter.put("printDepTelephone", orgEntity.getDepTelephone());
					parameter.put("printDepFax", orgEntity.getDepFax());					
				}
			}
			// 设置打印人信息
			parameter.put("printEmpName", otherRevenueEntity.getCreateUserName());
			parameter.put("printEmpCode", otherRevenueEntity.getCreateUserCode());
			//运单号
			parameter.put("waybillNo", otherRevenueEntity.getWaybillNo());
			//客户名称
			parameter.put("customerName", otherRevenueEntity.getCustomerName());
			//客户编码
			parameter.put("customerCode", otherRevenueEntity.getCustomerCode());
			//小票单号
			parameter.put("otherRevenueNo", otherRevenueEntity.getOtherRevenueNo());
			//付款类型
			parameter.put("paymentType", otherRevenueEntity.getPaymentType()); 
			//收入类别
			parameter.put("incomeCategories", otherRevenueEntity.getIncomeCategories()); 
			//付款类型
			parameter.put("paymentTypeName",  DictUtil.rendererSubmitToDisplay(otherRevenueEntity.getPaymentType(), "OTHER_REVENUE__PAYMENT_TYPE")); 
			//收入类别
			parameter.put("incomeCategoriesName", DictUtil.rendererSubmitToDisplay(otherRevenueEntity.getIncomeCategories(), "BILL_RECEIVABLE__COLLECTION_TYPE")); 
			//客户号码
			parameter.put("customerPhone", otherRevenueEntity.getCustomerPhone());
			//客户类型
			parameter.put("customerType", otherRevenueEntity.getCustomerType());
			//备注
			parameter.put("notes", otherRevenueEntity.getNotes()); 
			
			String amount = String.format("%23s%25s", "大写金额:"
					.concat(AmountConvertUtil
							.amountUppercase(otherRevenueEntity.getAmount())),
					"小写金额:￥".concat(otherRevenueEntity.getAmount().toString()));
			//金额
			parameter.put("amount", amount); 
			Date date = new Date();
			// 设置打印时间
			parameter.put("printTime",date);
			// 设置打印人
		}
		return parameter;
	}

	/**
	 * 设置打印jrxml中Detail模块中的内容.
	 *
	 * @param jasperContext the jasper context
	 * @return the list
	 * @throws Exception the exception
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-29 下午2:54:36
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		return null;
	}
}
