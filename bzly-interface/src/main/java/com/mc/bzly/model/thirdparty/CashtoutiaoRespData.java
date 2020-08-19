package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.List;
 
public class CashtoutiaoRespData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long date; 
	private Integer operateLable;
	private String log_id;
	private String channelType;
	private Integer total_comment;
	private String source;
	private String release_state;
	private String title;
	private Long articlePublishTime;
	private String url;
	private String tags;
	private String cover;
	private String retrieve_id;
	private Integer coverType;
	private String exp_id;
	private String strategy_id;
	private String id;
	private String sourceName;
	private Long release_time;
	private List<CashtoutiaoRespCover> covers;
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Integer getOperateLable() {
		return operateLable;
	}
	public void setOperateLable(Integer operateLable) {
		this.operateLable = operateLable;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public Integer getTotal_comment() {
		return total_comment;
	}
	public void setTotal_comment(Integer total_comment) {
		this.total_comment = total_comment;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRelease_state() {
		return release_state;
	}
	public void setRelease_state(String release_state) {
		this.release_state = release_state;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getArticlePublishTime() {
		return articlePublishTime;
	}
	public void setArticlePublishTime(Long articlePublishTime) {
		this.articlePublishTime = articlePublishTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getRetrieve_id() {
		return retrieve_id;
	}
	public void setRetrieve_id(String retrieve_id) {
		this.retrieve_id = retrieve_id;
	}
	public Integer getCoverType() {
		return coverType;
	}
	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}
	public String getExp_id() {
		return exp_id;
	}
	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}
	public String getStrategy_id() {
		return strategy_id;
	}
	public void setStrategy_id(String strategy_id) {
		this.strategy_id = strategy_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public Long getRelease_time() {
		return release_time;
	}
	public void setRelease_time(Long release_time) {
		this.release_time = release_time;
	}
	public List<CashtoutiaoRespCover> getCovers() {
		return covers;
	}
	public void setCovers(List<CashtoutiaoRespCover> covers) {
		this.covers = covers;
	}
	
}
