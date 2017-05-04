package com.deppon.foss.module.settlement.pay.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;


/**
 * 录入付款单
 * @author 045738-foss-maojianqiang
 * @date 2012-11-27 下午1:37:21
 */
public class InputsBillPaymentAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2798520511257880626L;
	
	/**
	 * 获取logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(InputsBillPaymentAction.class);
	
	/**
	 * 注入录入付款单服务
	 */
	private IBillPaymentPayService billPaymentPayService;
	
	/**
	 * 注入付款单vo
	 */
	private BillPaymentVo vo = new BillPaymentVo();
	
	/**
	 * 保存付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-27 下午1:56:01
	 */
	@JSON
	public String savaBillPaymentInfo(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(vo.getPaymentEntity().getNotes().getBytes().length > SettlementConstants.MAX_BILL_NO_SIZE){
				throw new SettlementException("备注信息过长不可以超过1000个字节");
			}
			//调用录入接口
			String paymentNo = billPaymentPayService.addBillPaymentInfo(vo.getPaymentEntity(), vo.getAddDtoList(), currentInfo);
			vo.getPaymentEntity().setPaymentNo(paymentNo);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getErrorCode(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 从对账单付款
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-12 上午9:52:33
	 */
	@JSON
	public String payFromStatement(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//邓大伟，2013-08-19，对接共享不用添加100条限制
			//获取对接系统参数
			DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("SETTLEMENT__PAYTOSYSTEM_TYPE");
			List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
			//如果对接系统数据字典没配置，则抛出异常
			if(CollectionUtils.isEmpty(dataList)){
				throw new SettlementException("FOSS付款的付款工作流对接系统类型数据字典没配置，请去数据字典进行配置！");
			}
			//对接系统必须配置，且必须是1条 Y--财务共享，N--代表费控
			if(dataList.size()!=1){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，必须只有1条。其中值为Y代表财务共享，N代表费控！");
			}
			
			//付款对接系统  Y--财务共享，N--代表费控 
			String payToSystem = dataList.get(0).getValueCode();
			//判空
			if(StringUtils.isEmpty(payToSystem)){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，不能为空！值必须为Y或N。其中值为Y代表财务共享，N代表费控！");
			}
			if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
				//每次批量付款不能超过100条
				if(vo.getBillNos().length>SettlementConstants.PAY_LIMIT_MAX){
					//抛出异常
					throw new SettlementException("批量付款一次不能超过100条");
				}
			}
			//调用接口进行查询
			BillPayableManageResultDto resultDto =  billPaymentPayService.payFormStatement(vo.getBillNos(), currentInfo);
			//封装返回函数
			vo.setAddDtoList(resultDto.getAddDtoList());
			vo.setPaymentEntity(resultDto.getPaymentEntity());
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getErrorCode(),e);
			return returnError(e);
		}
		
	}
	
	public BillPaymentVo getVo() {
		return vo;
	}

	public void setVo(BillPaymentVo vo) {
		this.vo = vo;
	}

	public void setBillPaymentPayService(IBillPaymentPayService billPaymentPayService) {
		this.billPaymentPayService = billPaymentPayService;
	}

}
