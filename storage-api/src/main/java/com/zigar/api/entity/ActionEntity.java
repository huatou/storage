package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 操作项表
 * </p>
 *
 * @author zigar
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_action")
@ApiModel(value = "ActionEntity对象", description = "操作项表")
public class ActionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {RequestUpdateAction.class, RequestDeleteAction.class}, message = "actionId不能为空")
    @ApiModelProperty(value = "操作项Id")
    @TableId("action_id_")
    private String actionId;

    @ApiModelProperty(value = "模块ID")
    @TableField("module_id_")
    private String moduleId;

    @NotBlank(groups = {RequestInsertAction.class}, message = "操作项名称不能为空")
    @ApiModelProperty(value = "操作项名称")
    @TableField("name_")
    private String name;

    @NotBlank(groups = {RequestInsertAction.class}, message = "操作项编码不能为空")
    @ApiModelProperty(value = "操作项编码")
    @TableField("code_")
    private String code;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled_", fill = FieldFill.INSERT)
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "是否默认操作项")
    @TableField(exist = false)
    private Boolean isDefault;

}
