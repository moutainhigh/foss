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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SyncDistrictService.java
 * 
 * FILE NAME        	: SyncDistrictService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncDistrictRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.module.base.baseinfo.server.util.PinyinUtil;
import com.deppon.ows.inteface.domain.DistrictInfo;
import com.deppon.ows.inteface.domain.SyncDistrictRequest;

/**
 * 同步FOSS的行政区域信息给OWS系统
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-15 上午11:50:25
 */
public class SyncDistrictService implements ISyncDistrictService {

    private static final Logger log = LoggerFactory.getLogger(SyncDistrictService.class);

    private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNC_DISTRICT";

    /**
     * 同步FOSS的行政区域信息给OWS系统
     * 
     * @author 101911-foss-zhouChunlai
     * @date 2013-1-15 上午11:50:25
     */
    @Override
    public void syncDistrictDataToOws(
	    List<AdministrativeRegionsEntity> administrativeRegionsList) {
	try {
	    if (CollectionUtils.isNotEmpty(administrativeRegionsList)) {
		SyncDistrictRequest districtRequest = new SyncDistrictRequest();
		List<DistrictInfo> districtInfoLst = new ArrayList<DistrictInfo>();

		StringBuilder versionNos = new StringBuilder();
		StringBuilder codes = new StringBuilder();
		for (AdministrativeRegionsEntity entity : administrativeRegionsList) {
		    if (entity == null) {
			continue;
		    }
		    DistrictInfo districtInfo = this.transFossToEsb(entity);

		    districtInfoLst.add(districtInfo);

		    versionNos.append(SymbolConstants.EN_COMMA);
		    versionNos.append(entity.getVersionNo());
		    codes.append(SymbolConstants.EN_COMMA);
		    codes.append(entity.getCode());
		}
		
		districtRequest.getDistrictInfo().addAll(districtInfoLst);

		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(codes.toString());
		accessHeader.setBusinessDesc1("send district info to other platform.同步行政区域信息 到其它平台");
		accessHeader.setBusinessDesc2(versionNos.toString());
		accessHeader.setVersion("0.1");

		log.info("start to send dictrct info to other platform 开始 同步行政区域信息 到其它平台：\n"
			+ new SyncDistrictRequestTrans()
				.fromMessage(districtRequest));

		ESBJMSAccessor.asynReqeust(accessHeader, districtRequest);

		log.info("end to send dictrct info to other platform 结束 同步行政区域信息 到其它平台：\n"
			+ new SyncDistrictRequestTrans()
				.fromMessage(districtRequest));
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    throw new SynchronousExternalSystemException("同步行政区域信息 到其它平台", "同步行政区域信息 到其它平台 发送数据到ESB错误");
	}
    }

    private DistrictInfo transFossToEsb(AdministrativeRegionsEntity fossEntity) {
	if (null == fossEntity || StringUtils.isBlank(fossEntity.getCode()) ) {
	    return null;
	}

	DistrictInfo info = new DistrictInfo();

	// ID
	info.setId(fossEntity.getId());
	// 行政区域编码
	info.setCode(fossEntity.getCode());
	// 区域全称
	info.setName(fossEntity.getName());
	// 简称
	info.setSimpleName(fossEntity.getSimpleName());
	// 可用名称
	info.setAvailableName(fossEntity.getAvailableName());
	// 上级行政区域编码
	info.setParentDistrictCode(fossEntity.getParentDistrictCode());
	// 上级行政区域名称
	info.setParentDistrictName(fossEntity.getParentDistrictName());
	// 虚拟行政区域
	info.setVirtualDistrictId(fossEntity.getVirtualDistrictId());
	// 行政区域级别
	info.setDegree(fossEntity.getDegree());
	// 是否启用
	info.setActive(fossEntity.getActive());
	// 后缀
	info.setRegionSuffix(fossEntity.getRegionsuffix());

	// 创建时间
	info.setCreateTime(fossEntity.getCreateDate());
	// 更新时间
	info.setModifyTime(fossEntity.getCreateDate());
	// 是否启用
	info.setActive(fossEntity.getActive());
	// 创建人
	info.setCreateUserCode(fossEntity.getCreateUser());
	// 更新人
	info.setModifyUserCode(fossEntity.getModifyUser());
	// 转换城市名字为拼音
	info.setPinyin(PinyinUtil.hanziToPinYinWithNoSeparator(fossEntity.getName()));
	try {
		//设置行政区域拼音简码
		info.setPinyinAbbr(PinyinUtil.getPinYinHeadChar(fossEntity.getName()));
	} catch (BadHanyuPinyinOutputFormatCombination badFormatException) {
		//打印log日志
		log.info("区域名称转换成拼音输出格式错误" + badFormatException);
		//抛出异常
		throw new BusinessException("区域拼音转换异常");
	}
	//返回查询结果
	return info;
    }

}
