package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.OuterPriceVo;

public interface IOuterPriceService {

	Long queryOuterPriceVoBatchInfoCount(
			OuterPriceCondtionDto outerPriceCondtionDto);

	public List<OuterPricePlanDto> queryOuterPriceVoBatchInfo(
			OuterPriceCondtionDto outerPriceCondtionDto, int start, int limit);

	void updateActiveToN(String outerPriceId);

	void immediatelyActiveOuterPrice(String outerPriceId, String yesOrNo, Date effectiveTime);
	
	OuterPriceVo addOuterPrice(OuterPriceEntity outerPriceEntity);
	
	/**
	 * <p>修改偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	public void updateOuterPrice(OuterPriceEntity record);
	
	/**
	 * <p>复制偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	public OuterPriceVo copyOuterPrice(String id, String name);

	
	/**
	 * <p>删除偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:02:48
	 * 
	 * @param marketingEventId
	 * 
	 * @see
	 */
	public void deleteByPrimaryKey(List<String> ids);
	
	/**
	 * 查询唯一偏线实体
	 * @param partialLineCode 代理CODE
	 * @param outFieldCode  外场
	 * @param productCode   产品 
	 * @param billTime  业务时间
	 * @return
	 */
	public OuterPriceEntity searchOuterPriceByArgument(String partialLineCode,String outFieldCode,String productCode,Date billTime);

	/**
	 * <p>
	 * Description:根据主键查询记录<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * OuterPriceEntity
	 */
	public OuterPriceVo selectByPrimaryKey(String id);
	
	/**
	 * <p>
	 * Description:根据主键Id批量激活偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	public void updateOuterPriceActiveById(List<OuterPricePlanDto> opps, String yesOrNo);
	
	/**
	 * <p>
	 * Description:导出偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return ExportResource
	 * int
	 */
	public ExportResource exportOuterPrice(OuterPriceCondtionDto dto);

	/**
     * <p>
     * Description:根据ID查询记录<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param id
     * @return
     * OuterPriceEntity
     */
	public OuterPriceVo selectById(String id);


	/**
	 * 
	 * @Description: 导入时效方案 Company:IBM
	 * 
	 * @author Rosanu
	 * 
	 * @date 2012-12-24 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public void importOuterPrice(Workbook book);

	/**
	 * 立即中止偏线价格方案
	 * @param outerPriceId	中止方案id
	 * @param effectiveTime 中止时间
	 */
	public void immediatelyStopOuterPrice(String outerPriceId, Date effectiveTime);

}
