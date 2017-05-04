package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.ReceivableDetailVo;
import com.deppon.foss.util.define.FossConstants;

public class ReceivableDetailAction extends AbstractAction {

	private static final long serialVersionUID = 132766583764669537L;
	
	//应收明细VO
	private ReceivableDetailVo receivableDetailVO;

	//查询应收明细service
	private IBillReceivableDService billReceivableDService;
	
	/**
	 * 按应收单号查询应收明细
	 *
	 * @return the string
	 * @author ddw
	 * @date 2016-01-15
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JSON
	public String queryReceivableDetail(){
		//获取应收单号
		String receivableNo = receivableDetailVO.getReceivableNo();
		//应收单号集合
		List<String> receivableNos = new ArrayList();
		//添加应收单号
		receivableNos.add(receivableNo);
		//按应收单号、有效状态查询应收明细
		List<BillReceivableDEntity> list = billReceivableDService.queryByReceivableNOs(receivableNos, FossConstants.ACTIVE);
		//设置返回结果
		receivableDetailVO.setReceivableDEntityList(list);
		//返回
		return returnSuccess();
	}

	public void setBillReceivableDService(IBillReceivableDService billReceivableDService) {
		this.billReceivableDService = billReceivableDService;
	}

	public ReceivableDetailVo getReceivableDetailVO() {
		return receivableDetailVO;
	}

	public void setReceivableDetailVO(ReceivableDetailVo receivableDetailVO) {
		this.receivableDetailVO = receivableDetailVO;
	}

}
