package com.deppon.pda.bdm.module.foss.upgrade.server.job;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.log4j.Logger;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
/**
 * 
 * TODO(生成数据版本号Job)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-9 上午11:28:47,content:TODO </p>
 * @author chengang
 * @date 2013-4-9 上午11:28:47
 * @since
 * @version
 */
public class BaseDataVerGenJob<T> {
	private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(job生成最新的数据版本)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-4-8 下午3:24:20
	 * @see
	 */
	public void genBaseDataVer() {
		try {
			log.debug("delete file start...");
			String currDate = DateUtils.formatDateNoSep(new Date());
			// 删除两天前的数据更新包
			this.delFile(currDate);
			log.debug("delete file success...");
		} catch (Exception e) {
			log.debug("delete file error...");
			log.error(LogUtil.logFormat(e));
		}

	}

	/**
	 * 
	 * <p>
	 * TODO(对文件处理)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-7 下午6:44:53
	 * @param currDate
	 * @param currTime
	 * @see
	 */
	public void delFile(String currDate) {
		//文件路径
		String path = PropertyConstant.DATAVER_DIR
				+ (Integer.parseInt(currDate) - 2);
		log.debug("delete file path :" + path);
		File file = new File(path);
		//判断文件是否存在
		if (file.exists()) {
			//根据文件路径删除两天前的文件
			this.delFolder(file.getPath());
		}
	}
	

	/**
	 * 
	 * 删除文件
	 * 
	 * @param folderPath
	 * @throws IOException
	 */
	private void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			this.delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));
		}
	}

	/**
	 * 
	 * <p>TODO(删除指定文件夹下所有文件 param path 文件夹完整绝对路径)</p> 
	 * @author chengang
	 * @date 2013-4-9 上午11:24:02
	 * @param path
	 * @return
	 * @throws IOException
	 * @see
	 */
	private boolean delAllFile(String path) throws IOException {
		boolean flag = false;
		File file = new File(path);
		//判断文件路径是否存在
		//不存在,直接返回false
		if (!file.exists()) {
			return flag;
		}
		//文件存在,则判断文件是否为目录
		if (!file.isDirectory()) {
			return flag;
		}
		//获取目录下所有文件及目录
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

}