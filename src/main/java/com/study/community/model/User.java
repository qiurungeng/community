package com.study.community.model;

import lombok.Data;

@Data
public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.ID
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.ACCOUNT
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.NAME
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.TOKEN
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.GMT_CREATE
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.GMT_MODIFIED
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.BIO
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.AVATAR_URL
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    private String avatarUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.ID
     *
     * @return the value of USER.ID
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.ID
     *
     * @param id the value for USER.ID
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.ACCOUNT
     *
     * @return the value of USER.ACCOUNT
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.ACCOUNT
     *
     * @param account the value for USER.ACCOUNT
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.NAME
     *
     * @return the value of USER.NAME
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.NAME
     *
     * @param name the value for USER.NAME
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.TOKEN
     *
     * @return the value of USER.TOKEN
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.TOKEN
     *
     * @param token the value for USER.TOKEN
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.GMT_CREATE
     *
     * @return the value of USER.GMT_CREATE
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.GMT_CREATE
     *
     * @param gmtCreate the value for USER.GMT_CREATE
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.GMT_MODIFIED
     *
     * @return the value of USER.GMT_MODIFIED
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.GMT_MODIFIED
     *
     * @param gmtModified the value for USER.GMT_MODIFIED
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.BIO
     *
     * @return the value of USER.BIO
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.BIO
     *
     * @param bio the value for USER.BIO
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.AVATAR_URL
     *
     * @return the value of USER.AVATAR_URL
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.AVATAR_URL
     *
     * @param avatarUrl the value for USER.AVATAR_URL
     *
     * @mbg.generated Sun Oct 20 13:07:16 CST 2019
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}