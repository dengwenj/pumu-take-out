package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.AddressBookEntity;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    void insert(AddressBookEntity addressBookEntity);

    // 获取全部地址
    List<AddressBookEntity> list();

    AddressBookEntity findById(Long id);
}
