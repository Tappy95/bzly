package com.mc.bzly.model.platform;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

 
@Alias("RightCollection")
public class RightCollection implements Serializable {

     
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String url;

    private Integer parentId;
    
    private Integer type;

    private Integer sonCount;
    
    private String icon;

    private String fileName;

    private List<RightCollection> rightCollections;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<RightCollection> getRightCollections() {
		return rightCollections;
	}

	public void setRightCollections(List<RightCollection> rightCollections) {
		this.rightCollections = rightCollections;
	}

	public Integer getSonCount() {
		return sonCount;
	}

	public void setSonCount(Integer sonCount) {
		this.sonCount = sonCount;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
