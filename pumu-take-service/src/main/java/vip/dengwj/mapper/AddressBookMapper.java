package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.AddressBookEntity;

@Mapper
public interface AddressBookMapper {
    void insert(AddressBookEntity addressBookEntity);
}
