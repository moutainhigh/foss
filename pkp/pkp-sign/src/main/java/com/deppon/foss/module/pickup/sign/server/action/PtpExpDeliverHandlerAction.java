package com.deppon.foss.module.pickup.sign.server.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人快递派送签收action
 * @author gpz
 * @date 2016年1月27日
 */
public class PtpExpDeliverHandlerAction extends AbstractAction {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3759943377275796666L;

	/**
	 * 到达联vo
	 */
	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
	
	/**
	 * 合伙人签收VO
	 */
	private PtpSignVo ptpExpDeliverHandlerVo = new PtpSignVo();
	
	/**
	 * 派送明细 Vo
	 */
	private DeliverbillDetailVo deliverbillDetailVo = new DeliverbillDetailVo();
	
	/**
	 * 派送处理Service
	 */
	@Autowired
	private IExpDeliverHandlerService expDeliverHandlerService;
	
	/**
	 * 运单签收Service
	 */
	@Autowired
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * 合伙人快递派送处理Service
	 */
	@Autowired
	private IPtpExpDeliverHandlerService ptpExpDeliverHandlerService;
	
	/**
	 * 签收结算货款服务
	 */
	@Autowired
	private IPaymentSettlementService paymentSettlementService;
	
	/**
	 * 合伙人签收service
	 */
	@Autowired
	private IPtpSignService	ptpSignService;
	
	/**
	 * 结清货款Service
	 */
	@Autowired
	private IRepaymentService repaymentService;

	/**
	 * 营业部Service
	 */
    @Autowired
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 派送处理---（待处理）查询运单号
	 * @author gpz
	 * @date 2016年1月27日
	 * @return String
	 */
	@JSON
	public String queryPtpExpWaybillNoByParams() {
		try {
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			SaleDepartmentEntity saleDept = saleDepartmentService
					.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (!FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是合伙人对接部门!");
				}
			}
			
			// 查询待处理模块下---运单信息
			List<DeliverbillDetailDto> deliverbillDetialDtoList = expDeliverHandlerService.queryPtpExpDeliverbillWaybillNo(deliverbillDetailVo.getDeliverbillDetailDto());
			deliverbillDetailVo.setDeliverbillDetailDtos(deliverbillDetialDtoList);
		} catch (BusinessException e) {// 异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 查询该派送单是否有到达联没有进行签收确认----送货确认操作
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return String
	 */
	@JSON
	public String queryArrivesheetIsSignForPtpExpDeliver() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//查询当前派送单中所有的运单到达联是否都完成了签收确认
			deliverbillDetailVo.getDeliverbillDetailDto().setLastLoadOrgCode(currentInfo.getCurrentDeptCode());//得到当前登录的部门
			List<DeliverbillDetailDto> lists = expDeliverHandlerService.queryArrivesheetIsSign(deliverbillDetailVo.getDeliverbillDetailDto());
			//lists集合不为空
			if (CollectionUtils.isNotEmpty(lists)) {
				StringBuffer waybillNos = new StringBuffer();
				for (DeliverbillDetailDto deliverbillDetailDto : lists) {
					//运单号
					waybillNos.append(deliverbillDetailDto.getWaybillNo());
					//常量","
					waybillNos.append(SignConstants.SPLIT_CHAR);
				}
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_NOT_SIGN,new Object[]{waybillNos.substring(0, waybillNos.length()-1)});
			} else {
				//录入接回货物信息
				int result = expDeliverHandlerService.addPullbackGoods(deliverbillDetailVo.getDeliverbillDetailDto(),currentInfo);
				//如果返回为1
				if (SignConstants.ONE == result) {
					return returnSuccess(DeliverHandlerException.NO_PULLBACK_GOODS_DELIVER_CONFIRM_SUCCESS);
				}else if(SignConstants.THREE == result){
					throw new DeliverHandlerException(DeliverHandlerException.DELIVER_IS_SIGNINFO_CONFIRMED);
				}else{
					//送货成功
					return returnSuccess(DeliverHandlerException.DELIVER_CONFIRM_SUCCESS);
				}
			}
		} catch (DeliverHandlerException e) {
			//异常处理
			return returnError(e);
		}
	}
	
	
	/**
	 * 修改派送处理签收信息 --无pda签收确认
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return String
	 */
	@JSON
	public String addNoPdaSignForPtpExpDeliver() {
		try {
			if(StringUtil.isEmpty(ptpExpDeliverHandlerVo.getWaybillNo())){
				//运单号为空异常
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_IS_NULL);
			}
			
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//判断是否结清: true 表已经结清
			boolean repaymentFlag = repaymentService.isPayment(ptpExpDeliverHandlerVo.getWaybillNo());
			//代收货款校验与扣款, 同时生成结清记录更新结清状态
			if (!repaymentFlag) {
				ptpSignService.validatePayCOD(ptpExpDeliverHandlerVo, currentInfo);
			}
			
			//合伙人快递派送处理  无PDA签收
			String result = ptpExpDeliverHandlerService.addNoPdaSignForPtpExpDeliver(arriveSheetVo.getArriveSheet(),deliverbillDetailVo);
			if(StringUtil.isNotEmpty(result)){
				return returnSuccess(result);
			}
			
		} catch (BusinessException e) { // 异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 修改派送处理签收信息 --有pda签收确认  录入签收人(合伙人快递派送签收没有PDA,所以此方法没用)
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String addPdaSignForPtpExpDeliver() {
		try {
			if(StringUtil.isEmpty(arriveSheetVo.getArriveSheet().getWaybillNo())){
				//运单号为空异常
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_IS_NULL);
			}
			//当前操作人
			arriveSheetVo.getArriveSheet().setOperator(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpName());
			//当前操作人编码
			arriveSheetVo.getArriveSheet().setOperatorCode(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			
			//合伙人快递派送处理  有PDA签收
			ptpExpDeliverHandlerService.addPdaSignForPtpExpDeliver(arriveSheetVo.getArriveSheet(),deliverbillDetailVo);

		  //异常处理
		} catch (DeliverHandlerException e) {
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}


	/**
	 * @return the arriveSheetVo
	 */
	public ArriveSheetVo getArriveSheetVo() {
		return arriveSheetVo;
	}

	/**
	 * @param arriveSheetVo the arriveSheetVo to set
	 */
	public void setArriveSheetVo(ArriveSheetVo arriveSheetVo) {
		this.arriveSheetVo = arriveSheetVo;
	}

	/**
	 * @return the deliverbillDetailVo
	 */
	public DeliverbillDetailVo getDeliverbillDetailVo() {
		return deliverbillDetailVo;
	}

	/**
	 * @param deliverbillDetailVo the deliverbillDetailVo to set
	 */
	public void setDeliverbillDetailVo(DeliverbillDetailVo deliverbillDetailVo) {
		this.deliverbillDetailVo = deliverbillDetailVo;
	}

	/**
	 * @return the ptpExpDeliverHandlerVo
	 */
	public PtpSignVo getPtpExpDeliverHandlerVo() {
		return ptpExpDeliverHandlerVo;
	}

	/**
	 * @param ptpExpDeliverHandlerVo the ptpExpDeliverHandlerVo to set
	 */
	public void setPtpExpDeliverHandlerVo(PtpSignVo ptpExpDeliverHandlerVo) {
		this.ptpExpDeliverHandlerVo = ptpExpDeliverHandlerVo;
	}
	
}
