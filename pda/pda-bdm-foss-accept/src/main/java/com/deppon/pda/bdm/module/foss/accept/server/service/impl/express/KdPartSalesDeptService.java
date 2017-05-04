package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctPdaDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdPartSalesDeptEntity;

public class KdPartSalesDeptService implements IBusinessService<List<KdPartSalesDeptEntity>, ScanMsgEntity>{

    private IAcctPdaDao acctPdaDao;
    
    public void setAcctPdaDao(IAcctPdaDao acctPdaDao) {
        this.acctPdaDao = acctPdaDao;
    }
    /**
     * 
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */	
	@Override
	public ScanMsgEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// TODO Auto-generated method stub
		//解析内容
		ScanMsgEntity scanMsgEntity = JsonUtil.parseJsonToObject(ScanMsgEntity.class, asyncMsg.getContent());
		return scanMsgEntity;
	}
    /**
     * 
     * @description 服务方法
     * @param asyncMsg
     * @param expBillingScan
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
     */
	@Override
	@Transactional
	public List<KdPartSalesDeptEntity> service(AsyncMsg asyncMsg, ScanMsgEntity param)
			throws PdaBusiException {
		// TODO Auto-generated method stub
		List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys= acctPdaDao.queryKdPartSalesDeptEntitys(asyncMsg.getUserCode());
		
		return kdPartSalesDeptEntitys;
	}

    /**
     * 业务类型
     */
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return AcceptConstant.OPER_TYPE_ACCT_KD_PARTSALESDEPT_ACTIVITY.VERSION;
	}
	
	/**
     * 同步还是异步
     */
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

}
