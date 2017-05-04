package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class OuterPriceDao extends SqlSessionDaoSupport implements IOuterPriceDao{
	
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.OuterPriceEntityMapper.";
	
	/**
	 * 查询偏线价格方案
	 */
	@Override
	public Long queryOuterPriceVoBatchInfoCount(
			OuterPriceCondtionDto outerPriceCondtionDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryOuterPriceVoBatchInfoCount", outerPriceCondtionDto);
	}
	
	/**
	 * 查询偏线价格方案
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OuterPricePlanDto> queryOuterPriceVoBatchInfo(
			OuterPriceCondtionDto outerPriceCondtionDto, int start, int limit) {
		if (start == 0 && limit == 0) {
			return this.getSqlSession().selectList(NAMESPACE+"queryOuterPriceVoBatchInfo", outerPriceCondtionDto);
		} else {
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE+"queryOuterPriceVoBatchInfo", outerPriceCondtionDto, rowBounds);
		}
	}

	@Override
	public void updateActiveToYesOrNo(String outerPriceId ,String active) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("active", active);
		paraMap.put("outerPriceId", outerPriceId);
		paraMap.put("versionNo", new Date().getTime());
		this.getSqlSession().update(NAMESPACE+"updateActiveToYesOrNo", paraMap);
	}


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
	@SuppressWarnings("unchecked")
	public List<OuterPriceEntity> queryOuterPriceByName(String name) {
		String sql = NAMESPACE + "queryOuterPriceByName";	
		return getSqlSession().selectList(sql, name);
	}

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
	public int insertSelective(OuterPriceEntity record) {
		String sql = NAMESPACE + "insertSelective";		
		return getSqlSession().insert(sql, record);
	}
	
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
	public int updateByPrimaryKeySelective(OuterPriceEntity record) {
		String sqlAddress = NAMESPACE + "updateOuterPriceByPrimaryKey";		
		return getSqlSession().update(sqlAddress, record);				
	}
	
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
	public int updateOuterPriceEndTime(OuterPriceEntity record) {
		String sqlAddress = NAMESPACE + "updateOuterPriceEndTime";		
		return getSqlSession().update(sqlAddress, record);
	}
	
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
	public int updateOuterPriceActiveById(List<String> ids, String yesOrNo) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("active", yesOrNo);
		paraMap.put("ids", ids);
		paraMap.put("versionNo", new Date().getTime());
		return getSqlSession().update(NAMESPACE + "updateOuterPriceActiveById", paraMap);
	}
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
	public int copyOuterPrice(OuterPriceEntity record){
		String sql = NAMESPACE + "insertCopyOuterPrice";		
		return getSqlSession().insert(sql, record);
	}
	
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
	public int deleteByPrimaryKey(List<String> ids) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("ids", ids);
		return getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", paraMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OuterPriceEntity searchOuterPriceByArgument(String partialLineCode,
			String outFieldCode, String productCode, Date billTime) {
		@SuppressWarnings("rawtypes")
		Map paramenterMap = new HashMap();
		paramenterMap.put("active", FossConstants.ACTIVE);
		paramenterMap.put("partialLineCode", partialLineCode);
		paramenterMap.put("outFieldCode", outFieldCode);
		paramenterMap.put("productCode", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
		paramenterMap.put("receiveDate", billTime);
		List<OuterPriceEntity> list = getSqlSession().selectList(NAMESPACE + "searchOuterPriceByArgument",paramenterMap);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

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
	public OuterPriceEntity selectByPrimaryKey(String id) {
		String sql = NAMESPACE + "selectByPrimaryKey";		
		return (OuterPriceEntity)getSqlSession().selectOne(sql, id);				
	}

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
	public OuterPricePlanDto selectById(String id) {
		String sql = NAMESPACE + "selectById";		
		return (OuterPricePlanDto)getSqlSession().selectOne(sql, id);				
	}

	/**
	 * 验证当前的方案名是否已经存在
	 */
	@Override
	public int checkIsExistName(OuterPriceEntity outerPriceEntity) {
		String sql = NAMESPACE + "checkIsExistName";
		return (Integer)getSqlSession().selectOne(sql, outerPriceEntity);
	}
	
	/**
	 *  查询偏线价格
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param paramEntity
	  * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OuterPriceEntity> searchOuterPriceByParamEntity(OuterPriceEntity paramEntity) {
		List<OuterPriceEntity> list = getSqlSession().selectList(NAMESPACE + "searchOuterPriceByParamEntity",paramEntity);
		if(CollectionUtils.isNotEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	
	
}
