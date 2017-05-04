package com.deppon.foss.module.transfer.oa.server.restful;
import java.util.Date;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.deppon.foss.module.transfer.common.api.server.service.IFirstTransferCenterHandoverTimeService;
import com.deppon.foss.module.transfer.oa.server.shared.response.FirstTransferCenterTimeResponse;
@Controller
@RequestMapping("/firstTransferCenterTime")
public class FirstTransferCenterHandoverTimeForCubc {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstTransferCenterHandoverTimeForCubc.class);
	
	
	@Resource
	private IFirstTransferCenterHandoverTimeService firstTransferCenterHandoverTimeService;
	/**
	 * 给CUBC接口
	 * 根据运单号   查询第一外场交接时间;
	 * 返回值是:第一外场交接时间;
	 * @param waybillNo
	 * @return
	 */
	 
	@RequestMapping(value = "/getFirstTransferCenterTime", method = RequestMethod.POST)
	public @ResponseBody FirstTransferCenterTimeResponse getFirstTransferCenterHandoverTime(@RequestBody WaybillEntityRequest waybillEntityRequest){
		LOGGER.error("CUBC查询第一外场交接时间,运单号 ===" + waybillEntityRequest.getWaybillNo());
		
		FirstTransferCenterTimeResponse response = new FirstTransferCenterTimeResponse();
		Date firstTranserCenterTime; 
		try {
		LOGGER.error("CUBC查询第一外场交接时间开始");
		firstTranserCenterTime = firstTransferCenterHandoverTimeService.getFirstTransferCenterHandoverTime(waybillEntityRequest.getWaybillNo());
		response.setFirstTranserCenterTime(firstTranserCenterTime);
		} catch (Exception e) {
			LOGGER.error("CUBC查询第一外场交接时间发生异常;运单号   :" + waybillEntityRequest.getWaybillNo());
			response.setErrmsg("CUBC查询第一外场交接时间出现异常："+e.getMessage());
		}
		LOGGER.error("CUBC查询第一外场交接时间结束");
		return response;
	}
	public void setFirstTransferCenterHandoverTimeService(
			IFirstTransferCenterHandoverTimeService firstTransferCenterHandoverTimeService) {
		this.firstTransferCenterHandoverTimeService = firstTransferCenterHandoverTimeService;
	}
	
	static class WaybillEntityRequest{
		private String waybillNo;

		public String getWaybillNo() {
			return waybillNo;
		}

		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
	}
}
