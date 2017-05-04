package com.deppon.foss.module.settlement.vtsitf.server.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vtsbill.SendWscWayBillRequest;
import com.deppon.esb.inteface.domain.vtsbill.SendWscWayBillResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by yaq on 2016/5/3.
 */
public class WscWayBillProcess implements IProcess {
    /**
     * 待刷卡service
     */
    private IWSCManageService wscManageService;

    /**
     * 待刷卡logger
     */
    private static final Logger LOGGER = LogManager.getLogger(WscWayBillProcess.class);
    
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;

    /**
     * 待刷卡一系列操作包含
     * 10，运单开单
     * 20，运单更改
     *
     * @author 309603 yangqiang
     * @date 2016-5-04 上午9:38:16
     * @see com.deppon.esb.core.process.IProcess#process(Object)
     */
    @SuppressWarnings("finally")
	@Override
    public Object process(Object obj) throws ESBBusinessException {
    	// 接口返回信息
    	SendWscWayBillResponse response = new SendWscWayBillResponse();
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
		String interfaceWaybillNo = "";//设置接口用的运单号
    	try {
        LOGGER.info("开始待刷卡操作。");
        
        // 获取接口参数
        SendWscWayBillRequest oldParam = (SendWscWayBillRequest) obj;
        response.setResult(1);
        response.setBusinessid(oldParam.getBusinessid());
        response.setWaybillNo(oldParam.getWayBillNo());
        // 获取状态
        if (!StringUtil.isNotBlank(oldParam.getWayBillNo())) {
            throw new SettlementException("传入参数不正确");
        }
        interfaceWaybillNo = oldParam.getWayBillNo();
        WSCEntity newParam = new WSCEntity();
        BeanUtils.copyProperties(oldParam, newParam);
        double a = new Double(oldParam.getWayBillAmount().toString());
        newParam.setWayBillAmount(a);
        double b = new Double(oldParam.getWaitSwipeAmount().toString());
        newParam.setWaitSwipeAmount(b);
        
		/**
		 * 定义插入log日志插入结算VTS专门的日志表
		 * stl.t_stl_bill_interfacelog
		 */
		interfaceEntity.setId(UUIDUtils.getUUID());
		interfaceEntity.setWaybillNo(oldParam.getWayBillNo());
		interfaceEntity.setEsbCode("FOSS_ESB2FOSS_SYNC_VTSFOSS_CARDBILL");//服务端编码:开单更改单银行卡
		interfaceEntity.setSystemType("VTS");
		interfaceEntity.setSendContent(JSONObject.toJSONString(oldParam));
		interfaceEntity.setCreateUser("218392");
		interfaceEntity.setModifyUser("开单/更改单银行卡异步接口（待刷卡）");
		interfaceEntity.setCreateTime(new Date());
		interfaceEntity.setIsSuccess("Y");
		interfaceEntity.setCorrectLog("开单/更改单异步接口同步至foss成功!");
        wscManageService.changeWayBill(newParam);
        } catch (SettlementException e) {
            String str = e.getErrorCode();
            this.errMsg(response, str);
            
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setIsSuccess("N");
			interfaceEntity.setCorrectLog("开单/更改单银行卡异步接口（待刷卡）报错，报错信息是：" + writer.toString());
		} catch (BusinessException e) {
            String str = e.getErrorCode();
            this.errMsg(response, str);
            
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setIsSuccess("N");
			interfaceEntity.setCorrectLog("开单/更改单银行卡异步接口（待刷卡）报错，报错信息是：" + writer.toString());
        } catch (Exception e) {
            String str = e.getMessage();
            this.errMsg(response,"未知异常"+ str);
            
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setIsSuccess("N");
			interfaceEntity.setCorrectLog("开单/更改单银行卡异步接口（待刷卡）报错，报错信息是：" + writer.toString());
        }finally {
            LOGGER.info("待刷卡操作结束。");
			//插入接口日志表
			boolean flag = this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			if(flag){
				LOGGER.info("接口日志表新增成功！运单号：" + interfaceWaybillNo);
			}else{
				LOGGER.info("接口日志表新增失败！业务id：运单号：" + interfaceWaybillNo);
			}
            return response;
        }
    }

    private void errMsg(SendWscWayBillResponse response, String str) {
        LOGGER.info(str);
        response.setResult(2);
        response.setReason(str);
    }

    /**
     * 错误处理
     *
     * @author 309603-foss-yangqiang
     * @date 2012-12-14 下午2:30:01
     * @see com.deppon.esb.core.process.IProcess#errorHandler(Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
        // 错误日志
        LOGGER.error(req.getClass().getName() + "处理待刷卡单据出错！");
        return null;
    }

    public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}
    
}
