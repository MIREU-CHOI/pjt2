package com.example.e4net;

public class TokenDTO {

    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

    private Long membSn;	// 회원번호
    private String membCls; // 회원구분 - ROLE_ADMIN:어드민, ROLE_SELLER:판매자, ROLE_USER: 사용자
    private String membId;	// 회원 ID

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(Long tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public Long getMembSn() {
        return membSn;
    }

    public void setMembSn(Long membSn) {
        this.membSn = membSn;
    }

    public String getMembCls() {
        return membCls;
    }

    public void setMembCls(String membCls) {
        this.membCls = membCls;
    }

    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }
}
