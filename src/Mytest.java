

public class Mytest {
    public static void main(String[] args) {
        // final String str1 = "str";
        // final String str2 = "ing";
        // String c = "str" + "ing";// 常量池中的对象
        // String d = str1 + str2; // 在堆上创建的新的对象
        // System.out.println(c == d);// false

        int i = 0;
        i ++ ;
        i+=1;
        
        
        // Address address = new Address();
        // address.setName("杭州");

        // Person person1 = new Person();
        // person1.setId(1);
        // person1.setGender("男");
        // person1.setAddress(address);

        // Person person2 = person1.clone();
        // System.out.println(person1 == person2); // false
        // System.out.println(person1.getId() == person2.getId()); // true
        // System.out.println(person1.getAddress() == person2.getAddress()); // true
        // System.out.println(person1.getAddress().getName() == person2.getAddress().getName()); // true
        // address.setName("北京");
        // System.out.println(person2.getAddress().getName()); // 北京
        // System.out.println(person1.getAddress().getName()); // 北京
        // System.out.println(person1.getAddress().getName() == person2.getAddress().getName()); // true

        // System.out.println(person1.getGender() == person2.getGender()); // true
        // person1.setGender("女");
        // System.out.println(person1.getGender() == person2.getGender()); // false


        // BigDecimal a = new BigDecimal("1.0");
        // BigDecimal b = new BigDecimal("0.9");
        // BigDecimal c = new BigDecimal("0.8");

        // BigDecimal x = a.subtract(b);
        // BigDecimal y = b.subtract(c);

        // System.out.println(x); /* 0.1 */
        // System.out.println(y); /* 0.1 */
        // System.out.println(Objects.equals(x, y)); /* true */
        // System.out.println(x == y); /* false */



        // char a = ''; //error

    }
    public static String getStr() {
        return "ing";
    }
}
 class Address implements Cloneable{
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    
    // 省略构造函数、Getter&Setter方法
    @Override
    public Address clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

 class Person implements Cloneable {
    private int id;
    private String gender;
    private Address address;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Person clone() {
        try {
            Person person = (Person) super.clone();
            return person;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
