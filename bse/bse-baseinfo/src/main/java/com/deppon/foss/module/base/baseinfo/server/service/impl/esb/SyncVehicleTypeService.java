package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncVehicleTypeRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.oms.inteface.domain.SyncVehicleTypeRequest;
import com.deppon.oms.inteface.domain.VehicleType;

/**
 * 车型每公里费用表基础资料同步 
 * @author 310854
 * @date 2016-4-20
 */
public class SyncVehicleTypeService implements ISyncVehicleTypeService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncVehicleTypeService.class);
	
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_CAR_CHARGE";

	private static final String VERSION = "1.0";
	
	@Override
	public void syncVehicleType(List<VehicleTypeEntity> entitys) {
		// TODO Auto-generated method stub
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncVehicleTypeRequest vehicleTypeRequest = new SyncVehicleTypeRequest();
			List<VehicleType> infos = new ArrayList<VehicleType>();

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			for (VehicleTypeEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				VehicleType info = this.transformationVehicleTypeEntity(entity);
				infos.add(info);

				codes.append(SymbolConstants.EN_COMMA);
				codes.append(entity.getSeq());
			}

			vehicleTypeRequest.getVehicleTypeInfo().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send region_vehicle info to other platform.同步车型每公里费用表基础资料   到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send region_vehicle info to other platform 开始 同步车型每公里费用表基础资料 到其它平台：\n"
					+ new SyncVehicleTypeRequestTrans()
							.fromMessage(vehicleTypeRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, vehicleTypeRequest);

			log.info("end to send region_vehicle info to other platform 结束 同步车型每公里费用表基础资料 到其它平台：\n"
					+ new SyncVehicleTypeRequestTrans()
							.fromMessage(vehicleTypeRequest));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		/*	throw new SynchronousExternalSystemException("同步车型每公里费用表基础资料 到其它平台",
					"同步车型每公里费用表基础资料 到其它平台 发送数据到ESB错误");*/
		}
	}

	/**
	 * 车型每公里费用表转换
	 * @author 310854
	 * @date 2016-4-20
	 */
	private VehicleType transformationVehicleTypeEntity(VehicleTypeEntity vehicleTypeEntity){
		if(null == vehicleTypeEntity){
			throw new BusinessException("vehicleTypeEntity对象为空，转换异常！！");
		}
		VehicleType vehicleType = new VehicleType();
		vehicleType.setActive(vehicleTypeEntity.getActive());
		vehicleType.setCreateTime(vehicleTypeEntity.getCreateDate());
		vehicleType.setCreateUserCode(vehicleTypeEntity.getCreateUser());
		if(null != vehicleTypeEntity.getEachKilometersFees()){
			vehicleType.setEachKilometersFees(vehicleTypeEntity.getEachKilometersFees().toString());
		}
		vehicleType.setId(vehicleTypeEntity.getId());
		vehicleType.setModifyTime(vehicleTypeEntity.getModifyDate());
		vehicleType.setModifyUserCode(vehicleTypeEntity.getModifyUser());
		if(null != vehicleTypeEntity.getSeq()){
			vehicleType.setSeq(vehicleTypeEntity.getSeq().toString());
		}
		if(null != vehicleTypeEntity.getVehicleLength()){
			vehicleType.setVehicleLength(vehicleTypeEntity.getVehicleLength().toString());
		}
		vehicleType.setVehicleLengthCode(vehicleTypeEntity.getVehicleLengthCode());
		vehicleType.setVehicleLengthName(vehicleTypeEntity.getVehicleLengthName());
		vehicleType.setVehicleSort(vehicleTypeEntity.getVehicleSort());
		vehicleType.setVehicleType(vehicleTypeEntity.getVehicleType());
		return vehicleType;
	}
}
