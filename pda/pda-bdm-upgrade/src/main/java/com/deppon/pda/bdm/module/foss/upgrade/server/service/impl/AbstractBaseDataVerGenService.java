package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IBaseDataVerGenDao;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao;
import com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

//@Transactional
public abstract class AbstractBaseDataVerGenService<T extends BaseEntity> implements
		IBaseDataVerGenService<T> {

	private Logger log = Logger.getLogger(getClass());

	private IBaseDataVerGenDao<T> baseDataVerGenDao;
	
	
	protected ISystemFunDao systemFunDao;
	
	protected DeptCache deptCache;
	
	@Override
	public abstract String getBaseDataClassName();
	
	@Override
	public QueryLocalListDto initBaseDataParam(AsyncMsg asyncMsg,QueryLocalListDto dto){
		return dto;
	}

	@Override
	public void genBaseDataFile(QueryLocalListDto dto) {
		List<T> entityList = null;
		try {
			// 如果verName等于all,则全部更新
			if (Constant.BASE_DATA_VERSION_ALL.equals(dto.getFlag())) {
				//查询pda服务器端基础数据,得到集合
				long start = System.currentTimeMillis();
				entityList = this.queryLocalEntityList(dto);
				log.debug("-------" + (System.currentTimeMillis() - start)
						+ "ms-------");
				//将List集合设置到Set集合中,去除相同数据
				Set<T> set = new HashSet<T>(entityList);
				//获得文件生成路径
				String filePath = this.bulidAllPath(dto.getCurrVer(), dto.getFile());
				//遍历Set集合,封装成String类型
				String entityBuffer = this.dealLocalDatas(set);
				//生成全部基础数据文件
				this.zipAllBaseDataFiles(filePath, entityBuffer);
			} else {
				long start = System.currentTimeMillis();
				//查询pda服务器端基础数据,得到集合
				entityList = this.queryLocalEntityList(dto);
				log.debug("-------" + (System.currentTimeMillis() - start)
						+ "ms-------");
				//将List集合设置到Set集合中,去除相同数据
				Set<T> set = new HashSet<T>(entityList);
				//获得文件生成路径
				String filePath = this.bulidIncPath(dto.getDateVer(), dto.getCurrVer(), dto.getFile(), Constant.TABLE_PDA);
				//遍历Set集合,封装成String类型
				String entityBuffer = this.dealLocalDatas(set);
				//生成增量基础数据文件
				this.zipAllBaseDataFiles(filePath, entityBuffer);
				log.debug(getBaseDataClassName());
				if(getBaseDataClassName().equals("")) {
					List<T> delList = this.queryLocalDelList(dto);
					Set<T> delSet = new HashSet<T>(delList);
					if(!delSet.isEmpty()){
						String delFilePath = this.bulidIncPath(dto.getDateVer(), dto.getCurrVer(), dto.getFile(), Constant.TABLE_PDA_DEL);
						String delBuffer = this.dealLocalDatasByDel(delSet);
						this.zipAllBaseDataDelFiles(delFilePath, delBuffer);
						delSet.clear();
						delSet = null;
					}
				}
				
			}
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));
		}finally{
			if(entityList != null){
				entityList.clear();
				entityList = null;
			}
		}
		
		
	}

	/**
	 * <p>TODO(查询本地数据(除删除数据))</p> 
	 * @author chengang
	 * @date 2013-6-13 上午10:21:41
	 * @param queryDto
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#queryLocalEntityList(com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto)
	 */
	@Override
	public List<T> queryLocalEntityList(QueryLocalListDto queryDto) {
		BaseDataModel model = new BaseDataModel();
		model.setDeptCode(queryDto.getDeptCode());
		model.setUserCode(queryDto.getUserCode());
		if (Constant.BASE_DATA_VERSION_INC.equals(queryDto.getFlag())) {
			log.debug("---beginTime:"+getCalendarTime(queryDto.getDateVer())+"---");
			log.debug("---endTime:"+getCalendarTime(queryDto.getCurrVer())+"---");
			model.setStartTime(getCalendarTime(queryDto.getDateVer()));
			model.setEndTime(getCalendarTime(queryDto.getCurrVer()));
		}
		return baseDataVerGenDao.queryLocalDatas(model);
	}
	
	/**
	 * 
	 * <p>TODO(将版本号转换成Date类型,供查询增量基础数据)</p> 
	 * @author chengang
	 * @date 2013-6-29 下午4:43:11
	 * @param vesrion
	 * @return
	 * @see
	 */
	private Date getCalendarTime(String vesrion) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(vesrion));
		return calendar.getTime();
	}
	/**
	 * <p>TODO(查询基础数据表中删除数据)</p> 
	 * @author chengang
	 * @date 2013-6-13 上午10:24:15
	 * @param queryDto
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#queryLocalDelList(com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto)
	 */
	@Override
	public List<T> queryLocalDelList(QueryLocalListDto queryDto) {
		BaseDataModel model = new BaseDataModel();
		model.setDeptCode(queryDto.getDeptCode());
		model.setUserCode(queryDto.getUserCode());
		model.setStartTime(getCalendarTime(queryDto.getDateVer()));
		model.setEndTime(getCalendarTime(queryDto.getCurrVer()));
		return baseDataVerGenDao.queryLocalDatasByDel(model);
	}

	/**
	 * 
	 * <p>TODO(生成完整基础数据文件路径)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:13:58
	 * @param currVer
	 * @param file
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#bulidAllPath(java.lang.String, java.io.File)
	 */
	@Override
	public abstract String bulidAllPath(String currVer, File file);

	/**
	 * 
	 * <p>TODO(生成增量基础数据文件路径)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:14:10
	 * @param dataVer
	 * @param currVer
	 * @param file
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#bulidIncPath(java.lang.String, java.lang.String, java.io.File)
	 */
	@Override
	public abstract String bulidIncPath(String dataVer, String currVer,
			File file, String tabName);

	/**
	 * 
	 * <p>TODO(处理本地数据)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:14:20
	 * @param localDatas
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#dealLocalDatas(java.util.Set)
	 */
	@Override
	public abstract String dealLocalDatas(Set<T> localDatas);
	
	@Override
	public String dealLocalDatasByDel(Set<T> localDatas) {
		StringBuffer sb = new StringBuffer();
		for (T t : localDatas) {
			sb.append(t.getId()).append(Constant.NEWLINE);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * <p>TODO(生成基础数据文件)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:14:48
	 * @param filePath
	 * @param buffer 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#zipAllBaseDataFiles(java.lang.String, java.lang.String)
	 */
	public abstract void zipAllBaseDataFiles(String filePath, String buffer);

	/**
	 * 
	 * <p>TODO(替换属性中的换行符、竖线)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:14:56
	 * @param attribute
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#replaceAttribute(java.lang.String)
	 */
	@Override
	public String replaceAttribute(String attribute) {
		String str = attribute
				.replace(Constant.FORMAT_ENTER, Constant.FORMAT_TRANSFORM)
				.replace(Constant.FORMAT_NEWLINE, Constant.FORMAT_TRANSFORM)
				.replace(Constant.VERTICAL_LINE, Constant.FORMAT_VIRGULE);
		return str;
	}
	
	/**
	 * 
	 * <p>TODO(查询基础数据是否有更新)</p> 
	 * @author chengang
	 * @date 2013-4-8 下午3:15:12
	 * @param dataVer
	 * @param currVer
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.service.IBaseDataVerGenService#queryLocalIncDataList(java.lang.String, java.lang.String)
	 */
	/*@Override
	public Integer queryLocalIncDataList(String dataVer, String currVer) {
		BaseDataModel model = new BaseDataModel();
		model.setStartTime(getCalendarTime(dataVer));
		model.setEndTime(getCalendarTime(currVer));
		return baseDataVerGenDao.queryLocalIncDataList(model);
	}*/

	/**
	 * 
	 * <p>
	 * TODO(判断属性是否为空)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-28 下午2:55:41
	 * @param object
	 * @return
	 * @see
	 */
	@Override
	public String isNull(String object) {
		// 如果属性值不为null,则替换属性中的换行符、竖线
		if (object != null) {
			return this.replaceAttribute(object);
		}
		// 如果属性值等于null,直接返回空格
		return Constant.BLANK;
	}
	
	/**
	 * 
	 * <p>TODO(生成删除数据文件)</p> 
	 * @author chengang
	 * @date 2013-6-13 下午2:37:13
	 * @param filePath
	 * @param buffer
	 * @see
	 */
	public void zipAllBaseDataDelFiles(String filePath, String buffer) {
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

	public String getTopFleetCodeByDeptId(String deptId) {
		while (true) {
			if (StringUtils.isEmpty(deptId)) {
				return null;
			}
			com.deppon.pda.bdm.module.core.shared.domain.DeptEntity dept = deptCache
					.getDept(deptId);
			if ("Y".equals(dept.getIsTopFleet())) {
				return dept.getDeptCode();
			}
			if (StringUtils.isEmpty(dept.getParentOrgCode())
					|| dept.getParentOrgCode().equals(dept.getId())) {
				return null;
			}
			deptId = dept.getParentOrgCode();
		}

	}
	public void setBaseDataVerGenDao(IBaseDataVerGenDao<T> baseDataVerGenDao) {
		this.baseDataVerGenDao = baseDataVerGenDao;
	}

	public void setSystemFunDao(ISystemFunDao systemFunDao) {
		this.systemFunDao = systemFunDao;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}
	
}
