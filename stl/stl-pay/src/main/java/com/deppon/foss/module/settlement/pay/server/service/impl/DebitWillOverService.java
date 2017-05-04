package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDebitWillOverDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IDebitWillOverService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DebitWillOverDto;
import com.deppon.foss.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 073615 on 2014/12/26.
 */
public class DebitWillOverService implements IDebitWillOverService {
    /**
     * 数据查询Dao
     */
    IDebitWillOverDao debitWillOverDao;

    /**
     * 根据条件查询
     * @param dto
     * @return
     */
    @Override
    public List<DebitWillOverEntity> queryByDebitInfobyCondition(DebitWillOverDto dto) {
        //获取当天的开始时间
        Date currentDate = new Date();
        Date startDate = DateUtils.getStartDatetime(currentDate);
        dto.setStartTime(startDate);
        List<DebitWillOverEntity> list = debitWillOverDao.getDebitInfoByCondition(dto);
        return list;
    }

    /**
     * 根据条件分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    @Override
    public List<DebitWillOverEntity> queryByDebitInfobyPages(DebitWillOverDto dto, int start, int limit) {
        //获取当天的开始时间
        Date currentDate = new Date();
        Date startDate = DateUtils.getStartDatetime(currentDate);
        dto.setStartTime(startDate);
        List<DebitWillOverEntity> list = debitWillOverDao.getDebitInfoByPages(start,limit,dto);
        return list;
    }

    /**
     * 导出
     * @param dto
     * @return
     */
    @Override
    public InputStream exportDebitInfo(DebitWillOverDto dto) {
    InputStream inputStream= null;
    final int numberOfEight = 8;
    final int numberOfNine = 9;
    int rowCount = (int)debitWillOverDao.getDebitInfoCount(dto);
                if(rowCount>0){
                    List<DebitWillOverEntity> list = debitWillOverDao.getDebitInfoByCondition(dto);
                    //封装数据
                    ExportResource resource = this.getResource(list);
                    ExportSetting exportSetting = new ExportSetting();
                    //sheet名称
                    Date currentDate = new Date();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
                    //导出设置
                    exportSetting.setSheetName(date+"结算天数");
                    Map<Integer,String> cellType = new HashMap<Integer, String>();
                    cellType.put(numberOfEight,"int");
                    cellType.put(numberOfNine,"int");
                    exportSetting.setDataType(cellType);
                    ExporterExecutor executor = new ExporterExecutor();
                    inputStream = executor.exportSync(resource, exportSetting);
                }


            return inputStream;

    }

    /**
     * 根据内容获取Excel 数据
     * @param list
     * @return
     */
    private ExportResource getResource(List<DebitWillOverEntity> list){
        ExportResource data = new ExportResource();
        String[] header = {"大区",
                "小区",
                "部门",
                "客户编码",
                "客户名称",
                "客户类型",
                "付款方式",
                "最早欠款日期",
                "结算天数",
                "剩余天数"
        };
        String[] columns= {"bigRegionName",
        "smallRegionName",
        "deptName",
        "customerCode",
        "customerName",
        "customerType",
        "paymentType",
        "minDebitTime",
        "debitDays",
        "remainDays"
        };
        //封装数据
        List<List<String>> showData = new ArrayList<List<String>>();
        List<String> rowList = null;
        Object fieldValue = null;
        String cellValue = null;
        //处理产品类型
        Map<String,String> customerTypeCache= new HashMap<String, String>();
        customerTypeCache.put("CT","月结客户");
        customerTypeCache.put("DT","临欠客户");

        //处理发票标记
        Map<String,String> paymentCache = new HashMap<String,String>();
        paymentCache.put("CT","月结");
        paymentCache.put("DT","临时欠款");

        //封装数据位Excel格式
        for(DebitWillOverEntity entity:list){
            rowList = new ArrayList<String>();
            for(String colName:columns){
                fieldValue = ReflectionUtils.getFieldValue(entity, colName);
                cellValue=(fieldValue==null?"":fieldValue.toString());
                //客户类型
                if("customerType".equals(colName)&&
                    customerTypeCache.get(cellValue) != null){
                    cellValue = customerTypeCache.get(cellValue);
                //付款方式
                }else if("paymentType".equals(colName)){
                    cellValue = paymentCache.get(cellValue);
                }else if("debitDays".equals(colName)&&
                        StringUtils.isBlank(cellValue)){
                    cellValue ="0";
                }else if("minDebitTime".equals(colName)){
                    cellValue = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(entity.getMinDebitTime());
                }
                rowList.add(cellValue);
            }
            showData.add(rowList);
        }
        data.setHeads(header);
        data.setRowList(showData);
        return data;

    }

    /**
     * 根据条件查询总行数
     * @param dto
     * @return
     */
    @Override
    public long queryDebitCount(DebitWillOverDto dto) {

        //获取当天的开始时间
        Date currentDate = new Date();
        Date startDate = DateUtils.getStartDatetime(currentDate);
        dto.setStartTime(startDate);
        long rowCount = debitWillOverDao.getDebitInfoCount(dto);
        return rowCount;
    }

    public void setDebitWillOverDao(IDebitWillOverDao debitWillOverDao) {
        this.debitWillOverDao = debitWillOverDao;
    }
}
