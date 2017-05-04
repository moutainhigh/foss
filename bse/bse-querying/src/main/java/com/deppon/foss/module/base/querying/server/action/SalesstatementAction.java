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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/action/SalesstatementAction.java
 * 
 * FILE NAME        	: SalesstatementAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.server.action
 * FILE    NAME: IntegrativeQueryAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.querying.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.querying.server.service.ISalesstatementService;
import com.deppon.foss.module.base.querying.shared.vo.SalesstatementVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业报表清单ACTION.
 * 
 * @author 073586-FOSS-LiXuexing
 * @date 2012-12-25
 */
@SuppressWarnings("unchecked")
public class SalesstatementAction extends AbstractAction {

    private static final long serialVersionUID = 1489395520530750919L;

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SalesstatementAction.class);

    // 营业报表清单service
    private ISalesstatementService salesstatementService;
    @Autowired
    private IWaybillQueryService waybillQueryService;
    // objectVo
    private SalesstatementVo objectVo = new SalesstatementVo();

    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;
    
    @Autowired
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
     * 分页 查询 营业报表清单 list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    @JSON
	public String querySalesStatementByComplexCondation() {
		try {
			
			//校验前台传过来的开单时间
			  Boolean flagBillTime =checkTime(objectVo.getCondition().getStartBillTime(), objectVo.getCondition().getEndBilltime());
			//校验前台传过来的派送时间
			  Boolean flagDelivertime =checkTime(objectVo.getCondition().getBeginDeliverTime(), objectVo.getCondition().getEndDeliverTime());
			  //当校验时间都为false,提示时间错误
			 if(flagBillTime.equals(false)&& flagDelivertime.equals(false)){
				 return returnError("传回的时间不符合要求");
			  }
			//获取前台已删除的记录
				String deleteWaybillNos = objectVo.getCondition().getDeleteWaybillNos();
				List deleteWaybillNosArr=null;
				if(null!=deleteWaybillNos){
					 deleteWaybillNosArr =Arrays.asList(deleteWaybillNos.split(","));
					 
				}
			Map<String, Object> queryMap = salesstatementService
					.querySalesStatementByComplexCondation(
							objectVo.getCondition(), start, limit);
			if(null == queryMap){
				return returnError("收货部门、制单部门和派送部门至少得有一个不能为空！");
			}
			/*
			 * 2013-06-06 22:08 zengJunFan
			 * 【注】经过杨巍确认： 收入总额 ==到付款+预付金额 == (到付总额-代收货款)+预付金额
			 * 而接送货传过来的amountTotal实际是（代收货款总额+到付款+预付金额）的
			 */
			// 获取合计实体
			CalcTotalFeeDto totalFeeDto = (CalcTotalFeeDto) queryMap
					.get(QueryingConstant.TOTAL_FEE_DTO);
			totalFeeDto.getPrePayAmountTotal();
			totalFeeDto.getInsuranceFee();
			totalFeeDto.getPackageFeeTotal();
			// 获取接送货传过来的amountTotal
			BigDecimal amountTotal = totalFeeDto.getAmountTotal();
			// 得到减掉代收总款的amountotal
			amountTotal = amountTotal.subtract(totalFeeDto.getCodAmountTotal());
			ArrayList<SalesStatementByComplexCondationResultDto> salesStatementList = (ArrayList<SalesStatementByComplexCondationResultDto>)queryMap.get(QueryingConstant.RESULT);
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
			if(null == org){
				org = new OrgAdministrativeInfoEntity();
			}
			String orgName = org.getName();
			String orgCode =org.getCode();
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
			List<String> orgStrings = new ArrayList<String>();
			for(OrgAdministrativeInfoEntity entity :orgList){
				orgStrings.add(entity.getCode());
			}
			for(SalesStatementByComplexCondationResultDto salesStatementDto:salesStatementList){
				String waybillNo  = salesStatementDto.getWaybillNo();
				WaybillInfoByWaybillNoReultDto resultByWaybillNoReultDto = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
				if(FossConstants.NO.equals(salesStatementDto.getIsPdaCreate())){// 如果不是pda开单，那么pda开单时间不显示
					salesStatementDto.setBillTime(null);
				}
				String receiveOrgName = salesStatementDto.getReceiveOrgName();
		    //DP-营销-FOSS-运费信息保密需求DN201605030003-当运单开单勾选“预付费保密”时，不是 出发部门、财务本部、市场营销本部的都无法查看运费相关信息 -wangyuanyuan(310430)
				//313353 sonar
				amountTotal = this.sonarSplitOne(resultByWaybillNoReultDto,
						salesStatementDto, orgName,
						receiveOrgName, orgStrings, totalFeeDto,
						amountTotal);
				}
			//移除已删除的记录
			ArrayList<SalesStatementByComplexCondationResultDto> operateList = (ArrayList<SalesStatementByComplexCondationResultDto>)salesStatementList.clone();
			if (null != deleteWaybillNosArr && CollectionUtils.isNotEmpty(deleteWaybillNosArr)
				&& CollectionUtils.isNotEmpty(salesStatementList)) {
			    for (SalesStatementByComplexCondationResultDto dto : salesStatementList) {
				if(deleteWaybillNosArr.contains(dto.getWaybillNo())){
				    operateList.remove(dto);
				}
			    }
			}
			
			objectVo.setDtoList(operateList);
		
			// 设置给合计实体
			totalFeeDto.setAmountTotal(amountTotal);
			// 设置给vo
			objectVo.setTotalFeeDto(totalFeeDto);

			int count = (Integer) queryMap.get(QueryingConstant.COUNT);
			totalCount = (long) count;
			this.setTotalCount(totalCount);
			
			
			return returnSuccess();
		 } catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private BigDecimal sonarSplitOne(WaybillInfoByWaybillNoReultDto resultByWaybillNoReultDto,
			SalesStatementByComplexCondationResultDto salesStatementDto, String orgName,
			String receiveOrgName, List<String> orgStrings, CalcTotalFeeDto totalFeeDto,
			BigDecimal amountTotal) {
		if(resultByWaybillNoReultDto!=null){
			String secretPrepaid =resultByWaybillNoReultDto.getSecretPrepaid();
		    if (null != secretPrepaid && secretPrepaid.equals("Y")
				&& !orgName.equals(receiveOrgName)
				&& !orgStrings.contains("W0103")
				&& !orgStrings.contains("W0122")) {
			salesStatementDto.setPrePayAmount(null);// 预付金额
			salesStatementDto.setPackageFee(null);// 包装费
			salesStatementDto.setTransportFee(null);// 运费
			salesStatementDto.setCodFee(null);// 代收货款手续费
			salesStatementDto.setRefundAmount(null);// 退款金额
			salesStatementDto.setTotalFee(null);// 开单金额
			salesStatementDto.setInsuranceFee(null);// 保险费
			salesStatementDto.setService_fee(null);// 装卸费
			salesStatementDto.setUnitPrice(null);// 费率
			totalFeeDto.setPrePayAmountTotal(null);
			totalFeeDto.setInsuranceFee(null);
			totalFeeDto.setPackageFeeTotal(null);
			amountTotal=null;
		}
		}
		return amountTotal;
	}

    /**
     * 导出 营业报表清单list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    
    public String exportStatementByComplexCondation() {
	try {
	    // 导出文件名称
	    downloadFileName = new String(QueryingConstant.SALES_STATEMENT_NAME.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO);
	    		//URLEncoder.encode(QueryingConstant.SALES_STATEMENT_NAME, "UTF-8");
	  //获取前台已删除的记录
		String deleteWaybillNos = objectVo.getCondition().getDeleteWaybillNos();
		List deleteWaybillNosArr=null;
		if(null!=deleteWaybillNos){
			 deleteWaybillNosArr =Arrays.asList(deleteWaybillNos.split(","));
			 
		} 
	    // 获取查询参数
	    ExportResource exportResource =  salesstatementService
		    .exportStatementByComplexCondation(null == objectVo.getCondition()?new WaybillListByComplexCondationDto():objectVo.getCondition(),deleteWaybillNosArr);
	    ExportSetting exportSetting = new ExportSetting();
	    // 设置名称
	    exportSetting.setSheetName(QueryingConstant.SALES_STATEMENT_NAME);

	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
	    return returnSuccess();
	    
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError("UnsupportedEncodingException", e);
	}
    }
    /**
     * 打印营业报表清单list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    public String printSalesStatementByComplexCondation() {
	// TODO Auto-generated method stub
	return returnSuccess();
    }
 
    /**
     *  精确查询 通过 CODE 查询 当前登录用户所在 营业部 
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    public String querySaleDepartmentByCode() {
	objectVo.setSaleDepartment(salesstatementService.querySaleDepartmentByCode(objectVo.getCodeStr()));
	return returnSuccess();
    }
    
    /**
     * 
     *<p> 校验时间的方法</p>
     *@author 132599-shenweihua
     *@date   2013-7-08下午3:20:24
     * @param start
     * @param end
     * @return
     */
    private Boolean checkTime(Date startTime,Date endTime){
    	//开始时间为空的话，为false
    	if(startTime ==null){
    		return false;
    	}
    	//结束时间为空的话，为false
    	if(endTime ==null){
    		return false;
    	}
    	// 返回两个时间的相差天数 
    	long diffs = DateUtils.getTimeDiff(startTime, endTime);
    	//判断开始时间是否大于结束时间
    	if(diffs<0){
    		returnError("开始时间不能大于结束时间");
    		return false;
    	//判断两个时间相差天数是不是超过三天了
    	}else  if(diffs>SettlementConstants.DATE_LIMIT_DAYS_WEEK){
    		returnError("查询时间不能超过"+SettlementConstants.DATE_LIMIT_DAYS_WEEK+"天");
    		return false;
    	}
    	return true;
    }

    /**
     * @param salesstatementService
     *            the salesstatementService to set
     */
    public void setSalesstatementService(
	    ISalesstatementService salesstatementService) {
	this.salesstatementService = salesstatementService;
    }

    /**
     * @return the objectVo
     */
    public SalesstatementVo getObjectVo() {
	return objectVo;
    }

    /**
     * @param objectVo
     *            the objectVo to set
     */
    public void setObjectVo(SalesstatementVo objectVo) {
	this.objectVo = objectVo;
    }

    /**
     * @return the downloadFileName
     */
    public String getDownloadFileName() {
	return downloadFileName;
    }

    /**
     * @param downloadFileName
     *            the downloadFileName to set
     */
    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
	return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
    }
}
