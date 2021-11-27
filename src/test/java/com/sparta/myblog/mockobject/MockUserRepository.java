package com.sparta.myblog.mockobject;
import com.sparta.myblog.domain.User;
import java.util.ArrayList;

public class MockUserRepository {

    private ArrayList<User> users;
    private Long userId = 1L;

    public MockUserRepository (){
        users = new ArrayList<>();
    }

    public User save(User user) {
        user.setId(userId);
        ++userId;
        users.add(user);
        return user;
    }

    boolean existsByNickname(String nickname) {
        for(User user : users) {
            if(nickname.equals(user.getNickname()))
                return true;
        }
        return false;
    }

}
