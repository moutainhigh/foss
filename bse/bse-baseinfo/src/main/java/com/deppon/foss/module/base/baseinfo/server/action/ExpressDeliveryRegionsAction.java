package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryRegionsException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryRegionsVo;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 快递派送区域基础资料Action
 * 
 * @author 130566
 * @Date 2014-01-16
 */
public class ExpressDeliveryRegionsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/**
	 * 日志监控
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpressDeliveryRegionsAction.class);
	/**
	 * 声明定义
	 */
	private IExpressDeliveryRegionsService expressDeliveryRegionsService;
	/**
	 * 数据字典Service
	 */
	private IDataDictionaryValueService dataDictionaryValueService;

	private ExpressDeliveryRegionsVo vo=new ExpressDeliveryRegionsVo();

	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	public ExpressDeliveryRegionsVo getVo() {
		return vo;
	}

	public void setVo(ExpressDeliveryRegionsVo vo) {
		this.vo = vo;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setExpressDeliveryRegionsService(
			IExpressDeliveryRegionsService expressDeliveryRegionsService) {
		this.expressDeliveryRegionsService = expressDeliveryRegionsService;
	}

	/**
	 * @param dataDictionaryValueService the dataDictionaryValueService to set
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * <P>
	 * 根据上级查询下级区域
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午10:36:41
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryExpressDeliveryRegionsByParentDistrictCode() {
		try {
			// 若父级编码为空
			if (null == vo.getExpressDeliveryRegionsEntity()
					|| StringUtils.isBlank(vo.getExpressDeliveryRegionsEntity()
							.getParentDistrictCode())) {
				// 若上级编码为空，返回根节点
				vo.setExpressDeliveryRegionsList(expressDeliveryRegionsService
						.queryRoot());
				return returnSuccess();
			}
			String parentCode = vo.getExpressDeliveryRegionsEntity()
					.getParentDistrictCode();
			// 定义树集合对象
			nodes = new ArrayList<TreeNode>();
			// 根据父级编码查询节点
			List<ExpressDeliveryRegionsEntity> list = expressDeliveryRegionsService
					.queryByParentDistrictCode(parentCode);
			if (CollectionUtils.isNotEmpty(list)) {
				// 循环行政区域信息
				for (ExpressDeliveryRegionsEntity pojo : list) {
					// 定义树节点对象
					TreeNode<ExpressDeliveryRegionsEntity, TreeNode> treeNode = new TreeNode<ExpressDeliveryRegionsEntity, TreeNode>();
					// 设置节点信息
					treeNode.setId(pojo.getCode());
					treeNode.setText(pojo.getName());
					// 若为行政级别为村路的话，则设为叶子节点
					treeNode.setLeaf(StringUtils.equals(pojo.getDegree(),
							DictionaryValueConstants.VILLAGE_ROAD));
					// 判断上级行政编码是否为空
					if (StringUtils.isBlank(pojo.getParentDistrictCode())) {
						treeNode.setParentId(null);
					} else {
						treeNode.setParentId(pojo.getParentDistrictCode());
					}
					treeNode.setEntity(pojo);
					// 讲节点信息添加到树种
					nodes.add(treeNode);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * <P>
	 * 根据code 进行查询
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18下午1:54:47
	 * @return
	 */
	@JSON
	public String queryExpressDeliveryRegionsByCode() {
		try {
			String code = vo.getExpressDeliveryRegionsEntity().getCode();
			// 查询实体
			ExpressDeliveryRegionsEntity entity = expressDeliveryRegionsService
					.queryExpressDeliveryRegionsByCode(code);
			if(entity!=null){
				//判断该code节点是否叶子节点--解决从行政区域同步独立省市区县过来产生派送属性不可编辑问题--187862-dujunhui
				String childCode=expressDeliveryRegionsService.queryMaxCodeChildRegions(code);
				if(StringUtil.isEmpty(childCode)){
					entity.setIsLeaf("Y");
				}else{
					entity.setIsLeaf("N");
				}
				
				//三级地址派件区域最后添加分号，是为了好截取分号后面手动输入的内容 ,跟需求约定好分号后是手动修改内容 pgy
				if("DISTRICT_COUNTY".equals(entity.getDegree())){
					entity.setRemark(entity.getRemark()+";");
				}
				// 设置给前台
				vo.setExpressDeliveryRegionsEntity(entity);
			}
			
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * <P>
	 * 分页查询
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-19下午3:12:31
	 * @return
	 */
	@JSON
	public String queryExpressDeliveryRegionsList() {
		ExpressDeliveryRegionsEntity entity = null;
		try {
			// 若vo 或者实体为空
			if (null == vo || null == vo.getExpressDeliveryRegionsEntity()) {
				entity = new ExpressDeliveryRegionsEntity();
			} else if (null != vo){
				entity = vo.getExpressDeliveryRegionsEntity();
			}
			
			//分页查询
			List<ExpressDeliveryRegionsEntity> list = expressDeliveryRegionsService
					.queryExpressDeliveryRegionsEntities(entity, this.start,
							this.limit);
			//313353 2016/09/12 sonar优化  start
			if (null != vo){
				vo.setExpressDeliveryRegionsList(list);
			}
			//313353 2016/09/12 sonar优化  end
			
			//查询count 
			totalCount =expressDeliveryRegionsService.queryCount(entity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 *<P>根据父节点查询子节点中，code最大的<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:03:59
	 * @return
	 */
	@JSON
	public String queryMaxChildRegionsCode(){
		try {
			String parentDistrictCode =vo.getExpressDeliveryRegionsEntity().getParentDistrictCode();
			//新建对象
			ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity =new ExpressDeliveryRegionsEntity();
			//设置值
			expressDeliveryRegionsEntity.setCode(expressDeliveryRegionsService.queryMaxCodeChildRegions(parentDistrictCode));
			vo.setExpressDeliveryRegionsEntity(expressDeliveryRegionsEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>添加区域（可废弃）<P>-AC项目需求：快递派送区域界面无新增删除操作，只可在行政区域新增作废
	 * @author :130566-zengJunfan
	 * @date : 2014-2-25上午8:57:43
	 * @return
	 */
	@JSON
	public String addExpressDeliveryRegions(){
		try {
			ExpressDeliveryRegionsEntity entity =vo.getExpressDeliveryRegionsEntity();
			CurrentInfo info =FossUserContext.getCurrentInfo();
			//设置创建人
			entity.setCreateUser(info.getEmpCode());
			entity.setModifyUser(info.getEmpCode());
			//添加对象
			expressDeliveryRegionsService.addExpressDeliveryRegions(entity);
			
			return returnSuccess(SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>修改区域<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-25下午3:56:35
	 * @return
	 */
	@JSON
	public String updateExpressDeliveryRegions(){
		try {
			ExpressDeliveryRegionsEntity entity =vo.getExpressDeliveryRegionsEntity();
			//获取当前登陆人
			CurrentInfo info =FossUserContext.getCurrentInfo();
			entity.setModifyUser(info.getEmpCode());
			//修改区域操作
			expressDeliveryRegionsService.updateExpressDeliveryRegions(entity);
			
			return returnSuccess(SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>作废区域（可废弃）-AC项目需求：快递派送区域界面无新增删除操作，只可在行政区域界面新增删除<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午8:55:35
	 * @return
	 */
	@JSON
	public String deleteExpressDeliveryRegions(){
		try {
			ExpressDeliveryRegionsEntity entity =vo.getExpressDeliveryRegionsEntity();
			List<String> codes =new ArrayList<String>();
 			//由于快递派送区域界面只能删除两个级别,所以只要判断删除的是否镇、村级别
			if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.VILLAGE_ROAD)){
				codes.add(entity.getCode());
			}else if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){
				//查询上级为该节点的下级组织
				List<ExpressDeliveryRegionsEntity> results =expressDeliveryRegionsService.queryByParentDistrictCode(entity.getCode());
				codes.add(entity.getCode());
				if(CollectionUtils.isNotEmpty(results)){
					for (ExpressDeliveryRegionsEntity result : results) {
						codes.add(result.getCode());
					}
				}
			}
			//获取作废人
			CurrentInfo info =FossUserContext.getCurrentInfo();
			//转换成数组
			String[] arrCodes =(String[])codes.toArray(new String[codes.size()]);
			
			//执行作废操作
			expressDeliveryRegionsService.deleteRegionsByCodes(arrCodes,info.getEmpCode());
			
			return returnSuccess(SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>批量作废（可废弃）-AC项目需求：快递派送区域界面无新增删除操作，只可在行政区域界面新增删除<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-27下午4:04:48
	 * @return
	 */
	@JSON
	public String deleteMoreExpressDeliveryRegions(){
		try {
			//获取
			String[] codes=vo.getCodes();
			//获取作废人
			CurrentInfo info =FossUserContext.getCurrentInfo();
			
			//执行作废
			expressDeliveryRegionsService.deleteRegionsByCodes(codes,info.getEmpCode());
			
			return returnSuccess(SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 * <P>根据编码查询自身及下级节点<P>
	 * @author: 187862-dujunhui
	 * @date: 2015-4-2下午2:17:34
	 * @return
	 */
	@JSON
	public String querySelfAndChildNodesByCode(){
		try{
			// 若父级编码为空
			if (null == vo|| null == vo.getExpressDeliveryRegionsEntity()) {
				throw new ExpressDeliveryRegionsException("传入的查询实体为空！");
			}
			//根据父级编码查询下级节点
			List<ExpressDeliveryRegionsEntity> list = expressDeliveryRegionsService
					.queryByParentDistrictCode(vo.getExpressDeliveryRegionsEntity().getCode());
			//查询自身节点
			ExpressDeliveryRegionsEntity entity=expressDeliveryRegionsService.
					queryExpressDeliveryRegionsByCode(vo.getExpressDeliveryRegionsEntity().getCode());
			//封装VO
			vo.setExpressDeliveryRegionsList(list);//VO中添加下级节点
			//封装自身节点派送属性名称以备查看
			DataDictionaryValueEntity valueEntity=dataDictionaryValueService.
					queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.
							DELIVERY_NATURE, entity.getDeliveryNature());//查询数据字典值名称
			if(valueEntity!=null){
				entity.setDeliveryNatureName(valueEntity.getValueName());
			}
			//拼接行政级别上下级名称
			if(StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){//点击镇街道级别
				ExpressDeliveryRegionsEntity countyEntity = expressDeliveryRegionsService
						.queryExpressDeliveryRegionsByCode(entity.getParentDistrictCode());
				if(countyEntity==null){
					throw new ExpressDeliveryRegionsException("区县级别为空");
				}
				ExpressDeliveryRegionsEntity cityEntity = expressDeliveryRegionsService
						.queryExpressDeliveryRegionsByCode(countyEntity.getParentDistrictCode());
				if(cityEntity==null){
					throw new ExpressDeliveryRegionsException("市级别为空");
				}
				ExpressDeliveryRegionsEntity provEntity = expressDeliveryRegionsService
						.queryExpressDeliveryRegionsByCode(cityEntity.getParentDistrictCode());
				if(provEntity==null){
					throw new ExpressDeliveryRegionsException("省级别为空");
				}
				entity.setDistrictAddress(provEntity.getName()+cityEntity.getName()+countyEntity.getName()+entity.getName());
			}
			vo.setExpressDeliveryRegionsEntity(entity);//VO中添加自身节点
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * <P>根据根据GIS地图修改快递派送区域信息<P>
	 * @author: 187862-dujunhui
	 * @date: 2015-4-2下午2:17:34
	 * @return
	 */
	@JSON
	public String updateExpressDeliveryRegionByGisMap(){
		try{
			//从前台获取实体，包含code、gisid、nature属性
			ExpressDeliveryRegionsEntity updateEntity=vo.getExpressDeliveryRegionsEntity();
			//根据code查询数据库实体信息
			ExpressDeliveryRegionsEntity bseEntity=expressDeliveryRegionsService.queryExpressDeliveryRegionsByCode(updateEntity.getCode());
			//将updateEntity信息填入到bseEntity中
			bseEntity.setExpressProxyCoordinate(updateEntity.getExpressProxyCoordinate());
			bseEntity.setExpressProxyDeliveryNature(updateEntity.getExpressProxyDeliveryNature());
			//获取当前登陆人
			CurrentInfo info =FossUserContext.getCurrentInfo();
			bseEntity.setModifyUser(info.getEmpCode());
			expressDeliveryRegionsService.updateExpressDeliveryRegions(bseEntity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
}
