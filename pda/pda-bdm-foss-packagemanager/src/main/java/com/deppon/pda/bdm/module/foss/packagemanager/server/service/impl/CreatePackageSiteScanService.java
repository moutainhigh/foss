package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.CreatePackageSiteEntity;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.CreatePackageSiteScanResult;

/**
 * 建包增加扫描生成目的站
 * @author 245955
 * @date 创建时间 2015-7-23 上午10:59:23
 * @version 1.0 * @parameter
 * @since  * @return
 */
public class CreatePackageSiteScanService implements IBusinessService<CreatePackageSiteScanResult,CreatePackageSiteEntity>{
	private IPDATransferLoadService pdaTransferLoadService;
	private static final Log LOG = LogFactory.getLog(CreatePackageSiteScanService.class);
	
	@Override
	public CreatePackageSiteEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		CreatePackageSiteEntity entity=JsonUtil.parseJsonToObject(CreatePackageSiteEntity.class,asyncMsg.getContent());
		//entity.setDeptCode(asyncMsg.getDeptCode());
		return entity;
	}

	@Override
	public CreatePackageSiteScanResult service(AsyncMsg asyncMsg,
			CreatePackageSiteEntity param) throws PdaBusiException {
		this.validate(param);
		CreatePackageSiteScanResult result=null;
		PackagePathLoaderDto res=new PackagePathLoaderDto();
		long start = System.currentTimeMillis();
		try {
			res=pdaTransferLoadService.unlockPackagePathDetail(param.getWayBillNo(), param.getDeptCode());
			LOG.info("调用FOSS接口所需时间："+(System.currentTimeMillis()-start));
			start = System.currentTimeMillis();
			//返回实体
			if(res!=null){
				result=new CreatePackageSiteScanResult();
				result.setDeptCode(res.getObjectiveOrgCode());
				result.setDeptName(res.getObjectiveOrgName());
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_SITE_CREATE.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	/**
	 * 非空验证
	 * @param createPackageSiteEntity
	 * @throws ArgumentInvalidException
	 */
	private void validate(CreatePackageSiteEntity createPackageSiteEntity)
			throws ArgumentInvalidException {
		Argument.notNull(createPackageSiteEntity, "createPackageSiteEntity");
		//运单号
		Argument.hasText(createPackageSiteEntity.getWayBillNo(),"createPackageSiteEntity.wayBillNo");
	}

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
	
}
