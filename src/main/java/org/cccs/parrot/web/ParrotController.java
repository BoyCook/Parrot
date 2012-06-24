package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
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
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static java.lang.String.format;

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
    protected static final String SERVICE_PATH = "/service";

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    public Object getParrotEntity(HttpServletRequest request,
                                  HttpServletResponse response) {
        ContextBuilder.init("org.cccs.parrot.domain");
        String inboundPath = urlPathHelper.getPathWithinApplication(request);
        inboundPath = inboundPath.substring(SERVICE_PATH.length());

        log.debug("Inbound URL: " + inboundPath);

        final String match = PathMatcher.getMatcher().match(inboundPath);

        if (match != null) {
            Class clazz = ContextBuilder.getContext().getRequestMappings().get(match);
            log.debug(format("Found resource match [%s] as [%s]", match, clazz.getSimpleName()));
        } else {
            log.error("No resource match found");
            response.setStatus(404);
        }

        return ContextBuilder.getContext();
    }

    public String extractParameter(HttpServletRequest request, int position) {
        return Utils.extractParameter(urlPathHelper.getPathWithinApplication(request), position);
    }
}
