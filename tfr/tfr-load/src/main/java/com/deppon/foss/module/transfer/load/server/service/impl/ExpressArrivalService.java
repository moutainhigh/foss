package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.service.IBseWaybillQueryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressArrivalDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;


/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年6月23日 上午10:45:14
*/
public class ExpressArrivalService implements IExpressArrivalService{

	private IExpressArrivalDao expressArrivalDao;
	//跟踪记录插入记录
	private IBseWaybillQueryService bseWaybillQueryService;
	
	
	/**
	* @description 设置快递到达Dao
	* @param expressArrivalDao
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:52:08
	*/
	public void setExpressArrivalDao(IExpressArrivalDao expressArrivalDao) {
		this.expressArrivalDao = expressArrivalDao;
	}
	
	/**
	 * @description 调用综合接口 往综合信息中的跟踪记录里面插入记录
	 * @author 269701--lln
	 * @date 2015-11-17
	 * @param bseWaybillQueryService
	 */
	public void setBseWaybillQueryService(
			IBseWaybillQueryService bseWaybillQueryService) {
		this.bseWaybillQueryService = bseWaybillQueryService;
	}

	/**
	* @description 查询快递到达记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService#queryExpressArrival(com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto, java.lang.String, int, int)
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午4:29:37
	* @version V1.0
	*/
	@Override
	public List<ExpressArrivalDisplayEntity> queryExpressArrival(
			ExpressArrivalQueryDto expressArrivalQueryDto, int limit, int start) {
		List<ExpressArrivalDisplayEntity> list = expressArrivalDao.queryExpressArrival(expressArrivalQueryDto,limit,start);
		return list;
	}
	
	/**
	* @description 查询快递到达count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService#queryExpressArrivalCount(com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:47:44
	* @version V1.0
	*/
	@Override
	public Long queryExpressArrivalCount(
			ExpressArrivalQueryDto expressArrivalQueryDto) {
		long count = expressArrivalDao.queryExpressArrivalCount(expressArrivalQueryDto);
		return count;
	}
	
	/**
	* @description 将确认的数据保存到tfr.t_opt_express_arrival表中,status = 1
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService#expressArrivalConfirm(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:02:44
	* @version V1.0
	*/
	@Override
	public Long expressArrivalConfirm(ExpressArrivalEntity expressArrivalEntity) {
		return expressArrivalDao.expressArrivalConfirm(expressArrivalEntity);
	}
	
	
	/**
	* @description 根据id查询表tfr.t_opt_express_arrival中是否有此数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService#expressArrivalSelectById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:21:45
	* @version V1.0
	*/
	@Override
	public ExpressArrivalEntity expressArrivalSelectById(String id) {
		return expressArrivalDao.expressArrivalSelectById(id);
	}
	
	
	/**
	* @description 从快递到达表中删除此条记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService#expressArrivalDelete(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月18日 上午9:10:48
	* @version V1.0
	*/
	@Override
	public Long expressArrivalDelete(String id, String waybillNo) {
		return expressArrivalDao.expressArrivalDelete(id,waybillNo);
	}
	

	/**
	 * @author 269701--lln
	 * @date 2015-11-13 下午 14:51:20:23
	 * 选中运单点击退回并选择“是”按钮，触发FOSS发送一条消息至综合查询-综合信息查询-运单相关信息-跟踪记录
	 * 1、跟踪内容统一为“运单已从快递到达界面退回中转场重新补码”
	 * 2、联系人为退回操作人
	 * 3、跟踪类别为“系统备注”
	 * 4、跟踪/起草时间为退回操作时间，格式为年-月-日 时:分:秒
	 * 5、跟踪/起草部门为退回操作部门
	 * 6、跟踪/起草人为退回操作人
	 * 7、受理时间/受理部门/受理人/受理备注均为空
	 */
	@Override
	public void expressArrivalBackLog(Date currentDate,String waybillNo){
		TrackRecordEntity entity = new TrackRecordEntity();
		//当前登录人code
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//当前登录人name
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//跟踪/起草人为退回操作人
		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		//跟踪/起草时间为退回操作时间，格式为年-月-日 时:分:秒
		entity.setCreateDate(currentDate);
		//联系人为退回操作人
		entity.setContacts(userCode);
		//跟踪/起草部门为退回操作部门
		entity.setCreateOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
		entity.setCreateOrgName(FossUserContext.getCurrentInfo().getCurrentDeptName());
		//跟踪类别
		entity.setTraceCategories("系统备注");
		//跟踪内容
		entity.setTrackContent("运单已从快递到达界面退回中转场重新补码");
		//运单号
		entity.setWaybillNo(waybillNo);
		
		//调用综合接口，往综合信息查询--跟踪记录中插入跟踪记录
		 int returnCount=bseWaybillQueryService.addTrackRecord(entity);
		 if(returnCount==0){
		  //跟踪记录--插入失败
		  throw new TfrBusinessException("运单补码退回, 跟踪记录插入失败！");
	  }
		
	}
}
