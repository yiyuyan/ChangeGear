package cn.ksmcbrigade.cg;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

    public final File config;

    public boolean stopOnConfiguring = true;
    public float timer = 2F;

    public boolean c = false;

    public boolean init = false;

    public Config(String config){
        this.config = new File(config);
    }

    public void init() throws Exception {
        if(!init){
            if(!config.exists()){
                save();
            }

            JsonObject object = JsonParser.parseString(Files.readString(this.config.toPath())).getAsJsonObject();
            this.timer = object.get("timer").getAsFloat();
            this.stopOnConfiguring = object.get("stopOnConfiguring").getAsBoolean();
            this.c = object.get("crack").getAsBoolean();
            init = true;
        }
    }

    public void save() throws IOException {
        JsonObject object = new JsonObject();
        object.addProperty("timer",timer);
        object.addProperty("stopOnConfiguring",stopOnConfiguring);
        object.addProperty("crack",false);
        Files.writeString(this.config.toPath(),object.toString());
    }

    public void setStopOnConfiguring(boolean stopOnConfiguring) throws IOException {
        this.stopOnConfiguring = stopOnConfiguring;
        save();
    }

    public void setTimer(float timer) throws IOException {
        this.timer = timer;
        save();
    }

    public void reload() throws Exception {
        init = false;
        init();
    }
}
