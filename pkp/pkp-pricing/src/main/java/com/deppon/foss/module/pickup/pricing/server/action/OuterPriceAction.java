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
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.OuterPriceVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class OuterPriceAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OuterPriceAction.class);
	// 偏线价格Vo
	private OuterPriceVo outerPriceVo;
	
	//偏线价格方案Servie
	private IOuterPriceService outerPriceService;
	
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

	public OuterPriceVo getOuterPriceVo() {
		return outerPriceVo;
	}

	public void setOuterPriceVo(OuterPriceVo outerPriceVo) {
		this.outerPriceVo = outerPriceVo;
	}
	
	public void setOuterPriceService(IOuterPriceService outerPriceService) {
		this.outerPriceService = outerPriceService;
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
     * @author DP-Foss-YueHongJie
     * @date 2012-12-27 上午11:33:22
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
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午10:32:46
	 * @param uploadFile
	 * @see
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	

	/**
	 * @功能 根据条件查询偏线价格方案
	 * @author Administrator
	 * @date 2013-8-16 9:37:54
	 * @return
	 */
	@JSON
	public String queryOuterPriceVoBatchInfo() {
		try {
			Long totalCount = outerPriceService.queryOuterPriceVoBatchInfoCount(outerPriceVo.getOuterPriceCondtionDto());
			if(totalCount != null && totalCount.intValue() >= 0){
				List<OuterPricePlanDto> outerPricePlanDtoList = outerPriceService.queryOuterPriceVoBatchInfo(
						outerPriceVo.getOuterPriceCondtionDto(), start, limit);
				outerPriceVo.setOuterPricePlanDtoList(outerPricePlanDtoList);
			}else{
				outerPriceVo.setOuterPricePlanDtoList(null);
			}
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	@JSON
	public String queryOuterPriceDetailInfo() {
		try {
//			List<OuterPriceDetailDto> outerPriceDetailDtoList = outerPriceService.queryOuterPriceDetailInfo();
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	@JSON
	public String stopOuterPrice() {
		try {
			outerPriceService.updateActiveToN(outerPriceVo.getOuterPriceId());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 激活偏线价格方案
	 * @return
	 */
	@JSON
	public String activeOuterPrice(){
		String temp = null;
		try{
			temp = outerPriceVo.getYesOrNo();
			if(FossConstants.YES.equals(temp)){
				// 激活
				outerPriceService.immediatelyActiveOuterPrice(outerPriceVo.getOuterPriceId(), 
						temp, outerPriceVo.getEffectiveTime());
			}else if("N".equals(temp)){
				// 立即中止
				outerPriceService.immediatelyStopOuterPrice(outerPriceVo.getOuterPriceId(), outerPriceVo.getEffectiveTime());
			}else{
				throw new BusinessException("激活中止标志位为空，请联系管理员");
			}
		}catch (BusinessException e) {
			if (FossConstants.YES.equals(temp)) {
				return returnError("激活偏线价格方案：" + e.getMessage());
			} else {
				return returnError("终止偏线价格方案：" + e.getMessage());
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
	 * Description:根据主键Id批量激活偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	@JSON
	public String updateOuterPriceActiveById() {
		try{
			outerPriceService.updateOuterPriceActiveById(
					outerPriceVo.getOuterPricePlanDtoList(), outerPriceVo.getYesOrNo());
		} catch (BusinessException e) {
			return returnError("批量激活偏线价格方案：" + e.getMessage());
		}
		return returnSuccess(MessageType.ACTIVE_SUCCESS);
	}
	
	/**
	 * .
	 * <p>
	 * 新增偏线价格方案<br/>
	 * 方法名：addPricingDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String addOuterPrice() {
		try {
			OuterPriceEntity outerPriceEntity = new OuterPriceEntity();
			
			outerPriceEntity = outerPriceVo.getOuterPriceEntity();
			
			outerPriceVo = outerPriceService.addOuterPrice(outerPriceEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
			
		} catch (BusinessException e) {
		    LOGGER.error("新增偏线价格方案: " + e.getMessage());
		    return returnError("新增偏线价格方案: " + e.getMessage());
		}
	}
	/**
	 * .
	 * <p>
	 * 修改偏线价格方案<br/>
	 * 方法名：updateOuterPrice
	 * </p>
	 * 
	 * @author Rosanu
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String updateOuterPrice() {
		try {
			outerPriceService.updateOuterPrice(outerPriceVo.getOuterPriceEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改偏线价格方案: "+e.getMessage());
		    return returnError("修改偏线价格方案: " + e.getMessage());
		}
	}
	
	/**
	 * .
	 * <p>
	 * 复制偏线价格方案<br/>
	 * 方法名：updateOuterPrice
	 * </p>
	 * 
	 * @author Rosanu
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String copyOuterPrice() {
		try {
			outerPriceVo = outerPriceService.copyOuterPrice(outerPriceVo.getOuterPriceId(),
					outerPriceVo.getCopyName());
			return returnSuccess(MessageType.COPY_PRICE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("复制偏线价格方案: " + e.getMessage());
		    return returnError("复制偏线价格方案: " + e.getMessage());
		}
	}
	
	
	
	/**
	 * .
	 * <p>
	 * 删除偏线价格方案<br/>
	 * 方法名：deletePricingDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String deleteOuterPrice() {
		try {
			outerPriceService.deleteByPrimaryKey(outerPriceVo.getOuterPriceIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除偏线价格方案: "+e.getMessage());
		    return returnError("删除偏线价格方案: " + e.getMessage());
		}
	}
	
    /**
     * 
     * <p>(
     * 根据方案ID查询计价方案与所有明细信息
     * )<br/>
     * </p> 
     * 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午3:29:14
     * @return
     * @see
     */
    @JSON
    public String queryOuterPriceInfoById(){
		try {
			outerPriceVo = outerPriceService.selectByPrimaryKey(outerPriceVo.getOuterPriceId());
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("根据方案ID查询计价方案与所有明细信息: "+e.getMessage());
		    return returnError(e);
		}
    }

	
	/**
	 * .
	 * <p>
	 * 导出偏线价格方案<br/>
	 * 方法名：exprtOuterPrice
	 * </p>
	 * 
	 * @author Rosanu
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String exportOuterPrice(){
		try{
			//文件名称
			String fileName = PricingColumnConstants.EXPORT_OUTER_PRICE_PLAN ;
			//下载文件名称
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			//判断输入参数是否为空且给以默认对象
			if(null == outerPriceVo.getOuterPriceCondtionDto()){
				outerPriceVo.setOuterPriceCondtionDto(new OuterPriceCondtionDto());
			}
			//导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_OUTER_PRICE_PLAN);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			//导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 存放导出数据
			ExportResource exportResource = outerPriceService.exportOuterPrice(outerPriceVo.getOuterPriceCondtionDto());
			/** 超过1w笔不能以IO形势返回 **/
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("偏线价格方案导出，出现异常: "+e.getMessage());
			return returnError("偏线价格方案导出，出现异常" + e.getMessage());
		}
		return returnSuccess();
	}

	
    /**
     * 
     * <p>(
     * 根据方案ID查询计价方案与所有明细信息
     * )<br/>
     * </p> 
     * 
     * @author DP-Foss-Rosanu
     * @date 2012-12-20 下午3:29:14
     * @return
     * @see
     */
    @JSON
    public String selectById(){
		try {
			outerPriceVo = outerPriceService.selectById(outerPriceVo.getOuterPriceId());
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("根据方案ID查询计价方案明细信息: "+e.getMessage());
		    return returnError("根据方案ID查询计价方案明细信息: " + e.getMessage());
		}
    }

	/**
	 * 
	 * @Description: 导入时效方案 Company:IBM
	 * 
	 * @author Rosanu
	 * 
	 * @date 2012-12-24 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public String importOuterPrice() {
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
				outerPriceService.importOuterPrice(book);
			}
			return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileException e) {
			return super.returnError("导入偏线价格方案: "+e.getMessage());
		} catch (IOException e) {
			return returnError("数据文件被破坏，请重新制作导入文件");
		} catch (BusinessException e) {
		    LOGGER.error("导入偏线价格方案: "+e.getMessage());
			return returnError("导入偏线价格方案: "+e.getMessage());
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
}
