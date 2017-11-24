package com.example.demo.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 通过调用context.setSessionCreationEnabled(false)表示不创建会话；如果之后调用Subject.getSession()将抛出DisabledSessionException异常。
 * Created by zxn on 2017/11/23.
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
