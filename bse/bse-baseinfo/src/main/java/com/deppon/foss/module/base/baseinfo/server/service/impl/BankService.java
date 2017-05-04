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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/BankService.java
 * 
 * FILE NAME        	: BankService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 * 
 * 接口名称	同步银行信息接口
        背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
        接口提供者	ESB系统	接口调用者	银企系统
        交互场景	
        1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
        2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
        3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
        4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
        5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
        
        
        请求参数	
        请求参数
        字段	是否必填	是否列表	备注
        银行信息	是	是	参见【银行信息】
        
        【银行信息】
        字段	是否必填	是否列表	备注
        银行编码	是	否	
        上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        银行名称	是	否	
        省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
        更新时间
        	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
        银行信息状态	是	否	表示银行信息是否有效：
        0无效，1有效
        
        返回参数	
        
        返回参数
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        银行编码	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填
        
        非功能需求	
        名称	要求
        调用实效	立即
        请求发生后是否需要立即依赖响应
        交互模式	否
        请求/回调
        是否需要顺序执行	否
        是否批量处理	是
        调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
        高峰时期	08:00-12:00，13:30-17:30
        消息大小	峰值一次10条左右
        时间响应要求	N/A
        安全性要求	无
        
        接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求


 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 银行Service接口实现
 * 
 * 
 * 接口名称	同步银行信息接口
        背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
        接口提供者	ESB系统	接口调用者	银企系统
        交互场景	
        1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
        2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
        3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
        4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
        5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
        
        
        请求参数	
        请求参数
        字段	是否必填	是否列表	备注
        银行信息	是	是	参见【银行信息】
        
        【银行信息】
        字段	是否必填	是否列表	备注
        银行编码	是	否	
        上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        银行名称	是	否	
        省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
        操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
        更新时间
        	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
        银行信息状态	是	否	表示银行信息是否有效：
        0无效，1有效
        
        返回参数	
        
        返回参数
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        银行编码	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填
        
        非功能需求	
        名称	要求
        调用实效	立即
        请求发生后是否需要立即依赖响应
        交互模式	否
        请求/回调
        是否需要顺序执行	否
        是否批量处理	是
        调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
        高峰时期	08:00-12:00，13:30-17:30
        消息大小	峰值一次10条左右
        时间响应要求	N/A
        安全性要求	无
        
        接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午4:12:20
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午4:12:20
 * @since
 * @version
 */
public class BankService implements IBankService {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);
    /**
     * 银行Dao接口.
     */
    private IBankDao bankDao;
    /**
     * 银行省市信息Service接口.
     */
    private IProvinceCityInfoService provinceCityInfoService;
    /**
     * 设置 银行Dao接口.
     * 
     * @param bankDao
     *            the new 银行Dao接口
     */
    public void setBankDao(IBankDao bankDao) {
	this.bankDao = bankDao;
    }
    /**
     * 设置 银行省市信息Service接口.
     * 
     * @param provinceCityInfoService
     *            the new 银行省市信息Service接口
     */
    public void setProvinceCityInfoService(IProvinceCityInfoService provinceCityInfoService) {
	this.provinceCityInfoService = provinceCityInfoService;
    }
    /**
     * 新增银行.
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param entity
     *            银行实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 下午4:07:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#addBank(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public int addBank(BankEntity entity) {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	// 设置下载版本号
	entity.setVersionNo(date.getTime());
	LOGGER.debug("versionNo:" + entity.getVersionNo());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 第一次记录新增时，虚拟编码为记录的id
	entity.setActive(FossConstants.ACTIVE);
	return bankDao.addBank(entity);
    }
    /**
     * 根据code作废银行.
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param codeStr
     *            code字符串
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:07:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#deleteBank(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public int deleteBank(String codeStr, String modifyUser) {
	if (StringUtil.isBlank(codeStr)) {
	    throw new BankException("银行ID允许为空！");
	}
	// 拆分字符串
	String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	LOGGER.debug("codeStr:" + codeStr);
	return bankDao.deleteBank(codes, modifyUser);
    }
    /**
     * 修改银行.
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param entity
     *            银行实体
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:07:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#updateBank(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public int updateBank(BankEntity entity) {
	Date date = new Date();
	if (null == entity) {
	    return FossConstants.FAILURE;
	} else {
	    if (StringUtil.isNotBlank(entity.getCode())) {
		LOGGER.debug("bankCode: " + entity.getCode());
		entity.setModifyDate(date);
		entity.setVersionNo(date.getTime());
		entity.setActive(FossConstants.ACTIVE);
		return bankDao.updateBank(entity);
	    } else {
		throw new BankException("银行编码不允许为空！");
	    }
	}
    }
    /**
     * 根据传入对象查询符合条件所有银行信息.
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:07:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#queryBanks(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity,
     *      int, int)
     */
    @Override
    public List<BankEntity> queryBanks(BankEntity entity, int limit, int start) {
	entity.setActive(FossConstants.ACTIVE);
	return convertInfoList(bankDao.queryBanks(entity, limit, start));
    }
    /**
     * 统计总记录数.
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param entity
     *            银行实体
     * @return
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:07:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public Long queryRecordCount(BankEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
	return bankDao.queryRecordCount(entity);
    }
    /**
     * <p>
     * 行政区域名称封装
     * </p>
     * .
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param list
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-12-1 下午5:37:55
     * @see
     */
    private List<BankEntity> convertInfoList(List<BankEntity> list) {
	List<BankEntity> entities = new ArrayList<BankEntity>();
	if (CollectionUtils.isNotEmpty(list)) {
	    for (BankEntity entity : list) {
		entity = convertInfo(entity);
		entities.add(entity);
	    }
	    return entities;
	}
	return null;
    }
    /**
     * <p>
     * 填充行政区域名称
     * </p>
     * .
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:12:38
     * @see
     */
    private BankEntity convertInfo(BankEntity entity) {
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isNotBlank(entity.getProvId())) {
	    // 获得省份对象
	    ProvinceCityEntity porvince = provinceCityInfoService
		    .queryCityEntityByCode(entity.getProvId());
	    if (null != porvince) {
		// 设置省份名称
		entity.setProvName(porvince.getDistrictName());
	    }
	}
	if (StringUtils.isNotBlank(entity.getCityCode())) {
	    // 获得城市对象
	    ProvinceCityEntity city = provinceCityInfoService
		    .queryCityEntityByCode(entity.getCityCode());
	    if (null != city) {
		// 设置城市名称
		entity.setCityName(city.getDistrictName());
	    }
	}
	return entity;
    }
    /**
     * 获取最后更新时间.
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @return
     * @author dp-yangtong
     * @date 2012-10-30 下午4:07:16
     * @see
     */
    @Override
    public Date getLastUpdateTime() {
	return bankDao.getLastModifyTime();
    }
    /**
     * @param fromDate
     * @param userID
     * @return Customer
     * @获得银行信息，用于银行信息同步
     */
    @Override
    public List<BankEntity> getBanks(Date fromDate, String userID) {

	return bankDao.getSyncData(fromDate, userID);
    }
    /**
     * <p>
     * 根据银行编码查询银行信息
     * </p>
     * 
     * 
     * 接口名称	同步银行信息接口
                    背 景	FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统存在有使用银行信息的用例，银行信息作为主数据存储在银企系统中，故需要将银行信息从银企系统同步至ESB系统，然后由ESB把银行信息同步至各个系统；
                    接口提供者	ESB系统	接口调用者	银企系统
                    交互场景	
                    1、银企系统中银行信息发生更改（新增、修改、作废），则调用此接口，传入银行信息和更改类型（新增、修改、作废）；
                    2、ESB系统接收银行信息并且分发请求数据给FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统
                    3、FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统接收ESB传入的银行信息，根据信息中唯一键（编码）确定是新增还是更新，如果是更新则根据版本号(最后更新时间)确定是否需要更新。并且把处理结果信息反馈至ESB
                    4、ESB接收FOSS、ERP、CRM、HR、官网、OA、财务自助、费控系统对银行信息的处理结果。并且回传给银企系统
                    5、银企系统处理同步信息的处理结果，对于由于系统异常未同步成功的信息银企将在下次发送最新数据给ESB
                    
                    
                    请求参数	
                    请求参数
                    字段	是否必填	是否列表	备注
                    银行信息	是	是	参见【银行信息】
                    
                    【银行信息】
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    上级银行编码	是	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    银行名称	是	否	
                    省份编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    城市编码	否	否	若为开户银行即顶级银行，例如中国银行，此字段则为空，否则若当前银行信息是【中国银行上海市青浦区徐泾镇分行】则此字段不为空
                    操作类别(新增、修改、作废)	是	否	传入整数，1、新增；2、修改；3、作废；
                    更新时间
                    	是	否	作为数据操作的标记，根据上一次的更新时间，确定是否要作更新操作，如果是新增将此时间保存，如果是封存此时间不管。
                    银行信息状态	是	否	表示银行信息是否有效：
                    0无效，1有效
                    
                    返回参数	
                    
                    返回参数
                    字段	是否必填	是否列表	备注
                    成功总数	是	否	
                    失败总数	是	否	
                    处理明细	是	是	参见【处理明细】
                    
                    处理明细
                    字段	是否必填	是否列表	备注
                    银行编码	是	否	
                    成功或失败的标识	是	否	整数类型，0、失败；1、成功
                    失败原因	否	否	如果处理失败，此字段为必填
                    
                    非功能需求	
                    名称	要求
                    调用实效	立即
                    请求发生后是否需要立即依赖响应
                    交互模式	否
                    请求/回调
                    是否需要顺序执行	否
                    是否批量处理	是
                    调用时段和调用量	00：00~24：00，一天调用1次，一天传送10条银行信息。
                    高峰时期	08:00-12:00，13:30-17:30
                    消息大小	峰值一次10条左右
                    时间响应要求	N/A
                    安全性要求	无
                    
                    接口提供端规则	1、接口处理需要具有幂等性即能处理相同数据的不同请求
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-2 上午10:34:13
     * @param bankCode
     *            银行编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBankService#queryBankInfoByCode(java.lang.String)
     */
    @Override
    public BankEntity queryBankInfoByCode(String bankCode) {
	if(StringUtil.isBlank(bankCode)){
	    throw new BankException("银行编码不允许为空！");
	}else {
	    return bankDao.queryBankInfoByCode(bankCode);
	}
    }
   /**
    * 根据银行名称精确查询银行信息
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-4-14 下午3:50:26
    * @return
    * @see
    */
    @Override
    public BankEntity queryBankInfoByName(String bankName){
    	if(StringUtil.isBlank(bankName)){
    	    throw new BankException("银行名称不允许为空！");
    	}
    	return bankDao.queryBankInfoByName(bankName);
    }
}
