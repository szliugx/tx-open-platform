package com.taxiong.tx_open_platform.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * API IOC 仓库
 *
 * @author szliugx@gmail.com
 * @created 2018-08-08 下午5:16
 **/
@Slf4j
public class ApiMethodStore {
    private ApplicationContext applicationContext;

    private HashMap<String, ApiRunnable> apiRunnableHashMap = new HashMap<String, ApiRunnable>();

    /**
     * spring ioc
     *
     * @param applicationContext
     */
    public ApiMethodStore(ApplicationContext applicationContext) {
        // 断言 applicationContext不为空
        this.applicationContext = applicationContext;
    }

    /**
     * 加载所有的Bean
     */
    public void loadApiFromSpringBeans() {
        // ioc 所有的bean
        String[] names = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        // 将ApiMethodMapping注解的函数名存起来「可以将参数名也存在一个list钟」
        for (String name : names) {
            type = applicationContext.getType(name);
            for (Method method : type.getDeclaredMethods()) {
                ApiMethodMapping apiMethodMapping = method.getAnnotation(ApiMethodMapping.class);
                if (apiMethodMapping != null) {
                    addApiItem(apiMethodMapping, name, method);
                }
            }
        }

    }

    /**
     * 将所有的接口写入HashMap中（启动时就将参数名、参数类型传入？？？？）
     *
     * @param apiMethodMapping
     * @param beanName
     * @param method
     */
    private void addApiItem(ApiMethodMapping apiMethodMapping, String beanName, Method method) {
        ApiRunnable apiRunnable = new ApiRunnable();
        apiRunnable.apiName = apiMethodMapping.value();
        apiRunnable.targetMethod = method;
        apiRunnable.targetName = beanName;
        // 将参数名和参数类型放入ashMap
        List<Object> methodParameterNameAndParameterType = new ArrayList<Object>();
        // 获取参数的类型
        List<String> paramNames = new ArrayList<>();
        // JDK1.8 后获取参数名称 (编译时，加上此参数 -parameters)
        for (final Parameter parameter : method.getParameters()) {
            paramNames.add(parameter.getName());
        }
        // 获取参数的类型
        Class<?>[] paramTypes = method.getParameterTypes();

        List<List<Object>> methodParameterNameAndParameterTypes = new ArrayList<List<Object>>();
        // 按顺序存
        for (int i = 0; i < paramTypes.length; i++) {
            methodParameterNameAndParameterType.add(paramNames.get(i));
            methodParameterNameAndParameterType.add(paramTypes[i]);
            methodParameterNameAndParameterTypes.add(methodParameterNameAndParameterType);
        }
        apiRunnable.methodParameterNameAndParameterType = methodParameterNameAndParameterTypes;
        apiRunnableHashMap.put(apiMethodMapping.value(), apiRunnable);
        log.info("『{}』方法注入进容器中", apiRunnable.apiName);
    }

    /**
     * 查找接口信息
     *
     * @param apiName
     * @return
     */
    public ApiRunnable findApiRunnable(String apiName) {
        return apiRunnableHashMap.get(apiName);
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 定义接口映射关系
     *
     */
    public class ApiRunnable {
        String apiName;
        String targetName;
        Object target;
        Method targetMethod;
        List<List<Object>> methodParameterNameAndParameterType;

        public Object run(Object... args) throws IllegalAccessException, InvocationTargetException {
            if (target == null) {

                target = applicationContext.getBean(targetName);
            }
            // 代理
            return targetMethod.invoke(target, args);
        }

        public Class<?>[] getParamTypes() {
            return targetMethod.getParameterTypes();
        }

        public String getApiName() {
            return apiName;
        }

        public String getTargetName() {
            return targetName;
        }

        public Object getTarget() {
            return target;
        }

        public List<List<Object>> getParameterNameAndParameterType() {
            return methodParameterNameAndParameterType;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }
    }
}
