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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirWaybillService.java
 *  
 *  FILE NAME          :AirWaybillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  SR-1	操作员打开界面：
1、	配载类型默认为全部；
2、	空运总调默认为当前操作人所在部门，根据用户数据权限可以修改，不可为空；
3、	制单起始时间默认为当天00:00:00，截止时间默认为当天23:59:59，不为空；
4、	目的站默认为空，输入范围为空运线路信息中的城市名称；
5、	航空公司默认为全部，来源于航空公司二字代码；
6、	正单号默认为空；
7、	默认不执行查询操作；

SR-2	点击查询按钮，执行查询操作，默认按制单时间降序排列；
SR-3	点击重置按钮，重置查询条件为默认值；
SR-4	点击操作列中的编辑控件，可对正单进行修改，
修改条件：
1、	操作员有修改正单权限；
2、	未收银的正单可以修改，判断正单是否收银；
3、	正单新增部门必须为本部门；

 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.shared.util.file.IOUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirTransPickupBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;

/**
 * 录入航空正单service实现类.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午6:42:39
 */
public class AirWaybillService implements IAirWaybillService {
	
	
	/**航空正单dao */
	private IAirWaybillDao airWayBillDao;
	
	/** 注入录入航空正单服务 */
	private IAirWaybillDetailService pointsSingleJointTicketService;
	
	/**
	 * 查询航空正单.
	 * @param airWayBillDto the air way bill dto
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:32
	 * @update 269701-foss-liulina-2016/04/26
	 * @update-param AirWayBillDto airWayBillDto 前台界面查询条件，int limit 最大行,int start 开始行
	 * @update-return List<AirWaybillEntity> resultAirWaybillNoList数据集
	 */
	@Override
	@Transactional
	public List<AirWaybillEntity> queryAirWayBillList(AirWayBillDto airWayBillDto,int limit ,int start) {
		//返回结果集
		List<AirWaybillEntity> resultAirWaybillNoList=new ArrayList<AirWaybillEntity>();
		List<AirWaybillEntity> airWaybillNoList= airWayBillDao.queryAirWayBillList(airWayBillDto,limit ,start);
		//269701--2016/04/26 begin
		//正单号
		String airWaybillNo =null;
		if(airWaybillNoList.size()>0){
			for(AirWaybillEntity entity:airWaybillNoList){
				airWaybillNo=entity.getAirWaybillNo();
				if(StringUtils.isEmpty(airWaybillNo)){
					throw new AirTransPickupBillException(
							AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDAIRWAYBILL);
				}
				//判断 配载类型
				//DDWFD--单独开单外发；HDPWF--合大票外发；DDKD--单独开单；HDP--合大票
				//“合大票外发”和“合大票”
				System.out.println("*************"+entity.getAirAssembleType());
				if(StringUtils.equals(entity.getAirAssembleType(), AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNO)
						||StringUtils.equals(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNOOUTSOURCE, entity.getAirAssembleType())){
				//根据正单号查询合大票，目的是 校验该正单号是否已经制作合大票
					boolean isPickUpMake=airWayBillDao.queryIsMakePickByBillNo(entity.getAirWaybillNo());
					if(isPickUpMake){
						//已经制作合大票
						entity.setAirPickState(FossConstants.YES);
					}else{
						//未制作合大票
						entity.setAirPickState(FossConstants.NO);
					}
					//单独开单外发和单独开单
				}else if(StringUtils.equals(entity.getAirAssembleType(), AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLINGOUTSOURCE)
						||StringUtils.equals(AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLING, entity.getAirAssembleType())){
					//“是否已做合大票”显示为“–”
					entity.setAirPickState("-");
				}
				System.out.println("*************"+entity.getAirPickState());
				resultAirWaybillNoList.add(entity);
			}
		}
		return resultAirWaybillNoList;
		//269701--2016/04/26 end
	}
	
	/**
	 * 查询航空正单（不分页）.
	 * @param airWayBillDto the air way bill dto
	 * @return the list
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:36:32
	 */
	@Override
	public List<AirWaybillEntity> queryAirWayBillListNoPage(AirWayBillDto airWayBillDto) {
		return airWayBillDao.queryAirWayBillListNoPage(airWayBillDto);
	}

	/**
	 * 根据ID查询航空正单.
	 * @param airWayBillDto the air way bill dto
	 * @return the air waybill entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:43
	 */
	@Override
	@Transactional(readOnly=true)
	public AirWaybillEntity queryResultEntity(AirWayBillDto airWayBillDto) {
		return airWayBillDao.queryResultEntity(airWayBillDto);
	}
	
	/**
	 * 获取总记录数.
	 * @param airWayBillDto the air way bill dto
	 * @return the count
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:56
	 */
	@Override
	public Long getCount(AirWayBillDto airWayBillDto) {
		return airWayBillDao.getCount(airWayBillDto);
	}
	
	/**
	 * 根据ID获取当前航空正单付款状态.
	 * @param airWayBillDto the air way bill dto
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:22
	 */
	@Override
	@Transactional(readOnly=true)
	public String queryStatus(AirWayBillDto airWayBillDto) {
		return airWayBillDao.queryStatus(airWayBillDto);
	}
	
	/**
	 * 根据ID获取数据.
	 * @param id the id
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillListByPrint(String id) {
		return airWayBillDao.queryAirWaybillListByPrint(id);
	}
	
	/**
	 * 根据ID获取打印数据.
	 * @param id the id
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillListForPrint(String id) {
		List<AirWaybillDetailEntity> airWaybillDetailEntityList = queryAirWaybillListByPrint(id);
		boolean f= false;
		//如果有包号的话，不去运单表里面查数据
		for(AirWaybillDetailEntity airWaybillDetail : airWaybillDetailEntityList){
			if((airWaybillDetail.getWaybillNo().substring(0, 1)).equals("B")){
				f=true;
				break;
			}
		}
		List<AirWaybillDetailEntity> airWaybillDetailEntityList1 = new ArrayList<AirWaybillDetailEntity>();
		if(f){
			for(AirWaybillDetailEntity airWaybillDetail : airWaybillDetailEntityList){
				if(!(airWaybillDetail.getWaybillNo().substring(0, 1)).equals("B")){
					airWaybillDetail=airWayBillDao.queryAirWaybillDetial(airWaybillDetail.getWaybillNo());
				}
				airWaybillDetailEntityList1.add(airWaybillDetail);
			}
			return airWaybillDetailEntityList1;
		}else{
			return airWayBillDao.queryAirWaybillListForPrint(id);
		}
	}
	
	/**
	 * 根据ID查询空运外发清单打印数据.
	 * @param airwaybillId the airwaybill id
	 * @return the air waybill entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-5 上午10:53:41
	 */	
	@Override
	public AirWaybillEntity queryAirWaybillEntityPrint(String airwaybillId) {
		return airWayBillDao.queryAirWaybillEntityPrint(airwaybillId);
	}
	
	/**
	 * 根据ID获取航空正单批量打印数据.
	 * @param ids the ids
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-6 下午2:30:08
	 */
	@Override
	public List<AirWaybillEntity> queryAirWaybillListPrint(String[] ids) {
		return airWayBillDao.queryAirWaybillListPrint(ids);
	}
	
	/**
	 * 提供给结算校验根据航空正单号和代理编码校验是否存在于空运配载单、合大票清单和中转清单中是否存在记录.
	 * @param airwaybillNo the airwaybill no
	 * @param destOrgCode the dest org code
	 * @return true, if successful
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午3:58:41
	 */
	@Override
	public boolean queryAirWaybillNoPickupBilllJoinTransferBillNo(
			String airwaybillNo, String agenctCode) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("airwaybillNo", airwaybillNo);
		dataMap.put("agenctCode", agenctCode);
		//根据正单号、外发代理编码查询航空正单是否存在
		int airWaybillNo = airWayBillDao.queryAirWaybillNoPickupBilllJoinTransferBillNo(dataMap);
		int pickupBilllNo = 0;
		int transferPickupBilllNo = 0;
		//如果未找到航空正单则查找合大票清单
		//此处判断为减少不必要的数据库访问
		if(airWaybillNo <= 0 ){
			pickupBilllNo = airWayBillDao.queryAirPickupBilllNo(dataMap);
		}
		//如果航空正单合大票清单都不存在则查询中转提货清单
		if(airWaybillNo <= 0 && pickupBilllNo <= 0){
			transferPickupBilllNo = airWayBillDao.queryAirTransferPickupBilllNo(dataMap);
		}
		return (airWaybillNo > 0 || pickupBilllNo > 0 || transferPickupBilllNo > 0);
	}
	
	/**
	 * 根据航空正单号和代理编码批量校验是否存在于空运配载单、
	 * 合大票清单和中转清单中是否存在记录：不匹配的数据返回标示FALSE和正单号及代理编码,
	 * 否则在任何一个表中存在即可返回标示TRUE和正单号及代理编码 
	 * @param List<BillRecAndPayImportDto>
	 * @return List<BillRecAndPayImportDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-30 上午9:48:24
	 */
	@SuppressWarnings("rawtypes")
	public List batchCheckAirWaybillNoisExist(List list){
		List<BillRecAndPayImportDto> dto = new ArrayList<BillRecAndPayImportDto>();
		for (Object obj : list) {
			BillRecAndPayImportDto billRecAndPayImportDto = (BillRecAndPayImportDto)obj;
			String isExist = airWaybillIsExist(billRecAndPayImportDto.getSourceBillNo(),
					billRecAndPayImportDto.getCustomerCode());
			billRecAndPayImportDto.setIsExist(isExist);
			dto.add(billRecAndPayImportDto);
		}
		return dto;
	}
	
	/**
	 * 根据正单号好、代理编码查询是否存在()
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-30 上午10:06:15
	 */
	private String airWaybillIsExist(String airWaybillNo,String agenctCode){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("airwaybillNo", airWaybillNo);
		dataMap.put("agenctCode", agenctCode);
		int countAirWayBillNo2 = 0;
		int countAirWayBillNo3 = 0;
		//根据正单号、外发代理编码查询航空正单是否存在
		int countAirWayBillNo1 = airWayBillDao.queryAirWaybillNoPickupBilllJoinTransferBillNo(dataMap);
		if(countAirWayBillNo1 <=0 ){
			countAirWayBillNo2 = airWayBillDao.queryAirPickupBilllNo(dataMap);
		}
		if(countAirWayBillNo1 <=0 && countAirWayBillNo2 <=0 ){
			countAirWayBillNo3 = airWayBillDao.queryAirTransferPickupBilllNo(dataMap);
		}
		if(countAirWayBillNo1 > 0 || countAirWayBillNo2 > 0 || countAirWayBillNo3 > 0){
			return FossConstants.YES;
		}else {
			return FossConstants.NO;
		}
	}
	
	/**
	 * 设置 航空正单dao.
	 * @param airWayBillDao the new 航空正单dao
	 */
	public void setAirWayBillDao(IAirWaybillDao airWayBillDao) {
		this.airWayBillDao = airWayBillDao;
	}

	/**
	 * 更新航空正单跟踪的相关信息: 实际出发时间、实际到达时间、跟踪状态、修改人、修改时间.
	 * @param airWaybillEntityList the air waybill entity list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-21 下午6:25:51
	 */
	@Override
	public void updateAirWayBillTrack(List<AirWaybillEntity> airWaybillEntityList) {
		airWayBillDao.updateAirWayBillTrack(airWaybillEntityList);
	}

	/**
	 * 根据航空正单id查询航空正单.
	 * @param id the id
	 * @return the air way bill dto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午8:20:40
	 */
	@Override
	public AirWayBillDto airWayBillDto(String id) {
		//航空正单实体
		AirWaybillEntity airWaybillEntity = airWayBillDao.queryAirWaybillEntityPrint(id);
		//如果商品代号为 ITEM_CODE将其设置为空
		if(airWaybillEntity.getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE)){
			airWaybillEntity.setItemCode("");
		}
		//航空正单明细list
		List<AirWaybillDetailEntity> airWaybillDetailEntityList =queryAirWaybillListByPrint(airWaybillEntity.getId());
		boolean f= false;
		for(AirWaybillDetailEntity airWaybillDetail : airWaybillDetailEntityList){
			if(airWaybillDetail.getWaybillNo().substring(0, 1).equals("B")){
				f=true;
				break;
			}
		}
		if(!f){
			airWaybillDetailEntityList = queryAirWaybillListForPrint(airWaybillEntity.getId());
		}
		AirWayBillDto airWayBillDto = new AirWayBillDto();
		//设置航空正单
		airWayBillDto.getAirWayBillVO().setOptAirWaybillEntity(airWaybillEntity);
		//设置航空正单明细list
		airWayBillDto.getAirWayBillVO().setAirWaybillDetailEntityList(airWaybillDetailEntityList);
		return airWayBillDto;
	}

	/** 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午6:43:12
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService#queryRate(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public AirlinesValueAddEntity queryRate(String flightCode,
			String loadOrgCode, String deptAirfieldCode, Date billDate) {
		return pointsSingleJointTicketService.queryRate(flightCode, loadOrgCode, deptAirfieldCode, billDate);
	}

	/**
	 * 调用航空正单基础费率.
	 * @param pointsSingleJointTicketService the new points single joint ticket service
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-30 下午4:29:20
	 */
	public void setPointsSingleJointTicketService(
			IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}

	/**
	 * 根据运单号查询该运单是否在航空正单明细中存在记录.
	 * @param waybillNo 运单号
	 * @param destOrgCode 操作部门
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午11:28:48
	 */
	@Override
	public boolean queryWaybillNoExists(String waybillNo, String destOrgCode) {
		return airWayBillDao.queryWaybillNoExists(waybillNo,destOrgCode);
	}
	
	

	/**
	 * 根据运单号查询该运单是否有航空正单交接单出库记录
	 * @param waybillNo 运单号
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-shixiaowei
	 * @date 2012-12-24 上午11:28:48
	 */
	@Override
	public boolean queryWaybillNoStockExists(String waybillNo){
		return airWayBillDao.queryWaybillNoStockExists(waybillNo);
	}
	
	/**
	 * 导出航空正单.
	 * @param airWayBillDto the air way bill dto
	 * @return InputStream
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:36:32
	 */
	@Override
	public InputStream queryAirWaybillForExport(AirWayBillDto airWayBillDto) {
		byte[] excelBytes = null;
		try {
			List<AirWaybillEntity> airWayBillList = queryAirWayBillListNoPage(airWayBillDto);
			for(int i=0;i<airWayBillList.size();i++){
				if(airWayBillList.get(i).getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE)){
					airWayBillList.get(i).setItemCode("");
				}
			}
			if(CollectionUtils.isEmpty(airWayBillList)){
				throw new AirTransPickupBillException(
						AirfreightConstants.AIRFREIGHT_EXCEPTION_NOTFOUNDAIRWAYBILL);
			}
			ExcelExport excelExportUtil = new ExcelExport();
			HSSFWorkbook workbook = excelExportUtil.createWorkBook();
	  		int dataSize = airWayBillList.size();
	  		int sheetSize = ConstantsNumberSonar.SONAR_NUMBER_5000;
	  		/**
	  		 * @desc 使用BigDecimal代替之前的除法运算
	  		 * @author wqh
	  		 * @date 2013-08-05
	  		 * */
	  	    BigDecimal bdataSize=new BigDecimal(dataSize);
	  	    BigDecimal bsheetSize=new BigDecimal(sheetSize);
	  	    BigDecimal result=bdataSize.divide(bsheetSize).setScale(0, RoundingMode.UP);
	  	    
	  		//double sheetNo = Math.ceil(dataSize / sheetSize);
	  	    double sheetNo=result.doubleValue();
	  		
	  	    //定义标题
	  		String title = "航空正单列表" ;
	  		
	  		// head1
	  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  		//组装类型
	  		DataDictionaryEntity airAssembleTypeData = DictUtil.getDataByTermsCode(DictionaryConstants.AIR_ASSEMBLE_TYPE);
	  		//运价类型
	  		DataDictionaryEntity rateClassData = DictUtil.getDataByTermsCode("RATE_CLASS");
	  		//付款类型
	  		DataDictionaryEntity payTypeData = DictUtil.getDataByTermsCode("PAYMENT_TYPE");
	  		Map<String,String> airAssembleTypeMap  = new HashMap<String,String>();
	  		Map<String,String> rateClassMap  = new HashMap<String,String>();
	  		Map<String,String> payTypeMap  = new HashMap<String,String>();
	  		for (DataDictionaryValueEntity value : airAssembleTypeData.getDataDictionaryValueEntityList()) {
	  			airAssembleTypeMap.put(value.getValueCode(), value.getValueName());
	  		}
	  		for (DataDictionaryValueEntity value : rateClassData.getDataDictionaryValueEntityList()) {
	  			rateClassMap.put(value.getValueCode(), value.getValueName());
	  		}
	  		for (DataDictionaryValueEntity value : payTypeData.getDataDictionaryValueEntityList()) {
	  			payTypeMap.put(value.getValueCode(), value.getValueName());
	  		}
	  			
	  		String[] heads = {
	  				"航空公司",airWayBillDto.getAirlineTwoletter(),  
	  				"目的站",airWayBillDto.getArrvRegionName(),
	  				"配载类型",airAssembleTypeMap.get(airWayBillDto.getAirAssembleType())==null?"":String.valueOf(airAssembleTypeMap.get(airWayBillDto.getAirAssembleType())), 
	  				"起始时间",format.format(airWayBillDto.getBeginInTime()),
	  				"结束时间",format.format(airWayBillDto.getEndInTime())
	  		};
	  		// head 2
			String[] columnNames = { 
					"航空公司",
					"正单号", 
					"始发站", 
					"目的站", 
					"配载类型",
					"和票号", 
					"到达网点",
					
					"收货人",
					"电话", 
					"结算事项", 
					"填开代理", 
					"地址",
					"储运事项",
				
					"航班号",
					"航班日期",
					"起飞时间", 
					"到达时间", 
					"运价种类", 
					"付款方式",
					"毛重", 
					"计费重量",
					"运价",
					"承运人外发代理",
					
					"声明价值", 
					"商品代号", 
					"件数",
					"体积", 
					"航空运费",
					"附加费",
					"地面运费", 
					"燃油附加税", 
					"运输保险", 
					"保险费",
					"总金额", 
					"费用说明",
					"制单费",
					"货物名称", 
					"包装说明", 
					
					"托运人", 
					"地址",
					"电话", 
					"提货方式",
					"制单部门",
					"制单人", 
					"制单时间"
			};
			
			HSSFCellStyle style = workbook.createCellStyle(); // 样式对象      
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平     
	        
	        for(int index = 0; index <= sheetNo; index++) {
	  			int rowIndex = 0;
	  			HSSFSheet sheet = excelExportUtil.createSheet(workbook, "航空正单" + index);
	            sheet.setDefaultColumnWidth(ConstantsNumberSonar.SONAR_NUMBER_15);
	            
	            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
				sheet.addMergedRegion(cellRangeAddress);
				rowIndex ++;
	            
				// title
	     		HSSFRow titleRow = sheet.createRow(rowIndex);
	     		HSSFCell titleCell = createHSSFCell(titleRow, 0, title);
	     		titleCell.setCellStyle(style);
	     		cellRangeAddress = new CellRangeAddress(1, 1, 0, ConstantsNumberSonar.SONAR_NUMBER_8);
				sheet.addMergedRegion(cellRangeAddress);
				rowIndex ++;
			
				// head 1
				HSSFRow headRow1 = sheet.createRow(rowIndex);
	     		for(int i = 0, len = heads.length; i<len; i++) {
	     			createHSSFCell(headRow1, i, heads[i]);
	     		}
	     		rowIndex++;
	     		
				// head 2
				HSSFRow headRow2 = sheet.createRow(rowIndex);
	     		for(int i = 0, len = columnNames.length; i<len; i++) {
	     			createHSSFCell(headRow2, i, columnNames[i]);
	     		}
	     		rowIndex++;
	     		
	     		int startNo = index * (sheetSize - 1);
	            int endNo = Math.min(startNo + sheetSize - 1, dataSize);
	            for (int i = startNo; i < endNo; i++) {
	            	createAirWayBillListCallDataRow(sheet, rowIndex, airWayBillList.get(i)
	            			,airAssembleTypeMap,rateClassMap,payTypeMap);
	     			rowIndex++;
	            }
	            
	            // foot
	            HSSFRow footRow = sheet.createRow(rowIndex);
	            CellRangeAddress  dpCellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, 0, 1);
				sheet.addMergedRegion(dpCellRangeAddress);
				
	            createHSSFCell(footRow, 2, "制表人:");
	            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_3, FossUserContext.getCurrentInfo().getEmpName());
	            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_4, "日期:");
	            createHSSFCell(footRow, ConstantsNumberSonar.SONAR_NUMBER_5, format.format(new Date()));
	            
	            excelBytes = getInputByte(workbook);
	        }   
			
		} catch (BusinessException e) {
			throw new AirChangeInventoryException(e.getErrorCode());
		}
		return new ByteArrayInputStream(excelBytes);
	}
	
	/**
	 * 创建cell.
	 * @param row the row
	 * @param columnIndex the column index
	 * @param value the value
	 * @return the hSSF cell
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 下午2:36:20
	 */
	private HSSFCell createHSSFCell(HSSFRow row, int columnIndex, String value) {
		//创建单元格
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(value);
		//设置单元格类型string
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}
	
	/**
	 * 获取io字节 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-29 下午2:56:59
	 */
	private byte[] getInputByte(HSSFWorkbook woorkBook){
  		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			woorkBook.write(out);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			IOUtils.close(out);
		}
		return out.toByteArray();
	}
	
	/**
	 * 创建航空正单列表行数据
	 * @param sheet 工作薄
	 * @param rowIndex 行索引
	 * @param result 航空正单
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:52:59
	 */
	private HSSFRow createAirWayBillListCallDataRow(HSSFSheet sheet, int rowIndex, AirWaybillEntity result,
			Map<String,String> airAssembleTypeMap,Map<String,String> rateClassMap,Map<String,String> payTypeMap) {
  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		HSSFRow row = sheet.createRow(rowIndex);
		int columnIndex = 0;
		//第1列    	航空公司
		createHSSFCell(row, columnIndex, result.getAirLineTwoletter());
		columnIndex ++;
		//第2列	正单号
		createHSSFCell(row, columnIndex, result.getAirWaybillNo());
		columnIndex ++;
		//第3列	始发站
		createHSSFCell(row, columnIndex, result.getDeptRegionName());
		columnIndex ++;
		//第4列	目的站
		createHSSFCell(row, columnIndex, result.getArrvRegionName());
		columnIndex ++;
		//第5列	配载类型
		createHSSFCell(row, columnIndex, airAssembleTypeMap.get(result.getAirAssembleType()).toString());
		columnIndex ++;
		//第6列	和票号
		createHSSFCell(row, columnIndex, result.getJointTicketNo());
		columnIndex ++;
		//第7列	到达网点
		createHSSFCell(row, columnIndex, result.getDedtOrgName());
		columnIndex ++;

		//第8列    	收货人
		createHSSFCell(row, columnIndex, result.getReceiverName());
		columnIndex ++;
		//第9列	电话
		createHSSFCell(row, columnIndex, result.getReceiverContactPhone());
		columnIndex ++;
		//第10列	结算事项
		createHSSFCell(row, columnIndex, result.getAccountItem());
		columnIndex ++;
		//第11列	填开代理
		createHSSFCell(row, columnIndex, result.getBillingAgency());
		columnIndex ++;
		//第12列	地址
		createHSSFCell(row, columnIndex, result.getReceiverAddress());
		columnIndex ++;
		//第13列	储运事项
		createHSSFCell(row, columnIndex, result.getStorageItem());
		columnIndex ++;

		//第14列	航班号
		createHSSFCell(row, columnIndex, result.getFlightNo());
		columnIndex ++;
		//第15列    	航班日期
		createHSSFCell(row, columnIndex, format2.format(result.getFlightDate()));
		columnIndex ++;
		//第16列	起飞时间
		createHSSFCell(row, columnIndex, format.format(result.getTakeOffTime()));
		columnIndex ++;
		//第17列	到达时间
		createHSSFCell(row, columnIndex, format.format(result.getArriveTime()));
		columnIndex ++;
		//第18列	运价种类
		createHSSFCell(row, columnIndex, rateClassMap.get(result.getRateClass()).toString());
		columnIndex ++;
		//第19列	付款方式
		createHSSFCell(row, columnIndex, payTypeMap.get(result.getPaymentType()).toString());
		columnIndex ++;
		//第20列	毛重
		createHSSFCell(row, columnIndex, result.getGrossWeight().toEngineeringString());
		columnIndex ++;
		//第21列	计费重量
		createHSSFCell(row, columnIndex, result.getBillingWeight().toEngineeringString());
		columnIndex ++;
		//第22列	运价
		createHSSFCell(row, columnIndex, result.getFee().toEngineeringString());
		columnIndex ++;
		//第23列	承运人外发代理
		createHSSFCell(row, columnIndex, result.getAgencyName());
		columnIndex ++;

		//第24列	声明价值
		createHSSFCell(row, columnIndex, result.getDeclareValue());
		columnIndex ++;
		//第25列    	商品代号
		createHSSFCell(row, columnIndex, result.getItemCode());
		columnIndex ++;
		//第26列	件数
		createHSSFCell(row, columnIndex, result.getGoodsQty().toString());
		columnIndex ++;
		//第27列	体积
		createHSSFCell(row, columnIndex, result.getVolume().toEngineeringString());
		columnIndex ++;
		//第28列	航空运费
		createHSSFCell(row, columnIndex, result.getAirFee()==null?BigDecimal.ZERO.toEngineeringString()
				:result.getAirFee().toEngineeringString());
		columnIndex ++;
		//第29列	附加费
		createHSSFCell(row, columnIndex, result.getExtraFee()==null?BigDecimal.ZERO.toEngineeringString()
				:result.getExtraFee().toEngineeringString());
		columnIndex ++;
		//第30列	地面运费
		createHSSFCell(row, columnIndex, result.getGroundFee()==null?BigDecimal.ZERO.toEngineeringString()
				:result.getGroundFee().toEngineeringString());
		columnIndex ++;
		//第31列	燃油附加税
		createHSSFCell(row, columnIndex, result.getFuelSurcharge()==null?BigDecimal.ZERO.toEngineeringString()
				: result.getFuelSurcharge().toEngineeringString());
		columnIndex ++;
		//第32列	运输保险
		createHSSFCell(row, columnIndex, result.getTransportInsurance()==null?BigDecimal.ZERO.toEngineeringString():
			result.getTransportInsurance().toEngineeringString());
		columnIndex ++;
		//第33列	保险费
		createHSSFCell(row, columnIndex, result.getInseranceFee()==null?BigDecimal.ZERO.toEngineeringString()
				:result.getInseranceFee().toEngineeringString());
		columnIndex ++;
		//第34列	总金额
		createHSSFCell(row, columnIndex, result.getFeeTotal().toEngineeringString());
		columnIndex ++;
		//第35列	费用说明
		createHSSFCell(row, columnIndex, result.getFeePlain());
		columnIndex ++;
		//第36列	制单费
		//BUG-55512 查询航空正单信息无法导出 
		//制单费用为空判断
		createHSSFCell(row, columnIndex, result.getBillingFee()==null?BigDecimal.ZERO.toEngineeringString()
				:result.getBillingFee().toEngineeringString());
		columnIndex ++;
		//第37列	货物名称
		createHSSFCell(row, columnIndex, result.getGoodsName());
		columnIndex ++;
		//第38列	包装说明
		createHSSFCell(row, columnIndex, result.getPackageStruction());
		columnIndex ++;

		//第39列	托运人
		createHSSFCell(row, columnIndex, result.getShipperName());
		columnIndex ++;
		//第40列	地址
		createHSSFCell(row, columnIndex, result.getShipperAddress());
		columnIndex ++;
		//第41列	电话
		createHSSFCell(row, columnIndex, result.getShipperContactPhone());
		columnIndex ++;
		//第42列	提货方式
		createHSSFCell(row, columnIndex, result.getPickupType());
		columnIndex ++;
		//第43列	制单部门
		createHSSFCell(row, columnIndex, result.getCreateOrgName());
		columnIndex ++;
		//第44列	制单人
		createHSSFCell(row, columnIndex, result.getCreateUserName());
		columnIndex ++;
		//第45列	制单时间
		createHSSFCell(row, columnIndex, format.format(result.getCreateTime()));
		columnIndex ++;
		return row;
	}
	
	/**
	 * 判断运单在0或1中是否存在
	 * @param waybillNo 运单号，type 0表示正单，1表示合票
	 * @return boolean
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	@Override
	public boolean judgeWaybillInAir(String waybillNo, String type) {
		if(StringUtils.equals("0", type)){
			int num = airWayBillDao.queryWaybillDetailsByWaybillNo(waybillNo);
			return (num>0)?true:false;
		}else if(StringUtils.equals("1", type)){
			int num = airWayBillDao.queryPickbillDetailsByWaybillNo(waybillNo);
			return (num>0)?true:false;
		}else{
			throw new AirChangeInventoryException("未知类型异常");
		}
	}
	/**
	 * 获取运单的正单制作部门
	 * @param waybillNo 运单号
	 * @return String
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	@Override
	public String queryAirWaybillDept(String waybillNo) {
		
		return airWayBillDao.queryAirWaybillDept(waybillNo);
	}

	/**
	 * 根据运单号查询所在航空正单的配载类型
	 * @param AirWaybillDetailDto 
	 * @return the list
	 * @author 200968  zwd 
	 * @date 2015-04-24 上午15:36:32
	 */
	@Override
	public List<AirWaybillEntity> queryAirWayBillListByWayBill(
			AirWaybillDetailDto airWaybillDetailDto) {
		return airWayBillDao.queryAirWayBillListByWayBill(airWaybillDetailDto);
	}
	
	
	
	
}