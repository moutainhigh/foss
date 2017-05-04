package com.deppon.foss.module.base.baseinfo.server.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProductService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 *  晚到补差价——service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-17 下午4:39:25,content:TODO </p>
 * @author 232607 
 * @date 2015-7-17 下午4:39:25
 * @since
 * @version
 */
public class LateCouponService implements ILateCouponService {
	/**
     * 加载日志文件
     */
	//private static final Logger log = Logger.getLogger(LateCouponService.class);
	/**
	 * 晚到补差价Dao
	 */
	private ILateCouponDao lateCouponDao;
    /**
   	 * 产品Service，用于封装前台产品多选选择器的赋值参数
   	 */
    private IProductService bseProductService;
    /**
     * 部门Service，用于封装前台出发、到达大区多选选择器的赋值参数
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 员工Service，用于查询创建人姓名
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 用于向上查事业部
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	
    public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}



	public void setLateCouponDao(ILateCouponDao lateCouponDao) {
		this.lateCouponDao = lateCouponDao;
	}



	public void setBseProductService(IProductService bseProductService) {
		this.bseProductService = bseProductService;
	}



	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/** 
     *  分页查询晚到补差价方案
     * @author 232607 
     * @date 2015-7-30 上午9:38:45
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#queryLateCouponByCodition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity, int, int)
     */
    @Override
	public List<LateCouponEntity> queryLateCouponByCodition(LateCouponEntity entity, int start, int limit) {
		//解决多选公共选择器传至后台的产品类型编码中存在空格问题（如FLF, FSF）
		if(StringUtils.isNotEmpty(entity.getProductItem())){
			entity.setProductItem(entity.getProductItem().replaceAll(" ",""));
		}
		//查询出想要的实体集合
		List<LateCouponEntity> entitys = lateCouponDao.queryLateCouponByCodition(entity, start, limit);
		//对集合中的每一个实体进行4项封装
		for(LateCouponEntity entity2:entitys){
			//1.封装前台出发大区多选选择器的赋值条件（实体集合和编码集合）
			List<OrgAdministrativeInfoEntity> startEntitys=new ArrayList<OrgAdministrativeInfoEntity>();
			String strStart=entity2.getStartBigzone();
			if(StringUtil.isNotEmpty(strStart)){
				String[] arrayStart=strStart.split(",");
				List<String> listStart=Arrays.asList(arrayStart);
				//将大区编码集合保存到实体中
				entity2.setStartBigzoneCodes(listStart);
				//遍历每一个编码，调用部门service找出对应的实体，存入预先创建好的集合中
				for(String code:listStart){
					OrgAdministrativeInfoEntity startEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
					startEntitys.add(startEntity);
				}
				//将实体集合保存到实体中
				entity2.setStartBigzoneEntitys(startEntitys);
			}
			//2.封装前台到达大区多选选择器的赋值条件（实体集合和编码集合）
			List<OrgAdministrativeInfoEntity> arriveEntitys=new ArrayList<OrgAdministrativeInfoEntity>();
			String strArrive=entity2.getArriveBigzone();
			if(StringUtil.isNotEmpty(strArrive)){
				String[] arrayArrive=strArrive.split(",");
				List<String> listArrive=Arrays.asList(arrayArrive);
				//将大区编码集合保存到实体中
				entity2.setArriveBigzoneCodes(listArrive);
				//遍历每一个编码，调用产品service找出对应的实体，存入预先创建好的集合中
				for(String code:listArrive){
					OrgAdministrativeInfoEntity arriveEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
					arriveEntitys.add(arriveEntity);
				}
				//将实体集合保存到实体中
				entity2.setArriveBigzoneEntitys(arriveEntitys);
			}
			//3.封装前台产品类型多选选择器的赋值条件（实体集合和编码集合）
			ProductEntity productEntity =new ProductEntity();
			List<ProductEntity> productEntitys=new ArrayList<ProductEntity>();
			String strProduct=entity2.getProductItem();
			if(StringUtil.isNotEmpty(strProduct)){
				String[] arrayProduct=strProduct.split(",");
				List<String> listProduct=Arrays.asList(arrayProduct);
				//将产品编码集合保存到实体中
				entity2.setProductCodes(listProduct);
				//遍历每一个编码，调用产品service找出对应的产品实体，存入预先创建好的集合中
				for(String code:listProduct){
					productEntity.setCode(code);
					productEntity.setActive("Y");
					List<ProductEntity> productEntityList=bseProductService.findProductByCondition(productEntity);
					if(productEntityList.size()>0){
						productEntitys.add(productEntityList.get(0));
					}
				}
				//将产品实体集合保存到实体中
				entity2.setProductEntitys(productEntitys);
			}
			//4.封装创建人姓名
			if(StringUtils.isNotEmpty(entity2.getCreateUser())){
				String createUserName=employeeService.queryEmpNameByEmpCode(entity2.getCreateUser());
				entity2.setCreateUserName(createUserName);
			}
		}
		return entitys;
	}
	
	/** 
	 * 计数——分页查询晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:15:50
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#countLateCouponByCodition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity)
	 */
	@Override
	public Long countLateCouponByCodition(LateCouponEntity entity) {
		return lateCouponDao.countLateCouponByCodition(entity);
	}
	

	/** 
	 * 新增晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:16:32
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#addLateCoupon(com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity)
	 */
	@Override
	public LateCouponEntity addLateCoupon(LateCouponEntity entity) {
		//查重
		queryRepeat(entity);
		//自动生成编码
		String code=generateProgramCode();
		entity.setCode(code);
		return lateCouponDao.addLateCoupon(entity);
	}
	
	/** 
	 * 作废晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:16:59
	 * @param lateCouponIds 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#deleteLateCoupon(java.util.List)
	 */
	@Override
	public void deleteLateCoupon(List<String> lateCouponIds) {
		//条件的非空判断，为空则抛出异常
		if (null==lateCouponIds || lateCouponIds.size()==0 ) {
			throw new BusinessException("传入参数为空");
		}
		//调用Dao层的方法，批量作废
		lateCouponDao.deleteLateCoupon(lateCouponIds);
	}
	
	/** 
	 * 更新晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:17:21
	 * @param entity 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#updateLateCoupon(com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity)
	 */
	@Override
	public void updateLateCoupon(LateCouponEntity entity) {
		//查重
		queryRepeat(entity);
		List<String> ids=new ArrayList<String>();
		ids.add(entity.getId());
		lateCouponDao.deleteLateCoupon(ids);
		lateCouponDao.addLateCoupon(entity);
	}
	/**
	 * <p>查重，只要存在相同的名字就算重复，重复则抛出异常</p>
	 * @author 232607 
	 * @date 2015-5-22 下午2:18:47
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public void queryRepeat(LateCouponEntity entity){
		//调用Dao层的查询重复的方法，返回一个集合
		List<LateCouponEntity> list=lateCouponDao.queryRepeat(entity);
		//如果集合不是空的，代表存在重复
		if(list.size()>0){
			//抛出异常：已经存在相同的虚拟营业部，请勿重复添加
			throw new BusinessException("方案名称重复，请重新输入名称");
		}
	}
	
	/**
	 *  给接送货pkp的接口，返回当前唯一激活有效的方案信息，并且经过处理，
	 *  封装发货人大区和收货人大区下属的所有营业部及集中开单组存入实体
	 * @author 232607 
	 * @date 2015-7-17 下午4:30:28
	 * @return
	 * @see
	 */
	@Override
	public LateCouponEntity returnSchemeToPKP() {
		//查询出当前唯一激活方案
		List<LateCouponEntity> list=lateCouponDao.queryActivationScheme();
		if(list.size()>0){
			LateCouponEntity entity=list.get(0);
			//封装出发大区下属营业部、集中开单组
			String startBigzone=entity.getStartBigzone();
			if(startBigzone!=null && startBigzone.length()>0){
				String[] startArray=startBigzone.split(",");
				List<String> startList=Arrays.asList(startArray);
				List<String> startSalesDept=lateCouponDao.querySubSalesDept(startList);		
				entity.setStartSalesDept(startSalesDept);
			}
			//封装到达大区下属营业部、集中开单组
			String arriveBigzone=entity.getArriveBigzone();
			if(arriveBigzone!=null && arriveBigzone.length()>0){
				String[] arriveArray=arriveBigzone.split(",");
				List<String> arriveList=Arrays.asList(arriveArray);
				List<String> arriveSalesDept=lateCouponDao.querySubSalesDept(arriveList);
				entity.setArriveSalesDept(arriveSalesDept);
			}
			//将产品类型字符串转换成集合，并存入实体
			String strProduct=entity.getProductItem();
			String[] arrayProduct=strProduct.split(",");
			List<String> listProduct=Arrays.asList(arrayProduct);
			entity.setProductCodes(listProduct);
			return entity;
		}else{
			return null;
		}
	}
	
	
	/**
	 * <p>给接送货pkp的接口，传入出发、到达营业部、产品编码，营业部向上查询事业部进行判断，
	 * 		  判断包含成功再返回其他判断条件给接送货进行判断，判断失败或无方案均返回null</p> 
	 * @author 232607 
	 * @date 2016-4-11 下午2:38:13
	 * @param startOrgcode
	 * @param destOrgcode
	 * @return
	 * @see
	 */
	@Override
	public LateCouponEntity returnSchemeToPKPByOrg(String startOrgcode,String arriveOrgcode,String productCode) {
		List<LateCouponEntity> list=lateCouponDao.queryActivationScheme();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
//		List<String> bizTypes = new ArrayList<String>();
//		//查询上级事业部
//		bizTypes.add(BizTypeConstants.ORG_DIVISION);
		OrgAdministrativeInfoEntity startDivision =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(startOrgcode);
		OrgAdministrativeInfoEntity arriveDivision =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(arriveOrgcode);
		boolean startFlag=false;
		boolean arriveFlag=false;
		boolean productFlag=false;
		LateCouponEntity object=null;
		for(LateCouponEntity entity:list){
			String startBigzone=entity.getStartBigzone();
			//不为空切不包含则返回null，为空代表全部事业部，算判断通过
			if(StringUtils.isNotEmpty(startBigzone)){
				String[] startArray=startBigzone.split(",");
				List<String> startList=Arrays.asList(startArray);
				if(!startList.contains(startDivision.getCityCode())){
					startFlag=false;
				}else{
					startFlag=true;
					
				}
				
			}
			
			String arriveBigzone=entity.getArriveBigzone();
			if(StringUtils.isNotEmpty(arriveBigzone)){
				String[] arriveArray=arriveBigzone.split(",");
				List<String> arriveList=Arrays.asList(arriveArray);
				if(!arriveList.contains(arriveDivision.getCityCode())){
					arriveFlag=false;
				}else{
					arriveFlag=true;
				}
			}
			String strProduct=entity.getProductItem();
			//产品为空，判断不通过
			if(StringUtils.isNotEmpty(strProduct)){
			String[] arrayProduct=strProduct.split(",");
			List<String> listProduct=Arrays.asList(arrayProduct);
			if(!listProduct.contains(productCode)){
				productFlag=false;
			}else{
				productFlag=true;
			}
			}
			if(startFlag&&arriveFlag&&productFlag){
				object=entity;
				break;
			}
		}
		return object;
	}
	/** 
	 * <p>升级版本，实际为复制一个未激活方案，除ID外什么都不变，编码和名称也不变</p> 
	 * @author 232607 
	 * @date 2015-8-4 下午5:18:11
	 * @param id 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#copyLateCoupon(java.lang.String)
	 */
	@Override
	public void copyLateCoupon(String id) {
		LateCouponEntity entity=new LateCouponEntity();
		entity.setId(id);
		List<LateCouponEntity> entitys = lateCouponDao.queryLateCouponByCodition(entity, 0, 1);
		entity=entitys.get(0);
		entity.setId(UUIDUtils.getUUID());
		lateCouponDao.addLateCoupon(entity);
	}

	/** 
	 * 激活方案 
	 * @author 232607 
	 * @date 2015-8-1 下午4:05:36
	 * @param id 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#activateLateCoupon(java.lang.String)
	 */
	@Override
	public void activateLateCoupon(String id) {
		LateCouponEntity entity=this.findById(id);
		if(entity==null||entity.getBeginTime()==null){
			throw new BusinessException("激活方案有错误。");
		}
//		//检查当前是否有有效方案
//		List<LateCouponEntity> list=lateCouponDao.findActivationScheme(entity);
//		//如果存在有效方案，则不允许激活
//		if(list.size()>0){
//			throw new BusinessException("存在已激活方案，不允许激活，请先中止已激活方案。");
//		}
		lateCouponDao.activateLateCoupon(id);
	}





	/** 
	 * 中止方案 
	 * @author 232607 
	 * @date 2015-8-1 下午4:05:51
	 * @param id 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService#stopLateCoupon(java.lang.String)
	 */
	@Override
	public void stopLateCoupon(String id) {
		lateCouponDao.stopLateCoupon(id);
	}

	/**
	 * 自动生成编码
	 * @author 232607 
	 * @date 2015-8-1 下午4:09:21
	 * @return
	 * @see
	 */
	public String generateProgramCode() {
		String maxCode = lateCouponDao.getMaxCode();
		String code = null;
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH)+1;
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		String currentYearStr =  String.valueOf(currentYear);
		String currentMonthStr = String.format("%02d", currentMonth);
		String currentDayStr = String.format("%02d", currentDay);
		if(StringUtil.isNotBlank(maxCode)) {
			String year = maxCode.substring(NumberConstants.NUMBER_4, NumberConstants.NUMBER_8);
			String month = maxCode.substring(NumberConstants.NUMBER_8, NumberConstants.NUMBER_10);
			String day = maxCode.substring(NumberConstants.NUMBER_10, NumberConstants.NUMBER_12);
			String seq = maxCode.substring(NumberConstants.NUMBER_12, NumberConstants.NUMBER_15);
			if(StringUtil.equals(year, currentYearStr) && StringUtil.equals(month, currentMonthStr) && StringUtil.equals(day, currentDayStr)) {
				code = "FQFA" + year + month + day;
				int num = Integer.parseInt(seq);
				num = num + 1;
				code = code + String.format("%03d", num);
			} else {
				code = "FQFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
			}
		} else {
			code = "FQFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
		}
		return code;
	}



	/**
	 * 根据Id查询详情
	 *273296
	 * @param id
	 * @return
	 */
	@Override
	public  LateCouponEntity findById(String id) {
		return lateCouponDao.queryLateCouponById(id);
	}




	
}