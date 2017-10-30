import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpServletRequestsTest {

    @Mock
    private HttpServletRequest request;

    @Test
    public void testGetClientAddress$Forwarded() {
        when(request.getHeader("Forwarded")).thenReturn("for=55.66.77.88; proto=http; by=5.4.2.6");
        when(request.getHeader("X-Forwarded-For")).thenReturn("77.88.55.66, 55.66.77.88");
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = HttpServletRequests.getClientAddress(request);
        assertThat(actual).isEqualTo("55.66.77.88");
    }

    @Test
    public void testGetClientAddress$XForwardedFor() {
        when(request.getHeader("X-Forwarded-For")).thenReturn("77.88.55.66, 55.66.77.88");
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = HttpServletRequests.getClientAddress(request);
        assertThat(actual).isEqualTo("77.88.55.66");
    }

    @Test
    public void testGetClientAddress$RequestRemoteAddr() {
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = HttpServletRequests.getClientAddress(request);
        assertThat(actual).isEqualTo("1.2.3.4");
    }
}
