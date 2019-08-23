package org.winterframework.sso.filter;

import org.winterframework.sso.util.AntPathMatcher;
import org.winterframework.sso.util.PathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YHG
 * @Date: 2019/8/18 10:12
 */
public abstract class BaseSsoFilter implements Filter {

    /**
     * 不拦截的映射
     * 支持Ant表达式
     */
    protected List<String> excludedMappingList;

    private final static PathMatcher PATH_MATCHER = new AntPathMatcher();
    /**
     * 添加排除映射
     *
     * @param excludedMapping
     */
    public void addExcludedMapping(String excludedMapping) {
        if (excludedMappingList == null) {
            excludedMappingList = new ArrayList<>();
        }
        excludedMappingList.add(excludedMapping);
    }

    /**
     * 判断是否需要排除
     *
     * @param mapping
     * @return
     */
    protected boolean isExcluded(String mapping) {
        if (excludedMappingList != null) {
            for (String excludedMapping : excludedMappingList){
                if (PATH_MATCHER.match(excludedMapping.trim(), mapping.trim())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
