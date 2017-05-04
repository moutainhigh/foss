package com.deppon.foss.module.transfer.load.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IvehiclEmptyBillService;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.VehiclEmptyBillVo;
public class VehiclEmptyBillAction extends AbstractAction {
	
	private VehiclEmptyBillVo vehiclEmptyBillVo=new VehiclEmptyBillVo();
	private IvehiclEmptyBillService  vehiclEmptyBillAndService;
	private static final long serialVersionUID = 1L;
	/**
	 * 空驶单查询
	 * @author zhangpeng 271297
	 * @date 2015-09-25 下午14:53
	 */
	@JSON
	public String queryvehiclEmptyBill(){
		//获取查询条件
		try{
	    VehiclEmptyBillDto vehiclEmDto=vehiclEmptyBillVo.getVehiclEmptyBillDto();
		List<VehiclEmptyBillDto> queryvehiclEmptyBill=vehiclEmptyBillAndService.queryVehicleEmptyBill(vehiclEmDto, limit, start);
		vehiclEmptyBillVo.setQueryvehiclEmptyBill(queryvehiclEmptyBill);
		Long totalCount = vehiclEmptyBillAndService.queryVehiclEmptyBillCount(vehiclEmDto);	
		this.setTotalCount(totalCount);
		return returnSuccess();
		//返回success
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}	
	}
	
	/**
	 * 根据交接单号作废空驶单
	 * @author 045923-foss-zhangpeng
	 * @date 2012-10-26 上午11:06:39
	 */
	@JSON
	public String cancelVehiclEmptyBillByVehicleNo(){
		//获取传入的交接单号
		 VehiclEmptyBillDto vehiclEmDto=vehiclEmptyBillVo.getVehiclEmptyBillDto();
		 String vehicleEmpNo=vehiclEmDto.getVehiclEmptyBillNo();
		 try{
			 //sonar 311396 返回值未使用 2016年12月20日16:04:28
			 /*int a= */
			 vehiclEmptyBillAndService.deleteTruckTaskByVehiclEmptyBill(vehicleEmpNo);
		}catch(BusinessException e){
			//返回异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	
	/**
	 * 保存空驶单
	 * @author 045923-foss-shiwei
	 * @date 2013-7-30 下午3:27:35
	 */
	@JSON
	public String addvehiclEmptyBill(){
		 VehiclEmptyBillDto vehiclEmDto=vehiclEmptyBillVo.getVehiclEmptyBillDto();
		 String vehicleNo=vehiclEmDto.getVehicleNo();
	     Long list=vehiclEmptyBillAndService.searchStatusByVehicleNo(vehicleNo);
	     if(list==1){
		 return returnError(new TfrBusinessException("空驶车辆在途，未出发不可新增","空驶车辆在途，未出发不可新增"));
	     } 
		 try{
				//保存时重新生成空驶单号
			 String vehiclEmptyBillNo=vehiclEmptyBillAndService.saveVehiclEmptyBill(vehiclEmDto);
				//生成的交接单号返回前台
			 vehiclEmptyBillVo.setVehiclEmptyBillNo(vehiclEmptyBillNo);
			
			}catch(BusinessException e){
				//返回业务异常信息
				return this.returnError(e);
			}
			//返回success
			return returnSuccess();
    	}
	
	
	/**
	 * 校验车牌号状态是否未出发，在途
	 * @author zhangpeng
	 * @date 2015-10-19 下午3:27:35
	 */
	@JSON
	public String CheckVehicleStatusIsArrive(){
		VehiclEmptyBillDto vehiclEmDto=vehiclEmptyBillVo.getVehiclEmptyBillDto();
		String vehicleNo=vehiclEmDto.getVehicleNo();
		Long list=vehiclEmptyBillAndService.searchStatusByVehicleNo(vehicleNo);
        if(list!=0){
	         return returnError(new TfrBusinessException("空驶车辆在途，未出发不可新增","空驶车辆在途，未出发不可新增"));
           }
		   return returnSuccess();
		
	}
	
	/**
	 * 校验空驶单号出发，在途
	 * @author zhangpeng
	 * @date 2015-10-19 下午3:27:35
	 */
	@JSON
	public String CheckVehicleStatusIsDepart(){
		VehiclEmptyBillDto vehiclEmDto=vehiclEmptyBillVo.getVehiclEmptyBillDto();
		String vehicleEmpNo=vehiclEmDto.getVehiclEmptyBillNo();
		Long list=vehiclEmptyBillAndService.searchStatusByVehiclEmptyBillNo(vehicleEmpNo);
        if(list==1 ){
	         return returnError(new TfrBusinessException("空驶车辆在途，已到达状态下不可删除","空驶车辆在途，已到达状态下不可删除"));
           }
		   return returnSuccess();
		
	}

	public void setVehiclEmptyBillAndService(
			IvehiclEmptyBillService vehiclEmptyBillAndService) {
		this.vehiclEmptyBillAndService = vehiclEmptyBillAndService;
	}


	public VehiclEmptyBillVo getVehiclEmptyBillVo() {
		return vehiclEmptyBillVo;
	}
	public void setVehiclEmptyBillVo(VehiclEmptyBillVo vehiclEmptyBillVo) {
		this.vehiclEmptyBillVo = vehiclEmptyBillVo;
	}


	
	
	}
