package com.example.idollbom.controller.login;

import com.example.idollbom.domain.dto.logindto.ParentDTO;
import com.example.idollbom.domain.dto.logindto.ProDTO;
import com.example.idollbom.service.loginservice.ParentService;
import com.example.idollbom.service.loginservice.ProService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class ParentController {

    private final ParentService parentService;
    private final ProService proService;

//  부모 로그인화면이동
    @GetMapping("/login")
    public String login() {
        return "html/login/loginpage";

    }
//  부모 회원가입 화면이동
    @GetMapping("/signup")
    public String signup() {
        log.info("로그인으로 이동");
        return "html/login/registerpage";
    }

//  부모 회원가입폼 제출
    @PostMapping("/signup")
        public String signup(ParentDTO dto) {
        log.info("HTML에서 넘어온 데이터: " + dto.toString());

        parentService.save(dto);
        return "redirect:/user/login";
}

//  로그아웃화면 이동
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/user/login";
    }

//  ============================= 전문가 관련 컨트롤러 ============================= //
//  프로 회원가입폼 이동
    @GetMapping("/proSignup")
    public String proSignupForm(Model model) {
        // 프로 회원가입 페이지에서 회원정보를 받아오기 위해 DTO 전달
        model.addAttribute("pro", new ProDTO());
        return "html/login/proRegisterPage"; // 프로 회원가입 페이지
    }

//  프로 회원가입폼 제출
    @PostMapping("/proSignup")
    public String proSignup(ProDTO proDTO) {
        // DB에 프로 회원정보 저장
        proService.savePro(proDTO);
        
        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

}
