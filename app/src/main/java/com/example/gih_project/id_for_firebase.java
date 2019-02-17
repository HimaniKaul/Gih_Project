package com.example.gih_project;

public class id_for_firebase {
        String product_id;
        String product_title;

        public id_for_firebase(){}

        public id_for_firebase(String product_id, String product_title) {
            this.product_id = product_id;
            this.product_title= product_title;
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
    }
