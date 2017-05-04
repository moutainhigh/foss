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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/PhotoUploadDto.java
 * 
 *  FILE NAME          :PhotoUploadDto.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto;

import java.io.File;

public class PhotoUploadDto implements java.io.Serializable{
	private static final long serialVersionUID = -7720403197924741665L;
	/** 文件*/
	private File frontPhoto;
    private File entiretyPhoto;
    private File goodsPhoto;
    private File photoA;
    private File photoB;
    /** 文件名*/
    private String photoAFileName;
    private String photoBFileName;
    private String frontPhotoFileName;
    private String entiretyPhotoFileName;
    private String goodsPhotoFileName;
	public File getFrontPhoto() {
		return frontPhoto;
	}
	public void setFrontPhoto(File frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	public File getEntiretyPhoto() {
		return entiretyPhoto;
	}
	public void setEntiretyPhoto(File entiretyPhoto) {
		this.entiretyPhoto = entiretyPhoto;
	}
	public File getGoodsPhoto() {
		return goodsPhoto;
	}
	public void setGoodsPhoto(File goodsPhoto) {
		this.goodsPhoto = goodsPhoto;
	}
	public File getPhotoA() {
		return photoA;
	}
	public void setPhotoA(File photoA) {
		this.photoA = photoA;
	}
	public File getPhotoB() {
		return photoB;
	}
	public void setPhotoB(File photoB) {
		this.photoB = photoB;
	}
	public String getPhotoAFileName() {
		return photoAFileName;
	}
	public void setPhotoAFileName(String photoAFileName) {
		this.photoAFileName = photoAFileName;
	}
	public String getPhotoBFileName() {
		return photoBFileName;
	}
	public void setPhotoBFileName(String photoBFileName) {
		this.photoBFileName = photoBFileName;
	}
	public String getFrontPhotoFileName() {
		return frontPhotoFileName;
	}
	public void setFrontPhotoFileName(String frontPhotoFileName) {
		this.frontPhotoFileName = frontPhotoFileName;
	}
	public String getEntiretyPhotoFileName() {
		return entiretyPhotoFileName;
	}
	public void setEntiretyPhotoFileName(String entiretyPhotoFileName) {
		this.entiretyPhotoFileName = entiretyPhotoFileName;
	}
	public String getGoodsPhotoFileName() {
		return goodsPhotoFileName;
	}
	public void setGoodsPhotoFileName(String goodsPhotoFileName) {
		this.goodsPhotoFileName = goodsPhotoFileName;
	}
     
     

}