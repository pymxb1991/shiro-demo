package cn.itcast.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/20
 */
public class PermissionRealm extends AuthorizingRealm {

    /**
     * 自定义Realm名称
     *      因为realm 可能会有很多，它是通过name来进行辨别的，
     *      这时在shiro中有认证处理，处理过程就是以name 来做的，
     * @param name
     */
    public void setName(String name){
        super.setName("permissionRealm");
    }


    /**
     *  授权  获取用户的授权数据，用户的权限数据
     *        授权：也就是用户登陆成功之后，进一步判断这个用户是否具有操作某一功能的权限
     *        授权的主要目的：
     *              根据认证数据获取到用户的权限信息
     *
     *    PrincipalCollection   包含了所有已经认证的安全数据；
     *
     *    AuthorizationInfo ；授权数据
     *
     *    此方法的主要目的就是，根据username 得到授权数据 ，也就是权限信息，
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权方法");
        //1、获取到安全数据   一般是username,用户ID
        String username = (String) principals.getPrimaryPrincipal();
        //2、根据id或名称查询用户
        //3、查询用户的角色和权限信息
        //模拟用户和角色权限信息
        List<String> perms = new ArrayList<>();
        perms.add("user:save");
        perms.add("user:update");
        List<String> roles = new ArrayList<>();
        roles.add("role1");
        roles.add("role2");

        //4、构造返回  还是用它的 S
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限集合
        info.addStringPermissions(perms);
        //设置角色集合
        info.addRoles(roles);
        return info;
    }

    /**
     * 认证    根据用户名密码登陆，将数据保存到  安全数据
     *      认证的目的： 比较用户名密码是否与数据库中是一致的；
     *      如果一致，将安全数据存入shiro，进行保管
     *
     *参数：token 登录构造的usernamepasswordtoken
     *
     * @param token  认证token ，其中之前用过的 USERNAMEPASSWORDTOKEN 就是它的实现
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证方法");
        //构造uptoken
        UsernamePasswordToken uptoken = (UsernamePasswordToken)token;
        //2、获取输入的用户名，密码
        String username = uptoken.getUsername();
        String password = new String(uptoken.getPassword());//注意uptoken.getPassword() 获取到的是个Char 数据，需要转换
        //3、根据用户名，查询数据库
        //4、比较密码和数据库中密码是否一致，（密码可能需要加密）

        //假设已经查询到数据；直接进行比较
        if ("123456".equals(password)) {
            //5、如果成功，向shiro中写入安全数据, 也就是要构造一个存入 AuthenticationInfo 的数据

            //一般情况下，我们认证数据用的是 AuthenticationInfo 的实现类  SimpleAuthenticationInfo  ,其中，他也是一个接口
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName()); //1.安全数据，2.密码。3。当前realm域名称
            return  info;
        }else {
            //6、失败，抛出异常
            throw new RuntimeException("用户名或密码错误");
        }
    }
}