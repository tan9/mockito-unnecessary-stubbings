import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FooTest {

    @Mock
    private HttpServletRequest request;

    @Test
    public void a() {
        when(request.getHeader("Forwarded")).thenReturn("for=55.66.77.88; proto=http; by=5.4.2.6");
        when(request.getHeader("X-Forwarded-For")).thenReturn("77.88.55.66, 55.66.77.88");
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = Foo.bar(request);
        assertThat(actual).isEqualTo("55.66.77.88");
    }

    @Test
    public void b() {
        when(request.getHeader("X-Forwarded-For")).thenReturn("77.88.55.66, 55.66.77.88");
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = Foo.bar(request);
        assertThat(actual).isEqualTo("77.88.55.66");
    }

    @Test
    public void c() {
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");

        String actual = Foo.bar(request);
        assertThat(actual).isEqualTo("1.2.3.4");
    }
}
