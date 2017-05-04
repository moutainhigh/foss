package com.deppon.foss.module.pickup.predeliver.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;

/**
 * 导出计划提前通知明细
* @author 329757-foss-liuxiangcheng 
* @date 2016-6-16 上午9:27:23 
*
 */
public class ExportArriveNoticeAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportArriveNoticeAction.class);
	
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;  
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	
	/**
	 *派送提前通知Service
	 */
	private IArriveNoticeService arriveNoticeService;
	
	/**
	 * 客户通知VO
	 */
	private NotifyCustomerVo vo = new NotifyCustomerVo();
	
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
	
	public String exportarriveNotices(){
		try {
			//设置文件名
			fileName = encodeFileName("计划提前通知");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(vo != null){
			//返回导出信息
			excelStream = arriveNoticeService.queryArriveNotices(vo.getConditionDto());
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 导出转换文件名
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-16 上午9:35:12
	* @param @param name
	* @param @return
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return String    返回类型 
	* @throws
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
	
	public IArriveNoticeService getArriveNoticeService() {
		return arriveNoticeService;
	}
	public void setArriveNoticeService(IArriveNoticeService arriveNoticeService) {
		this.arriveNoticeService = arriveNoticeService;
	}
	public NotifyCustomerVo getVo() {
		return vo;
	}
	public void setVo(NotifyCustomerVo vo) {
		this.vo = vo;
	}

}
