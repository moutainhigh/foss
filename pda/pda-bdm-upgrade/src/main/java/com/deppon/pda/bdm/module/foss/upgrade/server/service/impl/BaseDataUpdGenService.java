package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao;
import com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.BaseDataVerEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DnldBaseData;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DnldBaseDataVer;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.DataTimeOutException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.util.ConstantsUtil;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

/**
 * 
 * 数据下载服务类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-19 下午04:53:21
 */
public class BaseDataUpdGenService implements
		IBusinessService<DnldBaseDataVer, DnldBaseData> {
	
	//数据下载服务类 pda数据版本的长度
	public static final int number=13;
	//数据下载服务类 pda数据版本的长度
	public static final int number1=8;
	//小时
	public static final int hours=24;
	//分钟
	public static final int minute=60;
	//秒
	public static final int miao=60;
	//毫秒
	public static final int millis=1000;
	//天数
	public static final int dayNum=30;
	//数据下载服务类  锁常量
	public static final int locakNum=60; 
	//
	public static final int sleepNum=1000;
	private Logger log = Logger.getLogger(getClass());

	private ISystemFunDao systemFunDao;

	@SuppressWarnings("rawtypes")
	private List<IBaseDataVerGenService> baseDataVerGenServices;

	private IValidateService validateService;

	/**
	 * 
	 * <p>
	 * TODO(解析body)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-2 上午10:00:40
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public DnldBaseData parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DnldBaseData dbd = JsonUtil.parseJsonToObject(DnldBaseData.class,
				asyncMsg.getContent());
		return dbd;
	}

	@Override
	public DnldBaseDataVer service(AsyncMsg asyncMsg, DnldBaseData baseData)
			throws PdaBusiException {
		// 判断pda数据版本的长度
		if (baseData.getDataVer().length() < number) {
			baseData.setDataVer("");
		}
		// 从缓存中获取版本号,判断pda是否更新数据
		boolean dataFlag = validateService.checkDataVer(baseData.getDataVer());
		DnldBaseDataVer dnVer = new DnldBaseDataVer();
		// 如果dataFlag为false,则不需要更新
		if (!dataFlag) {
			// 设置是否更新字段值为false
			dnVer.setReqUpgrade(false);
			return dnVer;
		}
		// dataFlag值为true,则需要更新
		DnldBaseDataVer baseDataVer = this.getDnldDataVer(baseData, asyncMsg);
		// 判断对象baseDataVer是否为空,如果不为空,设置更新类型、强制更新、更新路径、最新版本号
		if (baseDataVer != null) {
			// 设置是否强制更新
			dnVer.setReqUpgrade(baseDataVer.isReqUpgrade());
			// 设置更新类型,如增量和全部更新
			dnVer.setUpdType(baseDataVer.getUpdType());
			// 设置更新路径
			dnVer.setUpdUrl(baseDataVer.getUpdUrl());
			// 设置数据库最新版本号
			dnVer.setNewDataVer(baseDataVer.getNewDataVer());
		}
		return dnVer;
	}

	private DnldBaseDataVer getDnldDataVer(DnldBaseData baseData,
			AsyncMsg asyncMsg) throws PdaBusiException {
		// 从数据库获取最新的数据版本号
		BaseDataVerEntity bsEntity = systemFunDao.getDataVerInfo();
		// 如果对象为空,直接返回
		if (bsEntity == null) {
			return null;
		}
		DnldBaseDataVer baseDataVer = new DnldBaseDataVer();
		String updUrl = "";
		// 如果数据库的版本号长度为8位,则转成毫秒数据版本号
		if (bsEntity.getDataVer().length() == number1) {
			long timeInMillis = this.getDateToMillis(bsEntity.getDataVer());
			bsEntity.setDataVer(String.valueOf(timeInMillis));
		}
		// 当前年月日，格式yyyyMMdd
		String currDate = DateUtils.getNowDateStrNoSep();

		String constantPath = PropertyConstant.DATAVER_DIR + currDate
				+ File.separator + bsEntity.getDataVer() + File.separator
				+ asyncMsg.getDeptCode() + File.separator;
		String constantUrl = ConstantsUtil.HTTPED_HOST
				+ PropertyConstant.DATA_PATH.trim() + currDate + File.separator
				+ bsEntity.getDataVer() + File.separator + asyncMsg.getDeptCode()
				+ File.separator;
		// 司机全部基础数据下载路径
		String filePath_driver_all = constantPath + bsEntity.getDataVer()
				+ Constant.UNDERLINE_DELIMITER + Constant.BASE_DATA_VERSION_ALL
				+ Constant.UNDERLINE_DELIMITER + baseData.getUserType() + ".zip";
		// 司机增量基础数据下载路径
		String filePath_driver_inc = constantPath + baseData.getDataVer() + "-"
				+ bsEntity.getDataVer() + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + Constant.UNDERLINE_DELIMITER
				+ baseData.getUserType() + ".zip";
		// 根据pda当前版本号与数据库版本号做比较,得出是否全部更新或增量更新
		if (this.isRequestAllBaseData(baseData.getDataVer(),
				bsEntity.getDataVer())) {
			// 全部更新
			log.debug("司机完整文件路径:" + filePath_driver_all);
			this.genAllDataVerURL(bsEntity.getDataVer(), asyncMsg,
					filePath_driver_all,baseData.getUserType());
			updUrl = constantUrl + bsEntity.getDataVer()
					+ Constant.UNDERLINE_DELIMITER
					+ Constant.BASE_DATA_VERSION_ALL
					+ Constant.UNDERLINE_DELIMITER + baseData.getUserType()+ ".zip";
			log.debug("司机完整更新地址:" + updUrl);
			baseDataVer.setUpdType(Constant.BASE_DATA_VERSION_ALL);
		} else {
			log.debug("司机增量文件路径:" + filePath_driver_inc);
			this.genIncDataVerURL(baseData.getDataVer(), bsEntity.getDataVer(),
					asyncMsg, filePath_driver_inc,baseData.getUserType());
			updUrl = constantUrl + baseData.getDataVer() + "-"
					+ bsEntity.getDataVer() + Constant.UNDERLINE_DELIMITER
					+ Constant.BASE_DATA_VERSION_INC
					+ Constant.UNDERLINE_DELIMITER + baseData.getUserType()+ ".zip";
			log.debug("司机增量更新地址:" + updUrl);
			baseDataVer.setUpdType(Constant.BASE_DATA_VERSION_INC);
		}
		baseDataVer.setNewDataVer(bsEntity.getDataVer());
		baseDataVer.setUpdUrl(updUrl);
		baseDataVer.setReqUpgrade(true);
		return baseDataVer;
	}

	/**
	 * 
	 * <p>
	 * TODO(判断数据更新是否全部更新或增量更新)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-8 下午2:53:29
	 * @param dataVer
	 * @param currVer
	 * @return
	 * @see
	 */
	private boolean isRequestAllBaseData(String dataVer, String currVer) {
		/**
		 * 1.如果pda端版本号为空,直接返回true 2.pda端版本号不为空,则判断数据库当前版本号与pda端版本号相差多少天
		 * 3.如果大于30天，则返回true 4.如果小于30天，则返回false
		 */
		if ("".equals(dataVer) || dataVer == null) {
			return true;
		}
	
		long beginDate = Long.parseLong(dataVer);
		long currDate = Long.parseLong(currVer);
		long day = (currDate - beginDate) / (hours * minute * miao * millis);
		if (day > dayNum) {
			return true;
		}

		return false;
	}

	/**
	 * <p>TODO(截取字符串)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:54:29
	 * @param startDate
	 * @return
	 * @see
	 */
	private String subString(String startDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(startDate.substring(0, 4));
		sb.append("-");
		sb.append(startDate.substring(4, 6));
		sb.append("-");
		sb.append(startDate.substring(6, 8));
		/** 修改处 **/
		sb.append(" ");
		sb.append("00:00:00");
		return sb.toString();
	}

	/**
	 * <p>TODO(司机增量更新)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:55:32
	 * @param dataVer
	 * @param currVer
	 * @param asyncMsg
	 * @param filePath
	 * @throws PdaBusiException
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	private void genIncDataVerURL(String dataVer, String currVer,
			AsyncMsg asyncMsg, String filePath,String userType) throws PdaBusiException {
		File file = new File(filePath);
		if (!file.exists()) {
			long start = System.currentTimeMillis();
			// 获取锁文件
			FileOutputStream output=null;
			FileLock genLock = null;
			try {
				String lockFilePath = this.checkFile(file);
				output = new FileOutputStream(lockFilePath);
				genLock = this.getFileLock(file, output, genLock);
				if(null == genLock) {
					log.debug(file+"已存在,直接返回下载地址");
					return;
				}
				QueryLocalListDto dto = new QueryLocalListDto();
				dto.setDateVer(dataVer);
				dto.setFlag(Constant.BASE_DATA_VERSION_INC);
				dto.setCurrVer(currVer);
				dto.setFile(file);
				dto.setUserCode(asyncMsg.getUserCode());
				for (IBaseDataVerGenService baseDataVerGenService : baseDataVerGenServices) {
					try {
						//设置部门编码
						dto.setDeptCode(asyncMsg.getDeptCode());
//						baseDataVerGenService.genBaseDataFile(dto);
						if(baseDataVerGenService.getUserType().equals(Constant.USER_TYPE.ALL)){
							dto = baseDataVerGenService.initBaseDataParam(asyncMsg, dto);
							//当前业务类型为ALL则,不分用户类型，都进行下载
							baseDataVerGenService.genBaseDataFile(dto);
						}else if(baseDataVerGenService.getUserType().equals(userType)){
							dto = baseDataVerGenService.initBaseDataParam(asyncMsg, dto);
							//当前登录用户与业务匹配,则下载。
							baseDataVerGenService.genBaseDataFile(dto);
						}
					} catch (Exception e) {
						log.error(LogUtil.logFormat(e));
					}
				}
				String driverPath = file.getParent() + File.separator
						+ dataVer + "-" + currVer
						+ Constant.UNDERLINE_DELIMITER
						+ Constant.BASE_DATA_VERSION_INC;
				log.debug("压缩文件目标路径:" + driverPath + "," + "压缩完成生成路径:"
						+ file.getPath());
				zip(driverPath, file.getPath());
				delFolder(driverPath);
				log.debug("-------增量更新耗时:" + (System.currentTimeMillis() - start) + "ms-------");
			} catch (FileNotFoundException e) {
				log.error(LogUtil.logFormat(e));
			} finally {
				try {
					if (null != genLock) {
						genLock.release();
					}
					if(output!=null){
					  output.close();
					}
				} catch (IOException e) {
					log.error(LogUtil.logFormat(e));
				}
			}
		}
	}

	/**
	 * <p>TODO(司机完整更新)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:55:49
	 * @param currVer
	 * @param asyncMsg
	 * @param filePath
	 * @throws PdaBusiException
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	private void genAllDataVerURL(String currVer, AsyncMsg asyncMsg,
			String filePath,String userType) throws PdaBusiException {
		File file = new File(filePath);
		if (!file.exists()) {
			long start = System.currentTimeMillis();
			FileOutputStream output = null;
			FileLock genLock = null;
			try {
				String lockFilePath = this.checkFile(file);
				output = new FileOutputStream(lockFilePath);
				genLock = this.getFileLock(file, output, genLock);
				if(null == genLock) {
					return;
				}
				QueryLocalListDto dto = new QueryLocalListDto();
				dto.setDateVer("");
				dto.setFlag(Constant.BASE_DATA_VERSION_ALL);
				dto.setCurrVer(currVer);
				dto.setFile(file);
				dto.setUserCode(asyncMsg.getUserCode());
				for (IBaseDataVerGenService baseDataVerGenService : baseDataVerGenServices) {
					try {
						//设置部门编码
						dto.setDeptCode(asyncMsg.getDeptCode());
						if(baseDataVerGenService.getUserType().equals(Constant.USER_TYPE.ALL)){
							dto = baseDataVerGenService.initBaseDataParam(asyncMsg,dto);
							//当前业务类型为ALL则,不分用户类型，都进行下载
							baseDataVerGenService.genBaseDataFile(dto);
						}else if(baseDataVerGenService.getUserType().equals(userType)){
							dto = baseDataVerGenService.initBaseDataParam(asyncMsg,dto);
							//当前登录用户与业务匹配,则下载。
							baseDataVerGenService.genBaseDataFile(dto);
						}
					} catch (Exception e) {
						log.error(LogUtil.logFormat(e));
					}
				}
				String driverPath = file.getParent() + File.separator
						+ currVer + Constant.UNDERLINE_DELIMITER
						+ Constant.BASE_DATA_VERSION_ALL;
				// 压缩全部更新基础数据文件
				log.debug("压缩文件目标路径:" + driverPath + "," + "压缩完成生成路径:"
						+ file.getPath());
				zip(driverPath, file.getPath());
				delFolder(driverPath);
			} catch (FileNotFoundException e) {
				log.error(LogUtil.logFormat(e));
			} finally {
				try {
					if (null != genLock) {
						genLock.release();
					}
					if(output!=null){
					  output.close();
					}
				} catch (IOException e) {
					log.error(LogUtil.logFormat(e));
				}
			}
			log.debug("-------完整更新耗时:" + (System.currentTimeMillis() - start) + "ms-------");
		}
	}
	
	/**
	 * <p>TODO(获取锁文件)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:21:05
	 * @param file
	 * @param output
	 * @param genLock
	 * @return
	 * @throws FileNotFoundException
	 * @see
	 */
	private FileLock getFileLock(File file, FileOutputStream output, FileLock genLock) throws FileNotFoundException {
		FileChannel fileChannel = output.getChannel();
		if (null == genLock) {
			for (int i = 0; i < locakNum; i++) {
				try {
					genLock = fileChannel.tryLock();
				} catch (Exception e) {
					genLock = null;
				}

				if (file.exists()) {
					return null;
				}

				if (null == genLock) {
					try {
						Thread.sleep(sleepNum);
					} catch (InterruptedException e) {
						log.error(LogUtil.logFormat(e));
					}
				} else {
					break;
				}
			}
			if (null == genLock) {
				throw new DataTimeOutException();
			}
		}
		return genLock;
	}

	/**
	 * 创建锁文件
	 * 
	 * @param file
	 * @return lockFilePath
	 */
	private synchronized String checkFile(File file) {
		String lockFilePath = file.getParent() + File.separator + Constant.GEN_LCK_FILE;
		log.debug("---------LockFilePath:" + lockFilePath + "-------------------");
		File lockFile = new File(lockFilePath);
		File parentFile = lockFile.getParentFile();
		if (!lockFile.exists()) {
			try {
				parentFile.mkdirs();
				lockFile.createNewFile();
			} catch (IOException e) {
				log.error(LogUtil.logFormat(e));
			}
		}
		return lockFilePath;
	}

	/**
	 * <p>TODO(将基础数据文件打包)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:56:50
	 * @param sourceDir
	 * @param zipFile
	 * @return
	 * @see
	 */
	private String zip(String sourceDir, String zipFile) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			File file = new File(sourceDir);
			os = new FileOutputStream(zipFile);
			bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);
			String basePath = null;
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {
				basePath = file.getParent();
			}
			zipFile(file, basePath, zos);
		} catch (IOException e) {
			log.error(LogUtil.logFormat(e));
		} finally {
			try {
				if(zos!=null){
				 zos.closeEntry();
				 zos.close();
				}
				bos.close();
				os.close();
			} catch (IOException e) {
				log.debug(LogUtil.logFormat(e));
			}
		}
		return zipFile;

	}

	/**
	 * <p>TODO(将基础数据文件打包)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:57:11
	 * @param source
	 * @param basePath
	 * @param zos
	 * @see
	 */
	private void zipFile(File source, String basePath, ZipOutputStream zos) {
		File[] files = new File[0];
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}
		String pathName;
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1)
							+ File.separator;
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else {
					log.debug("压缩:" + file.getName());
					pathName = file.getPath().substring(basePath.length() + 1);
					InputStream is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = bis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
					is.close();
				}
			}
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));
		}

	}

	/**
	 * <p>TODO(删除基础数据文件)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:57:25
	 * @param folderPath
	 * @see
	 */
	private void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));

		}
	}

	/**
	 * <p>TODO(删除指定文件夹下所有文件 param path 文件夹完整绝对路径)</p> 
	 * @author chengang
	 * @date 2013-6-14 上午10:57:49
	 * @param path
	 * @return
	 * @throws IOException
	 * @see
	 */
	private boolean delAllFile(String path) throws IOException {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return LoginConstant.OPER_TYPE_SYS_DATA_DNLD.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * <p>TODO(获取时间毫秒)</p>
	 * @author chengang
	 * @date 2013-4-2 上午10:04:07
	 * @param dataVer
	 * @return
	 * @see
	 */
	private long getDateToMillis(String dataVer) {
		Date date = null;
		try {
			date = DateUtils.parseDateTime(subString(dataVer));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	public void setSystemFunDao(ISystemFunDao systemFunDao) {
		this.systemFunDao = systemFunDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDataVerGenServices(
			List<IBaseDataVerGenService> baseDataVerGenServices) {
		this.baseDataVerGenServices = baseDataVerGenServices;
	}

	public void setValidateService(IValidateService validateService) {
		this.validateService = validateService;
	}

}
