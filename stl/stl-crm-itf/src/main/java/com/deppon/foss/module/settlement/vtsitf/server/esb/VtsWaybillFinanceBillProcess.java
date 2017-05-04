package com.deppon.foss.module.settlement.vtsitf.server.esb;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillFinanceBillRequest;
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillFinanceBillResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IVtsWaybillFinanceBillService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * VTS开单生成财务单据异步接口
 * @author 218392 张永雪  FOSS结算开发组
 * @date 2016-05-13 08:40:00
 *
 */

public class VtsWaybillFinanceBillProcess implements IProcess{
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(VtsWaybillFinanceBillProcess.class);

	/**
	 * 注入IVtsWaybillFinanceBillService
	 */
	private IVtsWaybillFinanceBillService vtsWaybillFinanceBillService;
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;
	
	/**
	 * 对应VTS开单生成财务单据实现service(除了开单银行卡)
	 * @218392 张永雪 FOSS结算开发组
	 * @date 2016-05-13 08:43:20
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		//记录日志开始
		logger.info("VTS开单非银行卡调用FOSS结算生成财务单据开始...");
		//获取请求的参数实体
		VtsWaybillFinanceBillRequest vtsRequest = (VtsWaybillFinanceBillRequest)req;
		//获取返回的参数实体
		VtsWaybillFinanceBillResponse vtsResponse = new VtsWaybillFinanceBillResponse();
		//校验参数
		if(vtsRequest == null || vtsRequest.getWaybillEntity() == null ||
				StringUtils.isEmpty(vtsRequest.getWaybillEntity().getWaybillNo())){
			logger.info("VTS传入FOSS结算开单参数为空或者传入运单号为空...");
			vtsResponse.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
			vtsResponse.setReason("VTS传入FOSS结算开单请求参数为空，调用接口失败");			
		}
		//获取传递的信息
		String businessId = vtsRequest.getBusinessId();
		String waybillNo = vtsRequest.getWaybillEntity().getWaybillNo(); 
		String waybillID = vtsRequest.getWaybillEntity().getId();//新运单或者新更改单的运单ID
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
		logger.info("VTS传入FOSS结算运单ID是:" + waybillID);
		String openBillType = vtsRequest.getOpenBillType();//运单操作类型：1.新增：add 2.更改：update 3.作废：disable 4.中止：stop
		logger.info("JMS异步接口调试成功！传入的单号为："+waybillNo+",操作类型为:"+openBillType);
		try{
			WaybillPickupInfoDto waybillPickupInfoDto = new WaybillPickupInfoDto();
			/**
			 * CurrentInfo currentInfo 信息实体转换
			 */
			//1.新建UserEntiy实体
			String empCode = vtsRequest.getCurrentInfo().getEmpCode();
			String empName = vtsRequest.getCurrentInfo().getEmpName();
			
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setEmpCode(empCode);
			employeeEntity.setEmpName(empName);
			
			UserEntity user = new UserEntity();
			user.setEmployee(employeeEntity);
			user.setEmpCode(empCode);
			user.setEmpName(empName);
			
			/**
			 * 定义插入log日志插入结算VTS专门的日志表
			 * stl.t_stl_bill_interfacelog
			 */
			interfaceEntity.setId(UUIDUtils.getUUID());
			interfaceEntity.setWaybillNo(vtsRequest.getWaybillEntity().getWaybillNo());
			interfaceEntity.setEsbCode("FOSS_ESB2FOSS_SYNC_VTSFOSS_NOCARDBILL");//服务端编码:开单更改单财务单据
			interfaceEntity.setSystemType("VTS");
			interfaceEntity.setSendContent(JSONObject.toJSONString(vtsRequest));
			interfaceEntity.setCreateUser("218392");
			interfaceEntity.setModifyUser("开单/更改单JMS接口");
			interfaceEntity.setCreateTime(new Date());
			
			//2.新建OrgAdministrativeInfoEntity实体
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			dept.setCode(vtsRequest.getCurrentInfo().getCurrentDeptCode());
			dept.setName(vtsRequest.getCurrentInfo().getCurrentDeptName());
			//3.对CurrentInfo执行构造函数，将值设置到CurrentInfo中
			CurrentInfo currentInfo = new CurrentInfo(user,dept);
			//4.同名属性copy
			BeanUtils.copyProperties(waybillPickupInfoDto, vtsRequest.getWaybillEntity());
			BeanUtils.copyProperties(waybillPickupInfoDto, vtsRequest);
			//运单新增
			if("add".equals(vtsRequest.getOpenBillType())){
				//foss内部service业务逻辑的处理
				vtsWaybillFinanceBillService.addWaybillFinanceBill(waybillPickupInfoDto, currentInfo);
				interfaceEntity.setCorrectLog("VTS开单同步至FOSS成功!");
			}else{//运单更改、作废、中止均走以下else
				//校验原运单号是否为空
				String oldWaybillNoCheck = vtsRequest.getWaybillEntity().getOldWaybillNo();
				if(StringUtils.isEmpty(oldWaybillNoCheck)){
					throw new SettlementException("运单发更改时候而且更改的是原单号，原单号为空！VTS没有传递过来！");
				}
				//调用运单变更、中止、作废service接口
				vtsWaybillFinanceBillService.modifyWaybillFinanceBill(waybillPickupInfoDto, currentInfo);
				interfaceEntity.setCorrectLog("VTS更改单同步至FOSS成功!");
			}
			//处理成功返回实体属性
			vtsResponse.setBusinessId(businessId);
			vtsResponse.setResult(1);//处理失败标志：1是成功；2是失败
			vtsResponse.setWaybillNo(waybillNo);
			interfaceEntity.setIsSuccess("Y");
			//记录成功日志
			logger.info("VTS开单生成财务单据处理成功...");
		}catch (SettlementException se) {
			logger.error("\n运单号:"+waybillNo+"VTS生成FOSS结算财务单据错误," + se.getErrorCode(), se);
			vtsResponse.setBusinessId(businessId);
			vtsResponse.setResult(2);//处理失败标志：1是成功；2是失败
			vtsResponse.setWaybillNo(waybillNo);
			vtsResponse.setReason("运单号:"+waybillNo+"VTS生成FOSS结算财务单据错误,"+se);//失败原因
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			se.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("VTS开单更改单报错：" + writer.toString());
		}catch (BusinessException ex) {
			logger.error("\n运单号:"+waybillNo+",VTS生成FOSS结算财务单据业务逻辑错误," + ex.getErrorCode(), ex);
			vtsResponse.setBusinessId(businessId);
			vtsResponse.setResult(2);//处理失败标志：1是成功；2是失败
			vtsResponse.setWaybillNo(waybillNo);
			vtsResponse.setReason("运单号:"+waybillNo+",VTS生成FOSS结算财务单据业务逻辑错误," + ex);//失败原因
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("VTS开单更改单报错：" + writer.toString());
		} catch (Exception e) {
			logger.error("\n运单号:"+waybillNo+"VTS生成FOSS结算财务单据未知异常(在Excepiton层报错)," + e.getMessage(), e);
			vtsResponse.setBusinessId(businessId);
			vtsResponse.setResult(2);//处理失败标志：1是成功；2是失败
			vtsResponse.setWaybillNo(waybillNo);
			vtsResponse.setReason("运单号:"+waybillNo+"VTS生成FOSS结算财务单据未知异常(在Excepiton层报错),"+e);//失败原因
			
			interfaceEntity.setIsSuccess("N");
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("VTS开单更改单报错：" + writer.toString());
			//记录失败日志
			logger.info("VTS开单生成财务单据处理失败原因是："+vtsResponse.getReason());
		}finally{
			//插入接口日志表
			boolean flag = this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			if(flag){
				logger.info("接口日志表新增成功！业务id："+vtsRequest.getBusinessId()+"运单号："+vtsRequest.getWaybillEntity().getWaybillNo());
			}else{
				logger.info("接口日志表新增失败！业务id："+vtsRequest.getBusinessId()+"运单号："+vtsRequest.getWaybillEntity().getWaybillNo());
			}
		}
		//记录日志
		logger.info("VTS到FOSS接口生成应收单结束....");
		return vtsResponse;
	}
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	public void setVtsWaybillFinanceBillService(
			IVtsWaybillFinanceBillService vtsWaybillFinanceBillService) {
		this.vtsWaybillFinanceBillService = vtsWaybillFinanceBillService;
	}
	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}

}
