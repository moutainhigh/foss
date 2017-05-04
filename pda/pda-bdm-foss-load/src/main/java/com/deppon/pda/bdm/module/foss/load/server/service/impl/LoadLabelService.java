package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaVehicleSealService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadPdaDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadLabelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SealOrigDetail;
import com.deppon.pda.bdm.module.foss.load.shared.exception.UserInfoNotWholeException;
/**
 * 装车录入封签
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class LoadLabelService  implements IBusinessService<Void, LoadLabelScanEntity>{

	
	private ILoadPdaDao loadPdaDao;

	public void setLoadPdaDao(ILoadPdaDao loadPdaDao) {
		this.loadPdaDao = loadPdaDao;
	}
	
	private UserCache userCache;
	
	private DeptCache deptCache;
	private IPdaVehicleSealService pdaVehicleSealService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:17:57
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public LoadLabelScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		LoadLabelScanEntity entity = JsonUtil.parseJsonToObject(LoadLabelScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:18:03
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, LoadLabelScanEntity param)
			throws PdaBusiException {
		log.info("录入封签开始");
		this.validate(param);
		
		List<SealOrigDetailEntity> sealOrigDetails = new ArrayList<SealOrigDetailEntity>();
    	if( param.getSealOrigDetails()!=null){
    	    SealOrigDetailEntity sealDE = null;         
            for(SealOrigDetail so : param.getSealOrigDetails()){
                sealDE = new SealOrigDetailEntity();
                sealDE.setSealNo(so.getSealNo());
                sealDE.setBindType(so.getBindType());
                sealDE.setSealType(so.getSealType());
                sealOrigDetails.add(sealDE);
            }
    	}
		
		
		
		
		//获取当前员工的营业部或者外场编码
		String currentDept = this.getTaskDept(asyncMsg.getUserCode(),param.getDeptType());
		//Argument.hasText(currentDept, "currentDept");
		//List<String> arrDeptCode = new ArrayList<String>();
		//for (String s : param.getArrDeptCode()) {
			//String str = CodeParseUtil.getDeptCode(s);
			//Argument.hasText(str, "CreateLoadTask.arrDeptCode");
			//arrDeptCode.add(str);
		//}
		//param.setArrDeptCode(arrDeptCode);
		//保存封签对应关系
		try {
		/*	pdaVehicleSealService.insertSealOrig(param.getTruckCode(),
					asyncMsg.getDeptCode(), param.getBackSealNos(), param.getSideSealNos(), asyncMsg.getUserCode(),
					asyncMsg.getPdaCode(),currentDept,param.getArrDeptCode().get(0));
	     */
		    
		    pdaVehicleSealService.insertSealOrig (param.getTruckCode(),
                     asyncMsg.getDeptCode(),
		             sealOrigDetails,
		             asyncMsg.getUserCode(),
                     asyncMsg.getPdaCode(),
                     currentDept,
                     param.getArrDeptCode().get(0),
                     sealOrigDetails.size()==0?null:sealOrigDetails.get(0).getBindType());
		
		
		
		
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.info("录入封签成功");
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:18:36
	 * @param loadLabelScanEntity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(LoadLabelScanEntity loadLabelScanEntity) throws ArgumentInvalidException  {
		Argument.notNull(loadLabelScanEntity, "loadLabelScanEntity");
		//标签号非空
		//Argument.hasText(loadLabelScanEntity.getLabelCode(), "loadLabelScanEntity.labelCode");
		//车牌号非空
		Argument.hasText(loadLabelScanEntity.getTruckCode(), "loadLabelScanEntity.truckCode");
		Argument.notEmpty(loadLabelScanEntity.getArrDeptCode(), "loadLabelScanEntity.ArrDeptCode");
		//任务号非空
		//Argument.hasText(loadLabelScanEntity.getTaskCode(), "loadLabelScanEntity.taskCode");
		//扫描时间非空
		//Argument.notNull(loadLabelScanEntity.getScanTime(), "loadLabelScanEntity.scanTime");
		//Argument.notEmpty(loadLabelScanEntity.getBackSealNos(), "loadLabelScanEntity.BackSealNos");
		//Argument.notEmpty(loadLabelScanEntity.getSideSealNos(), "loadLabelScanEntity.SideSealNos");
	}
	/**
	 * 
	 * <p>TODO(从缓存获取登录员工的大部门)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:18:48
	 * @param userCode
	 * @return
	 * @see
	 */
	/*private String getTaskDept(String userCode) {
		UserEntity userEntity = userCache.getUser(userCode);
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		if(deptEntity == null){
			throw new UserInfoNotWholeException();
		}
		String deptId = userEntity.getDeptId();
		DeptEntity bigDept = deptCache.get(deptId);
		if(bigDept != null){
			boolean isBigDept = 
					bigDept.getIsOutStorage().equals("1") || bigDept.getIsOutStorage().equals("0");
			// 查找大部门
			while(!isBigDept){
				bigDept = deptCache.get(deptId);
				if(bigDept.getIsOutStorage().equals("1") || bigDept.getIsOutStorage().equals("0")){
					isBigDept = true;
				}
				deptId = bigDept.getParentOrgCode();
			}
		}
		return bigDept.getDeptCode();
	}*/
	private String getTaskDept(String userCode) {
		UserEntity userEntity = userCache.getUser(userCode);
		DeptEntity dept = deptCache.getDept(userEntity.getDeptId());
				
		
		if (dept == null) {
			throw new UserInfoNotWholeException();
		}
		// 查找大部门
		while (true) {
			if (dept == null) {
				return null;
			}
			if (dept.getDeptAttribute().equals("1")
					|| dept.getDeptAttribute().equals("0")) {
				return dept.getDeptCode();
			}
			if (dept.getParentOrgCode() == null
					|| dept.getParentOrgCode().isEmpty()
					|| dept.getId().equals(dept.getParentOrgCode())) {
				return null;
			}
			dept = deptCache.get(dept.getParentOrgCode());
		}
	}
	
	private String getTaskDept(String userCode,String deptType) {
		UserEntity userEntity = userCache.getUser(userCode);
		DeptEntity dept = deptCache.getDept(userEntity.getDeptId());
			
		//营业部分部装车
		if(deptType!=null && "5".equals(deptType)){
			String expressBranchCode = loadPdaDao.queryExpressBranch(userCode);
			if(expressBranchCode==null || "".equals(expressBranchCode)){
				throw new FossInterfaceException(null,"该营业部找不到对应分部");
			}
			return expressBranchCode;
		}	
		
		if (dept == null) {
			throw new UserInfoNotWholeException();
		}
		// 查找大部门
		while (true) {
			if (dept == null) {
				return null;
			}
			if (dept.getDeptAttribute().equals("1")
					|| dept.getDeptAttribute().equals("0")) {
				return dept.getDeptCode();
			}
			if (dept.getParentOrgCode() == null
					|| dept.getParentOrgCode().isEmpty()
					|| dept.getId().equals(dept.getParentOrgCode())) {
				return null;
			}
			dept = deptCache.get(dept.getParentOrgCode());
		}
	}
	
	
	
	
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return LoadConstant.OPER_TYPE_LOAD_SEALS_ADD.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setPdaVehicleSealService(
			IPdaVehicleSealService pdaVehicleSealService) {
		this.pdaVehicleSealService = pdaVehicleSealService;
	}
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}
	
}
