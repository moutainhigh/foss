package com.deppon.foss.module.base.common.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.common.api.shared.exception.OnlineMessageException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class OnLineMsgService implements IOnLineMsgService{
	private IMsgOnlineDao msgOnlineDao;
	/**
	 * 记录日志
	 */
	public static final Logger logger = LoggerFactory
			.getLogger(IOnLineMsgService.class);
	

	/**
	 * 新增一条在线通知消息
	 * 
	 * @author:	WangPeng
	 * @date:	2013-7-10上午8:54:07
	 * @param:	MsgOnlineEntity
	 * @return: boolean
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean addOnlineMsg(MsgOnlineDto msgOnlineDto)throws OnlineMessageException{
		MsgOnlineEntity entity = new MsgOnlineEntity();
		String sendOrgCode = FossUserContext.getCurrentDeptCode();
		String sendOrgName = FossUserContext.getCurrentDeptName();
		String sendUserCode  = FossUserContext.getCurrentUser().getUserName();
		String sendUserName = FossUserContext.getCurrentUser().getEmpName();
		
		entity.setWaybillNo(msgOnlineDto.getBillNo());
		entity.setSendOrgCode(sendOrgCode);
		entity.setSendOrgName(sendOrgName);
		entity.setReceiveOrgCode(msgOnlineDto.getReceiveOrgCode());
		entity.setReceiveOrgName(msgOnlineDto.getReceiveOrgName());
		entity.setContext(msgOnlineDto.getContext());
		entity.setSendUserCode(sendUserCode);
		entity.setSendUserName(sendUserName);
		entity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
		boolean flag = false;
		//判断参数是否为空
		if(null == entity){
			throw new OnlineMessageException("参数为空");
		}
		else{
			if(StringUtils.isEmpty(entity.getWaybillNo())){
				throw new OnlineMessageException("运单单号为空");
			}
			if(StringUtils.isEmpty(entity.getSendOrgCode())){
				throw new OnlineMessageException("发送方组织编码为空");
			}
			if(StringUtils.isEmpty(entity.getReceiveOrgCode())){
				throw new OnlineMessageException("受理方组织编码为空");
			}
			try {
				//0代表界面新增数据
				int status = 0;
				//获得当前登录人的信息
				entity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
				//新增一条消息记录
				msgOnlineDao.addOnlineMsg(entity,status);
				flag = true;
				
			} catch (Exception e) {
				logger.debug(e.getStackTrace().toString());
				throw new OnlineMessageException(e.getStackTrace().toString());
			}
		}
		return flag;
		
	}
	/** 
	 * <p>ECS快递推送在线通知给FOSS，FOSS插入本地数据库的方法</p> 
	 * @author 232607 
	 * @date 2016-4-26 上午8:59:01
	 * @param msgOnlineDto
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService#addOnlineMsgByECS(com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto)
	 */
	@Override
	public boolean addOnlineMsgByECS(MsgOnlineEntity entity){
		if(entity==null){
			throw new OnlineMessageException("实体为空");
		}
		if(StringUtils.isEmpty(entity.getWaybillNo())){
			throw new OnlineMessageException("运单号为空");
		}
		if(StringUtils.isEmpty(entity.getSendOrgCode())){
			throw new OnlineMessageException("发送方组织编码为空");
		}
		if(StringUtils.isEmpty(entity.getReceiveOrgCode())){
			throw new OnlineMessageException("接收方组织编码为空");
		}
		int num;
		try {
			//受理状态为未受理
			entity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
			entity.setCreateUser(entity.getSendUserCode());
			//新增一条消息记录
			num=msgOnlineDao.addOnlineMsg(entity,0);
		} catch (Exception e) {
			logger.debug(e.getStackTrace().toString());
			throw new OnlineMessageException(e.getStackTrace().toString());
		}
		return num==1?true:false;
	}
	
	
	/**
	 * 修改一条在线通知消息，根据id
	 * 
	 * @author: WangPeng
	 * @date:	2013-7-10上午8:56:58
	 * @param:	String id
	 * @return:	boolean
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateOnlineMsg(MsgOnlineEntity entity) {

		boolean flag = false;
		// 判断参数是否为空
		if (null == entity) {
			throw new OnlineMessageException("参数为空");
		} else {
			if(StringUtils.isEmpty(entity.getId())){
				throw new OnlineMessageException("记录ID为空");
			}
			if(StringUtils.isEmpty(entity.getAcceptStatus())){
				throw new OnlineMessageException("受理状态为空");
			}
			if(StringUtils.isEmpty(entity.getContext())){
				throw new OnlineMessageException("消息内容不能为空");
			}
			//获得当前登录人的工号
			String empcode =FossUserContext.getCurrentUser().getUserName();
			//获得当前登录人的姓名
			String empName = FossUserContext.getCurrentUser().getEmpName();
			try {
				
				entity.setModifyUserCode(empcode);
				entity.setModifyUserName(empName);
				// 作废一条消息记录
			 int count = msgOnlineDao.updateOnlineMsg(entity);
			 if(count > 0){
				 //1代表作废后在新增
				 int status = 1;
				 entity.setCreateUser(empcode);
				 int m = msgOnlineDao.addOnlineMsg(entity,status);
				 if(m > 0){
					 flag = true;
				 }
			 }

			} catch (Exception e) {
				logger.debug(e.getStackTrace().toString());
				throw new OnlineMessageException("修改失败");
			}
		}
		return flag;
	}


	public IMsgOnlineDao getMsgOnlineDao() {
		return msgOnlineDao;
	}


	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}
	/**
	 * 批量增加在线通知消息
	 * 
	 * @author:	WangPeng
	 * @date:	2013-7-10上午8:54:07
	 * @param:	MsgOnlineEntity
	 * @return: void
	 */
	@Override
	public boolean addOnlineMsgList(List<MsgOnlineDto> msgOnlineDtos) {
		boolean flag = false;
		if(CollectionUtils.isNotEmpty(msgOnlineDtos)){
			for(MsgOnlineDto msgOnlineDto:msgOnlineDtos){
				msgOnlineDto.setReceiveOrgCode(msgOnlineDto.getBillSendOrgCode());
				msgOnlineDto.setReceiveOrgName(msgOnlineDto.getBillSendOrgName());
				flag = addOnlineMsg(msgOnlineDto);
				if(flag==false){
					throw new OnlineMessageException("发送失败！");
				}
			}
		}else{
			throw new OnlineMessageException("请选择发送单号！");
		}
		return flag;
	}


    /**
     * 
     * <p>导出中转线路</p> 
     * @author foss-zhujunyong
     * @date Mar 5, 2013 10:28:22 AM
     * @param line
     * @return
     * @see
     */
    private List<String> exportTransferLine(MsgOnlineEntity entity){
	List<String> result = new ArrayList<String>();
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//将时间转换为字符串
	//运单号
	if(StringUtils.isEmpty(entity.getWaybillNo())){
		result.add(null);
	}else{
		result.add(entity.getWaybillNo());
	}
	//起草部门
	if(StringUtils.isEmpty(entity.getSendOrgName())){
		result.add(null);
	}else{
		result.add(entity.getSendOrgName());
	}
	//起草人
	if(StringUtils.isEmpty(entity.getSendUserName())){
		result.add(null);
	}else{
		result.add(entity.getSendUserName());
	}
	//创建时间
	if(null==entity.getCreateTime()){
		result.add(null);
	}else{
		result.add(formatter.format(entity.getCreateTime()));
	}
	//通知内容
	if(null==entity.getContext()){
		result.add(null);
	}else{
		result.add(entity.getContext());
	}
	//受理状态
	if("N".equals(entity.getAcceptStatus())){
		result.add("拒绝");
	}else if("Y".equals(entity.getAcceptStatus())){
		result.add("受理");
	}else{
		result.add("未受理");
	}
	//受理备注
	if(null==entity.getRemarks()){
		result.add(null);
	}else{
		result.add(entity.getRemarks());
	}
	//受理部门
	if(null==entity.getReceiveOrgName()){
		result.add(null);
	}else{
		result.add(entity.getReceiveOrgName());
	}
	//受理人员
	if(null==entity.getModifyUserName()){
		result.add(null);
	}else{
		result.add(entity.getModifyUserName());
	}
	//受理时间
	if(null==entity.getModifyTime()){
		result.add(null);
	}else{
		result.add(formatter.format(entity.getModifyTime()));
	}
	return result;
    }
	@Override
	public ExportResource exportMsgList(MsgOnlineEntity entity) {
		// 线路对象的线路类别必须指定，用来判断是导出运作线路还是始发或到达线路
//		if (entity == null || StringUtils.isBlank(line.getLineSort())) {
//		    return null;
//		}
		List<MsgOnlineEntity> list =  msgOnlineDao.queryOnlineMsgByEntity(entity, 0, Integer.MAX_VALUE);
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		// 在循环外判断好，提高性能
//		boolean isTransfer = StringUtils.equals(line.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_TRANSFER);

//		// 取线路类型的数据字典(专线,偏线,空运)
//		List<DataDictionaryValueEntity> lineTypeList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.BSE_TRANS_LINE_TYPE);
//		Map<String, String> lineTypeMap = new HashMap<String, String>();
//		for (DataDictionaryValueEntity data : lineTypeList) {
//		    lineTypeMap.put(data.getValueCode(), data.getValueName());
//		}
//		// 取运输类型的数据字典(汽运,空运)
//		List<DataDictionaryValueEntity> transTypeList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.BSE_TRANS_TYPE);
//		Map<String, String> transTypeMap = new HashMap<String, String>();
//		for (DataDictionaryValueEntity data : transTypeList) {
//		    transTypeMap.put(data.getValueCode(), data.getValueName());
//		}
		
		for (MsgOnlineEntity msgOnlineentity : list) {
		    // 导出运作或始发到达格式
		    List<String> result = exportTransferLine(msgOnlineentity);
		    resultList.add(result);
		}
		ExportResource sheet = new ExportResource();
		
	    sheet.setHeads(ComnConst.VEHICLE_OLINEMSG_TITLE);
		
		sheet.setRowList(resultList);
		return sheet;
	    }

	
}
