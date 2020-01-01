package cn.onesdream.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dev_user
 * @author 
 */
@Data
public class DevUser implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 开发者帐号
     */
    private String devCode;

    /**
     * 开发者名称
     */
    private String devName;

    /**
     * 开发者密码
     */
    private String devPassword;

    /**
     * 开发者电子邮箱
     */
    private String devEmail;

    /**
     * 开发者简介
     */
    private String devInfo;

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