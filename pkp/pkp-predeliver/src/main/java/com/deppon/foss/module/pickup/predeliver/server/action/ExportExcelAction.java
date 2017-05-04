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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/ExportExcelAction.java
 * 
 * FILE NAME        	: ExportExcelAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveDeliverManagerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryGoodsService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryPredeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveDeliverVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillNewVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ExceptionProcessVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.GoodsInfoConditionVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.SendInfoQueryVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToSuppleService;
import com.deppon.foss.module.pickup.waybill.shared.vo.TodoActionVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillToSuppleVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverHandoverbillVo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;

/**
 * 
 *
 * 处理异常导出Action
 * @author 043258-foss-zhaobin
 * @date 2012-11-7 下午2:12:29
 * @since
 * @version
 */
public class ExportExcelAction extends AbstractAction 
{
	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelAction.class);
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;  
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	
	/**
	 * 消息提示   output
	 */
	private Map<String,Object> info = new HashMap<String,Object>(); 
	
	/**
	 * 处理异常Vo
	 */
	private ExceptionProcessVo vo;
	/**
	 * 派送单Vo
	 */
	private DeliverbillVo deliverbillVo;
	
	/**
	 * 派送单Vo（新）
	 */
	private DeliverbillNewVo deliverbillNewVo;
	
	/** 
	 * 查询货量VO
	 */
	private GoodsInfoConditionVo queryGoodVo;
	/**
	 * 处理异常Service
	 */
	private IExceptionProcessService exceptionProcessService;
	/**
	 * 派送单Service
	 */
	private IDeliverbillService deliverbillService;
	
	/**
	 * 派送单Service（新）
	 */
	private IDeliverbillNewService deliverbillNewService;
	/**
	 * 查询货量Service
	 */
	private IQueryGoodsService queryGoodsService;
	/**
	 * 到达派送service
	 */
	private IArriveDeliverManagerService arriveDeliverManagerService;
	/**
	 * 到达派送VO
	 */
	private ArriveDeliverVo arriveDeliverVo;
	/**
	 * 客户通知VO
	 */
	private NotifyCustomerVo notifyCustomerVo;
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 待办导出
	 */
	private TodoActionVo todoActionVo;
	private ITodoActionService todoActionService;
	
	/**
	 * 待补录Vo
	 */
	private WaybillToSuppleVo waybillToSuppleVo;
	
	/**
	 * 运单待补录信息
	 */
	private IWaybillToSuppleService waybillToSuppleService;
	private DeliverHandoverbillVo deliverHandoverbillVo;
	
	/** 派送信息查询. */
	private SendInfoQueryVo sendInfoQueryVo;
	/** 查询派送信息Service. */
	private IQueryPredeliverService queryPredeliverService;
	
	

    public SendInfoQueryVo getSendInfoQueryVo() {
		return sendInfoQueryVo;
	}

	public void setSendInfoQueryVo(SendInfoQueryVo sendInfoQueryVo) {
		this.sendInfoQueryVo = sendInfoQueryVo;
	}

	public void setQueryPredeliverService(
			IQueryPredeliverService queryPredeliverService) {
		this.queryPredeliverService = queryPredeliverService;
	}

	public void setDeliverHandoverbillService(IDeliverHandoverbillService deliverHandoverbillService) {
        this.deliverHandoverbillService = deliverHandoverbillService;
    }

    private IDeliverHandoverbillService deliverHandoverbillService;
	
	/** 
	 * 客户收货习惯vo 
	 */ 
	public CustomerReceiptHabitVo customerReceiptHabitVo;
	
	/** 
	 * 客户收货习惯service 
	 */ 
	public ICustomerReceiptHabitService customerReceiptHabitService;
	
	public void setWaybillToSuppleService(IWaybillToSuppleService waybillToSuppleService) {
		this.waybillToSuppleService = waybillToSuppleService;
	}
	
	public WaybillToSuppleVo getWaybillToSuppleVo() {
		return waybillToSuppleVo;
	}
	
	public void setWaybillToSuppleVo(WaybillToSuppleVo waybillToSuppleVo) {
		this.waybillToSuppleVo = waybillToSuppleVo;
	}
	
	/**
	 * Gets the excel stream.
	 *
	 * @return the excel stream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	/**
	 * Sets the excel stream.
	 *
	 * @param excelStream the new excel stream
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}  
	
	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public ExceptionProcessVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(ExceptionProcessVo vo) {
		this.vo = vo;
	}
	
	
	public GoodsInfoConditionVo getQueryGoodVo() {
		return queryGoodVo;
	}

	public void setQueryGoodVo(GoodsInfoConditionVo queryGoodVo) {
		this.queryGoodVo = queryGoodVo;
	}

	/**
	 * Sets the exception process service.
	 *
	 * @param exceptionProcessService the new exception process service
	 */
	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	public DeliverbillVo getDeliverbillVo() {
		return deliverbillVo;
	}

	public void setDeliverbillVo(DeliverbillVo deliverbillVo) {
		this.deliverbillVo = deliverbillVo;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	
	public void setQueryGoodsService(IQueryGoodsService queryGoodsService) {
		this.queryGoodsService = queryGoodsService;
	}
	
	/**
	 * @Title: exportReceiptHabit
	 * @Description: 导出客户收货习惯
	 * @return
	 */
	public String exportReceiptHabit() {
		try {
			//设置文件名
			fileName = encodeFileName("客户收货习惯");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = customerReceiptHabitService.selectReceiptHabitList(customerReceiptHabitVo);
			
		if(excelStream == null) {
			info.put("flag", true);
			return "receiptHabitIsNull";
		}
		//返回成功
		return returnSuccess();
	}

	/**
	 * 导出异常数据.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-6 下午4:29:21
	 */
	public String exportExcel(){
		try {
			//设置文件名
			fileName = encodeFileName("异常信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		if(null != vo){
			excelStream = exceptionProcessService.queryExceptionProcessInfo(vo.getExceptionProcessConditionDto());
		}else{
			throw new ExceptionProcessException("系统有问题,请稍后再试!");
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 
	 *	导出派送单信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-4 上午8:56:22
	 */
	public String exportDeliverbill(){
		try {
			//设置文件名
			fileName = encodeFileName("派送单");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if("P".equals(deliverbillVo.getDeliverbillDto().getDeliverbillNo())){
			deliverbillVo.getDeliverbillDto().setDeliverbillNo(null);
		}
		//返回导出信息
		excelStream = deliverbillService.queryDeliverbillList(deliverbillVo.getDeliverbillDto());
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 
	 *	导出派送单信息（新）
	 * @author 
	 * @date 
	 */
	public String exportDeliverbillNew(){
		try {
			//设置文件名
			fileName = encodeFileName("派送单(新)");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if (deliverbillNewVo!=null) {
			if (deliverbillNewVo.getDeliverbillNewDto()!=null) {
				if("P".equals(deliverbillNewVo.getDeliverbillNewDto().getDeliverbillNo())){
					deliverbillNewVo.getDeliverbillNewDto().setDeliverbillNo(null);
				}
				//返回导出信息
				excelStream = deliverbillNewService.queryDeliverbillList(deliverbillNewVo.getDeliverbillNewDto());
			}
		}
		
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 
	 *	导出派送单明细信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-4 上午8:56:22
	 */
	public String exportDeliverbillDetail(){
		try {
			//设置文件名
			fileName = encodeFileName("派送单明细");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if("P".equals(deliverbillVo.getDeliverbillDto().getDeliverbillNo())){
			deliverbillVo.getDeliverbillDto().setDeliverbillNo(null);
		}
		//返回导出信息
		excelStream = deliverbillService.queryDeliverbillDetailLists(deliverbillVo.getDeliverbillDto());
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 
	 *	导出派送单明细信息（新）
	 * @author 
	 * @date 
	 */
	public String exportDeliverbillNewDetail(){
			try {
				//设置文件名
				fileName = encodeFileName("派送单明细（新）");
			} catch (UnsupportedEncodingException se) {
				//记录错误信息
				LOGGER.error("error", se);
			}
			if (deliverbillNewVo!=null) {
				if (deliverbillNewVo.getDeliverbillNewDto()!=null) {
					if("P".equals(deliverbillNewVo.getDeliverbillNewDto().getDeliverbillNo())){
						deliverbillNewVo.getDeliverbillNewDto().setDeliverbillNo(null);
					}
					//返回导出信息
					excelStream = deliverbillNewService.queryDeliverbillDetailLists(deliverbillNewVo.getDeliverbillNewDto());
				}
			}
			
			//返回成功
			return returnSuccess();
		
	}
	
	/**
	 * 
	 *	导出查询货量信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-31 上午8:56:22
	 */
	public String exportGoodsInfo(){
		try {
			//设置文件名
			fileName = encodeFileName("查询货量");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = queryGoodsService.queryGoodsInfo(queryGoodVo.getGoodsInfoConditionDto());
		//返回成功
		return returnSuccess();
	}
	
	
	/**
	 * 
	 *	导出到达派送信息
	 * @author 043258-foss-meiying
	 * @date 2013-6-25 下午6:56:22
	 */
	public String exportArriveDeliverInfo(){
		try {
			//设置文件名
			fileName = encodeFileName("综合派送信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//得到当前登录的部门编码
		arriveDeliverVo.getArriveDeliverQueryDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
		//返回导出信息
		excelStream = arriveDeliverManagerService.exportArriveDeliverInfo(arriveDeliverVo.getArriveDeliverQueryDto(),this.getStart(), this.getLimit());
		//返回成功
		return returnSuccess();
	}
	/**
	 * 
	 * 导出客户通知信息
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 2, 2013 11:30:11 AM
	 */
	public String exportNoticeCustomer(){
		try {
			//设置文件名
			fileName = encodeFileName("客户通知");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//得到当前登录的部门编码
		notifyCustomerVo.getConditionDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
		//返回导出信息
		excelStream = notifyCustomerService.exportNotifyCustomerInfo(notifyCustomerVo.getConditionDto());
		//返回成功
		return returnSuccess();
	}
	/**
	 * 
	 * 待办导出
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 16, 2013 2:45:29 PM
	 */
	public String exportTodoAction(){
		try {
			//设置文件名
			fileName = encodeFileName("待办事项");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		todoActionVo.getTodoConditionDto().setIsPrinted(todoActionVo.getTodoConditionDto().getStatus());
		excelStream = todoActionService.exportTodoActionInfo(todoActionVo.getTodoConditionDto());
		//返回成功
		return returnSuccess();
	}
	
	public String exportWaybillSuppleAction(){
		try {
			//设置文件名
			fileName = encodeFileName("待补录与已转储运单处理操作记录");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		excelStream = waybillToSuppleService.exportWaybillSuppleAction(waybillToSuppleVo.getWaybillToSuppleCondtionDto());
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 导出派送交单-待交单信息
	 * @author 159231 meiying
	 * 2015-4-28  下午6:08:20
	 * @return
	 */
	public String exportPreDeliverHandover(){
		try {
			//设置文件名
			fileName = encodeFileName("派送待交单信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(null != deliverHandoverbillVo){
			//返回导出信息
			excelStream = deliverHandoverbillService.exportPreDeliverHandover(deliverHandoverbillVo.getPreDeliverHandoverbillQueryDto(),this.getStart(), this.getLimit());
		}
		//返回成功
		return returnSuccess();
	}

	/**
	 * 导出派送交单-交单信息
	 * @author 159231 meiying
	 * 2015-5-6  上午10:37:48
	 * @return
	 */
	public String exportDeliverHandover(){
		try {
			//设置文件名
			fileName = encodeFileName("交单信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(null != deliverHandoverbillVo){
			//返回导出信息
			excelStream = deliverHandoverbillService.exportDeliverHandover(deliverHandoverbillVo.getDeliverHandoverbillQueryDto(),this.getStart(), this.getLimit());
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 导出派送信息查询(交接单)
	 * @author zyr
	 * 2015-5-30  上午10:37:48
	 * @return
	 */
	public String exportPredeliverInfo(){
		try {
			//设置文件名
			fileName = encodeFileName("派送信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = queryPredeliverService.queryPredeliverInfo(sendInfoQueryVo.getSendInfoSearchDto());
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 导出派送信息查询(运单)
	 * @author zyr
	 * 2015-5-30  上午10:37:48
	 * @return
	 */
	public String exportPredeliverWaybillInfo(){
		try {
			//设置文件名
			fileName = encodeFileName("派送信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = queryPredeliverService.queryPredeliverWaybillInfo(sendInfoQueryVo.getSendInfoSearchDto());
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 导出派送信息查询(运单)
	 * @author zyr
	 * 2015-5-30  上午10:37:48
	 * @return
	 */
	public String exportExpressWaybillInfo(){
		try {
			//设置文件名
			fileName = encodeFileName("快递派送信息");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回导出信息
		excelStream = queryPredeliverService.queryExpressWaybillInfo(sendInfoQueryVo.getSendInfoSearchDto());
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

	public ArriveDeliverVo getArriveDeliverVo() {
		return arriveDeliverVo;
	}

	public void setArriveDeliverVo(ArriveDeliverVo arriveDeliverVo) {
		this.arriveDeliverVo = arriveDeliverVo;
	}

	public void setArriveDeliverManagerService(
			IArriveDeliverManagerService arriveDeliverManagerService) {
		this.arriveDeliverManagerService = arriveDeliverManagerService;
	}

	public NotifyCustomerVo getNotifyCustomerVo() {
		return notifyCustomerVo;
	}

	public void setNotifyCustomerVo(NotifyCustomerVo notifyCustomerVo) {
		this.notifyCustomerVo = notifyCustomerVo;
	}

	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setTodoActionVo(TodoActionVo todoActionVo) {
		this.todoActionVo = todoActionVo;
	}
	/**
	 * 获取deliverHandoverbillVo  
	 * @return deliverHandoverbillVo deliverHandoverbillVo
	 */
	public DeliverHandoverbillVo getDeliverHandoverbillVo() {
		return deliverHandoverbillVo;
	}

	/**
	 * 设置deliverHandoverbillVo  
	 * @param deliverHandoverbillVo deliverHandoverbillVo 
	 */
	public void setDeliverHandoverbillVo(DeliverHandoverbillVo deliverHandoverbillVo) {
		this.deliverHandoverbillVo = deliverHandoverbillVo;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}

	public TodoActionVo getTodoActionVo() {
		return todoActionVo;
	}

	/**
	 * 获取info
	 * @return the info
	 */
	public Map<String, Object> getInfo() {
		return info;
	}

	/**
	 * 设置info
	 * @param info 要设置的info
	 */
	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	/**
	 * 获取customerReceiptHabitVo
	 * @return the customerReceiptHabitVo
	 */
	public CustomerReceiptHabitVo getCustomerReceiptHabitVo() {
		return customerReceiptHabitVo;
	}

	/**
	 * 设置customerReceiptHabitVo
	 * @param customerReceiptHabitVo 要设置的customerReceiptHabitVo
	 */
	public void setCustomerReceiptHabitVo(
			CustomerReceiptHabitVo customerReceiptHabitVo) {
		this.customerReceiptHabitVo = customerReceiptHabitVo;
	}

	/**
	 * 设置customerReceiptHabitService
	 * @param customerReceiptHabitService 要设置的customerReceiptHabitService
	 */
	@Resource
	public void setCustomerReceiptHabitService(
			ICustomerReceiptHabitService customerReceiptHabitService) {
		this.customerReceiptHabitService = customerReceiptHabitService;
	}

	
	public DeliverbillNewVo getDeliverbillNewVo() {
		return deliverbillNewVo;
	}

	public void setDeliverbillNewVo(DeliverbillNewVo deliverbillNewVo) {
		this.deliverbillNewVo = deliverbillNewVo;
	}

	public void setDeliverbillNewService(
			IDeliverbillNewService deliverbillNewService) {
		this.deliverbillNewService = deliverbillNewService;
	}

}