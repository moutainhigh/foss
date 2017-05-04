package com.deppon.foss.services.training.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;

import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.QueryWaybillDetailRequest;
import com.deppon.esb.inteface.domain.foss.QueryWaybillDetailResponse;
import com.deppon.esb.inteface.domain.foss.QueryWaybillForCCRequest;
import com.deppon.esb.inteface.domain.foss.QueryWaybillForCCResponse;
import com.deppon.esb.inteface.domain.foss.QueryWaybillTrackingInfoRequest;
import com.deppon.esb.inteface.domain.foss.QueryWaybillTrackingInfoResponse;
import com.deppon.esb.inteface.domain.foss.QueryWaybillTrajectoryRequest;
import com.deppon.esb.inteface.domain.foss.QueryWaybillTrajectoryResponse;
import com.deppon.esb.inteface.domain.foss.WaybillTrackingInfo;
import com.deppon.esb.inteface.domain.foss.WaybillTrajectoryInfo;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.OnthewayDTO;
import com.deppon.foss.module.transfer.ontheway.api.shared.define.OnthewayConstant;
import com.deppon.foss.service.waybilltrackservice.CommonException;
import com.deppon.foss.service.waybilltrackservice.WayBillTrackService;

public class WayBillTrackServiceImpl implements WayBillTrackService
{
	private static Logger LOGGER = Logger
			.getLogger(WayBillTrackServiceImpl.class);

	/**
	 * 运单轨迹接口
	 */
	private IWayBillNoLocusService wayBillNoLocusService;

	/**
	 * 运单轨迹服务跟踪
	 */
	private ITrackingService trackingService;

	/**
	 * 
	 * 查询运单跟踪
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 下午5:13:53
	 * @see com.deppon.foss.service.waybilltrackservice.WayBillTrackService#queryWaybillTrackingInfo(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.foss.QueryWaybillTrackingInfoRequest)
	 */
	@Override
	public QueryWaybillTrackingInfoResponse queryWaybillTrackingInfo(
			Holder<ESBHeader> esbHeader,
			QueryWaybillTrackingInfoRequest queryWaybillTrackingInfoRequest)
			throws CommonException
	{
		String waybillNo = queryWaybillTrackingInfoRequest.getWaybillNo();

		try
		{
			List<OnthewayDTO> list = trackingService
					.getTaskTrackingByWayBillNo(waybillNo);
			List<WaybillTrackingInfo> waybillTrackingInfo = new ArrayList<WaybillTrackingInfo>();
			//时间格式转化
			DatatypeFactory dtf = null;
			try {
				dtf = DatatypeFactory.newInstance();
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			GregorianCalendar cal = null;
			
			for (OnthewayDTO onthewayDTO : list)
			{
				WaybillTrackingInfo waybill = new WaybillTrackingInfo();				
				
				// 跟踪内容转成线路和路段
				onthewayDTO.setCurrentContent("线路：" + onthewayDTO.getLineName()
						+ "；路段：" + onthewayDTO.getCurrentPlace());
				if (OnthewayConstant.ONTHEWAY_TRACKING_TYPE_GPS.equals(onthewayDTO.getTrackingType())){
					//系统跟踪状态
					onthewayDTO.setSystemTrackingStatus(onthewayDTO.getCurrentStatus());
				}else if (OnthewayConstant.ONTHEWAY_TRACKING_TYPE_HANDLE.equals(onthewayDTO.getTrackingType())) {
					//人工跟踪状态
					onthewayDTO.setManualTrackingStatus(onthewayDTO.getCurrentStatus());
				}
				
				waybill.setWaybillNo(waybillNo);// 运单号
				waybill.setTrackContent(onthewayDTO.getCurrentContent());// 跟踪内容
				waybill.setContactPerson(onthewayDTO.getDriverName());// 司机姓名
				waybill.setContactPersonCode(onthewayDTO.getDriverCode());// 司机编码
				// 系统跟踪类别
				waybill.setTrackType(onthewayDTO.getSystemTrackingStatus());// 系统跟踪类别
				waybill.setHumanTrackType(onthewayDTO.getManualTrackingStatus());// 人工跟踪类别
				//跟踪时间
				if(onthewayDTO.getTrackingTime()!=null){
					cal = new GregorianCalendar();
					cal.setTime(onthewayDTO.getTrackingTime());
					XMLGregorianCalendar trackTime = dtf.newXMLGregorianCalendar(cal);
					waybill.setTrackTime(trackTime);// 跟踪时间
				}else{
					waybill.setTrackTime(null);// 跟踪时间
				}
				
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
		} catch (BusinessException e){
			LOGGER.error(e.getErrorCode());
			throw new CommonException(e.getErrorCode());
		}
	}

	/**
	 * 
	 * 查询运单明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 下午5:14:07
	 * @see com.deppon.foss.service.waybilltrackservice.WayBillTrackService#queryWaybillDetail(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.foss.QueryWaybillDetailRequest)
	 */
	@Override
	public QueryWaybillDetailResponse queryWaybillDetail(
			Holder<ESBHeader> esbHeader,
			QueryWaybillDetailRequest queryWaybillDetailRequest)
			throws CommonException
	{
		return null;
	}

	/**
	 * 
	 * 查询运单轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 下午5:14:19
	 * @see com.deppon.foss.service.waybilltrackservice.WayBillTrackService#queryWaybillTrajectory(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.foss.QueryWaybillTrajectoryRequest)
	 */
	@Override
	public QueryWaybillTrajectoryResponse queryWaybillTrajectory(
			Holder<ESBHeader> esbHeader,
			QueryWaybillTrajectoryRequest queryWaybillTrajectoryRequest)
			throws CommonException
	{
		String waybillNo = queryWaybillTrajectoryRequest.getWaybillNo();

		//时间格式转化
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		GregorianCalendar cal = null;
		
		try
		{
			List<WayBillNoLocusDTO> list = wayBillNoLocusService
					.getWayBillNoLocus(waybillNo);
			List<WaybillTrajectoryInfo> waybillTrajectoryInfo = new ArrayList<WaybillTrajectoryInfo>();
			for (WayBillNoLocusDTO wayBillDTO : list)
			{
				WaybillTrajectoryInfo waybillInfo = new WaybillTrajectoryInfo();
				waybillInfo.setWaybillNo(wayBillDTO.getBillNo());// 运单号
				waybillInfo.setOperateDept(wayBillDTO.getOperateOrgName());// 操作部门
				waybillInfo.setOperateDeptCode(wayBillDTO.getOperateOrgCode());// 操作部门
				waybillInfo.setOperateDeptCityCode(wayBillDTO
						.getOperateCityCode());// 操作城市编码
				waybillInfo.setOperateDeptCityName(wayBillDTO
						.getOperateCityName());// 操作城市名称
				waybillInfo.setOperateType(wayBillDTO.getOperateType());// 操作类型
				waybillInfo.setOperateContent(wayBillDTO.getOperateContent());// 操作内容
				
				cal = new GregorianCalendar();
				if(wayBillDTO.getOperateTime()!=null){
					cal.setTime(wayBillDTO.getOperateTime());
					XMLGregorianCalendar operateTime = dtf.newXMLGregorianCalendar(cal);
					waybillInfo.setOperateTime(operateTime);// 操作时间
				}else{
					waybillInfo.setOperateTime(null);// 操作时间
				}
				
				waybillInfo.setOperator(wayBillDTO.getOperateName());// 操作员
				waybillInfo.setBillNo(wayBillDTO.getBillNo());// 单号
				waybillInfo.setLicensePlateNo(wayBillDTO.getVehicleNo());// 车牌号
				waybillInfo.setRemarks(wayBillDTO.getNotes());// 备注
				waybillInfo.setNextDept(wayBillDTO.getNextOrgCode());// 下一部门代码
				waybillInfo.setNextDeptName(wayBillDTO.getNextOrgName());// 下一部门名称
				waybillInfo.setNextDeptCityNum(wayBillDTO.getNextCityCode());// 下一部门城市编码
				waybillInfo.setNextDeptCityName(wayBillDTO.getNextCityName());// 下一部门城市名称
				waybillInfo.setDestinationDept(wayBillDTO
						.getDestinationStationOrgCode());// 目的部门编码
				waybillInfo.setDestinationDeptName(wayBillDTO
						.getDestinationStationOrgName());// 目的部门名称
				waybillInfo.setDestinationDeptCityNum(wayBillDTO
						.getDestinationStationCityCode());// 目的部门城市编码
				waybillInfo.setDestinationDeptCityName(wayBillDTO
						.getDestinationStationCityName());// 目的部门城市名称
				if(wayBillDTO.getPlanArriveTime() !=null){
					// 预计到达时间
					cal.setTime(wayBillDTO.getPlanArriveTime());
					XMLGregorianCalendar predictArriveNextDeptTime = dtf.newXMLGregorianCalendar(cal);
					waybillInfo.setPredictArriveNextDeptTime(predictArriveNextDeptTime);// 预计到达时间
				}else{
					waybillInfo.setPredictArriveNextDeptTime(null);// 预计到达时间
				}
				waybillInfo.setDispatcher(wayBillDTO.getDeliveryName());// 派送员
				waybillInfo.setDispatcherPhone(wayBillDTO.getDeliveryPhone());// 派送员电话
				waybillInfo.setSigner(wayBillDTO.getSignManName());// 接收员
				//**********设置预计到达时间 200968 2016-02-20 private Date preArriveTime;
				if(wayBillDTO.getPreArriveTime() !=null){
					cal.setTime(wayBillDTO.getPreArriveTime());
					XMLGregorianCalendar preArriveTime = dtf.newXMLGregorianCalendar(cal);
					waybillInfo.setPreArriveTime(preArriveTime);
				}else{
					waybillInfo.setPreArriveTime(null);
				}
				
				waybillTrajectoryInfo.add(waybillInfo);
			}
			QueryWaybillTrajectoryResponse response = new QueryWaybillTrajectoryResponse();

			response.getWaybillTrajectoryInfo().addAll(waybillTrajectoryInfo);
			return response;
		} catch (BusinessException e)
		{
			LOGGER.error(e.getMessage());
			com.deppon.esb.exception.CommonExceptionInfo commonExceptionInfo = new CommonExceptionInfo();
			commonExceptionInfo.setExceptioncode(e.getErrorCode());
			commonExceptionInfo.setMessage(e.getMessage());
			throw new CommonException(e.getMessage(), commonExceptionInfo);
		}
	}

	
	/**
	 * 
	 * <p>CC-语音货物轨迹查询</p> 
	 * @author alfred
	 * @date 2014-7-31 上午10:56:21
	 * @param esbHeader
	 * @param queryWaybillForCCRequest
	 * @return
	 * @throws CommonException 
	 * @see com.deppon.foss.service.waybilltrackservice.WayBillTrackService#queryWaybillCurrentStatueForCC(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.foss.QueryWaybillForCCRequest)
	 */
	@Override
	public QueryWaybillForCCResponse queryWaybillCurrentStatueForCC(
			Holder<ESBHeader> esbHeader,
			QueryWaybillForCCRequest queryWaybillForCCRequest)
			throws CommonException {
		QueryWaybillForCCResponse response = new QueryWaybillForCCResponse();
		
		LOGGER.error("-------CC调用轨迹程序开始----------");
		try {
			String waybillNo = queryWaybillForCCRequest.getWaybillNo();
			if (StringUtil.isBlank(waybillNo)){
				response.setErrorMessage("运单号为空");
				LOGGER.error("运单号为空");
				return response;
			}
			WayBillNoLocusDTO dto = wayBillNoLocusService.getWayBillNoLocusForCC(waybillNo);
			if(dto==null){
				return null;
			}else{
				//运单号
				response.setWaybillNo(dto.getWaybillNo());
				//运单状态
				response.setWaybillStatus(dto.getCurrentStatus());
				//目的地
				response.setDestinationDeptName(dto.getDestinationStationOrgName());
				//到达时间
				response.setArrivedTime(convertToXMLGregorianCalendar(dto.getOperateTime()));
				//派送员
				response.setDispatcher(dto.getDeliveryName());
				//派送员联系方式
				response.setDispatcherPhone(dto.getDeliveryPhone());
				//签收人
				response.setSigner(dto.getSignManName());
				//下一部门城市
				response.setNextDeptCityName(dto.getNextCityName());
				//下一部门
				response.setNextDeptName(dto.getNextOrgName());
				//上一部门城市
				response.setBeforeDeptCityName(dto.getOperateCityName());
				//上一部门
				response.setBeforeDeptName(dto.getOperateOrgName());
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			response.setErrorMessage(e.getMessage());
		}
		LOGGER.error("-------CC调用轨迹程序结束----------");
		return response;
	}
	
	
	/**
	 * 
	 * <p>
	 * 把java日期转换为XML格式日期
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-13 下午4:55:50
	 * @param date
	 * @return
	 * @see
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date)
	{
		if (date != null)
		{
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar gc = null;
			try
			{
				gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			} catch (Exception e)
			{
				LOGGER.info("XML日期类型转换错误：", e);
			}
			return gc;
		} else
		{
			return null;
		}
	}

	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService)
	{
		this.wayBillNoLocusService = wayBillNoLocusService;
	}

	public void setTrackingService(ITrackingService trackingService)
	{
		this.trackingService = trackingService;
	}

}
