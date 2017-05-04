package com.deppon.pda.bdm.module.core.server.service.impl;

import java.text.ParseException;
import java.util.Date;

import com.deppon.pda.bdm.module.core.server.cache.DataVerCache;
import com.deppon.pda.bdm.module.core.server.cache.DeviceCache;
import com.deppon.pda.bdm.module.core.server.cache.PgmVerCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.BaseDataVerEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PdaInfo;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.login.DeviceDisableException;
import com.deppon.pda.bdm.module.core.shared.exception.login.DeviceNotFoundException;
import com.deppon.pda.bdm.module.core.shared.exception.login.PasswordErrorException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserNotFoundException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserTypeUnMatchException;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;

/**
 * 
 * @ClassName ValidateService
 * @Description 数据验证
 * @author xujun cometzb@126.com
 * @date 2012-12-28
 */
public class ValidateService implements IValidateService {
	private UserCache pdaUserCache;

	private DeviceCache deviceCache;

	private DataVerCache dataVerCache;

	private PgmVerCache pgmVerCache;
	public static final  int four = 4;
	public static final  int six = 6;
	public static final  int eight  = 8;
	@Override
	public String checkPgmVer(String pgmVer, String pdaModel) {

		PgmVerEntity pgmVerEntity = pgmVerCache.getPgmVer(pdaModel);

		// PDA程序版本为空
		if (pgmVer == null || pgmVer.equals("")) {

			// 服务器程序版本为空，则不需要更新
			if (pgmVerEntity == null) {
				return Constant.PGM_VERSION_NOT_UPDATE;
			}
			// 服务器程序版本不为空，则需要更新
			else {
				// 如果是强制更新版本，则需要强制更新
				if (Constant.VERSION_UPDATE_FORCE.equals(pgmVerEntity
						.getForcFlag())) {
					pgmVerCache.invalid(pdaModel);
					pgmVerEntity = pgmVerCache.getPgmVer(pdaModel);
					if (Constant.VERSION_UPDATE_FORCE.equals(pgmVerEntity
							.getForcFlag())) {
						return Constant.PGM_VERSION_UPDATE_FORCE;
					}
				}
				// 如果是非强制更新版本或强制属性为空，则不需要强制更新
				return Constant.PGM_VERSION_UPDATE;
			}
		}

		// PDA程序版本不为空，服务器程序版本为空， 版本号相同或PDA端大于服务器端，则不需要更新
		if (pgmVerEntity == null
				|| pgmVerEntity.getPgmVer().compareTo(pgmVer) <= 0) {
			pgmVerCache.invalid(pdaModel);
			pgmVerEntity = pgmVerCache.getPgmVer(pdaModel);
			if (pgmVerEntity == null
					|| pgmVerEntity.getPgmVer().compareTo(pgmVer) <= 0) {

				if (pgmVerEntity == null) {
					return Constant.PGM_VERSION_NOT_UPDATE;
				}

				// 刷新缓存后，如果是强制更新版本，则需要强制更新
				if (Constant.VERSION_UPDATE_FORCE.equals(pgmVerEntity
						.getForcFlag())) {
					return Constant.PGM_VERSION_UPDATE_FORCE;
				}

				// 刷新缓存后，如果是非强制更新版本或强制属性为空，则不需要强制更新
				return Constant.PGM_VERSION_UPDATE;
			}

		}

		// 如果是强制更新版本，则需要强制更新
		if (Constant.VERSION_UPDATE_FORCE.equals(pgmVerEntity.getForcFlag())) {
			pgmVerCache.invalid(pdaModel);
			pgmVerEntity = pgmVerCache.getPgmVer(pdaModel);
			if (Constant.VERSION_UPDATE_FORCE
					.equals(pgmVerEntity.getForcFlag())) {
				return Constant.PGM_VERSION_UPDATE_FORCE;
			}
		}

		// 如果是非强制更新版本或强制属性为空，则不需要强制更新
		return Constant.PGM_VERSION_UPDATE;
	}

	/**
	 * 校验用户登录账号和密码是否存在或者一致
	 * 
	 * @param pdaLoginInfo
	 *            PdaLoginInfo 登录信息
	 * @throws UserTypeUnMatchException
	 */
	@Override
	public void checkPwd(PdaLoginInfo pdaLoginInfo) throws PdaBusiException {
		UserEntity userEntity = pdaUserCache
				.getUser(pdaLoginInfo.getUserCode());
		// 用户账号信息不为空，抛出用户不存在异常
		if (userEntity == null) {
			throw new UserNotFoundException();
		}
		// TODO 暂时不对用户类型做验证
		// 用户类型不一致
		// if (!userEntity.getUserType().equals(pdaLoginInfo.getUserType())) {
		// pdaUserCache.refresh(pdaLoginInfo.getUserCode());
		// userEntity = pdaUserCache.getUser(pdaLoginInfo.getUserCode());
		// if(!userEntity.getUserType().equals(pdaLoginInfo.getUserType())){
		// throw new UserTypeUnMatchException();
		// }
		// }
		// 用户密码信息校验，抛出密码不正确异常
		if (!userEntity.getPassword().equals(pdaLoginInfo.getPassword())) {
			pdaUserCache.invalid(pdaLoginInfo.getUserCode());
			userEntity = pdaUserCache.getUser(pdaLoginInfo.getUserCode());
			if (!userEntity.getPassword().equals(pdaLoginInfo.getPassword())) {
				throw new PasswordErrorException();
			}
		}
	}

	@Override
	public boolean checkDataVer(String dataVer) {
		BaseDataVerEntity baseDataVerEntity = dataVerCache
				.getBaseDataVer(Constant.BASE_DATA_VERSION_KEY);
		// PDA基础数据版本为空
		if (dataVer == null || "".equals(dataVer)) {
			// 服务器基础数据版本为空，则不需要更新
			if (baseDataVerEntity == null) {
				dataVerCache.invalid(Constant.BASE_DATA_VERSION_KEY);
				baseDataVerEntity = dataVerCache
						.getBaseDataVer(Constant.BASE_DATA_VERSION_KEY);
				if (baseDataVerEntity == null) {
					return false;
				}
				return true;
			} else {
				return true;
			}
		}
		if (baseDataVerEntity != null
				&& baseDataVerEntity.getDataVer().length() == Constant.DATAVER_BASE_DATA) {
			long timeInMillis = this.getDateToMillis(baseDataVerEntity.getDataVer());
			baseDataVerEntity.setDataVer(String.valueOf(timeInMillis));
		}

		/*if (!"".equals(dataVer) && dataVer.length() == 8) {
			long timeInMillis = this.getDateToMillis(dataVer);
			dataVer = String.valueOf(timeInMillis);
		}*/
		// PDA基础数据版本不为空，服务器基础数据版本为空，或者PDA数据版本大于等于服务器数据版本，则不需要更新
		if (baseDataVerEntity == null
				|| baseDataVerEntity.getDataVer().compareTo(dataVer) <= 0) {

			// 刷新缓存并再次判断，为空，则不需要更新
			dataVerCache.invalid(Constant.BASE_DATA_VERSION_KEY);
			baseDataVerEntity = dataVerCache
					.getBaseDataVer(Constant.BASE_DATA_VERSION_KEY);
			if (baseDataVerEntity == null
					|| baseDataVerEntity.getDataVer().compareTo(dataVer) <= 0) {
				return false;
			}
		}
		return true;
	}

	private long getDateToMillis(String dataVer) {
		Date date = null;
		try {
			String startDate = this.getDateByDataVer(dataVer);
			date = DateUtils.parseDateTime(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//return date.getTime();
		if (date == null)
			return 0;
		else
			return date.getTime();
	}

	private String getDateByDataVer(String startDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(startDate.substring(0, four));
		sb.append("-");
		sb.append(startDate.substring(four, six));
		sb.append("-");
		sb.append(startDate.substring(six, eight));
		sb.append(" ");
		sb.append("00:00:00");
		return sb.toString();
	}

	/**
	 * 校验PDA信息是否合法，用户信息，部门信息是否存在
	 * 
	 * @param pdaInfo
	 */
	@Override
	public void check(PdaInfo pdaInfo) throws PdaBusiException {
		// 获取PDA信息
		PdaDeviceEntity deviceEntity = deviceCache.getDevice(pdaInfo
				.getPdaCode());
		if (deviceEntity == null) {
			throw new DeviceNotFoundException();
		}
		// PDA设备状态为不可用
		if (Constant.DISABLE_DEVICE.equals(deviceEntity.getStatus())) {
			deviceCache.invalid(pdaInfo.getPdaCode());
			deviceEntity = deviceCache.getDevice(pdaInfo.getPdaCode());
			if (Constant.DISABLE_DEVICE.equals(deviceEntity.getStatus())) {
				throw new DeviceDisableException();
			}
		}
	}

	public void setPdaUserCache(UserCache pdaUserCache) {
		this.pdaUserCache = pdaUserCache;
	}

	public void setDeviceCache(DeviceCache deviceCache) {
		this.deviceCache = deviceCache;
	}

	public void setDataVerCache(DataVerCache dataVerCache) {
		this.dataVerCache = dataVerCache;
	}

	public void setPgmVerCache(PgmVerCache pgmVerCache) {
		this.pgmVerCache = pgmVerCache;
	}
 
}
