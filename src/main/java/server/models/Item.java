package server.models;

/*This class is used to define an item in the system,
 which contain get and set methods for them and constructors*/

public class Item {
    int id;
    int products_id;
    int order_id;
    Product product;

    public Item(int id, int products_id, int order_id) {
        this.id = id;
        this.products_id = products_id;
        this.order_id = order_id;
    }

    public Item() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProducts_id() {
        return products_id;
    }

    public void setProducts_id(int products_id) {
        this.products_id = products_id;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return product;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
