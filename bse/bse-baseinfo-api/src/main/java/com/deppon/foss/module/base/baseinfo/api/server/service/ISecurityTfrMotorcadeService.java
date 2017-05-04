package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;

/**
 * TODO(SecurityTfrMotorcade的Service接口)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:34:09
 * @since
 * @version
 */
public interface ISecurityTfrMotorcadeService extends IService {
	
	/**
	 * 
	 * @Description:根据保安组编码查询信息
	 * @author:187862-dujunhui
	 */
	List<SecurityTfrMotorcadeEntity> querySecurityTfrMotorcadeListBySecurityCode(SecurityTfrMotorcadeEntity entity,int limit,int start);
	
	/**
	 * 
	 * @Description:统计同一保安组信息条数
	 * @author:187862-dujunhui
	 */
	Long querySecurityCodeCount(SecurityTfrMotorcadeEntity entity);
	
	/**
	 * 
	 * @Description:统计所有保安组信息条数
	 * @author:187862-dujunhui
	 */
	Long queryRecordCount(SecurityTfrMotorcadeEntity entity);
	
	/**
	 * 
	 * @Description:添加保安组信息
	 * @author:187862-dujunhui
	 */
	int addSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity);
	
	/**
	 * 
	 * @Description:删除保安组信息
	 * @author:187862-dujunhui
	 */
	int deleteSecurityTfrMotorcade(String[] codeList);
	
	/**
	 * 
	 * @Description:修改保安组信息
	 * @author:187862-dujunhui
	 */
	int updateSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity);

}
