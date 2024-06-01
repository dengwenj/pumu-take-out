package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.dto.SetmealDTO;
import vip.dengwj.dto.SetmealDishDTO;
import vip.dengwj.entity.SetmealDishEntity;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.mapper.SetmealDishMapper;
import vip.dengwj.mapper.SetmealMapper;
import vip.dengwj.service.SetmealService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SetmealDTO setmealDTO) {
        SetmealEntity setmealEntity = new SetmealEntity();
        BeanUtils.copyProperties(setmealDTO, setmealEntity);

        // 套餐表
        setmealMapper.insert(setmealEntity);
        Long setmealId = setmealEntity.getId();

        // 套餐菜品关系表
        // 套餐菜品
        List<SetmealDishDTO> setmealDishDTOList = setmealDTO.getSetmealDishDTOList();
        // 套餐菜品实体
        List<SetmealDishEntity> setmealDishEntityList = new ArrayList<>();
        for (SetmealDishDTO setmealDishDTO : setmealDishDTOList) {
            SetmealDishEntity setmealDishEntity = SetmealDishEntity.builder()
                // 套餐id
                .setmealId(setmealId)
                // 菜品id
                .dishId(setmealDishDTO.getId())
                .name(setmealDishDTO.getName())
                .price(setmealDishDTO.getPrice())
                .copies(setmealDishDTO.getCopies())
                .build();

            setmealDishEntityList.add(setmealDishEntity);
        }

        // 套餐菜品表
        setmealDishMapper.insertBatch(setmealDishEntityList);
    }
}
