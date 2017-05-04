package com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.AbstractCalculateAdvisor;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.ResultRedirect;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.annotation.BeforeAdv;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.AbstractCalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.dispatcher.IBillCalculateDispatcher;

public class BillCalculateSwitchMethodAdvisor extends AbstractCalculateAdvisor{
	
	private IBillCalculateDispatcher billCalculateDispatcher;
	
	/**
	 * 运费
	 * searchProductPriceList: <br/>
	 * 
	 * Date:2014-6-30上午9:58:19
	 * @author 157229-zxy
	 * @param queryBillCacilateDto
	 * @return
	 * @throws Throwable
	 * @since JDK 1.6
	 */
	@BeforeAdv(value = "searchProductPriceList")
	public ResultRedirect searchProductPriceList(QueryBillCacilateDto queryBillCacilateDto) throws Throwable{
		AbstractCalculateControl calculateControl = (AbstractCalculateControl)billCalculateDispatcher.dispatch(queryBillCacilateDto.getProductCode());
		if(calculateControl != null){
			List<ProductPriceDto> resultBillCalculateDtoLst = calculateControl.handlerFRT(queryBillCacilateDto);
			return new ResultRedirect(resultBillCalculateDtoLst);
		}else
			return null;
	}
	
	/**
	 * 增值服务
	 * searchValueAddPriceList: <br/>
	 * 
	 * Date:2014-6-30上午9:58:09
	 * @author 157229-zxy
	 * @param queryBillCacilateValueAddDto
	 * @return
	 * @throws Throwable
	 * @since JDK 1.6
	 */
	@BeforeAdv(value = "searchValueAddPriceList")
	public ResultRedirect searchValueAddPriceList(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) throws Throwable{
		AbstractCalculateControl calculateControl = (AbstractCalculateControl)billCalculateDispatcher.dispatch(queryBillCacilateValueAddDto.getProductCode());
		if(calculateControl != null){
			List<ValueAddDto> resultBillCalculateDtoLst = calculateControl.handlerValueAdd(queryBillCacilateValueAddDto);
			return new ResultRedirect(resultBillCalculateDtoLst);
		}else
			return null;
	}
	
	public IBillCalculateDispatcher getBillCalculateDispatcher() {
		return billCalculateDispatcher;
	}

	public void setBillCalculateDispatcher(
			IBillCalculateDispatcher billCalculateDispatcher) {
		this.billCalculateDispatcher = billCalculateDispatcher;
	}

}
