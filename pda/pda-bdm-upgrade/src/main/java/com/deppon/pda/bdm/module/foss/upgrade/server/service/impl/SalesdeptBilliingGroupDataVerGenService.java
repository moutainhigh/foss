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
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.SalesdeptBilliingGroupEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

/**
 * 
 * TODO(基础数据服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:wenwuneng,date:2013-8-10 下午14:02:06,content:TODO
 * </p>
 * 
 * @author wenwuneng
 * @date 2013-8-10 下午14:02:06
 * @since
 * @version
 */

public class SalesdeptBilliingGroupDataVerGenService extends
		AbstractBaseDataVerGenService<SalesdeptBilliingGroupEntity> {

	private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(创建基础数据全部更新地址)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-10 下午14:02:06
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
				+ Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP.toUpperCase()
				+ ".txt";
		return filePath;
	}

	/**
	 * 
	 * <p>
	 * TODO(封装基础数据)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-10 下午14:02:06
	 * @param localDatas
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#dealLocalDatas(java.util.Set)
	 */
	@Override
	public String dealLocalDatas(Set<SalesdeptBilliingGroupEntity> localDatas) {
		StringBuffer buffer = new StringBuffer();
		// 把字段值封装到StringBuffer中
		for (SalesdeptBilliingGroupEntity entity : localDatas) {
			// 目前只需要当前开单组能够开到的营业部编码和名称
			// 把虚拟编码作为ID
			buffer.append(isNull(entity.getVirtualcode())).append(
					Constant.VERTICAL_LINE);
			// buffer.append(isNull(entity.getVirtualcode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getSalesdeptcode())).append(
					Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getSalesdeptname())).append(
					Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getBillinggroupcode())).append(
					Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getBillinggroupname())).append(
					Constant.NEWLINE);
		}
		return buffer.toString();
	}

	/**
	 * 
	 * <p>
	 * TODO(将基础数据写入文件)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-10 下午14:02:06
	 * @param filePath
	 * @param buffer
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#zipAllBaseDataFiles(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// 从配置文件得到字段数组
		String[] filed = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
						+ Constant.UNDERLINE_DELIMITER + Constant.FILED).split(
				Constant.VERTICAL_LINE);

		List<String> filedList = new ArrayList<String>();
		// 把字段数组添加到List集合
		for (int i = 0; i < filed.length; i++) {
			filedList.add(filed[i]);
		}
		// 从配置文件得到类型数组
		String[] type = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
						+ Constant.UNDERLINE_DELIMITER + Constant.TYPE).split(
				Constant.VERTICAL_LINE);

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

	/**
	 * 
	 * <p>
	 * TODO(创建增量基础数据地址并返回)
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-8-10 下午14:02:06
	 * @param dataVer
	 * @param currVer
	 * @param file
	 * @return
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#bulidIncPath(java.lang.String,
	 *      java.lang.String, java.io.File)
	 */
	@Override
	public String bulidIncPath(String dataVer, String currVer, File file,
			String tabName) {
		String filePathInc = file.getParent() + File.separator + dataVer + "-"
				+ currVer + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + File.separator + tabName
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP.toUpperCase()
				+ ".txt";
		return filePathInc;
	}

	@Override
	public String getBaseDataClassName() {
		return "";
	}

	@Override
	public String getUserType() {
		return Constant.USER_TYPE.DRIVER;
	}
	/**
	 * 
	 * <p>TODO(车队司机按开单组下拉出发部门)</p> 
	 * @author Administrator
	 * @date 2013-10-28 下午6:11:36
	 * @param asyncMsg
	 * @param userType
	 * @param dto
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.impl.AbstractBaseDataVerGenService#initBaseDataParam(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.String, com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto)
	 */
	@Override
	public QueryLocalListDto initBaseDataParam(AsyncMsg asyncMsg,
			QueryLocalListDto dto) {
		// 1通过deptCode 查询出ID
		String deptId = systemFunDao.getUserInfo(asyncMsg.getUserCode())
				.getDeptId();
		// 2通过ID查询出最顶级部门Code
		String topFleetCode = this.getTopFleetCodeByDeptId(deptId);
		// 3通过顶级Code查询出对应的开单组Code
		String billGroupCode = systemFunDao
				.getBillGroupCodeByFleetCode(topFleetCode);
		// 封装开单组code 进入实体 查询出该开单组对应的开单部门
		dto.setDeptCode(billGroupCode);
		return dto;
	}

}
