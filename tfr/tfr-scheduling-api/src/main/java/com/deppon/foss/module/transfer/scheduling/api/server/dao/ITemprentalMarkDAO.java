package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.SmallTicketEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TemprentalMarkSmticksEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcRequest;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShortRentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortRentalMarkVo;

/**
 *临时约车租车标记接口 dao
 * @author zenghaibin
 * @date 2014-06-11
 **/
public interface ITemprentalMarkDAO {
	
	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryRentalMarkEntityListByDate(RentalMarkDto rentalMarkDto,int limit,int start);//按单号查询
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryRentalMarkEntityListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start);//按日期查询
	/**
	 *根据日期查询数据数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	public Long queryRentalMarkEntityCountByDate(RentalMarkDto rentalMarkDto);
	/**
	 *根据单号查询数据数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	public Long queryRentalMarkEntityCountByBillNo(RentalMarkDto rentalMarkDto);
	
	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryStowageBillListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start);//按单号查询
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryStowageBillListByDate(RentalMarkDto rentalMarkDto,int limit,int start);//按日期查询
	
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryStowageBillListByDate(RentalMarkDto rentalMarkDto);//按日期查询
	
	/**
	 *根据单号查询配载单数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	public Long queryStowageBillCountByBillNo(RentalMarkDto rentalMarkDto);
	
	/**
	 *根据单号查询配载单数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	public Long queryStowageBillCountByDate(RentalMarkDto rentalMarkDto);
	
	
	/**
	 *查询约车编号
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param inviteVehicleNo 约车编号
	 * @return RentalMarkVo 页面需要的dto数据
	 **/
	public RentalMarkVo  queryInviteVehicleNo(String inviteVehicleNo);
	
	/**
	 *查询小票单号是否可见
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询所需参数 小票单号
	 * @return Long
	 **/
	public Long smallTicketValidate(RentalMarkDto rentalMarkDto);
	
	/**
	 *检查小票单号的合法性 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 小票单号
	 * @return List<SmallTicketEntity> 小票单号list
	 **/
	public List<SmallTicketEntity> querysmallTicketNum(RentalMarkDto rentalMarkDto);
	
	/**
	 *查询运单所需的正确小票号 
	 *@author zenghaibin
	 * @date 2014-10-112
	 * @param rentalMarkDto 小票单号
	 * @return List<SmallTicketEntity> 小票单号list
	**/
	public List<SmallTicketEntity> querySmallTicketForWayBill(HashMap<String,String> hp);
		
	
	
	/**
	 *查询交接单下的运单号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param handoverBillNoList 交接单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	public List<String> queryWayBillNoForHandoverBillNo(List<String> handoverBillNoList);
	
	/**
	 *查询派送单下的运单号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param deliverBillNoList 派送单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	public List<String> queryWayBillNoForDeliverBillNo(List<String> deliverBillNoList);
	
	/**
	 *查询配载单有多少运单 
	 *@author zenghaibin
	 * @date 2014-10-13
	 * @param stowageNolist 派送单号的list
	 * @return Long
	 ***/
	Long queryWayBillCountForStowageNo(List<String> stowageNolist);
	
	/**
	 *查询派送单有多少运单 
	 *@author zenghaibin
	 * @date 2014-10-13
	 * @param stowageNolist 派送单号的list
	 * @return Long
	 **/
	public Long queryWayBillForDeliverBill(List<String> deliverBillNoList);
	/**
	 *查租车编号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalUse 租车用途
	 * @param useDate 用车日期
	 * @return queryTempRentalMarkNO 租车编号
	 */
	public String queryTempRentalMarkNO(String rentalUse,Date useDate);
	
	/**
	 * 批量添加租车标记数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTempRentalMarkEntityList(List<TempRentalMarkEntity> tempRentalMarkEntityList);
	
	/**
	 * 批量添加租车标记明细数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTempRentalMarkDetailEntityList(List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList);

	/**
	 *查询租车标记明细表里是否有list里对应交接单或派送单单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 *@param handoverBillNoList 单号list
	 *@return long
	 **/
	public Long queryHandOverBillRepeatMark(List<String> handoverBillNoList);
	
	/**
	 *查询租车标记明细表里是否有list里对应运单单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 *@param handoverBillNoList 单号list
	 *@return long
	 **/
	public Long queryWayBillRepeatMark(RentalMarkDto rentalMarkDto);
	
	
	/**
	 * 批量添加租车标记小票单号数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTemprentalMarkSmticksEntityList(List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList);

	/**
	 *检查小票单号是否已经被标记过
	 *@author zenghaibin
	 *@param  list 小票单号List
	 *@date 2014-06-17 16:04
	 ***/
	public List<String> querySmallTicketRe(List<String> smallTicketList);
	
	/**
	 *检查运单开单上门接货且开单输入的工号是否为司机工号
	 *@author zenghaibin
	 *@param  String wayBillNo 运单号
	 *@date 2014-06-19 15:04
	 ***/
	public Long queryDriverNoOperateNo(String wayBillNo);
	/**
	 *更新租车标记表TFR.T_OPT_TEMPRENTALMARK 预提状态,预提工作流号,预提工作流处理结果
	 *@author zenghaibin
	 *@param  rentalMarkDtoList 批量跟新
	 *@date 2014-07-11 16:04
	 ***/
	public void updateTemprentalMarkAccrued(RentalMarkDto dto);
	
	/**
	 * 作废租车标记接口
	 * @author zenghaibin
	 * @date 2014-07-14
	 * @param hsp 修改人信息和租车编号
	 ***/
	public void invalidRentalMark(Map<String,Object> hsp);
	/**
	 *查询租车记录重复项
	 *@author zenghaibin
	 *@date 2014-07-14
	 *@param tempRentalMarkNo 租车编号
	 ***/
	public List<TempRentalMarkDetailEntity> queryRentalMarkDuplicates(String tempRentalMarkNo)throws Exception;
	
	/**
	 *查询租车记录重复项
	 *@author zenghaibin
	 *@date 2014-07-29
	 *@param inviteVehicleNo 约车编号
	 *@return String 租车编号
	 ***/
	public String valiteInviteVehicleNo(String inviteVehicleNo);
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryRentalMarkEntityListByBillNo(RentalMarkDto rentalMarkDto);
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryStowageBillListByBillNo(RentalMarkDto rentalMarkDto);
	
	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	public List<RentalMarkEntity> queryRentalMarkEntityListByDate(RentalMarkDto rentalMarkDto);
	 	/**
		 * 通过运单号查询 租车运单总条数--已标记和未标记
		 * @author 269701--lln
		 * @date 2016-04-15
		 * @param tempRentalQueryDto
		 * @return long
		 */
	 Long countTempWayBillByBillNos (Map<Object, Object> args);
	 /**
		 * 登陆部门为车队
		 * 通过运单号分页查询临时租车运单数据--标记和未标记
		 * @author 269701--lln
		 * @date 2016-04-15
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByBill(Map<Object, Object> args, int start, int limit);
	 /**
		 * 登陆部门为营业部/登陆部门为驻地营业部
		 * 通过运单号分页查询临时租车运单数据--已标记和未标记的数据
		 * @author 269701--lln
		 * @date 2016-04-15
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> querySaleDepartTempRentalWayBillByBill(Map<Object, Object> args, int start, int limit) ;
	 	/**
		 * 登陆部门为营业部或驻地营业部
		 * 通过时间段分页查询临时租车派送单数据
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit);
	 	/**
		 * 登陆部门为车队
		 * 通过时间段分页查询临时租车派送单数据
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit);
	 	/**
		 * 登陆部门为车队
		 * 通过派送单号分页查询临时租车派送单数据
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit);

	 	/**
		 * 通过单号分页查询临时租车派送单数据
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit) ;
	 /**
		 * 通过单号查临时租车派送单总条数
		 * @param tempRentalQueryDto
		 * @return
		 */
	 Long countTempRentalDeliverBillByBillNos (Map<Object, Object> args);
	 
	 	/**
		 * 通过时间段查临时租车派送单总条数
		 * @param tempRentalQueryDto
		 */
	 Long countTempRentalDeliverBillByDate (Map<Object, Object> args) ;
		
	 	/**
		 * 通过单号查临时租车快递运单总条数
		 * @param tempRentalQueryDto
		 */
	 Long countExpressByWayBill(Map<Object, Object> args);	
	 	/**
		 * 通过快递单号分页查询临时租车快递单号数据
		 * @param tempRentalQueryDto
		 */
	 List<RentalMarkEntity> queryExpressBillByBill(Map<Object, Object> args,int start, int limit);
	 
		/**
		 * 通过时间段查询临时租车已标记运单总条数
		 * @author 269701--lln
		 * @date 2016-04-14
		 * @param tempRentalQueryDto
		 * @return long
		 */
	 Long selectMarkedWayBillByDateCount(Map<Object, Object> args);
	 	/**
		 * 登陆部门为营业部/车队
		 * 通过时间段分页查询临时已标记租车运单数据
		 * @author 269701--lln
		 * @date 2016-04-14
		 * @param tempRentalQueryDto
		 * @param args
		 * @return
		 */
	 List<RentalMarkEntity> queryMarkedWayBillByDate(Map<Object,Object> args, int start, int limit);
	 
	 /**
		 * 通过运单号查询 对应的运单信息
		 * @author 269701--lln
		 * @date 2016-04-14
		 * @param String
		 * @return RentalMarkEntity
		 */
	 RentalMarkEntity queryMarkedWayBillInfo(String wayBillNo,String createOrgCode);
	 /**
		 * 登陆部门为营业部/登陆部门为驻地营业部
		 * 通过时间段分页查询临时租车运单数据
		 * @author 269701--lln
		 * @date 2016-03-22
		 * @param tempRentalQueryDto
		 * @param args
		 * @return
		 */
	  List<RentalMarkEntity> querySaleDepartTempRentalWayBillByDate(
				Map<Object, Object> args, int start, int limit) ;
	  /**
		 * 登陆部门为车队
		 * 通过时间段分页查询临时租车运单数据
		 * @author 269701--lln
		 * @date 2016-03-22
		 * @param tempRentalQueryDto
		 * @param args
		 * @return
		 */
	  
	 List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByDate(Map<Object, Object> args, int start, int limit) ;
		/**
		 * 通过时间段查询临时租车运单总条数
		 * @author 269701--lln
		 * @date 2016-04-02
		 * @param tempRentalQueryDto
		 * @return long
		 */
	 Long selectWayBillByDateCount (Map<Object, Object> args);
	 	/**
		 * 登陆部门为营业部/车队
		 * 通过时间段分页查询临时租车运单数据
		 * @author 269701--lln
		 * @date 2016-03-31
		 * @param tempRentalQueryDto
		 * @param args
		 * @return
		 */
	 RentalMarkEntity selectWayBillByDate(RentalMarkEntity entity);
	 
	 /**
		 * 获取交接单号父节点任务编号
		 * @author 283244
		 * */
		String queryTaskNo(String billNo);
		/**
		 * 获取任务编号下的所有交接单号
		 * @author 283244
		 * */
		List<String>  queryBillNo(String id);
		/**
		 * 获取交接单信息
		 * @author 283244
		 * */
		
		RentalMarkEntity queryBillNoinfo(String billNo);

		/**310248
		 * 查询费用车承担部门的名称
		 * @param inviteNo
		 * @return
		 */
		public String queryBearFeesDeptName(String inviteNo);
		
		/**310248CUBC
		 * 查询费用车承担部门
		 * @param inviteNo
		 * @return
		 */
		public RentCarCubcRequest queryBearFeesDept(String inviteNo);

		 /**
			 *根据单号查询数据数量，分页用  (快递交接单)
			 * @author gouyangyang
			 * @date 2016-05-12
			 * @param rentalMarkDto 查询参数
			 * @return Long 分页用的总量
			 **/
		 Long queryExpressEntityCountByBillNo(RentalMarkDto rentalMarkDto);
		 
		 /**
			 *根据日期查询数据数量，分页用(快递交接单)
			 * @author gouyangyang
			 * @date 2016-05-12
			 * @param rentalMarkDto 查询参数
			 * @return Long 分页用的总量
			 **/
		 Long queryEntityCountByDate(RentalMarkDto rentalMarkDto);
		 
		 /**
			 *按日期查询租车标记数据，并返回一个list  (快递交接单)
			 * @author gouyangyang
			 * @date 2016-05-13
			 * @param rentalMarkDto 查询参数
			 * @param limit 分页
			 *@return List<RentalMarkEntity>,返回一个entitylist
			 **/
		 List<WKTfrBillEntity> queryRentalDeliveryEntityListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start);
		 
		 /**
			 *根据单号查询数据数量，分页用 (快递交接单)
			 * @author gouyangyang   313352
			 * @date 2014-06-11
			 * @param rentalMarkDto 查询参数
			 * @return Long 分页用的总量
			 **/
		 Long queryRentalMarkCountByBillNo(RentalMarkDto rentalMarkDto) ;
			
			

			/**
			 *根据日期查询数据数量，分页用   (快递交接单)
			 * @author gouyangyang  313352
			 * @date 2014-06-11
			 * @param rentalMarkDto 查询参数
			 * @return Long 分页用的总量
			 **/
		  Long queryRentalAREntityCountByDate(RentalMarkDto rentalMarkDto);
			 
			 /**
				 * 按单号查询租车标记数据，并返回一个list(快递交接单)
				 * @author gouyangyang
				 * @param rentalMarkDto 查询参数
				 * @param limit 分页
				 *@return List<RentalMarkEntity>,返回一个entitylist
				 **/
		  List<WKTfrBillEntity> queryListByDate(RentalMarkDto rentalMarkDto,int limit,int start);
			
			/**
			 *查询快递交接单下的运单号 
			 *@author gouyangyang   313352
			 * @date 2016-05-11
			 * @param handoverBillNoList 交接单号的list
			 * @return List<String> 对应的运单号list
			 ***/
		  List<String> queryForHandoverBillNo(List<String> expressWayBillNoList);
			 
			 /**
				 *按单号查询租车标记数据，并返回一个list (快递交接单)
				 * @author gouyangyang
				 * @date 2015-05-13
				 * @param rentalMarkDto 查询参数
				 * @param 无分页
				 *@return List<RentalMarkEntity>,返回一个entitylist
				 **/
		  List<WKTfrBillEntity> queryRentalEntityListByBillNo(RentalMarkDto rentalMarkDto);
			 
			 /**
				 * 按单号查询租车标记数据，并返回一个list  (快递交接单)
				 * @author gouyangyang 313352
				 * @param rentalMarkDto 查询参数
				 * @param 无分页
				 *@return List<RentalMarkEntity>,返回一个entitylist
				 **/
		  List<WKTfrBillEntity> queryMarkEntityListByDate(RentalMarkDto rentalMarkDto);

			/**
			 * 根据运单号查询标记信息
			 *@author gouyangyang   313352
			 * @date 2016-05-11
			 * @param handoverBillNoList 交接单号的list
			 * @return List<String> 对应的运单号list
			 ***/
			public List<WKTfrBillEntity> queryExpressWayBillMarkInfo(List<String> expressWayBillNoList);
			
			/**
			 *根据单号查询数据数量，分页用
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param shortRentalMarkDto 查询参数
			 * @return Long 分页用的总量
			 **/
			Long queryShortRentalMarkEntityCount(ShortRentalMarkDto shortRentalMarkDto);
			
			/**
			 *查询租车标记数据，并返回一个list
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param shortRentalMarkDto 查询参数
			 * @param limit 分页
			 *@return List<ShortRentalMarkEntity>,返回一个ShortRentalMarkEntity
			 **/
			public List<ShortRentalMarkEntity> queryShortRentalMarkEntityList(ShortRentalMarkDto shortRentalMarkDto,int limit,int start);
			
			/**
			 *查询租车标记明细表里是否有list里对应车辆任务数据
			 *有，说明已经被标记过。
			 * author ruilibao
			 *@date 2014-6-25  15:51:10
			 *@param truckTaskId 车辆任务list
			 *@return long
			 **/
			public Long queryTruckTaskRepeatMark(List<String> truckTaskId);
			
			/**
			 *查询约车编号
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param inviteVehicleNo 约车编号
			 * @return RentalMarkVo 页面需要的dto数据
			 **/
			ShortRentalMarkVo queryShortInviteVehicleNo(String inviteVehicleNo);
			
			/**
			 *查询运单件数
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param truckTaskId 车辆任务ID
			 * @return LONG 运单件数
			 **/
			Long queryTruckTaskWaybillCount(String truckTaskId);
			
			/**
			 *查询运单件数
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param truckTaskId 车辆任务ID
			 * @return LONG 返回标记件数
			 **/
			Long queryTruckTaskMarkCount(String truckTaskId);
			
			/**
			 * 批量插入租车标记数据
			 * @aurhor zenghaibin
			 * @param tempRentalMarkEntityList 租车标记主数据list
			 * @date 2014-06-16 9：30
			 ***/
			void addShortTempRentalMarkEntityList(List<TempRentalMarkEntity> tempRentalMarkEntityList);
			
			/**
			 *查询车辆状态
			 * @author ruilibao
			 * @date 2014-06-11
			 * @param truckTaskId 车辆任务ID
			 * @return String 返回车辆状态
			 **/
			String queryTruckTaskState(String truckTaskId);
		
}


