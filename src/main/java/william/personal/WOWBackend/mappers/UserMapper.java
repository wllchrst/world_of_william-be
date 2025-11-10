package william.personal.WOWBackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import william.personal.WOWBackend.models.data.UserData;
import william.personal.WOWBackend.models.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    UserData toUserData(User user);
}
