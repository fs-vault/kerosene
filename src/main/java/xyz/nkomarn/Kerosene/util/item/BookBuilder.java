package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;

public class BookBuilder extends ItemBuilderBase<BookBuilder> {

    public BookBuilder() {
        this(1);
    }

    public BookBuilder(int amount) {
        super(Material.WRITTEN_BOOK, amount);
    }

    public BookBuilder writable() {
        this.item.setType(Material.WRITABLE_BOOK);
        return this;
    }

    public BookBuilder page(String content) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.addPage(content);
        this.item.setItemMeta(meta);
        return this;
    }

    public BookBuilder page(int page, String content) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setPage(page, content);
        this.item.setItemMeta(meta);
        return this;
    }

    public BookBuilder author(String author) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setAuthor(author);
        this.item.setItemMeta(meta);
        return this;
    }

    public BookBuilder title(String title) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setTitle(title);
        this.item.setItemMeta(meta);
        return this;
    }

    public BookBuilder generation(BookMeta.Generation generation) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setGeneration(generation);
        this.item.setItemMeta(meta);
        return this;
    }

}
