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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSAdvertisingSloganService.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService;
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSAdVertisingSloganException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 短信广告语service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 上午11:31:31
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 上午11:31:31
 * @since
 * @version
 */
public class SMSAdvertisingSloganService implements
	ISMSAdvertisingSloganService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SMSAdvertisingSloganService.class);

    /**
     * 短信广告语Dao接口.
     */
    private ISMSAdvertisingSloganDao smsAdvertisingSloganDao;

    /**
     * 部门短信广告语service接口.
     */
    private ISMSAdvertisingSloganForDeptService smsAdvertisingSloganForDeptService;

    /**
     * 行政组织接口.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    /**
     * 设置 行政组织接口.
     * 
     * @param orgAdministrativeInfoComplexService
     *            the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
	    IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
	this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    /**
     * 设置 部门短信广告语service接口.
     * 
     * @param smsAdvertisingSloganForDeptService
     *            the smsAdvertisingSloganForDeptService to set
     */
    public void setSmsAdvertisingSloganForDeptService(
	    ISMSAdvertisingSloganForDeptService smsAdvertisingSloganForDeptService) {
	this.smsAdvertisingSloganForDeptService = smsAdvertisingSloganForDeptService;
    }

    /**
     * 设置 短信广告语Dao接口.
     * 
     * @param smsAdvertisingSloganDao
     *            the smsAdvertisingSloganDao to set
     */
    public void setSmsAdvertisingSloganDao(
	    ISMSAdvertisingSloganDao smsAdvertisingSloganDao) {
	this.smsAdvertisingSloganDao = smsAdvertisingSloganDao;
    }

    /**
     * 新增短信广告语.
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:51:04
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#addSMSAdvertisingSlogan(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)
     */
    @Transactional
    @Override
    public int addSMSAdvertisingSlogan(SMSSloganEntity entity) {

	if (null != entity) {
	    SMSSloganEntity smsSlogan = querySmsSloganBySmsSloganCode(entity
		    .getSloganCode(),null);
	    if (null != smsSlogan) {
		throw new SMSAdVertisingSloganException("短信广告语代码不允许重复！");
	    }
	    smsSlogan = querySmsSloganBySmsSloganName(entity.getSloganName(),null);
	    if (null != smsSlogan) {
		throw new SMSAdVertisingSloganException("短信广告语名称不允许重复！");
	    }
	    // 类型短信广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	    LOGGER.debug("entering add info ,sloganSort:"
		    + entity.getSloganSort());
	    return smsAdvertisingSloganDao.addSMSAdvertisingSlogan(entity);
	} else {
	    throw new SMSAdVertisingSloganException("传入的参数不允许为空！");
	}

    }

    /**
     * 根据code作废短信广告语信息.
     * 
     * @param codeStr
     * @param modifyUser
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:51:18
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#deleteSMSAdvertisingSloganByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deleteSMSAdvertisingSloganByCode(String codeStr,
	    String modifyUser) {

	if (StringUtil.isBlank(codeStr)) {
	    throw new SMSAdVertisingSloganException("短信广告语的虚拟编码不允许为空！");
	} else {
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    
	    LOGGER.debug("entering delete info,codes:" + codeStr);
	    return smsAdvertisingSloganDao.deleteSMSAdvertisingSloganByCode(
		    codes, modifyUser);
	}
    }

    /**
     * 修改短信广告语信息.
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:51:28
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#updateSMSAdvertisingSlogan(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)
     */
    @Transactional
    @Override
    public int updateSMSAdvertisingSlogan(SMSSloganEntity entity) {

	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new SMSAdVertisingSloganException("短信广告语虚拟编码不允许为空！");
	}
	
	SMSSloganEntity smsSlogan = querySmsSloganBySmsSloganCode(entity
		.getSloganCode(),entity.getId());
	if (null != smsSlogan) {
	    throw new SMSAdVertisingSloganException("短信广告语代码不允许重复！");
	}
	smsSlogan = querySmsSloganBySmsSloganName(entity.getSloganName(),entity.getId());
	if (null != smsSlogan) {
	    throw new SMSAdVertisingSloganException("短信广告语名称不允许重复！");
	}
	// 类型短信广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	return smsAdvertisingSloganDao.updateSMSAdvertisingSlogan(entity);
    }

    /**
     * 根据传入对象查询符合条件所有短信广告语信息.
     * 
     * @param entity
     * @param limit
     * @param start
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:51:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#querySMSAdvertisingSlogans(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<SMSSloganEntity> querySMSAdvertisingSlogans(
	    SMSSloganEntity entity, int limit, int start) {
	entity.setActive(FossConstants.ACTIVE);
	// 类型短信广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	return smsAdvertisingSloganDao.querySMSAdvertisingSlogans(entity,
		limit, start);
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:52:03
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#getCount(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(SMSSloganEntity entity) {

	entity.setActive(FossConstants.ACTIVE);
	// 类型短信广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	return smsAdvertisingSloganDao.queryRecordCount(entity);
    }

    /**
     * <p>
     * 根据短信广告语代码、部门编码查询短信广告语，若部门不存在广告语，查询上级部门的广告语
     * </p>
     * .
     * 
     * @param smsSloganCode
     *            短信广告语代码
     * @param orgCode
     *            部门编码
     * @return 短信广告语
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:24:10
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#querySmsSloganContent(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String querySmsSloganContent(String smsSloganCode, String orgCode) {
	if (StringUtil.isBlank(smsSloganCode)) {
	    throw new SMSAdVertisingSloganException("短信广告语代码不允许为空！");
	} else {
	    // 根据短信广告语代码查询短信广告语
	    SMSSloganEntity entity = smsAdvertisingSloganDao
		    .querySmsSloganBySmsSloganCode(smsSloganCode,null);
	    if (null == entity) {
		return null;
	    } else {
		// 获取短信广告语内容
		String sms = entity.getContent();
		if (StringUtil.isBlank(orgCode)) {
		    return sms;
		} else {
		    // 根据部门编码查询本部门以及所有的上级部门
		    List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
			    .queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
		    if (CollectionUtils.isEmpty(orgList)) {
			return null;
		    }

		    SloganAppOrgEntity appOrgEntity = null;
		    for (int i = 0; i < orgList.size(); i++) {
			// 根据短信广告语虚拟编码、部门编码查询部门短信广告语
			appOrgEntity = smsAdvertisingSloganForDeptService
				.querySloganAppOrgByCode(orgCode,
					entity.getVirtualCode(),null);
			if (appOrgEntity != null) {
			    // 获得部门短信广告语内容
			    sms = appOrgEntity.getSloganContent();
			    break;
			} else {
			    // 获取上级部门编码
			    orgCode = getParentOrgCode(orgList, orgCode);
			}
		    }

		    return sms;
		}
	    }
	}
    }

    /**
     * <p>
     * 根据传入的部门编码查询上级部门的部门编码
     * </p>
     * .
     * 
     * @param list
     * @param orgCode
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午8:11:55
     * @see
     */
    private String getParentOrgCode(List<OrgAdministrativeInfoEntity> list,
	    String orgCode) {

	Map<String, OrgAdministrativeInfoEntity> codeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	Map<String, OrgAdministrativeInfoEntity> unicodeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	for (OrgAdministrativeInfoEntity entity : list) {
	    // 组织编码作为key把集合中的组织实体放在map中
	    codeMap.put(entity.getCode(), entity);
	    // 组织的标杆编码作为key把集合中的组织实体放在map中
	    unicodeMap.put(entity.getUnifiedCode(), entity);
	}
	LOGGER.debug("orgCode: " + orgCode);
	// 通过部门编码查询该部门的实体
	OrgAdministrativeInfoEntity orgEntity = codeMap.get(orgCode);

	if (orgEntity != null) {
	    // 如果上级标杆编码为空
	    if (StringUtil.isNotBlank(orgEntity.getParentOrgUnifiedCode())) {

		OrgAdministrativeInfoEntity parentOrg = unicodeMap
			.get(orgEntity.getParentOrgUnifiedCode());
		if (null != parentOrg) {
		    // 通过部门的上级部门标杆编码查询上级部门编码
		    return parentOrg.getCode();
		} else {
		    return orgCode;
		}
	    }
	    return orgCode;
	}
	// 没有上级，返回本部门的部门编码
	return orgCode;
    }

    /**
     * <p>
     * 根据短信广告语代码查询短信广告语
     * </p>
     * .
     * 
     * @param smsSloganCode
     *            短信广告语代码
     * @param smsId 短信广告语ID（该参数可以为空）
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#querySmsSloganBySmsSloganCode(java.lang.String)
     */
    @Override
    public SMSSloganEntity querySmsSloganBySmsSloganCode(String smsSloganCode,String smsId) {
	if (StringUtil.isBlank(smsSloganCode)) {
	    throw new SMSAdVertisingSloganException("短信广告语代码不允许为空！");
	} else {
	    return smsAdvertisingSloganDao
		    .querySmsSloganBySmsSloganCode(smsSloganCode,smsId);
	}
    }

    /**
     * <p>
     * 根据短信广告语名称查询短信广告语
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param sloganName
     *            短信广告语名称
     * @param smsId 短信广告语ID（该参数可以为空）
     * @return
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService#querySmsSloganBySmsSloganName(java.lang.String)
     */
    @Override
    public SMSSloganEntity querySmsSloganBySmsSloganName(String sloganName,String smsId) {
	if (StringUtil.isBlank(sloganName)) {
	    throw new SMSAdVertisingSloganException("短信广告语代码不允许为空！");
	} else {
	    return smsAdvertisingSloganDao
		    .querySmsSloganBySmsSloganName(sloganName,smsId);
	}
    }

}
