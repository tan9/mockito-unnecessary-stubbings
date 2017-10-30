import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Foo {

    private static final String FORWARDED_FOR_IP_GROUP_NAME = "ip";

    private static final Pattern FORWARDED_FOR_PATTERN = Pattern.compile("for=(?<" + FORWARDED_FOR_IP_GROUP_NAME + ">\\d{1,3}(?:\\.\\d{1,3}){3})");

    private Foo() {
        // prevent it from being initialized
    }

    public static String bar(HttpServletRequest request) {
        // RFC 7239: http://tools.ietf.org/html/rfc7239
        // Forwarded: for=192.0.2.60; proto=http; by=203.0.113.43
        String forwarded = request.getHeader("Forwarded");
        if (forwarded != null) {
            Matcher matcher = FORWARDED_FOR_PATTERN.matcher(forwarded);
            if (matcher.find()) {
                return matcher.group(FORWARDED_FOR_IP_GROUP_NAME);
            }
        }

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null) {
            return xForwardedFor.split(",")[0];
        }

        return request.getRemoteAddr();
    }
}
