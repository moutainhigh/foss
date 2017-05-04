package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISubmitRefundDao;
import com.deppon.foss.module.settlement.pay.api.server.service.ISubmitRefundConcreteService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.SubmitRefundDto;
import com.deppon.foss.withholdingservice.CommonException;

/**
 * 转报销接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-10 下午3:12:09,content:TODO </p>
 * @author 105762
 * @date 2014-7-10 下午3:12:09
 * @since 1.6
 * @version 1.0
 */
public class SubmitRefundConcreteService implements ISubmitRefundConcreteService {
	private ISubmitRefundDao submitRefundDao;

	/** 
	 * <p>转报销接口实现方法</p> 
	 * @author 105762
	 * @date 2014-7-10 下午4:04:51
	 * @param esbHeader
	 * @param submitRefundRequest
	 * @return
	 * @throws CommonException 
	 * @see com.deppon.foss.submitrefund.ISubmitRefundService#submitRefund(javax.xml.ws.Holder, com.deppon.foss.inteface.domain.stl.SubmitRefundRequest)
	 */
	@Override
	public boolean submitRefund(SubmitRefundDto dto) {
		// 非空校验
		SettlementUtil.valideIsNull(dto, "传入参数dto为空");
		SettlementUtil.valideIsNull(dto.getWorkflowNo(), "传入参数 预提工作流号 为空");
		SettlementUtil.valideIsNull(dto.getIsSuccess(), "传入参数 是否成功 为空");
		int updatedRows = 0;
		updatedRows = submitRefundDao.handleSubmitRefund(dto);
		return  updatedRows > 0 ? true : false;
	}

	/**
	 * @param submitRefundDao the submitRefundDao to set
	 */
	public void setSubmitRefundDao(ISubmitRefundDao submitRefundDao) {
		this.submitRefundDao = submitRefundDao;
	}

}
