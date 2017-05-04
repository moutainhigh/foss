/**   
 * @title AccountServiceImpl.java
 * @package com.deppon.foss.service 
 * @author 026123-foss-lifengteng
 * @version 0.1 2012-12-25
 */
package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.accounting.AppWayBillDetailInfo;
import com.deppon.esb.inteface.domain.accounting.ApplyChangeOrderRequest;
import com.deppon.esb.inteface.domain.accounting.ApplyChangeOrderResponse;
import com.deppon.esb.inteface.domain.accounting.CheckWaybillNo;
import com.deppon.esb.inteface.domain.accounting.EWayBillDetailInfo;
import com.deppon.esb.inteface.domain.accounting.FreightRouteParamDto;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailRequest;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailResponse;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementRequest;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementResponse;
import com.deppon.esb.inteface.domain.accounting.GetRefundRequest;
import com.deppon.esb.inteface.domain.accounting.GetRefundResponse;
import com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.LockReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.LockReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountRequest;
import com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountResponse;
import com.deppon.esb.inteface.domain.accounting.PrintLabelBean;
import com.deppon.esb.inteface.domain.accounting.QueryAppWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryAppWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.QueryEWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryEWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.QueryExistWaybillNoInfoRequest;
import com.deppon.esb.inteface.domain.accounting.QueryExistWaybillNoInfoResponse;
import com.deppon.esb.inteface.domain.accounting.QueryFreightRouteInfoRequest;
import com.deppon.esb.inteface.domain.accounting.QueryFreightRouteInfoResponse;
import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountRequest;
import com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountResponse;
import com.deppon.esb.inteface.domain.accounting.WayBillDetailInfo;
import com.deppon.foss.accountservice.AccountService;
import com.deppon.foss.accountservice.CommonException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteCommonsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteParamCommonsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**   
 * <p>
 * 官网订单明细查询接口实现类<br />
 * </p>
 * @title AccountServiceImpl.java
 * @package com.deppon.foss.service 
 * @author suyujun
 * @version 0.1 2012-12-25
 */
public class AccountServiceImpl implements AccountService {
	private static Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);
	/**
	 * waybillManagerService
	 */
	private IWaybillQueryService waybillQueryService;
	
	/**
	 * waybillRfcService
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * labelPrintInfoService
	 */
	private ILabelPrintInfoService labelPrintInfoService;
	
	/**
	 * WaybillManagerService
	 */
	private IWaybillManagerService waybillManagerService;
	
	private IWaybillPendingService waybillPendingService;
	
	private IFreightRouteService freightRouteService;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IAdministrativeRegionsService administrativeRegionsService;
	
	private IEWaybillService ewaybillService;
	
	private ISaleDepartmentService saleDepartmentService;
	
	
	
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	public void setEwaybillService(IEWaybillService ewaybillService) {
		this.ewaybillService = ewaybillService;
	}
	/**
	 * WaybillPendingService
	 * @return
	 */
	public IWaybillPendingService getWaybillPendingService() {
		return waybillPendingService;
	}
	/**
	 * WaybillPendingService
	 * @param waybillPendingService
	 */
	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	/**
	 * WaybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	
	/**
	 * WaybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * labelPrintInfoService
	 */
	public ILabelPrintInfoService getLabelPrintInfoService() {
		return labelPrintInfoService;
	}
	/**
	 * labelPrintInfoService
	 */
	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}

	/**
	 * @return waybillQueryService : set the property waybillQueryService.
	 */
	public IWaybillQueryService getWaybillQueryService() {
		return waybillQueryService;
	}

	/**
	 * @param waybillQueryService : return the property waybillQueryService.
	 */
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#getAccountDetailInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailRequest)
	 */
	@Override
	public GetAccountStatementDetailResponse getAccountDetailInfo(
			Holder<ESBHeader> esbHeader,
			GetAccountStatementDetailRequest payload) throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#getRefundInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.GetRefundRequest)
	 */
	@Override
	public GetRefundResponse getRefundInfo(Holder<ESBHeader> esbHeader,
			GetRefundRequest payload) throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#lockReceivableBill(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.LockReceivableBillRequest)
	 */
	@Override
	public LockReceivableBillResponse lockReceivableBill(
			Holder<ESBHeader> esbHeader, LockReceivableBillRequest payload)
			throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#getAccountInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.GetAccountStatementRequest)
	 */
	@Override
	public GetAccountStatementResponse getAccountInfo(
			Holder<ESBHeader> esbHeader, GetAccountStatementRequest payload)
			throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#repaymentByAccountInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountRequest)
	 */
	@Override
	public RepaymentByStatementOfAccountResponse repaymentByAccountInfo(
			Holder<ESBHeader> esbHeader,
			RepaymentByStatementOfAccountRequest payload)
			throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#repaymentByReceivableBill(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillRequest)
	 */
	@Override
	public RepaymentByReceivableBillResponse repaymentByReceivableBill(
			Holder<ESBHeader> esbHeader,
			RepaymentByReceivableBillRequest payload) throws CommonException {
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#lockAccountInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountRequest)
	 */
	@Override
	public LockStatementOfAccountResponse lockAccountInfo(
			Holder<ESBHeader> esbHeader, LockStatementOfAccountRequest payload)
			throws CommonException {
		return null;
	}
	
	 /*
	  * 电子运单接口
	  * @创建时间 2014-9-2 下午8:34:33   
	  * @创建人： 136334 - BaiLei
	  * 
	  */
	public QueryEWaybillInfosResponse queryEWaybillInfos(Holder<ESBHeader> esbHeader, 
			QueryEWaybillInfosRequest request) throws CommonException{
		int count = 0;
		List<EWayBillDetailInfo> resultList = new ArrayList<EWayBillDetailInfo>();
		
		esbHeader.value.setRequestId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		QueryEWaybillInfosResponse response = new QueryEWaybillInfosResponse();
		if(request == null ){
			response.setCount(count);
			response.getEWayBillList().addAll(resultList);
		}else{
			//查询条件封装
			Map<String,Object> args = convertToQueryEWaybillInfosCondition(request);
			//校验查询条件
			validateEWaybillInfosCondition(args);
			if(WaybillConstants.NO.equals(args.get("activeStatus"))){
				//查询待补录结果
				Map<String,Object> resultMap = waybillPendingService.queryUnactiveEWaybillInfos(args);
				//将走货路径和补录表信息转化转化为目标类型
				@SuppressWarnings("unchecked")
				List<EWayBillDetailInfo> result = convertToUnactiveEWaybillTargetType((List<WaybillPendingEntity>) resultMap.get("list"));
				
				count = (Integer) resultMap.get("count");
				response.setCount(count);
				if(result!=null){
					response.getEWayBillList().addAll(result);
				}
			}else if(WaybillConstants.YES.equals(args.get("activeStatus"))){
				//查询正式运单结果
				Map<String,Object> resultMap = waybillQueryService.queryActiveEWaybillInfos(args);
				
				@SuppressWarnings("unchecked")
				List<EWayBillDetailInfo> result = convertToActiveEWaybillTargetType((List<WaybillEntity>) resultMap.get("list"));
				
				count = (Integer) resultMap.get("count");
				response.setCount(count);
				if(result!=null){
					response.getEWayBillList().addAll(result);
				}
			}
		}
		return response;
	}
	
	/*
	 * 将请求转化为查询条件
	 * @创建时间 2014-9-2 下午8:34:33   
	 * @创建人： 136334 - BaiLei
	 */
	private Map<String,Object>  convertToQueryEWaybillInfosCondition(QueryEWaybillInfosRequest request){
		Map<String,Object> argsMap = new HashMap<String, Object>();
		
		argsMap.put("userName", request.getName());
		argsMap.put("linkmanId", request.getLinkmanId());
		argsMap.put("waybillNO", request.getWaybillNO());
		argsMap.put("orderNo", request.getOrderNO());
		argsMap.put("receiveCustomerName",request.getReceiveCustomerName());
		argsMap.put("deliveryCustomerName",request.getDeliveryCustomerName());
		argsMap.put("goodsName",request.getGoodsName());
		argsMap.put("startDate",DateUtils.converToJavaDate(request.getStartDate()));
		argsMap.put("endDate",DateUtils.converToJavaDate(request.getEndDate()));
		argsMap.put("activeStatus",request.getActiveStatus());
		argsMap.put("pageNum", request.getPageNum());
		argsMap.put("pageSize", request.getPageSize());
		
		return argsMap;
	}
	 
	/*
	 * 校验查询条件
	 * @创建时间 2014-9-2 下午8:34:33   
	 * @创建人： 136334 - BaiLei
	 */
	private void validateEWaybillInfosCondition(Map<String,Object> argsMap) throws CommonException{
		if(argsMap!=null)
		{
			if(argsMap.get("waybillNO")!=null){
				
				String waybillNO=(String)argsMap.get("waybillNO");
				if(StringUtils.isEmpty(waybillNO))
				{
					if(argsMap.get("startDate")==null||argsMap.get("endDate")==null)
					{
						throw new CommonException("时间不能为空！");
					}else{
						
						Date startDate=(Date)argsMap.get("startDate");
						Date endDate=(Date)argsMap.get("endDate");
						
						Long diffDate= DateUtils.getTimeDiff(startDate, endDate);
						if(diffDate>180)
						{
							throw new CommonException("查询时间范围超过180天，请调整时间！");
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 将DTO类型转换为目标类型
	 * @创建时间 2014-9-3 下午8:34:33   
	 * @创建人：136334  BaiLei
	 */
	private List<EWayBillDetailInfo> convertToUnactiveEWaybillTargetType(List<WaybillPendingEntity> list){
		List<EWayBillDetailInfo> targetList = new ArrayList<EWayBillDetailInfo>();
		FreightRouteEntity freghtRoute = new FreightRouteEntity();
		AdministrativeRegionsEntity city = new AdministrativeRegionsEntity();
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		EWayBillDetailInfo info = null;
		if(list == null || list.size() == 0){
			return null;
		}
		for(WaybillPendingEntity entity : list){
			
			info = new EWayBillDetailInfo();
			//运单信息
			info.setWaybillNO(entity.getWaybillNo());
			info.setOrderNO(entity.getOrderNo());
			info.setBillWeight(entity.getBillWeight());
			info.setPackageFee(entity.getPackageFee());
			info.setInsuranceCharge(entity.getInsuranceFee());
			info.setRefundFee(entity.getCodFee());
			info.setRefundAcountCode(entity.getAccountCode());
			info.setTransportCharge(entity.getTransportFee());
			info.setTotalCharge(entity.getTotalFee());
			info.setBillTime(DateUtils.convertToXMLGregorianCalendar(entity.getBillTime()));
			//目的站名称
			org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean
					(entity.getCustomerPickupOrgCode());
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(entity.getCustomerPickupOrgCode());
			if(org!=null){
				info.setCustomerPickupOrgName(org.getName());
			}
			//接货网点
			if(saleDepartmentEntity!=null){
				info.setStationNumber(saleDepartmentEntity.getStationNumber());
			}
			//始发城市
			city = administrativeRegionsService.queryAdministrativeRegionsByCode(entity.getDeliveryCustomerCityCode());
			if(city !=null){
				String cityName = StringUtil.defaultIfNull(city.getSimpleName());
				info.setDepartCityName(cityName);//需确定有没有去除“市”
			}
			
			//第二外场以及最终外场
			/*freghtRoute = freightRouteService.queryFreightRouteBySourceTarget
					(entity.getReceiveOrgCode(),entity.getCustomerPickupOrgCode(),entity.getProductCode());
			if(freghtRoute != null){
				String secondLoadOrgCode = freghtRoute.getOrginalOrganizationCode();
				String lastOutLoadOrgCode = freghtRoute.getDestinationOrganizationCode();
				info.setSecondLoadOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(secondLoadOrgCode).getName());
				info.setLastOutLoadOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(lastOutLoadOrgCode).getName());
			}*/
			//设置出发外场和到达外场
			setSecondAndFinalField(entity.getCreateOrgCode(), entity.getCustomerPickupOrgCode(), entity.getProductCode(), info);
			
			//设置星标方法,对外提供用于判断该标签是否支持自动分拣
			info.setIsStarFlag(ewaybillService.getExpressIsStarFlag(entity));
			
			targetList.add(info);
		}
		return targetList;
	}
	
	private List<EWayBillDetailInfo> convertToActiveEWaybillTargetType(List<WaybillEntity> list){
		List<EWayBillDetailInfo> targetList = new ArrayList<EWayBillDetailInfo>();
		FreightRouteEntity freghtRoute = new FreightRouteEntity();
		AdministrativeRegionsEntity city = new AdministrativeRegionsEntity();
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		EWayBillDetailInfo info = null;
		if(list == null || list.size() == 0){
			return null;
		}
		for(WaybillEntity entity : list){
			
			info = new EWayBillDetailInfo();
			//运单信息
			info.setWaybillNO(entity.getWaybillNo());
			info.setOrderNO(entity.getOrderNo());
			info.setBillWeight(entity.getBillWeight());
			info.setPackageFee(entity.getPackageFee());
			info.setInsuranceCharge(entity.getInsuranceFee());
			info.setRefundFee(entity.getCodFee());
			info.setTransportCharge(entity.getTransportFee());
			info.setTotalCharge(entity.getTotalFee());
			info.setBillTime(DateUtils.convertToXMLGregorianCalendar(entity.getBillTime()));
			
			//目的站名称
			org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean
					(entity.getCustomerPickupOrgCode());
			if(org!=null){
				info.setCustomerPickupOrgName(org.getName());
			}
			
			//始发城市
			city = administrativeRegionsService.queryAdministrativeRegionsByCode(entity.getDeliveryCustomerCityCode());
			if(city !=null){
				String cityName = StringUtil.defaultIfNull(city.getSimpleName());
				info.setDepartCityName(cityName);//需确定有没有去除“市”
			}
			
			//第二外场以及最终外场
			/*freghtRoute = freightRouteService.queryFreightRouteBySourceTarget
					(entity.getReceiveOrgCode(),entity.getCustomerPickupOrgCode(),entity.getProductCode());
			if(freghtRoute != null){
				String secondLoadOrgCode = freghtRoute.getOrginalOrganizationCode();
				String lastOutLoadOrgCode = freghtRoute.getDestinationOrganizationCode();
				info.setSecondLoadOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(secondLoadOrgCode).getName());
				info.setLastOutLoadOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(lastOutLoadOrgCode).getName());
			}*/
			//设置出发外场和到达外场
			setSecondAndFinalField(entity.getCreateOrgCode(), entity.getCustomerPickupOrgCode(), entity.getProductCode(), info);
			String cityCode = entity.getReceiveCustomerCityCode();
			//始发城市
			AdministrativeRegionsEntity receiveCity = administrativeRegionsService.queryAdministrativeRegionsByCode(cityCode);
			if(receiveCity != null){
				info.setReciveCity(receiveCity.getSimpleName());
			}			
			targetList.add(info);
		}
		return targetList;
	}
	
	/**
	 * 设置出发、到达外场
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-21 20:01:29
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @param info
	 */
	public void setSecondAndFinalField(String sourceCode, String targetCode, String productCode, EWayBillDetailInfo info){
		//获取走货线路
		FreightRouteDto freightRouteDto = waybillManagerService.queryFreightRouteBySourceTarget(sourceCode, targetCode, productCode, new Date());
		//判断是否为空
		if (CollectionUtils.isNotEmpty(freightRouteDto.getFreightRouteLinelist())) {
			List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();//获得走货路径list
			//获取最终配载部门编码
			String lastChangeCenterOrgCode = freightRouteLinelist.get(freightRouteLinelist.size() - 1).getSourceCode();
			//进行数据的赋值
			if(StringUtils.isNotEmpty(lastChangeCenterOrgCode)){
				OrgAdministrativeInfoEntity lastChangeCenterOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(lastChangeCenterOrgCode);
				if(lastChangeCenterOrg != null){
					info.setLastOutLoadOrgName(lastChangeCenterOrg.getName());
				}
			}
			// 得到途径始发营业部编码和外场编码 , 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
			List<String> addressInfoList = new ArrayList<String>();
			//拼接走货路径
			for (FreightRouteLineDto f : freightRouteLinelist) {
				addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
			}
			
			// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的外场集合同时包含出发部门到达部门
			List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
			String firstCityName = null;
			//判断是否有外场
			if(departmentInfoList.size() >= 2){
				//根据组织编码查询组织信息
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(departmentInfoList.get(1));
				//出发外场编码对应的城市名称
				if(null != orgInfo){
					firstCityName = orgInfo.getCityName();
					info.setDepartCityName(orgInfo.getCityName());
				}
				
				//若只有2个，则一定为同城
				if(departmentInfoList.size() == 2){
					//若为同城则设置第二城市外场简称为目的场
					info.setSecondLoadOrgName(info.getCustomerPickupOrgName());
					return;
				}
				// 判断外场个数（去除出发部门）
				else{
					//外场所在城市名称
					String loadCityName = "";
					//遍历集合（从第2个开始遍历），查找不同城市的外场编码
					for (int i=1; i<=(departmentInfoList.size()-1); i++) {
						OrgAdministrativeInfoEntity loadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(departmentInfoList.get(i));
						if(null != loadOrg){
							//城市名称是否为空
							if(StringUtils.isEmpty(loadOrg.getCityName())){
								AdministrativeRegionsEntity entityInfo = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(loadOrg.getCityCode());
								if(null != entityInfo){
									loadCityName = entityInfo.getName();
								}
							}else{
								//获得外场城市名
								loadCityName = StringUtil.defaultIfNull(loadOrg.getCityName()).trim();
							}
						}
						
						//外场所在城市编码与出发外场所在城市不一样
						if(StringUtil.isNotEmpty(firstCityName) && StringUtil.isNotEmpty(loadCityName) && !firstCityName.equals(loadCityName) ){
							//找到则设置第二城市外场简称,直接退出
							info.setSecondLoadOrgName(loadOrg.getOrgSimpleName());
							return;
						}
					}
					
					//若为同城则设置第二城市外场简称为目的场
					info.setSecondLoadOrgName(info.getCustomerPickupOrgName());
				}
			}else{
				throw new WaybillValidateException("该走货线路错误：只有出发部门和到达部门，没有外场！");
			}
		}
	}
	/**
	 * 根据第一个外场 从外场集合串 取出单个外场列表 删除重复的
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-29 下午6:25:50
	 */
	private List<String> removeDuplicateRoute(List<String> routeList) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < routeList.size(); i++) {
			temp.add(routeList.get(i).substring(0, routeList.get(i).indexOf("-")));
		}
		return temp;
	}
	
	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param request
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#queryWaybillInfos(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QueryWaybillInfosResponse queryWaybillInfos(
			Holder<ESBHeader> esbHeader, QueryWaybillInfosRequest request)
			throws CommonException {
		int count =  0;
		List<WayBillDetailInfo> resultList = new ArrayList<WayBillDetailInfo>();
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		QueryWaybillInfosResponse response = new QueryWaybillInfosResponse();
		if(request == null){
			response.setCount(count);
			response.getWayBillList().addAll(resultList);
		}else{
			Map<String,Object> args = convertToCondition(request);
			/**
			 * 校验时间
			 */
			validationData(args);
			Map<String,Object> resultMap = waybillQueryService.queryWaybillInfos(args);
			List<WayBillDetailInfo> result = convertToTargetType((List<CrmWaybillServiceDto>) resultMap.get("list"));
			count = (Integer) resultMap.get("count");
			response.setCount(count);
			response.getWayBillList().addAll(result);
		}
		return response;
	}
	
	/**
	 * @author WangQianJin
	 * @date 2014-07-19
	 * APP查询我的发货单/收货单信息
	 * @param esbHeader
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QueryAppWaybillInfosResponse queryAppWaybillInfos(
			Holder<ESBHeader> esbHeader, QueryAppWaybillInfosRequest request)
			throws CommonException {
		int count =  0;
		List<AppWayBillDetailInfo> resultList = new ArrayList<AppWayBillDetailInfo>();
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		QueryAppWaybillInfosResponse response = new QueryAppWaybillInfosResponse();
		if(request == null){
			response.setCount(count);
			response.getWayBillList().addAll(resultList);
		}else{
			LOGGER.info("app请求参数:"+ObjectUtils.toString(request));
			String mobilePhone = request.getMobilePhone();
			if(StringUtils.isEmpty(mobilePhone)){
				response.setCount(count);
				response.getWayBillList().addAll(resultList);
				return response;
			}
			Map<String,Object> args = convertToConditionForApp(request);
			//查询时间范围不能超过7天
			validationDataForApp(args);
			Map<String,Object> resultMap = waybillQueryService.queryAppWaybillInfos(args);
			List<AppWayBillDetailInfo> result = convertToTargetTypeForApp((List<CrmWaybillServiceDto>)resultMap.get("list"));
			count = (Integer) resultMap.get("count");
			response.setCount(count);
			response.getWayBillList().addAll(result);
		}
		return response;
	}
	
	/**
	 * 封装参数
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param request
	 * @return Map<String,Object>
	 */
	private Map<String,Object> convertToConditionForApp(QueryAppWaybillInfosRequest request){
		Map<String,Object> argsMap = new HashMap<String, Object>();
		argsMap.put("mobilePhone", request.getMobilePhone());
		argsMap.put("type", request.getType());		
		argsMap.put("status", request.getStatus());
		argsMap.put("startDate", converToJavaDate(request.getStartDate()));
		argsMap.put("endDate", converToJavaDate(request.getEndDate()));		
		argsMap.put("pageNum", request.getPageNum());
		argsMap.put("pageSize", request.getPageSize());	
		/**
		 * 设置参数：
		 * 关键字：keyWords
		 */
		argsMap.put("keyWords", request.getKeyWords());
		return argsMap;
	}
	
	/**
	 * 判断时间是否为空，如果为空判断异常
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param argsMap
	 * @throws CommonException
	 */
	private void validationDataForApp(Map<String,Object> argsMap) throws CommonException{
		if(argsMap!=null){
			if(argsMap.get("startDate")==null||argsMap.get("endDate")==null){
				throw new CommonException("时间不能为空！");
			}else{				
				Date startDate=(Date)argsMap.get("startDate");
				Date endDate=(Date)argsMap.get("endDate");
				Long diffDate= DateUtils.getTimeDiff(startDate, endDate);			 
				if(diffDate>30){
					throw new CommonException("查询时间范围超过30天，请调整时间！");
				}			
			}			
		}
	}
	
	/**
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param list
	 * @return
	 */
	private List<AppWayBillDetailInfo> convertToTargetTypeForApp(List<CrmWaybillServiceDto> list){
		List<AppWayBillDetailInfo> targetList = new ArrayList<AppWayBillDetailInfo>();
		AppWayBillDetailInfo info = null;
		for(CrmWaybillServiceDto dto : list){
			info = new AppWayBillDetailInfo();
			info.setConsigneeAddress(dto.getConsigneeAddress());
			info.setConsigneeMobile(dto.getConsigneeMobile());
			info.setConsigneeName(dto.getConsignee());
			info.setConsigneetel(dto.getConsigneePhone());
			info.setCubage(dto.getCubage());
			info.setDeliveryCharge(dto.getDeliveryCharge());
			info.setDeliveryMode(dto.getDeliveryType());
			info.setDeparture(dto.getDeparture());
			info.setDepartureAddress(dto.getDepartureDeptAddr());
			info.setDepartureName(dto.getDepartureDeptName());
			info.setDeparturetel(dto.getDepartureDeptPhone());
			info.setDestination(dto.getDestination());
			info.setGoodsName(dto.getGoodName());
			info.setInsurance(dto.getInsurance());
			info.setInsuranceFee(dto.getInsuranceValue());
			info.setOrderNum(dto.getOrderNumber());
			info.setOtherCharge(dto.getOtherFee());
			info.setPacking(dto.getPacking());
			info.setPackingCharge(dto.getPackingFee());
			info.setPayWay(dto.getPayment());
			info.setPieces(dto.getPieces());
			info.setReceiveCharge(dto.getPickCharge());
			info.setRefund(dto.getRefund());
			info.setRefundFee(dto.getRefundFee());
			info.setRefundType(dto.getRefundType());
			info.setReturnBillType(dto.getSignBackType());
			info.setSendDate(convertToXMLGregorianCalendar(dto.getSendTime()));
			info.setShipperAddress(dto.getDepartureDeptAddr());
			info.setShipperMobile(dto.getSenderMobile());
			info.setShipperName(dto.getSender());
			info.setShippertel(dto.getSenderPhone());
			info.setSignedDate(convertToXMLGregorianCalendar(dto.getSignedDate()));
			info.setStationaddress(dto.getLadingStationAddr());
			info.setStationtel(dto.getLadingStationPhone());
			info.setStationName(dto.getLadingStationName());
			info.setWayBillState(dto.getWaybillStatus());
			info.setWaybillNum(dto.getWaybillNo());
			info.setPayWay(dto.getPaidMethod());
			info.setTotalCharge(dto.getTotalCharge());
			info.setTransProperties(dto.getTransProperties());
			info.setTranCharge(dto.getTransportFee());
			info.setWeight(dto.getWeight());
			info.setDepartureFax(dto.getDepartureDeptFax());
			info.setDestinationFax(dto.getLadingStationFax());
			info.setDepartureCityName(dto.getDepartureCityName());
			info.setDestinationCityName(dto.getDestinationCityName());
			info.setPredictArriveTime(convertToXMLGregorianCalendar(dto.getPredictArriveTime()));
			targetList.add(info);
		}
		return targetList;
	}

	/**
	 * 将DTO类型转换为目标类型
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param list
	 * @return
	 * @return List<WayBillDetailInfo>
	 * @see
	 */
	private List<WayBillDetailInfo> convertToTargetType(List<CrmWaybillServiceDto> list){
		List<WayBillDetailInfo> targetList = new ArrayList<WayBillDetailInfo>();
		WayBillDetailInfo info = null;
		for(CrmWaybillServiceDto dto : list){
			info = new WayBillDetailInfo();
			info.setConsigneeAddress(dto.getConsigneeAddress());
			//发货地址备注
			info.setConsigneeAddressNote(dto.getReceiveCustomerAddressNote());
			info.setConsigneeMobile(dto.getConsigneeMobile());
			info.setConsigneeName(dto.getConsignee());
			info.setConsigneetel(dto.getConsigneePhone());
			info.setCubage(dto.getCubage());
			info.setDeliveryCharge(dto.getDeliveryCharge());
			info.setDeliveryMode(dto.getDeliveryType());
			info.setDeparture(dto.getDeparture());
			info.setDepartureAddress(dto.getDepartureDeptAddr());
			info.setDepartureName(dto.getDepartureDeptName());
			info.setDeparturetel(dto.getDepartureDeptPhone());
			info.setDestination(dto.getDestination());
			info.setGoodsName(dto.getGoodName());
			info.setInsurance(dto.getInsurance());
			info.setInsuranceFee(dto.getInsuranceValue());
			info.setOrderNum(dto.getOrderNumber());
			info.setOtherCharge(dto.getOtherFee());
			info.setPacking(dto.getPacking());
			info.setPackingCharge(dto.getPackingFee());
			info.setPayWay(dto.getPayment());
			info.setPieces(dto.getPieces());
			info.setReceiveCharge(dto.getPickCharge());
			info.setRefund(dto.getRefund());
			info.setRefundFee(dto.getRefundFee());
			info.setRefundType(dto.getRefundType());
			info.setReturnBillType(dto.getSignBackType());
			info.setSendDate(convertToXMLGregorianCalendar(dto.getSendTime()));
			info.setShipperAddress(dto.getDepartureDeptAddr());
			//发货地址备注
			info.setShipperAddressNote(dto.getDeliveryCustomerAddressNote());
			info.setShipperMobile(dto.getSenderMobile());
			info.setShipperName(dto.getSender());
			info.setShippertel(dto.getSenderPhone());
			info.setSignedDate(convertToXMLGregorianCalendar(dto.getSignedDate()));
			info.setStationaddress(dto.getLadingStationAddr());
			info.setStationtel(dto.getLadingStationPhone());
			info.setStationName(dto.getLadingStationName());
			info.setWayBillState(dto.getWaybillStatus());
			info.setWaybillNum(dto.getWaybillNo());
			info.setPayWay(dto.getPaidMethod());
			info.setTotalCharge(dto.getTotalCharge());
			info.setTransProperties(dto.getTransProperties());
			info.setTranCharge(dto.getTransportFee());
			info.setWeight(dto.getWeight());
			info.setDepartureFax(dto.getDepartureDeptFax());
			info.setDestinationFax(dto.getLadingStationFax());
			/**
			 * 将dto中的是否电子发票传值到info中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28下午19:19
			 */
			info.setIfEInvoice(dto.getIsElectronicInvoice());
			/**
			 * 将dto中的电子发票号码传值到info中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28下午19:19
			 */
			info.setInvoicePhone(dto.getInvoiceMobilePhone());
			/**
			 * 将dto中的电子发票邮箱传值到info中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-10-28下午19:19
			 */
			info.setInvoiceMail(dto.getEmail());
			targetList.add(info);
		}
		return targetList;
	}
	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#getUnWriteoffReceivableBillInfo(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillRequest)
	 */
	@Override
	public GetUnWriteoffReceivableBillResponse getUnWriteoffReceivableBillInfo(
			Holder<ESBHeader> esbHeader,
			GetUnWriteoffReceivableBillRequest payload) throws CommonException {
		return null;
	}
	/**
	 * 封装参数
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param request
	 * @return Map<String,Object>
	 */
	private Map<String,Object> convertToCondition(QueryWaybillInfosRequest request) throws CommonException{
		Map<String,Object> argsMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(request.getName())){
			 throw new CommonException("用户名不能空！");
		}
		argsMap.put("userName", request.getName());
		argsMap.put("consigneeName", request.getConsigneeName());
		argsMap.put("endDate", converToJavaDate(request.getEndDate()));
		argsMap.put("goodsName", request.getGoodsName());
		argsMap.put("linkmanId", request.getLinkmanId());
		argsMap.put("name", request.getName());
		argsMap.put("pageNum", request.getPageNum());
		argsMap.put("pageSize", request.getPageSize());
		argsMap.put("payWay", request.getPayWay());
		argsMap.put("startDate", converToJavaDate(request.getStartDate()));
		argsMap.put("status", request.getStatus());
		argsMap.put("waybillNumber", request.getWaybillNum());
		return argsMap;
	}
	
	/**
	 * 判断时间是否为空，如果为空判断异常
	 * @param argsMap
	 * @throws CommonException
	 */
	private void validationData(Map<String,Object> argsMap) throws CommonException
	{
		if(argsMap!=null)
		{
			if(argsMap.get("waybillNumber")!=null){
				String waybillNum=(String)argsMap.get("waybillNumber");
				if(StringUtils.isEmpty(waybillNum))
				{
					if(argsMap.get("startDate")==null||argsMap.get("endDate")==null)
					{
						throw new CommonException("时间不能为空！");
					}else{
						
					Date startDate=(Date)argsMap.get("startDate");
					Date endDate=(Date)argsMap.get("endDate");
					 Long diffDate= DateUtils.getTimeDiff(startDate, endDate);
					 if(diffDate>7){
						 throw new CommonException("查询时间范围超过7天，请调整时间！");
					 }
					}
				}
			}else{
				if(argsMap.get("startDate")==null||argsMap.get("endDate")==null)
				{
					throw new CommonException("时间不能为空！");
				}else{
				 Date startDate=(Date)argsMap.get("startDate");
				 Date endDate=(Date)argsMap.get("endDate");
				 Long diffDate= DateUtils.getTimeDiff(startDate, endDate);
				 if(diffDate>7) {
					 throw new CommonException("查询时间范围超过7天，请调整时间！");
				 }
				}
			}//if end
		}
	}
	
	
	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param cal xml格式日期
	 * @return Date Java类型日期
	 * @see
	 */
	private Date converToJavaDate(XMLGregorianCalendar cal){
		if (cal != null) {
			GregorianCalendar ca = cal.toGregorianCalendar();
			return ca.getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * <p>
	 * 把java日期转换为XML格式日期
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-13 下午4:55:50
	 * @param date
	 * @return
	 * @see
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
		if (date != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar gc = null;
			try {
				gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			} catch (Exception e) {
				LOGGER.info("XML日期类型转换错误：", e);
			}
			return gc;
		} else {
			return null;
		}
	}

	@Override
	public ApplyChangeOrderResponse applyChangeOrder(Holder<ESBHeader> esbHeader,
			ApplyChangeOrderRequest request) throws CommonException {
		// TODO Auto-generated method stub
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		WaybillRfcForAccountServiceDto dto = new WaybillRfcForAccountServiceDto();
		dto.setApplyPerson(request.getUserName());
		dto.setApplyTime(new Date());
		dto.setChangeContent(request.getChangeContent());
		dto.setContact(request.getLinkMan());
		dto.setContactHandy(request.getLinkManMobile());
		dto.setUnifieldCode(request.getDeptNum());
		
		dto.setWaybillNumber(request.getWaybillNum());
		// TODO
		ApplyChangeOrderResponse res = new ApplyChangeOrderResponse();
		res.setResult(waybillRfcService.applyChangeOrder(dto));
		return res;
	}

	
	
	/*
	@Override
	public QueryChangeOrderResponse queryChangeOrder(Holder<ESBHeader> arg0,
			QueryChangeOrderRequest arg1) throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	/**
	  * @return  the waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}

	/**
	 * @param waybillRfcService the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @功能 提供给官网的走货路径
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-7 9:55:54
	 */
	@Override
	public QueryFreightRouteInfoResponse queryFreightRouteInfo(
			Holder<ESBHeader> esbHeader, QueryFreightRouteInfoRequest request)
			throws CommonException {
		List<FreightRouteParamDto> freightRouteParamList = request.getFreightRouteParamDto();
		QueryFreightRouteInfoResponse response = new QueryFreightRouteInfoResponse();
		List<FreightRouteParamCommonsDto> freightRouteDtoList = new ArrayList<FreightRouteParamCommonsDto>();
		//转换请求参数
		freightRouteDtoList = convertToFreightRouteRequest(freightRouteParamList);
		//查找走货路径
		List<FreightRouteCommonsDto> freightRouteList = labelPrintInfoService.getFreightRouteCommonsList(freightRouteDtoList);
		//转换成对应的返回参数
		List<PrintLabelBean> printLabelBean = responseconvertToFreightRouteResponse(freightRouteList);
		response.getPrintLabelBean().addAll(printLabelBean);
		return response;
	}

	/**
	 * @功能 赋值走货路径相关值
	 * @param freightRouteList
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	private List<PrintLabelBean> responseconvertToFreightRouteResponse(
			List<FreightRouteCommonsDto> freightRouteList){
		//如果为空什么都不做
		if(CollectionUtils.isEmpty(freightRouteList)){
			return null;
		}
		List<PrintLabelBean> printLabelBeanList = new ArrayList<PrintLabelBean>();
		PrintLabelBean printLabelBean = null;
		for(FreightRouteCommonsDto freightRouteDto:freightRouteList){
			printLabelBean = new PrintLabelBean();
//			BeanUtils.copyProperties(printLabelBean, freightRouteDto);
			printLabelBean.setParamsId(freightRouteDto.getParamsId());
			printLabelBean.setAddr1(freightRouteDto.getAddr1());
			printLabelBean.setAddr2(freightRouteDto.getAddr2());
			printLabelBean.setAddr3(freightRouteDto.getAddr3());
			printLabelBean.setAddr4(freightRouteDto.getAddr4());
			printLabelBean.setFinaloutfieldid(freightRouteDto.getFinalOutFieldId());
			printLabelBean.setIsStartFlag(freightRouteDto.getIsStarFlag());
			printLabelBean.setLastTransCenterCity(freightRouteDto.getLastTransCenterCity());
			printLabelBean.setLastTransCenterNo(freightRouteDto.getLastTransCenterNo());
			printLabelBean.setLocation1(freightRouteDto.getLocation1());
			printLabelBean.setLocation2(freightRouteDto.getLocation2());
			printLabelBean.setLocation3(freightRouteDto.getLocation3());
			printLabelBean.setLocation4(freightRouteDto.getLocation4());
			printLabelBean.setDestinationCode(freightRouteDto.getDestinationCode());
			printLabelBean.setDestination(freightRouteDto.getDestination());
			printLabelBean.setIsSureAB(freightRouteDto.getIsSureAB());
			printLabelBeanList.add(printLabelBean);
		}
		return printLabelBeanList;
	}

	/**
	 * @功能 请求参数转化成FOSS对象
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-7 11:22:11
	 * @param freightRouteParamList
	 * @return
	 */
	private List<FreightRouteParamCommonsDto> convertToFreightRouteRequest(
			List<FreightRouteParamDto> freightRouteParamList) {
		//为空什么也不做
		if(CollectionUtils.isEmpty(freightRouteParamList)){
			return null;
		}
		List<FreightRouteParamCommonsDto> paramsList = new ArrayList<FreightRouteParamCommonsDto>();
		FreightRouteParamCommonsDto paramCommonsDto = null;
		for(FreightRouteParamDto paramDto: freightRouteParamList){
			paramCommonsDto = new FreightRouteParamCommonsDto();
			paramCommonsDto.setParamsId(paramDto.getParamsId());
			paramCommonsDto.setStartOrgCode(paramDto.getStartOrgCode());
			paramCommonsDto.setLastLoadOrgCode(paramDto.getLastLoadOrgCode());
			paramCommonsDto.setProductCode(paramDto.getProductCode());
			paramCommonsDto.setBillTime(paramDto.getBillTime().toGregorianCalendar().getTime());
			paramsList.add(paramCommonsDto);
		}			
		return paramsList;
	}

	/**
	 * @功能 判断运单号是否存在
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-7 11:09:54
	 */
	@Override
	public QueryExistWaybillNoInfoResponse queryExistWaybillNoInfo(
			Holder<ESBHeader> esbHeader, QueryExistWaybillNoInfoRequest request)
			throws CommonException {
		QueryExistWaybillNoInfoResponse response = new QueryExistWaybillNoInfoResponse();
		String waybillNo = request.getWaybillNo();
		CheckWaybillNo checkWaybillNo = new CheckWaybillNo();
		boolean isExist = waybillManagerService.isWayBillExsitsOnOnlineAndOffline(waybillNo);
		if(isExist){
			checkWaybillNo.setIsExist("true");
		}else{
			checkWaybillNo.setIsExist("false");
		}
		checkWaybillNo.setExceptMsg(FossConstants.NO);
		response.setCheckWaybillNo(checkWaybillNo);
		return response;
	}
}
