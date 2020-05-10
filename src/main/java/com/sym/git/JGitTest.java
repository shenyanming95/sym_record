package com.sym.git;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

/**
 * @author ym.shen
 * @date 2020/5/2 23:54.
 */

public class JGitTest {

    @Test
    public void test01() throws GitAPIException {
        String dirPath = "";
        String username = "";
        String password = "";
        GitUtil.setCredentials(username, password);
        boolean result = GitUtil.pull(dirPath);
        System.out.println(result);
    }

}
