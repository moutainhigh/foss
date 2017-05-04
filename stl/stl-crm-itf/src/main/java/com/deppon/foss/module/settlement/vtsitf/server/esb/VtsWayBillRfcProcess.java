package com.deppon.foss.module.settlement.vtsitf.server.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.SendWayBillRfcRequest;
import com.deppon.esb.inteface.domain.vtsbill.SendWayBillRfcResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsSyncWaybillAndActualService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * vts同步运单更改表至foss异步接口
 */
public class VtsWayBillRfcProcess implements IProcess {

    /**
     * 日志记录
     */
    private static final Logger logger = LogManager.getLogger(VtsWayBillRfcProcess.class);
    
	/**
	 * 注入同步更改单Serive
	 */
	private IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService;
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;
	
	/**
	 * 常量
	 */
	private static final String ESBCODE = "FOSS_ESB2FOSS_SYNC_VTSFOSS_VTSWAYBILLRFC";
	private static final String SYSTEMTYPE = "VTS";
	private static final String CREATEUSER = "306579";
	private static final String ISSUCCESS_Y = "Y";
	private static final String ISSUCCESS_N = "N";

    /**
     * vts同步运单更改表至foss
     * @author 306579--guoxinru
     * @date 2016-5-17
     * @see com.deppon.esb.core.process.IProcess#process(Object)
     */
    @Override
    public Object process(Object obj) throws ESBBusinessException {
    	logger.info("**********整车同步运单更改信息到foss开始*************");
        // 获取接口参数
        SendWayBillRfcRequest request = (SendWayBillRfcRequest) obj;
        // 接口返回信息
        SendWayBillRfcResponse response = new SendWayBillRfcResponse();
        //初始化接口日志实体；
        InterfaceLogEntity interfaceEntity =new InterfaceLogEntity();
        try {
			// 非空判断
			if (request == null) {
				throw new SettlementException("传入参数为空,不能进行新增操作！");
			}
			
			if(StringUtils.isEmpty(request.getBusinessId())){
				logger.error("vts传入foss业务id为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss业务id为空！");
			}
			
			if (StringUtils.isEmpty(request.getWaybillNo())) {
				logger.error("vts传入foss运单号为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss运单号为空！");
			}
			
			if (StringUtils.isEmpty(request.getRfcSource())) {
				logger.error("vts传入foss变更来源为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss变更来源为空！");
			}
			
			if (StringUtils.isEmpty(request.getRfcType())) {
				logger.error("vts传入foss变更类型为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss变更类型为空！");
			}
			
			if (StringUtils.isEmpty(request.getRfcReason())) {
				logger.error("vts传入foss变更原因为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss变更原因为空！");
			}
			
			if (StringUtils.isEmpty(request.getDraftOrgName())) {
				logger.error("vts传入foss起草部门为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss起草部门为空！");
			}
			
			if (StringUtils.isEmpty(request.getDraftOrgCode())) {
				logger.error("vts传入foss起草部门编码为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss起草部门编码为空！");
			}
			
			if (StringUtils.isEmpty(request.getDrafter())) {
				logger.error("vts传入foss起草人为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss起草人为空！");
			}
			
			if (StringUtils.isEmpty(request.getDrafterCode())) {
				logger.error("vts传入foss起草人编号为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss起草人编号为空！");
			}
			
			if (StringUtils.isEmpty(request.getStatus())) {
				logger.error("vts传入foss更改单状态为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss更改单状态为空！");
			}

			/**
			 * 封装参数到运单实体
			 */
			WaybillRfcEntity entity = new WaybillRfcEntity();
			BeanUtils.copyProperties(request, entity);
			entity.setId(UUIDUtils.getUUID());
			entity.setDraftTime(new Date());
			entity.setOperateTime(new Date());
			
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
			 * 调用接口实现
			 */
			int result = vtsSyncWaybillAndActualService.syncWayBillRfcMessage(entity);
			if (result == 0) {
				logger.error("*************************插入/更新更改单条数为0;运单号："+request.getWaybillNo());
				throw new SettlementException("插入/更新更改单条数为0;运单号:"+request.getWaybillNo());
			}
			// 插入/更新成功
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);
			response.setReason("vts同步运单更改表至foss成功!");
			// 接口日志添加成功信息
			interfaceEntity.setIsSuccess(ISSUCCESS_Y);
			interfaceEntity.setCorrectLog("vts同步运单更改表至foss成功!");
			logger.info("vts同步运单更改表至foss成功！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo());
		} catch (SettlementException e) {
			logger.info("vts同步运单更改表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getErrorCode());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步运单更改表至foss失败！"+e.getErrorCode());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess("N");
			interfaceEntity.setCorrectLog("vts同步运单更改表至foss失败！：" + writer.toString());
		} catch (BusinessException e) {
			logger.info("vts同步运单更改表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getErrorCode());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步运单更改表至foss失败！"+e.getErrorCode());
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setErrorLog("vts同步运单更改表至foss失败！:" + writer.toString());
		} catch (Exception e) {
			logger.info("vts同步运单更改表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getMessage());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步运单更改表至foss失败！"+e.getMessage());
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setErrorLog("vts同步运单更改表至foss失败！:"+e.getMessage());
		}finally{
			//插入接口日志表
			boolean flag = this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			if(flag){
				logger.info("接口日志表新增成功！");
			}else{
				logger.info("接口日志表新增失败！");
			}
		}
		logger.info("**********整车同步更改单信息到foss结束*************");
        return response;
    }

    /**
     * 错误处理
     *
     * @author 306579--guoxinru
     * @see com.deppon.esb.core.process.IProcess#errorHandler(Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
        // 错误日志
    	logger.error(req.getClass().getName() + "vts同步运单更改表至foss错误！");
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
