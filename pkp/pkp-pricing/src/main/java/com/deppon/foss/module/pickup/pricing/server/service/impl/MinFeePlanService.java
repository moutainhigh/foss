package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMinFeePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ProductException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class MinFeePlanService implements IMinFeePlanService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(MinFeePlanService.class);

	/**
	 * 自提件最低一票业务Dao对象
	 */
	@Inject
	private IMinFeePlanDao minFeePlanDao;

	/**
	 * 产品数据管理服务
	 */
	@Inject
	private IProductService productService;

	/**
	 * 数据字典服务
	 */
	@Inject
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
   	 * 员工操作SERVICE
   	 */ 
    @Inject
    private IEmployeeService employeeService;

	/**
	 * 
	 * 添加自提价最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午11:46:49
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#addMinFeePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity)
	 */
	@Override
	@Transactional
	public int addMinFeePlan(MinFeePlanEntity minFeePlanEntity) {

		String minFeePlanEntityUUId = UUIDUtils.getUUID();
		Date currentDate = new Date();

		minFeePlanEntity.setId(minFeePlanEntityUUId);
		minFeePlanEntity.setCode(generateMinFeePlanCode());
		minFeePlanEntity.setChannelId(getChannelIdByCode(minFeePlanEntity
				.getChannelCode()));
		minFeePlanEntity.setProductCode(getProductCodeById(minFeePlanEntity
				.getProductId()));
		minFeePlanEntity.setCreateUserCode(getCurrentUserCode());
		minFeePlanEntity.setCreateTime(currentDate);
		minFeePlanEntity.setCreateOrgCode(getCurrentOrgCode());
		minFeePlanEntity.setModifyUserCode(getCurrentUserCode());
		minFeePlanEntity.setModifyTime(currentDate);
		minFeePlanEntity.setModifyOrgCode(getCurrentOrgCode());
		minFeePlanEntity.setVersionNo(currentDate.getTime());
		minFeePlanEntity.setActive(FossConstants.INACTIVE);
		minFeePlanEntity.setPlanName(this.genePlanNameByChannelCode(minFeePlanEntity
				.getChannelCode()));
		return minFeePlanDao.insertMinFeePlan(minFeePlanEntity);
	}

	/**
	 * 
	 * 根据指定条件查询最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午11:45:52
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#getMinFeePlanEntityByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity,
	 *      int, int)
	 */
	@Override
	public List<MinFeePlanEntity> getMinFeePlanEntityByCondition(
			MinFeePlanEntity minFeePlanEntity, int start, int limit) {
		if ("ALL".equals(minFeePlanEntity.getActive())) {
			minFeePlanEntity.setActive(null);
		}
		List<MinFeePlanEntity> list = minFeePlanDao.selectMinFeePlanList(
				minFeePlanEntity, start, limit);
		if (CollectionUtils.isNotEmpty(list)) {
			for(MinFeePlanEntity toBox : list){
				boxPropertyName(toBox);
			}
		}
		return list;
	}

	/**
	 * 根据指定条件查询记录总数
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午3:30:45
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#countMinFeePlanByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity)
	 */
	@Override
	public Long countMinFeePlanByCondition(MinFeePlanEntity minFeePlanEntity) {
		if ("ALL".equals(minFeePlanEntity.getActive())) {
			minFeePlanEntity.setActive(null);
		}
		return minFeePlanDao.countMinFeePlan(minFeePlanEntity);
	}

	/**
	 * 
	 * 根据id删除最低一票记录(状态为未激活)
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:55:19
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#deleteMinFeePlan(java.util.List)
	 */
	@Override
	@Transactional
	public void deleteMinFeePlan(List<String> idList) {
		if (CollectionUtils.isEmpty(idList)) {
			throw new BusinessException("删除最低一票方案异常：idList不能为空");
		}
		for (String id : idList) {
			if (id != null && !id.trim().equals("")) {
				minFeePlanDao.deleteMinFeePlanById(id);
			}
		}
	}

	/**
	 * 
	 * 根据id查询最低一票记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:55:36
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#getMinFeePlanEntityById(java.lang.String)
	 */
	@Override
	public MinFeePlanEntity getMinFeePlanEntityById(String id) {
		if (id != null && !id.trim().equals("")) {
			return minFeePlanDao.selectMinFeePlanById(id);
		}
		return null;
	}

	/**
	 * 
	 * 更新最低一票记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午11:32:09
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#updateMinFeePlanEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity)
	 */
	@Override
	@Transactional
	public void updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity) {
		if (minFeePlanEntity.getId() == null
				|| minFeePlanEntity.getId().trim().equals("")) {
			throw new BusinessException("待更新最低一票记录ID为空");
		}
		minFeePlanEntity.setChannelId(getChannelIdByCode(minFeePlanEntity
				.getChannelCode()));
		minFeePlanEntity.setProductCode(getProductCodeById(minFeePlanEntity
				.getProductId()));
		minFeePlanEntity = this.boxUpdateInfo(minFeePlanEntity);
		minFeePlanEntity.setPlanName(this.genePlanNameByChannelCode(minFeePlanEntity.getChannelCode()));
		minFeePlanDao.updateMinFeePlanEntity(minFeePlanEntity);
	}

	/**
	 * 
	 * 查询指定日期的已激活的最低一票方案的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 下午3:41:48
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#getProductOfMinFeePlanBySpecifiedDate(java.util.Date[])
	 */
	@Override
	public List<MinFeePlanEntity> getMinFeePlanBySpecifiedDate(Date businessDate) {
		if(businessDate == null){
			businessDate = new Date();
		}
		MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
		minFeePlanEntity.setBusinessDate(businessDate);
		List<MinFeePlanEntity> list = minFeePlanDao.selectMinFeePlanBySpecifiedDateAndCondition(minFeePlanEntity);
		if(CollectionUtils.isNotEmpty(list)){
			for(MinFeePlanEntity toBox : list){
				boxPropertyName(toBox);				
			}
		}
		return list;
	}

	/**
	 * 
	 * 查询指定渠道编码和日期的已激活的最低一票方案的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 下午3:42:00
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#getActivedMinFeePlanByChannelCodeAndSpecifiedDate(java.lang.String,
	 *      java.util.Date[])
	 */
	@Override
	public List<ProductEntity> getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(
			String channelCode, Date businessDate) {
		if(businessDate == null){
			businessDate = new Date();
		}
		if(channelCode == null || channelCode.trim().equals("")){
			log.error("渠道代码不能为空！");
			throw new BusinessException("渠道代码不能为空！");
		}
		MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
		minFeePlanEntity.setBusinessDate(businessDate);
		minFeePlanEntity.setChannelCode(channelCode);
		List<MinFeePlanEntity> planList = minFeePlanDao.selectMinFeePlanBySpecifiedDateAndCondition(minFeePlanEntity);
		List<ProductEntity> productList = new ArrayList<ProductEntity>();
		if(CollectionUtils.isNotEmpty(planList)){
			for(MinFeePlanEntity index : planList){
				productList.add(this.getProductById(index.getProductId()));
			}
		}
		return productList;
	}

	/**
	 * 
	 * 查询指定渠道编码、产品编码和日期的已激活的最低一票方案的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 下午3:42:06
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService#getActivedMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(java.lang.String,
	 *      java.lang.String, java.util.Date[])
	 */
	@Override
	public ProductEntity getProductOfMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(
			String channelCode, String productCode, Date businessDate) {
		MinFeePlanEntity planEnityt = this.getMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(channelCode, productCode, businessDate);
		if(planEnityt != null){
			return this.getProductById(planEnityt.getProductId());
		}else{
			return null;			
		}
	}

	/**
	 * 查询指定渠道编码、产品编码和日期的已激活的最低一票方案，如果指定日期为空，则默认为服务器当前日期
	 */
	@Override
	public MinFeePlanEntity getMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(
			String channelCode, String productCode, Date businessDate) {
		if(businessDate == null){
			businessDate = new Date();
		}
		if(channelCode == null || channelCode.trim().equals("")){
			log.error("渠道代码不能为空！");
			throw new BusinessException("渠道代码不能为空！");
		}
		if(productCode == null || productCode.trim().equals("")){
			log.error("产品代码不能为空！");
			throw new BusinessException("产品代码不能为空！");
		}
		MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
		minFeePlanEntity.setBusinessDate(businessDate);
		minFeePlanEntity.setChannelCode(channelCode);
		minFeePlanEntity.setProductCode(productCode);
		List<MinFeePlanEntity> planList = minFeePlanDao.selectMinFeePlanBySpecifiedDateAndCondition(minFeePlanEntity);
		if(planList != null && planList.size() == 1){
			return planList.get(0);
		}else{
			return null;			
		}
	}

	/**
	 * 立即激活最低一票方案
	 */
	@Transactional
	@Override
	public void activateImmediatelyMinFeePlan(String id, Date newBeginTime) {
		if (id == null || id.trim().equals("")) {
			throw new BusinessException("待激活记录id为空");
		}
		MinFeePlanEntity minFeePlanEntity = minFeePlanDao
				.selectMinFeePlanById(id);
		if (minFeePlanEntity == null) {
			throw new BusinessException("查询不到待激活记录");
		}
		if (FossConstants.ACTIVE.equals(minFeePlanEntity.getActive())) {
			throw new BusinessException("记录已经是激活状态");
		}
		if(newBeginTime.before(new Date())){
			throw new BusinessException("生效日期必须大于当前时间");
		}
		if (newBeginTime.after(minFeePlanEntity.getEndTime())) {
			throw new BusinessException("开始时间不能晚于中止时间");
		}

		minFeePlanEntity.setBeginTime(newBeginTime);// 设置开始时间
		minFeePlanEntity.setActive(FossConstants.ACTIVE);// 设置激活状态
			
		List<MinFeePlanEntity> checkList = this.checkMinFeePlanValid(minFeePlanEntity, minFeePlanEntity.getId());
		if(CollectionUtils.isEmpty(checkList)){
			// 没有冲突，直接激活
			minFeePlanEntity = this.boxUpdateInfo(minFeePlanEntity);
			minFeePlanDao.updateMinFeePlanEntity(minFeePlanEntity);
		}else{
			if(checkList.size() == 1){
				MinFeePlanEntity entity = checkList.get(0);
				if(minFeePlanEntity.getBeginTime().before(entity.getEndTime())
						&& minFeePlanEntity.getBeginTime().after(entity.getBeginTime())){
					// 调整冲突方案的中止时间为新方案开始时间的前一秒
					Calendar newEndTimeCal = Calendar.getInstance();
					newEndTimeCal.setTime(minFeePlanEntity.getBeginTime());
					newEndTimeCal.set(Calendar.SECOND, newEndTimeCal.get(Calendar.SECOND) - 1);
					entity.setEndTime(newEndTimeCal.getTime());
					entity = this.boxUpdateInfo(entity);
					minFeePlanDao.updateMinFeePlanEntity(entity);
					
					minFeePlanEntity = this.boxUpdateInfo(minFeePlanEntity);
					minFeePlanDao.updateMinFeePlanEntity(minFeePlanEntity);
				}else{
					StringBuilder errMsg = new StringBuilder();
					errMsg.append("当前方案开始时间早于方案 [");
					errMsg.append(entity.getName());
					errMsg.append("] 的开始时间，请调整中止时间后再激活");
					log.error(errMsg.toString());
					throw new BusinessException(errMsg.toString());
				}
			}else{
				StringBuilder errMsg = new StringBuilder();
				errMsg.append("方案");
				for(MinFeePlanEntity entity : checkList){				
					errMsg.append(" [" + entity.getName() + "] ");
				}
				errMsg.append("与当前方案时间冲突，请调整后再激活");
				log.error(errMsg.toString());
				throw new BusinessException(errMsg.toString());
			}
		}

	}

	/**
	 * 立即中止最低一票方案
	 */
	@Override
	public void stopImmediatellyMinFeePlan(String id, Date newEndTime) {
		if (StringUtil.isBlank(id)) {
			throw new BusinessException("待中止记录id为空！");
		}
		if (newEndTime == null) {
			throw new BusinessException("中止日期不能为空！");
		}
		if (newEndTime.before(new Date())) {
			throw new BusinessException("中止日期必须大于当前营业日期！");
		}

		MinFeePlanEntity minFeePlanEntity = minFeePlanDao
				.selectMinFeePlanById(id);
		if (minFeePlanEntity == null) {
			log.error("id=" + id + "##没有查到对应方案记录！");
			throw new BusinessException("没有查到对应方案记录！");
		}
		if(newEndTime.before(minFeePlanEntity.getBeginTime())){
			log.error("id=" + id + "##中止日期不能早于原方案所制定的开始日期");
			throw new BusinessException("中止日期不能早于原方案的开始日期!");
		}
		if (newEndTime.after(minFeePlanEntity.getEndTime())) {
			log.error("id=" + id + "##中止日期不能延长原方案所制定的活动结束日期");
			throw new BusinessException("中止日期不能延长原方案所制定的活动结束日期!");
		}

		minFeePlanEntity.setEndTime(newEndTime);
		minFeePlanEntity = this.boxUpdateInfo(minFeePlanEntity);
		minFeePlanDao.updateMinFeePlanEntity(minFeePlanEntity);
	}
	
	/**
	 * 查询3级产品信息
	 * @return
	 */
	public List<ProductEntity> findProductByCondition(){
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		List<ProductEntity> list = productService.findExternalProductByCondition(condtion, null);
		if(CollectionUtils.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				ProductEntity entity = list.get(i);
				if(entity.getName().indexOf("整车") > -1){
					list.remove(i);
				}
			}
		}
		return list;
	}

	/**
	 * 生成minFeePlan的code
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午8:02:24
	 */
	private String generateMinFeePlanCode() {
		// TODO 需要根据业务规则重写
		Date current = new Date();
		return "ZDYPFA"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(current);
	}

	/**
	 * 
	 * 获取当前用户所在部门
	 * 
	 * @return
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext
				.getCurrentDept();
		return currentDept.getCode();
	}

	/**
	 * 
	 * 获取当前用户
	 * 
	 * @return
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}

	/**
	 * 根据channelCode获取channelId
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午8:00:00
	 */
	private String getChannelIdByCode(String channelCode) {
		return this.getChannelByCode(channelCode).getId();
	}

	private String getChannelNameByCode(String channelCode) {
		return this.getChannelByCode(channelCode).getValueName();
	}

	/**
	 * 
	 * 根据channelCode获取ChannelEntity(DataDictionaryValueEntity)
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午6:47:00
	 */
	private DataDictionaryValueEntity getChannelByCode(String channelCode) {
		if (channelCode == null) {
			throw new ProductException("channelCode不能为空", null);
		}
		return dataDictionaryValueService
				.queryDataDictionaryValueByTermsCodeValueCode(
						"PKP_PRICE_CHANNEL", channelCode);
	}

	/**
	 * 根据productId获取productCode
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午8:00:47
	 */
	private String getProductCodeById(String productId) {
		return this.getProductById(productId).getCode();
	}

	/**
	 * 
	 * 根据productId获取productName
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午6:42:59
	 */
	private String getProductNameById(String productId) {
		return this.getProductById(productId).getName();
	}

	/**
	 * 
	 * 根据productId获取productEntity对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午6:42:11
	 */
	private ProductEntity getProductById(String productId) {
		if (productId == null) {
			throw new ProductException("productId不能为空", null);
		}
		return productService.findProductById(productId);
	}

	/**
	 * 
	 * 补充name等数据库表未存储的信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-14 下午3:44:17
	 */
	private MinFeePlanEntity boxPropertyName(MinFeePlanEntity minFeePlanEntity) {
		if (minFeePlanEntity.getProductId() != null) {
			minFeePlanEntity.setProductName(getProductNameById(minFeePlanEntity
					.getProductId()));
		}
		if (minFeePlanEntity.getChannelCode() != null) {
			minFeePlanEntity
					.setChannelName(getChannelNameByCode(minFeePlanEntity
							.getChannelCode()));
		}
		String[] codeArray = new String[2];
		codeArray[0] = minFeePlanEntity.getCreateUserCode();
		codeArray[1] = minFeePlanEntity.getModifyUserCode();
		List<EmployeeEntity> employList = employeeService.queryEmployeeBatchByEmpCode(codeArray);
		if(CollectionUtils.isNotEmpty(employList)){
			for(EmployeeEntity employ : employList){
				if(minFeePlanEntity.getCreateUserCode() != null){
					if(minFeePlanEntity.getCreateUserCode().equals(employ.getEmpCode())){
						minFeePlanEntity.setCreateUserName(employ.getEmpName());
					}
				}
				if(minFeePlanEntity.getModifyUserCode() != null){
					if(minFeePlanEntity.getModifyUserCode().equals(employ.getEmpCode())){
						minFeePlanEntity.setModifyUserName(employ.getEmpName());						
					}
				}
			}
		}
		return minFeePlanEntity;
	}
	
	/**
	 * 读取与当前方案时间冲突的已激活方案
	 * @param minFeePlanEntity
	 * @param id
	 * @return
	 */
	private List<MinFeePlanEntity> checkMinFeePlanValid(MinFeePlanEntity minFeePlanEntity,
			String id){
		MinFeePlanEntity target = new MinFeePlanEntity();
		target.setChannelCode(minFeePlanEntity.getChannelCode());
		target.setChannelId(minFeePlanEntity.getChannelId());
		target.setProductCode(minFeePlanEntity.getProductCode());
		target.setProductId(minFeePlanEntity.getProductId());
		if (minFeePlanEntity.getBeginTime() == null) {
			throw new BusinessException("开始时间不能为空");
		}
		target.setBeginTime(minFeePlanEntity.getBeginTime());
		if (minFeePlanEntity.getEndTime() == null) {
			throw new BusinessException("中止时间不能为空");
		}
		target.setEndTime(minFeePlanEntity.getEndTime());
		if (id != null) {
			target.setId(id);
		}
		return minFeePlanDao.checkMinFeePlanEntityTimeValid(minFeePlanEntity);// TODO		
	}

	/**
	 * 添加修改信息
	 * 
	 * @param minFeePlanEntity
	 * @return
	 */
	private MinFeePlanEntity boxUpdateInfo(MinFeePlanEntity minFeePlanEntity) {
		minFeePlanEntity.setModifyUserCode(getCurrentUserCode());
		minFeePlanEntity.setModifyTime(new Date());
		minFeePlanEntity.setModifyOrgCode(getCurrentOrgCode());
		minFeePlanEntity.setVersionNo(new Date().getTime());
		return minFeePlanEntity;
	}
	
	/**
	 * 根据渠道生成指定的方案名
	 * 1、淘宝网、淘宝商城渠道的planName统一为“淘宝自提件”
	 * 2、阿里巴巴诚信通、阿里巴巴非诚信通渠道的planName统一为“阿里巴巴自提件”
	 * 3、其他的为“channelName + 自提件”
	 * @param channelCode
	 * @return
	 */
	private String genePlanNameByChannelCode(String channelCode){
		if(channelCode == null || channelCode.trim().length() == 0){
			return "";
		}
		if(channelCode.equals("TAOBAO") || channelCode.equals("SHANGCHENG")){
			return "淘宝自提件";
		}else if(channelCode.equals("ALIBABACXT") || channelCode.equals("ALIBABA")){
			return "阿里巴巴自提件";
		}else{
			return this.getChannelNameByCode(channelCode) + "自提件";
		}
	}

	public void setMinFeePlanDao(IMinFeePlanDao minFeePlanDao) {
		this.minFeePlanDao = minFeePlanDao;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

}
