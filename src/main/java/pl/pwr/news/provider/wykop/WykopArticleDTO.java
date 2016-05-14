package pl.pwr.news.provider.wykop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Generated("org.jsonschema2pojo")
public class WykopArticleDTO {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("tags")
    @Expose
    public String tags;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("source_url")
    @Expose
    public String sourceUrl;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("comment_count")
    @Expose
    public Integer commentCount;
    @SerializedName("report_count")
    @Expose
    public Integer reportCount;
    @SerializedName("related_count")
    @Expose
    public Integer relatedCount;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("author_group")
    @Expose
    public Integer authorGroup;
    @SerializedName("author_avatar")
    @Expose
    public String authorAvatar;
    @SerializedName("author_avatar_big")
    @Expose
    public String authorAvatarBig;
    @SerializedName("author_avatar_med")
    @Expose
    public String authorAvatarMed;
    @SerializedName("author_avatar_lo")
    @Expose
    public String authorAvatarLo;
    @SerializedName("author_sex")
    @Expose
    public String authorSex;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("group")
    @Expose
    public String group;
    @SerializedName("preview")
    @Expose
    public String preview;
    @SerializedName("user_vote")
    @Expose
    public Boolean userVote;
    @SerializedName("user_favorite")
    @Expose
    public Boolean userFavorite;
    @SerializedName("user_observe")
    @Expose
    public Boolean userObserve;
    @SerializedName("user_lists")
    @Expose
    public List<Object> userLists = new ArrayList<Object>();
    @SerializedName("plus18")
    @Expose
    public Boolean plus18;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("can_vote")
    @Expose
    public Boolean canVote;
    @SerializedName("has_own_content")
    @Expose
    public Boolean hasOwnContent;
    @SerializedName("is_hot")
    @Expose
    public Boolean isHot;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("violation_url")
    @Expose
    public String violationUrl;
    @SerializedName("info")
    @Expose
    public String info;
    @SerializedName("app")
    @Expose
    public String app;

}