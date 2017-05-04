package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsLineSignRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsPaymentSettlementRequestDto;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递签收业务调用相关结算接口  单元测试
 * @author 243921-zhangtingting
 * @date 2016-04-28 下午03:56:48
 */
public class EcsSignForSettlementTest {

	/**
	 * 查询财务单据信息，获取应收与已收
	 */
	@Test
	public void testQueryReceivableAmount() throws HttpException, IOException {

		String requestJson = "{\"waybillNo\":\"566666223\"}";
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsSignForSettlement/queryReceivableAmount";

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

	/**
	 * 结清货款，确认付款 实收货款 / 到付运单结转临欠月结
	 */
	@Test
	public void testConfirmToPayment() throws HttpException, IOException {
		EcsPaymentSettlementRequestDto request = new EcsPaymentSettlementRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//结清方式
		request.setSettleApproach("");
		//付款类型
		request.setPaymentType("CH");
		//部门code
		request.setCurrentDeptCode("W01060311");
		//部门名称
		request.setCurrentDeptName("上海青浦区赵巷营业部");
		//客户code
		request.setEmpCode("059387");
		//客户名称
		request.setEmpName("司品鑫");
		//时间
		request.setBusinessDate(new Date());
		//付款编号
		request.setSourceBillNo("20160323140015979222000018");
		//款项认领编号
		request.setPaymentNo("");
		//实收代收货款费用
		request.setCodFee(BigDecimal.ZERO);
		//实收到付运费
		request.setToPayFee(BigDecimal.ZERO);
		//币种
		request.setCurrencyCode("RMB");
		//串号
		request.setPosSerialNum("");
		//银行交易流水号
		request.setBatchNo("");
		//快递员工号
		request.setDeliverExpressCode("");
		//快递员姓名
		request.setDeliverExpressName("");

		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsSignForSettlement/confirmToPayment";

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
	
	/**
	 * 签收  确认收入 调用结算出库接口
	 */
	@Test
	public void testConfirmTaking() throws HttpException, IOException {
		EcsLineSignRequestDto request = new EcsLineSignRequestDto();

		// 操作人编码
		request.setEmpName("霍凯超");
		// 操作人名称
		request.setEmpCode("084544");
		// 签收部门名称
		request.setCurrentDeptName("北京派送中心");
		// 签收部门编码
		request.setCurrentDeptCode("W011305080404");
		// 运单号
		request.setWaybillNo("");
		// 签收时间
		request.setSignDate(new Date());
		request.setSignType("LS");
		// 是否整车运单
		request.setIsWholeVehicle(FossConstants.NO);
		// 是否PDA签收
		request.setIsPdaSign(FossConstants.NO);
		// 产品类型
		request.setProductType("PACKAGE");
		//外发单号
		request.setExternalWaybillNo("");
		//外发流水号
		request.setSerialNo("");
		//签收情况
		request.setSignSituation("NORMAL_SIGN");

		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsSignForSettlement/confirmTaking";

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
	
	/**
	 * 反签收 财务出库 调用结算接口
	 */
	@Test
	public  void testReverseConfirmTaking() throws HttpException, IOException {
		EcsLineSignRequestDto request = new EcsLineSignRequestDto();

		// 操作人编码
		request.setEmpName("霍凯超");
		// 操作人名称
		request.setEmpCode("084544");
		// 签收部门名称
		request.setCurrentDeptName("北京派送中心");
		// 签收部门编码
		request.setCurrentDeptCode("W011305080404");
		// 运单号
		request.setWaybillNo("");
		// 签收时间
		request.setSignDate(new Date());
		request.setSignType("LS");
		// 是否整车运单
		request.setIsWholeVehicle(FossConstants.NO);
		// 是否PDA签收
		request.setIsPdaSign(FossConstants.NO);
		// 产品类型
		request.setProductType("PACKAGE");
		//外发单号
		request.setExternalWaybillNo("");
		//外发流水号
		request.setSerialNo("");
		//签收情况
		request.setSignSituation("NORMAL_SIGN");

		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsSignForSettlement/reverseConfirmTaking";

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
	
	/**
	 * 反结清 反到付运费结转临欠/月结  反确认付款
	 */
	@Test
	public  void testReversConfirmToPayment() throws HttpException, IOException {
		EcsPaymentSettlementRequestDto request = new EcsPaymentSettlementRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//结清方式
		request.setSettleApproach("");
		//付款类型
		request.setPaymentType("CH");
		//部门code
		request.setCurrentDeptCode("W01060311");
		//部门名称
		request.setCurrentDeptName("上海青浦区赵巷营业部");
		//客户code
		request.setEmpCode("059387");
		//客户名称
		request.setEmpName("司品鑫");
		//时间
		request.setBusinessDate(new Date());
		//付款编号
		request.setSourceBillNo("20160323140015979222000018");
		//款项认领编号
		request.setPaymentNo("");
		//实收代收货款费用
		request.setCodFee(BigDecimal.ZERO);
		//实收到付运费
		request.setToPayFee(BigDecimal.ZERO);
		//币种
		request.setCurrencyCode("RMB");
		//串号
		request.setPosSerialNum("");
		//银行交易流水号
		request.setBatchNo("");
		//快递员工号
		request.setDeliverExpressCode("");
		//快递员姓名
		request.setDeliverExpressName("");

		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsSignForSettlement/reversConfirmToPayment";

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
