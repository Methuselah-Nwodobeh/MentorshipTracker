package com.guidetrack.mentorship_tracker.config;

import io.github.cdimascio.dotenv.Dotenv;


//@Getter
public class ConfigConstants {
    public final boolean debug;

    public ConfigConstants(){
        Dotenv dotenv = Dotenv.load();
        this.debug = Boolean.parseBoolean(dotenv.get("DEBUG"));
    }
}
