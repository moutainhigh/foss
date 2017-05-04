package com.deppon.foss.module.settlement.closing.api.shared.dto;


import java.util.List;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;

/**
 * @author  302307
 * @date 2015年12月15日11:30:08.
 */
public class MvrHiDto extends MvrHiEntity {

    /** 序列化Id*/
	private static final long serialVersionUID = 1L;

	/** 总条数. */
    private int count;

    /** 用户编码-设置用户数据查询权限. */
    private String userCode;

    /** 查询结果列表. */
    private List<MvrHiEntity> queryList;

    /**
     * Gets the count.
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count.
     *
     * @param count the new count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the query list.
     *
     * @return queryList
     */
    public List<MvrHiEntity> getQueryList() {
        return queryList;
    }

    /**
     * Sets the query list.
     *
     * @param queryList the new query list
     */
    public void setQueryList(List<MvrHiEntity> queryList) {
        this.queryList = queryList;
    }

    /**
     * @return userCode
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * @param userCode
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
