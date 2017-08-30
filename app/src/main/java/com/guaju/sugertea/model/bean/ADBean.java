package com.guaju.sugertea.model.bean;

import java.util.List;

/**
 * Created by guaju on 2017/8/30.
 */

public class ADBean {


    /**
     * num : 3
     * content : [{"id":"33","title":"眉眼之间","brief":"眉眼之间","url":0,"content":"","pic":"5978878b7e46d.png","addtime":"1501071243","leixing":"1","shanghuid":"4651","juli":39385},{"id":"34","title":"养生大作战","brief":"养生大作战","url":0,"content":"","pic":"597887c961d68.png","addtime":"1501215068","leixing":"1","shanghuid":"2852","juli":38162},{"id":"32","title":"东方丽人美甲 ","brief":"东方丽人美甲","url":0,"content":"","pic":"597887610a860.png","addtime":"1501151155","leixing":"1","shanghuid":"4594","juli":32436}]
     */

    private int num;
    private List<ContentBean> content;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 33
         * title : 眉眼之间
         * brief : 眉眼之间
         * url : 0
         * content :
         * pic : 5978878b7e46d.png
         * addtime : 1501071243
         * leixing : 1
         * shanghuid : 4651
         * juli : 39385
         */

        private String id;
        private String title;
        private String brief;
        private int url;
        private String content;
        private String pic;
        private String addtime;
        private String leixing;
        private String shanghuid;
        private int juli;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public int getUrl() {
            return url;
        }

        public void setUrl(int url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public String getShanghuid() {
            return shanghuid;
        }

        public void setShanghuid(String shanghuid) {
            this.shanghuid = shanghuid;
        }

        public int getJuli() {
            return juli;
        }

        public void setJuli(int juli) {
            this.juli = juli;
        }
    }
}
