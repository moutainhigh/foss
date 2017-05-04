package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.ICustomerReceiptHabitDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.ReflectUtils;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * @ClassName: CustomerReceiptHabitService
 * @Description: 客户收货习惯service实现类
 * @author 237982-foss-fangwenjun
 * @date 2015-3-30 上午9:02:26
 * 
 */
public class CustomerReceiptHabitService implements
		ICustomerReceiptHabitService {

	/**
	 * 注入客户收货习惯dao
	 */
	private ICustomerReceiptHabitDao customerReceiptHabitDao;
	
	/** 常量20000. */
	private static final int NUMBER = 20000;

	/**
	 * 设置customerReceiptHabitDao
	 * 
	 * @param customerReceiptHabitDao
	 *            要设置的customerReceiptHabitDao
	 */
	@Resource
	public void setCustomerReceiptHabitDao(
			ICustomerReceiptHabitDao customerReceiptHabitDao) {
		this.customerReceiptHabitDao = customerReceiptHabitDao;
	}

	/**
	 * @Title: insertReceiptHabit
	 * @Description: 插入收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	@Override
	public int insertCustomerReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 判断传入对象是否为空
		if(customerReceiptHabitEntity == null) {
			return -1;
		}
		// 判断手机号码和固话都为空时返回 -1
		if(StringUtil.isEmpty(customerReceiptHabitEntity.getCustomerMobilePhone()) 
				&& StringUtil.isEmpty(customerReceiptHabitEntity.getCustomerPhone())){
			return -1;
		}
		// 设置收货习惯Id
		customerReceiptHabitEntity.setId(UUIDUtils.getUUID());
		// 判断字段是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptHabitEntity, true)) {
			return -1;
		}
		// 返回结果
		return customerReceiptHabitDao.insertOrUpdateReceiptHabit(customerReceiptHabitEntity);
	}

	/**
	 * @Title: deleteById
	 * @Description: 根据Id删除收货习惯
	 * @param id 收货的习惯的Id
	 * @return 受影响的行数
	 */
	@Override
	public int deleteReceiptHabitById(String id) {
		// 判断传入Id是否为空
		if (id == null || "".equals(id.trim())) {
			return 0;
		}
		// 创建传入参数Map
		Map<String, String> parmMap = new HashMap<String, String>();
		// 添加Id
		parmMap.put("id", id);
		// 添加操作部门编码
		parmMap.put("orgCode", FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptHabitDao.deleteByIdAndOrgCode(parmMap);
	}
	
	/**
	 * @Title: updateReceiptHabit
	 * @Description: 修改收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	@Override
	public int updateReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		if(customerReceiptHabitEntity == null) {
			return -1;
		}
		
		// 判断操作人是否为空   为空返回-1
		if (customerReceiptHabitEntity.getModifyUser() == null 
				|| "".equals(customerReceiptHabitEntity.getModifyUser().trim())){
			return -1;
		}		
		// 判断收货习惯备注是否为空   为空返回-1
		if (customerReceiptHabitEntity.getOperatorName() == null 
				|| "".equals(customerReceiptHabitEntity.getOperatorName().trim())){
			return -1;
		}
		// 返回结果
		return customerReceiptHabitDao.updateReceiptHabit(customerReceiptHabitEntity);
	}

	/**
	 * @Title: queryReceiptHabitList
	 * @Description: 根据收货习惯值对象的属性值分页查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值(vo)对象
	 * @return 收货习惯集合
	 */
	@Override
	public List<CustomerReceiptHabitEntity> selectReceiptHabitList(
			CustomerReceiptHabitVo customerReceiptHabitVo, int start, int limit) {
		// 判断传入对象字段是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptHabitVo, false)) {
			return null;
		}
		// 设置当前部门编码
		customerReceiptHabitVo.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
		
		// 返回结果
		return customerReceiptHabitDao.selectListByParam(customerReceiptHabitVo, start, limit);
	}

	/**
	 * @Title: selectOneByParam
	 * @Description: 根据收货习惯对象的属性值查询一个收货习惯
	 * @param CustomerReceiptHabitEntity 收货习惯对象
	 * @return 收货习惯对象
	 */
	@Override
	public CustomerReceiptHabitEntity selectReceiptHabitByParam(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 判断对象是否为空
		if (customerReceiptHabitEntity == null) {
			return null;
		}
		// 设置当前部门编码
		customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptHabitDao.selectOneByParam(customerReceiptHabitEntity);
	}

	/**
	 * @Title: queryReceiptHabitCount
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯总数
	 * @param customerReceiptHabitVo
	 * @return 查询收货习惯总数
	 */
	@Override
	public Long selectReceiptHabitCount(
			CustomerReceiptHabitVo customerReceiptHabitVo) {
		// 判断操作时间开始和操作时间结束是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptHabitVo, false)) {
			return 0L;
		}
		// 设置当前部门编码
		customerReceiptHabitVo.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptHabitDao.selectCountByParam(customerReceiptHabitVo);
	}

	/**
	 * @Title: deleteByIdsAndOrgCode
	 * @Description: 根据多个Id删除收货习惯
	 * @param ids 收货的习惯的多个Id用","隔开
	 * @return 受影响的行数
	 */
	@Override
	public int deleteReceiptHabitByIds(String[] ids) {
		// 判断传入Id是否为空
		if (ids == null || ids.length == 0) {
			return 0;
		}
		// 创建传入参数Map
		Map<String, Object> parmMap = new HashMap<String, Object>();
		// 添加Id
		parmMap.put("ids", ids);
		// 添加操作部门编码
		parmMap.put("orgCode", FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptHabitDao.deleteByIdsAndOrgCode(parmMap);
	}
	
	/**
	 * @Title: selectReceiptHabitList
	 * @Description: 导出查询出的收货习惯(Excel)
	 * @param customerReceiptHabitVo 收货习惯值(vo)对象
	 * @return 收货习惯(Excel)的输入流
	 */
	@Override
	public InputStream selectReceiptHabitList(CustomerReceiptHabitVo customerReceiptHabitVo) {
		// 判断传入对象字段是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptHabitVo, false)) {
			return null;
		}
		// 设置当前部门编码
		customerReceiptHabitVo.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
		
		List<CustomerReceiptHabitEntity> receiptHabitList = customerReceiptHabitDao.selectListByParam(customerReceiptHabitVo);
		if(receiptHabitList == null || receiptHabitList.size() == 0) {
			return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(CustomerReceiptHabitEntity reciptHabit : receiptHabitList){
			List<String> columnList = new ArrayList<String>();
			// 客户名称
			columnList.add(reciptHabit.getCustomerName());
			// 客户手机号码
			columnList.add(reciptHabit.getCustomerMobilePhone());
			// 客户电话号码
			columnList.add(reciptHabit.getCustomerPhone());
			// 客户联系人名称
			columnList.add(reciptHabit.getCustomerContactName());
			// 送货时间段
			columnList.add(reciptHabit.getDeliveryTimeInterval());
			// 送货时间点
			if (StringUtils.isNotBlank(reciptHabit.getDeliveryTimeStart())) {
				columnList.add(reciptHabit.getDeliveryTimeStart() + " - " + reciptHabit.getDeliveryTimeOver());
			} else {
				columnList.add("");
			}
			// 发票类型
			columnList.add(DictUtil.rendererSubmitToDisplay(reciptHabit.getInvoiceType(), DictionaryConstants.PKP_RECEIPT_INVOICE_TYPE));
			// 发票类型描述
			columnList.add(reciptHabit.getInvoiceDetail());
			// 收货习惯备注
			columnList.add(reciptHabit.getReceiptHabitRemark());
			// 操作人名称
			columnList.add(reciptHabit.getOperatorName());
			// 操作日期
			columnList.add(DateUtils.convert(reciptHabit.getModifyDate(), DateUtils.DATE_TIME_FORMAT));
			// 操作人部门名称
			columnList.add(reciptHabit.getOperateOrgName());
			// 将列集合加入行中
			rowList.add(columnList);
		}
		// Excel  列标头名称数组
		String[] rowHeads = {"客户名称","联系人手机","联系人电话","收货联系人","送货时间段","送货时间点","发票类型","发票类型备注","收货习惯备注","操作人","操作日期","操作部门"};
	    ExportResource exportResource = new ExportResource();
	    exportResource.setHeads(rowHeads);
	    exportResource.setRowList(rowList);
	    ExportSetting exportSetting = new ExportSetting();
	    exportSetting.setSheetName("客户收货习惯列表");
	    exportSetting.setSize(NUMBER);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
        return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	/**
	 * @Title: insertOneReceiptHabit
	 * @Description: 添加收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	@Override
	public int insertOneReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 判断传入对象是否为空
		if(customerReceiptHabitEntity == null) {
			return -1;
		}
		// 判断手机号码和固话都为空时返回 -1
		if(StringUtil.isEmpty(customerReceiptHabitEntity.getCustomerMobilePhone()) 
				&& StringUtil.isEmpty(customerReceiptHabitEntity.getCustomerPhone())){
			return -1;
		}
		// 设置收货习惯Id
		customerReceiptHabitEntity.setId(UUIDUtils.getUUID());
		// 判断字段是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptHabitEntity, true)) {
			return -1;
		}
		// 返回结果
		return customerReceiptHabitDao.insertOneReceiptHabit(customerReceiptHabitEntity);
	}

}
