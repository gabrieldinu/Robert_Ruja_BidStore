package ro.fortech.application.bidstore.frontend.mvc.managed;

public class Paginator {

    private int pageSize = 10;
    private int pageCount;
    private int page = 1;
    private String sortBy;
    private boolean ascending;
    private int startIndex;
    private int endIndex;
    private int itemCount;


    public void calculate() {
        int count = itemCount / pageSize;
        int remainder = itemCount % pageSize;
        pageCount = remainder == 0 ? count:count+1;
        page = pageCount > page ? page : pageCount == 0 ? 1 : pageCount;
        startIndex = (page-1)*pageSize;
        endIndex = startIndex + pageSize < itemCount ? startIndex + pageSize : startIndex + remainder;

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
