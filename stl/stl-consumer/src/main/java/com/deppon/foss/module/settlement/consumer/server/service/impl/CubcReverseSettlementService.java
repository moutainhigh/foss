package com.deppon.foss.module.settlement.consumer.server.service.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IReverseSignDetailDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReverseSignDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBURequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcReverseSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcReverseSettleException;
/**
 * CUBC反结清接口的实现
 * 
 * @date 2016-11-02
 * @author 378375
 *
 */
public class CubcReverseSettlementService implements ICubcReverseSettlementService {
	
	private static final Logger logger = LogManager.getLogger(CubcReverseSettlementService.class);
	private String cubcRevAdd;	
	public void setCubcRevAdd(String cubcRevAdd) {
		this.cubcRevAdd = cubcRevAdd;
	}
	
	private IReverseSignDetailDao reverseSignDetailDao;
	
	public void setReverseSignDetailDao(IReverseSignDetailDao reverseSignDetailDao) {
		this.reverseSignDetailDao = reverseSignDetailDao;
	}

	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	private IRepaymentDao repaymentDao;

	/**
	 * FOSS调用CUBC反结清方法
	 */
	@Override
	public CUBCResponseDto reverseSettleReqToCUBU(FossToCubcRequestDto dto) {
		if(dto == null || dto.getCurrentInfo() == null || dto.getDto() == null){
			logger.info(CubcReverseSettleException.PARAMS_IS_NULL);
			throw new CubcReverseSettleException(CubcReverseSettleException.PARAMS_IS_NULL);
		}
		logger.info("调用CUBC反结清服务请求地址："+cubcRevAdd);
		CUBURequestDto reqDto = this.choiceParams(dto);
		PostMethod post = null;
		try {
			String requestJson = JSONObject.toJSONString(reqDto);
			logger.info("调用CUBC反结清服务请求信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson, "application/json", "UTF-8");
			post = new PostMethod(cubcRevAdd);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_24000);
			params.setSoTimeout(NumberConstants.NUMBER_24000);
			httpClient.executeMethod(post);
//		    String responseJson = post.getResponseBodyAsString();
		    InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			StringBuffer sbf = new StringBuffer();
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
			String responseJson = sbf.toString();
		    logger.info("调用CUBC反结清服务响应信息："+responseJson);
		    CUBCResponseDto resDto = JSONObject.parseObject(responseJson, CUBCResponseDto.class);
		    return resDto;
		} catch (Exception e) {
			logger.error(CubcReverseSettleException.CONNECTIONS_ERROR);
			throw new CubcReverseSettleException(CubcReverseSettleException.CONNECTIONS_ERROR);
		} finally {
			if(post != null){
				post.releaseConnection();
			}
		}
		
	}
	/**
	 * 挑选CUBC发送反结清请求  需要的参数
	 * @param dto
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
		//来源单号-存放（到达实收单号）  《付款编号》
		if(StringUtils.isNotBlank(dto.getDto().getSourceBillNo())){
			reqDto.setSourceBillNo(dto.getDto().getSourceBillNo());
		}
		//汇款编号  《款项认领编号》
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
		//反结清的批量付款编号
		if(this.getPaymentNos(dto.getCurrentInfo().getCubcID()) != null && this.getPaymentNos(dto.getCurrentInfo().getCubcID()).size() > 0){
			reqDto.setPaymentNos(this.getPaymentNos(dto.getCurrentInfo().getCubcID()));
		}
		return reqDto;
	}
	/**
	 * 得到反结清的批量付款编号
	 * @return 付款编号的集合
	 */
	public List<String> getPaymentNos(String cubcID){
    //反签收变更明细
	List<ReverseSignDetailEntity> rsdeList = reverseSignDetailDao
			.searchReverseSignDetailList(cubcID);
	// 存放反签收中的付款的付款编号
	List<String> paymentNoslist = new ArrayList<String>();
	// 付款实体
	RepaymentEntity repaymentEntity;
	//遍历反签收变更明细表，从中得到关联id》关联id就是付款信息表id
	for (ReverseSignDetailEntity reverseSignDetailEntity : rsdeList) {
		if (SignConstants.REVERSE_SIGN_TYPE_REPAYMENT
				.equals(reverseSignDetailEntity.getType())) {
			//通过id得到付款信息实体
			repaymentEntity = repaymentDao
					.searchRepaymentById(reverseSignDetailEntity.getMappingId());
			//通过付款实体get到批量付款编号  然后放入到集合中
			paymentNoslist.add(repaymentEntity.getRepaymentNo());
		}
	}
		return paymentNoslist;
	}
	
}
