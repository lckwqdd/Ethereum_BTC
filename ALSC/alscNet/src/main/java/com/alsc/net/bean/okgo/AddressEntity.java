package com.alsc.net.bean.okgo;


import java.io.Serializable;
import java.util.List;

/**
 * Created by WuQuan on 2019/12/28.
 */

public class AddressEntity implements Serializable {

    /**
     * data : {"total_count":1877,"page":1,"pagesize":50,"list":[{"tx_hash":"26c4fe0a07794cebcb4e9b90878726054de13592023087597436dc432bff61f6","tx_output_n":0,"tx_output_n2":0,"value":144022,"confirmations":12773},{"tx_hash":"33b42f6fd9b67c3a502fa355a47465458b688fefe71ad831c4cba149a5ef17e6","tx_output_n":0,"tx_output_n2":0,"value":146482,"confirmations":8973},{"tx_hash":"9a13436b7f0af2efd8ed5fd14c11cdcfd093d2ddcae87c771cc8c70569640d8c","tx_output_n":0,"tx_output_n2":0,"value":1407,"confirmations":8541},{"tx_hash":"13558bf80875c99f30cac289fc7597d83b25f7aed9223032d7e31707e39291e3","tx_output_n":0,"tx_output_n2":0,"value":1102,"confirmations":8538},{"tx_hash":"59d3cf821a016579cb97fb5cd9025f74864b3fcb9b1bc999797e5e16b072a8ef","tx_output_n":0,"tx_output_n2":0,"value":26786,"confirmations":7464},{"tx_hash":"445cad5ba4224abd00a1bab05f6e9f5864164ab628a432121a9437290f7024cc","tx_output_n":1,"tx_output_n2":0,"value":7420,"confirmations":6023},{"tx_hash":"7ad4ac13a41fa088526c3517a484201032033b39bbfcd4dbd6d9635b73fd212b","tx_output_n":53,"tx_output_n2":0,"value":50000,"confirmations":5700},{"tx_hash":"d7a30cfca782063abe76b4f7ce54b388dadae4359971cf3619625d919b32709e","tx_output_n":0,"tx_output_n2":0,"value":1000,"confirmations":5025},{"tx_hash":"a45cfbc577d67c21033058772fd6460a5e856a79e9aba7384475c3e4bc929291","tx_output_n":0,"tx_output_n2":0,"value":5500,"confirmations":923},{"tx_hash":"9fe22e38dbb7f1d812ae61114876f794e60004e5edccc4abc968320b7ceb5334","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":720},{"tx_hash":"facbdf8299361fb5f6c2685cec33668a90f431f060261ea4951187d4433d73b9","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":542},{"tx_hash":"90ef38142258213a1b4a062ff48beb1fe2a3d138d59255b9543075566989a81f","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":485},{"tx_hash":"182d07b0d8815b7d8c55884af4d5a8c7578e18f1150cc1e92b14bca0516d8a11","tx_output_n":0,"tx_output_n2":0,"value":1254924235,"confirmations":94},{"tx_hash":"4ef0347278276c68cb1326b4c51ceef3b69ed3274240ef981932a089502609fe","tx_output_n":0,"tx_output_n2":0,"value":1251643571,"confirmations":92},{"tx_hash":"b2b51791bfb06d4520196ae77fdf86245234ccfb6dab72b7161b7b5e1efdf2eb","tx_output_n":0,"tx_output_n2":0,"value":1257767272,"confirmations":90},{"tx_hash":"cea904be1b3b78cea8f29a32f463a271afcee96a7901370c02e588c681dfb3d3","tx_output_n":0,"tx_output_n2":0,"value":1261909345,"confirmations":83},{"tx_hash":"cc9839bda9c77d98981cbc87b4352e3cfa1de029d8ea5dbd1836da8ffb1e0553","tx_output_n":0,"tx_output_n2":0,"value":1264004681,"confirmations":82},{"tx_hash":"1fd9a7adf86eb0c8be65c2b0089ba93ca86bc7bed0952fa68093950c7a820ecc","tx_output_n":0,"tx_output_n2":0,"value":1259761040,"confirmations":81},{"tx_hash":"be042d5383976ac595d0479b6ab164f9d48399aab583e1b9de2c1fd0c9d3c360","tx_output_n":0,"tx_output_n2":0,"value":1280014722,"confirmations":75},{"tx_hash":"24a77ce6b8fa7d6467bc51eec086aea6ba49ea3a723de3d72e551e3b2f1e510d","tx_output_n":0,"tx_output_n2":0,"value":1252423493,"confirmations":58},{"tx_hash":"b32286348318dc944daf4a2537c9f2d21b1c951a2df8e8e08788cd1f81e9afe0","tx_output_n":0,"tx_output_n2":0,"value":1252247560,"confirmations":55},{"tx_hash":"4fe5ea0973658d6470e8419b146fadead4de6bdd907a431d3fa38a609e2f3175","tx_output_n":0,"tx_output_n2":0,"value":1252724261,"confirmations":53},{"tx_hash":"0aec781baaa210b15d812ca62ba2d5c9534b0fa2337262ec7e2e162e499db01c","tx_output_n":0,"tx_output_n2":0,"value":1259530441,"confirmations":52},{"tx_hash":"753a9caeb623f963ee9b95926cf848a85396e80df47ebab3c34849dda74f1dce","tx_output_n":0,"tx_output_n2":0,"value":1252350178,"confirmations":39},{"tx_hash":"7e0d76d6e83f17b9c5721b27a3f215e46b962948817eae12e8f70e8dd35f97f1","tx_output_n":0,"tx_output_n2":0,"value":1251671371,"confirmations":19},{"tx_hash":"106003ee6e8d84b6e979d6e590584fd7d9944a339960fdf06ac4e48b251e895e","tx_output_n":0,"tx_output_n2":0,"value":1252104549,"confirmations":15},{"tx_hash":"2a04e4c51db98ee0776baa2ba160808f32f8db46a6d5ff1885509d7081d1abbb","tx_output_n":0,"tx_output_n2":0,"value":1259354186,"confirmations":12},{"tx_hash":"9e39ff6e3fbcc9e9c3cd8169709d99636f324248f61d50cbdb8f2c39a7a6cd87","tx_output_n":0,"tx_output_n2":0,"value":1251208579,"confirmations":2}]}
     * err_no : 0
     * err_msg : null
     */

    private DataBean data;
    private int err_no;
    private String err_msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public static class DataBean {
        /**
         * total_count : 1877
         * page : 1
         * pagesize : 50
         * list : [{"tx_hash":"26c4fe0a07794cebcb4e9b90878726054de13592023087597436dc432bff61f6","tx_output_n":0,"tx_output_n2":0,"value":144022,"confirmations":12773},{"tx_hash":"33b42f6fd9b67c3a502fa355a47465458b688fefe71ad831c4cba149a5ef17e6","tx_output_n":0,"tx_output_n2":0,"value":146482,"confirmations":8973},{"tx_hash":"9a13436b7f0af2efd8ed5fd14c11cdcfd093d2ddcae87c771cc8c70569640d8c","tx_output_n":0,"tx_output_n2":0,"value":1407,"confirmations":8541},{"tx_hash":"13558bf80875c99f30cac289fc7597d83b25f7aed9223032d7e31707e39291e3","tx_output_n":0,"tx_output_n2":0,"value":1102,"confirmations":8538},{"tx_hash":"59d3cf821a016579cb97fb5cd9025f74864b3fcb9b1bc999797e5e16b072a8ef","tx_output_n":0,"tx_output_n2":0,"value":26786,"confirmations":7464},{"tx_hash":"445cad5ba4224abd00a1bab05f6e9f5864164ab628a432121a9437290f7024cc","tx_output_n":1,"tx_output_n2":0,"value":7420,"confirmations":6023},{"tx_hash":"7ad4ac13a41fa088526c3517a484201032033b39bbfcd4dbd6d9635b73fd212b","tx_output_n":53,"tx_output_n2":0,"value":50000,"confirmations":5700},{"tx_hash":"d7a30cfca782063abe76b4f7ce54b388dadae4359971cf3619625d919b32709e","tx_output_n":0,"tx_output_n2":0,"value":1000,"confirmations":5025},{"tx_hash":"a45cfbc577d67c21033058772fd6460a5e856a79e9aba7384475c3e4bc929291","tx_output_n":0,"tx_output_n2":0,"value":5500,"confirmations":923},{"tx_hash":"9fe22e38dbb7f1d812ae61114876f794e60004e5edccc4abc968320b7ceb5334","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":720},{"tx_hash":"facbdf8299361fb5f6c2685cec33668a90f431f060261ea4951187d4433d73b9","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":542},{"tx_hash":"90ef38142258213a1b4a062ff48beb1fe2a3d138d59255b9543075566989a81f","tx_output_n":0,"tx_output_n2":0,"value":550,"confirmations":485},{"tx_hash":"182d07b0d8815b7d8c55884af4d5a8c7578e18f1150cc1e92b14bca0516d8a11","tx_output_n":0,"tx_output_n2":0,"value":1254924235,"confirmations":94},{"tx_hash":"4ef0347278276c68cb1326b4c51ceef3b69ed3274240ef981932a089502609fe","tx_output_n":0,"tx_output_n2":0,"value":1251643571,"confirmations":92},{"tx_hash":"b2b51791bfb06d4520196ae77fdf86245234ccfb6dab72b7161b7b5e1efdf2eb","tx_output_n":0,"tx_output_n2":0,"value":1257767272,"confirmations":90},{"tx_hash":"cea904be1b3b78cea8f29a32f463a271afcee96a7901370c02e588c681dfb3d3","tx_output_n":0,"tx_output_n2":0,"value":1261909345,"confirmations":83},{"tx_hash":"cc9839bda9c77d98981cbc87b4352e3cfa1de029d8ea5dbd1836da8ffb1e0553","tx_output_n":0,"tx_output_n2":0,"value":1264004681,"confirmations":82},{"tx_hash":"1fd9a7adf86eb0c8be65c2b0089ba93ca86bc7bed0952fa68093950c7a820ecc","tx_output_n":0,"tx_output_n2":0,"value":1259761040,"confirmations":81},{"tx_hash":"be042d5383976ac595d0479b6ab164f9d48399aab583e1b9de2c1fd0c9d3c360","tx_output_n":0,"tx_output_n2":0,"value":1280014722,"confirmations":75},{"tx_hash":"24a77ce6b8fa7d6467bc51eec086aea6ba49ea3a723de3d72e551e3b2f1e510d","tx_output_n":0,"tx_output_n2":0,"value":1252423493,"confirmations":58},{"tx_hash":"b32286348318dc944daf4a2537c9f2d21b1c951a2df8e8e08788cd1f81e9afe0","tx_output_n":0,"tx_output_n2":0,"value":1252247560,"confirmations":55},{"tx_hash":"4fe5ea0973658d6470e8419b146fadead4de6bdd907a431d3fa38a609e2f3175","tx_output_n":0,"tx_output_n2":0,"value":1252724261,"confirmations":53},{"tx_hash":"0aec781baaa210b15d812ca62ba2d5c9534b0fa2337262ec7e2e162e499db01c","tx_output_n":0,"tx_output_n2":0,"value":1259530441,"confirmations":52},{"tx_hash":"753a9caeb623f963ee9b95926cf848a85396e80df47ebab3c34849dda74f1dce","tx_output_n":0,"tx_output_n2":0,"value":1252350178,"confirmations":39},{"tx_hash":"7e0d76d6e83f17b9c5721b27a3f215e46b962948817eae12e8f70e8dd35f97f1","tx_output_n":0,"tx_output_n2":0,"value":1251671371,"confirmations":19},{"tx_hash":"106003ee6e8d84b6e979d6e590584fd7d9944a339960fdf06ac4e48b251e895e","tx_output_n":0,"tx_output_n2":0,"value":1252104549,"confirmations":15},{"tx_hash":"2a04e4c51db98ee0776baa2ba160808f32f8db46a6d5ff1885509d7081d1abbb","tx_output_n":0,"tx_output_n2":0,"value":1259354186,"confirmations":12},{"tx_hash":"9e39ff6e3fbcc9e9c3cd8169709d99636f324248f61d50cbdb8f2c39a7a6cd87","tx_output_n":0,"tx_output_n2":0,"value":1251208579,"confirmations":2}]
         */

        private int total_count;
        private int page;
        private int pagesize;
        private List<ListBean> list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * tx_hash : 26c4fe0a07794cebcb4e9b90878726054de13592023087597436dc432bff61f6
             * tx_output_n : 0
             * tx_output_n2 : 0
             * value : 144022
             * confirmations : 12773
             */

            private String tx_hash;
            private int tx_output_n;
            private int tx_output_n2;
            private int value;
            private int confirmations;

            public String getTx_hash() {
                return tx_hash;
            }

            public void setTx_hash(String tx_hash) {
                this.tx_hash = tx_hash;
            }

            public int getTx_output_n() {
                return tx_output_n;
            }

            public void setTx_output_n(int tx_output_n) {
                this.tx_output_n = tx_output_n;
            }

            public int getTx_output_n2() {
                return tx_output_n2;
            }

            public void setTx_output_n2(int tx_output_n2) {
                this.tx_output_n2 = tx_output_n2;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getConfirmations() {
                return confirmations;
            }

            public void setConfirmations(int confirmations) {
                this.confirmations = confirmations;
            }
        }
    }
}
