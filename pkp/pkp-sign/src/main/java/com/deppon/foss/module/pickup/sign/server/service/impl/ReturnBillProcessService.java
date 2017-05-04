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
 * PROJECT NAME	: pkp-sign
 *
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/ReturnBillProcessService.java
 *
 * FILE NAME        	: ReturnBillProcessService.java
 *
 * AUTHOR			: FOSS接送货系统开发组
 *
 * HOME PAGE		: http://www.deppon.com
 *
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.sign.api.server.dao.IReturnBillProcessDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IReturnBillProcessService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReturnBillProcessConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.ReturnBillProcessException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 签收单返单service 查询签收单返单
 * @author xwshi
 * @date 2012-10-18 上午9:44:11
 */
public class ReturnBillProcessService implements IReturnBillProcessService {

	/**
	 * Logger 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBillProcessService.class);

	private static final String  RETURNBILLCONFIRM_NO = "未确认";

	private static final String  RETURNBILLCONFIRM_YES ="已确认";

	/**
	 * 签收单返单dao
	 */
	private IReturnBillProcessDao returnBillProcessDao;

	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;

	/**
	 * 运单管理Service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 短信接口
	 */
	private INotifyCustomerService notifyCustomerService;

	/**
	 * 消息的服务接口
	 */
	private IMessageService  messageService;

	/**
	 * 外场共同服务
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;

	/**
	 * 快递服务接口
	 */
	private IWaybillExpressService waybillExpressService;

	/**
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;


	/***
	 * 客户信息服务类
	 */
	private ICustomerDao customerDao;

	/**
	 * 在线通知Service
	 */
	private IOnLineMsgService onLineMsgService;

	/**
	 * 签收单返单结果
	 * 图1界面标题：
	 * 未返单运单查询
	 * 图2界面标题：
	 * 未返单运单处理
	 * 查询未返单运单界面主要包括三个部门查询条件区域、
	 * 返单明细列表和功能按钮区域。
	 * 1.	查询条件区域：
	 * 单号、
	 * 返单类型、
	 * 出发部门、
	 * 到达部门、
	 * 签收起始时间、
	 * 签收截止时间、
	 * 开单起始时间、
	 * 开单截止时间、
	 * 返单状态
	 * a)	单号：
	 * 运单号
	 * b)	返单类型：
	 * 运单返单类型
	 * c)	出发部门：
	 * 部门基础资料
	 * d)	到达部门：
	 * 提货网点基础资料
	 * e)	签收起始时间：
	 * 货物签收的起始时间
	 * f)	签收截止时间：
	 * 货物签收的截止时间
	 * g)	开单起始时间：
	 * 创建运单的开始时间
	 * h)	开单截止时间：
	 * 创建运单的截止时间
	 * i)	返单状态：
	 * 运单的返单状态
	 * j)	查询：
	 * 营业员输入查询条件后，
	 * 点击“查询”按钮查询符合条件的未返单运单。
	 * k)	重置：
	 * 点击“重置”按钮，
	 * 重置所有查询条件为页面初始化状态
	 * @date 2012-11-20 下午6:58:13
	 * * @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 */
	public List<RtSearchReturnBillProcessDto> searchReturnBillProcessList(
			SearchReturnBillProcessDto dto, int start, int limit) {
		LOGGER.info("LIST ========="+ReflectionToStringBuilder.toString(dto));
		//运单号
		String waybillNo = dto.getWaybillNo();
		SearchReturnBillProcessDto dtoNew = new SearchReturnBillProcessDto();
		if (StringUtil.equals(FossConstants.YES, dto.getOrgDiff())) {
			//本部门出发
			dtoNew.setFromDepartmentCode(FossUserContextHelper.getOrgCode());
			dto.setFromDepartmentCode(FossUserContextHelper.getOrgCode());
		} else {
			//到达本部门
			dto.setToDepartmentCode(FossUserContextHelper.getOrgCode());
		}
		//运单号查询具有排他性
		if(StringUtils.isNotEmpty(waybillNo)){
			//单号查询具有排他性
			dtoNew.setWaybillNo(waybillNo);

			List<RtSearchReturnBillProcessDto> rtSearchReturnBillProcessDtos = returnBillProcessDao.searchReturnBillProcessList(dtoNew, start, limit);
			if(CollectionUtils.isNotEmpty(rtSearchReturnBillProcessDtos)){
				for (RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto :rtSearchReturnBillProcessDtos){
					//如果返单确认不为'已确认',则设置返单确认为'未确认'
					if(!StringUtil.equals(RETURNBILLCONFIRM_YES,rtSearchReturnBillProcessDto.getReturnbillConfirm())){
						rtSearchReturnBillProcessDto.setReturnbillConfirm(RETURNBILLCONFIRM_NO);
					}
					//记录查询的选项  Y 出发部门  N 到达部门
					rtSearchReturnBillProcessDto.setOrgDiff(dto.getOrgDiff());
				}
			}  
			//返单结果数据集大小
			return rtSearchReturnBillProcessDtos;
		}
		List<String> stockInfo = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(FossUserContextHelper.getOrgCode());
		// 设置库存部门和库区编号
		if(CollectionUtils.isNotEmpty(stockInfo)){
			List<String> ld=new ArrayList<String>();
			ld.add(stockInfo.get(1));
			ld.add(stockInfo.get(2));
			dto.setStockOrgCode(stockInfo.get(0));
			dto.setGoodsAreaCodes(ld);
		}

		//268377 查询签收单返单纪录
		List<RtSearchReturnBillProcessDto> rtSearchReturnBillProcessDtos = returnBillProcessDao.searchReturnBillProcessList(dto,start,limit);
		if(CollectionUtils.isNotEmpty(rtSearchReturnBillProcessDtos)){
			for (RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto :rtSearchReturnBillProcessDtos){
				//如果返单确认不为'已确认',则设置返单确认为'未确认'
				if(!StringUtil.equals(RETURNBILLCONFIRM_YES,rtSearchReturnBillProcessDto.getReturnbillConfirm())){
					rtSearchReturnBillProcessDto.setReturnbillConfirm(RETURNBILLCONFIRM_NO);
				}
				//记录查询的选项  Y 出发部门  N 到达部门
				rtSearchReturnBillProcessDto.setOrgDiff(dto.getOrgDiff());
			}
		}
		//结果
		return rtSearchReturnBillProcessDtos;
	}

	/**
	 *
	 * 导出excel表格
	 *
	 * @author 038590-foss-wanghui
	 * @date 2013-6-27 下午6:04:21
	 */
	public InputStream searchReturnBillProcessList(
			SearchReturnBillProcessDto dto) {
		LOGGER.info("LIST ========="+ReflectionToStringBuilder.toString(dto));
		List<RtSearchReturnBillProcessDto> returnBillProcessDtos;
		//运单号
		String waybillNo = dto.getWaybillNo();
		//运单号查询具有排他性
		if(StringUtils.isNotEmpty(waybillNo)){
			SearchReturnBillProcessDto dtoNew = new SearchReturnBillProcessDto();
			//单号查询具有排他性
			dtoNew.setWaybillNo(waybillNo);
			//到达部门为本部门
			dtoNew.setToDepartmentCode(FossUserContextHelper.getOrgCode());
			//返单结果数据集大小
			returnBillProcessDtos = returnBillProcessDao.searchReturnBillProcessList(dtoNew);
		}else{
			List<String> stockInfo = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(FossUserContextHelper.getOrgCode());
			// 设置库存部门和库区编号
			if(CollectionUtils.isNotEmpty(stockInfo)){
				List<String> ld=new ArrayList<String>();
				ld.add(stockInfo.get(1));
				ld.add(stockInfo.get(2));
				dto.setStockOrgCode(stockInfo.get(0));
				dto.setGoodsAreaCodes(ld);
			}
			if (StringUtil.equals(FossConstants.YES, dto.getOrgDiff())) {
				//本部门出发
				dto.setFromDepartmentCode(FossUserContextHelper.getOrgCode());
			} else {
				//到达本部门
				dto.setToDepartmentCode(FossUserContextHelper.getOrgCode());
			}
			//结果
			returnBillProcessDtos = returnBillProcessDao.searchReturnBillProcessList(dto);
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(RtSearchReturnBillProcessDto row : returnBillProcessDtos){
			List<String> columnList = new ArrayList<String>();
			// 运单号
			columnList.add(row.getWaybillNo());
			// 出发部门
			columnList.add(row.getReceiveOrgName());
			// 返单时间
			columnList.add(DateUtils.convert(row.getReturnbillTime(), DateUtils.DATE_TIME_FORMAT));
			// 返单类型
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getReturnbillType(), DictionaryConstants.RETURN_BILL_TYPE));
			// 处理人
			columnList.add(row.getHandler());
			// 返单状态
			columnList.add(DictUtil.rendererSubmitToDisplay(row.getReturnbillStatus(), DictionaryConstants.PKP_RETURNBILL_STATUS));
			// 核实时间
			columnList.add(DateUtils.convert(row.getVerifyTime(), DateUtils.DATE_TIME_FORMAT));
			// 核实反馈信息
			columnList.add(row.getFeedbackInfo());
			
			//liuxiangcheng update 2015-5-25
			/*// 发货人
			columnList.add(row.getDeliveryCustomerContact());
			// 发货人手机
			columnList.add(row.getDeliveryCustomerMobilephone());
			// 发货人电话
			columnList.add(row.getDeliveryCustomerTel());*/
			// 快递公司
			columnList.add(row.getExpressCompany());
			// 快递单号
			columnList.add(row.getExpressNo());
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"运单号","出发部门","返单时间","返单类型","处理人","返单状态","核实时间","核实反馈信息",/*"发货人","发货人手机","发货人电话",*/"快递公司","快递单号"};
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("签收单返单");
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
}

	/**
	 * 查询签收单返单结果数据集大小
	 * @date 2012-11-20 下午6:58:13
	 * * @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 */
	public Long getReturnBillProcessCount(SearchReturnBillProcessDto dto) {
		LOGGER.info("count =========="+ReflectionToStringBuilder.toString(dto));
		//运单号
		String waybillNo = dto.getWaybillNo();
		SearchReturnBillProcessDto dtoNew = new SearchReturnBillProcessDto();
		if (StringUtil.equals(FossConstants.YES, dto.getOrgDiff())) {
			//本部门出发
			dtoNew.setFromDepartmentCode(FossUserContextHelper.getOrgCode());
			dto.setFromDepartmentCode(FossUserContextHelper.getOrgCode());
		} else {
			//到达本部门
			dtoNew.setToDepartmentCode(FossUserContextHelper.getOrgCode());
			dto.setToDepartmentCode(FossUserContextHelper.getOrgCode());
		}

		//运单号查询具有排他性
		if(StringUtils.isNotEmpty(waybillNo)){
			//单号查询具有排他性
			dtoNew.setWaybillNo(waybillNo);
			//返单结果数据集大小
			return returnBillProcessDao.getReturnBillProcessCount(dtoNew);
		}
		List<String> stockInfo = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(FossUserContextHelper.getOrgCode());
		// 设置库存部门和库区编号
		if(CollectionUtils.isNotEmpty(stockInfo)){
			List<String> ld=new ArrayList<String>();
			ld.add(stockInfo.get(1));
			ld.add(stockInfo.get(2));
			dto.setStockOrgCode(stockInfo.get(0));
			dto.setGoodsAreaCodes(ld);
		}

		//返单结果数据集大小
		return returnBillProcessDao.getReturnBillProcessCount(dto);
	}

	/**
	 * 根据id查询ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 * * @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 */
	public RtSearchReturnBillProcessDto searchReturnBillProcessById(
			SearchReturnBillProcessDto dto) {
		//根据id查询ReturnBillProcess
		return returnBillProcessDao.searchReturnBillProcessById(dto);
	}
	
	/**
	 * 签收单返单确认
	 */
	@Transactional
	@Override
	public String updateBatchReturnBillProcess(String[] ids,CurrentInfo currentInfo) {
		LOGGER.info("签收单返单确认开始"+ids);
		int result = 0;
		RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto = new RtSearchReturnBillProcessDto();
			if (null != ids && ids.length > 0  && null != currentInfo){
				rtSearchReturnBillProcessDto.setConfirmTime(new Date());//确认时间
				rtSearchReturnBillProcessDto.setReturnbillConfirm(RETURNBILLCONFIRM_YES);//已确认
				rtSearchReturnBillProcessDto.setConfirmHandler(currentInfo.getEmpName());//当前操作人
				rtSearchReturnBillProcessDto.setIds(ids);//ids
				//批量确认
				 result = returnBillProcessDao.updateBatchRetrunBillProcess(rtSearchReturnBillProcessDto);
			}else{
				LOGGER.info("签收单返单确认失败");
				return ReturnBillProcessException.CONFIRM_NO;
			}

		if(result > 0){
			LOGGER.info("签收单返单确认成功");
			return ReturnBillProcessException.CONFIRM_SUCESS;
		}else{
			LOGGER.info("签收单返单确认失败");
			return ReturnBillProcessException.CONFIRM_NO;
		}

	}

	/**
	 * 更新ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 * * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 * 2.	返单明细列表：操作列
	 * a)	操作列：“ ”表示处理，营业员选择返单明细中一条点击“ ”按钮弹出【处理未返单运单】界面进行处理。
	 * “ ”表示通知，营业员选择返单明细中一条点击“ ”按钮通知发货客户和发货部门
	 * b)	数据元素参见【返单明细】
	 * 1.5.1.2
	 * 处理未返单运单
	 * 处理未返单运单界面主要包括处理信息录入区域和功能按钮区域
	 * 1. 处理信息录入区域：
	 * 运单号、
	 * 返单类型、
	 * 核实时间、
	 * 处理人、
	 * 外发快递公司、
	 * 快递单号、
	 * 反馈信息
	 * a)	运单号：
	 * 自动加载
	 * b)	返单类型：
	 * 自动加载，
	 * 运单的返单类型
	 * c)	核实时间：
	 * 到达营业员核实信息时间 ，
	 * 默认系统当前时间，
	 * 必填项
	 * d)	处理人：
	 * 返单处理人，
	 * 必填项
	 * e)	外发快递公司：
	 * 原件返回时的外发快递公司
	 * f)	快递单号：
	 * 原件返回时的快递单号
	 * g)	反馈信息：
	 * 客户反馈的信息
	 * 2. 功能按钮区：
	 * 确认，
	 * 取消
	 * a)	确认：到达营业员录入返单处理信息完毕后，
	 * 点击“确认”按钮处理该单，系统修改状态“未返单”为“已返单”
	 */
	@Transactional
	public ResultDto updateReturnBillProcess(
			ReturnBillProcessEntity returnBillProcessEntity) {
		//更新ReturnBillProcess
		returnBillProcessDao.updateReturnBillProcess(returnBillProcessEntity);
		//签收单返单Dto
		SearchReturnBillProcessDto searchReturnBillProcessDto = new SearchReturnBillProcessDto();
		//设置id
		searchReturnBillProcessDto.setIds(returnBillProcessEntity.getId());
		//根据运单号查询运单信息
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(returnBillProcessEntity.getWaybillNo());
		//2、	调用在线通知接口生成在线通知至出发部门
		MsgOnlineDto msgOnlineDto = new MsgOnlineDto();
		if(waybillEntity != null ) {
			if (StringUtil.isNotEmpty(waybillEntity.getReceiveOrgCode())) {
				//获取收货部门编码
				msgOnlineDto.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
			}
			if (StringUtil.isNotEmpty(waybillEntity.getReceiveOrgName())) {
				//获取收货部门名称
				msgOnlineDto.setReceiveOrgName(waybillEntity.getReceiveOrgName());
			}
		}
		if(StringUtil.isNotEmpty(returnBillProcessEntity.getWaybillNo())){
			//运单号
			msgOnlineDto.setBillNo(returnBillProcessEntity.getWaybillNo());
		}
		//通知内容固定为"有签收单或传真件返回，请确认查收"
		msgOnlineDto.setContext("有签收单或传真件返回，请确认查收。");
		//推送在线通知
		onLineMsgService.addOnlineMsg(msgOnlineDto);
		//3、	调用短信平台发送短信至发货客户，短信内容包括：运单号、发货人姓名、联系电话、客户地址，模板待定
		SearchReturnBillProcessDto dto = new SearchReturnBillProcessDto();
		//Id
		dto.setIds(returnBillProcessEntity.getId());

		RtSearchReturnBillProcessDto entity = returnBillProcessDao
				.searchReturnBillProcessById(dto);

		//扫描件返单是不需要发消息的
		if(entity!=null){
			if(ReturnBillProcessConstants.SCANNED.equals(entity.getReturnbillType())){
				ResultDto result = new ResultDto();
				result.setCode(ResultDto.SUCCESS_NO_SEND);
				result.setMsg("");
				return result;
			}
		}

		//Send message
		return sendBillProcessSmsById(dto);

	}

	/**
	 * update：
	 * @data 2014年7月9日 下午4:30:16
	 * 添加状态码的判断：业务需求：分成三种状态 1.发件人短信批量发送 2.发件人短信停发 3.收件人短信停发  
	 * 0表示的是未选中   1表示的是选中
	 * 发件人短信批量发送	当CRM勾选发件人短信批量发送时，停发快递订单调度受理短信、快递签收收货人短信、签收单返单短信，次日向客户发送批量打包短信
	 * 发件人短信停发	当CRM勾选发件人短信停发时，停发快递订单调度受理短信、快递签收发件人短信。
	 * 收件人短信停发	当CRM勾选收件人短信停发时，停止发送快递派送收货人短信、快递开单收货人短信、快递签收收货人短信
	 * #######################  签收单返单短信
	 * 1、	更新对应运单号的返单信息
	 * 2、	调用在线通知接口生成在线通知至出发部门
	 * 3、	调用短信平台发送短信至发货客户，
	 * 短信内容包括：
	 * 运单号、
	 * 发货人姓名、
	 * 联系电话、
	 * 客户地址，
	 * 模板待定
	 * 4、 处理成功，
	 * 系统弹窗提示用户“处理成功！”，
	 * 失败，系统弹窗提示用户“处理失败”
	 *发送短信通知客户和营业员签单返回信息
	 ** @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 * @date 2012-11-22 下午1:58:45
	 */
	@Transactional
	public ResultDto sendBillProcessSmsById(
			SearchReturnBillProcessDto dto) {
		//返单对象
		ResultDto result  = new ResultDto();

		//查询短信需要的返单内容
		RtSearchReturnBillProcessDto rtDto = returnBillProcessDao.searchReturnBillProcessById(dto);
		//短信发送失败   返单信息不存在
		if (rtDto == null) {
			result.setCode(ResultDto.FAIL);
			result.setMsg("短信发送失败");
			return result;
		}

		//短信发送失败    运单信息不存在
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(rtDto.getWaybillNo());

		if(waybillEntity == null){//运单信息不存在
			result.setCode(ResultDto.FAIL);
			result.setMsg("短信发送失败");
			return result;
		}
		//获得短消息内容string
		String content = getNoticeContent(rtDto, waybillEntity);
		String moduleCode = "";
		//根据产品运输类型判断分为零担和快递   若运输产品类型-快递
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(),waybillEntity.getBillTime())){
			//更改 begin
			String deliveryCustomerCode = waybillEntity.getDeliveryCustomerCode();
			if(StringUtils.isNotEmpty(deliveryCustomerCode)){
				CustomerEntity customerEntity = customerDao.queryCustStateByCode(deliveryCustomerCode);
				if(customerEntity!=null){
					String shipperSms = customerEntity.getShipperSms();
					if(StringUtils.isNotEmpty(shipperSms)&&(shipperSms.equals(SignConstants.BATCH_MESSAGE_FOR_DELIVER)||shipperSms.equals(SignConstants.STOP_MESSAGE_FOR_DELIVER))){
						result.setCode(ResultDto.SUCCESS_NO_SEND);
						result.setMsg(StringUtils.EMPTY);
						return result;
					}else{
						moduleCode = NotifyCustomerConstants.SMS_PKP_SIGNRETURN_EXP;
					}
				}else{
					moduleCode = NotifyCustomerConstants.SMS_PKP_SIGNRETURN_EXP;
				}
			}else{
				moduleCode = NotifyCustomerConstants.SMS_PKP_SIGNRETURN_EXP;
			}
			//更改 end
		}else {
			moduleCode = NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE;
		}
		//发送短信
		try {
			result = sendMess(waybillEntity,moduleCode,content);
		} catch (ReturnBillProcessException e) {
			result.setCode(ResultDto.FAIL);
			result.setMsg(e.getErrorCode());
			return result;
		}
		//短信发送失败
		if(!ResultDto.SUCCESS.equals(result.getCode())){
			result.setCode(ResultDto.FAIL);
			result.setMsg("短信发送失败");
			return result;
		}
		try {
			//发送消息
			result = sendInnerMess(waybillEntity, content);
		} catch (MessageException e) {
			result.setCode(ResultDto.FAIL);
			result.setMsg(e.getErrorCode());
			return result;
		}

		//平台内部消息发送失败
		if(!ResultDto.SUCCESS.equals(result.getCode())){
			result.setCode(ResultDto.FAIL);
			result.setMsg("平台内部消息发送失败");
			return result;
		}
		return result;
	}

	/**
	 * 发送消息
	 * 已通知成功的未返单运单绿色标识，
	 * 已通知失败的未返单运单灰色标识
	 * 扫描件件返回的运单
	 * ，通知按钮“ ”不显示，
	 * 已返单运单，“ ”“ ”都不显示
	 * 运单号、
	 * 返单类型不可编辑
	 * 返单类型是原件返回时，
	 * 外发快递公司和快递单号不能为空
	 * 1.	运单已签收
	 * 2.	到达部门为本部门
	 * 3.	单号查询具有排他性
	 * 4.	签收起始/截止时间默认为当前时间前一天至当前时间，
	 * 开单起始/截止时间默认为当前时间前一个月至当前时间
	 * ，时间跨度不能超过一个月
	 * @param waybillEntity
	 * @param content 短信内容
	 */
	private ResultDto sendInnerMess(WaybillEntity waybillEntity, String content) {
		ResultDto dto = new ResultDto();
		//结果返回对象
		try{
			//平台内部发给营业员的消息 保留
			InstationJobMsgEntity entity = new InstationJobMsgEntity();
			//发送人员编码
			entity.setSenderCode(FossUserContextHelper.getUserCode());
			//发送人员
			entity.setSenderName(FossUserContextHelper.getUserName());
			//发送组织编码
			entity.setSenderOrgCode(FossUserContextHelper.getOrgCode());
			//发送组织
			entity.setSenderOrgName(FossUserContextHelper.getOrgName());

			//接收方组织编码
			entity.setReceiveOrgCode(waybillEntity.getCreateOrgCode());
			//普通消息
			entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
			//content
			entity.setContext(content);
			//接收方类型 MSG__RECEIVE_TYPE__ORG  组织 
			entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
			//id
			entity.setId(UUIDUtils.getUUID());
			// 未处理
			entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
			//未读
			entity.setPostDate(new Date());
			//创建时间
			messageService.createBatchInstationMsg(entity);

			//成功标志
			dto.setCode(ResultDto.SUCCESS);
			dto.setMsg("发送成功");
			return dto;
		}catch(MessageException e){
			//出现异常
			LOGGER.error("error", e);

			//发送失败
			throw new ReturnBillProcessException(e.getErrorCode(),e);
		}
	}

	/**
	 * 1、	系统根据未返单运单信息拼装短信模板
	 * 2、	调用短信平台发送短信至发货客户，
	 * 短信内容包括
	 * ：运单号、
	 * 发货人姓名、
	 * 联系电话、
	 * 客户地址，
	 * 模板待定
	 * 3、	调用在线通知接口生成在线通知
	 * 4、	成功，
	 * 系统弹窗提示用户“通知成功!”
	 * ，失败，
	 * 系统弹窗提示用户“通知失败！”
	 * 系统刷新返单明细列表，
	 * 参考SR1
	 * 1、	系统弹出处理未返单运单界面
	 * 2、	界面自动载入选中明细的运单号和返单类型
	 * 3、	参考SR3
	 * 1.	参考SR4
	 * 1、	更新对应运单号的返单信息
	 * 2、	调用在线通知接口生成在线通知至出发部门
	 * 3、	调用短信平台发送短信至发货客户
	 * ，短信内容包括：
	 * 运单号、
	 * 发货人姓名、
	 * 联系电话、
	 * 客户地址，
	 * 模板待定
	 * 4、 处理成功，
	 * 系统弹窗提示用户“处理成功！”
	 * ，失败，
	 * 系统弹窗提示用户“处理失败”
	 * 调用短信接口sendMessage
	 */
	private ResultDto sendMess(WaybillEntity waybillDto,String moduleCode,String content)  {
		ResultDto dto = new ResultDto();
		//结果返回对象
		try {
			//短信接口NotificationEntity对象
			NotificationEntity notificationEntity = new NotificationEntity();
			//运单号
			notificationEntity.setWaybillNo(waybillDto.getWaybillNo());
			//通知内容
			notificationEntity.setNoticeContent(content);
			//通知类型为短信通知
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			//操作部门
			notificationEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
			//操作部门编码
			notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
			//发货人
			notificationEntity.setConsignee(waybillDto.getDeliveryCustomerContact());
			//手机号
			notificationEntity.setMobile(waybillDto.getDeliveryCustomerMobilephone());
			//操作人编码
			notificationEntity.setOperatorNo(FossUserContextHelper.getUserCode());
			//操作人
			notificationEntity.setOperator(FossUserContextHelper.getUserName());
			//操作时间
			notificationEntity.setOperateTime(new Date());
			//模块名称
			notificationEntity.setModuleName(moduleCode);
			//发送短信给发货人
			notifyCustomerService.sendMessage(notificationEntity);
			//成功标志
			dto.setCode(ResultDto.SUCCESS);
			dto.setMsg("短信发送成功");
			return dto;
		} catch (NotifyCustomerException e) {
			//出现异常
			LOGGER.error("error", e);
			//短信发送失败
			throw new ReturnBillProcessException(e.getErrorCode(),e);
		}
	}

	/**
	 * 获取短信信息
	 * * @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 * @date Nov 21, 2012 4:02:40 PM
	 */
	private String getNoticeContent(RtSearchReturnBillProcessDto rtDto,
									WaybillEntity waybillEntity ) {
		// 返回短信
		String sms = "";

		// null return
		if (rtDto == null) {
			return sms;
		}
		// 短信模版CODE
		String smsCode = this.getSmsCode(waybillEntity.getProductCode());
		if (StringUtil.isEmpty(smsCode)) {
			return sms;
		}
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(FossUserContextHelper.getOrgCode());
		// 参数Map
		smsParamDto.setMap(this.getSmsParam(rtDto, waybillEntity));
		try {
			//获得短信具体信息
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (Exception e){
			//异常信息
			LOGGER.error("send Sms query Template Exception", e);
		}
		return sms;
	}

	/**
	 * 获取短信参数Map：运单号、发货人姓名、联系电话、客户地址，模板待定
	 * 模板待定：运单号$waybill$、发货人姓名$name$、联系电话$telephone$、客户地址$address$，
	 * 需要综合部门确认
	 * @date Nov 21, 2012 4:02:40 PM
	 * * @param ids;
	 *  ids
	 * @param signOrWaybillType;
	 *  签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	 * @param waybillNo;
	 *  运单号
	 * @param type;
	 *  返单类型
	 * @param fromDepartmentCode;
	 *  出发部门
	 * @param toDepartmentCode;
	 *  到达部门
	 * @param status;
	 *  返单状态
	 * @param startTime;
	 *  起始时间
	 * @param endTime;
	 *  结束时间
	 *  * @param waybillNo;
	 * 运单号
	 * @param returnbillStatus;
	 * 返单状态
	 * @param returnbillType;
	 * 返单type
	 * @param returnbillTime;
	 * 返单时间
	 * @param handler;
	 * 处理人
	 * @param verifyTime;
	 * 确认时间
	 * @param feedbackInfo;
	 * 反馈信息
	 * @param expressNo;
	 * 快递号
	 * @param expressCompany;
	 * 快递公司
	 * @param createUserName;
	 * 创建人名字
	 * @param createUserCode;
	 * 创建人编码
	 * @param createOrgName;
	 * 创建组织名字
	 * @param createOrgCode;
	 * 创建组织编码
	 * @param createTime;
	 * 创建时间
	 */
	private Map<String, String> getSmsParam(RtSearchReturnBillProcessDto rtDto,
											WaybillEntity waybillEntity) {
		//创建参数map对象
		Map<String, String> paramMap = new HashMap<String, String>();
		//运单号
		if (rtDto.getWaybillNo() != null) {
			paramMap.put(ReturnBillProcessConstants.SMS_WAYBILL, rtDto.getWaybillNo());
		} else {
			paramMap.put(ReturnBillProcessConstants.SMS_WAYBILL, "");
		}
		//发货联系人姓名
		if(waybillEntity.getDeliveryCustomerContact()!=null){
			paramMap.put(ReturnBillProcessConstants.SMS_NAME, waybillEntity.getDeliveryCustomerContact());
		} else {
			paramMap.put(ReturnBillProcessConstants.SMS_NAME, "");
		}
		//联系电话
		if(waybillEntity.getDeliveryCustomerMobilephone()!=null){
			paramMap.put(ReturnBillProcessConstants.SMS_TELPHONE, waybillEntity.getDeliveryCustomerMobilephone());
		} else {
			paramMap.put(ReturnBillProcessConstants.SMS_TELPHONE, "");
		}
		//客户地址
		if(waybillEntity.getDeliveryCustomerAddress()!=null){
			paramMap.put(ReturnBillProcessConstants.SMS_ADDRESS, waybillEntity.getDeliveryCustomerAddress());
		} else {
			paramMap.put(ReturnBillProcessConstants.SMS_ADDRESS, "");
		}
		return paramMap;

	}
	/**
	 * 获取短信模版CODE
	 * @date Nov 21, 2012 4:02:40 PM
	 */
	private String getSmsCode(String productCode) {
		//根据产品运输类型判断分为零担和快递   若运输产品类型-快递
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode,new Date())){
			return ReturnBillProcessConstants.SMS_CODE_EXPRESS_PROCESS_BILL_RETURN;
		}else {
			return ReturnBillProcessConstants.SMS_CODE_PROCESS_BILL_RETURN;
		}
	}

	/**
	 * 根据运单号查询签收单信息
	 * 运单号： 运单单号
	 * @date 2012-11-22 下午1:58:45
	 */
	public List<ReturnBillProcessEntity> querySignedBillByWaybillNo(
			String waybillNo) {
		return returnBillProcessDao.querySignedBillByWaybillNo(waybillNo);
	}

	/**
	 *
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param messageService
	 * void
	 */
	public void setReturnBillProcessDao(IReturnBillProcessDao dao) {
		this.returnBillProcessDao = dao;
	}

	/**
	 *
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param messageService
	 * void
	 */
	public void setsMSTempleteService(ISMSTempleteService service) {
		this.sMSTempleteService = service;
	}

	/**
	 *
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param messageService
	 * void
	 */
	public void setWaybillManagerService(
			IWaybillManagerService service) {
		this.waybillManagerService = service;
	}

	/**
	 *
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param messageService
	 * void
	 */
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 *
	 * <p>注入<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param messageService
	 * void
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * 在线通知 set
	 * @param onLineMsgService
	 */
	public void setOnLineMsgService(IOnLineMsgService onLineMsgService) {
		this.onLineMsgService = onLineMsgService;
	}
}