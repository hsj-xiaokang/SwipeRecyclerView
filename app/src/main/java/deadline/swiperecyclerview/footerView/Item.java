package deadline.swiperecyclerview.footerView;

/**
 * Created by hsj on 2017/10/23.
 */

public class Item {
    private String name;

    private int imageId;

    public Item(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
