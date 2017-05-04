package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IFindGoodsAdminDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.FindLostgoodsDto;
import com.deppon.foss.module.transfer.stock.api.shared.exception.FindGoodsAdminException;
import com.deppon.foss.module.transfer.stock.api.shared.vo.FindGoodsAdminVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.primeton.das.entity.impl.hibernate.exception.ExceptionUtils;

/**
 * 
 * @ClassName: FindGoodsAdminService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 189284--ZX
 * @date 2015-7-10 下午4:54:40
 * 
 */
public class FindGoodsAdminService implements IFindGoodsAdminService {
	// 日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FindGoodsAdminService.class);
	/**
	 * 综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IFindGoodsAdminDao findGoodsAdminDao;
	/**
	 * 库存
	 */
	private IStockService stockService;
	/** 综合管理 货区service */
	private IGoodsAreaService goodsAreaService;
	/** 综合管理 组织信息 Service */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	private ITfrCommonService tfrCommonService;// 生成包编号序列号的service

	/**
	 * 设置 //生成包编号序列号的service
	 * 
	 * @param tfrCommonService
	 *            the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	/**
	 * 设置 综合管理 组织信息 Service
	 * 
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 综合管理 货区service
	 * 
	 * @param goodsAreaService
	 *            the goodsAreaService to set
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 设置
	 * 
	 * @param findGoodsAdmindao
	 *            the findGoodsAdmindao to set
	 */
	public void setFindGoodsAdminDao(IFindGoodsAdminDao findGoodsAdminDao) {
		this.findGoodsAdminDao = findGoodsAdminDao;
	}

	/**
	 * 设置 综合管理 组织信息 Service
	 * 
	 * @param orgAdministrativeInfoComplexService
	 *            the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 设置 库存
	 * 
	 * @param stockService
	 *            the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 
	 * @Title: qureyFindGoodsAdmin
	 * @Description: 根据条件查询 找货管理 分页
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntity
	 * @param @return 设定文件
	 * @return List<FindGoodsAdminEntity> 返回类型
	 * @throws
	 */
	@Override
	public List<FindGoodsAdminEntity> qureyFindGoodsAdmin(
			FindGoodsAdminEntity findGoodsAdminEntity, int start, int limit) {
		List<FindGoodsAdminEntity> findGoodsAdminEntitys=findGoodsAdminDao.queryFindGoodsAdmin(findGoodsAdminEntity,start, limit);
		return findGoodsAdminEntitys;
	}

	/**
	 * 
	 * @Title: qureyFindGoodsAdminCount
	 * @Description: 根据条件查询 找货管理 总数
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntitys
	 * @param @return 设定文件
	 * @return Long 返回类型
	 * @throws
	 */
	@Override
	public Long qureyFindGoodsAdminCount(
			FindGoodsAdminEntity findGoodsAdminEntity) {
		return findGoodsAdminDao.queryFindGoodsAdminQueryCount(findGoodsAdminEntity);
	}

	/**
	 * 
	 * <p>
	 * Title: querySuperiorOrgByOrgCode
	 * </p>
	 * <p>
	 * 根据当前部门code获取上级外场 或者营业部
	 * </p>
	 * 
	 * @author 189284--ZX
	 * @param currentDeptCode
	 * @return
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(
			String currentDeptCode) {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 营业部派送部
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentDeptCode,bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		} else {
			// 获取上级部门失败
			LOGGER.error("################查询组织（code：" + currentDeptCode
					+ "所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}

	/**
	 * 
	 * @Title: commitFindGoodsAdmin
	 * @Description: 根据 任务编号 提交找货管理信息
	 * @author 189284--ZX
	 * @param taskNo 任务编号
	 * @param user 提交人
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public String commitFindGoodsAdmin(String taskNo, String user) {
		FindGoodsAdminEntity findGoodsAdminEntity = new FindGoodsAdminEntity();
		if (StringUtil.isNotEmpty(taskNo) || StringUtil.isNotEmpty(user)) {
			findGoodsAdminEntity.setTaskNo(taskNo);
			// 设置 任务状态为已提交 submit_end
			findGoodsAdminEntity.setTaskStatus("submit_end");
			findGoodsAdminEntity.setModifyDate(new Date());
			findGoodsAdminEntity.setModifyUserCode(user);
			findGoodsAdminDao.commitFindGoodsAdmin(findGoodsAdminEntity);
			return "编号为" + taskNo + "的任务提交成功";
		} else {
			throw new FindGoodsAdminException("任务编号，或者提交人为空！");
		}

	}

	/**
	 * 
	 * @Title: FindGoodsAdminInsert
	 * @Description:新增 找货管理信息
	 * @author 189284--ZX
	 * @param @param findGoodsAdminEntity
	 * @param @param findGoodsAdminDetailEntitys 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	@Transactional
	public int FindGoodsAdminInsert(FindGoodsAdminEntity findGoodsAdminEntity) {
		return findGoodsAdminDao.findGoodsAdminInsert(findGoodsAdminEntity);
	}

	/**
	 * 
	 * @Title: findGoodsAdminDetailInsert
	 * @Description: 新增 找货管理信息明细
	 * @author 189284--ZX
	 * @param @param findGoodsAdminDetailEntitys
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	@Transactional
	public int findGoodsAdminDetailInsert(
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys) {
		return findGoodsAdminDao.findGoodsAdminDetailInsert(findGoodsAdminDetailEntitys);
	}
	/**
	 * 
	 * @Title: qureyGoodsAreaByCode
	 * @Description: 提供给pda 查询 当前部门下的库区（如果为营业部 返回null）
	 * @author 189284--ZX
	 * @param @param organizationCode
	 * @param @return 设定文件
	 * @return List<GoodsAreaEntity> 返回类型
	 * @throws
	 */
	@Override
	public List<GoodsAreaEntity> qureyGoodsAreaByCode(String organizationCode) {
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(organizationCode);
		// 判断是否为营业部 营业部 没有库区 返回库区为null
		if (orgAdministrativeInfoEntity != null) {
			if (StringUtil.equals(FossConstants.NO,
					orgAdministrativeInfoEntity.getSalesDepartment())) {
				return goodsAreaService.queryGoodsAreaListByOrganizationCode(organizationCode);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @Title: queryFindGoodsAdminDeatil
	 * @Description: pda 创建找货任务的时间 查明细
	 * @author 189284--ZX
	 * @param findGoodsAdminEntity.goodsAreaCode 货区Code'
	 * @param findGoodsAdminEntity.orgCode 货区所属外场Code
	 * @param findGoodsAdminEntity.taskCrateDate 上报开始时间
	 * @param findGoodsAdminEntity.taskEndDate 上报结束时间
	 * @return List<FindGoodsAdminDetailEntity> 返回类型
	 * @throws
	 */
	@Override
	public List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDeatil(
			FindGoodsAdminEntity findGoodsAdminEntity) {
		return findGoodsAdminDao.queryFindGoodsAdminDeatil(findGoodsAdminEntity);
	}

	/**
	 * 
	* @Title: createFindGoodsTask
	* @Description: Pda 创建 找货任务（拉去明细 同时 保存到本地）
	* @author 189284--ZX
	* @param  findGoodsAdminEntity.goodsAreaCode货区
	* @param  findGoodsAdminEntity.orgCode部门
	* @param  findGoodsAdminEntity.findGoodsManCode找货人
	* @param  findGoodsAdminEntity.taskCreateDate开始上报时间
	* @param  findGoodsAdminEntity.taskEndDate结束上报时间
	* @param @return    设定文件
	* @return FindGoodsAdminVo 返回类型
	* findGoodsAdminDetailEntitys 明细 list
	* @throws
	 */
	@Override
	public FindGoodsAdminVo  createFindGoodsTask(
			FindGoodsAdminEntity findGoodsAdminEntity) {
		LOGGER.info("创建找货任务！拉取明细开始");
		if (findGoodsAdminEntity==null||StringUtil.isEmpty(findGoodsAdminEntity.getOrgCode())
				|| findGoodsAdminEntity.getTaskCreateDate()==null
				|| findGoodsAdminEntity.getTaskEndDate()==null) {
			throw new FindGoodsAdminException("部门，开始上报时间，结束上报时间为空！参数错误");
		}
		try {
			LOGGER.info("部门"+findGoodsAdminEntity.getOrgCode()+
					"货区"+findGoodsAdminEntity.getGoodsAreaCode()+
					"找货人"+findGoodsAdminEntity.getFindGoodsManCode()+
					"开始上报时间"+findGoodsAdminEntity.getTaskCreateDate()+
					"结束上报时间为空"+findGoodsAdminEntity.getTaskEndDate()
					);
			/**
			 * 需要添加的 找货明细List
			 */
			
			FindLostgoodsDto findLostgoodsDto=new FindLostgoodsDto();
			findLostgoodsDto.setOrgCode(findGoodsAdminEntity.getOrgCode());
			findLostgoodsDto.setTaskCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(findGoodsAdminEntity.getTaskCreateDate()));
			findLostgoodsDto.setTaskEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(findGoodsAdminEntity.getTaskEndDate()));
			
			
			JSONObject jsonObject = JSONObject.fromObject(findLostgoodsDto);
			
			String requestStr=jsonObject.toString();
			String responseStr = "";
			HttpClient httpClient = new HttpClient();  
			String url =PropertiesUtil.getKeyValue("esb.rs") + "/ESB_FOSS2ESB_LOOKING_FORGOODSINTER";
		    PostMethod postMethod = new PostMethod(url);  
		    Date startDate = new Date();
		    try {  
		    	httpClient.getParams().setContentCharset("UTF-8");
		    	postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				RequestEntity reqEntity = new StringRequestEntity(requestStr,"application/json","UTF-8");
				postMethod.setRequestEntity(reqEntity);
				
				// 设置连接超时和读取超时
			   
		        int status = httpClient.executeMethod(postMethod);  
		        if(status==ConstantsNumberSonar.SONAR_NUMBER_200){
		        	responseStr = postMethod.getResponseBodyAsString();
		        }else{
		        	throw new FindGoodsAdminException( "请求"+url+"传输数据异常：status="+status);
		        }
		    } catch (Exception e) {  
		    	throw new FindGoodsAdminException( "请求"+url+"上报丢货异常，开始时间："+startDate+"，结束时间"+new Date()
		    			+ExceptionUtils.getFullStackTrace(e));
		    	//LOGGER.error(responseStr);
		    } finally {
		    	//释放连接
		    	postMethod.releaseConnection();
		    }
		    
		  //  List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys=new ArrayList<FindGoodsAdminDetailEntity>();
		    //FindGoodsAdminDetailEntity findGoodsAdminDetailEntity=new FindGoodsAdminDetailEntity();
		    List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys =  new ArrayList<FindGoodsAdminDetailEntity>();		
				try{
					findGoodsAdminDetailEntitys = 
				         JSONArray.toList(JSONArray.fromObject(responseStr),
				         FindGoodsAdminDetailEntity.class);
				}catch(Exception e){
					//sonar-352203
					LOGGER.info("FindGoodsAdminService.createFindGoodsTask 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
				}
			
			
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailPda = new ArrayList<FindGoodsAdminDetailEntity>();
			// pda 创建找货任务的时间 查明细 根据上报时间 部门 货区
		//	List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys = queryFindGoodsAdminDeatil(findGoodsAdminEntity);
			if (findGoodsAdminDetailEntitys != null
					&& findGoodsAdminDetailEntitys.size() > 0) {
				// 生成找货编码 //格式为07+*年*月*日+业务号（随机4位不重复），如07201506130001
				String taskNo = "07"+tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.FGTASK, "");
				for (FindGoodsAdminDetailEntity findGoodsAdminDetailEntity : findGoodsAdminDetailEntitys) {
					if(findGoodsAdminDetailEntity.getLostGoodsQty()==null){
						findGoodsAdminDetailEntity.setLostGoodsQty(0L);
					}
					if(findGoodsAdminDetailEntity.getTotalQty()==null){
						findGoodsAdminDetailEntity.setLostGoodsQty(0L);
					}
					if(findGoodsAdminDetailEntity.getWeight()==null){
						findGoodsAdminDetailEntity.setWeight(new BigDecimal(0));
					}
					if(findGoodsAdminDetailEntity.getVolume()==null){
						findGoodsAdminDetailEntity.setVolume(new BigDecimal(0));
					}
					findGoodsAdminDetailEntity.setTaskNo(taskNo);
					// 根据 运单号 流水号 查询明细
					List<FindGoodsAdminDetailEntity> findGoodsAdminDetailList = findGoodsAdminDao.queryFindGoodsAdminDetail(findGoodsAdminDetailEntity);
					// 如果明细 存在 说明不需要在添加明细了
					if (CollectionUtils.isEmpty(findGoodsAdminDetailList)) {
						findGoodsAdminDetailEntity.setCreateUserCode(findGoodsAdminEntity.getFindGoodsManCode());
						findGoodsAdminDetailEntity.setModifyUserCode(findGoodsAdminEntity.getFindGoodsManCode());
						findGoodsAdminDetailEntity.setFindType(FossConstants.NO);
						findGoodsAdminDetailPda.add(findGoodsAdminDetailEntity);
					}
				}
				
				findGoodsAdminEntity.setTaskNo(taskNo);
				// 任务状态--找货中：find_ing(默认初始化状态) 已提交：submit_end
				findGoodsAdminEntity.setTaskStatus("find_ing");
				findGoodsAdminEntity.setCreateUserCode(findGoodsAdminEntity.getFindGoodsManCode());
				findGoodsAdminEntity.setModifyUserCode(findGoodsAdminEntity.getFindGoodsManCode());
				// 创建找货任务
				findGoodsAdminDao.findGoodsAdminInsert(findGoodsAdminEntity);
				// 批量创建找货任务关系
				findGoodsAdminDao.insertFGoodsDetailMapper(findGoodsAdminDetailEntitys);
				if(CollectionUtils.isNotEmpty(findGoodsAdminDetailPda)){
					// 批量创建 货物明细（丢货）
					findGoodsAdminDao.findGoodsAdminDetailInsert(findGoodsAdminDetailPda);
				}else{
					LOGGER.info("明细添加为空");
				}
							
			}
			FindGoodsAdminVo findGoodsAdminVo=new FindGoodsAdminVo();
			findGoodsAdminVo.setFindGoodsAdminDetailEntitys(findGoodsAdminDetailEntitys);
			findGoodsAdminVo.setFindGoodsAdminEntity(findGoodsAdminEntity);
			LOGGER.info("返回给PDA的明细"+JSONArray.fromObject(findGoodsAdminVo).toString());
			LOGGER.info("创建找货任务！拉取明细结束");
			return findGoodsAdminVo;
		} catch (BusinessException e) {
			System.out.println(e);
			  throw  new FindGoodsAdminException(e.getMessage());
		}
	}

	/***
	 * 
	 * @Title: scanFGoodsfgoodsDetail
	 * @Description: pda 扫描 提交 明细
	 * @author 189284--ZX
	 * @param waybillNo运单号
	 * @param serialNo流水
	 * @param user修改人
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	@Transactional
	public String scanFGoodsfgoodsDetail(String waybillNo, String serialNo,
			String user, String orgCode) {
		LOGGER.info("扫描 提交明细  开始");
		if (StringUtil.isEmpty(waybillNo) || StringUtil.isEmpty(serialNo)
				|| StringUtil.isEmpty(user)) {
			LOGGER.info("运单号，流水，修改人为空！参数错误");
			throw new FindGoodsAdminException("运单号，流水，修改人为空！参数错误");
		}
		LOGGER.info("运单" + waybillNo + "流水" + serialNo + "提交人" + user);
		try {
			findGoodsAdminDao.scanFGoodsfgoodsDetail(waybillNo, serialNo, user);
			// 出库
			InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setWaybillNO(waybillNo);
			inOutStockEntity.setSerialNO(serialNo);
			inOutStockEntity.setOperatorCode(user);
			inOutStockEntity.setOrgCode(orgCode);
			/**
			 * 少货找到PDA扫描 LOSS_GOODS_FIND_PDA
			 */
			inOutStockEntity.setInOutStockType(StockConstants.LOSE_GOODS_FOUND_PDA_IN_STOCK_TYPE);
			// 入库
			stockService.inStockPC(inOutStockEntity);
			LOGGER.info("扫描 提交明细 结束");
			return "运单" + waybillNo + "流水" + serialNo + "扫描成功";
		} catch (BusinessException e) {
			System.out.print(e);
			throw new FindGoodsAdminException(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: qureyFGoodsDetailByTaskNo
	 * @Description: 根据任务号 查询明细
	 * @author 189284--ZX
	 * @param @param taskNo
	 * @param @return 设定文件
	 * @return List<FindGoodsAdminDetailEntity> 返回类型
	 * @throws
	 */
	@Override
	public List<FindGoodsAdminDetailEntity> qureyFGoodsDetailBytaskNo(
			String taskNo) {
		if (StringUtil.isEmpty(taskNo)) {
			throw new FindGoodsAdminException("任务编号为");
		}
		 List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys=findGoodsAdminDao.qureyFGoodsDetailByTaskNo(taskNo);
		if(CollectionUtils.isNotEmpty(findGoodsAdminDetailEntitys)){
			for (FindGoodsAdminDetailEntity findGoodsAdminDetailEntity : findGoodsAdminDetailEntitys) {
				if(findGoodsAdminDetailEntity.getLostGoodsQty()==null){
					findGoodsAdminDetailEntity.setLostGoodsQty(0L);
				}
				if(findGoodsAdminDetailEntity.getTotalQty()==null){
					findGoodsAdminDetailEntity.setTotalQty(0L);
				}
				if(findGoodsAdminDetailEntity.getVolume()==null){
					findGoodsAdminDetailEntity.setVolume(new BigDecimal(0));
				}
				if(findGoodsAdminDetailEntity.getWeight()==null){
					findGoodsAdminDetailEntity.setWeight(new BigDecimal(0));
				}
			}
		}
		return findGoodsAdminDetailEntitys ;
	}
}
