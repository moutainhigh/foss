package com.deppon.foss.module.transfer.scheduling.server.service.impl;


import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IConsultPriceDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IConsultPriceService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;


public class ConsultPriceService implements IConsultPriceService {
	//日志
	private static final Logger logger = LogManager.getLogger(ConsultPriceService.class);
	//调用IConsultPriceDao接口
	private IConsultPriceDao consultPriceDao;
	
	public void setConsultPriceDao(IConsultPriceDao consultPriceDao) {
		this.consultPriceDao = consultPriceDao;
	}

	/**
	 * 将TPS传来的信息添加到数据库
	 * @param consultPriceNo 询价编号
	 * @param quotedInfo 报价信息(报价的价格)
	 * @param needVehicleDept 请车部门
	 */
	public void addConsultPriceInfo(ConsultPriceEntity consultPriceEntity) {
		logger.info("开始添加询价信息");
		if(consultPriceEntity!=null){
			try {
				consultPriceDao.addConsultPriceInfo(consultPriceEntity);
				logger.info("开始添加询价信息结束");
			} catch (Exception e) {
				logger.info("添加询价信息失败"+e.toString());
				throw new TfrBusinessException("添加询价信息失败");
			}
			
		}else{
			throw new TfrBusinessException("传入的添加信息数据为空");
		}
	}

	/**
	 * @param consultPriceNo 询价编号
	 * @return 询价信息的一个对象
	 */
	public ConsultPriceEntity queryByConsultPriceNo(String consultPriceNo) {
		if(consultPriceNo!=null&&!consultPriceNo.equals("")){
			List<ConsultPriceEntity> consultPriceNoList=consultPriceDao.queryIfConsultPriceNoUsed(consultPriceNo);
			if(consultPriceNoList!=null&&!consultPriceNoList.isEmpty()){
				ConsultPriceEntity consultPriceEntity=new ConsultPriceEntity();
				consultPriceEntity.setNeedVehicleDept("此询价编号已使用过");
				return consultPriceEntity;
			}else{
				List<ConsultPriceEntity> list=consultPriceDao.queryByConsultPriceNo(consultPriceNo);
				if(list!=null&&!list.isEmpty()){
					return list.get(0);
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}

}
