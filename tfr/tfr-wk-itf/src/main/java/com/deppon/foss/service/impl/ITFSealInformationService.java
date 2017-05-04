package com.deppon.foss.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.osoa.sca.annotations.Service;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IQueryAirWaybillTrailByWaybillNoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillQueryCreateDeptVO;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.ResponseAirWayBillQueryVO;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.StatusAndTrackRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.StatusAndTrackResponse;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.service.ISealInformationService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ResponseJsonInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealInformationEntity;
import com.deppon.foss.service.IITFSealInformationService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;



@Service
public class ITFSealInformationService implements IITFSealInformationService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	/**
	 * 定义tfr-load模块下面的service层
	 */
	private ISealInformationService sealInformationService;
	
	/**
	 * 定义tfr-airfreight模块下面的service层
	 */
	private IQueryAirWaybillTrailByWaybillNoService queryAirWaybillTrailByWaybillNoService;
	                                                
	/**
	 * 保存日志信息
	 */
	private static Logger LOGGER = LogManager.getLogger(ITFSealInformationService.class);
    
	/**
	 * json格式有问题
	 */
	private static final String JSONFORMATEXCEPTION="JSON 数据格式有误,请核实!";
	
	/**
	 * json数据有误
	 */
	private static final String JSONDATAEXCEPTION="JSON 数据字段有误,或者中文导致乱码,请核实!";

	/**
	 * 操作成功提示信息
	 */
	private static final String OPERATORSUCCESS="请求操作成功!";
	
	/**
	* @description 配合快递传递过来交接单号,获取foss系统的封签号【配合何永东开发】
	* (non-Javadoc) 假设reqMsg=“{"eirNum":"k45353257"}”
	* @see com.deppon.foss.service.IITFSealInformationService#querySealInfo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object querySealInfo(String reqMsg) {
		LOGGER.info("配合快递传递过来交接单号,获取foss系统的封签号："+reqMsg);
		int flag=0;
		String returnInfo="";
		//1.创建结果对象实例
		SealInformationEntity sealInformationEntity=new SealInformationEntity();
        //2.快递传过来的参数判空
		if(reqMsg!=null && !"".equals(reqMsg)){
			try{
				 //3.json字符码解码操作,防止中文乱码产生
				 String jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8"); 
				 JSONObject jsStr = JSONObject.fromObject(jsonStr);
				 if(jsStr.containsKey("eirNum")){
					 Object obj = jsStr.get("eirNum");
                     LOGGER.info("请求参数开始,交接单号参数：【"+obj+"】");
					 List<String> list= (List<String>) sealInformationService.querySealNumByEIRNumList(obj.toString());
					 LOGGER.info("请求查询结束!");
					 sealInformationEntity.setSealNumberList(list);
					 flag=1;
					 returnInfo=OPERATORSUCCESS;
					 LOGGER.info(OPERATORSUCCESS);
				 }else{
					 returnInfo=JSONDATAEXCEPTION;
					 LOGGER.error(JSONDATAEXCEPTION);
				 }
			}catch(net.sf.json.JSONException e){
				returnInfo=JSONFORMATEXCEPTION;
				LOGGER.error(JSONFORMATEXCEPTION,e);
			} catch (UnsupportedEncodingException e) {
				returnInfo=JSONDATAEXCEPTION;
				LOGGER.error(JSONDATAEXCEPTION,e);
			} catch (Exception e) {
				LOGGER.error(JSONDATAEXCEPTION,e);
				returnInfo="JSON格式有误";
			}
			
		}
		//3.以json字符串格式返回给接口调用者
		sealInformationEntity.setIsSuccess(flag);
		sealInformationEntity.setFailMessage(returnInfo);
		JSONObject object = JSONObject.fromObject(sealInformationEntity);
		String info="";
		if(object!=null){
			info = object.toString();
		}
		return info;
	}
	
	
	/**
	* @description 配合(快递)校验快递交接单生成[配合何永东校验]
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public Object checkExpressEIRGenerate(String reqMsg) { 
		LOGGER.info("校验快递交接单生成："+reqMsg);
		String info="";
		String flag=ResponseJsonInfoEntity.SUCCESS;
		Map<String,String> paramMap =null;
		List<String> listCode=null;
		ResponseJsonInfoEntity ent=null;
		//1.校验
		if(reqMsg!=null && !"".equals(reqMsg)){
			//2.解码UTF-8
			try {
				String jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
				JSONObject jsStr = JSONObject.fromObject(jsonStr);
				if(jsStr.containsKey("vehicleNo")&&
				   jsStr.containsKey("deptCode")&&
				   jsStr.containsKey("status")){
				   String vehicleNo = jsStr.getString("vehicleNo");
				   String deptCode  = jsStr.getString("deptCode");
				   String status    = jsStr.getString("status");
				   paramMap = new HashMap<String,String>();
				   paramMap.put("vehicleNo", vehicleNo);
				   paramMap.put("deptCode", deptCode);
				   paramMap.put("status", status);

				   LOGGER.info("校验查询数据开始,请求参数的车牌号：【"+vehicleNo+"】");
				   
				   //校验车辆的车票号是否正确是否合法
				   String isDo = sealInformationService.isDOVehicleNo(vehicleNo);
				   if(isDo.equals("0")){
					   info="车牌号：【"+vehicleNo+"】 该车辆车牌号有误或不是公司自有车辆亦或不是公司外请车辆，长点心请准确输入!";
			    	   flag=ResponseJsonInfoEntity.FAIL;
					   ent = new ResponseJsonInfoEntity(flag,info);
					   return JSONObject.fromObject(ent).toString();
				   }
				   
				   //3.先查询该车辆是否封
				   listCode = sealInformationService.checkCarIsSealService(vehicleNo);
				   LOGGER.info("查询数据结束!");
				   if(listCode!=null && listCode.size()>0){
					   info="该车辆未校验封签，可校验封签部门为:"+listCode.toString(); 
					   flag=ResponseJsonInfoEntity.FAIL;
				   }else{
					   listCode=(List<String>) sealInformationService.checkExpressEIRCarGenerateService(paramMap); 
				       if(listCode!=null && listCode.size()>0){
				    	   info="该车辆已被"+listCode.toString()+"部门使用，请更换车辆!";
				    	   flag=ResponseJsonInfoEntity.FAIL;
				       }else{
				    	   info="成功!【"+vehicleNo+"】该车辆可用!";
				       }
				   }
				}else{
					LOGGER.info(JSONFORMATEXCEPTION);
					info=JSONFORMATEXCEPTION;
					flag=ResponseJsonInfoEntity.JSONFORMATEXCEPTION;
				}
				
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(JSONDATAEXCEPTION,e);
				LOGGER.info("请求JSON参数的字节码格式有问题,请核实!");
				info=JSONDATAEXCEPTION;
				flag=ResponseJsonInfoEntity.JSONRESOLVEEXCEPTION;
			} catch (Exception e) {
				LOGGER.error("请求JSON格式或参数的字节码格式有问题,请核实!",e);
				info="请求JSON格式或参数的字节码格式有问题,请核实!";
				flag=ResponseJsonInfoEntity.JSONRESOLVEEXCEPTION;
			}
			
		}
		//返回结果集信息给调用者快递
		ent = new ResponseJsonInfoEntity(flag,info);
		return JSONObject.fromObject(ent).toString();
	}

	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object tempLeaseCarState(String reqMsg) {

		LOGGER.info("校验校验临时租车标记开始"+reqMsg);
		String info="";
		String flag=ResponseJsonInfoEntity.SUCCESS;
		try {
			String jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
			JSONObject jsStr = JSONObject.fromObject(jsonStr);
			if(jsStr.containsKey("handOverBillNo")){
				 //获取交接单号
				 String handOverBillNo = jsStr.getString("handOverBillNo");
				 LOGGER.info("查询开始：交接单号为 :【"+handOverBillNo+"】");
				 String logo = (String) sealInformationService.tempLeaseCarStateService(handOverBillNo);
				 LOGGER.info("查询结束");
				 if("Y".equals(logo)){
					 flag ="0";
					 info = "操作失败,当前在预提中或者已预提，交接单不可修改!";
				 }else{
					 flag ="1";
					 info = "操作成功,交接单可修改";
				 }
			}else{
				info="操作失败,json中的参数存在有误,请核实!";
				LOGGER.error("json传递参数有误,交接单参数应为'handOverBillNo'");
				flag="2";
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(JSONDATAEXCEPTION);
			info="操作失败,json字节码存在错误,请核实!";
			flag="2";
		} catch (Exception e){
			LOGGER.error(JSONDATAEXCEPTION);
			info="操作失败,json解析格式存在错误,请核实!";
			flag="2";
		}
		
		ResponseJsonInfoEntity ent = new ResponseJsonInfoEntity(flag,info);
		return JSONObject.fromObject(ent).toString();
	}
	
	/**
	* @description 配合(快递)根据正单号查询运单号
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkWaybillNoByPositiveNum(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object checkWaybillNoByPositiveNum(String reqMsg) {
		//记录日志开始
		LOGGER.info("根据正单号查询运单号 ："+reqMsg);
		String info="";
		String flag=ResponseJsonInfoEntity.SUCCESS;
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_RECEIVE_AIR_WAYBILL");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		//统一让ESB响应快递信息
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		try {
			String jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
			JSONObject jsStr = JSONObject.fromObject(jsonStr);
			if(jsStr.containsKey("airWaybillNo")){
				 //获取交接单号
				 String airWaybillNo = jsStr.getString("airWaybillNo");
				 LOGGER.error("查询数据开始,请求正单号参数为 ：【"+airWaybillNo+"】");
				 List<String> waybillNoList =  (List<String>) sealInformationService.queryWaybillNumByPositiveNum(airWaybillNo);
				 LOGGER.error("查询数据结束!");
				 //判断返回结果
				 if(waybillNoList.size()==0){
					 info="操作失败,输入正单号 【"+airWaybillNo+"】查询不出关联的运单或该正单号存在有误,请核实!";
					 flag =ResponseJsonInfoEntity.FAIL;
				 }else{
					 info=waybillNoList.toString();
				 }
			}else{
				info="操作失败,json中的参数存在有误,请核实!";
				LOGGER.error("json传递参数有误,参数应为'airWaybillNo'");
				flag =ResponseJsonInfoEntity.FAIL;
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(JSONDATAEXCEPTION,e);
			info="操作失败,json解析格式存在错误,请核实!";
			flag="2";
		} catch (Exception e){
			LOGGER.error(JSONDATAEXCEPTION);
			info="JSON存在异常!";
			flag="2";
		}
		ResponseJsonInfoEntity ent = new ResponseJsonInfoEntity(flag,info);
		return JSONObject.fromObject(ent).toString();
	}
	
	
	/**
	* @description 配合(快递)根据运单号查询航空正单轨迹
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkWaybillNoByPositiveNum(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object queryEIRAndSFByWaybillNo(String reqMsg) {
		LOGGER.error("根据运单号查询航空正单轨迹,请求信息："+reqMsg);
		//初始化状态 0标示失败 1 标示成功
		String state="0";
		//创建异常实体对象
		StatusAndTrackResponse  statusAndTrackResponse  = new StatusAndTrackResponse();
		StatusAndTrackRequest reqParEntity = new StatusAndTrackRequest();
		//保存返回结果集的实体对象
		StatusAndTrackResponse statusAndTrackResponse1=null;
		//转换字节码
		String jsonStr;
		try {
			jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
			// 获取传入参数
			reqParEntity = JSON.parseObject(jsonStr, StatusAndTrackRequest.class);
			String waybillNo = reqParEntity.getWaybillNo();
			// 判断请求中的车牌号是否为空
			if (StringUtils.isEmpty(waybillNo)) {
				statusAndTrackResponse.setState(state);
				statusAndTrackResponse.setReturnInfo("操作失败  运单号不能为空,请核实数据!");
				LOGGER.error("操作失败  运单号不能为空,请核实数据!");
				return JSONObject.fromObject(statusAndTrackResponse).toString();
			}
			LOGGER.error("查询数据开始,请求的运单号：【"+waybillNo+"】");
			//调用业务逻辑service层获取对应的航空正单轨迹信息
			statusAndTrackResponse1 = queryAirWaybillTrailByWaybillNoService.queryStatusAndTrackResponse(waybillNo);
			LOGGER.error("查询数据结束!");
			if(statusAndTrackResponse1!=null){
				statusAndTrackResponse1.setState("1");
				statusAndTrackResponse1.setReturnInfo("操作成功!");
			}else{
				statusAndTrackResponse.setState("0");
				statusAndTrackResponse.setReturnInfo("操作失败!");
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("操作失败,json字节码存在错误,请核实!");
			statusAndTrackResponse.setState(state);
			statusAndTrackResponse.setReturnInfo("操作失败,json字节码存在错误,请核实!");
			return JSONObject.fromObject(statusAndTrackResponse).toString();
		}catch(com.alibaba.fastjson.JSONException e){
			LOGGER.error("操作失败,json格式存在错误,请核实!");
			statusAndTrackResponse.setState(state);
			statusAndTrackResponse.setReturnInfo("操作失败,json格式存在错误,请核实!");
			return JSONObject.fromObject(statusAndTrackResponse).toString();
		}
		
		ObjectMapper objectMapper = null;
		objectMapper = obtainJSONObjectMapper();
		String requestJsonStr=null;
		try {
			requestJsonStr = objectMapper.writeValueAsString(statusAndTrackResponse1);
		} catch (Exception e) {
			statusAndTrackResponse.setState("0");
			statusAndTrackResponse.setReturnInfo("系统异常!");
			LOGGER.error("json转化异常",e);
			return JSONObject.fromObject(statusAndTrackResponse).toString();
		} 
		return requestJsonStr;
	}

	
	/**
	* @description 配合(快递)运单号查询航空正单和航空正单的制单部门等实体信息
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#queryAirWaybillByWaybillNo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object queryAirWaybillByWaybillNo(String reqMsg) {
		LOGGER.error("运单号查询航空正单和航空正单的制单部门等实体信息,请求信息："+reqMsg);
		//保存失败的信息
		Object info="";
		//标示失败
		String flag=ResponseAirWayBillQueryVO.FAILFLAG;
		//创建实体对象
		AirWayBillQueryCreateDeptVO  waybillCreateDeptEnt =null;
		try {
			//1.解码
			String jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
			JSONObject jsStr = JSONObject.fromObject(jsonStr);
			//2.判断json格式
			if(!jsStr.containsKey("waybillNo")){
				info = ResponseAirWayBillQueryVO.FAILINFO;
				ResponseAirWayBillQueryVO ent = new ResponseAirWayBillQueryVO(flag,info);
				LOGGER.info("json传递参数校验报错,传递的参数key应为：'waybillNo'");
				return JSONObject.fromObject(ent).toString();
			}
			//3.获取运单号
			String waybillNo= jsStr.getString("waybillNo");
			// 判断请求中的运号是否为空
			if (StringUtils.isEmpty(waybillNo)) {
				info = ResponseAirWayBillQueryVO.FAILINFO;
				LOGGER.info("json传递参数'waybillNo'值不能为空!");
				ResponseAirWayBillQueryVO ent = new ResponseAirWayBillQueryVO(flag,info);
				return JSONObject.fromObject(ent).toString();
			}
			LOGGER.info("查询数据开始,请求的运单号:【"+waybillNo+"】");
			//4.调用业务逻辑service层获取对应的航空正单轨迹信息
			waybillCreateDeptEnt = queryAirWaybillTrailByWaybillNoService.queryAirWaybillCreateDept(waybillNo);
			LOGGER.info("查询数据结束!");
			//5.如果返回的轨迹不为空,则将信息成功返回
			if(waybillCreateDeptEnt!=null){
				flag=ResponseAirWayBillQueryVO.SUCCESSFLAG;
				info =waybillCreateDeptEnt;
			}else{
				info="操作失败,输入的运单号："+waybillNo+" 是否正确或者数据库数据不全等原因,未能找到对应信息,请核实!";
			}
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(JSONDATAEXCEPTION);
			flag=ResponseAirWayBillQueryVO.EXCEPTIONFLAG;
			info=ResponseAirWayBillQueryVO.EXCEPTIONINFO;
		} catch(Exception e){
			LOGGER.error(e);
			flag=ResponseAirWayBillQueryVO.EXCEPTIONFLAG;
			info=ResponseAirWayBillQueryVO.EXCEPTIONINFO;
		}
		ResponseAirWayBillQueryVO ent = new ResponseAirWayBillQueryVO(flag,info);
		return JSONObject.fromObject(ent).toString();
	}
	
	/**
	 * ITFSealInformationService 对应的get...set...
	 * @return
	 */
	public ISealInformationService getSealInformationService() {
		return sealInformationService;
	}

	public void setSealInformationService(
			ISealInformationService sealInformationService) {
		this.sealInformationService = sealInformationService;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public IQueryAirWaybillTrailByWaybillNoService getQueryAirWaybillTrailByWaybillNoService() {
		return queryAirWaybillTrailByWaybillNoService;
	}

	public void setQueryAirWaybillTrailByWaybillNoService(
			IQueryAirWaybillTrailByWaybillNoService queryAirWaybillTrailByWaybillNoService) {
		this.queryAirWaybillTrailByWaybillNoService = queryAirWaybillTrailByWaybillNoService;
	}
	
	public static ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		return objectMapper;
	}


	/**
	* @description 配合(快递PDA)根据车牌号校验是否解绑封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#isCheckVehicleSealInfo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年11月24日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object isCheckVehicleSealInfo(String reqMsg) {
		/*定义返回给PDA接口的字段属性*/
		String msgInfo="";
		// "0"标示异常失败; "1"标示成功； 默认表示接口是成功
		String status="1";
		// "0"标示该车辆处于未封签绑定，可用; "1"标示车辆已绑定封签，不可用;默认标示该车辆未绑定状态
		String isSeal="0";
		/*封装json字符串返回给PDA调用者*/
		Map<String,String> jsonMap = new HashMap<String,String>();
		//保存车辆封签信息
		List<String> listCode=null;
		//1.解码
		String jsonStr;

		try {
			jsonStr = URLDecoder.decode(reqMsg.replaceAll("\ufeff", ""),"utf-8");
			JSONObject jsStr = JSONObject.fromObject(jsonStr);
			//2.判断json格式
			if(!jsStr.containsKey("vehicleNo")){
				
				msgInfo = "json传递参数校验格式报错,参数key应为：'vehicleNo'";
				jsonMap.put("msgInfo", msgInfo);
				jsonMap.put("status",status);
				jsonMap.put("isSeal",isSeal);
				LOGGER.info("json传递参数校验格式报错,参数key应为：'vehicleNo'");
				return JSONObject.fromObject(jsonMap).toString();
			}
			//3.获取运单号
			String vehicleNo= jsStr.getString("vehicleNo");
			// 判断请求中的运号是否为空
			if (StringUtils.isEmpty(vehicleNo)) {
				msgInfo = ResponseAirWayBillQueryVO.FAILINFO;
				LOGGER.info("json传递参数'vehicleNo'值不能为空!");
				jsonMap.put("msgInfo", msgInfo);
				jsonMap.put("status",status);
				jsonMap.put("isSeal",isSeal);
				return JSONObject.fromObject(jsonMap).toString();
			}
			LOGGER.info("查询数据开始,请求的车牌号:【"+vehicleNo+"】");
			//4.调用业务逻辑service层获取对应的航空正单轨迹信息
			listCode = sealInformationService.checkSealForCar(vehicleNo);
			if(listCode!=null && listCode.size()>0){
				isSeal="1";
				msgInfo="车牌号:"+vehicleNo+" 已经被绑定封签，不可用!";
			}else{
				msgInfo="车牌号:"+vehicleNo+" 未被绑定封签，可用!";
			}
				
		} catch (UnsupportedEncodingException e) {
			status="0";
			msgInfo="FOSS后端JSON解析异常:"+e.getMessage();
			//e.printStackTrace();
		}
		
		//5.返回结果给PDA调用端
		jsonMap.put("msgInfo", msgInfo);
		jsonMap.put("status",status);
		jsonMap.put("isSeal",isSeal);
		
		return JSONObject.fromObject(jsonMap).toString();
		
	}
	

}
