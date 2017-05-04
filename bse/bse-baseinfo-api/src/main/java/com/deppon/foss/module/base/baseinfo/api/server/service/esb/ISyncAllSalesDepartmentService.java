package com.deppon.foss.module.base.baseinfo.api.server.service.esb;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 同步FOSS的营业部信息给周边系统
 * 
 * @author 273311
 * @date 2016-3-22 上午11:50:25
 */
public interface ISyncAllSalesDepartmentService extends IService{

		/**
		 * 同步FOSS的营业部信息给周边系统
		 * 
		 * @author 273311
		 * @date 2016-3-22 上午11:50:25
		 * @return
		 */
       void syncAllSalesDepartmentDataToAll(
				List<SaleDepartmentEntity> saleDepartmentList, String operateType);
}
