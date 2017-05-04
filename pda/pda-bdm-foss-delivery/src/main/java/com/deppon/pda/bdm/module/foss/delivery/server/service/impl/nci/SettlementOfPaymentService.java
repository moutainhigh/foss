package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.IPdaRepaymentService;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentResult;
/**
 * 
 * @ClassName: SettlementOfPaymentService 
 * @Description: TODO(通过单号查询结清货款模块数据) 
 * @author &268974  wangzhili
 * @date 2016-1-27 下午4:36:55 
 *
 */
public class SettlementOfPaymentService implements IBusinessService<SettlementOfPaymentResult, SettlementOfPaymentEntity>{

	private static final Log LOG = LogFactory.getLog(SettlementOfPaymentService.class);
	
	private IPdaRepaymentService pdaRepaymentService;
	//解析参数
	@Override
	public SettlementOfPaymentEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		SettlementOfPaymentEntity entity = JsonUtil.parseJsonToObject(SettlementOfPaymentEntity.class,asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public SettlementOfPaymentResult service(AsyncMsg asyncMsg, SettlementOfPaymentEntity param)
			throws PdaBusiException {
		//校验参数合法性
		this.validate(asyncMsg, param);
		//请求封装参数
		CommonQueryParamDto dto=wrapCommonQueryParamDto(asyncMsg,param);
		CommonQueryParamDto dtoResult=null;
		SettlementOfPaymentResult sResult=null;
		// 调用FOSS接口
		try {
			LOG.info("调用foss接口开始-----查询结清货款数据-----");
			long startTime = System.currentTimeMillis();
			dtoResult = pdaRepaymentService.isDriver(dto);
			long endTime = System.currentTimeMillis();
			LOG.info("调用foss接口耗时:" + (endTime - startTime));
			if(dtoResult!=null){
			 sResult=new SettlementOfPaymentResult();
			 //收货人
			 sResult.setReceiveCustomerName(dtoResult.getReceiveCustomerName());
			 //业务时间
			 sResult.setCreateTime(dtoResult.getCreateTime());
			 //总金额
			 sResult.setToPayAmount(dtoResult.getToPayAmount());
			 //未核销金额
			 sResult.setUnverifyAmount(dtoResult.getUnverifyAmount());
			 //运单号
			 sResult.setWaybillNo(dtoResult.getWaybillNo().get(0));
			 //未付款总金额
			 sResult.setUnPayAmount(dtoResult.getUnPayAmount());
			 //归属系统
			 sResult.setAffiliation(dtoResult.getOrigin());
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} 
		return sResult;
		
	}

	// 封装参数
	private CommonQueryParamDto wrapCommonQueryParamDto(AsyncMsg asyncMsg,
			SettlementOfPaymentEntity entity) {
		CommonQueryParamDto dto = new CommonQueryParamDto();
		// 单号
		if (entity.getWaybillNo().length() == 0) {
			List<String> numbers = new ArrayList<String>();
            //dto.setNumbers(numbers);
			dto.setWaybillNo(numbers);
			// 部门编码
			//dto.setOrgCode(asyncMsg.getDeptCode());
			// 部门名称
			//dto.setOrgName(entity.getDeptName());
		} else {
			List<String> numbers = new ArrayList<String>();
			numbers.add(entity.getWaybillNo());
			dto.setWaybillNo(numbers);
		}
		// 部门编码
		dto.setOrgCode(asyncMsg.getDeptCode());
		// 部门名称
		dto.setOrgName(entity.getDeptName());
		// 登录人工号
		dto.setEmpCode(asyncMsg.getUserCode());
		// 登录人名称
		dto.setEmpName(entity.getUserName());
		// 是否为司机
		if ("NCI_DRIVER".equals(asyncMsg.getUserType())) {
			// 是否司机
			dto.setIsDriver("Y");
		} else if ("NCI_USER".equals(asyncMsg.getUserType())) {
			// 是否司机
			dto.setIsDriver("N");
		}
		return dto;
	}
	
	//操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_SETTLE_PAYMENT_BY_NO.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * @Title: validate 
	 * @Description: TODO(检验参数的合法性)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg, SettlementOfPaymentEntity entity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		// 工号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		// 部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 用户类型
		Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
		//运单号
		Argument.notEmpty(entity.getWaybillNo(), "entity.waybillNo");
	}
	
	public void setPdaRepaymentService(IPdaRepaymentService pdaRepaymentService) {
		this.pdaRepaymentService = pdaRepaymentService;
	}


}
