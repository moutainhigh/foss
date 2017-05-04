package com.deppon.foss.module.pickup.order.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.ExpressWorerStatusConditionVo;
import com.deppon.foss.util.CollectionUtils;

public class CourierReportAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(CourierReportAction.class);
	private ExpressWorerStatusConditionVo  expressWorerStatusConditionVo = new ExpressWorerStatusConditionVo();
	private ICourierReportService courierReportService;
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;
	/**
	 * 
	 * 根据条件查询特殊地址
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-19 上午8:42:39
	 */
	@JSON
	public String queryCourierReords() {
		try {
			CourierQueryDto courierQueryDto = new CourierQueryDto();
			courierQueryDto.setBigZoneCodes(expressWorerStatusConditionVo.getBigRegionCode());
			courierQueryDto.setSmallZoneCodes(expressWorerStatusConditionVo.getSmallRegionCode());
			courierQueryDto.setCourierCode(expressWorerStatusConditionVo.getExpressWorkerCode());
			courierQueryDto.setBeginTime(expressWorerStatusConditionVo.getStartDate());
			courierQueryDto.setEndTime(expressWorerStatusConditionVo.getEndDate());
			Long total = courierReportService.queryCourierByCommonCount(courierQueryDto);
			if(total > 0){
				List<CourierReportEntity> list = courierReportService.queryCourierByCommon(courierQueryDto, start, limit);
				expressWorerStatusConditionVo.setCourierReportList(list);
			}
			this.totalCount = total;
			//返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 导出记录
	 * @author 043258-foss-zhaobin
	 * @date 2014-4-19 下午3:08:54
	 */
	@JSON
	public String exportCourierReord() {
		try {
			CourierQueryDto courierQueryDto = new CourierQueryDto();
			courierQueryDto.setBigZoneCodes(expressWorerStatusConditionVo.getBigRegionCode());
			courierQueryDto.setSmallZoneCodes(expressWorerStatusConditionVo.getSmallRegionCode());
			courierQueryDto.setCourierCode(expressWorerStatusConditionVo.getExpressWorkerCode());
			courierQueryDto.setBeginTime(expressWorerStatusConditionVo.getStartDate());
			courierQueryDto.setEndTime(expressWorerStatusConditionVo.getEndDate());
			List<CourierReportEntity> list = courierReportService.queryCourierReord(courierQueryDto);
			try {
				// 导出文件名称：
				String exportExeclName = "快递员工作状态信息";
				this.setExeclName(new String(exportExeclName.getBytes("GBK"),"ISO8859_1"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(list);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName("快递员工作状态");
			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return returnSuccess();
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 设置excel头
	 * @author 043258-foss-zxy
	 * @date 2014-7-5 下午3:08:54 
	 */
    private ExportResource exportRfdResource (List<CourierReportEntity> list){
    ExportResource sheet = new ExportResource();
	//设置表单表头
	sheet.setHeads(new String[]{"姓名","快递员工号","收派大区","收派小区","属性","已接收订单"	,"已完成订单","无订单开单","应派送订单","已派送订单"});
	// 处理返回的结果集
	List<List<String>> resultList = this.exportWorkerCompelet(list);
	//设置表单数据
	sheet.setRowList(resultList);
	
	return sheet;
	}
    
    /**
     * 导出
     * @param expressWorkerCompleteDtoList
     * @return
     * @author 043258-foss-zxy
	 * @date 2014-7-5 下午3:08:54 
     */
    private List<List<String>> exportWorkerCompelet(List<CourierReportEntity> expressWorkerCompleteDtoList) {

		String[] columns = { "courierName", "courierCode", "bigRegionName",
				"smallRegionName", "courierType", "receiveOrderTotal", "receiveWaybillOrderTotal",
				"noOrderWaybillTotal", "signWaybillTotal", "deliverWaybillTotal" };
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(expressWorkerCompleteDtoList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (CourierReportEntity entity : expressWorkerCompleteDtoList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					if (StringUtils.equals(column, "courierName")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "courierCode")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());

					} else if (StringUtils.equals(column, "bigRegionName")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "smallRegionName")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "courierType")) {
						cellValue = (fieldValue == null ? "" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "receiveOrderTotal")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "receiveWaybillOrderTotal")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "noOrderWaybillTotal")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "signWaybillTotal")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					} else if (StringUtils.equals(column, "deliverWaybillTotal")) {
						cellValue = (fieldValue == null ? "0" : fieldValue
								.toString());
					}
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}
		return rowList;
	}

	public ExpressWorerStatusConditionVo getExpressWorerStatusConditionVo() {
		return expressWorerStatusConditionVo;
	}

	public void setExpressWorerStatusConditionVo(
			ExpressWorerStatusConditionVo expressWorerStatusConditionVo) {
		this.expressWorerStatusConditionVo = expressWorerStatusConditionVo;
	}

//	public ICourierReportService getCourierReportService() {
//		return courierReportService;
//	}

	public void setCourierReportService(ICourierReportService courierReportService) {
		this.courierReportService = courierReportService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExeclName() {
		return execlName;
	}

	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}
	
}
