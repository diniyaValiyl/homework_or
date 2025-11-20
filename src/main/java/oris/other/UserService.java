package oris.other;

public interface UserService {
    UserEntity authenticateUser(String username, String password);
    void save(User user);
}