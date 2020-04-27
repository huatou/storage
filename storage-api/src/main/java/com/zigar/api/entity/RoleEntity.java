package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zigar.zigarcore.myabtisplus.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_role")
@ApiModel(value = "RoleEntity对象", description = "角色")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId("role_id_")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    @TableField("name_")
    @Unique
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("sort_")
    private Integer sort;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled_", fill = FieldFill.INSERT)
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
