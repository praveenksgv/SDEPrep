import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class CouponManagerDemo {
    public static void main() {
        Coupon c = new Coupon("abc");
        System.out.println(c.id);


    }
}


/*

Coupon
- code
- value
- maxNoOfUsage
- DiscountType
- redeemedUserIds => set


Order
- id
- products
- totalBeforeDiscount
- finalTotal
- coupon

User
- email
- name


Product
- id
- name
- price

CouponManager(singleton class)
- instance
- coupons => hashmap
-


DiscountType
- PERCENTAGE
- FLAT
 */


class User {
    private String email;
    private String name;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Product {
    private final String id;
    private String name;
    private Integer price;

    public Product(String id, String name, Integer price) {
        this.id = String.valueOf(UUID.randomUUID());
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

enum DiscountType {
    PERCENTAGE,
    FLAT
}



class Order{
    private final String id;
    private Integer totalBeforeDiscount;
    private Integer finalTotal;
    private Coupon coupon;
    private List<Product> products;

    public Order(List<Product> products) {
        this.id = String.valueOf(UUID.randomUUID());
        this.coupon = null;
        this.products = products;
        totalBeforeDiscount = 0;

        for(Product product : products){
            totalBeforeDiscount += product.getPrice();
        }

        finalTotal = totalBeforeDiscount;
    }
}

Coupon
- code
- value
- maxNoOfUsage
- DiscountType
- redeemedUserIds => set


class Coupon {
    private final String id;
    private Integer value;
    private Integer maxNoOfUsage;
    private DiscountType discountType;
    private Set<String> redeemedUserIds;

    public Coupon(String id, Integer value, Integer maxNoOfUsage, DiscountType discountType) {
        this.id = id;
        this.value = value;
        this.maxNoOfUsage = maxNoOfUsage;
        this.discountType = discountType;
        this.redeemedUserIds = new CopyOnWriteArraySet<String>();
    }

    public boolean validate(){

    }
}