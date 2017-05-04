package com.deppon.pda.bdm.module.foss.packaging.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackagingConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedSupplierEntity;



/**
 * @author 092038
 *  获取包装供应商
 */
public class WrapedSupplierService implements
		IBusinessService<List<WrapedSupplierEntity>, Void> {
	private IPDAPackagingService pdaPackagingService;
	private Logger log = Logger.getLogger(getClass());

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:31:09
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Transactional
	@Override
	public List<WrapedSupplierEntity> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		
		List<WrapedSupplierEntity> res = new ArrayList<WrapedSupplierEntity>();
		log.debug("获取包装供应商开始");
		List<PackagingSupplierEntity> unpackRes = null;
		try {
			// 调用FOSS接口		
			unpackRes = pdaPackagingService.queryPackagingSupplierListByEmpCode(asyncMsg.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		// 封装返回PDA信息实体
		WrapedSupplierEntity en = null;
		//authot:245960 Date 2015-06-15  comment:可能返回的是null,进行判空操作，是空的话返回空    modify reason:bamp监控包nullpointer异常。
		if(null == unpackRes){
			return null;
		}
		for(PackagingSupplierEntity se:unpackRes){
			 en = new WrapedSupplierEntity(
					 se.getPackagingSupplierCode(),
					 se.getPackagingSupplier());
			 res.add(en);
		}
		
		
		log.debug("获取包装供应商成功");
		return res;
	}

	@Override
	public String getOperType() {
		return PackagingConstant.OPER_TYPE_WRAP_SUPPLIER_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaPackagingService(IPDAPackagingService pdaPackagingService) {
		this.pdaPackagingService = pdaPackagingService;
	}
}
