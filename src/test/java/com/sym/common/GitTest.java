package com.sym.common;

import com.sym.git.BatchGitUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.File;

/**
 * jGit的测试
 *
 * @author ym.shen
 * @date 2020/5/2 23:54.
 */
public class GitTest {

    /**
     * 原生git的操作
     */
    @Test
    public void test01() throws GitAPIException {
        // 初始化一个git仓库
        Git git = Git.init().setDirectory(new File("")).call();
        // 剩下的全是git命令相应的操作命令
    }

    /**
     * 批量git操作
     */
    @Test
    public void test02() {
        BatchGitUtil.pull("");
    }

    /**
     * 批量git操作
     */
    @Test
    public void test03() {
        BatchGitUtil.clone("/property/git.txt", "/Users/test");
    }

    /**
     * 批量git操作
     */
    @Test
    public void test04() {
        BatchGitUtil.commit("", "commit_message");
    }

    /**
     * 批量git操作
     */
    @Test
    public void test05() {
        BatchGitUtil.push("");
    }
}
