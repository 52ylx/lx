package com.gitee.ylx.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "分页工具类")
public class PageResult<T> {
	@ApiModelProperty(value = "总条数")
	private Long total;

	@ApiModelProperty(value = "返回的数据")
	private List<T> rows;

	@ApiModelProperty(value = "第几页",required = true)
	private Integer page;

	@ApiModelProperty(value = "查询条数",required = true)
	private Integer limit;

	public PageResult() {}

	public PageResult(Long total, List<T> rows, Integer page, Integer limit) {
		this.total = total;
		this.rows = rows;
		this.page = page;
		this.limit = limit;
	}

	public PageResult(Integer page, Integer limit) {
		this.page = page;
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}



}
