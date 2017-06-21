package rayan.avik.recyclerviewwithjson;

/**
 * Created by pc41 on 01-06-2017.
 */

public class ItemListPojo {
    String title, description, icon;

    public ItemListPojo(String title, String description, String icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
