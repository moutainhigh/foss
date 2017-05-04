package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.ICargoFcstService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CargoFcstVo;

public class CargoFcstAction extends AbstractAction {

	private static final long serialVersionUID = 475127377224533855L;

	private CargoFcstVo cargoFcstVo = new CargoFcstVo();

	private InputStream excelStream;

	private String downloadFileName;

	private ICargoFcstService cargoFcstService;

	public CargoFcstVo getCargoFcstVo() {
		return cargoFcstVo;
	}

	public void setCargoFcstVo(CargoFcstVo cargoFcstVo) {
		this.cargoFcstVo = cargoFcstVo;
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

	public void setCargoFcstService(ICargoFcstService cargoFcstService) {
		this.cargoFcstService = cargoFcstService;
	}

	/**
	 * @desc 入口函数，求当前部门的所属外场
	 * @return
	 * @date 2015年1月19日 上午10:42:00
	 * @author Ouyang
	 */
	public String cargoFcstIndex() {

		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity transferCenter = cargoFcstService
				.queryTfrCtrBySubCode(currentDeptCode);

		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (transferCenter != null) {
			tfrCtrCode = transferCenter.getCode();
			tfrCtrName = transferCenter.getName();
		}
		cargoFcstVo.setTfrCtrCode(tfrCtrCode);
		cargoFcstVo.setTfrCtrName(tfrCtrName);
		
		return SUCCESS;
	}

	/**
	 * @desc 界面查询
	 * @return
	 * @date 2015年3月19日 下午4:08:07
	 * @author Ouyang
	 */
	public String findFcstResult() {
		CargoFcstDto parameter = cargoFcstVo.getCargoFcstDto();
		cargoFcstVo.setCargoFcstResultDtos(cargoFcstService
				.findFcstResult(parameter));
		
		String tfrCtrCode = parameter.getTfrCtrCode();
		String config = cargoFcstService.findTfrCtrConfig(tfrCtrCode);
		if(StringUtils.isEmpty(config)){
			config = cargoFcstService.findDefaultConfig();
		}
		if(StringUtils.isNotEmpty(config)){
			config = config.substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_2)+":"+config.substring(PlatformConstants.SONAR_NUMBER_2);
		}
		cargoFcstVo.setConfig(config);
		return SUCCESS;
	}

	/**
	 * @desc 导出
	 * @return
	 * @date 2014年11月19日 上午10:19:59
	 * @author Ouyang
	 */
	public String exportCargoFcst() {
		CargoFcstDto parameter = cargoFcstVo.getCargoFcstDto();

		ExportResource exportResource = new ExportResource();
		try {
			exportResource = exportResource(parameter);
		} catch (BusinessException e) {
			return returnError(e);
		}

		String fileName = "货量预测.xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("货量预测");

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);

		return SUCCESS;
	}

	/**
	 * @desc 导出Excel
	 * @param parameter
	 * @return
	 * @date 2014年12月16日 下午8:15:27
	 * @author Ouyang
	 */
	private ExportResource exportResource(CargoFcstDto parameter) {

		List<List<String>> rowList = new ArrayList<List<String>>();

		List<CargoFcstResultDto> fcstResult = cargoFcstService
				.findFcstResult(parameter);

		for (CargoFcstResultDto item : fcstResult) {
			List<String> row = new ArrayList<String>();
			// 出发(到达)名称
			row.add(item.getLineName());

			// 预测货量，长途到达，重量、体积
			row.add(convertNum2Str(item.getFcstWeightArrLng()));
			row.add(convertNum2Str(item.getFcstVolumeArrLng()));

			// 预测货量，短途到达，重量、体积
			row.add(convertNum2Str(item.getFcstWeightArrSht()));
			row.add(convertNum2Str(item.getFcstVolumeArrSht()));

			// 预测货量，接送货到达，重量、体积
			row.add(convertNum2Str(item.getFcstWeightArrLng()));
			row.add(convertNum2Str(item.getFcstVolumeArrLng()));

			// 预测货量，长途出发，重量、体积
			row.add(convertNum2Str(item.getFcstWeightDptLng()));
			row.add(convertNum2Str(item.getFcstVolumeDptLng()));

			// 预测货量，短途出发，重量、体积
			row.add(convertNum2Str(item.getFcstWeightDptSht()));
			row.add(convertNum2Str(item.getFcstVolumeDptSht()));

			// 预测货量，派送出发，重量、体积、票数
			row.add(convertNum2Str(item.getFcstWeightDptDispath()));
			row.add(convertNum2Str(item.getFcstVolumeDptDispath()));

			// 预测总货量，重量、体积
			row.add(convertNum2Str(item.getFcstWeight()));
			row.add(convertNum2Str(item.getFcstVolume()));

			// 1周前的实际总货量，重量、体积
			row.add(convertNum2Str(item.getActualWeight7()));
			row.add(convertNum2Str(item.getActualVolume7()));

			// 2周前的实际总货量，重量、体积
			row.add(convertNum2Str(item.getActualWeight14()));
			row.add(convertNum2Str(item.getActualVolume14()));

			// 3周前的实际总货量，重量、体积
			row.add(convertNum2Str(item.getActualWeight21()));
			row.add(convertNum2Str(item.getActualVolume21()));

			// 4周前的实际总货量，重量、体积
			row.add(convertNum2Str(item.getActualWeight28()));
			row.add(convertNum2Str(item.getActualVolume28()));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		HeaderRows[] headerRows = { new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_2, PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_0, "线路"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_6, "预测到达货量"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_7, PlatformConstants.SONAR_NUMBER_12, "预测出发货量"),

				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_2, "长途"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_3, PlatformConstants.SONAR_NUMBER_4, "短途"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_5, PlatformConstants.SONAR_NUMBER_6, "集中接货"),

				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_7, PlatformConstants.SONAR_NUMBER_8, "长途"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_9, PlatformConstants.SONAR_NUMBER_10, "短途"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_11, PlatformConstants.SONAR_NUMBER_12, "派送货量"),

				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_13, PlatformConstants.SONAR_NUMBER_14, "预测总操作货量"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_15, PlatformConstants.SONAR_NUMBER_16, "前一周同期实际货量"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_17, PlatformConstants.SONAR_NUMBER_18, "前二周同期实际货量"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_19, PlatformConstants.SONAR_NUMBER_20, "前三周同期实际货量"),
				new HeaderRows(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_1, PlatformConstants.SONAR_NUMBER_21, PlatformConstants.SONAR_NUMBER_22, "前四周同期实际货量") };

		ExportResource sheet = new ExportResource();
		sheet.setHeaderHeight(PlatformConstants.SONAR_NUMBER_3);
		sheet.setHeaderList(Arrays.asList(headerRows));
		
		String[] source = { "重量", "体积" };
		
		String[] heads = new String[PlatformConstants.SONAR_NUMBER_1];
		for (int i = PlatformConstants.SONAR_NUMBER_0; i <= PlatformConstants.SONAR_NUMBER_10; i++) {
			heads = (String[]) ArrayUtils.addAll(heads, source);
		}
		
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		return sheet;
	}

	/**
	 * @desc 数字转化成字符传
	 * @param num
	 * @return
	 * @date 2015年3月19日 下午5:16:02
	 * @author Ouyang
	 */
	private String convertNum2Str(BigDecimal num) {
		return num == null ? null : num.toString();
	}

	/**
	 * @desc 导出Excel文件名处理
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @date 2014年11月19日 上午10:20:21
	 * @author Ouyang
	 */
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
