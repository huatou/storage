package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 模块
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_module")
@ApiModel(value = "ModuleEntity对象", description = "模块")
public class ModuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {RequestUpdateAction.class, RequestDeleteAction.class}, message = "moduleId不能为空")
    @ApiModelProperty(value = "模块ID")
    @TableId("module_id_")
    private String moduleId;

    @NotBlank(groups = RequestInsertAction.class, message = "新建请输入模块名")
    @ApiModelProperty(value = "模块名")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("sort_")
    private Integer sort;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled_", fill = FieldFill.INSERT)
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "图标")
    @TableField("icon_")
    private String icon;

    @ApiModelProperty(value = "路径")
    @TableField("to_")
    private String to;

    @TableField(exist = false)
    @Valid
    private List<ActionEntity> actionList;

    public List<ActionEntity> getActionList() {
        if (!CollectionUtils.isEmpty(actionList) && StringUtils.isNotEmpty(moduleId)) {
            actionList.forEach(actionEntity -> {
                actionEntity.setModuleId(moduleId);
            });
        }
        return actionList;
    }

}
