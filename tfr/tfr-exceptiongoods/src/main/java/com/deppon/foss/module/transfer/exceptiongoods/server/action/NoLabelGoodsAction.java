/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/action/NoLabelGoodsAction.java
 *  
 *  FILE NAME          :NoLabelGoodsAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PhotoUploadDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.NoLabelGoodsException;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo.NoLabelGoodsVo;
import com.deppon.foss.util.define.FossConstants;


//import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 无标签多货界面
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:16:36
 */
public class NoLabelGoodsAction extends AbstractAction{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoLabelGoodsAction.class); 

	/**
	* @fields serialVersionUID
	*/
	private static final long serialVersionUID = -6161408280692917593L;
	
	/**
	 * 定义了无标签多货的业务操作方法
	 */
	private INoLabelGoodsService noLabelGoodsService;
	
	/**
	 * 定义了标签打印信息的相关操作
	 */
	private IPrintLabelService printLabelService;
	
	/**
	* 封装了无标签多货界面参数
	*/
	private NoLabelGoodsVo noLabelGoodsVo = new NoLabelGoodsVo();
	
    /**
    * 正面照文件
    */
    private File frontPhoto;
    /**
    * 整体照文件
    */
    private File entiretyPhoto;
    /**
    * 内物照文件
    */
    private File goodsPhoto;
    /**
    * 附加照1文件
    */
    private File photoA;
    /**
    * 附加照2文件
    */
    private File photoB;
    /**
    *  附加照1文件名
    */
    private String photoAFileName;
    /**
    *  附加照2文件名
    */
    private String photoBFileName;
    /**
    *  正面照文件名
    */
    private String frontPhotoFileName;
    /**
    * 整体照文件名
    */
    private String entiretyPhotoFileName;
    /**
    * 内物照文件名
    */
    private String goodsPhotoFileName;
    /** 文件类型*/
    /**
    * 附加照1文件类型
    */
    private String photoAContentType;
    /**
    * 附加照2文件类型
    */
    private String photoBContentType;
    /**
    * 正面照文件类型
    */
    private String frontPhotoContentType;
    /**
    * 整体照文件类型
    */
    private String entiretyPhotoContentType;
    /**
    * 内物照文件类型
    */
    private String goodsPhotoContentType;
    /** 导出Excel 文件名*/
	private String fileName;  
	/** 导出Excel 文件流*/
	private InputStream excelStream;  

	
	
	/**
	* @description 获取 导出Excel 文件流.
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午3:46:06
	*/
	public InputStream getExcelStream() {
		return excelStream;
	}


	
	/**
	* @description 设置 导出Excel 文件流.
	* @param excelStream
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午3:46:17
	*/
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	/**
	* @description 获取 导出Excel 文件名.
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:36:07
	*/
	public String getFileName() {
		return fileName;
	}

	
	/**
	* @description 设置 导出Excel 文件名.
	* @param fileName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:36:12
	*/
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	* @param noLabelGoodsService
	* @description 设置无标签多货的业务操作方法
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-25 下午4:31:10
	*/
	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}
	
	/**
	* @param printLabelService
	* @description 设置标签打印信息的相关操作
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-25 下午4:31:54
	*/
	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}

	/**
	* @return
	* @description 获取封装了无标签多货界面参数
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:09:18
	*/
	public NoLabelGoodsVo getNoLabelGoodsVo() {
		return noLabelGoodsVo;
	}

	/**
	* @param noLabelGoodsVo
	* @description 设置封装了无标签多货界面参数
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:09:34
	*/
	public void setNoLabelGoodsVo(NoLabelGoodsVo noLabelGoodsVo) {
		this.noLabelGoodsVo = noLabelGoodsVo;
	}

	/**
	* @return
	* @description 获取 附加照1文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:10:32
	*/
	public File getPhotoA() {
		return photoA;
	}

	/**
	* @param photoA
	* @description 设置附加照1文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:10:46
	*/
	public void setPhotoA(File photoA) {
		this.photoA = photoA;
	}

	/**
	* @return
	* @description 获取附加照2文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:11:06
	*/
	public File getPhotoB() {
		return photoB;
	}

	/**
	* @param photoB
	* @description 设置附加照2文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:11:16
	*/
	public void setPhotoB(File photoB) {
		this.photoB = photoB;
	}

	/**
	* @return
	* @description  获取附加照1文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:11:52
	*/
	public String getPhotoAFileName() {
		return photoAFileName;
	}

	/**
	* @param photoAFileName
	* @description  设置附加照1文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:12:07
	*/
	public void setPhotoAFileName(String photoAFileName) {
		this.photoAFileName = photoAFileName;
	}

	/**
	* @return
	* @description 获取附加照2文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:12:31
	*/
	public String getPhotoBFileName() {
		return photoBFileName;
	}

	/**
	* @param photoBFileName
	* @description 设置附加照2文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:12:40
	*/
	public void setPhotoBFileName(String photoBFileName) {
		this.photoBFileName = photoBFileName;
	}

	/**
	* @return
	* @description 获取附加照2文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:13:09
	*/
	public String getPhotoBContentType() {
		return photoBContentType;
	}

	/**
	* @param photoBContentType
	* @description 设置附加照2文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:13:27
	*/
	public void setPhotoBContentType(String photoBContentType) {
		this.photoBContentType = photoBContentType;
	}
	
	/**
	* @return
	* @description 获取附加照1文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:13:53
	*/
	public String getPhotoAContentType() {
		return photoAContentType;
	}

	/**
	* @param photoAContentType
	* @description 设置附加照1文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:14:01
	*/
	public void setPhotoAContentType(String photoAContentType) {
		this.photoAContentType = photoAContentType;
	}
	
	/**
	* @return
	* @description 获取正面照文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-26 下午2:21:59
	*/
	public File getFrontPhoto() {
		return frontPhoto;
	}

	/**
	* @param frontPhoto
	* @description  设置正面照文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-26 下午2:22:37
	*/
	public void setFrontPhoto(File frontPhoto) {
		this.frontPhoto = frontPhoto;
	}

	/**
	* @return
	* @description 获取整体照文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:14:31
	*/
	public File getEntiretyPhoto() {
		return entiretyPhoto;
	}

	/**
	* @param entiretyPhoto
	* @description 设置整体照文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:14:46
	*/
	public void setEntiretyPhoto(File entiretyPhoto) {
		this.entiretyPhoto = entiretyPhoto;
	}

	/**
	* @return
	* @description 获取内物照文件
	* @version 1.0 
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:15:09
	*/
	public File getGoodsPhoto() {
		return goodsPhoto;
	}

	/**
	* @param goodsPhoto
	* @description  设置内物照文件
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:15:20
	*/
	public void setGoodsPhoto(File goodsPhoto) {
		this.goodsPhoto = goodsPhoto;
	}

	/**
	* @return
	* @description 获取正面照文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:15:42
	*/
	public String getFrontPhotoFileName() {
		return frontPhotoFileName;
	}

	/**
	* @param frontPhotoFileName
	* @description 设置正面照文件名 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:15:58
	*/
	public void setFrontPhotoFileName(String frontPhotoFileName) {
		this.frontPhotoFileName = frontPhotoFileName;
	}

	/**
	* @return
	* @description 获取整体照文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:21:15
	*/
	public String getEntiretyPhotoFileName() {
		return entiretyPhotoFileName;
	}

	/**
	* @param entiretyPhotoFileName
	* @description 设置整体照文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:21:24
	*/
	public void setEntiretyPhotoFileName(String entiretyPhotoFileName) {
		this.entiretyPhotoFileName = entiretyPhotoFileName;
	}

	/**
	* @return
	* @description 获取内物照文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:23:53
	*/
	public String getGoodsPhotoFileName() {
		return goodsPhotoFileName;
	}

	/**
	* @param goodsPhotoFileName
	* @description 设置内物照文件名
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:24:10
	*/
	public void setGoodsPhotoFileName(String goodsPhotoFileName) {
		this.goodsPhotoFileName = goodsPhotoFileName;
	}

	/**
	* @return
	* @description 获取正面照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:24:43
	*/
	public String getFrontPhotoContentType() {
		return frontPhotoContentType;
	}

	/**
	* @param frontPhotoContentType
	* @description 设置正面照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:24:50
	*/
	public void setFrontPhotoContentType(String frontPhotoContentType) {
		this.frontPhotoContentType = frontPhotoContentType;
	}

	/**
	* @return
	* @description 获取整体照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:25:22
	*/
	public String getEntiretyPhotoContentType() {
		return entiretyPhotoContentType;
	}

	/**
	* @param entiretyPhotoContentType
	* @description 设置整体照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:25:33
	*/
	public void setEntiretyPhotoContentType(String entiretyPhotoContentType) {
		this.entiretyPhotoContentType = entiretyPhotoContentType;
	}

	/**
	* @return
	* @description 获取内物照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:27:23
	*/
	public String getGoodsPhotoContentType() {
		return goodsPhotoContentType;
	}

	/**
	* @param goodsPhotoContentType
	* @description 设置内物照文件类型
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:27:32
	*/
	public void setGoodsPhotoContentType(String goodsPhotoContentType) {
		this.goodsPhotoContentType = goodsPhotoContentType;
	}
	
	
//	private IErrorLoseStartingService errorLoseStartingService;
	

/*	public void setErrorLoseStartingService(
			IErrorLoseStartingService errorLoseStartingService) {
		this.errorLoseStartingService = errorLoseStartingService;
	}
*/
	/**
	 * 保存无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-19 下午2:40:11
	 */
	public String addNoLabelGoods(){
		
		PhotoUploadDto photoUploadDto = new PhotoUploadDto();
		photoUploadDto.setFrontPhoto(frontPhoto);
		photoUploadDto.setFrontPhotoFileName(frontPhotoFileName);
		photoUploadDto.setEntiretyPhoto(entiretyPhoto);
		photoUploadDto.setEntiretyPhotoFileName(entiretyPhotoFileName);
		photoUploadDto.setGoodsPhoto(goodsPhoto);
		photoUploadDto.setGoodsPhotoFileName(goodsPhotoFileName);
		photoUploadDto.setPhotoA(photoA);
		photoUploadDto.setPhotoAFileName(photoAFileName);
		photoUploadDto.setPhotoB(photoB);
		photoUploadDto.setPhotoBFileName(photoBFileName);
		
		//************************
		NoLabelGoodsEntity noLabelGoodsEntity = noLabelGoodsVo.getNoLabelGoodsEntity();
		noLabelGoodsEntity.setCreateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		noLabelGoodsEntity.setCreateUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
		
		try{
			if(StringUtils.isNotBlank(noLabelGoodsEntity.getId())){
				noLabelGoodsService.updateNoLabelGoodsByBillNo(noLabelGoodsEntity, photoUploadDto);
			}else{
				noLabelGoodsService.addNoLabelGoods(noLabelGoodsEntity, photoUploadDto);
			}
		}catch(NoLabelGoodsException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 分页查询无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午1:15:47
	 */
	@JSON
	public String queryNoLabelGoods(){
		
		List<NoLabelGoodsEntity> list = noLabelGoodsService.queryNoLabelGoods(noLabelGoodsVo.getNoLabelGoodsEntity(), 
				this.getLimit(), this.getStart());
		noLabelGoodsVo.setNoLabelGoodsList(list);
		
		Long totalCount = noLabelGoodsService.queryNoLabelGoodsCount(noLabelGoodsVo.getNoLabelGoodsEntity());
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * 根据 id  获取无标签 对象 
	 * @return
	 */
	@JSON
	public String queryNoLabelGoodsOneById(){
		NoLabelGoodsEntity vo =noLabelGoodsService.queryUniqNoLabelGoods(noLabelGoodsVo.getNoLabelGoodsEntity().getNoLabelBillNo(), noLabelGoodsVo.getNoLabelGoodsEntity().getNoLabelSerialNo());
		noLabelGoodsVo.setNoLabelGoodsEntity(vo);
		
		try{
			OrgAdministrativeInfoEntity bigOrg = noLabelGoodsService.queryBigOrg(FossUserContext.getCurrentDept());
			noLabelGoodsVo.setBigOrgCode(bigOrg.getCode());
			noLabelGoodsVo.setBigOrgName(bigOrg.getName());
			noLabelGoodsVo.setUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			
			if(vo!=null && vo.getReportOATime()!=null){
				Date tempDate = new Date();
			    long tempNum = tempDate.getTime() - vo.getReportOATime().getTime();
			    tempNum = tempNum/(ExceptionGoodsConstants.SONAR_NUMBER_1000*ExceptionGoodsConstants.SONAR_NUMBER_60*ExceptionGoodsConstants.SONAR_NUMBER_60*ExceptionGoodsConstants.SONAR_NUMBER_24);//精确到天
			    noLabelGoodsVo.setToOaTime(tempNum+"");
			}else{
				noLabelGoodsVo.setToOaTime("0");
			}
//			System.out.println("=============");
//			System.out.println("(bigOrg.getCode():"+bigOrg.getCode()+"  bigOrg.getName():"+bigOrg.getName());
//			String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
//			String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
//			System.out.println("userCode:"+userCode+"  userName:"+userName);
//			System.out.println("=============");
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 无标签货物转弃货
	 * @return
	 */
	@JSON
	public String noLableGoodsToAbandon() {
		NoLabelGoodsEntity vo =noLabelGoodsService.queryUniqNoLabelGoods(noLabelGoodsVo.getNoLabelGoodsEntity().getNoLabelBillNo(), noLabelGoodsVo.getNoLabelGoodsEntity().getNoLabelSerialNo());
		noLabelGoodsVo.setNoLabelGoodsEntity(vo);
		try{
			OrgAdministrativeInfoEntity bigOrg = noLabelGoodsService.queryBigOrg(FossUserContext.getCurrentDept());
			noLabelGoodsVo.setBigOrgCode(bigOrg.getCode());
			noLabelGoodsVo.setBigOrgName(bigOrg.getName());
			noLabelGoodsVo.setUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			noLabelGoodsVo.setUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			noLabelGoodsService.toAbandonService(noLabelGoodsVo);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 生成无标签运单号
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午1:16:08
	 */
	@JSON
	public String generateNoLabelGoodsBillNo(){
		NoLabelGoodsEntity noLabelGoodsEntity = new NoLabelGoodsEntity();
		noLabelGoodsEntity.setNoLabelBillNo(noLabelGoodsService.generateNoLabelGoodsBillNo());
		noLabelGoodsVo.setNoLabelGoodsEntity(noLabelGoodsEntity);
		return returnSuccess();
	}
	/**
	 * 查询货物打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午1:18:13
	 */
	@JSON
	public String queryPrintLabelInfo(){
		List<PrintLabelDto> printLabelDtoList = printLabelService.queryPrintLabelInfo(noLabelGoodsVo.getPrintLabelDto());
		noLabelGoodsVo.setPrintLabelDtoList(printLabelDtoList);
		return returnSuccess();
	}
	
	
	/**
	* @description 判断运单是否电子运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年8月27日 上午9:24:09
	*/
	@JSON
	public String checkWallbillElectron(){
		try{
			if(printLabelService.isEWaybillInfoByWaybillNo(noLabelGoodsVo.getPrintLabelDto().getWaybillNo())){
				noLabelGoodsVo.setWallbillElectron(FossConstants.YES);
			}else{
				noLabelGoodsVo.setWallbillElectron(FossConstants.NO);
			}
		}catch(BusinessException e){
			return returnError(e.getMessage(),"");
		}
			return returnSuccess();
	}
	/**
	 * 获取打印内容,保存打印指定标签操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 下午4:35:52
	 */
	@JSON
	public String printAppointedLabel(){
		try{
			List<BarcodePrintLabelDto> barcodePrintDtoList = printLabelService.printAppointedLabel(noLabelGoodsVo.getPrintLabelDto().getWaybillNo(), 
					noLabelGoodsVo.getSerialNoList());
			noLabelGoodsVo.setBarcodePrintDtoList(barcodePrintDtoList);
			printLabelService.addPrintAppointedLabel(noLabelGoodsVo.getPrintLabelDto().getWaybillNo(), 
					noLabelGoodsVo.getSerialNoList(), noLabelGoodsVo.getUserCode());
		}catch(NoLabelGoodsException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	
	/**
	* @description 获取打印内容,保存打印指定标签操作信息
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:00:04
	*/
	@JSON
	public String printAppointedLabelExpress(){
		try{
			//截取字符串
			String waybillNoAndSerialNo = noLabelGoodsVo.getPrintLabelDto().getWaybillNo();
			String waybillNo = null;
			String serialNo = null;
			if(waybillNoAndSerialNo != null && 
					(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_10 || waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_14 
					|| waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_18)){
				//如果运单是10位就自添流水号 0001
				if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_10){
					waybillNo = waybillNoAndSerialNo;
					serialNo = "0001";
				}
				//如果运单是18位  前10位是运单号,11-14位是流水号
				else if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_14 || waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_18){
					waybillNo = waybillNoAndSerialNo.substring(0,ExceptionGoodsConstants.SONAR_NUMBER_10);
					serialNo = waybillNoAndSerialNo.substring(ExceptionGoodsConstants.SONAR_NUMBER_10,ExceptionGoodsConstants.SONAR_NUMBER_14);
				}
				List<String> sList = new ArrayList<String>();
				sList.add(serialNo);
				try {
					List<BarcodePrintLabelDto>	barcodePrintDtoList = 
							noLabelGoodsService.printAppointedLabelExpress(waybillNo,serialNo,sList);
					if(barcodePrintDtoList != null){
						noLabelGoodsVo.setBarcodePrintDtoList(barcodePrintDtoList);
					}
					
				}catch(BusinessException e){
					return returnError(e);
				}
			}else{
				return returnError("运单输入有误");
			}
		}catch(NoLabelGoodsException e){
			return returnError(e);
		}
		return returnSuccess();
		
	}
	
	
	/**
	* @description 查询货物打印信息(巴枪扫描打印的记录)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:02:49
	*/
	@JSON
	public String queryPrintLabelInfoExpress(){
		PrintLabelDto dto = noLabelGoodsVo.getPrintLabelDto();
		dto.setPrintType(ExceptionGoodsConstants.PRINT_TYPE);
		List<PrintLabelDto> printLabelDtoList = printLabelService.queryPrintLabelInfoExpress(noLabelGoodsVo.getPrintLabelDto(),this.getStart(),this.getLimit());
		Long totalCount = printLabelService.queryPrintLabelInfoExpressCount(noLabelGoodsVo.getPrintLabelDto());
		this.setTotalCount(totalCount);
		noLabelGoodsVo.setPrintLabelDtoList(printLabelDtoList);
		return returnSuccess();
	}
	
	
	/**
	* @description 补打电子运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午11:00:31
	*/
	@JSON
	public String printWallbillElectron(){
		try{
			List<EWaybillPrintDto> wallbillElectronList = printLabelService.printEWaybillLabel(noLabelGoodsVo.getPrintLabelDto().getWaybillNo(), 
					noLabelGoodsVo.getSerialNoList());
			noLabelGoodsVo.setWallbillElectronList(wallbillElectronList);
			printLabelService.addPrintAppointedLabel(noLabelGoodsVo.getPrintLabelDto().getWaybillNo(), 
					noLabelGoodsVo.getSerialNoList(), noLabelGoodsVo.getUserCode());
		}catch(NoLabelGoodsException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	
	
	
	
	
	/**
	 * 保存打印指定标签操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 下午4:36:14
	 */
	@JSON
	public String addPrintLabel(){
		printLabelService.addPrintAppointedLabel(noLabelGoodsVo.getPrintLabelDto().getWaybillNo(), 
				noLabelGoodsVo.getSerialNoList(), noLabelGoodsVo.getUserCode());
		return returnSuccess();
	}
	
	/**
	 * 更新无标签货物打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午10:02:38
	 */
	@JSON
	public String updateNoLabelGoodsPrintInfo(){
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		noLabelGoodsService.updateNoLabelGoodsPrintInfo(noLabelGoodsVo.getNoLabelGoodsList(), userCode, userName);
		return returnSuccess();
	}
	/**
	 * 登入异常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午10:08:40
	 */
	@JSON
	public String loginExceptionStock(){
		
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		String currentOrgCode = FossUserContext.getCurrentDeptCode();
		try{
			noLabelGoodsService.loginExceptionStock(noLabelGoodsVo.getNoLabelGoodsList(), userCode, userName, currentOrgCode);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 登出异常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午10:10:17
	 */
	@JSON
	public String logoutExceptionStock(){
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		String currentOrgCode = FossUserContext.getCurrentDeptCode();
		try{
			noLabelGoodsService.logoutExceptionStock(noLabelGoodsVo.getNoLabelGoodsList(), userCode, userName, currentOrgCode);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 上报OA
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-29 上午11:22:57
	 */
	public String reportOANoLabelGoods(){
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			noLabelGoodsService.reportOANoLabelGoods(noLabelGoodsVo.getNoLabelGoodsList(), userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 查询当前用户所在大部门（外场）
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-19 上午10:52:44
	 */
	public String queryBigOrg(){
		try{
			OrgAdministrativeInfoEntity bigOrg = noLabelGoodsService.queryBigOrg(FossUserContext.getCurrentDept());
			noLabelGoodsVo.setBigOrgCode(bigOrg.getCode());
			noLabelGoodsVo.setBigOrgName(bigOrg.getName());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 验证用户
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-24 下午3:58:53
	 */
	public String verifyUser(){
		boolean flag = noLabelGoodsService.verifyUser(noLabelGoodsVo.getUserCode(), noLabelGoodsVo.getPassword());
		if(flag){
			noLabelGoodsVo.setIsVerify(FossConstants.YES);
		}else{
			noLabelGoodsVo.setIsVerify(FossConstants.NO);
		}
		return returnSuccess();
	}
	/**
	 * 删除无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午2:07:27
	 */
	public String deleteNoLabelGoods(){
		noLabelGoodsService.deleteNoLabelGoods(noLabelGoodsVo.getNoLabelGoodsEntity());
		return returnSuccess();
	}
	
	
	/**
	* @description 将运单信息写入到标签打印信息表
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月21日 下午2:18:39
	*/
	public void insertPrintLabel(){
		String waybillNo = null;
		String serialNo = null;
		String waybillNoAndSerialNo = noLabelGoodsVo.getPrintLabelDto().getWaybillNo();
		if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_10){
			waybillNo = waybillNoAndSerialNo;
			serialNo = "0001";
		}
		//如果运单是18位  前10位是运单号,11-14位是流水号
		else if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_18 || waybillNoAndSerialNo.length() ==ExceptionGoodsConstants.SONAR_NUMBER_14){
			waybillNo = waybillNoAndSerialNo.substring(0,ExceptionGoodsConstants.SONAR_NUMBER_10);
			serialNo = waybillNoAndSerialNo.substring(ExceptionGoodsConstants.SONAR_NUMBER_10,ExceptionGoodsConstants.SONAR_NUMBER_14);
		}
		List<String> sList = new ArrayList<String>();
		sList.add(serialNo);
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		printLabelService.addPrintAppointedLabel(waybillNo, sList, userCode,ExceptionGoodsConstants.PRINT_TYPE);
		
	}
	/**
	* @description 上分拣和标签打印
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午3:04:59
	*/
	public String sortingAndPringLabel(){
		String waybillNoAndSerialNo = noLabelGoodsVo.getSortingAndPringLabelDto().getWaybillNo();
		//截取字符串
		String waybillNo = null;
		String serialNo = null;
		if(waybillNoAndSerialNo != null && 
				(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_10 || waybillNoAndSerialNo.length() ==ExceptionGoodsConstants.SONAR_NUMBER_14 || 
				waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_18)){
			//如果运单是10位就自添流水号 0001
			if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_10){
				waybillNo = waybillNoAndSerialNo;
				serialNo = "0001";
			}
			//如果运单是18位  前10位是运单号,11-14位是流水号
			else if(waybillNoAndSerialNo.length() == ExceptionGoodsConstants.SONAR_NUMBER_18 || waybillNoAndSerialNo.length() ==ExceptionGoodsConstants.SONAR_NUMBER_14){
				waybillNo = waybillNoAndSerialNo.substring(0,ExceptionGoodsConstants.SONAR_NUMBER_10);
				serialNo = waybillNoAndSerialNo.substring(ExceptionGoodsConstants.SONAR_NUMBER_10,ExceptionGoodsConstants.SONAR_NUMBER_14);
			}
			try {
				List<BarcodePrintLabelDto>	barcodePrintDtoList = noLabelGoodsService.sortingAndPringLabel(waybillNo,serialNo); 
				if(barcodePrintDtoList != null){
					noLabelGoodsVo.setBarcodePrintDtoList(barcodePrintDtoList);
				}
			}catch(BusinessException e){
				return returnError(e);
			}
			//部门code
			String orgCode = FossUserContext.getCurrentDeptCode();
			String isFirstTransfer = noLabelGoodsService.isFirstTransfer(waybillNo,serialNo,orgCode);
			if("Y".equals(isFirstTransfer)){
				noLabelGoodsVo.setIsFirstTransfer("Y");
			}
		}else{
			return returnError("运单号长度输入有误");
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 上分拣和标签打印查询action
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午9:40:07
	*/
	public String querySortingOrLabelPring(){
		
		String waybillNo = noLabelGoodsVo.getSortingAndPringLabelDto().getWaybillNo();
		String serialNo = noLabelGoodsVo.getSortingAndPringLabelDto().getSerialNo();
		String operateCode = noLabelGoodsVo.getSortingAndPringLabelDto().getOperateCode();
		Date beginTime = noLabelGoodsVo.getSortingAndPringLabelDto().getBeginTime();
		Date endTime = noLabelGoodsVo.getSortingAndPringLabelDto().getEndTime();
		String orgCode = FossUserContext.getCurrentDept().getCode();
		
		PrintLabelDto printLabelDto = new PrintLabelDto();
		printLabelDto.setWaybillNo(waybillNo);
		printLabelDto.setSerialNo(serialNo);
		printLabelDto.setOperateCode(operateCode);
		printLabelDto.setQueryTimeBegin(beginTime);
		printLabelDto.setQueryTimeEnd(endTime);
		printLabelDto.setOrgCode(orgCode);
		//根据是否标签打印来判断  1:查询标签打印记录     否则查询上分拣记录
		String labelPrint = noLabelGoodsVo.getSortingAndPringLabelDto().getLabelPrint();
		if("1".equals(labelPrint)){
			
			//根据部门code员工工号,查询标签打印记录
			List<SortingAndPringLabelDto> sortingAndPringLabelList = 
					printLabelService.queryPrintLabelInfoExpressByOrgCode(printLabelDto,this.getStart(),this.getLimit());
			noLabelGoodsVo.setSortingAndPringLabelList(sortingAndPringLabelList);
			
			Long totalCount = printLabelService.queryPrintLabelInfoExpressByOrgCodeCount(printLabelDto);
			this.setTotalCount(totalCount);
			
			return returnSuccess();
		}else{
			//查询上分拣记录
			List<SortingAndPringLabelDto> sortingAndPringLabelList = 
					printLabelService.querySortingInfoByOrgCode(printLabelDto,this.getStart(),this.getLimit());
			noLabelGoodsVo.setSortingAndPringLabelList(sortingAndPringLabelList);
			Long totalCount = printLabelService.querySortingInfoByOrgCodeCount(printLabelDto); 
			this.setTotalCount(totalCount);
		}
		return returnSuccess();
	}
	
	
	
	/**
	* @description 将标签打印或上分拣查询结果导出到Excel文件
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:28:13
	*/
	public String exportSortingOrLabelPringExcel(){
		try{
			try {
				if("1".equals(noLabelGoodsVo.getSortingAndPringLabelDto().getIsPrint())){
					fileName = this.encodeFileName("标签打印清单");
				}else{
					fileName = this.encodeFileName("上分拣清单");
				}
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("NoLabelGoodsAction.exportSortingOrLabelPringExcel 报错:" + e.getMessage());
			}
			excelStream = printLabelService.exportExcelStream(noLabelGoodsVo.getIds(),
					noLabelGoodsVo.getSortingAndPringLabelDto());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	 
	/**
	* @description 转换导出文件的文件名
	* @param name
	* @return
	* @throws UnsupportedEncodingException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:37:35
	*/
	public String encodeFileName(String name) throws UnsupportedEncodingException {
	    	String returnStr;
	    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
	    	if(agent != null && agent.indexOf("MSIE") == -1) {
	    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
	    	} else {
	    		returnStr = URLEncoder.encode(name, "UTF-8");
	    	}
	    	return returnStr;
	    }
	
	/**
	 * @author nly
	 * @date 2015年6月15日 下午4:15:04
	 * @function 根据品名查询品类
	 * @return
	 */
	public String queryTypeByGoodsName() {
		String type = noLabelGoodsService.queryTypeByGoodsName(noLabelGoodsVo.getNoLabelGoodsEntity().getGoodsName());
		noLabelGoodsVo.getNoLabelGoodsEntity().setGoodsType(type);
		return returnSuccess();
	}
}
