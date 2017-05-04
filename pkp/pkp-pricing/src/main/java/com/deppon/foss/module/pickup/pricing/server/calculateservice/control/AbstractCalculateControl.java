package com.deppon.foss.module.pickup.pricing.server.calculateservice.control;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;

/**
 * 挖掘数据,交由计算执行器处理
 * 目前针对不修改数据模型的情况
 * @author 157229-zxy
 *
 */
public abstract class AbstractCalculateControl implements ICalculateControl{
	
	/**
	 * 计算增值和运费的集合
	 * handler: <br/>
	 * 
	 * Date:2014-6-30下午6:25:27
	 * @author 157229-zxy
	 * @param billCalculateDto
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<GuiResultBillCalculateDto> handler(GuiQueryBillCalculateDto billCalculateDto);
	
	/**
	 * 计算运费
	 * handlerFRT: <br/>
	 * 
	 * Date:2014-6-30下午6:24:58
	 * @author 157229-zxy
	 * @param queryBillCacilateDto
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<ProductPriceDto> handlerFRT(QueryBillCacilateDto queryBillCacilateDto);

	/**
	 * 计算增值
	 * handlerValueAdd: <br/>
	 * 
	 * Date:2014-6-30下午6:25:03
	 * @author 157229-zxy
	 * @param queryBillCacilateValueAddDto
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<ValueAddDto> handlerValueAdd(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
}
