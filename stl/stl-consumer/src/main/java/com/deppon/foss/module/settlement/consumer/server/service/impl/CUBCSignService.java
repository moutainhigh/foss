package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.service.impl.CUBCCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;

/**
 * CUBC签收服务FOSS客户端实现
 * @author 353654 dongyangyang
 *
 */
public class CUBCSignService extends CUBCCommonService implements ICUBCSignService {
	private IExternalBillService externalBillService;
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}

	private final static String PARAMETERS_ERROR;
	private final static String CONNECTIONS_ERROR;
	static{
		PARAMETERS_ERROR = "调用CUBC签收服务参数异常---sendSignReqToCUBC";
		CONNECTIONS_ERROR = "调用CUBC签收服务连接异常---sendSignReqToCUBC";
	}
	/**
	 * FOSS调用CBUC签收接口实现
	 */
	@Override
	public CUBCSignOrRevSignResultDto sendSignReqToCUBC(FOSSSignOrRevSignRequestDto reqDto) {
		if(reqDto==null || reqDto.getLineSignDto()==null || reqDto.getCurrentInfo()==null){
			logger.error(PARAMETERS_ERROR+"---null");
			throw new CUBCSignException(PARAMETERS_ERROR);
		}
		PostMethod post = null;
		try {
				CUBCSignOrRevSignRequestDto cubcReqDto = this.converParams(reqDto);
				logger.info("CUBC签收请求地址信息:"+esbAddress);
				String requestJson = JSONObject.toJSONString(cubcReqDto);
				logger.info("调用CUBC签收服务请求JSON信息："+requestJson);
				RequestEntity reqEntity = new StringRequestEntity(requestJson,APPLICATION_JSON,CODE_FORMAT);
				post = new PostMethod(esbAddress);
				post.setRequestEntity(reqEntity);
				post.addRequestHeader(CONTENT_TYPE,REQUEST_HEADER);
				HttpClient httpClient = new HttpClient();
				HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
				params.setConnectionTimeout(NumberConstants.NUMBER_15000);
				params.setSoTimeout(NumberConstants.NUMBER_15000);
				httpClient.executeMethod(post);
				String resultJson = post.getResponseBodyAsString();
			 /* InputStream inputStream = post.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,CODE_FORMAT));
	 			StringBuffer sbf = new StringBuffer();
				String line = null;
				try {
					while ((line = br.readLine()) != null){
						sbf.append(line);
					}
				} catch (Exception e) {
					logger.info("读取响应数据失败！" + e.getMessage());
					throw new IOException("读取响应数据失败！");
				} finally{
					if(br != null){
						br.close();
					}
				}
				String resultJson = sbf.toString();*/
				logger.info("调用CUBC签收服务响应JSON信息："+resultJson);
				return JSONObject.parseObject(resultJson,CUBCSignOrRevSignResultDto.class);
			} catch (Exception e) {
				logger.error(CONNECTIONS_ERROR + e.getMessage());
				throw new CUBCSignException(CONNECTIONS_ERROR);
			} finally {
				if (post != null) {
					post.releaseConnection();
				}
			}
		
	}
	
	/**
	 * 将FOSSSignOrRevSignRequestDto转换成CUBCSignOrRevSignRequestDto
	 * @param FOSSSignOrRevSignRequestDto
	 * @return
	 */
	private CUBCSignOrRevSignRequestDto converParams(FOSSSignOrRevSignRequestDto reqDto){
		//封装CUBC的请求参数
		CUBCSignOrRevSignRequestDto cubcReqDto = new CUBCSignOrRevSignRequestDto();
		//用户名
		if(StringUtils.isNotBlank(reqDto.getCurrentInfo().getUserName())){
			cubcReqDto.setUserName(reqDto.getCurrentInfo().getUserName());
		}
		//当前登录部门编码
		if(StringUtils.isNotBlank(reqDto.getCurrentInfo().getCurrentDeptCode())){
			cubcReqDto.setCurrentDeptCode(reqDto.getCurrentInfo().getCurrentDeptCode());	
		}
		//当前登录部门名称
		if(StringUtils.isNotBlank(reqDto.getCurrentInfo().getCurrentDeptName())){
			cubcReqDto.setCurrentDeptName(reqDto.getCurrentInfo().getCurrentDeptName());
		}
		//员工工号
		if(StringUtils.isNotBlank(reqDto.getCurrentInfo().getEmpCode())){
			cubcReqDto.setEmpCode(reqDto.getCurrentInfo().getEmpCode());
		}
		//员工编码
		if(StringUtils.isNotBlank(reqDto.getCurrentInfo().getEmpName())){
			cubcReqDto.setEmpName(reqDto.getCurrentInfo().getEmpName());
		}
		//来源单号
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getWaybillNo())){
			cubcReqDto.setSourceNo(reqDto.getLineSignDto().getWaybillNo());
			//设置外发单号---外发签收
			List<String> list = externalBillService.getExternalBillNumListByWaybillNo(reqDto.getLineSignDto().getWaybillNo());
			if(!list.isEmpty()){
				cubcReqDto.setExternalWaybillNos(list);
				logger.info("调用接送货根据单号查询外发单号返回List信息："+ReflectionToStringBuilder.toString(list));
			}
		}
		//运输性质
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getProductType())){
			cubcReqDto.setProductType(reqDto.getLineSignDto().getProductType());
		}
		//签收部门编码
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getSignOrgCode())){
			cubcReqDto.setSignOrgCode(reqDto.getLineSignDto().getSignOrgCode());
		}
		//签收部门名称
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getSignOrgName())){
			cubcReqDto.setSignOrgName(reqDto.getLineSignDto().getSignOrgName());
		}
		//签收日期
		if(reqDto.getLineSignDto().getSignDate() != null){
			cubcReqDto.setSignDate(reqDto.getLineSignDto().getSignDate());
		}
		//是否整车
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getIsWholeVehicle())){
			cubcReqDto.setIsWholeVehicle(reqDto.getLineSignDto().getIsWholeVehicle());
		}
		//是否PDA
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getIsPdaSign())){
			cubcReqDto.setIsPdaSign(reqDto.getLineSignDto().getIsPdaSign());
		}
		//是否快递
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getIsExpress())){
			cubcReqDto.setIsExpress(reqDto.getLineSignDto().getIsExpress());
		}
		//操作人编码
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getOperatorCode())){
			cubcReqDto.setOperatorCode(reqDto.getLineSignDto().getOperatorCode());
		}
		//操作人名称
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getOperatorName())){
			cubcReqDto.setOperatorName(reqDto.getLineSignDto().getOperatorName());
		}
		//外发流水号
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getSerialNo())){
			cubcReqDto.setSerialNo(reqDto.getLineSignDto().getSerialNo());
		}
		//签收类型,正常和异常签收合二为一
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getSignType()) && StringUtils.isBlank(reqDto.getExpSignType())){
			cubcReqDto.setSignType(reqDto.getLineSignDto().getSignType());
		}
		if(StringUtils.isBlank(reqDto.getLineSignDto().getSignType()) && StringUtils.isNotBlank(reqDto.getExpSignType())){
			cubcReqDto.setSignType(reqDto.getExpSignType());
		}
		//签收情况---针对异常  正常。
		if(StringUtils.isNotBlank(reqDto.getLineSignDto().getSignSituation())){
			logger.info("签收情况："+reqDto.getLineSignDto().getSignSituation());
			cubcReqDto.setSignSituation(reqDto.getLineSignDto().getSignSituation());
		}
		//签收情况---针对丢货，弃货，违禁品
		if(StringUtils.isNotBlank(cubcReqDto.getSignType())){
			if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS, cubcReqDto.getSignType())){
				cubcReqDto.setSignSituation("UNNORMAL_CONTRABAND");//违禁品
			}else if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS, cubcReqDto.getSignType())){
				cubcReqDto.setSignSituation("UNNORMAL_LOSTCARGO");//丢货
			}else if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS, cubcReqDto.getSignType())){
				cubcReqDto.setSignSituation("UNNORMAL_ABANDONGOODS");//弃货
			}
			logger.info("丢货，弃货，违禁品,签收情况："+cubcReqDto.getSignSituation());
		}
		//发起时间
		cubcReqDto.setRequestDate(new Date());
		//备注字段,拓展用 notes
		return cubcReqDto;
	}
}
