package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
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
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.ILineCargoService;
import com.deppon.foss.module.transfer.platform.api.shared.define.LineCargoConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.LineCargoVo;

public class LineCargoAction extends AbstractAction {

	private static final long serialVersionUID = 6668297362652335437L;

	private ILineCargoService lineCargoService;

	private LineCargoVo lineCargoVo = new LineCargoVo();

	private InputStream excelStream;

	private String downloadFileName;

	public LineCargoVo getLineCargoVo() {
		return lineCargoVo;
	}

	public void setLineCargoVo(LineCargoVo lineCargoVo) {
		this.lineCargoVo = lineCargoVo;
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

	public void setLineCargoService(ILineCargoService lineCargoService) {
		this.lineCargoService = lineCargoService;
	}

	/**
	 * @desc 入口方法，获取当前部门所属外场
	 * @return
	 * @date 2014年11月19日 上午10:18:41
	 * @author Ouyang
	 */
	public String lineCargoIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();

		OrgAdministrativeInfoEntity transferCenter = lineCargoService
				.queryTfrCtrBySubCode(currentDeptCode);

		String transferCenterCode = null;
		String transferCenterName = null;
		if (transferCenter != null) {
			transferCenterCode = transferCenter.getCode();
			transferCenterName = transferCenter.getName();
		}
		lineCargoVo.setTransferCenterCode(transferCenterCode);
		lineCargoVo.setTransferCenterName(transferCenterName);

		return SUCCESS;
	}

	/**
	 * @desc 查询3级产品
	 * @return
	 * @date 2014年11月19日 上午10:18:58
	 * @author Ouyang
	 */
	public String queryProducts() {
		List<BaseDataDictDto> products = lineCargoService.queryProducts();
		lineCargoVo.setProducts(products);
		return SUCCESS;
	}

	/**
	 * @desc 查询线路货量
	 * @return
	 * @date 2014年11月19日 上午10:19:48
	 * @author Ouyang
	 */
	public String queryLineCargo() {
		LineCargoQcDto qcDto = lineCargoVo.getLineCargoQcDto();

		// 总量
		LineCargoTotalDto lineCargoTotal = new LineCargoTotalDto();

		// 总票数、总件数、总重量、总体积
		int totalVote = 0;
		int totalQty = 0;
		BigDecimal totalWeight = BigDecimal.ZERO;
		BigDecimal totalVolume = BigDecimal.ZERO;

		List<LineCargoDto> lineCargos = lineCargoService.queryLineCargo(qcDto, null);

		for (LineCargoDto cargo : lineCargos) {
			totalQty += cargo.getCurrentQty();
			totalVote++;

			BigDecimal currentVolume = cargo.getCurrentVolume();
			BigDecimal currentWeight = cargo.getCurrentWeight();

			if (currentVolume != null) {
				totalVolume = totalVolume.add(currentVolume);
			}
			if (currentWeight != null) {
				totalWeight = totalWeight.add(currentWeight);
			}
		}

		// 设置总票数、总件数、总重量、总体积
		lineCargoTotal.setTotalQty(totalQty);
		lineCargoTotal.setTotalVote(totalVote);
		lineCargoTotal.setTotalVolume(totalVolume);
		lineCargoTotal.setTotalWeight(totalWeight);

		// 线路货量、总量赋值
		lineCargoVo.setLineCargoDtos(lineCargos);
		lineCargoVo.setLineCargoTotalDto(lineCargoTotal);

		return SUCCESS;
	}

	/**
	 * @desc 导出
	 * @return
	 * @date 2014年11月19日 上午10:19:59
	 * @author Ouyang
	 */
	public String exportLineCargo() {
		LineCargoQcDto parameter = lineCargoVo.getLineCargoQcDto();

		ExportResource exportResource = new ExportResource();
		try {
			exportResource = exportResource(parameter);
		} catch (BusinessException e) {
			return returnError(e);
		}

		String fileName = "线路货量.xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("线路货量");

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
	private ExportResource exportResource(LineCargoQcDto parameter) {

		List<List<String>> rowList = new ArrayList<List<String>>();

		List<LineCargoDto> lineCargoDtos = lineCargoService.queryLineCargo(
				parameter, null);
		
		for (LineCargoDto item : lineCargoDtos) {
			List<String> row = new ArrayList<String>();
			// 下一部门
			row.add(item.getDestDeptName());
			// 运单号
			row.add(item.getWaybillNo());
			// 货物状态
			row.add(item.getStatusName());
			// 运单件数
			row.add(String.valueOf(item.getWaybillQty()));
			// 当前件数
			row.add(String.valueOf(item.getCurrentQty()));
			// 重量
			row.add(String.valueOf(item.getCurrentWeight()));
			// 体积
			row.add(String.valueOf(item.getCurrentVolume()));
			// 产品名称
			row.add(item.getProductName());
			// 开单时间
			Date staTime = item.getBillTime();
			row.add(String.format("%1$tF %2$tT", staTime, staTime));
			// 车次号
			row.add(item.getVehicleassembleNo());
			// 车牌号
			row.add(item.getVehicleNo());
			
			rowList.add(row);
		}
		
		if (CollectionUtils.isEmpty(rowList)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(LineCargoConstants.HEADER);
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

	/**
	 * @desc 查询运单流水号
	 * @return
	 * @date 2014年11月19日 上午10:20:10
	 * @author Ouyang
	 */
	public String querySerialNos() {
		LineCargoQcDto parameter = lineCargoVo.getLineCargoQcDto();
		List<LineCargoSerialNoDto> serialNos = lineCargoService
				.querySerialNos(parameter);
		lineCargoVo.setSerialNoDtos(serialNos);
		return SUCCESS;
	}
}
