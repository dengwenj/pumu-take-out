package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.dto.DishDTO;
import vip.dengwj.dto.DishQueryDTO;
import vip.dengwj.entity.DishEntity;
import vip.dengwj.entity.DishFlavorEntity;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.DishFlavorMapper;
import vip.dengwj.mapper.DishMapper;
import vip.dengwj.mapper.SetmealDishMapper;
import vip.dengwj.service.DishService;
import vip.dengwj.vo.DishVo;
import vip.dengwj.vo.PageVO;

import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

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

            // 添加菜品口味
            dishFlavorMapper.insertBatch(flavors);
        }
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

    /**
     * 删除菜品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String ids) {
        // 起售中的菜品不能删除
        List<DishEntity> dishList = dishMapper.getDisByIds(ids);
        for (DishEntity dish : dishList) {
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
                throw new BaseException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 被套餐关联的菜品不能删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new BaseException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 删除菜品后，关联的口味数据也需要删除
        dishMapper.deleteBatch(ids);
        dishFlavorMapper.deleteByDishId(ids);
    }

    /**
     * 根据菜品 id 获取菜品数据
     */
    @Override
    public DishDTO findById(Long id) {
        DishDTO dishDTO = dishMapper.findById(id);
        List<DishFlavorEntity> dishFlavors = dishFlavorMapper.getListByDishId(id);

        dishDTO.setFlavors(dishFlavors);

        return dishDTO;
    }

    /**
     * 更新菜品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DishDTO dishDTO) {
        DishEntity dishEntity = new DishEntity();
        BeanUtils.copyProperties(dishDTO, dishEntity);

        // 修改菜品表
        dishMapper.update(dishEntity);

        // 先删除再重新插入
        // 根据菜品 id 删除菜品口味表
        dishFlavorMapper.deleteByDishId(dishEntity.getId() + "");

        // 重新插入
        Long id = dishEntity.getId();
        List<DishFlavorEntity> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavorEntity flavor : flavors) flavor.setDishId(id);

            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
        }
    }

    /**
     * 菜品起售停售
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        DishEntity dishEntity = new DishEntity();
        if (Objects.equals(status, StatusConstant.ENABLE)) {
            dishEntity.setStatus(StatusConstant.DISABLE);
        } else {
            dishEntity.setStatus(StatusConstant.ENABLE);
        }
        dishEntity.setId(id);

        dishMapper.update(dishEntity);
    }
}
