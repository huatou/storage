package com.zigar.user.service.impl;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zigar.api.entity.UserEntity;
import com.zigar.api.util.Unique;
import com.zigar.core.exception.BusinessLogicException;
import com.zigar.core.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 自定义service基类
 * @param <M>
 * @param <T>
 */
public class ZServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {


    @Override
    public boolean saveOrUpdate(T entity) {
        unique(entity);
        return super.saveOrUpdate(entity);
    }

    /**
     * 判断数据的唯一性
     *
     * @param t
     * @throws IllegalAccessException
     */
    private void unique(T t) {
        Class clazz = t.getClass();
        Map<Field, Object> uniqueParamMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        Object id = null;
        String idName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Unique.class)) {
                Unique bananaAnnotation = field.getAnnotation(Unique.class);
                boolean isUnique = bananaAnnotation.value();
                if (isUnique) {
                    try {
                        field.setAccessible(true);
                        TableId tableIddAnnotation = field.getAnnotation(TableId.class);
                        Object param = field.get(t);
                        if (tableIddAnnotation != null) {
                            id = param;
                            idName = tableIddAnnotation.value();
                        } else {
                            uniqueParamMap.put(field, param);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Object finalId = id;
        String finalIdName = idName;
        uniqueParamMap.forEach((field, param) -> {
            TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
            QueryWrapper<T> queryWrapper = Wrappers.<T>query().eq(tableFieldAnnotation.value(), param);
            if (finalId != null) {
                queryWrapper = queryWrapper.ne(finalIdName, finalId);
            }
            int count = count(queryWrapper);
            if (count > 0) {
                ApiModelProperty apiModelAnnotation = field.getAnnotation(ApiModelProperty.class);
                throw new BusinessLogicException(StringUtils.append(apiModelAnnotation.value(), "：", param + " 已存在"));
            }
        });
    }

}