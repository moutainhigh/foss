package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.domain.foss2qms.ErrRequestParam;
import com.deppon.esb.pojo.domain.foss2qms.ErrorMainEntity;
import com.deppon.esb.pojo.domain.foss2qms.LDErrSubNoWaybillEntity;
import com.deppon.esb.pojo.transformer.json.ClearanceErrorReportingRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.IClearanceErrorReportingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 净空差错上报
 * 
 * @author
 * @date 
 */
public class ClearanceErrorReportingService implements IClearanceErrorReportingService {

	private static final Logger log = LoggerFactory
			.getLogger(ClearanceErrorReportingService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_ERROR_AUTO_REPORT_SYNC";
	private static final String VERSION = "1.0";
	 private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}
	/**
	 * 
	 * 净空差错上报
	 * 
	 * @author 
	 * @date 
	 */

	@Override
	public void syncClearanceError(Map<String, Object> entitys) {

		if (null == entitys ) {
			throw new BusinessException("传入的对象为空");
		}
		ErrRequestParam request = ConvertEsbEntity(entitys);
		
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("净空差错上报");
		accessHeader.setVersion(VERSION);
		long startTime = System.currentTimeMillis();
		EsbErrorLogging erlog = new EsbErrorLogging();
		try {
			erlog.setParamenter(new ClearanceErrorReportingRequestTrans().fromMessage(request));
			erlog.setRequestSystem("ESB");
			erlog.setRequestTime(new Date());
			erlog.setMethodDesc("净空差错上报");
			erlog.setMethodName(this.getClass().getName()+".syncClearanceError");
			log.info("开始调用 净空差错上报：\n"
					+ new ClearanceErrorReportingRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			long responseTime = (System.currentTimeMillis()-startTime); 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				erlog.setCreateTime(new Date());
				erlog.setErrorMessage("响应超时");
				erlog.setResponseTime(responseTime);
				esbErrorLoggingDao.addErrorMessage(erlog);
			}
			log.info("结束调用 净空差错上报：\n"+ new ClearanceErrorReportingRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			//esb发生异常未进行处理？？？？？是否要修改
			e.printStackTrace();
			log.error(e.getMessage(), e);
			erlog.setResponseTime(System.currentTimeMillis()-startTime);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			erlog.setCreateTime(new Date());
			e.printStackTrace(pw);
			erlog.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(erlog);
		}
		log.info(" 净空差错上报 \n");
		
	}
	//转换成发送对象
	private ErrRequestParam ConvertEsbEntity(Map<String, Object> entitys) {
		ErrRequestParam errRequestParam = new ErrRequestParam();
		errRequestParam.setErrCategoty("L");
		errRequestParam.setErrorTypeId("L201503250051");
		LDErrSubNoWaybillEntity ldsubNoInfo = new LDErrSubNoWaybillEntity();
		ErrorMainEntity mainInfo = new ErrorMainEntity();
		LeasedTruckEntity oldEntity = (LeasedTruckEntity) entitys.get("old");
		LeasedTruckEntity newEntity = (LeasedTruckEntity) entitys.get("new");
		CurrentInfo user = (CurrentInfo) entitys.get("userInfo");
		ldsubNoInfo.setCarCode(newEntity.getVehicleNo());
		ldsubNoInfo.setFormerClearanceVal(oldEntity.getSelfVolume().toString());
		ldsubNoInfo.setFormerMeasureEmpCode(oldEntity.getMeasureEmployeeCode());
		ldsubNoInfo.setFormerMeasureEmpName(oldEntity.getMeasureEmployeeName());
		ldsubNoInfo.setFormerMeasureManagerCode(oldEntity.getMeasureManagerCode());
		ldsubNoInfo.setFormerMeasureManagerName(oldEntity.getMeasureManagerName());
		ldsubNoInfo.setFormerMeasureManagerSupCode(oldEntity.getMeasureSeniorManagerCode());
		ldsubNoInfo.setFormerMeasureManagerSupName(oldEntity.getMeasureSeniorManagerName());
		ldsubNoInfo.setIncident("车牌号"+newEntity.getVehicleNo()+"，原净空"+oldEntity.getSelfVolume().toString()+"，新净空"+newEntity.getSelfVolume().toString()+"，按照文件标准《营15-057外请车管理规定》，超出净空误差范围5%将上报差错，并给予相关责任人奖惩。");
		ldsubNoInfo.setLaterClearanceVal(newEntity.getSelfVolume().toString());
		ldsubNoInfo.setLaterMeasureEmpCode(newEntity.getMeasureEmployeeCode());
		ldsubNoInfo.setLaterMeasureEmpName(newEntity.getMeasureEmployeeName());
		ldsubNoInfo.setLaterMeasureManagerCode(newEntity.getMeasureManagerCode());
		ldsubNoInfo.setLaterMeasureManagerName(newEntity.getMeasureManagerName());
		ldsubNoInfo.setLaterMeasureManagerSupCode(newEntity.getMeasureSeniorManagerCode());
		ldsubNoInfo.setLaterMeasureManagerSupName(newEntity.getMeasureSeniorManagerName());
		errRequestParam.setLdsubNoInfo(ldsubNoInfo);
		mainInfo.setDel("2");
		mainInfo.setDocStandarId(newEntity.getSelfVolume().compareTo(oldEntity.getSelfVolume())==1?"3702":"3703");
		mainInfo.setErrorCategory("1");
		mainInfo.setErrorTypeId("L201503250051");
		mainInfo.setLastAvaibleFBTime("");
		mainInfo.setNamespace("com.deppon.qms.module.error.ltl.domain.LDErrSubNoWaybillEntity");
		mainInfo.setNeedFeedback("2");
		mainInfo.setRepDeptCode(user.getDept().getUnifiedCode());
		mainInfo.setRepDeptName(user.getDept().getName());
		mainInfo.setRepEmpcode(user.getEmpCode());
		mainInfo.setRepEmpJob(user.getUser().getTitle());
		mainInfo.setRepEmpName(user.getEmpName());
		mainInfo.setRepSource("2");
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
		mainInfo.setRepSystem("FOSS");
		mainInfo.setRepTime(sdf.format(new Date()));
		mainInfo.setWayBillType("-1");
		errRequestParam.setMainInfo(mainInfo);
		errRequestParam.setReturnResult(false);
		return errRequestParam;
	}
	}
	

	
	
	
	

