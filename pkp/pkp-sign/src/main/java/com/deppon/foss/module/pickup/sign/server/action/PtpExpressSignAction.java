package com.deppon.foss.module.pickup.sign.server.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpExpressSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人快递自提签收Aciton
 * @author gpz
 * @date 2016年1月21日 
 */
public class PtpExpressSignAction extends AbstractAction {
	
	private static final Logger LOGGER = LogManager
			.getLogger(PtpExpressSignAction.class);

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6821396166694001124L;

    /**
     * 签收出库vo
     */
    private SignVo signVo = new SignVo();
    
    /**
	 * 签收明细vo
	 */
	private SignDetailVo signDetailVo = new SignDetailVo();

    /**
     * 定义查询签收出库界面的库存到达联
     */
    private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
    
    /**
	 * 合伙人签收VO
	 */
	private PtpSignVo ptpExpressSignVo = new PtpSignVo();
	
	/**
	 * 派送明细 Vo
	 */
	private DeliverbillDetailVo deliverbillDetailVo = new DeliverbillDetailVo();

    /**
     * 签收出库service
     */
    @Autowired
    private ISignService signService;
    
    /**
	 * 派送处理Service
	 */
    @Autowired
	private IDeliverHandlerService deliverHandlerService;

    /**
     * 合伙人快递签收service
     */
    @Autowired
    private IPtpExpressSignService ptpExpressSignService;

    /**
     * 快递自提签收服务
     */
    @Autowired
    private IExpSignService expSignService;
    
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
     * 合伙人快递自提签收出库--查询到达联
     * @author gpz
     * @date 2016年1月21日 
     * @return String
     */
    @JSON
    public String queryArriveSheetInfoPtpExpress() {
        try {
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			SaleDepartmentEntity saleDept = saleDepartmentService
					.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (!FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是合伙人对接部门!");
				}
			}
        	
        	//设置最终配载部门为当前登录部门
            signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
            //TODO 入库时间条件
            arriveSheetVo = expSignService.queryPtpExpressArriveSheetByParams(signVo.getSignDto(),this.getStart(), this.getLimit());
            //签收出库查询返回结果集合
            List<SignArriveSheetDto> signArriveSheetDtoList = arriveSheetVo.getSignArriveSheetDtoList();
            if (CollectionUtils.isNotEmpty(signArriveSheetDtoList)) {
                for (SignArriveSheetDto signArriveSheetDto : signArriveSheetDtoList) {
                	//将服务器现在时间传到页面显示
                    signArriveSheetDto.setServiceTime(DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT));
                }
            }
            this.setTotalCount(arriveSheetVo.getTotalCount());
        } catch (BusinessException e) {
            // 返回错误信息
            return returnError(e);
        }
        return returnSuccess();
    }
    
	/**
	 * 合伙人快递签收----查询财务信息
	 * @author gpz
	 * @date 2016年1月22日
	 * @return String
	 */
	@JSON
	public String queryFinanceForPtpExpress() {
		try {
			String waybillNo = deliverbillDetailVo.getDeliverbillDetailDto().getWaybillNo();
			//判断运单号是否为空
			if(StringUtils.isBlank(waybillNo)) {
				//运单号为空
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_IS_NULL);
			}
			
			//根据运单号查询财务信息
			deliverbillDetailVo.setFinancialDto(deliverHandlerService.queryFinanceSign(waybillNo));
			
		} catch (BusinessException e) {
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
    
    
    /**
     * 合伙人快递自提签收---签收操作
     * @author gpz
     * @date 2016年1月23日
     * @return String
     */
    @JSON
	public String addSignForPtpExpress() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			if (StringUtils.isEmpty(ptpExpressSignVo.getWaybillNo())) {
				// 抛出业务异常
				throw new SignException(SignException.WAYBILLNO_IS_NOT_NULL);// 运单号不能为空
			}
			
			//判断是否结清: true 表已经结清
			boolean repaymentFlag = repaymentService.isPayment(ptpExpressSignVo.getWaybillNo());
			//代收货款校验与扣款
			if (!repaymentFlag) {
				ptpSignService.validatePayCOD(ptpExpressSignVo, currentInfo);
			}
			
			// 核销应收单并提交签收信息
			String resultMsg = ptpExpressSignService.writeOffAndSign(ptpExpressSignVo,
					signDetailVo.getSignDetailList(),arriveSheetVo.getArriveSheet(), signVo, currentInfo);

			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
			
		} catch (BusinessException e) {
			LOGGER.error("合伙人快递自提签收异常", e);
			// 处理异常
			return returnError(e);
		}
		// 返回成功
		return returnSuccess(SignException.SIGN_SUCCESS);//签收出库成功
    }
    


	/**
	 * @return the signVo
	 */
	public SignVo getSignVo() {
		return signVo;
	}

	/**
	 * @param signVo the signVo to set
	 */
	public void setSignVo(SignVo signVo) {
		this.signVo = signVo;
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
	 * @return the ptpExpressSignVo
	 */
	public PtpSignVo getPtpExpressSignVo() {
		return ptpExpressSignVo;
	}

	/**
	 * @param ptpExpressSignVo the ptpExpressSignVo to set
	 */
	public void setPtpExpressSignVo(PtpSignVo ptpExpressSignVo) {
		this.ptpExpressSignVo = ptpExpressSignVo;
	}

	/**
	 * @return the signDetailVo
	 */
	public SignDetailVo getSignDetailVo() {
		return signDetailVo;
	}

	/**
	 * @param signDetailVo the signDetailVo to set
	 */
	public void setSignDetailVo(SignDetailVo signDetailVo) {
		this.signDetailVo = signDetailVo;
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

}
