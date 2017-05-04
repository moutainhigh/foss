package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutAdjustmentService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutvehicleFeeAdjustService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestAdjustFeeEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponseAdjustFeeEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsOutAdjustmentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IVtsOutvehicleFeeAdjust;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-05-27 19:10:20
 * VTS外请车费用调整同意之后调用结算重生成整车尾款应付单restful接口
 *
 */
public class VtsOutvehicleFeeAdjust implements IVtsOutvehicleFeeAdjust{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(VtsOutvehicleFeeAdjust.class);
	
	/**
     * 业务互斥锁
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 注入 IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService
     * @param businessLockService
     */
    private IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService;
    
    /**@author 340403
     * 注入整车费用调整service
     */
    private IVtsOutAdjustmentService vtsOutAdjustmentService;
    
	
    public void setVtsOutAdjustmentService(
			IVtsOutAdjustmentService vtsOutAdjustmentService) {
		this.vtsOutAdjustmentService = vtsOutAdjustmentService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
    
	public void setVtsOutvehicleFeeAdjustService(
			IVtsOutvehicleFeeAdjustService vtsOutvehicleFeeAdjustService) {
		this.vtsOutvehicleFeeAdjustService = vtsOutvehicleFeeAdjustService;
	}



	@Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	@Override
	public String modifyTL2PayableAmount(String requestAdjustFee) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_VTS_TO_FOSS_ADJUST_FEE");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		//定义请求实体:requestEntity为客户端定义的key，所以一定要和客户端的key保持一致{"requestEntity":{},"TYPE":"1"}
		JSONObject object = JSONObject.fromObject(requestAdjustFee).getJSONObject("requestEntity");
		RequestAdjustFeeEntity request = (RequestAdjustFeeEntity) JSONObject.toBean(object,RequestAdjustFeeEntity.class);
		//定义响应实体
		ResponseAdjustFeeEntity response = new ResponseAdjustFeeEntity();
		//判断非空
		if(request == null){
			throw new SettlementException("VTS外请车费用调整传递参数为空!");
		}
		//判断单号信息
		String waybillNo = request.getWaybillNo();
		LOGGER.info("开始获取VTS外请车费用调整同意之后调用结算应付单接口开始!单号为：" + waybillNo);
		//设置用户信息
		//1.新建UserEntiy实体
		UserEntity user = new UserEntity();
		user.setEmpCode(request.getEmpCode());
		user.setEmpName(request.getEmpName());
		//2.新建OrgAdministrativeInfoEntity实体
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(request.getModifyDeptCode());
		dept.setName(request.getModifyDeptName());
		//3.对CurrentInfo执行构造函数，将值设置到CurrentInfo中
		CurrentInfo currentInfo = new CurrentInfo(user,dept);
		
		/**
		 * 调用Service业务层，根据request请求判断费用调整是增加还有减少，来红冲整车尾款应付单，然后重新生成（根据已经有的应付单重新生成）
		 * 费用调整：只能是整车尾款，整车首款是不允许调整的
		 */
		try{
			resp.setHeader("ESB-ResultCode", "1");
			MutexElement mutex = new MutexElement(request.getWaybillNo(), "VTS_FOSS_OUTVEHICLE_FEEADJUST", MutexElementType.VTS_FOSS_OUTVEHICLE_FEEADJUST);
			LOGGER.info("VTS外请车费用调整开始加锁：" + mutex.getBusinessNo());
			/**
			 * 1.首先校验整车尾款应付单
			 */
			BillPayableConditionDto conDto = new BillPayableConditionDto();
			conDto.setWaybillNo(waybillNo);//单号
			conDto.setActive("Y");//有效
			conDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST});//整车尾款TL2
			conDto.setIsRedBack("N");//非红单
			
			List<BillPayableEntity> list = vtsOutvehicleFeeAdjustService.queryTLBillPayableByWaybillNo(conDto);
			//不存在整车尾款vts应付单信息，不能进行调整费用操作"
			if (CollectionUtils.isEmpty(list)) {
				throw new SettlementException("不存在整车尾款vts应付单信息，不能进行调整费用操作");
			}
			//存在多条外请尾款成本应付单，不能进行调整费用操作
			if(list.size()>1){
				throw new SettlementException("存在"+list.size()+"整车尾款成本应付单，不能进行调整费用操作");
			}
			//获取集合中的第一条数据
			BillPayableEntity entity = list.get(0);
			// 生成新单据实体newPayableEntity：VTS整车费用调整，只调整费用，其他不调整，所以针对原来数据库中已有的记录，只改变金额，时间，操作人，部门
			BillPayableEntity newPayableEntity = new BillPayableEntity();
			//复制
			BeanUtils.copyProperties(newPayableEntity, entity);
			//校验应付单
			this.validateBillPayableEntity(entity);	
			
			/**
			 * 2.生成费用调整后新的整车尾款应付单
			 */
			//首先判断费用增加还是减少:增减类型 increase-增加； decrease-减少
			if(StringUtils.equals("increase", request.getAdjustType())){//增加
				newPayableEntity.setAmount(newPayableEntity.getAmount().add(request.getAdjustAmount()));//应付单金额增加
				newPayableEntity.setUnverifyAmount(newPayableEntity.getUnverifyAmount().add(request.getAdjustAmount()));//应付单未核销金额
			}else{//减少
				newPayableEntity.setAmount(newPayableEntity.getAmount().add(request.getAdjustAmount().negate()));//应付单金额减少
				newPayableEntity.setUnverifyAmount(newPayableEntity.getUnverifyAmount().add(request.getAdjustAmount().negate()));//应付单未核销金额
			}
			
			/**
			 * 3.红冲整车尾款应付单，然后重新生成（根据已经有的应付单重新生成）；并且还要把 整车到达确认凭证表红冲，生成新的
			 */
			
			vtsOutvehicleFeeAdjustService.writeBackBillPayable(entity, newPayableEntity,currentInfo);
			

			
			//插入蓝单：整车尾款应付单
			//vtsOutvehicleFeeAdjustService.insertBillPayable(newPayableEntity,currentInfo);
			
			/**
			 * caixiao--340403
			 * 插入整车费用调整记录
			 */
			request.setContractCode(newPayableEntity.getSourceBillNo());
			int i=vtsOutAdjustmentService.insert(copyRequestToVtsOutAdjustmentEntity(request));
			if (i==0) {
				throw new SettlementException("插入整车费用调整记录失败!");
			}
			
			
			
			LOGGER.info("VTS外请车费用调整开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("VTS外请车费用调整完成解锁：" + mutex.getBusinessNo());
			LOGGER.info("try无异常抛出执行成功！"+"单号为："+waybillNo);
			response.setIfSuccess(true);
	}catch(SettlementException se){
		se.printStackTrace();
		LOGGER.info("打印捕捉异常信息为SettlementException层："+se.getMessage()+"单号为："+waybillNo);
		response.setIfSuccess(false);
		resp.setHeader("ESB-ResultCode", "1");
		response.setErrorMsg("数据异常:"+se.getErrorCode());
	}catch (BusinessException ex) {
		ex.printStackTrace();
		LOGGER.info("打印捕捉异常信息为BusinessException层："+ex.getMessage()+"单号为："+waybillNo);
		response.setIfSuccess(false);
		resp.setHeader("ESB-ResultCode", "1");
		response.setErrorMsg("数据异常:"+ex.getErrorCode());
	}catch(Exception e){
		e.printStackTrace();
		LOGGER.info("打印捕捉异常信息为Exception层："+e.getMessage()+"单号为："+waybillNo);
		response.setIfSuccess(false);
		resp.setHeader("ESB-ResultCode", "1");
		response.setErrorMsg("数据异常:"+e.getMessage());
	}
		LOGGER.info("try..catch之后FOSS响应成功!"+"单号为："+waybillNo);
		String responseJson = JSONObject.fromObject(response).toString();
		return responseJson;
	}
	
	
	
	/**
	 * 验证应付单
	 * @author @218392-FOSS结算开发组-张永雪
	 * @date 2016-05-29 14：18:20
	 * @param payableEntity
	 */
	private void validateBillPayableEntity(BillPayableEntity payableEntity) {
		//判断传入应付单参数是否为空
		if (payableEntity == null) {
			throw new SettlementException("应付单信息为空");
		}
		//判断传入应付单参数是否核销
		if (payableEntity.getVerifyAmount() != null
				&& payableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销");
		}
		//判断传入应付单参数是否冻结
		if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
				.equals(payableEntity.getFrozenStatus())) {
			throw new SettlementException("应付单已冻结");
		}
		//判断传入应付单参数是否付款
		if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
				.equals(payableEntity.getPayStatus())) {
			throw new SettlementException("应付单已付款");
		}
		
/*		//需求变更后，应付单审核后，不能进行修改和作废操作
		if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE
				.equals(payableEntity.getApproveStatus())) {
			throw new SettlementException("应付单已审核");
		}
*/			
	}
	/**
	 * 将请求实体属性值赋给整车调整信息实体
	 * @author 340403foss
	 * @createTime 2016年9月20日 下午4:58:40
	 * @param request
	 * @return
	 */
	private VtsOutAdjustmentEntity copyRequestToVtsOutAdjustmentEntity(RequestAdjustFeeEntity request){
		
		VtsOutAdjustmentEntity vtsOutAdjustmentEntity=new VtsOutAdjustmentEntity();
		vtsOutAdjustmentEntity.setId(UUIDUtils.getUUID());
		vtsOutAdjustmentEntity.setModifyDeptCode(request.getModifyDeptCode());
		vtsOutAdjustmentEntity.setModifyDeptName(request.getModifyDeptName());
		vtsOutAdjustmentEntity.setWaybillNo(request.getWaybillNo());
		vtsOutAdjustmentEntity.setAdjustAmount(request.getAdjustAmount());
		vtsOutAdjustmentEntity.setAdjustType(request.getAdjustType());
		vtsOutAdjustmentEntity.setBillTime(request.getBillTime());
		vtsOutAdjustmentEntity.setContractCode(request.getContractCode());
		vtsOutAdjustmentEntity.setContractDate(request.getContractDate());
		vtsOutAdjustmentEntity.setEmpCode(request.getEmpCode());
		vtsOutAdjustmentEntity.setEmpName(request.getEmpName());
		return vtsOutAdjustmentEntity;
	}

}
