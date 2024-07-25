package cn.ksmcbrigade.cg;

import cn.ksmcbrigade.cg.gui.ConfiguredScreen;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

import static cn.ksmcbrigade.cg.ChangeGear.config;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "cg",value = Dist.CLIENT)
public class ClientHandler {

    public static KeyMapping key = new KeyMapping("key.cg.configured", GLFW.GLFW_KEY_K,"key.categories.gameplay");

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterKeys(RegisterKeyMappingsEvent event){
        event.register(key);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onKeyInput(InputEvent.Key event){
        if(key.isDown()){
            Minecraft.getInstance().setScreen(new ConfiguredScreen());
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("cg").executes(context->{
            context.getSource().sendSystemMessage(Component.literal("Timer: "+config.timer));
            context.getSource().sendSystemMessage(Component.literal("isStopOnConfiguring: "+config.stopOnConfiguring));
            return 0;
        }).then(Commands.argument("timer", FloatArgumentType.floatArg()).executes(context ->
        {
            try {
                config.setTimer(FloatArgumentType.getFloat(context,"timer"));
                context.getSource().sendSystemMessage(CommonComponents.GUI_DONE);
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return 1;
            }
        }).then(Commands.argument("stopOn", BoolArgumentType.bool()).executes(context -> {
            try {
                config.setTimer(FloatArgumentType.getFloat(context,"timer"));
                config.setStopOnConfiguring(BoolArgumentType.getBool(context,"stopOn"));
                context.getSource().sendSystemMessage(CommonComponents.GUI_DONE);
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return 1;
            }
        }))));

        event.getDispatcher().register(Commands.literal("cg-reload").executes(context -> {
            try {
                config.reload();
                context.getSource().sendSystemMessage(CommonComponents.GUI_DONE);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }));
    }
}
