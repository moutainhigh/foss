package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachWaybillSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo;

public class BCMachSortScanDao  extends iBatis3DaoImpl implements IBCMachSortScanDao {
 public static final String NAMESAPCE="tfr.unload.bCMachSortScan.";
 
 
	
	/**
	* @description 查询称重量方数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#queryBCMachSortScan(com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo)
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午3:03:48
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<BCMachSortScanEntity> queryBCMachSortScan(BCMachSortScanVo vo,int i, int j) {
		//分页
		RowBounds rowBounds = new RowBounds(j, i);
		return this.getSqlSession().selectList(NAMESAPCE+"queryBCMachSortScan", vo,rowBounds);
	}



	
	/**
	* @description 查询总条数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#queryBCMachSortScanCount(com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo)
	* @author 105869-foss-heyongdong
	* @update 2015年5月8日 下午4:51:14
	* @version V1.0
	*/
	@Override
	public Long queryBCMachSortScanCount(BCMachSortScanVo vo) {
		return  (Long) this.getSqlSession().selectOne(NAMESAPCE+"queryBCMachSortScanCount",vo);
	}



	
	/**
	* @description	保存扫描信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#saveScanMsg(com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月8日 下午4:51:26
	* @version V1.0
	*/
	@Override
	public int saveScanMsg(BCMachSortScanEntity entity) {
		return this.getSqlSession().insert(NAMESAPCE+"saveScanMsg", entity);
	}




	
	/**
	* @description 查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#queryBCMachSortScanBySeriaNo(java.lang.String, java.lang.String)
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:47:17
	* @version V1.0
	*/
	@Override
	@SuppressWarnings("unchecked")
	public BCMachSortScanEntity queryBCMachSortScanBySeriaNo(String waybillNo,
			String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo",waybillNo);
		map.put("serialNo",serialNo);
		
		List<BCMachSortScanEntity> list = this.getSqlSession().selectList(NAMESAPCE+"queryBCMachSortScanBySeriaNo",map);
		if(list==null||list.size()<=0){
			return null;	
		}else{
			return list.get(0);
		}
		 
	}




	
	/**
	* @description 删除
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#deleteScanMsg(com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:47:28
	* @version V1.0
	*/
	@Override
	public long deleteScanMsg(BCMachSortScanEntity sortEntity) {
		
		return this.getSqlSession().delete(NAMESAPCE+"deleteScanMsg", sortEntity);
	}




	
	/**
	* @description 保存上计泡机日志
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#saveScanMsgLog(com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午7:46:20
	* @version V1.0
	*/
	@Override
	public int saveScanMsgLog(BCMachSortScanEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESAPCE+"saveScanMsgLog", entity);
	}




	
	/**
	* @description  更新或者插入 上计泡机运单信息综合表
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao#updateOrInsertWaybillMsg(java.lang.String)
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午7:46:23
	* @version V1.0
	*/
	@Override
	public int updateOrInsertWaybillMsg(String waybillNo) {
		return this.getSqlSession().update(NAMESAPCE+"updateOrInsertWaybillMsg", waybillNo);
	}



	/**
	 * 更具运单号在计泡机总重总体积表里面查询一条数据
	 * @param waybillNo
	 * @return 计泡机的运单实体
	 * @author 268084
	 */
	@Override
	public BCMachWaybillSortScanEntity queryOneBillFromBCMachine(
			String waybillNo) {
		
		return (BCMachWaybillSortScanEntity)this.getSqlSession().selectOne(NAMESAPCE+"queryFromBCMachineByWaybill", waybillNo);
	}

}
