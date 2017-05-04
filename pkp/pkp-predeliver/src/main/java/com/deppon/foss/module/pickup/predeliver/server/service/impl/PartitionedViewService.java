package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionedViewDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionedViewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AddressLabel;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionedViewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionedViewVo;

public class PartitionedViewService implements IPartitionedViewService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartitionedViewService.class);

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/** 
	 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service接口
	 */
	private IVehicleService vehicleService;
	
	/**
	 * 装载率百分比.
	 */
	private static final int LOADRATE = 100;
	
	/** 
	 * 零
	 */
	private static final int ZERO = 0;
	
	/**
	 * 派送单Service
	 */
	private IDeliverbillNewService deliverbillNewService;
	
	public void setDeliverbillNewService(
			IDeliverbillNewService deliverbillNewService) {
		this.deliverbillNewService = deliverbillNewService;
	}

	/**
	 * 分区查看Dao
	 */
	private IPartitionedViewDao partitionedViewDao;

	public void setPartitionedViewDao(IPartitionedViewDao partitionedViewDao) {
		this.partitionedViewDao = partitionedViewDao;
	}

	@Override
	public List<PartitionedViewDto> queryWaybillList(PartitionedViewDto partitionedViewDto) {
		return partitionedViewDao.queryWaybillList(partitionedViewDto);
	}

	@Override
	public Long queryWaybillNum(PartitionedViewDto partitionedViewDto) {
		return partitionedViewDao.queryWaybillNum(partitionedViewDto);
	}

	@Override
	public List<PartitionedViewDto> queryWaybillList_Smallzone(PartitionedViewDto partitionedViewDto) {
		return partitionedViewDao.queryWaybillList_Smallzone(partitionedViewDto);
	}

	@Override
	public List<String> querywaybills_BigZoneAndSmallZone(
			PartitionedViewDto partitionedViewDto) {
		return partitionedViewDao.querywaybills_BigZoneAndSmallZone(partitionedViewDto);
	}

	@Override
	public List<WaybillToArrangeDto> queryWaybillDetails(
			PartitionedViewVo partitionedViewVo) {
		return partitionedViewDao.queryWaybillDetails(partitionedViewVo);
	}

	@Override
	public List<AddressLabel> queryGisWaybillNoDetail(
			PartitionedViewVo partitionedViewVo) {
		return partitionedViewDao.queryGisWaybillNoDetail(partitionedViewVo);
	}
	
	public DeliverbillEntity LoadingRate(DeliverbillEntity entity)
	{
		try
		{
			// 装载率(重量/体积)
			VehicleAssociationDto vehicleAssociationDto = this.vehicleService.queryVehicleAssociationDtoByVehicleNo(entity.getVehicleNo());
			//若装载率(重量/体积)不为空
			if (vehicleAssociationDto != null)
			{
				String vehicleLoad = null;
				String vehicleVolume = null;
				// 装载率(重量)
				BigDecimal vehicleLoadRate = BigDecimalOperationUtil.div(entity.getWeightTotal(), vehicleAssociationDto.getVehicleDeadLoad()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleDeadLoad().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_1000)),2);
				//如果装载率(重量)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleLoadRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleLoad = "0%";
				}
				else
				{
					vehicleLoad = (BigDecimalOperationUtil.mul(vehicleLoadRate, new BigDecimal(LOADRATE), 0)).toString()+"%";
				}
				// 装载率(体积)
				BigDecimal vehicleVolumeRate = BigDecimalOperationUtil.div(entity.getVolumeTotal(), vehicleAssociationDto.getVehicleSelfVolume()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleSelfVolume(),2);
				
				//如果装载率(体积)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleVolumeRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleVolume = "0%";
				}
				else
				{
					vehicleVolume = (BigDecimalOperationUtil.mul(vehicleVolumeRate, new BigDecimal(LOADRATE), 0)).toString()+"%";
				}
				//设置装载率(重量/体积)
				entity.setLoadingRate(joinString(vehicleVolume,vehicleLoad));
			}
			return entity;
		} catch (LeasedDriverException le)
		{	
			//日志记录
			LOGGER.error("error", le);
			return null;
		}
	}
	
	/**
	 * 拼接页面信息.
	 *
	 * @param objects 
	 * the objects
	 * @return the string
	 * @author 
	 * @date 
	 */
	private String joinString(Object... objects)
	{
		StringBuffer sb = new StringBuffer();
		for (Object o : objects)
		{
			if (o != null && StringUtil.isNotEmpty(o.toString()))
			{
				sb.append(o.toString()).append(DeliverbillConstants.SPLIT_CHAR);
			}
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
	}

	@Override
	public PartitionedViewDto queryTotal(PartitionedViewDto partitionedViewVo) {
		return partitionedViewDao.queryTotal(partitionedViewVo);
	}
}
