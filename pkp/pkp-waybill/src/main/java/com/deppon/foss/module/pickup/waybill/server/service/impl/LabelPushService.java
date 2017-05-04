package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * DOP、OWS标签信息推送查询接口
 * @author foss-zhangfan 329834
 * @date 20160526
 * @since
 * @version
 */
public class LabelPushService implements ILabelPushService{

    private static final Log log = LogFactory.getLog(LabelPushService.class);
    
    private static final int TWO = 2;
    private static final int FOUR =4;
    
    private IWaybillPendingService waybillPendingService;
    private IProductService productService;
    private IVehicleAgencyDeptService vehicleAgencyDeptService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    private IOmsOrderService omsOrderService;
    private IWaybillManagerService waybillManagerService;
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    private IGoodsAreaService goodsAreaService;
    private IFreightRouteService freightRouteService;
    private IOutfieldService outfieldService;
    private ISaleDepartmentService saleDepartmentService;
    private IAdministrativeRegionsService administrativeRegionsService;
    
    /**
     * 获取标签信息
     * @param waybillNo 运单号
     * @param originOrgCode 始发部门CODE
     * @return 标签信息（包含订单信息）
     */
    @Override
    public LabelInfoDto queryLabelInfos(WaybillEntity waybillEntity) {
        Assert.notNull(waybillEntity, "运单实体不可为空！");
        // 获得运单基本信息
        LabelInfoDto printLabelBean = new LabelInfoDto();
        getWaybillInfo(waybillEntity, printLabelBean);
    	// 获取综合走货路径
        getFreightRoute(printLabelBean, waybillEntity);
        fillOrderInfo(printLabelBean);
        if(log.isInfoEnabled()){
        	log.info(waybillEntity.getWaybillNo() + "， 始发外场简称 :"+printLabelBean.getOriginateOutFieldSimpleName());
        }
        return printLabelBean;
    }
    
    /**
     * 根据运单号获得运单基本信息
     * 
     * @param waybillEntity 运单实体
     * @author foss-zhangfan
     * @date 20160601
     */
    private LabelInfoDto getWaybillInfo(WaybillEntity waybillEntity, LabelInfoDto printLabelBean) {
        
    	Assert.notNull(waybillEntity, "参数waybillEntity不可为空！");
    	Assert.notNull(printLabelBean, "参数printLabelBean不可为空！");
    	
        //运单号
        printLabelBean.setWaybillNo(waybillEntity.getWaybillNo());
        //订单号
        printLabelBean.setOrderNo(waybillEntity.getOrderNo());
        //运单创建时间(运单表的billTime和createTime是相同的)
        printLabelBean.setBillTime(waybillEntity.getCreateTime());
        //提货方式
        printLabelBean.setReceiveMethod(waybillEntity.getReceiveMethod());
        // 总件数
        printLabelBean.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal());
        //包装
        printLabelBean.setGoodsPackage(waybillEntity.getGoodsPackage());
        
        // 如果产品 code 为空运、偏线 查询目的站编码
        if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode()) 
                || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())) {
            // 偏线 查询目的站编码
            OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(waybillEntity.getCustomerPickupOrgCode(), null);
            if (outerBranchEntity != null) {
                // 偏线设置目的站编码
                printLabelBean.setStationNo(outerBranchEntity.getStationNumber()); // 目的站打印标签编码
                // 偏线设置目的站简称
                printLabelBean.setArrivedOrgSimpleName(outerBranchEntity.getSimplename());
            }
        }else {
            // 专线目的站编码
            SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
            if (saleDepartmentEntity != null) {
                printLabelBean.setStationNo(saleDepartmentEntity.getStationNumber()); // 目的站打印标签编码
            }
            // 根据提货网点OrgCode 获取部门信息
            OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getBillTime());
            if (orgAdministrativeInfoEntity == null) {
            	//因为组织信息在不停的变化，可能会出现运单开单时间跟组织信息时间的查询不匹配而查询不到数据，所以再查一次最新组织部门信息
                orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getCustomerPickupOrgCode());
                if(orgAdministrativeInfoEntity != null){
                    // 设置目的站为提货网点部门简称
                    printLabelBean.setArrivedOrgSimpleName(orgAdministrativeInfoEntity.getOrgSimpleName());
                }
            }else{
            	 // 设置目的站为提货网点部门简称
                printLabelBean.setArrivedOrgSimpleName(orgAdministrativeInfoEntity.getOrgSimpleName());
            }
        }
        if(log.isInfoEnabled()){
        	log.info("======目的站简称=======" + printLabelBean.getArrivedOrgSimpleName());
        }
        
        // 根据产品CODE 与开单时间 获得产品信息
        ProductEntity productEntity = productService.getProductByCache(waybillEntity.getProductCode(), waybillEntity.getBillTime());

        if (productEntity != null && productEntity.getName() != null) {
            // 设置运输类型
            printLabelBean.setProductCode(productEntity.getCode());
            printLabelBean.setProductName(productEntity.getName());
        }
        
        //第一外场城市名称
        OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getLoadOrgCode(), waybillEntity.getBillTime());
        if (org != null) {
             printLabelBean.setOriginateOutFieldCityName(org.getCityName());
        }
        
        //设置优惠券编码
        printLabelBean.setCouponNo(waybillEntity.getPromotionsCode());
        
        return printLabelBean;
    }
    
    /**
     * 获得走货路径信息 8+3 个 其中3个是最终外场信息
     * 
     * @author foss-zhangfan
     * @param printLabelBean  标签信息实体
     * @param waybillEntity 运单实体
     * @date 20160601
     */
    private void getFreightRoute(LabelInfoDto printLabelBean, WaybillEntity waybillEntity) {
        // 到达部门 ==到达营业部
        String lastLoadOrgCode = waybillEntity.getCustomerPickupOrgCode();
        // 收货部门 ==出发营业部
        String startOrg = waybillEntity.getCreateOrgCode();
        
        /**
         * 判断是否是集中开单
         */
        /*
         * 
         * 目前零担电子运单不走这块逻辑，先注掉
         * 
         * if (FossConstants.YES.equals(waybillEntity.getPickupCentralized())) {
            //查询组织信息
            OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode(),waybillEntity.getBillTime());
            //这里相当于集中开单组最多走三次查询，先查历史记录，如果没有，在找实时的数据，实在找不到，再走中转的走货路径
            if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getBillingGroup())){
                //判断是否是 异地开单
                String waybillNo =  waybillEntity.getWaybillNo();
                String  createOrgCode= waybillEntity.getCreateOrgCode();
                if(StringUtil.isNotEmpty(waybillNo)){
                    WaybillPictureEntity entity = new WaybillPictureEntity();
                    entity.setActive(FossConstants.YES);
                    entity.setWaybillNo(waybillNo);
                    entity = waybillPendingService.queryWaybillPictureByEntity(entity);
                    if(entity!=null && FossConstants.NO.equals(entity.getLocal())){
                        //根据本属开单组去查询
                        createOrgCode = entity.getLocalBillGroupCode();
                    }
                }
                //历史查找
                SaleDepartmentEntity saleDepartment = waybillManagerService.queryPickupCentralizedDeptCodeAndBillTime(createOrgCode,waybillEntity.getBillTime());
                if (saleDepartment != null) {
                    startOrg = saleDepartment.getCode();
                }else{
                    //再实时的查找当前数据
                    String transCenterCode = waybillManagerService.queryTransCenterByBillingGroupCode(createOrgCode);
                    SaleDepartmentEntity deliverDepartment = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(transCenterCode);
                    if(deliverDepartment == null){
                        //如果再找不到，则抛出异常，直接调用中转的走货路径：GUI和Web都有相应的扑捉Exception 方法
                        throw new WaybillLabelPrintException("根据集中开单组"+orgAdministrativeInfoEntity.getName()+"("+orgAdministrativeInfoEntity.getCode()+")没有找到对应的外场出发驻地部门!");
                    }
                    startOrg = deliverDepartment.getCode();
                }
            }
        }*/
        
        // 根据出发到达 营业部得到 走货路径 List A-B B-C C-D 包括始发营业部 和到达营业部门
        List<FreightRouteLineDto> freightRouteLineList = freightRouteService.queryFreightRouteBySourceTarget(startOrg, lastLoadOrgCode, waybillEntity.getProductCode(), waybillEntity.getBillTime());

        // 得到走货路径去掉始发营业部 A-B-C-D-d
        List<String> addressInfoList = new ArrayList<String>();
        // 走货路径 对应 货区号
        List<String> goodsAreaCodeList = new ArrayList<String>();
        // 最终外场org编码
        String lastChangeCenterOrgCode = null;
        //始发外场org编码
        String firstChangeCenterOrgCode = null;
        if (CollectionUtils.isNotEmpty(freightRouteLineList)) {
            lastChangeCenterOrgCode = freightRouteLineList.get(freightRouteLineList.size() - 1).getSourceCode();
            firstChangeCenterOrgCode = freightRouteLineList.get(1).getSourceCode();
            // 最终外场编码 == 最终外场编码等于 走货路径的最后一条的 出发部门
            // lastChangeCenterOrgCode =
            // lastCenterOrgCode.substring(0,lastCenterOrgCode.indexOf(SLASH));
            addressInfoList.add(freightRouteLineList.get(0).getTargetCode());
            // 偏线查货区
            if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())) {
                for (int i = 1; i < freightRouteLineList.size(); i++) {
                    addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
                    if(log.isInfoEnabled()){
                    	log.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
                    }
                    String goodsAreaCode = null;
                    // 根据走货路径获得货区号
                    if (i == freightRouteLineList.size() - 1) {
                        List<GoodsAreaEntity> goodsAreaEntities = new ArrayList<GoodsAreaEntity>();
                        // 偏线货区
                        goodsAreaEntities = goodsAreaService.queryGoodsAreaListByType(freightRouteLineList.get(i).getSourceCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
                        if (goodsAreaEntities.size() > 0) {
                            goodsAreaCode = goodsAreaEntities.get(0).getGoodsAreaCode();
                        }
                    } else {
                        goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), waybillEntity.getProductCode());
                    }
                    goodsAreaCodeList.add(goodsAreaCode);
                }
            } else {
                // 专线查货区
                for (int i = 1; i < freightRouteLineList.size(); i++) {
                    addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
                    if(log.isInfoEnabled()){
                    	log.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
                    }
                    String goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), waybillEntity.getProductCode());
                    goodsAreaCodeList.add(goodsAreaCode);
                }
            }
        }
        
        //获取行政区域
        String countyRegion = getCountyRegion(waybillEntity);
        //最终到达外场全称
        printLabelBean.setArrivedOutFieldName(queryOutfieldName(lastChangeCenterOrgCode, false) 
                + (StringUtils.isEmpty(countyRegion)?"":"-"+countyRegion));

        
        //始发外场简称
        printLabelBean.setOriginateOutFieldSimpleName(queryOutfieldName(firstChangeCenterOrgCode, true));
        
        if(log.isInfoEnabled()){
        	log.info("==========最终外场全称============" + printLabelBean.getArrivedOutFieldName());
        }

        // 去掉最后营业部code 外场编码 取最后四位
        if (addressInfoList.size() >= TWO) {
            List<String> newOrgCode = new ArrayList<String>(FOUR);
            List<String> sortOrgCode = new ArrayList<String>(FOUR);
            int x = 0;

            // 倒序
            for (int i = addressInfoList.size() - TWO; (i >= 0 && x < FOUR); i--) {
                newOrgCode.add(addressInfoList.get(i));
                x++;
            }
            // 正序
            for (int k = newOrgCode.size(); k >= 1; k--) {
                sortOrgCode.add(newOrgCode.get(k - 1));
            }
            
            String[] addressCodes = new String[FOUR]; 
            
            for(int i=0; i<sortOrgCode.size(); i++){
                addressCodes[i] = queryOutfieldCode(sortOrgCode.get(i));
            }
            
            printLabelBean.setAddrs(Collections.unmodifiableList(Arrays.asList(addressCodes)));

        }

        // 货区编号 取最后四位
        if (goodsAreaCodeList.size() > 0) {
            List<String> goodsAreaCode = new ArrayList<String>(FOUR);
            int y = 0;
            for (int n = 0; (n < goodsAreaCodeList.size() && y < FOUR); n++) {
                goodsAreaCode.add(goodsAreaCodeList.get(n));
                y++;
            }
            
            String[] locationCodes = new String[FOUR];
            
            for(int i=0; i<goodsAreaCode.size(); i++){
                locationCodes[i] = goodsAreaCode.get(i);
            }
            
            printLabelBean.setLocations(Collections.unmodifiableList(Arrays.asList(locationCodes)));
        }
        
    }
    
    private String getCountyRegion(WaybillEntity waybillEntity){
        //处理行政区域
        String countyRegion = "";
        if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
                && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())
                && !productService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode()
                        ,waybillEntity.getBillTime())) {
            SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
                    .querySaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
            // 判断是否为驻地部门
            if (saleDepartmentEntity.getStation() != null
                    && FossConstants.YES.equals(saleDepartmentEntity
                            .getStation())) {
                // 判断提货方式
                if (WaybillConstants.SELF_PICKUP.equals(waybillEntity
                        .getReceiveMethod())
                        || WaybillConstants.INNER_PICKUP.equals(waybillEntity.getReceiveMethod())
                        || WaybillConstants.AIR_PICKUP_FREE.equals(waybillEntity.getReceiveMethod())
                        || WaybillConstants.AIRPORT_PICKUP.equals(waybillEntity.getReceiveMethod())
                        || WaybillConstants.AIR_SELF_PICKUP.equals(waybillEntity.getReceiveMethod())) {
                    countyRegion = WaybillConstants.SELF_PICKUP_REGION;
                } else {
                    // 非自提，那么就获取发货客户地址的
                    if (waybillEntity.getReceiveCustomerDistCode() != null
                            && !"".equals(waybillEntity
                                    .getReceiveCustomerDistCode())) {
                        String tempCountyRegion = administrativeRegionsService
                                .gainDistrictNameByCode(waybillEntity.getReceiveCustomerDistCode());
                        if (StringUtils.isNotEmpty(tempCountyRegion)) {
                            countyRegion = tempCountyRegion;
                        } else {
                            countyRegion = "";
                        }
                    } else {
                        countyRegion = "";
                    }
                }
            }
        }
        
        return countyRegion;
    }

    private String queryOutfieldCode(String pOrgCode) {
        OutfieldEntity outfieldEntity = null;
        // 判断 是否为空运总调
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(pOrgCode);
        if (orgEntity != null) {
            if (FossConstants.YES.equals(orgEntity.getAirDispatch())) {
                // 空运查外场
                outfieldEntity = outfieldService.queryOutfieldEntityByAirDispatchCode(pOrgCode);
            } else {
                // 偏线、专线查外场
                outfieldEntity = outfieldService.queryOutfieldByOrgCode(pOrgCode);
            }
        }
        return outfieldEntity == null ? null : outfieldEntity.getCode();
    }
    
    private String queryOutfieldName(String pOrgCode, boolean isSimpleName) {
        Assert.isTrue(StringUtil.isNotEmpty(pOrgCode), "组织编码参数可为空！");
        OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pOrgCode);
        Assert.notNull(org, "根据组织编码没有找到对应的组织！组织编码为:"+pOrgCode);
        return isSimpleName ? org.getOrgSimpleName() : org.getName();
    }
    
    private void fillOrderInfo(LabelInfoDto printLabelBean){
        OmsOrderEntity omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(printLabelBean.getWaybillNo());
        printLabelBean.setOrderType(omsOrderEntity.getOrderType());
        printLabelBean.setGoodsWeightTotal(omsOrderEntity.getGoodsWeightTotal());
        printLabelBean.setGoodsVolumeTotal(omsOrderEntity.getGoodsVolumeTotal());
        printLabelBean.setDeliveryCustomerProvCode(omsOrderEntity.getDeliveryCustomerProvCode());
        printLabelBean.setDeliveryCustomerCityCode(omsOrderEntity.getDeliveryCustomerCityCode());
        printLabelBean.setDeliveryCustomerDistCode(omsOrderEntity.getDeliveryCustomerDistCode());
        printLabelBean.setDeliveryCustomerTownCode(omsOrderEntity.getDeliveryCustomerTownCode());
        printLabelBean.setDeliveryCustomerAddress(omsOrderEntity.getDeliveryCustomerAddress());
        printLabelBean.setDeliveryCustomerAddressCmt(omsOrderEntity.getDeliveryCustomerAddressCmt());
        printLabelBean.setDeliveryCustomerName(omsOrderEntity.getDeliveryCustomerName());
        printLabelBean.setDeliveryCustomerCode(omsOrderEntity.getDeliveryCustomerCode());
        printLabelBean.setDeliveryCustomerContact(omsOrderEntity.getDeliveryCustomerContact());
        printLabelBean.setDeliveryCustomerPhone(omsOrderEntity.getDeliveryCustomerPhone());
        printLabelBean.setDeliveryCustomerMobilephone(omsOrderEntity.getDeliveryCustomerMobilephone());
        printLabelBean.setGoodsName(omsOrderEntity.getGoodsName());
        printLabelBean.setGoodsTypeCode(omsOrderEntity.getGoodsTypeCode());
        printLabelBean.setOtherComment(omsOrderEntity.getOtherComment());
        printLabelBean.setProductCode(omsOrderEntity.getProductCode());
        printLabelBean.setCustomerPickupOrgCode(omsOrderEntity.getCustomerPickupOrgCode());
        printLabelBean.setOrderChannel(omsOrderEntity.getOrderChannel());
        printLabelBean.setChannelCode(omsOrderEntity.getChannelCode());
        printLabelBean.setChannelNo(omsOrderEntity.getChannelNo());
        printLabelBean.setPaidMethod(omsOrderEntity.getPaidMethod());
        printLabelBean.setCodType(omsOrderEntity.getCodType());
        printLabelBean.setCodAmount(omsOrderEntity.getCodAmount() == null
                ? BigDecimal.ZERO : omsOrderEntity.getCodAmount());
        printLabelBean.setAccountCode(omsOrderEntity.getAccountCode());
        printLabelBean.setAccountName(omsOrderEntity.getAccountName());
        printLabelBean.setAccountBank(omsOrderEntity.getAccountBank());
        printLabelBean.setInsuranceAmount(omsOrderEntity.getInsuranceAmount() == null
                ? BigDecimal.ZERO : omsOrderEntity.getInsuranceAmount());
        printLabelBean.setDimension(omsOrderEntity.getDimension());
        printLabelBean.setReceiveCustomerProvCode(omsOrderEntity.getReceiveCustomerProvCode());
        printLabelBean.setReceiveCustomerCityCode(omsOrderEntity.getReceiveCustomerCityCode());
        printLabelBean.setReceiveCustomerDictCode(omsOrderEntity.getReceiveCustomerDictCode());
        printLabelBean.setReceiveCustomerTownCode(omsOrderEntity.getReceiveCustomerTownCode());
        printLabelBean.setReceiveCustomerAddress(omsOrderEntity.getReceiveCustomerAddress());
        printLabelBean.setReceiveCustomerAddressCmt(omsOrderEntity.getReceiveCustomerAddressCmt());
        printLabelBean.setReceiveCustomerName(omsOrderEntity.getReceiveCustomerName());
        printLabelBean.setReceiveCustomerPhone(omsOrderEntity.getReceiveCustomerPhone());
        printLabelBean.setReceiveCustomerMobilephone(omsOrderEntity.getReceiveCustomerMobilephone());
        printLabelBean.setReceiveCustomerCode(omsOrderEntity.getReceiveCustomerCode());
        printLabelBean.setOtherFee(omsOrderEntity.getOtherFee()==null 
                ? BigDecimal.ZERO : omsOrderEntity.getOtherFee());
        printLabelBean.setOrderAcceptOrgName(omsOrderEntity.getOrderAcceptOrgName());
        printLabelBean.setOrderAcceptOrgCode(omsOrderEntity.getOrderAcceptOrgCode());
        printLabelBean.setPickupToDoor(omsOrderEntity.getPickupToDoor());
        printLabelBean.setFreePickupGoods(omsOrderEntity.getFreePickupGoods());
        printLabelBean.setIncomeOrgCode(omsOrderEntity.getIncomeOrgCode());
        printLabelBean.setIncomeOrgName(omsOrderEntity.getIncomeOrgName());
        printLabelBean.setLtlewBigCustomer(omsOrderEntity.getLtlewBigCustomer());
        printLabelBean.setPickupCentralized(omsOrderEntity.getPickupCentralized());
        printLabelBean.setTargetSystem(omsOrderEntity.getTargetSystem());
        printLabelBean.setLinkmanId(omsOrderEntity.getLinkmanId());
        printLabelBean.setOwsLoginName(omsOrderEntity.getOwsLoginName());
    }

    public IWaybillPendingService getWaybillPendingService() {
        return waybillPendingService;
    }

    public void setWaybillPendingService(
            IWaybillPendingService waybillPendingService) {
        this.waybillPendingService = waybillPendingService;
    }

    public IProductService getProductService() {
        return productService;
    }

    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
        return vehicleAgencyDeptService;
    }

    public void setVehicleAgencyDeptService(
            IVehicleAgencyDeptService vehicleAgencyDeptService) {
        this.vehicleAgencyDeptService = vehicleAgencyDeptService;
    }

    public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
        return orgAdministrativeInfoService;
    }

    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public IOmsOrderService getOmsOrderService() {
        return omsOrderService;
    }

    public void setOmsOrderService(IOmsOrderService omsOrderService) {
        this.omsOrderService = omsOrderService;
    }

    public IWaybillManagerService getWaybillManagerService() {
        return waybillManagerService;
    }

    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
        return orgAdministrativeInfoComplexService;
    }

    public void setOrgAdministrativeInfoComplexService(
            IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    public IGoodsAreaService getGoodsAreaService() {
        return goodsAreaService;
    }

    public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
        this.goodsAreaService = goodsAreaService;
    }

    public IFreightRouteService getFreightRouteService() {
        return freightRouteService;
    }

    public void setFreightRouteService(IFreightRouteService freightRouteService) {
        this.freightRouteService = freightRouteService;
    }

    public IOutfieldService getOutfieldService() {
        return outfieldService;
    }

    public void setOutfieldService(IOutfieldService outfieldService) {
        this.outfieldService = outfieldService;
    }

    public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }

    public void setAdministrativeRegionsService(
            IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }
}
