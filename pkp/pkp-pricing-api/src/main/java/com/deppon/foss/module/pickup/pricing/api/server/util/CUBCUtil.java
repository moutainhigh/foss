package com.deppon.foss.module.pickup.pricing.api.server.util;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultCUBCDto;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * Created by 343617 on 2016/10/28.
 * 和CUBC进行交互的通用工具类
 *
 */
public class CUBCUtil {
    private static Logger log = Logger.getLogger(CUBCUtil.class);

    /**
     *  Created by 343617 on 2016/10/28.
     *  @param url          同步接口地址
     *  @param transJson    请求数据（JSON格式的String）
     *  @param timeOut      超时时间
     */
    public static ResultCUBCDto synExecuteMethod(String url, String transJson, int timeOut) {

        if(StringUtil.isEmpty(url)){
            throw new BusinessException("Error URL");
        }

        //获得最终结果需要封装的类
        ResultCUBCDto resultCUBCDto = new ResultCUBCDto();
        //Post方法发送请求
        PostMethod method=null;
        //接收流中文本信息
        StringBuilder builder;
        //用来接收响应状态码
        int statusCode;

        try {
            method = new PostMethod(url);
            //设置请求格式
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            //设置请求参数，恢复策略，超时时间
            method.setRequestEntity(se);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0,false));
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            resultCUBCDto.setErrorMessage(e.getMessage());
            return resultCUBCDto;
        } catch (IllegalArgumentException e) {
            log.error("Error URL：" + url);
            resultCUBCDto.setErrorMessage(e.getMessage());
            return resultCUBCDto;
        }

        //设置连接超时时间连接初始化时间）
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);


        try {

            statusCode = httpClient.executeMethod(method);
            resultCUBCDto.setStatusCode(statusCode);
            //服务器返回的不是200代码，则统一认为抓取源码失败,并且记录原因
            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed: " + method.getStatusLine() + "\tstatusCode: " + statusCode);
            }
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = method.getResponseBodyAsStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                resultCUBCDto.setJsonResponse(builder.toString());
            } catch (IOException e) {
                //此处异常说明转换异常
                log.error(e.getMessage());
                resultCUBCDto.setErrorMessage(e.getMessage());
            }finally {
                //关闭流节约开销
                if(reader!=null){
                    reader.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            //此处异常说明超时
            log.error(e.getMessage());
            resultCUBCDto.setErrorMessage(e.getMessage());
        } finally {
            if (method != null) {
                //释放连接
                method.releaseConnection();
            }
        }
        return resultCUBCDto;
    }
}
