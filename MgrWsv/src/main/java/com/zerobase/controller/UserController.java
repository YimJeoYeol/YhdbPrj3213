package com.zerobase.controller;

//import com.zerobase.application.security.auth.LoginUser;
import com.zerobase.application.service.UserService;
import com.zerobase.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

   private final UserService userService;

//    private final CustomValidators.EmailValidator EmailValidator;
//    private final CustomValidators.NicknameValidator NicknameValidator;
//    private final CustomValidators.UsernameValidator UsernameValidator;
//
//    /* 커스텀 유효성 검증을 위해 추가 */
//    @InitBinder
//    public void validatorBinder(WebDataBinder binder) {
//        binder.addValidators(EmailValidator);
//        binder.addValidators(NicknameValidator);
//        binder.addValidators(UsernameValidator);
//    }

    @GetMapping("/auth/joinForm")
    public String joinForm(){

            /* 회원가입 페이지로 다시 리턴 */
            return "/user/joinForm";

    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }
    //
    @GetMapping("/user/listForm")
    public String dataList(Model model, @PageableDefault(size = 5, sort = "userid", direction = Sort.Direction.DESC)
    Pageable pageable){
        Page<User> user = userService.list(pageable);
        model.addAttribute("users", user);
        return "user/listForm";
    }

    /* Security에서 로그아웃은 기본적으로 POST지만, GET으로 우회 */
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }

    /* 회원정보 수정 */
//    @GetMapping("/modify")
//    public String modify(@LoginUser UserDto.Response user, Model model) {
//        if (user != null) {
//            model.addAttribute("user", user);
//        }
//        return "/user/user-modify";
//    }
}
