package service.impl;

import entity.ActionEntity;
import mapper.ActionMapper;
import service.ActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作项表 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-28
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, ActionEntity> implements ActionService {

}
