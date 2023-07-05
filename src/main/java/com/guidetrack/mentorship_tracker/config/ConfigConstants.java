package com.guidetrack.mentorship_tracker.config;

import io.github.cdimascio.dotenv.Dotenv;


//@Getter
public class ConfigConstants {
    public final Boolean debug;

    public ConfigConstants(){
        Dotenv dotenv = Dotenv.load();
        this.debug = Boolean.valueOf(dotenv.get("DEBUG"));
    }
}
