package com.deppon.pda.bdm.module.foss.login.server.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;
import com.deppon.pda.bdm.module.core.server.cache.DataVerCache;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.PgmVerCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.cache.VehicleCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.BaseDataVerEntity;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.domain.VehicleEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.CodeParseUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginDao;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginPdaDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DepartAssemblyDeptEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DestDeptInfoEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginDeviceEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginRetInfo;
import com.deppon.pda.bdm.module.foss.login.shared.domain.UserInfoEntity;
import com.deppon.pda.bdm.module.foss.login.shared.exception.DepartAssemblyDeptException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.ExpressVehicleNotUserException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.SourceDeptException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.StationSalesDeptNotFoundException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.TopFleetNotFoundException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.UserInfoNotWholeException;

/**
 * 
 * 登录模块服务类
 * 
 * @author xujun
 * @version 1.0
 * @created 2012-9-7 下午02:08:04
 */

/** 
  * @ClassName PdaLoginService 
  * @Description 修改 新增退出时间
  * @author 092038 
  * @date 2014-1-10 上午11:10:06 
*/ 
public class PdaLoginService implements IBusinessService<PdaLoginRetInfo, PdaLoginInfo> {
	private static final Logger LOG = Logger.getLogger(PdaLoginService.class);
	
	private IPdaSigninLogoutService pdaSigninLogoutService;
	
	private UserCache userCache;
	
	private DeptCache deptCache;
	
	private VehicleCache vehicleCache;
	
	private IValidateService validateService;
	
	private DataVerCache dataVerCache;
	
	private PgmVerCache pgmVerCache;
	private ILoginDao loginDao;
	private ILoginPdaDao loginPdaDao;
	
	@Override
	public PdaLoginInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PdaLoginInfo pdaLoginInfo = JsonUtil.parseJsonToObject(PdaLoginInfo.class, asyncMsg.getContent());
		pdaLoginInfo.setDeptCode(asyncMsg.getDeptCode());
		pdaLoginInfo.setPdaCode(asyncMsg.getPdaCode());
		pdaLoginInfo.setPdaPgmVer(asyncMsg.getPgmVer());
		pdaLoginInfo.setUserCode(asyncMsg.getUserCode());
		return pdaLoginInfo;
	}

	@Transactional
	@Override
	public PdaLoginRetInfo service(AsyncMsg asyncMsg, PdaLoginInfo param)
			throws PdaBusiException {
		LOG.info("username:"+param.getUserCode()+"   password:"+param.getPassword());
		//参数验证
		this.validate(asyncMsg,param);
		//用户名密码验证
		validateService.checkPwd(param);
		PdaLoginRetInfo pdaLoginRetInfo = new PdaLoginRetInfo();
		UserEntity userEntity = userCache.getUser(param.getUserCode());
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		
		String userType = asyncMsg.getUserType();
		//如果前台传的是分拣员，把它赋值为理货员，目前后台分拣员和理货员是同一个处理逻辑
		if("SORTER".equals(userType)){
			userType=PdaSignStatusConstants.USER_TYPE_TALLYPERSON;
		}
		String outStorageCode = null;
		//如果是司机则查找顶级车队
		if(userType.equals(PdaSignStatusConstants.USER_TYPE_DRIVER)){
			String platoonCode = getTopFleetCodeByDeptId(deptEntity.getId());
			if(StringUtils.isEmpty(platoonCode)){
				throw new TopFleetNotFoundException();
			}
			outStorageCode = loginDao.getOutStorageCode(platoonCode);
			/**
			 * 司机登陆返回司机所在顶级车队对应外场的始发驻地营业部与始发驻地营业部的时效区域
			 * gaojia 2014-03-03
			 */
			//查询外场对应的始发驻地营业部
			DeptEntity sourceStaionSalesDept = loginDao.querySourceStaionSalesDept(outStorageCode);
			if(sourceStaionSalesDept==null){
				throw new StationSalesDeptNotFoundException();
			}
			//始发驻地营业部
			pdaLoginRetInfo.setSourceStationSaleDept(sourceStaionSalesDept.getDeptCode());
			//始发驻地营业部时效区域
			pdaLoginRetInfo.setStationSaleDeptEffRegion(this.queryEffRegion(sourceStaionSalesDept));
			
		}
		
		//快递员出发部门
		String courierSourceOrgCode = null;
		//如果是快递员，则要查找快递员对应营业部的配载外场
		if(userType.equals(PdaSignStatusConstants.USER_TYPE_COURIER)){
		  //根据工号查找营业部code
            VehicleEntity deptParentEntity = vehicleCache.getDept(param.getUserCode());
            if(deptParentEntity == null){
            	throw new ExpressVehicleNotUserException();
            }
            DepartAssemblyDeptEntity departEntity = loginDao.getDepartAssemblyDept(deptParentEntity.getOrgCode());
            if(departEntity == null){
            	throw new DepartAssemblyDeptException();
            }
            outStorageCode = departEntity.getAssemblydeptcode();
            courierSourceOrgCode = CodeParseUtil.getDeptCode(deptEntity.getId(), asyncMsg.getUserCode(), userType);
            if(StringUtils.isEmpty(courierSourceOrgCode)){
            	throw new SourceDeptException();
            }
            //根据快递出发部门编码查询是出发部门时效区
            DeptEntity expDeptEntity=null;
            expDeptEntity = loginDao.queryDeptByCode(courierSourceOrgCode);
            if(expDeptEntity!=null){
            	 pdaLoginRetInfo.setStationSaleDeptEffRegion(this.queryExpEffRegion(expDeptEntity));                       
            }
            
            
         
            
           
		}
		
		if(deptEntity == null){
			throw new UserInfoNotWholeException();
		}else{
			// 1 为驻地门，如果该部门为驻地部门，则将其是否外场字段设为 1
			if("Y".equals(deptEntity.getIsstation())){
				deptEntity.setIsOutStorage("1");
			}
		}
		// 调用FOSS 接口
		try {
			if(PdaSignStatusConstants.USER_TYPE_TALLYPERSON.equals(userType)){
				//理货员不调FOSS接口
			}else{
				PdaSigninDto signinDto = new PdaSigninDto();
				signinDto.setDeviceNo(asyncMsg.getPdaCode());
				signinDto.setDriverCode(asyncMsg.getUserCode());
				signinDto.setVehicleNo(param.getTruckCode());
				signinDto.setSignTime(new Date());
				signinDto.setUserType(userType);
				signinDto.setOrgCode(deptEntity.getDeptCode());
				//如果是快递员则需要设置车牌号,以及上级部门
				if(PdaSignStatusConstants.USER_TYPE_COURIER.equals(userType)){
					param.setTruckCode("德"+asyncMsg.getUserCode());
					//根据工号查找营业部code
					VehicleEntity deptParentEntity = vehicleCache.getDept(param.getUserCode());
					if(deptParentEntity == null){
						throw new UserInfoNotWholeException();
					}
					signinDto.setOrgCode(deptParentEntity.getOrgCode());
				}
				
				//调用FOSS接口,进行签到
				pdaSigninLogoutService.signIn(signinDto); 
			}
		} catch (BusinessException e) {
			//LOG.error("登陆异常:"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		//返回本部门下员工Code与员工名称
		//--
		List<UserInfoEntity> userInfoList = loginDao.getUserInfoByDeptId(deptEntity.getId());
		
		//返回用户实体以及用户所在部门实体
		pdaLoginRetInfo.setDepartureFieldCode(outStorageCode);
		pdaLoginRetInfo.setUsers(userInfoList);
		
		pdaLoginRetInfo.setUserEntity(userEntity);
		//快递员出发营业部
		pdaLoginRetInfo.setCourierSourceOrgCode(courierSourceOrgCode);
		//根据用户返回用户权限修改于 ：2013-03-20 修改登录接
		
		//List<PrivilegeEntity> userPrivileges=loginDao.getUserPrivilege(param.getUserCode());
		// 封装集合数据
		//pdaLoginRetInfo.setUserPrivilege(userPrivileges);
		
		// 获取大部门
//		String deptId = userEntity.getDeptId();
		//--
		DeptEntity bigDept = getTaskDept(deptEntity);
		if(bigDept!=null){
			bigDept.setPhoneAndFax("0");
			//设置部门性质与当前大部门相同
			//deptEntity.setDeptAttribute(bigDept.getDeptAttribute());
		}
		//--
		pdaLoginRetInfo.setBigDept(bigDept);
		pdaLoginRetInfo.setDeptEntity(deptEntity);
		// 基础数据 程序版本检测
		this.check(pdaLoginRetInfo,param,asyncMsg);
		//根据用户返回目的站 ：2013-06-4  gaojia
		if(PdaSignStatusConstants.USER_TYPE_TALLYPERSON.equals(userType)){
			if(bigDept==null||(!"1".equals(bigDept.getDeptAttribute())&&!"0".equals(bigDept.getDeptAttribute()))){
				//throw new FossInterfaceException(null,"该用户类型不对!");
			}
			pdaLoginRetInfo.setReqDataUpgrade(false);
			pdaLoginRetInfo.setDataVer("");
			//外场目的站
			if(bigDept!=null&&"1".equals(bigDept.getDeptAttribute())){
				List<DestDeptInfoEntity> longDestDepts = this.getLongDestDepts(deptEntity,bigDept);
				pdaLoginRetInfo.setLongDestDepts(longDestDepts);
				List<DestDeptInfoEntity> shortDestDepts = this.getShortDestDepts(deptEntity,bigDept);
				pdaLoginRetInfo.setShortDestDepts(shortDestDepts);
			}
			//营业部目的站
			if(bigDept!=null&&"0".equals(bigDept.getDeptAttribute())){
				
				String  expressBranchdept = loginDao.queryExpressBranch(asyncMsg.getUserCode());
			    //营业部映射的分部存在	（ "6" 类型是分部到外场的）			
				//分部=》点部
				List<DestDeptInfoEntity> shortDestDeptBranchs=null;
				if(expressBranchdept!=null && !"".equals(expressBranchdept)){
					//对应分部编码
				    DeptEntity dept =  loginDao.queryDeptByCode(expressBranchdept);
					//分部=》外场   （"5"分部到点部）
				    List<DestDeptInfoEntity> longDestDepts = this.getLongDestDepts(dept,dept);
					for(int i=0 ; i<longDestDepts.size();i++){
						longDestDepts.get(i).setDeptType("6");
					}
					pdaLoginRetInfo.setLongDestDepts(longDestDepts);
					//分部=》点部
					shortDestDeptBranchs = this.getShortDestDepts(dept,dept);
					for(int i=0 ; i<shortDestDeptBranchs.size();i++){
						shortDestDeptBranchs.get(i).setDeptType("5");
					}					
				}
				
				List<DestDeptInfoEntity> shortDestDepts = this.getSalesShortDestDepts(deptEntity,bigDept);				

				if(shortDestDeptBranchs!=null && shortDestDeptBranchs.size()>0){
					shortDestDepts.addAll(shortDestDeptBranchs);
				}
				
				pdaLoginRetInfo.setShortDestDepts(shortDestDepts);				
								
			}
		}
		//保存PDA设备登陆信息
		try{
			LOG.info("------------------保存登陆用户和登陆设备信息------------------");
			PdaLoginDeviceEntity entity=new PdaLoginDeviceEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setDeptCode(deptEntity.getDeptCode());
			entity.setDeptName(deptEntity.getDeptName());
			entity.setDvcCode(asyncMsg.getPdaCode());
			entity.setLoginDate(new Date());
			entity.setStatus("0");
			entity.setUserCode(userEntity.getEmpCode());
			entity.setUserName(userEntity.getUserName());
			//设置退出时间为当前系统时间+8个小时
			entity.setLoginOutDate(new Date(new Date().getTime()+8*60*60*1000));
			String saveMsg=this.saveDeviceLoginData(entity);
			LOG.info("------------------"+saveMsg+"---------------");
			LOG.info("------------------保存登陆用户和登陆设备信息---成功---------------");
		}catch(PdaBusiException e){
			LOG.info("------------------保存登陆用户和登陆设备信息---失败---------------");
		}
		//298403 新增需求  每次登陆跟更新PDA的sim卡序列号 更新失败不能影响正常登陆
		try {
			if (StringUtil.isNotEmpty(param.getPdaSimIMSI())) {
				LOG.info("------------------更新登陆用户设备SIM卡序列号------------------");
				loginDao.updatePdaSimCardCode(param);
				LOG.info("------------------更新登陆用户设备SIM卡序列号  成功------------------");
			} else {
				LOG.info("------------------未启动更新登陆用户设备SIM卡序列号  SIMCARDCODE为空------------------");
			}

		} catch (Exception e) {
			LOG.info("------------------更新登陆用户设备SIM卡序列号  失败------------------");
			LOG.error("updatePdaSimCardCode error" + e.getMessage());
		}
		LOG.info("登陆成功");
		LOG.debug("部门编号："+deptEntity.getDeptCode()+"\n"+"用户名称："+userEntity.getUserName()+
				"\n"+"车牌号："+param.getTruckCode()+"\n部门性质："+deptEntity.getDeptAttribute()+
				"\n用户工号："+userEntity.getEmpCode()+"\n部门名称："+deptEntity.getDeptName());
		pdaLoginRetInfo.setCurrTime(new Date());
		return pdaLoginRetInfo;
	}
	
	
	/**   
	 * @Title: queryEffRegion  
	 * @Description: 查找零担的价格时效区
	 * @param @param sourceStaionSalesDept
	 * @param @return    设定文件   
	 * @return String    返回类型  
	 * @throws  
	 */
	private String queryEffRegion(DeptEntity sourceStaionSalesDept) {
		//按部门级别查找时效区域
		String effRegion = loginDao.queryEffRegionOrg(sourceStaionSalesDept.getDeptCode());
		if(StringUtils.isEmpty(effRegion)){
			//按区查找时效区域
			effRegion = loginDao.queryRegionByCounty(sourceStaionSalesDept.getDeptCounty());
		}
		if(StringUtils.isEmpty(effRegion)){
			//按市查找时效区域
			effRegion = loginDao.queryRegionByCity(sourceStaionSalesDept.getDeptCity());
		}
		if(StringUtils.isEmpty(effRegion)){
			//按省查找时效区域
			effRegion = loginDao.queryRegionByProvince(sourceStaionSalesDept.getDeptProvince());
		}
		return effRegion;
	}
	
	
	
	/**   
	 * @Title: queryExpEffRegion  
	 * @Description: 查找快递价格时效区
	 * @param @param sourceStaionSalesDept
	 * @param @return    设定文件   
	 * @return String    返回类型  
	 * @throws  
	 */
	private String queryExpEffRegion(DeptEntity sourceStaionSalesDept) {
		//按部门级别查找时效区域
		String effRegion = loginDao.queryExpEffRegionOrg(sourceStaionSalesDept.getDeptCode());
		if(StringUtils.isEmpty(effRegion)){
			//按区查找时效区域
			effRegion = loginDao.queryExpRegionByCounty(sourceStaionSalesDept.getDeptCounty());
		}
		if(StringUtils.isEmpty(effRegion)){
			//按市查找时效区域
			effRegion = loginDao.queryExpRegionByCity(sourceStaionSalesDept.getDeptCity());
		}
		if(StringUtils.isEmpty(effRegion)){
			//按省查找时效区域
			effRegion = loginDao.queryExpRegionByProvince(sourceStaionSalesDept.getDeptProvince());
		}
		return effRegion;
	}
	

	/**
	 * 
	 * 保存登陆用户和设备信息
	 * 
	 * @author wenwuneng
	 * @version 1.0
	 * @created 2013-7-31 下午02:08:04
	 */
	
	@Transactional
	private String saveDeviceLoginData(PdaLoginDeviceEntity entity)throws PdaBusiException{
		String returnMsg="";
		try{
			//更具设备号修改上一次登陆状态 states="1";
			//查询上一次登录退出时间是否大于当前时间，如果大于则设置上一次登录退出时间为当前系统时间
		   
		    PdaLoginDeviceEntity outDateEntity=new PdaLoginDeviceEntity();
            outDateEntity.setDvcCode(entity.getDvcCode());           
            outDateEntity.setLoginOutDate(new Date());
			boolean isOk=loginPdaDao.chekLastLoginOutDate(outDateEntity);
			
			
			if(!isOk){//上次是异常登录，且退出时间大于当前登录时间
			    //修改前一次异常退出时间为当前登入5秒前时间
				outDateEntity.setLoginOutDate(new Date(new Date().getTime()-5*1000));
				loginPdaDao.updPdaLoginOutDate(outDateEntity);
			}
			//修改最后登录状态
			loginPdaDao.updPdaLoginInfoData(entity);
			//插入最新记录
			loginPdaDao.savePdaLoginInfoData(entity);
			returnMsg="该PDA设备号:"+entity.getDvcCode()+"登陆信息保存成功";
		}catch(Exception e){
			throw new PdaBusiException(e.getCause());
		}
		
		return returnMsg;
	}
    //营业部到外场
	private List<DestDeptInfoEntity> getSalesShortDestDepts(
			DeptEntity deptEntity, DeptEntity bigDept) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deptId", deptEntity.getId());
		map.put("bigDeptId",bigDept.getId());
		List<DestDeptInfoEntity> deptInfoList = loginDao.getSalesShortDestDepts(map);
		return deptInfoList;
	}
    //外场到营业部
	private List<DestDeptInfoEntity> getShortDestDepts(DeptEntity deptEntity,
			DeptEntity bigDept) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deptId", deptEntity.getId());
		map.put("bigDeptId",bigDept.getId());
		List<DestDeptInfoEntity> deptInfoList = loginDao.getShortDestDepts(map);
		return deptInfoList;
	}
    //外场到外场（外场和分部）
	private List<DestDeptInfoEntity> getLongDestDepts(DeptEntity deptEntity,
			DeptEntity bigDept) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deptId", deptEntity.getId());
		map.put("bigDeptId",bigDept.getId());
		List<DestDeptInfoEntity> deptInfoList = loginDao.getLongDestDepts(map);
		return deptInfoList;
	}

	private void check(PdaLoginRetInfo loginRetInfo, PdaLoginInfo pdaLoginInfo, AsyncMsg asyncMsg) {

		// 获取角色权限
//		List<PrivilegeEntity> privilegeList = rolePrivilegeService
//				.queryRolePrivilege(pdaLoginInfo.getUserType());
//		loginRetInfo.setUserPrivilege(privilegeList);

		// 是否更新基础数据
		boolean dataFlag = validateService.checkDataVer(pdaLoginInfo.getPdaDataVer());
		BaseDataVerEntity dataVer = (BaseDataVerEntity)dataVerCache.get(Constant.BASE_DATA_VERSION_KEY);
		loginRetInfo.setDataVer(dataVer.getDataVer());
		LOG.debug("dataVer:"+dataVer.getDataVer());
		// 是否更新版本信息
		String pgmFlag = validateService.checkPgmVer(asyncMsg.getPgmVer(), asyncMsg.getPdaType());
		if (Constant.PGM_VERSION_NOT_UPDATE.equals(pgmFlag)) {
			loginRetInfo.setReqPgmUpgrade(false);
			loginRetInfo.setForcFlag(false);
		} else if (Constant.PGM_VERSION_UPDATE.equals(pgmFlag)) {
			loginRetInfo.setReqPgmUpgrade(true);
			loginRetInfo.setForcFlag(false);
		} else if (Constant.PGM_VERSION_UPDATE_FORCE.equals(pgmFlag)) {
			loginRetInfo.setReqPgmUpgrade(true);
			loginRetInfo.setForcFlag(true);
		}
		// 获取last  pgmVer
		PgmVerEntity pgmVer = pgmVerCache.get(asyncMsg.getPdaType());
		if(pgmVer != null) {
			loginRetInfo.setPgmVer(pgmVer.getPgmVer());
			LOG.debug("pgmVer:"+pgmVer.getPgmVer());
		}
		// 是否更新数据版本信息
		loginRetInfo.setReqDataUpgrade(dataFlag);
		loginRetInfo.setCurrTime(new Date());
	}

	private DeptEntity getTaskDept(DeptEntity dept) {
		// 查找大部门
		while (true) {
			if(dept==null){
				return null;
			}
			if (dept.getDeptAttribute().equals("1")
					|| dept.getDeptAttribute().equals("0")) {
				return dept;
			}
			if(dept.getParentOrgCode()==null||dept.getParentOrgCode().isEmpty()||dept.getId().equals(dept.getParentOrgCode())){
					return null;
			}
			dept = deptCache.get(dept.getParentOrgCode());
		}
	}
	
		
	/**
	 * 
	* @Description: 查找顶级车队
	* @param deptId
	* @return String  
	* @author xujun
	* @date 2013-8-19 下午4:15:42
	 */
	private String getTopFleetCodeByDeptId(String deptId){
		while(true){
			if(StringUtils.isEmpty(deptId)){
				return null;
			}
			DeptEntity dept = deptCache.getDept(deptId);
			if("Y".equals(dept.getIsTopFleet())){
				return dept.getDeptCode();
			}
			if(StringUtils.isEmpty(dept.getParentOrgCode())||dept.getParentOrgCode().equals(dept.getId())){
				return null;
			}
			deptId = dept.getParentOrgCode();
		}
		
	}

	private void validate(AsyncMsg asyncMsg, PdaLoginInfo param) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(param, "PdaLoginInfo");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.UserCode");
	}
	
	
//	List<PrivilegeEntity> getPrivilegeEntityReqList(List<PrivilegeEntity> privilegeEntityLists ,boolean isFlag){
//		List<PrivilegeEntity> privilegeLists=new ArrayList<PrivilegeEntity>();
//		for(PrivilegeEntity privilegeEntity : privilegeEntityLists){
//			PrivilegeEntity privilegeEntity1=new PrivilegeEntity();
//		String privilegeCode="";
//			if(isFlag){//司机 
//		 privilegeCode=OperationConstant.PDA_SERVICE_POWER_DRIVER.get(privilegeEntity.getPrivilegeCode());
//			}else{//理货员
//          privilegeCode=OperationConstant.PDA_SERVICE_POWER_CHECK.get(privilegeEntity.getPrivilegeCode());
//				
//			}
//			String privilegeName=privilegeEntity.getPrivilegeName();
//			if(privilegeCode ==null || privilegeCode.equals("")){
//				continue;
//			}
//			privilegeEntity1.setPrivilegeCode(privilegeCode);
//			privilegeEntity1.setPrivilegeName(privilegeName);
//			privilegeLists.add(privilegeEntity1);
//		}
//		return privilegeLists;	
//	}

	@Override
	public String getOperType() {
		return LoginConstant.OPER_TYPE_SYS_LOGIN.VERSION;
	}

	@Override
	public boolean isAsync() {
		// 同步
		return false;
	}

	public void setPdaSigninLogoutService(
			IPdaSigninLogoutService pdaSigninLogoutService) {
		this.pdaSigninLogoutService = pdaSigninLogoutService;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

	public void setValidateService(IValidateService validateService) {
		this.validateService = validateService;
	}

	public void setDataVerCache(DataVerCache dataVerCache) {
		this.dataVerCache = dataVerCache;
	}

	public void setPgmVerCache(PgmVerCache pgmVerCache) {
		this.pgmVerCache = pgmVerCache;
	}

	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public void setLoginPdaDao(ILoginPdaDao loginPdaDao) {
		this.loginPdaDao = loginPdaDao;
	}

	public void setVehicleCache(VehicleCache vehicleCache) {
		this.vehicleCache = vehicleCache;
	}
}
