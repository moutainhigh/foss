package com.deppon.foss.module.transfer.scheduling.server.action;

import java.math.BigDecimal;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IConsultPriceService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ConsultPriceVO;

public class ConsultPriceAction extends AbstractAction{

	/**
	 * 查询询价编号的Action
	 */
	private static final long serialVersionUID = 1L;

	ConsultPriceVO consultPriceVO = new ConsultPriceVO();

	public ConsultPriceVO getConsultPriceVO() {
		return consultPriceVO;
	}

	public void setConsultPriceVO(ConsultPriceVO consultPriceVO) {
		this.consultPriceVO = consultPriceVO;
	}

	// 调用查询询价信息IConsultPriceService接口
	private IConsultPriceService consultPriceService;

	public void setConsultPriceService(IConsultPriceService consultPriceService) {
		this.consultPriceService = consultPriceService;
	}

	/**
	 * 根据请车编号查询请车部门
	 * 
	 * @author 268084
	 * @return
	 */
	@JSON
	public String queryNeedVehicleDept() {
		if ("1".equals(consultPriceVO.getConsultPriceNo().trim())) {
			ConsultPriceEntity e = new ConsultPriceEntity();
			e.setNeedVehicleDept("1");
			BigDecimal bd = new BigDecimal("1");
			e.setQuotedInfo(bd);
			consultPriceVO.setConsultPriceEntity(e);
		} else {
			String currentDept = FossUserContext.getCurrentDeptName();
			ConsultPriceEntity consultPriceEntity = consultPriceService
					.queryByConsultPriceNo(consultPriceVO.getConsultPriceNo());
			if (consultPriceEntity != null) {
				if (consultPriceEntity.getNeedVehicleDept().equals("此询价编号已使用过")) {
					consultPriceVO.setConsultPriceEntity(consultPriceEntity);
				} else {
					if (currentDept.equals(consultPriceEntity
							.getNeedVehicleDept())) {
						consultPriceVO
								.setConsultPriceEntity(consultPriceEntity);
					} else {
						consultPriceVO.setConsultPriceEntity(null);
					}
				}

			}
		}
		return returnSuccess();
	}

}
