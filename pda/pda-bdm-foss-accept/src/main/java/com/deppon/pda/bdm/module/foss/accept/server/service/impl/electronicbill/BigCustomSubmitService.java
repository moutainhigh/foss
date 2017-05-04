package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;


import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.BigCustomSubmitEntity;

/**   
 * @ClassName BigCustomSubmitService  
 * @Description 大客户提交   
 * @author  092038 张贞献  
 * @date 2014-8-24    
 */ 
public class BigCustomSubmitService implements IBusinessService<Void, BigCustomSubmitEntity> {
	
	//private Logger log = Logger.getLogger(getClass());
	
	    private IAcctDao acctDao;

	    public void setAcctDao(IAcctDao acctDao) {
	        this.acctDao = acctDao;
	    }
	
	/**
	 * 
	 * @description 解析json字符串
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public BigCustomSubmitEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		BigCustomSubmitEntity acctFinish = JsonUtil.parseJsonToObject(BigCustomSubmitEntity.class,asyncMsg.getContent());
	
		return acctFinish;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param acctFinish
	 * @throws PdaBusiException 
	 * @created 2012-12-28 下午9:08:35
	 */
	private void validate(AsyncMsg asyncMsg, BigCustomSubmitEntity acctFinish) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		// 判断完成接货实体
		Argument.notNull(acctFinish, "BigCustomSubmitEntity");
		
		//验证大客户编码
		Argument.hasText(acctFinish.getCustomCode(), "BigCustomSubmitEntity.customCode");
		}

	/**
	 * 
	 * @description 完成接货服务
	 * @param asyncMsg
	 * @param acctFinish
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, BigCustomSubmitEntity acctFinish) throws PdaBusiException {
		// 校验数据
		this.validate(asyncMsg, acctFinish);
	
	
		

		if(acctDao.queryNoSyncScanMsgCount(asyncMsg.getUserCode(),asyncMsg.getPdaCode(),acctFinish.getCustomCode())){
			throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
		}
		
		
		return null;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_EWAYBILLSUBMIT.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}


}
