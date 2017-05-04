/**
 *  initial comments.
 *  
 *  目标是FOSS配置管理员需要维护付
 *  给航空公司增值服务价格信息，
 *  由具体航公司的出发机场来配置
 *  不同增值服务价格费率信息, 
 *  提供对航空公司增值服务提供符合实际业务范围，
 *  灵活且有效的配置管理信息。
 *	1、 	配载部门-空运总调信息
 *      2、	出发机场-基于航空公司下所选择的某个出发城市机场，先选择具体航空公司通过对应“选择”按钮弹出航空公司列表集合进行筛选后，然后点击出发机场的“选择”按钮，弹出某个航过公司下所有出发机场的列表结果集来进行筛选。
 *      3、	燃油附加费-设置燃油附加费
 *      4、	最低燃油附加费-设置最低
 *      5、	地面运输费-设置地面运输费
 *      6、	最低地面运输费-设置最低
 *      7、	保费-保费设置
 *      8、	最低保费-最低一票
 *      9、	航空公司-包含所有与德班业务往来的航空公司
 *      10、	航空公司-与我司业务往来的航空公司
 *      11、	生效时间-当前配置信息开始时间
 *      12、	截止时间-当前配置信息结束时间
 *      13、	最低费用-当重量 * X费率 = X增值服务费用<最低费用时就用此处最低费用来代替目的为了控制成本价格
 *      14、	最低总金额-所有以该航空公司该出发机场出发最后的金额不能低于这个数
 *      15、	是否激活-选择是否激活可让管理员抉择是否立即激活本次维护的配置信息，默认为“否”
 *      16、	备注描述： 备注信息
 *      
 *      	配载部门	空运总调	文本	
 *               无	100	是	无
 *              航空公司	德邦业务往来航公司，目前在基础数据总有维护	文本	无	100	是	无
 *              出发机场	航空公司关联的出发机场	下拉列表	无	100	是	无
 *              燃油附加费	燃油附加费设置	文本	无	20	是	
 *              最低燃油附加费	承诺最低一票	文本	无	20	是	
 *              地面运输费	地面运输费设置	文本	无	20	是	
 *              最低地面运输费	承诺最低一票	文本框	无	20	是	无
 *              保险费	保险费	文本	无	20	是	
 *              
 *              最低保险费	承诺最低一票	文本	无	20	是	
 *              
 *              生效日期	开始日期	日期组件	无	按照统一日期格式	是	无
 *              
 *              截止日期	结束日期	日期组件	无	按照统一日期格式	是	无
 *              
 *              最低总金额	承诺以该网点发货总费用最低一票	文本	无	20	是	无
 *              
 *              是否激活	选择是否立即激活	单选按钮	无	10	是	无
 *              
 *              备注信息	备注	文本域	无	1000	是	无
 *              
 *              
 *              
 *      SR1	录入时检查配载部门、航空公司、出发城市组合条件后台校验是否已经存在，
 *      
 *      	存在给予提示“该方案已经存在是否变更？”点击弹出框中确认按钮后台变更已有方案信息。
 *      
 *      	校验通过，提示“操作成功！”。
 *      
 *      SR2	点击按钮确认保存后、所录入的记录默认状态为“未激活”状态，
 *            	未激活状态的方案可以进行修改和删除操作，已激活的记录只能做变更了。
 *             	数据记录状态包括“未激活”、“已激活”、2个。“未激活”状态的方案可以勾选，进行激活操作，
 *             	已经激活的方案可以进行勾选“变更”操作，这些状态的操作均不能回退。
 *             	具体状态操作可以参考航空价格方案管理用例(其中包括增、删、改、查、激活、变更)。	
 * 	SR3	出发机场根据所选航空公司能做出发站的所有机场信息，当没有选择航空公司时，
 *	
 *		选择出发机场给予提示、“请先选择航空公司!”
 *		
 *	SR4	确认保存时校验燃油附加费、保费、地面运输费三者最低一票总和是否大于总金额最低一票， 
 *	
 *		确保不能小于总金额最低一票。 给以提示信息“您设置的各项最低一票总和不能小于总金额最低一票!”
 *		
 *	SR5	立即中止功能： 在价格查询列表中，只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
 *	
 *		点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，出现小于当前营业日期系统提示“
 *		
 *		立即中止操作的截止时间必须大于等于营业日期!” 。 
 *		
 *	SR6	立即激活功能： 在价格查询列表中，只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 选择生效时间，
 *	
 *		点击激活按钮，选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，
 *		
 *		出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” 。
 *
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/AirlinesValueAddService.java
 * 
 * FILE NAME        	: AirlinesValueAddService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.AirlinesValueAddException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 航空公司-(代理)增值服务费用.
 *
 * @author DP-Foss-YueHongJie
 * @date 2012-12-21 上午11:22:50
 * @version 1.0
 */
public class AirlinesValueAddService implements IAirlinesValueAddService {
	private static final Logger log = Logger.getLogger(AirlinesValueAddService.class);
	
	private static final int NUMBER_100 = 100;
    /**
     * 航空增值服务Dao
     */
    IAirlinesValueAddDao airlinesValueAddDao;
    
    /**
     * 
     * 员工服务
     * 
     */
    IEmployeeService employeeService;
    /**
     * 
     * 机场信息服务
     * 
     */
    IAirportService airportService;
    /**
     * 
     *  航空公司服务
     *  
     */
    IAirlinesService airlinesService;
    
    /**
     * 
     * 行政组织服务
     * 
     */
    IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * Sets the org administrative info service.
     *
     * @param orgAdministrativeInfoService the new org administrative info service
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * Sets the airport service.
     *
     * @param airportService the new airport service
     */
    public void setAirportService(IAirportService airportService) {
        this.airportService = airportService;
    }
    /**
     * Sets the airlines service.
     *
     * @param airlinesService the new airlines service
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
        this.airlinesService = airlinesService;
    }
    /**
     * Sets the employee service.
     *
     * @param employeeService the new employee service
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * Sets the airlines value add dao.
     *
     * @param airlinesValueAddDao the new airlines value add dao
     * @throws AirlinesValueAddException the airlines value add exception
     */
    public void setAirlinesValueAddDao(IAirlinesValueAddDao airlinesValueAddDao) throws AirlinesValueAddException{
        this.airlinesValueAddDao = airlinesValueAddDao;
    }
    /** 
     * <p>激活航空增值服务<br/>
     * 方法名： activeAirlinesValueAdd
     * 激活航空代理增值服务
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param ids
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#activeAirlinesValueAdd(java.util.List)
     */
    @Override
    public void activeAirlinesValueAdd(List<String> ids) throws AirlinesValueAddException{
	AirlinesValueAddEntity  airlinesValueAddEntity  = null;
	AirlinesValueAddEntity  temp = null;
	
	for (String id : ids) {
	    airlinesValueAddEntity =  airlinesValueAddDao.selectByPrimaryKey(id);
	    Date currentDate =  airlinesValueAddEntity.getBeginTime();
	    Date endTime = DateUtils.addDayToDate(currentDate, -1);
	    temp = airlinesValueAddDao.queryAirLinesValueAddByCodes(airlinesValueAddEntity.getAirlinesCode(), airlinesValueAddEntity.getAirport(), airlinesValueAddEntity.getLoadOrgCode(),currentDate);
	    if(null != temp){
		temp.setEndTime(endTime);
		airlinesValueAddDao.updateByPrimaryKeySelective(temp);
	    }
	}
	airlinesValueAddDao.activeAirlinesValueAdd(ids);
    }
    
    /** 
     * <p>批量删除航空增值服务<br/>
     * 
     * 方法名：deleteAirlinesValueAdd
     * 
     * 批量删除航空代理增值服务，只能对草稿状态的数据进行。一旦被激活过的数据将
     * 
     * 不能被删除掉。
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#deleteAirlinesValueAdd(java.util.List)
     */
    @Override
    @Transactional
    public void deleteAirlinesValueAdd(List<String> ids) {
	airlinesValueAddDao.deleteAirlinesValueAdd(ids);
    }
    
    /** 
     * <p>升级航空增值服务<br/>
     * 
     * 方法名： upgradeAirlinesValueAdd
     
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param entity
     * @return
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#upgradeAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public AirlinesValueAddEntity upgradeAirlinesValueAdd(AirlinesValueAddEntity entity) throws AirlinesValueAddException{
	   String primaryKey = entity.getId();
	   AirlinesValueAddEntity airlinesValueAddEntity = airlinesValueAddDao.selectByPrimaryKey(primaryKey);
		try {
			if(airlinesValueAddEntity !=null){
				PropertyUtils.copyProperties(entity, airlinesValueAddEntity);
			}
		} catch (IllegalAccessException e) {
			log.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.info(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.info(e.getMessage(), e);
		}
	   String uuid= UUIDUtils.getUUID();
	   if(airlinesValueAddEntity !=null){
		   airlinesValueAddEntity.setId(uuid);
		   airlinesValueAddEntity.setActive(FossConstants.INACTIVE);
		   airlinesValueAddDao.upgradeAirlinesValueAdd(airlinesValueAddEntity);
	   }
	   AirlinesValueAddEntity valueAddEntity = airlinesValueAddDao.selectByPrimaryKey(uuid);
	   boxDataAddEntities(valueAddEntity);
	   return valueAddEntity;
    }

    /** 
     * <p>删除航空增值服务<br/>
     * 
     * 方法名：deleteAirlinesValueAdd
     * 
     * 批量删除航空代理增值服务，只能对草稿状态的数据进行。一旦被激活过的数据将
     * 
     * 不能被删除掉。
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param id
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#deleteAirlinesValueAdd(java.lang.String)
     */
    @Override
    public void deleteAirlinesValueAdd(String id) throws AirlinesValueAddException{
	   airlinesValueAddDao.deleteByPrimaryKey(id);
    }

    /** 
     * <p>
     * 
     * 添加航空增值服务<br/>
     * 
     * 方法名： addAirlinesValueAdd
     * 
     * 新建航空增值服务、录入燃油附加费、地面运输费、保费。 新建航空增值服务默认
     * 
     * 为草稿状态的数据、在未激活之前可立即做删除草稿操作
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param entity
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#addAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void addAirlinesValueAdd(AirlinesValueAddEntity entity) throws AirlinesValueAddException{
	if(null == entity){
	    throw new AirlinesValueAddException("航空增值费用信息不能为空!",null);
	}
	Date currentDate = new Date();
	if(entity.getBeginTime().before(currentDate)){
	    throw new AirlinesValueAddException("开始时间必须大于当前营业日期!",null);
	}
	AirlinesValueAddDto airlinesValueAddDto = new AirlinesValueAddDto();
	airlinesValueAddDto.setAirport(entity.getAirport());
	airlinesValueAddDto.setLoadOrgCode(entity.getLoadOrgCode());
	airlinesValueAddDto.setAirlinesCode(entity.getAirlinesCode());
	List<AirlinesValueAddEntity> results=airlinesValueAddDao.findAirlinesValueAdd(airlinesValueAddDto);
	if(CollectionUtils.isNotEmpty(results)){
	    throw new AirlinesValueAddException("该方案已经存在",null);
	}
    	entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//人民币
    	entity.setActive(FossConstants.INACTIVE);//新增草稿状态
    	entity.setCreateDate(new Date());
    	entity.setId(UUIDUtils.getUUID());
 	airlinesValueAddDao.insertSelective(entity);
    }

    /** 
     * <p>修改航空增值服务<br/>
     * 
     * 	方法名： updateAirlinesValueAdd
     * 
     * 
     * 修改航空增值服务、修改燃油附加费、地面运输费、保费。 修改航空增值服务默认
     * 
     * 为草稿状态的数据、在未激活之前可立即做删除草稿操作
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param entity
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#updateAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void updateAirlinesValueAdd(AirlinesValueAddEntity entity) throws AirlinesValueAddException{
	airlinesValueAddDao.updateByPrimaryKeySelective(entity);
    }
    
    /** 
     * <p>
     * 查询航空增值服务<br/>
     * 
     * 方法名： findAirlinesValueAdd
     * 
     * 查询航空增值服务，根据不同维度条件来筛选增值服务的显示
     * 
     * 可按照、航空公司、出发机场、开始时间、结束时间来筛选客户想要查询
     * 
     * 的增值服务
     * 
     * 为草稿状态的数据、在未激活之前可立即做删除草稿操作
     *
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * 
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param dto
     * @return
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#findAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto)
     */
    @Override
    public List<AirlinesValueAddEntity> findAirlinesValueAdd(
	    AirlinesValueAddDto dto) throws AirlinesValueAddException{
	return airlinesValueAddDao.findAirlinesValueAdd(dto);
    }

    /** 
     * <p>根据ID查询航空增值服务<br/>
     * 方法名：findAirlinesValueAddById
     * 
     * 
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param primaryKey
     * @return
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#findAirlinesValueAddById(java.lang.String)
     */
    @Override
    public AirlinesValueAddEntity findAirlinesValueAddById(String primaryKey) throws AirlinesValueAddException{
	AirlinesValueAddEntity airlinesValueAddEntity = airlinesValueAddDao.selectByPrimaryKey(primaryKey);
	this.boxDataAddEntities(airlinesValueAddEntity);
	return airlinesValueAddEntity;
    }

     
    /** 
     * <p>根据配载部门航空公司出发机场获取唯一航空代理增值服务相关费用<br>
     * 
     * 提供给中转航空增值服务费率信息、
     * 
     * 根据航空公司编码、配载部门编码、
     * 
     * 出发机场编码、录入运单时间查询对应的航空增值服务信息
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * @author DP-Foss-YueHongJie
     * @param flightCode 航空公司编号
     * @param loadOrgCode 配载部门编号
     * @param deptAirfieldCode 出发机场编码
     * @param billDate 录入运单时间
     * <p>
     * @date 2012-11-2 上午11:12:49
     * @return AirlinesValueAddEntity 其中包括保费、燃油附加费、地面运输费、最低总金额
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @throws AirlinesValueAddException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#findAirlinesValueAddBycondition(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    public AirlinesValueAddEntity findAirlinesValueAddBycondition(String airlinesCode,
	    String loadOrgCode, String airPortCode,Date billDate) throws AirlinesValueAddException{
	if(StringUtil.isEmpty(airlinesCode)){
	    throw new AirlinesValueAddException("航空公司部能为空",null);
	}
	if(StringUtil.isEmpty(loadOrgCode)){
	    throw new AirlinesValueAddException("配载部门不能为空",null);
	}
	if(StringUtil.isEmpty(airPortCode)){
	    throw new AirlinesValueAddException("出发机场不能为空",null);
	}
	AirlinesValueAddDto airlinesValueAddDto  = new AirlinesValueAddDto();
	airlinesValueAddDto.setActive(FossConstants.ACTIVE);
	airlinesValueAddDto.setAirlinesCode(airlinesCode);
	airlinesValueAddDto.setLoadOrgCode(loadOrgCode);
	airlinesValueAddDto.setAirport(airPortCode);
	airlinesValueAddDto.setBillDate(billDate);
	List<AirlinesValueAddEntity> entityList = airlinesValueAddDao.findAirlinesValueAdd(airlinesValueAddDto);
	if(CollectionUtils.isNotEmpty(entityList)){
	    AirlinesValueAddEntity entity = entityList.get(0);
	    //将数据库中的分/100
	    this.processAirLinesFee(entity);
	    return entity;
	}else{
	    return  new AirlinesValueAddEntity();
	}
    }

    /** 
     * <p>查询航空增值服务</p> 
     * 分页查询
    
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#findAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto, int, int)
     */
    @Override
	public List<AirlinesValueAddEntity> findAirlinesValueAdd(
			AirlinesValueAddDto entity, int start, int limit) {
		List<AirlinesValueAddEntity> airlinesValueAddEntities = airlinesValueAddDao
				.findAirlinesValueAdd(entity, start, limit);
		List<AirlinesValueAddEntity> list = new ArrayList<AirlinesValueAddEntity>();
		if (CollectionUtils.isNotEmpty(airlinesValueAddEntities)) {
			for (AirlinesValueAddEntity airlinesValueAddEntity : airlinesValueAddEntities) {
				boxDataAddEntities(airlinesValueAddEntity);
				list.add(airlinesValueAddEntity);
			}
		}
		return list;
	}

    /**
     * <p>处理费率相关信息除100,数据库存的都是分</p>.
     * 
     * 将数据库分化为元
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * @param airlinesValueAddEntity the airlines value add entity
     * @author DP-Foss-YueHongJie
     * @date 2013-1-24 下午5:47:56
     * @see
     */
    private void processAirLinesFee(AirlinesValueAddEntity airlinesValueAddEntity){
	  airlinesValueAddEntity.setOilAddFee(BigDecimal.valueOf(airlinesValueAddEntity.getOilAddFee().doubleValue()/NUMBER_100)); //燃油附加费
	  airlinesValueAddEntity.setGroundTrsFee(BigDecimal.valueOf(airlinesValueAddEntity.getGroundTrsFee().doubleValue()/NUMBER_100));//地面运输费
	  airlinesValueAddEntity.setInsuranceFee(BigDecimal.valueOf(airlinesValueAddEntity.getInsuranceFee().doubleValue()/NUMBER_100));//保费
	  airlinesValueAddEntity.setMinGroundTrsFee(BigDecimal.valueOf(airlinesValueAddEntity.getMinGroundTrsFee().doubleValue()/NUMBER_100));//最低地面运输费
	  airlinesValueAddEntity.setMinInsuranceFee(BigDecimal.valueOf(airlinesValueAddEntity.getMinInsuranceFee().doubleValue()/NUMBER_100));//最低保费
	  airlinesValueAddEntity.setMinOilAddFee(BigDecimal.valueOf(airlinesValueAddEntity.getMinOilAddFee().doubleValue()/NUMBER_100));//最低燃油附加费
	  airlinesValueAddEntity.setMinTotalFee(BigDecimal.valueOf(airlinesValueAddEntity.getMinTotalFee().doubleValue()/NUMBER_100));//最低总费
    }
    
    /**
     * <p>设置增值服务查询时候需要关联的数据名称信息
     * 
     * 方法名： boxDataAddEntities
     * 
     *  
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * 
     * 
     * </p>.
     *
     * @param airlinesValueAddEntity the airlines value add entity
     * @author DP-Foss-YueHongJie
     * @date 2013-1-6 下午12:01:42
     * @see
     */
    private void boxDataAddEntities(AirlinesValueAddEntity airlinesValueAddEntity) {
	
	    //处理费用相关信息/100
	    this.processAirLinesFee(airlinesValueAddEntity);
	    if(StringUtil.isNotEmpty(airlinesValueAddEntity.getModifyUser())){
	    	EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(airlinesValueAddEntity.getModifyUser());
		    if(null != employeeEntity  &&  null != employeeEntity.getEmpName()) {
			airlinesValueAddEntity.setModifyUserName(employeeEntity.getEmpName());
		    }
	    }	    
	    
	    //航空代理公司
	    AirlinesEntity airlinesEntity = airlinesService.queryAirlineByCode(airlinesValueAddEntity.getAirlinesCode());
	    if(null != airlinesEntity && StringUtil.isNotBlank(airlinesEntity.getName())){
		airlinesValueAddEntity.setAirlinesName(airlinesEntity.getName());
	    }
	    
	    //机场
	    AirportEntity airPortEntity = new AirportEntity();
	    airPortEntity.setAirportCode(airlinesValueAddEntity.getAirport());
	    AirportEntity dbAirportEntity = airportService.queryAirportBySelective(airPortEntity);
	    
	    if(null != dbAirportEntity && StringUtil.isNotBlank(dbAirportEntity.getAirportName())){
		airlinesValueAddEntity.setAirportName(dbAirportEntity.getAirportName());
	    }
	    //配载部门
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(airlinesValueAddEntity.getLoadOrgCode());
	    if(null != orgAdministrativeInfoEntity && StringUtil.isNotBlank(orgAdministrativeInfoEntity.getName())){
		airlinesValueAddEntity.setLoadOrgName(orgAdministrativeInfoEntity.getName());
	    }
    }
    
    /** 
     * <p>查询航空增值服务总数</p> 
     * 
     * 方法名：findAirlinesValueAddCount
     * 
     * 目标：FOSS配置管理员需要维护付
     * 
     * 给航空公司增值服务价格信息，
     * 
     * 
     * 由具体航公司的出发机场来配置
     * 
     * 不同增值服务价格费率信息, 
     * 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 
     * 灵活且有效的配置管理信息。
     * 
     * 注意： 无效的服务不能用于实际中转开航空
     * 
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 
     * 版本在管理查询界面公布出来
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:41:16
     * 
     * @param entity
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#findAirlinesValueAddCount(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto)
     */
    @Override
    public Long findAirlinesValueAddCount(AirlinesValueAddDto entity) {
	return airlinesValueAddDao.findAirlinesValueAddCount(entity);
    }

    /** 
     * <p>修改航空增值服务</p> 
     * 
     * 目标：FOSS配置管理员需要维护付
     * 给航空公司增值服务价格信息，
     * 由具体航公司的出发机场来配置
     * 不同增值服务价格费率信息, 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 灵活且有效的配置管理信息。
     * 注意： 无效的服务不能用于实际中转开航空
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 版本在管理查询界面公布出来
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:41:16
     * 
     * @param entity
     *  
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void updateByPrimaryKeySelective(AirlinesValueAddEntity entity) {
	airlinesValueAddDao.updateByPrimaryKeySelective(entity);
    }


    /** 
     * <p>立即中止航空增值服务</p> 
     * 
     * 方法名称： immediatelyStopAirlinesValueAdd
     * 目标：FOSS配置管理员需要维护付
     * 给航空公司增值服务价格信息，
     * 由具体航公司的出发机场来配置
     * 不同增值服务价格费率信息, 
     * 提供对航空公司增值服务提供符合实际业务范围，
     * 灵活且有效的配置管理信息。
     * 注意： 无效的服务不能用于实际中转开航空
     * 正单正确使用， 只有被激活的数据，且日期符合范围的数据
     * 才能有效被利用起来。航空增值服务中主要包括、地面运输费、
     * 保费、燃油附加费用的基础费率进行了管理与统一调价。最终形成
     * 版本在管理查询界面公布出来
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:41:16
     * 
     * @param airlinesValueAddEntity
     * 
     * @throws AirlinesValueAddException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#immediatelyStopAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void immediatelyStopAirlinesValueAdd(AirlinesValueAddEntity airlinesValueAddEntity) throws AirlinesValueAddException {
	if(null != airlinesValueAddEntity){
	    AirlinesValueAddEntity dbEntity  = airlinesValueAddDao.selectByPrimaryKey(airlinesValueAddEntity.getId());
	    if(airlinesValueAddEntity.getEndTime().before(new Date())){
    	    throw new AirlinesValueAddException("中止日期必须大于当前营业日期!",null);
    	}
	    if(airlinesValueAddEntity.getEndTime().after(dbEntity.getEndTime())){
		 throw new AirlinesValueAddException("中止日期不能延长原方案所制定的活动结束日期!",null);
	    }
	    dbEntity.setEndTime(airlinesValueAddEntity.getEndTime());
	    airlinesValueAddDao.updateByPrimaryKeySelective(dbEntity);
	}else{
    	    throw new AirlinesValueAddException("方案信息为空");
	}
	
    }
    /** 
     * <p>立即生效航空增值服务</p> 
     * 方法名称： immediatelyActiveAirlinesValueAdd
     * 注意：无效的服务不能用于实际中转开航空
     * 正单正确使用， 只有被激活的数据，
     * 且日期符合范围的数据
     * 才能有效被利用起来。
     * 航空增值服务中主要包括、
     * 地面运输费、
     * 保费、燃油附加费用的基础费率进行了
     * 管理与统一调价。
     * 最终形成版
     * 本在管理查询界面公布出来 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-14 下午2:41:16
     * @param airlinesValueAddEntity
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService#immediatelyActiveAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void immediatelyActiveAirlinesValueAdd(AirlinesValueAddEntity airlinesValueAddEntity) {
	if(null != airlinesValueAddEntity){
	    	String airlinesValueAddId = airlinesValueAddEntity.getId();
		if(!StringUtil.isNotBlank(airlinesValueAddId)){
		    throw new AirlinesValueAddException("方案信息为空");
		}
		//处理上个方案的截止日期为当前方案-1天
		Date beginTime =  airlinesValueAddEntity.getBeginTime();
		Date endTime = DateUtils.addDayToDate(beginTime, -1);
		AirlinesValueAddEntity  temp = null;
		temp = airlinesValueAddDao.queryAirLinesValueAddByCodes(airlinesValueAddEntity.getAirlinesCode(), airlinesValueAddEntity.getAirport(), airlinesValueAddEntity.getLoadOrgCode(),beginTime);
		if(null != temp){
		    temp.setEndTime(endTime);
		    airlinesValueAddDao.updateByPrimaryKeySelective(temp);
		}
		//处理生效日期,激活状态
		AirlinesValueAddEntity airlinesEntity = new AirlinesValueAddEntity();
		airlinesEntity.setBeginTime(airlinesValueAddEntity.getBeginTime());
		airlinesEntity.setActive(FossConstants.ACTIVE);
		airlinesEntity.setId(airlinesValueAddEntity.getId());
		airlinesValueAddDao.updateByPrimaryKeySelective(airlinesEntity);
	}else{
    	    throw new AirlinesValueAddException("方案信息为空");
	}
    }
}