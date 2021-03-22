package com.firestartermc.kerosene.item;

import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Utility class intended to allow fast building of Books.
 */
public class BookBuilder extends ItemBuilderBase<BookBuilder> {

    /**
     * Basic written book.
     * amount: 1
     */
    public BookBuilder() {
        this(1);
    }

    /**
     * Basic written book.
     * @param amount The amount of the ItemStack.
     */
    public BookBuilder(int amount) {
        super(Material.WRITTEN_BOOK, amount);
    }

    /**
     * Set the type of the ItemStack to a writable book.
     * @return This instance
     */
    public BookBuilder writable() {
        this.item.setType(Material.WRITABLE_BOOK);
        return this;
    }

    /**
     * Add a page to the end of the book.
     * @param content The content of the page
     * @return This instance
     */
    public BookBuilder page(String content) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.addPage(content);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set a specific page of the book.
     * @param page The index of the page
     * @param content The contents of the page
     * @return This instance
     */
    public BookBuilder page(int page, String content) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setPage(page, content);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the author of the book.
     * @param author The name of the author
     * @return This instance
     */
    public BookBuilder author(String author) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setAuthor(author);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the title of the book
     * @param title The title of the book
     * @return This instance
     */
    public BookBuilder title(String title) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setTitle(title);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the generation of the book.
     * @param generation The generation
     * @return This instance
     */
    public BookBuilder generation(BookMeta.Generation generation) {
        BookMeta meta = (BookMeta) this.item.getItemMeta();
        meta.setGeneration(generation);
        this.item.setItemMeta(meta);
        return this;
    }

}
