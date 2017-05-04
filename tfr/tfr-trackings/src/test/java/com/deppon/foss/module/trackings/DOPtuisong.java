package com.deppon.foss.module.trackings;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsResponseDto;

public class DOPtuisong {
	//main方法给DOP推送货物轨迹信息
	public static void main(String[] args) throws ParseException {
		TaobaoTrackingsRequestDto t = new TaobaoTrackingsRequestDto();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		t.setWaybillNo("1234567890");
//		t.setChannelOrderNo("TIANMAO1234567890");
//		Date date1 = df.parse("2015-05-26 8:06:37");
//		t.setOperatime(date1);
//		t.setLogisticsCompany("DEPPON");
//		t.setTrackInfo("跟踪信息");
//		t.setOperateCity("上海");
//		t.setOrgType("2");
//		t.setOrgCode("W123456789");
//		t.setOrgName("上海枢纽中心");
//		t.setEnvetType("Arrival");
//		t.setContact("张三");
//		t.setContactPhone("13800138000");
//	    Date date2 = df.parse("2015-05-25 10:06:37");
//		t.setCreatTime(date2);
//		t.setModifyTime(new Date());
//		
//		t.setSerialNo("0001");
//		t.setGoodsQtyTotal(50);
//		t.setOrderChannel("TianMao");
//		t.setArriveOrgCode("W123456789");
//		t.setArriveOrgName("上海枢纽中心");
//		t.setArriveCity("上海");
//		t.setProductCode("产品类型");
//		t.setIsExpress("N");
		pushTrackingsDOP(t);
		System.out.println("发送成功************************************");
		
	}
	
	public static TaobaoTrackingsResponseDto pushTrackingsDOP(TaobaoTrackingsRequestDto dto){
		//esb服务编码   192.168.67.12:8580/esb2/rs/ESB_FOSS2ESB_TMALL_HOME_CLOTHING

		String code = "ESB_FOSS2ESB_TMALL_HOME_CLOTHING";
		HttpClient httpClient = new HttpClient();
		
		String url = "http://192.168.67.12:8580/esb2/rs" + "/" + code;
		//String url = "http://192.168.20.123:8180/sync/webservice/dop/fossStatusService/receiverStatus";
		PostMethod  postMethod=new PostMethod(url);
		TaobaoTrackingsResponseDto responseDto = new TaobaoTrackingsResponseDto();
		
		String requestStr = JSONObject.toJSONString(dto);
		System.out.println(requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
	     postMethod.setRequestEntity(requestEntity);
	     try {
				httpClient.executeMethod(postMethod);
				String responseStr = postMethod.getResponseBodyAsString();
				System.out.println(responseStr);
				if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
					return null;
				}
				responseDto = JSONObject.parseObject(responseStr, TaobaoTrackingsResponseDto.class);
				System.out.println("返回对象"+responseDto);
				return responseDto;
			} catch (HttpException e) {
				e.printStackTrace();
				responseDto.setResult("false");
				responseDto.setMessage(e.getMessage());
				return responseDto;
			} catch (IOException e) {
				e.printStackTrace();
				responseDto.setResult("false");
				responseDto.setMessage(e.getMessage());
				return responseDto;
			}	
	     
	}
}
