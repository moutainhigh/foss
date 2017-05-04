package com.deppon.foss.module.transfer.platform.server.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrPersonnelBudgetService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TfrCtrPersonnelBudgetVo;

public class TfrCtrPersonnelBudgetAction extends AbstractAction {

	private static final long serialVersionUID = 1925956101854807471L;

	private ITfrCtrPersonnelBudgetService tfrCtrPersonnelBudgetService;

	private TfrCtrPersonnelBudgetVo tfrCtrPersonnelBudgetVo = new TfrCtrPersonnelBudgetVo();

	/**
	 * 导入文件
	 */
	private File uploadFile;

	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;

	/**
	 * 导出Excel 文件流
	 */
	private InputStream inputStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;

	public void setTfrCtrPersonnelBudgetService(
			ITfrCtrPersonnelBudgetService tfrCtrPersonnelBudgetService) {
		this.tfrCtrPersonnelBudgetService = tfrCtrPersonnelBudgetService;
	}

	public TfrCtrPersonnelBudgetVo getTfrCtrPersonnelBudgetVo() {
		return tfrCtrPersonnelBudgetVo;
	}

	public void setTfrCtrPersonnelBudgetVo(
			TfrCtrPersonnelBudgetVo tfrCtrPersonnelBudgetVo) {
		this.tfrCtrPersonnelBudgetVo = tfrCtrPersonnelBudgetVo;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String tfrCtrPersonnelBudgetIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = tfrCtrPersonnelBudgetService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				tfrCtrPersonnelBudgetVo.setParentTfrCtrCode(transferCenter
						.get("code"));
				tfrCtrPersonnelBudgetVo.setParentTfrCtrName(transferCenter
						.get("name"));
			} else {
				tfrCtrPersonnelBudgetVo.setParentTfrCtrCode(null);
				tfrCtrPersonnelBudgetVo.setParentTfrCtrName(null);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	public String selectTfrCtrPersonnelBudget() {

		TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelBudgetQcDto();
		try {
			List<TfrCtrPersonnelBudgetDto> tfrCtrPersonnelBudgetDtos = tfrCtrPersonnelBudgetService
					.selectTfrCtrPersonnelBudgets(tfrCtrPersonnelBudgetQcDto);

			tfrCtrPersonnelBudgetVo
					.setTfrCtrPersonnelBudgetDtos(tfrCtrPersonnelBudgetDtos);

		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String insertBudget() {

		TfrCtrPersonnelBudgetEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelBudgetEntity();
		try {
			tfrCtrPersonnelBudgetService.insertBudget(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String updateBudget() {
		TfrCtrPersonnelBudgetEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelBudgetEntity();
		try {
			tfrCtrPersonnelBudgetService.updateBudget(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String insertActual() {
		TfrCtrPersonnelActualEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelActualEntity();
		try {
			tfrCtrPersonnelBudgetService.insertActual(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String updateActual() {
		TfrCtrPersonnelActualEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelActualEntity();
		try {
			tfrCtrPersonnelBudgetService.updateActual(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String selectBudgetLast() {
		TfrCtrPersonnelBudgetEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelBudgetEntity();
		try {
			TfrCtrPersonnelBudgetEntity result = tfrCtrPersonnelBudgetService
					.selectBudgetLast(entity);

			tfrCtrPersonnelBudgetVo.setTfrCtrPersonnelBudgetEntity(result);

		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;

	}

	public String selectActualLast() {
		TfrCtrPersonnelActualEntity entity = tfrCtrPersonnelBudgetVo
				.getTfrCtrPersonnelActualEntity();
		try {
			TfrCtrPersonnelActualEntity result = tfrCtrPersonnelBudgetService
					.selectActualLast(entity);

			tfrCtrPersonnelBudgetVo.setTfrCtrPersonnelActualEntity(result);

		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String importTfrCtrPersonnelBudgets() {
		try {
			tfrCtrPersonnelBudgetService.importTfrCtrPersonnelBudgets(
					uploadFileFileName, uploadFile);
		} catch (BusinessException e) {
			return returnError(e);
		}

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

	public String exportTfrCtrPersonnelBudgets() {

		try {
			downloadFileName = encodeFileName("外场预算人员信息.xls");

			ExportResource exportResource = tfrCtrPersonnelBudgetService
					.exportTfrCtrPersonnelBudgets(tfrCtrPersonnelBudgetVo
							.getTfrCtrPersonnelBudgetQcDto());
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName("外场预算人员信息");
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			inputStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return SUCCESS;
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", "");
		}
	}

}
