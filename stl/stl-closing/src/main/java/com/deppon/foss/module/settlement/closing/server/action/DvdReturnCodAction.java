package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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
import com.deppon.foss.module.settlement.closing.api.server.service.IDvdReturnCodService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdReturnCodDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.DvdReturnCodVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;

/**
 * 退代收货款
 * @author foss-pengzhen
 * @date 2013-4-1 下午6:26:50
 * @since
 * @version
 */
public class DvdReturnCodAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5062129924475847849L;

	private DvdReturnCodVo dvdReturnCodVo;
	
	private IDvdReturnCodService dvdReturnCodService;
	
	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(MvrAfiAction.class);
	
	/**
	 * 
	 * 根据多个参数查询退代收货款信息
	 * @author foss-pengzhen
	 * @date 2013-4-1 下午6:26:46
	 * @return
	 * @see
	 */
	@JSON
	public String querydvdReturnCodByConditions(){
		try {
			//获取界面传入的退代收货款参数
			DvdReturnCodDto dvdReturnCodDto = dvdReturnCodVo.getDvdReturnCodDto();
			//设置退代收货款返回的数据
			dvdReturnCodDto = dvdReturnCodService.queryDvdReturnCodByConditions(dvdReturnCodDto, getStart(), getLimit());
			dvdReturnCodVo.setDvdReturnCodDto(dvdReturnCodDto);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ERROR;
		}
	}

	/**
	 * 退代收货款报表导出.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-12 上午9:13:29
	 * @see
	 */
	public String dvdReturnCodExport(){
		try {
			//获取界面传入的退代收货款参数
			DvdReturnCodDto dvdReturnCodDto = dvdReturnCodVo.getDvdReturnCodDto();
			// 查询参数不能为空
			if (null == dvdReturnCodDto) {
				return returnError("查询参数为空");
			}

			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置导出excel名称
			String exportXlsName = "退代收货款报表";
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
			ExportResource exportResource = dvdReturnCodService.exportDvdReturnCods(dvdReturnCodDto, currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
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
	 * @return  the dvdReturnCodVo
	 */
	public DvdReturnCodVo getDvdReturnCodVo() {
		return dvdReturnCodVo;
	}


	
	/**
	 * @param dvdReturnCodVo the dvdReturnCodVo to set
	 */
	public void setDvdReturnCodVo(DvdReturnCodVo dvdReturnCodVo) {
		this.dvdReturnCodVo = dvdReturnCodVo;
	}



	/**
	 * @return  the excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	
	/**
	 * @param excelName the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	
	/**
	 * @return  the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
	/**
	 * @param dvdReturnCodService the dvdReturnCodService to set
	 */
	public void setDvdReturnCodService(IDvdReturnCodService dvdReturnCodService) {
		this.dvdReturnCodService = dvdReturnCodService;
	}

	
}
