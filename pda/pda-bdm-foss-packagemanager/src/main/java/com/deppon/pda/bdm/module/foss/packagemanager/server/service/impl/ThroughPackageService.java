package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackagePdaDao;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.ThroughPackage;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.ThroughPackageOrg;


/**  
 * 作者：肖龙雾
 * 描述：TODO 
 * 包名：com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl
 * 时间：2014-11-18 下午3:14:23
 */
public class ThroughPackageService implements IBusinessService<List<ThroughPackageOrg>, ThroughPackage> {
	private IPDAExpressPackageService pdaExpressPackageService;
	
	private IPackagePdaDao packagePdaDao;
	@Override
	public ThroughPackage parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ThroughPackage model = JsonUtil.parseJsonToObject(ThroughPackage.class,
				asyncMsg.getContent());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO(根据运单号下拉所有的到达部门，返回当前部门以后的所有部门给PDA并按路由号排序)</p> 
	 * @author mt
	 * @date 2013年7月30日9:59:14
	 * @param asyncMsg 请求消息
	 * @param param 直达包信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public List<ThroughPackageOrg> service(AsyncMsg asyncMsg, ThroughPackage param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		List<String> orgCodes = new ArrayList<String>();
		try{
			//调用FOSS接口 返回当前部门以后的所有部门给PDA并按路由号排序
			orgCodes = pdaExpressPackageService.obtainThroughPackArriveOrgCode(param.getPackageNo(),
					param.getWaybillNo(), param.getSerialNo(), asyncMsg.getDeptCode(),param.getPackageType());
		}
		catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		Map<String,String> map = new HashMap<String,String>();
		if(orgCodes != null && orgCodes.size() != 0){
			//根据FOSS返回的所有部门编码，查找所对应的部门名称,并把编码和编码对应的名称保存到list
			List<ThroughPackageOrg> orgs= packagePdaDao.findThroughPackArriveOrgByOrgCode(orgCodes);
			//将编码和名称保存到map集合
			for (String orgCode : orgCodes) {
				for (ThroughPackageOrg throughPackageOrg : orgs) {
					if(throughPackageOrg.getOrgCode().equals(orgCode)){
						map.put(orgCode, throughPackageOrg.getOrgName());//保存编码和名称
						break;
					}
				}
			}
		}
		List<ThroughPackageOrg> throughPackArriveOrgs = new ArrayList<ThroughPackageOrg>();
		//将部门名称和部门编码放入List<ThroughPackageOrg>，按照orgCodes顺序
		for (String orgCode : orgCodes) {
			ThroughPackageOrg throughPackageOrg = new ThroughPackageOrg();
			throughPackageOrg.setOrgCode(orgCode);//部门编码
			throughPackageOrg.setOrgName(map.get(orgCode));//从map中将部门编码对应的value保存到实体
			throughPackArriveOrgs.add(throughPackageOrg);//将实体放入list集合，返回给PDA
		}
		//将List集合放回给PDA，其中集合存放部门编码和部门名称，与FOSS返回的部门编码的顺序对应
		return throughPackArriveOrgs;
	}
	
	private void validate(ThroughPackage throughPackage)
			throws ArgumentInvalidException {
		Argument.notNull(throughPackage, "throughPackage");
		//包号
		Argument.hasText(throughPackage.getPackageNo(),"throughPackage.getPackageNo");
		//运单号
		Argument.hasText(throughPackage.getWaybillNo(),"throughPackage.getWaybillNo");
		//流水号
		Argument.hasText(throughPackage.getSerialNo(),"throughPackage.getSerialNo");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_ThroughPack.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}

	/**
	 * @param packagePdaDao the packagePdaDao to set
	 */
	public void setPackagePdaDao(IPackagePdaDao packagePdaDao) {
		this.packagePdaDao = packagePdaDao;
	}
	
}
