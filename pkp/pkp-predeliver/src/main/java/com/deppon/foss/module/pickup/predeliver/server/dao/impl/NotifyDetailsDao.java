package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyDetailsDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsConditonDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;

public class NotifyDetailsDao extends iBatis3DaoImpl implements INotifyDetailsDao {
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyDetailsDao.";
	
	@Override
	public long queryNoticeDetailCount(
			NotifyDetailsConditonDto notifyDetailsConditonDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryNoticeDetailCount", notifyDetailsConditonDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyDetailsDto> queryNoticeDetail(
			NotifyDetailsConditonDto notifyDetailsConditonDto, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		if(limit == 0){
			return this.getSqlSession().selectList(NAMESPACE+"queryNoticeDetail", notifyDetailsConditonDto);
		}		
		return this.getSqlSession().selectList(NAMESPACE+"queryNoticeDetail", notifyDetailsConditonDto, rowBounds);
	}

}
