package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.FossConfigEntityService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITaxPayerService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.TaxPayerInfoResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/12.
 * 通过该service从crm获取纳税人信息
 */
public class TaxPayerService implements ITaxPayerService {

    /**
     * 获取日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TaxPayerService.class);
    /**
     * esb地址配置Service
     */
    private FossConfigEntityService fossConfigEntityService;

    /**
     * 纳税人信息接口编码
     */
    private final static String CRM_TAXPAYER_SERVER_CODE = "/ESB_FOSS2ESB_SEND_TAXPAYER_INFO";
    /**
     * 通过传入客户编码
     * 调用纳税人信息接口获取纳税人信息
     * @param customerCodes 客户编码集合
     * @return
     */
    @Override
    public TaxPayerInfoResponse getTaxPayerByCustomerCode(List<String> customerCodes){
        //发送请求
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setContentCharset("UTF-8");
        //响应参数
        TaxPayerInfoResponse response = new TaxPayerInfoResponse();
        String responseBody = "";

        String url = "";
        //校验地址是否在ESB注册
        FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(CRM_TAXPAYER_SERVER_CODE);
        if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
            url = configEntity.getEsbAddr();
        } else {
            logger.error("\n\n获取纳税人信息读取esb地址有误:\n\n");
            throw new SettlementException("读取esb地址有误!");
        }
        //url="http://10.224.65.37:8080/crm-interface/ws/restful/fossToCrmRestfulService/sendTaxToFoss";
        PostMethod postMethod = new PostMethod(url);
        Map<String,List<String>> map = new HashMap<String, List<String>>();
        map.put("customerCodes",customerCodes);

        String json = JSONArray.toJSONString(map); //组装请求参数
       // Map<String,List<String>> aa = (Map<String,List<String>>)JSONArray.parse(json);
        try {
            logger.info("\n\nFoss获取纳税人信息请求参数:\n\n" + json);
            RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");

            // 执行postMethod
            httpClient.executeMethod(postMethod);
            // 获取返回值
            responseBody = postMethod.getResponseBodyAsString();
            if(responseBody==null||"".equals(responseBody)){
                logger.error("Foss获取纳税人信息响应为空");
                throw new Exception("Foss获取纳税人信息响应为空");
            }
            logger.info("Foss获取纳税人信息的响应信息:" + responseBody);

            // 将返回值转换成对象
            JSONObject returnJSON = JSONObject.parseObject(responseBody);
            response = JSONObject.toJavaObject(returnJSON, TaxPayerInfoResponse.class);

        }catch (Exception e) {
            throw new SettlementException(e.getMessage(),e);
        }  finally {
            if (null != postMethod) {
                postMethod.releaseConnection();
            }
        }
        logger.info("Foss获取纳税人信息结束....");
        return response;
    }


    public void setFossConfigEntityService(FossConfigEntityService fossConfigEntityService) {
        this.fossConfigEntityService = fossConfigEntityService;
    }
}
