package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;


/**
* @description 快递到达页面Dao
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 下午2:42:47
*/
public class ExpressArrivalDao extends iBatis3DaoImpl implements IExpressArrivalDao {
	
	private static final String NAMESPACE = "foss.load.express.arrival.";

	
	/**
	* @description 查询快递到达记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#queryExpressArrival(com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto, java.lang.String, int, int)
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:23:03
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressArrivalDisplayEntity> queryExpressArrival(
			ExpressArrivalQueryDto expressArrivalQueryDto, 
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressArrival",expressArrivalQueryDto,rowBounds);
	}


	
	/**
	* @description 查询快递到达记录count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalQueryDto(com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:49:27
	* @version V1.0
	*/
	@Override
	public Long queryExpressArrivalCount(
			ExpressArrivalQueryDto expressArrivalQueryDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryExpressArrivalCount",expressArrivalQueryDto);
	}



	
	/**
	* @description 将确认的数据保存到tfr.t_opt_express_arrival表中,status = 1
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalConfirm(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:08:31
	* @version V1.0
	*/
	@Override
	public Long expressArrivalConfirm(ExpressArrivalEntity expressArrivalEntity) {
		return (long)this.getSqlSession().insert(NAMESPACE+ "expressArrivalConfirm", expressArrivalEntity);
	}



	
	/**
	* @description 根据id查询表tfr.t_opt_express_arrival中是否有此数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalSelectById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:24:19
	* @version V1.0
	*/
	@Override
	public ExpressArrivalEntity expressArrivalSelectById(String id) {
		return (ExpressArrivalEntity)this.getSqlSession().selectOne(NAMESPACE + "expressArrivalSelectById",id);
	}



	
	/**
	* @description 将退回的数据保存到tfr.t_opt_express_arrival表中,status = 2
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalBack(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:41:23
	* @version V1.0
	*/
	@Override
	public Long expressArrivalBack(ExpressArrivalEntity expressArrivalEntity) {
		return (long)this.getSqlSession().insert(NAMESPACE+ "expressArrivalBack", expressArrivalEntity);
	}



	
	/**
	* @description 将状态从'确定' 更新到'退回'
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalFromConfirmToBack(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:46:54
	* @version V1.0
	*/
	@Override
	public Long expressArrivalFromConfirmToBack(String id) {
		return (long)this.getSqlSession().update(NAMESPACE+ "expressArrivalFromConfirmToBack", id);
		
	}



	
	/**
	* @description 给自动补码提供的方法,物理删除
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao#expressArrivalDelete(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月18日 上午9:13:33
	* @version V1.0
	*/
	@Override
	public Long expressArrivalDelete(String id, String waybillNo) {
		ExpressArrivalEntity e = new ExpressArrivalEntity();
		e.setId(id);
		e.setWaybillNo(waybillNo);
		return (long)this.getSqlSession().delete(NAMESPACE+ "expressArrivalDelete",e);
	}
	
	
	
}












