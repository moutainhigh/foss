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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/service/impl/UnloadTaskQueryService.java
 *  
 *  FILE NAME          :UnloadTaskQueryService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.service.impl.FOSSToWkService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskQueryDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

import net.sf.json.JSONObject;

/**
 * 
 * 卸车任务查询Service
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:31:22
 */
public class UnloadTaskQueryService implements IUnloadTaskQueryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(IUnloadTaskQueryService.class);
	/**
	 * 查询卸车任务dao
	 */
	private IUnloadTaskQueryDao unloadTaskQueryDao;
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 灰度开关
	 */
    private IConfigurationParamsService configurationParamsService;
	
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据部门code找顶级组织 
	 * @param orgCode
	 * @return    
	 * @return OrgAdministrativeInfoEntity    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-28下午4:46:59
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
		
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}

	/**
	 * 查询卸车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:39
	 */
	@Override
	public List<UnloadTaskDto> queryUnloadTask(UnloadTaskDto unloadTaskDto,
			int limit, int start) {
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		unloadTaskDto.setUnloadOrgCode(orgCode);
		return unloadTaskQueryDao.queryUnloadTask(unloadTaskDto, limit, start);
	}

	/**
	 * 查询卸车任务总数
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:54
	 */
	@Override
	public Long getTotCount(UnloadTaskDto unloadTaskDto) {
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		unloadTaskDto.setUnloadOrgCode(orgCode);
		return unloadTaskQueryDao.getTotCount(unloadTaskDto);
	}
	
	/**  零担
	 * 根据卸车任务编号获取单条卸车任务
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:06
	 */
	@Override
	public UnloadTaskDto getUnloadTaskByUnloadTaskNo(String unloadTaskNo) {
		return unloadTaskQueryDao.getUnloadTaskByUnloadTaskNo(unloadTaskNo);
	}
	

	/**
	 *  零担
	 * 根据卸车任务编号获取卸车明细</br>
	 * 	交接件数:</br>
			如果单据类型为交接单, 取该单据下 交接单明细表中sum(已配件数)</br>
			如果为配载单, 根据配载单找到该配载单下所有交接单, 再根据交接单找交接明细下sum(已配件数)</br>
			如果为接货任务, 转货交接单, 派送拉回货 则取交接表(pkp.t_srv_stay_handover) 下所有的件数</br>
		件数扫描率:</br>
			已扫描件数/交接件数</br>
		交接重量</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配重量)</br>
			如果为派送拉回货, 交接单开单重量*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为接回货, 取交接单中开单重量</br>
		交接体积</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配体积)</br>
			如果为派送拉回货, 交接单开单体积*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为接回货, 取交接单中开单体积</br>
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:33
	 */
	@Override
	public List<UnloadWaybillDetailDto> queryUnloadTaskDetailByUnloadTaskNo(
			String unloadTaskNo) {
		List<UnloadWaybillDetailDto> unloadWaybillDetails = unloadTaskQueryDao.queryUnloadTaskDetailByUnloadTaskNo(unloadTaskNo);
		//当多货时, 件数扫描率计算方式改为 (扫描件数/已操作件数)
		for(UnloadWaybillDetailDto unloadWaybillDetail : unloadWaybillDetails) {
			String billNo = unloadWaybillDetail.getBillNo();
			if(StringUtils.equals(billNo, "多货") || StringUtils.isEmpty(billNo)) {
				//操作件数
				Integer operationGoodsQty = unloadWaybillDetail.getOperationGoodsQty();
				if(operationGoodsQty == null) {
					continue;
				}
				//扫描件数
				Integer scanGoodsQty = unloadWaybillDetail.getScanGoodsQty();
				if(scanGoodsQty == null) {
					continue;
				}
				//件数扫描率
				BigDecimal scanGoodsQty1 = new BigDecimal(scanGoodsQty);
				BigDecimal operationGoodsQty1 = new BigDecimal(operationGoodsQty);
				
				BigDecimal scanGoodsQtyRate = BigDecimal.ZERO;
				if(operationGoodsQty > 0) {
					scanGoodsQtyRate = scanGoodsQty1.divide(operationGoodsQty1,ConstantsNumberSonar.SONAR_NUMBER_4,BigDecimal.ROUND_HALF_UP);
				}
				unloadWaybillDetail.setScanGoodsQtyRate(scanGoodsQtyRate);
			}
		}
		return unloadWaybillDetails;
	}
	
   /**
	* @description 根据卸车任务编号获取快递卸车明细
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#queryUnloadExpressTaskDetailByUnloadTaskNo(java.lang.String)
	* @author 328768-foss-gaojianfu
	* @update 2016年4月26日 下午3:19:46
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	public List<UnloadWaybillDetailDto> queryUnloadExpressTaskDetailByUnloadTaskNo(String unloadTaskNo) throws Exception{
		
		IFOSSToWkService fossToWkService =new FOSSToWkService();
		
		Map<String,String> map=new HashMap<String,String>();
		//设置  卸车任务编号
		map.put("unloadTaskNo", unloadTaskNo);
		//设置  操作部门编号
		map.put("orgCode", FossUserContext.getCurrentDeptCode());
		String requestParameter=JSONObject.fromObject(map).toString();
		LOGGER.info("Foss查询悟空快递卸车明细:"+requestParameter);
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.queryUnloadExpressTaskDetail(requestParameter);
		
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss查询悟空快递卸车明细失败!");
			throw new TfrBusinessException("Foss查询悟空快递卸车明细失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss查询悟空快递卸车明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss查询悟空快递卸车明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
		}
		
		List<Object> expressDetailList=(List<Object>) fossToWKResponseEntity.getData();
		if(null == expressDetailList) {
			return null;
		}
		List<UnloadWaybillDetailDto> unloadWaybillExpressDetailDtoList=new ArrayList<UnloadWaybillDetailDto>();
			
		UnloadWaybillDetailDto unloadWaybillDetail=null;
		JSONObject jsonObj=null;
		for(Object obj:expressDetailList){
			unloadWaybillDetail=new UnloadWaybillDetailDto();
	    	jsonObj=JSONObject.fromObject(obj);
	    	
	    	if("more".equalsIgnoreCase(jsonObj.getString("goodsStatus"))
	    			||"lack".equalsIgnoreCase(jsonObj.getString("goodsStatus"))){
	    		unloadWaybillDetail.setHasDifferences(true);
	    	}
	    	
	    	//goodsStatus如果是more则为多货
	    	if("more".equalsIgnoreCase(jsonObj.getString("goodsStatus"))){
	    		unloadWaybillDetail.setBillNo("多货");
	    	}else{
	    		unloadWaybillDetail.setBillNo(jsonObj.getString("handoverBillNo"));	
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("destOrgCode"))&&!"null".equals(jsonObj.getString("destOrgCode"))){
	    		//设置 到达部门编号
		    	unloadWaybillDetail.setDestOrgCode(jsonObj.getString("destOrgCode"));
	    	}else{
	    		unloadWaybillDetail.setDestOrgCode("");
	    	}
	    	
	    	if(StringUtils.isNotEmpty(jsonObj.getString("destOrgName"))&&!"null".equals(jsonObj.getString("destOrgName"))){
	    		//设置 到达部门名称
		    	unloadWaybillDetail.setDestOrgName(jsonObj.getString("destOrgName"));
	    	}else{
	    		unloadWaybillDetail.setDestOrgName("");
	    	}
	    	
	    	//设置 货名
	    	if("null".equals(jsonObj.getString("goodsName"))||"".equals(jsonObj.getString("goodsName"))){
	    		unloadWaybillDetail.setGoodsName("");
	    	}else{
	    		unloadWaybillDetail.setGoodsName(jsonObj.getString("goodsName"));
	    	}
	    	if(StringUtils.isNumeric(jsonObj.getString("cargoQty"))){
	    		//设置  交接件数
	    		unloadWaybillDetail.setHandoverTotQty(Integer.parseInt(jsonObj.getString("cargoQty")));
	    	}else{
	    		unloadWaybillDetail.setHandoverTotQty(0);
	    	}
	    	unloadWaybillDetail.setId(jsonObj.getString("id"));
	    	
	    	if(StringUtils.isNotEmpty(jsonObj.getString("origOrgName"))&&!"null".equals(jsonObj.getString("origOrgName"))){
	    		//设置  出发部门名称
		    	unloadWaybillDetail.setOrigOrgName(jsonObj.getString("origOrgName"));
	    	}else{
	    		unloadWaybillDetail.setOrigOrgName("");
	    	}
	    	//设置 包装
	    	
	    	if("null".equals(jsonObj.getString("pack"))||"".equals(jsonObj.getString("pack"))){
	    		unloadWaybillDetail.setPack("0");
	    	}else{
	    		unloadWaybillDetail.setPack(jsonObj.getString("pack"));
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("operationGoodsQty"))&&!"null".equals(jsonObj.getString("operationGoodsQty"))){
	    		//设置  已操作件数
	    		unloadWaybillDetail.setOperationGoodsQty(Integer.parseInt(jsonObj.getString("operationGoodsQty")));
	    	}else{
	    		unloadWaybillDetail.setOperationGoodsQty(0);
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("scanGoodsQty"))&&!"null".equals(jsonObj.getString("scanGoodsQty"))){
	    		//设置 扫描件数
	    		unloadWaybillDetail.setScanGoodsQty(Integer.parseInt(jsonObj.getString("scanGoodsQty")));
	    	}else{
	    		unloadWaybillDetail.setScanGoodsQty(0);
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("scanGoodsQtyRate"))&&!"null".equals(jsonObj.getString("scanGoodsQtyRate"))){
	    		//设置 件数扫描率
	    		unloadWaybillDetail.setScanGoodsQtyRate(BigDecimal.valueOf(Double.parseDouble(jsonObj.getString("scanGoodsQtyRate"))));
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("weightTotal"))&&!"null".equals(jsonObj.getString("weightTotal"))){
	    		//设置 交接重量
	    		unloadWaybillDetail.setUnloadWeightTotal(BigDecimal.valueOf(Double.parseDouble(jsonObj.getString("weightTotal"))));
	    	}else{
	    		unloadWaybillDetail.setUnloadWeightTotal(new BigDecimal(0));
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("volumeTotal"))&&!"null".equals(jsonObj.getString("volumeTotal"))){
	    		//设置 交接体积
	    		unloadWaybillDetail.setUnloadVolumeTotal(BigDecimal.valueOf(Double.parseDouble(jsonObj.getString("volumeTotal"))));
	    	}else{
	    		unloadWaybillDetail.setUnloadVolumeTotal(new BigDecimal(0));
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("transportType"))&&!"null".equals(jsonObj.getString("transportType"))){
	    		//设置 运输性质
		    	unloadWaybillDetail.setTransportType(jsonObj.getString("transportType"));
	    	}else{
	    		unloadWaybillDetail.setTransportType("");
	    	}
	    	if(StringUtils.isNotEmpty(jsonObj.getString("operationTime"))&&!"null".equals(jsonObj.getString("operationTime"))){
	    		//设置操作时间
	    		unloadWaybillDetail.setOptTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObj.getString("operationTime")));
	    	}
	    	
	    	//设置 运单号（笼号,包号,运单号其中一种） 
	    	unloadWaybillDetail.setWaybillNo(jsonObj.getString("cargoNo"));
	    	//设置 任务编号
	    	unloadWaybillDetail.setUnloadTaskNo(jsonObj.getString("unloadTaskNo"));
	    	//设置 件号
	    	unloadWaybillDetail.setCargoNo(jsonObj.getString("cargoNo"));
	    	//设置 件类型
	    	unloadWaybillDetail.setCargoType(jsonObj.getString("cargoType"));
	    	//设置  快递标识
	    	unloadWaybillDetail.setExpressOrLingdan("express");
	    	unloadWaybillExpressDetailDtoList.add(unloadWaybillDetail);
	    }
		
		return unloadWaybillExpressDetailDtoList; 
	}
	
	
	
	/**
	 * 根据taskID得到所有理货员
	 * 如果是手工卸车，卸车创建人不需要显示。
	 * 如果是PDA卸车，需要显示。
	 * T_OPT_LOADER_PARTICIPATION这个表中有be_creator字段，Y为创建者，N为参与者
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderByTaskId(
			String unloadTaskId, String unloadWay) {
		return unloadTaskQueryDao.queryLoaderByTaskId(unloadTaskId, unloadWay);
	}
	
	/**
	 * 根据unloadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@Override
	public List<UnloadSerialNoDetailEntity> queryUnloadSerialNoByUnloadWaybillDetailId(
			String unloadWaybillDetailId) {
		return unloadTaskQueryDao.queryUnloadSerialNoByUnloadWaybillDetailId(unloadWaybillDetailId);
	}
	
	/**
	 * 根据卸车运单ID获取所有流水号</br>
	 * @author gaojianfu
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception 
	 * @date 2016-04-22 下午13:20:10
	 */
	@SuppressWarnings("unchecked")
	public List<UnloadSerialNoDetailEntity> queryUnloadSerialNoByExpressUnloadWaybillDetailId(
			ExpressQueryUnloadWaybillDetailDto expressQueryUnloadWaybillDetailDto) throws Exception{
		IFOSSToWkService fossToWkService =new FOSSToWkService();
		//设置 部门编号
		expressQueryUnloadWaybillDetailDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		
		String requestParameter=JSONObject.fromObject(expressQueryUnloadWaybillDetailDto).toString();
		
		LOGGER.info("Foss查询悟空快递卸车明细:"+requestParameter);
		FossToWKResponseEntity fossToWKResponseEntity = fossToWkService.queryExpressUnloadSerialNo(requestParameter);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss查询悟空快递卸车明细失败!");
			throw new TfrBusinessException("Foss查询悟空快递卸车明细失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss查询悟空快递卸车明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss查询悟空快递卸车明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
		}
		List<Object> listData=(List<Object>) fossToWKResponseEntity.getData();
		
		UnloadSerialNoDetailEntity expressUnloadSerialNoDetailEntity=null;
		JSONObject jsonObj=null;
		List<UnloadSerialNoDetailEntity> list=new ArrayList<UnloadSerialNoDetailEntity>();
		for(Object obj:listData){
			expressUnloadSerialNoDetailEntity=new UnloadSerialNoDetailEntity();
			jsonObj=JSONObject.fromObject(obj);
			//设置  流水号
			expressUnloadSerialNoDetailEntity.setSerialNo(jsonObj.getString("cargoNo"));
			if(jsonObj.getString("scanStatus").equalsIgnoreCase(UnloadConstants.NOSCAN)){
				//设置 扫描状态
				expressUnloadSerialNoDetailEntity.setScanStatus("未扫");
			}else if(jsonObj.getString("scanStatus").equalsIgnoreCase(UnloadConstants.SCAN)){
				//设置 扫描状态
				expressUnloadSerialNoDetailEntity.setScanStatus("已扫");
			}else{
				expressUnloadSerialNoDetailEntity.setScanStatus("");
			}
			
			if(StringUtils.isEmpty(jsonObj.getString("goodsStatus"))||"null".equals(jsonObj.getString("goodsStatus"))){
				//设置 货物状态
				expressUnloadSerialNoDetailEntity.setGoodsStatus("");
			}else{
				expressUnloadSerialNoDetailEntity.setGoodsStatus(jsonObj.getString("goodsStatus"));
			}
			
			//设置 操作时间
			expressUnloadSerialNoDetailEntity.setOptTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObj.getString("operationTime")));
			list.add(expressUnloadSerialNoDetailEntity);
		}
		
		return list;
	}
	
	/**
	 * 设置 查询卸车任务dao.
	 *
	 * @param unloadTaskQueryDao the new 查询卸车任务dao
	 */
	public void setUnloadTaskQueryDao(IUnloadTaskQueryDao unloadTaskQueryDao) {
		this.unloadTaskQueryDao = unloadTaskQueryDao;
	}

	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}

	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出卸车明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public InputStream exportLoadWayBillByTaskNo(String taskNo) {
		InputStream excelStream = null;
		List<UnloadWaybillDetailDto> unloadWayBillDetails = unloadTaskQueryDao.queryExportUnloadTaskDetailByUnloadTaskNo(taskNo);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("根据卸车任务编号获取快递单条卸车任务开关" + tfrSwitch4Ecs);
		List<UnloadWaybillDetailDto> unloadWaybillDetailDtoExpressList = null;
		if (tfrSwitch4Ecs) {
			// 根据卸车任务编号查询快递卸车明细将返回结果与零担明细合并
			try {
				unloadWaybillDetailDtoExpressList = queryUnloadExpressTaskDetailByUnloadTaskNo(taskNo);
			} catch (Exception e) {
				throw new TfrBusinessException("ECS-->系统异常" + e.getMessage());
			}
		}

		if (CollectionUtils.isNotEmpty(unloadWaybillDetailDtoExpressList)) {
			// 添加到LIST里， 给导出用
			unloadWayBillDetails.addAll(unloadWaybillDetailDtoExpressList);
		}
		
		for(UnloadWaybillDetailDto unloadWayBillDetail : unloadWayBillDetails){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//交接/配载单号
			columnList.add(unloadWayBillDetail.getBillNo());

			//出发部门
			columnList.add(unloadWayBillDetail.getOrigOrgName());
			
			//到达部门
			columnList.add(unloadWayBillDetail.getDestOrgName());
			
			//运单号
			columnList.add(unloadWayBillDetail.getWaybillNo());
			
			//流水号
			columnList.add(unloadWayBillDetail.getSerialNo());

			//扫描时间
			Date optTime = unloadWayBillDetail.getOptTime();
			String optTimeStr = "";
			if(optTime != null) {
				optTimeStr = DateUtils.convert(optTime);
			}
			columnList.add(optTimeStr);
			
			//运输性质
			columnList.add(unloadWayBillDetail.getTransportType());
			
			//交接件数
			Integer handoverTotQty = unloadWayBillDetail.getHandoverTotQty() == null ? 0 : unloadWayBillDetail.getHandoverTotQty();
			columnList.add(handoverTotQty + "");
			
			//已操作件数
			Integer operationGoodsQty = unloadWayBillDetail.getOperationGoodsQty() == null ? 0 : unloadWayBillDetail.getOperationGoodsQty();
			columnList.add(operationGoodsQty + "");
			
			//扫描件数
			Integer scanGoodsQty = unloadWayBillDetail.getScanGoodsQty() == null ? 0 : unloadWayBillDetail.getScanGoodsQty();
			columnList.add(scanGoodsQty + "");
			
			//件数扫描率
			//件数扫描率
			BigDecimal scanGoodsQtyRate = BigDecimal.ZERO;
			String billNo = unloadWayBillDetail.getBillNo();
			if(StringUtils.equals(billNo, "多货") || StringUtils.isEmpty(billNo)) {
				//当多货时, 件数扫描率计算方式改为 (扫描件数/已操作件数)
				if(operationGoodsQty > 0) {
					scanGoodsQtyRate = new BigDecimal(scanGoodsQty).divide(new BigDecimal(operationGoodsQty),ConstantsNumberSonar.SONAR_NUMBER_4,BigDecimal.ROUND_HALF_UP);
				}
			}else{
				//正常时，扫描率计算方式
				scanGoodsQtyRate = unloadWayBillDetail.getScanGoodsQtyRate() == null ? BigDecimal.ZERO : unloadWayBillDetail.getScanGoodsQtyRate();
				
			}
			scanGoodsQtyRate = scanGoodsQtyRate.multiply(BigDecimal.valueOf(ConstantsNumberSonar.SONAR_NUMBER_100));
			DecimalFormat df = new DecimalFormat("0.000");
			columnList.add(df.format(scanGoodsQtyRate) + "%");
			
			
			//交接重量(公斤)
			BigDecimal unloadWeightTotal = unloadWayBillDetail.getUnloadWeightTotal() == null ? BigDecimal.ZERO : unloadWayBillDetail.getUnloadWeightTotal();
			columnList.add(unloadWeightTotal + "");
			
			//交接体积(方)
			BigDecimal unloadVolumeTotal = unloadWayBillDetail.getUnloadVolumeTotal() == null ? BigDecimal.ZERO : unloadWayBillDetail.getUnloadVolumeTotal();
			columnList.add(unloadVolumeTotal + "");
			
			//货名
			columnList.add(unloadWayBillDetail.getGoodsName());
			
			//包装
			columnList.add(unloadWayBillDetail.getPack());
			rowList.add(columnList);
		}
		String[] rowHeads = {"交接/配载单","出发部门","到达部门","运单号", "流水号", "扫描时间", "运输性质","交接件数","已操作件数","扫描件数",
				"件数扫描率","交接重量(公斤)","交接体积(方)","货名","包装"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("卸车明细");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	

	/** 
	 * @Title: exportUnloadTask 
	 * @Description: 根据查询条件导出卸车任务 
	 * @param unloadTaskDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午11:04:07
	 */ 
	@Override
	public InputStream exportUnloadTask(UnloadTaskDto unloadTaskDto) {
		InputStream excelStream = null;
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前部门顶级组织
		OrgAdministrativeInfoEntity administrativeInfo = querySuperiorOrgByOrgCode(currentDeptCode);
		//当前部门顶级组织code
		String orgCode = administrativeInfo.getCode();
		unloadTaskDto.setUnloadOrgCode(orgCode);
		List<UnloadTaskDto> unloadTasks = unloadTaskQueryDao.queryUnloadTask(unloadTaskDto);
		
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(UnloadTaskDto unloadTask : unloadTasks){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//卸车任务编号
			columnList.add(unloadTask.getUnloadTaskNo());

			//差异报告编号
			columnList.add(unloadTask.getGaprepNo());
			
			//卸车方式
			columnList.add(getUnloadWayMap().get(unloadTask.getUnloadWay()));
			
			//车牌号
			columnList.add(unloadTask.getVehicleNo());
			//272681 导出商务专递的路线
			if(unloadTask.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				String line = unloadTaskQueryDao.queryBusinessLine(unloadTask.getUnloadTaskNo());
				columnList.add(line);
			}else{
			//线路
			columnList.add(unloadTask.getLine());
			}
			//创建人
			columnList.add(unloadTask.getLoaderName());

			//月台号
			columnList.add(unloadTask.getPlatformNo());

			//卸车类型
			columnList.add(getUnloadTypeMap().get(unloadTask.getUnloadType()));

			//卸车状态
			columnList.add(getTaskStateMap().get(unloadTask.getTaskState()));

			//车辆到达时间
			columnList.add(DateUtils.convert(unloadTask.getArriveTime()));

			//任务建立时间
			columnList.add(DateUtils.convert(unloadTask.getUnloadStartTime()));

			//任务完成时间
			columnList.add(DateUtils.convert(unloadTask.getUnloadEndTime()));
			
			rowList.add(columnList);
		}
		String[] rowHeads = {"卸车任务编号","差异报告编号","卸车方式","车牌号","线路","创建人","月台号","卸车类型",
				"卸车状态","车辆到达时间","任务建立时间","任务完成时间"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("卸车任务");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	
	//卸车方式map
	private Map<String, String> unloadWayMap;
	private Map<String, String> getUnloadWayMap() {
		if(unloadWayMap == null){
			unloadWayMap = new HashMap<String, String>();
			unloadWayMap.put("PDA", "有PDA");
			unloadWayMap.put("NO_PDA", "无PDA");
		}
		return unloadWayMap;
	}

	//卸车类型map
	private Map<String, String> unloadTypeMap;
	private Map<String, String> getUnloadTypeMap() {
		if(unloadTypeMap == null){
			unloadTypeMap = new HashMap<String, String>();
			unloadTypeMap.put("LONG_DISTANCE", "长途卸车");
			unloadTypeMap.put("SHORT_DISTANCE", "短途卸车");
			unloadTypeMap.put("DELIVER", "集中卸车");
			unloadTypeMap.put("EXPRESS_PICK", "快递集中卸车");
			unloadTypeMap.put("BUSINESS_AIR", "商务专递卸车");// 导出卸车类型商务专递 272681
		}
		return unloadTypeMap;
	}

	//卸车状态map
	private Map<String, String> taskStateMap;
	private Map<String, String> getTaskStateMap() {
		if(taskStateMap == null){
			taskStateMap = new HashMap<String, String>();
			taskStateMap.put("UNLOADING", "进行中");
			taskStateMap.put("FINISHED", "已完成");
			taskStateMap.put("CANCELED", "已取消");
		}
		return taskStateMap;
	}

	/**   
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 * Date:2013-4-28下午5:02:27
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * 根据商务专递交接单号获取路线
	 * @author 272681 chenlei
	 * @date 2015/9/10
	 */
	public String queryBusinessLine(String unloadTaskNo){
		return unloadTaskQueryDao.queryBusinessLine(unloadTaskNo);
		
	}
}