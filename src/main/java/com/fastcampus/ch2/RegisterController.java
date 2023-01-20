package com.fastcampus.ch2;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller // ctrl+shift+o 자동 import
@RequestMapping("/register")
public class RegisterController {

    @InitBinder
    public void toDate(WebDataBinder binder){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.registerCustomEditor(String[].class, "hobby", new StringArrayPropertyEditor("#"));

        //자동 검증
//        binder.setValidator(new UserValidator()); //UserValidator를 WebDataBinder의 로컬 validator로 등록
        //globalValidator를 사용하기 위해 setValidator를 주석처리
//        binder.addValidators(new UserValidator());
        List<Validator> validatorList = binder.getValidators();
        System.out.println("validatorList = " + validatorList);
    }

    @RequestMapping(value="/add", method=RequestMethod.GET)
//	@GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    //	@RequestMapping(value="/save", method=RequestMethod.POST)
    @PostMapping("/add")  // 4.3부터
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result= " + result);
        System.out.println("user= " + user);

        //수동 검증 - validator를 직접 생성하고 validate()를 직접 호출했음
//        UserValidator userValidator = new UserValidator();
//        userValidator.validate(user, result);

        //user 객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여준다
        if(result.hasErrors()){
            return "registerForm";
        }
        // 1. 유효성 검사
//        if(!isValid(user)) {
//            String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
//
//            m.addAttribute("msg", msg);
//            return "forward:/register/add";
////			return "redirect:/register/add?msg="+msg; // URL재작성(rewriting)
//        }
        // 2. DB에 신규회원 정보를 저장
        return "registerInfo";
    }

    private boolean isValid(User user) {
        return true;
    }
}
