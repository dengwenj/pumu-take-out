package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.CategoryDTO;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.mapper.CategoryMapper;
import vip.dengwj.service.CategoryService;

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

        Long empId = BaseContext.get();
        categoryEntity.setCreateUser(empId);
        categoryEntity.setUpdateUser(empId);

        categoryMapper.save(categoryEntity);
    }
}
