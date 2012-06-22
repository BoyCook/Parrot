package org.cccs.parrot.web;

import org.cccs.parrot.context.ParrotContext;
import org.cccs.parrot.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 11:41
 */
@Controller
@Scope("session")
public class ParrotController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    public Object getParrotEntity(HttpServletRequest request) {
        log.debug(urlPathHelper.getPathWithinApplication(request));
        return new ParrotContext(null, null);
    }

    public String extractParameter(HttpServletRequest request, int position) {
        return Utils.extractParameter(urlPathHelper.getPathWithinApplication(request), position);
    }
}
