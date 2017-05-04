package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IrecordImportExcelFileInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IrecordImportExcelFileInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RecordImportExcelFileInfoException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 保存各个模块右键导出EXCEL文件的相关信息
 * 用户查询界面查询且下载相关的文件信息
 * 
 * @author:WangPeng
 * @date:2013-06-20 2:46PM
 *
 */
public class RecordImportExcelFileInfoService implements
		IrecordImportExcelFileInfoService {
	
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(RecordImportExcelFileInfoService.class); 

	/**
	 * 注入保存数据的 Dao
	 */
	private IrecordImportExcelFileInfoDao recordImportExcelFileInfoDao;
	
	public void setRecordImportExcelFileInfoDao(
			IrecordImportExcelFileInfoDao recordImportExcelFileInfoDao) {
		this.recordImportExcelFileInfoDao = recordImportExcelFileInfoDao;
	}

	/**
	 * 保存需要下载的资源信息
	 * @author:WangPeng
	 * @date:2013-6-20下午2:46:18
	 * @param:String[] info
	 * @return: void
	 * 
	 */
	@Override
	@Transactional
	public int saveImportExcelFileInfo(DownloadInfoEntity entity)throws RecordImportExcelFileInfoException {
	//返回状态 1代表成功 -1代表失败
	int result = FossConstants.FAILURE;
    //校验传递过来的信息
    boolean flag = verifyInfo(entity);
    //下载excel文件相关信息的实体对象
 	 if(flag){
 		 entity.setId(UUID.randomUUID().toString());
 		 entity.setActive(FossConstants.ACTIVE);
// 		 entity.setCreator(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
         entity.setCreateTime(new Date());
         entity.setModifyTime(new Date(NumberConstants.ENDTIME));
 		 
         LOGGER.debug(entity.getEmpCode());
         LOGGER.debug(entity.getFileLoadPath());
         LOGGER.debug(entity.getFileName());
 		 //校验通过，保存需要下载的数据信息
 		result = recordImportExcelFileInfoDao.saveNeedImportExcelFileInfo(entity);
 		return result >NumberConstants.ZERO?FossConstants.SUCCESS:result;

 	 }
 	 else{
 		return result;
 	 }
	}
	
	/**
	 * 校验通过接口传过来的信息
	 * @author:WangPeng
	 * @date:2013-6-20下午2:53:32
	 * @param:String[] info
	 * @return: boolean
	 */
	private boolean verifyInfo(DownloadInfoEntity entity) {
	    boolean flag = true;
	    if(null == entity){
	    	throw new RecordImportExcelFileInfoException("传入的对象为空！");
	    }
	    if(StringUtils.isBlank(entity.getEmpCode())){
	    	throw new RecordImportExcelFileInfoException("当前登录人的工号为空");
	    	
	    }else if(StringUtils.isBlank(entity.getOrgCode())){
	    	throw new RecordImportExcelFileInfoException("当前登录人的所属部门编码为空");
	    	
	    }else if(StringUtils.isBlank(entity.getFileName())){
	    	throw new RecordImportExcelFileInfoException("下载的文件名称为空");
	    	
	    }else if(StringUtils.isBlank(entity.getFileLoadPath())){
	    	throw new RecordImportExcelFileInfoException("文件下载路径为空");
	    	
	    }
		return flag;
	}

}
