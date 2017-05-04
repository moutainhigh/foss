package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IPaymentSettlementDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.ArriveOnlineDto;

/**
 * 付款查询相关dao
 * @author 239284
 *
 */
public class PaymentSettlementDao extends iBatis3DaoImpl implements IPaymentSettlementDao{

	private static final String NAMESPACE = "foss.stl.PaymentSettlementDao.";
	
	/**
	 * 根据单据类型、付款方式查询到达网上支付结果
	 * @param waybillNo 运单号
	 * @param billType 应收单单据类型
	 * @param payType 还款单付款方式
	 * @return
	 */
	@Override
	public List<ArriveOnlineDto> arriveOnlinePay(String waybillNo,
			String billType, String payType) {
		//判断传入的是否为空
		if(StringUtils.isEmpty(waybillNo) && StringUtils.isEmpty(billType)  && StringUtils.isEmpty(payType) ){
			throw new SettlementException("运单号或单据类型或付款方式为空，不能进行到达网上支付校验!");
		}
		Map map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("billType", billType);
		map.put("payType", payType);
		return getSqlSession().selectList(NAMESPACE + "queryArriveIsOnlinePay", map);
	}

}
