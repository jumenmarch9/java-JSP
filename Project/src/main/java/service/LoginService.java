package service;

import java.util.Optional;

import model.User;
import repository.LoginRepository;

public class LoginService {
    private LoginRepository loginRepository;
    
    public LoginService() {
        loginRepository = new LoginRepository();
    }
    
    // 회원 가입
    public boolean addUser(User user) { 
        if(checkById(user.getId())) {
            // 이미 같은 ID가 존재함
            return false;
        } else {
            // 비밀번호가 10자리 이하인지 확인 (필요시 추가)
            // 권한 "user" 설정
            user.setAuthority("user");

            // DB에 사용자 추가
            loginRepository.addUser(user);
            
            System.out.println("LoginService >> success add User");
            return true;
        }
    }
    
    // 로그인 확인
    public Optional<User> isUser(User input) { 
        System.out.println("LoginService >> isUser() >> checking ... ");

        // ID로 사용자 정보 조회
        Optional<User> dbUser = loginRepository.getUserById(input.getId());

        // ID가 존재하고 비밀번호가 일치하는지 확인
        if(dbUser.isPresent() && dbUser.get().getPasswd().equals(input.getPasswd())) {
            System.out.println("LoginService >> login success");
            return dbUser;
        } else {
            System.out.println("LoginService >> login fail");
            return dbUser;
        }
    }
    
    // ID 중복 확인
    private boolean checkById(String id){ 
        return loginRepository.getUserById(id).isPresent();
    }
}
