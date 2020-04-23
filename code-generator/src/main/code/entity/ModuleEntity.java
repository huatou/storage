package entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="ModuleEntity对象", description="模块")
public class ModuleEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "模块ID")
    @TableId("module_id_")
    private String moduleId;

    @ApiModelProperty(value = "模块名")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "操作项，多个以逗号隔开")
    @TableField("actions_")
    private String actions;

    @ApiModelProperty(value = "排序")
    @TableField("sort_")
    private Integer sort;

    @ApiModelProperty(value = "是否可用")
    @TableField("is_enabled_")
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time_")
    private LocalDateTime createTime;


}
