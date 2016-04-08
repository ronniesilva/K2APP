package com.app.k2app.pojo;

public class PojoFeedItem {

    private Integer Id;
    private String UserImage;
    private String ShowName;
    private String Dist;
    private Integer OldTime;
    private String ImageUrl;
    private String Text;
    private Integer QtdLike;
    private Integer LikeStatus;

    //GETTERS AND SETTERS


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getShowName() {
        return ShowName;
    }

    public void setShowName(String showName) {
        ShowName = showName;
    }

    public String getDist() {
        return Dist;
    }

    public void setDist(String dist) {
        Dist = dist;
    }

    public Integer getOldTime() {
        return OldTime;
    }

    public void setOldTime(Integer oldTime) {
        OldTime = oldTime;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Integer getQtdLike() {
        return QtdLike;
    }

    public void setQtdLike(Integer qtdLike) {
        QtdLike = qtdLike;
    }

    public Integer getLikeStatus() {
        return LikeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        LikeStatus = likeStatus;
    }
}