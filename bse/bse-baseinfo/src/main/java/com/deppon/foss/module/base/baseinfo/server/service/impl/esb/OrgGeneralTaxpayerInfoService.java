package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.crm.FailTaxpayerInfo;
import com.deppon.esb.inteface.domain.crm.GeneralTaxpayerInfo;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGeneralTaxpayerInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgGeneralTaxpayerInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgGeneralTaxpayerException;

/**
 * 
 * 同步一般纳税人信息service实现
 * @author 308861 
 * @date 2016-2-28 下午2:39:10
 * @since
 * @version
 */
public class OrgGeneralTaxpayerInfoService implements IOrgGeneralTaxpayerInfoService{
	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgGeneralTaxpayerInfoService.class);
	
	private IOrgGeneralTaxpayerInfoDao orgGeneralTaxpayerInfoDao;
	
	
	public void setOrgGeneralTaxpayerInfoDao(
			IOrgGeneralTaxpayerInfoDao orgGeneralTaxpayerInfoDao) {
		this.orgGeneralTaxpayerInfoDao = orgGeneralTaxpayerInfoDao;
	}

	@Override
	@Transactional
	public FailTaxpayerInfo operation(GeneralTaxpayerInfo taxpayerInfo) {
		//声明FailTaxpayerInfo对象
		FailTaxpayerInfo generalTaxpayerResponse=new FailTaxpayerInfo();
		//处理数据返回结果
		try {
			//传值对象为null 或 crmId为null 或 operation为null
			if(taxpayerInfo == null || 
					StringUtil.isEmpty(taxpayerInfo.getCrmId()) || 
					StringUtil.isEmpty(taxpayerInfo.getOperation())){
				throw new OrgGeneralTaxpayerException("一般纳税人参数不能为空",
						"唯一标识列crmId不能为空",
						"操作类型不能为空");
			}
			//将esbInfo转为FOSS对象
			GeneralTaxpayerInfoEntity entity=transEsbToFOSS(taxpayerInfo);
			if(entity != null){
				//根据 【crmId】 与【 active】 查询一般纳税人信息
				GeneralTaxpayerInfoEntity entityResult=orgGeneralTaxpayerInfoDao.
						queryTaxpayerInfoById(taxpayerInfo.getCrmId());
				//根据操作类型  1新增  2修改  3删除（作废）
				if(entity.getOperation().equals("1")){//新增
					//根据crmId查询没有数据直接插入
					if(entityResult == null){
						orgGeneralTaxpayerInfoDao.addGeneralTaxpayerInfo(entity);
					}else{
						//先作废再插入
						GeneralTaxpayerInfoEntity result=orgGeneralTaxpayerInfoDao.deleteTaxpayerInfo(entity);
						if(result == null){
						    String msg = "作废失败";
						    LOGGER.error(msg);
						}else{
							orgGeneralTaxpayerInfoDao.addGeneralTaxpayerInfo(entity);
						}
					}
				}else if(entity.getOperation().equals("2")){//修改
					if(entityResult != null && StringUtil.isNotEmpty(entity.getCrmId())){
						orgGeneralTaxpayerInfoDao.updateTaxpayerInfo(entity);
					}
				}else if(entity.getOperation().equals("3")){//删除
					if(entityResult != null && StringUtil.isNotEmpty(entity.getCrmId())){
						orgGeneralTaxpayerInfoDao.deleteTaxpayerInfo(entity);
					}
				}else{
					generalTaxpayerResponse.setReason("操作类型标识有误!");
					generalTaxpayerResponse.setId(taxpayerInfo.getCrmId());
				}
				generalTaxpayerResponse.setReason("成功!");
				generalTaxpayerResponse.setId(taxpayerInfo.getCrmId());
			}
		}catch (OrgGeneralTaxpayerException e) {
			LOGGER.error(e.getMessage());
			generalTaxpayerResponse.setReason(e.getMessage());
			generalTaxpayerResponse.setId(taxpayerInfo.getCrmId());
		}
		return generalTaxpayerResponse;
	}
	
	/**
	 * esb转FOSS对象
	 */
	public GeneralTaxpayerInfoEntity transEsbToFOSS(GeneralTaxpayerInfo esbInfo){
		if(esbInfo == null){
		    return null;
		}
		GeneralTaxpayerInfoEntity fossEntity=new GeneralTaxpayerInfoEntity();
		//唯一标识
		fossEntity.setCrmId(esbInfo.getCrmId());
		//税务登记号
		fossEntity.setTaxId(esbInfo.getTaxId());
		//发票抬头
		fossEntity.setBillTitle(esbInfo.getBillTitle());
		//开户行
		fossEntity.setBankName(esbInfo.getBankName());
		//银行账号
		fossEntity.setBankNumber(esbInfo.getBankNumber());
		//是否一般纳税人
		fossEntity.setIsTaxpayer(esbInfo.getIsTaxpayer());
		//注册电话
		fossEntity.setRegTel(esbInfo.getRegTel());
		//注册地址
		fossEntity.setRegAddress(esbInfo.getRegAddress());
		//创建部门
		fossEntity.setCreateDepartment(esbInfo.getCreateDepartment());
		//操作类型
		fossEntity.setOperation(esbInfo.getOperation());
		return fossEntity;
	}
}
