package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdBackGoodsReportEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryKdBackGoodsReportEntity;

/**
 * 
 * TODO(快递返货工单上报)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:245960,date:2015-8-19 下午2:10:30,content:TODO </p>
 * @author 245960
 * @date 2015-8-19 下午2:10:30
 * @since
 * @version
 * @discription 2015-09-08日常版本需求,pda前端对接人：袁苗苗,foss对接人：刘金伟
 */
public class KdBackGoodsReportService implements IBusinessService<KdBackGoodsReportEntity, QryKdBackGoodsReportEntity> {

	//注册log
    private Logger log = Logger.getLogger(getClass());

    // foss接口
    private IPdaWaybillService pdaWaybillService;

    /**
	 * @param pdaWaybillService the pdaWaybillService to set
	 */
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}
    
    /**
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public QryKdBackGoodsReportEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        //解析内容
    	QryKdBackGoodsReportEntity qryKdBackGoodsReportEntity = JsonUtil.parseJsonToObject(QryKdBackGoodsReportEntity.class, asyncMsg.getContent());
    	qryKdBackGoodsReportEntity.setUserCode(asyncMsg.getUserCode());
    	qryKdBackGoodsReportEntity.setDeptCode(asyncMsg.getDeptCode());
    	
        return qryKdBackGoodsReportEntity;
    }
    
    /**
     * @description 校验数据合法性
     * @param qryActOrder
     * @throws PdaBusiException 
     * @created 2012-12-26 下午9:25:05
     */
    private void validate(AsyncMsg asyncMsg, QryKdBackGoodsReportEntity qryKdBackGoodsReportEntity) throws PdaBusiException {
        // pdaInfo信息校验
        Argument.notNull(asyncMsg, "AsyncMsg");
    }

    /**
     * @description 服务方法
     * @param asyncMsg
     * @param qryActOrder
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
     */
    @Transactional
    @Override
    public KdBackGoodsReportEntity service(AsyncMsg asyncMsg, QryKdBackGoodsReportEntity qryKdBackGoodsReportEntity) throws PdaBusiException {
        //校验参数
    	this.validate(asyncMsg, qryKdBackGoodsReportEntity);
        
        log.debug("---调用FOSS  快递返货上报     单接口开始---参数：" + asyncMsg.getUserCode() + asyncMsg.getPdaCode() + asyncMsg.getContent());
        
        //-------------------------传参封装begin--------------------------------------------
        WaybillEntity bean = new WaybillEntity();
        //运单号
        bean.setWaybillNo(qryKdBackGoodsReportEntity.getWblCode());
        //返货原因
        bean.setReturnReason(qryKdBackGoodsReportEntity.getReturnReason());
        //返货类型
        bean.setReturnType(qryKdBackGoodsReportEntity.getReturnType());
        //发货人联系方式 前台传过来的可能是座机，可能是手机
        bean.setDeliveryCustomerPhone(qryKdBackGoodsReportEntity.getTelephone());
        //发货人联系方式 前台传过来的可能是座机，可能是手机
        bean.setDeliveryCustomerMobilephone(qryKdBackGoodsReportEntity.getTelephone());
        //申请事由
        bean.setApplicationReason(qryKdBackGoodsReportEntity.getApplicationReason());
        //工号
        bean.setUserCode(qryKdBackGoodsReportEntity.getUserCode());
        //员工姓名
        bean.setUserName(qryKdBackGoodsReportEntity.getUserName());
        //部门编号
        bean.setDeptCode(qryKdBackGoodsReportEntity.getDeptCode());
        //返货方式
        bean.setReturnMode(qryKdBackGoodsReportEntity.getReturnWay());      
        //返货原因
        bean.setReturnReason(qryKdBackGoodsReportEntity.getReturnReason());
        //原因明细
        bean.setReturnDetail(qryKdBackGoodsReportEntity.getReturnDetail());
        //-------------------------传参封装end--------------------------------------------
        
        boolean result;
        try {
        	result = pdaWaybillService.createReturneDworkOrder(bean);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch (Exception e) {
			throw new PdaBusiException(e);
		}
        
        //返回参数给前台
        KdBackGoodsReportEntity kdBackGoodsReportEntity = new KdBackGoodsReportEntity();
        kdBackGoodsReportEntity.setResponseFalg(result);
        
        log.debug("---调用FOSS  快递返货上报     单接口结束---参数：" + asyncMsg.getUserCode() + asyncMsg.getPdaCode() + asyncMsg.getContent());
        
        return kdBackGoodsReportEntity;
    }

    /**
     * 业务类型
     */
    @Override
    public String getOperType() {	
        return AcceptConstant.OPER_TYPE_ACCT_KD_BACKGOODS_REPORT.VERSION;
    }

    /**
     * 同步还是异步
     * false:同步
     * true:异步
     */
    @Override
    public boolean isAsync() {
        return false;
    }
    
}
