package server.models;

/*This class is used to define a product in the system,
 which contain get and set methods for them and constructors*/


public class Product {

    private int type;
    private int id;
    private String productName;
    private String productPrice;


    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Product(int type, int id, String productName, String productPrice) {
        this.type = type;
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Product() {

    }
}
