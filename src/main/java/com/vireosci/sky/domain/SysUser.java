package com.vireosci.sky.domain;

import com.vireosci.sky.common.data.Gender;
import com.vireosci.sky.common.jpa.BaseEntity;
import com.vireosci.sky.common.util.RegexMatchSupport;
import com.vireosci.sky.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

/// 用户
@Entity
@Table(
        name = "sys_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_phone", columnNames = { "phone", "deleted_at" }),
                @UniqueConstraint(name = "uk_email", columnNames = { "email", "deleted_at" }),
                @UniqueConstraint(name = "uk_wechat", columnNames = { "wechat_union_id", "deleted_at" })

        }
)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE sys_user SET deleted_at = now() WHERE id = ?;")
public class SysUser extends BaseEntity implements Principal, CredentialsContainer
{
    /// 数据ID
    @Comment("数据ID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, columnDefinition = "CHAR(36)")
    private String id;

    /// 手机号
    @Comment("手机号")
    @Column(length = 50)
    @Pattern(regexp = RegexMatchSupport.PHONE_REGEX, message = "手机号格式错误")
    private String phone;

    /// 邮箱
    @Comment("邮箱")
    @Column(length = 254)
    @Pattern(regexp = RegexMatchSupport.EMAIL_REGEX, message = "邮箱格式错误")
    private String email;

    /// 密码（使用加密后的密文）
    @Comment("密码（使用加密后的密文）")
    @Column(nullable = false, columnDefinition = "TEXT  ")
    private String password;

    /// 昵称
    @Comment("昵称")
    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^[\\p{L}_0-9@.]+$") // 为了在注册时可以使用邮箱作昵称，这里加上 @ 和 . 字符
    private String nickname;

    /// 用户真实姓名第一个字/词
    @Comment("用户姓名第一个字/词")
    @Column(length = 50)
    @Pattern(regexp = RegexMatchSupport.SYS_USER_NAME_REGEX)
    private String firstName;

    /// 用户真实姓名第二个字/词
    @Comment("用户姓名第二个字/词")
    @Column(length = 50)
    @Pattern(regexp = RegexMatchSupport.SYS_USER_NAME_REGEX)
    private String secondName;

    /// 性别
    @Comment("性别")
    @Column(columnDefinition = "CHAR(1)")
    private Gender gender;

    /// 头像
    @Comment("头像")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;

    /// 微信 UnionId
    @Comment("微信 UnionId")
    @Column(columnDefinition = "CHAR(28)")
    private String wechatUnionId;

    /// 返回用户的昵称 [#nickname]
    @Override public String getName() { return nickname; }

    /// 擦除敏感信息
    @Override public void eraseCredentials() { password = null; }

    @AssertTrue(message = "手机号和邮箱至少存储一个")
    public boolean hasValidPrincipal() { return StringUtils.notBlank(phone) || StringUtils.notBlank(email); }

    /// 根据 `principal` 匹配结果设置用户信息：
    ///
    /// 匹配以下内容：
    /// - 手机号
    /// - 邮箱
    ///
    /// 若匹配成功至少一种，同时将 `principal` 设为用户昵称 [#nickname]
    ///
    /// **该方法不检测数据有效性！默认传入的参数一定匹配成功！**
    public void setPrincipal(String principal)
    {
        if (RegexMatchSupport.isEmail(principal))
            setEmail(principal);
        else if (RegexMatchSupport.isPhone(principal))
            setPhone(principal);

        nickname = principal;
    }

    /// 将密码加密后设置 [#password] 值
    public void encodePassword(String password, PasswordEncoder encoder) { this.password = encoder.encode(password); }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }

    public void setSecondName(String secondName) { this.secondName = secondName; }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public byte[] getAvatar() { return avatar; }

    public void setAvatar(byte[] avatar) { this.avatar = avatar; }

    public String getWechatUnionId() { return wechatUnionId; }

    public void setWechatUnionId(String wechatUnionId) { this.wechatUnionId = wechatUnionId; }
}
