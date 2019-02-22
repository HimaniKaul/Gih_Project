package com.example.gih_project;

public class id_for_firebase {
        String product_id;
        String price;
        String product_title;
        String image_uri;

        public id_for_firebase(){}

        public id_for_firebase(String product_id, String product_title,String price,String image_uri) {
            this.product_id = product_id;
            this.product_title= product_title;
            this.price=price;
            this.image_uri=image_uri;
        }
        public String get_product_id() {
            return  product_id;
        }
        public String get_product_title() {
        return product_title;
       }
        public void set_product_id(String product_id) {
            this.product_id = product_id;
        }
        public void set_product_title(String product_title) {
            this.product_title = product_title;
        }
        public void set_Price(String price) {
        this.price = price;
    }

       public String get_Price() {
           return price;
     }
       public String getImage_uri() {
            return image_uri;

    }
}
