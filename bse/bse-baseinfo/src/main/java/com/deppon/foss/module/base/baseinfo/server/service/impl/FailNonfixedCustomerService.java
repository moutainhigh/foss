package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.crm.CreateScatterRequest;
import com.deppon.esb.inteface.domain.crm.ScatterInfo;
import com.deppon.esb.pojo.transformer.jaxb.CreateScatterRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFailNonfixedCustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFailNonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FailNonfixedCustomerEntity;
import com.deppon.foss.util.UUIDUtils;

public class FailNonfixedCustomerService implements IFailNonfixedCustomerService{
	
	IFailNonfixedCustomerDao  failNonfixedCustomerDao;
	/**
	 * 日志信息
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FailNonfixedCustomerService.class);
	/**
	 * 服务编码
	 */
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECIEVE_NONFIXEDCUSTOMER";

	/**
	 * 分页查询
	 *273296
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<FailNonfixedCustomerEntity> queryListFailNonfixedCustomerEntity(
			int start, int limit) {
		
		return failNonfixedCustomerDao.queryListFailNonfixedCustomerEntity(start, limit);
	}

	/**
	 * 新增记录
	 *273296
	 * @param entity
	 */
	@Override
	public void insert(FailNonfixedCustomerEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		failNonfixedCustomerDao.insert(entity);
		
	}

	/**
	 * 变更推送失败记录数
	 *273296
	 * @param entity
	 */
	@Override
	public void updateFailCount(FailNonfixedCustomerEntity entity) {
		failNonfixedCustomerDao.updateFailCount(entity);
		
	}
	/**
	 * 删除推送成功的记录
	 *273296
	 * @param id
	 */
	@Override
	public void deleteFailNonfixedCustomerEntity(String id) {
		failNonfixedCustomerDao.deleteFailNonfixedCustomerEntity(id);
		
	}

	/**
	 * 向CRM定时推送接口
	 */
	@Override
	public void sendInfoToCrm() {
		//每次查300条
		List<FailNonfixedCustomerEntity> list = queryListFailNonfixedCustomerEntity(
				0, 300);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		// 创建传递散客的请求对象
		CreateScatterRequest request = new CreateScatterRequest();
		List<ScatterInfo> infoList = request.getScatterInfos();
		StringBuffer codes = new StringBuffer();
		String[] ids = new String[list.size()];
		int i = 0;
		for (FailNonfixedCustomerEntity entity : list) {
			ScatterInfo info = new ScatterInfo();
			info.setFossId(entity.getOwnCustId());
			info.setCustName(entity.getCustomerName());
			info.setCustNumber(entity.getCustomerCode());
			info.setLinkmanName(entity.getLinkManName());
			// 手机号码
			info.setMobilephone(entity.getCellPhone());
			// 固定电话
			info.setTelephone(entity.getMobile());
			info.setLinkmanAdress(entity.getAddress());
			info.setCustAttribute(entity.getCustomerAttributes());
			info.setCreateUser(entity.getCreateUser());
			info.setCreateTime(entity.getCreateDate());
			info.setBusinessType(entity.getBusinessType());
			info.setStandardCode(entity.getUnifiedCode());
			info.setProvinceCode(entity.getProCode());
			info.setCityCode(entity.getCityCode());
			info.setAreaCode(entity.getCountyCode());
			info.setLinkmanAdressRemark(entity.getCustAddrRemark());
			infoList.add(info);
			ids[i] = entity.getId();
			i++;
			codes.append(entity.getCustomerCode()).append(
					SymbolConstants.EN_COMMA);
		}
		String businessCodes = codes.toString();
		String businessId = businessCodes.substring(0,
				businessCodes.lastIndexOf(","));
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(businessId);
		accessHeader.setBusinessDesc1("发送开单创建的散客数据到CRM系统");
		accessHeader.setBusinessDesc2(UUIDUtils.getUUID());
		accessHeader.setVersion("0.1");
		try {
			CreateScatterRequestTrans trans = new CreateScatterRequestTrans();
			logger.info("开始进行发送散客数据到CRM系统的操作：\n" + trans.fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			deleteByIds(ids);
			logger.info("结束发送散客数据到CRM系统的操作：\n" + trans.fromMessage(request));
		} catch (Exception e) {
			// logger.info("异常类型：" + e.getExceptionType());
			// logger.info("异常编码" + e.getExceptionCode());
			String errorMessage ="";
			if(e!=null){
				errorMessage += e.toString()+",";
			}
			if(e.getCause()!=null){
				errorMessage+=e.getCause().toString()+",";
			}
			if(e.getStackTrace()!=null){
				errorMessage+= e.getStackTrace().toString();
			}
			logger.info("推送散客失败异常：" + errorMessage);
			if (errorMessage.length() > 3000) {
				errorMessage = errorMessage.substring(0, 3000);
			}
			for (FailNonfixedCustomerEntity entity : list) {
				entity.setErrorInfo("定时任务推送散客异常：" + errorMessage);
				updateFailCount(entity);
			}
		}

	}
	
	private void deleteByIds(String[] ids){
		if(ids==null||ids.length==0){
			return;
		}
		for(int i=0;i<ids.length;i++){
			deleteFailNonfixedCustomerEntity(ids[i]);
		}
	}

	public void setFailNonfixedCustomerDao(
			IFailNonfixedCustomerDao failNonfixedCustomerDao) {
		this.failNonfixedCustomerDao = failNonfixedCustomerDao;
	}

}
