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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdInsuredEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdOneManyLimitEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdPartSalesDeptEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.NewPartSalesDeptEntity;

public class NewKdPartSalesDeptService implements IBusinessService<NewPartSalesDeptEntity, ScanMsgEntity>{

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
	public NewPartSalesDeptEntity service(AsyncMsg asyncMsg, ScanMsgEntity param)
			throws PdaBusiException {
		
		NewPartSalesDeptEntity sd = new NewPartSalesDeptEntity();
		// 点部映射营业部
		List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys= acctPdaDao.queryKdPartSalesDeptEntitys(asyncMsg.getUserCode());		
		//出发部门是否能开一票多件
		String  isOneMany = acctPdaDao.queryDeptEntityIsOneMany(asyncMsg.getUserCode());
		
		String expLimitWeight = acctPdaDao.queryExpLimitWeight();
		
		//author:245960 Date:2015-08-22 comment:骆敏霞需求获取  快递保价申明价值上限  1104   快递外发保价申明价值 1105
		List<KdInsuredEntity> listKdInsuredEntity = acctPdaDao.queryKdInsuredEntity();
		
		//一票多件重量、体积、件数限制
		List<KdOneManyLimitEntity> entity = acctPdaDao.queryOneManyLimit();	
		sd.setKdOneManyLimitEntity(entity);
		sd.setListKdInsuredEntity(listKdInsuredEntity);
		
		sd.setKdPartSalesDeptEntitys(kdPartSalesDeptEntitys);
		sd.setIsOneMany(isOneMany);
		sd.setLimitWeight(expLimitWeight);
		
		
		return sd;
	}

    /**
     * 业务类型
     */
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return AcceptConstant.OPER_TYPE_ACCT_KD_NEWPARTSALESDEPT_ACTIVITY.VERSION;
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
