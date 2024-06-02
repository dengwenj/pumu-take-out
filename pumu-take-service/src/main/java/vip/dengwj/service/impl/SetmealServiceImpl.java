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
        List<SetmealVO> setmeals = setmealMapper.getSetmealByIds(ids);
        for (SetmealVO s : setmeals) {
            if (Objects.equals(s.getStatus(), StatusConstant.ENABLE)) {
                throw new BaseException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        // 套餐菜品关系表中根据套餐 id 也要删除
        setmealDishMapper.deleteBySetmealIds(ids);

        // 删除套餐
        setmealMapper.deleteByIds(ids);
    }
}
