package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.CategoryDTO;
import vip.dengwj.dto.CategoryQueryDTo;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.CategoryMapper;
import vip.dengwj.service.CategoryService;
import vip.dengwj.vo.PageVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryDTO, categoryEntity);

        categoryMapper.save(categoryEntity);
    }

    @Override
    public PageVO<CategoryEntity> page(CategoryQueryDTo categoryQueryDTo) throws IllegalAccessException {
        Integer page = categoryQueryDTo.getPage();
        Integer pageSize = categoryQueryDTo.getPageSize();
        int start = (page - 1) * pageSize;
        categoryQueryDTo.setPage(start);

        Integer count = categoryMapper.count(categoryQueryDTo);

        List<CategoryEntity> list = categoryMapper.page(categoryQueryDTo);

        return new PageVO<>(count, list);
    }

    /**
     * 更新分类
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryDTO, categoryEntity);

        categoryMapper.update(categoryEntity);
    }

    /**
     * 启用禁用分类
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        if (Objects.equals(status, StatusConstant.ENABLE)) {
            status = StatusConstant.DISABLE;
        } else {
            status = StatusConstant.ENABLE;
        }

        CategoryEntity categoryEntity = CategoryEntity.builder()
            .id(id)
            .status(status)
            .updateTime(LocalDateTime.now())
            .updateUser(BaseContext.get())
            .build();

        categoryMapper.update(categoryEntity);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List<CategoryEntity> getListByType(Integer type) {
        if (type == null) {
            throw new BaseException("分类类型为空");
        }
        return categoryMapper.getListByType(type);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    // TODO 关系到菜品表和套餐表，有菜品或套餐不能删除
    public void delete(Long id) {
        categoryMapper.delete(id);
    }
}
