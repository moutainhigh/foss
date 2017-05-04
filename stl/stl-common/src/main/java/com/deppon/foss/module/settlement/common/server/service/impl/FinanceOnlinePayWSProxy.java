package com.deppon.foss.module.settlement.common.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.OnlinePayInfoDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.domain.QueryUnusedAmountsRequest;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.domain.QueryUnusedAmountsResponse;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.domain.UpdateUnuserdAmountsRequest;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.domain.UpdateUnuserdAmountsResponse;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.onlinepayservice.CommException;
import com.deppon.foss.module.thirdpay.server.onlinepay.fins.esb.onlinepay.onlinepayservice.IOnlinePayService;
import com.deppon.foss.util.UUIDUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.util.Map;


/**
 * Created by 105762 on 2014/9/24.
 */
public class FinanceOnlinePayWSProxy implements IFinanceOnlinePayWSProxy {
    /**
     * 占用
     */
    private static final String OBTAIN_OPTIONAL = "0";
    /**
     * 释放
     */
    private static final String RELEASE_OPTIONAL = "1";
    /**
     * 成功
     */
    private static final String SUCCESS = "1";
    /**
     * 失败
     */
    private static final String FAILED = "0";
    
    /**
     * 更改财务自助网上支付占用部门接口编码 
     */
    private static final String FINS_ESB2FINS_FOSS_UPDATEDEPT_FINS = "/FINS_ESB2FINS_FOSS_UPDATEDEPT_FINS";
    
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceOnlinePayWSProxy.class);


    /**
     * 财务自助接口
     */
    private IOnlinePayService onlinePayService;
    
    /**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;

    /**
     * 占用
     *
     * @param onlinePayCode
     * @param amount
     */
    public void obtain(String onlinePayCode, BigDecimal amount) {
        LOGGER.info("占用网上支付编码对应金额,编号:" + onlinePayCode + ",金额:" + amount);
        SettlementUtil.valideIsNull(onlinePayCode,"传入的网上支付编号为空");
        SettlementUtil.valideIsNull(amount,"传入的网上支付金额为空");
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new SettlementException("待占用金额必须大于零");
        }
        obtainOrReleaseSupport(onlinePayCode, amount, OBTAIN_OPTIONAL);

    }

    /**
     * 释放
     */
    public void release(String onlinePayCode, BigDecimal amount) {
        LOGGER.info("释放网上支付编码对应金额,编号:" + onlinePayCode + ",金额:" + amount);
        SettlementUtil.valideIsNull(onlinePayCode,"传入的网上支付编号为空");
        SettlementUtil.valideIsNull(amount,"传入的网上支付金额为空");
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new SettlementException("待占用金额必须大于零");
        }
        obtainOrReleaseSupport(onlinePayCode, amount, RELEASE_OPTIONAL);
    }

    /**
     * 查询
     */
    public OnlinePayInfoDto query(String onlinePayCode) {
        LOGGER.info("查询网上支付编码对应金额,编号:" + onlinePayCode);
        SettlementUtil.valideIsNull(onlinePayCode,"传入的网上支付编号为空");
        /*
        创建ESBHeader
         */
        ESBHeader header = new ESBHeader();
        header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_QUERY_OL_REMITINFO);
        //与业务相关的字段
        header.setBusinessId(onlinePayCode);
        header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
        header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
        //消息格式
        header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
        header.setRequestId(UUIDUtils.getUUID());
        //请求系统
        header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
        Holder<ESBHeader> holder = new Holder<com.deppon.esb.header.ESBHeader>(header);

        QueryUnusedAmountsRequest request = new QueryUnusedAmountsRequest();
        request.setOnlinePayCode(onlinePayCode);

        /*
         * 获取汇款信息
         */
        OnlinePayInfoDto dto = new OnlinePayInfoDto();
        try {
            QueryUnusedAmountsResponse response = onlinePayService.queryUnusedAmounts(holder, request);
            if (FAILED.equals(response.getIsSuccess())) {
                LOGGER.info("调用财务自助查询异常:" + response.getFalseReason());
                throw new SettlementException("调用财务自助查询异常:" + response.getFalseReason());
            } else {

               try{
                   dto.setRemittanceTime(SettlementUtil.xmlDateToDate(response.getRemittanceTime()));
               } catch (Exception e){
            	   LOGGER.error(e.getMessage(), e);
               }
                dto.setRemitterName(response.getRemitterName());
                dto.setUnuseredAmounts(response.getUnuseredAmounts());
            }
        } catch (CommException e) {
            LOGGER.info("调用财务自助查询异常:" + e.getMessage() + e.getStackTrace());
            throw new SettlementException("调用财务自助查询异常:" + e.getMessage());
        }
        return dto;
    }

    private void obtainOrReleaseSupport(String onlinePayCode, BigDecimal amount, String optional) {
        ESBHeader header = new ESBHeader();
        header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_OBTAIN_OL_REMIT_AMOUNT);
        //与业务相关的字段
        header.setBusinessId(onlinePayCode);
        header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
        header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
        //消息格式
        header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
        header.setRequestId(UUIDUtils.getUUID());
        //请求系统
        header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
        Holder<ESBHeader> holder = new Holder<com.deppon.esb.header.ESBHeader>(header);

        UpdateUnuserdAmountsRequest request = new UpdateUnuserdAmountsRequest();
        request.setOnlinePayCode(onlinePayCode);
        request.setAmounts(amount);
        request.setOperateType(optional);
        try {
            UpdateUnuserdAmountsResponse response = onlinePayService.updateUnuserdAmounts(holder, request);

            if(FAILED.equals(response.getIsSuccess())){
                throw new SettlementException("调用财务自助接口未成功:" + response.getFalseReason());
            }
        } catch (SettlementException e) {
            LOGGER.info("调用财务自助查询异常:" + e.getMessage() + e.getStackTrace());
            throw e;
        } catch (CommException e) {
            LOGGER.info("调用财务自助查询异常:" + e.getMessage() + e.getStackTrace());
            throw new SettlementException("调用财务自助接口异常:" + e.getMessage());
        }
    }

    /**
     * 悟空补码 达到应收网上支付 更改财务自助占用部门
     * 2016-10-02 update by 231434-bieyexiong-foss
     * 请求参数  onlinePayCode:网上支付编码
     * 			complementDeptCode:补码后占用部门
     * 响应参数  isSuccess:1表示调用成功,0表示调用失败
     * 			falseReason:当调用失败，返回失败信息
     */
	@Override
	public void updateObtainDept(Map<String,Object> map) {
		LOGGER.info("修改网上支付财务自助占用部门接口调用开始");
		//onlinePayCode:网上支付编码 ; complementDeptCode:补码后占用部门
		if(map == null || StringUtils.isBlank((String)map.get("onlinePayCode")) || StringUtils.isBlank((String)map.get("complementDeptCode"))){
			LOGGER.error("修改网上支付财务自助占用部门接口调用结束");
			throw new SettlementException("修改网上支付财务自助占用部门接口调用:请求参数为空");
		}
		LOGGER.info("修改网上支付财务自助占用部门接口调用开始:" + (String)map.get("onlinePayCode"));
		try {
			//将参数转换成json
			String reqJson = JSONObject.toJSONString(map);
			//创建请求实体
			RequestEntity requestEntity = new StringRequestEntity(reqJson, "application/json", "UTF-8");
			
			// 根据服务端的ESB_CODE查到esb地址
			FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(FINS_ESB2FINS_FOSS_UPDATEDEPT_FINS);

			if(configEntity == null || StringUtils.isBlank(configEntity.getEsbAddr())){
				LOGGER.error("\n\n修改网上支付财务自助占用部门从FOSS到财务自助系统请求接口-读取esb地址有误:"+(String)map.get("onlinePayCode"));
				throw new SettlementException("修改网上支付财务自助占用部门从FOSS到财务自助系统请求接口读取esb地址有误!");
			}
			String url = configEntity.getEsbAddr();
			
			//创建请求方式
			PostMethod post = new PostMethod(url);
			post.setRequestEntity(requestEntity);
			post.setRequestHeader("Content-Type","application/json;charset=UTF-8");
			
			//记录发送日志
			LOGGER.info("发送 修改网上支付财务自助占用部门 接口请求start：" + url + "," +reqJson);
			
			new HttpClient().executeMethod(post);
			
			//获取响应JSON
			String resJson = post.getResponseBodyAsString();
			//记录结算日志 若没有结束日志，表示请求发送异常
			LOGGER.info("发送 修改网上支付财务自助占用部门 接口请求end：" + url + "," +(String)map.get("onlinePayCode") + ",响应参数" +resJson);
			
			//响应实体  isSuccess:1表示调用成功,0表示调用失败 ; falseReason:当调用失败，返回失败信息
			JSONObject jsonObj = JSONObject.parseObject(resJson);

			if(jsonObj != null){
				String isSuccess = jsonObj.getString("isSuccess");
				//当不等于1时，表示财务自助处理失败失败
				if(!SUCCESS.equals(isSuccess)){
					LOGGER.info("修改网上支付财务自助占用部门接口调用结束:" + (String)map.get("onlinePayCode"));
					throw new SettlementException(jsonObj.getString("falseReason"));
				}
			}
			LOGGER.info("修改网上支付财务自助占用部门接口调用结束:" + (String)map.get("onlinePayCode"));
		} catch (SettlementException e) {
			throw new SettlementException("修改网上支付财务自助占用部门失败:" + e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error("修改网上支付财务自助占用部门接口调用失败");
			throw new SettlementException("修改网上支付财务自助占用部门接口调用失败:" + e.getMessage());
		} 
	}
    
    /**
     * setter
     *
     * @param onlinePayService
     */
    public void setOnlinePayService(IOnlinePayService onlinePayService) {
        this.onlinePayService = onlinePayService;
    }

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

}
