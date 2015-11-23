package com.hotel.common.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hotel.model.User;

public final class EndecryptUtils {
	/**
     * 对密码进行md5加密,并返回密文和salt，包含在User对象中
     * @param username 用户名
     * @param password 密码
     * @return 密文和salt
     */
    public static User md5Password(String username,String password){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username),"username不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password),"password不能为空");
        SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator();
        String salt= secureRandomNumberGenerator.nextBytes().toHex();
        //组合username,两次迭代，对密码进行加密
        String password_cipherText= new Md5Hash(password,username+salt,2).toHex();
        User user=new User();
        user.setPsd(password_cipherText);
        user.setSalt(salt);
        user.setName(username);
        return user;
    }
    /**
     * 通过username,password,salt,校验密文是否匹配 ，校验规则其实在配置文件中，这里为了清晰，写下来
     * @param username 用户名
     * @param password 原密码
     * @param salt  盐
     * @param md5cipherText 密文
     * @return
     */
    public static  boolean checkMd5Password(String username,String password,String salt,String md5cipherText)
    {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username),"username不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password),"password不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(md5cipherText),"md5cipherText不能为空");
        //组合username,两次迭代，对密码进行加密
        String password_cipherText= new Md5Hash(password,username+salt,2).toHex();
        return md5cipherText.equals(password_cipherText);
    }
}
