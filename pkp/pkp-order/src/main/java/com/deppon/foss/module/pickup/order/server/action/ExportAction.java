package com.deppon.foss.module.pickup.order.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderExpressService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskQueryService;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderConditionVo;

/**
 * 
 * 导出action
 * @author 038590-foss-wanghui
 * @date 2013-6-4 上午11:38:34
 */
public class ExportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3571130889767324255L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportAction.class);
	
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;  
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	
	private IOrderTaskQueryService orderTaskQueryService;
	private IOrderExpressService orderExpressService;
	
	private DispatchOrderConditionVo dispatchOrderConditionVo;
	
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
	
	public String exportVehicleRecord(){
		try {
			//设置文件名
			fileName = encodeFileName("派车记录");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(dispatchOrderConditionVo != null){
			//返回导出信息
			excelStream = orderTaskQueryService.queryVehicleRecord(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
		}
		//返回成功
		return returnSuccess();
	}
	public String exportExpressVehicleRecord(){
		try {
			//设置文件名
			fileName = encodeFileName("派车记录");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(dispatchOrderConditionVo != null){
			//返回导出信息
			excelStream = orderExpressService.queryVehicleRecord(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
		}
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
	
	public void setOrderTaskQueryService(IOrderTaskQueryService orderTaskQueryService) {
		this.orderTaskQueryService = orderTaskQueryService;
	}
	public DispatchOrderConditionVo getDispatchOrderConditionVo() {
		return dispatchOrderConditionVo;
	}
	public void setDispatchOrderConditionVo(DispatchOrderConditionVo dispatchOrderConditionVo) {
		this.dispatchOrderConditionVo = dispatchOrderConditionVo;
	}
	public void setOrderExpressService(IOrderExpressService orderExpressService) {
		this.orderExpressService = orderExpressService;
	}

}
