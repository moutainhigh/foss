package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutgoingBigCustomersService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OutgoingBigCustomersVo;

/**
 * @author 310854
 * 
 * @date 2016-2-25 下午3:41:20
 * 
 *       外发大客户Action
 */
public class OutgoingBigCustomersAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2296805527811642110L;

	private IOutgoingBigCustomersService outgoingBigCustomersService;

	private OutgoingBigCustomersVo outgoingBigCustomersVo;

	public void setOutgoingBigCustomersService(
			IOutgoingBigCustomersService outgoingBigCustomersService) {
		this.outgoingBigCustomersService = outgoingBigCustomersService;
	}

	public OutgoingBigCustomersVo getOutgoingBigCustomersVo() {
		return outgoingBigCustomersVo;
	}

	public void setOutgoingBigCustomersVo(
			OutgoingBigCustomersVo outgoingBigCustomersVo) {
		this.outgoingBigCustomersVo = outgoingBigCustomersVo;
	}

	/**
	 * 新增外发大客户
	 * 
	 * @author 310854
	 * @return
	 */
	public String createOutgoingBigCustomers() {
		try {
			outgoingBigCustomersService
					.addOutgoingBigCustomers(outgoingBigCustomersVo
							.getOutgoingBigCustomersEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 编辑外发大客户
	 * 
	 * @return
	 */
	public String updateOutgoingBigCustomers() {
		try {
			outgoingBigCustomersService
					.updateOutgoingBigCustomers(outgoingBigCustomersVo
							.getOutgoingBigCustomersEntity());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 查询外发大客户列表
	 * 
	 * @return
	 */
	@JSON
	public String queryOutgoingBigCustomersByCondition() {
		try {
			// boolean bo =
			// outgoingBigCustomersService.bigCustomersIsOutgoing("400693237");

			List<OutgoingBigCustomersEntity> cuslist = outgoingBigCustomersService
					.queryOutgoingBigCustomersEntitys(outgoingBigCustomersVo
							.getOutgoingBigCustomersEntity(), start, limit);

			outgoingBigCustomersVo.setOutgoingBigCustomersEntityList(cuslist);

			long totalCount = outgoingBigCustomersService
					.getCountByCondition(outgoingBigCustomersVo
							.getOutgoingBigCustomersEntity());

			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

}
