package epam.andrew.gameShop.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.TimeZone;

public class TimeZoneFilter implements Filter {
    private static final String TIME_ZONE = "timeZone";
    private static final String TIME_ZONE_ENCODE = "UTC";
    private String timeZone;

    @Override
    public void init(FilterConfig filterConfig) {
        timeZone = filterConfig.getInitParameter(TIME_ZONE);

        if (timeZone == null) {
            timeZone = TIME_ZONE_ENCODE;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String currentTimeZone = TimeZone.getDefault().getID();

        if (!currentTimeZone.equals(timeZone)) {
            TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
