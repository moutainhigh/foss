package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExternalPriceSchemeService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ExternalPriceSchemeException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ExternalPriceSchemeVo;
import com.deppon.foss.util.DateUtils;
/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Action
 */
public class ExternalPriceSchemeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2703505025920592674L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalPriceSchemeAction.class);
	/**
	 * 偏线外发价格方案Service
	 */
	private IExternalPriceSchemeService externalPriceSchemeService;
    
	/**
     * 导出文件名称
     */
    private String downloadFileName;
    
    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
    
	/** 
	 * 导入文件
	 */
	private File uploadFile;
	/** 
	 * 偏线外发价格方案VO
	 */
	private ExternalPriceSchemeVo externalPriceSchemeVo;
	/**
	 * @author 092020-lipengfei
	 * 根据参数查询偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String queryExternalPriceSchemeByParams(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
		List<ExternalPriceSchemeEntity> entityList=externalPriceSchemeService.queryExternalPriceSchemeByParam(entity, limit, start);
		externalPriceSchemeVo.setEntityList(entityList);
		totalCount=externalPriceSchemeService.queryRecordCount(entity);
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 根据ID查询偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String queryExternalPriceSchemeById(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeService.queryExternalePriceSchemeById(externalPriceSchemeVo.getId());
		externalPriceSchemeVo.setEntity(entity);
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 新增偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String addExternalPriceScheme(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
		try {
			externalPriceSchemeService.addExternalPriceScheme(entity);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 修改偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String updateExternalPriceScheme(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
		try {
			externalPriceSchemeService.updateExternalPriceScheme(entity);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 删除偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String deleteExternalPriceScheme(){
		List<String> idList=externalPriceSchemeVo.getIdList();
		try {
			externalPriceSchemeService.deleteExternalPriceSchemeById(idList);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 激活偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String activateExternalPriceScheme(){
		List<ExternalPriceSchemeEntity> entityList=externalPriceSchemeVo.getEntityList();
		try {
			externalPriceSchemeService.activateExternalPriceSchemeById(entityList);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 立即激活偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String immediatelyActivateScheme(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
		entity.setBeginTime(externalPriceSchemeVo.getBeginTime());
		try {
			externalPriceSchemeService.immediatelyActivateSchemeById(entity);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 立即中止偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String immediatelySuspendScheme(){
		ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
		entity.setEndTime(externalPriceSchemeVo.getEndTime());
		try {
			externalPriceSchemeService.immediatelySuspendSchemeById(entity);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 复制偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String copyExternalPriceScheme(){
		String id=externalPriceSchemeVo.getId();
		try {
			ExternalPriceSchemeEntity copyEntity=externalPriceSchemeService.copyExternalPriceScheme(id);
			externalPriceSchemeVo.setEntity(copyEntity);
			//吴涛修改的bug。原因：返回的Id是原来的旧ID
			externalPriceSchemeVo.setId(copyEntity.getId());
			return returnSuccess(MessageType.SAVE_SUCCESS);
			//end
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * @author 092020-lipengfei
	 * 导入偏线外发价格方案
	 * @return String
	 */
	@JSON
	public String importExternalPriceScheme(){
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 如果是2003版本
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 如果是2007版本
					book = new HSSFWorkbook(new FileInputStream(uploadFile));
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (null != book) {
				externalPriceSchemeService.importExternalPriceScheme(book);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					return returnError("文件关闭失败");
				}
			}
		}
		return returnSuccess();
	}
	/**
	 * @author 092020-lipengfei
	 * 导出偏线外发价格方案
	 * @return String
	 */
	
	@JSON
	public String exportExternalPriceScheme(){
		try{
			ExternalPriceSchemeEntity entity=externalPriceSchemeVo.getEntity();
			//文件名称
			String fileName = PricingColumnConstants.EXPORT_EXTERNAL_PRICE_SCHEME ;
			//下载文件名称
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			//判断输入参数是否为空且给以默认对象
			if(null == externalPriceSchemeVo.getEntity()){
				externalPriceSchemeVo.setEntity(new ExternalPriceSchemeEntity());				
			}
			//导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_EXTERNAL_PRICE_SCHEME);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			//导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 存放导出数据
			ExportResource exportResource = externalPriceSchemeService.exportExternalPriceSchemeByParams(entity);
			/** 超过1w笔不能以IO形势返回 **/
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("偏线外发价格方案导出，出现异常: "+e.getMessage());
			return returnError("偏线外发价格方案导出，出现异常" + e.getMessage());
		}
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
    private String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
	/*=======================getter/setter==============================*/
	 public InputStream getInputStream() {
	        return inputStream;
    }

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
    /**
     * 获得导出文件名称
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public ExternalPriceSchemeVo getExternalPriceSchemeVo() {
		return externalPriceSchemeVo;
	}
	public void setExternalPriceSchemeVo(ExternalPriceSchemeVo externalPriceSchemeVo) {
		this.externalPriceSchemeVo = externalPriceSchemeVo;
	}
	public void setExternalPriceSchemeService(
			IExternalPriceSchemeService externalPriceSchemeService) {
		this.externalPriceSchemeService = externalPriceSchemeService;
	}
}
