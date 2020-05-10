package com.sym.git;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ym.shen
 * @date 2020/5/3 9:27.
 */
@Slf4j
public class GitUtil {

    private final static String GIT_REPOSITORY_NAME = ".git";
    private static FilenameFilter filenameFilter;
    private static ThreadLocal<CredentialsProvider> credentials;

    static {
        filenameFilter = (dir, name) -> GIT_REPOSITORY_NAME.equals(name);
        credentials = new ThreadLocal<>();
    }

    /**
     * 拉取远程master分支
     * @param path
     * @return
     * @throws GitAPIException
     */
    public static boolean pull(String path) throws GitAPIException {
        checkCredentials();
        path = Objects.requireNonNull(path, "文件路径不能为空");
        File parentDir = new File(path);
        if(!parentDir.isDirectory()){
            log.info("非文件夹: {}", parentDir);
            return false;
        }
        List<Git> gitList = findGitRepository(parentDir);
        if(CollectionUtils.isEmpty(gitList)){
            return false;
        }
        int needCount = gitList.size();
        int concurrentCount = 0;
        List<String> failedFilePathList = new ArrayList<>();
        for (Git git : gitList) {
            try {
                // git仓库地址
                String repositoryPath = git.getRepository().getDirectory().getPath();
                // 拉取结果
                long startTime = System.currentTimeMillis();
                boolean flag = git.pull().setCredentialsProvider(credentials.get()).call().isSuccessful();
                long endTime = System.currentTimeMillis();
                if(flag) {
                    concurrentCount ++;
                    long usedTime = endTime - startTime;
                    log.info("git拉取成功, {} - {} ms", repositoryPath, usedTime);
                }else{
                    failedFilePathList.add(repositoryPath);
                }
            }finally {
                git.close();
            }
        }
        boolean result = concurrentCount == needCount;
        if(!result){
            log.info("拉取失败的仓库：{}", failedFilePathList);
        }
        return result;
    }

    public static void setCredentials(String username, String password){
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        credentials.set(credentialsProvider);
    }

    private static void checkCredentials(){
        Objects.requireNonNull(credentials.get());
    }

    /**
     * 找出父目录地址下的所有git仓库
     * @param parentDir 父目录
     * @return {@link Git}集合
     */
    private static List<Git> findGitRepository(File parentDir) {
        List<File> gitRepositoryList = new ArrayList<>();
        doFind(parentDir, gitRepositoryList);
        log.info("文件目录: {}, 找到git仓库数量: {}", parentDir, gitRepositoryList.size());
        return gitRepositoryList.stream().map(file -> {
            Objects.requireNonNull(file);
            return initGit(file);
        }).collect(Collectors.toList());
    }

    /**
     * 递归查询git仓库目录
     *
     * @param parentDir         父目录地址
     * @param gitRepositoryList 添加集合
     */
    private static void doFind(File parentDir, List<File> gitRepositoryList) {
        if (!parentDir.isDirectory()) {
            return;
        }
        if (isGitRepository(parentDir)) {
            gitRepositoryList.add(parentDir);
            return;
        }
        File[] files = parentDir.listFiles();
        if (null != files) {
            for (File file : files) {
                doFind(file, gitRepositoryList);
            }
        }
    }

    /**
     * 判断一个文件目录是不是git仓库
     *
     * @param file 文件目录
     * @return true表示git仓库
     */
    private static boolean isGitRepository(File file) {
        if (!file.isDirectory()) {
            return false;
        }
        String[] fileNameArray;
        return (fileNameArray = file.list(filenameFilter)) != null && fileNameArray.length > 0;
    }

    /**
     * 初始化 git 仓库
     *
     * @param file 文件夹
     * @return Git
     */
    private static Git initGit(File file) {
        try {
            return Git.open(file);
        } catch (IOException e) {
            log.error("初始化git仓库失败, 原因: {}", e.getMessage());
            throw new RuntimeException("git repository init fail");
        }
    }
}
