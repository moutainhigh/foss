package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.dao.IUnifiedSettlementDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IUnifiedSettlementService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.UnifiedSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.CollectionUtils;

/**
 * Created by 073615 on 2014/11/17.
 */
public class UnifiedSettlementService implements IUnifiedSettlementService{
    /**
     * 统一结算DAO
     */
    IUnifiedSettlementDao unifiedSettlementDao;
    /**
     * 产品类型
     */
    IProductService productService;

    /**
     * 根据条件分页查询统一结算明细
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    @Override
    public List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto, int start, int limit) {
        List<UnifiedSettlementEntity> list= unifiedSettlementDao.queryByCondition(dto,start,limit);
        return list;
    }

    /**
     * 根据条件查询所有统一结算明细
     * @param dto
     * @return
     */
    @Override
    public List<UnifiedSettlementEntity> queryBycondition(UnifiedSettlementDto dto) {
        List<UnifiedSettlementEntity> list = unifiedSettlementDao.queryByCondition(dto);
        return list;
    }

    /**
     * 根据条件查询总条数
     * @param dto
     * @return
     */
    @Override
    public int queryByConditionCount(UnifiedSettlementDto dto) {
        int rowcount = unifiedSettlementDao.queryByConditionCount(dto);
        return rowcount;
    }

    @Override
    public UnifiedSettlementEntity queryByConditionSum(UnifiedSettlementDto dto) {
        return unifiedSettlementDao.queryAmountSum(dto);
    }

    /**
     * 导出Excel
     * @return
     */
    @Override
    public InputStream exportUnifiedSettlementExcel(UnifiedSettlementDto dto) {
        InputStream inputStream= null;
        if(!StringUtils.isBlank(dto.getPeriodId())){
            int rowCount = unifiedSettlementDao.queryByConditionCount(dto);
            if(rowCount>0){
                List<UnifiedSettlementEntity> list = unifiedSettlementDao.queryByCondition(dto);
                //封装数据
                ExportResource resource = this.getResource(list);
                ExportSetting exportSetting = new ExportSetting();
                //sheet名称
                String sheetName = "统一结算"+dto.getPeriodId();
                //导出设置
                exportSetting.setSheetName(sheetName);
                Map<Integer,String> cellType = new HashMap<Integer, String>();
                for(int i=SettlementReportNumber.ZERO;i<SettlementReportNumber.SIXTY_TWO;i++){
                    cellType.put(i+SettlementReportNumber.EIGHT,"float");
                }
                exportSetting.setDataType(cellType);
                ExporterExecutor executor = new ExporterExecutor();
                inputStream = executor.exportSync(resource, exportSetting);
            }else{
                throw new SettlementException("没有数据需要导出的数据！");
            }

        }else{
            throw new SettlementException("期间不能为空");
        }

        return inputStream;
    }

    /**
     * 获取Excel导出内容
     * @return
     */
    private ExportResource getResource(List<UnifiedSettlementEntity> list){
        ExportResource data = new ExportResource();
        String[] header = {"期间",
                "发票标记",
                "客户名称",
                "客户编码",
                "产品类型",
                "部门类型",
                "部门编码",
                "部门名称",
                "还款银行未签收",
                "还款银行已签收",
                "还款银行未签收",
                "还款银行已签收",
                "应付代收货款冲01应收到付运费已签收",
                "应付代收货款冲01应收到付运费未签收",
                "应付代收货款冲02应收到付运费已签收",
                "应付代收货款冲02应收到付运费未签收",
                "应付代收货款冲02应收始发运费已签收",
                "应付代收货款冲02应收始发运费未签收",
                "应付代收货款冲02小票应收",
                "01理赔冲02始发应收已签收",
                "02理赔冲01始发应收已签收",
                "01理赔冲02始发应收未签收",
                "02理赔冲01始发应收未签收",
                "01理赔冲收入",
                "02理赔冲收入",
                "01理赔冲01到达应收已签收",
                "02理赔冲01到达应收已签收",
                "01理赔冲01到达应收未签收",
                "02理赔冲01到达应收未签收",
                "01理赔冲02到达应收已签收",
                "02理赔冲02到达应收已签收",
                "01理赔冲02到达应收未签收",
                "02理赔冲02到达应收未签收",
                "01理赔付款申请",
                "01预收客户冲02应收始发运费未签收",
                "01预收客户冲02应收始发运费已签收",
                "01预收客户冲01应收到付运费未签收",
                "01预收客户冲02应收到付运费未签收",
                "02预收客户冲01应收到付运费未签收",
                "02预收客户冲02应收到付运费未签收",
                "01预收客户冲01应收到付运费已签收",
                "01预收客户冲02应收到付运费已签收",
                "02预收客户冲01应收到付运费已签收",
                "02预收客户冲02应收到付运费已签收",
                "01应付理赔冲02小票应收",
                "02应付理赔冲01小票应收",
                "01预收客户冲02小票应收",
                "01退运费冲02始发应收已签收",
                "02退运费冲01始发应收已签收",
                "01退运费冲02始发应收未签收",
                "02退运费冲01始发应收未签收",
                "01退运费冲收入",
                "02退运费冲收入",
                "01退运费付款申请",
                "01退运费冲01到达应收已签收",
                "02退运费冲01到达应收已签收",
                "01退运费冲01到达应收未签收",
                "02退运费冲01到达应收未签收",
                "01退运费冲02到达应收已签收",
                "02退运费冲02到达应收已签收",
                "01退运费冲02到达应收未签收",
                "02退运费冲02到达应收未签收"
        };
        String[] columns= {"period",
                "invoiceMark",
                "customerName",
                "customerCode",
                "productCode",
                "orgType",
                "orgCode",
                "orgName",
                "uroDestCdNpod",
                "uroDestCdPod",
                "urtDestCdNpod",
                "urtDestCdPod",
                "codPayWoDestRcvoPod",
                "codPayWoDestRcvoNpod",
                "codPayWoDestRcvtPod",
                "codPayWoDestRcvtNpod",
                "codPayWoOrigRcvtPod",
                "codPayWoOrigRcvtNpod",
                "codPayWoOthRcvt",
                "claimOrigoWoOrigRcvtPod",
                "claimOrigtOrigRcvoPod",
                "claimOrigoWoOrigRcvtNpod",
                "claimOrigtWoOrigRcvoNpod",
                "claimDestoIncome",
                "claimDesttIncome",
                "claimDestoWoDestRcvoPod",
                "claimDesttWoDestRcvoPod",
                "claimDestoWoDestRcvoNpod",
                "claimDesttWoDestRcvoNpod",
                "claimDestoWoDestRcvtPod",
                "claimDesttWoDestRcvtPod",
                "claimDestoWoDestRcvtNpod",
                "claimDesttWoDestRcvtNpod",
                "claimDestoPayApply",
                "custDroWoOrigRcvtNpod",
                "custDroWoOrigRcvtPod",
                "custDroWoDestRcvoNpod",
                "custDroWoDestRcvtNpod",
                "custDrtWoDestRcvoNpod",
                "custDrtWoDestRcvtNpod",
                "custDroWoDestRcvoPod",
                "custDroWoDestRcvtPod",
                "custDrtWoDestRcvoPod",
                "custDrtWoDestRcvtPod",
                "orClaimPayoWoRcvt",
                "orClaimPaytWoRcvo",
                "orCustDroWoRcvt",
                "rdOrigoWoOrigRcvtPod",
                "rdOrigtWoOrigRcvoPod",
                "rdOrigoWoOrigRcvtNpod",
                "rdOrigtWoOrigRcvoNpod",
                "rdDestoIncome",
                "rdDesttIncome",
                "rdDestoPayApply",
                "rdDestoDestRcvoPod",
                "rdDesttWoDestRcvoPod",
                "rdDestoWoDestRcvoNpod",
                "rdDesttWoDestRcvoNpod",
                "rdDestoDestRcvtPod",
                "rdDesttWoDestRcvtPod",
                "rdDestoWoDestRcvtNpod",
                "rdDesttWoDestRcvtNpod"};
        //封装数据
        List<List<String>> showData = new ArrayList<List<String>>();
        List<String> rowList = null;
        Object fieldValue = null;
        String cellValue = null;
        //处理产品类型
        List<ProductEntity> productList= productService.queryLevel3ProductInfo();
        Map<String,String> productCache= new HashMap<String, String>();
        if(CollectionUtils.isNotEmpty(productList)){
            for(ProductEntity entity:productList){
                productCache.put(entity.getCode(),entity.getName());
            }
        }
        //处理发票标记
        Map<String,Object> invoiceMarkCache = new HashMap<String,Object>();
        Map<String, Map<String, Object>> cache = SettlementUtil.getDataDictionaryByTermsCodes(
                Arrays.asList(DictionaryConstants.SETTLEMENT_INVOICE_MARK));
        if(cache != null && CollectionUtils.isNotEmpty(cache.keySet())
                &&MapUtils.isNotEmpty(cache.get(DictionaryConstants.SETTLEMENT_INVOICE_MARK))){
            invoiceMarkCache= cache.get(DictionaryConstants.SETTLEMENT_INVOICE_MARK);
        }
        //处理部门类型
        Map<String,Object> orgTypeCache = new HashMap<String, Object>();
        cache = SettlementUtil.getDataDictionaryByTermsCodes(Arrays.asList(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE) );
        if(cache!= null && CollectionUtils.isNotEmpty(cache.keySet())
                && MapUtils.isNotEmpty(cache.get(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE))){
                orgTypeCache = cache.get(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE);
        }
        //封装数据位Excel格式
        for(UnifiedSettlementEntity entity:list){
            rowList = new ArrayList<String>();
            for(String colName:columns){
                fieldValue = ReflectionUtils.getFieldValue(entity,colName);
                cellValue=(fieldValue==null?"":fieldValue.toString());
                //处理产品类型
                if("productCode".equals(colName)&&
                        productCache.get(cellValue) != null){
                   cellValue = productCache.get(cellValue);
                //部门类型
                }else if("orgType".equals(colName)){
                    if("C".equals(cellValue)){
                        cellValue="合同";
                    }else if(orgTypeCache.get(cellValue)!= null){
                    cellValue = orgTypeCache.get(cellValue).toString();
                    }else{
                        cellValue ="";
                    }
                //发票标记
                }else if("invoiceMark".equals(colName)&&
                        invoiceMarkCache.get(cellValue)!= null){
                    cellValue = invoiceMarkCache.get(cellValue).toString();
                }
                rowList.add(cellValue);
            }
            showData.add(rowList);
        }
        data.setHeads(header);
        data.setRowList(showData);
        this.sheetHead(data);
        return data;
    }

    /**
     * 拼装Excel单元格
     * @param sheet
     */
    private void sheetHead(ExportResource sheet){
        List<HeaderRows> headerList = new ArrayList<HeaderRows>();
        sheet.setHeaderHeight(SettlementReportNumber.THREE);
        HeaderRows header1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.SEVEN, "数据统计维度");
        HeaderRows header2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.EIGHT,SettlementReportNumber.NINE,"还款运单总运费（到付）【01】");
        HeaderRows header3 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.TEN,SettlementReportNumber.ELEVEN,"还款运单总运费（到付）【02】");
        HeaderRows header4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.TWELVE,SettlementReportNumber.EIGHTEEN,"代收货款");
        HeaderRows header5 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.NINETEEN,SettlementReportNumber.THIRTY_THREE,"理赔");
        HeaderRows header6 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ONE,SettlementReportNumber.THIRTY_FOUR,SettlementReportNumber.FORTY_THREE,"预收客户");
        HeaderRows header7 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FORTY_FOUR,SettlementReportNumber.FORTY_SIX,"小票");
        HeaderRows header8 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FORTY_SEVEN,SettlementReportNumber.SIXTY_ONE,"退运费");
        HeaderRows header9 =  new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.FIFTEEN,SettlementReportNumber.EIGHTEEN,"出发部门申请");
        HeaderRows header10 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.NINETEEN,SettlementReportNumber.TWENTY_NINE,"到达部门申请");
        HeaderRows header11 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.FORTY_FOUR,SettlementReportNumber.FORTY_SIX,"小票核销");
        HeaderRows header12 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.FORTY_SEVEN,SettlementReportNumber.FIFTY,"出发部门申请");
        HeaderRows header13 = new HeaderRows(SettlementReportNumber.ONE,SettlementReportNumber.ONE,SettlementReportNumber.FIFTY_ONE,SettlementReportNumber.SIXTY_ONE,"到达部门申请");
        headerList.add(header1);
        headerList.add(header2);
        headerList.add(header3);
        headerList.add(header4);
        headerList.add(header5);
        headerList.add(header6);
        headerList.add(header7);
        headerList.add(header8);
        headerList.add(header9);
        headerList.add(header10);
        headerList.add(header11);
        headerList.add(header12);
        headerList.add(header13);
        sheet.setHeaderList(headerList);
    }

    public void setUnifiedSettlementDao(IUnifiedSettlementDao unifiedSettlementDao) {
        this.unifiedSettlementDao = unifiedSettlementDao;
    }

    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
