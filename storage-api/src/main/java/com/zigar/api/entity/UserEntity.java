package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigar.zigarcore.myabtisplus.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@ApiModel(value = "UserEntity对象", description = "用户")
public class UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "姓名")
    @TableField("display_name_")
    private String displayName;

    @ApiModelProperty(value = "性别")
    @TableField("sex_")
    private String sex;

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

    @JsonIgnore
    @TableField(value = "pwd_reset_time_")
    private LocalDateTime pwdResetTime;

    @JsonIgnore
    @TableField(value = "system_role_")
    private String systemRole;

    @JsonIgnore
    @TableField(value = "is_account_non_expired_")
    private Boolean isAccountNonExpired;

    @JsonIgnore
    @TableField(value = "is_account_non_locked_")
    private Boolean isAccountNonLocked;

    @JsonIgnore
    @TableField(value = "is_credentials_non_expired_")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled_")
    private Boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();



        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
