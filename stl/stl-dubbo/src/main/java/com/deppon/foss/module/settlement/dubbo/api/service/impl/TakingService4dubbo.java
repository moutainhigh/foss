package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.dubbo.ecs.api.define.exception.SettlementException;
import com.deppon.foss.module.settlement.dubbo.api.dao.IBillReceivablSelectEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.settlement.dubbo.api.service.ITakingService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.util.FossConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementDictionaryConstants;

public class TakingService4dubbo implements ITakingService4dubbo {
	
	private final Logger logger = LogManager.getLogger(TakingService4dubbo.class);

	/**
	 * 运单DAO 运单基本信息DAO服务接口 实现数据持久化
	 */
	@Resource
	private IWaybillDao4dubbo waybillDao4dubbo;
	
	@Resource
	private IBillReceivablSelectEntityDao4dubbo BillReceivablSelectEntityDao4dubbo;

	/**
	 * 开单网上支付，但是尚未支付的单据
	 * 
	 * @author 327090
	 * @return 尚未支付的单据
	 */
	@Override
	public List<String> unpaidOnlinePay(List<String> waybill_Nos) {

		// DN201607150014 裹裹订单开单网上支付不需要检验是否网上支付完成 by 243921
		/*Iterator<String> it = waybill_Nos.iterator();
		while (it.hasNext()) {
			String waybillNO = it.next();
			// 调用接送货接口查询运单
			WaybillEntity waybillEntity = queryWaybillBasicByNo(waybillNO);
			if (null != waybillEntity) {
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE
						.equals(waybillEntity.getPaidMethod())
						&& StringUtils.isNotBlank(waybillEntity.getOrderNo())
						&& waybillEntity.getOrderNo().contains("GG")) {
					// 将开单网上支付的裹裹运单从列表中删除
					it.remove();
				}
			} else {
				throw new SettlementException("该运单信息不存在！运单号：" + waybillNO);
			}
		}*/

		// 判断传入的是否为空
		if (CollectionUtils.isEmpty(waybill_Nos)) {
			return null;
		}
		List<String> unpaidWaybills = null;
		List<BillReceivableEntity> billReceivables = queryBySourceBillNOs(waybill_Nos,
				SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,
				FossConstants.ACTIVE);
		// 循环判断是否是存在未核销网上支付的运单
		logger.info("应收单实体有没有："+billReceivables);
		if (CollectionUtils.isNotEmpty(billReceivables)) {
			for (BillReceivableEntity billReceivable : billReceivables) {
				// 判断是否未核销，如果未核销则返回
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
						.equals(billReceivable.getBillType())
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE
								.equals(billReceivable.getPaymentType())
						&& BigDecimal.ZERO.compareTo(billReceivable
								.getUnverifyAmount()) < 0) {
					// 如果为空则需要创建对应的对象
					if (unpaidWaybills == null) {
						unpaidWaybills = new ArrayList<String>();
					}
					unpaidWaybills.add(billReceivable.getWaybillNo());
				}
			}
		}
		return unpaidWaybills;
	}

	/**
	 * 327090
	 */
	@Override
	public WaybillEntity queryWaybillBasicByNo(String waybillNo) {

		return this.waybillDao4dubbo.queryWaybillByNo(waybillNo);
	}

	/**
	 * @author 327090
	 */
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active) {

		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("查询应收单，输入的来源单号不能为空！");
		}
		return this.BillReceivablSelectEntityDao4dubbo.queryBySourceBillNOs(
				sourceBillNos, sourceBillType, active);
	}

}
