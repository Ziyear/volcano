package com.ziyear.volcano.exception;

import com.ziyear.volcano.util.Constants;
import org.springframework.context.MessageSource;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.Locale;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 23:06
 */
public class DuplicateProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/duplicate");

    public DuplicateProblem(String msg) {
        super(TYPE, "发现重复数据", Status.CONFLICT, msg);
    }

    public DuplicateProblem(String msgCode, MessageSource messageSource, Locale locale) {
        super(
                TYPE,
                messageSource.getMessage("Exception.duplicate.title", null, locale),
                Status.CONFLICT,
                messageSource.getMessage(msgCode, null, locale));
    }
}
