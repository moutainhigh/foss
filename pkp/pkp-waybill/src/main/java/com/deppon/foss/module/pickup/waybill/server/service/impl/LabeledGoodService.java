/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/LabeledGoodService.java
 * 
 * FILE NAME        	: LabeledGoodService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 货签服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:46:21
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午3:46:21
 * @since
 * @version
 */
public class LabeledGoodService implements ILabeledGoodService {

	/**
	 * 定义日志静态类 
	 * 通过日志工厂类获得该类的日志对象
	 * 使用该日志类的静态方法记录日志
	 */
	protected final static Logger LOGGER = LoggerFactory.getLogger(WaybillManagerService.class.getName());
	
	/**
	 * 运单DAO
	 * 提供与运单相关的持久化操作接口
	 */
	private IWaybillDao waybillDao;

	/**
	 * 货签DAO
	 * 提供与货签相的持久化操作接口
	 */
	private ILabeledGoodDao labeledGoodDao;
	
	/**
	 * 运单待处理信息的处理
	 * 提供与待处理信息相关的服务接口
	 */
	private IWaybillPendingService waybillPendingService;
	
	/**
	 * 交接的服务
	 * 提供与中转卸车交接相关的服务接口
	 */
	private IStayHandoverService stayHandoverService;
	
	
	/**
	 * 入库接口
	 */
	private IStockService stockService;
	
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	@Override
	public ResultDto insertSerialNo(String waybillNo, List<LabeledGoodDto> labeledGoodList) {
		return insertSerialNo(waybillNo,waybillNo,labeledGoodList);
	}

	/**
	 * 
	 * <p>
	 * 插入货签信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:46:50
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService#insertSerialNo(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public ResultDto insertSerialNo(String oldWaybillNo,String waybillNo, List<LabeledGoodDto> labeledGoodList) {
		String code = "1";
		String msg = "";
		//服务器当前时间
		Date curDate = new Date();
		//标签信息
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(oldWaybillNo);
		//定义标签对象
		List<LabeledGoodEntity> list = new ArrayList<LabeledGoodEntity>();
		//非空判断，若有标签，则拷贝
		if (waybillEntity != null) {
			LabeledGoodEntity labeledGood;
			for (LabeledGoodDto labeledGoodDto : labeledGoodList) {
				labeledGood = new LabeledGoodEntity();
				labeledGood.setWaybillNo(waybillNo);
				labeledGood.setActive(FossConstants.ACTIVE);
				labeledGood.setBillTime(waybillEntity.getBillTime());
				labeledGood.setCreateTime(curDate);
				labeledGood.setModifyTime(curDate);
				labeledGood.setSerialNo(labeledGoodDto.getSerialNo());
				labeledGood.setInitalVale(labeledGoodDto.getInitalVale());
				labeledGood.setIsNeedWooden(labeledGoodDto.getIsNeedWooden());
				labeledGood.setNumberChangItems(labeledGoodDto.getNumberChangItems());
				labeledGood.setOldSerialNo(labeledGoodDto.getOldSerialNo());
				//zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
				labeledGood.setPackageType(labeledGoodDto.getPackageType());
				list.add(labeledGood);
			}
			
		//若没有则新增
		} else {
			LabeledGoodEntity labeledGood;
			for (LabeledGoodDto labeledGoodDto : labeledGoodList) {
				labeledGood = new LabeledGoodEntity();
				labeledGood.setWaybillNo(waybillNo);
				labeledGood.setActive(FossConstants.ACTIVE);
				labeledGood.setBillTime(curDate);
				labeledGood.setCreateTime(curDate);
				labeledGood.setModifyTime(curDate);
				labeledGood.setSerialNo(labeledGoodDto.getSerialNo());
				labeledGood.setInitalVale(labeledGoodDto.getInitalVale());
				labeledGood.setIsNeedWooden(labeledGoodDto.getIsNeedWooden());
				labeledGood.setNumberChangItems(labeledGoodDto.getNumberChangItems());
				labeledGood.setOldSerialNo(labeledGoodDto.getOldSerialNo());
				//zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
				labeledGood.setPackageType(labeledGoodDto.getPackageType());
				list.add(labeledGood);
			}
		}
		
		//捕捉异常信息
		try{
			//返回更新条数
			int insertCount = labeledGoodDao.insertBatch(list);
			if(insertCount<0){
				code = "0";
				msg = "插入货签信息失败！";
			}
		}catch (Exception e) {
			//添加异常日志
			Log.error("插入货签信息失败",e);
			code = "0";
			msg = "插入货签信息失败！\n原因："+e.getMessage();
			//抛出异常信息  TODO 为了测试开打，若部署时需要注释，不影响正常提交业务
			//throw new WaybillImportException("插入货签信息失败！\n原因："+e.getMessage());
		}
		
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		
		return res;
	}
	
	/**
	 * 
	 * <p>
	 * 删除货签信息
	 * 不是真正的删除 是设置 active 为N
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:46:50
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService#deleteSerialNo(java.lang.String,
	 *      java.util.List)
	 */
	public int deleteSerialNo(
			String waybillNo, List<LabeledGoodDto> labeledGoodList) {
		List<LabeledGoodEntity> list = new ArrayList<LabeledGoodEntity>();
		for (LabeledGoodDto labeledGoodDto : labeledGoodList) {
			LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
			labeledGoodEntity.setWaybillNo(waybillNo);
			labeledGoodEntity.setSerialNo(labeledGoodDto.getSerialNo());
			list.add(labeledGoodEntity);
		}
		return labeledGoodDao.deleteGoodEntityBatch(list);
	}
	
	/**
	 * 根据运单号查询PDA货签是否已生成，若PDA未生成则调用insertSerialNo方法，若生成部分则在货签表里生成余下未生成的货签
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 上午10:13:24
	 */
	@Override
	public void addLabeledGoods(WaybillDto waybill,WaybillSystemDto systemDto){
		//运单基本信息
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		//运单号
		String waybillNo = StringUtil.defaultIfNull(waybillEntity.getWaybillNo());
		//老运单号
		String oldWaybillNo = waybill.getOldWaybillNo();
		
		//运单处理状态：运单暂存、PDA待处理、离线运单待提交、PAD已补录、暂存已开单、弃货导入开单
		String waybillStatus = StringUtil.defaultIfNull(waybillEntity.getPendingType());
		
		//PDA补录前的总件数
		int tagPieces = 0;
		
		//PDA补录后的运单总件数
		int totalPieces = waybillEntity.getGoodsQtyTotal();
		
		/**
		 * 一、当为PDA补录的运单信息时
		 */
		if(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillStatus)){
			//已打标签件数
			List<LabeledGoodEntity> labeledGoodsList = labeledGoodDao.queryAllSerialByWaybillNo(oldWaybillNo);

			// 待补录运单基本信息
			WaybillPendingEntity waybillPending = waybillPendingService.queryPendingByNo(oldWaybillNo);

			// PDA补录前的总件数
			tagPieces = labeledGoodsList.size();
			
			//存储流水号的集合
			List<String> numList = new ArrayList<String>();
			for (LabeledGoodEntity labeledGoodEntity : labeledGoodsList) {
				numList.add(labeledGoodEntity.getSerialNo());
			}
			
			//获取最大最小值 
			String max = "0";
			String min = "0";
			if(tagPieces >0 ){
				max = Collections.max(numList);
				min = Collections.min(numList);
			}
			
			//转换为数字
			int maxInt = Integer.parseInt(max);
			int minInt = Integer.parseInt(min);
			//更新已打标签的包装信息
			updateSalverLabeledGood(labeledGoodsList,waybill);
			/**
			 * 1）若PDA补录后，增加了件数
			 * 1、新增PDA货件(T_SRV_LABELED_GOOD)。
			 * 举例说明新增规则：若PDA开单的件数为5，补录时修改件数为8。
			 * 查询当前的PDA货件的最大流水号，应当为0005，需要添加货件0006-0008。
			 * 2、将新增的PDA货件插入中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中。
			 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),累加上增加的件数。
			 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，累加上增加的件数。
			 * 
			 */
			if(totalPieces > tagPieces){
				//需要新增的流水号个数
				int moreNum = totalPieces - tagPieces;
				//定义PDA标签对象集合
				List<LabeledGoodEntity> pdaList = new ArrayList<LabeledGoodEntity>();
				//定义流水号集合
				List<String> serials = new ArrayList<String>();
				//循环操作PDA标签对象
				for (int i = 1; i <= moreNum; i++) {
					// 生成流水号字符串
					String s = StringHandlerUtil.lpad(String.valueOf(++maxInt), NumberConstants.NUMBER_4, "0");
					//将组装的PDA货签信息加入到集合当中
					pdaList.add(getLabeledGoods(waybill,systemDto,s,FossConstants.ACTIVE));
					//将流水号加入到集合中
					serials.add(s);
				}
				
				/**
				 * 捕捉异常信息
				 * 
				 * 之所以不抛出异常，是因为该问题对正常业务影响不大，
				 * 不能因为这个出现异常造成业务不能正常处理
				 */
				try {
					//1、新增PDA货件(T_SRV_LABELED_GOOD)。举例说明新增规则：若PDA开单的件数为5，补录时修改件数为8。查询当前的PDA货件的最大流水号，应当为0005，需要添加货件0006-0008。
					moreLabeledGood(serials,oldWaybillNo, waybillNo,waybill);
					// 若该单已做交接，则更新中转卸车交接表中的相关数据；否则不做业务处理
					if(FossConstants.ACTIVE.equals(StringUtil.defaultIfNull(waybillPending.getHandoverStatus()))){
						/**
						 * 2、将新增的PDA货件插入中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中
						 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),累加上增加的件数。
						 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，累加上增加的件数。
						 */
						//定义待处理的货签数据
						
						
						StayHandoverDetailEntity entity =stayHandoverDetailService.queryByWaybillNo(oldWaybillNo);
						
						if(entity!=null && entity.getGoodsQty().intValue()!= totalPieces){
						
							
						
							PdaDto pdaDto = new PdaDto();
							//设置运单号
							pdaDto.setWaybillNo(oldWaybillNo);
							//设置流水号
							if(totalPieces- entity.getGoodsQty().intValue()!=moreNum){
								
								
								int k = Math.abs(totalPieces - entity.getGoodsQty().intValue() );
								int w=0;
								List<String> serials2 = new ArrayList<String>();
								for(int t = serials.size()-1;t>=0;t--){
									String s = serials.get(t);
									serials2.add(s);
									w ++;
									if(w>=k){
										break;
									}
								}
								pdaDto.setSerialNos(serials2);
								
							}else{
								pdaDto.setSerialNos(serials);
							}
							
							
							//设置需要更新的件数
							pdaDto.setGoodsQty(totalPieces- entity.getGoodsQty().intValue());
							//老运单号
							pdaDto.setWaybillNo(oldWaybillNo);
							//新运单号
							pdaDto.setNewWaybillNo(waybillNo);
							//更改交接信息
							stayHandoverService.makeupPdaAddgoodsQty(pdaDto);
						}
					}
				} catch (Exception e) {
					LOGGER.error("批量PDA货签信息出现异常",e);
				}
				
				
			
			/**
			 * 2）若PDA补录后，减少了件数：
			 * 1、 删除PDA货件(T_SRV_LABELED_GOOD)。举例说明新增规则：若PDA开单的件数为8，补录时修改件数为5。查询当前的PDA货件的最小流水号，应当为0001，需要减少货件0001-0003。
			 * 2、将删除的PDA货件从中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中删除。
			 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),减去件数。
			 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，减去件数。
			 */
			}else if(totalPieces < tagPieces){
				//需要减掉的流水号个数
				int lessNum = tagPieces - totalPieces;
				//定义PDA标签对象集合
				List<LabeledGoodEntity> pdaList = new ArrayList<LabeledGoodEntity>();
				//定义流水号集合
				List<String> serials = new ArrayList<String>();
				//循环操作PDA标签对象
				for (int i = minInt; i <= lessNum; i++) {
					// 新增的流水号数字
					// 生成流水号字符串
					String s = StringHandlerUtil.lpad(String.valueOf(minInt++), NumberConstants.NUMBER_4, "0");
					//将组装的PDA货签信息加入到集合当中
					pdaList.add(getLabeledGoods(waybill,systemDto,s,FossConstants.INACTIVE));
					//将流水号加入到集合中
					serials.add(s);
				}
				/**
				 * 捕捉异常信息
				 * 
				 * 之所以不抛出异常，是因为该问题对正常业务影响不大，
				 * 不能因为这个出现异常造成业务不能正常处理
				 */
				try {
					//1、删除PDA货件(T_SRV_LABELED_GOOD)。举例说明新增规则：若PDA开单的件数为8，补录时修改件数为5。查询当前的PDA货件的最小流水号，应当为0001，需要减少货件0001-0003。
					lessLabeledGood(serials, oldWaybillNo);
					try{
					deleteStock(oldWaybillNo,serials);
					}catch(Exception e)
					{
						LOGGER.error("删除库存后异常，"+"单号oldWaybillNo：",e);
					}
					// 若该单已做交接，则更新中转卸车交接表中的相关数据；否则不做业务处理
					if(FossConstants.ACTIVE.equals(StringUtil.defaultIfNull(waybillPending.getHandoverStatus()))){
						/**
						 * 2、将删除的PDA货件从中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中删除。
						 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),减去件数。
						 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，减去件数。
						 */
						//定义待处理的货签数据
						StayHandoverDetailEntity entity =stayHandoverDetailService.queryByWaybillNo(oldWaybillNo);
						
						if(entity!=null && entity.getGoodsQty().intValue()!= totalPieces){
						
							PdaDto pdaDto = new PdaDto();
							//设置运单号
							pdaDto.setWaybillNo(waybillNo);
							//设置流水号
							pdaDto.setSerialNos(serials);
							//设置需要更新的件数
							pdaDto.setGoodsQty(lessNum);
							//老运单号
							pdaDto.setWaybillNo(oldWaybillNo);
							//新运单号
							pdaDto.setNewWaybillNo(waybillNo);
							//更新交接信息
							stayHandoverService.makeupPdaReduceGoodsQty(pdaDto);
						
						}
					}
				} catch (Exception e) {
					//添加异常日志
					LOGGER.error("批量PDA货签信息出现异常",e);
				}
			}else{
				//若是相等，则不做业务处理
			}
			
			//判断是否已变更了运单号
			if(!StringUtil.defaultIfNull(oldWaybillNo).equals(waybillNo)){
				//根据原运单号更新货签运单号
				labeledGoodDao.modifyWaybillNo(oldWaybillNo, waybillNo);
			}
		}else{
			/**
			 * 二、当不为PDA补录时
			 * 
			 * 在货签表中插入数据
			 */
			/**
			 * 批量开单不在后台生成货签信息
			 * dp-foss-dongjialing
			 * 225131
			 */
			if(waybill.getActualFreightEntity() == null || !WaybillConstants.YES.equals(waybill.getActualFreightEntity().getIsBatchCreateWaybill()))
			{
				addLabeledGood(totalPieces,waybillNo,waybillNo,waybill);	
			}
			
		}
		
	}
	
	
	
	
	
	private void deleteStock(String waybillNO ,List<String> serialNOs){

		/* @param inOutStockEntity.waybillNO 运单号
		 * @param inOutStockEntity.serialNO  流水号
		 * @param inOutStockEntity.orgCode 部门编号(和宋杰沟通过，可为空)
		 * @param inOutStockEntity.operatorCode 操作人工号           
		 * @param inOutStockEntity.operatorName 操作人姓名
		 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
		 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
		*/
		UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
		String operatorCode=user.getEmployee().getEmpCode();
		String operatorName=user.getEmployee().getEmpName();
		String inOutStockType=StockConstants.WAYBILL_RFC_IN_STOCK_TYPE;
		if(serialNOs!=null){
		for(String serialNO:serialNOs){
			
		InOutStockEntity inOutStockEntity=new InOutStockEntity();
		inOutStockEntity.setOperatorCode(operatorCode);
		inOutStockEntity.setOperatorName(operatorName);
		inOutStockEntity.setInOutStockType(inOutStockType);
		inOutStockEntity.setWaybillNO(waybillNO);
		inOutStockEntity.setSerialNO(serialNO);
		stockService.outStockDelivery(inOutStockEntity);
		}
		}
	}
	
	/**
	 * 完全新增货签信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-23 下午5:50:16
	 * 版本 	   用户		时间			内容
	 * 0002		zxy		20131108    修改方法，增加WaybillDto waybill参数，打木托需要用到木托list
	 */
	public void addLabeledGood(int pieces,String oldWaybillNo,String waybillNo,WaybillDto waybill){
		//定义货签对象集合
		List<LabeledGoodDto> list = new ArrayList<LabeledGoodDto>();
		//定义货签对象 
		LabeledGoodDto labeledGoodDto = null;
		
		List<LabeledGoodEntity> labeledGoodEntities = waybill.getLabeledGoodEntities();//zxy 20131118 ISSUE-4391 获取木托list
		//生成流水号集合
		for (int i = 1; i <=pieces; i++) {
			//实例化货签对象
			labeledGoodDto = new LabeledGoodDto();
			//生成单个流水号
			String s = StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0");
			//设置流水号
			labeledGoodDto.setSerialNo(s);
			//设置是否开单初始值
			labeledGoodDto.setInitalVale(FossConstants.YES);
			//zxy 20131118 ISSUE-4391 start 新增：勾选的木托，设置包装类型=SALVER
			if(labeledGoodEntities != null && labeledGoodEntities.size() > 0){
				for(int j = 0; j < labeledGoodEntities.size(); j++){
					LabeledGoodEntity labeledGoodEntity = labeledGoodEntities.get(j);
					if(s.equals(labeledGoodEntity.getSerialNo()) && WaybillConstants.PACKAGE_TYPE_SALVER.equals(labeledGoodEntity.getPackageType())){
						labeledGoodDto.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
						break;
					}
				}
			}
			//zxy 20131118 ISSUE-4391 end 新增：勾选的木托，设置包装类型=SALVER
			//将货签对象加入集合当中
			list.add(labeledGoodDto);
		}
		
		//批量插入货签对象集合
		this.insertSerialNo(oldWaybillNo,waybillNo, list);
	}
	
	/**
	 * 根据流水号新增货签
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-23 下午5:54:06
	 * 版本 	   用户		时间			内容
	 * 0002		zxy		20131108    修改方法，增加WaybillDto waybill参数，打木托需要用到木托list
	 */
	public void moreLabeledGood(List<String> serials,String oldWaybillNo,String waybillNo,WaybillDto waybill){
		//集合非空判断
		if(CollectionUtils.isEmpty(serials)){
			return ;
		}
		List<LabeledGoodEntity> labeledGoodEntities = waybill.getLabeledGoodEntities();
		//定义货签对象集合
		List<LabeledGoodDto> list = new ArrayList<LabeledGoodDto>();
		//定义货签对象 
		LabeledGoodDto labeledGoodDto = null;
		//生成流水号集合
		for (int i = 0; i <serials.size(); i++) {
			//实例化货签对象
			labeledGoodDto = new LabeledGoodDto();
			//设置流水号
			labeledGoodDto.setSerialNo(serials.get(i));
			//设置是否开单初始值
			labeledGoodDto.setInitalVale(FossConstants.YES);
			//zxy 20131118 ISSUE-4391 start 新增：勾选的木托，设置包装类型=SALVER
			if(labeledGoodEntities != null && labeledGoodEntities.size() > 0){
				for(int j = 0; j < labeledGoodEntities.size(); j++){
					LabeledGoodEntity labeledGoodEntity = labeledGoodEntities.get(j);
					if(labeledGoodDto.getSerialNo().equals(labeledGoodEntity.getSerialNo()) && WaybillConstants.PACKAGE_TYPE_SALVER.equals(labeledGoodEntity.getPackageType())){
						labeledGoodDto.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
						break;
					}
				}
			}
			//zxy 20131118 ISSUE-4391 end 新增：勾选的木托，设置包装类型=SALVER
			//将货签对象加入集合当中
			list.add(labeledGoodDto);
		}
		
		//批量插入货签对象集合
		this.insertSerialNo(oldWaybillNo, waybillNo,list);
	}
	
	/**
	 * 更新PDA已打标签的包装类型
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18 			
	 * @param labeledGoods
	 * @param waybill
	 */
	public void updateSalverLabeledGood(List<LabeledGoodEntity> labeledGoods,WaybillDto waybill){
		List<LabeledGoodEntity> labeledGoodEntities = waybill.getLabeledGoodEntities();
		//打木托货签不为空
		if(labeledGoodEntities != null && labeledGoodEntities.size() > 0){
			//PDA已打标签
			if(labeledGoods != null && labeledGoods.size() > 0){
				for(int i = 0; i < labeledGoodEntities.size(); i++){
					for(int j = 0; j < labeledGoods.size(); j++){
						//如果打木托货签和已打标签是同一个流水号的，则表示同一件货
						if(labeledGoodEntities.get(i).getSerialNo().equals(labeledGoods.get(j).getSerialNo())){
							//设置货签包装类型=SALVER，并直接更新到库中
							if(WaybillConstants.PACKAGE_TYPE_SALVER.equals(labeledGoodEntities.get(i).getPackageType())){
								labeledGoods.get(j).setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
								labeledGoodDao.updateByPrimaryKeySelective(labeledGoods.get(j));
							}
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据运单号与流水号置货签记录为无效
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-23 下午6:07:03
	 */
	public void lessLabeledGood(List<String> serials,String waybillNo){
		//集合非空判断
		if(CollectionUtils.isEmpty(serials)){
			return ;
		}
		
		//定义货签对象集合
		List<LabeledGoodEntity> list = new ArrayList<LabeledGoodEntity>();
		//定义货签对象 
		LabeledGoodEntity entity = null;
		//生成流水号集合
		for (int i = 0; i <serials.size(); i++) {
			//实例化货签对象
			entity = new LabeledGoodEntity();
			//运单号
			entity.setWaybillNo(waybillNo);
			//流水号
			entity.setSerialNo(serials.get(i));
			//加入集合
			list.add(entity);
		}
		labeledGoodDao.deleteGoodEntityBatch(list);
	}
	
	

	
	
	/**
	 * 封装PDA标签对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-17 下午4:57:48
	 */
	private LabeledGoodEntity getLabeledGoods(WaybillDto waybillDto,WaybillSystemDto systemDto,String serials,String active){
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 定义PDA标签对象，用于接收新的PDA标签数据
		LabeledGoodEntity labeledGood = new LabeledGoodEntity();
		//运单号
		String waybillNo = StringUtil.defaultIfNull(waybill.getWaybillNo());
		//定义对象
		labeledGood = new LabeledGoodEntity();
		//运单号
		labeledGood.setWaybillNo(waybillNo);
		// 状态
		labeledGood.setActive(active);
		// 收货时间
		labeledGood.setBillTime(waybill.getBillTime());
		// 创建时间
		labeledGood.setCreateTime(systemDto.getCreateTime());
		// 修改时间
		labeledGood.setModifyTime(systemDto.getModifyTime());
		// 流水号
		labeledGood.setSerialNo(serials);
		// 是否开单初始值
		labeledGood.setInitalVale(FossConstants.YES);
		//是否需要打木架
		labeledGood.setIsNeedWooden(null);
		//件数变动事项
		labeledGood.setNumberChangItems(null);
		//关联原单号流水号
		labeledGood.setOldSerialNo(waybillDto.getOldWaybillNo());
		
		//返回PDA对象
		return labeledGood;
	}


	/**
	 * 
	 * <p>
	 * 更新货签信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:47:00
	 * @param waybillNo
	 * @param serialNo
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService#updateSerialNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public ResultDto updateSerialNo(String waybillNo, String serialNo,
			String active) {
		String code = "1";
		String msg = "";
		LabeledGoodDto labeledGoodDto = new LabeledGoodDto();
		labeledGoodDto.setSerialNo(serialNo);
		labeledGoodDto.setWaybillNo(waybillNo);
		try {
    		LabeledGoodEntity labeledGood = labeledGoodDao
    				.queryByWaybillNoAndSerialNo(labeledGoodDto,FossConstants.ACTIVE.equals(active)?false:true);
    		if (labeledGood != null) {
    			labeledGood.setActive(active);
    			labeledGood.setModifyTime(new Date());
    			int retValue = labeledGoodDao
    					.updateByPrimaryKeySelective(labeledGood);
    			if (retValue == 0) {
    				code = "0";
    				msg = "记录更新失败";
    			}
    		} else {
    			code = "0";
    			msg = "输入的条件不存在有效记录";
    		}
		} catch (TooManyResultsException e) {
			code = "0";
			msg = "输入的条件查询出多条记录";
		}
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号查询所有流水号
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		return labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);
	}

    /**
     * 
     * <p>根据标签号查询标签</p> 
     * @author foss-gengzhe
     * @date 2012-12-7 下午5:02:11
     * @param id
     * @return
     * @see
     */
	@Override
	public LabeledGoodEntity queryByPrimaryKey(String id) {
		return labeledGoodDao.queryByPrimaryKey(id);
	}
	
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
//	public void setLabeledGoodPDADao(ILabeledGoodPDADao labeledGoodPDADao) {
//		this.labeledGoodPDADao = labeledGoodPDADao;
//	}

	
	/**
	 *@param stayHandoverService the stayHandoverService to set.
	 */
	public void setStayHandoverService(IStayHandoverService stayHandoverService) {
		this.stayHandoverService = stayHandoverService;
	}

	/**
	 * 
	 * 更新流水号中的运单号
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午8:12:34
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService#modifyWaybillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyWaybillNo(String oldWaybillNo, String newWaybillNo) {
		 labeledGoodDao.modifyWaybillNo(oldWaybillNo, newWaybillNo);
	}

	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	/**
	 * 根据运单号和流水号获取状态是否有效(Y:有效  N：无效  不存在：NOTEXIST)
	 * @author WangQianJin
	 * @date 2013-6-20 下午4:02:21
	 */
	@Override
	public String getStautsByWaybillNoAndSerialNo(String waybillNo, String serialNo){
		// 默认不存在
		String status=FossConstants.NOTEXIST;
		if(waybillNo!=null && serialNo!=null){
			List<LabeledGoodEntity> list=labeledGoodDao.queryLabeledGoodByWaybillNoAndSerialNo(waybillNo, serialNo);
			if(list!=null && list.size()>0){
				
				
				for(int i =0 ; i < list.size(); i++){
					LabeledGoodEntity lgood=list.get(i);
					if(lgood!=null){
						if(FossConstants.ACTIVE.equals(lgood.getActive())){
							status = FossConstants.ACTIVE;
							//货签表里面可能存在好几条active=N 一条active=Y的记录 
							//只要有一条active=Y的记录  就认为是有效的 所以跳出该循环xiaowei
							break;
						}else{
							status = lgood.getActive();
						}
					}
				}
			
				
			}
		}	
		return status;
	}
	
	private IStayHandoverDetailService stayHandoverDetailService;

	public void setStayHandoverDetailService(
			IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
	}

	@Override
	public List<LabeledGoodEntity> queryLabelGoodStatusisNByWaybillNo(String waybillNo) {
		return labeledGoodDao.queryLabelGoodStatusisNByWaybillNo(waybillNo, FossConstants.NO);
	}
	
	@Override
	public List<LabeledGoodEntity> queryLabeledGoodByWaybillNoWithSerial(String waybillNo, List<String> serialNoList){
		return labeledGoodDao.queryLabeledGoodByWaybillNoWithSerial(waybillNo, serialNoList, FossConstants.YES);
		
	}

	@Override
	public void deleteLabByWaybillNos(List<String> waybillNos) {
		labeledGoodDao.deleteLabByWaybillNos(waybillNos);
	}

	@Override
	public void deleteLabByWaybillNo(String waybillNo) {
		labeledGoodDao.deleteLabByWaybillNo(waybillNo);
		
	}
	
}