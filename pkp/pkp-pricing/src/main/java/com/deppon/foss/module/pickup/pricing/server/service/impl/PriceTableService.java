package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEffectiveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ProvinceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PDFPriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartnerPriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartnerPriceTables;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTableHeadEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTables;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.RegionEffectiveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.RegionPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryEffctiveBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPriceBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionEffectiveDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerEffectiveDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPriceDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class PriceTableService implements IPriceTableService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceTableService.class);
    @Inject
    IRegionService regionService;
    @Inject
    IProductService productService;
    @Inject
    IPricePlanService pricePlanService;
    @Inject
    IPriceValuationDao priceValuationDao;
    @Inject
    IPopPriceDetailSectionDao popPriceDetailSectionDao;
    @Inject
    IRegionPriceDao regionPriceDao;
    @Inject
    IRegionEffectiveDao regionEffectiveDao;
    /**
	 * 魔法数字1
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int ONE=1;
	/**
	 * 魔法数字2
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int TWO=2;
	/**
	 * 魔法数字3
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int THREE=3;
	/**
	 * 魔法数字4
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FOUR=4;
	/**
	 * 魔法数字5
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FIVE=5;
	/**
	 * 魔法数字6
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int SIX=6;
    
	/**
	 * 查询界面查询价格表接口
	 * @param startDeptCode
	 * @param productType
	 * @param effectiveDate
	 * @param start
	 * @param limit
	 * @return
	 */
    @Override
    // PriceTables
	public PriceTables queryPriceTableListInfo(String startDeptCode,
	String productType, Date currentDateTime,int start, int limit){
    	if(StringUtil.isEmpty(startDeptCode)){
			return null;
    	}
    	if(null==currentDateTime){
			currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
		List<PriceTableEntity> list = queryPriceTableList(priceRegionId,startDeptCode,productType, currentDateTime);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		//正序  
		Collections.sort(list, new PriceTableEntityComparator());  
		int size = list.size();
		List<PriceTableEntity> entitys = new ArrayList<PriceTableEntity>();
		if(size>start){//存在数据
			for(int i=start;i<start+limit&&i<size;i++){
				entitys.add(list.get(i));
			}
		}
		PriceTables priceTables = new PriceTables();
		priceTables.setPriceTableEntitys(entitys);
		priceTables.setTotal(size);
		return priceTables;
    }
    /**
     * <p>[POP]查询界面查询价格表接口（包括分段對應價格表信息2-6段）</p> 
     * @author foss-148246-sunjianbo
     * @date 2014-10-30 上午11:38:50
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @param sectionID
     * @param start
     * @param limit
     * @return objArray[0] PriceTables && objArray[1] Map<String,String>
     * @see
     */
    @Override
	public Object[] queryPriceTableListInfo(String startDeptCode,
	String productType, Date currentDateTime, int sectionID, int start, int limit){
    	if(StringUtil.isEmpty(startDeptCode) || (sectionID < 1 && sectionID > SIX)){
			return null;
    	}
    	if(null==currentDateTime){
			currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
    	//  查询价格表全部信息
    	Object[] objArray = queryPriceTableList(priceRegionId,startDeptCode,productType, currentDateTime,sectionID);
		@SuppressWarnings("unchecked")
		List<PriceTableEntity> list = (List<PriceTableEntity>) objArray[0];
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		//排序  省份按照省份首字母进行排序，同一省份按照重货价格从小到大依次排序，重货价格相同的按照目的城市首字母进行排序
		Collections.sort(list, new PriceTableEntityComparator());  
		//分页
		int size = list.size();
		List<PriceTableEntity> entitys = new ArrayList<PriceTableEntity>();
		if(size>start){//存在数据
			for(int i=start;i<start+limit&&i<size;i++){
				entitys.add(list.get(i));
			}
		}
		PriceTables priceTables = new PriceTables();
		priceTables.setPriceTableEntitys(entitys);
		priceTables.setTotal(size);
		objArray[0] = priceTables;
		return objArray;
    }
    
    /**
     * 获得动态表头信息
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @return
     */
    @Override
    public PriceTableHeadEntity getTableHead(String startDeptCode,
			String productType, Date currentDateTime){
    	PriceTableHeadEntity head = new PriceTableHeadEntity();
    	if(null==currentDateTime){
    		currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
    	if(StringUtil.isEmpty(priceRegionId)){ 
    		return null;
    	}
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(priceRegionId,PricingConstants.PRICING_REGION);
    	if(null==priceRegionEntity){ 
    		return null;
    	}
    	String name = priceRegionEntity.getRegionName();
    	if(StringUtil.isEmpty(name)){
    		return null;
    	}
    	head.setCityName(name);
    	head.setName(name+"汽运价格表");
    	head.setCurrentDateTime(currentDateTime);
		if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
			head.setProductType("精准卡航/精准城运");
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
			head.setProductType("精准汽运（长途）/精准汽运（短途）");
		}
    	return head;
    }
	/**
	 * 导出PDF格式价格表
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @return
	 */
    @Override
	public List<PDFPriceTableEntity> expPDFPriceTableList(String startDeptCode,
			String productType, Date currentDateTime){
    	List<PDFPriceTableEntity> tabletEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	if(null==currentDateTime){
    		currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
		List<PriceTableEntity> list = queryPriceTableList(priceRegionId,startDeptCode,productType, currentDateTime);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}

    	List<PriceTableEntity> longList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> shortList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> longFastList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> shortFastList = new ArrayList<PriceTableEntity>();
		//三级产品-精准汽运(长途)
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT)){
				longList.add(tabletntity);
			}
		}
		//三级产品-精准汽运(短途)
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT)){
				shortList.add(tabletntity);
			}
		}
		//三级产品-精准卡航
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)){
				longFastList.add(tabletntity);
			}
		}
		//三级产品-精准城运
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)){
				shortFastList.add(tabletntity);
			}
		}
		if(CollectionUtils.isNotEmpty(longList)){
			//正序  
			Collections.sort(longList, new PriceTableEntityComparator());  
			//转换行
			 longEtitys = convertRow(longList);
		}
		if(CollectionUtils.isNotEmpty(shortList)){
			//正序  
			Collections.sort(shortList, new PriceTableEntityComparator());  
			//转换行
			shortEtitys = convertRow(shortList);
		}
		if(CollectionUtils.isNotEmpty(longFastList)){
			//正序  
			Collections.sort(longFastList, new PriceTableEntityComparator());  
			//转换行
			longFastEtitys = convertRow(longFastList);
		}
		if(CollectionUtils.isNotEmpty(shortFastList)){
			//正序  
			Collections.sort(shortFastList, new PriceTableEntityComparator());  
			//转换行
			shortFastEtitys = convertRow(shortFastList);
		}

		if(CollectionUtils.isNotEmpty(longEtitys)){
			tabletEtitys.addAll(mergeCells(longEtitys));
		}
		if(CollectionUtils.isNotEmpty(shortEtitys)){
			tabletEtitys.addAll(mergeCells(shortEtitys));
		}
		if(CollectionUtils.isNotEmpty(longFastEtitys)){
			tabletEtitys.addAll(mergeCells(longFastEtitys));
		}
		if(CollectionUtils.isNotEmpty(shortFastEtitys)){
			tabletEtitys.addAll(mergeCells(shortFastEtitys));
		}
		return tabletEtitys;
	}
	
    /** 
     * <p>[POP]导出PDF格式价格表</p> 
     * @author foss-148246-sunjianbo
     * @date 2014-11-11 下午2:43:12
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @return objArray[0] List<PDFPriceTableEntity> && objArray[1] Map<String,String>
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService#expPDFPriceTableList(java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
	public Object[] expPDFPriceTableList(String startDeptCode,
			String productType, Date currentDateTime, int sectionID){
    	List<PDFPriceTableEntity> tableEntitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	if(null==currentDateTime){
    		currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
    	Object[] objArray = queryPriceTableList(priceRegionId,startDeptCode,productType, currentDateTime,sectionID);
    	@SuppressWarnings("unchecked")
    	List<PriceTableEntity> list = (List<PriceTableEntity>) objArray[0];
		if(CollectionUtils.isEmpty(list)){
			return null;
		}

    	List<PriceTableEntity> longList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> shortList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> longFastList = new ArrayList<PriceTableEntity>();
    	List<PriceTableEntity> shortFastList = new ArrayList<PriceTableEntity>();
		//三级产品-精准汽运(长途)
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT)){
				longList.add(tabletntity);
			}
		}
		//三级产品-精准汽运(短途)
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT)){
				shortList.add(tabletntity);
			}
		}
		//三级产品-精准卡航
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)){
				longFastList.add(tabletntity);
			}
		}
		//三级产品-精准城运
		for(PriceTableEntity tabletntity :list){
			if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)){
				shortFastList.add(tabletntity);
			}
		}
		if(CollectionUtils.isNotEmpty(longList)){
			//正序  
			Collections.sort(longList, new PriceTableEntityComparator());  
			//转换行
			 longEtitys = convertRow(longList);
		}
		if(CollectionUtils.isNotEmpty(shortList)){
			//正序  
			Collections.sort(shortList, new PriceTableEntityComparator());  
			//转换行
			shortEtitys = convertRow(shortList);
		}
		if(CollectionUtils.isNotEmpty(longFastList)){
			//正序  
			Collections.sort(longFastList, new PriceTableEntityComparator());  
			//转换行
			longFastEtitys = convertRow(longFastList);
		}
		if(CollectionUtils.isNotEmpty(shortFastList)){
			//正序  
			Collections.sort(shortFastList, new PriceTableEntityComparator());  
			//转换行
			shortFastEtitys = convertRow(shortFastList);
		}

		if(CollectionUtils.isNotEmpty(longEtitys)){
			tableEntitys.addAll(mergeCells(longEtitys));
		}
		if(CollectionUtils.isNotEmpty(shortEtitys)){
			tableEntitys.addAll(mergeCells(shortEtitys));
		}
		if(CollectionUtils.isNotEmpty(longFastEtitys)){
			tableEntitys.addAll(mergeCells(longFastEtitys));
		}
		if(CollectionUtils.isNotEmpty(shortFastEtitys)){
			tableEntitys.addAll(mergeCells(shortFastEtitys));
		}
		objArray[0] = tableEntitys;
		return objArray;
	}
    
    /**
     * <p>导出PDF格式价格表(合伙人汽运价格表导出)</p> 
     * @author 370613-foss-LianHe
     * @date 2016年9月29日 上午10:08:55
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @param sectionID
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService#expPDFPartnerPriceTableList(java.lang.String, java.lang.String, java.util.Date, int)
     */
    @Override
    public Object[] expPDFPartnerPriceTableList(String startDeptCode,
    		String productType, Date currentDateTime, int sectionID){
    	List<PDFPriceTableEntity> tableEntitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> longFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	List<PDFPriceTableEntity> shortFastEtitys = new ArrayList<PDFPriceTableEntity>();
    	if(null==currentDateTime){
    		currentDateTime= new Date();
    	}
    	// 根据出发部门编号定位价格逻辑区域ID
//    	String priceRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
    	Object[] objArray = queryPartnerPriceTableListInfo4PDF(startDeptCode,productType, currentDateTime,sectionID);
    	@SuppressWarnings("unchecked")
    	List<PartnerPriceTableEntity> list = (List<PartnerPriceTableEntity>) objArray[0];
    	if(CollectionUtils.isEmpty(list)){
    		return null;
    	}
    	
    	List<PartnerPriceTableEntity> longList = new ArrayList<PartnerPriceTableEntity>();
    	List<PartnerPriceTableEntity> shortList = new ArrayList<PartnerPriceTableEntity>();
    	List<PartnerPriceTableEntity> longFastList = new ArrayList<PartnerPriceTableEntity>();
    	List<PartnerPriceTableEntity> shortFastList = new ArrayList<PartnerPriceTableEntity>();
    	//三级产品-精准汽运(长途)
    	for(PartnerPriceTableEntity tabletntity :list){
    		if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT)){
    			longList.add(tabletntity);
    		}
    	}
    	//三级产品-精准汽运(短途)
    	for(PartnerPriceTableEntity tabletntity :list){
    		if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT)){
    			shortList.add(tabletntity);
    		}
    	}
    	//三级产品-精准卡航
    	for(PartnerPriceTableEntity tabletntity :list){
    		if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)){
    			longFastList.add(tabletntity);
    		}
    	}
    	//三级产品-精准城运
    	for(PartnerPriceTableEntity tabletntity :list){
    		if(tabletntity.getProductCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)){
    			shortFastList.add(tabletntity);
    		}
    	}
    	if(CollectionUtils.isNotEmpty(longList)){
    		//正序  
    		Collections.sort(longList, new PartnerPriceTableEntityComparator());  
    		//转换行
    		longEtitys = convertRow2(longList);
    	}
    	if(CollectionUtils.isNotEmpty(shortList)){
    		//正序  
    		Collections.sort(shortList, new PartnerPriceTableEntityComparator());  
    		//转换行
    		shortEtitys = convertRow2(shortList);
    	}
    	if(CollectionUtils.isNotEmpty(longFastList)){
    		//正序  
    		Collections.sort(longFastList, new PartnerPriceTableEntityComparator());  
    		//转换行
    		longFastEtitys = convertRow2(longFastList);
    	}
    	if(CollectionUtils.isNotEmpty(shortFastList)){
    		//正序  
    		Collections.sort(shortFastList, new PartnerPriceTableEntityComparator());  
    		//转换行
    		shortFastEtitys = convertRow2(shortFastList);
    	}
    	
    	if(CollectionUtils.isNotEmpty(longEtitys)){
    		tableEntitys.addAll(mergeCells(longEtitys));
    	}
    	if(CollectionUtils.isNotEmpty(shortEtitys)){
    		tableEntitys.addAll(mergeCells(shortEtitys));
    	}
    	if(CollectionUtils.isNotEmpty(longFastEtitys)){
    		tableEntitys.addAll(mergeCells(longFastEtitys));
    	}
    	if(CollectionUtils.isNotEmpty(shortFastEtitys)){
    		tableEntitys.addAll(mergeCells(shortFastEtitys));
    	}
    	objArray[0] = tableEntitys;
    	return objArray;
    }
    
	//1 查询价格表全部信息
	private List<PriceTableEntity> queryPriceTableList(String priceRegionId,String startDeptCode,
			String productType, Date currentDateTime) {
    	if(StringUtil.isEmpty(priceRegionId)){
    		return null;
    	}
    	// 根据出发部门编号定位时效逻辑区域
    	String effectiveRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRESCRIPTION_REGION);
    	// 根据出发价格逻辑区域ID定位价格方案
    	PricePlanEntity pricePlanEntity = pricePlanService.findPricePlanByRegionId(currentDateTime,priceRegionId, FossConstants.ACTIVE);
    	if(null==pricePlanEntity){
    		return null;
    	}
    	// 根据价格方案ID获取价格方案明细
		List<RegionPriceEntity> regionPriceEntityList = queryPriceList( pricePlanEntity.getId(),
				 productType, currentDateTime, FossConstants.ACTIVE);
		List<String> productList = new ArrayList<String>();
		if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
			//精准卡航
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
			//精准城运
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
			//精准汽运(长途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
			//精准汽运(短途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
		}
		if(productList.isEmpty()){
			return null;
		}
		
		// 根据出发区域ID获取时效方案明细
		List<RegionEffectiveEntity> regionEffectiveEntityList = queryEffctiveList(effectiveRegionId,productType,
				productList, currentDateTime, FossConstants.ACTIVE);
		List<PriceTableEntity> priceTableEntity = null;
		if(CollectionUtils.isNotEmpty(regionPriceEntityList)&&CollectionUtils.isNotEmpty(regionEffectiveEntityList)){			
			priceTableEntity = encapulatePriceTable(regionPriceEntityList,regionEffectiveEntityList,currentDateTime);
		}
		return priceTableEntity;
	}
	
	/**
	 * <p>[POP]查询价格表全部信息</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-7 下午4:02:37
	 * @param priceRegionId
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @return objArray[0] List<PriceTableEntity> && objArray[1] Map<String,String>
	 * @see
	 */
	private Object[] queryPriceTableList(String priceRegionId,String startDeptCode,
			String productType, Date currentDateTime,int sectionID) {
		if(StringUtil.isEmpty(priceRegionId)){
			return null;
		}
		// 根据出发部门编号定位时效逻辑区域
		String effectiveRegionId = regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRESCRIPTION_REGION);
		// 根据出发价格逻辑区域ID定位价格方案
		PricePlanEntity pricePlanEntity = pricePlanService.findPricePlanByRegionId(currentDateTime,priceRegionId, FossConstants.ACTIVE);
		if(null==pricePlanEntity){
			return null;
		}
		// 根据价格方案ID获取价格方案明细
		List<RegionPriceEntity> regionPriceEntityList = queryPriceList( pricePlanEntity.getId(),
				productType, currentDateTime, FossConstants.ACTIVE,sectionID);
		List<String> productList = new ArrayList<String>();
		if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
			//精准卡航
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
			//精准城运
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
			//精准汽运(长途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
			//精准汽运(短途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
		}
		if(productList.isEmpty()){
			return null;
		}
		
		// 根据出发区域ID获取时效方案明细
		List<RegionEffectiveEntity> regionEffectiveEntityList = queryEffctiveList(effectiveRegionId,productType,
				productList, currentDateTime, FossConstants.ACTIVE);
		List<PriceTableEntity> priceTableEntity = null;
		if(CollectionUtils.isNotEmpty(regionPriceEntityList)&&CollectionUtils.isNotEmpty(regionEffectiveEntityList)){			
			priceTableEntity = encapulatePriceTable(regionPriceEntityList,regionEffectiveEntityList,currentDateTime);
		}
		//根据价格方案id取得价格分段重量体积范围
		Map<String,String> sectionScopeMap = this.querySectionScopeByPricePlanId(pricePlanEntity.getId());
		Object[] objArray = new Object[2];
		objArray[0] = priceTableEntity;
		objArray[1] = sectionScopeMap;
		return objArray;
	}
	
	/**
	 * <p>[POP]根据价格方案id取得价格分段重量体积范围</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-7 下午3:30:35
	 * @param pricePlanId
	 * @return
	 * @see
	 */
	private Map<String,String> querySectionScopeByPricePlanId(String pricePlanId){
		//根据价格方案id查询出价格分段数最多的分段价格明细记录信息
		List<PopPriceDetailSectionEntity> popPriceDetailSectionEntityList = popPriceDetailSectionDao.querySectionScopeByPricePlanId(pricePlanId);
		Map<String,String> map = null;
		if(CollectionUtils.isNotEmpty(popPriceDetailSectionEntityList)){
			map = new HashMap<String,String>();
			BigDecimal[] heavyCriticalValArray = new BigDecimal[SIX];
			BigDecimal[] lightCriticalValArray = new BigDecimal[SIX];
			heavyCriticalValArray[0] = BigDecimal.ZERO;
			lightCriticalValArray[0] = BigDecimal.ZERO;
			for(PopPriceDetailSectionEntity popPriceDetailSectionEntity:popPriceDetailSectionEntityList){
				//根据分段Id,设置相应段数的轻、重货临界值
				if(String.valueOf(TWO).equals(popPriceDetailSectionEntity.getSectionID())){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(popPriceDetailSectionEntity.getCaculateType())){
						heavyCriticalValArray[1] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(popPriceDetailSectionEntity.getCaculateType())){
						lightCriticalValArray[1] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}          
				}
				if(String.valueOf(THREE).equals(popPriceDetailSectionEntity.getSectionID())){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(popPriceDetailSectionEntity.getCaculateType())){
						heavyCriticalValArray[2] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(popPriceDetailSectionEntity.getCaculateType())){
						lightCriticalValArray[2] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}          
				}
				if(String.valueOf(FOUR).equals(popPriceDetailSectionEntity.getSectionID())){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(popPriceDetailSectionEntity.getCaculateType())){
						heavyCriticalValArray[THREE] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(popPriceDetailSectionEntity.getCaculateType())){
						lightCriticalValArray[THREE] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}          
				}
				if(String.valueOf(FIVE).equals(popPriceDetailSectionEntity.getSectionID())){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(popPriceDetailSectionEntity.getCaculateType())){
						heavyCriticalValArray[FOUR] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(popPriceDetailSectionEntity.getCaculateType())){
						lightCriticalValArray[FOUR] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}          
				}
				if(String.valueOf(SIX).equals(popPriceDetailSectionEntity.getSectionID())){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(popPriceDetailSectionEntity.getCaculateType())){
						heavyCriticalValArray[FIVE] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(popPriceDetailSectionEntity.getCaculateType())){
						lightCriticalValArray[FIVE] = popPriceDetailSectionEntity.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100));
					}          
				}
			}
			int sectionNum = popPriceDetailSectionEntityList.size()/2;
			for(int i=1;i<sectionNum;i++){
				map.put("section"+i,"当前价格对应重量范围："+heavyCriticalValArray[i-1]+"-"+heavyCriticalValArray[i]+"，体积范围："+lightCriticalValArray[i-1]+"-"+lightCriticalValArray[i]);
				if(i == sectionNum-1){
					map.put("section"+(i+1),"当前价格对应重量范围："+heavyCriticalValArray[i]+"以上"+"，体积范围："+lightCriticalValArray[i]+"以上");
				}
			}
		}
		return map;
	}
	
	/**
	 * 封装成价格时效表
	 * @param regionPriceEntityList
	 * @param regionEffectiveEntityList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<PriceTableEntity> encapulatePriceTable(
			List<RegionPriceEntity> regionPriceEntityList,
			List<RegionEffectiveEntity> regionEffectiveEntityList,Date currentDateTime) {
		if(CollectionUtils.isEmpty(regionPriceEntityList)||CollectionUtils.isEmpty(regionEffectiveEntityList)) {
			return null;
		}
		List<PriceTableEntity> priceTableEntityList = new ArrayList<PriceTableEntity>();
		//价格    精准、普通
		//时效    【（卡航、城运）、（长途、短途）】
		//价格匹配时效
		//价格分为区县级和市级，时效区分为、区县级和市级
		List<RegionEffectiveEntity> countyEffectiveList = new ArrayList<RegionEffectiveEntity>();
		List<RegionEffectiveEntity> cityEffectiveList= new ArrayList<RegionEffectiveEntity>();
		List<RegionPriceEntity> countyPriceList= new ArrayList<RegionPriceEntity>();
		List<RegionPriceEntity> cityPriceList= new ArrayList<RegionPriceEntity>();
		
		for( RegionPriceEntity entity:regionPriceEntityList){
			if(StringUtil.isNotEmpty(entity.getCountyCode())){
				countyPriceList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCityCode())){
				cityPriceList.add(entity);
			}
		}
		
		for( RegionEffectiveEntity entity:regionEffectiveEntityList){
			if(StringUtil.isNotEmpty(entity.getCountyCode())){
				entity.setProvinceName(ProvinceUtil.getSimplenameByName(entity.getProvinceName()));//改为简称
				countyEffectiveList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCityCode())){
				entity.setProvinceName(ProvinceUtil.getSimplenameByName(entity.getProvinceName()));//改为简称
				cityEffectiveList.add(entity);
			}
		}

		//前提是（价格：快、慢）；（时效：【（卡航、城运）、（长途、短途）】，前提就是快慢，因此运输性质就用“时效”
		//区县级类型的匹配
		if(CollectionUtils.isNotEmpty(countyEffectiveList)&&CollectionUtils.isNotEmpty(countyPriceList)) {
			for(int i=0;i<countyEffectiveList.size();i++){
				RegionEffectiveEntity effective = countyEffectiveList.get(i);
				out:
				for(int j=0;j<countyPriceList.size();j++){
					RegionPriceEntity price = countyPriceList.get(j);
					if(effective.getCountyCode().equals(price.getCountyCode())){
						PriceTableEntity priceTableEntity = new PriceTableEntity();
						priceTableEntity.setDestinationName(effective.getCountyName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(effective.getProvinceName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						//去除已经用过的价格
						countyPriceList.remove(price);
						countyEffectiveList.remove(effective);
						i--;
						break out;
					}
				}
			}
		}
		//城市及类型匹配
		if(CollectionUtils.isNotEmpty(cityEffectiveList)&&CollectionUtils.isNotEmpty(cityPriceList)) {
			
			List<RegionPriceEntity> tempCityPriceList= (List<RegionPriceEntity>) ((ArrayList) cityPriceList).clone();
			for(int i=0;i<cityEffectiveList.size();i++){
				RegionEffectiveEntity effective = cityEffectiveList.get(i);
				int tempSize = tempCityPriceList.size();
				if(tempSize==0){
					break;
				}
				out:
				for(int j=0;j<tempSize;j++){
					RegionPriceEntity price = tempCityPriceList.get(j);
					if(effective.getCityCode().equals(price.getCityCode())){
						PriceTableEntity priceTableEntity = new PriceTableEntity();
						priceTableEntity.setDestinationName(effective.getCityName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(effective.getProvinceName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						tempCityPriceList.remove(j);
						break out;
					}
				}
			}
		}
		//剩余时效为区县级，与价格为城市级匹配
		if(CollectionUtils.isNotEmpty(countyEffectiveList)&&CollectionUtils.isNotEmpty(cityPriceList)) {
			for( RegionEffectiveEntity effective:countyEffectiveList){
				out:
				for( RegionPriceEntity price:cityPriceList){
					if(effective.getCityCode().equals(price.getCityCode())){
						PriceTableEntity priceTableEntity = new PriceTableEntity();
						priceTableEntity.setDestinationName(effective.getCountyName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(effective.getProvinceName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						break out;
					}
				}
			}
		}
		return priceTableEntityList;
	}
	
	/**
	 * 查询时效
	 * @param deptRegionId
	 * @param productType
	 * @param effectiveDate
	 * @return
	 */
	private List<RegionEffectiveEntity> queryEffctiveList(String deptRegionId,String productType,
			List<String> productList, Date currentDateTime,String active) {
		List<RegionEffectiveDto> list = null;
		List<RegionEffectiveEntity> regionEffectiveEntityList = new ArrayList<RegionEffectiveEntity>();
		
		QueryEffctiveBean queryEffctiveBean = new QueryEffctiveBean();
		queryEffctiveBean.setActive(active);
		queryEffctiveBean.setCurrentDateTime(currentDateTime);
		queryEffctiveBean.setDeptRegionId(deptRegionId);
		queryEffctiveBean.setProductList(productList);
		list = regionEffectiveDao.queryEffctiveByQueryEffctiveBean(queryEffctiveBean);
		//转换成RegionEffectiveEntity
		//regionEffectiveEntityList = boxHeaveAndLight(list);
		if(CollectionUtils.isNotEmpty(list)) {
			int size = list.size();
			for(int i = 0; i < size; i++){
				RegionEffectiveDto dtoi = list.get(i);
				RegionEffectiveEntity effectiveEntity = new RegionEffectiveEntity();
				
				String maxTime = dtoi.getMaxTime()+"";
				String maxTimeUnit = dtoi.getMaxTimeUnit();
				String minTime = dtoi.getMinTime()+"";
				String time = dtoi.getArriveTime();

				//自提时间显示：快车（卡航与城运）显示具体的时间（例：1日14：30）；
				//汽运的如果是跨天的时间段就显示：*日到*日（例：1日到2日），如果是同一天就显示：*日+时间（例：2日14:30）。
				String arriveTime = null;
				if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
					if("DAY".equals(maxTimeUnit)){//天
						arriveTime = minTime+"日"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
					}else if("HOURS".equals(maxTimeUnit)){//
						arriveTime = minTime+"小时"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
					}
					
				}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
					if(dtoi.getMinTime()==dtoi.getMaxTime()){//日期相同
						if("DAY".equals(maxTimeUnit)){//天
							arriveTime = minTime+"日"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
						}else if("HOURS".equals(maxTimeUnit)){//
							arriveTime = minTime+"小时"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
						}
					}else{//日期不同
						if("DAY".equals(maxTimeUnit)){//天
							arriveTime = minTime+"到"+maxTime+"日";		
						}else if("HOURS".equals(maxTimeUnit)){//
							arriveTime = minTime+"到"+maxTime+"小时";		
						}	
					}
				}
				//-----------------------------派送日期没有确认
				effectiveEntity.setArriveTime(arriveTime);
				effectiveEntity.setCityCode(dtoi.getCityCode());
				effectiveEntity.setCityName(dtoi.getCityName());
				effectiveEntity.setCountyCode(dtoi.getCountyCode());
				effectiveEntity.setCountyName(dtoi.getCountyName());
				effectiveEntity.setLongOrShort(dtoi.getLongOrShort());
				effectiveEntity.setProductCode(dtoi.getProductCode());
				effectiveEntity.setProductId(dtoi.getProductId());
				effectiveEntity.setProvinceName(dtoi.getProvinceName());
				
				regionEffectiveEntityList.add(effectiveEntity);
			}
		}
		return regionEffectiveEntityList;
	}
	/**
	 * 查询价格
	 * @param deptRegionId
	 * @param productType
	 * @param effectiveDate
	 * @return
	 */
	private List<RegionPriceEntity> queryPriceList(String planId,
			String productType, Date currentDateTime,String active) {
		List<RegionPriceDto> list = null;
		List<RegionPriceEntity> regionPriceEntityList = null;
		QueryPriceBean queryPriceBean = new QueryPriceBean();
		queryPriceBean.setActive(active);
		queryPriceBean.setCurrentDateTime(currentDateTime);
		queryPriceBean.setPlanId(planId);
		queryPriceBean.setProductType(productType);
		list = regionPriceDao.queryPriceByQueryPriceBean(queryPriceBean);
		//转换成RegionPriceEntity
		regionPriceEntityList = boxHeaveAndLight(list);
		return regionPriceEntityList;
	}
	
	/**
	 * <p>[POP]查询价格</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-7 下午4:09:59
	 * @param planId
	 * @param productType
	 * @param currentDateTime
	 * @param active
	 * @param sectionID
	 * @return
	 * @see
	 */
	private List<RegionPriceEntity> queryPriceList(String planId,
			String productType, Date currentDateTime,String active,int sectionID) {
		List<RegionPriceDto> list = null;
		List<RegionPriceEntity> regionPriceEntityList = null;
		QueryPriceBean queryPriceBean = new QueryPriceBean();
		queryPriceBean.setActive(active);
		queryPriceBean.setCurrentDateTime(currentDateTime);
		queryPriceBean.setPlanId(planId);
		queryPriceBean.setProductType(productType);
		list = regionPriceDao.queryPriceByQueryPriceBean(queryPriceBean);
		if(sectionID != ONE){
			Iterator<RegionPriceDto> iterator = list.iterator();
			RegionPriceDto regionPriceDto = null;
			int count = 0;//用于判断是否查询分段无数据
		    while(iterator.hasNext()) {
		    	regionPriceDto = iterator.next();
		    	/*//查询分段价格明细
				PopPriceDetailSectionEntity popPriceDetailSectionEntity = new PopPriceDetailSectionEntity();
				popPriceDetailSectionEntity.setValuationId(regionPriceDto.getPricingValuationId());
				popPriceDetailSectionEntity.setSectionID(String.valueOf(sectionID));
				popPriceDetailSectionEntity.setCaculateType(regionPriceDto.getCaculateType());
				if("340600".equals(regionPriceDto.getCityCode()) || "341000".equals(regionPriceDto.getCityCode())){
					System.out.println(regionPriceDto.getCityCode()+":"+regionPriceDto.getPricingValuationId());
				}
				List<PopPriceDetailSectionEntity> sectionEntity = popPriceDetailSectionDao.querySectionListByCondition(popPriceDetailSectionEntity);
				//如果存在分段价格则替换
				if(CollectionUtils.isNotEmpty(sectionEntity)){
					regionPriceDto.setFeeRate(sectionEntity.get(0).getFee());
				}else{
					//如果不存在分段价格则移除
					iterator.remove();
				}*/
		    	//查询出计价条目id对应的所有的分段信息
				PopPriceDetailSectionEntity popPriceDetailSectionEntity = new PopPriceDetailSectionEntity();
				popPriceDetailSectionEntity.setValuationId(regionPriceDto.getPricingValuationId());
				popPriceDetailSectionEntity.setCaculateType(regionPriceDto.getCaculateType());
				List<PopPriceDetailSectionEntity> sectionEntityList = popPriceDetailSectionDao.querySectionListByCondition(popPriceDetailSectionEntity);
				//如果存在分段价格则替换
				String sectionIDString = String.valueOf(sectionID);
				if(CollectionUtils.isNotEmpty(sectionEntityList)){
					if(sectionEntityList.size() >= sectionID){
						//结果size大于等于sectionID表示存在sectionID这一段对应价格，保存该段价格
						for(PopPriceDetailSectionEntity sectionEntity :sectionEntityList){
							if(sectionIDString.equals(sectionEntity.getSectionID())){
								regionPriceDto.setFeeRate(sectionEntity.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								break;
							}
						}
					}else{
						//反之，找到该记录中最后一段价格，保存之
						//没有sectionID这一分段信息count++,
						count++;
						String listSize = String.valueOf(sectionEntityList.size());
						for(PopPriceDetailSectionEntity sectionEntity :sectionEntityList){
							if(listSize.equals(sectionEntity.getSectionID())){
								regionPriceDto.setFeeRate(sectionEntity.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								break;
							}
						}
					}
				}else{
					//如果没有分段信息
					count++;
				}
		    }
		    //如果该分段信息全部不存在，查询结果为空
		    if(count==list.size()){
		    	return null;
		    }
		}
		//转换成RegionPriceEntity
		regionPriceEntityList = boxHeaveAndLight(list);
		return regionPriceEntityList;
	}
	/**
	 * 转换成RegionPriceEntity
	 * @param list
	 * @return
	 */
    private List<RegionPriceEntity> boxHeaveAndLight(List<RegionPriceDto> list) {
    	List<RegionPriceEntity> regionPriceEntitys = new ArrayList<RegionPriceEntity>();
    	if(CollectionUtils.isNotEmpty(list)) {
    		for (int i = 0; i < list.size(); i++) {
    			RegionPriceDto dtoi = (RegionPriceDto)list.get(i);
    			RegionPriceEntity entity = null;
    			entity = new RegionPriceEntity();
				entity.setCityCode(dtoi.getCityCode());
				entity.setCountyCode(dtoi.getCountyCode());
				entity.setProductCode(dtoi.getProductCode());
				entity.setProductId(dtoi.getProductId());
				out:
				for (int j = i+1; j < list.size(); j++) {
					RegionPriceDto dtoj = list.get(j);
					if(dtoj.getPricingValuationId().equals(dtoi.getPricingValuationId())
							&&!StringUtil.equals(dtoi.getCaculateType(),dtoj.getCaculateType())) {
						
						if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoi.getCaculateType())
								&&StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoj.getCaculateType())){
								//重量计费和体积计费
								entity.setHeavyFeeRate(dtoi.getFeeRate());
								entity.setLightFeeRate(dtoj.getFeeRate());
								regionPriceEntitys.add(entity);
								list.remove(j);//从后向前移除
								list.remove(i);
								i--;
								break out;
						}
						if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoj.getCaculateType())
								&&StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoi.getCaculateType())){
								//重量计费和体积计费
								entity.setHeavyFeeRate(dtoj.getFeeRate());
								entity.setLightFeeRate(dtoi.getFeeRate());
								regionPriceEntitys.add(entity);
								list.remove(j);
								list.remove(i);
								i--;
								break out;
						}
					}
				}
			}
    	}
    	return regionPriceEntitys;
    }
    
	private static List<PDFPriceTableEntity> convertRow(List<PriceTableEntity> priceTableEntityList) {
		List<PDFPriceTableEntity> list = new ArrayList<PDFPriceTableEntity>();
		//获取行数
		int size = priceTableEntityList.size();
		if(size<=0){
			return null;
		}
		int sum = (size+THREE)/FOUR;//行数
		PDFPriceTableEntity price;
		if(sum>=FOUR){//空值填充到最后一列设置
			for(int i=0;i<sum;i++){
				
				//		list.add(new Price("广东省","惠州市","广东省","惠州市","四川省","阿坝州","四川省","阿坝州",null));
				PriceTableEntity p1 = priceTableEntityList.get(i);
				PriceTableEntity p2 = priceTableEntityList.get(i+sum);
				PriceTableEntity p3 = priceTableEntityList.get(i+2*sum);
				if(size>i+THREE*sum){
					PriceTableEntity p4 = priceTableEntityList.get(i+THREE*sum);
	
					price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
							p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
							p1.getLightPrice(), p2.getProvinceName(),
							p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
							p2.getLightPrice(), p3.getProvinceName(),
							p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
							p3.getLightPrice(), p4.getProvinceName(),
							p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
							p4.getLightPrice()) ; 
					}else{
						price=new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
								p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
								p1.getLightPrice(), p2.getProvinceName(),
								p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
								p2.getLightPrice(), p3.getProvinceName(),
								p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
								p3.getLightPrice(), null,null, null,null,null) ; 			
					}
				list.add(price);
			}
		}else{//空值填充到最后一行设置
			for(int i=0;i<sum-1;i++){//前sum-1行填满
				PriceTableEntity p1 = priceTableEntityList.get(i);
				PriceTableEntity p2 = priceTableEntityList.get(i+sum);
				PriceTableEntity p3 = null;
				PriceTableEntity p4 = null;
				if(size%FOUR==1){//最后一行只有1个
					p3 = priceTableEntityList.get(i+2*sum-1);
					p4 = priceTableEntityList.get(i+THREE*sum-2);
				}else if(size%FOUR==2){//最后一行只有2个
					p3 = priceTableEntityList.get(i+2*sum);
					p4 = priceTableEntityList.get(i+THREE*sum-1);
				}else {//最后一行只有3或4个
					p3 = priceTableEntityList.get(i+2*sum);
					p4 = priceTableEntityList.get(i+THREE*sum);
				}
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(), p4.getProvinceName(),
						p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
						p4.getLightPrice()) ; 
				list.add(price);
			}
			//以下为设置最后一行数据
			//最后一行
			if(size%FOUR==1){//剩1个
				PriceTableEntity p1 = priceTableEntityList.get(sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), null,null, null,null,null,null,null, null,null,null,null,null, null,null,null) ; 
				list.add(price);
			}else if(size%FOUR==2){//剩2个
				PriceTableEntity p1 = priceTableEntityList.get(sum-1);
				PriceTableEntity p2 = priceTableEntityList.get(2*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), null,null, null,null,null,null,null, null,null,null) ; 
				list.add(price);
			}else if(size%FOUR==THREE){//剩3个
				PriceTableEntity p1 = priceTableEntityList.get(sum-1);
				PriceTableEntity p2 = priceTableEntityList.get(2*sum-1);
				PriceTableEntity p3 = priceTableEntityList.get(THREE*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(),null,null, null,null,null) ; 
				list.add(price);
			}else{//剩4个
				PriceTableEntity p1 = priceTableEntityList.get(sum-1);
				PriceTableEntity p2 = priceTableEntityList.get(2*sum-1);
				PriceTableEntity p3 = priceTableEntityList.get(THREE*sum-1);
				PriceTableEntity p4 = priceTableEntityList.get(FOUR*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(), p4.getProvinceName(),
						p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
						p4.getLightPrice()) ; 
				list.add(price);
			}
		}
		return list;
	}
	/**
	 * <p>合伙人导出表内存排序</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 上午10:28:53
	 * @param partnerPriceTableEntityList
	 * @return
	 * @see
	 */
	private static List<PDFPriceTableEntity> convertRow2(List<PartnerPriceTableEntity> partnerPriceTableEntityList) {
		List<PDFPriceTableEntity> list = new ArrayList<PDFPriceTableEntity>();
		//获取行数
		int size = partnerPriceTableEntityList.size();
		if(size<=0){
			return null;
		}
		int sum = (size+THREE)/FOUR;//行数
		PDFPriceTableEntity price;
		if(sum>=FOUR){//空值填充到最后一列设置
			for(int i=0;i<sum;i++){
				
				//		list.add(new Price("广东省","惠州市","广东省","惠州市","四川省","阿坝州","四川省","阿坝州",null));
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(i);
				PartnerPriceTableEntity p2 = partnerPriceTableEntityList.get(i+sum);
				PartnerPriceTableEntity p3 = partnerPriceTableEntityList.get(i+2*sum);
				if(size>i+THREE*sum){
					PartnerPriceTableEntity p4 = partnerPriceTableEntityList.get(i+THREE*sum);
					
					price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
							p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
							p1.getLightPrice(), p2.getProvinceName(),
							p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
							p2.getLightPrice(), p3.getProvinceName(),
							p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
							p3.getLightPrice(), p4.getProvinceName(),
							p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
							p4.getLightPrice()) ; 
				}else{
					price=new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
							p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
							p1.getLightPrice(), p2.getProvinceName(),
							p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
							p2.getLightPrice(), p3.getProvinceName(),
							p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
							p3.getLightPrice(), null,null, null,null,null) ; 			
				}
				list.add(price);
			}
		}else{//空值填充到最后一行设置
			for(int i=0;i<sum-1;i++){//前sum-1行填满
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(i);
				PartnerPriceTableEntity p2 = partnerPriceTableEntityList.get(i+sum);
				PartnerPriceTableEntity p3 = null;
				PartnerPriceTableEntity p4 = null;
				if(size%FOUR==1){//最后一行只有1个
					p3 = partnerPriceTableEntityList.get(i+2*sum-1);
					p4 = partnerPriceTableEntityList.get(i+THREE*sum-2);
				}else if(size%FOUR==2){//最后一行只有2个
					p3 = partnerPriceTableEntityList.get(i+2*sum);
					p4 = partnerPriceTableEntityList.get(i+THREE*sum-1);
				}else {//最后一行只有3或4个
					p3 = partnerPriceTableEntityList.get(i+2*sum);
					p4 = partnerPriceTableEntityList.get(i+THREE*sum);
				}
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(), p4.getProvinceName(),
						p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
						p4.getLightPrice()) ; 
				list.add(price);
			}
			//以下为设置最后一行数据
			//最后一行
			if(size%FOUR==1){//剩1个
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), null,null, null,null,null,null,null, null,null,null,null,null, null,null,null) ; 
				list.add(price);
			}else if(size%FOUR==2){//剩2个
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(sum-1);
				PartnerPriceTableEntity p2 = partnerPriceTableEntityList.get(2*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), null,null, null,null,null,null,null, null,null,null) ; 
				list.add(price);
			}else if(size%FOUR==THREE){//剩3个
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(sum-1);
				PartnerPriceTableEntity p2 = partnerPriceTableEntityList.get(2*sum-1);
				PartnerPriceTableEntity p3 = partnerPriceTableEntityList.get(THREE*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(),null,null, null,null,null) ; 
				list.add(price);
			}else{//剩4个
				PartnerPriceTableEntity p1 = partnerPriceTableEntityList.get(sum-1);
				PartnerPriceTableEntity p2 = partnerPriceTableEntityList.get(2*sum-1);
				PartnerPriceTableEntity p3 = partnerPriceTableEntityList.get(THREE*sum-1);
				PartnerPriceTableEntity p4 = partnerPriceTableEntityList.get(FOUR*sum-1);
				price = new PDFPriceTableEntity(p1.getProductName(), p1.getProvinceName(),
						p1.getDestinationName(), p1.getRunTime(),p1.getHeavyPrice(),
						p1.getLightPrice(), p2.getProvinceName(),
						p2.getDestinationName(), p2.getRunTime(),p2.getHeavyPrice(),
						p2.getLightPrice(), p3.getProvinceName(),
						p3.getDestinationName(), p3.getRunTime(),p3.getHeavyPrice(),
						p3.getLightPrice(), p4.getProvinceName(),
						p4.getDestinationName(), p4.getRunTime(),p4.getHeavyPrice(),
						p4.getLightPrice()) ; 
				list.add(price);
			}
		}
		return list;
	}
    
	//合并单元格
	private  List<PDFPriceTableEntity> mergeCells(List<PDFPriceTableEntity> list) {
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		String firstPrivince1 = list.get(0).getProvinceName1();
		String firstPrivince2 = list.get(0).getProvinceName2();
		String firstPrivince3 = list.get(0).getProvinceName3();
		String firstPrivince4 = list.get(0).getProvinceName4();
		
		List<Integer> cols1=new ArrayList<Integer>();
		List<Integer> cols2=new ArrayList<Integer>();
		List<Integer> cols3=new ArrayList<Integer>();
		List<Integer> cols4=new ArrayList<Integer>();
		//横线分割
		List<Integer> line1=new ArrayList<Integer>();
		List<Integer> line2=new ArrayList<Integer>();
		List<Integer> line3=new ArrayList<Integer>();
		List<Integer> line4=new ArrayList<Integer>();
		//启示行
		int startCols1 = 1;
		int startCols2 = 1;
		int startCols3 = 1;
		int startCols4 = 1;

		//计算哪些行省份名称显示
		//从第二行开始，i等于行号
		for(int i=2;i<=list.size();i++){
			String privince1Now = list.get(i-1).getProvinceName1();
			String privince2Now = list.get(i-1).getProvinceName2();
			String privince3Now = list.get(i-1).getProvinceName3();
			String privince4Now = list.get(i-1).getProvinceName4();

			//与之前相同
			if(null!=firstPrivince1&&firstPrivince1.equals(privince1Now)){
				//如果是最后一行
				if(i==list.size()){
					//1计算中间数
					int countCols1 =  (startCols1+i)/2;
					cols1.add(countCols1);
				}
			}else{//与之前不同
				//1计算中间数
				int countCols1 =  (startCols1+i-1)/2;//之前的中间行
				cols1.add(countCols1);
				if(i==list.size()){//如果是最后一行
					cols1.add(i);
				}
				line1.add(i-1);//横线
				//2记录当前省的起始行数
				startCols1=i;
				firstPrivince1=privince1Now;
			}
			
			//与之前相同
			if(null!=firstPrivince2&&firstPrivince2.equals(privince2Now)){
				//如果是最后一行
				if(i==list.size()){
					//1计算中间数
					int countCols2 =  (startCols2+i)/2;
					cols2.add(countCols2);
				}
			}else{//与之前不同
				//1计算中间数
				int countCols2 =  (startCols2+i-1)/2;
				cols2.add(countCols2);
				if(i==list.size()){//如果是最后一行
					cols2.add(i);
				}
				line2.add(i-1);//横线
				//2记录当前省的起始行数
				startCols2=i;
				firstPrivince2=privince2Now;
			}
			
			//与之前相同
			if(null!=firstPrivince3&&firstPrivince3.equals(privince3Now)){
				//如果是最后一行
				if(i==list.size()){
					//1计算中间数
					int countCols3 =  (startCols3+i)/2;
					cols3.add(countCols3);
				}
			}else{//与之前不同
				//1计算中间数
				int countCols3 =  (startCols3+i-1)/2;
				cols3.add(countCols3);
				if(i==list.size()){//如果是最后一行
					cols3.add(i);
				}
				line3.add(i-1);//横线
				//2记录当前省的起始行数
				startCols3=i;
				firstPrivince3=privince3Now;
			}
			
			//与之前相同
			if(null!=firstPrivince4&&firstPrivince4.equals(privince4Now)){
				//如果是最后一行
				if(i==list.size()){
					//1计算中间数
					int countCols4 =  (startCols4+i)/2;
					cols4.add(countCols4);
				}
			}else{//与之前不同
				//1计算中间数
				int countCols4 =  (startCols4+i-1)/2;
				cols4.add(countCols4);
				if(i==list.size()){//如果是最后一行
					cols4.add(i);
				}
				line4.add(i-1);//横线
				//2记录当前省的起始行数
				startCols4=i;
				firstPrivince4=privince4Now;
			}
		}

		//如果集合中不包含这个数据，设置为空
		String line = "__________";
		for(int i=1;i<=list.size();i++){
			PDFPriceTableEntity p = list.get(i-1);
			if(!cols1.contains(i)){
				if(line1.contains(i)){//横线
					p.setProvinceName1(line);
				}else{
					p.setProvinceName1(null);
				}
			}
			if(!cols2.contains(i)){
				if(line2.contains(i)){//横线
					p.setProvinceName2(line);
				}else{
					p.setProvinceName2(null);
				}
			}
			if(!cols3.contains(i)){
				if(line3.contains(i)){//横线
					p.setProvinceName3(line);
				}else{
					p.setProvinceName3(null);
				}
			}
			if(!cols4.contains(i)){
				if(line4.contains(i)){//横线
					p.setProvinceName4(line);
				}else{
					p.setProvinceName4(null);
				}
			}
		}		
		return list;
	}
	
    /**
     * 优先级省份、重量计费、目的站
     * @author 肖明明
     *
     */
    class PriceTableEntityComparator implements Comparator<PriceTableEntity>{
    	Collator cmp = Collator.getInstance(java.util.Locale.CHINA); 
		@Override
		public int compare(PriceTableEntity o1, PriceTableEntity o2) {
			int province = cmp.compare(o1.getProvinceName(), o2.getProvinceName());
	        if (province==0){  
	        	int value=o1.getHeavyPrice().compareTo(o2.getHeavyPrice());
	        	if(value==0){//重货相等,比较目的站
	        		return cmp.compare(o1.getDestinationName(), o2.getDestinationName());
	        	}else{
	        		return value;
	        	}
	        }
	        return province;
		}
    }
    /**
     * 优先级省份、目的站、重量计费(合伙人汽运价格表查询)
     * @author Foss-352676-YUANHB
     *
     */
    class PartnerPriceTableEntityComparator implements Comparator<PartnerPriceTableEntity>{
    	Collator cmp = Collator.getInstance(java.util.Locale.CHINA); 
		@Override
		public int compare(PartnerPriceTableEntity o1, PartnerPriceTableEntity o2) {
			int province = cmp.compare(o1.getProvinceName(), o2.getProvinceName());
	        if (province==0){
	        	int destinationName=o1.getDestinationName().compareTo(o2.getDestinationName());
	        	if(destinationName==0){//目的地相等，比较重货
	        		return cmp.compare(o1.getHeavyPrice(), o2.getHeavyPrice());
	        	}else{
	        		return destinationName;
	        	}
	        	/*int value=o1.getHeavyPrice().compareTo(o2.getHeavyPrice());
	        	if(value==0){//重货相等,比较目的站
	        		return cmp.compare(o1.getDestinationName(), o2.getDestinationName());
	        	}else{
	        		return value;
	        	}*/
	        }
	        return province;
		}
    }
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public void setPricePlanService(IPricePlanService pricePlanService) {
		this.pricePlanService = pricePlanService;
	}
	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}
	public void setPopPriceDetailSectionDao(IPopPriceDetailSectionDao popPriceDetailSectionDao) {
		this.popPriceDetailSectionDao = popPriceDetailSectionDao;
	}
	public void setRegionPriceDao(IRegionPriceDao regionPriceDao) {
		this.regionPriceDao = regionPriceDao;
	}
	public void setRegionEffectiveDao(IRegionEffectiveDao regionEffectiveDao) {
		this.regionEffectiveDao = regionEffectiveDao;
	}
	/**
	 * <p>(合伙人汽运价格表数据查询)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-22 下午4:40:12
	 * @param startDeptCode
	 * @param productType
	 * @param effectiveDate
	 * @param sectionID
	 * @param start
	 * @param limit
	 * @return 
	 */
	@Override
	public Object[] queryPartnerPriceTableListInfo(String startDeptCode,
			String productType, Date currentDateTime, int sectionID, int start,
			int limit) {

    	if(StringUtil.isEmpty(startDeptCode) || (sectionID < 1 && sectionID > SIX)){
			return null;
    	}
    	if(null==currentDateTime){
			currentDateTime= new Date();
    	}
    	/**
    	 * 以下为价格区域
    	 */
    	//为当前出发部门定位价格区域ID
    	String priceRegionId=regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
    	if(StringUtil.isEmpty(priceRegionId)){
    		return null;
    	}
    	//获取当前出发部门的价格方案主信息ID
    	//生产上在业务上控制当前生效的价格主方案只有一条
    	PricePlanEntity pricePlanEntity = pricePlanService.findPricePlanByRegionId(currentDateTime,priceRegionId, FossConstants.ACTIVE);
    	if(null==pricePlanEntity){
    		return null;
    	}
    	//获取当前出发部门的所有价格区域价格列表
    	List<RegionPartnerPriceDto> regionPriceEntityList=queryPartnerPriceList(pricePlanEntity.getId(),productType,currentDateTime,sectionID);
    	/**
    	 * 以下为时效区域
    	 */
    	//为当前出发部门定位时效区域ID
    	String effectiveRegionId=regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRESCRIPTION_REGION);
    	if(StringUtil.isEmpty(effectiveRegionId)){
    		return null;
    	}
    	//获取当前出发部门的所有时间区域价格列表
    	List<RegionPartnerEffectiveDto> effectivePriceEntityList=queryPartnerEffctiveList(effectiveRegionId,currentDateTime,productType);
    	
    	//生成合伙人汽运价格方案List
    	List<PartnerPriceTableEntity> partnerPriceTableEntities =encapulatePartnerPriceTable(regionPriceEntityList,effectivePriceEntityList,currentDateTime);
  
    	//根据价格方案id取得价格分段重量体积范围
		Map<String,String> sectionScopeMap = this.querySectionScopeByPricePlanId(pricePlanEntity.getId());
		
		//根据价格方案id取得价格分段重量体积范围
		Object[] objArray = new Object[2];
		objArray[0] = partnerPriceTableEntities;
		objArray[1] = sectionScopeMap;
       	
		//按照价格表排序
		return sortByInitial(objArray, start,
				limit);
	}
	// ================优化内容:获取查询数据---提供给PDF打印功能/时间:2016年9月29日下午12:26:41/LianHe/start================
	/**
	 * <p>获取查询数据---提供给PDF打印功能</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 下午12:26:49
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService#queryPartnerPriceTableListInfo4PDF(java.lang.String, java.lang.String, java.util.Date, int, int, int)
	 */
	@Override
	public Object[] queryPartnerPriceTableListInfo4PDF(String startDeptCode,
			String productType, Date currentDateTime, int sectionID) {
		if(StringUtil.isEmpty(startDeptCode) || (sectionID < 1 && sectionID > SIX)){
			return null;
		}
		if(null==currentDateTime){
			currentDateTime= new Date();
		}
		/**
		 * 以下为价格区域
		 */
		//为当前出发部门定位价格区域ID
		String priceRegionId=regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRICING_REGION);
		//获取当前出发部门的价格方案主信息ID
		PricePlanEntity pricePlanEntity = pricePlanService.findPricePlanByRegionId(currentDateTime,priceRegionId, FossConstants.ACTIVE);
		//获取当前出发部门的所有价格区域价格列表
		List<RegionPartnerPriceDto> regionPriceEntityList=queryPartnerPriceList(pricePlanEntity.getId(),productType,currentDateTime,sectionID);
		/**
		 * 以下为时效区域
		 */
		//为当前出发部门定位时效区域ID
		String effectiveRegionId=regionService.findRegionOrgByDeptNo(startDeptCode, currentDateTime, null,PricingConstants.PRESCRIPTION_REGION);
		//获取当前出发部门的所有时间区域价格列表
		List<RegionPartnerEffectiveDto> effectivePriceEntityList=queryPartnerEffctiveList(effectiveRegionId,currentDateTime,productType);
		
		//生成合伙人汽运价格方案List
		List<PartnerPriceTableEntity> partnerPriceTableEntities =encapulatePartnerPriceTable(regionPriceEntityList,effectivePriceEntityList,currentDateTime);
		
		//根据价格方案id取得价格分段重量体积范围
		Map<String,String> sectionScopeMap = this.querySectionScopeByPricePlanId(pricePlanEntity.getId());
		//结果排序
		Collections.sort(partnerPriceTableEntities, new PartnerPriceTableEntityComparator());  
		//根据价格方案id取得价格分段重量体积范围
		Object[] objArray = new Object[2];
		objArray[0] = partnerPriceTableEntities;
		objArray[1] = sectionScopeMap;
		//按照价格表排序
		return objArray;
	}
	// ================优化内容:获取查询数据---提供给PDF打印功能/时间:2016年9月29日下午12:27:57/LianHe/end================
	public Object[] sortByInitial(Object[] objArray, int start,
			int limit){
		@SuppressWarnings("unchecked")
		List<PartnerPriceTableEntity> list = (List<PartnerPriceTableEntity>) objArray[0];
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		//排序  省份按照省份首字母进行排序，同一省份按照目的地首字母进行排序
		try {
			Collections.sort(list, new PartnerPriceTableEntityComparator());  
		} catch (Exception e) {
			LOGGER.info(e.toString());
		}
		
		//分页
		int size = list.size();
		List<PartnerPriceTableEntity> entitys = new ArrayList<PartnerPriceTableEntity>();
		if(size>start){//存在数据
			for(int i=start;i<start+limit&&i<size;i++){
				entitys.add(list.get(i));
			}
		}
		PartnerPriceTables priceTables = new PartnerPriceTables();
		priceTables.setPartnerPriceTableEntitys(entitys);
		priceTables.setTotal(size);
		objArray[0] = priceTables;
		return objArray;
	}

	/**
	 * 
	 * <p>(生成合伙人汽运价格方案List)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-27 下午2:29:08
	 * @param regionPriceEntityList
	 * @param effectivePriceEntityList
	 * @param currentDateTime
	 * @return
	 * @see
	 */
	private List<PartnerPriceTableEntity> encapulatePartnerPriceTable(
			List<RegionPartnerPriceDto> regionPriceEntityList,
			List<RegionPartnerEffectiveDto> effectivePriceEntityList,
			Date currentDateTime) {
		if(CollectionUtils.isEmpty(regionPriceEntityList)||CollectionUtils.isEmpty(effectivePriceEntityList)) {
			return null;
		}
		//价格    精准、普通
		//时效    【（卡航、城运）、（长途、短途）】
		//价格匹配时效
		//价格分为区县级和市级，时效区分为、区县级和市级
		List<RegionPartnerPriceDto> networkPriceList= new ArrayList<RegionPartnerPriceDto>();
		List<RegionPartnerPriceDto> countyPriceList = new ArrayList<RegionPartnerPriceDto>();
		List<RegionPartnerPriceDto> cityPriceList= new ArrayList<RegionPartnerPriceDto>();
		
		List<RegionPartnerEffectiveDto> networkEffectiveList = new ArrayList<RegionPartnerEffectiveDto>();
		List<RegionPartnerEffectiveDto> countyEffectiveList = new ArrayList<RegionPartnerEffectiveDto>();
		List<RegionPartnerEffectiveDto> cityEffectiveList = new ArrayList<RegionPartnerEffectiveDto>();
		
		for( RegionPartnerEffectiveDto entity:effectivePriceEntityList){
			if(StringUtil.isNotEmpty(entity.getOrgCode())){
				networkEffectiveList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCountyCode())){
				countyEffectiveList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCityCode())){
				cityEffectiveList.add(entity);
			}
		}
		
		for( RegionPartnerPriceDto entity:regionPriceEntityList){
			if(StringUtil.isNotEmpty(entity.getOrgCode())){
				entity.setProvName(ProvinceUtil.getSimplenameByName(entity.getProvName()));//改为简称
				networkPriceList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCountyCode())){
				entity.setProvName(ProvinceUtil.getSimplenameByName(entity.getProvName()));//改为简称
				countyPriceList.add(entity);
			}else if(StringUtil.isNotEmpty(entity.getCityCode())){
				entity.setProvName(ProvinceUtil.getSimplenameByName(entity.getProvName()));//改为简称
				cityPriceList.add(entity);
			}
		}
		
		List<PartnerPriceTableEntity> priceTableEntityList = new ArrayList<PartnerPriceTableEntity>();
		//网点匹配
		if(CollectionUtils.isNotEmpty(networkEffectiveList)&&CollectionUtils.isNotEmpty(networkPriceList)) {
			for(int i=0;i<networkPriceList.size();i++){
				RegionPartnerPriceDto price=networkPriceList.get(i);
				for(int j=0;j<networkEffectiveList.size();j++){
					RegionPartnerEffectiveDto effective = networkEffectiveList.get(j);
					if (StringUtil.equals(effective.getOrgCode(),price.getOrgCode())
							&& StringUtil.equals(effective.getCountyCode(),price.getCountyCode())
							&& StringUtil.equals(effective.getCityCode(),price.getCityCode())) {
						//如果有匹配的数据，则封装PartnerPriceTableEntity中
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
 						priceTableEntity.setDestinationName(price.getOrgName());//有网点的目的站匹配网点
//						priceTableEntity.setDestinationName(effective.getCityName());
//						priceTableEntity.setOrgName(price.getOrgName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						networkPriceList.remove(i);
						networkEffectiveList.remove(j);  //网点时效在下面不会再进行比对所以这边可以REMOVE
						
						i--;
						break;
				}
	   		  }
			}
		}
		
		//没有匹配到价格区域的网点，匹配时效区域的区域
		if(CollectionUtils.isNotEmpty(networkPriceList)&&CollectionUtils.isNotEmpty(countyEffectiveList)) {
			for(int i=0;i<networkPriceList.size();i++){
				RegionPartnerPriceDto price=networkPriceList.get(i);
				for(int j=0;j<countyEffectiveList.size();j++){
					RegionPartnerEffectiveDto effective = countyEffectiveList.get(j);
					if ( StringUtil.equals(effective.getCountyCode(),price.getCountyCode())
							&& StringUtil.equals(effective.getCityCode(),price.getCityCode())) {
						//如果有匹配的数据，则封装PartnerPriceTableEntity中
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
						priceTableEntity.setDestinationName(price.getOrgName());//网点匹配区域，显示网点
//						priceTableEntity.setDestinationName(effective.getCityName());
//						priceTableEntity.setOrgName(price.getOrgName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						networkPriceList.remove(i);
						i--;
						break;
				}
	   		  }
			}
		}
		
		//没有匹配到价格区域的网点，也没有匹配时效区域的区域，匹配时效区域的城市
		if(CollectionUtils.isNotEmpty(networkPriceList)&&CollectionUtils.isNotEmpty(cityEffectiveList)) {
			for(int i=0;i<networkPriceList.size();i++){
				RegionPartnerPriceDto price=networkPriceList.get(i);
				for(int j=0;j<cityEffectiveList.size();j++){
					RegionPartnerEffectiveDto effective = cityEffectiveList.get(j);
					if ( StringUtil.equals(effective.getCityCode(),price.getCityCode())) {
						//如果有匹配的数据，则封装PartnerPriceTableEntity中
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
						//priceTableEntity.setDestinationName(effective.getCountyName());
						priceTableEntity.setDestinationName(price.getOrgName());//网点匹配城市，显示网点
//						priceTableEntity.setOrgName(price.getOrgName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						networkPriceList.remove(i);
						i--;
						break;
				}
	   		  }
			}
		}
		
		//区域匹配
		if(CollectionUtils.isNotEmpty(countyPriceList)&&CollectionUtils.isNotEmpty(countyEffectiveList)) {
			for(int i=0;i<countyPriceList.size();i++){
				RegionPartnerPriceDto price=countyPriceList.get(i);
				for(int j=0;j<countyEffectiveList.size();j++){
					RegionPartnerEffectiveDto effective = countyEffectiveList.get(j);
					if ( StringUtil.equals(effective.getCountyCode(),price.getCountyCode())
							&& StringUtil.equals(effective.getCityCode(),price.getCityCode())) {
						//如果有匹配的数据，则封装PartnerPriceTableEntity中
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
						priceTableEntity.setDestinationName(price.getCityName()+price.getCountyName());//匹配到区域，显示城市和区域
//						priceTableEntity.setDestinationName(effective.getCityName());
//						priceTableEntity.setDestinationName(price.getOrgName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						countyPriceList.remove(i);
						countyEffectiveList.remove(j); //到这里，区域时效下面不再使用可以 remove
						i--;
						break;
				}
	   		  }
			}
		}
		//城市匹配
		if(CollectionUtils.isNotEmpty(cityPriceList)&&CollectionUtils.isNotEmpty(cityEffectiveList)) {
			for(int i=0;i<cityPriceList.size();i++){
				RegionPartnerPriceDto price=cityPriceList.get(i);
				for(int j=0;j<cityEffectiveList.size();j++){
					RegionPartnerEffectiveDto effective = cityEffectiveList.get(j);
					if ( StringUtil.equals(effective.getCityCode(),price.getCityCode())) {
						//如果有匹配的数据，则封装PartnerPriceTableEntity中
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
						//priceTableEntity.setDestinationName(effective.getCountyName());
						priceTableEntity.setDestinationName(price.getCityName());
//						priceTableEntity.setDestinationName(price.getOrgName());
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						cityPriceList.remove(i);
						i--;
						break;
				}
	   		  }
			}
		}
		
		//剩余价格为区县级，与时效为城市级匹配
		if(CollectionUtils.isNotEmpty(countyPriceList)&&CollectionUtils.isNotEmpty(cityEffectiveList)) {
			for( RegionPartnerPriceDto price:countyPriceList){
				for( RegionPartnerEffectiveDto effective:cityEffectiveList){
					if(effective.getCityCode().equals(price.getCityCode())){
						PartnerPriceTableEntity priceTableEntity = new PartnerPriceTableEntity();
						priceTableEntity.setDestinationName(price.getCityName()+price.getCountyName());//区域显示城市和区域
						priceTableEntity.setHeavyPrice(price.getHeavyFeeRate());
						priceTableEntity.setLightPrice(price.getLightFeeRate());
						priceTableEntity.setProvinceName(price.getProvName());
//						priceTableEntity.setDestinationName(effective.getCityName());
						priceTableEntity.setRunTime(effective.getArriveTime());
						priceTableEntity.setProductCode(effective.getProductCode());
						priceTableEntity.setProductID(effective.getProductId());
						ProductEntity productEntiy = productService.getProductByCache(effective.getProductCode(), currentDateTime);
						priceTableEntity.setProductName(productEntiy.getName());
						
						priceTableEntityList.add(priceTableEntity);
						break;
					} 
				}
			}
		}
		
		return priceTableEntityList;
	}
	/**
	 * 
	 * <p>(获取当前出发部门的所有时间区域价格列表)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-26 上午9:47:28
	 * @param effectiveRegionId
	 * @param currentDateTime
	 * @return
	 * @see
	 */
	private List<RegionPartnerEffectiveDto> queryPartnerEffctiveList(
			String effectiveRegionId, Date currentDateTime,String productType) {
		
		List<String> productList = new ArrayList<String>();
		if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
			//精准卡航
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
			//精准城运
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
		}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
			//精准汽运(长途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
			//精准汽运(短途)
			productList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
		}
		if(productList.isEmpty()){
			return null;
		}
		
		QueryEffctiveBean queryEffctiveBean = new QueryEffctiveBean();
		queryEffctiveBean.setActive(FossConstants.ACTIVE);
		queryEffctiveBean.setCurrentDateTime(currentDateTime);
		queryEffctiveBean.setDeptRegionId(effectiveRegionId);
		queryEffctiveBean.setProductList(productList);
		
		List<RegionPartnerEffectiveDto> regionPartnerEffectiveDtos=regionEffectiveDao.queryEffectivePriceList(queryEffctiveBean);
		//处理时效区域list的自提时间
		List<RegionPartnerEffectiveDto> regionPartnerEffectiveDtoList=new ArrayList<RegionPartnerEffectiveDto>();
		if(CollectionUtils.isNotEmpty(regionPartnerEffectiveDtos)) {
			int size = regionPartnerEffectiveDtos.size();
			for(int i = 0; i < size; i++){
				RegionPartnerEffectiveDto dtoi = regionPartnerEffectiveDtos.get(i);
				RegionPartnerEffectiveDto effectiveEntity = new RegionPartnerEffectiveDto();
				
				String maxTime = dtoi.getMaxTime()+"";
				String maxTimeUnit = dtoi.getMaxTimeUnit();
				String minTime = dtoi.getMinTime()+"";
				String time = dtoi.getArriveTime();

				//自提时间显示：快车（卡航与城运）显示具体的时间（例：1日14：30）；
				//汽运的如果是跨天的时间段就显示：*日到*日（例：1日到2日），如果是同一天就显示：*日+时间（例：2日14:30）。
				String arriveTime = null;
				
				if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001,productType)){//精准
					if("DAY".equals(maxTimeUnit)){//天
						arriveTime = minTime+"日"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
					}else if("HOURS".equals(maxTimeUnit)){//
						arriveTime = minTime+"小时"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
					}
					
				}else if(StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002,productType)){//普货
					if(dtoi.getMinTime()==dtoi.getMaxTime()){//日期相同
						if("DAY".equals(maxTimeUnit)){//天
							arriveTime = minTime+"日"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
						}else if("HOURS".equals(maxTimeUnit)){//
							arriveTime = minTime+"小时"+time.substring(0, 2)+":"+time.substring(2, FOUR);		
						}
					}else{//日期不同
						if("DAY".equals(maxTimeUnit)){//天
							arriveTime = minTime+"到"+maxTime+"日";		
						}else if("HOURS".equals(maxTimeUnit)){//
							arriveTime = minTime+"到"+maxTime+"小时";		
						}	
					}
				}
				//-----------------------------派送日期没有确认
				effectiveEntity.setArriveTime(arriveTime);
				effectiveEntity.setCityCode(dtoi.getCityCode());
				effectiveEntity.setCityName(dtoi.getCityName());
				effectiveEntity.setCountyCode(dtoi.getCountyCode());
				effectiveEntity.setCountyName(dtoi.getCountyName());
				effectiveEntity.setLongOrShort(dtoi.getLongOrShort());
				effectiveEntity.setProductCode(dtoi.getProductCode());
				effectiveEntity.setProductId(dtoi.getProductId());
				effectiveEntity.setProvinceName(dtoi.getProvinceName());
				
				regionPartnerEffectiveDtoList.add(effectiveEntity);
			}
		}
		return regionPartnerEffectiveDtoList;
	}
	/**
	 * <p>(获取当前出发部门的所有价格区域价格列表)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-23 下午6:28:31
	 * @param id
	 * @param currentDateTime
	 * @return
	 * @see
	 */
	private List<RegionPartnerPriceDto> queryPartnerPriceList(String id,String productType,
			Date currentDateTime,int sectionID) {
	  	
			QueryPriceBean queryPriceBean = new QueryPriceBean();
			queryPriceBean.setActive(FossConstants.ACTIVE);
			queryPriceBean.setCurrentDateTime(currentDateTime);
			queryPriceBean.setPlanId(id);
			queryPriceBean.setProductType(productType);
			
			
		List<RegionPartnerPriceDto> regionPriceEntities=regionService.queryRegionPriceList(queryPriceBean);
		if(sectionID != ONE){
			Iterator<RegionPartnerPriceDto> iterator = regionPriceEntities.iterator();
			RegionPartnerPriceDto regionPriceDto = null;
			int count = 0;//用于判断是否查询分段无数据
		    while(iterator.hasNext()) {
		    	regionPriceDto = iterator.next();
		    	
		    	//查询出计价条目id对应的所有的分段信息
				PopPriceDetailSectionEntity popPriceDetailSectionEntity = new PopPriceDetailSectionEntity();
				popPriceDetailSectionEntity.setValuationId(regionPriceDto.getPricingValuationId());
				popPriceDetailSectionEntity.setCaculateType(regionPriceDto.getCaculateType());
				List<PopPriceDetailSectionEntity> sectionEntityList = popPriceDetailSectionDao.querySectionListByCondition(popPriceDetailSectionEntity);
				//如果存在分段价格则替换
				String sectionIDString = String.valueOf(sectionID);
				if(CollectionUtils.isNotEmpty(sectionEntityList)){
					if(sectionEntityList.size() >= sectionID){
						//结果size大于等于sectionID表示存在sectionID这一段对应价格，保存该段价格
						for(PopPriceDetailSectionEntity sectionEntity :sectionEntityList){
							if(sectionIDString.equals(sectionEntity.getSectionID())){
								regionPriceDto.setFeeRate(sectionEntity.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								break;
							}
						}
					}else{
						//反之，找到该记录中最后一段价格，保存之
						//没有sectionID这一分段信息count++,
						count++;
						String listSize = String.valueOf(sectionEntityList.size());
						for(PopPriceDetailSectionEntity sectionEntity :sectionEntityList){
							if(listSize.equals(sectionEntity.getSectionID())){
								regionPriceDto.setFeeRate(sectionEntity.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								break;
							}
						}
					}
				}else{
					//如果没有分段信息
					count++;
				}
		    }
		    //如果该分段信息全部不存在，查询结果为空
		    if(count==regionPriceEntities.size()){
		    	return null;
		    }
		}
		
		List<RegionPartnerPriceDto> regionPartnerPriceEntities = partnerBoxHeaveAndLight(regionPriceEntities);
		return regionPartnerPriceEntities;
	}

	/**
	 * <p>(处理价格区域轻重货费率)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-27 下午2:29:31
	 * @param regionPriceEntities
	 * @return
	 * @see
	 */
	private List<RegionPartnerPriceDto> partnerBoxHeaveAndLight(
			List<RegionPartnerPriceDto> regionPriceEntities) {
    	List<RegionPartnerPriceDto> regionPriceEntitys = new ArrayList<RegionPartnerPriceDto>();
    	if(CollectionUtils.isNotEmpty(regionPriceEntities)) {
    		for (int i = 0; i < regionPriceEntities.size(); i++) {
    			
    			RegionPartnerPriceDto dtoi = regionPriceEntities.get(i);
    			RegionPartnerPriceDto entity = null;
    			entity = new RegionPartnerPriceDto();
				entity.setCityCode(dtoi.getCityCode());
				entity.setCountyCode(dtoi.getCountyCode());
				entity.setProductCode(dtoi.getProductCode());
				entity.setProductId(dtoi.getProductId());
				entity.setOrgCode(dtoi.getOrgCode());
				entity.setOrgName(dtoi.getOrgName());
				entity.setProvName(dtoi.getProvName());
				entity.setCityName(dtoi.getCityName());
				entity.setCountyName(dtoi.getCountyName());
				
				out:
				for (int j = i+1; j < regionPriceEntities.size(); j++) {
					RegionPartnerPriceDto dtoj = regionPriceEntities.get(j);
					if(dtoj.getPricingValuationId().equals(dtoi.getPricingValuationId())
							&&!StringUtil.equals(dtoi.getCaculateType(),dtoj.getCaculateType())) {
						
						if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoi.getCaculateType())
								&&StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoj.getCaculateType())){
								//重量计费和体积计费
								entity.setHeavyFeeRate(dtoi.getFeeRate());
								entity.setLightFeeRate(dtoj.getFeeRate());
								regionPriceEntitys.add(entity);
								regionPriceEntities.remove(j);//从后向前移除
								regionPriceEntities.remove(i);
								i--;
								break out;
						}
						if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoj.getCaculateType())
								&&StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoi.getCaculateType())){
								//重量计费和体积计费
								entity.setHeavyFeeRate(dtoj.getFeeRate());
								entity.setLightFeeRate(dtoi.getFeeRate());
								regionPriceEntitys.add(entity);
								regionPriceEntities.remove(j);
								regionPriceEntities.remove(i);
								i--;
								break out;
						}
					}
				}
			}
    	}
    	return regionPriceEntitys;
    
	}
	
}