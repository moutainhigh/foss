/**
 * 德邦推送运单轨迹给快递100需求
 * 一、前提：
 *	因公司与快递100签订合同，合同内容规定，德邦要推送运单轨迹给快递100.
 * 二、特殊说明：
 *  1、按现有规则，推送轨迹与FOSS系统并无直接联系，应由FOSS外的其他系统来承接，但目前公司并无此类系统，更无相应的开发资源，故只能在FOSS现有系统开发且共用FOSS库。
 *  2、为何中转开发员来开发？首先参照上一条；另这个需求前，配合菜鸟项目中快递代理外发与快递100对接的需求是中转开发员负责，更因现无合适人选，故继续接手。
 * 三、设计说明：
 * 	1、为了不影响现有FOSS系统的应用，故设计新增了一个单独应用foss-services-trackings，单独部署，把影响降到最低。
 * 	2、包结构说明如下：
 * 		foss	
 *  	  |___trackings(新增)
 *  	  |		   |_____foss-trackings-api(新增)
 *  	  |		   |_____foss-trackings(新增)
 *  	  |	
 *  	  |___foss-services
 *  				|____foss-services-trackings(新增)
 *  3、包功能说明：
 *    3.1 foss-trackings-api 接口
 *    3.2 foss-trackings 实现
 *    3.3 foss-services-trackings 服务
 *  4、接口说明：
 *  	接口采用restful方式，POST json
 *  	4.1 快递100订阅单号
 *  		4.1.1 快递100<--->DOP<--->ESB<--->FOSS	
 *  	4.2 FOSS推送订阅单号轨迹给快递100,为了节约DOP与快递100联通的时间，故FOSS与DOP间采用异步通信的方式
 *  		4.2.1 FOSS<--->ESB<--->DOP
 *  		4.2.2 快递100<--->DOP<--->ESB<--->FOSS
 */
package com.deppon.foss.services.trackings.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.dto.OrderWaybillDto;
import com.deppon.foss.module.trackings.api.shared.dto.ResponseDto;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class WaybillsTrackingsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillsTrackingsService.class);
	
	private IWaybillTrackingsService  waybillTrackingsService;
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	/**
	 * @author nly
	 * @date 2015年3月27日 下午12:54:12
	 * @function 保存快递100订阅的单号信息
	 * @param requestStr
	 * @return
	 */
	@POST
	@Path("/addOrderWaybillNos")
	public String addOrderWaybillNosFromEsb(String requestStr) {
		LOGGER.error("快递100订阅单号服务开始...");
		LOGGER.error("快递100订阅单号requestStr===" + requestStr);
		
		//ESB必须字段
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		
		//响应消息
		String responseStr = "";
		ResponseDto responseDto = new ResponseDto();
		//订阅单号
		String orderWaybillNo = "";
		
		OrderWaybillDto waybillDto = new OrderWaybillDto();
		try {
			//订阅单号信息转换
			waybillDto  = JSON.parseObject(requestStr,OrderWaybillDto.class);
			
		} catch (Exception e) {
			responseDto.setResult("false");
			responseDto.setReturnCode("400");
			responseDto.setMessage("数据不完整");
			responseStr = JSON.toJSONString(responseDto);
			return responseStr;
		} 
		
		try{
			LOGGER.error("快递100订阅单号，开始同步数据...订阅单号：" + orderWaybillNo);
			responseDto = waybillTrackingsService.saveOrderWaybillNo(waybillDto);
			LOGGER.error("快递100订阅单号，同步数据成功...订阅单号：" + orderWaybillNo);
		} catch(Exception e) {
			LOGGER.error("快递100订阅单号，同步数据异常...订阅单号：" + orderWaybillNo);
			responseDto.setResult("false");
			responseDto.setReturnCode("600");
			responseDto.setMessage("业务异常");
		}
		
		LOGGER.error("快递100订阅单号服务结束...");
		
		responseStr = JSON.toJSONString(responseDto);
		LOGGER.error("快递100订阅单号responseStr===" + responseStr);
		return responseStr;
	}

	/**
	 * @author nly
	 * @date 2015年3月27日 下午3:40:01
	 * @function 处理快递100返回的轨迹接收结果
	 * @param requestStr
	 * @return
	 */
	@POST
	@Path("/savePushResult")
	public String savePushResultFromESB(String requestStr) {
		LOGGER.error("DOP返回推送100返回结果服务开始...");
		LOGGER.error("DOP返回推送100返回结果requestStr===" + requestStr);
		
		//ESB必须字段
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		//响应消息
		String responseStr = "";
		ResponseDto responseDto = new ResponseDto();
		
		ResponseDto rspDto = JSON.parseObject(requestStr, ResponseDto.class);
		try{
			LOGGER.error("DOP返回推送结果开始，msgId:" + rspDto.getMsgId());
			waybillTrackingsService.savePushResult(rspDto);
			LOGGER.error("DOP返回推送结果结束，msgId:" + rspDto.getMsgId());
			responseDto.setResult(WaybillTrackingsConstants.FLG_SUCCESS);
			responseDto.setReturnCode(WaybillTrackingsConstants.CODE_SUCCESS);
			responseDto.setMessage(WaybillTrackingsConstants.MSG_SUCCESS);
			LOGGER.error("DOP返回推送结果成功，msgId:" + rspDto.getMsgId());
			
		} catch(Exception e) {
			LOGGER.error("DOP返回推送结果失败，msgId:" + rspDto.getMsgId());
			responseDto.setResult(WaybillTrackingsConstants.FLG_FAILURE);
			responseDto.setReturnCode(WaybillTrackingsConstants.CODE_FAILURE);
			responseDto.setMessage(WaybillTrackingsConstants.MSG_FAILURE);
		}
		
		responseStr = JSON.toJSONString(responseDto);
		LOGGER.error("DOP返回推送100返回结果responseStr===" + responseStr);
		LOGGER.error("DOP返回推送100返回结果服务结束...");
		return responseStr;
	}
	
	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
		this.waybillTrackingsService = waybillTrackingsService;
	}
		
}
