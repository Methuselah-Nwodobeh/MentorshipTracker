package com.guidetrack.mentorship_tracker.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBSeeder implements CommandLineRunner {

    private final SeedService seedService;

    @Override
    public void run(String... args) {
        seedService.seedDB();
    }
}
