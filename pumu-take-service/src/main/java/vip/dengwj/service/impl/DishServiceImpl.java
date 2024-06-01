package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.dto.DishDTO;
import vip.dengwj.dto.DishQueryDTO;
import vip.dengwj.entity.DishEntity;
import vip.dengwj.entity.DishFlavorEntity;
import vip.dengwj.mapper.DishFlavorMapper;
import vip.dengwj.mapper.DishMapper;
import vip.dengwj.service.DishService;
import vip.dengwj.vo.DishVo;
import vip.dengwj.vo.PageVO;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class) // 发生任何错误都要事务回滚
    public void saveWithFlavor(DishDTO dishDTO) {
        DishEntity dishEntity = new DishEntity();
        BeanUtils.copyProperties(dishDTO, dishEntity);
        dishMapper.save(dishEntity);

        // 菜品 id
        Long id = dishEntity.getId();
        List<DishFlavorEntity> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavorEntity flavor : flavors) flavor.setDishId(id);
        }

        // 添加菜品口味
        dishFlavorMapper.insertBatch(flavors);
    }

    /**
     * 查询菜品
     */
    @Override
    public PageVO<DishVo> page(DishQueryDTO dishQueryDTO) {
        Integer page = dishQueryDTO.getPage();
        Integer pageSize = dishQueryDTO.getPageSize();
        int start = (page - 1) * pageSize;
        dishQueryDTO.setPage(start);

        Integer count = dishMapper.count(dishQueryDTO);
        List<DishVo> dishVos = dishMapper.pageQuery(dishQueryDTO);

        return new PageVO<>(count, dishVos);
    }
}
