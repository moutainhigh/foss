
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAutoDeliverHandoverDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.tools.DeliverThreadPoolCaller;
import com.deppon.foss.util.define.FossConstants;

/**
 * 一、系统识别同时满足以下4个条件的运单，可以自动交单给调度排单：
1、	货物在库存中
2、	货物有送货日期
3、	到达部门为当前部门
4、	在自动交单的设置范围之内
【自动交单管理：参见ASP-用户需求说明书-交单管理】：
（1）	若交单管理中，操作部门的“自动交当日运单”时间已设置，则在此时间内自动读取满足货物在库存中且库存件数等于开单件数、送货日期为交单之日的、无异常未处理的运单；
（2）	若交单管理中，操作部门的“自动交第二日运单”时间已设置，则在此时间范围内，系统自动读取货物在库存中，送货日期为【交单日+1日】的、无异常未处理的运单。
（3）	若交单管理的自动交单管理“只自动交入库位的运单”字段进行了勾选设置，表示在设置的自动交单之间内【第（1）、（2）规则的时间设置】，只能交已进行库位确认的运单。
（4）	若不满足上述自动交单的条件的，不自动交单，但可人工交单。
（5）	自动交单时发现有更改单单未受理和有异常未处理，自动过滤。
二、自动交单不能交单的情形（满足其中之一即可）：
1、	若自动交单的运单有更改单未处理；
2、	有异常未处理完毕
3、	运单未补录
4、	开单为网上支付未付款的。
三、自动交单运单流向规则说明：
1、	若自动交单的部门为非驻地到达部门，交单之后，运单流向此非驻地到达部门的排单池【创建派送单（新）功能】
2、	若自动交单为驻地派送部，则交单之后运单流入对应顶级车队的排单池【创建派送单(新)功能】，供车队进行排单。
3、	若自动交单为驻地部门，但无对应的车队，交单之后，运单自动流入此驻地部门的排单池【创建派送单（新）功能】
4、	以上货物的流向与现在创建派送单功能的查询规则的数据获取方向是一致的，可以对此规则进行参考。
四、若运单的件数已经全部排单，则不自动交单（如：交单的运单在以前的创建派送单已经创建，则更新过滤）
五、自动交单后，待排单列表的数据更新最新待交单状态，运单状态变为已交单。


 * 所起应用为pkp-predeliver-web  spring 注入在pkp-predeliver-web下
 * 自动派送交单service
 * @author 159231 meiying
 * 2015-4-20  上午11:24:28
 */
public class QueryAutoDeliverHandoverService extends DeliverThreadPoolCaller 
{ 
	private IAutoDeliverHandoverDao autoDeliverHandoverDao;
	private AutoDeliverHandoverbillService autoDeliverHandoverbillService;
	public static final String DEFAULT_NULL = "N/A";
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryAutoDeliverHandoverService.class);

	/**
	 * 
	 * @author 159231 meiying
	 * 2015-5-29  上午8:57:27
	 * @return
	 */
	@Override
	public int serviceCaller() {
		LOGGER.info("自动交单查询开始" );
		List<AutoDeliverHandoverEntity> lists=autoDeliverHandoverDao.selectList();
		if(CollectionUtils.isNotEmpty(lists) && lists.size()>0){
			List<String> ids=new ArrayList<String>();
			for (AutoDeliverHandoverEntity autoDeliverHandoverEntity : lists) {
				ids.add(autoDeliverHandoverEntity.getId());
			}
			AutoPreDeliverHandoverbillDto dto =new AutoPreDeliverHandoverbillDto();
			dto.setPid(ids);
			dto.setOldJobId(DEFAULT_NULL);
			dto.setJobId(FossConstants.YES);//
			if(autoDeliverHandoverDao.updateAutoDeliverJobList(dto)==lists.size()){
				for (AutoDeliverHandoverEntity autoDeliverHandoverEntity : lists) {
					autoDeliverHandoverbillService.pushThreadsPool(autoDeliverHandoverEntity);
				}
			}else{
				throw new BusinessException("");
			}
			return lists.size();
		}else{
			return 0;
		}
		
	}
	/**
	 * 设置autoDeliverHandoverDao  
	 * @param autoDeliverHandoverDao autoDeliverHandoverDao 
	 */
	@Resource
	public void setAutoDeliverHandoverDao(
			IAutoDeliverHandoverDao autoDeliverHandoverDao) {
		this.autoDeliverHandoverDao = autoDeliverHandoverDao;
	}
	/**
	 * 设置autoDeliverHandoverbillService  
	 * @param autoDeliverHandoverbillService autoDeliverHandoverbillService 
	 */
	@Resource
	public void setAutoDeliverHandoverbillService(
			AutoDeliverHandoverbillService autoDeliverHandoverbillService) {
		this.autoDeliverHandoverbillService = autoDeliverHandoverbillService;
	}

	
}