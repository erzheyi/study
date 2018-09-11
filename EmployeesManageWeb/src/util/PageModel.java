package util;

import java.util.List;

import entity.Employee;

public class PageModel<T> {

	private int pageNumber;
	private int currPage;
	private int pages;
	private int count;
	private List<T> list;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getCount() {
		return count;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int nextPage() {
		return (currPage + 1) > pages ? pages : (currPage + 1);
	}

	public int prevPage() {
		return (currPage - 1) < 1 ? 1 : (currPage - 1);
	}

	public int getBeginPage() {
		int beginPage = 0;
		if (pages < pageNumber) {
			beginPage = 1;
		} else if (currPage <= pageNumber / 2) {
			beginPage = 1;
		} else if (currPage > pages - pageNumber / 2) {
			beginPage = pages - (pageNumber - 1);
		} else {
			if (pageNumber % 2 == 0) {
				beginPage = currPage - pageNumber / 2 + 1;
			}else {
				beginPage = currPage - pageNumber / 2;
			}
		}
		return beginPage;
	}
	
	public int getEndPage() {
		int endPage = 0;
		if (pages < pageNumber) {
			endPage = pages;
		} else if (currPage <= pageNumber / 2) {
			endPage = pageNumber;
		} else if (currPage > pages - pageNumber / 2) {
			endPage = pages;
		} else {
			endPage = currPage + pageNumber / 2;
		}
		return endPage;
	}
	
	public String getPagination() {
		StringBuffer sb = new StringBuffer();
		if (pages < pageNumber) {
			for (int i = 1; i <= pages; i++) {
				if (i == currPage) {
					sb.append("<li class='active'><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				} else {
					sb.append("<li><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				}
				sb.append(" ");
			}
		} else if (currPage <= pageNumber / 2) {
			for (int i = 1; i <= pageNumber; i++) {
				if (i == currPage) {
					sb.append("<li class='active'><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				} else {
					sb.append("<li><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				}
				sb.append(" ");
			}
		} else if (currPage > pages - pageNumber / 2) {
			for (int i = pages - (pageNumber - 1); i <= pages; i++) {
				if (i == currPage) {
					sb.append("<li class='active'><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				} else {
					sb.append("<li><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
				}
				sb.append(" ");
			}
		} else {
			if (pageNumber % 2 == 0) {
				for (int i = currPage - pageNumber / 2 + 1; i <= currPage + pageNumber / 2; i++) {
					if (i == currPage) {
						sb.append("<li class='active'><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
					} else {
						sb.append("<li><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
					}
					sb.append(" ");
				}
			}else {
				for (int i = currPage - pageNumber / 2; i <= currPage + pageNumber / 2; i++) {
					if (i == currPage) {
						sb.append("<li class='active'><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
					} else {
						sb.append("<li><a href='employee?type=show&page=" + i + "'>" + i + "</a></li>");
					}
					sb.append(" ");
				}
			}
		}
		return sb.toString();
	}

}
