package com.deppon.foss.module.pickup.pricing.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValidateForCUBCService;
import com.deppon.foss.module.pickup.pricing.api.server.util.CUBCUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CanChangeForCUBCDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DebitForCUBCDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultCUBCDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;
import com.deppon.foss.util.UUIDUtils;
import org.apache.commons.httpclient.HttpStatus;

import java.math.BigDecimal;
import java.util.Date;



/**
 * Created by 343617 on 2016/11/3.
 * 此类提供与CUBC进行交互的方法
 */
public class ValidateForCUBCService implements IValidateForCUBCService {

    /**
     * 系统配置参数
     */
    private IConfigurationParamsService configurationParamsService;

    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    private static int ZERO = 0;

    private static int ONE = 1;

    private static int TWO = 2;

    private final static int NUMBER_300 = 2000;



    public void setFOSSCUBCCHECKURL(String FOSSCUBCCHECKURL){
        this.FOSSCUBCCHECKURL=FOSSCUBCCHECKURL;
    }

    public void setFOSSCUBCMODCURL(String FOSSCUBCMODCURL) {
        this.FOSSCUBCMODCURL = FOSSCUBCMODCURL;
    }

    private  String FOSSCUBCCHECKURL=null;

    private  String FOSSCUBCMODCURL=null;



    @Override
    //调用CUBC接口，传入单号，判断能否发更改或作废
    public Object[] canModForCUBC(String waybillNo, String method) {
        //输入单号不能为空
        if (StringUtil.isNotEmpty(waybillNo)) {
            CanChangeForCUBCDto result ;
            String url = FOSSCUBCMODCURL;
            //封装JSON数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("waybillNo", waybillNo);
            jsonObject.put("operate",method);
            String transJson = jsonObject.toString();
            int timeOut = this.getTimeOut();


            //记录开始时间
            long beginTime = System.currentTimeMillis();
            ResultCUBCDto resultCUBCDto = CUBCUtil.synExecuteMethod(url, transJson, timeOut);
            //调用方法后所耗时间
            long costTime = System.currentTimeMillis() - beginTime;
            //获取id，jsonResponse，statusCode，
            String jsonResponse = resultCUBCDto.getJsonResponse();
            int statusCode = resultCUBCDto.getStatusCode();
            String desc1 = null;
            if(StringUtil.equals(method,WaybillConstants.CHANGE)){
                desc1= WaybillConstants.CUBC_CAN_CHANGE;
            }else if(StringUtil.equals(method,WaybillConstants.CANCEL)){
                desc1 = WaybillConstants.CUBC_CAN_CANCEL;
            }
            //封装基本数据
            CUBCLogEntity cubcLogEntity = this.getCubcLogEntity(UUIDUtils.getUUID(), transJson, jsonResponse, url, new Date(beginTime), costTime,statusCode,desc1, waybillNo);
            //调用方法，封装需要判断转换的数据，正常相应就能获得转换类
            result = this.getMyResult(cubcLogEntity, resultCUBCDto, CanChangeForCUBCDto.class);
            if(statusCode!=HttpStatus.SC_OK){
                result = new CanChangeForCUBCDto();
                result.setResult(false);
                result.setMessage("CUBC接口异常");
            }else{
                //如果ESB直接返回异常信息，则做转换
                if(StringUtil.equals(result.getMessage(),"异常信息:Connection refused")){
                    result.setMessage("CUBC接口异常");
                }
            }



            Object[] objects = new Object[TWO];
            objects[ZERO] = result;
            objects[ONE] = cubcLogEntity;
            return objects;
        } else {
            throw new BusinessException("传入的单号不能为空");
        }
    }

    @Override
    //调用CUBC接口，传入客户编码，收货部门编码，付款方式，结算金额，判断信用额度是否足够。
    public Object[] isBeBebtForCUBC(String customerCode, String orgCode, String debtType, BigDecimal debtAmount) {
        // 判断参数
        // 判断欠款方式
        // 判断付款方式是否为空
        if (StringUtil.isEmpty(debtType)) {
            throw new BusinessException("接口传入的付款方式不能为空！");
        }
        // 判断付款方式是否为非月结或临欠
        else if (!WaybillConstants.TEMPORARY_DEBT.equals(debtType)
                && !WaybillConstants.MONTH_PAYMENT.equals(debtType)) {
            throw new BusinessException("接口传入的付款方式非临欠或月结！");
        }
        // 判断欠款额度
        if (null == debtAmount) {
            throw new BusinessException("欠款额度不能为空！");
        }
        DebitForCUBCDto result= new DebitForCUBCDto();
        //信用校验默认通过，防止解析异常和服务器返回异常
        result.setIsSuccess(true);
        //此为调用CUBC接口的地址，做成配置参数(目前先写死，以后修改)
        String url = FOSSCUBCCHECKURL;
        //封装JSON数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customerCode", customerCode);
        jsonObject.put("deptCode", orgCode);
        jsonObject.put("billSettle", debtType);
        jsonObject.put("money",debtAmount.doubleValue());
        jsonObject.put("businessType",WaybillConstants.FOSS);
        String transJson = jsonObject.toString();
        int timeOut = this.getTimeOut();

        //记录开始时间
        long beginTime = System.currentTimeMillis();
        ResultCUBCDto resultCUBCDto = CUBCUtil.synExecuteMethod(url, transJson, timeOut);
        //调用方法后所耗时间
        long costTime = System.currentTimeMillis() - beginTime;
        //获取id，jsonResponse，statusCode，
        String jsonResponse = resultCUBCDto.getJsonResponse();
        int statusCode = resultCUBCDto.getStatusCode();
        String desc1 = WaybillConstants.CUBC_BE_BEBT;
        //封装基本数据
        CUBCLogEntity cubcLogEntity = this.getCubcLogEntity(UUIDUtils.getUUID(), transJson, jsonResponse, url, new Date(beginTime), costTime,statusCode,desc1, customerCode);
        //调用方法，封装需要判断转换的数据，正常相应就能获得转换类
        //20161226-zhaoyiqing 343617 修复响应码不为200无法通过
        DebitForCUBCDto resultNew = this.getMyResult(cubcLogEntity, resultCUBCDto, DebitForCUBCDto.class);
        if(resultNew!=null){
            result = resultNew;
        }
        if(statusCode!=HttpStatus.SC_OK){
            result.setIsSuccess(true);
        }
        Object[] objects = new Object[TWO];
        objects[ZERO] = result;
        objects[ONE] = cubcLogEntity;
        return objects;
    }


    //通过配置参数获取超时时间，没有配置默认300
    private int getTimeOut() {
        //通过配置参数获取值
        int timeOut= NUMBER_300;
        return timeOut;
    }

    //抽取方法用来封装数据
    private CUBCLogEntity getCubcLogEntity(String id, String transJson, String jsonResponse, String url, Date date, long costTime,int statusCode,String desc1, String desc2) {
        //封装传来的参数
        CUBCLogEntity cubcLogEntity = new CUBCLogEntity();
        cubcLogEntity.setId(id);
        cubcLogEntity.setRequestContent(transJson);
        cubcLogEntity.setResponseContent(jsonResponse);
        cubcLogEntity.setCodeOrUrl(url);
        cubcLogEntity.setCreateTime(date);
        cubcLogEntity.setResponseTime(costTime);
        cubcLogEntity.setStatu(String.valueOf(statusCode));
        cubcLogEntity.setDesc1(desc1);
        cubcLogEntity.setDesc2(desc2);
        return cubcLogEntity;
    }

    /**
     * Created by 343617 on 2016/11/3.
     * @param resultCUBCDto 接收接口交互的参数Dto
     * @param cubcLogEntity 用来封装写入数据库
     * @param clazz         接收要封装成的参数
     * @return T            泛型，和传入参数对应的类型一致
     */
    private  <T> T getMyResult(CUBCLogEntity cubcLogEntity,ResultCUBCDto resultCUBCDto,Class<T> clazz){
        int statusCodeCUBC = resultCUBCDto.getStatusCode();
        T t = null;
        if(statusCodeCUBC == HttpStatus.SC_OK){
            try {
                t = JSONObject.parseObject(cubcLogEntity.getResponseContent(),clazz);
            } catch (Exception e) {
            }
            cubcLogEntity.setIsSuccess(WaybillConstants.YES);
        }else{
            cubcLogEntity.setIsSuccess(WaybillConstants.NO);
            cubcLogEntity.setErrorMsg(resultCUBCDto.getErrorMessage());
        }
        return t;
    }


}
