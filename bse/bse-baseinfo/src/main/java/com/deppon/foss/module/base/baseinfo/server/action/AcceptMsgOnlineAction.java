package com.deppon.foss.module.base.baseinfo.server.action;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.common.api.shared.vo.MsgOnlineVo;
import com.deppon.foss.util.DateUtils;

public class AcceptMsgOnlineAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2821996703125090764L;
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptMsgOnlineAction.class);
	/**
	 * 在线通知Dto
	 */
	private MsgOnlineVo msgOnlineVo;
	/**
	 * 在线通知DTO
	 */
	private MsgOnlineDto msgOnlineDto;
	/**
	 * 在线通知service
	 */
	private IOnLineMsgService onLineMsgService;
	  /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;
	/**
	 * 站内消息初始化
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	public String  acceptNetMsgOnline() {
		return returnSuccess();
	}
	
	/**
	 *发送全网消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	@JSON
	public String sendMsgSend(){
		try {
		//	Boolean message =false;
			//当前用户登录信息
		//	message = onLineMsgService.addOnlineMsgList(msgOnlineVo.getMsgOnlineDtos());
			onLineMsgService.addOnlineMsgList(msgOnlineVo.getMsgOnlineDtos());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			//日志记录
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
		
	}
	@JSON
	public String updateMsgByBillNo(){
		
		onLineMsgService.updateOnlineMsg(msgOnlineVo.getMsgOnlineEntity());
	
		return returnSuccess("更新成功！");
	}
	/**
     * <p>导出在线通知数据至EXCEl</p>.
     *
     * @return 
     * @author 130346-foss-lifanghong
     * @date 2013-08-24 下午2:00:06
     * @see
     */
    public String exportOnLineMsg(){
    	try {
    	    //导出文件名称
    	    downloadFileName = URLEncoder.encode(ColumnConstants.EXPROT_ONLINEMSG_NAME, "UTF-8");
    	    //获取查询参数
    	    MsgOnlineEntity entity = msgOnlineVo.getMsgOnlineEntity();
    	    
    	    if(null == entity){
    	    	entity = new MsgOnlineEntity();
    	    }
    	    
    	  //站内消息结束时间
    		Date endTime = entity.getCreateEndTime();
    		//结束时间加1天
    		entity.setCreateEndTime(DateUtils.addDayToDate(endTime, 1));
    	    
    	    
//    	    //设置线路类型为始发线路
//    	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
    	    
    	    //导出在线通知
    	    ExportResource exportResource =onLineMsgService.exportMsgList(entity);
    	    ExportSetting exportSetting = new ExportSetting();
    	    //设置名称
    	    exportSetting.setSheetName(ColumnConstants.EXPROT_ONLINEMSG_NAME);
    	    //设置下载最大条数
//    	    exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
    	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
    	    // 导出成文件
    	    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
    	    
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	} catch (UnsupportedEncodingException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError("UnsupportedEncodingException", e);
    	}
        }
    
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public MsgOnlineVo getMsgOnlineVo() {
		return msgOnlineVo;
	}
	public void setMsgOnlineVo(MsgOnlineVo msgOnlineVo) {
		this.msgOnlineVo = msgOnlineVo;
	}
	public MsgOnlineDto getMsgOnlineDto() {
		return msgOnlineDto;
	}
	public void setMsgOnlineDto(MsgOnlineDto msgOnlineDto) {
		this.msgOnlineDto = msgOnlineDto;
	}
	public void setOnLineMsgService(IOnLineMsgService onLineMsgService) {
		this.onLineMsgService = onLineMsgService;
	}
	
}
