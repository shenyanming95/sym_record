package com.sym.git;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shenyanming
 * Created on 2020/6/1 18:32
 */
@Slf4j
public class BatchGits{
    private static String GIT_REPOSITORY_NAME = ".git";
    private static String DIR_LIMITER = "/";
    private static FilenameFilter filenameFilter;
    private static CredentialsProvider credentialsProvider;

    static {
        filenameFilter = (dir, name) -> GIT_REPOSITORY_NAME.equals(name);
        // 预先加载配置文件
        readProperties("/property/git.properties");
    }

    public void setCredentials(String username, String password){
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
    }

    public void setCredentialsFilePath(String filePath){
        readProperties(filePath);
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

    private static void readProperties(String path){
        InputStream inputStream = BatchGits.class.getResourceAsStream(path);
        if(null == inputStream){
            log.warn("未找到配置文件, 无法加载账户信息");
            return;
        }
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        } catch (IOException e) {
            log.error("加载配置文件[{}]失败, 原因: {}", path, e);
        }
    }

    private static void runCommand(String path, Function<Git, Boolean> doCommand){
        File parentDir = new File(path);
        if(!parentDir.isDirectory()){
            log.info("非文件夹: {}", parentDir);
            return;
        }
        List<Git> gitList = findGitRepository(parentDir);
        if(CollectionUtils.isEmpty(gitList)){
            log.info("当前目录[{}]下未找到任何git仓库", path);
            return;
        }
        List<FailureInfo> failureInfoList = new ArrayList<>();
        gitList.forEach(git -> {
            // git仓库地址
            String repositoryPath = git.getRepository().getDirectory().getPath();
            // 错误信息
            String errorMsg = null;
            boolean isSuccess = false;

            long startTime = System.currentTimeMillis();
            try {
                isSuccess = doCommand.apply(git);
            } catch (Exception e) {
                errorMsg = e.getMessage();
            } finally {
                git.close();
            }
            long endTime = System.currentTimeMillis();
            if(null != errorMsg || !isSuccess){
                failureInfoList.add(FailureInfo.of(repositoryPath, errorMsg));
            }else{
                long usedTime = endTime - startTime;
                log.info("git拉取成功, {} - {} ms", repositoryPath, usedTime);
            }
        });
        if(!failureInfoList.isEmpty()){
            log.error("操作失败的git仓库信息：{}", failureInfoList);
        }
    }

    public static void clone(String filePath, String dirPath){
        if(filePath.indexOf(DIR_LIMITER) != 0){
            filePath = (DIR_LIMITER + filePath);
        }
        InputStream inputStream = BatchGits.class.getResourceAsStream(filePath);
        if(inputStream == null){
            log.error("指定的文件不存在：{}", filePath);
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> urls = new ArrayList<>();
        String url;
        try {
            while((url = reader.readLine()) != null){
                urls.add(url);
            }
        }catch (Exception e){
            log.error("读取失败失败,", e);
        }
        if(urls.isEmpty()){
            log.warn("未加载到git仓库地址");
            return;
        }
        clone(urls, dirPath);
    }

    public static void clone(List<String> urls, String dirPath) {

        if(dirPath.lastIndexOf(DIR_LIMITER) == -1){
            dirPath += DIR_LIMITER;
        }
        CloneCommand cloneCommand = Git.cloneRepository();
        List<FailureInfo> failureInfoList = new ArrayList<>();
        for (String url : urls) {
            try {
                String dirName = url.substring(url.lastIndexOf(DIR_LIMITER), url.indexOf(GIT_REPOSITORY_NAME));
                cloneCommand.setRemote("origin")
                        .setBranch("master")
                        .setURI(url)
                        .setCredentialsProvider(credentialsProvider)
                        .setDirectory(new File(dirPath + dirName))
                        .setCloneSubmodules(true)
                        .call();
            }catch (Exception e){
                // 仓库克隆失败, 不要影响到其它仓库克隆
                failureInfoList.add(FailureInfo.of(url, e.getMessage()));
            }
        }
        if(failureInfoList.isEmpty()){
            log.info("克隆git仓库成功, 共载入{}个新仓库", urls.size());
        }else{
            log.error("载入失败的git仓库信息：{}", failureInfoList);
        }
    }

    public static void pull(String path) {
        runCommand(path, git -> {
            try {
                PullResult pullResult = git.pull().setCredentialsProvider(credentialsProvider).call();
                return pullResult.isSuccessful();
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void commit(String path, String message) {
        runCommand(path, git -> {
            try {
                git.commit().setMessage(message).call();
                return true;
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void push(String path) {
        runCommand(path, git -> {
            try {
                git.push().setCredentialsProvider(credentialsProvider).call();
                return true;
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Data
    @ToString
    @Accessors(chain = true)
    public static class FailureInfo {
        private String path;
        private String reason;
        private FailureInfo(String path, String reason){
            this.path = path;
            this.reason = reason;
        }
        public static FailureInfo of(String path, String reason){
            return new FailureInfo(path, reason);
        }
    }
}
