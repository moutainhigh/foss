package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressVehicleRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressVehicleToOMSService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.oms.inteface.domain.ExpressVehicleInfo;
import com.deppon.oms.inteface.domain.ExpressVehiclesDetailInfo;
import com.deppon.oms.inteface.domain.SyncExpressVehicleRequest;

public class SendExpressVehicleToOMSService implements
		ISendExpressVehicleToOMSService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendExpressVehicleToOMSService.class);

	/**
	 * 服务编码
	 */
	private static final String SYNC_EXPRESSVEHICLE_SERVE_CODE = "ESB_FOSS2ESB_EXPRESS_VEHICLE_LIST";
	   private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}
	/**
	 * 版本编号
	 */
	private static final String version = "1.0";

	/**
	 * 同步快递车辆信息到OMS
	 * 
	 * @author 313353
	 * @date 2016-3-21 上午11:50:25
	 */
	@Override
	public void syncExpressVehicleToOMS(List<ExpressVehiclesEntity> entitys,
			List<ExpressVehiclesDetailEntity> detailEntitys) {
		// 声明要传递的值
		SyncExpressVehicleRequest request = new SyncExpressVehicleRequest();

		StringBuilder versionNos = new StringBuilder();
		StringBuilder codes = new StringBuilder();

		List<ExpressVehicleInfo> esbInfos = request.getExpressVehicleInfos();
		List<ExpressVehiclesDetailInfo> detailEsbInfos = request.getExpressVehiclesDetailInfos();
		
		for (ExpressVehiclesEntity fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}

			versionNos.append(SymbolConstants.EN_COMMA);
			versionNos.append(fossEntity.getVersionNo());
			codes.append(SymbolConstants.EN_COMMA);
			codes.append(fossEntity.getVehicleNo());

			esbInfos.add(transFossToEsb(fossEntity));
		}
		
		for(ExpressVehiclesDetailEntity detailEntity : detailEntitys){
			if (detailEntity == null) {
				continue;
			}
			detailEsbInfos.add(vehiclesDetailTransFossToEsb(detailEntity));
		}

		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_EXPRESSVEHICLE_SERVE_CODE);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步快递车辆接口到OMS");
		header.setBusinessDesc2(versionNos.toString());
		long startTime = System.currentTimeMillis();
		EsbErrorLogging log = new EsbErrorLogging();
		try{
		log.setParamenter(new SyncExpressVehicleRequestTrans().fromMessage(request));
		log.setRequestSystem("ESB");
		log.setRequestTime(new Date());
		log.setMethodDesc("同步快递车辆接口到OMS");
		log.setMethodName(this.getClass().getName()+".syncExpressVehicleToOMS");
		LOGGER.info("start send t_bas_express_vehicle info to OMS.FOSS开始发送同步快递车辆接口到OMS 的报文："
				+ new SyncExpressVehicleRequestTrans().fromMessage(request));
		// 发送申请到ESB
		ESBJMSAccessor.asynReqeust(header, request);
		LOGGER.info("end send t_bas_express_vehicle info to other platform.FOSS结束发送同步快递车辆接口到OMS 的报文："
				+ new SyncExpressVehicleRequestTrans().fromMessage(request));
		  long responseTime = System.currentTimeMillis()-startTime; 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				log.setErrorMessage("响应超时");
				log.setResponseTime(responseTime);
				log.setCreateTime(new Date());
				esbErrorLoggingDao.addErrorMessage(log);
			}
		}catch (Exception e) {
			log.setResponseTime(System.currentTimeMillis()-startTime);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.setCreateTime(new Date());
			log.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(log);
			throw new BusinessException("同步快递车辆接口到OMS",e);
		}
		}

	/**
	 * foss对象转ESB
	 * 
	 * @author 313353-foss-qiupeng
	 * @date 2016-3-21 下午1:44:19
	 */
	private ExpressVehicleInfo transFossToEsb(ExpressVehiclesEntity fossEntity) {
		if (fossEntity == null) {
			return null;
		}
		ExpressVehicleInfo esbInfo = new ExpressVehicleInfo();

		// id
		esbInfo.setId(fossEntity.getId());

		// 车牌
		esbInfo.setVehicleNo(fossEntity.getVehicleNo());

		// 车型
		esbInfo.setVehicleLengthCode(fossEntity.getVehicleLengthCode());

		// 所属快递员
		esbInfo.setEmpCode(fossEntity.getEmpCode());

		// 开单营业部
		esbInfo.setOrgCode(fossEntity.getOrgCode());

		// 手机号
		esbInfo.setMobilePhone(fossEntity.getMobilePhone());

		// 创建时间
		esbInfo.setCreateTime(fossEntity.getCreateDate());

		// 更新时间
		esbInfo.setModifyTime(fossEntity.getModifyDate());

		// 是否启用
		esbInfo.setActive(fossEntity.getActive());

		// 创建人
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());

		// 更新人
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());

		// 数据版本
		if (null == fossEntity.getVersionNo()) {
			esbInfo.setVersionNo(0);
		} else {
			esbInfo.setVersionNo(fossEntity.getVersionNo());
		}

		// 备注
		esbInfo.setDescription(fossEntity.getNotes());

		return esbInfo;
	}

	/**
	 * 快递车辆详细信息foss对象转ESB
	 * 
	 * @author 313353-foss-qiupeng
	 * @date 2016-3-21 下午1:44:19
	 */
	private ExpressVehiclesDetailInfo vehiclesDetailTransFossToEsb(ExpressVehiclesDetailEntity fossEntity) {
		if (fossEntity == null) {
			return null;
		}
		ExpressVehiclesDetailInfo esbInfo = new ExpressVehiclesDetailInfo();

		// id
		esbInfo.setId(fossEntity.getId());
		// 所属快递员
		esbInfo.setEmpCode(fossEntity.getEmpCode());
		//行政区域
		esbInfo.setDistrictCode(fossEntity.getAreaCode());
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		esbInfo.setModifyTime(fossEntity.getModifyDate());
		esbInfo.setActive(fossEntity.getActive());
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());
		esbInfo.setVersionNo(fossEntity.getVersionNo());
	    esbInfo.setDescription(fossEntity.getNotes());

		return esbInfo;
	}
}
