package cn.onesdream.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ad_promotion
 * @author 
 */
@Data
public class AdPromotion implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * appId（来源于：app_info表的主键id）
     */
    private Long appId;

    /**
     * 广告图片存储路径
     */
    private String adPicPath;

    /**
     * 广告点击量
     */
    private Long adPV;

    /**
     * 轮播位（1-n）
     */
    private Integer carouselPosition;

    /**
     * 起效时间
     */
    private Date startTime;

    /**
     * 失效时间
     */
    private Date endTime;

    /**
     * 创建者（来源于backend_user用户表的用户id）
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新者（来源于backend_user用户表的用户id）
     */
    private Long modifyBy;

    /**
     * 最新更新时间
     */
    private Date modifyDate;

    private static final long serialVersionUID = 1L;
}