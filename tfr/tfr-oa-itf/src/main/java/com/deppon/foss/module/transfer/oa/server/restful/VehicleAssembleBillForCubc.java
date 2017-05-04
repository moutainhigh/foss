package com.deppon.foss.module.transfer.oa.server.restful;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.oa.server.shared.response.Response;
@RequestMapping("/vehicleAssembleBillForCubc")
public class VehicleAssembleBillForCubc {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleAssembleBillForCubc.class);
	
	@Resource
	private IVehicleAssembleBillService vehicleAssembleBillService;
	/**
	 * 给CUBC接口
	 * 根据运单号   查询配载单数;
	 * 返回值是:Y or N;
	 * @param waybillNo
	 * @return
	 */
	@RequestMapping(value = "/queryIsVehicleassembleByNo", method = RequestMethod.POST)
	public @ResponseBody Response queryIsVehicleassembleByNo(@RequestBody String waybillNo){
		LOGGER.error("CUBC查询配载单数,运单号 ===" + waybillNo);
		
		Response response = new Response();
		String hasTrack = ""; 
		try {
		LOGGER.error("CUBC查询配载单数开始");
		 hasTrack= vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
		 response.setHasTrack(hasTrack);
		} catch (Exception e) {
			LOGGER.error("CUBC查询配载单数发生异常;运单号   :" + waybillNo );
			response.setErrmsg("查询配载货物轨迹是出现异常："+e);
		}
		LOGGER.error("CUBC查询配载单数结束");
		return response;
	}
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}
}
