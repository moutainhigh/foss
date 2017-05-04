package com.deppon.foss.module.pickup.pricing.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionEcGoodsVo;

import java.util.Date;
import java.util.List;

public class RegionEcGoodsAction extends AbstractAction {
    /**
     * 注入service
     */
    private IRegionEcGoodsService regionEcGoodsService;

    /**
     * 区域Vo页面传参对象
     */
    private RegionEcGoodsVo regionEcVo = new RegionEcGoodsVo();

    public RegionEcGoodsVo getRegionEcVo() {
        return regionEcVo;
    }

    public void setRegionEcVo(RegionEcGoodsVo regionEcVo) {
        this.regionEcVo = regionEcVo;
    }

    public void setRegionEcGoodsService(
            IRegionEcGoodsService regionEcGoodsService) {
        this.regionEcGoodsService = regionEcGoodsService;
    }

    /**
     * searchRegionByCondition:查询所有的区域. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417 wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String searchRegionEcGoodsByCondition() {
        try {
            PriceRegionEcGoodsEntity regionEntity = regionEcVo.getRegionEntity();
            List<PriceRegionEcGoodsEntity> regionEntityList = regionEcGoodsService.searchRegionByCondition(regionEntity, this.getStart(), this.getLimit());
            regionEcVo.setRegionEntityList(regionEntityList);
            Long totalCount = regionEcGoodsService.countRegionByCondition(regionEntity);
            this.setTotalCount(totalCount);
            return returnSuccess();
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * activeRegions:激活区域. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417    wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String activeRegionsEcGoods() {
        try {
            Date dd = new Date();
            if (dd != null && regionEcVo.getBeginTime() != null && dd.compareTo(regionEcVo.getBeginTime()) > 0) {
                return returnError(MessageType.SHOW_FAILURE_MESSAGE);
            }
            regionEcGoodsService.immedietelyActiveRegion(regionEcVo.getRegionEntity().getId(), regionEcVo.getRegionNature(), regionEcVo.getBeginTime());
            return returnSuccess(MessageType.ACTIVE_SUCCESS);
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * deleteRegions:删除区域. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417 wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String deleteRegionsEcGoods() {
        try {
            regionEcGoodsService.deleteRegion(regionEcVo.getRegionIds(), regionEcVo.getRegionNature());
            return returnSuccess(MessageType.DELETE_SUCCESS);
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * addRegion:新增区域. <br/>
     *
     * Date:2016.06.29
     *
     * @return
     * @author 311417 wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String addRegionEcGoods() {
        try {
            regionEcGoodsService.addRegion(regionEcVo.getRegionEntity(), regionEcVo.getAddPriceRegioOrgnEntityList());
            return returnSuccess(MessageType.SAVE_SUCCESS);
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * updateRegion:修改区域. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417    wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String updateRegionEcGoods() {
        try {
            regionEcGoodsService.updateRegion(regionEcVo.getRegionEntity(), regionEcVo.getAddPriceRegioOrgnEntityList(), regionEcVo.getUpdatePriceRegioOrgnEntityList());
            return returnSuccess(MessageType.UPDATE_SUCCESS);
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * immedietelyStopRegion:立即终止. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417    wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String immedietelyStopRegionEcGoods() {
        try {
            //执行中止操作
            regionEcGoodsService.immedietelyStopRegion(regionEcVo.getRegionEntity().getId(), regionEcVo.getRegionNature(), regionEcVo.getEndTime());
            //返回状态值
            return returnSuccess(MessageType.STOP_SUCCESS);
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * searchRegionByID:查询区域. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417-wangfeng
     * @since JDK 1.6
     */
    @JSON
    public String searchRegionEcGoodsByID() {
        try {
            PriceRegionEcGoodsEntity priceRegionEcGoodsEntity = regionEcGoodsService.
                    searchRegionByID(regionEcVo.getRegionEntity().getId(), regionEcVo.getRegionEntity().getRegionNature());
            regionEcVo.setRegionEntity(priceRegionEcGoodsEntity);
            return returnSuccess();
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

    /**
     * @return
     */
    @JSON
    public String searchRegionEcGoodsDept() {
        try {
            List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsService
                    .searchRegionOrgByCondition(regionEcVo
                            .getPriceRegioOrgnEntity());
            regionEcVo.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
            return returnSuccess();
        } catch (BusinessException e) {
            return returnError(e);
        }
    }

}
