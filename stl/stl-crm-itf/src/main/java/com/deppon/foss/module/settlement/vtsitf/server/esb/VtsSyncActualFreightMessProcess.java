package com.deppon.foss.module.settlement.vtsitf.server.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.ActualFreightMessageRequest;
import com.deppon.esb.inteface.domain.vtsbill.ActualFreightMessageResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsSyncWaybillAndActualService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 对接整车平台实际承运信息到FOSS
 * 
 * @ClassName: VtsSyncActualFreightMessProcess
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-5-5 下午5:47:05
 * 
 */
public class VtsSyncActualFreightMessProcess implements IProcess {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(VtsSyncActualFreightMessProcess.class);

	/**
	 * 注入Serive
	 */
	private IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService;
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;
	
	/**
	 * 常量
	 */
	private static final String ESBCODE = "FOSS_ESB2FOSS_SYNC_VTSFOSS_VTSACTUALWAYBILL";
	private static final String SYSTEMTYPE = "VTS";
	private static final String CREATEUSER = "FOSSAUTO";
	private static final String ISSUCCESS_Y = "Y";
	private static final String ISSUCCESS_N = "N";
	
	/**
	 * 同步实际承运表
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午5:47:05
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		logger.info("**********整车同步实际承运表信息到foss开始*************");
		// 获取请求的数据
		ActualFreightMessageRequest request = (ActualFreightMessageRequest) req;

		// 设置返回值
		ActualFreightMessageResponse response = new ActualFreightMessageResponse();
		
		//初始化接口日志实体；
        InterfaceLogEntity interfaceEntity =new InterfaceLogEntity();
		try {
			// 非空判断
			if (request == null) {
				throw new SettlementException("传入参数为空,不能进行新增操作！");
			}

			logger.info(request.getWaybillNo());
			
			/**
			 * 封装接口日志信息实体
			 */
		    interfaceEntity = new InterfaceLogEntity();
			interfaceEntity.setId(UUIDUtils.getUUID());
			interfaceEntity.setWaybillNo(request.getWaybillNo());
			interfaceEntity.setEsbCode(ESBCODE);
			interfaceEntity.setSystemType(SYSTEMTYPE);
			interfaceEntity.setSendContent(JSONObject.toJSONString(request));
			interfaceEntity.setCreateUser(CREATEUSER);
			interfaceEntity.setCreateTime(new Date());
			/**
			 * 封装参数到实际承运表实体
			 */
			ActualFreightEntity entity = new ActualFreightEntity();
			BeanUtils.copyProperties(entity, request);

			/**
			 * 调用接口实现，存在则更新，不存在则插入
			 */
			vtsSyncWaybillAndActualService
					.syncActualFreightMessage(entity);

			/*if (result == 0) {
				throw new SettlementException("插入条数为0！");
			}*/
			// 插入成功
			response.setBussinessId(request.getBussinessId());
			response.setWaybillNo(request.getWaybillNo());
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);
			response.setReason("同步成功！");
			
			// 接口日志添加成功信息
			interfaceEntity.setIsSuccess(ISSUCCESS_Y);
			interfaceEntity.setCorrectLog("vts同步实际承运表至foss成功!");
		} catch (BusinessException e) {
			logger.info(e.getErrorCode());
			response.setBussinessId(request.getBussinessId());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason(e.getErrorCode());
			
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setCorrectLog("vts同步实际承运表至foss失败，失败原因是：" + writer.toString());
		} catch (Exception e) {
			logger.info(e.getMessage());
			response.setBussinessId(request.getBussinessId());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason(e.getMessage());
			
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setCorrectLog("vts同步实际承运表至foss失败，失败原因是：" + writer.toString());
		}finally{
			//插入接口日志表
			boolean flag = this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			if(flag){
				logger.info("接口日志表新增成功！");
			}
		}
		logger.info("**********整车同步实际承运表信息到foss结束*************");
		return response;
	}

	/**
	 * 异常处理
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		logger.info("************数据异常**********");
		return null;
	}
	
	public void setVtsSyncWaybillAndActualService(
			IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService) {
		this.vtsSyncWaybillAndActualService = vtsSyncWaybillAndActualService;
	}

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}
}
