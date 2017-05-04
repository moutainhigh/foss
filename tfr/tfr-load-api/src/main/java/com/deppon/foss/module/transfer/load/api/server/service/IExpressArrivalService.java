package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;


/**
* @description 快递到达service
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 下午2:28:07
*/
public interface IExpressArrivalService {

	
	/**
	* @description 查询快递到达记录
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午4:27:18
	*/
	List<ExpressArrivalDisplayEntity> queryExpressArrival(ExpressArrivalQueryDto expressArrivalQueryDto,int limit,int start);
	
	
	/**
	* @description 查询快递到达记录count
	* @param expressArrivalQueryDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:44:03
	*/
	Long queryExpressArrivalCount(ExpressArrivalQueryDto expressArrivalQueryDto);
	
	
	/**
	* @description 将确认的数据保存到tfr.t_opt_express_arrival表中,status = 1
	* @param expressArrivalEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:01:19
	*/
	Long expressArrivalConfirm(ExpressArrivalEntity expressArrivalEntity);
	
	
	
	/**
	* @description 根据id查询表tfr.t_opt_express_arrival中是否有此数据
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:16:20
	*/
	ExpressArrivalEntity expressArrivalSelectById(String id);
	
	
	
	/**
	* @description 从快递到达表中删除此条记录
	* @param id
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月18日 上午9:07:36
	*/
	Long expressArrivalDelete(String id,String waybillNo);
	
	/**
	 * @author 269701--lln
	 * @param currentDate 补码退回时间， waybillNo 运单号
	 * @date 2015-11-13 下午 14:51:20:23
	 * 选中运单点击退回并选择“是”按钮，触发FOSS发送一条消息至综合查询-综合信息查询-运单相关信息-跟踪记录
	 * 1、跟踪内容统一为“运单已从快递到达界面退回中转场重新补码
	 * 2、联系人为退回操作人
	 * 3、跟踪类别为“系统备注
	 * 4、跟踪/起草时间为退回操作时间，格式为年-月-日 时:分:秒
	 * 5、跟踪/起草部门为退回操作部门
	 * 6、跟踪/起草人为退回操作人
	 * 7、受理时间/受理部门/受理人/受理备注均为空
	 */
	void expressArrivalBackLog(Date currentDate,String waybillNo);
}
