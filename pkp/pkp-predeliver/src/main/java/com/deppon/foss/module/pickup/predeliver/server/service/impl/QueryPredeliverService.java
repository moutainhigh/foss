package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryPredeliverDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryPredeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchRequest;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendQueryingResult;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendingWayBillInfoResponse;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.PropertiesUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 派送信息查询Service
 * @author zyr
 * @date 2015-05-22 下午2:34:18
 * @since
 * @version
 */
public class QueryPredeliverService implements IQueryPredeliverService{
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(QueryPredeliverService.class.getName());
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
	private IQueryPredeliverDao queryPredeliverDao;
	
	private ISaleDepartmentService saleDepartmentService;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setQueryPredeliverDao(IQueryPredeliverDao queryPredeliverDao) {
		this.queryPredeliverDao = queryPredeliverDao;
	}
	/** 
	 * 按照查询条件查询派送信息
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public List<SendHandoverInfoDto> queryPredeliver(SendInfoSearchDto sendInfoSearchDto,int start, int limit) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				List<SendHandoverInfoDto> sendHandoverInfoDtoList = queryPredeliverDao.queryPredeliver(sendInfoSearchDto,start,limit);
				return sendHandoverInfoDtoList;
			}
		}
		
		return null;
	}
	/** 
	 * 查询派送信息总条数，用于分页.
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public Long getPredeliverInfoCount(SendInfoSearchDto sendInfoSearchDto) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				return queryPredeliverDao.getPredeliverInfoCount(sendInfoSearchDto);
			}
		}
		return null;
	}
	/** 
	 * 按照查询条件查询派送信息
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public List<SendHandoverInfoDto> queryPredeliverWaybill(SendInfoSearchDto sendInfoSearchDto,int start, int limit) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				List<SendHandoverInfoDto> sendHandoverInfoDtoList = queryPredeliverDao.queryPredeliverWaybill(sendInfoSearchDto,start,limit);
				return sendHandoverInfoDtoList;
			}
		}
		
		return null;
	}
	/** 
	 * 查询派送信息总条数，用于分页.
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public Long getPredeliverInfoCountWaybill(SendInfoSearchDto sendInfoSearchDto) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				return queryPredeliverDao.getPredeliverInfoCountWaybill(sendInfoSearchDto);
			}
		}
		return null;
	}
	/**
	 * 查询总派送信息(交接单).
	 * @author zyr
	 * @date 2015-05-28 下午3:31:16
	 */
	@Override
	public SendInfoSearchDto queryPredeliverTotal(SendInfoSearchDto sendInfoSearchDto,List<SendHandoverInfoDto> sendHandoverInfoDtoList) {
		//如果传入dto不为空
		if(sendInfoSearchDto != null){
			
			//默认值设为0
			Integer deliverQtyTotal = NumberConstants.ZERO;
			//默认值设为0
			BigDecimal deliverWeightTotal = BigDecimal.ZERO;
			//默认值设为0
			BigDecimal deliverVolumeTotal = BigDecimal.ZERO;
			//查询货量返回Dto
			
			//循环货量List
			for (SendHandoverInfoDto sendHandoverInfoDto : sendHandoverInfoDtoList) {	
				
				deliverQtyTotal = deliverQtyTotal+(sendHandoverInfoDto.getDeliverQty());
				deliverWeightTotal = deliverWeightTotal.add(sendHandoverInfoDto.getDeliverWeight());
				deliverVolumeTotal = deliverVolumeTotal.add(sendHandoverInfoDto.getDeliverVolume());
			}
			//设置总票数
			sendInfoSearchDto.setDeliverQtyTotal(deliverQtyTotal);
			//设置总重量
			sendInfoSearchDto.setDeliverWeightTotal(deliverWeightTotal);
			//设置总体积
			sendInfoSearchDto.setDeliverVolumeTotal(deliverVolumeTotal);
			return sendInfoSearchDto;
		}
		//返回空
		return null;
	}
	
	/**
	 * 查询总派送信息(运单).
	 * @author zyr
	 * @date 2015-05-28 下午3:31:16
	 */
	@Override
	public SendInfoSearchDto queryPredeliverWaybillTotal(SendInfoSearchDto sendInfoSearchDto,List<SendHandoverInfoDto> sendHandoverInfoDtoList) {
		//如果传入dto不为空
		if(sendInfoSearchDto != null){
			
			//默认值设为0
			//Integer deliverQtyTotal = NumberConstants.ZERO;
			//默认值设为0
			BigDecimal deliverWeightTotal = BigDecimal.ZERO;
			//默认值设为0
			BigDecimal deliverVolumeTotal = BigDecimal.ZERO;
			
			//循环货量List
			for (SendHandoverInfoDto sendHandoverInfoDto : sendHandoverInfoDtoList) {	
				
				//deliverQtyTotal = deliverQtyTotal+sendHandoverInfoDtoList.size();
				deliverWeightTotal = deliverWeightTotal.add(sendHandoverInfoDto.getDeliverWeight());
				deliverVolumeTotal = deliverVolumeTotal.add(sendHandoverInfoDto.getDeliverVolume());
			}
			//设置总票数
			sendInfoSearchDto.setDeliverQtyTotal(sendHandoverInfoDtoList.size());
			//设置总重量
			sendInfoSearchDto.setDeliverWeightTotal(deliverWeightTotal);
			//设置总体积
			sendInfoSearchDto.setDeliverVolumeTotal(deliverVolumeTotal);
			return sendInfoSearchDto;
		}
		//返回空
		return null;
	}
	
	/** 
	 * 按照查询条件导出派送信息(交接单)
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public InputStream queryPredeliverInfo(SendInfoSearchDto sendInfoSearchDto) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				List<SendHandoverInfoDto> sendHandoverInfoDtoList = queryPredeliverDao.queryPredeliverInfo(sendInfoSearchDto);
				List<List<String>> rowList = new ArrayList<List<String>>();
				for (SendHandoverInfoDto sendHandoverInfoDto : sendHandoverInfoDtoList) {
					List<String> columnList = new ArrayList<String>();
					//车牌号
					columnList.add(sendHandoverInfoDto.getVehicleNo());
					//配载车次号
					columnList.add(sendHandoverInfoDto.getVehicleassembleNo());
					//交接单号
					columnList.add(sendHandoverInfoDto.getHandoverNo());
					//派送（票数）
					columnList.add(sendHandoverInfoDto.getDeliverQty().toString());
					//派送（重量）
					columnList.add(sendHandoverInfoDto.getDeliverWeight().toString());
					//派送（体积）
					columnList.add(sendHandoverInfoDto.getDeliverVolume().toString());
					//出发部门
					columnList.add(sendHandoverInfoDto.getOrigOrgName());
					//到达部门
					columnList.add(sendHandoverInfoDto.getDestOrgName());
					//出发时间 
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getLeaveTime(), DateUtils.DATE_TIME_FORMAT));
					//到达时间
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
					//预计到达时间
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getPreArriveTime(), DateUtils.DATE_TIME_FORMAT));
					
					rowList.add(columnList);
				}
				String[] rowHeads = {"车牌号","配载车次号","交接单号","派送（票数）","派送（重量）","派送（体积）","出发部门","到达部门","出发时间 ","到达时间","预计到达时间"};
				
			    ExportResource exportResource = new ExportResource();
			    exportResource.setHeads(rowHeads);
			    exportResource.setRowList(rowList);
			    ExportSetting exportSetting = new ExportSetting();
			    exportSetting.setSheetName("车次|交接单");
			    exportSetting.setSize(NUMBER);
			    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		        return objExporterExecutor.exportSync(exportResource, exportSetting);
			}
		}
		
		return null;
	}
	
	/** 
	 * 按照查询条件导出派送信息(运单)
	 * @author zyr
	 * @date 2015-05-22 下午2:44:18
	 */
	@Override
	public InputStream queryPredeliverWaybillInfo(SendInfoSearchDto sendInfoSearchDto) {
		if (sendInfoSearchDto != null) {
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			if(StringUtils.isNotEmpty(sendInfoSearchDto.getDepartmentCode())&&StringUtils.isNotEmpty(sendInfoSearchDto.getTransferCenterCode())){
				List<SendHandoverInfoDto> sendHandoverInfoDtoList = queryPredeliverDao.queryPredeliverWaybillInfo(sendInfoSearchDto);
				List<List<String>> rowList = new ArrayList<List<String>>();
				for (SendHandoverInfoDto sendHandoverInfoDto : sendHandoverInfoDtoList) {
					List<String> columnList = new ArrayList<String>();
					//运单号
					columnList.add(sendHandoverInfoDto.getWaybillNo());
					//配载车次号
					columnList.add(sendHandoverInfoDto.getVehicleassembleNo());
					//交接单号
					columnList.add(sendHandoverInfoDto.getHandoverNo());
					//开单件数
					columnList.add(sendHandoverInfoDto.getGoodsQty().toString());
					//配载件数
					columnList.add(sendHandoverInfoDto.getAssembleQty().toString());
					//派送（重量）
					columnList.add(sendHandoverInfoDto.getDeliverWeight().toString());
					//派送（体积）
					columnList.add(sendHandoverInfoDto.getDeliverVolume().toString());
					//运输性质
					columnList.add(sendHandoverInfoDto.getProductCode());
					String receiveMethod = null;
					if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "DELIVER")) {
						receiveMethod = "免费送货";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "DELIVER_INGA")) {
						receiveMethod = "送货进仓";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "DELIVER_NOUP")) {
						receiveMethod = "送货(不含上楼)";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "DELIVER_UP")) {
						receiveMethod = "送货上楼";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "INNER_PICKUP")) {
						receiveMethod = "内部带货自提";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "LARGE_DELIVER_UP")) {
						receiveMethod = "大件上楼";
					}else if(StringUtils.equals(sendHandoverInfoDto.getReceiveMethod(), "SELF_PICKUP")) {
						receiveMethod = "自提";
					}else {
						receiveMethod = sendHandoverInfoDto.getProductCode();
					}
					//提货方式
					columnList.add(receiveMethod);
					//行政区
					columnList.add(sendHandoverInfoDto.getDistName());
					//出发时间 
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getLeaveTime(), DateUtils.DATE_TIME_FORMAT));
					//到达时间
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
					//预计到达时间
					columnList.add(DateUtils.convert(sendHandoverInfoDto.getPreArriveTime(), DateUtils.DATE_TIME_FORMAT));
					
					rowList.add(columnList);
				}
				String[] rowHeads = {"运单号","配载车次号","交接单号","开单件数","配载件数","派送（重量）","派送（体积）","运输性质","提货方式","行政区","出发时间 ","到达时间","预计到达时间"};
				
			    ExportResource exportResource = new ExportResource();
			    exportResource.setHeads(rowHeads);
			    exportResource.setRowList(rowList);
			    ExportSetting exportSetting = new ExportSetting();
			    exportSetting.setSheetName("运单");
			    exportSetting.setSize(NUMBER);
			    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		        return objExporterExecutor.exportSync(exportResource, exportSetting);
			}
		}
		
		return null;
	}
	
	private SendInfoSearchDto getTransferCenter(SendInfoSearchDto sendInfoSearchDto) {
		//获取当前部门
		String currOrgCode = FossUserContextHelper.getOrgCode();
		//通过OrgCode部门编号获取部门实体，判定此部门性质是否为派送部或者车队
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currOrgCode);
		if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
			
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(currOrgCode);
//			判断此营业部是否驻地部门并且是否有派送职能
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) && StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getDelivery())){
				sendInfoSearchDto.setTransferCenterCode(saleDepartmentEntity.getTransferCenter());
				
//				List<String> bizTypes = new ArrayList<String>();
//				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
//				//找到对应的上级为外场
//				OrgAdministrativeInfoEntity parentOrg = 
//						orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currOrgCode, bizTypes);
//				if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
//					throw new TfrBusinessException("未找到此部门：" + currOrgCode + " 所对应的的上级 外场");
//				}else{
//					sendInfoSearchDto.setTransferCenterCode(parentOrg.getCode());
//				}
				sendInfoSearchDto.setDepartmentCode(currOrgCode);
			}
			return sendInfoSearchDto;
		}else if(StringUtils.equals(FossConstants.YES, org.getTransDepartment()) 
				|| StringUtils.equals(FossConstants.YES, org.getDispatchTeam()) 
				|| StringUtils.equals(FossConstants.YES, org.getTransTeam())){
			//找顶级车队
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currOrgCode);
			if(null == topFleet || StringUtils.isEmpty(topFleet.getCode())){
				throw new TfrBusinessException("未找到此部门：" + currOrgCode + " 所对应的的顶级车队");
			}else{
				List<String> bizType = new ArrayList<String>();
				bizType.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				//找对应的外场
				OrgAdministrativeInfoEntity ransferCenter = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(topFleet.getCode(), bizType);
				if(null == ransferCenter || StringUtils.isEmpty(ransferCenter.getCode())){
					throw new TfrBusinessException("未找到此部门：" + currOrgCode + " 所对应的的上级 外场");
				}else{
					sendInfoSearchDto.setTransferCenterCode(ransferCenter.getCode());
					//根据外场组织CODE，查询该外场的驻地派送部门对象
					SaleDepartmentEntity saleDepartment = orgAdministrativeInfoComplexService.queryStationDeliverOrgOneByOutfieldCode(ransferCenter.getCode());
					if(null == saleDepartment || StringUtils.isEmpty(saleDepartment.getCode())){
						throw new TfrBusinessException("未找到此部门：" + currOrgCode + " 所对应的驻地派送部门");
					}else{
						sendInfoSearchDto.setDepartmentCode(saleDepartment.getCode());
					}
				}
			}
			return sendInfoSearchDto;
		}else {
			return sendInfoSearchDto;
		}
	}
	@Override
	public List<SendingWayBillInfoResponse> queryPredeliverExpressWaybill(
			SendInfoSearchDto sendInfoSearchDto) throws BusinessException{
		LOGGER.info("----FOSS派送信息查询快递运单 start");
		try {
			if (null==sendInfoSearchDto) {
				throw new BusinessException("查询条件为空!");
			}
			sendInfoSearchDto = this.getTransferCenter(sendInfoSearchDto);
			String	departmentCode=sendInfoSearchDto.getDepartmentCode();
			String transferCenterCode=sendInfoSearchDto.getTransferCenterCode();
			LOGGER.info("----FOSS派送信息查询快递运单 当前部门:"+departmentCode+";当前外场:"+transferCenterCode);
			if(StringUtils.isNotEmpty(departmentCode)&&StringUtils.isNotEmpty(transferCenterCode)){
				SendInfoSearchRequest searchRequest=new SendInfoSearchRequest();
				SimpleDateFormat dateFormat=new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
				// 交接单号
				searchRequest.setHandoverBillNo(sendInfoSearchDto.getHandoverNo());
				//车牌号
				searchRequest.setVehicleNo(sendInfoSearchDto.getVehicleNo());
				//出发开始时间
				Date leaveTimeBegin= sendInfoSearchDto.getLeaveTimeBegin();
				searchRequest.setStartDepartureTime(null!=leaveTimeBegin?dateFormat.format(leaveTimeBegin):"");
				//出发结束时间
				Date leaveTimeEnd= sendInfoSearchDto.getLeaveTimeEnd();
				searchRequest.setEndDepartureTime(null!=leaveTimeEnd?dateFormat.format(leaveTimeEnd):"");
				//预计到达开始时间
				Date preArriveTimeBegin =sendInfoSearchDto.getPreArriveTimeBegin();
				searchRequest.setStartArrivalTime(null!=preArriveTimeBegin?dateFormat.format(preArriveTimeBegin):"");
				//预计到达结束时间
				Date preArriveTimeEnd =sendInfoSearchDto.getPreArriveTimeEnd();
				searchRequest.setEndArrivalTime(null!=preArriveTimeEnd?dateFormat.format(preArriveTimeEnd):"");
				//出发部门编码 departOrgCode
				searchRequest.setDepartOrgCode(sendInfoSearchDto.getDepartDeptCode());
				//提货网点
				searchRequest.setPkpOrgCode(departmentCode);
				//目的外场
				searchRequest.setArrivalOrgCode(transferCenterCode);
				HttpClient httpClient = new HttpClient();
				httpClient.getParams().setContentCharset("UTF-8");
				String url = PropertiesUtil.getKeyValue("ecs.predeliver.queryPredeliverExpressWaybill");
				//String url ="http://10.224.102.85:8080/tfr-opt-service/v1/ecs/tfr/querySendingWayBillInfoToFoss";
				LOGGER.info("----FOSS派送信息查询快递运单，访问ESB接口地址：" + url);
				PostMethod  postMethod=new PostMethod(url);
				List<SendingWayBillInfoResponse> responseDto = new ArrayList<SendingWayBillInfoResponse>();
				String requestStr = null;//请求参数
				String responseStr = null;//返回参数
				try {
					requestStr = JSONObject.toJSONString(searchRequest);
					RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
					postMethod.setRequestEntity(requestEntity);
					httpClient.executeMethod(postMethod);
					responseStr = postMethod.getResponseBodyAsString();
					SendQueryingResult sendQueryingResult=JSONObject.parseObject(responseStr, SendQueryingResult.class);
					responseDto =sendQueryingResult.getData() ;
					if (0==sendQueryingResult.getStatus()) {
						LOGGER.error("悟空系统返回失败!!失败信息:" + sendQueryingResult.getExMsg());
						throw new BusinessException("悟空系统返回失败!!失败信息:"+sendQueryingResult.getExMsg());
					}
					LOGGER.info("----FOSS派送信息查询快递运单 end");
					return responseDto;
				} catch (Exception e) {
					LOGGER.error("----FOSS派送信息查询快递运单，传入参数：" + requestStr);
					LOGGER.error("----FOSS派送信息查询快递运单，返回参数：" + responseStr);
					throw new BusinessException("调用悟空系统返回失败!!", e);
				}
			}else{
				throw new BusinessException("当前部门或当前外场为空!!");
			}
		} catch (Exception e) {
			LOGGER.error("----FOSS派送信息查询快递运单 error：" + e.getMessage());
			throw new BusinessException(e.getMessage(),e);
		}
	}
	/**
	 * 查询总派送信息(运单).
	 * @author zyr
	 * @date 2015-05-28 下午3:31:16
	 */
	@Override
	public SendInfoSearchDto queryPredeliverWaybillExpressTotal(SendInfoSearchDto sendInfoSearchDto,List<SendingWayBillInfoResponse> wayBillInfoResponses){
		//如果传入dto不为空
		if(sendInfoSearchDto != null){
			
			//默认值设为0
			//Integer deliverQtyTotal = NumberConstants.ZERO;
			//默认值设为0
			BigDecimal weightTotal = BigDecimal.ZERO;
			//默认值设为0
			BigDecimal volumeTotal = BigDecimal.ZERO;
			//循环货量List
			for (SendingWayBillInfoResponse sendingWayBillInfoResponse : wayBillInfoResponses) {	
				BigDecimal weight=sendingWayBillInfoResponse.getWeight();
				weightTotal = weightTotal.add(null==weight?BigDecimal.ZERO:weight);
				BigDecimal volume=sendingWayBillInfoResponse.getVolume();
				volumeTotal = volumeTotal.add(null==volume?BigDecimal.ZERO:volume);
			}
			//设置总票数
			sendInfoSearchDto.setDeliverQtyTotal(wayBillInfoResponses.size());
			//设置总重量
			sendInfoSearchDto.setDeliverWeightTotal(weightTotal.setScale(1,BigDecimal.ROUND_HALF_UP));
			//设置总体积
			sendInfoSearchDto.setDeliverVolumeTotal(volumeTotal.setScale(3,BigDecimal.ROUND_HALF_UP));
			return sendInfoSearchDto;
		}
		//返回空
		return null;
	}
	/** 
	 * 按照查询条件导出派送信息(快递运单)
	 * @author zm
	 * @date 2016年12月10日14:52:33
	 */
	public InputStream queryExpressWaybillInfo(SendInfoSearchDto sendInfoSearchDto) {
		List<SendingWayBillInfoResponse> billInfoResponses = queryPredeliverExpressWaybill(sendInfoSearchDto);
		if (null==billInfoResponses || billInfoResponses.size()<1) {
			return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (SendingWayBillInfoResponse billInfoResponse : billInfoResponses) {
			List<String> columnList = new ArrayList<String>();
			//运单号
			columnList.add(billInfoResponse.getWaybillNo());
			//交接单号
			columnList.add(billInfoResponse.getHandoverBillNo());
			//开单时间
			columnList.add(DateUtils.convert(billInfoResponse.getBilling(), DateUtils.DATE_TIME_FORMAT));
			//开单件数
			columnList.add(String.valueOf(billInfoResponse.getWaybillQty()));
			//运单（重量）
			BigDecimal weight= billInfoResponse.getWeight();
			columnList.add(null!=weight?String.valueOf(weight.setScale(1,BigDecimal.ROUND_HALF_UP)):"");
			//运单（体积）
			BigDecimal volume= billInfoResponse.getVolume();
			columnList.add(null!=volume?String.valueOf(volume.setScale(3,BigDecimal.ROUND_HALF_UP)):"");
			//产品类型
			columnList.add(billInfoResponse.getTransportType());
			//行政区
			columnList.add(billInfoResponse.getArriveDistName());
			//出发时间 
			columnList.add(DateUtils.convert(billInfoResponse.getDepartTime(), DateUtils.DATE_TIME_FORMAT));
			//预计到达时间
			columnList.add(DateUtils.convert(billInfoResponse.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
			
			rowList.add(columnList);
		}
		String[] rowHeads = {"运单号","交接单号","开单时间","开单件数","运单（重量）","运单（体积）","产品类型","行政区","出发时间 ","预计到达时间"};
		
	    ExportResource exportResource = new ExportResource();
	    exportResource.setHeads(rowHeads);
	    exportResource.setRowList(rowList);
	    ExportSetting exportSetting = new ExportSetting();
	    exportSetting.setSheetName("运单");
	    exportSetting.setSize(NUMBER);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
        return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
}
