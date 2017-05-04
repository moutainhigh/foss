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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/AirlinesValueAddAction.java
 * 
 * FILE NAME        	: AirlinesValueAddAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.AirlinesValueAddVo;
/**
 * 航空增值服务ACTION
 * 
 * @author 张斌
 * @date 2012-11-21
 * @version 1.0
 */
public class AirlinesValueAddAction extends AbstractAction {
    
    	/**
    	 * 序列化 
    	 */
	private static final long serialVersionUID = 2883644272419312426L;
	/**
	 * 日志处理 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesValueAddAction.class);
	/**
	 * 
	 * 航空增值服务VO交互对象
	 * 
	 */
	private AirlinesValueAddVo airlinesValueAddVo = new AirlinesValueAddVo();
	/**
	 * 
	 * 获得航空增值服务VO交互对象
	 * 
	 */
	public AirlinesValueAddVo getAirlinesValueAddVo() {
		return airlinesValueAddVo;
	}
	/**
	 * 设置航空增值服务VO交互对象
	 */
	public void setAirlinesValueAddVo(AirlinesValueAddVo airlinesValueAddVo) {
		this.airlinesValueAddVo = airlinesValueAddVo;
	}
	/**
	 *航空增值服务
	 */
	public IAirlinesValueAddService airlinesValueAddService;
	/**
	 * set 注入航空增值服务
	 */
	public void setAirlinesValueAddService(
			IAirlinesValueAddService airlinesValueAddService) {
		this.airlinesValueAddService = airlinesValueAddService;
	}
	/**
	 * <p>
	 * 查询所有的航空增值服务，
	 * 
	 * 航空增值服务主要提供代理航空公司的服务费用维护以及查询，其中包含： 保费、燃油附加费、
	 * 
	 * 地面运输费，这些费用在中转做航空整单时候会用到。<br/>
	 * 
	 * 方法名：searchAirlinesValueByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String searchAirlinesValueByCondition() {
		try {
			List<AirlinesValueAddEntity> airlinesValueAddEntityList = airlinesValueAddService.findAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddDto(), start, limit);
			airlinesValueAddVo.setAirlinesValueAddEntityList(airlinesValueAddEntityList);
			this.setTotalCount(airlinesValueAddService.findAirlinesValueAddCount(airlinesValueAddVo.getAirlinesValueAddDto()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询所有的航空增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>
	 * 
	 * 录入时检查配载部门、航空公司、出发城市组合条件后台校验是否已经存在，存在给予提示“该方案已经存在是否变更？
	 * 
	 * 方法名：searchAirlinesValueByCondition
	 * </p>
	 * 
	 * @author梅影
	 * @时间 2013-10-22
	 * @since JDK1.6
	 */
	@JSON
	public String checkAirlinesValueExistsByCondition() {
		try {
			List<AirlinesValueAddEntity> airlinesValueAddEntityList = airlinesValueAddService.findAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddDto());
			if(CollectionUtils.isNotEmpty(airlinesValueAddEntityList)){
				return returnError("");
			}else {
				return returnSuccess();
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * <p>
	 * 新增航空增值服务<br/>
	 * 方法名：addAirlinesValue
	 * 添加航空增值服务信息包括：
         * 创建机构
         * 修改机构
         * 币种
         * 燃油附加费
         **最低燃油附加费
         * 地面运输费
         * 最低地面运输费
         * 保费
         * 最低保费
         * 最低总金额
         * 备注
         * 运价号
         * 是否当前版本
	 * </p>
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String addAirlinesValue() {
		try {
			airlinesValueAddService.addAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddEntity());
			return returnSuccess(MessageType.SAVE_AIRLINESVALUEADD_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增航空增值服务: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * <p>
	 * 
	 * 删除航空增值服务
	 * 
	 * 只能删除没有激活的服务，只能对草稿方案进行有效删除<br/>
	 * 
	 * 方法名：deleteAirlinesValue
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String deleteAirlinesValue() {
		try {
			airlinesValueAddService.deleteAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddIds());
			return returnSuccess(MessageType.DELETE_AIRLINESVALUEADD_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("删除航空增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * <p>
	 * 
	 * 激活航空增值服务<br/>
	 * 
	 * 方法名：activeAirlinesValue
	 * 
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String activeAirlinesValue() {
		try {
			airlinesValueAddService.activeAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddIds());
			return returnSuccess(MessageType.ACTIVE_AIRLINESVALUEADD_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("激活航空增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改航空增值服务<br/>
	 * 
	 * 方法名：updateAirlinesValue
	 * 
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String updateAirlinesValue() {
		try {
			airlinesValueAddService.updateAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddEntity());
			return returnSuccess(MessageType.UPDATE_AIRLINESVALUEADD_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("修改航空增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 
	 * 根据ID查询航空增值服务<br/>
	 * 
	 * 方法名：queryAirlinesValueAddById
	 * 
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-05
	 * @since JDK1.6
	 */
	@JSON
	public String queryAirlinesValueAddById() {
		try {
			AirlinesValueAddEntity airlinesValueAddEntity = airlinesValueAddService.findAirlinesValueAddById(airlinesValueAddVo.getAirlinesValueAddEntity().getId());
			airlinesValueAddVo.setAirlinesValueAddEntity(airlinesValueAddEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("根据ID查询航空增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 
	 * 升级版本增值服务<br/>
	 * 
	 * 方法名：upgradeAirlinesValueAdd
	 * 
	 * </p>
	 * 
	 * 
	 * @author 张斌
	 * 
	 * 
	 * @时间 2012-12-22
	 * 
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String upgradeAirlinesValueAdd() {
		try {
			AirlinesValueAddEntity airlinesValueAddEntity = airlinesValueAddService.upgradeAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddEntity());
			airlinesValueAddVo.setAirlinesValueAddEntity(airlinesValueAddEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("升级版本增值服务: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * 
	 * <p>立即中止航空增值服务方案</p> 
	 * 
	 * 方法名称：immediatelyStopAirlinesValueAdd
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-1 上午10:05:22
	 * 
	 * @return
	 * 
	 * @see
	 * 
	 */
	@JSON
	public String immediatelyStopAirlinesValueAdd(){
	    try {
		 airlinesValueAddService.immediatelyStopAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddEntity());
		 return returnSuccess(MessageType.STOP_SUCCESS);
	    } catch (BusinessException e) {
		LOGGER.error("立即中止航空增值服务方案: "+e.getMessage());
		return returnError(e);
	    }
	}
	
	/**
	 * 
	 * 
	 * <p>
	 * 
	 * 立即激活航空增值服务方案
	 * 
	 * 方法名称：immediatelyActiveAirlinesValueAdd
	 * 
	 * </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-1 上午10:05:22
	 * 
	 * @return
	 * 
	 * @see
	 */
	@JSON
	public String immediatelyActiveAirlinesValueAdd(){
	    try {
		 airlinesValueAddService.immediatelyActiveAirlinesValueAdd(airlinesValueAddVo.getAirlinesValueAddEntity());
		 return returnSuccess(MessageType.ACTIVE_SUCCESS);
	    } catch (BusinessException e) {
		LOGGER.error("立即激活航空增值服务方案: "+e.getMessage());
		return returnError(e);
	    }
	}
}