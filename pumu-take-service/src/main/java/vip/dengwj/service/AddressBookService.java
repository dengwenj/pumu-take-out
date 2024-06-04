package vip.dengwj.service;

import vip.dengwj.entity.AddressBookEntity;

import java.util.List;

public interface AddressBookService {
    // 新增地址
    void insert(AddressBookEntity addressBookEntity);

    // 获取全部地址
    List<AddressBookEntity> list();
}
