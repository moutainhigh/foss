package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrStaffService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TfrCtrStaffVo;

public class TfrCtrStaffAction extends AbstractAction {

	private static final long serialVersionUID = 3506849799937425775L;

	private ITfrCtrStaffService tfrCtrStaffService;

	private TfrCtrStaffVo tfrCtrStaffVo = new TfrCtrStaffVo();

	/**
	 * 导出Excel 文件流
	 */
	private InputStream inputStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public void setTfrCtrStaffService(ITfrCtrStaffService tfrCtrStaffService) {
		this.tfrCtrStaffService = tfrCtrStaffService;
	}

	public TfrCtrStaffVo getTfrCtrStaffVo() {
		return tfrCtrStaffVo;
	}

	public void setTfrCtrStaffVo(TfrCtrStaffVo tfrCtrStaffVo) {
		this.tfrCtrStaffVo = tfrCtrStaffVo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String tfrCtrStaffIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity transferCenter = tfrCtrStaffService
				.getTfrCtrBySubCode(currentDeptCode);
		String parentTfrCtrCode = null;
		String parentTfrCtrName = null;
		if (transferCenter != null) {
			parentTfrCtrCode = transferCenter.getCode();
			parentTfrCtrName = transferCenter.getName();
		}
		tfrCtrStaffVo.setParentTfrCtrCode(parentTfrCtrCode);
		tfrCtrStaffVo.setParentTfrCtrName(parentTfrCtrName);
		return SUCCESS;
	}

	public String queryTfrCtrStaffDtos() {
		TfrCtrStaffQcDto dto = tfrCtrStaffVo.getTfrCtrStaffQcDto();
		List<TfrCtrStaffDto> tfrCtrStaffDtos = tfrCtrStaffService
				.queryTfrCtrStaffDtos(dto);
		tfrCtrStaffVo.setTfrCtrStaffDtos(tfrCtrStaffDtos);
		return SUCCESS;
	}

	public String queryTfrCtrStaff3DayNoDuty() {
		String transferCenterCode = tfrCtrStaffVo.getTransferCenterCode();
		Date queryDate = tfrCtrStaffVo.getQueryDate();
		List<TfrCtrStaffNoDutyEntity> tfrCtrStaffNoDutyEntities = tfrCtrStaffService
				.queryTfrCtrStaff3DayNoDuty(transferCenterCode, queryDate);
		tfrCtrStaffVo.setTfrCtrStaffNoDutyEntities(tfrCtrStaffNoDutyEntities);
		return SUCCESS;
	}

	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String result;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			result = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			result = URLEncoder.encode(name, "UTF-8");
		}
		return result;
	}

	public String exportTfrCtrStaff3DayNoDuty() {

		String transferCenterCode = tfrCtrStaffVo.getTransferCenterCode();
		Date queryDate = tfrCtrStaffVo.getQueryDate();

		ExportResource exportResource = tfrCtrStaffService
				.exportTfrCtrStaff3DayNoDuty(transferCenterCode, queryDate);

		// 导出文件名
		try {
			downloadFileName = encodeFileName("月累计连续3日未出勤名单.xls");
		} catch (UnsupportedEncodingException e) {
			downloadFileName = "tfrCtrStaff3Day.xls";
		}

		ExportSetting exportSetting = new ExportSetting();
		// sheet名
		exportSetting.setSheetName("月累计连续3日未出勤名单");

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 导出文件
		inputStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);

		return SUCCESS;
	}

	public String exportTfrCtrStaffDtos() {
		TfrCtrStaffQcDto dto = tfrCtrStaffVo.getTfrCtrStaffQcDto();

		ExportResource exportResource = tfrCtrStaffService
				.exportTfrCtrStaffDtos(dto);
		// 导出文件名
		try {
			downloadFileName = encodeFileName("外场人员情况.xls");
		} catch (UnsupportedEncodingException e) {
			downloadFileName = "tfrCtrStaff.xls";
		}

		ExportSetting exportSetting = new ExportSetting();
		// sheet名
		exportSetting.setSheetName("月累计连续3日未出勤名单");

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 导出文件
		inputStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);

		return SUCCESS;
	}
}
