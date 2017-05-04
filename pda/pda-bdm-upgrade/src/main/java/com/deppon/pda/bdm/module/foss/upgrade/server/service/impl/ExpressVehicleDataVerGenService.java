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
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.ExpressVehicleEntity;

/**
 * 
  * @ClassName ExpressVehicleDataVerGenService 
  * @Description TODO 同步用户基础数据服务类
  * @author mt 
  * @date 2013-8-16 上午10:29:12
 */
public class ExpressVehicleDataVerGenService extends
		AbstractBaseDataVerGenService<ExpressVehicleEntity> {

	private Logger log = Logger.getLogger(getClass());

	@Override
	public String bulidAllPath(String currVer, File file) {
		// 路径
		String filePath = file.getParent() + File.separator + currVer
				+ Constant.UNDERLINE_DELIMITER + Constant.BASE_DATA_VERSION_ALL
				+ File.separator + Constant.TABLE_PDA
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_EXPRESS_VEHICLE.toUpperCase() + ".txt";
		return filePath;
	}

	@Override
	public String dealLocalDatas(Set<ExpressVehicleEntity> localDatas) {
		StringBuffer buffer = new StringBuffer();
		// 把字段值封装到StringBuffer中
		for (ExpressVehicleEntity expressVehicleEntity : localDatas) {
			buffer.append(isNull(expressVehicleEntity.getId())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getVehicleNo())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getVehiclelenghtCode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getEmpCode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getOrgCode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getMobilePhone())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getVersionNo())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getOperFlag())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(expressVehicleEntity.getDescription())).append(Constant.NEWLINE);
			//buffer.append(isNull(cityEntity.getOperFlag())).append(Constant.NEWLINE);
		}
		return buffer.toString();
	}

	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// 从配置文件得到字段数组
		String[] filed = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_EXPRESS_VEHICLE + Constant.UNDERLINE_DELIMITER
						+ Constant.FILED).split(Constant.VERTICAL_LINE);

		List<String> filedList = new ArrayList<String>();
		// 把字段数组添加到List集合
		for (int i = 0; i < filed.length; i++) {
			filedList.add(filed[i]);
		}
		// 从配置文件得到类型数组
		String[] type = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_EXPRESS_VEHICLE + Constant.UNDERLINE_DELIMITER
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
				// 清空缓存
				out.flush();
				os.flush();
				// 关闭流
				out.close();
				os.close();
			} catch (IOException e) {
				log.error(LogUtil.logFormat(e));
			}
		}
	}

	@Override
	public String bulidIncPath(String dataVer, String currVer, File file, String tabName) {
		String filePathInc = file.getParent() + File.separator + dataVer + "-"
				+ currVer + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + File.separator
				+ tabName + Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_EXPRESS_VEHICLE.toUpperCase() + ".txt";
		return filePathInc;

	}

	@Override
	public String getBaseDataClassName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getUserType() {
		return Constant.USER_TYPE.COURIER;
	}
}
