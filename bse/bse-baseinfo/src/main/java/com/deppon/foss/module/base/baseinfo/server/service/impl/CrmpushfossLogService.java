package com.deppon.foss.module.base.baseinfo.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICrmpushfossLogDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICrmpushfossLogService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *<p>Title: CrmpushfossLogService</p>
 * <p>Description: 实现存储主客户日志信息的service</p>
 * <p>Company: Deppon</p>
 * @author    CSS
 * @date       2015-7-24 上午08:48
 */
public class CrmpushfossLogService implements ICrmpushfossLogService {
	
	 /**
     * 日志.
     */
    //private static final Logger LOGGER = LoggerFactory
	   // .getLogger(CrmpushfossLogService.class);
    
    /**
     * 主客户日记信息转储接口.
     */
    private ICrmpushfossLogDao crmpushfossLogDao;   


	public void setCrmpushfossLogDao(ICrmpushfossLogDao crmpushfossLogDao) {
		this.crmpushfossLogDao = crmpushfossLogDao;
	}

	@Override
	public Long countByCrmpushfossLog(CrmpushfossLogEntity example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CrmpushfossLogEntity record) {
		if (null == record) {

		    throw new ContactAddressException("传入的参数不允许为空！");
		} else {		    
			record.setId(UUIDUtils.getUUID());

			crmpushfossLogDao.insert(record);
		} 
		return FossConstants.SUCCESS;
	}

	@Override
	public CrmpushfossLogEntity selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
