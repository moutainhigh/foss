package com.deppon.foss.module.pickup.predeliver.server.service.impl;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.inteface.domain.pdatime.SyncPdaTimeRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsPdaService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;

/**
 * FOSS系统同步PDA作业时间
 * @author 159231-foss-meiying
 * @date 2014-3-6 下午3:33:23
 */
public class GpsPdaService implements IGpsPdaService
{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GpsPdaService.class.getName());
	
	private IDeliverbillDao deliverbillDao;
	private IEmployeeService employeeService;
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	private AccessHeader createAccessHeader(String deliverbillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode(DeliverbillConstants.ESB_FOSS2ESB_SYNC_PDATIME_CODE);
		//版本随意
		header.setVersion(DeliverbillConstants.ESB_FOSS2ESB_SYNC_DELIVERYTASK_VERSION);
		//business id 随意
		header.setBusinessId(deliverbillNo);
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(deliverbillNo);
		return header;
	}
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * pda开单时FOSS同步数据给GPS
	 *  @author 159231-foss-meiying
	 * @date 2014-3-8 上午8:30:57
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsPdaService#pDaPickupToGps(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto)
	 */
	@Override
	public void pDaPickupToGps(WaybillPdaDto dto) {
		//准备消息头信息
		AccessHeader header = createAccessHeader(dto.getWaybillNo());
		//创建具体消息
		SyncPdaTimeRequest request = new SyncPdaTimeRequest();
		//DMANA-6495 FOSS将PDA信息同步到GPS接口优化start liutao 2014/11/18
		//request.setOrderNo(dto.getOrderNo());//订单号
		//request.setOrderType(DeliverbillConstants.GPS_ORDERTYPE_RECEIVE);//接送货标识 2 接货
		request.setPdaTime(dto.getBillStart());
		request.setWaybillNo(dto.getWaybillNo());
		if(StringUtils.isBlank(dto.getOrderNo())){
			request.setOrderType(DeliverbillConstants.GPS_UNORDERTYPE_RECEIVE);//接送货标识 3 无订单接货
			request.setOrderNo(null);//订单号
			request.setVehicleNo(dto.getLicensePlateNum());//车牌号
			request.setCustomerAddress(null);//接货地址
			// 司机姓名，根据工号读取姓名
			String empname=employeeService.queryEmpNameByEmpCode(dto.getCreateUserCode());
		    request.setDriverCode(dto.getCreateUserCode());
			request.setDriverName(empname);
		}else{
			request.setOrderType(DeliverbillConstants.GPS_ORDERTYPE_RECEIVE);//接送货标识 2  有订单接货
			request.setOrderNo(dto.getOrderNo());//订单号
			request.setVehicleNo(null);//车牌号
			DispatchOrderEntity dispatchorderentity=dispatchOrderEntityDao.queryAllInfoByOrderNo(dto.getOrderNo());
		    request.setCustomerAddress(dispatchorderentity==null ? null : dispatchorderentity.getPickupProvince()+dispatchorderentity.getPickupCity()+dispatchorderentity.getPickupCounty()+dispatchorderentity.getPickupElseAddress());
		    //司机工号,姓名
			 request.setDriverCode(null);
		     request.setDriverName(null);
		}
		// DMANA-6495 FOSS将PDA信息同步到GPS接口优化end liutao 2014/11/18 
		request.setVolume(dto.getGoodsVolumeTotal()==null ? 0:dto.getGoodsVolumeTotal().doubleValue());
		request.setWeight(dto.getGoodsWeightTotal()==null ?0:dto.getGoodsWeightTotal().doubleValue());
		// 发送请求
		try {
			ESBJMSAccessor.asynReqeust(header, request);
		} catch (Exception e) {
			LOGGER.error("FOSS系统同步PDA作业时间-送货 通过ESB发送请求报错"+e);
		}
	}
	/**
	 *  pda派送签收时FOSS同步数据给GPS
	 *  @author 159231-foss-meiying
	 * @date 2014-3-8 上午8:30:07
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsPdaService#pDaDeliverSignToGps(java.lang.String)
	 */
	@Override
	public void pDaDeliverSignToGps(String arrivesheetNo,String waybillNo,Date signTime) {
		DeliverbillDetailDto deliverbillDto = new DeliverbillDetailDto ();
		deliverbillDto.setArrivesheetNo(arrivesheetNo);//到达联编号
		deliverbillDto.setStatus(DeliverbillConstants.STATUS_PDA_DOWNLOADED);//派送单状态为已下拉
		deliverbillDto.setWaybillNo(waybillNo);
		DeliverbillDto billDto =deliverbillDao.queryDeliverBillByArrivesheetNo(deliverbillDto);
		if(billDto!=null){
			//准备消息头信息
			AccessHeader header = createAccessHeader(arrivesheetNo);
			//创建具体消息
			SyncPdaTimeRequest request = new SyncPdaTimeRequest();
			request.setOrderNo(billDto.getDeliverbillNo());//派送单号
			request.setOrderType(DeliverbillConstants.GPS_ORDERTYPE_DELIVER);//接送货标识 1
			request.setPdaTime(signTime);
			request.setWaybillNo(waybillNo);
			request.setVolume(billDto.getVolumeTotal()==null ?0:billDto.getVolumeTotal().doubleValue());
			request.setWeight(billDto.getWeightTotal()==null ?0:billDto.getWeightTotal().doubleValue());
			request.setCustomerAddress(handleQueryOutfieldService.getCompleteAddressAttachAddrNote(billDto.getReceiveCustomerProvCode(), billDto.getReceiveCustomerCityCode(), billDto.getReceiveCustomerDistCode(), billDto.getReceiveCustomerAddress(), billDto.getReceiveCustomerAddressNote()));//接货地址
			// 发送请求
			try {
				ESBJMSAccessor.asynReqeust(header, request);
			} catch (Exception e) {
				LOGGER.error("FOSS系统同步PDA作业时间-送货签收 通过ESB发送请求报错:"+waybillNo+e.getMessage());
			}
			LOGGER.info("FOSS系统同步PDA作业时间-送货签收 完成"+deliverbillDto.toString());
		}
	}
	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}
}
