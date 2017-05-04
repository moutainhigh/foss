package com.deppon.foss.module.pickup.order.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.pickup.order.api.server.dao.IBigCustomeDao;
import com.deppon.foss.module.pickup.order.api.server.service.IBigCustomeService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.BigCustomeException;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeService implements IBigCustomeService {
    private static final int NUMBER = 20000;
    private IBigCustomeService iBigCustomeService;
    private IBigCustomeDao iBigCustomeDao;

    @Override
    public BigCustomeDto queryBigCustomeDto(Date curDate, Date preDate) {
        BigCustomeDto dto = iBigCustomeDao.queryBigCustomeDto(curDate, preDate);
        if (dto == null) {
            throw new BigCustomeException(BigCustomeException.BIGCUSTOMENULL);
        }
        return dto;
    }


    @Override
    public List<BigCustomeDto> queryBigCustomeList(Date curDate, Date preDate,int start, int limit) {
        return iBigCustomeDao.queryBigCustomeList(curDate, preDate,start,limit);
    }

    @Override
    public List<CombinateBillEntity> queryBigCustomeSummaryList(Date billTimeFrom, Date billTimeTo) {
        return iBigCustomeDao.queryBigCustomeSummaryList(billTimeFrom, billTimeTo);
    }

    public void setiBigCustomeService(IBigCustomeService iBigCustomeService) {
        this.iBigCustomeService = iBigCustomeService;
    }

    public void setiBigCustomeDao(IBigCustomeDao iBigCustomeDao) {
        this.iBigCustomeDao = iBigCustomeDao;
    }

    /**
     * 页面显示明细总数
     * 按时间查询记录
     */
    @Override
    public Long queryBigCustomeTotalCount(Date billTimeFrom, Date billTimeTo){
        return (Long)iBigCustomeDao.queryBigCustomeTotalCount(billTimeFrom,billTimeTo);
    }

    /**
     * 查询导出
     *
     * @author caohuibin
     * 2015.9.18
     */
    @Override
    public InputStream queryExport(Date billTimeFrom, Date billTimeTo) {
        //如果传入bigCustomeDto不为空
        List<CombinateBillEntity> bigCustomerSummaryEntityList = this.queryBigCustomeSummaryList(billTimeFrom, billTimeTo);
        List<List<String>> rowList = new ArrayList<List<String>>();
        for (CombinateBillEntity bigCustomerSummaryEntity : bigCustomerSummaryEntityList) {
            List<String> columnList = new ArrayList<String>();
            if(WaybillConstants.YES.equals(bigCustomerSummaryEntity.getActive())){
            //运单号
            columnList.add(bigCustomerSummaryEntity.getWaybillNo());
            //收货门店编码
            columnList.add(bigCustomerSummaryEntity.getCustomerPickupOrgCode());
            //收货人
            columnList.add(bigCustomerSummaryEntity.getReceiveCustomerContact());
            //收货地址
            columnList.add(bigCustomerSummaryEntity.getReceiveCustomerAddress());
            //提货方式
            if(WaybillConstants.DELIVER_FREE.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("免费送货");	//免费送货
            }else if(WaybillConstants.DELIVER_STORAGE.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("送货进仓");	//送货进仓
            }else if(WaybillConstants.DELIVER_NOUP.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("送货(不含上楼)");	//送货(不含上楼)
            }else if(WaybillConstants.DELIVER_UP.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("送货上楼");	//送货上楼
            }else if(WaybillConstants.INNER_PICKUP.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("内部带货自提");	//内部带货自提
            }else if(WaybillConstants.LARGE_DELIVER_UP.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("大件上楼");	//大件上楼
            }else if(WaybillConstants.SELF_PICKUP.equals(bigCustomerSummaryEntity.getReceiveMethod())){
                columnList.add("自提");	//自提
            }else {
                columnList.add(null);
            }

            //付款方式
            if(WaybillConstants.CREDIT_CARD_PAYMENT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("银行卡");	//银行卡
            }else if(WaybillConstants.CHECK.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("支票");	//支票
            }else if(WaybillConstants.TEMPORARY_DEBT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("临时欠款");	//临时欠款
            }else if(WaybillConstants.ARRIVE_PAYMENT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("到付");	//到付
            }else if(WaybillConstants.CASH_PAYMENT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("现金");	//现金
            }else if(WaybillConstants.TELEGRAPHIC_TRANSFER.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("电汇");	//电汇
            }else if(WaybillConstants.ONLINE_PAYMENT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("网上支付");	//网上支付
            }else if(WaybillConstants.MONTH_PAYMENT.equals(bigCustomerSummaryEntity.getPaidMethod())){
                columnList.add("月结");	//月结
            }else{
                columnList.add(null);
            }
            //运输性质
            if(WaybillConstants.TRUCK_FLIGHT.equals(bigCustomerSummaryEntity.getProductCode())){
                columnList.add("精准卡航");	//精准卡航
            }else if(WaybillConstants.AIR_FLIGHT.equals(bigCustomerSummaryEntity.getProductCode())){
                columnList.add("精准空运");	//精准空运
            }else if(WaybillConstants.FSF_FLIGHT.equals(bigCustomerSummaryEntity.getProductCode())){
                columnList.add("精准城运");	//精准城运
            }else if(WaybillConstants.LRF_FLIGHT.equals(bigCustomerSummaryEntity.getProductCode())){
                columnList.add("精准汽运(长途)");	//精准汽运(长途)
            }else if(WaybillConstants.SRF_FLIGHT.equals(bigCustomerSummaryEntity.getProductCode())){
                columnList.add("精准汽运(短途)");	//精准汽运(短途)
            }else{
                columnList.add(null);
            }
            //件数
            columnList.add(bigCustomerSummaryEntity.getPieces().toString());
            //重量
            columnList.add(String.valueOf(bigCustomerSummaryEntity.getGoodsWeightTotal()));
            //体积
            columnList.add(String.valueOf(bigCustomerSummaryEntity.getGoodsVolumeTotal()));
            //ERP单号
            columnList.add(bigCustomerSummaryEntity.getErpOrderNo());
            //标签编码
            columnList.add(bigCustomerSummaryEntity.getCustomerLableNums());

            rowList.add(columnList);
            }
        }

        String[] rowHeads = {"运单号", "收货门店编码", "收货人", "收货地址", "提货方式", "付款方式", "运输性质",
                    "件数", "重量", "体积", "ERP单号", "标签编码"};
        ExportResource exportResource = new ExportResource();
        exportResource.setHeads(rowHeads);
        exportResource.setRowList(rowList);
        ExportSetting exportSetting = new ExportSetting();
        exportSetting.setSheetName("合票明细列表");
        exportSetting.setSize(NUMBER);
        ExporterExecutor objExporterExecutor = new ExporterExecutor();
        return objExporterExecutor.exportSync(exportResource, exportSetting);
    }

}
