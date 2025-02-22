package com.bci.infrastructure.output.adapter.mapper

import com.bci.domain.Phone
import com.bci.domain.User
import com.bci.infrastructure.output.repository.entity.PhoneData
import com.bci.infrastructure.output.repository.entity.UserData
import org.springframework.stereotype.Component

@Component
class UserMapperImpl implements UserMapper{
    @Override
    UserData toEntity(User user) {
        return UserData.builder()
                .userId(user.id)
                .name(user.name)
                .email(user.email)
                .password(user.password)
                .phones(phonesToPhonesData(user.phones))
                .created(user.created)
                .lastLogin(user.lastLogin)
                .token(user.token)
                .isActive(user.isActive)
                .build()
    }

    @Override
    User toDomain(UserData userData) {
        return User.builder()
                .id(userData.userId)
                .name(userData.name)
                .email(userData.email)
                .password(userData.password)
                .phones(phonesDataToPhones(userData.phones))
                .created(userData.created)
                .lastLogin(userData.lastLogin)
                .token(userData.token)
                .isActive(userData.isActive)
                .build()
    }

    private static List<Phone> phonesDataToPhones(List<PhoneData> phones) {
        return phones?.collect { phoneEntity ->
            new Phone(
                    number: phoneEntity.number,
                    cityCode: phoneEntity.cityCode,
                    countryCode: phoneEntity.countryCode
            )
        } ?: []
    }

    private static List<PhoneData> phonesToPhonesData(List<Phone> phones) {
        return phones?.collect { phone ->
            new PhoneData(
                    number: phone.number,
                    cityCode: phone.cityCode,
                    countryCode: phone.countryCode
            )
        } ?: []
    }


}
