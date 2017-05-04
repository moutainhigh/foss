package com.deppon.foss.module.pickup.predeliver.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyDetailsService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyDetailsActionVo;

/**
 * @功能 查询营业部派送、自提通知记录;支持导出功能
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 19:26:51
 *
 */

public class NotifyDetailsAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotifyDetailsAction.class);
	
	private NotifyDetailsActionVo vo;
	
	private INotifyDetailsService notifyDetailsService;
	
	//文件名
	private String fileName;
	
	private InputStream excelStream;  
	
	@JSON
	public String queryNotifyDetails(){
		try{
			notifyDetailsService.initNotifyDetailsConditonDto(vo.getNotifyDetailsConditonDto());
			Long totalCount = notifyDetailsService.queryNoticeDetailCount(vo.getNotifyDetailsConditonDto());
			if(totalCount != null && totalCount.intValue() > 0){
				List<NotifyDetailsDto> list = notifyDetailsService.queryNoticeDetail(vo.getNotifyDetailsConditonDto(), this.getStart(), this.getLimit());
				vo.setNotifyDetailsDtoList(list);
			}else{
				vo.setNotifyDetailsDtoList(null);
			}
			this.setTotalCount(totalCount);
		} catch (BusinessException e){
			returnError(e);
		}
		return returnSuccess();
	}
	
	public String exportNoticeDetails(){
		try {
			//设置文件名
			fileName = encodeFileName("通知明细");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		if(vo != null){
			//返回导出信息
			notifyDetailsService.initNotifyDetailsConditonDto(vo.getNotifyDetailsConditonDto());
			excelStream = notifyDetailsService.exportNoticeDetail(vo.getNotifyDetailsConditonDto());
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
    
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setVo(NotifyDetailsActionVo vo) {
		this.vo = vo;
	}
	
	public NotifyDetailsActionVo getVo() {
		return vo;
	}
	public void setNotifyDetailsService(
			INotifyDetailsService notifyDetailsService) {
		this.notifyDetailsService = notifyDetailsService;
	}

}
