package com.deppon.foss.module.settlement.pay.server.action;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.vo.PayableDetailVo;
import com.deppon.foss.util.define.FossConstants;

public class PayableDetailAction extends AbstractAction {

	
	//应收明细VO
	private PayableDetailVo payableDetailVO;

	//查询应收明细service
	private IBillPayableDService billPayableDService;

	/**
	 * 按应付单号查询应付明细
	 *
	 * @return the string
	 * @author ddw
	 * @date 2016-01-15
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JSON
	public String queryPayableDetail(){
		//获取应收单号
		String payableNo = payableDetailVO.getPayableNo();
		//应收单号集合
		List<String> payableNos = new ArrayList();
		//添加应收单号
		payableNos.add(payableNo);
		//按应收单号、有效状态查询应收明细
		List<BillPayableDEntity> list = billPayableDService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
		//设置返回结果
		payableDetailVO.setPayableDEntityList(list);
		//返回
		return returnSuccess();
	}
	
	public PayableDetailVo getPayableDetailVO() {
		return payableDetailVO;
	}

	public void setPayableDetailVO(PayableDetailVo payableDetailVO) {
		this.payableDetailVO = payableDetailVO;
	}

	public void setBillPayableDService(IBillPayableDService billPayableDService) {
		this.billPayableDService = billPayableDService;
	}
	
}
