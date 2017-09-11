package com.guaju.sugertea.model.lybbean;

/**
 * Created by guaju on 2017/9/5.
 */

public class Test {

    /**
     * code : 1
     * msg : 验证通过
     * data : {"username":"wll","userid":"1125","free_sum":"1994818.050","isphonefirst":0,"jxstatus":{"jx_user_status":3,"user_status_code":3},"mstr":"3b3b246b426ede1ec642f53fdf0accd0","invitecode":"2fcc","userMobile":"13811154385"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : wll
         * userid : 1125
         * free_sum : 1994818.050
         * isphonefirst : 0
         * jxstatus : {"jx_user_status":3,"user_status_code":3}
         * mstr : 3b3b246b426ede1ec642f53fdf0accd0
         * invitecode : 2fcc
         * userMobile : 13811154385
         */

        private String username;
        private String userid;
        private String free_sum;
        private int isphonefirst;
        private JxstatusBean jxstatus;
        private String mstr;
        private String invitecode;
        private String userMobile;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFree_sum() {
            return free_sum;
        }

        public void setFree_sum(String free_sum) {
            this.free_sum = free_sum;
        }

        public int getIsphonefirst() {
            return isphonefirst;
        }

        public void setIsphonefirst(int isphonefirst) {
            this.isphonefirst = isphonefirst;
        }

        public JxstatusBean getJxstatus() {
            return jxstatus;
        }

        public void setJxstatus(JxstatusBean jxstatus) {
            this.jxstatus = jxstatus;
        }

        public String getMstr() {
            return mstr;
        }

        public void setMstr(String mstr) {
            this.mstr = mstr;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public static class JxstatusBean {
            /**
             * jx_user_status : 3
             * user_status_code : 3
             */

            private int jx_user_status;
            private int user_status_code;

            public int getJx_user_status() {
                return jx_user_status;
            }

            public void setJx_user_status(int jx_user_status) {
                this.jx_user_status = jx_user_status;
            }

            public int getUser_status_code() {
                return user_status_code;
            }

            public void setUser_status_code(int user_status_code) {
                this.user_status_code = user_status_code;
            }
        }
    }
}
