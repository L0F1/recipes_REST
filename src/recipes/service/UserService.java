package recipes.service;

import recipes.model.User;

public interface UserService {

    boolean registerUser(User user);

    User getCurrentUser();
}
