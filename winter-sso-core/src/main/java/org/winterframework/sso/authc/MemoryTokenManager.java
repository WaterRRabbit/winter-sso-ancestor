package org.winterframework.sso.authc;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: YHG
 * @Date: 2019/8/18 16:20
 */
public class MemoryTokenManager implements TokenManager {

    private static Set<String> tokenPool;

    public MemoryTokenManager(){
        init();
    }
    private void init(){
        tokenPool = new HashSet<>();
    }
    @Override
    public boolean store(String token) {
        return tokenPool.add(token);
    }

    @Override
    public boolean check(String token) {
        return tokenPool.contains(token);
    }

    @Override
    public boolean remove(String token) {
        return tokenPool.remove(token);
    }

    @Override
    public String produce(Object object) {
        if (object instanceof String){
            return (String) object;
        }
        return null;
    }

}
