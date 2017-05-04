package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITemporaryRentalCarMarkTimeManagementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.TempRentalQueryDto;
import com.deppon.foss.module.settlement.agency.api.server.service.IRentCarService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.RentCarDto;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.CourierWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ExpressAirportEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QueryExpressAirportEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QueryWaybillToFoss;
import com.deppon.foss.module.transfer.common.api.shared.dto.AirExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITemprentalMarkDAO;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IASYRentCarCubcService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.SmallTicketEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TemprentalMarkSmticksEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MultiCarTakeGoodsDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcRequest;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDO;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShortRentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.BudgetEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlRequestEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlResponseEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortRentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortTempRentalMatkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TempRentalMatkVo;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *临时租车标记的service
 *用来管理租车标记 
 *@author zenghaibin
 *@date 2014-06-11
 **/
public class TemprentalMarkService implements ITemprentalMarkService {
	/**插入日志信息**/
	private static final Logger LOGGER = LogManager
			.getLogger(TemprentalMarkService.class);
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;//查询部门名称和部门编码
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService ;//查询上级部门名称和部门编码
	private ISaleDepartmentService saleDepartmentService;//营业部门
	private ITemprentalMarkDAO temprentalMarkDAO;//租车标记操作的dao
	private ILeasedDriverService leasedDriverService;
	private ITfrCommonService tfrCommonService;//生成租车编号序列号的service
	private IVehicleService vehicleService;//查询车型和净空的接口
	private IConfigurationParamsService configurationParamsService;//租车标记有效期的参数查询
	private IRentCarService rentCarService;//调结算接口生成应付单
	private IMotorcadeService motorcadeService;//查询车队信息
	private ISalesMotorcadeService salesMotorcadeService;//查询车队服务营业部
	private ITemporaryRentalCarMarkTimeManagementService temporaryRentalCarMarkTimeManagementService;//查询租车标记有效期时间
	private ILineService LineService;//调用线路接口
	private IFOSSToWkService  fossToWkService;  // 调用快递运单接口
	private IASYRentCarCubcService aSYRentCarCubcService;//租車標記新鄭時的
	private IInviteVehicleService inviteVehicleService; // 外请车接口
	
	private IFossToCubcService fossToCubcService;//FOSS同步推送数据至CUBC Service
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * 查询角色type 1：营业部 2：驻地营业部 3：车队 4:外场
	 */
	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;
	private final static int FOUR = 4;
	/**
	 * 单据类型： 1运单 2派送单
	 */
	private final static String BILL_TYPE_WAYBILL = "WAYBILL";
	private final static String BILL_TYPE_DELIVERBILL = "DELIVERBILL";
	/**
	 * 查询类型:QBD：按时间段查询 QBB：按单号查询
	 */
	private final static String QUERY_TYPE_QBD = "QBD";
	/**
	 * 查询类型:QBD：按时间段查询 QBB：按单号查询
	 */
	private final static String QUERY_TYPE_QBB = "QBB";
	
	/**
	 * 不作分页时参数
	 */
	private final static int NO_ROW_OFFSET = 0;
	private final static int NO_ROW_LIMIT = Integer.MAX_VALUE;
	private final static String YES = "Y";

	/**
	 * 获取当前部门编号
	 * @author zenghaibin
	 * @date 2014-5-28 下午4:50:53
	 * @return String
	 */
	@Override
	public String queryOrgCode() {
		
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);
		OrgAdministrativeInfoEntity topFleet= orgAdministrativeInfoComplexService.getTopFleetByCode(org.getCode());
		String departmentSignle=null;
		if (null != org) {
			// 校验是否是个营业部
			if (StringUtils.isNotBlank(org.getSalesDepartment())
					&& StringUtils.equals(org.getSalesDepartment(),
							FossConstants.YES)) {
				departmentSignle = "SalesDepartment_" + orgCode;
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
						.querySaleDepartmentByCodeNoCache(orgCode);
				if (null != saleDepartmentEntity) {
					if (StringUtils.isNotBlank(saleDepartmentEntity
							.getStation())     
							&& StringUtils.equals(
									saleDepartmentEntity.getStation(),
									FossConstants.YES)) {
						departmentSignle = "SalesDepartmentStation_" + orgCode;// 派送部驻地营业部
					}
				}
				
			} else if(topFleet!=null){
				//查询顶级车队
				MotorcadeEntity superOrg=null;
				//查询所属外场
				 superOrg = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
				
				 if (null != superOrg&&superOrg.getTransferCenter()!=null
						 &&!"".equals(superOrg.getTransferCenter())) {
						departmentSignle = "TransferChild_" + superOrg.getTransferCenter();
				 	}
			}else if(StringUtils.isNotBlank(org.getTransferCenter())
					&& StringUtils.equals(org.getTransferCenter(),FossConstants.YES)) {
				departmentSignle = "TransferCenter_" + orgCode;
			}else {
					// 如果顶级车对没有找到所服务外场，则返回
					departmentSignle = "Profdepartment_" + orgCode;
				}
		}
		return departmentSignle;
  }
	/**
	 *查询车队所属营业部 
	 * 
	 ***/
	
	private List<String> queryTransDepartmentCode(){
		List<String> orgCodeList=new ArrayList<String>();
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);
		if(org!=null){
	
			if (StringUtils.isNotBlank(org.getTransDepartment())&& 
					StringUtils.equals(org.getTransDepartment(),FossConstants.YES)
					||StringUtils.equals(org.getDispatchTeam(),FossConstants.YES)) {//如果是车队
				//查询顶级车队
				OrgAdministrativeInfoEntity topFleet= orgAdministrativeInfoComplexService.getTopFleetByCode(org.getCode());
				if(null!=topFleet){
					SalesMotorcadeEntity topEntity = new SalesMotorcadeEntity();
					topEntity.setMotorcadeCode(topFleet.getCode());
					//查询顶级车队所属营业部
					List<SalesMotorcadeEntity> salesDeparts = salesMotorcadeService
						.querySalesMotorcadeExactByEntity(topEntity,0,
								 Integer.MAX_VALUE);
				if( salesDeparts!=null&&salesDeparts.size()>0){
					
					for(int i=0;i<salesDeparts.size();i++){
						if(salesDeparts.get(i).getSalesdeptCode()!=null&&!salesDeparts.get(i).getSalesdeptCode().equals("")){
							orgCodeList.add(salesDeparts.get(i).getSalesdeptCode());
						}
					}
				}
				}
			}
		}
		return orgCodeList;
	}
	
	/**
	 * 查询租车标记数据
	 * @author zenghaibin
	 * @date 2014-5-30 上午 10:10:10
	 * @param RentalMarkDto,int,int
	 * @return List<RentalMarkEntity>
	 **/
	@Override
	public HashMap<String,Object> queryRentalMarkEntityList(RentalMarkVo rentalMarkVo,int limit,int start){
		if(rentalMarkVo==null){
			return null;
		}
		RentalMarkDto rentalMarkDto = rentalMarkVo.getRentalMarkDto();//交接单查询所需参数
		if(rentalMarkDto==null){
			return null;
		}
		String departmentSignle = rentalMarkDto.getDepartmentSignle();
		List<String> orgCodeList=new ArrayList<String>();
		if(departmentSignle.equals("Profdepartment")||departmentSignle.equals("TransferChild")
				||"TransferCenter".equals(departmentSignle)){
			orgCodeList=this.queryTransDepartmentCode();
		}
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();//用于查询派送单和运单，配载单和交接单不用这个
		HashMap<String,Object> queryMap = new HashMap<String,Object>();//查询结果map
		List<RentalMarkEntity> arrayList = new ArrayList<RentalMarkEntity>();//查询结果list
		List<WKTfrBillEntity> resultList = new ArrayList<WKTfrBillEntity>();//查询结果快递交接单list
		List<RentalMarkEntity> handoverList = new ArrayList<RentalMarkEntity>();//交接单数据
		List<RentalMarkEntity> wayBillList = new ArrayList<RentalMarkEntity>();//运单数据
		List<RentalMarkEntity> deliverBillList = new ArrayList<RentalMarkEntity>();//派送单数据
		List<RentalMarkEntity> stowageBillList = new ArrayList<RentalMarkEntity>();//配载单数据
		List<WKTfrBillEntity> handoverBillList = new ArrayList<WKTfrBillEntity>();//快递交接单数据
		List<WKTfrBillEntity> expressWaybillList = new ArrayList<WKTfrBillEntity>();// wk快递运单数据
		List<WKTfrBillEntity> fossXpressWaybillList = new ArrayList<WKTfrBillEntity>();// foss快递运单数据
		List<WKTfrBillEntity> expressAirportList = new ArrayList<WKTfrBillEntity>();// 快递机场扫面单号
		
		Long totalCount1=0L;//交接单条数
		Long totalCount2=0L;//运单条数
		Long totalCount3=0L;//派送单条数
		Long totalCount4=0L;//配载单条数
		/**wk快递运单条数    313352 gouyangyang*/
		Long totalCount5=0L;
		Long totalCount6=0L;//快递交接单条数
		/*****foss快递运单条数**************/
		Long totalCount7=0l;//wk
		Long totalCount8=0l;//快递机场扫面单号条数
		String createDept = StringUtils.isEmpty(rentalMarkDto.getCreateDept())?rentalMarkDto.getOrgCode():rentalMarkDto.getCreateDept();
		// 判断快递交接单信息是否为空  313352
		if ("2".equals(rentalMarkDto.getType())) {
			if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//如果按单号查询
				LOGGER.info("TFR-SCHEDULING 租车标记按单号查询开始");
				//根据单号查询总条数，为分页准备
				//快递交接单总条数 查询    gouyangyang   313352
				if(this.convertList(rentalMarkDto.getExpressBillNoList())==null){
					totalCount6=0L;
				}else if (configurationParamsService.queryTfrSwitch4Ecs()) {
						if(orgCodeList!=null&&orgCodeList.size()>0){
							rentalMarkDto.setOrgCodeList(orgCodeList);
						}
						totalCount6 = this.queryExpressMarkEntityCount(rentalMarkDto);//快递交接单dao的总条数(按日期和单号查询总条数)
					}
				//int start1 = (int)(totalCount6/limit);//快递交接单
				//快递交接单查询
				if(totalCount6>0){
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
					handoverBillList=temprentalMarkDAO.queryRentalDeliveryEntityListByBillNo(rentalMarkDto,limit,start);
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
				}
				/*****************开始查询快递运单******************************/ 
				if(this.convertList(rentalMarkDto.getExpressWaybillNoList())==null){
					totalCount5=0L;
					totalCount7=0l;
				}else if(totalCount6 < limit){//需要查询运单
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						//查询快递运单单号查询    gouyangyang 313352
						//拼接单号，传给快递
						ExpressDateDto inputDto = new ExpressDateDto();
						StringBuffer strbuf=null;
						List<String> list =rentalMarkDto.getExpressWaybillNoList();
						if (list!=null && list.size()>0){
							strbuf =new StringBuffer();
							for(int i=0;i<list.size();i++){
								if(i!=(list.size()-1)){
									strbuf.append(list.get(i)+",");
								}else{
									strbuf.append(list.get(i));
								}
							}
						}
						int start2=(start/limit-(int)(totalCount6/limit))*limit;//快递运单分页用
						inputDto.setWaybillNo(strbuf.toString());  //快递运单号
						inputDto.setPageSize(limit-handoverBillList.size());  // 页大小
						inputDto.setCurrentPageNo(start2+1); // 当前页数
						expressWaybillList= getExpressListInfo(inputDto,createDept);  //快递运单单号查询
						totalCount5 = (long) expressWaybillList.size();  // 返回快递运单的总条数
					}
					/**************开始查询FOSS快递运单***************/
					/*TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送单和运单所需参数
					tempRentalQueryDto.setOrgCode(orgCode);//部门编码
					tempRentalQueryDto.setCreateOrgCode(orgCode);//部门编码
					tempRentalQueryDto.setQueryType(InviteVehicleConstants.QBB);//查询类型，按日期或者单号
					tempRentalQueryDto.setWaybillNos(rentalMarkDto.getWayBillNoList());//运单号
//					//269701--lln--2015-09-06 begin
					tempRentalQueryDto.setExpressWaybillNos(rentalMarkDto.getExpressWaybillNoList());
//					//269701--lln--2015-09-06 end
					tempRentalQueryDto.setStart(0);//分页
					tempRentalQueryDto.setLimit(Integer.MAX_VALUE);
					totalCount7 = countExpressByWayBill(tempRentalQueryDto);
					//269701--lln--2015-09-06 begin
					if(totalCount6 + totalCount5 <limit){
						//快递运单接口 
						if(totalCount7 > 0){//如果快递运单条数大于0
							int start7 = (start / limit - (int)(totalCount6 + totalCount5) / limit);
							//设置分页
							tempRentalQueryDto.setStart(start7);//分页用
							tempRentalQueryDto.setLimit((int) (limit-totalCount6 + totalCount5));//设置limit
							LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息开始");
							List<RentalMarkEntity> expressList = searchExpressWaybill(tempRentalQueryDto, BILL_TYPE_WAYBILL);
							fossXpressWaybillList = fromFossEntityToWkEntity(expressList);
							LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息结束  expressList = " + expressList);
						}
					}*/
					//269701--lln--2015-09-06 end
					totalCount5 += totalCount7;
				}
				if(rentalMarkDto.getExpressAirportNoList().size()>0){   // 查询快递机场扫描单号
					AirExpressDateDto inputDto = new AirExpressDateDto();
					StringBuffer strbuf=null;
					List<String> list =rentalMarkDto.getExpressAirportNoList();
					if(list!=null && list.size()>0){
						strbuf =new StringBuffer();
						for(int i=0;i<list.size();i++){
							if(i!=(list.size()-1)){
								strbuf.append(list.get(i)+",");
							}else{
								strbuf.append(list.get(i));
							}
						}	
					}
					//int start2=(start/limit-(int)(totalCount8/limit))*limit;//快递机场单号分页
					inputDto.setWaybillNo(strbuf.toString());  // 快递机场单号
					inputDto.setPageSize(limit);  // 页大小
					//inputDto.setCurrentPageNo(start2); // 当前页数
					inputDto.setCurrentPageNo(start); // 当前页数
					expressAirportList = getWKExpressListInfo(inputDto,createDept);
					totalCount8 = (long)expressAirportList.size();
				}
				totalCount8 += totalCount7;
			} else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期查询
				LOGGER.info("TFR-SCHEDULING 租车标记按日期查询开始");
				TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();
				tempRentalQueryDto.setEndTime(rentalMarkDto.getBillGenerationEndTime());//单据结束时间
				tempRentalQueryDto.setTemprentalMarkNo(rentalMarkDto.getBorrowNo());//租车编号
				tempRentalQueryDto.setOrgCode(orgCode);//部门编码
				tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
				tempRentalQueryDto.setStartTime(rentalMarkDto.getBillGenerationBeginTime());//单据生成开始时间
				tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo());//车牌号
				tempRentalQueryDto.setCreateOrgCode(createDept);
				// 如果传入的是快递交接单
				if("Y".equals(rentalMarkDto.getIsHandoverEirBill()) && configurationParamsService.queryTfrSwitch4Ecs()){
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
					}
					totalCount6 = this.queryExpressEntityCount(rentalMarkDto);//交接单dao的总条数
				}
				//快递交接单
				if(totalCount6>0){//如果快递交接单条数大于0
					LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息开始");
					//快递交接单按日期查询
					handoverBillList=temprentalMarkDAO.queryListByDate(rentalMarkDto,limit,start);
					LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息结束");
				}
				int start2 = 0;
				if (handoverBillList.size()>0 && handoverBillList.size()<limit) {
					start2=(start/limit-(int)(totalCount6/limit))*limit;//快递运单分页用
				}
				// 快递运单
				if( "Y".equals(rentalMarkDto.getIsExpressWayBill()) && configurationParamsService.queryTfrSwitch4Ecs()){
					// 如果租车编号为空就进入
					if ("".equals(rentalMarkDto.getBorrowNo())) {
						// 快递运单按日期查询  313352 gouyangyang	
						ExpressDateDto inputDto = new ExpressDateDto();
						inputDto.setPageSize(limit - handoverBillList.size());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						inputDto.setBillGenerationBeginTime(sdf.format(rentalMarkDto.getBillGenerationBeginTime()));
						inputDto.setBillGenerationEndTime(sdf.format(rentalMarkDto.getBillGenerationEndTime()));
						inputDto.setOperatorDeptNo(createDept);  //登录部门编码
						inputDto.setWaybillNo("");
						inputDto.setCurrentPageNo(start/limit+1);
						expressWaybillList= getExpressListInfo(inputDto, createDept);
						tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo());//车牌号
						totalCount5 = Long.valueOf(this.getTotalNumber());  // 返回快递运单的总条数
					}
			}
				// 快递机场扫描单号
				if("Y".equals(rentalMarkDto.getIsAirPortBillList())){
					    // 如果租车编号为空就进入
						if("".equals(rentalMarkDto.getBorrowNo())){
							AirExpressDateDto inputDto = new AirExpressDateDto();
							inputDto.setPageSize(limit);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							inputDto.setBillGenerationBeginTime(sdf.format(rentalMarkDto.getBillGenerationBeginTime()));
							inputDto.setBillGenerationEndTime(sdf.format(rentalMarkDto.getBillGenerationEndTime()));
							inputDto.setOperatorDeptNo(createDept);  //登录部门编码
							inputDto.setWaybillNo("");
							inputDto.setCurrentPageNo(start);
							inputDto.setVehicleNo(rentalMarkDto.getVehicleNo());
							expressAirportList= getWKExpressListInfo(inputDto, createDept);
							tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo()); // 车牌号
							totalCount8 = Long.valueOf(this.getTotalNumber()); // 返回快递机场单号
						}
				}
				LOGGER.info("TFR-SCHEDULING 租车标记按日期查询结束");
			} 
			Long totalExpressCount = totalCount5+totalCount6;
			
			//快递交接单   
			if(handoverBillList!=null&&!handoverBillList.isEmpty()&&handoverBillList.size()>0){
				resultList.addAll(handoverBillList);
			}
			List<WKTfrBillEntity> expressMarkInfoList = null;
			//快递运单
			List<WKTfrBillEntity> airMarkInfoList = new ArrayList<WKTfrBillEntity>();
			for(WKTfrBillEntity entity : expressWaybillList){
				airMarkInfoList.add(entity);
			}
			// 快递机场扫描单
			for(WKTfrBillEntity entity : expressAirportList){
				airMarkInfoList.add(entity);
			}
			for (WKTfrBillEntity entity : airMarkInfoList) {
				List<String> expressWaybill = new ArrayList<String>();
				expressWaybill.add(entity.getHandoverBillNo());
				if (CollectionUtils.isNotEmpty(expressWaybill)) {
					expressMarkInfoList = temprentalMarkDAO.queryExpressWayBillMarkInfo(expressWaybill);
					try {
						WKTfrBillEntity wkTfrBillEntity = expressMarkInfoList.get(0);
						if (wkTfrBillEntity != null) {
							entity.setRentalNum(wkTfrBillEntity.getRentalNum());  //  租车编号 
							entity.setRentalUse(wkTfrBillEntity.getRentalUse()); //  租车用途
							entity.setCreateDate(wkTfrBillEntity.getCreateDate());  // 租车标记时间
							entity.setMarkDeptName(wkTfrBillEntity.getMarkDeptName());  // 租车标记部门
							entity.setMarkDeptCode(wkTfrBillEntity.getMarkDeptCode());  // 标记部门编码 
							entity.setMarkOperator(wkTfrBillEntity.getMarkOperator());  // 租车标记操作人 
							entity.setInviteVehicleNo(wkTfrBillEntity.getInviteVehicleNo());  //  约车编号
							entity.setRentalAmount(wkTfrBillEntity.getRentalAmount());  // 租车金额
							entity.setConsultPriceNo(wkTfrBillEntity.getConsultPriceNo()); // 询价编号
							entity.setVehicleNo(wkTfrBillEntity.getVehicleNo());  // 车牌号
							entity.setDriverName(wkTfrBillEntity.getDriverName());// 司机名称 
						}
					} catch (Exception e) {
						continue;
					}
				}
			}			
			resultList.addAll(expressWaybillList);
			resultList.addAll(fossXpressWaybillList);
			resultList.addAll(expressAirportList);
			if ("Y".equals(rentalMarkDto.getIsWayBill()) 
					|| "Y".equals(rentalMarkDto.getIsHandoverBill()) 
					|| "Y".equals(rentalMarkDto.getIsDeliverBill()) 
					|| "Y".equals(rentalMarkDto.getIsAirPortBillList())
					|| "Y".equals(rentalMarkDto.getIsStowageBill())){
				totalExpressCount=0L;
				}
			// 快递交接单map   
			queryMap.put("expressMarkEntityList", resultList);
			queryMap.put("totalCount", totalExpressCount);
			
			return queryMap;
		} else {/**
			 *分页逻辑：先显示交接单，然后运单，然后派送单
			 **/
			if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//如果按单号查询
				LOGGER.info("TFR-SCHEDULING 租车标记按单号查询开始");
				TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送但和运单所需参数
				//设置tempRentalQueryDto的值，用来查询运单和派送单
				tempRentalQueryDto.setDeliverbillNos(rentalMarkDto.getDeliverbillNoList());//派送单
				tempRentalQueryDto.setOrgCode(orgCode);//部门编码
				tempRentalQueryDto.setCreateOrgCode(orgCode);//部门编码
				tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
				tempRentalQueryDto.setWaybillNos(rentalMarkDto.getWayBillNoList());//零担运单号
				//269701--lln--2015-09-06 begin
				//快递运单号
				//tempRentalQueryDto.setExpressWaybillNos(rentalMarkDto.getExpressWaybillNoList());
				//269701--lln--2015-09-06 end
				//根据单号查询总条数，为分页准备
				//交接单总条数 查询
				if(this.convertList(rentalMarkDto.getHandoverBillNoList())==null){
					totalCount1=0L;
				}else{
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
					}
					 totalCount1 = this.queryRentalMarkEntityCount(rentalMarkDto);//交接单dao的总条数
				}
				//零担运单总条数 查询
				if(this.convertList(rentalMarkDto.getWayBillNoList())==null){
					totalCount2=0L;
				}else{
					totalCount2= countTempWayBillByBillNos(tempRentalQueryDto);//运单的总条数
				}
				//派送单总条数 查询
				if(this.convertList(rentalMarkDto.getDeliverbillNoList())==null){
					totalCount3=0L;
				}else{
					 totalCount3 = countByDeliverBillParams(tempRentalQueryDto);//派送单的总条数
				}
				//配载单总条数 查询
				if(this.convertList(rentalMarkDto.getStowageBillNoList())==null){//配载单的总条数
					totalCount4=0L;
				}else{
					totalCount4=this.queryStowageBillListCount(rentalMarkDto);
				}
				 
				//269701--lln--2015-09-06 begin
				//快递运单总条数 查询
				if(this.convertList(rentalMarkDto.getExpressWaybillNoList())==null){//快递运单的总条数
					totalCount5=0L;
				}else{
					//totalCount5= countExpressByWayBill(tempRentalQueryDto);
				}
				//269701--lln--2015-09-06 end
				//int start1 = (int)(totalCount1/limit);//交接单分页用
				int start2=(start/limit-(int)(totalCount1/limit))*limit;//零担运单分页用
				int start3 =(start/limit-(int)((totalCount1+totalCount2)/limit));//派送单分页
				int start4=(start/limit-(int)((totalCount1+totalCount2+totalCount3)/limit));//配载单分页
				//269701--lln--2015-09-06 begin
				//int start5=(start/limit-(int)((totalCount1+totalCount2+totalCount3+totalCount4)/limit))*limit;//快递运单分页用
				//269701--lln--2015-09-06 end
				
				//交接单查询
				if(totalCount1>0){
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
					handoverList=temprentalMarkDAO.queryRentalMarkEntityListByBillNo(rentalMarkDto,limit,start);//交接单
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
				}
				//零担运单查询
				if(handoverList.size()<limit){
					//零担运单接口 
					if(totalCount2>0){//如果零担运单条数大于0
						tempRentalQueryDto.setStart(start2);//分页用
						tempRentalQueryDto.setLimit(limit-handoverList.size());
						LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息开始");
						wayBillList=queryTempWaybillByBillNo(tempRentalQueryDto,"WAYBILL");
						LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息结束");
					}
				}
				//派送单查询
				if(wayBillList.size()+handoverList.size()<limit){
					//派送单接口 
					if(totalCount3>0){//如果派送单条数大于0
						tempRentalQueryDto.setStart(start3);//分页用
						tempRentalQueryDto.setLimit(limit-wayBillList.size()-handoverList.size());//设置limit
						LOGGER.info("TFR-SCHEDULING 租车标记查询派送单信息开始");
						deliverBillList=queryTempWaybillByDeliver(tempRentalQueryDto,"DELIVERBILL");//调用接送或的查询派送单接口
						LOGGER.info("TFR-SCHEDULING 租车标记查询派送单信息结束");
					}
				}
				//配载单查询
				if(wayBillList.size()+handoverList.size()+deliverBillList.size()<limit){
					//配载单接口 
					if(totalCount4>0){//如果配载单条数大于0
						
						LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息开始");
						stowageBillList=temprentalMarkDAO.queryStowageBillListByBillNo(rentalMarkDto,limit-wayBillList.size()-handoverList.size()-deliverBillList.size(),start4);//调用接送货的查询派送单接口
						LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息结束");
					}
				}
				
				//269701--lln--2015-09-06 begin
//				if(wayBillList.size()+handoverList.size()+deliverBillList.size()+stowageBillList.size()<limit){
//					//快递运单接口 
//					if(totalCount5>0){//如果快递运单条数大于0
//						//设置分页
//						tempRentalQueryDto.setStart(start5);//分页用
//						tempRentalQueryDto.setLimit(limit-wayBillList.size()-handoverList.size()-deliverBillList.size()-stowageBillList.size());//设置limit
//						LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息开始");
//						//expressList=searchExpressWaybill(tempRentalQueryDto,"WAYBILL");
//						LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息结束");
//					}
//				}
				//269701--lln--2015-09-06 end
				
				LOGGER.info("TFR-SCHEDULING 租车标记按单号查询结束");

			}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期查询
				LOGGER.info("TFR-SCHEDULING 租车标记按日期查询开始");
				TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送单和运单所需参数
				//设置tempRentalQueryDto的值，用来查询运单和派送单
				tempRentalQueryDto.setEndTime(rentalMarkDto.getBillGenerationEndTime());//单据结束时间
				tempRentalQueryDto.setTemprentalMarkNo(rentalMarkDto.getBorrowNo());//租车编号
				tempRentalQueryDto.setOrgCode(orgCode);//部门编码
				tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
				tempRentalQueryDto.setStartTime(rentalMarkDto.getBillGenerationBeginTime());//单据生成开始时间
				tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo());//车牌号
				tempRentalQueryDto.setCreateOrgCode(createDept);
				if("Y".equals(rentalMarkDto.getIsHandoverBill())){
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
					}
					totalCount1 = this.queryRentalMarkEntityCount(rentalMarkDto);//交接单dao的总条数
				}
				 if("Y".equals(rentalMarkDto.getIsWayBill())){
					 //判断租车编号是否为NULL，如果不为空，进零担运单表里面查询，否则就进租车标记表里面查询。
					 if("".equals(rentalMarkDto.getBorrowNo())){
						 totalCount2 = selectWayBillByDateCount(tempRentalQueryDto);//运单的总条数
					 }else{
						 totalCount2=0l;
					 }
				 }
				 if("Y".equals(rentalMarkDto.getIsDeliverBill())){
					 totalCount3 = countByDeliverBillParams(tempRentalQueryDto);//派送单的总条数
				 }
				 if("Y".equals(rentalMarkDto.getIsStowageBill())){
					 totalCount4 = this.queryStowageBillListCount(rentalMarkDto);//配载单的总条数
				 }
				int start1 = (int) (totalCount1 / limit);// 交接单分页用
				int start2 = (start / limit - start1) * limit;// 运单分页用
				int start3 =(start/limit-(int)((totalCount1+totalCount2)/limit));//派送单分页
				int start4=(start/limit-(int)((totalCount1+totalCount2+totalCount3)/limit));
			
				//查询交接单数据
				if(totalCount1>0){//如果交接单数据大于0
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
					handoverList=temprentalMarkDAO.queryRentalMarkEntityListByDate(rentalMarkDto,limit,start);
					LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
				}
				if(handoverList.size()<limit){//如果运单数据大于0
					//运单接口 
					tempRentalQueryDto.setStart(start2-totalCount1.intValue());//分页用
					tempRentalQueryDto.setLimit(limit);
					 if(totalCount2>0){
						 LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息开始");
						 wayBillList=queryTempWaybillByDate(tempRentalQueryDto,"WAYBILL");//运单
						 LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息结束");
					 }
					
					 
				}
				if(wayBillList.size()+handoverList.size()<limit){
					//派送单接口 
					tempRentalQueryDto.setStart(start3);//分页用
					tempRentalQueryDto.setLimit(limit-wayBillList.size()-handoverList.size());
					 if(totalCount3>0){//如果派送单数据大于0
						 LOGGER.info("TFR-SCHEDULING 租车标记派送单信息开始");
						 deliverBillList=queryTempWaybillByDeliver(tempRentalQueryDto,"DELIVERBILL");//派送单
						 LOGGER.info("TFR-SCHEDULING 租车标记查询派送信息结束");
					 }
				}
				if(wayBillList.size()+handoverList.size()+deliverBillList.size()<limit){
					//配载单
					if(totalCount4>0){//如果派送单条数大于0
						
						LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息开始");
						stowageBillList=temprentalMarkDAO.queryStowageBillListByDate(rentalMarkDto,limit-wayBillList.size()-handoverList.size()-deliverBillList.size(),start4);//调用接送或的查询派送单接口
						LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息结束");
					}
				}
				LOGGER.info("TFR-SCHEDULING 租车标记按日期查询结束");
			}else{
				
				return null;
			}
			Long totalCount = totalCount1+totalCount2+totalCount3+totalCount4+totalCount5;
			if(handoverList!=null&&!handoverList.isEmpty()&&handoverList.size()>0){
				arrayList.addAll(handoverList);
			}
			if(wayBillList!=null&&!wayBillList.isEmpty()&&wayBillList.size()>0){
				arrayList.addAll(wayBillList);
			}
			if(deliverBillList!=null&&!deliverBillList.isEmpty()&&deliverBillList.size()>0){
				arrayList.addAll(deliverBillList);
			}
			if(stowageBillList!=null&&!stowageBillList.isEmpty()&&stowageBillList.size()>0){
				arrayList.addAll(stowageBillList);
			}
			// 零担tab切换清空总条数
			if ( "Y".equals(rentalMarkDto.getIsHandoverEirBill()) ||
					"Y".equals(rentalMarkDto.getIsExpressWayBill())) {
				totalCount=0L;
			}
			queryMap.put("rentalMarkEntityList", arrayList);
			queryMap.put("totalCount", totalCount);
			return queryMap;
		}
	}
	
	/**
	 * 313352:将RentalMarkEntity中的页面必要字段转为WKTfrBillEntity
	 * @param expressList
	 * @author 313352   gyy
	 * @return List<WKTfrBillEntity> 
	 */
	@SuppressWarnings("unused")
	private List<WKTfrBillEntity> fromFossEntityToWkEntity(
			List<RentalMarkEntity> expressList) {
		//RentalMarkEntity renEntity = new RentalMarkEntity();
		List<WKTfrBillEntity> list = new ArrayList<WKTfrBillEntity>();
		for(RentalMarkEntity trans:expressList){
			WKTfrBillEntity wkBillEntity  = new WKTfrBillEntity();
			wkBillEntity.setHandoverBillNo(trans.getBillNo()); //单号
			wkBillEntity.setHandoverType(trans.getBillType()); //单据类型
			wkBillEntity.setVehicleNo(trans.getVehicleNo());   // 车牌号
			wkBillEntity.setDriverName(trans.getDriverName()); //司机
			wkBillEntity.setDepartOrgName(trans.getOrigOrgName());   // 出发部门
			wkBillEntity.setArriveOrgName(trans.getDestOrgName());	 // 到达部门
			wkBillEntity.setTotalWeight(trans.getWeight());	 // 重量
			wkBillEntity.setTotalVolumn(trans.getVolume());	 // 体积
			wkBillEntity.setSendAddress(trans.getSendAddress());  // 发货地址
			wkBillEntity.setDestination(trans.getDestination()); // 目的站
			wkBillEntity.setPickUpWayName(trans.getPickUpWay()); // 提货方式 
			wkBillEntity.setReceiptContacts(trans.getReceiptContacts()); // 收货联系人  receiptAddress
			wkBillEntity.setReceiptAddress(trans.getReceiptAddress()); // 收货地址  
			wkBillEntity.setCreateTime(trans.getBillCreateTime()); // 单据生成时间  
			list.add(wkBillEntity);
		}
		return list;
	}
	
	/**
	 * 根据条件查临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return Long
	 */
	public Long countExpressByWayBill(TempRentalQueryDto tempRentalQueryDto) {
		//返回结果
		long count = 0;
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isEmpty(orgCode)){
			return count;
		}
		
		getType(args, tempRentalQueryDto, BILL_TYPE_WAYBILL);
		args.put("billList", tempRentalQueryDto.getExpressWaybillNos());
		if (args.isEmpty()){
			return count;
		}
		
		if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) && CollectionUtils.isNotEmpty(tempRentalQueryDto.getExpressWaybillNos())) {
			count= temprentalMarkDAO.countExpressByWayBill(args);
		} else {
			return count;
		}
		return count;
	}
	
	
	/**
	 * 查询快递运单号信息快递运单
	 * @param billType  WAYBILL:按运单号查  
	 */
	public List<RentalMarkEntity>  searchExpressWaybill(TempRentalQueryDto tempRentalQueryDto, String billType){
		//返回结果集
		List<RentalMarkEntity> resultList= new ArrayList<RentalMarkEntity>();
		Map<Object, Object> maps = new HashMap<Object, Object>();

		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isEmpty(orgCode)){
			return resultList;
		}
		
		//1. 按快递运单查询
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			//2. 参数赋值
			getType(maps, tempRentalQueryDto, billType);
			
			if (maps.isEmpty()){
				return resultList;
			}
			
			int type = (Integer) maps.get("type");
			int start = tempRentalQueryDto.getStart();
			int limit = tempRentalQueryDto.getLimit();
			//3.按快递单号查询运单 并且运单信息不为空
			 if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) && CollectionUtils.isNotEmpty(tempRentalQueryDto.getExpressWaybillNos())) {
					//车队/外场/营业部/驻地营业部
					 if(type==1||type==2||type==ConstantsNumberSonar.SONAR_NUMBER_3||type==ConstantsNumberSonar.SONAR_NUMBER_4){
						 resultList = temprentalMarkDAO.queryExpressBillByBill(maps, start, limit); 
					 }
					 return resultList;	
			 }
		 	
		} 
		return resultList;
	}
	
	/**
	 * 查询临时租车信息--按单号查询
	 * @param TempRentalQueryDto
	 * @param billType  WAYBILL:按运单号查   
	 */
	public List<RentalMarkEntity> queryTempWaybillByBillNo (
			TempRentalQueryDto tempRentalQueryDto, String billType) {
		//1. 返回结果
		List<RentalMarkEntity> resultList=new ArrayList<RentalMarkEntity>();
		//2.如果是按日期查询--返回空
		if(QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())){
			return resultList;
		}else{
			//3. 按照单号查询，如果查询类型不是零担运单--返回空
			if(!BILL_TYPE_WAYBILL.equals(billType)){
				return resultList;
			}else{
				//4. 按照零担运单查询
				String orgCode = tempRentalQueryDto.getOrgCode();
					//5. 当前登录部门为空 或者运单号为空时--返回空
					if (StringUtils.isNotEmpty(orgCode) || (!tempRentalQueryDto.getWaybillNos().isEmpty() 
							&& tempRentalQueryDto.getWaybillNos().size() > 0)){
						
						Map<Object, Object> maps = new HashMap<Object, Object>();
						//6.参数赋值
						getType(maps, tempRentalQueryDto, billType);
						if (maps.isEmpty()) {
							return resultList;
						}
						
						int type = (Integer) maps.get("type");
						int start = tempRentalQueryDto.getStart();
						int limit = tempRentalQueryDto.getLimit();
						//营业部/驻地营业部
						if (type == 1 || type == 2) {
							resultList = temprentalMarkDAO.querySaleDepartTempRentalWayBillByBill(maps, start, limit);
							//车队/转运场
						} else if (type == ConstantsNumberSonar.SONAR_NUMBER_3 || type == ConstantsNumberSonar.SONAR_NUMBER_4) {
							resultList = temprentalMarkDAO.queryMotorcadeTempRentalWayBillByBill(maps, start, limit);
						}
						return resultList;
					}else{
						return resultList;
					}
			}
		}
		
	}
	
	/**
	 * 查临时租车派送单总条数
	 * 按日期查询和按派送单号查询
	 * @param tempRentalQueryDto
	 */
	private Long countByDeliverBillParams(TempRentalQueryDto tempRentalQueryDto) {
		Long count=new Long(0);
				
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isEmpty(orgCode)){
			return count;
		}
		//1. 参数赋值	
		getType(args, tempRentalQueryDto, BILL_TYPE_DELIVERBILL);
		//2.参数为空-返回空
		if (args.isEmpty()){
			return count;
		}
		//3. 按日期查询 派送单总条数
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			count= temprentalMarkDAO.countTempRentalDeliverBillByDate(args);
		//4. 按单号查询 派送单总条数
		} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType())
				&& !tempRentalQueryDto.getDeliverbillNos().isEmpty() 
				&& tempRentalQueryDto.getDeliverbillNos().size() > 0) {
			count= temprentalMarkDAO.countTempRentalDeliverBillByBillNos(args);
		} else {
			return count;
		}
		return count;
	}
	
	/**
	 * 查询临时租车信息--按单号查询
	 * @param TempRentalQueryDto
	 * @param billType  DELIVERBILL:按派送单查  
	 * @return
	 */
	public List<RentalMarkEntity> queryTempWaybillByDeliver (
			TempRentalQueryDto tempRentalQueryDto, String billType) {
		//1. 返回结果
		List<RentalMarkEntity> resultList=new ArrayList<RentalMarkEntity>();
		
		Map<Object, Object> maps = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		//当前登录部门为空--返回空
		if(StringUtils.isEmpty(orgCode)){
			return resultList;
		}
		//1.参数赋值
		getType(maps, tempRentalQueryDto, billType);
		if (maps.isEmpty()) {
			return resultList;
		}
		
		int dtype = (Integer) maps.get("type");
		int start = tempRentalQueryDto.getStart();
		int limit = tempRentalQueryDto.getLimit();
		
		//2.按日期查询-派送单
		if(QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())){
			//查询派送单
			if(BILL_TYPE_DELIVERBILL.equals(billType)){
				if (dtype == 1 || dtype == 2) {//营业部或驻地营业部
					resultList= temprentalMarkDAO.querySaleDepartTempRentalDeliverBillByDate(maps, start, limit);
				} else if (dtype == ConstantsNumberSonar.SONAR_NUMBER_3 || dtype == ConstantsNumberSonar.SONAR_NUMBER_4) {//车队或上级部门为车队
					resultList= temprentalMarkDAO.queryMotorcadeTempRentalDeliverBillByDate(maps, start, limit);
				} 
			}else{
				//返回空
				return  resultList;
			}
			return  resultList;
		
		}else{
			//按照单号查询
			//3.如果不是按派送单号 查询--返回空
			if(!BILL_TYPE_DELIVERBILL.equals(billType)){
				return resultList;
			}else{
				//4. 按派送单号查询
					if (!tempRentalQueryDto.getDeliverbillNos().isEmpty() && tempRentalQueryDto.getDeliverbillNos().size() > 0){
						//营业部/驻地营业部
						if (dtype == 1 || dtype == 2) {
							resultList = temprentalMarkDAO.querySaleDepartTempRentalDeliverBillByBillNos(maps, start, limit);
							//车队/转运场
						} else if (dtype == ConstantsNumberSonar.SONAR_NUMBER_3 || dtype == ConstantsNumberSonar.SONAR_NUMBER_4) {
							resultList = temprentalMarkDAO.queryMotorcadeTempRentalDeliverBillByBillNos(maps, start, limit);
						}
						return resultList;
					}else{
					// 派送单号为空时--返回空
						return resultList;
				}
			}
		}
		
	}
	/**
	 * 根据单号查临时租车运单总条数--已标记和未标记均被查出
	 * @param tempRentalQueryDto
	 *该查询原本放在接送货pkp-waybill，现为了优化sql移至查询至中转
	 *@author 269701--lln
	 *@date 2016-04-15 上午 10:10:10
	 *@param TempRentalQueryDto tempRentalQueryDto
	 *@return Long
	 */
	private Long countTempWayBillByBillNos(TempRentalQueryDto tempRentalQueryDto) {
		//返回结果
		Long count=new Long(0);
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isEmpty(orgCode)){
			return count;
		}
		//1.参数转换
		getType(args, tempRentalQueryDto, BILL_TYPE_WAYBILL);
		
		if (args.isEmpty()){
			return count;
		}
		//2.如果运单为空 则不进行查询 返回查询结果
		if(!tempRentalQueryDto.getWaybillNos().isEmpty()&& tempRentalQueryDto.getWaybillNos().size() > 0){
			System.out.println(tempRentalQueryDto.getWaybillNos());
			System.out.println(args.get("billList"));
			 count=temprentalMarkDAO.countTempWayBillByBillNos(args);
			 //if-else中内容相同 -352203
/*			if(null!=count){
				return count;
			}else{
				return count;
			}*/
		}/*else{
			//3. 查询运单为空 不进行查询 返回空
			return count;
		}*/
		return count;
		
		
	}
	/**
	 * 根据日期查询租车标记
	 * 查询运单
	 *该查询原本放在接送货pkp-waybill，现为了优化sql移至查询至中转
	 *@author 269701--lln
	 *@date 2016-03-22 上午 10:10:10
	 *@param TempRentalQueryDto String
	 *@return List<RentalMarkEntity>
	 */
	public List<RentalMarkEntity> queryTempWaybillByDate(
			TempRentalQueryDto tempRentalQueryDto, String billType) {

		List<RentalMarkEntity> resultList = new ArrayList<RentalMarkEntity>();

		//按照运单查询--返回空
		if (!QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType()) ) {
			return resultList;
		}else{
			//按照日期查询
			//如果查询类型不是 运单 则返回空
			if(!BILL_TYPE_WAYBILL.equals(billType)){
				return resultList;
			}else{
				
				String orgCode = tempRentalQueryDto.getOrgCode();
				if (StringUtils.isEmpty(orgCode)) {
					return resultList;
				}
				
				Map<Object, Object> maps = new HashMap<Object, Object>();
				getType(maps, tempRentalQueryDto, billType);

				if (maps.isEmpty()) {
					return resultList;
				}

				// 269701--2016-03-31--begin
				// 临时租车标记 按日期查询sql性能优化
				// 首先根据 界面查询条件，查询运单表以及运单承运表--得到对应的运单信息
				// 按时间段查询运单
				int type = (Integer) maps.get("type");
				int start = tempRentalQueryDto.getStart();
				int limit = tempRentalQueryDto.getLimit();
				//如果车牌号或者租车标记号不为为空--查询已标记的信息
				if(StringUtils.isNotEmpty(tempRentalQueryDto.getVehicleNo())
						||StringUtils.isNotEmpty(tempRentalQueryDto.getTemprentalMarkNo())){
					//查询已标记的租车信息
					resultList = temprentalMarkDAO.queryMarkedWayBillByDate(maps, start, limit);
						//根据查询的租车标记信息，查询运单表 获取该运单的信息
						for(RentalMarkEntity entity:resultList){
							//根据运单号查询 运单信息
							RentalMarkEntity wayBillInfo=temprentalMarkDAO.queryMarkedWayBillInfo(entity.getBillNo(),tempRentalQueryDto.getCreateOrgCode());
							if(null!=wayBillInfo){
								//出发部门
								entity.setOrigOrgCode(wayBillInfo.getOrigOrgCode());//出发部门编码
								entity.setOrigOrgName(wayBillInfo.getOrigOrgName());
								//到达部门
								entity.setDestOrgCode(wayBillInfo.getDestOrgCode());
								entity.setDestOrgName(wayBillInfo.getDestOrgName());
								// 重量
								entity.setWeight(wayBillInfo.getWeight());
								// 体积
								entity.setVolume(wayBillInfo.getVolume());
								// 货物名称
								entity.setGoodsName(wayBillInfo.getGoodsName());
								// 包装
								entity.setPacking(wayBillInfo.getPacking());
								// 是否上门接货
								entity.setIsDoorDeliver(wayBillInfo.getIsDoorDeliver());
								// 发货客户名称
								entity.setCustomerName(wayBillInfo.getCustomerName());
								// 发货地址
								entity.setSendAddress(wayBillInfo.getSendAddress());
								// 目的站
								entity.setDestination(wayBillInfo.getDestination());
								// 提货方式
								entity.setPickUpWay(wayBillInfo.getPickUpWay());
								//提货方式名称
								entity.setPickUpWayName(wayBillInfo.getPickUpWayName());
								// 收货联系人
								entity.setReceiptContacts(wayBillInfo.getReceiptContacts());
								// 收货地址
								entity.setReceiptAddress(wayBillInfo.getReceiptAddress());
								// 单据生成时间
								entity.setBillCreateTime(wayBillInfo.getBillCreateTime());
								//单据类型
								entity.setBillType(wayBillInfo.getBillType());
								//是否是快递
								entity.setIswaybill(wayBillInfo.getIswaybill());
							}else{
								//查询已标记数据--对应运单信息为空--说明该运单被作废等；不能查出
								resultList = new ArrayList<RentalMarkEntity>();
								return resultList;
							}
						}
						return resultList;
				}else{
					if (type == 1 || type == 2) {
						resultList = temprentalMarkDAO
								.querySaleDepartTempRentalWayBillByDate(maps, start, limit);
					} else if (type == ConstantsNumberSonar.SONAR_NUMBER_3 || type == ConstantsNumberSonar.SONAR_NUMBER_4) {
						resultList = temprentalMarkDAO.queryMotorcadeTempRentalWayBillByDate(maps, start, limit);
					}

					for (RentalMarkEntity index : resultList) {
						// 车牌号
						String vehicleNo = null;
						// 司机
						String driverName = null;
						// 租车编号
						String rentalNum = null;
						// 租车用途
						String rentalUse = null;
						// 租车标记部门
						String markDeptName = null;
						// 租车标记操作人
						String markOperator = null;
						// 租车标记时间
						Date createDate = null;
						//提货方式名称
						String pickUpWayName = null;
						//约车编号
						String inviteVehicleNo = null;
						//租车金额
						BigDecimal rentalAmount = null;
						//询价编号
						String consultPriceNo = null;
						index.setRentalNum(tempRentalQueryDto.getTemprentalMarkNo());
						index.setVehicleNo(tempRentalQueryDto.getVehicleNo());
						RentalMarkEntity tempResult = temprentalMarkDAO
								.selectWayBillByDate(index);
						if (tempResult != null) {
							vehicleNo = tempResult.getVehicleNo();
							driverName = tempResult.getDriverName();
							rentalNum = tempResult.getRentalNum();
							rentalUse = tempResult.getRentalUse();
							markDeptName = tempResult.getMarkDeptName();
							markOperator = tempResult.getMarkOperator();
							createDate = tempResult.getCreateDate();
							pickUpWayName = tempResult.getPickUpWayName();
							inviteVehicleNo = tempResult.getInviteVehicleNo();
							rentalAmount = tempResult.getRentalAmount();
							consultPriceNo = tempResult.getConsultPriceNo();

						}
						index.setVehicleNo(vehicleNo);// 车牌号
						index.setDriverName(driverName);// 司机
						index.setRentalNum(rentalNum);// 租车编号
						index.setRentalUse(rentalUse);	// 租车用途
						index.setMarkDeptName(markDeptName);// 租车标记部门
						index.setMarkOperator(markOperator);// 租车标记操作人
						index.setCreateDate(createDate);// 租车标记时间
						index.setPickUpWayName(pickUpWayName);//提货方式名称
						index.setInviteVehicleNo(inviteVehicleNo);//约车编号
						index.setRentalAmount(rentalAmount);//租车金额
						index.setConsultPriceNo(consultPriceNo);//询价编号
					}

				}
				
				return resultList;

			}
		}

		
	}
	/**
	 * 获取操作者部门的数据权限类别，即：营业部、驻地营业部、车队（及下属部门）、外场（及下属部门）所以查询的数据权限不一样
	 * @param maps
	 * @param tempRentalQueryDto
	 * @param billType
	 * @author 269701--lln
	 * @date 2016-03-22
	 */
	private void getType(Map<Object, Object> maps, TempRentalQueryDto tempRentalQueryDto, String billType) {
		String orgCode = tempRentalQueryDto.getOrgCode();
		Date now = Calendar.getInstance().getTime();
		int limit = tempRentalQueryDto.getLimit();
		if (limit <= 0)
			tempRentalQueryDto.setLimit(ConstantsNumberSonar.SONAR_NUMBER_50);
		
		maps.put("currentOrg", orgCode);
		
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			if (tempRentalQueryDto.getStartTime() != null) {
				maps.put("startTime", tempRentalQueryDto.getStartTime());
			} else {
				maps.put("startTime", getNextDay(now));//为空则置为系统日期的前一天
			}
				
			if (tempRentalQueryDto.getEndTime() != null) {
				maps.put("endTime", tempRentalQueryDto.getEndTime());
			} else {
				maps.put("endTime", now);//为空则置为系统日期
			}
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getTemprentalMarkNo())) {
				maps.put("TemprentalMarkNo", tempRentalQueryDto.getTemprentalMarkNo());
			}
				
			if (StringUtils.isNotBlank(tempRentalQueryDto.getVehicleNo()))
				maps.put("vehicleNo", tempRentalQueryDto.getVehicleNo());
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getCreateOrgCode()))
				maps.put("createOrgCode", tempRentalQueryDto.getCreateOrgCode());
		}
		
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			
			deal(tempRentalQueryDto,maps,orgCode);
			
			if (maps.isEmpty()){
				return;
			}
				
			//运单
			maps.put("billList", tempRentalQueryDto.getWaybillNos());
			
		}else if(BILL_TYPE_DELIVERBILL.equals(billType)){
			deal(tempRentalQueryDto,maps,orgCode);
			
			if (maps.isEmpty()){
				return;
			}
			//派送单
			maps.put("billList", tempRentalQueryDto.getDeliverbillNos());
		} else {
			maps.clear();
		}
	}

	/**
	 * 根据日期查询条件查临时租车运单总条数
	 * @param tempRentalQueryDto
	 *该查询原本放在接送货pkp-waybill，现为了优化sql移至查询至中转
	 *@author 269701--lln
	 *@date 2016-03-22 上午 10:10:10
	 *@param TempRentalQueryDto tempRentalQueryDto
	 *@return Long
	 */
	public Long selectWayBillByDateCount(TempRentalQueryDto tempRentalQueryDto) {
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isEmpty(orgCode)){
			return 0l;
		}
			
		//参数转换
		getType(args, tempRentalQueryDto, BILL_TYPE_WAYBILL);
		
		if (args.isEmpty()){
			return 0l;
		}
		//如果车牌号或者租车标记号不为为空--查询已标记的信息
		if(StringUtils.isNotEmpty(tempRentalQueryDto.getVehicleNo())
				||StringUtils.isNotEmpty(tempRentalQueryDto.getTemprentalMarkNo())){
			//租车编号
			args.put("TemprentalMarkNo", tempRentalQueryDto.getTemprentalMarkNo());
			//车牌号
			args.put("vehicleNo", tempRentalQueryDto.getVehicleNo());
			
			Long count=temprentalMarkDAO.selectMarkedWayBillByDateCount(args);
			if(null!=count){
				return count;
			}else{
				return 0l;
			}
		}else{
			Long count=temprentalMarkDAO.selectWayBillByDateCount(args);
			if(null!=count){
				return count;
			}else{
				return 0l;
			}
		}
		
		
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param date
	 * @return
	 */
	public Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	private void deal (TempRentalQueryDto tempRentalQueryDto, Map<Object, Object> maps, String orgCode) {
		List<String> orgs = new ArrayList<String>();
		
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if (orgAdministrativeInfoEntity == null) {
			maps.clear();
			return;
		}
		maps.put("entity", orgAdministrativeInfoEntity);//不做数据库查询参数用
		//登陆部门为营业部
		if (orgAdministrativeInfoEntity.checkSaleDepartment()) {
			maps.put("type", ONE);
			//是否驻地营业部
			SaleDepartmentEntity saleDepartmentEntity = 
					saleDepartmentService.querySimpleSaleDepartmentByCode(orgCode);
			
			if (saleDepartmentEntity != null && YES.equals(saleDepartmentEntity.getStation()) 
					&& YES.equals(saleDepartmentEntity.getActive())) {//登陆部门为驻地营业部
				maps.put("type", TWO);
			}
		} else if (orgAdministrativeInfoEntity.checkTransDepartment() 
					|| orgAdministrativeInfoEntity.checkTransferCenter()) {//登陆部门为车队或者外场
			this.dealMotorcadeAndTransDepart(orgAdministrativeInfoEntity, maps, orgs);
		} else {//登陆部门为车队或外场的下属部门，如果都不是则不进行查询
			
			List<String> bizTypes =new ArrayList<String>();
            bizTypes.add("TRANS_DEPARTMENT");
            //查询所属上级部门是否是个车队
            OrgAdministrativeInfoEntity superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
			if (superOrg == null) {
				bizTypes.clear();
				bizTypes.add("TRANSFER_CENTER");
				superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
				if (superOrg == null) {
					maps.clear();
					return;
				}
			}
			maps.put("parentEntity", superOrg);//不做数据库查询参数用
			
			if (superOrg.checkTransDepartment() 
					|| superOrg.checkTransferCenter()) {
				this.dealMotorcadeAndTransDepart(superOrg, maps, orgs);
			} else {
				//该登陆部门无权限操作
				maps.clear();
			}
		}
	}
	/**
	 * 处理车队与外场
	 * @param orgEntity
	 * @param maps
	 * @param orgs
	 */
	private void dealMotorcadeAndTransDepart (OrgAdministrativeInfoEntity orgEntity,Map<Object, Object> maps, List<String> orgs) {
		if (orgEntity.checkTransDepartment()) {//车队
			
			//1.查询车队的顶级车队所管辖的营业部（包括其本身）
			//首先查询本身是否顶级车队
			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(orgEntity.getCode());
			OrgAdministrativeInfoEntity topFleet = new OrgAdministrativeInfoEntity();
			if (motorcade != null) {
				//如果本身就是顶级车队
				if (StringUtils.equals(motorcade.getIsTopFleet(), FossConstants.YES)) {
					topFleet.setCode(motorcade.getCode());
				} else {
					//--查询顶级车队
					topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgEntity.getCode());
					if (topFleet == null) {
						maps.clear();
						return;
					}
				}
			} else {
				//--查询顶级车队
				topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgEntity.getCode());
				if (topFleet == null) {
					maps.clear();
					return;
				}
			}
			
			//--查询顶级车队所管辖的营业部（包括其本身）
			SalesMotorcadeEntity topEntity = new SalesMotorcadeEntity();
			topEntity.setMotorcadeCode(topFleet.getCode());
			//查询顶级车队所管辖的营业部
			List<SalesMotorcadeEntity> salesDeparts = salesMotorcadeService.querySalesMotorcadeExactByEntity(topEntity, NO_ROW_OFFSET, NO_ROW_LIMIT);
			if (salesDeparts.isEmpty() || salesDeparts.size() == 0) {
				maps.clear();
				return;
			}
			//--所有营业部
			orgs = getSalesDepartCodes(salesDeparts);
			//--把顶级车队加进去
			orgs.add(topFleet.getCode());
			
			//2.查询当前车队所有子部门的数据
			List<OrgAdministrativeInfoEntity> allSubDeparts = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode (topFleet.getCode());
			
			if (allSubDeparts != null && allSubDeparts.size() > 0) {
				List<String> allSubOrgIds = getOrgAdministrativeInfoCodes(allSubDeparts);
				orgs.addAll(allSubOrgIds);
			}
			
			if (orgs.isEmpty() || orgs.size() == 0) {
				maps.clear();
				return;
			}
			maps.put("type", THREE);//登陆部门为车队
			maps.put("orgs", orgs);
		} else {//外场
			//查询当前外场所有子部门的数据
			List<OrgAdministrativeInfoEntity> allSubDeparts = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode (orgEntity.getCode());
			
			if (allSubDeparts != null && allSubDeparts.size() > 0) {
				List<String> allSubOrgIds = getOrgAdministrativeInfoCodes(allSubDeparts);
				orgs.addAll(allSubOrgIds);
			}
			if (orgs.isEmpty() || orgs.size() == 0) {
				maps.clear();
				return;
			}
			maps.put("type", FOUR);//登陆部门为外场
			maps.put("orgs", orgs);
		}
	}
	/**
	 * 获取所有营业部
	 * @param entities
	 * @return
	 */
	private List<String> getSalesDepartCodes(List<SalesMotorcadeEntity> entities) {
		List<String> ids = new ArrayList<String>();
		for (SalesMotorcadeEntity entity : entities) {
			if (StringUtils.isNotBlank(entity.getSalesdeptCode()))
				ids.add(entity.getSalesdeptCode());
		}
		return ids;
	}
	/**
	 * 获取车队子部门数据
	 * @param entities
	 * @return
	 */
	private List<String> getOrgAdministrativeInfoCodes(List<OrgAdministrativeInfoEntity> entities) {
		List<String> ids = new ArrayList<String>();
		for (OrgAdministrativeInfoEntity entity : entities) {
			if (StringUtils.isNotBlank(entity.getCode()))
				ids.add(entity.getCode());
		}
		return ids;
	}
	/**

	 * 把空字符串数组变成null
	 *@author 205109 zenghaibin
	 *@date 2014/08/19 09:42
	 *@param List<String>
	 ***/
	private List<String> convertList(List<String> list){
		if(list.size()==1){
			if("".equals(list.get(0).trim())){
				list=null;
			}
		}
		return list;
		
	}
	
	/**
	 *查询交接单总条数 
	 *@author zenghaibin
	 *@date 2014-5-30 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	@Override
	public Long queryRentalMarkEntityCount(RentalMarkDto rentalMarkDto) {
		String departmentSignle=rentalMarkDto.getDepartmentSignle();
		List<String> orgCodeList=rentalMarkDto.getOrgCodeList();		//查询数据的数目
		if(departmentSignle==null||departmentSignle.equals("")){
			return 0L;
		}
		if((null==orgCodeList||orgCodeList.size()==0||orgCodeList.isEmpty())&&departmentSignle.equals("Profdepartment")){//如果营业部为空则返回空，做查询
			return 0L;
		}
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//按单号
			
			return temprentalMarkDAO.queryRentalMarkEntityCountByBillNo(rentalMarkDto);
			
		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期
			
			return temprentalMarkDAO.queryRentalMarkEntityCountByDate(rentalMarkDto);
		
		}
		return null;
	}
	
	
	/**
	 *查询快递交接单总条数   
	 *@author gouyangyang
	 *@date 2016-5-13 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	@Override
	public Long queryExpressEntityCount(RentalMarkDto rentalMarkDto) {
		String departmentSignle=rentalMarkDto.getDepartmentSignle();
		List<String> orgCodeList=rentalMarkDto.getOrgCodeList();		//查询数据的数目
		if(departmentSignle==null||departmentSignle.equals("")){
			return 0L;
		}
		if((null==orgCodeList||orgCodeList.size()==0||orgCodeList.isEmpty())&&departmentSignle.equals("Profdepartment")){//如果营业部为空则返回空，做查询
			return 0L;
		}
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//按单号
			return temprentalMarkDAO.queryRentalMarkCountByBillNo(rentalMarkDto);
			
		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期
			return temprentalMarkDAO.queryRentalAREntityCountByDate(rentalMarkDto);
		
		}
		return null;
	}
	
	

	/**
	 *查询快递交接单总条数 
	 *@author gouyangyang
	 *@date 2016-5-12 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	@Override
	public Long queryExpressMarkEntityCount(RentalMarkDto rentalMarkDto) {
		String departmentSignle=rentalMarkDto.getDepartmentSignle();
		List<String> orgCodeList=rentalMarkDto.getOrgCodeList();		//查询数据的数目
		if(departmentSignle==null||departmentSignle.equals("")){
			return 0L;
		}
		if((null==orgCodeList||orgCodeList.size()==0||orgCodeList.isEmpty())&&departmentSignle.equals("Profdepartment")){//如果营业部为空则返回空，做查询
			return 0L;
		}
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//按单号
			return temprentalMarkDAO.queryExpressEntityCountByBillNo(rentalMarkDto);
			
		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期
			return temprentalMarkDAO.queryEntityCountByDate(rentalMarkDto);
		
		}
		return null;
	}

	
	/**
	 *查询配载单总条数 
	 *@author zenghaibin
	 *@date 2014-9-21 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	@Override
	public Long queryStowageBillListCount(RentalMarkDto rentalMarkDto) {
		//查询数据的数目
		
		String departmentSignle = rentalMarkDto.getDepartmentSignle();
		if(!departmentSignle.equals("TransferChild")&&!departmentSignle.equals("TransferCenter")){
			
			return 0L;
		}
		if(rentalMarkDto==null||rentalMarkDto.getOrgCode()==null||rentalMarkDto.getOrgCode().equals("")){
			return 0L;
		}
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//按单号
			
			return temprentalMarkDAO.queryStowageBillCountByBillNo(rentalMarkDto);
			
		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期
			
			return temprentalMarkDAO.queryStowageBillCountByDate(rentalMarkDto);
		
		}
		return null;
	}
	/**
	*检查约车编号
	*@author zenghaibin
	*@date 2014-6-11  10:10:10
	*@param inviteVehicleNo
	*@return RentalMarkVo
	**/
	@Override  
	public RentalMarkVo queryInviteVehicleNo(RentalMarkVo rentalMarkVo) {
	 if(rentalMarkVo == null){
		 throw new TfrBusinessException("数据参数为空");
	 }
	 String inviteVehicleNo = rentalMarkVo.getInviteVehicleNo();
	 //获取零担运单标记数
	 long wayBillCount = this.queryWayBillCountByMark(rentalMarkVo);
	 RentalMarkVo resultVo=new RentalMarkVo();
	 RentalMarkVo result=null;
		if(inviteVehicleNo!=null&&!inviteVehicleNo.equals("")){
			result = temprentalMarkDAO.queryInviteVehicleNo(inviteVehicleNo);
		}
		if(result==null){
			resultVo.setAcceptPerson("");
			resultVo.setAcceptPersonCode("");
			resultVo.setInviteVehicleNo("");
			resultVo.setInviteState("");
			resultVo.setRentalAmount("");
		}
		else{
			//单票金额计算
			String singleSum = null;
			//判断
			if(wayBillCount>0){
				singleSum = this.querySingleSum(wayBillCount,result.getRentalAmount());
			}
			result.setSingleSum(singleSum);
			resultVo=result;
		}
		return resultVo;
	}
	
	/**
	*计算单票金额
	*@author 332219
	*@date 2017-04-11  10:10:10
	**/
	private String querySingleSum(long wayBillCount, String rentalAmount) {
		//初始化默认为0
		String singleSum = "0";
		//如运单数为0，则返回0
		if(wayBillCount == 0){
			return singleSum;
		}else{
			 BigDecimal rentalMoney = new BigDecimal(rentalAmount);
			 BigDecimal total = new BigDecimal(wayBillCount);
			 BigDecimal money = rentalMoney.divide(total, 1, BigDecimal.ROUND_DOWN);
			 singleSum = ""+money;
		}
		return singleSum;
	}
	
	/**
	*根据标记的零担获取运单数
	*@author 332219
	*@date 2017-04-11  10:10:10
	**/
	private long queryWayBillCountByMark(RentalMarkVo rentalMarkVo){
		
		Long waybillCount = 0L;
		List<String> wayBillNoList = new ArrayList<String>();//存放零担运单号
		List<String> deliverNoList = new ArrayList<String>();//存放派送单号
		Long deliverCount=0L;//派送单的运单数量
		
		if(rentalMarkVo.getRentalMarkEntityList() != null){
			//把不同类型的单号分别存放
			for(int i=0;i<rentalMarkVo.getRentalMarkEntityList().size();i++){
				//零担运单
				if(rentalMarkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_WAYBILL)){
					wayBillNoList.add(rentalMarkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//派送单
				else if(rentalMarkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_DELIVERBILL)){
					deliverNoList.add(rentalMarkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
			}
			//根据派送单号查询派送单
			if(deliverNoList!=null&&deliverNoList.size()>0){
				deliverCount= this.queryWayBillForDeliverBill(deliverNoList);
			}
			waybillCount=wayBillNoList.size()+deliverCount;//这里应该是所有运单的总数
		}
		return waybillCount;
	};

	/**
	 *检查小票单号 是否可见
	 *author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param RentalMarkDto
	 *@return boolean
	 *
	 **/
	@Override
	public boolean smallTicketValidate(RentalMarkDto rentalMarkDto) {
		boolean  result=false;
		  long count=temprentalMarkDAO.smallTicketValidate(rentalMarkDto);
		  if(count!=0){
			  result=true;
		  }
		  return result;
	}
	/**
	 *查询交接单对应的运单 
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param handoverBillNoList
	 *@return List<String>
	 **/
	@Override
	public List<String> queryWayBillNoForHandoverBillNo(List<String> handoverBillNoList){
		
		return temprentalMarkDAO.queryWayBillNoForHandoverBillNo(handoverBillNoList);
	}
	
	/**
	 *查询快递交接单对应的运单 
	 * author gouyangyang  313352
	 *@date 2014-6-11  10:10:10
	 *@param handoverBillNoList
	 *@return List<String>
	 **/
	@Override
	public List<String> queryForHandoverBillNo(List<String> expressWayBillNoList){
		return temprentalMarkDAO.queryForHandoverBillNo(expressWayBillNoList);
	}
	
	/**
	 *查询派送单对应的运单 （需要做小票的运单）
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param deliverBillNoList
	 *@return List<String>
	 **/
	@Override
	public List<String> queryWayBillNoForDeliverBillNo(List<String> deliverBillNoList){
		return temprentalMarkDAO.queryWayBillNoForDeliverBillNo(deliverBillNoList);
	}
	/**
	 *查询单对应的运单 
	 * author zenghaibin
	 *@date 2014-11-11  10:10:10
	 *@param deliverBillNoList
	 *@return List<String>
	 **/
	private Long queryWayBillCountForStowageNo(List<String> stowageNolist){
		
		return temprentalMarkDAO.queryWayBillCountForStowageNo(stowageNolist);
	}
	/**
	 *查询派送单对应的运单 
	 * author zenghaibin
	 *@date 2014-11-05  10:10:10
	 *@param deliverBillNoList
	 *@return List<String>
	 **/
	private Long queryWayBillForDeliverBill(List<String> deliverBillNoList){
		
		return temprentalMarkDAO.queryWayBillForDeliverBill(deliverBillNoList);
	}
	/**
	 * 把不合法的小票单号传到前台页面，每个运单对云一个小票单号，（包括的运单，派送单对应的运单）
	 * author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param RentalMarkDto
	 *@return List<String>
	 *检查小票单号的合法性
	 **/
	@Override
	public HashMap<String,String>  querysmallTicketNum(RentalMarkDto rentalMarkDto){
		List<String> s1 =new ArrayList<String>();//页面的小票list
		List<SmallTicketEntity> sw=new ArrayList<SmallTicketEntity>();//后台查询的小票和运单号
		List<String> w1 =new ArrayList<String>();//页面的所有运单（运单和派送单对应的运单）
		List<String> w2 =new ArrayList<String>();//存放正确的运单
		List<String> s2 =  new ArrayList<String>();//存放正确的小票号
		List<String> s3 =  new ArrayList<String>();//存放错误的小票号，和页面小票对比之后的
		List<String> w3 =  new ArrayList<String>();//存放没有填小票号的运单号
		HashMap<String,String> resultMap=new HashMap<String,String>();
		if(rentalMarkDto!=null){
			s1 = rentalMarkDto.getSmallTicketList();
			if(s1==null||s1.isEmpty()){
				
				resultMap.put("smallTickets", this.concat(s1));
				return resultMap;
			}

			//335284 cubc-gray 
			sw= temprentalMarkDAO.querysmallTicketNum(rentalMarkDto);//查询页面小票单号对应的运单和小票，查出来肯定小于于等于页面小票个数
			List<SmallTicketEntity> cubcSw = new ArrayList<SmallTicketEntity>();
			try {
				cubcSw = querysmallTicketNumFromCUBC(rentalMarkDto);
			} catch (Exception e) {
				LOGGER.error("CUBC晚于FOSS上线时间，先进行容错处理", e);
			}
			sw.addAll(cubcSw);
			////end cubc-gray
			
			
			if(rentalMarkDto.getWayBillNoList()!=null&&!rentalMarkDto.getWayBillNoList().isEmpty()){
				w1.addAll(rentalMarkDto.getWayBillNoList());//放入页面的运单
			}
			if(rentalMarkDto.getDeliverbillNoList()!=null&&!rentalMarkDto.getDeliverbillNoList().isEmpty()){
				List<String> wayBillForeDeliver=queryWayBillNoForDeliverBillNo(rentalMarkDto.getDeliverbillNoList());
				if(wayBillForeDeliver!=null&&wayBillForeDeliver.size()>0){
					w1.addAll(wayBillForeDeliver);//放入派送单对应的运单
				}
			}
		}
		if(sw!=null&&sw.size()>0){//如果后台查询小票号存在
			if(w1!=null&&w1.size()>0){//如果页面运单不为空
				for(int i=0;i<sw.size();i++){
					if(w1.contains(sw.get(i).getWayBillNo())){//把页面的运单跟后台查询的运单小票比较，如果包含在页面运单里，则放入s2，即：s2是完全符合条件的数据
					s2.add(sw.get(i).getSmallTicketNum());	
					w2.add(sw.get(i).getWayBillNo());
					}
				}
			}
			if(s2!=null&&s2.size()>0){//把正确的小票号跟页面的小票号对比，，把错误的放s3
				for(int i=0;i<s1.size();i++){
					if(!s2.contains(s1.get(i))){
						s3.add(s1.get(i));
					}
				}
			}else{
				resultMap.put("smallTickets", this.concat(s1));
				return resultMap;
			}
		}else{
			resultMap.put("smallTickets", this.concat(s1));
			return resultMap;
		}
		if(w2!=null&&w2.size()>0){
			for(int i=0;i<w1.size();i++){
				if(!w2.contains(w1.get(i))){
					w3.add(w1.get(i));
				}
			}
			resultMap.put("wayBill", this.concat(w3));
		}
		resultMap.put("smallTickets", this.concat(s3));
		return resultMap;
		
	}
	
	static class CUBCSmallTicket{
		public CUBCSmallTicket() {
		}
		CUBCSmallTicket(String rentalUse, List<String> items) {
			setRentalUse(rentalUse);
			setItems(items);
		}
		CUBCSmallTicket(String rentalUse, String waybill) {
			setRentalUse(rentalUse);
			setWaybillNo(waybill);
		}
		private String rentalUse;
		private List<String> items;
		private String waybillNo;
		/**
		 * 响应详细信息
		 */
		private List<SmallTicketEntity> fossSmallTicketInfoDOList;
		/**
		 * 是否成功
		 */
		private boolean isSuccess = Boolean.FALSE;
		/**
		 * 异常信息
		 */
		private String exceptionMsg;
		
		public String getRentalUse() {
			return rentalUse;
		}
		public void setRentalUse(String rentalUse) {
			this.rentalUse = rentalUse;
		}
		public List<String> getItems() {
			return items;
		}
		public void setItems(List<String> items) {
			this.items = items;
		}
		public List<SmallTicketEntity> getFossSmallTicketInfoDOList() {
			return fossSmallTicketInfoDOList;
		}
		public void setFossSmallTicketInfoDOList(List<SmallTicketEntity> fossSmallTicketInfoDOList) {
			this.fossSmallTicketInfoDOList = fossSmallTicketInfoDOList;
		}
		public boolean isSuccess() {
			return isSuccess;
		}
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		public String getExceptionMsg() {
			return exceptionMsg;
		}
		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		@Override
		public String toString() {
			return "CUBCSmallTicket [rentalUse=" + rentalUse + ", items=" + items + ", fossSmallTicketInfoDOList="
					+ fossSmallTicketInfoDOList + ", isSuccess=" + isSuccess + ", exceptionMsg=" + exceptionMsg + "]";
		}
		public String getWaybillNo() {
			return waybillNo;
		}
		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
	}
	/**
	 * fosstocubc
	 * @param rentalMarkDto
	 * @return fossTransferService/checkReceiptIsLegal
	 */
	private List<SmallTicketEntity> querysmallTicketNumFromCUBC(RentalMarkDto rentalMarkDto) {
		LOGGER.info("start... TemprentalMarkService.querysmallTicketNumFromCUBC(RentalMarkDto)");
		CUBCSmallTicket ticket = new CUBCSmallTicket(rentalMarkDto.getRentalUse(),rentalMarkDto.getSmallTicketList());
		String jsonString = JSONObject.toJSONString(ticket);
		String result = fossToCubcService.querysmallTicketNumFromCUBC(jsonString);
		List<SmallTicketEntity> list = new ArrayList<SmallTicketEntity>();
		CUBCSmallTicket smallTicket;
		try {
			smallTicket = JSONObject.parseObject(result, CUBCSmallTicket.class);
			LOGGER.info("querysmallTicketNumFromCUBC结果：" + smallTicket);
			if (smallTicket.isSuccess() && smallTicket.getFossSmallTicketInfoDOList() != null) {
				list = smallTicket.getFossSmallTicketInfoDOList();
			}
		} catch (JSONException e) {
			// 接口地址不通，JSONObject.parseObject会抛出JSON异常
			LOGGER.error("cubc - 接口异常querysmallTicketNumFromCUBC", e);
			throw new TfrBusinessException("cubc - 接口异常", e);
		}
		return list;
	}
	/**
	 *查询运单的小票单号
	 * author zenghaibin
	 *@date 2014-10-11  10:10:10
	 *@param RentalMarkDto
	 *@return List<String>
	 **/
	@Override
	
	public HashMap<String,String>  querySmallTicketForWayBill(RentalMarkDto rentalMarkDto){
		
		List<SmallTicketEntity> sw=new ArrayList<SmallTicketEntity>();//后台查询的小票和运单号
		List<String> wayBillNoList=new ArrayList<String>();//存放运单号需要小票的运单号
		List<String> deliverForWaybillList= new ArrayList<String>();//存放派送单对应的需要小票的运单
		List<String> w1=new ArrayList<String>();//存放没有小票号的运单
		List<String>s1=new ArrayList<String>();//存放小票号
		HashMap<String,String> result=new HashMap<String,String> ();
		String rentalUse="";
		if(rentalMarkDto!=null){
			rentalUse=rentalMarkDto.getRentalUse();
			if(rentalMarkDto.getWayBillNoList()!=null&&!rentalMarkDto.getWayBillNoList().isEmpty()){
				wayBillNoList.addAll(rentalMarkDto.getWayBillNoList());
			}
			if(rentalMarkDto.getDeliverbillNoList()!=null&&!rentalMarkDto.getDeliverbillNoList().isEmpty()){
				
				deliverForWaybillList=this.queryWayBillNoForDeliverBillNo(rentalMarkDto.getDeliverbillNoList());//查询派送单对应的运单
			}
			if(deliverForWaybillList!=null&&deliverForWaybillList.size()>0){
				wayBillNoList.addAll(deliverForWaybillList);//放入运单List
			}
		}
		if(wayBillNoList!=null&&wayBillNoList.size()>0){
			for(int i=0;i<wayBillNoList.size();i++){
				HashMap<String,String> hp=new HashMap<String,String>();
				List<SmallTicketEntity> smallTicketEntity=new ArrayList<SmallTicketEntity>();
				hp.put("rentalUse", rentalUse);
				hp.put("wayBillNo", wayBillNoList.get(i));
				smallTicketEntity=temprentalMarkDAO.querySmallTicketForWayBill(hp);
				
				//////335284 cubc
				if (smallTicketEntity == null || smallTicketEntity.size() == 0 || smallTicketEntity.isEmpty()) {
					try {
						smallTicketEntity=querySmallTicketForWayBillFromCUBC(rentalUse, wayBillNoList.get(i));
					} catch (Exception e) {
						LOGGER.error("CUBC晚于FOSS上线时间，先进行容错处理", e);
					}
				}
				//////end cubc				
				
				
				if(null!=smallTicketEntity&&!smallTicketEntity.isEmpty()){
					sw.add(smallTicketEntity.get(0));
				}
				if(smallTicketEntity==null||smallTicketEntity.size()==0||smallTicketEntity.isEmpty()){
					w1.add(wayBillNoList.get(i));
				}
			}
			if(sw!=null&&sw.size()>0&&!sw.isEmpty()){
				for(int i=0;i<sw.size();i++){
					s1.add(sw.get(i).getSmallTicketNum());
				}
			}
		}
		
		result.put("wayBill", concat(w1));
		result.put("smallTickets", concat(s1));
		return result;
	}

	/**
	 * fosstocubc
	 * @param rentalUse
	 * @param waybill
	 * @return fossTransferService/querySmallTicCodes
	 */
	private List<SmallTicketEntity> querySmallTicketForWayBillFromCUBC(String rentalUse, String waybill) {
		CUBCSmallTicket ticket = new CUBCSmallTicket(rentalUse, waybill);
		String jsonString = JSONObject.toJSONString(ticket);
		LOGGER.info("querySmallTicketForWayBillFromCUBC quest=" + jsonString);
		String result = fossToCubcService.querySmallTicketForWayBillFromCUBC(jsonString);
		List<SmallTicketEntity> list = new ArrayList<SmallTicketEntity>();
		try {
			CUBCSmallTicket smallTicket = JSONObject.parseObject(result, CUBCSmallTicket.class);
			LOGGER.info("querySmallTicketForWayBillFromCUBC结果：" + smallTicket);
			if (smallTicket.isSuccess() && smallTicket.getFossSmallTicketInfoDOList() != null) {
				list = smallTicket.getFossSmallTicketInfoDOList();
			}
		} catch (JSONException e) {
			LOGGER.error("cubc - 接口异常querySmallTicketForWayBillFromCUBC", e);
			throw new TfrBusinessException("cubc - 接口异常", e);
		}
		return list;
	}
	/**连接list的字符串**/
	private String concat(List<String> list){
		StringBuffer str = new StringBuffer();
		if(list!=null&&!list.isEmpty()){
			int size = list.size();
			for(int i=0;i<size;i++){
				if(i==size-1){
					str.append(list.get(i));
				}else{
					str.append(list.get(i)).append(",");
				}
			}
		}
		return  str.toString();
		
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
		
		return temprentalMarkDAO.queryHandOverBillRepeatMark(handoverBillNoList);
	}
	
	/**
	 * 根据出发部门、到达部门算出最大公里数
	 * @param entityList
	 * @modify 273247
	 * @return
	 */
	@Override
	public Long queryMaxDistance(List<RentalMarkEntity> entityList){
		LineEntity lineEntity = new LineEntity(); 
		long maxDistance =0L;
		for(RentalMarkEntity rentalMarkEntity:entityList){
			lineEntity.setOrginalOrganizationCode(rentalMarkEntity.getOrigOrgCode());
			lineEntity.setDestinationOrganizationCode(rentalMarkEntity.getDestOrgCode());
			List<LineEntity> line=LineService.querySimpleLineListByCondition(lineEntity);
			if(line!=null&&line.size()>0){
				long currentDistance=line.get(0).getDistance();
				if(currentDistance>maxDistance){
					maxDistance=currentDistance;
				}
			}
		}
		return maxDistance;
	}

	/**
	 *查询租车标记明细表里是否有list里对应运单单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 *@param handoverBillNoList 单号list
	 *@return long
	 **/
	@Override
	public Long queryWayBillRepeatMark(RentalMarkDto rentalMarkDto){
		
		return temprentalMarkDAO.queryWayBillRepeatMark(rentalMarkDto);
	}
	
	/**
	 *添加租车标记 
	 *author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param tempRentalMatkVo:租车标记的vo
	 * @throws ParseException 
	 **/
	@Override
	@Transactional
	public void addTempRentalMark(TempRentalMatkVo tempRentalMatkVo) {
		if(tempRentalMatkVo==null){
			throw new TfrBusinessException("数据参数为空");
		}
		//310248查询费用承担部门用
 	    String inviteNo = tempRentalMatkVo.getInviteVehicleNo();
	    String bearFeesDept = tempRentalMatkVo.getBearFeesDept();
	    String bearFeesDeptCode = tempRentalMatkVo.getBearFeesDeptCode();
	    
	    /***********author:lurker-lee date:2017-04-17   note：封装多车走货的租车金额 传递给报账系统*****/
		BudgetEntity budgetEntity =null;
		CrmBudgetControlRequestEntity crmBudgetControlRequestEntity=null;
		List<BudgetEntity> budgetEntityList = null;
		List<CrmBudgetControlRequestEntity> rentalMarkToFSSCList = null;
		//转换用车日期，并获取用车月份
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/***********author:lurker-lee date:2017-04-17   note：封装多车走货的租车金额 传递给报账系统*****/
		
	    //判断是快递还是零担
		if (CollectionUtils.isNotEmpty(tempRentalMatkVo.getWkTfrBillEntityList())) {
			List<TempRentalMarkEntity> tempRentalMarkEntityList = new ArrayList<TempRentalMarkEntity>();//租车标记主表List
			List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList = new ArrayList<TempRentalMarkDetailEntity>();//明细表List
			List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList = new ArrayList<TemprentalMarkSmticksEntity>();//小票号list
			List<RentCarDto> rentCarDtoList = new ArrayList<RentCarDto>();//调结算接口，所需参数产生应付单
			//310248
			List<RentCarCubcDto> rentCarCubcDtoList = new ArrayList<RentCarCubcDto>();//调用CUBC接口，所需参数产生应付单
			RentCarCubcRequest rentCarCubcRequst = new RentCarCubcRequest();
			List<String> expressWayBillNoList = new ArrayList<String>();//存放快递运单号
			List<String> expressHandOverBillList = new ArrayList<String>();//存放快递交接单号
			List<String> expressHandAirBillList = new ArrayList<String>();//存放快递航空单号
		
			budgetEntityList = new ArrayList<BudgetEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了
			rentalMarkToFSSCList = new ArrayList<CrmBudgetControlRequestEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了
			
			
			//把不同类型的单号分别存放
			for(int i=0;i<tempRentalMatkVo.getWkTfrBillEntityList().size();i++){
				// 快递运单
				if(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getBillType().equals("expresswaybill")){
					expressWayBillNoList.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getHandoverBillNo());
					// 快递交接单	
				}else if(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getBillType().equals("expresshandoverbill")){
					expressHandOverBillList.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getHandoverBillNo());
					
				}else if(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getBillType().equals("airPortbill")){
					expressHandAirBillList.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getHandoverBillNo());
				}
			}
			Long shallTakeGoodsQyt=(long) (expressWayBillNoList.size()+expressHandOverBillList.size()+expressHandAirBillList.size());//这里应该是所有运单的总数吧
			
			/******** 登录人员与部门信息***********/
			Date currentDate = new Date();//当前时间
			CurrentInfo user = FossUserContext.getCurrentInfo();//当前登入人员对象
			String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
			OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);//获取部门信息
			/******************************/
			
			StringBuffer smallTicketNum=new StringBuffer();
			TempRentalMarkEntity tempRentalMarkEntityTmp = new TempRentalMarkEntity();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义时间格式
			String tempRentalValidDays =this.queryRentalMarkValidDays(tempRentalMatkVo.getDepartmentSignle(), orgCode); //有效期
			for(int i=0;i<tempRentalMatkVo.getWkTfrBillEntityList().size();i++){
				Long from =0L;
				Long to=0L;
				try {
					from = df.parse(df.format(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getCreateTime())).getTime();//开始时间
					to = df.parse(df.format(new Date())).getTime();//结束时间
					} catch (ParseException e) {
						throw new TfrBusinessException("租车标记有效期日期转换异常！");
					}
				long result = (to - from) / (ConstantsNumberSonar.SONAR_NUMBER_1000 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_24);
				if(result>Long.parseLong(tempRentalValidDays)){
					throw new TfrBusinessException("该单:"+tempRentalMatkVo.getWkTfrBillEntityList().get(i).getHandoverBillNo()+"不在标记有效期内");
				}
				
			}


			//设置约车编号
			tempRentalMarkEntityTmp.setInviteVehicleNo(tempRentalMatkVo.getInviteVehicleNo());//约车编号
	
			
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，同一辆车重复标记校验开始。。。");
			//根据交接单号查询车辆任务ID
			List<String> allBillNos = new ArrayList<String>();
			for (String tempBillNo : expressHandOverBillList) {
				List<String> billNos = queryTaskNoOfBillno(tempBillNo);
				allBillNos.addAll(billNos);
			}
			Long resultCount = 0L;
			if (!CollectionUtils.isEmpty(allBillNos)) {
				resultCount = queryHandOverBillRepeatMark(allBillNos);
			} 
			
			if (resultCount >= 1) {
				throw new TfrBusinessException("该车已标记");
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，同一辆车重复标记校验结束。。。");
			
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，交接单多次标记校验开始。。。");
			// 校验交接单是否重复被标记
			List<String> billNoList=new ArrayList<String>();
			for(int i=0;i<tempRentalMatkVo.getWkTfrBillEntityList().size();i++){
				if(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getBillType().equals("expresshandoverbill")){
					billNoList.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getHandoverBillNo());
				}
			}
			
			if(billNoList!=null&&!billNoList.isEmpty()&&billNoList.size()>0){
				//查询交接单
				Long count=this.queryHandOverBillRepeatMark(billNoList);
				if(count>0){
					throw new TfrBusinessException("交接单不允许多次标记");
				}
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，交接单多次标记校验结束。。。");
			
			
			
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，快递运单校验小票号开始。。。");
			
			if(tempRentalMatkVo.getRentalUse()==null||"".equals(tempRentalMatkVo.getRentalUse())){
				throw new TfrBusinessException("租车用途不能为空");
			}
			if(tempRentalMatkVo.getUseCareDate()==null){
				throw new TfrBusinessException("用车日期不能为空");
			}
			if(tempRentalMatkVo.getCarReason()==null||"".equals(tempRentalMatkVo.getCarReason())){
				
				throw new TfrBusinessException("用车原因不能为空");
			}
			
			tempRentalMarkEntityTmp.setRentalCarUsetype(tempRentalMatkVo.getRentalUse());//租车用途
			tempRentalMarkEntityTmp.setUseCarDate(tempRentalMatkVo.getUseCareDate());//用车时间
			String carReason=tempRentalMatkVo.getCarReason();//用车原因
			if(InviteVehicleConstants.RENTALMARK_SHORTHANDED.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_shortHanded;
			}else if(InviteVehicleConstants.RENTALMARK_SPECIALGOODS.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_specialGoods;
			}else if(InviteVehicleConstants.RENTALMARK_EXHIBITION.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_exhibition;
			}else if(InviteVehicleConstants.RENTALMARK_WAREHOUSEENTRY.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_warehouseEntry;
			}else if(InviteVehicleConstants.RENTALMARK_LACKVEHICLES.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_lackVehiclesS;
			}else if(InviteVehicleConstants.RENTALMARK_LIMITLINE.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_limitLine;
			}else if(InviteVehicleConstants.RENTALMARK_CUSTOMERREASON.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_customerReason;
			}else if(InviteVehicleConstants.RENTALMARK_LONGLIVERY.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_longDelivery;
			}else if(InviteVehicleConstants.RENTALMARK_EXTERNALCAUSES.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_externalCauses;
			}else if(InviteVehicleConstants.RENTALMARK_OTHERS.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_others;
			}else{
				throw new TfrBusinessException("没有该类型的用车原因");
				
			}
			tempRentalMarkEntityTmp.setUserCarReason(carReason);//用车原因
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号重复使用校验开始。。。");
			
			if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){//判断小票单号是否重复使用，如果重复使用则返回
				List<String> resultList=querySmallTicketRe(tempRentalMatkVo.getSmallTicketNumList());
				if(resultList!=null&&!resultList.isEmpty()){
					StringBuffer str = new StringBuffer();
					int size = resultList.size();
					for(int i=0;i<size;i++){
						if(i==size-1){
							str.append(resultList.get(i));
						}else{
							str.append(resultList.get(i)).append(",");
						}
					}
					throw new TfrBusinessException("该小票单号已被标记过："+str.toString());
				}
				
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号重复使用校验结束。。。");
			


			if(tempRentalMatkVo.getAcceptPerson()!=null&&!"".equals(tempRentalMatkVo.getAcceptPerson().trim())){
				tempRentalMarkEntityTmp.setAcceptPerson(tempRentalMatkVo.getAcceptPerson().trim());//约车受理人
			}
			if(tempRentalMatkVo.getAcceptPersonCode()!=null&&"".equals(tempRentalMatkVo.getAcceptPersonCode())){
				tempRentalMarkEntityTmp.setAcceptPersonCode(tempRentalMatkVo.getAcceptPersonCode());//约车受理人工号
			}
			int size = 0;
			if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){//如果小票单号不为空
				size = tempRentalMatkVo.getSmallTicketNumList().size();
				for(int i=0;i<size;i++){
					if(i==size-1){
					smallTicketNum.append(tempRentalMatkVo.getSmallTicketNumList().get(i));
					}else{
						smallTicketNum.append(tempRentalMatkVo.getSmallTicketNumList().get(i)).append(",");
					}
				}
				tempRentalMarkEntityTmp.setSmallTicketNum(smallTicketNum.toString());//小票单号
			}
			if(tempRentalMatkVo.getRemark()!=null&&!"".equals(tempRentalMatkVo.getRemark())){
				tempRentalMarkEntityTmp.setNotes(tempRentalMatkVo.getRemark().trim());//备注
			}
			if(org.getName()==null||"".equals(org.getName())){
				throw new TfrBusinessException("标记部门为空");
			}
			if(orgCode==null||"".equals(orgCode)){
				throw new TfrBusinessException("标记部门编码为空");
			}
			if(user.getEmpCode()==null||"".equals(user.getEmpCode())){
				throw new TfrBusinessException("创建人工号或修改人工号为空");
			}
			if(user.getEmpName()==null||"".equals(user.getEmpName())){
				throw new TfrBusinessException("创建人姓名或修改人姓名为空");
			}
			tempRentalMarkEntityTmp.setMarkDepartName(org.getName());//当前标记部门名称
			tempRentalMarkEntityTmp.setMarkDepartCode(orgCode);//标记部门编码
			tempRentalMarkEntityTmp.setCreateUserName(user.getEmpName());//创建人
			tempRentalMarkEntityTmp.setCreateUserCode(user.getEmpCode());//创建人工号
			tempRentalMarkEntityTmp.setCreateDate(currentDate);//创建时间
			tempRentalMarkEntityTmp.setModifyUserNme(user.getEmpName());//修改人
			tempRentalMarkEntityTmp.setModifyUserCode(user.getEmpCode());//修改人工号
			tempRentalMarkEntityTmp.setModifyDate(currentDate);//修改时间
			tempRentalMarkEntityTmp.setActive(InviteVehicleConstants.RENTALMARK_Y);//状态
			if(tempRentalMatkVo.getIsRepeateMark()==null||"".equals(tempRentalMatkVo.getIsRepeateMark())){
				throw new TfrBusinessException("是否多次标记字段为空");
			}
			tempRentalMarkEntityTmp.setIsRepeateMark(tempRentalMatkVo.getIsRepeateMark());//多次标记
			BigDecimal weigthTotal = new BigDecimal("0");//总重量
			BigDecimal volumeTotal=new BigDecimal("0");//总体积
		///////////////////////////////////////////shallTakeGoodsQyt(为什么是走货数量)//////////////////////////////////////////////////////////
			tempRentalMarkEntityTmp.setActualTakeGoodsQyt(new BigDecimal(shallTakeGoodsQyt));//应走货数量
			for(int i=0;i<tempRentalMatkVo.getRentalMarkEntityList().size();i++){
				weigthTotal=weigthTotal.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getTotalWeight());
				volumeTotal=volumeTotal.add(tempRentalMatkVo.getWkTfrBillEntityList().get(i).getTotalVolumn());
			}
			tempRentalMarkEntityTmp.setWeigthTotal(weigthTotal);//总重量
			tempRentalMarkEntityTmp.setVolumeTotal(volumeTotal);//总体积
			BigDecimal multiCarSize;
			/*************************多车走货信息**************************/
			if(tempRentalMatkVo.getMultiCarTakeGoodsDtoList()!=null&&!tempRentalMatkVo.getMultiCarTakeGoodsDtoList().isEmpty()){
				for(int i=0;i<tempRentalMatkVo.getMultiCarTakeGoodsDtoList().size();i++){
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备开始。。。");
					/**租车标记主表数据准备**/
					multiCarSize=new BigDecimal(tempRentalMatkVo.getMultiCarTakeGoodsDtoList().size());//多车走货车数量
					
					MultiCarTakeGoodsDto multiCarTakeGoodsDto=tempRentalMatkVo.getMultiCarTakeGoodsDtoList().get(i);
					TempRentalMarkEntity tempRentalMarkEntity = new TempRentalMarkEntity();
					tempRentalMarkEntity.setRentalCarUsetype(tempRentalMarkEntityTmp.getRentalCarUsetype());//租车用途
					tempRentalMarkEntity.setUseCarDate(tempRentalMarkEntityTmp.getUseCarDate());//用车日期
					tempRentalMarkEntity.setUserCarReason(tempRentalMarkEntityTmp.getUserCarReason());//用车原因
					tempRentalMarkEntity.setInviteVehicleNo(tempRentalMarkEntityTmp.getInviteVehicleNo());//约车编号
					tempRentalMarkEntity.setAcceptPerson(tempRentalMarkEntityTmp.getAcceptPerson());//约车受理人
					tempRentalMarkEntity.setAcceptPersonCode(tempRentalMarkEntityTmp.getAcceptPersonCode());//约车受理人工号
					tempRentalMarkEntity.setSmallTicketNum(tempRentalMarkEntityTmp.getSmallTicketNum());//小票单号
					tempRentalMarkEntity.setNotes(tempRentalMarkEntityTmp.getNotes());//备注
					tempRentalMarkEntity.setMarkDepartName(tempRentalMarkEntityTmp.getMarkDepartName());//当前标记部门名称
					tempRentalMarkEntity.setMarkDepartCode(tempRentalMarkEntityTmp.getMarkDepartCode());//标记部门编码
					tempRentalMarkEntity.setCreateUserName(tempRentalMarkEntityTmp.getCreateUserName());//创建人
					tempRentalMarkEntity.setCreateUserCode(tempRentalMarkEntityTmp.getCreateUserCode());//创建人工号
					tempRentalMarkEntity.setCreateDate(tempRentalMarkEntityTmp.getCreateDate());//创建时间
					tempRentalMarkEntity.setModifyUserNme(tempRentalMarkEntityTmp.getModifyUserNme());//修改人
					tempRentalMarkEntity.setModifyUserCode(tempRentalMarkEntityTmp.getModifyUserCode());//修改人工号
					tempRentalMarkEntity.setModifyDate(tempRentalMarkEntityTmp.getModifyDate());//修改时间
					tempRentalMarkEntity.setActive(tempRentalMarkEntityTmp.getActive());//状态
					tempRentalMarkEntity.setActualTakeGoodsQyt(tempRentalMarkEntityTmp.getActualTakeGoodsQyt());//应走货数量
					tempRentalMarkEntity.setWeigthTotal(tempRentalMarkEntityTmp.getWeigthTotal().divide(multiCarSize,ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP));//平均重量
					tempRentalMarkEntity.setVolumeTotal(tempRentalMarkEntityTmp.getVolumeTotal().divide(multiCarSize,ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP));//总体积
					tempRentalMarkEntity.setIsRepeateMark(tempRentalMarkEntityTmp.getIsRepeateMark());//多次标记
					if(inviteNo == null || inviteNo.length()==0){
						tempRentalMarkEntity.setBearFeesDept(bearFeesDept);
						tempRentalMarkEntity.setBearFeesDeptCode(bearFeesDeptCode);
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询开始。。。");
					List<DriverAssociationDto> driverAssociationDtoList=new ArrayList<DriverAssociationDto>();
					driverAssociationDtoList=leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//查询外请车司机信息
					
					if(driverAssociationDtoList.size()>0){
						if(driverAssociationDtoList.get(0).getDriverIdCard()==null
								||"".equals(driverAssociationDtoList.get(0).getDriverIdCard())
								||driverAssociationDtoList.get(0).getDriverName()==null
								||"".equals(driverAssociationDtoList.get(0).getDriverName())){
							throw new TfrBusinessException("外请车司机信息为空");
						}
						tempRentalMarkEntity.setDriverName(driverAssociationDtoList.get(0).getDriverName());//司机姓名
						tempRentalMarkEntity.setDriverCode(driverAssociationDtoList.get(0).getDriverIdCard());//司机代码
						tempRentalMarkEntity.setDriverPhone(driverAssociationDtoList.get(0).getDriverPhone());//司机电话
					}else {
						throw new TfrBusinessException("外请车司机为空");
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询结束。。。");
					tempRentalMarkEntity.setId(UUIDUtils.getUUID());//ID
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号开始。。。");
					String tempRentalMarkNO	= creatTempRentalMarkNO(tempRentalMatkVo.getRentalUse(),currentDate);//租车编号
					
					if(tempRentalMarkNO==null||"".equals(tempRentalMarkNO)){
						throw new TfrBusinessException("生成租车编号失败");
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号"+tempRentalMarkNO+"成功结束。。。");
					tempRentalMarkEntity.setTempRentalMarkNO(tempRentalMarkNO);//租车编号
					VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(multiCarTakeGoodsDto.getVehicleNo()); 
					if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleLengthCode()!=null){
						tempRentalMarkEntity.setVehicleLenghtCode(vehicleAssociationDto.getVehicleLengthCode());//车型
					}
					if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleSelfVolume()!=null){
						tempRentalMarkEntity.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());//净空
					}
					tempRentalMarkEntity.setVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//车牌号
					if(multiCarTakeGoodsDto.getRentalAmount().signum()<1){
						throw new TfrBusinessException("租车金额不能小于0");
					}
					tempRentalMarkEntity.setRentalAmount(multiCarTakeGoodsDto.getRentalAmount().multiply(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_100)));//租车金额*100
					tempRentalMarkEntity.setKmsNum(multiCarTakeGoodsDto.getKmsNum());//公里数
					tempRentalMarkEntity.setDepartureCode(multiCarTakeGoodsDto.getDepartureCode());//出发地编码
					tempRentalMarkEntity.setDepartureName(multiCarTakeGoodsDto.getDepartureName());//出发地部门
					tempRentalMarkEntity.setDestinationCode(multiCarTakeGoodsDto.getDestinationCode());//到达部门编码
					tempRentalMarkEntity.setDestinationName(multiCarTakeGoodsDto.getDestinationName());//到达部门名称
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备结束。。。");
					/**设置结算参数**/
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备开始。。。");
					RentCarDto rentCarDto = new RentCarDto();//结算
					rentCarDto.setAmount(multiCarTakeGoodsDto.getRentalAmount());//租车金额
					rentCarDto.setCustomerCode(tempRentalMarkEntity.getDriverCode());//司机编码
					rentCarDto.setCustomerName(tempRentalMarkEntity.getDriverName());//司机姓名
					rentCarDto.setCustomerPhone(tempRentalMarkEntity.getDriverPhone());//司机联系方式
					rentCarDto.setIsRepeatemark(tempRentalMarkEntity.getIsRepeateMark());//是否多次标记
					rentCarDto.setRentCarNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
					rentCarDto.setRentCarUseType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
					rentCarDto.setUseCarDate(tempRentalMarkEntity.getUseCarDate());//用车日期
					
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUBC接口生成应付单数据准备开始。。。");
					RentCarCubcDto rentCarCubcDto = new RentCarCubcDto();//CUBC
					rentCarCubcDto.setAmount(multiCarTakeGoodsDto.getRentalAmount());//租车金额
					rentCarCubcDto.setCustomerCode(tempRentalMarkEntity.getDriverCode());//司机编码
					rentCarCubcDto.setCustomerName(tempRentalMarkEntity.getDriverName());//司机姓名
					rentCarCubcDto.setCustomerPhone(tempRentalMarkEntity.getDriverPhone());//司机联系方式
					rentCarCubcDto.setIsRepeatemark(tempRentalMarkEntity.getIsRepeateMark());//是否多次标记
					rentCarCubcDto.setRentCarNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
					rentCarCubcDto.setRentCarUseType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
					rentCarCubcDto.setUseCarDate(tempRentalMarkEntity.getUseCarDate());//用车日期
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备完成。。。");
					/**添加租车标记主表信息**/
					tempRentalMarkEntityList.add(tempRentalMarkEntity);
					/**添加结算参数信息**/
					
					rentCarDtoList.add(rentCarDto);
					
					/**添加cubc参数信息*/
					rentCarCubcDtoList.add(rentCarCubcDto);
					
					/**************author:lurker-lee  date:2017-04-18  description:获取多车走货******/
					budgetEntity =new BudgetEntity();
					crmBudgetControlRequestEntity =new CrmBudgetControlRequestEntity();
					//标识字段，0：占用预算占用	1：释放预算	2：查询预算
					crmBudgetControlRequestEntity.setFlag("0");
					//请求时间        
					crmBudgetControlRequestEntity.setApplyDate(simpleDate.format(new Date()));
					//理赔ID,但是FOSS这边给的是租车编码，因为唯一，所以在做租车释放的时候，也是根据租车编码来对应关联，FSSC根据租车编码（理赔ID）释放金额
					crmBudgetControlRequestEntity.setClaimID(tempRentalMarkNO);
					//申请人工号
					crmBudgetControlRequestEntity.setEmpCode(user.getEmpCode());
					//申请人部门标杆编码
					crmBudgetControlRequestEntity.setApplyDeptStandCode(org.getUnifiedCode());
					//预算总金额
					crmBudgetControlRequestEntity.setTotalAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
					
					budgetEntity.setAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
					//设置用车月份					
					cal.setTime(tempRentalMarkEntity.getUseCarDate());
					String year = String.valueOf(cal.get(cal.YEAR));
					String strMonth=null;
					int month = cal.get(cal.MONTH)+1;
					if(month<10){
						strMonth="0"+month;
					}else{
						strMonth=String.valueOf(month);
					}
					budgetEntity.setMonth(year+strMonth);
					budgetEntityList.add(budgetEntity);
					crmBudgetControlRequestEntity.setBudgetEntitys(budgetEntityList);
					rentalMarkToFSSCList.add(crmBudgetControlRequestEntity);
					/**************author:lurker-lee  date:2017-04-18  description:获取多车走货******/
					
					
					//租车标记明细表数据准备
					for(int j=0;j<tempRentalMatkVo.getWkTfrBillEntityList().size();j++){
						LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备开始。。。");
						WKTfrBillEntity rentalMarkEntity= tempRentalMatkVo.getWkTfrBillEntityList().get(j);
						TempRentalMarkDetailEntity tempRentalMarkDetailEntity =new TempRentalMarkDetailEntity(); 
						tempRentalMarkDetailEntity.setId(UUIDUtils.getUUID());//设置ID
						tempRentalMarkDetailEntity.setTempRentalMarkId(tempRentalMarkEntity.getId());//租车Id
						tempRentalMarkDetailEntity.setTempRentalMarkNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
						tempRentalMarkDetailEntity.setBillNo(rentalMarkEntity.getHandoverBillNo());//单号
						tempRentalMarkDetailEntity.setBillType(rentalMarkEntity.getBillType());//单据类型
						tempRentalMarkDetailEntity.setWeight(rentalMarkEntity.getTotalWeight());//重量
						tempRentalMarkDetailEntity.setVolume(rentalMarkEntity.getTotalVolumn());//体积
						tempRentalMarkDetailEntity.setCreateUserName(tempRentalMarkEntity.getCreateUserName());//创建人
						tempRentalMarkDetailEntity.setCreateUserCode(tempRentalMarkEntity.getCreateUserCode());//创建人工号
						tempRentalMarkDetailEntity.setCreateDate(tempRentalMarkEntity.getCreateDate());//创建时间
						tempRentalMarkDetailEntity.setRentalCarUserType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
						tempRentalMarkDetailEntity.setMarkDepartName(tempRentalMarkEntity.getMarkDepartName());//标记部门
						tempRentalMarkDetailEntity.setMarkDepartCode(tempRentalMarkEntity.getMarkDepartCode());//标记部门
						tempRentalMarkDetailEntity.setBillCreateDate(rentalMarkEntity.getCreateTime());
						tempRentalMarkDetailEntity.setConsultPriceNo(tempRentalMatkVo.getConsultPriceNo());
						LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备完成。。。");
						/**添加租车标记明细表信息**/
						tempRentalMarkDetailEntityList.add(tempRentalMarkDetailEntity);
					}
					if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){
						for(int k=0;k<tempRentalMatkVo.getSmallTicketNumList().size();k++){//小票单号明细表
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表数据准备开始。。。");
							TemprentalMarkSmticksEntity temprentalMarkSmticksEntity = new TemprentalMarkSmticksEntity();
							temprentalMarkSmticksEntity.setId(UUIDUtils.getUUID());// 设置Id
							temprentalMarkSmticksEntity.setTempRentalMarkId(tempRentalMarkEntity.getId());// 租车id
							temprentalMarkSmticksEntity.setTempRentalMarkNo(tempRentalMarkEntity.getTempRentalMarkNO());// 租车编号
							String smallTicket = tempRentalMatkVo.getSmallTicketNumList().get(k);// 小票单号
							List<String> smallTicketList = new ArrayList<String>();
							smallTicketList.add(smallTicket);
							RentalMarkDto rentalMarkDto = new RentalMarkDto();
							rentalMarkDto.setSmallTicketList(smallTicketList);
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表对应运单号校验开始。。。");
							List<SmallTicketEntity> resultList = temprentalMarkDAO.querysmallTicketNum(rentalMarkDto);
							String wayBillNo = "";
							if (resultList != null && !resultList.isEmpty()) {
								wayBillNo = resultList.get(0).getWayBillNo();// 运单号
							}

							// 335284 cubc-gray
							if (wayBillNo == null || "".equals(wayBillNo)) {
								List<SmallTicketEntity> cubcSw = null;
								try {
									cubcSw = querysmallTicketNumFromCUBC(rentalMarkDto);
								} catch (Exception e) {
									LOGGER.error("CUBC晚于FOSS上线时间，先进行容错处理", e);
								}
								 
								if (cubcSw != null && !cubcSw.isEmpty()) {
									wayBillNo = cubcSw.get(0).getWayBillNo();// 运单号
								}
							}
							//// end cubc-gray

							if (wayBillNo == null || "".equals(wayBillNo)) {
								throw new TfrBusinessException("运单号错误");
							}
							 LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表对应运单号校验完成。。。");
							temprentalMarkSmticksEntity.setSmallTickerNum(smallTicket);//小票单号
							temprentalMarkSmticksEntity.setWayBillNo(wayBillNo);//运单号
							temprentalMarkSmticksEntity.setCreateUserName(tempRentalMarkEntity.getCreateUserName());//创建人
							temprentalMarkSmticksEntity.setCreateUserCode(tempRentalMarkEntity.getCreateUserCode());//创建人工号
							temprentalMarkSmticksEntity.setCreateDate(tempRentalMarkEntity.getCreateDate());//创建人时间
							temprentalMarkSmticksEntity.setActive("Y");
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表数据准备完成。。。");
							/**添加小票号明细表信息**/
							temprentalMarkSmticksEntityList.add(temprentalMarkSmticksEntity);
						}
					}					
					
				}
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车主表。。。");
			 temprentalMarkDAO.addTempRentalMarkEntityList(tempRentalMarkEntityList);//添加租车主表信息
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车明细表。。。");
			 temprentalMarkDAO.addTempRentalMarkDetailEntityList(tempRentalMarkDetailEntityList);//添加租车明细表信息
			if(temprentalMarkSmticksEntityList!=null&&!temprentalMarkSmticksEntityList.isEmpty()){
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入小票单号表。。。");
			temprentalMarkDAO.addTemprentalMarkSmticksEntityList(temprentalMarkSmticksEntityList);//小票明细信息
			}
			if(inviteNo != null && inviteNo.length()>0){
				//310248查询费用承担部门
				rentCarCubcRequst = this.queryBearFeesDept(inviteNo);
				if(rentCarCubcRequst == null){
					throw new TfrBusinessException("在约车时,无费用承担部门！");	
				}else{//此else中的代码是为了处理历史数据，一段时间后可删除  2017.4.22
					OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrationInfoByName(rentCarCubcRequst.getBearFeesDept());
					if(null ==entity){
						throw new TfrBusinessException("在约车时,无费用承担部门！");	
					}
					rentCarCubcRequst.setBearFeesDeptCode(entity.getCode());
				}
			}else{
				rentCarCubcRequst.setBearFeesDept(bearFeesDept);
				rentCarCubcRequst.setBearFeesDeptCode(bearFeesDeptCode);
			}
			
			/*****author:lurker-lee date:2017-04-18 description:调用ESB接口推送给FSSC系统 行驶预算占用*******/
			//根据费用承担部门CODE获取对应的标杆标码
			OrgAdministrativeInfoEntity feeOrg =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(rentCarCubcRequst.getBearFeesDeptCode());//获取部门信息
            //遍历每一个租车费用，传递给报账系统			
			if(rentalMarkToFSSCList!=null && rentalMarkToFSSCList.size()>0){
				for(CrmBudgetControlRequestEntity ent:rentalMarkToFSSCList){
					List<BudgetEntity> list=ent.getBudgetEntitys();
					if(list!=null && list.size()>0){
						list.get(0).setDeptStandCode(feeOrg.getUnifiedCode());
					}
					//调用ESB接口
					LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  调用开始！");
					try{
						CrmBudgetControlResponseEntity  responseEnt=aSYRentCarCubcService.pushTemptalMarkFeeInfoToFSSC(ent);
						if(responseEnt==null){
							throw new TfrBusinessException("租车编码："+ent.getClaimID()+" 调用FSSC报账系统接口失败 ");
						}
						LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  FSSC响应返回："+responseEnt.toString());
						LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  成功结束！");
						if(0==responseEnt.getIsSucceed()){
							throw new TfrBusinessException("租车编码："+ent.getClaimID()+"  "+responseEnt.getFailure());
						}
					}catch(Exception e){
						throw new TfrBusinessException(e.getMessage());
					}
				}
			}
			/*****author:lurker-lee date:2017-04-18 description:调用ESB接口推送给FSSC系统 行驶预算占用*******/
			
			////cubcgray 335284-310248
			GrayParameterDto dto = new GrayParameterDto();
			dto.setSourceBillNos(getCustomerList(rentCarCubcDtoList));
			dto.setSourceBillType(CUBCGrayUtil.SBType.SJ.getName());
			VestResponse response = cubcUtil.getCubcGrayDataByCustomer(dto, new Throwable());
			List<VestBatchResult> batchResult = response.getVestBatchResult();
			List<RentCarDto> rentCarDtoList_ = null;
			List<RentCarCubcDto> rentCarCubcDtoList_ = null;
			long millis = System.currentTimeMillis();
			for (VestBatchResult vestBatchResult : batchResult) {
				if (CUBCGrayUtil.SYSTEM_FOSS.equals(vestBatchResult.getVestSystemCode())) {
					rentCarDtoList_ = substractStlList(vestBatchResult.getVestObject(), rentCarDtoList);
				}else{
					rentCarCubcDtoList_ = substractCubcList(vestBatchResult.getVestObject(), rentCarCubcDtoList);
				}
			}
			LOGGER.info("计算两个列表耗时：" + (System.currentTimeMillis() - millis));
			
			if (rentCarDtoList_ != null && !rentCarDtoList_.isEmpty()) {
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单。。。" + rentCarDtoList_.size());
				rentCarService.addRentCar(rentCarDtoList_, user);//掉财务结算接口
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单结束。。。");
			}
			if (rentCarCubcDtoList_ != null && !rentCarCubcDtoList_.isEmpty()) {
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUBC接口生成应付单。。。" + rentCarCubcDtoList_.size());
				rentCarCubcRequst.setEmpCode(user.getEmpCode());
				rentCarCubcRequst.setEmpName(user.getEmpName());
				rentCarCubcRequst.setCurrentDeptCode(user.getCurrentDeptCode());
				rentCarCubcRequst.setCurrentDeptName(user.getCurrentDeptName());
				rentCarCubcRequst.setTempRentalMarkDetailEntityList(tempRentalMarkDetailEntityList);
				rentCarCubcRequst.setTemprentalMarkSmticksEntityList(temprentalMarkSmticksEntityList);
				rentCarCubcRequst.setTempRentalMarkEntityList(tempRentalMarkEntityList);
				rentCarCubcRequst.setRentCarCubcList(rentCarCubcDtoList);
				
				aSYRentCarCubcService.pushaddRentCar(rentCarCubcRequst); // 310248
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUBC接口生成应付单结束。。。");
			}
			////end cubcgray 335284-310248
		} else {
			//零担逻辑处理
			List<TempRentalMarkEntity> tempRentalMarkEntityList = new ArrayList<TempRentalMarkEntity>();//租车标记主表List
			List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList = new ArrayList<TempRentalMarkDetailEntity>();//明细表List
			List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList = new ArrayList<TemprentalMarkSmticksEntity>();//小票号list
			List<RentCarDto> rentCarDtoList = new ArrayList<RentCarDto>();//调结算接口，所需参数产生应付单
			
			List<RentCarCubcDto> rentCarCubcDtoList = new ArrayList<RentCarCubcDto>();//调CUBC接口，所需参数产生应付单
			RentCarCubcRequest  rentCarCubcRequest =     new RentCarCubcRequest();
			
			budgetEntityList = new ArrayList<BudgetEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了
			rentalMarkToFSSCList = new ArrayList<CrmBudgetControlRequestEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了		
			
			List<String> wayBillNoList = new ArrayList<String>();//存放零担运单号
			List<String> handOverNoList = new ArrayList<String>();//存放交接单号
			List<String> deliverNoList = new ArrayList<String>();//存放派送单号
			List<String> stowageNoList = new ArrayList<String>();//存放配载单号
			List<String> expressWayBillNoList = new ArrayList<String>();//存放快递运单号
			Long stowageCount=0l;//配载单的运单数量
			Long deliverCount=0L;//派送单的运单数量
			//未使用-352203
//			Long expressCount=0L; //快递交接单数量
//			Long makeCount=0L;    //快递运单数量 

			//把不同类型的单号分别存放
			for(int i=0;i<tempRentalMatkVo.getRentalMarkEntityList().size();i++){
				//零担运单
				if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_WAYBILL)){
					wayBillNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//交接单
				else if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_HANDOVERBILL)){
					handOverNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//配载单
				else if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_STOWAGEBILL)){
					stowageNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//派送单
				else if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_DELIVERBILL)){
					deliverNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//269701--lln--2015-09-09 begin
				//快递运单
				else if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_EXPRESSRBILL)){
					expressWayBillNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
				//269701--lln--2015-09-09 end
			}
			//根据交接单号查询交接单
			if(handOverNoList!=null&&handOverNoList.size()>0){
				handOverNoList=this.queryWayBillNoForHandoverBillNo(handOverNoList);
			}
			//根据派送单号查询派送单
			if(deliverNoList!=null&&deliverNoList.size()>0){
				deliverCount= this.queryWayBillForDeliverBill(deliverNoList);
			}
			//根据配载单号查询配载单
			if(stowageNoList!=null&&stowageNoList.size()>0){
				 stowageCount=this.queryWayBillCountForStowageNo(stowageNoList);
			}

			Long shallTakeGoodsQyt=handOverNoList.size()+wayBillNoList.size()+deliverCount+stowageCount;//这里应该是所有运单的总数吧
			Date currentDate = new Date();//当前时间
			CurrentInfo user = FossUserContext.getCurrentInfo();//当前登入人员对象
			String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
			OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);//获取部门信息
			StringBuffer smallTicketNum=new StringBuffer();
			TempRentalMarkEntity tempRentalMarkEntityTmp = new TempRentalMarkEntity();
			
			
			//获取运单+派送单中的运单数量
			Long wayBillCount = deliverCount+wayBillNoList.size();
			/** 当运单数大于0时，校验单票费用 332219 **/
			if(wayBillCount>0){
				//判空
				if(tempRentalMatkVo.getMultiCarTakeGoodsDtoList()!=null&&!tempRentalMatkVo.getMultiCarTakeGoodsDtoList().isEmpty()){
					//当租车金额随意输入，计算单票费用
					MultiCarTakeGoodsDto multiCarTakeGoodsDto = tempRentalMatkVo.getMultiCarTakeGoodsDtoList().get(0);
					//获取租车金额
					BigDecimal rentalAmount = multiCarTakeGoodsDto.getRentalAmount();
					
					//计算单票金额
					String singleSum = this.querySingleSum(wayBillCount,String.valueOf(rentalAmount));
					//单票费用
					BigDecimal rentalMoney = new BigDecimal(singleSum);
					//零担单票费用标准
					double money = inviteVehicleService.queryVehicleCost(orgCode);
					BigDecimal singleMoney = new BigDecimal(money);
					//判断大小
					if(rentalMoney.compareTo(singleMoney) == 1){
						throw new TfrBusinessException("所标记单票金额超出区域单票上限，无法标记，请核实！如有疑问请查询OA公告《接送货外请车标记说明》。");
					}
				}else{
					throw new TfrBusinessException("租车金额不能为空！");
				}
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义时间格式
			String tempRentalValidDays =this.queryRentalMarkValidDays(tempRentalMatkVo.getDepartmentSignle(), orgCode); //有效期
			for(int i=0;i<tempRentalMatkVo.getRentalMarkEntityList().size();i++){
				Long from =0L;
				Long to=0L;
				try {
					from = df.parse(df.format(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillCreateTime())).getTime();//开始时间
					to = df.parse(df.format(new Date())).getTime();//结束时间
					} catch (ParseException e) {
						throw new TfrBusinessException("租车标记有效期日期转换异常！");
					}
				long result = (to - from) / (ConstantsNumberSonar.SONAR_NUMBER_1000 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_24);
				if(result>Long.parseLong(tempRentalValidDays)){
					throw new TfrBusinessException("该单:"+tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo()+"不在标记有效期内");
				}
				
			}
			
			/**
			 *若租车用途为“接货”，零担运单开单上门接货且开单输入的工号为公司司机工号，不允许租车标记。
			 *快递运单不做校验
			 **/
			if(InviteVehicleConstants.RENTALMARK_JH.equals(tempRentalMatkVo.getRentalUse())){
				int size = tempRentalMatkVo.getRentalMarkEntityList().size();
				for(int i=0;i<size;i++){
					if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_WAYBILL)
							&& tempRentalMatkVo.getRentalMarkEntityList().get(i).getIsDoorDeliver().equals("Y")){//如果为零担运单,并且为上门接货
						long count = queryDriverNoOperateNo(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
						if(count>0){
							throw new TfrBusinessException("零担运单"+tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo()+"不是由临时租车接货，不允许租车标记");
							}
					}
				}
				
			}
			/**
			 *判断零担运单是否重复标记，当运单号、租车用途都相同时，视为重复标记。
			 *快递运单可以重复标记
			 ***/
			 LOGGER.info("TFR-SCHEDULING 添加租车标记信息，重复标记校验开始。。。");
			if(!tempRentalMatkVo.getRentalMarkEntityList().isEmpty()){
				int size = tempRentalMatkVo.getRentalMarkEntityList().size();

				for(int i=0;i<size;i++){
					if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_WAYBILL)
							&&tempRentalMatkVo.getRentalMarkEntityList().get(i).getRentalUse()!=null
							&&tempRentalMatkVo.getRentalMarkEntityList().get(i).getRentalNum()!=null){//如果为零担运单,并且租车用途不为空，租车编号不为空
						if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getRentalUse().equals(tempRentalMatkVo.getRentalUse())){//如果租车用途一样
							
							throw new TfrBusinessException("不允许重复标记");

						}
					
					}
				
				}
			
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，重复标记校验结束。。。");
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，交接单和派送单多次标记校验开始。。。");
			List<String> billNoList=new ArrayList<String>();
			for(int i=0;i<tempRentalMatkVo.getRentalMarkEntityList().size();i++){
				if(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_HANDOVERBILL)
					||tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_DELIVERBILL)
					||tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillType().equals(InviteVehicleConstants.RENTALMARK_STOWAGEBILL)){
					billNoList.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getBillNo());
				}
			}
			if(billNoList!=null&&!billNoList.isEmpty()&&billNoList.size()>0){
				Long count=this.queryHandOverBillRepeatMark(billNoList);
				if(count>0){
					throw new TfrBusinessException("交接单、派送单和配载单不允许多次标记");
				}
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，交接单、派送和配载单多次标记校验结束。。。");
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，派送和零担运单校验小票号开始。。。");
			
			if(tempRentalMatkVo.getRentalUse()==null||"".equals(tempRentalMatkVo.getRentalUse())){
				throw new TfrBusinessException("租车用途不能为空");
			}
			if(tempRentalMatkVo.getUseCareDate()==null){
				throw new TfrBusinessException("用车日期不能为空");
			}
			if(tempRentalMatkVo.getCarReason()==null||"".equals(tempRentalMatkVo.getCarReason())){
				
				throw new TfrBusinessException("用车原因不能为空");
			}
			tempRentalMarkEntityTmp.setRentalCarUsetype(tempRentalMatkVo.getRentalUse());//租车用途
			tempRentalMarkEntityTmp.setUseCarDate(tempRentalMatkVo.getUseCareDate());//用车时间
			String carReason=tempRentalMatkVo.getCarReason();//用车原因
			if(InviteVehicleConstants.RENTALMARK_SHORTHANDED.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_shortHanded;
			}else if(InviteVehicleConstants.RENTALMARK_SPECIALGOODS.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_specialGoods;
			}else if(InviteVehicleConstants.RENTALMARK_EXHIBITION.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_exhibition;
			}else if(InviteVehicleConstants.RENTALMARK_WAREHOUSEENTRY.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_warehouseEntry;
			}else if(InviteVehicleConstants.RENTALMARK_LACKVEHICLES.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_lackVehiclesS;
			}else if(InviteVehicleConstants.RENTALMARK_LIMITLINE.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_limitLine;
			}else if(InviteVehicleConstants.RENTALMARK_CUSTOMERREASON.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_customerReason;
			}else if(InviteVehicleConstants.RENTALMARK_LONGLIVERY.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_longDelivery;
			}else if(InviteVehicleConstants.RENTALMARK_EXTERNALCAUSES.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_externalCauses;
			}else if(InviteVehicleConstants.RENTALMARK_OTHERS.equals(carReason)){
				carReason=InviteVehicleConstants.RENTALMARK_others;
			}else{
				throw new TfrBusinessException("没有该类型的用车原因");
				
			}
			tempRentalMarkEntityTmp.setUserCarReason(carReason);//用车原因
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号重复使用校验开始。。。");
			if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){//判断小票单号是否重复使用，如果重复使用则返回
				List<String> resultList=querySmallTicketRe(tempRentalMatkVo.getSmallTicketNumList());
				if(resultList!=null&&!resultList.isEmpty()){
					StringBuffer str = new StringBuffer();
					int size = resultList.size();
					for(int i=0;i<size;i++){
						if(i==size-1){
							str.append(resultList.get(i));
						}else{
							str.append(resultList.get(i)).append(",");
						}
					}
					throw new TfrBusinessException("该小票单号已被标记过："+str.toString());
				}
				
			}        
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号重复使用校验结束。。。");
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，零担约车编号校验开始。。。");
			//269701--lln--2015-09-09 begin
			//快递时，约车编号不校验--约车编号可以被多次标记
			//因为快递信息不会跟零担信息同时被标记，只要快递运单不为空，则标记的只有快递信息;否则，是零担信息
			if(expressWayBillNoList.size()<=0){
			//269701--lln--2015-09-09 end
				if(tempRentalMatkVo.getInviteVehicleNo()!=null&&!"".equals(tempRentalMatkVo.getInviteVehicleNo().trim())){
					String rentalMarkNo=this.valiteInviteVehicleNo(tempRentalMatkVo.getInviteVehicleNo());
					if(rentalMarkNo!=null&&!"".equals(rentalMarkNo)){
						throw new TfrBusinessException("约车编号"+tempRentalMatkVo.getInviteVehicleNo()+"!，已经被租车标记了（租车编号"+rentalMarkNo+"）");
					}
					tempRentalMarkEntityTmp.setInviteVehicleNo(tempRentalMatkVo.getInviteVehicleNo());//约车编号
				}
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，约车编号校验结束。。。");
			if(tempRentalMatkVo.getAcceptPerson()!=null&&!"".equals(tempRentalMatkVo.getAcceptPerson().trim())){
				tempRentalMarkEntityTmp.setAcceptPerson(tempRentalMatkVo.getAcceptPerson().trim());//约车受理人
			}
			if(tempRentalMatkVo.getAcceptPersonCode()!=null&&"".equals(tempRentalMatkVo.getAcceptPersonCode())){
				tempRentalMarkEntityTmp.setAcceptPersonCode(tempRentalMatkVo.getAcceptPersonCode());//约车受理人工号
			}
			int size = 0;
			if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){//如果小票单号不为空
				size = tempRentalMatkVo.getSmallTicketNumList().size();
				for(int i=0;i<size;i++){
					if(i==size-1){
					smallTicketNum.append(tempRentalMatkVo.getSmallTicketNumList().get(i));
					}else{
						smallTicketNum.append(tempRentalMatkVo.getSmallTicketNumList().get(i)).append(",");
					}
				}
				tempRentalMarkEntityTmp.setSmallTicketNum(smallTicketNum.toString());//小票单号
			}
			if(tempRentalMatkVo.getRemark()!=null&&!"".equals(tempRentalMatkVo.getRemark())){
				tempRentalMarkEntityTmp.setNotes(tempRentalMatkVo.getRemark().trim());//备注
			}
			if(org.getName()==null||"".equals(org.getName())){
				throw new TfrBusinessException("标记部门为空");
			}
			if(orgCode==null||"".equals(orgCode)){
				throw new TfrBusinessException("标记部门编码为空");
			}
			if(user.getEmpCode()==null||"".equals(user.getEmpCode())){
				throw new TfrBusinessException("创建人工号或修改人工号为空");
			}
			if(user.getEmpName()==null||"".equals(user.getEmpName())){
				throw new TfrBusinessException("创建人姓名或修改人姓名为空");
			}
			tempRentalMarkEntityTmp.setMarkDepartName(org.getName());//当前标记部门名称
			tempRentalMarkEntityTmp.setMarkDepartCode(orgCode);//标记部门编码
			tempRentalMarkEntityTmp.setCreateUserName(user.getEmpName());//创建人
			tempRentalMarkEntityTmp.setCreateUserCode(user.getEmpCode());//创建人工号
			tempRentalMarkEntityTmp.setCreateDate(currentDate);//创建时间
			tempRentalMarkEntityTmp.setModifyUserNme(user.getEmpName());//修改人
			tempRentalMarkEntityTmp.setModifyUserCode(user.getEmpCode());//修改人工号
			tempRentalMarkEntityTmp.setModifyDate(currentDate);//修改时间
			tempRentalMarkEntityTmp.setActive(InviteVehicleConstants.RENTALMARK_Y);//状态
			if(tempRentalMatkVo.getIsRepeateMark()==null||"".equals(tempRentalMatkVo.getIsRepeateMark())){
				throw new TfrBusinessException("是否多次标记字段为空");
			}
			tempRentalMarkEntityTmp.setIsRepeateMark(tempRentalMatkVo.getIsRepeateMark());//多次标记
			BigDecimal weigthTotal = new BigDecimal("0");//总重量
			BigDecimal volumeTotal=new BigDecimal("0");//总体积
		///////////////////////////////////////////shallTakeGoodsQyt(为什么是走货数量)//////////////////////////////////////////////////////////
			tempRentalMarkEntityTmp.setActualTakeGoodsQyt(new BigDecimal(shallTakeGoodsQyt));//应走货数量
			for(int i=0;i<tempRentalMatkVo.getRentalMarkEntityList().size();i++){
				weigthTotal=weigthTotal.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getWeight());
				volumeTotal=volumeTotal.add(tempRentalMatkVo.getRentalMarkEntityList().get(i).getVolume());
			}
			tempRentalMarkEntityTmp.setWeigthTotal(weigthTotal);//总重量
			tempRentalMarkEntityTmp.setVolumeTotal(volumeTotal);//总体积
			BigDecimal multiCarSize;
			if(tempRentalMatkVo.getMultiCarTakeGoodsDtoList()!=null&&!tempRentalMatkVo.getMultiCarTakeGoodsDtoList().isEmpty()){
				for(int i=0;i<tempRentalMatkVo.getMultiCarTakeGoodsDtoList().size();i++){
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备开始。。。");
					/**租车标记主表数据准备**/
					multiCarSize=new BigDecimal(tempRentalMatkVo.getMultiCarTakeGoodsDtoList().size());//多车走货车数量
					
					MultiCarTakeGoodsDto multiCarTakeGoodsDto=tempRentalMatkVo.getMultiCarTakeGoodsDtoList().get(i);
					TempRentalMarkEntity tempRentalMarkEntity = new TempRentalMarkEntity();
					tempRentalMarkEntity.setRentalCarUsetype(tempRentalMarkEntityTmp.getRentalCarUsetype());//租车用途
					tempRentalMarkEntity.setUseCarDate(tempRentalMarkEntityTmp.getUseCarDate());//用车日期
					tempRentalMarkEntity.setUserCarReason(tempRentalMarkEntityTmp.getUserCarReason());//用车原因
					tempRentalMarkEntity.setInviteVehicleNo(tempRentalMarkEntityTmp.getInviteVehicleNo());//约车编号
					tempRentalMarkEntity.setAcceptPerson(tempRentalMarkEntityTmp.getAcceptPerson());//约车受理人
					tempRentalMarkEntity.setAcceptPersonCode(tempRentalMarkEntityTmp.getAcceptPersonCode());//约车受理人工号
					tempRentalMarkEntity.setSmallTicketNum(tempRentalMarkEntityTmp.getSmallTicketNum());//小票单号
					tempRentalMarkEntity.setNotes(tempRentalMarkEntityTmp.getNotes());//备注
					tempRentalMarkEntity.setMarkDepartName(tempRentalMarkEntityTmp.getMarkDepartName());//当前标记部门名称
					tempRentalMarkEntity.setMarkDepartCode(tempRentalMarkEntityTmp.getMarkDepartCode());//标记部门编码
					tempRentalMarkEntity.setCreateUserName(tempRentalMarkEntityTmp.getCreateUserName());//创建人
					tempRentalMarkEntity.setCreateUserCode(tempRentalMarkEntityTmp.getCreateUserCode());//创建人工号
					tempRentalMarkEntity.setCreateDate(tempRentalMarkEntityTmp.getCreateDate());//创建时间
					tempRentalMarkEntity.setModifyUserNme(tempRentalMarkEntityTmp.getModifyUserNme());//修改人
					tempRentalMarkEntity.setModifyUserCode(tempRentalMarkEntityTmp.getModifyUserCode());//修改人工号
					tempRentalMarkEntity.setModifyDate(tempRentalMarkEntityTmp.getModifyDate());//修改时间
					tempRentalMarkEntity.setActive(tempRentalMarkEntityTmp.getActive());//状态
					tempRentalMarkEntity.setActualTakeGoodsQyt(tempRentalMarkEntityTmp.getActualTakeGoodsQyt());//应走货数量
					tempRentalMarkEntity.setWeigthTotal(tempRentalMarkEntityTmp.getWeigthTotal().divide(multiCarSize,ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP));//平均重量
					tempRentalMarkEntity.setVolumeTotal(tempRentalMarkEntityTmp.getVolumeTotal().divide(multiCarSize,ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP));//总体积
					tempRentalMarkEntity.setIsRepeateMark(tempRentalMarkEntityTmp.getIsRepeateMark());//多次标记
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询开始。。。");
					List<DriverAssociationDto> driverAssociationDtoList=new ArrayList<DriverAssociationDto>();
					driverAssociationDtoList=leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//查询外请车司机信息
					
					if(driverAssociationDtoList.size()>0){
						if(driverAssociationDtoList.get(0).getDriverIdCard()==null
								||"".equals(driverAssociationDtoList.get(0).getDriverIdCard())
								||driverAssociationDtoList.get(0).getDriverName()==null
								||"".equals(driverAssociationDtoList.get(0).getDriverName())){
							throw new TfrBusinessException("外请车司机信息为空");
						}
						tempRentalMarkEntity.setDriverName(driverAssociationDtoList.get(0).getDriverName());//司机姓名
						tempRentalMarkEntity.setDriverCode(driverAssociationDtoList.get(0).getDriverIdCard());//司机代码
						tempRentalMarkEntity.setDriverPhone(driverAssociationDtoList.get(0).getDriverPhone());//司机电话
					}else {
						throw new TfrBusinessException("外请车司机为空");
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询结束。。。");
					tempRentalMarkEntity.setId(UUIDUtils.getUUID());//ID
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号开始。。。");
					String tempRentalMarkNO	= creatTempRentalMarkNO(tempRentalMatkVo.getRentalUse(),currentDate);//租车编号
					if(tempRentalMarkNO==null||"".equals(tempRentalMarkNO)){
						throw new TfrBusinessException("生成租车编号失败");
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号"+tempRentalMarkNO+"成功结束。。。");
					tempRentalMarkEntity.setTempRentalMarkNO(tempRentalMarkNO);//租车编号
					VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(multiCarTakeGoodsDto.getVehicleNo()); 
					if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleLengthCode()!=null){
						tempRentalMarkEntity.setVehicleLenghtCode(vehicleAssociationDto.getVehicleLengthCode());//车型
					}
					if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleSelfVolume()!=null){
						tempRentalMarkEntity.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());//净空
					}
					tempRentalMarkEntity.setVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//车牌号
					if(multiCarTakeGoodsDto.getRentalAmount().signum()<1){
						throw new TfrBusinessException("租车金额不能小于0");
					}
					tempRentalMarkEntity.setRentalAmount(multiCarTakeGoodsDto.getRentalAmount().multiply(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_100)));//租车金额*100
					tempRentalMarkEntity.setKmsNum(multiCarTakeGoodsDto.getKmsNum());//公里数
					tempRentalMarkEntity.setDepartureCode(multiCarTakeGoodsDto.getDepartureCode());//出发地编码
					tempRentalMarkEntity.setDepartureName(multiCarTakeGoodsDto.getDepartureName());//出发地部门
					tempRentalMarkEntity.setDestinationCode(multiCarTakeGoodsDto.getDestinationCode());//到达部门编码
					tempRentalMarkEntity.setDestinationName(multiCarTakeGoodsDto.getDestinationName());//到达部门名称
					if(inviteNo == null || inviteNo.length()==0){
						tempRentalMarkEntity.setBearFeesDept(bearFeesDept);
						tempRentalMarkEntity.setBearFeesDeptCode(bearFeesDeptCode);
					}
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备结束。。。");
					/**设置结算参数**/
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备开始。。。");
					RentCarDto rentCarDto = new RentCarDto();//结算
					rentCarDto.setAmount(multiCarTakeGoodsDto.getRentalAmount());//租车金额
					rentCarDto.setCustomerCode(tempRentalMarkEntity.getDriverCode());//司机编码
					rentCarDto.setCustomerName(tempRentalMarkEntity.getDriverName());//司机姓名
					rentCarDto.setCustomerPhone(tempRentalMarkEntity.getDriverPhone());//司机联系方式
					rentCarDto.setIsRepeatemark(tempRentalMarkEntity.getIsRepeateMark());//是否多次标记
					rentCarDto.setRentCarNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
					rentCarDto.setRentCarUseType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
					rentCarDto.setUseCarDate(tempRentalMarkEntity.getUseCarDate());//用车日期
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备完成。。。");
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调cubc接口生成应付单数据准备开始。。。");
					RentCarCubcDto rentCarCubcDto = new RentCarCubcDto();//结算
					rentCarCubcDto.setAmount(multiCarTakeGoodsDto.getRentalAmount());//租车金额
					rentCarCubcDto.setCustomerCode(tempRentalMarkEntity.getDriverCode());//司机编码
					rentCarCubcDto.setCustomerName(tempRentalMarkEntity.getDriverName());//司机姓名
					rentCarCubcDto.setCustomerPhone(tempRentalMarkEntity.getDriverPhone());//司机联系方式
					rentCarCubcDto.setIsRepeatemark(tempRentalMarkEntity.getIsRepeateMark());//是否多次标记
					rentCarCubcDto.setRentCarNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
					rentCarCubcDto.setRentCarUseType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
					rentCarCubcDto.setUseCarDate(tempRentalMarkEntity.getUseCarDate());//用车日期
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUCBC接口生成应付单数据准备完成。。。");
					/**添加租车标记主表信息**/
					tempRentalMarkEntityList.add(tempRentalMarkEntity);
					/**添加结算参数信息**/
					
					rentCarDtoList.add(rentCarDto);
					
					/**添加CUBC参数信息**/
					rentCarCubcDtoList.add(rentCarCubcDto);
					
					
					/**************author:lurker-lee  date:2017-04-18  description:获取多车走货******/
					budgetEntity =new BudgetEntity();
					crmBudgetControlRequestEntity =new CrmBudgetControlRequestEntity();
					//标识字段，0：占用预算占用	1：释放预算	2：查询预算
					crmBudgetControlRequestEntity.setFlag("0");
					//请求时间
					crmBudgetControlRequestEntity.setApplyDate(simpleDate.format(new Date()));
					//理赔ID,但是FOSS这边给的是租车编码，因为唯一，所以在做租车释放的时候，也是根据租车编码来对应关联，FSSC根据租车编码（理赔ID）释放金额
					crmBudgetControlRequestEntity.setClaimID(tempRentalMarkNO);
					//申请人工号
					crmBudgetControlRequestEntity.setEmpCode(user.getEmpCode());
					//申请人部门标杆编码
					crmBudgetControlRequestEntity.setApplyDeptStandCode(org.getUnifiedCode());
					//预算总金额
					crmBudgetControlRequestEntity.setTotalAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
					
					budgetEntity.setAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
					//设置用车月份
					cal.setTime(tempRentalMarkEntity.getUseCarDate());
					String year = String.valueOf(cal.get(cal.YEAR));
					String strMonth=null;
					int month = cal.get(cal.MONTH)+1;
					if(month<10){
						strMonth="0"+month;
					}else{
						strMonth=String.valueOf(month);
					}
					budgetEntity.setMonth(year+strMonth);
					budgetEntityList.add(budgetEntity);
					crmBudgetControlRequestEntity.setBudgetEntitys(budgetEntityList);
					rentalMarkToFSSCList.add(crmBudgetControlRequestEntity);
					/**************author:lurker-lee  date:2017-04-18  description:获取多车走货******/
					
					
					
					//租车标记明细表数据准备
					for(int j=0;j<tempRentalMatkVo.getRentalMarkEntityList().size();j++){
						LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备开始。。。");
						RentalMarkEntity rentalMarkEntity= tempRentalMatkVo.getRentalMarkEntityList().get(j);
						TempRentalMarkDetailEntity tempRentalMarkDetailEntity =new TempRentalMarkDetailEntity(); 
						tempRentalMarkDetailEntity.setId(UUIDUtils.getUUID());//设置ID
						tempRentalMarkDetailEntity.setTempRentalMarkId(tempRentalMarkEntity.getId());//租车Id
						tempRentalMarkDetailEntity.setTempRentalMarkNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
						tempRentalMarkDetailEntity.setBillNo(rentalMarkEntity.getBillNo());//单号
						tempRentalMarkDetailEntity.setBillType(rentalMarkEntity.getBillType());//单据类型
						tempRentalMarkDetailEntity.setWeight(rentalMarkEntity.getWeight());//重量
						tempRentalMarkDetailEntity.setVolume(rentalMarkEntity.getVolume());//体积
						tempRentalMarkDetailEntity.setCreateUserName(tempRentalMarkEntity.getCreateUserName());//创建人
						tempRentalMarkDetailEntity.setCreateUserCode(tempRentalMarkEntity.getCreateUserCode());//创建人工号
						tempRentalMarkDetailEntity.setCreateDate(tempRentalMarkEntity.getCreateDate());//创建时间
						tempRentalMarkDetailEntity.setRentalCarUserType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
						tempRentalMarkDetailEntity.setMarkDepartName(tempRentalMarkEntity.getMarkDepartName());//标记部门
						tempRentalMarkDetailEntity.setMarkDepartCode(tempRentalMarkEntity.getMarkDepartCode());//标记部门
						tempRentalMarkDetailEntity.setBillCreateDate(rentalMarkEntity.getBillCreateTime());
						tempRentalMarkDetailEntity.setConsultPriceNo(tempRentalMatkVo.getConsultPriceNo());
						LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备完成。。。");
						/**添加租车标记明细表信息**/
						tempRentalMarkDetailEntityList.add(tempRentalMarkDetailEntity);
					}
					if(tempRentalMatkVo.getSmallTicketNumList()!=null&&!tempRentalMatkVo.getSmallTicketNumList().isEmpty()){
						for(int k=0;k<tempRentalMatkVo.getSmallTicketNumList().size();k++){//小票单号明细表
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表数据准备开始。。。");
							TemprentalMarkSmticksEntity temprentalMarkSmticksEntity = new TemprentalMarkSmticksEntity();
							temprentalMarkSmticksEntity.setId(UUIDUtils.getUUID());// 设置Id
							temprentalMarkSmticksEntity.setTempRentalMarkId(tempRentalMarkEntity.getId());// 租车id
							temprentalMarkSmticksEntity.setTempRentalMarkNo(tempRentalMarkEntity.getTempRentalMarkNO());// 租车编号
							String smallTicket = tempRentalMatkVo.getSmallTicketNumList().get(k);// 小票单号
							List<String> smallTicketList = new ArrayList<String>();
							smallTicketList.add(smallTicket);
							RentalMarkDto rentalMarkDto = new RentalMarkDto();
							rentalMarkDto.setSmallTicketList(smallTicketList);
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表对应运单号校验开始。。。");
							List<SmallTicketEntity> resultList = temprentalMarkDAO.querysmallTicketNum(rentalMarkDto);

							String wayBillNo = "";
							if (resultList != null && !resultList.isEmpty()) {
								wayBillNo = resultList.get(0).getWayBillNo();// 运单号
							}
							
							// 335284 cubc-gray
							if (wayBillNo == null || "".equals(wayBillNo)) {
								List<SmallTicketEntity> cubcSw = querysmallTicketNumFromCUBC(rentalMarkDto);
								if (cubcSw != null && !cubcSw.isEmpty()) {
									wayBillNo = cubcSw.get(0).getWayBillNo();// 运单号
								}
							}
							//// end cubc-gray

							if (wayBillNo == null || "".equals(wayBillNo)) {
								throw new TfrBusinessException("运单号错误");
							}
							 LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表对应运单号校验完成。。。");
							temprentalMarkSmticksEntity.setSmallTickerNum(smallTicket);//小票单号
							temprentalMarkSmticksEntity.setWayBillNo(wayBillNo);//运单号
							temprentalMarkSmticksEntity.setCreateUserName(tempRentalMarkEntity.getCreateUserName());//创建人
							temprentalMarkSmticksEntity.setCreateUserCode(tempRentalMarkEntity.getCreateUserCode());//创建人工号
							temprentalMarkSmticksEntity.setCreateDate(tempRentalMarkEntity.getCreateDate());//创建人时间
							temprentalMarkSmticksEntity.setActive("Y");
							LOGGER.info("TFR-SCHEDULING 添加租车标记信息，小票单号表数据准备完成。。。");
							/**添加小票号明细表信息**/
							temprentalMarkSmticksEntityList.add(temprentalMarkSmticksEntity);
						}
					}
				}
			}
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车主表。。。");
			temprentalMarkDAO.addTempRentalMarkEntityList(tempRentalMarkEntityList);//添加租车主表信息
			LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车明细表。。。");
		    temprentalMarkDAO.addTempRentalMarkDetailEntityList(tempRentalMarkDetailEntityList);//添加租车明细表信息
			if(temprentalMarkSmticksEntityList!=null&&!temprentalMarkSmticksEntityList.isEmpty()){
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入小票单号表。。。");
			temprentalMarkDAO.addTemprentalMarkSmticksEntityList(temprentalMarkSmticksEntityList);//小票明细信息
			}
			
			if(inviteNo != null && inviteNo.length()>0){
				//310248查询费用承担部门
				rentCarCubcRequest = this.queryBearFeesDept(inviteNo);
				if(rentCarCubcRequest == null){
					throw new TfrBusinessException("在约车时,无费用承担部门！");	
				}else{//此else中的代码是为了处理历史数据，一段时间后可删除 2017.4.22
					OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrationInfoByName(rentCarCubcRequest.getBearFeesDept());
					if(null ==entity){
						throw new TfrBusinessException("在约车时,无费用承担部门！");	
					}
					rentCarCubcRequest.setBearFeesDeptCode(entity.getCode());
					
				}
			}else{
				rentCarCubcRequest.setBearFeesDept(bearFeesDept);
				rentCarCubcRequest.setBearFeesDeptCode(bearFeesDeptCode);
			}
			
			//author:lurker-lee date:2017-04-18 description:调用ESB接口推送给FSSC系统 行驶预算占用
			//根据费用承担部门CODE获取对应的标杆标码
			OrgAdministrativeInfoEntity feeOrg =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(rentCarCubcRequest.getBearFeesDeptCode());//获取部门信息
            //遍历每一个租车费用，传递给报账系统
			if(rentalMarkToFSSCList!=null && rentalMarkToFSSCList.size()>0){
				for(CrmBudgetControlRequestEntity ent:rentalMarkToFSSCList){
					List<BudgetEntity> list=ent.getBudgetEntitys();
					if(list!=null && list.size()>0){
						list.get(0).setDeptStandCode(feeOrg.getUnifiedCode());
					}
					//调用ESB接口
					LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  调用开始！");
					try{
						CrmBudgetControlResponseEntity  responseEnt=aSYRentCarCubcService.pushTemptalMarkFeeInfoToFSSC(ent);
						if(responseEnt==null){
							throw new TfrBusinessException("租车编码："+ent.getClaimID()+" 调用FSSC报账系统接口失败 ");
						}
						LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  FSSC响应返回："+responseEnt.toString());
						LOGGER.info("租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  成功结束！");
						if(0==responseEnt.getIsSucceed()){
							throw new TfrBusinessException("租车编码："+ent.getClaimID()+"  "+responseEnt.getFailure());
						}
					}catch(Exception e){
						throw new TfrBusinessException(e.getMessage());
					}
				}
			}
			
			//// cubcgray 335284-310248
			GrayParameterDto dto = new GrayParameterDto();
			dto.setSourceBillNos(getCustomerList(rentCarCubcDtoList));
			dto.setSourceBillType(CUBCGrayUtil.SBType.SJ.getName());
			VestResponse response = cubcUtil.getCubcGrayDataByCustomer(dto, new Throwable());
			List<VestBatchResult> batchResult = response.getVestBatchResult();
			List<RentCarDto> rentCarDtoList_ = null;
			List<RentCarCubcDto> rentCarCubcDtoList_ = null;
			long millis = System.currentTimeMillis();
			for (VestBatchResult vestBatchResult : batchResult) {
				if (CUBCGrayUtil.SYSTEM_FOSS.equals(vestBatchResult.getVestSystemCode())) {
					rentCarDtoList_ = substractStlList(vestBatchResult.getVestObject(), rentCarDtoList);
				} else {
					rentCarCubcDtoList_ = substractCubcList(vestBatchResult.getVestObject(), rentCarCubcDtoList);
				}
			}
			LOGGER.info("计算两个列表耗时：" + (System.currentTimeMillis() - millis));
			if (rentCarDtoList_ != null && !rentCarDtoList_.isEmpty()) {
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单。。。");
				rentCarService.addRentCar(rentCarDtoList_, user);// 掉财务结算接口
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单结束。。。");
			}
			if (rentCarCubcDtoList_ != null && !rentCarCubcDtoList_.isEmpty()) {
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUBC接口生成应付单。。。");
				rentCarCubcRequest.setEmpCode(user.getEmpCode());
				rentCarCubcRequest.setEmpName(user.getEmpName());
				rentCarCubcRequest.setCurrentDeptCode(user.getCurrentDeptCode());
				rentCarCubcRequest.setCurrentDeptName(user.getCurrentDeptName());
				rentCarCubcRequest.setRentCarCubcList(rentCarCubcDtoList);
				rentCarCubcRequest.setTempRentalMarkDetailEntityList(tempRentalMarkDetailEntityList);
				rentCarCubcRequest.setTemprentalMarkSmticksEntityList(temprentalMarkSmticksEntityList);
				rentCarCubcRequest.setTempRentalMarkEntityList(tempRentalMarkEntityList);
				
				aSYRentCarCubcService.pushaddRentCar(rentCarCubcRequest); // 310248
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调CUBC接口生成应付单结束。。。");
			}
		}
		
	}
	
	/**
	 * 计算cubc列表
	 * @param vestObject
	 * @param rentCarCubcDtoList
	 * @return
	 */
	private List<RentCarCubcDto> substractCubcList(List<String> list, List<RentCarCubcDto> rentCarCubcDtoList) {
		List<RentCarCubcDto> r = new ArrayList<RentCarCubcDto>();
		for (String sNo : list) {
			for (RentCarCubcDto dto : rentCarCubcDtoList) {
				if (dto.getCustomerCode() != null && dto.getCustomerCode().equals(sNo)) {
					r.add(dto);
					rentCarCubcDtoList.remove(dto);
					break;
				}
			}
		}
		LOGGER.info("substractCubcList=" + r);
		return r;
	}
	/**
	 * 计算stl模块列表
	 * @param list
	 * @param rentCarDtoList
	 * @return
	 */
	private List<RentCarDto> substractStlList(List<String> list, List<RentCarDto> rentCarDtoList) {
		List<RentCarDto> r = new ArrayList<RentCarDto>();
		for (String sNo : list) {
			for (RentCarDto dto : rentCarDtoList) {
				if (dto.getCustomerCode() != null && dto.getCustomerCode().equals(sNo)) {
					r.add(dto);
					rentCarDtoList.remove(dto);//下次循环不再扫描该元素
					break;
				}
			}
		}
		LOGGER.info("substractStlList=" + r);
		return r;
	}
	private String[] getCustomerList(List<RentCarCubcDto> rentCarCubcDtoList) {
		List<String> list = new ArrayList<String>();
		for (RentCarCubcDto rentCarCubcDto : rentCarCubcDtoList) {
			list.add(rentCarCubcDto.getCustomerCode());
		}
		String[] ss = list.toArray(new String[list.size()]);
		return ss;
	}
	/**
	 *检查约车编号是否使用过 
	 *@param inviteVehicleNo 约车编号
	 *@author zenghaibin 205109
	 ***/
	private String valiteInviteVehicleNo(String inviteVehicleNo){
		
		return temprentalMarkDAO.valiteInviteVehicleNo(inviteVehicleNo);
	} 
	/**
	 * @author 205109 zenghaibin 
	 * @date 
	 *生成租车编号
	 *@param rentalUse 租车用途
	 **/
	private String creatTempRentalMarkNO(String rentalUse,Date useDate){
		String tempRentalMarkNO="";
		try {
			
			tempRentalMarkNO=tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.ZCBJ,rentalUse);

		} catch (Exception e) {
			throw new TfrBusinessException("生成租车编号异常");
		}
		return tempRentalMarkNO;
	}
	/**
	 *检查小票单号是否已经被标记过
	 *@author zenghaibin
	 *@param  list 小票单号List
	 ***/
	@Override
	public List<String> querySmallTicketRe(List<String> list){
		List<String> resultList = temprentalMarkDAO.querySmallTicketRe(list);
		return resultList;
	}
	
	/**
	 *检查运单开单上门接货且开单输入的工号是否为司机工号
	 *@author zenghaibin
	 *@param  String wayBillNo 运单号
	 *@date 2014-06-19 15:04
	 ***/
	private Long queryDriverNoOperateNo(String wayBillNo){
		
		return temprentalMarkDAO.queryDriverNoOperateNo(wayBillNo);
	}
	
	/**
	 *更新租车标记表TFR.T_OPT_TEMPRENTALMARK 预提状态,预提工作流号,预提工作流处理结果
	 *@author zenghaibin
	 *@param  rentalMarkDtoList 批量更新
	 *@date 2014-07-11 16:04
	 ***/
	public void updateTemprentalMarkAccrued(RentalMarkDto dto){
		//校验传入参数
		if(dto==null || CollectionUtils.isEmpty(dto.getRentCarNos())){
			throw new TfrBusinessException("传入租车编号不能为空！");
		}else{
			temprentalMarkDAO.updateTemprentalMarkAccrued(dto);
		}
	}
	/**
	 * 作废租车标记接口
	 * @author zenghaibin
	 * @date 2014-07-14
	 * @param String tempRentalMarkNo 租车编号
	 ***/
	@Override
	public void invalidRentalMark(List<String> tempRentalMarkNoList,CurrentInfo cInfo)throws Exception{
		Map<String,Object> hsp=new HashMap<String,Object>();
		if(tempRentalMarkNoList==null||tempRentalMarkNoList.size()==0){
			return;
		}
		hsp.put("tempRentalMarkNoList", tempRentalMarkNoList);
		hsp.put("cInfo", cInfo);
		hsp.put("date", new Date());
		temprentalMarkDAO.invalidRentalMark(hsp);
	}
	
	/**
	 *查询租车记录重复项
	 *@author zenghaibin
	 *@date 2014-07-14
	 *@param tempRentalMarkNo 租车编号
	 *@return 返回一个租车记录明细
	 ***/
	@Override
	public List<TempRentalMarkDetailEntity> queryRentalMarkDuplicates(String tempRentalMarkNo)throws Exception{
		
		return temprentalMarkDAO.queryRentalMarkDuplicates(tempRentalMarkNo);
	}	
	
	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 205109-foss-zenghaibin
	 * @date 2015-01-23 上午8:59:05
	 */
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("将文件名转成UTF-8编码时出错");
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}
		
	/**
	 *临时租车标记数据导出
	 *@param  tempRentalMatkVo 导出条件
	 * @date 2015-01-23 上午9：09
	 * @author 205109-foss-zenghaibin
	 ***/
	@Override
	public InputStream exportTempRentalExcel(RentalMarkVo rentalMarkVo) {
		
		InputStream excelStream = null;
		if(null==rentalMarkVo){
			throw new TfrBusinessException("参数为空");
		}
		List<RentalMarkEntity> rentalMarkEntityList =queryRentalMarkEntityList(rentalMarkVo);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(RentalMarkEntity rentalMarkEntity : rentalMarkEntityList){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//租车编号
			columnList.add(rentalMarkEntity.getRentalNum());
			//租车金额
			if(rentalMarkEntity.getRentalAmount()!=null){
				columnList.add(rentalMarkEntity.getRentalAmount().toString());
			}else{
				columnList.add("");
			}
			//单号
			columnList.add(rentalMarkEntity.getBillNo());
			//单据类型
			if(StringUtils.equals(rentalMarkEntity.getBillType(), "waybill")){
				columnList.add("运单");
			}else if(StringUtils.equals(rentalMarkEntity.getBillType(), "handoverbill")){
				columnList.add("交接单");
			}else if(StringUtils.equals(rentalMarkEntity.getBillType(), "deliverbill")){
				columnList.add("派送单");
			}else if(StringUtils.equals(rentalMarkEntity.getBillType(), "stowagebill")){
				columnList.add("配载单");
			}else{
				columnList.add("");
			}
			//车牌号
			columnList.add(rentalMarkEntity.getVehicleNo());
			//约车编号
			columnList.add(rentalMarkEntity.getInviteVehicleNo());
			//司机
			columnList.add(rentalMarkEntity.getDriverName());
			//出发部门
			columnList.add(rentalMarkEntity.getOrigOrgName());
			//到达部门
			columnList.add(rentalMarkEntity.getDestOrgName());
			//重量
			columnList.add(rentalMarkEntity.getWeight().toString());
			//体积
			columnList.add(rentalMarkEntity.getVolume().toString());
			//货物名称
			columnList.add(rentalMarkEntity.getGoodsName());
			//包装
			columnList.add(rentalMarkEntity.getPacking());
			//是否上门接货
			if(StringUtils.equals(rentalMarkEntity.getIsDoorDeliver(), FossConstants.YES)){
				columnList.add("是");
			}else if(StringUtils.equals(rentalMarkEntity.getIsDoorDeliver(), FossConstants.NO)){
				columnList.add("否");
			}else{
				columnList.add("");
			}
			//发货客户名称
			columnList.add(rentalMarkEntity.getCustomerName());
			//发货地址
			columnList.add(rentalMarkEntity.getSendAddress());
			//目的站
			columnList.add(rentalMarkEntity.getDestination());
			//提货方式
			columnList.add(rentalMarkEntity.getPickUpWayName());
			//收货联系人
			columnList.add(rentalMarkEntity.getReceiptContacts());
			//收获地址
			columnList.add(rentalMarkEntity.getReceiptAddress());
			//单据生成时间
			columnList.add(DateUtils.convert(rentalMarkEntity.getBillCreateTime()));
			//租车用途
			if(StringUtils.equals(rentalMarkEntity.getRentalUse(),"SH")){
				columnList.add("送货");
			}else if(StringUtils.equals(rentalMarkEntity.getRentalUse(),"ZH")){
				columnList.add("转货");
			}else if(StringUtils.equals(rentalMarkEntity.getRentalUse(),"JSH")){
				columnList.add("接送货");
			}else if(StringUtils.equals(rentalMarkEntity.getRentalUse(),"JH")){
				columnList.add("接货");
			}
			//租车标记时间
			columnList.add(DateUtils.convert(rentalMarkEntity.getCreateDate()));
			//租车标记部门
			columnList.add(rentalMarkEntity.getMarkDeptName());
			//租车标记操作人
			columnList.add(rentalMarkEntity.getMarkOperator());
			//询价编号
			columnList.add(rentalMarkEntity.getConsultPriceNo());
			rowList.add(columnList);
		}
		String[] rowHeads = {"租车编号","租车金额","单号","单据类型","车牌号","约车编号","司机","出发部门","到达部门","重量",
				"体积","货物名称","包装","是否上门接货","发货客户名称","发货地址",
				"目的站","提货方式","收货联系人","收获地址","单据生成时间","租车用途","租车标记时间"
				,"租车标记部门","租车标记操作人","询价编号"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("临时租车标记");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		
		return excelStream;
	}
	
	/**
	 *
	 *@param  tempRentalMatkVo 导出条件
	 * @date 2016-06-23 上午9：09
	 * @author 313352-gyy
	 ***/
	@Override
	public InputStream exportTempRentalMarkExcel(RentalMarkVo rentalMarkVo,int limit,int start) {
		InputStream excelStream = null;
		if(null==rentalMarkVo){
			throw new TfrBusinessException("参数为空");
		}
		limit=ConstantsNumberSonar.SONAR_NUMBER_1500;
		start=0;
		List<WKTfrBillEntity> wkTfrBillMarkEntityList =(List<WKTfrBillEntity>) queryRentalMarkEntityList(rentalMarkVo, limit, start).get("expressMarkEntityList");
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(WKTfrBillEntity wkMarkEntity : wkTfrBillMarkEntityList){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//租车编号
			columnList.add(wkMarkEntity.getRentalNum());
			//租车金额
			if(wkMarkEntity.getRentalAmount()!=null){
				columnList.add(wkMarkEntity.getRentalAmount().toString());
			}else{
				columnList.add("");
			}
			//单号
			columnList.add(wkMarkEntity.getHandoverBillNo());
			//单据类型
			if(StringUtils.equals(wkMarkEntity.getBillType(), "expresswaybill")){
				columnList.add("快递运单");
			}else if(StringUtils.equals(wkMarkEntity.getBillType(), "expresshandoverbill")){
				columnList.add("短途交接单");
			}else{
				columnList.add("");
			}
			//车牌号
			columnList.add(wkMarkEntity.getVehicleNo());
			//约车编号
			columnList.add(wkMarkEntity.getInviteVehicleNo());
			//司机
			columnList.add(wkMarkEntity.getDriverName());
			//出发部门
			columnList.add(wkMarkEntity.getDepartOrgName());
			//到达部门
			columnList.add(wkMarkEntity.getArriveOrgName());
			//重量
			columnList.add(String.valueOf(wkMarkEntity.getTotalWeight()));
			//体积
            columnList.add(String.valueOf(wkMarkEntity.getTotalVolumn()));
			//货物名称
			columnList.add(wkMarkEntity.getGoodsName());
			//是否上门接货
			if(StringUtils.equals(wkMarkEntity.getIsDoorDeliver(), FossConstants.YES)){
				columnList.add("是");
			}else if(StringUtils.equals(wkMarkEntity.getIsDoorDeliver(), FossConstants.NO)){
				columnList.add("否");
			}else{
				columnList.add("");
			}
			//发货客户名称
			columnList.add(wkMarkEntity.getCustomerName());
			//发货地址
			columnList.add(wkMarkEntity.getSendAddress());
			//目的站
			columnList.add(wkMarkEntity.getDestination());
			//提货方式
			columnList.add(wkMarkEntity.getPickUpWayName());
			//收货联系人
			columnList.add(wkMarkEntity.getReceiptContacts());
			//收获地址
			columnList.add(wkMarkEntity.getReceiptAddress());
			//单据生成时间
			columnList.add(DateUtils.convert(wkMarkEntity.getCreateTime()));
			//租车用途
			if(StringUtils.equals(wkMarkEntity.getRentalUse(),"SH")){
				columnList.add("送货");
			}else if(StringUtils.equals(wkMarkEntity.getRentalUse(),"ZH")){
				columnList.add("转货");
			}else if(StringUtils.equals(wkMarkEntity.getRentalUse(),"JSH")){
				columnList.add("接送货");
			}else if(StringUtils.equals(wkMarkEntity.getRentalUse(),"JH")){
				columnList.add("接货");
			}else {
				columnList.add("");
			}
			//租车标记时间
			columnList.add(DateUtils.convert(wkMarkEntity.getCreateDate()));
			//租车标记部门
			columnList.add(wkMarkEntity.getMarkDeptName());
			//租车标记操作人
			columnList.add(wkMarkEntity.getMarkOperator());
			//询价编号
			columnList.add(wkMarkEntity.getConsultPriceNo());
			rowList.add(columnList);
		}
		String[] rowHeads = {"租车编号","租车金额","单号","单据类型","车牌号","约车编号","司机","出发部门","到达部门","重量",
				"体积","货物名称","是否上门接货","发货客户名称","发货地址",
				"目的站","提货方式","收货联系人","收获地址","单据生成时间","租车用途","租车标记时间"
				,"租车标记部门","租车标记操作人","询价编号"};//定义表头
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("临时租车标记");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return excelStream;
	}
	
	/**
	 * 导出数据使用快递
	 * 查询租车标记数据
	 * @author gouyangyang
	 * @date 2016-6-23 上午 10:10:10
	 * @param RentalMarkDto,int,int
	 * @return List<WKTfrBillEntity>
	 **/
	@SuppressWarnings("unused")
	public List<WKTfrBillEntity> queryMarkEntityList(RentalMarkVo rentalMarkVo,int limit,int start){
		RentalMarkDto rentalMarkDto = rentalMarkVo.getRentalMarkDto();//交接单查询所需参数
		String departmentSignle = rentalMarkDto.getDepartmentSignle();
		if(null==rentalMarkDto){
			throw new TfrBusinessException("rentalMarkDto参数为空");
		}
		List<String> orgCodeList=new ArrayList<String>();
		if(departmentSignle.equals("Profdepartment")||departmentSignle.equals("TransferChild")
				||"TransferCenter".equals(departmentSignle)){
			orgCodeList=this.queryTransDepartmentCode();
		}
		String orgCode =  FossUserContext.getCurrentInfo().getCurrentDeptCode();//用于查询派送单和运单，配载单和交接单不用这个
		List<WKTfrBillEntity> handoverBillList = new ArrayList<WKTfrBillEntity>();//快递交接单数据
		List<WKTfrBillEntity> wKBillList = new ArrayList<WKTfrBillEntity>();  // 快递交接单返回list
		List<WKTfrBillEntity>  expressWaybillList= new ArrayList<WKTfrBillEntity>();  //快递运单数据
		//未使用-352203
//		List<WKTfrBillEntity> wKBillTypeList = new ArrayList<WKTfrBillEntity>();  // 快递运单返回list
		
		Long totalCount5=0L;//快递运单条数
		Long totalCount6=0L;//快递交接单条数
		
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//如果按单号查询
			LOGGER.info("TFR-SCHEDULING 租车标记按单号查询快递开始");
			TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送但和运单所需参数
			//设置tempRentalQueryDto的值，用来查询运单和派送单
			//tempRentalQueryDto.setDeliverbillNos(rentalMarkDto.getDeliverbillNoList());//派送单
			tempRentalQueryDto.setOrgCode(orgCode);//部门编码
			tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
			tempRentalQueryDto.setWaybillNos(rentalMarkDto.getWayBillNoList());//运单号
			//269701--lln--2015-09-06 begin
			//快递运单号
			tempRentalQueryDto.setExpressWaybillNos(rentalMarkDto.getExpressWaybillNoList());
			//269701--lln--2015-09-06 end
			//313352 gyy 
			// 快递交接单
			tempRentalQueryDto.setExpressDeliveryNos(rentalMarkDto.getExpressBillNoList());
			//快递交接单总条数 查询   gouyangyang  313352
			if(this.convertList(rentalMarkDto.getExpressBillNoList())==null){
				totalCount6=0L;
			}else{
				if(orgCodeList!=null&&orgCodeList.size()>0){
					rentalMarkDto.setOrgCodeList(orgCodeList);
				}
				//快递交接单dao的总条数(按单号和日期查询总条数)
				 totalCount6= this.queryExpressMarkEntityCount(rentalMarkDto);
			}
			tempRentalQueryDto.setStart(0);//分页
			tempRentalQueryDto.setLimit(Integer.MAX_VALUE);
			
			//快递交接单总条数 查询   gouyangyang  313352
			if (totalCount6 > 0) {
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息开始");
				handoverBillList = temprentalMarkDAO.queryMarkEntityListByDate(rentalMarkDto);
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息结束");
			}
			LOGGER.info("TFR-SCHEDULING 租车标记按日期查询结束");
			// 快递运单总条数   gyy  313352
			//totalCount5没有使用 - 352203
/*			if(this.convertList(rentalMarkDto.getExpressWaybillNoList())==null){
				totalCount5=0L;
			}*/
			LOGGER.info("TFR-SCHEDULING 租车标记按单号查询结束");
		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期查询
			LOGGER.info("TFR-SCHEDULING 租车标记按日期查询开始");
			TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送单和运单所需参数
			//设置tempRentalQueryDto的值，用来查询运单和派送单
			tempRentalQueryDto.setEndTime(rentalMarkDto.getBillGenerationEndTime());//单据结束时间
			tempRentalQueryDto.setTemprentalMarkNo(rentalMarkDto.getBorrowNo());//租车编号
			tempRentalQueryDto.setOrgCode(orgCode);//部门编码
			tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
			tempRentalQueryDto.setStartTime(rentalMarkDto.getBillGenerationBeginTime());//单据生成开始时间
			tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo());//车牌号
			tempRentalQueryDto.setCreateOrgCode(rentalMarkDto.getCreateDept());
			tempRentalQueryDto.setStart(0);//分页
			tempRentalQueryDto.setLimit(Integer.MAX_VALUE);

			 if("Y".equals(rentalMarkDto.getIsHandoverEirBill())){
				if(orgCodeList!=null&&orgCodeList.size()>0){
					rentalMarkDto.setOrgCodeList(orgCodeList);
				}
					totalCount6 = this.queryExpressEntityCount(rentalMarkDto);//快递交接单dao的总条数
			 }
		     if( "Y".equals(rentalMarkDto.getIsExpressWayBill())){
				// 快递运单按日期查询  313352 gouyangyang	
				ExpressDateDto inputDto = new ExpressDateDto();
				inputDto.setPageSize(ConstantsNumberSonar.SONAR_NUMBER_50);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				inputDto.setBillGenerationBeginTime(sdf.format(rentalMarkDto.getBillGenerationBeginTime()));
				inputDto.setBillGenerationEndTime(sdf.format(rentalMarkDto.getBillGenerationEndTime()));
				inputDto.setOperatorDeptNo(rentalMarkDto.getCreateDept());  //登录部门编码
				inputDto.setWaybillNo("");
				inputDto.setCurrentPageNo(start+1);
				LOGGER.info("inputDto.toString()"+inputDto.toString());
				expressWaybillList= getExpressListInfo(inputDto,rentalMarkDto.getCreateDept());
			  }
				// 如果传入的是快递运单    313352 gouyangyang
			 if("Y".equals(rentalMarkDto.getIsExpressWayBill())){
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
					}
				} 
			 
			 if(this.convertList(rentalMarkDto.getExpressBillNoList())==null){
					totalCount6=0L;
				}else{
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
					}
					//快递交接单dao的总条数(按单号和日期查询总条数)
					 totalCount6= this.queryExpressMarkEntityCount(rentalMarkDto);
				}
			// 快递交接单接口()
			if (totalCount6 > 0) {
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息开始");
				handoverBillList = temprentalMarkDAO.queryMarkEntityListByDate(rentalMarkDto);
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递交接单信息结束");
			}
			LOGGER.info("TFR-SCHEDULING 租车标记按日期查询结束");
			}
		    // 快递交接单   gyy  313352
			if(handoverBillList!=null&&!handoverBillList.isEmpty()&&handoverBillList.size()>0){
				wKBillList.addAll(handoverBillList);
			}
			// 快递运单       gyy  313352
			if(expressWaybillList!=null&&!expressWaybillList.isEmpty()&&expressWaybillList.size()>0){
				wKBillList.addAll(expressWaybillList);
			}
			return 	wKBillList;
		}
	
	/**
	 * 导出数据使用
	 * 查询租车标记数据
	 * @author zenghaibin
	 * @date 2014-5-30 上午 10:10:10
	 * @param RentalMarkDto,int,int
	 * @return List<RentalMarkEntity>
	 **/
	@SuppressWarnings("unused")
	private List<RentalMarkEntity>  queryRentalMarkEntityList(RentalMarkVo rentalMarkVo){
		RentalMarkDto rentalMarkDto = rentalMarkVo.getRentalMarkDto();//交接单查询所需参数
		String departmentSignle = rentalMarkDto.getDepartmentSignle();

		if(null==rentalMarkDto){
			throw new TfrBusinessException("rentalMarkDto参数为空");
		}
		List<String> orgCodeList=new ArrayList<String>();
		if(departmentSignle.equals("Profdepartment")||departmentSignle.equals("TransferChild")
				||"TransferCenter".equals(departmentSignle)){
			orgCodeList=this.queryTransDepartmentCode();
		}
		
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();//用于查询派送单和运单，配载单和交接单不用这个
		List<RentalMarkEntity> arrayList = new ArrayList<RentalMarkEntity>();//查询结果list
		List<RentalMarkEntity> handoverList = new ArrayList<RentalMarkEntity>();//交接单数据
		List<RentalMarkEntity> wayBillList = new ArrayList<RentalMarkEntity>();//运单数据
		List<RentalMarkEntity> deliverBill = new ArrayList<RentalMarkEntity>();//派送单数据
		List<RentalMarkEntity> stowageBillList = new ArrayList<RentalMarkEntity>();//配载单数据
		//269701--lln--2015-09-06 begin
		//未使用-352203
//		List<RentalMarkEntity> expressList = new ArrayList<RentalMarkEntity>();//快递单数据
		//269701--lln--2015-09-06 end
		// 313352 gouyangyang
		List<WKTfrBillEntity> handoverBillList = new ArrayList<WKTfrBillEntity>();//快递交接单数据
	   // WKTfrBillDto wkTfrBillDto= rentalMarkVo.getRentalMarkDto(); // 快递交接单所需要的参数
		//未使用-352203
//		List<WKTfrBillEntity> wKBillList = new ArrayList<WKTfrBillEntity>();
		Long totalCount1=0L;//交接单条数
		Long totalCount2=0L;//运单条数
		Long totalCount3=0L;//派送单条数
		Long totalCount4=0L;//配载单条数
		Long totalCount5=0L;//快递单条数
		Long totalCount6=0L;//快递交接单条数
		if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBB)){//如果按单号查询
			LOGGER.info("TFR-SCHEDULING 租车标记按单号查询开始");
			TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送但和运单所需参数
			//设置tempRentalQueryDto的值，用来查询运单和派送单
			tempRentalQueryDto.setDeliverbillNos(rentalMarkDto.getDeliverbillNoList());//派送单
			tempRentalQueryDto.setOrgCode(orgCode);//部门编码
			tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
			tempRentalQueryDto.setWaybillNos(rentalMarkDto.getWayBillNoList());//运单号
			//269701--lln--2015-09-06 begin
			//快递运单号
			tempRentalQueryDto.setExpressWaybillNos(rentalMarkDto.getExpressWaybillNoList());
			//269701--lln--2015-09-06 end
			tempRentalQueryDto.setStart(0);//分页
			tempRentalQueryDto.setLimit(Integer.MAX_VALUE);
			
			//交接单总条数 查询
			if(this.convertList(rentalMarkDto.getHandoverBillNoList())==null){
				totalCount1=0L;
			}else{
				if(orgCodeList!=null&&orgCodeList.size()>0){
					rentalMarkDto.setOrgCodeList(orgCodeList);
				}
				 totalCount1 = this.queryRentalMarkEntityCount(rentalMarkDto);//交接单dao的总条数
			}
			//零担运单总条数 查询
			if(this.convertList(rentalMarkDto.getWayBillNoList())==null){
				totalCount2=0L;
			}else{
				totalCount2= countTempWayBillByBillNos(tempRentalQueryDto);//运单的总条数
			}
			//派送单总条数 查询
			if(this.convertList(rentalMarkDto.getDeliverbillNoList())==null){
				totalCount3=0L;
			}else{
				 totalCount3 = countByDeliverBillParams(tempRentalQueryDto);//派送单的总条数
			}
			//配载单总条数 查询
			if(this.convertList(rentalMarkDto.getStowageBillNoList())==null){//配载单的总条数
				totalCount4=0L;
			}else{
				totalCount4=this.queryStowageBillListCount(rentalMarkDto);
			}
			//269701--lln--2015-09-06 begin
			//快递运单总条数 查询
			if(this.convertList(rentalMarkDto.getExpressWaybillNoList())==null){//快递运单的总条数
				totalCount5=0L;
			}else{
				//totalCount5= countExpressByWayBill(tempRentalQueryDto);
			}

			//快递交接单总条数 查询   gouyangyang  313352
			if(this.convertList(rentalMarkDto.getExpressBillNoList())==null){
				totalCount6=0L;
			}else{
				if(orgCodeList!=null&&orgCodeList.size()>0){
					rentalMarkDto.setOrgCodeList(orgCodeList);
				}
				 totalCount6= this.queryExpressEntityCount(rentalMarkDto);//快递交接单dao的总条数
			}
			 
			if(totalCount1>0){
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
				handoverList=temprentalMarkDAO.queryRentalMarkEntityListByBillNo(rentalMarkDto);//交接单
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
			}
			

			if(totalCount2>0){//如果运单条数大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息开始");
				wayBillList=queryTempWaybillByBillNo(tempRentalQueryDto,"WAYBILL");
				LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息结束");
			}
			if(totalCount3>0){//如果派送单条数大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询派送单信息开始");
				deliverBill=queryTempWaybillByDeliver(tempRentalQueryDto,"DELIVERBILL");
				LOGGER.info("TFR-SCHEDULING 租车标记查询派送单信息结束");
			}
			//派送单接口 
			if(totalCount4>0){//如果配载单条数大于0
				
				LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息开始");
				stowageBillList=temprentalMarkDAO.queryStowageBillListByBillNo(rentalMarkDto);//调用接送或的查询派送单接口
				LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息结束");
			}
			//269701--lln--2015-09-06 begin
				//快递运单接口 
			if(totalCount5>0){//如果快递运单条数大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息开始");
				//expressList=searchExpressWaybill(tempRentalQueryDto,"WAYBILL");
				LOGGER.info("TFR-SCHEDULING 租车标记查询快递运单信息结束");
			}
			//269701--lln--2015-09-06 end
			
			//313352--gyy--2016-06-16 begin
			if(totalCount6>0){//如果快递交接单数据大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
				//handoverBillList=temprentalMarkDAO.queryRentalMarkEntityListByDate(rentalMarkDto);
				handoverBillList=temprentalMarkDAO.queryMarkEntityListByDate(rentalMarkDto);
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
			}
			//313352--gyy--2016-06-16 end
			LOGGER.info("TFR-SCHEDULING 租车标记按单号查询结束");

		}else if(rentalMarkDto.getQueryType().equals(InviteVehicleConstants.QBD)){//按日期查询
			LOGGER.info("TFR-SCHEDULING 租车标记按日期查询开始");
			TempRentalQueryDto tempRentalQueryDto= new TempRentalQueryDto();//派送单和运单所需参数
			//设置tempRentalQueryDto的值，用来查询运单和派送单
			tempRentalQueryDto.setEndTime(rentalMarkDto.getBillGenerationEndTime());//单据结束时间
			tempRentalQueryDto.setTemprentalMarkNo(rentalMarkDto.getBorrowNo());//租车编号
			tempRentalQueryDto.setOrgCode(orgCode);//部门编码
			tempRentalQueryDto.setQueryType(rentalMarkDto.getQueryType());//查询类型，按日期或者单号
			tempRentalQueryDto.setStartTime(rentalMarkDto.getBillGenerationBeginTime());//单据生成开始时间
			tempRentalQueryDto.setVehicleNo(rentalMarkDto.getVehicleNo());//车牌号
			tempRentalQueryDto.setCreateOrgCode(rentalMarkDto.getCreateDept());
			tempRentalQueryDto.setStart(0);//分页
			tempRentalQueryDto.setLimit(Integer.MAX_VALUE);
			if("Y".equals(rentalMarkDto.getIsHandoverBill())){
				if(orgCodeList!=null&&orgCodeList.size()>0){
					rentalMarkDto.setOrgCodeList(orgCodeList);
				}
				totalCount1 = this.queryRentalMarkEntityCount(rentalMarkDto);//交接单dao的总条数
			}
			 if("Y".equals(rentalMarkDto.getIsWayBill())){
			 totalCount2 = selectWayBillByDateCount(tempRentalQueryDto);//运单的总条数
			 }
			 if("Y".equals(rentalMarkDto.getIsDeliverBill())){
				 totalCount3 = countByDeliverBillParams(tempRentalQueryDto);//派送单的总条数
			 }
			 if("Y".equals(rentalMarkDto.getIsStowageBill())){
				 totalCount4 = this.queryStowageBillListCount(rentalMarkDto);//配载单的总条数
			 }
			 if("Y".equals(rentalMarkDto.getIsHandoverEirBill())){
					if(orgCodeList!=null&&orgCodeList.size()>0){
						rentalMarkDto.setOrgCodeList(orgCodeList);
				}
					totalCount6 = this.queryExpressEntityCount(rentalMarkDto);//快递交接单dao的总条数
			}
			
			//查询交接单数据
			if(totalCount1>0){//如果交接单数据大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息开始");
				handoverList=temprentalMarkDAO.queryRentalMarkEntityListByDate(rentalMarkDto);
				LOGGER.info("TFR-SCHEDULING 租车标记查询交接单信息结束");
			}
			//运单接口 
			 if(totalCount2>0){
				 LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息开始");
				 wayBillList=queryTempWaybillByDate(tempRentalQueryDto,"WAYBILL");//运单
				 LOGGER.info("TFR-SCHEDULING 租车标记查询运单信息结束");
			 }
				 
			//派送单接口 
			 if(totalCount3>0){//如果派送单数据大于0
				 LOGGER.info("TFR-SCHEDULING 租车标记派送单信息开始");
				 deliverBill=queryTempWaybillByDeliver(tempRentalQueryDto,"DELIVERBILL");//派送单
				 LOGGER.info("TFR-SCHEDULING 租车标记查询派送信息结束");
			 }
			//配载单
			if(totalCount4>0){//如果派送单条数大于0
				LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息开始");
				stowageBillList=temprentalMarkDAO.queryStowageBillListByDate(rentalMarkDto);
				LOGGER.info("TFR-SCHEDULING 租车标记查询配载单信息结束");
			}
				LOGGER.info("TFR-SCHEDULING 租车标记按日期查询结束");
		}else{
			return null;
		}
		if(handoverList!=null&&!handoverList.isEmpty()&&handoverList.size()>0){
			arrayList.addAll(handoverList);
		}
		if(wayBillList!=null&&!wayBillList.isEmpty()&&wayBillList.size()>0){
			arrayList.addAll(wayBillList);
		}
		if(deliverBill!=null&&!deliverBill.isEmpty()&&deliverBill.size()>0){
			arrayList.addAll(deliverBill);
		}
		if(stowageBillList!=null&&!stowageBillList.isEmpty()&&stowageBillList.size()>0){
			arrayList.addAll(stowageBillList);
		}
		//269701--lln--2015-09-06 begin   expressList
		//快递运单 
//		if(expressList!=null&&!expressList.isEmpty()&&expressList.size()>0){
//			arrayList.addAll(expressList);
//		}
		return arrayList;
	}
	
	
	

	/**
	 * 营业部=SalesDepartment  
	 * 营业部驻地部门=SalesDepartmentStation
	 * 车队= TransferChild 
	 * 外场= TransferCenter
	 * Profdepartment：以上都不是
	 * 根据部门编码查询临时租车标记有效其天数
	 * 1、如果部门标识departmentSignle为营业部（非驻地部门）则按营业部属性查询有效期天数
	 * 2、如果部门为驻地，则须再去判断是否为派送部（是做到达、是可派送、为驻地部门），再按派送部属性查询有效期天数
	 * 3.如果为车队则，按车队查询有效期天数
	 * 4.如果是外场则按外场查询有效期天数
	 * 5、如果都不是
	 *@author 205109-zenghaibin-foss 
	 *@date 2015-01-29 11:10:55
	 * @param departmentSignle 部门标识（营业部、营业部驻地部门、外场、车队）；
	 * @param orgCode 部门编码
	 * 
	 ***/
	private String queryRentalMarkValidDays(String departmentSignle,String orgCode){
		String type="";
		String tempRentalValidDays="";
		if(StringUtils.equals(departmentSignle, "SalesDepartment")){
		//如果是营业部
			type="BUSINESS_DEPARTMENT_PROPERTIES";
		}else if(StringUtils.equals(departmentSignle, "SalesDepartmentStation")){
		//如果是驻地部门，查询是否为派送部
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCodeNoCache(orgCode);
			if(null!=saleDepartmentEntity
					&&StringUtils.equals(saleDepartmentEntity.getArrive(),FossConstants.YES)
					&&StringUtils.equals(saleDepartmentEntity.getDelivery(),FossConstants.YES)){
				//如果可到达，可派送，并且是驻地部门，则为派送不
				type="DELIVERY_OF_PROPERTY";
			}else{//否则是营业部
				type="BUSINESS_DEPARTMENT_PROPERTIES";
			}
			
		}else if(StringUtils.equals(departmentSignle, "TransferChild")){
			//如果是车队或者是车队子部门
			type="CAR_TEAM_ATTRIBUTES";
		}else if(StringUtils.equals(departmentSignle, "TransferCenter")){
			//如果是外场
			type="FIELD_PROPERTIES";
		}else{
			throw new TfrBusinessException("请给该部门配租车标记有效期");
		}
		try{
			tempRentalValidDays=temporaryRentalCarMarkTimeManagementService.querySetTimeByDeptAttributes(type);

		}catch(Exception e){
			throw new TfrBusinessException("调用综合接口查询租车有效期时间异常"+e.toString());
		}
		if(StringUtils.isBlank(tempRentalValidDays)){
			throw new TfrBusinessException("该部门未配置租车标记有效期");
		}
		return tempRentalValidDays;
	}
	
	
	/**
	 * 快递机场扫描单号(单号或日期查询)
	 * @param expressDeliveryDto
	 * @author 313352  gouyangyang
	 * @param createDept 
	 * @param flag 是否传入部门
	 * @return   
	 */
	private List<WKTfrBillEntity> getWKExpressListInfo(AirExpressDateDto airExpressDateDto, String createDept) {
		ExpressAirportEntity expressAirportEntity = fossToWkService.expressAirportToWk(airExpressDateDto);
		List<QueryExpressAirportEntity> queryExpressAirportEntities = null;
		List<WKTfrBillEntity> resultListAir = new ArrayList<WKTfrBillEntity>();
		if(expressAirportEntity != null){
			queryExpressAirportEntities = expressAirportEntity.getData();
		}
		if(expressAirportEntity.getExMsg()!=null && expressAirportEntity.getStatus()==1){
			Log.error("快递机场扫描单据查询为空");
			return null;
		}else{
			if(queryExpressAirportEntities!=null && queryExpressAirportEntities.size()>0){
				for(QueryExpressAirportEntity tempAir:queryExpressAirportEntities){
					WKTfrBillEntity billEntity = new WKTfrBillEntity();
					billEntity.setHandoverBillNo(tempAir.getAirscanTaskNo()); 
					billEntity.setVehicleNo(tempAir.getVehicleNo());
					billEntity.setTotalVolumn(tempAir.getVolume());
					billEntity.setTotalWeight(tempAir.getWeight());
					billEntity.setCreateTime(tempAir.getCreateTime());					
					billEntity.setBillType("airPortBill");
					resultListAir.add(billEntity);
				}
				this.setTotalNumber(expressAirportEntity.getData().size());
			} else{
				setTotalNumber(0);
			}
		}
		
		return resultListAir;
	}
	
	/**
	 * 快递运单(快递运单按单号或日期查询)
	 * @param expressDeliveryDto
	 * @author 313352  gouyangyang
	 * @param createDept 
	 * @param flag 是否传入部门
	 * @return   
	 */
		private List<WKTfrBillEntity> getExpressListInfo(ExpressDateDto expressDateDto, String createDept) {
		CourierWaybillEntity courierWaybillEntity = fossToWkService.expressDeliveryToWk(expressDateDto);
		List<QueryWaybillToFoss> queryWaybillToFossList = null;
		List<WKTfrBillEntity> resultList = new ArrayList<WKTfrBillEntity>();
		if (courierWaybillEntity != null) {
			 queryWaybillToFossList = courierWaybillEntity.getDetails();
		}
		if ( courierWaybillEntity != null && "0".equals(courierWaybillEntity.getStatus())) {
			Log.error("快递运单悟空系统查询失败"+courierWaybillEntity);
			return new ArrayList<WKTfrBillEntity>();
		} else {
			if (queryWaybillToFossList!= null && queryWaybillToFossList.size()>0) {
				for(QueryWaybillToFoss temp : queryWaybillToFossList){
					WKTfrBillEntity billEntity = new WKTfrBillEntity();
					billEntity.setHandoverBillNo(temp.getWaybillNo());  // 运单号
					billEntity.setHandoverType(temp.getBillType());     // 单据类型
					billEntity.setTotalWeight(temp.getGoodsWeight());   // 重量
					billEntity.setTotalVolumn(temp.getGoodsVolume());   // 体积
					billEntity.setGoodsName(temp.getGoodsName());  // 货物名称
					billEntity.setPacking(temp.getGoodsPackage()); // 货物包装
					billEntity.setCustomerName(temp.getShipperContactPerson()); // 寄件联系人
					billEntity.setCreateTime(temp.getCreateTime());  //单据生成时间
					billEntity.setDestination(temp.getDestinationPointName());// 目的站
					billEntity.setReceiptContacts(temp.getConsigneeContactPerson()); //  收货联系人
                   // billEntity.setReceiptAddress(temp.getConsigneeCompany());  // 收货地址(收件单位)
                    billEntity.setPickUpWayName(temp.getReceiveMethod());   // 提货方式
                    billEntity.setBillType(temp.getBillType()); //设置运单类型
                    billEntity.setSendAddress(temp.getConsignerArea());//发货人地址
                    billEntity.setReceiptAddress(temp.getConsigneeArea()); // 收货人地址
                    //billEntity.setCreateOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(createDept).getName());
					resultList.add(billEntity);
				}
			}
			this.setTotalNumber(courierWaybillEntity.getTotalNumber());
			return resultList;
		}
	}
	
	@Override
	public List<String> queryTaskNoOfBillno(String billNo) {
		String id=temprentalMarkDAO.queryTaskNo(billNo);
		List<String> billNos=temprentalMarkDAO.queryBillNo(id);
		// TODO Auto-generated method stub
		return billNos;
	}
	
	@Override
	public RentalMarkEntity queryBillNoInfo(String billNo) {
		// TODO Auto-generated method stub
		RentalMarkEntity rentalMarkEntity= temprentalMarkDAO.queryBillNoinfo(billNo);
					return rentalMarkEntity;
	}
	
	/**
	 * 短途查询租车标记数据
	 * @author ruilibao
	 * @date 2016-12-10
	 * @param ShortRentalMarkVo,int,int
	 * @return List<ShortRentalMarkEntity>
	 **/
	@Override
	public HashMap<String,Object> queryShortRentalMarkEntityList(ShortRentalMarkVo shortRentalMarkVo,int limit,int start){
		
		LOGGER.error("查询租车标记数据Service方法开始...");
		// 页面shortRentalMarkVo为空
		if(shortRentalMarkVo==null){
			return null;
		}
		//租车标记查询所需参数
		ShortRentalMarkDto shortRentalMarkDto = shortRentalMarkVo.getShortRentalMarkDto();
		//页面传入的查询对象是空
		if(shortRentalMarkDto==null){
			return null;
		}
		LOGGER.error("页面过来的查询参数 : " + shortRentalMarkDto.toString());
		
		// 获取页面传过来的当前部门
		String departmentSignle = shortRentalMarkDto.getDepartmentSignle();
		
		List<String> orgCodeList=new ArrayList<String>();
		if(departmentSignle.equals("Profdepartment")||departmentSignle.equals("TransferChild")
				||"TransferCenter".equals(departmentSignle)){
			orgCodeList = this.queryTransDepartmentCode();
		}
		//获取当前登录部门
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		
		//设置部门列表
		if (CollectionUtils.isNotEmpty(orgCodeList) && orgCodeList.size() > 0){
			shortRentalMarkDto.setOrgCodeList(orgCodeList);
		}
		
		//设置当前登录部门
		if (StringUtils.isNotEmpty(orgCode)) {
			shortRentalMarkDto.setOrgCode(orgCode);
		}
		//查询结果map
		HashMap<String,Object> queryMap = new HashMap<String,Object>();
		//查询结果list
		List<ShortRentalMarkEntity> arrayList = new ArrayList<ShortRentalMarkEntity>();
		//车辆任务总记录条数
		Long totalCount=0L;
		
		// 判断快递交接单信息是否为空 
		LOGGER.error("TFR-SCHEDULING 短途租车标记查询条数开始");
		//短途租车的总条数
		totalCount = this.queryShortRentalMarkEntityCount(shortRentalMarkDto);
		LOGGER.error("TFR-SCHEDULING 短途租车标记查询条数结束");
		
		if(totalCount>0){
			LOGGER.error("TFR-SCHEDULING 短途租车标记查询信息开始");
			arrayList=temprentalMarkDAO.queryShortRentalMarkEntityList(shortRentalMarkDto,limit,start);
			LOGGER.error("TFR-SCHEDULING 短途租车标记查询息结束");
		}
	
		queryMap.put("shortRentalMarkEntityList", arrayList);
		queryMap.put("totalCount", totalCount);
		return queryMap;
	}
	
	/**
	 * 根据出发部门、到达部门算出最大公里数
	 * @param entityList
	 * @modify 332209
	 * @return
	 */
	@Override
	public Long queryMaxDistanceShort(List<ShortRentalMarkEntity> shortEntityList){
		LineEntity lineEntity = new LineEntity(); 
		long maxDistance =0L;
		for(ShortRentalMarkEntity rentalMarkEntity:shortEntityList){
			lineEntity.setOrginalOrganizationCode(rentalMarkEntity.getOrigOrgCode());
			lineEntity.setDestinationOrganizationCode(rentalMarkEntity.getDestOrgCode());
			List<LineEntity> line=LineService.querySimpleLineListByCondition(lineEntity);
			if(line!=null&&line.size()>0){
				long currentDistance=line.get(0).getDistance();
				if(currentDistance>maxDistance){
					maxDistance=currentDistance;
				}
			}
		}
		return maxDistance;
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
		
		return temprentalMarkDAO.queryTruckTaskRepeatMark(truckTaskIdList);
	}
	
	/**
	*检查约车编号
	*@author ruilibao
	*@date 2014-6-11  10:10:10
	*@param inviteVehicleNo
	*@return RentalMarkVo
	**/
	@Override
	public ShortRentalMarkVo queryShortInviteVehicleNo(String inviteVehicleNo) {
	 ShortRentalMarkVo resultVo=new ShortRentalMarkVo();
	 ShortRentalMarkVo result=null;
		if(inviteVehicleNo!=null&&!inviteVehicleNo.equals("")){
			result = temprentalMarkDAO.queryShortInviteVehicleNo(inviteVehicleNo);
		}
		if(result==null){
			resultVo.setAcceptPerson("");
			resultVo.setAcceptPersonCode("");
			resultVo.setInviteVehicleNo("");
			resultVo.setInviteState("");
			resultVo.setRentalAmount("");
		}
		else{
			resultVo=result;
		}
		return resultVo;
	}
	
	/**
	 *查询交接单总条数 
	 *@author ruilibao
	 *@date 2014-5-30 上午 10:10:10
	 *@param RentalMarkDto
	 *@return Long
	 **/
	@Override
	public Long queryShortRentalMarkEntityCount(ShortRentalMarkDto shortRentalMarkDto) {
		//查询总记录数
		return temprentalMarkDAO.queryShortRentalMarkEntityCount(shortRentalMarkDto);
	}
	
	/**
	 *添加租车标记 
	 *author zenghaibin
	 *@date 2014-6-11  10:10:10
	 *@param tempRentalMatkVo:租车标记的vo
	 * @throws ParseException 
	 **/
	@Override
	@Transactional
	public void addShortTempRentalMark(ShortTempRentalMatkVo shortTempRentalMatkVo) {
		
		if(shortTempRentalMatkVo==null){
			throw new TfrBusinessException("数据参数为空");
		}
		
		List<TempRentalMarkEntity> tempRentalMarkEntityList = new ArrayList<TempRentalMarkEntity>();//租车标记主表List
		List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList = new ArrayList<TempRentalMarkDetailEntity>();//明细表List
		List<RentCarDto> rentCarDtoList = new ArrayList<RentCarDto>();//调结算接口，所需参数产生应付单
		
		// author:1ee date:2017-04-27  调用CUBC生成短途租车标记下游应付单单据
		//RentalMarkDO rentalMarkDO = new RentalMarkDO();
		
		
		//把车辆任务ID存放起来
		String truckTaskId = shortTempRentalMatkVo.getShortRentalMarkEntityList().get(0).getTruckTaskId();
		//根据车辆任务ID查询运单件数
		Long shallTakeGoodsQyt= temprentalMarkDAO.queryTruckTaskWaybillCount(truckTaskId);
		//当前时间
		Date currentDate = new Date();
		//当前登入人员对象
		CurrentInfo user = FossUserContext.getCurrentInfo();
		//获取当前登录部门
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//获取部门信息
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);
		TempRentalMarkEntity tempRentalMarkEntityTmp = new TempRentalMarkEntity();
		//定义时间格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//有效期
		//String tempRentalValidDays =this.queryRentalMarkValidDays(shortTempRentalMatkVo.getDepartmentSignle(), orgCode); 

		
		/***********author:lee date:2017-04-26   note：封装多车走货的租车金额 传递给报账系统*****/
	    //封装调用CUBC接口实体,进行分流生成CUBC的应付单
	    //RentCarCubcRequest rentCarCubcRequst = new RentCarCubcRequest();
	    //查询费用承担部门用
 	   /* String inviteNo = shortTempRentalMatkVo.getInviteVehicleNo();
 	    if(inviteNo != null && inviteNo.length()>0){
			//查询费用承担部门
			rentCarCubcRequst = this.queryBearFeesDept(inviteNo);
			if(rentCarCubcRequst == null){
				throw new TfrBusinessException("在约车时,无费用承担部门！");	
			}
		}else{
			rentCarCubcRequst.setBearFeesDept(shortTempRentalMatkVo.getBearFeesDept());
			rentCarCubcRequst.setBearFeesDeptCode(shortTempRentalMatkVo.getBearFeesDeptCode());
		}
		BudgetEntity budgetEntity =null;
		CrmBudgetControlRequestEntity crmBudgetControlRequestEntity=null;
		List<BudgetEntity> budgetEntityList = null;
		List<CrmBudgetControlRequestEntity> rentalMarkToFSSCList = null;
		//转换用车日期，并获取用车月份
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
		/***********author:lurker-lee date:2017-04-26   note：封装多车走货的租车金额 传递给报账系统*****/
		
		/**
		 *标记部门是否是出发部门 
		 */
		//获取顶级车队
//		OrgAdministrativeInfoEntity topFleet= orgAdministrativeInfoComplexService.getTopFleetByCode(org.getCode());
//		//查询车队所属外场
//		MotorcadeEntity superOrg = null;
//		//查询所属外场
//		if (topFleet != null){
//			superOrg = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
//		}
//		
//		if (null != superOrg&&superOrg.getTransferCenter()!=null
//				 &&!"".equals(superOrg.getTransferCenter())) {
//				for (int i = 0 ; i < shortTempRentalMatkVo.getShortRentalMarkEntityList().size(); i++){
//					ShortRentalMarkEntity entity = shortTempRentalMatkVo.getShortRentalMarkEntityList().get(i);
//					if (!entity.getOrigOrgCode().equals(superOrg.getTransferCenter())) {
//						throw new TfrBusinessException("无法标记,请切换" + entity.getOrigOrgName() + "部门进行标记");
//					}
//				}
//	 	}
		
		/**
		 * 校验租车标记日期
		 */
//		for(int i=0;i<shortTempRentalMatkVo.getShortRentalMarkEntityList().size();i++){
//			Long from =0L;
//			Long to=0L;
//			try {
//				from = df.parse(df.format(shortTempRentalMatkVo.getShortRentalMarkEntityList().get(i).getUseCareDate())).getTime();//开始时间
//				to = df.parse(df.format(new Date())).getTime();//结束时间
//				} catch (ParseException e) {
//					throw new TfrBusinessException("租车标记有效期日期转换异常！");
//				}
//			long result = (to - from) / (1000 * 60 * 60 * 24);
//			if(result>Long.parseLong(tempRentalValidDays)){
//				throw new TfrBusinessException("该车辆任务:"+shortTempRentalMatkVo.getShortRentalMarkEntityList().get(i).getTruckTaskId()+"不在标记有效期内");
//			}
//		}
		
		/**
		 *判断车辆任务是否完结
		 ***/
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，判断车辆任务是否完结校验开始。。。");
		if(!shortTempRentalMatkVo.getShortRentalMarkEntityList().isEmpty()){
			//根据车辆任务ID查询车辆状态
			String truckTaskState = temprentalMarkDAO.queryTruckTaskState(truckTaskId);
			if (!"ARRIVED".equals(truckTaskState) && !"UNLOADED".equals(truckTaskState)) {
				throw new TfrBusinessException("车辆任务尚未结束,不能标记");
			}
		}
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，判断车辆任务是否完结校验结束。。。");
		
		/**
		 *重复标记
		 ***/
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，重复标记校验开始。。。");
		if(!shortTempRentalMatkVo.getShortRentalMarkEntityList().isEmpty()){
			//根据车辆任务ID查询租车标记表是否有被标记过
			Long markCount = temprentalMarkDAO.queryTruckTaskMarkCount(truckTaskId);
			if (markCount > 0) {
				throw new TfrBusinessException("同一个车辆任务不能重复标记");
			}
		}
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，重复标记校验结束。。。");
		
		
		if(shortTempRentalMatkVo.getUseCareDate()==null){
			throw new TfrBusinessException("用车日期不能为空");
		}
		
		if(shortTempRentalMatkVo.getCarReason()==null||"".equals(shortTempRentalMatkVo.getCarReason())){
			throw new TfrBusinessException("用车原因不能为空");
		}
		
		tempRentalMarkEntityTmp.setUseCarDate(shortTempRentalMatkVo.getUseCareDate());//用车时间
		String carReason=shortTempRentalMatkVo.getCarReason();//用车原因
		if(InviteVehicleConstants.RENTALMARK_SHORTHANDED.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_shortHanded;
		}else if(InviteVehicleConstants.RENTALMARK_SPECIALGOODS.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_specialGoods;
		}else if(InviteVehicleConstants.RENTALMARK_EXHIBITION.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_exhibition;
		}else if(InviteVehicleConstants.RENTALMARK_WAREHOUSEENTRY.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_warehouseEntry;
		}else if(InviteVehicleConstants.RENTALMARK_LACKVEHICLES.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_lackVehiclesS;
		}else if(InviteVehicleConstants.RENTALMARK_LIMITLINE.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_limitLine;
		}else if(InviteVehicleConstants.RENTALMARK_CUSTOMERREASON.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_customerReason;
		}else if(InviteVehicleConstants.RENTALMARK_LONGLIVERY.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_longDelivery;
		}else if(InviteVehicleConstants.RENTALMARK_EXTERNALCAUSES.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_externalCauses;
		}else if(InviteVehicleConstants.RENTALMARK_OTHERS.equals(carReason)){
			carReason=InviteVehicleConstants.RENTALMARK_others;
		}else{
			throw new TfrBusinessException("没有该类型的用车原因");
			
		}
		tempRentalMarkEntityTmp.setUserCarReason(carReason);//用车原因	
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，约车编号校验开始。。。");
		String rentalMarkNo=this.valiteInviteVehicleNo(shortTempRentalMatkVo.getInviteVehicleNo());
		
		if(rentalMarkNo!=null&&!"".equals(rentalMarkNo)){
			throw new TfrBusinessException("约车编号"+shortTempRentalMatkVo.getInviteVehicleNo()+"!，已经被租车标记了（租车编号"+rentalMarkNo+"）");
		}
		//约车编号
		tempRentalMarkEntityTmp.setInviteVehicleNo(shortTempRentalMatkVo.getInviteVehicleNo());
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，约车编号校验结束。。。");
		
		

		if(shortTempRentalMatkVo.getRemark()!=null&&!"".equals(shortTempRentalMatkVo.getRemark())){
			tempRentalMarkEntityTmp.setNotes(shortTempRentalMatkVo.getRemark().trim());//备注
		}
		if(org.getName()==null||"".equals(org.getName())){
			throw new TfrBusinessException("标记部门为空");
		}
		if(orgCode==null||"".equals(orgCode)){
			throw new TfrBusinessException("标记部门编码为空");
		}
		tempRentalMarkEntityTmp.setMarkDepartName(org.getName());//当前标记部门名称
		tempRentalMarkEntityTmp.setMarkDepartCode(orgCode);//标记部门编码
		tempRentalMarkEntityTmp.setCreateUserName(user.getEmpName());//创建人
		tempRentalMarkEntityTmp.setCreateUserCode(user.getEmpCode());//创建人工号
		tempRentalMarkEntityTmp.setCreateDate(currentDate);//创建时间
		tempRentalMarkEntityTmp.setModifyUserNme(user.getEmpName());//修改人
		tempRentalMarkEntityTmp.setModifyUserCode(user.getEmpCode());//修改人工号
		tempRentalMarkEntityTmp.setModifyDate(currentDate);//修改时间
		tempRentalMarkEntityTmp.setActive(InviteVehicleConstants.RENTALMARK_Y);//状态
				
		if(shortTempRentalMatkVo.getIsRepeateMark()==null||"".equals(shortTempRentalMatkVo.getIsRepeateMark())){
			throw new TfrBusinessException("是否多次标记字段为空");
		}
		tempRentalMarkEntityTmp.setIsRepeateMark(shortTempRentalMatkVo.getIsRepeateMark());//多次标记
		BigDecimal weigthTotal = new BigDecimal("0");//总重量
		BigDecimal volumeTotal=new BigDecimal("0");//总体积
		tempRentalMarkEntityTmp.setActualTakeGoodsQyt(new BigDecimal(shallTakeGoodsQyt));//应走货数量
//		for(int i=0;i<shortTempRentalMatkVo.getShortRentalMarkEntityList().size();i++){
//			weigthTotal=weigthTotal.add(shortTempRentalMatkVo.getShortRentalMarkEntityList().get(i).getWeight());
//			volumeTotal=volumeTotal.add(shortTempRentalMatkVo.getShortRentalMarkEntityList().get(i).getVolume());
//		}
		tempRentalMarkEntityTmp.setWeigthTotal(weigthTotal);//总重量
		tempRentalMarkEntityTmp.setVolumeTotal(volumeTotal);//总体积
		
		/**封装分流给CUBC的实体基本信息 author：Lee date:2017-04-27*/
		/*String yT = shortTempRentalMatkVo.getShortRentalUse();//获取租车用途
		String yTInfo="";
		if("ZH".equals(yT)){
			yTInfo="转货";
		}else{
			yTInfo="大客户接货";
		}
		rentalMarkDO.setInviteVehicleNo(shortTempRentalMatkVo.getInviteVehicleNo());//短途租车约车编号 
		rentalMarkDO.setUserCarReason(carReason);//短途租车用车原因
		rentalMarkDO.setUseCarDate(shortTempRentalMatkVo.getUseCareDate());//短途租车用车时间 
		rentalMarkDO.setCreateDate(currentDate);//创建人时间
		rentalMarkDO.setCreateUserCode(user.getEmpCode());//创建人工号
		rentalMarkDO.setCreateUserName(user.getEmpName());//创建人姓名
		rentalMarkDO.setCurrentDeptCode(org.getCode());//创建当前登录部门CODE
		rentalMarkDO.setCurrentDeptName(org.getName());//创建当前登录部门名称
		rentalMarkDO.setEmpCode(user.getEmpCode());//当前操作人工号
		rentalMarkDO.setEmpName(user.getEmpName());//当前登录人姓名
		rentalMarkDO.setRentalCarUsetype(yTInfo);//租车用途
		rentalMarkDO.setBearFeesDept(rentCarCubcRequst.getBearFeesDept());//费用承担部门
		rentalMarkDO.setBearFeesDeptCode(rentCarCubcRequst.getBearFeesDeptCode());//费用承担部门CODE
		rentalMarkDO.setIsRepeateMark(shortTempRentalMatkVo.getIsRepeateMark());//是否多次标记
		rentalMarkDO.setModifyDate(currentDate);//修改时间
		rentalMarkDO.setModifyUserCode(user.getEmpCode());//修改人工号
		rentalMarkDO.setModifyUserNme(user.getEmpName());//修改人名称
		rentalMarkDO.setMarkDepartCode(orgCode);//标记部门编码
		rentalMarkDO.setMarkDepartName(org.getName());//当前标记部门名称
		rentalMarkDO.setActive(InviteVehicleConstants.RENTALMARK_Y);//状态
		rentalMarkDO.setNotes(shortTempRentalMatkVo.getRemark());//备注
		rentalMarkDO.setSalesVehiclePlatformName(shortTempRentalMatkVo.getSalesVehiclePlatformName());//请车平台名称
		rentalMarkDO.setActualTakeGoodsQyt(new BigDecimal(shallTakeGoodsQyt));//应走货票数
		
		*/
		/**封装分流给CUBC的实体基本信息 author：Lee date:2017-04-27*/
		
		BigDecimal multiCarSize;
		if(shortTempRentalMatkVo.getMultiCarTakeGoodsDtoList()!=null&&!shortTempRentalMatkVo.getMultiCarTakeGoodsDtoList().isEmpty()){
			for(int i=0;i<shortTempRentalMatkVo.getMultiCarTakeGoodsDtoList().size();i++){
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备开始。。。");
				/**租车标记主表数据准备**/
				multiCarSize=new BigDecimal(shortTempRentalMatkVo.getMultiCarTakeGoodsDtoList().size());//多车走货车数量
				
				MultiCarTakeGoodsDto multiCarTakeGoodsDto=shortTempRentalMatkVo.getMultiCarTakeGoodsDtoList().get(i);
				TempRentalMarkEntity tempRentalMarkEntity = new TempRentalMarkEntity();
				tempRentalMarkEntity.setUseCarDate(tempRentalMarkEntityTmp.getUseCarDate());//用车日期
				tempRentalMarkEntity.setUserCarReason(tempRentalMarkEntityTmp.getUserCarReason());//用车原因
				tempRentalMarkEntity.setInviteVehicleNo(tempRentalMarkEntityTmp.getInviteVehicleNo());//约车编号
				tempRentalMarkEntity.setNotes(tempRentalMarkEntityTmp.getNotes());//备注
				tempRentalMarkEntity.setMarkDepartName(tempRentalMarkEntityTmp.getMarkDepartName());//当前标记部门名称
				tempRentalMarkEntity.setMarkDepartCode(tempRentalMarkEntityTmp.getMarkDepartCode());//标记部门编码
				tempRentalMarkEntity.setCreateUserName(tempRentalMarkEntityTmp.getCreateUserName());//创建人
				tempRentalMarkEntity.setCreateUserCode(tempRentalMarkEntityTmp.getCreateUserCode());//创建人工号
				tempRentalMarkEntity.setCreateDate(tempRentalMarkEntityTmp.getCreateDate());//创建时间
				tempRentalMarkEntity.setModifyUserNme(tempRentalMarkEntityTmp.getModifyUserNme());//修改人
				tempRentalMarkEntity.setModifyUserCode(tempRentalMarkEntityTmp.getModifyUserCode());//修改人工号
				tempRentalMarkEntity.setModifyDate(tempRentalMarkEntityTmp.getModifyDate());//修改时间
				tempRentalMarkEntity.setActive(tempRentalMarkEntityTmp.getActive());//状态
				tempRentalMarkEntity.setActualTakeGoodsQyt(tempRentalMarkEntityTmp.getActualTakeGoodsQyt());//应走货数量
				tempRentalMarkEntity.setWeigthTotal(tempRentalMarkEntityTmp.getWeigthTotal().divide(multiCarSize,3,BigDecimal.ROUND_HALF_UP));//平均重量
				tempRentalMarkEntity.setVolumeTotal(tempRentalMarkEntityTmp.getVolumeTotal().divide(multiCarSize,3,BigDecimal.ROUND_HALF_UP));//总体积
				tempRentalMarkEntity.setIsRepeateMark(tempRentalMarkEntityTmp.getIsRepeateMark());//多次标记
				tempRentalMarkEntity.setTruckTaskId(truckTaskId);
				tempRentalMarkEntity.setRentalCarUsetype(shortTempRentalMatkVo.getShortRentalUse());
				
				/**封装分流给CUBC的实体基本信息 author：Lee date:2017-04-27*/
				/*rentalMarkDO.setRentalAmount(multiCarTakeGoodsDto.getRentalAmount());//获取租车费用
				rentalMarkDO.setKmsNum(multiCarTakeGoodsDto.getKmsNum());//公里数
				rentalMarkDO.setDepartureName(multiCarTakeGoodsDto.getDepartureName());//出发站
				rentalMarkDO.setDepartureCode(multiCarTakeGoodsDto.getDepartureCode());//出发站编码
				rentalMarkDO.setDestinationName(multiCarTakeGoodsDto.getDestinationName());//目的站
				rentalMarkDO.setDestinationCode(multiCarTakeGoodsDto.getDestinationCode());//目的站编码
				rentalMarkDO.setVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//车牌号
*/				/**封装分流给CUBC的实体基本信息 author：Lee date:2017-04-27*/
				
				
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询开始。。。");
				List<DriverAssociationDto> driverAssociationDtoList=new ArrayList<DriverAssociationDto>();
				driverAssociationDtoList=leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//查询外请车司机信息
				
				if(driverAssociationDtoList.size()>0){
					if(driverAssociationDtoList.get(0).getDriverIdCard()==null
							||"".equals(driverAssociationDtoList.get(0).getDriverIdCard())
							||driverAssociationDtoList.get(0).getDriverName()==null
							||"".equals(driverAssociationDtoList.get(0).getDriverName())){
						throw new TfrBusinessException("外请车司机信息为空");
					}
					tempRentalMarkEntity.setDriverName(driverAssociationDtoList.get(0).getDriverName());//司机姓名
					tempRentalMarkEntity.setDriverCode(driverAssociationDtoList.get(0).getDriverIdCard());//司机代码
					tempRentalMarkEntity.setDriverPhone(driverAssociationDtoList.get(0).getDriverPhone());//司机电话
					
					/**封装分流给CUBC的实体司机基本信息 author：Lee date:2017-04-27*/
					/*rentalMarkDO.setDriverCode(driverAssociationDtoList.get(0).getDriverIdCard());//司机代码
					rentalMarkDO.setDriverName(driverAssociationDtoList.get(0).getDriverName());//司机姓名
					rentalMarkDO.setDriverPhone(driverAssociationDtoList.get(0).getDriverPhone());//司机电话
*/					/**封装分流给CUBC的实体司机基本信息 author：Lee date:2017-04-27*/
					
					
				}else {
					throw new TfrBusinessException("外请车司机为空");
				}
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，外请车数据查询结束。。。");				
				tempRentalMarkEntity.setId(UUIDUtils.getUUID());//ID
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号开始。。。");
				String tempRentalMarkNO	= creatTempRentalMarkNO(shortTempRentalMatkVo.getShortRentalUse(),currentDate);//租车编号
				
				/**************author:lee  date:2017-04-26  description:获取多车走货******/
				/*rentalMarkDO.setTempRentalMarkNO(tempRentalMarkNO);//封装租车编码
				budgetEntity =new BudgetEntity();
				crmBudgetControlRequestEntity =new CrmBudgetControlRequestEntity();
				//标识字段，0：占用预算占用	1：释放预算	2：查询预算
				crmBudgetControlRequestEntity.setFlag("0");
				//请求时间        
				crmBudgetControlRequestEntity.setApplyDate(simpleDate.format(new Date()));
				//理赔ID,但是FOSS这边给的是租车编码，因为唯一，所以在做租车释放的时候，也是根据租车编码来对应关联，FSSC根据租车编码（理赔ID）释放金额
				crmBudgetControlRequestEntity.setClaimID(tempRentalMarkNO);
				//申请人工号
				crmBudgetControlRequestEntity.setEmpCode(user.getEmpCode());
				//申请人部门标杆编码
				crmBudgetControlRequestEntity.setApplyDeptStandCode(org.getUnifiedCode());
				//预算总金额
				crmBudgetControlRequestEntity.setTotalAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
				
				budgetEntity.setAmount(String.valueOf(multiCarTakeGoodsDto.getRentalAmount()));
				//设置用车月份					
				cal.setTime(tempRentalMarkEntity.getUseCarDate());
				String year = String.valueOf(cal.get(cal.YEAR));
				String strMonth=null;
				int month = cal.get(cal.MONTH)+1;
				if(month<10){
					strMonth="0"+month;
				}else{
					strMonth=String.valueOf(month);
				}
				budgetEntity.setMonth(year+strMonth);
				budgetEntityList = new ArrayList<BudgetEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了
				rentalMarkToFSSCList = new ArrayList<CrmBudgetControlRequestEntity>();//封装集合传递给FSSC报账系统，被报账系统的接口模板搞奔溃了
				budgetEntityList.add(budgetEntity);
				crmBudgetControlRequestEntity.setBudgetEntitys(budgetEntityList);
				rentalMarkToFSSCList.add(crmBudgetControlRequestEntity);*/
				/**************author:lee  date:2017-04-26  description:获取多车走货******/

				if(tempRentalMarkNO==null||"".equals(tempRentalMarkNO)){
					throw new TfrBusinessException("生成租车编号失败");
				}
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，生成租车编号"+tempRentalMarkNO+"成功结束。。。");
				tempRentalMarkEntity.setTempRentalMarkNO(tempRentalMarkNO);//租车编号
				VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(multiCarTakeGoodsDto.getVehicleNo()); 
				if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleLengthCode()!=null){
					tempRentalMarkEntity.setVehicleLenghtCode(vehicleAssociationDto.getVehicleLengthCode());//车型
					
					/**封装分流给CUBC的实体车型基本信息 author：Lee date:2017-04-27*/
					//rentalMarkDO.setVehicleLenghtCode(vehicleAssociationDto.getVehicleLengthCode());//车型
					/**封装分流给CUBC的实体车型基本信息 author：Lee date:2017-04-27*/
				}
				if(vehicleAssociationDto!=null&&vehicleAssociationDto.getVehicleSelfVolume()!=null){
					tempRentalMarkEntity.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());//净空
					/**封装分流给CUBC的实体净空基本信息 author：Lee date:2017-04-27*/
					//rentalMarkDO.setSelfVolume(vehicleAssociationDto.getVehicleSelfVolume());
					/**封装分流给CUBC的实体净空基本信息 author：Lee date:2017-04-27*/
				}
				tempRentalMarkEntity.setVehicleNo(multiCarTakeGoodsDto.getVehicleNo());//车牌号
				if(multiCarTakeGoodsDto.getRentalAmount().signum()<1){
					throw new TfrBusinessException("租车金额不能小于0");
				}
				tempRentalMarkEntity.setRentalAmount(multiCarTakeGoodsDto.getRentalAmount().multiply(new BigDecimal(100)));//租车金额*100
				tempRentalMarkEntity.setKmsNum(multiCarTakeGoodsDto.getKmsNum());//公里数
				tempRentalMarkEntity.setDepartureCode(multiCarTakeGoodsDto.getDepartureCode());//出发地编码
				tempRentalMarkEntity.setDepartureName(multiCarTakeGoodsDto.getDepartureName());//出发地部门
				tempRentalMarkEntity.setDestinationCode(multiCarTakeGoodsDto.getDestinationCode());//到达部门编码
				tempRentalMarkEntity.setDestinationName(multiCarTakeGoodsDto.getDestinationName());//到达部门名称
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记主表数据准备结束。。。");
				/**设置结算参数**/
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备开始。。。");
				RentCarDto rentCarDto = new RentCarDto();//结算
				rentCarDto.setAmount(multiCarTakeGoodsDto.getRentalAmount());//租车金额
				rentCarDto.setCustomerCode(tempRentalMarkEntity.getDriverCode());//司机编码
				rentCarDto.setCustomerName(tempRentalMarkEntity.getDriverName());//司机姓名
				rentCarDto.setCustomerPhone(tempRentalMarkEntity.getDriverPhone());//司机联系方式
				rentCarDto.setIsRepeatemark(tempRentalMarkEntity.getIsRepeateMark());//是否多次标记
				rentCarDto.setRentCarNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
				rentCarDto.setRentCarUseType(shortTempRentalMatkVo.getShortRentalUse());//租车用途
				rentCarDto.setUseCarDate(tempRentalMarkEntity.getUseCarDate());//用车日期
				LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单数据准备完成。。。");
				/**添加租车标记主表信息**/
				tempRentalMarkEntityList.add(tempRentalMarkEntity);
				/**添加结算参数信息**/
				
				rentCarDtoList.add(rentCarDto);
				//租车标记明细表数据准备
				for(int j=0;j<shortTempRentalMatkVo.getShortRentalMarkEntityList().size();j++){
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备开始。。。");
					ShortRentalMarkEntity shortRentalMarkEntity= shortTempRentalMatkVo.getShortRentalMarkEntityList().get(j);
					TempRentalMarkDetailEntity tempRentalMarkDetailEntity =new TempRentalMarkDetailEntity(); 
					tempRentalMarkDetailEntity.setId(UUIDUtils.getUUID());//设置ID
					tempRentalMarkDetailEntity.setTempRentalMarkId(tempRentalMarkEntity.getId());//租车Id
					tempRentalMarkDetailEntity.setTempRentalMarkNo(tempRentalMarkEntity.getTempRentalMarkNO());//租车编号
					tempRentalMarkDetailEntity.setCreateUserName(tempRentalMarkEntity.getCreateUserName());//创建人
					tempRentalMarkDetailEntity.setCreateUserCode(tempRentalMarkEntity.getCreateUserCode());//创建人工号
					tempRentalMarkDetailEntity.setCreateDate(tempRentalMarkEntity.getCreateDate());//创建时间
					tempRentalMarkDetailEntity.setRentalCarUserType(tempRentalMarkEntity.getRentalCarUsetype());//租车用途
					tempRentalMarkDetailEntity.setMarkDepartName(tempRentalMarkEntity.getMarkDepartName());//标记部门
					tempRentalMarkDetailEntity.setMarkDepartCode(tempRentalMarkEntity.getMarkDepartCode());//标记部门
					tempRentalMarkDetailEntity.setBillCreateDate(shortRentalMarkEntity.getUseCareDate());
					LOGGER.info("TFR-SCHEDULING 添加租车标记信息，租车标记明细表数据准备完成。。。");
					/**添加租车标记明细表信息**/
					tempRentalMarkDetailEntityList.add(tempRentalMarkDetailEntity);
				}
			}
		}
		/**
		 * 调用FSSC报账系统,进行租车金额预算部门卡控
		 * author:lee
		 * date  :2017-04-26
		 */                                                                                                      
		//根据费用承担部门CODE获取对应的标杆标码
		/*OrgAdministrativeInfoEntity feeOrg =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(rentCarCubcRequst.getBearFeesDeptCode());//获取部门信息
		if(rentalMarkToFSSCList!=null && rentalMarkToFSSCList.size()>0){
			for(CrmBudgetControlRequestEntity ent:rentalMarkToFSSCList){
				List<BudgetEntity> list=ent.getBudgetEntitys();
				if(list!=null && list.size()>0){
					list.get(0).setDeptStandCode(feeOrg.getUnifiedCode());
				}
				LOGGER.info("短途租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  调用开始！");
				try{
					CrmBudgetControlResponseEntity  responseEnt=aSYRentCarCubcService.pushTemptalMarkFeeInfoToFSSC(ent);
					if(responseEnt==null){
						throw new TfrBusinessException("短途租车编码："+ent.getClaimID()+" 调用FSSC报账系统接口失败 ");
					}
					LOGGER.info("短途租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  FSSC响应返回："+responseEnt.toString());
					LOGGER.info("短途租车标记调FSSC接口，租车编码："+ent.getClaimID()+"  成功结束！");
					if(0==responseEnt.getIsSucceed()){
						throw new TfrBusinessException("短途租车编码："+ent.getClaimID()+"  "+responseEnt.getFailure());
					}
				}catch(Exception e){
					throw new TfrBusinessException(e.getMessage());
				}
			}
		}*/
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车主表。。。");
		temprentalMarkDAO.addShortTempRentalMarkEntityList(tempRentalMarkEntityList);//添加租车主表信息
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，插入租车明细表。。。");
		temprentalMarkDAO.addTempRentalMarkDetailEntityList(tempRentalMarkDetailEntityList);//添加租车明细表信息
		
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单。。。");
		rentCarService.addRentCar(rentCarDtoList, user);//掉财务结算接口
		LOGGER.info("TFR-SCHEDULING 添加租车标记信息，调结算接口生成应付单结束。。。");

	}
	
	@Override
	public String queryBearFeesDeptName(String inviteNo) {
		return temprentalMarkDAO.queryBearFeesDeptName(inviteNo);
	}
	@Override//310248CUBC
	public RentCarCubcRequest queryBearFeesDept(String inviteNo) {
		
		return temprentalMarkDAO.queryBearFeesDept(inviteNo);
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	public void setTemprentalMarkDAO(ITemprentalMarkDAO temprentalMarkDAO) {
		this.temprentalMarkDAO = temprentalMarkDAO;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}


	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setRentCarService(IRentCarService rentCarService) {
		this.rentCarService = rentCarService;
	}


	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}


	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}
	public void setTemporaryRentalCarMarkTimeManagementService(
			ITemporaryRentalCarMarkTimeManagementService temporaryRentalCarMarkTimeManagementService) {
		this.temporaryRentalCarMarkTimeManagementService = temporaryRentalCarMarkTimeManagementService;
	}

	public void setLineService(ILineService lineService) {
		this.LineService = lineService;
	}
	
	private int totalNumber;
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	public void setaSYRentCarCubcService(
			IASYRentCarCubcService aSYRentCarCubcService) {
		this.aSYRentCarCubcService = aSYRentCarCubcService;
	}

	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public IFossToCubcService getFossToCubcService() {
		return fossToCubcService;
	}
	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	
	public void setInviteVehicleService(IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}
	
	

}
