package com.deppon.foss.module.settlement.consumer.server.service.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBURequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcSettleException;

/**
 * CUBC结清中转
 * 
 * @author 378375
 * @date 2016 10-31
 */
public class CubcSettlementService implements ICubcSettlementService {
	
	private static final Logger logger = LogManager.getLogger(CubcSettlementService.class);
	private String cubcAdd;  
	public void setCubcAdd(String cubcAdd) {
		this.cubcAdd = cubcAdd;
	}
	
	@Override
	public CUBCResponseDto settleReqToCUBU(FossToCubcRequestDto dto) {
		if (dto == null || dto.getCurrentInfo() == null || dto.getDto() == null) {
			logger.error(CubcSettleException.PARAMS_IS_NULL);
			throw new CubcSettleException(CubcSettleException.PARAMS_IS_NULL);
		}
		logger.info("调用CUBC结清服务请求地址信息:"+cubcAdd);
		CUBURequestDto reqDto = this.choiceParams(dto);
		PostMethod post = null;
		try {
			String requestJson = JSONObject.toJSONString(reqDto);
			logger.info("调用CUBC结清服务请求参数信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson, "application/json", "UTF-8");
			post = new PostMethod(cubcAdd);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_24000);
			params.setSoTimeout(NumberConstants.NUMBER_24000);
			httpClient.executeMethod(post);
//			String resJson = post.getResponseBodyAsString();
			StringBuffer sbf;
			InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resJson = sbf.toString();
            logger.info("调用CUBC结清服务响应参数信息：" + resJson);
            CUBCResponseDto resDto = JSONObject.parseObject(resJson, CUBCResponseDto.class);
            return resDto;
		} catch (ConnectTimeoutException e) {
			logger.error("发送post请求连接超时！" + e.getMessage());
			throw new CubcSettleException("发送post请求连接超时！");
		} catch (SocketTimeoutException e) {
			logger.error("发送post请求响应超时！" + e.getMessage());
			throw new CubcSettleException("发送post请求响应超时！");
		} catch (IOException e) {
			logger.error("发送post请求读取响应数据失败！" + e.getMessage());
			throw new CubcSettleException("读取响应数据失败！");
		} catch (Exception e) {
			logger.error(CubcSettleException.CONNECTIONS_ERROR + e.getMessage());
			throw new CubcSettleException(CubcSettleException.CONNECTIONS_ERROR);
		}finally {
			if(post != null){
				post.releaseConnection();
			}
		}

	}
	
	/**
	 * 挑选CUBC发送结清请求  需要的参数
	 * @param Dto
	 * @return reqDto
	 */
	private CUBURequestDto choiceParams(FossToCubcRequestDto dto){
		CUBURequestDto reqDto = new CUBURequestDto();
		//businessId VTS结清货款时候，传输给FOSS结算唯一标识
		if(StringUtils.isNotBlank(dto.getDto().getBusinessId())){
			reqDto.setBusinessId(dto.getDto().getBusinessId());
		}
		//运单号
		if(StringUtils.isNotBlank(dto.getDto().getWaybillNo())){
			reqDto.setWaybillNo(dto.getDto().getWaybillNo());
		}
		//付款方式
		if(StringUtils.isNotBlank(dto.getDto().getPaymentType())){
			reqDto.setPaymentType(dto.getDto().getPaymentType());
		}
		//到达部门编码
		if(StringUtils.isNotBlank(dto.getDto().getDestOrgCode())){
			reqDto.setDestOrgCode(dto.getDto().getDestOrgCode());
		}
		//到达部门名称
		if(StringUtils.isNotBlank(dto.getDto().getDestOrgName())){
			reqDto.setDestOrgName(dto.getDto().getDestOrgName());
		}
		//客户信息编码
		if(StringUtils.isNotBlank(dto.getDto().getCustomerCode())){
			reqDto.setCustomerCode(dto.getDto().getCustomerCode());
		}
		//客户信息名称
		if(StringUtils.isNotBlank(dto.getDto().getCustomerName())){
			reqDto.setCustomerName(dto.getDto().getCustomerName());
		}
		//业务发生日期
		if(dto.getDto().getBusinessDate() != null){
			reqDto.setBusinessDate(dto.getDto().getBusinessDate());
		}
		//是否反操作
		if(dto.getDto().isRevers()){
			reqDto.setRevers(dto.getDto().isRevers());
		}else if(!dto.getDto().isRevers()){
			reqDto.setRevers(dto.getDto().isRevers());
		}
		//结清方式
		if(StringUtils.isNotBlank(dto.getDto().getSettleApproach())){
			reqDto.setSettleApproach(dto.getDto().getSettleApproach());
		}
		//到付运费
		if(dto.getDto().getToPayFee() != null){
			reqDto.setToPayFee(dto.getDto().getToPayFee());
		}
		//代收货款运费
		if(dto.getDto().getCodFee() != null){
			reqDto.setCodFee(dto.getDto().getCodFee());
		}
		//来源单号-存放（到达实收单号）  付款编号
		if(StringUtils.isNotBlank(dto.getDto().getSourceBillNo())){
			reqDto.setSourceBillNo(dto.getDto().getSourceBillNo());
		}
		//汇款编号
		if(StringUtils.isNotBlank(dto.getDto().getPaymentNo())){
			reqDto.setPaymentNo(dto.getDto().getPaymentNo());
		}
		//POS串号
		if(StringUtils.isNotBlank(dto.getDto().getPosSerialNum())){
			reqDto.setPosSerialNum(dto.getDto().getPosSerialNum());
		}
		//银行交易流水号
		if(StringUtils.isNotBlank(dto.getDto().getBatchNo())){
			reqDto.setBatchNo(dto.getDto().getBatchNo());
		}
		//币种
		if(StringUtils.isNotBlank(dto.getDto().getCurrencyCode())){
			reqDto.setCurrencyCode(dto.getDto().getCurrencyCode());
		}
		//用户名
		if(StringUtils.isNotBlank(dto.getCurrentInfo().getUserName())){
			reqDto.setUserName(dto.getCurrentInfo().getUserName());
		}
		//员工编码
		if(StringUtils.isNotBlank(dto.getCurrentInfo().getEmpCode())){
			reqDto.setEmpCode(dto.getCurrentInfo().getEmpCode());
		}
		//员工名称
		if(StringUtils.isNotBlank(dto.getCurrentInfo().getEmpName())){
			reqDto.setEmpName(dto.getCurrentInfo().getEmpName());
		}
		//登录部门编码
		if(StringUtils.isNotBlank(dto.getCurrentInfo().getCurrentDeptCode())){
			reqDto.setCurrentDeptCode(dto.getCurrentInfo().getCurrentDeptCode());
		}
		//登录部门名称
		if(StringUtils.isNotBlank(dto.getCurrentInfo().getCurrentDeptName())){
			reqDto.setCurrentDeptName(dto.getCurrentInfo().getCurrentDeptName());
		}
		//是否快递
		if(StringUtils.isNotBlank(dto.getDto().getIsExpress())){
			reqDto.setIsExpress(dto.getDto().getIsExpress());
		}
		//派送快递员工号
		if(StringUtils.isNotBlank(dto.getDto().getDeliverExpressCode())){
			reqDto.setDeliverExpressCode(dto.getDto().getDeliverExpressCode());
		}
		//派送快递员名称
		if(StringUtils.isNotBlank(dto.getDto().getDeliverExpressName())){
			reqDto.setDeliverExpressName(dto.getDto().getDeliverExpressName());
		}
		//判断是否是合伙人的字段  Y为:是   默认是N
		if(StringUtils.isNotBlank(dto.getIsPtp())){
			reqDto.setIsPtp(dto.getIsPtp());
		}
		return reqDto;
	}
	
}
