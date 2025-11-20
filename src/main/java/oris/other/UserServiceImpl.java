package oris.other;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DataClass dataClass;
    private final UserToUserEntityConverter converter;

    public UserEntity authenticateUser(String username, String password) {
        return dataClass.getUser(username, password);
    }

    @Override
    public void save(User user) {
        dataClass.saveNewUser(converter.convert(user));
    }
}