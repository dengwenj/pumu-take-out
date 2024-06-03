package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.dto.SetmealDTO;
import vip.dengwj.dto.SetmealDishDTO;
import vip.dengwj.dto.SetmealQueryDTO;
import vip.dengwj.entity.SetmealDishEntity;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.SetmealDishMapper;
import vip.dengwj.mapper.SetmealMapper;
import vip.dengwj.service.SetmealService;
import vip.dengwj.vo.PageVO;
import vip.dengwj.vo.SetmealVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    /**
     * 分类查询
     */
    @Override
    public PageVO<SetmealVO> page(SetmealQueryDTO setmealQueryDTO) {
        Integer page = setmealQueryDTO.getPage();
        Integer pageSize = setmealQueryDTO.getPageSize();
        int start = (page - 1) * pageSize;

        setmealQueryDTO.setPage(start);

        Integer count = setmealMapper.count(setmealQueryDTO);
        List<SetmealVO> list = setmealMapper.page(setmealQueryDTO);

        return new PageVO<>(count, list);
    }

    /**
     * 批量删除套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String ids) {
        // 起售中的不能删除
        // 根据 ids 批量查询数据
        List<SetmealEntity> setmeals = setmealMapper.getSetmealByIds(ids);
        for (SetmealEntity s : setmeals) {
            if (Objects.equals(s.getStatus(), StatusConstant.ENABLE)) {
                throw new BaseException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        // 套餐菜品关系表中根据套餐 id 也要删除
        setmealDishMapper.deleteBySetmealIds(ids);

        // 删除套餐
        setmealMapper.deleteByIds(ids);
    }

    /**
     * 更新套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SetmealDTO setmealDTO) {
        SetmealEntity setmealEntity = new SetmealEntity();
        BeanUtils.copyProperties(setmealDTO, setmealEntity);
        // 套餐更新
        setmealMapper.update(setmealEntity);

        // 套餐菜品关系表
        // 先删除在插入
        setmealDishMapper.deleteBySetmealIds(setmealDTO.getId() + "");

        List<SetmealDishEntity> list = new ArrayList<>();
        for (SetmealDishDTO setmealDishDTO : setmealDTO.getSetmealDishDTOList()) {
            list.add(
                SetmealDishEntity.builder()
                    .setmealId(setmealDTO.getId())
                    .dishId(setmealDishDTO.getId())
                    .name(setmealDishDTO.getName())
                    .price(setmealDishDTO.getPrice())
                    .copies(setmealDishDTO.getCopies())
                    .build()
            );
        }
        setmealDishMapper.insertBatch(list);
    }

    /**
     * 根据 id 获取套餐
     */
    @Override
    public SetmealDTO getSetmealById(Long id) {
        List<SetmealEntity> list = setmealMapper.getSetmealByIds(id + "");
        SetmealEntity setmealVO = list.get(0);

        SetmealDTO setmealDTO = new SetmealDTO();
        BeanUtils.copyProperties(setmealVO, setmealDTO);

        // 根据套餐 id 获取套餐菜品数据
        List<SetmealDishEntity> setmealDishList = setmealDishMapper.findBySetmealId(id);
        List<SetmealDishDTO> setmealDishDTOList = new ArrayList<>();
        setmealDishList.forEach((item) -> {
            setmealDishDTOList.add(
                SetmealDishDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .copies(item.getCopies())
                .build()
            );
        });
        setmealDTO.setSetmealDishDTOList(setmealDishDTOList);

        return setmealDTO;
    }

    /**
     * 根据分类 id 查询套餐
     */
    @Override
    public List<SetmealEntity> getSetmealListByCategoryId(Long categoryId) {
        return setmealMapper.getSetmealListByCategoryId(categoryId);
    }
}
