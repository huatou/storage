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
 * 用户
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_user")
@ApiModel(value="UserEntity对象", description="用户")
public class UserEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    @TableId("contract_id_")
    private String contractId;

    @ApiModelProperty(value = "用户名")
    @TableField("username_")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password_")
    private String password;

    @ApiModelProperty(value = "性别")
    @TableField("sex_")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone_")
    private Integer phone;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time_")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time_")
    private LocalDateTime createTime;


}
