package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IDensityService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DensityVo;

public class DensityAction extends AbstractAction {

	private static final long serialVersionUID = -5484456017452250914L;

	private InputStream excelStream;

	private String downloadFileName;

	private IDensityService densityService;

	private IPlatformCommonService platformCommonService;

	private DensityVo densityVo = new DensityVo();

	public DensityVo getDensityVo() {
		return densityVo;
	}

	public void setDensityVo(DensityVo densityVo) {
		this.densityVo = densityVo;
	}

	public void setDensityService(IDensityService densityService) {
		this.densityService = densityService;
	}

	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
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
	 * @desc 入口函数
	 * @return
	 * @date 2015年4月7日 下午12:07:06
	 * @author Ouyang
	 */
	public String densityIndex() {

		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity tfrCtr = platformCommonService
				.querySupTfrCtr(currentDeptCode);

		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (tfrCtr != null) {
			tfrCtrCode = tfrCtr.getCode();
			tfrCtrName = tfrCtr.getName();
			densityVo.setTfrCtrCode(tfrCtrCode);
			densityVo.setTfrCtrName(tfrCtrName);
		} else {
			Map<String, String> hq = platformCommonService
					.findSupHq(currentDeptCode);
			densityVo.setHqCode(hq.get("HQ_CODE"));
			densityVo.setHqName(hq.get("HQ_NAME"));
		}

		return SUCCESS;
	}

	/**
	 * @desc 查询外场密度峰值
	 * @return
	 * @date 2015年4月7日 下午12:09:59
	 * @author Ouyang
	 */
	public String findTfrCtrDensityPeak() {
		densityVo.setTfrCtrDensityPeakEntities(densityService
				.findTfrCtrDensityPeak(densityVo.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 待叉区密度查询
	 * @return
	 * @date 2015年4月7日 下午12:11:12
	 * @author Ouyang
	 */
	public String findForkAreaDensity() {
		densityVo.setForkAreaDensityEntities(densityService
				.findForkAreaDensity(densityVo.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 派送库区密度查询
	 * @return
	 * @date 2015年4月7日 下午12:11:37
	 * @author Ouyang
	 */
	public String findDptAreaDensity() {
		densityVo.setDptAreaDensityEntities(densityService
				.findDptAreaDensity(densityVo.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 中转库区密度查询
	 * @return
	 * @date 2015年4月7日 下午12:11:37
	 * @author Ouyang
	 */
	public String findTfrAreaDensity() {
		densityVo.setTfrAreaDensityEntities(densityService
				.findTfrAreaDensity(densityVo.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 日趋势密度查询
	 * @return
	 * @date 2015年4月14日 下午2:17:09
	 * @author Ouyang
	 */
	public String findTrendDay() {
		densityVo.setTrendDtos(densityService.findTrendDay(densityVo
				.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 月趋势密度查询
	 * @return
	 * @date 2015年4月14日 下午2:17:09
	 * @author Ouyang
	 */
	public String findTrendMonth() {
		densityVo.setTrendDtos(densityService.findTrendMonth(densityVo
				.getDensityQcDto()));
		return SUCCESS;
	}

	/**
	 * @desc 导出外场密度峰值
	 * @return
	 * @date 2015年4月7日 下午12:24:39
	 * @author Ouyang
	 */
	public String exportTfrCtrDensityPeak() {
		String sheetName = "库区密度信息";
		try {
			exportResourceTfrCtrDensityPeak(
					densityService.findTfrCtrDensityPeak(densityVo
							.getDensityQcDto()), sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 导出待叉区密度
	 * @return
	 * @date 2015年4月7日 下午12:24:51
	 * @author Ouyang
	 */
	public String exportForkAreaDensity() {
		String sheetName = "待叉区密度";
		try {
			exportResourceForkAreaDensity(
					densityService.findForkAreaDensity(densityVo
							.getDensityQcDto()), sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 导出派送库区密度
	 * @return
	 * @date 2015年4月7日 下午12:24:51
	 * @author Ouyang
	 */
	public String exportDptAreaDensity() {
		String sheetName = "派送库区密度";
		try {
			exportResourceDptAreaDensity(
					densityService.findDptAreaDensity(densityVo
							.getDensityQcDto()), sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 导出中转库区密度
	 * @return
	 * @date 2015年4月7日 下午12:24:51
	 * @author Ouyang
	 */
	public String exportTfrAreaDensity() {
		String sheetName = "中转库区密度";
		try {
			exportResourceTfrAreaDensity(
					densityService.findTfrAreaDensity(densityVo
							.getDensityQcDto()), sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @desc 导出外场密度峰值
	 * @param infos
	 * @param sheetName
	 * @date 2015年4月7日 下午12:29:14
	 * @author Ouyang
	 */
	private void exportResourceTfrCtrDensityPeak(
			List<TfrCtrDensityPeakEntity> infos, String sheetName) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		for (TfrCtrDensityPeakEntity item : infos) {
			List<String> row = new ArrayList<String>();
			// 本部
			row.add(item.getHqName());
			// 外场
			row.add(item.getTfrCtrName());

			// 库区总容量
			row.add(convertNum2Str(item.getAreaVolumeD(), false));
			// 日库区密度峰值
			row.add(convertNum2Str(item.getPeakDensityD(), false));
			// 日库区密度峰值时间点
			row.add(item.getPeakTimeD());
			// 月库区密度峰值
			row.add(convertNum2Str(item.getPeakDensityM(), true));
			// 月库区密度峰值
			row.add(item.getPeakTimeM());

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = { "本部", "外场", "库区总容量", "日库区密度峰值", "日库区密度峰值时间点",
				"月库区密度峰值", "月库区密度峰值时间点" };
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(sheetName);

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(sheet, exportSetting);

		String fileName = sheetName + ".xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}
	}

	/**
	 * @desc 导出待叉区密度
	 * @param infos
	 * @param sheetName
	 * @date 2015年4月7日 下午12:29:14
	 * @author Ouyang
	 */
	private void exportResourceForkAreaDensity(
			List<ForkAreaDensityEntity> infos, String sheetName) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		for (ForkAreaDensityEntity item : infos) {
			List<String> row = new ArrayList<String>();
			// 外场
			row.add(item.getTfrCtrName());

			// 待叉区编号
			row.add(item.getAreaCode());
			// 待叉区容量
			row.add(convertNum2Str(item.getAreaVolume(), false));
			// 待叉区货量
			row.add(convertNum2Str(item.getGoodsVolume(), false));

			// 待叉区密度
			row.add(convertNum2Str(item.getDensity(), true));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = { "外场", "待叉区编号", "待叉区容量", "待叉区货量", "待叉区密度" };
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(sheetName);

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(sheet, exportSetting);

		String fileName = sheetName + ".xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}
	}

	/**
	 * @desc 导出派送库区密度
	 * @param infos
	 * @param sheetName
	 * @date 2015年4月7日 下午12:29:14
	 * @author Ouyang
	 */
	private void exportResourceDptAreaDensity(List<DptAreaDensityEntity> infos,
			String sheetName) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		for (DptAreaDensityEntity item : infos) {
			List<String> row = new ArrayList<String>();
			// 外场
			row.add(item.getTfrCtrName());

			// 分区名称
			row.add(item.getZoneName());
			// 分区名称
			row.add(item.getItemAreaName());
			// 件区容量
			row.add(convertNum2Str(item.getItemAreaVolume(), false));
			// 件区货量
			row.add(convertNum2Str(item.getGoodsVolume(), false));

			// 件区密度
			row.add(convertNum2Str(item.getDensity(), true));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = { "外场", "分区名称", "件区名称", "件区容量", "件区货量", "件区密度" };
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(sheetName);

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(sheet, exportSetting);

		String fileName = sheetName + ".xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}
	}

	/**
	 * @desc 导出中转库区密度
	 * @param infos
	 * @param sheetName
	 * @date 2015年4月7日 下午12:29:14
	 * @author Ouyang
	 */
	private void exportResourceTfrAreaDensity(List<TfrAreaDensityEntity> infos,
			String sheetName) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		for (TfrAreaDensityEntity item : infos) {
			List<String> row = new ArrayList<String>();
			// 外场
			row.add(item.getTfrCtrName());

			// 库区类别
			row.add(item.getAreaUsage());
			// 库区名称
			row.add(item.getAreaName());
			// 件区容量
			row.add(convertNum2Str(item.getAreaVolume(), false));
			// 件区货量
			row.add(convertNum2Str(item.getGoodsVolume(), false));

			// 件区密度
			row.add(convertNum2Str(item.getDensity(), true));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = { "外场", "库区类别", "库区名称", "库区容量", "库区货量", "库区密度" };
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(sheetName);

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(sheet, exportSetting);

		String fileName = sheetName + ".xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}
	}

	private String convertNum2Str(BigDecimal num, boolean percent) {
		return num == null ? null : (percent ? num.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100))
				+ "%" : num.toString());
	}

	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String result = null;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			result = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			result = URLEncoder.encode(name, "UTF-8");
		}
		return result;
	}
}
