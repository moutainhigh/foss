package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressValueAddServiceForECS;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ESCValueAddVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.EscWayBillValueAddDetaillVo;
import com.deppon.foss.util.define.FossConstants;

public class WaybillExpressValueAddServiceForECS implements IWaybillExpressValueAddServiceForECS {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillExpressValueAddServiceForECS.class);
	
	/**
	 * 价格计算服务
	 */
	private IBillCaculateService billCaculateService;
	
	
	public IBillCaculateService getBillCaculateService() {
		return billCaculateService;
	}

	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	@Override
	public Map<String, Object> queryWaybillInfosValueAddEcs(
			ESCValueAddVo escValueAddVo) {
		LOGGER.info("进入 queryWaybillInfosValueAdd_ecs");
//		long begin = System.currentTimeMillis();
		//返回的记录数
		int count = 0;
		//调用计费service查询增值信息
		List<ValueAddDto> valueAddDtoList = billCaculateService
				.searchECSValueAddPriceList(getQueryParam(escValueAddVo));
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != valueAddDtoList) {
			List<EscWayBillValueAddDetaillVo> list = getEscWayBillValueAddDetaillVoList(valueAddDtoList);
			count = list.size();
			result.put("list", list);
		}
		result.put("count",count);
//		LOGGER.info("----------->queryWaybillInfosValueAdd_ecs操作结束 耗时："+(System.currentTimeMillis()-begin));
		return result;
	}
	
	private List<EscWayBillValueAddDetaillVo> getEscWayBillValueAddDetaillVoList(
			List<ValueAddDto> valueAddDtoList) {
		
		//价格信息集合
		List<EscWayBillValueAddDetaillVo> escWayBillValueAddDetaillVoList = new ArrayList<EscWayBillValueAddDetaillVo>();
		// TODO Auto-generated method stub
		for(ValueAddDto valueAddDto: valueAddDtoList){
			EscWayBillValueAddDetaillVo escWayBillValueAddDetaillVo = new EscWayBillValueAddDetaillVo();
			//归集类别code
			escWayBillValueAddDetaillVo.setBelongToPriceEntityCode(valueAddDto.getBelongToPriceEntityCode());
			//TODO
			escWayBillValueAddDetaillVo.setBelongToPriceEntityName(valueAddDto.getBelongToPriceEntityName());
			// 是否可删除
			escWayBillValueAddDetaillVo.setCandelete(valueAddDto.getCandelete());
			// 是否可修改
			escWayBillValueAddDetaillVo.setCanmodify(valueAddDto.getCanmodify());
			// 描述
			escWayBillValueAddDetaillVo.setDescription(valueAddDto.getDescription());
			// 金额
			escWayBillValueAddDetaillVo.setFee(valueAddDto.getFee());
			// 上限
			escWayBillValueAddDetaillVo.setMaxFee(valueAddDto.getMaxFee());
			// 下限
			escWayBillValueAddDetaillVo.setMinFee(valueAddDto.getMinFee());
			// 费用code
			escWayBillValueAddDetaillVo.setSubType(valueAddDto.getSubType());
			// 费用名称
			escWayBillValueAddDetaillVo.setSubTypeName(valueAddDto.getSubTypeName());
			
			escWayBillValueAddDetaillVoList.add(escWayBillValueAddDetaillVo);
			
		}
		return escWayBillValueAddDetaillVoList;
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 273279-FOSS-liding
	 * @date 2016-04-20 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam(ESCValueAddVo bean) {
		
	
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getOriginalOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getDestinationOrgCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode());// 产品CODE
		//TODO
		//queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(nullBigDecimalToZero(bean.getWeight()));// 重量
		queryDto.setVolume(nullBigDecimalToZero(bean.getVolume()));// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
//		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * 
	 * 判断数据是否为空，如果为空则返回零
	 * 
	 * @author 273279-foss-liding
	 * @date 2016-4-20 下午2:13:14
	 */
	public  BigDecimal nullBigDecimalToZero(BigDecimal big) {
		if (big == null) {
			return BigDecimal.ZERO;
		} else {
			return big;
		}
	}


}
