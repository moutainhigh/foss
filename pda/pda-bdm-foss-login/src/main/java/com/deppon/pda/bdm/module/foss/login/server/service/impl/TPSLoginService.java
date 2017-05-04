package com.deppon.pda.bdm.module.foss.login.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pda.api.server.service.IBindingLeasedVehicleService;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.domain.BindingLeasedTruckEntity;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;
import com.deppon.pda.bdm.module.core.server.cache.DataVerCache;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.PgmVerCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
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
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.login.server.dao.IDeviceBundleDao;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginDao;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginPdaDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DeviceBundleEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginDeviceEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginRetInfo;
import com.deppon.pda.bdm.module.foss.login.shared.domain.UserInfoEntity;
import com.deppon.pda.bdm.module.foss.login.shared.exception.StationSalesDeptNotFoundException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.UserInfoNotWholeException;
import com.deppon.pda.bdm.module.foss.login.shared.exception.UserNotFoundException;

/**
 * 
 * @ClassName: TPSLoginService 
 * @Description: TODO(外请车司机登录入口) 
 * @author &268974  wangzhili
 * @date 2016-5-28 下午2:33:21 
 *
 */
public class TPSLoginService implements IBusinessService<PdaLoginRetInfo, PdaLoginInfo>{
	//小时
	public final static int HOUR = 8; 
	//分钟
	public final static int MIN = 60; 
	//毫秒
	public final static int MILLISECOND=1000;
	//秒
	public final static int SECOND=5;
	private static final Logger LOG = Logger.getLogger(PdaLoginService.class);
	private UserCache userCache;
	private DeptCache deptCache;	
	private IValidateService validateService;	
	private DataVerCache dataVerCache;	
	private PgmVerCache pgmVerCache;
	private ILoginDao loginDao;
	private ILoginPdaDao loginPdaDao;
	private IPdaSigninLogoutService pdaSigninLogoutService;
	private IBindingLeasedVehicleService bindingLeasedVehicleService;
	private IDeviceBundleDao deviceBundleDao;

	//解析包体
	@Override
	public PdaLoginInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PdaLoginInfo pdaLoginInfo = JsonUtil.parseJsonToObject(PdaLoginInfo.class, asyncMsg.getContent());
		pdaLoginInfo.setPdaCode(asyncMsg.getPdaCode());
		pdaLoginInfo.setPdaPgmVer(asyncMsg.getPgmVer());
		pdaLoginInfo.setUserCode(asyncMsg.getUserCode());
		return pdaLoginInfo;
	}

	//业务方法入口
	@SuppressWarnings("null")
	@Override
	public PdaLoginRetInfo service(AsyncMsg asyncMsg, PdaLoginInfo param)
			throws PdaBusiException {
		//校验参数
		LOG.info("校验参数工号为："+param.getUserCode());
		this.validate(asyncMsg, param);
		PdaLoginRetInfo pdaLoginRetInfo = new PdaLoginRetInfo();
		BindingLeasedTruckEntity trunckEntity = null;
		//查找顶级车队
		try{
			LOG.info("查找顶级车队"+param.getTruckCode());
			trunckEntity = bindingLeasedVehicleService.queryBindingLeasedDate(param.getTruckCode());
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}	
		if(trunckEntity==null){
			throw new FossInterfaceException(null, "系统无该外请车车辆信息");
		}
		if(trunckEntity.getOrgCode()==null){
			throw new FossInterfaceException(null, "该外请车无所属车队信息");
		}
		// 通过顶级车队查找对应的外场
		String outStorageCode = loginDao.getOutStorageCode(trunckEntity
				.getOrgCode());
		if (outStorageCode == null) {
			throw new FossInterfaceException(null, "绑定的顶级车队没有找到对应的外场");
		}
		if (trunckEntity.getOperatorCode() == null) {
			throw new FossInterfaceException(null, param.getTruckCode()
					+ "没有绑定顶级车队的操作人!");
		}
		UserEntity userEntity = userCache.getUser(trunckEntity.getOperatorCode());
		if(userEntity==null){
			throw new UserNotFoundException();
		}
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		//查询外场对应的始发驻地营业部
		DeptEntity sourceStaionSalesDept = loginDao.querySourceStaionSalesDept(outStorageCode);
		if(sourceStaionSalesDept.getDeptCode()==null){
			throw new StationSalesDeptNotFoundException();
		}
		
		/**
		 * 接送货APP外请车密码优化
		 * 
		 *  author  354682
		 */
		//查询外请车绑定信息
		List<DeviceBundleEntity> deviceMsgList = deviceBundleDao.queryDeviceBundleMsg(param.getPdaCode(),param.getTruckCode());
		if(deviceMsgList.size() == 0){
			DeviceBundleEntity deviceMsg = new DeviceBundleEntity();
			//系统自动绑定  BAM系统向pdasit.t_pda_TPSdevice中插入数据建立一对一的关系
 			deviceMsg.setDvcCode(param.getPdaCode());
			deviceMsg.setTruckCode(param.getTruckCode());
			deviceMsg.setStatus("Y");
			deviceMsg.setId(UUID.randomUUID().toString());
			deviceMsg.setSystemType(asyncMsg.getPdaType());
			try{
				LOG.info("根据部门编号查找顶级车队名称"+trunckEntity.getOrgCode());
				//查询顶级车队名称
				String topFleet=deviceBundleDao.findtopFleet(trunckEntity.getOrgCode());
				LOG.info("顶级车队名称: "+topFleet);
				deviceMsg.setTopFleet(topFleet);
				LOG.info("设备号绑定外请车"+JsonUtil.encapsulateJsonObject(deviceMsg));
				//绑定设备外请车信息
				deviceBundleDao.boundDvcTruck(deviceMsg);
			}catch (BusinessException e) {
				LOG.info("----------------绑定外请车失败！------------------");
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}catch(Exception e){
				LOG.info("----------------绑定外请车失败！------------------");
				throw new FossInterfaceException(null,"出现未知异常");
			}	
		}else if(deviceMsgList.size()==1){
			DeviceBundleEntity deviceMsg = deviceMsgList.get(0);
			if(!deviceMsg.getDvcCode().equals(param.getPdaCode())){
				LOG.info("----------------该车牌号已与其他设备号绑定，无法登陆！------------------");
				throw new FossInterfaceException(null, "该车牌号已与其他设备号绑定，无法登陆！");
				
			}else if(!deviceMsg.getTruckCode().equals(param.getTruckCode())){
				LOG.info("----------------该设备已与其他车牌号绑定，无法登陆！------------------");
				throw new FossInterfaceException(null, "该设备已与其他车牌号绑定，无法登陆！");
		    }
		}else if(deviceMsgList.size()==2){
			if((deviceMsgList.get(0).getDvcCode().equals(param.getPdaCode())&&deviceMsgList.get(0).getTruckCode().equals(param.getTruckCode())||
				deviceMsgList.get(1).getDvcCode().equals(param.getPdaCode())&&deviceMsgList.get(1).getTruckCode().equals(param.getTruckCode()))){
				LOG.info("----------------多条绑定记录！------------------");
			}else{
				LOG.info("----------------该设备或车牌已绑定，无法登陆！------------------");
				throw new FossInterfaceException(null, "该设备或车牌已绑定，无法登陆！");
			}
		}
		
		//始发驻地营业部
		pdaLoginRetInfo.setSourceStationSaleDept(sourceStaionSalesDept.getDeptCode());
		//始发驻地营业部时效区域
		pdaLoginRetInfo.setStationSaleDeptEffRegion(this.queryEffRegion(sourceStaionSalesDept));
		if(deptEntity == null){
			throw new UserInfoNotWholeException();
		}else{
			// 1 为驻地门，如果该部门为驻地部门，则将其是否外场字段设为 1
			if("Y".equals(deptEntity.getIsstation())){
				deptEntity.setIsOutStorage("1");
			}
		}
		//调用foss接口绑定
		try{
			PdaSigninDto signinDto = new PdaSigninDto();
			signinDto.setDeviceNo(asyncMsg.getPdaCode());
			signinDto.setDriverCode(asyncMsg.getUserCode());
			signinDto.setVehicleNo(param.getTruckCode());
			signinDto.setSignTime(new Date());
			signinDto.setUserType(asyncMsg.getUserType());
			signinDto.setOrgCode(deptEntity.getDeptCode());
			//调用FOSS接口,进行签到
			pdaSigninLogoutService.signIn(signinDto);
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}
		//返回本部门下员工Code与员工名称
		List<UserInfoEntity> userInfoList = loginDao.getUserInfoByDeptId(deptEntity.getId());
		//返回实发外场编码
		pdaLoginRetInfo.setDepartureFieldCode(outStorageCode);
		//返回用户所在部门下员工实体
		if(userInfoList!=null){
			pdaLoginRetInfo.setUsers(userInfoList);		
		}
		//返回用户实体以及
		if(trunckEntity.getDeiverName()!=null){
			//外请车司机有配置司机姓名，则传给前台  ，没有的话传操作人的用户姓名
			userEntity.setUserName(trunckEntity.getDeiverName());
		} 
		pdaLoginRetInfo.setUserEntity(userEntity);		
		//获取大部门
		DeptEntity bigDept = getTaskDept(deptEntity);
		if(bigDept!=null){
			bigDept.setPhoneAndFax("0");
		}
		//返回大部门
		pdaLoginRetInfo.setBigDept(bigDept);
		//返回部门实体
		pdaLoginRetInfo.setDeptEntity(deptEntity);
		//基础数据 程序版本检测
		this.check(pdaLoginRetInfo,param,asyncMsg);
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
			entity.setLoginOutDate(new Date(new Date().getTime()+HOUR*MIN*MIN*MILLISECOND));
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

	//操作类型
	@Override
	public String getOperType() {
		return LoginConstant.OPER_TYPE_TPS_LOGIN.VERSION;
	}
	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	//校验参数
	private void validate(AsyncMsg asyncMsg, PdaLoginInfo param) throws IllegalArgumentException{
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(param, "PdaLoginInfo");
		//校验PDA设备号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		//校验用户信息
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.UserCode");
	    //校验车牌号
		Argument.hasText(param.getTruckCode(), "param.trunckCode");
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
				outDateEntity.setLoginOutDate(new Date(new Date().getTime()-SECOND*MILLISECOND));
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
	 * 
	 * @Title: getTaskDept 
	 * @Description: TODO(获取大部门信息)  
	 * @return DeptEntity    返回类型 
	 * @param dept
	 * @return
	 * @author： 268974  wangzhili
	 */
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
	 * @Title: check 
	 * @Description: TODO(检查更新)  
	 * @return void    返回类型 
	 * @param loginRetInfo
	 * @param pdaLoginInfo
	 * @param asyncMsg
	 * @author： 268974  wangzhili
	 */
	private void check(PdaLoginRetInfo loginRetInfo, PdaLoginInfo pdaLoginInfo, AsyncMsg asyncMsg) {
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

	public void setPdaSigninLogoutService(
			IPdaSigninLogoutService pdaSigninLogoutService) {
		this.pdaSigninLogoutService = pdaSigninLogoutService;
	}

	public void setBindingLeasedVehicleService(
			IBindingLeasedVehicleService bindingLeasedVehicleService) {
		this.bindingLeasedVehicleService = bindingLeasedVehicleService;
	}

	public void setDeviceBundleDao(IDeviceBundleDao deviceBundleDao) {
		
		this.deviceBundleDao = deviceBundleDao;
	}
	
	
	
}
