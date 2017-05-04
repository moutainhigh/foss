package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataConsistencyDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IDataConsistencyService;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;

/**
 * 
 * GUI数据一致性检查
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-dengyao,date:2013-05-05 下午2:02:34,
 * </p>
 * 
 * @author foss-dengyao
 * @date 2013-05-05 下午2:02:34,
 * @since
 * @version
 * *
 */

public class DataConsistencyService implements IDataConsistencyService {
	
	IDataConsistencyDao dataConsistencyDao;
	
	public void setDataConsistencyDao(IDataConsistencyDao dataConsistencyDao){
		this.dataConsistencyDao=dataConsistencyDao;
	}
	
	/**
	 * 服务器对应下载数据表条目查询
	 * @author foss-dengyao
	 * @date 2013-05-02 上午9:32:15
	 * @param countServiceTableDate
	 * @see
	 */
	@Override
	@Transactional
	public List<DataConsistencyVo> countQueryTableDate(List<DataConsistencyVo> tableName,String userCode){
		List<DataConsistencyVo> serviceData =new  ArrayList<DataConsistencyVo>();
		//List<String> tableNames = new  ArrayList<String>();
		serviceData = dataConsistencyDao.countQueryTableDate(tableName,userCode);
		serviceData.remove(0);
				
		for (int i=0;i<tableName.size();i++){
			if(serviceData.get(i).getVersionNo()!=null){
				Date versionDate=new Date(serviceData.get(i).getVersionNo());
				serviceData.get(i).setSyncDate(versionDate);
			}		
			tableName.get(i).setCounts(serviceData.get(i).getCounts());
			tableName.get(i).setSyncDate(serviceData.get(i).getSyncDate());
		}
		
		return tableName;
	}

}
