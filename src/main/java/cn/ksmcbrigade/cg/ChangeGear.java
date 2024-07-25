package cn.ksmcbrigade.cg;

import net.minecraftforge.fml.common.Mod;

@Mod("cg")
public class ChangeGear {  //both

    public static Config config = new Config("config/cg-config.json");

    public ChangeGear() throws Exception {
        config.init();
    }
}
