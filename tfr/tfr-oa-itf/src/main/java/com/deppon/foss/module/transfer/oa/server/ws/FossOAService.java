/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-oa-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/oa/server/ws/FossOAService.java
 *  
 *  FILE NAME          :FossOAService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-oa-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.oa.server.ws
 * FILE    NAME: FossOAServiceImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.oa.server.ws;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.workflow.ContrabandStatusRequest;
import com.deppon.esb.inteface.domain.workflow.ContrabandStatusResponse;
import com.deppon.esb.inteface.domain.workflow.GoodsFoundRequest;
import com.deppon.esb.inteface.domain.workflow.GoodsFoundResponse;
import com.deppon.esb.inteface.domain.workflow.QueryExpressPackageRequest;
import com.deppon.esb.inteface.domain.workflow.QueryExpressPackageResponse;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.workflowservice.CommonException;
import com.deppon.foss.workflowservice.WorkflowService;

/**
 * FOSS向OA提供服务（通知无标签多货找到，更新违禁品信息）
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-14 下午3:22:40
 */
public class FossOAService implements WorkflowService {

	/**
	 * 
	 */
	private static Logger LOGGER = LogManager.getLogger(FossOAService.class);
	/**
	 * 通知无标签多货找到
	 */
	private INoLabelGoodsService noLabelGoodsService;
	/**
	 * 更新违禁品信息
	 */
	private IContrabandGoodsService contrabandGoodsService;
	/**
	 * 组织信息接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IWaybillManagerService waybillManagerService;
	/**
	 * 快递-包
	 */
	private IExpressPackageService expressPackageService;

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/** 
	 * 根据包号查询包信息
	 * @author ibm-liuzhaowei
	 * @date 2013-08-06 下午5:49:47
	 */
	@Override
	public QueryExpressPackageResponse queryExpressPackage(QueryExpressPackageRequest queryExpressPackageRequest, Holder<ESBHeader> esbHeader) throws CommonException {
		QueryExpressPackageResponse queryExpressPackageResponse = null;
		String expressNumber = queryExpressPackageRequest.getExpressNumber();
		ExpressPackageEntity expressPackageEntity = expressPackageService.queryExpressPackageByPackageNo(expressNumber);
		if(expressPackageEntity != null){
			queryExpressPackageResponse = new QueryExpressPackageResponse();
			//创建人code
			queryExpressPackageResponse.setCreatorNumber(expressPackageEntity.getCreateUserCode());
			//创建人名称
			queryExpressPackageResponse.setCreatorName(expressPackageEntity.getCreateUserName());
			//出发部门编码
			queryExpressPackageResponse.setDepartureNumber(expressPackageEntity.getDepartOrgCode());
			//出发部门名称
			queryExpressPackageResponse.setDepartureName(expressPackageEntity.getDepartOrgName());
			//到达 部门编码
			queryExpressPackageResponse.setDestinationNumber(expressPackageEntity.getArriveOrgCode());
			//到达部门名称
			queryExpressPackageResponse.setDestinationName(expressPackageEntity.getArriveOrgName());
			//创建时间
			queryExpressPackageResponse.setCreatetime(convertToXMLGregorianCalendar(expressPackageEntity.getCreateTime()));
			//票数
			queryExpressPackageResponse.setBillCount(expressPackageEntity.getWaybillQty());
			//件数
			queryExpressPackageResponse.setItems(expressPackageEntity.getGoodsQty());
			//重量
			queryExpressPackageResponse.setWeight(expressPackageEntity.getWeight());
			//体积
			queryExpressPackageResponse.setVolume(expressPackageEntity.getVolume());
			//建包人所在部门编码
			queryExpressPackageResponse.setCreatorDeptNumber(expressPackageEntity.getDepartOrgCode());
			//建包人所在部门名称
			queryExpressPackageResponse.setCreatorDeptName(expressPackageEntity.getDepartOrgName());
		}
		return queryExpressPackageResponse;
	}
	
	/**
     * 将Date日期转换成XMLGregorianCalendar
     * @param date
     * @return
     */
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) throws CommonException{
		XMLGregorianCalendar xmlGregorianCalendar = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		try{
			xmlGregorianCalendar= DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}catch (DatatypeConfigurationException e) {
			throw new CommonException("将Date日期转换成XMLGregorianCalendar异常");
		}
		return xmlGregorianCalendar;
	}

	/**
	 * 
	 * 通知无标签多货找到（提供给OA的接口）
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-5 上午8:38:51
	 * @see com.deppon.foss.workflowservice.WorkflowService#notifyFindLable(com.deppon.esb.inteface.domain.workflow.GoodsFoundRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public GoodsFoundResponse notifyFindLable(GoodsFoundRequest goodsFoundRequest, Holder<ESBHeader> esbHeader)
			throws CommonException {
		LOGGER.info("*********OA调用FOSS无标签多货找到接口，更新无标签处理结果，差错编号：" + goodsFoundRequest.getErrorNo() + "************");
		LOGGER.info(goodsFoundRequest.toString());
		GoodsFoundResponse response = new GoodsFoundResponse();
		// 差错编号
		String errorNo = goodsFoundRequest.getErrorNo();
		// 原流水号
		String originalSeqNo = goodsFoundRequest.getOriginalSeqNo();
		// 原运单号
		String originalWaybillNo = goodsFoundRequest.getOriginalWaybillNo();
		// 状态
		String state = goodsFoundRequest.getState();
		// 无标签运单号
		String unlabelWayBillNo = goodsFoundRequest.getUnlabeledWayBillNo();
		// 判断是否有为空的字段
		if (!StringUtils.isEmpty(errorNo) && !StringUtils.isEmpty(originalSeqNo)
				&& !StringUtils.isEmpty(originalWaybillNo) && !StringUtils.isEmpty(state)
				&& !StringUtils.isEmpty(unlabelWayBillNo)) {
			
			boolean serialNoExists = waybillManagerService.isSerialNoExsits(originalWaybillNo, originalSeqNo);
			//PDA-876
			if(!serialNoExists){
				//运单号或流水号不存在
				response.setResult(false);
				LOGGER.error("*********OA调用FOSS无标签多货找到接口，更新无标签处理结果失败，不存在运单号或流水号：" + originalWaybillNo + "--" + originalSeqNo + "***********");
			}
			
			int updateQty = noLabelGoodsService.updateNoLabelGoodsProcessStatus(unlabelWayBillNo, state, errorNo, originalWaybillNo,
					originalSeqNo);
			if(updateQty < 1){
				//更新失败
				response.setResult(false);
				LOGGER.error("*********OA调用FOSS无标签多货找到接口，更新无标签处理结果失败，不存在差错编号：" + errorNo + "************");
			}else{
				response.setResult(true);
				LOGGER.info("*********OA调用FOSS无标签多货找到接口，更新无标签处理结果完毕，差错编号：" + errorNo + "************");
			}
			return response;
		} else {
			response.setResult(false);
			LOGGER.error("*********OA调用FOSS无标签多货找到接口，执行失败，必填字段为空************");
			return response;
		}
	}

	/**
	 * 
	 * 更新违禁品信息
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-5 上午8:39:33
	 * @see com.deppon.foss.workflowservice.WorkflowService#updateContrabandStatus(com.deppon.esb.inteface.domain.workflow.ContrabandStatusRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public ContrabandStatusResponse updateContrabandStatus(ContrabandStatusRequest contrabandStatusRequest,
			Holder<ESBHeader> esbHeader) throws CommonException {
		LOGGER.info(contrabandStatusRequest.toString());

		// 操作类型
		String operateType = contrabandStatusRequest.getOperateType();
		int type = Integer.parseInt(operateType);
		// 运单号
		String waybillNumber = contrabandStatusRequest.getWaybillNumber();
		// 是否违禁品
		boolean isContraband = contrabandStatusRequest.isIsContraband();
		// 部门标杆编码
		String deptCode = contrabandStatusRequest.getDiscoverDeptCode();
		// 差错编号
		String errorNumber = contrabandStatusRequest.getErrorNumber();
		// 发现日期
		Date discoverDate = convertToDate(contrabandStatusRequest.getDiscoverDate());

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(deptCode);
		if (org != null) {
			deptCode = org.getCode();
		}
		// 调用业务接口
		int isSuccess = contrabandGoodsService.oaToFossContrabandGoods(waybillNumber, errorNumber, deptCode,
				discoverDate, isContraband, type);
		ContrabandStatusResponse response = new ContrabandStatusResponse();
		// 设置返回值
		if (isSuccess == FossConstants.SUCCESS) {
			response.setResult(true);
		} else {
			response.setResult(false);
		}

		LOGGER.info(response.toString());
		return response;
	}

	/**
	 * 
	 * 将XMLGregorianCalendar转为Date
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-5 下午8:42:51
	 */
	private Date convertToDate(XMLGregorianCalendar xcal) {

		GregorianCalendar nowGregorianCalendar = new GregorianCalendar();

		nowGregorianCalendar = xcal.toGregorianCalendar();

		Date date = nowGregorianCalendar.getTime();

		return date;
	}

	/**
	 * 设置 通知无标签多货找到.
	 * 
	 * @param noLabelGoodsService
	 *            the new 通知无标签多货找到
	 */
	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}

	/**
	 * 设置 更新违禁品信息.
	 * 
	 * @param contrabandGoodsService
	 *            the new 更新违禁品信息
	 */
	public void setContrabandGoodsService(IContrabandGoodsService contrabandGoodsService) {
		this.contrabandGoodsService = contrabandGoodsService;
	}

	/**
	 * 设置 组织信息接口.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new 组织信息接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 快递-包 接口.
	 * 
	 */
	public void setExpressPackageService(
			IExpressPackageService expressPackageService) {
		this.expressPackageService = expressPackageService;
	}

}