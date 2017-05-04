package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import com.deppon.foss.module.transfer.platform.api.server.service.ICargoArrivedService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CargoArrivedVo;

public class CargoArrivedAction extends AbstractAction {

	private static final long serialVersionUID = -7453134576871930427L;

	private ICargoArrivedService cargoArrivedService;

	private CargoArrivedVo cargoArrivedVo = new CargoArrivedVo();

	private InputStream excelStream;

	private String downloadFileName;

	public void setCargoArrivedService(ICargoArrivedService cargoArrivedService) {
		this.cargoArrivedService = cargoArrivedService;
	}

	public CargoArrivedVo getCargoArrivedVo() {
		return cargoArrivedVo;
	}

	public void setCargoArrivedVo(CargoArrivedVo cargoArrivedVo) {
		this.cargoArrivedVo = cargoArrivedVo;
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
	 * @desc 入口函数，求当前部门的所属外场
	 * @return
	 * @date 2015年1月19日 上午10:42:00
	 * @author Ouyang
	 */
	public String cargoArrivedIndex() {

		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity transferCenter = cargoArrivedService
				.queryTfrCtrBySubCode(currentDeptCode);

		String transferCenterCode = null;
		String transferCenterName = null;
		if (transferCenter != null) {
			transferCenterCode = transferCenter.getCode();
			transferCenterName = transferCenter.getName();
		}
		cargoArrivedVo.setTransferCenterCode(transferCenterCode);
		cargoArrivedVo.setTransferCenterName(transferCenterName);

		return SUCCESS;
	}

	/**
	 * @desc 到达货量统计
	 * @return
	 * @date 2015年1月19日 上午10:42:24
	 * @author Ouyang
	 */
	public String findCargoArrived() {
		CargoArrivedQcDto parameter = cargoArrivedVo.getCargoArrivedQcDto();
		List<CargoArrivedDto> cargoArrived = cargoArrivedService
				.findCargoArrived(parameter);
		if (CollectionUtils.isNotEmpty(cargoArrived)) {
			cargoArrivedVo.setCargoArrivedDtos(cargoArrived);
		}
		return SUCCESS;
	}

	/**
	 * @desc 长途在途明细
	 * @return
	 * @date 2015年1月19日 上午10:42:49
	 * @author Ouyang
	 */
	public String findDetails() {
		CargoArrivedQcDto parameter = cargoArrivedVo.getCargoArrivedQcDto();
		List<CargoArrivedDetailDto> cargoArrivedDetails = cargoArrivedService
				.findDetails(parameter);
		cargoArrivedVo.setCargoArrivedDetailDtos(cargoArrivedDetails);
		return SUCCESS;
	}

	/**
	 * @desc 导出
	 * @return
	 * @date 2014年11月19日 上午10:19:59
	 * @author Ouyang
	 */
	public String exportCargoArrived() {
		CargoArrivedQcDto parameter = cargoArrivedVo.getCargoArrivedQcDto();

		ExportResource exportResource = new ExportResource();
		try {
			exportResource = exportResource(parameter);
		} catch (BusinessException e) {
			return returnError(e);
		}

		String fileName = "到达货量统计.xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("到达货量统计");

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
	private ExportResource exportResource(CargoArrivedQcDto parameter) {

		List<List<String>> rowList = new ArrayList<List<String>>();

		List<CargoArrivedDto> cargoArrived = cargoArrivedService
				.findCargoArrived(parameter);

		for (CargoArrivedDto item : cargoArrived) {
			List<String> row = new ArrayList<String>();
			// 出发部门
			row.add(item.getOrigDeptName());

			// 总重量
			row.add(String.valueOf(item.getWeightTotal()));
			// 总体积
			row.add(String.valueOf(item.getVolumeTotal()));
			// 总票数
			row.add(String.valueOf(item.getVoteTotal()));

			// 卡航重量
			row.add(String.valueOf(item.getWeightFlf()));
			// 卡航体积
			row.add(String.valueOf(item.getVolumeFlf()));
			// 卡航票数
			row.add(String.valueOf(item.getVoteFlf()));

			// 城运重量
			row.add(String.valueOf(item.getWeightFsf()));
			// 城运体积
			row.add(String.valueOf(item.getVolumeFsf()));
			// 城运票数
			row.add(String.valueOf(item.getVoteFsf()));

			// 快递重量
			row.add(String.valueOf(item.getWeightExp()));
			// 快递体积
			row.add(String.valueOf(item.getVolumeExp()));
			// 快递票数
			row.add(String.valueOf(item.getVoteExp()));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = {"出发部门", "总重量", "总体积", "总票数", "卡航重量", "卡航体积", "卡航票数",
				"城运重量", "城运体积", "城运票数", "快递重量", "快递体积", "快递票数"};
		sheet.setHeads(heads);
		sheet.setRowList(rowList);
		return sheet;
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
