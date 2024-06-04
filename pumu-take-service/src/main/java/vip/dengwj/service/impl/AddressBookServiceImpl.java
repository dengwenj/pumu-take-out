package vip.dengwj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.context.BaseContext;
import vip.dengwj.entity.AddressBookEntity;
import vip.dengwj.mapper.AddressBookMapper;
import vip.dengwj.service.AddressBookService;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     */
    @Override
    public void insert(AddressBookEntity addressBookEntity) {
        addressBookEntity.setUserId(BaseContext.get());
        addressBookEntity.setIsDefault(0);
        addressBookMapper.insert(addressBookEntity);
    }

    /**
     * 获取全部地址
     */
    @Override
    public List<AddressBookEntity> list() {
        return addressBookMapper.list(BaseContext.get());
    }

    /**
     * 根据 id 查询地址
     */
    @Override
    public AddressBookEntity findById(Long id) {
        return addressBookMapper.findById(id);
    }

    /**
     * 根据 id 修改地址
     */
    @Override
    public void update(AddressBookEntity addressBookEntity) {
        addressBookMapper.update(addressBookEntity);
    }

    /**
     * 根据 id 删除地址
     */
    @Override
    public void delete(Long id) {
        addressBookMapper.delete(id);
    }

    /**
     * 查询默认地址
     */
    @Override
    public AddressBookEntity findDefault() {
        return addressBookMapper.findDefault(BaseContext.get());
    }

    /**
     * 设置默认地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDefault(Long id) {
        // 把原先的默认地址取消掉
        AddressBookEntity aDefault = findDefault();
        AddressBookEntity addressBookEntity = new AddressBookEntity();
        // 说明有默认地址
        if (aDefault != null) {
            addressBookEntity.setId(aDefault.getId());
            addressBookEntity.setIsDefault(0);
            addressBookMapper.update(addressBookEntity);
        }

        // 更换新默认地址
        addressBookEntity.setId(id);
        addressBookEntity.setIsDefault(1);
        addressBookMapper.update(addressBookEntity);
    }
}
