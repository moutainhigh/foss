package com.deppon.foss.module.generalquery.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.shared.domain.CargoTraceInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.QryTraceInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.TraceInfoEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillLocusDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;

/**
 * 货物追踪查询服务类
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-13 上午11:27:53
 */
public class CrgTraceService implements IBusinessService<CargoTraceInfoEntity, QryTraceInfoEntity> {
	private static final Log LOG = LogFactory.getLog(CrgTraceService.class);
	
	private IWayBillNoLocusService wayBillNoLocusService;
	
	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService) {
		this.wayBillNoLocusService = wayBillNoLocusService;
	}
	
	/**
	 * 解析包体
	 */
	@Override
	public QryTraceInfoEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QryTraceInfoEntity qryTraceInfoEntity = JsonUtil.parseJsonToObject(QryTraceInfoEntity.class, asyncMsg.getContent());
		return qryTraceInfoEntity;
	}
	
	/**
	 * 服务方法
	 */
	@Override
	public CargoTraceInfoEntity service(AsyncMsg asyncMsg,
			QryTraceInfoEntity qryTraceInfoEntity) throws PdaBusiException {
		CargoTraceInfoEntity cargoTraceInfoEntity = null;
		try {
			//验证数据有效性
			this.validate(qryTraceInfoEntity);
			//PDA查询货物接口 
			WayBillLocusDto billLocusDto = wayBillNoLocusService.getWayBillLocusForPda(qryTraceInfoEntity.getWblCode());
			//封装实体
			cargoTraceInfoEntity = wrapCargoTraceInfo(billLocusDto);
			return cargoTraceInfoEntity;
		} catch (BusinessException e) {
			LOG.error("货物追踪查询服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	
	/**
	 * 验证数据有效性
	 * @param qryTraceInfoEntity
	 */
	private void validate(QryTraceInfoEntity qryTraceInfoEntity) {
		Argument.notNull(qryTraceInfoEntity, "QryTraceInfoEntity");
		Argument.hasText(qryTraceInfoEntity.getWblCode(), "QryTraceInfoEntity.wblCode");
	}
	
	/**
	 * 封装实体
	 * @param billLocusDto
	 * @return
	 */
	private CargoTraceInfoEntity wrapCargoTraceInfo(WayBillLocusDto billLocusDto){
		CargoTraceInfoEntity cargoTraceInfoEntity = new CargoTraceInfoEntity();
		//运单号
		cargoTraceInfoEntity.setWblCode(billLocusDto.getWaybillNo());
		/*
		 * wwn 3013-05-31 16:07
		 * */
		//货物名称
		cargoTraceInfoEntity.setCrgName(StringUtils.convert(billLocusDto.getGoodsName()));
		//目的城市
		cargoTraceInfoEntity.setDestinationCity(billLocusDto.getTargetOrgCode());
		//提货方式
		cargoTraceInfoEntity.setTakeType(billLocusDto.getReceiveMethod());
		//承运方式
		cargoTraceInfoEntity.setTransType(billLocusDto.getProductCode());
		//货物件数
		cargoTraceInfoEntity.setCrgPieces(billLocusDto.getGoodsQtyTotal());
		double crgvolume = 0;
		if(billLocusDto.getGoodsVolumeTotal() != null){
			crgvolume = billLocusDto.getGoodsVolumeTotal().doubleValue();
		}
		//货物体积
		cargoTraceInfoEntity.setCrgVolume(crgvolume);
		double crgWeight = 0;
		if(billLocusDto.getGoodsWeightTotal() != null){
			crgWeight = billLocusDto.getGoodsWeightTotal().doubleValue();
		}
		//货物重量
		cargoTraceInfoEntity.setCrgWeight(crgWeight);
		List<WayBillNoLocusDTO> wayBillNoLocusDTOs = billLocusDto.getWayBillNoLocusDTOList();
		List<TraceInfoEntity> traceInfos = null;
		if(wayBillNoLocusDTOs != null && !wayBillNoLocusDTOs.isEmpty()){
			traceInfos = new ArrayList<TraceInfoEntity>();
			//TraceInfo , 记录货物状态
			for (WayBillNoLocusDTO dto : wayBillNoLocusDTOs) {
				TraceInfoEntity traceInfoEntity  = new TraceInfoEntity();
				traceInfoEntity.setCrgStatus(dto.getNotes());
				traceInfoEntity.setOperTime(dto.getOperateTime());
				traceInfos.add(traceInfoEntity);
			}
		}
		cargoTraceInfoEntity.setTraceInfos(traceInfos);
		return cargoTraceInfoEntity;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_CRG_TRCE_QRY.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}
