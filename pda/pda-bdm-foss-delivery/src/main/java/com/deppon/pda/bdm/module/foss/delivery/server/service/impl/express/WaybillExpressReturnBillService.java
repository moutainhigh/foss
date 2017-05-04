package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.express;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RebackBillingEntity;


/**   
 * @ClassName WaybillExpressReturnBill  
 * @Description 返货开单
 * @author  092038 张贞献  
 * @date 2015-5-21    
 */ 
public class WaybillExpressReturnBillService implements IBusinessService<Void, RebackBillingEntity> {
	
	private static final Log LOG = LogFactory.getLog(WaybillExpressReturnBillService.class);
	
	private IPdaWaybillService pdaWaybillService;
	
	/**
	 * 解析包体
	 */
	@Override
	public RebackBillingEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		RebackBillingEntity rebackBillingEntity = JsonUtil.parseJsonToObject(RebackBillingEntity.class, asyncMsg.getContent());
		return rebackBillingEntity;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, RebackBillingEntity rebackBillingEntity) throws PdaBusiException {
		LOG.info(rebackBillingEntity);
		ResultDto resultDto = null;
		try {
			//验证数据有效性
			this.validate(asyncMsg,rebackBillingEntity);
			
			WaybillExpressPdaDto dto = new WaybillExpressPdaDto();
			dto.setOriginalWaybillNo(rebackBillingEntity.getOldWblCode());
			dto.setWaybillNo(rebackBillingEntity.getNewWblCode());
			dto.setBillUserNo(asyncMsg.getUserCode());
			dto.setExpressEmpCode(asyncMsg.getUserCode());
			dto.setTargetOrgCode(rebackBillingEntity.getDestDeptCode());
			dto.setBillOrgCode(rebackBillingEntity.getDeptCode());
			
			//返货开单
			resultDto = pdaWaybillService.submitWaybillExpressReturnBill(dto, rebackBillingEntity.getDeptCode());
			
			if(resultDto != null &&  resultDto.getCode() != null &&ResultDto.FAIL.equals(resultDto.getCode())){
				throw new BusinessException(resultDto.getMsg(),resultDto.getCode());
			}
					
					
					
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	
	
	}

	
	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param param
	 */
	private void validate(AsyncMsg asyncMsg, RebackBillingEntity param){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		Argument.notNull(param, "RebackBillingEntity");
	    //新加的接口上传的字段，目的站：
		Argument.hasText(param.getDestDeptCode(), "RebackBillingEntity.destDeptCode");
	    //  收入部门：
		Argument.notNull(param.getDeptCode(), "RebackBillingEntity.deptCode");
		
	     //  原单号：
		Argument.hasText(param.getOldWblCode(), "RebackBillingEntity.oldWblCode");
		 //　新单号:
		Argument.notNull(param.getNewWblCode(), "RebackBillingEntity.newWblCode");

		
	}
	
	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_RETURN_BILLING_PROVIDED.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

}
