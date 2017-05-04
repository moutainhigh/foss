package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.cache.DeviceCache;
import com.deppon.pda.bdm.module.core.server.cache.PgmVerCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.login.PasswordErrorException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserTypeUnMatchException;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.core.shared.util.VersionUtil;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IDeviceDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeviceParamEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.PgmVerNotFoundException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.UserNotFoundException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.util.ConstantsUtil;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.DnldPgmVer;

/**
 * TODO(程序版本更新服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2013-1-4 下午2:17:45,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2013-1-4 下午2:17:45
 * @since
 * @version
 */
public class PgmUpgradeService implements
		IBusinessService<DnldPgmVer, PgmVerEntity> {

	private Logger log = Logger.getLogger(getClass());

	// private UserCache userCache;

	private PgmVerCache pgmVerCache;

	private DeviceCache deviceCache;

	private IDeviceDao deviceDao;

	/**
	 * @param pdaInfo
	 * @param bodyJson
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.business.IBusinessService#parseBody(com.deppon.pda.model.PdaInfo,
	 *      java.lang.String)
	 */

	@Override
	public PgmVerEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// 解析body
		PgmVerEntity pgmVerEntity = JsonUtil.parseJsonToObject(
				PgmVerEntity.class, asyncMsg.getContent());

		return pgmVerEntity;
	}

	/**
	 * @param pdaInfo
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.business.IBusinessService#service(com.deppon.pda.model.PdaInfo,
	 *      java.lang.Object)
	 */

	@Override
	public DnldPgmVer service(AsyncMsg asyncMsg, PgmVerEntity pgmVerEntity)
			throws PdaBusiException {
		// 校验数据合法性
		// this.check(asyncMsg);
		DnldPgmVer dp = new DnldPgmVer();
		//设置当前时间
		dp.setNowTime(DateUtils.formatDateTime(new Date()));
		// 通过设备编号获取设备信息
		PdaDeviceEntity pde = this.queryPdaDeviceInfo(asyncMsg.getPdaCode());
		if (pde == null) {
			pde = deviceDao.getDeviceInfo(asyncMsg.getPdaCode());
		}
		// 如果版本信息为null,直接返回更新标识为不更新
		if (pde == null) {
			log.debug("--设备号:"+asyncMsg.getPdaCode()+"在系统中不存在,不走程序更新--");
			dp.setReqUpgrade(Constant.PGM_VERSION_NOT_UPDATE);
			return dp;
		}
		// 通过设备型号获取版本更新信息
		PgmVerEntity pve = this.queryLastPgmVer(asyncMsg.getPdaType());
		
		// 修改设备表中当前设备的版本号与实际版本号
		DeviceParamEntity param = new DeviceParamEntity();
		long current_vesion_to_long = VersionUtil.parseVersionToLong(pgmVerEntity.getPgmVer());
		long newest_vesion_to_long = -1;
		if(pve != null) {
			param.setNewest_version(pve.getPgmVer());
			newest_vesion_to_long = VersionUtil.parseVersionToLong(pve.getPgmVer());
		}
		param.setPdaCode(asyncMsg.getPdaCode());	
		param.setCurrent_device_version(pgmVerEntity.getPgmVer());
		param.setCurrent_vesion_to_long(current_vesion_to_long);
		param.setNewest_vesion_to_long(newest_vesion_to_long);
		// 1.版本信息为空,则不更新
		// 2.PDA所属机构与服务端版本机构不符且PDA设备型号与服务端设备型号不符且版本号高于数据库版本号 ,则不更新
		if(pve == null) {
			param.setNewest_version("");
			dp.setReqUpgrade(Constant.PGM_VERSION_NOT_UPDATE);
			deviceDao.updDeviceInfo(param);
			return dp;
		}
		if (newest_vesion_to_long <= current_vesion_to_long) {
			param.setCurrent_device_version(asyncMsg.getPgmVer());
			param.setCurrent_vesion_to_long(VersionUtil.parseVersionToLong(asyncMsg.getPgmVer()));
			deviceDao.updDeviceInfo(param);
			dp.setReqUpgrade(Constant.PGM_VERSION_NOT_UPDATE);
			return dp;
		}
		deviceDao.updDeviceInfo(param);
		// 检查程序升级包是否存在,存在则返回更新路径
		String url = this.checkPgmVerFile(pve);
		// 程序更新路径
		dp.setUpdUrl(url);
		log.debug("--------------程序更新路径:" + url + "-------------------");
		// 设置程序版本更新标志
		if (Constant.PGM_VERSION_UPDATE.equals(pve.getForcFlag())) {
			// 不强制更新
			dp.setReqUpgrade(Constant.PGM_VERSION_UPDATE);
		} else {
			// 强制更新
			dp.setReqUpgrade(Constant.PGM_VERSION_UPDATE_FORCE);
		}
		// 数据库最新程序版本号
		dp.setCurrVer(pve.getPgmVer());
		// 程序生效时间
		dp.setActiveTime(pve.getActiveTime());
		return dp;
	}

	/**
	 * <p>
	 * TODO(查询最新程序版本)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-9 下午5:38:27
	 * @param pdaModel
	 * @return
	 * @see
	 */
	private PgmVerEntity queryLastPgmVer(String pdaModel) {

		return pgmVerCache.getPgmVer(pdaModel);
	}

	/**
	 * <p>
	 * TODO(查询设备信息)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-9 下午5:38:38
	 * @param pdaCode
	 * @return
	 * @see
	 */
	private PdaDeviceEntity queryPdaDeviceInfo(String pdaCode) {

		return deviceCache.getDevice(pdaCode);
	}

	/**
	 * <p>
	 * TODO(判断本地磁盘是否有程序升级文件)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-9 下午4:58:06
	 * @param pve
	 * @return
	 * @throws PgmVerNotFoundException
	 * @see
	 */
	private String checkPgmVerFile(PgmVerEntity pve)
			throws PgmVerNotFoundException {
		// 程序包路径
		String filePath = PropertyConstant.PGMVER_DIR + pve.getPdaModel()
				+ File.separator + pve.getPgmVer() + File.separator
				+ PropertyConstant.PGM_NAME;
		File file = new File(filePath);
		// 判断程序包是否存在
		if (!file.exists()) {
			throw new PgmVerNotFoundException();
		}
		return ConstantsUtil.HTTPED_HOST + PropertyConstant.PGM_PATH
				+ pve.getPdaModel() + File.separator + pve.getPgmVer()
				+ File.separator + PropertyConstant.PGM_NAME;
	}

	/**
	 * <p>
	 * TODO(校验用户登录账号和密码是否存在或者一致)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-9 下午5:38:58
	 * @param asyncMsg
	 * @throws UserNotFoundException
	 * @throws PasswordErrorException
	 * @throws UserTypeUnMatchException
	 * @see
	 */
	/*
	 * public void check(AsyncMsg asyncMsg) throws UserNotFoundException,
	 * PasswordErrorException, UserTypeUnMatchException {
	 * 
	 * // 获取用户信息 UserEntity user = userCache.getUser(asyncMsg.getUserCode());
	 * 
	 * // 用户账号信息不为空，抛出用户不存在异常 if (user == null) { throw new
	 * UserNotFoundException(); } }
	 */

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return LoginConstant.OPER_TYPE_SYS_VER_DNLD.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * public void setUserCache(UserCache userCache) { this.userCache =
	 * userCache; }
	 */

	public void setDeviceCache(DeviceCache deviceCache) {
		this.deviceCache = deviceCache;
	}

	public void setPgmVerCache(PgmVerCache pgmVerCache) {
		this.pgmVerCache = pgmVerCache;
	}

	public void setDeviceDao(IDeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

}
