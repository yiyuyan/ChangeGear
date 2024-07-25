package cn.ksmcbrigade.cg.mixin;

import cn.ksmcbrigade.cg.ChangeGear;
import cn.ksmcbrigade.cg.gui.ConfiguredScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraftforge.network.ConnectionData;
import net.minecraftforge.network.NetworkHooks;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Timer.class)
public class TimerMixin {

    @Mutable
    @Shadow @Final private float msPerTick;

    @Inject(method = "advanceTime",at = @At("HEAD"))
    public void change(long p_92526_, CallbackInfoReturnable<Integer> cir){
        this.msPerTick = 1000.0F / (20.0F * changeGear$getTimer());
    }

    @Unique
    private float changeGear$getTimer(){
        if(ChangeGear.config.stopOnConfiguring && (Minecraft.getInstance().screen instanceof ConfiguredScreen)){
            return 1;
        }
        if(changeGear$notOnServer()) return 1;
        return ChangeGear.config.timer;
    }

    @Unique
    private boolean changeGear$notOnServer(){
        if(ChangeGear.config.c) return false;
        Minecraft MC = Minecraft.getInstance();
        if(MC.isSingleplayer()) return false;
        if(MC.getConnection()==null) return false;
        ConnectionData data = NetworkHooks.getConnectionData(MC.getConnection().getConnection());
        if(data==null) return false;
        return !data.getModList().contains("cg");
    }
}
