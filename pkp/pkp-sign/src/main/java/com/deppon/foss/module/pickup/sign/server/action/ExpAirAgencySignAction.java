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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/AirAgencySignAction.java
 * 
 * FILE NAME        	: AirAgencySignAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.AirAgencySignVo;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;


/**
 * 
 * 签收偏线空运货物action
 * @author foss-meiying
 * @date 2012-10-16 下午2:24:19
 * @since
 * @version
 */
public class ExpAirAgencySignAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	/**
	 * 签收偏线空运货物service
	 */
	private IAirAgencySignService airAgencySignService;	
	
	/** 
  	 * 日志处理
  	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpAirAgencySignAction.class);
	
	/** 
	 * 导入文件
	 */
	private File uploadFile;
	
	/**
	 * 偏线空运vo
	 */
	private AirAgencySignVo airAgencySignVo=new AirAgencySignVo();
	/**
	 * 
	 * 保存签收信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:02:17
	 * @return
	 * @see
	 */
	@JSON
	public String addExpressWaybillSignResult(){
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			airAgencySignVo.getWaybillSignResultEntity().setSignTime(new Date());//签收时间
			String resultMsg = airAgencySignService.addExpressAgentSignResult(airAgencySignVo.getWaybillSignResultEntity(),currentInfo,airAgencySignVo.getLineSignDto(),airAgencySignVo.getOldArriveNotoutGoodsQty());
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess(AirAgencySignException.SIGN_SUCCESS);//返回成功
	}
	/**
	 * 
	 * 根据条件查询待处理运单号
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:04:03
	 * @return你
	 * @see
	 */
	public String queryExpressAgentSignByParams(){
		try {
			airAgencySignVo.getAirAgencyQueryDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());//当前登录部门
//			airAgencySignVo.setAgencyQueryDtos(airAgencySignService.queryAirInfobyParams(airAgencySignVo.getAirAgencyQueryDto()));
			//一次可以查询多个运单号
			airAgencySignVo.setAgencyQueryDtos(airAgencySignService.queryAirInfobyParamsWaybillNos(airAgencySignVo.getAirAgencyQueryDto()));
		} catch (AirAgencySignException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess();//返回成功
	}
	
	/**
	 * 
	 *根据单号查询运单基本信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:02:45
	 * @return
	 * @see
	 */
	@JSON
	public String queryByWaybillNo(){	
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WaybillDto waybillDto=new WaybillDto();
			//查询运单基本信息
			waybillDto=airAgencySignService.queryByWaybillNoIsSign(airAgencySignVo.getAirAgencyQueryDto().getWaybillNo(),airAgencySignVo.getAirAgencyQueryDto().getSerialNo());
			waybillDto.setServiceTime(sdf.format(new Date()));
			airAgencySignVo.setWaybillDto(waybillDto);
		} catch (AirAgencySignException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据运单号查询快递代理外发单信息
	 * @author foss-meiying
	 * @date 2012-12-12 下午9:17:35
	 * @return
	 * @see
	 */
	@JSON
	public String queryExpressExternalBillByNo(){
		try {
			airAgencySignVo.setExternalBillInfoDto(airAgencySignService.queryExpressExternalBillBNo(airAgencySignVo.getAirAgencyQueryDto().getWaybillNo(),airAgencySignVo.getAirAgencyQueryDto().getSerialNo()));
		} catch (AirAgencySignException e) {
			//异常处理
			//返回错误信息
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * 得到当前系统时间
	 * @author foss-meiying
	 * @date 2014-11-11 下午4:17:35
	 * @return
	 * @see
	 */
	@JSON
	public String queryExpAirAgencyNowDate(){
		try {
			if(airAgencySignVo.getAgencyQueryDtos()!=null){
				Date nowDate = new Date();
				List<AirAgencyQueryDto> agencyQueryDtos =new ArrayList<AirAgencyQueryDto> ();
				for (AirAgencyQueryDto dto : airAgencySignVo.getAgencyQueryDtos()) {
					if(StringUtils.isBlank(dto.getSignSituation())){
						dto.setSignSituation(SignConstants.NORMAL_SIGN);
						dto.setSignNote("正常签收");
					}
					if(dto.getSignGoodsQty()==null ||dto.getSignGoodsQty()<=0){
						dto.setSignGoodsQty(dto.getGoodsQtyTotal());
					}
					if(dto.getSignTime()==null){
						dto.setSignTime(nowDate);
					}
					dto.setIdentifyType(RepaymentConstants.ID_CARD);
					agencyQueryDtos.add(dto);
				}
				airAgencySignVo.setAgencyQueryDtos(agencyQueryDtos);
			}
		} catch (AirAgencySignException e) {
			//异常处理
			//返回错误信息
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * set方法
	 */
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		this.airAgencySignService = airAgencySignService;
	}
	/**
	 * get方法
	 */
	public AirAgencySignVo getAirAgencySignVo() {
		return airAgencySignVo;
	}
	/**
	 * set方法
	 */
	public void setAirAgencySignVo(AirAgencySignVo airAgencySignVo) {
		this.airAgencySignVo = airAgencySignVo;
	}
	

	/**
	 * 导入快递代理已签收的运单
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-16 上午11:37:32
	 */
	public String importExpressSignedBill() {
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					if (inputStream.available() > SettlementReportNumber.NUMBER_1024 * SettlementReportNumber.NUMBER_1024 * SettlementReportNumber.TEN) {
						uploadFile.delete();
						return returnError("上传的文件不能超过10M，请重新上传");
					}
					// 2003
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 2007
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				List<AirAgencyQueryDto> list = airAgencySignService.importQueryExpressSignedDetail(book);
				if (list == null || list.size() <= 0) {
					return returnError("导入快递代理签收运单失败: 没有查到有效的运单号信息！");
				}
				airAgencySignVo.setAgencyQueryDtos(list);
			}
			return super.returnSuccess();
		} catch (FileException e) {
			return super.returnError(e);
		} catch (IOException e) {
			return returnError("数据文件被破坏，请重新制作导入文件");
		} catch (BusinessException e) {
			String message = "";
			String code1 = StringUtil.defaultIfNull(messageBundle.getMessage(e.getErrorCode()));
			String code2 = StringUtil.defaultIfNull(messageBundle.getMessage(e.getMessage()));
			if(code1.equals(code2)){
				message = code1;
			}else{
				message = code1+code2;
			}
			LOGGER.error("导入快递代理签收运单失败: " + message);
			return returnError("导入快递代理签收运单失败: " +message.replace("\"", ""));
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					return returnError("文件关闭失败");
				}
			}
		}
	}
	
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
	/**
	 * 
	 * 批量保存签收信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:02:17
	 * @return
	 * @see
	 */
	@JSON
	public String batchAddExpressSign(){
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			String resultMsg = airAgencySignService.batchAddExpressSign(airAgencySignVo.getBatchSignDtos(), currentInfo);
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess(AirAgencySignException.SIGN_SUCCESS);//返回成功
	}
	
	
	
}