package com.app.k2app.pojo;

public class PojoContactsItem {

    private Integer Id;
    private String ImageUrl;
    private String ShowName;
    private String MsgStatus;

    // GETTERS AND SETTERS


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getShowName() {
        return ShowName;
    }

    public void setShowName(String showName) {
        ShowName = showName;
    }

    public String getMsgStatus() {
        return MsgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        MsgStatus = msgStatus;
    }
}