package cn.itcast.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
//注意默认的SecurityManager 坐标有问题，需要导入新的坐标；
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Test;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/18
 */
public class ShiroTest01 {
    /**
     * 测试用户认证：
     *        认证：用户登陆
     *        1、根据配置文件创建出SecurityManagerFactory
     *        2、通过工厂获取SecurityManager
     *        3、将SecurityManager 绑定到当前运行环境
     *        4、从当前运行环境中构造subject
     *        5、构造shiro 登陆数据
     *        6、主题登陆；
     */
    @Test
    public void testLogin(){
        //1、根据配置文件创建出SecurityManagerFactory
        //注意默认的SecurityManager 坐标有问题，需要导入新的坐标；
        Factory<SecurityManager> factory =  new IniSecurityManagerFactory("classpath:shiro-test-1.ini");
        //2、 通过工厂获取SecurityManager
        SecurityManager securityManager = factory.getInstance();
        //3、将SecurityManager 绑定到当前运行环境，此外借助的是工具类
        SecurityUtils.setSecurityManager(securityManager);
        // 4、从当前运行环境中构造subject ,  由于 上一步 已经当 SecurityManager绑定到了 当前环境，所以可以直接获取
        Subject subject = SecurityUtils.getSubject();
        //5、构造shiro 登陆数据
        String username = "zhangsan";
        String password = "123456";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //6、主题登陆； 注意接收参数 ：AuthenticationToken token  为用户认证数据；需要先构造认证数据；
        subject.login(token);

        //7验证用户是否登陆成功
        System.out.println("用户是否登陆成功："+subject.isAuthenticated());

        //8 获取用户登陆成功数据
        System.out.println("登陆成功数据：" + subject.getPrincipal()); //获取安全数据
    }
}