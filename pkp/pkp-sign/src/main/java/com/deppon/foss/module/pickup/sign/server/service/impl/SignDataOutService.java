package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDataOutService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultValueDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignDetailResultDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 输出数据给大客户
 * @author 269871-foss zhuliangzhi
 * @date 2015-7-17 下午5:10:43
 *
 */
public class SignDataOutService implements ISignDataOutService {
	/** 
	 * 派送单明细DAO接口
	 */
	private IDeliverbillDetailDao deliverbillDetailDao;
	
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}
	/**
	 * 运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	@Resource
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * 获取一个运单集合的签收数、异常签收数、签收率及运单明细
	 * @param list 运单集合
	 * @author 269871
	 */
	@Override
	public ResultValueDto getReturnData(List<String> list) {
		//派送中票数
		int deliveingSum=0;
		//签收数
		int signSum=0;
		//异常签收数
		int excepSum=0;
		//签收率
		double rateSign=0.0;
		//运单总数
		int totalWayBill=list.size();
		//签收人
		String signManName="";
		//签收时间
		String signDate="";
		//签收备注
		String signNote="";
		//签收状态-'delivering:派送中；signed：已签收'
		String signStatus="delivering";
		//返回签收明细集合
		List<SignDetailResultDto> detailList=new ArrayList<SignDetailResultDto>();
		SignDetailResultDto sd;
		WaybillSignResultEntity signResultEntity=new WaybillSignResultEntity();
		for(String wayBillNo:list){
			sd=new SignDetailResultDto();
			signResultEntity.setWaybillNo(wayBillNo);
			signResultEntity.setActive(FossConstants.ACTIVE);
			// 根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
			if(newEntity!=null){//签收结果表里有记录
				
				if (StringUtil.equals("SIGN_STATUS_ALL", newEntity.getSignStatus())) {//签收状态为全部签收
					signSum++;
					sd.setWaybillNo(wayBillNo);
					//已经签收
					signStatus="signed";
					//获取签收人
					signManName=newEntity.getDeliverymanName();
					//获取签收备注
					signNote=newEntity.getSignNote();
					//获取签收时间
					sd.setSignDate(newEntity.getSignTime());
					//签收明细数据实体的各个属性赋值
					sd.setSignName(signManName);
					sd.setSignNote(signNote);
					
					//查询异常签收数
					if(newEntity.getSignSituation().startsWith("UNNORMAL")){	
						excepSum++;
					}
				}else{//签收状态为部分签收
					//查询派送明细表
					List<DeliverbillDetailEntity> deliverDetailList = deliverbillDetailDao.queryByWaybillNo(wayBillNo);
					if(CollectionUtils.isNotEmpty(deliverDetailList) && deliverDetailList.size()>0){
						deliveingSum++;
						//派送中
						signStatus="delivering";
					}
				}
			}else{//签收结果表里没有记录
				List<DeliverbillDetailEntity> deliverDetailList = deliverbillDetailDao.queryByWaybillNo(wayBillNo);
				//派送明细表里有数据
				if(CollectionUtils.isNotEmpty(deliverDetailList) && deliverDetailList.size()>0){
					deliveingSum++;
					//派送中
					signStatus="delivering";
				}
			}
			
			//签收明细数据实体_状态赋值
			sd.setSignStatus(signStatus);
			detailList.add(sd);
		}
		//创建返回签收实体
		ResultValueDto resdto=new ResultValueDto();
		//设置派送中票数
		resdto.setDeliveingNum(deliveingSum);
		//设置异常签收数
		resdto.setExcepNum(excepSum);
		//设置签收数
		resdto.setSignedNum(signSum);
		//计算签收率
		rateSign=signSum/(double)totalWayBill;
		//签收率转为百分数
		NumberFormat nf=NumberFormat.getPercentInstance();
		//保存到小数点后2位
		nf.setMaximumFractionDigits(2);
		String rate=nf.format(rateSign);
		//设置签收率
		resdto.setSignRate(rate);
		//设置明细集合
		resdto.setList(detailList);
		return resdto;
	}
}
