package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ExpressDiscountPlanVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.CollectionUtils;

/**
 * @author Administrator
 * 快递折扣方案
 */
public class ExpressDiscountPlanAction extends AbstractAction {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志处理.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDiscountPlanAction.class);
	/**
	 * 快递折扣方案service
	 */
	private IExpressDiscountPlanService expressDiscountPlanService;
	/**
	 * 数据交互vo
	 */
	private ExpressDiscountPlanVo expressDiscountPlanVo = new ExpressDiscountPlanVo();
	/**
	 * 导入文件.
	 */
	private File uploadFile;
	/**
	 * 替代空白字符串"".
	 */
	private static final String BLANK = "";
	/**
	 * 产品信息service.
	 */
	private IProductService productService;
	/**
	 * 区域信息service.
	 */
	private IRegionExpressService regionExpressService;
	/**
	 * 价格添加service.
	 */
	private IPricingValueAddedService pricingValueAddedService;

	public void setPricingValueAddedService(
			IPricingValueAddedService pricingValueAddedService) {
		this.pricingValueAddedService = pricingValueAddedService;
	}

	/**
	 * 客户Service
	 */
	private ICustomerService customerService;

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	private IDataDictionaryValueService dataDictionaryValueService;

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setRegionExpressService(
			IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
	}

	public void setExpressDiscountPlanService(
			IExpressDiscountPlanService expressDiscountPlanService) {
		this.expressDiscountPlanService = expressDiscountPlanService;
	}

	public ExpressDiscountPlanVo getExpressDiscountPlanVo() {
		return expressDiscountPlanVo;
	}

	public void setExpressDiscountPlanVo(
			ExpressDiscountPlanVo expressDiscountPlanVo) {
		this.expressDiscountPlanVo = expressDiscountPlanVo;
	}

	/**
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.addExpressDiscountPlan
	 * @Description:添加快递折扣方案
	 * @return
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 下午2:54:20
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-10 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String addExpressDiscountPlan() {
		try {
			if (CollectionUtils.isNotEmpty(expressDiscountPlanVo
					.getChannelCodes())) {
				expressDiscountPlanVo.getExpressDiscountEntity()
						.setChannelCodes(
								expressDiscountPlanVo.getChannelCodes());
			}
			// 封装周特惠信息
			if (CollectionUtils.isNotEmpty(expressDiscountPlanVo.getWeeks())) {
				Map<String, String> map = packageWeeks(expressDiscountPlanVo
						.getWeeks());
				if (StringUtil.isNotBlank(map.get("weekCodes"))) {
					expressDiscountPlanVo.getExpressDiscountEntity()
							.setWeekCodes(map.get("weekCodes"));
				}
				if (StringUtil.isNotBlank(map.get("weekNames"))) {
					expressDiscountPlanVo.getExpressDiscountEntity()
							.setWeekNames(map.get("weekNames"));
				}
			} else {
				expressDiscountPlanVo.getExpressDiscountEntity().setWeekCodes(
						"ALL");
				expressDiscountPlanVo.getExpressDiscountEntity().setWeekNames(
						"全周");
			}
			expressDiscountPlanService.insertSelective(expressDiscountPlanVo
					.getExpressDiscountEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增快递折扣方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.updateByIdSelective
	 * @Description:根据折扣方案ID更新折扣方案信息
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 上午10:37:50
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-12 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String updateByIdSelective() {
		try {
			/**
			 * 更新操作分为以下两步 1.更新折扣方案主信息 2.更新，删除，或者新增折扣方案对应的渠道信息
			 */
			// 获取原方案的渠道信息
			List<String> oldChannelCodes = expressDiscountPlanVo
					.getOldChannelCodes();
			// 设置修改后的渠道信息
			expressDiscountPlanVo.getExpressDiscountEntity().setChannelCodes(
					expressDiscountPlanVo.getChannelCodes());
			// 封装周特惠信息
			if (CollectionUtils.isNotEmpty(expressDiscountPlanVo.getWeeks())) {
				Map<String, String> map = packageWeeks(expressDiscountPlanVo
						.getWeeks());
				if (StringUtil.isNotBlank(map.get("weekCodes"))) {
					expressDiscountPlanVo.getExpressDiscountEntity()
							.setWeekCodes(map.get("weekCodes"));
				}
				if (StringUtil.isNotBlank(map.get("weekNames"))) {
					expressDiscountPlanVo.getExpressDiscountEntity()
							.setWeekNames(map.get("weekNames"));
				}
			} else {
				expressDiscountPlanVo.getExpressDiscountEntity().setWeekCodes(
						"ALL");
				expressDiscountPlanVo.getExpressDiscountEntity().setWeekNames(
						"全周");
			}
			expressDiscountPlanService.updateByIdSelective(
					expressDiscountPlanVo.getExpressDiscountEntity(),
					oldChannelCodes);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("修改快递折扣方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.deleteExpressDsicountPlan
	 * @Description:根据快递折扣方案的ID删除对应的折扣方案
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午3:45:04
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-12 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String deleteExpressDsicountPlan() {
		try {
			expressDiscountPlanService.deleteByIds(expressDiscountPlanVo
					.getExpressDiscountPlanIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("删除快递折扣方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}

	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.queryExpressDiscountPlanList
	 * @Description:根据查询条件，分页查询满足条件的快递折扣方案
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午3:51:55
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-12 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String queryExpressDiscountPlanList() {
		try {
			List<ExpressDiscountEntity> discountEntityList = expressDiscountPlanService
					.queryExpressDiscountPlanList(
							expressDiscountPlanVo.getExpressDiscountEntity(),
							getStart(), getLimit());
			expressDiscountPlanVo
					.setExpressDiscountEntityList(discountEntityList);
			Long totalCount = expressDiscountPlanService
					.queryExpressDiscountPlanListCount(expressDiscountPlanVo
							.getExpressDiscountEntity());
			this.setTotalCount(totalCount);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("查询快递折扣方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.queryExpressDiscountPlanById
	 * @Description:根据快递折扣方案ID查询折扣方案
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午4:40:49
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-12 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String queryExpressDiscountPlanById() {
		try {
			ExpressDiscountEntity discountEntity = expressDiscountPlanService
					.queryExpressDiscountPlanById(expressDiscountPlanVo
							.getExpressDiscountEntity());
			expressDiscountPlanVo.setExpressDiscountEntity(discountEntity);
			expressDiscountPlanVo.setChannelCodes(discountEntity
					.getChannelCodes());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("查询快递折扣方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}

	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.addDiscountDetail
	 * @Description:新增快递折扣方案明细信息
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-13 上午10:28:46
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-13 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String addDiscountDetail() {
		try {
			expressDiscountPlanService
					.insertDiscountDetailSelective(expressDiscountPlanVo
							.getExpressDiscountDto());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增快递折扣方案明细信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.queryExpressDiscountPlanDetailList
	 * @Description:查询快递折扣方案的明细信息
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-13 下午2:01:09
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-13 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String queryExpressDiscountPlanDetailList() {
		try {
			List<ExpressDiscountDto> detailList = expressDiscountPlanService
					.queryExpressDiscountPlanDetailList(
							expressDiscountPlanVo.getExpressDiscountDto(),
							getStart(), getLimit());
			expressDiscountPlanVo.setExpressDiscountDtoList(detailList);
			this.setTotalCount(expressDiscountPlanService
					.queryExpressDiscountPlanDetailListCount(expressDiscountPlanVo
							.getExpressDiscountDto()));
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("查询快递折扣方案明细信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.activeExpressDiscountPlan
	 * @Description:激活快递折扣方案
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-13 下午2:14:15
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-13 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String activeExpressDiscountPlan() {
		try {
			Date nowDate = new Date();
			if (null != expressDiscountPlanVo.getExpressDiscountEntity()
					.getBeginTime()
					&& nowDate.compareTo(expressDiscountPlanVo
							.getExpressDiscountEntity().getBeginTime()) > 0) {
				return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			}
			expressDiscountPlanService
					.activeExpressDiscountPlan(expressDiscountPlanVo
							.getExpressDiscountEntity());
			return returnSuccess(MessageType.ACTIVE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("激活快递折扣方案出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.stopExpressDiscountPlan
	 * @Description:中止快递折扣方案
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-13 下午2:24:32
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-13 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String stopExpressDiscountPlan() {
		try {
			expressDiscountPlanService
					.stopExpressDiscountPlan(expressDiscountPlanVo
							.getExpressDiscountEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("中止快递折扣方案出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.addExpressDiscountDetail
	 * @Description:新增快递折扣方案明细信息
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午9:26:05
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String addExpressDiscountDetail() {
		try {
			expressDiscountPlanService
					.insertDiscountDetailSelective(expressDiscountPlanVo
							.getExpressDiscountDto());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增快递折扣方案明细出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.queryExpressDiscountDetail
	 * @Description:查询快递折扣方案明细
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午9:28:43
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String queryExpressDiscountDetail() {
		try {
			List<ExpressDiscountDto> expressDiscountDtoList = expressDiscountPlanService
					.queryExpressDiscountPlanDetailByCondition(expressDiscountPlanVo
							.getExpressDiscountDto());
			if (CollectionUtils.isNotEmpty(expressDiscountDtoList)) {
				expressDiscountPlanVo
						.setExpressDiscountDto(expressDiscountDtoList.get(0));
			}
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("查询快递折扣方案明细出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.updateExpressDiscountDetail
	 * @Description:修改快递折扣方案明细
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午9:29:46
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String updateExpressDiscountDetail() {
		try {
			expressDiscountPlanService
					.updateDiscountDetailSelective(expressDiscountPlanVo
							.getExpressDiscountDto());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("修改快递折扣方案明细出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.
	 *            ExpressDiscountPlanAction.deleteExpressDiscountDetail
	 * @Description:根据快递折扣方案明细ID 删除对应的折扣明细 支持批量删除
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午9:37:40
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2015-1-14 DP-FOSS-YANGKANG v1.0.0 create
	 */
	@JSON
	public String deleteExpressDiscountDetail() {
		try {
			expressDiscountPlanService.deleteDiscountDetailById(
					expressDiscountPlanVo.getExpressDiscountEntity(),
					expressDiscountPlanVo.getExpressDiscountDetailIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("删除快递折扣方案明细出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	/***
	 * 
	 * @author: 200945 吴涛
	 * @Title: upgradeExpressDiscountPlan
	 * @Description: 此action用于【前台升级】的按钮的实现方法
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String upgradeExpressDiscountPlan() {
		try {
			expressDiscountPlanService
					.upgradeExpressDiscountPlan(expressDiscountPlanVo
							.getExpressDiscountEntity());
			return returnSuccess(MessageType.COPY_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("在升级折扣方案时出现异常：" + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * dp-foss-dongjialing 225131 周特惠字段处理
	 * 
	 */
	public Map<String, String> packageWeeks(List<String> weeks) {
		StringBuffer weekCodes = new StringBuffer();
		StringBuffer weekNames = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		for (String weekCode : weeks) {
			if ("ALL".equals(weekCode)) {
				weekCodes.append("ALL");				
				break;
			} else if ("MON".equals(weekCode)) {
				weekCodes.append("MON");
				weekNames.append("周一");
			} else if ("TUE".equals(weekCode)) {
				weekCodes.append("TUE");
				weekNames.append("周二");
			} else if ("WED".equals(weekCode)) {
				weekCodes.append("WED");
				weekNames.append("周三");
			} else if ("THU".equals(weekCode)) {
				weekCodes.append("THU");
				weekNames.append("周四");
			} else if ("FRI".equals(weekCode)) {
				weekCodes.append("FRI");
				weekNames.append("周五");
			} else if ("SAT".equals(weekCode)) {
				weekCodes.append("SAT");
				weekNames.append("周六");
			} else if ("SUN".equals(weekCode)) {
				weekCodes.append("SUN");
				weekNames.append("周日");
			}
			weekCodes.append(",");
			weekNames.append(",");
		}
		if (weekCodes.toString().contains("ALL")) {
			weekCodes.delete(0, weekCodes.length());
			weekNames.delete(0, weekCodes.length());
			weekCodes.append("ALL");
			weekNames.append("全周");
		} else {
			weekCodes.deleteCharAt(weekCodes.length() - 1);
			weekNames.deleteCharAt(weekNames.length() - 1);
		}
		map.put("weekCodes", weekCodes.toString());
		map.put("weekNames", weekNames.toString());
		return map;

	}

	/**
	 * @Method: importDiscount
	 * @Description: 导入方案和方案明细.
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String importDiscount() {
		// 初始化
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 如果是2003版本
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 如果是2007版本
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(0);// 默认第一个
				// 读取Excel的所用数据
				// 读取最终数据结果，key为始发区域名称 ，值为折扣主方案数据
				Map<String, List<String>> discountMap = new HashMap<String, List<String>>();
				// 读取最终数据结果，key为始发区域名称 ，值为折扣方案数据明细
				Map<String, List<String>> detailMap = new HashMap<String, List<String>>();
				// 读取最终数据结果，key为方案名称 ，值为方案名称
				Map<String, String> planNameMap = new HashMap<String, String>();
				// 收集excel文件中的折扣方案区域map ，key为区域名称，值为区域名称
				Map<String, String> regionMap = new HashMap<String, String>();
				// 收集excel文件中的折扣方案货物类型map ，key为货物类型名称，值为货物类型名称
				Map<String, String> goodsMap = new HashMap<String, String>();
				// 收集excel文件中的产品信息map，key为产品名称，值为产品名称
				Map<String, String> productMap = new HashMap<String, String>();
				// 收集excel文件中客户信息map,key为客户编码，值为客户编码
				Map<String, String> customerMap = new HashMap<String, String>();
				// 收集excel文件中周特惠信息map,key为客户编码，值为客户编码
				Map<String, String> weeksNameMap = new HashMap<String, String>();
				// 收集excel文件中渠道信息map,key为客户编码，值为客户编码
				Map<String, String> channelMap = new HashMap<String, String>();
				// 将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
				makeExcelDtos(discountMap, detailMap, weeksNameMap,
						planNameMap, goodsMap, regionMap, productMap,
						customerMap, channelMap, sheet);
				if (discountMap == null || discountMap.size() < 1) {
					throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
				}
				checkDiscountPlanIsExists(planNameMap);
				// 把货物类型信息map通过service查询出相关产品的实体
				Map<String, GoodsTypeEntity> googsTypeEntityMap = processGoodsTypeInfo(goodsMap);
				// 把产品信息map通过service查询出相关产品的实体
				Map<String, ProductEntity> productEntityMap = processProductInfo(productMap);
				// 把区域信息map通过service查询出相关的区域信息
				Map<String, PriceRegionExpressEntity> priceRegionEntityMap = processPriceRegionInfo(regionMap);
				// 把客户信息map通过service查询出相关产品的实体
				Map<String, CustomerEntity> customerEntityMap = processCustomerInfo(customerMap);
				// 把客户信息map通过service查询出相关产品的实体
				Map<String, String> weeksEntityMap = validateWeekIndulgence(weeksNameMap);
				// 把客户信息map通过service查询出相关产品的实体
				Map<String, List<DataDictionaryValueEntity>> channelEntityMap = validateChannel(channelMap);
				// 批量新增折扣方案数据
				expressDiscountPlanService.addDiscountBatch(discountMap,
						detailMap, googsTypeEntityMap, priceRegionEntityMap,
						productEntityMap, customerEntityMap, weeksEntityMap,
						channelEntityMap);
			}
			return super.returnSuccess();
		} catch (FileException e) {
			return super.returnError(e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件被破坏，请重新制作导入文件" + e);
		} catch (PricePlanException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件不符合规范，请重新制作导入文件" + e);
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					return returnError("文件关闭失败");
				}
			}
		}
	}

	/**
	 * @Method: makeExcelDtos
	 * @Description: 读取excel数据并作必要的验证
	 * @param disCountMap
	 *            , detailMap, weeksNameMap , planNameMap, goodsMap ,regionMap,
	 *            productMap, customerMap , channelMap, sheet 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void makeExcelDtos(Map<String, List<String>> disCountMap,
			Map<String, List<String>> detailMap,
			Map<String, String> weeksNameMap, Map<String, String> planNameMap,
			Map<String, String> goodsMap, Map<String, String> regionMap,
			Map<String, String> productMap, Map<String, String> customerMap,
			Map<String, String> channelMap, Sheet sheet) {
		StringBuilder errorMessage = new StringBuilder();
		if (sheet != null && detailMap != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// EXCEL行记录
			if (rowCount < 2) {
				return;// no data
			}
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				// 取得一行的数据
				Row row1 = sheet.getRow(rowNum);
				Cell cell = null;
				// 存储需要验证的Discount数据
				List<String> discoutSheetData = new ArrayList<String>();
				cell = row1.getCell(0);
				if (cell != null) {
					if (StringUtil.isBlank(obtainStringVal(cell))) {
						if (checkIsBlankRow(rowNum, sheet)) {
							throw new FileException("折扣方案导入时发生错误：" + "第"
									+ (rowNum + 1) + "行方案名称不正确，请检查",
									errorMessage.toString());
						} else
							break;
					}
				} else {
					// 判断是否是空白行
					if (checkIsBlankRow(rowNum, sheet)) {
						throw new FileException("折扣方案导入时发生错误：" + "第"
								+ (rowNum + 1) + "行方案名称不正确，请检查",
								errorMessage.toString());
					} else
						break;

				}
				for (int loop = 0; loop <= NumericConstants.NUM_5; loop++) {
					cell = row1.getCell(loop);
					if (cell != null) {
						discoutSheetData.add(obtainStringVal(cell));
						// if(loop == FOUR && null !=
						// discoutSheetData.get(THREE) && null !=
						// discoutSheetData.get(FOUR)){
						// if(DateUtil.stringToDate(discoutSheetData.get(FOUR),PARTEN))
						// < discoutSheetData.get(THREE)){
						//
						// }
						// }
					} else {
						errorMessage.append("第" + (rowNum + 1) + "行第"
								+ (loop + 1) + "列数据不正确，请检查");
					}
				}
				// 存储detail数据
				List<String> detailSheetDate = new ArrayList<String>();
				for (int loop = NumericConstants.NUM_8; loop <= NumericConstants.NUM_17; loop++) {
					cell = row1.getCell(loop);
					if (cell != null) {
						detailSheetDate.add(obtainStringVal(cell));
					} else {
						errorMessage.append("第" + (rowNum + 1) + "行第"
								+ (loop + 1) + "列数据不正确，请检查");
					}
				}
				if (errorMessage.length() > 0) {
					throw new FileException("折扣方案导入时发生错误： "
							+ errorMessage.toString(), errorMessage.toString());
				}
				for (int loop = NumericConstants.NUM_6; loop <= NumericConstants.NUM_7; loop++) {
					cell = row1.getCell(loop);
					String data = BLANK;
					if (cell != null) {
						data = obtainStringVal(cell);
					}
					discoutSheetData.add(data);
				}
				discoutSheetData.add((rowNum + 1) + BLANK);
				// 封装方案名称到map
				planNameMap.put(discoutSheetData.get(0),
						discoutSheetData.get(0)+ "#" +(rowNum + 1));
				// 封装客户信息到map
				customerMap.put(discoutSheetData.get(1),
						discoutSheetData.get(1)+ "#" +(rowNum + 1));
				// 封装区域信息到map
				regionMap.put(detailSheetDate.get(0),
						detailSheetDate.get(0)+ "#" +(rowNum + 1));
				regionMap.put(detailSheetDate.get(1),
						detailSheetDate.get(1)+ "#" +(rowNum + 1));
				// 封装产品信息到map
				productMap.put(detailSheetDate.get(NumericConstants.NUM_3),
						detailSheetDate.get(NumericConstants.NUM_3)+ "#" +(rowNum + 1));
				// 封装货物类型信息到map
				goodsMap.put(detailSheetDate.get(NumericConstants.NUM_4),
						detailSheetDate.get(NumericConstants.NUM_4)+ "#" +(rowNum + 1));
				// 封装周特惠到map
				weeksNameMap.put(discoutSheetData.get(NumericConstants.NUM_5),
						discoutSheetData.get(NumericConstants.NUM_5)+ "#" +(rowNum + 1));
				String key = discoutSheetData.get(0) + "#"
						+ discoutSheetData.get(1) + "#"
						+ detailSheetDate.get(0) + "#"
						+ detailSheetDate.get(1) + "#"
						+ detailSheetDate.get(NumericConstants.NUM_3) + "#"
						+ detailSheetDate.get(NumericConstants.NUM_4) + "#"
						+ discoutSheetData.get(NumericConstants.NUM_5);
				if (StringUtil.isNotBlank(discoutSheetData.get(NumericConstants.NUM_6))) {
					key += "#" + discoutSheetData.get(NumericConstants.NUM_6);
					channelMap.put(discoutSheetData.get(NumericConstants.NUM_6),
							discoutSheetData.get(NumericConstants.NUM_6)+ "#" +(rowNum + 1));
				}
				if (disCountMap.get(key) == null)
					disCountMap.put(key, discoutSheetData);
				else {
					String repeatRow = disCountMap.get(key).get(NumericConstants.NUM_8);
					throw new FileException("折扣方案导入时第" + repeatRow + "行与第"+ (rowNum + 1) +"行出现重复数据 "
							,"折扣方案导入时第" + repeatRow + "行与第"+ (rowNum + 1) +"行出现重复数据 ");
				}
				detailMap.put(key, detailSheetDate);
			}
		}
	}

	
	/**
	 * @Method: validateChannel
	 * @Description: 验证并封装渠道信息
	 * @param @param channelMap
	 * @param @return 设定文件
	 * @return Map<String,List<DataDictionaryValueEntity>> 返回类型
	 * @throws
	 */
	private Map<String, List<DataDictionaryValueEntity>> validateChannel(
			Map<String, String> channelMap) {
		if (channelMap == null || channelMap.size() < 1) {
			return null;
		}
		List<DataDictionaryValueEntity> channelList = dataDictionaryValueService
				.queryDataDictionaryValueByTermsCode(WaybillConstants.DICTIONARY_TERMS_CODE);
		
		Iterator<String> it = channelMap.keySet().iterator();
		Map<String, List<DataDictionaryValueEntity>> resultMap = new HashMap<String, List<DataDictionaryValueEntity>>();
		List<DataDictionaryValueEntity> mapValue = null;
		while (it.hasNext()) {
			String channel = it.next();
			
			if(channel == null){
				throw new FileException("折扣方案导入时发生错误：channel为空，请检查", null);
			}
			
			String row = channelMap.get(channel).split("#")[1];
			// 检查渠道
			if (channel.lastIndexOf(",") == channel.length()-1 || ",".equals(channel.substring(0,1))  ) {
				throw new FileException("折扣方案导入时发生错误：第"+ row + "行渠道" + channel
						+ "格式不正确：首尾不允许出现逗号，请检查", null);
			}else if(channel.indexOf("，") > -1){
				throw new FileException("折扣方案导入时发生错误：第"+ row + "行渠道" + channel
						+ "格式不正确：不允许出现中文逗号，请检查", null);
			}else{
				if(channel.indexOf(",") > -1){
				String channels[] = channel.split(",");
				mapValue = new ArrayList<DataDictionaryValueEntity>();
				for (String c : channels) {
					boolean isExists = false;
					for (int i = 0; i < channelList.size() && !isExists; i++) {
						if (c != null
								&& c.equals(channelList.get(i).getValueName())) {
							mapValue.add(channelList.get(i));
							isExists = true;
						}
					}
					if (!isExists) {
						throw new FileException("折扣方案导入时发生错误：第"+ row + "行渠道" + c
								+ "在数据中没有找到，请检查", null);
					}
				}
				resultMap.put(channel, mapValue);
				}else{
					if(channel.indexOf("、") > -1 || channel.indexOf("#") > -1 || channel.indexOf(".") > -1 
							|| channel.indexOf("。") > -1 || channel.indexOf("|") > -1){
						throw new FileException("折扣方案导入时发生错误：第"+ row + "行渠道" + channel
								+ "分隔符必须是英文逗号，请检查", null);
					}
					mapValue = new ArrayList<DataDictionaryValueEntity>();
					boolean isExists = false;
					for (int i = 0; i < channelList.size() && !isExists; i++) {
						if (channel != null
								&& channel
								.equals(channelList.get(i).getValueName())) {
							mapValue.add(channelList.get(i));
							isExists = true;
						}
					}
					if (!isExists) {
						throw new FileException("折扣方案导入时发生错误：第"+ row + "行渠道" + channel
								+ "在数据中没有找到，请检查", null);
					}
				}
				}
			} 
				
		
		return resultMap;
	}
	
	/**
	 * @Method: checkIsBlankRow
	 * @Description: 验证是否是空白行
	 * @param rowNum
	 * @param sheet
	 * @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean checkIsBlankRow(int rowNum, Sheet sheet) {
		int rowCount = sheet.getPhysicalNumberOfRows();
		Cell cell = null;
		boolean isBlankRow = false;
		for (int i = rowNum + 1; i < rowCount / NumericConstants.NUM_3 && !isBlankRow; i++) {
			Row row = sheet.getRow(i);
			for (int col = 0; col < NumericConstants.NUM_18 && !isBlankRow; col++)
				cell = row.getCell(col);
			if (cell != null && StringUtil.isNotBlank(obtainStringVal(cell))) {
				isBlankRow = true;
			}
		}
		return isBlankRow;
	}

	/**
	 * @Method: validateWeekIndulgence
	 * @Description: 验证周特惠信息
	 * @param weeksName
	 * @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	private Map<String, String> validateWeekIndulgence(
			Map<String, String> weeksName) {
		Iterator<String> it = weeksName.keySet().iterator();
		Map<String, String> weeksMap = PricingConstants.PricingColumnConstants
				.getALLWeeksMap();
		Map<String, String> resultMap = new HashMap<String, String>();
		while (it.hasNext()) {
			String week = it.next();
			// 检查周特惠
			String weekCode = "";
			String row = weeksName.get(week).split("#")[1];
			// 检查渠道
			if (week.lastIndexOf(",") == week.length()-1 || ",".equals(week.substring(0,1))  ) {
				throw new FileException("折扣方案导入时发生错误：第"+ row + "行周特惠" + week
						+ "格式不正确：首尾不允许出现逗号，请检查", null);
			}else if(week.indexOf("，") > -1){
				throw new FileException("折扣方案导入时发生错误：第"+ row + "行周特惠" + week
						+ "格式不正确：不允许出现中文逗号，请检查", null);
			}else{
				if (week.indexOf(",") > 0) {
					String weeks[] = week.split(",");
					StringBuilder sb = new StringBuilder("");
					for (String w : weeks) {
						if (weeksMap.get(w) != null) {
							sb.append(weeksMap.get(w)).append(",");
						} else{
							throw new FileException("折扣方案导入时发生错误：第"+ row + "行周特惠  " + w + " 不正确，请检查", null);
						}
					}
					weekCode = sb.substring(0,sb.length() - 1);
				} else {
					if(week.indexOf("、") > -1 || week.indexOf("#") > -1 || week.indexOf(".") > -1 
							|| week.indexOf("。") > -1 || week.indexOf("|") > -1){
						throw new FileException("折扣方案导入时发生错误：第"+ row + "行周特惠 " + week
								+ "分隔符必须是英文逗号，请检查", null);
					}
					if (weeksMap.get(week) != null)
						weekCode = weeksMap.get(week);
					else{
						throw new FileException("折扣方案导入时发生错误：第"+ row + "行周特惠  " + week
								+ " 不正确，请检查", null);
					}
				}
			}
			resultMap.put(week, weekCode);
		}

		return resultMap;
	}

	/**
	* 单元格取值.
	* 
	* @Method: obtainStringVal 
	* @param cell
	* @return String
	* @author WangZengming
	* @date 2015-7-31 下午2:46:20
	* @see
	*/
	private String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = BLANK;
		// 单元格类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = com.deppon.foss.util.DateUtils.convert(
						HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
						com.deppon.foss.util.DateUtils.DATE_FORMAT);
			} else {// 纯数字
				cellVal = String.valueOf(new DecimalFormat("#").format(cell
						.getNumericCellValue()));

			}
			break;
		// 此行表示单元格的内容为string类型
		case HSSFCell.CELL_TYPE_STRING: // 字符串型
			cellVal = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型
			// 读公式计算值
			cellVal = String.valueOf(cell.getNumericCellValue());
			if (cellVal.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		case HSSFCell.CELL_TYPE_BLANK: // 空值
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellVal = BLANK;
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal;
	}

	public static void main(String are[]) {
		ExpressDiscountPlanAction ob = new ExpressDiscountPlanAction();
		File uploadFile = new File("c:\\test.xlsx");
		ob.setUploadFile(uploadFile);
		ob.importDiscount();
	}

	/**
	* 验证折扣方案是否存在，避免service中事物处理过长.
	* 
	* @Method: checkDiscountPlanIsExists 
	* @param planNameMap void
	* @author WangZengming
	* @date 2015-7-31 下午2:45:43
	* @see
	*/
	private void checkDiscountPlanIsExists(Map<String, String> planNameMap) {
		// 非空判断
		if (planNameMap == null || planNameMap.size() < 1) {
			throw new FileException("导入数据中没有方案名称，请检查", "导入数据中没有方案名称，请检查");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(planNameMap.keySet());
		// 批量查找方案名称
		Map<String, ExpressDiscountEntity> findResult = expressDiscountPlanService
				.findExpressDiscountByPlanNames(names);
		if (findResult == null || findResult.size() < 1) {
			return;
		}

		Iterator<String> it = planNameMap.keySet().iterator();
		while (it.hasNext()) {
			// 逐条验证有没有折扣方案区域
			String object = it.next();
			if (findResult.get(object) != null) {
				String row = planNameMap.get(object).split("#")[1];
				throw new FileException("第"+ row +"行方案名称：" + object + "，在数据库中已存在，请检查",
						"第"+ row +"行方案名称：" + object + "，在数据库中已存在，请检查");
			}
		}
	}

		 /**
	     * 处理产品信息，避免service中事物处理过长.
	     * 
	     * @param nameMap
	     * @return
	     * @author 王增明
	     * @date 2012-12-24 下午5:46:52
	     * @see
	     */
	    private Map<String, GoodsTypeEntity> processGoodsTypeInfo(
		    Map<String, String> goodsMap) {
		// 非空判断
		if (goodsMap == null || goodsMap.size() < 1) {
		    throw new FileException("导入数据中没有货物类型信息，请检查", "导入数据中没有货物类型信息，请检查");
		}
		List<GoodsTypeEntity> goodsTypeList = pricingValueAddedService.findGoodsTypeByCondiction();
		
		if (CollectionUtils.isEmpty(goodsTypeList)) {
		    throw new FileException("数据库中没有货物类型信息，请检查", "数据库中没有货物类型信息，请检查");
		}
		Map<String, GoodsTypeEntity> nameEntityMap = new HashMap<String, GoodsTypeEntity>();
		Iterator<String> keyIt = goodsMap.keySet().iterator();
		while (keyIt.hasNext()) {
		    // 逐个比对，是否导入的货物类型在数据库里存在‘
			
		    String goodsname = keyIt.next();
		    for (int loop = 0; loop < goodsTypeList.size(); loop++) {
		    	GoodsTypeEntity goodsTypeEntity = goodsTypeList.get(loop);
			if (StringUtil.equals(goodsname, goodsTypeEntity.getName())) {
				nameEntityMap.put(goodsname, goodsTypeEntity);
				// 找到后这次循环结束
				break;
			}
		}
		if (nameEntityMap.get(goodsname) == null) {
			// 最后都没找到，那就是出问题了
			String row = goodsMap.get(goodsname).split("#")[1];
			throw new FileException("数据库中没有货物类型:第"+ row + "行" + goodsname + "，请检查",
					"数据库中没有货物类型:第"+ row + "行" + goodsname + "，请检查");
		}
	}
	return nameEntityMap;
}

	/**
	* 处理产品信息，避免service中事物处理过长.
	* 
	* @Method: processProductInfo 
	* @param nameMap
	* @return Map<String,ProductEntity>
	* @author WangZengming
	* @date 2015-7-31 下午2:44:29
	* @see
	*/
	private Map<String, ProductEntity> processProductInfo(
			Map<String, String> nameMap) {
		// 非空判断
		if (nameMap == null || nameMap.size() < 1) {
			throw new FileException("导入数据中没有产品信息，请检查", "导入数据中没有产品信息，请检查");
		}
		// 读取二级产品，因为折扣方案数据都是二级的
		List<ProductEntity> allProductInfoList = productService
				.getLevel2And3ProductInfo();
		List<ProductEntity> productInfoList = new ArrayList<ProductEntity>();
		for (ProductEntity productEntity : allProductInfoList) {
			if(productService.onlineDetermineIsExpressByProductCode(productEntity.getCode(), new Date())){
				productInfoList.add(productEntity);
			}
		}
//		ProductEntity allProductEntity = new ProductEntity();
//		allProductEntity.setName("全部产品");
//		productInfoList.add(allProductEntity);
		if (CollectionUtils.isEmpty(productInfoList)) {
			throw new FileException("数据库中没有产品信息，请检查", "数据库中没有产品信息，请检查");
		}
		Map<String, ProductEntity> nameEntityMap = new HashMap<String, ProductEntity>();
		Iterator<String> keyIt = nameMap.keySet().iterator();
		while (keyIt.hasNext()) {
			// 逐个比对，是否导入的产品在数据库里存在
			String productname = keyIt.next();
			for (int loop = 0; loop < productInfoList.size(); loop++) {
				ProductEntity productEntity = productInfoList.get(loop);
				if (StringUtil.equals(productname, productEntity.getName())) {
					nameEntityMap.put(productname, productEntity);
					// 找到后这次循环结束
					break;
				}
			}
			if (nameEntityMap.get(productname) == null) {
				// 最后都没找到，那就是出问题了
				String row = nameMap.get(productname).split("#")[1];
				throw new FileException("数据库中没有产品:第"+ row + "行" + productname + "，请检查",
						"数据库中没有产品:第"+ row + "行" + productname + "，请检查");
			}
		}
		return nameEntityMap;
	}


	/**
	* 处理折扣方案区域信息，批量根据名字把区域查询出来.
	* 
	* @Method: processPriceRegionInfo 
	* @param regionMap
	* @return Map<String,PriceRegionExpressEntity>
	* @author WangZengming
	* @date 2015-7-31 下午2:42:58
	* @see
	*/
	private Map<String, PriceRegionExpressEntity> processPriceRegionInfo(
			Map<String, String> regionMap) {
		// 非空判断
		if (regionMap == null || regionMap.size() < 1) {
			throw new FileException("导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(regionMap.keySet());
		// 批量查找区域数据
		Map<String, PriceRegionExpressEntity> findResult = regionExpressService
				.findRegionByNames(names, PricingConstants.PRICING_REGION,
						new Date());
		if (findResult == null || findResult.size() < 1) {

			throw new FileException("数据库中没有匹配到导入的任何区域数据，请检查",
					"数据库中没有匹配到导入的任何区域数据，请检查");
		}

		Iterator<String> it = regionMap.keySet().iterator();
		while (it.hasNext()) {
			// 逐条验证有没有折扣方案区域
			String object = it.next();
			if (findResult.get(object) == null) {
				String row = regionMap.get(object).split("#")[1];
				throw new FileException("第"+ row +"行区域：" + object + "，在数据库中没有找到，请检查",
						"第"+ row +"行区域：" + object + "，在数据库中没有找到，请检查");
			}
		}
		return findResult;
	}

	/**
	* 处理客户信息.
	* 
	* @Method: processCustomerInfo 
	* @param customerMap
	* @return Map<String,CustomerEntity>
	* @author WangZengming
	* @date 2015-7-31 下午2:43:55
	* @see
	*/
	private Map<String, CustomerEntity> processCustomerInfo(
			Map<String, String> customerMap) {
		if (customerMap == null || customerMap.size() < 1) {
			return null;
		}
		Map<String, CustomerEntity> result = new HashMap<String, CustomerEntity>();
		CustomerEntity customerEntity = null;
		for (String c : customerMap.keySet()) {
			if (!StringUtils.isBlank(c)) {
				customerEntity = customerService.queryCustInfoByCode(c);
				if (customerEntity != null) {
					result.put(c, customerEntity);
				} else {
					String row = customerMap.get(c).split("#")[1];
					throw new FileException("第"+ row +"行客户编码为：" + c + "，在数据库中没有找到，请检查",
							"第"+ row +"行客户编码为：" + c + "，在数据库中没有找到，请检查");
				}

			}
		}
		if (result == null || result.size() < 1) {
			throw new FileException("数据库中没有匹配到导入的任何客户数据，请检查",
					"数据库中没有匹配到导入的任何客户数据，请检查");
		}
		return result;

	}
}
