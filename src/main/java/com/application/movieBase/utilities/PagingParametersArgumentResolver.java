package com.application.movieBase.utilities;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PagingParametersArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            @NonNull
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        return PageRequest.of(
                resolveOffset(webRequest),
                resolveLimit(webRequest));
    }

    private Integer resolveOffset(NativeWebRequest webRequest) {
        String offset = webRequest.getParameter("offset");
        return null != offset ? Integer.parseInt(offset) : 0;
    }

    private Integer resolveLimit(NativeWebRequest webRequest) {
        String limit = webRequest.getParameter("limit");
        return null != limit ? Integer.parseInt(limit) : 20;
    }
}
