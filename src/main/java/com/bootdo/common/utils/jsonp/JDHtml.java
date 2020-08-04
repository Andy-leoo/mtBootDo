package com.bootdo.common.utils.jsonp;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/04 18:16 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.common.utils.jsonp <br>
 */
public class JDHtml {

    private String img;
    private String title;
    private String price;

    public JDHtml(String img, String title, String price) {
        this.img = img;
        this.title = title;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
