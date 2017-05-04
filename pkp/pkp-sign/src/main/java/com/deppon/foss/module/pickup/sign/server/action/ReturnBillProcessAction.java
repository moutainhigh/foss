/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/ReturnBillProcessAction.java
 * 
 * FILE NAME        	: ReturnBillProcessAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.sign.api.server.service.IReturnBillProcessService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReturnBillProcessConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ReturnBillProcessVo;

/**
 * 签收单返单各类操作Action
 * 
 * @ClassName: ReturnBillProcessAction
 * @Description: 签收单返单各类操作Action
 * @author
 * @date 2012-10-25 上午11:02:25
 * 
 */
public class ReturnBillProcessAction extends AbstractAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBillProcessAction.class);

	/**
	 * 签收单返单Service
	 */
	private transient IReturnBillProcessService returnSignBillProcessService;

	/**
	 * 视图对象
	 */
	private ReturnBillProcessVo vo = new ReturnBillProcessVo();

	/**
	 * id 集合
	 */
	private String[] ids;

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;  
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;

	/**
	 * 查询签收单返单
	 * 
	 * @date 2012-11-21 下午1:58:34
	 */
	@JSON
	public String queryReturnBill() {
		try {
			Long totalCount = returnSignBillProcessService.getReturnBillProcessCount(vo.getSearchReturnBillProcessDto());
			if (totalCount > 0) {
				//查询签收单返单list
				vo.setRtSearchReturnBillProcessList(returnSignBillProcessService.searchReturnBillProcessList(vo.getSearchReturnBillProcessDto(), this.getStart(), this.getLimit()));
			}
			//查询签收单返单数量
			this.setTotalCount(totalCount);

		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}

	/**
	 * 根据id查询ReturnBillProcess
	 * 
	 * @date 2012-11-22 下午1:58:45
	 */
	@JSON
	public String queryBillProcessById() {
		try {
			//根据id查询ReturnBillProcess
			RtSearchReturnBillProcessDto rt = returnSignBillProcessService.searchReturnBillProcessById(vo.getSearchReturnBillProcessDto());
			rt.setVerifyTime(new Date());
			//verify date is default recent date
			vo.setRtSearchReturnBillProcessDto(rt);
			//set dto to vo

		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}

	/**
	 * 发送短信通知客户和营业员签单返回信息
	 * 
	 * @date 2012-11-22 下午1:58:45
	 */
	@JSON
	public String sendBillProcessSmsById() {
		try {
			//发送短信通知客户和营业员签单返回信息
			vo.setResultDto(returnSignBillProcessService.sendBillProcessSmsById(vo.getSearchReturnBillProcessDto()));

		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}

	/**
	 * 更新ReturnBillProcess
	 * 
	 * @date 2012-11-22 下午1:58:45
	 */
	@JSON
	public String updateReturnBillProcess() {
		try {
			//开单时间为最新时间
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setReturnbillTime(new Date());
			//状态为已经开单
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setReturnbillStatus(ReturnBillProcessConstants.ALREADY_RETURN_BILL);

			//创建人org code
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setCreateOrgCode(FossUserContextHelper.getOrgCode());

			//创建人org name
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setCreateOrgName(FossUserContextHelper.getOrgName());

			//创建人 code
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setCreateUserCode(FossUserContextHelper.getUserCode());

			//创建人org name
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setCreateUserName(FossUserContextHelper.getUserName());

			//最新时间
			vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity().setCreateDate(new Date());

			//更新数据库
			vo.setResultDto(returnSignBillProcessService.updateReturnBillProcess(vo.getSearchReturnBillProcessDto().getReturnBillProcessEntity()));
		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}

	/**
	 * 返单签收单确认
	 * @author 268377 yanling
	 * @return
	 */
	@JSON
	public String confirmReturnBill(){
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String msg = returnSignBillProcessService.updateBatchReturnBillProcess(ids, currentInfo);
		return returnSuccess(msg);
	}
	/**
	 * 
	 * 导出Excel表格
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-27 下午4:48:22
	 */
	public String exportReturnBill(){
		try {
			//设置文件名
			fileName = encodeFileName("签收单返单");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = returnSignBillProcessService.searchReturnBillProcessList(vo.getSearchReturnBillProcessDto());
		//返回成功
		return returnSuccess();
	}

	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-2 上午9:52:18
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see
	 */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
	
	/**
	 * set对象
	 */
	public void setReturnSignBillProcessService(IReturnBillProcessService returnBillProcessService) {
		this.returnSignBillProcessService = returnBillProcessService;
	}

	/**
	 * get对象
	 */
	public ReturnBillProcessVo getVo() {
		return vo;
	}

	/**
	 * set对象
	 */
	public void setVo(ReturnBillProcessVo returnBillProcessVo) {
		this.vo = returnBillProcessVo;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}