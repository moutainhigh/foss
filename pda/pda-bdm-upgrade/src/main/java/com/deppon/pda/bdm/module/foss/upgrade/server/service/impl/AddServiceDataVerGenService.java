package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.AddServiceEntity;

/**
 * 
 * TODO(增值服务基础数据服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2013-3-15 上午11:02:06,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2013-3-15 上午11:02:06
 * @since
 * @version
 */

public class AddServiceDataVerGenService extends
		AbstractBaseDataVerGenService<AddServiceEntity> {

	private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(创建基础数据全部更新地址)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-15 上午11:38:30
	 * @param currVer
	 * @param file
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#bulidAllPath(java.lang.String,
	 *      java.io.File)
	 */
	@Override
	public String bulidAllPath(String currVer, File file) {
		String filePath = file.getParent() + File.separator + currVer
				+ Constant.UNDERLINE_DELIMITER + Constant.BASE_DATA_VERSION_ALL
				+ File.separator + Constant.TABLE_PDA
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_ADD_SERVICE.toUpperCase() + ".txt";
		return filePath;
	}

	/**
	 * 
	 * <p>
	 * TODO(封装增值服务)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-15 上午11:41:01
	 * @param localDatas
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#dealLocalDatas(java.util.Set)
	 */
	@Override
	public String dealLocalDatas(Set<AddServiceEntity> localDatas) {
		StringBuffer buffer = new StringBuffer();
		// 把字段值封装到StringBuffer中
		for (AddServiceEntity entity : localDatas) {
			buffer.append(isNull(entity.getId())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getName())).append(Constant.VERTICAL_LINE);
			if(entity.getPrice() != null) {
				buffer.append(entity.getPrice()).append(Constant.VERTICAL_LINE);
			} else {
				buffer.append(Constant.BLANK).append(Constant.VERTICAL_LINE);
			}
			buffer.append(isNull(entity.getType())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getRemark())).append(Constant.VERTICAL_LINE);
			buffer.append(entity.getOperFlag()).append(Constant.VERTICAL_LINE);
			if (entity.getCreateDate() != null) {
				buffer.append(DateUtils.formatDateTime(entity.getCreateDate())).append(Constant.VERTICAL_LINE);
			} else {
				buffer.append(Constant.BLANK).append(Constant.VERTICAL_LINE);
			}
			if (entity.getUpdDate() != null) {
				buffer.append(DateUtils.formatDateTime(entity.getUpdDate())).append(Constant.VERTICAL_LINE);
			} else {
				buffer.append(Constant.BLANK).append(Constant.VERTICAL_LINE);
			}
			buffer.append(isNull(entity.getVersion())).append(Constant.NEWLINE);
		}
		return buffer.toString();
	}

	/**
	 * 
	 * <p>
	 * TODO(将增值服务基础数据写入文件)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-15 上午11:41:26
	 * @param filePath
	 * @param buffer
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#zipAllBaseDataFiles(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// 从配置文件得到字段数组
		String[] filed = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_ADD_SERVICE + Constant.UNDERLINE_DELIMITER
						+ Constant.FILED).split(Constant.VERTICAL_LINE);

		List<String> filedList = new ArrayList<String>();
		// 把字段数组添加到List集合
		for (int i = 0; i < filed.length; i++) {
			filedList.add(filed[i]);
		}
		// 从配置文件得到类型数组
		String[] type = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_ADD_SERVICE + Constant.UNDERLINE_DELIMITER
						+ Constant.TYPE).split(Constant.VERTICAL_LINE);

		List<String> typeList = new ArrayList<String>();
		// 类型数组设置到List集合
		for (int i = 0; i < type.length; i++) {
			typeList.add(type[i]);
		}

		OutputStream os = null;
		OutputStreamWriter out = null;

		try {
			File file = new File(filePath);
			// 得到上级文件夹
			File pf = file.getParentFile();
			// 判断文件和文件夹是否存在
			if (!file.exists()) {
				pf.mkdirs();
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			out = new OutputStreamWriter(os, "UTF-8");

			// 把字段List按格式写入到对应的文件中
			for (int i = 0; i < filedList.size(); i++) {
				out.write(filedList.get(i));
			}
			out.write(Constant.NEWLINE);
			// 把类型List按格式写入到对应的文件中
			for (int i = 0; i < typeList.size(); i++) {
				out.write(typeList.get(i));
			}
			out.write(Constant.NEWLINE);
			out.write(buffer);

		} catch (IOException e) {
			log.error(LogUtil.logFormat(e));
		} finally {
			try {
				// 清空缓存,关闭流
				if(out!=null){
				 out.flush();
				 out.close();
				}
				// 清空缓存,关闭流
				if(os!=null){
				 os.flush();
				 os.close();
				}
			} catch (IOException e) {
				log.error(LogUtil.logFormat(e));
			}
		}
	}

	/**
	 * 
	 * <p>
	 * TODO(创建增量基础数据地址并返回)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-15 上午11:41:54
	 * @param dataVer
	 * @param currVer
	 * @param file
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#bulidIncPath(java.lang.String,
	 *      java.lang.String, java.io.File)
	 */
	@Override
	public String bulidIncPath(String dataVer, String currVer, File file, String tabName) {
		String filePathInc = file.getParent() + File.separator + dataVer + "-"
				+ currVer + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + File.separator
				+ tabName + Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_ADD_SERVICE.toUpperCase() + ".txt";
		return filePathInc;

	}

	@Override
	public String getBaseDataClassName() {
		// TODO Auto-generated method stub
		return AddServiceEntity.class.getName();
	}

	@Override
	public String getUserType() {
		return Constant.USER_TYPE.ALL;
	} 
}
