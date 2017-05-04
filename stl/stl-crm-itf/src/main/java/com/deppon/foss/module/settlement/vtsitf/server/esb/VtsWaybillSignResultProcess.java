package com.deppon.foss.module.settlement.vtsitf.server.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillSignResultRequest;
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillSignResultResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsEffectPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsSyncWaybillAndActualService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;


/**
 * vts同步签收结果表至foss异步接口
 */
public class VtsWaybillSignResultProcess implements IProcess {

    /**
     * 日志记录
     */
    private static final Logger logger = LogManager.getLogger(VtsWaybillSignResultProcess.class);
    
	/**
	 * 注入Serive
	 */
	private IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService;
	
	private IVtsEffectPayableService  vtsEffectPayableService;
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;
	
	/**
	 * 常量
	 */
	private static final String ESBCODE = "FOSS_ESB2FOSS_SYNC_VTSFOSS_VTSWAYBILLRESULT";
	private static final String SYSTEMTYPE = "VTS";
	private static final String CREATEUSER = "306579";
	private static final String ISSUCCESS_Y = "Y";
	private static final String ISSUCCESS_N = "N";
    

	/**
     * vts同步签收结果表至foss
     * @author 306579--guoxinru
     * @date 2016-5-23
     * @see com.deppon.esb.core.process.IProcess#process(Object)
     */
    @Override
    public Object process(Object obj) throws ESBBusinessException {
    	logger.info("**********整车同步签收结果信息到foss开始*************");
        // 获取接口参数
    	VtsWaybillSignResultRequest request = (VtsWaybillSignResultRequest) obj;
        // 接口返回信息
    	VtsWaybillSignResultResponse response = new VtsWaybillSignResultResponse();
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
			
			if (StringUtils.isEmpty(request.getSignSituation())) {
				logger.error("vts传入foss签收情况为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss签收情况为空！");
			}
			
			if (StringUtils.isEmpty(request.getSignGoodsQty())) {
				logger.error("vts传入foss签收件数为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss签收件数为空！");
			}
			
			if (StringUtils.isEmpty(request.getActive())) {
				logger.error("vts传入foss是否有效为空！运单号："+request.getWaybillNo());
				throw new SettlementException("vts传入foss是否有效为空！");
			}

			/**
			 * 封装参数到运单实体
			 */
			WaybillSignResultEntity entity = new WaybillSignResultEntity();
			BeanUtils.copyProperties(entity, request);
			
			//设置id
			entity.setId(UUIDUtils.getUUID());
			//设置是否初始化
			entity.setIsInit("N");
			//设置签收时间
			entity.setSignTime(new Date());
			entity.setCreateTime(new Date());
			//设置修改时间
			Timestamp buydate=Timestamp.valueOf("2999-12-31 00:00:00");
			entity.setModifyTime(buydate);
			//新建UserEntiy实体
			UserEntity user = new UserEntity();
			user.setEmpCode(entity.getCreatorCode());
			user.setEmpName(entity.getCreator());
			//新建OrgAdministrativeInfoEntity实体
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			dept.setCode(entity.getCreateOrgCode());
			dept.setName(entity.getCreateOrgName());
			//对CurrentInfo执行构造函数，将值设置到CurrentInfo中
			CurrentInfo currentInfo = new CurrentInfo(user,dept); 
			
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
			 * @author 218392 zhangyongxue
			 * @date 2016-06-24 17:07:30
			 * 说明:(1)VTS的业务，部分签收之后，不允许反签收；考虑的业务是反签收时候他们页面扛不住
			 * (2)下面的逻辑是首先去签收结果表中查询根据单号+active为Y，如果查询结果为空继续走原来的；
			 * (3)如果有再判断签收状态是部分签收还是全部签收: SIGN_STATUS_ALL 全部签收; SIGN_STATUS_PARTIAL 部分签收
			 * (4)如果查询出来的数据签收状态是部分签收，把上次签收的记录置成N，新增的为Y
			 * (5)如果查询出来的数据签收状态是全部签收，插入一条为N的记录(这是考虑到异步接口，接收到同一个单号的签收数据不知道谁先谁后)
			 */
			VTSWaybillSignResultEntity resultEntity = new VTSWaybillSignResultEntity();
			resultEntity.setWaybillNo(request.getWaybillNo());
			resultEntity.setActive("Y");
			VTSWaybillSignResultEntity querySignResultEntity = vtsSyncWaybillAndActualService.queryWaybillSignResultByNo(resultEntity);
			//如果查询结果为空，那就说明，FOSS签收结果表中还没有签收记录，执行原来的操作不变
			if(querySignResultEntity == null){
				/**
				 * 调用接口同步签收结果表及新增财务签收表
				 */
				vtsSyncWaybillAndActualService.syncWaybillSignResult(entity);
				
				/**
				 * 调用新增接口,生效装卸费,代收货款应付单；
				 * @author 310970 caopeng
				 * @date 2016-6-13
				 */
				vtsEffectPayableService.EffectPayableByVtsInfo(entity, currentInfo);
				
			}else{//如果查询结果不为空走esle，里面再继续判断
				
				vtsSyncWaybillAndActualService.handlerSignResultWaybill(querySignResultEntity,entity);
				
			}
			
			// 插入成功
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);
			response.setReason("vts同步签收结果表至foss成功！");
			// 接口日志添加成功信息
			interfaceEntity.setIsSuccess(ISSUCCESS_Y);
			interfaceEntity.setCorrectLog("vts同步签收结果表至foss成功!");
			logger.info("vts同步签收结果表至foss成功！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo());
		} catch (SettlementException e) {
			logger.info("vts同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getErrorCode());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步签收结果表至foss失败！" + e.getErrorCode());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess("N");
			interfaceEntity.setCorrectLog("vts同步签收结果表至foss失败，报错信息是：" + writer.toString());
		}  catch (BusinessException e) {
			logger.info("vts同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getErrorCode());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步签收结果表至foss失败！" + e.getErrorCode());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setErrorLog("vts同步签收结果表至foss失败！" + writer.toString());
		} catch (Exception e) {
			logger.info("vts同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getMessage());
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("vts同步签收结果表至foss失败！"+e.getMessage());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			// 接口日志添加失败信息
			interfaceEntity.setIsSuccess(ISSUCCESS_N);
			interfaceEntity.setErrorLog("vts同步签收结果表至foss失败！" + writer.toString());
		}finally{
			//插入接口日志表
			boolean flag = this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			if(flag){
				logger.info("接口日志表新增成功！");
			}else{
				logger.info("接口日志表新增失败！");
			}
		}
		logger.info("**********整车同步签收结果表信息到foss结束*************");
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
    	logger.error(req.getClass().getName() + "vts同步签收结果表信息至foss错误！");
        return null;
    }

	public void setVtsSyncWaybillAndActualService(
			IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService) {
		this.vtsSyncWaybillAndActualService = vtsSyncWaybillAndActualService;
	}
	public void setVtsEffectPayableService(
			IVtsEffectPayableService vtsEffectPayableService) {
		this.vtsEffectPayableService = vtsEffectPayableService;
	}

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}
	
	


}
