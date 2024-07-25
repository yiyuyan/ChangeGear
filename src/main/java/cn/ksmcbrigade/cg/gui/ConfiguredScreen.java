package cn.ksmcbrigade.cg.gui;

import cn.ksmcbrigade.cg.ChangeGear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static net.minecraft.client.Options.genericValueLabel;

@OnlyIn(Dist.CLIENT)
public class ConfiguredScreen extends Screen {

    private final OptionInstance<Boolean> stopOn = OptionInstance.createBoolean("gui.cg.stop_on", ChangeGear.config.stopOnConfiguring,(ena)->{
        try {
            ChangeGear.config.setStopOnConfiguring(ena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    private final OptionInstance<Integer> timer = new OptionInstance<>("gui.cg.timer",OptionInstance.noTooltip(),(p_231913_, p_231914_) -> genericValueLabel(p_231913_,Component.translatable("gui.cg.timer_range", p_231914_)), new OptionInstance.IntRange(1,100), 12, (p_231992_) -> {
        try {
            ChangeGear.config.setTimer((float)p_231992_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public ConfiguredScreen() {
        super(Component.translatable("key.cg.configured"));
    }

    @Override
    protected void init() {
        OptionsList list = new OptionsList(Minecraft.getInstance(), this.width, this.height, 24, this.height - 32, 25);

        this.stopOn.set(ChangeGear.config.stopOnConfiguring);
        this.timer.set((int) ChangeGear.config.timer);

        list.addBig(this.stopOn);
        list.addBig(this.timer);

        this.addRenderableWidget(list);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,(p_96057_) -> this.onClose()).bounds((this.width - 200) / 2,this.height - 25,200,20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        this.renderDirtBackground(p_281549_);
        p_281549_.drawCenteredString(font,this.title.getString(),this.width / 2, 8, 16777215);
        super.render(p_281549_,p_281550_,p_282878_,p_282465_);
    }
}
