package com.deppon.foss.module.settlement.ptpitf.server.ws;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.UpdateWithholdStatusEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.ptpitf.server.inter.UpdateWithholdStatusService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import java.math.BigDecimal;
/**
 * 修改应收单扣款状态
 * 
 * @ClassName: UpdateWithholdStatusServiceImpl
  *@author 099995-foss-hemingyu
  * @date 2016-01-12 上午18:23:38
 */
public class UpdateWithholdStatusServiceImpl implements UpdateWithholdStatusService {
    /**
     * 日志属性
     */
    private static final Logger logger = LogManager
            .getLogger(UpdateWithholdStatusServiceImpl.class);

    @Context
    HttpServletResponse response;
    @Context
    HttpServletRequest request;
    /**
     * 注入Service
     * <p/>
     * 修改应收单扣款状态
     * 2016-01-12 上午18:23:38
     */
    private IBillReceivableService billReceivableService;

    /**
     * 修改应收单扣款状态
     *
     * @param  应收单实体
     * @author 099995-foss-hemingyu
     * @date 2016-01-12 上午18:23:38
     */
    @SuppressWarnings({ "checked", "finally" })
    @Override
    public String updateWithholdStatus(String obj) {
        //记录日志
        logger.info("开始接收合伙人计价平台发送的扣款状态.");
        //获取返回信息(改成此业务的类)
        UpdateWithholdStatusEntity db = new UpdateWithholdStatusEntity();
         {
            try {
                if(StringUtils.isEmpty(obj)) {
                    throw new SettlementException("合伙人计价平台发送的扣款状态结束,传入数据为空！");
                }
                // 获取接口的运单号信息
                JSONObject billReceivable = JSONObject.fromObject(obj);
                //BillReceivableEntity entity = (BillReceivableEntity) JSONObject.toBean(billReceivable, BillReceivableEntity.class);

                // 获取接口的运单号
                String wayBillNo = billReceivable.getString("wayBillNo");
                //获取接口的运单号的应收类型
                String billType = billReceivable.getString("billType");
                // 获取接口的运单号扣款状态
                String withholdStatus = billReceivable.getString("withholdStatus");
                
                logger.info("接受修改应收单扣款状态接口参数。运单号："+wayBillNo+"应收单单据类型："+billType+"扣款状态："+withholdStatus);
                
                db.setWayBillNo(wayBillNo);
                db.setBillType(billType);
                
                //根据运单号与应收类型找到需要更新的数据
                BillReceivableEntity entity = billReceivableService.selectByWayBillNoAndBillType(wayBillNo, billType);
                if(entity==null){
                    throw new SettlementException(String.format(
                            "不存在运单号为:%s的有效应收单，请检查数据是否正确", wayBillNo));
                }
                
                //应收单是否已结清货款
                //未核销金额
                BigDecimal unverifyAmount = entity.getUnverifyAmount()==null?BigDecimal.ZERO:entity.getUnverifyAmount();
                if(unverifyAmount.compareTo(BigDecimal.ZERO)==0){//已结清
                	logger.info("已结清货款，不能再更新扣款状态！");
                	throw new BusinessException("合伙人计价平台发送的扣款状态接口失败！已结清货款，不能再更新扣款状态！");
                }
                
                entity.setWithholdStatus(withholdStatus);
                billReceivableService.updateBillReceivableWithholdStatus(entity);
                db.setIsSuccess("true");
                db.setErrorMsg("推送成功");
            } catch (SettlementException e) {
            	db.setErrorType("2");//业务异常
                db.setIsSuccess("false");
                db.setErrorMsg(e.getErrorCode());
                logger.info("合伙人计价平台发送的扣款状态业务异常。" + e.getErrorCode());
            } catch (BusinessException be) {
            	db.setErrorType("1");//已结清货款异常
                db.setIsSuccess("false");
                db.setErrorMsg("已结清货款，不能再更新扣款状态！");
                logger.info("合伙人计价平台发送的扣款状态业务异常。" + "已结清货款，不能再更新扣款状态！");
            } catch (Exception e1) {
            	db.setErrorType("3");//系统异常
                db.setIsSuccess("false");
                db.setErrorMsg(e1.getMessage());
                logger.info("合伙人计价平台发送的扣款状态系统异常。" + e1.getMessage());
            } finally {
                response.addHeader("ESB-ResultCode", "1");
                logger.info("合伙人计价平台发送的扣款状态结束");
                return JSONObject.fromObject(db).toString();
            }
        }
    }

    public IBillReceivableService getBillReceivableService(){
        return billReceivableService;
    }
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
}
