package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IQueryAirWaybillTrailByWaybillNoDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IQueryAirWaybillTrailByWaybillNoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillQueryCreateDeptVO;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.StatusAndTrackResponse;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.WaybillTrack;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;

/**
* @description 配合(快递)根据运单号查询对应的航空运单轨迹
* (non-Javadoc)
* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
* @author 106162-foss-liping
* @update 2016年5月16日 上午10:07:04
* @version V1.0
*/
public class QueryAirWaybillTrailByWaybillNoService implements
		IQueryAirWaybillTrailByWaybillNoService {

	/**
	 * 定义QueryAirWaybillTrailByWaybillNoDao变量
	 */
	private IQueryAirWaybillTrailByWaybillNoDao queryAirWaybillTrailDao;
	
	/**
	 * 运单管理接口
	 */
	IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单轨迹服务跟踪
	 */
	ITrackingService trackingService;
	
	/**
	 * 查询空运正单信息
	 */
	IAirTrackFlightService airTrackFlightService;
	
	/**
	 * 组织信息 Service接口
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	* @description 配合(快递)根据运单查询航空正单轨迹
	* (non-Javadoc)
	* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
	*                             #queryStatusAndTrackResponse(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	* @param(5666661211,521521032,5215211010,5666661334,5666663120,5666663124,5666663133)
	*/
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public StatusAndTrackResponse queryStatusAndTrackResponse(String waybillNo) {   
		//0.创建返回实体实例
		//List<GeneralInformation> generalInformations = new ArrayList<GeneralInformation>();
	    List<WaybillTrack> waybillTracks =new ArrayList<WaybillTrack>();
		StatusAndTrackResponse statusAndTrackResponseEnt = new StatusAndTrackResponse();
		List<WayBillNoLocusDTO> tfrlist = new ArrayList<WayBillNoLocusDTO>();
		WaybillEntity waybillEntity =new WaybillEntity();
		
		//修改106162 date：2016-11-08  当悟空给我们传过来包号的时候，运单表是不保存，所以这步就不再去查询运单了
		if(null!=waybillNo && !waybillNo.contains("B")){
			waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			//1.封装实体信息,获取运单实体信息
			if(waybillEntity==null){
				return null;
			}
		}else{
			waybillEntity.setWaybillNo(waybillNo);
		}
		
		// 制作航空正单
		List<AirWaybillEntity> airWaybillEntityList = trackingService.queryAirWayBillListByWaybillNo(waybillEntity.getWaybillNo());
		for (AirWaybillEntity airWaybillEntity : airWaybillEntityList) {
			// 根据正单号查询合票清单明细
			AirTransPickupBillDto airTransPickupBillDto = new AirTransPickupBillDto();
			if (airWaybillEntity != null) {
				tfrlist.add(getWayBillNoLocusFromAirBill(waybillEntity, airWaybillEntity));
				airTransPickupBillDto.setAirWaybillNo(airWaybillEntity.getAirWaybillNo());
				// 如果正单的状态是待跟踪
				List<AirTrackFlightDto> airTrackinglist = airTrackFlightService.queryTrackInfoByAirWaybillNo(airWaybillEntity.getAirWaybillNo());
				if (airTrackinglist != null && airTrackinglist.size() == 1) {
					// 只有起飞
					tfrlist.add(getWayBillNoLocusFromAirTakeOff(waybillEntity, airTrackinglist.get(0), airWaybillEntity));
				} else if (airTrackinglist != null && airTrackinglist.size() == 2) {
					// 既有起飞也有到达
					tfrlist.add(getWayBillNoLocusFromAirTakeOff(waybillEntity, airTrackinglist.get(0), airWaybillEntity));
					tfrlist.add(getWayBillNoLocusFromAirArrive(waybillEntity, airTrackinglist.get(1), airWaybillEntity));
				}
			}
		}
		// 2.按照制单时间来排序
		//Collections.sort(tfrlist, new WayBillNoLocusDTO());
		// 3.转换实体获取部分值传给快递那边
		WaybillTrack waybillTrack=null;
		for(WayBillNoLocusDTO ent :tfrlist){
			waybillTrack = new WaybillTrack();
			BeanUtils.copyProperties(ent, waybillTrack);
			waybillTracks.add(waybillTrack);
		}
		//4.获取轨迹信息
		//waybillTracks = queryAirWaybillTrailDao.queryWaybillTracks(waybillNo);
		statusAndTrackResponseEnt.setWaybillTracks(waybillTracks);
		return statusAndTrackResponseEnt;
	}
	/**
	 * 配合快递项目航空正单轨迹
	 * @author 106162
	 * @param waybillEntiy
	 * @param airWaybillEntity
	 * @return WayBillNoLocusDTO
	 * @date 2016-06-01
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirBill(WaybillEntity waybillEntiy, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airWaybillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airWaybillEntity.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_HANDOVER_BILL);
		// 设置操作时间为起飞时间
		wayBillNoLocusDTO.setOperateTime(airWaybillEntity.getCreateTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airWaybillEntity.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes("预计航班起飞时间:" + DepartureHandle.getDateStr(airWaybillEntity.getTakeOffTime()) + ";航班运价:" + airWaybillEntity.getFee() + "元每公斤");
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 航空起飞
	 * @author 106162
	 * @param waybillEntiy
	 * @param airWaybillEntity
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirTakeOff(WaybillEntity waybillEntiy, AirTrackFlightDto airTrackFlightDto, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airTrackFlightDto.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airTrackFlightDto.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_TAKE_OFF);
		// 设置操作时间为起飞时间
		wayBillNoLocusDTO.setOperateTime(airTrackFlightDto.getActualTakeOffTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airTrackFlightDto.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes(airTrackFlightDto.getMessage());
		return wayBillNoLocusDTO;
	}

	/**
	 * 航空到达
	 * @author 106162
	 * @param waybillEntiy
	 * @param airWaybillEntity
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirArrive(WaybillEntity waybillEntiy, AirTrackFlightDto airTrackFlightDto, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airWaybillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airTrackFlightDto.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为航班到达
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_ARRIVE);
		// 设置操作时间为到达时间
		wayBillNoLocusDTO.setOperateTime(airTrackFlightDto.getActualArriveTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airTrackFlightDto.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes(airTrackFlightDto.getMessage());
		return wayBillNoLocusDTO;
	}

	/**
	 * 设置组织相关信息
	 * @param orgCode
	 * @param dto
	 * @param operOrgType
	 * @author 106162
	 * @date july 1 , 2016 2:38:19 PM
	 */
	private void setOrgInfo(String orgCode, WayBillNoLocusDTO dto, int operOrgType) {
		if (StringUtil.isBlank(orgCode)) {
			return;
		}
		// 根据部门编码，获取组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);

		switch (operOrgType) {
		case WayBillNoLocusConstant.OPERATE_ORG:
			// 操作部门编码
			dto.setOperateOrgCode(orgCode);
			// 操作部门标杆编码
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(orgCode);

			if (orgEntity != null) {
				//LOGGER.info("标杆编码：{}", orgEntity.getUnifiedCode());
				dto.setUnifiedCode(orgEntity.getUnifiedCode());
			}

			if (orgAdministrativeInfoEntity != null) {
				// 操作部门名称
				dto.setOperateOrgName(orgAdministrativeInfoEntity.getName());
				// 操作部门所在城市编码
				dto.setOperateCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 操作部门所在城市名称
				dto.setOperateCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		case WayBillNoLocusConstant.NEXT_ORG:
			if (orgAdministrativeInfoEntity != null) {
				// 下一站部门编码
				dto.setNextOrgCode(orgCode);
				// 下一站部门名称
				dto.setNextOrgName(orgAdministrativeInfoEntity.getName());
				// 下一站所在城市编码
				dto.setNextCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 下一站所在城市名称
				dto.setNextCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		case WayBillNoLocusConstant.DEST_ORG:
			if (orgAdministrativeInfoEntity != null) {
				// 目的站部门编码
				dto.setDestinationStationOrgCode(orgCode);
				// 目的站部门名称
				dto.setDestinationStationOrgName(orgAdministrativeInfoEntity.getName());
				// 目的站部门所在城市编码
				dto.setDestinationStationCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 目的站部门所在城市名称
				dto.setDestinationStationCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		default:
			return;
		}
	}
	

	/**
	 * @description 配合(快递)根据正单号查询运单号(配合快递魏文文需求)
	 * (non-Javadoc)
	 * @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
	 *                             #queryAirWaybillCreateDept(java.lang.String)
	 * @author 106162-foss-liping
	 * @update 2016年5月16日 上午10:07:04
	 * @version V1.0
	 */
	@Override
	public AirWayBillQueryCreateDeptVO queryAirWaybillCreateDept(String waybillNo) {
		AirWayBillQueryCreateDeptVO ent = queryAirWaybillTrailDao.queryAirWaybillCreateDeptDao(waybillNo);
		return ent;
	}
	
	/**
	 * set\get
	 * @return
	 */
	public IQueryAirWaybillTrailByWaybillNoDao getQueryAirWaybillTrailDao() {
		return queryAirWaybillTrailDao;
	}

	public void setQueryAirWaybillTrailDao(IQueryAirWaybillTrailByWaybillNoDao queryAirWaybillTrailDao) {
		this.queryAirWaybillTrailDao = queryAirWaybillTrailDao;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public ITrackingService getTrackingService() {
		return trackingService;
	}
	public void setTrackingService(ITrackingService trackingService) {
		this.trackingService = trackingService;
	}
	public IAirTrackFlightService getAirTrackFlightService() {
		return airTrackFlightService;
	}
	public void setAirTrackFlightService(
			IAirTrackFlightService airTrackFlightService) {
		this.airTrackFlightService = airTrackFlightService;
	}
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}
