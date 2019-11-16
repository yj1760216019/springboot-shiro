package com.carlinx.shiro.config.shiro;


import com.carlinx.shiro.config.realm.ManageRealm;
import com.carlinx.shiro.config.realm.UserRealm;
import com.carlinx.shiro.filter.JwtFilter;
import com.carlinx.shiro.utils.cache.ShiroCacheManager;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class ShiroConfig {

    /**
     * 配置安全管理器对象
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getSecurityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //使用自定义Realm
        manager.setAuthenticator(getMyModularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        realms.add(getUserRealm());
        realms.add(getManageRealm());
        manager.setRealms(realms);

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        //设置自定义Cache缓存
        manager.setCacheManager(new ShiroCacheManager());
        return manager;
    }


    /**
     * 自定义UserRealm
     * @return
     */
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setName("userRealm");
        return userRealm;
    }


    /**
     * 自定义ManageRealm
     * @return
     */
    public ManageRealm getManageRealm(){
        ManageRealm manageRealm = new ManageRealm();
        manageRealm.setName("manageRealm");
        return manageRealm;
    }



    @Bean
    public ModularRealmAuthenticator getMyModularRealmAuthenticator(){
        MyModularRealmAuthenticator myModularRealmAuthenticator = new MyModularRealmAuthenticator();
        myModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return myModularRealmAuthenticator;
    }


    /**
     * 配置shiro过滤规则
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setLoginUrl("/api/user/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/user/unlogin");
        //添加自定义jwt过滤器
        HashMap<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwtFilter",getJwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        //自定义url规则
        HashMap<String, String> urlRuleMap = new HashMap<>();
        urlRuleMap.put("/api/test/**","jwtFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(urlRuleMap);
        return shiroFilterFactoryBean;
    }


    @Bean("jwtFilter")
    public JwtFilter getJwtFilter() {
        return new JwtFilter();
    }


    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }



}
