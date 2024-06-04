package vip.dengwj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dengwj.context.BaseContext;
import vip.dengwj.entity.AddressBookEntity;
import vip.dengwj.mapper.AddressBookMapper;
import vip.dengwj.service.AddressBookService;

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
}
