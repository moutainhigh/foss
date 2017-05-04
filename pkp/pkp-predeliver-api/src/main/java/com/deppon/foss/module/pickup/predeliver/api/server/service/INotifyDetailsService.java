package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsConditonDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;

/**
 * @功能 查询营业部派送、自提通知记录;支持导出功能
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 19:26:51
 *
 */
public interface INotifyDetailsService {
	
	/**
	 * @功能 查询数量
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param notifyDetailsConditonDto
	 * @return
	 */
	 long queryNoticeDetailCount(NotifyDetailsConditonDto notifyDetailsConditonDto);
	
	/**
	 * @功能 查询通知记录详情
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param limit 
	 * @param start 
	 * @param vo
	 * @return
	 */
	 List<NotifyDetailsDto> queryNoticeDetail(NotifyDetailsConditonDto notifyDetailsConditonDto, int start, int limit);

	/**
	 * @功能 导出通知记录详情
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param limit 
	 * @param start 
	 * @param vo
	 * @return
	 */
	 InputStream exportNoticeDetail(NotifyDetailsConditonDto notifyDetailsConditonDto);
	 /**
	  * 初始化查询条件
	  *  @author 159231-foss-meiying
	  * @date 2014-3-18 下午6:31:57
	  */
	 void initNotifyDetailsConditonDto(NotifyDetailsConditonDto notifyDetailsConditonDto);
}
