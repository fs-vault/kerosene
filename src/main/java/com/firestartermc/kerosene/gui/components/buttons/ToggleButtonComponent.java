package com.firestartermc.kerosene.gui.components.buttons;

import com.firestartermc.kerosene.gui.components.buttons.base.ButtonBase;
import com.firestartermc.kerosene.util.message.Message;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import com.firestartermc.kerosene.gui.GuiDefaults;
import com.firestartermc.kerosene.gui.GuiPosition;
import com.firestartermc.kerosene.util.item.ItemBuilder;

import java.util.List;

/**
 * Toggle button
 */
public class ToggleButtonComponent extends ButtonBase {

    private final String title;
    private final List<String> description;
    private final Material enabledMaterial;
    private final Material disabledMaterial;
    private final StateProvider stateProvider;
    private final StateChangeCallback stateChangeCallback;

    /**
     * Create a toggle button.
     * @param x The horizontal position of the toggle button.
     * @param y The vertical position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param material The enabled and disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(int x, int y, String title, String description, Material material, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(new GuiPosition(x, y), title, description, material, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param position The position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param material The enabled and disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(GuiPosition position, String title, String description, Material material, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(position, title, description, material, material, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param x The horizontal position of the toggle button.
     * @param y The vertical position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param material The enabled and disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(int x, int y, String title, List<String> description, Material material, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(new GuiPosition(x, y), title, description, material, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param position The position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param material The enabled and disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(GuiPosition position, String title, List<String> description, Material material, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(position, title, description, material, material, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param x The horizontal position of the toggle button.
     * @param y The vertical position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param enabledMaterial The enabled {@link Material}.
     * @param disabledMaterial The disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(int x, int y, String title, String description, Material enabledMaterial, Material disabledMaterial, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(new GuiPosition(x, y), title, description, enabledMaterial, disabledMaterial, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param position The position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param enabledMaterial The enabled {@link Material}.
     * @param disabledMaterial The disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(GuiPosition position, String title, String description, Material enabledMaterial, Material disabledMaterial, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(position, title, Message.splitString(description, 25), enabledMaterial, disabledMaterial, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param x The horizontal position of the toggle button.
     * @param y The vertical position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param enabledMaterial The enabled {@link Material}.
     * @param disabledMaterial The disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(int x, int y, String title, List<String> description, Material enabledMaterial, Material disabledMaterial, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        this(new GuiPosition(x, y), title, description, enabledMaterial, disabledMaterial, stateProvider, stateChangeCallback);
    }

    /**
     * Create a toggle button.
     * @param position The position of the toggle button.
     * @param title The title of the toggle button.
     * @param description The description of the toggle button.
     * @param enabledMaterial The enabled {@link Material}.
     * @param disabledMaterial The disabled {@link Material}.
     * @param stateProvider The state provider.
     * @param stateChangeCallback The state change callback.
     */
    public ToggleButtonComponent(GuiPosition position, String title, List<String> description, Material enabledMaterial, Material disabledMaterial, StateProvider stateProvider, StateChangeCallback stateChangeCallback) {
        super(position, null);
        this.title = title;
        this.description = description;
        this.enabledMaterial = enabledMaterial;
        this.disabledMaterial = disabledMaterial;
        this.stateProvider = stateProvider;
        this.stateChangeCallback = stateChangeCallback;

        this.setItem(stateProvider.get());
    }

    @Override
    public void onInteract(InteractEvent event) {
        if (!event.getPosition().equals(this.getPosition())) return;


        StateChangeEvent stateChangeEvent = new StateChangeEvent(event.getPlayer(), !stateProvider.get());
        this.stateChangeCallback.toggled(stateChangeEvent);
        if(stateChangeEvent.isCanceled()) {
            GuiDefaults.playBadSelectSound(event.getPlayer());
            return;
        }
        GuiDefaults.playSelectSound(event.getPlayer());

        setItem(stateChangeEvent.getNewState());
        event.getGui().update();
    }

    /**
     * Set the item of the button based on the state.
     * @param state The state to set the item for.
     */
    public void setItem(boolean state) {
        ItemBuilder builder = new ItemBuilder(state ? enabledMaterial : disabledMaterial)
                .name(title)
                .lore(description)
                .addAllItemFlags()
                .addLore()
                .addLore(state ? "&aEnabled" : "&cDisabled");

        if (state) builder = builder.enchantUnsafe(Enchantment.MENDING, 1);
        setItem(builder.build());
    }

    @FunctionalInterface
    public interface StateProvider {
        boolean get();
    }

    @FunctionalInterface
    public interface StateChangeCallback {
        void toggled(StateChangeEvent event);
    }

    /**
     * State change details wrapper.
     */
    public static class StateChangeEvent {

        private final Player player;
        private final boolean newState;
        private boolean canceled;

        public StateChangeEvent(Player player, boolean newState) {
            this.player = player;
            this.newState = newState;
            this.canceled = false;
        }

        /**
         * Gets the player that triggered the state change.
         * @return The player.
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * Gets the new state.
         * @return The state.
         */
        public boolean getNewState() {
            return newState;
        }

        /**
         * Gets whether the state change is canceled.
         * @return True if canceled.
         */
        public boolean isCanceled() {
            return canceled;
        }

        /**
         * Change whether the state change is canceled.
         * @param canceled the cancel state
         */
        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
        }
    }
}
