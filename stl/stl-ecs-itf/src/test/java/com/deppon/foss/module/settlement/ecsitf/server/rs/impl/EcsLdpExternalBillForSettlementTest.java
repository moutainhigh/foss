package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.agency.api.shared.dto.EcsSettlementLdpExternalBillRequestDto;

/**
 * 快递 外发单业务调用相关结算接口 单元测试
 * @author 243921-zhangtingting
 * @date 2016-05-09 下午2:52:04
 */
public class EcsLdpExternalBillForSettlementTest {
	
	/**
	 * 新增外发单服务 结算接口
	 */
	@Test
	public void testAddExternalBill() throws HttpException, IOException {
		EcsSettlementLdpExternalBillRequestDto request = new EcsSettlementLdpExternalBillRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//付款方式    
		request.setPaidMethod("");
		//外发部门//制单人所在的部门
		request.setWaifabumen("");
		//外发部门//制单人所在的部门名称
		request.setWaifabumenName("");
		//外发单号
		request.setExternalBillNo("");
		//代收货款手续费
		request.setCodAgencyFee(BigDecimal.ZERO);
		//保价费
		request.setExternalInsuranceFee(BigDecimal.ZERO);
		//外发运费
		request.setExternalAgencyFee(BigDecimal.ZERO);
		//外发成本总额【应付费用】
		request.setCostAmount(BigDecimal.ZERO);
		//审核状态
		request.setAuditStatus("");
		//偏线代理编码
		request.setAgentCompanyCode("");
		//偏线代理名称
		request.setAgentCompanyName("");
		//是否中转外发
		request.setTransferExternal("");
		//币种
		request.setCurrencyCode("RMB");
		//业务日期(当前业务操作时间)
		request.setBusinessDate(new Date());
		//外发单创建时间(录入日期)
		request.setCreateTime(new Date());
		//运单Id
		request.setWaybillId(request.getWaybillId());
		//出发部门编码
		request.setReceiveOrgCode("W01060311");
		//到达部门编码
		request.setLastLoadOrgCode("W01060311");
		//总费用
		request.setTotalFee(BigDecimal.ZERO);
		//代收货款费用
		request.setCodAmount(BigDecimal.ZERO);
		//代理网点
		request.setAgentOrgCode("W01060311");
		
		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsLdpBillForSettlement/addExternalBil";

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
	 * 修改外发单服务 结算接口
	 */
	@Test
	public void testModifyExternalBill() throws HttpException, IOException {
		EcsSettlementLdpExternalBillRequestDto request = new EcsSettlementLdpExternalBillRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//付款方式    
		request.setPaidMethod("");
		//外发部门//制单人所在的部门
		request.setWaifabumen("");
		//外发部门//制单人所在的部门名称
		request.setWaifabumenName("");
		//外发单号
		request.setExternalBillNo("");
		//代收货款手续费
		request.setCodAgencyFee(BigDecimal.ZERO);
		//保价费
		request.setExternalInsuranceFee(BigDecimal.ZERO);
		//外发运费
		request.setExternalAgencyFee(BigDecimal.ZERO);
		//外发成本总额【应付费用】
		request.setCostAmount(BigDecimal.ZERO);
		//审核状态
		request.setAuditStatus("");
		//偏线代理编码
		request.setAgentCompanyCode("");
		//偏线代理名称
		request.setAgentCompanyName("");
		//是否中转外发
		request.setTransferExternal("");
		//币种
		request.setCurrencyCode("RMB");
		//业务日期(当前业务操作时间)
		request.setBusinessDate(new Date());
		//外发单创建时间(录入日期)
		request.setCreateTime(new Date());
		//运单Id
		request.setWaybillId(request.getWaybillId());
		//出发部门编码
		request.setReceiveOrgCode("W01060311");
		//到达部门编码
		request.setLastLoadOrgCode("W01060311");
		//总费用
		request.setTotalFee(BigDecimal.ZERO);
		//代收货款费用
		request.setCodAmount(BigDecimal.ZERO);
		//代理网点
		request.setAgentOrgCode("W01060311");
		
		String requestJson = JSONObject.toJSONString(request);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsLdpBillForSettlement/modifyExternalBill";

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
	 * 审核外发单服务 结算接口
	 */
	@Test
	public void testAuditExternalBill() throws HttpException, IOException {
		EcsSettlementLdpExternalBillRequestDto request = new EcsSettlementLdpExternalBillRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//付款方式    
		request.setPaidMethod("");
		//外发部门//制单人所在的部门
		request.setWaifabumen("");
		//外发部门//制单人所在的部门名称
		request.setWaifabumenName("");
		//外发单号
		request.setExternalBillNo("");
		//代收货款手续费
		request.setCodAgencyFee(BigDecimal.ZERO);
		//保价费
		request.setExternalInsuranceFee(BigDecimal.ZERO);
		//外发运费
		request.setExternalAgencyFee(BigDecimal.ZERO);
		//外发成本总额【应付费用】
		request.setCostAmount(BigDecimal.ZERO);
		//审核状态
		request.setAuditStatus("");
		//偏线代理编码
		request.setAgentCompanyCode("");
		//偏线代理名称
		request.setAgentCompanyName("");
		//是否中转外发
		request.setTransferExternal("");
		//币种
		request.setCurrencyCode("RMB");
		//业务日期(当前业务操作时间)
		request.setBusinessDate(new Date());
		//外发单创建时间(录入日期)
		request.setCreateTime(new Date());
		//运单Id
		request.setWaybillId(request.getWaybillId());
		//出发部门编码
		request.setReceiveOrgCode("W01060311");
		//到达部门编码
		request.setLastLoadOrgCode("W01060311");
		//总费用
		request.setTotalFee(BigDecimal.ZERO);
		//代收货款费用
		request.setCodAmount(BigDecimal.ZERO);
		//代理网点
		request.setAgentOrgCode("W01060311");
		
		List<EcsSettlementLdpExternalBillRequestDto> list = new ArrayList<EcsSettlementLdpExternalBillRequestDto>();
		
		String requestJson = JSONObject.toJSONString(list);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsLdpBillForSettlement/auditExternalBill";

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
	 * 作废外发单服务 结算接口
	 */
	@Test
	public void testDisableExternalBill() throws HttpException, IOException {
		EcsSettlementLdpExternalBillRequestDto request = new EcsSettlementLdpExternalBillRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//付款方式    
		request.setPaidMethod("");
		//外发部门//制单人所在的部门
		request.setWaifabumen("");
		//外发部门//制单人所在的部门名称
		request.setWaifabumenName("");
		//外发单号
		request.setExternalBillNo("");
		//代收货款手续费
		request.setCodAgencyFee(BigDecimal.ZERO);
		//保价费
		request.setExternalInsuranceFee(BigDecimal.ZERO);
		//外发运费
		request.setExternalAgencyFee(BigDecimal.ZERO);
		//外发成本总额【应付费用】
		request.setCostAmount(BigDecimal.ZERO);
		//审核状态
		request.setAuditStatus("");
		//偏线代理编码
		request.setAgentCompanyCode("");
		//偏线代理名称
		request.setAgentCompanyName("");
		//是否中转外发
		request.setTransferExternal("");
		//币种
		request.setCurrencyCode("RMB");
		//业务日期(当前业务操作时间)
		request.setBusinessDate(new Date());
		//外发单创建时间(录入日期)
		request.setCreateTime(new Date());
		//运单Id
		request.setWaybillId(request.getWaybillId());
		//出发部门编码
		request.setReceiveOrgCode("W01060311");
		//到达部门编码
		request.setLastLoadOrgCode("W01060311");
		//总费用
		request.setTotalFee(BigDecimal.ZERO);
		//代收货款费用
		request.setCodAmount(BigDecimal.ZERO);
		//代理网点
		request.setAgentOrgCode("W01060311");
		
		List<EcsSettlementLdpExternalBillRequestDto> list = new ArrayList<EcsSettlementLdpExternalBillRequestDto>();
		
		String requestJson = JSONObject.toJSONString(list);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsLdpBillForSettlement/disableExternalBill";

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
	 * 反审核外发单服务 结算接口
	 */
	@Test
	public void testReverseAuditExternalBill() throws HttpException, IOException {
		EcsSettlementLdpExternalBillRequestDto request = new EcsSettlementLdpExternalBillRequestDto();

		//运单号
		request.setWaybillNo("222000018");
		//付款方式    
		request.setPaidMethod("");
		//外发部门//制单人所在的部门
		request.setWaifabumen("");
		//外发部门//制单人所在的部门名称
		request.setWaifabumenName("");
		//外发单号
		request.setExternalBillNo("");
		//代收货款手续费
		request.setCodAgencyFee(BigDecimal.ZERO);
		//保价费
		request.setExternalInsuranceFee(BigDecimal.ZERO);
		//外发运费
		request.setExternalAgencyFee(BigDecimal.ZERO);
		//外发成本总额【应付费用】
		request.setCostAmount(BigDecimal.ZERO);
		//审核状态
		request.setAuditStatus("");
		//偏线代理编码
		request.setAgentCompanyCode("");
		//偏线代理名称
		request.setAgentCompanyName("");
		//是否中转外发
		request.setTransferExternal("");
		//币种
		request.setCurrencyCode("RMB");
		//业务日期(当前业务操作时间)
		request.setBusinessDate(new Date());
		//外发单创建时间(录入日期)
		request.setCreateTime(new Date());
		//运单Id
		request.setWaybillId(request.getWaybillId());
		//出发部门编码
		request.setReceiveOrgCode("W01060311");
		//到达部门编码
		request.setLastLoadOrgCode("W01060311");
		//总费用
		request.setTotalFee(BigDecimal.ZERO);
		//代收货款费用
		request.setCodAmount(BigDecimal.ZERO);
		//代理网点
		request.setAgentOrgCode("W01060311");
		
		List<EcsSettlementLdpExternalBillRequestDto> list = new ArrayList<EcsSettlementLdpExternalBillRequestDto>();
		
		String requestJson = JSONObject.toJSONString(list);
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsLdpBillForSettlement/reverseAuditExternalBill";

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
