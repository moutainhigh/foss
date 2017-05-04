package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutgoingBigCustomersDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutgoingBigCustomersService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;

/**
 * 外发大客户Service
 * 
 * @author 310854
 * @date 2016-2-25 下午5:15:53
 */
public class OutgoingBigCustomersService implements
		IOutgoingBigCustomersService {

	// 日志
	private Logger LOGGER = LoggerFactory
			.getLogger(OutgoingBigCustomersService.class);

	private IOutgoingBigCustomersDao outgoingBigCustomersDao;

	public IOutgoingBigCustomersDao getOutgoingBigCustomersDao() {
		return outgoingBigCustomersDao;
	}

	public void setOutgoingBigCustomersDao(
			IOutgoingBigCustomersDao outgoingBigCustomersDao) {
		this.outgoingBigCustomersDao = outgoingBigCustomersDao;
	}

	@Override
	public void addOutgoingBigCustomers(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		try {
			if (null == entity) {
				throw new BusinessException("服务器异常");
			} else if (StringUtil.isBlank(entity.getOutgoingBigCustomersCode())) {
				throw new BusinessException("客户编码不能为空！ ");
			}
			List<OutgoingBigCustomersEntity> customers = outgoingBigCustomersDao
					.queryOutgoingBigCustomersEntityByCode(entity
							.getOutgoingBigCustomersCode());
			if (null != customers && 0 < customers.size()) {
				throw new BusinessException("此外发大客户已经存在");
			}
			outgoingBigCustomersDao.addOutgoingBigCustomers(entity);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void updateOutgoingBigCustomers(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		try {
			outgoingBigCustomersDao.updateOutgoingBigCustomers(entity);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public List<OutgoingBigCustomersEntity> queryOutgoingBigCustomersEntitys(
			OutgoingBigCustomersEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		try {
			// 设置分页参数
			RowBounds rb = new RowBounds(start, limit);
			return outgoingBigCustomersDao.queryOutgoingBigCustomersEntitys(
					entity, rb);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public long getCountByCondition(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		try {
			return outgoingBigCustomersDao.getCountByCondition(entity);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
			return 0;
		}

	}

	@Override
	public boolean bigCustomersIsOutgoing(String code) {
		// TODO Auto-generated method stub
		boolean returnBo = false;
		try {
			List<OutgoingBigCustomersEntity> customers = outgoingBigCustomersDao
					.queryOutgoingBigCustomersEntityByCode(code);
			if (null != customers && 0 < customers.size()) {
				returnBo = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
		}

		return returnBo;
	}

}
