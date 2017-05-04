package com.deppon.foss.module.pickup.oa.server.jms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.DetentCargoWorkflowRequest;
import com.deppon.esb.inteface.domain.foss.DetentCargoWorkflowResponse;
import com.deppon.esb.inteface.domain.foss.GrandGoodAbnormalRequest;
import com.deppon.esb.inteface.domain.foss.GrandGoodAbnormalResponse;
import com.deppon.esb.inteface.domain.foss.QueryWaybillEntityRequest;
import com.deppon.esb.inteface.domain.foss.QueryWaybillEntityResponse;
import com.deppon.foss.fosspkpforoaservice.CommonException;
import com.deppon.foss.fosspkpforoaservice.FossPkpforOAService;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGrandGoodAbnormalService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.GrandGoodAbnormalEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * <p>特此声明：此类为接送货与OA交互专用接口，FOSSOA属于中转与OA的的接口，大家不要迷惑了</p>
 * @author 026123-foss-lifengteng
 * @date 2014-2-20 13:53:59
 *
 */
public class PkpWorkflowServiceImpl implements FossPkpforOAService{
	private static final String PDA_PENDING_TYPE = "PDA_PENDING";
	private static Logger LOGGER = Logger.getLogger(PkpWorkflowServiceImpl.class);
	public static final String ENOUGH = "N/A";
	public static final String ZERO = "0";
	public static final String ONE = "1";
	
	/**  操作成功 */
	private static final String OPERATE_SUCCESS = "1";
	/** 操作失败 */
	private static final String OPERATE_FAILURE = "0";
	
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 流水号服务类
	 */
	private ILabeledGoodService  labeledGoodService;
	
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 更改单服务类
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 产品定义Service
	 */
	private IProductService productService;
	
	/**
	 * 部门组织服务类
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	        
	/**
	 * 偏线服务接口
	 */
	private IVehicleAgencyDeptService  vehicleAgencyDeptService;
	/**
	 * 快递代理理外发代理服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	/**
	 * 行政区域 Service接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 重大货物异常服务
	 */
	private IGrandGoodAbnormalService grandGoodAbnormalService ;
	
	/**
	 * 库存服务
	 * @return
	 */
	private IStockService stockService;
	
	
	public IStockService getStockService() {
		return stockService;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public IGrandGoodAbnormalService getGrandGoodAbnormalService() {
		return grandGoodAbnormalService;
	}
	public void setGrandGoodAbnormalService(
			IGrandGoodAbnormalService grandGoodAbnormalService) {
		this.grandGoodAbnormalService = grandGoodAbnormalService;
	}
	/**
	 * 行政区域 Service接口
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 偏线服务接口
	 */
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/**
	 *快递代理外发代理服务接口
	 */
	public void setLdpAgencyDeptService(
			ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	
	/**
	 * 更改单服务类
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 更改单服务类
	 */
	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}
	/**
	 * 运单服务类
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	/**
	 * 运单服务类
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	
	
	public ILabeledGoodService getLabeledGoodService() {
		return labeledGoodService;
	}
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	
	
	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * 产品定义Service	
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * 部门组织服务类	
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * <p>此接口为扣货申请接口所用</p>
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-20 13:54:49
	 */
	@Override
	public DetentCargoWorkflowResponse detentCargoWorkflow(
			DetentCargoWorkflowRequest detentCargoWorkflowRequest,
			Holder<ESBHeader> esbHeader) throws com.deppon.foss.fosspkpforoaservice.CommonException {
		DetentCargoWorkflowResponse response = new DetentCargoWorkflowResponse();
		//ESB
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		//传入参数不能为空
		if(StringUtils.isBlank(detentCargoWorkflowRequest.getWaybillNo())){
			response.setDeliverContact(ENOUGH);//发货人
			response.setReceiverContact(ENOUGH);//收货人
			response.setInsuranceAmount(new BigDecimal(ZERO));//保价声明价值
			response.setCodAmount(new BigDecimal(ZERO));//代收货款金额
			response.setMessage("传递的参数为空");
			response.setResultcode(ZERO);
			return response;
		}
		LOGGER.info("运单为："+detentCargoWorkflowRequest.getWaybillNo());
		//查询运单数据，这里可以不需要查询暂存表的数据，因为不管暂存表的数据有没有，都是不允许发更改的
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(detentCargoWorkflowRequest.getWaybillNo());
		if(waybillEntity == null){
			//可能单号不存在或者未补录
			response.setDeliverContact(ENOUGH);//发货人
			response.setReceiverContact(ENOUGH);//收货人
			response.setInsuranceAmount(new BigDecimal(ZERO));//保价声明价值
			response.setCodAmount(new BigDecimal(ZERO));//代收货款金额
			response.setMessage("运单不存在或未补录，不能起草扣货申请工作流");
			response.setResultcode(ZERO);
			return response;
		}else{
			LOGGER.info("运单详情数据："+ReflectionToStringBuilder.toString(waybillEntity));
			//需要判断运单的状态是否为待补录状态
			if(StringUtils.equals(PDA_PENDING_TYPE, waybillEntity.getPendingType())){
				//判断是否为内部带货，内部带货已经初始化为0了，所以不需要判断
				response.setCodAmount(waybillEntity.getCodAmount());
				response.setDeliverContact(waybillEntity.getDeliveryCustomerContact());//发货人
				response.setReceiverContact(waybillEntity.getReceiveCustomerContact());//收货人
				response.setInsuranceAmount(waybillEntity.getInsuranceAmount());//保价声明价值
				response.setCodAmount(waybillEntity.getCodAmount());//代收货款金额
				response.setMessage("待补录运单不能起草扣货申请工作流");
				response.setResultcode(ZERO);
				return response;
			}else{
				//是否存在发生更改未被审核的情况
				List<String> waybillList = new ArrayList<String>();
				waybillList.add(detentCargoWorkflowRequest.getWaybillNo());
				List<String> list = waybillRfcService.isExsitsWayBillRfcs(waybillList);
				if(list != null && list.size()>0){
					//更改未被审核的不能起草扣货申请
					LOGGER.info("单号存在更改："+ReflectionToStringBuilder.toString(list));
					response.setDeliverContact(waybillEntity.getDeliveryCustomerContact());//发货人
					response.setReceiverContact(waybillEntity.getReceiveCustomerContact());//收货人
					response.setInsuranceAmount(waybillEntity.getInsuranceAmount());//保价声明价值
					response.setCodAmount(waybillEntity.getCodAmount());//代收货款金额
					response.setMessage("该单存在更改未审核记录，不能起草扣货申请工作流");
					response.setResultcode(ZERO);
					return response;
				}else{
					//因为内部带货已经初始化为0了，所以不需要判断
					response.setDeliverContact(waybillEntity.getDeliveryCustomerContact());//发货人
					response.setReceiverContact(waybillEntity.getReceiveCustomerContact());//收货人
					response.setInsuranceAmount(waybillEntity.getInsuranceAmount());//保价声明价值
					response.setCodAmount(waybillEntity.getCodAmount());//代收货款金额
					response.setMessage("正常");
					response.setResultcode(ONE);
					LOGGER.info("最终返回的数据："+ReflectionToStringBuilder.toString(response));
					return response;
				}
			}
		}
	}
	
	/**
	 * 根据运单号查询运单相关信息
	 * @author 153161-foss-lufeifei
	 */
	@Override
	public QueryWaybillEntityResponse queryWaybillEntity(
			QueryWaybillEntityRequest queryWaybillEntityRequest,
			Holder<ESBHeader> esbHeader) throws CommonException {
		//ESB header
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		String waybillNo = queryWaybillEntityRequest.getWaybillNo();
		WaybillEntity waybill = null;
		QueryWaybillEntityResponse response = new QueryWaybillEntityResponse();
		try{
			if(StringUtils.isEmpty(waybillNo)){
				LOGGER.info("运单号为空");
				throw new CommonException("运单号为空");
			}
			//运单信息
			waybill= waybillManagerService.queryWaybillBasicByNo(waybillNo);
			response = buildResponse(waybill); 
			LOGGER.info("对外投保详情："+ReflectionToStringBuilder.toString(response));
			response.setResultCode(OPERATE_SUCCESS);
			response.setMessage("成功");
		}catch(CommonException e){
			response.setResultCode(OPERATE_FAILURE);
			LOGGER.info("对外投保异常错误信息:"+e.getMessage());
			response.setMessage(e.getMessage());
		}catch(Exception e){
			response.setResultCode(OPERATE_FAILURE);
			LOGGER.info("对外投保异常错误信息:"+e.getMessage());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * 组装response
	 * @param waybill
	 * @author 153161-foss-lufeifei
	 * @throws CommonException 
	 */
	private QueryWaybillEntityResponse buildResponse(WaybillEntity waybill) throws CommonException{
		//验证数据是否为空
		if(waybill == null){
			LOGGER.info("运单号查询不到可用运单信息");
			throw new CommonException("运单号查询不到可用运单信息");
		}
		
		QueryWaybillEntityResponse response = new QueryWaybillEntityResponse();
		// 运单号
		response.setWaybillNo(StringUtil.defaultIfNull(waybill.getWaybillNo()));
		// 开单时间
		response.setBillTime(convertToXMLGregorianCalendar(waybill.getBillTime()));
		// 运输方式
		if(StringUtils.isNotEmpty(waybill.getProductCode())){
			//根据产品编码查询三级产品类型
			ProductEntity productEntity = productService.getLevel3ProductInfo(waybill.getProductCode());
			if(productEntity != null){
				response.setTranType(StringUtil.defaultIfNull(productEntity.getName()));
			}else{
				response.setTranType(StringUtil.defaultIfNull(waybill.getProductCode()));
			}
		}
		// 起运地
		if(StringUtils.isNotEmpty(waybill.getCreateOrgCode())){
			OrgAdministrativeInfoEntity departureOrgEntity = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(StringUtil.defaultIfNull(waybill.getCreateOrgCode()));
			if(departureOrgEntity != null){
				response.setDepartureCity(StringUtil.defaultIfNull(departureOrgEntity.getCityName()));
			}else{
				response.setDepartureCity(StringUtil.defaultIfNull(waybill.getCreateOrgCode()));
			}
		}
		// 目的地
		setDestinationCity(waybill, response);
		// 包装
		String goodsPackage = waybill.getGoodsPackage();
		if(StringUtils.isNotEmpty(goodsPackage)){
			response.setPackageType(goodsPackage);
		}else{
			StringBuffer packageBuilder = new StringBuffer();
			//纸包装件数
			if(waybill.getPaperNum() != null && waybill.getPaperNum() > 0){
				packageBuilder.append(waybill.getPaperNum()).append("纸");
			}
			//木包装件数
			if(waybill.getWoodNum() != null && waybill.getWoodNum() > 0){
				packageBuilder.append(waybill.getWoodNum()).append("木");
			}
			//纤包装件数
			if(waybill.getFibreNum() != null && waybill.getFibreNum() > 0){
				packageBuilder.append(waybill.getFibreNum()).append("纤");
			}
			//托包装件数
			if(waybill.getSalverNum() != null && waybill.getSalverNum() > 0){
				packageBuilder.append(waybill.getSalverNum()).append("托");
			}
			//膜包装件数
			if(waybill.getSalverNum() != null && waybill.getMembraneNum() > 0){
				packageBuilder.append(waybill.getMembraneNum()).append("膜");
			}
			//其他包装
			if(StringUtils.isNotEmpty(waybill.getOtherPackage())){
				packageBuilder.append(waybill.getOtherPackage());
			}
			if(StringUtils.isNotEmpty(packageBuilder.toString())){
				response.setPackageType(packageBuilder.toString());
			}
		}
		// 件数
		if(waybill.getGoodsQtyTotal() != null && waybill.getGoodsQtyTotal() > 0)
			response.setPieces(waybill.getGoodsQtyTotal());
		// 体积
		if(waybill.getGoodsVolumeTotal() != null){
			response.setCubage(waybill.getGoodsVolumeTotal());
		}
		// 重量
		if(waybill.getGoodsWeightTotal() != null){
			response.setWeight(waybill.getGoodsWeightTotal());
		}
		// 保价金额
		if(waybill.getInsuranceAmount() != null){
			response.setInsuranceValue(waybill.getInsuranceAmount());
		}			
		//预计到达时间
		response.setPreArriveTime(convertToXMLGregorianCalendar(waybill.getPreArriveTime()));
		// 货物名称
		response.setGoodsName(StringUtil.defaultIfNull(waybill.getGoodsName()));
		
		
		return response;
	}
    /**
     * @param waybill
     * @param response
     */
    private void setDestinationCity(WaybillEntity waybill,
            QueryWaybillEntityResponse response) {
        if(StringUtils.isNotEmpty(waybill.getCustomerPickupOrgCode())){
			if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, waybill.getProductCode()) 
					|| StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybill.getProductCode())){
		    //偏线或者空运
		    OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(waybill.getCustomerPickupOrgCode(), null);
		    if(outerBranchEntity != null){
		        AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(outerBranchEntity.getCityCode());
		        if(city != null){
		    	    response.setDestinationCity(StringUtil.defaultIfNull(city.getName()));
		        }else{
		    		response.setDestinationCity(waybill.getCustomerPickupOrgCode());
		    	}
		    }else{
	    		response.setDestinationCity(waybill.getCustomerPickupOrgCode());
	    	}
		    }else if(productService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())){
		    	//快递
		    	OuterBranchExpressEntity outerBranchEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(), FossConstants.YES);
		    	if(outerBranchEntity != null){
		    		response.setDestinationCity(StringUtil.defaultIfNull(outerBranchEntity.getCityName()));
		        }else{
		        	//营业部或者其他组织,因为目前快递也可能是公司的自有网点
			    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getCustomerPickupOrgCode());
			    	if(orgDeliveryInfo != null){
			    		response.setDestinationCity(StringUtil.defaultIfNull(orgDeliveryInfo.getCityName()));
			    	}else{
			    		response.setDestinationCity(StringUtil.defaultIfNull(waybill.getCustomerPickupOrgCode()));
			    	}
		        }
		    }else{
		    	//营业部或者其他组织
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getCustomerPickupOrgCode());
		    	if(orgDeliveryInfo != null){
		    		response.setDestinationCity(StringUtil.defaultIfNull(orgDeliveryInfo.getCityName()));
		    	}else{
		    		response.setDestinationCity(StringUtil.defaultIfNull(waybill.getCustomerPickupOrgCode()));
		    	}
			}
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
				LOGGER.error("XML日期类型转换错误：", e);
			}
			return gc;
		} else {
			return null;
		}
	}
	
	/**
	 * 重大货物异常上报
	 * @author foss-PanGuoYang
	 * @date 2014-05-22 下午14:55:50
	 */
	@Override
	@WebResult(name = "grandGoodAbnormalResponse", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss", partName = "grandGoodAbnormalResponse")
	@WebMethod(action = "http://www.deppon.com/foss/fossPkpForOaService/grandGoodAbnormalReport")
	public GrandGoodAbnormalResponse grandGoodAbnormalReport(
			@WebParam(partName = "grandGoodAbnormalRequest", name = "grandGoodAbnormalRequest", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss") GrandGoodAbnormalRequest grandGoodAbnormalRequest,
			@WebParam(partName = "esbHeader", mode = Mode.INOUT, name = "esbHeader", targetNamespace = "http://www.deppon.com/esb/header", header = true) Holder<ESBHeader> esbHeader)
			throws CommonException {
		GrandGoodAbnormalResponse  response = new  GrandGoodAbnormalResponse();
		try{
			esbHeader.value.setResponseId(UUID.randomUUID().toString());
			esbHeader.value.setResultCode(1);
			//单号
			String waybillNo =grandGoodAbnormalRequest.getWaybillNo();
			//运单信息
			WaybillEntity waybill= waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybill!=null){
				WaybillSignResultEntity entity = new WaybillSignResultEntity();
				entity.setWaybillNo(waybillNo);
				entity.setActive(FossConstants.YES);
				//签收结果
				WaybillSignResultEntity wentity=waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
				if(wentity!=null){
					//签收时间
					response.setSignTime(convertToXMLGregorianCalendar(wentity.getSignTime()));
				}
				//单号
				response.setWaybillNo(waybillNo);
				List<String> codes= stockService.queryInDeptCodeByWaybillNo(waybillNo);
				String unifiedCode="";
				
				if(codes!=null && codes.size()>0){
				    StringBuffer sb = new StringBuffer();
					for(String code:codes){
						OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
						if(org!=null){
						    sb.append(org.getUnifiedCode()).append(",");
						}
					}
					
					unifiedCode = sb.toString();
				    if(StringUtil.isNotEmpty(unifiedCode)){
				    	String str=unifiedCode;
						 str=str.substring(0,str.length()-1);
						//经手部门
						response.setPassDepartments(str);
				    }
				}
				response.setResultCode(OPERATE_SUCCESS);
				response.setMessage("成功");
				LOGGER.info("重大货物异常上报："+ReflectionToStringBuilder.toString(response));
			}else{
				response.setResultCode(OPERATE_FAILURE);
				response.setMessage("重大货物异常上报时没有查询到该单号相关信息");
			}
		}catch(Exception e){
			response.setResultCode(OPERATE_FAILURE);
			LOGGER.info("重大货物异常上报有异常:"+e.getMessage());
			response.setMessage(e.getMessage());
		}
		
		
		return response;
	}
	
	/**
	 * 重大货物异常处理
	 * @author foss-PanGuoYang
	 * @date 2014-05-22 下午14:55:50
	 */
	@Override
	@WebResult(name = "grandGoodAbnormalResponse", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss", partName = "grandGoodAbnormalResponse")
	@WebMethod(action = "http://www.deppon.com/foss/fossPkpForOaService/grandGoodAbnormalHandle")
	public GrandGoodAbnormalResponse grandGoodAbnormalHandle(
			@WebParam(partName = "grandGoodAbnormalRequest", name = "grandGoodAbnormalRequest", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss") GrandGoodAbnormalRequest grandGoodAbnormalRequest,
			@WebParam(partName = "esbHeader", mode = Mode.INOUT, name = "esbHeader", targetNamespace = "http://www.deppon.com/esb/header", header = true) Holder<ESBHeader> esbHeader)
			throws CommonException {
		GrandGoodAbnormalResponse  response = new  GrandGoodAbnormalResponse();
		try{
			esbHeader.value.setResponseId(UUID.randomUUID().toString());
			esbHeader.value.setResultCode(1);
			GrandGoodAbnormalEntity grangGood = new GrandGoodAbnormalEntity();
			//id
			grangGood.setId(UUIDUtils.getUUID());
			//单号
			String waybillNo =grandGoodAbnormalRequest.getWaybillNo();
			grangGood.setWaybillNo(waybillNo);
			//出险原因
			grangGood.setDangerCause(grandGoodAbnormalRequest.getDangerCause());
			//责任部门
			grangGood.setUnifiedCode(grandGoodAbnormalRequest.getUnifiedCode());
			//经手是否有责
			grangGood.setPassIsDuty(grandGoodAbnormalRequest.getPassIsDuty());
			//新增重大货物异常
			GrandGoodAbnormalEntity genty =grandGoodAbnormalService.queryGrandGoodAbnormal(waybillNo);
			if(genty!=null){
				grandGoodAbnormalService.updateGrandGoodAbnormal(grangGood);
				LOGGER.info("重大货物异常处理："+ReflectionToStringBuilder.toString(response));
				response.setWaybillNo(waybillNo);
				response.setResultCode(OPERATE_SUCCESS);
				response.setMessage("成功");
			}else{
				grandGoodAbnormalService.insertGrandGoodAbnormal(grangGood);
				LOGGER.info("重大货物异常处理："+ReflectionToStringBuilder.toString(response));
				response.setWaybillNo(waybillNo);
				response.setResultCode(OPERATE_SUCCESS);
				response.setMessage("成功");
			}
			
		}catch (Exception e) {
			response.setResultCode(OPERATE_FAILURE);
			LOGGER.info("重大货物异常处理有异常:"+e.getMessage());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * 按件差错管理
	 * @author foss-PanGuoYang
	 * @date 2014-05-22 下午14:55:50
	 */
	@Override
	@WebResult(name = "grandGoodAbnormalResponse", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss", partName = "grandGoodAbnormalResponse")
	@WebMethod(action = "http://www.deppon.com/foss/fossPkpForOaService/abnormalByPieceManage")
	public GrandGoodAbnormalResponse abnormalByPieceManage(
			@WebParam(partName = "grandGoodAbnormalRequest", name = "grandGoodAbnormalRequest", targetNamespace = "http://www.deppon.com/esb/inteface/domain/foss") GrandGoodAbnormalRequest grandGoodAbnormalRequest,
			@WebParam(partName = "esbHeader", mode = Mode.INOUT, name = "esbHeader", targetNamespace = "http://www.deppon.com/esb/header", header = true) Holder<ESBHeader> esbHeader)
			throws CommonException {
		GrandGoodAbnormalResponse  response = new  GrandGoodAbnormalResponse();
		try{
			esbHeader.value.setResponseId(UUID.randomUUID().toString());
			esbHeader.value.setResultCode(1);
			//运单号
			String waybillNo = grandGoodAbnormalRequest.getWaybillNo();
			//运单信息
			WaybillEntity waybill= waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybill!=null){
				//流水号
				List<LabeledGoodEntity>  labeledGoods= labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
				if(CollectionUtils.isNotEmpty(labeledGoods)){
					StringBuffer  labgood=new StringBuffer();
					 for(LabeledGoodEntity labeledGood:labeledGoods){
						 labgood.append(labeledGood.getSerialNo()+",");
					 }
					 String str=labgood.toString();
					 str=str.substring(0,str.length()-1);
					 response.setSertalNos(str);
				}
				//运单号
				response.setWaybillNo(waybillNo);
				//发货客户编码
				response.setDeliveryCustomerCode(waybill.getDeliveryCustomerCode());
				//收货客户编码
				response.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
				response.setResultCode(OPERATE_SUCCESS);
				response.setMessage("成功");
				LOGGER.info("按件差错管理："+ReflectionToStringBuilder.toString(response));
			}else{
				response.setResultCode(OPERATE_FAILURE);
				response.setMessage("按件差错管理时没有查询到该单号相关信息");
			}
		}catch(Exception e){
			response.setResultCode(OPERATE_FAILURE);
			LOGGER.info("按件差错管理有异常:"+e.getMessage());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	
}
