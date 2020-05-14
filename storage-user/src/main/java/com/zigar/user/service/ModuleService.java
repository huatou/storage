package com.zigar.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.api.entity.ModuleEntity;
import com.zigar.zigarcore.action.RequestInsertAction;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * <p>
 * 模块 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
public interface ModuleService extends IService<ModuleEntity> {

    void insertModule(ModuleEntity moduleEntity);

    void updateModule(ModuleEntity moduleEntity);

}
