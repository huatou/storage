package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.zigar.api.util.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Insert;

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
    @TableId("user_id_")
    private String userId;

    @ApiModelProperty(value = "用户名")
    @TableField("username_")
    @Unique
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password_")
    private String password;

    @ApiModelProperty(value = "性别")
    @TableField("sex_")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone_")
    @Unique
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time_")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
