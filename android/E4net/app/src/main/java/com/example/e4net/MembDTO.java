package com.example.e4net;

import com.google.gson.annotations.SerializedName;

public class MembDTO {
    //    {
    //        "membId": "id123",
    //        "membNm": "1234"
    //    }
    //  =>  위 json 파일형식을 api요청하여 받아온다면 이에 해당하는 변수를 대응시켜 만들어줘야함

    @SerializedName("membSn")
    private Long membSn;		// 회원번호
    private String membCls;  	// 회원구분 - ROLE_ADMIN:어드민, ROLE_SELLER:판매자, ROLE_USER: 사용자
    private String membStatusCd;// 회원상태코드 - 10:가입, 20:휴면, 99:탈퇴
    @SerializedName("membId")
    private String membId;		// 회원 ID
    private String membPwd;		// 회원 PWD
    private String membNm;		// 회원성명
    private String mobileNo;	// 휴대폰번호
    private String emailAddr;	// 이메일주소
    private String zipCd;		// 우편번호
    private String zipAddr;		// 우편번호주소
    private String detailAddr;	// 상세주소
    private String lastLoginDtm;// 최종 로그인 일시
//    private Money membMoney;	// 회원 머니 entity
    private String useYn;
    private Long frstRegistMembSn;
    private String frstRegistDt;
    private Long lastRegistMembSn;
    private String lastChangeDt;

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

    public String getMembStatusCd() {
        return membStatusCd;
    }

    public void setMembStatusCd(String membStatusCd) {
        this.membStatusCd = membStatusCd;
    }

    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public String getMembPwd() {
        return membPwd;
    }

    public void setMembPwd(String membPwd) {
        this.membPwd = membPwd;
    }

    public String getMembNm() {
        return membNm;
    }

    public void setMembNm(String membNm) {
        this.membNm = membNm;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public String getZipAddr() {
        return zipAddr;
    }

    public void setZipAddr(String zipAddr) {
        this.zipAddr = zipAddr;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getLastLoginDtm() {
        return lastLoginDtm;
    }

    public void setLastLoginDtm(String lastLoginDtm) {
        this.lastLoginDtm = lastLoginDtm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public Long getFrstRegistMembSn() {
        return frstRegistMembSn;
    }

    public void setFrstRegistMembSn(Long frstRegistMembSn) {
        this.frstRegistMembSn = frstRegistMembSn;
    }

    public String getFrstRegistDt() {
        return frstRegistDt;
    }

    public void setFrstRegistDt(String frstRegistDt) {
        this.frstRegistDt = frstRegistDt;
    }

    public Long getLastRegistMembSn() {
        return lastRegistMembSn;
    }

    public void setLastRegistMembSn(Long lastRegistMembSn) {
        this.lastRegistMembSn = lastRegistMembSn;
    }

    public String getLastChangeDt() {
        return lastChangeDt;
    }

    public void setLastChangeDt(String lastChangeDt) {
        this.lastChangeDt = lastChangeDt;
    }

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "MembDTO{" +
                "membSn=" + membSn +
                ", membCls=" + membCls +
                ", membStatusCd=" + membStatusCd +
                ", membId=" + membId +
                ", membPwd=" + membPwd +
                ", membNm=" + membNm +
                ", mobileNo=" + mobileNo +
                ", emailAddr=" + emailAddr +
                ", zipCd=" + zipCd +
                ", zipAddr=" + zipAddr +
                ", detailAddr=" + detailAddr +
                ", lastLoginDtm=" + lastLoginDtm +
                ", useYn=" + useYn +
                ", frstRegistMembSn=" + frstRegistMembSn +
                ", frstRegistDt=" + frstRegistDt +
                ", lastRegistMembSn=" + lastRegistMembSn +
                ", lastChangeDt=" + lastChangeDt +
//                ", title='" + title + "'\n" +
//                ", bodyValue='" + bodyValue + "'\n" +
                "}";
    }




}
