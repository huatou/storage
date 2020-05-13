package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_privilege")
@ApiModel(value = "PrivilegeEntity对象", description = "权限")
public class PrivilegeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableId("privilege_id_")
    private String privilegeId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id_")
    private String userId;

    @ApiModelProperty(value = "模块ID")
    @TableField("module_id_")
    private String moduleId;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id_")
    private String roleId;

    @ApiModelProperty(value = "手机号")
    @TableField("actions_")
    private Integer actions;

    @ApiModelProperty(value = "权限类型，用户（user），角色（role）")
    @TableField("type_")
    private Date type;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_",fill = FieldFill.INSERT)
    private Date createTime;


}
