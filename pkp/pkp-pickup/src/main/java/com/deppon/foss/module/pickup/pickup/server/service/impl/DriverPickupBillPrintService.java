package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.pickup.pickup.api.server.dao.IDriverPickupBillPrintDao;
import com.deppon.foss.module.pickup.pickup.api.server.service.IDriverPickupBillPrintService;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;

/**
 * 接送货司机接货单号打印Service
 * 
 * @author
 *
 */
public class DriverPickupBillPrintService implements
		IDriverPickupBillPrintService {
	
	/**
	 * Logger 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverPickupBillPrintService.class);
	
	/**
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	

	// 司机接货单号Dao
	private IDriverPickupBillPrintDao driverPickupBillPrintDao;
	/**
	 * 查询司机接货单号
	 * 
	 * @param dto
	 * @return
	 */
	@Override
	public InputStream queryDriverPickupBillPrint(
			DriverPickupBillPrintDto dto) {
		LOGGER.info("LIST ========="+ReflectionToStringBuilder.toString(dto));
		List<RtDriverPickupBillPrintDto> rtDtoList;
		//结果
		rtDtoList = driverPickupBillPrintDao.queryDriverPickupBillPrintList(dto);
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (int i = 0; i < rtDtoList.size(); i++) {
			
			
			List<String> columnList = new ArrayList<String>();
			// 序号
			columnList.add(i+1+"");
			// 开单时间
			columnList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rtDtoList.get(i).getPickupTime()));
			// 运单号
			columnList.add(rtDtoList.get(i).getWaybillNo());
			// 司机工号
			columnList.add(rtDtoList.get(i).getDriverCode());
			// 司机姓名
			columnList.add(rtDtoList.get(i).getDriverName());
			// 营业部
			columnList.add(rtDtoList.get(i).getCreateOrgName());
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"序号","开单时间","运单号","司机工号","司机姓名","营业部"};
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("司机接货单号清单");
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}


	/**
	 * 查询司机接货单号 分页
	 * 
	 * @param dto
	 * @return
	 */
	@Override
	public List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrint(
			DriverPickupBillPrintDto driverPickupBillPrintDto, int start,
			int limit) {
		LOGGER.info("司机接货单号查询:"+ReflectionToStringBuilder.toString(driverPickupBillPrintDto));
		 return driverPickupBillPrintDao.queryDriverPickupBillPrint(
				driverPickupBillPrintDto, start, limit);
	}

	/**
	 * 查询司机接货单号 Total
	 * 
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryDriverPickupBillPrintTotal(
			DriverPickupBillPrintDto driverPickupBillPrintDto) {
		LOGGER.info("司机接货单号总数查询:"+ReflectionToStringBuilder.toString(driverPickupBillPrintDto));
		return driverPickupBillPrintDao
				.queryDriverPickupBillPrintTotal(driverPickupBillPrintDto);
	}

	public void setDriverPickupBillPrintDao(
			IDriverPickupBillPrintDao driverPickupBillPrintDao) {
		this.driverPickupBillPrintDao = driverPickupBillPrintDao;
	}
	/**
	 * 查询司机接货单号 Total
	 * @date 2016年5月28日
	 * @author 306486
	 * @param dto
	 * @return
	 */

	@Override
	public List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrintNoPage(
			DriverPickupBillPrintDto driverPickupBillPrintDto) {
		LOGGER.info("司机接货单号查询:"+ReflectionToStringBuilder.toString(driverPickupBillPrintDto));
		return driverPickupBillPrintDao.queryDriverPickupBillPrintList(driverPickupBillPrintDto);
	}

}