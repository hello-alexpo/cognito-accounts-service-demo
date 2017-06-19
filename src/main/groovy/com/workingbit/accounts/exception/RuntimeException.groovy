package com.workingbit.accounts.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

/**
 * Created by Aleksey Popryaduhin on 23:50 18/06/2017.
 */
@ExceptionHandler(Throwable.class)
@ResponseBody
Throwable processRuntimeException(final HttpServletRequest req, final Throwable ex) {
    println("Uncaught exception ${ex}")
    throw ex
}
