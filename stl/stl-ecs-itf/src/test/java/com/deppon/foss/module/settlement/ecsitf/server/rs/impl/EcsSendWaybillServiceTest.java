package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class EcsSendWaybillServiceTest {

	@Test
	public void testEcsSendWaybill() throws HttpException, IOException{
		String requestJson = "{\"actualFreight\":{\"waybillNo\":\"actualFreight-001\"},\"waybillEntity\":{\"active\":\"Y\",\"billTime\":1461306459183,\"billWeight\":10,\"carDirectDelivery\":\"N\",\"codFee\":5,\"createOrgCode\":\"DDDDD\",\"createTime\":1461306459183,\"createUserCode\":\"CCCCC\",\"currencyCode\":\"yuan\",\"deliveryCustomerCode\":\"F2016021741153822\",\"deliveryCustomerContact\":\"安静\",\"forbiddenLine\":\"N\",\"freePickupGoods\":\"Y\",\"goodsName\":\"A\",\"goodsQtyTotal\":100,\"id\":\"11ea6d1a-6a76-4c10-a013-523dc6efded5\",\"isWholeVehicle\":\"N\",\"lastLoadOrgCode\":\"W0000000028\",\"loadOrgCode\":\"W0000000932\",\"paidMethod\":\"CH\",\"pickupCentralized\":\"Y\",\"pickupFee\":10,\"pickupToDoor\":\"Y\",\"picture\":false,\"preciousGoods\":\"N\",\"productCode\":\"PLF\",\"productId\":\"ac52d52d-2d74-4df2-8109-e6200ed966aa\",\"receiveCustomerContact\":\"jay\",\"receiveMethod\":\"DELIVER_UP\",\"receiveOrgCode\":\"123456789\",\"secretPrepaid\":\"N\",\"serviceFee\":10,\"specialShapedGoods\":\"N\",\"targetOrgCode\":\"上海市浦东新区中科路2500弄\",\"totalFee\":2000,\"waybillNo\":\"201642278\",\"wholeVehicleAppfee\":10},\"waybillExpress\":{\"waybillNo\":\"00000000-123dsdsd\"},\"waybillRfc\":{\"waybillNo\":\"rfc-00999dsjsds\"}}";
		String url = "http://10.224.229.137:8080/stl-ecs-itf/rest/v1/foss/stl/ecsSendWaybill/synchroWaybill";
		
		//创建请求实体
		RequestEntity reqEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
		//post请求
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(reqEntity);
		post.addRequestHeader("Content-Type","application/json;charset=UTF-8");
		
		
		//发送请求
		new HttpClient().executeMethod(post);
		
		String resJson = post.getResponseBodyAsString();
		System.out.println(resJson);
	}
}
