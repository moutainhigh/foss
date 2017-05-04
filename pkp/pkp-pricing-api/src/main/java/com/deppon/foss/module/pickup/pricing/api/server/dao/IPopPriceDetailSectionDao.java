package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;


/**
 * 计价方式分段明细，负责对相关明细的管理
 * 
 * @author POP-Team-LuoMengxiang
 * 
 * @date 2014/10/21
 */
   public interface IPopPriceDetailSectionDao {
	
	 /**
	   * 插入一条计价方式分段明细的记录
	   * 
	   * @author POP-Team-LuoMengxiang
	   * 
	   * @category 2014/10/21
	   */
	public void insertPriceDetailSection(PopPriceDetailSectionEntity record);
	/**
	 * 根据id查询计价方式分段明细的记录
	 * 
	 * @author POP-Team-LuoMengxiang
	 * 
	 * @date 2014/10/28
	 */
	public  List<PopPriceDetailSectionEntity>  selectById(String  id);
	/**
	 * <p>[POP]根据条件查询全部分段价格信息</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-7 上午9:10:31
	 * @param popPriceDetailSectionEntity
	 * @return
	 * @see
	 */
	List<PopPriceDetailSectionEntity> querySectionListByCondition(
			PopPriceDetailSectionEntity popPriceDetailSectionEntity);
	/**
	 * 根据规则ID查询计价方式分段明细的记录
	 * 
	 * @author POP-Team-LuoMengXiang
	 * 
	 * @date 2014/10/28
	 */
	public  List<PopPriceDetailSectionEntity>  selectByValuationId(String valuationId);
	/**
	 * 根据规则ID删除计价方式分段明细的记录
	 * 
	 * @author POP-Team-LuoMengxiang
	 * 
	 * @date 2014.11.1
	 */
	public void  deleteByValuationId(String valuationId);
	/**
	 * 根据规则ID修改计价方式分段明细的记录
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @date 2014.11.4
	 */
	public void updateByValuationId(PopPriceDetailSectionEntity popPriceDetailSectionEntity);
	/**
	 * 根据分段ID删除计价分段明细信息
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @date  2014.11.11
	 */
	public void deleteBySectionId(String sectionID);
	/**
	 * 修改操作的新增计价分段明细
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @date  2014.11.12
	 */
       public void insertSectionDetail(PopPriceDetailSectionEntity popPriceDetailSectionEntity);
       
    /**
   	 * <p>根据明细id查询分段信息<p>
   	 * 
   	 * @author Pop-Team-Luomengxiang
   	 * 
   	 * @date  2015.01.24
   	 */
       public List<PopPriceDetailSectionEntity> selectByCriteriaId(String criteriaId);
	
	/**
     * <p>根据价格方案id查询出价格分段数最多的分段价格明细记录信息</p> 
     * @author foss-148246-sunjianbo
     * @date 2015-2-5 上午9:05:54
     * @param pricePlanId
     * @return
     * @see
     */
    public List<PopPriceDetailSectionEntity> querySectionScopeByPricePlanId(
			String pricePlanId);
   }
