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
 *  FILE PATH          :/AirMakeFlightjointListAction.java
 * 
 *  FILE NAME          :AirMakeFlightjointListAction.java
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

import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirPickupBillJointlargeListException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTransPickupBillVo;

/**
 * 制作合大票清单action.
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 上午9:21:34
 */
public class AirMakeFlightjointListAction extends AbstractAction {
	
	private static final long serialVersionUID = 216869796298878039L;

	/** 制作中转提货清单service */
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
			//调用中转提货清单servie服务
			//获取中转单号
			airTransPickupBillVo.getAirTransPickupBillDto().setAirTransferPickupbillNo(airTransPickupBillService.generateTransfersNumber());
			//catch异常
		} catch (Exception e) {
			//不做任何处理
		}
		//返回成功
		return this.returnSuccess();
	}
	
	/**
	 * 新增合大票时 根据运单号 查询未制作合大票流水
	 * @return the string
	 * @author 269701-foss-lln
	 * @date 2016-04-20 下午2:20:29
	 */
	@JSON
	public String findLeftSerial() {
		//获取中转提货清单dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		//根据运单号查询流水信息
		List<SerialEntity> resultList=airTransPickupBillService.findLeftSerial(airTransPickupBillDto);
		airTransPickupBillVo.setSerialList(resultList);
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 合大票修改时 根据运单号查询未制作合大票流水号
	 * @return the string
	 * @author 269701-foss-lln
	 * @date 2016-04-20 下午2:20:29
	 */
	@JSON
	public String findLeftSerialForModify() {
		//获取中转提货清单dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		//根据运单号查询流水信息
		List<SerialEntity> resultList = null;
		//是包的时候不查
		if(!airTransPickupBillDto.getWaybillNo().substring(0, 1).equals("B")){
			resultList=airTransPickupBillService.findLeftSerialForModify(airTransPickupBillDto);
		}
		airTransPickupBillVo.setSerialList(resultList);
		//返回成功
		return super.returnSuccess();
	}
	
	
	
	/**
	 * 根据运单号 查询已制作合大票流水
	 * @return the string
	 * @author 269701-foss-lln
	 * @date 2016-04-20 下午2:20:29
	 */
	@JSON
	public String findRightSerial() {
		//获取中转提货清单dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		//根据运单号查询流水信息
		List<SerialEntity> resultList=airTransPickupBillService.findRightSerial(airTransPickupBillDto);
		airTransPickupBillVo.setSerialList(resultList);
		//返回成功
		return super.returnSuccess();
	}
	/**
	 * 保存制作合大票流水号
	 * @return the string
	 * @author 269701-foss-lln
	 * @date 2016-04-20 下午2:20:29
	 */
	@JSON
	public String saveSerialNo() {
		//获取中转提货清单dto
		List<SerialEntity> serialList = airTransPickupBillVo.getSerialList();
		//根据运单号查询流水信息
		airTransPickupBillService.saveSerialNo(serialList);
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 根据航空公司,正单号查询航空正单明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午2:20:29
	 */
	@JSON
	public String queryAirPickupbillList() {
		//获取中转提货清单dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		//根据正单号查询中转提货清单中是否存在此正单
		boolean  inexistence = airTransPickupBillService.checkExistAirTransPickupBill(airTransPickupBillDto);
		//如果不存在则不往下执行
		//否则将清单和明细结果返回
		if(!inexistence){
			//如果成功则根据正单号查询运单明细
			List<AirWaybillDetailEntity> resultAirWaybillDetailList = airTransPickupBillService.queryAirWaybillDetailList(airTransPickupBillDto);
			//如果运单明细list为空则不设置航空正单主体信息
			//否则设置航空正单主体信息
			if (!CollectionUtils.isEmpty(resultAirWaybillDetailList)) {//如果不为空
				//获取航空正单主信息
				AirWaybillEntity airWaybillEntity = airTransPickupBillService.queryAirWaybillReceiverInfo(resultAirWaybillDetailList.get(0).getAirWaybillId());
				//查询结果设置vo中定义的正单主体对象中
				airTransPickupBillVo.setAirWaybillEntity(airWaybillEntity);
			}
			//设置运单list结果
			airTransPickupBillVo.setResultAirWaybillDetailList(resultAirWaybillDetailList);
		}else {
			//设置判断结果
			//存在与否方便界面进行判断
			airTransPickupBillVo.getAirTransPickupBillDto().setInexistence(inexistence);
		}
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 添加合大票明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	@JSON
	public String addAirPickupbillDetailInfo() {
		//获取dto中的参数
		//将dto中的运单号获取作为后台查询条件
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		try{
			//查询运单明细
			List<AirWaybillDetailEntity> airWaybillDetailList = airTransPickupBillService.addWaybillNoInfo(airTransPickupBillDto.getWaybillNo());
			//设置运单明细
			airTransPickupBillVo.setResultAirWaybillDetailList(airWaybillDetailList);
		} catch (BusinessException e) {
			//抛出异常
			return super.returnError(e);
		}
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 新增合大票清单、合大票清单明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 上午9:06:09
	 */
	@JSON
	public String addAirPickBILLAirPickupBill() {
		try {
			//获取合大票信息
			AirTranDataCollectionEntity airTranDataCollectionEntity = airTransPickupBillVo.getAirTranDataCollectionEntity();
			//获取合大票明细list
			List<AirPickupbillDetailEntity> list = airTransPickupBillVo.getAirPickupbillDetailList();
			//根据正单id获取正单信息
			AirWaybillEntity airWaybillEntity = airTransPickupBillService.queryAirWaybillReceiverInfo(airTranDataCollectionEntity.getAirWaybillId());
			//保存合大票清单、明细
			AirPickupbillEntity airPickupbillEntity = airTransPickupBillService.addAirTransPickBILLAirPickupBill(list,airWaybillEntity, airTranDataCollectionEntity);
			//设置操作成功的正单号
			airTransPickupBillVo.getAirTransPickupBillDto().setAirWaybillNo(airPickupbillEntity.getAirWaybillNo());
			//设置正单明细
			airTransPickupBillVo.setAirWaybillEntity(airWaybillEntity);
			airTransPickupBillVo.setAirPickupbillId(airPickupbillEntity.getId());
			//catch异常
		} catch (BusinessException e) {
			//抛出异常
			return super.returnError(e);
		}
		//开发本地测试调用job
		//TODO
			//pushAirPickUpInfoService.doPushAirPickUpInfo();
		//返回成功
		return super.returnSuccess();
	}

	/**
	 * 根据航空正单id查询收货人姓名、电话、地址.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-13 下午4:06:21
	 */
	@JSON
	public String queryAirWaybillReceiverInfo() {
		//获取正单信息
		AirWaybillEntity airWaybillEntity = airTransPickupBillService.queryAirWaybillReceiverInfo(airTransPickupBillVo.getAirTransPickupBillDto().getAirWaybillId());
		//设置正单信息
		airTransPickupBillVo.setAirWaybillEntity(airWaybillEntity);
		//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 根据运单号查询是否在合大票明细中存在.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 上午10:33:02
	 */
	@JSON
	public String queryAirwaybillNoisNotExist(){
		//获取dto中的正单号
		String airWaybillNo = airTransPickupBillVo.getAirTransPickupBillDto().getAirWaybillNo();
		//查询合大票清单信息
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillService.queryAirPickupbillEntity(airWaybillNo);
		//设置合大票清单信息
		airTransPickupBillVo.setAirPickupbillEntity(airPickupbillEntity);
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 根据 制单时间,航空公司,正单号,目的站,到达网点,空运总调
	 * 查询合票清单明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午2:41:32
	 */
	@JSON
	public String queryMakePickGoods(){
		try {
			//获取dto
			AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
			//查询合大票明细list
			List<AirPickupbillEntity> airPickupbillList = airTransPickupBillService.queryMakePickGoods(airTransPickupBillDto,this.getLimit(), this.getStart());
			//获取查询结果总记录数
			Long count = airTransPickupBillService.getMakePickGoodsCount(airTransPickupBillDto);
			//设置总记录数
			this.setTotalCount(count);
			//设置查询结果
			airTransPickupBillVo.setAirPickupbillList(airPickupbillList);
			//catch异常
		} catch (AirPickupBillJointlargeListException e) {
			//抛出异常
			return super.returnError(e);
		}
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 根据航空正单id查询合大票清单、明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-3 下午4:19:53
	 */
	@JSON
	public String queryAirPickupbillDetail() {
		//获取正单号
		String airWaybillNo = airTransPickupBillVo.getAirTransPickupBillDto().getAirWaybillNo();
		//查询合大票请、明细 
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillService.queryAirTransPickupBillOrDetail(airWaybillNo);
		//设置合大票清单(数据来源dot)
		airTransPickupBillVo.setAirPickupbillEntity(airTransPickupBillDto.getAirPickupbillEntity());
		//设置合大票明细(数据来源dot)
		airTransPickupBillVo.setAirPickupbillDetailList(airTransPickupBillDto.getAirPickupbillDetailList());
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 上传合大票清单给EDI系统.
	 * @author 099197-foss-zhoudejun
	 * @throws UnsupportedEncodingException 
	 * @date 2012-12-27 上午11:45:47
	 */
	public String uploadPickupCallEdi(){
		//获取页面传递id
		List<String> idList = airTransPickupBillVo.getIds();
		//获取是否上传edi标识
		String callIsNotEdiFlag = airTransPickupBillVo.getCallIsNotEdiFlag();
		//获取运单号
		String airWaybillNo = airTransPickupBillVo.getAirWaybillNo();
		//获取航班号
		String fightNo = airTransPickupBillVo.getFightNo();
		try {
			//拼接文件名
			fileName = URLEncoder.encode(fightNo +"-"+ airWaybillNo,"UTF-8");
			//catch异常
		} catch (UnsupportedEncodingException e) {
			//抛出异常
			throw new AirPickupBillJointlargeListException("编码转换异常");
		}
		try {
			//获取输出流
			excelStream = airTransPickupBillService.uploadPickupCallEdi(idList,airWaybillNo,callIsNotEdiFlag);
			//catch异常
		} catch (AirPickupBillJointlargeListException e) {
			//抛出异常
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 查找合大票清单
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-6 上午11:56:48
	 */
	public String queryPickupInventory(){
		//获取正单号
		String airWaybillNo = airTransPickupBillVo.getAirWaybillNo();
		//根据正单号查合大票清单
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillService.queryAirPickupbillEntity(airWaybillNo);
		//设置合大票清单
		airTransPickupBillVo.setAirPickupbillEntity(airPickupbillEntity);
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 设置 制作中转提货清单service.
	 * @param airTransPickupBillService the new 制作中转提货清单service
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
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
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 导出Excel 文件名.
	 *
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