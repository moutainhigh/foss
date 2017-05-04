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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaWaybillService.java
 * 
 * FILE NAME        	: IPdaWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import java.io.IOException;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.RewardFineDetailVo;
import com.deppon.foss.module.pickup.waybill.shared.domain.ComplaintDetail;
import com.deppon.foss.module.pickup.waybill.shared.domain.ComplaintInfoForPdaResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.RewardFineDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponReverseResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPictureWaybillResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.primeton.bfs.engine.json.JSONException;

/**
 * 
 * PDA服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-19 上午10:41:04,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-19 上午10:41:04
 * @since
 * @version
 */
public interface IPdaWaybillService {
	
	/**
	 * 
	 * <p>PDA下拉和票汇总表</p> 
	 * @author PDA-fanbangyu
	 * @date 2015-9-16 下午14:10:30
	 * @param goodAttr
	 * @return
	 * @see
	 */
	List<LabelPrintDto> downloadCombinateBill(List<String> listLabelPrintEntity);

	
	/**
	 * 
	 * <p>PDA上传货物属性</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:47:36
	 * @param goodAttr
	 * @return
	 * @see
	 */
	ResultDto submitGoodsAttr(PDAGoodsAttrDto goodAttr);
	
	/**
	 * 
	 * <p>优惠券验证/使用</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:48:09
	 * @param couponInfo
	 * @return
	 * @see
	 */
	CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo);
	
	/**
	 * 
	 * <p>反使用优惠券</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:48:28
	 * @param couponNumber
	 * @return
	 * @see
	 */
	CouponReverseResultDto reverseCouponState(String couponNumber);
	
	/**
	 * 
	 * <p>PDA上传标签打印信息</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:48:38
	 * @param labeledGoodPDA
	 * @return
	 * @see
	 */
	ResultDto uploadLabeledGood(LabeledGoodPDADto labeledGoodPDA);
	
	/**
	 * 
	 * <p>PDA运单提交</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-12 下午4:31:34
	 * @param waybillPendingDto
	 * @see
	 */
	ResultDto submitWaybillByPDA(WaybillPdaDto waybillPdaDto);
	
	

	/**
	 * 
	 * 偏线和航运的PDA运单提交
	 * @author foss-gengzhe
	 * @date 2013-1-21 上午10:22:46
	 * @param waybillPdaDto
	 * @return
	 * @see
	 */
	ResultDto submitWaybillByPDAForAirAndVehicle(WaybillPdaDto waybillPdaDto);
	 
	/**
	 * 
	 * PDA开单
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-25 下午4:51:12
	 */
	void submitWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode);
	
	/**
	 * 
	 * PDA快递开单
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-22
	 */
	void submitWaybillExpress(WaybillExpressPdaDto waybillPdaDto, String billOrgCode);
		
	/**
	 * 
	 * PDA开单提交至正式运单
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-25 下午4:53:06
	 */
	void addForTransfer(WaybillPdaDto waybillPdaDto, String billOrgCode);

	

	/**
	 * @param waybillPdaDto
	 * @param billOrgCode
	 */
	void addForTransferExpress(WaybillExpressPdaDto waybillPdaDto,
			String billOrgCode);
	
	void submitPdaWaybill(WaybillExpressPdaDto waybillPdaDto,
			String billOrgCode) ;
	
	/** 
	 * @remark 修改PDA信息(PDA外部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	ResultDto updateWaybillByPDA(WaybillPdaDto waybillPdaDto);
	 
	/** 
	 * @remark 修改PDA待补录信息(FOSS内部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	void updateWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode);		

	/** 
	 * @remark 修改PDA正式运单信息(FOSS内部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	void updateForTransfer(WaybillPdaDto waybillPdaDto, String billOrgCode, WaybillPendingEntity pending);
	
	/**
	 * 获取CRM营销活动信息(PDA外部调用)
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto);
	
	/**
	 * 快递获取CRM营销活动信息(PDA外部调用)
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoListExpress(CrmActiveParamVo pdaParamDto);
	
	/**
	 * 针对大客户数据没有修改过记录可以直接激活
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:56:39
	 * @param eWaybillConditionDto
	 * @return
	 */
	ResultDto batchGenerateActiveEWaybillByPda(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 针对散客信息什么的可以修改的数据进行激活
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:56:39
	 * @param eWaybillConditionDto
	 * @return
	 */
	ResultDto activeEWaybillByPda(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode);
	
	/**
	 * 退回对应的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:57:38
	 */
	ResultDto returnEWaybillByPda(EWaybillConditionDto eWaybillConditionDto);

	
	/**
	 * 司机撤回运单
	 * @param waybill
	 * @param driverCode
	 * @return
	 */
	ResultDto cancelWaybillPictureByPDA(String waybill,	String driverCode);
	
	/**
	 * PDA图片运单提交
	 * @author zxy
	 * @date 2014-10-10
	 * @param waybillPdaDto
	 * @return
	 */
	ResultDto submitWaybillPictureByPDA(WaybillPicturePdaDto waybillPicturePdaDto);
	
	/**
	 * PDA图片运单查询
	 * @author zxy
	 * @date 2014-10-10
	 * @param waybillPdaDto
	 * @return
	 */
	QueryPictureWaybillResultDto queryWaybillPictureByPDA(WaybillPicturePdaDto waybillPicturePdaDto) throws Exception;
	
	/**
	 * 图片自动开单
	 * @author PanGuoYang
	 * @date 2014-10-10
	 * @return
	 * @throws Exception 
	 */
	ResultDto autoSubmitWaybillByPDA(WoodenRequirePdaDto woodenRequirePdaDto) throws Exception;

	/**
	 * 通过单号获取运单信息（图片开单项目使用）
	 * @param waybillNo
	 * @author 076234 PanGuoYang
	 * @return
	 */
	String queryWaybillPictureByWaybillNo(String waybillNo);
	/**
	 * 手机端反馈已接 删除推送信息
	 * gcl 14.12.4
	 * @return
	 */
	int pushMessageStatus(String waybillCode);
    /**
     * 自动生成运单
     * @param waybill
     */
	public void submitWaybill(WaybillDto waybill);
	
	/**
	 * 保存PDA扫描任务反馈的信息
	 * @author 200972 lanhuilan 
	 * @date 2015-01-30  下午10:02:53
	 */
	void savePdaScanInfo(PdaScanQueryDto pdaScanInfo);
	
	/**
	 * 完成接货并激活运单
	 * @author 200972 lanhuilan 
	 * @date 2015-02-03  下午10:02:53
	 */
	void overPickup(String taskId);
	/**
	 * DMANA-6657 PDA系统从FOSS间接获得差错和投诉信息
	 * @author Foss-xudan
	 * @throws IOException 
	 * @date 
	 */
	/**
	 * DMANA-6657 PDA系统从FOSS间接获得差错和投诉信息
	 * @author Foss-xudan
	 * @throws IOException 
	 * @date 2014-12-15
	 */
	List<ComplaintDetail> queryComplaintInfoDetail(RewardFineDetailVo rewardFineConditionDto) throws IOException;

	List<RewardFineDetailEntity> queryRewardFineDetail(
			RewardFineDetailVo rewardFineConditionDto) throws IOException;

	/**
	 * 提交快递PDA开快递返单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-18 21:26:18
	 * @param waybillExpressPdaDto
	 * @param billOrgCode
	 * @return
	 */
	ResultDto submitWaybillExpressReturnBill(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode);
	/**
	 * PDA创建返货工单，提交
	 */
	boolean createReturneDworkOrder(WaybillEntity bean);
	/**
	 * CRM处理完成返货工单
	 */
	List<CrmReturnedGoodsDto> disposeReturnedDworkOrder(String reportDepartmentCode);
	
	/**
	 * PDA开返货 校验单号与返货方式
	 * @param oriWayBillNo
	 * @param returnMode
	 * @author foss-206860
	 * */
	void validateReturnGoodsWaybillNo(String oriWayBillNo,String returnMode);
}