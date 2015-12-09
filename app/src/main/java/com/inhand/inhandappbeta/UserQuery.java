package com.inhand.inhandappbeta;

public class UserQuery {

        private int _id;
        private String _searchString;


        public UserQuery() { }

        public UserQuery(String _searchString) { this._searchString = _searchString; }

        public int get_id() { return _id; }

        public void set_id(int _id) { this._id = _id; }

        public String get_searchString() { return _searchString; }

        public void set_searchString(String _productname) { this._searchString = _searchString; }

}
