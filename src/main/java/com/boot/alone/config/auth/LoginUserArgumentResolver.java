package com.boot.alone.config.auth;

import com.boot.alone.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver { // WebMvcConfigurer통해 등록필요

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { // 컨트롤러 메서드의 특정 파라미터를 지원하는지
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; // 파라미터에 @LoginUser 어노테이션이 붙어 있는지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType()); // 파라미터 클래스 타입이 SessionUser.class 인지
        return isLoginUserAnnotation && isUserClass;
    }

    @Override // 파라미터에 전달할 객체
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
