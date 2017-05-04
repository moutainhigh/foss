package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;


public interface IOuterPriceDao {


	Long queryOuterPriceVoBatchInfoCount(
			OuterPriceCondtionDto outerPriceCondtionDto);

	public List<OuterPricePlanDto> queryOuterPriceVoBatchInfo(
			OuterPriceCondtionDto outerPriceCondtionDto, int start, int limit);

	void updateActiveToYesOrNo(String outerPriceId, String yesOrNo);

	/**
	 * <p>
	 * Description:根据NAME查询偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2013-08-20
	 * @param name
	 * @return
	 * List<MarketingEventDto>
	 */
	public List<OuterPriceEntity> queryOuterPriceByName(String name);
	
	/**
	 * 检查当前方案名是否已经存在
	 * @param outerPriceEntity 待验证的方案
	 * @return
	 */
	public int checkIsExistName(OuterPriceEntity outerPriceEntity);

	/**
    * <p>
    * Description:新增记录（内容不为空的字段）<br />
    * </p>
    * @author Rosanu
    * @version 0.1 2012-10-18
    * @param record
    * @return
    * int
    */
	public int insertSelective(OuterPriceEntity record);
	
	/**
     * <p>
     * Description:根据主键更新内容不为空的字段<br />
     * </p>
     * @author Rosanu
     * @version 0.1 2012-10-18
     * @param record
     * @return
     * int
     */
	public int updateByPrimaryKeySelective(OuterPriceEntity record);

	/**
     * <p>
     * Description:复制偏线价格方案<br />
     * </p>
     * @author Rosanu
     * @version 0.1 2012-10-18
     * @param id
     * @return
     * int
     */
	public int copyOuterPrice(OuterPriceEntity record);
	
	/**
	 * <p>
	 * Description:根据主键Id删除记录<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	public int deleteByPrimaryKey(List<String> ids);

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
	public OuterPriceEntity selectByPrimaryKey(String id);
	

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
	public int updateOuterPriceActiveById(List<String> ids, String yesOrNo);
	

	/**
	 * 查询唯一偏线实体 
	 * @param partialLineCode 偏线CODE
	 * @param outFieldCode  外场CODE
	 * @param productCode  产品CODE
	 * @param billTime  业务时间
	 * @return
	 */
	OuterPriceEntity searchOuterPriceByArgument(String partialLineCode,String outFieldCode, String productCode, Date billTime);

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
	public OuterPricePlanDto selectById(String id);
	
	/**
     * <p>
     * Description: 修改截止时间<br />
     * </p>
     * @author Rosanu
     * @version 0.1 2012-10-18
     * @param record
     * @return
     * int
     */
	public int updateOuterPriceEndTime(OuterPriceEntity record);
	
	/**
	 *  查询偏线价格
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param paramEntity
	  * @return
	 */
	public List<OuterPriceEntity> searchOuterPriceByParamEntity(OuterPriceEntity paramEntity);
	
}
