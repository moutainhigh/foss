/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/ArriveSheetMannerAction.java
 * 
 * FILE NAME        	: ArriveSheetMannerAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveSheetMannerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 到达联管理action
 * @author dengtingting
 *
 */
public class ArriveSheetMannerAction extends AbstractAction{
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(ArriveSheetMannerAction.class);
	//序列号
	private static final long serialVersionUID = 1L;
	//前台交互数据
	private ArriveSheetVo vo = new ArriveSheetVo();
	//到达联管理service
	private IArriveSheetManngerService arriveSheetManngerService; 
	//运单管理接口
	private IWaybillManagerService waybillManagerService;
	//子母件服务接口层
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 1、	只有按单号查询或到达联编号才能查到非本部门的到达联。否则只能查到到达部门为本部门的到达联。
     * 2、	查询到的到达联按照运单分组显示。
     * 3、	到达联件数与排单、装车、库存件数其中任何一个不一致用 颜色标识
     * 查询打印界面：
     * 主要由：查询条件区域、查询结果显示区域、操作区域三部分组成
     * 界面初始化时默认显示：如图一。默认加载到达部门为本部门且到达时间为今天的到达联。
     * 1.	查询条件区域：显示查询条件，
     * 其中到达联状态包括：生成
     * 、已打印、
     * 签收、
     * 作废、
     * 派送中。
     * 默认加载下拉框中的子项。
     * 显示查询结果：
     * 2.	查询结果显示区域：
     * 显示查询结果列表
     * 3.	操作区域：
     * 批量打印：
     * 选中复选框批量打印。
     * 作废：
     * 作废到达联。
     * 激活：
     * 将作废的到达联激活
	 * 到达联管理模块 
	 * 根据条件查询到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午10:57:15
	 */
	@JSON
	public String queryArriveSheet(){
		//日志信息
		LOGGER.info("queryArriveSheet 开始...");
		try {
			//日志，传入参数
			LOGGER.info("输入参数 ==..."+ReflectionToStringBuilder.toString(vo));
			//返回记录数量
			Long totalCount = arriveSheetManngerService.getArriveSheetCount(vo.getArriveSheetDto());
			if (totalCount > 0) {
				//获得所有到达联信息
				List<ArriveSheetDto> list = arriveSheetManngerService.queryArriveSheet(vo.getArriveSheetDto(),this.getStart(),this.getLimit());
				//返回到达联集合
				vo.setArriveDtoList(list);
			}
			//设置记录总数
			this.setTotalCount(totalCount);
			//捕获异常
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 激活到达联
	 * 1、	验货签收时要求录入的是到达联编号，
	 * 而不是之前的运单号。 
	 * 故在：
	 * DP-FOSS-接送货系统用例-集中送货-PDA客户货物签收-签收接口-V0.1.doc、
	 * 	  专线正常签收接口：
	 * SUC-597、
	 * 	  偏线空运正常签收接口：
	 * SUC-651中将传入参数“运单号”
	 * 修改成“到达联编号”
	 * 2、	到达联模板，
	 * 在条码后显示到达联编号
	 * ，而不是运单号。
	 * 在条码信息中加入到达联编号、
	 * 到达联信息以及运单信息。
	 * 故：在DP-FOSS-接送货系统用例-集中送货-打印到达联-V0.1.doc模板中修改模板。
	 * 3、1、	"是否打印"不再作为到达联的状态
	 * ，而只作为一个标识符。
	 * 3、2、	界面下拉框状态剔除"打印"
	 * 在旁边加上"是否打印"复选框 
	 * @author dp-dengtingting
	 * @date 2012-10-13 下午5:19:32
	 */
	@JSON
	public String activateArriveSheet(){
		try {
			//选择集合的到达联数组
			String id[] = vo.getArriveSheet().getId().split(ArriveSheetConstants.COMMA);
			//获得对应的运单号数组
			String waybillNos[] = vo.getArriveSheet().getWaybillNo().split(ArriveSheetConstants.COMMA);
			//获得到达件数数组
			String arriveSheetGoodsQtys[] = vo.getArriveSheetGoodsQtys().split(ArriveSheetConstants.COMMA);
			//遍历数组
			for (int i = 0; i < id.length; i++) {
				//实例化到达联实体
				ArriveSheetEntity entity = new ArriveSheetEntity();
				//设置实体ID
				entity.setId(id[i]);
				//设置销毁状态
				entity.setDestroyed(FossConstants.NO);
				//设置运单号
				entity.setWaybillNo(waybillNos[i]);
				//设置件数
				entity.setArriveSheetGoodsQty(Integer.valueOf(arriveSheetGoodsQtys[i]));
				//更新到达联
				arriveSheetManngerService.activateArriveSheet(entity);
			}
			//返回消息
			return returnSuccess("激活成功！");
			//捕获异常
		} catch (BusinessException e) {
			//返回错误消息
			return returnError(e);
		}
	}
	
	
	/**
	 * 作废到达联
	 * 根据ISSUE-1113 到达联管理界面变更：
	 * 1、在到达联状态下拉框中剔除"作废"状态 
	 * 2、在查询条件区域增加 "是否有效"复选框。作为查询条件
	 * 根据ISSUE-1179修改：
	 * 1、修改_作废_激活到达联 查询结果显示区域表格去除显示装车件数一列
	 * 1、	只有按单号查询或到达联编号才能查到非本部门的到达联。
	 * 否则只能查到到达部门为本部门的到达联。
	 * 2、	查询到的到达联按照运单分组显示。
	 * 3、	到达联件数与排单、
	 * 装车、
	 * 库存件数其中任何一个不一致用 颜色标识
	 * 1、	图二修改件数窗口。
	 * 到达件数不允许输入超过库存件数的数字。
	 * 如超过，
	 * 文本框自动恢复原值。
	 * 2、	已作废的到达联需激活才能改件数。
	 * 状态为“派送中”的到达联不能修改件数。
	 * 1、只能激活状态为“作废”的到达联。
	 * 其他状态激活则提示：
	 * “不是作废状态的到达联，
	 * 不用激活。”
	 * 1、状态“派送中”的到达联不能作废。
	 * 若作废则提示：
	 * “派送中不能作废！”
	 * @author dp-dengtingting
	 * @date 2012-10-13 下午5:20:27
	 */
	@JSON
	public String cancelArriveSheet(){
		try {
			//选择集合的到达联数组
			String id[] = vo.getArriveSheet().getId().split(ArriveSheetConstants.COMMA);
			//获得对应的运单号数组
			String waybillNos[] = vo.getArriveSheet().getWaybillNo().split(ArriveSheetConstants.COMMA);
			//获得到达件数数组
			String arriveSheetGoodsQtys[] = vo.getArriveSheetGoodsQtys().split(ArriveSheetConstants.COMMA);
			//遍历数组
			for (int i = 0; i < id.length; i++) {
				//实例化到达联实体
				ArriveSheetEntity entity = new ArriveSheetEntity();
				//设置实体ID
				entity.setId(id[i]);
				//设置销毁状态
				entity.setDestroyed(FossConstants.YES);
				//设置件数
				entity.setArriveSheetGoodsQty(new Integer(arriveSheetGoodsQtys[i]));
				//设置运单号
				entity.setWaybillNo(waybillNos[i]);
				//更新到达联
				arriveSheetManngerService.invalidArriveSheet(entity);
			}
			//返回消息
			return returnSuccess("作废成功！");
			//捕获异常
		} catch (BusinessException e) {
			//返回错误消息
			return returnError(e);
		}
	}
	
	/**
	 * 修改到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 下午5:20:56
	 */
	@JSON
	public String modifyArriveSheet(){
		try {
			//更新到达联
			arriveSheetManngerService.updateArriveSheetData(vo.getArriveSheet());
			//捕获异常
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 打印到达联模块
	 * 根据运单等条件查询到达联信息
	 * @author dp-dengtingting
	 * @date 2012-10-24 下午3:48:36
	 */
	@JSON
	public String queryArriveSheetByWaybill(){
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			List<ArriveSheetWaybillDto> list = arriveSheetManngerService.queryWaybillDetailByWaybill(vo.getArriveSheetWaybillDto(),currentInfo);
			vo.setArriveSheetWaybillList(list);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据运单号查询运单信息
	 * 主要由四部分组成：
	 * 查询条件区域、
	 * 查询结果显示区域、
	 * 运单详细信息区域，
	 * 操作区域。
	 * 界面初始化时默认显示：
	 * 查询结果区域显示到达部门为本部门且未签收的货物。
	 * 运单详细信息区域隐藏。
	 * 1.	查询条件区域：
	 * 运单号、
	 * 交接单号、
	 * 提货方式、
	 * 收货人的姓名、
	 * 手机/固定电话号码、
	 * 预派送单号。
	 * a)	运单号：
	 * 运单编号。
	 * b)	交接单号：
	 * 交接单号。
	 * c)	提货方式：
	 * 提货方式包括 （全部、自提、送货）三种，默认为全部。
	 * d)	收货人姓名：
	 * 运单开单中的收货人姓名。
	 * e)	手机号码：
	 * 收货人的手机号码。
	 * f)	固定电话：
	 * 收货人的固定电话号码。
	 * g)	预派送单号：
	 * 预排单所产生的派送单号
	 * h)	查询：
	 * 点击查询 将结果显示在左边结果显示区域中。
	 * 2.	查询结果区域：
	 * 显示查询结果：
	 * 显示运单号、
	 * 是否受理、
	 * 操作列是否打印。
	 * 3.	运单详细信息区域：
	 * 如界面所示显示运单的详细信息。	
	 * 4.	操作区域
	 * 选中所有：
	 * 点击“选中所有”勾选所有是否打印列。
	 * 取消选中：
	 * 点击“取消选中”取消勾选所有打印列。
	 * 预览：
	 * 点击“预览”预览所有选中运单。
	 * 打印：
	 * 点击“打印”打印所有选中运单
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-2 下午4:28:48
	 */
	@JSON
	public String queryWaybillInfoByWaybillNo(){
		try {
			
			vo = arriveSheetManngerService.queryWaybillDetailByWaybillNo(vo.getArriveSheetWaybillDto().getWaybillNo());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 打印到达联
	 * 查询打印界面：
	 * 主要由四部分组成：
	 * 查询条件区域、
	 * 查询结果显示区域、
	 * 运单详细信息区域，
	 * 操作区域。
	 * 界面初始化时默认显示：
	 * 查询结果区域显示到达部门为本部门且未签收的货物。
	 * 运单详细信息区域隐藏。
	 * 1.	查询条件区域：
	 * 运单号、
	 * 交接单号、
	 * 提货方式、
	 * 收货人的姓名、
	 * 手机/固定电话号码、
	 * 预派送单号。
	 * a)	运单号：
	 * 运单编号。
	 * b)	交接单号：
	 * 交接单号。
	 * c)	提货方式：
	 * 提货方式包括 （全部、自提、送货）三种，
	 * 默认为全部。
	 * d)	收货人姓名：
	 * 运单开单中的收货人姓名。
	 * e)	手机号码：
	 * 收货人的手机号码。
	 * f)	固定电话：
	 * 收货人的固定电话号码。
	 * g)	预派送单号：
	 * 预排单所产生的派送单号
	 * h)	查询：
	 * 点击查询 将结果显示在左边结果显示区域中。
	 * 2.	查询结果区域：
	 * 显示查询结果：
	 * 显示运单号、
	 * 是否受理、
	 * 操作列是否打印。
	 * 3.	运单详细信息区域：
	 * 如界面所示显示运单的详细信息。	
	 * 4.	操作区域
	 * 选中所有：
	 * 点击“选中所有”勾选所有是否打印列。
	 * 取消选中：
	 * 点击“取消选中”取消勾选所有打印列。
	 * 预览：
	 * 点击“预览”预览所有选中运单。
	 * 打印：
	 * 点击“打印”打印所有选中运单
	 * @author dp-dengtingting
	 * @date 2012-10-13 下午5:22:11
	 */
	@JSON
	public String printArriveSheet(){
		try {
		      if (StringUtil.isEmpty(vo.getArriveSheetNos())) {
		          throw new ArriveSheetMannerException("您选中的运单没有到达联或者已经作废，无法打印。");
		        }
		        
		        String[] nos = vo.getArriveSheetNos().split(ArriveSheetConstants.COMMA);
		        for (String arriveSheetNo : nos) {
		          //获取运单详情
		          WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(arriveSheetNo.substring(0, arriveSheetNo.length()- NumberConstants.NUMBER_3));
		          if (waybillEntity!=null) {
		        	//判断运单是否为快递件 
		            if (ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(waybillEntity.getProductCode())) {
		              Map<String ,Object> params = new HashMap<String ,Object>();
		              params.put("waybillNo", waybillEntity.getWaybillNo());
		              params.put("active", FossConstants.YES);
		              //查询运单是否属于字母件
		              TwoInOneWaybillDto oneDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
		              //非字母件的快递件还是需要补录
		              if (!"Y".equals(oneDto.getIsTwoInOne())) {
		            	  if (waybillEntity.getPendingType()!=null) {
			                  //如果为未补录状态
			                  if (waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_PENDING)
			                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PDA_PENDING)
			                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING)) {
			                    throw new ArriveSheetMannerException("运单状态为未补录，请补录后打印！");
			                  }else{
			                    //如果重量、体积为空或者小于0
			                    if (waybillEntity.getGoodsWeightTotal()==null || waybillEntity.getGoodsVolumeTotal()==null 
			                        || waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0))<=0 
			                        || waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))<=0 ) {
			                      throw new ArriveSheetMannerException("运单信息缺失，请补录后打印！");
			                    }
			                  }
			                }else{
			                  throw new ArriveSheetMannerException("运单补录信息为空，请补录后打印！");
			                }
		              }
		            //非快递件--零担(需要验证补录)
		            }else{
		            	if (waybillEntity.getPendingType()!=null) {
			                  //如果为未补录状态
			                  if (waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_PENDING)
			                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PDA_PENDING)
			                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING)) {
			                    throw new ArriveSheetMannerException("运单状态为未补录，请补录后打印！");
			                  }else{
			                    //如果重量、体积为空或者小于0
			                    if (waybillEntity.getGoodsWeightTotal()==null || waybillEntity.getGoodsVolumeTotal()==null 
			                        || waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0))<=0 
			                        || waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))<=0 ) {
			                      throw new ArriveSheetMannerException("运单信息缺失，请补录后打印！");
			                    }
			                  }
			                }else{
			                  throw new ArriveSheetMannerException("运单补录信息为空，请补录后打印！");
			                }
		            }
		          }else{
		            throw new ArriveSheetMannerException(ArriveSheetMannerException.ENTITY_IS_NOT_EXIST);
		          }
		        }
		        arriveSheetManngerService.updatePrintTimes(vo.getArriveSheetNos());
		      } catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 批量打印到达联
	 * 1、	只有按单号查询 才能查到非本部门未签收的货物。
	 * 否则只能查到本部门未签收的货物。
	 * 2、	查询到的货物按照提货方式（自提在前、上门派送在后）
	 * 、运单分组显示。
	 * 查询时：1、若只输入了收货人姓名、
	 * 手机号、电话号码查询中其中一项（对于此情况，
	 * 只能查询到达部门为本部门且未签收的货物）。支持按姓名模糊查询。
	 * 2.若输入了“单号”或“交接单号”此情况营业员可以查询到非本部门且未签收的运单并打印。
	 * 查询时不能单独按照“提货方式”进行查询，必须与运单号或交接单号组合查询。
	 * 其中条件 优先级：单号>交接单号>收货人信息。
	 * 查询结果区域结果显示：
	 * a)	查询出来的运单库存件数与开单不一致，
	 * 用紫色标识。
	 * b)	查询出来的运单非本部门库存运单，
	 * 用 标识。
	 * c)	查询出来的运单有未受理更改，
	 * 用 标识, ，
	 * 打印时提示:“此单需受理出发更改后，
	 * 方可打印！”
	 * d)	查询出来的运单含签收单用 标识。
	 * e)	该单已打印到达联的用 标识。
	 * 其中只有操作列是否打印可以修改。
	 * 其他项都为只读。
	 * 1、	在提交到达联打印时，
	 * 若此运单有出发更改未受理，
	 * 则提醒:“此单未受理，受理后方可打印。”
	 * 。在打印完到达联时 系统后台自动记录日志，日志内容包括运单号、
	 * 是否打印、目的地、操作人编号、操作人、操作时间、预派送单号等。
	 * 2、	打印到达联后生成一条到达联记录，
	 * 数据元素参照“SUC-794 修改_作废_激活到达联”。
	 * 到达联编号生成规则：（大写字母D +运单号+3位数字实别）后三位数字从1开始自增。
	 * 到达联生成时校验到达联编号的唯一性。
	 * 运单详细信息区域所有信息都不可修改。
	 * 打印整车运单时,如果是出发部门打印到达联且到达类型是“到达营业部”那么不打印收货人信息。
	 * 到达联能够打印出通知的内容包括计划送货时间、
	 * 备注内容。
	 * 1、	如是出发更改且已受理的运单如果付款方式为到付则：
	 * 到达联打印出更改受理后的运单相关信息，
	 * 并且在普通版左侧中打印出出发更改单的内容，
	 * 打印规则参照SR9第6项。
	 * 附：目前激光版内无打印更改信息这一栏。
	 * 3、	打印规则：
	 * 4、	所有带单位的数字信息出模板中已带单位否则在打印时必须带上单位，
	 * 金额：￥、体积：m2、重量：kg。
	 * 5、	运单中所有需要填充的信息字体都为宋体小四加粗。
	 * 6、	“其他服务费用”一栏打印顺序依 次为保价费、
	 * 综合服务费、燃油附加费、
	 * 代收手续费，其他不限定顺序。
	 * 7、	“其他服务费用”打印运单信息中以下框架内的所有内容，
	 * 目前为7个空格。当其他费用在7项以内时，
	 * 一个费用项对应一个格，
	 * 当其他费用超过7项时，
	 * 费用项目的间距缩小，
	 * 不必按格打印。（注：仓储费作为小票处理。）
	 * 8、	为打印后美观内容过长的信息可根据模板宽度预留N个汉字后换行。
	 * 9、	普通版更改内容打印规则：
	 * “更改内容：”+更改内容+时间，
	 * 多项用分号隔开。
	 * 10、	费用合计显示：
	 * 上行显示：现付金额（大写） 下行显示：
	 * 付款方式+金额+已付金额（大写）如模板所示。
	 * 11、	在提交到达联打印后，
	 * 派送处理前发起出发更改，
	 * 更改单受理时，
	 * 从右下角弹出提示重新打印此到达联。
	 * 12、	最终打印的到达联上的件数要与客户实际提货件数一致。如：
	 * 13、	1、 到达联上件数不等于实际提货件数。
	 * 则需线上标记更改到达联件数
	 * 14、	更改后系统提示“到达联更改，
	 * 是否重新打印？”如重新打印则作废以前到达联
	 * ，如不重新打印，
	 * 则线下手动修改纸质版到达联的件数。
	 * 15、	分批配送如第一次已结清，
	 * 第二批费用合计显示为0 ，
	 * 到达联模板上需打印已送多少件，
	 * 还剩多少件。
	 * 16、	到达联模版上的广告标语设置内容来源请参照：
	 * “SUC-178-新增_修改_作废_查询单据广告语”用例
	 * 17、	增加支付状态类型，
	 * 可以筛选出“网上支付”未付款订单；
	 * 18、	对“网上支付”未付款的运单，
	 * 在查询处理之后进行颜色标识。
	 * 19、	到达联打印时增加制单人、
	 * 制单人时间。
	 * 20、	对于网上支付的运单，
	 * 调用财务接口对运单识别此运单是否已经付款；
	 * 对于未付款的运单，标注颜色。
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-14 上午10:31:15
	 */
	@JSON
	public String batchPrintArriveSheet(){
		try {
			if (StringUtils.isBlank(vo.getWaybillNos())) {
				throw new ArriveSheetMannerException("传入订单号为空，请稍后再试！");
			}
			String[] waybillNos = vo.getWaybillNos().split(",");
			StringBuffer arriveSheetNos = new StringBuffer();
			for (int i = 0; i < waybillNos.length; i++) {
				ArriveSheetEntity arriveSheet = new ArriveSheetEntity();
				arriveSheet.setWaybillNo(waybillNos[i]);
				//获取运单详情
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNos[i]);
				if (waybillEntity!=null) {
					//判断运单是否为快递件 
		            if (ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(waybillEntity.getProductCode())) {
			              Map<String ,Object> params = new HashMap<String ,Object>();
			              params.put("waybillNo", waybillEntity.getWaybillNo());
			              params.put("active", FossConstants.YES);
			              //查询运单是否属于字母件
			              TwoInOneWaybillDto oneDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			              //非字母件的快递件还是需要补录
			              if (!"Y".equals(oneDto.getIsTwoInOne())) {
			            	  if (waybillEntity.getPendingType()!=null) {
				                  //如果为未补录状态
				                  if (waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_PENDING)
				                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PDA_PENDING)
				                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING)) {
				                    throw new ArriveSheetMannerException("运单状态为未补录，请补录后打印！");
				                  }else{
				                    //如果重量、体积为空或者小于0
				                    if (waybillEntity.getGoodsWeightTotal()==null || waybillEntity.getGoodsVolumeTotal()==null 
				                        || waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0))<=0 
				                        || waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))<=0 ) {
				                      throw new ArriveSheetMannerException("运单信息缺失，请补录后打印！");
				                    }
				                  }
				                }else{
				                  throw new ArriveSheetMannerException("运单补录信息为空，请补录后打印！");
				                }
			              }
			            //非快递件--零担(需要验证补录)
			            }else{
			            	if (waybillEntity.getPendingType()!=null) {
				                  //如果为未补录状态
				                  if (waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_PENDING)
				                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PDA_PENDING)
				                      || waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING)) {
				                    throw new ArriveSheetMannerException("运单状态为未补录，请补录后打印！");
				                  }else{
				                    //如果重量、体积为空或者小于0
				                    if (waybillEntity.getGoodsWeightTotal()==null || waybillEntity.getGoodsVolumeTotal()==null 
				                        || waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0))<=0 
				                        || waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))<=0 ) {
				                      throw new ArriveSheetMannerException("运单信息缺失，请补录后打印！");
				                    }
				                  }
				                }else{
				                  throw new ArriveSheetMannerException("运单补录信息为空，请补录后打印！");
				                }
			            }
		            }else{
					throw new ArriveSheetMannerException(ArriveSheetMannerException.ENTITY_IS_NOT_EXIST);
				}
				
				// 打印来源
				arriveSheet.setSource(vo.getSource());
				String sheetNos = arriveSheetManngerService.printArriveSheet(arriveSheet);
				if(arriveSheetNos.length() == 0) {
					arriveSheetNos.append(sheetNos);
				} else {
					arriveSheetNos.append(ArriveSheetConstants.COMMA).append(sheetNos);
				}
				vo.setArriveSheetNos(arriveSheetNos.toString());
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 
	 * <p>vo<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @return
	 * ArriveSheetVo
	 */
	public ArriveSheetVo getVo() {
		return vo;
	}
	/**
	 * 
	 * <p>vo<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param vo
	 * void
	 */
	public void setVo(ArriveSheetVo vo) {
		this.vo = vo;
	}

	/**
	 * 
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param arriveSheetManngerService
	 * void
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
}