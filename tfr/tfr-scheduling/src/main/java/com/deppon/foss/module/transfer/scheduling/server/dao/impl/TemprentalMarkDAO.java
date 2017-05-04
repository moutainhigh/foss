package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITemprentalMarkDAO;
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

public class TemprentalMarkDAO  extends iBatis3DaoImpl implements ITemprentalMarkDAO{
	
	/**命名空间常量*/
	private static final String NAMESPACE = "foss.scheduling.temprentalMark.";
	private static final String NAMESPACEWKTRFBILL = "foss.scheduling.wkTrfBill.";
	private static final String NAMESPACE_SHORT = "foss.scheduling.shorttemprentalMark.";
	
	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryRentalMarkEntityListByDate(RentalMarkDto rentalMarkDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryRentalMarkEntityListByDate",rentalMarkDto,rowBounds);

		
	}
	
	
	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param 无分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryRentalMarkEntityListByDate(RentalMarkDto rentalMarkDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryRentalMarkEntityListByDate",rentalMarkDto);
	}
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryRentalMarkEntityListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start){
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryRentalMarkEntityListByBillNo", rentalMarkDto, rowBounds);
	}
	
	/**
	 *按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2015-01-23
	 * @param rentalMarkDto 查询参数
	 * @param 无分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryRentalMarkEntityListByBillNo(RentalMarkDto rentalMarkDto){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryRentalMarkEntityListByBillNo", rentalMarkDto);
	}
	
	/**
	 *按单号查询租车标记数据，并返回一个list
	 *
	 * @author zenghaibin
	 * @date 2015-01-23
	 * @param rentalMarkDto 查询参数
	 * @param 无分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryStowageBillListByBillNo(RentalMarkDto rentalMarkDto){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryStowageBillListByBillNo", rentalMarkDto);
	}


	/**
	 *根据日期查询数据数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryRentalMarkEntityCountByDate(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryRentalMarkEntityCountByDate", rentalMarkDto);
	}


	/**
	 *根据单号查询数据数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryRentalMarkEntityCountByBillNo(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryRentalMarkEntityCountByBillNo", rentalMarkDto);
	}
	

	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryStowageBillListByDate(RentalMarkDto rentalMarkDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryStowageBillListByDate",rentalMarkDto,rowBounds);
	}

	/**
	 * 按单号查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryStowageBillListByDate(RentalMarkDto rentalMarkDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryStowageBillListByDate",rentalMarkDto);
	}
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryStowageBillListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start){
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryStowageBillListByBillNo", rentalMarkDto, rowBounds);
	}
	
	/**
	 *根据单号查询配载单数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryStowageBillCountByBillNo(RentalMarkDto rentalMarkDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryStowageBillCountByBillNo", rentalMarkDto);
	}
	
	/**
	 *根据单号查询配载单数量，分页用
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryStowageBillCountByDate(RentalMarkDto rentalMarkDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryStowageBillCountByDate", rentalMarkDto);
	}
	
	/**
	 *查询约车编号
	 * @author zenghaibin
	 * @date 2014-06-11
	 * @param inviteVehicleNo 约车编号
	 * @return RentalMarkVo 页面需要的dto数据
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public RentalMarkVo queryInviteVehicleNo(String inviteVehicleNo) {
																								   
		List<RentalMarkVo> list = (List<RentalMarkVo> )this.getSqlSession().selectList( NAMESPACE+"queryInviteVehicleNo2",  inviteVehicleNo);
		if(list!=null&&!list.isEmpty()){
			
			return list.get(0);
		}
		/*if(list.isEmpty()){
			list = (List<RentalMarkVo> )this.getSqlSession().selectList( NAMESPACE+"queryInviteVehicleNo2",  inviteVehicleNo);
			if(!list.isEmpty()){
			return list.get(0);	
			}
		}*/
		return null;
	}
	
	/**
	 *查询小票单号是否可见
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询所需参数 小票单号
	 * @return Long
	 **/
	@Override
	public Long smallTicketValidate(RentalMarkDto rentalMarkDto){
		
		long count=0;
		count= (Long)this.getSqlSession().selectOne(NAMESPACE+"smallTicketValidateDeliverBill",rentalMarkDto);
		return count;
		
	}
	/**
	 *检查小票单号的合法性 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalMarkDto 小票单号
	 * @return List<SmallTicketEntity> 小票单号list
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<SmallTicketEntity> querysmallTicketNum(RentalMarkDto rentalMarkDto){
		
		List<SmallTicketEntity> resultList= new ArrayList<SmallTicketEntity>();
		 resultList = (List<SmallTicketEntity>)this.getSqlSession().selectList(NAMESPACE+"querysmallTicketNum",rentalMarkDto);
			return resultList;

	}
	/**
	 *查询运单所需的正确小票号 
	 *@author zenghaibin
	 * @date 2014-10-112
	 * @param rentalMarkDto 小票单号
	 * @return List<SmallTicketEntity> 小票单号list
	**/
	@SuppressWarnings("unchecked")
	@Override
	public List<SmallTicketEntity> querySmallTicketForWayBill(HashMap<String,String> hp){
		
		List<SmallTicketEntity> resultList= new ArrayList<SmallTicketEntity>();
		 resultList = (List<SmallTicketEntity>)this.getSqlSession().selectList(NAMESPACE+"querySmallTicketForWayBill",hp);
			return resultList;
	}
	/**
	 *查询交接单下的运单号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param handoverBillNoList 交接单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWayBillNoForHandoverBillNo(List<String> handoverBillNoList){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryWayBillNoForHandoverBillNo",handoverBillNoList);
	}
	
	/**
	 *查询派送单下的运单号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param deliverBillNoList 派送单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	@SuppressWarnings("unchecked")
	public List<String> queryWayBillNoForDeliverBillNo(List<String> deliverBillNoList){
		List<String> resultList = this.getSqlSession().selectList(NAMESPACE+"queryWayBillNoForDeliverBillNo",deliverBillNoList);
		
		return resultList;
	}
	
	/**
	 *查询配载单有多少运单 
	 *@author zenghaibin
	 * @date 2014-10-13
	 * @param stowageNolist 派送单号的list
	 * @return Long
	 ***/
	public Long queryWayBillCountForStowageNo(List<String> stowageNolist){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryWayBillCountForStowageNo",stowageNolist);
	}
	
	/**
	 *查询派送单对应的运单 
	 * author zenghaibin
	 *@date 2014-11-05  10:10:10
	 *@param deliverBillNoList
	 *@return List<String>
	 **/
	public Long queryWayBillForDeliverBill(List<String> deliverBillNoList){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryWayBillForDeliverBill",deliverBillNoList);
	}
	/**
	 *查租车编号 
	 *@author zenghaibin
	 * @date 2014-06-11
	 * @param rentalUse 租车用途
	 * @param useDate 用车日期
	 * @return queryTempRentalMarkNO 租车编号
	 */
	@Override
	public String queryTempRentalMarkNO(String rentalUse,Date useDate){
		Map<String,String> map =new HashMap<String,String>();
		map.put("rentalUse", rentalUse);
		String useCarDate=com.deppon.foss.util.DateUtils.convert(useDate,com.deppon.foss.util.DateUtils.DATE_SHORT_FORMAT);
		map.put("useCarDate", useCarDate);
//		String tempRentalMarkNO;
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryTempRentalMarkNO", map);
	}
	
	/**
	 *查询租车标记明细表里是否有list里对应单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 *@param handoverBillNoList 单号list
	 *@return long
	 **/
	@Override
	public Long queryHandOverBillRepeatMark(List<String> handoverBillNoList){
		
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryHandOverBillRepeatMark", handoverBillNoList);
	}
	
	/**
	 *查询租车标记明细表里是否有list里对应运单单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 *@param handoverBillNoList 单号list
	 *@return long
	 **/
	public Long queryWayBillRepeatMark(RentalMarkDto rentalMarkDto){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryWayBillRepeatMark", rentalMarkDto);

	}
	/**
	 * 批量插入租车标记数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTempRentalMarkEntityList(List<TempRentalMarkEntity> tempRentalMarkEntityList){
		
		this.getSqlSession().insert(NAMESPACE+"addTempRentalMarkEntityList", tempRentalMarkEntityList);
	}
	
	/**
	 * 批量插入租车标记明细数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTempRentalMarkDetailEntityList(List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList){
		
		this.getSqlSession().insert(NAMESPACE+"addTempRentalMarkDetailEntityList", tempRentalMarkDetailEntityList);

	}

	/**
	 * 批量添加租车标记小票单号数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addTemprentalMarkSmticksEntityList(List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList){
		
		this.getSqlSession().insert(NAMESPACE+"addTemprentalMarkSmticksEntityList", temprentalMarkSmticksEntityList);

	}

	/**
	 *检查小票单号是否已经被标记过
	 *@author zenghaibin
	 *@param  list 小票单号List
	 *@date 2014-06-17 16:04
	 ***/
	@SuppressWarnings("unchecked")
	public List<String> querySmallTicketRe(List<String> smallTicketList){
		List<String> resultList= new ArrayList<String>();
		
		 resultList = (List<String>)this.getSqlSession().selectList(NAMESPACE+"querySmallTicketRe",smallTicketList);
		
		 return resultList;
	}
	
	/**
	 *检查运单开单上门接货且开单输入的工号是否为司机工号
	 *@author zenghaibin
	 *@param  String wayBillNo 运单号
	 *@date 2014-06-19 15:04
	 ***/
	@Override
	public Long queryDriverNoOperateNo(String wayBillNo){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDriverNoOperateNo",wayBillNo);
	}

	/**
	 *更新租车标记表TFR.T_OPT_TEMPRENTALMARK 预提状态,预提工作流号,预提工作流处理结果
	 *@author zenghaibin
	 *@param  list 小票单号List
	 *@date 2014-07-11 16:04
	 ***/
	@Override
	public void updateTemprentalMarkAccrued(RentalMarkDto dto){
		this.getSqlSession().update(NAMESPACE+"updateTemprentalMarkAccrued", dto);
	}

	/**
	 * 作废租车标记接口
	 * @author zenghaibin
	 * @date 2014-07-14
	 * @param hsp 修改人信息和租车编号
	 ***/
	public void invalidRentalMark(Map<String,Object> hsp){
		
		this.getSqlSession().update(NAMESPACE+"invalidRentalMark", hsp);
	}
	
	/**
	 *查询租车记录重复项
	 *@author zenghaibin
	 *@date 2014-07-14
	 *@param tempRentalMarkNo 租车编号
	 ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<TempRentalMarkDetailEntity> queryRentalMarkDuplicates(String tempRentalMarkNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryRentalMarkDuplicates", tempRentalMarkNo);
	}
	
	/**
	 *查询租车记录重复项
	 *@author zenghaibin
	 *@date 2014-07-29
	 *@param inviteVehicleNo 约车编号
	 *@return String 租车编号
	 ***/
	@Override
	public String valiteInviteVehicleNo(String inviteVehicleNo){
		List resultList=new ArrayList<String>();
		
		resultList=this.getSqlSession().selectList(NAMESPACE+"valiteInviteVehicleNo", inviteVehicleNo);
		if(resultList!=null&&!resultList.isEmpty()){
			return (String)resultList.get(0);
		}else{
			return "";
		}
	}
	/**
	 * 登陆部门为营业部/车队
	 * 通过时间段分页查询临时租车运单数据
	 * @author 269701--lln
	 * @date 2016-03-31
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RentalMarkEntity selectWayBillByDate(RentalMarkEntity entity) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		//运单号
		map.put("wayBillNo", entity.getBillNo());
		//提货方式
		map.put("pickUpWay", entity.getPickUpWay());
		//租车编号
		map.put("TemprentalMarkNo", entity.getRentalNum());
		//车牌号
		map.put("vehicleNo", entity.getVehicleNo());
		
		List<RentalMarkEntity> list=this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate",map);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	
	/**
	 * 通过时间段查询临时租车已标记运单总条数
	 * @author 269701--lln
	 * @date 2016-04-14
	 * @param tempRentalQueryDto
	 * @return long
	 */
	public Long selectMarkedWayBillByDateCount(Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "selectMarkedWayBillByDateCount", args);
	}
	
	
	
	/**
	 * 通过运单号查询 对应的运单信息
	 * @author 269701--lln
	 * @date 2016-04-14
	 * @param String
	 * @return RentalMarkEntity
	 */
	public RentalMarkEntity queryMarkedWayBillInfo(String wayBillNo,String createOrgCode) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		//运单号
		map.put("wayBillNo", wayBillNo);
		//（前台）创建部门--制单部门（运单表）
		map.put("createOrgCode", createOrgCode);
		@SuppressWarnings("unchecked")
		List<RentalMarkEntity> list=this.getSqlSession().selectList(NAMESPACE + "queryMarkedWayBillInfo",map);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 通过运单号查询 租车运单总条数--已标记和未标记
	 * @author 269701--lln
	 * @date 2016-04-15
	 * @param tempRentalQueryDto
	 * @return long
	 */
	public Long countTempWayBillByBillNos (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countTempWayBillByBillNos", args);
	}
	/**
	 * 登陆部门为营业部/登陆部门为驻地营业部
	 * 通过运单号分页查询临时租车运单数据--已标记和未标记的数据
	 * @author 269701--lln
	 * @date 2016-04-15
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> querySaleDepartTempRentalWayBillByBill(
			Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByBill_1_2",
				args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过运单号分页查询临时租车运单数据--标记和未标记
	 * @author 269701--lln
	 * @date 2016-04-15
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByBill(
			Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByBill_3_4",
				args, rowBounds);
	}
	/**
	 * 登陆部门为营业部或驻地营业部
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverByDate_1_2", args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverByDate_3_4", args, rowBounds);
	}
	
	/**
	 * 营业部/驻地营业部
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverBills_1_2", args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过派送单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverBills_3_4", args, rowBounds);
	}
	
	/**
	 * 通过时间段查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalDeliverBillByDate (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalDeliverBillByDate", args);
	}
	
	/**
	 * 通过单号查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalDeliverBillByBillNos (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalDeliverBillByBillNos", args);
	}
	/**
	 * 通过单号查临时租车快递运单总条数
	 * @param tempRentalQueryDto
	 */
	public Long countExpressByWayBill(Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countExpressByWayBill", args);
	}
	
	/**
	 * 通过快递单号分页查询临时租车快递单号数据
	 * @param tempRentalQueryDto
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> queryExpressBillByBill(Map<Object, Object> args,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressBillByBill",args,rowBounds);	
	}
	/**
	 * 登陆部门为营业部/登陆部门为驻地营业部
	 * 通过时间段分页查询临时租车运单数据
	 * @author 269701--lln
	 * @date 2016-03-22
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> querySaleDepartTempRentalWayBillByDate(
			Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate_1_2",
				args, rowBounds);
	}

	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车运单数据
	 * @author 269701--lln
	 * @date 2016-03-22
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByDate(
			Map<Object, Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate_3_4",
				args, rowBounds);
	}	
	/**
	 * 通过时间段查询临时租车运单总条数
	 * @author 269701--lln
	 * @date 2016-04-02
	 * @param tempRentalQueryDto
	 * @return long
	 */
	public Long selectWayBillByDateCount (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "selectWayBillByDateCount", args);
	}	
	/**
	 * 登陆部门为营业部/车队
	 * 通过时间段分页查询临时已标记租车运单数据
	 * @author 269701--lln
	 * @date 2016-04-14
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentalMarkEntity> queryMarkedWayBillByDate(Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "queryMarkedWayBillByDate",args,rowBounds);
	}

	/**
	 * 根据交接单号 查询车辆单据 表tfr.t_Opt_Truck_Task_Bill/TFR.T_OPT_TRUCK_TASK_DETAIL 
	 * 获取车辆任务主表id
	 */
	@Override
	public String queryTaskNo(String billNo) {
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryTaskNo",billNo);
	}


	/**
	 * 根据车辆任务主表ID 查询车辆任务单据表FR.T_OPT_TRUCK_TASK_BILL  获取单号
	 * @author 269701--lln
	 * @date 2016-06-30
	 * @desc 追加查询条件：单据类型为交接单（HANDOVER）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryBillNo(String id){
		return (List<String>)this.getSqlSession().selectList(NAMESPACE + "queryBillNo",id);
	}


	@Override
	public RentalMarkEntity queryBillNoinfo(String billNo) {
			return (RentalMarkEntity)this.getSqlSession().selectOne(NAMESPACE+"queryBillNoinfo",billNo);
	}


	@Override
	public String queryBearFeesDeptName(String inviteNo) {
		return  (String) this.getSqlSession().selectOne(NAMESPACE+"queryBearFeesDeptName",inviteNo);
		 
	}
	
	@Override//310248
	public RentCarCubcRequest queryBearFeesDept(String inviteNo) {
		return  (RentCarCubcRequest) this.getSqlSession().selectOne(NAMESPACE+"queryBearFeesDept",inviteNo);
		 
	}
	/**
	 * 按单号查询租车标记数据，并返回一个list  (快递交接单)
	 * @author gouyangyang 313352
	 * @param rentalMarkDto 查询参数
	 * @param 无分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<WKTfrBillEntity> queryMarkEntityListByDate(RentalMarkDto rentalMarkDto) {
		return this.getSqlSession().selectList(NAMESPACEWKTRFBILL + "queryMarkEntityListByDate",rentalMarkDto);
	}
	
	/**
	 *按日期查询租车标记数据，并返回一个list
	 * @author gouyangyang
	 * @date 2016-05-13
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<WKTfrBillEntity> queryRentalDeliveryEntityListByBillNo(RentalMarkDto rentalMarkDto,int limit,int start){
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACEWKTRFBILL+"queryRentalDeliveryEntityListByBillNo", rentalMarkDto, rowBounds);
	}

	/**
	 *按单号查询租车标记数据，并返回一个list (快递交接单)
	 * @author gouyangyang
	 * @date 2015-05-13
	 * @param rentalMarkDto 查询参数
	 * @param 无分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<WKTfrBillEntity> queryRentalEntityListByBillNo(RentalMarkDto rentalMarkDto){
		
		return this.getSqlSession().selectList(NAMESPACEWKTRFBILL+"queryRentalEntityListByBillNo", rentalMarkDto);
	}
	/**
	 *根据日期查询数据数量，分页用   (快递交接单)
	 * @author gouyangyang  313352
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryRentalAREntityCountByDate(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACEWKTRFBILL + "queryRentalAREntityCountByDate", rentalMarkDto);
	}
	
	/**
	 *根据日期查询数据数量，分页用(快递交接单)
	 * @author gouyangyang
	 * @date 2016-05-12
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryEntityCountByDate(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACEWKTRFBILL + "queryEntityCountByDate", rentalMarkDto);
	}
	
	/**
	 *根据单号查询数据数量，分页用 (快递交接单)
	 * @author gouyangyang   313352
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryRentalMarkCountByBillNo(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACEWKTRFBILL + "queryRentalMarkCountByBillNo", rentalMarkDto);
	}
	
	/**
	 *根据单号查询数据数量，分页用  (快递交接单)
	 * @author gouyangyang  313352
	 * @date 2016-05-12
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryExpressEntityCountByBillNo(RentalMarkDto rentalMarkDto) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACEWKTRFBILL+ "queryExpressEntityCountByBillNo", rentalMarkDto);
	}
	
	/**
	 * 按单号查询租车标记数据，并返回一个list(快递交接单)
	 * @author gouyangyang
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<WKTfrBillEntity> queryListByDate(RentalMarkDto rentalMarkDto,int limit,int start) {
		//分页
		List<WKTfrBillEntity> listEntity = null;
		try {
			RowBounds rowBounds = new RowBounds(start, limit);
			listEntity = this.getSqlSession().selectList(NAMESPACEWKTRFBILL + "queryListByDate",rentalMarkDto,rowBounds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listEntity;
	}
	/**
	 *查询快递交接单下的运单号 
	 *@author gouyangyang   313352
	 * @date 2016-05-11
	 * @param handoverBillNoList 交接单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryForHandoverBillNo(List<String> expressWayBillNoList){
		
		return this.getSqlSession().selectList(NAMESPACEWKTRFBILL+"queryForHandoverBillNo",expressWayBillNoList);
	}
	
	/**
	 * 根据运单号查询标记信息
	 *@author gouyangyang   313352
	 * @date 2016-05-11
	 * @param handoverBillNoList 交接单号的list
	 * @return List<String> 对应的运单号list
	 ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<WKTfrBillEntity> queryExpressWayBillMarkInfo(List<String> expressWayBillNoList){
		
		return this.getSqlSession().selectList(NAMESPACEWKTRFBILL+"queryExpressMarkInfo",expressWayBillNoList);
	}
	
	/**
	 *根据单号查询数据数量，分页用
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @return Long 分页用的总量
	 **/
	@Override
	public Long queryShortRentalMarkEntityCount(ShortRentalMarkDto shortRentalMarkDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE_SHORT + "queryShortRentalMarkEntityCount", shortRentalMarkDto);
	}
	
	/**
	 *查询租车标记数据，并返回一个list
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param rentalMarkDto 查询参数
	 * @param limit 分页
	 *@return List<RentalMarkEntity>,返回一个entitylist
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<ShortRentalMarkEntity> queryShortRentalMarkEntityList(ShortRentalMarkDto shortRentalMarkDto,int limit,int start){
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE_SHORT+"queryShortRentalMarkEntityList", shortRentalMarkDto, rowBounds);
	}
	
	/**
	 *查询租车标记明细表里是否有list里对应车辆任务数据
	 *有，说明已经被标记过。
	 * author ruilibao
	 *@date 2014-6-25  15:51:10
	 *@param truckTaskIdList 车辆任务list
	 *@return long
	 **/
	@Override
	public Long queryTruckTaskRepeatMark(List<String> truckTaskIdList){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE_SHORT+"queryTruckTaskRepeatMark", truckTaskIdList);
	}
	
	/**
	 *查询约车编号
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param inviteVehicleNo 约车编号
	 * @return RentalMarkVo 页面需要的dto数据
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public ShortRentalMarkVo queryShortInviteVehicleNo(String inviteVehicleNo) {
		
		List<ShortRentalMarkVo> list = (List<ShortRentalMarkVo> )this.getSqlSession().selectList( NAMESPACE_SHORT+"queryShortInviteVehicleNo",  inviteVehicleNo);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 *查询运单件数
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param truckTaskId 车辆任务ID
	 * @return Long 返回运单件数
	 **/
	@Override
	public Long queryTruckTaskWaybillCount(String truckTaskId) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE_SHORT+"queryTruckTaskWaybillCount", truckTaskId);
	}
	
	/**
	 *查询车辆标记件数
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param inviteVehicleNo 约车编号
	 * @return RentalMarkVo 页面需要的dto数据
	 **/
	@Override
	public Long queryTruckTaskMarkCount(String truckTaskId) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE_SHORT+"queryTruckTaskMarkCount", truckTaskId);
	}
	
	/**
	 * 批量插入租车标记数据
	 * @aurhor zenghaibin
	 * @param tempRentalMarkEntityList 租车标记主数据list
	 * @date 2014-06-16 9：30
	 ***/
	public void addShortTempRentalMarkEntityList(List<TempRentalMarkEntity> tempRentalMarkEntityList){
		
		this.getSqlSession().insert(NAMESPACE_SHORT+"addShortTempRentalMarkEntityList", tempRentalMarkEntityList);
	}
	
	/**
	 * 查询车辆状态
	 * @author ruilibao
	 * @date 2014-06-11
	 * @param truckTaskId 车辆任务ID
	 * @return String 车辆状态
	 **/
	@Override
	public String queryTruckTaskState(String truckTaskId) {
		return (String)this.getSqlSession().selectOne(NAMESPACE_SHORT+"queryTruckTaskState", truckTaskId);
	}
}
