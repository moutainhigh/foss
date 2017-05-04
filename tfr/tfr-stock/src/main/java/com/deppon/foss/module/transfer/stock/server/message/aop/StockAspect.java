package com.deppon.foss.module.transfer.stock.server.message.aop;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.deppon.dpap.rocketmq.core.send.DefaultMQProducerTemplate;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity;
import com.deppon.foss.module.transfer.stock.server.message.define.ActionType;
import com.deppon.foss.module.transfer.stock.server.message.define.StockKind;
import com.deppon.foss.module.transfer.stock.server.message.define.ToPFDStockActionEntity;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 335284 on 2017/4/10.
 */
@Aspect
public class StockAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockAspect.class);

    private static final String FOSS_PUSH_STOCK_CODE = "FOSS_PUSH_STOCK_CODE";
    private static final String TFR_FOSS_STOCK_PUSH = "TFR_FOSS_STOCK_PUSH";
    private IConfigurationParamsService configurationParamsService;

    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    private DefaultMQProducerTemplate MQtemplate;

    public void setMQtemplate(DefaultMQProducerTemplate MQtemplate) {//MQtemplate
        this.MQtemplate = MQtemplate;
    }

    private boolean notPushStockConfig() {
//        String value = configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, TFR_FOSS_STOCK_PUSH,
//                FossConstants.ROOT_ORG_CODE);
        String value = "N";
        LOGGER.info("开始判断是否推送库存 {}", value);
        return FossConstants.YES.equals(value) ? false : true;//开关打开后就推送库存，返回false是因为要反悔
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.StockAdd * *(..))")
    public void afterAdd(JoinPoint jp, Object retVal) {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------afterAdd--" + jp.getSignature().getName());
        boolean useNow = false;//入库时间使用当前时间吗
        if (retVal instanceof Integer && (Integer) retVal != FossConstants.SUCCESS) {
            if (LOGGER.isWarnEnabled()) {
                StockEntity o = (StockEntity) jp.getArgs()[0];
                LOGGER.warn("保存失败，不进行推送 {} {}", new Object[]{o.getWaybillNO(), o.getSerialNO()});
            }
            return;
        } else if (retVal instanceof Map) {
            Map paramsMap = (Map) retVal;
            String exceptionInfo = (String) paramsMap.get("exceptionInfo");
            if (StringUtils.isNotBlank(exceptionInfo)) {
                String waybillNo = (String) paramsMap.get("waybillNo");
                String serialNo = (String) paramsMap.get("serialNo");
                LOGGER.error("add失败 不进行推送 运单号{} 流水号{} {}", new Object[]{waybillNo, serialNo, exceptionInfo});
                return;
            }
            useNow = true;//开单和反签收存储过程都是使用的当前时间
        }
        pushAddStock(jp.getArgs(), useNow);
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.StockDelete * *(..))")
    public void afterDelete(JoinPoint joinPoint, Object retVal) throws Throwable {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------afterDelete--" + joinPoint.getSignature().getName());
        if (retVal instanceof Integer) {
            if ((Integer) retVal != FossConstants.SUCCESS) {
                if (LOGGER.isWarnEnabled()) {
                    InOutStockEntity o = (InOutStockEntity) joinPoint.getArgs()[0];
                    LOGGER.warn("删除失败，不进行推送 {} {}", new Object[]{o.getWaybillNO(), o.getSerialNO()});
                }
                return;
            }
        } else if (retVal instanceof Map) {
            Map paramsMap = (Map) retVal;
            String exceptionInfo = (String) paramsMap.get("exceptionInfo");
            if (StringUtils.isNotBlank(exceptionInfo)) {
                String waybillNo = (String) paramsMap.get("waybillNo");
                String serialNo = (String) paramsMap.get("serialNo");
                LOGGER.error("batch删除失败 不进行推送 运单号{} 流水号{} {}", new Object[]{waybillNo, serialNo, exceptionInfo});
                return;
            }
        }
        pushDeleteStock(joinPoint.getArgs());
    }

    private void pushDeleteStock(Object[] args) {
        List<InOutStockEntity> list = new ArrayList<InOutStockEntity>(1);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.DELETE, StockKind.CENTER, list);
        if (args[0] instanceof InOutStockEntity) {
            InOutStockEntity inOutStockEntity = (InOutStockEntity) args[0];
            list.add(inOutStockEntity);
        } else if (args[0] instanceof List) {
            List outList = (List) args[0];
            for (Object o : outList) {
                InOutStockEntity inOutStockEntity = (InOutStockEntity) o;
                list.add(inOutStockEntity);
            }
        }
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("删除{} 推送mq完成，result {}", new Object[]{args[0], result});
    }

    private void pushAddStock(Object[] args, boolean useNow) {
        List<StockEntity> list = new ArrayList<StockEntity>(1);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.ADD, StockKind.CENTER, list);
        if (args[0] instanceof StockEntity) {
            StockEntity stockEntity = (StockEntity) args[0];
            list.add(stockEntity);
            if (useNow) {
                Date now = new Date();
                stockEntity.setInStockTime(now);
                stockEntity.setScanTime(now);
            }
        } else if (args[0] instanceof List) {
            List outList = (List) args[0];
            Date now = new Date();
            for (Object o : outList) {
                InOutStockEntity inOutStockEntity = (InOutStockEntity) o;
                StockEntity stockEntity = new StockEntity();
                BeanUtils.copyProperties(inOutStockEntity, stockEntity);
                list.add(stockEntity);
                if (useNow) {
                    stockEntity.setInStockTime(now);
                    stockEntity.setScanTime(now);
                }
            }
        } else if (args[0] instanceof StockSaleEntity) { //营业部虚拟库存
            StockSaleEntity saleEntity = (StockSaleEntity) args[0];
            StockEntity stockEntity = new StockEntity();
            BeanUtils.copyProperties(saleEntity, stockEntity);
            list.add(stockEntity);
        }
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("保存{} 推送mq完成，result {}", new Object[]{args[0], result});
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.SaleStockAdd * *(..))")
    public void afterSaleStockAdd(JoinPoint joinPoint, Object retVal) throws Throwable {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------aftersaleadd--" + joinPoint.getSignature().getName());
        Integer retVal1 = (Integer) retVal;
        StockSaleEntity o = (StockSaleEntity) joinPoint.getArgs()[0];
        if (retVal1 != FossConstants.SUCCESS) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("保存saleStock失败，不进行推送 {} {}", new Object[]{o.getWaybillNo(), o.getSerialNO()});
            }
        }
        List<StockSaleEntity> list = new ArrayList<StockSaleEntity>(1);
        list.add(o);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.ADD, StockKind.SALE, list);
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("保存sale {} 推送mq完成，result {}", new Object[]{o, result});
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.SaleStockDelete * *(..))")
    public void afterSaleStockDel(JoinPoint joinPoint, Object retVal) throws Throwable {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------aftersaleDelete--" + joinPoint.getSignature().getName());
        Integer retVal1 = (Integer) retVal;
        InOutStockEntity o = (InOutStockEntity) joinPoint.getArgs()[0];
        if (retVal1 != FossConstants.SUCCESS) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("del saleStock失败，不进行推送 {} {}", new Object[]{o.getWaybillNO(), o.getSerialNO()});
            }
        }
        List<InOutStockEntity> list = new ArrayList<InOutStockEntity>(1);
        list.add(o);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.ADD, StockKind.SALE, list);
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("保存sale {} 推送mq完成，result {}", new Object[]{o, result});
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.TogetherAdd * *(..))")
    public void afterTogetherAdd(JoinPoint joinPoint, Object retVal) throws Throwable {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------aftertogetheradd--" + joinPoint.getSignature().getName());
        Integer retVal1 = (Integer) retVal;
        TogetherTruckStockEntity o = (TogetherTruckStockEntity) joinPoint.getArgs()[0];
        if (retVal1 != FossConstants.SUCCESS) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("保存合车失败，不进行推送 {} {}", new Object[]{o.getWaybillNO(), o.getSerialNO()});
            }
        }
        List<TogetherTruckStockEntity> list = new ArrayList<TogetherTruckStockEntity>(1);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.ADD, StockKind.TOGETHER, list);
        list.add(o);
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("保存合车 {} 推送mq完成，result {}", new Object[]{o, result});
    }

    @AfterReturning(returning = "retVal",
            pointcut = "execution(@com.deppon.foss.module.transfer.stock.server.message.annotaion.TogetherDelete * *(..))")
    public void afterTogetherDel(JoinPoint joinPoint, Object retVal) throws Throwable {
        if (notPushStockConfig()) return;
        LOGGER.info("----------------aftertogetherDelete--" + joinPoint.getSignature().getName());
        Integer retVal1 = (Integer) retVal;
        String waybill = (String) joinPoint.getArgs()[0];
        String serialNo = (String) joinPoint.getArgs()[1];
        String org = (String) joinPoint.getArgs()[2];
        if (retVal1 != FossConstants.SUCCESS) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("del合车失败，不进行推送 {} {} orgcode {}", new Object[]{waybill, serialNo, org});
            }
        }
        InOutStockEntity inOutStockEntity = new InOutStockEntity();
        inOutStockEntity.setOrgCode(org);
        inOutStockEntity.setWaybillNO(waybill);
        inOutStockEntity.setSerialNO(serialNo);
        List<InOutStockEntity> list = new ArrayList<InOutStockEntity>(1);
        ToPFDStockActionEntity entity = new ToPFDStockActionEntity(ActionType.ADD, StockKind.TOGETHER, list);
        list.add(inOutStockEntity);
        SendResult result = MQtemplate.send(FOSS_PUSH_STOCK_CODE, entity);
        LOGGER.info("del合车 {} {} {} 推送mq完成，result {}", new Object[]{waybill, serialNo, org, result});
    }

}
