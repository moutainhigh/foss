/**   
 * @Title: GoodsAreaDensityAction.java 
 * @Package com.deppon.foss.module.transfer.platform.server.action 
 * @Description: 库区密度查询
 * @author shiwei shiwei@outlook.com
 * @date 2014年3月12日 上午11:39:49 
 * @version V1.0   
 */
package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IGoodsAreaDensityService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.GoodsAreaDensityVo;

/**
 * @ClassName: GoodsAreaDensityAction
 * @Description: 库区密度查询
 * @author shiwei shiwei@outlook.com
 * @date 2014年3月12日 上午11:39:49
 * 
 */
public class GoodsAreaDensityAction extends AbstractAction {

	private static final long serialVersionUID = 1987465163542357L;

	private IGoodsAreaDensityService goodsAreaDensityService;

	private GoodsAreaDensityVo goodsAreaDensityVo = new GoodsAreaDensityVo();

	/**
	 * 导出Excel文件流
	 */
	private InputStream inputStream;

	/**
	 * 导出Excel文件名
	 */
	private String downloadFileName;

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

	public GoodsAreaDensityVo getGoodsAreaDensityVo() {
		return goodsAreaDensityVo;
	}

	public void setGoodsAreaDensityVo(GoodsAreaDensityVo goodsAreaDensityVo) {
		this.goodsAreaDensityVo = goodsAreaDensityVo;
	}

	public void setGoodsAreaDensityService(
			IGoodsAreaDensityService goodsAreaDensityService) {
		this.goodsAreaDensityService = goodsAreaDensityService;
	}

	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}

	/**
	 * @Title: goodsAreaDensityIndex
	 * @Description: 库区密度查询
	 * @author shiwei shiwei@outlook.com
	 * @date 2014年3月12日 下午2:42:58
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String goodsAreaDensityIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = goodsAreaDensityService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				goodsAreaDensityVo.setParentTfrCtrCode(transferCenter
						.get("code"));
				goodsAreaDensityVo.setParentTfrCtrName(transferCenter
						.get("name"));
			} else {
				goodsAreaDensityVo.setParentTfrCtrCode(null);
				goodsAreaDensityVo.setParentTfrCtrName(null);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * @Title: goodsAreaDensityDetailIndex
	 * @Description: 打开查询转运场库区密度明细页面
	 * @author shiwei shiwei@outlook.com
	 * @date 2014年3月17日 上午9:09:03
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String goodsAreaDensityDetailIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = goodsAreaDensityService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				goodsAreaDensityVo.setParentTfrCtrCode(transferCenter
						.get("code"));
				goodsAreaDensityVo.setParentTfrCtrName(transferCenter
						.get("name"));
			} else {
				goodsAreaDensityVo.setParentTfrCtrCode(null);
				goodsAreaDensityVo.setParentTfrCtrName(null);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return SUCCESS;
	}

	/**
	 * 分页货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingGoodsAreaDensity4Sum() {
		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();
		List<GoodsAreaDensity4SumDto> sumDtos;
		try {
			sumDtos = goodsAreaDensityService.selectGoodsAreaDensity4Sum(dto,
					super.getStart(), super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntGoodsAreaDensity4Sum(dto);

		goodsAreaDensityVo.setGoodsAreaDensity4SumDtos(sumDtos);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 各外场各货区整点货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectGoodsAreaDensity4Timely() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectGoodsAreaDensity4Timely(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		return SUCCESS;
	}

	/**
	 * 分页各外场各货区整点货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingGoodsAreaDensity4Timely() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectGoodsAreaDensity4Timely(dto, super.getStart(),
							super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntGoodsAreaDensity4Timely(dto);

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 各外场各货区各天平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectTfrCtrGoodsAreaAvgDensity4Daily() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrGoodsAreaAvgDensity4Daily(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		return SUCCESS;
	}

	/**
	 * 分页各外场各货区各天平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingTfrCtrGoodsAreaAvgDensity4Daily() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrGoodsAreaAvgDensity4Daily(dto,
							super.getStart(), super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntTfrCtrGoodsAreaAvgDensity4Daily(dto);

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 各外场各天平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectTfrCtrAvgDensity4Daily() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrAvgDensity4Daily(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		return SUCCESS;
	}

	/**
	 * 分页各外场各天平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingTfrCtrAvgDensity4Daily() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrAvgDensity4Daily(dto, super.getStart(),
							super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntTfrCtrAvgDensity4Daily(dto);

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 各外场各货区各月平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectTfrCtrGoodsAreaAvgDensity4Monthly() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrGoodsAreaAvgDensity4Monthly(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		return SUCCESS;
	}

	/**
	 * 分页各外场各货区各月平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingTfrCtrGoodsAreaAvgDensity4Monthly() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrGoodsAreaAvgDensity4Monthly(dto,
							super.getStart(), super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntTfrCtrGoodsAreaAvgDensity4Monthly(dto);

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 各外场各月平均货区密度查询F
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectTfrCtrAvgDensity4Monthly() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrAvgDensity4Monthly(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		return SUCCESS;
	}

	/**
	 * 分页各外场各月平均货区密度查询
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	public String selectPagingTfrCtrAvgDensity4Monthly() {

		GoodsAreaDensityQcDto dto = goodsAreaDensityVo
				.getGoodsAreaDensityQcDto();

		List<GoodsAreaDensityEntity> goodsAreaDensityEntities;

		try {
			goodsAreaDensityEntities = goodsAreaDensityService
					.selectTfrCtrAvgDensity4Monthly(dto, super.getStart(),
							super.getLimit());
		} catch (BusinessException e) {
			return returnError(e);
		}

		Long totalCount = goodsAreaDensityService
				.selectCntTfrCtrAvgDensity4Monthly(dto);

		goodsAreaDensityVo
				.setGoodsAreaDensityEntities(goodsAreaDensityEntities);

		super.setTotalCount(totalCount);

		return SUCCESS;
	}

	/**
	 * 货区密度导出
	 * @return
	 * @date 2014-3-18
	 * @author Ouyang
	 */
	public String exportGoodsAreaDensity4Sum() {

		try {
			// 查询条件
			GoodsAreaDensityQcDto dto = goodsAreaDensityVo
					.getGoodsAreaDensityQcDto();

			ExportResource exportResource = goodsAreaDensityService
					.exportGoodsAreaDensity4Sum(dto);

			// 导出文件名
			downloadFileName = encodeFileName(PlatformConstants.EXCEL_FILE_NAME_GAD);

			ExportSetting exportSetting = new ExportSetting();
			// sheet名
			exportSetting.setSheetName(String.format("%1$tF",
					dto.getStatisticDate()));

			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出文件
			inputStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return SUCCESS;
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", "");
		}

	}

	/**
	 * 各外场各货区整点货区密度导出
	 * @return
	 * @date 2014-3-18
	 * @author Ouyang
	 */
	public String exportGoodsAreaDensity4Timely() {

		try {
			// 查询条件
			GoodsAreaDensityQcDto dto = goodsAreaDensityVo
					.getGoodsAreaDensityQcDto();

			ExportResource exportResource = goodsAreaDensityService
					.exportGoodsAreaDensity4Timely(dto);

			// 外场名称
			String transferCenterName = dto.getTransferCenterName();
			// 货区名称
			String goodsAreaName = dto.getGoodsAreaName();

			// 查询开始日期
			String beginStatisticDate = String.format("%1$tF",
					dto.getBeginStatisticDate());
			// 查询结束日期
			String endStatisticDate = String.format("%1$tF",
					dto.getEndStatisticDate());
			// 查询时间点
			String statisticTimePoint = dto.getStatisticTimePoint();

			String separator = "_";
			StringBuilder fileName = new StringBuilder(transferCenterName);
			if (!StringUtils.isEmpty(goodsAreaName)) {
				fileName.append(separator);
				fileName.append(goodsAreaName);
			}
			fileName.append(separator);
			fileName.append(beginStatisticDate);
			fileName.append("~");
			fileName.append(endStatisticDate);
			fileName.append(separator);
			fileName.append(statisticTimePoint);
			fileName.append("点");
			fileName.append(".xls");

			// 导出文件名
			downloadFileName = encodeFileName(fileName.toString());

			ExportSetting exportSetting = new ExportSetting();
			// sheet名
			exportSetting.setSheetName(statisticTimePoint);

			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出文件
			inputStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return SUCCESS;
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", "");
		}

	}

	/**
	 * 各外场各货区各月平均货区密度导出
	 * @return
	 * @date 2014-3-18
	 * @author Ouyang
	 */
	public String exportTfrCtrGoodsAreaAvgDensity4Monthly() {
		try {
			// 查询条件
			GoodsAreaDensityQcDto dto = goodsAreaDensityVo
					.getGoodsAreaDensityQcDto();

			ExportResource exportResource = goodsAreaDensityService
					.exportTfrCtrGoodsAreaAvgDensity4Monthly(dto);

			// 外场名称
			String transferCenterName = dto.getTransferCenterName();
			// 货区名称
			String goodsAreaName = dto.getGoodsAreaName();
			// 查询结束月份
			String beginStatisticMonth = dto.getBeginStatisticMonth();
			// 查询开始月份
			String endStatisticMonth = dto.getEndStatisticMonth();

			String separator = "_";
			StringBuilder fileName = new StringBuilder(transferCenterName);
			if (!StringUtils.isEmpty(goodsAreaName)) {
				fileName.append(separator);
				fileName.append(goodsAreaName);
			}
			fileName.append(separator);
			fileName.append(beginStatisticMonth);
			fileName.append("~");
			fileName.append(endStatisticMonth);
			fileName.append(".xls");

			// 导出文件名
			downloadFileName = encodeFileName(fileName.toString());

			ExportSetting exportSetting = new ExportSetting();
			/*// sheet名
			exportSetting.setSheetName(String.format("%1$tF",
					dto.getStatisticDate()));*/

			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出文件
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
