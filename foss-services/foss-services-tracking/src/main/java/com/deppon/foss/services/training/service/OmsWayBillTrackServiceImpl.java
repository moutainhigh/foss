package com.deppon.foss.services.training.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.foss.services.training.shared.request.QueryWaybillTrackingInfoRequest;
import com.deppon.foss.services.training.shared.request.QueryWaybillTrajectoryRequest;
import com.deppon.foss.services.training.shared.response.QueryWaybillTrackingInfoResponse;
import com.deppon.foss.services.training.shared.response.QueryWaybillTrajectoryResponse;
import com.deppon.foss.services.training.shared.vo.WaybillTrackingInfo;
import com.deppon.foss.services.training.shared.vo.WaybillTrajectoryInfo;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.OnthewayDTO;
import com.deppon.foss.service.waybilltrackservice.CommonException;
@Controller
public class OmsWayBillTrackServiceImpl implements IOmsWayBillTrackService{

	private static Logger LOGGER = Logger
			.getLogger(WayBillTrackServiceImpl.class);
	/**
	 * 运单轨迹服务跟踪
	 */
	@Resource
	private ITrackingService trackingService;
	/**
	 * 运单轨迹接口
	 */
	@Resource
	private IWayBillNoLocusService wayBillNoLocusService;

	/**
	 * 
	 * 查询运单跟踪
	 * 
	 */
	public @ResponseBody QueryWaybillTrackingInfoResponse queryWaybillTrackingInfo(@RequestBody QueryWaybillTrackingInfoRequest queryWaybillTrackingInfoRequest) throws CommonException {
		String waybillNo = queryWaybillTrackingInfoRequest.getWaybillNo();

		try {
			List<OnthewayDTO> list = trackingService
					.getTaskTrackingByWayBillNo(waybillNo);
			List<WaybillTrackingInfo> waybillTrackingInfo = new ArrayList<WaybillTrackingInfo>();
			for (OnthewayDTO onthewayDTO : list) {
				WaybillTrackingInfo waybill = new WaybillTrackingInfo();
				waybill.setWaybillNo(waybillNo);// 运单号
				waybill.setTrackContent(onthewayDTO.getCurrentContent());// 跟踪内容
				waybill.setContactPerson(onthewayDTO.getDriverName());// 司机姓名
				waybill.setContactPersonCode(onthewayDTO.getDriverCode());// 司机编码
				waybill.setTrackType(onthewayDTO.getSystemTrackingStatus());// 系统跟踪类别
				waybill.setHumanTrackType(onthewayDTO.getManualTrackingStatus());// 人工跟踪类别
				waybill.setTrackTime(onthewayDTO.getTrackingTime());// 跟踪时间
				waybill.setTrackDept(onthewayDTO.getTrackingOrgName());// 跟踪部门
				waybill.setTrackDeptCode(onthewayDTO.getTrackingOrgCode());// 跟踪部门编码
				waybill.setDepartureDeptName(onthewayDTO.getOrigOrgName());// 出发部门
				waybill.setDepartureDeptNum(onthewayDTO.getOrigOrgCode());// 出发部门编码
				waybill.setDestinationDeptName(onthewayDTO.getDestOrgName());// 到达部门
				waybill.setDestinationDeptNum(onthewayDTO.getDestOrgCode());// 到达部门编码
				waybill.setTrackPerson(onthewayDTO.getTrackingUserCode());// 跟踪人
				waybillTrackingInfo.add(waybill);
			}
			QueryWaybillTrackingInfoResponse response = new QueryWaybillTrackingInfoResponse();

			response.getWaybillTrackingInfo().addAll(waybillTrackingInfo);
			return response;
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			throw new CommonException(e.getErrorCode());
		}
	}

	/**
	 * 
	 * 查询运单轨迹
	 * 
	 */
	public @ResponseBody QueryWaybillTrajectoryResponse queryWaybillTrajectory(@RequestBody QueryWaybillTrajectoryRequest queryWaybillTrajectoryRequest)throws CommonException{
		String waybillNo = queryWaybillTrajectoryRequest.getWaybillNo();

		try {
			List<WayBillNoLocusDTO> list = wayBillNoLocusService
					.getWayBillNoLocus(waybillNo);
			List<WaybillTrajectoryInfo> waybillTrajectoryInfo = new ArrayList<WaybillTrajectoryInfo>();
			for (WayBillNoLocusDTO wayBillDTO : list) {
				WaybillTrajectoryInfo waybillInfo = new WaybillTrajectoryInfo();
				waybillInfo.setWaybillNo(wayBillDTO.getBillNo());// 运单号
				waybillInfo.setOperateDept(wayBillDTO.getOperateOrgName());// 操作部门
				waybillInfo.setOperateDeptCode(wayBillDTO.getOperateOrgCode());// 操作部门
				waybillInfo.setOperateDeptCityCode(wayBillDTO.getOperateCityCode());// 操作城市编码
				waybillInfo.setOperateDeptCityName(wayBillDTO.getOperateCityName());// 操作城市名称
				waybillInfo.setOperateType(wayBillDTO.getOperateType());// 操作类型
				waybillInfo.setOperateContent(wayBillDTO.getOperateContent());// 操作内容
				waybillInfo.setOperateTime(wayBillDTO.getOperateTime());// 操作时间
				waybillInfo.setOperator(wayBillDTO.getOperateName());// 操作员
				waybillInfo.setBillNo(wayBillDTO.getBillNo());// 单号
				waybillInfo.setLicensePlateNo(wayBillDTO.getVehicleNo());// 车牌号
				waybillInfo.setRemarks(wayBillDTO.getNotes());// 备注
				waybillInfo.setNextDept(wayBillDTO.getNextOrgCode());// 下一部门代码
				waybillInfo.setNextDeptName(wayBillDTO.getNextOrgName());// 下一部门名称
				waybillInfo.setNextDeptCityNum(wayBillDTO.getNextCityCode());// 下一部门城市编码
				waybillInfo.setNextDeptCityName(wayBillDTO.getNextCityName());// 下一部门城市名称
				waybillInfo.setDestinationDept(wayBillDTO.getDestinationStationOrgCode());// 目的部门编码
				waybillInfo.setDestinationDeptName(wayBillDTO.getDestinationStationOrgName());// 目的部门名称
				waybillInfo.setDestinationDeptCityNum(wayBillDTO.getDestinationStationCityCode());// 目的部门城市编码
				waybillInfo.setDestinationDeptCityName(wayBillDTO.getDestinationStationCityName());// 目的部门城市名称
				waybillInfo.setPredictArriveNextDeptTime(wayBillDTO.getPlanArriveTime());// 预计到达时间
				waybillInfo.setDispatcher(wayBillDTO.getDeliveryName());// 派送员
				waybillInfo.setDispatcherPhone(wayBillDTO.getDeliveryPhone());// 派送员电话
				waybillInfo.setSigner(wayBillDTO.getSignManName());// 接收员
				// **********设置预计到达时间 200968 2016-02-20 private Date
				// preArriveTime;
				waybillInfo.setPreArriveTime(wayBillDTO.getPreArriveTime());
				waybillTrajectoryInfo.add(waybillInfo);
			}
			QueryWaybillTrajectoryResponse response = new QueryWaybillTrajectoryResponse();

			response.getWaybillTrajectoryInfo().addAll(waybillTrajectoryInfo);
			return response;
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			com.deppon.esb.exception.CommonExceptionInfo commonExceptionInfo = new CommonExceptionInfo();
			commonExceptionInfo.setExceptioncode(e.getErrorCode());
			commonExceptionInfo.setMessage(e.getMessage());
			throw new CommonException(e.getMessage(), commonExceptionInfo);
		}
	}

	/**
	 * 
	 * <p>
	 * 把java日期转换为XML格式日期
	 * </p>
	 * 
	 */
//	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
//		if (date != null) {
//			GregorianCalendar cal = new GregorianCalendar();
//			cal.setTime(date);
//			XMLGregorianCalendar gc = null;
//			try {
//				gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//			} catch (Exception e) {
//				LOGGER.info("XML日期类型转换错误：", e);
//			}
//			return gc;
//		} else {
//			return null;
//		}
//	}

	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService) {
		this.wayBillNoLocusService = wayBillNoLocusService;
	}

	public void setTrackingService(ITrackingService trackingService) {
		this.trackingService = trackingService;
	}

}
