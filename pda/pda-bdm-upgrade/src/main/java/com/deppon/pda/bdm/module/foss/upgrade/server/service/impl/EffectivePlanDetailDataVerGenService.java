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
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.EffectivePlanDetailEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

/**
 * 
 * TODO(时效方案明细基础数据服务类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:wenwuneng,date:2013-11-05 上午11:02:06,content:TODO
 * </p>
 * 
 * @author wenwuneng
 * @date 2013-11-05 上午11:02:06
 * @since
 * @version
 */

public class EffectivePlanDetailDataVerGenService extends
		AbstractBaseDataVerGenService<EffectivePlanDetailEntity> {

	private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(创建基础数据全部更新地址)
	 * </p>
	 * 
	 * <p style="display:none">
	 * version:V1.0,author:wenwuneng,date:2013-11-05 上午11:02:06,content:TODO
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-11-05 上午11:02:06
	 * @since
	 * @version
	 */
	@Override
	public String bulidAllPath(String currVer, File file) {
		String filePath = file.getParent() + File.separator + currVer
				+ Constant.UNDERLINE_DELIMITER + Constant.BASE_DATA_VERSION_ALL
				+ File.separator + Constant.TABLE_PDA
				+ Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL.toUpperCase() + ".txt";
		return filePath;
	}

	/**
	 * 
	 * <p>
	 * TODO(封装基础数据方法)
	 * </p>
	 * <p style="display:none">
	 * version:V1.0,author:wenwuneng,date:2013-11-05 上午11:02:06,content:TODO
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-11-05 上午11:02:06
	 * @since
	 * @version
	 */
	@Override
	public String dealLocalDatas(Set<EffectivePlanDetailEntity> localDatas) {
		StringBuffer buffer = new StringBuffer();
		// 把字段值封装到StringBuffer中
		for (EffectivePlanDetailEntity entity : localDatas) {
			buffer.append(isNull(entity.getId())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getProductCode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getDeptRegionCode())).append(Constant.VERTICAL_LINE);
			buffer.append(isNull(entity.getArrvRegionCode())).append(Constant.NEWLINE);
		}
		return buffer.toString();
	}

	/**
	 * 
	 * <p>
	 * TODO(将基础数据写入文件)
	 * </p>
	 * 
	 	 * version:V1.0,author:wenwuneng,date:2013-11-05 上午11:02:06,content:TODO
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-11-05 上午11:02:06
	 * @since
	 * @version
	 */
	@Override
	public void zipAllBaseDataFiles(String filePath, String buffer) {
		// 从配置文件得到字段数组
		String[] filed = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
						+ Constant.UNDERLINE_DELIMITER + Constant.FILED).split(
				Constant.VERTICAL_LINE);

		List<String> filedList = new ArrayList<String>();
		// 把字段数组添加到List集合
		for (int i = 0; i < filed.length; i++) {
			filedList.add(filed[i]);
		}
		// 从配置文件得到类型数组
		String[] type = PropertyConstant.PRO_DATA_MAP.get(
				Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
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
	 	 * version:V1.0,author:wenwuneng,date:2013-11-05 上午11:02:06,content:TODO
	 * </p>
	 * 
	 * @author wenwuneng
	 * @date 2013-11-05 上午11:02:06
	 * @since
	 * @version
	 */
	@Override
	public String bulidIncPath(String dataVer, String currVer, File file, String tabName) {
		String filePathInc = file.getParent() + File.separator + dataVer + "-"
				+ currVer + Constant.UNDERLINE_DELIMITER
				+ Constant.BASE_DATA_VERSION_INC + File.separator
				+ tabName + Constant.UNDERLINE_DELIMITER
				+ Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL.toUpperCase() + ".txt";
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
		//5通过驻地营业部查找始发区域
		String deptCode=residentdeptCode;
		List<String> regionCodes =systemFunDao.getRegionCodeByDeptCode(deptCode);
		String regionCode=null;
		if(regionCodes.isEmpty()){//根据部门编码查找部门信息
			DeptEntity dept=systemFunDao.getDeptInfoByCode(deptCode);
			//根据部门省市区，进行查找区域编码
			if(dept!=null){
				regionCode=this.getRegionCode(dept);	
			}
			
		}else{
			regionCode=regionCodes.get(0);
		}
		
		if(regionCode==null){
			regionCode=" ";
		}
		dto.setDeptCode(regionCode);
		return dto;
	}
	
	/**
	 根据部门省市区，进行查找区域编码
	 */
	public String getRegionCode(DeptEntity dept){
		String regionCode=null;
		//进行区线查找时效区域编码查找
		if(dept.getDeptCounty()!=null && !dept.getDeptCounty().equals("")){
			regionCode=systemFunDao.getRegionByCount(dept.getDeptCounty());
		}
		if(regionCode==null && dept.getDeptCity()!=null && !dept.getDeptCity().equals("") ){
			regionCode=systemFunDao.getRegionByCity(dept.getDeptCity());
		}
		if(regionCode==null && dept.getDeptProvince()!=null && !dept.getDeptProvince().equals("") ){
			regionCode=systemFunDao.getRegionByProvince(dept.getDeptProvince());
		}
		
		return regionCode;
	}
 
}
