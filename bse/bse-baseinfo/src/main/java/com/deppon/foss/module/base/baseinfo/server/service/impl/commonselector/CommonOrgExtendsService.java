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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonOrgExtendsService.java
 * 
 * FILE NAME        	: CommonOrgExtendsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgExtendsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgExtendsService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共查询选择器--组织信息扩展添加营业部信息Service.
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-28 上午10:27:14
 */
public class CommonOrgExtendsService implements ICommonOrgExtendsService {

	/**
	 * 公共查询选择器--营业部和偏线代理网点信息查询 Dao
	 */
	private ICommonOrgExtendsDao commonOrgExtendsDao;

	/**
	 * 组织查询扩展添加营业部信息查询
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午10:51:59
	 * @return
	 */
	@Override
	public List<CommonOrgDto> searchCommonOrgExtendsByCondition(
			CommonOrgDto dto, int start, int limit) {
		CommonOrgDto condition=getSearchCondtion(dto);
		return commonOrgExtendsDao.searchCommonOrgExtendsByCondition(condition,start, limit);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午11:08:51
	 * @return
	 */
	@Override
	public long countReocrd(CommonOrgDto dto) {
		CommonOrgDto condition=getSearchCondtion(dto);
		return commonOrgExtendsDao.countReocrd(condition);
	}

	/**
	 * 构造查询条件.
	 *
	 * @param commonOrgDto the org serch condtion
	 * @return OrgSearchCondition
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-17 下午1:38:51
	 */
	private CommonOrgDto getSearchCondtion(CommonOrgDto commonOrgDto) {
		// 如果部门属性不为空，这设置部门属性是'Y'
		if (StringUtil.isEmpty(commonOrgDto.getDivision())
				&& StringUtil.isEmpty(commonOrgDto.getAirDispatch())
				&& StringUtil.isEmpty(commonOrgDto.getBigRegion())
				&& StringUtil.isEmpty(commonOrgDto.getBillingGroup())
				&& StringUtil.isEmpty(commonOrgDto.getDispatchTeam())
				&& StringUtil.isEmpty(commonOrgDto.getDoAirDispatch())
				&& StringUtil.isEmpty(commonOrgDto.getIsArrangeGoods())
				&& StringUtil.isEmpty(commonOrgDto.getIsDeliverSchedule())
				&& StringUtil.isEmpty(commonOrgDto.getIsEntityFinance())
				&& sonarSplitOne(commonOrgDto) 
				) {
			commonOrgDto.setNature(FossConstants.YES);
		} else {
			commonOrgDto.setNature(null);
		}
		if(CollectionUtils.isNotEmpty(commonOrgDto.getTypes())){
			List<String> queryTypes=new ArrayList<String>();
			for(String tp : commonOrgDto.getTypes()){
				if(StringUtils.equals(DictionaryValueConstants.DEPPON_OWN_ORG,tp)){
					commonOrgDto.setType(tp);
				}else{
					queryTypes.add(tp); 
				}
			}
			commonOrgDto.setQueryTypes(queryTypes);
		}
		return commonOrgDto;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private boolean sonarSplitOne(CommonOrgDto commonOrgDto) {
		return StringUtil.isEmpty(commonOrgDto.getSalesDepartment())
				&& StringUtil.isEmpty(commonOrgDto.getSmallRegion())
				&& StringUtil.isEmpty(commonOrgDto.getTransDepartment())
				&& StringUtil.isEmpty(commonOrgDto.getTransferCenter())
				&& StringUtil.isEmpty(commonOrgDto.getTransTeam())
				&& StringUtil.isEmpty(commonOrgDto.getLeave())
				&& StringUtil.isEmpty(commonOrgDto.getArrive())
				&& StringUtil.isEmpty(commonOrgDto.getStation())
				&& StringUtil.isEmpty(commonOrgDto.getPickupSelf())
				&& StringUtil.isEmpty(commonOrgDto.getAirArrive())
				&& StringUtil.isEmpty(commonOrgDto.getTruckArrive())
				&& StringUtil.isEmpty(commonOrgDto.getInCentralizedShuttle())
				&& StringUtil.isEmpty(commonOrgDto.getCanPayServiceFee())
				&& StringUtil.isEmpty(commonOrgDto.getCanReturnSignBill())
				&& StringUtil.isEmpty(commonOrgDto.getCanCashOnDelivery())
				&& StringUtil.isEmpty(commonOrgDto.getCanAgentCollected())
				&& StringUtil.isEmpty(commonOrgDto.getTransferGoodDept())
				&& StringUtil.isEmpty(commonOrgDto.getStationNumber());
	}
	
	public ICommonOrgExtendsDao getCommonOrgExtendsDao() {
		return commonOrgExtendsDao;
	}

	
	public void setCommonOrgExtendsDao(ICommonOrgExtendsDao commonOrgExtendsDao) {
		this.commonOrgExtendsDao = commonOrgExtendsDao;
	}

}
