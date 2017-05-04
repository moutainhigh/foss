/**   
* @Title: IDeliverGoodsAreaQueryService.java 
* @Package com.deppon.foss.module.transfer.platform.api.server.service 
* @Description: 
* @author shiwei shiwei@outlook.com
* @date 2014年3月3日 上午9:47:26 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DeliverGoodsAreaQueryVo;

/** 
 * @ClassName: IDeliverGoodsAreaQueryService 
 * @Description: 派送情况查询service接口
 * @author shiwei shiwei@outlook.com
 * @date 2014年3月3日 上午9:47:26 
 *  
 */
public interface IDeliverGoodsAreaQueryService extends IService {
	
	/**
	* @Title: queryOutfieldInfo 
	* @Description: 查询外场信息，查询不到则视为统计部门
	* @author shiwei shiwei@outlook.com
	* @date 2014年3月3日 上午9:49:35 
	* @param @return    设定文件 
	* @return String[]    返回类型 
	* @throws
	 */
	String[] queryOutfieldInfo();
	
	
	/**
	* @description 日派送率分页查询
	* @param sendRateEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:38:19
	*/
	List<SendRateEntity> querySendRateList(SendRateEntity sendRateEntity,int start,int limit);
	
	
	/**
	* @description 日派送率分页查询 总记录数
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:38:22
	*/
	long querySendRateListCount(SendRateEntity sendRateEntity);
	
	
	/**
	* @description 日派送率导出结果
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:40:06
	*/
	InputStream sendRateDayExport(SendRateEntity sendRateEntity);
	
	/**
	* @description 退单率查询分页
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:08:38
	*/
	List<ReturnRateEntity> queryReturnRateList(ReturnRateEntity returnRateEntity,int start,int limit);
	
	/**
	* @description 退单率查询分页总记录数
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:08:42
	*/
	long queryReturnRateListCount(ReturnRateEntity returnRateEntity);
	
	
	/**
	* @description 导出日退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:13:59
	*/
	InputStream returnRateDayExport(ReturnRateEntity returnRateEntity);
	
	
	/**
	* @description 拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:08:45
	*/
	List<PullbackRateEntity> queryPullbackRateList(PullbackRateEntity pullbackRateEntity,int start,int limit);
	
	/**
	* @description 拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:08:47
	*/
	long queryPullbackRateListCount(PullbackRateEntity pullbackRateEntity);
	
	
	/**
	* @description 导出日拉回率
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:54:02
	*/
	InputStream pullbackRateDayExport(PullbackRateEntity pullbackRateEntity);
	
	
	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	
	List<PullbackRateEntity> queryPullbackRateLogList(PullbackRateEntity pullbackRateEntity,int start,int limit);
	
	/**
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	long queryPullbackRateLogListCount(PullbackRateEntity pullbackRateEntity);
	
	
	/**
	* @description 导出累计拉回率
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	InputStream pullbackRateLogExport(PullbackRateEntity pullbackRateEntity);
	
	
	/**
	* @description 累计派送率查询
	* @param sendRateEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:32:06
	*/
	List<SendRateEntity> querySendRateLogList(SendRateEntity sendRateEntity,int start,int limit);
	
	/**
	* @description 累计派送率查询的总和
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:32:09
	*/
	long querySendRateLogListCount(SendRateEntity sendRateEntity);
	
	
	/**
	* @description 累计派送率导出
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:33:24
	*/
	InputStream sendRateLogExport(SendRateEntity sendRateEntity);
	
	
	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	List<ReturnRateEntity> queryReturnRateLogList(ReturnRateEntity returnRateEntity,int start,int limit);
	
	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	long queryReturnRateLogListCount(ReturnRateEntity returnRateEntity);
	
	
	/**
	* @description 累计退单率导出
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	InputStream returnRateLogExport(ReturnRateEntity returnRateEntity);

	
	/**
	* 通过员工编码查询角色
	* @author 218427-hongwenyong
	* @date 2014-03-30 17:00
	*/
	public List<UserOrgRoleEntity> queryOrgRoleByCode(String userCode);
    
	/**
	* @description 查询票均时长
	* @param waybillAvgTimeEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218427-foss-hongwy
	* @update 2015年3月25日 上午09:53:29
	*/
	List<WaybillAvgTimeEntity> queryWaybillAvgTimeEntityList(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo, int start, int limit);
	
	/**
	* @description 查询票均时长分页记录
	* @param waybillAvgTimeEntity
	* @return
	* @version 1.0
	* @author 218427-foss-hongwy
	* @update 2015年3月25日 上午09:53:29
	*/
    long queryWaybillAvgTimeEntityListCount(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo);
    

}
