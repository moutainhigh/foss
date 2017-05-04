package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillCommonDao;
import com.deppon.foss.module.settlement.ecsitf.server.rs.ISignForService;
import com.deppon.foss.util.UUIDUtils;
@Service("signForService")
public class SignForService implements ISignForService{
	
	@Resource
	private IWaybillCommonDao waybillCommonDao;
	
	/**
	 * ECS-327090-2016-05-09
	 */
	@Override
	@Transactional
	public int updateOrInsertSignFor(WaybillSignResultEntity waybillSignResultEntity) {

		int count = 0;
		// 判断数据库中有没有这条信息
		count = waybillCommonDao.selectWaybillSignResult(waybillSignResultEntity);
		
		// 有则更新无则插入
		if (count == 1) {
			waybillCommonDao.updateWaybill(waybillSignResultEntity);
		} else if (count == 0) {	
			//传过来的对象主键不能用
			waybillSignResultEntity.setId(UUIDUtils.getUUID());
			//生效时间必填，不能为空
			if(waybillSignResultEntity.getCreateTime()==null){
				waybillSignResultEntity.setCreateTime(new Date());
			}
			waybillCommonDao.addWaybillSignResult(waybillSignResultEntity);
		}
		return count;
	}

}
