package com.cicada.storage.dao;

import com.cicada.storage.bean.UserInfo;
import com.dotin.common.utils.PropertiesUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Created by jinxiao.wu
 * Date: 1/18/18
 * Time: 15:21
 */
@Component
public class LdapDao {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LdapDao.class);

    // LDAP服务器端口默认为389
    private static final String LDAP_URL = PropertiesUtils.getPropertiesAttribute("ldapConfig", "ldap.host");
    private static final String ACCOUNT = PropertiesUtils.getPropertiesAttribute("ldapConfig", "ldap.account");
    private static final String PASSWORD = PropertiesUtils.getPropertiesAttribute("ldapConfig", "ldap.password");
    private static final String DC = PropertiesUtils.getPropertiesAttribute("ldapConfig", "ldap.dc");
    private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final SearchControls searchControls = new SearchControls();

    public LdapDao() {
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    }

    public UserInfo getLoginInfo(String email, String password) {
        try {
            UserInfo user = search(email);
            if (user != null) {
                user.setLoginId(email);
                Hashtable<String, String> env = new Hashtable();
                env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);//导入提供者
                env.put(Context.PROVIDER_URL, LDAP_URL + DC);//服务器地址
                env.put(Context.SECURITY_AUTHENTICATION, "simple");//验证方式
                env.put(Context.SECURITY_PRINCIPAL, user.getLdapNaming());//账户
                env.put(Context.SECURITY_CREDENTIALS, password);//证书
                DirContext context = new InitialDirContext(env);
                closeCtx(context);
            }
            return user;
        } catch (NamingException e) {
            logger.error("Account: " + email + " is not authenticated");
        } catch (Exception e) {
            logger.error("Account: " + email + "登录异常", e);
        }
        return null;
    }


    /**
     * 获取ladp上下文
     *
     * @return
     */
    private LdapContext getLdapContext() {
        LdapContext ctx;
        Hashtable<String, String> env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);//导入提供者
        env.put(Context.PROVIDER_URL, LDAP_URL + DC);//服务器地址
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//验证方式
        env.put(Context.SECURITY_PRINCIPAL, ACCOUNT);//账户
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);//证书
        try {
            ctx = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            throw new RuntimeException("ldap认证失败,请重新登录!", e);
        } catch (Exception e) {
            throw new RuntimeException("ldap认证失败,请重新登录!", e);
        }
        return ctx;
    }

    private UserInfo search(String account) {
        LdapContext ctx = getLdapContext();
        UserInfo user = null;
        try {
            NamingEnumeration<SearchResult> answer = ctx.search("", "(SamAccountName=" + account + ")", searchControls);
            while (answer != null && answer.hasMoreElements()) {
                SearchResult si = answer.nextElement();
                user = new UserInfo();
                user.setLdapNaming(si.getName() + "," + DC);
                user.setMemberOf(String.valueOf(si.getAttributes().get("memberOf")).replaceFirst("memberOf: ", ""));
                user.setEmail(String.valueOf(si.getAttributes().get("mail")).replaceFirst("mail: ", ""));
                user.setUserName(String.valueOf(si.getAttributes().get("name")).replaceFirst("name: ", ""));

                break;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取ldap dn失败,请重新登录!", e);
        } finally {
            closeCtx(ctx);
        }
        return user;
    }

    // 关闭LDAP服务器连接
    private static void closeCtx(DirContext context) {
        try {
            context.close();
        } catch (Exception ex) {
        }
    }

}
