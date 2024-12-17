

package com.atg.completion.mixin;

import com.atg.completion.Screens.SettingsScreen;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    @Shadow
    @Final
    private boolean showMenu;
    @Unique
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public GameMenuScreenMixin(RunArgs.Game screenHandler, Text text) {
    super(text);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo ci) {
        addDrawableChild(new ButtonWidget.Builder(Text.literal("Completion Crash"), this::SendCrashPackets)
            .position(5, 5)
            .size(100, 20)
            .build()
        );

        addDrawableChild(new TexturedButtonWidget.Builder(Text.literal("settings"), this::OpenSettingsMenu)
            .position(110, 5)
            .size(50, 20)
            .build()
        );
    }

    @Unique
    private void SendCrashPackets(ButtonWidget button) {
        System.out.println("if this prints it works 1");
    }
    @Unique
    private void OpenSettingsMenu(ButtonWidget button) {
        mc.setScreen(new SettingsScreen(this));
        System.out.println("if this prints it works 2");
    }
}
