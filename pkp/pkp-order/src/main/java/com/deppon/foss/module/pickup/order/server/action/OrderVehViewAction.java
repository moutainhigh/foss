package com.deppon.foss.module.pickup.order.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderVehViewService;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleViewSmallZoneDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.OrderVehVo;
import com.deppon.foss.util.CollectionUtils;

public class OrderVehViewAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(OrderVehViewAction.class);
	
	private IOrderVehViewService orderVehViewService;
	
	private OrderVehVo orderVehVo;
	/**
	 * 
	 * 根据条件查询出对应数据
	 * @author  045925-foss-yangbin
	 * @date 2014-4-19 上午8:42:39
	 */
	@JSON
	public String queryDriverRecords() {
		try {
			LOGGER.info("订单可视化，查询开始。。。。");
			DriverQueryDto driverQueryDto = new DriverQueryDto();
			driverQueryDto = orderVehVo.getQueryDto();
			if(driverQueryDto.getBigZoneCodes().size()==1&&driverQueryDto.getBigZoneCodes().get(0).equals("")) {
				driverQueryDto.setBigZoneCodes(null);
			}
			//根据大区编码查询下面的小区编码
			if(CollectionUtils.isNotEmpty(driverQueryDto.getBigZoneCodes())) {
				List<DriverQueryDto> smallZoneDtos  = orderVehViewService.querySmallZoneCodesByBigZoneCodes(driverQueryDto);
				if(CollectionUtils.isNotEmpty(smallZoneDtos)) {
					List<String> smallZoneCodes = new ArrayList<String>();
					for(DriverQueryDto dto : smallZoneDtos) {
						smallZoneCodes.add(dto.getSmallZoneCode());
					}
					driverQueryDto.setSmallZoneCodes(smallZoneCodes);
				}
			}
			//先判断订单号是否为空
			if(StringUtils.isNotEmpty(driverQueryDto.getOrderNo())){
				//根据运单号，以及该当前部门所属的顶级车队查询是否存在运单
				List<OrderVehViewSignDto> orderVehViewSignList = 
						orderVehViewService.queryOrderVehViewByCommon(driverQueryDto);
				if(CollectionUtils.isNotEmpty(orderVehViewSignList)){
					//将订单结果显示给前台
					orderVehVo.setOrderVehViewSignList(orderVehViewSignList);
				}else{
					throw new DispatchException("订单号不存在，或者此订单号不属于当前部门的顶级车队！");
				}
			}else{
				Long count = orderVehViewService.queryDriverByCommonCount(driverQueryDto);
				if(count > 0){
					this.totalCount = count;
					int start = this.getStart();
					int limit = this.getLimit();
					List<OwnTruckSignDto> ownTruckSignList = orderVehViewService.queryDriverByCommon(driverQueryDto, start, limit);
					orderVehVo.setOwnTruckSignList(ownTruckSignList);
					
					List<VehicleViewSmallZoneDto> vehSmallZones = orderVehViewService.queryDriverByCommonAll(driverQueryDto, 0, count.intValue());
					if(CollectionUtils.isNotEmpty(vehSmallZones)
							&& vehSmallZones.size() > 0){
						orderVehVo.setVehSmallZones(vehSmallZones);
					}
					
					List<OrderVehViewSignDto> orderVehViewSignList = 
							orderVehViewService.queryOrderVehViewByCommon(driverQueryDto);
					if(CollectionUtils.isNotEmpty(orderVehViewSignList)){
						orderVehVo.setOrderVehViewSignList(orderVehViewSignList);
					}
				}
			}
			LOGGER.info("订单可视化，查询结束。。。。");
			//返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 根据条件查询出对应数据
	 * @author  045925-foss-yangbin
	 * @date 2014-4-19 上午8:42:39
	 */
	@JSON
	public String openDriver() {
		try {
			ExpressWorkerStatusDto dto = new ExpressWorkerStatusDto();
			List<String> vehicleNoList = orderVehVo.getVehicleNos();
			if(CollectionUtils.isEmpty(vehicleNoList)){
				return returnError("所选择的车牌号集合为空！");
			}
			dto.setVehicleNoList(vehicleNoList);
			orderVehViewService.openExpressWorker(dto);
			return super.returnSuccess();
		} catch (DispatchException e) {
			LOGGER.info(e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 根据条件查询出对应数据
	 * @author  045925-foss-yangbin
	 * @date 2014-4-19 上午8:42:39
	 */
	@JSON
	public String stopDriver() {
		try {	
			ExpressWorkerStatusDto dto = new ExpressWorkerStatusDto();
			List<String> vehicleNoList = orderVehVo.getVehicleNos();
			if(CollectionUtils.isEmpty(vehicleNoList)){
				return returnError("所选择的车牌号集合为空！");
			}
			dto.setVehicleNoList(vehicleNoList);
			orderVehViewService.stopExrpessWorker(dto);
			//返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			LOGGER.info(e.getMessage());
			return returnError(e);
		}
	}

	public void setOrderVehViewService(IOrderVehViewService orderVehViewService) {
		this.orderVehViewService = orderVehViewService;
	}

	public OrderVehVo getOrderVehVo() {
		return orderVehVo;
	}

	public void setOrderVehVo(OrderVehVo orderVehVo) {
		this.orderVehVo = orderVehVo;
	}
	
}
