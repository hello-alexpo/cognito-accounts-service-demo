package com.workingbit.accounts.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ErrorAttributes
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
/**
 * Created by Aleksey Popryaduhin on 09:21 19/06/2017.
 */
@Controller
@RequestMapping('/error')
class IndexController implements ErrorController {

    private ErrorAttributes errorAttributes
    static final PATH = '/error'

    @Autowired
    SimpleErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    Map<String, Object> error(HttpServletRequest aRequest) {
        Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
        String trace = (String) body.get("trace");
        if (trace != null) {
            String[] lines = trace.split("\n\t");
            body.put("trace", lines);
        }
        return body;
    }

    private static boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    String getErrorPath() {
        return PATH
    }
}
