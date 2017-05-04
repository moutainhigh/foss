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
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IToAddPartnerProgramService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ToAddPartnerProgramVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class ToAddPartnerProgramAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ToAddPartnerProgramAction.class);
	// 一天的毫秒值
	private static final long MSEC_OF_DAY = 86400000L;
	// 合伙人到达加收Vo
	private ToAddPartnerProgramVo toAddPartnerProgramVo;
	
	//合伙人到达加收方案Servie
	private IToAddPartnerProgramService toAddPartnerProgramService;
	
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


	
	public ToAddPartnerProgramVo getToAddPartnerProgramVo() {
		return toAddPartnerProgramVo;
	}
	public void setToAddPartnerProgramVo(ToAddPartnerProgramVo toAddPartnerProgramVo) {
		this.toAddPartnerProgramVo = toAddPartnerProgramVo;
	}
	public void setToAddPartnerProgramService(IToAddPartnerProgramService toAddPartnerProgramService) {
		this.toAddPartnerProgramService = toAddPartnerProgramService;
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
    
    /**
     * <p>获取excel</p> 
     * @author DP-Foss-zoushengli
     * @date 2016-09-05 上午11:33:22
     * @return
     * @see
     */
    public InputStream getInputStream() {
        return inputStream;
    }

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * 
	 * <p>设置Excel文件 </p> 
	 * @author DP-Foss-邹胜利
	 * @date 2016-09-05 上午10:32:46
	 * @param uploadFile
	 * @see
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	/**
	 * @功能 根据条件查询合伙人到达加收方案
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	@JSON
	public String querytoAddPartnerProgramVoBatchInfo() {
		try {
			Long longTime = toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto().getEndDate().getTime();
			toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto().setEndDate(new Date(longTime+MSEC_OF_DAY-1));
			Long totalCount = toAddPartnerProgramService.queryToAddPartnerProgramVoBatchInfoCount(toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto());
			if(totalCount != null && totalCount.intValue() >= 0){
				List<ToAddPartnerProgramEntity> toAddPartnerProgramEntity = toAddPartnerProgramService.querytoAddPartnerProgramVoBatchInfo(
						toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto(), start, limit);
				toAddPartnerProgramVo.setToAddPartnerProgramEntityList(toAddPartnerProgramEntity);
			}else{
				toAddPartnerProgramVo.setToAddPartnerProgramEntityList(null);
			}
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

    /**
     * 
     * <p>(
     * 根据方案ID查询合伙到达加收方案明细信息
     * )<br/>
     * </p> 
     * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
     */
    @JSON
    public String selectById(){
		try {
		toAddPartnerProgramVo = toAddPartnerProgramService.selectById(toAddPartnerProgramVo.getToAddPartnerProgramid());
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("根据方案ID查询计价方案明细信息: "+e.getMessage());
		    return returnError("根据方案ID查询计价方案明细信息: " + e.getMessage());
		}
    }

	/**
	 * 激活合伙人到达加收方案
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	@JSON
	public String activeToAddPartnerProgram(){
		String temp = null;
		try{
			temp = toAddPartnerProgramVo.getYesOrNo();
			if(FossConstants.YES.equals(temp)){
				// 激活
				toAddPartnerProgramService.immediatelyActiveToAddPartnerProgram(toAddPartnerProgramVo.getToAddPartnerProgramid(), 
						temp, toAddPartnerProgramVo.getEffectiveTime());
			}else if("N".equals(temp)){
				// 立即中止
				toAddPartnerProgramService.immediatelyStopToAddPartnerProgram(toAddPartnerProgramVo.getToAddPartnerProgramid(), toAddPartnerProgramVo.getEffectiveTime());
			}else{
				throw new BusinessException("激活中止标志位为空，请联系管理员");
			}
		}catch (BusinessException e) {
			if (FossConstants.YES.equals(temp)) {
				return returnError("激活合伙人到达加收方案：" + e.getMessage());
			} else {
				return returnError("终止合伙人到达加收方案：" + e.getMessage());
			}
		}
		if (FossConstants.YES.equals(temp)) {
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} else {
			return returnSuccess(MessageType.STOP_SUCCESS);
		}
		
	}
    
	/**
	 * <p>
	 * Description:根据主键Id批量激活合伙人到达加收方案<br />
	 * </p>
	 * @author zoushengli
	 * @version 0.1 2016-09-05
	 * @param id
	 * @return
	 * int
	 */
	@JSON
	public String updateToAddPartnerProgramActiveById() {
		try{
			toAddPartnerProgramService.updateToAddPartnerProgramActiveById(
					toAddPartnerProgramVo.getToAddPartnerProgramEntityList(), toAddPartnerProgramVo.getYesOrNo());
		} catch (BusinessException e) {
			return returnError("批量激活合伙人到达加收方案：" + e.getMessage());
		}
		return returnSuccess(MessageType.ACTIVE_SUCCESS);
	}
    
	/**
	 * .
	 * <p>
	 * 新增合伙人到达加收方案<br/>
	 * 方法名：addToAddPartnerProgram
	 * </p>
	 * 
	 * @author 265475
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String addToAddPartnerProgram() {
		try {
			ToAddPartnerProgramEntity toAddPartnerProgramEntity = new ToAddPartnerProgramEntity();
			
			toAddPartnerProgramEntity = toAddPartnerProgramVo.getToAddPartnerProgramEntity();
			
			toAddPartnerProgramVo = toAddPartnerProgramService.toAddPartnerProgramPrice(toAddPartnerProgramEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
			
		} catch (BusinessException e) {
		    LOGGER.error("新增合伙人到达加收方案: " + e.getMessage());
		    return returnError("新增合伙人到达加收方案: " + e.getMessage());
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改合伙人到达加收方案<br/>
	 * 方法名：updateToAddPartnerProgram
	 * </p>
	 * 
	 * @author zoushengli
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String updateToAddPartnerProgram() {
		try {
			toAddPartnerProgramService.updateToAddPartnerProgram(toAddPartnerProgramVo.getToAddPartnerProgramEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改偏线价格方案: "+e.getMessage());
		    return returnError("修改偏线价格方案: " + e.getMessage());
		}
	}
	
	/**
	 * .
	 * <p>
	 * 删除合伙人到达加收方案<br/>
	 * 方法名：deletePricingDiscount
	 * </p>
	 * 
	 * @author 265475
	 * 
	 * @date 2016-09-05 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@JSON
	public String deleteToAddPartnerProgram() {
		try {
			toAddPartnerProgramService.deleteByPrimaryKey(toAddPartnerProgramVo.getToAddPartnerProgramids());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除合伙人到达加收方案: "+e.getMessage());
		    return returnError("删除合伙人到达加收方案: " + e.getMessage());
		}
	}
	
	/**
	 * .
	 * <p>
	 * 导出合伙人到达加收方案<br/>
	 * 方法名：exportToAddPartnerProgram
	 * </p>
	 * 
	 * @author zoushengli
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String exportToAddPartnerProgram(){
		try{
			//文件名称
			String fileName = PricingColumnConstants.EXPORT_TOADDPARTNERPROGRAM_PLAN ;
			//下载文件名称
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			//判断输入参数是否为空且给以默认对象
			if(null == toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto()){
				toAddPartnerProgramVo.setToAddPartnerProgramCondtionDto(new ToAddPartnerProgramCondtionDto());
			}
			//导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_TOADDPARTNERPROGRAM_PLAN);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			//导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 存放导出数据
			ExportResource exportResource = toAddPartnerProgramService.exportToAddPartnerProgram(toAddPartnerProgramVo.getToAddPartnerProgramCondtionDto());
			/** 超过1w笔不能以IO形势返回 **/
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("合伙人到达加收方案导出，出现异常: "+e.getMessage());
			return returnError("合伙人到达加收方案导出，出现异常" + e.getMessage());
		}
		return returnSuccess();
	}

	

	/**
	 * 
	 * @Description: 导入合伙人到达加收方案 
	 * 
	 * @author 265475
	 * 
	 * @date 2016-09-05 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public String importToAddPartnerProgram() {
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 2003
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 2007
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				toAddPartnerProgramService.importToAddPartnerProgram(book);
			}
			return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileException e) {
			return super.returnError("导入合伙人到达加收方案: "+e.getMessage());
		} catch (IOException e) {
			return returnError("数据文件被破坏，请重新制作导入文件");
		} catch (BusinessException e) {
		    LOGGER.error("导入合伙人到达加收方案: "+e.getMessage());
			return returnError("导入合伙人到达加收方案: "+e.getMessage());
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
	}

	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 265475
	 * 
	 * @date 2016-09-05 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
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
}
