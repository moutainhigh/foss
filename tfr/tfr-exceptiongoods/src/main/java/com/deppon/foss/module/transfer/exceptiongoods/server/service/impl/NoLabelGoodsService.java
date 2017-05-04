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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/service/impl/NoLabelGoodsService.java
 *  
 *  FILE NAME          :NoLabelGoodsService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.file.FileInfo;
import com.deppon.foss.framework.server.components.file.FileManager;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISourceCategoriesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrToQmsErrorService;
import com.deppon.foss.module.transfer.common.api.shared.define.QmsErrorConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsNolabelEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsNolableRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.NolabelRequestFromQMSDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportNolabel;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseFromQmsDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PhotoUploadDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.NoLabelGoodsException;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo.NoLabelGoodsVo;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现无标签多货的业务操作
 * @author 097457-foss-wangqiang 
 * @date 2012-12-25 下午12:18:36
 */
public class NoLabelGoodsService implements INoLabelGoodsService {
	
	private static final Logger LOGGER = LogManager.getLogger(NoLabelGoodsService.class);
	/**无标签DAO*/
	private INoLabelGoodsDao noLabelGoodsDao;
	/**库存*/
	private IStockService stockService;
	/**组织*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**上报OA*/
	private IFOSSToOAService fossToOAService;
	/**用户验证*/
	private ILoginService loginService;
	/**上传文件管理*/
	private FileManager fileManager;
	/**单号生成*/
	private ITfrCommonService tfrCommonService;
	/**上传图片给qms路径**/
	private String uploadImageToQMSPath;
	/**
	* 封装了无标签多货界面参数
	*/
	private NoLabelGoodsVo noLabelGoodsVo = new NoLabelGoodsVo();
	
	/**
	 * 定义了标签打印信息的相关操作
	 */
	private IPrintLabelService printLabelService;
	/**
	 * 定义了无标签多货的业务操作方法
	 */
//	private INoLabelGoodsService noLabelGoodsService;
	/**
	* @param noLabelGoodsService
	* @description 设置无标签多货的业务操作方法
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-25 下午4:31:10
	*/
/*	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}*/
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
	* @description 设置noLabelGoodsVo
	* @param noLabelGoodsVo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午2:13:12
	*/
	public void setNoLabelGoodsVo(NoLabelGoodsVo noLabelGoodsVo) {
		this.noLabelGoodsVo = noLabelGoodsVo;
	}
	
	public void setUploadImageToQMSPath(String uploadImageToQMSPath) {
		this.uploadImageToQMSPath = uploadImageToQMSPath;
	}

	/**
	 * 无标签转弃货
	 */
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;
	
	private IPDASortingDao pdaSortingDao;//分拣服务接口
	
	public void setPdaSortingDao(IPDASortingDao pdaSortingDao) {
		this.pdaSortingDao = pdaSortingDao;
	}
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IEmployeeService employeeService;
	//上报QMS无标签多货
	private ITfrToQmsErrorService tfrToQmsErrorService;
	//行业货源类别
	private ISourceCategoriesService sourceCategoriesService;
	//配置参数
	private IConfigurationParamsService configurationParamsService;
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setSourceCategoriesService(ISourceCategoriesService sourceCategoriesService) {
		this.sourceCategoriesService = sourceCategoriesService;
	}

	public void setTfrToQmsErrorService(ITfrToQmsErrorService tfrToQmsErrorService) {
		this.tfrToQmsErrorService = tfrToQmsErrorService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 无标签转弃货Service
	 * @param abandonGoodsApplicationService
	 */
	public void setAbandonGoodsApplicationService(
			IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

	/**
	* @param noLabelGoodsDao
	* @description 设置无标签DAO
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:40:44
	*/
	public void setNoLabelGoodsDao(INoLabelGoodsDao noLabelGoodsDao) {
		this.noLabelGoodsDao = noLabelGoodsDao;
	}

	/**
	* @param stockService
	* @description 设置库存
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:41:00
	*/
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	* @param orgAdministrativeInfoComplexService
	* @description 设置组织
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:41:21
	*/
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	* @param fossToOAService
	* @description 设置上报OA
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:41:40
	*/
	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
		System.out.println("*****************set fossToOAService*****************");
	}
	
	/**
	* @param loginService
	* @description 设置用户验证
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:42:01
	*/
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	/**
	* @param fileManager
	* @description 设置上传文件管理
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:42:31
	*/
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	/**
	* @param tfrCommonService
	* @description  设置单号生成
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:42:50
	*/
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	/**
	 * 保存无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-19 上午10:28:53
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#addNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	@Transactional
	public int addNoLabelGoods(NoLabelGoodsEntity noLabelGoods, PhotoUploadDto photoUploadDto) {
		/**
		 * BUG-50471 
		 * FOSS无标签上报，上报条数超过一定条数就不能上报了，
		 * 昨天不能上报，今天上报条数好像是五条吧，就又不能上报了，
		 * 症状为：填写完毕，点击保存后什么都不显示了
		 * 解决：错误处理。页面保存时添加进度条。
		 */
		try{
			//正面照
			if(photoUploadDto.getFrontPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getFrontPhoto());
				noLabelGoods.setFrontPhoto(fileInfo.getRelativePath());
				noLabelGoods.setFrontPhotoName(photoUploadDto.getFrontPhotoFileName());		
			}
			//整体照
			if(photoUploadDto.getEntiretyPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getEntiretyPhoto());
				noLabelGoods.setEntiretyPhoto(fileInfo.getRelativePath());
				noLabelGoods.setEntiretyPhotoName(photoUploadDto.getEntiretyPhotoFileName());		
			}
			//内物照
			if(photoUploadDto.getGoodsPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getGoodsPhoto());
				noLabelGoods.setGoodsPhoto(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoName(photoUploadDto.getGoodsPhotoFileName());		
			}
			//附加照1
			if(photoUploadDto.getPhotoA() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getPhotoA());
				noLabelGoods.setGoodsPhotoA(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoAName(photoUploadDto.getPhotoAFileName());		
			}
			//附加照2
			if(photoUploadDto.getPhotoB() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getPhotoB());
				noLabelGoods.setGoodsPhotoB(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoBName(photoUploadDto.getPhotoBFileName());		
			}
			
			//根据总件数生成并格式化流水号
			int goodsQty = noLabelGoods.getGoodsQty();
			DecimalFormat df = new DecimalFormat();
			String style = ExceptionGoodsConstants.SERIAL_NO_STYLE;
			df.applyPattern(style);
			//设置状态 库存、找货、上报OA
			noLabelGoods.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_NO_IN);
			noLabelGoods.setFindGoodsStatus(ExceptionGoodsConstants.NO_LABEL_GOODS_NO_FIND);
			noLabelGoods.setIsReportOa(FossConstants.NO);
			//设置无标签运单号
			noLabelGoods.setNoLabelBillNo(tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQYDH));
			for(int i=1;i<=goodsQty;i++){
				//设置流水号
				noLabelGoods.setNoLabelSerialNo(df.format(i));
				//生成无标签多货编号不要影响无标签信息的保存
				try{
					//设置无标签多货编号
					String oaErrorNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.WBQBH);
					noLabelGoods.setOaErrorNo(oaErrorNo);
				} catch(Exception e) {
					LOGGER.error("无标签多货运单号：" + noLabelGoods.getNoLabelBillNo() + ",流水号：" + noLabelGoods.getNoLabelSerialNo() + "生成无标签多货编号异常");
				}
				//保存无标签货物
				noLabelGoodsDao.addNoLabelGoods(noLabelGoods);
				
				// 保存时，同时进行登入操作
				List<NoLabelGoodsEntity> noLabelGoodsList = new ArrayList<NoLabelGoodsEntity>();
				noLabelGoodsList.add(noLabelGoods);
				this.loginExceptionStock(noLabelGoodsList, noLabelGoods.getCreateUserCode(), noLabelGoods.getCreateUserName(), FossUserContext.getCurrentDeptCode());	
			}
		}catch (NoLabelGoodsException e) {
			throw new NoLabelGoodsException("保存无标签货物失败","保存无标签货物失败");
		}
		return FossConstants.SUCCESS;
		
	}
	
	/**
	 * 查询无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:12:38
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#queryNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public List<NoLabelGoodsEntity> queryNoLabelGoods(
			NoLabelGoodsEntity noLabelGoods, int limit, int start) {
		return noLabelGoodsDao.queryNoLabelGoods(noLabelGoods, limit, start);
	}


	/**
	 * 查询无标签货物总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:44:03
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#queryNoLabelGoodsCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public Long queryNoLabelGoodsCount(NoLabelGoodsEntity noLabelGoods) {
		return noLabelGoodsDao.queryNoLabelGoodsCount(noLabelGoods);
	}
	/**
	 * 生成无标签货物运单号
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午1:12:43
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#generateNoLabelGoodsBillNo()
	 */
	public String generateNoLabelGoodsBillNo(){
		return tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.WBQYDH);
	}
	
	/**
	 * 登入异常货区
	 * 更新无标签货物库存状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午11:39:42
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#loginExceptionStock(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int loginExceptionStock(List<NoLabelGoodsEntity> noLabelGoodsList, String userCode, String userName, String currentOrgCode) throws BusinessException{
		
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		
		//调用综合接口获取当前外场部门
		List<String> bizTypesList = new ArrayList<String>();
		//设置查询参数：外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//设置库存部门
			inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
		}else{
			//查询外场部门失败
			LOGGER.error("查询外场部门失败");
			throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_TRANSFER_CENTER_ORG_ERROR_CODE,"查询外场部门失败");
		}
		
		inOutStockEntity.setOperatorCode(userCode);
		inOutStockEntity.setOperatorName(userName);
		
		for(NoLabelGoodsEntity noLabelGoods : noLabelGoodsList){
			inOutStockEntity.setWaybillNO(noLabelGoods.getNoLabelBillNo());
			inOutStockEntity.setSerialNO(noLabelGoods.getNoLabelSerialNo());
			inOutStockEntity.setInOutStockType(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE);
			//入库
			try{
				stockService.inStockPC(inOutStockEntity);
			}catch(BusinessException e){
				throw new NoLabelGoodsException(NoLabelGoodsException.IN_STOCK_EXCEPTION_ERROR_CODE,"入库失败");
			}
			//更新库存状态
			noLabelGoods.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_IN);
			noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 登出异常货区
	 * 更新无标签货物库存状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 下午3:57:30
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#logoutExceptionStock(java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int logoutExceptionStock(List<NoLabelGoodsEntity> noLabelGoodsList,
			String userCode, String userName, String currentOrgCode) throws BusinessException{
		
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		
		//调用综合接口获取当前外场部门
		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
		}else{
			//查询外场部门失败
			LOGGER.error("查询外场部门失败");
			throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_TRANSFER_CENTER_ORG_ERROR_CODE,"获取外场部门失败");
		}
		
		inOutStockEntity.setOperatorCode(userCode);
		inOutStockEntity.setOperatorName(userName);
		
		for(NoLabelGoodsEntity noLabelGoods : noLabelGoodsList){
			//封装无标签货出库参数
			inOutStockEntity.setWaybillNO(noLabelGoods.getNoLabelBillNo());
			inOutStockEntity.setSerialNO(noLabelGoods.getNoLabelSerialNo());
			inOutStockEntity.setInOutStockType(StockConstants.NO_LABEL_ORIGINAL_GOODS_IN_STOCK_TYPE);
			//出库异常货区
			stockService.outStockPC(inOutStockEntity);
			//封装原货件入库正常货区参数
			inOutStockEntity.setWaybillNO(noLabelGoods.getOriginalWaybillNo());
			inOutStockEntity.setSerialNO(noLabelGoods.getOriginalSerialNo());
			//原货件入库
			stockService.inStockPC(inOutStockEntity);
			//更新库存状态
			noLabelGoods.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_OUT);
			noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
		}
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 更新无标签货物的打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午11:39:22
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#updateNoLabelGoodsPrintInfo(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int updateNoLabelGoodsPrintInfo(List<NoLabelGoodsEntity> noLabelGoodsList, String userCode, String userName) {
		Date printTime = new Date();
		for(NoLabelGoodsEntity noLabelGoods : noLabelGoodsList){
			//打印时间
			noLabelGoods.setPrintTime(printTime);
			//打印操作人
			noLabelGoods.setPrintUserCode(userCode);
			noLabelGoods.setPrintUserName(userName);
			//打印操作人
			noLabelGoods.setIsPrintNoLabel(FossConstants.YES);
			//更新
			noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * 上报OA
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-29 上午11:08:27
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#reportOANoLabelGoods(java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public void reportOANoLabelGoods(List<NoLabelGoodsEntity> noLabelGoodsList,
			String userCode, String userName) throws BusinessException {
		
		LOGGER.debug("********无标签上报OA开始************");
		OaReportNolabel oaReportNolabel = new OaReportNolabel();
		Date reportOATime = new Date();
		for(NoLabelGoodsEntity noLabelGoods : noLabelGoodsList){
			//*************封装上报参数*********************
			oaReportNolabel.setNoLabelWayBill(noLabelGoods.getNoLabelBillNo());
			oaReportNolabel.setNoLabelSerail(noLabelGoods.getNoLabelSerialNo());
			oaReportNolabel.setUserId(userCode);
			List<String> bizTypesList = new ArrayList<String>();
			//大区类型
			bizTypesList.add(BizTypeConstants.ORG_BIG_REGION);
			//事业部类型
			bizTypesList.add(BizTypeConstants.ORG_DIVISION);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(noLabelGoods.getFindOrgCode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				oaReportNolabel.setFinalSysCode(orgAdministrativeInfoEntity.getUnifiedCode());
			}else{
				throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_BIG_REGION_ERROR_CODE,"查询所属大区或事业部失败");
			}
			//正面照
			oaReportNolabel.setImageNw(noLabelGoods.getFrontPhoto() + "," + noLabelGoods.getFrontPhotoName());
			//整体照
			oaReportNolabel.setImageZm(noLabelGoods.getEntiretyPhoto() + "," + noLabelGoods.getEntiretyPhotoName());
			//内物照
			oaReportNolabel.setImageZt(noLabelGoods.getGoodsPhoto() + "," + noLabelGoods.getGoodsPhotoName());
			//品类
			oaReportNolabel.setCategorys(noLabelGoods.getGoodsType());
			//品牌
			oaReportNolabel.setBrand(noLabelGoods.getGoodsBrand());
			//货物名称
			oaReportNolabel.setGoodsName(noLabelGoods.getGoodsName());
			//包装关键字
			oaReportNolabel.setPackageKey(noLabelGoods.getPackageKeyword());
			//包装
			oaReportNolabel.setGoodsPacked(noLabelGoods.getPackageType());
			//内物属性
			oaReportNolabel.setAttributes(noLabelGoods.getGoodsProperty());
			//重量
			oaReportNolabel.setWeight(noLabelGoods.getWeight().doubleValue());
			//体积
			oaReportNolabel.setVolume(noLabelGoods.getVolume().doubleValue());
			//手写关键字
			oaReportNolabel.setHandKey(noLabelGoods.getHandwritingKeyword());
			//事件经过
			oaReportNolabel.setEventReport(noLabelGoods.getEventProcess());
			//是否泄漏货
			oaReportNolabel.setLeakGoods(noLabelGoods.getLeakGoods());
			//**************非必填字段*****************
			//是否为快递货
			oaReportNolabel.setExpressGoods(noLabelGoods.getExpressGoods());
			//上一部门
			oaReportNolabel.setDeptOrgid(noLabelGoods.getPreviousOrgCode()); 
			oaReportNolabel.setDeptName(noLabelGoods.getPreviousOrgName());
			//发现货区
			oaReportNolabel.setFindPlace(noLabelGoods.getGoodsAreaName());
			//发现时间
			oaReportNolabel.setGafTime(noLabelGoods.getFindTime());
			//少货备注
			oaReportNolabel.setLessGoodsNote(noLabelGoods.getLossGoodsNotes());
			//短信通知对象
			oaReportNolabel.setNoticeObjects(noLabelGoods.getNoteNotifyPerson());
			//交接单号
			oaReportNolabel.setReplayBill(noLabelGoods.getHandoverNo());
			//开始上报
			ResponseDto responseDto = fossToOAService.reportNolabel(oaReportNolabel);
			if(responseDto != null && StringUtils.isNotBlank(responseDto.getErrorsNo())){
				//更新无标签货物上报OA信息
				noLabelGoods.setOaErrorNo(responseDto.getErrorsNo());
				noLabelGoods.setReportOATime(reportOATime);
				noLabelGoods.setReportOAUserCode(userCode);
				noLabelGoods.setReportOAUserName(userName);
				noLabelGoods.setIsReportOa(FossConstants.YES);
				noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
			}else{
				//上报失败
				LOGGER.error("无标签多货上报OA失败：OA返回差错编号为空");
				throw new NoLabelGoodsException(NoLabelGoodsException.REPORT_OA_ERROR_CODE,"无标签多货上报失败：返回差错编号为空");
			}
		}
		LOGGER.debug("********无标签上报OA结束**********");
	}
	
	/**
	 * 更新无标签货物的OA处理结果
	 * @param noLabelBillNo  无标签运单号
	 * @param findGoodsStatus  找货状态
	 * @param oaErrorCode  OA差错编号
	 * @param originalWaybillNo  原运单号
	 * @param originalSerialNo  原流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午11:15:15
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#updateNoLabelGoodsProcessStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateNoLabelGoodsProcessStatus(String noLabelBillNo,
			String findGoodsStatus, String oaErrorNo,
			String originalWaybillNo, String originalSerialNo) {
		
		NoLabelGoodsEntity noLabelGoods = new NoLabelGoodsEntity();
		//无标签运单号
		noLabelGoods.setNoLabelBillNo(noLabelBillNo);
		//找货状态
		noLabelGoods.setFindGoodsStatus(ExceptionGoodsConstants.NO_LABEL_GOODS_FIND);
		//OA差错编号
		noLabelGoods.setOaErrorNo(oaErrorNo);
		//原运单号
		noLabelGoods.setOriginalWaybillNo(originalWaybillNo);
		//原流水号
		noLabelGoods.setOriginalSerialNo(originalSerialNo);
		//无标签多货状态
		noLabelGoods.setGoodsStatus(ExceptionGoodsConstants.GOODS_STATUS_CONFIRM);
		//更新
		int updateQty = noLabelGoodsDao.updateProcessStatus(noLabelGoods);
		
		return updateQty;
	}
	
	/**
	* @description 转弃货申请传入一个差异编号oaErrorNo，中转调用OA接口获取对应的状态，将OA返回的状态 再返回给异常货处理模块
	* @param oaErrorNo OA差错编号
	* @return 0:变更成功; 1:货物已被认领 ; 2：其他原因变更失败;
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月22日 上午9:59:27
	*/
	@Override
	public int updateNoLabelGoodsProcessStatus(String oaErrorNo)  throws Exception {
		int backStatusByOA = 2;
		String backStatus = fossToOAService.updateGoodsArriverelay(oaErrorNo);
//		if(backStatus!=null){
//			try{
				backStatusByOA = Integer.parseInt(backStatus);
//			}catch(Exception e){
//				return backStatusByOA;
//			}
//		}
		return backStatusByOA;
	}

	/**
	 * 查询当前用户所在大部门（外场）
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-19 上午10:33:10
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#queryBigOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity queryBigOrg(OrgAdministrativeInfoEntity currentOrg){
		if(StringUtils.endsWith(FossConstants.YES, currentOrg.getTransferCenter())){
			return currentOrg;
		}
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrg.getCode(), bizTypesList);
		
		if(orgAdministrativeInfoEntity != null){
			return orgAdministrativeInfoEntity;
		}else{
			//查询用户所属外场部门失败
			LOGGER.error("查询用户所属外场部门失败");
			throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_USER_TRANSFER_CENTER_ERROR_CODE,"查询用户所属外场部门失败");
		}
	}
	/**
	 * 验证用户名 密码
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-24 下午3:50:35
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#verifyUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean verifyUser(String userCode, String password){
		return loginService.validateUser(userCode, password);
	}
	
	/**
	 * 根据无标签单号更新该单号下的所有无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-10 下午6:32:52
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#updateNoLabelGoodsByBillNo(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public int updateNoLabelGoodsByBillNo(NoLabelGoodsEntity noLabelGoods, PhotoUploadDto photoUploadDto){
		/**
		 * BUG-50471 
		 * FOSS无标签上报，上报条数超过一定条数就不能上报了，
		 * 昨天不能上报，今天上报条数好像是五条吧，就又不能上报了，
		 * 症状为：填写完毕，点击保存后什么都不显示了
		 * 解决：错误处理。页面保存时添加进度条。
		 */
		try{
			//*************上传图片****************
			//正面照
			if(photoUploadDto.getFrontPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getFrontPhoto());
				noLabelGoods.setFrontPhoto(fileInfo.getRelativePath());
				noLabelGoods.setFrontPhotoName(photoUploadDto.getFrontPhotoFileName());		
			}
			//整体照
			if(photoUploadDto.getEntiretyPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getEntiretyPhoto());
				noLabelGoods.setEntiretyPhoto(fileInfo.getRelativePath());
				noLabelGoods.setEntiretyPhotoName(photoUploadDto.getEntiretyPhotoFileName());		
			}
			//内物照
			if(photoUploadDto.getGoodsPhoto() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getGoodsPhoto());
				noLabelGoods.setGoodsPhoto(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoName(photoUploadDto.getGoodsPhotoFileName());		
			}
			//附加照1
			if(photoUploadDto.getPhotoA() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getPhotoA());
				noLabelGoods.setGoodsPhotoA(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoAName(photoUploadDto.getPhotoAFileName());		
			}
			//附加照2
			if(photoUploadDto.getPhotoB() != null){
				FileInfo fileInfo = fileManager.create(photoUploadDto.getPhotoB());
				noLabelGoods.setGoodsPhotoB(fileInfo.getRelativePath());
				noLabelGoods.setGoodsPhotoBName(photoUploadDto.getPhotoBFileName());		
			}
		}catch (NoLabelGoodsException e) {
			throw new NoLabelGoodsException("更新无标签货物失败","更新无标签货物失败");
		}
		//更新
		return noLabelGoodsDao.updateNoLabelGoodsByBillNo(noLabelGoods);
	}
	
	/**
	 * 删除无标签多货
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-15 上午8:45:40
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#deleteNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Transactional
	@Override
	public int deleteNoLabelGoods(NoLabelGoodsEntity noLabelGoods) {
		//删除
		noLabelGoodsDao.deleteNoLabelGoods(noLabelGoods.getId());
		//更新该票其他货件的总件数
		if(noLabelGoods.getGoodsQty() > 1){
			noLabelGoodsDao.updateGoodsQty(noLabelGoods.getNoLabelBillNo(), noLabelGoods.getGoodsQty()-1);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据无标签运单号、流水号查询
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午4:19:51
	 */
	@Override
	public NoLabelGoodsEntity queryUniqNoLabelGoods(String noLabelBillNo, String noLabelSerialNo){
		List<NoLabelGoodsEntity> noLabelGoodsList = noLabelGoodsDao.queryUniqNoLabelGoods(noLabelBillNo, noLabelSerialNo);
		if(CollectionUtils.isNotEmpty(noLabelGoodsList)){
			return noLabelGoodsList.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 更新无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午5:47:09
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#updateNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public int updateNoLabelGoods(NoLabelGoodsEntity noLabelGoods) {
		
		return noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
	}
	
	/**
	 * 无标签转弃货的业务流程
	 * @param noLabelGoodsPojo
	 * @throws BusinessException
	 */
	@Override
	public void toAbandonService(NoLabelGoodsVo noLabelGoodsPojo) {
		//转弃货相关参数的准备
		/*无标签运单号：AbandonGoodsApplicationEntity.waybillNo
		无标签流水号：AbandonGoodsApplicationEntity.serialNumber
		申请人：AbandonGoodsApplicationEntity.createUserCode
		 和AbandonGoodsApplicationEntity.createUserName
		///所属区域：AbandonGoodsApplicationEntity.createOrgCode
		   ///  和AbandonGoodsApplicationEntity.createOrgName
		OA差错编号：AbandonGoodsApplicationEntity.errorNumber
		申请事由：AbandonGoodsApplicationEntity.notes
		
		品名：AbandonGoodsApplicationEntity.goodsName
		重量：AbandonGoodsApplicationEntity.goodsWeightTotal
		体积：AbandonGoodsApplicationEntity.goodsVolumeTotal
		件数：AbandonGoodsApplicationEntity.goodsQtyTotal
		库存时长：AbandonGoodsApplicationEntity.storageDay
		仓储部门：AbandonGoodsApplicationEntity.lastStorageOrgCode
		
		操作部门：AbandonGoodsApplicationEntity.createOrgCode
			     和AbandonGoodsApplicationEntity.createOrgName
		所属区域：AbandonGoodsApplicationEntity.belongAreaCode

		*/
		AbandonGoodsApplicationEntity pojo = new AbandonGoodsApplicationEntity();
		pojo.setWaybillNo(noLabelGoodsPojo.getNoLabelGoodsEntity().getNoLabelBillNo());
		pojo.setSerialNumber(noLabelGoodsPojo.getNoLabelGoodsEntity().getNoLabelSerialNo());
		pojo.setCreateUserCode(noLabelGoodsPojo.getUserCode());
		pojo.setCreateUserName(noLabelGoodsPojo.getUserName());
//		pojo.setCreateOrgCode(noLabelGoodsPojo.getBigOrgCode());
//		pojo.setCreateOrgName(noLabelGoodsPojo.getBigOrgName());
		pojo.setErrorNumber(noLabelGoodsPojo.getNoLabelGoodsEntity().getOaErrorNo());
		pojo.setNotes(noLabelGoodsPojo.getToAbandonNotes());
		pojo.setGoodsName(noLabelGoodsPojo.getNoLabelGoodsEntity().getGoodsName());
		pojo.setGoodsWeightTotal(noLabelGoodsPojo.getNoLabelGoodsEntity().getWeight());
		pojo.setGoodsVolumeTotal(noLabelGoodsPojo.getNoLabelGoodsEntity().getVolume());
		pojo.setGoodsQtyTotal(noLabelGoodsPojo.getNoLabelGoodsEntity().getGoodsQty());
		pojo.setLastStorageOrgCode(noLabelGoodsPojo.getNoLabelGoodsEntity().getFindOrgCode());
		
		/*操作部门：AbandonGoodsApplicationEntity.createOrgCode
			     和AbandonGoodsApplicationEntity.createOrgName
		所属区域：AbandonGoodsApplicationEntity.belongAreaCode*/
		pojo.setCreateOrgCode(noLabelGoodsPojo.getNoLabelGoodsEntity().getFindOrgCode());
		pojo.setCreateOrgName(noLabelGoodsPojo.getNoLabelGoodsEntity().getFindOrgName());
		pojo.setBelongAreaCode(noLabelGoodsPojo.getBigOrgCode());

		//计算上报时间
		if(noLabelGoodsPojo.getNoLabelGoodsEntity()!=null && noLabelGoodsPojo.getNoLabelGoodsEntity().getReportOATime()!=null){
			Date tempDate = new Date();
		    long tempNum = tempDate.getTime() - noLabelGoodsPojo.getNoLabelGoodsEntity().getReportOATime().getTime();
		    tempNum = tempNum/(ExceptionGoodsConstants.SONAR_NUMBER_1000*ExceptionGoodsConstants.SONAR_NUMBER_60*ExceptionGoodsConstants.SONAR_NUMBER_60*ExceptionGoodsConstants.SONAR_NUMBER_24);//精确到天
		    try {
				pojo.setStorageDay(Integer.parseInt(tempNum+""));
			} catch (NumberFormatException e) {
				pojo.setStorageDay(0);
			}
		}else{
			pojo.setStorageDay(0);
		}
		
		//转弃货接口调用
		try{
			abandonGoodsApplicationService.insertAgEntityForNoTagTransfer(pojo);
		}catch(BusinessException e){
			throw e;
		}
		//更新无标签记录的状态为已转弃货
		NoLabelGoodsEntity noLabelPojo = noLabelGoodsPojo.getNoLabelGoodsEntity();
		noLabelPojo.setFindGoodsStatus(ExceptionGoodsConstants.NO_LABEL_GOODS_TO_ABANDON);
		try{
			//noLabelGoodsDao.updateNoLabelGoods(noLabelPojo);
			noLabelGoodsDao.updateNoLabelGoods(noLabelPojo);
		}catch(Exception e){
			throw new BusinessException("更新状态失败",e.getCause());
		}
		
	}
	
	public String xx (){
		return "xxxxx";
	}

	
	/**
	* @description 上分拣扫描同时判断此外场是否是此运单号的始发外场
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#sortingAndPringLabel(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午3:11:27
	* @version V1.0
	*/
	@Transactional
	@Override
	public List<BarcodePrintLabelDto> sortingAndPringLabel(String waybillNo,String serialNo) {
		List<BarcodePrintLabelDto> barcodePrintDtoList = null;
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//部门code
		String orgCode = FossUserContext.getCurrentDeptCode();
		//部门name
		String orgName = FossUserContext.getCurrentDeptName();
		SortingScanEntity sortingScanEntity = new SortingScanEntity();
		sortingScanEntity.setDeviceNo("BSC");
		sortingScanEntity.setOperatorCode(userCode);
		sortingScanEntity.setOperatorName(userName);
		sortingScanEntity.setOrgName(orgName);  
		sortingScanEntity.setOrgCode(orgCode);
		sortingScanEntity.setScanType(TransferPDADictConstants.SORTING_SCAN_TYPE_UP);
		sortingScanEntity.setScanTime(new Date());
		sortingScanEntity.setSerialNo(serialNo);
		sortingScanEntity.setWayBillNo(waybillNo);
		sortingScanEntity.setCreateTime(new Date());
		sortingScanEntity.setId(UUIDUtils.getUUID());
		pdaSortingDao.insertSortingScan(sortingScanEntity);
		
		String objectOrgCode = null;
		//判断运单是否分批 tfr.t_opt_transport_path   如果为分批 查找第一外场时就不用运单号        如果分批  就根据运单号,流水号,路段号去查找第一外场
		String isPartial = noLabelGoodsDao.selectIsPartial(waybillNo);
		
		//分批
		if("N".equals(isPartial)){
			objectOrgCode = noLabelGoodsDao.selectFirstOrgCodeIsPartial(waybillNo , 1);
			if(objectOrgCode == null){
				throw new NoLabelGoodsException("查询第一外场失败","查询第一外场失败");
			}
			//判断根据走货路径查询到的第一个部门是否是快递分部,如果是就找第二个部门,如果不是就找第一个部门
			String isExpressBranch = noLabelGoodsDao.selectIsExpressBranch(objectOrgCode); 
			//如果是快递分部,第一外场就是走货路径的第二个部门
			if("Y".equals(isExpressBranch)){
				objectOrgCode = noLabelGoodsDao.selectFirstOrgCodeIsPartial(waybillNo , 2);
			}
		}else{
			//判断此外场是否是此运单号的始发外场  如果是:打印标签   如果不是:不打印
			//从路径明细中去找始发外场   tfr.t_Opt_Path_Detail
			//根据运单号,流水号,线路段号 ROUTE_NO = 1 找到下一部门,此部门就是第一外场  然后和本orgCode比较
			objectOrgCode = noLabelGoodsDao.selectFirstOrgCode(waybillNo , serialNo , 1); 
			if(objectOrgCode == null){
				throw new NoLabelGoodsException("查询第一外场失败","查询第一外场失败");
			}
			//判断根据走货路径查询到的第一个部门是否是快递分部,如果是就找第二个部门,如果不是就找第一个部门
			String isExpressBranch = noLabelGoodsDao.selectIsExpressBranch(objectOrgCode); 
			//如果是快递分部,第一外场就是走货路径的第二个部门
			if("Y".equals(isExpressBranch)){
				objectOrgCode = noLabelGoodsDao.selectFirstOrgCode(waybillNo , serialNo , 2);
			}
		}
		
		//是第一外场  打印标签 
		if(orgCode.equals(objectOrgCode)){
			List<String> sList = new ArrayList<String>();
			sList.add(serialNo);
			barcodePrintDtoList = printLabelService.getLabelPrintInfoExpress(waybillNo, sList);
//			printLabelService.addPrintAppointedLabel(waybillNo, sList, userCode,ExceptionGoodsConstants.PRINT_TYPE);
		}else{
			
			//什么也不做
		}
		return barcodePrintDtoList;
	}
	
	@Override
	public String isFirstTransfer(String waybillNo,String serialNo, String orgCode) {
		String objectOrgCode = null;
		//判断运单是否分批 tfr.t_opt_transport_path   如果为分批 查找第一外场时就不用运单号        如果分批  就根据运单号,流水号,路段号去查找第一外场
		String isPartial = noLabelGoodsDao.selectIsPartial(waybillNo);
		
		//分批
		if("N".equals(isPartial)){
			objectOrgCode = noLabelGoodsDao.selectFirstOrgCodeIsPartial(waybillNo , 1);
			if(objectOrgCode == null){
				throw new NoLabelGoodsException("查询第一外场失败","");
			}
			//判断根据走货路径查询到的第一个部门是否是快递分部,如果是就找第二个部门,如果不是就找第一个部门
			String isExpressBranch = noLabelGoodsDao.selectIsExpressBranch(objectOrgCode); 
			//如果是快递分部,第一外场就是走货路径的第二个部门
			if("Y".equals(isExpressBranch)){
				objectOrgCode = noLabelGoodsDao.selectFirstOrgCodeIsPartial(waybillNo , 2);
			}
		}else{
			objectOrgCode = noLabelGoodsDao.selectFirstOrgCode(waybillNo , serialNo , 1); 
			if(objectOrgCode == null){
				throw new NoLabelGoodsException("查询第一外场失败","查询第一外场失败");
			}
			//判断根据走货路径查询到的第一个部门是否是快递分部,如果是就找第二个部门,如果不是就找第一个部门
			String isExpressBranch = noLabelGoodsDao.selectIsExpressBranch(objectOrgCode); 
			//如果是快递分部,第一外场就是走货路径的第二个部门
			if("Y".equals(isExpressBranch)){
				objectOrgCode = noLabelGoodsDao.selectFirstOrgCode(waybillNo , serialNo , 2);
			}
		}
		if(orgCode.equals(objectOrgCode)){
			return "Y";
		}else{
			return "N";
		}
		
	}
	/**
	* @description 获取打印内容,保存打印指定标签操作信息 同时进行上分拣扫描
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService#printAppointedLabelExpress(java.lang.String, java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午4:38:04
	* @version V1.0
	*/
	@Transactional
	@Override
	public List<BarcodePrintLabelDto> printAppointedLabelExpress(String waybillNo, String serialNo , List<String> sList) throws BusinessException{
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//部门code
		String orgCode = FossUserContext.getCurrentDeptCode();
		//部门name
		String orgName = FossUserContext.getCurrentDeptName();
		SortingScanEntity sortingScanEntity = new SortingScanEntity();
		sortingScanEntity.setDeviceNo("BSC");
		sortingScanEntity.setOperatorCode(userCode);
		sortingScanEntity.setOperatorName(userName);
		sortingScanEntity.setOrgName(orgName);
		sortingScanEntity.setOrgCode(orgCode);
		sortingScanEntity.setScanType(TransferPDADictConstants.SORTING_SCAN_TYPE_UP);
		sortingScanEntity.setScanTime(new Date());
		sortingScanEntity.setSerialNo(serialNo);
		sortingScanEntity.setWayBillNo(waybillNo);
		sortingScanEntity.setCreateTime(new Date());
		sortingScanEntity.setId(UUIDUtils.getUUID());
		
		List<BarcodePrintLabelDto> barcodePrintDtoList = null;
		barcodePrintDtoList = printLabelService.getLabelPrintInfoExpress(waybillNo, sList);
		//上分拣
		pdaSortingDao.insertSortingScan(sortingScanEntity);
		return barcodePrintDtoList;
	}
	
	/**
	 * @author nly
	 * @date 2015年6月15日 下午4:16:52
	 * @function 根据品名查询品类
	 * @param goodsName
	 * @return
	 */
	@Override
	public String queryTypeByGoodsName(String goodsName) {
		String category = "OTHER";
		List<SourceCategoriesEntity> list = sourceCategoriesService.querySourceCategoriesEntitysByName(goodsName);
		if(CollectionUtils.isNotEmpty(list)) {
			for(SourceCategoriesEntity entity : list) {
				if(null != entity && StringUtils.isNotEmpty(entity.getCategory())) {
					category = ExceptionGoodsConstants.goodsTypesMap.get(entity.getCategory().trim());			
					break;
				}
			}
		} 
		return category;
	}
	
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:50:10
	 * @function 上报QMS无标签多货
	 */
	@Override
	public void reportQmsNoLabelGoods() {
		LOGGER.error("-----上报QMS无标签多货开始-----");
		//查询需要上报的无标签多货列表
		
		//默认为需求上线时间
		Date bizDate = DateUtils.strToDate("2015-09-28 13:00:00");
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_REPORT_NOLABEL_CREATE_TIME, 
					FossConstants.ROOT_ORG_CODE);
			if(defaultBizHourSlice != null && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())){
				bizDate = DateUtils.strToDate(defaultBizHourSlice.getConfValue());
			}
			LOGGER.error("配置参数值"+bizDate);
		} catch(Exception e){
			LOGGER.error("获取配置参数失败");
		}		
		List<NoLabelGoodsEntity> nolabelGoodsList = noLabelGoodsDao.queryNoLabelReportList(bizDate);
		
		for(NoLabelGoodsEntity entity : nolabelGoodsList) {
			try{
				LOGGER.error("-----上报QMS无标签多货开始，无标签运单号：" + entity.getNoLabelBillNo() + "无标签流水号：" + entity.getNoLabelSerialNo() + ",无标签多货编号：" + entity.getOaErrorNo() + "-----");
				QmsNolabelEntity labelEntity = new QmsNolabelEntity();
				//品名
				labelEntity.setGoodsName(entity.getGoodsName());
				//无标签运单号
				labelEntity.setNoTranslateCode(entity.getNoLabelBillNo());
				//无标签多货流水号
				labelEntity.setFolwCode(entity.getNoLabelSerialNo());
				//品牌
				labelEntity.setGoodsBrand(entity.getGoodsBrand());
				//品类
				labelEntity.setGoodsKinds(ExceptionGoodsConstants.goodsTypeMap.get(entity.getGoodsType()));
				//车牌号
				labelEntity.setCarNo(entity.getVehicleNo());
				//包装
				labelEntity.setGoodsPackage(ExceptionGoodsConstants.packageTypeMap.get(entity.getPackageType()));
				//重量
				labelEntity.setBillWeight(null == entity.getWeight() ? "" : entity.getWeight().toString());
				//手写关键字
				labelEntity.setKeyWord(entity.getHandwritingKeyword());
				// 尺寸 
				labelEntity.setGoodsSize(entity.getVolumeLWH());
				// 体积 
				labelEntity.setVolume(null == entity.getVolume() ? "" : entity.getVolume().toString());
				// 上报人工号 
				labelEntity.setSubEmpCode(entity.getCreateUserCode());
				// 上报人 
				labelEntity.setApplyuserName(entity.getCreateUserName());
				// 上一环节部门标杆编码 
				if(StringUtils.isNotEmpty(entity.getPreviousOrgCode())) {
					OrgAdministrativeInfoEntity  orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPreviousOrgCode());
					if(null != orgEntity) {
						labelEntity.setLinkDeptCode(orgEntity.getUnifiedCode());
					}
				}
				// 上一环节部门 
				labelEntity.setLinkDept(entity.getPreviousOrgName());
				// 发现货区 
				labelEntity.setFindArea(entity.getGoodsAreaName());
				// 发货时间 
				labelEntity.setDeliveryTime(entity.getFindTime());
				//无标签发现类型 
				labelEntity.setFindType(ExceptionGoodsConstants.findTypeMap.get(entity.getFindType()));
				// 发现人 
				labelEntity.setFindPerson(entity.getFindUserName());
				// 上报人部门标杆编码
				if(StringUtils.isNotEmpty(entity.getFindOrgCode())) {
					OrgAdministrativeInfoEntity  orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getFindOrgCode());
					if(null != orgEntity) {
						labelEntity.setSubDeptCode(orgEntity.getUnifiedCode());
					}
				}
				// 上报人部门 
				labelEntity.setSubDept(entity.getFindOrgName());
				// 总件数 
				labelEntity.setTotalNum(null == entity.getGoodsQty() ? "" : entity.getGoodsQty().toString());
				// 无标签多货编号,若为空时则重新生成
				if(StringUtils.isEmpty(entity.getOaErrorNo())) {
					String oaErrorNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.WBQBH);
					labelEntity.setNoLabelCode(oaErrorNo);
					entity.setOaErrorNo(oaErrorNo);
				} else {
					labelEntity.setNoLabelCode(entity.getOaErrorNo());
				}
				// 是否为快递货 
				labelEntity.setIsExp("Y".equals(entity.getExpressGoods()) ? "1" : "2");
				// 事情经过 
				labelEntity.setThProcess(entity.getEventProcess());
				// 是否泄漏货 
				labelEntity.setIsLeakage("Y".equals(entity.getLeakGoods()) ? "1" : "2");
				
				List<String> bizTypesList = new ArrayList<String>();
				//大区类型
				bizTypesList.add(BizTypeConstants.ORG_BIG_REGION);
				//事业部类型
				bizTypesList.add(BizTypeConstants.ORG_DIVISION);
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
						queryOrgAdministrativeInfoByCode(entity.getFindOrgCode(), bizTypesList);
				if(orgAdministrativeInfoEntity != null){
					// 事业部标杆编码
					labelEntity.setRespDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
					//事业部名称
					labelEntity.setRespDivisionName(orgAdministrativeInfoEntity.getName());
				}
				//上报人职位 
				EmployeeEntity  employee = employeeService.queryEmployeeByEmpCode(entity.getCreateUserCode());
				if(null != employee) {
					labelEntity.setSubEmpPos(employee.getTitleName());
				}
				
				// 丢货上报时间
				labelEntity.setLoseRepTime(entity.getCreateDate());
				// 上报时间 
				labelEntity.setRepTime(entity.getCreateDate());

				// 发现时间
				labelEntity.setFindTime(entity.getFindTime());
				//foss上传图片的根目录
				String rootDir = PropertiesUtil.getKeyValue("tfr.upload.dir");
				//上传正面照
				String facePhotoPath = uploadImageToQMS(rootDir,entity.getFrontPhoto(),entity.getFrontPhotoName());				
				// 正面照路径 
				labelEntity.setFacePhotoPath(facePhotoPath+","+entity.getFrontPhotoName());
				//上传整体照
				String wholePhotoPath = uploadImageToQMS(rootDir,entity.getEntiretyPhoto(),entity.getEntiretyPhotoName());	
				// 整体照路径 
				labelEntity.setWholePhotoPath(wholePhotoPath+","+entity.getEntiretyPhotoName());
				//上传内物照
				String insidePhotoPath = uploadImageToQMS(rootDir,entity.getGoodsPhoto(),entity.getGoodsPhotoName());	
				// 内物照路径 
				labelEntity.setInsidePhotoPath(insidePhotoPath+","+entity.getGoodsPhotoName());
				//上传附件照1
				String goodsPhotoPath1 = uploadImageToQMS(rootDir,entity.getGoodsPhotoA(),entity.getGoodsPhotoAName());	
				// 附件照1路径 
				labelEntity.setAddPhotoPath1(goodsPhotoPath1);
				//上传附件照2
				String goodsPhotoPath2 = uploadImageToQMS(rootDir,entity.getGoodsPhotoB(),entity.getGoodsPhotoBName());	
				// 附件照2路径 
				labelEntity.setAddPhotoPath2(goodsPhotoPath2);
				// 附件照1名称 
				labelEntity.setAddPhotoName1(entity.getGoodsPhotoAName());
				// 附件照2名称
				labelEntity.setAddPhotoName2(entity.getGoodsPhotoBName());
				
				QmsNolableRequestParam request = new QmsNolableRequestParam();
				request.setEntity(labelEntity);
				//上报QMS无标签多货
				ResponseFromQmsDto responseDto = tfrToQmsErrorService.reportQmsNolabel(request);
				if(null != responseDto && "1".equals(responseDto.getCallResult())) {
					//更新无标签货物上报OA信息
					entity.setReportOATime(new Date());
					entity.setReportOAUserCode(entity.getCreateUserCode());
					entity.setReportOAUserName(entity.getCreateUserName());
					entity.setIsReportOa(FossConstants.YES);
					noLabelGoodsDao.updateNoLabelGoods(entity);
				}
				LOGGER.error("-----上报QMS无标签多货结束，无标签运单号：" + entity.getNoLabelBillNo() + "无标签流水号：" + entity.getNoLabelSerialNo() + ",无标签多货编号：" + entity.getOaErrorNo() + "-----");
			} catch(Exception e){
				LOGGER.error("上报无标签多货出现异常，无标签多货运单号：" + entity.getNoLabelBillNo() + "，异常信息：" + e.getMessage());
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_OA.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_OA.getBizCode());
				jobProcessLogEntity.setExecBizId(entity.getNoLabelBillNo());
				jobProcessLogEntity.setExecTableName("无标签多货");
				jobProcessLogEntity.setRemark("上报无标签多货差错时出现异常");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				continue;
			}
		}
		
		LOGGER.error("-----上报QMS无标签多货结束-----");
	}
	
	/**
	 * @author nly
	 * @date 2015年6月30日 下午3:46:59
	 * @function QMS进行认领操作的同时，进行登出操作，故放在同一事务中
	 */
	@Transactional
	@Override
	public ResponseFromQmsDto findAndLogOut(NolabelRequestFromQMSDto nolabelDto,ResponseFromQmsDto responseDto) {

		int updateQty = this.updateNoLabelGoodsProcessStatus(nolabelDto.getNoLabelBillNo(), ExceptionGoodsConstants.NO_LABEL_GOODS_FIND, nolabelDto.getErrorCode(), nolabelDto.getOriginalWaybillNo(),nolabelDto.getOriginalSerialNo());
		if(updateQty < 1){
			//更新失败
			responseDto.setCallResult("0");
			responseDto.setErrmsg("认领失败，不存在无标签多货编号：" + nolabelDto.getErrorCode());
			LOGGER.error("*********QMS调用FOSS无标签多货找到接口，更新无标签处理结果失败，不存在无标签多货编号：" + nolabelDto.getErrorCode() + "************");
		}else{
			List<NoLabelGoodsEntity> noLabelGoodsList = new ArrayList<NoLabelGoodsEntity>();
			NoLabelGoodsEntity entity = new NoLabelGoodsEntity();
			entity.setNoLabelBillNo(nolabelDto.getNoLabelBillNo());
			entity.setNoLabelSerialNo(nolabelDto.getNoLabelSerialNo());
			entity.setOriginalWaybillNo(nolabelDto.getOriginalWaybillNo());
			entity.setOriginalSerialNo(nolabelDto.getOriginalSerialNo());
			entity.setFindUserCode(nolabelDto.getOperatorCode());
			entity.setFindUserName(nolabelDto.getOperatorName());
			entity.setFindOrgCode(nolabelDto.getOperateDeptCode());
			noLabelGoodsList.add(entity);
			//登出
			int logOut = this.logoutExceptionStock(noLabelGoodsList, nolabelDto.getOperatorCode(), nolabelDto.getOperatorName(), nolabelDto.getOperateDeptCode());
			if(logOut < 1) {
				LOGGER.error("*********QMS调用FOSS无标签多货找到接口，执行失败，登出库存失败************");
				throw new NoLabelGoodsException("登出失败","登出失败"); 
			} else {
				responseDto.setCallResult("1");
				LOGGER.info("*********QMS调用FOSS无标签多货找到接口，更新无标签处理结果完毕，差错编号：" + nolabelDto.getErrorCode() + "************");
			}		
		}
		return responseDto;
	}
	/**
	 * 上报差错时将图片也上传
	 * @throws Exception 
	 */
	private String uploadImageToQMS(String rootPath,String relativePath,String fileName) throws Exception{
		LOGGER.info("开始上传图片,路径为:"+rootPath + relativePath);
		//如果无相对路径，则未上传附件
		if(StringUtils.isBlank(relativePath)){
			return "";
		}
		//未设置根目录，则默认相对目录
		if(rootPath == null){
			rootPath = "";
		}
		if(StringUtils.isBlank(uploadImageToQMSPath)){
			throw new Exception("未设置图片上传路径！");
		}

	    PostMethod postMethod = new PostMethod(uploadImageToQMSPath);
	    try{
	    	File file = new File(rootPath + relativePath);
	    	FilePart fp = new FilePart("filedata",fileName,file);
	    //	new FilePart("filedata", fileName, file);
	    	Part[] parts = {fp};
	    	 //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装  
	    	MultipartRequestEntity mre = new MultipartRequestEntity(parts,postMethod.getParams());
	    	postMethod.setRequestEntity(mre);
	    	//设置连接时间
	    	HttpClient client = new HttpClient();
	    	client.getHttpConnectionManager().getParams().setConnectionTimeout(QmsErrorConstants.CONNECTION_TIMEOUT);
	    	int status = client.executeMethod(postMethod);
	    	if(status == HttpStatus.SC_OK){
	    		/*
	    		 * 返回数据格式
	    		 * [{"attachmentId":0,"fielSize":775702,"fileName":"2015-10-19-689db7e7-7aa7-4db8-bb72-aeef84a2ad55",
	    		 * "fileType":"","saveName":"1764d25e-33f9-417b-87f4-141977159401",
	    		 * "savePath":"/qms/upload/201510/19/1764d25e-33f9-417b-87f4-141977159401"}]
	    		 */
	    		String response = postMethod.getResponseBodyAsString();
	    		JSONArray responseArray = JSON.parseArray(response);
	    		String responseJson = responseArray.get(0).toString();
	    		LOGGER.info("图片上传成功，返回值："+response);
	    		if(responseJson != null){
	    			return JSON.parseObject(responseJson).get("savePath").toString();
	    		}
	        } else {  
	                String response = postMethod.getResponseBodyAsString();
	                LOGGER.info(response);
	        }  
	     } catch (Exception e) {  
	            LOGGER.info(e.getStackTrace());
	     } finally {  
	            //释放连接  
	    	 	if(postMethod != null){
	    	 		postMethod.releaseConnection();  
	    	 	}
	     }  
	    return "";
	}
}