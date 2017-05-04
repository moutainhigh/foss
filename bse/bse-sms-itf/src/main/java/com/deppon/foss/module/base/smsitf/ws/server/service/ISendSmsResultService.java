/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.smsitf.ws.server.service;

import javax.jws.WebParam;
import javax.jws.WebService;


/**
 * 获取发送短信结果服务端接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-6-9 上午9:56:36 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-6-9 上午9:56:36
 * @since
 * @version
 */
@WebService
public interface ISendSmsResultService {
    
    public void sendSmsResultInfos(@WebParam(name="jsonArrayString")String jsonArrayString);

}
