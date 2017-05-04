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
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.NetGroupEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

/**
 * 
 * TODO(基础数据-网点组服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2013-5-17 下午4:20:39,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2013-5-17 下午4:20:39
 * @since
 * @version
 */

public class NetGroupDataVerGenService extends
		AbstractBaseDataVerGenService<NetGroupEntity> {

	private Logger log = Logger.getLogger(getClass());

	@Override
	public String bulidAllPath(String currVer, File file) {
		// 路径
		String filePath = file.getParent() + File.separator + currVer
				+ Constant.UNDERLINE_DELIMITER + Constant.BASE_DATA_VERSION_ALL
				+ File.separator + Constant.TABLE_PDA
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_NET_GROUP.toUpperCase() + ".txt";
		return filePath;
	}

	@Override
	public String dealLocalDatas(Set<NetGroupEntity> localDatas) {
		StringBuffer buffer = new StringBuffer();
		// 把字段值封装到StringBuffer中
		for (NetGroupEntity entity : localDatas) {
			buffer.append(isNull(entity.getId()))
					.append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getOrgCode())).append(
					Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getOrgType())).append(
					Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getRouteVirtualCode())).append(
					Constant.NEWLINE);
			// buffer.append(isNull(entity.getOperFlag())).append(Constant.NEWLINE);
		}
		return buffer.toString();
	}

	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// 从配置文件得到字段数组
		String[] filed = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_NET_GROUP + Constant.UNDERLINE_DELIMITER
						+ Constant.FILED).split(Constant.VERTICAL_LINE);

		List<String> filedList = new ArrayList<String>();
		// 把字段数组添加到List集合
		for (int i = 0; i < filed.length; i++) {
			filedList.add(filed[i]);
		}
		// 从配置文件得到类型数组
		String[] type = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_NET_GROUP + Constant.UNDERLINE_DELIMITER
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
	public String bulidIncPath(String dataVer, String currVer, File file,
			String tabName) {
		String filePathInc = file.getParent() + File.separator + dataVer + "-"
				+ currVer + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + File.separator + tabName
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_NET_GROUP.toUpperCase() + ".txt";
		return filePathInc;

	}

	@Override
	public String getBaseDataClassName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getUserType() {
		return Constant.USER_TYPE.DRIVER;
	}
	
	@Override
	public QueryLocalListDto initBaseDataParam(AsyncMsg asyncMsg,
			QueryLocalListDto dto) {
		//零担  根据
		// 1通过deptCode 查询出ID
		String deptId = systemFunDao.getUserInfo(asyncMsg.getUserCode())
				.getDeptId();
		// 2通过ID查询出最顶级车队部门Code
		String topFleetCode = this.getTopFleetCodeByDeptId(deptId);
		// 3.查找顶级车队的所服务的外场
		String sourceTransCenter = systemFunDao.queryTransCenter(topFleetCode);
		//4通过外场编码 查找驻地营业部编码
		String residentdeptCode = systemFunDao.queryResidentDeptCode(sourceTransCenter);
		if(residentdeptCode==null){
			residentdeptCode="";
		}
		dto.setDeptCode(residentdeptCode);
		return dto;
	}

}
