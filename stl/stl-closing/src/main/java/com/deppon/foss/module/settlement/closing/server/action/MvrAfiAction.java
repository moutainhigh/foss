package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrAfiVo;
import com.deppon.foss.module.settlement.closing.server.service.impl.MvrAfiService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 始发、空运Action.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:13:06
 * @since
 * @version
 */
public class MvrAfiAction extends AbstractAction {

	/** 序列号. */
	private static final long serialVersionUID = -625898101064703956L;

	/** 始发、空运Vo. */
	private MvrAfiVo mvrAfiVo;

	/** 注入始发、空运往来service. */
	private MvrAfiService mvrAfiService;
	
	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(MvrAfiAction.class);
	
	/**
	 * 根据多个参数查询始发空运信息.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:12:49
	 * @see
	 */
	@JSON
	public String queryMvrAfiByConditions(){
		try {
			MvrAfiDto mvrAfiDto = mvrAfiVo.getMvrAfiDto();
			//根据多个参数查询始发偏线信息
			MvrAfiDto dto = mvrAfiService.queryMvrAfiByConditions(mvrAfiDto,getStart(),getLimit());
			if(dto!=null){
				mvrAfiVo.setMvrAfiDto(dto);
				this.setTotalCount(dto.getSum());	
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ERROR;
		}
	}
	
	/**
	 * 始发空运往来月报表导出.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-12 上午9:13:29
	 * @see
	 */
	public String mvrAfiExport(){
		try {
			// 查询参数不能为空
			if (mvrAfiVo.getMvrAfiDto() == null) {
				return returnError("查询参数为空");
			}

			// 查询报表期间不能为空
			if (StringUtils.isEmpty(mvrAfiVo.getMvrAfiDto().getPeriod())) {
				return returnError("查询空运月报表期间为空");
			}
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置导出excel名称
			String exportXlsName = "始发空运往来月报表_" + mvrAfiVo.getMvrAfiDto().getPeriod();
			try {
//				this.setExcelName(URLEncoder.encode(exportXlsName,
//						SettlementConstants.UNICODE_UTF));
				
				this.setExcelName(new String((exportXlsName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//查询未确认收银的数据
			ExportResource exportResource = mvrAfiService.exportMvrAfis(mvrAfiVo.getMvrAfiDto(), currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
		    
		    //设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i = SettlementReportNumber.SIX; i <= SettlementReportNumber.TWENTY_FOUR; i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);
		    
		    //创建导出工具类
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);

			return SUCCESS;
		}  catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		    return returnError(e);
		}
	}
	
	/**
	 * Gets the excel name.
	 *
	 * @return  the excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	
	/**
	 * Sets the excel name.
	 *
	 * @param excelName the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	
	/**
	 * Gets the input stream.
	 *
	 * @return  the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	
	/**
	 * Sets the input stream.
	 *
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Gets the mvr afi vo.
	 *
	 * @return  the mvrAfiVo
	 */
	public MvrAfiVo getMvrAfiVo() {
		return mvrAfiVo;
	}

	
	/**
	 * Sets the mvr afi vo.
	 *
	 * @param mvrAfiVo the mvrAfiVo to set
	 */
	public void setMvrAfiVo(MvrAfiVo mvrAfiVo) {
		this.mvrAfiVo = mvrAfiVo;
	}


	
	/**
	 * Sets the mvr afi service.
	 *
	 * @param mvrAfiService the mvrAfiService to set
	 */
	public void setMvrAfiService(MvrAfiService mvrAfiService) {
		this.mvrAfiService = mvrAfiService;
	} 
	
	
}
