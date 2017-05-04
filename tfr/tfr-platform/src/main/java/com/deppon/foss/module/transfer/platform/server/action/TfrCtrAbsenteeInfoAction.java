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
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrAbsenteeInfoService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TfrCtrAbsenteeInfoVo;

public class TfrCtrAbsenteeInfoAction extends AbstractAction {

	private static final long serialVersionUID = -6077095794181482749L;

	private ITfrCtrAbsenteeInfoService tfrCtrAbsenteeInfoService;

	private TfrCtrAbsenteeInfoVo tfrCtrAbsenteeInfoVo = new TfrCtrAbsenteeInfoVo();

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
	private InputStream excelStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public void setTfrCtrAbsenteeInfoService(
			ITfrCtrAbsenteeInfoService tfrCtrAbsenteeInfoService) {
		this.tfrCtrAbsenteeInfoService = tfrCtrAbsenteeInfoService;
	}

	public TfrCtrAbsenteeInfoVo getTfrCtrAbsenteeInfoVo() {
		return tfrCtrAbsenteeInfoVo;
	}

	public void setTfrCtrAbsenteeInfoVo(
			TfrCtrAbsenteeInfoVo tfrCtrAbsenteeInfoVo) {
		this.tfrCtrAbsenteeInfoVo = tfrCtrAbsenteeInfoVo;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	/**
	 * 查询外场异常人员信息界面跳转， 获取当前部门是否外场，用于判断查询条件中外场是否可编辑
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	public String queryTfrCtrAbsenteeIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = tfrCtrAbsenteeInfoService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				tfrCtrAbsenteeInfoVo.setParentTfrCtrCode(transferCenter
						.get("code"));
				tfrCtrAbsenteeInfoVo.setParentTfrCtrName(transferCenter
						.get("name"));
			} else {
				tfrCtrAbsenteeInfoVo.setParentTfrCtrCode(null);
				tfrCtrAbsenteeInfoVo.setParentTfrCtrName(null);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 新增外场人员异常信息
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	public String insertTfrCtrAbsenteeInfo() {

		TfrCtrAbsenteeInfoEntity entity = tfrCtrAbsenteeInfoVo
				.getTfrCtrAbsenteeInfoEntity();

		try {
			tfrCtrAbsenteeInfoService.insertTfrCtrAbsenteeInfo(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 删除外场异常人员信息
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	public String deleteTfrCtrAbsenteeInfos() {

		List<String> ids = tfrCtrAbsenteeInfoVo.getIds();

		try {
			tfrCtrAbsenteeInfoService.deleteTfrCtrAbsenteeInfos(ids);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 修改外场异常人员信息
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	public String updateTfrCtrAbsenteeInfo() {
		TfrCtrAbsenteeInfoEntity entity = tfrCtrAbsenteeInfoVo
				.getTfrCtrAbsenteeInfoEntity();
		try {
			tfrCtrAbsenteeInfoService.updateTfrCtrAbsenteeInfo(entity);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 分页查询外场异常人员信息
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	public String queryPagingTfrCtrAbsenteeInfos() {

	
		
		TfrCtrAbsenteeInfoQcDto qcDto = tfrCtrAbsenteeInfoVo
				.getTfrCtrAbsenteeInfoQcDto();

		List<TfrCtrAbsenteeInfoEntity> tfrCtrAbsenteeInfoEntities;
		try {
			tfrCtrAbsenteeInfoEntities = tfrCtrAbsenteeInfoService
					.queryPagingTfrCtrAbsenteeInfos(qcDto, super.getStart(),
							super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = tfrCtrAbsenteeInfoService.queryTfrCtrAbsenteeInfoCount(qcDto);

		/**
		 * @desc 统计 自离、旷工、工伤、长假 总人数
		 * @author 105795
		 * @date 2015-01-22
		 * */
		//自离人数
		int restgnationNum=0;
		//旷工人数
		int absenteeismNum=0;
		//工伤人数
		int industrialInjuryNum=0;
		//长假人数
		int longHolidaysNum=0;
		
		List<TfrCtrAbsenteeInfoEntity> resultList=tfrCtrAbsenteeInfoService.queryPagingTfrCtrAbsenteeInfos(qcDto);
		for(TfrCtrAbsenteeInfoEntity entity:resultList)
		{
			if(PlatformConstants.ABSENTEESTATUS_CODES[PlatformConstants.SONAR_NUMBER_0].equalsIgnoreCase(entity.getAbsenteeStatus()))
			{
				restgnationNum++;
			}else if(PlatformConstants.ABSENTEESTATUS_CODES[PlatformConstants.SONAR_NUMBER_1].equalsIgnoreCase(entity.getAbsenteeStatus())){
				absenteeismNum++;
			}else if(PlatformConstants.ABSENTEESTATUS_CODES[PlatformConstants.SONAR_NUMBER_2].equalsIgnoreCase(entity.getAbsenteeStatus())){
				industrialInjuryNum++;
			}else if(PlatformConstants.ABSENTEESTATUS_CODES[PlatformConstants.SONAR_NUMBER_3].equalsIgnoreCase(entity.getAbsenteeStatus())){
				longHolidaysNum++;
			}
		}
		tfrCtrAbsenteeInfoVo.setRestgnationNum(restgnationNum);
		tfrCtrAbsenteeInfoVo.setAbsenteeismNum(absenteeismNum);

		tfrCtrAbsenteeInfoVo.setIndustrialInjuryNum(industrialInjuryNum);
		tfrCtrAbsenteeInfoVo.setLongHolidaysNum(longHolidaysNum);
		
		tfrCtrAbsenteeInfoVo.setTfrCtrAbsenteeInfoEntities(tfrCtrAbsenteeInfoEntities);

        super.setTotalCount(totalCount);
		
		return returnSuccess();
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

	public String exportTfrCtrAbsenteeInfos() {

		try {
			downloadFileName = encodeFileName(PlatformConstants.TFRCTRABSENTEEINFO_EXCEL_NAME);

			ExportResource exportResource = tfrCtrAbsenteeInfoService
					.exportTfrCtrAbsenteeInfos(tfrCtrAbsenteeInfoVo.getIds(),
							tfrCtrAbsenteeInfoVo.getTfrCtrAbsenteeInfoQcDto());
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting
					.setSheetName(PlatformConstants.TFRCTRABSENTEEINFO_EXCEL_NAME);
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			excelStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", "");
		}
	}

	public String importTfrCtrAbsenteeInfos() {
		try {
			tfrCtrAbsenteeInfoService.importTfrCtrAbsenteeInfos(
					uploadFileFileName, uploadFile);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	
}
