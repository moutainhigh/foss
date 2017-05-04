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
import com.deppon.foss.module.settlement.closing.api.server.service.IDvrReturnCodService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.DvrReturnCodVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 退代收货款
 * @author foss-pengzhen
 * @date 2013-4-1 下午6:26:50
 * @since
 * @version
 */
public class DvrReturnCodAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5062129924475847849L;

	private DvrReturnCodVo dvrReturnCodVo;
	
	private IDvrReturnCodService dvrReturnCodService;
	
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
	public String querydvrReturnCodByConditions(){
		try {
			//获取界面传入的退代收货款参数
			DvrReturnCodDto dvrReturnCodDto = dvrReturnCodVo.getDvrReturnCodDto();
			//设置退代收货款返回的数据
			dvrReturnCodVo.setDvrReturnCodDto(dvrReturnCodService
					.queryDvrReturnCodByConditions(dvrReturnCodDto, getStart(), getLimit()));
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
	public String dvrReturnCodExport(){
		try {
			//获取界面传入的退代收货款参数
			DvrReturnCodDto dvrReturnCodDto = dvrReturnCodVo.getDvrReturnCodDto();
			// 查询参数不能为空
			if (null == dvrReturnCodDto) {
				return returnError("查询参数为空");
			}

			// 查询报表期间不能为空
			if (StringUtils.isEmpty(dvrReturnCodDto.getPeriod())) {
				return returnError("查询退代收货款报表期间为空");
			}
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置导出excel名称
			String exportXlsName = "退代收货款报表_" + dvrReturnCodDto.getPeriod();
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
			ExportResource exportResource = dvrReturnCodService.exportDvrReturnCods(dvrReturnCodDto, currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
		    Map<Integer, String> map = new HashMap<Integer, String>(); 
		    map.put(SettlementReportNumber.TEN, "float");
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
	 * @return  the dvrReturnCodVo
	 */
	public DvrReturnCodVo getDvrReturnCodVo() {
		return dvrReturnCodVo;
	}


	
	/**
	 * @param dvrReturnCodVo the dvrReturnCodVo to set
	 */
	public void setDvrReturnCodVo(DvrReturnCodVo dvrReturnCodVo) {
		this.dvrReturnCodVo = dvrReturnCodVo;
	}


	
	/**
	 * @param dvrReturnCodService the dvrReturnCodService to set
	 */
	public void setDvrReturnCodService(IDvrReturnCodService dvrReturnCodService) {
		this.dvrReturnCodService = dvrReturnCodService;
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

}
