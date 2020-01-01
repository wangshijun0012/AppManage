package cn.onesdream.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * data_dictionary
 * @author 
 */
@Data
public class DataDictionary implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型值ID
     */
    private Long valueId;

    /**
     * 类型值Name
     */
    private String valueName;

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