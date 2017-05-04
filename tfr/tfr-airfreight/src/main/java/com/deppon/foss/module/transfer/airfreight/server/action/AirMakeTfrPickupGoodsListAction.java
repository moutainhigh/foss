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
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/AirMakeTfrPickupGoodsListAction.java
 * 
 *  FILE NAME          :AirMakeTfrPickupGoodsListAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirPickupBillJointlargeListException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirTransPickupBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTransPickupBillVo;

/**
 * 制作中转提货清单action.
 * @author 099197-foss-zhoudejun
 * @date 2012-11-16 上午11:59:35
 */
public class AirMakeTfrPickupGoodsListAction extends AbstractAction {
	
	private static final long serialVersionUID = 7457790032354046337L;

	/** 作中转提货清单service */
	private IAirTransPickupBillService airTransPickupBillService;

	/** 中转提货清单vo */
	private AirTransPickupBillVo airTransPickupBillVo = new AirTransPickupBillVo();
	
	/** 导出Excel 文件流. */
	private InputStream excelStream;  
	
	/** 导出Excel 文件名. */
	private String fileName;  
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;

	/**
	 * 生成中转单号.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 上午9:24:09
	 */
	@JSON
	public String generateTransfersNumber() {
		try {
			//调用生成中转单号服务
			//设置中转单号
			airTransPickupBillVo.getAirTransPickupBillDto().setAirTransferPickupbillNo(airTransPickupBillService.generateTransfersNumber());
			//catch异常
		} catch (Exception e) {
			//不做处理
		}
		//返回成功
		return this.returnSuccess();
	}

	/**
	 * 根据航空公司,正单号查询航空正单明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午2:20:29
	 */
	@JSON
	public String queryAirPickupbillDetailInfo() {
		//获取dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		//默认查询需要中转的运单
		airTransPickupBillDto.setBeTransfer(AirfreightConstants.AIRFREIGHT_YES_BETRANSFER);
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		airTransPickupBillDto.setOrigOrgCode(orgAdministrativeInfoEntity.getCode());
		//查询中转提货清单明细
		List<AirPickupbillDetailEntity> airPickupbillDetailList = airTransPickupBillService.queryAirPickupbillList(airTransPickupBillDto);
		//调用中转提货清单服务
		//查询中转提货清单
		//返回中转提货清单结果
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillService.queryAirPickupbillEntityByDto(airTransPickupBillDto);
		//设置中转提货清单明细list
		airTransPickupBillVo.setAirPickupbillDetailList(airPickupbillDetailList);
		//设置中转提货清单
		airTransPickupBillVo.setAirPickupbillEntity(airPickupbillEntity);
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 根据运单号添加一条合票清单明细信息.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	@JSON
	public String waybillNoAddToTransferDetail() {
		//获取dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		//调用中转提货清单服务
		//根据运单号查询合票清单明细
		//返回查询结果
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		airTransPickupBillDto.setOrigOrgCode(orgAdministrativeInfoEntity.getCode());
		airTransPickupBillDto.setBeTransfer(AirfreightConstants.AIRFREIGHT_YES_BETRANSFER);
		AirPickupbillDetailEntity airPickupbillDetailEntity = airTransPickupBillService.waybillNoAddToTransferDetail(airTransPickupBillDto);
		//设置合大票清单明细
		airTransPickupBillVo.setAirPickupbillDetailEntity(airPickupbillDetailEntity);
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 新增合大票清单.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午10:12:32
	 */
	@JSON
	public String addAirTransPickBILLAirPickupBill() {
		try {
			//获取vo中的合票清单
			//获取vo中的合票清单明细list
			//调用合大票新增服务
			airTransPickupBillService.addAirTransferWaybillDetail(airTransPickupBillVo.getAirTranDataCollectionEntity(),airTransPickupBillVo.getAirPickupbillDetailList());
			//catch异常
		} catch (AirTransPickupBillException e) {
			//抛出异常
			return returnError(e);
		}
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 上传中转提货清单给EDI系统.
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:32:37
	 */
	public String uploadTranPickupCallEdi(){
			//获取中转提货清单id
			List<String> idsList = airTransPickupBillVo.getIds();
			//获取正单号
			String airWaybillNo = airTransPickupBillVo.getAirWaybillNo();
			//获取是否调用edi标识
			String callIsNotEdiFlag = airTransPickupBillVo.getCallIsNotEdiFlag();
			//获取航班号
			String fightNo = airTransPickupBillVo.getFightNo();
			try {
				//设置文件名编码
				fileName = URLEncoder.encode(fightNo +"-"+ airWaybillNo,"UTF-8");
				//catch 异常
			} catch (UnsupportedEncodingException e) {
				//抛出异常
				throw new AirPickupBillJointlargeListException("编码转换出错!");
			}
			try {
				//根据idlist,正单号,edi标识,航班号
				//调用上传服务
				//返回输出流
				excelStream = airTransPickupBillService.uploadTranPickupCallEdi(idsList,airWaybillNo,callIsNotEdiFlag);
				//catch异常
			}catch (AirPickupBillJointlargeListException e) {
				//返回失败信息
				return returnError(e);
			}
			//返回成功信息
			return returnSuccess();
	}
	
	/**
	 * 获取 中转提货清单vo.
	 * @return the 中转提货清单vo
	 */
	public AirTransPickupBillVo getAirTransPickupBillVo() {
		return airTransPickupBillVo;
	}
	
	/**
	 * 设置 中转提货清单vo.
	 * @param airTransPickupBillVo the new 中转提货清单vo
	 */
	public void setAirTransPickupBillVo(
			AirTransPickupBillVo airTransPickupBillVo) {
		this.airTransPickupBillVo = airTransPickupBillVo;
	}
	
	/**
	 * 设置 作中转提货清单service.
	 * @param airTransPickupBillService the new 作中转提货清单service
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}
	
	/**
	 * 获取 导出Excel 文件流.
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	/**
	 * 设置 导出Excel 文件流.
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/**
	 * 获取 导出Excel 文件名.
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * 设置 导出Excel 文件名.
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
}