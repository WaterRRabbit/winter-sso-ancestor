import org.winterframework.sso.util.AntPathMatcher;
import org.winterframework.sso.util.PathMatcher;

/**
 * @Author: YHG
 * @Date: 2019/8/18 20:12
 */
public class PathTest {
    public static void main(String[] args) {
        PathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.isPattern("/ip/?"));
        System.out.println(pathMatcher.match("/ip/*/123", "/ip/1/123"));
    }
}
