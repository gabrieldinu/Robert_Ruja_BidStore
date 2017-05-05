package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.frontend.mvc.managed.Paginator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

/**
 * Created by robert.ruja on 28-Apr-17.
 */

@ManagedBean(name = "categoryView")
@ViewScoped
public class CategoryView implements Serializable {

    private TreeNode root;

    private List<Category> breadcrumbs;

    private List<Category> allCategories;

    private List<TreeNode> allNodeCategories;

    private String searchText;

    private Paginator paginator = new Paginator();

    @ManagedProperty(value = "#{contentView}")
    private ContentView contentView;

    @PostConstruct
    public void init(){
        allCategories = populate();
        buildTree();
    }

    public void updateCategoryView(Category category){
        contentView.setSelectedCategory(category);
        contentView.renderItemList();
        updateBreadcrumbs(category);
        buildTree();
    }

    private void updateBreadcrumbs(Category category) {
        breadcrumbs = new ArrayList<>();
        breadcrumbs.add(category);
        Category current = category;
        for(int i = allCategories.size() - 1; i >= 0; i--){
            Category searchCategory = allCategories.get(i);
            if(searchCategory.getId() != null && searchCategory.getId().equals(current.getParentId())){
                breadcrumbs.add(searchCategory);
                current = searchCategory;
            }
        }
        Collections.reverse(breadcrumbs);
    }

    private void buildTree(){

        allNodeCategories = new ArrayList<>();
        for(Category category: allCategories){
            TreeNode node = new DefaultTreeNode(category);
            node.setSelectable(true);
            allNodeCategories.add(node);
        }
        root = new DefaultTreeNode();
        root.setSelectable(true);
        for( int i = 0; i < allNodeCategories.size(); i++ ) {

            TreeNode currentNode = allNodeCategories.get(i);
            Category currentCategory = (Category)currentNode.getData();

            TreeNode searchNode;
            Category searchCategory;

            //search for direct children
            for( int j = i+1; j < allNodeCategories.size(); j++ ){
                searchNode = allNodeCategories.get(j);
                searchCategory = (Category) searchNode.getData();
                if(searchCategory.getParentId() != null && currentCategory.getId().equals(searchCategory.getParentId()))
                    //we have a child
                    currentNode.getChildren().add(searchNode);
                    //searchNode.setParent(currentNode);
            }
            //TODO: try to make expand to work
            //expand all parents if current node is selected node
            if(currentCategory.equals(contentView.getSelectedCategory())) {
                expand(currentNode);
                currentNode.setSelected(true);
            }
            //if is root category, add to tree
            if(currentCategory.getLevel() == 0) root.getChildren().add(currentNode);

        }
    }

    private void expand(TreeNode treeNode){
        if (treeNode.getParent()!=null){
            treeNode.getParent().setExpanded(true);
            expand(treeNode.getParent());
        }
    }

    public List<String> completeText(String query) {

        //query in db should be done
        List<String> result = new ArrayList<>();
        int i = 0;

        for(Category category: allCategories){
            if(category.getName().toLowerCase().contains(query.toLowerCase())){
                result.add(category.getName());
                i++;
            }
            if(i>6)break;
        }
        return result;
    }

    public List<Category> getCategoryList() {

        List<Category> results = new ArrayList<>();
        if(searchText != null && !searchText.isEmpty()){
            for(Category searchCategory: allCategories){
                if(searchCategory.getName().toLowerCase().contains(searchText.toLowerCase()))
                    results.add(searchCategory);
            }
        }
        paginator.setItemCount(results.size());
        paginator.calculate();
        return results.subList(paginator.getStartIndex(),paginator.getEndIndex());
    }

    public static List<Category> populate(){
        List<Category> list = new ArrayList<>();
        list.add(new Category("Desktop PC",null, 0L,"Some long description here",0));
        list.add(new Category("PC Components",null, 1L,"Some long description here",0));
        list.add(new Category("Laptops",null, 2L,"Some long description here",0));
        list.add(new Category("Laptop Accessiories",null, 3L,"Some long description here",0));
        list.add(new Category("CPUs",1L, 4L, "Cpu is a desktop pc component",1));
        list.add(new Category("Motherboards",1L, 5L, "Motherboard is a desktop pc component",1));
        list.add(new Category("AMD",4L,6L,"Amd cpus",2));
        list.add(new Category("Intel",4L,7L,"Intel cpus",2));
        list.add(new Category("Socket 1150",7L,8L,"Intel CPUs socket 1150",3));
        list.add(new Category("Socket FM2",6L,9L,"AMD CPUs socket FM2",3));
        list.add(new Category("Socket AM3+",6L,10L,"AMD CPUs socket AM3+",3));
        list.add(new Category("Cc1",0L,11L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,12L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,13L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,14L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,15L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,16L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,17L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,18L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,19L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,20L,"Description of cc1 ... ccn",1));
        list.add(new Category("Cc1",0L,21L,"Description of cc1 ... ccn",1));
        Collections.sort(list, (o1,o2) -> {
                return o1.getLevel().compareTo(o2.getLevel());
        });
        return list;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<Category> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Category> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public List<TreeNode> getAllNodeCategories() {
        return allNodeCategories;
    }

    public void setAllNodeCategories(List<TreeNode> allNodeCategories) {
        this.allNodeCategories = allNodeCategories;
    }

    public ContentView getContentView() {
        return contentView;
    }

    public void setContentView(ContentView contentView) {
        this.contentView = contentView;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
}
