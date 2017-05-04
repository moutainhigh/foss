package com.deppon.foss.module.base.querying.server.service.impl;

import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.querying.server.service.IRestfulService;
import com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService;
import com.deppon.foss.module.base.querying.shared.dto.DeliveryConfirmResponse;
import com.deppon.foss.module.base.querying.shared.dto.TrackRecordResponse;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;
import com.deppon.foss.module.transfer.partialline.api.server.service.ISalesdeptDeliveryprocService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.util.define.ESBHeaderConstant;


/**
 *  Restful服务端接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-9-10 下午2:02:52,content:TODO </p>
 * @author 232607 
 * @date 2015-9-10 下午2:02:52
 * @since
 * @version
 */
public class RestfulService  implements IRestfulService{
	
	private static final long serialVersionUID = 1L;
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(RestfulService.class);
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	private ISalesdeptDeliveryprocService salesDeptDeliveryProcService;
	
	private IWayBillRelevanceQueryService wayBillRelevanceQueryService;

	public void setSalesDeptDeliveryProcService(
			ISalesdeptDeliveryprocService salesDeptDeliveryProcService) {
		this.salesDeptDeliveryProcService = salesDeptDeliveryProcService;
	}


	public void setWayBillRelevanceQueryService(
			IWayBillRelevanceQueryService wayBillRelevanceQueryService) {
		this.wayBillRelevanceQueryService = wayBillRelevanceQueryService;
	}


	/** 
	 * <p>交货确认状态查询，restful服务端接口，DOP用此接口进行交货确认状态的查询（用于家装项目）
	 * 		  查询条件为运单号，返回字段为交货确认状态（交货确认为Y，取消交货确认为N）</p> 
	 * @author 232607 
	 * @date 2015-9-10 下午2:01:17
	 * @param json
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IQueryOrgAttitudeService#queryOrgAttribute(java.lang.String)
	 */
	@Override
	public String queryDeliveryConfirm(String waybillNo) {
		LOGGER.info("------------------------交货确认查询接口begin----------------------------");
		response.setCharacterEncoding("utf-8");
		response.setHeader("ESB-ResultCode", "1");
		DeliveryConfirmResponse dto = new DeliveryConfirmResponse();
		//交货状态默认返回true，即DOP不可更改运单信息，包含情况为：未传运单号、该运单无交货状态、未知异常。
		dto.setDeliveryConfirmStatus("Y");
		try {
			//DOP直接传递运单号过来，而不是json，所以不需要做转换。
			if(StringUtils.isBlank(waybillNo)){
				dto.setSuccess(false);
				dto.setMessage("请求中的运单号为空");
				String res=JSON.toJSONString(dto);
				return res;
			}
			
			//通过运单号查询交货确认状态并校验
			SalesdeptDeliveryEntity entity=salesDeptDeliveryProcService.salesDeptDeliveryQueryByWayBillNo(waybillNo);
			if(entity==null){
				//FOSS无该运单时Success为true，此设定为DOP要求，DOP拿这个字段来判断
				dto.setSuccess(true);
				dto.setMessage("运单号"+waybillNo+"在FOSS中未交货确认");
				dto.setDeliveryConfirmStatus("N");
				String res=JSON.toJSONString(dto);
				return res;
			}
			//运单存在但没有交货状态
			if(StringUtils.isEmpty(entity.getWaystaus())){
				//运单无交货状态时Success为true，此设定为DOP要求，DOP拿这个字段来判断
				dto.setSuccess(true);
				dto.setMessage("运单号"+waybillNo+"在FOSS中存在但无交货确认状态");
				String res=JSON.toJSONString(dto);
				return res;
			}
			//通过所有校验后，存值。（DE_CONFIRM为交货确认，NON_DE_CONFIRM为未确认或取消交货确认）
			dto.setDeliveryConfirmStatus(entity.getWaystaus().equals("DE_CONFIRM")?"Y":"N");
			dto.setConfirmTime(entity.getConfirmationTime());
			dto.setSuccess(true);
			dto.setMessage("查询成功，状态已返回。");
			LOGGER.info("------------------------交货确认查询接口end----------------------------");
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setMessage("数据异常");
		}
		String res=JSON.toJSONString(dto);
		return res;
	}
	
	private final int RESULT_TYPE_PARAMETER_NULL = 1;
	private final int RESULT_TYPE_SYS_ERROR = 2;
	private final int RESULT_TYPE_RESULT_NULL = 3;
	private final int RESULT_TYPE_SUCCESS = 4;
	
	/**
	 * 根据运单号查询跟踪记录
	 * @author 310854
	 * @date 2016-5-17
	 */
	public String queryWayBilllTrackRecords(String json){
		LOGGER.info("------------------------根据运单号查询跟踪记录,查询接口begin----------------------------");
		response.setCharacterEncoding("utf-8");
		//设置消息头
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ECS_RECEIVE_WAYBILL_TRACK");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		
		TrackRecordResponse dto = new TrackRecordResponse();
		try{
			if(StringUtils.isEmpty(json)){
				dto.setResultType(RESULT_TYPE_PARAMETER_NULL);
				dto.setMessage("请求中的运单号为空");
				String res=JSON.toJSONString(dto);
				return res;
			}
			String jsonStr = URLDecoder.decode(json.replace("\ufeff",""), "utf-8");
			JSONObject jsObject = JSONObject.fromObject(jsonStr);
			if(!jsObject.containsKey("waybillNo")){
				dto.setResultType(RESULT_TYPE_PARAMETER_NULL);
				dto.setMessage("请求中的运单号为空");
				String res=JSON.toJSONString(dto);
				return res;
			}
			TrackRecordEntity trackRecordEntity = new TrackRecordEntity();
		    trackRecordEntity.setWaybillNo(jsObject.getString("waybillNo"));
			WaybillVo vo = wayBillRelevanceQueryService.queryTrackRecords(trackRecordEntity);
			
			if(null == vo || CollectionUtils.isEmpty(vo.getTrackRecordList())){
				dto.setResultType(RESULT_TYPE_RESULT_NULL);
				dto.setMessage("该运单号的跟踪记录为空！");
				String res=JSON.toJSONString(dto);
				return res;
			}
		//	JSONArray jsonArray = JSONArray.fromObject(vo.getTrackRecordList());
			dto.setResultType(RESULT_TYPE_SUCCESS);
			dto.setRecords(vo.getTrackRecordList());
			dto.setMessage("查询成功，状态已返回。");
		} catch (Exception e) {
			dto.setResultType(RESULT_TYPE_SYS_ERROR);
			dto.setMessage("数据异常"+e.getMessage());
		}
		
		LOGGER.info("------------------------根据运单号查询跟踪记录,查询接口end----------------------------");
		String res=JSON.toJSONString(dto);
		return res;
	}
}
