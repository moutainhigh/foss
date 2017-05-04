package com.deppon.foss.module.settlement.ecsitf.server.jms;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.esb.inteface.domain.ecs.EcsFossComplementResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 快递补码接口
 * @author foss-231434-bieyexiong
 * @date 2016-5-31 10:21
 */
public class EcsComplementFunctionProcessor implements IProcess {

	//记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(EcsComplementFunctionProcessor.class);

	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";

	//补码接口
	private IComplementFunctionService complementFunctionService;
	
	private ILogEcsFossService logEcsFossService;
	
	//同步运单信息接口
	private IEcsSendWaybillService ecsSendWaybillService;
	/**
	 * 补码
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-31 10:35
	 */
	@Override
	public Object process(Object request) throws ESBBusinessException {
		LOGGER.info("ECS-FOSS补码--start");
		Date date = new Date();
		EcsFossComplementRequest req = (EcsFossComplementRequest)request;
		//响应
		EcsFossComplementResponse res = null;
		//响应状态1代表成功，0代表失败
		Boolean flag = null;
		if(req == null){
			LOGGER.info("ECS-FOSS补码--endt");
			res = this.getResponse(FAILURE, "结算补码失败：补码参数为空！",req);
		} else {
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
				res = this.getResponse(FAILURE, "结算补码失败：补码员信息为空！",req);
			} else {
				//设置补码信息
				SettlementComplementBillDto dto = this.getComplementDto(req);
				CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
				try{
					//补码
					complementFunctionService.ecsComplementFunctionWriteBackAndCreateReceivable(dto, currentInfo);
		
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
					res = this.getResponse(SUCCESS, "结算补码成功！",req);
					flag = true;
				}catch(BusinessException e){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
					res = this.getResponse(FAILURE, "结算补码失败：" +e.getErrorCode() != null ? e.getErrorCode() : e.getMessage(),req);
					flag = false;
				}catch(Exception e){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
					res = this.getResponse(FAILURE, "结算补码失败：系统异常，请重新操作，以校验财务单据！："+e,req);
					flag = false;
				}
			}
		}
		//记录异步接口日志
		try{
			//保存日志
			logEcsFossService.setLog(req, res, "FOSS_ESB2FOSS_COMPLEMENT_FUNCTION", req.getWaybillNo(), flag, date);
		}catch(Exception e){
			LOGGER.info("结算补码接口插入失败："+e);
		}
		try{
			//修改运单到达部门信息
			ecsSendWaybillService.updateWaybillByComplement(req);
		}catch(Exception e){
			LOGGER.error("运单号：" + req.getWaybillNo() + "补码修改运单信息失败！" + e.toString());
		}
		return res;
	}

	/**
	 * 获取补码参数
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-31 10:30
	 */
	private SettlementComplementBillDto getComplementDto(EcsFossComplementRequest req){
		SettlementComplementBillDto dto = new SettlementComplementBillDto();
		
		//运单号
		dto.setWaybillNo(req.getWaybillNo());
		//最新提货网点的到达部门编码
		dto.setDestOrgCode(req.getDestOrgCode());
		//最新提货网点的到达部门名称
		dto.setDestOrgName(req.getDestOrgName());
		//用于区分自由网点补码，还是快递代理补码 自由网点 Y 快递代理 N
		dto.setIsFreeSite(req.getIsFreeSite());
		//开单时间
		dto.setBillTime(req.getBillTime());
		//收货部门
		dto.setReceiveOrgCode(req.getReceiveOrgCode());
		//来源系统ECS
		dto.setSourceSystem(SettlementDictionaryConstants.SOURCE_SYSTEM_ECS);
		return dto;
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 17:27
	 */
	private EcsFossComplementResponse getResponse(String result,String message,EcsFossComplementRequest req){
		EcsFossComplementResponse res = new EcsFossComplementResponse();
		
		res.setResult(result);
		res.setMessage(message);
		if(req != null){
			//运单号
			res.setWaybillNo(req.getWaybillNo());
			//目的外场
			res.setDestTransferstationCode(req.getDestTransferstationCode());
			//提货网点
			res.setPkpOrgCode(req.getPkpOrgCode());
			//返回给悟空的运单号，转寄时，为新单号
			res.setBillNo(req.getBillNo());
		}
		return res;
	}
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.info("补码异常！");
		return null;
	}

	public void setComplementFunctionService(
			IComplementFunctionService complementFunctionService) {
		this.complementFunctionService = complementFunctionService;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

	public void setEcsSendWaybillService(
			IEcsSendWaybillService ecsSendWaybillService) {
		this.ecsSendWaybillService = ecsSendWaybillService;
	}

}
