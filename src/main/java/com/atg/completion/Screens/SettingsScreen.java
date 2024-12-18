package com.atg.completion.Screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Unique;

public class SettingsScreen extends Screen {
    private final Screen parent;

    public SettingsScreen(Screen parent) {
        super(Text.literal("Settings"));
        this.parent = parent;
    }
    @Unique
    private final MinecraftClient mc = MinecraftClient.getInstance();

    protected void init() {
        super.init();

        addDrawableChild(new SliderWidget(80, height/4, 140, 20, Text.literal("NOT IMPLEMENTED YET, DOES NOTHING"), -1.3) {
                @Override
                protected void updateMessage() {

                }

                @Override
                protected void applyValue() {


                }
            }
        );
    }
}
