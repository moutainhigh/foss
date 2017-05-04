package com.deppon.foss.module.base.uumsitf.esb.server;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.json.MsgOnlineResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.EmployeeException;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MsgOnlineInfo;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MsgOnlineInfoSyncRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MsgOnlineInfoSyncResponse;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.ProcessDetail;
import com.eos.system.utility.StringUtil;

/**
 * 家装项目--关于dop同步给foss 提货及签收信息同步接口
 * @author wangyanjin-273241
 * @date 2015-9-7 上午10:23:24
 *
 */
public class MsgOnlineInfoProcessor implements IProcess{
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgOnlineInfoProcessor.class);
	/**
	 * 在线通知Dao
	 */
	private IMsgOnlineDao msgOnlineDao;
	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}
	
	//成功失败次数声明
	int successCount = 0, failCount = 0;
	// 业务锁
	private IBusinessLockService businessLockService;
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	@Override
	public Object process(Object req) throws ESBBusinessException {
		//Request
		MsgOnlineInfoSyncRequest msgRequest = (MsgOnlineInfoSyncRequest)req;
		//Response
		MsgOnlineInfoSyncResponse msgResponse = new MsgOnlineInfoSyncResponse();
		//判断
		if(null !=msgRequest){
			LOGGER.info("------------------------------------家装项目：提货及签收信息展示在FOSS在线通知处begin------------------------------------");
			//处理明细list
			List<ProcessDetail> detailList = new ArrayList<ProcessDetail>();	
			List<MsgOnlineInfo> msgInfos  =msgRequest.getMsgOnlineList();
			//非空处理
			if(CollectionUtils.isNotEmpty(msgInfos)){
				//批处理来自DOP的提货信息-273241-wangyanjin
				//存入信息至FOSS上游中间表
					for(MsgOnlineInfo info:msgInfos){
						ProcessDetail result=new ProcessDetail();
						// 业务锁
						MutexElement mutex = new MutexElement(
								info.getWaybillNo()+new Date(), "DOP_WAYBILL_NUMBER_DATE",
								MutexElementType.DOP_WAYBILL_NUMBER_DATE);
						LOGGER.info("开始加锁：" + mutex.getBusinessNo());
						boolean businessResult = businessLockService.lock(mutex,
								NumberConstants.NUMBER_10);
					try{
						saveMsgOnlineInfo(detailList, info, result, mutex,
								businessResult);
				}catch(Exception e){
					failCount++;
					result.setJudgment(info.getJudgment());
					result.setNumber(info.getWaybillNo());
					result.setReason(e.getMessage());
					result.setResult(false);
					detailList.add(result); 
					LOGGER.info("开始解锁：" + mutex.getBusinessNo());
					// 解业务锁
					businessLockService.unlock(mutex);
					LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				}
				}
				//设置成功条数
				msgResponse.setSuccessCount(successCount);
				msgResponse.setFailCount(failCount);
				msgResponse.setDetail(detailList);
				LOGGER.info("DOP调用FOSS提货及签收信息接口，返回报文:\n"
						+ new MsgOnlineResponseTrans().fromMessage(msgResponse));
			}
			LOGGER.info("------------------------------------家装项目：提货及签收信息展示在FOSS在线通知处end------------------------------------");
		}
		return msgResponse;	
}
	private void saveMsgOnlineInfo(List<ProcessDetail> detailList,
			MsgOnlineInfo info, ProcessDetail result, MutexElement mutex,
			boolean businessResult) {
		if (businessResult) {
			int i = 0;
			int j = 0;
			if (info.getJudgment() == 0) {// 判断，如果judgment值为0，则DOP传来的信息是提货信息
				// 先判断传来的必填字段不为空
				if (StringUtil.isEmpty(info.getSupplierOrderNo())) {
					LOGGER.info("供应商订单号为空！");
					throw new EmployeeException("供应商订单号为空！");
				}
				if (StringUtil.isEmpty(info.getName())) {
					LOGGER.info("提货人姓名为空！");
					throw new EmployeeException("提货人姓名为空！");
				}
				if (StringUtil.isEmpty(info.getSupplierName())) {
					LOGGER.info("提货供应商名称为空！");
					throw new EmployeeException("提货供应商名称为空！");
				}
				if (StringUtil.isEmpty(info.getSupplierNo())) {
					LOGGER.info("供应商编码为空！");
					throw new EmployeeException("供应商编码为空！");
				}
				if (StringUtil.isEmpty(info.getSupplierMobile())) {
					LOGGER.info("提货供应商联系电话为空！");
					throw new EmployeeException("提货供应商联系电话为空！");
				}
				if (StringUtil.isEmpty(info.getLoadingStation())) {
					LOGGER.info("提货网点为空！");
					throw new EmployeeException("提货网点为空！");
				}
				if (StringUtil.isEmpty(info.getLoadingStationCode())) {
					LOGGER.info("提货网点编码为空！");
					throw new EmployeeException("提货网点编码为空！");
				}
				if ((Integer) info.getOperateType() == null) {
					LOGGER.info("操作符为空！");
					throw new EmployeeException("操作符为空！");
				}
				if (StringUtil.isEmpty(info.getReceiveOrg())) {
					LOGGER.info("收货部门名称为空！");
					throw new EmployeeException("收货部门名称为空！");
				}
				if (StringUtil.isEmpty(info.getReceiveOrgCode())) {
					LOGGER.info("收货部门编码为空！");
					throw new EmployeeException("收货部门编码为空！");
				}
				// 判断操作类型：type=0，表示新增；type=1，表示变更。
				MsgOnlineEntity myEntity = this.transToFossEntity0(info);
				if (info.getOperateType() == 0 || info.getOperateType() == 1) {
					// 给到达部门发在线通知
					i = msgOnlineDao.addOnlineMsg(myEntity, 0);
				}/*
				 * else if(info.getOperateType()==1){ //给到达部门发在线通知
				 * i=msgOnlineDao.addOnlineMsg(myEntity,0); }
				 */else {
					LOGGER.info("DOP过来的操作类型（OperateType值）错误！");
					throw new EmployeeException("DOP过来的操作类型（OperateType值）错误！");
				}
				if (i > 0) {// 插入成功
					successCount++;
					result.setResult(true);
					result.setNumber(info.getWaybillNo());
				} else {
					failCount++;
					result.setResult(false);
					result.setNumber(info.getWaybillNo());
					result.setReason("DOP提货信息新增至FOSS在线通知处失败!");
				}
				result.setJudgment(info.getJudgment());
				detailList.add(result);
			} else if (info.getJudgment() == 1) {// 判断，如果judgment值为1，则DOP传来的信息是签收信息
				// 先判断传来的必填字段不为空
				if (StringUtil.isEmpty(info.getSupplierNo())) {
					LOGGER.info("供应商编码为空！");
					throw new EmployeeException("供应商编码为空！");
				}
				if (StringUtil.isEmpty(info.getWaybillNo())) {
					LOGGER.info("运单号为空！");
					throw new EmployeeException("运单号为空！");
				}
				if (StringUtil.isEmpty(info.getPayMethod())) {
					LOGGER.info("付款方式为空！");
					throw new EmployeeException("付款方式为空！");
				}
				if (StringUtil.isEmpty(info.getReceiveOrgCode())) {
					LOGGER.info("收货部门编码为空！");
					throw new EmployeeException("收货部门编码为空！");
				}
				if (StringUtil.isEmpty(info.getLoadingStationCode())) {
					LOGGER.info("提货网点编码为空！");
					throw new EmployeeException("提货网点编码为空！");
				}
				if (StringUtil.isEmpty(info.getReceiveName())) {
					LOGGER.info("收货联系人为空！");
					throw new EmployeeException("收货联系人为空！");
				}
				if (info.getSignTime() == null) {
					LOGGER.info("签收时间为空！");
					throw new EmployeeException("签收时间为空！");
				}
				if (StringUtil.isEmpty(info.getSignState())) {
					LOGGER.info("签收状态为空！");
					throw new EmployeeException("签收状态为空！");
				}
				// 给到达部门发在线通知
				MsgOnlineEntity myEntity = transToFossEntity1(info);
				i = msgOnlineDao.addOnlineMsg(myEntity, 0);
				// 给出发部门发在线通知
				myEntity.setSendOrgName(info.getLoadingStation());
				myEntity.setSendOrgCode(info.getLoadingStationCode());
				myEntity.setReceiveOrgName(info.getReceiveOrg());
				myEntity.setReceiveOrgCode(info.getReceiveOrgCode());
				j = msgOnlineDao.addOnlineMsg(myEntity, 0);
				if (i > 0 && j > 0) {// 插入成功
					successCount++;
					result.setResult(true);
					result.setNumber(info.getWaybillNo());
				} else {
					failCount++;
					result.setResult(false);
					result.setNumber(info.getWaybillNo());
					result.setReason("DOP签收信息新增至FOSS在线通知处失败!");
				}
				result.setJudgment(info.getJudgment());
				detailList.add(result);
			} else {
				LOGGER.info("DOP过来的判断条件（judgment值）错误！");
				throw new EmployeeException("DOP过来的判断条件（judgment值）错误！");
			}
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		} else {
			// 错误次数加1
			failCount++;
			ProcessDetail d = new ProcessDetail();
			d.setResult(false);
			d.setJudgment(info.getJudgment());
			d.setNumber(info.getWaybillNo());
			d.setReason("加锁失败！");
			detailList.add(d);
			// 打印错误信息
			LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		}
	}
		
	/**提货信息
	 * 将同步过来的Object 转换成FOSS对象
	 * @param msgInfo
	 * @param msgResult
	 */
	private MsgOnlineEntity transToFossEntity0(MsgOnlineInfo msgInfo) {
		MsgOnlineEntity fossEntity=new MsgOnlineEntity();
		//保存DOP传过来的运单号WaybillNo
		if(StringUtil.isNotEmpty(msgInfo.getWaybillNo())){
			fossEntity.setWaybillNo(msgInfo.getWaybillNo());
		}
		//保存DOP传过来的提货信息在通知内容
		if((Integer)msgInfo.getOperateType()==0){
			msgInfo.setSignState("新增");
		}else if((Integer)msgInfo.getOperateType()==1){
			msgInfo.setSignState("变更");
		}
		String str0="供应商订单号:"+msgInfo.getSupplierOrderNo()+"\n"
                   +"提货人姓名:"+msgInfo.getName()+"\n"
		           +"提货供应商名称:"+msgInfo.getSupplierName()+"\n"
		           +"提货供应商联系电话:"+msgInfo.getSupplierMobile()+"\n"
		           +"提货网点:"+msgInfo.getLoadingStation()+"\n"
		           +"提货网点编码:"+msgInfo.getLoadingStationCode()+"\n"
		           +"操作类型："+msgInfo.getSignState()+"\n"//用于界面操作类型展示
		           +"备注:"+msgInfo.getRemark()+"\n"
		           +"收货部门名称:"+msgInfo.getReceiveOrg()+"\n"
		           +"收货部门编码:"+msgInfo.getReceiveOrgCode();
			try {
				if(str0.getBytes(ComnConst.STRING_TYPE_UTF8).length > NumberConstants.PROP_LENG_1000){
					str0 = str0.substring(NumberConstants.ZERO,NumberConstants.NUMBER_333);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			fossEntity.setContext(str0);
			
			//获取起草部门，即收货部门
			fossEntity.setSendOrgName(msgInfo.getReceiveOrg());
			//获取起草部门编码，即收货部门编码
			fossEntity.setSendOrgCode(msgInfo.getReceiveOrgCode());
			//获取起草人，即提货供应商
			fossEntity.setSendUserName(msgInfo.getSupplierName());	
			//获取起草人编码，即提货供应商编码
			fossEntity.setSendUserCode(msgInfo.getSupplierNo());
			//获取受理部门，即提货网点
			fossEntity.setReceiveOrgName(msgInfo.getLoadingStation());
			//获取受理部门编码，即提货网点编码
			fossEntity.setReceiveOrgCode(msgInfo.getLoadingStationCode());
			//获取起草时间，即当前操作时间
			fossEntity.setCreateTime(new Date());
			//获取受理状态，为“未受理”
			fossEntity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
			return fossEntity;
		}
	
	/**签收信息
	 * 将同步过来的Object 转换成FOSS对象
	 * @param msgInfo
	 * @param msgResult
	 */
	private MsgOnlineEntity transToFossEntity1(MsgOnlineInfo msgInfo) {
		MsgOnlineEntity fossEntity=new MsgOnlineEntity();
		//保存DOP传过来的运单号WaybillNo
		if(StringUtil.isNotEmpty(msgInfo.getWaybillNo())){
			fossEntity.setWaybillNo(msgInfo.getWaybillNo());
		}
		
		//签收状态转换：01 正常签收；02 异常-破损；03 异常-潮湿；04 异常-污染；05 异常-内物短少；06 异常-其他；07 同票多类异常；08 货物及费用与运单信息不符
		if(StringUtil.equal(msgInfo.getSignState(), "01")){
			msgInfo.setSignState("正常签收");
		}else if(StringUtil.equal(msgInfo.getSignState(), "02")){
			msgInfo.setSignState("异常-破损");
		}else if(StringUtil.equal(msgInfo.getSignState(), "03")){
			msgInfo.setSignState("异常-潮湿");
		}else if(StringUtil.equal(msgInfo.getSignState(), "04")){
			msgInfo.setSignState("异常-污染");
		}else if(StringUtil.equal(msgInfo.getSignState(), "05")){
			msgInfo.setSignState("异常-内物短少");
		}else if(StringUtil.equal(msgInfo.getSignState(), "06")){
			msgInfo.setSignState("异常-其他");
		}else if(StringUtil.equal(msgInfo.getSignState(), "07")){
			msgInfo.setSignState("同票多类异常");
		}else if(StringUtil.equal(msgInfo.getSignState(), "08")){
			msgInfo.setSignState("货物及费用与运单信息不符");
		}
		
		//保存DOP传过来的签收信息在通知内容
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1="供应商名称:"+msgInfo.getSupplierName()+"\n"
	               +"供应商编码:"+msgInfo.getSupplierNo()+"\n"
			       +"付款方式:"+msgInfo.getPayMethod()+"\n"
			       +"收货部门名称:"+msgInfo.getReceiveOrg()+"\n"
			       +"收货部门编码:"+msgInfo.getReceiveOrgCode()+"\n"
			       +"提货网点名称:"+msgInfo.getLoadingStation()+"\n"
			       +"提货网点编码："+msgInfo.getLoadingStationCode()+"\n"
			       +"收货联系人:"+msgInfo.getReceiveName()+"\n"
			       +"收货联系人电话:"+msgInfo.getReceiveMobile()+"\n"
			       +"签收时间:"+sdf.format(msgInfo.getSignTime())+"\n"
			       +"签收状态："+msgInfo.getSignState()+"\n"
			       +"签收件数："+msgInfo.getSignNumber()+"\n"
			       +"签收备注："+msgInfo.getSignRemark();
		try {
			if(str1.getBytes(ComnConst.STRING_TYPE_UTF8).length > NumberConstants.PROP_LENG_1000){
				str1 = str1.substring(NumberConstants.ZERO,NumberConstants.NUMBER_333);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    fossEntity.setContext(str1);		
		//获取起草部门，即收货部门
		fossEntity.setSendOrgName(msgInfo.getReceiveOrg());	
		//获取起草部门编码，即收货部门编码
		fossEntity.setSendOrgCode(msgInfo.getReceiveOrgCode());
		//获取起草人，即提货供应商
		fossEntity.setSendUserName(msgInfo.getSupplierName());	
		//获取起草人编码，即提货供应商编码
		fossEntity.setSendUserCode(msgInfo.getSupplierNo());
		//获取受理部门，即提货网点
		fossEntity.setReceiveOrgName(msgInfo.getLoadingStation());	
		//获取受理部门编码，即提货网点编码
		fossEntity.setReceiveOrgCode(msgInfo.getLoadingStationCode());
		//获取起草时间，即当前操作时间
		fossEntity.setCreateTime(new Date());		
		//获取受理状态，为“未受理”
		fossEntity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
		return fossEntity;
	}
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		return null;
	}
}
