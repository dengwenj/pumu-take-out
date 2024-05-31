package vip.dengwj.service;

import vip.dengwj.dto.CategoryDTO;
import vip.dengwj.dto.CategoryQueryDTo;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.vo.PageVO;

public interface CategoryService {
    // 新增分类
    void save(CategoryDTO categoryDTO);

    // 分类查询
    PageVO<CategoryEntity> page(CategoryQueryDTo categoryQueryDTo) throws IllegalAccessException;

    // 更新分类
    void update(CategoryDTO categoryDTO);
}