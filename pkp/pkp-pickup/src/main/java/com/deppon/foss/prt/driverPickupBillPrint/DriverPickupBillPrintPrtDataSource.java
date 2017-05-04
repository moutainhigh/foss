package com.deppon.foss.prt.driverPickupBillPrint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.pickup.pickup.api.server.service.IDriverPickupBillPrintService;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


public class DriverPickupBillPrintPrtDataSource implements JasperDataSource {

	// 注入日志
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(DriverPickupBillPrintPrtDataSource.class);

	
	private List<RtDriverPickupBillPrintDto> RtDto;

	/**
	 * 司机接货单号service
	 */
	private IDriverPickupBillPrintService DPBPService;
	

	public IDriverPickupBillPrintService getDriverPickupBillPrintService() {
		return DPBPService;
	}

	public void setDriverPickupBillPrintService(
			IDriverPickupBillPrintService driverPickupBillPrintService) {
		this.DPBPService = driverPickupBillPrintService;
	}

	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		logger.info("开始获取司机接货数据");
		// 声明打印值map
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (jasperContext != null && jasperContext.getParamMap() != null) {

			
			IDriverPickupBillPrintService dPBPService = (IDriverPickupBillPrintService) jasperContext.getApplicationContext().getBean("driverPickupBillPrintService");
			DriverPickupBillPrintDto dto = new DriverPickupBillPrintDto();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String startTime = (String) jasperContext.get("startTime");
			if (!StringUtils.isBlank(startTime)) {
				Date beginTime = sdf.parse(startTime);
				dto.setPickupTimeBegin(beginTime);
				parameter.put("startTime",beginTime);
			}
			
			// 获得结束日期
			String endTime = (String) jasperContext.get("endTime");
			if (!StringUtils.isBlank(endTime)) {
				Date lastTime = sdf.parse(endTime);
				dto.setPickupTimeEnd(lastTime);
				parameter.put("endTime",lastTime);
				
			}
			String waybillNo = (String) jasperContext.get("waybillNo");
			if (!StringUtils.isBlank(waybillNo)) {
				dto.setWaybillNo(waybillNo);
				parameter.put("waybillNo",waybillNo);
			}
			String driverCode = (String) jasperContext.get("driverCode");
			if (!StringUtils.isBlank(driverCode)) {
				dto.setDriverCode(driverCode);
				parameter.put("driverCode",driverCode);
			}
			String driverName = (String) jasperContext.get("driverName");
			if (!StringUtils.isBlank(driverName)) {
				dto.setDriverName(driverName);
				parameter.put("driverName",driverName);
			}
			String orgCode = (String) jasperContext.get("orgCode");
			if (!StringUtils.isBlank(orgCode)) {
				dto.setOrgCode(orgCode);
				parameter.put("orgCode",orgCode);
			}
			//查询参数集合
			RtDto = dPBPService.queryDriverPickupBillPrintNoPage(dto);
		}
		return parameter;
	}

	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		logger.info("开始填充司机接货数据");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询到的明细数据不为空时
		if (RtDto != null
				&& CollectionUtils.isNotEmpty(RtDto)) {

			for (int i = 0; i < RtDto.size(); i++) {
				RtDriverPickupBillPrintDto rtDto = RtDto.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				if (rtDto != null) {
					// 开单时间
					map.put("pickupTime", rtDto.getPickupTime());
					// 运单号
					map.put("waybillNo", rtDto.getWaybillNo());
					// 司机工号
					map.put("driverCode", rtDto.getDriverCode());
					// 司机姓名
					map.put("driverName", rtDto.getDriverName());
					// 开单组织
					map.put("createOrgName", rtDto.getCreateOrgName());

					list.add(map);
				}
			}
		}
		return list;
	}

}
