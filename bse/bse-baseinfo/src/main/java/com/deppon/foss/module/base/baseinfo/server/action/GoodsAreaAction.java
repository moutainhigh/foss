/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/GoodsAreaAction.java
 * 
 * FILE NAME        	: GoodsAreaAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GoodsAreaExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.GoodsAreaVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.copyUtil.XlsImpUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 库区ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class GoodsAreaAction extends AbstractAction {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OriginatingLineAction.class);
    
	private static final long serialVersionUID = 2883644272419312426L;

	// 前后台传的参数
	private GoodsAreaVo goodsAreaVo = new GoodsAreaVo();
	public GoodsAreaVo getGoodsAreaVo() {
		return goodsAreaVo;
	}

	public void setGoodsAreaVo(GoodsAreaVo goodsAreaVo) {
		this.goodsAreaVo = goodsAreaVo;
	}
	//库区service
	private IGoodsAreaService goodsAreaService;
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	/**
	 * .
	 * <p>
	 * 查询所有的库区根据条件<br/>
	 * 方法名：queryGoodsAreaByCondition
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String queryGoodsAreaByCondition() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			String currentOrgCode = user.getEmployee().getOrgCode();
			this.setTotalCount(goodsAreaService.countGoodsAreaByCondition(goodsAreaVo.getGoodsAreaEntity(),userCode, currentOrgCode));
			List<GoodsAreaEntity> goodsAreaEntityList = goodsAreaService.queryGoodsAreaByCondition(goodsAreaVo.getGoodsAreaEntity(), start, limit,userCode, currentOrgCode);
			goodsAreaVo.setGoodsAreaEntityList(goodsAreaEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 批量作废库区信息<br/>
	 * 方法名：deleteGoodsArea
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String deleteGoodsArea() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			goodsAreaService.deleteGoodsAreas(goodsAreaVo.getGoodsAreaVirtualCodes(), userCode);
			return returnSuccess(MessageType.DELETE_GOODSAREA_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改库区信息<br/>
	 * 方法名：updateGoodsArea
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String updateGoodsArea() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			goodsAreaVo.getGoodsAreaEntity().setModifyUser(userCode);
			goodsAreaService.updateGoodsArea(goodsAreaVo.getGoodsAreaEntity());
			return returnSuccess(MessageType.UPDAE_GOODSAREA_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 新增库区信息<br/>
	 * 方法名：addGoodsArea
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String addGoodsArea() {
		try {
			GoodsAreaEntity goodsAreaEntity = goodsAreaVo.getGoodsAreaEntity();
			//如果星标编码为空则设定为N
			if(StringUtils.isBlank(goodsAreaEntity.getAsteriskCode())){
				goodsAreaEntity.setAsteriskCode(FossConstants.NO);
			}else{  //如果星标编码不为空则设定为1号线
				goodsAreaEntity.setAsteriskCode(DictionaryValueConstants.ASTERISK_TYPE_LINE1);
			}
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			goodsAreaVo.getGoodsAreaEntity().setCreateUser(userCode);
			goodsAreaService.addGoodsArea(goodsAreaVo.getGoodsAreaEntity());
			return returnSuccess(MessageType.SAVE_GOODSAREA_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 查询库区详细信息<br/>
	 * 方法名：queryGoodsAreaByVirtualCode
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String queryGoodsAreaByVirtualCode() {
		try {
			GoodsAreaEntity goodsAreaEntity = goodsAreaService.queryGoodsAreaByVirtualCode(goodsAreaVo.getGoodsAreaEntity().getVirtualCode());
			goodsAreaVo.setGoodsAreaEntity(goodsAreaEntity);
			return returnSuccess(MessageType.SAVE_STORAGE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
    private String downloadFileName;

    private InputStream inputStream;
	
    public String getDownloadFileName() {
		return downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * FTP客户端
	 *//*
	private FTPClient ftpClient;
	
	*//**
	 * 下载ftp配置
	 *//*
	private FTPConfig downloadFtpConfig;
	
	*//**
	 * 文件下载服务
	 *//*
	private IrecordImportExcelFileInfoService recordImportExcelFileInfoService;
	
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public void setDownloadFtpConfig(FTPConfig downloadFtpConfig) {
		this.downloadFtpConfig = downloadFtpConfig;
	}

	public void setRecordImportExcelFileInfoService(
			IrecordImportExcelFileInfoService recordImportExcelFileInfoService) {
		this.recordImportExcelFileInfoService = recordImportExcelFileInfoService;
	}
*/
	/**
	 * 导出库区信息
	 * @author 107046
	 */
	public String exportGoodsArea(){
		try {
			String userTempCode = "";
			String deptTempCode = "";
			if(FossUserContext.getCurrentInfo() != null) {
				userTempCode = FossUserContext.getCurrentInfo().getEmpCode();
				deptTempCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
			}
			final String userCode = userTempCode;
			final String deptCode = deptTempCode;
			
			final GoodsAreaEntity entity = goodsAreaVo.getGoodsAreaEntity();
			final long dataSize = goodsAreaService.countGoodsAreaByCondition(entity, userCode, deptCode);
			if(dataSize <= 0) {
			    return returnSuccess("当前条件下无查询数据。");
			}
			final long platformSize = goodsAreaService.countPlatformByOrgCode(entity.getOrganizationCode());
			//文件名
			downloadFileName =new String(ColumnConstants.EXPROT_GOODS_AREA_FILE_NAME.getBytes("UTF-8"), "iso-8859-1");
		
			final ExportSetting settings = new ExportSetting();
			settings.setFileName(ColumnConstants.EXPROT_GOODS_AREA_FILE_NAME);
			settings.setSize(ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE);
			settings.setFileDesc(String.format("%s导出", settings.getFileName()));
			
			ExportResource resource = new ExportResource();
			// 设置表头
			resource.setHeads(ComnConst.GOODS_AREA_TITLE);
			int page = (int)Math.ceil((double)dataSize / ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE / platformSize);
			
			int iPageSize = (int)Math.ceil((double) ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE / platformSize);
			
			List<List<String>> result = goodsAreaService.genDataForExport(entity, page, iPageSize);
			//获取excel 信息
			resource.setRowList(result);
			
			/*IResourceListener resourceListener = new IResourceListener() {
				@Override
				public int getDataPage() {
					if(platformSize <= 1) {
						return 1;
					}
					int page = (int)Math.ceil((double)dataSize / ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE / platformSize);
					return page;
				}
				@Override
				public List<List<String>> getDataList(int page) {
					int iPageSize = (int)Math.ceil((double) ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE / platformSize);
					List<List<String>> result = goodsAreaService.genDataForExport(entity, page * iPageSize, 
							iPageSize);
					return result;
				}
			};
			
			// 导出后结果处理
			IExportListener exportListener = new IExportListener() {
				@Override
				public void doExported(ExportInfo info) {
					DownloadInfoEntity entity = new DownloadInfoEntity();
					String encodedUsername = downloadFtpConfig.getUserName();
					String encodedPassword = downloadFtpConfig.getPassword();
					//对用户名密码加密
					try {
						encodedUsername = URLEncoder.encode(encodedUsername,"UTF-8");
						encodedPassword = URLEncoder.encode(encodedPassword,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						LOG.error(e.getMessage(), e);
					}
					entity.setEmpCode(userCode);
					entity.setOrgCode(deptCode);
					entity.setFileName(settings.getFileDesc());
					entity.setFileLoadPath(String.format(
							"ftp://%s:%s@%s/%s/%s", encodedUsername,
							encodedPassword, downloadFtpConfig.getHost(),
							downloadFtpConfig.getPath(),
							info.getRemoteFileName()));
					//保存
					recordImportExcelFileInfoService.saveImportExcelFileInfo(entity);
				}
			};

			// 异步导出
			ExporterExecutor executor = new ExporterExecutor();
			executor.exportAsync(resource, settings, ftpClient, exportListener,
					resourceListener);*/
			ExporterExecutor executor = new ExporterExecutor();
			inputStream =executor.exportSync(resource, settings);
		    return returnSuccess("导出成功！文件已下载至浏览器的默认下载路径中。");
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}catch (UnsupportedEncodingException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError("UnsupportedEncodingException", e);
		}
    }
	
	/**
	 * 下载导入模板
	 * @author 107046
	 */
	public String downloadTemplate() {
		try {
		    downloadFileName =new String(ColumnConstants.EXPROT_GOODS_AREA_FILE_NAME.getBytes("UTF-8"), "iso-8859-1");
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(ComnConst.GOODS_AREA_TITLE);
		    exportResource.setRowList(goodsAreaService.genTemplateData());
		    ExportSetting exportSetting = new ExportSetting();
		    //exportSetting.setSheetName(ColumnConstants.EXPROT_GOODS_AREA_FILE_NAME);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		} catch (UnsupportedEncodingException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError("UnsupportedEncodingException", e);
		}
	}
	
	public String initPlatAreaDistance() {
		try {
			List<PlatAreaDistanceEntity> platAreaDistanceList = goodsAreaService.initPlatAreaDistance(goodsAreaVo.getGoodsAreaEntity());
			GoodsAreaEntity entity = new GoodsAreaEntity();
			entity.setDistanceList(platAreaDistanceList);
			goodsAreaVo.setGoodsAreaEntity(entity);
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
	}
	
    /**
     * 导入文件
     */
    private File uploadFile;
    
    public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
    
    private static final int CELL_COUNT = 18;

	public String importGoodsArea() {
		Workbook book = null;
//		FileInputStream fileInputStream = null;
		try {
		    if (uploadFile != null) {
				try {
					//转换流
					inputStream =new FileInputStream(uploadFile);
					//解析excel流
					book =XlsImpUtil.create(inputStream);
				} catch (Exception e) {
					e.printStackTrace();
					return returnError("数据异常:" + e.getMessage(), e);
				}
////		    	fileInputStream = new FileInputStream(uploadFile);
//		    	//转换流
//				inputStream =new FileInputStream(uploadFile);
//				//解析excel流
//				book =XlsImpUtil.create(inputStream);
//		    	
//		    	
//		    	
//				
//				try {
//				    book = new XSSFWorkbook(fileInputStream);
//				} catch (Exception ex) {
//				    book = new HSSFWorkbook(fileInputStream);
//				}
		    } else {
		    	throw new FileException("请选择导入文件", "请选择导入文件");
		    }
		    if (book != null) {
				Sheet sheet = book.getSheetAt(0);
				List<GoodsAreaExcelDto> excelDtos = new ArrayList<GoodsAreaExcelDto>();
				makeExcelDtos(excelDtos, sheet);
				if (CollectionUtils.isNotEmpty(excelDtos)) {
				    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
				    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
				    // 执行批量插入
				    List<Integer> numList = goodsAreaService.importGoodsAreaList(excelDtos, userCode);
				    goodsAreaVo.setNumList(numList);
				} else {
				    throw new FileException("导入数据为空", "导入数据为空");
				}
		    }
		    return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileException e) {
			e.printStackTrace();
		    return super.returnError(e);
		}finally {
		    if (book != null) {
		    	book = null;
		    }
		    if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				    return returnError("文件关闭失败");
				}
		    }
		}
    }
	
    private void makeExcelDtos(List<GoodsAreaExcelDto> excelDtos, Sheet sheet) {
		if (sheet != null && excelDtos != null) {
		    int rowCount = sheet.getPhysicalNumberOfRows();
		    Row row = null;
		    GoodsAreaExcelDto excelDto = null;
		    for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				excelDto = new GoodsAreaExcelDto();
				row = sheet.getRow(rowNum);
				boolean isContinu = false;
				for (int colNum = 0; colNum < CELL_COUNT; colNum++) {
				    isContinu = isContinu | obtainCellValue(row, colNum, excelDto);
				}
				if (!isContinu) {
				    break;
				}
				excelDto.setRowNo(rowNum);
				excelDtos.add(excelDto);
		    }
		}
    }
    
    private boolean obtainCellValue(Row row, int colNum,
    		GoodsAreaExcelDto excelDto) {
    	if (row != null && null != row.getCell(colNum)) {
    	    Cell cell = row.getCell(colNum);
	    		String cellVal = obtainStringVal(cell);
	    		if (StringUtil.isNotBlank(cellVal)) {
	    			if(sonarSplitOne(excelDto, colNum, cellVal)||sonarSplitTwo(excelDto, colNum, cellVal)){
	    				return true;
//	    			} else if(sonarSplitTwo(excelDto, colNum, cellVal)){
//	    				return true;
	    			} else {
	    				return sonarSplitThree(excelDto, colNum, cellVal);
	    			}
	    		}
    	}
    	return false;
    }
    
    /**
     * sonar优化拆分
     * @author 313353
     */
	private boolean sonarSplitOne(GoodsAreaExcelDto excelDto, int colNum,
			String cellVal) {
		switch (colNum) {
		case NumberConstants.NUMERAL_ZORE:
			excelDto.setTransferCode(cellVal);
			return true;
		case NumberConstants.NUMBER_1:
			excelDto.setOrganizationName(cellVal);
			return true;
		case NumberConstants.NUMBER_2:
			excelDto.setGoodsAreaCode(cellVal);
			return true;
		case NumberConstants.NUMBER_3:
			excelDto.setGoodsAreaName(cellVal);
			return true;
		case NumberConstants.NUMBER_4:
			excelDto.setGoodsAreaType(cellVal);
			return true;
		case NumberConstants.NUMBER_5:
			excelDto.setGoodsType(cellVal);
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private boolean sonarSplitTwo(GoodsAreaExcelDto excelDto, int colNum,
			String cellVal) {
		switch (colNum) {
		case NumberConstants.NUMBER_6:
			excelDto.setArriveRegionName(cellVal);
			return true;
		case NumberConstants.NUMBER_7:
			excelDto.setGoodsAreaUsage(cellVal);
			return true;
		case NumberConstants.NUMBER_8:
			excelDto.setCountingMode(cellVal);
			return true;
		case NumberConstants.NUMBER_9:
			excelDto.setAsteriskCode(cellVal);
			return true;
		case NumberConstants.NUMBER_10:
			excelDto.setNotes(cellVal);
			return true;
		default:
			return false;
		}
	}

	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private boolean sonarSplitThree(GoodsAreaExcelDto excelDto, int colNum,
			String cellVal) {
		switch (colNum) {
		case NumberConstants.NUMBER_11:
			excelDto.setArea(cellVal);
			return true;
		case NumberConstants.NUMBER_12:
			excelDto.setGoodsAreaHeight(cellVal);
			return true;
		case NumberConstants.NUMBER_13:
			excelDto.setAbscissa(cellVal);
			return true;
		case NumberConstants.NUMBER_14:
			excelDto.setOrdinate(cellVal);
			return true;
		case NumberConstants.NUMBER_15:
			excelDto.setPlatformCode(cellVal);
			return true;
		case NumberConstants.NUMBER_16:
			excelDto.setDistance(cellVal);
			return true;
		default:
			return true;
		}
	}
    
    private String obtainStringVal(Cell cell) {
		String cellVal = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
			    if (HSSFDateUtil.isCellDateFormatted(cell)) {
			    	cellVal = com.deppon.foss.util.DateUtils.convert(
					HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
					com.deppon.foss.util.DateUtils.DATE_FORMAT);
			    } else {
			    	cellVal = String.valueOf(cell.getNumericCellValue());
			    }
			    break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串型
			    //cellVal = cell.getRichStringCellValue().toString();
			    //break;
			case HSSFCell.CELL_TYPE_FORMULA:// 公式型
//			    cellVal = String.valueOf(cell.getNumericCellValue());
//			    if (cellVal.equals("NaN")) {
			    	cellVal = cell.getRichStringCellValue().toString();
//			    }
			    break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			    cellVal = " " + cell.getBooleanCellValue();
			    break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
			   // cellVal = "";
			   // break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
			    cellVal = "";
			    break;
			default:
			    cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal;
    }
	
}
