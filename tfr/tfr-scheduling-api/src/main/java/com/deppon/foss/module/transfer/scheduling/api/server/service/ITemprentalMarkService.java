package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcRequest;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShortRentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortRentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortTempRentalMatkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TempRentalMatkVo;


/**
 *临时租车标记的service
 *用来管理和设置租车标记 
 *@author zenghaibin
 *@date 2014-06-11
 **/
public interface ITemprentalMarkService extends IService {

	
	/**
	 * 获取当前部门编号
	 * @author zenghaibin
	 * @date 2014-5-28 下午4:50:53
	 * @return String
	 */
	public String queryOrgCode();
	
	/**
	 * 查询租车标记数据
	 * @author zenghaibin
	 * @date 2014-5-30 上午 10:10:10
	 * @param RentalMarkDto,int,int
	 * @return List<RentalMarkEntity>
	 **/
	public HashMap<String,Object> queryRentalMarkEntityList(RentalMarkVo rentalMarkVo,int limit,int start);
	/**
	 *根据查询总条数 
	 *@author zenghaibin
	 *@date 2014-5-30 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	public Long queryRentalMarkEntityCount(RentalMarkDto rentalMarkDto);
	
	
	/**
	 * 根据出发部门、到达部门算出最大公里数
	 * @param entityList
	 * @modify 273247
	 * @return
	 */
	public Long queryMaxDistance(List<RentalMarkEntity> entityList);
	/**
	 *查询总条数 
	 *@author zenghaibin
	 *@date 2014-9-21 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	public Long queryStowageBillListCount(RentalMarkDto rentalMarkDto);
	
	/**
	*检查约车编号
	*@author zenghaibin
	*@date 2014-6-11  10:10:10
	*@param rentalMarkVo
	*@return RentalMarkVo
	**/
	public RentalMarkVo queryInviteVehicleNo(RentalMarkVo rentalMarkVo);
	
	/**
	 *检查小票单号 是否可见
	 *author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param RentalMarkDto
	 *@return boolean
	 *
	 **/
	public boolean smallTicketValidate (RentalMarkDto rentalMarkDto);
	
	/**
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param rentalMarkDto 租车标记dto
	 *@return List<String>
	 *检查小票单号的合法性
	 **/
	public HashMap<String,String>  querysmallTicketNum(RentalMarkDto rentalMarkDto);
	
	/**
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param rentalMarkDto 租车标记dto
	 *@return List<String>
	 *查询运单的小票号
	 **/
	public HashMap<String,String>  querySmallTicketForWayBill(RentalMarkDto rentalMarkDto);
	/**
	 *查询交接单对应的运单 
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param handoverBillNoList 交接单号list
	 *@return List<String>
	 **/
	public List<String> queryWayBillNoForHandoverBillNo(List<String> handoverBillNoList);
	
	/**
	 *查询派送单对应的运单 
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param deliverBillNoList 派送单号list
	 *@return List<String>
	 **/
	public List<String> queryWayBillNoForDeliverBillNo(List<String> deliverBillNoList);
	
	/**
	 *查询租车标记明细表里是否有list里对应单号数据
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
	 *添加租车标记 
	 *author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param tempRentalMatkVo:租车标记的vo
	 * @throws ParseException 
	 **/
	public void addTempRentalMark(TempRentalMatkVo tempRentalMatkVo);
	
	
	
	/**
	 *检查小票单号是否已经被标记过
	 *@author zenghaibin
	 *@param  list 小票单号List
	 *@date 2014-06-17 16:04
	 ***/
	public List<String> querySmallTicketRe(List<String> list);
	

	/**
	 *更新租车标记表TFR.T_OPT_TEMPRENTALMARK 预提状态,预提工作流号,预提工作流处理结果
	 *@author zenghaibin
	 *@param  参数list 批量更新
	 *@date 2014-07-11 16:04
	 ***/
	public void updateTemprentalMarkAccrued(RentalMarkDto dto);
	
	/**
	 * 作废租车标记接口
	 * @author zenghaibin
	 * @date 2014-07-14
	 * @param String tempRentalMarkNo 租车编号
	 ***/
	public void invalidRentalMark (List<String> tempRentalMarkNoList,CurrentInfo cInfo)throws Exception;
	
	/**
	 *查询多次标记项
	 *@author zenghaibin
	 *@date 2014-07-14
	 *@param tempRentalMarkNo 租车编号
	 ***/
	public List<TempRentalMarkDetailEntity> queryRentalMarkDuplicates(String tempRentalMarkNo) throws Exception;	
	
	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 205109-foss-zenghaibin
	 * @date 2015-01-23 上午8:59:05
	 */
	public String encodeFileName(String fileName) ;
	
	/**
	 *临时租车标记数据导出
	 *@param  tempRentalMatkVo 导出条件
	 * @date 2015-01-23 上午9：09
	 * @author 205109-foss-zenghaibin
	 ***/
	public InputStream exportTempRentalExcel(RentalMarkVo rentalMarkVo);
	
	/**
	 *
	 *@param  tempRentalMatkVo 导出条件
	 * @date 2016-06-23 
	 * @author 313352-gyy
	 ***/
	public InputStream exportTempRentalMarkExcel(RentalMarkVo rentalMarkVo,int limit,int start);
	
	/**
	 *根据交接单号获取当前任务编号下的所有交接的交接单号
	 *@author 283244
	 ***/
	public List<String> queryTaskNoOfBillno(String  billNo);
	/**
	 * 获取交接单号的详细信息
	 * */
	public RentalMarkEntity queryBillNoInfo(String billNo);
	/**
	 * 310248给结算
	 * 通过约车编号查询费用承担部门
	 */
	public String queryBearFeesDeptName(String inviteNo);
	/**
	 * 310248给CUBC临时租车时用
	 * 通过约车编号查询费用承担部门
	 */
	public RentCarCubcRequest queryBearFeesDept(String inviteNo);
	/**
	 *查询快递交接单总条数 
	 *@author gouyangyang
	 *@date 2016-5-12 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	public Long queryExpressMarkEntityCount(RentalMarkDto rentalMarkDto);
	
	/**
	 *查询快递交接单总条数 
	 *@author gouyangyang
	 *@date 2016-5-13 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	public Long queryExpressEntityCount(RentalMarkDto rentalMarkDto);
	
	/**
	 *查询快递交接单对应的运单 
	 * author gouyangyang  313352
	 *@date 2014-6-11  10:10:10
	 *@param handoverBillNoList
	 *@return List<String>
	 **/
	public List<String> queryForHandoverBillNo(List<String> expressWayBillNoList);
	
	
	/**
	 * 短途查询租车标记数据
	 * @author ruilibao
	 * @date 2016-12-10
	 * @param ShortRentalMarkVo,int,int
	 * @return List<ShortRentalMarkEntity>
	 **/
	HashMap<String,Object> queryShortRentalMarkEntityList(ShortRentalMarkVo shortRentalMarkVo,int limit,int start);
	
	/**
	 *查询租车标记总条数 
	 *@author ruilibao
	 *@date 2014-5-30 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	Long queryShortRentalMarkEntityCount(ShortRentalMarkDto shortRentalMarkDto);
	
	/**
	 *查询租车标记明细表里是否有list里对应车辆任务数据
	 *有，说明已经被标记过。
	 * author ruilibao
	 *@date 2014-6-25  15:51:10
	 *@param truckTaskId 车辆任务list
	 *@return long
	 **/
	Long queryTruckTaskRepeatMark(List<String> truckTaskId);

	/**
	 * 根据出发部门、到达部门算出最大公里数
	 * @param entityList
	 * @modify 332209
	 * @return
	 */
	Long queryMaxDistanceShort(List<ShortRentalMarkEntity> shortEntityList);
	/**
	 * 查询约车编号
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	ShortRentalMarkVo queryShortInviteVehicleNo(String inviteVehicleNo);
	
	/**
	 *添加租车标记 
	 *author ruilibao
	 *@date 2014-6-11  10:10:10
	 *@param tempRentalMatkVo:租车标记的vo
	 * @throws ParseException 
	 **/
	public void addShortTempRentalMark(ShortTempRentalMatkVo shortTempRentalMatkVo);
}
