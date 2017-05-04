package com.deppon.foss.module.base.baseinfo.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICrmpushfossLogBackupsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICrmpushfossLogBackupsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogBackupsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *<p>Title: CrmpushfossLogBackupsService</p>
 * <p>Description: 实现存储主客户日志信息转储的service</p>
 * <p>Company: Deppon</p>
 * @author    CSS
 * @date       2015-7-24 上午08:48
 */
public class CrmpushfossLogBackupsService implements
		ICrmpushfossLogBackupsService {
	
	 /**
     * 日志.
     */
   // private static final Logger LOGGER = LoggerFactory
	   // .getLogger(CrmpushfossLogBackupsService.class);
    
    /**
     * 主客户日记信息转储接口.
     */
    private ICrmpushfossLogBackupsDao crmpushfossLogBackupsDao;

	public void setCrmpushfossLogBackupsDao(
			ICrmpushfossLogBackupsDao crmpushfossLogBackupsDao) {
		this.crmpushfossLogBackupsDao = crmpushfossLogBackupsDao;
	}

	@Override
	public Long countByCrmpushfossLogBackups(CrmpushfossLogBackupsEntity example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CrmpushfossLogBackupsEntity record) {
		if (null == record) {

		    throw new ContactAddressException("传入的参数不允许为空！");
		} else {		    
			record.setId(UUIDUtils.getUUID());

			crmpushfossLogBackupsDao.insert(record);
		} 
		return FossConstants.SUCCESS;
	}

	@Override
	public CrmpushfossLogBackupsEntity selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
