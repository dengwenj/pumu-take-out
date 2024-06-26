package vip.dengwj.service;

import vip.dengwj.entity.AddressBookEntity;

import java.util.List;

public interface AddressBookService {
    // 新增地址
    void insert(AddressBookEntity addressBookEntity);

    // 获取全部地址
    List<AddressBookEntity> list();

    AddressBookEntity findById(Long id);

    void update(AddressBookEntity addressBookEntity);

    void delete(Long id);

    AddressBookEntity findDefault();

    void updateDefault(Long id);
}
