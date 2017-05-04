package com.deppon.foss.module.settlement.vtsitf.server.esb;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.VtsPayableFinanceBillRequest;
import com.deppon.esb.inteface.domain.vtsbill.VtsPayableFinanceBillResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IAddVtsBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsStlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author 310970  caopeng
 * @date 2016-5-15
 * VTS打印运输合同将流水推送至FOSS
 * FOSS将流水号信息获取,生成首位款
 */
public class VtsPayableFinanceBillProcess implements IProcess {
    private static final Logger logger = LogManager.getLogger(VtsPayableFinanceBillProcess.class);

    IAddVtsBillPayableService addVtsBillPayableService;

    /**
     * 注入接口日志service
     */
    private IEsbInterfaceLogService esbInterfaceLogService;


    @Override
    public Object process(Object req) throws ESBBusinessException {
        //记录日志
        logger.info("**********FOSS结算生成首尾款开始**********");
        //获取请求的尸体
        VtsPayableFinanceBillRequest vtsRequest = (VtsPayableFinanceBillRequest) req;
        //获取返回的实体
        VtsPayableFinanceBillResponse vtsResponse = new VtsPayableFinanceBillResponse();

        //非空判断
        if (vtsRequest == null || StringUtils.isEmpty(vtsRequest.getWaybillNo()) || vtsRequest.getWaybillNo() == null) {
            logger.info("VTS传入参数为空.....");
            vtsResponse.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);
            vtsResponse.setReason("VTS请求参数为空，调用接口失败,请检查参数");
        }
        InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
        String businessId = vtsRequest.getBussinessId();
        String waybillNo = vtsRequest.getWaybillNo();
        try {
            /**
             * 封装数据
             * 将VTS传过来的数据 进行封装
             */
            //单号

            VtsStlVehicleAssembleBillDto dto = new VtsStlVehicleAssembleBillDto();
            dto.setWaybillNo(vtsRequest.getWaybillNo());
            dto.setPaymentType(vtsRequest.getPaymentType());
            dto.setFeeTotal(vtsRequest.getFeeTotal());
            dto.setPrePaidFeeTotal(vtsRequest.getPrePaidFeeTotal());
            dto.setOrigOrgCode(vtsRequest.getOrigOrgCode());
            dto.setOrigOrgName(vtsRequest.getOrigOrgName());
            dto.setDestOrgCode(vtsRequest.getDestOrgCode());
            dto.setDestOrgName(vtsRequest.getDestOrgName());
            dto.setCurrencyCode(vtsRequest.getCurrencyCode());
            dto.setVehicleNo(vtsRequest.getVehicleNo());
            dto.setArriveFeeTotal(vtsRequest.getArriveFeeTotal());
            dto.setSourceBillType(vtsRequest.getSourceBillType());
            dto.setCustomerCode(vtsRequest.getCustomerCode());
            dto.setCustomerName(vtsRequest.getCustomerName());
            dto.setSourceBillNo(vtsRequest.getSourceBillNo());
            //20160811 dto封装businessId
            dto.setBusinessId(vtsRequest.getBussinessId());
            if (StringUtils.isNotEmpty(vtsRequest.getLgDriverCode())) {
                dto.setLgdriverCode(vtsRequest.getLgDriverCode());
            }
            if (StringUtils.isNotEmpty(vtsRequest.getLgDriverName())) {
                dto.setLgdriverName(vtsRequest.getLgDriverName());
            }
            // 379106 传递保理
            if (null == vtsRequest.getFactoring() || StringUtils.isEmpty(vtsRequest.getFactoring())) {
                throw new SettlementException("传入Factoring参数为空,不能进行新增操作！");
            }
            dto.setFactoring(vtsRequest.getFactoring());
            if (vtsRequest.getFactoring().equals("Y")) {
                dto.setFactorBeginTime(vtsRequest.getFactorBeginTime());
                dto.setFactorEndTime(vtsRequest.getFactorEndTime());
                dto.setFactorAccount(vtsRequest.getFactorAccount());
                dto.setCusCode(vtsRequest.getCusCode());
            }
            /**
             * CurrentInfo currentInfo 信息实体转换
             */
            //1.新建UserEntiy实体
            UserEntity user = new UserEntity();
            user.setEmpCode(vtsRequest.getEmpCode());
            user.setEmpName(vtsRequest.getEmpName());
            //2.新建OrgAdministrativeInfoEntity实体
            OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
            dept.setCode(vtsRequest.getCurrentDeptCode());
            dept.setName(vtsRequest.getCurrentDeptName());
            //3.对CurrentInfo执行构造函数，将值设置到CurrentInfo中
            CurrentInfo currentInfo = new CurrentInfo(user, dept);
            //调用foss新增内部service业务逻辑的处理
            addVtsBillPayableService.addvtsBillPayable(dto, currentInfo);

            /**
             * 定义插入log日志插入结算VTS专门的日志表
             * stl.t_stl_bill_interfacelog
             */
            interfaceEntity.setId(UUIDUtils.getUUID());
            interfaceEntity.setWaybillNo(waybillNo);
            interfaceEntity.setEsbCode("FOSS_ESB2FOSS_SYNC_VTSFOSS_VTSPAYABLE");//服务端编码:打印运输合同
            interfaceEntity.setSystemType("VTS");
            interfaceEntity.setSendContent(JSONObject.toJSONString(vtsRequest));
            interfaceEntity.setCreateUser("VTS打印运输合同");
            interfaceEntity.setModifyUser("VTS打印运输合同");
            interfaceEntity.setCreateTime(new Date());

            vtsResponse.setBussinessId(businessId);
            vtsResponse.setWaybillNo(waybillNo);
            vtsResponse.setResult(1);
            logger.info("请求成功");
        } catch (SettlementException se) {
            logger.error("\n运单号:" + waybillNo + "VTS生成FOSS结算财务单据错误," + se.getErrorCode(), se);
            vtsResponse.setResult(2);//处理失败标志
            vtsResponse.setBussinessId(businessId);
            vtsResponse.setWaybillNo(waybillNo);
            vtsResponse.setReason("运单号是" + waybillNo + "生成首位款失败," + se.getErrorCode());

            interfaceEntity.setIsSuccess("N");
            StringWriter writer = new StringWriter();
            se.printStackTrace(new PrintWriter(writer));
            interfaceEntity.setErrorLog("VTS打印运输合同生成整车首尾款应付单报错，错误信息是：" + writer.toString());
        } catch (BusinessException ex) {
            logger.error("\n运单号:" + waybillNo + ",VTS生成FOSS结算财务单据业务逻辑错误," + ex.getErrorCode(), ex);
            vtsResponse.setBussinessId(businessId);
            vtsResponse.setResult(2);//处理失败标志：1是成功；2是失败
            vtsResponse.setWaybillNo(waybillNo);
            vtsResponse.setReason("运单号:" + waybillNo + ",VTS生成FOSS结算财务单据业务逻辑错误," + ex.getErrorCode());//失败原因

            interfaceEntity.setIsSuccess("N");
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            interfaceEntity.setErrorLog("VTS打印运输合同生成整车首尾款应付单报错，错误信息是：" + writer.toString());
        } catch (Exception e) {
            logger.error("\n运单号:" + waybillNo + "VTS生成FOSS结算财务单据未知异常(在Excepiton层报错)," + e.getMessage(), e);
            vtsResponse.setBussinessId(businessId);
            vtsResponse.setResult(2);//处理失败标志：1是成功；2是失败
            vtsResponse.setWaybillNo(waybillNo);
            vtsResponse.setReason("运单号:" + waybillNo + "VTS生成FOSS结算财务单据未知异常(在Excepiton层报错)," + e.getMessage());//失败原因

            interfaceEntity.setIsSuccess("N");
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            interfaceEntity.setErrorLog("VTS打印运输合同生成整车首尾款应付单报错，错误信息是：" + writer.toString());
            //记录失败日志
            logger.info("VTS开单生成财务单据处理失败原因是：" + vtsResponse.getReason());
        } finally {
            //插入接口日志表
            this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
        }
        //记录日志
        logger.info("FOSS接口生成应付单结束....响应结果。成功标志是：" + vtsResponse.getResult() + "，失败原因是：" + vtsResponse.getReason());
        //记录日志
        logger.info("FOSS接口生成应付单结束....");

        return vtsResponse;
    }

    @Override
    public Object errorHandler(Object arg0) throws ESBBusinessException {
        return null;
    }

    public void setAddVtsBillPayableService(
            IAddVtsBillPayableService addVtsBillPayableService) {
        this.addVtsBillPayableService = addVtsBillPayableService;
    }

    public void setEsbInterfaceLogService(
            IEsbInterfaceLogService esbInterfaceLogService) {
        this.esbInterfaceLogService = esbInterfaceLogService;
    }

}
