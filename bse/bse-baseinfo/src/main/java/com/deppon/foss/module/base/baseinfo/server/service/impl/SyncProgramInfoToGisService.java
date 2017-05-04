package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceClient;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncProgramInfoToGisService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SysToGisOrgDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressTrainScheduleException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.gis.gisserviceroute.CommonException;
import com.deppon.gis.gisserviceroute.DeptInfo;
import com.deppon.gis.gisserviceroute.ExpressRoutePlanRequest;
import com.deppon.gis.gisserviceroute.ExpressRoutePlanResponse;
import com.deppon.gis.gisserviceroute.GisServiceRoute;
/**
 * 
 *<p>Title: ISyncProgramInfoToGisService</p>
 * <p>Description: 同步快递支线班车方案信息给GIS  Web Service客户端服务接口实现</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-14
 */
@WebServiceClient
public class SyncProgramInfoToGisService implements
		ISyncProgramInfoToGisService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncProgramInfoToGisService.class);
	
	private GisServiceRoute gisServiceRoute;
	
	
	public GisServiceRoute getGisServiceRoute() {
		return gisServiceRoute;
	}


	public void setGisServiceRoute(GisServiceRoute gisServiceRoute) {
		this.gisServiceRoute = gisServiceRoute;
	}
	/**
	 *<p>Title: syncProgramInfo</p>
	 *<p>向Gis同步方案信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午7:18:31
	 * @param dto
	 * @return
	 */
	@Override
	public boolean syncProgramInfo(ExpressTrainProgramDto dto) {
		LOGGER.info("syncExpressTrainProgramInfo:send info start.............");

		ESBHeader header = new ESBHeader();
		// 设置服务编码
		header.setEsbServiceCode("ESB_FOSS2ESB_EXPRESS_ROUTEPLAN_INFO");
		header.setMessageFormat("SOAP");
		header.setSourceSystem("FOSS");
		header.setExchangePattern(1);
		header.setVersion("1.0");
		header.setRequestId(UUID.randomUUID().toString());

		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		if(null!=dto){
			//非空校验
			if(null!=dto.getExpressTrainProgramEntity()
					&&null!=dto.getOutFieldDto()&&CollectionUtils.isNotEmpty(dto.getSalesDtoList())){
				//方案名称作为唯一标识
				header.setBusinessId(dto.getExpressTrainProgramEntity().getProgramName());
				ExpressRoutePlanRequest request =new ExpressRoutePlanRequest();
				//封装request
				request =convertToGISEntity(request,dto);
				boolean flag =false;
				try {
					ExpressRoutePlanResponse  response = gisServiceRoute.expressRoutePlan(holder, request);
					LOGGER.info("syncLine:send info end.............");
					LOGGER.debug("syncLine:result: "+response.getResultCode());
					//如果成功，返回true
					if(response.getResultCode().equals("1")){
						flag = true;
					}else{
						if(response.getResultReason().startsWith("有坐标无效营业部")||response.getResultReason().startsWith("该方案外场坐标无效")){
							throw new ExpressTrainScheduleException(response.getResultReason());
						}
						flag = false;
					}
					return flag;
				 }catch (CommonException e) {
					return false;
				 }
			}
		}
		return false;
	}
	/**
	 * 
	 *<p>Title: convertToGISEntity</p>
	 *<p>将传过来的dto 转换成Esb对象</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-15上午9:45:00
	 * @param request
	 * @param dto
	 * @return
	 */
	private ExpressRoutePlanRequest convertToGISEntity(
			ExpressRoutePlanRequest request, ExpressTrainProgramDto dto) {
		//方案名称
		request.setProgramName(dto.getExpressTrainProgramEntity().getProgramName());
		//城市编码
		request.setCityCode(dto.getExpressTrainProgramEntity().getCityCode());
		//城市名称
		request.setCityName(dto.getExpressTrainProgramEntity().getCityName());
		//车速
		request.setTruckSpeed(dto.getExpressTrainProgramEntity().getSpeed());
		//装卸时间
		request.setUnloadTime(BigInteger.valueOf(dto.getExpressTrainProgramEntity().getHandlingTime()));
		//开始时间
		request.setLeaveTime(convertToXMLGregorianCalendar(dto.getExpressTrainProgramEntity().getStartTime()));
		//结束时间
		request.setArriveTime(convertToXMLGregorianCalendar(dto.getExpressTrainProgramEntity().getEndTime()));
		//外场
		DeptInfo deptInfo = new DeptInfo();
		deptInfo.setDeptName(dto.getOutFieldDto().getOrgName());
		deptInfo.setDeptNo(dto.getOutFieldDto().getOrgCode());
		deptInfo.setDeptCordinateCode(dto.getOutFieldDto().getOrgCoordinate());
		request.setWaiChang(deptInfo);
		//营业部集合
		for (SysToGisOrgDto  toGisDto: dto.getSalesDtoList()) {
			DeptInfo saleInfo = new DeptInfo();
			saleInfo.setDeptName(toGisDto.getOrgName());
			saleInfo.setDeptNo(toGisDto.getOrgCode());
			saleInfo.setDeptCordinateCode(toGisDto.getOrgCoordinate());
			request.getDeptsList().add(saleInfo);
		}
		return request;
	}
	/**
	 * 
	 *<p>Title: convertToXMLGregorianCalendar</p>
	 *<p>时间转换类型</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-15上午10:17:27
	 * @param startTime
	 * @return
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date startTime) {
		  	GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(startTime);
	        XMLGregorianCalendar gc = null;
	        try {
	            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        } catch (Exception e) {
	             e.printStackTrace();
	        }
	        return gc;
	}
}
