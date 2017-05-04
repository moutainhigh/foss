package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.ptp.ContractBasisInfo;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncContractBasisInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncContractBasisInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContractBasisInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SyncContractBasisInfoException;

/**
 * 
 * 同步合同基础信息service实现
 * @author 308861 
 * @date 2016-8-12 下午3:51:28
 */
public class SyncContractBasisInfoService implements ISyncContractBasisInfoService{
	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncContractBasisInfoService.class);
	
	
	private ISyncContractBasisInfoDao  syncContractBasisInfoDao;
	//setter方法
	public void setSyncContractBasisInfoDao(
			ISyncContractBasisInfoDao syncContractBasisInfoDao) {
		this.syncContractBasisInfoDao = syncContractBasisInfoDao;
	}
	
	/**
	 * 
	 * 同步合同信息接口 
	 * @author 308861 
	 * @date 2016-8-15 上午11:00:11
	 * @param info 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncContractBasisInfoService#operation(com.deppon.esb.inteface.domain.ptp.ContractBasisInfo)
	 */
	@Override
	@Transactional
	public void operation(ContractBasisInfo info){
		//处理数据返回结果
		try {
			//传值对象为null 或 Id为null 或operatorSign为空
			if(info == null ||
					StringUtil.isEmpty(info.getId()) ||
					StringUtil.isEmpty(info.getOperatorSign())){
				throw new SyncContractBasisInfoException("合同基础信息参数不能为空",
						"合同编号不能为空",
						"操作类型不能为空");
			}
			//将esbinfo转为foss对象
			ContractBasisInfoEntity entity=transEsbToFOSS(info);
			if(entity != null){
				//根据【ptpId】与【active】查询合同基础信息
				ContractBasisInfoEntity entityResult=syncContractBasisInfoDao.
						queryContractBasisInfById(info.getId());
				//根据ptp唯一标识id查询是否存在
				if(entityResult ==null){//不存在直接新增
					ContractBasisInfoEntity result=syncContractBasisInfoDao
							.addContractBasisInfo(entity);
					if(result == null){
						String msg="新增失败";
						LOGGER.error(msg);
					}
				}else{//存在直接修改
					ContractBasisInfoEntity result=syncContractBasisInfoDao.updateContractBasisInfo(entity);
					if(result == null){
						String msg="作废失败";
						LOGGER.error(msg);
					}
				}
			}
		} catch (SyncContractBasisInfoException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * esb转FOSS对象
	 */
	public ContractBasisInfoEntity transEsbToFOSS(ContractBasisInfo esbInfo){
		if(esbInfo == null){
		    return null;
		}
		ContractBasisInfoEntity fossEntity=new ContractBasisInfoEntity();
		//合同编号
		fossEntity.setPtpId(esbInfo.getId());
		//合伙人营业部编码
		fossEntity.setPointNumber(esbInfo.getPointNumber());
		//合伙人营业部名称
		fossEntity.setPointName(esbInfo.getPointName());
		//对接营业部编码
		fossEntity.setDockingDepNumber(esbInfo.getDockingDepNumber());
		//对接营业部名称
		fossEntity.setDockingDepName(esbInfo.getDockingDepName());
		//创建时间
		fossEntity.setCreateDate(esbInfo.getCreateTime());
		//修改时间
		fossEntity.setModifyDate(esbInfo.getModifyTime());
		//合同生效时间
		fossEntity.setContractStateTime(esbInfo.getContractStateTime());
		//合同终止时间
		fossEntity.setContractEndTime(esbInfo.getContractEndTime());
		//是否有效
		fossEntity.setActive(esbInfo.getActive());
		//创建人
		fossEntity.setCreateUser(esbInfo.getCreateUserCode());
		//更新人
		fossEntity.setModifyUser(esbInfo.getModifyUserCode());
		//操作类型
		fossEntity.setOperatorSign(esbInfo.getOperatorSign());
		return fossEntity;
	}
}
