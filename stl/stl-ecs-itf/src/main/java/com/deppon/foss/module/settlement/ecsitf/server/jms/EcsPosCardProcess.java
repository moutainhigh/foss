package com.deppon.foss.module.settlement.ecsitf.server.jms;

import com.deppon.ecs.inteface.domain.SynPosCardDetailEntity;
import com.deppon.ecs.inteface.domain.SynPosCardRequest;
import com.deppon.ecs.inteface.domain.SynPosCardResponse;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yaq on 2016/5/3.
 */
public class EcsPosCardProcess implements IProcess {
    /**
     * 待刷卡service
     */
    private IWSCManageService wscManageService;
    private ILogEcsFossService logEcsFossService;
    /**
     * 待刷卡logger
     */
    private static final Logger LOGGER = LogManager.getLogger(EcsPosCardProcess.class);

    /**
     * 生成T+0，一系列操作
     * @author 309603 yangqiang
     * @date 2016-5-04 上午9:38:16
     */
    @SuppressWarnings("finally")
    @Override
    public Object process(Object obj) throws ESBBusinessException {
    	Date date = new Date();
        // 接口返回信息
        SynPosCardResponse response = new SynPosCardResponse();
        response.setIsSuccess("Y");
        LOGGER.info("开始待刷卡操作。");
        // 获取接口参数
        SynPosCardRequest param = (SynPosCardRequest) obj;
        //响应状态1代表成功，0代表失败
        Boolean flag = null;
        try {
            if (null == param.getTradeSerialNo()) {
                String str = "传入交易流水为空！";
                throw new SettlementException(str);
            }
            PosCardEntity posCardEntity = new PosCardEntity();
            BeanUtils.copyProperties(param, posCardEntity);
            List<PosCardDetailEntity> list = new ArrayList<PosCardDetailEntity>();
            posCardEntity.setPosCardDetailEntitys(list);
            for (SynPosCardDetailEntity entity : param.getPosCardDetailEntitys()) {
                PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
                BeanUtils.copyProperties(entity, posCardDetailEntity);
                list.add(posCardDetailEntity);
            }
            wscManageService.addPosCardAndDetail(posCardEntity);
            flag = true;
        } catch (BusinessException e) {
            String str = e.getErrorCode();
            this.errMsg(response, str);
            flag = false;
        } catch (Exception e) {
            String str = e.getMessage();
            this.errMsg(response,"未知异常"+ str);
            flag = false;
        }finally {
            //添加日志
            logEcsFossService.setLog(param, response, "FOSS_ESB2FOSS_ECS_FOSS_SEND_POSCARD", StringUtils.isBlank(param.getTradeSerialNo())?"传入交易流水为空！":param.getTradeSerialNo(), flag, date);
            LOGGER.info("待刷卡操作结束。");
            return response;
        }
    }

    private void errMsg(SynPosCardResponse response, String str) {
        LOGGER.info(str);
        response.setIsSuccess("N");
        response.setMessage(str);
    }

    /**
     * 错误处理
     *
     * @author 309603-foss-yangqiang
     * @date 2016-6-14 下午2:30:01
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

    public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
        this.logEcsFossService = logEcsFossService;
    }
}
