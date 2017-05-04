package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.file.FileInfo;
import com.deppon.foss.framework.server.components.file.FileManager;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.framework.server.web.upload.IAttachementListener;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SystemHelpVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 系统帮助action
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午4:28:40
 */
public class SystemHelpAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4644737578775160569L;
	
    
    //文件输入流
    private InputStream inputStream;
	
	private File photo;
	
	//文件管理器
  	private FileManager fileManager;
  	
  	private IAttachementListener attachementListener;
	
    //文件存储相对路径
    private String relativePath;
    
    //实体id uuid
    private String id;
    
    //模块路径
    private String modulePath;
    
    //文件大小
    private String fileSize;
    
    //文件扩展名
    private String fileType;
    
  //文件名称
    private String fileName;
        
    /**
	 * wabApp目录路径
	 */
	private String wabAppDir; 
    
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemHelpAction.class);
	
	/**
	 * 系统帮助vo
	 */
	private SystemHelpVo systemHelpVo;
	
	/**
	 * 系统帮助service
	 */
	private ISystemHelpService systemHelpService;

	/**
	 * 查询系统帮助信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:36:05
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	@JSON
	public String querySystemHelpList(){
		
		try{
			// 获取总的记录数
			long totalCount = systemHelpService.queryRecordCount(systemHelpVo.getSystemHelpDto());
			
			if(totalCount>0){
			
				// 获取总的系统帮助信息
				List<SystemHelpEntity> systemHelpEntitys = systemHelpService
						.querySystemHelpEntity(systemHelpVo.getSystemHelpDto(), this.limit, this.start);
				// 设置返回前台的信息
				systemHelpVo.setSystemHelpEntitys(systemHelpEntitys);
				//设置总条数
				this.setTotalCount(totalCount);
			}
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增系统帮助
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:40:58
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@JSON
	public String addSystemHelp(){
		
		try{
			
			SystemHelpEntity systemHelpEntity = systemHelpVo.getSystemHelpDto().getSystemHelpEntity();
			// 获取当前的用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户
			systemHelpEntity.setCreateUser(currentInfo.getEmpCode());
			systemHelpEntity.setModifyUser(currentInfo.getEmpCode());

			// 添加信息
			systemHelpService.addSystemHelpEntity(systemHelpEntity);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
		return returnSuccess();
	}
	

	/**
	 * 查询符合条件的公告通过id
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-8 下午5:16:41
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	@JSON
	public String querySystemHelpById() {
		try {
			SystemHelpEntity systemHelpEntity = systemHelpService.
					querySystemHelpEntityById(systemHelpVo.getSystemHelpDto().getSystemHelpEntity().getId());
			//新建dto 
			SystemHelpDto systemHelpDto =new SystemHelpDto();
			//设置 entity
			systemHelpDto.setSystemHelpEntity(systemHelpEntity);
			//设置dto
			systemHelpVo.setSystemHelpDto(systemHelpDto);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 修改公告信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:30:59
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@JSON
	public String updateSystemHelp() {
		try {
			// 获取传过来的数据
			SystemHelpEntity systemHelpEntity = systemHelpVo.getSystemHelpDto().getSystemHelpEntity();
			// 获取当前用户
			CurrentInfo user = FossUserContext.getCurrentInfo();
			// 设置修改的公告信息用户
			systemHelpEntity.setModifyUser(user.getEmpCode());

			systemHelpService.upadteSystemHelpEntity(systemHelpEntity);

			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}


	/**
	 * 作废信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:30:41
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@JSON
	public String deleteSystemHelp() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 废除
			systemHelpService.deleteSystemHelpEntityById(systemHelpVo.getIdlist(),
					currentInfo.getEmpCode());

			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 文件上传
	 * @return
	 */
	public String uploadFile() {
		
		try {
			FileInfo fileInfo = fileManager.create(photo);
			relativePath = fileInfo.getRelativePath();
			AttachementEntity entity = new AttachementEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setRelativePath(relativePath);
			entity.setFileName(fileName);
			entity.setFileSize(fileSize);
			entity.setFileType("JPG");
			entity.setModulePath(modulePath);
			entity.setRelatedKey("");
			if(attachementListener != null) {
			    attachementListener.addAttachementInfo(entity);
			}
		} catch(BusinessException e) {
			return returnError(e);
		}
    	return returnSuccess();
    }
	
	/**
	 * 图片预览
	 * @return
	 */
    public String reviewSystemHelpImg() {
    	if(relativePath != null && !relativePath.equals("")) {
		    File f = fileManager.read(relativePath);
			FileInputStream objInputStream = null;
		    try {
		    	objInputStream = new FileInputStream(f);
		    	inputStream = new BufferedInputStream(objInputStream);
		    	return returnSuccess();
			} catch (FileNotFoundException e) {
				return returnError("The file can not be found");
			}
    	} else {
    		return returnError("The relative path of the file is invalidate");
    	}
    }
	
	/**
	 * @get
	 * @return systemHelpVo
	 */
	public SystemHelpVo getSystemHelpVo() {
		/*
		 * @get
		 * @return systemHelpVo
		 */
		return systemHelpVo;
	}

	
	/**
	 * @set
	 * @param systemHelpVo
	 */
	public void setSystemHelpVo(SystemHelpVo systemHelpVo) {
		/*
		 *@set
		 *@this.systemHelpVo = systemHelpVo
		 */
		this.systemHelpVo = systemHelpVo;
	}

	
	/**
	 * @set
	 * @param systemHelpService
	 */
	public void setSystemHelpService(ISystemHelpService systemHelpService) {
		/*
		 *@set
		 *@this.systemHelpService = systemHelpService
		 */
		this.systemHelpService = systemHelpService;
	}
	


	
	/**
	 * @set
	 * @param photo
	 */
	public void setPhoto(File photo) {
		/*
		 *@set
		 *@this.photo = photo
		 */
		this.photo = photo;
	}
	


	


	
	/**
	 * @set
	 * @param fileManager
	 */
	public void setFileManager(FileManager fileManager) {
		/*
		 *@set
		 *@this.fileManager = fileManager
		 */
		this.fileManager = fileManager;
	}

	/**
	 * @set
	 * @param attachementListener
	 */
	public void setAttachementListener(IAttachementListener attachementListener) {
		/*
		 *@set
		 *@this.attachementListener = attachementListener
		 */
		this.attachementListener = attachementListener;
	}

	
	/**
	 * @get
	 * @return relativePath
	 */
	public String getRelativePath() {
		/*
		 * @get
		 * @return relativePath
		 */
		return relativePath;
	}

	
	/**
	 * @set
	 * @param relativePath
	 */
	public void setRelativePath(String relativePath) {
		/*
		 *@set
		 *@this.relativePath = relativePath
		 */
		this.relativePath = relativePath;
	}

	
	/**
	 * @get
	 * @return id
	 */
	public String getId() {
		/*
		 * @get
		 * @return id
		 */
		return id;
	}

	
	/**
	 * @set
	 * @param id
	 */
	public void setId(String id) {
		/*
		 *@set
		 *@this.id = id
		 */
		this.id = id;
	}

	
	/**
	 * @get
	 * @return modulePath
	 */
	public String getModulePath() {
		/*
		 * @get
		 * @return modulePath
		 */
		return modulePath;
	}

	
	/**
	 * @set
	 * @param modulePath
	 */
	public void setModulePath(String modulePath) {
		/*
		 *@set
		 *@this.modulePath = modulePath
		 */
		this.modulePath = modulePath;
	}

	
	/**
	 * @get
	 * @return fileSize
	 */
	public String getFileSize() {
		/*
		 * @get
		 * @return fileSize
		 */
		return fileSize;
	}

	
	/**
	 * @set
	 * @param fileSize
	 */
	public void setFileSize(String fileSize) {
		/*
		 *@set
		 *@this.fileSize = fileSize
		 */
		this.fileSize = fileSize;
	}

	
	/**
	 * @get
	 * @return fileType
	 */
	public String getFileType() {
		/*
		 * @get
		 * @return fileType
		 */
		return fileType;
	}

	
	/**
	 * @set
	 * @param fileType
	 */
	public void setFileType(String fileType) {
		/*
		 *@set
		 *@this.fileType = fileType
		 */
		this.fileType = fileType;
	}

	
	/**
	 * @set
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 *@set
		 *@this.fileName = fileName
		 */
		this.fileName = fileName;
	}

	
	/**
	 * @get
	 * @return wabAppDir
	 */
	public String getWabAppDir() {
		/*
		 * @get
		 * @return wabAppDir
		 */
		return wabAppDir;
	}

	
	/**
	 * @set
	 * @param wabAppDir
	 */
	public void setWabAppDir(String wabAppDir) {
		/*
		 *@set
		 *@this.wabAppDir = wabAppDir
		 */
		this.wabAppDir = wabAppDir;
	}

	
	/**
	 * @get
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		/*
		 * @get
		 * @return inputStream
		 */
		return inputStream;
	}

	
	/**
	 * @set
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}

	
	
	
}
