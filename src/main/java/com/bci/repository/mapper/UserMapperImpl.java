package com.bci.repository.mapper;

import com.bci.domain.Phone;
import com.bci.domain.User;
import com.bci.repository.entity.PhoneData;
import com.bci.repository.entity.UserData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserData toEntity(User user) {
        return UserData.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(phonesToPhonesData(user.getPhones()))
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.isActive())
                .build();
    }

    @Override
    public User toDomain(UserData userData) {
        return User.builder()
                .id(userData.getUserId())
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .phones(phonesDataToPhones(userData.getPhones()))
                .created(userData.getCreated())
                .lastLogin(userData.getLastLogin())
                .token(userData.getToken())
                .isActive(userData.isActive())
                .build();
    }

    private List<Phone> phonesDataToPhones(List<PhoneData> phones) {
        if (phones == null) {
            return List.of();
        }
        return phones.stream()
                .map(phoneEntity -> new Phone(
                        phoneEntity.getNumber(),
                        phoneEntity.getCityCode(),
                        phoneEntity.getCountryCode()
                ))
                .collect(Collectors.toList());
    }

    private List<PhoneData> phonesToPhonesData(List<Phone> phones) {
        if (phones == null) {
            return List.of();
        }
        return phones.stream()
                .map(phone -> new PhoneData(
                        phone.getNumber(),
                        phone.getCityCode(),
                        phone.getCountryCode()
                ))
                .collect(Collectors.toList());
    }
}