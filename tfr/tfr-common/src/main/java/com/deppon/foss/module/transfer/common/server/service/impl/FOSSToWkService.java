package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.AirTransportRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.CourierWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ExpressAirportEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKDepartCodeEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResStdEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseAndPageEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TruckTaskInfoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.AirExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.RepAirTransportDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.api.shared.vo.ResultBean;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.module.transfer.load.api.shared.vo.ToFossTaskVo;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportMakeDto;
import com.deppon.foss.util.DateUtils;

/**
 * @author 283250-foss-liuyi
 * @version 1.0
 * @description FOSS对接快递拆分, 客户端请求公共类
 * @update 2016年6月6日 下午8:32:27
 */
public class FOSSToWkService implements IFOSSToWkService {

    private static final Logger log = LoggerFactory.getLogger(FOSSToWkService.class);

    /**
     * @description 同步车辆任务到悟空系统(注意:本地址走ESB)
     * [改了车辆任务以后，这个方法就不走了。]
     * (non-Javadoc)
     * @author 283250-foss-liuyi
     * @update 2016年4月25日 下午3:44:26
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#updateTruckTaskToWk(com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity)
     */
    public TruckTaskInfoResponseDto pushTruckTaskToWk(TruckTaskInfoDto truckTaskInfo) {
    	
    	//ECS系统需求走ESB服务改成直连
        String code = "ESB_FOSS2ESB_CREATE_TRUCKTASK";
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.error("FOSS同步车辆任务到悟空系统到WK开始！" + url);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        TruckTaskInfoResponseDto responseParameter = new TruckTaskInfoResponseDto();

        try {
            String requestJsonStr = objectMapper.writeValueAsString(truckTaskInfo);
            log.error("FOSS同步车辆任务到悟空系统到WK开始！" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody=convertStreamToString(postMethod.getResponseBodyAsStream());
            //------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
            log.info("FOSS同步车辆任务到悟空系统到WK,快递响应结果" + responseBody);
            responseParameter = objectMapper.readValue(responseBody, TruckTaskInfoResponseDto.class);

            log.info("FOSS同步车辆任务到悟空系统到WK结束,响应信息：" + responseBody);
            return responseParameter;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("FOSS同步车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("FOSS同步车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        }

    }
    
    /**
     * Stream转化成String
     */
    private String convertStreamToString(InputStream is) {  
		   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
		        StringBuilder sb = new StringBuilder();   
		        String line = null;   
		        try {   
		            while ((line = reader.readLine()) != null) {   
		                sb.append(line + "/n");   
		            }   
		        } catch (IOException e) {   
		            e.printStackTrace();   

		        } finally {   
		            try {   
		                is.close();   
		            } catch (IOException e) {   
		                e.printStackTrace();   
		            }   
		        }   
		        	return sb.toString();   
    }  


    /**
     * @description 更新车辆任务到悟空系统(注意:本地址走ESB)
     * [改了车辆任务以后，这个方法就不走了。]
     * (non-Javadoc)
     * @author 283250-foss-liuyi
     * @update 2016年5月13日 上午11:51:40
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#updateTruckTaskToWk(com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoDto)
     */
    public TruckTaskInfoResponseDto updateTruckTaskToWk(TruckTaskInfoDto truckTaskInfo) {
        String code = "ESB_FOSS2ESB_UPDATE_TRUCKTASK_STATUS";
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.error("FOSS更新车辆任务到悟空系统到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        TruckTaskInfoResponseDto responseParameter = new TruckTaskInfoResponseDto();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(truckTaskInfo);
            log.error("FOSS更新车辆任务到悟空系统到WK开始！" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            //------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
            responseParameter = objectMapper.readValue(responseBody, TruckTaskInfoResponseDto.class);
            log.info("FOSS更新车辆任务到悟空系统到WK结束,响应信息：" + responseBody);
            return responseParameter;
        } catch (JsonGenerationException e) {
            log.error("FOSS更新车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("FOSS更新车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("FOSS更新车辆任务到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setBeSuccess(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        }
    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description 同步创建装车到快递系统(wk系统)(注意:本地址走ESB)
     * 这个方法已经废弃了，已经不用这个方法了
     * @version 1.0
     * @author 283250-foss-liuyi
     * @update 2016年4月25日 下午3:40:03
     */
    public ResponseParameterEntity sysncCreateLoadToWk(RequestParameterEntity requestParameter) throws Exception {
        String code = "ESB_FOSS2ESB_CREATE_LOADTASK_FROMFOSS";
        requestParameter.setType("sysncCreateLoadToWk");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.error("FOSS同步创建装车到悟空系统到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);

        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();

        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);

            //填入各个表单域的值
            /*	NameValuePair[] data = {
                       new NameValuePair("type", type),
                       new NameValuePair("requestEntity", requestEntity),
               };*/
            //将表单的值放入postMethod中
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            //------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
            responseParameter = objectMapper.readValue(responseBody, ResponseParameterEntity.class);

            log.info("FOSS同步创建装车到悟空系统到WK结束,响应信息：" + responseBody);
            return responseParameter;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步创建装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("FOSS同步创建装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("FOSS同步创建装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description 同步完成装车到快递系统(wk系统)(注意:本地址走ESB) 完成不需要告诉ECS系统，固该方法废弃
     * @version 1.0
     * @author 283250-foss-liuyi
     * @update 2016年4月25日 下午3:40:03
     */
    public ResponseParameterEntity sysncFinishCreateLoadToWk(RequestParameterEntity requestParameter) throws Exception {
        String code = "ESB_FOSS2ESB_SUBMIT_LOAD_TASK";
        requestParameter.setType("sysncFinishCreateLoadToWk");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.error("FOSS同步完成装车到悟空系统到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);

        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);

            //填入各个表单域的值
            /*	NameValuePair[] data = {
                       new NameValuePair("type", type),
                       new NameValuePair("requestEntity", requestEntity),
               };*/
            //将表单的值放入postMethod中
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            //------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
            responseParameter = objectMapper.readValue(responseBody, ResponseParameterEntity.class);

            log.info("FOSS同步完成装车到悟空系统到WK结束,响应信息：" + responseBody);
            return responseParameter;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步完成装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("FOSS同步完成装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("FOSS同步完成装车到悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return responseParameter;
        }

    }

    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询卸车托盘绑定管理信息
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月10日
     */
    public String queryUnloadTaskbindTrayListExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("FOSS查询卸车托盘绑定管理信息开始！");
        requestParameter.setType("queryUnloadTaskbindTrayListExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.queryUnloadTaskbindTrayListExpress");
        //String url = "http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressBindTrayToFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();

        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("FOSS查询卸车托盘绑定管理信息请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("FOSS查询卸车托盘绑定管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("FOSS查询卸车托盘绑定管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("FOSS查询卸车托盘绑定管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @description 根据卸车任务编号获取快递卸车明细(注意:本地址走不走ESB)
     * (non-Javadoc)
     * @author 328768-foss-gaojianfu
     * @update 2016年4月28日 上午10:18:58
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#queryUnloadExpressTaskDetail(java.lang.String)
     */
    @Override
    public FossToWKResponseEntity queryUnloadExpressTaskDetail(String requestParameter) throws Exception {
        HttpClient httpClient = new HttpClient();

        String url = PropertiesUtil.getKeyValue("ecs.unload.queryExpressUnloadTaskDetailByUnloadTaskNo");
        log.info("FOSS从悟空系统查询卸车明细开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {

            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS从悟空系统查询卸车明细返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();
            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);

            if (fossToWKResponseEntity == null) {
                log.error("FOSS从悟空系统查询卸车明细失败!");
                return fossToWKResponseEntity;
            }

            log.info("FOSS从悟空系统查询卸车明细结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS从悟空系统查询卸车明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS从悟空系统查询卸车明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS从悟空系统查询卸车明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }


    /**
     * @description: 根据航空正单查询运单信息(注意:本地址走不走ESB)
     * @author 268220-chenmin
     * @date 2016-4-28 下午4:05:59
     * @version V1.0
     */
    public ResultBean<List<RepAirTransportDto>> queryTicketInfo(AirTransportRequestEntity entity) {
        HttpClient httpClient = new HttpClient();
        //String url = "http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryWaitForAirWaybillToFoss";
        String url = PropertiesUtil.getKeyValue("ecs.airfreight.queryWayBillInfo");
        log.error("FOSS调用悟空系统接口查询合票明细开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");

        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        postMethod.addRequestHeader("Accept", "application/json");

//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);

        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResultBean<List<RepAirTransportDto>> resultBean = new ResultBean<List<RepAirTransportDto>>();

        try {
            String requestJsonStr = objectMapper.writeValueAsString(entity);


            //将表单的值放入postMethod中
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            if (responseBody.contains("exceptionCode") &&
                    responseBody.contains("S000099")) {
                log.error("FOSS查询合票明细调用悟空系统到WK，反回'S000099'和'exceptionCode'");
                return null;

            }
            //------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
            TypeReference<ResultBean<List<RepAirTransportDto>>> jtype = new TypeReference<ResultBean<List<RepAirTransportDto>>>() {
            };
            resultBean = objectMapper.readValue(responseBody, jtype);

            log.info("FOSS查询合票明细调用悟空系统到WK结束,响应信息：" + responseBody);
            return resultBean;
        } catch (JsonGenerationException e) {
            log.error("FOSS查询合票明细调用悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            resultBean.setStatus(0);
            resultBean.setExMsg(e.getMessage());
            return resultBean;
        } catch (JsonMappingException e) {
            log.error("FOSS查询合票明细调用悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            resultBean.setStatus(0);
            resultBean.setExMsg(e.getMessage());
            return resultBean;
        } catch (IOException e) {
            log.error("FOSS查询合票明细调用悟空系统到WK失败：" + e.getMessage());
            e.printStackTrace();
            resultBean.setStatus(0);
            resultBean.setExMsg(e.getMessage());
            return resultBean;
        }
    }

    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询卸车托盘绑定明细管理信息
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月14日
     */
    public String queryUnloadbindTrayDetailByUnloadTaskNoExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("FOSS查询卸车托盘绑定明细管理信息开始！");
        requestParameter.setType("queryUnloadbindTrayDetailByUnloadTaskNoExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.queryUnloadTaskbindTrayListExpressDetail");
        //String url="http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressTrayDetailFromFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("FOSS查询卸车托盘绑定明细管理信息请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("FOSS查询卸车托盘绑定明细管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("FOSS查询卸车托盘绑定明细管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("FOSS查询卸车托盘绑定明细管理信息失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @description 根据卸车运单明细编号到悟空系统查询卸车扫描明细
     * (non-Javadoc)
     * @author 328768-foss-gaojianfu
     * @update 2016年4月28日 上午9:03:22
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#queryUnloadSerialNoByExpressUnloadWaybillDetailId(java.lang.String)
     */
    @Override
    public FossToWKResponseEntity queryExpressUnloadSerialNo(String requestParameter) throws Exception {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.queryUnloadSerialNoByExpressUnloadWaybillDetailId");
        log.info("FOSS从悟空系统查询卸车扫描明细开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;
        try {

            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS从悟空系统查询卸车扫描明细回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();
            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS从悟空系统查询卸车扫描明细失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS从悟空系统查询卸车扫描明细结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS从悟空系统查询卸车扫描明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS从悟空系统查询卸车扫描明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS从悟空系统查询卸车扫描明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @description FOSS同步新建卸车任务给悟空
     * (non-Javadoc)
     * @author 328768-foss-gaojianfu
     * @update 2016年5月10日 上午11:15:07
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#syncNewExpressUnloadTaskToWk(java.lang.String)
     */
    @Override
    public FossToWKResponseEntity syncNewExpressUnloadTaskToWk(String requestParameter) throws Exception {
//        String code = "ESB_FOSS2ESB_CREATE_UNLOADTASK";
        HttpClient httpClient = new HttpClient();
        //ECS系统需求改成接口直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.createUnloadTask");
        log.info("FOSS同步卸车任务到悟空系统到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("FOSS同步卸车任务到悟空系统到WK返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步卸车任务到悟空系统到WK失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询叉车工作量管理
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月16日
     */
    public String queryTrayScanListExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("FOSS查询叉车工作量管理开始！");
        requestParameter.setType("queryTrayScanListExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.queryTrayOfflineScanListExpress");
        //String url="http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressTrayScanTaskToFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("FOSS查询叉车工作量管理请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("FOSS查询叉车工作量管理失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("FOSS查询叉车工作量管理失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("FOSS查询叉车工作量管理失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description FOSS同步确认卸车任务给悟空
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月10日 上午11:27:22
     */
    @Override
    public FossToWKResponseEntity syncConfirmExpressUnloadTaskToWk(String requestParameter) throws Exception {
//        String code = "ESB_FOSS2ESB_CONFIRM_UNLOADTASK";
        HttpClient httpClient = new HttpClient();
        //ECS系统需求改成接口直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.confirmUnloadTask");
        log.info("FOSS同步确认卸车任务到悟空系统WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS同步确认卸车任务到悟空系统WK返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }
            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();
            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步确认卸车任务到悟空系统到WK失败!");
                return fossToWKResponseEntity;
            }
            log.info("FOSS同步确认卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步确认卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步确认卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步确认卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description FOSS同步修改卸车任务到悟空系统
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 上午11:28:49
     */
    public FossToWKResponseEntity syncupdateExpressUnloadTask(String requestParameter) throws Exception {
//        String code = "ESB_FOSS2ESB_UPDATE_UNLOADTASK";
        HttpClient httpClient = new HttpClient();
        //ECS系统需求改成接口直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.updateUnloadTask");
        log.info("FOSS同步修改任务到悟空系统到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            log.debug("修改卸车任务到悟空系统返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步修改卸车任务到WK失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步修改卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步修改卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步修改卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步修改卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询叉车工作量管理明细
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月18日
     */
    public String queryWaybillByTaskNoExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("查询叉车工作量管理明细开始！");
        requestParameter.setType("queryWaybillByTaskNoExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.load.queryWaybillByTaskNoExpress");
        //String url="http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressTrayScanDetailToFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
        //	postMethod.addRequestHeader("version","0.1");
        //	postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("查询叉车工作量管理明细请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("查询叉车工作量管理明细失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("查询叉车工作量管理明细失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("查询叉车工作量管理明细失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description FOSS同步取消卸车任务到悟空系统
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 上午11:28:49
     */
    public FossToWKResponseEntity syncCancelUnloadTaskToWk(String requestParameter) throws Exception {
//        String code = "ESB_FOSS2ESB_CANCEL_UNLOADTASK";
        HttpClient httpClient = new HttpClient();
        //ECS系统需求给改成接口直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.cancleUnloadTask");
        log.info("FOSS同步取消卸车任务到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            log.debug("FOSS同步取消卸车任务到悟空返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步取消卸车任务到WK失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步取消卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步取消卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步取消卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步取消卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询离线叉车工作量
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月19日
     */
    public String queryTrayOfflineScanListExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("查询离线叉车工作量开始！");
        requestParameter.setType("queryTrayOfflineScanListExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.load.queryTrayScanListExpress");
        //String url="http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressForkWorkedToFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();
        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("查询离线叉车工作量请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("查询离线叉车工作量失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("查询离线叉车工作量失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("查询离线叉车工作量失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description Foss同步取消分配卸车任务到悟空
     * ECS系统不要这个功能，接口废弃
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 下午4:15:10
     */
    public FossToWKResponseEntity syncCancelAssignUnloadTaskToWk(String requestParameter) throws Exception {
        String code = "ESB_FOSS2ESB_CANCEL_ASSINGDED_UNLOAD_TASK";

        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.info("FOSS同步取消分配卸车任务到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS同步取消分配卸车任务到WK返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步取消分配卸车任务到WK失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步取消分配卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步取消分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步取消分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步取消分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description Foss同步分配卸车任务到悟空
     * ECS系统不要这个功能，接口废弃
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 下午4:36:25
     */
    @Override
    public FossToWKResponseEntity syncAssignUnloadTaskToWk(String requestParameter) throws Exception {
        String code = "ESB_FOSS2ESB_OPERATE_ASSING_UNLOADTASK";

        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
        log.info("FOSS同步分配卸车任务到WK开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS同步分配卸车任务到WK返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步分配卸车任务到WK失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步分配卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步分配卸车任务到悟空系统到失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }


    /**
     * @param requestParameter
     * @return String
     * @throws Exception
     * @description 查询待叉区货物
     * @version 1.0
     * @author 328060-foss-yekai
     * @update 2016年5月19日
     */
    public String querySubForkAreaGoodsExpress(RequestParameterEntity requestParameter) throws Exception {
        log.error("查询待叉区货物开始！");
        requestParameter.setType("querySubForkAreaGoodsExpress");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.querySubForkAreaGoodsExpress");
        //String url="http://10.224.228.166:8081/tfr-opt-service/v1/ecs/tfr/queryExpressWaitForForkGoodsToFoss";
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Accept", "application/json");
        postMethod.addRequestHeader("Content-type", "application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        ResponseParameterEntity responseParameter = new ResponseParameterEntity();

        try {
            String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
            log.info("查询待叉区货物请求的参数:" + requestJsonStr);
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.info("返回的字符串：" + responseBody);
            return responseBody;
        } catch (JsonGenerationException e) {
            log.error("查询待叉区货物失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (JsonMappingException e) {
            log.error("查询待叉区货物失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        } catch (IOException e) {
            log.error("查询待叉区货物失败：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setResultFlag(false);
            responseParameter.setFailureReason(e.getMessage());
            return "";
        }

    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description 通过卸车任务编号从悟空系统 查询所有交接单据
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月3日 上午9:20:00
     */
    public FossToWKResponseEntity queryUnloadWaybillDetailFromWk(String requestParameter) throws Exception {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.expressLoadUnloadTaskInfo");
        log.info("从WK系统查询单据明细开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            log.debug("从WK系统查询单据明细返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("从WK系统查询单据明细失败!");
                return fossToWKResponseEntity;

            }

            log.info("从WK系统查询单据明细结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("从WK系统查询单据明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("从WK系统查询单据明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("从WK系统查询单据明细失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description 根据交接单编号从WK系统查询笼号，包号，运单号
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月3日 上午10:33:42
     */
    public FossToWKResponseEntity expressQueryWaybillListByHandOverBillNo(String requestParameter) throws Exception {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.expressQueryWaybillListByHandOverBillNo");
        log.info("从WK系统查询笼号,包号,运单号开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("从WK系统查询笼号,包号,运单号返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("从WK系统查询笼号,包号,运单号失败!");
                return fossToWKResponseEntity;

            }

            log.info("从WK系统查询笼号,包号,运单号结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("从WK系统查询笼号,包号,运单号失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("从WK系统查询笼号,包号,运单号失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("从WK系统查询笼号,包号,运单号失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }


    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description 添加快递多货时，校验输入的运单号、流水号是否合法
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月3日 上午10:33:42
     */
    public FossToWKResponseEntity validateExpressWaybillNoAndSerialNo(String requestParameter) throws Exception {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.validateCargono");
        log.info("检验快递运单号、流水号是否合法开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("检验快递运单号、流水号是否合法返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("检验快递运单号、流水号是否合法失败!");
                return fossToWKResponseEntity;

            }

            log.info("检验快递运单号、流水号是否合法结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("检验快递运单号、流水号是否合法失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("检验快递运单号、流水号是否合法失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("检验快递运单号、流水号是否合法失败：" + e.getMessage());
            e.printStackTrace();
            return fossToWKResponseEntity;
        }
    }


    /**
     * @param requestParameter
     * @return
     * @throws Exception
     * @description Foss同步pda确认卸车任务到wk系统
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月18日 下午5:26:27
     */
    @Override
    public FossToWKResponseEntity syncPDAConfirmUnloadTaskToWk(String requestParameter) throws Exception {
        log.info("Foss同步pda确认卸车任务到wk系统开始！");
//        String code = "ESB_FOSS2ESB_CONFIRM_PDA_UNLOADTASK";

        HttpClient httpClient = new HttpClient();
        //ECS系统需求接口改成直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.syncUnloadTask");
        log.info("Foss同步pda确认卸车任务到wk系统开始！"+url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.error("Foss同步pda确认卸车任务到wk系统返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("Foss同步pda确认卸车任务到wk系统失败!");
                return fossToWKResponseEntity;

            }

            log.info("Foss同步pda确认卸车任务到wk系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("Foss同步pda确认卸车任务到wk系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("Foss同步pda确认卸车任务到wk系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("Foss同步pda确认卸车任务到wk系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        }
    }


    /**
     * @param requestParameter
     * @return
     * @description 同步PDA创建卸车任务到悟空系统
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月19日 上午9:56:31
     */
    @Override
    public FossToWKResponseEntity syncPDACreateUnloadTaskToWk(String requestParameter) {
//        String code = "ESB_FOSS2ESB_CREATE_UNLOADTASK";
        HttpClient httpClient = new HttpClient();
        //ECS系统需求接口改成直连
        String url = PropertiesUtil.getKeyValue("ecs.unload.createUnloadTask");
        log.info("FOSS同步PDA创建卸车任务到悟空系统开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS同步PDA创建卸车任务到悟空系统返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS同步PDA创建卸车任务到悟空系统失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS同步PDA创建卸车任务到悟空系统结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS同步PDA创建卸车任务到悟空系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        } catch (JsonMappingException e) {
            log.error("FOSS同步PDA创建卸车任务到悟空系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        } catch (IOException e) {
            log.error("FOSS同步PDA创建卸车任务到悟空系统失败：" + e.getMessage());
            return fossToWKResponseEntity;
        }
    }

    /**
     * @param requestJsonStr
     * @return
     * @description 查询交接单号list
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年6月8日 上午8:39:10
     */
    public FossToWKResponseEntity queryExpressHandOverBillListByWaybillNo(String requestJsonStr) {

        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.unload.queryExpressHandOverBillListByWaybillNo");
        log.info("FOSS通过运单号从悟空系统查询交接单list开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseEntity fossToWKResponseEntity = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            log.debug("FOSS从悟空系统查询交接单list返回结果" + responseBody);

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                return fossToWKResponseEntity;
            }

            //json工具类
            ObjectMapper objectMapper = null;
            objectMapper = obtainJSONObjectMapper();

            fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);
            if (fossToWKResponseEntity == null) {
                log.error("FOSS从悟空系统查询交接单list失败!");
                return fossToWKResponseEntity;

            }

            log.info("FOSS从悟空系统查询交接单list结束,响应信息：" + responseBody);
            return fossToWKResponseEntity;
        } catch (JsonGenerationException e) {
            log.error("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
            throw new TfrBusinessException("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
        } catch (JsonMappingException e) {
            log.error("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
            throw new TfrBusinessException("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
        } catch (IOException e) {
            log.error("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
            throw new TfrBusinessException("FOSS从悟空系统查询交接单list失败：" + e.getMessage());
        }
    }


    /**
     * @description 同步临时租车快递运单到悟空系统(按单号查询)
     * (non-Javadoc)
     * @author 313352-foss-gouyangyang
     * @update 2016年5月13日 下午3:44:26
     * @version V1.0
     */
    public CourierWaybillEntity expressDeliveryToWk(ExpressDateDto expressDateDto) {
        log.error("FOSS同步临时租车快递运单到悟空系统开始！");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esc.upload.expressDeliveryToWk");
          //String url = "http://10.230.20.146/tfr-opt-service/v1/ecs/tfr/queryWaybillToFoss";
        httpClient.getParams().setContentCharset("UTF-8");
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        CourierWaybillEntity courierWaybillEntity = null;
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        try {
            //请求json参数
            String jsonStr = objectMapper.writeValueAsString(expressDateDto);
            RequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);

            log.error("FOSS同步临时租车快递运单到悟空系统开始！" + jsonStr);
            System.out.println("requestJsonStr.toString()" + jsonStr);

            String responseBody = postMethod.getResponseBodyAsString();
            if (responseBody == null) {
                log.error("请求失败，返回结果" + responseBody);
                courierWaybillEntity = new CourierWaybillEntity();
                courierWaybillEntity.setStatus("0");
                courierWaybillEntity.setExMsg("请求失败,或者接口服务不通,请核实!");
            }

            // 接收
            courierWaybillEntity = objectMapper.readValue(responseBody, CourierWaybillEntity.class);
            log.debug("FOSS从悟空系统查询快递运单(时间段)list返回结果" + responseBody);
            if (courierWaybillEntity == null) {
                log.error("请求失败，返回结果" + responseBody);
                return courierWaybillEntity;
            }
            log.info("FOSS同步临时租车快递运单到悟空系统WK结束,响应信息：" + requestEntity);
            return courierWaybillEntity;
        } catch (Exception e) {
            log.error("FOSS同步临时租车快递运单到悟空WK失败：" + e.getMessage());
            e.printStackTrace();
            courierWaybillEntity = new CourierWaybillEntity();
            courierWaybillEntity.setStatus("0");  // 接口调用失败
            courierWaybillEntity.setExMsg(e.getMessage());//错误信息
            return courierWaybillEntity;
        }

    }

    /**
     * @description 同步临时租车快递运单到悟空系统(按日期查询)
     * (non-Javadoc)
     * @author 313352-foss-gouyangyang
     * @update 2016年5月29日 下午3:44:26
     * @version V1.0
     */
    public CourierWaybillEntity expressDeliveryMarkToWk(ExpressDateDto expressDateDto) {
        log.error("FOSS同步临时租车快递运单到悟空系统开始！");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("esc.upload.expressDeliveryToWk");
        //String url = "http://10.230.20.146/tfr-opt-service/v1/ecs/tfr/queryWaybillToFoss";
        httpClient.getParams().setContentCharset("UTF-8");
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        CourierWaybillEntity courierWaybillEntity = null;
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        try {
            //请求json参数
            String jsonStr = objectMapper.writeValueAsString(expressDateDto);
            RequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);

            log.error("FOSS同步临时租车快递运单到悟空系统开始！" + jsonStr);
            System.out.println("requestJsonStr.toString()" + jsonStr);

            String responseBody = postMethod.getResponseBodyAsString();
            if (responseBody == null) {
                log.error("请求失败，返回结果" + responseBody);
                courierWaybillEntity = new CourierWaybillEntity();
                courierWaybillEntity.setStatus("0");
                courierWaybillEntity.setExMsg("请求失败,或者接口服务不通,请核实!");
            }

            // 接收
            courierWaybillEntity = objectMapper.readValue(responseBody, CourierWaybillEntity.class);
            log.debug("FOSS从悟空系统查询快递运单(时间段)list返回结果" + responseBody);
            if (courierWaybillEntity == null) {
                log.error("请求失败，返回结果" + responseBody);
                return courierWaybillEntity;
            }
            log.info("FOSS同步临时租车快递运单到悟空系统WK结束,响应信息：" + requestEntity);
            return courierWaybillEntity;
        } catch (Exception e) {
            log.error("FOSS同步临时租车快递运单到悟空WK失败：" + e.getMessage());
            e.printStackTrace();
            courierWaybillEntity = new CourierWaybillEntity();
            courierWaybillEntity.setStatus("0");  // 接口调用失败
            courierWaybillEntity.setExMsg(e.getMessage());//错误信息
            return courierWaybillEntity;
        }
    }


    /**
     * 设置 json 数据格式
     *
     * @author ibm-liuzhaowei
     * @date 2013-08-01 上午9:21:30
     */
    public static ObjectMapper obtainJSONObjectMapper() {
        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);

        return objectMapper;
    }

    @Override
    public Object QueryWKLoadTaskDetail(String requestParameter){


        String code = "ecs.load.queryLoadTaskDetail";

        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue(code);
        if (log.isInfoEnabled()) {
            log.info("从WK系统查询悟空装车明细开始！" + url);
            log.info("参数是:" + requestParameter);
        }

        //	url = "http://10.230.20.80/tfr-opt-service/v1/ecs/tfr/queryLoadTaskDetailToFoss";

        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        Object res = null;

        try {
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            if (responseBody == null) {
                log.error("请求失败，返回结果" + responseBody);
                return res;
            }

            log.info("从WK系统查询悟空装车明细开始,响应信息：" + responseBody);

            Map<String, Object> map = JSON.parseObject(responseBody);

            if ("1".equals(map.get("status").toString())) {
                ToFossTaskVo taskvo = (ToFossTaskVo) JSON.parseObject(map.get("data").toString(), ToFossTaskVo.class);
                return taskvo;
            }


            return null;
        } catch (JsonGenerationException e) {
            log.error("从WK系统查询悟空装车明细开始失败：" + e.getMessage());
            e.printStackTrace();
            return res;
        } catch (JsonMappingException e) {
            log.error("从WK系统查询悟空装车明细开始失败：" + e.getMessage());
            e.printStackTrace();
            return res;
        } catch (IOException e) {
            log.error("从WK系统查询悟空装车明细开始失败：" + e.getMessage());
            e.printStackTrace();
            return res;
        }
    }

    /**
     * @description FOSS从悟空系统查询快递卸车差异信息
     * (non-Javadoc)
     * @author 332209-foss-ruilibao
     * @update 2016-5-24 下午5:23:14
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpressDiffList(java.lang.String)
     */
    @Override
    public FossToWKResponseAndPageEntity getExpressDiffList(
            String diffReportNo, String orgCode, int limit, int start) {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.load.queryUnloadDiffDetailToFoss");
        //String url = "http://10.230.20.80/tfr-opt-service/v1/ecs/tfr/queryUnloadDiffDetailToFoss";
        //String url = "http://10.224.228.168:8080/tfr-opt-service/v1/ecs/tfr/queryUnloadDiffDetailToFoss";
        PostMethod postMethod = new PostMethod(url);
        log.info("FOSS从悟空系统查询快递卸车差异信息开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseAndPageEntity responseEntity = new FossToWKResponseAndPageEntity();
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("diffReportNo", diffReportNo);  // 差异编号
            map.put("orgCode", orgCode);  // 部门
            map.put("pageSize", String.valueOf(limit));  // 页大小
            map.put("currentPageNo", String.valueOf(start)); // 当前页

            String requestParameter = JSONObject.fromObject(map).toString();
            RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            if (StringUtils.isEmpty(responseBody)) {
                log.error("请求失败，返回结果" + responseBody);
                responseEntity.setStatus("0");
                responseEntity.setExMsg("请求失败");
                return responseEntity;
            }

            responseEntity = objectMapper.readValue(responseBody, FossToWKResponseAndPageEntity.class);

            if ("1".equals(responseEntity.getStatus())) {
                log.info("FOSS从悟空系统查询快递卸车差异信息结束,响应信息：" + responseBody);
                return responseEntity;
            } else {
                log.error(responseEntity.getExMsg());
                return null;
            }
        } catch (JsonGenerationException e) {
            log.error("FOSS从悟空系统查询快递卸车差异信息失败：" + e.getMessage());
            responseEntity.setStatus("0");
            responseEntity.setExMsg("FOSS请求悟空系统异常");
            throw new TfrBusinessException(responseEntity.getExMsg());
        } catch (JsonMappingException e) {
            log.error("FOSS从悟空系统查询快递卸车差异信息失败：" + e.getMessage());
            responseEntity.setStatus("0");
            responseEntity.setExMsg("FOSS请求悟空系统异常");
            throw new TfrBusinessException(responseEntity.getExMsg());
        } catch (IOException e) {
            log.error("FOSS从悟空系统查询快递卸车差异信息失败：" + e.getMessage());
            responseEntity.setStatus("0");
            responseEntity.setExMsg("FOSS请求悟空系统异常");
            throw new TfrBusinessException(responseEntity.getExMsg());
        }
    }


    /**
     * @description FOSS从悟空系统查询快递卸车差异信息(添加少货和多货)
     * (non-Javadoc)
     * @author 313352-foss-gouyangyang
     * @update 2016-5-24 下午5:23:14
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpressDiffList(java.lang.String)
     */
    @Override
    public Object getExpressLessCargoList(String[] str, Date startDate, Date endDate) {
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.tfr.opt.service.queryUnloadDiffReportToFossUrl");
        //String url = "http://10.230.20.80/tfr-opt-service/v1/ecs/tfr/queryUnloadDiffReportToFoss";
        //String url = "http://10.224.228.168:8080/tfr-opt-service/v1/ecs/tfr/queryUnloadDiffReportToFoss";
        PostMethod postMethod = new PostMethod(url);
        log.info("FOSS从悟空系统查询快递卸车差异信息开始！" + url);
        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        FossToWKResponseAndPageEntity responseEntity = new FossToWKResponseAndPageEntity();
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        try {
        	QueryUnloadDiffReportMakeDto diffRepDto = new QueryUnloadDiffReportMakeDto();
            diffRepDto.setOrgCode(str[0]);
            diffRepDto.setVehicleNo(str[1]);
            diffRepDto.setUnloadTaskNo(str[2]);
            diffRepDto.setLoadManCode(str[TransferConstants.SONAR_NUMBER_3]);
            diffRepDto.setHandleStatus(str[TransferConstants.SONAR_NUMBER_4]);
            diffRepDto.setDiffReportNo(str[TransferConstants.SONAR_NUMBER_5]);
            diffRepDto.setWaybillNo(str[TransferConstants.SONAR_NUMBER_6]);
            diffRepDto.setBeginCreateTime(startDate);
            diffRepDto.setEndCreateTime(endDate);
            String requestParameter = objectMapper.writeValueAsString(diffRepDto);
            RequestEntity requestEntity = new StringRequestEntity(requestParameter,"application/json","UTF-8");

            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            if (StringUtils.isNotEmpty(responseBody)) {
                log.error("请求成功，返回结果" + responseBody);
                responseEntity.setStatus("0");
                responseEntity.setExMsg("请求成功");
                Map<String, Object> resMap = JSON.parseObject(responseBody);
                if (resMap.containsKey("status")) {
                    if (resMap.get("status").toString().equals("1")) {
                        String data = resMap.get("data").toString();
                        if (StringUtils.isNotBlank(data)) {
                            List<UnloadDiffReportEntity> list = JSON.parseArray(data, UnloadDiffReportEntity.class);
                            return list;
                        }
                    }
                }
            }
        }catch(Exception e){
            log.error("FOSS从悟空系统查询快递卸车差异信息失败：" + e.getMessage());
            responseEntity.setStatus("0");
            responseEntity.setExMsg("FOSS请求悟空系统异常");
            throw new TfrBusinessException(responseEntity.getExMsg());
        }
        return  null;
    }

    /**
     * @description 同步装车给悟空
     * (non-Javadoc)
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#sysnCreateLoadToWK(java.lang.String)
     * @author 328864-foss-xieyang
     * @update 2016年5月30日 上午10:42:47
     * @version V1.0
     */
    @Override
    public Object sysnCreateLoadToWK (String reqMsg){
        if (log.isInfoEnabled()) {
            log.error("FOSS推送创建装车给悟空开始...");
            log.info("传给悟空的数据是:" + reqMsg);
        }
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒) 20S
        managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
        // 设置读数据超时时间(单位毫秒) 40S
        managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);
        //ECS系统需求改成接口直连的方式
        String url = PropertiesUtil.getKeyValue("ecs.sys.createLoadTask");
//		String url = "http://10.224.228.121:8081/tfr-opt-service/v1/ecs/tfr/createLoadTaskFromFoss";
        log.error("sysnCreateLoadToWK url = " + url);
        PostMethod postMethod = new PostMethod(url);

//		int result = 0; // 失败
        Map<String, String> resMap = new HashMap<String, String>();

        try {
            StringRequestEntity requestEntity = new StringRequestEntity(reqMsg, "application/json", "UTF-8");
            // 设置字符集编码
            postMethod.getParams().setContentCharset("UTF-8");
            postMethod.getParams().setHttpElementCharset("UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            httpClient.getState();
            String responseStr = postMethod.getResponseBodyAsString();
            java.util.Map<String, Object> map = JSON.parseObject(responseStr);
            if (log.isInfoEnabled()) {
                log.info(map.toString());
            }
            if (map.containsKey("status") && map.get("status").toString().equals("1")) {
//				wkLoadTempService.deleteData(dto);
                resMap.put("result", "1");
                return resMap;
            }
            resMap.put("result", "0");
            resMap.put("errMsg", responseStr);
            log.info("responseStr:" + responseStr);
            return resMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resMap.put("result", "0");
            resMap.put("errMsg", e.getMessage());
            return resMap;
        } finally {
            postMethod.releaseConnection();
        }
    }

    /**
     *
     * @description FOSS获取快递发车计划线路信息
     * (non-Javadoc)
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpresslineInfoFromWk(com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity)
     * @author 332209-foss-ruilibao
     * @update 2016-5-12 下午4:30:12
     * @version V1.0
     */
    @Override
    public FossToWKResStdEntity getExpresslineInfoFromWk (
            String origOrgCode, String destOrgCode)throws Exception {
        log.error("FOSS获取快递发车计划线路信息Service开始！");

        //String code = "ESB_CODE";
        // 悟空固定的获取快递发车计划线路URL
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.scheduling.getExpresslineInfoFromWk");
        //String url = "http://10.230.20.80/tfr-opt-service/v1/ecs/tfr/queryExpressLineToFoss";

        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        postMethod.addRequestHeader("Accept", "application/json");

        // json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();

        FossToWKResStdEntity responseParameter = new FossToWKResStdEntity();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("departureStationCode", origOrgCode);
            map.put("destinationCode", destOrgCode);
            String requestJsonStr = objectMapper.writeValueAsString(map);
            // 将表单的值放入 中
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            // JSON转换成实体
            // 其中包含List复杂类型
            responseParameter = objectMapper.readValue(responseBody, FossToWKResStdEntity.class);

            if (!StringUtils.equals("1", responseParameter.getStatus())) {
                log.error("获取悟空系统信息异常", responseParameter.getExMsg());
                return responseParameter;
            }

            log.info("FOSS获取快递发车计划线路信息Service结束！" + responseBody);
            return responseParameter;

        } catch (JsonGenerationException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatus("0");
            responseParameter.setExMsg(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatus("0");
            responseParameter.setExMsg(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatus("0");
            responseParameter.setExMsg(e.getMessage());
            return responseParameter;
        }
    }

    /**
     *
     * @description FOSS获取快递到达部门列表
     * (non-Javadoc)
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpresslineInfoFromWk(com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity)
     * @author 332209-foss-ruilibao
     * @update 2016-5-12 下午4:30:12
     * @version V1.0
     */
    @Override
    public FossToWKDepartCodeEntity getExpressArriveDepartListFromWk (
            String origOrgCode, String planType)throws Exception {

        log.error("FOSS获取快递到达部门列表Service开始！");

        // 悟空固定的获取快递到达部门列表URL
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.scheduling.getExpressArriveDepartListFromWk");
        //String url = "http://10.230.20.80/rut-business-service/v1/rut/rutservice/rutBase/queryNextStationList";
        log.error("获取快递到达部门列表URL:" + url);

        //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        postMethod.addRequestHeader("Accept", "application/json");

        // json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();

        FossToWKDepartCodeEntity responseParameter = new FossToWKDepartCodeEntity();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("origOrgCode", origOrgCode);
            map.put("planType", planType);
            String requestJsonStr = objectMapper.writeValueAsString(map);
            // 将表单的值放入 中
            RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);

            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();

            log.error("快递返回到达部门列表信息" + responseBody);

            // JSON转换成实体
            // 其中包含List复杂类型
            responseParameter = objectMapper.readValue(responseBody, FossToWKDepartCodeEntity.class);

            if (!StringUtils.equals("100", responseParameter.getStatusCode())) {
                log.error("获取悟空系统信息异常", responseParameter.getErrMsg());
                return responseParameter;
            }

            log.info("FOSS获取快递到达部门列表Service结束！" + responseBody);
            return responseParameter;

        } catch (JsonGenerationException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatusCode("0");
            responseParameter.setErrMsg(e.getMessage());
            return responseParameter;
        } catch (JsonMappingException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatusCode("0");
            responseParameter.setErrMsg(e.getMessage());
            return responseParameter;
        } catch (IOException e) {
            log.error("获取悟空系统信息异常：" + e.getMessage());
            e.printStackTrace();
            responseParameter.setStatusCode("0");
            responseParameter.setErrMsg(e.getMessage());
            return responseParameter;
        }
    }
    
    
    /**
    *
    * @description 调用悟空接口修改悟空交接单车牌号
    * (non-Javadoc)
    * @author 332209-foss-ruilibao
    * @update 2016-5-12 下午4:30:12
    * @version V1.0
    */
   @Override
   public TruckTaskInfoResponse editWkHandOverBillVehicleno(String vehicleno, String handoverBillNo, String operationOrgCode){
       
	   
	   log.error("调用悟空修改车牌号开始！ 车牌号是：" + vehicleno);
       log.error("调用悟空修改车牌号开始！ 交接单号：" + handoverBillNo);
       log.error("调用悟空修改车牌号开始！ 操作部门：" + operationOrgCode);


       //获取悟空的地址
       HttpClient httpClient = new HttpClient();
       String url = PropertiesUtil.getKeyValue("ecs.load.editWkHandOverBillVehicleno");
       //String url = "http://10.230.20.146/tfr-opt-service/v1/ecs/tfr/updateHandoverBillVehicleNo";
       log.error("调用悟空的地址是URL：" + url);
       
       //设置编码格式
       httpClient.getParams().setContentCharset("UTF-8");
       //设置链接超时
       httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
       //设置读取超时
       httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
       PostMethod postMethod = new PostMethod(url);
       postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
       postMethod.addRequestHeader("Accept", "application/json");

       // json工具类
       ObjectMapper objectMapper = null;
       objectMapper = obtainJSONObjectMapper();

       TruckTaskInfoResponse responseParameter = new TruckTaskInfoResponse();

		try {
			Map<String, String> map = new HashMap<String, String>();
			//更新交接单接口，需要传入的参数
			map.put("handoverBillNo", handoverBillNo);
			map.put("operationOrgCode", operationOrgCode);
			map.put("vehicleNo", vehicleno);
			
			Log.error("传入的参数JSON是 : " + map);
			
			String requestJsonStr = objectMapper.writeValueAsString(map);
			// 将表单的值放入 中
			RequestEntity requestEntity = new StringRequestEntity(requestJsonStr, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);

			// 执行方法
			httpClient.executeMethod(postMethod);
			// 获取悟空返回的BODY
			String responseBody = postMethod.getResponseBodyAsString();
			log.error("悟空返回的body：" + responseBody);
			// JSON转换成实体
			// 其中包含List复杂类型
			responseParameter = objectMapper.readValue(responseBody, TruckTaskInfoResponse.class);

			//调用接口失败
			if (responseParameter == null) {
				log.error("调用悟空修改交接单接口失败了！返回的对象是空");
				return responseParameter;
			}
			
			//调用接口失败
			if (!responseParameter.getBeSuccess()) {
				log.error("调用悟空修改交接单接口失败了！" + responseParameter.getFailureReason());
				return responseParameter;
			}
			
			//成功
			log.error("调用悟空修改车牌号结束！" + responseBody);
			return responseParameter;
			
		} catch (JsonGenerationException e) {
            log.error("调用悟空修改交接单接口失败了！失败原因" + e.getCause());
            return responseParameter;
		} catch (JsonMappingException e) {
            log.error("调用悟空修改交接单接口失败了！失败原因" + e.getCause());
            return responseParameter;
		} catch (JsonParseException e) {
            log.error("调用悟空修改交接单接口失败了！失败原因" + e.getCause());
            return responseParameter;
		} catch (HttpException e) {
			log.error("调用悟空修改交接单接口失败了！失败原因" + e.getCause());
            return responseParameter;
		} catch (IOException e) {
			log.error("调用悟空修改交接单接口失败了！失败原因" + e.getCause());
            return responseParameter;
		}
   }

    /**
	 * 同步提交装车任务给悟空
	 * <p>第二套改同步</p>
	 * @param jsonString
	 * @return
	 */
	@Override
	public Map<String, Object> sysnSubmitLoadToWK(String jsonString) {
		if(log.isInfoEnabled()) {
			log.error("FOSS同步提交装车给悟空开始..." + jsonString);
		}
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒) 20S
		managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
		// 设置读数据超时时间(单位毫秒) 40S
		managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);
		//ECS系统需求接口改成直连
		String url = PropertiesUtil.getKeyValue("ecs.sys.submitLoadTask");
		PostMethod postMethod = new PostMethod(url);
		log.info("sysnSubmitLoadToWK接口URL： " + url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
			// 设置字符集编码
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.getParams().setHttpElementCharset("UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			httpClient.getState();
			
			String responseStr = postMethod.getResponseBodyAsString();
			log.info("FOSS同步提交装车给悟空的结果str: " + responseStr);
			return  JSON.parseObject(responseStr);
			
		} catch (Exception e) {
			log.info("Exception " ,e);
			throw new TfrBusinessException(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	
    /**
     * @description 修改交接单到悟空系统(注意:本地址不走ESB)
     * 改造以后的车辆任务(车辆任务不同步给悟空了，只同步交接单状态)
     * (non-Javadoc)
     * @author foss-332209 ruilibao
     * @update 2016年4月25日 下午3:44:26
     * @version V1.0
     */
	@Override
    public Map<String, Object> editHandOverBillToWk(String jsonString) {
    	if(log.isInfoEnabled()) {
			log.error("FOSS同步修改悟空交接单开始 ：" + jsonString);
		}
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒) 20S
		managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
		// 设置读数据超时时间(单位毫秒) 40S
		managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);

//		String url = PropertiesUtil.getKeyValue("esb.rs") + "/FOSS_ECS_UPDATEHANDOVERBILLSTATE";
		//接口不走ESB，接口做直连
		String url = PropertiesUtil.getKeyValue("ecs.sys.updateHandoverBillState");
//		String url = "http://10.224.102.115:8080/tfr-opt-service/v1/ecs/tfr/updateHandoverBillStateToFoss";
		PostMethod postMethod = new PostMethod(url);
		log.error("editHandOverBillToWk接口URL： " + url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
			// 设置字符集编码
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.getParams().setHttpElementCharset("UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			httpClient.getState();
			
			String responseStr = postMethod.getResponseBodyAsString();
			log.error("FOSS同步修改悟空交接单的结果str: " + responseStr);
			log.error("FOSS同步修改悟空交接单结束...");
			return  JSON.parseObject(responseStr);
			
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
    }
	
	/**
     * @description 同步修改理货员信息到悟空系统
     * (non-Javadoc)
     * @author 328768-foss-gaojianfu
     * @update 2016年11月18日 上午10:18:58
     * @version V1.0
     * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#syncPDAmodifyLoaderToWk(java.lang.String)
     */
	@Override
	public FossToWKResponseEntity syncPDAmodifyLoaderToWk(String requestParameter) throws Exception {
		HttpClient httpClient = new HttpClient();

		String url = PropertiesUtil.getKeyValue("ecs.unload.syncPDAmodifyLoaderToWk");
		log.error("FOSS同步修改理货员信息到悟空系统开始！" + url);
		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
		FossToWKResponseEntity fossToWKResponseEntity = null;

		try {

			RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);

			httpClient.executeMethod(postMethod);
			String responseBody = postMethod.getResponseBodyAsString();
			log.error("FOSS同步修改理货员信息到悟空系统返回结果" + responseBody);

			if (StringUtils.isEmpty(responseBody)) {
				return fossToWKResponseEntity;
			}

			// json工具类
			ObjectMapper objectMapper = null;
			objectMapper = obtainJSONObjectMapper();
			fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);

			return fossToWKResponseEntity;
		} catch (JsonGenerationException e) {
			log.error("FOSS同步修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} catch (JsonMappingException e) {
			log.error("FOSS同步修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} catch (IOException e) {
			log.error("FOSS同步修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * @description 同步装车修改理货员信息到悟空系统
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年11月18日 上午10:15:23
	 */
	@Override
	public FossToWKResponseEntity syncPDALoadModifyLoaderToWk(String requestParameter) throws Exception {
		HttpClient httpClient = new HttpClient();

		String url = PropertiesUtil.getKeyValue("ecs.unload.syncPDALoadModifyLoaderToWk");
		log.error("FOSS同步装车修改理货员信息到悟空系统开始！" + url);
		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_10000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_60000);
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
		FossToWKResponseEntity fossToWKResponseEntity = null;

		try {

			RequestEntity requestEntity = new StringRequestEntity(requestParameter, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);

			httpClient.executeMethod(postMethod);
			String responseBody = postMethod.getResponseBodyAsString();
			log.error("FOSS同步装车修改理货员信息到悟空系统返回结果" + responseBody);

			if (StringUtils.isEmpty(responseBody)) {
				return fossToWKResponseEntity;
			}

			// json工具类
			ObjectMapper objectMapper = null;
			objectMapper = obtainJSONObjectMapper();
			fossToWKResponseEntity = objectMapper.readValue(responseBody, FossToWKResponseEntity.class);

			return fossToWKResponseEntity;
		} catch (JsonGenerationException e) {
			log.error("FOSS同步装车修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} catch (JsonMappingException e) {
			log.error("FOSS同步装车修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} catch (IOException e) {
			log.error("FOSS同步装车修改理货员信息到悟空系统失败：" + e.getMessage());
			return fossToWKResponseEntity;
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
     * @description 快递机场扫面单据(日期和单号)
     * (non-Javadoc)
     * @author 313352-foss-gouyangyang
     * @update 2016年12月06日 下午3:44:26
     * @version V1.0
     */
    public ExpressAirportEntity expressAirportToWk(AirExpressDateDto airExpressDateDto) {
        log.error("FOSS同步临时租车快递机场扫描单据开始！");
        HttpClient httpClient = new HttpClient();
        String url = PropertiesUtil.getKeyValue("ecs.tfr.opt.service.queryAirtaskListUrl");
        //String url = "http://ecsuat.deppon.com/tfr-opt-service/v1/ecs/tfr/queryAirtaskList";
        //String url = "http://10.230.20.80/tfr-opt-service/v1/ecs/tfr/queryAirtaskList";
        httpClient.getParams().setContentCharset("UTF-8");
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        ExpressAirportEntity expressAirportEntity = null;
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
        try {
            //请求json参数
            String jsonStr = objectMapper.writeValueAsString(airExpressDateDto);
            RequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);

            log.error("FOSS同步临时租车快递机场扫描单据开始！" + jsonStr);
            System.out.println("requestJsonStr.toString()" + jsonStr);

            String responseBody = postMethod.getResponseBodyAsString();
            if (responseBody == null) {
                log.error("请求失败，返回结果" + responseBody);
                expressAirportEntity = new ExpressAirportEntity();
                expressAirportEntity.setStatus(0);
                expressAirportEntity.setExMsg("请求失败,或者接口服务不通,请核实!");
            }
            // 接收
            expressAirportEntity = objectMapper.readValue(responseBody, ExpressAirportEntity.class);
            String   result = objectMapper.writeValueAsString(responseBody);
            log.debug("FOSS从悟空系统查快递机场扫描单(时间段)list返回结果" + result);
            log.info("FOSS同步临时租车快递机场扫描单到悟空系统WK结束,响应信息：" + result);
            return expressAirportEntity;
        } catch (Exception e) {
            log.error("FOSS同步临时租车快递机场扫描单到悟空WK失败：" + e.getMessage());
            e.printStackTrace();
            expressAirportEntity = new ExpressAirportEntity();
            expressAirportEntity.setStatus(0);  // 接口调用失败
            expressAirportEntity.setExMsg(e.getMessage());//错误信息
            return expressAirportEntity;
        }
    }

/**
     * @description 同步修改装车车牌号给悟空
     * @author 332209-foss-ruilibao
     * @version V1.0
     */
    @Override
    public Object sysnEditLoadToWkByTaskNo (String reqMsg){
        if (log.isInfoEnabled()) {
            log.error("FOSS推送装车修改车牌号给悟空开始...");
            log.info("传给悟空的数据是:" + reqMsg);
        }
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒) 20S
        managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
        // 设置读数据超时时间(单位毫秒) 40S
        managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);
        //ECS系统需求改成接口直连的方式
        String url = PropertiesUtil.getKeyValue("ecs.loadtask.editvehicleNo");
        //http://10.226.142.27:8080/tfr-opt-service/v1/ecs/tfr/updateLoadTaskVehicleNo
        log.error("sysnEditLoadToWkByTaskNo url = " + url);
        PostMethod postMethod = new PostMethod(url);

        Map<String, String> resMap = new HashMap<String, String>();

        try {
            StringRequestEntity requestEntity = new StringRequestEntity(reqMsg, "application/json", "UTF-8");
            // 设置字符集编码
            postMethod.getParams().setContentCharset("UTF-8");
            postMethod.getParams().setHttpElementCharset("UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            httpClient.getState();
            String responseStr = postMethod.getResponseBodyAsString();
            java.util.Map<String, Object> map = JSON.parseObject(responseStr);
            if (log.isInfoEnabled()) {
                log.info(map.toString());
            }
            if (map.containsKey("status") && map.get("status").toString().equals("1")) {
                resMap.put("result", "1");
                return resMap;
            }
            resMap.put("result", "0");
            resMap.put("errMsg", responseStr);
            log.info("responseStr:" + responseStr);
            return resMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resMap.put("result", "0");
            resMap.put("errMsg", e.getMessage());
            return resMap;
        } finally {
            postMethod.releaseConnection();
        }
    }

	
}
