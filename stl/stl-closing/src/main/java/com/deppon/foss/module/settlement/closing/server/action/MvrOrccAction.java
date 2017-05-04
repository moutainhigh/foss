package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrOrccService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrOrccVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

public class MvrOrccAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 界面查询条件
	 */
	MvrOrccVo vo;
	/**
	 *业务处理servcie
	 */
	IMvrOrccService mvrOrccService;

	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	/**
	 * 日志
	 */
	private static final Logger logger =LogManager.getLogger(MvrOrccAction.class);
	
	/**
	 * 始发外请车报表查询action
	 * @return
	 */
	@JSON
	public String queryOrccByConditions(){
		List<MvrOrccEntity> list =  mvrOrccService.queryMvrOrccByParam(vo.getDto(), start, limit);
		vo.setOrccEntitys(list);
		totalCount =mvrOrccService.queryMvrOrccByParamCount(vo.getDto());
		return returnSuccess();
	}
	/**
	 * 导出始发外请车报表
	 */
	public String exportMrvOrcc() {

		// 设置导出excel名称
		String exportXlsName = "始发外请车月报表_"
				+ vo.getDto().getPeriod();
		try {
			// 转化编码
			this.setExcelName(new String((exportXlsName)
					.getBytes(SettlementConstants.UNICODE_GBK),
					SettlementConstants.UNICODE_ISO));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return returnError("导出Excel失败");
		}

		// 查询未确认收银的数据
		CurrentInfo currInfo = FossUserContext.getCurrentInfo();
		ExportResource exportResource = mvrOrccService.exportMvrOrcc(vo.getDto(), currInfo);
		// 创建导出表头对象
		ExportSetting exportSetting = new ExportSetting();
		// 设置名称
		exportSetting.setSheetName(exportXlsName);

		// 设置格式
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = SettlementReportNumber.EIGHT; i <= SettlementReportNumber.FOURTEEN; i++) {
			map.put(i, "float");
		}
		exportSetting.setDataType(map);

		// 创建导出工具类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 导出成文件
		inputStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);
		return returnSuccess();

	}

	public MvrOrccVo getVo() {
		return vo;
	}

	public void setVo(MvrOrccVo vo) {
		this.vo = vo;
	}

	public void setMvrOrccService(IMvrOrccService mvrOrccService) {
		this.mvrOrccService = mvrOrccService;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	
	

}
