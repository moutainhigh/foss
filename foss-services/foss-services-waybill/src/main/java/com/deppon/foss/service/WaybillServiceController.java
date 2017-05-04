package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.WaybillCostInfo;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IClaimStatusService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDataOutService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultValueDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillSignService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillChargeCostDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.request.QueryAppWaybillInfosRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryAppWaybillDetailResponse;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryAppWaybillInfosResponse;
import com.deppon.foss.module.pickup.waybill.shared.vo.AppWayBillDetaillVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillDetailVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.transfer.load.api.server.service.ICalWaybillQtyService;
import com.deppon.foss.module.transfer.load.api.shared.define.CalWaybillQtyConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.stock.api.server.dao.IMatchTaskOrgDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.shared.request.WaybillDetailForOfficialRequest;
import com.deppon.foss.shared.request.WaybillDetailForSOCRequest;
import com.deppon.foss.shared.request.WaybillSignDetailQueryRequest;
import com.deppon.foss.shared.response.WaybillDetailForSOCResponse;
import com.deppon.foss.shared.response.WaybillSignDetailResponse;
import com.deppon.foss.shared.vo.WaybillDetail;
import com.deppon.foss.shared.vo.WaybillDetailForOfficialVo;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.util.define.FossConstants;

@Controller
@RequestMapping("/waybill")
public class WaybillServiceController {

	Log logger = LogFactory.getLog(WaybillServiceController.class);
	
	//接送货 服务 
	@Resource
	private IWaybillSignService waybillSignService ;

	/**库存service     根据运单号，查询库存信息*/
	@Resource
	private IStockService stockService;
	//结算 服务 接口
	@Autowired(required=false)
	private ISignDataOutService signDataOutService;
	
	//中转 服务 接口
	@Autowired(required=false)
	private ICalWaybillQtyService calWaybillQtyService ;
	
	//快递订单接口
	@Resource 
	private IWaybillExpressService waybillExpressService ;
	
	/**运单查询service   根据运单号，查询运单详细信息*/
	@Resource
	private IWaybillQueryService waybillQueryService;
	/**匹配任务部门 Dao*/
	@Resource
	private IMatchTaskOrgDao matchTaskOrgDao;
	/**
	 * 运单签收结果service
	 */
	@Resource
	private IWaybillSignResultService waybillSignResultService;
	/** 
	 * 货件库存DAO
	 * */
	@Resource
	private IStockDao stockDao;
	/** 
	 * 派送单明细DAO接口
	 */
	@Resource
	private IDeliverbillDetailDao deliverbillDetailDao;
	
	@Autowired(required=false)
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService ;
	
	/**
	 * 应付单服务.
	 */
	@Resource
	private IBillPayableService billPayableService;
	/**
	 * 查询更改理赔
	 */
	@Resource
	private IClaimStatusService claimStatusService;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@RequestMapping(value = "/sign/getWaybillSign")
	@ResponseBody
	public WaybillSignDetailResponse getWaybillSign(@RequestBody WaybillSignDetailQueryRequest param, HttpServletResponse response) {
		logger.info("进入请求 getWaybillSign ");
		long time_map = 0,time_JieSuan=0,time_zhongzhuan=0,begin=System.currentTimeMillis();
		WaybillSignDetailResponse waybillSignDetailResponse = new WaybillSignDetailResponse() ;
		try {
			WaybillSignDetailQueryVo waybillSignDetailQueryVo = new WaybillSignDetailQueryVo() ;
			waybillSignDetailQueryVo.setDeliveryCustomerCode(param.getDeliveryCustomerCode());
			waybillSignDetailQueryVo.setStartTime(param.getStartTime());
			waybillSignDetailQueryVo.setEndTime(param.getEndTime());
			logger.info("请求参数："+param.toString());
			
			waybillSignDetailResponse.setStartTime(param.getStartTime());
			waybillSignDetailResponse.setEndTime(param.getEndTime());
			
			//获取总记录数
			int totalCount = waybillSignService.countQueryWayBillSignDetail(waybillSignDetailQueryVo);
			waybillSignDetailResponse.setTotalCount(totalCount);//总记录数
			waybillSignDetailResponse.setTotalWaybill(totalCount);//总票数
			if(totalCount > 0){
				// 获取 快递签收状态 的详细信息
				List<WaybillSignDetailVo> waybillSignDetailVoList = waybillSignService.queryWayBillSignDetail(waybillSignDetailQueryVo, param.getStart(), param.getLimit(),false);
				
				//获取运单号
				List<String> waybillNoList = new ArrayList<String>();
				for(int i=0; i<waybillSignDetailVoList.size();i++){
					WaybillSignDetailVo waybillSignDetailVo = waybillSignDetailVoList.get(i);
					waybillNoList.add(waybillSignDetailVo.getWaybillNo());
//					logger.info("运单号："+waybillSignDetailVo.getWaybillNo()) ;
				}
				
				//调用 结算 接口 ；返回值：ResultValueDto。
				long beign_sign = System.currentTimeMillis();
				ResultValueDto resultValueDto = signDataOutService.getReturnData(waybillNoList) ;
				waybillSignDetailResponse.setSignWaybill(resultValueDto.getSignedNum());//已签收票数
				waybillSignDetailResponse.setExcepSignWaybill(resultValueDto.getExcepNum());//异常签收票数
				waybillSignDetailResponse.setSignRatio(resultValueDto.getSignRate()); //签收率
				waybillSignDetailResponse.setDispatchWaybill(resultValueDto.getDeliveingNum());//派件途中票数
				
				long begin_cal = System.currentTimeMillis();
				time_JieSuan = (begin_cal-beign_sign);
				logger.info("调用结算接口消耗时长："+time_JieSuan);
				
				//调用 中转 接口 
//				CalWaybillQtyDto calWaybillQtyDto = calWaybillQtyService.calWaybillOnQtys(waybillNoList) ;
//				waybillSignDetailResponse.setTransWaybill(calWaybillQtyDto.getOnTheWayQTYTotal()-waybillSignService.queryWaybillInvalid(waybillNoList)-waybillSignService.queryWaybillBack(waybillNoList));// 运输中票数
//				waybillSignDetailResponse.setLoseWaybill(calWaybillQtyDto.getLoseGoodsQTYTotal()) ;// 遗失票数 
				
				long end_cal = System.currentTimeMillis();
				//调用 中转 外发接口
				List<LdpExternalBillDto> ldpExternalBillDtos = getExternalBillDtos(waybillNoList);
				
				//ldpExternalBillDtos 封装到 map中
				//外发单号
				Map<String, String> map_ldp_billNo = new HashMap<String, String>();
				//外发公司名称
				Map<String, String> map_ldp_compName = new HashMap<String, String>();
				for(LdpExternalBillDto ldpDto : ldpExternalBillDtos){
					map_ldp_billNo.put(ldpDto.getWaybillNo(), ldpDto.getExternalBillNo());
					map_ldp_compName.put(ldpDto.getWaybillNo(), ldpDto.getAgentCompanyName());
				}
				
				//获取运单状态
				Map<String, String> map = getStatusMap(waybillNoList);
				if(map!=null){
					map.get("map");
					int onTheWayQTYTotal = Integer.valueOf(map.get("onTheWayQTYTotal"));
					int loseGoodsQTYTotal = Integer.valueOf(map.get("loseGoodsQTYTotal"));
					waybillSignDetailResponse.setTransWaybill(onTheWayQTYTotal
							-waybillSignService.queryWaybillInvalid(waybillNoList) //作废的票数
							-waybillSignService.queryWaybillBack(waybillNoList) //退回的票数
							);// 运输中票数
					waybillSignDetailResponse.setLoseWaybill(loseGoodsQTYTotal) ;// 遗失票数 
					
					//更新运单状态
					for(WaybillSignDetailVo waybillSignDetailVo  : waybillSignDetailVoList){
						String status = map.get(waybillSignDetailVo.getWaybillNo());
						if(StringUtils.isNotEmpty(status)){
							waybillSignDetailVo.setSignStatus(status);
						}
						//外发
						waybillSignDetailVo.setExternalBillNo(map_ldp_billNo.get(waybillSignDetailVo.getWaybillNo()));
						waybillSignDetailVo.setAgentCompanyName(map_ldp_compName.get(waybillSignDetailVo.getWaybillNo()));
						
					}
				}
				
				long end_map = System.currentTimeMillis();
				time_map = (end_map - end_cal);
				logger.info("调用中转接口和map信息消耗时长："+time_map);
				
				waybillSignDetailResponse.setCancelWaybill(waybillSignService.queryWaybillInvalid(waybillNoList)) ;//作废票数
				waybillSignDetailResponse.setBackWaybill(waybillSignService.queryWaybillBack(waybillNoList));//退回票数
				logger.info("返回结果 返回记录数："+waybillSignDetailVoList.size()+" 请求记录数："+param.getLimit());//+" 返回记录数："+waybillSignDetailResponse.getWaybillSignDetailVoList().size()
				
				int start = param.getStart();//起始页码(大客户特别要求)
				int limit = param.getLimit();//每页条数
				// 策画须要显示的成果数据
				List<WaybillSignDetailVo> pageList = new ArrayList<WaybillSignDetailVo>();
				//start从0开始计数
				for(int i = start ; i < waybillSignDetailVoList.size() && i < start + limit ; i++){
					pageList.add(waybillSignDetailVoList.get(i));
			}
				waybillSignDetailResponse.setWaybillSignDetailVoList(pageList);
			}
			
			logger.info("最终返回结果："+waybillSignDetailResponse.toString());
			logger.info("调用结算接口消耗时长："+time_JieSuan+";调用中转接口消耗时长："+time_zhongzhuan+";调用中转和map信息消耗时长："+time_map+";总耗时："+(System.currentTimeMillis()-begin));
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return waybillSignDetailResponse;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			waybillSignDetailResponse.setSuccess(FossConstants.NO);
			waybillSignDetailResponse.setErrorMsg(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			return waybillSignDetailResponse;
		}
	}

	/**
	 * 根据运单号获取 运单的外发信息（外发单号、外发公司）
	 * @param waybillNoList 
	 * @return
	 */
	private List<LdpExternalBillDto> getExternalBillDtos(List<String> waybillNoList) {
		
		List<LdpExternalBillDto> ldpExternalBillDtos = new ArrayList<LdpExternalBillDto>();
		try {
			int size = waybillNoList.size();
			if(size >= 1000){
				List<String> waybillNo_tmp = new ArrayList<String>();
				int pageCount = (size-1)/900 +1 ;
				for(int i=0 ; i < pageCount ; i++ ){
					if(i== pageCount-1){
						waybillNo_tmp = waybillNoList.subList(i * 900 , size);
					}else {
						waybillNo_tmp = waybillNoList.subList(i * 900 , (i+1)*900 );
					}
					List<LdpExternalBillDto> tmps= calWaybillQtyService.queryLdpExternalBillNoListByWayBillNos(waybillNo_tmp) ;
					if(tmps!=null){
						ldpExternalBillDtos.addAll(tmps);
					}
				}
			}else {
				ldpExternalBillDtos = calWaybillQtyService.queryLdpExternalBillNoListByWayBillNos(waybillNoList) ;
			}
		} catch (Exception e) {
			logger.error("签收详情接口 调用中转外发接口异常");
			throw new BusinessException("调用中转外发接口异常",e);
		}
		return ldpExternalBillDtos;
	}
	
	/**
	 * 大客户运单详情 获取运单状态
	 * @param waybillList
	 * @return 运单状态集合
	 */
	private Map<String,String> getStatusMap(List<String> waybillList){
		//<运单号,运单状态>
    	Map<String, String> map = new HashMap<String, String>();
    	// 零担丢货管理组
    	final String LOSEORGCODE = "W01000301050203";
    	
    	int onTheWayQTYTotal = 0 , loseGoodsQTYTotal = 0 ;
    	try {
		
		for (String waybillNo : waybillList) {
		//获得运单信息
		WaybillInfoByWaybillNoReultDto waybill = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
	    if(waybill == null){
	    	logger.error("运单"+waybillNo+"信息不存在！");
	    }else{
	    	//运单状态
	    	String status = null;	
	    	
	    	List<String> strList = new ArrayList<String>();
	    	strList.add(waybillNo);
	    	int canlelNum = waybillSignService.queryWaybillInvalid(strList);//作废票数
	    	if( status == null  && canlelNum>0 ){
	    		//作废
	    		status = CalWaybillQtyConstants.CANCELD; 
	    	}
	    	 
	    	int returnNum = waybillSignService.queryWaybillBack(strList);
	    	if(status == null && returnNum>0){
	    		//退回
	    		status = CalWaybillQtyConstants.RETURNBACK; 
	    	}
	    	
			WaybillSignResultEntity signResultEntity=new WaybillSignResultEntity();
			signResultEntity.setWaybillNo(waybillNo);
			signResultEntity.setActive(FossConstants.ACTIVE);
			// 根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
			// 根据运单号查询派送单明细(新)
			List<DeliverbillDetailEntity> deliverDetailList = deliverbillDetailDao.queryByWaybillNo(waybillNo);
			if(CollectionUtils.isNotEmpty(deliverDetailList) ){
				//派送中-派送单详情有记录的前提下：1、签收结果表为空；2、签收结果表有部分签收记录  
				if((status == null && newEntity == null) || (newEntity!=null && !StringUtil.equals("SIGN_STATUS_ALL", newEntity.getSignStatus()))){
					//派送中
					status = CalWaybillQtyConstants.DElIVERY_IN;	
				}
			}
	    
			WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
			waybillStockEntity.setWaybillNO(waybillNo);
			//根据运单号查询库存查询当前运单的 库存 信息 
			List<StockEntity> stockList = stockService.queryStockByWaybillNo(waybillStockEntity);
			boolean isSigned = false;
			//根据运单号查询运单是否 全部签收
			isSigned = matchTaskOrgDao.checkWaybillIsSigned(waybillNo);
			//全部签收
			if(status == null  && isSigned){
				//签收
				status = CalWaybillQtyConstants.SIGN;
			}
			
			List<StockEntity> stockEntityList = new ArrayList<StockEntity>();
			stockEntityList = stockDao.queryStockByWO(waybillNo,waybill.getReceiveOrgCode());

			//开单件数等于收货部门的库存件数 = 营业部库存
			if(status == null  && stockEntityList.size() == waybill.getGoodsQtyTotal()){
				//营业部库存
				status = CalWaybillQtyConstants.SALES_INVENTORY;
			}	
			
			//部分签收
			if(!isSigned){
				//没有库存
				 if(CollectionUtils.isNotEmpty(stockList)){
			     for (StockEntity stock : stockList) {
			    	 if(stock.getOrgCode().equals(LOSEORGCODE)){
			    		 loseGoodsQTYTotal += 1;
			    		 //遗失
			    		 if(status == null){
				    		 status = CalWaybillQtyConstants.lOSING; 
			    		 }
			    		 break;
			    	 }
				}			
			  }
			}	
			
			if(status == null){
				//运输中
				status = CalWaybillQtyConstants.TRANSPORTATION;
				onTheWayQTYTotal += 1;
			}
			
			map.put(waybillNo, status);
			
		} 
	}
		
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		map.put("loseGoodsQTYTotal", loseGoodsQTYTotal+"");
		map.put("onTheWayQTYTotal", onTheWayQTYTotal+"");
		return map ;
	}


	/**
	 * 根据请求参数查询 运单信息(包括子母件)-APP子母件发货记录查询
	 * @param appWaybillInfosRequest 请求参数
	 * @param response 
	 * @return 查询到的结果集
	 * @author 272311
	 * 2015.09.07
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAppWaybillRelateDetailInfos")
	public @ResponseBody QueryAppWaybillInfosResponse getAppWaybillRelateDetailInfos(@RequestBody QueryAppWaybillInfosRequest appWaybillInfosRequest,HttpServletResponse response){
		logger.info("进入请求 getAppWaybillCZMInfos ");
		QueryAppWaybillInfosResponse appWaybillInfosResponse = new QueryAppWaybillInfosResponse();
		try {
			logger.info("请求参数："+appWaybillInfosRequest.toString());
			if(StringUtils.isBlank(appWaybillInfosRequest.getMobilePhone())){
				throw new BusinessException("电话号码不能为空");
			}
			if(appWaybillInfosRequest.getStartDate()==null || appWaybillInfosRequest.getEndDate()==null){
				throw new BusinessException("请输入查询时间范围");
			}
			//把参数封装成map
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("mobilePhone", appWaybillInfosRequest.getMobilePhone());
			map.put("type", appWaybillInfosRequest.getType());		
			map.put("status", appWaybillInfosRequest.getStatus());
			map.put("startDate", appWaybillInfosRequest.getStartDate());
			map.put("endDate", appWaybillInfosRequest.getEndDate());		
			map.put("pageNum", appWaybillInfosRequest.getPageNum());
			map.put("pageSize", appWaybillInfosRequest.getPageSize());	
			map.put("keyWords", appWaybillInfosRequest.getKeyWords());
			
			Map<String,Object> resultMap = waybillQueryService.queryAppWaybillInfosExp(map);
			int count = (Integer) resultMap.get("count");
			logger.info("返回的记录数："+count);
			List<AppWayBillDetaillVo> lists = (List<AppWayBillDetaillVo>) resultMap.get("list");
			appWaybillInfosResponse.setCount(count);
			appWaybillInfosResponse.setWaybillList(lists);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return appWaybillInfosResponse ;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			appWaybillInfosResponse.setSuccess(FossConstants.NO);
			appWaybillInfosResponse.setErrorMsg(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			return appWaybillInfosResponse;
		}
		
	}

	/**
	* 官网查询子母件接口
	* 
	* @Method: queryWaybillDetailForOfficial 
	* @param param
	* @param response
	* @return WaybillDetailForOfficialResponse
	* @author WangZengming
	* @date 2015-9-2 下午1:43:55
	* @see
	*/
	@RequestMapping(value = "/getWaybillForOfficial")
	@ResponseBody
	public Object queryWaybillDetailForOfficial(@RequestBody WaybillDetailForOfficialRequest param, HttpServletResponse response) {
		logger.info("进入请求 queryWaybillDetailForOfficial ");
		try {
			
			List<String> waybillNoList = param.getWaybillNoList();
			List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfoForOfficialCP(waybillNoList);
			WaybillDetailForOfficialVo wayBillDetail = null;
			Map<String,WaybillDetailForOfficialVo> waybillDetailForOfficialMap =
					new ConcurrentHashMap<String,WaybillDetailForOfficialVo>();
			if(CollectionUtils.isNotEmpty(waybillNoList)){
				for(WaybillInfoDto waybillInfo : waybillInfoDtoList){
					wayBillDetail =  new WaybillDetailForOfficialVo();
					//是否子母件
					wayBillDetail.setIsChlidParentWaybill(waybillInfo.isParentChildWaybill());
					//母件单号
					wayBillDetail.setParentWaybillNo(waybillInfo.getParentWaybillNo());
					//子单号集合
					if(CollectionUtils.isNotEmpty(waybillInfo.getChildWaybillNoList()))
						wayBillDetail.setChildWaybillNoList(waybillInfo.getChildWaybillNoList());
					//运单信息封装成Map
					waybillDetailForOfficialMap.put(waybillInfo.getWaybillNo(), wayBillDetail);
				}
			}
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			logger.info("---请求结束--- ");
			return waybillDetailForOfficialMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	//APP货物信息查询 包括 子件/母件/正常件（如果是子母件，把子母件关联的其他单号的信息一起传）-APP货物信息查询
	@RequestMapping("/getAppWaybillRelateDetails")
	public @ResponseBody QueryAppWaybillDetailResponse getAppWaybillRelateDetails(@RequestBody QueryDetailRequest payload,HttpServletResponse response) {
		logger.info("进入请求 getAppWaybillCZMInfos ");
		// 返回对象
		QueryAppWaybillDetailResponse queryAppWaybillDetailResponse = new QueryAppWaybillDetailResponse();
		try {

			if (payload != null) {
				// 运单号集合
				List<String> waybillList = payload.getWaybillNo();
				
				//存放查询出的运单号集合
				List<String> waybillNos = new ArrayList<String>();
				
				// 运单信息集合
				List<WaybillDetailVo> waybillDetailVoList = getWaybillDetails(waybillList, waybillNos);
				
				//把两次查询到的结果合并成一个结果集
				List<WaybillDetailVo> resultList = new ArrayList<WaybillDetailVo>();
				//存储 运单号 和 是否子母单标示
				ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<String, String>();
				if(waybillNos != null && waybillNos.size()>0){
					logger.info("根据有效运单集合查询所有子母单信息");
					//根据有效运单号集合判断是否子母件以及该单号关联的其他子母件
					List<WaybillRelateDetailEntity> waybillRelateDetailEntities = waybillRelateDetailEntityService.queryWaybillRelateDetailsByWaybillNos(waybillNos, 0, 0, false);
					
					List<String> lists = new ArrayList<String>();
					for(WaybillRelateDetailEntity entity : waybillRelateDetailEntities){
						hashMap.put(entity.getWaybillNo(), entity.getIsPicPackage());
						//查询到的子母单单号如果没有信息详情，则要再次进行查询获取信息详情
						if(!waybillNos.contains(entity.getWaybillNo())){
							lists.add(entity.getWaybillNo());
						}
					}
					//再次进行查询获取其他 子、母单 信息详情
					List<WaybillDetailVo> waybillDetailVos = getWaybillDetails(lists, waybillNos);
					//把两次查询到的结果合并成一个结果集
					/*if(CollectionUtils.isNotEmpty(waybillDetailVos) && CollectionUtils.isNotEmpty(waybillDetailVoList)){
						resultList = ListUtils.union(waybillDetailVos, waybillDetailVoList);
					}else if(CollectionUtils.isNotEmpty(waybillDetailVos)){
//						waybillDetailVoList.addAll(waybillDetailVos);
						resultList = waybillDetailVos ;
					}else if(CollectionUtils.isNotEmpty(waybillDetailVoList)){
						resultList = waybillDetailVoList ;
					}*/
					 if(CollectionUtils.isNotEmpty(waybillDetailVos)){
							waybillDetailVoList.addAll(waybillDetailVos);
						}
				}else{
					logger.info("没有找到其他的运单号信息") ;
					resultList = waybillDetailVoList ;
				}
//				ListUtils.intersection(list1, list2)
			
				if(CollectionUtils.isNotEmpty(waybillDetailVoList)){
					for(WaybillDetailVo waybillDetailVo : waybillDetailVoList){
						if(hashMap.containsKey(waybillDetailVo.getNumber())){
							//子母件集合中没有该运单信息 则再次判断是子单或母单
							if("Y".equals(hashMap.get(waybillDetailVo.getNumber()))){
								waybillDetailVo.setWaybillType(WaybillConstants.WAYBILL_PARENT);
							}else{
								waybillDetailVo.setWaybillType(WaybillConstants.WAYBILL_CHILD);
							};
						}else{//子母件集合中没有该运单信息 则是正常件
							waybillDetailVo.setWaybillType(WaybillConstants.WAYBILL_NORMAL);
						}
					}
					queryAppWaybillDetailResponse.setCount(waybillDetailVoList.size());
					queryAppWaybillDetailResponse.setWayBillDetailVoList(waybillDetailVoList);
				}
				
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				return queryAppWaybillDetailResponse;
			} else {
				logger.warn("传入的参数为空");
				response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
				queryAppWaybillDetailResponse.setSuccess(FossConstants.NO);
				queryAppWaybillDetailResponse.setErrorMsg("查询参数不能为空");
				return queryAppWaybillDetailResponse;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			queryAppWaybillDetailResponse.setSuccess(FossConstants.NO);
			queryAppWaybillDetailResponse.setErrorMsg(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			return queryAppWaybillDetailResponse;
		}
	}

	/**
	 * 根据运单号集合查询运单信息详情
	 * @param waybillList 运单号集合
	 * @param waybillNos 有效运单号集合，待返回后使用
	 * @return 运单信息
	 * @author 272311-sangwenhao
	 * 2015.09.08
	 */
	private List<WaybillDetailVo> getWaybillDetails(List<String> waybillList,
			List<String> waybillNos) {
		logger.info("进入getWaybillDetails") ;
		// 运单信息集合
		List<WaybillDetailVo> waybillDetailVoList = new ArrayList<WaybillDetailVo>();
		// 运单费用信息集合
		List<WaybillCostInfo> waybillCostInfos = new ArrayList<WaybillCostInfo>();
		
		try {
		logger.info("根据运单号集合开始查询运单信息详情") ;
		//查询出运单信息集合
		List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
		if(waybillInfoDtoList!=null && waybillInfoDtoList.size()>0){
		for (WaybillInfoDto waybillInfo : waybillInfoDtoList) {
			// 运单信息
			WaybillDetailVo wayBillDetail = new WaybillDetailVo();
			if (waybillInfo.getWaybillChargeCostDto() != null) {
				for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto()) {
					// 复制费用信息
					WaybillCostInfo waybillCostInfo = new WaybillCostInfo();
					waybillCostInfo.setCostType(waybillChargeCostDto
							.getCostType());
					waybillCostInfo.setCostName(waybillChargeCostDto
							.getCostName());
					waybillCostInfo.setCostMoney(waybillChargeCostDto
							.getCostMoney());
					waybillCostInfos.add(waybillCostInfo);
				}
			}
//			wayBillDetail.getWaybillCostInfos().addAll(waybillCostInfos);
			wayBillDetail.setWaybillCostInfos(waybillCostInfos);
			// 设置运单号
			wayBillDetail.setNumber(waybillInfo.getWaybillNo());
			waybillNos.add(waybillInfo.getWaybillNo());
			/**
			 * KDTE-5323 快递单号待补录状态时FOSS与CRM系统信息不一致
			 */
			// 设置运输类型(产品)
			wayBillDetail.setTranType(waybillExpressService
						.getTransTypeByLevel3ProductCode(waybillInfo
								.getProductCode()));
			// 设置运输性质
			wayBillDetail.setTranProperty(waybillInfo.getProductCode());
			// 发货联系人
			wayBillDetail.setSender(waybillInfo
					.getDeliveryCustomerName());
			// 发货人电话
			wayBillDetail.setSenderPhone(waybillInfo
					.getDeliveryCustomerPhone());
			// 发货客户手机
			wayBillDetail.setSenderMobile(waybillInfo
					.getDeliveryCustomerMobilephone());
			// 始发站
			wayBillDetail.setDeparture(waybillInfo
					.getDeliveryCustomerCityName());
			// 发货人地址
			wayBillDetail.setSenderAddress(waybillInfo
					.getDeliveryCustomerAddress());
			// 发货人地址备注
			wayBillDetail.setSenderAddressNote(waybillInfo
					.getDeliveryCustomerAddressNote());
			// 收货人
			wayBillDetail.setConsignee(waybillInfo
					.getReceiveCustomerName());
			// 收货人电话
			wayBillDetail.setConsigneePhone(waybillInfo
					.getReceiveCustomerPhone());
			// 收货人手机
			wayBillDetail.setConsigneeMobile(waybillInfo
					.getReceiveCustomerMobilephone());
			// 目的站
			wayBillDetail
					.setDestination(waybillInfo.getTargetOrgCode());
			// 收货人地址
			wayBillDetail.setConsigneeAddress(waybillInfo
					.getReceiveCustomerAddress());
			// 收货人地址备注
			wayBillDetail.setConsigneeAddressNote(waybillInfo
					.getReceiveCustomerAddressNote());
			// 货物名称
			wayBillDetail.setGoodName(waybillInfo.getGoodsName());
			// 件数
			wayBillDetail.setPieces(waybillInfo.getGoodsQtyTotal());
			// 重量
			wayBillDetail.setWeight(Float.parseFloat(waybillInfo
					.getGoodsWeightTotal().toString()));
			// 体积
			wayBillDetail.setCubage(Float.parseFloat(waybillInfo
					.getGoodsVolumeTotal().toString()));
			// 总费用
			wayBillDetail.setTotalCharge(waybillInfo.getTotalFee());
			// 付款方式
			wayBillDetail.setPayment(waybillInfo.getPaidMethod());
			// 预付金额
			wayBillDetail.setPreCharge(waybillInfo.getPrePayAmount());
			// 到付金额
			wayBillDetail.setArriveCharge(waybillInfo.getToPayAmount());
			// 代收货款类型
			wayBillDetail.setRefundType(waybillInfo.getRefundType());
			// 代收货款
			wayBillDetail.setRefund(waybillInfo.getCodAmount());
			// 代收货款手续费
			wayBillDetail.setRefundFee(waybillInfo.getCodFee());
			// 开单提货方式
			wayBillDetail.setDeliveryType(waybillInfo
					.getReceiveMethod());
			// 接货费
			wayBillDetail.setConsignCharge(waybillInfo.getPickupFee());
			// 送货费
			wayBillDetail.setDeliveryCharge(waybillInfo
					.getDeliveryGoodsFee());
			// 包装费
			wayBillDetail.setPickCharge(waybillInfo.getPackageFee());
			// 装卸费
			wayBillDetail.setLaborRebate(waybillInfo.getServiceFee());
			// 公布价运费
			wayBillDetail.setPublishCharge(waybillInfo
					.getTransportFee());
			// 出发部门名称
			wayBillDetail.setDepartureDeptName(waybillInfo
					.getDepartureDeptName());
			// 出发部门标杆编码
			wayBillDetail.setDepartureDeptNumber(waybillInfo
					.getDepartureDeptNumber());
			// 出发部门地址
			wayBillDetail.setDepartureDeptAddr(waybillInfo
					.getDepartureDeptAddr());
			// 出发部门电话
			wayBillDetail.setDepartureDeptPhone(waybillInfo
					.getDepartureDeptPhone());
			// 出发部门传真
			wayBillDetail.setDepartureDeptFax(waybillInfo
					.getDepartureDeptFax());
			// 到达网点名称
			wayBillDetail.setLadingStationName(waybillInfo
					.getLadingStationName());
			// 到达网点标杆编码
			wayBillDetail.setLadingStationNumber(waybillInfo
					.getLadingStationNumber());
			// 到达网点地址
			wayBillDetail.setLadingStationAddr(waybillInfo
					.getLadingStationAddr());
			// 到达网点电话
			wayBillDetail.setLadingStationPhone(waybillInfo
					.getLadingStationPhone());
			// 到达网点传真
			wayBillDetail.setLadingStationFax(waybillInfo
					.getLadingStationFax());
			// 是否签收
			wayBillDetail.setIsSigned(waybillInfo.isSigned());
			// 是否正常签收
			wayBillDetail.setIsNormalSigned(waybillInfo
					.isNormalSigned());
			// 签收录入人
			wayBillDetail.setSignRecorderId(waybillInfo
					.getDeliverymanName());
			// 签收时间
			wayBillDetail.setSignedDate(waybillInfo.getSignTime());
			// 第一次签收时间
			wayBillDetail.setFirstSignedDate(waybillInfo
					.getFirstSignTime());
			// 签收备注
			wayBillDetail.setSignedDesc(waybillInfo.getSignNote());
			
			//update by foss-231434-bieyexiong 非异常弃货，直接发送签收信息
			if(!ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(waybillInfo.getSignSituation())){
				// 运单状态
				wayBillDetail.setOrderState(waybillInfo.getActive());
			}else if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillInfo.getProductCode(),null)){
				//(需求DN201603140026只针对零担) 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
				List<BillPayableEntity> billPayables = billPayableService.queryByWaybillNosAndByBillTypes(
						Arrays.asList(waybillInfo.getWaybillNo()),
						Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
				//异常弃货，且有上报理赔，才发送签收信息
				if(CollectionUtils.isNotEmpty(billPayables)){
					// 运单状态
					wayBillDetail.setOrderState(waybillInfo.getActive());
				}
			}
			
			// 订单号
			wayBillDetail.setOrderNumber(waybillInfo.getOrderNo());
			// 保价声明
			wayBillDetail.setInsuranceValue(waybillInfo
					.getInsuranceAmount());
			// 保价手续费
			wayBillDetail.setInsurance(waybillInfo.getInsuranceFee());
			// 货物包装
			wayBillDetail.setPacking(waybillInfo.getGoodsPackage());
			// 其它费用
			wayBillDetail.setOtherPayment(waybillInfo.getOtherFee());
			// 托运备注
			wayBillDetail.setTranDesc(waybillInfo.getOuterNotes());
			// 发货客户编码
			wayBillDetail.setSenderNumber(waybillInfo
					.getDeliveryCustomerCode());
			// 收货客户编码
			wayBillDetail.setConsigneeNumber(waybillInfo
					.getReceiveCustomerCode());
			// 是否已结款
			wayBillDetail.setIsClear(waybillInfo.getSettleStatus());
			// 返单类型
			wayBillDetail.setSignBackType(waybillInfo
					.getReturnBillType());
			// 储运事项
			wayBillDetail.setTransNotice(waybillInfo
					.getTransportationRemark());
			// 发货时间
			wayBillDetail.setSendTime(waybillInfo.getBillTime());
			// 收货部门名称
			wayBillDetail.setReceiveDeptName(waybillInfo
					.getReceiveOrgName());
			// 收货部门标杆代码
			wayBillDetail.setReceiveDeptNumber(waybillInfo
					.getReceiveOrgNumber());
			// 配载部门标杆编码
			wayBillDetail
					.setStowageDept(waybillInfo.getLoadOrgNumber());
			// 发货人城市编码
			wayBillDetail.setSenderCityCode(waybillInfo
					.getDeliveryCustomerCityCode1());
			// 发货人城市名称
			wayBillDetail.setSenderCityName(waybillInfo
					.getDeliveryCustomerCityName1());
			// 发货人省份编码
			wayBillDetail.setSenderProvinceCode(waybillInfo
					.getDeliveryCustomerProvCode());
			// 发货人省份名称
			wayBillDetail.setSenderProvinceName(waybillInfo
					.getDeliveryCustomerProvName());
			// 收货人城市编码
			wayBillDetail.setConsigneeCityCode(waybillInfo
					.getReceiveCustomerCityCode());
			// 收货人城市名称
			wayBillDetail.setConsigneeCityName(waybillInfo
					.getReceiveCustomerCityName());
			// 收货人省份编码
			wayBillDetail.setConsigneeProvinceCode(waybillInfo
					.getReceiveCustomerProvCode());
			// 收货人省份名称
			wayBillDetail.setConsigneeProvinceName(waybillInfo
					.getReceiveCustomerProvName());
			// 是否上门接货
			wayBillDetail.setIsDoorToDoorPick(waybillInfo.isPickup());
			// 短信通知状态
			wayBillDetail.setSmsNoticeResult(waybillInfo
					.getNotificationResult());
			// 签收单返回方式
			wayBillDetail.setSignBillBackWay(waybillInfo
					.getReturnBillType());

			// 小件 新加的逻辑----------------------
			// 运单所属快递大区编码
			wayBillDetail.setExDepartureRegionNubmer(waybillInfo
					.getDistrictCode());
			// 运单所属快递大区名称
			wayBillDetail.setExDepartureRegionName(waybillInfo
					.getDistrictName());
			// 运单所属快递大区标杆编码
			wayBillDetail
					.setExDepartureRegionStandardNubmer(waybillInfo
							.getDistrictUnifiedCode());

			// 运单所属快递大区编码
			wayBillDetail.setExDestinationRegionNubmer(waybillInfo
					.getEndDistrictCode());
			// 运单所属快递大区名称
			wayBillDetail.setExDestinationRegionName(waybillInfo
					.getEndDistrictName());
			// 运单所属快递大区标杆编码
			wayBillDetail
					.setExDestinationRegionStandardNubmer(waybillInfo
							.getEndDistrictUnifiedCode());

			// 快递员CODE-出发
			wayBillDetail.setExDepartureCourierNumber(waybillInfo
					.getStartExpressEmpCode());
			// 快递员名称-出发
			wayBillDetail.setExDepartureCourierName(waybillInfo
					.getStartExpressEmpName());
			// 快递点部CODE-出发
			wayBillDetail.setExDepartureDeptNumber(waybillInfo
					.getStartExpressOrgCode());
			// 快递点部标杆编码-出发
			wayBillDetail.setExDepartureDeptStandardNumber(waybillInfo
					.getStartExpressUnfiedCode());
			// 快递点部名称-出发
			wayBillDetail.setExDepartureDeptName(waybillInfo
					.getStartExpressOrgName());

			// 快递员CODE-到达
			wayBillDetail.setExDestinationCourierNumber(waybillInfo
					.getEndExpressEmpCode());

			// 快递员名称-到达
			wayBillDetail.setExDestinationCourierName(waybillInfo
					.getEndExpressEmpName());
			// 快递点部CODE-到达
			wayBillDetail.setExDestinationDeptNumber(waybillInfo
					.getEndExpressOrgCode());

			// 快递点部名称-到达
			wayBillDetail.setExDestinationDeptName(waybillInfo
					.getEndExpressOrgName());

			// 快递点部标杆编码-到达
			wayBillDetail
					.setExDestinationDeptStandardNumber(waybillInfo
							.getEndExpressUnfiedCode());
			waybillDetailVoList.add(wayBillDetail);
			logger.info("-- 运单查询---- "+ ReflectionToStringBuilder.toString(wayBillDetail));
		  }
		 }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null ;
		}
		logger.info("根据运单号集合查询运单信息详情查询处理完毕 返回") ;
		return waybillDetailVoList;
	}
	
	/**
	* 理赔接口
	* 
	* @Method: queryWaybillDetailForSOC 
	* @param param
	* @param response
	* @return WaybillDetailForSOCResponse
	* @author WangZengming
	* @date 2015-9-2 下午1:46:35
	* @see
	*/
	@RequestMapping(value = "/soc/queryWaybillDetailForSOC")
	@ResponseBody
	public WaybillDetailForSOCResponse queryWaybillDetailForSOC(@RequestBody WaybillDetailForSOCRequest param, HttpServletResponse response) {
		logger.info("进入请求 queryWaybillDetailForSOC ");
		try {
			List<String> waybillNoList = param.getWaybillNoList();
			List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfoForSOC(waybillNoList);
			/*//add by 306486 2016年6月22日
			if (CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
				// 签收信息
				WaybillSignResultEntity entity = new WaybillSignResultEntity();
				for (WaybillInfoDto dto : waybillInfoDtoList) {
					//判断签收状态 是否为空
					if (StringUtil.isNotBlank(dto.getSignSituation())) {
						// 异常签收
						if (!dto.getSignSituation().equals(WaybillConstants.ACCOUNT_NORMAL_SIGN)) {
							// 运单号
							entity.setWaybillNo(dto.getWaybillNo());
							// 调用结算接口,FOSS结算断货物状态为“异常签收”后，锁定签收状态，不允许更改
							claimStatusService.setStatus(entity);
						} 
					}
				}
			}*/
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return getWaybillDetailForSOCResponse(waybillInfoDtoList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	private WaybillDetailForSOCResponse getWaybillDetailForSOCResponse(
			List<WaybillInfoDto> waybillInfoDtoList) {
		WaybillDetailForSOCResponse waybillDetailForSOCResponse = new WaybillDetailForSOCResponse();
		//运单信息集合
		List<WaybillDetail> waybillDetailForSOCVoList = new ArrayList<WaybillDetail> ();
		//运单费用信息集合
//		List<WaybillCostInfoVo> waybillCostInfos = new ArrayList<WaybillCostInfoVo> ();
		//运单信息
		WaybillDetail waybillDetailForSOCVo = null;
		//运单费用信息
//		WaybillCostInfoVo waybillCostInfo = null;
		if(CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
			List<String> returnWaybillNoList = null;
			for(WaybillInfoDto infoDto : waybillInfoDtoList) {
				if(StringUtils.isNotEmpty(infoDto.getWaybillNo())) {
					if(StringUtils.isEmpty(infoDto.getOriginalWaybillNo())) {
						WaybillExpressEntity  entity = waybillExpressService.queryWaybillByOriginalWaybillNo(infoDto.getWaybillNo(), 
								WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
						if(entity != null && StringUtils.isNotEmpty(entity.getOriginalWaybillNo())) {
							infoDto.setOriginalWaybillNo(entity.getWaybillNo());
							returnWaybillNoList = new ArrayList<String>();
							returnWaybillNoList.add(entity.getWaybillNo());
							infoDto.setReturnWaybillNoList(returnWaybillNoList);
						}
					}
				}
				
			}
			//运单信息
			for(WaybillInfoDto waybillInfo : waybillInfoDtoList){
				waybillDetailForSOCVo = new WaybillDetail();
//				if(waybillInfo.getWaybillChargeCostDto() != null)
//				{
//					for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
//					{
//						//复制费用信息
//						waybillCostInfo = new WaybillCostInfoVo();
//						waybillCostInfo.setCostType(waybillChargeCostDto.getCostType());
//						waybillCostInfo.setCostName(waybillChargeCostDto.getCostName());
//						waybillCostInfo.setCostMoney(waybillChargeCostDto.getCostMoney());
//						waybillCostInfos.add(waybillCostInfo);
//					}
//				}
//				waybillDetailForSOCVo.setWaybillCostInfoForOffical(waybillCostInfos);
				//设置运单号
				waybillDetailForSOCVo.setNumber(waybillInfo.getWaybillNo());
				/**
				 * KDTE-5323 快递单号待补录状态时FOSS与CRM系统信息不一致
				 */
				//设置运输类型(产品)
				if(WaybillConstants.directDetermineIsExpressByProductCode(waybillInfo.getProductCode())){
					waybillDetailForSOCVo.setTranType(ExpWaybillConstants.TRANS_EXPRESS);
				}else{
					waybillDetailForSOCVo.setTranType(waybillInfo.getTransportType());
				}
				//设置运输性质
				waybillDetailForSOCVo.setTranProperty(waybillInfo.getProductCode());
				//发货联系人
				waybillDetailForSOCVo.setSender(waybillInfo.getDeliveryCustomerName());
				// 发货客户为大客户标示，返回给官网
				 
				waybillDetailForSOCVo.setIsBigDeliverCustomer(waybillInfo.getIsBigDeliverCustomer());
				// 返货类型
				 
				waybillDetailForSOCVo.setReturnType(waybillInfo.getReturnType());
				// 返货:原运单号
				 
				waybillDetailForSOCVo.setOriginalWaybillNo(waybillInfo.getOriginalWaybillNo());
				//发货人电话
				waybillDetailForSOCVo.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
				//发货客户手机
				waybillDetailForSOCVo.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
				//始发站
				waybillDetailForSOCVo.setDeparture(waybillInfo.getDeliveryCustomerCityName());
				//发货人地址
				waybillDetailForSOCVo.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
				//发货人地址备注
				waybillDetailForSOCVo.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
				//收货人
				waybillDetailForSOCVo.setConsignee(waybillInfo.getReceiveCustomerName());
				//收货人电话
				waybillDetailForSOCVo.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
				//收货人手机
				waybillDetailForSOCVo.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
				//目的站
				waybillDetailForSOCVo.setDestination(waybillInfo.getTargetOrgCode());
				//收货人地址
				waybillDetailForSOCVo.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
				//收货地址备注
				waybillDetailForSOCVo.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
				//货物名称
				waybillDetailForSOCVo.setGoodName(waybillInfo.getGoodsName());
				//件数
				waybillDetailForSOCVo.setPieces(waybillInfo.getGoodsQtyTotal());
				//重量
				waybillDetailForSOCVo.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
				//体积
				waybillDetailForSOCVo.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
				//总费用
				waybillDetailForSOCVo.setTotalCharge(waybillInfo.getTotalFee());
				//付款方式
				waybillDetailForSOCVo.setPayment(waybillInfo.getPaidMethod());
				//预付金额
				waybillDetailForSOCVo.setPreCharge(waybillInfo.getPrePayAmount());
				//到付金额
				waybillDetailForSOCVo.setArriveCharge(waybillInfo.getToPayAmount());
				//代收货款类型
				waybillDetailForSOCVo.setRefundType(waybillInfo.getRefundType());
				//代收货款
				waybillDetailForSOCVo.setRefund(waybillInfo.getCodAmount());
				//代收货款手续费
				waybillDetailForSOCVo.setRefundFee(waybillInfo.getCodFee());
				//开单提货方式
				waybillDetailForSOCVo.setDeliveryType(waybillInfo.getReceiveMethod());
				//接货费
				waybillDetailForSOCVo.setConsignCharge(waybillInfo.getPickupFee());
				//送货费
				waybillDetailForSOCVo.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
				//包装费
				waybillDetailForSOCVo.setPickCharge(waybillInfo.getPackageFee());
				//装卸费
				waybillDetailForSOCVo.setLaborRebate(waybillInfo.getServiceFee());
				//公布价运费
				waybillDetailForSOCVo.setPublishCharge(waybillInfo.getTransportFee());
				//出发部门名称
				waybillDetailForSOCVo.setDepartureDeptName(waybillInfo.getDepartureDeptName());
				//出发部门标杆编码
				waybillDetailForSOCVo.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
				//出发部门地址
				waybillDetailForSOCVo.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
				//出发部门电话
				waybillDetailForSOCVo.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
				//出发部门传真
				waybillDetailForSOCVo.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
				//到达网点名称
				waybillDetailForSOCVo.setLadingStationName(waybillInfo.getLadingStationName());
				//到达网点标杆编码
				waybillDetailForSOCVo.setLadingStationNumber(waybillInfo.getLadingStationNumber());
				//到达网点地址
				waybillDetailForSOCVo.setLadingStationAddr(waybillInfo.getLadingStationAddr());
				//到达网点电话
				waybillDetailForSOCVo.setLadingStationPhone(waybillInfo.getLadingStationPhone());
				//到达网点传真
				waybillDetailForSOCVo.setLadingStationFax(waybillInfo.getLadingStationFax());
				//是否签收
				waybillDetailForSOCVo.setIsSigned(waybillInfo.isSigned());
				//是否正常签收
				waybillDetailForSOCVo.setIsNormalSigned(waybillInfo.isNormalSigned());
				//是否异常签收
				waybillDetailForSOCVo.setUuormaSign(waybillInfo.isAbnormalSigned());
				//是否弃货签收
				waybillDetailForSOCVo.setMissingSign(waybillInfo.isAbandonGoodsSigned());
				//子件单号集合
				waybillDetailForSOCVo.setChildWaybillNoList(waybillInfo.getChildWaybillNoList());
				//返货单号集合
				waybillDetailForSOCVo.setReturnWaybillNoList(waybillInfo.getReturnWaybillNoList());
				//签收录入人
				waybillDetailForSOCVo.setSignRecorderId(waybillInfo.getDeliverymanName());
				//签收时间
				waybillDetailForSOCVo.setSignedDate(waybillInfo.getSignTime());
				//第一次签收时间
				waybillDetailForSOCVo.setFirstSignedDate(waybillInfo.getFirstSignTime());
				//签收备注
				waybillDetailForSOCVo.setSignedDesc(waybillInfo.getSignNote());
				//订单号
				waybillDetailForSOCVo.setOrderNumber(waybillInfo.getOrderNo());
				//保价声明
				waybillDetailForSOCVo.setInsuranceValue(waybillInfo.getInsuranceAmount());
				//保价手续费
				waybillDetailForSOCVo.setInsurance(waybillInfo.getInsuranceFee());
				//货物包装
				waybillDetailForSOCVo.setPacking(waybillInfo.getGoodsPackage());
				//运单状态
				waybillDetailForSOCVo.setOrderState(waybillInfo.getActive());
				//其它费用
				waybillDetailForSOCVo.setOtherPayment(waybillInfo.getOtherFee());
				//托运备注
				waybillDetailForSOCVo.setTranDesc(waybillInfo.getOuterNotes());
				//发货客户编码
				waybillDetailForSOCVo.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
				//收货客户编码
				waybillDetailForSOCVo.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
				//是否已结款
				waybillDetailForSOCVo.setIsClear(waybillInfo.getSettleStatus());
				//返单类型
				waybillDetailForSOCVo.setSignBackType(waybillInfo.getReturnBillType());
				//储运事项
				waybillDetailForSOCVo.setTransNotice(waybillInfo.getTransportationRemark());
				//发货时间
				waybillDetailForSOCVo.setSendTime(waybillInfo.getBillTime());
				//收货部门名称
				waybillDetailForSOCVo.setReceiveDeptName(waybillInfo.getReceiveOrgName());
				//收货部门标杆代码
				waybillDetailForSOCVo.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
				//配载部门标杆编码
				waybillDetailForSOCVo.setStowageDept(waybillInfo.getLoadOrgNumber());
				//发货人城市编码
				waybillDetailForSOCVo.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
				//发货人城市名称
				waybillDetailForSOCVo.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
				//发货人省份编码
				waybillDetailForSOCVo.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
				//发货人省份名称
				waybillDetailForSOCVo.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
				//收货人城市编码
				waybillDetailForSOCVo.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
				//收货人城市名称
				waybillDetailForSOCVo.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
				//收货人省份编码
				waybillDetailForSOCVo.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
				//收货人省份名称
				waybillDetailForSOCVo.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
				//是否上门接货
				waybillDetailForSOCVo.setIsDoorToDoorPick(waybillInfo.isPickup());
				//短信通知状态
				waybillDetailForSOCVo.setSmsNoticeResult(waybillInfo.getNotificationResult());
				//签收单返回方式
				waybillDetailForSOCVo.setSignBillBackWay(waybillInfo.getReturnBillType());
				
				
				//小件 新加的逻辑----------------------
				//运单所属快递大区编码
				waybillDetailForSOCVo.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
				//运单所属快递大区名称
				waybillDetailForSOCVo.setExDepartureRegionName(waybillInfo.getDistrictName());
				//运单所属快递大区标杆编码
				waybillDetailForSOCVo.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());
				
				//运单所属快递大区编码
				waybillDetailForSOCVo.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
				//运单所属快递大区名称
				waybillDetailForSOCVo.setExDestinationRegionName(waybillInfo.getEndDistrictName());
				//运单所属快递大区标杆编码
				waybillDetailForSOCVo.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
				//是否子母件
				waybillDetailForSOCVo.setIsPicPackage(waybillInfo.isParentChildWaybill());
				//母件单号
				waybillDetailForSOCVo.setParentWaybillNo(waybillInfo.getParentWaybillNo());
				//快递员CODE-出发
				waybillDetailForSOCVo.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
				//快递员名称-出发
				waybillDetailForSOCVo.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
				//快递点部CODE-出发
				waybillDetailForSOCVo.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
				//快递点部标杆编码-出发
				waybillDetailForSOCVo.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
				//快递点部名称-出发
				waybillDetailForSOCVo.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
				//快递员CODE-到达
				waybillDetailForSOCVo.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
				//快递员名称-到达
				waybillDetailForSOCVo.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
				//快递点部CODE-到达
				waybillDetailForSOCVo.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
				//快递点部名称-到达
				waybillDetailForSOCVo.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());
				
				//快递点部标杆编码-到达
				waybillDetailForSOCVo.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
				
				waybillDetailForSOCVoList.add(waybillDetailForSOCVo);
				logger.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(waybillDetailForSOCVo));
			}
		}
		waybillDetailForSOCResponse.setWaybillDetailForOfficialList(waybillDetailForSOCVoList);
		return waybillDetailForSOCResponse;
	}
}
