package com.deppon.foss.module.transfer.oa.server.ws;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.oa.server.domain.SealModel;
import com.deppon.foss.module.transfer.oa.server.domain.SealResponseEntity;
/**
 * Foss传给OA的封签差错需求信息
 * @author 370210
 * @date 2016-10-31 14:31:11
 */
public class FossSealService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FossSealService.class);
	
	@Context
	private HttpServletResponse response;

	/**车辆任务明细Service**/
	private ITruckTaskService truckTaskService;
	/**配载单Service**/
	private IVehicleAssembleBillService vehicleAssembleBillService;
	/**交接单Service**/
	private IHandOverBillService handOverBillService;

	/**交接单**/
	private final static String HANDOVER = "HANDOVER";
	/**配载单**/
	private final static String VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	
	/**
	 * @Description: 上报封签差错所需foss信息
	 * @return: String
	 * @author: Xingmin , 2016-10-31 下午3:22:44
	 */
	@POST
	@Path("/getSealInfo/{handoverNo}")
	public String getSealInfo(@PathParam("handoverNo") String handoverNo ) {
		LOGGER.info("接收数据-交接单号：" + handoverNo);

		response.setCharacterEncoding("UTF-8");
		SealResponseEntity sealResponseEntity = new SealResponseEntity();
		
		SealModel sealModel = new SealModel();
		sealResponseEntity.setResult(sealModel);
		sealResponseEntity.setIsSuccess(String.valueOf(true));
		
		String responseStr = "";
		try {
			if (StringUtils.isEmpty(handoverNo)) {
				throw new RuntimeException("交接单号或配载单号不能为空!Sorry,The Arguments Can Not Be Empty!");
			}
			//获取车辆任务明细
			List<TruckTaskDetailEntity> taskDetails = truckTaskService.queryTruckTaskDetail(handoverNo);
			
			if (null != taskDetails && taskDetails.size() > 0) {
				TruckTaskDetailEntity truckTaskDetailEntity = taskDetails.get(0);
				
				/*
				 * 筛选有效的
				if (!truckTaskDetailEntity.getBillLevel().equals("1")) {
					throw new RuntimeException("单据无效!");
				}*/
				
				
				//获取单据类型（交接单、配载单）
				String billType = truckTaskDetailEntity.getBillType();
				
				if (StringUtils.isEmpty(billType)) {
					throw new RuntimeException("数据无效，未知单据类型!");
				}
				
				//交接单
				if (billType.toUpperCase().equals(HANDOVER)) {
					HandOverBillEntity handOverBill = handOverBillService.queryHandOverBillByNo(handoverNo);
					if (handOverBill != null) {
						sealModel.setTransferTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(handOverBill.getHandOverTime()));
					}
				}
				//配载单
				else if (billType.toUpperCase().equals(VEHICLEASSEMBLE)) {
					//获取配载单明细
					List<VehicleAssembleBillEntity> vehicleAssembleBillList = vehicleAssembleBillService.queryVehicleAssembleBillByNo(handoverNo);
					if (!CollectionUtils.isEmpty(vehicleAssembleBillList)) {
						sealModel.setTransferTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vehicleAssembleBillList.get(0).getCreateDate()));
					}
				}else {
					throw new RuntimeException("未知单据类型!");
				}
				
				sealModel.setWayBillNum(handoverNo);
				sealModel.setArriveDeptCode(truckTaskDetailEntity.getDestOrgCode());
				sealModel.setArriveDeptName(truckTaskDetailEntity.getDestOrgName());
				sealModel.setLicensePlateNumber(truckTaskDetailEntity.getVehicleNo());
				sealModel.setSetOutDeptCode(truckTaskDetailEntity.getOrigOrgCode());
				sealModel.setSetOutDeptName(truckTaskDetailEntity.getOrigOrgName());
			}else {
				throw new RuntimeException("未查到信息!");
			}
			responseStr = JSON.toJSONString(sealResponseEntity);
			
		} catch (Exception e) {
			sealResponseEntity.setIsSuccess(String.valueOf(false));
			sealResponseEntity.setMas(e.getMessage());
			responseStr = JSON.toJSONString(sealResponseEntity);
			
			e.printStackTrace();
		}
		LOGGER.info("返回数据：" + responseStr);
		return responseStr;
	}


	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setVehicleAssembleBillService(IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}


	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}



 
	
}
