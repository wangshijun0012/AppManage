package cn.onesdream.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * app_category
 * @author 
 */
@TableName(value = "app_category")
@Data
public class AppCategory implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父级节点id
     */
    private Long parentId;

    /**
     * 创建者（来源于backend_user用户表的用户id）
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date creationTime;

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