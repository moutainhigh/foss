package com.deppon.foss.module.transfer.unload.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.ISubForkAreaGoodsQueryDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ISubForkAreaGoodsQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SubForkAreaGoodsVo;
import com.deppon.foss.util.define.FossConstants;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SubForkAreaGoodsQueryService implements ISubForkAreaGoodsQueryService {


    private static Logger LOGGER = LoggerFactory.getLogger(SubForkAreaGoodsQueryService.class);

    /**
     * 产品Service
     */
    @Resource
    private IProductService productService4Dubbo;

    /**
     * 查询dao
     **/
    private ISubForkAreaGoodsQueryDao subForkAreaGoodsQueryDao;
    /**
     * 查询组织编码
     **/
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 获取当前部门编号
     *
     * @return String
     * @author zenghaibin
     * @date 2014-7-7 下午4:50:53
     */
    public String queryOrgCode() {
        String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
        OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(orgCode);
        if (null != org) {
            //校验是否是个营业部
            if (StringUtils.isNotBlank(org.getSalesDepartment()) && StringUtils.equals(org.getSalesDepartment(), FossConstants.YES) ||
                    (StringUtils.isNotBlank(org.getTransferCenter()) && StringUtils.equals(org.getTransferCenter(), FossConstants.YES))) {
                return orgCode;
                //校验是否是个外场
            }/*else if(StringUtils.isNotBlank(org.getTransferCenter())&&StringUtils.equals(org.getTransferCenter(), FossConstants.YES)){
                return orgCode;
			}*/ else {
                List<String> bizTypes = new ArrayList<String>();
                bizTypes.add("TRANSFER_CENTER");
                //查询所属上级部门是否是个外场
                OrgAdministrativeInfoEntity superOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org.getCode(), bizTypes);
                //若上级外场存在， 则返回上级外场，若不存在，则返回用户数据权限
                if (null != superOrg) {
                    return superOrg.getCode();//
                } else {
                    //默认为专业部门
                    return orgCode;
                }
            }
        }
        return null;
    }

    /**
     * 查询三级产品列表
     *
     * @author 097457-foss-wangqiang
     * @date 2012-11-16 下午2:37:08
     * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryProductList()
     */
    @Override
    public List<BaseDataDictDto> queryProductList() {
        List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();

        ProductDto productDto = new ProductDto();
        productDto.setLevels(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
        List<ProductEntity> productList = productService4Dubbo.findExternalProductByCondition(productDto, null);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("SubForkAreaGoodsQueryService调用计价dubbo接口成功，productList = {}," +
                    "productService4Dubbo={}", productList, productService4Dubbo);
        }
        if (CollectionUtils.isNotEmpty(productList)) {
            for (ProductEntity product : productList) {
                BaseDataDictDto dict = new BaseDataDictDto();
                dict.setValueName(product.getName());
                dict.setValueCode(product.getCode());
                list.add(dict);
            }
        }
        return list;
    }


    /**
     * 查询运输性质列表   零担
     *
     * @param subForkAreaGoodsVo 查询条件参数
     * @return 返回一个参数列别List<SubForkAreaGoodsEntity> 待叉区货物数据
     * @author zenghaibin
     * @date 2014-07-037 上午9:35:34
     */
    @Override
    public List<SubForkAreaGoodsEntity> querySubForkAreaGoods(SubForkAreaGoodsVo subForkAreaGoodsVo, int limit, int start) {
        List subForkAreaGoodsEntityList = new ArrayList<SubForkAreaGoodsEntity>();
        subForkAreaGoodsVo.setOrgCode(this.queryOrgCode());
        subForkAreaGoodsEntityList = subForkAreaGoodsQueryDao.querySubForkAreaGoods(subForkAreaGoodsVo, limit, start);
        return subForkAreaGoodsEntityList;
    }

    /**
     * 查询运输性质列表  快递
     *
     * @param subForkAreaGoodsVo 查询条件参数
     * @return 返回一个参数列别List<SubForkAreaGoodsEntity> 待叉区货物数据
     * @author zenghaibin
     * @date 2014-07-037 上午9:35:34
     */
    @Override
    public List<SubForkAreaGoodsExpressEntity> querySubForkAreaGoodsExpress(SubForkAreaGoodsVo subForkAreaGoodsVo, int limit, int start) {
        //定义返回的实体类
        List<SubForkAreaGoodsExpressEntity> subForkAreaGoodsEntityList = new ArrayList<SubForkAreaGoodsExpressEntity>();
        //定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取JSON字符串数据
        String resultData = subForkAreaGoodsQueryDao.querySubForkAreaGoodsExpress(subForkAreaGoodsVo, limit, start);
        try {
            int a = 0;
            if (!"".equals(resultData)) {
                //数据转化成JSON
                JSONObject resultJson = JSONObject.fromObject(resultData);
                //判断返回的数据是否为空
                if (!"".equals(resultJson.getString("data")) && !"null".equals(resultJson.getString("data"))) {
                    for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
                        SubForkAreaGoodsExpressEntity subForkAreaGoods = new SubForkAreaGoodsExpressEntity();
                        /**运单/包/笼号**/
                        subForkAreaGoods.setWaybillNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("wayBillNo")));
                        /**开单件数**/
                        a = (StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("totalPiece"), "null") ? 0 : resultJson.getJSONArray("data").getJSONObject(i).getInt("totalPiece"));
                        subForkAreaGoods.setBillingNum(a);
                        /**已绑定件数**/
                        a = (StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("bindedPiece"), "null") ? 0 : resultJson.getJSONArray("data").getJSONObject(i).getInt("bindedPiece"));
                        subForkAreaGoods.setBindNum(a);
                        /**未扫描件数**/
                        a = (StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("unScanPiece"), "null") ? 0 : resultJson.getJSONArray("data").getJSONObject(i).getInt("unScanPiece"));
                        subForkAreaGoods.setUnScanNum(a);
                        /**运输性质**/
                        subForkAreaGoods.setTransType(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("transType")));
                        /**下一目的站**/
                        subForkAreaGoods.setNextDestination(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("nextDestination")));
                        /**创建人**/
                        subForkAreaGoods.setCreatePerson(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("createName")));
                        /**创建人工号**/
                        subForkAreaGoods.setCreatePersonCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("createNo")));
                        /**扫描时间**/
                        Date date = (StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanTime"), "null") ? null : sdf.parse(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanTime")));
                        subForkAreaGoods.setScanTime(date);
                        /**卸车任务号**/
                        subForkAreaGoods.setUnloadTaskNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("unloadTaskNo")));
                        //数据总数
                        subForkAreaGoods.setCount(resultJson.getLong("totalRows"));
                        subForkAreaGoodsEntityList.add(subForkAreaGoods);
                    }
                    return subForkAreaGoodsEntityList;
                }
            }
        } catch (ParseException e) {
            System.out.println("数据转化失败:" + e.getMessage());
        }
        return subForkAreaGoodsEntityList;
    }

    //把"null"转换成“”
    public String isNotNull(String sta) {
        if ("null".equals(sta)) {
            return "";
        }
        return sta;
    }

    /**
     * 分页  零担
     *
     * @return 数据量
     * @author zenghaibin
     **/
    @Override
    public Long querySubForkAreaGoodsCount(SubForkAreaGoodsVo subForkAreaGoodsVo) {
        subForkAreaGoodsVo.setOrgCode(this.queryOrgCode());
        return subForkAreaGoodsQueryDao.querySubForkAreaGoodsCount(subForkAreaGoodsVo);
    }

    /**
     * 分页  快递
     *
     * @return 数据量
     * @author zenghaibin
     **/
    @Override
    public Long querySubForkAreaGoodsCountExpress(SubForkAreaGoodsVo subForkAreaGoodsVo) {
        subForkAreaGoodsVo.setOrgCode(this.queryOrgCode());
        return subForkAreaGoodsQueryDao.querySubForkAreaGoodsCountExpress(subForkAreaGoodsVo);
    }

//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}


    public void setSubForkAreaGoodsQueryDao(
            ISubForkAreaGoodsQueryDao subForkAreaGoodsQueryDao) {
        this.subForkAreaGoodsQueryDao = subForkAreaGoodsQueryDao;
    }

    public void setOrgAdministrativeInfoComplexService(
            IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

}
