package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
/**
 * 降价发券方案VO
 * @author dujunhui-187862
 * @date 2014-9-23 下午5:56:35
 * @since
 * @version
 */
public class PriceCouponVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	
	/**
	 * 市场活动实体类
	 */
	private MarketingSchemeEntity marketingSchemeEntity;
	
	/**
	 * 市场活动实体类LIST
	 */
	private List<MarketingSchemeEntity> marketingSchemeEntityList;
	/**
	 * 当前服务器时间
	 */
	private Date nowTime;
	/**
	 * 导入条数
	 */
	private int importTotal;
	
	
	public MarketingSchemeEntity getMarketingSchemeEntity() {
		return marketingSchemeEntity;
	}

	public void setMarketingSchemeEntity(MarketingSchemeEntity marketingSchemeEntity) {
		this.marketingSchemeEntity = marketingSchemeEntity;
	}

	public List<MarketingSchemeEntity> getMarketingSchemeEntityList() {
		return marketingSchemeEntityList;
	}

	public void setMarketingSchemeEntityList(
			List<MarketingSchemeEntity> marketingSchemeEntityList) {
		this.marketingSchemeEntityList = marketingSchemeEntityList;
	}

	/**
	 * @return  the importTotal
	 */
	public int getImportTotal() {
		return importTotal;
	}

	/**
	 * @param importTotal the importTotal to set
	 */
	public void setImportTotal(int importTotal) {
		this.importTotal = importTotal;
	}

	/**
	 * 优惠券起始目的组织网点实体类
	 */
	private DiscountOrgEntity couponOrgEntity;
	
	/**
	 * 折扣起始目的组织网点实体类List(起始)
	 */
	private List<DiscountOrgEntity> startCouponOrgEntityList;
	
	/**
	 * 折扣起始目的组织网点实体类List（结束）
	 */
	private List<DiscountOrgEntity> endCouponOrgEntityList;
//	
//	/**
//	 * 计费规则实体类
//	 */
//	private PriceValuationEntity valuationEntity;
//	
//	/**
//	 * 计价方式明细
//	 */
//	private PriceCriteriaDetailEntity criteriaDetailEntity;
//	
    /**
     * 查询条件(降价发券方案明细Dto)
     */
	private PriceCouponDto priceCouponDto;
	/**
	 * 查询结果集
	 */
	private List<PriceCouponDto> priceCouponDtoList;
//	/**
//	 * 出发区域结果集
//	 */
//	private List<CouponOrgEntity> startRegionList;
//
//	/**
//	 * 到达区域结果集
//	 */
//	private List<CouponOrgEntity> arrvRegionList;
	/**
	 * 删除和激活
	 */
    private List<String> priceCouponIds;
//    /**
//     * 增值服务LIST
//     */
//    private List<PriceEntity> priceEntityList;
//    
    /**
     * 查询城市条件
     */
    private AdministrativeRegionsEntity administrativeRegionsEntity;
    /**
     * 返回结果城市
     */
    private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
    /**
     * 出发全网
     */
    private String startAllNet;
    /**
     * 到达全网
     */
    private String arrvAllNet;
	/**
	 * @return the startAllNet
	 */
	public String getStartAllNet() {
		return startAllNet;
	}

	/**
	 * @param startAllNet the startAllNet to set
	 */
	public void setStartAllNet(String startAllNet) {
		this.startAllNet = startAllNet;
	}

	/**
	 * @return the arrvAllNet
	 */
	public String getArrvAllNet() {
		return arrvAllNet;
	}

	/**
	 * @param arrvAllNet the arrvAllNet to set
	 */
	public void setArrvAllNet(String arrvAllNet) {
		this.arrvAllNet = arrvAllNet;
	}

	/**
	 * 获取 查询城市条件.
	 *
	 * @return the 查询城市条件
	 */
	public AdministrativeRegionsEntity getAdministrativeRegionsEntity() {
		return administrativeRegionsEntity;
	}

	/**
	 * 设置 查询城市条件.
	 *
	 * @param administrativeRegionsEntity the new 查询城市条件
	 */
	public void setAdministrativeRegionsEntity(
			AdministrativeRegionsEntity administrativeRegionsEntity) {
		this.administrativeRegionsEntity = administrativeRegionsEntity;
	}

	/**
	 * 获取 返回结果城市.
	 *
	 * @return the 返回结果城市
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}

	/**
	 * 设置 返回结果城市.
	 *
	 * @param administrativeRegionsEntityList the new 返回结果城市
	 */
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}

//	/**
//	 * 获取 增值服务LIST.
//	 *
//	 * @return the 增值服务LIST
//	 */
//	public List<PriceEntity> getPriceEntityList() {
//		return priceEntityList;
//	}
//
//	/**
//	 * 设置 增值服务LIST.
//	 *
//	 * @param priceEntityList the new 增值服务LIST
//	 */
//	public void setPriceEntityList(List<PriceEntity> priceEntityList) {
//		this.priceEntityList = priceEntityList;
//	}
//
//	/**
//	 * 获取 市场活动实体类LIST.
//	 *
//	 * @return the 市场活动实体类LIST
//	 */
//	public List<MarketingEventEntity> getMarketingEventEntityList() {
//		return marketingEventEntityList;
//	}
//
//	/**
//	 * 设置 市场活动实体类LIST.
//	 *
//	 * @param marketingEventEntityList the new 市场活动实体类LIST
//	 */
//	public void setMarketingEventEntityList(
//			List<MarketingEventEntity> marketingEventEntityList) {
//		this.marketingEventEntityList = marketingEventEntityList;
//	}
//
	/**
	 * 获取 删除和激活.
	 *
	 * @return the 删除和激活
	 */
	public List<String> getPriceCouponIds() {
		return priceCouponIds;
	}

	/**
	 * 设置 删除和激活.
	 *
	 * @param priceCouponIds the new 删除和激活
	 */
	public void setPriceCouponIds(List<String> priceCouponIds) {
		this.priceCouponIds = priceCouponIds;
	}
//
//	/**
//	 * 获取 市场活动实体类.
//	 *
//	 * @return the 市场活动实体类
//	 */
//	public MarketingEventEntity getMarketingEventEntity() {
//		return marketingEventEntity;
//	}
//
//	/**
//	 * 设置 市场活动实体类.
//	 *
//	 * @param marketingEventEntity the new 市场活动实体类
//	 */
//	public void setMarketingEventEntity(MarketingEventEntity marketingEventEntity) {
//		this.marketingEventEntity = marketingEventEntity;
//	}
//
//	/**
//	 * 获取 渠道实体类.
//	 *
//	 * @return the 渠道实体类
//	 */
//	public MarketingEventChannelEntity getChannelEntity() {
//		return channelEntity;
//	}
//
//	/**
//	 * 设置 渠道实体类.
//	 *
//	 * @param channelEntity the new 渠道实体类
//	 */
//	public void setChannelEntity(MarketingEventChannelEntity channelEntity) {
//		this.channelEntity = channelEntity;
//	}
//
	/**
	 * 获取 折扣起始目的组织网点实体类.
	 */
	public DiscountOrgEntity getCouponOrgEntity() {
		return couponOrgEntity;
	}
	/**
	 * 设置 折扣起始目的组织网点实体类.
	 */
	public void setCouponOrgEntity(DiscountOrgEntity couponOrgEntity) {
		this.couponOrgEntity = couponOrgEntity;
	}

//	/**
//	 * 获取 计费规则实体类.
//	 *
//	 * @return the 计费规则实体类
//	 */
//	public PriceValuationEntity getValuationEntity() {
//		return valuationEntity;
//	}
//
//	/**
//	 * 设置 计费规则实体类.
//	 *
//	 * @param valuationEntity the new 计费规则实体类
//	 */
//	public void setValuationEntity(PriceValuationEntity valuationEntity) {
//		this.valuationEntity = valuationEntity;
//	}
//
//	/**
//	 * 获取 计价方式明细.
//	 *
//	 * @return the 计价方式明细
//	 */
//	public PriceCriteriaDetailEntity getCriteriaDetailEntity() {
//		return criteriaDetailEntity;
//	}
//
//	/**
//	 * 设置 计价方式明细.
//	 *
//	 * @param criteriaDetailEntity the new 计价方式明细
//	 */
//	public void setCriteriaDetailEntity(
//			PriceCriteriaDetailEntity criteriaDetailEntity) {
//		this.criteriaDetailEntity = criteriaDetailEntity;
//	}

	

	/**
	 * 获取 查询条件.
	 *
	 * @return the 查询条件
	 */
	public PriceCouponDto getPriceCouponDto() {
		return priceCouponDto;
	}

	/**
	 * 设置 查询条件.
	 *
	 * @param priceCouponDto the new 查询条件
	 */
	public void setPriceCouponDto(PriceCouponDto priceCouponDto) {
		this.priceCouponDto = priceCouponDto;
	}

//	/**
//	 * 获取 出发区域结果集.
//	 *
//	 * @return the 出发区域结果集
//	 */
//	public List<CouponOrgEntity> getStartRegionList() {
//		return startRegionList;
//	}
//
//	/**
//	 * 设置 出发区域结果集.
//	 *
//	 * @param startRegionList the new 出发区域结果集
//	 */
//	public void setStartRegionList(List<CouponOrgEntity> startRegionList) {
//		this.startRegionList = startRegionList;
//	}
//
//	/**
//	 * 获取 到达区域结果集.
//	 *
//	 * @return the 到达区域结果集
//	 */
//	public List<CouponOrgEntity> getArrvRegionList() {
//		return arrvRegionList;
//	}
//
//	/**
//	 * 设置 到达区域结果集.
//	 *
//	 * @param arrvRegionList the new 到达区域结果集
//	 */
//	public void setArrvRegionList(List<CouponOrgEntity> arrvRegionList) {
//		this.arrvRegionList = arrvRegionList;
//	}

	/**
	 * 获取 查询结果集.
	 *
	 * @return the 查询结果集
	 */
	public List<PriceCouponDto> getPriceCouponDtoList() {
		return priceCouponDtoList;
	}

	/**
	 * 设置 查询结果集.
	 *
	 * @param priceCouponDtoList the new 查询结果集
	 */
	public void setPriceCouponDtoList(List<PriceCouponDto> priceCouponDtoList) {
		this.priceCouponDtoList = priceCouponDtoList;
	}

//	/**
//	 * 获取 渠道实体类.
//	 *
//	 * @return the 渠道实体类
//	 */
//	public List<MarketingEventChannelEntity> getChannelEntityList() {
//		return channelEntityList;
//	}
//
//	/**
//	 * 设置 渠道实体类.
//	 *
//	 * @param channelEntityList the new 渠道实体类
//	 */
//	public void setChannelEntityList(
//			List<MarketingEventChannelEntity> channelEntityList) {
//		this.channelEntityList = channelEntityList;
//	}
//
	/**
	 * 获取 折扣起始目的组织网点实体类LIST(起始).
	 *
	 * @return the 折扣起始目的组织网点实体类LIST(起始)
	 */
	public List<DiscountOrgEntity> getStartCouponOrgEntityList() {
		return startCouponOrgEntityList;
	}
	/**
	 * 设置 折扣起始目的组织网点实体类LIST(起始).
	 *
	 * @param startCouponOrgEntityList the new 折扣起始目的组织网点实体类LIST(起始)
	 */
	public void setStartCouponOrgEntityList(
			List<DiscountOrgEntity> startCouponOrgEntityList) {
		this.startCouponOrgEntityList = startCouponOrgEntityList;
	}
	/**
	 * 获取 折扣起始目的组织网点实体类LIST（结束）.
	 *
	 * @return the 折扣起始目的组织网点实体类LIST（结束）
	 */
	public List<DiscountOrgEntity> getEndCouponOrgEntityList() {
		return endCouponOrgEntityList;
	}
	/**
	 * 设置 折扣起始目的组织网点实体类LIST（结束）.
	 *
	 * @param endCouponOrgEntityList the new 折扣起始目的组织网点实体类LIST（结束）
	 */
	public void setEndCouponOrgEntityList(
			List<DiscountOrgEntity> endCouponOrgEntityList) {
		this.endCouponOrgEntityList = endCouponOrgEntityList;
	}

	/**
	 * @return  the nowTime
	 */
	public Date getNowTime() {
		return nowTime;
	}

	/**
	 * @param nowTime the nowTime to set
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

}