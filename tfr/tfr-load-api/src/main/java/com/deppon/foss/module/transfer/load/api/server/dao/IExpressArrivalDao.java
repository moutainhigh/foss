package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;


/**
* @description 快递到达页面Dao
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 下午2:40:57
*/
public interface IExpressArrivalDao {

	
	/**
	* @description 查询快递到达记录
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:21:57
	*/
	List<ExpressArrivalDisplayEntity> queryExpressArrival(
			ExpressArrivalQueryDto expressArrivalQueryDto,int limit, int start);
	
	
	/**
	* @description 查询快递到达记录count
	* @param expressArrivalQueryDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:49:04
	*/
	Long queryExpressArrivalCount(ExpressArrivalQueryDto expressArrivalQueryDto);
	
	
	/**
	* @description 将确认的数据保存到tfr.t_opt_express_arrival表中,status = 1
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:05:44
	*/
	Long expressArrivalConfirm(ExpressArrivalEntity expressArrivalEntity);
	
	
	/**
	* @description 将退回的数据保存到tfr.t_opt_express_arrival表中,status = 2
	* @param expressArrivalEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:40:34
	*/
	Long expressArrivalBack(ExpressArrivalEntity expressArrivalEntity);
	/**
	* @description 根据id查询表tfr.t_opt_express_arrival中是否有此数据
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:23:33
	*/
	ExpressArrivalEntity expressArrivalSelectById(String id);
	
	
	/**
	* @description 将状态从'确定' 更新到'退回'
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:45:33
	*/
	Long expressArrivalFromConfirmToBack(String id);
	
	
	/**
	* @description 给自动补码提供的方法,物理删除
	* @param id
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月18日 上午9:11:47
	*/
	Long expressArrivalDelete(String id,String waybillNo);
}
