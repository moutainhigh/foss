package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.deppon.foss.module.transfer.platform.api.server.service.IStockDurationService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.StockDurationVo;

public class StockDurationAction extends AbstractAction {

	private static final long serialVersionUID = 981757044736669796L;

	private IStockDurationService stockDurationService;

	private StockDurationVo stockDurationVo = new StockDurationVo();

	private InputStream excelStream;

	private String downloadFileName;

	public void setStockDurationService(
			IStockDurationService stockDurationService) {
		this.stockDurationService = stockDurationService;
	}

	public StockDurationVo getStockDurationVo() {
		return stockDurationVo;
	}

	public void setStockDurationVo(StockDurationVo stockDurationVo) {
		this.stockDurationVo = stockDurationVo;
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
	 * @desc 查询提货方式
	 * @return 
	 * @date 2015年4月10日 上午11:33:58
	 * @author Ouyang
	 */
	public String findReceiveMethod() {
		stockDurationVo.setReceiveMethods(stockDurationService.findReceiveMethod());
		return SUCCESS;
	}

	/**
	 * @desc 入口函数
	 * @return
	 * @date 2015年3月26日 下午5:29:18
	 * @author Ouyang
	 */
	public String stockDurationIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity transferCenter = stockDurationService
				.queryTfrCtrBySubCode(currentDeptCode);

		String tfrCtrCode = null;
		String tfrCtrName = null;
		if (transferCenter != null) {
			tfrCtrCode = transferCenter.getCode();
			tfrCtrName = transferCenter.getName();
		}
		stockDurationVo.setTfrCtrCode(tfrCtrCode);
		stockDurationVo.setTfrCtrName(tfrCtrName);

		return SUCCESS;
	}

	/**
	 * @desc 分页查询快递中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	public String findTfrExp() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setTfrExps(stockDurationService.findTfrExp(parameter,
				super.getStart(), super.getLimit()));
		super.setTotalCount(stockDurationService.findTfrExpCnt(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 分页查询零担中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	public String findTfrLtc() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setTfrLtcs(stockDurationService.findTfrLtc(parameter,
				super.getStart(), super.getLimit()));
		super.setTotalCount(stockDurationService.findTfrLtcCnt(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 分页查询快递派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	public String findDptExp() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setDptExps(stockDurationService.findDptExp(parameter,
				super.getStart(), super.getLimit()));
		super.setTotalCount(stockDurationService.findDptExpCnt(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 分页查询零担派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	public String findDptLtc() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setDptLtcs(stockDurationService.findDptLtc(parameter,
				super.getStart(), super.getLimit()));
		super.setTotalCount(stockDurationService.findDptLtcCnt(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 查询快递数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:06
	 * @author Ouyang
	 */
	public String findExpDay() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setExpDays(stockDurationService.findExpDay(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 查询快递数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:09
	 * @author Ouyang
	 */
	public String findExpMonth() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setExpMonths(stockDurationService
				.findExpMonth(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 查询零担数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:11
	 * @author Ouyang
	 */
	public String findLtcDay() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setLtcDays(stockDurationService.findLtcDay(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 查询零担数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:14
	 * @author Ouyang
	 */
	public String findLtcMonth() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();
		stockDurationVo.setLtcMonths(stockDurationService
				.findLtcMonth(parameter));
		return SUCCESS;
	}

	/**
	 * @desc 导出快递数据日汇总
	 * @return
	 * @date 2015年3月27日 上午10:31:52
	 * @author Ouyang
	 */
	public String exportExpDay() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();

		String sheetName = "快递日均库存时长";
		try {
			exportResource(stockDurationService.findExpDay(parameter),
					sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	/**
	 * @desc 导出快递数据月汇总
	 * @return
	 * @date 2015年3月27日 上午10:31:52
	 * @author Ouyang
	 */
	public String exportExpMonth() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();

		String sheetName = "快递月均库存时长";
		try {
			exportResource(stockDurationService.findExpMonth(parameter),
					sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	/**
	 * @desc 导出零担数据日汇总
	 * @return
	 * @date 2015年3月27日 上午10:31:52
	 * @author Ouyang
	 */
	public String exportLtcDay() {
		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();

		String sheetName = "零担日均库存时长";
		try {
			exportResource(stockDurationService.findLtcDay(parameter),
					sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	/**
	 * @desc 导出零担数据月汇总
	 * @return
	 * @date 2015年3月27日 上午10:31:52
	 * @author Ouyang
	 */
	public String exportLtcMonth() {

		StockDurationQcDto parameter = stockDurationVo.getStockDurationQcDto();

		String sheetName = "零担月均库存时长";
		try {
			exportResource(stockDurationService.findLtcMonth(parameter),
					sheetName);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	/**
	 * @desc
	 * @param infos
	 *            查询结果
	 * @param sheetName
	 *            excel名称
	 * @date 2015年3月27日 上午10:59:55
	 * @author Ouyang
	 */
	private void exportResource(List<StockDuration> infos, String sheetName) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		for (StockDuration item : infos) {
			List<String> row = new ArrayList<String>();
			// 外场
			row.add(item.getTfrCtrName());

			// 卸车等待时长
			row.add(convertNum2Str(item.getAvgUnloadWaitTime()));
			// 卸车时长
			row.add(convertNum2Str(item.getAvgUnloadTime()));
			// 待叉区停留时长
			row.add(convertNum2Str(item.getAvgForkAreaStayTime()));

			// 包装库区停留时长
			row.add(convertNum2Str(item.getAvgPkgAreaStayTime()));
			// 中转库区停留时长
			row.add(convertNum2Str(item.getAvgTfrAreaTime()));
			// 派送库区停留时长
			row.add(convertNum2Str(item.getAvgDptAreaTime()));

			rowList.add(row);
		}

		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		String[] heads = { "外场", "卸车等待时长", "卸车时长", "待叉区停留时长", "包装库区停留时长",
				"中转库区停留时长", "派送库区停留时长" };
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

	private String convertNum2Str(BigDecimal num) {
		return num == null ? null : num.toString();
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
